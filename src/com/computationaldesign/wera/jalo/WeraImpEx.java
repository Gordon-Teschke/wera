/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.ProductReference;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.ComposedType;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;


/**
 * 
 * @author teschke
 */
public class WeraImpEx extends WeraPricelist
{

	/**
	 * Edit the local|project.properties to change logging behavior (properties 'log4j.*').
	 */
	private static final Logger LOG = Logger.getLogger(WeraPricelist.class.getName());
	private final HashMap m_hashExtImage = new HashMap();
	private final HashMap m_hashWeblink = new HashMap();
	private final WeraManager m_wm = WeraManager.getInstance();
	private final HashMap<String, String> m_hDataLine = new HashMap();
	private ComposedType m_oProductReferenceType = null;
	private EnumerationManager em = null;
	private EnumerationType m_oETProductReferenceTypeEnum = null;
	private EnumerationValue m_evCROSSELLING = null;
	final Collection<String> colErrMessages = new ArrayList();

	/**
	 * Import einer CSV-Datei für Weblinks / Crosslinks / ...
	 * 
	 * @param sPfadName
	 * @param sFileName
	 * @return
	 */
	public String strImportCrosssellingCSV(final String sPfadName, final String sFileName)
	{

		// --- Debug
		LOG.info("strImportCrosssellingCSV ( start ) " + " sFileName=" + sPfadName + sFileName);

		// --- declare vars
		String strLine = "";
		String strColumnName = "";
		WeraProduct oProduct = null;
		final HashMap<String, Integer> hHeader = new HashMap();
		final Collection<String> aMandatoryColsHeader = new ArrayList();
		//final Collection<String> colErrMessages = new ArrayList();

		// --- preset vars ---------------------------------------------------
		// --- initialize
		try
		{
			m_oProductReferenceType = JaloSession.getCurrentSession().getTypeManager().getComposedType(ProductReference.class);
			em = JaloSession.getCurrentSession().getEnumerationManager();
			m_oETProductReferenceTypeEnum = em.getEnumerationType("ProductReferenceTypeEnum");
			m_evCROSSELLING = em.getEnumerationValue(m_oETProductReferenceTypeEnum, "CROSSELLING");
		}
		catch (final Exception e)
		{
			colErrMessages.add("Fehler bei der Initiailisierung. " + e.getMessage());
			LOG.error("Fehler bei der Initiailisierung. " + e.getMessage());
			return StringUtils.join(colErrMessages.toArray(), "<br>");
		}

		// --- list of mandatory columns
		aMandatoryColsHeader.addAll(Arrays.asList("pk", "pk_sis", "produkt-code", "crosslinking-produkte", "weblink #1",
				"weblink #2", "weblink #3", "weblink #4", "weblink #5", "weblink #6", "ext. bild #1", "ext. bild #2", "ext. bild #3",
				"ext. bild #4", "ext. bild #5", "ext. bild #6", "ext. bild #7"));
		// --- preset vars ---------------------------------------------------



		try
		{

			// --- Einlesen der Datei --------------------------------------------------------------------------------------------
			final BufferedReader reader = new BufferedReader(new FileReader(sPfadName + sFileName));
			LOG.info("Daten werden eingelesen...");
			int iCnt = 0;
			while ((strLine = reader.readLine()) != null)
			{
				iCnt++;
				LOG.info("Lese Zeile #" + iCnt + "...");
				// --- Sind Daten vorhanden?
				if (strLine == null || strLine.trim().length() == 0)
				{
					LOG.info("> Zeile #" + iCnt + " ist leer, überspringen ...");
					continue;
				}

				// --- Zeile auftrennen
				final String[] aLineInput = strLine.split("\t");

				// --- first of all we have to initialize the header
				if (hHeader.size() == 0)
				{
					// ---- Headerzeile initialisieren -----------------------------------------------------------
					for (int iCol = 0; iCol < aLineInput.length; iCol++)
					{
						strColumnName = aLineInput[iCol].toLowerCase();
						// LOG.info("Baue Header auf: Spalte " + iCol + " = >" + strColumnName + "<");
						hHeader.put(strColumnName, new Integer(iCol));
					}

					// --- check all mandatory cols
					final Collection<String> colErrMessges = new ArrayList();
					for (final String strMandatoryColumnName : aMandatoryColsHeader)
					{
						if (!hHeader.containsKey(strMandatoryColumnName))
						{
							colErrMessages.add("=> Spalte '" + strMandatoryColumnName + "' nicht vorhanden. Abbruch");
						}
					}

					// ---- Headerzeile initialisieren -----------------------------------------------------------

					// --- some errors?
					if (colErrMessages.size() > 0) // --- Abbruch
					{
						break;
					}

				}
				else
				{

					// --- only full length lines
					if (aLineInput.length > 0)
					{

						// --- get code values as key 
						final String strCode = aLineInput[hHeader.get("produkt-code").intValue()];

						// --- collect line, and remember Codenummer as key
						m_hDataLine.put(strCode.trim(), strLine);

					} // --- if ( aLineInput1.length > 0 ) {
				}

			} // --- while ((strLine = reader.readLine()) != null) {
			  // --- Einlesen der Datei --------------------------------------------------------------------------------------------


			// --- close inputbuffering
			reader.close();


			// --- Daten import starts here --------------------------------------------------------------------------------------

			// --- extimages
			final Collection<ExtImage> colExtImage = new ArrayList();
			colExtImage.addAll(ExtImage.getAllInstances());
			for (final ExtImage oExtImage : colExtImage)
			{
				m_hashExtImage.put(oExtImage.getCode(), oExtImage);
			}

			// --- preset weblinks
			final Collection<Weblink> colWeblink = new ArrayList();
			colWeblink.addAll(Weblink.getAllInstances());
			for (final Weblink oWeblink : colWeblink)
			{
				m_hashWeblink.put(oWeblink.getCode(), oWeblink);
			}

			// --- iterate over all data
			for (final String strAktDataRow : m_hDataLine.values())
			{

				// --- convert the oncemore    
				final String[] aAktDataRow = strAktDataRow.split("\t");
				strLine = strAktDataRow;

				// --- preset 
				final String strCode = aAktDataRow[hHeader.get("produkt-code").intValue()];
				final String strPK = aAktDataRow[hHeader.get("pk").intValue()];
				final String strPK_SIS = aAktDataRow[hHeader.get("pk_sis").intValue()];

				// --- debug
				LOG.info("Bearbeite Produkt Code = " + strCode);

				// --- get dest product
				if (!strPK_SIS.equals(""))
				{
					oProduct = (WeraProduct) JaloSession.getCurrentSession().getItem(strPK_SIS);
				}
				else
				{
					oProduct = (WeraProduct) JaloSession.getCurrentSession().getItem(strPK);
				}

				if (oProduct != null)
				{

					// --- Crosslinking Produkte
					this.__importCrosslinkingProducts(hHeader, aAktDataRow, oProduct);

					// --- import external images
					this.__importExternalImages(hHeader, aAktDataRow, oProduct);

					// ---  weblinks
					this.__importWeblinks(hHeader, aAktDataRow, oProduct);

				}
				else
				{
					colErrMessages.add("ERROR\t<span style='color:red;'>+Produkt not found. strPK=" + strPK + ", strPK_SIS="
							+ strPK_SIS + "</span>");
				}


			} // --- for ( String strAktDataRow  : hDataLine.values() ) {
			  // --- Daten import starts here --------------------------------------------------------------------------------------//




			// --- fertig
			colErrMessages.add("Daten wurden erfoglreich importiert.");

			// --- show all messages
			if (colErrMessages.size() > 0)
			{
				for (final String strErrMessage : colErrMessages)
				{
					LOG.error(strErrMessage);
				}

			}

		}
		catch (final Exception e)
		{
			LOG.error("+ErrorZeile=" + strLine);
			colErrMessages.add("ERROR\t<span style='color:red;'>+ErrorZeile=" + strLine + strLine + "</span>");

			e.printStackTrace();
		}



		// --- end of import
		LOG.info("strImportCrosssellingCSV ( ende ) " + " sFileName=" + sPfadName + sFileName);


		return StringUtils.join(colErrMessages.toArray(), "<br>");
	}

	/**
	 * 
	 * @param hHeader
	 * @param aAktDataRow
	 */
	private void __importExternalImages(final HashMap<String, Integer> hHeader, final String[] aAktDataRow,
			final WeraProduct oProduct)
	{

		// --- declaration
		ExtImage oExtImage = null;
		final Collection colExImages = null;
		final Collection colExImagesNew = new ArrayList();

		/*
		 * // --- get ext images try { colExImages = (Collection) m_wm.getAttribute(oProduct, "extimages"); for ( Iterator
		 * itImages = colExImages.iterator(); itImages.hasNext(); ) { Item oItem = (Item) itImages.next(); oItem.remove();
		 * } } catch ( Exception e ) {}
		 */

		// --- iterate over all images
		for (int iImageNo = 1; iImageNo <= 8; iImageNo++)
		{
			if ((aAktDataRow.length - 1) >= hHeader.get("ext. bild #" + iImageNo).intValue())
			{
				final String strExtImage = aAktDataRow[hHeader.get("ext. bild #" + iImageNo).intValue()].trim();
				if (!strExtImage.equals(""))
				{

					if (this.m_hashExtImage.containsKey(strExtImage))
					{

						// --- get image object
						oExtImage = (ExtImage) this.m_hashExtImage.get(strExtImage);

						// --- no more then once
						if (!colExImagesNew.contains(oExtImage))
						{
							// --- append image
							colExImagesNew.add(oExtImage);

							// --- LOG
							LOG.info("__importExternalImages ( " + strExtImage + ") wird importiert. ");
							colErrMessages.add("OK\t" + oProduct.getCode() + "\t__importExternalImages ( " + strExtImage
									+ ") wird importiert. ");
						}

					}
					else
					{
						// --- LOG
						LOG.error("__importExternalImages ( " + strExtImage + ") wurde nicht gefunden. ");
						colErrMessages.add("ERROR\t" + oProduct.getCode() + "\t<span style='color:red;'>__importExternalImages ( "
								+ strExtImage + ") wurde nicht gefunden.</span>");
					}

				} // --- if (!strExtImage.equals("")) {

			}
		} // --- for ( int iImageNo=1; iImageNo <= 8; iImageNo++ ) {

		// --- add images to product
		if (colExImagesNew != null && colExImagesNew.size() > 0)
		{
			m_wm.setAttribute(oProduct, "extimages", colExImagesNew);
		}
	}

	/**
	 * 
	 * @param hHeader
	 * @param aAktDataRow
	 */
	@SuppressWarnings("empty-statement")
	private void __importCrosslinkingProducts(final HashMap<String, Integer> hHeader, final String[] aAktDataRow,
			final WeraProduct oProduct)
	{

		// --- declaration
		final Collection<ProductReference> colProductReferences = null;
		Collection<ProductReference> colProductReferencesNew = null;
		Collection<WeraProduct> colTargetProducts = null;
		WeraProduct oCrossProduct = null;
		String[] aDataRowCrosslinking = null;
		String strTemp = "";
		String strPK = "";
		String strPK_SIS = "";



		if ((aAktDataRow.length - 1) >= hHeader.get("crosslinking-produkte").intValue())
		{

			// --- get crossselling lsit
			final String strCrosslinkingProdukte = aAktDataRow[hHeader.get("crosslinking-produkte").intValue()];

			// --- we don't want to do tooo much
			if (!strCrosslinkingProdukte.equals(""))
			{
				// --- current crosssellings
				colProductReferencesNew = new ArrayList();
				colTargetProducts = new ArrayList();
				/*
				 * colProductReferences = (Collection<ProductReference>) m_wm.getAttribute(oProduct, "productReferences");
				 * if (colProductReferences != null && colProductReferences.size() > 0) {
				 * colProductReferencesNew.addAll(colProductReferences);
				 * 
				 * // --- create little helper collection for ( ProductReference oProductReference : colProductReferences )
				 * { colTargetProducts.add( (WeraProduct) oProductReference.getTarget() ); } // --- safety to avoid self
				 * reference colTargetProducts.add( oProduct ); }
				 */
				// --- split it and then iterate
				final String[] aCrosslinkingProdukte = strCrosslinkingProdukte.split("\\|");
				for (String strCrosslinkingProdukt : aCrosslinkingProdukte)
				{

					// --- normalize codenr
					strCrosslinkingProdukt = strCrosslinkingProdukt.trim();

					// --- get product object
					if (m_hDataLine.containsKey(strCrosslinkingProdukt))
					{

						// --- get product-data
						strTemp = m_hDataLine.get(strCrosslinkingProdukt);
						aDataRowCrosslinking = strTemp.split("\t");

						// --- preset 
						strPK = aDataRowCrosslinking[hHeader.get("pk").intValue()];
						strPK_SIS = aDataRowCrosslinking[hHeader.get("pk_sis").intValue()];

						LOG.info("__importCrosslinkingProducts(" + strCrosslinkingProdukt + "): retrieving product for PK >" + strPK
								+ "< and SIS PK >" + strPK_SIS + "<");

						// --- get dest product
						if (!strPK_SIS.equals(""))
						{
							oCrossProduct = (WeraProduct) JaloSession.getCurrentSession().getItem(strPK_SIS);
						}
						else
						{
							oCrossProduct = (WeraProduct) JaloSession.getCurrentSession().getItem(strPK);
						}

						// --- don't create it twice :)
						if (!colProductReferencesNew.contains(oCrossProduct))
						{

							// --- create newProductReference
							try
							{
								final HashMap hashNewProductrefence = new HashMap();
								hashNewProductrefence.put("target", oCrossProduct);
								hashNewProductrefence.put("source", oProduct);
								hashNewProductrefence.put("active", new Boolean(true));
								hashNewProductrefence.put("referenceType", m_evCROSSELLING);
								final ProductReference oProductReference = (ProductReference) m_oProductReferenceType
										.newInstance(hashNewProductrefence);

								// --- collect it
								colProductReferencesNew.add(oProductReference);

								// --- LOG
								LOG.info("__importCrosslinkingProducts (" + strCrosslinkingProdukt + ") wird importiert. ");
								colErrMessages.add("OK\t" + oProduct.getCode() + "\t__importCrosslinkingProducts ("
										+ strCrosslinkingProdukt + ") wird importiert. ");

							}
							catch (final Exception e)
							{

								LOG.info("__importCrosslinkingProducts (" + oProduct.getCode() + ") Importfehler. " + e.getMessage());
								colErrMessages.add("ERROR\t" + oProduct.getCode()
										+ "\t<span style='color:red;'>__importCrosslinkingProducts (" + oProduct.getCode()
										+ ") Importfehler. " + e.getMessage() + "</span>");
							}

						} // --- if ( colTargetProducts.contains(oCrossProduct) ) {



					}
					else
					{
						// --- LOG
						LOG.info("__importCrosslinkingProducts (" + strCrosslinkingProdukt
								+ ") wurde nicht gefunden, falsche Referenz, und wird nicht importiert. ");
						colErrMessages
								.add("ERROR\t" + oProduct.getCode() + "\t<span style='color:red;'>__importCrosslinkingProducts ("
										+ strCrosslinkingProdukt
										+ ") wurde nicht gefunden, falsche Referenz, und wird nicht importiert. </span>");
					}


				} // --- for ( String strCrosslinkingProdukte : aCrosslinkingProdukte ) {



				// --- add crossselling to product
				if (colProductReferencesNew != null && colProductReferencesNew.size() > 0)
				{
					m_wm.setAttribute(oProduct, "productReferences", colProductReferencesNew);
				}


			} // --- if ( !strCrosslinkingProdukte.equals("") ) {
		}
	}

	/**
	 * 
	 * @param hHeader
	 * @param aAktDataRow
	 */
	private void __importWeblinks(final HashMap<String, Integer> hHeader, final String[] aAktDataRow, final WeraProduct oProduct)
	{


		// --- declaration
		Weblink oWebLink = null;
		final Collection colWebLink = null;
		final Collection colWebLinkNew = new ArrayList();

		// --- get weblinks
		/*
		 * try { colWebLink = (Collection) m_wm.getAttribute(oProduct, "weblinks"); for ( Iterator itWebLinks =
		 * colWebLink.iterator(); itWebLinks.hasNext(); ) { Item oItem = (Item) itWebLinks.next(); oItem.remove(); } }
		 * catch ( Exception e ) {}
		 */

		// --- iterate over all weblinks
		for (int iLinkNo = 1; iLinkNo <= 6; iLinkNo++)
		{

			if ((aAktDataRow.length - 1) >= hHeader.get("weblink #" + iLinkNo).intValue())
			{
				final String strWebLink = aAktDataRow[hHeader.get("weblink #" + iLinkNo).intValue()];
				if (!strWebLink.equals(""))
				{

					if (this.m_hashWeblink.containsKey(strWebLink))
					{

						// --- get link object
						oWebLink = (Weblink) this.m_hashWeblink.get(strWebLink);

						// --- no more then onces
						if (!colWebLinkNew.contains(oWebLink))
						{
							// --- append weblink
							colWebLinkNew.add(oWebLink);

							// --- LOG
							LOG.info("__importWeblinks (" + strWebLink + ") wird importiert. ");
							colErrMessages
									.add("OK\t" + oProduct.getCode() + "\t__importWeblinks (" + strWebLink + ") wird importiert. ");
						}

					}
					else
					{
						// --- LOG
						LOG.error("__importWeblinks (" + strWebLink + ") wurde nicht gefunden. ");
						colErrMessages.add("ERROR\t" + oProduct.getCode() + "\t<span style='color:red;'>__importWeblinks ("
								+ strWebLink + ") wurde nicht gefunden.</span>");
					}

				} // --- if (!strWebLink.equals("") ) {

			}

		} // --- for ( int iLinkNo=1; iLinkNo <= 6; iLinkNo++ ) {

		// --- add images to product
		if (colWebLinkNew != null && colWebLinkNew.size() > 0)
		{
			m_wm.setAttribute(oProduct, "weblinks", colWebLinkNew);
		}
	}

	/**
	 * Ausgabe einer CSV-Datei aller Produkte / Artikel mit Preisen
	 * 
	 * @param strLanguage
	 * @param strKatalogversionPriceliste
	 * @param strReferenzKatalogversionPrint
	 * @param strFileName
	 * @return
	 */
	public String strExportCrosssellingCSV(final String strLanguage, final String strKatalogversionPriceliste,
			final String strReferenzKatalogversionPrint, final String strFileName)
	{

		// --- Initialize
		final String strInhalt = "";
		final String strVarDE = "";
		final String strVarUS = "";
		String strAbtrieb = "";
		Collection icons1 = null;
		WeraMedia icon = null;
		String strSortKey = "";
		final int iAnzahlSollNettoPrices = 5;
		int iProductCounter = 0;
		final String strFN = "strExportPriceListCSV ==> ";
		String strResult = "";
		String strOutput = "";
		String strLine = "";
		Collection productsSorted = null;
		Collection articles = null;
		final String strPreisInfo = "";
		final String strBesch = "";
		final String strCode = "";
		final String strEAN = "";
		String oPageNr = null;
		final Boolean bNeu = null;
		String strCategorie = "";
		HashMap hashmapValues = new HashMap();
		m_strCatalogversion = strKatalogversionPriceliste;

		// --- Datum für Aushabe initialisieren
		final String strFileDatum = m_wm.InitOutputDatum();

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(strLanguage);

		try
		{
			// --- Erzeuge die Export-Ablage
			final String strOutputFile = CreateOutputPath(strFileDatum, strLanguage) + "/" + strFileName + ".txt";
			final FileWriter fw = new FileWriter(strOutputFile);

			// --- Schreibe Überschrift
			strLine = "\"PK" + "\"\t\"PK_SIS\"\t\"ArtikelNr" + "\"\t\"SortKey" + "\"\t\"Kategorie\"\r\n";
			fw.write(strLine);

			// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
			final CatalogVersion weraCatalogVersion = m_wm.getCatalogVersion(m_strCatalogPriceliste, strKatalogversionPriceliste);

			// --- Hole Instanz WeraKatalog
			final WeraKatalog wk = new WeraKatalog();
			wk.InitCatalogPricelist(m_strCatalogPriceliste);
			wk.InitCatalogPrint(m_strCatalogPrint);

			// --- Alle alle Produkte sortiert nach Order
			strOutput = "Hole sortierte Produktliste (" + strLanguage + ") ...";
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			productsSorted = wk.getProductsFromPreisliste_v2(m_strCatalogPriceliste, strKatalogversionPriceliste,
					strReferenzKatalogversionPrint, 2);
			LOG.info(strFN + "Anzahl Produkte=" + productsSorted.size());


			// --- Schleife über alle Produkte
			strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";

			LOG.info("Looping over " + productsSorted.size() + " products.");
			final int iProdCnt = 0;
			for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();)
			{
				hashmapValues = (HashMap) it1.next();

				// --- Hole Produkt
				m_product = (Product) hashmapValues.get("product");

				// --- Hole OrderKey
				strSortKey = ((Integer) hashmapValues.get("sortkey0")).toString();

				if (m_product instanceof WeraProduct || m_product instanceof WeraProductSet
						|| m_product instanceof WeraProductSetinSet)
				{

					// --- Debug
					LOG.info("++Code=" + m_product.getCode());

					iProductCounter++;

					// --- Initialisieren der Category
					strCategorie = "";
					final Category categoryWera = (Category) hashmapValues.get("category");
					if (categoryWera != null)
					{
						strCategorie = categoryWera.getName();
					}
					if (strCategorie == null)
					{
						strCategorie = "";
					}

					// --- Hole Seitennummern
					oPageNr = (String) hashmapValues.get("pages");
					if (oPageNr == null)
					{
						oPageNr = new String("");
					}

					// --- Abtriebicon holen
					strAbtrieb = "";
					icons1 = ((WeraProduct) m_product).getIcons1();
					if (icons1.size() > 0)
					{
						icon = (WeraMedia) icons1.iterator().next();
						strAbtrieb = icon.getCode();
					}

					// --- Bearbeite Satz
					if (m_product instanceof WeraProductSet || m_product instanceof WeraProductSetinSet)
					{
						Boolean bIstDisplay = null;
						if (m_product instanceof WeraProductSetinSet)
						{
							bIstDisplay = (Boolean) m_wm.getAttribute(m_product, "ist_display");
						}
						if (bIstDisplay == null)
						{
							bIstDisplay = new Boolean(false);
						}
						if (m_product instanceof WeraProductSetinSet && bIstDisplay.booleanValue() == false)
						{

							LOG.info("++class=" + m_product.getClass().getName());

							// --- Satz in Satz (Liste der Sätze
							articles = (Collection) m_wm.getAttribute(m_product, "weraproductsetvariants_qual");

							// --- Schleife über alle Artikel
							WeraProductSetVariants oWeraProductSetVariants = null;
							WeraProductSet oWeraProductset = null;
							for (final Iterator it2 = articles.iterator(); it2.hasNext();)
							{

								// --- Hole den aktuellen Artikel
								oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
								oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");

								// --- Verpackungseinheit
								final Integer oVPE_SIS = (Integer) m_wm.getAttribute(oWeraProductSetVariants, "vpe");

								LOG.info("++class (satz)=" + oWeraProductset.getClass().getName());

								// --- Fülle einen Artikel
								if (oWeraProductset != null)
								{
									// --- Holen der Daten für einen einzelnen Satz
									strLine = getDataWeraProductSet(oWeraProductset, (WeraProductSetinSet) m_product, strLanguage,
											strCategorie, strSortKey, oPageNr, strAbtrieb, oVPE_SIS);
									fw.write(strLine);
								}
							}

						}
						else
						{

							// --- Verpackungseinheit
							final Integer oVPE = (Integer) m_wm.getAttribute(m_product, "contentQuantity");

							// --- Holen der Daten für einen einzelnen Satz
							strLine = getDataWeraProductSet((WeraProductSet) m_product, null, strLanguage, strCategorie, strSortKey,
									oPageNr, strAbtrieb, oVPE);
							fw.write(strLine);
						}

					}
					else
					{

						strLine = "\"" + m_product.getPK() + "\"\t\"\"\t\"" + m_product.getCode() + "\"\t\"" + strSortKey + "\"\t\""
								+ strCategorie + "\"\r\n";
						fw.write(strLine);


					}

				}
			}

			LOG.info(strResult);

			//  --- Schliessen
			strOutput = "Export abgeschlossen. Anzahl Produkte=" + iProductCounter;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			fw.close();

		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Aufräumen
		cleanUp();

		return strResult;
	}

	public String getDataWeraProductSet(final WeraProductSet weraproductset, final WeraProductSetinSet weraproductsetinset,
			final String strLanguage, final String strCategorie, final String strSortKey, final String strPageNr,
			final String strAbtrieb, Integer oVPE)
	{

		// --- Initialize
		String strInhalt = "";
		String strVarDE = "";
		String strVarUS = "";
		final Collection icons1 = null;
		final WeraMedia icon = null;
		final int iAnzahlSollNettoPrices = 5;
		final int iProductCounter = 0;
		final String strFN = "strExportPriceListCSV ==> ";
		final String strResult = "";
		final String strOutput = "";
		String strLine = "";
		final Collection productsSorted = null;
		final Collection articles = null;
		final String strPreisInfo = "";
		String strBesch = "";
		String strCode = "";
		String strEAN = "";
		Boolean bNeu = null;


		// --- Holen der Preisinfos für den Satz
		// strPreisInfo = _getPriceInfo(weraproductset, strKundenPriceListe, iAnzahlSollNettoPrices, "\t");

		// --- Hole ContentQuantity
		//Integer intContentQuantity = (Integer) m_wm.getAttribute(weraproductset, "contentQuantity");
		if (oVPE == null)
		{
			oVPE = new Integer(1);
		}

		// --- Hole VariantenNr(n)
		strVarDE = (String) m_wm.getAttribute(weraproductset, "variantenNr");
		initLanguage("us-en");
		strVarUS = (String) m_wm.getAttribute(weraproductset, "variantenNr");
		initLanguage(strLanguage);

		// --- Ermitteln des Satzinhaltes 
		strInhalt = _getSetContent(weraproductset);

		// --- Schreiben der Artikeldaten
		strBesch = weraproductset.getName().replaceAll("\"", " ");
		strCode = (String) m_wm.getAttribute(weraproductset, "lagerNr") + (String) m_wm.getAttribute(weraproductset, "artnr")
				+ strVarDE;
		bNeu = (Boolean) m_wm.getAttribute(weraproductset, "produkt_neu");
		if (bNeu == null)
		{
			bNeu = new Boolean(false);
		}
		strEAN = (String) m_wm.getAttribute(weraproductset, "ean");
		if (strEAN == null || strEAN.length() == 0)
		{
			strEAN = "";
		}

		String strPK_SIS = "";
		if (weraproductsetinset != null)
		{
			strPK_SIS = weraproductsetinset.getPK().toString();
		}

		String strArtNr = "";
		if (weraproductsetinset != null)
		{
			strArtNr = weraproductsetinset.getCode();
		}
		else
		{
			strArtNr = weraproductset.getCode();
		}

		strLine = "\"" + weraproductset.getPK() + "\"\t\"" + strPK_SIS + "\"\t\"" + strArtNr + "\"\t\"" + strSortKey + "\"\t\""
				+ strCategorie + "\r\n";

		return strLine;
	}
}

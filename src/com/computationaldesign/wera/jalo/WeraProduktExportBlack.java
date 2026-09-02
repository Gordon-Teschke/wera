/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computationaldesign.wera.jalo;

import java.util.Collection;

import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.media.MediaManager;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.c2l.LocalizableItem;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.security.JaloSecurityException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.jdom.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Comment;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author teschke
 *
 * Modifications: Background="J" => Background="N"
 * PrintFileName="pictures/code_schwarz.eps" =>
 * PrintFileName="pictures/test_trans.eps" UnitSymbol="Code => UnitSymbol=" "
 *
 */
public class WeraProduktExportBlack extends WeraProduktExport {

	/**
	 * Used logger instanc
	 * e.
	 */
	private static final Log LOG = LogFactory.getLog(WeraProduktExportBlack.class);
	WeraProduct m_product = null;


	/**
	 * produktExportFlatVersion
	 * Export als eine Datei gemäss übergeordneter Reihenfolge
	 *
	 * @param strLanguage
	 * @param strCatalog
	 * @param strCatalogversion
	 * @param strXmlPath
	 * @param bTextwechsel
	 * @return
	 */
	public String produktExportFlatVersion ( String strLanguage, final String strCatalog,
											final String strCatalogversion, String strXmlPath, final boolean bTextwechsel, final int anzahlProduteJePaket ) {

		// --- setze ausgabedatum
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMdd_HHmmss");
		Date currentTime = new Date();
		String strTimestamp	= formatter.format(currentTime);

		// --- debug
		LOG.info("produktExportMinikatalog() called.");
		LOG.info("produktExportMinikatalog, strCatalog=" + strCatalog + ", strCatalogversion=" + strCatalogversion);

/*
		// --- TExtbausteine einlesen
		LOG.info( "Textbausteine einlesen ... "  );
		final ComposedType TextbausteineType = TypeManager.getInstance().getComposedType(Textbaustein.class);
		final Collection<Textbaustein> Textbausteine = (Collection<Textbaustein>)TextbausteineType.getAllInstances();
		for ( Textbaustein textbaustein : Textbausteine) {
			m_hashTextbausteine.put( (String)textbaustein.getCode(), textbaustein);
		}
*/

		// --- TExtbausteine einlesen
		LOG.info( "Textbausteine einlesen ... "  );
		final ComposedType TextbausteineType = TypeManager.getInstance().getComposedType(Textbaustein.class);
		final Collection<Textbaustein> Textbausteine = (Collection<Textbaustein>)TextbausteineType.getAllInstances();
		for ( Textbaustein textbaustein : Textbausteine) {
			m_hashTextbausteine.put( (String)textbaustein.getCode(), textbaustein);
		}

		// --- Initialize
		String strResultPath = "";
		int iPos = 1;
		Category oAktCategory = null;
		String strCategory = null;
		final Collection categories = null;
		final Collection colCategory2Export = new ArrayList();
		m_strLanguage = strLanguage;
		m_strCatalog = strCatalog;
		m_strCatalogversion = strCatalogversion;
		m_bTextwechsel = bTextwechsel;

		// --- Datum ermitteln
		final String strFileDatum = m_wm.InitOutputDatum();

		// --- Init Sprache / Pfade
		strResultPath = "katalog_" + strCatalogversion + "_" + strFileDatum;
		m_oExportFormatter.SetLanguage(strLanguage);
		m_oExportFormatter.SetExportPath("katalog_" + strCatalogversion + "_" + strFileDatum);

		// --- Vorbereitene Arbeiten abh�ngig von der Ausgabe
		doPrevJob();

		// --- Unser neuer Classifiactioan Helper
		m_weraclassificationhelper = new WeraClassificationHelper();

		// --- LogFile
		m_aLogList = new ArrayList();

		// --- Debug
		output("strLanguage       =" + strLanguage, 1);
		output("strCatalog        =" + strCatalog, 1);
		output("strCatalogversion =" + strCatalogversion, 1);

		// --- Ausgabesprachen
		final ArrayList alSprachen = new ArrayList();
		alSprachen.add(strLanguage);

		// --- Export-Result
		//m_oFileReport = new FileWriter(Config.getParameter("wera.exportpath") + "export_result.txt");
		// --- First off all we have to login
		if (bLogin()) {
			// --- temp. Fussnote "Auslaufartikel"
			if (m_oFootnoteAuslaufartikelTmp == null) {

				try {
					m_oFootnoteAuslaufartikelTmp = Footnote.getFootnodeByCode("discontinued_item");

				} catch (Exception e) {
					LOG.info("+++ Error getFootnodeByCode Footnote Auslaufartikel (discontinued_item) =" + e.getMessage());
					m_oFootnoteAuslaufartikelTmp = null;
				}

			} // --- if ( m_oFootnoteAuslaufartikelTmp == null ) {

			// --- Erzeuge die Export-Ablage
			strXmlPath = CreateOutputPath(strFileDatum, strLanguage);

			// --- Alle alle Produkte sortiert nach Produktnummer
			final WeraKatalog wk = new WeraKatalog();
			String strOutput = "Hole sortierte Produktliste "/* + m_strExportLanguage + "() ..." */;
			LOG.info( strOutput );
			String strResult = "";
			strResult += strOutput + "<br>";
			final String strCatalogVersionName		= strCatalogversion;
			final String strReferenzKatalogVersion	= "print_2018";
			final String strCatalogPrint			= strCatalog;
			final String strCatalogPriceliste		= strCatalog;
			Collection productsSorted = wk.getProductsFromPreisliste_v3(strCatalogVersionName, strReferenzKatalogVersion, strCatalogPrint, strCatalogPriceliste, 2);
			ArrayList products = new ArrayList();
			products.addAll(productsSorted);

			// --- anzahl pakete
			ArrayList	productSublist = new ArrayList();
			int offset = 0;
			LOG.info("productsSorted.size()=" + productsSorted.size() );
			LOG.info("anzahlProduteJePaket=" + anzahlProduteJePaket );
			for ( offset=0; offset < productsSorted.size(); ) {

				int iBisIndex	= offset + anzahlProduteJePaket;
				if ( iBisIndex >= productsSorted.size() ) {
					iBisIndex = productsSorted.size();
				}
				String partInfo	= offset + "_" + iBisIndex + "_" + strTimestamp;

				// --- hole Datenpaket
				productSublist.clear();
				productSublist.addAll(products.subList(offset, iBisIndex));

				// --- Keine Unterkategorien, dann ausgeben
				LOG.info("productsSorted.size()=" + productSublist.size() );
				LOG.info("partInfo=" + partInfo );
				_ProduktExport("root", strLanguage, strCatalog, strCatalogversion, strXmlPath, productSublist, partInfo );

				// --- neuer offset
				offset += anzahlProduteJePaket;
			}
			if ( offset < productsSorted.size() ) {
				LOG.info("REST offset" + offset + ", bis iBisIndex=" + productsSorted.size() );

				// --- Keine Unterkategorien, dann ausgeben
				String partInfo	= offset + "_" + productsSorted.size() + "_" + strTimestamp;

				// --- hole Datenpaket
				productSublist.clear();
				productSublist.addAll(products.subList(offset, productsSorted.size()) );

				LOG.info("R: productsSorted.size()=" + productSublist.size() );
				LOG.info("R: partInfo=" + partInfo );
				_ProduktExport("root", strLanguage, strCatalog, strCatalogversion, strXmlPath, productSublist, partInfo );
			}

			// --- ben�tigte Produktbilder
			try {
				// --- �ffnen der LOG-Datei
				FileWriter m_oFileWriterLog = new FileWriter(strXmlPath + "/kompaktkatalog_imagelist.txt");
				if (m_oFileWriterLog != null) {

					// ---- Schleife �ber alle Zeilen
					Iterator it = m_hashProdukbilder.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry)it.next();
						m_oFileWriterLog.write( (String)pair.getKey() + (String)pair.getValue() + "\r\n" );
						it.remove(); // avoids a ConcurrentModificationException
					}

					// --- Schliesen
					m_oFileWriterLog.close();

					// --- Log l�schen
					m_hashProdukbilder.clear();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// --- ben�tigte FarbCodess
			try {
				// --- �ffnen der LOG-Datei
				FileWriter m_oFileWriterLog = new FileWriter(strXmlPath + "/kompaktkatalog_farben.txt");
				if (m_oFileWriterLog != null) {

					// ---- Schleife �ber alle Zeilen
					Iterator it = m_hashAttributeFarbenList.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry)it.next();
						m_oFileWriterLog.write( (String)pair.getKey() + (String)pair.getValue() + "\r\n" );
						it.remove(); // avoids a ConcurrentModificationException
					}

					// --- Schliesen
					m_oFileWriterLog.close();

					// --- Log l�schen
					m_hashAttributeFarbenList.clear();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
/*
			// --- tempor�r, kann sp�ter entfallen, liste der bullet-points
			try {
				// --- �ffnen der LOG-Datei
				FileWriter m_oFileWriterLog = new FileWriter(strXmlPath + "/kompaktkatalog_bulletpoints.txt");
				if (m_oFileWriterLog != null) {

					// --- header schreiben
					m_oFileWriterLog.write( "UPDATE WeraProduct;pk[unique=true];bulletpoints_print_selection;\r\n" );

					// ---- Schleife �ber alle Zeilen
					Iterator it = m_hashBulletPointList.entrySet().iterator();
					while (it.hasNext()) {
						Map.Entry pair = (Map.Entry)it.next();
						m_oFileWriterLog.write( (String)pair.getKey() + (String)pair.getValue() + "\r\n" );
						it.remove(); // avoids a ConcurrentModificationException
					}

					// --- Schliesen
					m_oFileWriterLog.close();

					// --- Log l�schen
					m_hashBulletPointList.clear();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
*/

		} // if (bLogin()) {

		return strResultPath;
	}


	/**
	 *
	 * @param oTreegroupLink
	 * @param category
	 * @param oTreegroupList
	 * @param oProductList
	 * @param strCatalogversion
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	protected void initCategory(final Element oTreegroupLink, final Category category, final Element oTreegroupList,
								Element oProductList, Element oTipList, final String strCatalogversion, Collection productsSorted ) throws /*JaloInvalidParameterException, */ JaloSecurityException {

		// --- globale kategorie
		m_oCategoryWera = category;

		// --- Eindeutige ProduktNr
		String strRootCategory = "ROOT";
		if (category != null && category.getSupercategory() != null) {
			strRootCategory = category.getSupercategory().getCode();
		}
		//int iOffsetProduktID = m_iProduktID++;
		int iOffsetProduktID = m_iProduktID;
		/*
		 * Random oRandom = new Random( 100 ); int iOffsetProduktID = oRandom.nextInt(); if ( iOffsetProduktID < 0 )
		 * iOffsetProduktID *= -1;
		 */

		// TODO Auto-generated method stub
		// --- Hole alle UNTERKATEGORIE
		// --- Hole alle PRODUKTGRUPPEN
		// --- Hole alle PRODUKTE
		// --- Hole alle MERKMALE PCLASS
		// --- Hole alle MERKMALE WERA
		// --- Hole alle NOTIZEN
		// --- <TreegroupLink LinkedId="1264" LinkedRefCode="01-11-02-00 S�tze" LinkedType="CHAPTER" LinkedWorkflow="OKE_DATEN" Sequence="10" Hierarchy="ONLINE_CATALOG_03/04" LinkedName="S�tze" LinkedTypeName="CHAPTER" LinkedWorkflowName="OKE_DATEN">
		oTreegroupLink.setAttribute("LinkedId", category.getCode());
		oTreegroupLink.setAttribute("LinkedRefCode", strRootCategory);
		oTreegroupLink.setAttribute("LinkedType", "CHAPTER");
		//oTreegroupLink.setAttribute("LinkedWorkflow",     "OK");
		String strOrder = "";
		if (m_wm.getAttribute(category, "order") == null) {
			strOrder = "0000000";
		} else {
			strOrder = "0000000" + m_wm.getAttribute(category, "order").toString();
		}
		oTreegroupLink.setAttribute("Sequence", strOrder.substring(strOrder.length() - 7));
		//oTreegroupLink.setAttribute("Hierarchy",          strCatalogversion);
		oTreegroupLink.setAttribute("LinkedName", "Saetze");
		oTreegroupLink.setAttribute("LinkedTypeName", "MAINCHAPTER");
		//oTreegroupLink.setAttribute("LinkedWorkflowName", "OK");

		// --- Debug
		m_aLogList.add("\n\r" + category.getCode() + " - " + category.getName());

		// --- Initialize Category Data
		output("initCategory=" + category.getCode(), 1);
		//kategorieXML.setAttribute("SPRACHE", m_strLanguage) ;
		//kategorieXML.setAttribute("ID",    category.getCode() ) ;
		//kategorieXML.setAttribute("NAME",  getValidString(category.getName(m_jaloSession.getSessionContext())) );

		// --- Reihenfolge
		//Element orderXML = createOrderElement ( category.getAttribute("order").toString() );
		//kategorieXML.addContent(orderXML);
		// --- Hole alle aktiven Unterkatrgorien, falls vorhanden
		final Collection categories = m_wm.getCategories(m_strCatalog, m_strCatalogversion, category.getCode());
		final Collection categoriesSorted = new ArrayList();
		categoriesSorted.addAll(categories);

		// --- preset
		int iReferenzElementID					=	0;
		m_colExportedTipps						= new ArrayList();
		m_colExportedTextbausteinBullet			= new ArrayList();
		m_colExportedTextbausteinMarketingtext	= new ArrayList();


		//    			 TODO entfernen
		//m_wm.m_bCheckForActivation = false;
		//    			 TODO
		// --- Hole alle aktiven Produkte der aktuellen Kategorie
		final boolean bCheckForActivationOld = m_wm.m_bCheckForActivation;
		SetCheckForActivation(false);
		// final Collection productsSorted = m_wm.getProductsOrderedByCategory(category);
		ReSetCheckForActivation(bCheckForActivationOld);
		//Collection productsSorted = m_wm.getProducts(category);
			/*
			 * Collection productsSorted = new ArrayList(); productsSorted.addAll(products); if ( productsSorted != null &&
			 * productsSorted.size() > 0 ) Collections.sort( (List) productsSorted, new OrderComparatorExport() );
			 */
		output("category=" + category.getName(), 1);
		output("Anzahl Produkte=" + productsSorted.size(), 1);
		if (productsSorted.size() > 0) {
			// --- iterate on all products, sorted by category
			Element produktXML = null;
			for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {

				// --- Hole Produkt
				HashMap hashmapValues = (HashMap) it1.next();
				WeraProduct oWeraProduct = (WeraProduct) hashmapValues.get("product");
				m_product = oWeraProduct;

				// --- Pr�fe Alternativ-Produkt
				final WeraProduct oAlternativProduct = oWeraProduct.getAlternateProduct(m_strLanguage);
				if (oAlternativProduct != null) {
					LOG.info("++Change to alternate product=" + oAlternativProduct.getCode());
					oWeraProduct = oAlternativProduct;
				}

				// --- filter product ---------------------------------------------
				if (_isFilteredProduct(oWeraProduct)) {
					// LOG.info("++Skip _isFilteredProduct " + oWeraProduct.getCode());
					LOG.info("++Skip _isFilteredProduct (Auslaufartikel) " + oWeraProduct.getCode());
					continue;
				} // --- if ( !isFilteredProduct(oWeraProduct) ) {
				// --- filter product ---------------------------------------------

				// --- Ausschlussliste pr�fen
				if (m_hExludeList.contains(oWeraProduct.getCode())) {
					LOG.info("++Skip " + oWeraProduct.getCode());
					continue;
				}
				// --- Filter
				if (!(oWeraProduct instanceof WeraProduct) && !(oWeraProduct instanceof WeraProductSet)) {
					continue;
				}
				if (this.m_oProductsToExport != null && this.m_oProductsToExport.contains(oWeraProduct) == false) {
					continue;
				}


				// --- Hier geht es los
				LOG.info("oWeraProduct=" + oWeraProduct);
				// --- Eindeutige ProduktID
				//m_strProduktID = "P" + iOffsetProduktID;
				m_strProduktID = "P" + m_iProduktID++;
				//iOffsetProduktID++;


				// --- ermitteln der zu exporierenden Tipps --------------------------------------------------------------------
				// --- Tipps des aktuellen Produks holen
				Collection<Tipp> colTippByProducts1	= (Collection<Tipp>)m_wm.getAttribute( oWeraProduct, "sortimenttippref");
				ArrayList<Tipp> colTippByProducts = new ArrayList();
				if ( colTippByProducts1 != null ) { colTippByProducts.addAll(colTippByProducts1); }
				if ( oWeraProduct instanceof WeraProductSetinSet ) {

					Collection<Tipp> colTippByProducts2	= (Collection<Tipp>)m_wm.getAttribute( oWeraProduct, "sortimenttipprefsetinset");
					if ( colTippByProducts2 != null ) { colTippByProducts.addAll(colTippByProducts2); }
				}

				// --- schleife über alle gefundeneen Tipps
				for (Tipp oProductTipp : colTippByProducts ) {

					// --- get code des aktuellen tipps
					String strTippCode	= oProductTipp.getCode();

					// --- pr�fe, ob der Tip bereits exportiert wurde
					if ( !m_colExportedTipps.contains(strTippCode)) {

						// --- collect tipp
						m_colTippByProducts.add(oProductTipp);

						// --- collect exported tipps
						m_colExportedTipps.add(strTippCode);
					}
				}
				// --- ermitteln der zu exporierenden Tipps --------------------------------------------------------------------


				// --- ermitteln der zu exporierenden Bulletpoints -------------------------------------------------------------
				Collection<Textbaustein> colBulletpointsByProducts	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weraproductset2bulletpoints");
				//Collection<Textbaustein> colBulletpointsByVariante	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weravariante2bulletpoints");

				// --- sammeln der Marketingtextbaustein
				_collectTextbausteineBulletpoints ( colBulletpointsByProducts );
				//LOG.info("++++++ collect.m_colTextbausteinBullet.size=" + m_colTextbausteinBullet.size());
				// --- ermitteln der zu exporierenden Bulletpoints -------------------------------------------------------------

				// --- ermitteln der zu exporierenden Marketingtexte -----------------------------------------------------------
				Collection<Textbaustein> colMarketingByProducts	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weraproduct2marketing");
				//Collection<Textbaustein> colMarketingByVariante		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weravariante2marketing");

				// --- sammeln der Marketingtextbaustein
				_collectTextbausteineMarketing ( colMarketingByProducts );
				// LOG.info("++++++ collect.m_colTextbausteinMarketingtext.size=" + m_colTextbausteinMarketingtext.size());
				// --- ermitteln der zu exporierenden Marketingtexte -----------------------------------------------------------


				// --- F�lle LinkListe ( neues Produkt => Categorien )
				// --- <ProductLink LinkedId="3773" LinkedRefCode="U100T601" LinkedType="SET" LinkedWorkflow="OKE_DATEN" LinkedManufacturer="WIHA" LinkedEAN="4010995260156" Sequence="10" Hierarchy="ONLINE_CATALOG_03/04" LinkedName="System 6 Wechselklingen Magnetic Set, 6-tlg." LinkedTypeName="SET" LinkedWorkflowName="OKE_DATEN" LinkedManufacturerDescription="Wiha">
				final Element oProductLink = new Element("ProductLink");
				//  					TODO: Write Log
				String strCode = (String) getAttribute(oWeraProduct, "code");
				if (strCode == null) {
					strCode = m_strProduktID;
					m_aLogList.add(oWeraProduct + " - CodeNr fehlt.");
				}
				oProductLink.setAttribute("LinkedRefCode", m_strProduktID);
				oProductLink.setAttribute("LinkedId", m_strProduktID);
				//oProductLink.setAttribute( "LinkedWorkflow",               "OK" );
				//oProductLink.setAttribute( "LinkedManufacturer",           "WERA" );
				//oProductLink.setAttribute( "LinkedEAN",                    "TD" );
				//  					TODO: Write Log
				strOrder = "";
				if (m_wm.getAttribute(oWeraProduct, "order") == null) {
					strOrder = "00000000";
				} else {
					strOrder = "0000000" + m_wm.getAttribute(oWeraProduct, "order").toString();
				}

				//Integer intOrder = (Integer)getAttribute(oWeraProduct,"order");
				if (strOrder == null) {
					oProductLink.setAttribute("Sequence", "1");
					m_aLogList.add(oWeraProduct + " - Reihenfolge fehlt.");
				} else {
					oProductLink.setAttribute("Sequence", strOrder.substring(strOrder.length() - 7));
				}
				//oProductLink.setAttribute( "Hierarchy",                     strCatalogversion );
				//  					TODO: Write Log
				String strName = (String) getAttribute(oWeraProduct, "name");
				if (strName == null) {
					strName = "??";
					m_aLogList.add(oWeraProduct + " - Name fehlt.");
				}
				//oProductLink.setAttribute( "LinkedName",                    strName );
				//oProductLink.setAttribute( "LinkedWorkflowName",            "OK" );
				//oProductLink.setAttribute( "LinkedManufacturerDescription", "WERA" );
				if (m_strProduktID.equals("P0") && category.getCode().contains("PRO_")) {
					String strTemplateDummy = "";
					if (category.getCode().equals("PRO_4")) {
						strTemplateDummy = "140MM_DUMMY";
					} else {
						strTemplateDummy = "110MM_DUMMY";
					}
					final Element oProductLinkTmp1 = new Element("ProductLink");
					final Element oProductLinkTmp2 = new Element("ProductLink");
					oProductLinkTmp1.setAttribute("LinkedRefCode", "P0");
					oProductLinkTmp1.setAttribute("LinkedId", "P0");
					oProductLinkTmp1.setAttribute("LinkedTypeName", strTemplateDummy);
					oProductLinkTmp1.setAttribute("LinkedType", strTemplateDummy);
					oProductLinkTmp2.setAttribute("LinkedRefCode", "P0");
					oProductLinkTmp2.setAttribute("LinkedId", "P0");
					oProductLinkTmp2.setAttribute("LinkedTypeName", strTemplateDummy);
					oProductLinkTmp2.setAttribute("LinkedType", strTemplateDummy);
					oTreegroupLink.addContent(oProductLinkTmp1);
					oTreegroupLink.addContent(oProductLink);
					oTreegroupLink.addContent(oProductLinkTmp2);
				} else {
					oTreegroupLink.addContent(oProductLink);
				}
				final Comment nameComment = new Comment(strName);
				oTreegroupLink.addContent(nameComment);

				// --- F�lle ProduktListe ( neues Produkt => Categorien )
				produktXML = new Element("Product");
				oProductList.addContent(produktXML);

				// --- F�lle Datenzweig
				//LOG.info("initWeraProduct called (B)...");
				m_strCurrentTemplateName = "";
				m_strCurrentTemplateName = initWeraProduct(produktXML, oWeraProduct, oProductList, m_strCurrentTemplateName);
				//LOG.info("initWeraProduct finshed (E)...");
				//output("E-PRODUKT=" + oWeraProduct.getCode(), 2);

				// --- Setze Templatetyp in Productlink
				oProductLink.setAttribute("LinkedTypeName", m_strCurrentTemplateName);
				oProductLink.setAttribute("LinkedType", m_strCurrentTemplateName);

				// --- SB-Varianten --------------------------------------------------------------------------------------------
				// initalizeSB_Variants ( oTreegroupLink, oProductList , oWeraProduct );
				if (initalizeSB_Variants(oTreegroupLink, oProductList, oWeraProduct)) {
					// --- element enth�lt zugeordnete SB-Varianten (false = kein Abstand, zum n�chsten Template)
					produktXML.setAttribute("LastItem", "false");

					// --- korrigiere ProductID-Offset
					//iOffsetProduktID = m_iProduktID;
				} else {
					// --- element enth�lt kein zugeordnete SB-Varianten (true = Abstand zum n�chsten Template)
					produktXML.setAttribute("LastItem", "true");
				}
				// --- SB-Varianten --------------------------------------------------------------------------------------------

			} // --- for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();)

		} // --- if ( products.size() ) {

		// --- debug
		LOG.info("EOL=" + " - Anzahl Produkte=" + productsSorted.size());
	}


	/**
	 *
	 * @param colCategories
	 * @param strLanguage
	 * @param strCatalog
	 * @param strCatalogversion
	 * @param strXmlPath
	 * @param iPos
	 */
	public void _ProduktExport( final String strAktCategory, final String strLanguage, final String strCatalog,
								final String strCatalogversion, String strXmlPath, Collection productsSorted, final String strXMLPart ) {


		LOG.info("_ProduktExport() called.");
		// --- Initialize
		LOG.info("_ProduktExport, strCatalog=" + strCatalog + ", strCatalogversion=" + strCatalogversion);
		String strXmlFile = "";
		m_strLanguage = strLanguage;
		m_colCategories.add(strAktCategory);

		// --- preset Tip-liste
		m_colTippByProducts				=	new ArrayList();
		m_colTextbausteinMarketingtext	=	new ArrayList();
		m_colTextbausteinBullet			=	new ArrayList();

		// --- Setze Sprache, und Defaultsprache=de
		final Language m_Language = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
		m_jaloSession.getSessionContext().setLanguage(m_Language);
		m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);
		LOG.info("m_Language=" + m_Language);

		try {
			final SAXBuilder builder = new SAXBuilder(); // parameters control validation, etc
			//Document doc = builder.build(strXmlFile);

			// --- MediaXML - Real Rootcontent "Inbetween"
			final Element root = new Element("MediaXML");
			String strLangName = m_Language.getName();
			if (strLangName == null) {
				strLangName = m_strLanguage;
			}
			root.setAttribute("Language", strLangName);
			//root.setAttribute("LanguageName", "Leertext");
			//root.setAttribute("ThumbnailRoot", "TD");
			//root.setAttribute("MotiveRoot", "TD");

			// --- <ReferenceElements HierarchyRefCode="ONLINE_CATALOG_03/04" Type="HIERARCHY">
			final Element oReferenceElements = new Element("ReferenceElements");
			oReferenceElements.setAttribute("Language", strLangName);
			oReferenceElements.setAttribute("HierarchyRefCode", strCatalogversion);
			oReferenceElements.setAttribute("Type", "HIERARCHY");
			if (m_bAktionsprospekt) {
				oReferenceElements.setAttribute("Aktionsliste", "J");
			} else {
				oReferenceElements.setAttribute("Aktionsliste", "N");
			}
			root.addContent(oReferenceElements);

			// --- <TreegroupLink LinkedId="1376" LinkedRefCode="ONLINE_CATALOG_03/04" LinkedType="ROOT" LinkedWorkflow="OKE_DATEN" Sequence="0" Hierarchy="ONLINE_CATALOG_03/04" LinkedName="ONLINE CATALOG 03/04" LinkedTypeName="ROOT" LinkedWorkflowName="OKE_DATEN">
			final Element oKatalogLnkList = new Element("TreegroupLink");
			oKatalogLnkList.setAttribute("LinkedId", "root");
			oKatalogLnkList.setAttribute("LinkedRefCode", strAktCategory);
			oKatalogLnkList.setAttribute("LinkedType", "ROOT");
			oKatalogLnkList.setAttribute("Sequence", "0");
			oKatalogLnkList.setAttribute("LinkedName", "Saetze");
			oKatalogLnkList.setAttribute("LinkedTypeName", "ROOT");
			//oKatalogLnkList.setAttribute("LinkedWorkflowName", "OK");
			//oKatalogLnkList.setAttribute("LinkedWorkflow",     "OK");
			//oKatalogLnkList.setAttribute("Hierarchy",          strCatalogversion);
			oReferenceElements.addContent(oKatalogLnkList);

			// --- <Treegroup-List>
			final Element oTreegroupList = new Element("Treegroup-List");
			root.addContent(oTreegroupList);

			// --- <Treegroup Id="1376" Type="ROOT" RefCode="ONLINE_CATALOG_03/04" Workflow="OKE_DATEN" TypeName="ROOT" Name="ONLINE CATALOG 03/04" WorkflowName="OKE_DATEN">
			Element oTreegroup = createTreegroupElement("root", "ROOT", strCatalogversion, null);

			// --- <Product-List>
			final Element oProductList = new Element("Product-List");
			root.addContent(oProductList);

			// --- <Tip-List>
			final Element oTipList = new Element("Tip-List");
			root.addContent(oTipList);

			// --- Ausgabedatum
			final String strDatum = (new Date()).toLocaleString();
			final Comment dateComment = new Comment(" Datenexport vom: " + strDatum);
			root.addContent(dateComment);

			// --- <Datasheet>
			if (getDatasheetMap() != null) {
				final Map oMapDatasheet = getDatasheetMap();

				// --- <Datasheet> container mit Attributen
				final Element oDatasheet = new Element("Datasheet");
				if (oMapDatasheet.containsKey("author")) {
					oDatasheet.setAttribute("author", (String) oMapDatasheet.get("author"));
				}
				oDatasheet.setAttribute("creationdate", strDatum);

				// --- <DatasheetHeader>
				final Element oDatasheetHeader = new Element("DatasheetHeader");
				if (oMapDatasheet.containsKey("headimage")) {
					oDatasheetHeader.setAttribute("headimage", (String) oMapDatasheet.get("headimage"));
				}

				if (oMapDatasheet.containsKey("breadcrumbList")) {
					final Collection oBreadcrumbList = (Collection) oMapDatasheet.get("breadcrumbList");
					for (final Iterator itList = oBreadcrumbList.iterator(); itList.hasNext();) {
						final LinkedList listSegments = (LinkedList) itList.next();
						final Element oBreadcrumb = new Element("Breadcrumb");

						for (final Iterator itSeg = listSegments.iterator(); itSeg.hasNext();) {
							final String sBreadcrumb = (String) itSeg.next();
							final Element oSegment = new Element("Segment");
							oSegment.addContent(sBreadcrumb);
							oBreadcrumb.addContent(oSegment);
						}
						oDatasheetHeader.addContent(oBreadcrumb);
					}
				}

				// --- <DatasheetFooter>
				final Element oDatasheetFooter = new Element("DatasheetFooter");
				if (oMapDatasheet.containsKey("cataloguepage")) {
					final Element oCatPage = new Element("Catalogue-Page");
					oCatPage.addContent((String) oMapDatasheet.get("cataloguepage"));
					oDatasheetFooter.addContent(oCatPage);
				}
				oDatasheet.addContent(oDatasheetHeader);
				oDatasheet.addContent(oDatasheetFooter);
				root.addContent(oDatasheet);

			}

			// --- Hole alle KATEGORIE
			// --- Hole alle Oberkategorien (nur aktive)
			//Collection categories = m_wm.getCategories(strCatalog,
			//		strCatalogversion, "root");
			final Collection categories = m_wm.getCategories(strCatalog, strCatalogversion, strAktCategory);
			LOG.info("categories=" + categories.size() + ", strAktCategory=" + strAktCategory);
			final Collection colKategorieXML = new ArrayList();
			Element oTreegroupLink = null;

			// --- Wurde diese Kategory f�r den Export ausgew�hlt?
			//LOG.info("strAktCategory=" + strAktCategory);
			if (m_colCategories.contains(strAktCategory)) {


				// --- Hole die Kategorie
				//Category oAktCategory = CatalogManager.getInstance().getCatalogCategory(m_weraCatalogVersion,strAktCategory);
				//Category oAktCategory = CategoryManager.getInstance().getCategoryByCode(strAktCategory);
				final Category oAktCategory = CatalogManager.getInstance()
						.getCatalogCategory(m_weraCatalogVersion, strAktCategory);
				LOG.info("oAktCategory=" + oAktCategory);

				// --- Ausgabe der Kategorie
				if (oAktCategory != null) {
					oTreegroupLink = new Element("TreegroupLink");
					oKatalogLnkList.addContent(oTreegroupLink);

					// --- Hole Category aus Masterkatalog
					final Category oMasterCategory = getMasterCategory(oAktCategory);

					/* temp. umkopieren da �bersetzertool falsch l�uft
					 // --- strCategoryName �bernehmen in Master ----------------------------
					 String strCategoryName = oAktCategory.getName();
					 if (strCategoryName != null)
					 oMasterCategory.setName(strCategoryName);
					 final String strCategorySubTitle = (String) getAttribute(oAktCategory, "untertitel");
					 if ( strCategorySubTitle != null )
					 setAttribute( oMasterCategory, "untertitel", strCategorySubTitle);
					 // ---------------------------------------------------------------------
					 */
					// --- hole f�r alle Ausgabesprache (Multilayer - Texte -------------------------------
					Collection<String> allLanguages = new ArrayList();
					allLanguages.addAll(m_coAdditionallLanguages);
					allLanguages.add(m_strLanguage);
					for (String strLanguageTmp : allLanguages) {

						// --- Setze Sprache, und Defaultsprache=de
						Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(strLanguageTmp);
						m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);

						// --- Attribute f�r TextNode vorbelegen
						HashMap<String, String> hashAttributes = new HashMap();
						hashAttributes.put("Language", strLanguageTmp);

						// --- Hole Category aus Masterkatalog
						String strName = oMasterCategory.getName();
						if (strName == null) {
							strName = "";
						}
						hashAttributes.put("RefCode", strAktCategory);
						hashAttributes.put("Name",  _genBruch(strName));

						// --- get subname
						String strSubTitle = (String) getAttribute(oMasterCategory, "untertitel");
						if (strSubTitle == null) {
							strSubTitle = "";
						}
						hashAttributes.put("Subname",  _genBruch(strSubTitle) );

						// --- <Treegroup Id="2" Type="MAINCHAPTER" RefCode="01 Schraubendreher" Workflow="OKE_DATEN" TypeName="MAINCHAPTER" Name="Schraubendreher" WorkflowName="OKE_DATEN">
						oTreegroup = createTreegroupElement(oMasterCategory.getCode(), "MAINCHAPTER", strCatalogversion, hashAttributes);

						//LOG.info("++strSubTitle=" + strSubTitle);
						oTreegroupList.addContent(oTreegroup);

					} // --- for ( String strAdditionalLange : allLanguages ) {

					// --- Setze Sprache, und Defaultsprache=de
					Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
					m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);
					m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);
					// --- hole f�r alle Ausgabesprache (Multilayer - Texte -------------------------------

					// --- Initialisiere die Daten dieser Kategory
					output(oAktCategory.getCode(), 2);
					initCategory(oTreegroupLink, oAktCategory, oTreegroupList, oProductList, oTipList, strCatalogversion, productsSorted );



					// --- ausgeben der Tips, falls vorhanden ---------------------------------------------------------------------

					// --- wurden tips gefunden?
					if ( m_colTippByProducts != null && m_colTippByProducts.size() > 0 ) {

						// --- preset
						int iTipID	=	0;

						// --- <Tip-List>
						//Element oTipList = new Element("Tip-List");
						//root.addContent(oTipList);

						// --- darstellen der TipLinks
						__generateTipLinks ( "producttippref", oTreegroupLink, m_colTippByProducts, iTipID );

						// --- erstellen der Tips
						__generateTippsElements ( "producttippref", oTipList , m_colTippByProducts, iTipID );

					} // --- if ( m_colTippByProducts != null && m_colTippByProducts.size() > 0 ) {
					// --- ausgeben der Tips, falls vorhanden ---------------------------------------------------------------------


					// --- ausgeben der Textbaustein (Bulletpoints), falls vorhanden ---------------------------------------------------
					// --- wurden Textbausteine gefunden?
					if ( m_colTextbausteinBullet != null && m_colTextbausteinBullet.size() > 0 ) {

						//LOG.info("export m_colTextbausteinBullet.size=" + m_colTextbausteinBullet.size());

						// --- preset
						int iBulletID	=	0;

						// --- <Tip-List>
						Element oBulletList = new Element("Bullet-List");
						root.addContent(oBulletList);

						// --- darstellen der TipLinks
						__generateTextbausteinLinks ( "B_T", "BulletLink", "bullettextref", oTreegroupLink, m_colTextbausteinBullet, iBulletID );

						// --- erstellen der Tips
						__generateTextbausteinElements ( "B", "bullettextref", oBulletList , m_colTextbausteinBullet, iBulletID );

					} // --- if ( m_colTippByProducts != null && m_colTippByProducts.size() > 0 ) {
					// --- ausgeben der Tips, falls vorhanden ---------------------------------------------------------------------

					// --- ausgeben der Textbaustein (Marketingtexte), falls vorhanden ---------------------------------------------------
					// --- wurden Textbausteine gefunden?
					if ( m_colTextbausteinMarketingtext != null && m_colTextbausteinMarketingtext.size() > 0 ) {

						// LOG.info("export m_colTextbausteinMarketingtext.size=" + m_colTextbausteinMarketingtext.size());
						// --- preset
						int iMarketingID	=	0;

						// --- <Tip-List>
						Element oMarketingList = new Element("Marketing-List");
						root.addContent(oMarketingList);

						// --- darstellen der TipLinks
						__generateTextbausteinLinks ( "M_T", "MarketingLink", "marketingtextref", oTreegroupLink, m_colTextbausteinMarketingtext, iMarketingID );

						// --- erstellen der Tips
						__generateTextbausteinElements ( "M", "marketingtextref", oMarketingList , m_colTextbausteinMarketingtext, iMarketingID );

					} // --- if ( m_colTippByProducts != null && m_colTippByProducts.size() > 0 ) {
					// --- ausgeben der Tips, falls vorhanden ---------------------------------------------------------------------


					// --- Kategorie -  Bezeichnung
					Comment dummyComment = new Comment(" Kategorie-Code: " + oAktCategory.getCode());
					root.addContent(dummyComment);
					dummyComment = new Comment(" Kategoriename: " + oAktCategory.getName());
					root.addContent(dummyComment);

					// --- Export-Ablage
					final Integer intOrderSuperCategory = null;
					final Category superCategory = oAktCategory.getSupercategory();
					Integer intOrder = (Integer) oAktCategory.getAttribute("order");
					if (intOrder == null) {
						intOrder = 0;
					}
					final String strFileIndex = "0000" + intOrder;

					final String strXmlFilePrefix = m_oExportFormatter.formatXmlFilePrefix(strFileIndex.substring(strFileIndex
							.length() - 5) + "_");
					strXmlFile = strXMLPart + '_' + strLanguage + "_" + strXmlFilePrefix + strAktCategory + "_products.xml";

					// --- Erzeuge die Export-Ablage, falls nicht vorhanden
					strXmlPath = strXmlPath + m_oExportFormatter.formatXmlFilePrefix("/" + strXmlFilePrefix + strAktCategory);
					LOG.info("mkdir()= " + m_wm.createDirectory(strXmlPath));
				} else {
					LOG.info("+++++ERROR + Category not FOUND." + strAktCategory);

				}
			} else {
				LOG.info("+++++m_colCategories.contains(strAktCategory: ERROR + Category not FOUND." + strAktCategory);

			} // if (m_colCategories.contains(strAktCategory)) {

			// --- ausgeben der Tips, falls vorhanden ---------------------------------------------------------------------


			// --- Zusammenfassung
			Comment dummyComment = new Comment(" Anzahl Produkte: " + m_iCountProduct);
			root.addContent(dummyComment);
			dummyComment = new Comment(" Anzahl Artikel: " + m_iCountArticels);
			root.addContent(dummyComment);

			dummyComment = new Comment(" Anzahl Texte: " + m_iCntText);
			root.addContent(dummyComment);

			dummyComment = new Comment(" Anzahl Fussnoten: " + m_iCntFussnoten);
			root.addContent(dummyComment);

			dummyComment = new Comment(" Anzahl Schlagworte: " + m_iCntSchlagworte);
			root.addContent(dummyComment);

			dummyComment = new Comment(" Sprache: " + m_strLanguage);
			root.addContent(dummyComment);
			output("+++Anzahl Produkte: " + m_iCountProduct, 1);
			output("+++Anzahl Artikel: " + m_iCountArticels, 1);
			output("+++Anzahl Texte: " + m_iCntText, 1);
			output("+++Anzahl Fussnoten: " + m_iCntFussnoten, 1);
			output("+++Anzahl Schlagworte: " + m_iCntSchlagworte, 1);
			output("+++Sprache: " + m_strLanguage, 1);

			// --- Reset Counters
			m_iProduktID = 0;
			Integer iTmpProductGes = (Integer) m_CountProductGes.get(m_strLanguage);
			if (iTmpProductGes == null) {
				iTmpProductGes = 0;
			}
			iTmpProductGes = iTmpProductGes.intValue() + m_iCountProduct;
			m_CountProductGes.put(m_strLanguage, iTmpProductGes); // --- iTmpProductGes Gesamt
			m_iCountProduct = 0;

			m_iCountArticels = 0;
			m_iOffset = 0;
			m_iOffsetArtikel = 0;
			m_iCntText = 0; // --- Text
			m_iCntFussnoten = 0; // --- Fussnoten
			Integer iTmpSchlagworte = (Integer) m_CntSchlagworteGes.get(m_strLanguage);
			if (iTmpSchlagworte == null) {
				iTmpSchlagworte = 0;
			}
			iTmpSchlagworte = iTmpSchlagworte.intValue() + m_iCntSchlagworte;
			m_CntSchlagworteGes.put(m_strLanguage, iTmpSchlagworte); // --- Schlagworte Gesamt
			m_iCntSchlagworte = 0; // --- Schlagworte
			output("+++Reset Counters", 1);

			// --- Document erzeugen und schreiben
			output("+++Document erzeugen und schreiben", 1);
			final Document doc = new Document(root);

			// --- DTD setzen
			output("+++DTD setzen", 1);
			final DocType xhtml = new DocType("MediaXML", "MEDIANDO.dtd");
			doc.setDocType(xhtml);

			// --- ausgabedatei
			if ( strXmlFile == "" ) {
				strXmlFile	= "";
			}

			// --- Debug
			LOG.info("Ausgabe in Datei  =" + strXmlPath + "/" + strXmlFile);
			output("Ausgabe in Datei  =" + strXmlPath + "/" + strXmlFile, 1);

			// --- Formatierung
			final XMLOutputter outp = new XMLOutputter(Format.getPrettyFormat());
			//outp.setIndent("  ");
			//outp.setNewlines(true);

			final FileOutputStream out = new FileOutputStream(strXmlPath + "/" + strXmlFile, false);
			outp.output(doc, out);

			// SJ: Neu (last xml file written)
			m_strXmlFile = strXmlPath + "/" + strXmlFile;

			// --- Dateiliste merken
			m_xmlFileList.add(strXmlPath + "/" + strXmlFile);

		} catch (final Exception e) {
			//System.err.println(e);
			e.printStackTrace();
		}
	}

	/**
	 * modification: Background="J" => Background="N" return result background
	 *
	 * marker (J/N), J=show green column background, N=do not show background
	 *
	 * @param Boolean hasBackground
	 * @return String
	 */
	protected String _getBackgroundMarker(Boolean hasBackground) {

		// --- initialize
		String strBackgroundMarker = "";

		// --- get Background Marker (always none background))
		strBackgroundMarker = "N";

		return strBackgroundMarker;
	}

	/**
	 * modification: UnitSymbol="Code => UnitSymbol=" "
	 *
	 * @param oAttributeList
	 * @param strID
	 * @param strBez
	 * @param strType
	 * @param strWert
	 * @param iOrder
	 * @param strMediaCode
	 * @param stringUnit
	 */
	protected void initMerkmal(final Element oAttributeList, final String strID, final String strBez, final String strType,
			final String strWert, final int iOrder, final String strMediaCode, String stringUnit) {

		// --- unit-symbol code nicht mehr ausgeben
		if (stringUnit.toLowerCase().equals("code")) {
			stringUnit = " ";
		}

		// --- default handlings
		super.initMerkmal(oAttributeList, strID, strBez, strType, strWert, iOrder, strMediaCode, stringUnit);
	}

	/**
	 * modification: PrintFileName="pictures/code_schwarz.eps" =>
	 * PrintFileName="pictures/test_trans.eps"
	 *
	 * @param weramedia
	 * @param strDirectory
	 * @param cProductNr
	 * @param bProductBild
	 * @return
	 */
	protected Element _initImage(WeraMedia weramedia, String strDirectory, String cProductNr, final boolean bProductBild) {
		// --- code - icon unterdr�cken
		if (weramedia != null) {
			if (weramedia.getRealFileName().contains("code_schwarz")) {
				cProductNr = ""; // -> test_trans;
				weramedia = null;
			}

		}

		// --- default handling
		return super._initImage(weramedia, strDirectory, cProductNr, bProductBild, null);
	}

	/**
	 * Version f�r Printausgabe f�r Katalog 2016 zus�tzliches Textelement f�r
	 * weitere Icons
	 *
	 * @param iCntIcon
	 * @param iOrder
	 * @param oIcon1
	 * @param oTexList
	 * @return Element
	 */
	@Override
	protected Element _assign2TextList(int iCntIcon, int iOrder, Element oIconLinks, Element textXML, Element oTexList) {
		
		// --- bildpfad aktionsprospect f�r icons anpassen
		if ( super.isM_bAktionsprospekt() ) {

			String iconPathName	= oIconLinks.getAttribute("Name").getValue();
			iconPathName	= iconPathName.replaceAll("pictures/", "pictures/aktionen/");
			oIconLinks.setAttribute("TypeName", iconPathName);
			oIconLinks.setAttribute("Name", iconPathName);
		}

		if (iCntIcon == 1) {

			// --- Kennzeichner 1 oder 2. Icon-Links
			oIconLinks.setAttribute("second_icon", "N");

			
			// --- 1. icon
			textXML.addContent(oIconLinks);
			
		} else {

			// --- Kennzeichner 1 oder 2. Icon-Links
			oIconLinks.setAttribute("second_icon", "J");

			// --- Icons 2 - n als weitere Textelenmente ablegen
			// --- Textobjekt anlegen
			Element textXML2 = _createTextElement("", "", iOrder, m_iOffset, "BLT_SET", false, "", 0, "", false );
			oTexList.addContent(textXML2);

			// --- 2 - n. icon
			textXML2.addContent(oIconLinks);

			// --- offset erh�hen
			m_iOffset++;
		}

		return oTexList;
	}

	/**
	 * generiert ein Textelement unter ber�cksichtigung der multilay funktionen
	 *
	 * @param strTagContent
	 * @param strLinkType
	 * @param bConvertTag
	 * @return
	 */
	@Override
	protected Element _createTextElementML(String strTagContent, boolean bConvertTag, HashMap<String, String> hashAttributes, Element textXML) {
		// --- Pr�fe auf Br�che
//		if ((strTagContent.contains("\"") || strTagContent.contains("&quot;")) && strTagContent.contains("/")) {
		strTagContent = _genBruch(strTagContent);
//		}

		// --- Text konvertieren
		strTagContent = strTagContent.replaceAll("</", "##e_");
		if (bConvertTag) {
			strTagContent = strTagContent.replaceAll("<", "##");
			strTagContent = strTagContent.replaceAll(">", "##");
		}
		strTagContent = strTagContent.replaceAll("&quot;", "\"");
		strTagContent = strTagContent.trim();

		// --- <TextBlock Language="German">In stabiler Klapptasche mit Klettverschluss zur praktischen Aufbewahrung.</TextBlock>
		final Element inhaltXML = new Element("TextBlock");
		for (String strAttrName : hashAttributes.keySet()) {
			String strAttrValue = hashAttributes.get(strAttrName);
			inhaltXML.setAttribute(strAttrName, strAttrValue);
		}
		//CDATA cdataText = new CDATA(strTagContent);
		strTagContent = strTagContent.replaceAll("##sub##", "");
		strTagContent = strTagContent.replaceAll("##e_sub##", "");
		strTagContent = strTagContent.replaceAll("##sup##", "");
		strTagContent = strTagContent.replaceAll("##e_sup##", "");
		strTagContent = strTagContent.replaceAll("##br##", " ");
		strTagContent = strTagContent.replaceAll("##br /##", " ");
		strTagContent = strTagContent.replaceAll("##br/##", " ");
		strTagContent = this.getExportFormatter().formatDescription(strTagContent);
		inhaltXML.addContent(strTagContent);
		textXML.addContent(inhaltXML);

		return textXML;
	}

	/**
	 * create Text-Elements of default-export language and additional
	 * exportlanguages
	 *
	 * @param hashTagContents
	 * @param oTexList
	 * @param strType
	 * @return
	 */
	protected Element createMultiLayerTextElement(HashMap<String, String> hashTagContents, final Element oTexList, String strType) {

		// --- nur bei Multilayer-Support
		if (!m_bMultilayer) {
			// --- kein Multilayer XML-Version
			return createTextElement(hashTagContents.get(m_strLanguage), oTexList, strType);
		}

		// --- initialize
		Element textXML_LeftContent = null;
		Element textXML_RightContent = null;

		for (String strCurrentLanguage : hashTagContents.keySet()) {

			// --- get current content
			String strTagContent = hashTagContents.get(strCurrentLanguage);
// System.out.println("createMultiLayerTextElement=>" + strTagContent + "<=");

			// --- Initialize
			if (strType == null) {
				strType = "BLT_SET";
			}
			boolean bLeftElement = false;
			boolean bOutput = false;
			String strLeftContent = "";
			String strRightContent = "";

			/* Content s�ubern und nach <br/> splitten */
			strTagContent = prepareForLineSplit(strTagContent);
			final String aList[] = strTagContent.split("<br/>");

			// --- Schleife �ber alle Eintr�ge
			output("normalized-aList.length=" + aList.length, 2);
			for (int iPos = 0; iPos < aList.length; iPos++) {

				// --- initialize
				bOutput = false;
				bLeftElement = false;     // --- linker content nicht vorhanden (=> trigger Template productset1 bei S�tzen)
				strLeftContent = "";
				strRightContent = "";
				final String aElements[] = aList[iPos].split("</b>");

				// --- ist text vorhanden?
				if (aElements.length >= 1) {

					// --- Textz�hler
					m_iCntText++;

					// --- Text-Element aufbauen (left)
					//if ( textXML_LeftContent == null ) {
					textXML_LeftContent = new Element("Text");
					textXML_LeftContent.setAttribute("Id", new Integer(m_iOffset++).toString());
					textXML_LeftContent.setAttribute("Type", strType);
					textXML_LeftContent.setAttribute("LinkType", strType);
					textXML_LeftContent.setAttribute("TypeName", "LC");
					textXML_LeftContent.setAttribute("Sequence", new Integer(iPos).toString());
					textXML_LeftContent.setAttribute("Language", strCurrentLanguage);

					// --- add content to textlist
					oTexList.addContent(textXML_LeftContent);

					//} // --- if textXML_LeftContent textXML == null ) {
					// --- Ausgabetyp pr�fen
					if (strType.equals("BLT_1")) {

						bOutput = true;

						// --- left / right content
						strLeftContent = aElements[0].trim();
						if (aElements.length == 2) {

							// --- rechter content vorhanden
							strRightContent = aElements[1].trim();

							// --- Left-content Einleitendes Steuertag entfernen (aktuelle Wera-Konvention - description1)
							strLeftContent = strLeftContent.replaceAll("<b>", "");

							// --- linker content vorhanden (=> trigger Template productset bei S�tzen)
							bLeftElement = true;

						} // --- if ( aElements.length == 2 ) {
						else {

							// --- rechter content vorhanden, linke content leer
							strLeftContent = "";
							strRightContent = aElements[0].trim();

							// --- right-content Einleitendes Steuertag entfernen (aktuelle Wera-Konvention - description1)
							strRightContent = strRightContent.replaceAll("<b>", "");

						}
						/*                                
						 // --- Text-Element aufbauen (right)
						 if ( textXML_RightContent == null ) {

						 textXML_RightContent = new Element("Text");
						 textXML_RightContent.setAttribute("Id", new Integer(m_iOffset++).toString());
						 textXML_RightContent.setAttribute("Type", strType );
						 textXML_RightContent.setAttribute("LinkType", strType );
						 textXML_RightContent.setAttribute("TypeName", "RIGHT_CONTENT" );
						 textXML_RightContent.setAttribute("Sequence", new Integer(iPos).toString() );

						 // --- add content to textlist
						 oTexList.addContent(textXML_RightContent);

						 } // --- if ( textXML_RightContent == null ) {
						 */
					} else {

						// --- default - only left content
						strLeftContent = aElements[0].trim();
						bOutput = true;
					}

				} // --- if (aElements.length >= 1) {

				if (bOutput) {

					if (strType.equals("BLT_1")) {

						// --- Attribute f�r TextNode vorbelegen
						HashMap<String, String> hashAttributes = new HashMap();
						hashAttributes.put("Language", strCurrentLanguage);

						// --- left / right content ---------------------------------------------
						// --- left-content
						hashAttributes.put("TypeName", "LC");
						textXML_LeftContent = _createTextElementML(strLeftContent, true, hashAttributes, textXML_LeftContent);

						// --- right-content
						hashAttributes.put("TypeName", "RC");
						textXML_LeftContent = _createTextElementML(strRightContent, true, hashAttributes, textXML_LeftContent);
						//textXML_RightContent = _createTextElementML ( strRightContent, true, strCurrentLanguage, textXML_RightContent );

						// --- left / right content ---------------------------------------------
					} else {

						// --- Attribute f�r TextNode vorbelegen
						HashMap<String, String> hashAttributes = new HashMap();
						hashAttributes.put("Language", strCurrentLanguage);

						// --- left content ------------------------------------------------------
						// --- left-content
						textXML_LeftContent = _createTextElementML(strLeftContent, true, hashAttributes, textXML_LeftContent);

						// --- left content ------------------------------------------------------
					}

				} // --- if (bOutput) {

			} // ---for (int iPos = 0; iPos < aList.length; iPos++) {

			// --- Flag f�r ein linkes Tabellenelement setzen
			if ( strType.equals("BLT_1") ) {

				// --- nur hier kann ein linker content vorkommen
				m_bLeftElement = bLeftElement;
			}

		} // --- for ( String strCurrentLanguage : hashTagContents.keySet() ) {

		return oTexList;
	}

	/**
	 * generiert ein einzelnes Textelement
	 *
	 * @param strTypeName
	 * @param strTagContent
	 * @param iOrder
	 * @param iID
	 * @param strLinkType
	 * @param bConvertTag
	 * @return
	 */
	@Override
	protected Element _createTextElement(String strTypeName, String strTagContent, final Integer iOrder, final Integer iID,
			final String strLinkType, final boolean bConvertTag, String strName, int contentCounter, String strVPE, boolean firstItemOfContent ) {
//System.out.println("*+++++~~~ start._createTextElement=" + strTypeName );

		// --- Content bereinigen
		strTypeName = strTypeName.replaceAll("<p>", "");
		strTypeName = strTypeName.replaceAll("</p>", "");
		strTypeName = strTypeName.replaceAll("<b>", "");
		strTypeName = strTypeName.replaceAll("</b>", "");

		// --- Text konvertieren
		strTagContent = strTagContent.replaceAll("</", "##e_");
		if (bConvertTag) {
			strTagContent = strTagContent.replaceAll("<", "##");
			strTagContent = strTagContent.replaceAll(">", "##");
		}
// System.out.println("*+++++~~~ start._createTextElement (V)=" + strTagContent );
		strTagContent = strTagContent.replaceAll("&quot;", "\"");
// System.out.println("*+++++~~~ start._createTextElement (N)=" + strTagContent );
		strTypeName = strTypeName.trim();
		strTagContent = strTagContent.trim();

		// --- elemente pr�fen
		if ( strName == null ) { strName	= ""; }
		if ( strVPE == null ) { strVPE	= ""; }
		
		// --- Element aufbauen
		final Element textXML = new Element("Text");
		textXML.setAttribute("Id", iID.toString());
		textXML.setAttribute("Type", strLinkType);
		textXML.setAttribute("LinkType", strLinkType);
		textXML.setAttribute("TypeName", strTypeName);
		textXML.setAttribute("Sequence", iOrder.toString());
		textXML.setAttribute("title", strName );
		textXML.setAttribute("contentCounter", new Integer(contentCounter).toString()  );
		textXML.setAttribute("vpe", strVPE.toString());
		textXML.setAttribute("firstItemOfContent", firstItemOfContent ? "1" : "0" );
		//textXML.setAttribute("LinkTypeName", strTypeName) ;

		// --- <TextBlock Language="German">In stabiler Klapptasche mit Klettverschluss zur praktischen Aufbewahrung.</TextBlock>
		final Element inhaltXML = new Element("TextBlock");
		inhaltXML.setAttribute("Language", m_strLanguage);
		//CDATA cdataText = new CDATA(strTagContent);
		strTagContent = strTagContent.replaceAll("##br##", " ");
		strTagContent = strTagContent.replaceAll("##br /##", " ");
		strTagContent = strTagContent.replaceAll("##br/##", " ");
//System.out.println("_createTextElement=" + strTagContent);
		strTagContent = this.getExportFormatter().formatDescription(strTagContent);
		inhaltXML.addContent(strTagContent);
		textXML.addContent(inhaltXML);

		return textXML;
	}

	/**
	 * generiert ein Textelement unter ber�cksichtigung der multilay funktionen
	 * 12.03.2015 - TypeName bei BLT_1 wird aufgeteilt in "LC" / "RC"
	 *
	 * @param strTagContent
	 * @param oTexList
	 * @param strType
	 * @return
	 */
	@Override
	protected Element createTextElement(String strTagContent, final Element oTexList, String strType /* "BLT_1" */) {
		// --- <Text Id="16336" Type="BLT_Extra" LinkType="BLT_Extra" TypeName="Extra" LinkTypeName="Extra" Sequence="10">
		//LOG.info("s.createTextElement.strTagContent=" + strTagContent);
// System.out.println("createTextElement=>" + strTagContent + "<=");

		// --- Initialize
		if (strType == null) {
			strType = "BLT_SET";
		}
		boolean bLeftElement = false;
		String strTypeName = "";

		/* Content s�ubern und nach <br/> splitten */
		strTagContent = prepareForLineSplit(strTagContent);
		final String aList[] = strTagContent.split("<br/>");

		// --- Schleife �ber alle Eintr�ge
		boolean bOutput = false;
		Element textXML = null;
		output("normalized-aList.length=" + aList.length, 2);
		for (int iPos = 0; iPos < aList.length; iPos++) {
			// --- strTypeName
			bOutput = false;
			final String aElements[] = aList[iPos].split("</b>");

			// --- Content
			if (aElements.length == 2) {
				strTypeName = aElements[0].trim();
				strTagContent = aElements[1].trim();
				bOutput = true;
			} else {
				if (aElements.length > 0) {
					strTypeName = "  ";
					strTagContent = aElements[0].trim();
					if (strTagContent.length() > 0) {
						bOutput = true;
					}
				}
			}

			if (bOutput) {

				// --- Gibt es ein linkes Tabellenelement=
				if (!strTypeName.equals("  ")) {
					bLeftElement = true;
				}

				// --- Pr�fe auf Br�che
//				if ((strTagContent.contains("\"") || strTagContent.contains("&quot;")) && strTagContent.contains("/")) {
				strTagContent = _genBruch(strTagContent);
//				}
// System.out.println("createTextElement (in)=>" + strTagContent + "<=");

				// --- Textz�hler
				m_iCntText++;

				textXML = _createTextElement(strTypeName, strTagContent, iPos, m_iOffset, strType, true, "", 0, "", false );
				oTexList.addContent(textXML);
				m_iOffset++;

			}
		}

		// --- Flag f�r ein linkes Tabellenelement setzen
		if ( strType.equals("BLT_1") ) {
			
			// --- nur hier kann ein linker content vorkommen
			m_bLeftElement = bLeftElement;
		}

		//LOG.info("e.createTextElement.length=" + strTagContent);
		return oTexList;
	}

	/**
	 * generiert ein Textelement unter ber�cksichtigung der multilay funktionen
	 *
	 * @param strTagContent
	 * @param oTexList
	 * @param strType
	 * @return
	 */
	protected Element createTextElementFliesstext(String strTagContent, final Element oTexList, String strType /* "BLT_MT" */) {
		// --- <Text Id="16336" Type="BLT_Extra" LinkType="BLT_Extra" TypeName="Extra" LinkTypeName="Extra" Sequence="10">
		//LOG.info("s.createTextElement.strTagContent=" + strTagContent);

		// --- Initialize
		if (strType == null) {
			strType = "BLT_MT";
		}
		boolean bLeftElement = false;
		String strTypeName = "";
// System.out.println("createTextElementFliesstext=>" + strTagContent + "<=");

		// --- Content
		strTagContent = prepareForLineSplit(strTagContent);
		Element textXML = null;
		textXML = _createTextElement(strTypeName, strTagContent, 0, m_iOffset, strType, true, "", 0, "", false );
		oTexList.addContent(textXML);
		m_iOffset++;

		// --- Flag f�r ein linkes Tabellenelement setzen
		m_bLeftElement = bLeftElement;

		//LOG.info("e.createTextElement.length=" + strTagContent);
		return oTexList;
	}

	/**
	 * filter products - filter auslaufartikel
	 *
	 * @param WeraProduct
	 * @return boolean
	 */
	protected boolean _isFilteredProduct(WeraProduct oWeraProduct) {
		/*
		 O-Ton RH - Mail vom 18.02.2015
            
		 Produkt => "aktiv/nicht aktiv" wird �ber weralive f�r das Produkt und den Katalog gesteuert
		 S�tze ==> "aktiv/nicht aktiv" wird �ber weralive f�r das Produkt und den Katalog gesteuert
            
		 => Ab M�rz 2016 <=
		 Webkataloge, Printkataloge, Preislisten, BMEcats beinhalten nur noch aktive/g�ltige Varianten, Produkte, S�tze.
		 Dann ist das Flag �Auslaufartikel� irrrelevant (bis es wieder gebraucht wird).
            
		 Boolean bProduktAuslauf = null;
		 if ( oWeraProduct instanceof WeraProductSet ) {
		 bProduktAuslauf = (Boolean) m_wm.getAttribute(oWeraProduct, "artikel_auslauf");
		 }
		 if ( bProduktAuslauf == null )  bProduktAuslauf = new Boolean(false);

		 return bProduktAuslauf.booleanValue();
		 */
		return false;
	}

	/**
	 * filter varianten - filter auslaufartikel
	 * 25.06.2021 - neue Regel Gültigkeit Datum von / Datum bis
	 *
	 * filter varianten - filter auslaufartikel
	 *
	 * @param WeraVariante
	 * @return boolean
	 */
	protected boolean _isFilteredVariante(WeraVariante oWeraVariante) {

		// --- prüfe ob der produkt exportiert werden darf (neue regel, datum von / bis)
		if ( m_wm.getVisibilityForCatalog( oWeraVariante, m_weraCatalogVersion, false ) == -1 ) {

			String strTmpCode = (String) m_wm.getAttribute(oWeraVariante, "code");
			System.out.println( strTmpCode + ", skipped, product not allowd for current catalogversion");
			return true;
		}

		/*
		 O-Ton RH - Mail vom 18.02.2015
            
		 => Ab M�rz 2016 <=
		 Webkataloge, Printkataloge, Preislisten, BMEcats beinhalten nur noch aktive/g�ltige Varianten, Produkte, S�tze.
		 Dann ist das Flag �Auslaufartikel� irrrelevant (bis es wieder gebraucht wird).
		 */
		Boolean bVarianteAuslauf = (Boolean) m_wm.getAttribute(oWeraVariante, "artikel_auslauf");
		if (bVarianteAuslauf == null) {
			bVarianteAuslauf = new Boolean(false);
		}

		return bVarianteAuslauf.booleanValue();
	}

	
	/**
	 * initialize list off SB-Variants (not supported in this object)
	 *
	 * @param ElementoTreegroupLink
	 * @param Element oProductList
	 * @param WeraProduct oWeraHauptProduct
	 * @return 
	 */
	@Override
	protected boolean initalizeSB_Variants(Element oTreegroupLink, Element oProductList, WeraProduct oWeraHauptProduct) {

		Collection<WeraProductSetinSet> colSB_Variants = (Collection<WeraProductSetinSet>) m_wm.getAttribute(oWeraHauptProduct, "weraproductsetinsets_relation");
		if (colSB_Variants.size() > 0) {

			LOG.info("initalizeSB_Variants oWeraHauptProduct.code=" + oWeraHauptProduct.getCode());

			// --- iterate on all
			int iCounter = 1;
			for (WeraProductSetinSet oWeraProductSetinSet : colSB_Variants) {

				LOG.info("initalizeSB_Variants oWeraProductSetinSet.code=" + oWeraProductSetinSet.getCode());
				if (colSB_Variants.size() == iCounter) {
					oProductList = _initalizeSB_Variants(oTreegroupLink, oProductList, oWeraHauptProduct, (WeraProduct) oWeraProductSetinSet, true);
				} else {
					oProductList = _initalizeSB_Variants(oTreegroupLink, oProductList, oWeraHauptProduct, (WeraProduct) oWeraProductSetinSet, false);
				}

				// --- increment counter
				iCounter++;

			} // --- for ( WeraProductSetInSet oWeraProductSetInSet : colSB_Variants ) {

		} // --- if ( colSB_Variants.size() > 0 ) {

		return (colSB_Variants.size() > 0);
	}

	/**
	 * initial SB-Variant-Data
	 *
	 * @param oProductList
	 * @param oWeraHauptProduct
	 * @param oWeraProductSetinSet
	 * @param boolean bLastItem (true=last variant-item)
	 */
	private Element _initalizeSB_Variants(Element oTreegroupLink, Element oProductList, WeraProduct oWeraHauptProduct, WeraProduct oWeraProductSetinSet, boolean bLastItem) {

		// --- initialize
		String strOrder = "";

		// --- F�lle ProduktListe ( neues Produkt => Categorien )
		Element produktXML = new Element("Product");
		oProductList.addContent(produktXML);

		try {
			// --- Eindeutige ProduktID
			m_strProduktID = "P" + m_iProduktID;
			LOG.info("v: NEW m_strProduktID=" + m_strProduktID);

			// --- F�lle LinkListe ( neues Produkt => Categorien )
			// --- <ProductLink LinkedId="3773" LinkedRefCode="U100T601" LinkedType="SET" LinkedWorkflow="OKE_DATEN" LinkedManufacturer="WIHA" LinkedEAN="4010995260156" Sequence="10" Hierarchy="ONLINE_CATALOG_03/04" LinkedName="System 6 Wechselklingen Magnetic Set, 6-tlg." LinkedTypeName="SET" LinkedWorkflowName="OKE_DATEN" LinkedManufacturerDescription="Wiha">
			final Element oProductLink = new Element("ProductLink");

			//  					TODO: Write Log
			String strCode = (String) getAttribute(oWeraProductSetinSet, "code");
			if (strCode == null) {
				strCode = m_strProduktID;
				m_aLogList.add(oWeraProductSetinSet + " - CodeNr fehlt.");
			}
			oProductLink.setAttribute("LinkedRefCode", m_strProduktID);
			oProductLink.setAttribute("LinkedId", m_strProduktID);
			if (m_wm.getAttribute(oWeraHauptProduct, "order") == null) {
				strOrder = "00000000";
			} else {
				strOrder = "0000000" + m_wm.getAttribute(oWeraHauptProduct, "order").toString();
			}

			if (strOrder == null) {
				oProductLink.setAttribute("Sequence", "1");
				m_aLogList.add(oWeraProductSetinSet + " - Reihenfolge fehlt.");
			} else {
				oProductLink.setAttribute("Sequence", strOrder.substring(strOrder.length() - 7));
			}
			String strName = (String) getAttribute(oWeraProductSetinSet, "name");
			if (strName == null) {
				strName = "??";
				m_aLogList.add(oWeraProductSetinSet + " - Name fehlt.");
			}
			oTreegroupLink.addContent(oProductLink);
			final Comment nameComment = new Comment(strName);
			oTreegroupLink.addContent(nameComment);

			// --- F�lle ProduktListe ( neues Produkt => Categorien )
			produktXML = new Element("Product");
			oProductList.addContent(produktXML);

			// --- last variant-item (verwemdet zur Steuerung der Template-Abst�nde)
			if (bLastItem) // ---  (true = Abstand zum n�chsten Template)
			{
				produktXML.setAttribute("LastItem", "true");
			} else // --- (false = kein Abstand, zum n�chsten Template)
			{
				produktXML.setAttribute("LastItem", "false");
			}

			// --- template festlegen f�r sb-variante
			if (oWeraHauptProduct instanceof WeraProductSet) {
				m_strCurrentTemplateName = "PRODUCTSETINSET_SBVARIANT_SORTIMENT";
			} else {
				m_strCurrentTemplateName = "PRODUCTSETINSET_SBVARIANT";
			}
			LOG.info("v:m_strCurrentTemplateName=" + m_strCurrentTemplateName);

			// --- F�lle Datenzweig
			initWeraProduct(produktXML, oWeraProductSetinSet, oProductList, m_strCurrentTemplateName);

			// --- immmer SB-fähig
			produktXML.setAttribute("SB", "J");

			LOG.info("n:m_strCurrentTemplateName=" + m_strCurrentTemplateName);

			// --- Setze Templatetyp in Product-node
			produktXML.setAttribute("LinkedType", m_strCurrentTemplateName);
			produktXML.setAttribute("TypeName", m_strCurrentTemplateName);
			produktXML.setAttribute("Typ", m_strCurrentTemplateName);

			// --- Setze Templatetyp in Productlink
			oProductLink.setAttribute("LinkedTypeName", m_strCurrentTemplateName);
			oProductLink.setAttribute("LinkedType", m_strCurrentTemplateName);

			// --- Eindeutige ProduktID erh�hen
			m_iProduktID++;

		} catch (Exception e) {
			e.printStackTrace();
		}

		return oProductList;
	}

	/**
	 * create inital all media data, supports feature-icon excludelist
	 *
	 * @param WeraProduct weraProduct
	 * @return Element
	 */
	@Override
	protected Element initializeMediaData(final WeraProduct weraProduct) {

		// --- initialize
		WeraMedia weramedia = null;
		Element bildXML = null;
		int iSequence = 1;

		// --- Media-Daten
		final Element motivelistXML = new Element("Motive-List");

		// --- ICON 1 (Liste) ----------------------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.ICON1=");
		// WeraMedia weramedia = m_wm._getPicture(weraProduct, "icons1");
		Collection<WeraMedia> colIconMedias = (Collection<WeraMedia>) m_wm.getAttribute(weraProduct, "icons1");
		if (colIconMedias != null) {

			// --- iterate on all icons
			for (WeraMedia wera_icon : colIconMedias) {

				// --- Hole Artikel / Variante
				if (wera_icon != null) {
					bildXML = createBildElement("ICON1", wera_icon, String.valueOf(iSequence++), "icons", "");
					motivelistXML.addContent(bildXML);
				}

			} // --- for (  WeraMedia wera_icon : colIconMedias ) {

		} // --- if ( colIconMedias != null ) {
		// --- ICON 1 (Liste) ----------------------------------------------------------------------------------------------------------

		// --- ICON 2 ------------------------------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.ICON2=");
		weramedia = m_wm._getPicture(weraProduct, "icons2");
		if (weramedia != null) {
			bildXML = createBildElement("ICON2", weramedia, "2", "icons", "");
			motivelistXML.addContent(bildXML);
		}
		// --- ICON 2 ------------------------------------------------------------------------------------------------------------------

		// --- BILD1 -------------------------------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.PICTURES1=");
		weramedia = m_wm._getPicture(weraProduct, "pictures1");
		if (weramedia != null) {
			
			// --- bildelement aus weramedia object
			bildXML = createBildElement("PICTURE1", weramedia, "1", "pictures", weraProduct.getCode());
			motivelistXML.addContent(bildXML);
			
		} else {
			
			// --- bildelement nicht vorhanden, verwendet virtuelles
			HashMap weraPicture	= new HashMap();
			weraPicture.put("location", weraProduct.normalizeFilenameForImageLookup() + ".jpg" );
			bildXML = createBildreferenzElement("PICTURE1", (Object)weraPicture, "1", "pictures" );
			motivelistXML.addContent(bildXML);

		}
		// --- BILD1 -------------------------------------------------------------------------------------------------------------------

		// --- BILD2 -------------------------------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.PICTURES2=");
		//colIconMedias = (Collection<WeraMedia>) m_wm._getPictures(weraProduct, "others_productpictures");
		Collection<Bildreferenz> colIBildreferenz = (Collection<Bildreferenz>) m_wm.getAttribute(weraProduct, "secondaryimagesref2weraproduct" );
		if (colIBildreferenz != null && colIBildreferenz.size() >  0 ) {

			
			// --- next picture
			Integer iPicture = 2;

			// --- iterate on all icons
			for (Bildreferenz wera_picture2 : colIBildreferenz) {
				if (wera_picture2 != null) {

					/////////////////////////////////////////////////////////////////////////////////////////////////////
					// FILTER
					/////////////////////////////////////////////////////////////////////////////////////////////////////
					// --- pr�fe ausgabe
					boolean exportAllowed	= false;
					if ( m_isPriceListExport ) {
						// --- Pr�fe ob Bild exportiert werden darf f�r Preisliste (use_in_pricelist)
						Boolean boolTemp	= (Boolean)m_wm.getAttribute(weraProduct, "use_in_pricelist");
						if ( boolTemp != null ) {
							exportAllowed	= boolTemp.booleanValue();
						}
					} else {

						// --- Pr�fe ob Bild exportiert werden darf f�r Kataolg (use_in_catalog)
						Boolean boolTemp	= (Boolean)m_wm.getAttribute(weraProduct, "use_in_catalog");
						if ( boolTemp != null ) {
							exportAllowed	= boolTemp.booleanValue();
						}
					}
					// --- bild nicht exportieren?
					if ( !exportAllowed ) {
						continue;
					}
					/////////////////////////////////////////////////////////////////////////////////////////////////////
					
					
					// --- create bild-element aus Referenz
					bildXML = createBildreferenzElement("PICTURE" + iPicture.toString(), wera_picture2, iPicture.toString(), "pictures" );
					motivelistXML.addContent(bildXML);

					// --- bild hochz�hlen
					iPicture++;
				}

			} // --- for (  WeraMedia wera_picture2 : colIconMedias ) {

		}
		// --- BILD2 -------------------------------------------------------------------------------------------------------------------

		// --- Satz in Satz Premium Icon -----------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.Satz in Satz Premium Icon=");
		if (weraProduct instanceof WeraProductSetinSet) {
			// output("WeraProductSetinSet.PREMIUMICON=", 2);
			final EnumerationValue eSbClassificatrion = (EnumerationValue) m_wm.getAttribute(weraProduct, "SbClassificatrion");
			String strSbClassificatrion = "classic";
			if (eSbClassificatrion != null) {
				strSbClassificatrion = eSbClassificatrion.getCode();
			}
			if (strSbClassificatrion.equals("classic") || strSbClassificatrion.length() == 0) {
				strSbClassificatrion = "SB_CLASSIC";
			} else {

				if (strSbClassificatrion.equals("premium_plus")) {
					strSbClassificatrion = "SB_PREMIUMPLUS";
				}
				if (strSbClassificatrion.equals("premium")) {
					strSbClassificatrion = "SB_PREMIUM";
				}
			}
			final Collection colSbClassificatrion = MediaManager.getInstance().getMediaByCode(strSbClassificatrion);
			if (colSbClassificatrion != null && colSbClassificatrion.size() > 0) {
				final WeraMedia oMediaSbClassificatrion = (WeraMedia) colSbClassificatrion.iterator().next();
				bildXML = createBildElement("PREMIUMICON", oMediaSbClassificatrion, "1", "pictures", strSbClassificatrion);
				motivelistXML.addContent(bildXML);
			}
		}
		// --- Satz in Satz Premium Icon -----------------------------------------------------------------------------------------------

		// --- Hole Feature-Icons ------------------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.Feature-Icons=");
		if (true /* m_bAktionsprospekt */) {

			// --- get the feature-icon exclude-liste by current export-category
			String strFeatureIconExcludeListByCategory = weraProduct.getFeatureIconExcludeListByCategory(m_oCategoryWera);

			final HashMap mapFeatureIcons = weraProduct.getFeatureIconsByBooleanProperties();
			if (mapFeatureIcons != null) {
				// --- Hole Collection der Media-Objekte
				final Collection<WeraMedia> colMedia = (Collection<WeraMedia>) mapFeatureIcons.get("iconlist");
				LOG.info("initializeMediaData.colMedia=" + colMedia );
				if (colMedia != null && colMedia.size() > 0) {

					// --- initialize
					Integer iMediaCounter = 0;

					// --- iterate on all medias
					for (WeraMedia oWeraMedia : colMedia) {
						// --- icon nur verwenden wenn nicht in exclude-list enthalten
						if (!strFeatureIconExcludeListByCategory.toLowerCase().contains(":" + oWeraMedia.getCode().toLowerCase() + ":")) {

							// --- FEATRUREICONS n
							iMediaCounter++;

							bildXML = createBildElement("FEATRUREICONS" + iMediaCounter.toString(), oWeraMedia, iMediaCounter.toString(), "features", "");
							motivelistXML.addContent(bildXML);

						} // --- if ( !strFeatureIconExcludeListByCategory.toLowerCase().contains( ":"+oWeraMedia.getCode().toLowerCase()+":" ) ) {
					}

				} // --- if ( colMedia != null && colMedia.size() > 0 )
			}

		} // --- if ( m_bAktionsprospekt ) {
		// --- Hole Feature-Icons ------------------------------------------------------------------------------------------------------

		return motivelistXML;
	}

	/**
	 * initialize the template-name
	 *
	 * @param WeraProduct weraProduct
	 * @return String strTemplate
	 */
	@Override
	protected String _initTemplateName(WeraProduct weraProduct) {

		// --- initialize
		String strTemplate = null;

		// --- get templatename from hybris-product-data
		strTemplate = (String) getAttribute(weraProduct, "outputtemplate");
		LOG.info("_initTemplateNamem.outputtemplate (oc)=" + strTemplate );

		// --- Template ggf. korrigieren
		strTemplate = strTemplate.toUpperCase();
		if (strTemplate.equals("PRODUKT")) {
			strTemplate = "PRODUCT";
		}
		if (strTemplate.equals("PRODUKTSET")) {
			strTemplate = "PRODUCTSET";
		}

		// --- set template, falls nicht bereits gesetzt
		if (( strTemplate == null || strTemplate.equals("")) || (!strTemplate.toUpperCase().equals("PRODUCT8") && !strTemplate.toUpperCase().equals("PRODUCT16") && !strTemplate.toUpperCase().equals("COLOREDPRODUCT"))) {

			// --- ist das produkt ein SIS?
			if (weraProduct instanceof WeraProductSetinSet ) {

				// --- prüfen ob der SIS weitere SIS enthält ------------------------------------
				if ( ((WeraProductSetinSet)weraProduct).containsSIS() ) {

					// --- SIS mit enthaltenmen SIS
					// --- version 3 mit allen Inhalten
					LOG.info("+++++++++SIS containsSIS" );
					strTemplate = "PRODUCTSETINSET_IN_SET_V3";

				} else {
/*
					// --- "normales" SIS
					Boolean istDisplay = ((WeraProductSetinSet)weraProduct).isDisplay();
					if ( istDisplay.booleanValue() ) {

						LOG.info("+++++++++SIS istDisplay" );
						strTemplate = "PRODUCTSETINSET_NO_CONTENT";

					} else {

						strTemplate = "PRODUCTSETINSET";
					}
*/
				}

			} // if (weraProduct instanceof WeraProductSetinSet ) {
			else {

				// --- ist das Produkt ein Satz?
				if (weraProduct instanceof WeraProductSet) {

					// --- normaler SATZ
					strTemplate = "PRODUCTSET";

				} else {

					// --- normales Produkt
					strTemplate = "PRODUCT";
				}
			}

		} // --- if ((strTemplate == null || strTemplate.equals("")) || ...

		LOG.info("_initTemplateNamem.outputtemplate return=" + strTemplate );

		// --- maximale Anzahl an Spalten für KK - Katalog -------------------------
		if ( m_isPriceListExport ) {

			// --- Katalog export - 16 Spalten
			if ( strTemplate.toUpperCase().equals("PRODUCT16")) {

				// --- neues 16 Spaltiges Template, max. 15 Wertespalten
				this.setM_maxAttributeCols(15);

			} else {

				// --- default: max. 6 Werte-Spalten
				this.setM_maxAttributeCols(6);
			}

		} // if ( m_isPriceListExport ) {
		// --- maximale Anzahl an Spalten für KK - Katalog -------------------------

		return strTemplate;
	}

}

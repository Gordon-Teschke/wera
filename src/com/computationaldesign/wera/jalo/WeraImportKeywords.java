package com.computationaldesign.wera.jalo;

import java.io.*;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.Keyword;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloAbstractTypeException;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.util.Config;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import org.apache.log4j.Logger;

public class WeraImportKeywords extends WeraManager {

	private static final Logger LOG = Logger.getLogger(DataImport.class.getName());

	public WeraManager m_wm = null;

	public Product m_product = null;

	public CatalogVersion m_weraCatalogVersion = null;

	public WeraImportKeywords() {
		super();
		// TODO Auto-generated constructor stub
		m_wm = WeraManager.getInstance();
	}
	
	/**
	 * 
	 * @param sPfadName
	 * @param sFileName 
	 */
	public void importKeywordCSV (final String sPfadName, final String sFileName)
	{

		// --- Initialize
		final WeraManager wm = WeraManager.getInstance();
		Collection languages = C2LManager.getInstance().getAllLanguages();
		int iImportedRows				= 0;
		int iImportedKeywordsVariante	= 0;
		int iImportedKeywordsProductset	= 0;
		WeraProduct	oBaseProduct		= null;
 		String strPK_Code = "";
		String strCode = "";
		String strContent = "";
		final String PK_CODE = "pk";
		final String DEUTSCH = "deutsch";
		final String ENGLISCH = "englisch";
		final String DAENISCH = "daenisch";
		final String TSCHECHISCH = "tschechisch";
		final String RUSSISCH = "russisch";
		final String NIEDERLAENDISCH = "niederlaendisch";
		final String POLNISCH = "polnisch";
		final String SPANISCH = "spanisch";
		final String FRANZOESISCH = "franzoesisch";
		final String ITALIENISCH = "italienisch";
		
 		final String DELIMETER = "\t";
		final HashMap hHeader = new HashMap();
		final HashMap hProducts = new HashMap();
 		String[] aKeywords;
 		String line = "";
 		Integer iColLanguage		= null;
		Integer iColPK_CODE			= null;
		Integer iColDEUTSCH			= null;
		Integer iColENGLISCH		= null;
		Integer iColDAENISCH		= null;
		Integer iColRUSSISCH		= null;
		Integer iColTSCHECHISCH		= null;
		Integer iColNIEDERLAENDISCH	= null;
		Integer iColPOLNISCH		= null;
		Integer iColSPANISCH		= null;
		Integer iColFRANZOESISCH	= null;
		Integer iColITALIENISCH		= null;
 

		// --- aktuelle Katalogversion setzen
		m_weraCatalogVersion = m_wm.getCatalogVersion("weracatalog", "weramaster");

		// --- Debug
		LOG.info("importKeywordCSV ( start ) " + " sFileName=" + sPfadName + sFileName);

		try
		{
			// --- Einlesen der Header ---------------------------------------------------------------------------------------------
			final BufferedReader reader = new BufferedReader(new FileReader(sPfadName + sFileName));
			LOG.info("Daten werden eingelesen...");
			while ((line = reader.readLine()) != null)
			{
				// --- Sind Daten vorhanden?
				if (line == null || line.trim().length() == 0)
				{
					continue;
				}

				// --- Zeile bearbeiten
				final String[] aLineInput = line.split("\t");
				if (hHeader.size() == 0)
				{
					// ---- Headerzeile initialisieren -----------------------------------------------------------
					for (int iCol = 0; iCol < aLineInput.length; iCol++)
					{
						hHeader.put(aLineInput[iCol].toLowerCase(), new Integer(iCol));
					}
					iColPK_CODE = ((Integer) hHeader.get(PK_CODE));
					if (iColPK_CODE == null)
					{
						LOG.error("=> Spalte '" + PK_CODE + "' nicht vorhanden. Abbruch");
						return;
					}
					iColDEUTSCH = ((Integer) hHeader.get(DEUTSCH));
					if (iColDEUTSCH == null)
					{
						LOG.error("=> Spalte '" + DEUTSCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColENGLISCH = ((Integer) hHeader.get(ENGLISCH));
					if (iColENGLISCH == null)
					{
						LOG.error("=> Spalte '" + ENGLISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColDAENISCH = ((Integer) hHeader.get(DAENISCH));
					if (iColDAENISCH == null)
					{
						LOG.error("=> Spalte '" + DAENISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColRUSSISCH = ((Integer) hHeader.get(RUSSISCH));
					if (iColRUSSISCH == null)
					{
						LOG.error("=> Spalte '" + RUSSISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColTSCHECHISCH = ((Integer) hHeader.get(TSCHECHISCH));
					if (iColTSCHECHISCH == null)
					{
						LOG.error("=> Spalte '" + TSCHECHISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColNIEDERLAENDISCH = ((Integer) hHeader.get(NIEDERLAENDISCH));
					if (iColNIEDERLAENDISCH == null)
					{
						LOG.error("=> Spalte '" + NIEDERLAENDISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColPOLNISCH = ((Integer) hHeader.get(POLNISCH));
					if (iColPOLNISCH == null)
					{
						LOG.error("=> Spalte '" + POLNISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColSPANISCH = ((Integer) hHeader.get(SPANISCH));
					if (iColSPANISCH == null)
					{
						LOG.error("=> Spalte '" + SPANISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColITALIENISCH = ((Integer) hHeader.get(ITALIENISCH));
					if (iColITALIENISCH == null)
					{
						LOG.error("=> Spalte '" + ITALIENISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					iColFRANZOESISCH = ((Integer) hHeader.get(FRANZOESISCH));
					if (iColFRANZOESISCH == null)
					{
						LOG.error("=> Spalte '" + FRANZOESISCH + "' nicht vorhanden. Abbruch");
						return;
					}
					// ---- Headerzeile initialisieren -----------------------------------------------------------

				}
				else
				{
					
					// --- Zeile merken
					strPK_Code = aLineInput[iColPK_CODE.intValue()];
					if (aLineInput.length > iColPK_CODE.intValue())
					{
						LOG.info("iColPK_CODE.intValue()=" + iColPK_CODE.intValue() + ", PH="+strPK_Code  + ", lenght=" + aLineInput.length);
						strPK_Code = aLineInput[iColPK_CODE.intValue()];
						if (strPK_Code.length() > 2 )
						{
							hProducts.put(strPK_Code, aLineInput);
							LOG.info("storing line to key " + strPK_Code );
							
							// --- zählen
							iImportedRows++;
						}

					}
				}
			}
			reader.close();

			
			// --- Hole alle WeraVarianten ----------------------------------------------------------------
			LOG.info("##");
			LOG.info("Artikel werden zugewiesen...");
			final ComposedType WeraVarianteType = JaloSession.getCurrentSession().getTypeManager()
					.getComposedType(WeraVariante.class);
			final Set weravariants = WeraVarianteType.getAllInstances();
			WeraVariante weravariant = null;
			for (final Iterator it1 = weravariants.iterator(); it1.hasNext();)
			{
				// --- Get WeraVariante
				weravariant = (WeraVariante) it1.next();
				if (weravariant instanceof WeraVarianteSet)
				{
					continue;
				}

				// --- hole das basisprodukt
				oBaseProduct	=  (WeraProduct) wm.getAttribute(weravariant, "baseproduct");
				
				// --- Prüfe, ob eine Datenzeile vorhanden ist
				if ( oBaseProduct != null ) {
					
					strPK_Code = oBaseProduct.getPK().toString();
					strCode = "05" + weravariant.getCode() + "001";
					final String[] aLineInput = (String[]) hProducts.get(strPK_Code);
					if (aLineInput != null && aLineInput.length > 0) {
						
						LOG.info("WeraVariante.found PK="+strPK_Code  + ", strVarianteCode=" + strCode + ", oBaseProduct=" + oBaseProduct.getCode() );

						// --- zählen
						iImportedKeywordsVariante++;
						
						// --- Schleife über alle Sprachen, keywords löschen und neu anlegen -------------------------------------------------------------------
						for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext();) {
							String strLang = ((Language) itLanguages.next()).getIsoCode();
							if ( strLang.equals("de") ) {
								iColLanguage = iColDEUTSCH;
								
							} else if ( strLang.equals("en") ) { 
							
								iColLanguage = iColENGLISCH;
								
							} else if ( strLang.equals("dk") ) { 
							
								iColLanguage = iColDAENISCH;
								
							} else if ( strLang.equals("cs") ) { 
							
								iColLanguage = iColTSCHECHISCH;
								
							} else if ( strLang.equals("ru") ) { 
							
								iColLanguage = iColRUSSISCH;
								
							} else if ( strLang.equals("nl") ) { 
							
								iColLanguage = iColNIEDERLAENDISCH;
								
							} else if ( strLang.equals("pl") ) { 
							
								iColLanguage = iColPOLNISCH;
								
							} else if ( strLang.equals("es") ) { 
							
								iColLanguage = iColSPANISCH;
								
							} else if ( strLang.equals("it") ) { 
							
								iColLanguage = iColITALIENISCH;
								
							} else if ( strLang.equals("fr") ) { 
							
								iColLanguage = iColFRANZOESISCH;
								
							} else {
								continue;
							}
							
							if ( aLineInput.length >iColLanguage ) {
								
								strContent = (String) aLineInput[iColLanguage];
 								if (strContent != null
										&& strContent.trim().length() > 0) {
									
									SetLanguage(strLang);
									LOG.info("WeraVariante strLang=" + strLang /* + " - keyword=" + strContent */ );
									
									// --- Vorhandene Schlagwörter in der aktuellen
									// Sprache löschen
									Collection keywords = new ArrayList();
									keywords = (Collection) m_wm.getAttribute( oBaseProduct, "keywords");
									if (keywords != null && keywords.size() > 0) {
										_deleteAllKeyword(strLang, keywords);
									}
									
									// --- Neue Schlagworte anlegen
									aKeywords = strContent.split(",");
									_createKeywords(oBaseProduct, strLang, aKeywords);
									
								} // --- if (strContent != null...
								
							} // --- if ( aLineOutput.length >iColLanguage ) {

						} // --- for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext();) {
						SetLanguage("de");
						// --- Schleife über alle Sprachen, keywords löschen und neu anlegen -------------------------------------------------------------------

					} // --- if (aLineOutput != null && aLineOutput.length > 0) {
					else {

						// --- lösche alle keywords -----------------------------------------------------------------------
						for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext();) {

							String strLang = ((Language) itLanguages.next()).getIsoCode();
							SetLanguage(strLang);

							// --- Vorhandene Schlagwörter in der aktuellen
							// Sprache löschen
							Collection keywords = new ArrayList();
							keywords = (Collection) m_wm.getAttribute( oBaseProduct, "keywords");
							if ( keywords != null && keywords.size() > 0 ) {
								_deleteAllKeyword(strLang, keywords);
							}

						} // --- for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext();) {

						// --- reset language
						SetLanguage("de");
						// --- lösche alle keywords -----------------------------------------------------------------------

					}
					
				} // --- if ( oBaseProduct != null ) 

			}
			// --- Hole alle WeraVarianten ----------------------------------------------------------------


			// --- Hole alle WeraProductSet ----------------------------------------------------------------
			LOG.info("##");
			LOG.info("Sätze werden zugewiesen...");
			final ComposedType WeraProductSetType = JaloSession.getCurrentSession().getTypeManager()
					.getComposedType(WeraProductSet.class);
			final Set weraproductsets = WeraProductSetType.getAllInstances();
			WeraProductSet weraproductset = null;
			for (final Iterator it1 = weraproductsets.iterator(); it1.hasNext();)
			{
				// --- Get WeraVariante
				weraproductset = (WeraProductSet) it1.next();
				strCode = (String) wm.getAttribute(weraproductset, "artnr");

				// --- Prüfe, ob eine Datenzeile vorhanden ist
				strPK_Code = weraproductset.getPK().toString();
				final String[] aLineInput = (String[]) hProducts.get(strPK_Code);
				if (aLineInput != null && aLineInput.length > 0) {
					LOG.info("WeraProductSet.found PK="+strPK_Code  + ", strCode=" + strCode);
							
					// --- zählen
					iImportedKeywordsProductset++;
					
 					// --- Schleife über alle Sprachen, keywords löschen und neu anlegen -------------------------------------------------------------------
					for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext();) {
						String strLang = ((Language) itLanguages.next()).getIsoCode();
						if ( strLang.equals("de") ) {
							iColLanguage = iColDEUTSCH;

						} else if ( strLang.equals("en") ) { 

							iColLanguage = iColENGLISCH;

						} else if ( strLang.equals("dk") ) { 

							iColLanguage = iColDAENISCH;
								
						} else if ( strLang.equals("cs") ) { 

							iColLanguage = iColTSCHECHISCH;

						} else if ( strLang.equals("ru") ) { 
							
							iColLanguage = iColRUSSISCH;
								
						} else if ( strLang.equals("nl") ) { 
							
							iColLanguage = iColNIEDERLAENDISCH;
							
						} else if ( strLang.equals("pl") ) { 

							iColLanguage = iColPOLNISCH;

						} else if ( strLang.equals("es") ) { 

							iColLanguage = iColSPANISCH;
							
						} else if ( strLang.equals("it") ) { 

							iColLanguage = iColITALIENISCH;

						} else if ( strLang.equals("fr") ) { 

							iColLanguage = iColFRANZOESISCH;
 								
						} else {
							continue;
						}

						if ( aLineInput.length > iColLanguage ) {

							strContent = (String) aLineInput[iColLanguage];
							if (strContent != null
									&& strContent.trim().length() > 0) {

								SetLanguage(strLang);
								LOG.info("WeraProductSet strLang=" + strLang /* + " - keyword=" + strContent*/ );

								// --- Vorhandene Schlagwörter in der aktuellen
								// Sprache löschen
								Collection keywords = new ArrayList();
								keywords = (Collection) m_wm.getAttribute( weraproductset, "keywords");
								if (keywords != null && keywords.size() > 0) {
									_deleteAllKeyword(strLang, keywords);
								}

								// --- Neue Schlagworte anlegen
								aKeywords = strContent.split(",");
								_createKeywords( weraproductset, strLang, aKeywords);
 
							} // --- if (strContent != null...

						} // --- if ( aLineOutput.length >iColLanguage ) {

					} // --- for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext();) {
					SetLanguage("de");
					// --- Schleife über alle Sprachen, keywords löschen und neu anlegen -------------------------------------------------------------------
					
					

				} // --- if (aLineInput != null && aLineInput.length > 0) {
				else {
					
					// --- lösche alle keywords -----------------------------------------------------------------------
					for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext();) {
						
						String strLang = ((Language) itLanguages.next()).getIsoCode();
						SetLanguage(strLang);
						
						// --- Vorhandene Schlagwörter in der aktuellen
						// Sprache löschen
						Collection keywords = new ArrayList();
						keywords = (Collection) m_wm.getAttribute( weraproductset, "keywords");
						if ( keywords != null && keywords.size() > 0 ) {
							_deleteAllKeyword(strLang, keywords);
						}
					
					} // --- for (Iterator itLanguages = languages.iterator(); itLanguages.hasNext();) {
					
					// --- reset language
					SetLanguage("de");
					// --- lösche alle keywords -----------------------------------------------------------------------
					
				}
			}
			// --- Hole alle WeraProductSet ----------------------------------------------------------------

		}
		catch (final Exception e)
		{
			LOG.error("+Zeile=" + line);
			e.printStackTrace();
		}

		// --- Debug
		LOG.info("iImportedRows=" + iImportedRows);
		LOG.info("iImportedKeywordsVariante=" + iImportedKeywordsVariante);
		LOG.info("iImportedKeywordsProductset=" + iImportedKeywordsProductset);
		LOG.info("importKeywordCSV ( ende ) ");

	}

	// --- Ausgabe einer CSV-Datei aller Produkte / Artikel mit Preisen
	public String strExportKeywordCSV ( String strCatalogVersion ) {

		// --- Initialize
 		ArrayList aOutput = new ArrayList();
		String strProduktName = "";
		String strSortKey = "";
		int iAnzahlSollNettoPrices = 5;
		int iProductCounter = 0;
		String strFN = "strExportKeywordCSV ==> ";
		String strResult = "";
		String strOutput = "";
		String strLine = "";
		Collection productsSorted = null;
		Collection articles = null;
		String strDelimiter = "\t";
		String strPreisInfo = "";
		String strBesch = "";
		String strCode = "";
		String strEAN = "";
		String oPageNr = null;
		Boolean bNeu = null;
		String strCategorie = "";
		HashMap hashmapValues = new HashMap();

		// --- Initialize
		if ( m_strCatalogPrint == null || m_strCatalogPrint.length() == 0 )            m_strCatalogPrint      = "print";
		if ( m_strCatalogPriceliste == null || m_strCatalogPriceliste.length() == 0  ) m_strCatalogPriceliste = "preisliste";
		
		// --- Datum für Aushabe initialisieren
		String strFileDatum = InitOutputDatum();

		// --- Setze Sprache, und Defaultsprache=de
		SetLanguage("de");

		try {
			// --- Erzeuge die Export-Ablage
			String strPath       = Config.getParameter("wera.homepath") + "/export/katalog/" + strCatalogVersion.toLowerCase();
			String strOutputFile = strPath + "/keywords/" + strCatalogVersion.toLowerCase() + "_keywords.txt";
			System.out.println("mkdir()= " + m_wm.createDirectory ( strPath) );
			System.out.println("mkdir()= " + m_wm.createDirectory ( strPath + "/keywords") );

			// --- Schreibe Überschrift
			strLine = "PK" + strDelimiter + "ProduktNr";

			// --- Schleife über alle Sprachen
			Collection languages = C2LManager.getInstance().getAllLanguages();
			for (Iterator it1 = languages.iterator(); it1.hasNext();) {
				String strLang = ((Language) it1.next()).getIsoCode();
				strLine += strDelimiter + "name_" + strLang;
				strLine += strDelimiter + "keyword_" + strLang;
			}
			aOutput.add(strLine);

			// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
			//CatalogVersion weraCatalogVersion = m_wm.getCatalogVersion( "weracatalog", strCatalogVersion );

			// --- Alle alle Produkte sortiert nach Order
			WeraKatalog wk = new WeraKatalog();
			wk.InitCatalogPricelist ( m_strCatalogPriceliste );
			wk.InitCatalogPrint     ( m_strCatalogPrint );
			
			strOutput = "Hole sortierte Produktliste (de) ...";
			System.out.println(strFN + strOutput);
			strResult += strOutput + "<br>";
			productsSorted = wk.getProductsFromCatalogVersion ( m_strCatalogPrint, strCatalogVersion, 2  );
			
			// --- Schleife über alle Produkte
			strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
			System.out.println(strFN + strOutput);
			strResult += strOutput + "<br>";

			System.out.println("Looping over " + productsSorted.size() + " products.");
			int iProdCnt = 0;
			for (Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
				hashmapValues = (HashMap) it1.next();

				// --- Hole Produkt
				m_product = (Product) hashmapValues.get("product");

				// --- Hole OrderKey
				strSortKey = ((Integer) hashmapValues.get("sortkey0")).toString();

				if ( m_product instanceof WeraProduct || m_product instanceof WeraProductSet || m_product instanceof WeraProductSetinSet ) {

					// --- zeile zusammenbauen (suche alle keywords)
					strLine = __outputKeywordLine( (Product) m_product , languages);

					// --- Zeile schreiben
					if ( !strLine.equals("") ) {
						// --- zeile schreiben
						aOutput.add( strLine );

						// --- increment counter
						iProductCounter++;
					}
		
					// --- prüfe auf SB-Varianten -------------------------------------------------------------------------------------------
					Collection<WeraProductSetinSet> colSB_Variants = (Collection<WeraProductSetinSet>) m_wm.getAttribute(m_product, "weraproductsetinsets_relation");
					if (colSB_Variants.size() > 0) {

						LOG.info("initalizeSB_Variants oWeraHauptProduct.code=" + m_product.getCode());

						// --- iterate on all
						int iCounter = 1;
						for (WeraProductSetinSet oWeraProductSetinSet : colSB_Variants) {

							LOG.info("initalizeSB_Variants oWeraProductSetinSet.code=" + oWeraProductSetinSet.getCode());
							
							// --- zeile zusammenbauen (suche alle keywords)
							strLine = __outputKeywordLine( (Product)oWeraProductSetinSet , languages);

							// --- Zeile schreiben
							if ( !strLine.equals("") ) {
								// --- zeile schreiben
								aOutput.add( strLine );
								
								// --- increment counter
								iProductCounter++;
							}


						} // --- for ( WeraProductSetInSet oWeraProductSetInSet : colSB_Variants ) {

					} // --- if ( colSB_Variants.size() > 0 ) {
					// --- prüfe auf SB-Varianten -------------------------------------------------------------------------------------------
					
				} // --- if ( m_product instanceof WeraProduct || m_product instanceof WeraProductSet || m_product instanceof WeraProductSetinSet ) {
				
			} // --- for (Iterator it1 = productsSorted.iterator(); it1.hasNext();) {


			
			
			// --- Schliessen
			strOutput = "Export abgeschlossen. Anzahl Produkte=" + iProductCounter;
			System.out.println(strResult);
			System.out.println(strFN + strOutput);
			strResult += strOutput + "<br>";
			XmlSupport oXmlSupport = new XmlSupport();
			oXmlSupport._WriteFileFromArrayEncoding(aOutput, strOutputFile, "UTF8");

		} catch (JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Aufräumen
		// cleanUp();

		return strResult;
	}
	
	/**
	 * 
	 * @param weraProduct
	 * @param languages 
	 */
	private String __outputKeywordLine( Product weraProduct, Collection languages) {
		
		// --- initialize
		String strProduktName	= "";
		String strLine			=	"";
		String strDelimiter		= "\t";
		
		// --- Debug
		System.out.println("++Code=" + weraProduct.getCode());

		// --- Schreiben der Artikeldaten
		strLine = weraProduct.getPK() + strDelimiter + weraProduct.getCode();

		// --- Holen der Keywords (für alle Sprachen)
		for (Iterator it2 = languages.iterator(); it2.hasNext();) {
			String strLang = ((Language) it2.next()).getIsoCode();
			SetLanguage(strLang);
			strProduktName = weraProduct.getName();
			if (strProduktName == null)
				strProduktName = "";
			strLine += strDelimiter + strProduktName;
			strLine += strDelimiter + _initKeywords(weraProduct, strLang);
		}
		
		return strLine;
	}

	public String _initKeywords(Product product, String strLanguage) {

		// --- Initialize
		String strResult = "";

		Collection keywords = new ArrayList();
		strResult = "";
		keywords = (Collection) m_wm.getAttribute(product, "keywords");
		for (Iterator it2 = keywords.iterator(); it2.hasNext();) {
			Keyword keyword = (Keyword) it2.next();
			strResult += keyword.getKeyword() + ", ";
		}
		if (strResult.length() > 0)
			strResult = strResult.substring(0, strResult.length() - 2);

		return strResult;
	}

	public void resetProducts(String strLanguage) {

		// --- Setzen der Sprache
		SetLanguage(strLanguage);

		// --- Zurücksetzen der Daten
		// --- Zurücksetzen des Flags für
		Set setProductSets = WeraProduct.getAllInstances();
		WeraProduct productset = null;
		for (Iterator it2 = setProductSets.iterator(); it2.hasNext();) {
			productset = (WeraProduct) it2.next();
			productset.setLocalizedProperty("ausgabe_preisliste", new Boolean(
					false));
			WeraManager.getInstance().setAttribute(productset, "orderPL",
					new Integer(0));
			System.out.println("Reset WP=>" + productset.getCode());
		}

		setProductSets = WeraProductSet.getAllInstances();
		productset = null;
		for (Iterator it2 = setProductSets.iterator(); it2.hasNext();) {
			productset = (WeraProductSet) it2.next();
			productset.setLocalizedProperty("ausgabe_preisliste", new Boolean(
					false));
			WeraManager.getInstance().setAttribute(productset, "orderPL",
					new Integer(0));
			System.out.println("Reset WPS=>" + productset.getCode());
		}

		Set setVariantSets = WeraVariante.getAllInstances();
		WeraVariante variantset = null;
		for (Iterator it2 = setVariantSets.iterator(); it2.hasNext();) {
			variantset = (WeraVariante) it2.next();
			variantset.setLocalizedProperty("ausgabe_preisliste", new Boolean(
					false));
			System.out.println("Reset WV=>" + variantset.getCode());
		}

	}

	// --- Einlesen der XML-Datei
	private HashMap _readInputFile(HttpServletRequest request, String strEncoding ) {

		System.out.println ( "++_readInputFile =" + request);
		// --- Initialze
		String strFileName = "";
		HashMap ReqParam = new HashMap();

		try {
			// --- MultiForm-Request einlesen
			MultiPartFormData mpfa = new MultiPartFormData(request);
			ReqParam    = mpfa.getParameters();
			System.out.println ( "++_readInputFile.ReqParam.get(fileName) =" + ReqParam.get("fileName"));
			
			// --- Upload-Datei schreiben
			strFileName = Config.getParameter("wera.homepath") + "/tmp/" + ReqParam.get("fileName");
			ReqParam.put("strFileName", strFileName);
			FileOutputStream baos = new FileOutputStream(strFileName);
			//OutputStreamWriter baos = new OutputStreamWriter (new FileOutputStream(strFileName),strEncoding);
			baos.write(mpfa.getFile(), 0, mpfa.getFile().length);
			baos.flush();
			baos.close();
			
			// --- Debug
			System.out.println("Import File=" + ReqParam.get("fileName"));

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ReqParam;
	}

	public String importKeywordsXML(HttpServletRequest request) {

		// --- Einlesen der XML-Datei
		HashMap hashReqParam = _readInputFile(request, "UTF-8");
		
		System.out.println("+++start importKeywordsXML" );
		
		// --- Initialize
		String strResult         = "";
		String sFileName         = (String) hashReqParam.get("strFileName");
		String strCatalogVersion = (String) hashReqParam.get("catalogversion");

		// --- Debug
		System.out.println("++importKeywordsXML");
		System.out.println("sFileName=" + sFileName);
		System.out.println("strCatalogVersion=" + strCatalogVersion);

		// --- Impotieren der XMl-Daten
		DataImport dataimport = new DataImport();
		boolean bError = dataimport.bImportAllData ( sFileName );
		if ( bError ) {
			return dataimport.getStringError();
		}
		DataImportExt ContentHandlerExt = dataimport.getContentHandler();
		
		// --- Prüfen ob Ok, dann übernahme der Preise nach Hybris
		HashMap hashMap = null;
		String strKey = "";

		String strImagePath = Config.getParameter("wera.importpath");
		FileWriter import_log = null;
		try {
			import_log = new FileWriter(strImagePath + "importKeywordsXML.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// --- ...otherwise handle normal import
		//final int iOrderStepWidth = 10;
		// boolean bNet = ! sFileName.equals("wera_de_preis_brutto.csv");
		//boolean bIsEVK = false;
		//boolean bNet = false;
		//boolean bIsNewItem = false;
		//int iNumColumns = ContentHandlerExt.m_iMaxCol;
		//int iNumPriceColumns = ((iNumColumns - 13) / 4);
		//System.out.println("iNumPriceColumns=" + iNumPriceColumns);
		//System.out.println("ContentHandlerExt.m_iMaxCol="	+ ContentHandlerExt.m_iMaxCol);
		//int iHasEVK = 0;
		//final String sOrderOfEVK = "1000";

		//CatalogManager catalogManager = CatalogManager.getInstance();
		//ClassificationSystem weraCatalog = catalogManager.getClassificationSystem(Config.getParameter("wera.mastercatalog"));
		//m_weraCatalogVersion = (ClassificationSystemVersion) weraCatalog.getCatalogVersion(Config.getParameter("wera.mastercatalogversion"));

		// --- All data read. Now, iterate over each row (=variant/set)
		Collection languages = C2LManager.getInstance().getAllLanguages();
		String strContent = "";
		String strKeyword = "";
		String aKeywords[] = null;
		String strTmp = "";
		Set setDuplicateFinder = new HashSet();
		Map articleData = null;
		String msg = "";
		String sCode = "";
		String sProduktNr = ""; // CSV Productcode
		String sIsNewItem = "";
		String sEAN = "";
		String sLanguage = "de";
		String sWaehrung = "";
		String sPreislistenausgabe = "";
		String sVPE = "";
		String sLFDNR_PL = "";
		String sSeite = "";
		String strField = "";
		boolean bReset = true;
		System.out.println("Anzahl=" + ContentHandlerExt.m_aArrayList.size());

		// --- Reihgenfolge setzen
		System.out.println("Keyworte werden gesetzt...");
		System.out.println("Anzahl:" + ContentHandlerExt.m_aArrayList.size() );
		for (Iterator itmap1 = ContentHandlerExt.m_aArrayList.iterator(); itmap1
				.hasNext();) {
			SetLanguage("de");
			articleData = (Map) itmap1.next();
			// sLFDNR_PL = ((String) articleData.get("lfd_Nr")).trim();
			sProduktNr = ((String) articleData.get("ProduktNr")).trim(); // CSV

			// Collection products = weraCatalogVersion.getProducts(sArtikelNr);
			Product product = null;
			Integer iOrderPL = null;
			boolean bPreislistenAusgabe = false;
			String strPK = (String) articleData.get("PK");
			product = (Product) JaloSession.getCurrentSession().getItem(strPK);
			if (product != null) {
				System.out.println("++CODE=" + product.getCode());

				// --- Schleife über alle Sprachen
				for (Iterator it1 = languages.iterator(); it1.hasNext();) {
					String strLang = ((Language) it1.next()).getIsoCode();
					strField = "keyword_" + strLang;
					System.out.println("strKey=" + strField );
					if (articleData.containsKey(strField)) {
						strContent = (String) articleData.get(strField);
						System.out.println("strKey=" + strField + "/ strField="
								+ articleData.get(strField) + "=");
						if (strContent != null
								&& strContent.trim().length() > 0) {
							SetLanguage(strLang);

							// --- Vorhandene Schlagwörter in der aktuellen
							// Sprache löschen
							Collection keywords = new ArrayList();
							keywords = (Collection) m_wm.getAttribute(product,
									"keywords");
							if (keywords != null && keywords.size() > 0)
								_deleteAllKeyword(strLang, keywords);

							// --- Neue Schlagworte anlegen
							//aKeywords = strContent.split("/");
							aKeywords = strContent.split(",");
							_createKeywords(product, strLang, aKeywords);
						}

					} // --- if ( articleData.containsKey(strField) ) {
					else {
						System.out.println("Sprache " + strLang + " nicht gefunden.");
						
					}
					
				} // --- for ( Iterator
					// it1=languages.iterator();it1.hasNext();) {

			} else
				System.out.println("Produkt == null");
		}

		// --- Sprache zurücksetzen
		SetLanguage("de");

		return strResult;
	}
/*
	public String importVarNrXML(HttpServletRequest request)  {

		// --- Einlesen der XML-Datei
		HashMap hashReqParam = _readInputFile(request,"UTF-8");

		// --- Initialize
		System.out.println ( "starte importVarNrXML..." );
		String strResult = "";
		String sFileName = (String) hashReqParam.get("strFileName");
		String strCatalogVersion = (String) hashReqParam.get("catalogversion");

		// --- Debug
		System.out.println("++importVarNrXML");
		System.out.println("sFileName=" + sFileName);
		System.out.println("strCatalogVersion=" + strCatalogVersion);
		return "";
	}
*/	
	public String importVarNrXML(HttpServletRequest request)  {

		// --- Einlesen der XML-Datei
		HashMap hashReqParam = _readInputFile(request,"UTF-8");

		// --- Initialize
		System.out.println ( "starte importVarNrXML..." );
		String strResult         = "";
		String sFileName         = (String) hashReqParam.get("strFileName");
		String strCatalogVersion = (String) hashReqParam.get("catalogversion");

		// --- Debug
		System.out.println("++importVarNrXML");
		System.out.println("sFileName=" + sFileName);
		System.out.println("strCatalogVersion=" + strCatalogVersion);

		// --- Impotieren der XMl-Daten
		DataImport dataimport = new DataImport();
		boolean bError = dataimport.bImportAllData ( sFileName );
		if ( bError ) {
			return dataimport.getStringError();
		}
		DataImportExt ContentHandlerExt = dataimport.getContentHandler();
		
		// --- Prüfen ob Ok, dann übernahme der Preise nach Hybris
		HashMap hashMap = null;
		String strKey = "";

		String strImagePath = Config.getParameter("wera.importpath");
		FileWriter import_log = null;
		try {
			import_log = new FileWriter(strImagePath + "importVarNrXML.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// --- All data read. Now, iterate over each row (=variant/set)
		Collection languages = C2LManager.getInstance().getAllLanguages();
		ArrayList aLang = new ArrayList();
		aLang.add("us-en");
		aLang.add("us-es");
		aLang.add("us-fr");
		String strLang = "";
		String strContent = "";
		String strKeyword = "";
		String aKeywords[] = null;
		String strTmp = "";
		Set setDuplicateFinder = new HashSet();
		Map articleData = null;
		String msg = "";
		String sCode = "";
		String sProduktNr = ""; // CSV Productcode
		String sLanguage = "de";
		String strField = "";
		boolean bReset = true;
		Boolean bAktiv = new Boolean(true);
		Boolean bPassiv = new Boolean(false);
		System.out.println("Anzahl=" + ContentHandlerExt.m_aArrayList.size());

		// --- Reihgenfolge setzen
		System.out.println("VariantenNr werden gesetzt...");
		for (Iterator itmap1 = ContentHandlerExt.m_aArrayList.iterator(); itmap1
				.hasNext();) {
			SetLanguage("de");
			articleData = (Map) itmap1.next();
			// sLFDNR_PL = ((String) articleData.get("lfd_Nr")).trim();
			// sProduktNr = ((String) articleData.get("ProduktNr")).trim(); //
			// CSV
			try {

				// Collection products =
				// weraCatalogVersion.getProducts(sArtikelNr);
				Product product = null;
				Integer iOrderPL = null;
				boolean bPreislistenAusgabe = false;
				String strPK = (String) articleData.get("PK");
				product = (Product) JaloSession.getCurrentSession().getItem(
						strPK);
				if (product != null) {
					System.out.println("++CODE=" + product.getCode());
					import_log.write("\r\n-------------\r\n++CODE=" + product.getCode() );
					import_log.write("\r\nArtikelNr=" + (String) articleData.get("ArtikelNr") );

					strField = "VarNR_us";
					if (articleData.containsKey(strField))
						strContent = (String) articleData.get(strField);
					else
					   strContent = "";
					
					System.out.println("strKey=" + strField + "/ strField="
								+ articleData.get(strField) + "=");
						
					// --- VariantenNr setzen
					strContent = strContent.replaceAll("'", "");
					if (product instanceof WeraProductSet 
								|| product instanceof WeraVariante
								|| product instanceof WeraVarianteSet ) {
							
							// --- Schleife über alle Sprachen
							for ( Iterator itLang=aLang.iterator(); itLang.hasNext(); ) {
								
								// --- Sprache setzen
								strLang = (String)itLang.next();
								SetLanguage(strLang);
								
								// --- VariantenNr setzen
								setAttribute(product, "variantenNr", strContent);
								import_log.write("\r\n++VAR-NR ("+ strLang + ")=" + strContent );
								
								// --- Aktiv / Passiv setzen
								if (strContent.trim().length() == 0 ) {
									setAttribute(product, "aktiv", bPassiv);
									import_log.write("= - change to passiv");
								} else {
									setAttribute(product, "aktiv", bAktiv);
									import_log.write("= - change to aktiv");
								}
							} // --- for ( Iterator itLang=aLang.iterator(); itLang.hasNext(); ) {
							
					}
					else
						import_log.write("PK was WeraProduct!!!");

				} else {
					System.out.println("Produkt == null");
				    import_log.write("\r\n-------------\r\n++ NOT FOUND strPK=" + strPK + "=" );
				}
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		// --- LOG schliessen
		try {
			import_log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// --- Sprache zurücksetzen
		SetLanguage("de");

		System.out.println("importVarNrXML.READY...");
		
		return strResult;
	}

	// --- Neues Schlagworte anlegen
	public void _createKeywords(Product product, String strLanguage,
			String aKeywords[]) {

		// --- Initialize
		WeraManager wm = new WeraManager();
		Language oLang = C2LManager.getInstance().getLanguageByIsoCode(
				strLanguage);
		Collection keywords = new ArrayList();
		// Collection keywordsTmp = (Collection)
		// wm.getAttribute(product,"keywords");
		// if ( keywordsTmp != null ) keywords.addAll(keywordsTmp);

		// --- Schleife über Schlagworte
		String strKeyword = "";
		Keyword okeywordNew = null;
		for (int iCnt = 0; iCnt < aKeywords.length; iCnt++) {
			strKeyword = aKeywords[iCnt].trim();

			// --- Neues Schlagwort anlegen
			okeywordNew = _createKeyword(oLang, strKeyword, product);
			keywords.add(okeywordNew);
		}
		wm.setAttribute(product, "keywords", keywords);
	}

	// --- Neues Schlagwort anlegen
	public Keyword _createKeyword(Language oLang, String strKeyword,
			Product product) {

		// --- Initialize
		Keyword okeywordNew = null;
		ComposedType KeywordType = JaloSession.getCurrentSession()
				.getTypeManager().getComposedType(Keyword.class);

		// --- Anlegen einer neuen Instance des Keywords in strOutputLanguage
		// new Instance
		HashMap mKeyword = new HashMap();
		mKeyword.put("keyword", strKeyword);
		mKeyword.put("language", oLang);
		mKeyword.put("catalogVersion", m_weraCatalogVersion);
		Collection products = new ArrayList();
		products.add(product);
		try {
			okeywordNew = (Keyword) KeywordType.newInstance(mKeyword);
			okeywordNew.setProducts(products);
		} catch (JaloGenericCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JaloAbstractTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return okeywordNew;

	}

	public void _deleteAllKeyword(String strOutputLanguage, Collection keywords) {

		// --- Initialize
		System.out.println("Schlagworte in " + strOutputLanguage
				+ " werden gelöscht.");
		WeraManager wm = new WeraManager();
		Language oLang = C2LManager.getInstance().getLanguageByIsoCode(
				strOutputLanguage);

		// --- Schleife über alle Keywords
		Keyword keyword = null;
		for (Iterator it1 = keywords.iterator(); it1.hasNext();) {
			// --- Get Keyword
			keyword = (Keyword) it1.next();

			// --- Prüfe die Sprache
			try {
				keyword.remove();
			} catch (ConsistencyCheckException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private Collection _getValidVariantOrSetForCodes(
			ClassificationSystemVersion weraCatalogVersion, String sArtikelNr,
			String sCode, Set setDuplicateFinder, FileWriter import_log,
			String sLFDNR_PL) {
		String msg = "";
		Collection pList = new ArrayList();
		Product p = null;
		WeraProduct baseProduct = null;
		String sCodeOfBaseProduct = null;

		// --- 1st examine alle variants for the code, and check if one of them
		// matches
		// --- the given base product code. If so, we assume all variants should
		// be assigned the price.
		boolean bFoundMatchingVariant = false;
		boolean bFoundMismatchVariant = false;
		Collection colProducts = weraCatalogVersion.getProducts(sCode);
		for (Iterator itprod = colProducts.iterator(); itprod.hasNext();) {
			p = (Product) itprod.next();
			if (p instanceof WeraVariante) {
				try {
					baseProduct = (WeraProduct) p.getAttribute("baseproduct");
				} catch (JaloInvalidParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (JaloSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (baseProduct != null) {
					sCodeOfBaseProduct = baseProduct.getCode();
					bFoundMatchingVariant = true;
					if (sCodeOfBaseProduct.equals(sArtikelNr)) {
						bFoundMatchingVariant = true;
					} else {
						msg += "WARNING: Found hybris baseproduct with code "
								+ sCodeOfBaseProduct
								+ ", but import data demands " + sArtikelNr
								+ ".\n";
						bFoundMismatchVariant = true;
					}
					pList.add(p);
					if (setDuplicateFinder.add(p.getCode()) == false) {
						msg += "WARNING: Product with code " + p.getCode()
								+ " already added.";
					}

					/*
					 * // --- Reihenfolge Preisliste if (
					 * bFoundMismatchVariant==false && (sLFDNR_PL != null &&
					 * sLFDNR_PL.length()>0) ) { Integer iOrderPL = (Integer)
					 * WeraManager.getInstance().getAttribute(baseProduct,"orderPL");
					 * if ( iOrderPL.intValue() == 0 )
					 * WeraManager.getInstance().setAttribute(baseProduct,"orderPL",new
					 * Integer(Integer.parseInt(sLFDNR_PL))); }
					 */
				} else {
					msg += "WARNING: found no baseProduct for product "
							+ p.getCode() + " - probably variant zombie!\n";
					bFoundMismatchVariant = true;
				}

			}

		}
		// --- No variant in the system has the baseproduct code given by CSV
		// --- We dont want to risk ambiguities with sets, so we assume this is
		// NOT a variant
		if (bFoundMatchingVariant == false) {
			pList.clear();
			msg += "WARNING: No matching hybris baseproduct found for any variant with code "
					+ sCode + ".\n";
		} else {
			if (bFoundMismatchVariant) {
				msg += "INFO: Matching variant for code "
						+ sCode
						+ " found instead of zombie/variant with mismatched base.\n\n";
			}
		}

		// --- If we didnt find a variant, we try finding a matching set...
		if (pList.isEmpty()) {

			boolean bIsSet = false;

			WeraProductSet fnResult = null;
			Map value = new HashMap();
			value.put("artnr", sCode);
			//
			SearchResult res = JaloSession.getCurrentSession()
					.getFlexibleSearch().search(
							"select {" + Item.PK
									+ "} from {WeraProductSet} where {"
									+ WeraProductSet.ARTNR + "}=?artnr", value,
							Collections.singletonList(WeraProductSet.class),
							true, // fail on unknown fields
							true, // don't need total
							0, 1 // range
					);

			if (res.getCount() > 0) {
				System.out.println("++Found " + res.getCount() + " for "
						+ sCode);
				WeraProductSet oProductSet = null;
				int iPos = 0;
				while (iPos < res.getCount()) {
					fnResult = (WeraProductSet) res.getResult().get(0);
					if (fnResult.getLocalizedProperty("artnr").equals(sCode)) {
						oProductSet = fnResult;
						break;
					}
					iPos++;
				}

				if (oProductSet != null) {
					/*
					 * // --- Reihenfolge Preisliste if ( sLFDNR_PL != null &&
					 * sLFDNR_PL.length()>0 ) { Integer iOrderPL = (Integer)
					 * WeraManager.getInstance().getAttribute(oProductSet,"orderPL");
					 * if ( iOrderPL.intValue() == 0 )
					 * WeraManager.getInstance().setAttribute(oProductSet,"orderPL",new
					 * Integer(Integer.parseInt(sLFDNR_PL))); }
					 */
					pList.add(oProductSet);
					if (setDuplicateFinder.add(sCode) == false) {
						msg += "WARNING: ProductSet with artnr " + sArtikelNr
								+ "/code " + oProductSet.getCode()
								+ " already added.";
					}
				}
			}

			/*
			 * Collection colSets = weraCatalogVersion.getProducts(sArtikelNr);
			 * int iCntCol = 0; for ( Iterator itset = colSets.iterator();
			 * itset.hasNext();) { p = (Product) itset.next(); iCntCol++; if ( p
			 * instanceof WeraProductSet ) { WeraProductSet wset =
			 * (WeraProductSet) p;
			 * 
			 * String sSetArtikelNr = (String)
			 * wset.getLocalizedProperty("artnr"); if ( sSetArtikelNr == null ) {
			 * sSetArtikelNr = "(null)"; }
			 * 
			 * if ( sSetArtikelNr.equals(sCode) ) { pList.add(wset); msg +=
			 * "INFO: Code " + sCode + " successfully resolved for set " +
			 * sArtikelNr + "(Artnr:" + sSetArtikelNr + ")\n\n"; } else { msg +=
			 * "WARNING: Found hybris Set with code/artnr = " + sArtikelNr + "/" +
			 * sSetArtikelNr + ", but import data demands artno=" + sCode +
			 * ".\n"; }
			 * 
			 * if ( setDuplicateFinder.add( p.getCode() ) == false ) { msg +=
			 * "WARNING: Product with code " + p.getCode() + " already added."; } //
			 * sAttributeForNeuProperty = "produkt_neu";
			 * sAttributeForNeuProperty = "artikel_neu"; } }
			 */
			if (pList.isEmpty()) {
				msg += "ERROR: Product " + sCode
						+ " not in WeraCatalog - skipping...\n\n";
			}
		}

		// --- Output warning messages on stdout and logfile.
		if (msg.length() > 0) {
			System.out.println(msg);
			try {
				import_log.write(msg + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return pList;
	}


public class MultiPartFormData {

	private HashMap parameters = new HashMap();
	private byte[] fileBytes, bytes;
	private final int mbLimit = 10;
	private final int FILE_SIZE_LIMIT = 1024*1024*mbLimit;
	private String data;

	public MultiPartFormData(HttpServletRequest request) throws IOException {
		
		int contentLength = request.getContentLength();
	    System.out.println("MultiPartFormData.contentLength=" + contentLength);
		
		if(contentLength > FILE_SIZE_LIMIT)
			throw new IOException("File has exceeded size limit.");
		
		ServletInputStream in = request.getInputStream();
		
		bytes = new byte[contentLength];
		byte[] tempByte = new byte[1];
		int paramCount = 0;
		int paramLineCount = 0;	
		int byteCount = 0;
		
		while(in.read(tempByte) > -1) {
			
			bytes[byteCount] = tempByte[0];
			byteCount++;
		}

		String data = new String(bytes, "ISO-8859-1");
		this.data = data;
		String boundary = data.substring(0,data.indexOf('\n'));
		String[] elements = data.split(boundary);

		for(int i = 0; i < elements.length; i++) {

			if(elements[i].length() > 0) {

				String[] descval = elements[i].split("\n");
				
				// take the first line of this element and split it by ";"
				String[] disp = descval[1].split(";");

				// if there's a filename, it's a file				
				System.out.println("descval[1]=" + descval[1]);
				System.out.println("descval[2]=" + descval[2]);
				if(disp.length > 2) {

						String longFileName = disp[2].substring(
							disp[2].indexOf('"')+1,disp[2].length()-2).trim();
						parameters.put("longFileName",longFileName);
						parameters.put("fileName",longFileName.substring(
							longFileName.lastIndexOf("\\")+1,
							longFileName.length()));
						parameters.put("contentType",descval[2].substring(
							descval[2].indexOf(' ')+1,
							descval[2].length()-1));
	
						int pos = 0;
						int lineCount = 0;
						//paramLineCount--;
						while(lineCount != paramLineCount+3) {
						//while(lineCount <  disp.length ) {
	
							System.out.print((char)bytes[pos]);
							if((char)bytes[pos] == '\n') lineCount++;
							pos++;
						}
						//paramLineCount += lineCount;
						System.out.println("disp.length=" + disp.length);
						System.out.println("paramLineCount=" + paramLineCount);
						System.out.println("LineCount=" + lineCount);
						System.out.println("1.pos=" + pos);
	
						fileBytes = new byte[bytes.length - boundary.length() - 4 - pos];
						int fileByteCount = 0;
						
						for(int k = pos; k < (bytes.length - boundary.length() - 4); k++) {
							
							fileBytes[fileByteCount] = bytes[k];
							fileByteCount++;
						}	
						//System.out.println("pos="+pos);
						//System.out.println("boundary.length()="+boundary.length());
						//System.out.println("fileByteCount="+fileByteCount);
						//System.out.println("contentLength="+fileBytes.length);
						//System.out.println("paramLineCount="+paramLineCount);
	
				} else {
					
					paramCount++;
					paramLineCount += 4;
					
					// loop for multi-line params
					String value = "";
					for(int p = 3; p < descval.length; p++) {
						
						if(p != 3) value += "\n";
						value += descval[p].trim();
						paramLineCount++;
					}
					
					parameters.put(
						descval[1].substring(
							descval[1].indexOf('"')+1,
							descval[1].length()-2).trim(),
						value
					);
				}
			}
		}
		
		bytes = null;
		System.gc();
	}

	public byte[] getFile() { return fileBytes; }
	public HashMap getParameters() { return parameters; }
}

/*
	public class MultiPartFormData {

		private HashMap parameters = new HashMap();

		private byte[] fileBytes, bytes;

		private final int mbLimit = 10;

		private final int FILE_SIZE_LIMIT = 1024 * 1024 * mbLimit;

		private String data;

		public MultiPartFormData(HttpServletRequest request) throws IOException {

			int contentLength = request.getContentLength();

			if (contentLength > FILE_SIZE_LIMIT)
				throw new IOException("File has exceeded size limit.");

			ServletInputStream in = request.getInputStream();

			bytes = new byte[contentLength];
			byte[] tempByte = new byte[1];
			int paramCount = 0;
			int paramLineCount = 0;
			int byteCount = 0;

			while (in.read(tempByte) > -1) {

				bytes[byteCount] = tempByte[0];
				byteCount++;
			}

			String data = new String(bytes, "ISO-8859-1");
			this.data = data;
			String boundary = data.substring(0, data.indexOf('\n'));
			String[] elements = data.split(boundary);

			for (int i = 0; i < elements.length; i++) {

				if (elements[i].length() > 0) {

					String[] descval = elements[i].split("\n");

					// take the first line of this element and split it by ";"
					String[] disp = descval[1].split(";");

					// if there's a filename, it's a file
					// System.out.println("descval=" + descval);
					// for ( int ival=0;ival < descval.length; ival++){
					// System.out.println("descval="+descval[ival]);
					// }
					if (disp.length > 2) {
						//System.out.println("o.disp[2]=" + disp[2] + "=");
						//System.out.println("o.descval=" + descval + "=");
						//System.out.println("o.descval[1]=" + descval[1] + "=");
						//System.out.println("o.descval[2]=" + descval[2] + "=");

						String longFileName = disp[2].substring(
								disp[2].indexOf('"') + 1, disp[2].length() - 2)
								.trim();
						parameters.put("longFileName", longFileName);
						parameters.put("fileName", longFileName.substring(
								longFileName.lastIndexOf("\\") + 1,
								longFileName.length()));
						parameters.put("contentType", descval[2].substring(
								descval[2].indexOf(' ') + 1, descval[2]
										.length() - 1));

						int pos = 0;
						int lineCount = 0;
						paramLineCount--;
						while (lineCount != paramLineCount) {

							if ((char) bytes[pos] == '\n')
								lineCount++;
							pos++;
						}
						//System.out.println("LineCount=" + lineCount);
						//System.out.println("1.pos=" + pos);
						fileBytes = new byte[bytes.length - boundary.length()
								- 4 - pos];
						int fileByteCount = 0;

						for (int k = pos; k < (bytes.length - boundary.length() - 4); k++) {

							fileBytes[fileByteCount] = bytes[k];
							fileByteCount++;
						}
						//System.out.println("2.pos=" + pos);
						//System.out.println("contentType="+ parameters.get("contentType"));
						//System.out.println("boundary.length()="+ boundary.length());
						//System.out.println("fileByteCount=" + fileByteCount);
						//System.out.println("contentLength=" + fileBytes.length);
						//System.out.println("paramLineCount=" + paramLineCount);

					} else {

						paramCount++;
						paramLineCount += 4;

						//System.out.println("u.disp[1]=" + disp[1]);
						//System.out.println("u.descval[1]=" + descval[1]);

						// loop for multi-line params
						String value = "";
						for (int p = 3; p < descval.length; p++) {

							if (p != 3)
								value += "\n";
							value += descval[p].trim();
							paramLineCount++;
						}

						parameters.put(descval[1].substring(
								descval[1].indexOf('"') + 1,
								descval[1].length() - 2).trim(), value);
					}
				}
			}

			bytes = null;
			System.gc();
		}

		public byte[] getFile() {
			return fileBytes;
		}

		public HashMap getParameters() {
			return parameters;
		}
	}
*/
}

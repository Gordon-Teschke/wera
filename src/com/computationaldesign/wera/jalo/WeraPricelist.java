package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.util.Config;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.europe1.enums.UserPriceGroup;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import org.apache.log4j.Logger;
import org.jdom.Attribute;

import org.jdom.Comment;
import org.jdom.Element;

//Wrapperklassen f�r formatierte Ausgabe der Bildpfade
//Standardklasse ist ExportFormatter f�r den XML Export f�r die Katalogausgabe
//Sie liefert stets den Eingabestring unver�ndert zur�ck
public class WeraPricelist extends MediandoXml {

	/**
	 * Edit the local|project.properties to change logging behavior (properties
	 * 'log4j.*').
	 */
	private static final Logger LOG = Logger.getLogger(WeraPricelist.class.getName());

	public static final String logisticZolltarifnummer = "Zolltarifnummer";
	public static final String logisticUrsprungsland = "Ursprungsland";
	public static final String logisticPackmassGewichtseinheit = "PackmassGewichtseinheit";
	public static final String logisticPackmassLaengeeinheit = "PackmassLaengeeinheit";
	public static final String logisticPackmassLaenge = "PackmassLaenge";
	public static final String logisticPackmassHoehe = "PackmassHoehe";
	public static final String logisticPackmassBreite = "PackmassBreite";
	public static final String logisticArtikelLaenge = "ArtikelLaenge";
	public static final String logisticArtikelHoehe = "ArtikelHoehe";
	public static final String logisticArtikelBreite = "ArtikelBreite";
	public static final String logisticGewichtProStueck = "GewichtProStueck";
	public static final String logisticGewichtVPE = "GewichtVPE";
	public static final String logisticGewichteinheit = "Gewichteinheit";
	public static final String logisticPackmassHoeheEinheit = "PackmassHoeheEinheit";
	public static final String logisticPackmassBreiteEinheit = "PackmassBreiteEinheit";
	public static final String logisticLaengeEeinheitArtikel = "LaengeEeinheitArtikel";
	public static final String logisticHoeheEinheitArtikel = "HoeheEinheitArtikel";
	public static final String logisticBreiteEinheitArtikel = "BreiteEinheitArtikel";
	public static final String logisticPackmittelVolumen = "PackmittelVolumen";
	public static final String logisticPackmittelVolumeneinheit = "PackmittelVolumeneinheit";
	public static final String logisticBruttoGewicht = "BruttoGewicht";
	public static final String logisticBruttoGewichtseinheit = "BruttoGewichtseinheit";

	
	// --- Seitenzahlen aus Hybris = false, Seitenzahlen per Paltzhalter=true
	boolean m_platzhalterSeitennummer	= false; // <<<<=== Hier setzen
	boolean m_keineSeitennummern	= true; // <<<<=== Hier setzen



	// --- Member	
	WeraClassificationHelper m_weraclassificationhelper = null;
	CatalogVersion weraCatalogVersion = null;
	CatalogVersion m_weraRefCatalogVersion = null;
	ExportFormatterPL m_ExportFormatterPL = null;
	String m_strControlOSPath = "";
	String m_strDataPath = "";
	String m_strControlPath = "";
	String m_strXML_File = "";
	String m_strPriceList = "";
	public String m_strResultFile = "";
	String m_strImageTransferPath = "";
	String m_strDatenTransferPath = "";
	String m_strOutputPath = "";
	String m_strOutputFile = "";
	HashMap m_hashImages = null;
	String m_strDatum = "";
	ArrayList m_aArrayLog = null;
	String m_strCatalogversion = "";
	private String m_strProduktID = "";
	Product m_product = null;
	Product m_article = null;
	String m_strExportLanguage = "de";
	String m_strWaehrung = "EUR";
	String m_strProduktName = "";
	String m_strProduktNameEN = "";
	String m_strStringNew = "";
	ArrayList m_aLogDatei = new ArrayList();
	ArrayList m_aLogDatei1 = new ArrayList();
	Object m_oPageNr = null;
	char m_cTemplateInfo = '5';
	private String strTypeName;
	int m_iMaxPrices = -1;
	public String m_strResultPath = "";
	Boolean m_bIstDisplay = null;
	String m_strCurrentTemplateName = "";
	// private Collection<Language> m_languages	= null;
	private Collection<String> m_pricelists		= null;
	private Collection<String> m_kontrollCSV	= null;

	// --- WeraManager
	WeraManager m_wm = WeraManager.getInstance();

	// --- Default Kataloge
	String m_strCatalogPriceliste = "preisliste";
	String m_strCatalogPrint = "print";
	String m_strCatalogMaster = Config.getParameter("wera.mastercatalog");

	/**
	 * Initialize Catalogs
	 * @param strCatalogPriceliste 
	 */
	public void InitCatalogPricelist(final String strCatalogPriceliste) {
		m_strCatalogPriceliste = strCatalogPriceliste;
	}

	/**
	 * 
	 * @param strCatalogPrint 
	 */
	public void InitCatalogPrint(final String strCatalogPrint) {
		m_strCatalogPrint = strCatalogPrint;
	}

	/**
	 * 
	 * @param strCatalogMaster 
	 */
	public void InitCatalogMaster(final String strCatalogMaster) {
		m_strCatalogMaster = strCatalogMaster;
	}

	/**
	 * 
	 */
	public WeraPricelist() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * 
	 * @param category
	 * @return 
	 */
	private String _getCategorieTree(final Category category) {

		return m_wm._getCategorieTree(category);
	}

	/**
	 * 
	 * @return 
	 */
	private String _strWriteTransferFile() {

		// --- Schreiben der Bilderliste -----------------------------------------------
		final Set keySet = m_hashImages.keySet();

		// --- Sortiere nach Namen
		final List listImages = Arrays.asList(keySet.toArray());

		String strCR = "";
		if (Config.getParameter("wera.os").equals("linux")) {
			strCR = "\n";
		} else {
			strCR = "\r\n";
		}
		// --- Pfad nalegen falls noch nicht vorhanden
		final String strDatum = m_strDatum.substring(6, 10) + "_" + m_strDatum.substring(3, 5) + "_" + m_strDatum.substring(0, 2);
		final String strImageDir = "wera_de_" + strDatum + "_media";
		String strImageListFile = "";
		if (Config.getParameter("wera.os").equals("linux")) {
			strImageListFile = m_strOutputPath + "make_transferfile.sh";
		} else {
			strImageListFile = m_strOutputPath + "make_transferfile.bat";
		}
		LOG.info("Scriptfile f�r Transfer=" + strImageListFile);
		if (listImages != null && listImages.size() > 0) {
			Collections.sort(listImages);
		}
		String strImageName = "";
		try {
			final FileWriter oFileWriterOK = new FileWriter(strImageListFile);
			File fileDirectory = null;

			if (Config.getParameter("wera.os").equals("linux")) {
				oFileWriterOK.write("#!/bin/sh" + strCR + "#" + strCR + strCR);
			} else {
				m_strOutputPath = m_strOutputPath.replace("/", "\\");
				m_strImageTransferPath = m_strImageTransferPath.replace("/", "\\");
			}

			// --- Aktuelles Verzeichnis setzen
			oFileWriterOK.write("cd " + m_strOutputPath + strCR);

			// --- Schleife �ber alle Bilder
			for (final Iterator it1 = listImages.iterator(); it1.hasNext();) {

				strImageName = (String) it1.next();
				fileDirectory = new File("/home/hybris/nexmart/imagepool/" + strImageName);

				// --- Pr�fen, ob die Datei vorhanden ist
				if (fileDirectory.exists()) {
					// --- Dateinamen holen und schreiben
					if (Config.getParameter("wera.os").equals("windows")) {
						oFileWriterOK.write("copy c:\\home\\hybris\\nexmart\\imagepool\\" + strImageName + " " + m_strOutputPath
								+ m_strImageTransferPath + strCR);
					} else {
						oFileWriterOK.write("cp /home/hybris/nexmart/imagepool/" + strImageName + " " + m_strOutputPath
								+ m_strImageTransferPath + strCR);
					}
				} else {
					// --- Dateinamen holen und schreiben
					m_aArrayLog.add("ERROR => Fehlendes Bild " + strImageName);
				}
			}

			// --- Erzeugen der Archive
			if (Config.getParameter("wera.os").equals("linux")) {
				oFileWriterOK.write("rm -f " + m_strImageTransferPath + ".zip" + strCR);
				oFileWriterOK.write("zip -9 -rv " + m_strImageTransferPath + " " + m_strImageTransferPath + strCR);
				oFileWriterOK.write("rm -rf " + m_strImageTransferPath + strCR);
				m_strResultFile = m_strOutputPath + m_strImageTransferPath + ".zip";
			}

			// --- Datei schliessen
			oFileWriterOK.close();

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strImageListFile;
	}

	/**
	 * Ausgabe einer CSV-Datei aller Produkte / Artikel mit Preisen
	 * @return 
	 */
	public String strExportListCSV() {

		// --- Initialize
		int iProductCounter = 0;
		final String strFN = "strExportListCSV ==> ";
		String strResult = "";
		String strOutput = "";
		String strLine = "";
		Collection productsSorted = null;
		Collection articles = null;
		final String strPreisInfo = "";
		final String strBesch = "";
		String strCode = "";
		final String strEAN = "";
		final Integer oPageNr = null;
		final Boolean bNeu = null;

		try {
			// --- �ffnen der Ausgabedatei
			final String strOutputFile = Config.getParameter("wera.exportpath") + "Produkt_Artikelliste.csv";
			final FileWriter fw = new FileWriter(strOutputFile);

			// --- Schreibe �berschrift
			strLine = "\"ArtikelNr" + "\";\"Produktbezeichnung_de" + "\";\"Produktbezeichnung_en" + "\";\"Produktbezeichnung_es"
					+ "\";\"Produktbezeichnung_fr" + "\";\"Produktbezeichnung_it" + "\";\"Artikel" + "\"\r\n";
			fw.write(strLine);

			// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
			weraCatalogVersion = m_wm.getCatalogVersion(Config.getParameter("wera.mastercatalog"), Config
					.getParameter("wera.mastercatalogversion"));

			// --- Alle alle Produkte sortiert nach Produktnummer
			strOutput = "Hole sortierte Produktliste ...";
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			productsSorted = WeraProduct.getAllProductsFromCatalog(weraCatalogVersion, true, "code");

			// --- Schleife �ber alle Produkte
			strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			final int iProdCnt = 0;
			for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
				m_product = (Product) it1.next();
				if (m_product instanceof WeraProduct || m_product instanceof WeraProductSet
						|| m_product instanceof WeraProductSetinSet) {

					// --- Ist das Produkt aktiv?
					if (((WeraProduct) m_product).IsAktiv()) {
						iProductCounter++;

						// --- Setze Sprache, und Defaultsprache=de
						initLanguage("de");
						String strBez_de = (String) m_product.getLocalizedProperty("name");
						strBez_de = strBez_de.replaceAll("\"", " ");
						initLanguage("en");
						String strBez_en = (String) m_product.getLocalizedProperty("name");
						strBez_en = strBez_en.replaceAll("\"", " ");
						initLanguage("es");
						String strBez_es = (String) m_product.getLocalizedProperty("name");
						strBez_es = strBez_es.replaceAll("\"", " ");
						initLanguage("fr");
						String strBez_fr = (String) m_product.getLocalizedProperty("name");
						strBez_fr = strBez_fr.replaceAll("\"", " ");
						initLanguage("it");
						String strBez_it = (String) m_product.getLocalizedProperty("name");
						strBez_it = strBez_it.replaceAll("\"", " ");
						initLanguage("de");

						if (m_product instanceof WeraProductSet || m_product instanceof WeraProductSetinSet) {

							// --- prüfe ob der produkt exportiert werden darg
							if ( m_wm.getVisibilityForCatalog( m_product, weraCatalogVersion, false ) == -1 ) {

								String strTmpCode = (String) m_product.getAttribute("code");
								LOG.info(strFN + strTmpCode + ", skipped, product not allowed for current catalogversion");
								continue;
							}

							// --- Schreiben der Artikeldaten
							strCode = (String) m_product.getAttribute("lagerNr") + (String) m_product.getAttribute("artnr")
									+ (String) m_product.getAttribute("variantenNr");
							strLine = "\"" + (String) m_product.getAttribute("code") + "\";\"" + strBez_de + "\";\"" + strBez_en
									+ "\";\"" + strBez_es + "\";\"" + strBez_fr + "\";\"" + strBez_it + "\";\"" + strCode + "\"\r\n";
							fw.write(strLine);

						} else {

							// --- Hole alle akitven Varianten des Products
							articles = ((WeraProduct) m_product).getVarianten();
							for (final Iterator it2 = articles.iterator(); it2.hasNext();) {

								// --- nächste Variante
								m_article = (WeraVariante) it2.next();

								// --- prüfe ob der produkt exportiert werden darg
								if ( m_wm.getVisibilityForCatalog( m_article, weraCatalogVersion, false ) == -1 ) {

									String strTmpCode = (String) m_article.getAttribute("code");
									LOG.info(strFN + strTmpCode + ", skipped, variant not allowed for current catalogversion");
									continue;
								}

								// --- Schreiben der Artikeldaten
								strCode = (String) m_article.getAttribute("lagerNr") + (String) m_article.getAttribute("code")
										+ (String) m_article.getAttribute("variantenNr");
								strLine = "\"" + (String) m_product.getAttribute("code") + "\";\"" + strBez_de + "\";\"" + strBez_en
										+ "\";\"" + strBez_es + "\";\"" + strBez_fr + "\";\"" + strBez_it + "\";\"" + strCode + "\"\r\n";
								fw.write(strLine);

								strBez_de = "";
								strBez_en = "";
								strBez_es = "";
								strBez_fr = "";
								strBez_it = "";
							}

							// --- Aufr�umen
							articles.clear();
							articles = null;
						}

					}
				}

			}
			LOG.info(strResult);

			//  --- Schliessen
			strOutput = "Export abgeschlossen. Anzahl Produkte=" + iProductCounter;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			fw.close();

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Aufr�umen
		cleanUp();

		return strResult;
	}

	/**
	 * 
	 * @param strFileDatum
	 * @param strLanguage
	 * @return 
	 */
	public String CreateOutputPath(String strFileDatum, final String strLanguage) {

		strFileDatum = strLanguage + "_" + strFileDatum;
		final String strPath1 = Config.getParameter("wera.homepath") + "/export/katalog/" + m_strCatalogversion.toLowerCase();
		m_strResultPath = "preisliste_" + m_strCatalogversion.toLowerCase() + "_" + strFileDatum;
		final String strPath2 = strPath1 + "/" + m_strResultPath;
		LOG.info("mkdir()= " + m_wm.createDirectory(Config.getParameter("wera.homepath") + "/export/preisliste/"));
		LOG.info("mkdir()= " + m_wm.createDirectory(strPath1));
		LOG.info("mkdir()= " + m_wm.createDirectory(strPath2));

		return strPath2;
	}

	/**
	 * Ausgabe einer CSV-Datei aller Produkte / Artikel mit Preisen
	 * @param strLanguage
	 * @param strKatalogversionPriceliste
	 * @param strReferenzKatalogversionPrint
	 * @param strKundenPriceListe
	 * @return 
	 */
	public String strExportTestCSV(final String strLanguage, final String strKatalogversionPriceliste,
			final String strReferenzKatalogversionPrint, final String strKundenPriceListe) {

		// --- Initialize
		String strInhalt = "";
		String strVarDE = "";
		String strVarUS = "";
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
		String strPreisInfo = "";
		String strImageName = "";
		String strBesch = "";
		String strCode = "";
		String strEAN = "";
		String oPageNr = null;
		Boolean bNeu = null;
		String strCategorie = "";
		HashMap hashmapValues = new HashMap();
		m_strCatalogversion = strKatalogversionPriceliste;

		// --- Datum f�r Aushabe initialisieren
		final String strFileDatum = m_wm.InitOutputDatum();

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(strLanguage);

		try {
			// --- Erzeuge die Export-Ablage
			final String strOutputFile = CreateOutputPath(strFileDatum, strLanguage) + "/" + strKundenPriceListe + ".txt";
			final FileWriter fw = new FileWriter(strOutputFile);

			// --- Schreibe �berschrift
			strLine = "\"PK" + "\"\t\"PK_SIS\"\t\"ArtikelNr" + "\"\t\"CodeNr" + "\"\t\"Produkttext" + "\"\t\"Abmessung" + "\"\t\"Seite"
					+ "\"\t\"Neu" + "\"\t\"EAN" + "\"\t\"Land" + "\"\t\"Waehrung" + "\"\t\"Menge_S1" + "\"\t\"Preis_S1"
					+ "\"\t\"Menge_S2" + "\"\t\"Preis_S2" + "\"\t\"Menge_S3" + "\"\t\"Preis_S3" + "\"\t\"Menge_S4" + "\"\t\"Preis_S4"
					+ "\"\t\"Menge_S5" + "\"\t\"Preis_S5" + "\"\t\"EVP" + "\"\t\"VPE" + "\"\t\"SortKey" + "\"\t\"Kategorie"
					+ "\"\t\"Abtrieb" + "\"\t\"VarNR_de" + "\"\t\"VarNR_us" + "\"\t\"Inhalt" + "\"\r\n";
			fw.write(strLine);

			// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
			weraCatalogVersion = m_wm.getCatalogVersion(m_strCatalogPriceliste, strKatalogversionPriceliste);

			// --- Hole Instanz WeraKatalog
			final WeraKatalog wk = new WeraKatalog();
			wk.InitCatalogPricelist(m_strCatalogPriceliste);
			wk.InitCatalogPrint(m_strCatalogPrint);

			// --- Alle alle Produkte sortiert nach Order
			strOutput = "Hole sortierte Produktliste (" + strLanguage + ") ...";
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			productsSorted = wk.getProductsFromPreisliste_v2(m_strCatalogPriceliste, strKatalogversionPriceliste, strReferenzKatalogversionPrint, 2);
			LOG.info(strFN + "Anzahl Produkte=" + productsSorted.size());

			// --- Schleife �ber alle Produkte
			strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";

			LOG.info("Looping over " + productsSorted.size() + " products.");
			final int iProdCnt = 0;
			for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
				hashmapValues = (HashMap) it1.next();

				// --- Hole Produkt
				m_product = (Product) hashmapValues.get("product");

				// --- Hole OrderKey
				strSortKey = ((Integer) hashmapValues.get("sortkey0")).toString();

				if (m_product instanceof WeraProduct || m_product instanceof WeraProductSet
						|| m_product instanceof WeraProductSetinSet) {

					// --- Debug
					LOG.info("++Code=" + m_product.getCode());

					iProductCounter++;

					// --- Initialisieren der Category
					strCategorie = "";
					final Category categoryWera = (Category) hashmapValues.get("category");
					if (categoryWera != null) {
						strCategorie = categoryWera.getName();
					}
					if (strCategorie == null) {
						strCategorie = "";
					}

					// --- Hole Seitennummern
					oPageNr = (String) hashmapValues.get("pages");
					if (oPageNr == null) {
						oPageNr = new String("");
					}

					// --- Abtriebicon holen
					strAbtrieb = "";
					icons1 = ((WeraProduct) m_product).getIcons1();
					if (icons1.size() > 0) {
						icon = (WeraMedia) icons1.iterator().next();
						strAbtrieb = icon.getCode();
					}

					// --- Bearbeite Satz
					if (m_product instanceof WeraProductSet || m_product instanceof WeraProductSetinSet) {


						// --- prüfe ob der produkt exportiert werden darg
						if ( m_wm.getVisibilityForCatalog( m_product, weraCatalogVersion, false ) == -1 ) {

							String strTmpCode = (String) m_wm.getAttribute(m_product, "code");
							LOG.info(strFN + strTmpCode + ", skipped, product not allowed for current catalogversion");
							continue;
						}

						Boolean bIstDisplay = null;
						if (m_product instanceof WeraProductSetinSet) {
							bIstDisplay = (Boolean) m_wm.getAttribute(m_product, "ist_display");
						}
						if (bIstDisplay == null) {
							bIstDisplay = new Boolean(false);
						}
						if (m_product instanceof WeraProductSetinSet && bIstDisplay.booleanValue() == false) {

							LOG.info("++class=" + m_product.getClass().getName());

							// --- Satz in Satz (Liste der S�tze
							articles = (Collection) m_wm.getAttribute(m_product, "weraproductsetvariants_qual");

							// --- Schleife �ber alle Artikel
							WeraProductSetVariants oWeraProductSetVariants = null;
							WeraProductSet oWeraProductset = null;
							for (Iterator it2 = articles.iterator(); it2.hasNext();) {

								// --- Hole den aktuellen Artikel
								oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
								oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");

								// --- prüfe ob der produkt exportiert werden darg
								if ( m_wm.getVisibilityForCatalog( oWeraProductset, weraCatalogVersion, false ) == -1 ) {

									String strTmpCode = (String) m_wm.getAttribute(oWeraProductset, "code");
									LOG.info(strFN + strTmpCode + ", skipped, SIS oWeraProductset not allowed for current catalogversion");
									continue;
								}

								// --- Verpackungseinheit
								Integer oVPE_SIS = (Integer) m_wm.getAttribute(oWeraProductSetVariants, "vpe");

								LOG.info("++class (satz)=" + oWeraProductset.getClass().getName());

								// --- F�lle einen Artikel
								if (oWeraProductset != null) {
									/**
									 * deaktiviert testweise GT 13.10.2020
									 */
									// --- Holen der Daten f�r einen einzelnen Satz
									// strLine = getDataWeraProductSet((WeraProductSet) oWeraProductset, (WeraProductSetinSet) m_product, strKundenPriceListe,
									//		strLanguage, strCategorie, strSortKey, oPageNr, strAbtrieb, oVPE_SIS);
									fw.write(strLine);
								}
							}

						} else {

							// --- prüfe ob der produkt exportiert werden darg
							if ( m_wm.getVisibilityForCatalog( m_product, weraCatalogVersion, false ) == -1 ) {

								String strTmpCode = (String) m_wm.getAttribute(m_product, "code");
								LOG.info(strFN + strTmpCode + ", skipped, Display m_product not allowed for current catalogversion");
								continue;
							}

							// --- Verpackungseinheit
							Integer oVPE = (Integer) m_wm.getAttribute(m_product, "contentQuantity");

							// --- Holen der Daten f�r einen einzelnen Satz
							/**
                             * deaktiviert testweise GT 13.10.2020
							 */
							// strLine = getDataWeraProductSet((WeraProductSet) m_product, null, strKundenPriceListe, strLanguage,
							//		strCategorie, strSortKey, oPageNr, strAbtrieb, oVPE);
							fw.write(strLine);
						}

					} else {
						// --- Hole alle akitven Varianten des Products
						articles = ((WeraProduct) m_product).getVarianten();

						// --- Hole Abmessungen
						final ArrayList aArticles = ((WeraProduct) m_product)._genCADataForVariantList(articles, null);

						for (final Iterator it2 = articles.iterator(); it2.hasNext();) {
							m_article = (WeraVariante) it2.next();

							// --- prüfe ob der produkt exportiert werden darg
							if ( m_wm.getVisibilityForCatalog( m_article, weraCatalogVersion, false ) == -1 ) {

								String strTmpCode = (String) m_article.getAttribute("code");
								LOG.info(strFN + strTmpCode + ", skipped, variant not allowed for current catalogversion");
								continue;
							}

							String strAbmessung = "";
							if (aArticles != null && aArticles.size() > 0) {
								Product product = null;
								HashMap hashmap = new HashMap();
								HashMap oHashMapArtikel = new HashMap();
								hashmap = (HashMap) aArticles.get(0);
								final ArrayList colHashArtikel = (ArrayList) hashmap.get("colHashArtikel");
								for (final Iterator it21 = colHashArtikel.iterator(); it21.hasNext();) {
									// --- Hole ProfiClassAttribute
									oHashMapArtikel = (HashMap) it21.next();
									product = (Product) oHashMapArtikel.get("variant");

									if (product.getCode().equals(m_article.getAttribute("code"))) {
										strAbmessung = oHashMapArtikel.get("value").toString();
										if (strAbmessung.length() > 4) {
											strAbmessung = strAbmessung.substring(4);
										}
										break;
									}

								}
							}
							if (strAbmessung == null || strAbmessung.length() == 0) {
								strAbmessung = "";
							}

							// --- Hole ContentQuantity
							Integer intContentQuantity = (Integer) m_wm.getAttribute(m_article, "contentQuantity");
							if (intContentQuantity == null) {
								intContentQuantity = new Integer(1);
							}

							// --- Holen der Preisinfos f�r den Artikel
							strPreisInfo = _getPriceInfo(m_article, strKundenPriceListe, iAnzahlSollNettoPrices, "\t");

							// --- Hole VariantenNr(n)
							strVarDE = (String) m_article.getAttribute("variantenNr");
							initLanguage("us-en");
							strVarUS = (String) m_article.getAttribute("variantenNr");
							initLanguage(strLanguage);

							// --- Schreiben der Artikeldaten
							strBesch = m_product.getName().replaceAll("\"", " ");
							bNeu = (Boolean) m_article.getAttribute("artikel_neu");
							if (bNeu == null) {
								bNeu = new Boolean(false);
							}
							strEAN = (String) m_article.getAttribute("ean");
							if (strEAN == null || strEAN.length() == 0) {
								strEAN = "";
							}
							strCode = (String) m_article.getAttribute("lagerNr") + (String) m_article.getAttribute("code") + strVarDE;
							strLine = "\"" + m_article.getPK() + "\"\t\"\"\t\"" + m_product.getCode() + "\"\t\"" + "'"
									+ strCode.replace("\"", "''") + "'" + "\"\t\"" + strBesch.replace("\"", "''") + "\"\t\""
									+ strAbmessung.replace("\"", "''") + "\"\t\"" + oPageNr + "\"\t\"" + bNeu.toString() + "\"\t\""
									+ strEAN + "\"\t\"" + strLanguage + "\"\t\"" + strPreisInfo + "\"\t\"" + intContentQuantity + "\"\t\""
									+ strSortKey + "\"\t\"" + strCategorie + "\"\t\"" + strAbtrieb + "\"\t\"" + "'" + strVarDE + "'"
									+ "\"\t\"" + "'" + strVarUS + "'" + "\"\t\"" + "\"\r\n";
							fw.write(strLine);
						}

						// --- Aufr�umen
						aArticles.clear();
						articles.clear();
						articles = null;
					}

				}
			}

			LOG.info(strResult);

			//  --- Schliessen
			strOutput = "Export abgeschlossen. Anzahl Produkte=" + iProductCounter;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			fw.close();

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Aufr�umen
		cleanUp();

		return strResult;
	}

	/**
	 * 
	 * @param aCsvHeader
	 * @return 
	 */
	private String __getLogisticHeadline(String[] aCsvHeader) {

		// --- initialize
		String strLine = "";

		// --- iterate over all elements
		for (String strColHeader : aCsvHeader) {

			strLine += "\t\"" + strColHeader + "\"";

		}
		return strLine;
	}

	/**
	 * get logistikdata-line
	 * @param aCsvHeader
	 * @param oProduct
	 * @return 
	 */
	private String __getLogisticData(String[] aCsvHeader, Product oProduct) {

		// --- initialize
		Object oDataTmp = "";
		HashMap aLogisticData = new HashMap();
		String strLine = "";

		// --- collect the data -------------------------------------------------------------------------------------------
		// --- AntiPattern3
		if (false) {

			// --- Hole alle ClassAttributeAssignment von Produkt / Variante oder Set
			final List<ClassAttributeAssignment> classattributeassignments = m_weraclassificationhelper
					.getAllClassAttributeAssignmentByProduct(oProduct);
			for (final Iterator it1 = classattributeassignments.iterator(); it1.hasNext();) {
				// --- Hole ProfiClassAttribute
				final ClassAttributeAssignment oClassAttributeAssignment = (ClassAttributeAssignment) it1.next();
				final ClassificationAttribute oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();
				final HashMap<String, ClassificationAttributeValue> hFeatureValues = m_weraclassificationhelper
						.getPickedClassificationAttributeValuesByProduct(oProduct, oClassAttributeAssignment);
				for (final Iterator it2 = hFeatureValues.values().iterator(); it2.hasNext();) {
					// --- Hole ProductFeature
					final ClassificationAttributeValue oClassificationAttributeValue = (ClassificationAttributeValue) it2.next();
					if (null == oClassificationAttributeValue) {
						continue;
					}
					try {
						if (oClassificationAttribute.getCode().equals("_AA081f001")) {
							// --- ArtikelLaenge
							oDataTmp = (String) oClassificationAttributeValue.getAttribute("name");
							if (null != oDataTmp) {
								aLogisticData.put(this.logisticArtikelLaenge, (String) oDataTmp.toString());
							}

						}
						if (oClassificationAttribute.getCode().equals("_AA040f001")) {
							// --- ArtikelBreite
							oDataTmp = (String) oClassificationAttributeValue.getAttribute("name");
							if (null != oDataTmp) {
								aLogisticData.put(this.logisticArtikelBreite, (String) oDataTmp.toString());
							}

						}
						if (oClassificationAttribute.getCode().equals("_AA031f001")) {
							// --- ArtikelHoehe
							oDataTmp = (String) oClassificationAttributeValue.getAttribute("name");
							if (null != oDataTmp) {
								aLogisticData.put(this.logisticArtikelHoehe, (String) oDataTmp.toString());
							}

						}

					} catch (final Exception e) {
						e.printStackTrace();
					}

				} // --- for (final Iterator it2 = hFeatureValues.values().iterator(); it2.hasNext();)
			}
		}

		//  --- ArtikelLaenge
		oDataTmp = (String) m_wm.getAttribute(oProduct, "artikel_laenge");
		if (null != oDataTmp) {
			aLogisticData.put(WeraPricelist.logisticArtikelLaenge, (String) oDataTmp);
		}

		//  --- ArtikelBreite
		oDataTmp = (String) m_wm.getAttribute(oProduct, "artikel_breite");
		if (null != oDataTmp) {
			aLogisticData.put(WeraPricelist.logisticArtikelBreite, (String) oDataTmp);
		}

		//  --- ArtikelHoehe
		oDataTmp = (String) m_wm.getAttribute(oProduct, "artikel_hoehe");
		if (null != oDataTmp) {
			aLogisticData.put(WeraPricelist.logisticArtikelHoehe, (String) oDataTmp);
		}

		//  --- Zolltarifnummer
		oDataTmp = (String) m_wm.getAttribute(oProduct, "Zolltarifnr");
		if (null != oDataTmp) {
			aLogisticData.put(WeraPricelist.logisticZolltarifnummer, (String) oDataTmp);
		}

		//  --- Ursprungsland
		oDataTmp = (String) m_wm.getAttribute(oProduct, "Ursprungsland");
		if (oDataTmp != null) {
			aLogisticData.put(WeraPricelist.logisticUrsprungsland, (String) oDataTmp);
		}

		// --- PackmassGewichtseinheit
		oDataTmp = (EnumerationValue) m_wm.getAttribute(oProduct, "packm_gewichteh");
		if (oDataTmp != null) {
			aLogisticData.put(WeraPricelist.logisticPackmassGewichtseinheit, ((EnumerationValue) oDataTmp).getCode());
		}

		// --- PackmassLaengeeinheit
		oDataTmp = (EnumerationValue) m_wm.getAttribute(oProduct, "packm_laengen_einheit");
		if (oDataTmp != null) {
			aLogisticData.put(WeraPricelist.logisticPackmassLaengeeinheit, ((EnumerationValue) oDataTmp).getCode());
		}

		// --- PackmassLaenge
		oDataTmp = (String) m_wm.getAttribute(oProduct, "packm_laenge");
		if (oDataTmp != null) {
			aLogisticData.put(WeraPricelist.logisticPackmassLaenge, (String) oDataTmp.toString());
		}

		// --- PackmassHoehe
		oDataTmp = (String) m_wm.getAttribute(oProduct, "packm_hoehe");
		if (oDataTmp != null) {
			aLogisticData.put(WeraPricelist.logisticPackmassHoehe, (String) oDataTmp.toString());
		}

		// --- PackmassBreite
		oDataTmp = (String) m_wm.getAttribute(oProduct, "packm_breite");
		if (oDataTmp != null) {
			aLogisticData.put(WeraPricelist.logisticPackmassBreite, (String) oDataTmp.toString());
		}

		// --- GewichtProStueck
		oDataTmp = (String) m_wm.getAttribute(oProduct, "Gewicht");
		if (oDataTmp != null) {
			aLogisticData.put(this.logisticGewichtProStueck, (String) oDataTmp.toString());
		}

		// --- GewichtVPE
		oDataTmp = (String) m_wm.getAttribute(oProduct, "GewVE");
		if (oDataTmp != null) {
			aLogisticData.put(this.logisticGewichtVPE, (String) oDataTmp.toString());
		}

		// --- Gewichteinheit
		oDataTmp = (EnumerationValue) m_wm.getAttribute(oProduct, "GewichtEinheit");
		if (oDataTmp != null) {
			aLogisticData.put(this.logisticGewichteinheit, ((EnumerationValue) oDataTmp).getCode());
		}

		// --- LaengeEeinheitArtikel
		oDataTmp = (EnumerationValue) m_wm.getAttribute(oProduct, "artikel_laengen_einheit");
		if (oDataTmp != null) {
			aLogisticData.put(this.logisticLaengeEeinheitArtikel, ((EnumerationValue) oDataTmp).getCode());
		}

		// --- PackmittelVolumen
		oDataTmp = (String) m_wm.getAttribute(oProduct, "packm_volumen");
		if (oDataTmp != null) {
			aLogisticData.put(this.logisticPackmittelVolumen, (String) oDataTmp.toString());
		}

		// --- PackmittelVolumeneinheit = PackmassLaengeeinheit
		oDataTmp = (EnumerationValue) m_wm.getAttribute(oProduct, "packm_laengen_einheit");
		if (oDataTmp != null) {
			aLogisticData.put(this.logisticPackmittelVolumeneinheit, ((EnumerationValue) oDataTmp).getCode() + "�");
		}

		// --- BruttoGewicht
		oDataTmp = (String) m_wm.getAttribute(oProduct, "GewichtBrutto");
		if (oDataTmp != null) {
			aLogisticData.put(this.logisticBruttoGewicht, (String) oDataTmp.toString());
		}

		// --- BruttoGewichtseinheit = GewichtEinheit
		oDataTmp = (EnumerationValue) m_wm.getAttribute(oProduct, "GewichtEinheit");
		if (oDataTmp != null) {
			aLogisticData.put(this.logisticBruttoGewichtseinheit, ((EnumerationValue) oDataTmp).getCode());
		}

		// --- collect the data -------------------------------------------------------------------------------------------
		// --- join it ----------------------------------------------------------------------------------------------------
		// --- iterate over all cols to join it
		for (String strColHeader : aCsvHeader) {
			if (aLogisticData.containsKey(strColHeader)) {
				strLine += "\t" + aLogisticData.get(strColHeader) + "";
			} else {
				strLine += "\t-";
			}

		}
		// --- join it ----------------------------------------------------------------------------------------------------

		return strLine;

	}

	/**
	 * Ausgabe einer CSV-Datei aller Produkte / Artikel mit Preisen
	 * aktuelle Version
	 *
	 * @param StringstrLanguage
	 * @param StringstrKatalogversionPriceliste
	 * @param StringstrReferenzKatalogversionPrint
	 * @param Collection<String> colPriceLists
	 * @return
	 */
	public String strExportPriceListCSV(final String strLanguage, final String strKatalogversionPriceliste,
			final String strReferenzKatalogversionPrint, Collection<String> colPriceLists  ) {

		// --- Initialize
		int iProductCounter = 0;
		final String strFN = "strExportPriceListCSV ==> ";
		String strResult = "";
		String strOutput = "";
		String strLine = "";
		Collection productsSorted = null;
		HashMap hashmapValues = new HashMap();
		m_strCatalogversion = strKatalogversionPriceliste;

		// --- Unser neuer Classifiactioan Helper
		m_weraclassificationhelper = new WeraClassificationHelper();

		// --- Col-Names for logistgic-data extention
		String[] aLogisticHeader = {};

		// --- Datum f�r Aushabe initialisieren
		final String strFileDatum = m_wm.InitOutputDatum();

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(strLanguage);

		// --- erste  Preisliste als ref

		try {

			// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
			weraCatalogVersion = m_wm.getCatalogVersion(m_strCatalogPriceliste, strKatalogversionPriceliste);
			
			// --- hole katalogsprachens
			// m_languages	= (Collection<Language>)m_wm.getAttribute(weraCatalogVersion, "languages");

			// --- Holen der Sprache ---------------------------------------------------------------------------------------------
			m_strLanguage	= strLanguage;
			// --- Hole Sprache (default)
			m_languages	= new ArrayList();
			final Collection<Language> languages = weraCatalogVersion.getLanguages();
			m_languages.add(m_strLanguage);
			for (Language lang : languages ) {

				String strTmpLanguage = lang.getIsoCode();
				if ( !strTmpLanguage.equals(m_strLanguage)) {
					m_languages.add(strTmpLanguage);
				}
			}
			// --- Holen der Sprache ---------------------------------------------------------------------------------------------

			// --- Erzeuge die Export-Ablage
			final String strOutputFile = CreateOutputPath(strFileDatum, strLanguage) + "/" + strKatalogversionPriceliste + ".txt";
			final FileWriter fw = new FileWriter(strOutputFile);

			// --- Schreibe �berschrift
			strLine = "\"PK\"\t\"PK_SIS\"\t\"ArtikelNr\"\t\"CodeNr\"\t\"Produkttext\"\t\"EAN\"\t\"Auslaufartikel\"\t\"Neu\"\t\"VPE\"\t\"SortKey\"";
			strLine += "\t\"valid_from\"\t\"valid_from\"";

			// --- preislistendaten hinzufügen
			for (String strPriceList :
					colPriceLists ) {
				strLine += "\t\""  + strPriceList + "~Waehrung"
						+ "\"\t\"" + strPriceList + "~Menge_S1" + "\"\t\"" + strPriceList + "~Preis_S1"
						+ "\"\t\"" + strPriceList + "~Menge_S2" + "\"\t\"" + strPriceList + "~Preis_S2"
						+ "\"\t\"" + strPriceList + "~Menge_S3" + "\"\t\"" + strPriceList + "~Preis_S3"
						+ "\"\t\"" + strPriceList + "~Menge_S4" + "\"\t\"" + strPriceList + "~Preis_S4"
						+ "\"\t\"" + strPriceList + "~Menge_S5" + "\"\t\"" + strPriceList + "~Preis_S5"
						+ "\"\t\"" + strPriceList + "~EVP\"";

			}

			// --- extend-line logistic data
			fw.write(strLine + "\r\n");
					
			// --- Hole Instanz WeraKatalog
			final WeraKatalog wk = new WeraKatalog();
			wk.InitCatalogPricelist(m_strCatalogPriceliste);
			wk.InitCatalogPrint(m_strCatalogPrint);

			// --- Alle alle Produkte sortiert nach Order
			strOutput = "Hole sortierte Produktliste (" + strLanguage + ") ... [x]";
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			productsSorted = wk.getProductsFromPreisliste_v2(m_strCatalogPriceliste, strKatalogversionPriceliste, strReferenzKatalogversionPrint, 2);
			LOG.info(strFN + "Anzahl Produkte=" + productsSorted.size());

			// --- Schleife �ber alle Produkte
			strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";

			LOG.info("Looping over " + productsSorted.size() + " products.");
			final int iProdCnt = 0;
			for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
				hashmapValues = (HashMap) it1.next();

				// --- Hole Produkt
				m_product = (Product) hashmapValues.get("product");

				// --- export aktuelles produkt
				iProductCounter = this._ExportPriceListCSV(strLanguage, m_product, hashmapValues, fw, aLogisticHeader, colPriceLists);

				// --- sind sb varianten vorhanden
				if (!(m_product instanceof WeraProductSetinSet)) {

					Collection<WeraProductSetinSet> colSB_Variants = (Collection<WeraProductSetinSet>) m_wm.getAttribute(m_product, "weraproductsetinsets_relation");
					if (colSB_Variants.size() > 0) {

						LOG.info("strExportPriceListCSV oWeraHauptProduct.code=" + m_product.getCode());

						// --- iterate on all
						int iCounter = 1;
						for (WeraProductSetinSet oWeraProductSetinSet : colSB_Variants) {

							LOG.info("strExportPriceListCSV oWeraProductSetinSet.code=" + oWeraProductSetinSet.getCode());
							if (colSB_Variants.size() == iCounter) {
								// --- letzte sb-vairante
								iProductCounter = this._ExportPriceListCSV(strLanguage, oWeraProductSetinSet, hashmapValues, fw, aLogisticHeader, colPriceLists);
							} else {
								// --- 1- vorletzte SB_Variante
								iProductCounter = this._ExportPriceListCSV(strLanguage, oWeraProductSetinSet, hashmapValues, fw, aLogisticHeader, colPriceLists);
							}

							// --- increment counter
							iCounter++;

						} // --- for ( WeraProductSetInSet oWeraProductSetInSet : colSB_Variants ) {

					} // --- if ( colSB_Variants.size() > 0 ) {

				} // --- if ( !(oParamproduct instanceof WeraProductSetinSet) ) {

			}

			LOG.info(strResult);

			//  --- Schliessen
			strOutput = "Export abgeschlossen. Anzahl Produkte=" + iProductCounter;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			fw.close();

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Aufr�umen
		cleanUp();

		return strResult;
	}




	/**
	 * Ausgabe einer CSV-Datei aller Produkte / Artikel mit Preisen
	 * alte Version
	 *
	 * @param strLanguage
	 * @param strKatalogversionPriceliste
	 * @param strReferenzKatalogversionPrint
	 * @param strKundenPriceListe
	 * @return
	 */
	public String strExportPriceListCSV_deakiviert (final String strLanguage, final String strKatalogversionPriceliste,
			final String strReferenzKatalogversionPrint, final String strKundenPriceListe) {

		// --- Initialize
		int iProductCounter = 0;
		final String strFN = "strExportPriceListCSV ==> ";
		String strResult = "";
		String strOutput = "";
		String strLine = "";
		Collection productsSorted = null;
		HashMap hashmapValues = new HashMap();
		m_strCatalogversion = strKatalogversionPriceliste;

		// --- Unser neuer Classifiactioan Helper
		m_weraclassificationhelper = new WeraClassificationHelper();

		// --- Col-Names for logistgic-data extention
		String[] aLogisticHeader = {
			this.logisticZolltarifnummer, this.logisticUrsprungsland, this.logisticPackmassGewichtseinheit,
			this.logisticPackmassLaengeeinheit, this.logisticPackmassLaenge, this.logisticPackmassHoehe,
			this.logisticPackmassBreite, this.logisticArtikelLaenge, this.logisticArtikelHoehe, this.logisticArtikelBreite,
			this.logisticGewichtProStueck, this.logisticGewichtVPE, this.logisticGewichteinheit,
			this.logisticLaengeEeinheitArtikel,
			this.logisticPackmittelVolumen, this.logisticPackmittelVolumeneinheit,
			this.logisticBruttoGewicht, this.logisticBruttoGewichtseinheit
		};

		// --- Datum f�r Aushabe initialisieren
		final String strFileDatum = m_wm.InitOutputDatum();

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(strLanguage);

		try {

			// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
			weraCatalogVersion = m_wm.getCatalogVersion(m_strCatalogPriceliste, strKatalogversionPriceliste);

			// --- hole katalogsprachens
			// m_languages	= (Collection<Language>)m_wm.getAttribute(weraCatalogVersion, "languages");

			// --- Holen der Sprache ---------------------------------------------------------------------------------------------
			m_strLanguage	= strLanguage;
			// --- Hole Sprache (default)
			m_languages	= new ArrayList();
			final Collection<Language> languages = weraCatalogVersion.getLanguages();
			m_languages.add(m_strLanguage);
			for (Language lang : languages ) {

				String strTmpLanguage = lang.getIsoCode();
				if ( !strTmpLanguage.equals(m_strLanguage)) {
					m_languages.add(strTmpLanguage);
				}
			}
			// --- Holen der Sprache ---------------------------------------------------------------------------------------------

			// --- Erzeuge die Export-Ablage
			final String strOutputFile = CreateOutputPath(strFileDatum, strLanguage) + "/" + strKundenPriceListe + ".txt";
			final FileWriter fw = new FileWriter(strOutputFile);

			// --- Schreibe �berschrift
			strLine = "\"PK" + "\"\t\"PK_SIS\"\t\"ArtikelNr" + "\"\t\"CodeNr" + "\"\t\"Produkttext" + "\"\t\"Abmessung" + "\"\t\"Seite"
					+ "\"\t\"Neu" + "\"\t\"Auslaufartikel" + "\"\t\"EAN" + "\"\t\"Land" + "\"\t\"Waehrung" + "\"\t\"Menge_S1" + "\"\t\"Preis_S1"
					+ "\"\t\"Menge_S2" + "\"\t\"Preis_S2" + "\"\t\"Menge_S3" + "\"\t\"Preis_S3" + "\"\t\"Menge_S4" + "\"\t\"Preis_S4"
					+ "\"\t\"Menge_S5" + "\"\t\"Preis_S5" + "\"\t\"EVP" + "\"\t\"VPE" + "\"\t\"SortKey" + "\"\t\"Kategorie"
					+ "\"\t\"Abtrieb" + "\"\t\"VarNR_de" + "\"\t\"VarNR_us" + "\"\t\"Inhalt\"\t\"Bildname\"\t\"SB_faehig\"";

			// --- artikelnummer index
			strLine += this.__getArtikelnrIndexHeadline();

			// --- extend-line logistic data
			strLine += this.__getLogisticHeadline(aLogisticHeader);
			fw.write(strLine + "\r\n");

			// --- Hole Instanz WeraKatalog
			final WeraKatalog wk = new WeraKatalog();
			wk.InitCatalogPricelist(m_strCatalogPriceliste);
			wk.InitCatalogPrint(m_strCatalogPrint);

			// --- Alle alle Produkte sortiert nach Order
			strOutput = "Hole sortierte Produktliste (" + strLanguage + ") ...";
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			productsSorted = wk.getProductsFromPreisliste_v2(m_strCatalogPriceliste, strKatalogversionPriceliste, strReferenzKatalogversionPrint, 2);
			LOG.info(strFN + "Anzahl Produkte=" + productsSorted.size());

			// --- Schleife �ber alle Produkte
			strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";

			LOG.info("Looping over " + productsSorted.size() + " products.");
			final int iProdCnt = 0;
			for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
				hashmapValues = (HashMap) it1.next();

				// --- Hole Produkt
				m_product = (Product) hashmapValues.get("product");

				// --- export aktuelles produkt
				iProductCounter = this._ExportPriceListCSV_deaktiviert(strLanguage, m_product, hashmapValues, fw, aLogisticHeader, strKundenPriceListe);

				// --- sind sb varianten vorhanden
				if (!(m_product instanceof WeraProductSetinSet)) {

					Collection<WeraProductSetinSet> colSB_Variants = (Collection<WeraProductSetinSet>) m_wm.getAttribute(m_product, "weraproductsetinsets_relation");
					if (colSB_Variants.size() > 0) {

						LOG.info("strExportPriceListCSV oWeraHauptProduct.code=" + m_product.getCode());

						// --- iterate on all
						int iCounter = 1;
						for (WeraProductSetinSet oWeraProductSetinSet : colSB_Variants) {

							LOG.info("strExportPriceListCSV oWeraProductSetinSet.code=" + oWeraProductSetinSet.getCode());

							// --- prüfe ob der produkt exportiert werden darg
							if ( m_wm.getVisibilityForCatalog( oWeraProductSetinSet, weraCatalogVersion, false ) == -1 ) {

								String strTmpCode = (String) oWeraProductSetinSet.getAttribute("code");
								LOG.info( strTmpCode + ", skipped, oWeraProductSetinSet not allowed for current catalogversion");
								continue;
							}

							if (colSB_Variants.size() == iCounter) {
								// --- letzte sb-vairante
								iProductCounter = this._ExportPriceListCSV_deaktiviert(strLanguage, oWeraProductSetinSet, hashmapValues, fw, aLogisticHeader, strKundenPriceListe);
							} else {
								// --- 1- vorletzte SB_Variante
								iProductCounter = this._ExportPriceListCSV_deaktiviert(strLanguage, oWeraProductSetinSet, hashmapValues, fw, aLogisticHeader, strKundenPriceListe);
							}

							// --- increment counter
							iCounter++;

						} // --- for ( WeraProductSetInSet oWeraProductSetInSet : colSB_Variants ) {

					} // --- if ( colSB_Variants.size() > 0 ) {

				} // --- if ( !(oParamproduct instanceof WeraProductSetinSet) ) {

			}

			LOG.info(strResult);

			//  --- Schliessen
			strOutput = "Export abgeschlossen. Anzahl Produkte=" + iProductCounter;
			LOG.info(strFN + strOutput);
			strResult += strOutput + "<br>";
			fw.close();

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Aufr�umen
		cleanUp();

		return strResult;
	}

	/**
	 * Export Produktzeile
	 *
	 * @param String strLanguage
	 * @param Product oParamproduct
	 * @param HashMap hashmapValues
	 * @param HashMap hashmapValues
	 * @param FileWriter fw
	 * @param String[]  aLogisticHeader
	 * @param Collection<String> colPriceLists
	 */
	public int _ExportPriceListCSV(String strLanguage, Product oParamproduct, HashMap hashmapValues, FileWriter fw, String[] aLogisticHeader, Collection<String> colPriceLists ) throws Exception {

		// --- Initialize
		int iProductCounter = 0;
		String strLine = "";
		Collection articles = null;
		String strImageName = "";
		String strVarDE = "";
		String strAbtrieb = "";
		String strCategorie = "";
		String strSortKey = "";
		String strPreisInfo = "";
		String strEAN = "";
		Boolean bAuslauf = null;
		Boolean bNeu = null;
		String oPageNr = null;
		String strBesch = "";
		String strCode = "";
		String strArtNr = "";
		Date oDateFrom = null;
		Date oDateTo = null;
		String strValidFrom = "";
		String strValidTo = "";


		// --- preset
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
		int iAnzahlSollNettoPrices = 5;

		if (oParamproduct instanceof WeraProduct || oParamproduct instanceof WeraProductSet
				|| oParamproduct instanceof WeraProductSetinSet) {

			// --- Debug
			LOG.info("++Code=" + oParamproduct.getCode());

			iProductCounter++;

			// --- sortkey
			Integer order 				= (Integer) hashmapValues.get("sortkey0");
			strSortKey					= order.toString();

			// --- Bearbeite Satz
			if (oParamproduct instanceof WeraProductSet || oParamproduct instanceof WeraProductSetinSet) {

				Boolean bIstDisplay = null;
				if (oParamproduct instanceof WeraProductSetinSet) {
					bIstDisplay = (Boolean) m_wm.getAttribute(oParamproduct, "ist_display");
				}
				if (bIstDisplay == null) {
					bIstDisplay = new Boolean(false);
				}
				if (oParamproduct instanceof WeraProductSetinSet && bIstDisplay.booleanValue() == false) {

					LOG.info("++class=" + oParamproduct.getClass().getName());

					// --- Satz in Satz (Liste der S�tze
					articles = (Collection) m_wm.getAttribute(oParamproduct, "weraproductsetvariants_qual");

					// --- Schleife �ber alle Artikel
					WeraProductSetVariants oWeraProductSetVariants = null;
					WeraProductSet oWeraProductset = null;
					for (Iterator it2 = articles.iterator(); it2.hasNext();) {

						// --- Hole den aktuellen Artikel
						oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
						oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");

						// --- prüfe ob der produkt exportiert werden darg
						if ( m_wm.getVisibilityForCatalog( oWeraProductset, weraCatalogVersion, false ) == -1 ) {

							String strTmpCode = (String) oWeraProductset.getAttribute("code");
							LOG.info( strTmpCode + ", skipped, SIS oWeraProductset not allowed for current catalogversion");
							continue;
						}

						// --- Verpackungseinheit
						Integer oVPE_SIS = (Integer) m_wm.getAttribute(oWeraProductSetVariants, "vpe");

						// --- F�lle einen Artikel
						if (oWeraProductset != null) {

							LOG.info("++class (satz)=" + oWeraProductset.getClass().getName());
							
							// --- Holen der Daten f�r einen einzelnen Satz
							strLine = getDataWeraProductSet((WeraProductSet) oWeraProductset, (WeraProductSetinSet) oParamproduct, colPriceLists,
									strLanguage, strCategorie, strSortKey, oPageNr, strAbtrieb, oVPE_SIS);

							fw.write(strLine + "\r\n");
						}
					}

				} else {

					// --- Verpackungseinheit
					Integer oVPE = (Integer) m_wm.getAttribute(oParamproduct, "contentQuantity");
					
					// --- Holen der Daten f�r einen einzelnen Satz
					strLine = getDataWeraProductSet((WeraProductSet) oParamproduct, null, colPriceLists, strLanguage,
							strCategorie, strSortKey, oPageNr, strAbtrieb, oVPE);


					fw.write(strLine + "\r\n");
				}

			} else {
				// --- Hole alle akitven Varianten des Products
				LOG.info("++ vor getVarianten()" );
				articles = ((WeraProduct) oParamproduct).getVarianten();
////////// getVisibilityForCatalog
				// --- Hole Abmessungen
				final ArrayList aArticles = ((WeraProduct) oParamproduct)._genCADataForVariantList(articles, null);

				for (final Iterator it2 = articles.iterator(); it2.hasNext();) {
					m_article = (WeraVariante) it2.next();

					// --- prüfe ob der produkt exportiert werden darg
					if ( m_wm.getVisibilityForCatalog( m_article, weraCatalogVersion, false ) == -1 ) {

						String strTmpCode = (String) m_article.getAttribute("code");
						LOG.info( strTmpCode + ", skipped, variant not allowed for current catalogversion");
						continue;
					}

					// --- Hole ContentQuantity
					Integer intContentQuantity = (Integer) m_wm.getAttribute(m_article, "contentQuantity");
					if (intContentQuantity == null) {
						intContentQuantity = new Integer(0);
					}

					// --- Hole VariantenNr(n)
					strVarDE = (String) m_article.getAttribute("variantenNr");
					//initLanguage("us-en");

					// --- Schreiben der Artikeldaten
					strBesch = oParamproduct.getName();
					if (strBesch == null) {
						strBesch = "";
					}
					strBesch = strBesch.replaceAll("\"", " ");

					// --- Neu-Flag
					bNeu = (Boolean) m_article.getAttribute("artikel_neu");
					if (bNeu == null) {
						bNeu = new Boolean(false);
					}

					// --- Auslauf-Flag
					bAuslauf = (Boolean) m_article.getAttribute("artikel_auslauf");
					if (bAuslauf == null) {
						bAuslauf = new Boolean(false);
					}
					if (bAuslauf.booleanValue()) {
						// --- Auslaufartikel sind nie neu!!
						bNeu = new Boolean(false);
					}

					// --- EAN
					strEAN = (String) m_article.getAttribute("ean");
					if (strEAN == null || strEAN.length() == 0) {
						strEAN = "";
					}

					// --- valid_from
					oDateFrom = (Date) m_wm.getAttribute(m_article, "valid_from");
					if (oDateFrom == null) {
						strValidFrom = "";
					} else {
						strValidFrom = dateFormat.format(oDateFrom);;
					}

					// --- valid_from
					oDateTo = (Date) m_wm.getAttribute(m_article, "valid_to");
					if (oDateTo == null) {
						strValidTo = "";
					} else {
						strValidTo = dateFormat.format(oDateTo);;
					}

					// --- EAN
					strEAN = (String) m_article.getAttribute("ean");
					if (strEAN == null || strEAN.length() == 0) {
						strEAN = "";
					}

					// --- arnr-nummer
					strArtNr			= (String) oParamproduct.getCode();
					
					// --- code-nummer
					strCode = (String) m_article.getAttribute("lagerNr") + (String) m_article.getAttribute("code") + strVarDE;

					// --- zeile zusammenbauen
					String strLinePrefix	= "\"" + m_article.getPK() + "\"\t\"\"\t\"" + strArtNr + "\"\t\""
							+ strCode
							+ "\"\t\"" + strBesch.replace("\"", "''")
							+ "\"\t\"" + strEAN.replace("\"", "''")
							+ "\"\t\"" + bAuslauf.toString()
							+ "\"\t\"" + bNeu.toString()
							+ "\"\t\"" + intContentQuantity
							+ "\"\t\"" + strSortKey
							+ "\"\t\"" + strValidFrom
							+ "\"\t\"" + strValidTo + "\"";

					// --- Holen der Preisinfos f�r den Artikel
					strPreisInfo	= "";
					for (String strKundenPriceListe :  colPriceLists ) {

						strPreisInfo += "\t\"" + _getPriceInfo(m_article, strKundenPriceListe, iAnzahlSollNettoPrices, "\t") + "\"";
					}
					//strPreisInfo += "\"";

					// --- zeile zusammenbauen
					strLine = strLinePrefix + strPreisInfo;

					fw.write(strLine + "\r\n");
				}

				// --- Aufr�umen
				aArticles.clear();
				articles.clear();
				articles = null;
			}
		}

		return iProductCounter;
	}

	/**
	 * Export Produktzeile
	 *
	 * @param strLanguage
	 * @param m_product
	 * @param hashmapValues
	 */
	public int _ExportPriceListCSV_deaktiviert (String strLanguage, Product oParamproduct, HashMap hashmapValues, FileWriter fw, String[] aLogisticHeader, String strKundenPriceListe) throws Exception {

		// --- Initialize
		int iProductCounter = 0;
		String strLine = "";
		Collection articles = null;
		String strImageName = "";
		String strVarDE = "";
		String strVarUS = "";
		String strAbtrieb = "";
		String strCategorie = "";
		String strSortKey = "";
		String strPreisInfo = "";
		String strEAN = "";
		Boolean bAuslauf = null;
		Boolean bNeu = null;
		Boolean bSB_faehig = null;
		String oPageNr = null;
		String strBesch = "";
		String strCode = "";
		String strArtNr = "";
		String strArtikelnrIndex = "";
		String strInhalt = "";
		Collection icons1 = null;
		WeraMedia icon = null;
		int iAnzahlSollNettoPrices = 5;

		// --- Hole OrderKey
		strSortKey = ((Integer) hashmapValues.get("sortkey0")).toString();

		if (oParamproduct instanceof WeraProduct || oParamproduct instanceof WeraProductSet
				|| oParamproduct instanceof WeraProductSetinSet) {

			// --- Debug
			LOG.info("++Code=" + oParamproduct.getCode());

			iProductCounter++;

			// --- Initialisieren der Category
			strCategorie = "";
			final Category categoryWera = (Category) hashmapValues.get("category");
			if (categoryWera != null) {
				strCategorie = categoryWera.getName();
			}
			if (strCategorie == null) {
				strCategorie = "";
			}

			// --- Hole Seitennummern
			oPageNr = (String) hashmapValues.get("pages");
			if (oPageNr == null) {
				oPageNr = "";
			}

			// --- Abtriebicon holen
			strAbtrieb = "";
			icons1 = ((WeraProduct) oParamproduct).getIcons1();
			if (icons1.size() > 0) {
				icon = (WeraMedia) icons1.iterator().next();
				strAbtrieb = icon.getCode();
			}

			// --- Bearbeite Satz
			if (oParamproduct instanceof WeraProductSet || oParamproduct instanceof WeraProductSetinSet) {


				Boolean bIstDisplay = null;
				if (oParamproduct instanceof WeraProductSetinSet) {
					bIstDisplay = (Boolean) m_wm.getAttribute(oParamproduct, "ist_display");
				}
				if (bIstDisplay == null) {
					bIstDisplay = new Boolean(false);
				}
				if (oParamproduct instanceof WeraProductSetinSet && bIstDisplay.booleanValue() == false) {

					LOG.info("++class=" + oParamproduct.getClass().getName());

					// --- Satz in Satz (Liste der S�tze
					articles = (Collection) m_wm.getAttribute(oParamproduct, "weraproductsetvariants_qual");

					// --- localisierte Artikelnummer
					strArtikelnrIndex	= this.__getArtikelnrIndex ( oParamproduct );

					// --- Schleife �ber alle Artikel
					WeraProductSetVariants oWeraProductSetVariants = null;
					WeraProductSet oWeraProductset = null;
					for (Iterator it2 = articles.iterator(); it2.hasNext();) {

						// --- Hole den aktuellen Artikel
						oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
						oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");

						// --- prüfe ob der produkt exportiert werden darg
						if ( m_wm.getVisibilityForCatalog( oWeraProductset, weraCatalogVersion, false ) == -1 ) {

							String strTmpCode = (String) m_article.getAttribute("code");
							LOG.info( strTmpCode + ", skipped, SIS oWeraProductset not allowed for current catalogversion");
							continue;
						}

						// --- Verpackungseinheit
						Integer oVPE_SIS = (Integer) m_wm.getAttribute(oWeraProductSetVariants, "vpe");

						// --- F�lle einen Artikel
						if (oWeraProductset != null) {

							LOG.info("++class (satz)=" + oWeraProductset.getClass().getName());

							// --- Holen der Daten f�r einen einzelnen Satz
							strLine = getDataWeraProductSet_deaktiviert ((WeraProductSet) oWeraProductset, (WeraProductSetinSet) oParamproduct, strKundenPriceListe,
									strLanguage, strCategorie, strSortKey, oPageNr, strAbtrieb, oVPE_SIS);

							// --- localisiert artikelnummer
							strLine	+= "\t" + strArtikelnrIndex;

							// --- get logistikdata-line
							strLine += this.__getLogisticData(aLogisticHeader, oWeraProductset);

							fw.write(strLine + "\r\n");
						}
					}

				} else {

					// --- Verpackungseinheit
					Integer oVPE = (Integer) m_wm.getAttribute(oParamproduct, "contentQuantity");

					// --- localisierte Artikelnummer
					strArtikelnrIndex	= this.__getArtikelnrIndex ( oParamproduct );

					// --- prüfe ob der produkt exportiert werden darg
					if ( m_wm.getVisibilityForCatalog( (WeraProductSet) oParamproduct, weraCatalogVersion, false ) == -1 ) {

						String strTmpCode = (String) m_article.getAttribute("code");
						LOG.info( strTmpCode + ", skipped, SIS Display not allowed for current catalogversion");
						return iProductCounter;
					}

					// --- Holen der Daten f�r einen einzelnen Satz
					strLine = getDataWeraProductSet_deaktiviert((WeraProductSet) oParamproduct, null, strKundenPriceListe, strLanguage,
							strCategorie, strSortKey, oPageNr, strAbtrieb, oVPE);

					// --- localisiert artikelnummer
					strLine	+= "\t" + strArtikelnrIndex;

					// --- get logistikdata-line
					strLine += this.__getLogisticData(aLogisticHeader, oParamproduct);

					fw.write(strLine + "\r\n");
				}

			} else {
				// --- Hole alle akitven Varianten des Products
				articles = ((WeraProduct) oParamproduct).getVarianten();

				// --- Hole Abmessungen
				final ArrayList aArticles = ((WeraProduct) oParamproduct)._genCADataForVariantList(articles, null);

				for (final Iterator it2 = articles.iterator(); it2.hasNext();) {
					m_article = (WeraVariante) it2.next();

					// --- prüfe ob der produkt exportiert werden darg
					if ( m_wm.getVisibilityForCatalog( m_article, weraCatalogVersion, false ) == -1 ) {

						String strTmpCode = (String) m_article.getAttribute("code");
						LOG.info( strTmpCode + ", skipped, variant not allowed for current catalogversion");
						continue;
					}

					String strAbmessung = "";
					if (aArticles != null && aArticles.size() > 0) {
						Product product = null;
						HashMap hashmap = new HashMap();
						HashMap oHashMapArtikel = new HashMap();
						hashmap = (HashMap) aArticles.get(0);
						final ArrayList colHashArtikel = (ArrayList) hashmap.get("colHashArtikel");
						for (final Iterator it21 = colHashArtikel.iterator(); it21.hasNext();) {
							// --- Hole ProfiClassAttribute
							oHashMapArtikel = (HashMap) it21.next();
							product = (Product) oHashMapArtikel.get("variant");

							if (product.getCode().equals(m_article.getAttribute("code"))) {
								strAbmessung = oHashMapArtikel.get("value").toString();
								if (strAbmessung.length() > 3) {
									strAbmessung = strAbmessung.substring(3);
								}
								break;
							}

						}
					}
					if (strAbmessung == null || strAbmessung.length() == 0) {
						strAbmessung = "";
					}

					// --- Hole ContentQuantity
					Integer intContentQuantity = (Integer) m_wm.getAttribute(m_article, "contentQuantity");
					if (intContentQuantity == null) {
						intContentQuantity = new Integer(0);
					}

					// --- Holen der Preisinfos f�r den Artikel
					strPreisInfo = _getPriceInfo(m_article, strKundenPriceListe, iAnzahlSollNettoPrices, "\t");

					// --- Hole VariantenNr(n)
					strVarDE = (String) m_article.getAttribute("variantenNr");
					initLanguage("us-en");
					strVarUS = (String) m_article.getAttribute("variantenNr");
					initLanguage(strLanguage);

					// --- Schreiben der Artikeldaten
					strImageName = ((WeraProduct) oParamproduct).normalizeFilenameForImageLookup();
					if (strImageName == null) {
						strImageName = "";
					}
					strBesch = oParamproduct.getName();
					if (strBesch == null) {
						strBesch = "";
					}
					strBesch = strBesch.replaceAll("\"", " ");
					bNeu = (Boolean) m_article.getAttribute("artikel_neu");
					if (bNeu == null) {
						bNeu = new Boolean(false);
					}
					bSB_faehig = (Boolean) oParamproduct.getAttribute("artikel_sb_faehig");
					if (bSB_faehig == null) {
						bSB_faehig = new Boolean(false);
					}
					bAuslauf = (Boolean) m_article.getAttribute("artikel_auslauf");
					if (bAuslauf == null) {
						bAuslauf = new Boolean(false);
					}
					if (bAuslauf.booleanValue()) {
						// --- Auslaufartikel sind nie neu!!
						bNeu = new Boolean(false);
					}
					strEAN = (String) m_article.getAttribute("ean");
					if (strEAN == null || strEAN.length() == 0) {
						strEAN = "";
					}
//System.out.println( "Abmessung |" + strAbmessung + "|");

					// --- arnr-nummer
					strArtNr			= (String) oParamproduct.getCode();

					// --- localisierte Artikelnummer
					strArtikelnrIndex	= this.__getArtikelnrIndex ( oParamproduct );

					// --- code-nummer
					strCode = (String) m_article.getAttribute("lagerNr") + (String) m_article.getAttribute("code") + strVarDE;

					// --- zeile zusammenbauen
					strLine = "\"" + m_article.getPK() + "\"\t\"\"\t\"" + strArtNr + "\"\t\"" + "'"
							+ strCode.replace("\"", "''") + "'" + "\"\t\"" + strBesch.replace("\"", "''") + "\"\t\""
							+ strAbmessung.replace("\"", "''") + "\"\t\"" + oPageNr + "\"\t\"" + bNeu.toString() + "\"\t\"" + bAuslauf.toString() + "\"\t\""
							+ strEAN + "\"\t\"" + strLanguage + "\"\t\"" + strPreisInfo + "\"\t\"" + intContentQuantity + "\"\t\""
							+ strSortKey + "\"\t" + strCategorie + "\t\"" + strAbtrieb + "\"\t\"" + "'" + strVarDE + "'"
							+ "\"\t\"" + "'" + strVarUS + "'" + "\"\t\"" + "\"" + "\t" + strImageName + ".jpg" + "\t" + bSB_faehig.toString();

					// --- localisiert artikelnummer
					strLine	+= "\t" + strArtikelnrIndex;

					// --- get logistikdata-line
					strLine += this.__getLogisticData(aLogisticHeader, m_article);

					fw.write(strLine + "\r\n");
				}

				// --- Aufr�umen
				aArticles.clear();
				articles.clear();
				articles = null;
			}
		}

		return iProductCounter;
	}

	/**
	 * liste der localisieren Artikelnummern holen
	 */
	private String __getArtikelnrIndexHeadline () {
		
		// --- preset
		int iCount							= 0;
		String strArtikelnrIndexHeadline	= "";
		
		// --- Schleife �ber alle Sprachen
		for ( String isocode : m_languages) {

			// --- hole localisierte artnr
			//String isocode					= (String)m_wm.getAttribute(currentLanguage, "isocode");
		
			// --- n�chstes Tab
			if ( iCount == 0 ) {
				strArtikelnrIndexHeadline	+= "\t\"" + isocode + "\"";
			} else {
				strArtikelnrIndexHeadline	+= "\t\"" + isocode + "\"";
			}

			// --- n�chstes Tab
			iCount++;
			
		} // --- for ( Language currentLanguage : m_languages) {
	
		return strArtikelnrIndexHeadline;
	}

	/**
	 * liste der localisieren Artikelnummern holen
	 */
	private String __getArtikelnrIndex ( Product oParamproduct ) {
		
		// --- preset
		int iCount					= 0;
		String strArtNr				= (String) oParamproduct.getCode();
		String strArtikelnrIndex	= "";
		
		// --- Schleife �ber alle Sprachen
		Language defaultLang	= m_jaloSession.getSessionContext().getLanguage();
		for ( String isocode : m_languages) {

			// --- Hole Sprachen und aktiviere sie.
			// m_jaloSession.getSessionContext().setLanguage(currentLanguage);

			// --- sprache setzen
			initLanguage(isocode);

			// --- hole localisierte artnr
			// String isocode		= (String)m_wm.getAttribute(currentLanguage, "isocode");
			// String strArtNrTmp	= (String)m_wm.getAttribute(oParamproduct, "name");
			String strArtNrTmp	= (String)m_wm.getAttribute(oParamproduct, "artikelnr_index");
			if ( strArtNrTmp == null || strArtNrTmp == "" ) {
				strArtNrTmp	= strArtNr;
			}
			if ( iCount == 0 ) {
				strArtikelnrIndex	+= "\"" + strArtNrTmp + "\"";
			} else {
				strArtikelnrIndex	+= "\t\"" + strArtNrTmp + "\"";
			}
			
			// --- n�chstes Tab
			iCount++;

		} // --- for ( Language currentLanguage : m_languages) {
		
		// --- default spreache zur�cksetzen
		m_jaloSession.getSessionContext().setLanguage(defaultLang);
	
		return strArtikelnrIndex;
	}
	
	 /* 
	 * @param WeraProductSet weraproductset
	 * @param WeraProductSetinSet weraproductsetinset
	 * @param Collection<String> colPriceLists
	 * @param String strLanguage
	 * @param String strCategorie
	 * @param String strSortKey
	 * @param String strPageNr
	 * @param String strAbtrieb
	 * @param Integer oVPE
	 * @return 
	 */
	public String getDataWeraProductSet(WeraProductSet weraproductset, WeraProductSetinSet weraproductsetinset, Collection<String> colPriceLists, String strLanguage,
			String strCategorie, String strSortKey, String strPageNr, String strAbtrieb, Integer oVPE) {

		// --- Initialize
		String strVarDE = "";
		final int iAnzahlSollNettoPrices = 5;
		String strLine = "";
		String strPreisInfo = "";
		String strBesch = "";
		String strCode = "";
		String strEAN = "";
		Boolean bAuslauf = null;
		Boolean bNeu = null;
		Date oDateFrom = null;
		Date oDateTo = null;
		String strValidFrom = "";
		String strValidTo = "";

		// --- preset
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");


		// --- Hole ContentQuantity
		//Integer intContentQuantity = (Integer) m_wm.getAttribute(weraproductset, "contentQuantity");
		if (oVPE == null) {
			oVPE = new Integer(0);
		}

		// --- Hole VariantenNr(n)
		strVarDE = (String) m_wm.getAttribute(weraproductset, "variantenNr");
		//initLanguage("us-en");
		//initLanguage(strLanguage);

		// --- Schreiben der Artikeldaten
		String strLagerNr = (String) m_wm.getAttribute(weraproductset, "lagerNr");
		if (strLagerNr == null) {
			strLagerNr = "";
		}
		String strArtNr = (String) m_wm.getAttribute(weraproductset, "artnr");
		if (strArtNr == null) {
			strArtNr = "";
		}
		strCode = strLagerNr + strArtNr + strVarDE;

		// --- Neu-Flag
		bNeu = (Boolean) m_wm.getAttribute(weraproductset, "produkt_neu");
		if (bNeu == null) {
			bNeu = new Boolean(false);
		}

		// --- Auslauf-Flag
		bAuslauf = (Boolean) m_wm.getAttribute(weraproductset, "artikel_auslauf");
		if (bAuslauf == null) {
			bAuslauf = new Boolean(false);
		}
		if (bAuslauf.booleanValue()) // --- Auslaufartikel sind nie neu!!
		{
			bNeu = new Boolean(false);
		}

		// --- EAN
		strEAN = (String) m_wm.getAttribute(weraproductset, "ean");
		if (strEAN == null || strEAN.length() == 0) {
			strEAN = "";
		}

		// --- PK bei SIS
		String strPK_SIS = "";
		if (weraproductsetinset != null) {
			strPK_SIS = (String) weraproductsetinset.getPK().toString();
		}

		// --- Artikelnummer / Beschreibung ----------------------
		String strArtikelNr = "";
		if (weraproductsetinset != null) {
			// --- ArtNr vom Hauptprodukt verwenden
			strArtikelNr = weraproductsetinset.getCode();

			// --- Beschreibung
			strBesch = weraproductsetinset.getName();
		} else {
			strArtikelNr = weraproductset.getCode();

			// --- Beschreibung
			strBesch = weraproductset.getName();
		}
		if (strBesch == null) {
			strBesch = "";
		}
		strBesch = strBesch.replaceAll("\"", " ");
		// --- Artikelnummer / Beschreibung ----------------------


		// --- valid_from
		oDateFrom = (Date) m_wm.getAttribute(weraproductset, "valid_from");
		if (oDateFrom == null) {
			strValidFrom = "";
		} else {
			strValidFrom = dateFormat.format(oDateFrom);;
		}

		// --- valid_from
		oDateTo = (Date) m_wm.getAttribute(weraproductset, "valid_to");
		if (oDateTo == null) {
			strValidTo = "";
		} else {
			strValidTo = dateFormat.format(oDateTo);;
		}

		// --- zeile zusamensetzen
		String strLinePrefix	= "\"" + weraproductset.getPK() + "\"\t\"" + strPK_SIS + "\"\t\"" + strArtikelNr + "\"\t\""
				+ strCode + "\"\t\""
				+ strBesch.replace("\"", "''")
				+ "\"\t\"" + strEAN.replace("\"", "''")
				+ "\"\t\"" + bAuslauf.toString()
				+ "\"\t\"" + bNeu.toString()
				+ "\"\t\"" + oVPE
				+ "\"\t\"" + strSortKey
				+ "\"\t\"" + strValidFrom
				+ "\"\t\"" + strValidTo + "\"";

		// --- Holen der Preisinfos f�r den Artikel
		strPreisInfo	= "";
		for (String strKundenPriceListe :  colPriceLists ) {

			strPreisInfo += "\t\"" + _getPriceInfo(weraproductset, strKundenPriceListe, iAnzahlSollNettoPrices, "\t") + "\"";
		}
		///strPreisInfo += "\"";

		// --- zeile zusammenbauen
		strLine = strLinePrefix + strPreisInfo;


		return strLine;
	}


	 /*
	 * @param weraproductset
	 * @param weraproductsetinset
	 * @param strKundenPriceListe
	 * @param strLanguage
	 * @param strCategorie
	 * @param strSortKey
	 * @param strPageNr
	 * @param strAbtrieb
	 * @param oVPE
	 * @return
	 */
	public String getDataWeraProductSet_deaktiviert (WeraProductSet weraproductset, WeraProductSetinSet weraproductsetinset, String strKundenPriceListe, String strLanguage,
			String strCategorie, String strSortKey, String strPageNr, String strAbtrieb, Integer oVPE) {

		// --- Initialize
		String strInhalt = "";
		String strVarDE = "";
		String strVarUS = "";
		Collection icons1 = null;
		WeraMedia icon = null;
		final int iAnzahlSollNettoPrices = 5;
		int iProductCounter = 0;
		final String strFN = "strExportPriceListCSV ==> ";
		String strResult = "";
		String strOutput = "";
		String strLine = "";
		Collection productsSorted = null;
		Collection articles = null;
		String strPreisInfo = "";
		String strBesch = "";
		String strImageName = "";
		String strCode = "";
		String strEAN = "";
		Boolean bNeu = null;
		Boolean bAuslauf = null;
		Boolean bSB_faehig = null;

		// --- Holen der Preisinfos f�r den Satz
		strPreisInfo = _getPriceInfo(weraproductset, strKundenPriceListe, iAnzahlSollNettoPrices, "\t");

		// --- Hole ContentQuantity
		//Integer intContentQuantity = (Integer) m_wm.getAttribute(weraproductset, "contentQuantity");
		if (oVPE == null) {
			oVPE = new Integer(0);
		}

		// --- Hole VariantenNr(n)
		strVarDE = (String) m_wm.getAttribute(weraproductset, "variantenNr");
		initLanguage("us-en");
		strVarUS = (String) m_wm.getAttribute(weraproductset, "variantenNr");
		initLanguage(strLanguage);

		// --- Ermitteln des Satzinhaltes
		strInhalt = _getSetContent(weraproductset);

		// --- Schreiben der Artikeldaten
		strImageName = ((WeraProduct) weraproductset).normalizeFilenameForImageLookup();
		if (strImageName == null) {
			strImageName = "";
		}
		String strLagerNr = (String) m_wm.getAttribute(weraproductset, "lagerNr");
		if (strLagerNr == null) {
			strLagerNr = "";
		}
		String strArtNr = (String) m_wm.getAttribute(weraproductset, "artnr");
		if (strArtNr == null) {
			strArtNr = "";
		}
		strCode = strLagerNr + strArtNr + strVarDE;
		bNeu = (Boolean) m_wm.getAttribute(weraproductset, "produkt_neu");
		if (bNeu == null) {
			bNeu = new Boolean(false);
		}
		bSB_faehig = (Boolean) m_wm.getAttribute(weraproductset, "artikel_sb_faehig");
		if (bSB_faehig == null) {
			bSB_faehig = new Boolean(false);
		}
		if (weraproductsetinset != null) {
			// --- SB-Produkt
			bSB_faehig = new Boolean(true);
		}

		bAuslauf = (Boolean) m_wm.getAttribute(weraproductset, "artikel_auslauf");
		if (bAuslauf == null) {
			bAuslauf = new Boolean(false);
		}
		if (bAuslauf.booleanValue()) // --- Auslaufartikel sind nie neu!!
		{
			bNeu = new Boolean(false);
		}
		strEAN = (String) m_wm.getAttribute(weraproductset, "ean");
		if (strEAN == null || strEAN.length() == 0) {
			strEAN = "";
		}

		String strPK_SIS = "";
		if (weraproductsetinset != null) {
			strPK_SIS = (String) weraproductsetinset.getPK().toString();
		}

		// --- Artikelnummer / Beschreibung ----------------------
		String strArtikelNr = "";
		if (weraproductsetinset != null) {
			// --- ArtNr vom Hauptprodukt verwenden
			strArtikelNr = weraproductsetinset.getCode();

			// --- Beschreibung
			strBesch = weraproductsetinset.getName();
		} else {
			strArtikelNr = weraproductset.getCode();

			// --- Beschreibung
			strBesch = weraproductset.getName();
		}
		if (strBesch == null) {
			strBesch = "";
		}
		strBesch = strBesch.replaceAll("\"", " ");
		// --- Artikelnummer / Beschreibung ----------------------

		strLine = "\"" + weraproductset.getPK() + "\"\t\"" + strPK_SIS + "\"\t\"" + strArtikelNr + "\"\t\"" + "'"
				+ strCode.replace("\"", "''") + "'" + "\"\t\"" + strBesch.replace("\"", "''") + "\"\t\"" + "" + "\"\t\""
				+ strPageNr + "\"\t\"" + bNeu.toString() + "\"\t\"" + bAuslauf.toString() + "\"\t\"" + strEAN + "\"\t\"" + strLanguage + "\"\t\""
				+ strPreisInfo + "\"\t\"" + oVPE + "\"\t\"" + strSortKey + "\"\t" + strCategorie
				+ "\t\"" + strAbtrieb + "\"\t\"" + "'" + strVarDE + "'" + "\"\t\"" + "'" + strVarUS + "'" + "\"\t"
				+ strInhalt + "\t" + strImageName + ".jpg" + "\t" + bSB_faehig.toString();

		return strLine;
	}

	/**
	 * 
	 * @param oProductSet
	 * @return 
	 */
	protected String _getSetContent(final Product oProductSet) {
		// TODO Auto-generated method stub

		// --- Initialize
		String strInhalt = "";
		HashMap oHashMapArtikel = new HashMap();
		ArrayList aContent = new ArrayList();
		int iPos = 0;

		// --- Hole Satzinhalt
		aContent = ((WeraProductSet) oProductSet).generateWeraProductSetData();

		// --- Schleife �ber alle Content-Inhalte
		if (aContent != null && aContent.size() > 0) {
			for (final Iterator it1 = aContent.iterator(); it1.hasNext();) {

				// --- Hole Map
				final HashMap oHashMapProdukt = (HashMap) it1.next();
				iPos++;

				// --- Initialize
				final ArrayList colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
				strInhalt += (String) oHashMapProdukt.get("code") + " ";
				if (colHash != null && colHash.size() > 0) {
					int iPos1 = 0;
					for (final Iterator it2 = colHash.iterator(); it2.hasNext();) {
						// --- Hole Map
						iPos1++;
						oHashMapArtikel = (HashMap) it2.next();
						if (oHashMapArtikel != null) {
							if (iPos1 < colHash.size()) {
								strInhalt += "" + oHashMapArtikel.get("value") + "; ";
							} else {
								strInhalt += "" + oHashMapArtikel.get("value") + "; ";
							}
						}
					}
				}

				// --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
				if ( m_strLanguage.equals("en") || m_strLanguage.equals("us-en")) {
					strInhalt = strInhalt.replace(",", ".");
				} else {
					strInhalt = strInhalt.replace(".", ",");
				}

				// --- Content korrigieren
				if (strInhalt.length() > 2) {
					strInhalt = strInhalt.trim();
				}

			} // --- for ( Iterator it1 = aContent.iterator(); it1.hasNext(); ) {

		} // --- if ( aContent != null && aContent.size() > 0 ) {

		// --- Anf�hrungszeichen entfernen
		strInhalt = strInhalt.replaceAll("'", "");
		//strInhalt = strInhalt.replace( "\"", "\u0093" );

		return strInhalt;
	}


	/**
	 * _getPriceInfo
	 *
	 * @param Product product
	 * @param String strPriceListe
	 * @param int iAnzahlSollNettoPrices
	 * @param strDelim
	 * @return 
	 */
	public String _getPriceInfo(final Product product, final String strPriceListe, final int iAnzahlSollNettoPrices,
			final String strDelim) {
		// TODO Auto-generated method stub
		String strPriceInfo = "";
		int iPricePos = 0;
		Boolean bIsEVK = null;
		Boolean bAufAnfrage = null;
		final Boolean bIsEVKDef = new Boolean(false);
		Double dBruttoPrice = null;
		Double dPrice = null;
		String strBruttoPrice = "";
		String strUnit = "";
		String strPriceValue = "";

		// --- Setze W�hrung
		m_strWaehrung = "EUR";
		strPriceInfo += m_strWaehrung + "\"" + strDelim;

		// --- Hole nur die Preise die Exportiert werden sollen
		ArrayList prices = new ArrayList();
		if (product != null) {
			prices = (ArrayList) _aGetPriceList(product, strPriceListe);
		}

		if (prices != null && prices.size() > 0) {

			// --- Initialize
			PriceRow pricerow = null;

			for (final Iterator it3 = prices.iterator(); it3.hasNext();) {

				// --- GetIt
				pricerow = (PriceRow) it3.next();

				// --- W�hrung
				final String strSymbol = pricerow.getCurrency().getSymbol().toString();
				final String strIsoCode = pricerow.getCurrency().getIsoCode().toString();

				// --- ist der Preis EVK?
				bIsEVK = (Boolean) m_wm.getAttribute(pricerow, "evk");
				if (bIsEVK == null) {
					bIsEVK = bIsEVKDef;
				}

				// --- Preis auf Anfrage
				bAufAnfrage = (Boolean) m_wm.getAttribute(pricerow, "aufanfrage");
				//LOG.info("bAufAnfrage.1="+bAufAnfrage);
				if (bAufAnfrage == null) {
					bAufAnfrage = new Boolean(false);
					//LOG.info("bAufAnfrage.2="+bAufAnfrage);
				}

				if (dBruttoPrice == null && (!pricerow.isNet() || bIsEVK.booleanValue())) {
					// --- Preis auf Anfrage? 
					if (bAufAnfrage.booleanValue() == false) {
						dBruttoPrice = new Double(pricerow.getPrice());
						strBruttoPrice = "";
						if (dBruttoPrice != null && dBruttoPrice.doubleValue() != 0) {
							strBruttoPrice = _strFormatPrice(dBruttoPrice);
						}

						//  --- Unit
						if (pricerow.getMinQuantity() != 0) {
							strUnit = new Long(pricerow.getMinQuantity()).toString();
						}
					} else {
						strBruttoPrice = "a.A.";
						//LOG.info("strBruttoPrice="+strBruttoPrice);
					}
				} else {
					// --- inc PriceCounter
					iPricePos++;

					// --- Initialie
					strPriceValue = "";
					strUnit = "";

					// --- Preis auf Anfrage? 
					if (bAufAnfrage.booleanValue() == false) {
						// --- Pricing
						dPrice = new Double(pricerow.getPrice());
						if (dPrice.doubleValue() != 0) {
							strPriceValue = _strFormatPrice(dPrice);
						}

						// --- Unit
						strUnit = "";
						if (pricerow.getMinQuantity() != 0) {
							strUnit = new Long(pricerow.getMinQuantity()).toString();
						}

					} else {
						strPriceValue = "a.A.";
						strUnit = "";
					}

					// --- Hole Order
					Integer iOrder = (Integer) m_wm.getAttribute(pricerow, "order");
					if (iOrder == null) {
						iOrder = new Integer(0);
					}
					if (iOrder.intValue() != (iPricePos * 10)) {
						// --- Leere Preisspalte
						int iPricePosTmp = iPricePos * 10;
						if (iPricePosTmp < iOrder.intValue()) {
							for (; iPricePosTmp < iOrder.intValue(); iPricePosTmp += 10) {
								// --- Leere Preisspalten
								strPriceInfo += "\"" + "\"" + strDelim + "\"" + "\"" + strDelim;
							}
						}
						iPricePos = iPricePosTmp / 10;
					}

					// --- Preis n
					strPriceInfo += "\"" + strUnit + "\"" + strDelim + "\"" + strPriceValue + "\"" + strDelim;
				}
			}
		}

		// --- Auff�llen  von Dummy-Prices Spalten
		for (; iPricePos < iAnzahlSollNettoPrices; iPricePos++) {

			// --- Preis n
			strPriceInfo += "\"\"" + strDelim + "\"\"" + strDelim;
		}

		// --- Preis (Brutto) - PRBRUTTO
		strPriceInfo += "\"" + strBruttoPrice;

		return strPriceInfo;
	}

	/**
	 * Ausgabe einer XML-Datei aller Produkte / Artikel mit Preisen (Inbetween)
	 * @return 
	 */
	public Collection strGetAllPriceLists() {

		// --- Auswahlwert
		final EnumerationManager em = m_jaloSession.getEnumerationManager();
		EnumerationType et = null;
		final EnumerationValue ev = null;
		et = em.getEnumerationType("UserPriceGroup");

		return et.getValues();
	}

	/**
	 * 
	 * @return 
	 */
	public String strExportCSV() {
		// --- Initialize
		final int iProductCounter = 0;
		final int iArticleCounter = 0;
		String strResult = "";
		String strPageNr = "";
		String strOutput = "";
		String strLine = "";
		final String strFN = "strExportPriceListXML ==> ";
		Collection productsSorted = null;
		final Collection articles = null;
		final String strOutputFile = Config.getParameter("wera.exportpath") + "imagelist.csv";

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		weraCatalogVersion = m_wm.getCSV(Config.getParameter("wera.mastercatalog"), Config
				.getParameter("wera.mastercatalogversion"));

		// --- Initialisieren der XML-Struktur
		// --- Setze Sprache, und Defaultsprache=de
		initLanguage("de");

		// --- Alle alle Produkte sortiert nach Produktnummer
		strOutput = "Hole sortierte Produktliste (" + m_strLanguage + ") ...";
		LOG.info(strOutput);
		strResult = strOutput + "<br>";
		productsSorted = WeraProduct.getAllProductsFromCatalog4PriceList(weraCatalogVersion, true, "code");

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(m_strLanguage);

		// --- �ffnen der Ausgabedatei
		FileWriter fw = null;
		try {
			fw = new FileWriter(strOutputFile);

			if (fw != null) {

				// --- Schreibe �berschrift
				strLine = "\"ArtikelNr" + "\";\"Produktbild" + "\";\"KS" + "\";\"Produktbeschreibung" + "\"\r\n";
				fw.write(strLine);

				// --- Schleife �ber alle Produkte
				strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
				strResult += strOutput + "<br>";
				for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
					// --- Hole Produkt
					m_product = (Product) it1.next();
					if (m_product != null) {

						// --- Hole die SeitenNr
						strPageNr = (String) m_wm.getAttribute(m_product, "pagenr");
						if (strPageNr == null) {
							strPageNr = "";
						}

						// --- Baue die Zeile zusammen
						strLine = "\"" + m_product.getCode() + "\";\""
								+ WeraProduct.s_normalizeFilenameForImageLookup(m_product.getCode()) + "\";\"" + strPageNr + "\";\""
								+ m_product.getName() + "\"\r\n";
					}
					fw.write(strLine);
				}

				fw.close();
			}

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strResult;
	}

	/**
	 * ExportFormater Pricelist (XML4IB)
	 */
	class ExportFormatterPL {

		public boolean m_bIsIB_Exporter = true;

		/**
		 * 
		 * @param strLanguage
		 * @param bProduktNeu
		 * @return 
		 */
		String strGetNew(final String strLanguage, Boolean bProduktNeu) {

			// --- Initialize
			String cNeu = "";
			if (bProduktNeu == null) {
				bProduktNeu = new Boolean(false);
			}

			if (bProduktNeu.booleanValue()) {
				cNeu = "*NEU / NEW*";
				if (strLanguage.equals("fr")) {
					cNeu = "*nouveau*";
				}
				if (strLanguage.equals("it")) {
					cNeu = "*nuovo*";
				}
/////////////////////////// 
// entfernt template-optimierung					
//				cNeu = "<CharStyle:pl\\_wera\\_new><cSkew:10.000000>" + cNeu + "<cSkew:><CharStyle:>";
/////////////////////////// 
//				cNeu = "<CharStyle:wera\\_produktbez><cColor:C\\=0 M\\=100 Y\\=100 K\\=0><cSkew:10.000000> " + cNeu
//						+ "<cSkew:><cColor:><CharStyle:>";
			} else {
				cNeu = "";
			}

			// --- Kein Neu-Flag mehr ausgeben
			cNeu = "";
			return cNeu;
		}

		/**
		 * 
		 * @param strLanguage
		 * @param bProduktAuslauf
		 * @return 
		 */
		String strGetAuslaufartikel(final String strLanguage, Boolean bProduktAuslauf) {

			// --- Initialize
			String cText = "";
			if (bProduktAuslauf == null) {
				bProduktAuslauf = new Boolean(false);
			}

			if (bProduktAuslauf.booleanValue()) {
				cText = " *Auslaufartikel / discontinued item*";
				if (strLanguage.equals("fr")) {
					cText = "*discontinued item(fr)*";
				}
				if (strLanguage.equals("it")) {
					cText = "*f. es.*";
				}
/////////////////////////// 
// entfernt template-optimierung					
//				cText = "<CharStyle:pl\\_wera\\_new><cSkew:10.000000>" + cText + "<cSkew:><CharStyle:>";
				cText = cText;
/////////////////////////// 
			}

			return cText;
		}

		/**
		 * 
		 * @param strEANraw
		 * @return 
		 */
		public String strGetEAN(final String strEANraw) {
			// TODO Auto-generated method stub
			return strEANraw;
/*			
			String strEAN = "<ASCII-WIN>\r\n<Version:4><ParaStyle:wera\\_ean>";
			strEAN += "<CharStyle:pl_wera\\_ean><cSkew:10.000000>";
			strEAN += strEANraw;
			strEAN += "<cSkew:>";
			strEAN += "<CharStyle:>";

			return strEAN;
*/			
		}

		/**
		 * 
		 * @return 
		 */
		public String strGetParagraph() {
/////////////////////////// 
// entfernt template-optimierung					
			//return "<ParaStyle:links ohne Einzug>";
			return "";
/////////////////////////// 
		}

		/**
		 * 
		 * @return 
		 */
		public String strGetParagraphCodeNr() {
/////////////////////////// 
// entfernt template-optimierung					
//			return "<ParaStyle:pl\\_inhalt\\_codenr>";
/////////////////////////// 
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		public String strGetTagVersion() {
/////////////////////////// 
// entfernt template-optimierung					
//			return "<ASCII-WIN>\r\n<Version:4>";
/////////////////////////// 
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		public String getBeginNoBreak() {
/////////////////////////// 
// entfernt template-optimierung					
			//return "<CharStyle:wera\\_tabelle><cNoBreak:1>";
//			return "<CharStyle:Tabelle Inhalt linksb�ndig>";
/////////////////////////// 
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		public String getEndNoBreak() {
/////////////////////////// 
// entfernt template-optimierung					
//			return "<CharStyle:>";
/////////////////////////// 
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		public String getBeginSup() {
			return ""; //##sub##";
		}

		/**
		 * 
		 * @return 
		 */
		public String getEndSup() {
			return ""; // "##e_sub##";
		}

		/**
		 * 
		 * @param strTagName
		 * @param weramedia
		 * @param strPrio
		 * @param strDirectory
		 * @param strCodeNrProduct
		 * @return 
		 */
		public Element createBildElement(final String strTagName, final WeraMedia weramedia, final String strPrio,
				final String strDirectory, final String strCodeNrProduct) {
			// TODO Auto-generated method stub
			return null;
		}
	}

	/**
	 * ExportFormater Pricelist (XML4FO)
	 */
	class ExportFormatterPL4FO extends ExportFormatterPL {

		public boolean m_bIsIB_Exporter = false;

		/**
		 * 
		 * @param strLanguage
		 * @param bProduktNeu
		 * @return 
		 */
		@Override
		String strGetNew(final String strLanguage, Boolean bProduktNeu) {

			// --- Initialize
			String cNeu = "";
			if (bProduktNeu == null) {
				bProduktNeu = new Boolean(false);
			}

			if (bProduktNeu.booleanValue()) {
				if (strLanguage.equals("fr")) {
					cNeu = "*nouveau*";
				} else {
					cNeu = "*NEU / NEW*";
				}
			} else {
				cNeu = "";
			}

			return cNeu;
		}

		/**
		 * 
		 * @param strEANraw
		 * @return 
		 */
		@Override
		public String strGetEAN(final String strEANraw) {
			// TODO Auto-generated method stub

			final String strEAN = strEANraw;

			return strEAN;
		}

		/**
		 * 
		 * @return 
		 */
		@Override
		public String strGetParagraph() {
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		@Override
		public String strGetTagVersion() {
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		@Override
		public String getBeginNoBreak() {
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		@Override
		public String getEndNoBreak() {
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		@Override
		public String getBeginSup() {
			return "";
		}

		/**
		 * 
		 * @return 
		 */
		@Override
		public String getEndSup() {
			return "";
		}

		/**
		 * 
		 * @param strTagName
		 * @param weramedia
		 * @param strPrio
		 * @param strDirectory
		 * @param strCodeNrProduct
		 * @return 
		 */
		@Override
		public Element createBildElement(String strTagName, final WeraMedia weramedia, final String strPrio,
				String strDirectory, String strCodeNrProduct) {

			// --- Initialize
			String strCode = "";
			String strRealName = "";
			strDirectory = Config.getParameter("media.replication.dirs");

			// ---  Validate
			if (strTagName == null) {
				strTagName = "";
			}

			// --- <File-List>
			final Element oFileList = new Element("File-List");
			final Element oFile = new Element("File");
			if (weramedia != null) {

				//oFile.setAttribute("PrintMimeType", "image/eps" );
				strCode = weramedia.getCode();
				strRealName = weramedia.getFileName();
				if (strRealName != null) {
					strRealName = strRealName.replace("\\", "/");
					final Pattern p = Pattern.compile("/");
					final String[] aRealName = p.split(strRealName);
					if (aRealName.length > 1) {
						strRealName = aRealName[aRealName.length - 1];
					}
					strRealName = strRealName.replaceAll(".ps", ".jpg");
					strRealName = strRealName.replaceAll(".eps", ".jpg");

					// --- Suche
					if ((new File(strDirectory + "/" + strRealName)).exists() == false) {
						strRealName = strRealName.replaceAll(".gif", ".jpg");
						if ((new File(strDirectory + "/" + strRealName)).exists() == false) {
							strRealName = "";
						}
					}
				}

				// --- Korrektur - Alle Grafiken aus einem Bilderordner
				strDirectory = "pictures";

				oFile.setAttribute("PrintFileName", strDirectory + "/" + strRealName);
			} else {
				// --- Korrektur - Alle Grafiken aus einem Bilderordner
				strDirectory = "pictures";

				// --- Bereinige ProduktNr f�r Bildsuche
				if (!strCodeNrProduct.equals("")) {

					strCodeNrProduct = WeraProduct.s_normalizeFilenameForImageLookup(strCodeNrProduct);
					oFile.setAttribute("PrintFileName", strDirectory + "/" + strCodeNrProduct + ".eps");
				} else {
					m_iOffsetIDEbene3++;
					oFile.setAttribute("PrintFileName", strDirectory + "/" + "test_trans.eps");
				}
			}

			// --- Init Motiv
			final Element oMotive = new Element("Motive");
			oMotive.setAttribute("Id", strCode);
			oMotive.setAttribute("TypeName", strTagName);
			oMotive.setAttribute("Sequence", strPrio);

			// --- Setze zusammen
			oFileList.addContent(oFile);
			oMotive.addContent(oFileList);

			return oMotive;
		}
	}

	/**
	 * 
	 * @param strCatalog
	 * @param strRefcatalog
	 * @param Collection<String> colPriceLists
	 * @return 
	 */
	public String strExportPriceListXML4IB(final String strCatalog, final String strRefcatalog, Collection<String> colPriceLists) {

		// --- Exportformater initialisieren
		m_ExportFormatterPL = new ExportFormatterPL();

		// --- Ausgabe starten
		return _strExportPriceListXML(strCatalog, strRefcatalog, colPriceLists);
	}

	/**
	 * 
	 * @param strCatalog
	 * @param strRefcatalog
	 * @param strPriceList
	 * @return 
	 */
	public String strExportPriceListXML4FO(final String strCatalog, final String strRefcatalog, final String strPriceList) {

		// --- Exportformater initialisieren
		m_ExportFormatterPL = new ExportFormatterPL4FO();
		m_ExportFormatterPL.m_bIsIB_Exporter = false;
/*
		// --- Ausgabe starten
		//final String strPath = _strExportPriceListXML(strCatalog, strRefcatalog, strPriceList);

		// --- Erzeugen der Shell-Scripte
		GenerateBatch(strPath, "_preisliste");

		// --- Starten der Shell-Scripte
		StartBatch();

		*/

		return ""; //strPath;
	}

	/**
	 * Starten der Shell-Scripte
	 */
	public void StartBatch() {

		final XmlSupport oXmlSupport = new XmlSupport();
		oXmlSupport.startCmdFile(m_strControlOSPath + "generateall.cmd");
	}

	/**
	 * Erzeugen der Shell-Scripte
	 * @param strPath
	 * @param strVersion 
	 */
	public void GenerateBatch(final String strPath, final String strVersion) {

		// --- Tempor�r
		//m_strControlPath = "c:/home/hybris/export/website/20080425_151831/control/";
		//m_strDataPath = "c:/home/hybris/export/website/20080425_151831/data/";
		m_strOutputPath = "x:/website/hybris_ausgabe_webseite/entwicklung/templates/test.co-de.de/";

		// --- Variablen Initialisieren
		String strCRLF = "";
		final ArrayList aBatchFile = new ArrayList();
		final String strLanguage = "";
		String strLine = "";
		final XmlSupport oXmlSupport = new XmlSupport();

		// --- Templatespfade f�r Windows / Linux initialisieren
		if (Config.getParameter("wera.os").equals("linux")) {
			strCRLF = "\n";
			m_strControlOSPath = Config.getParameter("wera.homepath") + "/export/preisliste/control/templates-linux" + strVersion
					+ "/";
			m_strOutputPath = strPath;
		} else {
			strCRLF = "\r\n";
			m_strControlOSPath = Config.getParameter("wera.homepath") + "/export/preisliste/control/templates-windows" + strVersion
					+ "/";
			m_strOutputPath = strPath;
		}
		// --- Scripttemplate Datei �ffnen und einlesen
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(m_strControlOSPath + "batchfile.txt")));

			String strCode = "";
			try {
				while ((strCode = in.readLine()) != null) {
					aBatchFile.add(strCode);
					LOG.info("strCode=" + strCode);
				}
				in.close();

				// --- Hauptdatei zum generieren aller Scripte
				final FileWriter hScript = new FileWriter(m_strControlOSPath + "generateall.cmd");
				if (Config.getParameter("wera.os").equals("linux")) {
					hScript.write("#!/bin/sh" + strCRLF);
					hScript.write("#" + strCRLF);
					hScript.write("" + strCRLF);
				}

				// --- Languagescript registrieren
				hScript.write(m_strControlOSPath + "generate.cmd" + strCRLF);

				// --- Erzeugen der Batch-Scripts
				final FileWriter hScriptLang = new FileWriter(m_strControlOSPath + "generate.cmd");
				if (hScriptLang != null) {

					// --- Schleife �ber Scripttemplate
					for (final Iterator itBatch = aBatchFile.iterator(); itBatch.hasNext();) {
						strLine = (String) itBatch.next();

						// --- Platzhalter ersetzen
						strLine = strBatchReplacement(strLine, strLanguage);

						// --- Zeile schreiben
						hScriptLang.write(strLine + strCRLF);
					}

					// --- Datei schliessen und ggf. als ausf�hrbar freigeben
					hScriptLang.close();
					if (Config.getParameter("wera.os").equals("linux")) {
						oXmlSupport.startCmdFile("dos2unix " + m_strControlOSPath + "generate.cmd");
						oXmlSupport.startCmdFile("chmod a+x " + m_strControlOSPath + "generate.cmd");
					}
				}

				// --- Datei schliessen und ggf. als ausf�hrbar freigeben
				hScript.close();
				if (Config.getParameter("wera.os").equals("linux")) {
					oXmlSupport.startCmdFile("dos2unix " + m_strControlOSPath + "generateall.cmd");
					oXmlSupport.startCmdFile("chmod a+x " + m_strControlOSPath + "generateall.cmd");
				}

			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Aufr�umen
		aBatchFile.clear();
	}

	/**
	 * 
	 * @param strLine
	 * @param strLanguage
	 * @return 
	 */
	private String strBatchReplacement(String strLine, final String strLanguage) {
		// TODO Auto-generated method stub

		String strControlPath = m_strControlPath;
		String strDataPath = m_strDataPath;

		if (Config.getParameter("wera.os").equals("windows")) {
			strControlPath = strControlPath.replaceAll("/", "\\\\");
			strDataPath = strDataPath.replaceAll("/", "\\\\");
		}

		strLine = strLine.replaceAll("##param_language##", strLanguage);

		if (strLanguage.equals("us-en")) {
			strLine = strLine.replaceAll("##param_languagedir##", "us/");
		} else {
			strLine = strLine.replaceAll("##param_languagedir##", strLanguage + "/");
		}

		strLine = strLine.replace("##param_outputpath##", m_strOutputPath);
		strLine = strLine.replace("##param_path_xmlfiles##", m_strOutputPath);
		strLine = strLine.replace("##param_xmlfile##", m_strXML_File);
		strLine = strLine.replace("##param_path_controllfiles##", strControlPath);
		strLine = strLine.replace("##param_outputfile##", m_strPriceList);

		return strLine;
	}

	/**
	 * filter varianten - filter auslaufartikel
	 * 25.06.2021 - neue Regel Gültigkeit Datum von / Datum bis
	 *
	 * @param WeraVariante
	 * @return boolean
	 */
	protected boolean _isFilteredVariante(WeraVariante oWeraVariante) {

		// --- prüfe ob der produkt exportiert werden darf (neue regel, datum von / bis)
		if ( m_wm.getVisibilityForCatalog( oWeraVariante, weraCatalogVersion, false ) == -1 ) {

			String strTmpCode = (String) m_wm.getAttribute(oWeraVariante, "code");
			System.out.println( strTmpCode + ", skipped, product not allowed for current catalogversion");
			return true;
		}

		/*
		 O-Ton RH - Mail vom 18.02.2015
            
		 => Ab M�rz 2016 <=
		 Webkataloge, Printkataloge, Preislisten, BMEcats beinhalten nur noch aktive/g�ltige Varianten, Produkte, S�tze.
		 Dann ist das Flag �Auslaufartikel� irrrelevant (bis es wieder gebraucht wird).
		 */
/*
		Boolean bVarianteAuslauf = (Boolean) m_wm.getAttribute(oWeraVariante, "artikel_auslauf");
		if (bVarianteAuslauf == null) {
			bVarianteAuslauf = new Boolean(false);
		}

		return bVarianteAuslauf.booleanValue();
*/		
/*
Mail vom 12.11.2019 - RH
ja, das sollten wir f�r die Staffel-Preislisten 2020 �ndern.
Auslaufartikel werden 2020 mitausgegeben; mit der in hybris4 zugeordneten Fu�note!

P.S. F�r den Kompaktkatalog ist die Ausgabemechanik eine andere. Diese Artikel werden gar nicht in weralive aktiv gestellt.
*/
		return false;
	}

	/**
	 * 
	 * @param strCatalog
	 * @param strRefcatalog
	 * @param Collection<String> colPriceLists
	 * @return 
	 */
	public String _strExportPriceListXML(final String strCatalog, String strRefcatalog, Collection<String> colPriceLists) {

		// --- Initialize
		m_strCatalogversion = strCatalog;
		HashMap hashmapValues = null;
		int iProductCounter = 0;
		final int iArticleCounter = 0;
		String strResult = "";
		String strOutput = "";
		final String strFN = "strExportPriceListXML ==> ";
		Collection productsSorted = null;
		final Collection articles = null;

		// --- Datum ermitteln
		final String strFileDatum = m_wm.InitOutputDatum();

		// --- kontroll-CSV
		m_kontrollCSV	= (Collection<String>) new ArrayList();

		// --- Preisliste
		m_strPriceList = colPriceLists.iterator().next();
		m_pricelists	= (Collection<String>) new ArrayList();
		m_pricelists.addAll(colPriceLists);

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		strRefcatalog	= "print_2018";
		weraCatalogVersion = m_wm.getCatalogVersion(m_strCatalogPriceliste, strCatalog);
		m_weraRefCatalogVersion = m_wm.getCatalogVersion(m_strCatalogPrint, "print_2018" );

/*
		// --- Holen der Sprache
		final Collection languages = weraCatalogVersion.getLanguages();
		if (languages != null && languages.size() > 0) {
			final Language lang = (Language) languages.iterator().next();
			m_strLanguage = lang.getIsoCode();
		} else {
			return "Keine Sprache zugeordnet.";
		}

		// --- Hole Sprache
		m_strExportLanguage = m_strLanguage;
*/
		// --- Holen der Sprache ---------------------------------------------------------------------------------------------
		// --- Hole Sprache (default)
		m_strLanguage = m_strExportLanguage = "de"; // "de"; // "en"; // "de";
		m_languages	= new ArrayList();
		final Collection<Language> languages = weraCatalogVersion.getLanguages();
		String strLanguage;
		if (languages == null || languages.size() == 0) {
			return "Keine Sprache zugeordnet.";
		}
		m_languages.add(m_strLanguage);
		for (Language lang : languages ) {

			strLanguage = lang.getIsoCode();
			if ( !strLanguage.equals(m_strLanguage)) {
				m_languages.add(strLanguage);
			}
		}

		// --- Hole Sprache
		m_strExportLanguage = m_strLanguage;
		super.m_strExportLanguage = m_strLanguage;
		// --- Holen der Sprache ---------------------------------------------------------------------------------------------


		// --- Erzeuge die Export-Ablage
		final String strOutputFilePath = CreateOutputPath(strFileDatum, m_strLanguage);

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(m_strLanguage);
		LOG.info("Sprache=" + m_strExportLanguage);

		// --- Setze DTD-Info
		initDTD("MediaXML", "MEDIANDO.dtd");

		// --- Initialisieren der XML-Struktur
		if (bInitMediando(strCatalog)) {

			// --- header für kontrolldatei
			// --- headerzeile
			String strKontrollHeaderLine	= "Code\tVPE\tEAN";
			for (String strPricelist: m_pricelists) {
				strKontrollHeaderLine += "\t" + strPricelist + "~Menge1";
				strKontrollHeaderLine += "\t" + strPricelist + "~Preis1";
				strKontrollHeaderLine += "\t" + strPricelist + "~Menge2";
				strKontrollHeaderLine += "\t" + strPricelist + "~Preis2";
				strKontrollHeaderLine += "\t" + strPricelist + "~Menge3";
				strKontrollHeaderLine += "\t" + strPricelist + "~Preis3";
				strKontrollHeaderLine += "\t" + strPricelist + "~Menge4";
				strKontrollHeaderLine += "\t" + strPricelist + "~Preis4";
				strKontrollHeaderLine += "\t" + strPricelist + "~Menge5";
				strKontrollHeaderLine += "\t" + strPricelist + "~Preis5";
				strKontrollHeaderLine += "\t" + strPricelist + "~Brutto";
			}
			m_kontrollCSV.add(strKontrollHeaderLine);

			try {
				// --- Setze Sprache, und Defaultsprache=de
				initLanguage(m_strExportLanguage);

				// --- Alle alle Produkte sortiert nach Produktnummer
				final WeraKatalog wk = new WeraKatalog();
				strOutput = "Hole sortierte Produktliste (" + m_strExportLanguage + ") ...";
				LOG.info(strFN + strOutput);
				strResult += strOutput + "<br>";
				//productsSorted = WeraProduct.getAllProductsFromCatalog(weraCatalogVersion, true, "code" );
				//productsSorted = WeraProduct.getAllProductsFromCatalog4PriceList(weraCatalogVersion, true, strSortBy );
				//productsSorted = WeraProduct.getAllProductsFromCatalog4PriceList(weraCatalogVersion, true, strSortBy );
				// productsSorted = wk.getProductsFromPreisliste(strCatalog, strRefcatalog, 2);
				productsSorted = wk.getProductsFromPreisliste_v3(strCatalog, strRefcatalog, m_strCatalogPrint, m_strCatalogPriceliste, 2);

				// --- Analysiert die Preise der Preisliste und gibt die maximale Anzahl zur�ck
				m_iMaxPrices = _analyzePrices(productsSorted);

				// --- Setze Sprache, und Defaultsprache=de
				initLanguage(m_strExportLanguage);

				// --- Schleife �ber alle Produkte
				m_strXML_File = m_strCatalogPriceliste + "_" + m_strPriceList + ".xml";
				strOutput = "Ausgabe der Produkte nach:" + strOutputFilePath + "/" + m_strXML_File;
				//m_strPriceList = colPriceLists.get(0);;
				LOG.info(strFN + strOutput);
				strResult += strOutput + "<br>";
				for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
					hashmapValues = (HashMap) it1.next();
					// --- Hole Produkt
					m_product = (Product) hashmapValues.get("product");


					///////////////////////////////////////////////////////////////////////////
					// Seitennummer Platzhalter
					///////////////////////////////////////////////////////////////////////////
					String strCatalogPages = "";
					if ( m_platzhalterSeitennummer ) {

						// --- platzhalter f�r Katalogseiten (nachtr�gliches einf�gen) -------------------------------------
						strCatalogPages = "##KS_CODE_KS##";
						// --- platzhalter f�r Katalogseiten (nachtr�gliches einf�gen) -------------------------------------
						
					} else if ( m_keineSeitennummern ) {

						// --- keine Katalogseiten -------------------------------------------------------------------------
						strCatalogPages = "";
						// --- Katalogseiten aus Hybris --------------------------------------------------------------------

					} else {

						// --- Katalogseiten aus Hybris --------------------------------------------------------------------
						strCatalogPages = (String) hashmapValues.get("pages");
						// --- Katalogseiten aus Hybris --------------------------------------------------------------------
					}


					LOG.info("CODE=" + m_product.getCode() + ", Seite=" + hashmapValues.get("pages"));

					if (m_product != null) {

						// --- SB f�hig (J/N)
						Boolean bSB_faehig = new Boolean(false);
						bSB_faehig = (Boolean) m_wm.getAttribute(m_product, "artikel_sb_faehig");
						if (bSB_faehig == null) {
							bSB_faehig = new Boolean(false);
						}

						if (m_product instanceof WeraProduct || m_product instanceof WeraProductSet) {
							// --- Ist das Produkt aktiv?
							//if ( m_product != null && (((WeraProduct)m_product).IsAktiv() || ((WeraProduct)m_product).bHasPrices ())) {
							//if ( m_product != null && ((WeraProduct) m_product).bIsAkitvPriceList() ) {

							// --- Produtcounter
							iProductCounter++;

							// --- Init Produkt (keine SB-Variante, nicht letztes Produkt)
							_InitializeProductLinkXML(strCatalogPages, false, false, bSB_faehig);

							// --- pr�fe SB-Varianten -----------------------------------------------------------
							if (!(m_product instanceof WeraProductSetinSet)) {

								Collection<WeraProductSetinSet> colSB_Variants = (Collection<WeraProductSetinSet>) m_wm.getAttribute(m_product, "weraproductsetinsets_relation");
								if (colSB_Variants.size() > 0) {

									LOG.info("_strExportPriceListXML m_product.code=" + m_product.getCode());

									// --- iterate on all
									int iCounter = 1;
									for (WeraProductSetinSet oWeraProductSetinSet : colSB_Variants) {

										LOG.info("_strExportPriceListXML oWeraProductSetinSet.code=" + oWeraProductSetinSet.getCode());
										m_product = oWeraProductSetinSet;
										if (colSB_Variants.size() == iCounter) {
											// --- letzte variante
											_InitializeProductLinkXML(strCatalogPages, true, true, false);
										} else {
											// --- 1. - n.Variante
											_InitializeProductLinkXML(strCatalogPages, true, false, false);
										}

										// --- increment counter
										iCounter++;

									} // --- for ( WeraProductSetInSet oWeraProductSetInSet : colSB_Variants ) {

								} // --- if ( colSB_Variants.size() > 0 ) {

							} // --- if ( !(m_product instanceof WeraProductSetinSet) ) {
							// --- pr�fe SB-Varianten -----------------------------------------------------------

						} // --- if (m_product instanceof WeraProduct || m_product instanceof WeraProductSet)

					} // --- if (m_product != null) {

				} // --- for ( Iterator it1 = productsSorted.iterator(); it1.hasNext(); ) {

				// --- Zusammenfassung
				Comment dummyComment = new Comment(" Anzahl Produkte: " + iProductCounter);
				m_rootElement.addContent(dummyComment);
				dummyComment = new Comment(" Anzahl Artikel: " + iArticleCounter);
				m_rootElement.addContent(dummyComment);
				dummyComment = new Comment(" Sprache: " + m_strExportLanguage);
				m_rootElement.addContent(dummyComment);

				// --- Schreiben der XML-Datei
				writeDocument(strOutputFilePath, "/" + m_strXML_File);

			} catch (final JaloInvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// --- Aufr�umen
			cleanUp();
		}

		// --- LOG-File
		try {
			// --- �ffnen der LOG-Datei
			String strLine = "";
			final FileWriter m_oFileWriterLog = new FileWriter(strOutputFilePath + "/pricelist.log");
			LOG.info("m_aArrayLog.size()=" + m_aLogDatei.size());
			if (m_oFileWriterLog != null) {

				// ---- Schleife �ber alle Zeilen
				for (final Iterator it1 = m_aLogDatei.iterator(); it1.hasNext();) {
					strLine = (String) it1.next();
					m_oFileWriterLog.write(strLine + "\r\n");
				}

				// --- Schliesen
				m_oFileWriterLog.close();

				// --- Log l�schen
				m_aLogDatei.clear();
			}

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// --- Kontroll-File II
		try {
			// --- �ffnen der LOG-Datei
			String strLine = "";
			final FileWriter m_oFileWriterKontroll = new FileWriter(strOutputFilePath + "/" + m_strXML_File.replaceAll(".xml",".csv") );
			LOG.info("m_kontrollCSV. exportfilename=" + strOutputFilePath + "/" + m_strXML_File.replaceAll(".xml",".csv") );
			LOG.info("m_kontrollCSV.size()=" + m_kontrollCSV.size());
			if (m_oFileWriterKontroll != null) {

				// ---- Schleife �ber alle Zeilen
				for (final Iterator it1 = m_kontrollCSV.iterator(); it1.hasNext();) {
					strLine = (String) it1.next();
					m_oFileWriterKontroll.write(strLine + "\r\n");
				}

				// --- Schliesen
				m_oFileWriterKontroll.close();

				// --- Log l�schen
				m_kontrollCSV.clear();
			}

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- LOG-File II
		try {
			// --- �ffnen der LOG-Datei
			String strLine = "";
			final FileWriter m_oFileWriterLog = new FileWriter(strOutputFilePath+ "/produktlist.log");
			LOG.info("m_aArrayLog.size()=" + m_aLogDatei.size());
			if (m_oFileWriterLog != null) {

				// ---- Schleife �ber alle Zeilen
				for (final Iterator it1 = m_aLogDatei1.iterator(); it1.hasNext();) {
					strLine = (String) it1.next();
					m_oFileWriterLog.write(strLine + "\r\n");
				}

				// --- Schliesen
				m_oFileWriterLog.close();

				// --- Log l�schen
				m_aLogDatei.clear();
			}

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strOutputFilePath;

	}

	/**
	 * gibt ein Produkt aus
	 *
	 * @param strCatalogPages
	 * @param bSBVariante
	 * @param bLastSBVariante
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	private void _InitializeProductLinkXML(final String strCatalogPages, boolean bSBVariante, boolean bLastSBVariante, boolean bSB_faehig) throws JaloInvalidParameterException,
			JaloSecurityException {

		// TODO Auto-generated method stub
		// --- Eindeutige ProduktID
		m_strProduktID = "P" + m_iOffsetIDEbene1;
		m_iOffsetIDEbene1++;

		// --- F�lle LinkListe ( neues Produkt => Categorien )
		// --- <ProductLink LinkedId="3773" LinkedRefCode="U100T601" LinkedType="SET" LinkedWorkflow="OKE_DATEN" LinkedManufacturer="WIHA" LinkedEAN="4010995260156" Sequence="10" Hierarchy="ONLINE_CATALOG_03/04" LinkedName="System 6 Wechselklingen Magnetic Set, 6-tlg." LinkedTypeName="SET" LinkedWorkflowName="OKE_DATEN" LinkedManufacturerDescription="Wiha">
		m_oProductLink = new Element("ProductLink");
		// String strCode = (String) m_wm.getAttribute(m_product, "code");
		String strCode = (String) m_product.getCode();
		if (strCode == null) {
			strCode = m_strProduktID;
		}

		// --- Entferne Warenzeichen
		strCode = corrData_trademarks(strCode);

		m_oProductLink.setAttribute("LinkedRefCode", m_strProduktID);
		m_oProductLink.setAttribute("LinkedId", m_strProduktID);
		//oProductLink.setAttribute( "LinkedWorkflow",               "OK" ); 
		//oProductLink.setAttribute( "LinkedManufacturer",           "WERA" ); 
		//oProductLink.setAttribute( "LinkedEAN",                    "TD" ); 

		final Integer intOrder = (Integer) m_wm.getAttribute(m_product, "order");
		if (intOrder == null) {
			m_oProductLink.setAttribute("Sequence", "1");
		} else {
			m_oProductLink.setAttribute("Sequence", intOrder.toString());
			//oProductLink.setAttribute( "Hierarchy",                     strCatalogversion ); 
		}

		initLanguage(m_strExportLanguage);
		m_strProduktName = (String) m_wm.getAttribute(m_product, "name");
		if (m_strProduktName == null) {
			m_strProduktName = "??";
		}
		// --- Setze Sprache, und Defaultsprache=de
		initLanguage("en");
		m_strProduktNameEN = (String) m_wm.getAttribute(m_product, "name");
		if (m_strProduktNameEN == null) {
			m_strProduktNameEN = "??";
		}
		m_strProduktName = m_strProduktName.trim();
		m_strProduktNameEN = m_strProduktNameEN.trim();
		if (m_strProduktName.toLowerCase().equals(m_strProduktNameEN.toLowerCase()) || !m_strExportLanguage.equals("de")) {
			m_strProduktNameEN = "";
		}
		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(m_strExportLanguage);

		//oProductLink.setAttribute( "LinkedName",                    strName );
		//oProductLink.setAttribute( "LinkedWorkflowName",            "OK" ); 
		//oProductLink.setAttribute( "LinkedManufacturerDescription", "WERA" );
		m_oTreegroupLink.addContent(m_oProductLink);
		String strArtNr = (String) m_wm.getAttribute(m_product, "code");
		Comment nameComment = new Comment(strArtNr + "\t" + m_strProduktName);
		m_oTreegroupLink.addContent(nameComment);

		// --- F�lle ProduktListe ( neues Produkt => Categorien )
		final Element produktXML = new Element("Product");
		m_oProductList.addContent(produktXML);

		// --- Produktname als Kommentar
		nameComment = new Comment(m_strProduktName);
		produktXML.addContent(nameComment);
		Comment nameCommentEN = new Comment(m_strProduktNameEN);
		produktXML.addContent(nameCommentEN);

		// --- pr�fe auf Display
		m_bIstDisplay = null;
		if (m_product instanceof WeraProductSetinSet) {
			m_bIstDisplay = (Boolean) m_wm.getAttribute(m_product, "ist_display");
		}
		if (m_bIstDisplay == null) {
			m_bIstDisplay = new Boolean(false);
		}

		// --- F�lle Produktdaten
		String strTemplate = "";
		strTemplate = _initWeraProductXML(produktXML, m_oProductList, strCatalogPages, bSBVariante, bLastSBVariante);

		// --- Hochstellen des Registerzeichens
/////////////////////////// 
// entfernt template-optimierung					
//		m_strProduktName = m_strProduktName.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
//		m_strProduktNameEN = m_strProduktNameEN.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
/////////////////////////// 

		// --- letzte SB-Variante 
		if (bLastSBVariante) {
			produktXML.setAttribute("lastSB", "1");
		} else {
			produktXML.setAttribute("lastSB", "0");
		}

		// --- SB f�hig (J/N)
		if (bSB_faehig) {
			produktXML.setAttribute("SB", "J");
		} else {
			produktXML.setAttribute("SB", "N");
		}

		// --- Setze Name
		produktXML.setAttribute("Name", m_strProduktName);
		produktXML.setAttribute("NameEN", m_strProduktNameEN);

		// --- Setze Templatetyp
		produktXML.setAttribute("TypeName", strTemplate);
		produktXML.setAttribute("LinkedType", strTemplate);
		m_oProductLink.setAttribute("LinkedTypeName", strTemplate);
		m_oProductLink.setAttribute("LinkedType", strTemplate);

		// --- Fussnoten
		String strFootnote = "";
		final ArrayList colAllFootnotes = new ArrayList();

		// --- Ist es ein Satz in Satz?
		if (m_product instanceof WeraProductSetinSet && m_bIstDisplay.booleanValue() == false) {
			/*			
			((WeraProductSetinSet) m_product).generateWeraProductSetData();
			
			final Collection<WeraMedia> colSetIcons = new ArrayList();
			final Collection<Collection<String>> setData = ((WeraProductSetinSet) m_product).generateOutputCells(colSetIcons);
			// --- Satz in Satz
			for (final Collection<String> colCells : setData) {
					final Iterator<String> itCell = colCells.iterator();
					final String strVariantenNr = itCell.next();
					final String strCodeP   = itCell.next();
					final String strLagerNr = itCell.next();
					final String strTipIcon = itCell.next();
					final String strAntriebsIcon = itCell.next();
					final String strBaseCode = itCell.next();
					final String strVariantInfo = itCell.next();
					final String strEAN = itCell.next();
					final String strContentQuantity = itCell.next();
						LOG.info("++++++strVariantenNr=" + strVariantenNr);
						LOG.info("++strCode=" + strCodeP);
						LOG.info("++strLagerNr=" + strLagerNr);
						LOG.info("++strTipIcon=" + strTipIcon);
						LOG.info("++strAntriebsIcon=" + strAntriebsIcon);
						LOG.info("++strBaseCode=" + strBaseCode);
						LOG.info("++strVariantInfo=" + strVariantInfo);
						LOG.info("++strEAN=" + strEAN);
						LOG.info("++strContentQuantity=" + strContentQuantity);

				// --- WERA-ProduktSatz
				m_article = m_product;
				
				// --- Gibt einen String mit Notizen zur�ck
				_initWeraProductVariante(final ArrayList colAllFootnotes, final ArrayList articles)
			}
			 */

			// --- SB-Darstellung
			Collection colWeraProductSet = (Collection) ((WeraProductSetinSet) m_product).getAttribute("weraproductsetvariants_qual");
			String strCodeTmp = "";
			strCodeTmp = m_product.getCode();

			if (strCodeTmp != null && colWeraProductSet.size() == 1) {

				// --- Hochstellen des Registerzeichens
				strCodeTmp = corrData_trademarks(strCodeTmp);
/////////////////////////// 
// entfernt template-optimierung					
				String strWeraProduktNr = strCodeTmp;
				//String strWeraProduktNr = strCodeTmp.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
				//strWeraProduktNr = strWeraProduktNr.replace("�", "##sub##" + "�" + "##e_sub##");
/////////////////////////// 

				produktXML.setAttribute("WeraProduktNr", corrData_trademarks(strWeraProduktNr));
				
				// --- katalogseiten 
				m_oPageNr = strCatalogPages;
				
				
				if (m_oPageNr == null || m_oPageNr == "") {
					m_oPageNr = new String("-");
				} else {
					m_oPageNr = m_oPageNr.toString();
				}
				produktXML.setAttribute("Katalogseite", (String) m_oPageNr);
				produktXML.setAttribute("IndexArtNr", corrData_trademarks(strCodeTmp) + ", " + (String) m_oPageNr);
			}

			LOG.info("+++++SIS code=" + strCodeTmp);
			for (final Iterator iterPSet = colWeraProductSet.iterator(); iterPSet.hasNext();) {

				// --- Hole ID und erh�he diese
				final Integer oRefId = new Integer(m_iOffsetIDEbene3++);

				// --- Hole den Satz
				final WeraProductSetVariants weraproductsetvariants = (WeraProductSetVariants) iterPSet.next();
				final WeraProductSet weraproductset = (WeraProductSet) weraproductsetvariants.getAttribute("weraproductsets");
				final Integer intContentQuantitySiS = (Integer) weraproductsetvariants.getAttribute("vpe");
				// --- WERA-ProduktSatz
				m_article = weraproductset;
				LOG.info(">>>>>>>>SATZ aus SIS code=" + weraproductset.getCode() + " refID=" + oRefId.intValue());
				_initWeraProductVariante(colAllFootnotes, null, oRefId, intContentQuantitySiS, colWeraProductSet.size(), produktXML );

			} // --- for (final Iterator iterPSet = colWeraProductSet.iterator(); iterPSet.hasNext();) {

			// --- TemplateVersion V2 SETINSET
			// if ( colWeraProductSet.size() > 1 && g_oHashMapCodeNr.size()> 1 && m_bIstDisplay.booleanValue() == false ) {
			if (colWeraProductSet.size() == 1 && m_bIstDisplay.booleanValue() == false) {

				// --- SIS enth�lt nur ein Satz mit jeweils eigener Code / EAN
				// --- darstellung als sb-variante
				if (bSBVariante) {
					// --- Setze Templatetype
					strTemplate = strTemplate.replaceAll("PRODUCTSETINSET", "PRODUCTSETINSET_SBVARIANT_V2");
				} else {
					// --- Setze Templatetype
					strTemplate = strTemplate.replaceAll("PRODUCTSETINSET", "PRODUCTSETINSET_V2");
				}

				// --- f�lle attribute
				produktXML.setAttribute("TypeName", strTemplate);
				produktXML.setAttribute("LinkedType", strTemplate);
				m_oProductLink.setAttribute("LinkedTypeName", strTemplate);
				m_oProductLink.setAttribute("LinkedType", strTemplate);

				// --- SIS enth�lt nur ein Satz mit jeweils eigener Code / EAN
			} // --- if ( colWeraProductSet.size() == 1 && colWeraArtikels.size() > 1 ) {

		} else {

			if (m_product instanceof WeraProductSet || m_product instanceof WeraProductSetinSet) {
				// --- Satz Darstellung oder Darstellung als Display
				// --- Hole ID und erh�he diese
				final Integer oRefId = new Integer(m_iOffsetIDEbene3++);

				// --- WERA-ProduktSatz
				m_article = m_product;

				// --- Gibt einen String mit Notizen zur�ck
				strFootnote = _initWeraProductVariante(colAllFootnotes, null, oRefId, null, 1, produktXML);

			} else {
				// --- Setze Sprache, und Defaultsprache=de
				initLanguage("de");

				// --- Hole alle Varianten, die eine akitve Preisliste haben!!!!!
				final Collection articles = ((WeraProduct) m_product).getPriceListVariants();

				// --- Setze Sprache, und Defaultsprache=de
				initLanguage(m_strExportLanguage);

				// --- Hole Abmessung
				final ArrayList aArticles = ((WeraProduct) m_product)._genCADataForVariantList(articles, null);

				// --- Schleife �ber alle Artikel
				m_article = null;
				for (final Iterator it2 = articles.iterator(); it2.hasNext();) {

					// --- Hole den aktuellen Artikel
					m_article = (WeraVariante) it2.next();

					// --- filter Auslaufartikel
					if (_isFilteredVariante((WeraVariante) m_article)) {
						// ---- Variante nicht verwenden da auslaufartikel
						continue;
					}
					//Boolean ausgabe_preisliste = (Boolean)m_wm.getAttribute(m_article,"ausgabe_preisliste");
					//if ( ausgabe_preisliste == null || ausgabe_preisliste.booleanValue() == false )
					//	continue;

					// --- Hole Fussnoten = >Schleife �ber alle Attribute f�r Artikel
					final Collection footnotes = ((WeraVariante) m_article).getFootnotes();
					for (final Iterator it4 = footnotes.iterator(); it4.hasNext();) {
						// --- Hole ProfiClassAttribute
						final Footnote oFootnote = (Footnote) it4.next();

						// --- Artikelnummer entfernen
						strCode = oFootnote.getAttribute("code").toString();
						if (strCode.contains("_")) {
							strCode = strCode.substring(strCode.indexOf('_') + 1);
						}

						// --- Fussnote merken falls noch nicht vorhanden
						if (!colAllFootnotes.contains(oFootnote) && !strCode.equals("LT")) {
							if (strCode.equals("999")) {
								colAllFootnotes.add(0, oFootnote);
							} else {
								colAllFootnotes.add(oFootnote);
							}
						}

					} // --- for (Iterator it2 = footnotes.iterator(); it2.hasNext();) {

					// --- Hole ID und erh�he diese
					final Integer oRefId = new Integer(m_iOffsetIDEbene3++);

					// --- Gibt einen String mit Notizen zur�ck
					strFootnote = _initWeraProductVariante(colAllFootnotes, aArticles, oRefId, null, 1, produktXML);
				}

				// --- Aufr�umen
				aArticles.clear();

			} // --- if ( m_product instanceof WeraProductSet ) {

		} // --- if ( m_product instanceof WeraProductSetinSet) {

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(m_strExportLanguage);

		// --- Alle gesammelten Fussnoten ins Produkt �bernehmen
		// --- Schleife �ber alle Fussnoten
		Element textXML1 = null;
		Footnote oFootnote = null;
		String strFN = "";
		for (final Iterator it2 = colAllFootnotes.iterator(); it2.hasNext();) {
			// --- Hole ProfiClassAttribute
			oFootnote = (Footnote) it2.next();

			// --- Neues XML-Element
			strCode = oFootnote.getAttribute("code").toString();

			// --- Artikel entfernen
			if (strCode.contains("_")) {
				strCode = strCode.substring(strCode.indexOf('_') + 1);
			}

			// --- Notiz initialisieren
			m_iOffsetIDEbene3++;
			initLanguage(m_strExportLanguage);
			strFN = getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
			strFN = strFN.trim();
			if ( m_strExportLanguage.equals( "de") ) {
				initLanguage("en");
				strFN += "\n" + getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
				strFN = strFN.trim();
				initLanguage(m_strExportLanguage);
			}
			textXML1 = _createTextElement(new Integer(oFootnote.getLfdNr()).toString() + ")", strFN, new Integer(m_iOffsetIDEbene3),
					new Integer(m_iOffsetIDEbene3), "BLT_FN", true);

			// --- �bernehme Notiz in das Produkt
			m_oTextList.addContent(textXML1);
		}

		// --- Hole Fussnoten die am Produkt liegem = >Schleife �ber alle Attribute f�r Artikel
		final Collection footnotes = ((GeneratedWeraProduct) m_product).getFootnotes();
		oFootnote = null;
		strFN = "";
		String strKennz = "";
		for (final Iterator it2 = footnotes.iterator(); it2.hasNext();) {
			// --- Hole ProfiClassAttribute
			oFootnote = (Footnote) it2.next();

			// --- Notiz initialisieren
			initLanguage(m_strExportLanguage);
			strFN = getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
			strFN = strFN.trim();
			if ( m_strExportLanguage.equals("de") ) {
				initLanguage("en");
				strFN += "\n" + getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
				strFN = strFN.trim();
				initLanguage(m_strExportLanguage);
			}
			strKennz = (String) m_wm.getAttribute(oFootnote, "kennzeichner");
			if (!strKennz.equals("*")) {

				m_iOffsetIDEbene3++;
				if (strKennz != null) {
					// m_oTextItem = _createTextElement(m_wm.getAttribute(oFootnote, "kennzeichner") + ")", getValidString(oFootnote
					//		.getName(m_jaloSession.getSessionContext())), new Integer(m_iOffsetIDEbene3),
					//		new Integer(m_iOffsetIDEbene3), "BLT_FN", true);
					m_oTextItem = _createTextElement(m_wm.getAttribute(oFootnote, "kennzeichner") + ")", strFN, new Integer(m_iOffsetIDEbene3),
							new Integer(m_iOffsetIDEbene3), "BLT_FN", true);
				} else {
					// m_oTextItem = _createTextElement(")", getValidString(oFootnote.getName(m_jaloSession.getSessionContext())),
					//		new Integer(m_iOffsetIDEbene3), new Integer(m_iOffsetIDEbene3), "BLT_FN", true);
					m_oTextItem = _createTextElement(")", strFN,
							new Integer(m_iOffsetIDEbene3), new Integer(m_iOffsetIDEbene3), "BLT_FN", true);
				}

				// --- �bernehme Notiz in das Produkt
				m_oTextList.addContent(m_oTextItem);
			}

		} // --- for (Iterator it2 = footnotes.iterator(); it2.hasNext();) {

		// --- Mind. 1 Fussnote
		if ((footnotes == null || footnotes.size() == 0) && (colAllFootnotes == null || colAllFootnotes.size() == 0)) {

			// --- Dummy Fussnote
			m_iOffsetIDEbene3++;
			m_oTextItem = _createTextElement(" ", " ", new Integer(m_iOffsetIDEbene3), new Integer(m_iOffsetIDEbene3), "BLT_FN",
					true);
			m_oTextList.addContent(m_oTextItem);
		}
	}

	/**
	 * Analysiert die Preise der Preisliste und gibt die maximale Anzahl zur�ck
	 * @param productsSorted
	 * @return 
	 */
	private int _analyzePrices(final Collection productsSorted) {

		// --- Initialize
		HashMap hashmapValues = new HashMap();
		ArrayList prices = new ArrayList();
		Product oProduct = new Product();
		Product oArticle = new Product();
		int iMaxPrices = -1;

		// --- Debug
		LOG.info("++ Preisstruktur wird analysiert (" + m_strPriceList + ") ..." + "-Anzahl=" + productsSorted.size());

		// --- Schleife �ber alle Produkte
		for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
			hashmapValues = (HashMap) it1.next();

			// --- Hole Produkt
			oProduct = (Product) hashmapValues.get("product");

			// --- Satz oder Product
			if (oProduct instanceof WeraProductSet) {

				// --- Hole nur die Preise die Exportiert werden sollen
				prices = (ArrayList) _aGetPriceList(oProduct, m_strPriceList);
				if (prices != null && prices.size() > iMaxPrices) {
					iMaxPrices = prices.size();
				}
				prices.clear();

			} else {

				// --- Produkt
				// --- Hole alle Varianten, die eine akitve Preisliste haben!!!!!
				try {
					final Collection articles = ((WeraProduct) oProduct).getPriceListVariants();
					for (final Iterator it2 = articles.iterator(); it2.hasNext();) {

						// --- Hole Produkt
						oArticle = (Product) it2.next();

						// --- Hole nur die Preise die Exportiert werden sollen
						prices = (ArrayList) _aGetPriceList(oArticle, m_strPriceList);
						if (prices != null && prices.size() > iMaxPrices) {
							iMaxPrices = prices.size();
						}
						prices.clear();

					} // --- for ( Iterator it2 = articles.iterator(); it2.hasNext(); ) {

				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} // --- for ( Iterator it1 = productsSorted.iterator(); it1.hasNext(); ) { 

		// --- Debug
		LOG.info("++ Anzahl Preise = " + iMaxPrices);

		return iMaxPrices;
	}

	/**
	 *
	 * @param produktXML
	 * @param productList
	 * @param strCatalogPages
	 * @param bSBVariante
	 * @param bLastSBVariante
	 * @return
	 */
	private String _initWeraProductXML(final Element produktXML, final Element productList, final String strCatalogPages, boolean bSBVariante, boolean bLastSBVariante) {
		// TODO Auto-generated method stub

		// --- Setze Templatetyp
		String strTemplate = "";
		int iMaxPrices = m_iMaxPrices;
		if (iMaxPrices != 1) {
			iMaxPrices--;
		}
/*
		if (iMaxPrices == -1) {

			if (m_strPriceList.contains("staffel")) {
				m_cTemplateInfo = '3';

			} else if (m_strPriceList.contains("haendler")) {

				// --- 2 Preisspalten
				m_cTemplateInfo = '2';

			} else {

				// --- alle anderen nur 1 Preisspalte!
				m_cTemplateInfo = '1';
			}
		} else {

			// --- Template gem�ss der anzahl der Preise
			m_cTemplateInfo = String.valueOf(iMaxPrices).charAt(0);
		}
*/
		if ( iMaxPrices > 1 ) {

			// --- nur noch ein Template (netto)
			m_cTemplateInfo = '3';
		} else {

			// --- nur noch ein Template (bruttopreisliste)
			m_cTemplateInfo = '1';

		}
		LOG.info("m_cTemplateInfo=" + m_cTemplateInfo + ", iMaxPrices=" + iMaxPrices);
		
		if (m_product instanceof WeraProductSetinSet) {

			if (m_bIstDisplay.booleanValue()) {

				// --- template f�r displays
				if (bSBVariante) {
					strTemplate = "PRODUCTSETINSET_DISPLAY_SBVARIANT" + m_cTemplateInfo;
				} else {
					strTemplate = "PRODUCTSETINSET_DISPLAY" + m_cTemplateInfo;
				}

			} else {
				// --- Satz in Satz Template
				if (bSBVariante) {
					strTemplate = "PRODUCTSETINSET_SBVARIANT" + m_cTemplateInfo;
				} else {
					strTemplate = "PRODUCTSETINSET" + m_cTemplateInfo;
				}
			}

		} else {
			if (m_product instanceof WeraProductSet) {
				strTemplate = "PRODUCTSET" + m_cTemplateInfo;
			} else {
				strTemplate = "PRODUCT" + m_cTemplateInfo;
			}
		}

		// --- preislistenversion hinterlegen
		m_oTreegroupLink.setAttribute("pricelistversion", String.valueOf(m_cTemplateInfo));

		// --- akutelle template merken
		this.m_strCurrentTemplateName = strTemplate;

		// --- <Product Id="3773" Type="SET" RefCode="U100T601" Workflow="OKE_DATEN" Manufacturer="WIHA" ProductNumber="26015" EAN="4010995260156" TypeName="SET" Name="System 6 Wechselklingen Magnetic Set, 6-tlg." WorkflowName="OKE_DATEN" ManufacturerDescription="Wiha">
		produktXML.setAttribute("Id", m_strProduktID);
		produktXML.setAttribute("LinkedId", m_strProduktID);
		produktXML.setAttribute("RefCode", m_strProduktID);
		//produktXML.setAttribute( "Workflow", "OK" ); 
		//produktXML.setAttribute( "Manufacturer", "WERA"  );
		produktXML.setAttribute("ProductNumber", m_strProduktID);

		// --- Hochstellen des Registerzeichens
		String strCodeTmp = corrData_trademarks(m_product.getCode());
/////////////////////////// 
// entfernt template-optimierung					
//		strCodeTmp = strCodeTmp.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
		//strCodeTmp = strCodeTmp.replace("�", "##sub##" + "�" + "##e_sub##");
/////////////////////////// 

		produktXML.setAttribute("WeraProduktNr", strCodeTmp);

		// --- Hole Katalogseite
		initLanguage("de");
		//m_oPageNr = (Object)m_product.getLocalizedProperty("pagenr");;
		m_oPageNr = strCatalogPages;

		// --- Zur�cksetzen der Ausgabesprache
		initLanguage(m_strExportLanguage);

		if (m_oPageNr == null || m_oPageNr == "") {
			m_oPageNr = new String("-");
		} else {
			m_oPageNr = m_oPageNr.toString();
		}
		produktXML.setAttribute("Katalogseite", (String) m_oPageNr);
		produktXML.setAttribute("IndexArtNr", corrData_trademarks(m_product.getCode()) + ", " + (String) m_oPageNr);

		// --- NEU
		Boolean bProduktNeu = (Boolean) m_wm.getAttribute(m_product, "produkt_neu");
		if (bProduktNeu == null) {
			bProduktNeu = new Boolean(false);
		}
		Boolean bProduktAuslauf = (Boolean) m_wm.getAttribute(m_product, "artikel_auslauf");
		if (bProduktAuslauf == null) {
			bProduktAuslauf = new Boolean(false);
		}
		/* deaktiviert gem�ss RH, Email 12.11.2019 10:19 */
		if (false && bProduktAuslauf.booleanValue()) // --- auslaufartikel
		{
			m_strStringNew = m_ExportFormatterPL.strGetAuslaufartikel(m_strExportLanguage, bProduktAuslauf);
		} else // --- neue Artikel
		{
			m_strStringNew = m_ExportFormatterPL.strGetNew(m_strExportLanguage, bProduktNeu);
		}
		produktXML.setAttribute("Neu", m_strStringNew);

		// --- Debug
		if (m_oPageNr.toString().length() == 0) {
			m_aLogDatei.add("SEITE:" + m_product.getCode() + "=>" + m_product.getName());
		}

		// --- W�hrung
		produktXML.setAttribute("Waehrung", m_strWaehrung);

		//produktXML.setAttribute( "EAN", "" ); 
		//produktXML.setAttribute( "WorkflowName", "OK"  );
		//produktXML.setAttribute( "ManufacturerDescription", "WERA" );
		final Integer order = (Integer) m_wm.getAttribute(m_product, "order");
		if (order == null) {
			produktXML.setAttribute("Sequence", "1");
		} else {
			produktXML.setAttribute("Sequence", order.toString());
		}

		// --- <Text-List>
		m_oTextList = new Element("Text-List");
		produktXML.addContent(m_oTextList);

		// --- <ProductJoin-List>
		//m_oProductJoinList = new Element("ProductJoin-List");
		//produktXML.addContent(m_oProductJoinList);
		// --- <Attribute-List>
		//m_oArticelList = new Element("Attribute-List");
		//produktXML.addContent(m_oAttributeList);
		// --- <Attribute-List>
		//Element oAttributeHeaderList = new Element("AttributeHeader-List");
		//produktXML.addContent(oAttributeHeaderList);
		// --- <Price-List>
		//Element oPriceList = new Element("Price-List");
		//produktXML.addContent(oPriceList);
		// --- <Footnote-List>
		//Element oFootnoteList = new Element("Footnote-List");
		//produktXML.addContent(oFootnoteList);
		// --- Media-Daten
		m_motivelistXML = new Element("Motive-List");
		produktXML.addContent(m_motivelistXML);

		// --- ICON 1
		WeraMedia weramedia = m_wm._getPicture(m_product, "icons1");
		m_bildXML = createBildElement("ICON1", weramedia, "1", "pictures", "");
		m_motivelistXML.addContent(m_bildXML);

		// --- ICON 2
		weramedia = m_wm._getPicture(m_product, "icons2");
		m_bildXML = createBildElement("ICON2", weramedia, "2", "pictures", "");
		m_motivelistXML.addContent(m_bildXML);

		// --- BILD1 ------------------------------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.PICTURES1=");
		weramedia = m_wm._getPicture(m_product, "pictures1");
		if (weramedia != null) {
			
			// --- bildelement aus weramedia object
			m_bildXML = createBildElement("PICTURE1", weramedia, "1", "pictures", m_product.getCode());
			m_motivelistXML.addContent(m_bildXML);
			
		} else {
			
			// --- bildelement nicht vorhanden, verwendet virtuelles
			HashMap weraPicture	= new HashMap();
			weraPicture.put("location", ((WeraProduct) m_product).normalizeFilenameForImageLookup() + ".jpg" );
			m_bildXML = createBildreferenzElement("PICTURE1", (Object)weraPicture, "1", "pictures" );
			m_motivelistXML.addContent(m_bildXML);

		}
		// --- BILD1 ------------------------------------------------------------------------------------------------------------------

		// --- Satz in Satz Premium Icon
		int iMediaCounter = 1;
		String strMediaCounter = new Integer(iMediaCounter).toString();
		if (m_product instanceof WeraProductSetinSet) {
			final EnumerationValue eSbClassificatrion = (EnumerationValue) m_wm.getAttribute(m_product, "SbClassificatrion");
			if (eSbClassificatrion != null) {
				m_bildXML = createBildElement("SBCLASSIFICATRIONICON" + strMediaCounter, null, "1", "pictures", eSbClassificatrion.getCode());
			} else {
				m_bildXML = createBildElement("SBCLASSIFICATRIONICON" + strMediaCounter, null, "1", "pictures", "");
			}
			m_motivelistXML.addContent(m_bildXML);
		}

		// --- Hole Feature-Icons
		final HashMap mapFeatureIcons = ((WeraProduct) m_product).getFeatureIconsByBooleanProperties();
		if (mapFeatureIcons != null) {

			// --- Hole Collection der Media-Objekte
			final Collection colMedia = (Collection) mapFeatureIcons.get("iconlist");
			if (colMedia != null) {

				strMediaCounter = "";
				WeraMedia oWeraMedia = null;
				for (final Iterator itMedia = colMedia.iterator(); itMedia.hasNext();) {
					// --- Hole Artikel / Variante
					oWeraMedia = (WeraMedia) itMedia.next();

					// --- FEATRUREICONS n
					strMediaCounter = new Integer(iMediaCounter).toString();
					m_bildXML = createBildElement("FEATRUREICONS" + strMediaCounter, oWeraMedia, strMediaCounter, "pictures", "");
					m_motivelistXML.addContent(m_bildXML);
					iMediaCounter++;
				}
			}
		}

		return strTemplate;
	}

	/**
	 * Gibt einen String mit Notizen zur�ck
	 * @param colAllFootnotes
	 * @param articles
	 * @param oLocalRefId
	 * @param intContentQuantitySiS
	 * @param nCountProductSet
	 * @return 
	 */
	private String _initWeraProductVariante(final ArrayList colAllFootnotes, final ArrayList articles, Integer oLocalRefId, Integer intContentQuantitySiS, int nCountProductSet, Element produktXML ) {
		// TODO Auto-generated method stub

		//LOG.info("_initWeraProductVariante (start)");
		// --- Initialize
		final ArrayList aResult = new ArrayList();
		String strResult = "";
		String strCode = "";
		String strPriceValue = "";
		String strUnit = "";
		Element oPrice = null;
		String strKontrollLine	= "";

		try {
			// --- Code-Nr
			if (m_article.getAttribute("lagerNr") == null) {
				m_wm.setAttribute(m_article, "lagerNr", "05");
			}
			if (m_article.getAttribute("variantenNr") == null) {
				m_wm.setAttribute(m_article, "variantenNr", "001");
			}
			if (m_article instanceof WeraProductSet) {
				strCode = (String) m_article.getAttribute("artnr");
			} else {
				strCode = (String) m_article.getAttribute("code");
			}
			final String strWeraCode = (String) m_article.getAttribute("lagerNr") /* + strCode */
///////////////////////////
// entfernt template-optimierung					
//					+ "##b##" + strCode + "##e_b##"
/////////////////////////// 
					+ strCode
					+ (String) m_article.getAttribute("variantenNr");
			final String strRawCode = (String) m_article.getAttribute("lagerNr") + strCode
					+ (String) m_article.getAttribute("variantenNr");

			// --- Kontrolle
			strKontrollLine	= strRawCode;


			// --- Logdatei der Artikelnummren
			m_aLogDatei1.add(strRawCode + ", " + m_article.getName());

			// --- Hole Order
			Integer oOrder = (Integer) m_wm.getAttribute(m_product, "orderPL");
			if (oOrder == null) {
				oOrder = new Integer(0);
			}

			// --- Neuer Artikel / Produkt
			Boolean bArtikelNeu = null;
			Boolean bArtikelAuslauf = new Boolean(false);
			m_strStringNew = "";
			if (m_article instanceof WeraProductSet) {
				bArtikelNeu = (Boolean) m_wm.getAttribute(m_article, "produkt_neu");
				bArtikelAuslauf = (Boolean) m_wm.getAttribute(m_article, "artikel_auslauf");
			} else {
				bArtikelNeu = (Boolean) m_wm.getAttribute(m_article, "artikel_neu");
				bArtikelAuslauf = (Boolean) m_wm.getAttribute(m_article, "artikel_auslauf");
			}
			if (bArtikelNeu == null) {
				bArtikelNeu = new Boolean(false);
			}
			if (bArtikelAuslauf == null) {
				bArtikelAuslauf = new Boolean(false);
			}
			if (bArtikelAuslauf.booleanValue()) // --- auslaufartikel
			{
				m_strStringNew = m_ExportFormatterPL.strGetAuslaufartikel(m_strExportLanguage, bArtikelAuslauf);
			} else // --- neue Artikel
			{
				m_strStringNew = m_ExportFormatterPL.strGetNew(m_strExportLanguage, bArtikelNeu);
			}

			// --- EAN
			String strEAN = (String) m_wm.getAttribute(m_article, "ean");
			String strEAN_short = strEAN;
			String strEAN_long = strEAN;
			if (strEAN != null) {
				// --- neue version verk�rzte ean
				if (strEAN_short.length() > 7) {
					strEAN_short = strEAN_short.substring(7);
				}
				strEAN_short = m_ExportFormatterPL.strGetEAN(strEAN_short);
				strEAN_long = m_ExportFormatterPL.strGetEAN(strEAN_long);
			}

			if (true || m_cTemplateInfo != '1') {
				/*
				 * if ( m_article instanceof WeraProductSet ) { if ( strEAN.length() == 0 ) strEAN =
				 * "<ASCII-WIN>\r\n<Version:4><ParaStyle:links ohne Einzug>" + m_strStringNew; else strEAN +=
				 * m_strStringNew; }
				 */
				if (m_strStringNew.length() > 0) {
/////////////////////////// 
// entfernt template-optimierung					
//					m_strStringNew = m_ExportFormatterPL.strGetParagraph() + m_strStringNew;
/////////////////////////// 
					m_strStringNew = m_strStringNew;
				}
/////////////////////////// 
// entfernt template-optimierung					
//				m_oTextItem = createTextElement("", m_ExportFormatterPL.strGetTagVersion() + m_strStringNew, oOrder, oLocalRefId,
//						"BLT_NEW", false);
/////////////////////////// 
				m_oTextItem = createTextElement("", m_strStringNew, oOrder, oLocalRefId, "BLT_NEW", false);
				m_oTextList.addContent(m_oTextItem);
			}

			///////////////////////////////////////////////////////////////////////////
			// Seitennummer Platzhalter
			///////////////////////////////////////////////////////////////////////////
			String strSeitennummer	= m_oPageNr.toString();
			if ( m_platzhalterSeitennummer ) {
				
				strSeitennummer			= m_oPageNr.toString();
				strSeitennummer			= strSeitennummer.replaceAll("CODE", strRawCode);
				produktXML.setAttribute("Katalogseite", strSeitennummer);
				Attribute oIndexArtNr	= (Attribute)produktXML.getAttribute("IndexArtNr" );
				String strIndexArtNr	= oIndexArtNr.getValue().replaceAll("CODE", strRawCode);
				produktXML.setAttribute("IndexArtNr", strIndexArtNr );
			
			} else {
				
				// --- Seitennummer aus Hybris
				strSeitennummer	= m_oPageNr.toString();
			}

			// --- preislisten

			// --- EAN
			m_oTextItem = createTextElement("", strEAN_short, oOrder, oLocalRefId, "BLT_EAN", false);
			m_oTextList.addContent(m_oTextItem);
			m_oTextItem.setAttribute("IndexCode", strRawCode /* + ", " + (String) strSeitennummer */ );
			m_oTextItem = createTextElement("", strEAN_long, oOrder, oLocalRefId, "BLT_COMPLETTE_EAN", false);
			m_oTextList.addContent(m_oTextItem);
			m_oTextItem.setAttribute("IndexCode", strRawCode /* + ", " + (String) strSeitennummer */ );
			//if ( m_ExportFormatterPL.m_bIsIB_Exporter == false )

			// --- Formatierung
			String strLineResult = "";
			final String strFormat = m_ExportFormatterPL.strGetTagVersion() + m_ExportFormatterPL.strGetParagraph();
/////////////////////////// 
// entfernt template-optimierung					
//			final String strPreFormat = m_ExportFormatterPL.getBeginNoBreak();
//			final String strPostFormat = m_ExportFormatterPL.getEndNoBreak();
/////////////////////////// 
			final String strPreFormat = "";
			final String strPostFormat = "";

			// --- Eintrag als TextItem anlegen und in Textliste �bernehmen
			if (m_product instanceof WeraProductSetinSet) {
				// --- SATZ in SATZ --------------------------------------------------------------------------
				// --- BLT_SET
				createContentElementSetinSet((WeraProductSet) m_article, m_oTextList, oLocalRefId, nCountProductSet);

				// --- Initialize Tabellen Inhalt
				String strCodeStr = "";
				String strCodeStrSB = "";
				String strAbmessung = "";
				String strCodeStrListe = "";
				String strCodeStrListeRaw = "";
				if (m_bIstDisplay.booleanValue()) {

					// --- Display-Darstellung --------------------------
					String name			= "";
					String nameEN		= "";
					Item weraProduct 	= null;
					Collection colWeraProductSet = (Collection) ((WeraProductSetinSet) m_article).getWeraProductSetDataComponents();
					for (final Iterator iterPSet = colWeraProductSet.iterator(); iterPSet.hasNext();) {

						// --- Hole den Satz
						Object cContainer = iterPSet.next();
						if ( cContainer instanceof WeraVarianteVariants ) {

							// --- variante
							weraProduct	= (Item) m_wm.getAttribute((Item)cContainer,"weravariants" );

						} else {

							// --- satz
							weraProduct	=  (Item) m_wm.getAttribute((Item)cContainer,"weraproductsets" );
						}

						// --- prüfe ob der produkt exportiert werden darg
						if ( m_wm.getVisibilityForCatalog( weraProduct, weraCatalogVersion, false ) == -1 ) {

							String strTmpCode = (String) m_wm.getAttribute(weraProduct, "code");
							System.out.println( strTmpCode + ", skipped, product not allowed for current catalogversion");
							continue;
						}

						// --- verpackunseinheit hole
						final Integer intWeraproductsetVPE = (Integer) m_wm.getAttribute((Item)cContainer, "vpe");

						// --- BLT_SET anlegen
						setDisplayTitle( (Product) weraProduct, oLocalRefId, "BLT_DISPLAYSET", intWeraproductsetVPE.toString() + "x " );
						oLocalRefId = new Integer(m_iOffsetIDEbene3++);

						// --- name in der exportsrpache holen
						name	= (String) m_wm.getAttribute(weraProduct, "name" );

						// --- sprache auswertungen
						if ( m_strLanguage.equals("de") ) {
							// --- name in en holen
							initLanguage("en");
							nameEN = (String) m_wm.getAttribute(weraProduct, "name" );
							if ( !name.equals(nameEN)) {

								name = name + " / " + nameEN;
							}
							initLanguage("de");
						}

						strAbmessung += intWeraproductsetVPE + "x " + name + "\r\n";
					}


/**
 * alte version ih  ohne Artikel SIS
					// --- Display-Darstellung --------------------------
					String name	= "";
					String nameEN	= "";
					Collection colWeraProductSet = (Collection) ((WeraProductSetinSet) m_article).getAttribute("weraproductsetvariants_qual");
					for (final Iterator iterPSet = colWeraProductSet.iterator(); iterPSet.hasNext();) {

						// --- Hole den Satz
						final WeraProductSetVariants weraproductsetvariants = (WeraProductSetVariants) iterPSet.next();
						final WeraProductSet weraproductset = (WeraProductSet) weraproductsetvariants.getAttribute("weraproductsets");
						final Integer intWeraproductsetVPE = (Integer) weraproductsetvariants.getAttribute("vpe");
						
						// --- name in der exportsrpache holen
						name = ((WeraProductSet) weraproductset).getName();

						// --- sprache auswertungen
						if ( m_strLanguage.equals("de") ) {
							// --- name in en holen
							initLanguage("en");
							nameEN = ((WeraProductSet) weraproductset).getName();
							if ( !name.equals(nameEN)) {
								
								name = name + " / " + nameEN;
							}
							initLanguage("de");
						}

						strAbmessung += intWeraproductsetVPE + "x " + name + "\r\n";
					}
*/


				} else {
					// --- SB-Darstellung -------------------------------
/////////////////////////// 
// entfernt template-optimierung					
//					strCodeStr = "<CharStyle:wera\\_tabelle\\_bold>" + ((WeraProductSet) m_article).getCode() + "\n<CharStyle:>";
//					strCodeStrSB = "<CharStyle:wera\\_tabelle\\_bold>" + ((WeraProductSet) m_article).getCode() + "\n<CharStyle:>";
//					strAbmessung = "<CharStyle:wera\\_tabelle>" + g_strAbmessung + "<CharStyle:>";
///////////////////////// 
					strCodeStr		= ((WeraProductSet) m_article).getCode();
					strCodeStrSB	= ((WeraProductSet) m_article).getCode();
					strAbmessung	= g_strAbmessung;
					// --- SB-Darstellung -------------------------------
					int iPosTmp = 1;
					for (Iterator itCode = g_oHashMapCodeNr.values().iterator(); itCode.hasNext();) {
						String strCodeTmp = (String) itCode.next();
						strCodeStrListe += (String) strCodeTmp;
						if (iPosTmp < g_oHashMapCodeNr.size()) {
							strCodeStrListe += "\r\n";
						}
						if (iPosTmp == 1) {
							strCodeStr = strCodeTmp;
						}
						iPosTmp++;
					}
//                                        System.out.println("nCountProductSet="+nCountProductSet);
//                                        System.out.println("class(article)="+m_article.getClass().getName());
//                                        System.out.println("size="+g_oHashMapCodeNr.size());
//                                        System.out.println("strCodeStrListe="+strCodeStrListe);
//                                        System.out.println("strCodeStrSB="+strCodeStrSB);
/////////////////////////// 
// entfernt template-optimierung					
//	strCodeStrListe = strCodeStrListe.replace("\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
///////////////////////// 
					strCodeStrListeRaw = strCodeStrListe;
					if (!strCodeStrListe.isEmpty()) {
/////////////////////////// 
// entfernt template-optimierung					
//						strCodeStrListe = "<CharStyle:wera\\_tabelle\\_bold>" + strCodeStrListe + "<CharStyle:>";
///////////////////////// 
						strCodeStrListe = strCodeStrListe;
					}
				}

				if (m_cTemplateInfo == '1') {
					// --- Neu Info nur bei Bruttopreisliste
					if (m_strStringNew.length() > 0) {
						m_strStringNew = m_strStringNew;
					}
				}

				// ---  Abmessung bereinigen
				strAbmessung = strAbmessung.replace("--x", "");
				strAbmessung = strAbmessung.replace("-x", "");
				// ---  Abmessung bereinigen

				// --- BTL_ARTIKEL anlegen inkl. 2 Textbl�cke
				Element oTextBlock = m_oTextItem.getChild("TextBlock");
/////////////////////////// 
// entfernt template-optimierung					
//				m_oTextItem = createTextElement("", m_ExportFormatterPL.strGetTagVersion() + m_ExportFormatterPL.strGetParagraphCodeNr()
//						+ strCodeStrSB + strAbmessung + m_strStringNew, oOrder, oLocalRefId,
//						"BLT_ARTICEL", false);
///////////////////////// 
				m_oTextItem = createTextElement("", strCodeStrSB + strAbmessung + " " + m_strStringNew, oOrder, oLocalRefId,
						"BLT_ARTICEL", false);
				oTextBlock.setAttribute("Id", "codenr");

				Element artikelnrSBXML = new Element("TextBlock");
				artikelnrSBXML.setAttribute("Id", "sb_artikelnr");
				String strTagContentArtikelnrSB = "";
				if (nCountProductSet == 1) // --- SIS enth�lt nur ein Satz mit jeweils eigener Code / EAN
				{
/////////////////////////// 
// entfernt template-optimierung					
//					strTagContentArtikelnrSB = strCodeStrSB.replace("\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
/////////////////////////// 
				} else // --- SIS enth�lt mehrere verschiedene S�tze mit jeweils eigener Code / EAN
				{
/////////////////////////// 
// entfernt template-optimierung					
//					strTagContentArtikelnrSB = strCodeStrListe.replace("\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
/////////////////////////// 
				}
				artikelnrSBXML.addContent(m_ExportFormatterPL.strGetTagVersion() + m_ExportFormatterPL.strGetParagraph() + strTagContentArtikelnrSB);
				m_oTextItem.addContent(artikelnrSBXML);

				// ean kurz (strEAN_short)
				Element eanShort = new Element("TextBlock");
				eanShort.setAttribute("Id", "ean_short");
				eanShort.addContent(strEAN_short);
				m_oTextItem.addContent(eanShort);

				// artikelnr
				Element artikelnrXML = new Element("TextBlock");
				artikelnrXML.setAttribute("Id", "artikelnr");
				String strTagContentArtikelnr = "";
				if (nCountProductSet == 1) // --- SIS enth�lt nur ein Satz mit jeweils eigener Code / EAN
				{
/////////////////////////// 
// entfernt template-optimierung					
//					strTagContentArtikelnr = strCodeStrListe.replace("\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
/////////////////////////// 
				} else // --- SIS enth�lt mehrer verschiedene S�tze mit jeweils eigener Code / EAN
				{
/////////////////////////// 
// entfernt template-optimierung					
//					strTagContentArtikelnr = strCodeStrSB.replace("\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
/////////////////////////// 
				}
				artikelnrXML.addContent(m_ExportFormatterPL.strGetTagVersion() + m_ExportFormatterPL.strGetParagraph() + strTagContentArtikelnr);
				m_oTextItem.addContent(artikelnrXML);

//				String strTagContentArtikelnr = m_ExportFormatterPL.strGetTagVersion() + m_ExportFormatterPL.strGetParagraph() 
//											+ strCodeStrListe + m_strStringNew;
				//String strTagContentArtikelnr = strCodeStrListeRaw.replace("\u00ae","##sub##" + "\u00ae" + "##e_sub##");
				//String strTagContentArtikelnr = strCodeStrListeRaw.replace( "\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
				Element inhaltXML = new Element("TextBlock");
				if (m_cTemplateInfo == '1') {
					if (m_strStringNew.length() > 0) {
						m_strStringNew = m_ExportFormatterPL.strGetParagraph() + m_strStringNew;
					}
				}

				String strTagContent = m_ExportFormatterPL.strGetTagVersion() + m_ExportFormatterPL.strGetParagraph()
								+ strAbmessung + m_strStringNew;
				inhaltXML.setAttribute("Language", m_strLanguage);
				inhaltXML.setAttribute("Id", "nocodenr");
				strTagContent = strTagContent.replaceAll("##br##", " ");
				strTagContent = strTagContent.replaceAll("##br /##", " ");
				strTagContent = strTagContent.replaceAll("##br/##", " ");
				inhaltXML.addContent(strTagContent);
				m_oTextItem.addContent(inhaltXML);

				// --- Setze Katalogseite
				String strCatalogPages = "-";
				if (m_weraRefCatalogVersion != null) {
					Collection category2productexts = ((WeraProduct) m_article).getCategory2ProductextsByCatalogVersion(m_weraRefCatalogVersion.getVersion().toString());
					if (category2productexts != null && category2productexts.size() > 0) {
						Category2ProductExt category2productext = (Category2ProductExt) category2productexts.iterator().next();
						strCatalogPages = category2productext.getPagenr_catalog();
						if (strCatalogPages == null || strCatalogPages == "") {
							strCatalogPages = "-";
						}
					}
				}
				m_oTextItem.setAttribute("Katalogseite", strCatalogPages);

				// --- Setze EAN
				if (m_ExportFormatterPL.m_bIsIB_Exporter == false) {
					m_oTextItem.setAttribute("EAN", strEAN);
				}

				// --- Hole ContentQuantity
				if (intContentQuantitySiS == null) {
					intContentQuantitySiS = new Integer(1);
				}
				m_oTextItem.setAttribute("Unit", intContentQuantitySiS.toString());

				// --- Kontrolle VPE
				strKontrollLine += "\t" + intContentQuantitySiS.toString();

			} else {

				//////////////////////////////////////////////////////////////////////////////////
				// SATZ
				//////////////////////////////////////////////////////////////////////////////////
				if (m_article instanceof WeraProductSet) {
					// --- SATZ ------------------------------------------------------------------------------
					// --- BLT_SET
					// --- Hole ID-Set
					createContentElement((WeraProductSet) m_product, m_oTextList, oLocalRefId);
/*
					////////////// TEST ////////////////
					final Collection colFootnodesSet = new ArrayList();
					if (((WeraProductSet) m_product).getWeraProductSetFootnotes() != null) {
						colFootnodesSet.addAll(((WeraProductSet) m_product).getWeraProductSetFootnotes());
					}
					for (final Iterator it9 = colFootnodesSet.iterator(); it9.hasNext();) {
						final Footnote oFootnote = (Footnote) it9.next();

						// --- Artikelnummer entfernen
						strCode = oFootnote.getAttribute("code").toString();
						LOG.info(strRawCode + ", FN=" + strCode);
					}
					////////////// TEST ////////////////
*/
					if (m_cTemplateInfo == '1') {
						if (m_strStringNew.length() > 0) {
/////////////////////////// 
// entfernt template-optimierung					
//							m_strStringNew = m_ExportFormatterPL.strGetParagraph() + m_strStringNew;
/////////////////////////// 
							m_strStringNew = m_strStringNew;
						}
/////////////////////////// 
// entfernt template-optimierung					
//						m_oTextItem = createTextElement("", m_ExportFormatterPL.strGetTagVersion() + m_strStringNew, oOrder, oLocalRefId,
//								"BLT_ARTICEL", false);
/////////////////////////// 
						m_oTextItem = createTextElement("", m_strStringNew, oOrder, oLocalRefId,
								"BLT_ARTICEL", false);
					} else {
						m_oTextItem = createTextElement("", "", oOrder, oLocalRefId, "BLT_ARTICEL", true);
					}

					// --- Setze EAN
					if (m_ExportFormatterPL.m_bIsIB_Exporter == false) {
						m_oTextItem.setAttribute("EAN", strEAN);
					}

					// --- Hole ContentQuantity
					Integer intContentQuantity = (Integer) m_wm.getAttribute(m_article, "contentQuantity");
					if (intContentQuantity == null) {
						intContentQuantity = new Integer(1);
					}
					m_oTextItem.setAttribute("Unit", intContentQuantity.toString());

					// --- Kontrolle VPE
					strKontrollLine += "\t" + intContentQuantity.toString();

				} else {

					//////////////////////////////////////////////////////////////////////////////////
					// PRODUCT
					//////////////////////////////////////////////////////////////////////////////////
					String strAbmessung = "";
					if (articles != null && articles.size() > 0) {
						Product product = null;
						HashMap hashmap = new HashMap();
						HashMap oHashMapArtikel = new HashMap();
						hashmap = (HashMap) articles.get(0);
						final ArrayList colHashArtikel = (ArrayList) hashmap.get("colHashArtikel");
						for (final Iterator it2 = colHashArtikel.iterator(); it2.hasNext();) {
							// --- Hole ProfiClassAttribute
							oHashMapArtikel = (HashMap) it2.next();
							product = (Product) oHashMapArtikel.get("variant");
							if (product.getCode().equals(strCode)) {
								strAbmessung = oHashMapArtikel.get("value").toString();
								//LOG.info("ABMESSUNG 0:" + m_product.getCode() + "=>" + strAbmessung);
								if (strAbmessung.length() > 3) {
									strAbmessung = strAbmessung.substring(3);
								}
								//LOG.info("ABMESSUNG 1:" + m_product.getCode() + "=>" + strAbmessung);
								break;
							}

						}
					}

					// --- Formatierung Abmessung
					if (strAbmessung.length() == 0) {
						m_aLogDatei.add("ABMESSUNG:" + m_product.getCode() + "=>" + m_product.getName());
					}
					if (strAbmessung.length() > 0 || m_strStringNew.length() > 0) {

						if (m_strExportLanguage.equals("en") || m_strExportLanguage.equals("en-us")) {
							strAbmessung = strAbmessung.replace(',', '.');
						} else {
							strAbmessung = strAbmessung.replace('.', ',');
						}
// LOG.info("ABMESSUNG 2:" + m_product.getCode() + "=>" + strAbmessung);

						// --- Indesign-Tag-Marke
/////////////////////////// 
// entfernt template-optimierung					
//						strLineResult = strFormat;
/////////////////////////// 
						strLineResult = "";

						// --- EAN
						//if ( strEAN.length() > 0 )
						//	strLineResult += strEAN;
						// ---  Abmessung bereinigen
						strAbmessung = strAbmessung.replace("--x", "");
						strAbmessung = strAbmessung.replace("-x", "");
						// ---  Abmessung bereinigen

						// --- Abmessung
						if (strAbmessung.length() > 0 && strAbmessung != "-") {
							strLineResult += strPreFormat + strAbmessung + strPostFormat;
						}

						// --- Neuer Artikel
						if (m_strStringNew.length() > 0) {
							strLineResult += " " + m_strStringNew;
						}
					}
					// LOG.info("################ ABMESSUNG 3:" + m_product.getCode() + "=>" + strLineResult);

//if ( strRawCode == "05029502001" ) {
	// LOG.info("################ Abmessung=" + strAbmessung);
//}

					m_oTextItem = createTextElement("", strLineResult, oOrder, oLocalRefId, "BLT_ARTICEL", false);

					// --- Setze EAN
					if (m_ExportFormatterPL.m_bIsIB_Exporter == false) {
						m_oTextItem.setAttribute("EAN", strEAN);
					}

					// --- VPE einf�gen
					// --- Hole ContentQuantity
					Integer intContentQuantity = (Integer) m_wm.getAttribute(m_article, "contentQuantity");
					if (intContentQuantity == null) {
						intContentQuantity = new Integer(1);
					}
					m_oTextItem.setAttribute("Unit", intContentQuantity.toString());

					// --- Kontrolle VPE
					strKontrollLine += "\t" + intContentQuantity.toString();
				}
			}

			m_oTextList.addContent(m_oTextItem);

			// --- CodeNr (05 123456 001)
			String tmpWeraCode	= strWeraCode + "               ";
			m_oTextItem.setAttribute("CodeNr", tmpWeraCode.substring(0,2) + " " + tmpWeraCode.substring(2,8) + " " + tmpWeraCode.substring(8,11));

			// --- Kontrolle EAN
			strKontrollLine += "\t" + strEAN_long;


			//		 --------------------------------------------------------------------------------------
			//		 --- PRICING (START) ------------------------------------------------------------------
			//		 --------------------------------------------------------------------------------------
			for (String strPricelist: m_pricelists) {

				// ---
				Element m_oTextItemPL = createTextElement("", "", oOrder, oLocalRefId, "BLT_PREISLISTE", false);
				m_oTextList.addContent(m_oTextItemPL);

				// --- platzhalter Preisdaten
				String strPreislisteNr		= "";

				// --- staffel oder haendlernetto
				if ( strPricelist.toLowerCase().contains("staffel") || strPricelist.toLowerCase().contains("campaign") ) {

					// --- staffelpreisliste
					m_oTextItemPL.setAttribute( "preisliste", "staffel" );
					strPreislisteNr		= "1-";

				} else {

					// --- haendlernetto preisliste
					m_oTextItemPL.setAttribute( "preisliste", "haendlernetto" );
					strPreislisteNr		= "2-";
				}

				// --- platzhalter Preisdaten
				String strPlatzhalterPreis	= strPreislisteNr + strRawCode + "-p-";
				String strPlatzhalterUnit	= strPreislisteNr + strRawCode + "-u-";

				// --- Initialize
				int iPricePos = 0;
				final int iAnzahlSollNettoPrices = 5;
				int iDummyPrices = 0;
				Double dBruttoPrice = null;
				String strBruttoPrice = "";
				Double dPrice = null;
				final Boolean bIsEVKDef = new Boolean(false);
				Boolean bIsEVK = null;
				Boolean bAufAnfrage = null;
				String strSymbol = "";
				String strIsoCode = "";

				// --- Hole nur die Preise die Exportiert werden sollen
				ArrayList prices = new ArrayList();
				// LOG.info( "Hole Preise=" + strPricelist);
				prices = (ArrayList) _aGetPriceList(m_article, strPricelist);
				LOG.info( "Anzahl  Preise=" + prices.size());

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Bei Displays wird hier immer ein PR4 ausgegeben, 3 Dummy Preise
// Dieses muss noch korrigiert werden
// F�hrede Dummypreise erstmal auskommentiert
////////////////////////////////////////////////////////////////////////////////////////////////////////////////


				// --- Hole die PreislistenInfos
				if (strPricelist.toLowerCase().contains("brutto")) {

					iDummyPrices = 5;

				} else {

					// --- Hole die Preislisten-Nr char cLastChar = m_strPriceList.charAt(m_strPriceList.length()-1); if (
					//* cLastChar == '3' ) iDummyPrices = 2; if ( cLastChar == '2' ) iDummyPrices = 3;

//////////////
					// --- preset
					iDummyPrices = -1;

					// --- Initialize
					PriceRow pricerow = null;
					if (prices != null && prices.size() > 0) {
						for (final Iterator it0 = prices.iterator(); it0.hasNext();) {

							// --- GetIt
							pricerow = (PriceRow) it0.next();

							// --- Korrektur Dummy ermittlung auch für Displays
							Integer iOrder = (Integer) m_wm.getAttribute(pricerow, "order");
							if (iOrder != null) {
								iDummyPrices = (iOrder - 10) / 10;
							}
							//LOG.info("Code=" + strWeraCode + ", Order=" + iOrder + ", iDummyPrices=" + iDummyPrices);
							break;
						}
					}
					if ( iDummyPrices == -1 || iDummyPrices > 5 ) {

						iDummyPrices = 4 - prices.size();
					}
//////////////
				}

				// --- Anlegen von Dummy-Prices Spalten
				for (iPricePos = 0; iPricePos < iDummyPrices; iPricePos++) {

					// --- Preis n
					oPrice = new Element("Price");
					m_oTextItemPL.addContent(oPrice);
					oPrice.setAttribute("Id", "PR" + (char) (49 + iPricePos));
					oPrice.setAttribute("Preis", " ");
					oPrice.setAttribute("Unit", " ");
					oPrice.setAttribute("Symbol", strSymbol);
					oPrice.setAttribute("IsoCode", strIsoCode);

					// --- platzhalter
					oPrice.setAttribute("php", strPlatzhalterPreis + "pr" + (char) (49 + iPricePos) );
					oPrice.setAttribute("phu", strPlatzhalterUnit + "pr" + (char) (49 + iPricePos) );

					// --- zeile übernehmen
					strKontrollLine += "\t\t";

				}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// Bei Displays wird hier immer ein PR4 ausgegeben, 3 Dummy Preise
// Dieses muss noch korrigiert werden
// F�hrede Dummypreise erstmal auskommentiert
////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				if (prices != null && prices.size() > 0) {

					// --- Initialize
					PriceRow pricerow = null;

					for (final Iterator it3 = prices.iterator(); it3.hasNext();) {

						// --- GetIt
						pricerow = (PriceRow) it3.next();

						// --- W�hrung
						strSymbol = pricerow.getCurrency().getSymbol().toString();
						strIsoCode = pricerow.getCurrency().getIsoCode().toString();

						// --- ist der Preis EVK?
						bIsEVK = (Boolean) m_wm.getAttribute(pricerow, "evk");
						if (bIsEVK == null) {
							bIsEVK = bIsEVKDef;
						}

						// --- Preis auf Anfrage
						bAufAnfrage = (Boolean) m_wm.getAttribute(pricerow, "aufanfrage");
						if (bAufAnfrage == null) {
							bAufAnfrage = new Boolean(false);
						}

						if (dBruttoPrice == null && (!pricerow.isNet() || bIsEVK.booleanValue())) {
							// --- Preis auf Anfrage?
							if (bAufAnfrage.booleanValue() == false) {
								dBruttoPrice = new Double(pricerow.getPrice());
								strBruttoPrice = "";
								if (dBruttoPrice != null && dBruttoPrice.doubleValue() != 0) {
									strBruttoPrice = _strFormatPrice(dBruttoPrice);
								}

								//  --- Unit
								if (pricerow.getMinQuantity() != 0) {
									strUnit = new Long(pricerow.getMinQuantity()).toString();
								}
							} else {
								strBruttoPrice = "";
							}
						} else {
							// --- inc PriceCounter
							iPricePos++;

							// --- Initialie
							strPriceValue = " ";
							strUnit = " ";

							// --- Preis auf Anfrage?
							if (bAufAnfrage.booleanValue() == false) {
								// --- Pricing
								dPrice = new Double(pricerow.getPrice());
								if (dPrice.doubleValue() != 0) {
									strPriceValue = _strFormatPrice(dPrice);
								}

								// --- Unit
								strUnit = "";
								if (pricerow.getMinQuantity() != 0) {
									strUnit = new Long(pricerow.getMinQuantity()).toString();
								}
							} else {
								strPriceValue = " ";
								strUnit = " ";
							}

							// --- Hole Order
							Integer iOrder = (Integer) m_wm.getAttribute(pricerow, "order");
							if (iOrder == null) {
								iOrder = new Integer(0);
							}
							if (iOrder.intValue() != (iPricePos * 10)) {
								// --- Leere Preisspalte
								int iPricePosTmp = iPricePos * 10;
								if (iPricePosTmp < iOrder.intValue()) {
									for (; iPricePosTmp < iOrder.intValue(); iPricePosTmp += 10) {
										// --- Leere Preisspalten
										oPrice = new Element("Price");
									}
									m_oTextItemPL.addContent(oPrice);
									oPrice.setAttribute("Id", "PR" + (char) (48 + iPricePos));
									oPrice.setAttribute("Preis", " ");
									oPrice.setAttribute("Unit", " ");
									oPrice.setAttribute("Symbol", strSymbol);
									oPrice.setAttribute("IsoCode", strIsoCode);

									// --- platzhalter
									oPrice.setAttribute("php", strPlatzhalterPreis + "pr" + (char) (48 + iPricePos) );
									oPrice.setAttribute("phu", strPlatzhalterUnit + "pr" + (char) (48 + iPricePos) );
								}
								iPricePos = iPricePosTmp / 10;
							}

							// --- Preis n
							oPrice = new Element("Price");
							m_oTextItemPL.addContent(oPrice);
							oPrice.setAttribute("Id", "PR" + (char) (48 + iPricePos));
							oPrice.setAttribute("Preis", strPriceValue);
							oPrice.setAttribute("Unit", strUnit);
							oPrice.setAttribute("Symbol", strSymbol);
							oPrice.setAttribute("IsoCode", strIsoCode);

							// --- platzhalter
							oPrice.setAttribute("php", strPlatzhalterPreis + "pr" + (char) (48 + iPricePos) );
							oPrice.setAttribute("phu", strPlatzhalterUnit + "pr" + (char) (48 + iPricePos) );

							// --- zeile übernehmen
							strKontrollLine += "\t" + strUnit + "\t" + strPriceValue;
						}
					}

				}

				// --- Auff�llen  von Dummy-Prices Spalten
				for (; iPricePos < iAnzahlSollNettoPrices; iPricePos++) {

					// --- Preis n
					oPrice = new Element("Price");
					m_oTextItemPL.addContent(oPrice);
					oPrice.setAttribute("Id", "PR" + (char) (49 + iPricePos));
					oPrice.setAttribute("Preis", " ");
					oPrice.setAttribute("Unit", " ");
					oPrice.setAttribute("Symbol", strSymbol);
					oPrice.setAttribute("IsoCode", strIsoCode);

					// --- platzhalter
					oPrice.setAttribute("php", strPlatzhalterPreis + "pr" + (char) (49 + iPricePos) );
					oPrice.setAttribute("phu", strPlatzhalterUnit + "pr" + (char) (49 + iPricePos) );

					// --- zeile übernehmen
					strKontrollLine += "\t\t";
				}

				// --- Preis (Brutto) - PRBRUTTO
				oPrice = new Element("Price");
				m_oTextItemPL.addContent(oPrice);
				oPrice.setAttribute("Id", "PRBRUTTO");
				//strPriceValue  = _strFormatPrice( dBruttoPrice );
				strPriceValue = strBruttoPrice;
				oPrice.setAttribute("Preis", strPriceValue);
				oPrice.setAttribute("Unit", strUnit);
				oPrice.setAttribute("Symbol", strSymbol);
				oPrice.setAttribute("IsoCode", strIsoCode);

				// --- platzhalter
				oPrice.setAttribute("php", strPlatzhalterPreis + "prbrutto" );
				oPrice.setAttribute("phu", "" );

				// --- zeile übernehmen
				strKontrollLine += "\t" + strPriceValue;
				//		 --------------------------------------------------------------------------------------
				//		 --- PRICING --------------------------------------------------------------------------
				//		 --------------------------------------------------------------------------------------





			} // for (String strPricelist: m_pricelists) {
			//		 --------------------------------------------------------------------------------------
			//		 --- PRICING (ENDE----------------------------------------------------------------
			//		 --------------------------------------------------------------------------------------

			//		 --------------------------------------------------------------------------------------
			//		 --- Footnotes ------------------------------------------------------------------------
			//		 --------------------------------------------------------------------------------------
			// --- Setze Sprache, und Defaultsprache=de
			initLanguage(m_strExportLanguage);

			// --- Holle alle verkn�pften Fussnoten
			final Collection footnotes = (Collection) m_wm.getAttribute(m_article, "footnotes");
LOG.info("CODE=" + strRawCode );
LOG.info("Anzahl FN=" + footnotes.size() );
			// --- Schleife �ber alle Attribute f�r Artikel
			final Element notizXML = null;
			int iPos = 0;
			for (final Iterator it2 = footnotes.iterator(); it2.hasNext();) {
				// --- Hole ProfiClassAttribute
				final Footnote oFootnote = (Footnote) it2.next();

				// --- Artikelnummer entfernen
				strCode = oFootnote.getAttribute("code").toString();
				if (strCode.contains("_")) {
					strCode = strCode.substring(strCode.indexOf('_') + 1);
				}

				// --- Fussnote merken falls noch nicht vorhanden
				if (colAllFootnotes.contains(oFootnote)) {
					iPos = colAllFootnotes.indexOf(oFootnote) + 1;
					oFootnote.setLfdNr(iPos);

					// --- Notiznummer setzen
					final String strFootnote = new Integer(oFootnote.getLfdNr()).toString() + ")";
					if (!aResult.contains(strFootnote)) {
						aResult.add(strFootnote);
					}
				}

			} // --- for (Iterator it2 = footnotes.iterator(); it2.hasNext();) {

			// --- Sortiere Ergebnisliste
			if (aResult.size() > 0) {
				Collections.sort(aResult);
				for (final Iterator it = aResult.iterator(); it.hasNext();) {
					strResult += " " + (String) it.next();
				}
				if (strResult.length() > 0) {
					strResult = strResult.substring(1);
				}
			}
			// --- Fussnoten hochgestellt
			if (strResult.length() > 0) {
				strResult = m_ExportFormatterPL.getBeginSup() + strResult + m_ExportFormatterPL.getEndSup();
			}

			// --- Fussnoten
			m_oTextItem.setAttribute("Fussnote", strResult);

			// --- CodeNr erg�nzen um Fussnoten
			if (m_ExportFormatterPL.m_bIsIB_Exporter) {
				tmpWeraCode	= strWeraCode + "               ";
				m_oTextItem.setAttribute("CodeNr", tmpWeraCode.substring(0,2) + " " + tmpWeraCode.substring(2,8) + " " + tmpWeraCode.substring(8,11) + " " + strResult);
				//		 --------------------------------------------------------------------------------------
				//		 --- Footnotes ------------------------------------------------------------------------
				//		 --------------------------------------------------------------------------------------
			}

		} catch (final Exception e) {
			LOG.info("ERROR=" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		//LOG.info("_initWeraProductVariante (ende)=" + strResult);


		// --- Kontroll-Zeile übernehmen
		m_kontrollCSV.add( strKontrollLine );

		return strResult;
	}

	/**
	 * Formatiere den Preice
	 * @param dPriceValue
	 * @return 
	 */
	protected String _strFormatPrice(final Double dPriceValue) {

		return m_wm._strFormatPrice(dPriceValue, m_strExportLanguage);
	}

	/**
	 * 
	 * @param article
	 * @param strPriceList
	 * @return 
	 */
	protected Collection _aGetPriceList(final Product article, final String strPriceList) {

		return m_wm._aGetPriceList(article, strPriceList);
	}

	/**
	 * Datenkorrektur der tschechischen �bersetzungen
	 * @param strData
	 * @return 
	 */
	public String corrData_trademarks(final String strData) {

		// --- Initialize
		String strResult = strData;
		if (strResult != null) {

			strResult = strResult.replaceAll("1-Komponenten", "1-komponentig");
			strResult = strResult.replaceAll("Kraftform\u00AE", "Kraftform");
			strResult = strResult.replaceAll("Bit-Check\u00AE", "Bit-Check");
			strResult = strResult.replaceAll("Bit-Checks\u00AE", "Bit-Checks");
			strResult = strResult.replaceAll("Bit-Safe\u00AE", "Bit-Safe");
			strResult = strResult.replaceAll("Bit-Safes\u00AE", "Bit-Safes");
			strResult = strResult.replaceAll("BiTorsion\u00AE", "BiTorsion");
			strResult = strResult.replaceAll("Kraftform Kompakt\u00AE", "Kraftform Kompakt");
			strResult = strResult.replaceAll("Lasertip\u00AE", "Lasertip");
			strResult = strResult.replaceAll("Rapidaptor\u00AE", "Rapidaptor");
			strResult = strResult.replaceAll("Der Schraubmeissel\u00AE", "Der Schraubmeissel");
			strResult = strResult.replaceAll("Weralit\u00AE", "Weralit");
			strResult = strResult.replaceAll("Kraftform Micro\u00AE", "Kraftform Micro");
			strResult = strResult.replaceAll("Kraftform\u00AE Micro", "Kraftform Micro");
			strResult = strResult.replaceAll("BlackLaser\u00AE", "BlackLaser");
			/*
			 * Kraftform Bit-Check Bit-Safe BiTorsion Kraftform Kompakt Lasertip Rapidaptor Wera: Der Schraubmeissel
			 * Weralit Kraftform Micro BlackLaser
			 */
		} else {
			strResult = "";
		}

		return strResult;
	}

	/**
	 * 
	 * @param strTagName
	 * @param weramedia
	 * @param strPrio
	 * @param strDirectory
	 * @param strCodeNrProduct
	 * @return 
	 */
	@Override
	public Element createBildElement(final String strTagName, final WeraMedia weramedia, final String strPrio,
			final String strDirectory, final String strCodeNrProduct) {
		// TODO Auto-generated method stub

		if (m_ExportFormatterPL instanceof ExportFormatterPL4FO) {
			m_bildXML = m_ExportFormatterPL.createBildElement(strTagName, weramedia, strPrio, strDirectory, strCodeNrProduct);
		} else {
			m_bildXML = this._createBildElement(strTagName, weramedia, strPrio, strDirectory, strCodeNrProduct);
		}

		return m_bildXML;
	}

	/**
	 *
	 * @param strTagName
	 * @param weramedia
	 * @param strPrio
	 * @param strDirectory
	 * @param strCodeNrProduct
	 * @return
	 */
	private Element _createBildElement(String strTagName, WeraMedia weramedia, String strPrio, String strDirectory, String strCodeNrProduct) {
		Element oMotive = new Element("Motive");

		// --- Initialize
		Double dBildRahmenBreiteDelta = new Double(0);
		String strCode = "";
		String strRealName = "";
		if (weramedia != null) {

			strCode = weramedia.getCode();
			strRealName = weramedia.getRealFileName();
			if (strRealName != null) {
				strRealName = strRealName.replaceAll(".jpg", ".eps");
				strRealName = strRealName.replaceAll(".gif", ".eps");
			}

			// ---- bild 1
			if (strTagName.equals("PICTURE1")) {

				// --- hole rahmenbreite in %
				Double dBildRahmenBreiteP = (Double) m_wm.getAttribute(weramedia, "rahmenbreite_in_percent");
				if (dBildRahmenBreiteP != null && dBildRahmenBreiteP.doubleValue() > 0) {
				
					// --- brutto / netto - Template?
					String strTemplate	=	this.m_strCurrentTemplateName.toLowerCase();
					String strConfigName	=	"";
					if ( strTemplate.contains("1") ) {
						strConfigName	=	"wera.print_template_pricelistbrutto_mm_bild1";
					} else {
						strConfigName	=	"wera.print_template_pricelistnetto_mm_bild1";
					}
					// --- hole die rahmenbreiten aus der config-datei
					// String strRahmenBreiteMM_Bild1 = Config.getParameter( strConfigName );
					// --- staffel-Preisliste
					LOG.info("m_cTemplateInfo=" + m_cTemplateInfo);
					String strRahmenBreiteMM_Bild1 = "27";
					if ( m_cTemplateInfo == '1' ) {
						strRahmenBreiteMM_Bild1 = "20"; // derzeit in Templates hinterlegter Wer

					}
					//if ( strRahmenBreiteMM_Bild1 == null ) {
					//	strRahmenBreiteMM_Bild1 = "20";
					//}
					// --- ermittle die neue rahmenbreite in mm (nur das delta da IB keine absoluten werde verarbeitet)
					try {
						if (strRahmenBreiteMM_Bild1 != null) {

/**
 * Mail RH: 07.12.2020
 * Zu Ihren Punkten unten:
 Produktbilder
 Wäre es nicht möglich (gewesen), dass die Templates der Preislisten (Gesamt und Aktion) den in hybris hinterlegten prozentualen
 Ausgabewert anders interpretieren als die Templates für den Kompaktkatalog?
 Anders ausgedrückt: 100% Kompaktkatalog = 100 % Preislisten, 50 % Kompaktkatalog = 100 % Preislisten usw.?
 Wenn das ginge, wäre das Thema für bestehende und für neue Bilder gelöst, und wir würden nicht wieder von vorne mit dem Thema anfangen.
 siehe ===> (1)
 */
							Double dCurrentRahmenBreiteInMM = new Double(strRahmenBreiteMM_Bild1) / 1.2; // <==== (1)
							Double dNewBildRahmenBreite		= (dBildRahmenBreiteP / 100) * dCurrentRahmenBreiteInMM;
							dBildRahmenBreiteDelta			= dCurrentRahmenBreiteInMM - dNewBildRahmenBreite;
							if ( dBildRahmenBreiteDelta >= dCurrentRahmenBreiteInMM ) {
								dBildRahmenBreiteDelta	= 0.0;
							}
						}


					} catch (Exception e) {
					}
					LOG.info("m_cTemplateInfo=" + m_cTemplateInfo + ", strRahmenBreiteMM_Bild1=" + strRahmenBreiteMM_Bild1 + ", WidthP=" + dBildRahmenBreiteDelta.toString() );

				} // --- if (dBildRahmenBreiteP != null && dBildRahmenBreiteP.doubleValue() > 0) {

			} // --- if (strTagName.equals("PICTURE1")) {
		}

		// --- Neues Element anlegen
		// --- <Motive Id="0" Type="ATTRIBUTE_IMAGE" LinkType="ATTRIBUTE_IMAGE" RefCode="ATTR_aussen_sechskant_durchmesser" Thumbnail="" Sequence="0" TypeName="ATTRIBUTE_IMAGE" LinkTypeName="ATTRIBUTE_IMAGE" Name="Leertext" Keywords="">
		oMotive.setAttribute("Id", strCode);
		oMotive.setAttribute("TypeName", strTagName);
		oMotive.setAttribute("Sequence", strPrio);
		oMotive.setAttribute("WidthP", dBildRahmenBreiteDelta.toString());

		// --- <File-List>
		Element oFileList = new Element("File-List");

		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		if (strTagName.equals("PICTURE1")) {
			oFileList.addContent(_initImage(weramedia, strDirectory, strCodeNrProduct,".jpg"));
		} else {
			oFileList.addContent(_initImage(weramedia, strDirectory, strCodeNrProduct,".eps"));
		}
		
		// --- Setze zusammen
		oMotive.addContent(oFileList);

		return oMotive;
	}
	/**
	 *
	 * @param strTagName
	 * @param weramedia
	 * @param strPrio
	 * @param strDirectory
	 * @param strCodeNrProduct
	 * @return
	 */
	protected Element createBildreferenzElement(final String strTagName, final Object bildreferenz, final String strPrio, final String strDirectory ) {

		// --- Initialize
		Double dBildRahmenBreiteDelta = new Double(0);
		Double dBildRahmenBreiteP = (Double)100.0;
		//String strCode = "";
		//String strRealName = "";
		if (bildreferenz != null) {
/*
			strCode = weramedia.getCode();
			strRealName = weramedia.getRealFileName();
			if (strRealName == null ) { strRealName	= ""; }
			if ( strTagName.contains("FEATRUREICONS") ) {
				strRealName = strRealName.replaceAll(".jpg", ".eps");
				strRealName = strRealName.replaceAll(".gif", ".eps");
			} else {
				strRealName = strRealName.replaceAll(".eps", ".jpg");
			}
*/
			// --- hole rahmenbreite in %
			if (dBildRahmenBreiteP != null && dBildRahmenBreiteP.doubleValue() > 0) {

				// ---- bild 1
				if (strTagName.equals("PICTURE1")) {
					// --- hole die rahmenbreiten aus der config-datei
					String strRahmenBreiteMM_Bild1 = Config.getParameter("wera.print_template_" + m_strCurrentTemplateName.toLowerCase() + "_rahmenbreite_mm_bild1");
					if (strRahmenBreiteMM_Bild1 == null) {
						strRahmenBreiteMM_Bild1 = Config.getParameter("wera.print_template_product_rahmenbreite_mm_bild1");
					}

					// --- ermittle die neue rahmenbreite in mm (nur das delta da IB keine absoluten werde verarbeitet)
					try {
						if (strRahmenBreiteMM_Bild1 != null) {

							Double dCurrentRahmenBreiteInMM = new Double(strRahmenBreiteMM_Bild1);
							Double dNewBildRahmenBreite = (dBildRahmenBreiteP / 100) * dCurrentRahmenBreiteInMM;
							dBildRahmenBreiteDelta = dCurrentRahmenBreiteInMM - dNewBildRahmenBreite;
						}
					} catch (Exception e) {
					}
				}

				// --- bild 2
				if (strTagName.equals("PICTURE2")) {
					// --- hole die rahmenbreiten aus der config-datei
					String strRahmenBreiteMM_Bild2 = Config.getParameter("wera.print.template_" + m_strCurrentTemplateName + ".rahmenbreite_mm_bild2");
					if (strRahmenBreiteMM_Bild2 == null) {
						strRahmenBreiteMM_Bild2 = Config.getParameter("wera.print.template_product.rahmenbreite_mm_bild2");
					}

					// --- ermittle die neue rahmenbreite in mm (nur das delta da IB keine absoluten werde verarbeitet)
					try {
						if (strRahmenBreiteMM_Bild2 != null) {

							Double dCurrentRahmenBreiteInMM = new Double(strRahmenBreiteMM_Bild2);
							Double dNewBildRahmenBreite = (dBildRahmenBreiteP / 100) * dCurrentRahmenBreiteInMM;
							dBildRahmenBreiteDelta = dCurrentRahmenBreiteInMM - dNewBildRahmenBreite;
						}
					} catch (Exception e) {
					}
				}
			}
		}


		// --- <File-List>
		final Element oFileList = new Element("File-List");

		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		String strLocation = "";
		if ( bildreferenz instanceof Item ) {
			
			strLocation = (String) m_wm.getAttribute((Item)bildreferenz, "location");
			
		} else {
			
			strLocation = (String) ((HashMap) bildreferenz).get( "location" );
		}
		if (strTagName.contains("PICTURE")) {
			
			final Element oFile = new Element("File");
			oFile.setAttribute("PrintFileName", strDirectory + "/" + strLocation );
			oFileList.addContent( oFile );
			
		} else {
			
			final Element oFile = new Element("File");
			oFile.setAttribute("PrintFileName", strDirectory + "/" + strLocation );
			oFileList.addContent( oFile );
		}

		// --- Neues Element anlegen
		// --- <Motive Id="0" Type="ATTRIBUTE_IMAGE" LinkType="ATTRIBUTE_IMAGE" RefCode="ATTR_aussen_sechskant_durchmesser" Thumbnail="" Sequence="0" TypeName="ATTRIBUTE_IMAGE" LinkTypeName="ATTRIBUTE_IMAGE" Name="Leertext" Keywords="">
		final Element oMotive = new Element("Motive");
		oMotive.setAttribute("Id", strLocation );
		oMotive.setAttribute("TypeName", strTagName);
		oMotive.setAttribute("Sequence", strPrio);
		oMotive.setAttribute("WidthP", dBildRahmenBreiteDelta.toString());

		// --- Setze zusammen
		oMotive.addContent(oFileList);

		return oMotive;
	}

	/**
	 * 
	 * @param weraProductSet
	 * @param oTexList
	 * @param oLocalRefId
	 * @return 
	 */
	@Override
	public Element createContentElement(final WeraProductSet weraProductSet, final Element oTexList, Integer oLocalRefId) {
		if (m_ExportFormatterPL instanceof ExportFormatterPL4FO) {
			// --- Initialize
			Element oFirstElement = null;
			WeraMedia icon1 = null;
			WeraMedia icon2 = null;
			ArrayList aContent = null;
			ArrayList colHash = null;
			HashMap oHashMapProdukt = null;
			HashMap oHashMapArtikel = null;
			aContent = weraProductSet.generateWeraProductSetData();
			//weraProductSet.debugOutWeraProductSetData();

			String strTypeName = "";
			String strTagContent = "";
			int iPos = 0;
			//Random oRandom = new Random( 100 );
			//int iID = oRandom.nextInt();
			//if ( iID < 0 )
			//	iID *= -1;

			// --- Schleife �ber alle Content-Inhalte
			if (aContent != null && aContent.size() > 0) {
				for (final Iterator it1 = aContent.iterator(); it1.hasNext();) {

					// --- Hole Map
					oHashMapProdukt = (HashMap) it1.next();
					iPos++;

					// --- Initialize
					strTypeName = (String) oHashMapProdukt.get("code");
					icon1 = (WeraMedia) oHashMapProdukt.get("icons1");
					icon2 = (WeraMedia) oHashMapProdukt.get("icons2");
					strTagContent = "";

					colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
					if (colHash != null && colHash.size() > 0) {
						int iPos1 = 0;
						for (final Iterator it2 = colHash.iterator(); it2.hasNext();) {
							// --- Hole Map
							iPos1++;
							oHashMapArtikel = (HashMap) it2.next();
							if (oHashMapArtikel != null) {
								if (iPos1 < colHash.size()) {
									strTagContent += "" + oHashMapArtikel.get("value") + "; ";
								} else {
									strTagContent += "" + oHashMapArtikel.get("value") + "; ";
								}
							}
						}
					}

					// --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
					if ( m_strLanguage.equals("en") || m_strLanguage.equals("us-en")) {
						strTagContent = strTagContent.replace(",", ".");
					} else {
						strTagContent = strTagContent.replace(".", ",");
					}

					// --- Content korrigieren
					if (strTagContent.length() > 2) {
						strTagContent = strTagContent.trim();
					}

					// --- Formatmarken f�r Indesign
					strTagContent = "" + strTagContent;
					strTagContent = strTagContent + "";

					// --- Hochstellen des Registerzeichens
/////////////////////////// 
// entfernt template-optimierung					
//					strTypeName = strTypeName.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
//					strTagContent = strTagContent.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
/////////////////////////// 

					
					// --- Zusammenhalten einer Zeile
					final Element textXML = _createTextElement(strTypeName, strTagContent , new Integer(iPos), oLocalRefId, "BLT_SET", false);
					m_iOffsetIDEbene3++;
					oTexList.addContent(textXML);

					// --- 1. Textelement merken
					if (oFirstElement == null) {
						oFirstElement = textXML;
					}

					final String strDirectory = Config.getParameter("media.replication.dirs");
					// --- Icon 1 
					if (icon1 != null) {
						//Element oIcon1 = _initMerkmalIcon(null,null,icon1,null, "icons", "1" );
						final Element oIcon1 = m_ExportFormatterPL.createBildElement("ICON1", icon1, "1", strDirectory, "");
						oIcon1.setAttribute("Sequence", "1");
						textXML.addContent(oIcon1);
					}

					// --- Icon 2 
					if (icon2 != null) {
						//Element oIcon2 = _initMerkmalIcon(null,null,icon2,null, "icons", "2" );
						final Element oIcon2 = m_ExportFormatterPL.createBildElement("ICON2", icon2, "2", strDirectory, "");
						oIcon2.setAttribute("Sequence", "2");
						textXML.addContent(oIcon2);
					}

				} // --- for ( Iterator it1 = aContent.iterator(); it1.hasNext(); ) {

			} // --- if ( aContent != null && aContent.size() > 0 ) {

			return oFirstElement;
		} else {
			// TODO Auto-generated method stub
			LOG.info("SET.BLT_SET oLocalRefId=" + oLocalRefId.intValue());
			return super.createContentElement(weraProductSet, oTexList, oLocalRefId);
		}
	}

	/**
	 * 
	 * @param weraProductSet
	 * @param oTexList
	 * @param oLocalRefId
	 * @param nCountProductSet
	 * @return 
	 */
	@Override
	public Element createContentElementSetinSet(final WeraProductSet weraProductSet, final Element oTexList, Integer oLocalRefId, int nCountProductSet) {
		if (m_ExportFormatterPL instanceof ExportFormatterPL4FO) {
			// --- Initialize
			Element oFirstElement = null;
			WeraMedia icon1 = null;
			WeraMedia icon2 = null;
			ArrayList aContent = null;
			ArrayList colHash = null;
			HashMap oHashMapProdukt = null;
			g_oHashMapCodeNr = new HashMap();
			HashMap oHashMapArtikel = null;
			aContent = weraProductSet.generateWeraProductSetData();

			String strTypeName = "";
			String strTagContent = "";
			int iPos = 0;

			// --- Schleife �ber alle Content-Inhalte
			if (aContent != null && aContent.size() > 0) {
				for (final Iterator it1 = aContent.iterator(); it1.hasNext();) {
					// --- Hole Map
					oHashMapProdukt = (HashMap) it1.next();
					iPos++;

					// --- code
					strTypeName = (String) oHashMapProdukt.get("code");
					LOG.info ( "createContentElementSetinSet Produkt.getCode()=" + oHashMapProdukt.get("code"));
					g_oHashMapCodeNr.put(iPos, strTypeName);

					// --- icons
					icon1 = (WeraMedia) oHashMapProdukt.get("icons1");
					icon2 = (WeraMedia) oHashMapProdukt.get("icons2");
					strTagContent = "";

					colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
					if (colHash != null && colHash.size() > 0) {
						int iPos1 = 0;
						for (final Iterator it2 = colHash.iterator(); it2.hasNext();) {
							// --- Hole Map
							iPos1++;
							oHashMapArtikel = (HashMap) it2.next();
							if (oHashMapArtikel != null) {
								if (iPos1 < colHash.size()) {
									strTagContent += "" + oHashMapArtikel.get("value") + "; ";
								} else {
									strTagContent += "" + oHashMapArtikel.get("value") + "; ";
								}
							}
						}
					}

					// --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
					if ( m_strLanguage.equals("en") || m_strLanguage.equals("us-en")) {
						strTagContent = strTagContent.replace(",", ".");
					} else {
						strTagContent = strTagContent.replace(".", ",");
					}

					
					// --- Content korrigieren
					if (strTagContent.length() > 2) {
						strTagContent = strTagContent.trim();
					}

					// --- Formatmarken f�r Indesign
					strTagContent = "" + strTagContent;
					strTagContent = strTagContent + "";

					// --- Hochstellen des Registerzeichens
/////////////////////////// 
// entfernt template-optimierung					
//					strTypeName = strTypeName.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
//					strTagContent = strTagContent.replace("\u00ae", "##sub##" + "\u00ae" + "##e_sub##");
/////////////////////////// 

					// --- NEU -----------------------------------------------------------------------------------
					System.out.println("oHashMapProdukt #########################");
					System.out.println(oHashMapProdukt);
					System.out.println("#########################");
/*
					Boolean neu	= (Boolean)m_wm.getAttribute(oHashMapProdukt, "produkt_neu");
					if ( neu != null && neu.booleanValue() ) {
						
						String strNewMsg	= m_ExportFormatterPL.strGetNew(m_strLanguage, neu.booleanValue() );
						strTagContent		+= strNewMsg;
					}
*/
					// --- NEU -----------------------------------------------------------------------------------

					// --- Zusammenhalten einer Zeile
					final Element textXML = _createTextElement(strTypeName, strTagContent, new Integer(iPos), oLocalRefId, "BLT_SET", false);
					m_iOffsetIDEbene3++;
					oTexList.addContent(textXML);

					// --- 1. Textelement merken
					if (oFirstElement == null) {
						oFirstElement = textXML;
					}

					final String strDirectory = Config.getParameter("media.replication.dirs");
					// --- Icon 1 
					if (icon1 != null) {
						//Element oIcon1 = _initMerkmalIcon(null,null,icon1,null, "icons", "1" );
						final Element oIcon1 = m_ExportFormatterPL.createBildElement("ICON1", icon1, "1", strDirectory, "");
						oIcon1.setAttribute("Sequence", "1");
						textXML.addContent(oIcon1);
					}

					// --- Icon 2 
					if (icon2 != null) {
						//Element oIcon2 = _initMerkmalIcon(null,null,icon2,null, "icons", "2" );
						final Element oIcon2 = m_ExportFormatterPL.createBildElement("ICON2", icon2, "2", strDirectory, "");
						oIcon2.setAttribute("Sequence", "2");
						textXML.addContent(oIcon2);
					}

				} // --- for ( Iterator it1 = aContent.iterator(); it1.hasNext(); ) {

			} // --- if ( aContent != null && aContent.size() > 0 ) {

			return oFirstElement;
		} else {
			// TODO Auto-generated method stub
			LOG.info("SIS.BLT_SET oLocalRefId=" + oLocalRefId.intValue());
			return super.createContentElementSetinSet(weraProductSet, oTexList, oLocalRefId);
		}
	}
}

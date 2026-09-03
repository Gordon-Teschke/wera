package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.GeneratedWeraConstants;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.europe1.enums.UserPriceGroup;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.util.Config;
import org.apache.log4j.Logger;
import org.jdom.Attribute;
import org.jdom.Comment;
import org.jdom.Element;

import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

//Wrapperklassen f�r formatierte Ausgabe der Bildpfade
//Standardklasse ist ExportFormatter f�r den XML Export f�r die Katalogausgabe
//Sie liefert stets den Eingabestring unver�ndert zur�ck
public class WeraPricelistNeutral extends MediandoXml {

	/**
	 * Edit the local|project.properties to change logging behavior (properties
	 * 'log4j.*').
	 */
	private static final Logger LOG = Logger.getLogger(WeraPricelistNeutral.class.getName());


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
	Product m_articleOrg = null;
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
	private Collection<String> m_pricelists		= null;
	private Collection<String> m_kontrollCSV	= null;
	String m_strOutputFilePath	= "";
	private HashMap<String,FileWriter> m_hashMapFileWriterScript = null;
	private HashMap<String,Integer> m_hashMapFileWriterAnzahlPreise = null;
	private HashMap<String,Item> m_hashMapFileWriterUserPriceGroup = null;

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
	public WeraPricelistNeutral() {
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
			System.out.println( strTmpCode + ", skipped, product not allowd for current catalogversion");
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


		// --- Holen der Sprache ---------------------------------------------------------------------------------------------
		// --- Hole Sprache (default)
		m_strLanguage = m_strExportLanguage = "de";

		final Collection<Language> languages = weraCatalogVersion.getLanguages();
		final Collection<Item> userpriceGroups = (Collection<Item>)m_wm.getAttribute( weraCatalogVersion, "upg4csv" );
		m_languages	= new ArrayList();
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
		// --- Holen der Sprache ---------------------------------------------------------------------------------------------


		// --- Erzeuge die Export-Ablage
		m_strOutputFilePath = CreateOutputPath(strFileDatum, m_strLanguage);;

		// --- schleife über alle preislisten ------------------------------------------------------------------------
		m_hashMapFileWriterScript			= new HashMap();
		m_hashMapFileWriterAnzahlPreise		= new HashMap();
		m_hashMapFileWriterUserPriceGroup	= new HashMap();
		for (String strPricelist: m_pricelists) {

			try {

				// --- anlegen der scriptdateien
				final FileWriter oFileWriterScript = new FileWriter(m_strOutputFilePath + "/" + strPricelist + ".json");

				// --- JSON Klammer Start
				oFileWriterScript.write( "{\r\n");

				// --- merken
				m_hashMapFileWriterAnzahlPreise.put ( strPricelist, new Integer(0) );
				m_hashMapFileWriterScript.put( strPricelist, oFileWriterScript);

				// --- suche die Preisliste
				for (Item userpriceGroup: userpriceGroups) {

					// --- aktuelle Preisliste?
					String codePL	= (String)m_wm.getAttribute( userpriceGroup, "code" );
					if ( codePL.equals(strPricelist) ) {
						// --- Preisliste gefunden?
						m_hashMapFileWriterUserPriceGroup.put(strPricelist, userpriceGroup);
						break;
					}
				}

			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


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
				strOutput = "Ausgabe der Produkte nach:" + m_strOutputFilePath + "/" + m_strXML_File;
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
											_InitializeProductLinkXML(strCatalogPages, true, true, true);
										} else {
											// --- 1. - n.Variante
											_InitializeProductLinkXML(strCatalogPages, true, false, true);
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
				writeDocument(m_strOutputFilePath, "/" + m_strXML_File);

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
			final FileWriter m_oFileWriterLog = new FileWriter(m_strOutputFilePath + "/pricelist.log");
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


		// --- Scriptdateien (Indesign)
		// --- schleife über alle preislisten
		for (String strPricelist: m_pricelists) {

			try {

				// --- schliessen der scriptdateien
				FileWriter oFileWriterScript	= m_hashMapFileWriterScript.get( strPricelist );
				Item userPriceGroup 			= (Item)m_hashMapFileWriterUserPriceGroup.get ( strPricelist );
				Boolean preiseMitKomma			= (Boolean)m_wm.getAttribute( (Item)userPriceGroup, "preise_mit_komma");
				if ( preiseMitKomma == null ) {
					preiseMitKomma	= new Boolean(true);
				}

				// --- JSON Klammer abschluß
				Integer anzahlPreise = m_hashMapFileWriterAnzahlPreise.get ( strPricelist );
				oFileWriterScript.write(  "\"anzahl_preise\":\"" + anzahlPreise.intValue() + "\",\r\n" );
				oFileWriterScript.write(  "\"namePricelist\":\"" + strPricelist + "\", \r\n" );
				if ( preiseMitKomma.booleanValue() ) {

					oFileWriterScript.write(  "\"preiseMitKomma\":\"true\"" );
				} else {

					oFileWriterScript.write(  "\"preiseMitKomma\":\"false\"" );
				}
				oFileWriterScript.write( "\r\n}\r\n");

				// --- datei schliessen
				oFileWriterScript.close();

			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}


		// --- Kontroll-File II
		try {
			// --- �ffnen der LOG-Datei
			String strLine = "";
			final FileWriter m_oFileWriterKontroll = new FileWriter(m_strOutputFilePath + "/" + m_strXML_File.replaceAll(".xml",".csv") );
			LOG.info("m_kontrollCSV. exportfilename=" + m_strOutputFilePath + "/" + m_strXML_File.replaceAll(".xml",".csv") );
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
			final FileWriter m_oFileWriterLog = new FileWriter(m_strOutputFilePath+ "/produktlist.log");
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

		return m_strOutputFilePath;

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

LOG.info("" );
LOG.info("_InitializeProductLinkXML ---------------------------" );
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


		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(m_strExportLanguage);


		m_oTreegroupLink.addContent(m_oProductLink);
		String strArtNr = (String) m_wm.getAttribute(m_product, "code");
		Comment nameComment = new Comment(strArtNr + "\t" + m_strProduktName);
		m_oTreegroupLink.addContent(nameComment);

		// --- F�lle ProduktListe ( neues Produkt => Categorien )
		final Element produktXML = new Element("Product");
		m_oProductList.addContent(produktXML);

		// --- produkt nicht ausgeben, vorbereiten, sobald Preise enthalten sind wird das auf 0 gesetzt
		produktXML.setAttribute("hideProduct", "1");


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

		// --- Produkttitle hinzufügen
		_setTitle( m_product );


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
		// produktXML.setAttribute("Name", m_strProduktName);
		// produktXML.setAttribute("NameEN", m_strProduktNameEN);

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
				// produktXML.setAttribute("Katalogseite", (String) m_oPageNr);
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
				m_articleOrg = m_article;
				m_article = weraproductset;

				// --- prüfe ob der produkt exportiert werden darg
				if ( m_wm.getVisibilityForCatalog( weraproductset, weraCatalogVersion, false ) == -1 ) {

					String strTmpCode = (String) weraproductset.getAttribute("code");
					LOG.info( strTmpCode + ", skipped, SIS weraproductset not allowed for current catalogversion");
					continue;
				}

				//
				LOG.info(">>>>>>>>SATZ aus SIS code=" + weraproductset.getCode() + " refID=" + oRefId.intValue() + ", colWeraProductSet.size()=" + colWeraProductSet.size() );
				_initWeraProductVariante(colAllFootnotes, null, null, oRefId, intContentQuantitySiS, colWeraProductSet.size(), produktXML, m_articleOrg );

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
				m_articleOrg = m_article;
				m_article = m_product;

				// --- Gibt einen String mit Notizen zur�ck
				strFootnote = _initWeraProductVariante(colAllFootnotes, null, null, oRefId, null, 1, produktXML, m_articleOrg);

			} else {
				// --- Setze Sprache, und Defaultsprache=de
				initLanguage("de");

				// --- Hole alle Varianten, die eine akitve Preisliste haben!!!!!
				final Collection articles = ((WeraProduct) m_product).getPriceListVariants();

				// --- Setze Sprache, und Defaultsprache=de
				initLanguage(m_strExportLanguage);

				// --- Hole Abmessung
				ArrayList aArticles = null;
				HashMap localizedArticleData = null;
				if ("PRODUCTLOCAL3".equalsIgnoreCase(m_strCurrentTemplateName)) {
					localizedArticleData = _getLocalizedArticleData(articles);
					aArticles = (ArrayList)localizedArticleData.get(m_strExportLanguage);
					if (aArticles == null) {
						aArticles = (ArrayList)localizedArticleData.get("de");
					}
				} else {
					aArticles = ((WeraProduct) m_product)._genCADataForVariantList(articles, null);
				}

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
					strFootnote = _initWeraProductVariante(colAllFootnotes, aArticles, localizedArticleData, oRefId, null, 1, produktXML, m_article);
				}

				// --- Aufr�umen
				if (aArticles != null) {
					aArticles.clear();
				}
				if (localizedArticleData != null) {
					localizedArticleData.clear();
				}

			} // --- if ( m_product instanceof WeraProductSet ) {

		} // --- if ( m_product instanceof WeraProductSetinSet) {

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(m_strExportLanguage);

		// --- Alle gesammelten Fussnoten ins Produkt �bernehmen
		// --- Schleife �ber alle Fussnoten
		Element textXML1 = null;
		Footnote oFootnote = null;
		String strFN = "";
LOG.info("Fussnoten - colAllFootnotes=" + colAllFootnotes.size() );
		for (final Iterator it2 = colAllFootnotes.iterator(); it2.hasNext();) {
			// --- Hole ProfiClassAttribute
			oFootnote = (Footnote) it2.next();

			// --- Neues XML-Element
			strCode = oFootnote.getAttribute("code").toString();
LOG.info("Fussnoten - colAllFootnotes.strCode=" + strCode );

			// --- Artikel entfernen
			if (strCode.contains("_")) {
				strCode = strCode.substring(strCode.indexOf('_') + 1);
			}

			// --- schleife über alle sprachen -------------------------------------------------------------------------------------------
			Element textFootnote	= null;
			for (String language : m_languages) {

				// --- sprache setzen
				initLanguage(language);

				// --- Notiz initialisieren
				m_iOffsetIDEbene3++;
				strFN = getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
				strFN = strFN.trim();
				if ( language.equals( "de") ) {
					initLanguage("en");
					strFN += "\n" + getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
					strFN = strFN.trim();
					initLanguage(language);
				}
				if ( textFootnote == null ) {

					textFootnote = createTextElementWithLanguage(new Integer(oFootnote.getLfdNr()).toString() + ")", strFN, new Integer(m_iOffsetIDEbene3),
							new Integer(m_iOffsetIDEbene3), "BLT_FN", true, language);

					// --- �bernehme Notiz in das Produkt
					m_oTextList.addContent(textFootnote);

				} else {

					// --- fussnotentext in neuer sprache
					Element textElement = new Element("TextBlock");
					textElement.setAttribute("Language",language);
					textElement.addContent( strFN);
					textFootnote.addContent( textElement );
				}


			} // for (String language : m_languages) {
			// --- schleife über alle sprachen -------------------------------------------------------------------------------------------

		}

		// --- Hole Fussnoten die am Produkt liegem = >Schleife �ber alle Attribute f�r Artikel
		final Collection footnotes = ((GeneratedWeraProduct) m_product).getFootnotes();
LOG.info("Fussnoten - footnotes am Produkt=" + footnotes.size() );
		oFootnote = null;
		strFN = "";
		String strKennz = "";
		for (final Iterator it2 = footnotes.iterator(); it2.hasNext();) {
			// --- Hole ProfiClassAttribute
			oFootnote = (Footnote) it2.next();
LOG.info("Fussnoten (am Produkt) - footnotes.code=" + oFootnote.getAttribute("code").toString() );

			// --- schleife über alle sprachen -------------------------------------------------------------------------------------------
			Element textFootnote	= null;
			for (String language : m_languages) {

				// --- sprache setzen
				initLanguage(language);

				// --- Notiz initialisieren
				strFN = getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
				strFN = strFN.trim();
				if ( language.equals("de") ) {
					initLanguage("en");
					strFN += "\n" + getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
					strFN = strFN.trim();
					initLanguage(language);
				}
				strKennz = (String) m_wm.getAttribute(oFootnote, "kennzeichner");
LOG.info("Fussnoten (am Produkt) - footnotes.strKennz=" + strKennz );
				//if (!strKennz.equals("*")) {

					m_iOffsetIDEbene3++;
					if ( textFootnote == null ) {
LOG.info("Fussnoten (am Produkt) - footnotes.strFN=" + strFN );

						if (strKennz != null) {
							textFootnote = createTextElementWithLanguage(m_wm.getAttribute(oFootnote, "kennzeichner") + ")", strFN, new Integer(m_iOffsetIDEbene3),
									new Integer(m_iOffsetIDEbene3), "BLT_FN", true, language);
						} else {
							textFootnote = createTextElementWithLanguage(")", strFN,
									new Integer(m_iOffsetIDEbene3), new Integer(m_iOffsetIDEbene3), "BLT_FN", true, language);
						}

						// --- �bernehme Notiz in das Produkt
						m_oTextList.addContent(textFootnote);

					} else {

						// --- fussnotentext in neuer sprache
						Element textElement = new Element("TextBlock");
						textElement.setAttribute("Language",language);
						textElement.addContent( getValidString(oFootnote.getName(m_jaloSession.getSessionContext())) );
						textFootnote.addContent( textElement );

					}

				//}

			} // for (String language : m_languages) {
			// --- schleife über alle sprachen -------------------------------------------------------------------------------------------


		} // --- for (Iterator it2 = footnotes.iterator(); it2.hasNext();) {

		// --- Mind. 1 Fussnote
		if ((footnotes == null || footnotes.size() == 0) && (colAllFootnotes == null || colAllFootnotes.size() == 0)) {

			// --- Dummy Fussnote
			m_iOffsetIDEbene3++;
			m_oTextItem = _createTextElement(" ", " ", new Integer(m_iOffsetIDEbene3), new Integer(m_iOffsetIDEbene3), "BLT_FN",
					true);
			m_oTextList.addContent(m_oTextItem);
		}

		// --- abschluss
		String hideProduct = (String)produktXML.getAttribute ("hideProduct").getValue();
		if ( hideProduct.equals("1")) {

			String typeName = (String)produktXML.getAttribute ("TypeName").getValue();
			typeName = "hide_" + typeName;

			produktXML.setAttribute ("TypeName", typeName);
			produktXML.setAttribute ("LinkedType", typeName);
			m_oProductLink.setAttribute ("LinkedTypeName", typeName );
			m_oProductLink.setAttribute ("LinkedType", typeName );
		}
	}


	/**
	 * Produkttitle hinzufügen
	 *
	 * @param product
     */
	private void _setTitle( Product product ) {

		// --- preset
		String strProduktName	= "";
		String strProduktNameEN	= "";

		// --- Element aufbauen
		Element textXML = new Element("Text");
		textXML.setAttribute("Id", "1");
		textXML.setAttribute("Type", "BLT_TITLE");
		textXML.setAttribute("LinkType", "BLT_TITLE");
		m_oTextList.addContent(textXML);

		// --- schleife über alle sprachen
		for (String language : m_languages) {

			// --- sprache setzen
			initLanguage(language);

			// --- produktname holen
			strProduktName = (String) m_wm.getAttribute(product, "name");
			if (strProduktName == null) {
				strProduktName = "??";
			}

			// --- Setze Sprache, und De
			if ( language.equals("de") ) {

				// --- hole name in en
				initLanguage("en");
				strProduktNameEN = (String) m_wm.getAttribute(product, "name");

				if (strProduktNameEN == null) {
					strProduktNameEN = "??";
				}
				if ( !strProduktName.trim().toLowerCase().equals(strProduktNameEN.trim().toLowerCase()) ) {
					strProduktName = strProduktName + "\n" + strProduktNameEN;
				}
			}

			// --- <TextBlock Language="German">Title</TextBlock>
			Element inhaltXML = new Element("TextBlock");
			inhaltXML.setAttribute("Language", language);
			inhaltXML.addContent(strProduktName);
			textXML.addContent(inhaltXML);

		} // for (String language : Collection<String> languages) {

		// --- sprache zurücksetzen
		initLanguage(m_strExportLanguage);
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

		// --- immer 3
		String cTemplateInfo = "3";
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
					strTemplate = "PRODUCTSETINSET_DISPLAY_SBVARIANT" + cTemplateInfo;
				} else {
					strTemplate = "PRODUCTSETINSET_DISPLAY" + cTemplateInfo;
				}

			} else {
				// --- Satz in Satz Template
				if (bSBVariante) {
					strTemplate = "PRODUCTSETINSET_SBVARIANT" + cTemplateInfo;
				} else {
					strTemplate = "PRODUCTSETINSET" + cTemplateInfo;
				}
			}

		} else {
			if (m_product instanceof WeraProductSet) {
				strTemplate = "PRODUCTSET" + cTemplateInfo;
			} else {
				String strOutputtemplate = (String)m_wm.getAttribute(m_product, "outputtemplate");
				if (strOutputtemplate != null && strOutputtemplate.equals("PRODUCT16")) {
					strTemplate = "PRODUCTLOCAL" + cTemplateInfo;
				} else {
					strTemplate = "PRODUCT" + cTemplateInfo;
				}
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
		//produktXML.setAttribute("Katalogseite", (String) m_oPageNr);
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
		//produktXML.setAttribute("Neu", m_strStringNew);
		if ( bProduktNeu ) {

			produktXML.setAttribute("Neu", "J");
			//produktXML.setAttribute("Neu", m_strStringNew);
		} else {

			produktXML.setAttribute("Neu", "N");
		}

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

		// --- preset
		int iMediaCounter = 1;
		String strMediaCounter = new Integer(iMediaCounter).toString();

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
	private HashMap _getLocalizedArticleData(final Collection articles) {
		final HashMap localizedArticleData = new HashMap();
		final ArrayList languages = new ArrayList();

		try {
			if (m_languages != null) {
				for (final Iterator itLanguage = m_languages.iterator(); itLanguage.hasNext();) {
					final Object oLanguage = itLanguage.next();
					if (oLanguage != null) {
						final String language = oLanguage.toString();
						if (!languages.contains(language)) {
							languages.add(language);
						}
					}
				}
			}
			if (!languages.contains("de")) {
				languages.add("de");
			}
			if (!languages.contains("en")) {
				languages.add("en");
			}

			for (final Iterator itLanguage = languages.iterator(); itLanguage.hasNext();) {
				final String language = (String)itLanguage.next();
				initLanguage(language);
				localizedArticleData.put(language, ((WeraProduct)m_product)._genCADataForVariantList(articles, null));
			}
			LOG.info("PRODUCTLOCAL3 localized article data: " + m_product.getCode() + ", languages=" + _joinLanguages(languages));
		} catch (final Exception e) {
			LOG.error("PRODUCTLOCAL3 localized article data failed: " + m_product.getCode(), e);
		} finally {
			initLanguage(m_strExportLanguage);
		}

		return localizedArticleData;
	}

	private String _joinLanguages(final ArrayList languages) {
		String strResult = "";
		if (languages == null) {
			return strResult;
		}
		for (final Iterator itLanguage = languages.iterator(); itLanguage.hasNext();) {
			if (strResult.length() > 0) {
				strResult += ",";
			}
			strResult += itLanguage.next().toString();
		}
		return strResult;
	}

	private String _getLocalizedArticleValue(final ArrayList localizedArticles, final String articleCode) {
		if (localizedArticles == null || articleCode == null) {
			return "";
		}
		try {
			for (final Iterator it1 = localizedArticles.iterator(); it1.hasNext();) {
				final HashMap hashmap = (HashMap)it1.next();
				if (hashmap == null) {
					continue;
				}
				final ArrayList colHashArtikel = (ArrayList)hashmap.get("colHashArtikel");
				if (colHashArtikel == null) {
					continue;
				}
				for (final Iterator it2 = colHashArtikel.iterator(); it2.hasNext();) {
					final HashMap oHashMapArtikel = (HashMap)it2.next();
					if (oHashMapArtikel == null) {
						continue;
					}
					final Product product = (Product)oHashMapArtikel.get("variant");
					if (product != null && product.getCode().equals(articleCode)) {
						final Object oValue = oHashMapArtikel.get("value_no_quant");
						if (oValue != null) {
							return oValue.toString();
						}
						return "";
					}
				}
			}
		} catch (final Exception e) {
			LOG.error("PRODUCTLOCAL3 localized BLT_ARTICEL lookup failed: article=" + articleCode, e);
		}
		return "";
	}

	private String _cleanProductLocal3ArticleValue(String strValue) {
		if (strValue == null) {
			return "";
		}
		strValue = strValue.replace("--x", "");
		strValue = strValue.replace("-x", "");
		return strValue;
	}

	private Element _createProductLocal3ArticleTextElement(final HashMap localizedArticleData, final String articleCode, final Integer oOrder, final Integer oLocalRefId) {
		String strDE = _cleanProductLocal3ArticleValue(_getLocalizedArticleValue((ArrayList)localizedArticleData.get("de"), articleCode));
		String strEN = _cleanProductLocal3ArticleValue(_getLocalizedArticleValue((ArrayList)localizedArticleData.get("en"), articleCode));
		String strDEmitEN = strDE;
		if (strEN.length() > 0) {
			if (strDEmitEN.length() > 0) {
				strDEmitEN += "\n";
			}
			strDEmitEN += strEN;
		}

		Element oTextItem = createTextElementWithLanguage("", strDEmitEN, oOrder, oLocalRefId, "BLT_ARTICEL", false, "de");
		if (strDE.length() == 0) {
			LOG.info("PRODUCTLOCAL3 missing localized BLT_ARTICEL value: article=" + articleCode + ", language=de");
		}

		if (m_languages != null) {
			for (final Iterator itLanguage = m_languages.iterator(); itLanguage.hasNext();) {
				final Object oLanguage = itLanguage.next();
				if (oLanguage == null) {
					continue;
				}
				final String language = oLanguage.toString();
				if (language.equals("de")) {
					continue;
				}
				String strValue = _cleanProductLocal3ArticleValue(_getLocalizedArticleValue((ArrayList)localizedArticleData.get(language), articleCode));
				if (strValue.length() == 0) {
					LOG.info("PRODUCTLOCAL3 missing localized BLT_ARTICEL value: article=" + articleCode + ", language=" + language);
				}
				Element textElement = new Element("TextBlock");
				textElement.setAttribute("Language", language);
				textElement.addContent(strValue);
				oTextItem.addContent(textElement);
			}
		}

		return oTextItem;
	}

	private String _initWeraProductVariante(final ArrayList colAllFootnotes, final ArrayList articles, final HashMap localizedArticleData, Integer oLocalRefId, Integer intContentQuantitySiS,
											int nCountProductSet, Element produktXML, final Item oArtikel ) {
		// TODO Auto-generated method stub

		LOG.info("_initWeraProductVariante (start) --------------------------------------------");
		// --- Initialize
		final ArrayList aResult = new ArrayList();
		String strResult = "";
		String strCode = "";
		String strPriceValue = "";
		String strUnit = "";
		Element oPrice = null;
		String strKontrollLine	= "";

		try {

			// --- Produkt enthält Verkaufsartikel, also ausgeben!
			produktXML.setAttribute ("hideProduct", "0" );

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

			// --- NEU-Flag beim Produkt setzen wenn ein Artikel neu ist!
			if ( bArtikelNeu.booleanValue() ) {

				produktXML.setAttribute("Neu","J");
			}

			// --- EAN
			String strEAN = (String) m_wm.getAttribute(m_article, "ean");
			if ( strEAN == null  ) { strEAN = ""; }
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
				Element m_oTextItem	= null;
				String strStringNew	= "";
				for (String language : m_languages) {

					if (m_strStringNew.length() == 0) {
						strStringNew = "";
					} else if ( language.equals("en")) {
						strStringNew	= "*NEW*";
					} else if ( language.equals("pl")) {
						strStringNew	= "*NOWY*";
					} else if ( language.equals("it")) {
						strStringNew	= "*NUOVO*";

					} else { // de and other
						strStringNew	= "*NEU / NEW*";
					}

					if ( m_oTextItem == null ) {

						m_oTextItem = createTextElementWithLanguage ("", strStringNew, oOrder, oLocalRefId, "BLT_NEW", false, language);
						m_oTextList.addContent(m_oTextItem);
						if (m_strStringNew.length() == 0) {
							// --- nur ein eintrag
							m_oTextItem.setAttribute("isNew","N");
							break;
						}
						m_oTextItem.setAttribute("isNew","J");

					} else {

						// --- fussnotentext in neuer sprache
						Element textElement = new Element("TextBlock");
						textElement.setAttribute("Language",language);
						textElement.addContent( strStringNew);
						m_oTextItem.addContent( textElement );
					}

				} // for (String language : m_languages) {

			}

			///////////////////////////////////////////////////////////////////////////
			// Seitennummer Platzhalter
			///////////////////////////////////////////////////////////////////////////
			String strSeitennummer	= m_oPageNr.toString();
			if ( m_platzhalterSeitennummer ) {
				
				strSeitennummer			= m_oPageNr.toString();
				strSeitennummer			= strSeitennummer.replaceAll("CODE", strRawCode);
				//produktXML.setAttribute("Katalogseite", strSeitennummer);
				Attribute oIndexArtNr	= (Attribute)produktXML.getAttribute("IndexArtNr" );
				String strIndexArtNr	= oIndexArtNr.getValue().replaceAll("CODE", strRawCode);
				produktXML.setAttribute("IndexArtNr", strIndexArtNr );
			
			} else {
				
				// --- Seitennummer aus Hybris
				strSeitennummer	= m_oPageNr.toString();
			}

			// --- preislisten

			// --- EAN
			// m_oTextItem = createTextElement("", strEAN_short, oOrder, oLocalRefId, "BLT_EAN", false);
			// m_oTextList.addContent(m_oTextItem);
			// m_oTextItem.setAttribute("IndexCode", strRawCode /* + ", " + (String) strSeitennummer */ );
			// m_oTextItem = createTextElement("", strEAN_long, oOrder, oLocalRefId, "BLT_COMPLETTE_EAN", false);
			// m_oTextList.addContent(m_oTextItem);
			// m_oTextItem.setAttribute("IndexCode", strRawCode /* + ", " + (String) strSeitennummer */ );
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
					Item weraProduct = null;
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
							System.out.println( strTmpCode + ", skipped, product not allowd for current catalogversion");
							continue;
						}

						// --- verpackunseinheit hole
						final Integer intWeraproductsetVPE = (Integer) m_wm.getAttribute((Item)cContainer, "vpe");

						// --- BLT_DISPLAYSET anlegen
						setDisplayTitle( (Product) weraProduct, oLocalRefId, "BLT_DISPLAYSET", intWeraproductsetVPE.toString() + "x " );
						oLocalRefId = new Integer(m_iOffsetIDEbene3++);
					}

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

///////////////////////////
// entfernt template-optimierung					
//				m_oTextItem = createTextElement("", m_ExportFormatterPL.strGetTagVersion() + m_ExportFormatterPL.strGetParagraphCodeNr()
//						+ strCodeStrSB + strAbmessung + m_strStringNew, oOrder, oLocalRefId,
//						"BLT_ARTICEL", false);
///////////////////////// 
//				m_oTextItem = createTextElement("", strCodeStrSB + strAbmessung + " " + m_strStringNew, oOrder, oLocalRefId,
//						"BLT_ARTICEL", false);

/*
21.06.2021: ACHTUNG für PRODUCTSETINSET_SBVARIANT3 hier geändert!!
				Element oTextBlock = m_oTextItem.getChild("TextBlock");
				oTextBlock.setAttribute("Id", "codenr");

				m_oTextItem = createTextElement("", strCodeStrSB + strAbmessung, oOrder, oLocalRefId,
						"BLT_ARTICEL", false);
*/
				// --- BTL_ARTIKEL anlegen inkl. 2 Textbl�cke
				m_oTextItem = createTextElement("", strAbmessung, oOrder, oLocalRefId, "BLT_ARTICEL", false);
// System.out.println("++++++++++ codenr:strAbmessung=" + strAbmessung);

/*
21.06.2021: ACHTUNG für PRODUCTSETINSET_SBVARIANT3 hier geändert!!

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
*/

/*
21.06.2021: ACHTUNG für PRODUCTSETINSET_SBVARIANT3 hier geändert!!

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
*/

//				String strTagContentArtikelnr = m_ExportFormatterPL.strGetTagVersion() + m_ExportFormatterPL.strGetParagraph() 
//											+ strCodeStrListe + m_strStringNew;
				//String strTagContentArtikelnr = strCodeStrListeRaw.replace("\u00ae","##sub##" + "\u00ae" + "##e_sub##");
				//String strTagContentArtikelnr = strCodeStrListeRaw.replace( "\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");

/**
 21.06.2021: ACHTUNG für PRODUCTSETINSET_SBVARIANT3 hier geändert!!
 * nocodenr entfernt

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
*/

/**
 * Katalogseiten zur Zeit nicht in der Preisliste enthalten
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
*/

				// --- Setze EAN
				if (m_ExportFormatterPL.m_bIsIB_Exporter == false) {
					m_oTextItem.setAttribute("EAN", strEAN);
				}

				// --- Hole ContentQuantity
				if (intContentQuantitySiS == null) {
					intContentQuantitySiS = new Integer(1);
				}
				m_oTextItem.setAttribute("Unit", intContentQuantitySiS.toString());
				m_oTextItem.setAttribute("EAN", strEAN );

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

					////////////// TEST ////////////////
/*
* auskommentiert, Fussnoten in Satzkomponenten werden laut Mail RH vom 26.08.22 nicht verwendet
*

LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++ TEST");

					final Collection colFootnodesSet = new ArrayList();
					if (((WeraProductSet) m_product).getWeraProductSetFootnotes() != null) {
						colFootnodesSet.addAll(((WeraProductSet) m_product).getWeraProductSetFootnotes());
					}
LOG.info("Fussnoten.colFootnodesSetsize=" + colFootnodesSet.size() );
					for (final Iterator it9 = colFootnodesSet.iterator(); it9.hasNext();) {
						final Footnote oFootnote = (Footnote) it9.next();

						// --- Artikelnummer entfernen
						strCode = oFootnote.getAttribute("code").toString();
						LOG.info(strRawCode + ", FN=" + strCode);
					}
					colAllFootnotes.addAll(colFootnodesSet);
LOG.info("Fussnoten.colAllFootnotes.size=" + colAllFootnotes.size() );
LOG.info("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
*/
					////////////// TEST ////////////////

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
						m_oTextItem = createTextElement("", m_strStringNew, oOrder, oLocalRefId, "BLT_ARTICEL", false);

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
					m_oTextItem.setAttribute("EAN", strEAN );

					// --- Kontrolle VPE
					strKontrollLine += "\t" + intContentQuantity.toString();

				} else {

					//////////////////////////////////////////////////////////////////////////////////
					// PRODUCT
					//////////////////////////////////////////////////////////////////////////////////
					String strAbmessung = "";
					final boolean bProductLocal3 = "PRODUCTLOCAL3".equalsIgnoreCase(m_strCurrentTemplateName);
					if (bProductLocal3 && localizedArticleData != null) {
						strAbmessung = _getLocalizedArticleValue((ArrayList)localizedArticleData.get("de"), strCode);
					} else if (articles != null && articles.size() > 0) {
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

						if (!bProductLocal3) {
							if (m_strExportLanguage.equals("en") || m_strExportLanguage.equals("en-us")) {
								strAbmessung = strAbmessung.replace(',', '.');
							} else {
								strAbmessung = strAbmessung.replace('.', ',');
							}
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
/*
						// --- Neuer Artikel
						if (m_strStringNew.length() > 0) {
							strLineResult += " " + m_strStringNew;
						}
*/
					}
					// LOG.info("################ ABMESSUNG 3:" + m_product.getCode() + "=>" + strLineResult);

//if ( strRawCode == "05029502001" ) {
	// LOG.info("################ Abmessung=" + strAbmessung);
//}

					if (bProductLocal3 && localizedArticleData != null) {
						m_oTextItem = _createProductLocal3ArticleTextElement(localizedArticleData, strCode, oOrder, oLocalRefId);
					} else {
						m_oTextItem = createTextElement("", strLineResult, oOrder, oLocalRefId, "BLT_ARTICEL", false);
					}

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
// System.out.println("########### EAN=" + strEAN );
					m_oTextItem.setAttribute("Unit", intContentQuantity.toString());
					m_oTextItem.setAttribute("EAN", strEAN );

					// --- Kontrolle VPE
					strKontrollLine += "\t" + intContentQuantity.toString();
				}
			}

			m_oTextList.addContent(m_oTextItem);

			// --- CodeNr (05 123456 001)
			String tmpWeraCode	= strWeraCode + "               ";
			m_oTextItem.setAttribute("CodeNr", tmpWeraCode.substring(0,2) + " " + tmpWeraCode.substring(2,8) + " " + tmpWeraCode.substring(8,11) + "??");
			m_oTextItem.setAttribute("CodeNrRaw", tmpWeraCode.trim() );

			// --- Kontrolle EAN
			strKontrollLine += "\t" + strEAN_long;


			//		 --------------------------------------------------------------------------------------
			//		 --- PRICING (START) ------------------------------------------------------------------
			//		 --------------------------------------------------------------------------------------
			// --- Initialize
			String strCodeNr6					= tmpWeraCode.substring(2,8);
			String strCodeNr11					= tmpWeraCode.substring(0,11);;
			int iPreislisteNr 					= 0;
			final int iAnzahlSollNettoPrices 	= 3;
			final Boolean bIsEVKDef 			= new Boolean(false);
			for (String strPricelist: m_pricelists) {

				// --- Initialize
				iPreislisteNr++;
				Double dBruttoPrice 				= null;
				String strBruttoPrice 				= "";
				Double dPrice 						= null;
				Boolean bIsEVK 						= null;
				Boolean bAufAnfrage 				= null;
				String strSymbol 					= "";
				String strIsoCode 					= "";

				//////////////////////////////////////////////////////////////////////////////////////////////////////////
				// Preislisten Steuerparameter laden (START)
				//////////////////////////////////////////////////////////////////////////////////////////////////////////
				// --- hole preislisten gruppe
				Item userPriceGroup 			= (Item)m_hashMapFileWriterUserPriceGroup.get ( strPricelist );

				// --- EVP-Preisliste  ------------------------
				Boolean isBrutto			= (Boolean)m_wm.getAttribute( (Item)userPriceGroup, "isbrutto");
				if ( isBrutto == null ) {
					// --- by Default keine Bruttoliste
					isBrutto	= new Boolean(false);
				}
				// --- EVP-Preisliste  ------------------------

				// --- Anzahl Preiszeilen  --------------------
				Integer maxPreiszeilen			= (Integer)m_wm.getAttribute( (Item)userPriceGroup, "max_preiszeilen");
				if ( maxPreiszeilen == null ) {
					// --- default immer alle zur Sicherheit!
					maxPreiszeilen	= new Integer(3);
				}
				// --- Anzahl Preiszeilen  --------------------
				//////////////////////////////////////////////////////////////////////////////////////////////////////////
				// Preislisten Steuerparameter laden (ENDE)
				//////////////////////////////////////////////////////////////////////////////////////////////////////////


				// --- Preislisten Element anlegen
				// <Text Id="1" Type="BLT_PREISLISTE" LinkType="BLT_PREISLISTE" TypeName="" Sequence="0" preisliste="vertrauliche_staffelpreisliste_2021" preislistnumber="5">
				Element m_oTextItemPL	= new Element("Text");
				m_oTextList.addContent(m_oTextItemPL);
				m_oTextItemPL.setAttribute("Id", oLocalRefId.toString() );
				m_oTextItemPL.setAttribute("Type", "BLT_PREISLISTE");
				m_oTextItemPL.setAttribute("LinkType", "BLT_PREISLISTE");
				m_oTextItemPL.setAttribute("TypeName", "");
				m_oTextItemPL.setAttribute("Sequence", oOrder.toString());
				m_oTextItemPL.setAttribute("isBrutto", isBrutto.toString() );
				m_oTextItemPL.setAttribute("maxPreiszeilen", maxPreiszeilen.toString() );

				// --- staffel oder haendlernetto
				if ( false && strPricelist.toLowerCase().contains("campaign") ) {

					// --- staffelpreisliste
					m_oTextItemPL.setAttribute( "preisliste", "staffel" );

				} else {

					// --- haendlernetto preisliste
					m_oTextItemPL.setAttribute( "preisliste", strPricelist.toLowerCase() );
				}
				m_oTextItemPL.setAttribute( "preislistnumber", String.valueOf(iPreislisteNr) );


				// --- Hole nur die Preise die Exportiert werden sollen
				ArrayList prices = new ArrayList();
				// LOG.info( "Hole Preise=" + strPricelist);
				prices = (ArrayList) _aGetPriceList(m_article, strPricelist);
				LOG.info( "Anzahl  Preise=" + prices.size());

				// --- sind Preise für diesen Artikel vorhanden?
				if (prices != null && prices.size() > 0) {

					// --- Initialize
					PriceRow pricerow 		= null;

					// --- schleife über alle preiszeilen
					PriceRow[] priceRows = {null,null,null,null};
					for (final Iterator it3 = prices.iterator(); it3.hasNext();) {

						// --- GetIt
						pricerow = (PriceRow) it3.next();

						// --- ist der Preis EVK?
						bIsEVK = (Boolean) m_wm.getAttribute(pricerow, "evk");
						if (bIsEVK == null) {
							bIsEVK = bIsEVKDef;
						}

						// --- Währung
						strSymbol = pricerow.getCurrency().getSymbol().toString();
						strIsoCode = pricerow.getCurrency().getIsoCode().toString();

						// --- brutto / netto preis
						if ( !pricerow.isNet() || bIsEVK.booleanValue() ) {

							// --- EVK
							priceRows[3]	= pricerow;

						} else {

							// --- staffelpreis
							Integer iOrder 		= (Integer) m_wm.getAttribute(pricerow, "order");
							int iPos			= (iOrder.intValue() / 10) - 1;
							priceRows[iPos]		= pricerow;
						}
					}

					// --- umsortieren, rechts startent
					for ( int iCounter=2; iCounter >=0; iCounter-- ) {
						if ( priceRows[iCounter] == null  && iCounter > 0 ) {
							priceRows[iCounter]		= priceRows[iCounter-1];
							priceRows[iCounter-1]	= null;
						}
					}



					// --- schleife über alle Preiszeilen
					for ( int iCounter=0; iCounter < 4; iCounter++ ) {

						// --- preset
						boolean isEmptyPrice	= false;

						// --- GetIt
						pricerow = priceRows[iCounter];

						if ( isEmptyPrice=(pricerow ==  null) ) {

							// --- dummy preis
							oPrice = __newPriceElement( strPricelist, iPreislisteNr, strCodeNr11, "PR" + (char) (48 + iCounter + 1), "", "", "", "" );
							m_oTextItemPL.addContent(oPrice);

							// --- zeile übernehmen
							strKontrollLine += "\t" + "" + "\t" + "";
							continue;
						}


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

								// auf anfrage, als leer Preis
								isEmptyPrice	= true;
								strPriceValue 	= " ";
								strUnit 		= " ";
							}

							// --- Preis n
							oPrice = __newPriceElement( strPricelist, iPreislisteNr, strCodeNr11, "PR" + (char) (48 + iCounter + 1), strPriceValue, strUnit, strSymbol, strIsoCode );
							m_oTextItemPL.addContent(oPrice);

							// --- zeile übernehmen
							strKontrollLine += "\t" + strUnit + "\t" + strPriceValue;
						}

					} // for ( iCounter=0; iCounter < 4; iCounter++ ) {

					// --- Preis (Brutto) - PRBRUTTO
					oPrice 			= __newPriceElement( strPricelist, iPreislisteNr, strCodeNr11, "PRBRUTTO", strBruttoPrice, strUnit, strSymbol, strIsoCode );
					m_oTextItemPL.addContent(oPrice);

					// --- zeile übernehmen
					strKontrollLine += "\t" + strBruttoPrice;

				} else {

					// --- keine Preise vorhanden, setze alles dummy preiste
					for ( int iCounter=0; iCounter < iAnzahlSollNettoPrices; iCounter++ ) {

						// --- zeile übernehmen
						strKontrollLine += "\t" + "" + "\t" + "";

						oPrice = __newPriceElement( strPricelist, iPreislisteNr, strCodeNr11, "PR" + (char) (48 + iCounter + 1), "", "", "", "" );
						m_oTextItemPL.addContent(oPrice);
					}

					// --- Preis (Brutto) - PRBRUTTO
					oPrice 			= __newPriceElement( strPricelist, iPreislisteNr, strCodeNr11, "PRBRUTTO", "", "", "", "" );
					m_oTextItemPL.addContent(oPrice);

					// --- zeile übernehmen
					strKontrollLine += "\t" + strBruttoPrice;
				}


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

			if ( oArtikel != null ) {

				final Collection footnotes1 = (Collection) m_wm.getAttribute(oArtikel, "footnotes");
				if ( footnotes1 != null ) {
					LOG.info("Fussnoten._initWeraProductVariante - Anzahl FN Artikel (oArtikel)=" + footnotes1.size());
				}
				if (oArtikel instanceof WeraProductSetinSet) {
					LOG.info("Fussnoten.oArtikel instanceof WeraProductSetinSet" );
				} else {

					if (oArtikel instanceof WeraProductSet) {
						LOG.info("Fussnoten.oArtikel instanceof WeraProductSet" );
					} else if (oArtikel instanceof WeraProduct) {
						LOG.info("Fussnoten.oArtikel instanceof WeraProduct" );
					}
				}
			}

			// --- Holle alle verkn�pften Fussnoten
			final Collection footnotes = (Collection) m_wm.getAttribute(m_article, "footnotes");
LOG.info("Fussnoten._initWeraProductVariante -CODE=" + strRawCode );
LOG.info("Fussnoten._initWeraProductVariante - Anzahl FN Artikel=" + footnotes.size() );
//////////////////////////////////////////////
/*
			if (m_article instanceof WeraProductSetVariants) {
				LOG.info("Fussnoten.m_article instanceof WeraProductSetVariants" );
			}
*/

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
				m_oTextItem.setAttribute("CodeNrRaw", tmpWeraCode.trim() + " " + strResult);
				//		 --------------------------------------------------------------------------------------
				//		 --- Footnotes ------------------------------------------------------------------------
				//		 --------------------------------------------------------------------------------------
			}

		} catch (final Exception e) {
			LOG.info("ERROR=" + e.getLocalizedMessage());
			e.printStackTrace();
		}
		//LOG.info("_initWeraProductVariante (ende)=" + strResult);
		LOG.info("Fussnoten._initWeraProductVariante - colAllFootnotes.size()=" + colAllFootnotes.size() );


		// --- Kontroll-Zeile übernehmen
		m_kontrollCSV.add( strKontrollLine );

		return strResult;
	}

	/**
	 * erzeugen eines  Preiselelments
	 *
	 * @param FileWriter oFileWriterScript
	 * @param int iPreislisteNr
	 * @param string strCodeNr
	 * @param boolean isEmpty
	 * @param string strPriceId
	 * @param string strPriceValue
	 * @param string strUnit
	 * @param string strSymbol
	 * @param string strIsoCode
     * @return Element oPrice
     */
		private Element __newPriceElement( String strPricelist, int iPreislisteNr, String strCodeNr, String strPriceId, String strPriceValue, String strUnit, String strSymbol, String strIsoCode ) {

		// --- preset
		// String strIndesignParam		= "{includeFootnotes:false, includeMasterPages:false, includeHiddenLayers:false, wholeWord:true}";
		String strPlatzhalterPreis	= "";
		String strPlatzhalterUnit	= "";

		// --- Preise und / Platzhalter bei Default immer ausgeben
		boolean isEmpty	= false;

		// --- holen des Filewriters der aktuellen Preisliste
		FileWriter oFileWriterScript	= m_hashMapFileWriterScript.get( strPricelist );
		int anzahlExportierterPreise	= m_hashMapFileWriterAnzahlPreise.get (strPricelist  ).intValue();

		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Aussteuerung von Preislisten Parametern (START)
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//
		// --- hole preislisten gruppe
		Item userPriceGroup 			= (Item)m_hashMapFileWriterUserPriceGroup.get ( strPricelist );

		// --- preise mit oder ohne komma ------------------------
		Boolean preiseMitKomma			= (Boolean)m_wm.getAttribute( (Item)userPriceGroup, "preise_mit_komma");
		if ( preiseMitKomma == null ) {
			preiseMitKomma	= new Boolean(true);
		}
		if ( preiseMitKomma.booleanValue() ) {
			strPriceValue	= strPriceValue.replace(".",",");
		} else {
			strPriceValue	= strPriceValue.replace(",",".");
		}

		// --- EVP-Preisliste  ------------------------
		Boolean isBrutto			= (Boolean)m_wm.getAttribute( (Item)userPriceGroup, "isbrutto");
		if ( isBrutto == null ) {
			// --- by Default keine Bruttoliste
			isBrutto	= new Boolean(false);
		}

		// --- nur die Bruttopreise in die Platzhalter dateo aufnehmen
		isEmpty = !(( isBrutto.booleanValue() && strPriceId.equals("PRBRUTTO") ) || isBrutto.booleanValue() == false);
		// --- EVP-Preisliste  ------------------------

		//
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Aussteuerung von Preislisten Parametern (ENDE)
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// --- Textersetzung Format: Indesign
		//text	{findWhat:"platzhalter"}	{changeTo:"value"}	"{includeFootnotes:true, includeMasterPages:true, includeHiddenLayers:true, wholeWord:false}"

		// --- Pyhton Script Format:
		// "platzhalter":"value"

		// --- sind preisdaten vorhanden, oder dummy-preis dann platzhalter verwenden
		// --- immer platzhalter ausgeben da die Preise erst später kommen
		if ( !isEmpty ) {


			try {

				// --- Preis --------------------------------------------------------------------------------------
				// --- Platzhalter Kennzeichen generieren (Peis)
				strPlatzhalterPreis	= String.valueOf(iPreislisteNr) + "-" + strCodeNr + "-p-" + strPriceId.toLowerCase();

				// --- ist ein Preis vorhanden
				if ( !strPriceValue.equals("") ) {

					// --- anzahl preisdaten je preisliste zählen -
					// Hintergrund: bei 0 Preisen erfoltg keine Preiseetzung um eine
					// Preisliste zu erkennnenbei der noch keine Preise importiert wurden!
					anzahlExportierterPreise++;
					m_hashMapFileWriterAnzahlPreise.put ( strPricelist, new Integer ( anzahlExportierterPreise ) );
				}

				String strFindWhat				= "\"" + strPlatzhalterPreis +  "\"";
				String strChangeTo				= "\"" + strPriceValue +  "\"";
				oFileWriterScript.write( strFindWhat + ":" + strChangeTo + ",\r\n" );

				// --- unit --------------------------------------------------------------------------------------
				if ( !strPriceId.equals("PRBRUTTO") ) {

					// --- Platzhalter Kennzeichen generieren (Unit)
					strPlatzhalterUnit	= String.valueOf(iPreislisteNr) + "-" + strCodeNr + "-u-" + strPriceId.toLowerCase();
					strFindWhat					= "\"" + strPlatzhalterUnit +  "\"";
					strChangeTo					= "\"" + strUnit +  "\"";
					oFileWriterScript.write( strFindWhat + ":" + strChangeTo + ",\r\n" );
				}

			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		// --- Preis XML-Node anlegen
		Element oPrice = new Element("Price");

		// --- preisdaten
		oPrice.setAttribute("Id", strPriceId );
		oPrice.setAttribute("Preis", strPriceValue);
		oPrice.setAttribute("Unit", strUnit);
		// oPrice.setAttribute("Symbol", strSymbol); // <<<<< im Moment nicht verwenden
		// oPrice.setAttribute("IsoCode", strIsoCode); // <<<<< im Moment nicht verwenden

		// --- platzhalter
		oPrice.setAttribute("php", strPlatzhalterPreis );
		oPrice.setAttribute("phu", strPlatzhalterUnit );


		return oPrice;
	}

	/**
	 * Formatiere den Preis
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

		m_bildXML = this._createBildElement(strTagName, weramedia, strPrio, strDirectory, strCodeNrProduct);

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
	 * @param WeraProductSet weraProductSet
	 * @param Element oTexList
	 * @param Integer oLocalRefId
	 * @return Element
	 */
	@Override
	public Element createContentElement(final WeraProductSet weraProductSet, final Element oTexList, Integer oLocalRefId) {
		// TODO Auto-generated method stub
		LOG.info("createContentElement.BLT_SET oLocalRefId=" + oLocalRefId.intValue());
		LOG.info("createContentElement.BLT_SET getCode=" + weraProductSet.getCode() );
		return super.createContentElement(weraProductSet, oTexList, oLocalRefId);

	}

	/**
	 * 
	 * @param WeraProductSet weraProductSet
	 * @param Element oTexList
	 * @param Integer oLocalRefId
	 * @param int nCountProductSet
	 * @return Element
	 */
	@Override
	public Element createContentElementSetinSet(final WeraProductSet weraProductSet, final Element oTexList, Integer oLocalRefId, int nCountProductSet) {
		// TODO Auto-generated method stub
		LOG.info("createContentElementSetinSet.BLT_SET oLocalRefId=" + oLocalRefId.intValue());
		LOG.info("createContentElementSetinSet.BLT_SET getCode=" +  weraProductSet.getCode() );
		// LOG.info("createContentElementSetinSet.BLT_SET nCountProductSet=" +  nCountProductSet );
		//return super.createContentElementSetinSet(weraProductSet, oTexList, oLocalRefId);
		return super.createContentElementSetinSet(weraProductSet, oTexList, oLocalRefId, nCountProductSet);
	}
}

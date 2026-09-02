package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.constants.GeneratedCatalogConstants;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeUnit;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.catalog.jalo.classification.util.Feature;
import de.hybris.platform.catalog.jalo.classification.util.FeatureValue;
import de.hybris.platform.catalog.jalo.classification.util.TypedFeature;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.util.Config;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.jdom.Comment;
import org.jdom.Element;


class OrderComparatorExportCAB implements Comparator
{

	public Collection m_ouputcontrols = new ArrayList();
	public WeraManager m_wm = null;

	public void init(final Collection ouputcontrols)
	{
		m_ouputcontrols.addAll(ouputcontrols);
		m_wm = WeraManager.getInstance();
	}

	private Object _getOutputcontrol(final String strCode) throws JaloInvalidParameterException, JaloSecurityException
	{
		// --- Initialize
		Object outputcontrol = null;

		if (m_ouputcontrols != null)
		{
			outputcontrol = m_wm.checkContaining(m_ouputcontrols, "code", strCode);
		}

		return outputcontrol;
	}

	public int compare(final Object o1, final Object o2) /* descending order */
	{

		// --- Initialize
		String strCode1 = "";
		String strCode2 = "";
		Object outputcontrol1 = null;
		Object outputcontrol2 = null;
		Integer iValue1 = new Integer(0);
		Integer iValue2 = new Integer(0);
		final int iResult = 0;

		try
		{
			// --- Hole Ausgabesteuerung
			strCode1 = (String) ((ClassAttributeAssignment) o1).getClassificationAttribute().getAttribute("code");
			strCode2 = (String) ((ClassAttributeAssignment) o2).getClassificationAttribute().getAttribute("code");
			outputcontrol1 = _getOutputcontrol(strCode1);
			outputcontrol2 = _getOutputcontrol(strCode2);

			// --- Hole Values
			if (outputcontrol1 != null)
			{
				iValue1 = (Integer) ((Item) outputcontrol1).getAttribute("order");
			}
			else
			{
				iValue1 = new Integer(1);
			}
			if (outputcontrol2 != null)
			{
				iValue2 = (Integer) ((Item) outputcontrol2).getAttribute("order");
			}
			else
			{
				iValue2 = new Integer(1);
			}

		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final JaloSecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (iValue1 == null || iValue2 == null)
		{
			return 0;
		}
		else
		{
			return iValue1.compareTo(iValue2);
		}
	}
}


public class BMEcatXml extends XmlSupport
{

	/** Edit the local|project.properties to change logging behavior (properties 'log4j.*'). */
	private static final Logger LOG = Logger.getLogger(BMEcatXml.class.getName());

	// --- Member
	public String m_strResultPath = "";
	public String m_strCatalog = "weracatalog";
	boolean m_bTranferByFTP = true;
	String m_strDatenTransferPath = "";
	String m_strImageTransferPath = "";
	Product m_product = null;
	Product m_article = null;
	String m_strDatum = "";
	String m_strLanguage = "de";
	public String m_strOutputPath = "";
	String m_strOutputFile = "";
	CatalogVersion m_weraCatalogVersion = null;
	CatalogVersion m_weraMasterCatalogVersion = null;
	Collection m_ouputcontrols = null;
	int m_ioAA_img_pictoCounter = 0;
	boolean m_bCriticalError = false;
	ArrayList m_attributesArticle = null;
	ArrayList m_attributesProduct = null;
	HashMap m_hashCategory = null;
	HashMap m_hashCATALOGGROUP = null;
	HashMap m_hashImages = null;
	HashMap m_hashEAN = null;
	TreeSet m_hashsetMedia = null;
	String m_strLieferantenNr = "412058"; // --- Nummer EDE
	String m_strCatalogversion = "";
	ArrayList m_ProduktList = new ArrayList();
	WeraClassificationHelper m_weraclassificationhelper = null;
	MediaCollector m_mediaCollector = null;
	String m_strNamePreisListe = "";
	EnumerationValue m_oPriceList = null;
	String m_strKunde = ""; // Kundendatenxport BMECAT
	boolean m_bHTML_Strip_enabled = false;
	boolean m_bPRIMIUM_enabled = false;
	boolean m_bNordwestClassification = false;
        
	private final String m_IMAGE_ARCHIVE_NAME = "images_cmyk";


	// --- WeraManager
	WeraManager m_wm = WeraManager.getInstance();

	// --- Logfile
	FileWriter m_oFileWriterLog = null;
	ArrayList m_aArrayLog = new ArrayList();
	ArrayList m_aArrayLog_30 = new ArrayList();

	// --- Zähler
	public int m_iOffsetIDCategory = 0;
	public int m_iOffsetIDEbene2 = 0;
	public int m_iOffsetIDEbene3 = 0;


	// --- Knotenelemente
	public Element m_oCatalog = null;
	public Element m_oTreegroupLink = null;
	public Element m_oReferenceElements = null;
	public Element m_oTreegroupList = null;
	public Element m_oTreegroup = null;
	public Element m_oProductList = null;
	public Element m_oProductLink = null;
	public Element m_oProductJoinList = null;
	public Element m_oProductJoin = null;
	public Element m_oTextList = null;
	public Element m_oTextItem = null;
	public Element m_oAttributeList = null;
	public Element m_oAttributeListArtikel = null;
	public Element m_articelXML = null;
	public Element m_motivelistXML = null;
	public Element m_bildXML = null;
	public Element m_oArticelList = null;
	public Element m_oXmlProduct = null;
	public Element m_oXmlVariant = null;
	public Element m_oVariants = null;
	public Element m_oNewHeaderElement = null;
	public Element m_oNewCatalogElement = null;

	// --- Kategories
	String m_strCategoryID = "";
	String m_strCategoryName = "";
	String m_strCategoryTemplate = "";
	String m_strCategoryOnline = "";
	String m_strCategoryParent = "";
	String m_strCategoryHotdeals = "";
	String m_strCategoryDescripction = "";
	String m_strCategoryDisplayName = "";


	//static final String XMLLANG = "xml:lang";
	//static final String C_BMECAT_CATEGORIES          = "categories";
	// --- BMECAT - Konstanten
	static final String C_BMECAT_PRODUCTS = "products";
	static final String C_BMECAT_SELLER = "wera";
	static final String C_BMECAT_FEATRURE_WERA_VERSION = "udf_wera-1.0";
	static final String C_BMECAT_SKU = "weraXXXXXXXXXXXX";
	static final String C_BMECAT_DISPLAYSELLER = "wera";
	static final String C_BMECAT_DISPLAYMANUFACTURE = "Wera Werkzeuge GmbH";
	protected String C_BMECAT_VERSION = "1.2";
	// protected    String C_BMECAT_VERSION               = "2005";
	static final String C_BMECAT_CATALOG_NAME = "Katalog Wera Werkzeuge, Wuppertal";
	static final String C_BMECAT_EMPTY_TAG = "-";
	static final String C_BMECAT_DTD = "bmecat_new_catalog_1_2.dtd";
	static final String C_BMECAT_ROOT_GROUP_ID = "1";

	// --- XML-Extensions
	//static final String C_XMLEXT_XMLLANG  = "extlang"; // xml:lang
	//static final String C_XMLEXT_DTDT     = "extdtdt"; // dt:dt

	public BMEcatXml()
	{
		super();
		// TODO Auto-generated constructor stub

		// --- Unser neuer Classifiactioan Helper
		m_weraclassificationhelper = new WeraClassificationHelper();

		// --- Media Collector anlegen
		m_mediaCollector = new MediaCollector();
	}

	// --- Umkopieren der Mediendaten
	public void archiveMedias()
	{

		// --- Mediadaten archivieren
		m_mediaCollector.archiveMedias();
	}

	// --- Zurücksetzen der Sprache, aufräumen
	@Override
	public void cleanUp()
	{

		// --- Inistialiue
		String strLine = "";

		// --- Default		
		super.cleanUp();

		// --- LOG-File
		try
		{

			// --- Logdatei (BMECAT) schreiben -------------------------------
			if (m_aArrayLog.size() > 0)
			{
				strLine = "";
				m_oFileWriterLog = new FileWriter(m_strOutputPath + m_strDatum + "_bmecat.log");
				if (m_oFileWriterLog != null)
				{

					// ---- Schleife über alle Zeilen
					LOG.info("Logdatei (BMECAT) wird geschrieben..., " + m_aArrayLog.size() + " Zeilen.");
					for (final Iterator it1 = m_aArrayLog.iterator(); it1.hasNext();)
					{
						strLine = (String) it1.next();
						m_oFileWriterLog.write(strLine + "\r\n");
					}

					// --- Schliesen
					m_oFileWriterLog.close();
				}
			} // --- if ( m_aArrayLog.size() > 0 ) {
			  // --- Logdatei (BMECAT) schreiben -------------------------------


			// --- Logdatei (Klassifizierung) schreiben -------------------------------
			if (m_aArrayLog_30.size() > 0)
			{
				strLine = "";
				m_oFileWriterLog = new FileWriter(m_strOutputPath + m_strDatum + "_bmecat_30.log");
				if (m_oFileWriterLog != null)
				{

					// ---- Schleife über alle Zeilen
					LOG.info("Logdatei (Klassifizierung) wird geschrieben..., " + m_aArrayLog_30.size() + " Zeilen.");
					for (final Iterator it1 = m_aArrayLog_30.iterator(); it1.hasNext();)
					{
						strLine = (String) it1.next();
						m_oFileWriterLog.write(strLine + "\r\n");
					}

					// --- Schliesen
					m_oFileWriterLog.close();
				}
			} // --- if ( m_aArrayLog_30.size() > 0 ) {
			  // --- Logdatei (Klassifizierung) schreiben -------------------------------


			// --- Liste der Mediendaten schreiben -------------------------------
			if (m_hashsetMedia.size() > 0)
			{
				strLine = "";
				m_oFileWriterLog = new FileWriter(m_strOutputPath + m_strDatenTransferPath + "/liste-der-medien-daten.txt");
				if (m_oFileWriterLog != null)
				{

					// ---- Schleife über alle Zeilen
					LOG.info("Liste der Mediendaten wird geschrieben..., " + m_hashsetMedia.size() + " Zeilen.");
					for (final Iterator it1 = m_hashsetMedia.iterator(); it1.hasNext();)
					{
						strLine = (String) it1.next();
						m_oFileWriterLog.write(strLine + "\r\n");
					}

					// --- Schliesen
					m_oFileWriterLog.close();
				}
			} // --- if ( m_hashsetMedia.size() > 0 ) {
			  // --- Liste der Mediendaten schreiben -------------------------------

			// --- Mediadaten archivieren
			//m_mediaCollector.archiveMedias();
			m_mediaCollector.archiveMedias300DPI();

			// --- Zippen der Media-Datei -----------------------------------------
			try
			{
				final String strImageListFile = m_strOutputPath + m_strDatenTransferPath + "/make_transferfile.sh";
				LOG.info("Shellscript wird angelegt (zippen der Images) Dateiname=" + strImageListFile + ".");

				final FileWriter oFileWriterOK = new FileWriter(strImageListFile);

				// --- Erzeugen der Archive
				oFileWriterOK.write("#!/bin/sh\n");
				oFileWriterOK.write("#\n");
				oFileWriterOK.write("cd " + m_strOutputPath + m_strDatenTransferPath + "/" + this.m_IMAGE_ARCHIVE_NAME + "\n");
				//oFileWriterOK.write("zip -9 -rv images images\n" );
				oFileWriterOK.write("zip -9 " + this.m_IMAGE_ARCHIVE_NAME + " *\n");
				oFileWriterOK.write("mv " + this.m_IMAGE_ARCHIVE_NAME + ".zip ..\n");
				oFileWriterOK.write("cd ..\n");
				oFileWriterOK.write("rm -rf " + this.m_IMAGE_ARCHIVE_NAME + "\n");

				// --- Datei schliessen
				oFileWriterOK.close();

				// --- Daten aufbereiten zum Transfer (Ausführen der SHell-Datei)
				startCmdFile("chmod a+x " + strImageListFile);
				startCmdFile(strImageListFile);

			}
			catch (final IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// --- Zippen der Media-Datei -----------------------------------------

		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		// --- Aufräumen und Nodes freigeben
		m_hashsetMedia.clear();
		m_aArrayLog.clear();
		m_ProduktList.clear();
		m_oCatalog = null;
		m_oTreegroupLink = null;
		m_oReferenceElements = null;
		m_oTreegroupList = null;
		m_oTreegroup = null;
		m_oProductList = null;
		m_oProductLink = null;
		m_oProductJoinList = null;
		m_oProductJoin = null;
		m_oTextList = null;
		m_oTextItem = null;
		m_oAttributeList = null;
		m_oAttributeListArtikel = null;
		m_articelXML = null;
		m_motivelistXML = null;
		m_bildXML = null;
		m_oArticelList = null;
		m_oXmlProduct = null;
		m_oVariants = null;
		m_oXmlVariant = null;
		m_attributesArticle = null;
		m_attributesProduct = null;
		m_hashCategory = null;
		m_hashCATALOGGROUP = null;
		m_strDatenTransferPath = "";
		m_strImageTransferPath = "";
		m_bHTML_Strip_enabled = false;
		m_bPRIMIUM_enabled = false;

		// --- Zähler zurücksetzen
		m_iOffsetIDCategory = 0;
		m_iOffsetIDEbene2 = 0;
		m_iOffsetIDEbene3 = 0;
	}

        public void setM_strLanguage(String m_strLanguage) {
            this.m_strLanguage = m_strLanguage;
        }

        /* Norwest special Format Klassifikation */
        public void setM_bNordwestClassification(boolean bNordwestClassification) {
            this.m_bNordwestClassification = bNordwestClassification;
        }

	// --- HTML-Tags entfernen (Default=false)
	public void setHTML_Strip_enabled(final boolean bHTML_Strip_enabled)
	{

		// --- Initialize
		m_bHTML_Strip_enabled = bHTML_Strip_enabled;
	}

	// --- Artikelnummern nach PRIMIUM
	public void setPRIMIUM_enabled(final boolean bPRIMIUM_enabled)
	{

		// --- Initialize
		m_bPRIMIUM_enabled = bPRIMIUM_enabled;
	}

	public void InitBMEcatDefaultCatalog(final String strCatalog)
	{

		// --- Initialize
		m_strCatalog = strCatalog;
	}

	public void InitBMEcatDefaultLanguage(final String strLanguage)
	{

		// --- Initialize
		m_strLanguage = strLanguage;
	}

	public boolean bInitBMEcat(final String strCatalogversion)
	{
		// --- Debug
		LOG.info("bInitBMEcat ...");

		// --- Initialize
		m_attributesArticle = new ArrayList();
		m_attributesProduct = new ArrayList();
		m_hashsetMedia = new TreeSet();
		m_hashCategory = new HashMap();
		m_hashCATALOGGROUP = new HashMap();
		m_hashImages = new HashMap();
		m_hashEAN = new HashMap();
		m_strCatalogversion = strCatalogversion;

		// --- Setze Sprache, und Defaultsprache=de
		initLanguage(m_strLanguage);

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		m_weraCatalogVersion = m_wm.getCatalogVersion(m_strCatalog, strCatalogversion);
		m_weraMasterCatalogVersion = m_wm.getCatalogVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));

		// --- Preisliste
		final Collection catalogPricelists = (Collection) m_wm.getAttribute(m_weraCatalogVersion, "upg4csv");
		if (catalogPricelists != null && catalogPricelists.size() > 0)
		{
			// --- preislisten Informationen initialisieren
			m_oPriceList = (EnumerationValue) catalogPricelists.iterator().next();
			m_strNamePreisListe = m_oPriceList.getCode();

		}
		else
		{
			// --- keine Preisliste hinterlegt
			m_oPriceList = null;
			m_strNamePreisListe = "";
		}

		// --- FTP-Tranfer 
		m_bTranferByFTP = false;

		// --- MediaXML - Real Rootcontent "BMECAT"
		m_rootElement = new Element("BMECAT");
		m_rootElement.setAttribute("version", _getBMECAT_VERSION());
		// initDTD("BMECAT",C_BMECAT_DTD );

		// --- Katalog - Element 
		m_oNewHeaderElement = new Element("HEADER");
		m_oNewCatalogElement = new Element("T_NEW_CATALOG");

		// --- Elemente in Root-Knoten einfügen
		m_rootElement.addContent(m_oNewHeaderElement);
		m_rootElement.addContent(m_oNewCatalogElement);

		// --- Ausgabedatei / Verzeichnis erzeugen
		genOutputFileName();

		return true;
	}

	// --- Ausgabedatei / Verzeichnis erzeugen
	public void genOutputFileName()
	{
		// TODO Auto-generated method stub
		// --- Datum ermitteln
		m_strDatum = m_wm.InitOutputDatum();

		// --- Ausgabedatum
		final Comment dateComment = new Comment(" Datenexport vom: " + m_strDatum);
		m_rootElement.addContent(dateComment);

		final String strDatum = m_strDatum;
		m_strResultPath = "bmecat_proficlass-3.0_" + m_strKunde + "_" + strDatum;
		m_strOutputPath = Config.getParameter("wera.exportpath") + "katalog/" + m_strCatalogversion + "/" + m_strResultPath + "/";
		m_strOutputFile = C_BMECAT_SELLER + "_" + m_strLanguage + "_" + strDatum;
		m_strOutputFile += "_" + C_BMECAT_PRODUCTS + ".xml";

		// --- Pfad anlegen falls noch nicht vorhanden
		m_wm.createDirectory(Config.getParameter("wera.exportpath") + "katalog/" + m_strCatalogversion);
		m_wm.createDirectory(m_strOutputPath);

		// --- Auagabepfade für XML-Daten anlegen
		m_strDatenTransferPath = "wera_" + m_strLanguage + "_" + strDatum + "_daten";
		m_wm.createDirectory(m_strOutputPath + m_strDatenTransferPath);

		// --- Mediapfad
		m_strImageTransferPath = m_strOutputPath + m_strDatenTransferPath + "/" + this.m_IMAGE_ARCHIVE_NAME;
		m_wm.createDirectory(m_strImageTransferPath);

		// --- Debug
		LOG.info("START BMECAT - Export");
		LOG.info("Ausgabe Katalog / Version=" + m_strCatalog + "/" + m_strCatalogversion);
		LOG.info("Ausgabesprache=" + m_strLanguage);
	}

	// -----------------------------------------------------------------------------------------------------	
	// --- START
	// -----------------------------------------------------------------------------------------------------	
	public String _strJspEntryExportBMEcatXML(final String strCatalogversion, final String strLanguage)
	{
		// --- Dummy
		return strLanguage;

	}

	// --- Einsprungspunkt JSP
	public String strJspEntryExportBMEcatXML(final String strCatalogversion)
	{
		// --- Initialiue
		final String strResult = "";
		final String strLanguage = "de";

		// --- Hauptprogramm   
		BMEcatGenerate(strCatalogversion);

		return strResult;
	}

	// --- Hauptprogramm   
	public void BMEcatGenerate(final String strCatalogversion)
	{
		// --- Initialisierung
		if (bInitBMEcat(strCatalogversion))
		{

			// --- HEADER>...</HEADER
			BMEcatGenHEADER();

			// --- FEATURE_SYSTEM>...</FEATURE_SYSTEM
			BMEcatGenFEATURE_SYSTEM();

			// --- CLASSIFICATION_SYSTEM>... </CLASSIFICATION_SYSTEM>
			BMEcatGenCLASSIFICATION_SYSTEM();

			// --- CATALOG_GROUP_SYSTEM>... </CATALOG_GROUP_SYSTEM>
			BMEcatGenCATALOG_GROUP_SYSTEM();

			// --- ARTICLE mode="new">...</ARTICLE>
			BMEcatGenARTICLE();

			// --- ARTICLE_TO_CATALOGGROUP_MAP>...</ARTICLE_TO_CATALOGGROUP_MAP>
			BMEcatGenARTICLE_TO_CATALOGGROUP_MAP();

			// --- Schreiben der XML-Datei
			writeDocument(m_strOutputPath + m_strDatenTransferPath + "/", m_strOutputFile);

			// --- Zurücksetzen der Sprache, Aufräumen
			cleanUp();
		}
	}

	// --- HEADER>...</HEADER
	public void BMEcatGenHEADER()
	{
		// --- Initialize
		Element oElementTmp = null;

		// --- Infos
		m_oNewHeaderElement.addContent(new Element("GENERATOR_INFO").addContent("Hybris Export BMECAT"));

		// --- Katalog
		final Element oElementCatalog = new Element("CATALOG");
		m_oNewHeaderElement.addContent(oElementCatalog);
		oElementCatalog.addContent(new Element("LANGUAGE").addContent(m_strLanguage));
		oElementCatalog.addContent(new Element("CATALOG_ID").addContent(m_strCatalog));
		oElementCatalog.addContent(new Element("CATALOG_VERSION").addContent(m_strCatalogversion));
		oElementCatalog.addContent(new Element("CATALOG_NAME").addContent(C_BMECAT_CATALOG_NAME));
		oElementTmp = new Element("DATETIME").setAttribute("type", "generation_date");
		oElementCatalog.addContent(oElementTmp);
		oElementTmp.addContent(new Element("DATE").addContent(m_strDatum.substring(0, 4) + "-" + m_strDatum.substring(4, 6) + "-"
				+ m_strDatum.substring(6, 8)));

		// --- Supplier
		final Element oElementSupplier = new Element("SUPPLIER");
		m_oNewHeaderElement.addContent(oElementSupplier);
		oElementTmp = new Element("SUPPLIER_ID").setAttribute("type", "buyer_specific");
		oElementTmp = new Element("SUPPLIER_NAME").addContent(C_BMECAT_DISPLAYMANUFACTURE);
		//oElementSupplier.addContent(oElementTmp.addContent(m_strLieferantenNr));
		oElementTmp = new Element("ADDRESS");
		oElementTmp.setAttribute("type", "supplier");
		oElementTmp.addContent(new Element("NAME").addContent("Hermann Werner GmbH & Co. KG"));
		oElementTmp.addContent(new Element("STREET").addContent("Korzerter Strassse 21-25"));
		oElementTmp.addContent(new Element("ZIP").addContent("42349"));
		oElementTmp.addContent(new Element("CITY").addContent("Wuppertal"));
		oElementTmp.addContent(new Element("COUNTRY").addContent("Deutschland"));
		oElementTmp.addContent(new Element("COUNTRY_CODED").addContent("DE"));
		oElementTmp.addContent(new Element("PHONE").addContent("+49 202 4045 311"));
		oElementTmp.addContent(new Element("FAX").addContent("+49 202 4036 34"));
		oElementTmp.addContent(new Element("URL").addContent("http://www.wera.de"));
		oElementSupplier.addContent(oElementTmp);
	}

	// --- FEATURE_SYSTEM>...</FEATURE_SYSTEM
	public void BMEcatGenFEATURE_SYSTEM()
	{
		// --- Initialize
		final Element oElement = new Element("FEATURE_SYSTEM");

		// --- Element in Root-Knoten einfügen
		//m_oNewCatalogElement.addContent(oElement);
	}

	// --- ARTICLE_PRICE_DETAILS>...</ARTICLE_PRICE_DETAILS
	public Element BMEcatGenARTICLE_PRICE_DETAILS(final Product oVerkaufsProduct)
	{
		// --- Initialize
		final Element oARTICLE_PRICE_DETAILS = new Element("ARTICLE_PRICE_DETAILS");
		Element oDATETIME = new Element("DATETIME");
		oARTICLE_PRICE_DETAILS.addContent(oDATETIME);
		oDATETIME.addContent(new Element("DATE").addContent(m_strDatum.substring(0, 4) + "-01-01"));
		oDATETIME.setAttribute("type", "valid_start_date");
		oDATETIME = new Element("DATETIME");
		oARTICLE_PRICE_DETAILS.addContent(oDATETIME);
		oDATETIME.addContent(new Element("DATE").addContent(m_strDatum.substring(0, 4) + "-12-31"));
		oDATETIME.setAttribute("type", "valid_end_date");
		oARTICLE_PRICE_DETAILS.addContent(new Element("DAILY_PRICE").addContent("true"));

		// --- ARTIKEL_PRICE
		final Element oARTICLE_PRICE = new Element("ARTICLE_PRICE");
		oARTICLE_PRICE_DETAILS.addContent(oARTICLE_PRICE);
		oARTICLE_PRICE.setAttribute("price_type", "net_list");
		oARTICLE_PRICE.addContent(new Element("PRICE_AMOUNT").addContent(C_BMECAT_EMPTY_TAG));
		oARTICLE_PRICE.addContent(new Element("PRICE_CURRENCY").addContent("EUR"));
		oARTICLE_PRICE.addContent(new Element("TAX").addContent("0.19"));
		//oARTICLE_PRICE.addContent(new Element("PRICE_FACTOR").addContent("1"));
		oARTICLE_PRICE.addContent(new Element("LOWER_BOUND").addContent(C_BMECAT_EMPTY_TAG));

		return oARTICLE_PRICE_DETAILS;
	}

	// --- Logistische Daten
	// PRODUCT_LOGISTIC_DETAILS>...</PRODUCT_LOGISTIC_DETAILS>
	public Element BMEcatGenPRODUCT_LOGISTIC_DETAILS(final Product oVerkaufsProduct)
	{

		// --- Debug
		LOG.info("BMEcatGenPRODUCT_LOGISTIC_DETAILS ...");

		// --- Initialize
		final Element oPRODUCT_LOGISTIC_DETAILS = new Element("PRODUCT_LOGISTIC_DETAILS");

		// --- Iniatialize Data
		final String strZolltarifNr = (String) m_wm.getAttribute(oVerkaufsProduct, "ZolltarifNr");
		final String strUrsprungsland = (String) m_wm.getAttribute(oVerkaufsProduct, "Ursprungsland");
		String strGewVE = (String) m_wm.getAttribute(oVerkaufsProduct, "GewVE");
		if (strGewVE == null || strGewVE.length() == 0)
		{
			strGewVE = C_BMECAT_EMPTY_TAG;
		}
		else
		{
			try
			{
				strGewVE = strGewVE.replaceAll(",", ".");
				strGewVE = new Double(Double.parseDouble(strGewVE) / 1000.0).toString();
			}
			catch (final Exception e)
			{
				strGewVE = C_BMECAT_EMPTY_TAG;
			}
		}

		String strPackm_breite = (String) m_wm.getAttribute(oVerkaufsProduct, "packm_breite");
		if (strPackm_breite == null || strPackm_breite.length() == 0)
		{
			strPackm_breite = C_BMECAT_EMPTY_TAG;
		}
		else
		{
			try
			{
				strPackm_breite = strPackm_breite.replaceAll(",", ".");
				strPackm_breite = new Double(Double.parseDouble(strPackm_breite) / 1000.0).toString();
			}
			catch (final Exception e)
			{
				strPackm_breite = C_BMECAT_EMPTY_TAG;
			}
		}
		String strPackm_laenge = (String) m_wm.getAttribute(oVerkaufsProduct, "packm_laenge");
		if (strPackm_laenge == null || strPackm_laenge.length() == 0)
		{
			strPackm_laenge = C_BMECAT_EMPTY_TAG;
		}
		else
		{
			try
			{
				strPackm_laenge = strPackm_laenge.replaceAll(",", ".");
				strPackm_laenge = new Double(Double.parseDouble(strPackm_laenge) / 1000.0).toString();
			}
			catch (final Exception e)
			{
				strPackm_laenge = C_BMECAT_EMPTY_TAG;
			}
		}
		String strPackm_hoehe = (String) m_wm.getAttribute(oVerkaufsProduct, "packm_hoehe");
		if (strPackm_hoehe == null || strPackm_hoehe.length() == 0)
		{
			strPackm_hoehe = C_BMECAT_EMPTY_TAG;
		}
		else
		{
			try
			{
				strPackm_hoehe = strPackm_hoehe.replaceAll(",", ".");
				strPackm_hoehe = new Double(Double.parseDouble(strPackm_hoehe) / 1000.0).toString();
			}
			catch (final Exception e)
			{
				strPackm_hoehe = C_BMECAT_EMPTY_TAG;
			}
		}


		// --- Zolltarifnummer
		final Element oCUSTOMS_TARIFF_NUMBER = new Element("CUSTOMS_TARIFF_NUMBER");
		oPRODUCT_LOGISTIC_DETAILS.addContent(oCUSTOMS_TARIFF_NUMBER);
		oCUSTOMS_TARIFF_NUMBER.addContent(new Element("CUSTOMS_NUMBER").addContent(strZolltarifNr));

		// --- Herkunftsland
		oPRODUCT_LOGISTIC_DETAILS.addContent(new Element("COUNTRY_OF_ORIGIN").addContent(strUrsprungsland));


		final Element oElementPRODUCT_DIMENSIONS = new Element("PRODUCT_DIMENSIONS");
		oPRODUCT_LOGISTIC_DETAILS.addContent(oElementPRODUCT_DIMENSIONS);
		oElementPRODUCT_DIMENSIONS.addContent(new Element("WEIGHT").addContent(strGewVE));
		oElementPRODUCT_DIMENSIONS.addContent(new Element("LENGTH").addContent(strPackm_laenge));
		oElementPRODUCT_DIMENSIONS.addContent(new Element("WIDTH").addContent(strPackm_breite));
		oElementPRODUCT_DIMENSIONS.addContent(new Element("DEPTH").addContent(strPackm_hoehe));

		return oPRODUCT_LOGISTIC_DETAILS;
	}

	// --- CLASSIFICATION_SYSTEM>... </CLASSIFICATION_SYSTEM>
	public void BMEcatGenCLASSIFICATION_SYSTEM()
	{
		// --- Initialize
		final Element oElement = new Element("CLASSIFICATION_SYSTEM");

		// --- Element in Root-Knoten einfügen
		//m_oNewCatalogElement.addContent(oElement);
	}

	// --- CATALOG_GROUP_SYSTEM>... </CATALOG_GROUP_SYSTEM>
	public void BMEcatGenCATALOG_GROUP_SYSTEM()
	{
		// --- Initialize
		m_wm.m_bCheckForActivation = true;
		Category oCategory = null;
		ArrayList categories = null;
		Element oXmlCategory = null;
		final Element oElementTmp = null;

		// --- Nodes anlegen
		final Element oElement = new Element("CATALOG_GROUP_SYSTEM");
		oElement.addContent(new Element("GROUP_SYSTEM_ID").addContent(m_strCatalogversion));
		oElement.addContent(new Element("GROUP_SYSTEM_NAME").addContent(m_strCatalogversion.toUpperCase() + "_GROUP_SYSTEM"));

		// --- Hole alle Kateogieren
		try
		{
			// --- Hole alle Kategorien des Katalogs
			categories = (ArrayList) m_wm.getCategories(m_strCatalog, m_strCatalogversion, "");

			// --- Schleife über alle Kategorien
			for (final Iterator it1 = categories.iterator(); it1.hasNext();)
			{
				// -- Get Category
				oCategory = (Category) it1.next();

				// --- Keine klassifizieren Klassen
				if (oCategory instanceof ClassificationClass)
				{
					LOG.info("++ SKIP klassifizierte Kategorie = " + oCategory.getCode());
					continue;
				}

				// --- Auslassen
				if (oCategory.getCode().equals("VERKAUFSHILFE") || oCategory.getCode().equals("PREISLISTENARTIKEL"))
				{
					LOG.info("++ SKIP Kategorie = " + oCategory.getCode());
					continue;
				}

				// --- Categorie übernehmen und Produkte sammeln
				oXmlCategory = GenCATALOG_STRUCTURE(oCategory);

				// --- Kategory übernehmen
				if (oXmlCategory != null)
				{
					oElement.addContent(oXmlCategory);
				}
				else
				{
					LOG.info("BMEcatGenCATALOG_GROUP_SYSTEM => XML-Fehler in Kategory (" + oCategory.getName() + ")");
				}

			} // --- for ( Iterator it1 = categories.iterator(); it1.hasNext();)

		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final JaloSecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Element in Root-Knoten einfügen
		m_oNewCatalogElement.addContent(oElement);
	}

	public Element GenCATALOG_STRUCTURE(final Category oCategory)
	{

		// TODO Auto-generated method stub
		LOG.info("BMEcatXml.GenCATALOG_STRUCTURE (" + oCategory.getCode() + ")");

		// --- Initialize
		Collection oColCategoryParent = null;
		Category oCategoryParent = null;
		String strParentID = "";

		// --- Kategorie erzeugen					
		final Element oXmlCategory = new Element("CATALOG_STRUCTURE");

		// --- Hole Parent-Category
		if (!oCategory.getCode().equals("root"))
		{
			oColCategoryParent = (Collection) m_wm.getAttribute(oCategory, "supercategories");
			if (oColCategoryParent != null && oColCategoryParent.size() > 0)
			{
				oCategoryParent = (Category) oColCategoryParent.iterator().next();
				//oXmlCategory = oCreateProductCategoryElement ( oCategory, oCategoryParent );

				// --- Kategorie füllen, prüfen auf Leaf-Node (enthält Produkte)
				final Collection oColProducts = (Collection) m_wm.getAttribute(oCategory, "products");
				if (oColProducts.size() > 0)
				{
					oXmlCategory.setAttribute("type", "leaf");

					// --- Produkte merken
					m_ProduktList.addAll(oColProducts);
				}
				else
				{
					oXmlCategory.setAttribute("type", "node");
				}

				// --- ParentID prüfen
				strParentID = m_wm.getAttribute(oCategoryParent, "code").toString();
				if (strParentID.equals("root"))
				{
					strParentID = C_BMECAT_ROOT_GROUP_ID;
				}

				// --- Kategorie füllen					
				oXmlCategory.addContent(new Element("GROUP_ID").addContent(m_wm.getAttribute(oCategory, "code").toString()));
				oXmlCategory.addContent(new Element("GROUP_NAME").addContent(m_wm.getAttribute(oCategory, "name").toString()));
				oXmlCategory.addContent(new Element("PARENT_ID").addContent(strParentID));
				Object oOrder = m_wm.getAttribute(oCategory, "order");
				if ( oOrder != null ) {
					oXmlCategory.addContent(new Element("GROUP_ORDER").addContent( oOrder.toString() ));
				} else {
					oXmlCategory.addContent(new Element("GROUP_ORDER").addContent( "0" ));
				}
			}
		}
		else
		{

			// --- Kategorie füllen					
			oXmlCategory.setAttribute("type", "root");
			// oXmlCategory.addContent(new Element("GROUP_ID").addContent(m_wm.getAttribute(oCategory,"code").toString()));
			oXmlCategory.addContent(new Element("GROUP_ID").addContent(C_BMECAT_ROOT_GROUP_ID));
			oXmlCategory.addContent(new Element("GROUP_NAME").addContent(m_wm.getAttribute(oCategory, "name").toString()));
			oXmlCategory.addContent(new Element("PARENT_ID").addContent("0"));
			oXmlCategory.addContent(new Element("GROUP_ORDER").addContent(m_wm.getAttribute(oCategory, "order").toString()));
		}


		return oXmlCategory;
	}

	// --- ARTICLE mode="new">...</ARTICLE>
	public void BMEcatGenARTICLE()
	{
		// --- Initialize
		final ArrayList oArticelList = new ArrayList();

		// --- Schleife über alle Produkte
		final int iCnt = 0;
		Product oProduct = null;
		LOG.info("Anzahl Produkte =" + m_ProduktList.size());
		for (final Iterator it1 = m_ProduktList.iterator(); it1.hasNext();)
		{

			// -- Get WERAPRODUKT
			oProduct = (Product) it1.next();

//if ( !oProduct.getCode().equals("8000 A SB SiS") )
//	continue;
	
			// --- Erzeuge ein Produktelement
			if (oProduct instanceof WeraProduct || oProduct instanceof WeraProductSetinSet)
			{

				// --- Hole alle Kategorien
				final Collection categories = (Collection) m_wm.getAttribute(oProduct, "supercategories");
				//if ( m_wm.checkContaining( categories, "code", "VERKAUFSHILFE" ) != null )
				//continue;
				if (m_wm.checkContaining(categories, "code", "PREISLISTENARTIKEL") != null)
				{
					continue;
				}

				// --- Prüfe nochmal ob das Produkt auch aktiv ist!!!!
				Boolean bAktiv = (Boolean) m_wm.getAttribute(oProduct, "aktiv");
				if (bAktiv == null)
				{
					bAktiv = new Boolean(false);
				}
				if (bAktiv.booleanValue())
				{

					// --- Liste löschen
					oArticelList.clear();

					// --- Alles Aritkel oder Sätze des aktuellen Produkts als Liste generieren
					oArticelList.addAll(CreateArticleList((WeraProduct) oProduct, categories));

					// --- Schleife über alle Elemete
					for (final Iterator it2 = oArticelList.iterator(); it2.hasNext();)
					{
						// --- Element in Root-Knoten einfügen
						m_oNewCatalogElement.addContent((Element) it2.next());
					}

				} // --- if ( bAktiv.booleanValue() ) {

			} // --- if ( oProduct instanceof WeraProduct ) {

		} // --- for ( Iterator it1 = m_ProduktList.iterator(); it1.hasNext();) {

	}

	public Collection CreateArticleList(final WeraProduct oProduct, final Collection categories)
	{

		// --- Initialize
		final ArrayList oArticelList = new ArrayList();
		final String strProductCode = "";


		if ((oProduct instanceof WeraProductSetinSet))
		{
			// --- Debug
			LOG.info("++ SetinSet = " + oProduct.getCode());

			// --- Artikelliste eines Produktes
			Collection articles = null;
			try
			{
				articles = (Collection) m_wm.getAttribute(oProduct, "weraproductsetvariants_qual");

				// --- Schleife über alle Artikel
				WeraProductSetVariants oWeraProductSetVariants = null;
				WeraProductSet oWeraProductset = null;
				for (final Iterator it2 = articles.iterator(); it2.hasNext();)
				{
					// --- Hole den aktuellen Artikel
					oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
					oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");

					// --- Fülle einen Artikel
					if (oWeraProductset != null)
					{
						final Element oElementTmp = CreateArticle(oWeraProductset, categories, oProduct);
						if (oElementTmp != null)
						{
							oArticelList.add(oElementTmp);
						}
					}
				}

			}
			catch (final JaloInvalidParameterException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		else
		{
			if (oProduct instanceof WeraProductSet)
			{

				// --- Debug
				LOG.info("++ Set = " + oProduct.getCode());

				// --- Fülle einen Satz
				if (oProduct != null)
				{
					final Element oElementTmp = CreateArticle(oProduct, categories, null);
					if (oElementTmp != null)
					{
						oArticelList.add(oElementTmp);
					}
				}
			}
			else
			{
				// --- Debug
				LOG.info("++ Product = " + oProduct.getCode());

				// --- Artikelliste eines Produktes
				Collection articles = null;
				try
				{
					articles = oProduct.getVarianten();
				}
				catch (final JaloInvalidParameterException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// --- Schleife über alle Artikel
				WeraVariante oArticle = null;
				for (final Iterator it2 = articles.iterator(); it2.hasNext();)
				{

					// --- Hole den aktuellen Artikel
					oArticle = (WeraVariante) it2.next();

					// --- Fülle einen Artikel
					if (oArticle != null)
					{
						final Element oElementTmp = CreateArticle(oArticle, categories, null);
						if (oElementTmp != null)
						{
							oArticelList.add(oElementTmp);
						}
					}
				}
			}
		}

		return oArticelList;

	}

	public Element CreateArticle(final Product oProduct, final Collection categories, final Product oBaseOfSetinSet)
	{

		// --- Initialize
		final HashMap hashCategoriesWeraZusatz = new HashMap();
		Element oElementARTICLE = null;

		// --- Produktcode
		String strProductCode = (String) m_wm.getAttribute(oProduct, "lagerNr");
		if (oProduct instanceof WeraVariante)
		{
			strProductCode += oProduct.getCode();
		}
		else
		{
			strProductCode += m_wm.getAttribute(oProduct, "artnr");
		}
		strProductCode += m_wm.getAttribute(oProduct, "variantenNr");
		
		// --- Code-Nummer / Artikelnummer ----------------------------------
		// --- Verwender WERA-Codenummer, als Fallback (default)
		String strSUPPLIER_ALT_PID = oProduct.getPK().toString();
		String strSUPPLIER_AID = strProductCode;
		if (m_bPRIMIUM_enabled)
		{
			// --- Hole EAN
			final String strEAN_code = (String) m_wm.getAttribute(oProduct, "ean");
			if (strEAN_code != null && strEAN_code.length() == 13)
			{

				// --- PRIMIUM-ArtNr generieren
				// Beispiel:
				// EAN: 4013288164407
				// PRIMIUM Art.-Nr. 0516440
				final String strPRIMIUM_ArtNr = "05" + strEAN_code.substring(7, 12);

				// --- generiere PRIMIUM-Artikelnummer
				strSUPPLIER_AID = strPRIMIUM_ArtNr;

			}
		} // --- if ( m_bPRIMIUM_enabled ) {
		  // --- Code-Nummer / Artikelnummer ----------------------------------

		// --- Keine doppelten Artikel übernehmen
		if (m_hashCATALOGGROUP.containsKey(strSUPPLIER_AID) == false)
		{

			// --- Debug
			LOG.info("++ add = " + oProduct.getClass().getName());

			// --- Initialize
			oElementARTICLE = new Element("ARTICLE");
			oElementARTICLE.setAttribute("mode", "new");

			// --- Initialize
			hashCategoriesWeraZusatz.clear();
			m_attributesArticle.clear();
			m_attributesProduct.clear();
			m_hashCategory.clear();

			// --- Initialize
			boolean bIsMasterProduct = false;
			boolean bIsOrderProduct = false;
			if (oProduct instanceof WeraProduct && !(oProduct instanceof WeraProductSet))
			{
				bIsMasterProduct = true;
			}
			if (oProduct instanceof WeraProductSetinSet || oProduct instanceof WeraProductSet || oProduct instanceof WeraVariante)
			{
				bIsOrderProduct = true;
			}

			// --- Hole das Basisprodukt
			Product oBaseProduct = null;
			if (oProduct instanceof WeraVariante)
			{
				// --- Produkt ist eine Variante
				oBaseProduct = (Product) m_wm.getAttribute(oProduct, "baseproduct");
			}
			else
			{
				// --- Product/ Satz ist Basis
				oBaseProduct = oProduct;
			}

			// --- Hole die Klassifizierenden Kategorien
			final Collection colClassificationClasses = (Collection) m_wm.getAttribute(oProduct, "classificationClasses");

			// --- Artikelnummer
			oElementARTICLE.addContent(new Element("SUPPLIER_AID").addContent(strSUPPLIER_AID));
                        
			// --- Artikelnummer (Alternativ / PK )
			oElementARTICLE.addContent(new Element("SUPPLIER_ALT_PID").addContent(strSUPPLIER_ALT_PID));

			// --- Artikel-Details
			final Element oElementDetails = new Element("ARTICLE_DETAILS");
			String strDescription1 = getValidString(((GeneratedWeraProduct) oBaseProduct).getDescription1(m_jaloSession
					.getSessionContext()));
                        
                        // --- Auslaufartikel --------------------------------------------------------------------------------------------------
                        if ( bIsOrderProduct ) {
                            Boolean bProduktAuslauf = bProduktAuslauf = new Boolean(false);
                            if ( oProduct instanceof WeraProduct )
                                bProduktAuslauf = (Boolean) m_wm.getAttribute(oProduct, "artikel_auslauf");
                            else 
                                bProduktAuslauf = (Boolean) m_wm.getAttribute(oProduct, "artikel_auslauf");
                            if ( bProduktAuslauf == null )  bProduktAuslauf = new Boolean(false);
                            if ( bProduktAuslauf.booleanValue() ) {
                                strDescription1 += "<br /><br />*Auslaufartikel / discontinued item*";
                                oElementDetails.addContent(new Element("PRODUCT_STATUS").addContent("Auslauf").setAttribute("type", "others"));
                            }
                       }
                        // --- Auslaufartikel --------------------------------------------------------------------------------------------------

			// --- HTML-Tags entfernen (Default=false)
			if (m_bHTML_Strip_enabled)
			{
				strDescription1 = strDescription1.replace("<br />", "\n");
				strDescription1 = removeHTML(strDescription1);
			}

			oElementARTICLE.addContent(oElementDetails);
			if (oBaseOfSetinSet != null)
			{
				// --- Später wieder ändern - 06.08.2012 GT
				oElementDetails.addContent(new Element("DESCRIPTION_SHORT").addContent((String) m_wm.getAttribute(oProduct, "code")));
				//oElementDetails.addContent(new Element("DESCRIPTION_SHORT").addContent((String) m_wm.getAttribute(oProduct,"name") ));
			}
			else
			{
				oElementDetails.addContent(new Element("DESCRIPTION_SHORT").addContent((String) m_wm.getAttribute(oBaseProduct,
						"name")));
			}
			oElementDetails.addContent(new Element("DESCRIPTION_LONG").addContent(strDescription1));
			oElementDetails.addContent(new Element("EAN").addContent((String) m_wm.getAttribute(oProduct, "ean")));
			oElementDetails.addContent(new Element("MANUFACTURER_AID").addContent(strProductCode));
			oElementDetails.addContent(new Element("MANUFACTURER_NAME").addContent(C_BMECAT_DISPLAYMANUFACTURE));

			// --- Schleife über alle Kategorien
			final Collection categoriesCATALOGGROUP = new ArrayList();
			for (final Iterator it1 = categories.iterator(); it1.hasNext();)
			{
				final Category oCategory = (Category) it1.next();
				final CatalogVersion catalogversion = (CatalogVersion) m_wm.getAttribute(oCategory, "catalogVersion");
				if (catalogversion.equals(m_weraCatalogVersion))
				{
					// --- Merke Kategorie
					categoriesCATALOGGROUP.add(oCategory);
				}
			}
			m_hashCATALOGGROUP.put(strSUPPLIER_AID, categoriesCATALOGGROUP);

			// --- Artikel / Kategorie Zuordnung
			boolean bInitCategoryWera = false;
			boolean bInitCategoryPClass = false;
			ClassificationClass oClassificationClass = null;
			final ArrayList aWeraCategory = new ArrayList();
			final ArrayList aPClassCategory = new ArrayList();
			Element oElementFeaturesProfiClass = null;
			Element oElementFeaturesWera = null;
			for (final Iterator it1 = colClassificationClasses.iterator(); it1.hasNext();)
			{
				oClassificationClass = (ClassificationClass) it1.next();
				// --- Portalattribute nicht verwenden!!
				if (oClassificationClass.getCode().equals("PORTALATTRIBUTES"))
				{
					continue;
				}

				final ClassificationSystemVersion catalogversion = (ClassificationSystemVersion) m_wm.getAttribute(
						oClassificationClass, "catalogVersion");
				if (catalogversion.toString().indexOf("werazusatz") != -1)
				{

					//aWeraCategory.add(oClassificationClass);
					//if ( bInitCategoryWera == false ) {
					if (!hashCategoriesWeraZusatz.containsKey(oClassificationClass.getCode()))
					{

						bInitCategoryWera = true;

						aWeraCategory.add(oClassificationClass);

						oElementFeaturesWera = new Element("ARTICLE_FEATURES");
						oElementFeaturesWera.addContent(new Element("REFERENCE_FEATURE_SYSTEM_NAME").addContent(catalogversion
								.getFullVersionName().toUpperCase()));
						oElementFeaturesWera.addContent(new Element("REFERENCE_FEATURE_GROUP_ID").addContent(oClassificationClass
								.getCode()));

						// --- Merke alle
						hashCategoriesWeraZusatz.put(oClassificationClass.getCode(), oElementFeaturesWera);
					}

				}
				else
				{

					if (bInitCategoryPClass == false)
					{

						bInitCategoryPClass = true;
						aPClassCategory.add(oClassificationClass);

						oElementFeaturesProfiClass = new Element("ARTICLE_FEATURES");
						oElementFeaturesProfiClass.addContent(new Element("REFERENCE_FEATURE_SYSTEM_NAME").addContent(catalogversion
								.getFullVersionName().toUpperCase()));
						// --- Artikel-Features (ProfiClass)
						oElementFeaturesProfiClass.addContent(new Element("REFERENCE_FEATURE_GROUP_ID").addContent(oClassificationClass
								.getCode()));

					}


				}

			} // --- for ( Iterator it1=categories.iterator(); it1.hasNext(); ) {


			// --- Initialisiere Merkmale
			for (final Iterator itCat1 = aPClassCategory.iterator(); itCat1.hasNext();)
			{
				final Category oCategory = (Category) itCat1.next();
				oInitVariationAttributes(bIsMasterProduct, oBaseProduct, oProduct, oElementFeaturesProfiClass, oElementFeaturesWera,
						oCategory);
			}

			// --- Initialisiere Merkmale
			for (final Iterator itCat2 = aWeraCategory.iterator(); itCat2.hasNext();)
			{
				final Category oCategory = (Category) itCat2.next();
				oElementFeaturesWera = (Element) hashCategoriesWeraZusatz.get(oCategory.getCode());
				oInitVariationAttributes(bIsMasterProduct, oBaseProduct, oProduct, oElementFeaturesProfiClass, oElementFeaturesWera,
						oCategory);
			}

			// --- proficlass System nur einfügen falls Features vorhanden
			if (aPClassCategory.size() > 0 && oElementFeaturesProfiClass.getChildren().size() > 2)
			{

				// --- Artikel-Features (PClass)
				oElementARTICLE.addContent(oElementFeaturesProfiClass);
			}

			// --- Wera System(e) nur einfügen falls Features vorhanden
			for (final Iterator itCat3 = aWeraCategory.iterator(); itCat3.hasNext();)
			{
				final Category oCategory = (Category) itCat3.next();
				oElementFeaturesWera = (Element) hashCategoriesWeraZusatz.get(oCategory.getCode());
				if (oElementFeaturesWera.getChildren().size() > 2)
				{
					// --- Artikel-Features (Wera)
					oElementARTICLE.addContent(oElementFeaturesWera);
				}
			}

			// --- ARTICLE_ORDER_DETAILS
			final Integer iMenge = (Integer) m_wm.getAttribute(oProduct, "contentQuantity");
			String strMenge = "";
			if (iMenge != null)
			{
				strMenge = iMenge.toString();
			}
			final Element oARTICLE_ORDER_DETAILS = new Element("ARTICLE_ORDER_DETAILS");
			oElementARTICLE.addContent(oARTICLE_ORDER_DETAILS);
			oARTICLE_ORDER_DETAILS.addContent(new Element("ORDER_UNIT").addContent("PCE"));
			oARTICLE_ORDER_DETAILS.addContent(new Element("CONTENT_UNIT").addContent("PCE"));
			oARTICLE_ORDER_DETAILS.addContent(new Element("NO_CU_PER_OU").addContent(strMenge));
			oARTICLE_ORDER_DETAILS.addContent(new Element("PRICE_QUANTITY").addContent("1"));
			oARTICLE_ORDER_DETAILS.addContent(new Element("QUANTITY_MIN").addContent(strMenge));
			oARTICLE_ORDER_DETAILS.addContent(new Element("QUANTITY_INTERVAL").addContent(strMenge));
/*
			// --- Logistische Daten
			final Element oElementPRODUCT_LOGISTIC_DETAILS = BMEcatGenPRODUCT_LOGISTIC_DETAILS(oProduct);
			oElementARTICLE.addContent(oElementPRODUCT_LOGISTIC_DETAILS);
*/
			// --- ARTICLE_PRICE_DETAILS	
			final Element oARTICLE_PRICE_DETAILS = BMEcatGenARTICLE_PRICE_DETAILS(oProduct);
			oElementARTICLE.addContent(oARTICLE_PRICE_DETAILS);


			// --- MIME_INFO				
			// --- Normal
			WeraMedia oWeraMedia = null;
			String strProduktImage = "";
			if (oBaseOfSetinSet != null)
			{
				strProduktImage = ((WeraProduct) oBaseOfSetinSet).normalizeFilenameForImageLookup() + ".jpg";
				oWeraMedia = m_wm._getPicture(oBaseOfSetinSet, "pictures1");
			}
			else
			{
				// --- BILD1
				strProduktImage = ((WeraProduct) oBaseProduct).normalizeFilenameForImageLookup() + ".jpg";
				oWeraMedia = m_wm._getPicture(oBaseProduct, "pictures1");
			}
			//String strProduktImage = strProduktBild.replace(".jpg","_normal.jpg");
			strProduktImage = _NormalizeImageName(strProduktImage);
			m_hashImages.put(strProduktImage, strProduktImage);

			//WeraMedia weramedia = m_wm._getPicture(oBaseProduct,"featureicons1");
			if (oWeraMedia != null)
			{
				m_mediaCollector.addMedia(oWeraMedia.getPK().toString(), strProduktImage, oWeraMedia.getFileName(),
						m_strImageTransferPath);
			}

			final Element oMIME_INFO = new Element("MIME_INFO");
			oElementARTICLE.addContent(oMIME_INFO);
			final Element oMIME = new Element("MIME");
			oMIME_INFO.addContent(oMIME);
			oMIME.addContent(new Element("MIME_TYPE").addContent("image/jpg"));
			oMIME.addContent(new Element("MIME_SOURCE").addContent(strProduktImage));
			oMIME.addContent(new Element("MIME_DESCR").addContent("Fotografie-Produktbild"));
			oMIME.addContent(new Element("MIME_PURPOSE").addContent("normal"));
			oMIME.addContent(new Element("MIME_ORDER").addContent("1"));

			// --- Media - Daten merken
			m_hashsetMedia.add(strProduktImage);


		} // --- if ( m_hashCATALOGGROUP.containsKey(strProductCode) == false ) {

		return oElementARTICLE;
	}

	private void oInitVariationAttributes(final boolean bIsMasterProduct, final Product oBaseProduct, final Product oProduct,
			final Element oElementFeaturesProfiClass, final Element oElementFeaturesWera, final Category oCategoryRoot)
	{


		// --- Initialize
		ClassificationAttribute oClassificationAttribute = null;
		final Category categoryByCa = null;
		final String strName = "";
		final String strCaName = "";
		final String strEinheit = "";
		final Element oCustomAttribute = null;
		final int iOrder = 1;

		try
		{
			// --- Nur 1x Initialisieren
			if (bIsMasterProduct)
			{
				LOG.debug(oProduct.getCode() + "<= Initialize Master =>" + m_attributesArticle.size());
			}
			else
			{
				LOG.debug(oProduct.getCode() + "<= Initialize Variant =>" + +m_attributesArticle.size());
			}
			if (true || m_attributesArticle.size() == 0)
			{

				// --- Initialize
				m_attributesArticle.clear();
				m_attributesProduct.clear();
				m_hashCategory.clear();

				// ---- Initialize Sichtbarkeit (Attribute)
				final EnumerationManager em = m_jaloSession.getEnumerationManager();
				EnumerationType et = null;
				final EnumerationValue ev = null;
				et = em.getEnumerationType("ClassificationAttributeVisibilityEnum");
				final EnumerationValue evVISIBLE = em.getEnumerationValue(et, "VISIBLE");
				final EnumerationValue evVISIBLE_IN_BASE = em.getEnumerationValue(et, "VISIBLE_IN_BASE");
				final EnumerationValue evVISIBLE_IN_VARIANT = em.getEnumerationValue(et, "VISIBLE_IN_VARIANT");

				// --- Hole alle ClassAttributeAssignment von Produkt / Variante oder Set
				final List<ClassAttributeAssignment> classattributeassignments = m_weraclassificationhelper
						.getAllClassAttributeAssignmentByProduct(oProduct);

				// --- Schleife über alle Attribute
				m_ouputcontrols = (Collection) m_wm.getAttribute(oBaseProduct, "outputcontrols");
				Outputcontrol outputcontrol = null;
				EnumerationValue evVisibility = null;
				for (final Iterator it1 = classattributeassignments.iterator(); it1.hasNext();)
				{
					// --- Hole ProfiClassAttribute
					final ClassAttributeAssignment oClassAttributeAssignment = (ClassAttributeAssignment) it1.next();
					oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();

					// --- Hole die Klassifizierende Klasse
					final ClassificationClass oClassificationClass = oClassAttributeAssignment.getClassificationClass();
					m_hashCategory.put(oClassificationAttribute.getCode(), oClassificationClass);

					// ---  Hole Ausgabesteuerung
					outputcontrol = (Outputcontrol) m_wm.checkContaining(m_ouputcontrols, "code", oClassificationAttribute.getCode());
					if (outputcontrol != null)
					{
						evVisibility = (EnumerationValue) m_wm.getAttribute(outputcontrol, "visibility");
					}
					else
					{
						evVisibility = evVISIBLE_IN_BASE;
					}

					// --- Sortieren der Merkmale
					if (m_hashCategory.containsKey(oClassificationAttribute.getCode())
							&& m_hashCategory.get(oClassificationAttribute.getCode()).equals(oCategoryRoot))
					{

						if (evVisibility.equals(evVISIBLE_IN_VARIANT) || evVisibility.equals(evVISIBLE))
						{
							if (!m_attributesArticle.contains(oClassAttributeAssignment))
							{
								m_attributesArticle.add(oClassAttributeAssignment);
							}
						}
						else if (!m_attributesProduct.contains(oClassAttributeAssignment))
						{
							m_attributesProduct.add(oClassAttributeAssignment);
						}

					} // --- if ( m_hashCategory.containsKey(oClassificationAttribute.getCode()) {

				} // --- for (Iterator it1 = attributes.iterator(); it1.hasNext();) {

				// --- Sortiere die Atrribute nach Order
				final OrderComparatorExportCAB orderComparatorExportCA = new OrderComparatorExportCAB();
				orderComparatorExportCA.init(m_ouputcontrols);
				if (m_attributesArticle != null && m_attributesArticle.size() > 0)
				{
					Collections.sort((List) m_attributesArticle, orderComparatorExportCA);
				}
			}

			// --- Erzeuge Featureliste
			GenFeatureList(bIsMasterProduct, m_attributesProduct, oBaseProduct, oElementFeaturesWera, oElementFeaturesProfiClass,
					oCategoryRoot);
			if (oProduct != oBaseProduct)
			{
				GenFeatureList(bIsMasterProduct, m_attributesArticle, oProduct, oElementFeaturesWera, oElementFeaturesProfiClass,
						oCategoryRoot);
			}


		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void GenFeatureList(final boolean bIsMasterProduct, final Collection attributesList, final Product oProduct,
			final Element oElementFeaturesWera, final Element oElementFeaturesProfiClass, final Category oCategoryRoot)
	{

		// --- Initialize
		String strProfiClassPfad = "";
		ClassificationAttribute oClassificationAttribute = null;
		ClassificationClass categoryByCa = null;
		final String strName = "";
		String strCaName = "";
		String strEinheit = "";
		final Element oCustomAttribute = null;
		int iOrder = 1;

		// --- Schleife über alle Attribute für Artikel
		for (final Iterator it2 = attributesList.iterator(); it2.hasNext();)
		{
			// --- Hole ProfiClassAttribute
			final ClassAttributeAssignment oClassAttributeAssignment = (ClassAttributeAssignment) it2.next();
			oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();
			categoryByCa = (ClassificationClass) m_hashCategory.get(m_wm.getAttribute(oClassificationAttribute, "code"));

			// --- Kategorie merken
			strProfiClassPfad = categoryByCa.getCode() + "\t";

			// --- Einheit
			strEinheit = _getUnit4CA(oClassAttributeAssignment, categoryByCa);
			if (strEinheit == null || strEinheit.trim().equals(""))
			{
				strEinheit = C_BMECAT_EMPTY_TAG;
			}
			strEinheit = strEinheit.trim();

			// --- Generate CA-Name
			strCaName = _NormalizeCAName(oClassificationAttribute, strEinheit);
			iOrder++;

			// --- Values
			final ArrayList colValue = new ArrayList();
			colValue.addAll(colGetCAValue(oProduct, oClassAttributeAssignment));

			// --- Merkmalbeschreibung Detail
			if (colValue.size() > 0 && colValue.get(0).toString().equals("0") == false)
			{
				final Element oFeature = new Element("FEATURE");
				String strFVALUE_DESCR = "";
				String strFVALUE = "";
				final String strFVALUE_DETAILS = "";
				//if ( colValue.size()> 1 && colValue.get(1).toString().contains("v") ) {
				if (colValue.size() > 1)
				{
					strFVALUE = (String) colValue.get(0);
					strFVALUE_DESCR = (String) colValue.get(1);
				}
				else
				{
					strFVALUE = (String) colValue.get(0);
				}
				if (strFVALUE_DESCR.trim().equals(""))
				{
					strFVALUE_DESCR = C_BMECAT_EMPTY_TAG;
				}
				if (strFVALUE.trim().equals(""))
				{
					strFVALUE = C_BMECAT_EMPTY_TAG;
				}
				strFVALUE = strFVALUE.trim();

				// --- Feature - Tag füllen
				oFeature.addContent(new Element("FNAME").addContent(oClassificationAttribute.getCode().trim()));
				oFeature.addContent(new Element("FVALUE").addContent(strFVALUE));
				oFeature.addContent(new Element("FUNIT").addContent(strEinheit.trim()));
				oFeature.addContent(new Element("FDESCR").addContent(oClassificationAttribute.getName()));
				oFeature.addContent(new Element("FVALUE_DETAILS").addContent(strFVALUE_DESCR));

				// --- Merkmal + Value + Unit merken
				strProfiClassPfad += oProduct.getName() + "\t";
				strProfiClassPfad += oCategoryRoot.getCode() + "\t";
				strProfiClassPfad += oClassificationAttribute.getCode() + "\t";
				strProfiClassPfad += strFVALUE.trim() + "\t";
				strProfiClassPfad += strEinheit + "\t";
				strProfiClassPfad += oClassificationAttribute.getName() + "\t";
				strProfiClassPfad += strFVALUE_DESCR;
				if (strProfiClassPfad.substring(0, 2).equals("AA"))
				{
					m_aArrayLog_30.add(strProfiClassPfad);
				}

				// --- Zuordnen zum System
				if (categoryByCa.getCode().substring(0, 1).equals("_") || strFVALUE.substring(0, 1).equals("_")
						|| oClassificationAttribute.getCode().substring(0, 1).equals("_") || oElementFeaturesProfiClass == null)
				{

					oElementFeaturesWera.addContent(oFeature);
				}
				else
				{

					oElementFeaturesProfiClass.addContent(oFeature);
				}

			}
		}

	}

	private String _NormalizeCAName(final ClassificationAttribute classificationAttribute, final String strEinheit)
	{
		// TODO Auto-generated method stub
		String strName = classificationAttribute.getName();

		strName = strName.replace("\u00e4", "228"); // --- "ä"
		strName = strName.replace("\u00c4", "196"); // --- "Ä"
		strName = strName.replace("\u00f6", "246"); // --- ö
		strName = strName.replace("\u00d6", "214"); // --- Ö
		strName = strName.replace("\u00fc", "252"); // --- ü
		strName = strName.replace("\u00dc", "220"); // --- Ü
		strName = strName.replace("\u00df", "223"); // --- ß
		strName = strName.replace(" ", "032");
		strName = strName.replace("(", "040");
		strName = strName.replace(")", "041");

		// --- Name vervollständigen
		strName += strEinheit;

		return strName;
	}

	//public Collection colGetCAValue(Product product, ClassificationAttribute classificationAttribute) {
	public Collection colGetCAValue(final Product product, final ClassAttributeAssignment classattributeassignment)
	{
		// TODO Auto-generated method stub

		// --- Initialize
		final Collection colResult = new ArrayList();
		String strResult = "";
		int iShowCntAttribute = 1;

		// --- Beim Produkt einen Dummy-Eintrag zeigen
		if (product instanceof WeraVariante)
		{
			iShowCntAttribute = 9999;
		}



		// --- J / N - Boolean
		if (classattributeassignment.getAttributeType().getCode()
				.equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.BOOLEAN))
		{

			final TypedFeature<Boolean> features = Feature.loadTyped(product, classattributeassignment);
			final List<FeatureValue<Boolean>> featurevalues = features.getValues();
			if (featurevalues.size() > 0)
			{
				final Boolean bValue = featurevalues.iterator().next().getValue();
				if (bValue.booleanValue())
				{
					strResult = "J";
				}
				else
				{
					strResult = "N";
				}
				colResult.add(strResult);
			}
		}
		else
		{

			// --- Hole alle Produktfeatures zum aktuellen Merkmal
			final HashMap<String, ClassificationAttributeValue> hFeatureValues = m_weraclassificationhelper
					.getPickedClassificationAttributeValuesByProduct(product, classattributeassignment);

			// --- Schleife über alle Produktfeatures
			for (final Iterator it1 = hFeatureValues.values().iterator(); it1.hasNext();)
			{
				// --- Hole ProductFeature
				//oProductFeature = (ProductFeature) it1.next();
				final ClassificationAttributeValue oClassificationAttributeValue = (ClassificationAttributeValue) it1.next();
				if (oClassificationAttributeValue == null)
				{
					continue;
				}


				// --- String - Result / Werreliste
				if (classattributeassignment.getAttributeType().getCode()
						.equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.STRING))
				{
					//if (oProductFeature.getType() == 4) {

					// --- Schleife über alle Merkmale
					final Item item = null;
					//Collection col = oProductFeature.getValues();
					//for (Iterator it2 = col.iterator(); it2.hasNext();) {
					// --- Hole Element
					//item = (Item) it2.next();

					// --- Anzahl der Werte eingrenzen
					iShowCntAttribute--;
					if (iShowCntAttribute < 0)
					{
						break;
					}

					// --- Initialisiere Merkmale
					try
					{
						//strResult = initProductFeature( classificationAttribute, item.getAttribute("code").toString(), (String)item.getAttribute("name") );
						strResult = initProductFeature(classattributeassignment, oClassificationAttributeValue.getAttribute("code")
								.toString(), (String) oClassificationAttributeValue.getAttribute("name"));
						colResult.add(strResult);
						colResult.add(oClassificationAttributeValue.getAttribute("code").toString());
					}
					catch (final Exception e)
					{
						e.printStackTrace();
					}

					//} // --- for (Iterator it2 = col.iterator(); it2.hasNext();) {

				}
				else
				{

					// --- Initialisiere Merkmale
					try
					{
						//strResult = initProductFeature( classificationAttribute, oProductFeature.getQualifier(), (String)oProductFeature.getAttribute("stringValue") );
						strResult = initProductFeature(classattributeassignment, oClassificationAttributeValue.getAttribute("code")
								.toString(), (String) oClassificationAttributeValue.getAttribute("name"));
						colResult.add(strResult);
						//colResult.add(oProductFeature.getQualifier());
						colResult.add(oClassificationAttributeValue.getAttribute("code").toString());

					}
					catch (final Exception e)
					{
						e.printStackTrace();
					}

				}

			} // --- for (Iterator it1 = productfeatures.iterator(); it1.hasNext();) {


		} // --- // --- J / N - Boolean



		return colResult;
	}


	private String initProductFeature(final ClassAttributeAssignment classattributeassignment, final String strWertId,
			String strWert) throws JaloInvalidParameterException, JaloSecurityException
	{
		// TODO Auto-generated method stub

		// --- Initialize
		final ClassificationAttribute classificationAttribute = classattributeassignment.getClassificationAttribute();
		final Outputcontrol outputcontrol = _getOutputcontrol(classificationAttribute);
		if (strWert == null)
		{
			strWert = "n/a";
		}
		String strBez = classificationAttribute.getName(m_jaloSession.getSessionContext());
		if (strBez == null)
		{
			strBez = "n/a";
		}

		// --- Datentyp
		String strType = "";
		final EnumerationValue ev = classattributeassignment.getAttributeType();
		if (ev != null)
		{
			if (ev.getCode().equals("string"))
			{
				strType = "alphanumerisch";
			}
			if (ev.getCode().equals("number"))
			{
				strType = "numerisch";
			}
			if (ev.getCode().equals("boolean"))
			{
				strType = "logisch";
				if (strWert.equals("true"))
				{
					strWert = "J";
				}
				else
				{
					strWert = "N";
				}
			}
		}
		/*
		 * // --- Korrigiere WertID (HIDDEN) if ( strWertId.length() > 0 ) { if ( strWertId.substring(0,2).equals("H_") )
		 * strWertId = strWertId.substring(2); if ( strWertId.substring(1).contains("_") ) strWertId =
		 * strWertId.substring(0, strWertId.substring(1).indexOf('_')+1 ); }
		 */
		// --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
		if (!m_strLanguage.equals("en") && !m_strLanguage.equals("us-en"))
		{
			strWert = strWert.replace(".", ",");
		}

		// --- Zoll-Zeichen die mit den Werten kommen entfernen
		strWert = strWert.replace("\"", "");
		strWert = strWert.replace("\u00b4", "");

		// --- Einheiten im Wert entfernen
		strWert = strWert.replace(" mm", "");
		strWert = strWert.replace(" Nm", "");
		strWert = strWert.replace(" in,", "");
		strWert = strWert.replace(" lbs,", "");
		strWert = strWert.replace(" lbf,ft", "");

		// --- Leerzeichen entfernen, bei Wert mit + / -
		if (strWert.contains("+") || strWert.contains("-"))
		{
			strWert = strWert.replace(" ", "");
		}


		return strWert;
	}

	public String _getUnit4CA(final ClassAttributeAssignment classattributeassignment, final Category category)
	{

		// --- Initialize
		final ClassificationAttribute classificationAttribute = classattributeassignment.getClassificationAttribute();
		final Outputcontrol outputcontrol = _getOutputcontrol(classificationAttribute);

		// --- Hole Einheit
		String stringUnit = "";
		if (outputcontrol != null)
		{
			stringUnit = (String) outputcontrol.getLocalizedProperty("unitca");
			if (stringUnit != null && stringUnit.equals("[ohne]"))
			{
				stringUnit = " ";
			}
		}
		ClassificationAttributeUnit oUnit = null;
		oUnit = classattributeassignment.getUnit();
		if (stringUnit == null || stringUnit.length() == 0)
		{
			if (oUnit != null)
			{
				//stringUnit = oUnit.getUnitType();
				stringUnit = oUnit.getSymbol();
			}
			else
			{
				stringUnit = "";
			}
		}

		return stringUnit;
	}

	private Outputcontrol _getOutputcontrol(final ClassificationAttribute classificationAttribute)
	{
		// --- Initialize
		Outputcontrol outputcontrol = null;

		if (m_ouputcontrols != null)
		{
			outputcontrol = (Outputcontrol) m_wm.checkContaining(m_ouputcontrols, "code", classificationAttribute.getCode());
		}

		return outputcontrol;
	}

	static public String _NormalizeImageName(String strImageName)
	{
		if (strImageName != null)
		{
			strImageName = strImageName.replace("\\", "/");
			final Pattern p = Pattern.compile("/");
			final String[] aRealName = p.split(strImageName);
			if (aRealName.length > 1)
			{
				strImageName = aRealName[aRealName.length - 1];
			}

			// --- Nur Kleinbuchstaben
			strImageName = strImageName.toLowerCase();
		}

		return strImageName;
	}

	private String _getRealImageName(final WeraMedia weramedia)
	{

		// --- Image für Liste der benötigten Grafiken merken
		final String strImageName = _NormalizeImageName(weramedia.getRealFileName());
		if (!m_hashImages.containsKey(strImageName))
		{
			m_hashImages.put(strImageName, strImageName);
		}

		return strImageName;
	}

	// --- ARTICLE_TO_CATALOGGROUP_MAP>...</ARTICLE_TO_CATALOGGROUP_MAP>
	public void BMEcatGenARTICLE_TO_CATALOGGROUP_MAP()
	{
		// --- Initialize
		String strArtikel = null;

		// --- Schleife über alle Zuordnungen
		for (final Iterator it1 = m_hashCATALOGGROUP.keySet().iterator(); it1.hasNext();)
		{
			strArtikel = (String) it1.next();

			// --- Holen der Kategorien
			final Collection categories = (Collection) m_hashCATALOGGROUP.get(strArtikel);
			for (final Iterator it2 = categories.iterator(); it2.hasNext();)
			{
				final Category oCategory = (Category) it2.next();
				final Element oElementCATALOGGROUP = new Element("ARTICLE_TO_CATALOGGROUP_MAP");
				oElementCATALOGGROUP.addContent(new Element("ART_ID").addContent(strArtikel));
				oElementCATALOGGROUP.addContent(new Element("CATALOG_GROUP_ID").addContent(oCategory.getCode()));

				// --- Element in Root-Knoten einfügen
				m_oNewCatalogElement.addContent(oElementCATALOGGROUP);
			}
		}
	}

	// --- BMECAT-VERSION
	public String _getBMECAT_VERSION()
	{
		return C_BMECAT_VERSION;
	}


	final String removeHTML(final String string)
	{

		final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
		final Pattern REMOVE_ENTITIES = Pattern.compile("&.{0,}?;");

		if (string == null || string.length() == 0)
		{
			return string;
		}

		final Matcher m = REMOVE_TAGS.matcher(string);
		final String sNoHTMLTags = m.replaceAll("");
		final Matcher m2 = REMOVE_ENTITIES.matcher(sNoHTMLTags);
		return m2.replaceAll("");
	}
	// -----------------------------------------------------------------------------------------------------
	// --- ENDE
	// -----------------------------------------------------------------------------------------------------
}

package com.computationaldesign.wera.jalo;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

import org.jdom.Comment;
import org.jdom.Element;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
//import de.hybris.platform.catalog.jalo.classification.proficlass.ProfiClassClassificationAttribute;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.util.Config;
import java.util.Comparator;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.constants.GeneratedCatalogConstants;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeUnit;
import org.apache.log4j.Logger;

class OrderComparatorExportCAC implements Comparator {

    public Collection m_ouputcontrols = new ArrayList();
    public WeraManager m_wm = null;

    public void init(Collection ouputcontrols) {
        m_ouputcontrols.addAll(ouputcontrols);
        m_wm = WeraManager.getInstance();
    }

    private Object _getOutputcontrol(String strCode) throws JaloInvalidParameterException, JaloSecurityException {
        // --- Initialize
        Object outputcontrol = null;

        if (m_ouputcontrols != null) {
            outputcontrol = (Object) m_wm.checkContaining(m_ouputcontrols, "code", strCode);
        }

        return outputcontrol;
    }

    public int compare(Object o1, Object o2) /*descending order*/ {

        // --- Initialize
        String strCode1 = "";
        String strCode2 = "";
        Object outputcontrol1 = null;
        Object outputcontrol2 = null;
        Integer iValue1 = new Integer(0);
        Integer iValue2 = new Integer(0);
        int iResult = 0;

        try {
            // --- Hole Ausgabesteuerung
            strCode1 = (String) ((ClassAttributeAssignment) o1).getClassificationAttribute().getAttribute("code");
            strCode2 = (String) ((ClassAttributeAssignment) o2).getClassificationAttribute().getAttribute("code");
            outputcontrol1 = _getOutputcontrol(strCode1);
            outputcontrol2 = _getOutputcontrol(strCode2);
            //System.out.println("outputcontrol1="+outputcontrol1);
            //System.out.println("outputcontrol2="+outputcontrol2);

            // --- Hole Values
            if (outputcontrol1 != null) {
                iValue1 = (Integer) ((Item) outputcontrol1).getAttribute("order");
            } else {
                iValue1 = (Integer) new Integer(1);
            }
            if (outputcontrol2 != null) {
                iValue2 = (Integer) ((Item) outputcontrol2).getAttribute("order");
            } else {
                iValue2 = (Integer) new Integer(1);
            }

        } catch (JaloInvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JaloSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if (iValue1 == null || iValue2 == null) {
            return 0;
        } else {
            return iValue1.compareTo(iValue2);
        }
    }
}

public class NexmartXml extends XmlSupport {

    private static final Logger LOG = Logger.getLogger(NexmartXml.class.getName());
    // --- Member
    boolean m_bTranferByFTP = true;
    String m_strImageTransferPath = "";
    String m_strDatenTransferPath = "";
    public String m_strResultPath = "";
    Product m_product = null;
    Product m_article = null;
    String m_strDatum = "";
    String m_strOutputPath = "";
    String m_strOutputFile = "";
    String m_strCatalog = "";
    CatalogVersion m_weraCatalogVersion = null;
    CatalogVersion m_weraMasterCatalogVersion = null;
    Collection m_ouputcontrols = null;
    int m_ioAA_img_pictoCounter = 0;
    boolean m_bCriticalError = false;
    ArrayList m_attributesArticle = null;
    HashMap m_hashCategory = null;
    HashMap m_hashImages = null;
    HashMap m_hashEAN = null;
    HashMap m_hashArtNr = null;
    Collection m_allProductsFromCatalog = new ArrayList();
    WeraClassificationHelper m_weraclassificationhelper = null;
    // --- Logfile
    FileWriter m_oFileWriterLog = null;
    ArrayList m_aArrayLog = new ArrayList();
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
    // --- NEXMART - Konstanten
    static final String C_NEXMART_SELLER = "wera";
    static final String C_NEXMART_SKU = "weraXXXXXXXXXXXX";
    static final String C_NEXMART_DISPLAYSELLER = "wera";
    static final String C_NEXMART_DISPLAYMANUFACTURE = "Wera Werkzeuge GmbH, Wuppertal";
    static final String C_NEXMART_CATEGORIES = "categories";
    static final String C_NEXMART_PRODUCTS = "products";
    // --- XML-Extensions
    static final String C_XMLEXT_XMLLANG = "extlang"; // xml:lang
    static final String C_XMLEXT_DTDT = "extdtdt"; // dt:dt
    // ---- Devirses
    static final String CATALOG_HAENDLER_PLATTFORM = "haendler_plattform";
	
	// --- csv-import
	private final HashMap<String, String> m_hDataLine = new HashMap();
	private final HashMap<String, Integer> m_hHeader = new HashMap();

    public NexmartXml() {
        super();
        // TODO Auto-generated constructor stub

        // --- Initialize
        m_strCatalog = CATALOG_HAENDLER_PLATTFORM;

        // --- Datum ermitteln
        m_strDatum = m_wm.InitOutputDatum();
    }

    // Zurücksetzen der Sprache, Aaufräumen
    public void cleanUp() {

        // --- Default		
        super.cleanUp();

        // --- XML-Knoten zurücksetzen
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
        m_hashCategory = null;
        m_strImageTransferPath = "";
        m_strDatenTransferPath = "";

        // --- Zähler zurücksetzen
        m_iOffsetIDCategory = 0;
        m_iOffsetIDEbene2 = 0;
        m_iOffsetIDEbene3 = 0;

        // --- LOG-File
        try {
            // --- Öffnen der LOG-Datei
            String strDatum = m_strDatum.substring(4, 8) + "_"
                    + m_strDatum.substring(2, 4) + "_" + m_strDatum.substring(0, 2);
            String strLine = "";
            m_oFileWriterLog = new FileWriter(m_strOutputPath + strDatum + "_nexmart.log");
            // System.out.println("m_aArrayLog.size()=" + m_aArrayLog.size());
            if (m_oFileWriterLog != null) {

                // ---- Schleife über alle Zeilen
                for (Iterator it1 = m_aArrayLog.iterator(); it1.hasNext();) {
                    strLine = (String) it1.next();
                    m_oFileWriterLog.write(strLine + "\r\n");
                }

                // --- Schliesen
                m_oFileWriterLog.close();

                // --- Log löschen
                m_aArrayLog.clear();
            }

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

    public boolean bInitNexMart(String strCatalogversion, boolean bTranferByFTP) {
        //SAXBuilder builder = new SAXBuilder();  // parameters control validation, etc
        //Document doc = builder.build(strXmlFile);

        // --- Initialize
        m_attributesArticle = new ArrayList();
        m_hashCategory = new HashMap();
        m_hashImages = new HashMap();
        m_hashEAN = new HashMap();
        m_hashArtNr = new HashMap();

        // --- Unser neuer Classifiactioan Helper
        m_weraclassificationhelper = new WeraClassificationHelper();

        // --- Transfer per FTP ein-/ ausschalten
        m_bTranferByFTP = bTranferByFTP;

        // --- MediaXML - Real Rootcontent "NexMart"
        m_rootElement = new Element("enfinity");

        // --- Init-Katalogversion
        m_weraMasterCatalogVersion = m_wm.getCatalogVersion(Config.getParameter("wera.mastercatalog"), Config.getParameter("wera.mastercatalogversion"));
        /*
         Namespace xsi = Namespace.getNamespace("xsi", 
         "http://www.w3.org/1999/XMLSchema-instance");
         m_rootElement.addNamespaceDeclaration(xsi);
         Namespace noNamespaceSchemaLocation = Namespace.getNamespace("noNamespaceSchemaLocation", 
         "http://www.intershop.com/xml/namespaces/enfinity");
         m_rootElement.addNamespaceDeclaration(noNamespaceSchemaLocation);
		
         //Namespace xml = Namespace.getNamespace("xml", 
         // "http://www.intershop.com/xml/namespaces/enfinity/XML");
         //m_rootElement.addNamespaceDeclaration(xml);
		
         Namespace dt = Namespace.getNamespace("dt", 
         "http://www.intershop.com/xml/namespaces/enfinity/DT");
         m_rootElement.addNamespaceDeclaration(dt);
         m_rootElement.setAttribute("major", "2" );
         m_rootElement.setAttribute("minor", "1" ); 
         m_rootElement.setAttribute("family", "enfinity" ); 
         m_rootElement.setAttribute("branch", "enterprise" ); 
         m_rootElement.setAttribute("build", "" ); 
         */
        // --- Ausgabedatum
        //Date oDate = new Date();
        //m_strDatum = oDate.toLocaleString();
        Comment dateComment = new Comment(" Datenexport vom: " + m_strDatum);
        m_rootElement.addContent(dateComment);

        // --- Ausgabedatei
        String strDatum = m_strDatum;
        m_strResultPath = "nexmart_" + strDatum;
        m_strOutputPath = Config.getParameter("wera.exportpath") + "katalog/" + strCatalogversion + "/" + m_strResultPath + "/";
        m_strOutputFile = C_NEXMART_SELLER + "_de_" + strDatum;

        // --- Pfad nalegen falls noch nicht vorhanden
        m_wm.createDirectory(Config.getParameter("wera.exportpath") + "katalog/" + strCatalogversion);
        m_wm.createDirectory(m_strOutputPath);

        // --- Auagabepfade für XML-Daten anlegen
        m_strImageTransferPath = "wera_de_" + strDatum + "_media";
        m_strDatenTransferPath = "wera_de_" + strDatum + "_daten";
        m_wm.createDirectory(m_strOutputPath + m_strImageTransferPath);
        m_wm.createDirectory(m_strOutputPath + m_strDatenTransferPath);

        return true;
    }

    // --- Überarbeiten der XML-Datei
    public void KorrekturXMLFile(String strXmlFile) {

        try {
            // --- Öffnen der XML-Datei zum lesen
            BufferedReader oFileReader = new BufferedReader(new FileReader(strXmlFile));;
            if (oFileReader != null) {

                // --- Erzeugen einer neuen Temp. Datei
                String strXmlFileTmp = strXmlFile + "_tmp";
                FileWriter oFileWriter = new FileWriter(strXmlFileTmp);

                String strLine = null;
                while ((strLine = oFileReader.readLine()) != null) {

                    // --- Replacements
                    strLine = strLine.replace(C_XMLEXT_XMLLANG, "xml:lang");
                    strLine = strLine.replace(C_XMLEXT_DTDT, "dt:dt");

                    if (strLine.startsWith("<enfinity")) {
                        strLine = "<enfinity xmlns:xsi=\"http://www.w3.org/1999/XMLSchema-instance\" xsi:noNamespaceSchemaLocation=\"http://www.intershop.com/xml/namespaces/enfinity\"  xmlns:xml=\"http://www.intershop.com/xml/namespaces/enfinity/XML\" xmlns:dt=\"http://www.intershop.com/xml/namespaces/enfinity/DT\" major=\"2\" minor=\"1\" family=\"enfinity\" branch=\"enterprise\" build=\"\" >";
                    }

                    // ---- Zeile schreiben
                    oFileWriter.write(strLine + "\r\n");
                }

                // --- Quelldatei schliessen
                if (oFileReader != null) {
                    oFileReader.close();
                    oFileReader = null;
                }
                if (oFileWriter != null) {
                    oFileWriter.close();
                    oFileWriter = null;
                }

                // --- Quelldatei löschen
                File file = new File(strXmlFile);
                if (file != null) {
                    file.delete();
                    file = null;
                }

                // --- Neue Datei umbenennen
                file = new File(strXmlFileTmp);
                if (file != null) {
                    file.renameTo(new File(strXmlFile));
                    file = null;
                }
            }

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

	
    // -----------------------------------------------------------------------------------------------
    // --- IMPORT einer CSV-Datei der Produkte -------------------------------------------------------
    // -----------------------------------------------------------------------------------------------
	/**
	 * Import einer CSV-Datei für zusätzliche Daten
	 * 
	 * @param sPfadName
	 * @param sFileName
	 * @return
	 */
	public String strImportNexmartCSV(final String sPfadName, final String sFileName )
	{

		// --- Debug
		LOG.info("strImportNexmartCSV ( start ) " + " sFileName=" + sPfadName + sFileName);

		// --- declare vars
		Collection colErrMessages = new ArrayList();
		String strLine = "";
		String strColumnName = "";
		final Collection<String> aMandatoryColsHeader = new ArrayList();

		// --- preset vars ---------------------------------------------------
		// --- initialize


		// --- list of mandatory columns
		aMandatoryColsHeader.addAll(Arrays.asList("codenr","artikel","rundungsmenge","ean"));
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
				if (m_hHeader.size() == 0)
				{
					LOG.info("Lese Zeile Header #" + iCnt + "...");
					// ---- Headerzeile initialisieren -----------------------------------------------------------
					for (int iCol = 0; iCol < aLineInput.length; iCol++)
					{
						strColumnName = aLineInput[iCol].toLowerCase();
						LOG.info("Baue Header auf: Spalte " + iCol + " = >" + strColumnName + "<");
						m_hHeader.put(strColumnName, new Integer(iCol));
					}

					// --- check all mandatory cols
					final Collection<String> colErrMessges = new ArrayList();
					for (final String strMandatoryColumnName : aMandatoryColsHeader)
					{
						if (!m_hHeader.containsKey(strMandatoryColumnName))
						{
							LOG.info("=> Spalte '" + strMandatoryColumnName + "' nicht vorhanden. Abbruch");
							colErrMessages.add("=> Spalte '" + strMandatoryColumnName + "' nicht vorhanden. Abbruch");
						}
					}

					// ---- Headerzeile initialisieren -----------------------------------------------------------

					// --- some errors?
					if (colErrMessages.size() > 0) // --- Abbruch
					{
						LOG.error("stop reading, found some errors.");
						break;
					}

				}
				else
				{
					// --- only full length lines
					if (aLineInput.length > 0)
					{

						// --- get code values as key 
						final String strCode = aLineInput[m_hHeader.get("codenr").intValue()];

						// --- collect line, and remember Codenummer as key
						m_hDataLine.put(strCode.trim(), strLine);

					} // --- if ( aLineInput1.length > 0 ) {
				}

			} // --- while ((strLine = reader.readLine()) != null) {
			  // --- Einlesen der Datei --------------------------------------------------------------------------------------------


			// --- close inputbuffering
			reader.close();


			// --- Daten import starts here --------------------------------------------------------------------------------------
/*
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
*/
		}
		catch (final Exception e)
		{
			LOG.error("+ErrorZeile=" + strLine);
			colErrMessages.add("ERROR\t<span style='color:red;'>+ErrorZeile=" + strLine + strLine + "</span>");

			e.printStackTrace();
		}



		// --- end of import
		LOG.info("strImportNexmartCSV ( ende ) " + " sFileName=" + sPfadName + sFileName);


		return ""; // StringUtils.join(colErrMessages.toArray(), "<br>");
	}
	
	
    // -----------------------------------------------------------------------------------------------
    // --- EXPORT der Produkte - ---------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------
    // --- Ausgabe einer XML-Datei aller Kategorien
    public String strJspEntryExportProductsXML(String strCatalog, String strCatalogVersion, boolean bTranferByFTP) {

        // --- Initialize
        m_strCatalog = strCatalog;

        // --- wera catalog und catalogversion holen, oder ggf. neu anlegen
        //CatalogVersion weraCatalogVersion = m_wm.getCatalogVersion( CATALOG_HAENDLER_PLATTFORM, strCatalogVersion );
        CatalogVersion weraCatalogVersion = m_wm.getCatalogVersion(strCatalog, strCatalogVersion);
		
		// --- import csv-data
		this.strImportNexmartCSV( "/home/hybris/nexmart/", "Kopie_von_PL_WERA_9_2015_04_01_Rundungsmengen.txt" );

        return _strExportProductsXML(weraCatalogVersion, bTranferByFTP);
    }

    public String strExportProductsXML(ClassificationSystemVersion weraCatalogVersion) {

        return _strExportProductsXML(weraCatalogVersion, true);
    }

    private String _strExportProductsXML(CatalogVersion weraCatalogVersion, boolean bTranferByFTP) {

        // --- Initialize
//        int iProductCounter = 0;
//        int iArticleCounter = 0;
//        String strOutput = "";
//        String strFN = "strExportCategoryXML ==> ";
//        Collection productsSorted = null;
//        Collection articles = null;
        String strResult = "";
        m_weraCatalogVersion = weraCatalogVersion;
        String strKatalogVersion = (String) m_wm.getAttribute(weraCatalogVersion, "version");
        HashSet hashSetProducts = new HashSet();

        // --- Hole Sprache
        m_strLanguage = "de";

        // --- Setze Sprache, und Defaultsprache=de
        initLanguage(m_strLanguage);

        // --- Initialisieren der XML-Struktur
        if (bInitNexMart(strKatalogVersion, bTranferByFTP)) {

            // --- Ausgabedatei
            m_strOutputFile += "_" + C_NEXMART_PRODUCTS + ".xml";

            // --- Catalog
            m_oCatalog = new Element("catalog");
            m_rootElement.addContent(m_oCatalog);

            // --- Alle Produkte#
            Product oProduct = null;
//            Element oXmlProduct = null;
            //Collection products = (Collection)WeraProduct.getAllProductsFromCatalog(weraCatalogVersion,true, "code");
            //for ( Iterator it1 = products.iterator(); it1.hasNext();) {
            // System.out.println("Anzahl=" + m_allProductsFromCatalog.size());
            for (Iterator it1 = m_allProductsFromCatalog.iterator(); it1.hasNext();) {

                // -- Get WERAPRODUKT
                oProduct = (Product) it1.next();

                // --- Dummy
                //if ( !oProduct.getCode().equals("testsetinset") ) { continue; }
                // --- Prüfe, ob das Produkt bereits ausgegeben wurde
                if (hashSetProducts.contains(WeraProduct.s_normalizeFilenameForImageLookup(oProduct.getCode()))) {
                    continue;
                }
                hashSetProducts.add(WeraProduct.s_normalizeFilenameForImageLookup(oProduct.getCode()));

                // --- Erzeuge ein Produktelement
                if (oProduct instanceof WeraProduct) {

                    // --- Hole alle Kategorien
                    Collection categories = (Collection) m_wm.getAttribute(oProduct, "supercategories");
                    //if ( m_wm.checkContaining( categories, "code", "SBKATALOG" ) != null )
                    //continue;
                    if (m_wm.checkContaining(categories, "code", "VERKAUFSHILFE") != null) {
                        continue;
                    }
                    if (m_wm.checkContaining(categories, "code", "PREISLISTENARTIKEL") != null) {
                        continue;
                    }

                    if (true) {
                        // --- Prüfe nochmal ob das Produkt auch aktiv ist!!!!
                        Boolean bAktiv = (Boolean) m_wm.getAttribute(oProduct, "aktiv");
                        if (bAktiv == null) {
                            bAktiv = new Boolean(false);
                        }
                        if (bAktiv.booleanValue()) {

/**
 * Ausgabe Satz in Satz in für NexMart passend machen und alle Sätze als 1 Element übertragen
 * diesen Teil hier wo die Sätze einzeln übertragen wurden deakivieren 26.08.2019 lt. RH
 * 
                            // --- prüfe auf Satz in Satz
                            if (oProduct instanceof WeraProductSetinSet) {

                                // --- SATZ in SATZ -------------------------------------
                                // --- Artikelliste eines Produktes
                                Collection productssets = null;
                                try {
                                    productssets = (Collection) m_wm.getAttribute(oProduct, "weraproductsetvariants_qual");

                                    // --- Schleife über alle Artikel
                                    WeraProductSetVariants oWeraProductSetVariants = null;
                                    WeraProductSet oWeraProductset = null;
                                    for (final Iterator it2 = productssets.iterator(); it2.hasNext();) {
                                        // --- Hole den aktuellen Artikel
                                        oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
                                        oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");

                                        // --- Fülle einen Artikel
                                        if (oWeraProductset != null) {
                                            // --- Produkt inkl. Varianten ausgeben
                                            oCreateProductElement((WeraProduct) oWeraProductset, (WeraProduct) oProduct);
                                        }
                                    }

                                } catch (final JaloInvalidParameterException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                // --- SATZ in SATZ -------------------------------------                                
                            } else {
                                // --- Produkt inkl. Varianten ausgeben
                                oCreateProductElement((WeraProduct) oProduct, (WeraProduct) oProduct);
                            }
*/
							// --- Produkt inkl. Varianten ausgeben
							oCreateProductElement((WeraProduct) oProduct, (WeraProduct) oProduct);
							
                        } // if (bAktiv.booleanValue()) {
                    }
                }

            } // --- for ( Iterator it1 = products.iterator(); it1.hasNext();) {

            // --- Schreiben der XML-Datei
            writeDocument(m_strOutputPath + m_strDatenTransferPath + "/", m_strOutputFile);

            // --- Überarbeiten der XML-Datei
            KorrekturXMLFile(m_strOutputPath + m_strDatenTransferPath + "/" + m_strOutputFile);

            // --- Schreiben der Bilderliste -----------------------------------------------
            Set keySet = m_hashImages.keySet();

            // --- Sortiere nach Namen
            List listImages = Arrays.asList(keySet.toArray());

            String strCR = "";
            if (Config.getParameter("wera.os").equals("linux")) {
                strCR = "\n";
            } else {
                strCR = "\r\n";
            }
            // --- Pfad nalegen falls noch nicht vorhanden
//            String strDatum = m_strDatum.substring(4, 8) + "_"
//                    + m_strDatum.substring(2, 4) + "_" + m_strDatum.substring(0, 2);
//            String strImageDir = "wera_de_" + strDatum + "_media";
            String strImageListFile = "";
            if (Config.getParameter("wera.os").equals("linux")) {
                strImageListFile = m_strOutputPath + "make_transferfile.sh";
            } else {
                strImageListFile = m_strOutputPath + "make_transferfile.bat";
            }
            LOG.info ( "Scriptfile für Transfer=" + strImageListFile );
            if (listImages != null && listImages.size() > 0) {
                Collections.sort((List) listImages);
            }
            String strImageName = "";
            try {
                FileWriter oFileWriterOK = new FileWriter(strImageListFile);
                File fileDirectory = null;

                if (Config.getParameter("wera.os").equals("linux")) {
                    oFileWriterOK.write("#!/bin/sh" + strCR + "#" + strCR + strCR);
                }

                if (Config.getParameter("wera.os").equals("windows")) {
                    m_strOutputPath.replace("/", "\\");
                }

                // --- Aktuelles Verzeichnis setzen
                oFileWriterOK.write("cd " + m_strOutputPath + strCR);

                // --- Schleife über alle Bilder
                for (Iterator it1 = listImages.iterator(); it1.hasNext();) {

                    strImageName = (String) it1.next();
                    fileDirectory = new File("/home/hybris/nexmart/imagepool/" + strImageName);

                    // --- Prüfen, ob die Datei vorhanden ist
                    if (fileDirectory.exists()) {
                        // --- Dateinamen holen und schreiben
                        if (Config.getParameter("wera.os").equals("windows")) {
                            oFileWriterOK.write("copy c:\\home\\hybris\\nexmart\\imagepool\\" + strImageName + " " + m_strOutputPath + m_strImageTransferPath + strCR);
                        } else {
                            oFileWriterOK.write("cp /home/hybris/nexmart/imagepool/" + strImageName + " " + m_strOutputPath + m_strImageTransferPath + strCR);
                        }
                    } else {
                        // --- Dateinamen holen und schreiben
                        m_aArrayLog.add("ERROR => Fehlendes Bild " + strImageName);
                    }
                }

                // --- Erzeugen der Archive
                if (Config.getParameter("wera.os").equals("linux")) {
                    oFileWriterOK.write("zip -9 -rv " + m_strImageTransferPath + " " + m_strImageTransferPath + strCR);
                    oFileWriterOK.write("rm -rf " + m_strImageTransferPath + strCR);
                    oFileWriterOK.write("zip -9 -rv " + m_strDatenTransferPath + " " + m_strDatenTransferPath + strCR);
                    oFileWriterOK.write("rm -rf " + m_strDatenTransferPath + strCR);
                }

                // --- Datei schliessen
                oFileWriterOK.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // --- Daten aufbereiten zum Transfer (Ausführen der SHell-Datei)
            if (Config.getParameter("wera.os").equals("linux")) {
                startCmdFile("chmod a+x " + strImageListFile);
                startCmdFile(strImageListFile);
            }

            // TODO:  
            if (m_bTranferByFTP) {

                m_aArrayLog.add("Daten werden per FTP auf den NexMart-Server übertragen...");
                if (true || m_bCriticalError == false) {
                    // --- Bilder umkopieren
                    // --- FTP-Transfer
                    bFTP_Transfer("80.66.100.5", "wera_de", "nIvANKf2", "nexmart_abnahme/mediendaten/catalog/media", m_strOutputPath + m_strImageTransferPath + ".zip");
                    bFTP_Transfer("80.66.100.5", "wera_de", "nIvANKf2", "nexmart_abnahme/produktdaten/neuanlage", m_strOutputPath + m_strDatenTransferPath + ".zip");
                }
            }

            if (true) {
                m_aArrayLog.add("Datentransfer erfolgreich abgeschlossen.");
            } else {
                m_aArrayLog.add("Datentransfer fehlerhaft.");
            }

            // --- Zurücksetzen der Sprache, Aaufräumen
            cleanUp();

        }

        return strResult;
    }

    private String ___orginal_26082019__strExportProductsXML(CatalogVersion weraCatalogVersion, boolean bTranferByFTP) {

        // --- Initialize
//        int iProductCounter = 0;
//        int iArticleCounter = 0;
//        String strOutput = "";
//        String strFN = "strExportCategoryXML ==> ";
//        Collection productsSorted = null;
//        Collection articles = null;
        String strResult = "";
        m_weraCatalogVersion = weraCatalogVersion;
        String strKatalogVersion = (String) m_wm.getAttribute(weraCatalogVersion, "version");
        HashSet hashSetProducts = new HashSet();

        // --- Hole Sprache
        m_strLanguage = "de";

        // --- Setze Sprache, und Defaultsprache=de
        initLanguage(m_strLanguage);

        // --- Initialisieren der XML-Struktur
        if (bInitNexMart(strKatalogVersion, bTranferByFTP)) {

            // --- Ausgabedatei
            m_strOutputFile += "_" + C_NEXMART_PRODUCTS + ".xml";

            // --- Catalog
            m_oCatalog = new Element("catalog");
            m_rootElement.addContent(m_oCatalog);

            // --- Alle Produkte#
            Product oProduct = null;
//            Element oXmlProduct = null;
            //Collection products = (Collection)WeraProduct.getAllProductsFromCatalog(weraCatalogVersion,true, "code");
            //for ( Iterator it1 = products.iterator(); it1.hasNext();) {
            // System.out.println("Anzahl=" + m_allProductsFromCatalog.size());
            for (Iterator it1 = m_allProductsFromCatalog.iterator(); it1.hasNext();) {

                // -- Get WERAPRODUKT
                oProduct = (Product) it1.next();

                // --- Dummy
                //if ( !oProduct.getCode().equals("testsetinset") ) { continue; }
                // --- Prüfe, ob das Produkt bereits ausgegeben wurde
                if (hashSetProducts.contains(WeraProduct.s_normalizeFilenameForImageLookup(oProduct.getCode()))) {
                    continue;
                }
                hashSetProducts.add(WeraProduct.s_normalizeFilenameForImageLookup(oProduct.getCode()));

                // --- Erzeuge ein Produktelement
                if (oProduct instanceof WeraProduct) {

                    // --- Hole alle Kategorien
                    Collection categories = (Collection) m_wm.getAttribute(oProduct, "supercategories");
                    //if ( m_wm.checkContaining( categories, "code", "SBKATALOG" ) != null )
                    //continue;
                    if (m_wm.checkContaining(categories, "code", "VERKAUFSHILFE") != null) {
                        continue;
                    }
                    if (m_wm.checkContaining(categories, "code", "PREISLISTENARTIKEL") != null) {
                        continue;
                    }

                    if (true) {
                        // --- Prüfe nochmal ob das Produkt auch aktiv ist!!!!
                        Boolean bAktiv = (Boolean) m_wm.getAttribute(oProduct, "aktiv");
                        if (bAktiv == null) {
                            bAktiv = new Boolean(false);
                        }
                        if (bAktiv.booleanValue()) {

                            // --- prüfe auf Satz in Satz
                            if (oProduct instanceof WeraProductSetinSet) {

                                // --- SATZ in SATZ -------------------------------------
                                // --- Artikelliste eines Produktes
                                Collection productssets = null;
                                try {
                                    productssets = (Collection) m_wm.getAttribute(oProduct, "weraproductsetvariants_qual");

                                    // --- Schleife über alle Artikel
                                    WeraProductSetVariants oWeraProductSetVariants = null;
                                    WeraProductSet oWeraProductset = null;
                                    for (final Iterator it2 = productssets.iterator(); it2.hasNext();) {
                                        // --- Hole den aktuellen Artikel
                                        oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
                                        oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");

                                        // --- Fülle einen Artikel
                                        if (oWeraProductset != null) {
                                            // --- Produkt inkl. Varianten ausgeben
                                            oCreateProductElement((WeraProduct) oWeraProductset, (WeraProduct) oProduct);
                                        }
                                    }

                                } catch (final JaloInvalidParameterException e) {
                                    // TODO Auto-generated catch block
                                    e.printStackTrace();
                                }

                                // --- SATZ in SATZ -------------------------------------                                
                            } else {
                                // --- Produkt inkl. Varianten ausgeben
                                oCreateProductElement((WeraProduct) oProduct, (WeraProduct) oProduct);
                            }
                        }
                    }
                }

            } // --- for ( Iterator it1 = products.iterator(); it1.hasNext();) {

            // --- Schreiben der XML-Datei
            writeDocument(m_strOutputPath + m_strDatenTransferPath + "/", m_strOutputFile);

            // --- Überarbeiten der XML-Datei
            KorrekturXMLFile(m_strOutputPath + m_strDatenTransferPath + "/" + m_strOutputFile);

            // --- Schreiben der Bilderliste -----------------------------------------------
            Set keySet = m_hashImages.keySet();

            // --- Sortiere nach Namen
            List listImages = Arrays.asList(keySet.toArray());

            String strCR = "";
            if (Config.getParameter("wera.os").equals("linux")) {
                strCR = "\n";
            } else {
                strCR = "\r\n";
            }
            // --- Pfad nalegen falls noch nicht vorhanden
//            String strDatum = m_strDatum.substring(4, 8) + "_"
//                    + m_strDatum.substring(2, 4) + "_" + m_strDatum.substring(0, 2);
//            String strImageDir = "wera_de_" + strDatum + "_media";
            String strImageListFile = "";
            if (Config.getParameter("wera.os").equals("linux")) {
                strImageListFile = m_strOutputPath + "make_transferfile.sh";
            } else {
                strImageListFile = m_strOutputPath + "make_transferfile.bat";
            }
            LOG.info ( "Scriptfile für Transfer=" + strImageListFile );
            if (listImages != null && listImages.size() > 0) {
                Collections.sort((List) listImages);
            }
            String strImageName = "";
            try {
                FileWriter oFileWriterOK = new FileWriter(strImageListFile);
                File fileDirectory = null;

                if (Config.getParameter("wera.os").equals("linux")) {
                    oFileWriterOK.write("#!/bin/sh" + strCR + "#" + strCR + strCR);
                }

                if (Config.getParameter("wera.os").equals("windows")) {
                    m_strOutputPath.replace("/", "\\");
                }

                // --- Aktuelles Verzeichnis setzen
                oFileWriterOK.write("cd " + m_strOutputPath + strCR);

                // --- Schleife über alle Bilder
                for (Iterator it1 = listImages.iterator(); it1.hasNext();) {

                    strImageName = (String) it1.next();
                    fileDirectory = new File("/home/hybris/nexmart/imagepool/" + strImageName);

                    // --- Prüfen, ob die Datei vorhanden ist
                    if (fileDirectory.exists()) {
                        // --- Dateinamen holen und schreiben
                        if (Config.getParameter("wera.os").equals("windows")) {
                            oFileWriterOK.write("copy c:\\home\\hybris\\nexmart\\imagepool\\" + strImageName + " " + m_strOutputPath + m_strImageTransferPath + strCR);
                        } else {
                            oFileWriterOK.write("cp /home/hybris/nexmart/imagepool/" + strImageName + " " + m_strOutputPath + m_strImageTransferPath + strCR);
                        }
                    } else {
                        // --- Dateinamen holen und schreiben
                        m_aArrayLog.add("ERROR => Fehlendes Bild " + strImageName);
                    }
                }

                // --- Erzeugen der Archive
                if (Config.getParameter("wera.os").equals("linux")) {
                    oFileWriterOK.write("zip -9 -rv " + m_strImageTransferPath + " " + m_strImageTransferPath + strCR);
                    oFileWriterOK.write("rm -rf " + m_strImageTransferPath + strCR);
                    oFileWriterOK.write("zip -9 -rv " + m_strDatenTransferPath + " " + m_strDatenTransferPath + strCR);
                    oFileWriterOK.write("rm -rf " + m_strDatenTransferPath + strCR);
                }

                // --- Datei schliessen
                oFileWriterOK.close();

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            // --- Daten aufbereiten zum Transfer (Ausführen der SHell-Datei)
            if (Config.getParameter("wera.os").equals("linux")) {
                startCmdFile("chmod a+x " + strImageListFile);
                startCmdFile(strImageListFile);
            }

            // TODO:  
            if (m_bTranferByFTP) {

                m_aArrayLog.add("Daten werden per FTP auf den NexMart-Server übertragen...");
                if (true || m_bCriticalError == false) {
                    // --- Bilder umkopieren
                    // --- FTP-Transfer
                    bFTP_Transfer("80.66.100.5", "wera_de", "nIvANKf2", "nexmart_abnahme/mediendaten/catalog/media", m_strOutputPath + m_strImageTransferPath + ".zip");
                    bFTP_Transfer("80.66.100.5", "wera_de", "nIvANKf2", "nexmart_abnahme/produktdaten/neuanlage", m_strOutputPath + m_strDatenTransferPath + ".zip");
                }
            }

            if (true) {
                m_aArrayLog.add("Datentransfer erfolgreich abgeschlossen.");
            } else {
                m_aArrayLog.add("Datentransfer fehlerhaft.");
            }

            // --- Zurücksetzen der Sprache, Aaufräumen
            cleanUp();

        }

        return strResult;
    }

    private Element oCreateProductElement(WeraProduct oProduct, WeraProduct oBasisProduct) {

        // --- Doppelte Produkte / Artikel entfernen
        String strCode = oProduct.getCode();
        if (		strCode.equals("2095 S") || strCode.equals("2096 S") || strCode.equals("2170 S")
				|| strCode.equals("8001 A SB DISPLAY") || strCode.equals("BC BR 30 8001 A SB DISPLAY") || strCode.equals("SCHIRM") || strCode.equals("BUNTSTIFT") ) {
            return null;
        }

        // --- Initialize
        m_attributesArticle.clear();
        m_hashCategory.clear();

        // --- Initialize
        m_oXmlProduct = new Element("product");

        // --- Initialize das Produktelement
        if (_oCreateProductElement(oProduct, m_oXmlProduct, oBasisProduct) != null) {
            m_oCatalog.addContent(m_oXmlProduct);
        }

        return m_oXmlProduct;
    }

    private Element oCreateVariantElement(WeraVariante oProduct) {

        // --- Initialize
        Element m_oXmlVariant = new Element("product");

        // --- Initialize Varinant
        if (_oCreateProductElement(oProduct, m_oXmlVariant, oProduct) != null) {
            m_oCatalog.addContent(m_oXmlVariant);
        }

        return m_oXmlVariant;
    }
	
	/**
	 * codenummer ermiteln
	 * @param oProduct
	 * @return 
	 */
	private String _getCodeNr ( Product oProduct ) {
        
		// --- Initialize
        boolean bIsMasterProduct = false;
        boolean bIsOrderProduct = false;
		
		if (oProduct instanceof WeraProductSet || oProduct instanceof WeraVariante) {
            bIsOrderProduct = true;
        }
		
        String strProductCode = "";
        if (bIsOrderProduct) {
            strProductCode = (String) m_wm.getAttribute(oProduct, "lagerNr");
            if (oProduct instanceof WeraVariante) {
                strProductCode += oProduct.getCode();
            } else {
                strProductCode += m_wm.getAttribute(oProduct, "artnr");
            }
            strProductCode += m_wm.getAttribute(oProduct, "variantenNr");
        } else // --- Initialize
        {
            strProductCode = ((WeraProduct) oProduct).normalizeFilenameForImageLookup();
        }
		
		return strProductCode;
	}

    private Element _oCreateProductElement(Product oProduct, Element oXmlProduct, Product oBasisProduct) {

        // --- Bearbeite Produkt
        m_aArrayLog.add("HINWEIS => Bearbeite Produkt: " + oProduct.getCode() + " - " + oProduct.getName());

        // --- Initialize
        boolean bIsMasterProduct = false;
        boolean bIsOrderProduct = false;
        if (oProduct instanceof WeraProduct
                && !(oProduct instanceof WeraProductSet)) {
            bIsMasterProduct = true;
            // --- Prüfe Alternativ-Produkt
            WeraProduct oAlternativProduct = ((WeraProduct) oProduct)
                    .getAlternateProduct(m_strLanguage);
            if (oAlternativProduct != null) {
                LOG.info("++Change to alternate product=" + oAlternativProduct.getCode() );
                oProduct = oAlternativProduct;
            }
        }
        if (oProduct instanceof WeraProductSet || oProduct instanceof WeraVariante) {
            bIsOrderProduct = true;
        }

        // --- Hole das Basisprodukt
        Product oBaseProduct = null;
        if (oProduct instanceof WeraVariante) // --- Produkt ist eine Variante
        {
            oBaseProduct = (Product) m_wm.getAttribute(oProduct, "baseproduct");
        } else // --- Product/ Satz ist Basis
        {
            oBaseProduct = oProduct;
        }

        // --- Order
        Integer oOrder = (Integer) m_wm.getAttribute(oProduct, "order");
        if (oOrder == null) {
            oOrder = new Integer(0);
        }
        String strNewOrder = "00000" + oOrder.toString();
        strNewOrder.substring(strNewOrder.length() - 6);

        // --- Codenummer holen
		String strProductCode	= _getCodeNr ( oProduct );

        // --- Püfen auf doppelte Produkte / EANs
        if (bIsOrderProduct) {

            // --- Prüfen der EAN-Nummer
            String strEAN = "";
            strEAN = (String) m_wm.getAttribute(oProduct, "ean");
            if (strEAN != null && strEAN.length() > 0) {
                if (m_hashEAN.containsKey(strEAN)) {
                    m_aArrayLog.add("CRITICAL => Doppelte EAN-Nummer (" + strEAN + ") bei Artikel:" + m_hashEAN.get(strEAN) + "!!");
                    m_aArrayLog.add("CRITICAL => Doppelte EAN-Nummer (" + strEAN + ") bei Artikel:" + strProductCode + " EAN-Nummer wird entfernt!!");
                    m_bCriticalError = true;
                    return null;
                } else {
                    m_hashEAN.put(strEAN, strProductCode);
                }
                if (strEAN.length() == 0) {
                    m_aArrayLog.add("CRITICAL => Fehlende EAN-Nummer bei:" + strProductCode + "!!");
                }
            }
            // --- Prüfe doppelte Produktnummern
            if (strProductCode != null && strProductCode.length() > 0) {
                if (m_hashArtNr.containsKey(strProductCode)) {
                    m_aArrayLog.add("CRITICAL => Doppelte Artnr-Nummer (" + strProductCode + ") bei Artikel:" + m_hashArtNr.get(strProductCode) + "!!");
                    m_aArrayLog.add("CRITICAL => Doppelte Artnr-Nummer (" + strProductCode + ") bei Artikel:" + strProductCode + " Artikel wird entfernt!!");
                    m_bCriticalError = true;
                    return null;
                } else {
                    m_hashArtNr.put(strProductCode, strProductCode);
                }
                if (strProductCode.length() == 0) {
                    m_aArrayLog.add("CRITICAL => Fehlende ArtNr-Nummer bei:" + strProductCode + "!!");
                }
            }
        }

        oXmlProduct.setAttribute("sku", C_NEXMART_SKU + strProductCode);
        oXmlProduct.addContent(oGetNewElement("sku", C_NEXMART_SKU + strProductCode));
        oXmlProduct.addContent(new Element("tax-class").setAttribute("id", "FullTax"));

        LOG.info("Code=" + strProductCode);

        // --- Produktabbildung
        Element oImage = new Element("image");
        oXmlProduct.addContent(oImage);

        oXmlProduct.addContent(new Element("product-type", "").setAttribute("name", "Basic"));
        oXmlProduct.addContent(oGetNewElement("online", "1"));
        oXmlProduct.addContent(oGetNewElement("list-price-used", "1"));

        // --- Auslaufartikel --------------------------------------------------------------------------------------------------
        String strAvailable = "1";
		Boolean bProduktAuslauf = bProduktAuslauf = new Boolean(false);
        if (bIsOrderProduct) {
            bProduktAuslauf = (Boolean) m_wm.getAttribute(oProduct, "artikel_auslauf");
            if (bProduktAuslauf == null) {
                bProduktAuslauf = new Boolean(false);
            }
            if (bProduktAuslauf.booleanValue()) {
                // --- Auslaufaritkel
                // strAvailable = "0";
            }
        }
        // --- Verfügbarkeit = 1 kaufbar / 0= Auslaufartikel
        oXmlProduct.addContent(oGetNewElement("available", strAvailable));
        // --- Auslaufartikel --------------------------------------------------------------------------------------------------

        // --- Order-required-attributes
        Element oOrderRequiredAttributes = new Element("order-required-attributes");
        oXmlProduct.addContent(oOrderRequiredAttributes);
        Element oOrderRequiredAttribute = new Element("order-required-attribute").setAttribute("name", "SellerCompanyName");
        oOrderRequiredAttributes.addContent(oOrderRequiredAttribute);
        oOrderRequiredAttribute = new Element("order-required-attribute").setAttribute("name", "AA_QuantityData");
        oOrderRequiredAttributes.addContent(oOrderRequiredAttribute);
        oOrderRequiredAttribute = new Element("order-required-attribute").setAttribute("name", "AA_QuantityDisplay");
        oOrderRequiredAttributes.addContent(oOrderRequiredAttribute);
        // --- Preis aA
        oOrderRequiredAttribute = new Element("order-required-attribute").setAttribute("name", "AA_standard_VK_EUR");
        oOrderRequiredAttributes.addContent(oOrderRequiredAttribute);
        if (bIsOrderProduct) {
            oOrderRequiredAttribute = new Element("order-required-attribute").setAttribute("name", "AA_standard_erp_sku");
            oOrderRequiredAttributes.addContent(oOrderRequiredAttribute);
            oOrderRequiredAttribute = new Element("order-required-attribute").setAttribute("name", "AA_standard_ean");
            oOrderRequiredAttributes.addContent(oOrderRequiredAttribute);
        }

        // --- manufacturer
        Element oManufacturer = new Element("manufacturer");
        oXmlProduct.addContent(oManufacturer);
        oManufacturer.addContent(oGetNewElement("manufacturer-name", C_NEXMART_DISPLAYMANUFACTURE));
        oManufacturer.addContent(oGetNewElement("manufacturer-sku", strProductCode));

        // --- category-links
        Category oAktCategory = null;
        Element oCategoryLinks = new Element("category-links");
        if (bIsMasterProduct || oProduct instanceof WeraProductSet) {
            //Collection categories = (Collection) m_wm.getAttribute(oProduct, "supercategories");

            Collection categories = (Collection) m_wm.getAttribute(oBasisProduct, "supercategories");
            // --- Schleife über alle Kategorien
            if (categories != null) {
                for (Iterator it2 = categories.iterator(); it2.hasNext();) {

                    Category category = (Category) it2.next();

                    // --- Kein PClass-Kategorie
                    if (category.getCode().substring(0, 2).equals("AA") || category.getCode().substring(0, 1).equals("_")) {
                        continue;
                    }

                    CatalogVersion testcsv = (CatalogVersion) m_wm.getAttribute(category, "catalogVersion");
                    if (testcsv.equals(m_weraCatalogVersion)) {
                        Boolean bAktivCatTmp = (Boolean) category.getLocalizedProperty("aktiv");
                        if (true || bAktivCatTmp != null && bAktivCatTmp.booleanValue()) {

                            // --- Kategorie-ID
                            String strNormalizedCategoryId = WeraProduct.s_normalizeFilenameForImageLookup(category.getCode());

                            Element oCategoryLink = new Element("category-link");
                            oOrder = (Integer) m_wm.getAttribute(category, "order");
                            strNewOrder = _GetOrderString(oOrder);
                            //oCategoryLink.setAttribute("name", C_NEXMART_SELLER + strNewOrder);
                            oCategoryLink.setAttribute("name", C_NEXMART_SELLER + strNormalizedCategoryId);
                            oCategoryLink.setAttribute("hotdeal", "0");
                            oCategoryLink.setAttribute("default", "0");
                            oCategoryLinks.addContent(oCategoryLink);
                            oAktCategory = category;
                        }
                    }
                }
            }
        } else {
            Element oCategoryLink = new Element("category-link");
            oCategoryLink.setAttribute("name", C_NEXMART_SELLER + "ProductVariations");
            oCategoryLink.setAttribute("hotdeal", "0");
            oCategoryLink.setAttribute("default", "0");
            oCategoryLinks.addContent(oCategoryLink);
        }

        // --- Preise und Bestelleinheiten
        if (bIsOrderProduct) {

            // --- Hole ContentQuantity
/*			
            Integer intContentQuantity = (Integer) m_wm.getAttribute(oProduct, "contentQuantity");
            if (intContentQuantity == null) {
                intContentQuantity = new Integer(1);
            }
*/			
			// --- Anpassungen laut Mail RH. vom 07.08.15
			// bitte beim nexmart-Export ab dem nächsten Export (01.09.2015) bei der Übergabe die Menge (abweichend von der in der hmc hinterlegten Verpackungseinheit) immer mit 1 angeben.
			// ###
			// 15.09.15 nexmart und kein Ende. Bitte noch einmal den export starten mit der Menge, die in der beigefügten Liste zu finden ist.
			// ###
            Integer intContentQuantity = new Integer(1);
			if ( m_hDataLine.containsKey(strProductCode) ) {
//System.out.println("found in csv");				
				// --- get menge aus CSV-Datei
				String strAktDataRow = m_hDataLine.get(strProductCode);
				// --- convert the oncemore    
				if ( strAktDataRow != null ) {
//System.out.println("found in csv 1");				
					final String[] aAktDataRow = strAktDataRow.split("\t");
					// --- preset 
					String strRundungsmenge = aAktDataRow[m_hHeader.get("rundungsmenge").intValue()];
					if ( strRundungsmenge != null && !strRundungsmenge.equals("") ) {
//System.out.println("found in csv 2");				
			            intContentQuantity = new Integer(strRundungsmenge.trim());
					}
				}
			}
			// --- Anpassungen laut Mail RH. vom 07.08.15
			
			
			
            // --- Bestelleinheiten quantity
            Element oQuantity = new Element("quantity");
            oXmlProduct.addContent(oQuantity);
            oQuantity.setAttribute("unit", "PCE");
            Element oMinOrderQuantity = new Element("min-order-quantity").addContent(intContentQuantity.toString());
            oQuantity.addContent(oMinOrderQuantity);
            Element oStepQuantity = new Element("step-quantity").addContent(intContentQuantity.toString());
            oQuantity.addContent(oStepQuantity);

            // --- Category-Links
            oXmlProduct.addContent(oCategoryLinks);

            // --- Product-list-prices
            Element oProductListPrices = new Element("product-list-prices");
            oXmlProduct.addContent(oProductListPrices);
            Element oProductListPrice = new Element("product-list-price");
            oProductListPrices.addContent(oProductListPrice);
            oProductListPrice.setAttribute("net-price", "1");
            oProductListPrice.setAttribute("currency", "EUR");
            oProductListPrice.addContent("0.00");
            oProductListPrice.addContent("");

        } else {

            // --- Bestelleinheiten quantity
            Element oQuantity = new Element("quantity");
            oXmlProduct.addContent(oQuantity);
            oQuantity.setAttribute("unit", "PCE");
            Element oMinOrderQuantity = new Element("min-order-quantity").addContent("1");
            oQuantity.addContent(oMinOrderQuantity);
            Element oStepQuantity = new Element("step-quantity").addContent("1");
            oQuantity.addContent(oStepQuantity);

            // --- Category-Links
            oXmlProduct.addContent(oCategoryLinks);

            // --- Product-list-prices
            Element oProductListPrices = new Element("product-list-prices");
            oXmlProduct.addContent(oProductListPrices);
            Element oProductListPrice = new Element("product-list-price");
            oProductListPrices.addContent(oProductListPrice);
            oProductListPrice.setAttribute("net-price", "1");
            oProductListPrice.setAttribute("currency", "EUR");
            oProductListPrice.addContent("0.00");
            oProductListPrice.addContent("");
        }

        // --- Zubebör bei Sätzen
        if (oProduct instanceof WeraProductSet) {
            Element oBundledProducts = oGetNewElement("bundled-products", "");
            oXmlProduct.addContent(oBundledProducts);
            Element oProductLinks = oGetNewElement("product-links", "");
            oXmlProduct.addContent(oProductLinks);

        }

        // --- Description
        oXmlProduct.addContent(oGetNewElement("name", oBaseProduct.getName()).setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        Element oShortDescription = oGetNewElement("short-description", "").setAttribute(C_XMLEXT_XMLLANG, "de-DE");
        oXmlProduct.addContent(oShortDescription);
        Element oLongDescription = oGetNewElement("long-description", "").setAttribute(C_XMLEXT_XMLLANG, "de-DE");
        oXmlProduct.addContent(oLongDescription);

        // --- Product-Varianten
        Element oVariationAttributes = null;
        if (bIsMasterProduct) {
            // --- variants
            m_oVariants = oCreateMasteredVariants((WeraProduct) oBaseProduct);
            oXmlProduct.addContent(m_oVariants);

            // --- Merkmale 
            oVariationAttributes = new Element("variation-attributes");
            m_oVariants.addContent(oVariationAttributes);
        }

        // --- Custom-Attributes
        Element oCustomAttributes = new Element("custom-attributes");
        oXmlProduct.addContent(oCustomAttributes);
        Element oCustomAttribute = _CreateCustomAttribute("SellerCompanyName", "de-DE", "string");
        oCustomAttribute.addContent(C_NEXMART_SELLER);
        oCustomAttributes.addContent(oCustomAttribute);
        // --- Preis aA
        oCustomAttribute = _CreateCustomAttribute("AA_standard_VK_EUR", "de-DE", "string");
        oCustomAttribute.addContent("0.00");
        oCustomAttributes.addContent(oCustomAttribute);
/*
		// --- Auslaufartikel
		if ( bProduktAuslauf.booleanValue() ) {
			oCustomAttribute = _CreateCustomAttribute("AA_txtlng_notes", "de-DE", "string");
			oCustomAttribute.addContent("Auslaufartikel, Lieferung auf Anfrage.");
			oCustomAttributes.addContent(oCustomAttribute);
		}
*/				
        if (bIsOrderProduct) {

            // --- Lesen der EAN
            String strEAN = "";
            strEAN = (String) m_wm.getAttribute(oProduct, "ean");

            oCustomAttribute = _CreateCustomAttribute("AA_standard_erp_sku", "de-DE", "string");
            oCustomAttribute.addContent(strProductCode);
            oCustomAttributes.addContent(oCustomAttribute);
            oCustomAttribute = _CreateCustomAttribute("AA_standard_ean", "de-DE", "string");
            oCustomAttribute.addContent(strEAN);
            oCustomAttributes.addContent(oCustomAttribute);
        }
        oCustomAttribute = _CreateCustomAttribute("AA_QuantityData", null, "string");
        oCustomAttributes.addContent(oCustomAttribute);
        _AddValueTag(oCustomAttribute, "N/A");
        _AddValueTag(oCustomAttribute, "PCE");

        // --- Order ---------- 
        oCustomAttribute = _CreateCustomAttribute("AA_standard_product_sequence", "de-DE", "string");
        oCustomAttributes.addContent(oCustomAttribute);
        Integer iOrder = new Integer(0);
        if (oProduct instanceof WeraVariante) {

            iOrder = (Integer) m_wm.getAttribute(oProduct, "order");
            if (iOrder == null) {
                iOrder = new Integer(0);
            }
        } else {

            // --- Hole die Reihenfolge an der Relation
            iOrder = m_wm._getCategory2ProductsOrder(oProduct, oAktCategory);
        }
        oCustomAttribute.addContent(_GetOrderString(iOrder));
        // --- Order ---------- 

        // --- Merkmalbilder
        m_ioAA_img_pictoCounter = 0;
        Element oAA_img_picto = _CreateCustomAttribute("AA_img_picto", "de-DE", "string");
        _AddValueTag(oAA_img_picto, "Pictogramme");

        // --- Generate AA_txtlng_1
        Element oAA_txtlng_1 = oCreateAA_txtlng_1(getValidString(((GeneratedWeraProduct) oBaseProduct).getDescription1(m_jaloSession.getSessionContext())));

        // --- Generate AA_txtlng_2 (Fussnoten)
        Element oAA_txtlng_2 = oCreateAA_txtlng_2(oBaseProduct, oProduct);

        // --- Tabelle (variation-attributes) =>Satz Inhalt --------------------------------------------------
        Element oAA_txtval_techgroup_2 = null;
        if ( oProduct instanceof WeraProductSetinSet ) {
			
			// --- SATZ in SATZ -------------------------------------
			// --- Artikelliste eines Produktes
			Collection productssets = null;
			try {
				productssets = (Collection) m_wm.getAttribute(oProduct, "weraproductsetvariants_qual");

				// --- Schleife über alle Artikel
				WeraProductSetVariants oWeraProductSetVariants = null;
				WeraProductSet oWeraProductset = null;
				Integer oWeraProductsetQuantity = new Integer(1);
				for (final Iterator it2 = productssets.iterator(); it2.hasNext();) {
					// --- Hole den aktuellen Artikel
					oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
					oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");
					oWeraProductsetQuantity = (Integer) m_wm.getAttribute(oWeraProductSetVariants, "vpe");
					if ( oWeraProductsetQuantity == null ) {
						oWeraProductsetQuantity = new Integer(1);
					}
					
					// --- Fülle einen Artikel
					if (oWeraProductset != null) {
						
						// --- Codenummer holen
						String strProductCodeInhalt	= oWeraProductset.getCode();
           
						// --- satzinhalt darstellen
						oAA_txtval_techgroup_2 = oCreateAA_txtval_techgroup_2(oWeraProductset,  
								oWeraProductsetQuantity.toString()
								+ " x " + strProductCodeInhalt
								+ " Inhalt:" , oAA_img_picto, oAA_txtval_techgroup_2 );
						
					}
				}

			} catch (final JaloInvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// --- SATZ in SATZ -------------------------------------                                

		} else if ( oProduct instanceof WeraProductSet ) { 			
            
			// --- satzinhalt darstellen
			oAA_txtval_techgroup_2 = oCreateAA_txtval_techgroup_2(oProduct, "Inhalt", oAA_img_picto, null );
		}
        // --- Tabelle (variation-attributes) =>Satz Inhalt --------------------------------------------------

		
        // --- Tabelle (variation-attributes) =>Product-Varianten
        Element oAA_txtval_techgroup_3 = null;
        if (!(oProduct instanceof WeraProductSet)) {
            oAA_txtval_techgroup_3 = oInitVariationAttributes(bIsMasterProduct, oBaseProduct, oProduct, oVariationAttributes, oCustomAttributes);
        }

        // --- Übernehme Techgroup 2
        if (oAA_txtval_techgroup_2 != null) {
            oCustomAttributes.addContent(oAA_txtval_techgroup_2);
        }

        // --- Übernehme Techgroup 3
        if (oAA_txtval_techgroup_3 != null) {
            oCustomAttributes.addContent(oAA_txtval_techgroup_3);
        }

        // --- Übernehme oAA_txtlng_1
        if (oAA_txtlng_1 != null) {
            oCustomAttributes.addContent(oAA_txtlng_1);
        }

        // --- Übernehme oAA_txtlng_2
        if (oAA_txtlng_2 != null) {
            oCustomAttributes.addContent(oAA_txtlng_2);
        }

        // -------------------------------------
        // --- Images --------------------------
        // -------------------------------------
        // --- Hole das Basisprodukt
        if (oProduct instanceof WeraVariante) // --- Produkt ist eine Variante
        {
            oBaseProduct = (Product) m_wm.getAttribute(oProduct, "baseproduct");
        } else // --- Product/ Satz ist Basis
        {
            oBaseProduct = oBasisProduct;
        }

        // --- Normal
        WeraMedia oWeraMedia = null;
        String strProduktBild = ((WeraProduct) oBaseProduct).normalizeFilenameForImageLookup() + ".jpg";
        oCustomAttribute = _CreateCustomAttribute("AA_img_normal", "de-DE", "string");
        oCustomAttributes.addContent(oCustomAttribute);
        _AddValueTag(oCustomAttribute, "Hauptproduktbild");
        _AddValueTag(oCustomAttribute, "Hauptproduktbild");
        String strProduktImage = strProduktBild.replace(".jpg", "_normal.jpg");
        strProduktImage = _NormalizeImageName(strProduktImage);
        _AddValueTag(oCustomAttribute, strProduktImage);
        if (oProduct instanceof WeraProductSet) {
            oImage.addContent(strProduktImage);
        }
        m_hashImages.put(strProduktImage, strProduktImage);

        // --- PICTO => Hole Antrieb, Abtrieb-Symbole
        // --- Tag anlegen -------------------------------------------------------------------------------------------------------------
        // --- ICON 1 (Liste) ----------------------------------------------------------------------------------------------------------
        // WeraMedia weramedia = m_wm._getPicture(weraProduct, "icons1");
        Collection<WeraMedia> colIconMedias = ( Collection<WeraMedia>)m_wm.getAttribute(oBaseProduct, "icons1");
        if ( colIconMedias != null ) {

            // --- iterate on all icons
            for (final Iterator itIconMedias = colIconMedias.iterator(); itIconMedias.hasNext();) {
                
                // --- Hole Artikel / Variante
                oWeraMedia = (WeraMedia) itIconMedias.next();
                if (oWeraMedia != null) {
                    _AddValueTag(oAA_img_picto, "Merkmal " + new Integer(++m_ioAA_img_pictoCounter).toString());
                    _AddValueTag(oAA_img_picto, _getRealImageName(oWeraMedia));
                }
            }

        } // --- if ( colIconMedias != null ) {
        // --- ICON 1 (Liste) ----------------------------------------------------------------------------------------------------------
        
        // --- ICON 2 ------------------------------------------------------------------------------------------------------------------
        oWeraMedia = m_wm._getPicture(oBaseProduct, "icons2");
        if (oWeraMedia != null) {
            _AddValueTag(oAA_img_picto, "Merkmal " + new Integer(++m_ioAA_img_pictoCounter).toString());
            _AddValueTag(oAA_img_picto, _getRealImageName(oWeraMedia));
        }
        // --- ICON 2 ------------------------------------------------------------------------------------------------------------------

        
        // --- PICTO => Hole Feature-Icons
        HashMap mapFeatureIcons = ((WeraProduct) oBaseProduct).getFeatureIconsByBooleanProperties();
        if (mapFeatureIcons != null) {

            // --- Hole Collection der Media-Objekte
            Collection colMedia = (Collection) mapFeatureIcons.get("iconlist");
            if (colMedia != null) {

                for (Iterator itMedia = colMedia.iterator(); itMedia.hasNext();) {
                    // --- Hole Artikel / Variante
                    oWeraMedia = (WeraMedia) itMedia.next();

                    // --- FEATRUREICONS n
                    _AddValueTag(oAA_img_picto, "Merkmal " + new Integer(++m_ioAA_img_pictoCounter).toString());
                    _AddValueTag(oAA_img_picto, _getRealImageName(oWeraMedia));
                }
            }
        }

        // --- Thumb
        oCustomAttribute = _CreateCustomAttribute("AA_img_thumb", "de-DE", "string");
        oCustomAttributes.addContent(oCustomAttribute);
        _AddValueTag(oCustomAttribute, "Thumbnail");
        _AddValueTag(oCustomAttribute, "Thumbnail");
        strProduktImage = strProduktBild.replace(".jpg", "_thumb.jpg");
        strProduktImage = _NormalizeImageName(strProduktImage);
        _AddValueTag(oCustomAttribute, strProduktImage);
        m_hashImages.put(strProduktImage, strProduktImage);

        // --- UDSCASE
        // --- Zoombild
        oCustomAttribute = _CreateCustomAttribute("AA_img_zoom", "de-DE", "string");
        oCustomAttributes.addContent(oCustomAttribute);
        _AddValueTag(oCustomAttribute, "Zoombild");
        _AddValueTag(oCustomAttribute, "Zoombild");
        strProduktImage = strProduktBild.replace(".jpg", "_zoom.jpg");
        strProduktImage = _NormalizeImageName(strProduktImage);
        _AddValueTag(oCustomAttribute, strProduktImage);
        m_hashImages.put(strProduktImage, strProduktImage);

        // --- Pictogramme anhängen
        if (m_ioAA_img_pictoCounter > 0) {
            oCustomAttributes.addContent(oAA_img_picto);
        }

        return oXmlProduct;
    }

    private Element oCreateAA_txtlng_2(Product baseProduct, Product product) {
        // TODO Auto-generated method stub

        // --- Initialize
        String strKennzeichner = "";
        Collection footnotes1 = new ArrayList();
        Collection footnotes2 = new ArrayList();

        // --- Tag anlegen
        Element oAA_txtlng_2 = null;

        // --- Hole die Fussnoten
        footnotes1 = ((GeneratedWeraProduct) baseProduct).getFootnotes();
        if (!product.equals(baseProduct)) {
            footnotes2 = ((WeraVariante) product).getFootnotes();
        }

        // --- Sind Fussnoten vorhanden?
        if (footnotes1.size() > 0 || footnotes2.size() > 0) {

            // --- Tag anlegen
            oAA_txtlng_2 = _CreateCustomAttribute("AA_txtlng_2", "de-DE", "string");
            Element oValue = _AddValueTag(oAA_txtlng_2, "Besondere Hinweise");

            // --- Hole Fussnoten = >Schleife über alle Attribute für Produkt
            for (Iterator it2 = footnotes1.iterator(); it2.hasNext();) {
                // --- Hole ProfiClassAttribute
                Footnote oFootnote = (Footnote) it2.next();

                strKennzeichner = "*";
                oValue = _AddValueTag(oAA_txtlng_2, strKennzeichner);
                oValue = _AddValueTag(oAA_txtlng_2, oFootnote.getName());

            } // --- for (Iterator it2 = footnotes.iterator(); it2.hasNext();) {

            // --- Hole Fussnoten = >Schleife über alle Attribute für Produkt
            int iCounter = 1;
            for (Iterator it2 = footnotes2.iterator(); it2.hasNext();) {
                // --- Hole ProfiClassAttribute
                Footnote oFootnote = (Footnote) it2.next();

                strKennzeichner = new Integer(iCounter++).toString() + ".";
                oValue = _AddValueTag(oAA_txtlng_2, strKennzeichner);
                oValue = _AddValueTag(oAA_txtlng_2, oFootnote.getName());

            } // --- for (Iterator it2 = footnotes.iterator(); it2.hasNext();) {

        }

        return oAA_txtlng_2;
    }

    private Element oCreateAA_txtlng_1(String strTagContent) {

        // --- Tag anlegen
        Element oAA_txtlng_1 = _CreateCustomAttribute("AA_txtlng_1", "de-DE", "string");
        Element oValue = null;
        oValue = _AddValueTag(oAA_txtlng_1, "Beschreibung");

        // --- Initialize
        boolean bLeftElement = false;
        String strTypeName = "";
        strTagContent = strTagContent.replaceAll("<strong>", "<b>");
        strTagContent = strTagContent.replaceAll("</strong>", "</b>");
        strTagContent = strTagContent.replaceAll("<B>", "<b>");
        strTagContent = strTagContent.replaceAll("</B>", "</b>");
        strTagContent = strTagContent.replaceAll("<BR", "<br");
        strTagContent = strTagContent.replaceAll("<bR", "<br");
        strTagContent = strTagContent.replaceAll("<Br", "<br");
        strTagContent = strTagContent.replaceAll("<br />", "<br/>");
        strTagContent = strTagContent.replaceAll("<br  />", "<br/>");
        String aList[] = strTagContent.split("<br/>");

        // --- Schleife über alle Einträge
        boolean bOutput = false;
        for (int iPos = 0; iPos < aList.length; iPos++) {
            // --- strTypeName
            bOutput = false;
            String aElements[] = aList[iPos].split("</b>");

            // --- Content
            if (aElements.length == 2) {
                strTypeName = aElements[0].trim();
                strTagContent = aElements[1].trim();
                bOutput = true;
            } else {
                if (aElements.length > 0) {
                    strTypeName = "  ";
                    strTagContent = aElements[0].trim();
                    bOutput = true;
                }
            }

            if (bOutput) {

                // --- Aufräumen
                strTagContent = strTagContent.replace("<b>", "");
                strTagContent = strTagContent.replace("</b>", "");
                strTagContent = strTagContent.replace("<br/>", "");
                strTypeName = strTypeName.replace("<b>", "");
                strTypeName = strTypeName.replace("</b>", "");
                strTypeName = strTypeName.replace("<br/>", "");

                // --- Textzähler
                oValue = _AddValueTag(oAA_txtlng_1, strTypeName);
                oValue = _AddValueTag(oAA_txtlng_1, strTagContent);
            }
        }

        //System.out.println("e.createTextElement.length=" + strTagContent);
        return oAA_txtlng_1;
    }

    // --- Inhalt der Sätze
    private Element oCreateAA_txtval_techgroup_2(Product weraProductSet, String strStringBesch, Element oAA_img_picto, Element oAA_txtval_techgroup_2) {
		
        // --- Tag anlegen falls noch nicht vorhanden
		if ( oAA_txtval_techgroup_2 == null ) {
			
			oAA_txtval_techgroup_2 = _CreateCustomAttribute("AA_txtval_techgroup_2", "de-DE", "string");
		}
        Element oValue = _AddValueTag(oAA_txtval_techgroup_2, strStringBesch);

        // --- Initialize
        int iPos = 0;
        WeraMedia icon1 = null;
        WeraMedia icon2 = null;
        ArrayList aContent = null;
        ArrayList colHash = null;
        HashMap oHashIcons = new HashMap();
        HashMap oHashMapProdukt = null;
        HashMap oHashMapArtikel = null;
        String strTypeName = "";
        String strTagContent = "";

        // --- Schleife über alle Content-Inhalte
        aContent = ((WeraProductSet) weraProductSet).generateWeraProductSetData();
        if (aContent != null && aContent.size() > 0) {
            for (Iterator it1 = aContent.iterator(); it1.hasNext();) {

                // --- Hole Map
                oHashMapProdukt = (HashMap) it1.next();
                iPos++;

                // --- Initialize
                strTypeName = (String) oHashMapProdukt.get("code");
                strTagContent = "";

                colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
                if (colHash != null && colHash.size() > 0) {
                    int iPos1 = 0;
                    for (Iterator it2 = colHash.iterator(); it2.hasNext();) {
                        // --- Hole Map
                        iPos1++;
                        oHashMapArtikel = (HashMap) it2.next();
                        if (oHashMapArtikel != null) {
                            if (iPos1 < colHash.size()) {
                                strTagContent += oHashMapArtikel.get("value") + ";";
                            } else {
                                strTagContent += oHashMapArtikel.get("value");
                            }
                        }
                    }
                }

                // --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
                if (!m_strLanguage.equals("en") && !m_strLanguage.equals("us-en")) {
                    strTagContent = strTagContent.replace(".", ",");
                }

                // --- Content korrigieren
                if (strTagContent.length() > 2) {
                    strTagContent = strTagContent.trim();
                }

                // --- Zusammenhalten einer Zeile
                // --- Textobjekt anlegen
                // --- Textzähler
                oValue = _AddValueTag(oAA_txtval_techgroup_2, strTypeName);
                oValue = _AddValueTag(oAA_txtval_techgroup_2, strTagContent);
                oValue = _AddValueTag(oAA_txtval_techgroup_2, "");

/*                
            			final Collection<WeraMedia> colWeraMediaIcon1 = (Collection<WeraMedia>) oHashMapProdukt.get("icons1_collection");
                                int iCntIcon = 1;
                                if ( colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1 ) {

                                     // --- iterate on icon-collection
                                    for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)
                                    {
                                        // --- get icon
                                        icon1  = (WeraMedia) itIconMedias.next();
                                        if (icon1 != null)
                                        {
                                            final Element oIcon1 = _initMerkmalIcon(null, null, icon1, null, "icons", new Integer(iCntIcon).toString() );
                                            oIcon1.setAttribute("Sequence", "1" );
                                            
                                            // --- media-icon (text-element zuweisen)
                                            // achtung print-export braucht einen zusätzlichen "Text"-node, daher auslagern in methode!!
                                            _assign2TextList ( iCntIcon, iOrder, oIcon1, textXML, oTexList );
                                            
                                            // --- icon-counter
                                            iCntIcon++;
                                        }
                                        
                                    } // ---for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)
                                    
                                } // --- if ( colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1 ) {
*/                                
                // --- Icon 1 -------------------------------------------------------------------------------------------------------------------------
                // icon1 = (WeraMedia) oHashMapProdukt.get("icons1");
                final Collection<WeraMedia> colWeraMediaIcon1 = (Collection<WeraMedia>) oHashMapProdukt.get("icons1_collection");
                if ( colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1 ) {

                     // --- iterate on icon-collection
                    for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();) {
                        
                        // --- get icon
                        icon1  = (WeraMedia) itIconMedias.next();
                        if (icon1 != null) {
                            if (!oHashIcons.containsKey(icon1.getRealFileName())) {
                                _AddValueTag(oAA_img_picto, "Merkmal " + new Integer(++m_ioAA_img_pictoCounter).toString());
                                _AddValueTag(oAA_img_picto, _getRealImageName(icon1));
                                oHashIcons.put(icon1.getRealFileName(), icon1.getRealFileName());
                            }
                        }
                        
                    } // --- for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)
                    
                } // --- if ( colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1 ) {

                // --- Icon 2 ------------------------------------------------------------------------------------------------------------------------- 
                // icon2 = (WeraMedia) oHashMapProdukt.get("icons2");
                final Collection<WeraMedia> colWeraMediaIcon2 = (Collection<WeraMedia>) oHashMapProdukt.get("icons2_collection");
                if ( colWeraMediaIcon2 != null && colWeraMediaIcon2.size() >= 1 ) {

                     // --- iterate on icon-collection
                    for (final Iterator itIconMedias = colWeraMediaIcon2.iterator(); itIconMedias.hasNext();) {
                        
                        // --- get icon
                        icon2  = (WeraMedia) itIconMedias.next();
                        if (icon2 != null) {
                            if (!oHashIcons.containsKey(icon2.getRealFileName())) {
                                _AddValueTag(oAA_img_picto, "Merkmal " + new Integer(++m_ioAA_img_pictoCounter).toString());
                                _AddValueTag(oAA_img_picto, _getRealImageName(icon2));
                                oHashIcons.put(icon2.getRealFileName(), icon2.getRealFileName());
                            }
                        }
                        
                    } // --- for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)
                    
                } // --- if ( colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1 ) {
                

            } // --- for ( Iterator it1 = aContent.iterator(); it1.hasNext(); ) {

        } // --- if ( aContent != null && aContent.size() > 0 ) {

        return oAA_txtval_techgroup_2;
    }

    private Element oCreateMasteredVariants(WeraProduct oProduct) {

        // --- Variantenknoten
        m_oVariants = new Element("variations");
        Element oMasteredProducts = new Element("mastered-products");
        m_oVariants.addContent(oMasteredProducts);
        Collection articles = null;
        try {
            articles = ((WeraProduct) oProduct).getVarianten();
        } catch (JaloInvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        // --- Schleife über alle Artikel
        m_article = null;
        String strCode = "";
        for (Iterator it2 = articles.iterator(); it2.hasNext();) {

            // --- Hole den aktuellen Artikel
            m_article = (WeraVariante) it2.next();

            // --- Hole CODE-NR
            if (m_wm.getAttribute(m_article, "lagerNr") == null) {
                m_wm.setAttribute(m_article, "lagerNr", "05");
            }
            if (m_wm.getAttribute(m_article, "variantenNr") == null) {
                m_wm.setAttribute(m_article, "variantenNr", "001");
            }
            if (m_article instanceof WeraProductSet) {
                strCode = (String) m_wm.getAttribute(m_article, "artnr");
            } else {
                strCode = (String) m_wm.getAttribute(m_article, "code");
            }
            String strWeraCode = m_wm.getAttribute(m_article, "lagerNr")
                    + strCode + m_wm.getAttribute(m_article, "variantenNr");

            // --- Nur bei 334 
            if (strWeraCode.equals("05018274001") && oProduct.getCode().equals("932 A")) {
                continue;
            }
            if (strWeraCode.equals("05073205001") && oProduct.getCode().equals("2095 S/2096 S/2170 S/2270 S")) {
                continue;
            }

            // --- mastered-product
            Element oMasteredProduct = new Element("mastered-product");
            oMasteredProducts.addContent(oMasteredProduct);
            oMasteredProduct.setAttribute("sku", C_NEXMART_SKU + strWeraCode);
            oMasteredProduct.setAttribute("default", "0");

            // --- Variante als Produkt-TAG
            Element m_oXmlVariant = oCreateVariantElement((WeraVariante) m_article);
        }

        return m_oVariants;
    }

    private Element _AddValueTag(Element customAttribute, String string) {
        // TODO Auto-generated method stub
        Element oValue = new Element("value");
        oValue.addContent(string);
        customAttribute.addContent(oValue);

        return oValue;
    }

    private Element _CreateCustomAttribute(String strName, String strLanguage, String strDatenTyp) {
        // TODO Auto-generated method stub
        Element oCustomAttribute = new Element("custom-attribute");

        if (strName != null) {
            oCustomAttribute.setAttribute("name", strName);
        }
        if (strLanguage != null) {
            oCustomAttribute.setAttribute(C_XMLEXT_XMLLANG, strLanguage);
        }
        if (strDatenTyp != null) {
            oCustomAttribute.setAttribute(C_XMLEXT_DTDT, strDatenTyp);
        }

        return oCustomAttribute;
    }

    private Element oInitVariationAttributes(boolean bIsMasterProduct, Product oBaseProduct, Product oProduct, Element oVariationAttributes, Element oCustomAttributes) {

        // --- Initialize
        Element oAA_txtval_techgroup_3 = _CreateCustomAttribute("AA_txtval_techgroup_3", "de-DE", "string");
        ClassificationAttribute oClassificationAttribute = null;
        Category categoryByCa = null;
        String strName = "";
        String strCaName = "";
        String strEinheit = "";
        Element oCustomAttribute = null;
        int iOrder = 1;

        try {
            // --- Nur 1x Initialisieren
            if (bIsMasterProduct) {
                LOG.info (oProduct.getCode() + "<=MASTER=>" + m_attributesArticle.size());
            } else {
                LOG.info (oProduct.getCode() + "<=VARIANTE=>" + +m_attributesArticle.size());
            }
            if (m_attributesArticle.size() == 0) {

                // --- Initialize
                m_attributesArticle.clear();
                m_hashCategory.clear();

                // ---- Initialize Sichtbarkeit (Attribute)
//                EnumerationValue ev = null;
                EnumerationManager em = m_jaloSession.getEnumerationManager();
                EnumerationType et = null;
                et = em.getEnumerationType("ClassificationAttributeVisibilityEnum");
                EnumerationValue evVISIBLE = em.getEnumerationValue(et, "VISIBLE");
                EnumerationValue evVISIBLE_IN_BASE = em.getEnumerationValue(et, "VISIBLE_IN_BASE");
                EnumerationValue evVISIBLE_IN_VARIANT = em.getEnumerationValue(et, "VISIBLE_IN_VARIANT");
                //ArrayList attributesProduct = new ArrayList();

                // --- Hole alle ClassAttributeAssignment von Produkt / Variante oder Set
                List<ClassAttributeAssignment> classattributeassignments = m_weraclassificationhelper.getAllClassAttributeAssignmentByProduct(oProduct);
                /*				
                 // --- Initialisieren der Merkmale
                 ClassificationClass categoryPClass = null;
                 ClassificationClass categoryWera = null;
                 Collection  attributes = new ArrayList();
                 Collection categories = m_wm.getCategoriesByProduct ( (WeraProduct) oBaseProduct, m_strLanguage );
                 //Collection categories = WeraManager.getInstance().getCategoriesByProduct (weraProduct );
                 ClassificationAttribute oClassificationAttributes = null;
                 if ( categories != null && categories.size() > 0 ) {
                 // --- Schleife über alle Kategorien
                 ClassificationClass oCategory = null;
                 for (Iterator it1 = categories.iterator(); it1.hasNext();) {
                 // --- Hole Category
                 oCategory = (ClassificationClass) it1.next();
						
                 // --- Hole PCLass-Kategory
                 if ( oCategory.getCode().substring(0,2).equals("AA" ) || oCategory.getCode().substring(0,1).equals("_" )) {
                 //strPClass = oCategory.getCode();
                 categoryPClass = oCategory;
                 }
                 else {
                 categoryWera = oCategory;
                 }
						
                 // --- Initialisiere alle Attribute
                 //System.out.println("++oCategory.getCode()="+oCategory.getCode());
                 Collection colAttr = m_wm.getClassificationAttributes ( oCategory ); 
                 if ( colAttr != null ) {
                 attributes.addAll( colAttr );
                 //System.out.println("++attributes added="+colAttr.size());
                 for ( Iterator it2 = colAttr.iterator(); it2.hasNext(); ) {
                 // --- Hole Category
                 oClassificationAttributes = (ClassificationAttribute) it2.next();
                 m_hashCategory.put(oClassificationAttributes.getCode(),oCategory);
                 }
                 }
                 //else
                 //System.out.println("++attributes not added="+colAttr);
						
                 } // --- for (Iterator it1 = categories.iterator(); it1.hasNext();) 
                 }
                 */

                // --- Schleife über alle Attribute
                m_ouputcontrols = (Collection) m_wm.getAttribute(oBaseProduct, "outputcontrols");
                Outputcontrol outputcontrol = null;
                EnumerationValue evVisibility = null;
                //for (Iterator it1 = attributes.iterator(); it1.hasNext();) {
                for (Iterator it1 = classattributeassignments.iterator(); it1.hasNext();) {
                    // --- Hole ProfiClassAttribute
                    ClassAttributeAssignment oClassAttributeAssignment = (ClassAttributeAssignment) it1.next();
                    oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();

                    // --- Hole die Klassifizierende Klasse
                    ClassificationClass oClassificationClass = oClassAttributeAssignment.getClassificationClass();
                    m_hashCategory.put(oClassificationAttribute.getCode(), oClassificationClass);

                    // ---  Hole Ausgabesteuerung
                    outputcontrol = (Outputcontrol) m_wm.checkContaining(m_ouputcontrols, "code", oClassificationAttribute.getCode());
                    if (outputcontrol != null) {
                        evVisibility = (EnumerationValue) m_wm.getAttribute(outputcontrol, "visibility");
                    } else {
                        evVisibility = evVISIBLE_IN_BASE;
                    }

                    // --- Sortieren der Merkmale
                    // if (  evVisibility==evVISIBLE_IN_VARIANT ||  evVisibility==evVISIBLE ) {
                    if (evVisibility.equals(evVISIBLE_IN_VARIANT) || evVisibility.equals(evVISIBLE)) {
                        if (!m_attributesArticle.contains(oClassificationAttribute)) {
                            m_attributesArticle.add(oClassAttributeAssignment);
                            //System.out.println("++added="+oClassificationAttribute.getCode());
                        }
                        //else
                        //System.out.println("++not added 1="+oClassificationAttribute.getCode());
                    }
                    //else
                    //System.out.println("++not added 2="+oClassificationAttribute.getCode());

                } // --- for (Iterator it1 = attributes.iterator(); it1.hasNext();) {

                // --- Sortiere die Atrribute nach Order
                OrderComparatorExportCAC orderComparatorExportCA = new OrderComparatorExportCAC();
                orderComparatorExportCA.init(m_ouputcontrols);
                if (m_attributesArticle != null && m_attributesArticle.size() > 0) {
                    Collections.sort((List) m_attributesArticle, orderComparatorExportCA);
                }

            }

            // --- Schleife über alle Attribute für Artikel
            _AddValueTag(oAA_txtval_techgroup_3, "Artikel Merkmale");
            for (Iterator it2 = m_attributesArticle.iterator(); it2.hasNext();) {
                // --- Hole ProfiClassAttribute
                //oClassificationAttribute = ( ClassificationAttribute) it2.next();
                ClassAttributeAssignment oClassAttributeAssignment = (ClassAttributeAssignment) it2.next();
                oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();
                categoryByCa = (Category) m_hashCategory.get(oClassificationAttribute.getAttribute("code"));

                // --- Einheit
                strEinheit = _getUnit4CA(oClassAttributeAssignment, categoryByCa);

                // --- Generate CA-Name
                strCaName = _NormalizeCAName(oClassificationAttribute, strEinheit);
                strName = "VA_standard_" + "00000" + +iOrder + "_" + strCaName;
                iOrder++;

                // --- Merkmal variation-attribute
                if (bIsMasterProduct) {
                    Element oVariationAttribute = new Element("variation-attribute");
                    oVariationAttributes.addContent(oVariationAttribute);
                    oVariationAttribute.setAttribute("name", strName);
                }

                // --- Merkmalbeschreibung
                if (bIsMasterProduct) {
                    oCustomAttribute = _CreateCustomAttribute(strName + "_displayname", "de-DE", "string");
                    oCustomAttributes.addContent(oCustomAttribute);
                    oCustomAttribute.addContent(oClassificationAttribute.getName() + " " + strEinheit);
                }

                // --- Values
                String strValue = "";
                if (!bIsMasterProduct) {
                    strValue = strGetCAValue(oProduct, oClassAttributeAssignment);
                    oCustomAttribute = _CreateCustomAttribute(strName, "de-DE", "string");
                    oCustomAttributes.addContent(oCustomAttribute);
                    oCustomAttribute.addContent(strValue);
                }

                // --- Merkmalbeschreibung Detail
                _AddValueTag(oAA_txtval_techgroup_3, oClassificationAttribute.getName());
                _AddValueTag(oAA_txtval_techgroup_3, strValue);
                _AddValueTag(oAA_txtval_techgroup_3, strEinheit);
            }

            // --- Merkmalbeschreibung Detail
            //oCustomAttributes.addContent(oAA_txtval_techgroup_3);
        } catch (JaloInvalidParameterException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (JaloSecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return oAA_txtval_techgroup_3;
    }

    private String _NormalizeCAName(ClassificationAttribute classificationAttribute, String strEinheit) {
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

    //private String strGetCAValue(Product product, ClassificationAttribute classificationAttribute) {
    public String strGetCAValue(Product product, ClassAttributeAssignment classattributeassignment) {
        // TODO Auto-generated method stub

        // --- Initialize
        String strResult = "";
        int iShowCntAttribute = 1;

        // --- Beim Produkt einen Dummy-Eintrag zeigen
        if (product instanceof WeraVariante) {
            iShowCntAttribute = 9999;
        }

        HashMap<String, ClassificationAttributeValue> hFeatureValues = m_weraclassificationhelper.getPickedClassificationAttributeValuesByProduct(product, classattributeassignment);
        /*
         // --- Hole alle Produktfeatures zum aktuellen Merkmal
         Collection productfeatures = null;
         try {
         productfeatures = ProductFeatureHelper.getProductFeatures(product,classificationAttribute);
         } catch (JaloInvalidParameterException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
         } catch (JaloSecurityException e1) {
         // TODO Auto-generated catch block
         e1.printStackTrace();
         }
         */
        // --- Schleife über alle Produktfeatures
        //ProductFeature oProductFeature = null;
        for (Iterator it1 = hFeatureValues.values().iterator(); it1.hasNext();) {
            // --- Hole ProductFeature
            //oProductFeature = (ProductFeature) it1.next();
            ClassificationAttributeValue oClassificationAttributeValue = (ClassificationAttributeValue) it1.next();
            if (oClassificationAttributeValue == null) {
                continue;
            }

            // --- Unterscheidung nach Datentyp
            if (classattributeassignment.getAttributeType().getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.STRING)) {
                //if (oProductFeature.getType() == 4) {

                // --- Schleife über alle Merkmale
                Item item = null;
                //Collection col = oProductFeature.getVarianten();
                //for (Iterator it2 = col.iterator(); it2.hasNext();) {
                // --- Hole Element
                //item = (Item) it2.next();

                // --- Anzahl der Werte eingrenzen
                iShowCntAttribute--;
                if (iShowCntAttribute < 0) {
                    break;
                }

                // --- Initialisiere Merkmale
                try {
                    strResult = initProductFeature(classattributeassignment, oClassificationAttributeValue.getAttribute("code").toString(), (String) oClassificationAttributeValue.getAttribute("name"));
                    //strResult = initProductFeature( classificationAttribute, item.getAttribute("code").toString(), (String)item.getAttribute("name") );
                } catch (Exception e) {
                    e.printStackTrace();
                }

                //} // --- for (Iterator it2 = col.iterator(); it2.hasNext();) {
            } else {

                // --- Initialisiere Merkmale
                try {
                    //strResult = initProductFeature( classificationAttribute, oProductFeature.getQualifier(), (String)oProductFeature.getAttribute("stringValue") );
                    strResult = initProductFeature(classattributeassignment, oClassificationAttributeValue.getAttribute("code").toString(), (String) oClassificationAttributeValue.getAttribute("name"));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

        } // --- for (Iterator it1 = productfeatures.iterator(); it1.hasNext();) {

        return strResult;
    }

    private String initProductFeature(ClassAttributeAssignment classattributeassignment, String strWertId, String strWert) throws JaloInvalidParameterException, JaloSecurityException {
//private String initProductFeature( ClassificationAttribute classificationAttribute, String strWertId, String strWert ) throws JaloInvalidParameterException, JaloSecurityException {
        // TODO Auto-generated method stub

        // --- Initialize
        ClassificationAttribute classificationAttribute = classattributeassignment.getClassificationAttribute();
        Outputcontrol outputcontrol = _getOutputcontrol(classificationAttribute);
        if (strWert == null) {
            strWert = "n/a";
        }
        String strBez = classificationAttribute.getName(m_jaloSession.getSessionContext());
        if (strBez == null) {
            strBez = "n/a";
        }

        // --- Datentyp
        String strType = "alphanumerisch";
        EnumerationValue ev = (EnumerationValue) classattributeassignment.getAttributeType();
        if (ev != null) {
            if (ev.getCode().equals("string")) {
                strType = "alphanumerisch";
            }
            if (ev.getCode().equals("number")) {
                strType = "numerisch";
            }
            if (ev.getCode().equals("boolean")) {
                strType = "logisch";
                if (strWert.equals("true")) {
                    strWert = "J";
                } else {
                    strWert = "N";
                }
            }
        }

        // --- Korrigiere WertID (HIDDEN)
        if (strWertId.length() > 0) {
            if (strWertId.substring(0, 2).equals("H_")) {
                strWertId = strWertId.substring(2);
            }
            if (strWertId.substring(1).contains("_")) {
                strWertId = strWertId.substring(0, strWertId.substring(1).indexOf('_') + 1);
            }
        }

        // --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
        if (!m_strLanguage.equals("en") && !m_strLanguage.equals("us-en")) {
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
        if (strWert.contains("+") || strWert.contains("-")) {
            strWert = strWert.replace(" ", "");
        }

        return strWert;
    }

    public String _getUnit4CA(ClassAttributeAssignment classattributeassignment, Category category) {
        //private String _getUnit4CA( ClassificationAttribute classificationAttribute, Category category ) {

        // --- Initialize
        ClassificationAttribute classificationAttribute = classattributeassignment.getClassificationAttribute();
        Outputcontrol outputcontrol = _getOutputcontrol(classificationAttribute);

        // --- Hole Einheit
        String stringUnit = "";
        if (outputcontrol != null) {
            stringUnit = (String) outputcontrol.getLocalizedProperty("unitca");
            if (stringUnit != null && stringUnit.equals("[ohne]")) {
                stringUnit = " ";
            }
        }
        ClassificationAttributeUnit oUnit = null;
        oUnit = classattributeassignment.getUnit();
        //Unit oUnit = null;
        //oUnit = classificationAttribute.getUnitForCategory(m_jaloSession.getSessionContext(), category );
        if (stringUnit == null || stringUnit.length() == 0) {
            if (oUnit != null) {
                stringUnit = oUnit.getUnitType();
            } else {
                stringUnit = "";
            }
        }

        return stringUnit;
    }

    private Outputcontrol _getOutputcontrol(ClassificationAttribute classificationAttribute) {
        // --- Initialize
        Outputcontrol outputcontrol = null;

        if (m_ouputcontrols != null) {
            outputcontrol = (Outputcontrol) m_wm.checkContaining(m_ouputcontrols, "code", classificationAttribute.getCode());
        }

        return outputcontrol;
    }

    private String _NormalizeImageName(String strImageName) {
        if (strImageName != null) {
            strImageName = strImageName.replace("\\", "/");
            Pattern p = Pattern.compile("/");
            String[] aRealName = p.split(strImageName);
            if (aRealName.length > 1) {
                strImageName = aRealName[aRealName.length - 1];
            }

            // --- Nur Kleinbuchstaben
            strImageName = strImageName.toLowerCase();
        }

        return strImageName;
    }

    private String _getRealImageName(WeraMedia weramedia) {

        // --- Image für Liste der benötigten Grafiken merken
        String strImageName = _NormalizeImageName(weramedia.getRealFileName());
        if (!m_hashImages.containsKey(strImageName)) {
            m_hashImages.put(strImageName, strImageName);
        }

        return strImageName;
    }

    // -----------------------------------------------------------------------------------------------
    // --- EXPORT der Kategorien ---------------------------------------------------------------------
    // -----------------------------------------------------------------------------------------------
    // --- Ausgabe einer XML-Datei aller Kategorien
    public String strJspEntryExportCategoryXML(String strCatalog, String strCatalogVersion, boolean bTranferByFTP) {

        // --- Initialize
        m_strCatalog = strCatalog;

        // --- wera catalog und catalogversion holen, oder ggf. neu anlegen
        //CatalogVersion weraCatalogVersion = m_wm.getCatalogVersion( CATALOG_HAENDLER_PLATTFORM, strCatalogVersion );
        CatalogVersion weraCatalogVersion = m_wm.getCatalogVersion(strCatalog, strCatalogVersion);

        return _strExportCategoryXML(weraCatalogVersion, bTranferByFTP);
    }

    public String strExportCategoryXML(CatalogVersion weraCatalogVersion) {

        return _strExportCategoryXML(weraCatalogVersion, true);
    }

    private String _strExportCategoryXML(CatalogVersion weraCatalogVersion, boolean bTranferByFTP) {

        // --- Initialize
        int iProductCounter = 0;
        int iArticleCounter = 0;
        String strResult = "";
        String strOutput = "";
        String strFN = "strExportCategoryXML ==> ";
        Collection productsSorted = null;
        Collection articles = null;
        String strKatalogVersion = (String) m_wm.getAttribute(weraCatalogVersion, "version");

        LOG.info("_strExportCategoryXML Katalog=" + m_strCatalog + "/" + strKatalogVersion);

        // --- Hole Sprache
        m_strLanguage = "de";

        // --- Setze Sprache, und Defaultsprache=de
        initLanguage(m_strLanguage);

        // --- Setze DTD-Info
        //initDTD ( "MediaXML", "MEDIANDO.dtd" );
        // --- Initialisieren der XML-Struktur
        if (bInitNexMart(strKatalogVersion, bTranferByFTP)) {

            // --- Ausgabedatei
            m_strOutputFile += "_" + C_NEXMART_CATEGORIES + ".xml";

            // --- Catalog
            m_oCatalog = new Element("catalog");
            m_rootElement.addContent(m_oCatalog);

            // --- Default Categorien
            m_oCatalog.addContent(oCreateRootCategoryElement());
            m_oCatalog.addContent(oCreateVariantCategoryElement());
            m_oCatalog.addContent(oCreateDummyCategoryElement());

            // --- Hole alle Kateogieren
            Collection oColCategoryParent = null;
            Category oCategoryParent = null;
            Category oCategory = null;
            Category oCategoryWM = null;
            ArrayList categories = null;
            Element oXmlCategory = null;
            try {
                m_wm.m_bCheckForActivation = false;
                categories = (ArrayList) m_wm.getCategories(m_strCatalog, strKatalogVersion, "");
            } catch (JaloInvalidParameterException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (JaloSecurityException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            for (Iterator it1 = categories.iterator(); it1.hasNext();) {
                // -- Get Category
                oCategory = (Category) it1.next();

                // --- Kein PClass-Kategorie
                if (oCategory.getCode().substring(0, 2).equals("AA") || oCategory.getCode().substring(0, 1).equals("_")) {
                    continue;
                }

                // --- Auslassen
                if (oCategory.getCode().equals("VERKAUFSHILFE") || oCategory.getCode().equals("PREISLISTENARTIKEL")) //if ( oCategory.getCode().equals("SBKATALOG") || oCategory.getCode().equals("VERKAUFSHILFE")  || oCategory.getCode().equals("PREISLISTENARTIKEL") )
                {
                    continue;
                }

                // --- Hole Parent-Category
                if (!oCategory.getCode().equals("root")) {
                    oColCategoryParent = (Collection) m_wm.getAttribute(oCategory, "supercategories");
                    if (oColCategoryParent != null && oColCategoryParent.size() > 0) {
                        oCategoryParent = (Category) oColCategoryParent.iterator().next();
                        // System.out.println("Category=" + oCategory.getCode() + ", Parent-Category=" + oCategoryParent.getCode());

                        // --- Merke alle Produkte der Kategorie
                        Collection products = oCategory.getProducts();
                        m_allProductsFromCatalog.addAll(products);

                        // --- Hole Order für den Index
                        Integer oOrder = (Integer) m_wm.getAttribute(oCategory, "order");
                        String strCategoryOrder = _GetOrderString(oOrder);

                        // --- Hole Category aus Weramaster für Category-Texte, etc....
                        oCategoryWM = m_weraMasterCatalogVersion.getCategory(oCategory.getCode());
						if ( oCategoryWM == null ) {
							// --- WeraMaster - Kategorie existiert nicht, verwendet die aktuelle
							oCategoryWM = oCategory;
						}
                        oXmlCategory = oCreateProductCategoryElement(oCategoryWM, oCategoryParent, strCategoryOrder);

                        // --- Kategory übernehmen
                        if (oXmlCategory != null) {
                            m_oCatalog.addContent(oXmlCategory);
                        } else {
                           LOG.warn ( "XML-Fehler=" + oCategory.getName() );
                        }
                    }
                }

            } // --- for ( Iterator it1 = categories.iterator(); it1.hasNext();)
        }

        // --- Schreiben der XML-Datei
        writeDocument(m_strOutputPath + m_strDatenTransferPath + "/", m_strOutputFile);

        // --- Überarbeiten der XML-Datei
        KorrekturXMLFile(m_strOutputPath + m_strDatenTransferPath + "/" + m_strOutputFile);

        // --- Aufräumen
        cleanUp();

        return strResult;

    }

    private Element oCreateRootCategoryElement() {

        Element oXMLCategory = new Element("category");
        oXMLCategory.setAttribute("name", C_NEXMART_SELLER);
        oXMLCategory.addContent(oGetNewElement("name", C_NEXMART_SELLER));

        // --- Optionen I
        oXMLCategory.addContent(oGetNewElement("template", ""));
        oXMLCategory.addContent(oGetNewElement("online", "1"));
        oXMLCategory.addContent(new Element("sequence"));

        // --- PARENT
        Element oXmlParent = new Element("parent");
        oXmlParent.setAttribute("name", "nexMart");
        oXMLCategory.addContent(oXmlParent);

        // --- Optionen II
        oXMLCategory.addContent(oGetNewElement("hotdeals-template", ""));
        oXMLCategory.addContent(oGetNewElement("description", m_strDatum.substring(0, 8)).setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        //oXMLCategory.addContent(oGetNewElement( "description", m_strDatum.substring(0,10)).setAttribute(C_XMLEXT_XMLLANG,"en-DE") );
        //oXMLCategory.addContent(oGetNewElement( "description", m_strDatum.substring(0,10)).setAttribute(C_XMLEXT_XMLLANG,"es-DE") );
        oXMLCategory.addContent(oGetNewElement("display-name", C_NEXMART_DISPLAYSELLER).setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        //oXMLCategory.addContent(oGetNewElement( "display-name", C_NEXMART_DISPLAYSELLER).setAttribute(C_XMLEXT_XMLLANG,"en-DE") );
        //oXMLCategory.addContent(oGetNewElement( "display-name", C_NEXMART_DISPLAYSELLER).setAttribute(C_XMLEXT_XMLLANG,"es-DE") );

        return oXMLCategory;
    }

    private Element oCreateVariantCategoryElement() {

        Element oXMLCategory = new Element("category");
        oXMLCategory.setAttribute("name", C_NEXMART_SELLER + "ProductVariations");
        oXMLCategory.addContent(oGetNewElement("name", C_NEXMART_SELLER + "ProductVariations"));

        // --- Optionen I
        oXMLCategory.addContent(oGetNewElement("template", ""));
        oXMLCategory.addContent(oGetNewElement("online", "0"));
        oXMLCategory.addContent(new Element("sequence"));

        // --- PARENT
        Element oXmlParent = new Element("parent");
        oXmlParent.setAttribute("name", C_NEXMART_SELLER);
        oXMLCategory.addContent(oXmlParent);

        // --- Optionen II
        oXMLCategory.addContent(oGetNewElement("hotdeals-template", ""));
        oXMLCategory.addContent(oGetNewElement("description", "-").setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        //oXMLCategory.addContent(oGetNewElement( "description", "-").setAttribute(C_XMLEXT_XMLLANG,"en-DE") );
        //oXMLCategory.addContent(oGetNewElement( "description", "-").setAttribute(C_XMLEXT_XMLLANG,"es-DE") );
        oXMLCategory.addContent(oGetNewElement("display-name", "ProductVariations").setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        //oXMLCategory.addContent(oGetNewElement( "display-name", "ProductVariations").setAttribute(C_XMLEXT_XMLLANG,"en-DE") );
        //oXMLCategory.addContent(oGetNewElement( "display-name", "ProductVariations").setAttribute(C_XMLEXT_XMLLANG,"es-DE") );

        return oXMLCategory;
    }

    private Element oCreateDummyCategoryElement() {

        Element oXMLCategory = new Element("category");
        oXMLCategory.setAttribute("name", C_NEXMART_SELLER + "dummy");
        oXMLCategory.addContent(oGetNewElement("name", C_NEXMART_SELLER + "dummy"));

        // --- Optionen I
        oXMLCategory.addContent(oGetNewElement("template", ""));
        oXMLCategory.addContent(oGetNewElement("online", "0"));
        oXMLCategory.addContent(new Element("sequence"));

        // --- PARENT
        Element oXmlParent = new Element("parent");
        oXmlParent.setAttribute("name", C_NEXMART_SELLER);
        oXMLCategory.addContent(oXmlParent);

        // --- Optionen II
        oXMLCategory.addContent(oGetNewElement("hotdeals-template", ""));
        oXMLCategory.addContent(oGetNewElement("description", "-").setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        //oXMLCategory.addContent(oGetNewElement( "description", "-").setAttribute(C_XMLEXT_XMLLANG,"en-DE") );
        //oXMLCategory.addContent(oGetNewElement( "description", "-").setAttribute(C_XMLEXT_XMLLANG,"es-DE") );
        oXMLCategory.addContent(oGetNewElement("display-name", "dummy").setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        //oXMLCategory.addContent(oGetNewElement( "display-name", "dummy").setAttribute(C_XMLEXT_XMLLANG,"en-DE") );
        //oXMLCategory.addContent(oGetNewElement( "display-name", "dummy").setAttribute(C_XMLEXT_XMLLANG,"es-DE") );

        return oXMLCategory;
    }

    private Element oCreateProductCategoryElement(Category oCategory, Category oParentCategory, String strOrderFromOriginalCategory) {

        // --- Initialize
        Integer oOrder = null;
        String strNewOrder = "";
        String strNewOrderP = "";
        String strNormalizedCategoryId = "";
        String strNormalizedCategoryIdParent = "";

        // --- Hole PRIO
        if (strOrderFromOriginalCategory != null) {
            strNewOrder = strOrderFromOriginalCategory;
        } else {
            oOrder = (Integer) m_wm.getAttribute(oCategory, "order");
            strNewOrder = _GetOrderString(oOrder);
			strOrderFromOriginalCategory = strNewOrder;
        }
        LOG.info("oCategory=" + oCategory + "/ oParentCategory=" + oParentCategory + "/strOrderFromOriginalCategory=" + strOrderFromOriginalCategory);
        LOG.info("oCreateProductCategoryElement=" + oCategory.getCode() + "/" + strOrderFromOriginalCategory);
		
        if (oParentCategory != null && !oParentCategory.getCode().equals("root")) {
            oOrder = (Integer) m_wm.getAttribute(oParentCategory, "order");
            strNewOrderP = _GetOrderString(oOrder);
            strNormalizedCategoryIdParent = WeraProduct.s_normalizeFilenameForImageLookup(oParentCategory.getCode());
        } else {
            strNewOrderP = "";
            strNormalizedCategoryIdParent = "";
        }

        // --- Kategorie-ID
        strNormalizedCategoryId = WeraProduct.s_normalizeFilenameForImageLookup(oCategory.getCode());

        // --- Neue Category
        Element oXMLCategory = new Element("category");
        //oXMLCategory.setAttribute("name", C_NEXMART_SELLER + strNewOrder);
        //oXMLCategory.addContent(oGetNewElement("name", C_NEXMART_SELLER + strNormalizedCategoryId) ));
        oXMLCategory.setAttribute("name", C_NEXMART_SELLER + strNormalizedCategoryId);
        oXMLCategory.addContent(oGetNewElement("name", C_NEXMART_SELLER + strNormalizedCategoryId));

        // --- Optionen I
        oXMLCategory.addContent(oGetNewElement("online", "1"));
        oXMLCategory.addContent(oGetNewElement("sequence", strNewOrder));
        oXMLCategory.addContent(oGetNewElement("template", ""));

        // --- PARENT
        Element oXmlParent = new Element("parent");
        //oXmlParent.setAttribute("name", C_NEXMART_SELLER + strNewOrderP);
        oXmlParent.setAttribute("name", C_NEXMART_SELLER + strNormalizedCategoryIdParent);
        oXMLCategory.addContent(oXmlParent);

        // --- GROUP_SUBTITEL an GROUP_NAME anhängen
        Boolean bConcat_description = (Boolean) m_wm.getAttribute(oCategory, "concat_description");
        if (bConcat_description == null) {
            bConcat_description = new Boolean(false);
        }
        String strBeschDesc = oCategory.getName();
        if (strBeschDesc == null) {
            strBeschDesc = "";
        }
        if (false && bConcat_description.booleanValue()) {
            String sUntertitel = (String) m_wm.getAttribute(oCategory, "untertitel");
            if (sUntertitel == null) {
                sUntertitel = "";
            }
            strBeschDesc = strBeschDesc + " - " + sUntertitel;
        }

        // --- Optionen II
        oXMLCategory.addContent(oGetNewElement("hotdeals-template", ""));
        initLanguage("de");
        oXMLCategory.addContent(oGetNewElement("display-name", strBeschDesc).setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        //initLanguage ( "en" );
        //oXMLCategory.addContent(oGetNewElement( "display-name", oCategory.getName() ).setAttribute(C_XMLEXT_XMLLANG,"de-DE") );
        //initLanguage ( "es" );
        //oXMLCategory.addContent(oGetNewElement( "display-name", oCategory.getName() ).setAttribute(C_XMLEXT_XMLLANG,"es-DE") );
        oXMLCategory.addContent(oGetNewElement("description", "").setAttribute(C_XMLEXT_XMLLANG, "de-DE"));
        //oXMLCategory.addContent(oGetNewElement( "description", "").setAttribute(C_XMLEXT_XMLLANG,"en-DE") );
        //oXMLCategory.addContent(oGetNewElement( "description", "").setAttribute(C_XMLEXT_XMLLANG,"es-DE") );

        // --- Custom-Attributes
        Element Custom_Attributes = new Element("custom-attributes");
        oXMLCategory.addContent(Custom_Attributes);
        initLanguage("de");
        Element Custom_Attribute = oGetNewElement("custom-attribute", strBeschDesc).setAttribute(C_XMLEXT_XMLLANG, "de-DE");
        Custom_Attribute.setAttribute("name", "AA_standard_headline_category");
        Custom_Attributes.addContent(Custom_Attribute);
        /*
         initLanguage ( "en" );
         Custom_Attribute = oGetNewElement( "custom-attribute", oCategory.getName()).setAttribute(C_XMLEXT_XMLLANG,"en-DE");
         Custom_Attribute.setAttribute( "name", "AA_standard_headline_category");
         Custom_Attributes.addContent(Custom_Attribute);
         initLanguage ( "es" );
         Custom_Attribute = oGetNewElement( "custom-attribute", oCategory.getName()).setAttribute(C_XMLEXT_XMLLANG,"es-DE");
         Custom_Attribute.setAttribute( "name", "AA_standard_headline_category");
         Custom_Attributes.addContent(Custom_Attribute);
         */

        String strUntertitel = (String) m_wm.getAttribute(oCategory, "untertitel");
        Custom_Attribute = new Element("custom-attribute");
        Custom_Attribute.setAttribute(C_XMLEXT_XMLLANG, "de-DE");
        Custom_Attribute.setAttribute("name", "AA_txtsgl_category");
        Element oValue = oGetNewElement("value", strUntertitel);
        Custom_Attribute.addContent(oValue);
        Custom_Attributes.addContent(Custom_Attribute);

        return oXMLCategory;
    }

    private String _GetOrderString(Integer oOrder) {

        // --- Hole PRIO
        if (oOrder == null) {
            oOrder = new Integer(0);
        }

        // --- Formatiere
        String strNewOrder = "00000" + oOrder.toString();
        strNewOrder = strNewOrder.substring(strNewOrder.length() - 6);

        return strNewOrder;

    }
}

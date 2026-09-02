package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.model.WeraProductSetModel;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.core.Registry;
import com.computationaldesign.wera.model.TextbausteinModel;

import de.hybris.platform.catalog.constants.GeneratedCatalogConstants;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.Keyword;
import de.hybris.platform.catalog.jalo.ProductFeature;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeUnit;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.c2l.LocalizableItem;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.media.MediaManager;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.user.Employee;
import de.hybris.platform.jalo.user.UserManager;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.WebSessionFunctions;
import de.hybris.platform.jalo.product.ProductManager;
import de.hybris.platform.core.PK;
import de.hybris.platform.jalo.type.TypeManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.xml.sax.XMLReader;

class OrderComparatorExport implements Comparator {

	public int compare(final Object o1, final Object o2) /* descending order */ {

		// --- Initialize
		Integer iValue1 = 0;
		Integer iValue2 = 0;
		final int iResult = 0;

		try {
			// --- Hole Values
			iValue1 = (Integer) ((Item) o1).getAttribute("order");
			iValue2 = (Integer) ((Item) o2).getAttribute("order");
		} catch (final JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloSecurityException e) {
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

class OrderComparatorExportCAA implements Comparator {

	public Collection m_ouputcontrols = new ArrayList();
	public WeraManager m_wm = null;

	public void init(final Collection ouputcontrols) {
		m_ouputcontrols.addAll(ouputcontrols);
		m_wm = WeraManager.getInstance();
	}

	protected Object _getOutputcontrol(final String strCode) throws JaloInvalidParameterException, JaloSecurityException {
		// --- Initialize
		Object outputcontrol = null;

		if (m_ouputcontrols != null) {
			outputcontrol = m_wm.checkContaining(m_ouputcontrols, "code", strCode);
		}

		return outputcontrol;
	}

	public int compare(final Object o1, final Object o2) /* descending order */ {

		// --- Initialize
		String strCode1 = "";
		String strCode2 = "";
		Object outputcontrol1 = null;
		Object outputcontrol2 = null;
		Integer iValue1 = 0;
		Integer iValue2 = 0;
		final int iResult = 0;

		try {
			// --- Hole Ausgabesteuerung
			strCode1 = (String) ((ClassAttributeAssignment) o1).getClassificationAttribute().getAttribute("code");
			strCode2 = (String) ((ClassAttributeAssignment) o2).getClassificationAttribute().getAttribute("code");
			outputcontrol1 = _getOutputcontrol(strCode1);
			outputcontrol2 = _getOutputcontrol(strCode2);
			//LOG.info("outputcontrol1="+outputcontrol1);
			//LOG.info("outputcontrol2="+outputcontrol2);

			// --- Hole Values
			if (outputcontrol1 != null) {
				iValue1 = (Integer) ((Item) outputcontrol1).getAttribute("order");
			} else {
				iValue1 = 1;
			}
			if (outputcontrol2 != null) {
				iValue2 = (Integer) ((Item) outputcontrol2).getAttribute("order");
			} else {
				iValue2 = 1;
			}

		} catch (final JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloSecurityException e) {
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

// Wrapperklassen f�r formatierte Ausgabe der Bildpfade
// Standardklasse ist ExportFormatter f�r den XML Export f�r die Katalogausgabe
// Sie liefert stets den Eingabestring unver�ndert zur�ck
class ExportFormatter {

	public String m_strExportPath = "";
	public String m_strLanguage = "";

	public void SetExportPath(final String strExportPath) {
		m_strExportPath = strExportPath;
	}

	public void SetLanguage(final String strLanguage) {
		m_strLanguage = strLanguage;
	}

	public String formatIconPath(final String s) {
		return s;
	}

	public String formatPicturePath(final String s) {
		return s;
	}

	public String formatSetContent(final String s) {
		return s;
	}

	public String formatCharakters(String s) {
		if (s == null) {
			s = "";
		}
		s = s.replaceAll("\u00AE", formatSetSupS() + "\u00AE" + formatSetSupE());

		return s;
	}

	public String formatDescription(String s) {

		s = formatCharakters(s);

		return s;
	}

	public String formatFileDatum(final String strLang, final String strDatum) {
		//LOG.info("ExportFormatter.formatFileDatum=>" + strLang + strDatum );
		return strLang + strDatum;
	}

	public String formatXmlFilePrefix(final String s) {
		return s;
	}

	public String formatIndesignSetSupS() {
		return "<cPosition:Superscript>";
	}

	public String formatIndesignSetSupE() {
		return "<cPosition:>";
	}

	public String formatSetSupS() {
		return ""; // ##sup##";
	}

	public String formatSetSupE() {
		return ""; //##e_sup##";
	}

	public String formatSetSubS() {
		return ""; // ##down##";
	}

	public String formatSetSubE() {
		return ""; // "##e_down##";
	}

	public String formatCode(final String strLagerNr, final String strCode, final String strVarNr) {
		//return strLagerNr + "##b##" + strCode + "##e_b##" + strVarNr;
		return strLagerNr + strCode + strVarNr;
	}

	public String formatCodeUS(final String strLagerNr, final String strCode, final String strVarNr) {
/*		
		String strOutput = "<ASCII-WIN>\r\n";
		strOutput += "<Version:4><FeatureSet:InDesign-Roman><ColorTable:=<Black:COLOR:CMYK:Process:0.000000,0.000000,0.000000,1.000000><Black \\(CMYK\\):COLOR:CMYK:Process:0.000000,0.000000,0.000000,1.000000>>\r\n";
		strOutput += "<DefineCharStyle:wera\\_tabelle=<Nextstyle:wera\\_tabelle><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT>>\r\n";
		strOutput += "<DefineParaStyle:links ohne Einzug=<Nextstyle:links ohne Einzug><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:Black \\(CMYK\\)><pRuleBelowStroke:0.000000><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:Black \\(CMYK\\)>>\r\n";
		strOutput += "<DefineParaStyle:rechts Einzug 2.1mm=<Nextstyle:rechts Einzug 2.1mm><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><pHyphenationLadderLimit:0><pRightIndent:5.952756><pMinCharBeforeHyphen:3><pHyphenateCapitals:0><pShortestWordHyphenated:6><pHyphenationZone:0.000000><cFont:Helvetica Neue LT><pDesiredWordSpace:1.100000><pMaxWordSpace:2.500000><pMinWordSpace:0.850000><pMaxLetterspace:4.000000><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:wera\\_icons\\_bg><pRuleBelowStroke:0.198425><pRuleBelowOffset:2.834646><pRuleBelowStrokeType:CannedDash3x2><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:white \\(CMYK\\)><pTextAlignment:Right>>\r\n";
		strOutput += "<DefineParaStyle:center\\_wera=<Nextstyle:center\\_wera><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:Black \\(CMYK\\)><pRuleBelowStroke:5.669291><pRuleBelowOffset:0.850394><pRuleBelowStrokeType:Dashed><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:Black \\(CMYK\\)><pTextAlignment:Center>>\r\n";
		strOutput += "<ParaStyle:links ohne Einzug><cSize:6.000000>" + strLagerNr + "<cSize:><cTypeface:Bold>" + strCode
				+ "<cTypeface:><cSize:6.000000>" + strVarNr + "<cSize:>";
*/
		return strLagerNr + strCode + strVarNr;
	}

}

public class WeraProduktExport {

	/**
	 * Used logger instance.
	 */

	HashMap<String,Textbaustein> m_hashTextbausteine	= new HashMap();

	protected static final Log LOG = LogFactory.getLog(WeraProduktExport.class);

	protected static final int m_iDebugLevel = 1;

	protected boolean m_bExportDiscontinuedItemAsFootnote = false;

	protected boolean m_exportHyperLinks	= true;

	protected XMLReader parser;
	protected int		m_maxAttributeCols = 8; // default handling je Template
	protected boolean   m_isPriceListExport	= false;
	protected Boolean	m_bSB_faehig = false;

	protected Writer out;
	protected final FileWriter m_oFileReport = null;
	Collection m_xmlFileList = new ArrayList();

	protected final StringBuffer m_strText = null;

	protected final boolean m_bReadText = false;

	protected String m_strDataPath;
	protected String m_strProduktID = "";
	protected String m_strXmlFile = null;

	// --- aktuelle sprache
	protected String m_strLanguage;

	// --- erstele CodeNr (Produktliste oder Satz)
	protected String m_firstcodeNr = "";

	// --- alle sprachen
	protected Collection<String> m_coAdditionallLanguages = new ArrayList();

	protected final Collection m_colCategories = new ArrayList();
	protected String m_strCatalog;
	protected String m_strCatalogversion;
	static CatalogVersion m_weraCatalogVersion;
	static CatalogVersion m_exportCatalogVersion;
	CatalogVersion m_weraMasterCatalogVersion = null;
	WeraClassificationHelper m_weraclassificationhelper = null;

	static JaloSession m_jaloSession;
	public Category m_oCategoryWera = null;

	protected int m_iCntTipps = 0;
	protected int m_iProduktID = 0;
	protected int m_iCountProduct = 0;
	protected int m_iCountArticels = 0;
	protected int m_iOffset = 0;
	protected int m_iOffsetArtikel = 0;
	protected int m_iCntText = 0; // --- Text
	protected int m_iCntFussnoten = 0; // --- Fussnoten
	protected int m_iCntSchlagworte = 0; // --- Schlagworte
	protected boolean m_bLeftElement = false; // --- Flag f�r ein linkes Tabellenelement

	protected final int m_iCountProductGes = 0;
	protected final HashMap m_CntSchlagworteGes = new HashMap();
	protected final HashMap m_CountProductGes = new HashMap();

	public WeraManager m_wm = WeraManager.getInstance();
	public Collection m_ouputcontrols = null;
	HashMap m_hashAttributeHeaderList = null;
	protected ArrayList m_aLogList = null;
	protected boolean m_bTextwechsel = false;
	protected boolean m_bMultilayer = true;
	protected boolean m_bAktionsprospekt = true;

	//protected Collection __colAllProductsByCatalogVersion = null;
	protected Set m_oProductsToExport = null;
	protected boolean m_bDoLogin = true;
	public HashSet m_hExludeList = new HashSet();
	protected WeraProduct m_weraProductAkt = null;
	Footnote m_oFootnoteAuslaufartikelTmp = null;
	String m_strCurrentTemplateName = "";

	protected Collection<Tipp> m_colTippByProducts	=	null;

	protected Collection<Textbaustein> m_colTextbausteinBullet 			=	null;
	protected Collection<Textbaustein> m_colTextbausteinMarketingtext	=	null;

	protected Collection<String> m_colExportedTextbausteinBullet			= null;
	protected Collection<String> m_colExportedTipps							= null;
	protected Collection<String> m_colExportedTextbausteinMarketingtext		= null;

	protected HashMap<String,Tipp> m_hashSortimentTipps = null;

	HashMap<String,String> m_hashProdukbilder	= new HashMap();
	HashMap<String,String> m_hashAttributeFarbenList	= new HashMap();
	
	protected void _debug(final String strCode, final String strOutput) {
		//if ( strCode.contains("1429") || strCode.contains("027456") )
		//LOG.info ( strCode + " => " + strOutput );
	}

	// --- lade eine Lsite mit Exlude-ProduktNummern
	public void LoadExcludeList(final String strFileName) {

		// --- HashSet l�schen
		m_hExludeList.clear();

		// --- Datei �ffnen und einlesen
		BufferedReader in;
		try {
			in = new BufferedReader(new InputStreamReader(new FileInputStream(strFileName)));

			String strCode = "";
			try {
				while ((strCode = in.readLine()) != null) {
					m_hExludeList.add(strCode);
				}
				in.close();
			} catch (final IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} catch (final FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String doPrevJob() {
		return "";
	}

	public String doPostJob(final Collection colColection) {
		return "";
	}

	// -- f�r Bluestore-Ausgabe
	protected Map m_oDatasheetMap = null;

	public Map getDatasheetMap() {
		return this.m_oDatasheetMap;
	}

	public void setDatasheetMap(final Map oMap) {
		this.m_oDatasheetMap = oMap;
	}

	protected ExportFormatter m_oExportFormatter = new ExportFormatter();

	public void setExportFormatter() {

		this.m_oExportFormatter = new ExportFormatter();
	}

	public ExportFormatter getExportFormatter() {
		return this.m_oExportFormatter;
	}

	public boolean bLogin() {
		boolean bResult;

		this.output("bLogin\n", 1);

		// --- Initialize
		m_CntSchlagworteGes.put("de", 0);
		m_CntSchlagworteGes.put("ru", 0);
		m_CntSchlagworteGes.put("en", 0);
		m_CntSchlagworteGes.put("es", 0);
		m_CntSchlagworteGes.put("fr", 0);
		m_CntSchlagworteGes.put("it", 0);
		m_CntSchlagworteGes.put("cs", 0);
		m_CntSchlagworteGes.put("cn", 0);
		m_CntSchlagworteGes.put("pl", 0);
		m_CntSchlagworteGes.put("us-en", 0);
		m_CountProductGes.put("de", 0);
		m_CountProductGes.put("ru", 0);
		m_CountProductGes.put("en", 0);
		m_CountProductGes.put("es", 0);
		m_CountProductGes.put("fr", 0);
		m_CountProductGes.put("it", 0);
		m_CountProductGes.put("cs", 0);
		m_CountProductGes.put("cn", 0);
		m_CountProductGes.put("pl", 0);
		m_CountProductGes.put("us-en", 0);
		m_wm = WeraManager.getInstance();
		bResult = false;

		m_jaloSession = JaloSession.getCurrentSession();

		if (m_bDoLogin) {
			LOG.info("Logging in...");
			final UserManager userMg = m_jaloSession.getUserManager();
			String loginname = "";
			String password = "";
			try {
				// --- Get adminuser
				final Employee customer = userMg.getAdminEmployee();

				// --- Initialize Logindata
				loginname = customer.getLogin();
				password = customer.getPassword();
			} catch (final JaloItemNotFoundException e) {
				e.printStackTrace();
			}
			// set the new user to the jaloSession
			final Properties prop = new Properties();
			prop.setProperty("user.principal", loginname);
			prop.setProperty("user.credentials", password);
			// the user is a employee
			prop.setProperty("session.type", "employee");

			try {
				m_jaloSession.transfer(prop);
				bResult = true;
			} catch (final JaloSecurityException e) {
				e.printStackTrace();
			} catch (final JaloInvalidParameterException e) {
				e.printStackTrace();
			}
		} else {
			LOG.info("Skipping login...");
			bResult = true;
		}

		// --- Katalogversionsystem WERA
		m_weraCatalogVersion = m_wm.getCatalogVersion(m_strCatalog, m_strCatalogversion);
		m_weraMasterCatalogVersion = m_wm.getCatalogVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));

		// --- Festlegen einer eindeutigen Nummer f�r die TextID
		//Random oRandom = new Random( 100 );
		//m_iOffset = oRandom.nextInt();
		m_iOffset = 100;
		m_firstcodeNr	= "";

		//if ( m_iOffset < 0 )
		//	m_iOffset *= -1;

		// --- Output
		this.output("bLogin=" + bResult + "\n", 1);

		return bResult;
	}

	protected void Logout() {
		// TODO Auto-generated method stub
		if (m_bDoLogin) {
			LOG.info("Logging out...");
			WebSessionFunctions.invalidateSession(m_jaloSession.getHttpSession());
		} else {
			LOG.info("Skipping logout...");
		}
		//WebSessionFunctions.invalidateSession((HttpSession) m_jaloSession);
	}

	public void setProductsToExport(final Set oMyProducts) {
		this.m_oProductsToExport = oMyProducts;
	}

	public Set getProductsToExport() {
		return this.m_oProductsToExport;
	}

	public void setDoLogin(final boolean bDoLogin) {
		this.m_bDoLogin = bDoLogin;
	}

	public boolean getDoLogin() {
		return this.m_bDoLogin;
	}

	// --- Hilfsroutinen
	public void setAttribute(final Item oItem, final String strAttribute, final Object oAttributeValue) {
		try {
			// --- Attribute setzen
			if (oAttributeValue != null) {
				oItem.setAttribute(strAttribute, oAttributeValue);
			}
		} catch (final JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	Object getAttribute(final Item oItem, final String strAttribute) {
		// --- Initialize
		Object oObject = null;

		try {
			// --- Hole das Attrbiute
			oObject = oItem.getAttribute(strAttribute);

		} catch (final JaloInvalidParameterException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (final JaloSecurityException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return oObject;
	}

	public void output(final String stringOut, final int iDebugLevel) {

		if (iDebugLevel < m_iDebugLevel || iDebugLevel == 255) {
			System.out.print("[EXPORT] " + stringOut + "\n\r");
		}

	}

	protected String getValidString(final String strString) {
		if (strString == null) {
			return "";
		} else {
			return strString;
		}
	}

	/**
	 * dummy
	 *
	 * @param strTagContent
	 * @param bConvertTag
	 * @param strLanguage
	 * @param textXML
	 * @return
	 */
	protected Element _createTextElementML(String strTagContent, boolean bConvertTag, HashMap<String, String> hashAttributes, Element textXML) {
		return null;
	}

	/**
	 * erstellt einen Textblock f�r Fussnoten, MultiLayer Support
	 *
	 * @param strTypeName
	 * @param hashTagContents
	 * @param iOrder
	 * @param iID
	 * @param strLinkType
	 * @param bConvertTag
	 * @return
	 */
	protected Element createTextElementFootnote(String strTypeName, HashMap<String, String> hashTagContents, final Integer iOrder, final Integer iID,
			final String strLinkType, final boolean bConvertTag) {

		if (m_bMultilayer) {

			// --- Text-Element aufbauen (left)
			Element textXML_FootnoteContent = new Element("Text");
			textXML_FootnoteContent.setAttribute("Id", iID.toString());
			textXML_FootnoteContent.setAttribute("Type", strLinkType);
			textXML_FootnoteContent.setAttribute("LinkType", strLinkType);
			textXML_FootnoteContent.setAttribute("TypeName", strTypeName);
			textXML_FootnoteContent.setAttribute("Sequence", iOrder.toString());

			// --- iterate on all footnotes
			for (String strCurrentLanguage : hashTagContents.keySet()) {

				// --- Attribute f�r TextNode vorbelegen
				HashMap<String, String> hashAttributes = new HashMap();
				hashAttributes.put("Language", strCurrentLanguage);

				// --- get current content
				String strTagContent = hashTagContents.get(strCurrentLanguage);

				// --- Footnote - erstellten
				textXML_FootnoteContent = _createTextElementML(strTagContent, true, hashAttributes, textXML_FootnoteContent);
			}

			return textXML_FootnoteContent;

		} else {

			// --- default without multilayer, get export language
			return _createTextElement(strTypeName, hashTagContents.get(m_strLanguage), iOrder, iID, strLinkType, bConvertTag, "", 0, "", false );
		}
	}

	/**
	 * Text Element ausgeben
	 *
	 * @param String strTypeName
	 * @param String strTagContent
	 * @param Integer iOrder
	 * @param Integer iID
	 * @param String strLinkType
	 * @param boolean bConvertTag
	 * @param String strName
	 * @param int contentCounter
	 * @param String strVPE
	 * @param boolean firstItemOfContent
	 *
     * @return Element textXML
     */
	protected Element _createTextElement(String strTypeName, String strTagContent, final Integer iOrder, final Integer iID,
			final String strLinkType, final boolean bConvertTag, String strName, int contentCounter, String strVPE, boolean firstItemOfContent ) {
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
		strTagContent = strTagContent.replaceAll("&quot;", "\"");
		strTypeName = strTypeName.trim();
		strTagContent = strTagContent.trim();

		// --- Element aufbauen
		final Element textXML = new Element("Text");
		textXML.setAttribute("Id", iID.toString());
		textXML.setAttribute("Type", strLinkType);
		textXML.setAttribute("LinkType", strLinkType);
		textXML.setAttribute("TypeName", strTypeName);
		textXML.setAttribute("Sequence", iOrder.toString());
		//textXML.setAttribute("LinkTypeName", strTypeName) ;

		// --- <TextBlock Language="German">In stabiler Klapptasche mit Klettverschluss zur praktischen Aufbewahrung.</TextBlock>
		final Element inhaltXML = new Element("TextBlock");
		inhaltXML.setAttribute("Language", m_strLanguage);
		//CDATA cdataText = new CDATA(strTagContent);
		strTagContent = strTagContent.replaceAll("##br##", " ");
		strTagContent = strTagContent.replaceAll("##br /##", " ");
		strTagContent = strTagContent.replaceAll("##br/##", " ");
		strTagContent = this.getExportFormatter().formatDescription(strTagContent);
		inhaltXML.addContent(strTagContent);
		textXML.addContent(inhaltXML);

		return textXML;
	}

	/**
	 * Text Element ausgeben localized Version
	 *
	 * @param String strTypeName
	 * @param String strTagContent
	 * @param Integer iOrder
	 * @param Integer iID
	 * @param String strLinkType
	 * @param boolean bConvertTag
	 * @param String strName
	 * @param int contentCounter
	 * @param String strVPE
     * @param boolean firstItemOfContent
     * @param Item weraProduct
     * @param boolean bcurrentIsSet
     * @param boolean boolHideArtikelnummer
	 *
	 * @return Element textXML
     */
	protected Element _createTextElementLocalized (String strTypeName, String strTagContent, final Integer iOrder, final Integer iID,
			final String strLinkType, final boolean bConvertTag, String strName, int contentCounter, String strVPE, boolean firstItemOfContent,
												   Item weraProduct, boolean bcurrentIsSet, boolean boolHideArtikelnummer ) {

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
		strTagContent = strTagContent.replaceAll("&quot;", "\"");
		strTypeName = strTypeName.trim();
		strTagContent = strTagContent.trim();

		// --- Element aufbauen
		final Element textXML = new Element("Text");
		textXML.setAttribute("Id", iID.toString());
		textXML.setAttribute("Type", strLinkType);
		textXML.setAttribute("LinkType", strLinkType);
		textXML.setAttribute("TypeName", strTypeName);
		textXML.setAttribute("Sequence", iOrder.toString());
		//textXML.setAttribute("LinkTypeName", strTypeName) ;

		// --- Artikelnummer localized ---------------------------------------------------------------
		// --- preset
		Collection<String> colLanguages = new ArrayList();
		colLanguages.addAll(m_coAdditionallLanguages);
		colLanguages.add(m_strLanguage);

		// --- get current language
		Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();

		// --- oTextBlock liste erstellen
		for ( String language  : colLanguages ) {

			// -- set language
			m_wm.SetLanguage(language);

			// --- Textblock erzeugen
			String strNameLocalized = "";
			if ( weraProduct instanceof WeraVariante ) {

				strNameLocalized = (String) m_wm.getAttribute(weraProduct, "code");
				// strNameLocalized = (String) m_wm.getAttribute(weraProduct, "name");

			} else {

				strNameLocalized = (String) m_wm.getAttribute(weraProduct, "artikelnr_index");
				//strNameLocalized = (String) m_wm.getAttribute(weraProduct, "name");
			}
			if ( boolHideArtikelnummer ) {
				strNameLocalized	= "";
			}
			Element oTextBlock	= new Element("TextBlock");
			oTextBlock.setAttribute("Language", language );
			oTextBlock.setAttribute("Type", "LocalizedArtNr" );

			//LOG.info("+++++_createTextElementLocalized.strNameLocalized=" +strNameLocalized);
			//LOG.info("+++++_createTextElementLocalized.getClass().getName()=" +weraProduct.getClass().getName());
			if ( true /* bcurrentIsSet */ ) {

				oTextBlock.setText(strNameLocalized);

			} else {

				oTextBlock.setText( strVPE + "x " + strNameLocalized);
			}
			textXML.addContent(oTextBlock);

		} // for ( String language  : colLanguages ) {

		// --- reset current language
		m_wm.SetLanguage( currentSessionLanguage.getIsoCode() );
		// --- Artikelnummer localized ---------------------------------------------------------------

		// --- <TextBlock Language="German">In stabiler Klapptasche mit Klettverschluss zur praktischen Aufbewahrung.</TextBlock>
		final Element inhaltXML = new Element("TextBlock");
		inhaltXML.setAttribute("Type", "content");
		inhaltXML.setAttribute("Language", m_strLanguage);
		//CDATA cdataText = new CDATA(strTagContent);
		strTagContent = strTagContent.replaceAll("##br##", " ");
		strTagContent = strTagContent.replaceAll("##br /##", " ");
		strTagContent = strTagContent.replaceAll("##br/##", " ");
		strTagContent = this.getExportFormatter().formatDescription(strTagContent);
		inhaltXML.addContent(strTagContent);
		textXML.addContent(inhaltXML);

		return textXML;
	}


	/**
	 * Inhalt der S�tze und / Satz in Satz
	 *
	 * @param WeraProduct weraProductSet
	 * @param Element oTexList
     * @return Collection colFootnodes
     */
	protected Collection createContentElement(final WeraProduct weraProductSet, final Element oTexList ) {

		// --- preset
		final HashMap hashmapAllFootnotes 	= new HashMap();
		final Collection colFootnodes 		= new ArrayList();

		// --- Pruefen auf Satz in Satz
		if (weraProductSet instanceof WeraProductSetinSet) {
				
			// --- Inhalt von Satz in Satz
			_createContentElementSetinSet((WeraProductSetinSet) weraProductSet, oTexList, hashmapAllFootnotes, colFootnodes );

/* DEBUG
LOG.info("1. createContentElement.hashmapAllFootnotes.size()=" + hashmapAllFootnotes.size());
LOG.info("1. createContentElement.hashmapAllFootnotes=" + hashmapAllFootnotes);
LOG.info("1. createContentElement.colFootnodes=" + colFootnodes);

// ---- Schleife �ber alle Zeilen
for (final Iterator it1 = colFootnodes.iterator(); it1.hasNext();) {

	Footnote oFootnote = (Footnote) it1.next();
	LOG.info("1. createContentElement.hashmapAllFootnotes.getCode()=" + (String) m_wm.getAttribute( oFootnote, "code") );
	LOG.info("1. createContentElement.hashmapAllFootnotes.getName()=" + (String) m_wm.getAttribute( oFootnote, "name") );
}
*/
		} else {
				

			if ( weraProductSet instanceof WeraProductSet ) {
				
				// --- Inhalt der S�tze
				System.out.println("call _createContentElementSet");
				_createContentElementWeraProductSet( (WeraProductSet)weraProductSet, oTexList );
			
			} else {

			
				System.out.println("call _createContentElement");
				_createContentElementWeraProduct ( weraProductSet, oTexList);
			}
				
		}

		return colFootnodes;
	}

	/**
	 * Inhalt des Produtcs
	 */
	protected void _createContentElementWeraProduct (final WeraProduct weraProduct, final Element oTexList) {
		
		ArrayList<WeraVariante> listVariants = new ArrayList();
		ArrayList<WeraVariante> listVarianteSet = new ArrayList();
		final Collection<WeraVariante> weraVariants = (Collection) m_wm.getAttribute( weraProduct, "variants" );
		
		for ( WeraVariante weraVariant : weraVariants ) {
			listVariants.add(weraVariant);
			//m_wm.setAttribute(weraVariant, "contentQuantity", new Integer(1));
			listVarianteSet.add(weraVariant);
		}
		ArrayList aContent = weraProduct._genCADataForVariantList(listVariants, listVarianteSet);
		
		_createContentElement ( weraProduct, oTexList, aContent );
	}
	
	protected void _createContentElementWeraProductSet (final WeraProductSet weraProductSet, final Element oTexList) {

		ArrayList aContent = weraProductSet.generateWeraProductSetData();
		_createContentElement ( (WeraProduct) weraProductSet, oTexList, aContent );
	}
	
	protected void _createContentElement (final WeraProduct weraProductSet, final Element oTexList, ArrayList aContent ) {
		// --- Debug
		// LOG.info ("s._createContentElementSet="+weraProduct.getCode() );

		// --- Initialize
		final String strLastCodeNr = "";
		final Element oMetaData = null;
		WeraMedia icon1 = null;
		WeraMedia icon2 = null;
		ArrayList colHash = null;
		HashMap oHashMapProdukt = null;
		HashMap oHashMapArtikel = null;
		final ArrayList aMetaData = new ArrayList();
		//LOG.info("Nach generateWeraProductSetData");
		//weraProduct.debugOutWeraProductSetData();

		// --- Initialize
		String strTypeName = "";
		String strTagContent = "";
		int iPos = 0;
		int iOrder = 0;

		// --- Schleife �ber alle Content-Inhalte
		if (aContent != null && aContent.size() > 0) {
			for (final Iterator it1 = aContent.iterator(); it1.hasNext();) {

				// --- Hole Map
				oHashMapProdukt = (HashMap) it1.next();
				iPos++;

				// --- Initialize
				strTypeName = (String) oHashMapProdukt.get("code");
				strTagContent = "";

				colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
				if (colHash != null && colHash.size() > 0) {
					int iPos1 = 0;

					// --- Schleife �ber alle Artikeldaten
					for (final Iterator it2 = colHash.iterator(); it2.hasNext();) {
						// --- Hole Map
						iPos1++;
						oHashMapArtikel = (HashMap) it2.next();
						//LOG.info ( "oHashMapArtikel=" + oHashMapArtikel );
						if (oHashMapArtikel != null) {

							// --- Hole alle Fussnoten des Artikels
							String strFN = "";
							final Collection colFN = (Collection) oHashMapArtikel.get("footnotes");
							if (colFN != null) {
								for (final Iterator it3 = colFN.iterator(); it3.hasNext();) {
									strFN += " " + ((Integer) it3.next()).toString() + ")";
								}
							}

							if (strFN.length() > 0) {
								// --- strFN = "<cPosition:Superscript>" + strFN + "<cPosition:>";
								// strFN = m_oExportFormatter.formatIndesignSetSupS() + strFN + m_oExportFormatter.formatIndesignSetSupE();
								strFN = /* m_oExportFormatter.formatIndesignSetSupS() + */ strFN  /* + m_oExportFormatter.formatIndesignSetSupE() */;
							}
							if (iPos1 < colHash.size()) {
								//strTagContent += "<cNoBreak:1>" + _NormalizeInchCharacter((String) oHashMapArtikel.get("value")) + strFN + ";<cNoBreak:> ";
								strTagContent += _NormalizeInchCharacter((String) oHashMapArtikel.get("value")) + strFN  + "; ";
							} else {
								//strTagContent += "<cNoBreak:1>" + _NormalizeInchCharacter((String) oHashMapArtikel.get("value")) + strFN + "<cNoBreak:>";
								strTagContent += _NormalizeInchCharacter((String) oHashMapArtikel.get("value")) + strFN;
							}
						}

						// --- XML-Element anlegen
						// --- Default Sequnce - Order
						iOrder = iPos;

					} // --- for ( Iterator it2 = colHash.iterator(); it2.hasNext(); ) {

				} // --- if ( colHash != null && colHash.size() > 0 ) {

				// --- Hier korrigieren wir die Dezimalpunkte nach Sprachen
				if (!m_strLanguage.equals("en") && !m_strLanguage.equals("us-en")) {
					strTagContent = strTagContent.replace(".", ",");
				}

				// --- Content korrigieren
				if (strTagContent.length() > 2) {
					strTagContent = strTagContent.trim();
				}

				// <ASCII-WIN><Version:4><ParaStyle:links ohne Einzug><CharStyle:wera\_tabelle>
				//    <cNoBreak:1>1 x 0,5x4,0x25;<cNoBreak:> <cNoBreak:1>1 x 1,0x5,5x25<cNoBreak:>
				// <CharStyle:>
/*				
				// --- Formatmarken f�r Indesign
				strTagContent = "<ASCII-WIN>\r\n<Version:4><ParaStyle:links ohne Einzug><CharStyle:wera\\_tabelle>" + strTagContent;
				strTagContent = strTagContent.replace("\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
				strTagContent = strTagContent + "<CharStyle:>";
				strTagContent = this.getExportFormatter().formatSetContent(strTagContent);
*/
				strTagContent = this.getExportFormatter().formatSetContent(strTagContent);

				// --- Hochstellen des Registerzeichens
				strTypeName = strTypeName.replace("\u00ae",
						m_oExportFormatter.formatSetSupS() + "\u00ae" + m_oExportFormatter.formatSetSupE());
//				strTagContent = strTagContent.replace("\u00ae",
//						m_oExportFormatter.formatSetSupS() + "\u00ae" + m_oExportFormatter.formatSetSupE());

				// --- get the product
				WeraProduct oSetProduct = (WeraProduct) ProductManager.getInstance().getProductByPK((PK) oHashMapProdukt.get("pk"));
				boolean isWeraProductSet	= ( oSetProduct instanceof WeraProductSet);

				// --- Zusammenhalten einer Zeile
				// --- Textobjekt anlegen
				LOG.info("strTypeName (code)=" + strTypeName + ", strTagContent=" + strTagContent );
				// final Element textXML = _createTextElement(strTypeName, strTagContent, iOrder, m_iOffset,"BLT_SET", false, "", 0, "", false );
				final Element textXML = _createTextElementLocalized( strTypeName, strTagContent, iOrder, m_iOffset,"BLT_SET", false, "", 0, "", false, oSetProduct, isWeraProductSet, false  );
				m_iOffset++;
				oTexList.addContent(textXML);

				// --- additional infomations for Webexport -------------------------------------------------------------------
				// --- initialize
				String strTitle = "";
				String strNormalizedUrl_Product = "";
				String strNormalizedUrl_Category = "";

				// --- Normalized URLs for Webexport
				// --- get the product
				if (oSetProduct != null) {

					// --- Title f�r Webexport
					strTitle = oSetProduct.getName();
					if (strTitle == null) {
						strTitle = "";
					}

					// --- get the category (first one only)
					Category oCategoryWera = oSetProduct.getFirstCategoryByProduct(m_strCatalogversion);
					if (oCategoryWera == null) {
						// --- i know it's wrong, but shouldn't be here
						oCategoryWera = m_oCategoryWera;
						LOG.error("no category-info found, we take the default category!!");
					}
					strNormalizedUrl_Category = m_wm.strNormalizeURL(oCategoryWera, m_strLanguage);
					strNormalizedUrl_Product = weraProductSet.s_normalizeFilenameForImageLookup(oSetProduct.getCode());

					textXML.setAttribute("NormalizedProductUrl", strNormalizedUrl_Product);
					textXML.setAttribute("NormalizedCategoryUrl", strNormalizedUrl_Category);

				} // --- if ( oSetProduct != null ) {

				// --- pr�fe ob das Produkt im Katalog ist --------------------------------------------------------------------
				Collection colSuperCategories = m_wm.getCategoriesByProductAndCatalogVersion(oSetProduct, this.m_strCatalogversion);
				//LOG.info("+++ this.m_strCatalogversion=" + this.m_strCatalogversion );
				//LOG.info("+++ colSuperCategories=" + colSuperCategories );
				//LOG.info("+++ oSetProduct.getCode()=" + oSetProduct.getCode());
				if (oSetProduct.IsAktiv() && colSuperCategories.size() > 0) {
					// --- Satz-Produkt befindet sich ebenfalls im Export
					//LOG.info("+++ oSetProduct.getCode()=" + oSetProduct.getCode() + " => is_catalog=1 " );
					textXML.setAttribute("is_catalog", "1");
				} else {
					// --- Satz-Produkt befindet sich nicht im Export
					//LOG.info("+++ oSetProduct.getCode()=" + oSetProduct.getCode() + " => is_catalog=0 " );
					textXML.setAttribute("is_catalog", "0");
				}
				// --- pr�fe ob das Produkt im Katalog ist --------------------------------------------------------------------

				// --- Title for Webexport
				textXML.setAttribute("Title", strTitle);
				// --- additional infomations for Webexport -------------------------------------------------------------------

				// --- Icon 1
				//icon1 = (WeraMedia) oHashMapProdukt.get("icons1");
				final Collection<WeraMedia> colWeraMediaIcon1 = (Collection<WeraMedia>) oHashMapProdukt.get("icons1_collection");
				int iCntIcon = 1;
				if (colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1) {

					// --- iterate on icon-collection
					for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();) {
						// --- get icon
						icon1 = (WeraMedia) itIconMedias.next();
						if (icon1 != null) {
							final Element oIcon1 = _initMerkmalIcon(null, null, icon1, null, "icons", new Integer(iCntIcon).toString());
							oIcon1.setAttribute("Sequence", "1");

							// --- media-icon (text-element zuweisen)
							// achtung print-export braucht einen zus�tzlichen "Text"-node, daher auslagern in methode!!
							_assign2TextList(iCntIcon, iOrder, oIcon1, textXML, oTexList);

							// --- icon-counter
							iCntIcon++;
						}

					} // ---for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)

				} // --- if ( colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1 ) {

				// --- Icon 2
				//icon2 = (WeraMedia) oHashMapProdukt.get("icons2");
				final Collection<WeraMedia> colWeraMediaIcon2 = (Collection<WeraMedia>) oHashMapProdukt.get("icons2_collection");
				if (colWeraMediaIcon2 != null && colWeraMediaIcon2.size() >= 1) {

					// --- iterate on icon-collection
					for (final Iterator itIconMedias = colWeraMediaIcon2.iterator(); itIconMedias.hasNext();) {
						// --- get icon
						icon2 = (WeraMedia) itIconMedias.next();
						if (icon2 != null) {
							final Element oIcon2 = _initMerkmalIcon(null, null, icon2, null, "icons", new Integer(iCntIcon++).toString());
							oIcon2.setAttribute("Sequence", "2");
							textXML.addContent(oIcon2);
						}
					}
				}

			} // --- for ( Iterator it1 = aContent.iterator(); it1.hasNext(); ) {

		} // --- if ( aContent != null && aContent.size() > 0 ) {

		// --- Debug
		//LOG.info ("e._createContentElementSet="+weraProduct.getCode() );
	}

	/**
	 * Version f�r Printausgabe vor Katalog 2016 (nur 1. Icon wird verwendet)
	 *
	 * @param iCntIcon
	 * @param oIcon1
	 * @param oTexList
	 * @return Element
	 */
	protected Element _assign2TextList(int iCntIcon, int iOrder, Element oIconLinks, Element textXML, Element oTexList) {

		if (iCntIcon == 1) {
			// --- 1. icon
			textXML.addContent(oIconLinks);
		} else {
			// --- weitere icons ignorieren
			// ....
		}

		return oTexList;
	}


	/**
	 * Inhalt der S�tze
	 *
	 * @param WeraProductSetinSet weraProduct
	 * @param Element oTexList
	 */
	protected void _createContentElementSetinSet(final WeraProductSetinSet weraProductSetinSet, final Element oTexList, HashMap hashmapAllFootnotes, Collection colFootnotes ) {

		// --- Debug
		LOG.info ("s._createContentElementSetinSet getCode()="+ weraProductSetinSet.getCode() );

		// --- Initialize
		int iEbeneEinrueckung	= 0;
		WeraMedia oMediaIcon1 = null;
		WeraMedia oMediaIcon2 = null;
		Element oMetaData = null;
		boolean bIsNextSet = true;
		String strPrevArtnr = "";
		final int iPos = 0;
		int iOrder = 0;
		HashSet setCode = new HashSet();
		HashSet setEAN = new HashSet();
		boolean firstItemOfContent	= true;

		// --- Initialisiere den Content f�r Satz in Satz
		weraProductSetinSet.generateWeraProductSetData();

		// --- wenn sis in sis dann product-header einfügen  ---------------------------------------------------
		if ( weraProductSetinSet.containsSIS() ) {

			// --- VPE auswerten
			Integer intContentQuantitySiS = (Integer) m_wm.getAttribute(weraProductSetinSet,"contentQuantity");
			if ( intContentQuantitySiS == null ) { intContentQuantitySiS = new Integer(1); }

			// --- Header ELement angegen
			Element oProductheaderset	= this._createHeaderSet(weraProductSetinSet, oTexList, "BLT_PRODUCTHEADERSET", intContentQuantitySiS.toString(), true );
		}
		// --- wenn sis in sis dann product-header einfügen  ---------------------------------------------------

		// --- Inhalt des Satz in Satz Content generieren
		this._createcolWeraProductSetinSetData (weraProductSetinSet, weraProductSetinSet.m_colWeraProductSetinSetData, oTexList, hashmapAllFootnotes, colFootnotes, iEbeneEinrueckung, firstItemOfContent );

		// --- Display-Darstellung ------------------------------------------------------------------------------
		setDisplayTitleKatalog(weraProductSetinSet, oTexList, null );

		// --- Debug
		LOG.info ("e._createContentElementSetinSet="+ weraProductSetinSet.getCode() );
	}


	/**
	 * Header Element anlegen
	 *
	 * @param weraProduct
	 * @param oTexList
	 * @param strTyName
     */
	protected Element _createHeaderSet (final Item weraProduct, final Element oTexList, final String strTyName, final String strVPE, boolean exportMetadata ) {

		// --- preset
		String strArtnrSiS = "";
		if ( weraProduct instanceof WeraProductSet ) {
			strArtnrSiS = (String) m_wm.getAttribute(weraProduct, "artnr");
		} else {
			strArtnrSiS = (String) m_wm.getAttribute(weraProduct, "code");
		}
		if (strArtnrSiS == null) { strArtnrSiS = ""; }
		String strVarnrSiS = (String) m_wm.getAttribute(weraProduct,"variantennr");
		if ( strVarnrSiS == null ) { strVarnrSiS = ""; }
		String strLagernrSiS = (String) m_wm.getAttribute(weraProduct,"lagernr");
		if ( strLagernrSiS == null ) { strLagernrSiS = ""; }
		final String strCodeSiS = strLagernrSiS + strArtnrSiS + strVarnrSiS;
		String strEAN = (String) m_wm.getAttribute(weraProduct,"ean");
		if ( strEAN == null ) { strEAN = ""; }

		// --- Element aufbauen
		final Element textXML = new Element("Text");
		textXML.setAttribute("Id", 			new Integer(m_iOffset).toString() );
		textXML.setAttribute("Type", 		strTyName );
		textXML.setAttribute("LinkType", 	strTyName );
		textXML.setAttribute("TypeName", 	strTyName );
		textXML.setAttribute("Sequence", 	"0" );
		m_iOffset++;
		oTexList.addContent(textXML);

		// --- preset
		Collection<String> colLanguages = new ArrayList();
		colLanguages.addAll(m_coAdditionallLanguages);
		colLanguages.add(m_strLanguage);

		// --- get current language
		Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();

		// --- oTextBlock liste erstellen
		for ( String language  : colLanguages ) {

			// -- set language
			m_wm.SetLanguage(language);

			// --- Textblock erzeugen
			WeraProduct weraProductName	= null;
			String strNameLocalized 	= "";
			if ( weraProduct instanceof WeraVariante ) {

				// --- hole basisprodukt
				weraProductName = (WeraProduct) m_wm.getAttribute(weraProduct, "baseproduct");

			} else {

				weraProductName	= (WeraProduct)weraProduct;
			}
			strNameLocalized = (String) m_wm.getAttribute(weraProductName, "artikelnr_index");


			// --- Textblock erzeugen
			Element oTextBlock	= new Element("TextBlock");
			oTextBlock.setAttribute("Language", language );
			oTextBlock.setAttribute("Type", "LocalizedArtNr" );
			oTextBlock.setText(strVPE + "x " + strNameLocalized);
			textXML.addContent(oTextBlock);

		} // for ( String language  : colLanguages ) {

		// --- reset current language
		m_wm.SetLanguage( currentSessionLanguage.getIsoCode() );


		// --- metadata
		if ( exportMetadata ) {

			Element oMetadata	= new Element("Metadata");
			oMetadata.setAttribute("rawcode", strCodeSiS );
			oMetadata.setAttribute("vpe", strVPE );
			oMetadata.setAttribute("ean", strEAN);
			oMetadata.setText(strCodeSiS);
			textXML.addContent(oMetadata);
		}

		return textXML;
	}

	/**
	 * Inhalt des Satz in Satz Content generieren
	 *
	 * @param WeraProductSetinSet weraProduct
	 * @param Collection colWeraProductSetinSetData
	 * @param Element oTexList
	 */
	protected void _createcolWeraProductSetinSetData(final WeraProductSet weraProductSetinSet, final Collection colWeraProductSetinSetData,
													 final Element oTexList, HashMap hashmapAllFootnotes, Collection colParamFootnotes, int iEbeneEinrueckung, boolean firstItemOfContent ) {

		// --- Initialize
		WeraMedia oMediaIcon1 = null;
		WeraMedia oMediaIcon2 = null;
		Element oMetaData = null;
		boolean bIsNextSet = true;
		String strPrevArtnr = "";
		final int iPos = 0;
		int iOrder = 0;
		HashSet setCode = new HashSet();
		HashSet setEAN = new HashSet();


		// --- preset
		String strArtnrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"artnr");
		if ( strArtnrSiS == null ) { strArtnrSiS = ""; }
		String strVarnrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"variantennr");
		if ( strVarnrSiS == null ) { strVarnrSiS = ""; }
		String strLagernrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"lagernr");
		if ( strLagernrSiS == null ) { strLagernrSiS = ""; }
		final String strCodeSiS = strLagernrSiS + strArtnrSiS + strVarnrSiS;
		Integer intContentQuantitySiS = (Integer) m_wm.getAttribute(weraProductSetinSet,"contentQuantity");
		if ( intContentQuantitySiS == null ) { intContentQuantitySiS = new Integer(1); }


		// --- Schleife �ber alle Daten
		for (final Iterator iterProductSetinSet = colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {

			// --- N�chster Satz
			final HashMap productHash = (HashMap) iterProductSetinSet.next();


			// --- Initialize
			boolean bcurrentIsSet				= false;
			final String strArtnr				= (String) productHash.get("artnr");
			final String strVarnr				= (String) productHash.get("varnr");
			final String strLagernr				= (String) productHash.get("lagernr");
			String strName						= (String) productHash.get("name");
			String strVPE						= (String) productHash.get("vpe");
			int		contentCounter				= 0;

			// --- hole weraprodukt
			PK oPK 								= (PK) productHash.get("pk");
			final Item oWeraProduct 			= JaloSession.getCurrentSession().getItem(oPK);

			// --- ermitteln der zu exporierenden Marketing und Bulletpoints -------------------------------------------------------------
			Collection<Textbaustein> colBulletpoints	= null;
			Collection<Textbaustein> colMarketing		= null;
			if ( oWeraProduct instanceof WeraVariante ) {

				colBulletpoints	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weravariante2bulletpoints");
				colMarketing		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weravariante2marketing");

			} else {

				colBulletpoints		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weraproductset2bulletpoints");
				colMarketing		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weraproduct2marketing");
			}

			// --- sammeln der Marketingtextbaustein
			_collectTextbausteineBulletpoints ( colBulletpoints );
			_collectTextbausteineMarketing ( colMarketing );
			//LOG.info("++++++ collect.m_colTextbausteinBullet.size=" + m_colTextbausteinBullet.size());
			//LOG.info("++++++ collect.m_colTextbausteinMarketingtext.size=" + m_colTextbausteinMarketingtext.size());

			// --- ermitteln der zu exporierenden Marketing und Bulletpoints -------------------------------------------------------------


			// --- pürfung auf Satz in Satz in SATZ ----------------------------------
			String strCodeTmp = strLagernr + strArtnr + strVarnr;
			// LOG.info("_createContentElementSetinSet.strCode=" + strCodeTmp );
			// LOG.info ("++++++++++++++++ createContentElementSetinSet.strCode=" + strCodeTmp );
			// LOG.info ("++++++++++++++++ createContentElementSetinSet.artNr=" + productHash.get("code") );
			// LOG.info ("++++++++++++++++ createContentElementSetinSet.strVPE=" + strVPE );
			if ( productHash.get("isSiS") == "1" ) {

				// --- Wera-Produktheader ------------------------------------------------
				if ( oWeraProduct instanceof WeraProductSetinSet ) {

					if ( !(m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT") || m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT") )) {
						// --- Header ELement angegen
						Element oHeaderset = this._createHeaderSet((WeraProductSetinSet) oWeraProduct, oTexList, "BLT_HEADERSET", strVPE, true);
					}

				}
				// --- Wera-Produktheader ------------------------------------------------

				// --- Inhalt des Satz in Satz Content generieren
				this._createcolWeraProductSetinSetData( (WeraProductSetinSet) oWeraProduct, (Collection)productHash.get("SiSComponents"), oTexList, hashmapAllFootnotes, colParamFootnotes, ++iEbeneEinrueckung, firstItemOfContent );

				// --- skio
				continue;

			} else {

				// LOG.info ("++++++++++++++++ createContentElementSetinSet.isSiS in SIS=0" );
			}
			// LOG.info ("++++++++++++++++ oWeraProduct.getClass().getSimpleName()=" + oWeraProduct.getClass().getSimpleName());

			// --- Bei Varianten immer eine Beschreibung mit ausgeben
			if ( oWeraProduct instanceof WeraVariante ) {

				if ( !(m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT") || m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT") )) {
					// --- Header ELement angegen
					// LOG.info ("++++++++++++++++ ist WeraVariante schreibe Header=" );
					Element oHeaderset = this._createHeaderSet((WeraVariante) oWeraProduct, oTexList, "BLT_HEADERSET", strVPE, true);

					// --- ebene SIS in SIS
					oHeaderset.setAttribute("ebene", String.valueOf(iEbeneEinrueckung));
				}

			}
			// --- pürfung auf Satz in Satz in SATZ ----------------------------------

			// --- Wera-Produktheader ------------------------------------------------
			//LOG.info("_createContentElementSetinSet strName=" + strName );
			//LOG.info("_createContentElementSetinSet ist getClass=" + oWeraProduct.getClass().getName() );
			if ( oWeraProduct instanceof WeraProductSet ) {

				if ( !(m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT") || m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT") )) {
					// --- Header ELement angegen
					// LOG.info("_createContentElementSetinSet strName=" + strName );
					// LOG.info("_createContentElementSetinSet ist WeraProductSet" );
					Element oHeaderset = this._createHeaderSet(oWeraProduct, oTexList, "BLT_HEADERSET", strVPE, true);

					// --- ebene SIS in SIS
					oHeaderset.setAttribute("ebene", String.valueOf(iEbeneEinrueckung));

					bcurrentIsSet = true;
				}

			} else {

				bcurrentIsSet	= false;
			}
			// --- Wera-Produktheader ------------------------------------------------


			// --- Schleife �ber alle Varianten des Satzes
			final ArrayList listVariantdata = (ArrayList) productHash.get("variantdata");
			// --- Zusammenhalten einer Zeile

			// --- Textobjekt anlegen
			if ( listVariantdata.size() == 0 ) {

				System.out.println("*+++++~~~ zukauf._createTextElement=" + "" + ", name=" + strName + ", counter=" + contentCounter + ", strVPE=" + strVPE);
				final Element textXML = _createTextElementLocalized(    "BLT_SET", "", iOrder, m_iOffset, "BLT_SET", false, strName, 1, strVPE,
																		firstItemOfContent, oWeraProduct, bcurrentIsSet, false );
				m_iOffset++;
				oTexList.addContent(textXML);
			}


			// --- schleife über alle Varianten
			for (final Iterator itVariant = listVariantdata.iterator(); itVariant.hasNext();) {

				// --- get hashmap
				final HashMap hashVariantdata = (HashMap) itVariant.next();

				// --- hole weraprodukt
				PK oPKTmp							= (PK) hashVariantdata.get("pk");
				final Item oWeraArticle 			= JaloSession.getCurrentSession().getItem(oPKTmp);

				// --- ermitteln der zu exporierenden Marketing und Bulletpoints -------------------------------------------------------------
				colBulletpoints	= null;
				colMarketing	= null;
				if ( oWeraArticle instanceof WeraVariante ) {

					colBulletpoints	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraArticle, "weravariante2bulletpoints");
					colMarketing		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraArticle, "weravariante2marketing");

				} else {

					colBulletpoints		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraArticle, "weraproductset2bulletpoints");
					colMarketing		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraArticle, "weraproduct2marketing");
				}

				// --- sammeln der Marketingtextbaustein
				_collectTextbausteineBulletpoints ( colBulletpoints );
				_collectTextbausteineMarketing ( colMarketing );
				//LOG.info("++++++ collect.m_colTextbausteinBullet.size=" + m_colTextbausteinBullet.size());
				//LOG.info("++++++ collect.m_colTextbausteinMarketingtext.size=" + m_colTextbausteinMarketingtext.size());

				// --- ermitteln der zu exporierenden Marketing und Bulletpoints -------------------------------------------------------------

				// --- counter
				contentCounter++;

				// --- Pr�fen, ob eBLT_SETin neuer Satz beginnnt
				bIsNextSet = (strPrevArtnr == null || strArtnr == null) || (strPrevArtnr.equals(strArtnr) == false);

				// --- Neue Zeile initialisieren
				final Collection<String> colCells = new ArrayList();

				// --- Initialize
				String strTypeName = "";
				String strTagContent = "";
				String strEan = "";
				String strVpe = "";
				final String strMedia1 = "";
				final String strMedia2 = "";
				final String strVariant = "";
				final String strCode = "";

				// --- Code
				// LOG.info("+++++productHash.code="+productHash.get("code"));
				// LOG.info("+++++hashVariantdata.code=" + hashVariantdata.get("code"));
				// LOG.info("+++++hashVariantdata.hashVariantdata=" + hashVariantdata );
				//strTypeName = (String) productHash.get("code");

				strTypeName = (String) hashVariantdata.get("code");
				strTypeName = strTypeName.replace("Satz", "");

				// --- Variantdaten
				final Collection colVariants = (Collection) hashVariantdata.get("colHashArtikel");
				//LOG.info("+++++colVariants=" + colVariants);
				int iPos1 = 1;
				for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();) {

					// --- get hashmap
					final HashMap oHashMapArtikel = (HashMap) iterVariant.next();

					if (oHashMapArtikel != null) {

					// LOG.info ( "_createContentElementSetinSet.oHashMapArtikel=" + oHashMapArtikel );

						// --- Hole alle Fussnoten des Artikels
						String strFN = "";
						final Collection colFN = (Collection) oHashMapArtikel.get("footnotes");

//////////////////////////////////////////////////// DEBUG

						// --- hole weravariante
						Item oWeraArticleTmp = (Item) oHashMapArtikel.get("variant");
						Collection colFootnotes	=  (Collection)m_wm.getAttribute(oWeraArticleTmp,"footnotes");
						if ( colFootnotes != null && colFootnotes.size() > 0 ) {

							//LOG.info("+++++strTagContent.oWeraArticleTmp.getCode() (FN)=" + m_wm.getAttribute(oWeraArticleTmp,"code") );

							// --- schleife über alle fussnoten der variante
							for (final Iterator it4 = colFootnotes.iterator(); it4.hasNext();) {

								Footnote oFootnote	= (Footnote) it4.next();
								String strCode1	= (String)m_wm.getAttribute(oFootnote,"code");
								String strName1	= (String)m_wm.getAttribute(oFootnote,"name");
								//LOG.info("+++++fn.oFootnote.code (FN)=" + strCode1 );
								//LOG.info("+++++fn.oFootnote.name (FN)=" + strName1 );
								String strFootnote_Number	= "";
								if ( hashmapAllFootnotes.get(strCode1) == null ) {

									strFootnote_Number	= Integer.toString(hashmapAllFootnotes.size() + 1);
									hashmapAllFootnotes.put(strCode1, strFootnote_Number );

									// --- fussnote merken
									colParamFootnotes.add(oFootnote);

								} else {

									strFootnote_Number	= (String)hashmapAllFootnotes.get(strCode1);
								}

								strFN += " " + strFootnote_Number + ")";

							} // for (final Iterator it4 = colFootnotes.iterator(); it4.hasNext();) {
						}

////////////////////////////////////////////////////
//////////////////////////////////////////////////// original -
/*
						if (colFN != null) {
							for (final Iterator it3 = colFN.iterator(); it3.hasNext();) {
								strFN += " " + ((Integer) it3.next()).toString() + ")";
							}
						}
*/
//////////////////////////////////////////////////// original -


						if (strFN.length() > 0) {
							// --- strFN = "<cPosition:Superscript>" + strFN + "<cPosition:>";
							//strFN = m_oExportFormatter.formatIndesignSetSupS() + strFN + m_oExportFormatter.formatIndesignSetSupE();
							strFN = /* m_oExportFormatter.formatIndesignSetSupS() + */ strFN /* + m_oExportFormatter.formatIndesignSetSupE() */;
						}
						if (iPos1 < colVariants.size()) {
							//strTagContent += "<cNoBreak:1>" + oHashMapArtikel.get("value") + strFN + ";<cNoBreak:> ";
							strTagContent += /* "<cNoBreak:1>" + */ oHashMapArtikel.get("value") + strFN + "; "; /* + ";<cNoBreak:> "*/
						} else {
							// strTagContent += "<cNoBreak:1>" + oHashMapArtikel.get("value") + strFN + "<cNoBreak:>";
							strTagContent += /* "<cNoBreak:1>" + */ oHashMapArtikel.get("value") + strFN /* + "<cNoBreak:>" */;
						}


					} // --- if ( oHashMapArtikel!=  null ) {

					// --- Default Sequnce - Order
					iPos1++;
					iOrder = iPos;

				} // --- for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();)

				// --- VPE / EAN
				if (m_strLanguage.equals("us-en") || m_strLanguage.equals("us-es") || m_strLanguage.equals("us-fr")) {
					strEan = (String) productHash.get("ean_us");
				} else {
					strEan = (String) productHash.get("ean");
				}
				if (strEan == null || strEan.length() == 0 || strEan.length() < 8) {
					strEan = "";
				} else {
					strEan = strEan.substring(7);
				}
				strVpe = (String) productHash.get("vpe");
				if (strVpe == null) {
					strVpe = "";
				}

				// --- Neuer Satz, Metadata -----------------------------------------------------------------------------------
				oMetaData = new Element("Metadata");
//System.out.println("***_createContentElementSetinSet."+m_strCurrentTemplateName + "***");
				if (bIsNextSet && (m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT"))) {
//System.out.println("***create second BLT_SET"***");
					// --- Element anlegen
					oMetaData = new Element("Metadata");

					// --- Element Inhalt setzen
					oMetaData.addContent(m_oExportFormatter.formatCode(strLagernr, strArtnr, strVarnr));

					// --- attribute setzen
					oMetaData.setAttribute("ean", strEan);
					oMetaData.setAttribute("vpe", strVpe);
					oMetaData.setAttribute("rawcode", strLagernr + strArtnr + strVarnr);

					if ( m_strCurrentTemplateName.equals("PRODUCTSETINSET_NO_CONTENT") ) {

						// --- Hyperlink CodeNr
						if ( !strCodeSiS.contains("??") ) {
							m_firstcodeNr = strCodeSiS;
						}

					} else {
						// --- Hyperlink CodeNr
						if ( !strCode.contains("??") ) {
							m_firstcodeNr = strCode;
						}

					}


					// --- Zusammenhalten einer Zeile
					// --- Textobjekt anlegen
					final Element textXML = _createTextElementLocalized("", "", iOrder, m_iOffset, "BLT_SET", false, "", 0, "", false, oWeraArticle, bcurrentIsSet, false );
					m_iOffset++;
					oTexList.addContent(textXML);

					// --- SatzInSatz Info
					textXML.addContent(oMetaData);

				}

				// --- Element anlegen
				oMetaData = new Element("Metadata");

				// --- im n�chsten Block als "leer" ausgeben
				String strTagContentTmp = "";
				if (m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT")) {

					// --- Formatmarken f�r Indesign (Ausgabe mit ArtNr + Zeilenumbruch)
/*
					strTagContent = "<ASCII-WIN>\r\n<Version:4><ParaStyle:links ohne Einzug><CharStyle:wera\\_tabelle\\_sbvariante>"
							+ "<cTypeface:LT 67 Medium Condensed><cNoBreak:1>" + strTypeName + "<0x000A><cTypeface:>"
							+ strTagContent;
					strTagContent = strTagContent.replace("\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
					strTagContent = strTagContent + "<CharStyle:>";
*/
					strTagContent = this.getExportFormatter().formatSetContent(strTagContent);

					// --- ean / vpe leer �bergeben
					strEan = "";
					strVpe = "";

					// --- Element Inhalt setzen
					strTagContentTmp = "";

				} else {
/*
					// --- Formatmarken f�r Indesign (Ausgabe ohne ArtNr + Zeilenumbruch)
					strTagContent = "<ASCII-WIN>\r\n<Version:4><ParaStyle:links ohne Einzug><CharStyle:wera\\_tabelle\\_sbvariante>"
							+ strTagContent;
					strTagContent = strTagContent.replace("\u00ae", "<cPosition:Superscript><cSkew:-0.000000><0x00AE><cPosition:>");
					strTagContent = strTagContent + "<CharStyle:>";
					strTagContent = this.getExportFormatter().formatSetContent(strTagContent);
*/
					strTagContent = this.getExportFormatter().formatSetContent(strTagContent);

					// --- Element Inhalt setzen
					strTagContentTmp = m_oExportFormatter.formatCode(strLagernr, strArtnr, strVarnr);
				}

				// --- Hochstellen des Registerzeichens
				strTypeName = strTypeName.replace("\u00ae",
						m_oExportFormatter.formatSetSupS() + "\u00ae" + m_oExportFormatter.formatSetSupE());
//				strTagContent = strTagContent.replace("\u00ae",
//						m_oExportFormatter.formatSetSupS() + "\u00ae" + m_oExportFormatter.formatSetSupE());

//<Metadata rawcode="05073313001" ean="029140" vpe="5">05##b##073313##e_b##001</Metadata>
				// --- doppelte code-nummer leer lassen
				if (setCode.contains(strLagernr + strArtnr + strVarnr)) {
					oMetaData.setAttribute("rawcode", "");
					oMetaData.setAttribute("vpe", "");

					// --- tag-content
					oMetaData.addContent("");

				} else {

					oMetaData.setAttribute("rawcode", strLagernr + strArtnr + strVarnr);
					oMetaData.setAttribute("vpe", strVpe);


					if ( m_strCurrentTemplateName.equals("PRODUCTSETINSET_NO_CONTENT") ) {

						// --- Hyperlink CodeNr
						if ( m_firstcodeNr	== "" ) {
							m_firstcodeNr = strCodeSiS;
						}

					} else {
						// --- Hyperlink CodeNr
						if ( m_firstcodeNr	== "" ) {
							m_firstcodeNr	= strLagernr + strArtnr + strVarnr;
						}

					}

					// --- tag-content
					oMetaData.addContent(strTagContentTmp);

					// --- merken
					setCode.add(strLagernr + strArtnr + strVarnr);
				}

				// --- doppelte ean leer lassen
				if (setEAN.contains(strEan)) {
					oMetaData.setAttribute("ean", "");
				} else {
					oMetaData.setAttribute("ean", strEan);

					// --- merken
					setEAN.add(strEan);
				}

				// --- attribute-vpe setzen
				oMetaData.setAttribute("vpe", strVpe);
				// --- Neuer Satz, Metadata -----------------------------------------------------------------------------------

				// --- artikelnummer nicht ausgeben bei varianten
				boolean boolHideArtikelnumber = false;;
				if ( oWeraProduct instanceof WeraVariante ) {

					// --- Artikelnummer nicht anzeigen
					LOG.info ("++++++++++++++++ hide boolHideArtikelnumber=" );
					boolHideArtikelnumber	= true;
				}

				// --- Zusammenhalten einer Zeile
				// --- Textobjekt anlegen
// System.out.println("*+++++~~~ vor._createTextElement=" + strTypeName + ", name=" + strName + ", counter=" + contentCounter + ", strVPE=" + strVPE);
				final Element textXML = _createTextElementLocalized(strTypeName, strTagContent, iOrder, m_iOffset, "BLT_SET", false, strName, contentCounter, strVPE, firstItemOfContent, oWeraArticle, bcurrentIsSet, boolHideArtikelnumber );
				m_iOffset++;
				oTexList.addContent(textXML);

				// --- CodeSIS
				textXML.setAttribute("sis_code", strCodeSiS );

				// --- SatzInSatz Info
				textXML.addContent(oMetaData);


				// --- Icons --------------------------------------------------------------------------------------------------------------
				int iCntIcon = 1;
				// --- Icon 1
				//icon1 = (WeraMedia) oHashMapProdukt.get("icons1");
				final Collection<WeraMedia> colWeraMediaIcon1 = (Collection<WeraMedia>) hashVariantdata.get("icons1_collection");
				if (colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1) {

					// --- iterate on icon-collection
					for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();) {
						// --- get icon
						oMediaIcon1 = (WeraMedia) itIconMedias.next();
						if (oMediaIcon1 != null) {
							final Element oIcon1 = _initMerkmalIcon(null, null, oMediaIcon1, null, "icons", new Integer(iCntIcon++).toString());
							oIcon1.setAttribute("Sequence", "1");
							textXML.addContent(oIcon1);
						}
					}
				}
				/*
				 // --- Icon 1
				 oMediaIcon1 = (WeraMedia) hashVariantdata.get("icons1");
				 if (oMediaIcon1 != null)
				 {
				 final Element oIcon1 = _initMerkmalIcon(null, null, oMediaIcon1, null, "icons", "1");
				 oIcon1.setAttribute("Sequence", "1");
				 textXML.addContent(oIcon1);
				 }
				 */

				// --- Icon 2
				//icon2 = (WeraMedia) oHashMapProdukt.get("icons2");
				final Collection<WeraMedia> colWeraMediaIcon2 = (Collection<WeraMedia>) hashVariantdata.get("icons2_collection");
				if (colWeraMediaIcon2 != null && colWeraMediaIcon2.size() >= 1) {

					// --- iterate on icon-collection
					for (final Iterator itIconMedias = colWeraMediaIcon2.iterator(); itIconMedias.hasNext();) {
						// --- get icon
						oMediaIcon2 = (WeraMedia) itIconMedias.next();
						if (oMediaIcon2 != null) {
							final Element oIcon2 = _initMerkmalIcon(null, null, oMediaIcon2, null, "icons", new Integer(iCntIcon++).toString());
							oIcon2.setAttribute("Sequence", "2");
							textXML.addContent(oIcon2);
						}
					}
				}
				/*
				 // --- Icon 2
				 oMediaIcon2 = (WeraMedia) hashVariantdata.get("icons2");
				 if (oMediaIcon2 != null)
				 {
				 final Element oIcon2 = _initMerkmalIcon(null, null, oMediaIcon2, null, "icons", "2");
				 oIcon2.setAttribute("Sequence", "2");
				 textXML.addContent(oIcon2);
				 }
				 */
				// --- Icons --------------------------------------------------------------------------------------------------------------

				// --- Artikelnummer merken
				strPrevArtnr = strArtnr;

			} // --- for ( Iterator itVariant=listVariantdata.Iterator(); itVariant.hasNext() )

			// --- n�chstes Element ist nicht mehr das erste!
			firstItemOfContent	= false;

		} // --- for (final Iterator iterProductSetinSet = m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {
	}


	/**
	 * BLT_DISPLAYSET: Display Produkttitle hinzufügen Katalogversion
	 *
	 * @param Item weraProduct
	 * @param Element oTexList
     */
	protected void setDisplayTitleKatalog( Item weraProductSet, Element oTexList, String strParamPrefix ) {

		LOG.info("Produktexport Display-Darstellung localized .(start)");

		// --- preset
		Collection<String> allLanguages = new ArrayList();
		allLanguages.addAll(m_coAdditionallLanguages);
		allLanguages.add(m_strLanguage);
		boolean first		= true;
		String strPrefix	= null;
		String strEan		= "";

		// --- EAN
		if (m_strLanguage.equals("us-en") || m_strLanguage.equals("us-es") || m_strLanguage.equals("us-fr")) {
			strEan = (String) m_wm.getAttribute( weraProductSet,"ean_us" );
		} else {
			strEan = (String) m_wm.getAttribute( weraProductSet,"ean" );
		}
		if (strEan == null || strEan.length() == 0 || strEan.length() < 8) {
			strEan = "";
		} else {
			strEan = strEan.substring(7);
		}

		// --- inhalt des Displays zeigen
		Item weraProduct = null;
		Collection colWeraProductSet = (Collection) ((WeraProductSetinSet) weraProductSet).getWeraProductSetDataComponents();
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
			if ( m_wm.getVisibilityForCatalog( weraProduct, m_weraCatalogVersion, false ) == -1 ) {

				String strTmpCode = (String) m_wm.getAttribute(weraProduct, "code");
				System.out.println( strTmpCode + ", skipped, product not allowd for current catalogversion");
				continue;
			}

			// --- verpackunseinheit hole
			final Integer intWeraproductsetVPE = (Integer) m_wm.getAttribute((Item)cContainer, "vpe");
			//LOG.info("intWeraproductsetVPE=" + intWeraproductsetVPE.toString() );

			// --- prefix
			if ( strParamPrefix != null ) {
				strPrefix	= intWeraproductsetVPE.toString() + strParamPrefix + " ";
			}

			// --- Text-ELEMENT BLT_DISPLAYSET aufbauen
			Element textXML	= _setDisplayTitleKatalog( weraProduct, allLanguages, new Integer(m_iOffset), "BLT_DISPLAYSET", m_strLanguage, strPrefix );
			oTexList.addContent(textXML);
			m_iOffset++;

			// --- Metadata
			Element oMetaData = new Element("MetaData");
			if ( first ) {

				textXML.setAttribute("sis_code", m_firstcodeNr );
				textXML.setAttribute("sis_ean", strEan );
				textXML.setAttribute("sis_vpe", intWeraproductsetVPE.toString() );
				oMetaData.setAttribute("rawcode", m_firstcodeNr );
				oMetaData.setAttribute("ean", strEan );
				oMetaData.setAttribute("vpe", intWeraproductsetVPE.toString() );
				first = false;

			} else {

				textXML.setAttribute("sis_code", "" );
				textXML.setAttribute("sis_ean", "" );
				textXML.setAttribute("sis_vpe", "" );
				oMetaData.setAttribute("rawcode", "" );
				oMetaData.setAttribute("vpe", intWeraproductsetVPE.toString() );
				oMetaData.setAttribute("ean", "" );
			}
			textXML.addContent (oMetaData);

		}
		//LOG.info("Produktexport Display-Darstellung localized (ende)");

	}

	/**
	 * BLT_DISPLAYSET: Display Produkttitle hinzufügen Katalogversion
	 *
	 * @param Item product
	 * @param Collection<String> languages
	 * @param Object iID
	 * @param String strLinkType
	 * @param Element oTexList
	 * @param String strExportLanguage
	 * @param String strPrefix
	 * @return Element containerXML
	 */
	protected Element _setDisplayTitleKatalog(Item product, Collection<String> languages, Object iID, String strLinkType, String strExportLanguage, String strPrefix ) {

		// --- preset
		String strCode			= (String) m_wm.getAttribute(product, "code");
		String strLagerNr		= (String) m_wm.getAttribute(product, "lagerNr");
		String strVariantenNr	= (String) m_wm.getAttribute(product, "variantenNr");

		// --- weravariante
		WeraProduct oBaseProduct = null;
		if ( product instanceof WeraVariante ) {
			oBaseProduct = (WeraProduct) m_wm.getAttribute(product, "baseproduct");
		}

		// --- Element aufbauen
		Element containerXML = new Element("Text");
		containerXML.setAttribute("Id", iID.toString() );
		containerXML.setAttribute("Type", strLinkType);
		containerXML.setAttribute("LinkType", strLinkType );

		// --- schleife über alle sprachen
		for (String language : languages) {

			// --- preset
			String strProduktName	= null;

			// --- sprache setzen
			m_wm.SetLanguage(language);

			// --- bei WeraVarinaten Abmessung holen
			if ( product instanceof WeraVariante ) {
				Map<String, String> hCode2NewShopTitle = oBaseProduct.getDWHInfoForVariants(language, "new_shop_title" );
				strProduktName = hCode2NewShopTitle.get(strLagerNr + strCode + strVariantenNr);

// System.out.println(language + ":-------------------:" + strCode + " ist variante - get new_shop_title >" + strProduktName );
			}

			// --- produktname holen
			if ( strProduktName == null) {
				strProduktName = (String) m_wm.getAttribute(product, "name");
// System.out.println(language + ":-------------------:" + strCode + " fallback - get name >" + strProduktName );
			}

			/* Anpassung 10.01.24 GT 
			// --- bei WeraVarinaten Abmessung holen
			if ( product instanceof WeraVariante ) {

				strProduktName = (String) m_wm.getAttribute(product, "name");
			}

			// --- produktname holen
			if ( strProduktName == null) {
				strProduktName = (String) m_wm.getAttribute(product, "artikelnr_index");
			}
			 Anpassung 10.01.24 GT */
			if (strProduktName == null) {
				strProduktName = "??";
			}

			// --- <TextBlock Language="de">Title</TextBlock>
			Element inhaltXML = new Element("TextBlock");
			inhaltXML.setAttribute("Language", language);
			if ( strPrefix == null )
				inhaltXML.addContent( strProduktName);
			else
				inhaltXML.addContent( strPrefix + strProduktName);
			containerXML.addContent(inhaltXML);

		} // for (String language : Collection<String> languages) {

		// --- sprache zurücksetzen
		m_wm.SetLanguage(strExportLanguage);

		return containerXML;
	}


	/**
	 * Inhalt der S�tze (Satz in Satz)
	 * Display ohne Einzelbestückung
	 *
	 * @param weraProductSet
	 * @param oTexList
	 */
	protected void _createContentElementSetinSet_noContent (final WeraProductSetinSet weraProductSet, final Element oTexList) {

		// --- Initialize
		WeraMedia oMediaIcon1 = null;
		WeraMedia oMediaIcon2 = null;
		Element oMetaData = null;
		boolean bIsNextSet = true;
		String strPrevArtnr = "";
		final int iPos = 0;
		int iOrder = 0;
		HashSet setCode = new HashSet();
		boolean firstElement	= true;

		// --- preset
		Integer contentQuantity = (Integer) m_wm.getAttribute(weraProductSet,"contentQuantity");
		if ( contentQuantity == null ) { contentQuantity = new Integer(1); }
		String strVpeSiS	= contentQuantity.toString();
		String strArtnrSiS = (String) m_wm.getAttribute(weraProductSet,"artnr");
		if ( strArtnrSiS == null ) { strArtnrSiS = ""; }
		String strVarnrSiS = (String) m_wm.getAttribute(weraProductSet,"variantennr");
		if ( strVarnrSiS == null ) { strVarnrSiS = ""; }
		String strLagernrSiS = (String) m_wm.getAttribute(weraProductSet,"lagernr");
		if ( strLagernrSiS == null ) { strLagernrSiS = ""; }
		final String strCodeSiS = strLagernrSiS + strArtnrSiS + strVarnrSiS;
		// --- EAN
		String strEANrSiS	= "";
		if (m_strLanguage.equals("us-en") || m_strLanguage.equals("us-es") || m_strLanguage.equals("us-fr")) {
			strEANrSiS = (String) m_wm.getAttribute(weraProductSet,"ean_us");
		} else {
			strEANrSiS = (String) m_wm.getAttribute(weraProductSet,"ean");
		}
		if (strEANrSiS == null || strEANrSiS.length() == 0 || strEANrSiS.length() < 8) {
			strEANrSiS = "";
		} else {
			strEANrSiS = strEANrSiS.substring(7);
		}

		// --- Debug
//		LOG.info ("s._createContentElementSetinSet_noContent, getCode()=" + weraProduct.getCode() );
//		LOG.info ("s._createContentElementSetinSet_noContent getName=" + weraProduct.getName() );
//		LOG.info ("s._createContentElementSetinSet_noContent strCodeSiS=" + strCodeSiS );

		// --- Initialisiere den Content f�r Satz in Satz
		weraProductSet.generateWeraProductSetData();


		// --- erster Eintrag Header ---------------------------------------------
		oMetaData = new Element("Metadata");
		oMetaData.addContent( "Code" );
		oMetaData.setAttribute("rawcode",  "Code" );
		oMetaData.setAttribute("ean", "4013288" );
		oMetaData.setAttribute("vpe", "" );
		final Element textXMLohneContent1 = _createTextElement( "BLT_SETHEADER", "", -1, m_iOffset, "BLT_SETHEADER", false, "", 0, "", false );
		oTexList.addContent(textXMLohneContent1);
		textXMLohneContent1.addContent( oMetaData );

		// --- VPE-Icon
		final Element oMotive = new Element("Motive");
		textXMLohneContent1.addContent( oMotive );
		oMotive.setAttribute("Id", "" );
		oMotive.setAttribute("Type", "HEADER_IMAGE");
		oMotive.setAttribute("LinkType", "HEADER_IMAGE");
		oMotive.setAttribute("RefCode", "minikatalog-verpackung.eps_1" );
		oMotive.setAttribute("TypeName", "pictures/minikatalog-verpackung.eps" );
		oMotive.setAttribute("LinkTypeName", "" );
		oMotive.setAttribute("Name", "pictures/minikatalog-verpackung.eps" );
		final Element oFileList = new Element("File-List");
		oMotive.addContent(oFileList);
		final Element oFile = new Element("File");
		oFileList.addContent(oFile);
		oFile.setAttribute("PrintFileName", "pictures/minikatalog-verpackung.eps" );

		m_iOffset++;
		// --- erster Eintrag Header ---------------------------------------------

		// --- Hyperlink CodeNr
		if ( !strCodeSiS.contains("??") ) {
			m_firstcodeNr = strCodeSiS;
		}


		// --- Element anlegen
		oMetaData = new Element("Metadata");
		oMetaData.setAttribute("rawcode", strCodeSiS );
		oMetaData.setAttribute("vpe", strVpeSiS );
		oMetaData.setAttribute("ean", strEANrSiS );

		// --- Zusammenhalten einer Zeile
		// --- Textobjekt anlegen
		// System.out.println("_createContentElementSetinSet_noContent.strName=" + strName);
		Element textXML = _createTextElement(weraProductSet.getCode(), "", iOrder, m_iOffset, "BLT_SET", false, "", 0, "", false);
		textXML.setAttribute("sis_code", strCodeSiS );
		textXML.setAttribute("sis_ean", strEANrSiS );
		textXML.setAttribute("sis_vpe", strVpeSiS );

		// --- nächstes Element
		m_iOffset++;
		oTexList.addContent(textXML);


		// --- SatzInSatz Info
		textXML.addContent(oMetaData);


		// --- Schleife �ber alle Daten
		for (final Iterator iterProductSetinSet = weraProductSet.m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {

			// --- N�chster Satz
			final HashMap productHash = (HashMap) iterProductSetinSet.next();
			// LOG.info("+++++productHash=" + productHash);


			// --- Initialize ---------------------------------------------------------------------------
			// --- codenr
			String strArtnr = (String) productHash.get("artnr");
			if ( strArtnr == null ) { strArtnr = ""; }
			String strVarnr = (String) productHash.get("varnr");
			if ( strVarnr == null ) { strVarnr = ""; }
			String strLagernr = (String) productHash.get("lagernr");
			if ( strLagernr == null ) { strLagernr = ""; }
			String strName = (String) productHash.get("name");
			if ( strName == null ) { strName = ""; }
			String strArtikelNr = (String) productHash.get("code");
			if ( strArtikelNr == null ) { strArtikelNr = ""; }
			final String strCode = strLagernr + strArtnr + strVarnr;
/*
LOG.info("################################" );
LOG.info("_createContentElementSetinSet_noContent.strCode=" + strCode );
LOG.info("_createContentElementSetinSet_noContent.strName=" + strName );
*/


			// --- meta-data
			String strEan = "";
			String strVpe = "";
			// --- VPE / EAN
			if (m_strLanguage.equals("us-en") || m_strLanguage.equals("us-es") || m_strLanguage.equals("us-fr")) {
				strEan = (String) productHash.get("ean_us");
			} else {
				strEan = (String) productHash.get("ean");
			}
			if (strEan == null || strEan.length() == 0 || strEan.length() < 8) {
				strEan = "";
			} else {
				strEan = strEan.substring(7);
			}
			strVpe = (String) productHash.get("vpe");
			if (strVpe == null) {
				strVpe	= "";
			}
			// --- Element anlegen
			oMetaData = new Element("Metadata");
			oMetaData.setAttribute("rawcode", strCode );
			oMetaData.setAttribute("vpe", strVpe );
			oMetaData.setAttribute("ean", strEan );

			// --- Zusammenhalten einer Zeile
			// --- Textobjekt anlegen
			// System.out.println("_createContentElementSetinSet_noContent.strName=" + strName);
			textXML = _createTextElement(strArtikelNr, strVpe + " x " + strName, iOrder, m_iOffset, "BLT_SET", false, "", 0, "", false);
			textXML.setAttribute("sis_code", "" );
			textXML.setAttribute("sis_ean", "" );
			textXML.setAttribute("sis_vpe", "" );

			// --- nächstes Element
			m_iOffset++;
			oTexList.addContent(textXML);

////////////////////////////////////////////////////////////////////////
			System.out.println("Produktexport Titelliste localized");
			// --- preset
			Collection<String> allLanguages = new ArrayList();
			allLanguages.addAll(m_coAdditionallLanguages);
			allLanguages.add(m_strLanguage);

			// --- schleife über alle sprachen
			for (String strLanguage : m_coAdditionallLanguages) {
				System.out.println("Produktexport Titelliste localized - " + strLanguage);

				// --- sprache setzen
				m_wm.SetLanguage(strLanguage);

				// --- Sprach element anlegen
				Element oMetadata_localized = new Element("Metadata_localized");
				oMetadata_localized.setAttribute( "language", strLanguage );

				// --- produktname holen
				String strProduktName = (String) m_wm.getAttribute(weraProductSet, "name");
				if (strProduktName == null) {
					strProduktName = "??";
				}
				oMetadata_localized.addContent( strVpe + " x " + strProduktName);

				// --- SatzInSatz Info
				textXML.addContent(oMetadata_localized);
			}

			// --- sprache zurücksetzen
			m_wm.SetLanguage(m_strLanguage);
////////////////////////////////////////////////////////////////////////


			// --- SatzInSatz Info
			textXML.addContent(oMetaData);


		} // --- for (final Iterator iterProductSetinSet = m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {

		// --- Debug
		// LOG.info ("e._createContentElementSetinSet_noContent="+weraProduct.getCode() );
	}


	// ---------------------------------------------------------------------------------------------------
	// --- optional, zur Zeit nur WebExport -------------------------------------------------------------- 
	// ---

	/**
	 * Marketingtexte (optional, zur Zeit nur WebExport)
	 *
	 * @param String strDescription2
	 * @param Element oTextList
     * @return
     */
	public Element createMarketingTextElement(String strDescription2, Element oTextList) {
		return null;
	}

	/**
	 * Weblink-List (optional, zur Zeit nur WebExport)
	 *
	 * @param Element produktXML
	 * @param WeraProduct weraProduct
     * @return
     */
	public Element createWeblinkList(Element produktXML, WeraProduct weraProduct) {
		return null;
	}

	/**
	 * Hyperlink-List
	 *
	 * @param Element oTextList
	 * @param WeraProduct weraProduct
     * @return
     */
	public Element createHyperlinkList(Element oTextList, String codenr) {


		if ( codenr == "" || codenr.contains("??") || codenr.length() != 11 ) {

			// --- Keine Hyperlinks generieren
			LOG.error("+++++ Keine Hyperlinks vorhanden!");

		} else {

			// --- offset erhöhen
			m_iOffset++;

			// --- Element aufbauen
			final Element textXML = new Element("Text");
			oTextList.addContent(textXML);
			textXML.setAttribute("Id",  new Integer(m_iOffset).toString() );
			textXML.setAttribute("Type", "BLT_HYPERLINK");
			textXML.setAttribute("LinkType", "BLT_HYPERLINK");

			/*
			// --- schleife über alle sprachen
			// --- hole f�r alle Ausgabesprache (Multilayer - Texte -------------------------------
			Collection<String> allLanguages = new ArrayList();
			allLanguages.addAll(m_coAdditionallLanguages);
			allLanguages.add(m_strLanguage);
			for (String strLanguage : allLanguages) {
				final Element textBlockXML = new Element("TextBlock");
				textXML.addContent(textBlockXML);

 				// --- sprache setzen
				textBlockXML.setAttribute("Language", "interactive_" + strLanguage);

				// --- mapping für links
				if ( strLanguage.equals("jp") ) {
					strLanguage = "ja";
				}

				textBlockXML.setText( "https://www.wera.de/" + strLanguage + "/" + codenr );
			}
*/
			// textBlockXML.setText( "https://www.wera.de/" + strLanguage + "/" + codenr );
			// --- Hyperlinks nur noch ohne sprachkürzel
			final Element textBlockXML = new Element("TextBlock");
			textXML.addContent(textBlockXML);
			textBlockXML.setAttribute("Language", "de" );
			textBlockXML.setText( "https://www.wera.de/" + codenr );
		}

		return oTextList;
	}

	// --- Crosslink-List (optional, zur Zeit nur WebExport)
	public Element createCrosslinkList(Element produktXML, WeraProduct weraProduct) {
		return null;
	}

	// --- External-Image-List (optional, zur Zeit nur WebExport)
	public Element createExternalImageList(Element produktXML, WeraProduct weraProduct) {
		return null;
	}

	// --- optional, zur Zeit nur WebExport -------------------------------------------------------------- 
	// ---------------------------------------------------------------------------------------------------
	protected Element createTextSchlagworte(final WeraProduct weraProduct, final Element oTexList) {
		/*            
		 // --- Initialize
		 int iPos = 0;
		 Element textXML = null;

		 // --- Schleife �ber alle Eintr�ge
		 final Collection keywords = (Collection) getAttribute(weraProduct, "keywords");
		 //output("normalized-keywords.length="+keywords.size(),2);
		 Keyword keyworditem = null;
		 //ProfiClassKeywordItem keyworditem = null;
		 for (final Iterator it1 = keywords.iterator(); it1.hasNext();)
		 {
		 // --- Get value
		 iPos++;
		 keyworditem = (Keyword) it1.next();

		 // --- Generate Entry
		 final String keyword = (String) getAttribute(keyworditem, "keyword");
		 if (m_bTextwechsel || (m_bTextwechsel == false && keyword.trim().length() > 0))
		 {
		 textXML = _createTextElement(" ", keyword.trim(), new Integer(iPos), new Integer(m_iOffset), "BLT_AINDEX", true);
		 m_iOffset++;
		 oTexList.addContent(textXML);
		 // --- Schlagwortz�hler
		 m_iCntSchlagworte++;
		 }
		 }

		 // --- Mind. 1 Schlagwort anlegen f�r Textwechsel
		 if (m_bTextwechsel && (keywords == null || keywords.size() == 0))
		 {

		 // --- Generate Entry
		 textXML = _createTextElement(" ", " ", new Integer(1), new Integer(m_iOffset), "BLT_AINDEX", true);
		 m_iOffset++;
		 oTexList.addContent(textXML);

		 // --- Schlagwortz�hler
		 m_iCntSchlagworte++;
		 }
		 */
		return oTexList;
	}

	/**
	 * 
	 * @param strTagContent
	 * @return 
	 */
	protected String prepareForLineSplit(String strTagContent) {
		/* strong auf b mappen und leere b knotes raus */
		strTagContent = strTagContent.replaceAll("<strong>", "<b>");
		strTagContent = strTagContent.replaceAll("</strong>", "</b>");
		strTagContent = strTagContent.replaceAll("<B>", "<b>");
		strTagContent = strTagContent.replaceAll("</B>", "</b>");
		strTagContent = strTagContent.replaceAll("<b></b>", "");

		/* br normalisieren */
		strTagContent = normalizeBR (strTagContent);

		return strTagContent;
	}

	/**
	 * BR normalisieren
	 * 
	 * @param strTagContent
	 * @return 
	 */
	protected String normalizeBR(String strTagContent) {

		/* br normalisieren */
		if ( strTagContent == null ) {
			return "";
		}
		
		// --- handle br
		strTagContent = strTagContent.replaceAll("<BR", "<br");
		strTagContent = strTagContent.replaceAll("<bR", "<br");
		strTagContent = strTagContent.replaceAll("<Br", "<br");
		strTagContent = strTagContent.replaceAll("<br />", "<br/>");
		strTagContent = strTagContent.replaceAll("<br  />", "<br/>");
		strTagContent = strTagContent.replaceAll("<br>", "<br/>");

		return strTagContent;
	}

	protected Collection genTextList(String strTagContent) {
		// --- Initialize
		final Collection colResult = new ArrayList();
		final boolean bLeftElement = false;
		String strTypeName = "";

		/* Content s�ubern und nach <br/> splitten */
		strTagContent = prepareForLineSplit(strTagContent);
		final String aList[] = strTagContent.split("<br/>");

		// --- Schleife �ber alle Eintr�ge
		boolean bOutput = false;
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
					strTypeName = "";
					strTagContent = aElements[0].trim();
					if (strTagContent.length() > 0) {
						bOutput = true;
					}
				}
			}

			if (bOutput) {

				// --- Gibt es ein linkes Tabellenelement=
				strTypeName = strTypeName.replaceAll("<b>", "");
				strTagContent = strTagContent.replaceAll("<b>", "");
				if (strTypeName.length() > 0) {
					colResult.add(strTypeName);
				}
				if (strTagContent.length() > 0) {
					colResult.add(strTagContent);
				}
			}
		}

		return colResult;
	}

	/**
	 * create one Text-Element of default-export language
	 *
	 * @param hashTagContents
	 * @param oTexList
	 * @param strType
	 * @return
	 */
	protected Element createMultiLayerTextElement(HashMap<String, String> hashTagContents, final Element oTexList, String strType) {

		if (hashTagContents.containsKey(m_strLanguage)) {
			return createTextElement(hashTagContents.get(m_strLanguage), oTexList, strType);
		} else {
			return oTexList;
		}
	}


	/**
	 * Export Preisdaten
	 * 
	 * @param Element oAttributeList
	 * @param WeraVariante article
	 * @param int iOrder
	 * @return int
	 */
	protected int initMerkmalPriceData (final Element oAttributeList, Product article, int iOrder) {
		//LOG.info("#####produktexport=" );
		return iOrder++;
	}

	/**
	 *
	 * @param strTagContent
	 * @param oTexList
	 * @param strType
	 * @return
	 */
	protected Element createTextElement(String strTagContent, final Element oTexList, String strType) {
		// --- <Text Id="16336" Type="BLT_Extra" LinkType="BLT_Extra" TypeName="Extra" LinkTypeName="Extra" Sequence="10">
		//LOG.info("s.createTextElement.strTagContent=" + strTagContent);

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

				// --- Textz�hler
				m_iCntText++;

				// --- Pr�fe auf Br�che
//				if ((strTagContent.contains("\"") || strTagContent.contains("&quot;")) && strTagContent.contains("/")) {
				strTagContent = _genBruch(strTagContent);
//				}

				textXML = _createTextElement(strTypeName, strTagContent, new Integer(iPos), new Integer(m_iOffset), strType, true, "", 0, "", false );
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

		// --- Content
		strTagContent = prepareForLineSplit(strTagContent);
		Element textXML = null;
		textXML = _createTextElement(strTypeName, strTagContent, new Integer(0), new Integer(m_iOffset), strType, true, "", 0, "", false );
		oTexList.addContent(textXML);
		m_iOffset++;

		// --- Flag f�r ein linkes Tabellenelement setzen
		m_bLeftElement = bLeftElement;

		//LOG.info("e.createTextElement.length=" + strTagContent);
		return oTexList;
	}

	/**
	 * Zoll-Zeichen angleichen
	 *
	 * @param strTagContent
	 * @return
	 */
	protected String _NormalizeInchCharacter(String strTagContent) {

		// --- Initialize
		// --- Pr�fe Eingabe
		if (strTagContent != null && !strTagContent.equals("")) {

			// --- Quote-Zeichen angleichen
			strTagContent = strTagContent.replaceAll("\u201C", "\"");
			strTagContent = strTagContent.replaceAll("\u201D", "\"");
			strTagContent = strTagContent.replaceAll("\u201E", "\"");
			strTagContent = strTagContent.replaceAll("\u201F", "\"");
			strTagContent = strTagContent.replaceAll("&quot;", "\"");

		}

		return strTagContent;
	}

	/**
	 * Hoch- / Tiefstellung von Br�chen (Inbetween - Marken)
	 *
	 * @param strTagContent
	 * @return
	 */
	protected String _genBruch(String strTagContent) {

		// --- Initialize
		String strResult = "";

		// --- Pr�fe Eingabe
		if (strTagContent != null && !strTagContent.equals("")) {

			// --- Quote-Zeichen angleichen
			strTagContent = _NormalizeInchCharacter(strTagContent);

			// --- Initialize Tags gem�sse Ausgabekanal
			final String strSupS = m_oExportFormatter.formatSetSupS();
			final String strSupE = m_oExportFormatter.formatSetSupE();
			final String strSubS = m_oExportFormatter.formatSetSubS();
			final String strSubE = m_oExportFormatter.formatSetSubE();

			// --- Reg Pattern
			// System.out.println("~~~~~~~~~~_genBruch - vorher:"+strTagContent);
			final String strSearchPattern = "(\\d+)/(\\d+)\"";
			final String strReplacePattern = strSupS + "$1" + strSupE + "/" + strSubS + "$2" + strSubE + "\"";

			// --- Hole InputString
			strResult = strTagContent.trim().replaceAll(strSearchPattern, strReplacePattern);
			// System.out.println("~~~~~~~~~~_genBruch - nachher:"+strResult);
		}

		return strResult;
	}

	/**
	 * Hoch- / Tiefstellung von Br�chen (Indesign - Marken)
	 *
	 * @param strTagContent
	 * @return
	 */
	protected String _genBruchInDesign(String strTagContent) {

		// --- Initialize
		String strResult = "";

		// --- Pr�fe Eingabe
		if (strTagContent != null && !strTagContent.equals("")) {

			// --- Quote-Zeichen angleichen
			strTagContent = _NormalizeInchCharacter(strTagContent);

			// --- Initialize Tags gem�sse Ausgabekanal
			final String strSupS = m_oExportFormatter.formatSetSupS();
			final String strSupE = m_oExportFormatter.formatSetSupE();
			final String strSubS = m_oExportFormatter.formatSetSubS();
			final String strSubE = m_oExportFormatter.formatSetSubE();

			// --- Reg Pattern
			final String strSearchPattern = "(\\d+)/(\\d+)\"";
			// System.out.println("~~~~~~~~~~_genBruchInDesign - vorher:"+strTagContent);
			final String strReplacePattern = "<cNoBreak:1><cPosition:Superscript>$1<cNoBreak:><cPosition:><cNoBreak:1>"
					+ "/"
					+ "<cNoBreak:><cNoBreak:1><cPosition:Subscript>$2<cNoBreak:><cPosition:><cNoBreak:1><0x22>";
			// --- Hole InputString
			strResult = strTagContent.trim().replaceAll(strSearchPattern, strReplacePattern);
			// System.out.println("~~~~~~~~~~_genBruchInDesign - nachher:"+strResult);
		}

		return strResult;
	}

	/*
	 * protected String __genBruch(String strTagContent) { // TODO Auto-generated method stub String strResult = "";
	 * //LOG.info("s._genBruch="+strTagContent); strTagContent = strTagContent.replace("\"","&quot;");
	 * 
	 * final String[] sStrings = strTagContent.split("/"); if ( sStrings.length > 1 ) { String strString1 = sStrings[0];
	 * String strString2 = ""; String strString3 = ""; for ( int iPos=1; iPos < sStrings.length; iPos++ ){
	 * 
	 * // --- Hole String strString2 = sStrings[iPos];
	 * 
	 * // --- Teilstring auswerten if ( strString2.contains("&quot;") ) {
	 * 
	 * final String[] aElements = strString1.split(" "); strString3 = ""; for ( int iCnt=0; iCnt < aElements.length;
	 * iCnt++ ) { if ( iCnt == (aElements.length-1) ) { boolean bHasFormated=false; char strTempStr[] =
	 * aElements[iCnt].toCharArray(); strString3 += " "; for ( int iPos1=0; iPos1 < strTempStr.length; iPos1++ ) { if (
	 * bHasFormated == false && strTempStr[iPos1] >= 48 && strTempStr[iPos1] <= 57 ) { strString3 +=
	 * m_oExportFormatter.formatSetSupS() + strTempStr[iPos1]; bHasFormated = true; } else strString3 +=
	 * strTempStr[iPos1]; } // strString3 += " " + strPreString + m_oExportFormatter.formatSetSupS() + strPostString; }
	 * else { strString3 += " " + aElements[iCnt]; //LOG.info("genBruch.strString3="+strString3); }
	 * 
	 * }// --- for ( int iCnt=0; iCnt < aElements.length; iCnt++ ) strString1 = strString3.substring(1);
	 * //LOG.info("genBruch.strString1="+strString1);
	 * 
	 * // --- Zusammenbau strString2 = strString2.replaceFirst("&quot;",m_oExportFormatter.formatSetSubE() + "&quot;");
	 * //LOG.info("genBruch.strString2="+strString2); strString1 = strString1 + m_oExportFormatter.formatSetSupE() + "/"
	 * + m_oExportFormatter.formatSetSubS() + strString2; //LOG.info("genBruch.strString1="+strString1);
	 * 
	 * } // --- if ( strString2.contains("&quot;") ) { else { // --- Kein Bruch in diesem Teil vorhanden strString1 =
	 * strString1 + "/" + strString2; //LOG.info("genBruch.Kein Bruch in diesem Teil vorhanden="+strString1); }
	 * 
	 * // --- Reststring strResult = strString1;
	 * 
	 * } // --- for ( int iPos=0; iPos < sStrings; iPos++ ){ }
	 * 
	 * if ( strResult == "" ) { strResult=strTagContent; }
	 * 
	 * //LOG.info("e._genBruch="+strResult); return strResult; }
	 */
	/**
	 *
	 * @param strTagName
	 * @param weramedia
	 * @param strPrio
	 * @param strDirectory
	 * @param strCodeNrProduct
	 * @return
	 */
	protected Element createBildElement(final String strTagName, final WeraMedia weramedia, final String strPrio,
			final String strDirectory, final String strCodeNrProduct) {
		final Element oMotive = new Element("Motive");

		// --- Initialize
		Double dBildRahmenBreiteDelta	= new Double(0);
		Double dBildRahmenBreiteP		= new Double(100.0);
		String strCode = "";
		String strRealName = "";
		if (weramedia != null) {
			strCode = weramedia.getCode();
			strRealName = weramedia.getRealFileName();
			if (strRealName == null ) { strRealName	= ""; }
			if ( strTagName.contains("FEATRUREICONS") ) {
				strRealName = strRealName.replaceAll(".jpg", ".eps");
				strRealName = strRealName.replaceAll(".gif", ".eps");
			} else {
				strRealName = strRealName.replaceAll(".eps", ".jpg");
			}

			// --- hole rahmenbreite in %
			dBildRahmenBreiteP = (Double) m_wm.getAttribute(weramedia, "rahmenbreite_in_percent");
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
			} else {
				dBildRahmenBreiteP	= new Double(0.0);
			}
		}

		// --- Neues Element anlegen
		// --- <Motive Id="0" Type="ATTRIBUTE_IMAGE" LinkType="ATTRIBUTE_IMAGE" RefCode="ATTR_aussen_sechskant_durchmesser" Thumbnail="" Sequence="0" TypeName="ATTRIBUTE_IMAGE" LinkTypeName="ATTRIBUTE_IMAGE" Name="Leertext" Keywords="">
		oMotive.setAttribute("Id", strCode);
		oMotive.setAttribute("TypeName", strTagName);
		oMotive.setAttribute("Sequence", strPrio);
		oMotive.setAttribute("WidthP", dBildRahmenBreiteDelta.toString());
		//oMotive.setAttribute("RefCode",      strCode );
		//oMotive.setAttribute("Type",         strTagName );
		//oMotive.setAttribute("LinkType",     strTagName );
		//oMotive.setAttribute("Thumbnail",    "" );
		//oMotive.setAttribute("LinkTypeName", strTagName );
		//oMotive.setAttribute("Name",         strRealName );
		//oMotive.setAttribute("Keywords",     "" );

		// --- <File-List>
		final Element oFileList = new Element("File-List");

		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		if (strTagName.equals("PICTURE1")) {
				
			// --- produktbild merken
			m_hashProdukbilder.put(strCode, "\tPICTURE1\t" + dBildRahmenBreiteP.toString() );
			
			oFileList.addContent(_initImage(weramedia, strDirectory, strCodeNrProduct, true, strTagName ));
		} else {
			oFileList.addContent(_initImage(weramedia, strDirectory, strCodeNrProduct, false, strTagName));
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
			m_iOffset++;
			oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + strLocation ) );
			oFileList.addContent( oFile );
			
			// --- Bild merken
			m_hashProdukbilder.put(strLocation, "\tPICTURE2\t" + dBildRahmenBreiteP.toString() );
			
		} else {
			
			final Element oFile = new Element("File");
			m_iOffset++;
			oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + strLocation ) );
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
	 * @param strOrder
	 * @return
	 */
	protected Element createOrderElement(final String strOrder) {
		final Element orderXML = new Element("REIHENFOLGE");
		orderXML.setAttribute("ID", "PRIO");
		orderXML.setAttribute("NAME", strOrder);

		return orderXML;
	}

	// protected Element createTreegroupElement(final String strId, final String strType, final String strCatalogversion )
	/**
	 *
	 * @param strId
	 * @param strType
	 * @param strCatalogversion
	 * @param hashAttributes
	 * @return
	 */
	protected Element createTreegroupElement(final String strId, final String strType, final String strCatalogversion, HashMap<String, String> hashAttributes) {

		final Element oTreegroup = new Element("Treegroup");
		oTreegroup.setAttribute("Id", strId);
		oTreegroup.setAttribute("Type", strType);
		oTreegroup.setAttribute("RefCode", strCatalogversion);
		//oTreegroup.setAttribute("Workflow",     "GEPRUEFFT" );
		oTreegroup.setAttribute("TypeName", strType);
		oTreegroup.setAttribute("Name", strCatalogversion);
		//oTreegroup.setAttribute("WorkflowName", "GEPRUEFFT" );

		// --- additional attributes
		if (hashAttributes != null) {

			for (String strAttrName : hashAttributes.keySet()) {
				String strAttrValue = hashAttributes.get(strAttrName);
				oTreegroup.setAttribute(strAttrName, strAttrValue);
			}
		}

		return oTreegroup;
	}

	/**
	 * Datenkorrektur der tschechischen �bersetzungen
	 *
	 * @param strData
	 * @return
	 */
	protected String _corrDataUS_trademarks(final String strData) {

		// --- Initialize
		String strResult = strData;
		if (strResult != null) {
			strResult = strResult.replaceAll("Bit-Check\u00AE", "Bit-Check");
			strResult = strResult.replaceAll("Bit-Checks\u00AE", "Bit-Checks");
			strResult = strResult.replaceAll("Bit-Safe\u00AE", "Bit-Safe");
			strResult = strResult.replaceAll("Bit-Safes\u00AE", "Bit-Safes");
			strResult = strResult.replaceAll("Kraftform Kompakt\u00AE", "Kraftform Kompakt");
			strResult = strResult.replaceAll("BlackLaser\u00AE", "BlackLaser");
			strResult = strResult.replaceAll("Kraftform Micro\u00AE", "Kraftform\u00AE Micro");
			strResult = strResult.replaceAll("Weralit\u00AE", "Weralit");
		} else {
			strResult = "";
		}

		return strResult;
	}

	/**
	 * Hole alle Fussnoten
	 *
	 * @param strExportPathInp
	 * @param strLanguage
	 * @param bRemoveTrademarks
	 */
	public void exportAllFootnotes(final String strExportPathInp, final String strLanguage, final boolean bRemoveTrademarks) {

		// --- Initialze
		String strText = "";
		String strLine = "";
		Footnote footnote = null;
		ArrayList aCSV = new ArrayList();

		// --- Header setzen
		aCSV.add("'ID';'" + strLanguage + "';'translate_text'");

		// --- Hole alle Fussnoten
		final Set footnotes = m_wm.getAllFootnotes();

		// --- Schleife �ber alle Ergebniss
		for (final Iterator it1 = footnotes.iterator(); it1.hasNext();) {

			// --- Hole Produkt
			footnote = (Footnote) it1.next();
			try {
				strLine = "'" + footnote.getAttribute("code") + "'";
				m_wm.SetLanguage(strLanguage);
				strText = (String) footnote.getLocalizedProperty("name");
				LOG.info("Fussnote=" + footnote.getAttribute("code"));
				LOG.info("+++ de=" + strText);
				strLine += ";'" + strText + "'";

				// --- at next line
				if (bRemoveTrademarks) {
					aCSV.add(_corrDataUS_trademarks(strLine));
				} else {
					aCSV.add(strLine);
				}

			} catch (final JaloInvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArray(aCSV, strExportPathInp + "/footnotes.csv");
		oSupport = null;
		aCSV.clear();
		aCSV = null;
	}

	/**
	 * Hole alle Keywords
	 *
	 * @param strExportPathInp
	 * @param strLanguage
	 * @param bRemoveTrademarks
	 */
	public void exportAllKeywords(final String strExportPathInp, final String strLanguage, final boolean bRemoveTrademarks) {

		// --- Initialze
		String strText = "";
		String strLine = "";
		Keyword keyword1 = null;
		ArrayList aCSV = new ArrayList();

		// --- Header setzen
		aCSV.add("'ID';'" + strLanguage + "';'translate_text'");

		// --- Hole alle Fussnoten
		final Set keywords = m_wm.getAllKeyword();

		// --- Schleife �ber alle Ergebniss
		for (final Iterator it1 = keywords.iterator(); it1.hasNext();) {

			// --- Hole Produkt
			keyword1 = (Keyword) it1.next();
			try {
				// --- Sprachfilter
				if (keyword1.getLanguage().equals(m_wm.SetLanguage(strLanguage))) {
					// --- Filter f�r Werakatalog
					if (keyword1.getCatalogVersion().equals(m_weraCatalogVersion)) {
						strLine = "'" + keyword1.getAttribute("pk") + "'";
						strText = (String) keyword1.getAttribute("keyword");
						LOG.info("keyword1=" + keyword1.getAttribute("pk"));
						LOG.info("+++=" + strText);
						strLine += ";'" + strText + "'";

						// --- at next line
						if (bRemoveTrademarks) {
							aCSV.add(_corrDataUS_trademarks(strLine));
						} else {
							aCSV.add(strLine);
						}
					}
				}

			} catch (final JaloInvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (final JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArray(aCSV, strExportPathInp + "/keywords.csv");
		oSupport = null;
		aCSV.clear();
		aCSV = null;

	}

	/**
	 *
	 * @param strExportPathInp
	 * @param colCategories
	 * @param strLanguage
	 * @param strCatalog
	 * @param strCatalogversion
	 * @param bRemoveTrademarks
	 * @return
	 */
	public String CategoryExportCSV(final String strExportPathInp, final Collection colCategories, final String strLanguage,
			final String strCatalog, final String strCatalogversion, final boolean bRemoveTrademarks) {

		// --- Debug
		LOG.info("CategoryExportCSV.bRemoveTrademarks" + bRemoveTrademarks);

		// --- Initialize
		String categorystring = null;
		String strTmp = null;
		Category oAktCategory = null;
		m_strCatalog = strCatalog;
		m_strCatalogversion = strCatalogversion;
		String strExportPath = "";
		String strFileDatum = (new Date()).toLocaleString();
		strFileDatum = strFileDatum.replace(" ", "_");
		strFileDatum = strFileDatum.replace(":", "");
		strFileDatum = strFileDatum.replace(".", "");
		strFileDatum = strFileDatum.substring(4, 8) + strFileDatum.substring(2, 4) + strFileDatum.substring(0, 2)
				+ strFileDatum.substring(8);
		String strLine = "\"category\";\"" + strLanguage + "_text1\";\"translation_text1\";\"" + strLanguage
				+ "_text2\";\"translation_text2\";";
		ArrayList aOutput = new ArrayList();
		final String strBlankCol = "\"\";";

		// --- First we add a header
		aOutput.add(strLine);

		// --- First off all we have to login
		if (bLogin()) {

			// --- Set language
			m_wm.SetLanguage(strLanguage);

			// --- Erzeuge die Export-Ablage
			strFileDatum = strLanguage + "_" + "csv_" + strFileDatum;
			if (strExportPathInp == null) {
				strExportPath = Config.getParameter("wera.homepath") + "/export/" + strFileDatum;
				LOG.info("mkdir()= " + m_wm.createDirectory(strExportPath));
			} else {
				strExportPath = strExportPathInp;
			}

			// --- Schleife �ber alle Categorien
			for (final Iterator it1 = colCategories.iterator(); it1.hasNext();) {
				// --- get it
				categorystring = (String) it1.next();

				// --- Hole die Kategorie und pr�fe, ob Produkte enthalten sind
				oAktCategory = CatalogManager.getInstance().getCatalogCategory(m_weraCatalogVersion, categorystring);
				if (oAktCategory == null) {
					LOG.info("+++Category skipped =>" + categorystring);
					continue;
				} else {
					LOG.info("+++Category export =>" + categorystring);
					strLine = "\"" + oAktCategory.getCode() + "\";";
					strTmp = (String) m_wm.getAttribute(oAktCategory, "untertitel");
					if (strTmp == null) {
						strTmp = "";
					}
					if (bRemoveTrademarks) {
						strLine += "\"" + _corrDataUS_trademarks(oAktCategory.getName()) + "\";" + strBlankCol;
						strLine += "\"" + _corrDataUS_trademarks(strTmp) + "\";";
					} else {
						strLine += "\"" + oAktCategory.getName() + "\";" + strBlankCol;
						strLine += "\"" + strTmp + "\";";
					}

					// --- at next line
					if (bRemoveTrademarks) {
						aOutput.add(_corrDataUS_trademarks(strLine));
					} else {
						aOutput.add(strLine);
					}
				}

			} // ---- for (Iterator it1=colCategories.iterator(); it1.hasNext()) {
		} // --- 		if (bLogin()) {

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArray(aOutput, strExportPath + "/category.csv");
		oSupport = null;
		aOutput.clear();
		aOutput = null;

		return strExportPath;
	}

	/**
	 *
	 * @param strExportPathInp
	 * @param colCategories
	 * @param strLanguage
	 * @param strCatalog
	 * @param strCatalogversion
	 * @param bRemoveTrademarks
	 * @return
	 */
	public String ProduktExportCSV(final String strExportPathInp, final Collection colCategories, final String strLanguage,
			final String strCatalog, final String strCatalogversion, final boolean bRemoveTrademarks) {

		// --- Debug
		LOG.info("ProduktExportCSV.bRemoveTrademarks" + bRemoveTrademarks);

		// --- Initialize
		WeraMedia weramedia = null;
		String strRealName;
		final int iAnzahlText = 0;
		final String strBlankCol = "\"\";";
		final Collection colTestList = new ArrayList();
		String categorystring = null;
		String strTmp = null;
		Category oAktCategory = null;
		WeraProduct oAktProduct = null;
		m_strCatalog = strCatalog;
		m_strCatalogversion = strCatalogversion;
		String strExportPath = "";
		String strFileDatum = (new Date()).toLocaleString();
		strFileDatum = strFileDatum.replace(" ", "_");
		strFileDatum = strFileDatum.replace(":", "");
		strFileDatum = strFileDatum.replace(".", "");
		strFileDatum = strFileDatum.substring(4, 8) + strFileDatum.substring(2, 4) + strFileDatum.substring(0, 2)
				+ strFileDatum.substring(8);
		ArrayList aOutput = new ArrayList();


		// --- First we add a header
		String strLine = "product\t";
		strLine += "titel_de\ttitel_en\ttitel_fr\ttitel_es\ttitel_it\ttitel_pl\ttitel_cz\ttitel_ru\t";
		strLine += "besch_de\tbesch_en\tbesch_fr\tbesch_es\tbesch_it\tbesch_pl\tbesch_cz\tbesch_ru\t";
		aOutput.add(strLine);

		// --- First off all we have to login
		if (bLogin()) {

			// --- Set language
			m_wm.SetLanguage(strLanguage);

			// --- Erzeuge die Export-Ablage
			strFileDatum = strLanguage + "_" + "csv_" + strFileDatum;
			if (strExportPathInp == null) {
				strExportPath = Config.getParameter("wera.homepath") + "/export/" + strFileDatum;
				LOG.info("mkdir()= " + m_wm.createDirectory(strExportPath));
			} else {
				strExportPath = strExportPathInp;
			}

			// --- Schleife �ber alle Categorien
			for (final Iterator it1 = colCategories.iterator(); it1.hasNext();) {
				// --- get it
				categorystring = (String) it1.next();

				// --- Hole die Kategorie und pr�fe, ob Produkte enthalten sind
				oAktCategory = CatalogManager.getInstance().getCatalogCategory(m_weraCatalogVersion, categorystring);

				if (oAktCategory == null) {
					LOG.info("+++Category skipped =>" + categorystring);
					continue;
				} else {
					// --- Hole alle aktiven Produkte der aktuellen Kategorie
					final Collection productsSorted = m_wm.getProducts(oAktCategory);
					LOG.info("+Category export =>" + oAktCategory.getCode());

					// --- Schleife �ber alle Produkte
					for (final Iterator it2 = productsSorted.iterator(); it2.hasNext();) {
						// --- get it
						oAktProduct = (WeraProduct) it2.next();
						LOG.info("+++Product export =>" + oAktProduct.getCode());

						// --- Here we go
						strLine = oAktProduct.getCode() + "\t";

						// --- Set language (DE)
						m_wm.SetLanguage("de");
						strTmp = oAktProduct.getName();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Set language (EN)
						m_wm.SetLanguage("en");
						strTmp = oAktProduct.getName();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Set language (FR)
						m_wm.SetLanguage("fr");
						strTmp = oAktProduct.getName();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Set language (ES)
						m_wm.SetLanguage("es");
						strTmp = oAktProduct.getName();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Set language (IT)
						m_wm.SetLanguage("it");
						strTmp = oAktProduct.getName();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Set language (PL)
						m_wm.SetLanguage("pl");
						strTmp = oAktProduct.getName();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Set language (CS)
						m_wm.SetLanguage("cs");
						strTmp = oAktProduct.getName();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Set language (RU)
						m_wm.SetLanguage("ru");
						strTmp = oAktProduct.getName();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}

						// --- Produktbezeichnung (DE)
						m_wm.SetLanguage("de");
						strTmp = oAktProduct.getDescription1();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Produktbezeichnung (EN)
						m_wm.SetLanguage("en");
						strTmp = oAktProduct.getDescription1();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Produktbezeichnung (FR)
						m_wm.SetLanguage("fr");
						strTmp = oAktProduct.getDescription1();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Produktbezeichnung (ES)
						m_wm.SetLanguage("es");
						strTmp = oAktProduct.getDescription1();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Produktbezeichnung (IT)
						m_wm.SetLanguage("it");
						strTmp = oAktProduct.getDescription1();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Produktbezeichnung (PL)
						m_wm.SetLanguage("pl");
						strTmp = oAktProduct.getDescription1();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Produktbezeichnung (CS)
						m_wm.SetLanguage("cs");
						strTmp = oAktProduct.getDescription1();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}
						// --- Produktbezeichnung (RU)
						m_wm.SetLanguage("ru");
						strTmp = oAktProduct.getDescription1();
						if (strTmp != null) {
							strLine += strTmp.replaceAll("\t", "").replaceAll("\r", "").replaceAll("\n", "") + "\t";
						} else {
							strLine += "\t";
						}

						// --- Produktabbildung
						strRealName = "kein Bild";
						weramedia = m_wm._getPicture(oAktProduct, "pictures1");
						if (weramedia != null) {
							strRealName = weramedia.getRealFileName();
							if (strRealName != null) {
								strRealName = strRealName.replaceAll(".jpg", ".eps");
								strRealName = strRealName.replaceAll(".gif", ".eps");
							}
						}
						strLine += strRealName + "\t";


						/*
						 * if ( strTmp != null ) {
						 * 
						 * // --- Aufteilen der Texte colTestList.addAll( genTextList ( strTmp ) ); if ( iAnzahlText <
						 * colTestList.size() ) iAnzahlText = colTestList.size(); // --- Loop for ( Iterator
						 * it3=colTestList.iterator(); it3.hasNext(); ) { // --- get it strTmp = (String) it3.next(); strLine
						 * += "\"" + strTmp + "\";"; } colTestList.clear(); }
						 */
						// --- at next line
						if (bRemoveTrademarks) {
							aOutput.add(_corrDataUS_trademarks(strLine));
						} else {
							aOutput.add(strLine);
						}
					}
				}

			} // ---- for (Iterator it1=colCategories.iterator(); it1.hasNext()) {
		} // --- 		if (bLogin()) {

		// --- Schreiben des Headers
		strTmp = "";
		/*
		 * for ( int iCnt=1; iCnt <= iAnzahlText; iCnt++ ) {
		 * 
		 * // --- Generiere Textzeile strTmp += "\"" + strLanguage + "_text" + new Integer(iCnt).toString() + "\";";
		 * strTmp += "\"" + "translation_text" + new Integer(iCnt).toString() + "\";";
		 * 
		 * } // --- for ( int iCnt=0; iCnt < iAnzahlText; iCnt++ ) {
		 */

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArrayEncoding(aOutput, strExportPath + "/" + strCatalogversion + "_products.txt", "UTF-8");
		oSupport = null;
		aOutput.clear();
		aOutput = null;

		return strExportPath;
	}

	/**
	 *
	 * @param strExportPathInp
	 * @param colCategories
	 * @param strLanguage
	 * @param strCatalog
	 * @param strCatalogversion
	 * @param bRemoveTrademarks
	 * @return
	 */
	public String ProduktExportCSV_katalog(final String strExportPathInp, final Collection colCategories,
			final String strLanguage, final String strCatalog, final String strCatalogversion, final boolean bRemoveTrademarks) {

		// --- Debug
		LOG.info("ProduktExportCSV.bRemoveTrademarks" + bRemoveTrademarks);

		// --- Initialize
		final int iAnzahlText = 0;
		String strCode;
		final String strBlankCol = "\"\";";
		final Collection colTestList = new ArrayList();
		String categorystring = null;
		String strTmp = null;
		Category oAktCategory = null;
		WeraProduct oAktProduct = null;
		Collection articles = null;
		Product m_article = null;
		m_strCatalog = strCatalog;
		m_strCatalogversion = strCatalogversion;
		String strExportPath = "";
		String strFileDatum = (new Date()).toLocaleString();
		strFileDatum = strFileDatum.replace(" ", "_");
		strFileDatum = strFileDatum.replace(":", "");
		strFileDatum = strFileDatum.replace(".", "");
		strFileDatum = strFileDatum.substring(4, 8) + strFileDatum.substring(2, 4) + strFileDatum.substring(0, 2)
				+ strFileDatum.substring(8);
		ArrayList aOutput = new ArrayList();
		final WeraManager wm = WeraManager.getInstance();

		// --- First we add a header
		String strLine = "\"product\";\"";
		aOutput.add(strLine);

		// --- First off all we have to login
		if (bLogin()) {

			// --- Set language
			m_wm.SetLanguage(strLanguage);

			// --- Erzeuge die Export-Ablage
			strFileDatum = strLanguage + "_" + "csv_" + strFileDatum;
			if (strExportPathInp == null) {
				strExportPath = Config.getParameter("wera.homepath") + "/export/" + strFileDatum;
				LOG.info("mkdir()= " + m_wm.createDirectory(strExportPath));
			} else {
				strExportPath = strExportPathInp;
			}

			// --- Schleife �ber alle Categorien
			for (final Iterator it1 = colCategories.iterator(); it1.hasNext();) {
				// --- get it
				categorystring = (String) it1.next();

				// --- Hole die Kategorie und pr�fe, ob Produkte enthalten sind
				oAktCategory = CatalogManager.getInstance().getCatalogCategory(m_weraCatalogVersion, categorystring);

				if (oAktCategory == null) {
					LOG.info("+++Category skipped =>" + categorystring);
					continue;
				} else {
					// --- Hole alle aktiven Produkte der aktuellen Kategorie
					final Collection productsSorted = m_wm.getProducts(oAktCategory);
					LOG.info("+Category export =>" + oAktCategory.getCode());

					// --- Schleife �ber alle Produkte
					for (final Iterator it2 = productsSorted.iterator(); it2.hasNext();) {
						// --- get it
						oAktProduct = (WeraProduct) it2.next();
						//--------						
						LOG.info("+++Product export =>" + oAktProduct.getCode());
						try {
							final String strName = oAktProduct.getName();

							if (oAktProduct instanceof WeraProductSet) {

								// --- Schreiben der Artikeldaten
								strCode = (String) oAktProduct.getAttribute("lagerNr") + (String) oAktProduct.getAttribute("artnr")
										+ (String) oAktProduct.getAttribute("variantenNr");
								strLine = "\"" + (String) oAktProduct.getAttribute("code") + "\";\"" + strCode + "\";\"" + strName + "\"";

								// --- at next line
								aOutput.add(strLine);
							} else {

								// --- Hole alle akitven Varianten des Products
								articles = (oAktProduct).getVarianten();
								//articles = ((WeraProduct) oAktProduct).getVariants();
								for (final Iterator it3 = articles.iterator(); it3.hasNext();) {
									m_article = (WeraVariante) it3.next();

									// --- Schreiben der Artikeldaten
									strCode = (String) m_article.getAttribute("lagerNr") + (String) m_article.getAttribute("code")
											+ (String) m_article.getAttribute("variantenNr");
									strLine = "\"" + (String) oAktProduct.getAttribute("code") + "\";\"" + strCode + "\";\"" + strName
											+ "\"";

									// --- at next line
									aOutput.add(strLine);
								}

								// --- Aufr�umen
								articles.clear();
								articles = null;
							}

						} catch (final Exception e) {
							e.printStackTrace();
						}

						//--------						
					}
				}

			} // ---- for (Iterator it1=colCategories.iterator(); it1.hasNext()) {
		} // --- 		if (bLogin()) {

		// --- Schreiben des Headers
		strTmp = "";
		for (int iCnt = 1; iCnt <= iAnzahlText; iCnt++) {

			// --- Generiere Textzeile
			strTmp += "\"" + strLanguage + "_text" + new Integer(iCnt).toString() + "\";";
			strTmp += "\"" + "translation_text" + new Integer(iCnt).toString() + "\";";

		} // --- for ( int iCnt=0; iCnt < iAnzahlText; iCnt++ ) {

		// --- Now we replace the first Line
		strLine = "\"product\";\"" + strTmp;
		aOutput.set(0, strLine);

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArray(aOutput, strExportPath + "/products.csv");
		oSupport = null;
		aOutput.clear();
		aOutput = null;

		return strExportPath;
	}

	/**
	 *
	 * @param strFileDatum
	 * @param strLanguage
	 * @return
	 */
	public String CreateOutputPath(String strFileDatum, final String strLanguage) {

		LOG.info("CreateOutputPath.FileDatum=" + strFileDatum);
		strFileDatum = m_oExportFormatter.formatFileDatum(strLanguage + "_", strFileDatum);
		final String strPath1 = Config.getParameter("wera.homepath") + "/export/katalog/" + m_strCatalogversion.toLowerCase();
		final String strPath2 = strPath1 + "/katalog_" + m_strCatalogversion.toLowerCase() + "_" + strFileDatum;
		LOG.info("mkdir()= " + m_wm.createDirectory(strPath1));
		LOG.info("mkdir()= " + m_wm.createDirectory(strPath2));

		return strPath2;
	}

	/**
	 *
	 * @param colCategories
	 * @param strLanguage
	 * @param strCatalog
	 * @param strCatalogversion
	 * @param strXmlPath
	 * @param bTextwechsel
	 * @return
	 */
	public String ProduktExport(final Collection colCategories, String strLanguage, final String strCatalog,
			final String strCatalogversion, String strXmlPath, final boolean bTextwechsel) {
		// --- debug
		LOG.info("ProduktExport() called.");
		LOG.info("ProduktExport, strCatalog=" + strCatalog + ", strCatalogversion=" + strCatalogversion);

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

			// --- get all products
//                        this.__colAllProductsByCatalogVersion = (Collection)m_weraCatalogVersion.getAllProducts();
//			LOG.info("Anzahl Produkte f�r Export = " + this.__colAllProductsByCatalogVersion.size());
			// --- Erzeuge die Export-Ablage
			strXmlPath = CreateOutputPath(strFileDatum, strLanguage);

			// --- TExtbausteine einlesen
			LOG.info( "Textbausteine einlesen ... "  );
			final ComposedType TextbausteineType = TypeManager.getInstance().getComposedType(Textbaustein.class);
			final Collection<Textbaustein> Textbausteine = (Collection<Textbaustein>)TextbausteineType.getAllInstances();
			for ( Textbaustein textbaustein : Textbausteine) {
				m_hashTextbausteine.put( (String)textbaustein.getCode(), textbaustein);
			}

			// --- Schleife �ber alle Kategorien
			LOG.info("Anzahl Kategorien f�r Export =" + colCategories.size());
			for (final Iterator it1 = colCategories.iterator(); it1.hasNext();) {
				// --- ClassAttributeAssignments Cache freigeben
				m_weraclassificationhelper.ResetCache();

				// --- Hole Kategorie
				strCategory = (String) it1.next();
				LOG.info("strCategory=" + strCategory);

				// --- Hole die Kategorie und pr�fe, ob Produkte enthalten sind
				oAktCategory = CatalogManager.getInstance().getCatalogCategory(m_weraCatalogVersion, strCategory);
				m_oCategoryWera = oAktCategory;
				LOG.info("oAktCategory=" + oAktCategory);
				//oAktCategory = CategoryManager.getInstance().getCategoryByCode(strCategory);
				//Collection colCategories1 = CategoryManager.getInstance().getCategoriesByCode(strCategory);
				//oAktCategory = null;
				//for ( Iterator itCat=colCategories1.iterator(); itCat.hasNext(); ) {
				//	Category oCategory = (Category) itCat.next();
				//	CatalogVersion catalogVersion = (CatalogVersion)getAttribute(oCategory,"catalogVersion");
				//	LOG.info("oCategory.catalogVersion="+ catalogVersion.getVersion() + ", strCatalogversion="+strCatalogversion);
				//	if ( catalogVersion != null && catalogVersion.getVersion().equals(strCatalogversion) ) {
				//		oAktCategory = oCategory;
				//	    break;
				//	}
				//}
				//oAktCategory = m_weraCatalogVersion.getCategory(strCategory);
				//LOG.info("oAktCategory=" + oAktCategory);
				if (oAktCategory == null) {
					LOG.info("++Abbruch=> oAktCategory=" + oAktCategory);
					continue;
				}

				/*
				 * // --- Pr�fe ob Unterkatorien vorhanden sind try { categories = m_wm.getCategories(m_strCatalog,
				 * m_strCatalogversion, strCategory); } catch (JaloInvalidParameterException e) { // TODO Auto-generated
				 * catch block e.printStackTrace(); } catch (JaloSecurityException e) { // TODO Auto-generated catch block
				 * e.printStackTrace(); }
				 */
				// --- Setze Sprache auf DE
				//    		    	Language oLanguage = m_jaloSession.getC2LManager().getLanguageByIsoCode( "de" );
				//    				m_jaloSession.getSessionContext().setLanguage(oLanguage);
				// --- Doit
				LOG.info("oAktCategory=" + oAktCategory);
				final boolean bCheckForActivationOld = m_wm.m_bCheckForActivation;
				m_wm.m_bCheckForActivation = false;
				final Collection products = m_wm.getProductsOrderedByCategory(oAktCategory);
				m_wm.m_bCheckForActivation = bCheckForActivationOld;
				LOG.info("m_wm.m_bCheckForActivation=" + m_wm.m_bCheckForActivation);
				//Collection products = m_wm.getProducts(oAktCategory);
				if (products != null && products.size() > 0) {

					// --- Erzeuge Collection mit der zu exportierenden Category
					colCategory2Export.clear();
					colCategory2Export.add(strCategory);

					// --- Schleife �ber alle Sprachen
					for (final Iterator itLang = alSprachen.iterator(); itLang.hasNext();) {

						// --- Hole Sprachen und aktiviere sie.
						strLanguage = (String) itLang.next();
						m_strLanguage = strLanguage;
						LOG.info("Sprache=" + m_strLanguage);
						
						// --- Keine Unterkategorien, dann ausgeben
						_CategoryExport (colCategory2Export, strLanguage, strCatalog, strCatalogversion, strXmlPath, iPos);
						
						// --- Keine Unterkategorien, dann ausgeben
						_ProduktExport(colCategory2Export, strLanguage, strCatalog, strCatalogversion, strXmlPath, iPos);
						
						// --- next
						iPos++;

					} // --- for (Iterator itLang = alSprachen.iterator(); itLang.hasNext();) {

				}
			}

			// --- Summe Index
			LOG.info("m_CountProductGes[de]=" + m_CountProductGes.get("de"));
			LOG.info("m_CountProductGes[de]=" + m_CountProductGes.get("ru"));
			LOG.info("m_CountProductGes[en]=" + m_CountProductGes.get("en"));
			LOG.info("m_CountProductGes[fr]=" + m_CountProductGes.get("fr"));
			LOG.info("m_CountProductGes[es]=" + m_CountProductGes.get("es"));
			LOG.info("m_CountProductGes[it]=" + m_CountProductGes.get("it"));
			LOG.info("m_CountProductGes[cs]=" + m_CountProductGes.get("cs"));
			LOG.info("m_CountProductGes[cn]=" + m_CountProductGes.get("cn"));
			LOG.info("m_CountProductGes[pl]=" + m_CountProductGes.get("pl"));
			LOG.info("m_CountProductGes[us-en]=" + m_CountProductGes.get("us-en"));

			LOG.info("m_CntSchlagworteGes[de]=" + m_CntSchlagworteGes.get("de"));
			LOG.info("m_CntSchlagworteGes[de]=" + m_CntSchlagworteGes.get("ru"));
			LOG.info("m_CntSchlagworteGes[en]=" + m_CntSchlagworteGes.get("en"));
			LOG.info("m_CntSchlagworteGes[fr]=" + m_CntSchlagworteGes.get("fr"));
			LOG.info("m_CntSchlagworteGes[es]=" + m_CntSchlagworteGes.get("es"));
			LOG.info("m_CntSchlagworteGes[it]=" + m_CntSchlagworteGes.get("it"));
			LOG.info("m_CntSchlagworteGes[cs]=" + m_CntSchlagworteGes.get("cs"));
			LOG.info("m_CntSchlagworteGes[cn]=" + m_CntSchlagworteGes.get("cn"));
			LOG.info("m_CntSchlagworteGes[pl]=" + m_CntSchlagworteGes.get("pl"));
			LOG.info("m_CntSchlagworteGes[us-en]=" + m_CntSchlagworteGes.get("us-en"));

			// --- Aufr�umen
			colCategory2Export.clear();
			//colCategories.clear();

			// --- Logfile schreiben
			MediandoXml oSupport = new MediandoXml();

			// oSupport._WriteFileFromArray(m_aLogList,"/home/hybris/export/export.log");
			oSupport._WriteFileFromArray(m_aLogList, strXmlPath + "/export_log.txt");

			oSupport = null;
			m_aLogList.clear();
			m_aLogList = null;

			// --- Now we can logout of hybris, Hurra!
			// Logout();
		}

		// --- Vorbereitene Arbeiten abh�ngig von der Ausgabe
		doPostJob(m_xmlFileList);
		m_xmlFileList.clear();

		// --- Aufr�umen
		System.gc();

		return strResultPath;
	}

	/**
	 * dummy filter products
	 *
	 * @param oWeraProduct
	 * @return boolean
	 */
	protected boolean _isFilteredProduct(WeraProduct oWeraProduct) {
		return false;
	}

	/**
	 * filter varianten
	 *
	 * @param oWeraVariante
	 * @return boolean
	 */
	protected boolean _isFilteredVariante(WeraVariante oWeraVariante) {
		return false;
	}


	/**
	 * Export aller Informationen zu einer Kategorie
	 * @param colCategories
	 * @param strLanguage
	 * @param strCatalog
	 * @param strCatalogversion
	 * @param strXmlPath
	 * @param iPos
	 */
	public void _CategoryExport(final Collection colCategories, final String strLanguage, final String strCatalog,
			final String strCatalogversion, String strXmlPath, final int iPos) {

		// --- Initialize
		String strXmlFile = "";
		m_strLanguage = strLanguage;
		m_colCategories.addAll(colCategories);
		final String strAktCategory = (String) colCategories.iterator().next();

		// --- Setze Sprache, und Defaultsprache=de
		final Language m_Language = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
		m_jaloSession.getSessionContext().setLanguage(m_Language);
		m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);

		// --- debug
		LOG.info("_CategoryExport() called.");
		LOG.info("_CategoryExport, strCatalog=" + strCatalog + ", strCatalogversion=" + strCatalogversion);
		LOG.info("m_Language=" + m_Language);

		
		try {
			// --- XML-Builder object
			final SAXBuilder builder = new SAXBuilder(); // parameters control validation, etc

			// --- MediaXML - Real Rootcontent "Inbetween"
			final Element root = new Element("MediaXML");
			String strLangName = m_Language.getName();
			if (strLangName == null) {
				strLangName = m_strLanguage;
			}
			root.setAttribute("Language", strLangName);

			// --- <ReferenceElements HierarchyRefCode="ONLINE_CATALOG_03/04" Type="HIERARCHY">
			final Element oReferenceElements = new Element("ReferenceElements");
			oReferenceElements.setAttribute("Language", strLangName);
			oReferenceElements.setAttribute("HierarchyRefCode", strAktCategory );
			oReferenceElements.setAttribute("Type", "CHAPTER");
			if (m_bAktionsprospekt) {
				oReferenceElements.setAttribute("Aktionsliste", "J");
			} else {
				oReferenceElements.setAttribute("Aktionsliste", "N");
			}
			root.addContent(oReferenceElements);

			// --- <Treegroup-List>
			// final Element oTreegroupList = new Element("Treegroup-List");
			// root.addContent(oTreegroupList);

			// --- <Treegroup Id="1376" Type="ROOT" RefCode="ONLINE_CATALOG_03/04" Workflow="OKE_DATEN" TypeName="ROOT" Name="ONLINE CATALOG 03/04" WorkflowName="OKE_DATEN">
			Element oTreegroup = createTreegroupElement("root", "ROOT", strCatalogversion, null);

			// --- <Referenz-List>
			final Element oReferenzList = new Element("Referenz-List");
			root.addContent(oReferenzList);

			// --- Ausgabedatum
			final String strDatum = (new Date()).toLocaleString();
			final Comment dateComment = new Comment(" Datenexport vom: " + strDatum);
			root.addContent(dateComment);

			// --- Hole alle KATEGORIE
			// --- Hole alle Oberkategorien (nur aktive)
			final Collection categories = m_wm.getCategories(strCatalog, strCatalogversion, strAktCategory);
			LOG.info("categories=" + categories.size() + ", strAktCategory=" + strAktCategory);
			final Collection colKategorieXML = new ArrayList();


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

					// --- Hole Category aus Masterkatalog
					final Category oMasterCategory = getMasterCategory(oAktCategory);

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
						hashAttributes.put("Name", _genBruch(strName));

						// --- get subname
						String strSubTitle = (String) getAttribute(oMasterCategory, "untertitel");
						if (strSubTitle == null) {
							strSubTitle = "";
						}
						hashAttributes.put("Subname", _genBruch(strSubTitle));

						// --- <Treegroup Id="2" Type="MAINCHAPTER" RefCode="01 Schraubendreher" Workflow="OKE_DATEN" TypeName="MAINCHAPTER" Name="Schraubendreher" WorkflowName="OKE_DATEN">
						// oTreegroup = createTreegroupElement(oMasterCategory.getCode(), "MAINCHAPTER", strCatalogversion, hashAttributes);
						// oTreegroupList.addContent(oTreegroup);

					} // --- for ( String strAdditionalLange : allLanguages ) {

					
					// --- Setze Sprache, und Defaultsprache=de
					Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
					m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);
					m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);
					// --- hole f�r alle Ausgabesprache (Multilayer - Texte -------------------------------

					
					// --- holen und ausgeben der Tips ----------------------------------------------------
					
					// --- preset
					int iReferenzElementID	=	0;
					m_colExportedTipps						= new ArrayList();
					m_colExportedTextbausteinBullet			= new ArrayList();
					m_colExportedTextbausteinMarketingtext	= new ArrayList();

					// --- haupttip der kategorie --------------------------------------------------------------------------------------------
					Collection<Tipp> colHauptTippRefs		= (Collection<Tipp>)m_wm.getAttribute(oMasterCategory, "haupttippref");
					if ( colHauptTippRefs != null && colHauptTippRefs.size() >  0 ) {
						
						// --- kommentar
						oReferenceElements.addContent(new Comment(" Haupttip der Kategorie "));
						
						// --- darstellen der TipLinks
						m_colExportedTipps.addAll(__generateReferenzLinks ("haupttippref", oReferenceElements, colHauptTippRefs, iReferenzElementID ) );
						
						// --- erstellen der Tips
						__generateTippsElements ("haupttippref", oReferenzList , colHauptTippRefs, iReferenzElementID );
						
						// --- TipID hochzaehlen
						iReferenzElementID	+=	colHauptTippRefs.size();
					}

					
					// --- Liste der Kategorietips holen und aufteilen in colTippRefsTeil1 = 1-6 (linke Seite) undc olTippRefsTeil1 = 7 -n (restliche)
					Collection<Tipp> colAllTippRefs			= (Collection<Tipp>)m_wm.getAttribute(oMasterCategory, "tippref");
					Collection<Tipp> colTippRefsTeil1		= new ArrayList<Tipp>();
					Collection<Tipp> colTippRefsTeil2		= new ArrayList<Tipp>();
					for ( Tipp oTipp : colAllTippRefs ) {
						if ( colTippRefsTeil1.size() < 6 ) {
							colTippRefsTeil1.add(oTipp);
						} else {
							colTippRefsTeil2.add(oTipp);
						}
					}

					
					// --- Tipp reference-groups Tip Kategorien Teil 1 (linke Seite)-----------------------------------------------------------------
					//<ReferenceLinkTipGroup LinkedRefID="G1" LinkedRefCode="TipReihe1" Sequence="000001" LinkedTypeName="tippgroupref1" />
					if ( colTippRefsTeil1 != null && colTippRefsTeil1.size() >  0 ) {
						
						// --- kommentar
						oReferenceElements.addContent(new Comment(" Tip Kategorien Teil 1 (linke Seite) "));

						// --- preset tip-export-list
						Collection<Tipp> colTippRefs	= new ArrayList<Tipp>();
						String strRefernceTyp			= "tippgroupref1";
						
						// --- schleife �ber alle elemente
						for ( Tipp oTipp : colTippRefsTeil1 ) {
								
							// --- tip merken
							colTippRefs.add ( oTipp );
							
							if ( colTippRefs.size() == 3 ) {
								
								// --- darstellen der TipLinks-referenzen
								m_colExportedTipps.addAll(__generateReferenceLinkTipGroup ( strRefernceTyp, oReferenceElements, oReferenzList, colTippRefs, iReferenzElementID ) );
								m_colExportedTipps.addAll(__generateReferenzLinks ( "tippref", oReferenceElements, colTippRefs, iReferenzElementID ) );
						
								// --- erstellen der Tips
								__generateTippsElements ( "tippref", oReferenzList, colTippRefs, iReferenzElementID );

								// --- TipID hochzaehlen
								iReferenzElementID	+=	colTippRefs.size();
								
								// --- 2. - n. Export immer "tippgroupref2"
								strRefernceTyp	= "tippgroupref2";
								
								// --- liste leeren
								colTippRefs.clear();
							}
							
						} // --- for ( Tipp oTipp : colTippRefsTeil1 ) {
						
						// --- sind noch tips zum exportieren �brig?
						if ( colTippRefs.size() > 0 ) {	
							
							// --- darstellen der TipLinks-referenzen
							m_colExportedTipps.addAll(__generateReferenceLinkTipGroup ( strRefernceTyp, oReferenceElements, oReferenzList, colTippRefs, iReferenzElementID ) );
							m_colExportedTipps.addAll(__generateReferenzLinks ( "tippref", oReferenceElements, colTippRefs, iReferenzElementID ) );

							// --- erstellen der Tips
							__generateTippsElements ( "tippref", oReferenzList, colTippRefs, iReferenzElementID );

							// --- TipID hochzaehlen
							iReferenzElementID	+=	colTippRefs.size();
						}
						
					} // --- if ( colTippRefsTeil1 != null && colTippRefsTeil1.size() >  0 ) {

					
					// --- Tip: Anwenderbild (rechts) -----------------------------------------------------------------------------------------
					Collection<Bildreferenz> colAnwenderBildRefs	= (Collection<Bildreferenz>)m_wm.getAttribute(oMasterCategory, "anwenderimageref");
					if ( colAnwenderBildRefs != null && colAnwenderBildRefs.size() >  0 ) {
						
						// --- kommentar
						oReferenceElements.addContent(new Comment(" Anwenderbild (Hero) "));
						
						// --- darstellen der BildreferenzLinks
						__generateReferenzLinks ("anwenderimageref", oReferenceElements, colAnwenderBildRefs, iReferenzElementID );
						
						// --- generate external image-lists
						__generateBildreferenzElements ( "anwenderimageref", oReferenzList, colAnwenderBildRefs, iReferenzElementID );
						
						// --- TipID hochzaehlen
						iReferenzElementID	+=	colAnwenderBildRefs.size();
					}


					
					// --- Tipp reference-groups Tip Kategorien Teil 2 (restliche) -----------------------------------------------------------
					//<ReferenceLinkTipGroup LinkedRefID="G1" LinkedRefCode="TipReihe1" Sequence="000001" LinkedTypeName="tippgroupref1" />
					if ( colTippRefsTeil2 != null && colTippRefsTeil2.size() >  0 ) {
						
						// --- kommentar
						oReferenceElements.addContent(new Comment(" Tip Kategorien Teil 2 (restliche) "));

						// --- preset tip-export-list
						Collection<Tipp> colTippRefs	= new ArrayList<Tipp>();
						String strRefernceTyp			= "tippgroupref2";
						
						// --- schleife �ber alle elemente
						for ( Tipp oTipp : colTippRefsTeil2 ) {
								
							// --- tip merken
							colTippRefs.add ( oTipp );
							
							if ( colTippRefs.size() == 3 ) {
								
								// --- darstellen der TipLinks-referenzen
								m_colExportedTipps.addAll(__generateReferenceLinkTipGroup ( strRefernceTyp, oReferenceElements, oReferenzList, colTippRefs, iReferenzElementID ) );
								m_colExportedTipps.addAll(__generateReferenzLinks ( "tippref", oReferenceElements, colTippRefs, iReferenzElementID ) );
						
								// --- erstellen der Tips
								__generateTippsElements ("tippref", oReferenzList, colTippRefs, iReferenzElementID );

								// --- TipID hochzaehlen
								iReferenzElementID	+=	colTippRefs.size();
								
								// --- liste leeren
								colTippRefs.clear();
							}
							
						} // --- for ( Tipp oTipp : colTippRefsTeil2 ) {
						
						// --- sind noch tips zum exportieren �brig?
						if ( colTippRefs.size() > 0 ) {	
							
							// --- darstellen der TipLinks-referenzen
							m_colExportedTipps.addAll(__generateReferenceLinkTipGroup ( strRefernceTyp, oReferenceElements, oReferenzList, colTippRefs, iReferenzElementID ) );
							m_colExportedTipps.addAll(__generateReferenzLinks ( "tippref", oReferenceElements, colTippRefs, iReferenzElementID ) );

							// --- erstellen der Tips
							__generateTippsElements ( "tippref", oReferenzList, colTippRefs, iReferenzElementID );

							// --- TipID hochzaehlen
							iReferenzElementID	+=	colTippRefs.size();
						}
						
					} // --- if ( colTippRefsTeil2 != null && colTippRefsTeil2.size() >  0 ) {
					
					
					// --- anzahl der Tips
					m_iCntTipps	=	iReferenzElementID + 1;
					
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
					strXmlFile = strLanguage + "_" + strXmlFilePrefix + strAktCategory + "_category.xml";

					// --- Erzeuge die Export-Ablage, falls nicht vorhanden
					strXmlPath = strXmlPath + m_oExportFormatter.formatXmlFilePrefix("/" + strXmlFilePrefix + strAktCategory);
					LOG.info("mkdir()= " + m_wm.createDirectory(strXmlPath));
					
					
				} else {
					LOG.info("+++++ERROR + Category not FOUND." + strAktCategory);
				}
			}

			// --- Zusammenfassung
			Comment dummyComment = new Comment(" Anzahl Tipps: " + m_iCntTipps);
			root.addContent(dummyComment);

			dummyComment = new Comment(" Sprache: " + m_strLanguage);
			root.addContent(dummyComment);
			output("+++Anzahl Tipps: " + m_iCntTipps, 1);
			output("+++Sprache: " + m_strLanguage, 1);

			// --- Reset Counters
			m_iCntTipps = 0; // --- Text
			output("+++Reset Counters", 1);

			// --- Document erzeugen und schreiben
			output("+++Document erzeugen und schreiben", 1);
			final Document doc = new Document(root);

			// --- DTD setzen
			output("+++DTD setzen", 1);
			final DocType xhtml = new DocType("MediaXML", "MEDIANDO.dtd");
			doc.setDocType(xhtml);

			// --- Debug
			LOG.info("Ausgabe in Datei  =" + strXmlPath + "/" + strXmlFile);
			output("Ausgabe in Datei  =" + strXmlPath + "/" + strXmlFile, 1);

			// --- Formatierung
			final XMLOutputter outp = new XMLOutputter(Format.getPrettyFormat());

			final FileOutputStream oFileOutputStream;
			oFileOutputStream = new FileOutputStream(strXmlPath + "/" + strXmlFile, false);
			outp.output(doc, oFileOutputStream);

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
	 * generates a list of tip-link elemens
	 * 
	 * @param strTippTyp
	 * @param oContainterElement
	 * @param colTippRefs 
	 * @param iTipID 
	 * @return colExportedTipps
	 */
	protected Collection<String> __generateTipLinks(String strTippTyp, Element oContainterElement, Collection<Tipp> colTippRefs, int iTipID ) {
		
		// --- initialize
		Collection<String>	colExportedTipps = new ArrayList();
		
		// --- <TipLink LinkedRefID="T0" LinkedRefCode="anderwender_code" Sequence="00000" LinkedTypeName="haupttipphref" />
		for ( Tipp oTippRef : colTippRefs ) {
			
			// --- generate tip-node
			Element oTipLink = new Element("TipLink");
			oContainterElement.addContent(oTipLink);
			
			// --- set tip-link attributes
			oTipLink.setAttribute("LinkedRefID", "I" + iTipID );
			String strTippCode	= (String) m_wm.getAttribute(oTippRef, "code");
			oTipLink.setAttribute("LinkedRefCode", strTippCode );
			oTipLink.setAttribute("Sequence", String.format("%6s", iTipID ).replace(' ', '0') );
			oTipLink.setAttribute("LinkedTypeName", strTippTyp );
			
			// --- collect exported tipps
			if ( !colExportedTipps.contains(strTippCode)) {
				colExportedTipps.add(strTippCode);
			}
			
			// --- next TipID
			iTipID++;
			
		} // --- for ( Tipp oTextbausteinRef : colTextbausteinRefs ) {
		
		
		return colExportedTipps;
	}

	/**
	 * generates a list of textbaustein-link elements
	 *
	 * @param String strPrefix
	 * @param String strLinkTyp
	 * @param String strTextbausteinTyp
	 * @param Element oContainterElement
	 * @param Collection<Textbaustein> colTextbausteinRefs
	 * @param iTextbausteinID
	 * @return Collection<String> colExportedTextbausteine
	 */
	protected Collection<String> __generateTextbausteinLinks (String strPrefix, String strLinkTyp, String strTextbausteinTyp, Element oContainterElement, Collection<Textbaustein> colTextbausteinRefs, int iTextbausteinID) {

		// --- initialize
		Collection<String>	colExportedTextbausteine = new ArrayList();

		// --- <TipLink LinkedRefID="T0" LinkedRefCode="anderwender_code" Sequence="00000" LinkedTypeName="haupttipphref" />
		for ( Textbaustein oTextbausteinRef : colTextbausteinRefs) {

			// --- generate tip-node
			Element oTextbausteinLink = new Element(strLinkTyp /* "TipLink" */ );
			oContainterElement.addContent(oTextbausteinLink);

			// --- set tip-link attributes
			oTextbausteinLink.setAttribute("LinkedRefID", strPrefix + iTextbausteinID);
			String strTextbausteinCode	= (String) m_wm.getAttribute(oTextbausteinRef, "code");
			oTextbausteinLink.setAttribute("LinkedRefCode", strTextbausteinCode );
			oTextbausteinLink.setAttribute("Sequence", String.format("%6s", iTextbausteinID).replace(' ', '0') );
			oTextbausteinLink.setAttribute("LinkedTypeName", strTextbausteinTyp);

			// --- collect exported tipps
			if ( !colExportedTextbausteine.contains(strTextbausteinCode)) {
				colExportedTextbausteine.add(strTextbausteinCode);
			}

			// --- next TipID
			iTextbausteinID++;

		} // --- for ( Tipp oTextbausteinRef : colTextbausteinRefs ) {


		return colExportedTextbausteine;
	}



	/**
	 * sammeln der Bulletpoints Textbaustein
	 *
	 * @param Collection<Textbaustein> colTextbausteinByProducts
	 */
	protected void _collectTextbausteineBulletpoints (Collection<Textbaustein> colTextbausteinByProducts) {

		// --- nur ausführen wenn daten vorhanden
		if ( m_colExportedTextbausteinBullet == null || colTextbausteinByProducts == null ) {
			return;
		}

		// --- schleife über alle gefundenen Textbausteine
		for (Textbaustein oProductTextbaustein : colTextbausteinByProducts ) {

			// --- get code des aktuellen tipps
			String strTextbausteinCode	= oProductTextbaustein.getCode();

			// --- pr�fe, ob der Textbaustein bereits exportiert wurde
			if ( !m_colExportedTextbausteinBullet.contains(strTextbausteinCode)) {

				// --- collect Textbaustein
				m_colTextbausteinBullet.add(oProductTextbaustein);

				// --- collect exported tipps
				m_colExportedTextbausteinBullet.add(strTextbausteinCode);
			}
		}

	}

	/**
	 * sammeln der Marketingtextbaustein
	 *
	 * @param Collection<Textbaustein> colTextbausteinByProducts
     */
	protected void _collectTextbausteineMarketing (Collection<Textbaustein> colTextbausteinByProducts) {

		// --- nur ausführen wenn daten vorhanden
		if ( m_colExportedTextbausteinMarketingtext == null || colTextbausteinByProducts == null ) {
			return;
		}

		// --- schleife über alle gefundenen Textbausteine
		for (Textbaustein oProductTextbaustein : colTextbausteinByProducts ) {

			// --- get code des aktuellen tipps
			String strTextbausteinCode	= oProductTextbaustein.getCode();

			// --- pr�fe, ob der Textbaustein bereits exportiert wurde
			if ( !m_colExportedTextbausteinMarketingtext.contains(strTextbausteinCode)) {

				// --- collect tipp
				m_colTextbausteinMarketingtext.add(oProductTextbaustein);

				// --- collect exported tipps
				m_colExportedTextbausteinMarketingtext.add(strTextbausteinCode);
			}
		}

	}

	/**
	 * generates a list of reference-link (Tip-Group) elemens
	 * 
	 * @param strReferenzTyp
	 * @param oContainterElement
	 * @param colReferences 
	 * @param iReferenceID 
	 * @return colExportedTipps
	 */
	protected Collection<String> __generateReferenceLinkTipGroup( String strReferenzTyp, Element oContainterElement, Element oReferenzList, 
																Collection colReferences, int iReferenceID ) {
		
		// --- initialize
		Collection<String>	colExportedTipps = new ArrayList();
		
		// --- <ReferenceLinkTipGroup LinkedRefID="G1" LinkedRefCode="TipReihe1" Sequence="000001" LinkedTypeName="tippgroupref1" />
		Element oReferenceLinkTipGroup = new Element("ReferenceLinkTipGroup");
		oContainterElement.addContent(oReferenceLinkTipGroup);
		oReferenceLinkTipGroup.setAttribute("LinkedRefID", "G" + iReferenceID );
		oReferenceLinkTipGroup.setAttribute("LinkedRefCode", "TipReihe" + iReferenceID );
		oReferenceLinkTipGroup.setAttribute("Sequence", String.format("%6s", iReferenceID ).replace(' ', '0') );
		oReferenceLinkTipGroup.setAttribute("LinkedTypeName", strReferenzTyp );
		
		// --- <TipGroup Id="G1" LinkedTypeName="tippgroupref1">
		Element oTipGroup = new Element("TipGroup");
		oReferenzList.addContent(oTipGroup);
		oTipGroup.setAttribute("Id", "G" + iReferenceID );
		oTipGroup.setAttribute("LinkedTypeName", strReferenzTyp );

		// --- schleife �ber alle tipps
		for ( Item oReferenceItem : (Collection<Item>)colReferences ) {
			
			// --- generate reference-node
			Element oTipLink = new Element("ReferenceLink");
			oTipGroup.addContent(oTipLink);
			
			// --- set tip-link attributes
			oTipLink.setAttribute("LinkedRefID", "I" + iReferenceID );
			String strCodeReferenceItem	= (String) m_wm.getAttribute(oReferenceItem, "code");
			oTipLink.setAttribute("LinkedRefCode", strCodeReferenceItem );
			oTipLink.setAttribute("Sequence", String.format("%6s", iReferenceID ).replace(' ', '0') );
			oTipLink.setAttribute("LinkedTypeName", "tippref" );
			
			// --- collect exported tipps
			if ( !colExportedTipps.contains(strCodeReferenceItem) ) {
				colExportedTipps.add(strCodeReferenceItem);
			}
			
			// --- next TipID
			iReferenceID++;
			
		} // --- for ( Item oReferenceItem : (Collection<Item>)colReferences ) {
		
		
		return colExportedTipps;
	}

	/**
	 * generates a list of reference-link elemens
	 * 
	 * @param strReferenzTyp
	 * @param oContainterElement
	 * @param colReferences 
	 * @param iReferenceID 
	 * @return colExportedTipps
	 */
	protected Collection<String> __generateReferenzLinks( String strReferenzTyp, Element oContainterElement, Collection colReferences, int iReferenceID ) {
		
		// --- initialize
		Collection<String>	colExportedTipps = new ArrayList();
		
		// --- <TipLink LinkedRefID="T0" LinkedRefCode="anderwender_code" Sequence="00000" LinkedTypeName="haupttipphref" />
		for ( Item oReferenceItem : (Collection<Item>)colReferences ) {
			
			// --- generate reference-node
			Element oTipLink = new Element("ReferenceLink");
			oContainterElement.addContent(oTipLink);
			
			// --- set tip-link attributes
			oTipLink.setAttribute("LinkedRefID", "I" + iReferenceID );
			String strCodeReferenceItem	= (String) m_wm.getAttribute(oReferenceItem, "code");
			oTipLink.setAttribute("LinkedRefCode", strCodeReferenceItem );
			oTipLink.setAttribute("Sequence", String.format("%6s", iReferenceID ).replace(' ', '0') );
			oTipLink.setAttribute("LinkedTypeName", strReferenzTyp );
			
			// --- collect exported tipps
			if ( !colExportedTipps.contains(strCodeReferenceItem)) {
				colExportedTipps.add(strCodeReferenceItem);
			}
			
			// --- next TipID
			iReferenceID++;
			
		} // --- for ( Tipp oTextbausteinRef : colTextbausteinRefs ) {
		
		
		return colExportedTipps;
	}
	
	
	/**
	 * generates a tip elemens
	 * 
	 * @param String strPreFixID
	 * @param String strTextbausteinTyp
	 * @param oContainerElement
	 * @param colTextbaustinRefs
	 * @param iTextbausteinID
	 */
	protected void __generateTextbausteinElements (String strPreFixID, String strTextbausteinTyp, Element oContainerElement, Collection colTextbaustinRefs, int iTextbausteinID) {
			
		// --- preset
		int iTextID		= 0;

		// --- generate text-list
		iTextID	= __generateTextbausteinElement ( strTextbausteinTyp, oContainerElement, colTextbaustinRefs, strPreFixID, iTextID );
	}


	/**
	 * generates a tip elemens
	 *
	 * @param strTippTyp
	 * @param oContainerElement
	 * @param colTippRefs
	 * @param iTipID
	 */
	protected void __generateTippsElements (String strTippTyp, Element oContainerElement, Collection colTippRefs, int iTipID ) {

		// --- preset
		int iMotivID	= 0;
		int iTextID		= 0;

		// --- <Tip id="T0" TipCode="tip_4711" LinkedTypeName="haupttipphref" />
		for ( Tipp oTippRef : (Collection<Tipp>)colTippRefs ) {

			// --- preset
			String strPreFixID	= "I" + iTipID;

			// --- generate tip-node
			Element oTip = new Element("Tip");
			oContainerElement.addContent(oTip);

			// --- set tip-link attributes
			oTip.setAttribute("Id", strPreFixID );
			oTip.setAttribute("TipCode", (String) m_wm.getAttribute(oTippRef, "code"));
			oTip.setAttribute("TipTypeName", strTippTyp);


			// --- Text-List -----------------------------------------------------------------------------------

			// --- generate Text-List
			Element oTextList = new Element("TipText-List");
			oTip.addContent(oTextList);

			// --- Textbaustein Teile
			Collection<Textbaustein> oTitleTextbaustein	= (Collection<Textbaustein>)m_wm.getAttribute(oTippRef, "textbausteinheadlineref");
			if ( oTitleTextbaustein != null ) {

				// --- generate text-list
				iTextID	= __generateTextbausteinElement ("headline", oTextList, oTitleTextbaustein, strPreFixID, iTextID );
			}

			// --- Textbaustein Haupttext
			Collection<Textbaustein> oTextbausteine	= (Collection<Textbaustein>)m_wm.getAttribute(oTippRef, "textbausteinref");
			if ( oTextbausteine != null ) {

				// --- generate text-list
				iTextID	= __generateTextbausteinElement ("texte", oTextList, oTextbausteine, strPreFixID, iTextID );
			}
			// --- Text-List -----------------------------------------------------------------------------------



			// --- Motiv-Lists ---------------------------------------------------------------------------

			// --- generate Motive-List
			Element oMotiveList = new Element("TipMotive-List");
			oTip.addContent(oMotiveList);

			Collection<Bildreferenz> colBildreferenzIcons			= (Collection<Bildreferenz>)m_wm.getAttribute(oTippRef, "iconref");
			if ( colBildreferenzIcons != null && colBildreferenzIcons.size() >  0 ) {

				// --- generate external image-lists
				iMotivID	= __generateMotiveElement ( "ICON", oMotiveList, colBildreferenzIcons, strPreFixID, iMotivID );
			}
			Collection<Bildreferenz> colBildreferenzImages			= (Collection<Bildreferenz>)m_wm.getAttribute(oTippRef, "imageref");
			if ( colBildreferenzImages != null && colBildreferenzImages.size() >  0 ) {

				// --- generate external image-lists
				iMotivID	= __generateMotiveElement ( "PICTURE", oMotiveList, colBildreferenzImages, strPreFixID, iMotivID );
			}
			// --- Motiv-Lists ---------------------------------------------------------------------------

			// --- incremtent iTextbausteinID
			iTipID++;

		} // --- for ( Tipp oTextbausteinRef : colTextbausteinRefs ) {
	}

	
	/**
	 * generates a list of Bildreferenz elemens
	 * 
	 * @param strBildreferenceTyp
	 * @param oContainerElement
	 * @param colBildreferences
	 * @param iBildreferenceID 
	 */
	protected void __generateBildreferenzElements (String strBildreferenceTyp, Element oContainerElement, Collection<Bildreferenz> colBildreferences, int iBildreferenceID ) {
			
		// --- preset
		int iTextID				= 0;
		
		// --- <Bildreference id="T0" BildreferenceCode="tip_4711" BildreferenceTypeName="haupttipphref" />
		for ( Bildreferenz oBildreference : colBildreferences ) {
			
			// --- preset
			String strPreFixID	= "I" + iBildreferenceID;

			// --- generate tip-node
			Element oNodeBildreference = new Element("Bildreference");
			oContainerElement.addContent(oNodeBildreference);
			
			// --- set tip-link attributes
			oNodeBildreference.setAttribute("Id", strPreFixID );
			oNodeBildreference.setAttribute("BildreferenceCode", (String) m_wm.getAttribute(oBildreference, "code"));
			oNodeBildreference.setAttribute("BildreferenceTypeName", strBildreferenceTyp);

			
			// --- Text-List (Title) ----------------------------------------------------------------------------------
			// --- get all title-elements
			Collection<Textbaustein> oTitleTextbaustein	= (Collection<Textbaustein>)m_wm.getAttribute(oBildreference, "title");
			if ( oTitleTextbaustein != null && oTitleTextbaustein.size() > 0 ) {
				
				Element oTextList = new Element("BildreferenceText-List");
				oNodeBildreference.addContent(oTextList);

				// --- Title
				if ( oTitleTextbaustein != null ) {

					iTextID	= __generateTextbausteinElement ("title", oTextList, oTitleTextbaustein, strPreFixID, iTextID );
				}
				
			} // --- if ( oTitleTextbaustein != null && oTitleTextbaustein.size() > 0 ) {
			// --- Text-List (Title) ----------------------------------------------------------------------------------

			
			// --- File-List ------------------------------------------------------------------------------------------
			Element oFileList = new Element("BildreferenceFile-List");
			oNodeBildreference.addContent(oFileList);

			// --- File
			Element oFile = new Element("File");
			oFileList.addContent(oFile);
			String strLocation = (String)getAttribute(oBildreference, "location");
			oFile.setAttribute("PrintFileName", "pictures/" + strLocation );
			// --- File-List ------------------------------------------------------------------------------------------


			// --- incremtent iBildreferenceID
			iBildreferenceID++;
			
		} // --- for ( Bildreferenz oBildreference : colBildreferences ) {
	}

	
	/**
	 * generates a list of text-elemens (Textbaustein)
	 * 
	 * @param strTextTyp
	 * @param oContainerElement
	 * @param colTextbausteinRefs
	 * @param strPrefixID
	 * @param iTextID
	 * @return 
	 */
	protected int __generateTextbausteinElement (String strTextTyp, Element oContainerElement, Collection<Textbaustein> colTextbausteinRefs, String strPrefixID, int iTextID ) {
		
		
		// --- <TipLink LinkedRefCode="T0" Sequence="00001" LinkedTypeName="haupttipphref" />
		for ( Textbaustein oTextbaustein : colTextbausteinRefs ) {
			
			// --- preset
			String strPrefixTextID	= strPrefixID +  "_T" + iTextID;

			// --- generate tip-node
			Element oText = new Element("TextbausteinText");
			oContainerElement.addContent(oText);

			// --- set tip-link attributes
			oText.setAttribute("Id", strPrefixTextID );
			oText.setAttribute("TextCode", (String) m_wm.getAttribute(oTextbaustein, "code"));
			oText.setAttribute("TextTypeName", strTextTyp);

			// --- hole f�r alle Ausgabesprache (Multilayer - Texte -------------------------------
			Collection<String> allLanguages = new ArrayList();
			allLanguages.addAll(m_coAdditionallLanguages);
			allLanguages.add(m_strLanguage);
			for (String strLanguageTmp : allLanguages) {

				// --- Setze Sprache, und Defaultsprache=de
				Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(strLanguageTmp);
				m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);

				// --- <TextBlock Language="de">text</TextBlock>
				Element oTextBlock = new Element("TextbausteinTextBlock");
				oText.addContent(oTextBlock);
				
				// --- set attributess
				oTextBlock.setAttribute("Language", strLanguageTmp );
				
				// --- set content
				String strText	= (String) getAttribute(oTextbaustein, "text");
				strText			= normalizeBR( strText );
				strText			= strText.replaceAll("<br>", "##BR##");
				strText			= strText.replaceAll("(\\r|\\n|\\t)", "");
				
				oTextBlock.addContent(strText);


			} // --- for ( String strAdditionalLange : allLanguages ) {

			// --- incremtent iTextID
			iTextID++;

		} // --- for ( Textbaustein oTextbaustein : colTextbausteinRefs ) {

		
		// --- Setze Sprache, und Defaultsprache=de
		Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
		m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);
		m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);
		
		return iTextID;
	}


	/**
	 * generates a list of text-elemens (Textbaustein) f�r Bulltetpoints
	 *
	 * @param strTextTyp
	 * @param oContainerElement
	 * @param colTextbausteinRefs
	 * @param strPrefixID
	 * @param iTextID
	 * @return
	 */
	protected int __generateTextbausteinBulletpointElementModel (String strTextTyp, Element oContainerElement, Collection<TextbausteinModel> colTextbausteinRefs, String strPrefixID, int iTextID ) {

		System.out.println("+++++++++++" + colTextbausteinRefs );

		// --- <TipLink LinkedRefCode="T0" Sequence="00001" LinkedTypeName="haupttipphref" />
		for ( TextbausteinModel oTextbaustein : colTextbausteinRefs ) {
			System.out.println("+++++++++++" + oTextbaustein.getCode() );

			// --- preset
			String strPrefixTextID	= "";
			if ( strPrefixID.equals("") ) {
				strPrefixTextID	= String.valueOf(iTextID);
			} else {
				strPrefixTextID	= strPrefixID +  "_T" + iTextID;
			}

			// --- generate tip-node
			Element oText = new Element("Text");
			oContainerElement.addContent(oText);

			// --- set tip-link attributes
			oText.setAttribute("Id", strPrefixTextID );
			oText.setAttribute("TextCode", (String) oTextbaustein.getCode() );
			oText.setAttribute("LinkType", strTextTyp);

			// --- hole f�r alle Ausgabesprache (Multilayer - Texte -------------------------------
			Collection<String> allLanguages = new ArrayList();
			allLanguages.addAll(m_coAdditionallLanguages);
			allLanguages.add(m_strLanguage);
			for (String strLanguageTmp : allLanguages) {

				// --- Setze Sprache, und Defaultsprache=de
				Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(strLanguageTmp);
				m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);

				// --- <TextBlock Language="de">text</TextBlock>
				Element oTextBlock = new Element("TextBlock");
				oText.addContent(oTextBlock);

				// --- set attributess
				oTextBlock.setAttribute("Language", strLanguageTmp );

				// --- set content
				String strText	= (String) oTextbaustein.getText(); // getAttribute(oTextbaustein, "text");
				strText			= normalizeBR( strText );
				strText			= strText.replaceAll("<br>", "##BR##");
				strText			= strText.replaceAll("(\\r|\\n|\\t)", "");

				oTextBlock.addContent(strText);


			} // --- for ( String strAdditionalLange : allLanguages ) {

			// --- incremtent iTextID
			iTextID++;

		} // --- for ( Textbaustein oTextbaustein : colTextbausteinRefs ) {


		// --- Setze Sprache, und Defaultsprache=de
		Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
		m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);
		m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);

		return iTextID;
	}


	/**
	 * generates a list of text-elemens (Textbaustein) f�r Bulltetpoints
	 *
	 * @param strTextTyp
	 * @param oContainerElement
	 * @param colTextbausteinRefs
	 * @param strPrefixID
	 * @param iTextID
	 * @return
	 */
	protected int __generateTextbausteinBulletpointElement (String strTextTyp, Element oContainerElement, Collection<Textbaustein> colTextbausteinRefs, String strPrefixID, int iTextID ) {

		// --- <TipLink LinkedRefCode="T0" Sequence="00001" LinkedTypeName="haupttipphref" />
		for ( Textbaustein oTextbaustein : colTextbausteinRefs ) {

			// --- preset
			String strPrefixTextID	= "";
			if ( strPrefixID.equals("") ) {
				strPrefixTextID	= String.valueOf(iTextID);
			} else {
				strPrefixTextID	= strPrefixID +  "_T" + iTextID;
			}

			// --- generate tip-node
			Element oText = new Element("Text");
			oContainerElement.addContent(oText);

			// --- set tip-link attributes
			oText.setAttribute("Id", strPrefixTextID );
			oText.setAttribute("TextCode", (String) m_wm.getAttribute(oTextbaustein, "code"));
			oText.setAttribute("LinkType", strTextTyp);

			// --- hole f�r alle Ausgabesprache (Multilayer - Texte -------------------------------
			Collection<String> allLanguages = new ArrayList();
			allLanguages.addAll(m_coAdditionallLanguages);
			allLanguages.add(m_strLanguage);
			for (String strLanguageTmp : allLanguages) {

				// --- Setze Sprache, und Defaultsprache=de
				Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(strLanguageTmp);
				m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);

				// --- <TextBlock Language="de">text</TextBlock>
				Element oTextBlock = new Element("TextBlock");
				oText.addContent(oTextBlock);

				// --- set attributess
				oTextBlock.setAttribute("Language", strLanguageTmp );

				// --- set content
				String strText	= (String) getAttribute(oTextbaustein, "text");
				strText			= normalizeBR( strText );
				strText			= strText.replaceAll("<br>", "##BR##");
				strText			= strText.replaceAll("(\\r|\\n|\\t)", "");
				oTextBlock.addContent(strText);


			} // --- for ( String strAdditionalLange : allLanguages ) {

			// --- incremtent iTextID
			iTextID++;

		} // --- for ( Textbaustein oTextbaustein : colTextbausteinRefs ) {


		// --- Setze Sprache, und Defaultsprache=de
		Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
		m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);
		m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);

		return iTextID;
	}

	/**
	 * generates a list of motive-elemens
	 * 
	 * @param strMotiveTyp
	 * @param oContainerElement
	 * @param colMotivItems
	 * @param iMotivID 
	 * 
	 * @return iMotivID
	 */
	protected int __generateMotiveElement ( String strMotiveTyp, Element oContainerElement, Collection<Bildreferenz> colMotivItems, String strPrefixID, int iMotivID ) {
		
		// --- initialize
		int iTextID = 0;

			
		// --- <TipLink LinkedRefCode="T0" Sequence="00001" LinkedTypeName="haupttipphref" />
		for ( Bildreferenz oBildreferenz : colMotivItems ) {
			
			// --- preset
			String strPreFixMotivID	= strPrefixID +"_M" + iMotivID;
			
			// --- generate tip-node
			Element oMotive = new Element("TipMotive");
			oContainerElement.addContent(oMotive);
			
			// --- <Motive Id="T0_M0" TypeName="PICTURE" Sequence="0" WidthP="0.0">
			// --- set tip-link attributes
			oMotive.setAttribute("Id", strPreFixMotivID );
			
			// --- bildname
			String strLocation = (String) getAttribute(oBildreferenz, "location");
			if ( strMotiveTyp.equals("ICON")) {
				strLocation	= strLocation.replaceAll(".jpg", ".eps");
			}
			
			oMotive.setAttribute("MotiveCode", strLocation );
			oMotive.setAttribute("MotiveSequence", String.format("%6s", iMotivID ).replace(' ', '0') );
			oMotive.setAttribute("MotiveTypeName", strMotiveTyp );
			oMotive.setAttribute("MotiveWidthP", "0.0");
			
			
			// --- Text-List (Title) ----------------------------------------------------------------------------------
			// --- get all title-elements
			Collection<Textbaustein> oTitleTextbaustein	= (Collection<Textbaustein>)m_wm.getAttribute(oBildreferenz, "title");
			if ( oTitleTextbaustein != null && oTitleTextbaustein.size() > 0 ) {
				
				Element oTextList = new Element("TipText-List");
				oMotive.addContent(oTextList);

				// --- Title
				if ( oTitleTextbaustein != null ) {

					iTextID	= __generateTextbausteinElement ("title", oTextList, oTitleTextbaustein, strPreFixMotivID, iTextID );
				}
				
			} // --- if ( oTitleTextbaustein != null && oTitleTextbaustein.size() > 0 ) {
			// --- Text-List (Title) ----------------------------------------------------------------------------------

			
			// --- File-List ------------------------------------------------------------------------------------------
			Element oFileList = new Element("TipFile-List");
			oMotive.addContent(oFileList);

			// --- File
			Element oFile = new Element("File");
			oFileList.addContent(oFile);
			//strLocation = (String)getAttribute(oBildreferenz, "location");
			oFile.setAttribute("PrintFileName", "pictures/" + strLocation );
			// --- File-List ------------------------------------------------------------------------------------------

			// --- incremtent MotivID
			iMotivID++;
			
		} // --- for ( Bildreferenz oBildreferenz : colMotivItems ) {
		
		
		return iMotivID;
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
	public void _ProduktExport(final Collection colCategories, final String strLanguage, final String strCatalog,
			final String strCatalogversion, String strXmlPath, final int iPos) {

		LOG.info("_ProduktExport() called.");
		// --- Initialize
		LOG.info("_ProduktExport, strCatalog=" + strCatalog + ", strCatalogversion=" + strCatalogversion);
		String strXmlFile = "";
		m_strLanguage = strLanguage;
		m_colCategories.addAll(colCategories);
		final String strAktCategory = (String) colCategories.iterator().next();

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
			if (false && categories.size() > 0) {

				// --- Schleife �ber alle Kategorien
				Category oCategory = null;
				for (final Iterator it1 = categories.iterator(); it1.hasNext();) {
					// --- Hole Kategorie
					oCategory = (Category) it1.next();

					// --- Wurde diese Kategory f�r den Export ausgew�hlt?
					if (m_colCategories.contains(oCategory.getCode())) {

						// --- Neue Kataegorie
						oTreegroupLink = new Element("TreegroupLink");
						oKatalogLnkList.addContent(oTreegroupLink);

						// --- Hole Category aus Masterkatalog
						final Category oMasterCategory = getMasterCategory(oCategory);

						/* temp. umkopieren da �bersetzertool falsch l�uft
						 // --- strCategoryName �bernehmen in Master ----------------------------
						 String strCategoryName = oCategory.getName();
						 if (strCategoryName == null)
						 oMasterCategory.setName(strCategoryName);
						 final String strCategorySubTitle = (String) getAttribute(oCategory, "untertitel");
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

							// --- get Name
							String strName = oMasterCategory.getName();
							if (strName == null) {
								strName = "";
							}
							hashAttributes.put("RefCode", strName);
							hashAttributes.put("Name", _genBruch(strName) );

							// --- get subname
							String strSubTitle = (String) getAttribute(oMasterCategory, "untertitel");
							if (strSubTitle == null) {
								strSubTitle = "";
							}
							hashAttributes.put("Subname", _genBruch(strSubTitle) );

							// --- <Treegroup Id="2" Type="MAINCHAPTER" RefCode="01 Schraubendreher" Workflow="OKE_DATEN" TypeName="MAINCHAPTER" Name="Schraubendreher" WorkflowName="OKE_DATEN">
							oTreegroup = createTreegroupElement(oCategory.getCode(), "MAINCHAPTER", strCatalogversion, hashAttributes);

							//LOG.info("++strSubTitle=" + strSubTitle);
							oTreegroupList.addContent(oTreegroup);

						} // --- for ( String strAdditionalLange : allLanguages ) {

						// --- Setze Sprache, und Defaultsprache=de
						Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
						m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);
						m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);
                                                // --- hole f�r alle Ausgabesprache (Multilayer - Texte -------------------------------

						// --- Initialisiere die Daten dieser Kategory
						output(oCategory.getCode(), 2);
						initCategory(oTreegroupLink, oCategory, oTreegroupList, oProductList, strCatalogversion);
					}

				} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();) {

			} // --- if ( categories.size() > 0 ) {
			else {

				// --- Wurde diese Kategory f�r den Export ausgew�hlt?
				//LOG.info("strAktCategory=" + strAktCategory);
				if (m_colCategories.contains(strAktCategory)) {

					// --- preset
					m_colTippByProducts	=	new ArrayList();
					
					
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
						initCategory(oTreegroupLink, oAktCategory, oTreegroupList, oProductList, strCatalogversion);

						
						// --- ausgeben der Tips, falls vorhanden ---------------------------------------------------------------------
						
						// --- wurden tips gefunden?
						if ( m_colTippByProducts != null && m_colTippByProducts.size() > 0 ) {
							
							// --- preset
							int iTipID	=	0;
							
							// --- <Tip-List>
							Element oTipList = new Element("Tip-List");
							root.addContent(oTipList);

							// --- darstellen der TipLinks
							__generateTipLinks ( "producttippref", oTreegroupLink, m_colTippByProducts, iTipID );

							// --- erstellen der Tips
							__generateTippsElements ( "producttippref", oTipList , m_colTippByProducts, iTipID );

						} // --- if ( m_colTippByProducts != null && m_colTippByProducts.size() > 0 ) {


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
						strXmlFile = strLanguage + "_" + strXmlFilePrefix + strAktCategory + "_products.xml";

						// --- Erzeuge die Export-Ablage, falls nicht vorhanden
						strXmlPath = strXmlPath + m_oExportFormatter.formatXmlFilePrefix("/" + strXmlFilePrefix + strAktCategory);
						LOG.info("mkdir()= " + m_wm.createDirectory(strXmlPath));
					} else {
						LOG.info("+++++ERROR + Category not FOUND." + strAktCategory);
					}
				}
			}

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
	 *
	 * @param oCategory
	 * @return
	 */
	protected Category getMasterCategory(Category oCategory) {
		// TODO Auto-generated method stub

		// --- Initialize
		String strCatalogVersion = "";
		Boolean oIsWebCatalog = null;

		// --- Pr�fe, ob die Katalogversion "Weramaster ist", sonst andere katagorie holen
		CatalogVersion catalogversion = null;
		try {
			catalogversion = (CatalogVersion) oCategory.getAttribute("catalogVersion");
			//LOG.info("+++getMasterCategory.strCatalogVersion="+ catalogversion.getVersion() );
			if (catalogversion != null) {
				strCatalogVersion = catalogversion.getVersion();
				oIsWebCatalog = (Boolean) catalogversion.getAttribute("is_webcatalog");
				if (oIsWebCatalog == null) {
					oIsWebCatalog = new Boolean(false);
				}
			}
			//if ( oIsWebCatalog.booleanValue() && !strCatalogVersion.equals("weramaster") ) {
			if (!strCatalogVersion.equals(Config.getParameter("wera.mastercatalogversion"))) {
				// --- Hole Category aus WeraMaster
				//LOG.info("+++strCatalogVersion="+ strCatalogVersion+ ", get weramaster");
				oCategory = m_weraMasterCatalogVersion.getCategory(oCategory.getCode());
			}

		} catch (final JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Kategory zur�ckgeben
		return oCategory;
	}

	/**
	 *
	 * @return
	 */
	public String getLastXMLFileWritten() {
		return m_strXmlFile;
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
			Element oProductList, final String strCatalogversion) throws JaloInvalidParameterException, JaloSecurityException {

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
		if (false && categoriesSorted.size() > 0) {
			Element produktgruppeXML = null;
			for (final Iterator it1 = categoriesSorted.iterator(); it1.hasNext();) {
				// --- Hole Kategorie
				final Category oCategory = (Category) it1.next();

				// --- Wurde diese Kategory f�r den Export ausgew�hlt?
				if (m_colCategories.contains(oCategory.getCode())) {

					// --- <Treegroup Id="1003" Type="CHAPTER" RefCode="01-11-01 System 6" Workflow="OKE_DATEN" TypeName="CHAPTER" Name="System 6" WorkflowName="OKE_DATEN">
					final Element oTreegroup = createTreegroupElement(oCategory.getCode(), "CHAPTER", strCatalogversion, null);
					oTreegroup.setAttribute("RefCode", oCategory.getName());
					oTreegroup.setAttribute("Name", oCategory.getName());
					oTreegroupList.addContent(oTreegroup);

					// --- F�lle Datenzweig
					produktgruppeXML = new Element("TreegroupLink");
					output("PRODUKTGRUPPE=" + oCategory.getCode(), 2);
					initCategory(produktgruppeXML, oCategory, oTreegroupList, oProductList, strCatalogversion);

					// --- �bernehme Kategory
					oTreegroupLink.addContent(produktgruppeXML);
				}

			} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();) {

		} // --- if ( categories.size() > 0 )
		else {

			//    			 TODO entfernen
			//m_wm.m_bCheckForActivation = false;
			//    			 TODO
			// --- Hole alle aktiven Produkte der aktuellen Kategorie
			final boolean bCheckForActivationOld = m_wm.m_bCheckForActivation;
			SetCheckForActivation(false);
			final Collection productsSorted = m_wm.getProductsOrderedByCategory(category);
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
					WeraProduct oWeraProduct = (WeraProduct) it1.next();

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
					
					// --- ermitteln der zu exporierenden Bulletpoints -------------------------------------------------------------
					Collection<Textbaustein> colBulletpointsByProducts	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weraproductset2bulletpoints");
					//Collection<Textbaustein> colBulletpointsByVariante	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weravariante2bulletpoints");
					// --- schleife über alle gefundenen Textbausteine
					for (Textbaustein oProductTextbaustein : colBulletpointsByProducts ) {

						// --- get code des aktuellen tipps
						String strTextbausteinCode	= oProductTextbaustein.getCode();

						// --- pr�fe, ob der Textbaustein bereits exportiert wurde
						if ( !m_colExportedTextbausteinBullet.contains(strTextbausteinCode)) {

							// --- collect Textbaustein
							m_colTextbausteinBullet.add(oProductTextbaustein);

							// --- collect exported tipps
							m_colExportedTextbausteinBullet.add(strTextbausteinCode);
						}
					}
					//LOG.info("m_colTextbausteinBullet.size=" + m_colTextbausteinBullet.size());
					// --- ermitteln der zu exporierenden Bulletpoints -------------------------------------------------------------

					// --- ermitteln der zu exporierenden Marketingtexte -----------------------------------------------------------
					Collection<Textbaustein> colMarketingByProducts	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weraproduct2marketing");
					//Collection<Textbaustein> colMarketingByVariante		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weravariante2marketing");
					// --- schleife über alle gefundenen Textbausteine
					for (Textbaustein oProductTextbaustein : colMarketingByProducts ) {

						// --- get code des aktuellen tipps
						String strTextbausteinCode	= oProductTextbaustein.getCode();

						// --- pr�fe, ob der Textbaustein bereits exportiert wurde
						if ( !colMarketingByProducts.contains(strTextbausteinCode)) {

							// --- collect tipp
							m_colTextbausteinMarketingtext.add(oProductTextbaustein);

							// --- collect exported tipps
							m_colExportedTextbausteinMarketingtext.add(strTextbausteinCode);
						}
					}
					//LOG.info("m_colTextbausteinMarketingtext.size=" + m_colTextbausteinMarketingtext.size());
					// --- ermitteln der zu exporierenden Marketingtexte -----------------------------------------------------------

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
					

					// --- Hier geht es los
					LOG.info("oWeraProduct=" + oWeraProduct);
					// --- Eindeutige ProduktID
					//m_strProduktID = "P" + iOffsetProduktID;
					m_strProduktID = "P" + m_iProduktID++;
					//iOffsetProduktID++;

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
					LOG.info("initWeraProduct called (B)..." + m_strCurrentTemplateName);
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

			LOG.info("EOL=" + " - Anzahl Produkte=" + productsSorted.size());
		}
	}

	/**
	 * initialize list off SB-Variants (not supported in this object)
	 *
	 * @param Element oTreegroupLink
	 * @param Element oProductList
	 * @param WeraProduct oWeraHauptProduct
	 * @return Element
	 */
	protected boolean initalizeSB_Variants(Element oTreegroupLink, Element oProductList, WeraProduct oWeraHauptProduct) {
		return false;
	}

	/**
	 *
	 * @param checkForActivation
	 */
	protected void ReSetCheckForActivation(final boolean checkForActivation) {
		// TODO Auto-generated method stub
		m_wm.m_bCheckForActivation = checkForActivation;
	}

	/**
	 *
	 * @param checkForActivation
	 */
	protected void SetCheckForActivation(final boolean checkForActivation) {
		// TODO Auto-generated method stub
		m_wm.m_bCheckForActivation = checkForActivation;
	}

	/**
	 *
	 * @param textareaXML
	 * @param textitems
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	protected void initTextitem(final Element textareaXML, final Collection textitems) throws JaloInvalidParameterException,
			JaloSecurityException {

		Element textitemXML = null;
		for (final Iterator it1 = textitems.iterator(); it1.hasNext();) {
			// --- Hole Kategorie
			final Textitem oTextitem = (Textitem) it1.next();

			// --- F�lle Datenzweig
			textitemXML = new Element("TEXTITEM");
			//textitemXML.setAttribute("SPRACHE", m_strLanguage) ;
			textitemXML.setAttribute("ID", oTextitem.getAttribute("code").toString());
			final Element textXML = new Element("TEXT");
			final CDATA cdataText = new CDATA(getValidString((String) oTextitem.getAttribute(m_jaloSession.getSessionContext(),
					"textblock")));
			textXML.addContent(cdataText);
			textitemXML.addContent(textXML);

			// --- �bernehme Textelemente
			textareaXML.addContent(textitemXML);

		} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();) {
	}

	/**
	 *
	 * @param produktXML
	 * @param weraProduct
	 * @param oProductList
	 * @param strCurrentTemplateName
	 * @return
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	protected String initWeraProduct(final Element produktXML, final WeraProduct weraProduct, final Element oProductList, String strParamTemplateName)
			throws JaloInvalidParameterException, JaloSecurityException {
		// --- Initialize
		m_weraProductAkt = weraProduct;

		LOG.info("initWeraProduct=" + weraProduct.getCode());
		LOG.info("initWeraProduct.ParamTemplate=" + strParamTemplateName + "=");

		// --- Setze Templatetyp
		String strTemplate = "";
		if (strParamTemplateName == null || strParamTemplateName == "") {

			if (weraProduct instanceof WeraProductSet) {
				strTemplate = "PRODUCTSET";
			} else {
				strTemplate = "PRODUCT";
			}
			if (weraProduct instanceof WeraProductSetinSet) {
				strTemplate = "PRODUCTSETINSET";
			}
		} else {
			strTemplate = strParamTemplateName;
		}



		// -- Z�hler
		m_iCountProduct++;

		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// PRODUKT spezifische Daten
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// --- SB f�hig (J/N)
		m_bSB_faehig = new Boolean(false );
		m_bSB_faehig = (Boolean) getAttribute(weraProduct, "artikel_sb_faehig");
		if (m_bSB_faehig == null) {
			m_bSB_faehig = new Boolean(false );
		}
		if (m_bSB_faehig.booleanValue()) {
			produktXML.setAttribute("SB", "J");
		} else {
			produktXML.setAttribute("SB", "N");
		}
		
		// --- Neues Produkt (J/N)
		Boolean bNeuesProdukt = false;
		bNeuesProdukt = (Boolean) getAttribute(weraProduct, "produkt_neu");
		if (bNeuesProdukt == null) {
			bNeuesProdukt = false;
		}
		if (bNeuesProdukt.booleanValue()) {
			produktXML.setAttribute("NeuesProdukt", "J");
		} else {
			produktXML.setAttribute("NeuesProdukt", "N");
		}

		// --- <Product Id="3773" Type="SET" RefCode="U100T601" Workflow="OKE_DATEN" Manufacturer="WIHA" ProductNumber="26015" EAN="4010995260156" TypeName="SET" Name="System 6 Wechselklingen Magnetic Set, 6-tlg." WorkflowName="OKE_DATEN" ManufacturerDescription="Wiha">
		produktXML.setAttribute("Id", m_strProduktID);
		produktXML.setAttribute("LinkedId", m_strProduktID);
		produktXML.setAttribute("RefCode", m_strProduktID);
		//produktXML.setAttribute( "Workflow", "OK" );
		//produktXML.setAttribute( "Manufacturer", "WERA"  );
		produktXML.setAttribute("ProductNumber", m_strProduktID);
		produktXML.setAttribute("WeraProduktNr", weraProduct.getCode());
		//produktXML.setAttribute( "EAN", "" );
		String strName = (String) getAttribute(weraProduct, "name");
		if (strName == null) {
			strName = (String) "??";
		}
		produktXML.setAttribute("Name", m_oExportFormatter.formatCharakters(strName));
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// PRODUKT spezifische Daten
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// --- hole zus�tzliche sprachen
		HashMap<String, String> hashTitle = new HashMap();
		hashTitle.put(m_strLanguage, strName);
		if (m_bMultilayer) {
			for (String strAdditionalLange : m_coAdditionallLanguages) {

				// --- Setze Sprache, und Defaultsprache=de
				Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(strAdditionalLange);
				m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);

				strName = (String) getAttribute(weraProduct, "name");
				if (strName == null) {
					strName = (String) "??";
				}
				hashTitle.put(strAdditionalLange, strName);
			}

			// --- Setze Sprache, und Defaultsprache=de
			final Language m_Language = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
			m_jaloSession.getSessionContext().setLanguage(m_Language);
			m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);
		}
		LOG.info("initWeraProduct (1)=" + weraProduct.getCode());

//produktXML.setAttribute( "WorkflowName", "OK"  );
		//produktXML.setAttribute( "ManufacturerDescription", "WERA" );
		final Integer order = (Integer) getAttribute(weraProduct, "order");
		String strOrder = "";
		if (m_wm.getAttribute(weraProduct, "order") == null) {
			strOrder = "00000000";
		} else {
			strOrder = "0000000" + m_wm.getAttribute(weraProduct, "order").toString();
		}
		if (strOrder == null) {
			produktXML.setAttribute("Sequence", "1");
		} else {
			produktXML.setAttribute("Sequence", strOrder.substring(strOrder.length() - 7));
		}
		LOG.info("initWeraProduct (2)=" + weraProduct.getCode());

		// --- <Text-List>
		final Element oTextList = new Element("Text-List");
		produktXML.addContent(oTextList);

		// --- Titel (default language)
		LOG.info("initWeraProduct (3)=" + weraProduct.getCode());
		createMultiLayerTextElement(hashTitle, oTextList, "BLT_TITLE");

		LOG.info("initWeraProduct (4)=" + weraProduct.getCode());
		// --- <ProductJoin-List>
		final Element oProductJoinList = new Element("ProductJoin-List");
		produktXML.addContent(oProductJoinList);

		LOG.info("initWeraProduct (5)=" + weraProduct.getCode());
		// --- <Attribute-List>
		final Element oAttributeList = new Element("Attribute-List");
		produktXML.addContent(oAttributeList);

		// --- <Attribute-List>
		//Element oAttributeHeaderList = new Element("AttributeHeader-List");
		//produktXML.addContent(oAttributeHeaderList);
		// --- <Price-List>
		//Element oPriceList = new Element("Price-List");
		//produktXML.addContent(oPriceList);
		// --- <Footnote-List>
		//Element oFootnoteList = new Element("Footnote-List");
		//produktXML.addContent(oFootnoteList);
		// ---- Initialize Sichtbarkeit (Attribute)
		final EnumerationManager em = m_jaloSession.getEnumerationManager();
		EnumerationType et = null;
		final EnumerationValue ev = null;
		et = em.getEnumerationType("ClassificationAttributeVisibilityEnum");
		final EnumerationValue evVISIBLE = em.getEnumerationValue(et, "VISIBLE");
		final EnumerationValue evVISIBLE_IN_BASE = em.getEnumerationValue(et, "VISIBLE_IN_BASE");
		final EnumerationValue evVISIBLE_IN_VARIANT = em.getEnumerationValue(et, "VISIBLE_IN_VARIANT");
		//ArrayList attributesProduct = new ArrayList();
		final ArrayList attributesArticle = new ArrayList();

		// --- Initialisieren der Merkmale
		LOG.info("initWeraProduct.Initialisieren der Merkmale=");
		final HashMap hashCategory = new HashMap();
		final Category categoryPClass = null;
		final Category categoryWera = null;
		final Collection attributes = new ArrayList();

		// --- Hole alle ClassAttributeAssignment von Produkt / Variante oder Set
		final List<ClassAttributeAssignment> classattributeassignments = m_weraclassificationhelper
				.getAllClassAttributeAssignmentByProduct(weraProduct);

		/*
		 * //m_wm.disableCheckForActivation(); Collection categories = m_wm.getCategoriesByProduct ( weraProduct,
		 * m_strLanguage ); //m_wm.enableCheckForActivation(); //Collection categories =
		 * WeraManager.getInstance().getCategoriesByProduct (weraProduct ); ClassificationAttribute
		 * oClassificationAttributes = null; if ( categories != null && categories.size() > 0 ) { // --- Schleife �ber
		 * alle Kategorien ClassificationClass oCategory = null; for (Iterator it1 = categories.iterator();
		 * it1.hasNext();) { // --- Hole Category oCategory = (ClassificationClass) it1.next();
		 * output("initWeraProduct.Schleife.oCategory="+oCategory.getCode(),2);
		 * 
		 * // --- Hole PCLass-Kategory if ( oCategory.getCode().substring(0,2).equals("AA" ) ||
		 * oCategory.getCode().substring(0,1).equals("_" )) { //strPClass = oCategory.getCode(); categoryPClass =
		 * oCategory; } else { categoryWera = oCategory; }
		 * 
		 * // --- Initialisiere alle Attribute Collection colAttr = m_wm.getClassificationAttributes ( oCategory );
		 * output("s.initWeraProduct.Schleife �ber alle colAttr.size=" + colAttr.size(),2); if ( colAttr != null ) {
		 * attributes.addAll( colAttr ); for ( Iterator it2 = colAttr.iterator(); it2.hasNext(); ) { // --- Hole Category
		 * oClassificationAttributes = (ClassificationAttribute) it2.next();
		 * hashCategory.put(oClassificationAttributes.getCode(),oCategory); output("Found CA=" +
		 * oClassificationAttributes.getCode(),2); } }
		 * 
		 * } // --- for (Iterator it1 = categories.iterator(); it1.hasNext();) }
		 */
		
		// --- hole erste Variant
		final Collection variants = m_wm.getVariants(weraProduct);
		WeraVariante oWeraVarianteTmp =	null;
		if ( variants != null && variants.size() > 0 ) {
			
			oWeraVarianteTmp =	(WeraVariante)variants.iterator().next();
		}
		
		// --- Schleife �ber alle Attribute
		//LOG.info("initWeraProduct.Schleife �ber alle Attribute.classattributeassignments.size=" + classattributeassignments.size() );
		m_ouputcontrols = (Collection) weraProduct.getAttribute("outputcontrols");
		Collection foundOuputcontrols = new ArrayList();
		Outputcontrol outputcontrol = null;
		EnumerationValue evVisibility = null;
		for (final Iterator it1 = classattributeassignments.iterator(); it1.hasNext();) {
			// --- Hole ProfiClassAttribute
			final ClassAttributeAssignment oClassAttributeAssignment = (ClassAttributeAssignment) it1.next();
			final ClassificationAttribute oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();

			// --- debug ---
			//LOG.info("##### oClassificationAttribute " + oClassificationAttribute.getCode());
			output("+", 2);

			// --- hole werte -------------
			if ( oWeraVarianteTmp == null ) {
				// --- Abbruch
				// LOG.info("- skip oClassificationAttribute=" + oClassAttributeAssignment );
				continue;
			}



			// --- prüfe ob werte vorhanden sind erweitere die Prüfung auf alle Varianten ------------------------------------------------------------------
/**
 * bugfix ex: AAA971f001 bei 8790 HMA
 * GT: 05.01.2021 Fall: Zoll Spalte bei der nicht ab der  ersten variante werte vorhanden sind.
 *
 // --- Wurden Werte ausgewh�lt, wurde nur die erste variante geprüft
 if (hFeatureValues.size() == 0) {
 // --- Abbruch
 LOG.info("- skip oClassificationAttribute=" + oClassAttributeAssignment );
 continue;
 }
 // --- hole werte -------------
 */
			// LOG.info("prüfe oClassificationAttribute=" + oClassAttributeAssignment );
			boolean valuesFound	= false;
			for (final Iterator itVariant = variants.iterator(); itVariant.hasNext();) {

				oWeraVarianteTmp = (WeraVariante) itVariant.next();

				// --- hole ausgewählten wert, sofern vorhanden
				final HashMap<String, ClassificationAttributeValue> hFeatureValues = m_weraclassificationhelper
						.getPickedClassificationAttributeValuesByProduct(oWeraVarianteTmp, oClassAttributeAssignment);
				// LOG.info("prüfe variante " + oWeraVarianteTmp.getCode() + ", anzahl features=" + hFeatureValues.size() );

				// --- wurde ein wert gefunden
				if (hFeatureValues.size() > 0) {
					// --- Abbruch, es sind werte in der spalten vorhanden!
					valuesFound	= true;
					// LOG.info("BREAK: prüfe variante " + oWeraVarianteTmp.getCode() + ", anzahl features=" + hFeatureValues.size() );
					break;
				}

			} // for (final Iterator itVariant = variants.iterator(); itVariant.hasNext();) {
			// --- wurden bei einer variante werte gefunden?
			if ( valuesFound == false ) {
				// --- keine werte gefunden, wir ignorieren das attribute, keine ausgabe dieser Tabellenspalte!
				LOG.info("- no values found, skip oClassificationAttribute=" + oClassAttributeAssignment );
				continue;
			}
			// LOG.info("- value found, use oClassificationAttribute=" + oClassAttributeAssignment );
//LOG.info("oWeraVarianteTmp=" + oWeraVarianteTmp.getCode() + ", oClassificationAttribute=" + oClassificationAttribute.getCode() + ", Anzahl hFeatureValues= " + hFeatureValues.size() );
			// --- prüfe ob werte vorhanden sind erweitere die Prüfung auf alle Varianten ------------------------------------------------------------------




			// ---  Hole Ausgabesteuerung (pr�fe auf sichtbare Elemente)
			//outputcontrol = (Outputcontrol) m_wm.checkContaining(m_ouputcontrols, "code", oClassificationAttribute.getCode());
			outputcontrol = (Outputcontrol) m_wm.checkContainingsVisibilityByOutputControl(m_ouputcontrols, "code", oClassificationAttribute.getCode(), evVISIBLE_IN_VARIANT);
			if (outputcontrol != null) {
				evVisibility = (EnumerationValue) m_wm.getAttribute(outputcontrol, "visibility");
				output("outputcontrol=" + outputcontrol, 2);
				output("outputcontrol.evVisibility=" + evVisibility.getName(), 2);
				output("outputcontrol.order=" + m_wm.getAttribute(outputcontrol, "order"), 2);
			} else {
				evVisibility = evVISIBLE_IN_BASE;
			}
			output("oClassificationAttribute=" + oClassAttributeAssignment, 2);
			// --- debug ---

			// --- Sortieren der Merkmale
			// if (  evVisibility==evVISIBLE_IN_VARIANT ||  evVisibility==evVISIBLE ) {
			if (evVisibility.equals(evVISIBLE_IN_VARIANT) || evVisibility.equals(evVISIBLE)) {
				if (!attributesArticle.contains(oClassAttributeAssignment)) {
					foundOuputcontrols.add(outputcontrol);
					//LOG.info("attributesArticle add " + oClassificationAttribute.getCode());
					attributesArticle.add(oClassAttributeAssignment);

					// --- Grifffarbe ==> eigenes Template
					//if ( oClassificationAttribute.getCode().equals("AAA972f001") || oClassificationAttribute.getCode().equals("AAC551f001") || oClassificationAttribute.getCode().equals("AAA358f001") )
					//strTemplate = "COLOREDPRODUCT";
				} else {
					//LOG.info("VI: attributesArticle not add " + oClassificationAttribute.getCode());
				}
			} else {
				//LOG.info("VI: attributesArticle not add " + oClassificationAttribute.getCode());
			}
			/*
			 * if ( evVisibility==evVISIBLE_IN_BASE ) { if ( !attributesProduct.contains(oClassificationAttribute)) {
			 * output("attributesProduct.add(oClassificationAttribute)",2);
			 * attributesProduct.add(oClassificationAttribute); } }
			 */

		} // --- for (Iterator it1 = attributes.iterator(); it1.hasNext();) {

		// --- Debug -------------------------------------------------------------------------------------------------------------
		if (attributesArticle.size() == 0 && !(weraProduct instanceof WeraProductSet)) {
			LOG.error("Fehler in der Anzahl der Spalten bei Product(" + weraProduct.getCode() + ") - Spalten="
					+ attributesArticle.size());
			LOG.error("Debug siehe export_log.txt!!");

			m_aLogList.add("\n\r--------\n\r\n\rAnzahl Spalten Product=(" + weraProduct.getCode() + ") - "
					+ attributesArticle.size());
			final Language m_Language = m_jaloSession.getSessionContext().getLanguage();
			m_aLogList.add("m_Language.getName()=" + m_Language.getName());
			m_aLogList.add("m_jaloSession.getSessionID()=" + m_jaloSession.getSessionID());
			m_aLogList.add("m_jaloSession.getUser()=" + m_jaloSession.getUser().getLogin());
			m_aLogList.add("Anzahl caa=" + classattributeassignments.size());
			for (final Iterator it1 = classattributeassignments.iterator(); it1.hasNext();) {
				// --- Hole ProfiClassAttribute
				final ClassAttributeAssignment oClassAttributeAssignment = (ClassAttributeAssignment) it1.next();
				final ClassificationAttribute oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();

				// ---  Hole Ausgabesteuerung
				outputcontrol = (Outputcontrol) m_wm.checkContaining(m_ouputcontrols, "code", oClassificationAttribute.getCode());
				if (outputcontrol == null) {
					m_aLogList.add("ca=" + oClassificationAttribute.getCode() + ", kein Outputcontrol gefunden.");
				} else {
					evVisibility = (EnumerationValue) m_wm.getAttribute(outputcontrol, "visibility");
					if (evVisibility.equals(evVISIBLE_IN_VARIANT) || evVisibility.equals(evVISIBLE)) {
						m_aLogList.add("ca (visible in artikel)=" + oClassificationAttribute.getCode() + ", outputconrol ("
								+ m_wm.getAttribute(outputcontrol, "code") + ")=" + evVisibility.getCode());
					} else {
						m_aLogList.add("ca (visible in produkt)=" + oClassificationAttribute.getCode() + ", outputconrol ("
								+ m_wm.getAttribute(outputcontrol, "code") + ")=" + evVisibility.getCode());
					}
				}
			}
			m_aLogList.add("Anzahl ouputcontrols=" + m_ouputcontrols.size());
			for (final Iterator it2 = m_ouputcontrols.iterator(); it2.hasNext();) {
				outputcontrol = (Outputcontrol) it2.next();
				evVisibility = (EnumerationValue) m_wm.getAttribute(outputcontrol, "visibility");
				m_aLogList.add("outputconrol (" + m_wm.getAttribute(outputcontrol, "code") + ")=" + evVisibility.getCode());
			}
		}
		// --- Debug -------------------------------------------------------------------------------------------------------------

		// --- Template f�r Ausgabe setzen -----------------------------------------------
		LOG.info("strParamTemplateName >" + strParamTemplateName + "<" );
		if (strParamTemplateName == null || strParamTemplateName.equals("")) {
			strTemplate = m_strCurrentTemplateName = _initTemplateName(weraProduct);
		} else {
			strTemplate = m_strCurrentTemplateName = strParamTemplateName;
		}
		LOG.info("m_strCurrentTemplateName set to (oc)=" + m_strCurrentTemplateName );
		LOG.info("strTemplate set to (oc)=" + strTemplate );
		// --- Template f�r Ausgabe setzen -----------------------------------------------

		// --- Sortiere die Atrribute nach Order
		output("initWeraProduct.Vor sort orderComparatorExportCA=", 2);
		final OrderComparatorExportCAA OrderComparatorExportCAA = new OrderComparatorExportCAA();
		OrderComparatorExportCAA.init(foundOuputcontrols);
		//if ( attributesProduct != null && attributesProduct.size() > 0 )
		//	   Collections.sort( (List) attributesProduct, orderComparatorExportCA );
		if (attributesArticle != null && attributesArticle.size() > 0) {
			Collections.sort(attributesArticle, OrderComparatorExportCAA);
		}
		output("initWeraProduct.nach sort orderComparatorExportCA=", 2);

		// --- Initialize Product Data
		output("initWeraProduct.Initialize Product Data=", 2);
		//produktXML.setAttribute("SPRACHE", m_strLanguage) ;
		//produktXML.setAttribute("Id",    weraProduct.getCode() ) ;
		//produktXML.setAttribute("Name",  getValidString(weraProduct.getName(m_jaloSession.getSessionContext())) );
		final ComposedType WeraProductType = m_jaloSession.getTypeManager().getComposedType(WeraProduct.class);
		String codeNr = "";
		m_firstcodeNr = "";
		if (weraProduct instanceof WeraProductSet) {
			if (weraProduct instanceof WeraProductSetinSet) {
				produktXML.setAttribute("Typ", "PRODUKTSATZINSATZ");
			} else {
				produktXML.setAttribute("Typ", "PRODUKTSATZ");
			}
			if (weraProduct.getAttribute("lagerNr") == null) {
				m_wm.setAttribute(weraProduct, "lagerNr", "05");
			}
			if (weraProduct.getLocalizedProperty("variantenNr") == null) {
				weraProduct.setLocalizedProperty("variantenNr", "001");
			}
			// --
			final String strVariantenNr = (String) weraProduct.getLocalizedProperty("variantenNr");
			// --

			if (weraProduct.getAttribute("artnr") == null) {
				m_wm.setAttribute(weraProduct, "artnr", "??");
			}
			codeNr = weraProduct.getAttribute("lagerNr").toString() + weraProduct.getAttribute("artnr").toString() + strVariantenNr;
			produktXML.setAttribute("Codenr", codeNr);

			// --- Hyperlink CodeNr
			if ( !codeNr.contains("??") ) {
				m_firstcodeNr = codeNr;
			}

		} else {

			// --- Hyperlink CodeNr
			m_firstcodeNr = "";

			produktXML.setAttribute("Typ", "PRODUKT");
		}

		// --- initialize MediaDaten
		final Element motivelistXML = initializeMediaData(weraProduct);
		produktXML.addContent(motivelistXML);


		// --- content info ---------------------------------------------------------------------------------------------------------------------
		String strContentInfo					= (String) "";
		String strContentInfoDE					= (String) "";
		HashMap<String, String> hashContentInfo = new HashMap();
		// --- content_info
		if (weraProduct instanceof WeraProductSet) { 
			// getDWHInfoForVariants(currentSessionLangIsoCode, "content_info" )
                        // getDWHInfoString
                        // getDWHInfo (for set)
                        
			// --- Content-Info aus Satz holen
			strContentInfoDE	= ((WeraProductSet) weraProduct).getDWHInfo("de", "content_info"); 
			
		} else {
			
			// --- Content-Info Produkt hole
			strContentInfoDE	= weraProduct.getDWHInfoString ( "de", "content_info" );
		}
		if ( strContentInfoDE == null ) {
			strContentInfoDE	= "";
		}
		hashContentInfo.put( "de", strContentInfoDE );
		// --- content info ---------------------------------------------------------------------------------------------------------------------

		
		// --- TEXT-Beschreibung ----------------------------------------------------------------------------------------------------------------
		// String strDescription1 = getValidString(weraProduct.getDescription1(m_jaloSession.getSessionContext()));
		String strDescription1 = (String) m_wm.getAttribute(weraProduct, "description1");
		if (strDescription1 == null) {
			strDescription1 = "";
		}
		if (strDescription1.contains("::")) {
			strDescription1 = strDescription1.replaceAll("::", ":");
		}
		HashMap<String, String> hashDescription = new HashMap();
		hashDescription.put(m_strLanguage, strDescription1);
		if (m_bMultilayer) {
			for (String strAdditionalLange : m_coAdditionallLanguages) {

				// --- Setze Sprache, und Defaultsprache=de
				Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(strAdditionalLange);
				m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);

				// --- TEXT-Beschreibung
				strDescription1 = (String) m_wm.getAttribute(weraProduct, "description1");
				if (strDescription1 == null) {
					strDescription1 = "";
				}
				if (strDescription1.contains("::")) {
					strDescription1 = strDescription1.replaceAll("::", ":");
				}
				hashDescription.put(strAdditionalLange, strDescription1);
				
				if ( !m_isPriceListExport ) {
					
					// --- content_info
					if (weraProduct instanceof WeraProductSet) { 

						// --- Content-Info aus Satz holen
						strContentInfo	= ((WeraProductSet) weraProduct).getDWHInfo(strAdditionalLange, "content_info"); 

					} else {

						// --- Content-Info Produkt hole
						strContentInfo	= weraProduct.getDWHInfoString ( strAdditionalLange, "content_info" );
					}
				}


				if ( strContentInfo == null ) {
					// --- fallback DE
					strContentInfo	= strContentInfoDE;
				}
				hashContentInfo.put( strAdditionalLange, strContentInfo );
				
			} // --- for (String strAdditionalLange : m_coAdditionallLanguages) {

			// --- Setze Sprache, und Defaultsprache=de
			final Language m_Language = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
			m_jaloSession.getSessionContext().setLanguage(m_Language);
			m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);

		} // if (m_bMultilayer) {

		// --- create text-element
		if ( !m_isPriceListExport ) {
			
			createMultiLayerTextElement(hashDescription, oTextList, "BLT_1");
		}

		// --- CONTENT_INFO (Nur Katalog)
		if ( !m_isPriceListExport ) {
			
			createMultiLayerTextElement(hashContentInfo, oTextList, "BLT_CONTENTINFO");
		}

		// --- TEXT-Beschreibung ----------------------------------------------------------------------------------------------------------------


		
		if (m_bLeftElement == false && weraProduct instanceof WeraProductSetinSet) {
			Boolean istDisplay = (Boolean)m_wm.getAttribute(weraProduct,"ist_display");
			if ( istDisplay == null ) {
				istDisplay	 = new Boolean(false);
			}
			LOG.info("+++++++++strTemplate.istDisplay=" + istDisplay );
			if ( !istDisplay.booleanValue() ) {
				strTemplate = "PRODUCTSETINSET";
			}

		} else if (m_bLeftElement == false && weraProduct instanceof WeraProductSet) {
			strTemplate = "PRODUCTSET1";
			LOG.info("+++Template set to=" + strTemplate );
		}


		// --- Marketingtexte (optional, zur Zeit nur WebExport) ------------------------
		String strDescription2 = (String) m_wm.getAttribute(weraProduct, "description2");
		if (strDescription2 == null) {
			strDescription2 = "";
		}
		createMarketingTextElement(strDescription2, oTextList);
		// --- Marketingtexte (optional, zur Zeit nur WebExport) ------------------------


		// --- Weblink-List (optional, zur Zeit nur WebExport)
		createWeblinkList(produktXML, weraProduct);
		// --- Weblink-List (optional, zur Zeit nur WebExport)

		// --- Crosslink-List (optional, zur Zeit nur WebExport)
		createCrosslinkList(produktXML, weraProduct);
		// --- Crosslink-List (optional, zur Zeit nur WebExport)

		// --- External-Image-List (optional, zur Zeit nur WebExport)
		createExternalImageList(produktXML, weraProduct);
		// --- External-Image-List (optional, zur Zeit nur WebExport)

		// --- TEXT-Schlagworte
		createTextSchlagworte(weraProduct, oTextList);

		// --- Inhalt der Tabelle bei S�tzen
		final Collection colFootnodesSet = new ArrayList();
		Collection colFootnodesTmpSet = new ArrayList();
		if (weraProduct instanceof WeraProductSet) {

			// --- inhalt von satz / satz in Satz ausgeben
			colFootnodesTmpSet = createContentElement((WeraProduct) weraProduct, oTextList );

			// --- hole fussnoten des satz
			if ( !(weraProduct instanceof WeraProductSetinSet) ) {
				if (((WeraProductSet) weraProduct).getWeraProductSetFootnotes() != null) {
					colFootnodesSet.addAll(((WeraProductSet) weraProduct).getWeraProductSetFootnotes());
				}

			} else {

				// --- fussnoten satz in satz
				if (colFootnodesTmpSet != null) {
					colFootnodesSet.addAll( colFootnodesTmpSet );
				}
			}

			// --- Auslaufartikel als Fussnote ausgebeb?
			if (this.m_bExportDiscontinuedItemAsFootnote) {

				// --- Auslaufartikel in Fussnoten
				Boolean bProduktAuslauf = (Boolean) m_wm.getAttribute(weraProduct, "artikel_auslauf");
				if (bProduktAuslauf == null) {
					bProduktAuslauf = new Boolean(false);
				}
				if (bProduktAuslauf.booleanValue()) {

					if (!colFootnodesSet.contains(m_oFootnoteAuslaufartikelTmp)) {
						LOG.info("+++Fussnote m_oFootnoteAuslaufartikelTmp (1) set added=" + weraProduct.getCode());
						colFootnodesSet.add(m_oFootnoteAuslaufartikelTmp);
					}
				}

			} // --- if ( this.m_bExportDiscontinuedItemAsFootnote ) {

		} else {
			
			if (weraProduct instanceof WeraProduct ) {
				colFootnodesTmpSet = createContentElement( weraProduct, oTextList );
			}
		}




		// --- Beschreibung f�r Tabellenkopf (START) -----------------------------------------------
		ClassificationAttribute oClassificationAttribute = null;
		ClassAttributeAssignment oClassAttributeAssignment = null;
		if (m_hashAttributeHeaderList != null) {
			m_hashAttributeHeaderList.clear();
		}
		m_hashAttributeHeaderList = null;
		m_hashAttributeHeaderList = new HashMap();
		HashMap hashAttributeHeaderList = null;
		int iPosCa = 1;
		Outputcontrol outputcontrol1 = null;
		LOG.info(weraProduct.getCode() + " - Beschreibung f�r Tabellenkopf");
		LOG.info(weraProduct.getCode() + ", attributesArticle.size()=" + attributesArticle.size());
		for (final Iterator it2 = attributesArticle.iterator(); it2.hasNext();) {

			// --- Hole ClassificationAttribute / ClassAttributeAssignment
			oClassAttributeAssignment = (ClassAttributeAssignment) it2.next();
			oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();
			iPosCa++;

			// --- Hashmap anlegen
			hashAttributeHeaderList = new HashMap();
			hashAttributeHeaderList.put("Id", oClassificationAttribute.getCode());
			hashAttributeHeaderList.put("Tabulator", "R");
			// --- Hole die Ausgabesteuerung
			outputcontrol1 = (Outputcontrol) m_wm.checkContaining(m_ouputcontrols, "code", oClassificationAttribute.getCode());
			Boolean bBackground = null;
			if (outputcontrol1 != null) {
				bBackground = (java.lang.Boolean) outputcontrol1.getLocalizedProperty("background");
			}
			hashAttributeHeaderList.put("Background", _getBackgroundMarker(bBackground));
//			if (bBackground != null && bBackground.booleanValue())
//			{
//				hashAttributeHeaderList.put("Background", "J");
//			}
//			else
//			{
//				hashAttributeHeaderList.put("Background", "N");
//			}
			hashAttributeHeaderList.put("Sequence", new Integer(iPosCa).toString());
			hashAttributeHeaderList.put("FileName", "");
			hashAttributeHeaderList.put("PrintFileName", "");
			hashAttributeHeaderList.put("UnitName", "");

			// --- Hash �bernehmen
			m_hashAttributeHeaderList.put(oClassificationAttribute.getCode(), hashAttributeHeaderList);
		}
		hashAttributeHeaderList = new HashMap();
		hashAttributeHeaderList.put("Id", "code");
		hashAttributeHeaderList.put("FileName", "");
		hashAttributeHeaderList.put("PrintFileName", "");
		m_hashAttributeHeaderList.put("code", hashAttributeHeaderList);
		hashAttributeHeaderList = new HashMap();
		hashAttributeHeaderList.put("Id", "contentQuantity");
		hashAttributeHeaderList.put("FileName", "");
		hashAttributeHeaderList.put("PrintFileName", "");
		m_hashAttributeHeaderList.put("contentQuantity", hashAttributeHeaderList);
		hashAttributeHeaderList = new HashMap();
		hashAttributeHeaderList.put("Id", "preis");
		hashAttributeHeaderList.put("FileName", "");
		hashAttributeHeaderList.put("PrintFileName", "");
		m_hashAttributeHeaderList.put("preis", hashAttributeHeaderList);

		output("initWeraProduct.Beschreibung f�r Tabellenkopf=", 2);
		final Element tabellenkopfXML = new Element("TABLEHEADER");
		final Element spalteXML = null;

		// --- Merkmaltype
		String strType = "TECHNISCHE_MERKMALE";
		if ( m_isPriceListExport ) {
			strType = "CODE_NR";
		}

		// produktXML.addContent(tabellenkopfXML);
		// --- Produktspezifische Merkmale
		String strCode = weraProduct.getAttribute("code").toString();
		int iOrder = 0;
		if (weraProduct instanceof WeraProductSet) {
			if (weraProduct.getAttribute("artnr") == null) {
				m_wm.setAttribute(weraProduct, "artnr", "");
			}
			if (weraProduct.getAttribute("lagerNr") == null) {
				m_wm.setAttribute(weraProduct, "lagerNr", "05");
			}
			if (weraProduct.getLocalizedProperty("variantenNr") == null) {
				weraProduct.setLocalizedProperty("variantenNr", "001");
			}
			final String strVarNr = (String) weraProduct.getLocalizedProperty("variantenNr");

			final String strLagerNr = weraProduct.getAttribute("lagerNr").toString();
			//strCode = "##b##" + weraProduct.getAttribute("artnr").toString() + "##e_b##";
			strCode = /*"##b##" + */ weraProduct.getAttribute("artnr").toString()/* + "##e_b##" */;
			String strOutput = "";
			if (m_strLanguage.equals("us-en")) {
				strOutput = m_oExportFormatter.formatCodeUS(weraProduct.getAttribute("lagerNr").toString(),
						weraProduct.getAttribute("artnr").toString(), strVarNr);
			} else {
				strOutput = m_oExportFormatter.formatCode(strLagerNr, strCode, strVarNr);
			}

			if (false && m_strLanguage.equals("ru")) {
				// --- "???"
				initMerkmalAttributeType(oAttributeList, "code", "CODE", strType, strOutput, iOrder, "ICON_CODE", "\u043A\u043E\u0434");
			} else {
				if ( !m_isPriceListExport ) {
					initMerkmalAttributeType(oAttributeList, "code", "CODE", strType, strOutput, iOrder, "ICON_CODE", "");
				} else {
					initMerkmalAttributeType(oAttributeList, "code", "CODE", strType, strOutput, iOrder, "ICON_CODE", "Code");
				}
			}
			iOrder++;
			initMerkmalAttributeType(oAttributeList, "inhalt", "INHALT", "TECHNISCHE_MERKMALE", "", iOrder, "", "");
		} else {
			strCode = "##b##" + weraProduct.getAttribute("code").toString() + "##e_b##";
			if ( false && m_strLanguage.equals("ru")) {
				// --- "???"
				initMerkmalAttributeType(oAttributeList, "code", "CODE", strType, strCode, iOrder, "ICON_CODE", "\u043A\u043E\u0434");
			} else {
				
				if ( !m_isPriceListExport ) {
					initMerkmalAttributeType(oAttributeList, "code", "CODE", strType, strCode, iOrder, "ICON_CODE", "");
				} else {
					initMerkmalAttributeType(oAttributeList, "code", "CODE", strType, strCode, iOrder, "ICON_CODE", "Code");
				}
			}
			iOrder++;
			initMerkmalAttributeType(oAttributeList, "notiz", "NOTIZ", strType, "", iOrder, "", "");
		}
		
		// --- Artikelspezifische Merkmale (I)
/*		
		iOrder++;
		if (weraProduct instanceof WeraProductSet || weraProduct instanceof WeraProductSetinSet) {
			String strMenge = "1";
			if (weraProduct.getAttribute("contentQuantity") != null) {
				strMenge = weraProduct.getAttribute("contentQuantity").toString();
			}
					
			strVKHQuantity	= strMenge;
			
			initMerkmal(oAttributeList, "contentQuantity", "Menge", "numerisch", strMenge, iOrder, "ICON_VERPACKUNGSEINHEIT", "");
		} else {
			initMerkmal(oAttributeList, "contentQuantity", "Menge", "numerisch", "", iOrder, "ICON_VERPACKUNGSEINHEIT", "");
		}
*/		
		iOrder++;
		// initMerkmal(oAttributeList, "preis", "Preis", "numerisch", "", iOrder, "ICON_PREIS", " ");
		if (weraProduct instanceof WeraProductSet || weraProduct instanceof WeraProductSetinSet) {
			String strEAN = "";
			if (m_strLanguage.equals("us-en") || m_strLanguage.equals("us-es") || m_strLanguage.equals("us-fr")) {
				strEAN = (String) m_wm.getAttribute(weraProduct, "ean_us");
			} else {
				strEAN = (String) m_wm.getAttribute(weraProduct, "ean");
			}

			initMerkmalAttributeType(oAttributeList, "ean", "EAN", strType, strEAN, iOrder, "ICON_EAN", " " );
		} else {

			if ( m_isPriceListExport ) {

					// --- leere EAN spalte
					initMerkmalAttributeType(oAttributeList, "ean", "EAN", strType, "", iOrder, "ICON_EAN", "4013288");
			}
		}

		
		// --- Schleife �ber alle Attribute f�r Artikel
		output("initWeraProduct.Schleife �ber alle Artikel-Merkmale=" + weraProduct.getAttribute("code"), 2);
		Category categoryByCa = null;
		String strID	= "";
		boolean containsLanguages = false;
		int iCounter = 0;
		for (final Iterator it2 = attributesArticle.iterator(); it2.hasNext();) {
			
			// --- Hole ClassificationAttribute / ClassAttributeAssignment
			oClassAttributeAssignment = (ClassAttributeAssignment) it2.next();
			oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();
			output("initWeraProduct.oClassificationAttribute.getAttribute(code)=" + oClassificationAttribute.getAttribute("code"), 2);
			
			// --- pr�fe merkmal begrenzung bei preislisten --------------------------
			if ( m_isPriceListExport ) {
				if ( iCounter >= m_maxAttributeCols ) {
					continue;
				}
			}
			// --- pr�fe merkmal begrenzung bei preislisten --------------------------

			// --- F�lle Datenzweig und �bernehme Merkmale (ProfiClassClassificationAttribute) in den Artikel
			categoryByCa = (Category) hashCategory.get(oClassificationAttribute.getAttribute("code"));
			iOrder++;
			strID		= oClassificationAttribute.getCode();
			if (!(weraProduct instanceof WeraProductSet)) {

				if (strID.equals("AAA972f001")
						|| strID.equals("AAC551f001")
						|| strID.equals("AAA358f001")
						|| strID.equals("_AAA972f001")) {
					initClassificationAttributes(weraProduct, oAttributeList, oClassAttributeAssignment, categoryByCa, iOrder, 1);
					initClassificationAttributes(weraProduct, oAttributeList, oClassAttributeAssignment, categoryByCa, iOrder, 2);
				} else {
					initClassificationAttributes(weraProduct, oAttributeList, oClassAttributeAssignment, categoryByCa, iOrder, 0);
				}
				
				// --- pr�fen ob die Spalte Sprache vorkomme
				if ( strID.toLowerCase().equals("_00808f001") ) {

					containsLanguages	= true;
				}

			} // --- if (!(weraProduct instanceof WeraProductSet)) {
	
			// --- counter
			iCounter++;

		} // --- for (final Iterator it2 = attributesArticle.iterator(); it2.hasNext();) {
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		String strVKHTitle		= "";
		String strVKHWeight		= "";
		String strVKHDimension	= "";
		String strVKHQuantity	= "0";
		String strVKHTiefe		= "";
		String strVKHLength		= "";
		String strVKHWidth		= "";
		String strVKHHeight		= "";
		String strVKHPrice		= "-";
		if ( containsLanguages ) {

			// --- version VKH-Title = aktuelle sprache
			if ( hashTitle.containsKey(m_strLanguage) ) {
				strVKHTitle	= (String)hashTitle.get(m_strLanguage);
			}

		} else {

			// --- version VKH-Title = de | en
			if ( hashTitle.containsKey("de") ) {
				strVKHTitle	= (String)hashTitle.get("de");
			}
			if ( hashTitle.containsKey("en") && !hashTitle.get("en").equals(strVKHTitle) ) {
				strVKHTitle	+= " | " + (String)hashTitle.get("en");
			}
		}
		if (weraProduct instanceof WeraProductSet || weraProduct instanceof WeraProductSetinSet) {
			strVKHWeight	= (String)m_wm.getAttribute(weraProduct, "GewichtBrutto");
			strVKHTiefe		= (String)m_wm.getAttribute(weraProduct, "artikel_breite");
			strVKHWidth		= (String)m_wm.getAttribute(weraProduct, "artikel_laenge");
			strVKHHeight	= (String)m_wm.getAttribute(weraProduct, "artikel_hoehe");
			if ( strVKHWeight == null ) strVKHWeight	= "0";
			if ( strVKHWidth == null ) strVKHWidth		= "";
			if ( strVKHHeight == null ) strVKHHeight	= "";
			if ( strVKHTiefe == null ) strVKHTiefe		= "";
			strVKHDimension	= strVKHWidth + " x " + strVKHHeight + " x " + strVKHTiefe + " mm";
		}

		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		//output("initWeraProduct.2="+weraProduct.getCode());

		// --- Leer Spalten anlegen, auf�llen bis 7 -----------------------------------------------------------------
		int iCntDummyAttr = 7 - attributesArticle.size();
		if ( !m_isPriceListExport ) {

			// --- Katalog export
			if (m_strCurrentTemplateName.toUpperCase().equals("PRODUCT8")) {
				iCntDummyAttr = 8 - attributesArticle.size();
			}
			if (m_strCurrentTemplateName.toUpperCase().equals("PRODUCT16")) {
				iCntDummyAttr = 16 - attributesArticle.size();
			}
			
		} else {
		
			// --- preisliste export (max x spalten unabh�ngig vom template )
			iCntDummyAttr = m_maxAttributeCols - attributesArticle.size();
		}
		if (!(weraProduct instanceof WeraProductSet)) {
			for (; iCntDummyAttr > 0; iCntDummyAttr--) {
				final Integer intCntDummyAttr = iCntDummyAttr;
				//LOG.info("++Produkt=>Create Dummy kategory " + intCntDummyAttr.toString() );
				initMerkmal(oAttributeList, "dy" + intCntDummyAttr.toString(), "dy" + intCntDummyAttr.toString(), "numerisch", "",
						iOrder, "", "");
				iOrder++;
			}
		}
		// --- Leer Spalten anlegen, auf�llen bis 7 -----------------------------------------------------------------

		
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// preisdaten Kompaktkatalog (start)
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		iOrder++;
		if (weraProduct instanceof WeraProductSet || weraProduct instanceof WeraProductSetinSet) {
			
			// --- preise f�r verkaufsartikel (hier ausleitung �ber Metadata
			// initMerkmalPriceData ( oAttributeList, weraProduct, iOrder );
			
		} else {
			
			// --- preise f�r verkaufsartikel
			initMerkmalPriceData ( oAttributeList, weraProduct, iOrder );
		}



		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// TEXT-Attribute (nur Verkaufshilfen Katalog)
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		if ( !m_isPriceListExport ) {
			
			createTextElement(strVKHQuantity, oTextList, "BLT_VKH_QUANTITY");

			// --- create text-element (Varianten)
			createTextElement(strVKHTitle, oTextList, "BLT_VKH_TITLE");

			// --- create text-element (Varianten)
			createTextElement(strVKHWeight, oTextList, "BLT_VKH_WEIGHT");

			// --- create text-element (Varianten)
			createTextElement(strVKHDimension, oTextList, "BLT_VKH_DIMENSION");

			// --- create text-element (Varianten)
			createTextElement(strVKHPrice, oTextList, "BLT_VKH_PRICE");
			
		} // if ( !m_isPriceListExport ) {
		// --- create text-element (Varianten)
		
		////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		
		// --- Beschreibung f�r Tabellenkopf (ENDE)  -----------------------------------------------

		// --- Fussnoten
		String strFootnote = "";
		ArrayList colAllFootnotes = null;
		if (weraProduct instanceof WeraProductSet) {
			colAllFootnotes = (ArrayList) colFootnodesSet;
		} else {
			colAllFootnotes = new ArrayList();
		}

		// --- Hole alle aktiven Artikel des aktuellen Produkts
		output("initWeraProduct.Hole alle aktiven Artikel des aktuellen Produkts=", 2);

		final boolean bCheckForActivationOld = m_wm.m_bCheckForActivation;
		m_wm.m_bCheckForActivation = true;
		final Collection articles = m_wm.getVariants(weraProduct);
		m_wm.m_bCheckForActivation = bCheckForActivationOld;

		final Collection articlesSorted = new ArrayList();
		articlesSorted.addAll(articles);

		
		// --- TEXT-Beschreibung Bulletpoints ---------------------------------------------------------------------------------------------------
		Collection<Textbaustein> oBulletsTextbausteine	= null;
		// product
		if ( weraProduct instanceof WeraProductSet || weraProduct instanceof WeraProductSetinSet) {
			// --- satz
			oBulletsTextbausteine	= (Collection<Textbaustein>)m_wm.getAttribute(weraProduct, "weraproductset2bulletpoints");

		} else {

			// --- preset
			oBulletsTextbausteine	= new ArrayList();

			// --- Textbaustene-Liste anlegen
			HashMap<String,Textbaustein> oHashMapBullets	= new HashMap();

			// --- satz
			Collection<Textbaustein> oBulletsTextbausteineProdTmp	= (Collection<Textbaustein>)m_wm.getAttribute(weraProduct, "weraproductset2bulletpoints");
			if ( oBulletsTextbausteineProdTmp != null && oBulletsTextbausteineProdTmp.size() > 0 ) {
				for ( Textbaustein oBulletsTextbaustein : oBulletsTextbausteineProdTmp ) {

					String code = (String)m_wm.getAttribute(oBulletsTextbaustein, "code");
					if ( !oHashMapBullets.containsKey(code) ) {
						oHashMapBullets.put(code, oBulletsTextbaustein);
						oBulletsTextbausteine.add(oBulletsTextbaustein);
					}
				}
			}

			// --- Schleife �ber alle Artikel und Fussnoten zusammensuchen
			WeraVariante oWeraVariante	= null;
			for (final Iterator it1 = articlesSorted.iterator(); it1.hasNext();) {

				// --- Hole Artikel / Variante
				oWeraVariante = (WeraVariante) it1.next();
				if( oWeraVariante != null ) {

					Collection<Textbaustein> oBulletsTextbausteineTmp	= (Collection<Textbaustein>)m_wm.getAttribute(oWeraVariante, "weravariante2bulletpoints");
					if ( oBulletsTextbausteineTmp != null && oBulletsTextbausteineTmp.size() > 0 ) {
						for ( Textbaustein oBulletsTextbaustein : oBulletsTextbausteineTmp ) {
							
							String code = (String)m_wm.getAttribute(oBulletsTextbaustein, "code");
							if ( !oHashMapBullets.containsKey(code) ) {
								oHashMapBullets.put(code, oBulletsTextbaustein);
								oBulletsTextbausteine.add(oBulletsTextbaustein);
							}
						}
					}



					// --- ermitteln der zu exporierenden Bulletpoints -------------------------------------------------------------
					//Collection<Textbaustein> colBulletpointsByProducts	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weraproductset2bulletpoints");
					Collection<Textbaustein> colBulletpointsByVariante	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraVariante, "weravariante2bulletpoints");

					// --- sammeln der Marketingtextbaustein
					_collectTextbausteineBulletpoints ( colBulletpointsByVariante );
					// LOG.info("++++++ collect.m_colTextbausteinBullet.size=" + m_colTextbausteinBullet.size());
					// --- ermitteln der zu exporierenden Bulletpoints -------------------------------------------------------------

					// --- ermitteln der zu exporierenden Marketingtexte -----------------------------------------------------------
					//Collection<Textbaustein> colMarketingByProducts	= (Collection<Textbaustein>)m_wm.getAttribute( oWeraProduct, "weraproduct2marketing");
					Collection<Textbaustein> colMarketingByVariante		= (Collection<Textbaustein>)m_wm.getAttribute( oWeraVariante, "weravariante2marketing");

					// --- sammeln der Marketingtextbaustein
					_collectTextbausteineMarketing ( colMarketingByVariante );
					// LOG.info("++++++ collect.m_colTextbausteinMarketingtext.size=" + m_colTextbausteinMarketingtext.size());
					// --- ermitteln der zu exporierenden Marketingtexte -----------------------------------------------------------


				}
			}
			
			// --- textbausteine �bernehmen
			// oBulletsTextbausteine.addAll(oHashMapBullets.values());
		}
		// --- Textbausteine Bulletpoints
		if ( oBulletsTextbausteine != null ) {
			if ( !m_isPriceListExport ) {

				// --- generate text-list
				m_iOffset	= __generateTextbausteinBulletpointElement ( "BLT_BULLET", oTextList, oBulletsTextbausteine, "", new Integer(m_iOffset) );
			}
		}
		// --- TEXT-Beschreibung Bulletpoints ---------------------------------------------------------------------------------------------------

		// --- Aktionsbroschüre: Bulletpoints ---------------------------------------------------------------------------------------------------------#
		// --- hole komma getrennte Liste deer bulletpoint-Textbausteine

		// --- preset
		Collection<Textbaustein> oBulletsTextbausteineAK	= new ArrayList();

		String bulletpoints_print_selection			= (String)m_wm.getAttribute(weraProduct, "ab_bulletpoints_print_selection");
		// String bulletpoints_print_selection			= (String)m_wm.getAttribute(weraProduct, "bulletpoints_print_selection");
		if ( bulletpoints_print_selection == null ) { bulletpoints_print_selection = ""; }
		String[]	bulletpoints_print_selections	= bulletpoints_print_selection.split("#");
		// String exportBulletPointList	= "";

		// --- sind bulletpoints in der exportliste?
		if ( bulletpoints_print_selections != null && bulletpoints_print_selections.length > 0 ) {


			// --- schleife �ber alle bulletpoints
			for ( String bulletpointId : bulletpoints_print_selections ) {

				// --- bulletpoint bereinigen
				bulletpointId	= bulletpointId.trim();
				if ( bulletpointId.equals("") ) {
					continue;
				}

				// --- existiert der bulletpoint?
				if ( m_hashTextbausteine.containsKey(bulletpointId) ) {

					// --- hole Textbauustein
					Textbaustein bulletpoint	= m_hashTextbausteine.get(bulletpointId);
					oBulletsTextbausteineAK.add(bulletpoint);

				}

			} // for ( String bulletpointId : bulletpoints_print_selections ) {
		}
		// --- Textbausteine Bulletpoints
		if ( oBulletsTextbausteineAK != null ) {
			if ( !m_isPriceListExport ) {

				// --- generate text-list
				m_iOffset	= __generateTextbausteinBulletpointElement ( "BLT_BULLET_AK", oTextList, oBulletsTextbausteineAK, "", new Integer(m_iOffset) );
			}
		}
		// --- Aktionsbroschüre: Bulletpoints ---------------------------------------------------------------------------------------------------------#

		// --- Sonderbroschüre: Bulletpoints ---------------------------------------------------------------------------------------------------------#
		// --- hole komma getrennte Liste deer bulletpoint-Textbausteine

		// --- preset
		Collection<Textbaustein> oBulletsTextbausteineSB	= new ArrayList();

		String bulletpoints_print_selectionSB			= (String)m_wm.getAttribute(weraProduct, "sb_bulletpoints_print_selection");
		// String bulletpoints_print_selection			= (String)m_wm.getAttribute(weraProduct, "bulletpoints_print_selection");
		if ( bulletpoints_print_selectionSB == null ) { bulletpoints_print_selectionSB = ""; }
		String[]	bulletpoints_print_selectionsSB	= bulletpoints_print_selectionSB.split("#");
		// String exportBulletPointList	= "";

		// --- sind bulletpoints in der exportliste?
		if ( bulletpoints_print_selectionsSB != null && bulletpoints_print_selectionsSB.length > 0 ) {


			// --- schleife �ber alle bulletpoints
			for ( String bulletpointId : bulletpoints_print_selectionsSB ) {

				// --- bulletpoint bereinigen
				bulletpointId	= bulletpointId.trim();
				if ( bulletpointId.equals("") ) {
					continue;
				}

				// --- existiert der bulletpoint?
				if ( m_hashTextbausteine.containsKey(bulletpointId) ) {

					// --- hole Textbauustein
					Textbaustein bulletpoint	= m_hashTextbausteine.get(bulletpointId);
					oBulletsTextbausteineSB.add(bulletpoint);

				}

			} // for ( String bulletpointId : bulletpoints_print_selections ) {
		}
		// --- Textbausteine Bulletpoints
		if ( oBulletsTextbausteineSB != null ) {
			if ( !m_isPriceListExport ) {

				// --- generate text-list
				m_iOffset	= __generateTextbausteinBulletpointElement ( "BLT_BULLET_SB", oTextList, oBulletsTextbausteineSB, "", new Integer(m_iOffset) );
			}
		}
		// --- Sonderbroschüre: Bulletpoints ---------------------------------------------------------------------------------------------------------#


		if (!(weraProduct instanceof WeraProductSet) && (articlesSorted != null && articlesSorted.size() > 0)) {
			Collections.sort((List) articlesSorted, new OrderComparatorExport());
		}
		if (!(weraProduct instanceof WeraProductSet) && articlesSorted.size() > 0) {

			Element articelXML = null;
			WeraVariante oWeraVariante = null;

			// --- Schleife �ber alle Artikel und Fussnoten zusammensuchen
			for (final Iterator it1 = articlesSorted.iterator(); it1.hasNext();) {

				// --- Hole Artikel / Variante
				oWeraVariante = (WeraVariante) it1.next();

				// --- Hole Fussnoten = >Schleife �ber alle Attribute f�r Artikel
				final Collection footnotes = oWeraVariante.getFootnotes();
				for (final Iterator it2 = footnotes.iterator(); it2.hasNext();) {
					// --- Hole ProfiClassAttribute
					final Footnote oFootnote = (Footnote) it2.next();

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

				// ---- Auslaufartikel als Fussnote ausgeben??
				if (this.m_bExportDiscontinuedItemAsFootnote) {

					// --- Auslaufartikel in Fussnoten
					Boolean bArtikelAuslauf = (Boolean) m_wm.getAttribute(oWeraVariante, "artikel_auslauf");
					if (bArtikelAuslauf == null) {
						bArtikelAuslauf = new Boolean(false);
					}
					if (bArtikelAuslauf.booleanValue()) {

						// --- Fussnote (temp) anh�ngen
						LOG.info("+++Fussnote m_oFootnoteAuslaufartikelTmp (1) added=" + oWeraVariante.getCode());
						if (!colAllFootnotes.contains(m_oFootnoteAuslaufartikelTmp)) {
							colAllFootnotes.add(m_oFootnoteAuslaufartikelTmp);
						}
					}

				} // --- if ( this.m_bExportDiscontinuedItemAsFootnote ) {

			} // --- for (Iterator it1 = articlesSorted.iterator(); it1.hasNext();) {

			for (final Iterator it1 = articlesSorted.iterator(); it1.hasNext();) {
				// --- Hole Variante
				oWeraVariante = (WeraVariante) it1.next();

				// --- filter variante -------------------------------------------
				if (_isFilteredVariante(oWeraVariante)) {
					// LOG.info("+++Skip _isFilteredVariante Variant=" + oWeraVariante.getCode() );
					LOG.info("+++Skip _isFilteredVariante Variant (Auslaufartikel)=" + oWeraVariante.getCode());
					continue;
				}
				// --- filter variante -------------------------------------------
				
				

				// -- Z�hler
				m_iCountArticels++;

				// --- Pflege ArtikelReferenzliste
				// --- <ProductJoin JoinType="SET-PRODUCT" JoinedId="3252" JoinedType="PRODUCT" JoinedRefCode="481M" JoinedProductNumber="25574" JoinedWorkflow="OKE_DATEN" JoinedManufacturer="WIHA" JoinedEAN="4010995255749" Sequence="10" JoinTypeName="SET-PRODUCT" JoinedTypeName="PRODUCT" JoinedName="Wiha Magnetic Umsteckgriff f�r System 6." JoinedWorkflowName="OKE_DATEN" JoinedManufacturerDescription="Wiha">
				final Element oProductJoin = new Element("ProductJoin");
				oProductJoinList.addContent(oProductJoin);

				// --- <Product>
				articelXML = new Element("Product");
				output(oWeraVariante.getCode(), 2);


				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// TEXT-Attribute (nur Verkaufshilfen Katalog)
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// --- initialisierung
				strVKHWeight		= "";
				strVKHQuantity		= "0";
				strVKHDimension		= "";
				strVKHLength		= "";
				strVKHTiefe			= "";
				strVKHWidth			= "";
				strVKHHeight		= "";
				strVKHDimension		= "";
				strVKHPrice			= "";
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				// --- <Attribute-List>
				final Element oAttributeListArtikel = new Element("Attribute-List");
				articelXML.addContent(oAttributeListArtikel);

				// --- F�lle Datenzweig
				strFootnote = initWeraProductVariante(articelXML, oWeraVariante, colAllFootnotes, oProductJoin);

				// --- Artikelspezifische Merkmale
				if (oWeraVariante.getAttribute("lagerNr") == null) {
					m_wm.setAttribute(oWeraVariante, "lagerNr", "05");
				}
				if (oWeraVariante.getLocalizedProperty("variantenNr") == null) {
					oWeraVariante.setLocalizedProperty("variantenNr", "001");
				}
				// --
				final String strVarNr = (String) oWeraVariante.getLocalizedProperty("variantenNr");
				// --
				if (m_strLanguage.equals("us-en")) {
					strCode = m_oExportFormatter.formatCodeUS(oWeraVariante.getAttribute("lagerNr").toString(), oWeraVariante
							.getAttribute("code").toString(), strVarNr);
				} else {
					strCode = m_oExportFormatter.formatCode(oWeraVariante.getAttribute("lagerNr").toString(), oWeraVariante
							.getAttribute("code").toString(), strVarNr);
				}
				/*
				 * if ( m_strLanguage.equals("us-en") ) { strCode = "<ASCII-WIN>\r\n"; strCode +=
				 * "<Version:4><FeatureSet:InDesign-Roman><ColorTable:=<Black:COLOR:CMYK:Process:0.000000,0.000000,0.000000,1.000000><Black \\(CMYK\\):COLOR:CMYK:Process:0.000000,0.000000,0.000000,1.000000>>\r\n"
				 * ; strCode +=
				 * "<DefineCharStyle:wera\\_tabelle=<Nextstyle:wera\\_tabelle><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT>>\r\n"
				 * ; strCode +=
				 * "<DefineParaStyle:links ohne Einzug=<Nextstyle:links ohne Einzug><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:Black \\(CMYK\\)><pRuleBelowStroke:0.000000><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:Black \\(CMYK\\)>>\r\n"
				 * ; strCode +=
				 * "<DefineParaStyle:rechts Einzug 2.1mm=<Nextstyle:rechts Einzug 2.1mm><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><pHyphenationLadderLimit:0><pRightIndent:5.952756><pMinCharBeforeHyphen:3><pHyphenateCapitals:0><pShortestWordHyphenated:6><pHyphenationZone:0.000000><cFont:Helvetica Neue LT><pDesiredWordSpace:1.100000><pMaxWordSpace:2.500000><pMinWordSpace:0.850000><pMaxLetterspace:4.000000><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:wera\\_icons\\_bg><pRuleBelowStroke:0.198425><pRuleBelowOffset:2.834646><pRuleBelowStrokeType:CannedDash3x2><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:white \\(CMYK\\)><pTextAlignment:Right>>\r\n"
				 * ; strCode +=
				 * "<DefineParaStyle:center\\_wera=<Nextstyle:center\\_wera><cColor:Black \\(CMYK\\)><cTypeface:LT 47 Light Condensed><cSize:7.000000><cFont:Helvetica Neue LT><pRuleAboveColor:Black \\(CMYK\\)><pRuleAboveStroke:0.000000><pRuleBelowColor:Black \\(CMYK\\)><pRuleBelowStroke:5.669291><pRuleBelowOffset:0.850394><pRuleBelowStrokeType:Dashed><pRuleAboveGapColor:Black \\(CMYK\\)><pRuleBelowGapColor:Black \\(CMYK\\)><pTextAlignment:Center>>\r\n"
				 * ; strCode += "<ParaStyle:links ohne Einzug><cSize:6.000000>" +
				 * oWeraVariante.getAttribute("lagerNr").toString() + "<cSize:><cTypeface:Bold>" +
				 * oWeraVariante.getAttribute("code").toString() + "<cTypeface:><cSize:6.000000>" + strVarNr + "<cSize:>"; }
				 * else strCode = oWeraVariante.getAttribute("lagerNr").toString() + "##b##" +
				 * oWeraVariante.getAttribute("code").toString() + "##e_b##" + strVarNr;
				 */
				iOrder = 0;
				if (m_strLanguage.equals("ru")) {
					// --- "???"
					initMerkmalAttributeType(oAttributeListArtikel, "code", "CODE", strType, strCode, iOrder, "ICON_CODE",
							"\u043A\u043E\u0434");
				} else {
					
					if ( !m_isPriceListExport ) {
						initMerkmalAttributeType(oAttributeListArtikel, "code", "CODE", strType, strCode, iOrder, "ICON_CODE", "");
					} else {
						initMerkmalAttributeType(oAttributeListArtikel, "code", "CODE", strType, strCode, iOrder, "ICON_CODE", "Code");
					}
				}

				
				iOrder++;
				initMerkmalAttributeType(oAttributeListArtikel, "notiz", "NOTIZ", strType, strFootnote, iOrder, "", "");

				// --- Artikelspezifische Merkmale
/*				
				iOrder++;
				if (oWeraVariante.getAttribute("contentQuantity") == null) {
					initMerkmal(oAttributeListArtikel, "contentQuantity", "Menge", "numerisch", "0", iOrder,
							"ICON_VERPACKUNGSEINHEIT", "");
				} else {
					initMerkmal(oAttributeListArtikel, "contentQuantity", "Menge", "numerisch",
							oWeraVariante.getAttribute("contentQuantity").toString(), iOrder, "ICON_VERPACKUNGSEINHEIT", "");
					
					strVKHQuantity	= oWeraVariante.getAttribute("contentQuantity").toString();
				}
				initMerkmal(oAttributeListArtikel, "preis", "Preis", "numerisch", "", iOrder, "ICON_PREIS", " ");
*/
				
				iOrder++;
				String strEAN = "";
				strEAN = (String) m_wm.getAttribute(oWeraVariante, "ean");
				if ( strEAN == null ) {
					strEAN = "";
				}
				if (strEAN == null || strEAN.length() == 0 || strEAN.length() < 8) {
					strEAN = "";
				} else {
					strEAN = strEAN.substring(7);
				}
				if ( m_isPriceListExport ) {
					initMerkmalAttributeType(oAttributeListArtikel, "ean", "EAN", strType, strEAN, iOrder, "ICON_EAN", " ");
				}
				
				// --- Schleife �ber alle Attribute f�r Artikel
				output("initWeraProduct.Schleife �ber alle Artikel-Merkmale=" + oWeraVariante.getAttribute("code"), 1);
				categoryByCa			= null;
				String strAttrContent	= "";
				iCounter				= 0;
				LOG.info(oWeraVariante.getAttribute("code").toString() + ", attributesArticle.size()=" + attributesArticle.size());
				for (final Iterator it2 = attributesArticle.iterator(); it2.hasNext();) {

					//LOG.info(oWeraVariante.getAttribute("code").toString() + ", add ca=" + oClassificationAttribute.getCode());
					// --- Hole ClassificationAttribute / ClassAttributeAssignment
					oClassAttributeAssignment = (ClassAttributeAssignment) it2.next();
					oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();
					output(
							"initWeraProduct.oClassificationAttribute.getAttribute(code)="
							+ oClassificationAttribute.getAttribute("code"), 2);

					// --- F�lle Datenzweig und �bernehme Merkmale (ProfiClassClassificationAttribute) in den Artikel
					categoryByCa = (Category) hashCategory.get(oClassificationAttribute.getAttribute("code"));
					iOrder++;

					if (oClassificationAttribute.getCode().equals("AAA972f001")
							|| oClassificationAttribute.getCode().equals("AAC551f001")
							|| oClassificationAttribute.getCode().equals("AAA358f001")
							|| oClassificationAttribute.getCode().equals("_AAA972f001")) {
						// --- Wert auftrennen => Farbeliste
						strAttrContent	= initClassificationAttributes(oWeraVariante, oAttributeListArtikel, oClassAttributeAssignment, categoryByCa,
								iOrder, 1);
						strAttrContent	= initClassificationAttributes(oWeraVariante, oAttributeListArtikel, oClassAttributeAssignment, categoryByCa,
								iOrder, 2);
					} else {
						strAttrContent	= initClassificationAttributes(oWeraVariante, oAttributeListArtikel, oClassAttributeAssignment, categoryByCa,
								iOrder, 0);
					}
					
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					// TEXT-Attribute (nur Verkaufshilfen Katalog) - VARIANTEN (PRODUCT-Template)
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////#
					if ( oClassificationAttribute.getCode().equals("_AA040f001") ) {
						
						strVKHWidth	= strAttrContent;
					}
					if ( oClassificationAttribute.getCode().equals("_AA031f001") ) {
						
						strVKHHeight	= strAttrContent;
					}
					if ( oClassificationAttribute.getCode().equals("_00089f001") ) {
						
						strVKHTiefe	= strAttrContent;
					}
					if ( oClassificationAttribute.getCode().equals("_00804f001") ) { // gewicht
						
						strVKHWeight	= strAttrContent;
					}
					if ( oClassificationAttribute.getCode().equals("_00809f001") ) {
						
						strVKHPrice	= strAttrContent;
					}
					strVKHDimension	= strVKHWidth + " x " + strVKHHeight;
					if ( !strVKHTiefe.equals("") ) {
						
						strVKHDimension	+= " x " + strVKHTiefe;
					}
					strVKHDimension	+= " mm";
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
					// TEXT-Attribute (nur Verkaufshilfen Katalog)
					////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	
					// --- counter
					iCounter++;
					
				} // for (final Iterator it2 = attributesArticle.iterator(); it2.hasNext();) {
				//output("initWeraProduct.2="+weraProduct.getCode());

				// --- Leer Spalten anlegen, auf�llen bis 7
				if (!(weraProduct instanceof WeraProductSet)) {

					// --- Leer Spalten anlegen, auf�llen bis 7
					if ( !m_isPriceListExport ) {

						// --- Katalog export
						iCntDummyAttr = 7 - attributesArticle.size();
						if (m_strCurrentTemplateName.toUpperCase().equals("PRODUCT8")) {
							iCntDummyAttr = 8 - attributesArticle.size();
						}
						if (m_strCurrentTemplateName.toUpperCase().equals("PRODUCT16")) {
							iCntDummyAttr = 16 - attributesArticle.size();
						}

					} else {

						// --- preisliste export (max x spalten unabh�ngig vom template )
						iCntDummyAttr = m_maxAttributeCols - attributesArticle.size();
					}
					
					
					for (; iCntDummyAttr > 0; iCntDummyAttr--) {
						final Integer intCntDummyAttr = iCntDummyAttr;
						_debug(oWeraVariante.getAttribute("code").toString(),
								"++Artikel=>Create Dummy kategory " + intCntDummyAttr.toString());
						//LOG.info("++Artikel=>Create Dummy kategory " + intCntDummyAttr.toString() );
						initMerkmal(oAttributeListArtikel, "dy" + intCntDummyAttr.toString(), "dy" + intCntDummyAttr.toString(),
								"numerisch", "", iOrder, "", "");
						iOrder++;
					}
				}
				
				//////////////////////////////////////////////////////////////////////////////////
				// PREISINFO (START)
				//////////////////////////////////////////////////////////////////////////////////
				iOrder++;
			    iOrder	= initMerkmalPriceData ( oAttributeListArtikel, oWeraVariante, iOrder );
	

				//////////////////////////////////////////////////////////////////////////////////
				// PREISINFO (ENDE)
				//////////////////////////////////////////////////////////////////////////////////
				
				
				//--------------
				final List enumChilds = oAttributeListArtikel.getChildren();
				final Iterator iter = enumChilds.iterator();
				while (iter.hasNext()) {
					final Element childElement = (Element) iter.next();
					_debug(oWeraVariante.getAttribute("code").toString(), " childElement.getAttribute('Id')="
							+ childElement.getAttribute("Id").getValue());

				}
				//--------------
				// --- �bernehme Artikel als Produkt
				oProductList.addContent(articelXML);


				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				// TEXT-Attribute (nur Verkaufshilfen Katalog)
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
				if ( !m_isPriceListExport ) {
					
					// --- <Text-List>
					final Element oTextListArtikel = new Element("Text-List");
					articelXML.addContent(oTextListArtikel);

					// --- create text-element (Varianten)
					createTextElement(strVKHQuantity, oTextListArtikel, "BLT_VKH_QUANTITY");

					// --- create text-element (Varianten)
					createTextElement(strVKHTitle, oTextListArtikel, "BLT_VKH_TITLE");

					// --- create text-element (Varianten)
					createTextElement(strVKHWeight, oTextListArtikel, "BLT_VKH_WEIGHT");

					// --- create text-element (Varianten)
					createTextElement(strVKHDimension, oTextListArtikel, "BLT_VKH_DIMENSION");

					// --- create text-element (Varianten)
					createTextElement(strVKHPrice, oTextListArtikel, "BLT_VKH_PRICE");
					
				} // if ( !m_isPriceListExport ) {
				////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

				
			} // --- if ( products.size() ) {

		} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();) {
		//output("initWeraProduct.3="+weraProduct.getCode());

		// --- Alle gesammelten Fussnoten ins Produkt �bernehmen
		// --- Schleife �ber alle Fussnoten
//LOG.info("1. colAllFootnotes.size()=" + colAllFootnotes.size());
		Element textXML1 = null;
		Footnote oFootnote = null;
		String strFN = "";
		int iLfrNr = 1;
		for (final Iterator it2 = colAllFootnotes.iterator(); it2.hasNext();) {
			// --- Hole ProfiClassAttribute
			oFootnote = (Footnote) it2.next();

			// --- Neues XML-Element
			strCode = oFootnote.getAttribute("code").toString();
// LOG.info("1. colAllFootnotes.strCode()=" + strCode);

			// --- Artikel entfernen
			if (strCode.contains("_")) {
				strCode = strCode.substring(strCode.indexOf('_') + 1);
			}

			// --- Notiz initialisieren
			m_iOffset++;

			// --- TEXT-Fussnote ----------------------------------------------------------------------------------------------------------------

			HashMap hashFootnote 	= new HashMap();
			boolean hideFN			= true;
			strFN = getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
			if (strFN == null) {
				strFN = "";
			}
			strFN = strFN.trim();
			// ---- fussnote ausblenden?
			if ( !strFN.equals("") ) {
				// --- fussnote anzeigen
				hideFN	= false;
			}
			hashFootnote.put(m_strLanguage, strFN);
			if (m_bMultilayer) {
				for (String strAdditionalLange : m_coAdditionallLanguages) {

					// --- Setze Sprache, und Defaultsprache=de
					Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(strAdditionalLange);
					m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);

					// --- TEXT-Beschreibung
					strFN = getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
					if (strFN == null) {
						strFN = "";
					}
					strFN = strFN.trim();
// LOG.info("1. colAllFootnotes.strFN=" + strFN);

					// ---- fussnote ausblenden?
					if ( !strFN.equals("") ) {
						// --- fussnote anzeigen
						hideFN	= false;
					}
					hashFootnote.put(strAdditionalLange, strFN);
				}

				// --- Setze Sprache, und Defaultsprache=de
				final Language m_Language = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
				m_jaloSession.getSessionContext().setLanguage(m_Language);
				m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);

			}
			// --- TEXT-Fussnote ----------------------------------------------------------------------------------------------------------------
			if ( !hideFN ) {

				if ((weraProduct instanceof WeraProductSet)) {

					textXML1 = createTextElementFootnote(new Integer(iLfrNr++).toString() + ")", hashFootnote, m_iOffset, m_iOffset, "BLT_FN", true);

				} else {

					textXML1 = createTextElementFootnote(new Integer(oFootnote.getLfdNr()).toString() + ")", hashFootnote, m_iOffset, m_iOffset, "BLT_FN", true);
				}

			} //

			// --- Fussnotenz�hler erh�hen
			m_iCntFussnoten++;

			// --- �bernehme Notiz in das Produkt
			if ( textXML1 != null ) { oTextList.addContent(textXML1); }

		} // for (final Iterator it2 = colAllFootnotes.iterator(); it2.hasNext();) {


		// --- Hole Fussnoten die am Produkt liegem = >Schleife �ber alle Attribute f�r Artikel
		final Collection footnotes = weraProduct.getFootnotes();
		Collection allFootnotes = new ArrayList();
		allFootnotes.addAll(footnotes);
		//LOG.info("2. allFootnotes.size()=" + allFootnotes.size() + " / " + weraProduct.getCode());

		oFootnote = null;
		strFN = "";
		String strKennz = "";
		for (final Iterator it2 = allFootnotes.iterator(); it2.hasNext();) {
			// --- Hole ProfiClassAttribute
			oFootnote = (Footnote) it2.next();
			// LOG.info("allFootnotes=" + getAttribute(oFootnote, "kennzeichner"));

			// --- TEXT-Fussnote ----------------------------------------------------------------------------------------------------------------
			// --- Notiz initialisieren
			HashMap hashFootnoteProduct = new HashMap();
			boolean hideFNProduct		= true;
			strFN = getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
			if (strFN == null) {
				strFN = "";
			}
			strFN = strFN.trim();
			// ---- fussnote ausblenden?
			if ( !strFN.equals("") ) {
				// --- fussnote anzeigen
				hideFNProduct	= false;
			}
			hashFootnoteProduct.put(m_strLanguage, strFN);
			if (m_bMultilayer) {
				for (String strAdditionalLange : m_coAdditionallLanguages) {

					// --- Setze Sprache, und Defaultsprache=de
					Language m_LanguageTmp = m_jaloSession.getC2LManager().getLanguageByIsoCode(strAdditionalLange);
					m_jaloSession.getSessionContext().setLanguage(m_LanguageTmp);

					// --- TEXT-Beschreibung
					strFN = getValidString(oFootnote.getName(m_jaloSession.getSessionContext()));
					if (strFN == null) {
						strFN = "";
					}
					strFN = strFN.trim();

					// ---- fussnote ausblenden?
					if ( !strFN.equals("") ) {
						// --- fussnote anzeigen
						hideFNProduct	= false;
					}
					hashFootnoteProduct.put(strAdditionalLange, strFN);
				}

				// --- Setze Sprache, und Defaultsprache=de
				final Language m_Language = m_jaloSession.getC2LManager().getLanguageByIsoCode(m_strLanguage);
				m_jaloSession.getSessionContext().setLanguage(m_Language);
				m_jaloSession.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);

			}
			// --- TEXT-Fussnote ----------------------------------------------------------------------------------------------------------------
			if ( !hideFNProduct ) {

				strKennz = (String) getAttribute(oFootnote, "kennzeichner");
				m_iOffset++;
				if (strKennz != null) {
					textXML1 = createTextElementFootnote(getAttribute(oFootnote, "kennzeichner") + ")",
							hashFootnoteProduct, m_iOffset, m_iOffset, "BLT_FN", true);
				} else {
					textXML1 = createTextElementFootnote(")", hashFootnoteProduct, m_iOffset, m_iOffset, "BLT_FN", true);
				}

				// --- Fussnotenz�hler erh�hen
				m_iCntFussnoten++;

				// --- �bernehme Notiz in das Produkt
				oTextList.addContent(textXML1);

			} // if ( !hideFNProduct ) {

		} // --- for (Iterator it2 = footnotes.iterator(); it2.hasNext();) {

		// --- Mind. 1 Fussnote
		if (false && m_bTextwechsel
				&& ((allFootnotes == null || allFootnotes.size() == 0) && (colAllFootnotes == null || colAllFootnotes.size() == 0))) {
			// --- TEXT-Fussnote ----------------------------------------------------------------------------------------------------------------
			HashMap hashFootnoteDummy = new HashMap();
			hashFootnoteDummy.put(m_strLanguage, "");
			if (m_bMultilayer) {
				for (String strAdditionalLange : m_coAdditionallLanguages) {
					// --- TEXT-Beschreibung
					hashFootnoteDummy.put(strAdditionalLange, "");
				}
			}
			// --- TEXT-Fussnote ----------------------------------------------------------------------------------------------------------------

			// --- Dummy Fussnote
			m_iOffset++;
			textXML1 = createTextElementFootnote(" ", hashFootnoteDummy, m_iOffset, m_iOffset, "BLT_FN", true);
			oTextList.addContent(textXML1);

			// --- Fussnotenz�hler erh�hen
			m_iCntFussnoten++;
		}

		// --- eHyperlink-List (optional)
		if ( this.m_exportHyperLinks ) {
			createHyperlinkList(oTextList, m_firstcodeNr);
		}
		// --- eHyperlink-List (optional)

		
		// --- Setze Templatetyp
		LOG.info("set TypeName >"+strTemplate + "<");
		produktXML.setAttribute("TypeName", strTemplate);
		produktXML.setAttribute("FileName", WeraProduct.s_normalizeFilenameForImageLookup(weraProduct.getCode()));
		produktXML.setAttribute("LinkedType", strTemplate);

		// --- Aufr�umen
		output("e.initWeraProduct=" + weraProduct.getCode(), 2);
		// LOG.info("e.initWeraProduct="+weraProduct.getCode());
		return strTemplate;
	}

	/**
	 *
	 * @param weramedia
	 * @param strDirectory
	 * @param cProductNr
	 * @param bProductBild
	 * @return
	 */
	protected Element _initImage(final WeraMedia weramedia, String strDirectory, String cProductNr, final boolean bProductBild, String strTagName) {

		//output("s._initImage=", 2);
		// --- Korrektur - Alle Grafiken aus einem Bilderordner
		strDirectory = "pictures";
		if ( isM_bAktionsprospekt() && (strTagName.equals("ICON1") || strTagName.equals("ICON2")) ) {

			strDirectory = "pictures/aktionen";
		}
		
		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		final Element oFile = new Element("File");
		m_iOffset++;
		oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + "test_trans.eps"));
		if (weramedia != null) {

			//oFile.setAttribute("PrintMimeType", "image/eps" );
			if (bProductBild) {
				// --- Produktbild
				cProductNr = WeraProduct.s_normalizeFilenameForImageLookup(cProductNr);
				String strFileExtenion;
				if ( !strTagName.contains("PICTURE") ) {
					strFileExtenion	= ".eps";
				} else {
					strFileExtenion	= ".jpg";
				}
				oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + cProductNr + strFileExtenion));
				
			} else {
				String strRealName = weramedia.getRealFileName();
				if (strRealName != null) {
					strRealName = strRealName.replace("\\", "/");
					final Pattern p = Pattern.compile("/");
					final String[] aRealName = p.split(strRealName);
					if (aRealName.length > 1) {
						strRealName = aRealName[aRealName.length - 1];
					}
					if ( !strTagName.contains("PICTURE") ) {
						strRealName = strRealName.replaceAll(".jpg", ".eps");
						strRealName = strRealName.replaceAll(".gif", ".eps");
					} else {
						strRealName = strRealName.replaceAll(".gif", ".jpg");
						strRealName = strRealName.replaceAll(".eps", ".jpg");
					}
					oFile.setAttribute("PrintFileName", getExportFormatter().formatIconPath(strDirectory + "/" + strRealName));
				}
			}
		} else {
					
			
			// --- Bereinige ProduktNr f�r Bildsuche
			if (!cProductNr.equals("")) {

				cProductNr = WeraProduct.s_normalizeFilenameForImageLookup(cProductNr);
				String strFileExtenion;
				if ( !strTagName.contains("PICTURE") ) {
					strFileExtenion	= ".eps";
				} else {
					strFileExtenion	= ".jpg";
				}
				oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + cProductNr + strFileExtenion ));

			} else {
				m_iOffset++;
				oFile.setAttribute("PrintFileName", getExportFormatter().formatPicturePath(strDirectory + "/" + "test_trans.eps"));
			}
		}

		//output("e._initImage=", 2);
		return oFile;
	}

	/**
	 *
	 * @param classattributeassignment
	 * @param outputcontrol
	 * @param weramedia
	 * @param strHashCode
	 * @param strDirectory
	 * @param strOrder
	 * @return
	 */
	protected Element _initMerkmalIcon(final ClassAttributeAssignment classattributeassignment, final Outputcontrol outputcontrol,
			final WeraMedia weramedia, final String strHashCode, String strDirectory, final String strOrder) {

		// LOG.info("_initMerkmalIcon() called.");
		// --- Initialize
		ClassificationAttribute classificationAttribute = null;
		if (classattributeassignment != null) {
			classificationAttribute = classattributeassignment.getClassificationAttribute();
		}
		/*
		 // --- Debug
		 if (strHashCode != null)
		 {
		 output("s._initMerkmalIcon=" + strHashCode, 2);
		 }
		 else
		 {
		 output("s._initMerkmalIcon=", 2);
		 }
		 */
		// --- Korrektur - Alle Grafiken aus einem Bilderordner
		strDirectory = "pictures";

		// --- Initialize
		final Element oMotive = new Element("Motive");
		WeraMedia icon = null;
		String strIconCode = strHashCode;
		String strIconRealName = "";
		String strCaCode = "";
		Collection icons = new ArrayList();
		if (classificationAttribute != null) {
			strCaCode = classificationAttribute.getCode();
		}

		// --- �bernehme �bergegebenes Object
		if (weramedia != null) {
			icons.add(weramedia);
		} else {
			// --- Hole Icon aus Ausgabesteuerung
			if (outputcontrol != null) {
				icons = (Collection) getAttribute(outputcontrol, "icons");
			}
			if (icons == null || icons.size() == 0) {
				// --- Fallback aus CA
				if (classificationAttribute != null) {
					icons = (Collection) getAttribute(classattributeassignment, "icons");
				}
			}

		}

		// --- Wurde ein Icon gefunden
		if (icons != null && icons.size() > 0) {

			// --- Initialisiere Daten
			icon = (WeraMedia) icons.iterator().next();
			strIconCode = icon.getCode() + "_" + strOrder;
			strIconRealName = icon.getRealFileName();
		}
		if (strIconRealName != null) {
			strIconRealName = strIconRealName.replaceAll(".jpg", ".eps");
			strIconRealName = strIconRealName.replaceAll(".gif", ".eps");
		} else {
			strIconRealName = "";
		}

		// --- Neues Element anlegen
		// --- <Motive Id="0" Type="ATTRIBUTE_IMAGE" LinkType="ATTRIBUTE_IMAGE" RefCode="ATTR_aussen_sechskant_durchmesser" Thumbnail="" Sequence="0" TypeName="ATTRIBUTE_IMAGE" LinkTypeName="ATTRIBUTE_IMAGE" Name="Leertext" Keywords="">
//		output("s._initMerkmalIcon.strIconCode=" + strIconCode, 2);
//		output("s._initMerkmalIcon.strOrder=" + strOrder, 2);
//		output("s._initMerkmalIcon.strIconRealName=" + strIconRealName, 2);
//		output("s._initMerkmalIcon.strCaCode=" + strCaCode, 2);
		oMotive.setAttribute("Id", strIconCode);
		oMotive.setAttribute("Type", "ATTRIBUTE_IMAGE");
		oMotive.setAttribute("LinkType", "ATTRIBUTE_IMAGE");
		oMotive.setAttribute("RefCode", strIconCode);
		//oMotive.setAttribute("Thumbnail",    "" );
		oMotive.setAttribute("Sequence", strOrder);
		oMotive.setAttribute("TypeName", strIconRealName);
		oMotive.setAttribute("LinkTypeName", strCaCode);
		oMotive.setAttribute("Name", strIconRealName);
		//oMotive.setAttribute("Keywords",     "" );

		// --- Attribute-Header-List (ICON)
		if (strHashCode != null) {
			final HashMap hashCA = (HashMap) m_hashAttributeHeaderList.get(strHashCode);
			if (hashCA != null) {
				hashCA.put("FileName", "");
				hashCA.put("PrintFileName", "");
				if (icon != null) {
					hashCA.put("FileName", icon.getFileName());

					hashCA.put("PrintFileName", getExportFormatter().formatIconPath(strDirectory + "/" + strIconRealName));
					// LOG.info("Calling getExportFormatter() getting " + getExportFormatter().formatMerkmalePath ( strDirectory + "/" + strIconRealName) );
					m_hashAttributeHeaderList.put(strHashCode, hashCA);
				}
			}
		}

		// --- Pr�fe OutputControl
		final String stringUnit = "";

		// --- <File-List>
		final Element oFileList = new Element("File-List");
		
		
		// --- <File Id="0" MimeType="image/gif" Media="INTERNET" FileName="\images\pictos\aussen_sechskant_durchmesser.gif" Thumbnail="" ColorDepth="" Height="" Size="" Software="" Author="" Sequence="10" MediaName="INTERNET" Keywords="">
		final Element oFile = _initImage(icon, strDirectory, "", false, "ATTRIBUTE_IMAGE" );
		strIconRealName = oFile.getAttributeValue("PrintFileName");
//		output("s._initMerkmalIcon.oFile=" + oFile, 2);
//		output("s._initMerkmalIcon.strIconRealName=" + strIconRealName, 2);
		if (strIconRealName == null) {
			strIconRealName = "";
		}
		
		// --- SB-Icon in Preisliste
		//LOG.info("++++++s._initMerkmalIcon.m_isPriceListExport=" + m_isPriceListExport);
		//LOG.info("++++++s._initMerkmalIcon.strIconCode=" + strIconCode );
		//LOG.info("++++++s._initMerkmalIcon.m_bSB_faehig.booleanValue(=" + m_bSB_faehig.toString());
		if ( m_isPriceListExport && strIconCode.equals( "ICON_CODE_0" ) && m_bSB_faehig.booleanValue() ) {
			strIconRealName	= "pictures/SB_loch.eps";
			oFile.setAttribute("PrintFileName", strIconRealName );
			//LOG.info("++++++SET +++++ s._initMerkmalIcon.strIconRealName=" + strIconRealName );
		}
		//LOG.info("++++++s._initMerkmalIcon.strIconRealName=" + strIconRealName );
		
		// --- vpe-Icon in Preisliste
		//LOG.info("++++++s._initMerkmalIcon.m_isPriceListExport=" + m_isPriceListExport);
		//LOG.info("++++++s._initMerkmalIcon.strIconCode=" + strIconCode );
		//LOG.info("++++++s._initMerkmalIcon.m_bSB_faehig.booleanValue(=" + m_bSB_faehig.toString());
		if ( m_isPriceListExport && strIconCode.equals( "ICON_PRBRUTTO_QUANTITY_0" ) ) {
			strIconRealName	= "pictures/minikatalog-verpackung.eps";
			oFile.setAttribute("PrintFileName", strIconRealName );
			//LOG.info("++++++SET +++++ s._initMerkmalIcon.strIconRealName=" + strIconRealName );
		}
		
		if ( m_isPriceListExport && strIconCode.equals( "ICON_PRBRUTTO_QUANTITY_0" ) ) {
			strIconRealName	= "pictures/minikatalog-verpackung.eps";
			oFile.setAttribute("PrintFileName", strIconRealName );
			//LOG.info("++++++SET +++++ s._initMerkmalIcon.strIconRealName=" + strIconRealName );
		}
		
//		output("s._initMerkmalIcon.strIconRealName=" + strIconRealName, 2);
//		output("s._initMerkmalIcon.oFile=" + oFile, 2);
		oMotive.setAttribute("TypeName", strIconRealName);
		oMotive.setAttribute("Name", strIconRealName);
		oFileList.addContent(oFile);

		// --- Setze zusammen
		oMotive.addContent(oFileList);

		// --- Debug
//		output("e._initMerkmalIcon=", 2);
		//LOG.info("_initMerkmalIcon() finished.");
		return oMotive;
	}

	/**
	 *
	 * @param oAttributeValueList
	 * @param strID
	 * @param strBez
	 * @param strOrder
	 * @return
	 */
	protected Element _initMerkmalAttribute(final Element oAttributeValueList, final String strID, final String strBez,
			final String strOrder) {
		// --- Debug
//		LOG.info ("s._initMerkmalAttribute=" + strID);
//		output("s._initMerkmalAttribute=", 2);

		// --- Neues Element anlegen
		final Element oAttribute = new Element("Attribute");

		// --- Bezeichnung
		final Comment commentName = new Comment(strBez);
		oAttribute.addContent(commentName);

		// --- F�lle Content
		if (m_strLanguage.equals("de")) {
			oAttribute.setAttribute("Alttag", strBez);
		}
		oAttribute.setAttribute("Id", strID);
		oAttribute.setAttribute("Type", "TECHNISCHE_MERKMALE");
		oAttribute.setAttribute("Sequence", strOrder);
		oAttribute.setAttribute("Background", "N");
		//oAttribute.setAttribute("TypeName",  "TECHNISCHE_MERKMALE" );
		//oAttribute.setAttribute("Name",  strBez );
		//oAttribute.setAttribute("RefCode",   strBez );
		//oAttribute.setAttribute("ProductNumberExtension",  "" );
		//oAttribute.setAttribute("Description",    "" );
		String strTab = "";
		if (strID.equals("contentQuantity")) {
			strTab = "Z";
		} else {
			strTab = "L";
		}
		if (strID.toLowerCase().equals("code")) {
			strTab = "Z";
		}
		oAttribute.setAttribute("Tabulator", strTab);
		oAttribute.setAttribute("Einhtabulator", strTab);
		oAttribute.addContent(oAttributeValueList);

		// --- Debug
		output("e._initMerkmalAttribute=", 2);
		return oAttribute;
	}

	/**
	 *
	 * @param oAttributeValueList
	 * @param strID
	 * @param strBez
	 * @param strOrder
	 * @return
	 */
	protected Element _initMerkmalAttribute(final Element oAttributeValueList, final String strID, final String strBez,
			final String strOrder, final String strType ) {
		// --- Debug
//		LOG.info ("s._initMerkmalAttribute=" + strID);
//		output("s._initMerkmalAttribute=", 2);

		// --- Neues Element anlegen
		final Element oAttribute = new Element("Attribute");

		// --- Bezeichnung
		final Comment commentName = new Comment(strBez);
		oAttribute.addContent(commentName);

		// --- F�lle Content
		if (m_strLanguage.equals("de")) {
			oAttribute.setAttribute("Alttag", strBez);
		}
		oAttribute.setAttribute("Id", strID);
		oAttribute.setAttribute("Type", strType);
		oAttribute.setAttribute("Sequence", strOrder);
		oAttribute.setAttribute("Background", "N");
		String strTab = "";
		if (strType.equals("PREIS")) {
			strTab = "R";
		} else {
			strTab = "L";
		}
		oAttribute.setAttribute("Tabulator", strTab);
		oAttribute.setAttribute("farbcode", "N");
		oAttribute.setAttribute("Einhtabulator", strTab);
		oAttribute.addContent(oAttributeValueList);

		// --- Debug
		return oAttribute;
	}

	/**
	 *
	 * @param oAttributeValue
	 * @return
	 */
	protected Element _initMerkmalAttributeValueList(final Element oAttributeValue) {
		// --- Debug
		//output("s._initMerkmalAttributeValueList=", 2);

		// --- Neues Element anlegen
		final Element oAttributeValueList = new Element("AttributeValue-List");

		// --- F�lle Content
		oAttributeValueList.addContent(oAttributeValue);

		// --- Debug
		//output("e._initMerkmalAttributeValueList=", 2);
		return oAttributeValueList;
	}

	/**
	 *
	 * @param strID
	 * @param strBez
	 * @param strType
	 * @param strWert
	 * @param strOrder
	 * @return
	 */
	protected Element _initMerkmalAttributeValue(final String strID, final String strBez, final String strType,
			String strWert, final String strOrder) {
		// --- Debug
		//output("s._initMerkmalAttributeValue=", 2);

		// --- Neues Element anlegen
		final Element oAttributeValue = new Element("AttributeValue");

		// --- Value
		final Element oValue = new Element("Value");
		//Element oText      = new Element("Text");
		//Element oTextBlock = new Element("TextBlock");
		oAttributeValue.addContent(oValue);
		//oAttributeValue.addContent(oText);
		if (strID.length() > 0) {
			oAttributeValue.setAttribute("Id", strID);
		} else {
			oAttributeValue.setAttribute("Id", "empty");
			//oAttributeValue.setAttribute("RefCode", strType );
			//oAttributeValue.setAttribute("Type", strType );
		}
		
		// --- flaggen (Merkmal Sprache)
		if (strID.toLowerCase().equals("_00808f001")) {
			if ( strWert == null ) {
				strWert = "";
			}
			strWert = "pictures/" + strWert.toLowerCase() + ".eps";
		}

		oValue.addContent(m_oExportFormatter.formatDescription(strWert));

		/*
		 * oText.addContent(oTextBlock); oText.setAttribute("Id", "TD:" ); oText.setAttribute("Type", "TD:" );
		 * oText.setAttribute("TypeName", "TD:" ); oText.setAttribute("Sequence", strOrder );
		 * oTextBlock.setAttribute("Language", m_strLanguage );
		 */
		// --- Debug
		//output("e._initMerkmalAttributeValue=", 2);
		return oAttributeValue;
	}

	/**
	 *
	 * @param oAttributeValue
	 * @param classattributeassignment
	 * @param outputcontrol
	 */
	protected void _initMerkmalAttributeUnit(final Element oAttributeValue, final ClassAttributeAssignment classattributeassignment,
			final Outputcontrol outputcontrol) {

		// --- Debug
		final ClassificationAttribute classificationattribute = classattributeassignment.getClassificationAttribute();
		//output("s._initMerkmalAttributeUnit=" + classificationattribute.getCode(), 2);

		// --- Pr�fe OutputControl
		String stringUnit = "";
		final Boolean bBackground = new Boolean(false);
		if (outputcontrol != null) {
			stringUnit = (String) outputcontrol.getLocalizedProperty("unitca");
			if (stringUnit != null && stringUnit.equals("[ohne]")) {
				stringUnit = " ";
			}
		}

		// --- Einheit --- (START)
		ClassificationAttributeUnit oUnit = null;
		oUnit = classattributeassignment.getUnit();
		if (stringUnit == null || stringUnit.length() == 0) {
			if (oUnit != null) {
				stringUnit = oUnit.getSymbol();
			} else {
				stringUnit = "";
			}
		}
		if (stringUnit.toLowerCase().equals("inch")) {
			stringUnit = "";
		}
		if (classificationattribute.getCode().equals("_00011f001")) {
			stringUnit = "Art.No.";
		}
		oAttributeValue.setAttribute("UnitSymbol", stringUnit);
		//oAttributeValue.setAttribute("UnitRefCode", stringUnit );
		//oAttributeValue.setAttribute("Name",        stringUnit );
		//oAttributeValue.setAttribute("UnitName",    stringUnit );
		// --- Einheit --- (ENDE)

		// --- Attribute-Header-List (Unit)
		final HashMap hashCA = (HashMap) m_hashAttributeHeaderList.get(classificationattribute.getCode());
		if (hashCA != null) {
			output("++add Unit to Map", 2);
			hashCA.put("UnitName", stringUnit);
			m_hashAttributeHeaderList.put(classificationattribute.getCode(), hashCA);
		}

		// --- Debug
		//output("e._initMerkmalAttributeUnit=" + classificationattribute.getCode(), 2);
	}

	/**
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
			final String strWert, final int iOrder, final String strMediaCode, final String stringUnit) {

		//LOG.info("initMerkmal() called. strID=" + strID);
		// --- Neues Element anlegen
		//output("s.initMerkmal=" + strBez + " / " + strID, 2);
		// --- Initialize
		String strOrder = "";
		strOrder = new Integer(iOrder).toString();

		// --- Elemente anlegen
		final Element oAttributeValue = _initMerkmalAttributeValue(strID, strBez, strType, strWert, strOrder);
		final Element oAttributeValueList = _initMerkmalAttributeValueList(oAttributeValue);
		final Element oAttribute = _initMerkmalAttribute(oAttributeValueList, strID, strBez, strOrder);

		// --- Hintergrund
		final Element oBackground = new Element("Background");
		oAttribute.setAttribute("Background", "N");

		// --- Setze Unit
		oAttributeValue.setAttribute("UnitSymbol", stringUnit);
		//oAttributeValue.setAttribute("UnitRefCode", stringUnit );
		//oAttributeValue.setAttribute("Name", stringUnit  );
		//oAttributeValue.setAttribute("UnitName", stringUnit );

		// --- Der Attributeliste zuordnen
		oAttributeList.addContent(oAttribute);

		// --- Hole Media-Object
		WeraMedia weramedia = null;
		final MediaManager mm = MediaManager.getInstance();
		final Collection myMedia = mm.getMediaByCode(strMediaCode);
		if (myMedia != null && myMedia.size() > 0) {
			weramedia = (WeraMedia) myMedia.iterator().next();
		}

		// --- Media-Infos
		final Element oMotive = _initMerkmalIcon(null, null, weramedia, strID, "merkmale", strOrder);
		oAttribute.addContent(oMotive);

		// --- default farbcode
		oAttribute.setAttribute("farbcode","N");

		//output("e.initMerkmal=" + strBez + " / " + strID, 2);
		//LOG.info("initMerkmal() finished.");
	}
	
	/**
	 * setzt den Merknal Attribute-Type
	 * @param oAttributeList
	 * @param strID
	 * @param strBez
	 * @param strType
	 * @param strWert
	 * @param iOrder
	 * @param strMediaCode
	 * @param stringUnit
	 */
	protected void initMerkmalAttributeType (final Element oAttributeList, final String strID, final String strBez, final String strType,
			final String strWert, final int iOrder, final String strMediaCode, final String stringUnit) {

		//LOG.info("initMerkmal() called. strID=" + strID);
		// --- Neues Element anlegen
		//output("s.initMerkmal=" + strBez + " / " + strID, 2);
		// --- Initialize
		String strOrder = "";
		strOrder = new Integer(iOrder).toString();

		// --- Elemente anlegen
		final Element oAttributeValue = _initMerkmalAttributeValue(strID, strBez, strType, strWert, strOrder);
		final Element oAttributeValueList = _initMerkmalAttributeValueList(oAttributeValue);
		final Element oAttribute = _initMerkmalAttribute(oAttributeValueList, strID, strBez, strOrder, strType);

		// --- Hintergrund
		final Element oBackground = new Element("Background");
		oAttribute.setAttribute("Background", "N");

		// --- Setze Unit
		oAttributeValue.setAttribute("UnitSymbol", stringUnit);
		//oAttributeValue.setAttribute("UnitRefCode", stringUnit );
		//oAttributeValue.setAttribute("Name", stringUnit  );
		//oAttributeValue.setAttribute("UnitName", stringUnit );

		// --- Der Attributeliste zuordnen
		oAttributeList.addContent(oAttribute);

		// --- Hole Media-Object
		WeraMedia weramedia = null;
		final MediaManager mm = MediaManager.getInstance();
		final Collection myMedia = mm.getMediaByCode(strMediaCode);
		if (myMedia != null && myMedia.size() > 0) {
			weramedia = (WeraMedia) myMedia.iterator().next();
		}

		// --- Media-Infos
		final Element oMotive = _initMerkmalIcon(null, null, weramedia, strID, "merkmale", strOrder);
		oAttribute.addContent(oMotive);

		//output("e.initMerkmal=" + strBez + " / " + strID, 2);
		//LOG.info("initMerkmal() finished.");
	}

	//protected void initCol(Element tableheaderXML, ProfiClassClassificationAttribute classificationAttribute, Category catPClass, Category catWera  ) {
	/**
	 *
	 * @param tableheaderXML
	 * @param strMERKMALID
	 * @param strUNIT
	 * @param strBackG
	 */
	protected void initColfixed(final Element tableheaderXML, final String strMERKMALID, final String strUNIT, final String strBackG) {

		//output("s.initColfixed="+strMERKMALID,2);
		// --- Neues Element anlegen
		final Element spalteXML = new Element("COL");
		spalteXML.setAttribute("MERKMALID", strMERKMALID);

		// --- UNIT
		spalteXML.setAttribute("UNIT", strUNIT);

		// --- ICON
		spalteXML.setAttribute("ICON", "..");

		// --- Hintergrund
		spalteXML.setAttribute("BG", strBackG);

		// --- Dem Artikel zuordnen
		tableheaderXML.addContent(spalteXML);

		//output("e.initColfixed="+strMERKMALID,2);
	}

	/**
	 * Gibt einen String mit Notizen zur�ck
	 *
	 * @param oArtikel
	 * @param weraVariante
	 * @param colAllFootnotes
	 * @param oProductJoin
	 * @return
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	protected String initWeraProductVariante(final Element oArtikel, final WeraVariante weraVariante,
			final ArrayList colAllFootnotes, final Element oProductJoin) throws JaloInvalidParameterException, JaloSecurityException {
		// TODO Auto-generated method stub

		// --- Initialize
		//output("initWeraProductVariante="+weraVariante.getCode(),2);
		final ArrayList aResult = new ArrayList();
		String strResult = "";
		if (weraVariante.getAttribute("lagerNr") == null) {
			m_wm.setAttribute(weraVariante, "lagerNr", "05");
		}
		if (weraVariante.getLocalizedProperty("variantenNr") == null) {
			weraVariante.setLocalizedProperty("variantenNr", "001");
		}
		// --
		final String strVarNr = (String) weraVariante.getLocalizedProperty("variantenNr");
		// --

		final String strWeraCode = weraVariante.getAttribute("lagerNr").toString() + weraVariante.getAttribute("code").toString()
				+ strVarNr;

		// --- HyperLinks erste CodeNr
		if ( m_firstcodeNr == "" ) {
			m_firstcodeNr	= strWeraCode;
		}

		String strCode;
		String strName = "";
		if (weraVariante.getName() != null) {
			strName = weraVariante.getName();
		}
		//output("strName="+strName,2);
		String strEAN = "";
		if (getAttribute(weraVariante, "ean") != null) {
			if (m_strLanguage.equals("us-en") || m_strLanguage.equals("us-es") || m_strLanguage.equals("us-fr")) {
				strEAN = (String) getAttribute(weraVariante, "ean_us");
			} else {
				strEAN = (String) getAttribute(weraVariante, "ean");
			}
		}
		if (strEAN == null || strEAN.equals("null")) {
			strEAN = "-";
		}
		if (strEAN == null || strEAN.length() == 0 || strEAN.length() < 8) {
			strEAN = "";
		} else {
			strEAN = strEAN.substring(7);
		}

		// --- Produktname
		//Comment nameVariante = new Comment(strName);
		//oArtikel.addContent(nameVariante);
		// --- Hole Randnumber
		m_iOffsetArtikel++;
		final String strRefId = "A" + m_iOffsetArtikel + "_" + weraVariante.getAttribute("code").toString();

		// --- Initialiere die Produktreferenz dieses Artikels
		//oProductJoin.setAttribute("JoinType","SET-PRODUCT");
		//oProductJoin.setAttribute("JoinedType","PRODUCT");
		oProductJoin.setAttribute("JoinedProductNumber", strRefId);
		oProductJoin.setAttribute("RawCodeNumber", strWeraCode);
		//oProductJoin.setAttribute("JoinedId",strRefId);
		//oProductJoin.setAttribute("JoinedRefCode",strRefId);
		//oProductJoin.setAttribute("JoinedWorkflow","OK");
		//oProductJoin.setAttribute("JoinedManufacturer","WERA");
		oProductJoin.setAttribute("JoinedEAN", strEAN);
		oProductJoin.setAttribute("Sequence", getAttribute(weraVariante, "order").toString());
		//oProductJoin.setAttribute("JoinTypeName","SET-PRODUCT");
		//oProductJoin.setAttribute("JoinedTypeName","PRODUCT");
		//oProductJoin.setAttribute("JoinedName", strName );
		//oProductJoin.setAttribute("JoinedWorkflowName","OK");
		//oProductJoin.setAttribute("JoinedManufacturerDescription","WERA");

		// --- Initialize Product Data
		//articelXML.setAttribute("SPRACHE", m_strLanguage) ;
		//articelXML.setAttribute("ID",    weraVariante.getCode() ) ;
		//articelXML.setAttribute("NAME",  weraVariante.getCode() );
		// --- <Product Id="1527" Type="PRODUCT" RefCode="U10409" Workflow="OKE_DATEN" Manufacturer="WIHA" ProductNumber="08889" EAN="4010995088897" TypeName="PRODUCT" Name="System 6 Wechselklingen f�r Innensechskant-Schrauben." WorkflowName="OKE_DATEN" ManufacturerDescription="Wiha">
		oArtikel.setAttribute("Id", strRefId);
		//oArtikel.setAttribute("Type",          "PRODUCT" );
		oArtikel.setAttribute("RefCode", strRefId);
		//oArtikel.setAttribute("Workflow",      "OK" );
		//oArtikel.setAttribute("Manufacturer",  "WERA");
		oArtikel.setAttribute("ProductNumber", strRefId);
		//oArtikel.setAttribute("EAN",           strEAN );
		//oArtikel.setAttribute("TypeName",      "PRODUCT");
		//oArtikel.setAttribute("Name", strName);
		//oArtikel.setAttribute("WorkflowName",  "OK" );
		//oArtikel.setAttribute("ManufacturerDescription","WERA");

		// --- Holle alle verkn�pften Fussnoten
		Collection footnotes = (weraVariante).getFootnotes();
		Collection allFootnotes = new ArrayList();
		allFootnotes.addAll(footnotes);

		if (this.m_bExportDiscontinuedItemAsFootnote) {

			// --- Auslaufartikel in Fussnoten
			Boolean bArtikelAuslauf = (Boolean) m_wm.getAttribute(weraVariante, "artikel_auslauf");
			if (bArtikelAuslauf == null) {
				bArtikelAuslauf = new Boolean(false);
			}
			if (bArtikelAuslauf.booleanValue()) {

				// --- Fussnote (temp) anh�ngen
				LOG.info("+++Fussnote m_oFootnoteAuslaufartikelTmp (2) added=" + weraVariante.getCode());
				if (!allFootnotes.contains(m_oFootnoteAuslaufartikelTmp)) {
					allFootnotes.add(m_oFootnoteAuslaufartikelTmp);
				}
			}

		} // --- if ( this.m_bExportDiscontinuedItemAsFootnote ) {

		// --- Schleife �ber alle Attribute f�r Artikel
		final Element notizXML = null;
		int iPos = 0;
		String strFootnote = null;
		output("e.footnotes.size()=" + allFootnotes.size(), 2);
		for (final Iterator it2 = allFootnotes.iterator(); it2.hasNext();) {
			// --- Hole ProfiClassAttribute
			final Footnote oFootnote = (Footnote) it2.next();

			// --- Artikelnummer entfernen
			strCode = oFootnote.getAttribute("code").toString();
			if (strCode.contains("_")) {
				strCode = strCode.substring(strCode.indexOf('_') + 1);
			}

			// --- Fussnote merken falls noch nicht vorhanden
			if (colAllFootnotes.contains(oFootnote)) {
				output("footnotes.strCode=" + strCode, 2);
				iPos = colAllFootnotes.indexOf(oFootnote) + 1;
				oFootnote.setLfdNr(iPos);

				// --- Notiznummer setzen
				strFootnote = new Integer(oFootnote.getLfdNr()).toString() + ")";
				if (!aResult.contains(strFootnote)) {
					aResult.add(strFootnote);
				}
				//output( "footnotes.getName="   + oFootnote.getName(), 2 );
				//output( "footnotes.strResult=" + strResult, 2 );
			}

		} // --- for (Iterator it2 = footnotes.iterator(); it2.hasNext();) {
		//output("e.initWeraProductVariante="+weraVariante.getCode(),2);

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
			strResult = m_oExportFormatter.formatSetSupS() + strResult + m_oExportFormatter.formatSetSupE();
		}

		return strResult;
	}

	//output("initClassificationAttributes="+product.getCode(),2);
	/**
	 * Initialize
	 *
	 * @param product
	 * @param oAttributeList
	 * @param classattributeassignment
	 * @param category
	 * @param iOrder
	 * @param iSplitValue
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 * @return String strContentValue
	 */
	protected String initClassificationAttributes(final Product product, final Element oAttributeList,
			final ClassAttributeAssignment classattributeassignment, final Category category, final int iOrder, final int iSplitValue)
			throws JaloInvalidParameterException, JaloSecurityException {
		// TODO Auto-generated method stub
		final ClassificationAttribute classificationattribute = classattributeassignment.getClassificationAttribute();
		final String strOrder = new Integer(iOrder).toString();
		String strID	= "";
		String strContentValue = "";
		int iShowCntAttribute = 1;

		//LOG.info("initClassificationAttributes (S)=" + product.getCode() + ", classattributeassignment=" + classattributeassignment.getClassificationAttribute().getCode() );

		// --- Setze Defaultsprache auf DE (Werte f�r die deutsche Version holen, falls Sprache nicht vorhnaden)
		final Language oAktLanguage = m_jaloSession.getSessionContext().getLanguage();
		List Fallbacks = null;
		if (!oAktLanguage.getIsoCode().toString().equals("de")) {
			Fallbacks = oAktLanguage.getFallbackLanguages();
			final Language oNewLanguage = m_jaloSession.getC2LManager().getLanguageByIsoCode("de");
			final ArrayList listNewFallback = new ArrayList();
			listNewFallback.add(oNewLanguage);
			oAktLanguage.setFallbackLanguages(listNewFallback);
		}

		// --- Beim Produkt einen Dummy-Eintrag zeigen
		if (product instanceof WeraVariante) {
			iShowCntAttribute = 9999;
		}

		// --- Hole Ausgabesteuerung
		//Collection outputcontrols = null;
		//if ( product instanceof WeraProduct )
		//outputcontrols = (Collection) getAttribute(product,"outputcontrols");
		// --- Hole alle Produktfeatures zum aktuellen Merkmal
		//Collection productfeatures = ProductFeatureHelper.getProductFeatures(product,classificationAttribute);
		// --- Hole die ausgew�hlten ClassificationAttributeValue (Typ-String) eines Products / Values / oder Sets
		/*
		 * if ( classattributeassignment.getClassificationAttribute().getCode().equals("AAA968f001" ) ) { _debug (
		 * product.getAttribute("code").toString(), " caa.getClassificationAttribute().getCode()=" +
		 * classattributeassignment.getClassificationAttribute().getCode() ); _debug (
		 * product.getAttribute("code").toString(),
		 * " AAA968f001, weraproduct - Exportclassattributeassignment.getVersion()=" +
		 * classattributeassignment.getSystemVersion().getVersion() ); }
		 */
		final HashMap<String, ClassificationAttributeValue> hFeatureValues = m_weraclassificationhelper
				.getPickedClassificationAttributeValuesByProduct(product, classattributeassignment);

		// --- Wurden Werte ausgewh�lt
		if (hFeatureValues.size() == 0) {

			// --- Initialisiere Merkmal, kein Wert vorhanden
			try {
				initProductFeature(classattributeassignment, oAttributeList, "", "", category, strOrder, iSplitValue, "", "" );
			} catch (final Exception e) {
				e.printStackTrace();
			}

		} else {

			// --- Schleife �ber alle Produktfeatures
			//LOG.info(product.getAttribute("code").toString() + " hFeatureValues.values().size()=" + hFeatureValues.values().size());
			final ProductFeature oProductFeature = null;
			for (final Iterator it1 = hFeatureValues.values().iterator(); it1.hasNext();) {

				//LOG.info("classattributeassignment.getClassificationAttribute().getCode()=" + classattributeassignment.getClassificationAttribute().getCode() );

				// --- Hole ProductFeature
				//oProductFeature = (ProductFeature) it1.next();
				final ClassificationAttributeValue oClassificationAttributeValue = (ClassificationAttributeValue) it1.next();
				//LOG.info(product.getAttribute("code").toString() + " oClassificationAttributeValue=" + oClassificationAttributeValue);
				if (oClassificationAttributeValue == null) {
					LOG.info("skip (oClassificationAttributeValue == null)=" );
					continue;
				}

				// --- Unterscheidung nach Datentyp
				if (classattributeassignment.getAttributeType().getCode()
						.equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.STRING)) {

					// --- Schleife �ber alle Merkmale
					final Item item = null;
					//Collection col = oProductFeature.getValues();
					//for (Iterator it2 = col.iterator(); it2.hasNext();) {
					// --- Hole Element
					//item = (Item) it2.next();

					// --- Anzahl der Werte eingrenzen
					iShowCntAttribute--;
					if (iShowCntAttribute < 0) {
						break;
					}

					// --- Initialisiere Merkmale
					//LOG.info(product.getAttribute("code").toString() + " oClassificationAttributeValue.getAttribute('name')="
					//		+ oClassificationAttributeValue.getAttribute("name"));
					try {
						strID			= oClassificationAttributeValue.getAttribute("code").toString();
						strContentValue	= (String) oClassificationAttributeValue.getAttribute("name");

						// --- farbcode --------------------------------------------------------
						String farbcode_cmyk 	= "not found";
						String farbcode_rgb 	= "not found";
						if ( iSplitValue > 0 ) {
							Farbitem farbitem = null;
							Collection<Farbitem> farbitemsTmp 	= (Collection<Farbitem>) oClassificationAttributeValue.getAttribute("farbitemspref");
							if ( farbitemsTmp != null ) {
								ArrayList<Farbitem> farbitems = new ArrayList<Farbitem>(farbitemsTmp);
								if ( farbitems.size() >= iSplitValue ) {
									farbitem	= farbitems.get(iSplitValue-1);
								} else if ( farbitems.size() > 0 ) {
									farbitem	= farbitems.get(0);
								}
								if ( farbitem != null ) {

									String farbcode_cmykTmp 	= (String) farbitem.getAttribute("farbcode_cmyk");
									String farbcode_rgbTmp 	= (String) farbitem.getAttribute("farbcode_web");

									if ( farbcode_cmykTmp != null ) {

										String[] afarbcode_cmykValueList = farbcode_cmykTmp.split("/");
										if ( afarbcode_cmykValueList.length >= iSplitValue ) {
											farbcode_cmyk = afarbcode_cmykValueList[iSplitValue - 1];
										} else {
											farbcode_cmyk = afarbcode_cmykValueList[0];
										}

									} else  {
										farbcode_cmyk	= "";
									}
									if ( farbcode_rgbTmp != null ) {

										String[] afarbcode_rgbValueList = farbcode_rgbTmp.split("/");
										if ( afarbcode_rgbValueList.length >= iSplitValue ) {
											farbcode_rgb = afarbcode_rgbValueList[iSplitValue - 1];
										} else {
											farbcode_rgb = afarbcode_rgbValueList[0];
										}

									} else  {
										farbcode_rgb	= "";
									}
								}

							} // if ( farbitems != null ) {
						}
						// --- farbcode --------------------------------------------------------

						//LOG.info ( "1. classificationattributevalue=" + oClassificationAttributeValue );
						initProductFeature(classattributeassignment, oAttributeList, oClassificationAttributeValue.getAttribute("code")
								.toString(), (String) oClassificationAttributeValue.getAttribute("name"), category, strOrder, iSplitValue, farbcode_cmyk, farbcode_rgb );

					} catch (final Exception e) {
						e.printStackTrace();
					}

					//} // --- for (Iterator it2 = col.iterator(); it2.hasNext();) {
				} else {

					// --- Initialisiere Merkmale
					try {
						strID			= oProductFeature.getQualifier();
						strContentValue	= (String) oProductFeature.getAttribute("stringValue");

						// --- farbcode --------------------------------------------------------
						String farbcode_cmyk 	= "";
						String farbcode_rgb 	= "";
						if ( iSplitValue > 0 ) {
							String farbcode_cmykTmp 	= (String) oClassificationAttributeValue.getAttribute("farbcode_cmyk");
							String farbcode_rgbTmp 	= (String) oClassificationAttributeValue.getAttribute("farbcode_web");

							if ( farbcode_cmykTmp != null ) {

								String[] afarbcode_cmykValueList = farbcode_cmykTmp.split("/");
								if ( afarbcode_cmykValueList.length >= iSplitValue ) {
									farbcode_cmyk = afarbcode_cmykValueList[iSplitValue - 1];
								} else {
									farbcode_cmyk = afarbcode_cmykValueList[0];
								}

							} else  {
								farbcode_cmyk	= "";
							}
							if ( farbcode_rgbTmp != null ) {

								String[] afarbcode_rgbValueList = farbcode_rgbTmp.split("/");
								if ( afarbcode_rgbValueList.length >= iSplitValue ) {
									farbcode_rgb = afarbcode_rgbValueList[iSplitValue - 1];
								} else {
									farbcode_rgb = afarbcode_rgbValueList[0];
								}

							} else  {
								farbcode_rgb	= "";
							}


						}
						// --- farbcode --------------------------------------------------------

						//LOG.info ( "2. classificationattributevalue=" + oClassificationAttributeValue );
						initProductFeature(classattributeassignment, oAttributeList, strID, strContentValue, category, strOrder, iSplitValue, farbcode_cmyk, farbcode_rgb );
						
					} catch (final Exception e) {
						e.printStackTrace();
					}

				}

				

			} // --- for (Iterator it1 = productfeatures.iterator(); it1.hasNext();) {

		} // --- if ( productfeatures.size() == 0 ) {

		// --- Ausgabesprache zur�cksetzen
		if (oAktLanguage != null && Fallbacks != null) {
			//   m_jaloSession.getSessionContext().setLanguage(oAktLanguage);
			oAktLanguage.setFallbackLanguages(Fallbacks);
		}

		//LOG.info("initClassificationAttributes (E)=" + product.getCode() + ", classattributeassignment=" + classattributeassignment.getClassificationAttribute().getCode() );

		return strContentValue;
		//output("e.initClassificationAttributes="+product.getCode(),2);
	}

	/**
	 *
	 * @param classificationAttribute
	 * @return
	 */
	protected Outputcontrol _getOutputcontrol(final ClassificationAttribute classificationAttribute) {
		// --- Initialize
		Outputcontrol outputcontrol = null;

		if (m_ouputcontrols != null) {
			outputcontrol = (Outputcontrol) m_wm.checkContaining(m_ouputcontrols, "code", classificationAttribute.getCode());
		}

		return outputcontrol;
	}

	/**
	 *
	 * @param ClassAttributeAssignment classattributeassignment
	 * @param Element oAttributeList
	 * @param StringstrWertId
	 * @param String strWert
	 * @param Categorycategory
	 * @param String strOrder
	 * @param int iSplitValue
	 * @param String farbcode_cmyk
	 * @param String farbcode_rgb
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	protected void initProductFeature(final ClassAttributeAssignment classattributeassignment, final Element oAttributeList,
			String strWertId, String strWert, final Category category, final String strOrder, final int iSplitValue, String farbcode_cmyk, String farbcode_rgb )
			throws JaloInvalidParameterException, JaloSecurityException {
		// TODO Auto-generated method stub

		// --- Initialize
		final ClassificationAttribute classificationattribute = classattributeassignment.getClassificationAttribute();

		// --- Debug
		//LOG.info("initProductFeature=" + classificationattribute.getCode() );
//LOG.info("v.classificationattribute.getCode()=" + classificationattribute.getCode() + ", wert=>" + strWert + "<" );
		//output("s.initProductFeature=" + classificationattribute.getCode(), 2);
		//output("s.initProductFeature="+classificationAttribute.getCode(),2);
		// --- Initialize
		final Outputcontrol outputcontrol = _getOutputcontrol(classificationattribute);
		if (strWert == null) {
			strWert = "n/a";
		}
		String strBez = classificationattribute.getName(m_jaloSession.getSessionContext());
		if (strBez == null) {
			strBez = "n/a";
		}

		// --- Datentyp
		String strType = "";
		final EnumerationValue ev = classattributeassignment.getAttributeType();
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
		} else {
			strType = "alphanumerisch";
		}

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
		if (stringUnit == null || stringUnit.length() == 0) {
			if (oUnit != null) {
				stringUnit = oUnit.getSymbol();
			} else {
				stringUnit = "";
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


		/**
		 * auskommentieren, brüche über stilvorlagen formatieren, daher zollzeichen erhalten
		 * 06.01.2021 GT
		 */
/*
		// --- Zoll-Zeichen die mit den Werten kommen entfernen
		strWert = strWert.replace("\"", "");
		strWert = strWert.replace("\u00b4", "");
*/
		// --- Einheiten im Wert entfernen
		strWert = strWert.replace(" mm", "");
		strWert = strWert.replace(" Nm", "");
		strWert = strWert.replace(" in,", "");
		strWert = strWert.replace(" lbs,", "");
		strWert = strWert.replace(" lbf,ft", "");

		// --- Leerzeichen entfernen, bei Wert mit + / -
		if (!classificationattribute.getCode().equals("_00011f001")) {
			if (strWert.contains("+") || strWert.contains("-")) {
				strWert = strWert.replace(" ", "");
			}
		}

		// --- " f�r Inchwerte und Inchwerte als Br�che darstellen
		String strCaName = classificationattribute.getName();
		if (strCaName == null) {
			strCaName = "";
		}
		strWert = strWert.trim();
		boolean bIsUnitInch = (stringUnit.toLowerCase().equals("inch") || (strWert.contains("/") && iSplitValue == 0) || strCaName
				.toLowerCase().contains("zoll"));
		if (strWert.toLowerCase().contains("#") || strWert.toLowerCase().contains("p")) {
			bIsUnitInch = false;
		}
		if (classificationattribute.getCode().equals("_00011f001")) {
			bIsUnitInch = false;
		}
		//if ( stringUnit.toLowerCase().equals("inch") && strWert.length() > 0 ) {

		/**
		 * auskommentieren, brüche über stilvorlagen formatieren
		 * 06.01.2021 GT
		 */
		if (false && bIsUnitInch && strWert.length() > 0) {

			///////////////////////////////////////////////////////////////////////////////////////
			final String[] aValueList = strWert.split("/");
			if (aValueList.length >= 2) {
				// --- Prozentzeichen
				String strWert1 = m_oExportFormatter.formatSetSupS() + aValueList[0].trim() + m_oExportFormatter.formatSetSupE();
				final String strWert2 = m_oExportFormatter.formatSetSubS() + aValueList[1].trim()
						+ m_oExportFormatter.formatSetSubE();

				final String[] aValueList1 = aValueList[0].split(" ");
				if (aValueList1.length >= 2) {
					strWert1 = aValueList1[0].trim();
					strWert1 = strWert1 + " " + m_oExportFormatter.formatSetSupS() + aValueList1[1].trim()
							+ m_oExportFormatter.formatSetSupE();
				}
				strWert = strWert1 + "/" + strWert2;
			}
			if (strWert.equals("-")) {
				strWert = "";
			} else {
				strWert = strWert + "\"";
			}
			///////////////////////////////////////////////////////////////////////////////////////
		} // if (false && bIsUnitInch && strWert.length() > 0) {
		if ( bIsUnitInch && strWert.length() > 0 ) {
			if ( strWert.equals("-") ) {
				strWert = "";
			} else {
				if ( !strWert.contains("\"") ) {
					strWert = strWert + "\"";
				}
			}
		}


		// --- Wert soll aufgetrennt werden - Trenner zur Zeit '/'
		if (iSplitValue > 0) {
			final String[] aValueList = strWert.split("/");
			if (aValueList.length < 2) {
				strWert = aValueList[0];
			} else {
				strWert = aValueList[iSplitValue - 1];
			}
		}
//LOG.info("n.classificationattribute.getCode()=" + classificationattribute.getCode() + ", wert=>" + strWert + "<" );

		// --- Initialisiere Attribute
		final Element oMotive = _initMerkmalIcon(classattributeassignment, outputcontrol, null, classificationattribute.getCode(),
				"merkmale", strOrder);
		Element oAttributeValue = null;
		if (iSplitValue > 0) {
			oAttributeValue = _initMerkmalAttributeValue(strWertId, strBez, strType, "", strOrder);
		} else {
			oAttributeValue = _initMerkmalAttributeValue(strWertId, strBez, strType, strWert, strOrder);
		}
		final Element oAttributeValueList = _initMerkmalAttributeValueList(oAttributeValue);

		final Element oAttribute = _initMerkmalAttribute(oAttributeValueList, classificationattribute.getCode(), strBez, strOrder);

		// --- farbcode -----------------------------
		if ( iSplitValue > 0 ) {

			// --- farbcode setzen
			oAttribute.setAttribute("farbcode", "J" );
			oAttribute.setAttribute("farbcode_cmyk", farbcode_cmyk );
			oAttribute.setAttribute("farbcode_rgb", farbcode_rgb );
			String farbcodeFileName = "notfound.svg";
			if ( !(farbcode_cmyk.equals("not found") || farbcode_rgb.equals("not found")
					|| farbcode_cmyk.equals("") || farbcode_rgb.equals("")
			) ) {

				farbcodeFileName = farbcode_rgb.replaceAll("#","").replaceAll("#",";") + "-" + farbcode_cmyk.replaceAll(",","_") + ".svg";
			}
			oAttribute.setAttribute("farbcode_filename", "https://hybris-media.wera.de/ibimage_generator/images/" + farbcodeFileName );
		} else {
			oAttribute.setAttribute("farbcode", "N" );
		}
		// --- farbcode -----------------------------


		_initMerkmalAttributeUnit(oAttributeValue, classattributeassignment, outputcontrol);
		oAttribute.addContent(oMotive);
		_debug("367.028048 ??", " addContent(oAttribute).strWertId=" + strWertId + ", order=" + strOrder);
		oAttributeList.addContent(oAttribute);

		// --- Wert soll aufgetrennt werden - Trenner zur Zeit '/', dann muss die ID auch eindeutig sein!!
		if (iSplitValue > 0) {
			// --- Initialize
			final String strOffset = "." + new Integer(iSplitValue);
			String strId = oMotive.getAttributeValue("Id");
			strId = strId + strOffset;
			oMotive.setAttribute("Id", strId);
			oMotive.setAttribute("RefCode", strId);
			final Element oFilelist = oMotive.getChild("File-List");
			final Element oFile = oFilelist.getChild("File");
			if (iSplitValue >= 1) {
				String strPrintFileName = oFile.getAttributeValue("PrintFileName");
				strPrintFileName = strPrintFileName.replaceAll(".eps", "_" + new Integer(iSplitValue) + ".eps");
				oFile.setAttribute("PrintFileName", strPrintFileName);
			}
			strId = oMotive.getAttributeValue("LinkTypeName");
			strId = strId + strOffset;
			oMotive.setAttribute("LinkTypeName", strId);
			oAttribute.setAttribute("Id", strId);
		}

		// --- Tabulator
		String strTab = "";
		strTab = "R";
		if (strWert.contains("+")) {
			strTab = "L";
		}
		//	    if ( strWert.contains("+") || strWert.contains("PH") || strWert.contains("PZ") || strWert.contains("TX")
		//		|| strWert.contains("IP") || strWert.contains("SW") || strWert.contains("M")  || strWert.contains("E") )
		strTab = "21R";

		// --- TX-BO, oder �hnlich lang
		if (strWert.length() >= 7 && bIsUnitInch == false) {
			strTab = "ZS";
		}

		// --- Inch oder Artikelnummer immer zentriert
		if (bIsUnitInch) {
			strTab = "Z";
		}
		String strEinhTab = strTab;

		// --- Kleinere Schrift verwenden
		// --- Wert von / bis
		if (strWert.contains("-")) {
			strTab = "RS01";
			strEinhTab = "Z";
		}
		if (classificationattribute.getCode().equals("_00011f001")) {
			strTab = classificationattribute.getCode() + "ZS";
			strEinhTab = "Z";
		}
		if (classificationattribute.getCode().equals("AAA418f001")) {
			strTab = "R11";
			strEinhTab = "Z";
		}
		if (stringUnit.equals("in. lbs.")) {
			if (strWert.contains("-") || strWert.length() >= 7) {
				strTab = "ZS";
			} else {
				strTab = "21R";
			}
			strEinhTab = "ZS";
		}

		// --- Attribute-Header-List (Tabulator)
		final HashMap hashCA = (HashMap) m_hashAttributeHeaderList.get(classificationattribute.getCode());
		if (hashCA != null) {
			//output("++add Tabulator to Map", 2);
			hashCA.put("Tabulator", strTab);
			hashCA.put("Einhtabulator", strEinhTab);
			m_hashAttributeHeaderList.put(classificationattribute.getCode(), hashCA);
		}
		oAttribute.setAttribute("Tabulator", strTab);
		oAttribute.setAttribute("Einhtabulator", strEinhTab);

		// --- Hintergrund
		String strBackground = "";
		final Element oBackground = new Element("Background");
		final Boolean bBackground = (java.lang.Boolean) outputcontrol.getLocalizedProperty("background");
		strBackground = _getBackgroundMarker(bBackground);
//		if (bBackground != null && bBackground.booleanValue())
//		{
//			strBackground = "J";
//		}
//		else
//		{
//			strBackground = "N";
//		}

		// --- Hintergrund setzen
		oAttribute.setAttribute("Background", strBackground);

		// --- Wert soll aufgetrennt werden - Trenner zur Zeit '/' (hintergrundfarbe setzen)
		if (iSplitValue > 0) {
			strWert = strWert.replace("�", "ue");
			strWert = strWert.replace("�", "ae");
			strWert = strWert.replace("�", "oe");
			oAttribute.setAttribute("Background", strWert);
			m_hashAttributeFarbenList.put(strWert, strWert);
		}
		// --- Debug
		//output("e.initProductFeature=" + classificationattribute.getCode(), 2);
	}

	/**
	 * return result background marker (J/N), J=show green column background,
	 * N=do not show background
	 *
	 * @param Boolean hasBackground
	 * @return String
	 */
	protected String _getBackgroundMarker(Boolean hasBackground) {

		// --- initialize
		String strBackgroundMarker = "";

		// --- get Background Marker
		if (hasBackground != null && hasBackground.booleanValue()) {
			strBackgroundMarker = "J";
		} else {
			strBackgroundMarker = "N";
		}

		return strBackgroundMarker;
	}

	/**
	 * create inital all media data
	 *
	 * @param WeraProduct weraProduct
	 * @return Element
	 */
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

		// --- BILD1 ------------------------------------------------------------------------------------------------------------------
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
		// --- BILD1 ------------------------------------------------------------------------------------------------------------------

		// --- Satz in Satz Premium Icon ----------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.Satz in Satz Premium Icon=");
		if (weraProduct instanceof WeraProductSetinSet) {
			// output("initializeMediaData.WeraProductSetinSet.PREMIUMICON=", 2);
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
		// --- Satz in Satz Premium Icon ----------------------------------------------------------------------------------------------

		// --- Hole Feature-Icons -----------------------------------------------------------------------------------------------------
		LOG.info("initializeMediaData.Feature-Icons=");
		final HashMap mapFeatureIcons = weraProduct.getFeatureIconsByBooleanProperties();
		if (mapFeatureIcons != null) {
			// --- Hole Collection der Media-Objekte
			final Collection<WeraMedia> colMedia = (Collection<WeraMedia>) mapFeatureIcons.get("iconlist");
			if (colMedia != null && colMedia.size() > 0) {
				int iMediaCounter = 0;
				String strMediaCounter = "";
				for (WeraMedia oWeraMedia : colMedia) {
					// --- FEATRUREICONS n
					iMediaCounter++;
					strMediaCounter = new Integer(iMediaCounter).toString();
					// output("initializeMediaData.FEATRUREICONS" + strMediaCounter + "=", 2);
					// weramedia = m_wm._getPicture(weraProduct,"featureicons" + strMediaCounter );
					bildXML = createBildElement("FEATRUREICONS" + strMediaCounter, oWeraMedia, strMediaCounter, "features", "");
					motivelistXML.addContent(bildXML);
				}

			} // --- if ( colMedia != null && colMedia.size() > 0 )
		}
		// --- Hole Feature-Icons -----------------------------------------------------------------------------------------------------

		return motivelistXML;
	}

	/**
	 * initialize the template-name
	 *
	 * @param WeraProduct weraProduct
	 * @return String strTemplate
	 */
	protected String _initTemplateName(WeraProduct weraProduct) {

		// --- initialize
		String strTemplate = null;

		// --- get templatename from hybris-product-data
		strTemplate = (String) getAttribute(weraProduct, "outputtemplate");
		LOG.info("_initTemplateNamem.outputtemplate (oc)=" + strTemplate );

		// --- set template
		if (strTemplate == null || strTemplate.equals("")) {
			if (weraProduct instanceof WeraProductSet) {
				strTemplate = "PRODUCTSET";
			} else {
				strTemplate = "PRODUCT";
			}
			if (weraProduct instanceof WeraProductSetinSet ) {

				// --- prüfe auf Display
				Boolean istDisplay = ((WeraProductSetinSet)weraProduct).isDisplay();

				if ( istDisplay.booleanValue() ) {
					strTemplate = "PRODUCTSETINSET_NO_CONTENT";
				} else {
					strTemplate = "PRODUCTSETINSET";
				}
			}
		}
		strTemplate = strTemplate.toUpperCase();
		if (strTemplate.equals("PRODUKT")) {
			strTemplate = "PRODUCT";
		}
		if (strTemplate.equals("PRODUKTSET")) {
			strTemplate = "PRODUCTSET";

		}
		LOG.info("_initTemplateNamem.outputtemplate return=" + strTemplate );

		return strTemplate;
	}

// --- GETTER / SETTER  --------------------------------------------------------------------------------------------------------------    

	public void setM_isPriceListExport(boolean m_isPriceListExport) {
		this.m_isPriceListExport = m_isPriceListExport;
	}

	public void setM_maxAttributeCols(int m_maxAttributeCols) {
		LOG.info("setM_maxAttributeCols set to =" + m_maxAttributeCols );
		this.m_maxAttributeCols = m_maxAttributeCols;
	}

	public void setM_bExportDiscontinuedItemAsFootnote(boolean m_bExportDiscontinuedItemAsFootnote) {
		this.m_bExportDiscontinuedItemAsFootnote = m_bExportDiscontinuedItemAsFootnote;
	}

	public void setM_bMultilayer(boolean m_bMultilayer) {
		LOG.info("setM_bMultilayer=" + m_bMultilayer);
		this.m_bMultilayer = m_bMultilayer;
	}

	public void setM_bAktionsprospekt(boolean m_bAktionsprospekt) {
		LOG.info("setM_bAktionsprospekt=" + m_bAktionsprospekt);
		this.m_bAktionsprospekt = m_bAktionsprospekt;
	}

	public boolean isM_bAktionsprospekt() {
		return m_bAktionsprospekt;
	}

	public void setM_coAdditionallLanguages(Collection<String> m_coAdditionallLanguages, String strDefaultLanguage) {

		LOG.info("setM_coAdditionallLanguages Anzahl Sprachen = " + m_coAdditionallLanguages.size());

		// --- l�sche zusatzliche ausgabesprache
		this.m_coAdditionallLanguages.clear();

		if (m_coAdditionallLanguages != null && m_coAdditionallLanguages.size() > 0) {

			for (String strLanguage : m_coAdditionallLanguages) {
				if (!strLanguage.equals(strDefaultLanguage)) {
					LOG.info("++add additional language = " + strLanguage);
					this.m_coAdditionallLanguages.add(strLanguage);
				}
			}
		}
	}

// --- GETTER / SETTER  --------------------------------------------------------------------------------------------------------------    
}

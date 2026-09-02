package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.Keyword;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.JaloAbstractTypeException;
import de.hybris.platform.jalo.type.JaloGenericCreationException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.XMLFilterImpl;
import org.xml.sax.helpers.XMLReaderFactory;

class FooFilter extends XMLFilterImpl {

	public FooFilter() {
	}

	public FooFilter(final XMLReader parent) {
		super(parent);
	}

	/**
	 * Filter the Namespace URI for start-element events.
	 */
	@Override
	public void startElement(String uri, final String localName, final String qName, final Attributes atts) throws SAXException {
		if (uri.equals("http://www.foo.com/ns/")) {
			uri = "http://www.bar.com/ns/";
		}
		System.out.println("++++Filter.startElement.localName=" + localName);
		super.startElement(uri, localName, qName, atts);
	}

	/**
	 * Filter the Namespace URI for end-element events.
	 */
	@Override
	public void endElement(String uri, final String localName, final String qName) throws SAXException {
		if (uri.equals("http://www.foo.com/ns/")) {
			uri = "http://www.bar.com/ns/";
		}
		System.out.println("++++Filter.endElement.localName=" + localName);
		super.endElement(uri, localName, qName);
	}

}

class DataImportExt implements ContentHandler {

	private XMLReader m_parser;
	private StringBuffer m_strText = null;
	private final String m_strKey = "";
	private String m_strValue = "";
	boolean m_bReadText = false;
	int m_iCol = 0;
	int m_iRow = 0;
	public int m_iMaxCol = 0;
	public int m_iMaxRow = 0;
	int m_iPosIndex = 0;
	int m_iPosValue = 0;
	public HashMap m_hHashMap = null;
	public ArrayList m_aIndexArrayList = null;
	public ArrayList m_aArrayList = null;
	private final boolean m_bModifyOriginal = false;

	public void setDocumentLocator(final Locator locator) {
		// TODO Auto-generated method stub
		System.out.println("+++setDocumentLocator+++");
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("+++startDocument+++");
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("+++endDocument.READY+++");
	}

	public void startPrefixMapping(final String prefix, final String uri) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("startPrefixMapping=" + prefix + ", uri=" + uri);

	}

	public void endPrefixMapping(final String prefix) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("endPrefixMapping=" + prefix);

	}

	public void startElement(final String uri, final String localName, final String qName, final Attributes atts)
			throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("startElement.localName=" + localName);

		if (localName.equals("Table")) {
			m_iRow = 0;
			m_iCol = 0;
			if (m_iMaxCol == 0) {
				m_iMaxCol = Integer.parseInt(atts.getValue("ss:ExpandedColumnCount"));
			}
			if (m_iMaxRow == 0) {
				m_iMaxRow = Integer.parseInt(atts.getValue("ss:ExpandedRowCount"));
				System.out.println("startElement.m_iMaxCol=" + m_iMaxCol);
				System.out.println("startElement.m_iMaxRow=" + m_iMaxRow);
			}
		}

		if (localName.equals("Row")) {
			m_iCol = 0;
		}
		if (localName.equals("Cell")) {
			// --- Überprüfe Korrektur der Zellen
			final String strIndex = atts.getValue("ss:Index");
			System.out.println("strIndex=" + strIndex);
			if (strIndex != null && !strIndex.equals("")) {
				final int iIndex = Integer.parseInt(strIndex) - 1;

				// --- Spalten auffüllen
				if (m_hHashMap == null) {
					m_hHashMap = new HashMap();
				}
				for (; m_iCol < iIndex; m_iCol++) {
					if (m_aIndexArrayList != null && m_aIndexArrayList.get(m_iCol) != null) {
						m_hHashMap.put(m_aIndexArrayList.get(m_iCol), "");
						//System.out.println("Korr m_iCol=" + m_iCol + ", gen. Feld=" + m_aIndexArrayList.get(m_iCol) );
					}
				}
				System.out.println("Korr m_iCol=" + m_iCol + "nach " + iIndex + ", Field=" + m_aIndexArrayList.get(iIndex));
			}
		}
		if (localName.equals("Data")) {
			//System.out.println("startElement.localName="+localName);
			// --- Textfeld
			if (m_strText == null) {
				m_strText = new StringBuffer();
			} else {
				m_strText.delete(0, m_strText.length());
			}
			m_bReadText = true;
		}
	}

	public void endElement(final String uri, final String localName, final String qName) throws SAXException {

		System.out.println("endElement.localName=" + localName);

		// --- Initialize
		m_strValue = "";

		// --- Ende der Zelle
		if (localName.equals("Cell")) {
			// --- Next
			if (m_strText != null) {
				m_strText.delete(0, m_strText.length());
			}
			m_iCol++;
			if (m_iCol >= m_iMaxCol) {
				m_iRow++;
				m_iCol = 0;
			}
			System.out.println("++m_strText=" + m_strText);
		}

		// --- Ende des Datentags
		if (localName.equals("Data")) {
			// --- Get the Data
			m_bReadText = false;
			m_strValue = m_strText.toString();
			System.out.println("++m_strText=" + m_strText + " m_iRow=" + m_iRow + ", m_iCol=" + m_iCol);

			if (m_iRow == 0) {
				if (m_aIndexArrayList == null) {
					m_aIndexArrayList = new ArrayList();
				}
				if (m_aArrayList == null) {
					m_aArrayList = new ArrayList();
				}
				m_aIndexArrayList.add(m_strValue);
			} else {
				if (m_iCol == 0) {
					m_hHashMap = new HashMap();
					m_aArrayList.add(m_hHashMap);
				}
				m_hHashMap.put(m_aIndexArrayList.get(m_iCol), m_strValue);
				System.out.println("Row (" + m_iRow + "), Col (" + m_iCol + ") =" + m_aIndexArrayList.get(m_iCol) + ", Value=" + m_strValue);
			}

		}
	}

	public void characters(final char[] ch, final int start, final int length) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("+characters (" + m_bReadText + ")=" + ch.toString() + ", start= " + start + ", length=" + length);
		if (m_bReadText) {
			for (int i = start; i < (start + length); i++) {
				if (ch[i] != 13 && ch[i] != 10) {
					m_strText.append(ch[i]);
				}
			}
			System.out.println("+characters, m_strText=" + m_strText);
		}
	}

	public void ignorableWhitespace(final char[] ch, final int start, final int length) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("+ignorableWhitespace");

	}

	public void processingInstruction(final String target, final String data) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("+processingInstruction.target=" + target + ",data=" + data);

	}

	public void skippedEntity(final String name) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("+skippedEntity. name=" + name);

	}

}

public class DataImport implements ContentHandler {

	private static final Logger LOG = Logger.getLogger(DataImport.class.getName());

	private XMLReader m_parser;
	private StringBuffer m_strText = null;
	private String m_strKey = "";
	private String m_strValue = "";
	boolean m_bReadText = false;
	int m_iCol = 0;
	int m_iPosIndex = 0;
	int m_iPosValue = 0;
	HashMap m_aHashMap;
	HashSet m_aHashSet = null;
	private boolean m_bModifyOriginal = false;
	public String m_strError;
	WeraImportPricelist wm_weraimportpricelist = null;

	public DataImport() {
		super();
		// TODO Auto-generated constructor stub
	}

	public void setDocumentLocator(final Locator locator) {
		// TODO Auto-generated method stub
		System.out.println("setDocumentLocator");
	}

	public void startDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("startDocument");
	}

	public void endDocument() throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("endDocument");
	}

	public void startPrefixMapping(final String prefix, final String uri) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("startPrefixMapping.prefix=" + prefix);
	}

	public void endPrefixMapping(final String prefix) throws SAXException {
		// TODO Auto-generated method stub
		System.out.println("endPrefixMapping.prefix=" + prefix);
	}

	public void startElement(final String uri, final String localName, final String qName, final Attributes atts)
			throws SAXException {
		// TODO Auto-generated method stub

		System.out.println("startElement.localName=" + localName);

		if (localName.equals("Row")) {
			m_iCol = 0;
		}
		if (localName.equals("Data")) {
			// --- Textfeld
			if (m_strText == null) {
				m_strText = new StringBuffer();
			} else {
				m_strText.delete(0, m_strText.length());
			}
			m_bReadText = true;
		}
	}

	public void endElement(final String uri, final String localName, final String qName) throws SAXException {
		// TODO Auto-generated method stub

		if (localName.equals("Data")) {

			// --- Get the Data
			m_bReadText = false;
			if (m_iCol == m_iPosIndex) {
				m_strKey = m_strText.toString();
			}
			m_strKey = m_strKey.replaceAll("\'", "");
			if (m_iCol == m_iPosValue) {
				m_strValue = m_strText.toString();
				m_strValue = m_strValue.replaceAll("\'", "");
				if (m_aHashMap == null) {
					m_aHashMap = new HashMap();
				}
				m_aHashMap.put(m_strKey, m_strValue);
			}
			/*
			 * System.out.println("m_iCol="+m_iCol); System.out.println("m_iPosIndex="+m_iPosIndex);
			 * System.out.println("m_iPosValue="+m_iPosValue); System.out.println("m_strValue="+m_strValue);
			 * System.out.println("m_strKey="+m_strKey);
			 */

			// --- Next
			if (m_strText != null) {
				m_strText.delete(0, m_strText.length());
			}
			m_iCol++;
		}
	}

	public void characters(final char[] text, final int start, final int length) throws SAXException {
		// TODO Auto-generated method stub
		if (m_bReadText) {
			for (int i = start; i < (start + length); i++) {
				if (text[i] != 13 && text[i] != 10) {
					m_strText.append(text[i]);
				}
			}
		}
	}

	public void ignorableWhitespace(final char[] ch, final int start, final int length) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void processingInstruction(final String target, final String data) throws SAXException {
		// TODO Auto-generated method stub
	}

	public void skippedEntity(final String name) throws SAXException {
		// TODO Auto-generated method stub
	}

	public boolean isM_bModifyOriginal() {
		return m_bModifyOriginal;
	}

	public void setM_bModifyOriginal(final boolean modifyOriginal) {
		m_bModifyOriginal = modifyOriginal;
	}

	public DataImportExt getContentHandler() {
		return (DataImportExt) m_parser.getContentHandler();
	}

	public String getStringError() {
		return m_strError;
	}

	// --- Importiere die XML-Datei ein Array/ Hash
	public boolean bImportAllData(final String strXmlFile) {

		// --- Initialize
		boolean bError = true;
		try {

			this.m_parser = XMLReaderFactory.createXMLReader();

			// --- Kopie der Inputdatei erstellen, und Prolog entfernen <?xml...
			final InputStreamReader reader = new InputStreamReader(new FileInputStream(strXmlFile), "UTF-8");
			final BufferedReader in = new BufferedReader(reader);
			final OutputStreamWriter writer = new OutputStreamWriter(
					new FileOutputStream(strXmlFile.replaceAll(".xml", "_tmp.xml")), "UTF-8");
			final BufferedWriter out = new BufferedWriter(writer);
			String zeile = null;
			while ((zeile = in.readLine()) != null) {
				if (zeile.length() > 0) {
					if (!zeile.startsWith("<?xml")) {
						out.write(zeile);
						out.newLine();
					}
					if (zeile.toUpperCase().startsWith("</WORKBOOK>")) {
						// --- here is the end
						break;
					}
				}
			}
			out.flush();
			out.close();
			in.close();

			// this.out = new OutputStreamWriter(System.out);
			final FileInputStream oFileInputStream = new FileInputStream(strXmlFile.replaceAll(".xml", "_tmp.xml"));
			final InputStreamReader oInputStreamReader = new InputStreamReader(oFileInputStream, "UTF-8");

			// --- Initialize Content-Handler
			final DataImportExt dataimportext = new DataImportExt();
			this.m_parser.setContentHandler(dataimportext);
			this.m_parser.parse(new InputSource(oInputStreamReader));

			// --- Alles OK
			bError = false;

		} catch (final IOException e1) {
			m_strError = "IOException=" + e1.getMessage();

		} catch (final SAXException e2) {
			m_strError = "SAXException=" + e2.getMessage();
			e2.printStackTrace();
		}
		if (bError) {
			System.out.println(m_strError);
		}

		return bError;
	}

	public void ImportData(final String strXmlFile, final String strJob, final String strInputLanguage,
			final String strOutputLanguage, final int iPosIndex, final int iPosValue) {

		// --- Initialize
		m_iPosIndex = iPosIndex;
		m_iPosValue = iPosValue;
		m_aHashSet = new HashSet();

		// --- Setze Sprache
		//m_DefaultLanguage = SetLanguage("DE");
		try {
			final XMLReader parser = XMLReaderFactory.createXMLReader();

			// Since this just writes onto the console, it's best
			// to use the system default encoding, which is what
			// we get by not specifying an explicit encoding here.
			this.m_parser = parser;
			// this.out = new OutputStreamWriter(System.out);

			// --- Initialize Content-Handler
			m_parser.setContentHandler(this);
			m_parser.parse(strXmlFile);

			// --- Initialisieren 
			if (strJob.equals("import_keywords")) {
				// --- Alle Keyword der Ausgabespache löschen
				deleteAllKeyword(strOutputLanguage);
			}

			// --- Schleife über alle Keys
			if (strJob.equals("import_footnotes") || strJob.equals("import_keywords")) {

				String strKey = "";
				String strValue = "";
				System.out.println("SIZE=" + m_aHashMap.size());
				for (final Iterator it1 = m_aHashMap.keySet().iterator(); it1.hasNext();) {

					// --- Get Key / Value
					strKey = (String) it1.next();
					strValue = (String) m_aHashMap.get(strKey);
					strKey = strKey.replaceAll("\'", "");
					strValue = strValue.replaceAll("\'", "");

					// --- JOB => Import Fussnoten
					if (strJob.equals("import_footnotes")) {
						setFootnote(strKey, strOutputLanguage, strValue);
					}

					// --- JOB => Import Keyowrds
					if (strJob.equals("import_keywords")) {
						if (strKey.indexOf('-') >= 0) {
							setKeywords(strKey, strInputLanguage, strOutputLanguage, strValue);
						}
					}
				}

			} // --- if ( strJob.equals("import_footnotes") || strJob.equals("import_keywords") ) {

			// --- JOB => Import USA-Varianten
			if (strJob.equals("import_us_varianten")) {
				setVariants(m_aHashMap, strOutputLanguage);
			}

			// --- JOB => Import EAN-Nummern
			if (strJob.equals("import_ean")) {
				setEAN(m_aHashMap, strOutputLanguage);
			}

		} catch (final Exception e) {
			System.err.println(e);
		}
	}

	public void deleteAllKeyword(final String strOutputLanguage) {

		// --- Initialize
		System.out.println("Schlagworte in " + strOutputLanguage + " werden gelöscht.");
		final WeraManager wm = new WeraManager();
		final Language oLang = C2LManager.getInstance().getLanguageByIsoCode(strOutputLanguage);

		// --- Hole alle Keywords
		final Set keywords = wm.getAllKeyword();

		// --- Schleife über alle Keywords
		Keyword keyword = null;
		for (final Iterator it1 = keywords.iterator(); it1.hasNext();) {
			// --- Get Keyword
			keyword = (Keyword) it1.next();

			// --- Prüfe die Sprache
			if (keyword.getLanguage() != null && keyword.getLanguage().equals(oLang)) {
				try {
					keyword.remove();
				} catch (final ConsistencyCheckException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public void setKeywords(final String strPk, final String strInputLanguage, final String strOutputLanguage,
			final String strValue) {

		// --- Initialize
		final WeraManager wm = new WeraManager();
		final JaloSession js = JaloSession.getCurrentSession();
		Collection aKeywords = null;
		final ComposedType KeywordType = js.getTypeManager().getComposedType(Keyword.class);
		final Language oLang = C2LManager.getInstance().getLanguageByIsoCode(strOutputLanguage);

		// --- Hole das Original Keyowrd
		Item item = null;
		try {
			item = js.getItem(strPk);
		} catch (final Exception e) {
			System.out.println("+++++++++++++Keyword" + strPk + "not found.");
			return;
		}
		final Keyword kw = (Keyword) item;
		System.out.println("getKeyword=>" + kw.getKeyword());
		Collection products = new ArrayList();

		// --- Hole alle Produkte des Keyword
		wm.SetLanguage(strInputLanguage);
		products = (Collection) wm.getAttribute(kw, "products");

		// --- Original-Keyword ebenfalls anpassen
		if (m_bModifyOriginal) {
			System.out.println("Modify Keyword" + strValue);
			wm.setAttribute(kw, "keyword", strValue);
		}

		// --- Anlegen einer neuen Instance des Keywords in strOutputLanguage
		// new Instance
		wm.SetLanguage(strOutputLanguage);
		final HashMap mKeyword = new HashMap();
		mKeyword.put("keyword", strValue);
		mKeyword.put("language", oLang);
		mKeyword.put("products", products);
		mKeyword.put("catalogVersion", wm.m_weraCatalogVersion);
		Keyword okeywordNew = null;
		try {
			// --- Keyword anlegen
			okeywordNew = (Keyword) KeywordType.newInstance(mKeyword);

			// --- Schleife über alle diese Keyworte
			Product product = null;
			for (final Iterator it1 = products.iterator(); it1.hasNext();) {
				product = (Product) it1.next();

				// --- Ordne das Keyword dem Produkt zu
				if (!m_aHashSet.contains(product)) {
					// --- Lösche die Keywords des Products in der Importsprache (strLanguage)
					wm.SetLanguage(strOutputLanguage);
					aKeywords = new ArrayList();

					// --- Merke das Object
					m_aHashSet.add(product);
				} else {
					// --- Hole die Liste der Keywords des Produkts
					final Collection aKeywordsTmp = (Collection) wm.getAttribute(product, "keywords");
					aKeywords = new ArrayList();
					aKeywords.addAll(aKeywordsTmp);
				}
				// --- Keyword übernehmen
				if (!aKeywords.contains(okeywordNew)) {
					aKeywords.add(okeywordNew);
					wm.setAttribute(product, "keywords", aKeywords);
				}
				System.out.println("Product.code=" + product.getCode());

			} // --- for ( Iterator it1 = products.iterator(); it1.hasNext();  )

		} catch (final JaloGenericCreationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (final JaloAbstractTypeException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void setFootnote(final String strIndex, final String strLanguage, final String strValue) {

		// --- Initialize
		final String strCode = strIndex;
		final String strName = "";
		final String strString = "";

		Footnote footnote = null;
		final Map value = new HashMap();
		value.put("code", strIndex);

		final SearchResult res = JaloSession
				.getCurrentSession()
				.getFlexibleSearch()
				.search("SELECT {" + Item.PK + "} FROM {Footnote} " + "WHERE {" + Footnote.CODE + "} " + "LIKE ?code", value,
						Collections.singletonList(Footnote.class), true, // fail on unknown fields
						true, // don't need total
						0, 1 // range
				);

		if (res.getCount() > 0) {
			System.out.print("FOUND=" + strCode);
			footnote = (Footnote) res.getResult().get(0);
			final WeraManager wm = new WeraManager();
			wm.SetLanguage(strLanguage);
			footnote.setLocalizedProperty("name", strValue);
		} else {
			System.out.print("NOTFOUND=" + strCode);
		}
	}

	public void setVariants(final HashMap aHashSet, final String strLanguage) {

		// --- Initialize
		final WeraManager wm = new WeraManager();
		String strCode = "";
		String strLang = "";
		final String[] aWeraLanguages
				= {"de", "cn", "cs", "en", "es", "fr", "it", "jp", "pl", "ru", "us-en"};
		final HashSet setWeraLanguages = new HashSet(Arrays.asList(aWeraLanguages));

		// --- Hole alle WeraVarianten
		final ComposedType WeraVarianteType = JaloSession.getCurrentSession().getTypeManager().getComposedType(WeraVariante.class);
		final Set weravariants = WeraVarianteType.getAllInstances();
		WeraVariante weravariant = null;
		for (final Iterator it1 = weravariants.iterator(); it1.hasNext();) {
			// --- Get WeraVariante
			weravariant = (WeraVariante) it1.next();
			strCode = weravariant.getCode();
			final String strHS = (String) aHashSet.get("05" + strCode + "001");

			for (final Iterator it2 = setWeraLanguages.iterator(); it2.hasNext();) {
				strLang = (String) it2.next();
				wm.SetLanguage(strLang);
				if (strLang.equals(strLanguage) && strHS != null) {
					weravariant.setLocalizedProperty("variantenNr", strHS.substring(8));
					System.out.print("SET Aritkel=> strCode=" + "05" + strCode + "001");
					System.out.print(", LANG=> strCode=" + strLanguage);
					System.out.println("-strHS=>" + strHS.substring(8));
				} else {
					weravariant.setLocalizedProperty("variantenNr", "001");
				}
			}
		}

		// --- Hole alle WeraProductSet
		final ComposedType WeraProductSetType = JaloSession.getCurrentSession().getTypeManager()
				.getComposedType(WeraProductSet.class);
		final Set weraproductsets = WeraProductSetType.getAllInstances();
		WeraProductSet weraproductset = null;
		for (final Iterator it1 = weraproductsets.iterator(); it1.hasNext();) {
			// --- Get WeraVariante
			weraproductset = (WeraProductSet) it1.next();
			strCode = (String) wm.getAttribute(weraproductset, "artnr");
			final String strHS = (String) aHashSet.get("05" + strCode + "001");

			for (final Iterator it2 = setWeraLanguages.iterator(); it2.hasNext();) {
				strLang = (String) it2.next();
				wm.SetLanguage(strLang);
				if (strLang.equals(strLanguage) && strHS != null) {
					weraproductset.setLocalizedProperty("variantenNr", strHS.substring(8));
					System.out.print("SET Satz=> strCode=" + "05" + strCode + "001");
					System.out.print(", LANG=> strCode=" + strLanguage);
					System.out.println("-strHS=>" + strHS.substring(8));
				} else {
					weraproductset.setLocalizedProperty("variantenNr", "001");
				}
			}
		}
	}

	public void setEAN(final HashMap aHashSet, final String strLanguage) {

		// --- Initialize
		final WeraManager wm = new WeraManager();
		String strCode = "";
		final String strLang = "";
		final String[] aWeraLanguages
				= {"de", "cn", "cs", "en", "es", "fr", "it", "jp", "pl", "ru", "us-en"};
		final HashSet setWeraLanguages = new HashSet(Arrays.asList(aWeraLanguages));

		// --- Hole alle WeraVarianten
		final ComposedType WeraVarianteType = JaloSession.getCurrentSession().getTypeManager().getComposedType(WeraVariante.class);
		final Set weravariants = WeraVarianteType.getAllInstances();
		WeraVariante weravariant = null;
		for (final Iterator it1 = weravariants.iterator(); it1.hasNext();) {
			// --- Get WeraVariante
			weravariant = (WeraVariante) it1.next();
			if (weravariant instanceof WeraVarianteSet) {
				continue;
			}

			strCode = weravariant.getCode();
			final String strEAN = (String) aHashSet.get("05" + strCode + "001");
			if (strEAN != null && strEAN.length() > 0) {
				wm.setAttribute(weravariant, "ean", strEAN);
				System.out.println("VAR=05" + strCode + "001=>" + strEAN + "<==");
			}
		}

		// --- Hole alle WeraProductSet
		final ComposedType WeraProductSetType = JaloSession.getCurrentSession().getTypeManager()
				.getComposedType(WeraProductSet.class);
		final Set weraproductsets = WeraProductSetType.getAllInstances();
		WeraProductSet weraproductset = null;
		for (final Iterator it1 = weraproductsets.iterator(); it1.hasNext();) {
			// --- Get WeraVariante
			weraproductset = (WeraProductSet) it1.next();
			strCode = (String) wm.getAttribute(weraproductset, "artnr");
			final String strEAN = (String) aHashSet.get("05" + strCode + "001");
			if (strEAN != null && strEAN.length() > 0) {
				wm.setAttribute(weraproductset, "ean", strEAN);
				System.out.println("PS=05" + strCode + "001=>" + strEAN + "<==");
			}
		}
	}

	// --- CSV-Liste aller Artikel einer Katalogversion erstellen
	public void exportEANCSV(final String sPfadName, final String sFileName, final String strCatalog,
			final String strCatalogVersion, final String strLanguage) {

		// --- Debug
		LOG.info("exportEANCSV ( start ) " + " sFileName=" + sPfadName + sFileName);

		// --- Initialize
		final WeraManager wm = new WeraManager();
		String strProdcuktNr = "";
		String strCode = "";
		String strEAN = "";
		String strEAN_US = "";
		String strName = "";
		String strLine = "";
		final ArrayList aOutput = new ArrayList();
		WeraProduct weraproduct = null;

		// --- Headerzeile generieren und merken
		strLine = "Code\tEAN\tEAN_US\tProduktNr\tProduktname";
		aOutput.add(strLine);

		// --- Hole alle Produkte einer bestimmten Kataloges / KatalogVesion (sortiert)
		final WeraKatalog oWerakatalog = new WeraKatalog();
		final Collection products = oWerakatalog.getProductsFromCatalogVersion(strCatalog, strCatalogVersion, 1);

		// --- Sprache setzen
		wm.SetLanguage(strLanguage);

		// --- Schleife über alle Produkte
		for (final Iterator it1 = products.iterator(); it1.hasNext();) {

			// --- Get WeraProduktSet und Daten
			final HashMap hashData = (HashMap) it1.next();
			weraproduct = (WeraProduct) hashData.get("product");

			// --- Product oder Satz
			if (weraproduct instanceof WeraProductSet) {
				// --- Ausagbe Satz
				strCode = (String) wm.getAttribute(weraproduct, "lagernr") + (String) wm.getAttribute(weraproduct, "artnr")
						+ (String) wm.getAttribute(weraproduct, "variantenNr");
				strProdcuktNr = (String) wm.getAttribute(weraproduct, "code");
				strName = (String) wm.getAttribute(weraproduct, "name");
				strEAN = (String) wm.getAttribute(weraproduct, "ean");
				strEAN_US = (String) wm.getAttribute(weraproduct, "ean_us");

				// --- Zeile generieren und merken
				strLine = strCode + "\t" + strEAN + "\t" + strEAN_US + "\t" + strProdcuktNr + "\t" + strName;
				aOutput.add(strLine.replace("null", ""));

			} else {

				// --- Daten direkt from Produkt
				strName = (String) wm.getAttribute(weraproduct, "name");
				strProdcuktNr = (String) wm.getAttribute(weraproduct, "code");
				final Collection articels = (Collection) wm.getAttribute(weraproduct, "variants");

				// --- Ausagbe Artikel
				for (final Iterator it1_variant = articels.iterator(); it1_variant.hasNext();) {
					// --- Get WeraVariante
					final WeraVariante weravariant = (WeraVariante) it1_variant.next();
					if (weravariant instanceof WeraVarianteSet) {
						continue;
					}

					// --- Hole Aktiv (passive Filtern)
					final Boolean bAktiv = (Boolean) wm.getAttribute(weravariant, "aktiv");
					if (bAktiv.booleanValue() == false) {
						continue;
					}

					// --- Hole Daten
					strCode = (String) wm.getAttribute(weravariant, "lagernr") + (String) wm.getAttribute(weravariant, "code")
							+ (String) wm.getAttribute(weravariant, "variantenNr");
					strEAN = (String) wm.getAttribute(weravariant, "ean");
					strEAN_US = (String) wm.getAttribute(weravariant, "ean_us");

					// --- Zeile generieren und merken
					strLine = strCode + "\t" + strEAN + "\t" + strEAN_US + "\t" + strProdcuktNr + "\t" + strName;
					aOutput.add(strLine.replace("null", ""));

				}
			}

		} // --- for ( Iterator it1=products.iterator(); it1.hasNext(); ) { 

		// --- Daten schreiben
		final XmlSupport oXmlSupport = new XmlSupport();
		oXmlSupport._WriteFileFromArrayEncoding(aOutput, sPfadName + sFileName, "UTF-8");

		// --- Debug
		LOG.info("exportEANCSV ( ende ) ");
	}

	public void importEANCSV(final String sPfadName, final String sFileName) {

		// --- Initialize
		final WeraManager wm = WeraManager.getInstance();
		String strEAN = "";
		String strEANUS = "";
		String strCode = "";
		String strZolltarifNr = "";
		String strUrsprungsland = "";
		String strManufacturer = "";
		String strGewicht = "";
		final String PRODUCTS = "code";
		final String EANSCODE = "ean";
		final String EANSCODE_US = "ean_us";
		final String ZOLLTARIF = "zolltarif";
		final String URSPRUNGSLAND = "ursprungsland";
		final String MANUFACTURER = "manufacturer";
		final String GEWICHT = "gewicht";
		final String DELIMETER = "\t";
		final HashMap hHeader = new HashMap();
		final HashMap hProducts = new HashMap();
		final HashMap hProductsVarNr = new HashMap();

		final String strResult = "";
		String line = "";
		final ArrayList records = new ArrayList();
		Integer iColEANCodes = null;
		Integer iColEAN_USCodes = null;
		Integer iColZOLLTARIF = null;
		Integer iColURSPRUNGSLAND = null;
		Integer iColMANUFACTURER = null;
		Integer iColGEWICHT = null;
		Integer iColProductNr = null;

		// --- Debug
		LOG.info("importEANCSV ( start ) " + " sFileName=" + sPfadName + sFileName);

		try {
			// --- Einlesen der Header ---------------------------------------------------------------------------------------------
			final BufferedReader reader = new BufferedReader(new FileReader(sPfadName + sFileName));
			LOG.info("Daten werden eingelesen...");
			while ((line = reader.readLine()) != null) {
				// --- Sind Daten vorhanden?
				if (line == null || line.trim().length() == 0) {
					continue;
				}

				// --- Zeile bearbeiten
				final String[] aLineInput = line.split("\t");
				if (hHeader.size() == 0) {
					// ---- Headerzeile initialisieren -----------------------------------------------------------
					for (int iCol = 0; iCol < aLineInput.length; iCol++) {
						hHeader.put(aLineInput[iCol].toLowerCase(), new Integer(iCol));
					}
					iColEAN_USCodes = ((Integer) hHeader.get(EANSCODE_US));
					if (iColEAN_USCodes == null) {
						LOG.error("=> Spalte '" + EANSCODE_US + "' nicht vorhanden. Abbruch");
						return;
					}
					/*
					// --- Prüfen der Headerspalten
					iColEANCodes = ((Integer) hHeader.get(EANSCODE));
					if (iColEANCodes == null)
					{
						LOG.error("=> Spalte '" + EANSCODE + "' nicht vorhanden. Abbruch");
						return;
					}
					iColZOLLTARIF = ((Integer) hHeader.get(ZOLLTARIF));
					if (iColZOLLTARIF == null)
					{
						LOG.error("=> Spalte '" + ZOLLTARIF + "' nicht vorhanden. Abbruch");
						return;
					}
					iColURSPRUNGSLAND = ((Integer) hHeader.get(URSPRUNGSLAND));
					if (iColURSPRUNGSLAND == null)
					{
						LOG.error("=> Spalte '" + URSPRUNGSLAND + "' nicht vorhanden. Abbruch");
						return;
					}
					iColMANUFACTURER = ((Integer) hHeader.get(MANUFACTURER));
					if (iColMANUFACTURER == null)
					{
						LOG.error("=> Spalte '" + MANUFACTURER + "' nicht vorhanden. Abbruch");
						return;
					}

					iColGEWICHT = ((Integer) hHeader.get(GEWICHT));
					if (iColGEWICHT == null)
					{
						LOG.error("=> Spalte '" + GEWICHT + "' nicht vorhanden. Abbruch");
						return;
					}
					 */
					iColProductNr = ((Integer) hHeader.get(PRODUCTS));
					if (iColProductNr == null) {
						LOG.error("=> Spalte '" + PRODUCTS + "' nicht vorhanden. Abbruch");
						return;
					}
					// ---- Headerzeile initialisieren -----------------------------------------------------------

				} else {
					// --- Zeile merken
					if (aLineInput.length > iColProductNr.intValue()) {
						strCode = aLineInput[iColProductNr.intValue()];
						if (strCode.length() == 11) {
							final String sVarNr = strCode.substring(8);
							final String sBaseCode = strCode.substring(0, 8);
							hProducts.put(sBaseCode + "001", aLineInput);
							hProductsVarNr.put(sBaseCode + "001", sVarNr);
							LOG.info("storing line to key " + sBaseCode + "001, real VarNr is " + sVarNr);
						}

					}
				}
			}
			reader.close();

			// --- Hole alle WeraVarianten ----------------------------------------------------------------
			LOG.info("Artikel werden zugewiesen...");
			final ComposedType WeraVarianteType = JaloSession.getCurrentSession().getTypeManager()
					.getComposedType(WeraVariante.class);
			final Set weravariants = WeraVarianteType.getAllInstances();
			WeraVariante weravariant = null;
			for (final Iterator it1 = weravariants.iterator(); it1.hasNext();) {
				// --- Get WeraVariante
				weravariant = (WeraVariante) it1.next();
				if (weravariant instanceof WeraVarianteSet) {
					continue;
				}

				// --- Prüfe, ob eine Datenzeile vorhanden ist
				strCode = weravariant.getCode();
				final String[] aLineOutpunt = (String[]) hProducts.get("05" + strCode + "001");
				if (aLineOutpunt != null && aLineOutpunt.length > 0) {
					/*
					// --- EAN
					if (aLineOutpunt.length > iColEANCodes.intValue())
					{
						strEAN = aLineOutpunt[iColEANCodes.intValue()];
						if (strEAN != null && strEAN.length() > 0)
						{
							wm.setAttribute(weravariant, "ean", strEAN);
							LOG.info("Artikel " + strCode + ", EAN=" + strEAN);
						}
					}
					 */
					// --- EAN US
					if (aLineOutpunt.length > iColEAN_USCodes.intValue()) {
						strEANUS = aLineOutpunt[iColEAN_USCodes.intValue()];
						if (strEANUS != null && strEANUS.length() > 0) {
							wm.setAttribute(weravariant, "ean_us", strEANUS);
							LOG.info("Artikel " + strCode + ", EAN_US=" + strEANUS);
						}
					}
					/*
					// --- ZolltarifNr
					if (aLineOutpunt.length > iColZOLLTARIF.intValue())
					{
						strZolltarifNr = aLineOutpunt[iColZOLLTARIF.intValue()];
						if (strZolltarifNr != null && strZolltarifNr.length() > 0)
						{
							wm.setAttribute(weravariant, "ZolltarifNr", strZolltarifNr);
							LOG.info("Artikel " + strCode + ", ZOLLTARIFNR=" + strZolltarifNr);
						}
					}
					// --- Ursprungsland
					if (aLineOutpunt.length > iColURSPRUNGSLAND.intValue())
					{
						strUrsprungsland = aLineOutpunt[iColURSPRUNGSLAND.intValue()];
						if (strUrsprungsland != null && strUrsprungsland.length() > 0)
						{
							wm.setAttribute(weravariant, "Ursprungsland", strUrsprungsland);
							LOG.info("Artikel " + strCode + ", URSPRUNGSLAND=" + strUrsprungsland);
						}
					}
					// --- Manufacturer
					if (aLineOutpunt.length > iColMANUFACTURER.intValue())
					{
						strManufacturer = aLineOutpunt[iColMANUFACTURER.intValue()];
						if (strManufacturer != null && strManufacturer.length() > 0)
						{
							wm.setAttribute(weravariant, "manufacturerName", strManufacturer);
							LOG.info("Artikel " + strCode + ", MANUFACTURER=" + strManufacturer);
						}
					}

					// --- Gewicht
					if (aLineOutpunt.length > iColGEWICHT.intValue())
					{
						strGewicht = aLineOutpunt[iColGEWICHT.intValue()];
						if (strGewicht != null && strGewicht.length() > 0)
						{
							wm.setAttribute(weravariant, "Gewicht", strGewicht);
							LOG.info("Artikel " + strCode + ", GEWICHT=" + strGewicht);
						}
					}
					 */
				} // --- if ( aLineOutpunt.length > 0 ) {

			}
			// --- Hole alle WeraVarianten ----------------------------------------------------------------

			// --- Hole alle WeraProductSet ----------------------------------------------------------------
			LOG.info("Sätze werden zugewiesen...");
			final ComposedType WeraProductSetType = JaloSession.getCurrentSession().getTypeManager()
					.getComposedType(WeraProductSet.class);
			final Set weraproductsets = WeraProductSetType.getAllInstances();
			WeraProductSet weraproductset = null;
			for (final Iterator it1 = weraproductsets.iterator(); it1.hasNext();) {
				// --- Get WeraVariante
				weraproductset = (WeraProductSet) it1.next();
				strCode = (String) wm.getAttribute(weraproductset, "artnr");

				// --- Prüfe, ob eine Datenzeile vorhanden ist
				final String[] aLineOutpunt = (String[]) hProducts.get("05" + strCode + "001");
				if (aLineOutpunt != null && aLineOutpunt.length > 0) {
					/*
					// --- EAN
					if (aLineOutpunt.length > iColEANCodes.intValue())
					{
						strEAN = aLineOutpunt[iColEANCodes.intValue()];
						if (strEAN != null && strEAN.length() > 0)
						{
							wm.setAttribute(weraproductset, "ean", strEAN);
							LOG.info("Satz " + strCode + ", EAN=" + strEAN);
						}
					}
					 */
					// --- EAN US
					if (aLineOutpunt.length > iColEAN_USCodes.intValue()) {
						strEANUS = aLineOutpunt[iColEAN_USCodes.intValue()];
						if (strEANUS != null && strEANUS.length() > 0) {
							wm.setAttribute(weraproductset, "ean_us", strEANUS);
							LOG.info("Satz " + strCode + ", EAN_US=" + strEANUS);
						}
					}
					/*
					// --- ZolltarifNr
					if (aLineOutpunt.length > iColZOLLTARIF.intValue())
					{
						strZolltarifNr = aLineOutpunt[iColZOLLTARIF.intValue()];
						if (strZolltarifNr != null && strZolltarifNr.length() > 0)
						{
							wm.setAttribute(weraproductset, "ZolltarifNr", strZolltarifNr);
							LOG.info("Satz " + strCode + ", ZOLLTARIFNR=" + strZolltarifNr);
						}
					}
					// --- Ursprungsland
					if (aLineOutpunt.length > iColURSPRUNGSLAND.intValue())
					{
						strUrsprungsland = aLineOutpunt[iColURSPRUNGSLAND.intValue()];
						if (strUrsprungsland != null && strUrsprungsland.length() > 0)
						{
							wm.setAttribute(weraproductset, "Ursprungsland", strUrsprungsland);
							LOG.info("Satz " + strCode + ", URSPRUNGSLAND=" + strUrsprungsland);
						}
					}
					// --- Manufacturer
					if (aLineOutpunt.length > iColMANUFACTURER.intValue())
					{
						strManufacturer = aLineOutpunt[iColMANUFACTURER.intValue()];
						if (strManufacturer != null && strManufacturer.length() > 0)
						{
							wm.setAttribute(weraproductset, "manufacturerName", strManufacturer);
							LOG.info("Satz " + strCode + ", MANUFACTURER=" + strManufacturer);
						}
					}

					// --- Gewicht
					if (aLineOutpunt.length > iColGEWICHT.intValue())
					{
						strGewicht = aLineOutpunt[iColGEWICHT.intValue()];
						if (strGewicht != null && strGewicht.length() > 0)
						{
							wm.setAttribute(weraproductset, "Gewicht", strGewicht);
							LOG.info("Satz " + strCode + ", GEWICHT=" + strGewicht);
						}
					}
					 */

				} // --- if ( aLineOutpunt.length > 0 ) {
			}
			// --- Hole alle WeraProductSet ----------------------------------------------------------------

		} catch (final Exception e) {
			LOG.error("+Zeile=" + line);
			e.printStackTrace();
		}

		// --- Debug
		LOG.info("importEANCSV ( ende ) ");

	}

	// --- Import logistische Daten und kundenspezifische Preise (Nordwestliste(
	public void importBMECAT_Daten_Preise(final String sPfadName, final String sFileName, final String strKundenpreisListe) {

		// --- Debug
		LOG.info("importBMECAT_Daten_Preise ( start ) " + " sFileName=" + sPfadName + sFileName);

		// --- Initialize
		String strValue = "";
		String strCode = "";
		final String PRODUCTS = "code";
		final String SPALTE_WAEHRUNG = "währung";
		final String SPALTE_M1 = "m1";
		final String SPALTE_P1 = "p1";
		final String SPALTE_M2 = "m2";
		final String SPALTE_P2 = "p2";
		final String SPALTE_EVP = "evp";
		// final String SPALTE_NEU = "neu";
		//final String SPALTE_VE = "ve";
		final String SPALTE_EAN = "ean";
		final String SPALTE_ZOLLTARIF = "zolltarif";
		final String SPALTE_URSPRUNGSLAND = "ursprungsland";
		final String SPALTE_GEWICHT = "gewicht";
		final String SPALTE_GEWICHTSEINHEIT = "gewichtseinheit";
		final String SPALTE_PACKM_GEW = "packm.gew";
		final String SPALTE_PACKM_GEW_EH = "packm.gew.eh";
		final String SPALTE_PACKM_LAENGE = "packm.länge";
		final String SPALTE_PACKM_BREITE = "packm.breite";
		final String SPALTE_PACKM_HOEHE = "packm.höhe";
		final String SPALTE_PACKM_EINH = "packm.einh";

		final String DELIMETER = "\t";
		final HashMap<String, Integer> hHeader = new HashMap();
		final HashMap hProducts = new HashMap();
		final String strResult = "";
		String line = "";
		final ArrayList records = new ArrayList();
		final EnumerationValue eEnumValueGramm = EnumerationManager.getInstance().getEnumerationValue("EinheitEnum", "gramm");
		final EnumerationValue eEnumValueMillimeter = EnumerationManager.getInstance().getEnumerationValue("EinheitPackmEnum",
				"millimeter");

		// --- Initialize Katalogversion
		wm_weraimportpricelist = new WeraImportPricelist(); // --- abgeleitet von WeraManager
		wm_weraimportpricelist.m_weraCatalogVersion = wm_weraimportpricelist.getCatalogVersion("weracatalog", "weramaster");

		// --- Benötigte Spalten vorinitialisieren
		hHeader.put(PRODUCTS, new Integer(-1));
		hHeader.put(SPALTE_EAN, new Integer(-1));
		hHeader.put(SPALTE_ZOLLTARIF, new Integer(-1));
		hHeader.put(SPALTE_URSPRUNGSLAND, new Integer(-1));
		hHeader.put(SPALTE_GEWICHT, new Integer(-1));
		hHeader.put(SPALTE_GEWICHTSEINHEIT, new Integer(-1));
		hHeader.put(SPALTE_PACKM_GEW, new Integer(-1));
		hHeader.put(SPALTE_PACKM_GEW_EH, new Integer(-1));

		hHeader.put(SPALTE_WAEHRUNG, new Integer(-1));
		hHeader.put(SPALTE_M1, new Integer(-1));
		hHeader.put(SPALTE_P1, new Integer(-1));
		hHeader.put(SPALTE_M2, new Integer(-1));
		hHeader.put(SPALTE_P2, new Integer(-1));
		hHeader.put(SPALTE_EVP, new Integer(-1));

		//hHeader.put(SPALTE_NEU, new Integer(-1));
		//hHeader.put(SPALTE_VE, new Integer(-1));
		hHeader.put(SPALTE_PACKM_LAENGE, new Integer(-1));
		hHeader.put(SPALTE_PACKM_BREITE, new Integer(-1));
		hHeader.put(SPALTE_PACKM_HOEHE, new Integer(-1));
		hHeader.put(SPALTE_PACKM_EINH, new Integer(-1));

		// --- Initialize
		boolean bColError = false;
		boolean bHeaderInitialized = false;

		try {
			// --- Einlesen der Eingabedatei ---------------------------------------------------------------------------------------
			final BufferedReader reader = new BufferedReader(new FileReader(sPfadName + sFileName));
			LOG.info("Daten werden eingelesen...");
			while ((line = reader.readLine()) != null) {
				// --- Sind Daten vorhanden?
				if (line == null || line.trim().length() == 0) {
					continue;
				}

				// --- Zeile auftrennen und Anzahl der Datenfelder prüfen
				final String[] aLineInput = line.split("\t");
				if (aLineInput.length < 5) {
					continue;
				}

				// --- Zeile bearbeiten
				if (bHeaderInitialized == false) {
					// ---- Headerzeile initialisieren -----------------------------------------------------------
					for (int iCol = 0; iCol < aLineInput.length; iCol++) {
						// --- Spaltennummer der erforderlichen Spalten merken
						if (hHeader.containsKey(aLineInput[iCol].toLowerCase())) {
							hHeader.put(aLineInput[iCol].toLowerCase(), new Integer(iCol));
							bHeaderInitialized = true;
						}
					}

					// --- Prüfen der Headerspalten, falls diese initialisiert wurden
					if (bHeaderInitialized) {
						for (final Iterator itCols = hHeader.keySet().iterator(); itCols.hasNext();) {
							final String strKey = (String) itCols.next();
							if (hHeader.get(strKey).intValue() == -1) {
								bColError = true;
								LOG.error("Spalte " + strKey + " ist nicht vorhanden!");
							}
						}
					} // --- if ( bHeaderInitialized ) {
					// ---- Headerzeile initialisieren -----------------------------------------------------------

				} else {
					// --- Nur einlesen, wenn der Header initialisiert wurde....
					if (bHeaderInitialized) {
						// --- Zeile merken
						strCode = aLineInput[hHeader.get(PRODUCTS).intValue()];
						hProducts.put(strCode, aLineInput);
					}
				}

			}

			// --- Eingabedatei schliessen
			reader.close();

			// --- Ist ein Fehler aufgetreten, dann brechen wir hier besser ab.....
			if (bColError) {
				LOG.error("Es sind nicht alle erforderlichen Spalten vorhanden. Funktionsabbruch!!");
				return;
			}
			// --- Einlesen der Eingabedatei ---------------------------------------------------------------------------------------

			// --- Deletes all PriceRows of all products in the Wera Catalogue
			// 		 - returns number of deleted rows
			LOG.info("Preise werden geloescht. Preisliste: " + strKundenpreisListe);
			final int iNumRowsDeleted = wm_weraimportpricelist._deleteAllPrices(strKundenpreisListe);
			LOG.info(iNumRowsDeleted + " Preise wurden geloescht.");

			// --- Hole alle WeraVarianten ----------------------------------------------------------------
			LOG.info("Artikel werden zugewiesen...");
			final ComposedType WeraVarianteType = JaloSession.getCurrentSession().getTypeManager()
					.getComposedType(WeraVariante.class);
			final Set weravariants = WeraVarianteType.getAllInstances();
			WeraVariante weravariant = null;
			for (final Iterator it1 = weravariants.iterator(); it1.hasNext();) {
				// --- Get WeraVariante
				weravariant = (WeraVariante) it1.next();
				if (weravariant instanceof WeraVarianteSet) {
					continue;
				}

				// --- Prüfe, ob eine Datenzeile vorhanden ist
				strCode = (String) wm_weraimportpricelist.getAttribute(weravariant, "lagernr") + weravariant.getCode()
						+ (String) wm_weraimportpricelist.getAttribute(weravariant, "variantenNr");
				final String[] aLineOutpunt = (String[]) hProducts.get(strCode);
				if (aLineOutpunt != null && aLineOutpunt.length > 0) {
					LOG.info("Artikel " + strCode + " wird verarbeitet.");

					// --- EAN
					if (aLineOutpunt.length > hHeader.get(SPALTE_EAN).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_EAN).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "ean", strValue);
							// LOG.info( "Artikel "+ strCode + ", EAN=" + strValue );
						}
					}
					// --- ZolltarifNr
					if (aLineOutpunt.length > hHeader.get(SPALTE_ZOLLTARIF).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_ZOLLTARIF).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "ZolltarifNr", strValue);
							//LOG.info( "Artikel "+ strCode + ", ZOLLTARIFNR=" + strValue );
						}
					}
					// --- Ursprungsland
					if (aLineOutpunt.length > hHeader.get(SPALTE_URSPRUNGSLAND).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_URSPRUNGSLAND).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "Ursprungsland", strValue);
							//LOG.info( "Artikel "+ strCode + ", URSPRUNGSLAND=" + strValue );
						}
					}
					// --- Gewicht / Einheit
					if (aLineOutpunt.length > hHeader.get(SPALTE_GEWICHT).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_GEWICHT).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "Gewicht", strValue);
							wm_weraimportpricelist.setAttribute(weravariant, "GewichtEinheit", eEnumValueGramm);
							wm_weraimportpricelist.setAttribute(weravariant, "packm_gewichteh", eEnumValueGramm);
							//LOG.info( "Artikel "+ strCode + ", GEWICHT=" + strValue );
						}
					}
					// --- Gewicht pro Pack
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_GEW).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_GEW).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "GewVE", strValue);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_LAENGE).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_LAENGE).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "packm_laenge", strValue);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_HOEHE).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_HOEHE).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "packm_hoehe", strValue);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_BREITE).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_BREITE).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "packm_breite", strValue);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_EINH).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_EINH).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weravariant, "packm_laengen_einheit", eEnumValueMillimeter);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}

					// --- Kundenpreisliste ---------------------------------------------------------------
					// --- Initialize
					boolean bNet = true;
					boolean bIsEVK = false;
					boolean isAufAnfrage = false;

					// --- Währung initialisieren
					String sWaehrung = "EUR";
					if (aLineOutpunt.length > hHeader.get(SPALTE_WAEHRUNG).intValue()) {
						sWaehrung = aLineOutpunt[hHeader.get(SPALTE_WAEHRUNG).intValue()];
					}

					// --- Preisinfo speichern (P1)
					int iPriority = 10;
					_importPreisInfo((Product) weravariant, SPALTE_P1, SPALTE_M1, iPriority, strKundenpreisListe,
							bNet, bIsEVK, isAufAnfrage, sWaehrung,
							aLineOutpunt, hHeader);

					// --- Preisinfo speichern (P2)
					iPriority = 20;
					_importPreisInfo((Product) weravariant, SPALTE_P2, SPALTE_M2, iPriority, strKundenpreisListe,
							bNet, bIsEVK, isAufAnfrage, sWaehrung,
							aLineOutpunt, hHeader);

					// --- Preisinfo speichern (EVP)
					iPriority = 30;
					bNet = false;
					bIsEVK = true;
					_importPreisInfo((Product) weravariant, SPALTE_EVP, "", iPriority, strKundenpreisListe,
							bNet, bIsEVK, isAufAnfrage, sWaehrung,
							aLineOutpunt, hHeader);

					// --- Kundenpreisliste ---------------------------------------------------------------
				} // --- if ( aLineOutpunt.length > 0 ) {
				else {
					LOG.error("Artikel " + strCode + " wurde nicht in der Daten gefunden.");
				}

			}
			// --- Hole alle WeraVarianten ----------------------------------------------------------------

			// --- Hole alle WeraProductSet ----------------------------------------------------------------
			LOG.info("Sätze werden zugewiesen...");
			final ComposedType WeraProductSetType = JaloSession.getCurrentSession().getTypeManager()
					.getComposedType(WeraProductSet.class);
			final Set weraproductsets = WeraProductSetType.getAllInstances();
			WeraProductSet weraproductset = null;
			for (final Iterator it1 = weraproductsets.iterator(); it1.hasNext();) {
				// --- Get WeraVariante
				weraproductset = (WeraProductSet) it1.next();
				strCode = (String) wm_weraimportpricelist.getAttribute(weraproductset, "lagernr")
						+ (String) wm_weraimportpricelist.getAttribute(weraproductset, "artnr")
						+ (String) wm_weraimportpricelist.getAttribute(weraproductset, "variantenNr");

				// --- Prüfe, ob eine Datenzeile vorhanden ist
				final String[] aLineOutpunt = (String[]) hProducts.get(strCode);
				if (aLineOutpunt != null && aLineOutpunt.length > 0) {
					LOG.info("Satz " + strCode + " wird verarbeitet.");

					// --- EAN
					if (aLineOutpunt.length > hHeader.get(SPALTE_EAN).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_EAN).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "ean", strValue);
							//LOG.info( "Satz "+ strCode + ", EAN=" + strValue );
						}
					}
					// --- ZolltarifNr
					if (aLineOutpunt.length > hHeader.get(SPALTE_ZOLLTARIF).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_ZOLLTARIF).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "ZolltarifNr", strValue);
							//LOG.info( "Satz "+ strCode + ", ZOLLTARIFNR=" + strValue );
						}
					}
					// --- Ursprungsland
					if (aLineOutpunt.length > hHeader.get(SPALTE_URSPRUNGSLAND).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_URSPRUNGSLAND).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "Ursprungsland", strValue);
							//LOG.info( "Satz "+ strCode + ", URSPRUNGSLAND=" + strValue );
						}
					}
					// --- Gewicht / Einheit
					if (aLineOutpunt.length > hHeader.get(SPALTE_GEWICHT).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_GEWICHT).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "Gewicht", strValue);
							wm_weraimportpricelist.setAttribute(weraproductset, "GewichtEinheit", eEnumValueGramm);
							wm_weraimportpricelist.setAttribute(weraproductset, "packm_gewichteh", eEnumValueGramm);
							//LOG.info( "Satz "+ strCode + ", GEWICHT=" + strValue );
						}
					}
					// --- Gewicht pro Pack
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_GEW).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_GEW).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "GewVE", strValue);
							//LOG.info( "Satz "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					// --- Gewicht pro Pack
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_GEW).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_GEW).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "GewVE", strValue);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_LAENGE).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_LAENGE).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "packm_laenge", strValue);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_HOEHE).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_HOEHE).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "packm_hoehe", strValue);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_BREITE).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_BREITE).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "packm_breite", strValue);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}
					if (aLineOutpunt.length > hHeader.get(SPALTE_PACKM_EINH).intValue()) {
						strValue = aLineOutpunt[hHeader.get(SPALTE_PACKM_EINH).intValue()];
						if (strValue != null && strValue.length() > 0) {
							wm_weraimportpricelist.setAttribute(weraproductset, "packm_laengen_einheit", eEnumValueMillimeter);
							//LOG.info( "Artikel "+ strCode + ", Gewicht pro Pack=" + strValue );
						}
					}

					// --- Kundenpreisliste ---------------------------------------------------------------
					// --- Initialize
					boolean bNet = true;
					boolean bIsEVK = false;
					boolean isAufAnfrage = false;

					// --- Währung initialisieren
					String sWaehrung = "EUR";
					if (aLineOutpunt.length > hHeader.get(SPALTE_WAEHRUNG).intValue()) {
						sWaehrung = aLineOutpunt[hHeader.get(SPALTE_WAEHRUNG).intValue()];
					}

					// --- Preisinfo speichern (P1)
					int iPriority = 10;
					_importPreisInfo((Product) weraproductset, SPALTE_P1, SPALTE_M1, iPriority, strKundenpreisListe,
							bNet, bIsEVK, isAufAnfrage, sWaehrung,
							aLineOutpunt, hHeader);

					// --- Preisinfo speichern (P2)
					iPriority = 20;
					_importPreisInfo((Product) weraproductset, SPALTE_P2, SPALTE_M2, iPriority, strKundenpreisListe,
							bNet, bIsEVK, isAufAnfrage, sWaehrung,
							aLineOutpunt, hHeader);

					// --- Preisinfo speichern (EVP)
					iPriority = 30;
					bNet = false;
					bIsEVK = true;
					_importPreisInfo((Product) weraproductset, SPALTE_EVP, "", iPriority, strKundenpreisListe,
							bNet, bIsEVK, isAufAnfrage, sWaehrung,
							aLineOutpunt, hHeader);

					// --- Kundenpreisliste ---------------------------------------------------------------
				} // --- if ( aLineOutpunt.length > 0 ) {
				else {
					LOG.error("Satz " + strCode + " wurde nicht in der Daten gefunden.");
				}
			}
			// --- Hole alle WeraProductSet ----------------------------------------------------------------

		} catch (final Exception e) {
			LOG.error("Fehler in Zeile = " + line);
			e.printStackTrace();
		}

		// --- Debug
		LOG.info("importEANCSV ( ende ) ");
	}

	// --- Preisinfo speichern
	private void _importPreisInfo(Product oProduct, String strSpaltePreis, String strSpalteQty, int iPriority, String strKundenpreisListe,
			boolean bNet, boolean bIsEVK, boolean isAufAnfrage, String sWaehrung,
			String[] aLineOutpunt, HashMap<String, Integer> hHeader) {

		// --- Debug
		LOG.info("Lese Preis " + strSpaltePreis);

		// --- Initialize
		double dPrice = 0;

		try {

			// --- Hole Preis
			if (aLineOutpunt.length > hHeader.get(strSpaltePreis).intValue()) {
				String sPriceValue = aLineOutpunt[hHeader.get(strSpaltePreis).intValue()];
				if (sPriceValue == null) {
					sPriceValue = "";
				} else {
					sPriceValue.trim();
				}
				// --- Nur der letzte Punkt zählt bei Fliesskommazahlen
				while (sPriceValue.indexOf('.') != sPriceValue.lastIndexOf('.')) {
					sPriceValue = sPriceValue.replaceFirst("\\.", "");
				}
				sPriceValue = sPriceValue.replaceAll(",", "");
				if (sPriceValue == null || sPriceValue.length() == 0) // --- Kein Preis vorhanden
				{
					LOG.error("Artikel " + oProduct.getCode() + ", Preis nicht vorhanden!");
				} else // --- Preis holen
				{
					dPrice = Double.parseDouble(sPriceValue);
				}
			}

			// --- Preisstaffel, -1 = EVP
			int iMinQty = 1;
			if (strSpalteQty.length() > 0) {
				if (aLineOutpunt.length > hHeader.get(strSpalteQty).intValue()) {
					String sMengeValue = aLineOutpunt[hHeader.get(strSpalteQty).intValue()];
					if (sMengeValue == null) {
						sMengeValue = "";
					} else {
						sMengeValue.trim();
					}
					if (isAufAnfrage == false && sMengeValue.length() > 0) {
						iMinQty = Integer.parseInt(sMengeValue);
					}
				}
			}

			// --- Preis speichern
			if (dPrice != 0) {
				wm_weraimportpricelist.insertPriceRow(oProduct, iPriority, strKundenpreisListe, iMinQty,
						bNet, dPrice, bIsEVK, isAufAnfrage, sWaehrung);
			}

		} catch (Exception e) {
			LOG.error("Artikel " + oProduct.getCode() + ", Exception = " + e.getMessage());
			e.printStackTrace();
		}
	}
}

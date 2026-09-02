package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.Keyword;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.product.ProductManager;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.jalo.VariantProduct;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;

/**
 * @author teschke
 *
 */
class IntComparator implements Comparator {

	public int compare(final Object o1, final Object o2) /* descending order */ {

		// --- Initialize
		if (o1 == null || o2 == null) {
			return 0;
		} else {
			return ((Integer) o1).compareTo((Integer) o2);
		}
	}
}

/**
 *
 * @author teschke
 */
class HashMapComparator implements Comparator {

	private boolean m_bSortAlpha = false;

	/**
	 *
	 * @param bSortAlpha
	 */
	public void SetSortType(final boolean bSortAlpha) {
		m_bSortAlpha = bSortAlpha;
	}

	/**
	 *
	 * @param o1
	 * @param o2
	 * @return
	 */
	public int compare(final Object o1, final Object o2) /* descending order */ {

		// --- Nummerische Sortierung ------------------------------------------------
		if (m_bSortAlpha == false) {
			// --- Initialize
			Integer iKey1 = null;
			Integer iKey2 = null;

			// --- Holen der Parameter
			if (o1 != null) {
				iKey1 = (Integer) ((HashMap) o1).get("sortkey0");
			}
			if (o2 != null) {
				iKey2 = (Integer) ((HashMap) o2).get("sortkey0");
			}
			if (iKey1 == null || iKey2 == null) {
				return 0;
			} else {
				return iKey1.compareTo(iKey2);
			}
		}
		// --- Nummerische Sortierung ------------------------------------------------

		// --- Alpha-Nummerische Staffel-Sortierung (ASS) ----------------------------
		// --- Initialize
		String strKey1 = null;
		String strKey2 = null;

		// --- Holen der Parameter
		if (o1 != null) {
			strKey1 = (String) ((HashMap) o1).get("sortkey0");
		}
		if (o2 != null) {
			strKey2 = (String) ((HashMap) o2).get("sortkey0");
		}

		//System.out.print("strKey1="+strKey1 + "/ strKey2="+strKey2 + "/ iResult1="+ strKey1.compareTo(strKey2));
		if (strKey1 == null || strKey2 == null) {
			return 0;
		} else {
			// --- Vergleich
			int iResult = strKey1.compareTo(strKey2);
			if (iResult == 0) {
				// --- Eintr�ge sind gleich, pr�fe 2. Key
				strKey1 = (String) ((HashMap) o1).get("sortkey1");
				strKey2 = (String) ((HashMap) o2).get("sortkey1");
				if (strKey1 != null && strKey2 != null) {
					iResult = strKey1.compareTo(strKey2);
				}

				// --- 3. Stufe
				if (iResult == 0) {
					strKey1 = (String) ((HashMap) o1).get("sortkey2");
					strKey2 = (String) ((HashMap) o2).get("sortkey2");
					if (strKey1 != null && strKey2 != null) {
						iResult = strKey1.compareTo(strKey2);
					}
				}
			}

			//System.out.println("/ iResult2=" + iResult);
			return iResult;
		}
	}
}

/**
 *
 * @author teschke
 */
public class WeraKatalog extends WeraProductCopy {

	/**
	 *
	 */
	private static final Logger LOG = Logger.getLogger(WeraKatalog.class.getName());

	private CatalogVersion m_oMasterCatalogVersion = null;
	private CatalogVersion m_oNewCatalogVersion = null;
	private Catalog m_oCatalogSystem = null;
	private ArrayList m_alErrorLogFile = null;
	private ArrayList m_alLogFile = null;
	private JaloSession m_oJaloSession = null;
	private String m_strLanguage = "de";
	private XmlSupport m_oXmlSupport = null;

	/**
	 *
	 */
	public WeraKatalog() {
		super();
		// TODO Auto-generated constructor stub

		// --- Arrays f�r logs eranlegen
		m_alErrorLogFile = new ArrayList();
		m_alLogFile = new ArrayList();

		// --- Initialisiere Masterkatalog
		m_oMasterCatalogVersion = getCatalogVersion(m_strCatalogMaster, Config.getParameter("wera.mastercatalogversion"));
		m_oCatalogSystem = getCatalog(m_strCatalogMaster);

		// --- Session
		m_oJaloSession = JaloSession.getCurrentSession();
	}

	/**
	 *
	 * @return
	 */
	public ArrayList getErrorLog() {
		return m_alErrorLogFile;
	}

	/**
	 *
	 * @return
	 */
	public ArrayList getLog() {
		return m_alLogFile;
	}

	// ----------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------
	/**
	 * Erstellen der Indexdatei
	 *
	 * @param request
	 * @param strKatalog
	 * @param strLanguage
	 * @return
	 */
	public String CreateIndex(final HttpServletRequest request, final String strKatalog, String strLanguage) {

		final Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();
		// --- DEBUG
		System.out.println("\n\nINDEXERSTELLUNG gestartet...");
		System.out.println("Sprache=" + strLanguage);
		System.out.println("Katalog=" + strKatalog);

		// --- Einlesen und parsen der Indesign-Datei
		final HashMap hashMasterCode = _readInputFile(request);

		// --- DEBUG
		System.out.println("Anzahl Artikel=" + hashMasterCode.keySet().size());

		// --- Initialize
		m_oXmlSupport = new XmlSupport();
		HashMap hashmapValues = null;
		Product oResultProduct = null;
		String strKey = "";
		final ArrayList collectionMasterProduct = new ArrayList();
		final HashMap hashmapMasterAlpha = new HashMap();
		m_alErrorLogFile.add("Fehlerreport");
		m_alLogFile.add("");
		m_alLogFile.add("Liste der indizierten Produkte");

		// --- Setzen der Sprache
		if (strLanguage == null) {
			strLanguage = m_strLanguage;
		}
		SetLanguage(strLanguage);

		// --- Schleife �ber alle Wera-CODE-Nummers (KEYS)
		System.out.println("Produkte werden zusammengestellt...");
		for (final Iterator it1 = hashMasterCode.keySet().iterator(); it1.hasNext();) {
			strKey = (String) it1.next();
			hashmapValues = (HashMap) hashMasterCode.get(strKey);

			// --- Suchen des zugh�rigen Produktes
			oResultProduct = (Product) hashmapValues.get("product");
			if (oResultProduct == null) {

				// --- Pordukt / Satz suchen
				oResultProduct = _SearchProduct(strKey, strKatalog);
				if (oResultProduct != null) {
					// --- Produkt merken
					_registerProdukt(oResultProduct, hashmapValues, hashMasterCode, collectionMasterProduct, hashmapMasterAlpha,
							strKatalog);
				}
			}

			// --- Error-LOG
			if (oResultProduct == null) {
				m_alErrorLogFile.add("Artikel " + strKey + " wurde nicht gefunden und nicht in den Index aufgenommen.");
				System.out.println("++Artikel " + strKey + " wurde nicht gefunden und nicht in den Index aufgenommen.");
			}

		}

		// --- Ausgabepfad generieren
		final String strResultPath = "index_" + strLanguage;
		final String strOutputPath = _CreateOutputPath(strLanguage, strKatalog);

		// --- Index nach CODE-Nummern erstellen
		System.out.println("CODE - Index wird geschrieben...");
		_createCodeIndex(hashMasterCode, strOutputPath);

		// --- Index Nummerisch erstellen
		System.out.println("Alphanummerischer - Index wird geschrieben...");
		_createNumIndex( collectionMasterProduct, strOutputPath, strLanguage );

		// --- Index Alphabetisch erstellen
		System.out.println("Stichwort - Index wird geschrieben...");
		_createAlphaIndex( hashmapMasterAlpha, strOutputPath );

		// --- Fehlerdatei schreiben	
		System.out.println("Report wird geschrieben...");
		m_alErrorLogFile.addAll(m_alLogFile);
		m_oXmlSupport._WriteFileFromArray(m_alErrorLogFile, strOutputPath + "/readme.txt");

		// --- DEBUG
		System.out.println("INDEXERSTELLUNG beendet...");

		SetLanguage ( currentSessionLanguage.getIsoCode() );
		return strResultPath;
	}

	/**
	 * Generieren des Ausgabepfades und anlegen der Verzeichnisse
	 *
	 * @param strLanguage
	 * @param strCatalogversion
	 * @return
	 */
	public String _CreateOutputPath(final String strLanguage, final String strCatalogversion) {

		final String strPath1 = Config.getParameter("wera.homepath") + "/export/katalog/" + strCatalogversion.toLowerCase();
		final String strPath2 = strPath1 + "/index_" + strLanguage;

		// --- Anlegen der Verzeichnisse
		WeraManager.getInstance().createDirectory(strPath1);
		WeraManager.getInstance().createDirectory(strPath2);

		return strPath2;
	}

	/**
	 *
	 * @param hashMasterCode
	 * @param strOutputPath
	 */
	private void _createCodeIndex(final HashMap hashMasterCode, final String strOutputPath) {

		// --- Initialize
		String strLine = "";
		HashMap hashmapValues = null;
		HashSet hashsetPages = null;
		final ArrayList aIndex = new ArrayList();
		String strPage = "";

		// --- Hole Liste der Code-Nummrs
		final ArrayList aListCode = new ArrayList();
		aListCode.addAll(hashMasterCode.keySet());

		// --- Sortieren der CODE-Nummerns
		Collections.sort(aListCode);

		// --- Schleife �ber alle Code-nummern
		String strCodeNr = "";
		for (final Iterator it1 = aListCode.iterator(); it1.hasNext();) {
			strCodeNr = (String) it1.next();
			hashmapValues = (HashMap) hashMasterCode.get(strCodeNr);
			hashsetPages = (HashSet) hashmapValues.get("pages");

			// --- Schleife �ber alle Seitenzahlen
			strLine = strCodeNr.substring(0, 2) + "\t" + strCodeNr.substring(2, 8) + "\t" + strCodeNr.substring(8) + "\t";
			strPage = _genPageStr(hashsetPages);
			strLine += strPage;
			strLine = strLine.replace("\r", "");
			aIndex.add(strLine);
		}

		// --- Datei schreiben	
		m_oXmlSupport._WriteFileFromArray(aIndex, strOutputPath + "/codeindex.txt");
	}

	/**
	 *
	 * @param hashsetPages
	 * @return
	 */
	private String _genPageStr(final HashSet hashsetPages) {

		// --- Initialize
		final IntComparator oIntComparator = new IntComparator();
		String strPages = "";
		Object[] aPages = null;

		aPages = hashsetPages.toArray();
		Arrays.sort(aPages, oIntComparator);
		for (int iPos = 0; iPos < aPages.length; iPos++) {
			final String strPage = aPages[iPos].toString();
			strPages += strPage + ", ";
		}
		strPages = strPages.substring(0, strPages.length() - 2);
		return strPages;

	}


	/**
	 *
	 * @param collectionMasterProduct
	 * @param strOutputPath
	 */
	private void _createNumIndex(final ArrayList collectionMasterProduct, final String strOutputPath, String currentLanguage ) {

		// --- Initialize
		final IntComparator oIntComparator = new IntComparator();
		final HashMapComparator oHashMapComparator = new HashMapComparator();
		String strLine = "";
		HashSet exportCodeNr	= new HashSet();
		HashMap hashmapValues = null;
		HashSet hashsetPages = null;
		Object[] aPages = null;
		final ArrayList aIndex = new ArrayList();
		String strPage = "";

		// --- Sortieren der Liste
		oHashMapComparator.SetSortType(true);
		Collections.sort(collectionMasterProduct, oHashMapComparator);

		// --- Schleife �ber alle Code-nummern
		String strCodeNr			= "";
		String strCodeNrLocalized	= "";
		for (final Iterator it1 = collectionMasterProduct.iterator(); it1.hasNext();) {

			hashmapValues = (HashMap) it1.next();

			// --- artikel nummer
			strCodeNr			= (String) hashmapValues.get("code");
			strCodeNrLocalized	= (String) hashmapValues.get("code_localized");
			
			hashsetPages = (HashSet) hashmapValues.get("pages");
			aPages = hashsetPages.toArray();
			Arrays.sort(aPages, oIntComparator);

			// --- Schleife �ber alle Seitenzahlen
			if ( strCodeNrLocalized != null && !strCodeNrLocalized.equals("") ) {
				
				// --- lokalisierte Artikelnummer
				strCodeNr			= strCodeNrLocalized;
			}
			
			// --- filter keine doppelten Artikel exportieren
			if ( exportCodeNr.contains(strCodeNr) ) {
				// --- Artikel ist bereits exportiert
				continue;
			}
			exportCodeNr.add(strCodeNr);
			
			strLine = currentLanguage + "\t" + strCodeNr + "\t";
			strPage = "";
			for (int iPos = 0; iPos < aPages.length; iPos++) {
				strPage = aPages[iPos].toString();
				strLine += strPage + ", ";
			}
			strLine = strLine.substring(0, strLine.length() - 2);
			strLine = strLine.replace("\r", "");
			aIndex.add(strLine);
		}

		// --- Datei schreiben	
		m_oXmlSupport._WriteFileFromArrayEncoding(aIndex, strOutputPath + "/" + currentLanguage + "_alphanumindex.txt", "UTF8");
	}

	/**
	 *
	 * @param hashmapMasterAlpha
	 * @param strOutputPath
	 */
	private void _createAlphaIndex(final HashMap hashmapMasterAlpha, final String strOutputPath) {

		// --- Initialize
		final IntComparator oIntComparator = new IntComparator();
		String strLine = "";
		HashMap hashmapValues = null;
		HashSet hashsetPages = null;
		Object[] aPages = null;
		String strKeyword = "";
		final ArrayList aIndex = new ArrayList();
		String strPage = "";

		// --- Hole Liste der Code-Nummrs
		final ArrayList aListCode = new ArrayList();
		aListCode.addAll(hashmapMasterAlpha.keySet());

		// --- Sortieren der CODE-Nummerns
		Collections.sort(aListCode);

		// --- Schleife �ber alle Code-nummern
		String strCodeNr = "";
		String strLastKeyword = "";
		for (final Iterator it1 = aListCode.iterator(); it1.hasNext();) {
			strCodeNr = (String) it1.next();
			hashmapValues = (HashMap) hashmapMasterAlpha.get(strCodeNr);
			hashsetPages = (HashSet) hashmapValues.get("pages");
			strKeyword = (String) hashmapValues.get("keyword");

			aPages = hashsetPages.toArray();
			Arrays.sort(aPages, oIntComparator);

			// --- nromalize and replace vorderen Kexword-Teil
			// strKeyword	= strKeyword.replaceAll( "##", "\t" );
			// --- 1. Teil des Keyword entfernen --------------------------------------
			// --- split keyword
			String[] aKeywords = strKeyword.split("##");

			// --- 1. Teil des Keyword entfernen
			String strKeywordNew = "";
			for (int iKeyword = 0; iKeyword < aKeywords.length; iKeyword++) {

				// --- hole aktuelles Keyword
				String strCurrentKeyword = aKeywords[iKeyword];

				// --- pr�fe ob der 1. Teil des keyword bereits vorkam
				if (iKeyword == 0) {

					if (strCurrentKeyword.equals(strLastKeyword)) {

						// --- keyword kam bereits vor also l�schen
						strCurrentKeyword = "";

					} else {
						// --- keyword merken
						strLastKeyword = strCurrentKeyword;
					}

				} // --- if ( iKeyword == 0 ) {

				if (strKeywordNew.isEmpty() && iKeyword == 0) {
					strKeywordNew = strCurrentKeyword;
				} else {
					strKeywordNew = strKeywordNew + "\t" + strCurrentKeyword;
				}

			}
			// --- 1. Teil des Keyword entfernen --------------------------------------

			// --- Schleife �ber alle Seitenzahlen
			if (aKeywords.length == 1) {
				strLine = strKeywordNew + "\t\t";
			} else {
				strLine = strKeywordNew + "\t";
			}
			strPage = "";
			for (int iPos = 0; iPos < aPages.length; iPos++) {
				strPage = aPages[iPos].toString();
				strLine += strPage + ", ";
			}
			strLine = strLine.substring(0, strLine.length() - 2);
			strLine = strLine.replace("\r", "");
			aIndex.add(strLine);
		}

		// --- Datei schreiben	
		m_oXmlSupport._WriteFileFromArrayEncoding(aIndex, strOutputPath + "/alphaindex.txt", "UTF8");
	}

	/**
	 *
	 * @param resultProduct
	 * @param hashmapValues
	 * @param hashMasterCode
	 * @param collectionMasterProduct
	 * @param hashmapMasterAlpha
	 * @param strKatalog
	 */
	private void _registerProdukt(final Product resultProduct, final HashMap hashmapValues, final HashMap hashMasterCode,
			final ArrayList collectionMasterProduct, final HashMap hashmapMasterAlpha, final String strKatalog) {
		// TODO Auto-generated method stub
		// --- Logdatei erstellen
		m_alLogFile.add(resultProduct.getCode());

		if (resultProduct instanceof WeraProductSet) {
			// --- register Satz
			hashmapValues.put("product", resultProduct);
		} else {
			// --- Optimized register of all variants / products relations
			// --- Initialize
			String strCode = "";
			final String strVariantenNr = "";
			HashMap hashmapValuesOpt = null;

			// --- Hole alle vorhanden Varianten des Produkts
			final Collection variants = (Collection) getAttribute(resultProduct, "variants");
			VariantProduct variantproduct = null;
			for (final Iterator it1 = variants.iterator(); it1.hasNext();) {
				variantproduct = (VariantProduct) it1.next();
				strCode = variantproduct.getCode();
				hashmapValuesOpt = (HashMap) hashMasterCode.get("05" + strCode + strVariantenNr);
				//hashmapValuesOpt = (HashMap) hashMasterCode.get( "05" + strCode + "001" );
				if (hashmapValuesOpt != null) {
					// --- found and register
					hashmapValuesOpt.put("product", resultProduct);
					hashMasterCode.put("05" + strCode + strVariantenNr, hashmapValuesOpt);
					//hashMasterCode.put( "05" + strCode + "001", hashmapValuesOpt );
				}
			}
		}

		// --- Speichern der Seiten-Nummern
		Collection category2productexts = (Collection) getAttribute(resultProduct, "category2productexts");
		Category2ProductExt category2productext = null;
		final String strCatalogVerion = "";
		if (category2productexts == null) {
			WeraManager.getInstance().createCategory2ProductExt((WeraProduct) resultProduct,
					Config.getParameter("wera.mastercatalogversion"));
			category2productexts = (Collection) getAttribute(resultProduct, "category2productexts");
		}
		if (category2productexts != null) {
			for (final Iterator it1 = category2productexts.iterator(); it1.hasNext();) {
				// --- Get value
				category2productext = (Category2ProductExt) it1.next();
				if (category2productext.getCatalogversion_desc() != null
						&& category2productext.getCatalogversion_desc().equals(strKatalog)) {
					try {
						HashSet hashsetPages = (HashSet) hashmapValues.get("pages");
						if (hashsetPages == null) {
							hashsetPages = new HashSet();
						}
						final String strPages = _genPageStr(hashsetPages);
						category2productext.setAttribute("pagenr_catalog", strPages);
					} catch (final Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}
			}
		}

		// --- Schleife �ber alle Schlagw�rter (AlphaIndex)
		final Collection keywords = (Collection) getAttribute(resultProduct, "keywords");
		Keyword keyworditem = null;
		String keyword = "";
		String[] aKeywords = null;
		for (final Iterator it1 = keywords.iterator(); it1.hasNext();) {
			// --- Get value
			keyworditem = (Keyword) it1.next();

			// --- Generate Entry
			keyword = (String) getAttribute(keyworditem, "keyword");
			if (keyword != null) {

				// --- nromalize
				keyword = keyword.trim();

				// --- split keyword and generate new keywords --------------------------------------------------------------
				aKeywords = keyword.split("/");
				String strGeneretedKeyword = "";
				ArrayList<String> aGeneretedKeywords = new ArrayList();
				for (int iKeyword = 0; iKeyword < aKeywords.length; iKeyword++) {
					if (strGeneretedKeyword.isEmpty()) {
						strGeneretedKeyword = aKeywords[iKeyword].trim();
					} else {
						strGeneretedKeyword = strGeneretedKeyword + "##" + aKeywords[iKeyword].trim();
					}
				}
				aGeneretedKeywords.add(strGeneretedKeyword);
				if (false && aKeywords.length > 1) {
					strGeneretedKeyword = "";
					// --- liste umdrehen
					for (int iKeyword = aKeywords.length - 1; iKeyword >= 0; iKeyword--) {
						if (strGeneretedKeyword.isEmpty()) {
							strGeneretedKeyword = aKeywords[iKeyword].trim();
						} else {
							strGeneretedKeyword = strGeneretedKeyword + "##" + aKeywords[iKeyword].trim();
						}
					}
					aGeneretedKeywords.add(strGeneretedKeyword);
				}
				// --- split keyword and generate new keywords --------------------------------------------------------------

				// --- alle keywords in liste �bernehmen --------------------------------------------------------------------
				for (String strKeyword : aGeneretedKeywords) {

					String strKeyHash = strKeyword.toUpperCase().replace(" ", "_");
					HashMap hashmapValuesTmp = (HashMap) hashmapMasterAlpha.get(strKeyHash);
					if (hashmapValuesTmp == null) {
						hashmapValuesTmp = new HashMap();
					}
					HashSet hashsetPagesTmp = (HashSet) hashmapValuesTmp.get("pages");
					if (hashsetPagesTmp == null) {
						hashsetPagesTmp = new HashSet();
					}
					hashsetPagesTmp.addAll((Collection) hashmapValues.get("pages"));
					hashmapValuesTmp.put("pages", hashsetPagesTmp);
					hashmapValuesTmp.put("keyword", strKeyword);
					hashmapMasterAlpha.put(strKeyHash, hashmapValuesTmp);

				} // --- for ( String strGeneretedKeyword : aGeneretedKeywords ) {

				// --- alle keywords in liste �bernehmen --------------------------------------------------------------------
			}
		}

		// --- Num-Index
		String strCode = resultProduct.getCode();
		hashmapValues.put("code", strCode);
		String strArtikelnrIndex = (String)getAttribute(resultProduct, "artikelnr_index");
		hashmapValues.put("code_localized", strArtikelnrIndex);
		strCode = ((WeraProduct) resultProduct).normalizeFilenameForImageLookup();
		strCode = strCode.replace("-", "_");
		final String[] aString = strCode.split("_");
		String strSortKey1 = aString[0].trim();
		hashmapValues.put("sortkey0", strSortKey1.substring(0, 1));
		if (strSortKey1.charAt(0) <= '9') {
			strSortKey1 = strSortKey1.replaceAll("[a-zA-Z]", "");
			strSortKey1 = "000000000000" + strSortKey1;
			hashmapValues.put("sortkey1", strSortKey1.substring(strSortKey1.length() - 10));
		} else {
			hashmapValues.put("sortkey1", strSortKey1.toLowerCase());
		}
		String strSortKey2 = "";
		for (int iPos = 1; iPos < aString.length; iPos++) {
			strSortKey2 += aString[iPos].trim();
		}
		hashmapValues.put("sortkey2", strSortKey2.toLowerCase());
		collectionMasterProduct.add(hashmapValues);
	}

	/**
	 * Einlesen und parsen der Indesign-Datei
	 *
	 * @param request
	 * @return
	 */
	private HashMap _readInputFile(final HttpServletRequest request) {

		// --- Initialize
		final HashMap hashInput = new HashMap();
		HashMap hashmapValues = null;
		HashSet hashsetPages = null;

		try {

			// --- Einlesen der Datei
			final int lengthOfBuffer = request.getContentLength();
			System.out.println("_readInputFile.lengthOfBuffer " + lengthOfBuffer);
			final BufferedInputStream in = new BufferedInputStream(request.getInputStream());
			final int chunkSize = 32768;
			final byte[] chunk = new byte[chunkSize];
			final ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int size = -1;
			while ((size = in.read(chunk, 0, chunkSize)) > 0) {
				baos.write(chunk, 0, size);
			}

			// --- Parsen der Index-Zeilen
			String strKey = "";
			String strValue = "";
			final String[] aList = baos.toString().split("\n");
			for (int iPos = 0; iPos < aList.length; iPos++) {

				// --- Parse Parameter Spracge
				if (aList[iPos].toString().indexOf("language") >= 0) {
					iPos += 2;
					m_strLanguage = aList[iPos].toString().trim();
				}

				final String[] aLine = aList[iPos].split("\t");
				if (aLine != null && aLine.length == 2) {

					strKey = aLine[0];
					strValue = aLine[1];
					if (strValue != null && !strValue.trim().equals("")) {

						// --- Hashes aktulisieren
						if (hashInput.containsKey(strKey)) {
							hashmapValues = (HashMap) hashInput.get(strKey);
							hashsetPages = (HashSet) hashmapValues.get("pages");
						} else {
							hashmapValues = new HashMap();
							hashsetPages = new HashSet();
						}
						hashsetPages.add(new Integer(strValue.replace("\r", "")));

						// --- Wert merken
						hashmapValues.put("pages", hashsetPages);
						hashmapValues.put("product", null);
						hashInput.put(strKey, hashmapValues);

					} // --- if ( strValue != null && !strValue.trim().equals("") ) {

				} // --- if (aLine != null && aLine.length == 2) {

			} // --- for (int iPos = 0; iPos < aList.length; iPos++) {

		} catch (final Exception e) {
			e.printStackTrace();
		}

		return hashInput;
	}

	/**
	 *
	 * @param searchresult
	 * @param strKatalog
	 * @return
	 */
	private Product _CheckKatalogVersion(final SearchResult searchresult, final String strKatalog) {

		// --- Initialize
		Product oResultProduct = null;
		Product oBaseProduct = null;
		String strCsvVersion = "";
		CatalogVersion cv = null;

		// --- Schleife �ber alle Produkte
		// und pr�fen auf Katalogversion
		for (final Iterator itResult2 = searchresult.getResult().iterator(); itResult2.hasNext();) {
			oBaseProduct = (Product) itResult2.next();
			cv = (CatalogVersion) getAttribute(oBaseProduct, "catalogVersion");
			strCsvVersion = cv.getVersion();

			// --- Aus Katalogversion pr�fen (WERAMASTER=Fallback)
			if (strCsvVersion.equals(Config.getParameter("wera.mastercatalogversion"))) {
				oResultProduct = oBaseProduct;
			}
			if (strCsvVersion.equals(strKatalog)) {
				oResultProduct = oBaseProduct;
				break;
			}
		}

		return oResultProduct;
	}

	/**
	 *
	 * @param strKey
	 * @param strKatalog
	 * @return
	 */
	private Product _SearchProduct(final String strKey, final String strKatalog) {

		// --- Suchen der Variante
		String strSearch = "select {pk} from {VariantProduct} WHERE code='" + strKey.substring(2, 8) + "'";
		//		SearchResult res = serachItem(strSearch, Collections
		//				.singletonList(VariantProduct.class), -1);
		final List aResult = JaloSession.getCurrentSession().getFlexibleSearch()
				.search(strSearch, null, Collections.singletonList(VariantProduct.class), true, // fail on unknown fields
						true, // don't need total
						0, 1 // range
				).getResult();
		Product oResultProduct = null;
		Product oBaseProduct = null;
		final String strCsvVersion = "";
		final ClassificationSystemVersion csv = null;
		if (aResult != null && aResult.isEmpty() == false) {
			VariantProduct oVariantProductDest = null;
			// --- Schleife �ber alle Varianten
			for (final Iterator itResult1 = aResult.iterator(); itResult1.hasNext();) {
				oVariantProductDest = (VariantProduct) itResult1.next();

				// --- Holen des Basisprodukts
				oBaseProduct = oVariantProductDest.getBaseProduct();
				if (oBaseProduct != null) {

					// --- Holen aller Produkte mit gleicher CODE-Nummer
					// und pr�fen auf Katalogversion
					strSearch = "select {pk} from {WeraProduct} WHERE code='" + oBaseProduct.getCode() + "'";
					final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
							.search(strSearch, null, Collections.singletonList(Product.class), true, // fail on unknown fields
									true, // don't need total
									0, 1 // range
							);

					// --- Schleife �ber alle Produkte
					//     und pr�fen auf Katalogversion
					oResultProduct = _CheckKatalogVersion(res, strKatalog);
				}
			}

		} else {
			strSearch = "select {pk} from {WeraProductSet} WHERE {artnr[de]}='" + strKey.substring(2, 8) + "'";
			final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch()
					.search(strSearch, null, Collections.singletonList(WeraProductSet.class), true, // fail on unknown fields
							true, // don't need total
							0, 1 // range
					);

			// --- Schleife �ber alle Produkte
			//     und pr�fen auf Katalogversion
			oResultProduct = _CheckKatalogVersion(res, strKatalog);

		}

		return oResultProduct;
	}

	// ----------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------
	/**
	 * Erstellen einer Kopie aller Produkte die sich in der katalogversion
	 * befinden, Anschlie�end werden die Produkte auf RO gesetzt.
	 *
	 * @param strDestCatalogName
	 * @return
	 */
	public boolean archiveCatalog(final String strDestCatalogName) {

		// --- Initialize
		Collection colProducts = null;
		final Collection colAllProducts = new ArrayList();

		// --- Hole die aktuelle Katalogversion
		m_oNewCatalogVersion = getCSV("weracatalog", strDestCatalogName);

		// --- Hole alle Categories der Katalogversion
		final Collection colCategories = m_oNewCatalogVersion.getAllCategories();

		// --- Schleife �ber alle Categorien und sammle alle Produkte
		Category oAktCategory = null;
		for (final Iterator itCategories = colCategories.iterator(); itCategories.hasNext();) {
			// --- Hole Category
			oAktCategory = (Category) itCategories.next();

			// --- Hole alle Produkte der Kategory
			colProducts = oAktCategory.getProducts();
			if (colProducts != null) {
				colAllProducts.addAll(colProducts);
			}
		}

		// --- Schleife �ber alle Produkte
		WeraProduct oAktWeraProduct = null;
		WeraProduct oNewWeraProduct = null;
		for (final Iterator itProducts = colAllProducts.iterator(); itProducts.hasNext();) {
			// --- Hole Produkt
			oAktWeraProduct = (WeraProduct) itProducts.next();
			System.out.println("===>START <===Clone Produkt strSrcProdukt=" + oAktWeraProduct.getCode());

			// --- Kopieren eines kompletten Produktes inkl. aller Artikel und pi, pa, po
			oNewWeraProduct = (WeraProduct) cloneProduct(oAktWeraProduct, oAktWeraProduct.getCode());

			// --- Katalogversion entfernen die nicht zum Produkt geh�rt
			RemoveCatalogVersionFromCategories(oAktWeraProduct, m_oNewCatalogVersion);
			RemoveCatalogVersionFromCategories(oNewWeraProduct, m_oMasterCatalogVersion);

			// --- Steuerparameter des Produkts anpassen
			createCategory2ProductExt(oAktWeraProduct, Config.getParameter("wera.mastercatalogversion"));
			createCategory2ProductExt(oNewWeraProduct, Config.getParameter("wera.mastercatalogversion"));

			System.out.println("++++READY");
		}

		// --- Katalog als archiviert kennzeichnen
		setAttribute(m_oNewCatalogVersion, "archiviert", new Boolean(true));

		return true;
	}

	/**
	 *
	 * @param oWeraProduct
	 * @param newCatalogVersion
	 */
	private void RemoveCatalogVersionFromCategories(final WeraProduct oWeraProduct, final CatalogVersion newCatalogVersion) {
		// TODO Auto-generated method stub

		// --- Hole alle Categories f�r das Product
		final Collection colCategories = (Collection) getAttribute(oWeraProduct, "supercategories");
		final Collection colNewCategories = new ArrayList();

		// --- Schleife �ber alle Kategorien
		for (final Iterator it1 = colCategories.iterator(); it1.hasNext();) {
			// --- Hole Kategorie
			final Category oCategory = (Category) it1.next();

			// --- Handelt sich um die Katalogversion dann herrausfoltern
			if (!getAttribute(oCategory, "catalogVersion").equals(newCatalogVersion)) {
				colNewCategories.add(oCategory);
			}

		} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();)

		// --- Kategorien wieder zuweisen
		setAttribute(oWeraProduct, "supercategories", colNewCategories);
	}

	/**
	 * Kopieren eines kompletten Produktes inkl. aller Artikel und pi, pa, po
	 *
	 * @param productSrc
	 * @param strDestProduct
	 * @return
	 */
	public Product cloneProduct(final Product productSrc, final String strDestProduct) {
		// --- Iniitialize
		String strResult = "";
		final SessionContext sessioncontext = JaloSession.getCurrentSession().getSessionContext();
		final ProductManager pm = ProductManager.getInstance();
		final WeraManager wm = WeraManager.getInstance();
		final Collection products = new ArrayList();
		WeraProduct productDest = null;
		Set descriptorsProduct = null;
		final String strSrcProdukt = productSrc.getCode();

		// --- Hole das Orginal-Produkt
		if (productSrc != null) {

			// --- Produkt anlegen
			final Map mapAttributes = new HashMap();
			mapAttributes.put("code", strDestProduct);
			mapAttributes.put("catalogVersion", m_oNewCatalogVersion);
			mapAttributes.put("masterpk", productSrc.getPK().toString());
			if (productSrc instanceof WeraProductSet) {
				productDest = wm.createWeraProductSet(mapAttributes);
				// --- Felder initialisieren (Produkt)
				descriptorsProduct = TypeManager.getInstance().getComposedType(WeraProductSet.class).getAttributeDescriptors();
			} else {
				productDest = wm.createWeraProduct(mapAttributes);
				// --- Felder initialisieren (Produkt)
				descriptorsProduct = TypeManager.getInstance().getComposedType(WeraProduct.class).getAttributeDescriptors();
			}
			if (descriptorsProduct == null) {
				strResult = "++++ERROR+++ cloneProduct.descriptorsProduct=" + descriptorsProduct;
				System.out.println(strResult);
				return productDest;
			}
			if (productDest == null) {
				strResult = "++++ERROR+++ cloneProduct.strDestProduct=" + strDestProduct;
				System.out.println(strResult);
				return productDest;
			}

			// --- Hole alle Categories f�r das Product
			final Collection colCategories = (Collection) getAttribute(productSrc, "supercategories");
			final Collection colNewCategories = new ArrayList();
			// --- Schleife �ber alle Kategorien
			for (final Iterator it1 = colCategories.iterator(); it1.hasNext();) {
				// --- Hole Kategorie
				final Category oCategory = (Category) it1.next();

				// --- Hole Katalogversion
				final ClassificationSystemVersion csv = (ClassificationSystemVersion) getAttribute(oCategory, "catalogVersion");
				if (csv.equals(m_proficlassCatalogVersion) || csv.equals(m_oNewCatalogVersion)) {
					// --- Nur Proficlass und unsere neue Katalogversion mitkopieren
					colNewCategories.add(oCategory);
				}

			} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();)
			setAttribute(productDest, "supercategories", colNewCategories);

			// --- �bertragung der Elemente
			_cloneItems(descriptorsProduct, productSrc, productDest);

			// --- Setzen einzelner Werte
			setAttribute(productDest, "catalogVersion", m_oNewCatalogVersion);

			// --- Felder initialisieren (Variante)
			final ComposedType cpWV = TypeManager.getInstance().getComposedType("WeraVariante");
			final Set descriptorsVariante = cpWV.getAttributeDescriptors();

			// --- Hole alle Artikel f�r das Product -------------------------------------------- START
			if ((productSrc instanceof WeraProductSet) == false) {

				final Collection colVariants = (Collection) getAttribute(productSrc, "variants");
				final Collection colNewVariants = new ArrayList();
				VariantProduct oVariante = null;
				for (final Iterator it2 = colVariants.iterator(); it2.hasNext();) {
					// --- Hole Name
					final Product oVariant = (Product) it2.next();
					//if ( colSollArtikel.contains(oVariant.getCode()))
					if (true) {
						// --- Kopieren eines Artikels
						//System.out.println ( "===>START <===Clone Artikel. CODE=" + oVariant.getCode() );
						oVariante = _cloneArtikel(productDest, descriptorsVariante, oVariant.getPK().toString(), oVariant.getCode(), "");
						colNewVariants.add(oVariante);
						//System.out.println ( "===>ENDE <===Clone Artikel. CODE=" + oVariant.getCode() );
					}
				}
				setAttribute(productDest, "variants", colNewVariants);
			}
			//else
			//System.out.println("WERAPRODUCTSET => Skipping cloneArtikels");
			// --- Hole alle Artikel f�r das Product -------------------------------------------- ENDE

		} else {
			strResult = "++++ERROR+++ cloneProduct.productSrc=null";
			System.out.println(strResult);
		}

		// --- Setzt das neue Produkt
		setClonedProduct(productDest);

		// --- Fertig
		//System.out.println ( "===>ENDE <===Clone Produkt="  );
		return productDest;
	}

	/**
	 * Kopieren eines Artikels
	 *
	 * @param oBaseProdukt
	 * @param descriptors
	 * @param strSrcProduktPK
	 * @param strDestProduct
	 * @param strTargCategory
	 * @return
	 */
	private VariantProduct _cloneArtikel(final Product oBaseProdukt, final Set descriptors, final String strSrcProduktPK,
			final String strDestProduct, final String strTargCategory) {
		// --- Initialize
		final SessionContext sessioncontext = JaloSession.getCurrentSession().getSessionContext();
		final ProductManager pm = ProductManager.getInstance();
		final WeraManager wm = WeraManager.getInstance();
		final Collection products = new ArrayList();
		VariantProduct oVariantProductSrc = null;
		VariantProduct oVariantProductDest = null;

		final String strSearch = "select {pk} from {VariantProduct} WHERE pk='" + strSrcProduktPK + "'";
		final SearchResult res = serachItem(strSearch, Collections.singletonList(VariantProduct.class));
		if (res.getCount() > 0) {
			// --- Hole die Original-Variante
			oVariantProductSrc = (VariantProduct) res.getResult().get(0);
			if (oVariantProductSrc == null) {
				System.out.println("oVariantProductSrc not found=" + strSrcProduktPK);
				return null;
			}

			// --- Artikel / Variante anlegen
			final Map mapAttributes = new HashMap();
			mapAttributes.put("code", strDestProduct);
			mapAttributes.put("baseProduct", oBaseProdukt);
			mapAttributes.put("catalogVersion", m_oNewCatalogVersion);
			oVariantProductDest = wm.createWeraProductVariante(mapAttributes);
			if (oVariantProductDest == null) {
				System.out.println("oVariantProductDest-Error=" + strDestProduct);
				return null;
			}

			// --- Hole alle Categories f�r die Variante
			final Collection colCategories = (Collection) getAttribute(oVariantProductSrc, "supercategories");
			final Collection colNewCategories = new ArrayList();
			// --- Schleife �ber alle Kategorien
			for (final Iterator it1 = colCategories.iterator(); it1.hasNext();) {
				// --- Hole Kategorie
				final Category oCategory = (Category) it1.next();

				// --- Handelt sich um eine Wera Category, dann l�schen
				if (oCategory.getCode().substring(0, 2).equals("AA") || oCategory.getCode().substring(0, 1).equals("_")) {
					colNewCategories.add(oCategory);
				}

			} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();)

			// --- Hole Wera Category
			//Category categoryWeraClass = m_weraCatalogVersion.getCategory(strTargCategory);
			//colNewCategories.add(categoryWeraClass);
			setAttribute(oVariantProductDest, "supercategories", colNewCategories);

			// --- �bertragung der Elemente
			_cloneItems(descriptors, oVariantProductSrc, oVariantProductDest);

			// --- Setzen einzelner Werte
			setAttribute(oVariantProductDest, "catalogVersion", m_oNewCatalogVersion);
		}

		return oVariantProductDest;
	}

	/**
	 * Hole Produkt / Category Relationen und gebe diese als HashMap zur�ck
	 *
	 * @param strCatalogName
	 * @return
	 */
	public HashMap getCatalogContent(final String strCatalogName) {
		return _getCatalogContent(strCatalogName, "", true);
	}

	/**
	 * Hole Produkt / Category Relationen und gebe diese als HashMap zur�ck
	 *
	 * @param strCatalogName
	 * @param strAction
	 * @param bSourceIsMasterKatalog
	 * @return
	 */
	public HashMap _getCatalogContent(final String strCatalogName, final String strAction, final boolean bSourceIsMasterKatalog) {

		// --- Debug
		//System.out.println("++getCatalogContent=" + strCatalogName);
		// --- Initialize
		final HashMap hashCategorie2Product = new HashMap();
		final Collection colCategories2Delete = new ArrayList();
		HashMap hashEntry = null;
		Collection colProducts = null;
		Collection colSubCatergories = null;
		Category oAktCategory = null;
		Product oAktProduct = null;
		String strCategoryPK = "";

		try {

			// --- Pr�fe, ob Katalogversion bereits vorhanden ist
			m_oNewCatalogVersion = getCSV("weracatalog", strCatalogName);
			if (m_oNewCatalogVersion != null) {

				// --- Hole alle Categories der Katalogversion
				final Collection colCategories = m_oNewCatalogVersion.getAllCategories();

				// --- Schleife �ber alle Categorien und sammle alle Produkte
				for (final Iterator itCategories = colCategories.iterator(); itCategories.hasNext();) {

					// --- Hole Category und Master-PK
					oAktCategory = (Category) itCategories.next();
					if (bSourceIsMasterKatalog) {
						strCategoryPK = (String) getAttribute(oAktCategory, "masterpk");
					} else {
						strCategoryPK = null;
					}
					if (strCategoryPK == null) {
						strCategoryPK = oAktCategory.getPK().toString();
					}

					// --- Hole alle Produkte der Kategory
					colProducts = oAktCategory.getProducts();

					// --- Hole alle Unterkategorien
					colSubCatergories = oAktCategory.getSubcategories();

					// --- �bernehme alle Kategorie
					if ((colSubCatergories != null && colSubCatergories.size() > 0) || (colProducts != null && colProducts.size() > 0)) {

						// --- �bernehme die Kategory
						hashEntry = new HashMap();
						hashEntry.put("category", strCategoryPK);
						hashEntry.put("product", "all");
						if (strAction.length() > 0) {
							hashEntry.put("action", strAction);
						}
						hashCategorie2Product.put(strCategoryPK + "|all", hashEntry);

						// --- �bernehme alle Produkte, falls vorhanden
						for (final Iterator itProducts = colProducts.iterator(); itProducts.hasNext();) {
							// --- Hole Produkt
							oAktProduct = (Product) itProducts.next();
							if (oAktProduct instanceof WeraProduct) {

								// --- Hole MasterPK (Produkt)
								String strProductPK = null;
								if (bSourceIsMasterKatalog) {
									strProductPK = (String) getAttribute(oAktProduct, "masterpk");
								}
								if (strProductPK == null) {
									strProductPK = oAktProduct.getPK().toString();
								}

								// --- �bernehme Produkt / Category Relation
								hashEntry = new HashMap();
								hashEntry.put("category", strCategoryPK);
								hashEntry.put("product", strProductPK);
								if (strAction.length() > 0) {
									hashEntry.put("action", strAction);
								}
								hashCategorie2Product.put(strCategoryPK + "|" + strProductPK, hashEntry);
							}

						} // --- for ( Iterator itProducts =

					} else {
						System.out.println("add2delete Category=" + oAktCategory.getCode());
						colCategories2Delete.add(oAktCategory);
					}

				} // --- for ( Iterator itCategories =

				// --- Schleife �ber alle Kategorien die gel�scht werden k�nnen
				if (colCategories2Delete.size() > 0) {

					for (final Iterator itCategories2Delete = colCategories2Delete.iterator(); itCategories2Delete.hasNext();) {
						oAktCategory = (Category) itCategories2Delete.next();
						try {
							// --- L�sche Kategory
							if (oAktCategory != null) {
								oAktCategory.remove();
							}

						} catch (final ConsistencyCheckException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} // --- if ( colCategories2Delete.size() > 0 ) {

			} // --- if ( m_oNewCatalogVersion != null ) {

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return hashCategorie2Product;
	}

	/**
	 * Hole alle Produkte einer bestimmten Kataloges / KatalogVesion
	 * (Preisliste)
	 *
	 * @param strCatalogVersionName
	 * @param strReferenzKatalogVersion
	 * @param iSorted
	 * @return
	 */
	public Collection getProductsFromPreisliste(final String strCatalogVersionName, final String strReferenzKatalogVersion,
			final int iSorted) {

		// --- Initialize
		m_strCatalogMaster = Config.getParameter("wera.mastercatalog");
		m_strCatalogPrint = "print";
		m_strCatalogPriceliste = "preisliste";

		return _getProductsFromCatalogVersion(strCatalogVersionName, strReferenzKatalogVersion, iSorted, false, false);
	}

	/**
	 * Hole alle Produkte einer bestimmten Kataloges / KatalogVesion
	 * Katalog inkl. doppelter
	 *
	 * @param strCatalogVersionName
	 * @param strReferenzKatalogVersion
	 * @param strCatalogPrint
	 * @param strCatalogPriceliste
	 * @param iSorted
	 * @return
	 */
	public Collection getProductsFromPreisliste_v4(final String strCatalogVersionName, final String strReferenzKatalogVersion,
												   final String strCatalogPrint, final String strCatalogPriceliste, final int iSorted) {

		// --- Initialize
		m_strCatalogMaster = Config.getParameter("wera.mastercatalog");
		m_strCatalogPrint = strCatalogPrint;
		m_strCatalogPriceliste = strCatalogPriceliste;

		return _getProductsFromCatalogVersion(strCatalogVersionName, strReferenzKatalogVersion, iSorted, false, true);
	}

	/**
	 * Hole alle Produkte einer bestimmten Kataloges / KatalogVesion
	 * (Preisliste)
	 *
	 * @param strCatalogVersionName
	 * @param strReferenzKatalogVersion
	 * @param strCatalogPrint
	 * @param strCatalogPriceliste
	 * @param iSorted
	 * @return
	 */
	public Collection getProductsFromPreisliste_v3(final String strCatalogVersionName, final String strReferenzKatalogVersion,
			final String strCatalogPrint, final String strCatalogPriceliste, final int iSorted) {

		// --- Initialize
		m_strCatalogMaster = Config.getParameter("wera.mastercatalog");
		m_strCatalogPrint = strCatalogPrint;
		m_strCatalogPriceliste = strCatalogPriceliste;

		return _getProductsFromCatalogVersion(strCatalogVersionName, strReferenzKatalogVersion, iSorted, false, false);
	}

	/**
	 * Hole alle Produkte einer bestimmten Kataloges / KatalogVesion
	 * (Preisliste)
	 *
	 * @param strCatalogPriceliste
	 * @param strCatalogVersionName
	 * @param strReferenzKatalogVersion
	 * @param iSorted
	 * @return
	 */
	public Collection getProductsFromPreisliste_v2(final String strCatalogPriceliste, final String strCatalogVersionName, final String strReferenzKatalogVersion,
			final int iSorted) {

		// --- Initialize
		m_strCatalogMaster = Config.getParameter("wera.mastercatalog");
		m_strCatalogPrint = "print";
		m_strCatalogPriceliste = strCatalogPriceliste;

		return _getProductsFromCatalogVersion(strCatalogVersionName, strReferenzKatalogVersion, iSorted, false, false);
	}

	/**
	 * Hole alle Produkte einer bestimmten Kataloges / KatalogVesion
	 *
	 * @param strCatalog
	 * @param strCatalogVersion
	 * @param iSorted
	 * @return
	 */
	public Collection getProductsFromCatalogVersion(final String strCatalog, final String strCatalogVersion, final int iSorted) {

		// --- Initialize
		m_strCatalogMaster = Config.getParameter("wera.mastercatalog");
		m_strCatalogPrint = strCatalog;

		return _getProductsFromCatalogVersion(strCatalogVersion, null, iSorted, false, false);
	}

	/**
	 * Hole alle Produkte einer bestimmten Kataloges / KatalogVesion optional
	 * inkl. der SB-Varianen
	 *
	 * @param strCatalog
	 * @param strCatalogVersion
	 * @param iSorted
	 * @param bExportSBVariants
	 * @return
	 */
	public Collection getProductsFromCatalogVersion(final String strCatalog, final String strCatalogVersion, final int iSorted, boolean bExportSBVariants) {

		// --- Initialize
		m_strCatalogMaster = Config.getParameter("wera.mastercatalog");
		m_strCatalogPrint = strCatalog;

		return _getProductsFromCatalogVersion(strCatalogVersion, null, iSorted, bExportSBVariants, false );
	}

	/**
	 * Hole alle Produkte des Masterkatalogs
	 *
	 * @param iSorted
	 * @return
	 */
	public Collection getProductsFromMasterCatalog(final int iSorted) {

		// --- Initialize
		m_strCatalogMaster = Config.getParameter("wera.mastercatalog");
		m_strCatalogPrint = Config.getParameter("wera.mastercatalog");

		return _getProductsFromCatalogVersion("weramaster", null, iSorted, false, false);
	}

	/**
	 * Gebe alle Produkte einer katalogversion HashMap zur�ck
	 *
	 * @param strCatalogVersionName -> Auslesen aller Produkte dieser
	 * KatalogVersion
	 * @param strReferenzKatalogVersion -> null / optional=Holen der
	 * Seitenzahlen aus einem 2. Katalog (Katalog / Preisliste)
	 * @param iSorted -> 0=nosort/ 1=SortByCode/ 2=Sort By First Order/ 3=Sort By Order current Category
	 * @param bExportSBVariants -> true=exportiere inkl. SB-Varianten,
	 * false=ignoriere SB-Varianten
	 * @param allowDoubleProducts -> exportiere Produkte doppelt
	 * @return
	 */
	public Collection _getProductsFromCatalogVersion(final String strCatalogVersionName, final String strReferenzKatalogVersion,
			final int iSorted, boolean bExportSBVariants, boolean allowDoubleProducts) {
		// --- Debug
		LOG.info("++WeraKatalog");
		LOG.info("strCatalogVersionName=" + strCatalogVersionName);
		LOG.info("strReferenzKatalogVersion=" + strReferenzKatalogVersion);
		LOG.info("m_strCatalogMaster=" + m_strCatalogMaster);
		LOG.info("m_strCatalogPrint=" + m_strCatalogPrint);
		LOG.info("m_strCatalogPriceliste=" + m_strCatalogPriceliste);
		LOG.info("iSorted=" + iSorted);
		LOG.info("bExportSBVariants=" + bExportSBVariants);

		// --- Initialize
		CatalogVersion oReferenzCatalogVersion = null;
		final HashSet hashsetPK = new HashSet();
		HashMap hashmapValues = new HashMap();
		final ArrayList collectionMasterProduct = new ArrayList();
		final HashMap hashEntry = null;
		Collection colProducts = null;
		Collection colSubCatergories = null;
		Category oAktCategory = null;
		Product oAktProduct = null;
		String strCategoryPK = "";

		try {

			// --- Pr�fe, ob Katalogversion bereits vorhanden ist
			if (strReferenzKatalogVersion != null) {
				oReferenzCatalogVersion = getCatalogVersion(m_strCatalogPrint, strReferenzKatalogVersion);
				m_oNewCatalogVersion = getCatalogVersion(m_strCatalogPriceliste, strCatalogVersionName);
			} else {
				m_oNewCatalogVersion = getCatalogVersion(m_strCatalogPrint, strCatalogVersionName);
			}
			if (m_oNewCatalogVersion != null) {

				// --- Hole alle Categories der Katalogversion
				final Collection colCategories = m_oNewCatalogVersion.getAllCategories();

				// --- Schleife �ber alle Categorien und sammle alle Produkte
				for (final Iterator itCategories = colCategories.iterator(); itCategories.hasNext();) {

					// --- Hole Category und Master-PK
					oAktCategory = (Category) itCategories.next();
					strCategoryPK = (String) getAttribute(oAktCategory, "masterpk");
					if (strCategoryPK == null) {
						strCategoryPK = oAktCategory.getPK().toString();
					}

					// --- Hole alle Produkte der Kategory
					colProducts = oAktCategory.getProducts();

					// --- Hole alle Unterkategorien
					colSubCatergories = oAktCategory.getSubcategories();

					// --- �bernehme alle Kategorie
					if ((colSubCatergories != null && colSubCatergories.size() > 0) || (colProducts != null && colProducts.size() > 0)) {

						// --- �bernehme alle Produkte, falls vorhanden
						for (final Iterator itProducts = colProducts.iterator(); itProducts.hasNext();) {
							// --- Hole Produkt
							oAktProduct = (Product) itProducts.next();
							//System.out.println("oAktProduct.getCode()=" + oAktProduct.getCode());

							// --- prüfe ob der produkt exportiert werden darg
							if ( getVisibilityForCatalog( oAktProduct, m_oNewCatalogVersion, false ) == -1 ) {

								System.out.println(oAktProduct.getCode() + ", skipped, product not allowd for current catalogversion");
								continue;
							}

							// --- Pr�fe, ob das Produkt bereits vorhanden ist
							if (allowDoubleProducts == false && hashsetPK.contains(oAktProduct.getPK())) {
								System.out.println(oAktProduct.getCode() + ", skipped, no double product allowed");
								continue;
							}

							// --- prüfe ob das produkt exportiert werden darf
							if (oAktProduct instanceof WeraProduct) {
								// --- Produkt merken
								hashsetPK.add(oAktProduct.getPK());
								hashmapValues = new HashMap();
								hashmapValues.put("product", oAktProduct);
								String strCode = oAktProduct.getCode();
								hashmapValues.put("code", strCode);

								// --- Kategory merken
								hashmapValues.put("category", oAktCategory);

								// --- Sortierung initialisieren
								String strOrder = "";
								Integer iOrder = null;
								Collection category2productexts	= null;
								switch ( iSorted ) {
									default:
									case 0:
										break;

									case 1:// --- Sort By Code
										strCode = ((WeraProduct) oAktProduct).normalizeFilenameForImageLookup();
										// LOG.info("product (" + oAktProduct.getCode() + ") normalized name = >" + strCode + "<");
										strCode = strCode.replace("-", "_");
										final String[] aString = strCode.split("_");
										String strSortKey1 = aString[0].trim();
										if (strSortKey1.length() > 0 && strSortKey1.charAt(0) <= '9') {
											strSortKey1 = strSortKey1.replaceAll("[a-zA-Z]", "");
											strSortKey1 = "000000000000" + strSortKey1;
											hashmapValues.put("sortkey0", strSortKey1.substring(strSortKey1.length() - 10));
										} else {
											hashmapValues.put("sortkey0", "/" + strSortKey1.toLowerCase());
										}
										String strSortKey2 = "";
										for (int iPos = 1; iPos < aString.length; iPos++) {
											strSortKey2 += aString[iPos].trim();
										}
										hashmapValues.put("sortkey1", strSortKey2.toLowerCase());
										// System.out.println("Sort By Code, Result Order =>" + strSortKey2 );
										break;

									case 2:// --- Sort By first Order
										iOrder = null;
										category2productexts = ((WeraProduct) oAktProduct)
												.getCategory2ProductextsByCatalogVersion(strCatalogVersionName);
										if (category2productexts != null && category2productexts.size() > 0) {
											final Category2ProductExt category2productext = (Category2ProductExt) category2productexts
													.iterator().next();
											strOrder = category2productext.getPriority();
											if (strOrder == null || strOrder.equals("")) {
												iOrder = new Integer(0);
											} else {
												iOrder = new Integer(strOrder);
											}
										} else {
											final Double dOrder = (Double) getAttribute(oAktProduct, "priority");
											if (dOrder == null) {
												iOrder = new Integer(0);
											} else {
												iOrder = new Integer(dOrder.intValue());
											}
										}
										if (iOrder == null) {
											iOrder = new Integer(0);
										}
										// System.out.println("Sort By first Order, Result Order =>" + iOrder);
										hashmapValues.put("sortkey0", iOrder);
										break;

									case 3:// --- Sort By  Order current category

										iOrder = null;
										category2productexts = ((WeraProduct) oAktProduct)
												.getCategory2ProductextsByCatalogVersion(strCatalogVersionName);
										if (category2productexts != null && category2productexts.size() > 0) {
											for( Category2ProductExt category2productext: (Collection<Category2ProductExt>) category2productexts ) {
												Category tmpCategory	= (Category) getAttribute(category2productext,"category");
												// System.out.println("tmpCategory.getCode()=" +  tmpCategory.getCode() + ", oAktCategory.getCode()=" + oAktCategory.getCode() );
												if ( tmpCategory != null && tmpCategory.getCode().equals(oAktCategory.getCode()) ) {

													strOrder = category2productext.getPriority();
													if (strOrder == null || strOrder.equals("")) {
														iOrder = new Integer(0);
													} else {
														iOrder = new Integer(strOrder);
													}
													break;
												}
											}

										} else {
											final Double dOrder = (Double) getAttribute(oAktProduct, "priority");
											if (dOrder == null) {
												iOrder = new Integer(0);
											} else {
												iOrder = new Integer(dOrder.intValue());
											}
										}
										if (iOrder == null) {
											iOrder = new Integer(0);
										}
										//System.out.println("Sort By  Order current category, Result Order =>" + iOrder);
										hashmapValues.put("sortkey0", iOrder);
										break;
								} // switch ( iSorted ) {

								// --- Hole Seitenzahlen
								// strReferenzKatalogVersion
								// --- Hole alle Kategorien
								String strPages = "";
								if (oReferenzCatalogVersion != null) {
									category2productexts = ((WeraProduct) oAktProduct)
											.getCategory2ProductextsByCatalogVersion(strReferenzKatalogVersion);
									if (category2productexts != null && category2productexts.size() > 0) {
										final Category2ProductExt category2productext = (Category2ProductExt) category2productexts
												.iterator().next();
										strPages = category2productext.getPagenr_catalog();
										if (strPages == null || strPages == "") {
											strPages = "-";
										}
									}
								}
								hashmapValues.put("pages", strPages);

								// --- Product speichern
								collectionMasterProduct.add(hashmapValues);
							}

						} // --- for ( Iterator itProducts =

					}

				} // --- for ( Iterator itCategories =

				// --- Sortieren der Ergebnisse
				if (iSorted != 0) {
					final HashMapComparator oHashMapComparator = new HashMapComparator();
					if (iSorted == 1) {
						// --- Sort By Code
						oHashMapComparator.SetSortType(true);
					}
					if (iSorted == 2) {
						// --- Sort By Nummerisch
						oHashMapComparator.SetSortType(false);
					}
					Collections.sort(collectionMasterProduct, oHashMapComparator);
				}

			} // --- if ( m_oNewCatalogVersion != null ) {

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- SB-Varianten anf�gen -----------------------------------------------------------------------
		if (bExportSBVariants) {

			// --- preset tmp-array
			ArrayList<HashMap> productsTmp = new ArrayList();
			productsTmp.clear();
			productsTmp.addAll(collectionMasterProduct);
			collectionMasterProduct.clear();

			// --- iterate on all collected produtcs
			for (HashMap oWeraProductHashMap : productsTmp) {

				// --- hole aktuelles WeraProduct
				WeraProduct oWeraProduct = (WeraProduct) oWeraProductHashMap.get("product");

				// --- weraproduct wieder in resultset einf�gen
				collectionMasterProduct.add(oWeraProductHashMap);

				// --- hole alle SB-Varianten
				Collection<WeraProductSetinSet> colSBVariants = WeraProduct.getAllSB_Variants(oWeraProduct);
				for (WeraProductSetinSet oWeraSBVariant : colSBVariants) {

					// --- get cloned hash
					HashMap hashSBVariant = (HashMap) oWeraProductHashMap.clone();

					// --- prüfe ob der produkt exportiert werden darg
					if ( getVisibilityForCatalog( oWeraSBVariant, m_oNewCatalogVersion, false ) == -1 ) {

						System.out.println(oWeraSBVariant.getCode() + ", skipped, product not allowd for current catalogversion");
						continue;
					}

					// --- tausche die SB-Variante aus
					hashSBVariant.put("product", oWeraSBVariant);

					// --- sb-variante anf�gen
					collectionMasterProduct.add(hashSBVariant);
				}

			}

		} // --- if { bExportSBVariants ) {

		// --- SB-Varianten anf�gen -----------------------------------------------------------------------
		return collectionMasterProduct;
	}

	/**
	 * Erstellt eine neue Katalogversion inkl. Kopien der Kategorien und
	 * Verlinkungen zu den jeweiligen Produkten als Vorlage dient eine bereits
	 * vorhandene Katalogversion
	 *
	 * @param strSrcCatalogName
	 * @param strDestCatalogName
	 * @return
	 */
	public boolean CopyLinkedCatalog(final String strSrcCatalogName, final String strDestCatalogName) {

		// --- Debug
		System.out.println("==>CopyLinkedCatalog(" + strSrcCatalogName + "," + strDestCatalogName + ")");

		// --- Hole den Inhalt des Kataloges
		final HashMap hashCategorie2Product = _getCatalogContent(strSrcCatalogName, "addone", false);

		// --- Erzeuge eine Kopie
		final boolean bResult = GenerateLinkedCatalog(strSrcCatalogName, strDestCatalogName, hashCategorie2Product);
		if (bResult) {
			// --- Hole alle Produkte (unsortiert)
			final Collection products = getProductsFromCatalogVersion("print", strDestCatalogName, 0, false);

			// --- F�r alle Produkte die Reihefolge des Eingabekataloges �bernehmen
			for (final Iterator it1 = products.iterator(); it1.hasNext();) {

				WeraProduct weraproduct = null;
				HashMap hashmap = null;

				hashmap = (HashMap) it1.next();
				if (hashmap != null) {
					weraproduct = (WeraProduct) hashmap.get("product");
				}

				// --- Steuerparameter anlegen aus Referenzkatalog
				if (weraproduct != null) {
					createCategory2ProductExt(weraproduct, strSrcCatalogName);
				}
			}
		}

		return bResult;
	}

	/**
	 * Erstellt eine neue Katalogversion inkl. Kopien der Kategorien und
	 * Verlinkungen zu den jeweiligen Produkten
	 *
	 * @param strSrcCatalogName
	 * @param strDestCatalogName
	 * @param hashCategorie2Product
	 * @return
	 */
	public boolean GenerateLinkedCatalog(final String strSrcCatalogName, final String strDestCatalogName,
			final HashMap hashCategorie2Product) {

		// --- secure
		if (hashCategorie2Product == null) {
			return false;
		}

		// --- Initialize
		HashMap hashCategory2Product = null;
		String strScrCategoryPK = "";
		String strSrcProductPK = "";
		String strAction = "";
		Category oNewCategory = null;
		Category oSrcCategoryItem = null;

		// --- Katalog holen oder erstellen falls noch nicht vorhanden
		m_oNewCatalogVersion = CreateNewCatalogVersion(strDestCatalogName);

		// --- Katalog vorhanden, dann weiter
		if (m_oNewCatalogVersion != null) {
			// --- Schleife �ber alle Categorie / Produkt Relations
			for (final Iterator itC2P = hashCategorie2Product.keySet().iterator(); itC2P.hasNext();) {

				// --- Hole den Kategorie - Key (PK)
				final String strKey = (String) itC2P.next();
				hashCategory2Product = (HashMap) hashCategorie2Product.get(strKey);
				strScrCategoryPK = (String) hashCategory2Product.get("category");
				strSrcProductPK = (String) hashCategory2Product.get("product");
				strAction = (String) hashCategory2Product.get("action");
				System.out.println("==>GenerateLinkedCatalog key=" + strKey + ", catPK=" + strScrCategoryPK + ", ProdPK="
						+ strSrcProductPK + ", action=" + strAction);

				// --- Hole die SrcCategory
				oSrcCategoryItem = (Category) m_oJaloSession.getItem(strScrCategoryPK);

				// --- Erstelle Category als Inhalt der neuen Katalogversion
				oNewCategory = CreateNewCategoryTree(oSrcCategoryItem, null);

				// --- Verlinke die Produkte in die neuen Kategorien
				if (strAction.equals("addone") || strAction.equals("addall")) {
					LinkProduct2Category(oNewCategory, oSrcCategoryItem, strSrcProductPK);
				} else {
					UnLinkProduct2Category(oNewCategory, oSrcCategoryItem, strSrcProductPK);
				}
			}
		}

		return true;
	}

	/**
	 *
	 * @param strPkCategory
	 * @param strAction
	 * @param hashResult
	 * @return
	 */
	public HashMap GenerateActionMap(final String strPkCategory, final String strAction, HashMap hashResult) {

		// --- Initialialize
		if (hashResult == null) {
			hashResult = new HashMap();
		}
		Category oRootCategory = null;
		Category oAktCategory = null;
		String strAktPK = "";

		// --- Hole die aktuelle Kategorie
		oRootCategory = (Category) JaloSession.getCurrentSession().getItem(strPkCategory);
		if (oRootCategory != null) {

			// --- Schleife �ber Unterkategorien
			final Collection colSubCategories = oRootCategory.getAllSubcategories();
			for (final Iterator itSubCategories = colSubCategories.iterator(); itSubCategories.hasNext();) {

				// --- Hole Kategory
				oAktCategory = (Category) itSubCategories.next();

				// --- Erzeuge Hash
				final HashMap hashEntry = new HashMap();
				strAktPK = oAktCategory.getPK().toString();
				hashEntry.put("category", strAktPK);
				hashEntry.put("product", "all");
				hashEntry.put("action", strAction);
				hashResult.put(strAktPK + "|all", hashEntry);
			}
		}

		// --- Eintrag f�r "Root-Category" erzeugen
		final HashMap hashEntry = new HashMap();
		strAktPK = oRootCategory.getPK().toString();
		hashEntry.put("category", strAktPK);
		hashEntry.put("product", "all");
		hashEntry.put("action", strAction);
		hashResult.put(strAktPK + "|all", hashEntry);

		return hashResult;
	}

	/**
	 *
	 * @param oDestNewCategory
	 * @param oSrcCategoryItem
	 * @param strSrcProductPK
	 * @return
	 */
	private Category LinkProduct2Category(final Category oDestNewCategory, final Category oSrcCategoryItem,
			final String strSrcProductPK) {
		// TODO Auto-generated method stub

		// --- Initialize
		WeraProduct oWeraProduct = null;
		final List<Product> colNewProductList = new ArrayList<Product>();

		// --- Hole die Original WeraProdukt
		if (!strSrcProductPK.equals("all")) {

			// --- Hole das einzelne Produkt
			oWeraProduct = (WeraProduct) JaloSession.getCurrentSession().getItem(strSrcProductPK);

			// --- Pr�fe, ob sich das Produkt bereits in der Kategory befindet
			final Collection colProducts = oDestNewCategory.getProducts();
			if (oWeraProduct != null && !colProducts.contains(oWeraProduct)) {
				// --- Produktliste �bernehmen
				colNewProductList.addAll(colProducts);
				colNewProductList.add(oWeraProduct);
				oDestNewCategory.setProducts(colNewProductList);
			}
		} else {
			// --- Alle Produkte der Eingabekategorie �bernehmen
			final Collection colProducts = oSrcCategoryItem.getProducts();
			colNewProductList.addAll(colProducts);
			oDestNewCategory.setProducts(colNewProductList);
		}

		return null;
	}

	/**
	 *
	 * @param oDestNewCategory
	 * @param oSrcCategoryItem
	 * @param strSrcProductPK
	 * @return
	 */
	private Category UnLinkProduct2Category(final Category oDestNewCategory, final Category oSrcCategoryItem,
			final String strSrcProductPK) {
		// TODO Auto-generated method stub

		// --- Initialize
		System.out.println("UnLinkProduct2Category.strProductPK=" + strSrcProductPK);
		WeraProduct oWeraProduct = null;
		final List<Product> colNewProductList = new ArrayList<Product>();

		// --- Hole die Original WeraProdukt
		if (!strSrcProductPK.equals("all")) {

			// --- Hole das einzelne Produkt
			oWeraProduct = (WeraProduct) JaloSession.getCurrentSession().getItem(strSrcProductPK);

			// --- Pr�fe, ob sich das Produkt bereits in der Kategory befindet
			final Collection colProducts = oDestNewCategory.getProducts();
			if (oWeraProduct != null && colProducts.contains(oWeraProduct)) {
				// --- Produktliste �bernehmen
				colNewProductList.addAll(colProducts);
				colNewProductList.remove(oWeraProduct);
				oDestNewCategory.setProducts(colNewProductList);
			}
		} else {
			// --- Alle Produkte der Eingabekategorie entfernen
			final Collection colProducts = oDestNewCategory.getProducts();
			colNewProductList.clear();
			oDestNewCategory.setProducts(colNewProductList);
			try {
				oDestNewCategory.remove();
			} catch (final ConsistencyCheckException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return null;
	}

	/**
	 * Erstelle eine neue Category als Kopie einer vorhandenen Funktion erstellt
	 * rekursiv den Tree nach oben falls nicht vorhanden
	 *
	 * @param oSrcCategoryItem
	 * @param oDestCategorySubItem
	 * @return
	 */
	private Category CreateNewCategoryTree(final Category oSrcCategoryItem, final Category oDestCategorySubItem) {
		// TODO Auto-generated method stub

		final Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();
		
		// --- Initialize
		Category oNewCategory = null;

		// --- Pr�fe, ob die Kategory bereits existiert.
		oNewCategory = m_oNewCatalogVersion.getCategory(oSrcCategoryItem.getCode());
		if (oNewCategory == null) {

			// --- Hole CatelogType
			final ComposedType CategoryType = JaloSession.getCurrentSession().getTypeManager().getComposedType(Category.class);

			// --- Erzeugen der neue Category
			try {
				String strMasterPK = (String) oSrcCategoryItem.getAttribute("masterpk");
				if (strMasterPK == null || strMasterPK.length() == 0) {
					strMasterPK = oSrcCategoryItem.getPK().toString();
				}
				final Map parameters = new HashMap();
				parameters.put("code", oSrcCategoryItem.getCode());
				parameters.put("catalogVersion", m_oNewCatalogVersion);
				parameters.put("masterpk", strMasterPK);
				parameters.put("concat_description", oSrcCategoryItem.getAttribute("concat_description"));
				oNewCategory = (Category) CategoryType.newInstance(parameters);

				// --- �bernehme CA-Liste
				final Collection classificationattributes = (Collection) getAttribute(oSrcCategoryItem, "classificationAttributes");
				if (classificationattributes != null && classificationattributes.size() > 0) {
					final Collection newCA = new ArrayList();
					newCA.addAll(classificationattributes);
					setAttribute(oNewCategory, "classificationAttributes", newCA);
				}

				oNewCategory.setAllName(oSrcCategoryItem.getAllName());
				for (final Iterator itLanguages = C2LManager.getInstance().getAllLanguages().iterator(); itLanguages.hasNext();) {
					// --- Sprache aktivieren
					final Language lang = (Language) itLanguages.next();
					SetLanguage(lang.getIsoCode());

					oNewCategory.setAttribute("aktiv", oSrcCategoryItem.getAttribute("aktiv"));
					oNewCategory.setAttribute("order", oSrcCategoryItem.getAttribute("order"));
					oNewCategory.setAttribute("name", oSrcCategoryItem.getAttribute("name"));
					oNewCategory.setAttribute("untertitel", oSrcCategoryItem.getAttribute("untertitel"));
				}
				SetLanguage( currentSessionLanguage.getIsoCode() );

			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // --- if ( oNewCategory != null ) {

		if (oNewCategory != null) {

			// --- Pr�fe, ob die SubCategory bereits eingeh�ngt wurden
			final Collection colDestSubCategories = oNewCategory.getSubcategories();
			if (oDestCategorySubItem != null && !colDestSubCategories.contains(oDestCategorySubItem)) {
				//Collection colSubCategories = new ArrayList();
				final List<Category> colSubCategories = new ArrayList<Category>();
				colSubCategories.addAll(colDestSubCategories);
				colSubCategories.add(oDestCategorySubItem);
				oNewCategory.setSubcategories(colSubCategories);
			}

			// --- Hole die SuperCategories 
			List<Category> colSubCategories = null;
			Category oSuperDestCategoryItem = null;
			final Collection colDestSuperCategories = oNewCategory.getSupercategories();
			final Collection colSrcSuperCategories = oSrcCategoryItem.getSupercategories();
			for (final Iterator it1 = colSrcSuperCategories.iterator(); it1.hasNext();) {
				final Category oSuperSrcCategoryItem = (Category) it1.next();

				// --- Pr�fe, die die SuperCategory bereits existiert
				oSuperDestCategoryItem = m_oNewCatalogVersion.getCategory(oSrcCategoryItem.getCode());
				if (oSuperDestCategoryItem != null) {
					oSuperDestCategoryItem = CreateNewCategoryTree(oSuperSrcCategoryItem, oNewCategory);
				}

				// --- Pr�fe, ob die Supercategory bereits eingeh�ngt  ist
				if (oSuperDestCategoryItem != null && !colDestSuperCategories.contains(oSuperDestCategoryItem)) {
					//colSubCategories = new ArrayList();
					colSubCategories = new ArrayList<Category>();
					colSubCategories.addAll(colDestSuperCategories);
					colSubCategories.add(oSuperDestCategoryItem);
					oNewCategory.setSupercategories(colSubCategories);
				}

			}

		} // --- if ( oNewCategory != null ) {

		return oNewCategory;
	}

	/**
	 * Holen oder anlegen einer neuen Katalogversion
	 *
	 * @param strDestCatalogName
	 * @return
	 */
	public CatalogVersion CreateNewCatalogVersion(final String strDestCatalogName) {

		// --- Pr�fe, ob Katalogversion bereits vorhanden ist
		m_oNewCatalogVersion = getCatalogVersion("weracatalog", strDestCatalogName);
		if (m_oNewCatalogVersion != null) {
			return m_oNewCatalogVersion;
		}

		// --- Initialize
		final ComposedType CatalogVersionType = JaloSession.getCurrentSession().getTypeManager()
				.getComposedType(CatalogVersion.class);

		// TODO Auto-generated method stub
		try {

			final Map parameters = new HashMap();
			parameters.put("version", strDestCatalogName);
			parameters.put("catalog", m_oCatalogSystem);
			m_oNewCatalogVersion = (CatalogVersion) CatalogVersionType.newInstance(parameters);

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return m_oNewCatalogVersion;
	}

}

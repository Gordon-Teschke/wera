package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeUnit;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.catalog.model.classification.ClassAttributeAssignmentModel;
import de.hybris.platform.catalog.model.classification.ClassificationAttributeValueModel;
import de.hybris.platform.catalog.model.classification.ClassificationClassModel;
import de.hybris.platform.catalog.model.classification.ClassificationSystemVersionModel;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.category.jalo.CategoryManager;
import de.hybris.platform.category.model.CategoryModel;
import de.hybris.platform.classification.ClassificationService;
import de.hybris.platform.classification.features.Feature;
import de.hybris.platform.classification.features.FeatureValue;
import de.hybris.platform.core.Registry;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.servicelayer.model.ModelService;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.jalo.VariantProduct;
import de.hybris.platform.variants.model.VariantProductModel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jdom.Comment;
import org.jdom.Element;

import com.computationaldesign.wera.model.WeraProductModel;
import com.computationaldesign.wera.model.WeraProductSetModel;
import com.computationaldesign.wera.model.WeraVarianteModel;

public class DataExport extends WeraManager {

	private final static Logger LOG = Logger.getLogger(DataExport.class.getName());

	// --- Kistenpfennig
	public String m_strResultFile = "";
	String m_strOutputFile = "";
	String m_strOutputRoot = "";
	String m_strImageTransferPath = "";
	String m_strDatenTransferPath = "";
	ArrayList m_aArrayLog = null;
	String m_strDatum = "";
	private final String m_CSV_SEP = "\t";
	HashMap m_hashImages = null;
	Product m_product = null;
	Product m_article = null;
	private String m_strDelim = "'";
	private String m_strSeparater = ";";
	private CatalogVersion m_weraCatalogVersion = null;
	private ClassificationSystemVersion m_proficlassCatalogVersion = null;
	private String m_strOutputPath = "";
	WeraClassificationHelper m_weraclassificationhelper = null;

	// --- WeraManager
	WeraManager m_wm = WeraManager.getInstance();

	// --- Europarts
	private BMEcatXml m_oBMEcatXml = null;
	private HashMap m_HeaderMap = null;
	private HashMap m_ValueMap = null;

	private ModelService m_modelService = null;

	private ClassificationService m_classificationService = null;

	/**
	 * 
	 */
	public DataExport() {
		super();
		// TODO Auto-generated constructor stub

		// --- Unser neuer Classifiactioan Helper
		m_weraclassificationhelper = new WeraClassificationHelper();
		this.m_modelService = (ModelService) Registry.getGlobalApplicationContext().getBean("modelService");
		this.m_classificationService = (ClassificationService) Registry.getGlobalApplicationContext().getBean(
				"defaultClassificationService");
	}

	/**
	 * Datenexport Templates fï¿½r Kategorie Mapping
	 * 
	 * @param strLang
	 * @param strPath 
	 */
	public void generateKategorieMappingTemplate(final String strLang, final String strPath) {

		// --- Initialze
		final String strDelim = "\t";
		final Set setCC = new HashSet();
		String strCC_Code = "";
		String strCC_Name = "";
		final String strCode = "";
		final Collection colArticles = new ArrayList();
		Collection colCategories = new ArrayList();
		String strLine = "";
		String strAktKlasse = "";
		WeraProduct product = null;
		final Object object = null;
		ClassificationClass classificationclass = null;
		ArrayList aCSV_de = new ArrayList();

		// --- Datenausgabe
		final MediandoXml oSupport = new MediandoXml();

		// --- Setze Sprache und hole Daten
		SetLanguage(strLang);

		// --- Hole alle aktiven Produkte, sortiert
		m_weraCatalogVersion = getCatalogVersion("weracatalog", "weramaster");
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "3.0");
		final Collection products = WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion, true, "code");

		// --- Schleife ï¿½ber alle Ergebnisse
		System.out.println("Anzahl Produkte:" + products.size());
		for (final Iterator it1 = products.iterator(); it1.hasNext();) {

			// --- Hole Produkt
			product = (WeraProduct) it1.next();
			if (product instanceof WeraProduct) {

				try {
					// --- Klassifizierende - Kategorien sammeln
					colCategories = _getAllCategoriesByProduct(product);

					// --- Schleife ï¿½ber alle Categorien
					for (final Iterator itCategories = colCategories.iterator(); itCategories.hasNext();) {
						// --- Hole Category
						classificationclass = (ClassificationClass) itCategories.next();

						// --- Hole Version
						final String strVersion = classificationclass.getSystemVersion().getFullVersionName();

						// --- Jede Klasse nur 1x ausgeben
						if (!setCC.contains(classificationclass)) {

							// --- Klasse merken
							setCC.add(classificationclass);

							// --- Hole Code und Beschreibung 
							strCC_Code = (String) classificationclass.getAttribute("code");
							strCC_Name = (String) classificationclass.getLocalizedProperty("name");
							strAktKlasse = strVersion;
							strAktKlasse += "\t" + strCC_Code;
							strAktKlasse += "\t" + strCC_Name;

							// ---- Stopper -----------------------------------
							//if ( !strCC_Code.equals("AAA818c001") )
							//continue;
							// ---- Stopper -----------------------------------
							// --- Header setzen
							aCSV_de.clear();
							aCSV_de
									.add("ClassificationVersion_Src	KlasseID_Src	BezeichnungKlasse_Src	MerkmalID_Src	BezeichnungMerkmal_Src	WertID_Src	Wert_Src	Einheit_Src	ClassificationVersion_Dst	KlasseID_Dst	BezeichnungKlasse_Dst	MerkmalID_Dst	BezeichnungMerkmal_Dst	WertID_Dst	Wert_Dst	Einheit_Dst	Pflicht_Dst");

							// --- Hole alle Assignments einer Klasse
							final Collection<ClassAttributeAssignment> colCaa = classificationclass
									.getDeclaredClassificationAttributeAssignments();

							// --- Schleifer ï¿½ber alle Merkmale
							for (final Iterator it_assignment = colCaa.iterator(); it_assignment.hasNext();) {

								// --- Hole das Assignment
								final ClassAttributeAssignment classattributeassignment = (ClassAttributeAssignment) it_assignment.next();
								final ClassificationAttribute classificationattribute = classattributeassignment
										.getClassificationAttribute();
								final EnumerationValue ev_caa = classattributeassignment.getAttributeType();

								// --- Hole die mï¿½glichen Werte eines ClassAttributeAssignment 
								final HashMap<String, ClassificationAttributeValue> classificationattributevaluesResult = m_weraclassificationhelper
										.getClassificationAttributeValues(classattributeassignment);

								// --- Schleife ï¿½ber alle ClassficatioAttributeValues
								if (ev_caa.getCode().equals("boolean")) {

									// --- Zeile zusammenbauen
									strLine = strAktKlasse + "\t" + classattributeassignment.getClassificationAttribute().getCode();
									strLine = strLine + "\t" + classattributeassignment.getClassificationAttribute().getName();

									// --- JA / NEIN
									strLine = strLine + "\t";
									strLine = strLine + "\tja / nein";

									// --- Zeilen zur Ausgabe vorbereiten
									aCSV_de.add(strLine);
								} else {
									for (final Iterator it_value = classificationattributevaluesResult.keySet().iterator(); it_value
											.hasNext();) {

										// --- Zeile zusammenbauen
										strLine = strAktKlasse + "\t" + classattributeassignment.getClassificationAttribute().getCode();
										strLine = strLine + "\t" + classattributeassignment.getClassificationAttribute().getName();

										// --- Werte anhï¿½ngen
										final String strValueID = (String) it_value.next();
										final ClassificationAttributeValue cav = classificationattributevaluesResult.get(strValueID);
										strLine = strLine + "\t" + cav.getCode();
										strLine = strLine + "\t" + cav.getName();

										final ClassificationAttributeUnit oUnit = classattributeassignment.getUnit();
										if (oUnit != null) {
											strLine = strLine + "\t" + oUnit.getSymbol();
										} else {
											strLine = strLine + "\t";
										}

										// --- Zeilen zur Ausgabe vorbereiten
										aCSV_de.add(strLine);
									}
								}

								// --- Schleife ï¿½ber alle CustomValues
							}

							// --- Daten schreiben (falls vorhanden)
							if (aCSV_de.size() > 1) {
								final String strFileName = strCC_Code.trim().replace("/", "_").replace("\"", "_")
										+ "_"
										+ strCC_Name.trim().replace("/", "_").replace(",", "").replace(".", "").replace("\"", "_")
												.replace(" ", "_");
								// oSupport._WriteFileFromArrayEncoding (aCSV_de, strPath + "/" + strVersion + "/" + strFileName + ".txt", "UTF-8");
								oSupport._WriteFileFromArrayEncoding(aCSV_de, strPath + "/" + strVersion + "/" + strFileName + ".txt",
										"ISO-8859-1");
							}

						}

					} // --- for ( Iterator itCategories = colCategories.iterator(); itCategories.hasNext(); ) {

				} catch (final JaloInvalidParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final JaloSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			} // --- if ( product instanceof WeraProduct ) {

		} // --- for ( Iterator it1 = products.iterator(); it1.hasNext(); ) {

		aCSV_de = null;
	}

	/**
	 * 
	 * @param product
	 * @return 
	 */
	private Collection _getAllCategoriesByProduct(final WeraProduct product) {
		//	private Collection _getAllCategoriesByProduct(WeraProduct product, ClassificationSystemVersion oSourceCatalogVersion) {
		// TODO Auto-generated method stub

		// --- Initialize 
		//Collection colCategories = new ArrayList();
		//Collection colResult     = new ArrayList();
		//Category category = null;
		//ClassificationSystemVersion catalogVersion = null
		// --- Hole alle Classizierenden Categorien
		final List<ClassificationClass> colResult = m_weraclassificationhelper.getAllClassificationClassByProduct(product);

		return colResult;
	}

	/**
	 *
	 * @param WeraProduct product
	 * @return Collection
	 */
	private Collection _getAllArticleByProduct(final WeraProduct product) {

		// --- Initialize 
		boolean bDone = false;
		final Collection colArticles = new ArrayList();
		WeraVariante weravariant = null;
		HashMap hashmap = new HashMap();

		try {
			if ((product instanceof WeraProductSetinSet)) {
				Boolean isDisplay = (Boolean) getAttribute(product, "ist_display");
				if (isDisplay != null && isDisplay.booleanValue()) {

					// --- Display wie einen Satz behandlen
					// --- Prüfe ob der Artikel aktiv ist
					final Boolean bAktiv = (Boolean) product.getAttribute("aktiv");
					if (bAktiv != null && bAktiv.booleanValue()) {
						hashmap = new HashMap();
						hashmap.put("code", (String) product.getAttribute("lagerNr") + (String) product.getAttribute("artnr")
								+ (String) product.getLocalizedProperty("variantenNr"));
						hashmap.put("pk", product.getAttribute("pk").toString());
						hashmap.put("weravariant", product);
						colArticles.add(hashmap);
					}

				} else {

					// --- Artikelliste eines Produktes
					Collection articles = null;
					articles = (Collection) m_wm.getAttribute(product, "weraproductsetvariants_qual");

					// --- Schleife ï¿½ber alle Artikel
					WeraProductSetVariants oWeraProductSetVariants = null;
					WeraProductSet oWeraProductset = null;
					for (final Iterator it2 = articles.iterator(); it2.hasNext();) {

						// --- Hole den aktuellen Artikel
						oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
						oWeraProductset = (WeraProductSet) m_wm.getAttribute(oWeraProductSetVariants, "weraproductsets");

						// --- Fï¿½lle einen Artikel
						if (oWeraProductset != null) {

							// --- Prï¿½fe ob der Artikel aktiv ist
							final Boolean bAktiv = (Boolean) oWeraProductset.getAttribute("aktiv");
							if (bAktiv != null && bAktiv.booleanValue()) {
								hashmap = new HashMap();
								hashmap.put("code",
										(String) oWeraProductset.getAttribute("lagerNr") + (String) oWeraProductset.getAttribute("artnr")
										+ (String) oWeraProductset.getLocalizedProperty("variantenNr"));
								hashmap.put("pk", oWeraProductset.getAttribute("pk").toString());
								hashmap.put("weravariant", oWeraProductset);
								colArticles.add(hashmap);
							}
						}
					}

				}

				// --- we got it
				bDone = true;

			} // --- if ( (product instanceof WeraProductSetinSet) ) {

			if (!bDone && (product instanceof WeraProductSet)) {
				// --- Prï¿½fe ob der Artikel aktiv ist
				final Boolean bAktiv = (Boolean) product.getAttribute("aktiv");
				if (bAktiv != null && bAktiv.booleanValue()) {
					hashmap = new HashMap();
					hashmap.put("code", (String) product.getAttribute("lagerNr") + (String) product.getAttribute("artnr")
							+ (String) product.getLocalizedProperty("variantenNr"));
					hashmap.put("pk", product.getAttribute("pk").toString());
					hashmap.put("weravariant", product);
					colArticles.add(hashmap);
				}

				// --- we got it
				bDone = true;

			} // --- if ( (product instanceof WeraProductSet) ) {

			if (!bDone && (product instanceof WeraProduct)) {
				final Collection colVariants = (Collection) product.getAttribute("variants");
				if (colVariants != null) {
					for (final Iterator itArticel = colVariants.iterator(); itArticel.hasNext();) {
						weravariant = (WeraVariante) itArticel.next();
						// --- Prï¿½fe ob der Artikel aktiv ist
						final Boolean bAktiv = (Boolean) weravariant.getAttribute("aktiv");
						if (bAktiv != null && bAktiv.booleanValue()) {
							hashmap = new HashMap();
							hashmap.put("code", (String) weravariant.getAttribute("lagerNr") + (String) weravariant.getAttribute("code")
									+ (String) weravariant.getLocalizedProperty("variantenNr"));
							hashmap.put("pk", weravariant.getAttribute("pk").toString());
							hashmap.put("artikel", weravariant);
							colArticles.add(hashmap);
						}
					}
				}

				// --- we got it
				bDone = true;

			} // --- if ( (product instanceof WeraProduct) ) {

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return colArticles;
	}

	/**
	 * Ausgabe einer CSV-Datei aller Produkte / Artikel mit Preisen
	 * (Kistenpfennig)
	 *
	 * @param String strCatalog
	 * @param String strCatalogVersion
	 * @return String
	 */
	public String strJspExportKistenpfenningCSV(final String strCatalog, final String strCatalogVersion) {

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final CatalogVersion weraCatalogVersion = getCatalogVersion(strCatalog, strCatalogVersion);

		return strExportKistenpfenningCSV(weraCatalogVersion, strCatalogVersion);
	}

	/**
	 * (Kistenpfennig)
	 *
	 * @param CatalogVersion weraCatalogVersion
	 * @param String strCatalogVersion
	 * @return String
	 */
	public String strExportKistenpfenningCSV(final CatalogVersion weraCatalogVersion, final String strCatalogVersion) {

		// --- Initialize
		final XmlSupport oXmlSupport = new XmlSupport();
		int iProductCounter = 0;
		final String strFN = "strExportKistenpfenningCSV ==> ";
		String strResult = "";
		String strOutput = "";
		String strLine = "";
		Collection productsSorted = null;
		Collection articles = null;
		final String strPreisInfo = "";
		final String strBesch = "";
		String strCode = "";
		String strEAN = "";
		String strPreis = "";
		String strProduktBild = "";
		final Integer oPageNr = null;
		final Boolean bNeu = null;
		WeraMedia oWeraMedia = null;
		m_hashImages = new HashMap();
		ArrayList prices = null;
		m_aArrayLog = new ArrayList();

		// --- Meldung
		System.out.println("Starte Datenexport Kistenpfennig.....");

		// --- Ausgabedatum ermitteln
		m_strDatum = m_wm.InitOutputDatum();

		// --- Ausgabedatei
		m_strOutputRoot = Config.getParameter("wera.exportpath") + "/katalog/" + strCatalogVersion + "/";
		m_strOutputPath = "kistenpfennig_" + m_strDatum;
		m_strOutputFile = "wera" + "_de_" + m_strDatum;

		// --- Pfad nalegen falls noch nicht vorhanden
		createDirectory(m_strOutputRoot);
		createDirectory(m_strOutputRoot + "/" + m_strOutputPath);

		// --- Auagabepfade fï¿½r XML-Daten anlegen
		m_strImageTransferPath = "wera_de_" + m_strDatum + "_daten";
		createDirectory(m_strOutputRoot + "/" + m_strOutputPath + "/" + m_strImageTransferPath);

		try {
			// --- ï¿½ffnen der Ausgabedatei
			final String strOutputFile = m_strOutputRoot + "/" + m_strOutputPath + "/" + m_strImageTransferPath
					+ "/wera_fuer_dia.csv";
			m_strResultFile = strOutputFile;
			final FileWriter fw = new FileWriter(strOutputFile);

			// --- Schreibe ï¿½berschrift
			strLine = "\"Hersteller" + "\";\"Herstellerartikelnummer" + "\";\"Artikelbezeichnung" + "\";\"Mengeneinheit"
					+ "\";\"Preis" + "\";\"Gueltigkeitsdatum" + "\";\"Waehrung" + "\";\"Preiseinheit" + "\";\"Gewicht"
					+ "\";\"Gewichtseinheit" + "\";\"Mindestmenge" + "\";\"Verfï¿½gbar" + "\";\"EAN-Nummer" + "\";\"Verpackungseinheit"
					+ "\";\"Zollnummer" + "\";\"Bild 1" + "\";\"Interne Herstellerartikel-Identnummer" + "\";\"Artikelfamilie\"\r\n";
			fw.write(strLine);

			// --- Alle alle Produkte sortiert nach Produktnummer
			strOutput = "Hole sortierte Produktliste ...";
			System.out.println(strFN + strOutput);
			strResult += strOutput + "<br>";
			productsSorted = WeraProduct.getAllProductsFromCatalog(weraCatalogVersion, true, "code");

			// ---- Initialize Sichtbarkeit (Attribute)
			final EnumerationManager em = JaloSession.getCurrentSession().getEnumerationManager();
			EnumerationType et = null;
			final EnumerationValue ev = null;
			et = em.getEnumerationType("ClassificationAttributeVisibilityEnum");
			final EnumerationValue evVISIBLE = em.getEnumerationValue(et, "VISIBLE");
			final EnumerationValue evVISIBLE_IN_VARIANT = em.getEnumerationValue(et, "VISIBLE_IN_VARIANT");
			EnumerationValue evGewichtEinheit = null;

			// --- Schleife ï¿½ber alle Produkte
			strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
			System.out.println(strFN + strOutput);
			strResult += strOutput + "<br>";
			final int iProdCnt = 0;
			for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
				m_product = (Product) it1.next();
				if (m_product instanceof WeraProduct || m_product instanceof WeraProductSet) {

					// --- Initialize
					final Boolean bAktiv = null;

					// --- Normal
					oWeraMedia = null;
					strProduktBild = ((WeraProduct) m_product).normalizeFilenameForImageLookup() + ".jpg";
					strProduktBild = strProduktBild.replace(".jpg", "_normal.jpg");
					m_hashImages.put(strProduktBild, strProduktBild);
					String strCategorie = "";

					// --- Ist das Produkt aktiv?
					if (((WeraProduct) m_product).IsAktiv()) {
						iProductCounter++;

						// --- Setze Sprache, und Defaultsprache=de
						SetLanguage("de");
						final String strBez_de = (String) m_product.getLocalizedProperty("name");

						// --- Initialisieren der Category
						Category categoryWera = null;
						final Collection categories = getCategoriesByProduct((WeraProduct) m_product, "de");
						final ClassificationAttribute oClassificationAttributes = null;
						if (categories != null && categories.size() > 0) {
							// --- Schleife ï¿½ber alle Kategorien
							Category oCategory = null;
							for (final Iterator it2 = categories.iterator(); it2.hasNext();) {
								// --- Hole Category
								oCategory = (Category) it2.next();

								// --- Hole PCLass-Kategory
								if (oCategory.getCode().substring(0, 2).equals("AA") || oCategory.getCode().substring(0, 1).equals("_")) {
									;
								} else {
									categoryWera = oCategory;
								}

							} // --- for (Iterator it1 = categories.iterator(); it1.hasNext();) 
						}

						// TODO: Prï¿½fen was bei was passiert wenn das Produkt in mehreren 
						// Kategorien vorkommt.
						// --- Hole Category 
						if (categoryWera != null) {
							strCategorie = _getCategorieTree(categoryWera);
						}
						strCategorie = strCategorie.substring(1);

						if (m_product instanceof WeraProductSet) {

							// --- Hole nur die Preise die Exportiert werden sollen
							prices = new ArrayList();
							prices = (ArrayList) _aGetPriceList(m_product, "wera_de_preis_brutto");
							strPreis = "";
							if (prices != null && prices.size() > 0) {

								final Double dPrice = new Double(((PriceRow) prices.iterator().next()).getPrice());
								if (dPrice.doubleValue() != 0) {
									strPreis = _strFormatPrice(dPrice, "de");
								}
							}

							// --- Hole ContentQuantity
							//Integer intContentQuantity = (Integer) m_wm.getAttribute(m_product,"contentQuantity");
							Integer intContentQuantity = new Integer(1);
							if (intContentQuantity == null) {
								intContentQuantity = new Integer(1);
							}

							// --- Schreiben der Artikeldaten
							strEAN = (String) getAttribute(m_product, "ean");
							strCode = (String) m_product.getAttribute("lagerNr") + (String) m_product.getAttribute("artnr")
									+ (String) m_product.getAttribute("variantenNr");
							final String strZolltarifNr = (String) getAttribute(m_product, "ZolltarifNr");
							final String strGewicht = (String) getAttribute(m_product, "Gewicht");
							evGewichtEinheit = (EnumerationValue) m_product.getAttribute("GewichtEinheit");
							String strGewichtE = "";
							if (evGewichtEinheit != null) {
								strGewichtE = evGewichtEinheit.getName();
							}

							strLine = "\"" + "WERA" + "\";\"" + strCode + "\";\"" + strBez_de + "\";\"" + "Stueck" + "\";\"" + strPreis
									+ "\";\"" + m_strDatum + "\";\"" + "EUR" + "\";\"" + intContentQuantity.toString() + "\";\""
									+ strGewicht + "\";\"" + strGewichtE + "\";\"" + intContentQuantity.toString() + "\";\"" + "ab Lager"
									+ "\";\"" + strEAN + "\";\"" + intContentQuantity.toString() + "\";\"" + strZolltarifNr + "\";\""
									+ strProduktBild + "\";\"" + strCode + "\";\"" + strCategorie;

							// --- Schreiben der Zeile
							strLine += "\"\r\n";
							fw.write(strLine);

						} else {
							// --- Hole alle akitven Varianten des Products
							articles = ((WeraProduct) m_product).getVarianten();
							for (final Iterator it2 = articles.iterator(); it2.hasNext();) {
								m_article = (WeraVariante) it2.next();

								// --- Hole ContentQuantity
								//Integer intContentQuantity = (Integer) m_wm.getAttribute(m_article,"contentQuantity");
								Integer intContentQuantity = new Integer(1);
								if (intContentQuantity == null) {
									intContentQuantity = new Integer(1);
								}

								// --- Hole nur die Preise die Exportiert werden sollen
								prices = new ArrayList();
								prices = (ArrayList) _aGetPriceList(m_article, "wera_de_preis_brutto");
								strPreis = "";
								if (prices != null && prices.size() > 0) {

									final Double dPrice = new Double(((PriceRow) prices.iterator().next()).getPrice());
									if (dPrice.doubleValue() != 0) {
										strPreis = _strFormatPrice(dPrice, "de");
									}
								}

								// --- Schreiben der Artikeldaten
								strEAN = (String) getAttribute(m_article, "ean");
								strCode = (String) m_article.getAttribute("lagerNr") + (String) m_article.getAttribute("code")
										+ (String) m_article.getAttribute("variantenNr");

								final String strZolltarifNr = (String) getAttribute(m_article, "ZolltarifNr");
								final String strGewicht = (String) getAttribute(m_article, "Gewicht");
								evGewichtEinheit = (EnumerationValue) getAttribute(m_article, "GewichtEinheit");
								String strGewichtE = "";
								if (evGewichtEinheit != null) {
									strGewichtE = evGewichtEinheit.getName();
								}

								strLine = "\"" + "WERA" + "\";\"" + strCode + "\";\"" + strBez_de + "\";\"" + "Stueck" + "\";\""
										+ strPreis + "\";\"" + m_strDatum + "\";\"" + "EUR" + "\";\"" + intContentQuantity.toString()
										+ "\";\"" + strGewicht + "\";\"" + strGewichtE + "\";\"" + intContentQuantity.toString() + "\";\""
										+ "ab Lager" + "\";\"" + strEAN + "\";\"" + intContentQuantity.toString() + "\";\""
										+ strZolltarifNr + "\";\"" + strProduktBild + "\";\"" + strCode + "\";\"" + strCategorie;

								// --- Schreiben der Zeile
								strLine += "\"\r\n";
								fw.write(strLine);
							}
						}
					}
				}
			}

			System.out.println(strResult);

			//  --- Schliessen
			strOutput = "Export abgeschlossen. Anzahl Produkte=" + iProductCounter;
			System.out.println(strFN + strOutput);
			strResult += strOutput + "<br>";
			fw.close();

			// --- Schreiben der Bilderliste
			final String strTransferFile = _strWriteTransferFile();

			// --- Log der nicht gefundenen Bilder
			//if ( m_aArrayLog.size()  == 0 ) 
			//	m_aArrayLog.add(m_strDatum + " - Alle Bilder wurden erfolgreich ï¿½bernommen.");
			//oXmlSupport._WriteFileFromArray( m_aArrayLog,m_strOutputRoot + "/" + m_strOutputPath + "/" + m_strDatum + "_kistenpf.log" );
			// --- Daten aufbereiten zum Transfer (Ausfï¿½hren der SHell-Datei)
			if (Config.getParameter("wera.os").equals("linux")) {
				oXmlSupport.startCmdFile("chmod a+x " + strTransferFile);
				oXmlSupport.startCmdFile(strTransferFile);
			}

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

		// --- Aufrï¿½umen
		oXmlSupport.cleanUp();

		// --- Meldung
		System.out.println("Datenexport Kistenpfennig beendet.");

		return m_strOutputPath;
	}

	/**
	 * exports footnotes with id and name by articles and products. only
	 * considers products in web_de_2014
	 *
	 * @param String sCatalogId
	 * @param String sCatalogVersion
	 * @return String
	 */
	public String strExportArticlesWithFootnotes(final String sCatalogId, final String sCatalogVersion) {
		// --- Initialize
		final XmlSupport oXmlSupport = new XmlSupport();
		int iProductCounter = 0;
		final String strFN = "strExportArticlesWithFootnotesCSV ==> ";
		String strResult = "";
		String strOutput = "";
		String strLine = "";
		Collection productsSorted = null;
		Collection articles = null;
		String strCode = "";
		String strPK = "";
		String strBase = "";
		String strFootnoteId = "";
		String strFootnoteName = "";
		String strClassificationClass = "";

		m_aArrayLog = new ArrayList();

		// --- Meldung
		System.out.println("Starte Datenexport Footnotes ...");

		// --- Ausgabedatum ermitteln
		m_strDatum = m_wm.InitOutputDatum();

		// --- Ausgabedatei
		m_strOutputRoot = Config.getParameter("wera.exportpath") + "/katalog/" + sCatalogVersion + "/";
		m_strOutputPath = "footnotes-by-articles_" + m_strDatum;
		m_strOutputFile = "wera" + "_de_" + m_strDatum;

		// --- Pfad anlegen falls noch nicht vorhanden
		createDirectory(m_strOutputRoot);
		createDirectory(m_strOutputRoot + "/" + m_strOutputPath);

		// --- Auagabepfade fÃ¼r XML-Daten anlegen
		m_strImageTransferPath = "wera_de_" + m_strDatum + "_daten";
		createDirectory(m_strOutputRoot + "/" + m_strOutputPath + "/" + m_strImageTransferPath);

		try {
			// --- Ã–ffnen der Ausgabedatei
			final String strOutputFile = m_strOutputRoot + "/" + m_strOutputPath + "/" + m_strImageTransferPath
					+ "/footnotes-by-articles.csv";
			m_strResultFile = strOutputFile;
			final FileWriter fw = new FileWriter(strOutputFile);

			// --- Schreibe CSV Header
			strLine = "\"PK" + "\";\"Artikelnummer" + "\";\"Basis Produkt" + "\";\"Proficlass" + "\";\"Fussnote Kennzeichner"
					+ "\";\"Fussnote Text DE\"\r\n";
			fw.write(strLine);

			// --- Alle alle Produkte sortiert nach Produktnummer
			strOutput = "Hole sortierte Produktliste ...";
			System.out.println(strFN + strOutput);
			strResult += strOutput + "<br>";
			final CatalogVersion oCatalogVersion = m_wm.getCatalogVersion(sCatalogId, sCatalogVersion);
			if (oCatalogVersion != null) {

				productsSorted = WeraProduct.getAllProductsFromCatalog(oCatalogVersion, true, "code");

				// --- Schleife Ã¼ber alle Produkte
				strOutput = "Ausgabe der Produkte nach:" + strOutputFile;
				System.out.println(strFN + strOutput);
				strResult += strOutput + "<br>";
				final int iProdCnt = 0;

				// --- Setze Sprache, und Defaultsprache=de
				SetLanguage("de");

				for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {
					m_product = (Product) it1.next();
					if (m_product instanceof WeraProduct || m_product instanceof WeraProductSet) {

						final WeraProduct wp = (WeraProduct) m_product;

						boolean bIsValidProduct = false;
						final Collection<Category2ProductExt> colcat2prodext = wp.getCategory2productexts();
						if (colcat2prodext != null && colcat2prodext.size() > 0) {
							for (final Iterator itCategories = colcat2prodext.iterator(); itCategories.hasNext();) {
								final Category2ProductExt cat2prodext = (Category2ProductExt) itCategories.next();
								final String sCatVersion = cat2prodext.getCatalogversion_desc();
								if (sCatVersion != null && sCatVersion.equals("web_de_2014")) {
									bIsValidProduct = true;
									break;
								}
							}
						}
						if (bIsValidProduct) {
							strClassificationClass = "";
							try {
								// --- Hole alle Superkategorien
								final Collection<Category> categories = (Collection) wp.getAttribute("supercategories");
								// --- Schleife Ã¼ber alle Ergebniss
								for (final Category cat : categories) {
									// --- Hole Katalogversion
									final Item catalogVersion = (Item) getAttribute(cat, "catalogVersion");

									if (catalogVersion.getAttribute("version").toString().equals("3.0")) {
										if (strClassificationClass.equals("")) {
											strClassificationClass = cat.getCode();
										} else {
											strClassificationClass = (strClassificationClass + " | " + cat.getCode());
										}

									}

								}
							} catch (final Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}

							strBase = m_product.getCode();

							// handle footnotes of the base product
							final Collection<Footnote> colFootnotesOfProduct = wp.getFootnotes();
							if (colFootnotesOfProduct != null && colFootnotesOfProduct.size() > 0) {
								strCode = "---";
								if (m_product instanceof WeraProductSet) {

									strCode = (String) m_product.getAttribute("lagerNr") + (String) m_product.getAttribute("artnr")
											+ (String) m_product.getAttribute("variantenNr");
								}

								strPK = m_product.getPK().toString();

								for (final Iterator it2 = colFootnotesOfProduct.iterator(); it2.hasNext();) {
									final Footnote fn = (Footnote) it2.next();
									if (fn != null) {
										strFootnoteId = (String) fn.getAttribute("code");
										strFootnoteName = (String) fn.getLocalizedProperty("name");
										strLine = "\"" + strPK + "\";\"" + strCode + "\";\"" + strBase + "\";\"" + strClassificationClass
												+ "\";\"" + strFootnoteId + "\";\"" + strFootnoteName;
										strLine += "\"\r\n";

										// --- Schreiben der Zeile
										fw.write(strLine);
									}

								}

							}

							iProductCounter++;

							if (!(m_product instanceof WeraProductSet)) {
								// --- Hole alle aktiven Varianten des Produkts
								articles = wp.getVarianten();
								for (final Iterator it2 = articles.iterator(); it2.hasNext();) {
									final WeraVariante wv = (WeraVariante) it2.next();

									// handle footnotes of the base product
									final Collection<Footnote> colFootnotesOfArticles = wv.getFootnotes();
									if (colFootnotesOfArticles != null && colFootnotesOfArticles.size() > 0) {

										for (final Iterator it3 = colFootnotesOfArticles.iterator(); it3.hasNext();) {
											final Footnote fn = (Footnote) it3.next();
											if (fn != null) {

												strFootnoteId = (String) fn.getAttribute("code");
												strFootnoteName = (String) fn.getLocalizedProperty("name");

												strCode = (String) wv.getAttribute("lagerNr") + (String) wv.getAttribute("code")
														+ (String) wv.getAttribute("variantenNr");
												strPK = wv.getPK().toString();

												strLine = "\"" + strPK + "\";\"" + strCode + "\";\"" + strBase + "\";\""
														+ strClassificationClass + "\";\"" + strFootnoteId + "\";\"" + strFootnoteName;
												strLine += "\"\r\n";

												// --- Schreiben der Zeile
												fw.write(strLine);
											}
										}
									}
								}
							}

						} else {
							System.out.println("Product " + wp.getCode() + " is not a web_de_2014 product, skipping ...");
						}

					}
				}

				System.out.println(strResult);

				//  --- Schliessen
				strOutput = "Export abgeschlossen. Anzahl Produkte=" + iProductCounter;
				System.out.println(strFN + strOutput);
				strResult += strOutput + "<br>";
				fw.close();

				// --- Schreiben der Bilderliste
				// final String strTransferFile = _strWriteTransferFile();
				// --- Log der nicht gefundenen Bilder
				//if ( m_aArrayLog.size()  == 0 ) 
				//	m_aArrayLog.add(m_strDatum + " - Alle Bilder wurden erfolgreich ï¿½bernommen.");
				//oXmlSupport._WriteFileFromArray( m_aArrayLog,m_strOutputRoot + "/" + m_strOutputPath + "/" + m_strDatum + "_kistenpf.log" );
			} else {
				System.out.println("Katalogversion wurde nicht gefunden.");
			}

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

		// --- Aufrï¿½umen
		oXmlSupport.cleanUp();

		// --- Meldung
		System.out.println("Datenexport Footnotes beendet.");

		return m_strOutputPath;
	}

	/**
	 *
	 * @return String
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
		System.out.println("Scriptfile fï¿½r Transfer=" + strImageListFile);
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

			// --- Schleife ï¿½ber alle Bilder
			for (final Iterator it1 = listImages.iterator(); it1.hasNext();) {

				strImageName = (String) it1.next();
				fileDirectory = new File("/home/hybris/nexmart/imagepool/" + strImageName);

				// --- Prï¿½fen, ob die Datei vorhanden ist
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
	 * Datenexport zu Tesdtzwecken, kann ï¿½bherschrieben werden
	 *
	 * @param strFileName
	 */
	public void ArticelExport(final String strFileName) {

		final WeraVariante weravariant = null;
		WeraProduct weraproductset = null;
		String strCode;
		String strProductNr;
		final String strEAN;
		String strText;
		ArrayList aCSV_de = new ArrayList();

		// --- Header setzen
		aCSV_de.add("'CODE';'DE';'FR';'EN';'IT';'ES'");

		// --- Hole alle Sï¿½tze
		final ComposedType WeraProductSetType = getSession().getTypeManager().getComposedType(WeraProduct.class);
		final Collection sets = WeraProductSetType.getAllInstances();
		for (final Iterator it1 = sets.iterator(); it1.hasNext();) {

			// --- Hole Sï¿½tze
			weraproductset = (WeraProduct) it1.next();

			final Boolean bValue = ((Boolean) getAttribute(weraproductset, "aktiv"));
			if (bValue != null && bValue.booleanValue()) {

				SetLanguage("de");
				strProductNr = weraproductset.getCode();

				String strText_DE = "";
				strText_DE = weraproductset.getName();

				SetLanguage("fr");
				String strText_FR = "";
				strText_FR = weraproductset.getName();

				SetLanguage("en");
				String strText_EN = "";
				strText_EN = weraproductset.getName();

				SetLanguage("it");
				String strText_IT = "";
				strText_IT = weraproductset.getName();

				SetLanguage("ES");
				String strText_ES = "";
				strText_ES = weraproductset.getName();

				final Collection colArticles = _getAllArticleByProduct(weraproductset);
				// --- Schleife ï¿½ber alle Artikel
				for (final Iterator itArticle = colArticles.iterator(); itArticle.hasNext();) {
					// --- Hole ArtNr 
					strCode = (String) itArticle.next();

					strText = m_strDelim + strProductNr + m_strDelim + m_strSeparater + m_strDelim + strCode + m_strDelim
							+ m_strSeparater + m_strDelim + strText_DE + m_strDelim + m_strSeparater + m_strDelim + strText_FR
							+ m_strDelim + m_strSeparater + m_strDelim + strText_EN + m_strDelim + m_strSeparater + m_strDelim
							+ strText_IT + m_strDelim + m_strSeparater + m_strDelim + strText_ES + m_strDelim;
					aCSV_de.add(strText);
				}

			}
		}

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArrayEncoding(aCSV_de, "/home/hybris/export/" + strFileName, "UTF-8");
		oSupport = null;
		aCSV_de.clear();
		aCSV_de = null;
	}

	/**
	 * Datenexport zu Tesdtzwecken, kann ï¿½bherschrieben werden
	 * 
	 * @param products
	 * @param foreignWeraLanguages
	 * @param strFileName 
	 */
	private void ProduktExport(final Collection products, final ArrayList foreignWeraLanguages, final String strFileName) {

	}

	/**
	 * Category in 
	 * 
	 * @param category
	 * @return 
	 */
	private Collection _Category2CsvString(final Category category) {

		// --- Initialize
		final String strSeparater = "|";
		final Collection colResult = new ArrayList();
		final Collection colParents = new ArrayList();
		Category categoryParent = null;
		String strParentCode = "";

		// --- Hole alle Vï¿½ter
		final Collection colParentsByCategory = (Collection) getAttribute(category, "supercategories");
		System.out.println(category.getCode() + "=>v.Anzahl:" + colParentsByCategory.size());

		// --- Kein Vater vorhanden, oder Root
		if (colParentsByCategory.size() == 0) {
			colParents.add(category);
		} else {
			if (((Category) colParentsByCategory.iterator().next()).getCode().equals("root")) {
				colParents.add(category);
			} else {
				colParents.addAll(colParentsByCategory);
			}
		}
		System.out.println("n.Anzahl:" + colParentsByCategory.size());

		// --- Schleife ï¿½ber alle Vï¿½ter
		for (final Iterator it1 = colParentsByCategory.iterator(); it1.hasNext();) {

			// --- Hole Parent
			categoryParent = (Category) it1.next();
			strParentCode = categoryParent.getCode().toString();

			// --- Hole CODE
			String strLine = m_strDelim + category.getCode() + m_strDelim;

			// --- Category-Bezeichung
			String strText = strSeparater + m_strDelim + category.getLocalizedProperty("name").toString() + m_strDelim;

			// --- Parent-Category
			if (strParentCode.equals(category.getCode().toString()) || strParentCode.equals("root")) {
				strText += strSeparater + m_strDelim + "" + m_strDelim;
			} else {
				strText += strSeparater + m_strDelim + strParentCode + m_strDelim;
			}

			// --- Category-Bild
			strText += strSeparater + m_strDelim + "" + m_strDelim;

			// --- Zeile zusammensetzen
			strLine += strText;
			colResult.add(strLine);
		}

		// --- Zeile zurï¿½ckgeben
		return colResult;
	}

	/**
	 * Datenexport Hoffmann Daten 1 - Lieferantenhierachien
	 *
	 * @param Category category
	 * @return ArrayList
	 */
	private ArrayList _CategoryCsv(Category category) {
		// --- Initialize
		final String strSeparater = "|";
		final ArrayList aCSV_data = new ArrayList();
		Collection categories = new ArrayList();
		final Collection colResult = new ArrayList();
		String strAktCategory = "";

		try {
			// --- Hole alle Kategorien
			// categories = getCategories(Config.getParameter("wera.mastercatalog"), Config.getParameter("wera.mastercatalogversion"), "");
			categories = getCategories((String) m_weraCatalogVersion.getCatalog().getId(), (String) m_weraCatalogVersion.getVersion(), "");

			// --- Schleife ï¿½ber alle Ergebnisse
			System.out.println("Anzahl Categories:" + categories.size());
			for (final Iterator it1 = categories.iterator(); it1.hasNext();) {

				// --- Hole die Category 
				category = (Category) it1.next();
				if (category instanceof Category) {

					// --- Hole die Kategory fï¿½r jeweils fï¿½r alle Oberkategorien einmal
					try {
						strAktCategory = category.getCode();

						// --- Hole eine Zeile
						if (!strAktCategory.equals("root") && !strAktCategory.subSequence(0, 1).equals("_")) {
							aCSV_data.addAll(_Category2CsvString(category));
						}

					} catch (final JaloInvalidParameterException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		} catch (final Exception e1) {
			e1.printStackTrace();
		}

		return aCSV_data;
	}

	/**
	 * Hoffmann: Lhierach_Csv
	 *
	 * @param String strCategory
	 * @param String strLang
	 * @param String strFileName
	 */
	private void Lhierach_Csv(final String strCategory, final String strLang, final String strFileName) {

		// --- Initialze
		final String strSeparater = "|";
		Category RootCategory = null;
		ArrayList aCSV_de = new ArrayList();

		// --- Setze Sprache und hole Daten
		SetLanguage(strLang);

		// --- Header setzen
		aCSV_de.add(m_strDelim + "ID" + m_strDelim + strSeparater + m_strDelim + "Bezeichnung_der_Gruppe" + m_strDelim
				+ strSeparater + m_strDelim + "IDParent" + m_strDelim + strSeparater + m_strDelim + "Group_Picture" + m_strDelim);

		// --- Start-Category holen
		RootCategory = CategoryManager.getInstance().getCategoriesByCode(strCategory).iterator().next();

		// --- Hole Daten
		aCSV_de.addAll(_CategoryCsv(RootCategory));

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArray(aCSV_de, m_strOutputPath + strFileName);
		oSupport = null;
		aCSV_de.clear();
		aCSV_de = null;
	}

	/**
	 * Hoffmann: Art_Lhierach_Csv Datenexport Hoffmann (Artikel/ Lieferanten
	 * Hiercharchie)
	 *
	 * @param Collection products
	 * @param String strLang
	 * @param String strFileName
	 */
	private void Art_Lhierach_Csv(final Collection products, final String strLang, final String strFileName) {
		// --- Initialze
		final String strSeparater = "|";
		Collection colArticles = new ArrayList();
		Collection colCategories = new ArrayList();
		String strArtNr = "";
		String strLine = "";
		WeraProduct product = null;
		WeraProduct oWeraVaterProduct = null;
		final WeraVariante weravariant = null;
		final Object object = null;
		Category category = null;
		ArrayList aCSV_de = new ArrayList();
		HashMap hHashMapTmp = null;
		HashMap hHashMapProducts = new HashMap();

		// --- Setze Sprache und hole Daten
		SetLanguage(strLang);

		// --- Header setzen
		aCSV_de.add(m_strDelim + "ID" + m_strDelim + strSeparater + m_strDelim + "Bezeichnung_der_Gruppe" + m_strDelim
				+ strSeparater + "Artikelnummer" + m_strDelim);

		// --- Schleife ï¿½ber alle Ergebniss
		System.out.println("Anzahl Produkte:" + products.size());
		for (final Iterator it1 = products.iterator(); it1.hasNext();) {

			// --- Hole Produkt
			oWeraVaterProduct = product = (WeraProduct) it1.next();
			if (product instanceof WeraProduct) {
				// --- filter, nur produkte die noch nicht exportiert wurden!
				if (hHashMapProducts.containsKey(product.getCode())) {
					// --- product wurde bereits exportiert
					continue;
				}

				// --- Vaterprodukt merken
				if (product instanceof WeraProductSetinSet) {

					// --- hole das echte Vaterprodukt (für die Kategoriezuordnung)
					oWeraVaterProduct = ((WeraProductSetinSet) product).getMainProduct();
					if (oWeraVaterProduct == null) {

						// --- es existiert kein Vaterprodukt
						oWeraVaterProduct = product;
					}
				}

				try {
					// --- Kategorien sammeln (immer vom Vaterprodukt holen - siehe SB-Varianten)
					//colCategories = getCategoriesByProduct(oWeraVaterProduct, "de");
					colCategories = getCategoriesByProductAndCatalogVersion(product, m_weraCatalogVersion.getVersion());
					//colCategories = _getAllCategoriesByProduct(product);
					//System.out.println(m_weraCatalogVersion);

					// --- Artikelnummern sammeln
					colArticles = _getAllArticleByProduct(product);

					// --- Schleife ï¿½ber alle Artikel
					for (final Iterator itArticle = colArticles.iterator(); itArticle.hasNext();) {
						// --- Hole ArtNr 
						hHashMapTmp = (HashMap) itArticle.next();
						strArtNr = (String) hHashMapTmp.get("code");

						// --- Schleife ï¿½ber alle Categorien
						for (final Iterator itCategories = colCategories.iterator(); itCategories.hasNext();) {
							// --- Hole Category
							category = (Category) itCategories.next();

							// --- Keine klassifizierende Kategorien
							if (category instanceof ClassificationClass) {
								//System.out.println("Skip Category =>" +  category);
								continue;
							}

							// --- Keine klassifizierende Kategorien
							final CatalogVersion oCatalogVersion = (CatalogVersion) getAttribute(category, "catalogVersion");
							//System.out.println(oCatalogVersion);
							if (!oCatalogVersion.equals(m_weraCatalogVersion)) {
								//System.out.println("Skip Catalogvresion =>" +  oCatalogVersion);
								continue;
							}

							// --- Hole Code und Beschreibung 
							//System.out.println("Write Category =>" +  category.getCode() + " p=" + product.getCode() );
							strLine = m_strDelim + category.getAttribute("code") + m_strDelim;
							strLine += strSeparater + m_strDelim + category.getLocalizedProperty("name").toString() + m_strDelim;

							// --- Hole Artnr
							strLine += strSeparater + strArtNr;

							// --- Zeilen zur Ausgabe vorbereiten
							aCSV_de.add(strLine);

						} // --- for ( Iterator itCategories = colCategories.iterator(); itCategories.hasNext(); ) {
					} // --- for ( Iterator itArticel = colArticles.iterator(); itArticel.hasNext(); ) {

				} catch (final JaloInvalidParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final JaloSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// --- merke product
				hHashMapProducts.put(product.getCode(), product.getCode());
			}
		}

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArrayEncoding(aCSV_de, m_strOutputPath + strFileName, "UTF-8");
		oSupport = null;
		aCSV_de.clear();
		aCSV_de = null;

	}

	/**
	 * Zugriff auf Mapping-Datenbank aus hybris Config
	 */
	final protected String MAPPING_DB_NAME = Config.getParameter("wera.classificationmapping.db.name");
	final protected String MAPPING_DB_USER = Config.getParameter("wera.classificationmapping.db.user");
	final protected String MAPPING_DB_PASS = Config.getParameter("wera.classificationmapping.db.password");
	
	/*
	 * final protected String MAPPING_DB_NAME = "wera_mapping"; final protected String MAPPING_DB_USER = "root"; final
	 * protected String MAPPING_DB_PASS = "";
	 */
	protected Connection con = null;
	String m_strECLassNr = "";
	String m_strECLassName = "";

	/**
	 * Datenexport Hoffmann (Artikel/ Klassifikation)
	 *
	 * @param Collection<WeraProduct> products
	 * @param String strLang
	 * @param String strFileName
	 */
	private void FS_pclass30_Csv(final Collection products, final String strLang, final String strFileName) {
		// --- Initialze
		Collection colArticles = new ArrayList();
		Collection colCategories = new ArrayList();
		String strArtNr = "";
		final String strECLassNr = "";
		final String strECLassName = "";
		String strLine = "";
		WeraProduct product = null;
		WeraProduct oWeraVaterProduct = null;
		final WeraVariante weravariant = null;
		final Object object = null;
		Category category = null;
		ArrayList aCSV_de = new ArrayList();
		HashMap hHashMapTmp = null;

		// --- Setze Sprache und hole Daten
		SetLanguage(strLang);

		// ---- Create connection to MySQL
		_bInit_db();

		// --- Header setzen
		aCSV_de.add(m_strDelim + "Spalte1" + m_strDelim + m_strSeparater + m_strDelim + "Spalte2" + m_strDelim + m_strSeparater
				+ "Spalte3" + m_strDelim + m_strSeparater + "Spalte4" + m_strDelim);

		// --- Schleife ï¿½ber alle Ergebniss
		System.out.println("Anzahl Produkte:" + products.size());
		for (final Iterator it1 = products.iterator(); it1.hasNext();) {

			// --- Hole Produkt
			oWeraVaterProduct = product = (WeraProduct) it1.next();
			if (product instanceof WeraProduct) {

				try {

					// --- Vaterprodukt merken
					if (product instanceof WeraProductSetinSet) {

						// --- hole das echte Vaterprodukt (für die Kategoriezuordnung)
						oWeraVaterProduct = ((WeraProductSetinSet) product).getMainProduct();
						if (oWeraVaterProduct == null) {

							// --- es existiert kein Vaterprodukt
							oWeraVaterProduct = product;
						}
					}

					// --- Klassifizierende - Kategorien sammeln
					//colCategories = _getAllCategoriesByProduct( product, m_proficlassCatalogVersion );
					colCategories = _getAllCategoriesByProduct(oWeraVaterProduct);

					// --- Artikelnummern sammeln
					colArticles = _getAllArticleByProduct(product);

					// --- Schleife ï¿½ber alle Artikel
					for (final Iterator itArticle = colArticles.iterator(); itArticle.hasNext();) {
						// --- Hole ArtNr 
						hHashMapTmp = (HashMap) itArticle.next();
						strArtNr = (String) hHashMapTmp.get("code");

						// --- Schleife ï¿½ber alle Categorien
						for (final Iterator itCategories = colCategories.iterator(); itCategories.hasNext();) {
							// --- Hole Category
							category = (Category) itCategories.next();

							// --- nur ProfiClass und WeraEigene Klassen
							final CatalogVersion oCatalogVersion = (CatalogVersion) getAttribute(category, "catalogVersion");
							//System.out.println(oCatalogVersion.getVersion());
							if (oCatalogVersion.getVersion().equals("3.0") || category.getCode().toString().substring(0, 1).equals("_")) {

								// --- Hole Code und Beschreibung 
								strLine = m_strDelim + "V" + m_strDelim;
								strLine += m_strSeparater;
								strLine += m_strSeparater + "'" + strArtNr + "'" + m_strDelim;

								// --- EClass dranhï¿½ngen
								m_strECLassNr = "";
								m_strECLassName = "";
								if (convert2MappingClass(category.getCode(), "eclass/7.0")) {
									strLine += m_strSeparater + m_strDelim + m_strECLassNr + m_strDelim;
									strLine += m_strSeparater + m_strDelim + m_strECLassName.replaceAll(m_strSeparater, ",") + m_strDelim;
								} else {
									String strName = (String) category.getLocalizedProperty("name");
									if (strName == null) {
										strName = "";
									}
									strLine += m_strSeparater + m_strDelim + category.getAttribute("code") + ": (eclass/7.0 not found)"
											+ m_strDelim;
									strLine += m_strSeparater + m_strDelim + strName.toString().replaceAll(m_strSeparater, ",")
											+ m_strDelim;
								}

								// --- Zeilen zur Ausgabe vorbereiten
								aCSV_de.add(strLine);

							} // --- if ( oCatalogVersion.equals(m_proficlassCatalogVersion)   	

						} // --- for ( Iterator itCategories = colCategories.iterator(); itCategories.hasNext(); ) {

					} // --- for ( Iterator itArticel = colArticles.iterator(); itArticel.hasNext(); ) {

				} catch (final JaloInvalidParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (final JaloSecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArrayEncoding(aCSV_de, m_strOutputPath + strFileName, "UTF-8");
		oSupport = null;
		aCSV_de.clear();
		aCSV_de = null;

	}

	/**
	 * convert2MappingClass
	 *
	 * @param String strPClassKlass
	 * @param String strDestKlassificationSystem
	 * @return boolean
	 */
	private boolean convert2MappingClass(final String strPClassKlass, final String strDestKlassificationSystem) {

		// --- initialize
		boolean bResult = false;
		final String strQuery = "select KlasseID_Dst, BezeichnungKlasse_Dst from mapping where  " + " KlasseID_Src='"
				+ strPClassKlass + "' and ClassificationVersion_Dst='" + strDestKlassificationSystem + "' limit 1";
		// --- default
		m_strECLassNr = strPClassKlass + ": (" + strDestKlassificationSystem + " not found)";

		// --- Initialize SQL
		try {
			final Statement stmt = con.createStatement();
			final ResultSet rs = stmt.executeQuery(strQuery);
			if (rs.next()) {
				m_strECLassNr = rs.getString("KlasseID_Dst");
				if (m_strECLassNr == null || m_strECLassNr.equals("null")) {
					// --- default
					m_strECLassNr = strPClassKlass + ": (" + strDestKlassificationSystem + " not found)";
				} else {
					// --- mapping erfolgreich
					bResult = true;
					m_strECLassName = rs.getString("BezeichnungKlasse_Dst");
				}
			}
			rs.close();
		} catch (final SQLException e) {
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}

		return bResult;
	}

	/**
	 * Create connection to MySQL
	 *
	 * @return boolean
	 */
	private boolean _bInit_db() {
		boolean rval = false;
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			con = DriverManager.getConnection("jdbc:mysql:///" + MAPPING_DB_NAME, MAPPING_DB_USER, MAPPING_DB_PASS);

			if (!con.isClosed()) {
				LOG.info("Successfully connected to " + "MySQL server using TCP/IP...");
				rval = true;
			}
		} catch (final Exception e) {
			System.err.println("Exception: " + e.getMessage());
		}

		/*
		 * finally { try { if (con != null) { con.close(); } } catch(SQLException e) {} }
		 */
		return rval;
	}

	/**
	 * Hoffmann: ProduktMerkmaleCsv
	 *
	 * @param Collection products
	 * @param String strLang
	 * @param String strFileName
	 * @param HashMap hProduktBesch
	 */
	private void ProduktMerkmaleCsv(final Collection products, final String strLang, final String strFileName,
			final HashMap hProduktBesch) {
		// --- Initialze
		String strBeschProdukt = "";
		final Collection colArticles = new ArrayList();
		String strArtNr = "";
		String strPK = "";
		String aLines[];
		String aHeader[];
		String strText = "";
		String strHeader = "";
		final String strDescription = "";
		String strLine = "";
		WeraProduct product = null;
		final WeraVariante weravariant = null;
		ArrayList aCSV_Data = new ArrayList();
		HashMap hHashMapTmp = null;
		final HashMap hHashMapDoubletten = new HashMap();

		// --- DatenCrector
		final DataCorrector oDC = new DataCorrector();

		// --- Initialize Header
		final HashSet hHeaderSet = new HashSet();
		hHeaderSet.clear();
		hHeaderSet.add("1CODE");
		hHeaderSet.add("2ARTNR");

		// --- Setze Sprache und hole Daten
		SetLanguage(strLang);

		// --- Schleife ï¿½ber alle Ergebniss
		System.out.println("Ausgabesprache: " + strLang);
		System.out.println("Anzahl Produkte:" + products.size());
		for (final Iterator it1 = products.iterator(); it1.hasNext();) {

			// --- Hole Produkt
			product = (WeraProduct) it1.next();
			if (product instanceof WeraProduct) {

				System.out.println("Bearbeite " + product.getCode());

				try {
					// --- Artikelnummern sammeln
					colArticles.clear();
					colArticles.addAll(_getAllArticleByProduct(product));

					// --- Schleife ï¿½ber alle Artikel
					for (final Iterator itArticel = colArticles.iterator(); itArticel.hasNext();) {

						// --- Hole ArtNr
						final HashMap hHeaderMap = new HashMap();
						hHashMapTmp = (HashMap) itArticel.next();
						strArtNr = (String) hHashMapTmp.get("code");
						strPK = (String) hHashMapTmp.get("pk");

						// --- check if we already worked on it
						if (hHashMapDoubletten.containsKey(strPK)) {
							// --- we stop here
							continue;
						} else {
							// --- remember it!!
							hHashMapDoubletten.put(strPK, hHashMapTmp);
						}

						hHeaderMap.put("2ARTNR", "'" + strArtNr + "'");

						// --- Hole CODE
						String strCode = (String) getAttribute(product, "code");
						if (strCode == null) {
							strCode = "";
						}
						hHeaderMap.put("1CODE", strCode);

						// --- Baue Text zusammen (ProduktBesch) 
						if (hProduktBesch.containsKey(strPK)) {
							strBeschProdukt = (String) hProduktBesch.get(strPK);
						} else {
							strBeschProdukt = strCode + "\t'" + strArtNr + "'";
						}
						String strNameProdukt = (String) getAttribute(product, "name");
						if (strNameProdukt == null) {
							strNameProdukt = "";
						}
						strBeschProdukt += "\t" + strNameProdukt;
						hProduktBesch.put(strPK, strBeschProdukt);

						// --- Holen und auftrennen des Textes
						strText = (String) getAttribute(product, "description1");
						if (strText != null && strText.length() > 0) {

							// --- normalize text
							strText = strText.replaceAll("\\t", "");
							strText = strText.replaceAll("\\n", " ");
							strText = strText.replaceAll("\t", " ");
							strText = strText.replaceAll("\r", " ");
							strText = strText.replaceAll("\n", " ");
							strText = strText.replaceAll(m_strSeparater, ",");
							strText = strText.replaceAll("<p />", "");
							strText = strText.replaceAll("<strong>", "<b>");
							strText = strText.replaceAll("</strong>", "</b>");
							strText = strText.replaceAll("<B>", "<b>");
							strText = strText.replaceAll("</B>", "</b>");
							strText = strText.replaceAll("<b/>", "</b>");
							strText = strText.replaceAll("<b />", "</b>");
							strText = strText.replaceAll("<BR", "<br");
							strText = strText.replaceAll("<bR", "<br");
							strText = strText.replaceAll("<Br", "<br");
							strText = strText.replaceAll("<br>", "<br/>");
							strText = strText.replaceAll("<br />", "<br/>");
							strText = strText.replaceAll("<br  />", "<br/>");

							// --- Baue die Felder zusammen
							aLines = strText.split("<br/>");
							strText = "";
							strHeader = "";
							for (int iPos = 0; iPos < aLines.length; iPos++) {

								// --- Sind Daten vorhanden?
								if (aLines[iPos].length() > 0) {

									// --- Header + Text oder nur Text
									aHeader = aLines[iPos].split("</b>");
									if (aHeader.length > 1) {
										// --- Separiere Header und Text
										strHeader = aHeader[0].replaceAll("<b>", "");
										strHeader = strHeader.replaceAll("<b>", "");
										strHeader = strHeader.replaceAll("<p>", "");
										strHeader = strHeader.replaceAll("<vr />", "");
										strHeader = strHeader.replaceAll(":", "");
										strHeader = strHeader.replaceAll(",", "");
										strHeader = strHeader.replaceAll("<", "");
										strHeader = strHeader.replaceAll(">", "");
										strHeader = strHeader.replaceAll("\t", "");
										strHeader = strHeader.replaceAll("\r", "");
										strHeader = strHeader.replaceAll("\n", "");
										strHeader = strHeader.trim();
										strText = aHeader[1].trim().replaceAll("<b>", "");
										if (strHeader.length() == 0) {
											strHeader = "Zeile" + (iPos + 1);
											// --- Datenwert merken
											hHeaderMap.put(strHeader, strText);
										} else {
											// --- Datenwert merken
											hHeaderMap.put(strHeader, strHeader + ":" + strText);
										}
									} else {

										// --- Datenwert merken
										strHeader = "Zeile" + (iPos + 1);
										strText = aHeader[0].trim();
										hHeaderMap.put(strHeader, strText);
									}

									// --- Alle mï¿½glichen Header merken
									if (!hHeaderSet.contains(strHeader)) {
										hHeaderSet.add(strHeader);
									}

								} // --- if ( aLines[iPos].length() > 0 ) {

							} // --- for ( int iPos=0; iPos < aLines.length; iPos++ ) {
						}

						// --- Zeilen zur Ausgabe vorbereiten
						aCSV_Data.add(hHeaderMap);

					} // --- for ( Iterator itArticel = colArticles.iterator(); itArticel.hasNext(); ) {

				} catch (final JaloInvalidParameterException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		// --- Baue Output zusammen
		ArrayList aCSV_output = new ArrayList();
		final HashMap hHeaderMap = new HashMap();
		strLine = "";
		for (final Iterator it2 = hHeaderSet.iterator(); it2.hasNext();) {
			final String strKey = (String) it2.next();
			hHeaderMap.put(strKey, strKey);
		}
		aCSV_output.add(hHeaderMap);
		aCSV_output.addAll(aCSV_Data);

		// --- Logfile schreiben
		MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromHash(aCSV_output, hHeaderSet, m_strOutputPath + strFileName, m_strDelim, m_strSeparater);
		oSupport = null;
		hHeaderSet.clear();
		hHeaderMap.clear();
		aCSV_Data.clear();
		aCSV_output.clear();
		aCSV_Data = null;
		aCSV_output = null;
	}

	/**
	 * Hoffmann Hauptroutine Textdateien für Kunde Hoffmann
	 *
	 * @param String strCatalog
	 * @param String strCatalogVersion
	 * @return String
	 */
	public String HoffmannCsv1(final String strCatalog, final String strCatalogVersion) {
		// --- DEBUG
		System.out.println("Ausgabe Hoffmann....");
		System.out.println("Katalog=" + strCatalog);
		System.out.println("Katalogversion=" + strCatalogVersion);

		// --- Initialize
		String strLang = "";

		// --- Trennter initialisieren
		m_strDelim = "";
		m_strSeparater = "\t";

		// --- Ausgabedatum
		final String strDatum = m_wm.InitOutputDatum();

		// --- Ausgabedatei, Pfad anlegen falls noch nicht vorhanden
		System.out.println("datum=" + strDatum);
		m_strOutputPath = Config.getParameter("wera.exportpath") + "/katalog/" + strCatalogVersion + "/";
		createDirectory(m_strOutputPath);
		final String strOutputDir = "hoffmann_" + strDatum;
		m_strOutputPath += strOutputDir + "/";
		createDirectory(m_strOutputPath);

		// --- Exportsprachen 
		final ArrayList foreignWeraLanguages = new ArrayList();
		foreignWeraLanguages.add("de");
		foreignWeraLanguages.add("en");
		foreignWeraLanguages.add("fr");
		foreignWeraLanguages.add("es");

		// --- Hole alle aktiven Produkte, sortiert, inkl. SB-Varianten
		m_weraCatalogVersion = getCatalogVersion(strCatalog, strCatalogVersion);
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		//final Collection products = WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion, true, "code");
		final Collection colFilter = new ArrayList();
		//                colFilter.add("PREISLISTENARTIKEL");
		boolean bExportSBVariants = true;

		// --- hole alle
		final Collection products = WeraProduct.getAllProductsFromAnyCatalog(m_weraCatalogVersion, true, "code", colFilter, bExportSBVariants);
		System.out.println("Anzahl Produkte=" + products.size());

		// --- Exportiere Merkmale-Beschreibungen
		HashMap hProduktBesch = new HashMap();
		for (final Iterator itLang = foreignWeraLanguages.iterator(); itLang.hasNext();) {
			strLang = (String) itLang.next();

			// --- Exportiere alle Produktbeschreibungen
			System.out.println("ProduktMerkmaleCsv" + strLang);
			ProduktMerkmaleCsv(products, strLang, strLang + "_prod_merkale.txt", hProduktBesch);
		}

		// --- Ausgabe der Produktbeschreibungen
		if (hProduktBesch.size() > 0) {
			// --- Logfile schreiben
			MediandoXml oSupport = new MediandoXml();
			ArrayList colProduktBesch = new ArrayList();
			colProduktBesch.add("CODE\tARTNR\tPRODUKT_DE\tPRODUKT_EN\tPRODUKT_FR\tPRODUKT_ES");
			colProduktBesch.addAll(hProduktBesch.values());
			oSupport._WriteFileFromArrayEncoding(colProduktBesch, m_strOutputPath + "produktbesch.txt", "UTF-8");
			oSupport = null;
			colProduktBesch.clear();
			hProduktBesch.clear();
			hProduktBesch = null;
			colProduktBesch = null;
		}

		// --- Trennter initialisieren
		m_strDelim = "";
		m_strSeparater = "\t";

		// --- Ausgabedatum
		//strDatum = oDate.toLocaleString();
		final Comment dateComment = new Comment(" Datenexport vom: " + strDatum);

		// --- Lieferantenhiearchien (I)
		Lhierach_Csv("root", "de", "Lhierach_WERA_" + strDatum.substring(0, 10).replace(".", "") + ".txt");

		// --- Artikel / Lieferantenhiearchien (II)
		Art_Lhierach_Csv(products, "de", "Art_Lhierach_WERA_" + strDatum.substring(0, 10).replace(".", "") + ".txt");

		// --- Datenexport Hoffmann (Artikel/ Klassifikation)
		FS_pclass30_Csv(products, "de", "FS_eclass70_WERA_" + strDatum.substring(0, 10).replace(".", "") + ".txt");

		return strOutputDir;
	}

	/**
	 * Europarts: Exportdateien fï¿½r Kunde Europarts
	 * 
	 * @param strKatalog
	 * @param strKatalogVersion
	 * @param strLanguage
	 * @return 
	 */
	public String EuropartsCsv(final String strKatalog, final String strKatalogVersion, final String strLanguage) {

		// --- Initialize
		final ArrayList aArrayResultFile = new ArrayList();
		HashMap hashProdukt = null;
		final HashMap hashCodeListe = new HashMap();
		final HashMap hashResultMap = new HashMap();
		String strCode = "";
		String[] aString = null;
		String strLine = "";
		String strProduktData = "";
		final ArrayList colCA = new ArrayList();
		m_HeaderMap = new HashMap();
		m_ValueMap = new HashMap();

		// --- Debug
		System.out.println("EuropartsCsv => Datenexport Europarts wurde gestartet...");
		System.out.println("EuropartsCsv => +Produktdaten werden initialisiert...");

		// --- Ausgabedatum
		final String strDatum = m_wm.InitOutputDatum();

		// --- BMECat - Support anforden
		m_oBMEcatXml = new BMEcatXml();

		// --- Sprache setzen
		SetLanguage(strLanguage);

		// --- Kopfzeile
		final String strHeader1 = "Hersteller_Nr\tEP-Nr\tArtikelbezeichnung1\tArtikelbezeichnung2\tName\tProdukttext\tAnmerkung\tAnwendungsbereich\tLieferumfang\tTechnische Daten\tBildname1\tBildname2\tBildtyp2";
		final String strHeader2 = "\tF_Name1\tF_Value1\tF_Unit1\t F_Name2\tF_Value2\tF_Unit2\t F_Name3\tF_Value3\tF_Unit3\t F_Name4\tF_Value4\tF_Unit4\t F_Name5\tF_Value5\tF_Unit5\t F_Name6\tF_Value6\tF_Unit6\t F_Name7\tF_Value7\tF_Unit7\t F_Name8\tF_Value8\tF_Unit8\t F_Name9\tF_Value9\tF_Unit9\t F_Name10\tF_Value10\tF_Unit10\t F_Name11\tF_Value11\tF_Unit11\t F_Name12\tF_Value12\tF_Unit12\t F_Name13\tF_Value13\tF_Unit13\t F_Name14\tF_Value14\tF_Unit14\t F_Name15\tF_Value15\tF_Unit15\t F_Name16\tF_Value16\tF_Unit16\t F_Name17\tF_Value17\tF_Unit17\t F_Name18\tF_Value18\tF_Unit18\t F_Name19\tF_Value19\tF_Unit19\tF_Name20\tF_Value20\tF_Unit20\tF_Name21\tF_Value21\tF_Unit21";
		String strHeader3 = "\t\tArtikelnummer\t\tProdukt\tBeschreibung 1\t\t\t\t\t\t\t\t";
		aArrayResultFile.add(strHeader1 + strHeader2);

		// --- Initialize II
		final String strOutputFile = "europarts_" + strDatum + ".txt";
		final String strOutputPath = "/europarts_" + strDatum;
		final String m_strControlOSPath = Config.getParameter("wera.homepath") + "/export/europarts/control/";
		final String m_strDataOSPath = Config.getParameter("wera.homepath") + "/export/katalog/" + strKatalogVersion
				+ "/europarts_" + strDatum + "/data/";
		createDirectory(Config.getParameter("wera.homepath") + "/export/katalog/" + strKatalogVersion + strOutputPath);
		createDirectory(m_strDataOSPath);

		// --- Steuerdatei einlesen
		BufferedReader in;
		try {

			in = new BufferedReader(new InputStreamReader(new FileInputStream(m_strControlOSPath + "europarts.txt")));

			while ((strCode = in.readLine()) != null) {
				aString = strCode.split("\t");
				if (aString.length == 2) {
					hashCodeListe.put(aString[0].trim(), aString[1].trim());
				} else {
					hashCodeListe.put(aString[0].trim(), "<empty>");
				}
			}
			in.close();
		} catch (final Exception e) {
		}

		// --- Hole alle aktiven Produkte, unsorted
		m_weraCatalogVersion = getCatalogVersion(strKatalog, strKatalogVersion);
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion, false, "code");
		System.out.println("EuropartsCsv => +Anzahl products=" + products.size());

		// --- Schleife ï¿½ber alle Produkte
		Product weraproduct = null;
		int iVariants = 0;
		int iProducts = 0;
		int iProductsSet = 0;
		for (final Iterator it1 = products.iterator(); it1.hasNext();) {
			weraproduct = (Product) it1.next();

			if (weraproduct instanceof WeraProductSet || weraproduct instanceof WeraProductSetinSet) {
				iProductsSet++;
			} else {
				if (weraproduct instanceof WeraProduct) {
					iProducts++;
				} else {
					iVariants++;
				}
			}

			if (!(weraproduct instanceof WeraVariante)) {

				try {

					if (weraproduct instanceof WeraProductSet || weraproduct instanceof WeraProductSetinSet) {

						// --- Satz ---------------------------------------------------------------------
						strCode = (String) weraproduct.getAttribute("lagerNr") + (String) weraproduct.getAttribute("artnr")
								+ (String) weraproduct.getLocalizedProperty("variantenNr");
						if (hashCodeListe.containsKey(strCode)) {
							// --- Debuig
							System.out.println("+Export (SATZ)=" + strCode);

							strLine = strCode + "\t" + (String) hashCodeListe.get(strCode) + "\t";

							// --- Initiallize allg. Produktdaten
							strProduktData = _EP_getProduktData(weraproduct);
							strLine += strProduktData;

							hashCodeListe.remove(strCode);
							hashResultMap.put(strCode, strLine);
						}
						// --- Satz ---------------------------------------------------------------------

					} else {
						// --- Produkt ------------------------------------------------------------------

						// --- Initiallize allg. Produktdaten (reset)
						strProduktData = "";
						colCA.clear();

						// ---  Artikelnummern holen - Schleife ï¿½ber alle Artikel
						final Collection colArticles = _getAllArticleByProduct((WeraProduct) weraproduct);
						if (colArticles != null && colArticles.size() > 0) {
							for (final Iterator itArticel = colArticles.iterator(); itArticel.hasNext();) {

								// --- Hole ArtNr
								hashProdukt = (HashMap) itArticel.next();
								strCode = ((String) hashProdukt.get("code")).trim();
								if (hashCodeListe.containsKey(strCode)) {

									// --- Debuig
									System.out.println("+Export=" + strCode);

									// --- Initialize Produktdaten (1x fï¿½r alle Artikel)
									if (colCA.size() == 0 || strProduktData.length() == 0) {
										// --- Initiallize allg. Produktdaten
										strProduktData = _EP_getProduktData(weraproduct);

										// --- Initiallize ClassificationAttributes => Artikeldaten
										_EP_getClassificationAttributes(weraproduct, strLanguage, colCA);
									}

									strLine = strCode + "\t" + (String) hashCodeListe.get(strCode) + "\t";

									// --- Hole die Produktbeschreibung
									strLine += strProduktData;

									// --- Hole die Artikelwerte
									_EP_getCAValues(strCode, (Product) hashProdukt.get("artikel"), colCA);

									hashResultMap.put(strCode, strLine);
									hashCodeListe.remove(strCode);
								}
							}
							// --- Produkt ------------------------------------------------------------------
						}
					}

				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}

		// --- Schleife ï¿½ber alle Artikel
		if (hashCodeListe.size() > 0) {
			System.out.println("+Liste der fehlenden Artikel...");
			for (final Iterator itArticel = hashCodeListe.keySet().iterator(); itArticel.hasNext();) {
				strCode = (String) itArticel.next();
				System.out.println("Fehlende Artikel=" + strCode);
				strLine = strCode + "\t" + (String) hashCodeListe.get(strCode) + "\t- fehlt in Hybris -";
				hashResultMap.put(strCode, strLine);
			}
		}

		// --- Schleifer ï¿½ber alle Artikelzeilen
		String strHeaderName = "";
		String strDataProdukt = "";
		String strDataArtikel = "";
		final Object[] aHeader = m_HeaderMap.keySet().toArray();
		Arrays.sort(aHeader);
		final Object[] aResultArray = hashResultMap.keySet().toArray();
		Arrays.sort(aResultArray);
		System.out.println("EuropartsCsv => +Daten werden sortiert - size=" + aResultArray.length);
		for (int iPos1 = 0; iPos1 < aResultArray.length; iPos1++) {
			// --- Initialize
			strCode = (String) aResultArray[iPos1];
			strDataProdukt = (String) hashResultMap.get(strCode);
			strDataArtikel = "";

			// --- Schelife ï¿½ber alle Header
			for (int iPos = 0; iPos < aHeader.length; iPos++) {
				strHeaderName = (String) aHeader[iPos];
				if (m_ValueMap.containsKey(strCode + "-" + strHeaderName)) {
					strDataArtikel += (String) m_ValueMap.get(strCode + "-" + strHeaderName);
				} else {
					strDataArtikel += "\t\t\t";
				}
			}

			// --- In Array merken
			strLine = strDataProdukt + strDataArtikel;
			aArrayResultFile.add(strLine);
		}

		// --- 2. Kopfzeile (Legende) generieren
		for (int iPos = 0; iPos < aHeader.length; iPos++) {
			strHeader3 += (String) m_HeaderMap.get(aHeader[iPos]) + "\t\t\t";
		}
		if (aArrayResultFile == null || aArrayResultFile.size() <= 1) {
			aArrayResultFile.clear();
			aArrayResultFile.add(strHeader3);
		} else {
			aArrayResultFile.set(1, strHeader3);
		}

		// --- Zusammenfassung
		System.out.println("EuropartsCsv => +Results...");
		System.out.println("EuropartsCsv => +products.size()=" + products.size());
		System.out.println("EuropartsCsv => +iVariants=" + iVariants);
		System.out.println("EuropartsCsv => +iProducts=" + iProducts);
		System.out.println("EuropartsCsv => +iProductsSet=" + iProductsSet);

		// --- Datum ermitteln
		m_strDatum = strDatum;

		// --- Datenfile schreiben
		System.out.println("EuropartsCsv => +Daten werden geschrieben...");
		System.out.println("EuropartsCsv => +Pfad=" + m_strDataOSPath);
		System.out.println("EuropartsCsv => +File=" + strOutputFile);
		final MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArrayEncoding(aArrayResultFile, m_strDataOSPath + strOutputFile, "UTF-8");

		return strOutputPath;
	}

	/**
	 * Europarts: Initiallize allg. Produktdaten
	 * 
	 * @param weraproduct
	 * @return 
	 */
	private String _EP_getProduktData(final Product weraproduct) {

		// --- Initialize
		String strTmp = "";
		String strResult = "";

		// --- CODE, "Artikelbezeichnung1"
		strTmp = weraproduct.getCode();
		if (strTmp == null) {
			strTmp = "";
		}
		strResult += strTmp.replace("\t", "").replace("\r", "").replace("\n", "") + "\t";

		//		 --- , "Artikelbezeichnung2"
		strResult += "\t";

		//		 --- name, Name
		strTmp = weraproduct.getName();
		if (strTmp == null) {
			strTmp = "";
		}
		strResult += strTmp.replace("\t", "").replace("\r", "").replace("\n", "") + "\t";

		//		 --- , Produkttext
		strTmp = ((WeraProduct) weraproduct).getDescription1();
		if (strTmp == null) {
			strTmp = "";
		}
		strResult += strTmp.replace("\t", "").replace("\r", "").replace("\n", "") + "\t";

		//		 --- , Anmerkung
		if (weraproduct instanceof WeraProductSet) {
			// --- Gen. Bestï¿½ckung
			final Collection aContent = ((WeraProductSet) weraproduct).generateWeraProductSetData();
			HashMap oHashMapProdukt = null;
			String strTagContent = "";
			ArrayList colHash = null;
			if (aContent != null && aContent.size() > 0) {
				for (final Iterator it1 = aContent.iterator(); it1.hasNext();) {

					// --- Hole Map
					oHashMapProdukt = (HashMap) it1.next();

					// --- Initialize
					final String strCodeName = (String) oHashMapProdukt.get("code");
					colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
					if (colHash != null && colHash.size() > 0) {
						int iPos1 = 0;
						for (final Iterator it2 = colHash.iterator(); it2.hasNext();) {
							// --- Hole Map
							iPos1++;
							final HashMap oHashMapArtikel = (HashMap) it2.next();
							if (oHashMapArtikel != null) {
								if (iPos1 < colHash.size()) {
									strTagContent += strCodeName + ":" + oHashMapArtikel.get("value") + ";";
								} else {
									strTagContent += strCodeName + ":" + oHashMapArtikel.get("value");
								}
							}
						}
					}

					// --- Content korrigieren
					if (strTagContent.length() > 2) {
						strTagContent = strTagContent.trim();
					}
				}
			}

			// --- Bestï¿½ckung
			strResult += strTagContent + "\t";
		} else {
			strResult += "\t";
		}

		//		 --- , Anwendungsbereich
		strResult += "\t";

		//		 --- , Lieferumfang
		strResult += "\t";

		//		 --- , Technische Daten
		strResult += "\t";

		// --- , Bildname1
		String strProduktImage = ((WeraProduct) weraproduct).normalizeFilenameForImageLookup();
		strProduktImage = m_oBMEcatXml._NormalizeImageName(strProduktImage);
		strResult += strProduktImage + "\t";

		//		 --- , Bildname2
		strResult += strProduktImage + ".eps\t";

		//		 --- , Bildtyp2
		strResult += "Fotografie-Produktbild\t";

		return strResult;
	}

	/**
	 * Europarts: Initiallize ClassificationAttributes => Artikeldaten
	 * @param weraproduct
	 * @param strLanguage
	 * @param colCA
	 * @return 
	 */
	private ClassificationClass _EP_getClassificationAttributes(final Product weraproduct, final String strLanguage,
			final Collection colCA) {

		// --- Initialize
		ClassAttributeAssignment oClassAttributeAssignment = null;
		ClassificationAttribute oClassificationAttribute = null;
		final Category categoryByCa = null;
		final String strName = "";
		final String strCaName = "";
		final String strEinheit = "";
		final Element oCustomAttribute = null;
		final int iOrder = 1;
		HashMap hashCategory2CA = new HashMap();

		// --- Initialize
		hashCategory2CA.clear();

		// ---- Initialize Sichtbarkeit (Attribute)
		final EnumerationManager em = JaloSession.getCurrentSession().getEnumerationManager();
		EnumerationType et = null;
		final EnumerationValue ev = null;
		et = em.getEnumerationType("ClassificationAttributeVisibilityEnum");
		final EnumerationValue evVISIBLE = em.getEnumerationValue(et, "VISIBLE");
		final EnumerationValue evVISIBLE_IN_BASE = em.getEnumerationValue(et, "VISIBLE_IN_BASE");
		final EnumerationValue evVISIBLE_IN_VARIANT = em.getEnumerationValue(et, "VISIBLE_IN_VARIANT");

		// --- Schleife ï¿½ber alle Attribute
		final Collection ouputcontrols = (Collection) getAttribute(weraproduct, "outputcontrols");
		Outputcontrol outputcontrol = null;
		EnumerationValue evVisibility = null;
		ClassificationClass oCategory = null;

		try {

			// --- Holen der Klassifizierenden Kategorien
			//Collection categories = getCategoriesByProduct(
			//		(WeraProduct) weraproduct, strLanguage);
			final Collection categories = _getAllCategoriesByProduct((WeraProduct) weraproduct);

			// --- Initialisieren der Merkmale
			if (categories != null && categories.size() > 0) {
				// --- Schleife ï¿½ber alle Kategorien
				for (final Iterator it1 = categories.iterator(); it1.hasNext();) {
					// --- Hole Category
					oCategory = (ClassificationClass) it1.next();

					// --- Initialisiere alle Attribute
					///Collection colAttr = getClassificationAttributes(oCategory);
					final Collection<ClassAttributeAssignment> colAttr = m_weraclassificationhelper
							.getClassificationAttributeAssignments(oCategory);

					if (colAttr != null && colAttr.size() > 0) {
						for (final Iterator it2 = colAttr.iterator(); it2.hasNext();) {

							// --- Hole das CA
							oClassAttributeAssignment = (ClassAttributeAssignment) it2.next();
							oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();

							// --- Hole Ausgabesteuerung
							outputcontrol = (Outputcontrol) WeraManager.checkContaining(ouputcontrols, "code",
									oClassificationAttribute.getCode());
							if (outputcontrol != null) {
								evVisibility = (EnumerationValue) getAttribute(outputcontrol, "visibility");
							} else {
								evVisibility = evVISIBLE_IN_BASE;
							}

							// --- Nur die benï¿½tigten Attribute nehmen
							// if (evVisibility == evVISIBLE_IN_VARIANT || evVisibility == evVISIBLE)
							if (evVisibility.equals(evVISIBLE_IN_VARIANT) || evVisibility.equals(evVISIBLE)) {

								hashCategory2CA = new HashMap();
								hashCategory2CA.put("category", oCategory);
								hashCategory2CA.put("classificationattribute", oClassificationAttribute);
								hashCategory2CA.put("classattributeassignment", oClassAttributeAssignment);
								hashCategory2CA.put("outputcontrol", outputcontrol);
								colCA.add(hashCategory2CA);

							} // --- if (evVisibility == evVISIBLE_IN_VARIANT ...

						} // --- for (Iterator it2 = colAttr.iterator();
						// it2.hasNext();) ...

					} // --- if (colAttr != null && colAttr.size() > 0) {

				} // --- for (Iterator it1 = categories.iterator();

			} // --- if (categories != null && categories.size() > 0) {
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return oCategory;
	}

	/**
	 * Euroarts: Initiallize Artikeldaten
	 * 
	 * @param strCode
	 * @param weraarticle
	 * @param colCA 
	 */
	private void _EP_getCAValues(final String strCode, final Product weraarticle, final Collection colCA) {

		// --- Initialize
		final ArrayList colValue = new ArrayList();
		String strHeader = "";
		final String strResult = "";
		String strValue = "";
		HashMap hashCA = null;
		ClassificationAttribute oClassificationAttribute = null;
		ClassAttributeAssignment oClassattributeAssignment = null;
		Category categoryByCa = null;

		// --- Schleife ï¿½ber alle Werte
		for (final Iterator itCA = colCA.iterator(); itCA.hasNext();) {
			// --- Initialize
			hashCA = (HashMap) itCA.next();
			oClassificationAttribute = (ClassificationAttribute) hashCA.get("classificationattribute");
			oClassattributeAssignment = (ClassAttributeAssignment) hashCA.get("classattributeassignment");

			categoryByCa = (Category) hashCA.get("category");

			// --- Bezeichnung
			strValue = oClassificationAttribute.getName();

			// --- Wert
			colValue.clear();
			colValue.addAll(m_oBMEcatXml.colGetCAValue(weraarticle, oClassattributeAssignment));
			strValue += "\t";
			if (colValue != null && colValue.size() > 0) {
				if (strValue.toLowerCase().indexOf("zoll") != -1 || colValue.get(0).toString().indexOf("/") != -1
						|| colValue.get(0).toString().indexOf("\"") != -1) {
					if (!strValue.equals("-")) {
						strValue += "'" + colValue.get(0) + "\"'";
					}
				} else {
					strValue += (String) colValue.get(0);
				}
			}

			// --- EInheit
			String strUnit = m_oBMEcatXml._getUnit4CA(oClassattributeAssignment, categoryByCa);
			if (strValue.toLowerCase().indexOf("zoll") != -1) {
				strUnit = "Zoll";
			}
			strValue += "\t" + strUnit + "\t";

			// --- Merken
			strHeader = oClassificationAttribute.getName().trim().replace(",", "").replace(" ", "").replace(")", "")
					.replace("(", "");
			m_HeaderMap.put(strHeader, oClassificationAttribute.getName().trim());
			m_ValueMap.put(strCode + "-" + strHeader, strValue);
			//strResult += strValue;
		}

		//return strResult;
	}

	/**
	 * 
	 * @param wpm
	 * @return 
	 */
	private ClassificationClassModel getDimensionenClass(final WeraProductModel wpm) {
		ClassificationClassModel ccm = null;
		final Collection<CategoryModel> colSuperCats = wpm.getSupercategories();

		for (final CategoryModel superCat : colSuperCats) {
			final CatalogVersionModel cvm = superCat.getCatalogVersion();
			if (cvm.getVersion().equals("werazusatz") && superCat.getCode().equals("DIMENSIONEN")) {
				ccm = (ClassificationClassModel) superCat;
				break;
			}
		}

		return ccm;
	}

	/**
	 * 
	 * @param oClass
	 * @param sAttributeName
	 * @return 
	 */
	private ClassAttributeAssignmentModel getClassAttributeAssignment(final ClassificationClassModel oClass,
			final String sAttributeName) {
		ClassAttributeAssignmentModel caam = null;
		final ClassificationClass oClassJalo = m_modelService.getSource(oClass);
		final ClassificationAttribute oAttributeJalo = oClassJalo.getClassificationAttribute(sAttributeName);
		if (oAttributeJalo != null) {
			final ClassAttributeAssignment caa = oClassJalo.getAttributeAssignment(oAttributeJalo);
			if (caa != null) {
				caam = m_modelService.get(caa);
			}
		}
		return caam;
	}

	/**
	 * 
	 * @param sFeatureValue
	 * @param caam
	 * @return 
	 */
	private String getSelectedAttributeValue(final String sFeatureValue, final ClassAttributeAssignmentModel caam) {
		//final long startTime = System.currentTimeMillis();

		String sValue = null;

		final List<ClassificationAttributeValueModel> listCAAM = new ArrayList();

		final List<ClassificationAttributeValueModel> listCustomValues = caam.getCustomValues();
		if (listCustomValues != null) {
			listCAAM.addAll(listCustomValues);
		}
		listCAAM.addAll(caam.getAttributeValues());

		for (final ClassificationAttributeValueModel currentCaam : listCAAM) {
			//LOG.info("getSelectedAttributeValue() sFeatureValue = " + sFeatureValue + ", CAAM code = " + currentCaam.getCode());
			if (currentCaam.getCode().equals(sFeatureValue)) {
				sValue = currentCaam.getName();
				if (sValue == null) {
					LOG.info("getSelectedAttributeValue() could not find value for FeatureValue " + sFeatureValue
							+ ", trying german value...");
				}
				break;
			}
		}
		if (sValue == null) {
			LOG.info("getSelectedAttributeValue() could not find value for FeatureValue " + sFeatureValue + "...");
		}
		//final long stopTime = System.currentTimeMillis();
		//final long lDiff = stopTime - startTime;
		//lTotal += lDiff;
		return sValue;
	}

	/**
	 * 
	 * @param wvm
	 * @param caam
	 * @return 
	 */
	private String getFeatureValueForCaam(final WeraVarianteModel wvm, final ClassAttributeAssignmentModel caam) {
		String sReturnValue = "0";
		if (caam == null) {
			LOG.warn("getFeatureValueForCaam(): caam = NULL for variante " + wvm.getCode() + "!");
			sReturnValue = "CAAM null (" + wvm.getCode() + ")";
		} else {
			final Feature variantFeature = this.m_classificationService.getFeature(wvm, caam);
			if (variantFeature != null) {
				final FeatureValue variantFeatureValue = variantFeature.getValue();
				if (variantFeatureValue != null) {

					String sFeatureValue = null;
					try {
						sFeatureValue = (String) variantFeatureValue.getValue();
					} catch (final ClassCastException cce) {
						LOG.warn("getFeatureValueForCaam():ClassCastException Exception boxing variantFeatureValue.getValue() into String!");
						sReturnValue = "ClassCastExc. boxing FV into String";
					}
					if (sFeatureValue != null) {
						final String sValue = getSelectedAttributeValue(sFeatureValue, caam);
						if (sValue != null) {
							sReturnValue = sValue;
						} else {
							LOG.warn("No attribute value found for FeatureValue " + sFeatureValue);
							sReturnValue = "No AV for FV " + sFeatureValue;
						}
					}
				} else {
					LOG.warn("no fValue " + variantFeature.getName());
					sReturnValue = "no fValue " + variantFeature.getName();
				}
			}
		}
		return sReturnValue;
	}

	/**
	 * 
	 * @param wvm
	 * @param ccm
	 * @return 
	 */
	public Map getLBHFromDimensionen(final WeraVarianteModel wvm, final ClassificationClassModel ccm) {
		final String sAttributeLaengeId = "_AA081f001";
		final String sAttributeBreiteId = "_AA040f001";
		final String sAttributeHoeheId = "_AA031f001";

		final ClassAttributeAssignmentModel caamL = getClassAttributeAssignment(ccm, sAttributeLaengeId);
		final ClassAttributeAssignmentModel caamB = getClassAttributeAssignment(ccm, sAttributeBreiteId);
		final ClassAttributeAssignmentModel caamH = getClassAttributeAssignment(ccm, sAttributeHoeheId);

		final Map rMap = new HashMap();

		rMap.put("L", getFeatureValueForCaam(wvm, caamL));
		rMap.put("B", getFeatureValueForCaam(wvm, caamB));
		rMap.put("H", getFeatureValueForCaam(wvm, caamH));

		return rMap;
	}

	/**
	 * 
	 * @param strKatalog
	 * @param strKatalogVersion
	 * @param strLanguage
	 * @param strPreisliste_brutto
	 * @param strPreisliste_staffel
	 * @return 
	 */
	public String ExportLBH(final String strKatalog, final String strKatalogVersion, final String strLanguage,
			final String strPreisliste_brutto, final String strPreisliste_staffel) {

		// --- Initialize
		final ClassificationClass oCategory = null;
		final Boolean bAktiv = null;
		final ArrayList aArrayResultFile = new ArrayList();
		HashMap hashProdukt = null;
		String strCode = "";
		final String[] aString = null;
		String strLine = "";
		final ArrayList colCA = new ArrayList();

		m_HeaderMap = new HashMap();
		m_ValueMap = new HashMap();

		// --- Debug
		LOG.info("ExportLBH => ExportLBH wurde gestartet...");
		LOG.info("ExportLBH => +Produktdaten werden initialisiert...");

		// --- Ausgabedatum
		final String strDatum = m_wm.InitOutputDatum();

		// --- BMECat - Support anforden
		m_oBMEcatXml = new BMEcatXml();

		// --- Sprache setzen
		SetLanguage("de");

		// --- Initialize II
		final String strOutputFile = strKatalogVersion + "_" + strDatum + ".txt";
		final String strOutputPath = "/lbh_data_" + strDatum;
		final String m_strControlOSPath = Config.getParameter("wera.homepath") + "/export/classified_data/control/";
		final String m_strDataOSPath = Config.getParameter("wera.homepath") + "/export/katalog/" + strKatalogVersion + "/lbh_data_"
				+ strDatum + "/data/";
		createDirectory(Config.getParameter("wera.homepath") + "/export/katalog/" + strKatalogVersion + strOutputPath);
		createDirectory(m_strDataOSPath);

		// --- Hole alle aktiven Produkte, unsorted
		m_weraCatalogVersion = getCatalogVersion(strKatalog, strKatalogVersion);
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion, false, "code");
		LOG.info("ExportLBH => +Anzahl products=" + products.size());

		// --- Schleife ï¿½ber alle Produkte
		Product weraproduct = null;
		final int iCnt = 0;

		// --- Deutsch
		// SetLanguage("de");
		final String sWrapSep = "\"" + m_CSV_SEP + "\"";

		aArrayResultFile.add("\"Code" + sWrapSep + "Laenge" + sWrapSep + "Breite" + sWrapSep + "Hoehe\"");

		final HashMap hCA_Result = new HashMap();

		final ModelService modelService = (ModelService) Registry.getGlobalApplicationContext().getBean("modelService");

		for (final Iterator it1 = products.iterator(); it1.hasNext() && iCnt < 10000;) {
			weraproduct = (Product) it1.next();
			if (!(weraproduct instanceof WeraVariante)) {
				LOG.info("ExportLBH(): Processing product " + weraproduct.getCode() + ", Type: "
						+ weraproduct.getClass().getSimpleName());
				try {
					// --- Kein Satz ( in Satz )
					if (weraproduct instanceof WeraProductSet || weraproduct instanceof WeraProductSetinSet) {
						continue;
					}
					// Kategorien und Klassifikationsklassen sammeln
					final Set<CategoryModel> setCategories = new HashSet();
					final Set<ClassificationClassModel> setClassificationClasses = new HashSet();
					final String strCategories = "";
					final String strClassificationClasses = "";
					final WeraProductModel wpm = modelService.get((WeraProduct) weraproduct);
					final Collection<CategoryModel> colCategories = wpm.getSupercategories();

					// Kategorien zusammenstellen
					for (final CategoryModel cm : colCategories) {
						final String sCatalogVersion = cm.getCatalogVersion().getVersion();

						if (sCatalogVersion.equals("weramaster")) {
							setCategories.add(cm);
						}
					}
					String sInvalidCategory = null;
					for (final CategoryModel cm : setCategories) {
						final String sCategoryId = cm.getCode();

						if (sCategoryId.equals("LKEYS") || sCategoryId.startsWith("ROCKO") || sCategoryId.equals("S7400SCHWARZ")
								|| sCategoryId.equals("S7400SCHWARZ") || sCategoryId.equals("ARCHIV")
								|| sCategoryId.equals("VERKAUFSHILFE") || sCategoryId.equals("SB_ARCHIV")
								|| sCategoryId.equals("TOOLMODULE") || sCategoryId.equals("SCHRAUBCHEMIE")) {
							sInvalidCategory = sCategoryId;
							break;
						}

					}
					if (sInvalidCategory != null) {
						LOG.info("ExportLBH(): Skipping product " + weraproduct.getCode() + ". Reason: Inactive category "
								+ sInvalidCategory);
						continue;
					}

					// --- Produkt ------------------------------------------------------------------
					// --- Initiallize allg. Produktdaten (reset)
					// --- Initiallize ClassificationAttributes => Artikeldaten
					colCA.clear();

					final ClassificationClassModel ccm = this.getDimensionenClass(wpm);

					// ---  Artikelnummern holen - Schleife ï¿½ber alle Artikel
					SetLanguage(strLanguage);
					final Collection colArticlesLang = _getAllArticleByProduct((WeraProduct) weraproduct);

					// SetLanguage("de");
					// final Collection colArticles = _getAllArticleByProduct((WeraProduct) weraproduct);
					final Collection colAllArticles = new ArrayList();
					colAllArticles.addAll(colArticlesLang);
					// colAllArticles.addAll(colArticles);

					final Set<String> setVariantCodes = new HashSet();

					if (colAllArticles.size() > 0) {
						for (final Iterator itArticel = colAllArticles.iterator(); itArticel.hasNext();) {

							// --- Hole ArtNr
							hashProdukt = (HashMap) itArticel.next();
							strCode = ((String) hashProdukt.get("code")).trim();
							if (strCode.length() == 11) {
								// 01234567890
								// 05123456001
								strCode = strCode.substring(2, 8);
							}

							if (setVariantCodes.contains(strCode)) {
								LOG.info("Code " + strCode + " already covered.");
								continue;
							}

							setVariantCodes.add(strCode);
							strCode = "C" + strCode;

							// --- Debug
							LOG.info("+Export (Artikel)=" + strCode);

							// --- Hole die Variante
							final WeraVariante oArticle = (WeraVariante) hashProdukt.get("artikel");

							// --- Prï¿½fe auf Aktiv
							// bAktiv = (Boolean) oArticle.getAttribute("aktiv");
							// if (bAktiv != null && bAktiv.booleanValue() == false)
							// {
							// LOG.info("++Skip Variante=" + oArticle.getCode());
							// 	continue;
							// }
							final WeraVarianteModel wvm = this.m_modelService.get(oArticle);

							if (ccm != null) {
								final Map hashLBH = getLBHFromDimensionen(wvm, ccm);
								String strLaenge = (String) hashLBH.get("L");
								String strBreite = (String) hashLBH.get("B");
								String strHoehe = (String) hashLBH.get("H");

								SetLanguage(strLanguage);
								if (oArticle.isAktiv() != null && oArticle.isAktiv().booleanValue()) {
									{
										final String strCodeComplete = wvm.getLagerNr() + wvm.getCode() + wvm.getVariantenNr();

										strLine = "\"" + strCodeComplete + sWrapSep + strLaenge + sWrapSep + strBreite + sWrapSep
												+ strHoehe + "\"";
										aArrayResultFile.add(strLine);
										LOG.info("++ adding variant " + strLanguage);
									}
									if (1 == 0) {
										SetLanguage("de");
										if (oArticle.isAktiv() != null && oArticle.isAktiv().booleanValue()) {
											final String strCodeComplete = wvm.getLagerNr() + wvm.getCode() + wvm.getVariantenNr();

											strLine = "\"" + strCodeComplete + sWrapSep + strLaenge + sWrapSep + strBreite + sWrapSep
													+ strHoehe + "\"";
											aArrayResultFile.add(strLine);
											LOG.info("++ adding variant DE");
										}
									}
								} else {
									LOG.warn("No DIM class found for product " + wpm.getCode());
									strLaenge = "Base not in DIMENSIONEN";
									strBreite = "Base not in DIMENSIONEN";
									strHoehe = "Base not in DIMENSIONEN";

									final String strCodeComplete = wvm.getLagerNr() + wvm.getCode() + wvm.getVariantenNr();
									strLine = "\"" + strCodeComplete + sWrapSep + strLaenge + sWrapSep + strBreite + sWrapSep + strHoehe
											+ "\"";
								}

							}

							// --- Produkt ------------------------------------------------------------------
						}

					}
				} catch (final Exception e) {
					e.printStackTrace();
				}
			}
		}
		// --- Datenfile schreiben
		LOG.info("ExportLBH => +Pfad=" + m_strDataOSPath);
		LOG.info("ExportLBH => +File=" + strOutputFile);
		final MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArrayEncoding(aArrayResultFile, m_strDataOSPath + strOutputFile, "UTF-8");

		return strOutputPath;

	}

	// -------------------------------------------------------------------------------------------------------------------------------------
	// --- Datenexport ExportClassifiedData ---------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * 
	 * @param strKatalog
	 * @param strKatalogVersion
	 * @param strLanguage
	 * @param strPreisliste_brutto
	 * @param strPreisliste_staffel
	 * @return 
	 */
	public String ExportClassifiedData(final String strKatalog, final String strKatalogVersion, final String strLanguage,
			final String strPreisliste_brutto, final String strPreisliste_staffel) {

		// --- Initialize
		ClassificationClass oCategory = null;
		Boolean bAktiv = null;
		final ArrayList aArrayResultFile = new ArrayList();
		HashMap hashProdukt = null;
		String strCode = "";
		final String[] aString = null;
		String strLine = "";
		final ArrayList colCA = new ArrayList();

		m_HeaderMap = new HashMap();
		m_ValueMap = new HashMap();

		// --- Debug
		LOG.info("ExportClassifiedData => ExportClassifiedData wurde gestartet...");
		LOG.info("ExportClassifiedData => +Produktdaten werden initialisiert...");

		// --- Ausgabedatum
		final String strDatum = m_wm.InitOutputDatum();

		// --- BMECat - Support anforden
		m_oBMEcatXml = new BMEcatXml();

		// --- Sprache setzen
		SetLanguage(strLanguage);

		// --- Kopfzeile
		final String strHeader1Prefix = "#H1" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + ""
				+ m_CSV_SEP;
		final String strHeader2Prefix = "#H2" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + ""
				+ m_CSV_SEP;

		final String strHeader3Prefix = "#H3" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + "" + m_CSV_SEP + ""
				+ m_CSV_SEP;
		final String strHeader4Prefix = "#H4" + m_CSV_SEP + "Artikelnummer" + m_CSV_SEP + "Code" + m_CSV_SEP + "Produkttyp"
				+ m_CSV_SEP + "Kategorie" + m_CSV_SEP + "Klassifikation" + m_CSV_SEP;

		// aArrayResultFile.add(strHeader1);
		// --- Initialize II
		final String strOutputFile = strKatalogVersion + "_" + strDatum + ".txt";
		final String strOutputPath = "/classified_data_" + strDatum;
		final String m_strControlOSPath = Config.getParameter("wera.homepath") + "/export/classified_data/control/";
		final String m_strDataOSPath = Config.getParameter("wera.homepath") + "/export/katalog/" + strKatalogVersion
				+ "/classified_data_" + strDatum + "/data/";
		createDirectory(Config.getParameter("wera.homepath") + "/export/katalog/" + strKatalogVersion + strOutputPath);
		createDirectory(m_strDataOSPath);

		// --- Hole alle aktiven Produkte, unsorted
		m_weraCatalogVersion = getCatalogVersion(strKatalog, strKatalogVersion);
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion, false, "code");
		LOG.info("ExportClassifiedData => +Anzahl products=" + products.size());

		// --- Schleife ï¿½ber alle Produkte
		Product weraproduct = null;
		final int iVariants = 0;
		final int iProducts = 0;
		final int iProductsSet = 0;
		int iCnt = 0;

		// --- Deutsch
		// SetLanguage("de");
		HashMap hCA_Result = new HashMap();

		final ModelService modelService = (ModelService) Registry.getGlobalApplicationContext().getBean("modelService");

		for (final Iterator it1 = products.iterator(); it1.hasNext() && iCnt < 10000;) {
			weraproduct = (Product) it1.next();
			if (!(weraproduct instanceof WeraVariante)) {
				LOG.info("ExportClassifiedData(): Processing product " + weraproduct.getCode() + ", Type: "
						+ weraproduct.getClass().getSimpleName());
				try {
					// --- Kein Satz ( in Satz )
					if (weraproduct instanceof WeraProductSet || weraproduct instanceof WeraProductSetinSet) {
						continue;
					}

					// Kategorien und Klassifikationsklassen sammeln
					final Set<CategoryModel> setCategories = new HashSet();
					final Set<ClassificationClassModel> setClassificationClasses = new HashSet();
					String strCategories = "";
					String strClassificationClasses = "";
					final WeraProductModel wpm = modelService.get((WeraProduct) weraproduct);
					final Collection<CategoryModel> colCategories = wpm.getSupercategories();

					// Kategorien und Klassifikationsklassen zusammenstellen
					for (final CategoryModel cm : colCategories) {
						final String sCatalogVersion = cm.getCatalogVersion().getVersion();

						if (cm instanceof ClassificationClassModel) {
							if (sCatalogVersion.equals("3.0") || sCatalogVersion.equals("werazusatz")) {
								setClassificationClasses.add((ClassificationClassModel) cm);
							}
						} else {
							if (sCatalogVersion.equals("weramaster")) {
								setCategories.add(cm);
							}
						}
					}
					String sInvalidCategory = null;
					for (final CategoryModel cm : setCategories) {
						final String sCategoryId = cm.getCode();

						if (sCategoryId.equals("LKEYS") || sCategoryId.startsWith("ROCKO") || sCategoryId.equals("S7400SCHWARZ")
								|| sCategoryId.equals("S7400SCHWARZ") || sCategoryId.equals("ARCHIV")
								|| sCategoryId.equals("VERKAUFSHILFE") || sCategoryId.equals("SB_ARCHIV")
								|| sCategoryId.equals("TOOLMODULE") || sCategoryId.equals("SCHRAUBCHEMIE")) {
							sInvalidCategory = sCategoryId;
							break;
						}

						if (strCategories.length() > 0) {
							strCategories += "|";
						}
						strCategories += cm.getCode();
					}
					if (sInvalidCategory != null) {
						LOG.info("ExportClassifiedData(): Skipping product " + weraproduct.getCode() + ". Reason: Inactive category "
								+ sInvalidCategory);
						continue;
					}
					for (final ClassificationClassModel ccm : setClassificationClasses) {
						if (strClassificationClasses.length() > 0) {
							strClassificationClasses += "|";
						}
						strClassificationClasses += ccm.getCode();
					}
					// --- Zï¿½hler
					iCnt++;

					// Header vorbereiten
					colCA.clear();
					oCategory = getClassificationAttributes(weraproduct, strLanguage, colCA);
					SetLanguage("de");
					hCA_Result = __getCAValues(weraproduct, colCA);
					SetLanguage(strLanguage);
					final Collection<String> colHeaderVisibilities = (Collection) hCA_Result.get("HeaderVisibilities");
					final Collection<String> colHeaderCodePaths = (Collection) hCA_Result.get("HeaderCodePaths");
					final Collection<String> colHeaderCodes = (Collection) hCA_Result.get("HeaderCodes");
					final Collection<String> colHeaders = (Collection) hCA_Result.get("Header");
					String strHeader1 = strHeader1Prefix;
					String strHeader2 = strHeader2Prefix;
					String strHeader3 = strHeader3Prefix;
					String strHeader4 = strHeader4Prefix;

					// strLine += ExportClassification(weraproduct, colCA, oCategory);
					for (final String strHeader : colHeaderVisibilities) {
						strHeader1 += (strHeader + m_CSV_SEP);
					}
					for (final String strHeader : colHeaderCodePaths) {
						strHeader2 += (strHeader + m_CSV_SEP);
					}

					for (final String strHeader : colHeaderCodes) {
						strHeader3 += (strHeader + m_CSV_SEP);
					}

					for (final String strHeader : colHeaders) {
						strHeader4 += (strHeader + m_CSV_SEP);
					}
					aArrayResultFile.add(strHeader1);
					aArrayResultFile.add(strHeader2);
					aArrayResultFile.add(strHeader3);
					aArrayResultFile.add(strHeader4);

					boolean bOutput = false;

					// --- Satz
					if (weraproduct instanceof WeraProductSet) {

						// --- Satz ---------------------------------------------------------------------
						strCode = (String) weraproduct.getAttribute("artnr");
						strCode = "C" + strCode;

						// --- Debug
						LOG.info("+Export (SATZ)=" + strCode);

						// --- Initiallize ClassificationAttributes => Artikeldaten
						colCA.clear();
						oCategory = getClassificationAttributes(weraproduct, "de", colCA);

						strLine = m_CSV_SEP
								+ (String) weraproduct.getAttribute("code") // ERSATZ_ARTNUM
								+ m_CSV_SEP + strCode + "S" + m_CSV_SEP + strCategories + m_CSV_SEP + strClassificationClasses
								+ m_CSV_SEP;

						aArrayResultFile.add(strLine);
						bOutput = true;

						// --- Satz ---------------------------------------------------------------------
					} else {
						// --- Produkt ------------------------------------------------------------------

						// --- Initiallize allg. Produktdaten (reset)
						// --- Initiallize ClassificationAttributes => Artikeldaten
						colCA.clear();
						oCategory = getClassificationAttributes(weraproduct, "de", colCA);

						// ---  Artikelnummern holen - Schleife ï¿½ber alle Artikel
						final Collection colArticles = _getAllArticleByProduct((WeraProduct) weraproduct);
						if (colArticles != null && colArticles.size() > 0) {
							for (final Iterator itArticel = colArticles.iterator(); itArticel.hasNext();) {

								// --- Hole ArtNr
								hashProdukt = (HashMap) itArticel.next();
								strCode = ((String) hashProdukt.get("code")).trim();
								if (strCode.length() == 11) {
									// 01234567890
									// 05123456001
									strCode = strCode.substring(2, 8);
								}
								strCode = "C" + strCode;

								// --- Debug
								LOG.info("+Export (Artikel)=" + strCode);

								// --- Hole die Variante
								final WeraVariante oArticle = (WeraVariante) hashProdukt.get("artikel");

								SetLanguage("de");
								final Boolean bAktivDE = (Boolean) oArticle.getAttribute("aktiv");
								if (bAktivDE != null && bAktivDE.booleanValue()) {
									// --- in DE aktive Varianten ï¿½berspringen
									LOG.info("+Artikel ueberspringen, weil in DE aktiv" + strCode);
									SetLanguage(strLanguage);
									continue;
								}

								SetLanguage(strLanguage);
								// --- Prï¿½fe auf Aktiv
								bAktiv = (Boolean) oArticle.getAttribute("aktiv");
								if (bAktiv != null && bAktiv.booleanValue() == false) {
									LOG.info("++Skip Variante=" + oArticle.getCode());
									continue;
								}

								strLine = m_CSV_SEP + (String) weraproduct.getAttribute("code") + m_CSV_SEP + strCode + m_CSV_SEP + "A"
										+ m_CSV_SEP + strCategories + m_CSV_SEP + strClassificationClasses + m_CSV_SEP;

								// --- Classification data -------------------------------------------------------
								// strLine += ExportClassification(oArticle, colCA, oCategory);
								// --- Classification data -------------------------------------------------------
								hCA_Result = __getCAValues(oArticle, colCA);
								final Collection<String> colValues = (Collection) hCA_Result.get("Values");
								for (final String strValue : colValues) {
									strLine += (strValue + m_CSV_SEP);
								}

								// --- Preise und Verpackungs Einheiten --------------------------------------------------------------------
								// strLine += ExportWT_Logistik(oArticle, strPreisliste_brutto, strPreisliste_staffel);
								// --- Preise und Verpackungs Einheiten --------------------------------------------------------------------
								aArrayResultFile.add(strLine);

							}
							bOutput = true;
							// --- Produkt ------------------------------------------------------------------
						}
					}

					if (!bOutput) {
						// Dummy Zeile ausgeben, falls keine Produktdaten anfallen wg. Aktiv-Flags 
						strLine = "#" + m_CSV_SEP + "(nicht aktiv)" + m_CSV_SEP + m_CSV_SEP + m_CSV_SEP + m_CSV_SEP + m_CSV_SEP
								+ m_CSV_SEP;
						aArrayResultFile.add(strLine);

					}

				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

			//if ( iCnt > 100 ) {
			//break;
			//}
		}

		// --- Zusammenfassung
		LOG.info("ExportClassifiedData => +Results...");
		LOG.info("ExportClassifiedData => +products.size()=" + products.size());
		LOG.info("ExportClassifiedData => +iVariants=" + iVariants);
		LOG.info("ExportClassifiedData => +iProducts=" + iProducts);
		LOG.info("ExportClassifiedData => +iProductsSet=" + iProductsSet);

		// --- Datum ermitteln
		m_strDatum = strDatum;

		// --- Datenfile schreiben
		LOG.info("ExportClassifiedData => +Pfad=" + m_strDataOSPath);
		LOG.info("ExportClassifiedData => +File=" + strOutputFile);
		final MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArrayEncoding(aArrayResultFile, m_strDataOSPath + strOutputFile, "UTF-8");

		return strOutputPath;
	}

	/**
	 * Initiallize Artikeldaten (Werte - Tabelle), Rückgabe Nummer der Klasse
	 * 
	 * @param weraarticle
	 * @param colCA
	 * @return 
	 */
	private HashMap __getCAValues(final Product weraarticle, final ArrayList colCA) {
		// --- Initialize
		final HashMap hCA_Result = new HashMap();
		final ArrayList colValue = new ArrayList();
		String strValue = "";

		final Collection colHeaders = new ArrayList();
		final Collection colHeaderCodes = new ArrayList();
		final Collection colHeaderCodePaths = new ArrayList();
		final Collection colHeaderVisibilities = new ArrayList();
		final Collection colValues = new ArrayList();

		HashMap hashCA = null;
		ClassificationAttribute oClassificationAttribute = null;
		ClassAttributeAssignment oClassattributeAssignment = null;
		ClassAttributeAssignmentModel oCaam = null;

		Category categoryByCa = null;
		Category categoryByCaResult = null;
		int iCount = 0;

		final ModelService modelService = (ModelService) Registry.getGlobalApplicationContext().getBean("modelService");

		// --- Schleife ï¿½ber alle Werte
		for (final Iterator itCA = colCA.iterator(); itCA.hasNext();) {
			// --- Initialize
			iCount++;
			hashCA = (HashMap) itCA.next();
			oClassificationAttribute = (ClassificationAttribute) hashCA.get("classificationattribute");
			oClassattributeAssignment = (ClassAttributeAssignment) hashCA.get("classattributeassignment");

			oCaam = modelService.get(oClassattributeAssignment);
			final String sClassName = oCaam.getClassificationClass().getCode();
			final String sClassificationVersionName = oCaam.getClassificationClass().getCatalogVersion().getVersion();
			final String sCatalogName = oCaam.getClassificationClass().getCatalogVersion().getCatalog().getId();
			final String sPath = sCatalogName + "/" + sClassificationVersionName + "/" + sClassName;

			categoryByCa = (Category) hashCA.get("category");
			if (categoryByCa != null && categoryByCaResult != null) {
				categoryByCaResult = categoryByCa;
			}

			// --- Bezeichnung
			colHeaders.add(oClassificationAttribute.getName());
			colHeaderCodePaths.add(sPath);
			colHeaderCodes.add(oClassificationAttribute.getCode());
			colHeaderVisibilities.add(hashCA.get("visibility"));

			// --- Wert
			colValue.clear();
			colValue.addAll(m_oBMEcatXml.colGetCAValue(weraarticle, oClassattributeAssignment));
			strValue = "";
			if (colValue.size() > 0) {
				if (strValue.toLowerCase().indexOf("zoll") != -1 || colValue.get(0).toString().indexOf("/") != -1
						|| colValue.get(0).toString().indexOf("\"") != -1) {
					strValue += "'" + colValue.get(0) + "\"'";
				} else {
					strValue += (String) colValue.get(0);
				}
			}
			colValues.add(strValue);
		}

		if (categoryByCaResult != null) {
			hCA_Result.put("PClass", categoryByCa.getCode());
		} else {
			hCA_Result.put("PClass", "");
		}
		hCA_Result.put("Header", colHeaders);
		hCA_Result.put("HeaderCodes", colHeaderCodes);
		hCA_Result.put("HeaderCodePaths", colHeaderCodePaths);
		hCA_Result.put("HeaderVisibilities", colHeaderVisibilities);
		hCA_Result.put("Values", colValues);

		return hCA_Result;
	}

	/**
	 * 
	 * @param sAttribute
	 * @return 
	 */
	private boolean isAttributeDescribingProductDimensions(final String sAttribute) {
		final List<String> listOfMagicWords = Arrays.asList("hï¿½he", "grï¿½ï¿½e", "grï¿½sse", "lï¿½nge", "breite", "durchmesser");
		final String sLowerCaseAttribute = sAttribute.toLowerCase();
		boolean bIsDescribingDimensions = false;
		for (final String sWord : listOfMagicWords) {
			if (sLowerCaseAttribute.contains(sWord)) {
				bIsDescribingDimensions = true;
				break;
			}
		}
		return bIsDescribingDimensions;
	}

	/**
	 * 
	 * @param weraproduct
	 * @param strLanguage
	 * @param colCA
	 * @return 
	 */
	private ClassificationClass getClassificationAttributes(final Product weraproduct, final String strLanguage,
			final Collection colCA) {

		// --- Initialize
		ClassAttributeAssignment oClassAttributeAssignment = null;
		ClassificationAttribute oClassificationAttribute = null;
		HashMap hashCategory2CA = new HashMap();
		final Collection<HashMap> colCAsForVariants = new ArrayList();
		final Collection<HashMap> colCAsForBaseproducts = new ArrayList();
		final Collection<HashMap> colCAsForBoth = new ArrayList();

		// --- Initialize
		hashCategory2CA.clear();

		// ---- Initialize Sichtbarkeit (Attribute)
		final EnumerationManager em = JaloSession.getCurrentSession().getEnumerationManager();
		EnumerationType et = null;
		final EnumerationValue ev = null;
		et = em.getEnumerationType("ClassificationAttributeVisibilityEnum");
		final EnumerationValue evVISIBLE = em.getEnumerationValue(et, "VISIBLE");
		final EnumerationValue evVISIBLE_IN_BASE = em.getEnumerationValue(et, "VISIBLE_IN_BASE");
		final EnumerationValue evVISIBLE_IN_VARIANT = em.getEnumerationValue(et, "VISIBLE_IN_VARIANT");

		// --- Schleife ï¿½ber alle Attribute
		final Collection ouputcontrols = (Collection) getAttribute(weraproduct, "outputcontrols");
		Outputcontrol outputcontrol = null;
		EnumerationValue evVisibility = null;
		ClassificationClass oCategory = null;

		try {

			// --- Holen der Klassifizierenden Kategorien
			//Collection categories = getCategoriesByProduct(
			//		(WeraProduct) weraproduct, strLanguage);
			SetLanguage("de");
			final Collection categories = _getAllCategoriesByProduct((WeraProduct) weraproduct);

			// --- Initialisieren der Merkmale
			if (categories != null && categories.size() > 0) {
				// --- Schleife ï¿½ber alle Kategorien
				for (final Iterator it1 = categories.iterator(); it1.hasNext();) {
					// --- Hole Category
					oCategory = (ClassificationClass) it1.next();

					// --- Initialisiere alle Attribute
					///Collection colAttr = getClassificationAttributes(oCategory);
					final Collection<ClassAttributeAssignment> colAttr = m_weraclassificationhelper
							.getClassificationAttributeAssignments(oCategory);

					if (colAttr != null && colAttr.size() > 0) {
						for (final Iterator it2 = colAttr.iterator(); it2.hasNext();) {

							// --- Hole das CA
							oClassAttributeAssignment = (ClassAttributeAssignment) it2.next();
							oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();

							// --- Nur Attribute ï¿½bernehmen, die Produktdimensionen beschreiben und 
							// --- NICHT Attribute aus der Weraportale Klasse sind.
							final String sGermanName = oClassificationAttribute.getName();
							final boolean bIsDim = isAttributeDescribingProductDimensions(sGermanName);

							if (bIsDim && !oClassAttributeAssignment.getSystemVersion().getVersion().equals("weraportale")) {

								// --- Hole Ausgabesteuerung
								outputcontrol = (Outputcontrol) WeraManager.checkContaining(ouputcontrols, "code",
										oClassificationAttribute.getCode());
								if (outputcontrol != null) {
									evVisibility = (EnumerationValue) getAttribute(outputcontrol, "visibility");
								} else {
									evVisibility = null;
								}

								// --- Nur die benï¿½tigten Attribute nehmen
								if (evVisibility != null
										&& (evVisibility.equals(evVISIBLE_IN_BASE) || evVisibility.equals(evVISIBLE_IN_VARIANT) || evVisibility
										.equals(evVISIBLE))) {

									hashCategory2CA = new HashMap();
									hashCategory2CA.put("category", oCategory);
									hashCategory2CA.put("classificationattribute", oClassificationAttribute);
									hashCategory2CA.put("classattributeassignment", oClassAttributeAssignment);
									hashCategory2CA.put("outputcontrol", outputcontrol);

									if (evVisibility.equals(evVISIBLE_IN_BASE)) {
										hashCategory2CA.put("visibility", "P");
										colCAsForBaseproducts.add(hashCategory2CA);
									} else {
										if (evVisibility.equals(evVISIBLE)) {
											hashCategory2CA.put("visibility", "*");
											colCAsForBoth.add(hashCategory2CA);
										} else {
											hashCategory2CA.put("visibility", "V");
											colCAsForVariants.add(hashCategory2CA);
										}
									}
								} // --- if (evVisibility == evVISIBLE_IN_VARIANT ...
							}

						} // --- for (Iterator it2 = colAttr.iterator();
						// it2.hasNext();) ...

					} // --- if (colAttr != null && colAttr.size() > 0) {

				} // --- for (Iterator it1 = categories.iterator();

			} // --- if (categories != null && categories.size() > 0) {

		} catch (final Exception e) {
			e.printStackTrace();
		}
		SetLanguage(strLanguage);
		colCA.addAll(colCAsForVariants);
		colCA.addAll(colCAsForBaseproducts);
		colCA.addAll(colCAsForBoth);
		return oCategory;
	}

	/**
	 * 
	 * @param s11DigitCode
	 * @param sType
	 * @param sBasisCode
	 * @param lProductToProfiClass
	 * @param lProductToWeraClass
	 * @param hCodeToName
	 * @param sSep
	 * @return 
	 */
	private String _createLineForArticlesToClasses(final String s11DigitCode, final String sType, final String sBasisCode,
			final List<String> lProductToProfiClass, final List<String> lProductToWeraClass, final Map<String, String> hCodeToName,
			final String sSep) {

		String sLine = s11DigitCode + sSep + sType + sSep + sBasisCode;
		for (int i = 0; i < 4; i++) {
			String sPC = null;
			String[] aPCClassOnly = null;
			try {
				sPC = (i < 1) ? lProductToProfiClass.get(i) : lProductToWeraClass.get(i - 1);
			} catch (final IndexOutOfBoundsException ioobe) {
				sPC = "";
			}
			aPCClassOnly = sPC.split("\\/");
			String sPCClassOnly = "";
			if (aPCClassOnly.length == 2) {
				sPCClassOnly = aPCClassOnly[1];
			}

			sLine += (sSep + sPCClassOnly);

			final String sClassName = hCodeToName.get(sPC);
			sLine += (sSep + ((sClassName == null) ? "" : sClassName));

		}
		return sLine;
	}

	/**
	 * 
	 * @param strKatalog
	 * @param strKatalogVersion
	 * @param strLanguage
	 * @return 
	 */
	public String ArticlesToClasses(final String strKatalog, final String strKatalogVersion, final String strLanguage) {

		final String sRval = "";
		final String sSep = "\t";
		final String sFileExtension = ".txt";

		// --- Sprache setzen
		SetLanguage(strLanguage);

		// --- Hole alle aktiven Produkte, unsorted
		m_weraCatalogVersion = getCatalogVersion("weracatalog", "weramaster");
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion, false, "code");
		LOG.info("ArticlesToClasses => +Anzahl products=" + products.size());

		final List<String> lProductToProfiClass = new ArrayList();
		final List<String> lProductToWeraClass = new ArrayList();
		final List<String> lProductToWeraAAClass = new ArrayList();
		final List<String> lProductToWeraUnderscoreClass = new ArrayList();
		final List<String> lProductToWeraZusatzClass = new ArrayList();

		final Map<String, String> hCodeToName = new HashMap();

		final String strOutputFile = "/home/hybris/export/" + strKatalog + "_" + strKatalogVersion + "_" + strLanguage
				+ sFileExtension;
		FileWriter fw;
		try {
			fw = new FileWriter(strOutputFile);

			fw.write("Artikelnummer" + sSep + "Typ" + sSep + "Basisprodukt" + sSep + "ProfiClass" + sSep + "ProfiClass Name" + sSep
					+ "WeraClass 1" + sSep + "WeraClass 1 Name" + sSep + "WeraClass 2" + sSep + "WeraClass 2 Name" + sSep
					+ "WeraClass 3" + sSep + "WeraClass 3 Name\n");

			// --- Schleife ueber alle Produkte
			Product weraproduct = null;
			for (final Iterator it1 = products.iterator(); it1.hasNext();) {
				weraproduct = (Product) it1.next();

				String s11DigitCode = null;
				String sArticleLine = null;

				String sVariantenNr = null;
				String sLagerNr = null;

				lProductToProfiClass.clear();
				lProductToWeraClass.clear();

				lProductToWeraAAClass.clear();
				lProductToWeraUnderscoreClass.clear();
				lProductToWeraZusatzClass.clear();

				if (!(weraproduct instanceof VariantProduct)) {
					// --- Skip SiS products
					if (weraproduct instanceof WeraProductSetinSet) {
						continue;
					}

					final WeraProductModel wpm = this.m_modelService.get(weraproduct);

					boolean bProductIsInRequestedCatalogVersion = false;
					final Collection<CategoryModel> colCategories = wpm.getSupercategories();
					for (final CategoryModel cm : colCategories) {
						if (cm.getCatalogVersion().getVersion().equals(strKatalogVersion)
								&& cm.getCatalogVersion().getCatalog().getId().equals(strKatalog)) {
							bProductIsInRequestedCatalogVersion = true;
							break;
						}
					}

					if (bProductIsInRequestedCatalogVersion) {

						final List<ClassificationClassModel> listClasses = wpm.getClassificationClasses();
						for (final ClassificationClassModel ccm : listClasses) {
							final String sClassCode = ccm.getCode();
							if (!(sClassCode.equals("PORTALATTRIBUTES") || sClassCode.equals("DIMENSIONEN"))) {
								// todo

								final ClassificationSystemVersionModel csvm = ccm.getCatalogVersion();
								final String sClassificationSystemName = csvm.getVersion();
								final String sClassName = ccm.getName();
								hCodeToName.put(sClassificationSystemName + "/" + sClassCode, sClassName);

								// proficlass 3.0
								if (sClassificationSystemName.equals("3.0")) {
									lProductToProfiClass.add("3.0/" + sClassCode);
								}
								// werazusatz
								if (sClassificationSystemName.equals("werazusatz")) {
									if (sClassCode.startsWith("_")) {
										lProductToWeraUnderscoreClass.add("werazusatz/" + sClassCode);
									} else {
										if (sClassCode.startsWith("AA")) {
											lProductToWeraAAClass.add("werazusatz/" + sClassCode);
										} else {
											lProductToWeraZusatzClass.add("werazusatz/" + sClassCode);
										}
									}
								}
							}
						}

						lProductToWeraClass.addAll(lProductToWeraAAClass);
						lProductToWeraClass.addAll(lProductToWeraUnderscoreClass);
						lProductToWeraClass.addAll(lProductToWeraZusatzClass);

						// sets
						if (weraproduct instanceof WeraProductSet) {
							final WeraProductSetModel wpsm = (WeraProductSetModel) this.m_modelService.get(weraproduct);
							sVariantenNr = wpsm.getVariantenNr();
							sLagerNr = wpsm.getLagerNr();
							if (sVariantenNr != null && sLagerNr != null && sVariantenNr.length() == 3 && sLagerNr.length() == 2) {
								s11DigitCode = sLagerNr + wpsm.getArtnr() + sVariantenNr;
								sArticleLine = _createLineForArticlesToClasses(s11DigitCode, "Satz", wpsm.getCode(),
										lProductToProfiClass, lProductToWeraClass, hCodeToName, sSep);
								fw.write(sArticleLine + "\n");
							}
						} else {

							// products: fetch the articles
							final Collection<VariantProductModel> colVariants = wpm.getVariants();
							if (colVariants != null && colVariants.size() > 0) {
								for (final VariantProductModel vpm : colVariants) {
									final WeraVarianteModel wvm = (WeraVarianteModel) vpm;

									// only consider variants active for the chosen language
									if (wvm.getAktiv().booleanValue()) {
										sVariantenNr = wvm.getVariantenNr();
										sLagerNr = wvm.getLagerNr();
										if (sVariantenNr != null && sLagerNr != null && sVariantenNr.length() == 3
												&& sLagerNr.length() == 2) {
											s11DigitCode = sLagerNr + wvm.getCode() + sVariantenNr;
											sArticleLine = _createLineForArticlesToClasses(s11DigitCode, "Artikel", wpm.getCode(),
													lProductToProfiClass, lProductToWeraClass, hCodeToName, sSep);
											fw.write(sArticleLine + "\n");
										}
									}
								}

							}

						}

					} else {
						LOG.info("ArticlesToClasses(): product " + wpm.getCode() + " not in requested catalogversion, skipping.");
					}
				}
			}
			fw.close();
		} catch (final IOException e) {
			// YTODO Auto-generated catch block
			e.printStackTrace();
		}

		return sRval;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------
	// --- Datenexport Wuppertools ---------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------------------------------
	/**
	 * Wuppertools
	 * 
	 * @param strKatalog
	 * @param strKatalogVersion
	 * @param strLanguage
	 * @param strPreisliste_brutto
	 * @param strPreisliste_staffel
	 * @return 
	 */
	public String ExportWuppertools(final String strKatalog, final String strKatalogVersion, final String strLanguage,
			final String strPreisliste_brutto, final String strPreisliste_staffel) {

		// --- Initialize
		ClassificationClass oCategory = null;
		Boolean bAktiv = null;
		final ArrayList aArrayResultFile = new ArrayList();
		HashMap hashProdukt = null;
		String strCode = "";
		final String[] aString = null;
		String strLine = "";
		String strProduktData = "";
		final ArrayList colCA = new ArrayList();
		String strDescription;
		String strBestueckung;
		m_HeaderMap = new HashMap();
		m_ValueMap = new HashMap();

		// --- Debug
		LOG.info("WuppertoolsCsv => Datenexport Wuppertools wurde gestartet...");
		LOG.info("WuppertoolsCsv => +Produktdaten werden initialisiert...");

		// --- Ausgabedatum
		final String strDatum = m_wm.InitOutputDatum();

		// --- BMECat - Support anforden
		m_oBMEcatXml = new BMEcatXml();

		// --- Sprache setzen
		SetLanguage(strLanguage);

		// --- Kopfzeile
		String strHeader1 = "BARCODE\tERSATZ_ARTNUM\tHERST_ARTNUM\tARTNUM\tKURZNAME_DE\tSHOP_KURZTEXT_DE\tSHOP_LANGTEXT_DE\tKURZNAME_EN\tSHOP_KURZTEXT_EN\tSHOP_LANGTEXT EN\tKURZNAME ES\tSHOP_KURZTEXT ES\tSHOP_LANGTEXT ES\tUSERFELD_01\tUSERFELD_02\tUSERFELD_03\tUSERFELD_04\tUSERFELD_05\tUSERFELD_06\tUSERFELD_07\tUSERFELD_08\tUSERFELD_09\tUSERFELD_10\tVPE\tVK5B";
		strHeader1 += "\tMengeSt1\tPreisSt1\tMengeSt2\tPreisSt2\tMengeSt3\tPreisSt3";
		strHeader1 += "\tUrsprungsland\tZolltarifNr\tGewicht\tGewichtVE\tGewichtEinheit\tpackm_gewichteh\tpackm_laenge\tvpackm_hoehe\tpackm_breite\tpackm_laengen_einheit\tProduktbild";
		aArrayResultFile.add(strHeader1);

		// --- Initialize II
		final String strOutputFile = "wuppertools_" + strDatum + ".txt";
		final String strOutputPath = "/wuppertools_" + strDatum;
		final String m_strControlOSPath = Config.getParameter("wera.homepath") + "/export/wuppertools/control/";
		final String m_strDataOSPath = Config.getParameter("wera.homepath") + "/export/katalog/" + strKatalogVersion
				+ "/wuppertools_" + strDatum + "/data/";
		createDirectory(Config.getParameter("wera.homepath") + "/export/katalog/" + strKatalogVersion + strOutputPath);
		createDirectory(m_strDataOSPath);

		// --- Hole alle aktiven Produkte, unsorted
		m_weraCatalogVersion = getCatalogVersion(strKatalog, strKatalogVersion);
		m_proficlassCatalogVersion = getClassificationSystemVersion("proficlass", "pc-3.0");
		final Collection products = WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion, false, "code");
		LOG.info("wuppertoolsCsv => +Anzahl products=" + products.size());

		// --- Schleife ï¿½ber alle Produkte
		Product weraproduct = null;
		final int iVariants = 0;
		final int iProducts = 0;
		final int iProductsSet = 0;
		int iCnt = 0;
		for (final Iterator it1 = products.iterator(); it1.hasNext();) {
			weraproduct = (Product) it1.next();

			// --- Deutsch
			SetLanguage("de");

			if (!(weraproduct instanceof WeraVariante)) {
				try {
					// --- Kein Satz in Satz
					if (weraproduct instanceof WeraProductSetinSet) {
						continue;
					}

					// --- Pï¿½rfe auf Aktiv
					bAktiv = (Boolean) weraproduct.getAttribute("aktiv");
					if (bAktiv != null && bAktiv.booleanValue() == false) {
						LOG.info("++Skip Produkt=" + weraproduct.getCode());
						continue;
					}

					// --- Zï¿½hler
					iCnt++;

					// --- Bildname1
					String strProduktImage = ((WeraProduct) weraproduct).normalizeFilenameForImageLookup();
					strProduktImage = m_oBMEcatXml._NormalizeImageName(strProduktImage) + ".jpg";

					// --- Satz
					if (weraproduct instanceof WeraProductSet) {

						// --- Satz ---------------------------------------------------------------------
						strCode = (String) weraproduct.getAttribute("lagerNr") + (String) weraproduct.getAttribute("artnr")
								+ (String) weraproduct.getLocalizedProperty("variantenNr");

						// --- Debug
						LOG.info("+Export (SATZ)=" + strCode);

						// --- Initiallize ClassificationAttributes => Artikeldaten
						colCA.clear();
						oCategory = _EP_getClassificationAttributes(weraproduct, "de", colCA);

						strLine = weraproduct.getAttribute("ean") // BARCODE 
								+ "\t" + (String) weraproduct.getAttribute("code") // ERSATZ_ARTNUM
								+ "\t" + strCode // HERST_ARTNUM
								+ "\t" + "WERA" + strCode; // ARTNUM

						// --- Bestï¿½ckung Satz -------------------------------------------------------------------------------------
						strBestueckung = __GetSatzBestueckung(weraproduct);
						// --- Bestï¿½ckung Satz -------------------------------------------------------------------------------------

						// --- Deutsch
						SetLanguage("de");
						strDescription = (String) weraproduct.getAttribute("description1");
						if (strDescription == null) {
							strDescription = "";
						} else {
							strDescription = strDescription.replaceAll("\n", "<br>").replaceAll("\r", "").replaceAll("\t", "");
						}
						strLine += "\t" + "WERA " + weraproduct.getAttribute("name") // KURZNAME_DE
								+ "\t" + "" // SHOP_KURZTEXT_DE
								+ "\t" + strDescription + "<br>" + strBestueckung; // SHOP_LANGTEXT_DE 

						// --- Englisch
						SetLanguage("en");
						strDescription = (String) weraproduct.getAttribute("description1");
						if (strDescription == null) {
							strDescription = "";
						} else {
							strDescription = strDescription.replaceAll("\n", "<br>").replaceAll("\r", "").replaceAll("\t", "");
						}
						strLine += "\t" + "WERA " + weraproduct.getAttribute("name") // KURZNAME_EN
								+ "\t" + "" // SHOP_KURZTEXT_EN
								+ "\t" + strDescription + "<br>" + strBestueckung; // SHOP_LANGTEXT_EN

						// --- Spanish
						SetLanguage("es");
						strDescription = (String) weraproduct.getAttribute("description1");
						if (strDescription == null) {
							strDescription = "";
						} else {
							strDescription = strDescription.replaceAll("\n", "<br>").replaceAll("\r", "").replaceAll("\t", "");
						}
						strLine += "\t" + "WERA " + weraproduct.getAttribute("name") // KURZNAME_ES
								+ "\t" + "" // SHOP_KURZTEXT_ES
								+ "\t" + strDescription + "<br>" + strBestueckung; // SHOP_LANGTEXT_ES

						// --- USERFELDER (Classifizierung / Tabellendaten ) -------------------------------------------------------
						strLine += ExportKlassifizierung(weraproduct, colCA, oCategory);
						// --- USERFELDER (Classifizierung / Tabellendaten ) -------------------------------------------------------

						// --- Preise und Verpackungs Einheiten --------------------------------------------------------------------
						strLine += ExportWT_Logistik(weraproduct, strPreisliste_brutto, strPreisliste_staffel);
						// --- Preise und Verpackungs Einheiten --------------------------------------------------------------------

						// --- Bildname1
						strLine += "\t" + strProduktImage;

						aArrayResultFile.add(strLine);

						// --- Satz ---------------------------------------------------------------------
					} else {
						// --- Produkt ------------------------------------------------------------------

						// --- Initiallize allg. Produktdaten (reset)
						strProduktData = "";

						// --- Initiallize ClassificationAttributes => Artikeldaten
						colCA.clear();
						oCategory = _EP_getClassificationAttributes(weraproduct, "de", colCA);

						// ---  Artikelnummern holen - Schleife ï¿½ber alle Artikel
						final Collection colArticles = _getAllArticleByProduct((WeraProduct) weraproduct);
						if (colArticles != null && colArticles.size() > 0) {
							for (final Iterator itArticel = colArticles.iterator(); itArticel.hasNext();) {

								// --- Hole ArtNr
								hashProdukt = (HashMap) itArticel.next();
								strCode = ((String) hashProdukt.get("code")).trim();

								// --- Debug
								LOG.info("+Export (Artikel)=" + strCode);

								// --- Hole die Variante
								final WeraVariante oArticle = (WeraVariante) hashProdukt.get("artikel");

								// --- Pï¿½rfe auf Aktiv
								bAktiv = (Boolean) oArticle.getAttribute("aktiv");
								if (bAktiv != null && bAktiv.booleanValue() == false) {
									LOG.info("++Skip Variante=" + oArticle.getCode());
									continue;
								}

								strLine = oArticle.getAttribute("ean") // BARCODE 
										+ "\t" + (String) weraproduct.getAttribute("code") // ERSATZ_ARTNUM
										+ "\t" + strCode // HERST_ARTNUM
										+ "\t" + "WERA" + strCode; // ARTNUM

								// --- Deutsch
								strDescription = __getattributeString(weraproduct, "description1", "de");
								strDescription = strDescription.replaceAll("\n", "<br>").replaceAll("\r", "").replaceAll("\t", "");
								strLine += "\t" + "WERA " + __getattributeString(weraproduct, "name", "de") // KURZNAME_DE
										+ "\t" + "" // SHOP_KURZTEXT_DE
										+ "\t" + strDescription; // SHOP_LANGTEXT_DE

								// --- Englisch
								strDescription = __getattributeString(weraproduct, "description1", "en");
								strDescription = strDescription.replaceAll("\n", "<br>").replaceAll("\r", "").replaceAll("\t", "");
								strLine += "\t" + "WERA " + __getattributeString(weraproduct, "name", "en") // KURZNAME_EN
										+ "\t" + "" // SHOP_KURZTEXT_EN
										+ "\t" + strDescription; // SHOP_LANGTEXT_EN

								// --- Spanish
								strDescription = __getattributeString(weraproduct, "description1", "es");
								strDescription = strDescription.replaceAll("\n", "<br>").replaceAll("\r", "").replaceAll("\t", "");
								strLine += "\t" + "WERA " + __getattributeString(weraproduct, "name", "es") // KURZNAME_ES
										+ "\t" + "" // SHOP_KURZTEXT_ES
										+ "\t" + strDescription; // SHOP_LANGTEXT_ES

								// --- USERFELDER (Classifizierung / Tabellendaten ) -------------------------------------------------------
								strLine += ExportKlassifizierung(oArticle, colCA, oCategory);
								// --- USERFELDER (Classifizierung / Tabellendaten ) -------------------------------------------------------

								// --- Preise und Verpackungs Einheiten --------------------------------------------------------------------
								strLine += ExportWT_Logistik(oArticle, strPreisliste_brutto, strPreisliste_staffel);
								// --- Preise und Verpackungs Einheiten --------------------------------------------------------------------

								// --- Bildname1
								strLine += "\t" + strProduktImage;

								aArrayResultFile.add(strLine);

							}
							// --- Produkt ------------------------------------------------------------------
						}
					}

				} catch (final Exception e) {
					e.printStackTrace();
				}
			}

			//if ( iCnt > 100 ) {
			//break;
			//}
		}

		// --- Zusammenfassung
		LOG.info("Wuppertools => +Results...");
		LOG.info("Wuppertools => +products.size()=" + products.size());
		LOG.info("Wuppertools => +iVariants=" + iVariants);
		LOG.info("Wuppertools => +iProducts=" + iProducts);
		LOG.info("Wuppertools => +iProductsSet=" + iProductsSet);

		// --- Datum ermitteln
		m_strDatum = strDatum;

		// --- Datenfile schreiben
		LOG.info("Wuppertools => +Pfad=" + m_strDataOSPath);
		LOG.info("Wuppertools => +File=" + strOutputFile);
		final MediandoXml oSupport = new MediandoXml();
		oSupport._WriteFileFromArrayEncoding(aArrayResultFile, m_strDataOSPath + strOutputFile, "UTF-8");

		return strOutputPath;
	}

	/**
	 * Wuppertools: USERFELDER (Classifizierung / Tabellendaten )
	 * 
	 * @param weraproduct
	 * @param colCA
	 * @param oCategory
	 * @return 
	 */
	public String ExportKlassifizierung(final Product weraproduct, final ArrayList colCA, final ClassificationClass oCategory) {

		// --- Initialize
		HashMap hCA_Result = new HashMap();
		String strCategory = "";
		String strLine = "";

		// --- Initiallize Artikeldaten (Werte - Tabelle)
		if (weraproduct instanceof WeraVariante) {
			// --- Hole Tabellenwerte
			hCA_Result = __getCAValuesWT(weraproduct, colCA);
			strCategory = (String) hCA_Result.get("PClass");
		} else {
			if (oCategory != null) {
				strCategory = oCategory.getCode();
			} else {
				strCategory = "";
			}
		}

		// --- ProfiClass
		strLine += "\t" + strCategory; // USERFELD_01

		// --- leer
		strLine += "\t"; // USERFELD_02

		// --- Neutraler Produkttitel (mehrsprachig) - USERFELD_03
		strLine += "\t" + __getattributeString(weraproduct, "name", "de");
		strLine += "|" + __getattributeString(weraproduct, "name", "en");
		strLine += "|" + __getattributeString(weraproduct, "name", "es");

		// --- leer
		strLine += "\t"; // USERFELD_04
		strLine += "\t"; // USERFELD_05

		// --- Spaltenï¿½berspriften (DE)
		// --- Initiallize Artikeldaten (Werte - Tabelle)
		if (weraproduct instanceof WeraVariante) {
			strLine += "\t" + (String) hCA_Result.get("Header"); // USERFELD_06
		} else {
			strLine += "\t"; // USERFELD_06
		}

		// --- Spaltenï¿½berspriften (EN)
		strLine += "\t"; // USERFELD_07

		// --- Spaltenï¿½berspriften (ES)
		strLine += "\t"; // USERFELD_08

		// --- Einheiten (neutral)
		if (weraproduct instanceof WeraVariante) {
			strLine += "\t" + (String) hCA_Result.get("Units"); // USERFELD_09
		} else {
			strLine += "\t"; // USERFELD_09
		}

		// --- Werte
		if (weraproduct instanceof WeraVariante) {
			strLine += "\t" + (String) hCA_Result.get("Values"); // USERFELD_10								
		} else {
			strLine += "\t"; // USERFELD_10
		}

		return strLine;
	}

	// --- Preise und Verpackungs Einheiten --------------------------------------------------------------------
	/**
	 * Wuppertools: 
	 * 
	 * Menge preise + 2.5% Nettopreise Staffel und Bruttopreise Zolltarifnummer: Ursprungsland : Gewicht pro Stï¿½ck :
	 * Gewicht / VPE: => GewichtVE Gewichteinheit: Packmass (Gewichtseinheit): =>packm_gewichteh Packmass (Lï¿½nge):
	 * =>packm_laenge Packmass (Hï¿½he): =>vpackm_hoehe Packmass (Breite): =>packm_breite Packmass (Lï¿½ngeeinheit)
	 * =>packm_laengen_einheit
	 * 
	 * @param weraproduct
	 * @param strPreisliste_brutto
	 * @param strPreisliste_staffel
	 * @return 
	 */
	public String ExportWT_Logistik(final Product weraproduct, final String strPreisliste_brutto,
			final String strPreisliste_staffel) {

		// --- Initialize
		final EnumerationValue evGewichtEinheit = null;
		final EnumerationValue evPackmassLaengeeinheit = null;
		String strLine = "";

		// --- Hole ContentQuantity
		Integer intContentQuantity = (Integer) m_wm.getAttribute(weraproduct, "contentQuantity");
		if (intContentQuantity == null) {
			intContentQuantity = new Integer(1);
		}
		strLine += "\t" + intContentQuantity.toString(); // VPE

		// --- Hole nur die Preise die Exportiert werden sollen BruttoPreise
		ArrayList prices = new ArrayList();
		prices = (ArrayList) _aGetPriceList(weraproduct, strPreisliste_brutto);
		String strPreis = "";
		if (prices != null && prices.size() > 0) {
			final Double dPrice = new Double(((PriceRow) prices.iterator().next()).getPrice());
			if (dPrice.doubleValue() != 0) {
				strPreis = _strFormatPrice(new Double(dPrice * 1.025), "de");
			}
		}
		strLine += "\t" + strPreis; // VK5B

		// --- EK-Preis 
		int iCntPrice = 0;
		prices = (ArrayList) _aGetPriceList(weraproduct, strPreisliste_staffel);
		if (prices != null && prices.size() > 0) {
			// --- Schleife ï¿½ber alle Preise
			for (final Iterator itPrices = prices.iterator(); itPrices.hasNext();) {

				// --- Hole PriceRow
				final PriceRow pricerow = (PriceRow) itPrices.next();
				final Boolean bNet = (Boolean) m_wm.getAttribute(pricerow, "net");
				if (bNet.booleanValue()) {

					// --- Initialize
					strPreis = "";
					iCntPrice++;

					// --- Hole Preis und Menge
					final Double dPrice = new Double(pricerow.getPrice());
					if (dPrice.doubleValue() != 0) {
						strPreis = _strFormatPrice(new Double(dPrice * 1.025), "de");
					}
					Long longMinqtd = (Long) m_wm.getAttribute(pricerow, "minqtd");
					if (longMinqtd == null) {
						longMinqtd = new Long(1);
					}
					strLine += "\t" + longMinqtd.toString(); // Staffel
					strLine += "\t" + strPreis.toString(); // Preis
				}
			} // --- for (final Iterator itPrices = prices.iterator(); itPrices.hasNext();) {

		}
		// --- Preisspalten auffï¿½llen
		for (int iCnt = iCntPrice; iCnt < 3; iCnt++) {
			strLine += "\t"; // Staffel
			strLine += "\t"; // Preis
		}

		// --- Holen der logistischen Daten
		strLine += "\t" + __getattributeString(weraproduct, "Ursprungsland", null);
		strLine += "\t" + __getattributeString(weraproduct, "ZolltarifNr", null);
		strLine += "\t" + __getattributeString(weraproduct, "Gewicht", null);
		strLine += "\t" + __getattributeString(weraproduct, "GewVE", null);
		strLine += "\t" + __getattributeEnum(weraproduct, "GewichtEinheit", null);

		// --- Packmasse
		strLine += "\t" + __getattributeEnum(weraproduct, "packm_gewichteh", null);
		strLine += "\t" + __getattributeString(weraproduct, "packm_laenge", null);
		strLine += "\t" + __getattributeString(weraproduct, "packm_hoehe", null);
		strLine += "\t" + __getattributeString(weraproduct, "packm_breite", null);
		strLine += "\t" + __getattributeEnum(weraproduct, "packm_laengen_einheit", null);

		return strLine;
	}

	/**
	 * 
	 * @param weraproduct
	 * @param strAttrField
	 * @param strLanguage
	 * @return 
	 */
	protected String __getattributeString(final Product weraproduct, final String strAttrField, final String strLanguage) {

		// --- Initialize
		String strResultValue = "";

		// --- Sprache setzen
		if (strLanguage != null) {
			SetLanguage(strLanguage);
		}

		// --- Wert holen
		strResultValue = (String) m_wm.getAttribute(weraproduct, strAttrField);
		if (strResultValue == null || strResultValue.equals("null")) {
			strResultValue = "";
		}

		// --- Sprache zurï¿½cksetzen
		if (strLanguage != null) {
			SetLanguage("de");
		}

		return strResultValue;
	}

	/**
	 * 
	 * @param weraproduct
	 * @param strAttrField
	 * @param strLanguage
	 * @return 
	 */
	protected String __getattributeEnum(final Product weraproduct, final String strAttrField, final String strLanguage) {
		// --- Initialize
		EnumerationValue evEnumerationValue = null;
		String strResultValue = "";

		// --- Sprache setzen
		if (strLanguage != null) {
			SetLanguage(strLanguage);
		}

		evEnumerationValue = (EnumerationValue) m_wm.getAttribute(weraproduct, strAttrField);
		if (evEnumerationValue != null) {
			strResultValue = evEnumerationValue.getName();
		}
		if (strResultValue == null || strResultValue.equals("null")) {
			strResultValue = "";
		}

		// --- Sprache zurï¿½cksetzen
		if (strLanguage != null) {
			SetLanguage("de");
		}

		return strResultValue;
	}

	/**
	 * Initiallize Artikeldaten (Werte - Tabelle), Rückgabe Nummer der Klasse
	 * 
	 * @param weraarticle
	 * @param colCA
	 * @return 
	 */
	private HashMap __getCAValuesWT(final Product weraarticle, final ArrayList colCA) {
		// --- Initialize
		final HashMap hCA_Result = new HashMap();
		final ArrayList colValue = new ArrayList();
		String strHeaders = "";
		String strValue = "";
		String strValues = "";
		String strUnits = "";
		String strUnit = "";
		HashMap hashCA = null;
		ClassificationAttribute oClassificationAttribute = null;
		ClassAttributeAssignment oClassattributeAssignment = null;
		Category categoryByCa = null;
		Category categoryByCaResult = null;
		int iCount = 0;

		// --- Schleife ï¿½ber alle Werte
		for (final Iterator itCA = colCA.iterator(); itCA.hasNext();) {
			// --- Initialize
			iCount++;
			hashCA = (HashMap) itCA.next();
			oClassificationAttribute = (ClassificationAttribute) hashCA.get("classificationattribute");
			oClassattributeAssignment = (ClassAttributeAssignment) hashCA.get("classattributeassignment");

			categoryByCa = (Category) hashCA.get("category");
			if (categoryByCa != null && categoryByCaResult != null) {
				categoryByCaResult = categoryByCa;
			}

			// --- Bezeichnung
			strHeaders += oClassificationAttribute.getName();

			// --- Wert
			colValue.clear();
			colValue.addAll(m_oBMEcatXml.colGetCAValue(weraarticle, oClassattributeAssignment));
			strValue = "";
			if (colValue != null && colValue.size() > 0) {
				if (strValue.toLowerCase().indexOf("zoll") != -1 || colValue.get(0).toString().indexOf("/") != -1
						|| colValue.get(0).toString().indexOf("\"") != -1) {
					strValue += "'" + colValue.get(0) + "\"'";
				} else {
					strValue += (String) colValue.get(0);
				}
			}
			strValues += strValue;

			// --- Einheit
			strUnit = m_oBMEcatXml._getUnit4CA(oClassattributeAssignment, categoryByCa);
			if (strValue.toLowerCase().indexOf("zoll") != -1) {
				strUnit = "Zoll";
			}
			strUnits += strUnit;

			// --- Trennzeichen setzen
			if (iCount < colCA.size()) {
				strHeaders += "|";
				strValues += "|";
				strUnits += "|";
			}
		}

		if (categoryByCaResult != null) {
			hCA_Result.put("PClass", categoryByCa.getCode());
		} else {
			hCA_Result.put("PClass", "");
		}
		hCA_Result.put("Header", strHeaders);
		hCA_Result.put("Units", strUnits);
		hCA_Result.put("Values", strValues);

		return hCA_Result;
	}

	/**
	 * Bestückung Satz
	 * 
	 * @param weraproduct
	 * @return 
	 */
	private String __GetSatzBestueckung(final Product weraproduct) {

		// --- Gen. Bestï¿½ckung
		final Collection aContent = ((WeraProductSet) weraproduct).generateWeraProductSetData();
		HashMap oHashMapProdukt = null;
		String strTagContent = "Bestï¿½ckung:<br>";
		ArrayList colHash = null;
		if (aContent != null && aContent.size() > 0) {
			for (final Iterator it1 = aContent.iterator(); it1.hasNext();) {

				// --- Hole Map
				oHashMapProdukt = (HashMap) it1.next();

				// --- Initialize
				final String strCodeName = (String) oHashMapProdukt.get("code");
				colHash = (ArrayList) oHashMapProdukt.get("colHashArtikel");
				if (colHash != null && colHash.size() > 0) {
					int iPos1 = 0;
					for (final Iterator it2 = colHash.iterator(); it2.hasNext();) {
						// --- Hole Map
						iPos1++;
						final HashMap oHashMapArtikel = (HashMap) it2.next();
						if (oHashMapArtikel != null) {
							if (iPos1 < colHash.size()) {
								strTagContent += strCodeName + ":" + oHashMapArtikel.get("value") + ";";
							} else {
								strTagContent += strCodeName + ":" + oHashMapArtikel.get("value");
							}
						}
					}
				}

				// --- Content korrigieren
				if (strTagContent.length() > 2) {
					strTagContent = strTagContent.trim();
				}
			}
		}

		return strTagContent;
	}

	// -------------------------------------------------------------------------------------------------------------------------------------
	// --- Datenexport Wuppertools ---------------------------------------------------------------------------------------------------------
	// -------------------------------------------------------------------------------------------------------------------------------------
}

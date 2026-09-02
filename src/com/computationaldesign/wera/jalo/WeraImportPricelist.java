package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.GeneratedWeraConstants;
import de.hybris.platform.europe1.enums.UserPriceGroup;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.classification.util.Feature;
import de.hybris.platform.catalog.jalo.classification.util.TypedFeature;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystem;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.europe1.jalo.Europe1PriceFactory;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Currency;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.order.OrderManager;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.product.ProductManager;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.util.Config;
import de.hybris.platform.variants.jalo.VariantProduct;
import de.hybris.platform.jalo.type.ComposedType;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
import java.util.Map;
import java.util.Set;
import java.lang.*;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequest;
import org.apache.log4j.Logger;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.io.FilenameUtils;
import java.io.InputStream;
import org.apache.commons.fileupload.FileUploadException;
import javax.servlet.ServletException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.io.File;
import java.io.BufferedWriter;

public class WeraImportPricelist extends WeraManager {

	/**
	 * Edit the local|project.properties to change logging behavior (properties
	 * 'log4j.*').
	 */
	private static final Logger LOG = Logger.getLogger(WeraImportPricelist.class.getName());

	WeraClassificationHelper m_weraclassificationhelper = null;
	private CatalogVersion m_oMasterCatalogVersion = null;
	private CatalogVersion m_oNewCatalogVersion = null;
	private ClassificationSystem m_oCatalogSystem = null;
	private ArrayList m_alErrorLogFile = null;
	private ArrayList m_alLogFile = null;
	private JaloSession m_oJaloSession = null;
	private String m_strLanguage = "de";
	private XmlSupport m_oXmlSupport = null;


	// --- Seitenzahlen aus Hybris = false, Seitenzahlen per Paltzhalter=true
	boolean m_platzhalterSeitennummer = true; // <<<<=== Hier setzen


	public WeraImportPricelist() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param strLanguage
	 */
	public void resetProducts(String strLanguage) {

		// --- Setzen der Sprache
		SetLanguage(strLanguage);

		// --- Zur�cksetzen der Daten
		// --- Zur�cksetzen des Flags f�r
		Set setProductSets = WeraProduct.getAllInstances();
		WeraProduct productset = null;
		for (Iterator it2 = setProductSets.iterator(); it2.hasNext(); ) {
			productset = (WeraProduct) it2.next();
			productset.setLocalizedProperty("ausgabe_preisliste", new Boolean(false));
			WeraManager.getInstance().setAttribute(productset, "orderPL", new Integer(0));
			LOG.info("Reset WP=>" + productset.getCode());
		}

		setProductSets = WeraProductSet.getAllInstances();
		productset = null;
		for (Iterator it2 = setProductSets.iterator(); it2.hasNext(); ) {
			productset = (WeraProductSet) it2.next();
			productset.setLocalizedProperty("ausgabe_preisliste", new Boolean(false));
			WeraManager.getInstance().setAttribute(productset, "orderPL", new Integer(0));
			LOG.info("Reset WPS=>" + productset.getCode());
		}

		Set setVariantSets = WeraVariante.getAllInstances();
		WeraVariante variantset = null;
		for (Iterator it2 = setVariantSets.iterator(); it2.hasNext(); ) {
			variantset = (WeraVariante) it2.next();
			variantset.setLocalizedProperty("ausgabe_preisliste", new Boolean(false));
			LOG.info("Reset WV=>" + variantset.getCode());
		}

	}

	/**
	 * Einlesen der Eingabe-Datei
	 *
	 * @param HttpServletRequest request
	 * @return HashMap ReqParam
	 */
	public HashMap _readInputFile(HttpServletRequest request) {

		// --- Initialze
		HashMap ReqParam = new HashMap();
		ReqParam.put("error", 0);
		ReqParam.put("errormsg", "");
		String strFileName = "";

		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (!item.isFormField()) {

					// Process form file field (input type="file").
					String fieldName = item.getFieldName();
					String fileName = FilenameUtils.getName(item.getName());
					try {
					InputStream fileContent = item.getInputStream();
					// ... (do your job here)
					LOG.info("2.fieldName="+fieldName);
					LOG.info("2.fileName="+fileName);
					LOG.info("2.fileContent="+fileContent);
						InputStreamReader isr = new InputStreamReader(fileContent, Charset.forName("UTF-8"));
						char[] buffer = new char[32768];
						StringBuilder stringBuilder = new StringBuilder();
						int length = 0;

						while ((length = isr.read(buffer, 0, buffer.length)) >= 0) {
							stringBuilder.append(buffer, 0, length);
						}
						isr.close();
						LOG.info("stringBuilder="+stringBuilder);

						// --- datei schreiben
						//MultiPartFormData mpfa = new MultiPartFormData(request);
						//ReqParam = mpfa.getParameters();
						strFileName = Config.getParameter("wera.homepath") + "/tmp/" + ReqParam.get("fileName");
						ReqParam.put("strPathName", Config.getParameter("wera.homepath") + "/tmp/");
						ReqParam.put("strFileNameOnly", ReqParam.get("fileName"));
						ReqParam.put("strFileName", strFileName);
						//LOG.info("Import File=" + ReqParam.get("fileName"));
						//LOG.info("mpfa.getFile()=" +mpfa.getFile());

						BufferedWriter bwr = new BufferedWriter ( new FileWriter ( new File ( strFileName ) ) );
						bwr.write(stringBuilder.toString());
						bwr.flush();
						bwr.close();


					} catch (IOException e) {
						LOG.info("IOException");
					}
				}
			}
		} catch (FileUploadException e) {
			LOG.info("FileUplaodExcetion");
		}

		return ReqParam;
	}

	/**
	 * old_version
	 * @param request
	 * @return
     */
	public HashMap _old_version_readInputFile(HttpServletRequest request) {

		// --- Initialze
		HashMap ReqParam = new HashMap();
		ReqParam.put("error", 0);
		ReqParam.put("errormsg", "");
		String strFileName = "";

		try {
			List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
			for (FileItem item : items) {
				if (item.isFormField()) {
					/*
					// Process regular form field (input type="text|radio|checkbox|etc", select, etc).
					String fieldName = item.getFieldName();
					String fieldValue = item.getString();
					// ... (do your job here)
					LOG.info("1.fieldName="+fieldName);
					LOG.info("1.fieldValue="+fieldValue);
					*/
				} else {
					// Process form file field (input type="file").
					String fieldName = item.getFieldName();
					String fileName = FilenameUtils.getName(item.getName());
					try {
					InputStream fileContent = item.getInputStream();
					// ... (do your job here)
					LOG.info("2.fieldName="+fieldName);
					LOG.info("2.fileName="+fileName);
					LOG.info("2.fileContent="+fileContent);
						InputStreamReader isr = new InputStreamReader(fileContent, Charset.forName("UTF-8"));
						char[] buffer = new char[32768];
						StringBuilder stringBuilder = new StringBuilder();
						int length = 0;

						while ((length = isr.read(buffer, 0, buffer.length)) >= 0) {
							stringBuilder.append(buffer, 0, length);
						}
						isr.close();
						LOG.info("stringBuilder="+stringBuilder);

					} catch (IOException e) {
						LOG.info("IOException");
					}
				}
			}
		} catch (FileUploadException e) {
			LOG.info("FileUplaodExcetion");
		}

		try {
			MultiPartFormData mpfa = new MultiPartFormData(request);
			ReqParam = mpfa.getParameters();
			strFileName = Config.getParameter("wera.homepath") + "/tmp/" + ReqParam.get("fileName");
			ReqParam.put("strPathName", Config.getParameter("wera.homepath") + "/tmp/");
			ReqParam.put("strFileNameOnly", ReqParam.get("fileName"));
			ReqParam.put("strFileName", strFileName);
			LOG.info("Import File=" + ReqParam.get("fileName"));
			LOG.info("mpfa.getFile()=" +mpfa.getFile());
			FileOutputStream baos = new FileOutputStream(strFileName);
			baos.write(mpfa.getFile(), 0, mpfa.getFile().length);
			baos.flush();
			baos.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ReqParam.put("error", 1);
			ReqParam.put("errormsg", e.getMessage());
		}

		return ReqParam;
	}

	/**
	 *
	 * @param strEnumGroup
	 * @param strEnumcode
	 * @return
	 */
	private EnumerationValue __getEnumerationValue(String strEnumGroup, String strEnumcode) {

		// --- initialize
		EnumerationValue oEnumValueResult = null;

		try {
			EnumerationValue eEnumValueTmp = EnumerationManager.getInstance().getEnumerationValue(strEnumGroup, strEnumcode);
			if (eEnumValueTmp != null) {
				oEnumValueResult = eEnumValueTmp;
			}

		} catch (Exception e) {
		}

		return oEnumValueResult;
	}

	/**
	 * @param oClassAttributeAssignment
	 * @param strClassificationAttributeCode
	 * @param strValue
	 * @return
	 */
	private ClassificationAttributeValue __createCaV(ClassAttributeAssignment oClassAttributeAssignment, String strClassificationAttributeCode, String strValue) {

		// --- initialize
		ClassificationAttributeValue oNewCaV = null;
		final ComposedType ClassificationAttributeValueType = JaloSession.getCurrentSession().getTypeManager()
				.getComposedType(ClassificationAttributeValue.class);

		// --- get all customvalues
		final Collection<ClassificationAttributeValue> customvalues = (Collection<ClassificationAttributeValue>) getAttribute(oClassAttributeAssignment, "customValues");
		if (customvalues != null) {

			try {
				// --- create new cav
				final Map parameters = new HashMap();
				parameters.put("code", "H__" + strClassificationAttributeCode + "_" + strValue);
				parameters.put("name", strValue);
				parameters.put("systemVersion", getAttribute(oClassAttributeAssignment, "systemVersion"));
				oNewCaV = (ClassificationAttributeValue) ClassificationAttributeValueType.newInstance(parameters);
				if (null != oNewCaV) {
					customvalues.add(oNewCaV);
					setAttribute(oClassAttributeAssignment, "customValues", customvalues);
				}
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}

		return oNewCaV;
	}

	/**
	 * @param oProduct
	 * @param oClassAttributeAssignment
	 * @param oNewCaV
	 */
	private void __saveCAVasFeature(Product oProduct, ClassAttributeAssignment oClassAttributeAssignment, ClassificationAttributeValue oNewCaV) {

		// --- Speichern String
		final TypedFeature<String> feature = Feature.loadTyped(oProduct, oClassAttributeAssignment);
		feature.clearAll();

		try {
			// --- now create it
			feature.createValue(oNewCaV.getCode());
			feature.getParent().store();
		} catch (final ConsistencyCheckException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @param oProduct
	 * @param strClassificationAttributeCode
	 * @param strNewValue
	 */
	private void __importLBH(Product oProduct, String strClassificationAttributeCode, String strNewValue) {

//        System.out.println("__importLBH[START] code=" + oProduct.getCode() + " + ca=" + strClassificationAttributeCode + ", new-value=" +  strNewValue );
		// --- initialize
		ClassificationAttributeValue oNewCaV = null;

		// --- Hole alle ClassAttributeAssignment von Produkt / Variante oder Set
		final List<ClassAttributeAssignment> classattributeassignments = m_weraclassificationhelper
				.getAllClassAttributeAssignmentByProduct(oProduct);
		for (final Iterator it1 = classattributeassignments.iterator(); it1.hasNext(); ) {
//System.out.println("1" );
			// --- Hole ProfiClassAttribute
			final ClassAttributeAssignment oClassAttributeAssignment = (ClassAttributeAssignment) it1.next();
			final ClassificationAttribute oClassificationAttribute = oClassAttributeAssignment.getClassificationAttribute();

			// --- filter current ca
			if (oClassificationAttribute.getCode().equals(strClassificationAttributeCode)) {
//System.out.println("2" );
				try {
					// --- preset
					boolean bNewValue = false;

					// --- get all posible values
					m_weraclassificationhelper.getAllClassificationAttributeValuesByCaa(oClassAttributeAssignment);
					HashMap<String, ClassificationAttributeValue> hPosibleFeatureValues = m_weraclassificationhelper.hClassificationattributevaluesByValue;

					final HashMap<String, ClassificationAttributeValue> hFeatureValues = m_weraclassificationhelper
							.getPickedClassificationAttributeValuesByProduct(oProduct, oClassAttributeAssignment);
					if (hFeatureValues.size() == 0) {

						// --- we don't have any value
//                        System.out.println("__importLBH.we don't have any value, new value=" + strNewValue );
						bNewValue = true;

					} else {
						// --- get current values to compare with new one
						for (final Iterator it2 = hFeatureValues.keySet().iterator(); it2.hasNext(); ) {

							// --- get data
							String strKey = (String) it2.next();
							ClassificationAttributeValue oClassificationAttributeValue = hFeatureValues.get(strKey);
							if (!oClassificationAttributeValue.getAttribute("name").equals(strNewValue)) {
								bNewValue = true;
								break;
							}

						} // --- for (final Iterator it2 = hFeatureValues.keySet().iterator(); it2.hasNext();) {
					}

					// --- check if we have to change the value
					if (bNewValue) {

//                        System.out.println("__importLBH.change to new value=" + strNewValue );
						// --- check if our value is somewhere
						if (hPosibleFeatureValues.containsKey(strNewValue)) {
							// --- hey we found it :)
							oNewCaV = hPosibleFeatureValues.get(strNewValue);
//                            System.out.println("__importLBH.change to new-cav=" + oNewCaV.getAttribute("name") );
						} else {
							// --- we have to create a new cav (:
							oNewCaV = this.__createCaV(oClassAttributeAssignment, strClassificationAttributeCode, strNewValue);
//                            System.out.println("__importLBH.change to create-new-cav=" + oNewCaV.getAttribute("name") );
						}
						if (null != oNewCaV) {
//                            System.out.println("__importLBH.change to save-cav=" + oNewCaV.getAttribute("name") );
							this.__saveCAVasFeature(oProduct, oClassAttributeAssignment, oNewCaV);
						}

					} // --- bNewValue
					else {
						// --- stop here
						break;
					}
				} catch (final Exception e) {
					e.printStackTrace();
				}

			} // --- if ( oClassificationAttribute.getCode().equals(strClassificationAttributeCode) ) {

		} // --- for (final Iterator it1 = classattributeassignments.iterator(); it1.hasNext();) { 
		//System.out.println("__importLBH[END]" );
	}

	/**
	 * Datenimport Preis/ Produktdaten
	 * alte Version
	 *
	 * @param HttpServletRequest request
	 * @param boolean            bImportOnlyPriceData
	 * @return
	 */
	public String importPriceListXML_deaktiviert(HttpServletRequest request, boolean bImportOnlyPriceData) {

		// --- Einlesen der XML-Datei
		HashMap hashReqParam = _readInputFile(request);
//                if ( (Integer)hashReqParam.get("error") == 1 ) {
//                    return (String)hashReqParam.get("errormsg");
//                }

		// --- Initialize
		LOG.info("starte importPriceListXML...");
		String sFileName = (String) hashReqParam.get("strFileName");
		String sPriceList = (String) hashReqParam.get("preisliste");
		String strCatalogVersion = (String) hashReqParam.get("catalogversion");
		String strRefCatalogVersion = (String) hashReqParam.get("refcatalog");

		// --- Unser neuer Classifiactioan Helper
		m_weraclassificationhelper = new WeraClassificationHelper();

		String[] aCatalogVersion = strCatalogVersion.split("/");
		if (aCatalogVersion.length > 1) {
			strCatalogVersion = aCatalogVersion[1];
		} else {
			strCatalogVersion = aCatalogVersion[0];
		}
		aCatalogVersion = strRefCatalogVersion.split("/");
		if (aCatalogVersion.length > 1) {
			strRefCatalogVersion = aCatalogVersion[1];
		} else {
			strRefCatalogVersion = aCatalogVersion[0];
		}

		//String[] aTmpList = sFileName.split("/");
		//sPriceList = aTmpList[aTmpList.length - 1].replace(".xml", "");
		// --- Impotieren der XML-Daten
		DataImport dataimport = new DataImport();
		boolean bError = dataimport.bImportAllData(sFileName);
		if (bError) {
			return dataimport.getStringError();
		}
		DataImportExt ContentHandlerExt = dataimport.getContentHandler();

		// --- Prüfen ob Ok, dann übernahme der Preise nach Hybris
		HashMap hashMap = null;
		String strKey = "";

		String strImagePath = Config.getParameter("wera.importpath");
		FileWriter import_log = null;
		try {
			import_log = new FileWriter(strImagePath + "pricelist_import1.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// --- ...otherwise handle normal import
		final int iOrderStepWidth = 10;
		boolean bIsEVK = false;
		boolean bNet = false;
		boolean bIsNewItem = false;
		boolean bIsAuslaufItem = false;
		int iHasEVK = 0;
		int iNumColumns = ContentHandlerExt.m_iMaxCol;
		int iNumPriceColumns = ((iNumColumns - 13) / 4);
		final String sOrderOfEVK = "1000";

		// --- import infos
		LOG.info("sFileName=" + sFileName);
		LOG.info("sPriceList=" + sPriceList);
		LOG.info("strCatalogVersion=" + strCatalogVersion);
		LOG.info("strRefCatalogVersion=" + strRefCatalogVersion);
		LOG.info("iNumPriceColumns=" + iNumPriceColumns);
		LOG.info("ContentHandlerExt.m_iMaxCol=" + ContentHandlerExt.m_iMaxCol);
		if (bImportOnlyPriceData) {
			LOG.info("Import von Preisdaten und Reihenfolge.");
		} else {
			LOG.info("Import von  Preisdaten, EAN, VPE, Neu, Katalogseiten, und Reihenfolge.");
		}

		CatalogManager catalogManager = CatalogManager.getInstance();
		Catalog weraCatalog = catalogManager.getCatalog(m_strCatalogPrint);
//CatalogVersion weraCatalogVersion = (CatalogVersion) weraCatalog.getCatalogVersion(strRefCatalogVersion);

		// --- All data read. Now, iterate over each row (=variant/set)
		String strTmp = "";
		Set setDuplicateFinder = new HashSet();
		Map articleData = null;
		String msg = "";
		String sCode = "";
		String sArtikelNr = ""; // CSV Productcode
		String sIsNewItem = "";
		String sIsAuslaufItem = "";
		String sEAN = "";
		String sLanguage = "";
		String sWaehrung = "";
		String sPreislistenausgabe = "";
		String sVPE = "";
		String sLFDNR_PL = "";
		String sSeite = "";
		boolean bReset = true;
		LOG.info("Anzahl=" + ContentHandlerExt.m_aArrayList.size());
		if (ContentHandlerExt.m_aArrayList.size() == 0) {
			return "Es wurden keine Daten importiert.";
		}

		// --- Reihgenfolge setzen
		LOG.info("Reihenfolge wird gesetzt...");
		Collection Produkte_nicht_vorhanden = new ArrayList();
		Collection Produkte_vorhanden = new ArrayList();
		for (Iterator itmap1 = ContentHandlerExt.m_aArrayList.iterator(); itmap1
				.hasNext(); ) {
			articleData = (Map) itmap1.next();
			LOG.info("-----");
			//sLFDNR_PL = ((String) articleData.get("lfd_Nr")).trim();

			// --- aritkel-nr
			sArtikelNr = ((String) articleData.get("ArtikelNr")).trim(); // CSV

			// --- code-nr
			strTmp = (String) articleData.get("CodeNr");
			if (strTmp == null) {
				strTmp = "";
			}
			sCode = strTmp.trim(); // CSV

			// --- Waehrung
			strTmp = (String) articleData.get("Waehrung");
			if (strTmp == null) {
				strTmp = "EUR";
			}
			sWaehrung = strTmp;

			// --- neu-flag
			sIsNewItem = (String) articleData.get("Neu");

			// --- sIsAuslaufItem-flag
			sIsAuslaufItem = (String) articleData.get("Auslaufartikel");

			sPreislistenausgabe = "true"; // --- always true

			// --- sprache
			sLanguage = (String) articleData.get("Land");
			if (sLanguage == null || sLanguage.equals("")) {
				sLanguage = "de";
			}

			//LOG.info("sLanguage="+sLanguage);
			SetLanguage(sLanguage);

			//Collection products = weraCatalogVersion.getProducts(sArtikelNr);
			Product product = null;
			Integer iOrderPL = null;
			boolean bPreislistenAusgabe = false;
			String strPK = (String) articleData.get("PK");
			String strPK_SIS = (String) articleData.get("PK_SIS");
			LOG.info("++PK=" + strPK + ", PK_SIS=" + strPK_SIS);
			if (strPK.equals("")) {
				LOG.warn("++skip Row, empty PK!!");
				continue;
			}
			product = (Product) JaloSession.getCurrentSession().getItem(strPK);
			Product product_sis = null;
			if (strPK_SIS != null && !strPK_SIS.equals("")) {
				product_sis = (Product) JaloSession.getCurrentSession().getItem(strPK_SIS);
			}
			if (product != null) {
				// -------------------------------------------------------------------------------------
				// --- Ab hier alle Artikel / Satz spezifischen Daten importieren -----------------------------
				// -------------------------------------------------------------------------------------
				// --- Variante oder Satz

				// --- Urspungsland, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Ursprungsland");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Ursprungsland", strTmp);
				}
				// --- Zolltarifnummer, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Zolltarifnummer");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Zolltarifnr", strTmp);
				}
				// --- PackmassLaenge, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassLaenge");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_laenge", strTmp);
				}
				// --- PackmassHoehe, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassHoehe");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_hoehe", strTmp);
				}
				// --- PackmassBreite, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassBreite");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_breite", strTmp);
				}
				// --- PackmassGewichtseinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassGewichtseinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "gramm";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "packm_gewichteh", eEnumValueTmp);
					}
				}
				// --- PackmassLaengeeinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassLaengeeinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "millimeter";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitPackmEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "packm_laengen_einheit", eEnumValueTmp);
					}
				}
				// --- GewichtProStueck, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("GewichtProStueck");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Gewicht", strTmp);
				}
				// --- GewichtVPE, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("GewichtVPE");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "GewVE", strTmp);
				}
				// --- Gewichteinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Gewichteinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "gramm";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "GewichtEinheit", eEnumValueTmp);
					}
				}
				// --- ArtikelLaenge, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelLaenge");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA081f001", strTmp);
				}
				// --- ArtikelHoehe, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelHoehe");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA031f001", strTmp);
				}
				// --- ArtikelBreite, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelBreite");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA040f001", strTmp);
				}


				// --- EAN, import only if allowed
				if ( /* false && */ !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("EAN");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp.equals("-");
					}
					// ---- set value
					setAttribute(product, "EAN", strTmp);
				}

				// --- Auslauf Artikel / Produkt, import only if allowed
				String sAttributeForAuslaufProperty = (product instanceof WeraVariante ? "artikel_auslauf"
						: "artikel_auslauf");
				bIsAuslaufItem = (sIsAuslaufItem != null && sIsAuslaufItem.toLowerCase().equals("true"));
				if (!bImportOnlyPriceData) {
					// ---- set value
					setAttribute(product, sAttributeForAuslaufProperty, new Boolean(bIsAuslaufItem));
				}

				// --- Neuer Artikel / Produkt, import only if allowed
				String sAttributeForNeuProperty = (product instanceof WeraVariante ? "artikel_neu" : "produkt_neu");
				bIsNewItem = (sIsNewItem != null && sIsNewItem.toLowerCase().equals("true"));
				if (bIsAuslaufItem) // --- Auslaufartikel, nicht neu!!!
				{
					bIsNewItem = false;
				}
				if (!bImportOnlyPriceData) {
					// ---- set value
					setAttribute(product, sAttributeForNeuProperty, new Boolean(bIsNewItem));
				}


				// --- Verpackungseinheit, import only if allowed
				String strVPE = (String) articleData.get("VPE");
				if (strVPE == null || strVPE.trim().equals("") || strVPE.trim().equals("0")) {
					LOG.warn("Not Imported VPE=" + articleData.get("VPE") + "=, value is empty or null.");
				} else {
					Integer intContentQuantity = new Integer(strVPE.trim());
					if ( /* false && */ !bImportOnlyPriceData) {
//                                        LOG.info ("Import VPE=" + strVPE.trim() + "=" );
						// ---- set value
						setAttribute(product, "contentQuantity", intContentQuantity);
					}
				}

				// --- Delete prices for product's pricelist
				int iNumPriceRowsDeleted = this.deletePriceRows(product,
						sPriceList);
				if (iNumPriceRowsDeleted > 0) {
					//LOG.info("Deleted " + iNumPriceRowsDeleted
					//		+ " price rows for " + sPriceList
					//		+ ", Product " + product.getCode());
				}

				// --- Preise einpflegen
				//     Netto Preise auslesen
				for (int iPriceCol = 1; iPriceCol <= 5; iPriceCol++) {
					String strKeyPreis = "Preis_S" + iPriceCol;
					String strKeyQty = "Menge_S" + iPriceCol;
					//LOG.info("strKeyPreis=" + articleData.get(strKeyPreis) + "=" );
					//LOG.info("strKeyQty ("+strKeyQty+")=" + articleData.get(strKeyQty) + "=" );

					// --- Reihenfolge / Netto / Brutto
					int iPriority = iPriceCol * 10;

					// --- Menge
					boolean isAufAnfrage = false;
					String sMengeValue = (String) articleData.get(strKeyQty);
					if (sMengeValue != null) {
						sMengeValue = sMengeValue.trim();
					}
					if (sMengeValue != null
							&& (sMengeValue.toLowerCase().equals(
							"auf anfrage") || sMengeValue.toLowerCase().equals("a.A."))) {
						isAufAnfrage = true;
					}
					if (sMengeValue == null || sMengeValue.length() == 0
							|| sMengeValue.equals("-")) {
						continue;
					}

					int iMinQty = 0;
					if (isAufAnfrage == false || sMengeValue.length() > 0) {
						iMinQty = Integer.parseInt(sMengeValue);
					}

					// --- Preis
					String sPriceValue = (String) articleData.get(strKeyPreis);
					if (sPriceValue != null) {
						sPriceValue = sPriceValue.trim();
					}
					if (sPriceValue == null || sPriceValue.length() == 0
							|| sPriceValue.equals("-")) {
						LOG.info("skip insert PriceRow" + iPriceCol + "=");
						continue;
					}
					// --- Nur der letzte Punkt zählt bei Fliesskommazahlen
					sPriceValue = sPriceValue.trim().replace(",", ".");
					while (sPriceValue.indexOf('.') != sPriceValue
							.lastIndexOf('.')) {
						sPriceValue = sPriceValue.replaceFirst("\\.", "");
					}
					double dPrice = Double.parseDouble(sPriceValue);

					// --- Preiszeile anlegen
					bNet = true;
					bIsEVK = false;
					insertPriceRow(product, iPriority,
							sPriceList, iMinQty, bNet, dPrice, bIsEVK,
							isAufAnfrage, sWaehrung);
				}

				// --- Brutto Preise übernehmen
				// --- Preiszeile anlegen
				//LOG.info("import Brutto");
				//LOG.info("Brutto EVP=" + articleData.get("EVP") + "=" );
				//LOG.info("Brutto VPE=" + articleData.get("VPE") + "=" );
				bNet = false;
				bIsEVK = true;
				int iPriority = 1000;
				int iMinQty = 1;
				boolean isAufAnfrage = true;
				double dPrice = 0;
				String sPriceValue = (String) articleData.get("EVP");
				if (sPriceValue == null
						|| sPriceValue.toLowerCase().equals("auf anfrage")
						|| sPriceValue.toLowerCase().equals("a.a.")
						|| sPriceValue.trim().equals("")) {
					isAufAnfrage = true;
				} else {
					isAufAnfrage = false;
					// --- Preis formatieren
					sPriceValue = sPriceValue.trim().replace(",", ".");
					while (sPriceValue.indexOf('.') != sPriceValue
							.lastIndexOf('.')) {
						sPriceValue = sPriceValue.replaceFirst("\\.", "");
					}
					dPrice = Double.parseDouble(sPriceValue);
				}
				if (sPriceValue == null || sPriceValue.length() == 0
						|| sPriceValue.equals("-")) {
					LOG.warn("skip insert PriceRow Brutto=" + "=");
				} else {
					insertPriceRow(product, iPriority,
							sPriceList, iMinQty, bNet, dPrice, bIsEVK,
							isAufAnfrage, sWaehrung);
				}

				// -------------------------------------------------------------------------------------
				// --- Ab hier alle Produkt spezifischen Daten importieren -----------------------------
				// -------------------------------------------------------------------------------------
				if (product instanceof WeraVariante) // --- Hole Basisprodukt
				{
					product = ((WeraVariante) product).getBaseProduct();
				}

				if (product_sis != null) {
					// --- SIS
					if (setDuplicateFinder.contains(product_sis.getPK())) {
						// --- Bereits bearbeitet
						LOG.info("++Bereits bearbeitet product_sis.getCode()=" + product_sis.getCode());
						continue;
					} else {
						setDuplicateFinder.add(product_sis.getPK());
					}
				} else {
					// --- keine SIS
					if (setDuplicateFinder.contains(product.getPK())) {
						// --- Bereits bearbeitet
						LOG.info("++Bereits bearbeitet product.getCode()=" + product.getCode());
						continue;
					} else {
						setDuplicateFinder.add(product.getPK());
					}
				}

				if (product instanceof WeraProduct) {

					// --- Gibt es ein SIS
					Product oProduktKatalog = product;
					if (product_sis != null && product_sis != product) {
						LOG.info("Produkt ist SIS");
						oProduktKatalog = product_sis;
					}
					LOG.info("++sArtikelNr=" + sArtikelNr);

					if (oProduktKatalog.getCode().equals(sArtikelNr)) {

						LOG.info("++Found product.getCode()=" + product.getCode());
						// --- Order
						String strPrio = (String) articleData.get("SortKey");
						if (strPrio == null) {
							strPrio = "-1";
						}
						String strSeite = (String) articleData.get("Seite");
						if (strSeite == null) {
							strSeite = "";
						}
						LOG.info("++Order=" + strPrio);
						LOG.info("++Seite=" + strSeite);

						// --- Order im Export-Katalog
						LOG.info("Katalogversion-Export = " + strCatalogVersion + " (Order)=" + strPrio);
						Collection colCategory2productextOrder = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strCatalogVersion);
						if (colCategory2productextOrder == null || colCategory2productextOrder.size() == 0) {
							WeraManager.getInstance().createCategory2ProductExt((WeraProduct) oProduktKatalog, strCatalogVersion);
							colCategory2productextOrder = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strCatalogVersion);
						}
						for (Iterator it2 = colCategory2productextOrder.iterator(); it2.hasNext(); ) {
							Category2ProductExt category2productext = (Category2ProductExt) it2.next();
							LOG.info("set order to " + strPrio);
							category2productext.setPriority(strPrio);
						}

						// --- Seite im Referenzkatalog ablegen
						if (false && !bImportOnlyPriceData) {
							// ---- import only if allowed
							//LOG.info ( "Katalogversion-Referenz = " + strRefCatalogVersion + " (Page)=" + strSeite );
							if (strSeite.equals("")) {
								// --- no page to set on referenz-catalog-version
								//LOG.info ( "no page to set on referenz-catalog-version!" );
							} else {
								// --- set page on referenz-catalog-version
								Collection colCategory2productextPage = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strRefCatalogVersion);
								//LOG.info ( "colCategory2productextPage=" + colCategory2productextPage + " size="  + colCategory2productextPage.size() );
								if (colCategory2productextPage == null || colCategory2productextPage.size() == 0) {
									//LOG.info ( "colCategory2productextPage=" + null );
									WeraManager.getInstance().createCategory2ProductExt((WeraProduct) oProduktKatalog, strRefCatalogVersion);
									colCategory2productextPage = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strRefCatalogVersion);
									//LOG.info ( "colCategory2productextPage=" + null );
								}
								for (Iterator it2 = colCategory2productextPage.iterator(); it2.hasNext(); ) {
									Category2ProductExt category2productext = (Category2ProductExt) it2.next();
									String strPageOld = (String) category2productext.getPagenr_catalog();
									//LOG.info ( "set page to " + strSeite );
									category2productext.setPagenr_catalog(strSeite);
								}

							} // --- if ( !strSeite.equals("") ) {

						} // --- if ( !bImportOnlyPriceData ) {

						Produkte_vorhanden.add(sArtikelNr + "=>" + sLFDNR_PL);

					} else {
						LOG.info("Reihenfolge nicht gesetzt. ");
					}

				} // --- if ( product instanceof WeraProduct) {
				else {
					// ---
					LOG.info("Fehler: product.getCode().equals(sArtikelNr)");
				}
			} else {
				Produkte_nicht_vorhanden.add(sArtikelNr + " => " + sCode);
			}

		} // --- for (Iterator itmap1 = ContentHandlerExt.m_aArrayList.iterator(); itmap1

		// --- Ausgabe der nicht vorhandenen Produkte
		LOG.info("pricelist_import1 wird geschrieben...");
		try {
			import_log.write("Liste der fehlenden Produkte \n");
			for (Iterator it2 = Produkte_nicht_vorhanden.iterator(); it2
					.hasNext(); ) {
				String strCodeNr = (String) it2.next();
				import_log.write(strCodeNr + "\n");
			}
			import_log.write("Liste der vorhandenen Produkte \n");
			for (Iterator it2 = Produkte_vorhanden.iterator(); it2
					.hasNext(); ) {
				String strCodeNr = (String) it2.next();
				import_log.write(strCodeNr + "\n");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// --- Logdatei schliesen
		try {
			import_log.flush();
			import_log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- 2. Logdatei
		try {
			import_log = new FileWriter(strImagePath + "pricelist_import2.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		// --- Logdatei schliesen
		try {
			import_log.flush();
			import_log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Datenimport erfolgreich.";
	}

	/**
	 * Datenimport Produktdaten Reihenfolge
	 *
	 * @param HttpServletRequest request
	 * @param boolean            bImportOnlyPriceData
	 * @return
	 */
	public String importOrderXML(HttpServletRequest request, boolean bImportOnlyPriceData) {

		// --- Einlesen der XML-Datei
		HashMap hashReqParam = _readInputFile(request);
//                if ( (Integer)hashReqParam.get("error") == 1 ) {
//                    return (String)hashReqParam.get("errormsg");
//                }

		// --- Initialize
		LOG.info("starte importPriceListXML...");
		String sFileName = (String) hashReqParam.get("strFileName");
		String sPriceList = (String) hashReqParam.get("preisliste");
		String strCatalogVersion = (String) hashReqParam.get("catalogversion");
		String strRefCatalogVersion = (String) hashReqParam.get("refcatalog");

		// --- Unser neuer Classifiactioan Helper
		m_weraclassificationhelper = new WeraClassificationHelper();

		String[] aCatalogVersion = strCatalogVersion.split("/");
		if (aCatalogVersion.length > 1) {
			strCatalogVersion = aCatalogVersion[1];
		} else {
			strCatalogVersion = aCatalogVersion[0];
		}
		aCatalogVersion = strRefCatalogVersion.split("/");
		if (aCatalogVersion.length > 1) {
			strRefCatalogVersion = aCatalogVersion[1];
		} else {
			strRefCatalogVersion = aCatalogVersion[0];
		}

		//String[] aTmpList = sFileName.split("/");
		//sPriceList = aTmpList[aTmpList.length - 1].replace(".xml", "");
		// --- Impotieren der XML-Daten
		DataImport dataimport = new DataImport();
		boolean bError = dataimport.bImportAllData(sFileName);
		if (bError) {
			return dataimport.getStringError();
		}
		DataImportExt ContentHandlerExt = dataimport.getContentHandler();

		// --- Pr�fen ob Ok, dann �bernahme der Preise nach Hybris
		HashMap hashMap = null;
		String strKey = "";

		String strImagePath = Config.getParameter("wera.importpath");
		FileWriter import_log = null;
		try {
			import_log = new FileWriter(strImagePath + "pricelist_import1.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// --- ...otherwise handle normal import
		final int iOrderStepWidth = 10;
		boolean bIsEVK = false;
		boolean bNet = false;
		boolean bIsNewItem = false;
		boolean bIsAuslaufItem = false;
		int iHasEVK = 0;
		int iNumColumns = ContentHandlerExt.m_iMaxCol;
		int iNumPriceColumns = ((iNumColumns - 13) / 4);
		final String sOrderOfEVK = "1000";

		// --- import infos
		LOG.info("sFileName=" + sFileName);
		LOG.info("sPriceList=" + sPriceList);
		LOG.info("strCatalogVersion=" + strCatalogVersion);
		LOG.info("strRefCatalogVersion=" + strRefCatalogVersion);
		LOG.info("iNumPriceColumns=" + iNumPriceColumns);
		LOG.info("ContentHandlerExt.m_iMaxCol=" + ContentHandlerExt.m_iMaxCol);
		if (bImportOnlyPriceData) {
			LOG.info("Import von Preisdaten und Reihenfolge.");
		} else {
			LOG.info("Import von  Preisdaten, EAN, VPE, Neu, Katalogseiten, und Reihenfolge.");
		}

		CatalogManager catalogManager = CatalogManager.getInstance();
		Catalog weraCatalog = catalogManager.getCatalog(m_strCatalogPrint);
//CatalogVersion weraCatalogVersion = (CatalogVersion) weraCatalog.getCatalogVersion(strRefCatalogVersion);

		// --- All data read. Now, iterate over each row (=variant/set)
		String strTmp = "";
		Set setDuplicateFinder = new HashSet();
		Map articleData = null;
		String msg = "";
		String sCode = "";
		String sArtikelNr = ""; // CSV Productcode
		String sIsNewItem = "";
		String sIsAuslaufItem = "";
		String sEAN = "";
		String sLanguage = "";
		String sWaehrung = "";
		String sPreislistenausgabe = "";
		String sVPE = "";
		String sLFDNR_PL = "";
		String sSeite = "";
		boolean bReset = true;
		LOG.info("Anzahl=" + ContentHandlerExt.m_aArrayList.size());
		if (ContentHandlerExt.m_aArrayList.size() == 0) {
			return "Es wurden keine Daten importiert.";
		}

		// --- Reihgenfolge setzen
		LOG.info("Reihenfolge wird gesetzt...");
		Collection Produkte_nicht_vorhanden = new ArrayList();
		Collection Produkte_vorhanden = new ArrayList();
		for (Iterator itmap1 = ContentHandlerExt.m_aArrayList.iterator(); itmap1
				.hasNext(); ) {
			articleData = (Map) itmap1.next();
			LOG.info("-----");
			//sLFDNR_PL = ((String) articleData.get("lfd_Nr")).trim();

			// --- aritkel-nr
			sArtikelNr = ((String) articleData.get("ArtikelNr")).trim(); // CSV

			// --- code-nr
			strTmp = (String) articleData.get("CodeNr");
			if (strTmp == null) {
				strTmp = "";
			}
			sCode = strTmp.trim(); // CSV

			// --- Waehrung
			strTmp = (String) articleData.get("Waehrung");
			if (strTmp == null) {
				strTmp = "EUR";
			}
			sWaehrung = strTmp;

			// --- neu-flag
			sIsNewItem = (String) articleData.get("Neu");

			// --- sIsAuslaufItem-flag
			sIsAuslaufItem = (String) articleData.get("Auslaufartikel");

			sPreislistenausgabe = "true"; // --- always true

			// --- sprache
			sLanguage = (String) articleData.get("Land");
			if (sLanguage == null || sLanguage.equals("")) {
				sLanguage = "de";
			}

			//LOG.info("sLanguage="+sLanguage);
			SetLanguage(sLanguage);

			//Collection products = weraCatalogVersion.getProducts(sArtikelNr);
			Product product = null;
			Integer iOrderPL = null;
			boolean bPreislistenAusgabe = false;
			String strPK = (String) articleData.get("PK");
			String strPK_SIS = (String) articleData.get("PK_SIS");
			LOG.info("++PK=" + strPK + ", PK_SIS=" + strPK_SIS);
			if (strPK.equals("")) {
				LOG.warn("++skip Row, empty PK!!");
				continue;
			}
			product = (Product) JaloSession.getCurrentSession().getItem(strPK);
			Product product_sis = null;
			if (strPK_SIS != null && !strPK_SIS.equals("")) {
				product_sis = (Product) JaloSession.getCurrentSession().getItem(strPK_SIS);
			}
			if (product != null) {
				// -------------------------------------------------------------------------------------
				// --- Ab hier alle Artikel / Satz spezifischen Daten importieren -----------------------------
				// -------------------------------------------------------------------------------------
				// --- Variante oder Satz

				// --- Urspungsland, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Ursprungsland");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Ursprungsland", strTmp);
				}
				// --- Zolltarifnummer, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Zolltarifnummer");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Zolltarifnr", strTmp);
				}
				// --- PackmassLaenge, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassLaenge");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_laenge", strTmp);
				}
				// --- PackmassHoehe, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassHoehe");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_hoehe", strTmp);
				}
				// --- PackmassBreite, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassBreite");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_breite", strTmp);
				}
				// --- PackmassGewichtseinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassGewichtseinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "gramm";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "packm_gewichteh", eEnumValueTmp);
					}
				}
				// --- PackmassLaengeeinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassLaengeeinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "millimeter";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitPackmEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "packm_laengen_einheit", eEnumValueTmp);
					}
				}
				// --- GewichtProStueck, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("GewichtProStueck");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Gewicht", strTmp);
				}
				// --- GewichtVPE, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("GewichtVPE");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "GewVE", strTmp);
				}
				// --- Gewichteinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Gewichteinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "gramm";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "GewichtEinheit", eEnumValueTmp);
					}
				}
				// --- ArtikelLaenge, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelLaenge");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA081f001", strTmp);
				}
				// --- ArtikelHoehe, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelHoehe");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA031f001", strTmp);
				}
				// --- ArtikelBreite, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelBreite");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA040f001", strTmp);
				}


				// --- EAN, import only if allowed
				if ( /* false && */ !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("EAN");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp.equals("-");
					}
					// ---- set value
					setAttribute(product, "EAN", strTmp);
				}

				// --- Auslauf Artikel / Produkt, import only if allowed
				String sAttributeForAuslaufProperty = (product instanceof WeraVariante ? "artikel_auslauf"
						: "artikel_auslauf");
				bIsAuslaufItem = (sIsAuslaufItem != null && sIsAuslaufItem.toLowerCase().equals("true"));
				if (!bImportOnlyPriceData) {
					// ---- set value
					setAttribute(product, sAttributeForAuslaufProperty, new Boolean(bIsAuslaufItem));
				}

				// --- Neuer Artikel / Produkt, import only if allowed
				String sAttributeForNeuProperty = (product instanceof WeraVariante ? "artikel_neu" : "produkt_neu");
				bIsNewItem = (sIsNewItem != null && sIsNewItem.toLowerCase().equals("true"));
				if (bIsAuslaufItem) // --- Auslaufartikel, nicht neu!!!
				{
					bIsNewItem = false;
				}
				if (!bImportOnlyPriceData) {
					// ---- set value
					setAttribute(product, sAttributeForNeuProperty, new Boolean(bIsNewItem));
				}


				// --- Verpackungseinheit, import only if allowed
				String strVPE = (String) articleData.get("VPE");
				if (strVPE == null || strVPE.trim().equals("") || strVPE.trim().equals("0")) {
					LOG.warn("Not Imported VPE=" + articleData.get("VPE") + "=, value is empty or null.");
				} else {
					Integer intContentQuantity = new Integer(strVPE.trim());
					if ( /* false && */ !bImportOnlyPriceData) {
//                                        LOG.info ("Import VPE=" + strVPE.trim() + "=" );
						// ---- set value
						setAttribute(product, "contentQuantity", intContentQuantity);
					}
				}

				// --- Delete prices for product's pricelist
				int iNumPriceRowsDeleted = this.deletePriceRows(product,
						sPriceList);
				if (iNumPriceRowsDeleted > 0) {
					//LOG.info("Deleted " + iNumPriceRowsDeleted
					//		+ " price rows for " + sPriceList
					//		+ ", Product " + product.getCode());
				}

				// --- Preise einpflegen
				//     Netto Preise auslesen
				for (int iPriceCol = 1; iPriceCol <= 5; iPriceCol++) {
					String strKeyPreis = "Preis_S" + iPriceCol;
					String strKeyQty = "Menge_S" + iPriceCol;
					//LOG.info("strKeyPreis=" + articleData.get(strKeyPreis) + "=" );
					//LOG.info("strKeyQty ("+strKeyQty+")=" + articleData.get(strKeyQty) + "=" );

					// --- Reihenfolge / Netto / Brutto
					int iPriority = iPriceCol * 10;

					// --- Menge
					boolean isAufAnfrage = false;
					String sMengeValue = (String) articleData.get(strKeyQty);
					if (sMengeValue != null) {
						sMengeValue = sMengeValue.trim();
					}
					if (sMengeValue != null
							&& (sMengeValue.toLowerCase().equals(
							"auf anfrage") || sMengeValue.toLowerCase().equals("a.A."))) {
						isAufAnfrage = true;
					}
					if (sMengeValue == null || sMengeValue.length() == 0
							|| sMengeValue.equals("-")) {
						continue;
					}

					int iMinQty = 0;
					if (isAufAnfrage == false || sMengeValue.length() > 0) {
						iMinQty = Integer.parseInt(sMengeValue);
					}

					// --- Preis
					String sPriceValue = (String) articleData.get(strKeyPreis);
					if (sPriceValue != null) {
						sPriceValue = sPriceValue.trim();
					}
					if (sPriceValue == null || sPriceValue.length() == 0
							|| sPriceValue.equals("-")) {
						LOG.info("skip insert PriceRow" + iPriceCol + "=");
						continue;
					}
					// --- Nur der letzte Punkt z�hlt bei Fliesskommazahlen
					sPriceValue = sPriceValue.trim().replace(",", ".");
					while (sPriceValue.indexOf('.') != sPriceValue
							.lastIndexOf('.')) {
						sPriceValue = sPriceValue.replaceFirst("\\.", "");
					}
					double dPrice = Double.parseDouble(sPriceValue);

					// --- Preiszeile anlegen
					bNet = true;
					bIsEVK = false;
					insertPriceRow(product, iPriority,
							sPriceList, iMinQty, bNet, dPrice, bIsEVK,
							isAufAnfrage, sWaehrung);
				}

				// --- Brutto Preise �bernehmen
				// --- Preiszeile anlegen
				//LOG.info("import Brutto");
				//LOG.info("Brutto EVP=" + articleData.get("EVP") + "=" );
				//LOG.info("Brutto VPE=" + articleData.get("VPE") + "=" );
				bNet = false;
				bIsEVK = true;
				int iPriority = 1000;
				int iMinQty = 1;
				boolean isAufAnfrage = true;
				double dPrice = 0;
				String sPriceValue = (String) articleData.get("EVP");
				if (sPriceValue == null
						|| sPriceValue.toLowerCase().equals("auf anfrage")
						|| sPriceValue.toLowerCase().equals("a.a.")
						|| sPriceValue.trim().equals("")) {
					isAufAnfrage = true;
				} else {
					isAufAnfrage = false;
					// --- Preis formatieren
					sPriceValue = sPriceValue.trim().replace(",", ".");
					while (sPriceValue.indexOf('.') != sPriceValue
							.lastIndexOf('.')) {
						sPriceValue = sPriceValue.replaceFirst("\\.", "");
					}
					dPrice = Double.parseDouble(sPriceValue);
				}
				if (sPriceValue == null || sPriceValue.length() == 0
						|| sPriceValue.equals("-")) {
					LOG.warn("skip insert PriceRow Brutto=" + "=");
				} else {
					insertPriceRow(product, iPriority,
							sPriceList, iMinQty, bNet, dPrice, bIsEVK,
							isAufAnfrage, sWaehrung);
				}

				// -------------------------------------------------------------------------------------
				// --- Ab hier alle Produkt spezifischen Daten importieren -----------------------------
				// -------------------------------------------------------------------------------------
				if (product instanceof WeraVariante) // --- Hole Basisprodukt
				{
					product = ((WeraVariante) product).getBaseProduct();
				}

				if (product_sis != null) {
					// --- SIS
					if (setDuplicateFinder.contains(product_sis.getPK())) {
						// --- Bereits bearbeitet
						LOG.info("++Bereits bearbeitet product_sis.getCode()=" + product_sis.getCode());
						continue;
					} else {
						setDuplicateFinder.add(product_sis.getPK());
					}
				} else {
					// --- keine SIS
					if (setDuplicateFinder.contains(product.getPK())) {
						// --- Bereits bearbeitet
						LOG.info("++Bereits bearbeitet product.getCode()=" + product.getCode());
						continue;
					} else {
						setDuplicateFinder.add(product.getPK());
					}
				}

				if (product instanceof WeraProduct) {

					// --- Gibt es ein SIS
					Product oProduktKatalog = product;
					if (product_sis != null && product_sis != product) {
						LOG.info("Produkt ist SIS");
						oProduktKatalog = product_sis;
					}
					LOG.info("++sArtikelNr=" + sArtikelNr);

					if (oProduktKatalog.getCode().equals(sArtikelNr)) {

						LOG.info("++Found product.getCode()=" + product.getCode());
						// --- Order
						String strPrio = (String) articleData.get("SortKey");
						if (strPrio == null) {
							strPrio = "-1";
						}
						String strSeite = (String) articleData.get("Seite");
						if (strSeite == null) {
							strSeite = "";
						}
						LOG.info("++Order=" + strPrio);
						LOG.info("++Seite=" + strSeite);

						// --- Order im Export-Katalog
						LOG.info("Katalogversion-Export = " + strCatalogVersion + " (Order)=" + strPrio);
						Collection colCategory2productextOrder = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strCatalogVersion);
						if (colCategory2productextOrder == null || colCategory2productextOrder.size() == 0) {
							WeraManager.getInstance().createCategory2ProductExt((WeraProduct) oProduktKatalog, strCatalogVersion);
							colCategory2productextOrder = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strCatalogVersion);
						}
						for (Iterator it2 = colCategory2productextOrder.iterator(); it2.hasNext(); ) {
							Category2ProductExt category2productext = (Category2ProductExt) it2.next();
							LOG.info("set order to " + strPrio);
							category2productext.setPriority(strPrio);
						}

						// --- Seite im Referenzkatalog ablegen
						if (false && !bImportOnlyPriceData) {
							// ---- import only if allowed
							//LOG.info ( "Katalogversion-Referenz = " + strRefCatalogVersion + " (Page)=" + strSeite );
							if (strSeite.equals("")) {
								// --- no page to set on referenz-catalog-version
								//LOG.info ( "no page to set on referenz-catalog-version!" );
							} else {
								// --- set page on referenz-catalog-version
								Collection colCategory2productextPage = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strRefCatalogVersion);
								//LOG.info ( "colCategory2productextPage=" + colCategory2productextPage + " size="  + colCategory2productextPage.size() );
								if (colCategory2productextPage == null || colCategory2productextPage.size() == 0) {
									//LOG.info ( "colCategory2productextPage=" + null );
									WeraManager.getInstance().createCategory2ProductExt((WeraProduct) oProduktKatalog, strRefCatalogVersion);
									colCategory2productextPage = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strRefCatalogVersion);
									//LOG.info ( "colCategory2productextPage=" + null );
								}
								for (Iterator it2 = colCategory2productextPage.iterator(); it2.hasNext(); ) {
									Category2ProductExt category2productext = (Category2ProductExt) it2.next();
									String strPageOld = (String) category2productext.getPagenr_catalog();
									//LOG.info ( "set page to " + strSeite );
									category2productext.setPagenr_catalog(strSeite);
								}

							} // --- if ( !strSeite.equals("") ) {

						} // --- if ( !bImportOnlyPriceData ) {

						Produkte_vorhanden.add(sArtikelNr + "=>" + sLFDNR_PL);

					} else {
						LOG.info("Reihenfolge nicht gesetzt. ");
					}

				} // --- if ( product instanceof WeraProduct) {
				else {
					// ---
					LOG.info("Fehler: product.getCode().equals(sArtikelNr)");
				}
			} else {
				Produkte_nicht_vorhanden.add(sArtikelNr + " => " + sCode);
			}

		} // --- for (Iterator itmap1 = ContentHandlerExt.m_aArrayList.iterator(); itmap1

		// --- Ausgabe der nicht vorhandenen Produkte
		LOG.info("pricelist_import1 wird geschrieben...");
		try {
			import_log.write("Liste der fehlenden Produkte \n");
			for (Iterator it2 = Produkte_nicht_vorhanden.iterator(); it2
					.hasNext(); ) {
				String strCodeNr = (String) it2.next();
				import_log.write(strCodeNr + "\n");
			}
			import_log.write("Liste der vorhandenen Produkte \n");
			for (Iterator it2 = Produkte_vorhanden.iterator(); it2
					.hasNext(); ) {
				String strCodeNr = (String) it2.next();
				import_log.write(strCodeNr + "\n");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// --- Logdatei schliesen
		try {
			import_log.flush();
			import_log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- 2. Logdatei
		try {
			import_log = new FileWriter(strImagePath + "pricelist_import2.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		// --- Logdatei schliesen
		try {
			import_log.flush();
			import_log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Datenimport erfolgreich.";
	}

	/**
	 * Datenimport Preis/ Produktdaten
	 * alte Version
	 *
	 * @param HttpServletRequest request
	 * @param boolean            bImportOnlyPriceData
	 * @return
	 */
	public String importPriceListXML(HttpServletRequest request, boolean bImportOnlyPriceData) {

		// --- Einlesen der XML-Datei
		HashMap hashReqParam = _readInputFile(request);
//                if ( (Integer)hashReqParam.get("error") == 1 ) {
//                    return (String)hashReqParam.get("errormsg");
//                }

		// --- Initialize
		LOG.info("starte importPriceListXML...");
		String sFileName = (String) hashReqParam.get("strFileName");
		String sPriceList = (String) hashReqParam.get("preisliste");
		String strCatalogVersion = (String) hashReqParam.get("catalogversion");
		String strRefCatalogVersion = (String) hashReqParam.get("refcatalog");

		// --- Unser neuer Classifiactioan Helper
		m_weraclassificationhelper = new WeraClassificationHelper();

		String[] aCatalogVersion = strCatalogVersion.split("/");
		if (aCatalogVersion.length > 1) {
			strCatalogVersion = aCatalogVersion[1];
		} else {
			strCatalogVersion = aCatalogVersion[0];
		}
		aCatalogVersion = strRefCatalogVersion.split("/");
		if (aCatalogVersion.length > 1) {
			strRefCatalogVersion = aCatalogVersion[1];
		} else {
			strRefCatalogVersion = aCatalogVersion[0];
		}

		//String[] aTmpList = sFileName.split("/");
		//sPriceList = aTmpList[aTmpList.length - 1].replace(".xml", "");
		// --- Impotieren der XML-Daten
		DataImport dataimport = new DataImport();
		boolean bError = dataimport.bImportAllData(sFileName);
		if (bError) {
			return dataimport.getStringError();
		}
		DataImportExt ContentHandlerExt = dataimport.getContentHandler();

		// --- Pr�fen ob Ok, dann �bernahme der Preise nach Hybris
		HashMap hashMap = null;
		String strKey = "";

		String strImagePath = Config.getParameter("wera.importpath");
		FileWriter import_log = null;
		try {
			import_log = new FileWriter(strImagePath + "pricelist_import1.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// --- ...otherwise handle normal import
		final int iOrderStepWidth = 10;
		boolean bIsEVK = false;
		boolean bNet = false;
		boolean bIsNewItem = false;
		boolean bIsAuslaufItem = false;
		int iHasEVK = 0;
		int iNumColumns = ContentHandlerExt.m_iMaxCol;
		int iNumPriceColumns = ((iNumColumns - 13) / 4);
		final String sOrderOfEVK = "1000";

		// --- import infos
		LOG.info("sFileName=" + sFileName);
		LOG.info("sPriceList=" + sPriceList);
		LOG.info("strCatalogVersion=" + strCatalogVersion);
		LOG.info("strRefCatalogVersion=" + strRefCatalogVersion);
		LOG.info("iNumPriceColumns=" + iNumPriceColumns);
		LOG.info("ContentHandlerExt.m_iMaxCol=" + ContentHandlerExt.m_iMaxCol);
		if (bImportOnlyPriceData) {
			LOG.info("Import von Preisdaten und Reihenfolge.");
		} else {
			LOG.info("Import von  Preisdaten, EAN, VPE, Neu, Katalogseiten, und Reihenfolge.");
		}

		CatalogManager catalogManager = CatalogManager.getInstance();
		Catalog weraCatalog = catalogManager.getCatalog(m_strCatalogPrint);
//CatalogVersion weraCatalogVersion = (CatalogVersion) weraCatalog.getCatalogVersion(strRefCatalogVersion);

		// --- All data read. Now, iterate over each row (=variant/set)
		String strTmp = "";
		Set setDuplicateFinder = new HashSet();
		Map articleData = null;
		String msg = "";
		String sCode = "";
		String sArtikelNr = ""; // CSV Productcode
		String sIsNewItem = "";
		String sIsAuslaufItem = "";
		String sEAN = "";
		String sLanguage = "";
		String sWaehrung = "";
		String sPreislistenausgabe = "";
		String sVPE = "";
		String sLFDNR_PL = "";
		String sSeite = "";
		boolean bReset = true;
		LOG.info("Anzahl=" + ContentHandlerExt.m_aArrayList.size());
		if (ContentHandlerExt.m_aArrayList.size() == 0) {
			return "Es wurden keine Daten importiert.";
		}

		// --- Reihgenfolge setzen
		LOG.info("Reihenfolge wird gesetzt...");
		Collection Produkte_nicht_vorhanden = new ArrayList();
		Collection Produkte_vorhanden = new ArrayList();
		for (Iterator itmap1 = ContentHandlerExt.m_aArrayList.iterator(); itmap1
				.hasNext(); ) {
			articleData = (Map) itmap1.next();
			LOG.info("-----");
			//sLFDNR_PL = ((String) articleData.get("lfd_Nr")).trim();

			// --- aritkel-nr
			sArtikelNr = ((String) articleData.get("ArtikelNr")).trim(); // CSV

			// --- code-nr
			strTmp = (String) articleData.get("CodeNr");
			if (strTmp == null) {
				strTmp = "";
			}
			sCode = strTmp.trim(); // CSV

			// --- Waehrung
			strTmp = (String) articleData.get("Waehrung");
			if (strTmp == null) {
				strTmp = "EUR";
			}
			sWaehrung = strTmp;

			// --- neu-flag
			sIsNewItem = (String) articleData.get("Neu");

			// --- sIsAuslaufItem-flag
			sIsAuslaufItem = (String) articleData.get("Auslaufartikel");

			sPreislistenausgabe = "true"; // --- always true

			// --- sprache
			sLanguage = (String) articleData.get("Land");
			if (sLanguage == null || sLanguage.equals("")) {
				sLanguage = "de";
			}

			//LOG.info("sLanguage="+sLanguage);
			SetLanguage(sLanguage);

			//Collection products = weraCatalogVersion.getProducts(sArtikelNr);
			Product product = null;
			Integer iOrderPL = null;
			boolean bPreislistenAusgabe = false;
			String strPK = (String) articleData.get("PK");
			String strPK_SIS = (String) articleData.get("PK_SIS");
			LOG.info("++PK=" + strPK + ", PK_SIS=" + strPK_SIS);
			if (strPK.equals("")) {
				LOG.warn("++skip Row, empty PK!!");
				continue;
			}
			product = (Product) JaloSession.getCurrentSession().getItem(strPK);
			Product product_sis = null;
			if (strPK_SIS != null && !strPK_SIS.equals("")) {
				product_sis = (Product) JaloSession.getCurrentSession().getItem(strPK_SIS);
			}
			if (product != null) {
				// -------------------------------------------------------------------------------------
				// --- Ab hier alle Artikel / Satz spezifischen Daten importieren -----------------------------
				// -------------------------------------------------------------------------------------
				// --- Variante oder Satz

				// --- Urspungsland, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Ursprungsland");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Ursprungsland", strTmp);
				}
				// --- Zolltarifnummer, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Zolltarifnummer");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Zolltarifnr", strTmp);
				}
				// --- PackmassLaenge, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassLaenge");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_laenge", strTmp);
				}
				// --- PackmassHoehe, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassHoehe");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_hoehe", strTmp);
				}
				// --- PackmassBreite, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassBreite");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "packm_breite", strTmp);
				}
				// --- PackmassGewichtseinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassGewichtseinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "gramm";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "packm_gewichteh", eEnumValueTmp);
					}
				}
				// --- PackmassLaengeeinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("PackmassLaengeeinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "millimeter";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitPackmEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "packm_laengen_einheit", eEnumValueTmp);
					}
				}
				// --- GewichtProStueck, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("GewichtProStueck");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "Gewicht", strTmp);
				}
				// --- GewichtVPE, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("GewichtVPE");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp = "-";
					}
					// ---- set value
					setAttribute(product, "GewVE", strTmp);
				}
				// --- Gewichteinheit, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("Gewichteinheit");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("-")) {
						// --- set to default
						strTmp = "gramm";
					}
					// ---- set value
					EnumerationValue eEnumValueTmp = this.__getEnumerationValue("EinheitEnum", strTmp.toLowerCase());
					if (eEnumValueTmp != null) {
						setAttribute(product, "GewichtEinheit", eEnumValueTmp);
					}
				}
				// --- ArtikelLaenge, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelLaenge");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA081f001", strTmp);
				}
				// --- ArtikelHoehe, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelHoehe");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA031f001", strTmp);
				}
				// --- ArtikelBreite, import only if allowed
				if (false && !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("ArtikelBreite");
					if ((strTmp == null || strTmp.equals("") || strTmp.equals("0"))) {
						strTmp = "-";
					}
					// ---- set value
					this.__importLBH(product, "_AA040f001", strTmp);
				}


				// --- EAN, import only if allowed
				if ( /* false && */ !bImportOnlyPriceData) {
					strTmp = (String) articleData.get("EAN");
					if (strTmp == null || strTmp.equals("") || strTmp.equals("0")) {
						strTmp.equals("-");
					}
					// ---- set value
					setAttribute(product, "EAN", strTmp);
				}

				// --- Auslauf Artikel / Produkt, import only if allowed
				String sAttributeForAuslaufProperty = (product instanceof WeraVariante ? "artikel_auslauf"
						: "artikel_auslauf");
				bIsAuslaufItem = (sIsAuslaufItem != null && sIsAuslaufItem.toLowerCase().equals("true"));
				if (!bImportOnlyPriceData) {
					// ---- set value
					setAttribute(product, sAttributeForAuslaufProperty, new Boolean(bIsAuslaufItem));
				}

				// --- Neuer Artikel / Produkt, import only if allowed
				String sAttributeForNeuProperty = (product instanceof WeraVariante ? "artikel_neu" : "produkt_neu");
				bIsNewItem = (sIsNewItem != null && sIsNewItem.toLowerCase().equals("true"));
				if (bIsAuslaufItem) // --- Auslaufartikel, nicht neu!!!
				{
					bIsNewItem = false;
				}
				if (!bImportOnlyPriceData) {
					// ---- set value
					setAttribute(product, sAttributeForNeuProperty, new Boolean(bIsNewItem));
				}


				// --- Verpackungseinheit, import only if allowed
				String strVPE = (String) articleData.get("VPE");
				if (strVPE == null || strVPE.trim().equals("") || strVPE.trim().equals("0")) {
					LOG.warn("Not Imported VPE=" + articleData.get("VPE") + "=, value is empty or null.");
				} else {
					Integer intContentQuantity = new Integer(strVPE.trim());
					if ( /* false && */ !bImportOnlyPriceData) {
//                                        LOG.info ("Import VPE=" + strVPE.trim() + "=" );
						// ---- set value
						setAttribute(product, "contentQuantity", intContentQuantity);
					}
				}

				// --- Delete prices for product's pricelist
				int iNumPriceRowsDeleted = this.deletePriceRows(product,
						sPriceList);
				if (iNumPriceRowsDeleted > 0) {
					//LOG.info("Deleted " + iNumPriceRowsDeleted
					//		+ " price rows for " + sPriceList
					//		+ ", Product " + product.getCode());
				}

				// --- Preise einpflegen
				//     Netto Preise auslesen
				for (int iPriceCol = 1; iPriceCol <= 5; iPriceCol++) {
					String strKeyPreis = "Preis_S" + iPriceCol;
					String strKeyQty = "Menge_S" + iPriceCol;
					//LOG.info("strKeyPreis=" + articleData.get(strKeyPreis) + "=" );
					//LOG.info("strKeyQty ("+strKeyQty+")=" + articleData.get(strKeyQty) + "=" );

					// --- Reihenfolge / Netto / Brutto
					int iPriority = iPriceCol * 10;

					// --- Menge
					boolean isAufAnfrage = false;
					String sMengeValue = (String) articleData.get(strKeyQty);
					if (sMengeValue != null) {
						sMengeValue = sMengeValue.trim();
					}
					if (sMengeValue != null
							&& (sMengeValue.toLowerCase().equals(
							"auf anfrage") || sMengeValue.toLowerCase().equals("a.A."))) {
						isAufAnfrage = true;
					}
					if (sMengeValue == null || sMengeValue.length() == 0
							|| sMengeValue.equals("-")) {
						continue;
					}

					int iMinQty = 0;
					if (isAufAnfrage == false || sMengeValue.length() > 0) {
						iMinQty = Integer.parseInt(sMengeValue);
					}

					// --- Preis
					String sPriceValue = (String) articleData.get(strKeyPreis);
					if (sPriceValue != null) {
						sPriceValue = sPriceValue.trim();
					}
					if (sPriceValue == null || sPriceValue.length() == 0
							|| sPriceValue.equals("-")) {
						LOG.info("skip insert PriceRow" + iPriceCol + "=");
						continue;
					}
					// --- Nur der letzte Punkt z�hlt bei Fliesskommazahlen
					sPriceValue = sPriceValue.trim().replace(",", ".");
					while (sPriceValue.indexOf('.') != sPriceValue
							.lastIndexOf('.')) {
						sPriceValue = sPriceValue.replaceFirst("\\.", "");
					}
					double dPrice = Double.parseDouble(sPriceValue);

					// --- Preiszeile anlegen
					bNet = true;
					bIsEVK = false;
					insertPriceRow(product, iPriority,
							sPriceList, iMinQty, bNet, dPrice, bIsEVK,
							isAufAnfrage, sWaehrung);
				}

				// --- Brutto Preise �bernehmen
				// --- Preiszeile anlegen
				//LOG.info("import Brutto");
				//LOG.info("Brutto EVP=" + articleData.get("EVP") + "=" );
				//LOG.info("Brutto VPE=" + articleData.get("VPE") + "=" );
				bNet = false;
				bIsEVK = true;
				int iPriority = 1000;
				int iMinQty = 1;
				boolean isAufAnfrage = true;
				double dPrice = 0;
				String sPriceValue = (String) articleData.get("EVP");
				if (sPriceValue == null
						|| sPriceValue.toLowerCase().equals("auf anfrage")
						|| sPriceValue.toLowerCase().equals("a.a.")
						|| sPriceValue.trim().equals("")) {
					isAufAnfrage = true;
				} else {
					isAufAnfrage = false;
					// --- Preis formatieren
					sPriceValue = sPriceValue.trim().replace(",", ".");
					while (sPriceValue.indexOf('.') != sPriceValue
							.lastIndexOf('.')) {
						sPriceValue = sPriceValue.replaceFirst("\\.", "");
					}
					dPrice = Double.parseDouble(sPriceValue);
				}
				if (sPriceValue == null || sPriceValue.length() == 0
						|| sPriceValue.equals("-")) {
					LOG.warn("skip insert PriceRow Brutto=" + "=");
				} else {
					insertPriceRow(product, iPriority,
							sPriceList, iMinQty, bNet, dPrice, bIsEVK,
							isAufAnfrage, sWaehrung);
				}

				// -------------------------------------------------------------------------------------
				// --- Ab hier alle Produkt spezifischen Daten importieren -----------------------------
				// -------------------------------------------------------------------------------------
				if (product instanceof WeraVariante) // --- Hole Basisprodukt
				{
					product = ((WeraVariante) product).getBaseProduct();
				}

				if (product_sis != null) {
					// --- SIS
					if (setDuplicateFinder.contains(product_sis.getPK())) {
						// --- Bereits bearbeitet
						LOG.info("++Bereits bearbeitet product_sis.getCode()=" + product_sis.getCode());
						continue;
					} else {
						setDuplicateFinder.add(product_sis.getPK());
					}
				} else {
					// --- keine SIS
					if (setDuplicateFinder.contains(product.getPK())) {
						// --- Bereits bearbeitet
						LOG.info("++Bereits bearbeitet product.getCode()=" + product.getCode());
						continue;
					} else {
						setDuplicateFinder.add(product.getPK());
					}
				}

				if (product instanceof WeraProduct) {

					// --- Gibt es ein SIS
					Product oProduktKatalog = product;
					if (product_sis != null && product_sis != product) {
						LOG.info("Produkt ist SIS");
						oProduktKatalog = product_sis;
					}
					LOG.info("++sArtikelNr=" + sArtikelNr);

					if (oProduktKatalog.getCode().equals(sArtikelNr)) {

						LOG.info("++Found product.getCode()=" + product.getCode());
						// --- Order
						String strPrio = (String) articleData.get("SortKey");
						if (strPrio == null) {
							strPrio = "-1";
						}
						String strSeite = (String) articleData.get("Seite");
						if (strSeite == null) {
							strSeite = "";
						}
						LOG.info("++Order=" + strPrio);
						LOG.info("++Seite=" + strSeite);

						// --- Order im Export-Katalog
						LOG.info("Katalogversion-Export = " + strCatalogVersion + " (Order)=" + strPrio);
						Collection colCategory2productextOrder = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strCatalogVersion);
						if (colCategory2productextOrder == null || colCategory2productextOrder.size() == 0) {
							WeraManager.getInstance().createCategory2ProductExt((WeraProduct) oProduktKatalog, strCatalogVersion);
							colCategory2productextOrder = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strCatalogVersion);
						}
						for (Iterator it2 = colCategory2productextOrder.iterator(); it2.hasNext(); ) {
							Category2ProductExt category2productext = (Category2ProductExt) it2.next();
							LOG.info("set order to " + strPrio);
							category2productext.setPriority(strPrio);
						}

						// --- Seite im Referenzkatalog ablegen
						if (false && !bImportOnlyPriceData) {
							// ---- import only if allowed
							//LOG.info ( "Katalogversion-Referenz = " + strRefCatalogVersion + " (Page)=" + strSeite );
							if (strSeite.equals("")) {
								// --- no page to set on referenz-catalog-version
								//LOG.info ( "no page to set on referenz-catalog-version!" );
							} else {
								// --- set page on referenz-catalog-version
								Collection colCategory2productextPage = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strRefCatalogVersion);
								//LOG.info ( "colCategory2productextPage=" + colCategory2productextPage + " size="  + colCategory2productextPage.size() );
								if (colCategory2productextPage == null || colCategory2productextPage.size() == 0) {
									//LOG.info ( "colCategory2productextPage=" + null );
									WeraManager.getInstance().createCategory2ProductExt((WeraProduct) oProduktKatalog, strRefCatalogVersion);
									colCategory2productextPage = ((WeraProduct) oProduktKatalog).getCategory2ProductextsByCatalogVersion(strRefCatalogVersion);
									//LOG.info ( "colCategory2productextPage=" + null );
								}
								for (Iterator it2 = colCategory2productextPage.iterator(); it2.hasNext(); ) {
									Category2ProductExt category2productext = (Category2ProductExt) it2.next();
									String strPageOld = (String) category2productext.getPagenr_catalog();
									//LOG.info ( "set page to " + strSeite );
									category2productext.setPagenr_catalog(strSeite);
								}

							} // --- if ( !strSeite.equals("") ) {

						} // --- if ( !bImportOnlyPriceData ) {

						Produkte_vorhanden.add(sArtikelNr + "=>" + sLFDNR_PL);

					} else {
						LOG.info("Reihenfolge nicht gesetzt. ");
					}

				} // --- if ( product instanceof WeraProduct) {
				else {
					// ---
					LOG.info("Fehler: product.getCode().equals(sArtikelNr)");
				}
			} else {
				Produkte_nicht_vorhanden.add(sArtikelNr + " => " + sCode);
			}

		} // --- for (Iterator itmap1 = ContentHandlerExt.m_aArrayList.iterator(); itmap1

		// --- Ausgabe der nicht vorhandenen Produkte
		LOG.info("pricelist_import1 wird geschrieben...");
		try {
			import_log.write("Liste der fehlenden Produkte \n");
			for (Iterator it2 = Produkte_nicht_vorhanden.iterator(); it2
					.hasNext(); ) {
				String strCodeNr = (String) it2.next();
				import_log.write(strCodeNr + "\n");
			}
			import_log.write("Liste der vorhandenen Produkte \n");
			for (Iterator it2 = Produkte_vorhanden.iterator(); it2
					.hasNext(); ) {
				String strCodeNr = (String) it2.next();
				import_log.write(strCodeNr + "\n");
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		// --- Logdatei schliesen
		try {
			import_log.flush();
			import_log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- 2. Logdatei
		try {
			import_log = new FileWriter(strImagePath + "pricelist_import2.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}


		// --- Logdatei schliesen
		try {
			import_log.flush();
			import_log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Datenimport erfolgreich.";
	}

	/**
	 * @param sFileName
	 * @return
	 */
	public String importPriceList(String sFileName) {
		// ArtikelNr CodeNr Produkttext Seite Neu EAN Land
		// Waehrung Menge_S1 Preis_S1 Menge_S2 Preis_S2 Menge_S3 Preis_S3
		// Menge_S4 Preis_S4 Menge_S5 Preis_S5 EvP

		// wera_de_preis_3.csv => Hybris Preisliste: wera_de_preis_3
		// wera_de_preis_5.csv => Hybris Preisliste: wera_de_preis_5
		// wera_de_preis_brutto.csv => Hybris Preisliste: wera_de_preis_brutto
		// wera_fr_officialpreis_2.csv => Hybris Preisliste:
		// wera_fr_officialpreis_2
		// wera_fr_internpreis_2.csv => Hybris Preisliste: wera_fr_internpreis_2
		Map mapFileNames = new HashMap();

		// --------------------------------------------------------------------------------------------
		Map mapDE3 = new HashMap();
		mapDE3.put("spalten", "15"); // Anzahl erwarterter Spalten im CSV
		// File
		mapDE3.put("preisspalten", "3"); // Anzahl erwarterter Preisspalten
		// (ohne EVK sofern vorhanden)
		mapDE3.put("hat_evk", "1"); // "1", falls EVK Spalte enthalten ist
		mapFileNames.put("wera_de_preis_3.csv", mapDE3);
		// --------------------------------------------------------------------------------------------
		Map mapDE5 = new HashMap();
		mapDE5.put("spalten", "19"); // Anzahl erwarterter Spalten im CSV
		// File
		mapDE5.put("preisspalten", "5"); // Anzahl erwarterter Preisspalten
		// (ohne EVK sofern vorhanden)
		mapDE5.put("hat_evk", "1"); // "1", falls EVK Spalte enthalten ist
		mapFileNames.put("wera_de_preis_5.csv", mapDE5);
		// --------------------------------------------------------------------------------------------
		Map mapDEbrutto = new HashMap();
		mapDEbrutto.put("spalten", "9"); // Anzahl erwarterter Spalten im CSV
		// File
		mapDEbrutto.put("preisspalten", "0"); // Anzahl erwarterter
		// Preisspalten (ohne EVK sofern
		// vorhanden)
		mapDEbrutto.put("hat_evk", "1"); // "1", falls EVK Spalte enthalten
		// ist
		mapFileNames.put("wera_de_preis_brutto.csv", mapDEbrutto);
		// --------------------------------------------------------------------------------------------
		Map mapFR2intern = new HashMap();
		mapFR2intern.put("spalten", "12"); // Anzahl erwarterter Spalten im CSV
		// File
		mapFR2intern.put("preisspalten", "2"); // Anzahl erwarterter
		// Preisspalten (ohne EVK sofern
		// vorhanden)
		mapFR2intern.put("hat_evk", "0"); // "1", falls EVK Spalte enthalten
		// ist
		mapFileNames.put("wera_fr_internpreis_2.csv", mapFR2intern);
		// --------------------------------------------------------------------------------------------
		Map mapFR2official = new HashMap();
		mapFR2official.put("spalten", "12"); // Anzahl erwarterter Spalten im
		// CSV File
		mapFR2official.put("preisspalten", "2"); // Anzahl erwarterter
		// Preisspalten (ohne EVK
		// sofern vorhanden)
		mapFR2official.put("hat_evk", "0"); // "1", falls EVK Spalte enthalten
		// ist
		mapFileNames.put("wera_fr_officialpreis_2.csv", mapFR2official);
		// --------------------------------------------------------------------------------------------

		String strImagePath = Config.getParameter("wera.importpath");
		FileWriter import_log = null;
		try {
			import_log = new FileWriter(strImagePath + "pricelist_import.log");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		final String cCSVSeparator = ";";
		final String cCSVAuxSeparator = "~";
		final int iOrderStepWidth = 10;
		final String sOrderOfEVK = "1000";

		boolean bIsEVK = false;
		boolean bIsNewItem = false;
		String sRval = "";
		String line;
		int iLineCnt = 0;
		int iColumnCnt = 0;
		int iValueCnt = 0;
		boolean bFormatOK = true;
		String[] sFields = null;
		String[] sValues = null;

		Set setDuplicateFinder = new HashSet();

		String sFormatError = "Format Ok";
		Map mapData = new HashMap(); // -- the hash of variant codes => hash
		// of (col_name,col_value)

		String sPriceList = sFileName.split("\\.")[0];
		LOG.info("Filename = " + sFileName + ", Preisliste = "
				+ sPriceList);

		// --- If requested, delete all PriceRows in the system
		if (sFileName != null && sFileName.startsWith("_deleteall_")) {
			String sPricelistForDeletion = null;
			if (!sFileName.equals("_deleteall_")) {
				sPricelistForDeletion = sPriceList.substring("_deleteall_"
						.length());
			}
			sRval = "Deleted " + _deleteAllPrices(sPricelistForDeletion)
					+ " price rows.";
			LOG.info(sRval);
			return sRval + "<br/>";
		}

		// --- ...otherwise handle normal import
		boolean bNet = !sFileName.equals("wera_de_preis_brutto.csv");

		if (!mapFileNames.containsKey(sFileName)) {
			sRval = sFileName
					+ " ist kein gueltiger Name f�r eine Wera Preisliste.";

		} else {

			Map mapListProperties = (Map) mapFileNames.get(sFileName);
			int iNumColumns = Integer.parseInt((String) mapListProperties
					.get("spalten"));
			int iNumPriceColumns = Integer.parseInt((String) mapListProperties
					.get("preisspalten"));
			int iHasEVK = Integer.parseInt((String) mapListProperties
					.get("hat_evk"));

			FileReader fr = null;
			try {
				fr = new FileReader(strImagePath + sFileName);
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			BufferedReader br = new BufferedReader(fr);
			try {
				while ((line = br.readLine()) != null && bFormatOK) {
					// LOG.info( line );
					iLineCnt++;
					if (iLineCnt == 1) {
						sFields = line.split(cCSVSeparator);
						// Read in the CSV header line with column headers
						iColumnCnt = sFields.length;
						bFormatOK = (iColumnCnt == iNumColumns);
						if (bFormatOK == false) {
							sFormatError = "Anzahl Spalten muss " + iNumColumns
									+ "  sein (Ist:" + iColumnCnt + ")";
						}
						LOG.info("Line 1 read - " + iColumnCnt
								+ " columns.");
					} else {
						// --- If CSV fields are quoted, we must ignore any ';'
						// inside
						if (line.indexOf('\"') >= 0) {
							String[] sQuotedFields = line.split("\"");
							line = null;
							line = "";
							for (int y = 0; y < sQuotedFields.length; y++) {
								if (y % 2 == 1) {
									line += sQuotedFields[y].replace(
											cCSVSeparator, cCSVAuxSeparator);
								} else {
									line += sQuotedFields[y];
								}
							}
						}
						sValues = line.split(cCSVSeparator);
						iValueCnt = sValues.length;
						String sVariantCodeExtended = sValues[1];

						// --- Variant Code for searching is excluding extended
						// code: "(0)5 000005 001"
						String sVariantCode = null;
						if (sVariantCodeExtended.length() == 10) {
							sVariantCode = sVariantCodeExtended.substring(1, 7);
						} else { // --- assuming length = 11
							sVariantCode = sVariantCodeExtended.substring(2, 8);
						}

						// --- Initialize new data for new variant
						Map mapValues = new HashMap();
						mapData.put(sVariantCode, mapValues);
						for (int x = 0; x < sValues.length; x++) {
							mapValues.put(sFields[x], sValues[x]);
						}
						// LOG.info( "Line " + iLineCnt + " read." );
					}
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();

			}
			if (!bFormatOK) {
				return sFormatError;
			}
			CatalogManager catalogManager = CatalogManager.getInstance();
			ClassificationSystem weraCatalog = catalogManager
					.getClassificationSystem("weracatalog");
			ClassificationSystemVersion weraCatalogVersion = (ClassificationSystemVersion) weraCatalog
					.getActiveCatalogVersion();

			// --- All data read. Now, iterate over each row (=variant/set)
			for (Iterator itmap = mapData.keySet().iterator(); itmap.hasNext(); ) {
				String msg = "";
				String sCode = (String) itmap.next(); // CSV Variantcode
				Map articleData = (Map) mapData.get(sCode);
				String sArtikelNr = ((String) articleData.get("ArtikelNr"))
						.trim(); // CSV Productcode
				String sIsNewItem = (String) articleData.get("Neu");
				String sEAN = (String) articleData.get("EAN");

				bIsNewItem = (sIsNewItem != null && sIsNewItem.equals("true"));
				String sAttributeForNeuProperty = null;

				// --- Retrieve valid product (variant/set) for CSV data
				// sArtikelNr, sCode
				Collection colProducts = _getValidVariantOrSetForCodes(
						weraCatalogVersion, sArtikelNr, sCode,
						setDuplicateFinder, import_log, "");

				// --- Assign prices and attributes to matching products
				for (Iterator itProduct = colProducts.iterator(); itProduct
						.hasNext(); ) {
					Product p = (Product) itProduct.next();
					sAttributeForNeuProperty = (p instanceof WeraVariante ? "artikel_neu"
							: "produkt_neu");

					if (p != null) {
						LOG.info("DEBUG: Prodcode = " + p.getCode());

						// --- Set NEU flag
						try {
							p.setAttribute(sAttributeForNeuProperty,
									new Boolean(bIsNewItem));
							if (sEAN != null && sEAN.length() > 0) {
								p.setAttribute("EAN", sEAN);
							}

							//p.setAttribute("ausgabe_preisliste", new Boolean(
							//		true));
						} catch (Exception e) {
							e.printStackTrace();
						}
						// --- Delete prices for product's pricelist
						int iNumPriceRowsDeleted = this.deletePriceRows(p,
								sPriceList);
						if (iNumPriceRowsDeleted > 0) {
							LOG.info("Deleted "
									+ iNumPriceRowsDeleted + " price rows for "
									+ sPriceList + ", Product " + p.getCode());
						}

						boolean isAufAnfrage = false;
						for (int i = 0; i < iNumPriceColumns; i++) {
							String sMenge = "Menge_S" + i;
							String sMengeValue = (String) articleData
									.get(sMenge);
							if (sMengeValue != null
									&& sMengeValue.toLowerCase().equals(
									"auf anfrage")) {
								isAufAnfrage = true;
								break;
							}
						}

						for (int i = 0; i <= iNumPriceColumns; i++) {
							String sMenge = null;
							String sPrice = null;
							String sMengeValue = null;
							String sPriceValue = null;
							int iPriority = i;
							if (i == 0) { // 1.iteration reserved for EVK if
								// present
								if (iHasEVK == 1) {
									sPrice = "EvP";
									sMengeValue = sOrderOfEVK;
									bIsEVK = true;
									iPriority = (iNumPriceColumns + 1);
								} else {
									continue;
								}
							} else {
								sMenge = "Menge_S" + i;
								sPrice = "Preis_S" + i;
								sMengeValue = (String) articleData.get(sMenge);
								bIsEVK = false;
							}

							sPriceValue = (String) articleData.get(sPrice);
							if (sMengeValue == null
									|| sMengeValue.length() == 0) {
								sMengeValue = "0";
							}
							if (sPriceValue == null
									|| sPriceValue.length() == 0) {
								sPriceValue = "0.00";
							}
							// --- Nur der letzte Punkt z�hlt bei
							// Fliesskommazahlen
							sPriceValue = sPriceValue.replace(",", ".");
							while (sPriceValue.indexOf('.') != sPriceValue
									.lastIndexOf('.')) {
								sPriceValue = sPriceValue.replaceFirst("\\.",
										"");
							}

							int iMinQty = 0;
							if (isAufAnfrage == false) {
								iMinQty = Integer.parseInt(sMengeValue);
							}
							// LOG.info ("DEBUG: Price = " +
							// sPriceValue );
							double dPrice = Double.parseDouble(sPriceValue);
							insertPriceRow(p, iPriority * iOrderStepWidth,
									sPriceList, iMinQty, bNet, dPrice, bIsEVK,
									isAufAnfrage, "EUR");
						}

					}
				} // -- of for ( Iterator itProduct ... )

			} // -- for ( Iterator itmap ... )

		}

		try {
			import_log.flush();
			import_log.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sRval;
	}

	/**
	 * @param p
	 * @param iPriority
	 * @param sPriceList
	 * @param iMinQty
	 * @param bNet
	 * @param dPrice
	 * @param bIsEVK
	 * @param isAufAnfrage
	 * @param sWaehrung
	 * @return
	 */
	public String insertPriceRow(Product p, int iPriority, String sPriceList,
								 int iMinQty, boolean bNet, double dPrice, boolean bIsEVK,
								 boolean isAufAnfrage, String sWaehrung) {
		final OrderManager om = getSession().getOrderManager();
		final Europe1PriceFactory pFactory = (Europe1PriceFactory) om
				.getPriceFactory();
		final EnumerationManager em = EnumerationManager.getInstance();
		final EnumerationType et = em.getEnumerationType("UserPriceGroup");
		// final EnumerationValue ev = em.getEnumerationValue ( et,
		// "wera_de_preis_3" );
		EnumerationValue ev = null;
		try {
			ev = em.getEnumerationValue(et, sPriceList);
		} catch (Exception e1) {
			try {
				ev = em.createEnumerationValue(et, sPriceList);
				ev.setName(sPriceList);
			} catch (JaloInvalidParameterException e2) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (ConsistencyCheckException e2) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		// final Currency cur =
		// JaloSession.getCurrentSession().getSessionContext().getCurrency();
		final Currency cur = C2LManager.getInstance().getCurrencyByIsoCode(
				sWaehrung);
		final Collection units = ProductManager.getInstance().getUnitsByCode(
				"pieces");
		final int iUnitFactor = 1;
		Unit unit = null;
		if (units != null) {
			Iterator itunit = units.iterator();
			unit = (Unit) itunit.next();
		}
		try {
			/*
			 * product - the product which will be priced by this row (may be
			 * null) productPriceGroup - the product price group which will be
			 * priced by this row (may be null) user - the user whose special
			 * price is represented by this row (may be null) userPriceGroup -
			 * the user price group which special price is represented by this
			 * row (may be null) minQuantity - the minimum quantity for which
			 * this price is applicable ( > 0 ) currency - the Currency for
			 * which this price is applicable (not null) unit - the Unit for
			 * which this price is applicable (not null) unitFactor - parameter
			 * to handle small prices: baseprice = price / unitFactor net -
			 * defines if this price should be seen as including or excluding
			 * taxes dateRange - the DateRange when this price is applicable
			 * (may be null) price - the price (may contain more digits than
			 * actually possible with this currency)
			 */
			// WeraVariante wv = (WeraVariante) p;
			PriceRow pRow = null;
			pRow = pFactory.createPriceRow(p, null, null, ev, iMinQty, cur,
					unit, iUnitFactor, bNet, null, dPrice);
			if (pRow != null) {
				pRow.setAttribute("order", new Integer(iPriority));
				pRow.setAttribute("evk", new Boolean(bIsEVK));
				pRow.setAttribute("aufanfrage", new Boolean(isAufAnfrage));
			}
			LOG.info("Insert PriceRow for " + p.getCode() + " ("
					+ iMinQty + "," + dPrice + ", EVK:" + bIsEVK
					+ ", auf Anfrage:" + isAufAnfrage + ", W�hrung:"
					+ sWaehrung + ", Brutto:" + bNet + ") @" + iPriority);

		} catch (JaloPriceFactoryException e) {
			e.printStackTrace();
		} catch (JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JaloSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JaloBusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "ok";
	}

	/**
	 * @param p
	 * @param sPriceList
	 * @return
	 */
	public int deletePriceRows(Product p, String sPriceList) {
		int iNumRowsDeleted = 0;
		// --- Remove all price rows for p before we insert new ones
		Collection currentPriceRows;
		try {
			currentPriceRows = (Collection) p.getAttribute("europe1Prices");
			if (currentPriceRows != null) {
				for (Iterator itpr = currentPriceRows.iterator(); itpr
						.hasNext(); ) {
					PriceRow pr = (PriceRow) itpr.next();
					if (pr != null) {
						EnumerationValue evCurrent = pr.getUserGroup();
						if (evCurrent.getCode().equals(sPriceList)) {
							if (pr != null) {
								pr.remove();
								iNumRowsDeleted++;
								LOG.info("Removing PriceRow for product "
										+ p.getCode()
										+ ", list: "
										+ evCurrent.getCode());
							}
						} else {
							// newPriceRows.add(pr);
						}
					}
				}
				// p.setAttribute("europe1Prices", null);
				// p.setAttribute("europe1Prices", newPriceRows);
			}
		} catch (JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JaloSecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return iNumRowsDeleted;
	}

	/**
	 * Deletes all PriceRows of all products in the Wera Catalogue,
	 * returns number of deleted rows
	 *
	 * @param sPriceList
	 * @return
	 */
	public int _deleteAllPrices(String sPriceList) {
		int iNumRowsDeleted = 0;

		EnumerationManager em = EnumerationManager.getInstance();
		EnumerationType et = em.getEnumerationType("UserPriceGroup");
		EnumerationValue ev = null;
		if (sPriceList != null) {
			ev = em.getEnumerationValue(et, sPriceList);
		}
		Collection colProducts = WeraManager.m_weraCatalogVersion
				.getAllProducts();
		// Collection colProducts = getProducts();
		int iNumProducts = colProducts.size();
		int iProductCnt = 0;
		int i5percent = iNumProducts / 20;
		for (Iterator it = colProducts.iterator(); it.hasNext(); ) {

			if (++iProductCnt % i5percent == 0) {
				LOG.info("Deletion "
						+ Math
						.round(((double) iProductCnt / (double) iNumProducts) * 100)
						+ "% done.");
			}
			Product p = (Product) it.next();
			Collection colPrices = null;

			try {
				colPrices = (Collection) p.getAttribute("europe1prices");
			} catch (JaloInvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (colPrices != null) {
				for (Iterator itpr = colPrices.iterator(); itpr.hasNext(); ) {
					PriceRow pr = (PriceRow) itpr.next();
					if (pr != null) {
						if (sPriceList == null
								|| pr.getUserGroup().getCode().equals(
								ev.getCode())) {
							try {
								pr.remove();
							} catch (ConsistencyCheckException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							iNumRowsDeleted++;
						}
					}
				}
			}
		}
		return iNumRowsDeleted;
	}

	/**
	 * @param weraCatalogVersion
	 * @param sArtikelNr
	 * @param sCode
	 * @param setDuplicateFinder
	 * @param import_log
	 * @param sLFDNR_PL
	 * @return
	 */
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
		for (Iterator itprod = colProducts.iterator(); itprod.hasNext(); ) {
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
				LOG.info("++Found " + res.getCount() + " for "
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
			 * "WARNING: Product with code " + p.getCode() + " already added."; }
			 *  // sAttributeForNeuProperty = "produkt_neu";
			 * sAttributeForNeuProperty = "artikel_neu"; }
			 *  }
			 */
			if (pList.isEmpty()) {
				msg += "ERROR: Product " + sCode
						+ " not in WeraCatalog - skipping...\n\n";
			}
		}

		// --- Output warning messages on stdout and logfile.
		if (msg.length() > 0) {
			LOG.info(msg);
			try {
				import_log.write(msg + "\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return pList;
	}

	// ----------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------
	// ----------------------------------------------------------------------------------------------------------

	/**
	 * IMportieren der Produktreihenfolge in eine Katalogversion
	 *
	 * @param HttpServletRequest 				request
	 * @param String             				strCatalog
	 * @param String             				strCatalogversion
	 * @param Collection<String>              	colPriceLists
	 * @return
	 */
	public String produktImportPreisdatenCSV(HttpServletRequest request, final String strCatalog, final String strCatalogversion ) {

		// --- Arrays f�r logs eranlegen
		m_alErrorLogFile = new ArrayList();
		m_alLogFile = new ArrayList();

		// --- Einlesen und der CSV-Datei
		HashMap hashImportOrderList = _readInputFile(request);

		// --- Initialize
		m_oXmlSupport = new XmlSupport();
		Collection Produkte_nicht_vorhanden = new ArrayList();
		Collection Produkte_vorhanden = new ArrayList();
		HashMap hashmapValues = null;
		HashMap hashMapOrders = null;
		Product oResultProduct = null;
		String strPK = "";
		ArrayList collectionMasterProduct = new ArrayList();
		HashMap hashmapMasterAlpha = new HashMap();

		m_alLogFile.add("");
		m_alLogFile.add("Liste der importierten Produkte");



		// --- Setzen der Sprache
		String strLanguage = "de";
		SetLanguage(strLanguage);
		String strFileName = (String) hashImportOrderList.get("strFileName");

		// --- DEBUG
		LOG.info("\n\nproduktImportPreisdatenCSV gestartet...");
		LOG.info("Sprache=" + strLanguage);
		LOG.info("strCatalog Preisliste=" + strCatalog);
		LOG.info("strCatalogversion Preisliste=" + strCatalogversion);
		LOG.info("strFileName=" + strFileName);


		// --- Einlesen und parsen der Preislistn CSV-Datei
		HashMap hashmapPreisliste 						= _readInputFilePreisliste(strFileName);
		HashMap<Integer,String> hashmapInputPreislisten	= (HashMap<Integer,String>) hashmapPreisliste.get("preislisten");
		HashMap hashmapInputPreiszeilen					= (HashMap) hashmapPreisliste.get("preiszeilen");

		// --- Prüfe Preislisten Eingabedatei mit Preislisten aktueller Katalog -------------------------------------------------------
		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final CatalogVersion weraCatalogVersion = getCatalogVersion(strCatalog, strCatalogversion);
		Collection<Item> catalogPricelists = (Collection<Item>) getAttribute(weraCatalogVersion,"upg4csv");
		LOG.info("Anzahl Preislisten in Hybris =" + catalogPricelists.size());
		HashSet	hashSetPreislistenHybris = new HashSet();
		for (Item upg : catalogPricelists ) {
			LOG.info("Preisliste (HYBRIS)=" + (String)getAttribute(upg,"code") );
			hashSetPreislistenHybris.add((String)getAttribute(upg,"code"));
		}

		// --- DEBUG
		LOG.info( "Anzahl Artikel=" + hashmapInputPreiszeilen.keySet().size());
		m_alErrorLogFile.add( "Anzahl Artikel=" + hashmapInputPreiszeilen.keySet().size() );
		LOG.info( "Anzahl Preislisten Eingabedatei=" + hashmapInputPreislisten.keySet().size());
		m_alErrorLogFile.add( "Anzahl Preislisten Eingabedatei=" + hashmapInputPreislisten.keySet().size() );
		for (Integer preisliste : hashmapInputPreislisten.keySet() ) {

			// --- Preisliste prüfen
			LOG.info("Preisliste (EINGABE)=" + hashmapInputPreislisten.get(preisliste) );
			if ( !hashSetPreislistenHybris.contains( hashmapInputPreislisten.get(preisliste)) ) {
				LOG.info( "Preisliste unbekannt: " + hashmapInputPreislisten.get(preisliste)  + ", Daten werden ignoriert") ;
				m_alErrorLogFile.add( "Eingabepreislisten (Headerzeilen) wurden geprüft und sind in Ordnung" );
				hashmapInputPreiszeilen.remove( hashmapInputPreislisten.get(preisliste) );
			}

		} // for (Integer preisliste : hashmapInputPreislisten.keySet() ) {


		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Preislisten prüfen und Preise importieren (START)
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		 * Preislisten ok, Preiszeilen werden importiert
		 */

		// --- Schleife über alle Produkte ---------------------------------------------------------
		for (Iterator it1 = hashmapInputPreiszeilen.keySet().iterator(); it1.hasNext();) {

			// --- hole values
			strPK 			= (String) it1.next();
			hashmapValues 	= (HashMap) hashmapInputPreiszeilen.get(strPK);
/*
			for (Iterator it2 = hashmapValues.keySet().iterator(); it2.hasNext();) {
				String strKey 			= (String) it2.next();
				LOG.info( "hashmapValues.strKey: " + strKey ) ;
			}
*/

			// --- hole Produktkennzahlen
			String sArtikelNr 	=  (String) hashmapValues.get("ArtikelNr");
			String sCode 		=  (String) hashmapValues.get("CodeNr");
			String strPK_SIS 	= (String) hashmapValues.get("PK_SIS");

			//
			// --- hole Produkt -----------------------------------------------------------------------------------
			Product product = null;
			Product product_sis = null;
			LOG.info("++PK=" + strPK + ", PK_SIS=" + strPK_SIS + ", sArtikelNr=" + sArtikelNr + ", sCode=" + sCode);
			m_alErrorLogFile.add("++PK=" + strPK + ", PK_SIS=" + strPK_SIS + ", sArtikelNr=" + sArtikelNr + ", sCode=" + sCode);
			if (strPK.equals("")) {
				LOG.warn("++skip Row, empty PK!!");
				continue;
			}
			product 	= (Product) JaloSession.getCurrentSession().getItem(strPK);
			product_sis	= null;
			if (strPK_SIS != null && !strPK_SIS.equals("")) {
				product_sis = (Product) JaloSession.getCurrentSession().getItem(strPK_SIS);
			}
			if ( product == null ) {
				Produkte_nicht_vorhanden.add(sArtikelNr + " => " + sCode);
				LOG.warn(sArtikelNr + " => " + sCode + " not found");
				continue;
			}


			//
			// --- schleife über alle Inputpreislisten des produkts ----------------------------------------------
			for (Integer preisliste : hashmapInputPreislisten.keySet() ) {

				// --- preisliste holen
				String sPriceList	= hashmapInputPreislisten.get(preisliste);

				// --- Preisliste prüfen
				LOG.info("Preisliste (EINGABE)=" + hashmapInputPreislisten.get(preisliste) );
				if ( !hashSetPreislistenHybris.contains( hashmapInputPreislisten.get(preisliste)) ) {
					// --- ignore
					continue;
				}
				LOG.info( "Preise werden importiert: " + sPriceList ) ;
				m_alErrorLogFile.add("Preise werden importiert: " + sPriceList );

				// --- hole preiszeilen
				HashMap hashMapPreiszeilen	= (HashMap)hashmapValues.get(sPriceList);
/*
				for (Iterator it3 = hashMapPreiszeilen.keySet().iterator(); it3.hasNext();) {
					String strKey 			= (String) it3.next();
					LOG.info( "hashMapPreiszeilen.strKey: " + strKey ) ;
				}
*/

				/////////////////////////////////////////////////////////////////////////////////////////////////
				// --- Ab hier alle Artikel / Satz spezifischen Daten importieren
				/////////////////////////////////////////////////////////////////////////////////////////////////
				//
				// -------------------------------------------------------------------------------------
				// --- EAN, VPE, Auslauf-/ NeuFlag
				// -------------------------------------------------------------------------------------
				// --- EAN, import only if hashmapValues
				String strEAN = (String) hashmapValues.get("EAN");
				if (strEAN != null && !strEAN.equals("") && !strEAN.equals("0") ) {

					// ---- set value
					setAttribute(product, "EAN", strEAN.trim() );
				}

				// --- Auslauf Artikel / Produkt, import only if allowed
				String sIsAuslaufItem = (String) hashmapValues.get("Auslaufartikel");
				boolean bIsAuslaufItem = false;
				if (sIsAuslaufItem != null && !sIsAuslaufItem.equals("") && !sIsAuslaufItem.equals("0") ) {

					String sAttributeForAuslaufProperty = (product instanceof WeraVariante ? "artikel_auslauf"
							: "artikel_auslauf");
					bIsAuslaufItem = (sIsAuslaufItem != null && sIsAuslaufItem.toLowerCase().equals("true"));

					// ---- set value
					setAttribute(product, sAttributeForAuslaufProperty, new Boolean(bIsAuslaufItem));
				}

				// --- Neuer Artikel / Produkt, import only if allowed
				String sIsNewItem = (String) hashmapValues.get("Neu");
				if (sIsNewItem != null && !sIsNewItem.equals("") && !sIsNewItem.equals("0") ) {
					String sAttributeForNeuProperty = (product instanceof WeraVariante ? "artikel_neu" : "produkt_neu");
					boolean bIsNewItem = (sIsNewItem != null && sIsNewItem.toLowerCase().equals("true"));
					if (bIsAuslaufItem) // --- Auslaufartikel, nicht neu!!!
					{
						bIsNewItem = false;
					}

					// ---- set value
					setAttribute(product, sAttributeForNeuProperty, new Boolean(bIsNewItem));
				}

				// --- Verpackungseinheit, import only if allowed --------------------------------------------
				String strVPE = (String) hashmapValues.get("VPE");
				if ( strVPE != null && !strVPE.trim().equals("") && !strVPE.trim().equals("0") ) {

					// --- VPE
					Integer intContentQuantity = new Integer(strVPE.trim());

					if ( product_sis != null ) {

						Boolean bIstDisplay = null;
						if (product_sis instanceof WeraProductSetinSet) {
							bIstDisplay = (Boolean) getAttribute(product_sis, "ist_display");
						}
						if (bIstDisplay == null) {
							bIstDisplay = new Boolean(false);
						}
						if (product_sis instanceof WeraProductSetinSet && bIstDisplay.booleanValue() == false) {

							// ---- set value
							LOG.info( "IST WeraProductSetinSet - try vpe=" + intContentQuantity );

							// --- Satz in Satz (Liste der S�tze
							Collection articles = (Collection) getAttribute(product_sis, "weraproductsetvariants_qual");

							// --- Schleife �ber alle Artikel
							WeraProductSetVariants oWeraProductSetVariants = null;
							WeraProductSet oWeraProductset = null;
							for (Iterator it2 = articles.iterator(); it2.hasNext();) {

								// --- Hole den aktuellen Artikel
								oWeraProductSetVariants = (WeraProductSetVariants) it2.next();
								oWeraProductset = (WeraProductSet) getAttribute(oWeraProductSetVariants, "weraproductsets");

								// --- F�lle einen Artikel
								if ( oWeraProductset != null ) {

									String lagerNr		= (String) getAttribute( oWeraProductset, "lagerNr" );
									String codeNr		= (String) getAttribute( oWeraProductset, "artnr" );
									String variantenNr	= (String) getAttribute( oWeraProductset, "variantenNr" );
									String codeNrGesamt	= lagerNr + codeNr + variantenNr;
									LOG.info( "vergleich codeNrGesamt=" + codeNrGesamt + " / sCode=" + sCode);
									if ( codeNrGesamt.equals(sCode) ) {

										setAttribute(oWeraProductSetVariants, "vpe", intContentQuantity );
										LOG.info("found, ++class (satz)=" + oWeraProductset.getClass().getName());
										LOG.info( "IST WeraProductSetinSet - try vpe=" + intContentQuantity );
										break;
									}
								}
							}

						} else {

							// ---- set value
							LOG.info( "IST WeraProductSetinSet - set contentQuantity=" + intContentQuantity );
							setAttribute(product_sis, "contentQuantity", intContentQuantity);
						}
						// ---- set value

					} else {

						// ---- set value
						setAttribute(product, "contentQuantity", intContentQuantity);
					}
				}
				// --- Verpackungseinheit, import only if allowed --------------------------------------------


				//
				// -------------------------------------------------------------------------------------
				// --- Preisdaten
				// -------------------------------------------------------------------------------------

				// --- Delete prices for product's pricelist
				int iNumPriceRowsDeleted = this.deletePriceRows(product,
						sPriceList);
				if (iNumPriceRowsDeleted > 0) {
					LOG.info("Deleted " + iNumPriceRowsDeleted
							+ " price rows for " + sPriceList
							+ ", Product " + product.getCode());
				}

				// --- währung
				String sWaehrung =  (String) hashMapPreiszeilen.get("Waehrung");

				// --- Preise einpflegen
				//     Netto Preise auslesen
				for (int iPriceCol = 1; iPriceCol <= 5; iPriceCol++) {
					String strKeyPreis = "Preis_S" + iPriceCol;
					String strKeyQty = "Menge_S" + iPriceCol;
					//LOG.info("strKeyPreis=" + articleData.get(strKeyPreis) + "=" );
					//LOG.info("strKeyQty ("+strKeyQty+")=" + articleData.get(strKeyQty) + "=" );

					// --- Reihenfolge / Netto / Brutto
					int iPriority = iPriceCol * 10;

					// --- Menge
					boolean isAufAnfrage = false;
					String sMengeValue = (String) hashMapPreiszeilen.get(strKeyQty);
					if (sMengeValue != null) {
						sMengeValue = sMengeValue.trim();
					}
					if (sMengeValue != null
							&& (sMengeValue.toLowerCase().equals(
							"auf anfrage") || sMengeValue.toLowerCase().equals("a.A."))) {
						isAufAnfrage = true;
					}
					if (sMengeValue == null || sMengeValue.length() == 0
							|| sMengeValue.equals("-")) {
						continue;
					}

					int iMinQty = 0;
					if (isAufAnfrage == false || sMengeValue.length() > 0) {
						iMinQty = Integer.parseInt(sMengeValue);
					}


					// --- Preis
					String sPriceValue = (String) hashMapPreiszeilen.get(strKeyPreis);
					if (sPriceValue != null) {
						sPriceValue = sPriceValue.trim();
					}
					if (sPriceValue == null || sPriceValue.length() == 0
							|| sPriceValue.equals("-")) {
						LOG.info("skip insert PriceRow" + iPriceCol + "=");
						continue;
					}
					// --- Nur der letzte Punkt zählt bei Fliesskommazahlen
					sPriceValue = sPriceValue.trim().replace(",", ".");
					while (sPriceValue.indexOf('.') != sPriceValue
							.lastIndexOf('.')) {
						sPriceValue = sPriceValue.replaceFirst("\\.", "");
					}
					double dPrice = Double.parseDouble(sPriceValue);

					// --- Preiszeile anlegen
					boolean bNet = true;
					boolean bIsEVK = false;
					insertPriceRow(product, iPriority,
							sPriceList, iMinQty, bNet, dPrice, bIsEVK,
							isAufAnfrage, sWaehrung);
				}

				// --- Brutto Preise übernehmen
				// --- Preiszeile anlegen
				LOG.info("import Brutto");
				LOG.info("Brutto EVP=" + hashMapPreiszeilen.get("EVP") + "=" );
				LOG.info("Brutto VPE=" + hashMapPreiszeilen.get("VPE") + "=" );
				boolean bNet = false;
				boolean bIsEVK = true;
				int iPriority = 1000;
				int iMinQty = 1;
				boolean isAufAnfrage = true;
				double dPrice = 0;
				String sPriceValue = (String) hashMapPreiszeilen.get("EVP");
				if (sPriceValue == null
						|| sPriceValue.toLowerCase().equals("auf anfrage")
						|| sPriceValue.toLowerCase().equals("a.a.")
						|| sPriceValue.trim().equals("")) {
					isAufAnfrage = true;
				} else {
					isAufAnfrage = false;
					// --- Preis formatieren
					sPriceValue = sPriceValue.trim().replace(",", ".");
					while (sPriceValue.indexOf('.') != sPriceValue
							.lastIndexOf('.')) {
						sPriceValue = sPriceValue.replaceFirst("\\.", "");
					}
					dPrice = Double.parseDouble(sPriceValue);
				}
				if (sPriceValue == null || sPriceValue.length() == 0
						|| sPriceValue.equals("-")) {
					LOG.warn("skip insert PriceRow Brutto=" + "=");
				} else {
					insertPriceRow(product, iPriority,
							sPriceList, iMinQty, bNet, dPrice, bIsEVK,
							isAufAnfrage, sWaehrung);
				}


			} // for (Integer preisliste : hashmapInputPreislisten.keySet() ) {
//System.exit(0);

		} // for (Iterator it1 = hashmapInputPreiszeilen.keySet().iterator(); it1.hasNext();) {
		//
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		// Preislisten prüfen und Preise importieren (ENDE)
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

		// --- protokoll
		if ( Produkte_nicht_vorhanden.size() > 0 ) {

			m_alErrorLogFile.add( "Liste der nicht vorhandenen Produkte \n");
			for (Iterator it2 = Produkte_nicht_vorhanden.iterator(); it2
					.hasNext(); ) {
				String strCodeNr = (String) it2.next();
				m_alErrorLogFile.add( strCodeNr );
			}
		}


		// --- Ausgabepfad generieren
		String strResultPath = "index_" + strLanguage;

		// --- DEBUG
		LOG.info("produktImportPreisdatenCSV beendet...");

		// --- result auswerten
		String strResult	= "";
		if ( m_alErrorLogFile.size() > 0 ) {

			strResult	= "Report";
			for (Iterator itTmp =m_alErrorLogFile.iterator(); itTmp.hasNext();) {
				// --- Zeilen zusammenbauen
				strResult += (String) itTmp.next() + "<br>";
			}
		} else {
			strResult	= "Import OK!";
		}
		return strResult;
	}

	/**
	 * IMportieren der Produktreihenfolge in eine Katalogversion
	 *
	 * @param HttpServletRequest request
	 * @param String             strCatalog
	 * @param String             strCatalogversion
	 * @return
	 */
	public String produktImportOrderCSV(HttpServletRequest request, final String strCatalog, final String strCatalogversion) {

		// --- Arrays f�r logs eranlegen
		m_alErrorLogFile = new ArrayList();
		m_alLogFile = new ArrayList();

		// --- Einlesen und parsen der CSV-Datei
		HashMap hashImportOrderList = _readInputFile(request);

		// --- Initialize
		m_oXmlSupport = new XmlSupport();
		HashMap hashmapValues = null;
		HashMap hashMapOrders = null;
		Product oResultProduct = null;
		String strPK = "";
		ArrayList collectionMasterProduct = new ArrayList();
		HashMap hashmapMasterAlpha = new HashMap();

		m_alLogFile.add("");
		m_alLogFile.add("Liste der importierten Produkte");

		// --- Setzen der Sprache
		String strLanguage = "de";
		SetLanguage(strLanguage);
		String strFileName = (String) hashImportOrderList.get("strFileName");

		// --- DEBUG
		LOG.info("\n\nproduktImportOrderCSV gestartet...");
		LOG.info("Sprache=" + strLanguage);
		LOG.info("strCatalog Preisliste=" + strCatalog);
		LOG.info("strCatalogversion Preisliste=" + strCatalogversion);
		LOG.info("strFileName=" + strFileName);



		// --- Einlesen und parsen der Indesign-Datei
		HashMap hashMasterCode = _readInputFileOrder(strFileName);

		// --- DEBUG
		LOG.info("Anzahl Artikel=" + hashMasterCode.keySet().size());
		m_alErrorLogFile.add( "Anzahl Artikel=" + hashMasterCode.keySet().size() );

		// --- Schleife �ber alle Wera-CODE-Nummers (KEYS)
		LOG.info("Produkte werden zusammengestellt...");
		for (Iterator it1 = hashMasterCode.keySet().iterator(); it1.hasNext();) {

			// --- hole valuess
			strPK 			= (String) it1.next();
			hashmapValues 	= (HashMap) hashMasterCode.get(strPK);

			// --- Holen des Produktes
			oResultProduct	 = (Product) JaloSession.getCurrentSession().getItem(strPK);
			if ( oResultProduct == null ) {
				LOG.error("PK=" + strPK + ", code"  + hashmapValues.get("Code") + " wurde nict gefunden, Reihenfolge wird ignoriert." );
				m_alErrorLogFile.add("FEHLER: PK=" + strPK + ", code"  + hashmapValues.get("Code") + " wurde nict gefunden, Reihenfolge wird ignoriert.");
				continue;
			}

			// --- Holen der Order-Liste
			hashMapOrders = (HashMap) hashmapValues.get("orders");

			// --- alles ok, wir können die Daten laden!
			LOG.info("PK=" + strPK + hashmapValues.get("Code") + " wurde gefunden." );
			LOG.info("orders=" + hashMapOrders );

			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// daten übernehmen (START)
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

			// --- Initialize
			final Collection category2ProductexResult = new ArrayList();
			Category2ProductExt category2productext = null;
			Category2ProductExt oResult = null;
			Category oCategoryTmp = null;
			String strCatalogVersionTmp = null;
			String strCategory 			= null;
			String strOrder 			= null;

			try {
				final Collection category2productexts = (Collection) oResultProduct.getAttribute("category2productexts");
				if (category2productexts != null && category2productexts.size() > 0) {
					// --- Schleife über alle Steuerparameter
					for (final Iterator itcategory2Poductext = category2productexts.iterator(); itcategory2Poductext.hasNext();) {
						category2productext = (Category2ProductExt) itcategory2Poductext.next();

						// --- Pr�fe, ob der Eintrag zur Category passt
						strCatalogVersionTmp 	= (String) category2productext.getAttribute("catalogversion_desc");
						oCategoryTmp 			= (Category) category2productext.getAttribute("category");

						// --- schleife über alle Categorien
						for (Iterator itOrders = hashMapOrders.keySet().iterator(); itOrders.hasNext();) {

							strCategory 			= (String) itOrders.next();
							strOrder 				= (String) hashMapOrders.get(strCategory);

							// --- prüfen ob die Kategory gefunden wurde!
							if ( oCategoryTmp != null && oCategoryTmp.getCode().equals(strCategory) && strCatalogVersionTmp.equals(strCatalogversion) ) {
								//LOG.info("found oCategoryTmp.getCode()=" + oCategoryTmp.getCode() );
								//LOG.info("neue sstrOrder=" + strOrder );
								hashMapOrders.remove(strCategory);

								// --- order setzen
								category2productext.setAttribute("priority", strOrder);
								break;
							}

						} // for (Iterator itOrders = hashMapOrders.keySet().iterator(); itOrders.hasNext();) {

						// --- element merken
						category2ProductexResult.add(category2productext);

						/*
						// --- wurden alle gefunden dann hier abbrechen
						if ( hashMapOrders.size() == 0) {
							//LOG.info("found all, we stop here!" );
							break;
						};
						*/
					}
				}

				// --- Konnten alle Orders zugeordnet werden
				if ( hashMapOrders.size() > 0 ) {

					// --- hier sind Fehler aufgetreten
					m_alErrorLogFile.add("FEHLER: PK=" + strPK + hashmapValues.get("Code") + " wurde nicht komplett importiert!");
					for (Iterator itOrders1 = hashMapOrders.keySet().iterator(); itOrders1.hasNext();) {

						strCategory 			= (String) itOrders1.next();
						strOrder 				= (String) hashMapOrders.get(strCategory);

						m_alErrorLogFile.add("Kategorie: " + strCategory + "Order: " + strOrder);
					}

				} else {

					m_alErrorLogFile.add("OK: PK=" + strPK + hashmapValues.get("Code") + " wurde fehlerfrei importiert!");
				}

				// --- Eintragen
				oResultProduct.setAttribute( "category2productexts", category2ProductexResult);

			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// daten übernehmen (START)
			////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


		}



		// --- Ausgabepfad generieren
		String strResultPath = "index_" + strLanguage;

		// --- DEBUG
		LOG.info("produktImportOrderCSV beendet...");

		// --- result auswerten
		String strResult	= "";
		if ( m_alErrorLogFile.size() > 0 ) {

			strResult	= "Report";
			for (Iterator itTmp =m_alErrorLogFile.iterator(); itTmp.hasNext();) {
				// --- Zeilen zusammenbauen
				strResult += (String) itTmp.next() + "<br>";
			}
		} else {
			strResult	= "Import OK!";
		}
		return strResult;
	}

	/**
	 * Erstellen der Indexdatei f�r Preisliste
	 * @param request
	 * @param strKatalog
	 * @param strLanguage
	 * @return
	 */
	public String CreateIndexPL(HttpServletRequest request, String strKatalog, String strLanguage) {

		// --- Einlesen und parsen der Indesign-Datei
		HashMap hashPriceList = _readInputFile(request);

		// --- Arrays f�r logs eranlegen
		m_alErrorLogFile = new ArrayList();
		m_alLogFile = new ArrayList();

		// --- Initialize
		m_oXmlSupport = new XmlSupport();
		HashMap hashmapValues = null;
		Product oResultProduct = null;
		String strKey = "";
		ArrayList collectionMasterProduct = new ArrayList();
		HashMap hashmapMasterAlpha = new HashMap();
		m_alErrorLogFile.add("Fehlerreport");
		m_alLogFile.add("");
		m_alLogFile.add("Liste der indizierten Produkte");

		// --- Setzen der Sprache
		if (strLanguage == null) {
			strLanguage = m_strLanguage;
		}
		strLanguage = "it";
		m_strLanguage = "it";
		SetLanguage(strLanguage);
		String strReferenzKatalog = (String) hashPriceList.get("refcatalog");
		if ( strReferenzKatalog == null ) {
			strReferenzKatalog = "print/hauptkatalog_seitenzahlen_2020";
		}
		String[] aRefcatalog = strReferenzKatalog.split("/");
		String strRefcatalogPrint = aRefcatalog[0];
		String strRefcatalogVersionPrint = aRefcatalog[1];
		String strFileName = (String) hashPriceList.get("strFileName");

		// --- DEBUG
		LOG.info("\n\nINDEXERSTELLUNG gestartet...");
		LOG.info("Sprache=" + strLanguage);
		LOG.info("Katalog Preisliste=" + strKatalog);
		LOG.info("strRefcatalogPrint=" + strRefcatalogPrint);
		LOG.info("strRefcatalogVersionPrint=" + strRefcatalogVersionPrint);
		LOG.info("strFileName=" + strFileName);

		// --- Einlesen und parsen der Indesign-Datei
		HashMap hashMasterCode = _readInputFilePL(strFileName);

		// --- DEBUG
		LOG.info("Anzahl Artikel=" + hashMasterCode.keySet().size());

		// --- Schleife �ber alle Wera-CODE-Nummers (KEYS)
		LOG.info("Produkte werden zusammengestellt...");
		for (Iterator it1 = hashMasterCode.keySet().iterator(); it1.hasNext();) {
			strKey = (String) it1.next();
			hashmapValues = (HashMap) hashMasterCode.get(strKey);

			// --- Suchen des zugh�rigen Produktes
			oResultProduct = (Product) hashmapValues.get("product");
			if (oResultProduct == null) {
				LOG.info("oResultProduct (null).key=" + strKey );
				// --- Pordukt / Satz suchen
				oResultProduct = _SearchProduct(strKey, strKatalog);
				if (oResultProduct != null) // --- Produkt merken
				{
					LOG.info("oResultProduct (!=null).key=" + oResultProduct.getCode() );
					_registerProdukt(oResultProduct, hashmapValues, hashMasterCode, collectionMasterProduct, hashmapMasterAlpha, strKatalog, strRefcatalogVersionPrint);
				}
			} else {
				LOG.info("oResultProduct.getCode()=" + oResultProduct.getCode());
			}

			// --- Error-LOG
			if (oResultProduct == null) {
				m_alErrorLogFile.add("Artikel " + strKey + " wurde nicht gefunden und nicht in den Index aufgenommen.");
				LOG.info("++Artikel " + strKey + " wurde nicht gefunden und nicht in den Index aufgenommen.");
			}

		}

		// --- Ausgabepfad generieren
		String strResultPath = "index_" + strLanguage;
		String strOutputPath = _CreateOutputPath(strLanguage, strKatalog);

		// --- Index nach CODE-Nummern erstellen
		LOG.info("CODE - Index wird geschrieben...");
		_createCodeIndex(hashMasterCode, strOutputPath);

		// --- Index Nummerisch erstellen
		LOG.info("Nummerischer - Index wird geschrieben...");
		_createNumIndex(collectionMasterProduct, strOutputPath, strLanguage);

		// --- Fehlerdatei schreiben
		LOG.info("Report wird geschrieben...");
		m_alErrorLogFile.addAll(m_alLogFile);
		m_oXmlSupport._WriteFileFromArray(m_alErrorLogFile, strOutputPath + "/readme.txt");

		// --- DEBUG
		LOG.info("INDEXERSTELLUNG beendet...");

		return strResultPath;
	}

	/**
	 * Generieren des Ausgabepfades und anlegen der Verzeichnisse
	 * @param strLanguage
	 * @param strCatalogversion
	 * @return 
	 */
	public String _CreateOutputPath(String strLanguage, String strCatalogversion) {

		String strPath1 = Config.getParameter("wera.homepath")
				+ "/export/katalog/"
				+ strCatalogversion.toLowerCase();
		String strPath2 = strPath1 + "/index_" + strLanguage;

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
	private void _createCodeIndex(HashMap hashMasterCode, String strOutputPath) {

		// --- Initialize
		String strLine = "";
		HashMap hashmapValues = null;
		HashSet hashsetPages = null;
		HashSet hashsetPagesKL = null;
		ArrayList aIndex = new ArrayList();
		String strPage = "";
		String strPageKL = "";

		// --- Hole Liste der Code-Nummrs
		ArrayList aListCode = new ArrayList();
		aListCode.addAll(hashMasterCode.keySet());

		// --- Sortieren der CODE-Nummerns
		Collections.sort(aListCode);

		// --- Schleife �ber alle Code-nummern
		String strCodeNr = "";
		for (Iterator it1 = aListCode.iterator(); it1.hasNext();) {
			strCodeNr = (String) it1.next();
			hashmapValues = (HashMap) hashMasterCode.get(strCodeNr);
			hashsetPages = (HashSet) hashmapValues.get("pages");
			strPageKL = (String) hashmapValues.get("pagesKL");

			// --- Schleife �ber alle Seitenzahlen
			strLine = strCodeNr + "\t" + strPageKL + "\t";

			// --- Preislistenseiten
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
	private String _genPageStr(HashSet hashsetPages) {

		// --- Initialize
		IntComparator oIntComparator = new IntComparator();
		String strPages = "";
		Object[] aPages = null;

		aPages = hashsetPages.toArray();
		Arrays.sort(aPages, oIntComparator);
		for (int iPos = 0; iPos < aPages.length; iPos++) {
			String strPage = aPages[iPos].toString();
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
	private void _createNumIndex(ArrayList collectionMasterProduct, String strOutputPath, String currentLanguage ) {

		// --- Initialize
		IntComparator oIntComparator = new IntComparator();
		HashMapComparator oHashMapComparator = new HashMapComparator();
		String strLine = "";
		HashSet exportCodeNr	= new HashSet();
		HashMap hashmapValues = null;
		HashSet hashsetPages = null;
		HashSet hashsetPagesKL = null;
		Object[] aPages = null;
		ArrayList aIndex = new ArrayList();
		String strPage = "";
		String strPageKL = "";
		String strPageKL_Platzhalter = "";

		// --- Sortieren der Liste
		Collections.sort(collectionMasterProduct, oHashMapComparator);

		// --- Schleife �ber alle Code-nummern
		String strCodeNr			= "";
		String strCodeNrLocalized	= "";
		for (Iterator it1 = collectionMasterProduct.iterator(); it1.hasNext();) {

			hashmapValues = (HashMap) it1.next();

			// --- artikel nummer
			strCodeNr				= (String) hashmapValues.get("code");
			strCodeNrLocalized		= (String) hashmapValues.get("code_localized");
LOG.info("strCodeNr >" + strCodeNr + "<");
LOG.info("strCodeNrLocalized >" + strCodeNrLocalized + "<");
			hashsetPages			= (HashSet) hashmapValues.get("pages");
			strPageKL				= (String) hashmapValues.get("pagesKL");
			strPageKL_Platzhalter	= (String) hashmapValues.get("pagesKL_platzhalter");

			// --- Katalogseiten nachtr�glich einf�gen 
			if ( m_platzhalterSeitennummer ) {
				strPageKL	= strPageKL_Platzhalter;
			}
			
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

			// --- Schleife �ber alle Seitenzahlen
			strLine = strCodeNr + "\t" + strPageKL + "\t";

			// --- Schleife �ber alle Seitenzahlen
			aPages = hashsetPages.toArray();
			Arrays.sort(aPages, oIntComparator);

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
	 * @param resultProduct
	 * @param hashmapValues
	 * @param hashMasterCode
	 * @param collectionMasterProduct
	 * @param hashmapMasterAlpha
	 * @param strKatalog
	 * @param strReferenzCatalogVersion 
	 */
	private void _registerProdukt(Product resultProduct, HashMap hashmapValues, HashMap hashMasterCode,
			ArrayList collectionMasterProduct, HashMap hashmapMasterAlpha,
			String strKatalog, String strReferenzCatalogVersion) {
		// TODO Auto-generated method stub
		// --- Logdatei erstellen
		m_alLogFile.add(resultProduct.getCode());

		// --- Hole Seitenzahlen aus Referenzkatalog
		// --- Pr�fe, ob Katalogversion bereits vorhanden ist
		// --- Hole alle Kategorien
		String strPagesKL = "";
		if (strReferenzCatalogVersion != null) {
			Collection category2productexts = ((WeraProduct) resultProduct).getCategory2ProductextsByCatalogVersion(strReferenzCatalogVersion);
			if (category2productexts != null && category2productexts.size() > 0) {
				Category2ProductExt category2productext = (Category2ProductExt) category2productexts.iterator().next();
				strPagesKL = category2productext.getPagenr_catalog();
				if (strPagesKL == null || strPagesKL == "") {
					strPagesKL = "-";
				}
			}
		}
		hashmapValues.put("pagesKL", strPagesKL);

		String firstCodeNummer	= "";
		if (resultProduct instanceof WeraProductSet) // --- register Satz
		{
			hashmapValues.put("product", resultProduct);
			
			// --- hole codenummer Satz
			firstCodeNummer	= (String)getAttribute(resultProduct, "artnr");
			
		} else {
			// --- Optimized register of all variants / products relations
			// --- Initialize
			String strCode = "";
			HashMap hashmapValuesOpt = null;

			// --- Hole alle vorhanden Varianten des Produkts
			Collection variants = (Collection) getAttribute(resultProduct, "variants");
			VariantProduct variantproduct = null;
			for (Iterator it1 = variants.iterator(); it1.hasNext();) {
				variantproduct = (VariantProduct) it1.next();
				strCode = variantproduct.getCode();
				
				// --- hole codenummer 1. Variante
				if ( firstCodeNummer == "" ) {
					
					firstCodeNummer	= (String)strCode;
				} 
			
				hashmapValuesOpt = (HashMap) hashMasterCode.get("05" + strCode + "001");
				if (hashmapValuesOpt != null) {
					// --- found and register
					hashmapValuesOpt.put("pagesKL", strPagesKL);
					hashmapValuesOpt.put("product", resultProduct);
					hashMasterCode.put("05" + strCode + "001", hashmapValuesOpt);
				}
			}
		}

		// --- Num-Index, sorted By Order
		String strCode = resultProduct.getCode();
		hashmapValues.put("code", strCode);
		String strArtikelnrIndex = (String)getAttribute(resultProduct, "artikelnr_index");
		hashmapValues.put("code_localized", strArtikelnrIndex);
		hashmapValues.put("pagesKL_platzhalter", "##KS_05" + firstCodeNummer + "001_KS##" );

		// --- 
		Integer iOrder = null;
		String strOrder = "";
		Collection category2productexts = ((WeraProduct) resultProduct).getCategory2ProductextsByCatalogVersion(strKatalog);
		if (category2productexts != null && category2productexts.size() > 0) {
			Category2ProductExt category2productext = (Category2ProductExt) category2productexts.iterator().next();
			strOrder = category2productext.getPriority();
			if (strOrder == null) {
				strOrder = "0";
			}
			iOrder = new Integer(strOrder);
		} else {
			Double dOrder = (Double) getAttribute(resultProduct, "priority");
			if (dOrder == null) {
				iOrder = new Integer(0);
			} else {
				iOrder = new Integer(dOrder.intValue());
			}
		}
		if (iOrder == null) {
			iOrder = new Integer(0);
		}
		hashmapValues.put("sortkey0", iOrder);
		/*		
		// --- Num-Index, sorted by CODE
		hashmapValues.put("code",strCode);
		strCode = ((WeraProduct)resultProduct).normalizeFilenameForImageLookup();
		strCode = strCode.replace("-","_");
		String[] aString = strCode.split("_");
		String strSortKey1 = aString[0].trim();
	    hashmapValues.put("sortkey0", strSortKey1.substring(0,1) );
		if ( strSortKey1.charAt(0) <= '9' ) {
		   strSortKey1 = strSortKey1.replaceAll("[a-zA-Z]","");
		   strSortKey1 = "000000000000" + strSortKey1;
		   hashmapValues.put("sortkey1", strSortKey1.substring(strSortKey1.length()-10 )  );
		}
		else 
		   hashmapValues.put("sortkey1", strSortKey1.toLowerCase()  );
		String strSortKey2 = "";
		for ( int iPos=1; iPos < aString.length; iPos++ )
			strSortKey2 += aString[iPos].trim();
        hashmapValues.put("sortkey2", strSortKey2.toLowerCase()  );
		 */
		collectionMasterProduct.add(hashmapValues);

	}

	/**
	 * Einlesen und parsen der Indesign-Datei
	 * @param strFileName
	 * @return 
	 */
	private HashMap _readInputFilePL(String strFileName) {

		// --- Initialize
		HashMap hashInput = new HashMap();
		HashMap hashmapValues = null;
		HashSet hashsetPages = null;

		try {

			// --- Einlesen der Datei
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(strFileName));
			int chunkSize = 327680;
			byte[] chunk = new byte[chunkSize];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int size = -1;
			while ((size = in.read(chunk, 0, chunkSize)) > 0) {
				baos.write(chunk, 0, size);
			}

			// --- Parsen der Index-Zeilen
			String strKey = "";
			String strValue = "";
			String[] aList = baos.toString().split("\n");
			for (int iPos = 0; iPos < aList.length; iPos++) {
				// --- Parse Parameter Spracge
				if (aList[iPos].toString().indexOf("language") >= 0) {
					iPos += 2;
					m_strLanguage = aList[iPos].toString().trim();
				}

				String[] aLine = aList[iPos].split("\t");
				if (aLine != null && aLine.length >= 2) {
					strKey = aLine[0];
					strKey = strKey.replace(" ", "").replace("-", "").replace(",", "");
					strValue = aLine[1];
					strValue = strValue.replace(" ", "").replace("-", "").replace(",", "");

					// --- Hashes aktulisieren
					if (hashInput.containsKey(strKey)) {
						hashmapValues = (HashMap) hashInput.get(strKey);
						hashsetPages = (HashSet) hashmapValues.get("pages");
					} else {
						hashmapValues = new HashMap();
						hashsetPages = new HashSet();
					}
					LOG.info("strKey=" + strKey + ", strValue=" + strValue);
					if (strValue.length() > 0) {
						hashsetPages.add(new Integer(strValue.replace("\r", "")));
					}

					// --- Wert merken
					hashmapValues.put("pages", hashsetPages);
					hashmapValues.put("product", null);
					hashInput.put(strKey, hashmapValues);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hashInput;
	}

	/**
	 * Einlesen und parsen der Order-Datei
	 *
	 * @param String strFileName
	 * @return HashMap hashInput
	 */
	private HashMap _readInputFileOrder(String strFileName) {

		// --- Initialize
		HashMap hashInput = new HashMap();
		HashMap hashmapValues = null;
		HashMap hashmapOrders = null;

		try {

			// --- Einlesen der Datei
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(strFileName));
			int chunkSize = 327680;
			byte[] chunk = new byte[chunkSize];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int size = -1;
			while ((size = in.read(chunk, 0, chunkSize)) > 0) {
				baos.write(chunk, 0, size);
			}

			// --- Parsen der Index-Zeilen
			String strKey = "";
			String strPK = "";
			String strCode = "";
			String strOrder = "";
			String strKategorieCode = "";
			String[] aList = baos.toString().split("\r\n");
			LOG.info("Anzahl Zeilen=" + aList.length );
			m_alErrorLogFile.add("Importdatei wird gelesen: " + aList.length + " Zeilen.");
			for (int iPos = 1; iPos < aList.length; iPos++) {

				// --- Line: PK;Code;Produktname;Order;KategorieCode;KategoryName
				String[] aLine = aList[iPos].split("\t");
				// LOG.info("aList[iPos] =" + aList[iPos] );
				if (aLine != null && aLine.length >= 6) {

					//LOG.info("Anzahl Spalten =" + aLine.length );
					strPK 				= aLine[0];
					strPK 				= strPK.replace(" ", "").replace("-", "").replace(",", "");

					strCode 			= aLine[1];

					strOrder 			= aLine[3];
					strOrder 			= strOrder.replace(" ", "").replace("-", "").replace(",", "").replace(".s", "");

					strKategorieCode 	= aLine[4];

					// --- key fuer hash setzen
					strKey	= strPK;

					// --- Hashes aktulisieren
					if (hashInput.containsKey(strKey)) {
						hashmapValues = (HashMap) hashInput.get(strKey);
						hashmapOrders = (HashMap) hashmapValues.get("orders");
					} else {
						hashmapValues = new HashMap();
						hashmapOrders = new HashMap();
					}

					// --- Wert merken (PK;Code;Produktname;Order;KategorieCode;KategoryName)
					//LOG.info("strKey=" + strKey + ", strCode=" + strCode + ", strValue=" + strOrder);
					hashmapValues.put( "PK", strPK); // 0
					hashmapValues.put( "Code", strCode); // 1
					hashmapOrders.put( strKategorieCode, strOrder );
					hashmapValues.put( "orders", hashmapOrders); // 1
					hashInput.put(strKey, hashmapValues);
				} else {
/*
					// --- konnte die Zeile eingelesen werden
					if ( aLine.length >= 1 && aList[iPos].trim() != "" ) {
						m_alErrorLogFile.add("Zeile >" + aList[iPos] + "< konnte nicht verarbeitet werden." );
					}
					*/
				}
			}
			// LOG.info("hashInput=" + hashInput);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hashInput;
	}

	/**
	 * Einlesen und parsen der Preislisten-Datei
	 *
	 * @param String strFileName
	 * @return HashMap<String,HashMap> hashResult
	 */
	private HashMap<String,HashMap> _readInputFilePreisliste(String strFileName) {

		// --- Initialize
		HashMap hashResult 		= new HashMap();
		HashMap hashmapInput 	= new HashMap();
		HashMap hashmapValues 	= new HashMap();


		try {

			// --- preset
			hashResult.put("preislisten", new HashMap() );
			hashResult.put("preiszeilen", new HashMap() );


			// --- Einlesen der Datei
			LOG.info("strFileName=" + strFileName );
			BufferedInputStream in = new BufferedInputStream(new FileInputStream(strFileName));
			int chunkSize = 327680;
			byte[] chunk = new byte[chunkSize];
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			int size = -1;
			while ((size = in.read(chunk, 0, chunkSize)) > 0) {
				baos.write(chunk, 0, size);
			}

			// --- Parsen der Index-Zeilen
			String strKey = "";
			String strHeaderKey = "";
			String[] aList = baos.toString().split("\r\n");
			//LOG.info("baos.toString()=" + baos.toString() );
			LOG.info("Anzahl Zeilen=" + aList.length );
			m_alErrorLogFile.add("Importdatei wird gelesen: " + aList.length + " Zeilen.");

			// --- header einlesen und preislisten ermitteln
			HashMap<Integer,String> hashmapPreislisten	= new HashMap<Integer,String>();
			String[] aLineHeader = aList[0].replace("\"","").split("\t");
			for (int iPos = 0; iPos < aLineHeader.length; iPos++) {
				LOG.info("aLineHeader[" + iPos + "]=" + aLineHeader[iPos]);
				String[] tmpKey	= aLineHeader[iPos].split("~");
				if ( tmpKey.length > 1 && !hashmapPreislisten.containsValue(tmpKey[0]) ) {

					// --- position "startspalte" merken
					hashmapPreislisten.put ( new Integer(iPos), tmpKey[0] );
				}
			}
			// --- merke preislisten
			hashResult.put("preislisten", hashmapPreislisten);
			LOG.info("hashsetPreisliste(n)=" + hashmapPreislisten);

			// --- zeilen einlesen
			for (int iPos = 1; iPos < aList.length; iPos++) {

				// --- Line: "PK"	"PK_SIS"	"ArtikelNr"	"CodeNr"	"Produkttext"	"VPE"...
				String[] aLine = aList[iPos].replace("\"","").split("\t");
				LOG.info("aList[iPos] =" + aList[iPos] );
				if (aLine != null && aLine.length > 6) {

					// --- daten zurücksetzen
					hashmapValues = new HashMap();

					// --- schleife über alle startspalten
					for (int iPosData = 0; iPosData < aLine.length; iPosData++ ) {

						// --- wert merken
						strKey = aLineHeader[iPosData];
						if ( !strKey.contains("~") ) {

							//LOG.info("read: strKey=" + strKey + ", Data=" + aLine[iPosData] );
							hashmapValues.put(strKey, aLine[iPosData]);
						}
					}

					// --- preiszeilen
					for (Integer integerStartCol :
							hashmapPreislisten.keySet() ) {

						// --- startposition merken
						int iStartCol				= integerStartCol.intValue();
						String strPreisListe		= (String)hashmapPreislisten.get(iStartCol);
						HashMap hashmapPreisValues	= new HashMap();
						LOG.info("######################### strPreisListe=" + strPreisListe + ", StartCol=" + iStartCol );
						for ( int iCol=0; iCol < 12; iCol++ ) {

							if ( iStartCol+iCol < aLine.length ) {

								// --- wert merken
								strHeaderKey	= aLineHeader[iStartCol+iCol].replace(strPreisListe + "~","");
								//LOG.info("read-preisdata: strHeaderKey=" + strHeaderKey + ", Data=" + aLine[iStartCol+iCol] );
								hashmapPreisValues.put(strHeaderKey, aLine[iStartCol+iCol] );

								//LOG.info(" iStartCol+iCol=" + iStartCol+iCol );
								//LOG.info(" strHeaderKey=" + strHeaderKey );
								//LOG.info(" Value=" + aLine[iStartCol+iCol] );
							}

						}
						hashmapValues.put(strPreisListe, hashmapPreisValues );
					}

					// --- zeile merken
					hashmapInput.put(aLine[0], hashmapValues);

				} else {
/*
					// --- konnte die Zeile eingelesen werden
					if ( aLine.length >= 1 && aList[iPos].trim() != "" ) {
						m_alErrorLogFile.add("Zeile >" + aList[iPos] + "< konnte nicht verarbeitet werden." );
					}
					*/
				}

			} // --- for (int iPos = 1; iPos < aList.length; iPos++) {


			// --- merke Daten
			hashResult.put("preiszeilen", hashmapInput );

		} catch (Exception e) {
			e.printStackTrace();
		}

		return hashResult;
	}

	/**
	 * 
	 * @param searchresult
	 * @param strKatalog
	 * @return 
	 */
	private Product _CheckKatalogVersion(SearchResult searchresult, String strKatalog) {

		// --- Initialize
		Product oResultProduct = null;
		Product oBaseProduct = null;
		String strCsvVersion = "";
		CatalogVersion csv = null;

		// --- Schleife �ber alle Produkte
		// und pr�fen auf Katalogversion
		for (Iterator itResult2 = searchresult.getResult().iterator(); itResult2
				.hasNext();) {
			oBaseProduct = (Product) itResult2.next();
			csv = (CatalogVersion) getAttribute(
					oBaseProduct, "catalogVersion");
			strCsvVersion = csv.getVersion();

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
	private Product _SearchProduct(String strKey, String strKatalog) {

		// --- Suchen der Variante
		String strSearch = "select {pk} from {VariantProduct} WHERE code='"
				+ strKey.substring(2, 8) + "'";
		SearchResult res = serachItem(strSearch, Collections
				.singletonList(VariantProduct.class), -1);
		Product oResultProduct = null;
		Product oBaseProduct = null;
		String strCsvVersion = "";
		ClassificationSystemVersion csv = null;
		if (res.getCount() > 0) {
			VariantProduct oVariantProductDest = null;
			// --- Schleife �ber alle Varianten
			for (Iterator itResult1 = res.getResult().iterator(); itResult1
					.hasNext();) {
				oVariantProductDest = (VariantProduct) itResult1.next();
//LOG.info("1.oVariantProductDest >" + oVariantProductDest.getCode() + "<");

				// --- Holen des Basisprodukts
				oBaseProduct = oVariantProductDest.getBaseProduct();
				if (oBaseProduct != null) {
//					LOG.info("1.oBaseProduct >" + oBaseProduct.getCode() + "<");

					// --- Holen aller Produkte mit gleicher CODE-Nummer
					// und pr�fen auf Katalogversion
					strSearch = "select {pk} from {WeraProduct} WHERE code='"
							+ oBaseProduct.getCode() + "'";
					res = serachItem(strSearch, Collections
							.singletonList(Product.class), -1);

					// --- Schleife �ber alle Produkte
					//     und pr�fen auf Katalogversion
					oResultProduct = _CheckKatalogVersion(res, strKatalog);
//					LOG.info("1. oResultProduct >" + oResultProduct.getCode() + "<");
				}
			}

		} else {
			strSearch = "select {pk} from {WeraProductSet} WHERE {artnr[de]}='"
					+ strKey.substring(2, 8) + "'";
			res = serachItem(strSearch, Collections
					.singletonList(WeraProductSet.class), -1);

			// --- Schleife �ber alle Produkte
			//     und pr�fen auf Katalogversion
			oResultProduct = _CheckKatalogVersion(res, strKatalog);
/*
			LOG.info("2. oResultProduct >" + oResultProduct.getCode() + "<");
			oBaseProduct = oResultProduct.getBaseProduct();
			if (oBaseProduct != null) {
				LOG.info("2.oBaseProduct >" + oBaseProduct.getCode() + "<");
			}
*/
		}

		return oResultProduct;
	}

	/**
	 * 
	 */
	public class MultiPartFormData {

		private HashMap parameters = new HashMap();
		private byte[] fileBytes, bytes;
		private final int mbLimit = 100;
		private final int FILE_SIZE_LIMIT = 1024 * 1024 * mbLimit;
		private String data;

		/**
		 * 
		 * @param request
		 * @throws IOException 
		 */
		public MultiPartFormData(HttpServletRequest request) throws IOException {

			int contentLength = request.getContentLength();

			if (contentLength > FILE_SIZE_LIMIT) {
				throw new IOException("File has exceeded size limit.");
			}

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
LOG.info("request=" + request);
//LOG.info("bytes=" + bytes);
			String data = new String(bytes, "ISO-8859-1");
			this.data = data;
			String boundary = data.substring(0, data.indexOf('\n'));
			String[] elements = data.split(boundary);

			for (int i = 0; i < elements.length; i++) {

				if (elements[i].length() > 0) {

					String[] descval = elements[i].split("\n");

					// take the first line of this element and split it by ";"
					String[] disp = descval[1].split(";");
LOG.info("descval=" + descval);
LOG.info("disp=" + disp);

					// if there's a filename, it's a file				
					if (disp.length > 2) {

						String longFileName = disp[2].substring(
								disp[2].indexOf('"') + 1, disp[2].length() - 2).trim();
						parameters.put("longFileName", longFileName);
						parameters.put("fileName", longFileName.substring(
								longFileName.lastIndexOf("\\") + 1,
								longFileName.length()));
						parameters.put("contentType", descval[2].substring(
								descval[2].indexOf(' ') + 1,
								descval[2].length() - 1));

						int pos = 0;
						int lineCount = 0;
						paramLineCount--;
						//while(lineCount != paramLineCount) {
						while (lineCount != paramLineCount + 3) {

							if ((char) bytes[pos] == '\n') {
								lineCount++;
							}
							pos++;
						}

						fileBytes = new byte[bytes.length - boundary.length() - 4 - pos];
						int fileByteCount = 0;

						for (int k = pos; k < (bytes.length - boundary.length() - 4); k++) {

							fileBytes[fileByteCount] = bytes[k];
							fileByteCount++;
						}
						//LOG.info("fileBytes="+fileBytes);
						//LOG.info("pos="+pos);
						//LOG.info("boundary.length()="+boundary.length());
						//LOG.info("fileByteCount="+fileByteCount);
						//LOG.info("contentLength="+fileBytes.length);
						//LOG.info("paramLineCount="+paramLineCount);

					} else {

						paramCount++;
						paramLineCount += 4;

						// loop for multi-line params
						String value = "";
						for (int p = 3; p < descval.length; p++) {

							if (p != 3) {
								value += "\n";
							}
							value += descval[p].trim();
							paramLineCount++;
						}

						parameters.put(
								descval[1].substring(
										descval[1].indexOf('"') + 1,
										descval[1].length() - 2).trim(),
								value
						);
					}
				}
			}

			bytes = null;
			System.gc();
		}

		/**
		 * 
		 * @return 
		 */
		public byte[] getFile() {
			return fileBytes;
		}

		/**
		 * 
		 * @return 
		 */
		public HashMap getParameters() {
			return parameters;
		}
	}

}

/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2010 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 *
 */
package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.Catalog;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.Keyword;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystem;
import de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.category.jalo.CategoryManager;
import de.hybris.platform.core.Registry;
import de.hybris.platform.core.model.ItemModel;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Country;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.AttributeDescriptor;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.util.Config;
import de.hybris.platform.util.JspContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.model.WeraProductSetModel;
import com.computationaldesign.wera.model.WeraVarianteModel;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.model.product.ProductModel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Date;
import java.util.logging.Level;

/**
 * This is the extension manager of the Wera extension.
 */
public class WeraManager extends GeneratedWeraManager {

	/**
	 * Edit the local|project.properties to change logging behavior (properties
	 * 'log4j.*').
	 */
	private static final Logger LOG = Logger.getLogger(WeraManager.class.getName());
	static final String PROFICLASS_NAME = "proficlass";

	static ClassificationSystemVersion m_proficlassCatalogVersion;

	static public CatalogVersion m_weraCatalogVersion;

        public final static long EARLIEST_SECS = 0L;
        public final static long LATEST_SECS = 32503676399L;
        public static Date dEarliest = new Date( EARLIEST_SECS * 1000 );
        public static Date dLatest = new Date( LATEST_SECS * 1000 ); // year 3000
        
	boolean m_bCheckForActivation = true;
	HashMap m_hashClassificationClasses = null;

	// --- Default Kataloge
	public String m_strCatalogPriceliste = "preisliste";
	public String m_strCatalogPrint = "print";
	public String m_strCatalogMaster = Config.getParameter("wera.mastercatalog");
	

	/**
	 * Initialize Catalogs
	 *
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
	 * Some important tips for development:
	 *
	 * Do NEVER use the default constructor of manager's or items. => If you
	 * want to do something whenever the manger is created use the initSorter() or
	 * destroy() methods described below
	 *
	 * Do NEVER use STATIC fields in your manager or items! => If you want to
	 * cache anything in a "static" way, use an instance variable in your
	 * manager, the manager is created only once in the lifetime of a
	 * "deployment" or tenant.
	 */
	public class OrderComparator implements Comparator {

		/**
		 *
		 * @param o1
		 * @param o2
		 * @return
		 */
		public int compare(final Object o1, final Object o2) /* descending order */ {

			// --- Initialize
			Integer iValue1 = new Integer(0);
			Integer iValue2 = new Integer(0);
			final int iResult = 0;

			try {
				// --- Hole Values
				iValue1 = (Integer) ((Item) o1).getAttribute("order");
				iValue2 = (Integer) ((Item) o2).getAttribute("order");
				//System.out.println("iValue1="+iValue1+", iValue2="+iValue2);
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

	/**
	 * Emailversand
	 *
	 * @param strSubject
	 * @param strMailTo
	 * @param strBody
	 */
	public void SendMail(final String strSubject, final String strMailTo, final String strBody) {

		try {
			LOG.info("email notification " + strSubject + " to: " + strMailTo);

			// --- Dateinamen / Pfad f�r Exporte
			final String strMailFile = InitOutputDatum() + "_mailtext.txt";

			// --- Email-Text
			final XmlSupport oXmlSupport = new XmlSupport();
			final String strMailBodyFile = Config.getParameter("wera.emailpath") + "/" + strMailFile;
			final BufferedWriter oFileWriterEmail = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(strMailBodyFile),
					"ISO-8859-1"));
			oFileWriterEmail.write(strBody + "\r\n");
			oFileWriterEmail.flush();
			oFileWriterEmail.close();

			// --- Email-Script
			final String strShellScript = Config.getParameter("wera.homepath") + "/emailscript.sh";
			final FileWriter oFileWriter = new FileWriter(strShellScript);
			oFileWriter.write("#!/bin/sh\n");
			oFileWriter.write("#\n");
			oFileWriter.write("cd " + Config.getParameter("wera.homepath") + "\n");
			oFileWriter.write("mail -s \"" + strSubject + "\" -a FROM:wera-admin@co-de.de " + strMailTo + " < " + strMailBodyFile + "\n");
			oFileWriter.close();

			// --- Ausf�hren der SHell-Datei - und anschlie�endes l�schen
			oXmlSupport.startCmdFile("chmod a+x " + strShellScript);
			oXmlSupport.startCmdFile(strShellScript);
			oXmlSupport.startCmdFile("rm " + strShellScript);

		} catch (final IOException e) {
			// TODO Auto-generated catch block
			LOG.error("email notification " + strSubject + " to " + strMailTo);
			e.printStackTrace();
		}
		// --- Zippen der Media-Datei -----------------------------------------

	}

	/**
	 * Get the valid instance of this manager.
	 *
	 * @return the current instance of this manager
	 */
	public static WeraManager getInstance() {
		return (WeraManager) Registry.getCurrentTenant().getJaloConnection().getExtensionManager()
				.getExtension(WeraConstants.EXTENSIONNAME);
	}

	/**
	 * Never call the constructor of any manager directly, call getInstance()
	 * You can place your business logic here - like registering a jalo session
	 * listener. Each manager is created once for each tenant.
	 */
	public WeraManager() // NOPMD 
	{
		if (LOG.isDebugEnabled()) {
			LOG.debug("constructor of WeraManager called.");
		}
	}

	/**
	 * Use this method to do some basic work only ONCE in the lifetime of a
	 * tenant resp. "deployment". This method is called after manager creation
	 * (for example within startup of a tenant). Note that if you have more than
	 * one tenant you have a manager instance for each tenant.
	 */
	@Override
	public void init() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("initSorter() of WeraManager called. " + getTenant().getTenantID());
		}
	}

	/**
	 * Use this method as a callback when the manager instance is being
	 * destroyed (this happens before system initialization, at redeployment or
	 * if you shutdown your VM). Note that if you have more than one tenant you
	 * have a manager instance for each tenant.
	 */
	@Override
	public void destroy() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("destroy() of WeraManager called, current tenant: " + getTenant().getTenantID());
		}
	}

	/**
	 * checks the wera - version
	 *
	 * @param String strCheckVersion - default "2015"
	 * @return boolean
	 */
	public boolean isVersion(String strCheckVersion) {

		// --- get version from config-file
		String m_strConfigVersion = Config.getParameter("wera.version");

		// --- set default 2015
		if (m_strConfigVersion == null) {
			m_strConfigVersion = "2015";
		}

		// --- set default check-version 2015
		if (strCheckVersion == null) {
			strCheckVersion = "2015";
		}

		return m_strConfigVersion.equals(strCheckVersion);
	}

	/**
	 * Implement this method to create initial objects. This method will be
	 * called by system creator during initialization and system update. Be sure
	 * that this method can be called repeatedly.
	 *
	 * An example usage of this method is to create required cronjobs or
	 * modifying the type system (setting e.g some default values)
	 *
	 * @param params the parameters provided by user for creation of objects for
	 * the extension
	 * @param jspc the jsp context; you can use it to write progress information
	 * to the jsp page during creation
	 */
	@Override
	public void createEssentialData(final Map arg0, final JspContext arg1) throws Exception {
		// TODO Auto-generated method stub
		super.createEssentialData(arg0, arg1);

		// --- Erzeuge die Medien-Ablage
		final String strImagePath = Config.getParameter("wera.homepath") + Config.getParameter("wera.imagepath");
		System.out.println("anwendungsbilder.mkdir()= " + createDirectory(strImagePath + "anwendungsbilder"));
		System.out.println("icons.mkdir()= " + createDirectory(strImagePath + "icons"));
		System.out.println("symbole.mkdir()= " + createDirectory(strImagePath + "symbole"));
		System.out.println("produktbilder.mkdir()= " + createDirectory(strImagePath + "produktbilder"));

		// --- Sprachen anlegen
		// de,	en (gb, uk), es, us-en (canada), it, fr, pl, ru, cs, cn, jp
		Language langDE = C2LManager.getInstance().getLanguageByIsoCode("de");
		if (langDE == null) {
			langDE = C2LManager.getInstance().createLanguage("de");
		}
		langDE.setActive(true);

		// L�nder anlegen
		// de, uk, us, za
		Country cDe = null;
		try {
			cDe = C2LManager.getInstance().getCountryByIsoCode("de");
		} catch (final JaloItemNotFoundException e) {
			System.out.println("Country DE not found");
		}
		if (cDe == null) {
			cDe = C2LManager.getInstance().createCountry("de");
		}
		cDe.setLocalizedProperty("name", "Deutschland");

		Country cUk = null;
		try {
			cUk = C2LManager.getInstance().getCountryByIsoCode("uk");
		} catch (final JaloItemNotFoundException e) {
			System.out.println("Country UK not found");
		}
		if (cUk == null) {
			cUk = C2LManager.getInstance().createCountry("uk");
		}
		cUk.setLocalizedProperty("name", "Grossbritannien");

		Country cUs = null;
		try {
			cUs = C2LManager.getInstance().getCountryByIsoCode("us");
		} catch (final JaloItemNotFoundException e) {
			System.out.println("Country US not found");
		}
		if (cUs == null) {
			cUs = C2LManager.getInstance().createCountry("us");
		}
		cUs.setLocalizedProperty("name", "Nordamerika");

		// --- Sprachen anlegen
		createLanguage("en", "Englisch", langDE);
		createLanguage("us-en", "Amerikanisch-Canada", langDE);
		createLanguage("us-es", "Amerikanisch-Spanisch", langDE);
		createLanguage("us-fr", "Amerikanisch-Franzoesisch", langDE);
		createLanguage("fr", "Franzoesich", langDE);
		createLanguage("it", "Italienisch", langDE);
		createLanguage("es", "Spanisch", langDE);
		createLanguage("cs", "Tschechisch", langDE);
		createLanguage("ru", "Russisch", langDE);
		createLanguage("pl", "Polnisch", langDE);
		createLanguage("jp", "Japanisch", langDE);
		createLanguage("cn", "Chinesich", langDE);
		createLanguage("dk", "Daenisch", langDE);
		createLanguage("nl", "Niederlaendisch", langDE);
                createLanguage("fi", "Finnisch", langDE);
                createLanguage("no", "Norwegisch", langDE);
                createLanguage("se", "Schwedisch", langDE);
                createLanguage("gr", "Griechisch", langDE);
                createLanguage("ro", "Rumaenisch", langDE);
                createLanguage("by", "Weissrussisch", langDE);
                createLanguage("bg", "Bulgarisch", langDE);
                createLanguage("ee", "Estnisch", langDE);
                createLanguage("lt", "Litauisch", langDE);
                createLanguage("lv", "Lettisch", langDE);
                createLanguage("hu", "Ungarisch", langDE);
                createLanguage("tr", "Tuerkisch", langDE);

		/*
		 * // --- Satz in Satz final ComposedType weraWeraProductSetinSetType =
		 * getSession().getTypeManager().getComposedType(WeraProductSetinSet.class); final ComposedType
		 * weraVariantenTypeSetInSet = getSession().getTypeManager().getComposedType(WeraVarianteSetInSet.class); final
		 * AttributeDescriptor variantTypeSetInSet = weraWeraProductSetinSetType.getAttributeDescriptor("variantType");
		 * variantTypeSetInSet.setDefaultValue(weraVariantenTypeSetInSet);
		 */
		// --- Defaultwert f�r WeraProduct / WeraVariante setzen
		final ComposedType weraProductSetinSetType = getSession().getTypeManager().getComposedType(WeraProductSetinSet.class);
		final ComposedType weraProductSetType = getSession().getTypeManager().getComposedType(WeraProductSet.class);
		final ComposedType weraProduktType = getSession().getTypeManager().getComposedType(WeraProduct.class);
		final ComposedType weraVariantenType = getSession().getTypeManager().getComposedType(WeraVariante.class);
		final ComposedType weraMediaType = getSession().getTypeManager().getComposedType(WeraMedia.class);
		final ComposedType CategoryType = getSession().getTypeManager().getComposedType(Category.class);
		final ComposedType OutputControlType = getSession().getTypeManager().getComposedType(Outputcontrol.class);

		final AttributeDescriptor variantType = weraProduktType.getAttributeDescriptor("variantType");
		variantType.setDefaultValue(weraVariantenType);

		final AttributeDescriptor eanType = weraProduktType.getAttributeDescriptor("ean");
		eanType.setDefaultValue("");
		final AttributeDescriptor desc1Type = weraProduktType.getAttributeDescriptor("description1");
		desc1Type.setDefaultValue("");
		final AttributeDescriptor desc2Type = weraProduktType.getAttributeDescriptor("description2");
		desc2Type.setDefaultValue("");
		final AttributeDescriptor desc3Type = weraProduktType.getAttributeDescriptor("description3");
		desc3Type.setDefaultValue("");

		// Defaultwert f�rs Ausgabetemplate in InBetween setzen
		AttributeDescriptor outputtemplateType = weraProduktType.getAttributeDescriptor("outputtemplate");
		outputtemplateType.setDefaultValue("PRODUCT");
		outputtemplateType = weraProductSetType.getAttributeDescriptor("outputtemplate");
		outputtemplateType.setDefaultValue("PRODUCTSET");

		outputtemplateType = weraProductSetinSetType.getAttributeDescriptor("outputtemplate");
		outputtemplateType.setDefaultValue("PRODUCTSETINSET");

		// --- Defaultwerte f�r WeraVarianten setzen
		final AttributeDescriptor lagernrDesc = weraVariantenType.getAttributeDescriptor("lagerNr");
		lagernrDesc.setDefaultValue("05");

		// --- Defaultwert f�r Attribut priorityWebSearch beim Typ Category setzen
		final AttributeDescriptor priorityWebSearchDesc = CategoryType.getAttributeDescriptor("priorityWebSearch");
		priorityWebSearchDesc.setDefaultValue(new Integer(50));

		// ---
		//HashMap hVariantenNr = new HashMap();
		//hVariantenNr.put(langDE,"001");
		final SessionContext sessioncontext = getSession().getSessionContext();
		sessioncontext.setLanguage(null);

		final Map langMapFalse = new HashMap();
		final Map langMapTrue = new HashMap();
		final Map langMap = new HashMap();

		final Set<Language> setLanguages = C2LManager.getInstance().getAllLanguages();
		for (final Language l : setLanguages) {
			langMap.put(l, "001");
			langMapTrue.put(l, new java.lang.Boolean(true));
			langMapFalse.put(l, new java.lang.Boolean(false));
		}

		final AttributeDescriptor variantennrDesc = weraVariantenType.getAttributeDescriptor("variantenNr");
		final SessionContext sessioncontext1 = getSession().getSessionContext();
		//variantennrDesc.setDefaultValue(null, null);
		variantennrDesc.setProperty(null, AttributeDescriptor.DEFAULTVALUE, null);
		//		sessioncontext.setLanguage(null);
		variantennrDesc.setDefaultValue(null, langMap);

		final AttributeDescriptor orderDesc = weraVariantenType.getAttributeDescriptor("order");
		orderDesc.setDefaultValue(new java.lang.Integer(1));

		final AttributeDescriptor aktivDesc = weraVariantenType.getAttributeDescriptor("aktiv");
		aktivDesc.setProperty(null, AttributeDescriptor.DEFAULTVALUE, null);
		aktivDesc.setDefaultValue(null, langMapTrue);

		final AttributeDescriptor aktivProduct = weraProduktType.getAttributeDescriptor("aktiv");
		aktivProduct.setProperty(null, AttributeDescriptor.DEFAULTVALUE, null);
		aktivProduct.setDefaultValue(null, new java.lang.Boolean(true));

		final AttributeDescriptor backgroundOutputControl = OutputControlType.getAttributeDescriptor("background");
		backgroundOutputControl.setProperty(null, AttributeDescriptor.DEFAULTVALUE, null);
		backgroundOutputControl.setDefaultValue(null, langMapFalse);

		// --- Defaultwert f�r WeraProductSet / WeraVarianteSet setzen (S�TZE)
		final ComposedType weraVariantenSetType = getSession().getTypeManager().getComposedType(WeraVarianteSet.class);

		final AttributeDescriptor variantTypeSet = weraProductSetType.getAttributeDescriptor("variantType");
		variantTypeSet.setDefaultValue(weraVariantenSetType);

		final AttributeDescriptor setQuantType = weraVariantenSetType.getAttributeDescriptor("contentQuantity");
		setQuantType.setDefaultValue(new java.lang.Integer(1));

		final AttributeDescriptor aktivProductSet = weraProductSetType.getAttributeDescriptor("aktiv");
		aktivProductSet.setProperty(null, AttributeDescriptor.DEFAULTVALUE, null);
		aktivProductSet.setDefaultValue(null, new java.lang.Boolean(true));

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final CatalogManager catalogManager = CatalogManager.getInstance();
		Catalog weraCatalog = catalogManager.getCatalog(Config.getParameter("wera.mastercatalog"));

		//ClassificationSystem weraCatalog = catalogManager.getClassificationSystem("weracatalog");
		if (weraCatalog == null) {
			weraCatalog = catalogManager.createCatalog(Config.getParameter("wera.mastercatalog"));
		}
		CatalogVersion weraCatalogVersion = weraCatalog.getCatalogVersion(Config.getParameter("wera.mastercatalogversion"));
		// ClassificationSystemVersion weraCatalogVersion = (ClassificationSystemVersion) weraCatalog.getCatalogVersion("weramaster");
		if (weraCatalogVersion == null) {
			weraCatalogVersion = catalogManager.createCatalogVersion(weraCatalog, Config.getParameter("wera.mastercatalogversion"),
					langDE);
			weraCatalogVersion.setActive(true);
		}

		// --- Initialisiere Steuerparameter
		final Set weraproducts = WeraProduct.getAllInstances();
		for (final Iterator itProd = weraproducts.iterator(); itProd.hasNext();) {
			final WeraProduct weraproduct = (WeraProduct) itProd.next();
			createCategory2ProductExt(weraproduct, Config.getParameter("wera.mastercatalogversion"));
		}

		// --- jedes neu angelegte produkt (etc.) soll defaultm�ssig im weracatalog sein
		final AttributeDescriptor catalogVersion = weraProduktType.getAttributeDescriptor("catalogVersion");
		catalogVersion.setDefaultValue(weraCatalogVersion);

		final AttributeDescriptor catalogVersionVariant = weraVariantenType.getAttributeDescriptor("catalogVersion");
		catalogVersionVariant.setDefaultValue(weraCatalogVersion);

		final AttributeDescriptor catalogVersionWeraMedia = weraMediaType.getAttributeDescriptor("catalogVersion");
		catalogVersionWeraMedia.setDefaultValue(weraCatalogVersion);

		final AttributeDescriptor catalogVersionCategory = CategoryType.getAttributeDescriptor("catalogVersion");
		catalogVersionCategory.setDefaultValue(weraCatalogVersion);

		final AttributeDescriptor catalogVersionProductSet = weraProductSetType.getAttributeDescriptor("catalogVersion");
		catalogVersionProductSet.setDefaultValue(weraCatalogVersion);

		final AttributeDescriptor catalogVersionProductSetInSet = weraProductSetinSetType.getAttributeDescriptor("catalogVersion");
		catalogVersionProductSetInSet.setDefaultValue(weraCatalogVersion);

		final AttributeDescriptor catalogVersionVariantSet = weraVariantenSetType.getAttributeDescriptor("catalogVersion");
		catalogVersionVariantSet.setDefaultValue(weraCatalogVersion);

	}

	/**
	 * Implement this method to create data that is used in your project. This
	 * method will be called during the system initialization.
	 *
	 * An example use is to import initial data like currencies or languages for
	 * your project from an csv file.
	 *
	 * @param params the parameters provided by user for creation of objects for
	 * the extension
	 * @param jspc the jsp context; you can use it to write progress information
	 * to the jsp page during creation
	 */
	@Override
	public void createProjectData(final Map<String, String> params, final JspContext jspc) {
		// implement here code creating project data
	}

	// --------------------------------------------------------------------------------------------------------------------------------	
	// --- Hilfsfunktionen
	// --------------------------------------------------------------------------------------------------------------------------------	
	/**
	 *
	 * @param strIsoCode
	 * @param strName
	 * @param langFallback
	 * @throws ConsistencyCheckException
	 */
	public void createLanguage(final String strIsoCode, final String strName, final Language langFallback)
			throws ConsistencyCheckException {
		// --- Englisch
		Language lang = null;
		try {
			lang = C2LManager.getInstance().getLanguageByIsoCode(strIsoCode);
		} catch (final JaloItemNotFoundException e) {
			lang = C2LManager.getInstance().createLanguage(strIsoCode);

		}
		lang.setName(strName);
		lang.setActive(true);
		lang.setFallbackLanguages(Collections.singletonList(langFallback));

	}

	/**
	 * Setzen der Sprachen, r�gabe der verherigen
	 * 
	 * @param String strLanguage
	 * @return Language
	 */
	public Language SetLanguage(final String strLanguage) {

		Language lang = C2LManager.getInstance().getLanguageByIsoCode(strLanguage);
		if (lang == null) {
			try {
				lang = C2LManager.getInstance().createLanguage(strLanguage);
			} catch (final ConsistencyCheckException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		JaloSession.getCurrentSession().getSessionContext().setLanguage(lang);
		//this.output("Spache setzen strSprache=" + strLanguage);
		return lang;
	}

	/**
	 * Pr�fe ob ein Verzeichnis vorhanden ist sonst anlegen
	 *
	 * @param String strDirName
	 * @return boolean
	 */
	public boolean createDirectory(final String strDirName) {
		// --- Initialize
		boolean bResult = true;

		final File fileDirectory = new File(strDirName);
		if (fileDirectory.exists() == false) {
			bResult = fileDirectory.mkdir();
		}

		return bResult;
	}

	/**
	 * ProfiClass-catalog und catalogversion holen, oder ggf. neu anlegen
	 *
	 * @param stringCatalogName
	 * @param stringCatalogVersion
	 * @return
	 */
	public static ClassificationSystemVersion getClassificationSystemVersion(final String stringCatalogName,
			final String stringCatalogVersion) {
		final CatalogManager catalogManager = CatalogManager.getInstance();
		final ClassificationSystem classificationSystem = catalogManager.getClassificationSystem(stringCatalogName);
		final ClassificationSystemVersion classificationSystemVersion = (ClassificationSystemVersion) classificationSystem
				.getCatalogVersion(stringCatalogVersion);

		return classificationSystemVersion;
	}

	/**
	 * This method is used to determine, if a classification system version is
	 * used to hold a group system for classifying purposes or not.
	 * Category2ProductExt-structures are only created for system, that do not
	 * classify.
	 *
	 * @param csv
	 * @return
	 */
	public boolean is_classifying_catalogversion(final CatalogVersion csv) {
		boolean bOk = false;
		if (csv != null) {
			final Catalog oCatalog = csv.getCatalog();
			if (csv.getVersion().equals("werazusatz") && oCatalog.getId().equals("weraclassification")) {
				bOk = true;
			}
			if (csv.getVersion().equals("weraportale") && oCatalog.getId().equals("weraportalclasses")) {
				bOk = true;
			}
			if (oCatalog.getId().equals("proficlass")) {
				bOk = true;
			}
		}
		return bOk;
	}

	/**
	 *
	 * @param weraproduct
	 * @param strDefaultCatalogVersion
	 */
	public void createCategory2ProductExt(final WeraProduct weraproduct, String strDefaultCatalogVersion) {
		// TODO Auto-generated method stub

		// --- Initialize
		Category category = null;
		Category2ProductExt category2productext = null;
		Category2ProductExt category2productextTmp = null;
		final HashMap hashCategories = new HashMap();
		final HashMap hashCategories2Catalog = new HashMap();
		final Collection category2ProductexResult = new ArrayList();

		// --- Default-Katalogversion (Order wird von dieser Version kopiert)
		if (strDefaultCatalogVersion == null || strDefaultCatalogVersion.length() == 0) {
			strDefaultCatalogVersion = Config.getParameter("wera.mastercatalogversion");
		}

		// --- Initialisiere Sprachen
		final Collection languages = C2LManager.getInstance().getAllLanguages();

		// --- Nur Wera-Produkte
		if (weraproduct != null) {

			// --- Hole alle Kategorien
			final Collection supercategories = (Collection) getAttribute(weraproduct, "supercategories");

			// --- Hole alle Produkt / Kategory Erg�nzngen
			final Collection category2Poductext = (Collection) getAttribute(weraproduct, "category2productexts");
			if (category2Poductext != null) {
				for (final Iterator itcategory2Poductext = category2Poductext.iterator(); itcategory2Poductext.hasNext();) {
					// --- you can get it if you realy want, hey hey hey
					category2productext = (Category2ProductExt) itcategory2Poductext.next();
					category = (Category) getAttribute(category2productext, "category");

					// --- Leichen vertilgen
					if (category == null) {
						try {
							category2productext.remove();
						} catch (final ConsistencyCheckException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						continue;
					}
					if (!hashCategories.containsKey(category.getPK())) {
						//final ClassificationSystemVersion catalogversion = (ClassificationSystemVersion) getAttribute(category,  "catalogVersion");
						final CatalogVersion catalogversion = (CatalogVersion) getAttribute(category, "catalogVersion");
						if (catalogversion != null && !is_classifying_catalogversion(catalogversion)) {
							hashCategories.put(category.getPK(), category2productext);
							final String strKey = category.getCode() + "_" + catalogversion.getVersion();
							//LOG.info("createCategory2ProductExt(): create category key = " + strKey);
							hashCategories2Catalog.put(strKey, category2productext);
						}
					}
				}
			}

			// --- Schleife �ber alle Kategorien
			if (supercategories != null) {
				for (final Iterator itsupercategories = supercategories.iterator(); itsupercategories.hasNext();) {
					// --- you can get it if you realy want, hey hey hey
					category = (Category) itsupercategories.next();
					//LOG.info("Beareite Category=" + category.getCode() );

					// --- Nur Wera-Kategorien
					CatalogVersion catalogversion = (CatalogVersion) getAttribute(category, "catalogVersion");
					if (catalogversion != null && !is_classifying_catalogversion(catalogversion)) {
						//LOG.info("1" );

						// --- Existiert bereis ein Eintrag f�r diese Category???
						if (hashCategories.containsKey(category.getPK())) {
							category2productext = (Category2ProductExt) hashCategories.get(category.getPK());
							// LOG.info("2" );
						} else {
							// --- Anlegen
							final HashMap newitem = new HashMap();
							newitem.put("category", category);
							category2productext = createCategory2ProductExt(newitem);
							// LOG.info("3" );
						}

						if (category2productext.getPriority() == null ) {
							//  LOG.info("4" );
							catalogversion = (CatalogVersion) getAttribute(category, "catalogVersion");
							final String strKey = category.getCode() + "_" + strDefaultCatalogVersion;
							// LOG.info("createCategory2ProductExt(): search category key = " + strKey);

							category2productextTmp = (Category2ProductExt) hashCategories2Catalog.get(strKey);

							if (category2productextTmp != null && category2productextTmp.getPriority() != null
									&& !catalogversion.getVersion().equals(Config.getParameter("wera.mastercatalogversion"))) {
								// LOG.info("createCategory2ProductExt(): priority = " + category2productextTmp.getPriority());
								category2productext.setPriority(category2productextTmp.getPriority());
							} else {
								if ( getAttribute(weraproduct, "order") != null ) {

									category2productext.setPriority(((Integer) getAttribute(weraproduct, "order")).toString());
								} else {
									category2productext.setPriority("0");
								}
							}
						}
						/*
						else {
							LOG.info("4.fail" );

								LOG.info("category2productext.getPriority()=" + category2productext.getPriority() );


								LOG.info("getAttribute(weraproduct, \"order\")=" + getAttribute(weraproduct, "order"));

						}
*/
						// --- Merke
						category2ProductexResult.add(category2productext);

					} // --- if ( catalogversion.getCatalog().toString().equals("weracataolog") ) {
					/*
					else {
						LOG.info("1.fail" );
					}
*/
				}
			} else {
				LOG.info("supercategories == null, nothing changed");
			}

			// --- Eintragen
			setAttribute(weraproduct, "category2productexts", category2ProductexResult);

		} // --- if ( weraproduct != null && weraproduct instanceof WeraProduct  ) {
		LOG.info("createCategory2ProductExt(): finished  for wera product " + weraproduct.getCode());
	}

	/**
	 *
	 * @param weraproduct
	 * @return
	 * @throws JaloInvalidParameterException
	 * @throws JaloBusinessException
	 */
	public HashMap createOutputcontrolByProduct(final Product weraproduct) throws JaloInvalidParameterException,
			JaloBusinessException {
		// --- Initialize
		final HashMap resultDB = new HashMap();
		final String strPClassCategory = "";
		final EnumerationValue evVisibility;
		final CategoryManager cm = CategoryManager.getInstance();
		final EnumerationManager em = JaloSession.getCurrentSession().getEnumerationManager();
		EnumerationType et = null;
		final EnumerationValue ev = null;
		et = em.getEnumerationType("ClassificationAttributeVisibilityEnum");
		final EnumerationValue evVISIBLE_IN_BASE = em.getEnumerationValue(et, "VISIBLE_IN_BASE");
		final Collection colLang = C2LManager.getInstance().getAllLanguages();
		final Category pclasscategory = null;
		final Category weracategory = null;

		if (weraproduct instanceof WeraProduct) {

			// --- Wera Produkct, ok dann weiter
			LOG.info("createOutputcontrolByProduct(): product code = " + weraproduct.getCode());

			// --- Hole alle Kategorien
			ClassificationClass classificationclass = null;
			final Collection classificationattributes = new ArrayList();
			final Collection categories = (Collection) weraproduct.getAttribute("classificationClasses");
			for (final Iterator it1 = categories.iterator(); it1.hasNext();) {
				// --- Hole Kategorie CA der Kataegory
				classificationclass = (ClassificationClass) it1.next();

				// --- Portalklassifizierungen nicht verwenden
				if (classificationclass.getCode().equals("PORTALATTRIBUTES")) {
					// LOG.info("createOutputcontrolByProduct(): skipping classificationclass=PORTALATTRIBUTES");
					continue;
				}

				// LOG.info("createOutputcontrolByProduct(): classification class=" + classificationclass.getCode());
				// --- Hole Attribute, denke an assignments!
				classificationattributes.addAll(classificationclass.getClassificationAttributeAssignments());

			}

			// --- Schleife �ber alle CA'
			final Collection ouputcontrolsNew = new ArrayList();
			Collection ouputcontrols = (Collection) weraproduct.getAttribute("outputcontrols");
			final Collection ouputcontrols_org = ouputcontrols;
			if (ouputcontrols == null || ouputcontrols.size() == 0) {
				ouputcontrols = new ArrayList();
			} else {
				// --- Abbruch, keine Korrektur da bereits vorhanden
				// LOG.info("createOutputcontrolByProduct(): ouputcontrols size() = " + ouputcontrols.size());
				// return resultDB;
			}

			//ProfiClassClassificationAttribute classificationattribute1 = null;
			ClassAttributeAssignment classattributeassignment = null;
			Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();
			for (final Iterator it2 = classificationattributes.iterator(); it2.hasNext();) {

				// --- Hole Kategorie CA der Kataegory
				classattributeassignment = (ClassAttributeAssignment) it2.next();

				// --- Hole Datenbank Zuordnung
				//System.out.println("CA=" + classattributeassignment.getClassificationAttribute().getCode());
				// resultDB.clear();
				// resultDB = getHybrisMerkmale (classattributeassignment.getCode(),weraproduct.getCode());
				// --- Pr�fe, ob das Element bereits vorhanden ist
				Outputcontrol outputcontrol = (Outputcontrol) checkContainingCAA(ouputcontrols, classattributeassignment);
				//Outputcontrol outputcontrol = (Outputcontrol) checkContaining(ouputcontrols, "code", classattributeassignment
				//		.getClassificationAttribute().getCode());

				if (outputcontrol == null) {
					// --- Outputcontrol anlegen
					System.out.println("Neues Control = " + classattributeassignment.getClassificationAttribute().getCode());
					final HashMap newitem = new HashMap();
					newitem.put("code", classattributeassignment.getClassificationAttribute().getCode());
					outputcontrol = createOutputcontrol(newitem);
					setAttribute(outputcontrol, "visibility", classattributeassignment.getVisibility());
					//setAttribute(outputcontrol,"visibility", getVisibilityem.getEnumerationValue( et, "VISIBLE_IN_BASE" ));
					outputcontrol.setAttribute("showinset", new Boolean(false));
                                        outputcontrol.setAttribute("showinset_onlineshops", new Boolean(false));
					setAttribute(outputcontrol, "order", (getAttribute(classattributeassignment, "order")));
					
					
					for (final Iterator itLanguages = C2LManager.getInstance().getAllLanguages().iterator(); itLanguages.hasNext();) {
						// --- Sprache aktivieren
						final Language lang = (Language) itLanguages.next();
						SetLanguage(lang.getIsoCode());

						outputcontrol.setAttribute("background", new Boolean(false));
					}
					SetLanguage( currentSessionLanguage.getIsoCode() );

				}
				final Integer oOrder = (Integer) outputcontrol.getAttribute("order");
				if (oOrder == null) {
					outputcontrol.setAttribute("order", new Integer(0));
				}
				setAttribute(outputcontrol, "classattributeassignment", classattributeassignment);

				// --- Output-Control �bernhemen
				ouputcontrolsNew.add(outputcontrol);

			} // --- for (Iterator it2 = classificationattributes.iterator(); it2.hasNext();) {

			// --- Sortiere, nach "order"
			if (ouputcontrolsNew != null && ouputcontrolsNew.size() > 0) {
				Collections.sort((List) ouputcontrolsNew, new OrderComparator());
			}

			// --- Werte setzen / bzw. zur�ckschreiben
			weraproduct.setAttribute("outputcontrols", ouputcontrolsNew);
			
			// --- Sprache zurücksetzen
			SetLanguage( currentSessionLanguage.getIsoCode() );
		}

		return resultDB;
	}

	/**
	 *
	 * @param oItem
	 * @param strAttribute
	 * @param oAttributeValue
	 */
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
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param Item oItem
	 * @param string strAttribute
	 * @return Object
	 */
	public Object getAttribute(final Item oItem, final String strAttribute) {
		// --- Initialize
		Object oObject = null;

		try {
			// --- Hole das Attrbiute
			oObject = oItem.getAttribute(strAttribute);

		} catch (final Exception e1) {
			// TODO Auto-generated catch block
			LOG.warn("getAttribute(): error=>oItem=" + oItem + ", strAttribute=" + strAttribute);
			e1.printStackTrace();
		}

		return oObject;
	}

	/**
	 * �berarbeiten der zugeordneten Bilder, beim Speichern eines WeraProdukts
	 *
	 * @param item
	 * @param values
	 */
	public void checkMedia(final Item item, final Map values) {

		// --- Initialize
		System.out.println("checkMedia( Item item, Map values) MUST BE IMPLEMENTED!!!!!!!!! =" + item.getPK() + "-class="
				+ item.getClass().toString());

		/*
		 * !!! ACHTUNG Collection pictures = null; String strDestDir = null; String strImagePath =
		 * Config.getParameter("wera.homepath") + Config.getParameter("wera.imagepath");
		 * 
		 * // --- Archiviere Icons der Merkmale if ( item instanceof ProfiClassClassificationAttribute ) { // ---
		 * Bearbeite Bilder ProfiClassClassificationAttribute proficlassclassificationattribute =
		 * (ProfiClassClassificationAttribute)item;
		 * System.out.println("checkMedia.weramanager.ProfiClassClassificationAttribute.code=" +
		 * proficlassclassificationattribute.getCode() );
		 * 
		 * // --- Pr�fe auf gespeicherte Symbole try { pictures = (Collection)
		 * proficlassclassificationattribute.getAttribute("icons"); } catch (JaloInvalidParameterException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch (JaloSecurityException e) { // TODO Auto-generated
		 * catch block e.printStackTrace(); } System.out.println( "product.mkdir()= " + createDirectory ( strImagePath +
		 * "icons/merkmale" ) ); strDestDir = strImagePath + "icons/merkmale" ; archiveMedia ( pictures, strImagePath,
		 * strDestDir ); return; }
		 * 
		 * Product oProduct = null; if ( item instanceof WeraProduct ) { // --- Bearbeite Bilder oProduct = (Product)item;
		 * System.out.println("checkMedia.weramanager.WeraProduct.code=" + oProduct.getCode() ); } if ( item instanceof
		 * WeraProductSet ) { // --- Bearbeite Bilder oProduct = (Product)item;
		 * System.out.println("checkMedia.weramanager.WeraProductSet.code=" + oProduct.getCode() ); } if ( values != null
		 * ) { System.out.println("checkMedia.weramanager.WeraProductSet.values.values()=" + values.values() );
		 * System.out.println("checkMedia.weramanager.WeraProductSet.values.keySet()=" + values.keySet() ); } if (
		 * oProduct == null ) return;
		 * 
		 * 
		 * // --- Kategory-Verzeichnisanlegen // --- Hole die Wera-Kategorie Collection categories = null; try {
		 * categories = (Collection) oProduct.getAttribute("supercategories"); } catch (JaloInvalidParameterException e) {
		 * // TODO Auto-generated catch block e.printStackTrace(); } catch (JaloSecurityException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } String strWeraCategory = ""; for (Iterator it1 =
		 * categories.iterator(); it1.hasNext();) { // --- Hole Kategorie Category oCategory = (Category) it1.next(); if(
		 * oCategory.getCode().substring(0,2).compareTo("AA") != 0 && oCategory.getCode().substring(0,1).compareTo("_" )
		 * != 0 ) strWeraCategory = oCategory.getCode(); }
		 * 
		 * // --- Erzeuge den Kategory-Path strWeraCategory = strWeraCategory.replace (' ','_');
		 * System.out.println("strWeraCategory= " + strImagePath + strWeraCategory); if ( strWeraCategory.length() > 0 ) {
		 * System.out.println( "category.mkdir()= " + createDirectory ( strImagePath + "anwendungsbilder/" +
		 * strWeraCategory ) ); System.out.println( "category.mkdir()= " + createDirectory ( strImagePath + "icons/" +
		 * strWeraCategory ) ); System.out.println( "category.mkdir()= " + createDirectory ( strImagePath + "symbole/" +
		 * strWeraCategory ) ); System.out.println( "category.mkdir()= " + createDirectory ( strImagePath +
		 * "produktbilder/" + strWeraCategory ) ); }
		 * 
		 * // --- Produktverzeichnis anlegen String strProductPath = ""; String strProductCode = oProduct.getCode();
		 * strProductCode = strProductCode.replace (' ','_'); if ( strWeraCategory.length() > 0 ) { System.out.println(
		 * "product.mkdir()= " + createDirectory ( strImagePath + "anwendungsbilder/" + strWeraCategory + "/" +
		 * strProductCode ) ); System.out.println( "product.mkdir()= " + createDirectory ( strImagePath + "icons/" +
		 * strWeraCategory + "/" + strProductCode ) ); System.out.println( "product.mkdir()= " + createDirectory (
		 * strImagePath + "symbole/" + strWeraCategory + "/" + strProductCode ) ); System.out.println( "product.mkdir()= "
		 * + createDirectory ( strImagePath + "produktbilder/" + strWeraCategory + "/" + strProductCode ) );
		 * 
		 * // --- Pr�fe Bilder, wenn vorhanden dann archivieren try { // --- produktbilder --- START --- pictures =
		 * (Collection) oProduct.getAttribute("pictures1"); strDestDir = strImagePath + "produktbilder/" + strWeraCategory
		 * + "/" + strProductCode; archiveMedia ( pictures, strImagePath, strDestDir );
		 * 
		 * pictures = (Collection) oProduct.getAttribute("pictures2"); strDestDir = strImagePath + "produktbilder/" +
		 * strWeraCategory + "/" + strProductCode; archiveMedia ( pictures, strImagePath, strDestDir );
		 * 
		 * pictures = (Collection) oProduct.getAttribute("pictures3"); strDestDir = strImagePath + "produktbilder/" +
		 * strWeraCategory + "/" + strProductCode; archiveMedia ( pictures, strImagePath, strDestDir ); // ---
		 * produktbilder --- START ---
		 * 
		 * // --- icons --- START --- pictures = (Collection) oProduct.getAttribute("icons1"); strDestDir = strImagePath +
		 * "icons/" + strWeraCategory + "/" + strProductCode; archiveMedia ( pictures, strImagePath, strDestDir );
		 * 
		 * pictures = (Collection) oProduct.getAttribute("icons2"); strDestDir = strImagePath + "icons/" + strWeraCategory
		 * + "/" + strProductCode; archiveMedia ( pictures, strImagePath, strDestDir ); // --- icons --- ENDE ---
		 * 
		 * // --- anwendungsbilder --- START --- pictures = (Collection) oProduct.getAttribute("featureicons1");
		 * strDestDir = strImagePath + "anwendungsbilder/" + strWeraCategory + "/" + strProductCode; archiveMedia (
		 * pictures, strImagePath, strDestDir );
		 * 
		 * pictures = (Collection) oProduct.getAttribute("featureicons2"); strDestDir = strImagePath + "anwendungsbilder/"
		 * + strWeraCategory + "/" + strProductCode; archiveMedia ( pictures, strImagePath, strDestDir ); // ---
		 * anwendungsbilder --- START ---
		 * 
		 * 
		 * } catch (JaloInvalidParameterException e) { // TODO Auto-generated catch block e.printStackTrace(); } catch
		 * (JaloSecurityException e) { // TODO Auto-generated catch block e.printStackTrace(); }
		 * 
		 * } // ---if ( strWeraCategory.length() > 0 ) {
		 */
	}

	/**
	 * NOTE: Checks a collections for containing a code-number
	 * stringField=Field-Attribute to be checked (must exist) stringMatch=The
	 * Value
	 *
	 * @return Object, or null if not found
	 */
	public static Object checkContainingModel(final Collection<ItemModel> collection, final String stringField,
			final String stringMatch) {
		Object oResult = null;
		for (final ItemModel itemModel : collection) {
			try {
				//LOG.info("Comparing items field " + stringField + " (value:"
				//		+ itemModel.getAttributeProvider().getAttribute(stringField) + ") with literal " + stringMatch);
				if (itemModel.getAttributeProvider().getAttribute(stringField).equals(stringMatch)) {
					oResult = itemModel;
					break;
				}
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				LOG.error("checkContainingModel(): Exception: " + e.getMessage());
			}
		}

		return oResult;
	}

	/**
	 * NOTE: Checks a collections for containing a code-number
	 * stringField=Field-Attribute to be checked (must exist) stringMatch=The
	 * Value
	 *
	 * @param collection
	 * @param stringField
	 * @param stringMatch
	 * @return Object, or null if not found
	 */
	public static Object checkContaining(final Collection collection, final String stringField, final String stringMatch) {
		Object oResult = null;
		Item item = null;
		for (final Iterator it1 = collection.iterator(); it1.hasNext();) {

			item = (Item) it1.next();
			try {
				if (item.getAttribute(stringField).equals(stringMatch)) {
					oResult = item;
					break;
				}
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return oResult;
	}

	/**
	 * NOTE: Checks a collections for containing a code-number
	 * stringField=Field-Attribute to be checked (must exist) stringMatch=The
	 * Value
	 *
	 * @param collection
	 * @param stringField
	 * @param stringMatch
	 * @return Object, or null if not found
	 */
	public static Object checkContainingsVisibilityByOutputControl(final Collection collection, final String stringField, final String stringMatch, EnumerationValue evVISIBLE_IN_VARIANT ) {
		
		// --- initialize
		Object oResult					= null;
		Item item						= null;
		EnumerationValue evVisibility	= null;

		
		for (final Iterator it1 = collection.iterator(); it1.hasNext();) {

			item = (Item) it1.next();
			try {
				if (item.getAttribute(stringField).equals(stringMatch)) {
					
					// --- sichtbarkeit in artikeltabelle pr�fen
					evVisibility = (EnumerationValue) item.getAttribute( "visibility");
					if ( evVisibility.equals(evVISIBLE_IN_VARIANT) ) {
						oResult = item;
						break;
					}
				}
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return oResult;
	}

	/**
	 * NOTE:
	 *
	 * @return Object, or null if not found
	 */
	public static Object checkContainingCAA(final Collection collection, final ClassAttributeAssignment caa) {
		Object oResult = null;
		Item item = null;
		for (final Iterator it1 = collection.iterator(); it1.hasNext();) {

			item = (Item) it1.next();
			try {
				if (item.getAttribute("classattributeassignment").equals(caa)) {
					oResult = item;
					break;
				}
			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return oResult;
	}

	/**
	 * Hole alle aktiven getClassificationAttributes, sortiert
	 *
	 * @param category
	 * @return
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	public Collection getClassificationAttributes(final ClassificationClass category) throws JaloInvalidParameterException,
			JaloSecurityException {
		// --- Initialize
		final List colList = new ArrayList();
		Collection classificationattributes = new ArrayList();

		// --- Hole alle Kategorien
		//classificationattributes = CatalogManager.getInstance().getClassificationAttributes(category);
		classificationattributes = category.getClassificationAttributes();

		// --- Schleife �ber alle Ergebniss
		//System.out.println("s.CA.size()=" + classificationattributes.size());
		for (final Iterator it0 = classificationattributes.iterator(); it0.hasNext();) {
			final ClassificationAttribute classificationattribute = (ClassificationAttribute) it0.next();

			// --- Hole Katalogversion
			final ClassificationSystemVersion catalogVersion = (ClassificationSystemVersion) getAttribute(category, "catalogVersion");

			// --- Hole Aktiv-Flag Localized
			Boolean bAktiv = null;
			if (catalogVersion.getAttribute("version").equals(Config.getParameter("wera.mastercatalogversion"))) {
				bAktiv = (java.lang.Boolean) classificationattribute.getLocalizedProperty("aktiv");
			} else {
				// --- Andere Klassifikationssystem immer als aktiv durchgehen lassen
				bAktiv = new Boolean(true);
			}

			//System.out.println("1.getClassificationAttributes() => bAktiv="+bAktiv+", Name="+classificationattribute.getName());
			if (m_bCheckForActivation == false || (bAktiv != null && bAktiv.booleanValue())) {
				colList.add(classificationattribute);
			}
		}

		// --- Sortiere nach Order
		if (colList != null && colList.size() > 0) {
			Collections.sort(colList, new OrderComparator());
		}

		return colList;
	}

	/**
	 * Hole alle aktiven getClassattributeAssignments, sortiert
	 *
	 * @param classificationclass
	 * @return
	 */
	public Collection getClassattributeAssignments(final ClassificationClass classificationclass) {
		// --- Initialize
		final List colList = new ArrayList();
		Collection classattributeassignments = new ArrayList();

		// --- Hole alle Kategorien
		classattributeassignments = classificationclass.getClassificationAttributeAssignments();

		// --- Schleife �ber alle Ergebniss
		//System.out.println("s.CA.size()=" + classattributeassignments.size());
		for (final Iterator it0 = classattributeassignments.iterator(); it0.hasNext();) {
			final ClassAttributeAssignment classattributeassignment = (ClassAttributeAssignment) it0.next();

			// --- Hole Katalogversion
			final ClassificationSystemVersion catalogVersion = (ClassificationSystemVersion) getAttribute(classificationclass,
					"catalogVersion");

			// --- Hole Aktiv-Flag Localized
			Boolean bAktiv = null;
			// --- Andere Klassifikationssystem immer als aktiv durchgehen lassen
			bAktiv = new Boolean(true);
			try {
				if (catalogVersion.getAttribute("version").equals(Config.getParameter("wera.mastercatalogversion"))) {
					bAktiv = (java.lang.Boolean) classattributeassignment.getLocalizedProperty("aktiv");
				}

			} catch (final JaloSecurityException e) {
				e.printStackTrace();
			}

			//System.out.println("1.getClassificationAttributes() => bAktiv="+bAktiv+", Name="+classattributeassignment.getName());
			if (m_bCheckForActivation == false || (bAktiv != null && bAktiv.booleanValue())) {
				colList.add(classattributeassignment);
			}
		}

		// --- Sortiere nach Order
		if (colList != null && colList.size() > 0) {
			Collections.sort(colList, new OrderComparator());
		}

		return colList;
	}

	/**
	 * Hole alle aktiven Kategorien, sortiert
	 *
	 * @param product
	 * @param strIsoCode
	 * @return
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	public Collection getCategoriesByProduct(final WeraProduct product, final String strIsoCode)
			throws JaloInvalidParameterException, JaloSecurityException {
		// --- Initialize
		final List colList = new ArrayList();
		Collection categories = new ArrayList();

		// --- Sprachen anlegen, bzw. setzen
		final Language langDef = C2LManager.getInstance().getLanguageByIsoCode(strIsoCode);

		// --- Hole alle Kategorien
		categories = (Collection) product.getAttribute("supercategories");
		//categories = product.getLinkedItems(false, "CategoryProductRelation", langDef );

		// --- Schleife �ber alle Ergebniss
		for (final Iterator it0 = categories.iterator(); it0.hasNext();) {
			final Category category = (Category) it0.next();

			// --- Hole Katalogversion
			//ClassificationSystemVersion catalogVersion = (ClassificationSystemVersion) getAttribute(category,"catalogVersion");
			final Item catalogVersion = (Item) getAttribute(category, "catalogVersion");

			// --- Hole Aktiv-Flag Localized
			Boolean bAktiv = null;
			if (catalogVersion.getAttribute("version").equals(Config.getParameter("wera.mastercatalogversion"))) {
				bAktiv = (java.lang.Boolean) category.getLocalizedProperty("aktiv");
			} else {
				// --- Andere Klassifikationssystem immer als aktiv durchgehen lassen
				bAktiv = new Boolean(true);
			}

			//System.out.println("getCategoriesByProduct() => bAktiv="+bAktiv+", Name="+category.getName());
			if (m_bCheckForActivation == false || (bAktiv != null && bAktiv.booleanValue())) {
				colList.add(category);
			}
		}

		// --- Sortiere nach Order
		if (colList != null && colList.size() > 0) {
			Collections.sort(colList, new OrderComparator());
		}

		return colList;
	}

	/**
	 * Hole alle aktiven Kategorien (keine klassifizierenden) eines Produkt f�r
	 * eine bestimmte Katalogversion, sortiert
	 *
	 * @param product
	 * @param strCatalogVersion
	 * @return
	 * @throws JaloInvalidParameterException
	 */
	public Collection getCategoriesByProductAndCatalogVersion(final WeraProduct product, final String strCatalogVersion)
			throws JaloInvalidParameterException {
		// --- Initialize
		final List colList = new ArrayList();
		Collection categories = new ArrayList();

		try {
			// --- Hole alle Superkategorien
			categories = (Collection) product.getAttribute("supercategories");

			// --- Schleife �ber alle Ergebniss
			for (final Iterator it0 = categories.iterator(); it0.hasNext();) {
				final Category category = (Category) it0.next();
				if (!(category instanceof ClassificationClass)) {

					// --- Hole Katalogversion
					final Item catalogVersion = (Item) getAttribute(category, "catalogVersion");

					// --- Hole Aktiv-Flag Localized
					Boolean bAktiv = null;
					if (catalogVersion.getAttribute("version").toString().equals(strCatalogVersion)) {
						bAktiv = (java.lang.Boolean) category.getLocalizedProperty("aktiv");
					}

					//System.out.println("getCategoriesByProduct() => bAktiv="+bAktiv+", Name="+category.getName() + ", version=" + catalogVersion.getAttribute("version").toString());
					if ((bAktiv != null && bAktiv.booleanValue())) {
						colList.add(category);
					}

				}
			}
		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Sortiere nach Order
		if (colList != null && colList.size() > 0) {
			Collections.sort(colList, new OrderComparator());
		}

		return colList;
	}

	/**
	 *
	 * @param parameters
	 * @return
	 */
	public WeraProductSet createWeraProduktSet(final Map parameters) {
		try {
			System.out.println("WeraManager::createWeraProductSet called");
			final ComposedType WeraProduktType = getSession().getTypeManager().getComposedType(WeraProductSet.class);

			// --- Wera Variantentyp
			final ComposedType WeraVariantenType = getSession().getTypeManager().getComposedType(WeraVarianteSet.class);
			parameters.put("variantType", WeraVariantenType);

			// --- Produkt anlegen
			final WeraProductSet oWeraProductSet = (WeraProductSet) WeraProduktType.newInstance(parameters);

			// --- Defaultwerte initialisieren
			oWeraProductSet.setEssentialData();

			return oWeraProductSet;
		} catch (final JaloBusinessException e) {
			throw new JaloSystemException(e, "error creating WeraProduktSet.", 0);
		}

	}

	/**
	 *
	 * @param code
	 * @return
	 */
	public WeraProductSet createWeraProduktSet(final String code) {
		try {
			System.out.println("WeraManager::createWeraProductSet called");
			final ComposedType WeraProduktType = getSession().getTypeManager().getComposedType(WeraProductSet.class);
			final Map parameters = new HashMap();
			parameters.put("code", code);

			// --- Produkt anlegen
			final WeraProductSet oWeraProductSet = (WeraProductSet) WeraProduktType.newInstance(parameters);

			// --- Defaultwerte initialisieren
			oWeraProductSet.setEssentialData();

			return oWeraProductSet;

		} catch (final JaloBusinessException e) {
			throw new JaloSystemException(e, "error creating WeraProdukt.", 0);
		}
	}

	/**
	 *
	 * @param code
	 * @return
	 */
	public WeraProduct createWeraProdukt(final String code) {
		try {
			System.out.println("WeraManager::createWeraProduct called");
			final ComposedType WeraProduktType = getSession().getTypeManager().getComposedType(WeraProduct.class);
			final Map parameters = new HashMap();
			parameters.put("code", code);

			// --- Produkt anlegen
			final WeraProduct oWeraProduct = (WeraProduct) WeraProduktType.newInstance(parameters);

			// --- Defaultwerte initialisieren
			oWeraProduct.setEssentialData();

			return oWeraProduct;

		} catch (final JaloBusinessException e) {
			throw new JaloSystemException(e, "error creating WeraProdukt.", 0);
		}
	}

	/**
	 *
	 * @param WeraVariantenType
	 * @param parameters
	 * @return
	 */
	public WeraVariante createWeraProductVariante(final ComposedType WeraVariantenType, final Map parameters) {
		try {
			System.out.println("WeraManager::createWeraProductVariante called");
			// --- WeraVariante anlegen
			final WeraVariante oWeraVariante = (WeraVariante) WeraVariantenType.newInstance(parameters);

			// --- Defaultwerte initialisieren
			oWeraVariante.setEssentialData();

			return oWeraVariante;

		} catch (final JaloBusinessException e) {
			throw new JaloSystemException(e, "error creating WeraProductVariante.", 0);
		}

	}

	/**
	 *
	 * @param parameters
	 * @return
	 */
	public WeraVariante createWeraProductVariante(final Map parameters) {
		try {
			System.out.println("WeraManager::createWeraProductVariante called");
			final ComposedType WeraVariantenType = getSession().getTypeManager().getComposedType(WeraVariante.class);
			//System.out.println("WeraVariantenType="+WeraVariantenType);
			// --- WeraVariante anlegen
			//System.out.println("parameters="+parameters);
			final WeraVariante oWeraVariante = (WeraVariante) WeraVariantenType.newInstance(parameters);

			// --- Defaultwerte initialisieren
			//System.out.println("oWeraVariante="+oWeraVariante);
			oWeraVariante.setEssentialData();

			return oWeraVariante;

		} catch (final JaloBusinessException e) {
			throw new JaloSystemException(e, "error creating WeraProductVariante.", 0);
		}

	}

	/**
	 *
	 * @param WeraVariantenType
	 * @param parameters
	 * @return
	 */
	public WeraVariante createWeraProductVarianteSet(final ComposedType WeraVariantenType, final Map parameters) {
		try {
			// --- WeraVariante anlegen
			System.out.println("WeraManager::createWeraProductVarianteSet called");

			final WeraVarianteSet oWeraVariante = (WeraVarianteSet) WeraVariantenType.newInstance(parameters);

			return oWeraVariante;
		} catch (final JaloBusinessException e) {
			throw new JaloSystemException(e, "error creating WeraProductVariante.", 0);
		}

	}

	/**
	 *
	 * @param parameters
	 * @return
	 */
	public WeraProduct createWeraProdukt(final Map parameters) {
		try {
			System.out.println("WeraManager::createWeraProduct called");
			final ComposedType WeraProduktType = getSession().getTypeManager().getComposedType(WeraProduct.class);

			// --- Wera Variantentyp
			final ComposedType WeraVariantenType = getSession().getTypeManager().getComposedType(WeraVariante.class);
			parameters.put("variantType", WeraVariantenType);

			// --- Produkt anlegen
			final WeraProduct oWeraProduct = (WeraProduct) WeraProduktType.newInstance(parameters);

			// --- Initialisiere Grunddaten
			oWeraProduct.setEssentialData();

			return oWeraProduct;
		} catch (final JaloBusinessException e) {
			throw new JaloSystemException(e, "error creating yourType.", 0);
		}

	}

	/**
	 * Hole alle Kategorien
	 *
	 * @param strClassificationSystem
	 * @param strCatalogVersion
	 * @param strStartCategorie
	 * @return
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	public Collection getCategories(final String strClassificationSystem, final String strCatalogVersion,
			final String strStartCategorie) throws JaloInvalidParameterException, JaloSecurityException {
		// --- Initialize
		// System.out.println("strStartCategorie="+strStartCategorie);
		//C2LManager.getInstance().
		final String strResult = "";
		final List colList = new ArrayList();
		Collection categories = new ArrayList();

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final CatalogVersion weraCatalogVersion = getCatalogVersion(strClassificationSystem, strCatalogVersion);

		// --- Hole alle Kategorien
		if (weraCatalogVersion != null) {
			if (strStartCategorie.length() == 0) {
				// --- Alle Categorien
				//System.out.println("Alle Kategorien");
				categories = weraCatalogVersion.getAllCategories();
			} else {
				// --- Nur Unterkategorien
				//System.out.println("Nur Unterkategorien: " + strStartCategorie);
				final Category categoryRoot = weraCatalogVersion.getCategory(strStartCategorie);
				if (categoryRoot != null) {
					categories = (Collection) categoryRoot.getAttribute("categories");

					//System.out.println("Kategorie: " + categoryRoot.getCode());
					//System.out.println("Anzahl Unterkategorien: " + categories.size());
				}
				//				else
				//				   System.out.println(">>>NOT FOUND=" + strStartCategorie);
				//				if ( categoryRoot != null ) {
				//					categories = (Collection) categoryRoot.getAllSubcategories();
				//				}
			}
		} // --- if ( weraCatalogVersion != null )

		// --- Schleife �ber alle Ergebniss
		for (final Iterator it0 = categories.iterator(); it0.hasNext();) {
			final Category category = (Category) it0.next();

			// --- Hole Aktiv-Flag Localized
			final Boolean bAktiv = (java.lang.Boolean) category.getLocalizedProperty("aktiv");

			//System.out.println("bAktiv="+bAktiv+", Name="+category.getName());
			if (m_bCheckForActivation == false || (bAktiv != null && bAktiv.booleanValue())) {
				colList.add(category);
			}
		}

		// --- Sortiere nach Order
		if (colList != null && colList.size() > 0) {
			Collections.sort(colList, new OrderComparator());
		}
		return colList;
	}

	/**
	 *
	 * @param strCatalog
	 * @param strCatalogVersion
	 * @return
	 */
	public CatalogVersion getCatalogVersion(final String strCatalog, final String strCatalogVersion) {

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final CatalogManager catalogManager = CatalogManager.getInstance();
		final Catalog weraCatalog = catalogManager.getCatalog(strCatalog);
		final CatalogVersion weraCatalogVersion = weraCatalog.getCatalogVersion(strCatalogVersion);

		return weraCatalogVersion;
	}

	/**
	 *
	 * @param strCatalog
	 * @return
	 */
	public Catalog getCatalog(final String strCatalog) {
		// --- wera catalog holen, oder ggf. neu anlegen
		final CatalogManager catalogManager = CatalogManager.getInstance();
		final Catalog weraCatalog = catalogManager.getCatalog(strCatalog);

		return weraCatalog;
	}

	/**
	 *
	 * @param strClassificationSystem
	 * @param strCatalogVersion
	 * @return
	 */
	public ClassificationSystemVersion getCSV(final String strClassificationSystem, final String strCatalogVersion) {
		final String strResult = "";

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final CatalogManager catalogManager = CatalogManager.getInstance();
		final ClassificationSystem weraCatalog = catalogManager.getClassificationSystem(strClassificationSystem);
		final ClassificationSystemVersion weraCatalogVersion = weraCatalog.getSystemVersion(strCatalogVersion);

		return weraCatalogVersion;
	}

	/**
	 *
	 * @return
	 */
	public Set getAllKeyword() {

		final ComposedType KeywordType = getSession().getTypeManager().getComposedType(Keyword.class);

		return KeywordType.getAllInstances();
	}

	/**
	 *
	 * @return
	 */
	public Set getAllFootnotes() {

		final ComposedType FootnoteType = getSession().getTypeManager().getComposedType(Footnote.class);

		return FootnoteType.getAllInstances();

	}

	/**
	 *
	 * @return
	 */
	public Set getAllExtImages() {
		final ComposedType ExtImageType = getSession().getTypeManager().getComposedType(ExtImage.class);
		return ExtImageType.getAllInstances();
	}

	/**
	 *
	 * @param oItem
	 * @param strField
	 * @return
	 */
	WeraMedia _getPicture(final Item oItem, final String strField) {
		// --- Initialize
		WeraMedia weramedia = null;

		final Collection colPictures = (Collection) getAttribute(oItem, strField);
		if (colPictures != null && colPictures.size() > 0) {
			weramedia = (WeraMedia) colPictures.iterator().next();
		}

		return weramedia;
	}

	/**
	 *
	 * @param oItem
	 * @param strField
	 * @return
	 */
	Collection<WeraMedia> _getPictures(final Item oItem, final String strField) {
		// --- Initialize
		Collection<WeraMedia> colIconMedias = (Collection<WeraMedia>) getAttribute(oItem, strField);
		if (colIconMedias == null) {
			colIconMedias = new ArrayList();
		}

		return colIconMedias;
	}

	/**
	 * Hole alle aktiven Varianten, sortiert
	 *
	 * @param product
	 * @return
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	public Collection getVariants(final WeraProduct product) throws JaloInvalidParameterException, JaloSecurityException {
		// --- Debug
		//System.out.println("s.getVariants() => f�r product=" + product.getCode());

		// --- Initialize
		final List colList = new ArrayList();
		Collection variants = new ArrayList();

		// --- Hole alle Produkte
		variants = (Collection) product.getAttribute("variants");
		// --- Schleife �ber alle Ergebniss
		for (final Iterator it0 = variants.iterator(); it0.hasNext();) {
			final WeraVariante weraproductvariant = (WeraVariante) it0.next();
			if (weraproductvariant != null) {
				// --- Hole Aktiv-Flag Localized
				final Boolean bAktiv = (java.lang.Boolean) weraproductvariant.getLocalizedProperty("aktiv");
				if (m_bCheckForActivation == false || (bAktiv != null && bAktiv.booleanValue())) {
					//System.out.println("getVariants() => bAktiv="+bAktiv+", Code="+weraproductvariant.getCode() + ", m_bCheckForActivation=" + m_bCheckForActivation);
					colList.add(weraproductvariant);
				}
			}
		}

		// --- Sortiere nach Order
		if (colList != null && colList.size() > 0) {
			Collections.sort(colList, new OrderComparator());
		}

		//System.out.println("s.getVariants() => f�r product="+product.getCode()+"result="+colList.size());
		return colList;
	}

	/**
	 *
	 * @return
	 */
	public Collection getProducts() {
		final String strResult = "";

		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final CatalogManager catalogManager = CatalogManager.getInstance();
		final ClassificationSystem weraCatalog = catalogManager.getClassificationSystem(Config.getParameter("wera.mastercatalog"));
		//ClassificationSystemVersion weraCatalogVersion = (ClassificationSystemVersion) weraCatalog.getActiveCatalogVersion();
		final CatalogVersion weraCatalogVersion = getCatalogVersion(Config.getParameter("wera.mastercatalog"),
				Config.getParameter("wera.mastercatalogversion"));

		return weraCatalogVersion.getAllProducts();
	}

	/**
	 * Hole alle aktiven Produkte, sortiert
	 *
	 * @param category
	 * @return
	 */
	public Collection getProductsOrderedByCategory(final Category category) {
		//System.out.println("++getProductsOrderedByCategory.category="+category.getCode());
		// --- Initialize
		final String strPkCategory = category.getPK().toString();
		Category2ProductExt category2productext = null;
		final List colList = new ArrayList();
		Collection products = new ArrayList();
		Product product = null;
		boolean bAktiv = false;
		Integer oIntOrder = null;

		// --- Sortierungs Comperator initialisieren
		final WeraProductByCategoryOrderComparator oWeraProductByCategoryOrderComparator = new WeraProductByCategoryOrderComparator();

		// --- Hole alle Produkte
		products = category.getProducts();
		//System.out.println("category.getpk=" + category.getPK() + "/ code=" + category.getCode() + "products.size()="
		//		+ products.size());

		// --- Schleife �ber alle Ergebniss
		for (final Iterator it0 = products.iterator(); it0.hasNext();) {

			// --- Hole Produkt
			product = (Product) it0.next();
			if (product instanceof WeraProduct) {

				try {
					// --- Initialisiere Default, Aktiv-Flag (Produkt) / Order (Product)
					final Boolean boolAktiv = (Boolean) ((WeraProduct) product).getAttribute("aktiv");
					if (boolAktiv != null) {
						bAktiv = boolAktiv.booleanValue();
					} else {
						bAktiv = false;
					}
					oIntOrder = (Integer) getAttribute("order");

					// --- Hole erweitere Steuerparameter
					category2productext = ((WeraProduct) product).getCategory2Productexts(strPkCategory);
					if (category2productext != null) {

						// --- Hole Order / By Category
						final String strPrio = category2productext.getPriority();
						if (strPrio != null && strPrio.trim().length() > 0) {
							oIntOrder = new Integer(Integer.parseInt(strPrio));
						}
					}

					// --- Prüfe Aktiv-Flag Localized
					if (m_bCheckForActivation == false || bAktiv) {
						colList.add(product);
						if (oIntOrder == null) {
							oIntOrder = new Integer(0);
						}
						oWeraProductByCategoryOrderComparator.hashCategory2ProductExt.put(product.getPK().toString(), oIntOrder);
						// System.out.println("++add="+product.getCode() + ", m_bCheckForActivation="+m_bCheckForActivation);
					}

				} catch (final Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		} // --- for (Iterator it0 = products.iterator(); it0.hasNext();) {

		// --- Sortiere nach Order
		if (colList != null && colList.size() > 0) {
			Collections.sort(colList, oWeraProductByCategoryOrderComparator);
		} else {
			//System.out.println("category.getpk=" + category.getPK() + "/ code=" + category.getCode() + "colList.size()="
			//		+ colList.size());
		}

		return colList;
	}

	/**
	 * Hole alle aktiven Produkte, sortiert
	 *
	 * @param category
	 * @return
	 */
	public Collection getProducts(final Category category) {
		// --- Initialize
		final List colList = new ArrayList();
		Collection products = new ArrayList();
		Product product = null;

		// --- Hole alle Produkte
		products = category.getProducts();

		// --- Schleife �ber alle Ergebniss
		for (final Iterator it0 = products.iterator(); it0.hasNext();) {

			// --- Hole Produkt
			product = (Product) it0.next();
			// System.out.println("product.getClass().toString()="+product.getClass().toString());
			if (product instanceof WeraProduct) {

				// --- Hole Aktiv-Flag Localized
				final Boolean bAktiv = (java.lang.Boolean) getAttribute(product, "aktiv");

				//System.out.println("getProducts() => bAktiv="+bAktiv+", Name="+product.getName());
				if (m_bCheckForActivation == false || (bAktiv != null && bAktiv.booleanValue())) {
					colList.add(product);
					//System.out.println("++added++"+product.getCode());
				}
			}
		}

		// --- Sortiere nach Order
		if (colList != null && colList.size() > 0) {
			Collections.sort(colList, new OrderComparator());
		}

		return colList;
	}

	/**
	 *
	 * @param strIsoCode
	 */
	public void initLanguageByIsoCode(final String strIsoCode) {
		// --- Sprachen anlegen, bzw. setzen
		final Language langDef = C2LManager.getInstance().getLanguageByIsoCode(strIsoCode);
		if (langDef != null) {
			try {
				langDef.setActive(true);
			} catch (final ConsistencyCheckException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			JaloSession.getCurrentSession().getSessionContext().setLanguage(langDef);
			System.out.println("Spache setzen strSprache=" + strIsoCode);
		} else {
			System.out.println("Fehler beim setzen der Spache (null)=" + strIsoCode);
		}
	}

	/**
	 *
	 */
	public void enableCheckForActivation() {
		m_bCheckForActivation = true;
	}

	/**
	 *
	 */
	public void disableCheckForActivation() {
		m_bCheckForActivation = false;
	}

	/**
	 *
	 * @return
	 */
	public boolean getCheckForActivation() {
		return m_bCheckForActivation;
	}

	/**
	 * Hole alle Kategorien
	 *
	 * @param strClassificationSystem
	 * @param strCatalogVersion
	 * @return
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	public Category getRootCategory(final String strClassificationSystem, final String strCatalogVersion)
			throws JaloInvalidParameterException, JaloSecurityException {
		// --- wera catalog und catalogversion holen, oder ggf. neu anlegen
		final CatalogVersion weraCatalogVersion = getCatalogVersion(strClassificationSystem, strCatalogVersion);
		final Collection colRootCategories = weraCatalogVersion.getRootCategories();
		if (colRootCategories != null && colRootCategories.size() > 0) {
			for (final Iterator it1 = colRootCategories.iterator(); it1.hasNext();) {
				final Category category = (Category) it1.next();
				if (category.getCode().equals("root")) {
					return category;
				}
			}
		}

		return null;
	}

	/**
	 *
	 * @param dPriceValue
	 * @param strExportLanguage
	 * @return
	 */
	public String _strFormatPrice(final Double dPriceValue, final String strExportLanguage) {

		// --- Initialize
		String strPriceValue = "";

		final Object oFormat[]
				= {dPriceValue};
		strPriceValue = String.format("%10.2f", oFormat);
		if (strExportLanguage.equals("en") || strExportLanguage.equals("en-us")) {
			strPriceValue = strPriceValue.replace(',', '.');
		} else {
			strPriceValue = strPriceValue.replace('.', ',');
		}

		// --- Leerzeichen entfernen
		strPriceValue = strPriceValue.replaceAll(" ", "");

		return strPriceValue;
	}

	/**
	 *
	 * @param article
	 * @param strPriceList
	 * @return
	 */
	public Collection _aGetPriceList(final Product article, final String strPriceList) {

		// --- Initialize
		final ArrayList prices = new ArrayList();

		final Collection europe1prices = (Collection) getAttribute(article, "europe1Prices");
		if (europe1prices != null && europe1prices.size() > 0) {

			// --- Initialize
			PriceRow pricerow = null;

			// --- Hole nur die Preise die Exportiert werden sollen
			for (final Iterator it = europe1prices.iterator(); it.hasNext();) {
				pricerow = (PriceRow) it.next();
				final String cPriceGroup = pricerow.getCustomerGroup().getCode();
				if (cPriceGroup.equals(strPriceList)) {
					prices.add(pricerow);
				}
			}
			// --- Sortiere die Preise nach der Staffel
			final OrderComparator oOrderComparator = new OrderComparator();
			Collections.sort(prices, oOrderComparator);
		}

		return prices;
	}

	/**
	 *
	 * @param category
	 * @return
	 */
	public String _getCategorieTree(final Category category) {

		// --- Initialize
		String strResult = "";
		Category supercategory = null;

		// --- Hole alle Supercategorien
		final Collection supercategories = (Collection) getAttribute(category, "supercategories");

		// --- Schleife �ber alle
		if (supercategories != null && supercategories.size() > 0) {
			for (final Iterator it1 = supercategories.iterator(); it1.hasNext();) {
				supercategory = (Category) it1.next();
				if (supercategory.getCode().equals("root")) {
					break;
				} else {
					strResult += _getCategorieTree(supercategory);
				}
			}
		}

		// --- Eigene Categorie anf�gen
		strResult += "." + category.getName();

		return strResult;
	}

	/**
	 *
	 * @param strSearch
	 * @param listArg2
	 * @param iRange
	 * @return
	 */
	SearchResult serachItem(final String strSearch, final List listArg2, final int iRange) {
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(strSearch, null, listArg2, true, // fail on unknown fields
				true, // don't need total
				0, iRange // range
		);

		return res;
	}

	/**
	 *
	 * @param strSearch
	 * @param listArg2
	 * @return
	 */
	SearchResult serachItem(final String strSearch, final List listArg2) {
		final SearchResult res = JaloSession.getCurrentSession().getFlexibleSearch().search(strSearch, null, listArg2, true, // fail on unknown fields
				true, // don't need total
				0, 1 // range
		);

		return res;
	}

	/**
	 * Dateinamen / Pfad f�r Exporte
	 *
	 * @return
	 */
	public String InitOutputDatum() {

		// --- Initialize
		String strDatum = "";

		// --- Datum ermitteln
		final Calendar cal = Calendar.getInstance();
		final DateFormat formatter = new SimpleDateFormat("yyyyMMdd_HHmm");
		strDatum = formatter.format(cal.getTime());

		// --- Sonderzeichen entfernen
		strDatum = strDatum.replace(" ", "_");
		strDatum = strDatum.replace(":", "");
		strDatum = strDatum.replace(".", "");

		return strDatum;

	}

	/**
	 * Hole die Reihenfolge an der Relation Product / Category
	 *
	 * @param oProduct
	 * @param oCategory
	 * @return
	 */
	public Integer _getCategory2ProductsOrder(final Product oProduct, final Category oCategory) {

		// --- Initialize
		Integer iOrder = new Integer(0);

		// --- Hole die Reihenfolge in abh�ndigkeit zum Category / Produkt
		iOrder = ((WeraProduct) oProduct).getOrderByCategory(oCategory);

		return iOrder;
	}

	/**
	 *
	 * @param strFileName
	 * @param strCode
	 * @param strMediaKategorie
	 * @return
	 */
	public WeraMedia createWebMedia(final String strFileName, final String strCode, final String strMediaKategorie) {

		// --- Initialize
		WeraMedia oWeraMedia = null;

		// --- Initialisiere File
		File file = null;
		if (strFileName != null) {
			file = new File(strFileName);
		}

		// --- Pr�fe, ob die Datei �berhaupt existiert
		if (file != null && file.exists()) {

			// --- Wera Mediatyp
			final ComposedType WeraMediaType = getSession().getTypeManager().getComposedType(WeraMedia.class);

			// --- Initialize
			final HashMap parameters = new HashMap();
			parameters.put("code", strCode);
			parameters.put("mediakategorie", strMediaKategorie);
			parameters.put("realfilename", file.getName());

			// --- Media anlegen und f�llen anlegen
			try {
				// --- Neue Instance
				oWeraMedia = (WeraMedia) WeraMediaType.newInstance(parameters);
				System.out.println(">>>createWebMedia.newInstance:Result=" + oWeraMedia);

				// --- Grafik hochladen
				try {
					oWeraMedia.setFile(file);
				} catch (final JaloBusinessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				// --- Aufr�umen
				file = null;

			} catch (final Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} // --- if ( file.exists() == false )
		else {
			System.out.println(">>>createWebMedia:Result=" + oWeraMedia);
		}

		return oWeraMedia;
	}

	/**
	 *
	 * @param oCategory
	 * @param strLanguage
	 * @return
	 */
	public String strNormalizeURL(final Category oCategory, final String strLanguage) {
		// TODO Auto-generated method stub
		// --- Initialize
		String strResult = "";
		Category oParentCategory = null;
		Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();
		// --- Sprache f�r Kategorie nur DE oder EN
		if (strLanguage.equals("de") || strLanguage.equals("en") || strLanguage.equals("fr") || strLanguage.equals("it")
				|| strLanguage.equals("es") || strLanguage.equals("us-en")) {
			SetLanguage(strLanguage);
		} else {
			SetLanguage("en");
			//LOG.info("strNormalizeURL(): setting language to EN; native is " + strLanguage);
		}

		// --- Beschreibung und Untertitel zusammenfassen
		Boolean bConcat_description = (Boolean) getAttribute(oCategory, "concat_description");
		if (bConcat_description == null) {
			bConcat_description = new Boolean(false);
		}

		// --- Name der Kategory holen und Sprache zur�cksetzen
		String strUntertitel = "";
		if (bConcat_description.booleanValue()) {
			strUntertitel = "_" + (String) getAttribute(oCategory, "untertitel");
		}

		strResult = ((String) getAttribute(oCategory, "name")) + strUntertitel;
		
		Collection colCategories = (Collection) getAttribute(oCategory, "supercategories");
		while (colCategories.size() > 0) {
			oParentCategory = (Category) colCategories.iterator().next();
			String strResultParent = ( getAttribute(oParentCategory, "name") != null ) ? getAttribute(oParentCategory, "name").toString() : "";
			if (!oParentCategory.getCode().equals("root")) {
				strResult = strResultParent + "_" + strResult;
			}
			colCategories = (Collection) getAttribute(oParentCategory, "supercategories");
		}

		// LOG.info("strNormalizeURL(): current path is: " + strResult);
		// --- Ergebnis konvertieren
		strResult = _strNormalizeURL(strResult);

		// LOG.info("strNormalizeURL(): corrected path is: " + strResult);
		// --- Sprache zur�cksetzen
		SetLanguage(currentSessionLanguage.getIsoCode());

		return strResult;
	}

	/**
	 *
	 * @param strValue
	 * @return
	 */
	private String _strNormalizeURL(String strValue) {
		// TODO Auto-generated method stub

		// --- Kategoriepfad konvertieren
		strValue = strValue.toLowerCase();
		strValue = WeraProduct.s_normalizeFilenameForImageLookup(strValue);
		strValue = strValue.replace("rootkategorie_", "");
		strValue = strValue.replace("__", "_");

		// --- Mapping FR/IT/ES (FR default)
		strValue = strValue.replace("\u00A1", "i"); // --- � (ES)
		strValue = strValue.replace("\u00e9", "e"); // --- �
		strValue = strValue.replace("\u00C0", "a"); // --- �
		strValue = strValue.replace("\u00C1", "a"); // --- �
		strValue = strValue.replace("\u00C2", "a"); // --- �
		strValue = strValue.replace("\u00C6", "ae"); // --- �
		strValue = strValue.replace("\u00C7", "c"); // --- �
		strValue = strValue.replace("\u00C8", "e"); // --- �
		strValue = strValue.replace("\u00C9", "e"); // --- �
		strValue = strValue.replace("\u00CA", "e"); // --- �
		strValue = strValue.replace("\u00CB", "e"); // --- �
		strValue = strValue.replace("\u00CC", "i"); // --- � (IT)
		strValue = strValue.replace("\u00CD", "i"); // --- � (IT)
		strValue = strValue.replace("\u00CE", "i"); // --- �
		strValue = strValue.replace("\u00CF", "i"); // --- �
		strValue = strValue.replace("\u00D1", "N"); // --- � (ES)
		strValue = strValue.replace("\u00D2", "o"); // --- � (IT)
		strValue = strValue.replace("\u00D3", "o"); // --- � (IT)
		strValue = strValue.replace("\u00D4", "o"); // --- �
		strValue = strValue.replace("\u00D9", "u"); // --- �
		strValue = strValue.replace("\u00DA", "u"); // --- � (IT)
		strValue = strValue.replace("\u00DB", "u"); // --- �
		strValue = strValue.replace("\u00E0", "a"); // --- �
		strValue = strValue.replace("\u00E1", "a"); // --- �
		strValue = strValue.replace("\u00E2", "a"); // --- �
		strValue = strValue.replace("\u00E6", "ae"); // --- �
		strValue = strValue.replace("\u00E7", "c"); // --- �
		strValue = strValue.replace("\u00E8", "e"); // --- �
		strValue = strValue.replace("\u00E9", "e"); // --- �
		strValue = strValue.replace("\u00EA", "e"); // --- �
		strValue = strValue.replace("\u00EB", "e"); // --- �
		strValue = strValue.replace("\u00EC", "i"); // --- � (IT)
		strValue = strValue.replace("\u00ED", "i"); // --- � (IT)
		strValue = strValue.replace("\u00EE", "i"); // --- �
		strValue = strValue.replace("\u00EF", "i"); // --- �
		strValue = strValue.replace("\u00F1", "n"); // --- � (ES)
		strValue = strValue.replace("\u00F2", "o"); // --- � (IT)
		strValue = strValue.replace("\u00F3", "o"); // --- � (IT)
		strValue = strValue.replace("\u00F4", "o"); // --- �
		strValue = strValue.replace("\u00F9", "u"); // --- �
		strValue = strValue.replace("\u00FA", "u"); // --- � (IT)
		strValue = strValue.replace("\u00FB", "u"); // --- �
		strValue = strValue.replace("\u00FC", "u"); // --- � (ES)
		strValue = strValue.replace("\u00FF", "y"); // --- �
		strValue = strValue.replace("\u0152", "oe"); // --- �
		strValue = strValue.replace("\u0153", "oe"); // --- �
		strValue = strValue.replace("\u0178", "y"); // --- �

		strValue = strValue.replaceAll("[^\\w\\-]", "");

		return strValue;
	}
	
    /*
     * create connection to MySQL
     */
    public static Connection getDWHConnection() {
	final String DATAWAREHOUSE_DB_NAME = "wera_datawarehouse";
	final String DATAWAREHOUSE_DB_USER = "weradwh";
	final String DATAWAREHOUSE_DB_PASS = "w3r4";
// mysqldump -u root -p wera_datawarehouse products_merged > /www_root/dwh_250821.sql

	Connection myCon = null;
	try {
	    Class.forName("com.mysql.jdbc.Driver").newInstance();
	    myCon = DriverManager.getConnection("jdbc:mysql:///" + DATAWAREHOUSE_DB_NAME + "?useUnicode=true&characterEncoding=utf-8",
		    DATAWAREHOUSE_DB_USER, DATAWAREHOUSE_DB_PASS);

	    if (!myCon.isClosed()) {
		// LOG.info("getDWHConnection(): Successfully connected to " + "MySQL server using TCP/IP...");
	    }
	} catch (final Exception e) {
	    LOG.error("getDWHConnection(): Exception: " + e.getMessage());
	}

	return myCon;
    }

    /*
     * same as getVisibilityForCatalog for model objects, see below.
    */
    public byte getVisibilityForCatalog( final Object p, final CatalogVersion cv, final boolean bLive ) {
        Date dFrom, dTo, dCatalogFrom, dNow;
        byte iVisibility = 0; // default: product visible within catalog constraints
        
        if ( p instanceof WeraVariante ) {
            WeraVariante wvm = (WeraVariante) p;
            dFrom = wvm.getValid_from() == null ? WeraManager.dEarliest : wvm.getValid_from();
            dTo = wvm.getValid_to() == null ? WeraManager.dLatest : wvm.getValid_to();
        } else {
            if ( p instanceof WeraProductSet ) {
                WeraProductSet wps = (WeraProductSet) p;
                dFrom = wps.getValid_from() == null ? WeraManager.dEarliest : wps.getValid_from();
                dTo = wps.getValid_to() == null ? WeraManager.dLatest : wps.getValid_to();
            } else {
                LOG.warn("getVisibilityForCatalog(jalo): no valid product type for date check. assuming visible");
                return iVisibility;
            }
        } 
        if ( bLive ) {
            // for live catalogs ignore the catalog version parameter, only reference is "now".
            dNow = new Date();
            // compare start to now
            int iCmp1 = dFrom.compareTo(dNow);

            // compare end to now
            int iCmp2 = dTo.compareTo(dNow);

            // LOG.info("getVisibilityForLiveCatalog(): product from/to = "+dFrom+"/"+dTo+". iCmp1 = "+iCmp1+", iCmp2 = "+iCmp2);
            if ( iCmp1 > 0 ) {
                iVisibility = 1; // product not yet visible 
            } else {
                if ( iCmp2 < 0 ) {
                   iVisibility = -1; // product expired
                }
            }            
        } else {
            // date-restricted catalog version
            if ( cv != null ) {
                try {
                    dCatalogFrom = (Date) cv.getAttribute("valid_from");
                    LOG.info("catalog "+cv.getVersion()+" has valid_from: "+ dCatalogFrom);
                    // dCatalogTo = cvm.getValid_to() == null ? WeraManager.dLatest : cvm.getValid_to();
					if ( dCatalogFrom == null ) {
						dCatalogFrom = WeraManager.dEarliest;
					}

                } catch (Exception ex) {
                    dCatalogFrom = WeraManager.dEarliest;
                }
            } else {
                dCatalogFrom = WeraManager.dEarliest;
            }
            // compare start dates
            int iCmp1 = dFrom.compareTo(dCatalogFrom);
            // compare end date with catalog start
            int iCmp2 = dTo.compareTo(dCatalogFrom);

            LOG.info("getVisibilityForCatalog(jalo): product from/to = "+dFrom+"/"+dTo+", catalog from/to = "+dCatalogFrom+"/not Used. iCmp1 = "+iCmp1+", iCmp2 = "+iCmp2);
            if ( iCmp1 > 0 ) {
                iVisibility = 1; // product visibility starts AFTER catalog start date
            } else {
                if ( iCmp2 <= 0 ) {
                    iVisibility = -1; // product visibility ended BEFORE catalog start date
                }
            }
        }
        LOG.info("getVisibilityForCatalog(jalo) returning :"+iVisibility);
        return iVisibility;        
    }
   /*
     * same as getVisibilityForCatalog with 3 arguments but fetch current catalog version from JaloSession
    */
    public byte getVisibilityForCatalog( final Object p ) {
        
        // without catalog arguments get catalog version from JaloSession
        Collection<CatalogVersion> colCatalogs = CatalogManager.getInstance().getSessionCatalogVersions(JaloSession.getCurrentSession().getSessionContext());
        CatalogVersion cv = null;
        boolean bLive = true;
        if ( colCatalogs != null  ) {
            cv = colCatalogs.iterator().next();
            try {
                Date dValidFrom = (Date) cv.getAttribute("valid_from");
                Date dValidTo = (Date) cv.getAttribute("valid_to");
                bLive = dValidFrom == null && dValidTo == null;
            } catch ( JaloSecurityException jse ) {
                LOG.error("ERROR getting converting attribute valid_from|to to Date.");
            }
        }
        return getVisibilityForCatalog ( p, cv, bLive );
        /*
        LOG.info("getVisibilityForCatalog() called for arguments:");
        if ( p instanceof WeraVariante ) {
            WeraVariante wvm = (WeraVariante) p;
            LOG.info("P = " + wvm.getCode() );
        }
        if ( p instanceof WeraProductSet ) {
            WeraProductSet wps = (WeraProductSet) p;
            LOG.info("P = " + wps.getCode() );
        }
        LOG.info("CV = " + (cv != null ? cv.getVersion() : "null" ) );
        LOG.info("L = "+bLive);
        
        Date dFrom, dTo, dCatalogFrom, dNow;
        byte iVisibility = 0; // default: product visible within catalog constraints
        
        if ( p instanceof WeraVariante ) {
            WeraVariante wvm = (WeraVariante) p;
            dFrom = wvm.getValid_from() == null ? WeraManager.dEarliest : wvm.getValid_from();
            dTo = wvm.getValid_to() == null ? WeraManager.dLatest : wvm.getValid_to();
        } else {
            if ( p instanceof WeraProductSet ) {
                WeraProductSet wps = (WeraProductSet) p;
                dFrom = wps.getValid_from() == null ? WeraManager.dEarliest : wps.getValid_from();
                dTo = wps.getValid_to() == null ? WeraManager.dLatest : wps.getValid_to();
            } else {
                LOG.warn("getVisibilityForCatalog(jalo): no valid product type for date check. assuming visible");
                return iVisibility;
            }
        } 
        if ( bLive ) {
            // for live catalogs ignore the catalog version parameter, only reference is "now".
            dNow = new Date();
            // compare start to now
            int iCmp1 = dFrom.compareTo(dNow);

            // compare end to now
            int iCmp2 = dTo.compareTo(dNow);

            // LOG.info("getVisibilityForLiveCatalog(): product from/to = "+dFrom+"/"+dTo+". iCmp1 = "+iCmp1+", iCmp2 = "+iCmp2);
            if ( iCmp1 > 0 ) {
                iVisibility = 1; // product not yet visible 
            } else {
                if ( iCmp2 < 0 ) {
                   iVisibility = -1; // product expired
                }
            }            
        } else {
            // date-restricted catalog version
            if ( cv != null ) {
                try {
                    dCatalogFrom = (Date) cv.getAttribute("valid_from");
                    LOG.info("catalog "+cv.getVersion()+" has valid_from: "+ dCatalogFrom);
                    // dCatalogTo = cvm.getValid_to() == null ? WeraManager.dLatest : cvm.getValid_to();
					if ( dCatalogFrom == null ) {
						dCatalogFrom = WeraManager.dEarliest;
					}

                } catch (Exception ex) {
                    dCatalogFrom = WeraManager.dEarliest;
                }
            } else {
                dCatalogFrom = WeraManager.dEarliest;
            }
            // compare start dates
            int iCmp1 = dFrom.compareTo(dCatalogFrom);
            // compare end date with catalog start
            int iCmp2 = dTo.compareTo(dCatalogFrom);

            LOG.info("getVisibilityForCatalog(jalo): product from/to = "+dFrom+"/"+dTo+", catalog from/to = "+dCatalogFrom+"/not Used. iCmp1 = "+iCmp1+", iCmp2 = "+iCmp2);
            if ( iCmp1 > 0 ) {
                iVisibility = 1; // product visibility starts AFTER catalog start date
            } else {
                if ( iCmp2 <= 0 ) {
                    iVisibility = -1; // product visibility ended BEFORE catalog start date
                }
            }
        }
        LOG.info("getVisibilityForCatalog(jalo) returning :"+iVisibility);
        return iVisibility;        
        */
    }    
    /* 
     * parameters:
     * p : either WeraVarianteModel or WeraProductSetModel (or subtype)
     * cvm : catalogversion as reference, will be ignored for live catalags.
     *       if "null", from/to dates for non-live catalogs will be set to earliest/latest dates possible.
     * bLive : if true, context is a live catalog like weramaster, where no date boundaries are set and the "now" date is referenced.
     * 
     * product is considered visible, if ...
     * valid_from(P) <= valid_from(C) && valid_to(P) > valid_from(C) for restricted catalogs.
     * valid_from(P) <= now <= valid_to(P) for live catalogs
     *
     * return value:
     * 0  : product visible within catalog's context
     * 1  : product invisible, might be visible in future or after the catalog's start date
     * -1 : product invisible, it's expired or it's visible before the catalog's start date
     */
    public byte getVisibilityForCatalog( final ProductModel p, final CatalogVersionModel cvm, final boolean bLive ) {
        // LOG.info("getVisibilityForCatalog called with "+p.getCode()+", "+cvm.getVersion() + ", " + bLive);
        Date dFrom, dTo, dCatalogFrom, dNow;
        byte iVisibility = 0; // default: product visible within catalog constraints

        if ( p instanceof WeraVarianteModel ) {
            WeraVarianteModel wvm = (WeraVarianteModel) p;
            dFrom = wvm.getValid_from() == null ? WeraManager.dEarliest : wvm.getValid_from();
            dTo = wvm.getValid_to() == null ? WeraManager.dLatest : wvm.getValid_to();
        } else {
            if ( p instanceof WeraProductSetModel ) {
                WeraProductSetModel wps = (WeraProductSetModel) p;
                dFrom = wps.getValid_from() == null ? WeraManager.dEarliest : wps.getValid_from();
                dTo = wps.getValid_to() == null ? WeraManager.dLatest : wps.getValid_to();
            } else {
                LOG.warn("getVisibilityForCatalog(): no valid product type for date check. assuming visible");
                return iVisibility;
            }
        } 
        if ( bLive ) {
            // for live catalogs ignore the catalog version parameter, only reference is "now".
            dNow = new Date();
            // compare start to now
            int iCmp1 = dFrom.compareTo(dNow);

            // compare end to now
            int iCmp2 = dTo.compareTo(dNow);

            // LOG.info("getVisibilityForLiveCatalog(): product from/to = "+dFrom+"/"+dTo+". iCmp1 = "+iCmp1+", iCmp2 = "+iCmp2);
            if ( iCmp1 > 0 ) {
                iVisibility = 1; // product not yet visible 
            } else {
                if ( iCmp2 < 0 ) {
                   iVisibility = -1; // product expired
                }
            }            
        } else {
            // date-restricted catalog version
            if ( cvm != null ) {
                dCatalogFrom = cvm.getValid_from() == null ? WeraManager.dEarliest : cvm.getValid_from();
                // dCatalogTo = cvm.getValid_to() == null ? WeraManager.dLatest : cvm.getValid_to();
            } else {
                dCatalogFrom = WeraManager.dEarliest;
                // dCatalogTo = WeraManager.dLatest;
            }
            // compare start dates
            int iCmp1 = dFrom.compareTo(dCatalogFrom);
            // compare end date with catalog start
            int iCmp2 = dTo.compareTo(dCatalogFrom);

            // LOG.info("getVisibilityForRestrictedCatalog(): product from/to = "+dFrom+"/"+dTo+", catalog from/to = "+dCatalogFrom+"/"+dCatalogTo+". iCmp1 = "+iCmp1+", iCmp2 = "+iCmp2);
            if ( iCmp1 > 0 ) {
                iVisibility = 1; // product visibility starts AFTER catalog start date
            } else {
                if ( iCmp2 <= 0 ) {
                    iVisibility = -1; // product visibility ended BEFORE catalog start date
                }
            }
        }
        // LOG.info("getVisibilityForCatalog returns "+iVisibility);
        return iVisibility;
    }

}

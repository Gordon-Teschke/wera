/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.computationaldesign.wera.jalo;

import de.hybris.platform.europe1.jalo.PriceRow;
import java.util.Collection;

import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.media.MediaManager;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.c2l.LocalizableItem;
import de.hybris.platform.catalog.jalo.CatalogManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.product.ProductManager;
import de.hybris.platform.core.PK;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Map;
import java.text.SimpleDateFormat;
import java.util.Set;

import org.jdom.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jdom.Comment;
import org.jdom.DocType;
import org.jdom.Document;
import org.jdom.input.SAXBuilder;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * @author teschke
 *
 * Modifications: Background="J" => Background="N"
 * PrintFileName="pictures/code_schwarz.eps" =>
 * PrintFileName="pictures/test_trans.eps" UnitSymbol="Code => UnitSymbol=" "
 *
 */
public class WeraProduktExportMinikatalog extends WeraProduktExportBlack {

	/**
	 * Used logger instance.
	 */
	private static final Log LOG					= LogFactory.getLog(WeraProduktExportMinikatalog.class);
	HashMap<String,String> m_hashBulletPointList	= new HashMap();
	HashMap<String,Textbaustein> m_hashTextbausteine	= new HashMap();

	WeraProduct m_product 					= null;
	String m_strPriceList 					= "";
	WeraPricelist m_wp 						= null;
	int m_iCountTipp						= 0;

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// CSV EXPORT (START)
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

	/**
	 * produktExportOrderCSV
	 * Ausgabe der Produktreihenfolge als CSV-Datei
	 *
	 * @param String strLanguage
	 * @param String strCatalog
	 * @param String strCatalogversion
	 * @param String strXmlPath
	 * @return
	 */
	public String produktExportOrderCSV( final String strCatalog, final String strCatalogversion, String strXmlPath, final String strPriceList ) {

		// --- setze ausgabedatum
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMdd_HHmmss");
        Date currentTime = new Date();
		String strTimestamp	= formatter.format(currentTime);
				
		// --- debug
		LOG.info("produktExportOrderCSV() called.");
		LOG.info("produktExportOrderCSV, strCatalog=" + strCatalog + ", strCatalogversion=" + strCatalogversion);
		LOG.info("produktExportOrderCSV, strPriceList=" + strPriceList);
		
		// --- preisdaten
		m_strPriceList = strPriceList;

		// --- maximale Anzahl Attributespalten
		setM_maxAttributeCols(6);
		setM_isPriceListExport( true );
		
		// --- Priceliste initialisieren
		m_wp = new WeraPricelist();

		// --- Initialize
		String strResultPath = "";
		int iPos = 1;
		Category oAktCategory = null;
		String strCategory = null;
		final Collection categories = null;
		final Collection colCategory2Export = new ArrayList();
		m_strCatalog = strCatalog;
		m_strCatalogversion = strCatalogversion;

		// --- daten temp. setzen (später aufräumen)
		String strLanguage	= "de";
		int anzahlProduteJePaket	= 10000;
		m_strLanguage = strLanguage;
		m_bTextwechsel = false;

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

		// --- Debug
		output("strLanguage       =" + strLanguage, 1);
		output("strCatalog        =" + strCatalog, 1);
		output("strCatalogversion =" + strCatalogversion, 1);

		try {

			// --- Erzeuge die Export-Ablage
			final String strOutputFile = CreateOutputPath(strFileDatum, strLanguage) + "/productorder_" + strCatalogversion + ".txt";
			// final FileWriter fw = new FileWriter(strOutputFile);
			FileOutputStream fileStream = new FileOutputStream(new File(strOutputFile));
			final OutputStreamWriter fw = new OutputStreamWriter(fileStream, StandardCharsets.UTF_8);

			// --- First off all we have to login
			if (bLogin()) {

				// --- Alle alle Produkte sortiert nach Sortkey Katalogversion zu Kategory (Sorttyp=3)
				final WeraKatalog wk = new WeraKatalog();
				String strOutput = "Hole sortierte Produktliste "/* + m_strExportLanguage + "() ..." */;
				LOG.info( strOutput );
				final String strCatalogVersionName		= strCatalogversion;
				final String strReferenzKatalogVersion	= "print_2018";
				final String strCatalogPrint			= strCatalog;
				final String strCatalogPriceliste		= strCatalog;
				Collection productsSorted = wk.getProductsFromPreisliste_v4(strCatalogVersionName, strReferenzKatalogVersion, strCatalogPrint, strCatalogPriceliste, 3 );
				ArrayList products = new ArrayList();
				products.addAll(productsSorted);

				// --- anzahl pakete
				ArrayList	productSublist = new ArrayList();
				int offset = 0;
				LOG.info("productsSorted.size()=" + productsSorted.size() );
				LOG.info("anzahlProduteJePaket=" + anzahlProduteJePaket );
				for ( offset=0; offset < productsSorted.size(); ) {

					int iBisIndex	= offset + anzahlProduteJePaket;
					if ( iBisIndex >= productsSorted.size() ) {
						iBisIndex = productsSorted.size();
					}
					String partInfo	= offset + "_" + iBisIndex + "_" + strTimestamp;

					// --- hole Datenpaket
					productSublist.clear();
					productSublist.addAll(products.subList(offset, iBisIndex));

					// --- Keine Unterkategorien, dann ausgeben
					LOG.info("productsSorted.size()=" + productSublist.size() );
					LOG.info("partInfo=" + partInfo );
					//_ProduktExportOrderCSV("root", strLanguage, strCatalog, strCatalogversion, strXmlPath, productSublist, partInfo );

					output("Anzahl Produkte=" + productsSorted.size(), 1);
					if (productsSorted.size() > 0) {
						// --- iterate on all products, sorted by category
						for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();) {

							// --- Hole Produkt
							HashMap hashmapValues = (HashMap) it1.next();
							WeraProduct oWeraProduct 	= (WeraProduct) hashmapValues.get("product");
							oAktCategory 		= (Category) hashmapValues.get("category");
							Integer order 				= (Integer) hashmapValues.get("sortkey0");

							// --- filter product ---------------------------------------------
							if (_isFilteredProduct(oWeraProduct)) {
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
							// --- filter product ---------------------------------------------


							// --- hier CSV bauen --------------------------------------------------------------------------
							if ( m_iCountProduct	== 0 ) {
								fw.write( "PK\tCode\tProduktname\tOrder\tKategorieCode\tKategoryName\r\n" );
							}
							String strLine = oWeraProduct.getPK().toString() + "\t"
												+ oWeraProduct.getCode() + "\t" + "\"\"" + oWeraProduct.getName()
												+ "\t" + order
												+ "\t" + oAktCategory.getCode() + "\t" + "\"\"" + oAktCategory.getName();
							/*
							System.out.println("############");
							System.out.println("oAktCategory.getCode()="+ oAktCategory.getCode() + ": " + oAktCategory.getName());
							System.out.println("oWeraProduct.getPK().toString()="+ oWeraProduct.getPK().toString() );
							System.out.println("oWeraProduct.getCode()="+ oWeraProduct.getCode() + ": " + oWeraProduct.getName() );
							System.out.println("Order="+ order );
							System.out.println("strLine="+ strLine );
							System.out.println("############");
							*/
							fw.write(strLine + "\r\n");
							m_iCountProduct++;
							// --- hier CSV bauen --------------------------------------------------------------------------


						} // --- for (final Iterator it1 = productsSorted.iterator(); it1.hasNext();)

					} // --- if ( products.size() ) {

					// --- neuer offset
					offset += anzahlProduteJePaket;
				}

			} // if (bLogin()) {

			//  --- Schliessen
			LOG.info("Export abgeschlossen. Anzahl Produkte=" + m_iCountProduct );
			fw.close();

		} catch (final Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return strResultPath;
	}

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
// CSV EXPORT (ENDE)
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	/**
	 * produktExportMinikatalog
	 * @param strLanguage
	 * @param strCatalog
	 * @param strCatalogversion
	 * @param strXmlPath
	 * @param bTextwechsel
	 * @return
	 */
	public String produktExportMinikatalog( String strLanguage, final String strCatalog,
			final String strCatalogversion, String strXmlPath, final boolean bTextwechsel, final String strPriceList, final int anzahlProduteJePaket ) {

		// --- setze ausgabedatum
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyyMMdd_HHmmss");
        Date currentTime = new Date();
		String strTimestamp	= formatter.format(currentTime);

		// --- debug
		LOG.info("produktExportMinikatalog() called.");
		LOG.info("produktExportMinikatalog, strCatalog=" + strCatalog + ", strCatalogversion=" + strCatalogversion);
		LOG.info("produktExportMinikatalog, strPriceList=" + strPriceList);

		// --- preisdaten
		m_strPriceList = strPriceList;

		// --- TExtbausteine einlesen
		LOG.info( "Textbausteine einlesen ... "  );
		final ComposedType TextbausteineType = TypeManager.getInstance().getComposedType(Textbaustein.class);
		final Collection<Textbaustein> Textbausteine = (Collection<Textbaustein>)TextbausteineType.getAllInstances();
		for ( Textbaustein textbaustein : Textbausteine) {
			m_hashTextbausteine.put( (String)textbaustein.getCode(), textbaustein);
		}

		// --- maximale Anzahl Attributespalten
		setM_maxAttributeCols(6);
		setM_isPriceListExport( true );

		// --- Priceliste initialisieren
		m_wp = new WeraPricelist();

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

			// --- Erzeuge die Export-Ablage
			strXmlPath = CreateOutputPath(strFileDatum, strLanguage);

			// --- Alle alle Produkte sortiert nach Produktnummer
			final WeraKatalog wk = new WeraKatalog();
			String strOutput = "Hole sortierte Produktliste "/* + m_strExportLanguage + "() ..." */;
			LOG.info( strOutput );
			String strResult = "";
			strResult += strOutput + "<br>";
			final String strCatalogVersionName		= strCatalogversion;
			final String strReferenzKatalogVersion	= "print_2018";
			final String strCatalogPrint			= strCatalog;
			final String strCatalogPriceliste		= strCatalog;
			Collection productsSorted = wk.getProductsFromPreisliste_v3(strCatalogVersionName, strReferenzKatalogVersion, strCatalogPrint, strCatalogPriceliste, 2);
			ArrayList products = new ArrayList();
			products.addAll(productsSorted);

			// --- anzahl pakete
			ArrayList	productSublist = new ArrayList();
			int offset = 0;
			LOG.info("productsSorted.size()=" + productsSorted.size() );
			LOG.info("anzahlProduteJePaket=" + anzahlProduteJePaket );
			for ( offset=0; offset < productsSorted.size(); ) {

				int iBisIndex	= offset + anzahlProduteJePaket;
				if ( iBisIndex >= productsSorted.size() ) {
					iBisIndex = productsSorted.size();
				}
				String partInfo	= offset + "_" + iBisIndex + "_" + strTimestamp;

				// --- hole Datenpaket
				productSublist.clear();
				productSublist.addAll(products.subList(offset, iBisIndex));

				// --- Keine Unterkategorien, dann ausgeben
				LOG.info("productsSorted.size()=" + productSublist.size() );
				LOG.info("partInfo=" + partInfo );
				_ProduktExport("root", strLanguage, strCatalog, strCatalogversion, strXmlPath, productSublist, partInfo );

				// --- neuer offset
				offset += anzahlProduteJePaket;
			}
			if ( offset < productsSorted.size() ) {
				LOG.info("REST offset" + offset + ", bis iBisIndex=" + productsSorted.size() );

				// --- Keine Unterkategorien, dann ausgeben
				String partInfo	= offset + "_" + productsSorted.size() + "_" + strTimestamp;

				// --- hole Datenpaket
				productSublist.clear();
				productSublist.addAll(products.subList(offset, productsSorted.size()) );

				LOG.info("R: productsSorted.size()=" + productSublist.size() );
				LOG.info("R: partInfo=" + partInfo );
				_ProduktExport("root", strLanguage, strCatalog, strCatalogversion, strXmlPath, productSublist, partInfo );
			}

			// --- ben�tigte Produktbilder
			try {
				// --- �ffnen der LOG-Datei
				FileWriter m_oFileWriterLog = new FileWriter(strXmlPath + "/kompaktkatalog_imagelist.txt");
				if (m_oFileWriterLog != null) {

					// ---- Schleife �ber alle Zeilen
					Iterator it = m_hashProdukbilder.entrySet().iterator();
					while (it.hasNext()) {
						   Map.Entry pair = (Map.Entry)it.next();
						   m_oFileWriterLog.write( (String)pair.getKey() + (String)pair.getValue() + "\r\n" );
						   it.remove(); // avoids a ConcurrentModificationException
					}

					// --- Schliesen
					m_oFileWriterLog.close();

					// --- Log l�schen
					m_hashProdukbilder.clear();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// --- ben�tigte FarbCodess
			try {
				// --- �ffnen der LOG-Datei
				FileWriter m_oFileWriterLog = new FileWriter(strXmlPath + "/kompaktkatalog_farben.txt");
				if (m_oFileWriterLog != null) {

					// ---- Schleife �ber alle Zeilen
					Iterator it = m_hashAttributeFarbenList.entrySet().iterator();
					while (it.hasNext()) {
						   Map.Entry pair = (Map.Entry)it.next();
						   m_oFileWriterLog.write( (String)pair.getKey() + (String)pair.getValue() + "\r\n" );
						   it.remove(); // avoids a ConcurrentModificationException
					}

					// --- Schliesen
					m_oFileWriterLog.close();

					// --- Log l�schen
					m_hashAttributeFarbenList.clear();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// --- tempor�r, kann sp�ter entfallen, liste der bullet-points
			try {
				// --- �ffnen der LOG-Datei
				FileWriter m_oFileWriterLog = new FileWriter(strXmlPath + "/kompaktkatalog_bulletpoints.txt");
				if (m_oFileWriterLog != null) {

					// --- header schreiben
					m_oFileWriterLog.write( "UPDATE WeraProduct;pk[unique=true];bulletpoints_print_selection;\r\n" );

					// ---- Schleife �ber alle Zeilen
					Iterator it = m_hashBulletPointList.entrySet().iterator();
					while (it.hasNext()) {
						   Map.Entry pair = (Map.Entry)it.next();
						   m_oFileWriterLog.write( (String)pair.getKey() + (String)pair.getValue() + "\r\n" );
						   it.remove(); // avoids a ConcurrentModificationException
					}

					// --- Schliesen
					m_oFileWriterLog.close();

					// --- Log l�schen
					m_hashBulletPointList.clear();
				}

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}


		} // if (bLogin()) {

		return strResultPath;
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
	public void _ProduktExport( final String strAktCategory, final String strLanguage, final String strCatalog,
			final String strCatalogversion, String strXmlPath, Collection productsSorted, final String strXMLPart ) {


		LOG.info("_ProduktExport() called.");
		// --- Initialize
		LOG.info("_ProduktExport, strCatalog=" + strCatalog + ", strCatalogversion=" + strCatalogversion);
		String strXmlFile = "";
		m_strLanguage = strLanguage;
		m_colCategories.add(strAktCategory);

		// --- preset Tip-liste
		m_colTippByProducts	=	new ArrayList();

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

			// --- <Tip-List>
			final Element oTipList = new Element("Tip-List");
			root.addContent(oTipList);

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
					initCategory(oTreegroupLink, oAktCategory, oTreegroupList, oProductList, oTipList, strCatalogversion, productsSorted );



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
					strXmlFile = strXMLPart + '_' + strLanguage + "_" + strXmlFilePrefix + strAktCategory + "_products.xml";

					// --- Erzeuge die Export-Ablage, falls nicht vorhanden
					strXmlPath = strXmlPath + m_oExportFormatter.formatXmlFilePrefix("/" + strXmlFilePrefix + strAktCategory);
					LOG.info("mkdir()= " + m_wm.createDirectory(strXmlPath));
				} else {
					LOG.info("+++++ERROR + Category not FOUND." + strAktCategory);

				}
			} else {
					LOG.info("+++++m_colCategories.contains(strAktCategory: ERROR + Category not FOUND." + strAktCategory);

			} // if (m_colCategories.contains(strAktCategory)) {

			// --- ausgeben der Tips, falls vorhanden ---------------------------------------------------------------------


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

			// --- ausgabedatei
			if ( strXmlFile == "" ) {
				strXmlFile	= "";
			}

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
	 * @param oTreegroupLink
	 * @param category
	 * @param oTreegroupList
	 * @param oProductList
	 * @param strCatalogversion
	 * @throws JaloInvalidParameterException
	 * @throws JaloSecurityException
	 */
	protected void initCategory(final Element oTreegroupLink, final Category category, final Element oTreegroupList,
			Element oProductList, Element oTipList, final String strCatalogversion, Collection productsSorted ) throws /*JaloInvalidParameterException, */ JaloSecurityException {
		
		// --- globale kategorie
		m_oCategoryWera 		= category;

		// --- keine hyperlinks exportieren
		this.m_exportHyperLinks	= false;

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

		
		
			//    			 TODO entfernen
			//m_wm.m_bCheckForActivation = false;
			//    			 TODO
			// --- Hole alle aktiven Produkte der aktuellen Kategorie
			final boolean bCheckForActivationOld = m_wm.m_bCheckForActivation;
			SetCheckForActivation(false);
			// final Collection productsSorted = m_wm.getProductsOrderedByCategory(category);
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
					HashMap hashmapValues = (HashMap) it1.next();
					WeraProduct oWeraProduct = (WeraProduct) hashmapValues.get("product");
					m_product = oWeraProduct;

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
					

					// --- Hier geht es los
					LOG.info("oWeraProduct=" + oWeraProduct);
					// --- Eindeutige ProduktID
					//m_strProduktID = "P" + iOffsetProduktID;
					m_strProduktID = "P" + m_iProduktID++;
					//iOffsetProduktID++;

					// --- muss vor dem Produkt ein Tip sein, muss das wirklich sein :) ---------------------------------------------------
					if ( _checkExportTip(oWeraProduct) ) {
						
						// --- darstellen der TipLinks
						String strTipId	= __generateTipLink ( "producttippref", oTreegroupLink, "tipref" );
						
						// --- erstellen der Tips
						__generateTippsElements ( oWeraProduct, "producttippref", oTipList, strTipId, strCatalogversion );
					}
					// --- muss vor dem Produkt ein Tip sein, muss das wirklich sein :) ---------------------------------------------------

					
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

			// --- debug
			LOG.info("EOL=" + " - Anzahl Produkte=" + productsSorted.size());
	}

	/**
	 * pr�fen ob vor diesem Produkt ein Tip ausgegben werden muss
	 * @param oWeraProdukt
	 * @return 
	 */
	protected boolean _checkExportTip( WeraProduct oWeraProduct ) {
		
		// --- initialize
		boolean tipAvailable	= false;
		
		// --- pr�fe greattools
		String[] greatToolsHeadlines = null;
		if ( (greatToolsHeadlines=oWeraProduct.getActiveGreattoolString()) != null && greatToolsHeadlines.length > 0 ) {
		
			// --- Debug
			LOG.info ("ExportMinikatalog._checkExportTip => greatToolsHeadline="+ greatToolsHeadlines);
			tipAvailable	= true;
		}
	
		// --- pr�fe marketing-texte
		Item	item = null;
		if ( (item=oWeraProduct.getExportmarktingtext()) != null ) {
			LOG.info ("ExportMinikatalog._checkExportTip => getExportmarktingtext="+ ((Product)item).getCode());
			tipAvailable	= true;
		}
		
		// --- pr�fe bulletpoints
		String bulletpoints_print_selection			= (String)m_wm.getAttribute(oWeraProduct, "bulletpoints_print_selection");
		if ( bulletpoints_print_selection != null && bulletpoints_print_selection.trim() != "" ) {
			tipAvailable	= true;
		}
		
 	
		return tipAvailable;
	}

	/**
	 * Erstellen des Tipps
	 * 
	 * @param WeraProduct oWeraProduct
	 * @param String strTippTyp
	 * @param Element oContainterElement
	 * @param String strTippRef
	 * @param String strCatalogversion
	 * @return Element
	 */
	protected Element __generateTippsElements ( WeraProduct oWeraProduct, String strTippTyp, Element oContainterElement, String strTippRef, String strCatalogversion ) {

		// --- initialize
		Element oTipMotive					= null;
		Element oTipText					= null;
		String normalizedArtikelNr			= oWeraProduct.normalizeFilenameForImageLookup();
		String greatToolsHeadline			= "";
		Collection<String>	colDefaulFiles	= new ArrayList();
		colDefaulFiles.add("default");
		Collection<String>	colLanguages	= new ArrayList();
		colLanguages.add(m_strLanguage);
		colLanguages.addAll(m_coAdditionallLanguages);
		
		// --- F�lle ProduktListe ( neues Produkt => Categorien )
		Element tipXML = new Element("Tip");
		oContainterElement.addContent(tipXML);

		// --- tip elemen f�llen
		tipXML.setAttribute("Id", strTippRef );
		tipXML.setAttribute("TipCode", strTippRef );
		tipXML.setAttribute("TipTypeName", strTippTyp );

		// --- TipText-liste
		Element oTipTextList = new Element("TipText-List");
		tipXML.addContent(oTipTextList);

		// --- preset
		int iIndex	= 0;

		
		// --- pr�fe greattools -------------------------------------------------------------------------------------------------------------
		String[] greatToolsHeadlines = null;
		if ( (greatToolsHeadlines=oWeraProduct.getActiveGreattoolString()) != null && greatToolsHeadlines.length > 0 ) {
		
			// --- motive-liste
			Element oTipMotiveList = new Element("TipMotive-List");
			tipXML.addContent(oTipMotiveList);
		
			// --- PrintFileName-node erstellen
			for ( int iIndexGT=0; iIndexGT < greatToolsHeadlines.length; iIndexGT++ ) {

				// --- hole headline
				greatToolsHeadline	= (String)greatToolsHeadlines[iIndexGT];
				
				// --- default motive
				oTipMotive	= __generateTipMotive ( "M0_", greatToolsHeadline, "GREATTOOL_IMAGE_DEFAULT", colDefaulFiles, iIndexGT );
				oTipMotiveList.addContent(oTipMotive);

				// --- motive-liste verschiedene sprachen
				oTipMotive	= __generateTipMotive ( "M1_", greatToolsHeadline, "GREATTOOL_IMAGE_LOCALIZED", colLanguages, iIndexGT );
				oTipMotiveList.addContent(oTipMotive);
				
			} // --- for ( String greatToolsHeadline  : greatToolsHeadlines ) {

		} else {
			
			// --- Headline ausgeben immer wenn Greattools nicht ausgegeben werden ----------------------------------------------------------
			// --- preset
			oTipText	= null;
			
			// --- headline, nur wenn kein greattool
			Boolean output_category_titel			= (Boolean)m_wm.getAttribute(oWeraProduct, "output_category_titel");
			if ( output_category_titel != null && output_category_titel.booleanValue() ) { 
				
				// --- verwendet kategorietitel

				// --- hole die Kategorie in der aktuellen katalogversion
				Category category	= null;
				Collection categoriesAktuelleCatalogversion	= m_wm.getCategoriesByProductAndCatalogVersion(oWeraProduct, strCatalogversion );
				System.out.println(strCatalogversion + "=" + categoriesAktuelleCatalogversion);
				if ( categoriesAktuelleCatalogversion != null && categoriesAktuelleCatalogversion.size() > 0 ) {
					category = (Category) categoriesAktuelleCatalogversion.iterator().next();

					// --- hole die kategorien aus weramaster, es können dort mehrere sein!
					Collection<Category> categoriesWeraMaster	= (Collection<Category>)m_wm.getCategoriesByProductAndCatalogVersion(oWeraProduct, "weramaster" );
					for ( Category categoryWeraMaster : categoriesWeraMaster ) {
						if ( categoryWeraMaster.getCode().equals(category.getCode())) {
							category	= categoryWeraMaster;
							break;
						}
					}

					// --- haben wir die kategory gefunden
					if ( category != null ) {

						oTipText = __generateTipText("I" + m_strProduktID, normalizedArtikelNr, "headline", colLanguages, iIndex++, category, "name");
					}
				}
			}
			
			// --- immer als produkttitel erzeugen, falls Kategorie nicht funktioniert hat!
			if ( oTipText == null ) {
				
				// --- verwendet produkttitel
				oTipText	= __generateTipText ( "I" + m_strProduktID, normalizedArtikelNr, "headline", colLanguages, iIndex++, oWeraProduct, "name" );
			}
		
			// --- collect Tip-headline
			if ( oTipText != null ) {
				oTipTextList.addContent(oTipText);
			}			
		}
		
		
		// --- TipText-List -----------------------------------------------------------------------------------------------------------------

		
		// --- pr�fe marketingtools ---------------------------------------------------------------------------------------------------------
		Item	item = oWeraProduct.getExportmarktingtext();
		System.out.println("################################oWeraProduct.getCode() >" + m_wm.getAttribute(oWeraProduct, "code") + "<" );
		System.out.println("################################oWeraProduct.getClass() >" + oWeraProduct.getClass().getName() + "<" );
		if ( item != null ) {

			String attrName = "";
			if ( item instanceof WeraVariante ) {
				attrName = "weravariante2marketing";
			} else {
				attrName = "weraproduct2marketing";
			}

			// --- marketingtext
			oTipText	= __generateTipText ( "I" + m_strProduktID, normalizedArtikelNr, "marketingtext", colLanguages, iIndex++, item, attrName /*"shop_description" */ );
			if ( oTipText != null ) {
				oTipTextList.addContent(oTipText);
			}
			
		} // if ( (item=oWeraProduct.getExportmarktingtext()) != null ) {
		// --- pr�fe marketingtools ---------------------------------------------------------------------------------------------------------

/*		
		// --- tempor�r, bulletpoints von variante - liste erstellen ------------------------------------------------------------------------
		// --- preset
		Collection<Textbaustein>oBulletsTextbausteineTmp	= null;

		// --- ist eine Variante f�r den Export makriert
		if ( (item=oWeraProduct.getExportbulletpoints()) != null ) {
			
			if ( (oWeraProduct instanceof WeraProductSet || oWeraProduct instanceof WeraProductSetinSet) ) {

				// --- hole bulletpoints von Satz
				oBulletsTextbausteineTmp	= (Collection)m_wm.getAttribute(item, "weraproductset2bulletpoints");

			} else {

				// --- hole bulletpoints von variante
				oBulletsTextbausteineTmp	= (Collection)m_wm.getAttribute(item, "weravariante2bulletpoints");
			}

 		} // if ( (item=oWeraProduct.getExportbulletpoints()) != null ) {
		
		// --- sind bullet-points vorhanden
			
		// --- preset
		String exportBulletPointList	= "";
		if ( oBulletsTextbausteineTmp != null && oBulletsTextbausteineTmp.size() > 0 ) {

			if ( oBulletsTextbausteineTmp != null && oBulletsTextbausteineTmp.size() > 0 ) {

				for ( Textbaustein bulletpoint : oBulletsTextbausteineTmp ) {

					// --- Bulletpoint merken ---------------------------------------------------------------------------
					// --- tempor�r, sp�ter l�schen bulletpoints merken
					exportBulletPointList	+= bulletpoint.getCode() + "#";
					// --- Bulletpoint merken ---------------------------------------------------------------------------
				}
			}

			// --- tempor�r, sp�ter l�schen bulletpoints merken
			if ( !exportBulletPointList.equals("") ) {
				m_hashBulletPointList.put(";" + oWeraProduct.getPK().toString() + ";", exportBulletPointList);
			}
			
		}
		// --- tempor�r, bulletpoints von variante - liste erstellen ------------------------------------------------------------------------
*/
		
		// --- pr�fe bullets ---------------------------------------------------------------------------------------------------------#
		// --- hole komma getrennte Liste deer bulletpoint-Textbausteine
		String bulletpoints_print_selection			= (String)m_wm.getAttribute(oWeraProduct, "bulletpoints_print_selection");
		if ( bulletpoints_print_selection == null ) { bulletpoints_print_selection = ""; }
		String[]	bulletpoints_print_selections	= bulletpoints_print_selection.split("#");
		String exportBulletPointList	= "";
 
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

					// --- tip aufbauen
					oTipText	= __generateTipText ( "I" + m_strProduktID, normalizedArtikelNr, "bullet", colLanguages, iIndex++, bulletpoint, "text" );
					if ( oTipText != null ) {
						
						oTipTextList.addContent(oTipText);

						// --- Bulletpoint merken ---------------------------------------------------------------------------
						// --- tempor�r, sp�ter l�schen bulletpoints merken
						exportBulletPointList	+= bulletpoint.getCode() + "#";
						// --- Bulletpoint merken ---------------------------------------------------------------------------
					}

				}
				
			} // for ( String bulletpointId : bulletpoints_print_selections ) {
		}	
		// --- tempor�r, sp�ter l�schen bulletpoints merken
		if ( !exportBulletPointList.equals("") ) {
			m_hashBulletPointList.put(";" + oWeraProduct.getPK().toString() + ";", exportBulletPointList);
		}
 
		// --- pr�fe bullets ---------------------------------------------------------------------------------------------------------
		
	

		return tipXML;
	}

	/**
	 * motive-liste erstellen verschiedene sprachen
	 */
	private Element __generateTipMotive ( String strPrefixMotivId, String strMotiveCode, String strMotiveTypeName, Collection<String> colLanguages, int iIndex ) {
		
		Element oTipMotive		= new Element("TipMotive");
		oTipMotive.setAttribute("Id", strPrefixMotivId + strMotiveCode );
		oTipMotive.setAttribute("MotiveCode", strMotiveCode );
		oTipMotive.setAttribute("MotiveTypeName", strMotiveTypeName );
		oTipMotive.setAttribute("MotiveWidthP", "0.0" );
		oTipMotive.setAttribute("MotiveIndex", String.valueOf(iIndex) );
		
		// --- filelist-container
		Element oTipFileList	= new Element("TipFile-List");
		oTipMotive.addContent(oTipFileList);
		
		// --- PrintFileName-node erstellen
		for ( String language  : colLanguages ) {
			
			Element oTipFile	= new Element("File");
			oTipFileList.addContent(oTipFile);
			oTipFile.setAttribute("Language", language );
			oTipFile.setAttribute("PrintFileName", "pictures/great-tools-feature-overview-" + language + "-" + strMotiveCode + ".tif" );
		}
		
		return oTipMotive;
	}
	
	/**
	 * text-liste erstellen verschiedene sprachen
	 */
	private Element __generateTipText ( String strPrefixMotivId, String strTextCode, String strTextTypeName, Collection<String> colLanguages, int iIndex, Item item, String strAttribute ) {

		// --- preset
		boolean textsFound	= false;
		
		// --- get current language
		Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();
			
		
		Element oTextbausteinText		= new Element("TextbausteinText");
		oTextbausteinText.setAttribute("Id", strPrefixMotivId + String.valueOf(iIndex) );
		oTextbausteinText.setAttribute("TextCode", strTextCode );
		oTextbausteinText.setAttribute("TextTypeName", strTextTypeName );
		
		// --- TextbausteinTextBlock liste erstellen
		for ( String language  : colLanguages ) {
			
			// -- set language
			m_wm.SetLanguage(language);
			
			// --- get text from attribute
			Object value	= (Object)m_wm.getAttribute(item, strAttribute);
			String strValue	= "";
			if ( value instanceof Collection ) {

				Collection<Textbaustein> weravariante2marketingTextbausteine	= (Collection<Textbaustein>)m_wm.getAttribute(item, strAttribute);
				for ( Textbaustein marketingTextbaustein : weravariante2marketingTextbausteine ) {
					String strText = (String)m_wm.getAttribute(marketingTextbaustein, "text");
					if (strText == null ) { strText = ""; }

					if ( strValue != "" ) {

						strValue += "\r\n" + strText;
					} else  {

						strValue += strText;
					}
				} // for ( Textbaustein marketingTextbaustein : weravariante2marketingTextbausteine ) {
			}
			else {

				strValue	= (String)value;
			}


			if ( strValue != null && strValue.trim() != "" ) { 

				// --- flag setzen
				textsFound						= true;
				
				// --- text-element ausgeben
				Element oTextbausteinTextBlock	= new Element("TextbausteinTextBlock");
				oTextbausteinTextBlock.setAttribute("Language", language );
				oTextbausteinText.addContent(oTextbausteinTextBlock);
				oTextbausteinTextBlock.setText(strValue);
			}
		}

		// --- reset current language
		m_wm.SetLanguage( currentSessionLanguage.getIsoCode() );
		
		if ( textsFound )
			
			// --- textbausteine zur�ckgeben
			return oTextbausteinText;
		else {
			
			// --- keine Daten gefunden
			return null;
		}
	}
	
	/**
	 * generates a list of tip-link elemens
	 * 
	 * @param strTippTyp
	 * @param oContainterElement
	 * @param strTippRef 
	 * @param iTipID 
	 * @return strTipId
	 */
	protected String __generateTipLink(String strTippTyp, Element oContainterElement, String strTippRef ) {
		
		// --- initialize
		String strTipId	= "P" + m_iCountProduct + "_I" + m_iCountTipp;
		
		// --- <TipLink LinkedRefID="T0" LinkedRefCode="anderwender_code" Sequence="00000" LinkedTypeName="haupttipphref" />
			
		// --- generate tip-node
		Element oTipLink = new Element("TipLink");
		oContainterElement.addContent(oTipLink);

		// --- set tip-link attributes
		oTipLink.setAttribute("LinkedRefID", strTipId );
		String strTippCode	= strTippRef;
		oTipLink.setAttribute("LinkedRefCode", strTippCode );
		oTipLink.setAttribute("Sequence", String.format("%6s", m_iCountTipp ).replace(' ', '0') );
		oTipLink.setAttribute("LinkedTypeName", strTippTyp );

		// --- tipId erheohen
		m_iCountTipp++;
		
		return strTipId;
	}

	/**
	 * Export Preisdaten
	 * 
	 * @param Element oAttributeList
	 * @param WeraVariante article
	 * @param int iOrder
	 * @return int
	 */
	protected int initMerkmalPriceData (final Element oAttributeList, Product article, int iOrderParam) {
		//LOG.info("#####Minikatalog=" );
		
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
		String strSymbol		= "";
		String strIsoCode		= "";
		String strID			= "";
		String strBez			= "";
		String strType			= "";
		String strWert			= "";
		String strOrder			= "";
		String strUnit			= "";
		String strPriceValue	= "";

/*
		// --- Hole nur die Preise die Exportiert werden sollen
		ArrayList prices = new ArrayList();
		prices = (ArrayList) m_wp._aGetPriceList(article, m_strPriceList);
		//LOG.info("##### Minikatalog.anzahl preise=" + prices.size() );
		
		///////////////////////////////////////////////////////////////////////////////////////////
		// Anlegen von Dummy-Prices Spalten
		///////////////////////////////////////////////////////////////////////////////////////////
		// --- Hole die PreislistenInfos
		if ( m_strPriceList.toLowerCase().contains("brutto") || m_strPriceList.toLowerCase().contains("minikatalog") || m_strPriceList.toLowerCase().contains("kompaktkatalog") ) {
		
			// --- mi
			iDummyPrices = 5;
		
		} else {

			 // --- Anzahl der Preise
			iDummyPrices = 4 - prices.size();
		}
		for ( iPricePos = 0; iPricePos < iDummyPrices; iPricePos++, iOrderParam++ ) {

			// --- Preis n
			strID		= "PR" + (char) (49 + iPricePos);
			strBez		= "PRREIS " + (char) (49 + iPricePos);
			strType		= "PREIS";
			strWert		= "";
			strOrder	= "" + (char) (49 + iOrderParam);
			strUnit		= "";
			Element oAttributeValue		= _initMerkmalAttributeValue(strID, strBez, strType, strWert, strOrder);
			oAttributeValue.setAttribute("Unit", strUnit);
			oAttributeValue.setAttribute("Symbol", strSymbol);
			oAttributeValue.setAttribute("IsoCode", strIsoCode);
			Element oAttributeValueList = _initMerkmalAttributeValueList(oAttributeValue);
			Element oAttribute			= _initMerkmalAttribute(oAttributeValueList, strID, strBez, strOrder, "PREIS" );

			// --- Der Attributeliste zuordnen
			oAttributeList.addContent(oAttribute);
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
								strBruttoPrice = m_wp._strFormatPrice(dBruttoPrice);
							}

							//  --- Unit
							if (pricerow.getMinQuantity() != 0) {
								strUnit = new Long(pricerow.getMinQuantity()).toString();
							}
						} else {
							strBruttoPrice	= "";
							strUnit			= "";
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
								strPriceValue = m_wp._strFormatPrice(dPrice);
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
							
									// --- Preis n
									strID		= "PR" + (char) (48 + iPricePos);
									strBez		= "PRREIS " + (char) (49 + iPricePos);
									strType		= "PREIS";
									strWert		= "";
									strOrder		= "" + (char) (49 + iOrderParam);
									Element oAttributeValue		= _initMerkmalAttributeValue(strID, strBez, strType, strWert, strOrder);
									oAttributeValue.setAttribute("Unit", strUnit);
									oAttributeValue.setAttribute("Symbol", strSymbol);
									oAttributeValue.setAttribute("IsoCode", strIsoCode);
									Element oAttributeValueList = _initMerkmalAttributeValueList(oAttributeValue);
									Element oAttribute			= _initMerkmalAttribute(oAttributeValueList, strID, strBez, strOrder, "PREIS" );

									// --- Der Attributeliste zuordnen
									oAttributeList.addContent(oAttribute);
									iOrderParam++;
								}
							}
							iPricePos = iPricePosTmp / 10;
						}

						// --- Preis n
						strID		= "PR" + (char) (48 + iPricePos);
						strBez		= "PRREIS " + (char) (49 + iPricePos);
						strType		= "PREIS";
						strWert		= strPriceValue;
						strOrder		= "" + (char) (49 + iOrderParam);
						Element oAttributeValue		= _initMerkmalAttributeValue(strID, strBez, strType, strWert, strOrder);
						oAttributeValue.setAttribute("Unit", strUnit);
						oAttributeValue.setAttribute("Symbol", strSymbol);
						oAttributeValue.setAttribute("IsoCode", strIsoCode);
						Element oAttributeValueList = _initMerkmalAttributeValueList(oAttributeValue);
						Element oAttribute			= _initMerkmalAttribute(oAttributeValueList, strID, strBez, strOrder, "PREIS" );

						// --- Der Attributeliste zuordnen
						oAttributeList.addContent(oAttribute);
						iOrderParam++;
					}
				}

			} // if (prices != null && prices.size() > 0) {

			// --- Auff�llen  von Dummy-Prices Spalten
			for (; iPricePos < iAnzahlSollNettoPrices; iPricePos++) {

				// --- Preis n
				strID		= "PR" + (char) (49 + iPricePos);
				strBez		= "PRREIS " + (char) (49 + iPricePos);
				strType		= "PREIS";
				strWert		= " ";
				strOrder	= "" + (char) (49 + iOrderParam);
				Element oAttributeValue		= _initMerkmalAttributeValue(strID, strBez, strType, strWert, strOrder);
				oAttributeValue.setAttribute("Unit", strUnit);
				oAttributeValue.setAttribute("Symbol", strSymbol);
				oAttributeValue.setAttribute("IsoCode", strIsoCode);
				Element oAttributeValueList = _initMerkmalAttributeValueList(oAttributeValue);
				Element oAttribute			= _initMerkmalAttribute(oAttributeValueList, strID, strBez, strOrder, "PREISDATEN" );

				// --- Der Attributeliste zuordnen
				oAttributeList.addContent(oAttribute);
				
				
			}
*/
			// --- Preis (Brutto) - PRBRUTTO
			Integer contentQuantity	= new Integer(0);
			if ( article instanceof WeraVariante ) {
				contentQuantity		= (Integer)m_wm.getAttribute(article, "contentQuantity");
			}
			if ( contentQuantity == null ) {
				contentQuantity	= new Integer(0);
			}
			strID		= "PRBRUTTO_QUANTITY";
			strBez		= "PRBRUTTO";
			strWert		= contentQuantity.toString();
			strOrder	= "" + (char) (49 + iOrderParam);
			Element oAttributeValue		= _initMerkmalAttributeValue(strID, strBez, "TECHNISCHE_MERKMALE", strWert, strOrder);
			oAttributeValue.setAttribute("Unit", contentQuantity.toString() );
			oAttributeValue.setAttribute("Symbol", strSymbol);
			oAttributeValue.setAttribute("IsoCode", strIsoCode);
			Element oAttributeValueList = _initMerkmalAttributeValueList(oAttributeValue);
			Element oAttribute			= _initMerkmalAttribute(oAttributeValueList, strID, strBez, strOrder, "TECHNISCHE_MERKMALE" );

			// --- Media-Infos
			final Element oMotive = _initMerkmalIcon(null, null, null, "ICON_PRBRUTTO_QUANTITY_0", "merkmale", strOrder);
			oAttribute.addContent(oMotive);

			// --- Der Attributeliste zuordnen
			oAttributeList.addContent(oAttribute);
/*
			strID						= "PRBRUTTO";
			strWert						= strBruttoPrice;
			strOrder					= "" + (char) (49 + iOrderParam);
			oAttributeValue				= _initMerkmalAttributeValue(strID, strBez, strType, strWert, strOrder);
			oAttributeValue.setAttribute("Unit", "");
			oAttributeValue.setAttribute("Symbol", strSymbol);
			oAttributeValue.setAttribute("IsoCode", strIsoCode);
			oAttributeValueList			= _initMerkmalAttributeValueList(oAttributeValue);
			oAttribute					= _initMerkmalAttribute(oAttributeValueList, strID, strBez, strOrder, "PREISDATEN_BRUTTO" );

			// --- Der Attributeliste zuordnen
			oAttributeList.addContent(oAttribute);
*/


		return iOrderParam;
	}
	
	/**
	 * initialisiere Preisdatens
	 * Zur Zeit nur die Bruttopreise
	 * 
	 * @param article
	 * @return 
	 */
	protected HashMap getPriceData ( Product article ) {
		
		// --- preset
		HashMap	preisData		= new HashMap();
		Double dBruttoPrice 	= null;
		String strBruttoPrice 	= "";
		String strUnit 	= "";
		String strSymbol		= "";
		String strIsoCode		= "";
		Boolean bIsEVK			= null;
		Boolean bIsEVKDef		= null;
		Boolean bAufAnfrage		= null;

		// --- Hole nur die Preise die Exportiert werden sollen
		ArrayList prices = new ArrayList();
		prices = (ArrayList) m_wp._aGetPriceList(article, m_strPriceList);
		
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
								strBruttoPrice = m_wp._strFormatPrice(dBruttoPrice);
							}

							//  --- Unit
							if (pricerow.getMinQuantity() != 0) {
								strUnit = new Long(pricerow.getMinQuantity()).toString();
							}
						} else {
							strBruttoPrice	= "";
							strUnit			= "";
						}
						
						preisData.put("PRBRUTTO_QUANTITY",	strUnit);
						preisData.put("PRBRUTTO",			strBruttoPrice);
						
						break;
					}					
					
				} // for (final Iterator it3 = prices.iterator(); it3.hasNext();) {
				
		} // if (prices != null && prices.size() > 0) {

		
		// --- daten auff�llen
		if ( !preisData.containsKey("PRBRUTTO_QUANTITY") ) {
			preisData.put("PRBRUTTO_QUANTITY", "" );
		}
		if ( !preisData.containsKey("PRBRUTTO") ) {
			preisData.put("PRBRUTTO","" );
		}
		
		return preisData;
	}


	// --- Inhalt der S�tze und / Satz in Satz
	protected Collection createContentElement(final WeraProduct weraProductSet, final Element oTexList) {

		// --- preset
		final HashMap hashmapAllFootnotes 	= new HashMap();
		final Collection colFootnodes 		= new ArrayList();

// --- Pr�fen auf Satz in Satz
		if (weraProductSet instanceof WeraProductSetinSet) {

			// --- Inhalt von Satz in Satz
			System.out.println("call _createContentElementSetinSet");
			if ( m_strCurrentTemplateName.equals("PRODUCTSETINSET_NO_CONTENT") ) {

				// spezial Template (Fremdproduke, Satz in Satz)
				_createContentElementSetinSet_noContent ( (WeraProductSetinSet) weraProductSet, oTexList);

			} else  {

				this._createContentElementSetinSet((WeraProductSetinSet) weraProductSet, oTexList, hashmapAllFootnotes, colFootnodes );
				//this._org_createContentElementSetinSet((WeraProductSetinSet) weraProductSet, oTexList );
			}

		} else {


			if ( weraProductSet instanceof WeraProductSet ) {

				// --- Inhalt der S�tze
				System.out.println("call _createContentElementSet");
				_createContentElementWeraProductSet( (WeraProductSet)weraProductSet, oTexList);

			} else {


				System.out.println("call _createContentElement");
				_createContentElementWeraProduct ( weraProductSet, oTexList);
			}

		}

		return colFootnodes;
	}

	/**
	 * Inhalt der S�tze (Satz in Satz (orogonal version, wird nicht mehr benötigt!)
	 * 
	 * @param WeraProductSetinSet weraProductSetinSet
	 * @param Element oTexList
	 */

	protected void _org_createContentElementSetinSet(final WeraProductSetinSet weraProductSet, final Element oTexList) {

		// --- Debug
		LOG.info ("s._createContentElementSetinSet="+weraProductSet.getCode() );

		// --- Initialize
		WeraMedia oMediaIcon1 = null;
		WeraMedia oMediaIcon2 = null;
		Element oMetaData = null;
		boolean bIsNextSet = true;
		String strPrevArtnr = "";
		final int iPos = 0;
		int iOrder = 0;
		HashSet setCode = new HashSet();

		// --- Initialisiere den Content f�r Satz in Satz
		weraProductSet.generateWeraProductSetData();

		// --- preset
		String strArtnrSiS = (String) m_wm.getAttribute(weraProductSet,"artnr");
		if ( strArtnrSiS == null ) { strArtnrSiS = ""; }
		String strVarnrSiS = (String) m_wm.getAttribute(weraProductSet,"variantennr");
		if ( strVarnrSiS == null ) { strVarnrSiS = ""; }
		String strLagernrSiS = (String) m_wm.getAttribute(weraProductSet,"lagernr");
		if ( strLagernrSiS == null ) { strLagernrSiS = ""; }
		final String strCodeSiS = strLagernrSiS + strArtnrSiS + strVarnrSiS;


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



		// --- Schleife �ber alle Daten
		for (final Iterator iterProductSetinSet = weraProductSet.m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {

			// --- N�chster Satz
			final HashMap productHash = (HashMap) iterProductSetinSet.next();
			//LOG.info("+++++productHash=" + productHash);

			// --- Initialize ---------------------------------------------------------------------------
			// --- codenr
			String strArtnr = (String) productHash.get("artnr");
			if ( strArtnr == null ) { strArtnr = ""; }
			String strVarnr = (String) productHash.get("varnr");
			if ( strVarnr == null ) { strVarnr = ""; }
			String strLagernr = (String) productHash.get("lagernr");
			if ( strLagernr == null ) { strLagernr = ""; }
			final String strCode = strLagernr + strArtnr + strVarnr;
			LOG.info("_createContentElementSetinSet.strCode=" + strCode );

			// --- Hyperlink CodeNr
			if ( !strCode.contains("??") ) {
				m_firstcodeNr = strCode;
			}


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

			// --- Schleife �ber alle Varianten des Satzes
			final ArrayList listVariantdata = (ArrayList) productHash.get("variantdata");
			for (final Iterator itVariant = listVariantdata.iterator(); itVariant.hasNext();) {
				final HashMap hashVariantdata = (HashMap) itVariant.next();

				// --- Pr�fen, ob eBLT_SETin neuer Satz beginnnt
				bIsNextSet = (strPrevArtnr == null || strArtnr == null) || (strPrevArtnr.equals(strArtnr) == false);

				// --- Neue Zeile initialisieren
				final Collection<String> colCells = new ArrayList();

				// --- Initialize
				String strTypeName = "";
				String strTagContent = "";
				//Integer oVpe = new Integer(0);
				final String strMedia1 = "";
				final String strMedia2 = "";
				final String strVariant = "";



				// --- Code
				LOG.info("+++++_createContentElementSetinSet.productHash.code="+productHash.get("code"));
				LOG.info("+++++_createContentElementSetinSet.hashVariantdata.code=" + hashVariantdata.get("code"));
				//strTypeName = (String) productHash.get("code");
				strTypeName = (String) hashVariantdata.get("code");
				strTypeName = strTypeName.replace("Satz", "");

				// --- ArtikelNr
				strTagContent = strTypeName + "\r";

				// --- Variantdaten
				final Collection colVariants = (Collection) hashVariantdata.get("colHashArtikel");
				//LOG.info("+++++colVariants=" + colVariants);
				int iPos1 = 1;
				for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();) {

					final HashMap oHashMapArtikel = (HashMap) iterVariant.next();

					LOG.info ( "_createContentElementSetinSet.oHashMapArtikel=" + oHashMapArtikel );
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
							//strFN = m_oExportFormatter.formatIndesignSetSupS() + strFN + m_oExportFormatter.formatIndesignSetSupE();
							strFN = /* m_oExportFormatter.formatIndesignSetSupS() + */ strFN /* + m_oExportFormatter.formatIndesignSetSupE() */;
						}
						if ( iPos1 > 0 && iPos1 < colVariants.size()) {
							strTagContent += oHashMapArtikel.get("value") + strFN + "; "/* + ";<cNoBreak:> "*/ ;
						} else {
							strTagContent += oHashMapArtikel.get("value") + strFN;
						}

					} // --- if ( oHashMapArtikel!=  null ) {

					// --- Default Sequnce - Order
					iPos1++;
					iOrder = iPos;

				} // --- for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();)


				// --- Neuer Satz, Metadata -----------------------------------------------------------------------------------
				oMetaData = new Element("Metadata");
//System.out.println("***_createContentElementSetinSet."+m_strCurrentTemplateName + "***");
				if (false && bIsNextSet && (m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT"))) {
//System.out.println("***create second BLT_SET"***");
					// --- Element anlegen
					oMetaData = new Element("Metadata");

					// --- Element Inhalt setzen
					oMetaData.addContent(m_oExportFormatter.formatCode(strLagernr, strArtnr, strVarnr));

					// --- attribute setzen
					oMetaData.setAttribute("ean", strEan);
					oMetaData.setAttribute("vpe", strVpe );
					oMetaData.setAttribute("rawcode", strLagernr + strArtnr + strVarnr);

					// --- Hyperlinks
					if ( m_firstcodeNr	== "" ) {
						m_firstcodeNr	= strLagernr + strArtnr + strVarnr;
					}

					// --- Zusammenhalten einer Zeile
					// --- Textobjekt anlegen
					final Element textXML = _createTextElement("", "", iOrder, m_iOffset, "BLT_SET", false, "", 0, "", false);
					m_iOffset++;
					oTexList.addContent(textXML);

					// --- Hole die Preisdaten
					HashMap	priceData	= getPriceData ( weraProductSet );
					oMetaData.setAttribute("PRBRUTTO_QUANTITY", (String)priceData.get("PRBRUTTO_QUANTITY") );
					oMetaData.setAttribute("PRBRUTTO", (String)priceData.get("PRBRUTTO") );

					// --- SatzInSatz Info
					textXML.addContent(oMetaData);

				} else {

				}
				// --- Element anlegen
				oMetaData = new Element("Metadata");

				// --- im n�chsten Block als "leer" ausgeben
				String strTagContentTmp = "";
				if (false && m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT")) {

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
				if (setCode.contains(strCode)) {
					oMetaData.setAttribute("rawcode", "");
					oMetaData.setAttribute("vpe", "");
					oMetaData.setAttribute("ean", "");

					// --- tag-content
					oMetaData.addContent("");

				} else {

					oMetaData.setAttribute("rawcode", strCode );
					oMetaData.setAttribute("vpe", strVpe );
					oMetaData.setAttribute("ean", strEan );

					// --- Hyperlinks
					if ( strCode.contains("??") && m_firstcodeNr	== "" ) {
						m_firstcodeNr	= strCode;
					}

					// --- tag-content
					oMetaData.addContent(strTagContentTmp);

					// --- merken
					setCode.add( strCode );
				}
				// --- Neuer Satz, Metadata -----------------------------------------------------------------------------------

				// --- Zusammenhalten einer Zeile
				// --- Textobjekt anlegen
// System.out.println("Vor _createTextElement=" + strTagContent);
				final Element textXML = _createTextElement(strTypeName, strTagContent, iOrder, m_iOffset, "BLT_SET", false, "", 0, "", false);
				m_iOffset++;
				oTexList.addContent(textXML);

				// --- CodeSIS
				textXML.setAttribute("sis_code", strCodeSiS );

				// --- Hole die Preisdaten
				//HashMap	priceData	= getPriceData ( weraProductSet );
				//oMetaData.setAttribute("PRBRUTTO_QUANTITY", (String)priceData.get("PRBRUTTO_QUANTITY") );
				//oMetaData.setAttribute("PRBRUTTO", (String)priceData.get("PRBRUTTO") );
				oMetaData.setAttribute("PRBRUTTO_QUANTITY", "" );
				oMetaData.setAttribute("PRBRUTTO", "" );

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
				// --- Icons --------------------------------------------------------------------------------------------------------------

				// --- Artikelnummer merken
				strPrevArtnr = strArtnr;

			} // --- for ( Iterator itVariant=listVariantdata.Iterator(); itVariant.hasNext() )

		} // --- for (final Iterator iterProductSetinSet = m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {

		// --- Debug
		LOG.info ("e._createContentElementSetinSet="+weraProductSet.getCode() );
	}


	/**
	 * Inhalt der S�tze (Satz in Satz
	 *
	 * @param WeraProductSetinSet weraProductSetinSet
	 * @param Element oTexList
	 */
	protected void _createContentElementSetinSet(final WeraProductSetinSet weraProductSetinSet, final Element oTexList, HashMap hashmapAllFootnotes, Collection colFootnotes) {

		// --- Debug
		LOG.info ("++++++++++++++++s._createContentElementSetinSet getCode()="+ weraProductSetinSet.getCode() );

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

		// --- Initialisiere den Content f�r Satz in Satz
		weraProductSetinSet.generateWeraProductSetData();

		// --- preset
		String strArtnrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"artnr");
		if ( strArtnrSiS == null ) { strArtnrSiS = ""; }
		String strVarnrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"variantennr");
		if ( strVarnrSiS == null ) { strVarnrSiS = ""; }
		String strLagernrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"lagernr");
		if ( strLagernrSiS == null ) { strLagernrSiS = ""; }
		final String strCodeSiS = strLagernrSiS + strArtnrSiS + strVarnrSiS;


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

		// --- wenn sis in sis dann product-header einfügen  ---------------------------------------------------
		if ( weraProductSetinSet.containsSIS() ) {

			// --- VPE auswerten
			Integer intContentQuantitySiS = (Integer) m_wm.getAttribute(weraProductSetinSet,"contentQuantity");
			if ( intContentQuantitySiS == null ) { intContentQuantitySiS = new Integer(1); }

			// --- Header ELement angegen
			Element oProductheaderset	= this._createHeaderSet ( weraProductSetinSet, oTexList, "BLT_PRODUCTHEADERSET", intContentQuantitySiS.toString(), false );
		}
		// --- wenn sis in sis dann product-header einfügen  ---------------------------------------------------


		// --- Inhalt des Satz in Satz Content generieren
		this._createcolWeraProductSetinSetData (weraProductSetinSet, weraProductSetinSet.m_colWeraProductSetinSetData, oTexList, hashmapAllFootnotes, colFootnotes, iEbeneEinrueckung);


		// --- Debug
		LOG.info ("e._createContentElementSetinSet getCode()="+ weraProductSetinSet.getCode() );
	}


	/**
	 * Inhalt des Satz in Satz Content generieren
	 *
	 * @param WeraProductSetinSet weraProductSetinSet
	 * @param Collection colWeraProductSetinSetData
	 * @param Element oTexList
     */
	protected void _createcolWeraProductSetinSetData(final WeraProductSetinSet weraProductSetinSet, final Collection colWeraProductSetinSetData, final Element oTexList, HashMap hashmapAllFootnotes, Collection colParamFootnotes, int iEbeneEinrueckung  ) {

		// --- initialize
		WeraMedia oMediaIcon1 = null;
		WeraMedia oMediaIcon2 = null;
		Element oMetaData = null;
		boolean bIsNextSet = true;
		String strPrevArtnr = "";
		final int iPos = 0;
		int iOrder = 0;
		HashSet setCode = new HashSet();

		// --- preset
		String strArtnrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"artnr");
		if ( strArtnrSiS == null ) { strArtnrSiS = ""; }
		String strVarnrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"variantennr");
		if ( strVarnrSiS == null ) { strVarnrSiS = ""; }
		String strLagernrSiS = (String) m_wm.getAttribute(weraProductSetinSet,"lagernr");
		if ( strLagernrSiS == null ) { strLagernrSiS = ""; }
		final String strCodeSiS = strLagernrSiS + strArtnrSiS + strVarnrSiS;

		// --- bei anzeige als Display leere Metatag einsetzen -----------------------------------------------
		Boolean istDisplay = (Boolean)m_wm.getAttribute(weraProductSetinSet,"ist_display");
		if ( istDisplay && iEbeneEinrueckung == 0 ) {

			// --- set Data
			String currentArtNr		= weraProductSetinSet.getCode();
			Integer intVPE = (Integer) m_wm.getAttribute(weraProductSetinSet,"contentQuantity");
			if ( intVPE == null ) { intVPE = new Integer(1); }
			String strEAN = (String) m_wm.getAttribute(weraProductSetinSet,"EAN");
			if (strEAN == null || strEAN.length() == 0 || strEAN.length() < 8) {
				strEAN = "";
			} else {
				strEAN = strEAN.substring(7);
			}

			// --- erster Eintrag mit VPE -----------------------------------------------
			// --- attribute setzen
			oMetaData = new Element("Metadata");
			oMetaData.addContent( strCodeSiS );
			oMetaData.setAttribute("rawcode",  strCodeSiS );
			oMetaData.setAttribute("ean", strEAN );
			oMetaData.setAttribute("vpe", intVPE.toString() );

			final Element textXMLohneContent = _createTextElement("", "", iOrder, m_iOffset,"BLT_SET", false, "", 0, "", false);
			oTexList.addContent(textXMLohneContent);
			textXMLohneContent.addContent( oMetaData );
			m_iOffset++;
		}
		// --- bei anzeige als Display leere Metatag einsetzen

		// --- schleife über alle Inhalte
		for (final Iterator iterProductSetinSet = colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {

			// --- N�chster Satz
			final HashMap productHash = (HashMap) iterProductSetinSet.next();
			//LOG.info("+++++productHash=" + productHash);

			// --- Initialize ---------------------------------------------------------------------------
			// --- codenr
			boolean bcurrentIsSet				= false;
			String strArtnr = (String) productHash.get("artnr");
			if ( strArtnr == null ) { strArtnr = ""; }
			String strVarnr = (String) productHash.get("varnr");
			if ( strVarnr == null ) { strVarnr = ""; }
			String strLagernr = (String) productHash.get("lagernr");
			if ( strLagernr == null ) { strLagernr = ""; }
			String strCode = strLagernr + strArtnr + strVarnr;
			LOG.info("_createContentElementSetinSet.strCode=" + strCode );
			String strVpe = (String) productHash.get("vpe");
			if (strVpe == null) {
				strVpe	= "";
			}

			// --- hole weraprodukt
			PK oPK 								= (PK) productHash.get("pk");
			final Item oWeraProduct 			= JaloSession.getCurrentSession().getItem(oPK);

			// --- pürfung auf Satz in Satz in SATZ ----------------------------------
			LOG.info ("++++++++++++++++ createContentElementSetinSet.getClass=" + oWeraProduct.getClass().getSimpleName() );
			LOG.info ("++++++++++++++++ createContentElementSetinSet.strCode=" + strCode + "=" );
			LOG.info ("++++++++++++++++ createContentElementSetinSet.artNr=" + productHash.get("code") );
			if ( productHash.get("isSiS") == "1" ) {

				// --- Wera-Produktheader ------------------------------------------------
				if ( oWeraProduct instanceof WeraProductSetinSet ) {

					if ( !(m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT") || m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT") )) {
						// --- Header ELement angegen
						Element oHeaderset = this._createHeaderSet((WeraProductSetinSet) oWeraProduct, oTexList, "BLT_HEADERSET", strVpe, false );

						// --- ebene SIS in SIS
						oHeaderset.setAttribute("ebene", String.valueOf(iEbeneEinrueckung));
					}
				}
				// --- Wera-Produktheader ------------------------------------------------

				// --- Inhalt des Satz in Satz Content generieren
				this._createcolWeraProductSetinSetData( weraProductSetinSet, (Collection)productHash.get("SiSComponents"), oTexList, hashmapAllFootnotes, colParamFootnotes, ++iEbeneEinrueckung );

				// --- skio
				continue;

			} else {

				LOG.info ("++++++++++++++++ createContentElementSetinSet.isSiS in SIS=0" );
			}
			// --- pürfung auf Satz in Satz in SATZ ----------------------------------


			// --- Hyperlink CodeNr
			if ( !strCode.contains("??") ) {
				m_firstcodeNr = strCode;
			}


			String strEan = "";
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


			// --- Bei Varianten immer eine Beschreibung mit ausgeben
			if ( oWeraProduct instanceof WeraVariante ) {

				if ( !(m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT") || m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT") )) {
					// --- Header ELement angegen
					// LOG.info ("++++++++++++++++ ist WeraVariante schreibe Header=" );
					Element oHeaderset = this._createHeaderSet((WeraVariante) oWeraProduct, oTexList, "BLT_HEADERSET", strVpe, false);

					// --- ebene SIS in SIS
					oHeaderset.setAttribute("ebene", String.valueOf(iEbeneEinrueckung));
				}

				// --- bei varianten keine Metadaten anzeigen
				LOG.info ("++++++++++++++++ createContentElementSetinSet.strCode CLEAR=");
				strCode		= "";
				strLagernr	= "";
				strArtnr	= "";
				strVarnr	= "";
				strVpe		= "";
				strEan		= "";
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
					Element oHeaderset = this._createHeaderSet(oWeraProduct, oTexList, "BLT_HEADERSET", strVpe, false);
					bcurrentIsSet = true;

					// --- ebene SIS in SIS
					oHeaderset.setAttribute("ebene", String.valueOf(iEbeneEinrueckung));
				}

			} else {

				bcurrentIsSet	= false;
			}
			// --- Wera-Produktheader ------------------------------------------------

			// --- Schleife �ber alle Varianten des Satzes
			final ArrayList listVariantdata = (ArrayList) productHash.get("variantdata");
			for (final Iterator itVariant = listVariantdata.iterator(); itVariant.hasNext();) {
				final HashMap hashVariantdata = (HashMap) itVariant.next();

				// --- hole weraprodukt
				PK oPKTmp							= (PK) hashVariantdata.get("pk");
				final Item oWeraArticle 			= JaloSession.getCurrentSession().getItem(oPKTmp);

				// --- Pr�fen, ob eBLT_SETin neuer Satz beginnnt
				bIsNextSet = (strPrevArtnr == null || strArtnr == null) || (strPrevArtnr.equals(strArtnr) == false);

				// --- Neue Zeile initialisieren
				final Collection<String> colCells = new ArrayList();

				// --- Initialize
				String strTypeName = "";
				String strTagContent = "";
				//Integer oVpe = new Integer(0);
				final String strMedia1 = "";
				final String strMedia2 = "";
				final String strVariant = "";



				// --- Code
				LOG.info("+++++_createContentElementSetinSet.productHash.code="+productHash.get("code"));
				LOG.info("+++++_createContentElementSetinSet.hashVariantdata.code=" + hashVariantdata.get("code"));
				//strTypeName = (String) productHash.get("code");
				strTypeName = (String) hashVariantdata.get("code");
				strTypeName = strTypeName.replace("Satz", "");

				// --- ArtikelNr
				strTagContent = ""; // strTypeName + "\r";

				// --- Variantdaten
				final Collection colVariants = (Collection) hashVariantdata.get("colHashArtikel");
				//LOG.info("+++++colVariants=" + colVariants);
				int iPos1 = 1;
				for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();) {

					final HashMap oHashMapArtikel = (HashMap) iterVariant.next();

					LOG.info ( "_createContentElementSetinSet.oHashMapArtikel=" + oHashMapArtikel );
					if (oHashMapArtikel != null) {

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

/////////////////////////////////////////////////////////////////////////////////////////////////////// Orginal
/*
						if (colFN != null) {
							for (final Iterator it3 = colFN.iterator(); it3.hasNext();) {
								strFN += " " + ((Integer) it3.next()).toString() + ")";
							}
						}
*/
/////////////////////////////////////////////////////////////////////////////////////////////////////// Orginal

						if (strFN.length() > 0) {
							// --- strFN = "<cPosition:Superscript>" + strFN + "<cPosition:>";
							//strFN = m_oExportFormatter.formatIndesignSetSupS() + strFN + m_oExportFormatter.formatIndesignSetSupE();
							strFN = /* m_oExportFormatter.formatIndesignSetSupS() + */ strFN /* + m_oExportFormatter.formatIndesignSetSupE() */;
						}
						if ( iPos1 > 0 && iPos1 < colVariants.size()) {
							strTagContent += oHashMapArtikel.get("value") + strFN + "; "/* + ";<cNoBreak:> "*/ ;
						} else {
							strTagContent += oHashMapArtikel.get("value") + strFN;
						}

					} // --- if ( oHashMapArtikel!=  null ) {

					// --- Default Sequnce - Order
					iPos1++;
					iOrder = iPos;

				} // --- for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();)


				// --- Neuer Satz, Metadata -----------------------------------------------------------------------------------
				oMetaData = new Element("Metadata");
//System.out.println("***_createContentElementSetinSet."+m_strCurrentTemplateName + "***");
/*
				if (false && bIsNextSet && (m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT"))) {
//System.out.println("***create second BLT_SET"***");
					// --- Element anlegen
					oMetaData = new Element("Metadata");

					// --- Element Inhalt setzen
					oMetaData.addContent(m_oExportFormatter.formatCode(strLagernr, strArtnr, strVarnr));

					// --- attribute setzen
					oMetaData.setAttribute("ean", strEan);
					oMetaData.setAttribute("vpe", strVpe );
					oMetaData.setAttribute("rawcode", strLagernr + strArtnr + strVarnr);

					// --- Hyperlinks
					if ( m_firstcodeNr	== "" ) {
						m_firstcodeNr	= strLagernr + strArtnr + strVarnr;
					}

					// --- Zusammenhalten einer Zeile
					// --- Textobjekt anlegen
					final Element textXML = _createTextElement("", "", iOrder, m_iOffset, "BLT_SET", false, "", 0, "", false);
					m_iOffset++;
					oTexList.addContent(textXML);

					// --- Hole die Preisdaten
					HashMap	priceData	= getPriceData ( weraProductSetinSet );
					oMetaData.setAttribute("PRBRUTTO_QUANTITY", (String)priceData.get("PRBRUTTO_QUANTITY") );
					oMetaData.setAttribute("PRBRUTTO", (String)priceData.get("PRBRUTTO") );

					// --- SatzInSatz Info
					textXML.addContent(oMetaData);

				}
*/
				// --- Element anlegen
				oMetaData = new Element("Metadata");

				// --- im n�chsten Block als "leer" ausgeben
				String strTagContentTmp = "";
				if ( !(m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT") ||
						m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT")) ) {
				// if (false && m_strCurrentTemplateName.equals("PRODUCTSETINSET_SBVARIANT_SORTIMENT")) {

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
					strEan 	= "";
					strVpe 	= "";
					strCode	= "";

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
				if (setCode.contains(strCode)) {
					oMetaData.setAttribute("rawcode", "");
					oMetaData.setAttribute("vpe", "");
					oMetaData.setAttribute("ean", "");

					// --- tag-content
					oMetaData.addContent("");

				} else {

					LOG.info ("++++++++++++++++ !!.createContentElementSetinSet.getClass=" + oWeraArticle.getClass().getSimpleName() );
					LOG.info ("++++++++++++++++ !!.createContentElementSetinSet.strCode=" + strCode + "=" );
					LOG.info ("++++++++++++++++ !!.createContentElementSetinSet.strEan=" + strEan + "=" );

					oMetaData.setAttribute("rawcode", strCode );
					oMetaData.setAttribute("vpe", strVpe );
					oMetaData.setAttribute("ean", strEan );

					// --- Hyperlinks
					if ( strCode.contains("??") && m_firstcodeNr	== "" ) {
						m_firstcodeNr	= strCode;
					}

					// --- tag-content
					oMetaData.addContent(strTagContentTmp);

					// --- merken
					setCode.add( strCode );
				}
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
// System.out.println("Vor _createTextElement=" + strTagContent);
				final Element textXML = _createTextElementLocalized(strTypeName, strTagContent, iOrder, m_iOffset, "BLT_SET", false, "", 0, "", false, oWeraArticle, bcurrentIsSet, boolHideArtikelnumber );
				m_iOffset++;
				oTexList.addContent(textXML);

				// --- CodeSIS
				textXML.setAttribute("sis_code", strCodeSiS );

				// --- Hole die Preisdaten
				//HashMap	priceData	= getPriceData ( weraProductSetinSet );
				//oMetaData.setAttribute("PRBRUTTO_QUANTITY", (String)priceData.get("PRBRUTTO_QUANTITY") );
				//oMetaData.setAttribute("PRBRUTTO", (String)priceData.get("PRBRUTTO") );
				oMetaData.setAttribute("PRBRUTTO_QUANTITY", "" );
				oMetaData.setAttribute("PRBRUTTO", "" );

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
				// --- Icons --------------------------------------------------------------------------------------------------------------

				// --- Artikelnummer merken
				strPrevArtnr = strArtnr;

			} // --- for ( Iterator itVariant=listVariantdata.Iterator(); itVariant.hasNext() )

		} // --- for (final Iterator iterProductSetinSet = m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {

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
//		LOG.info ("s._createContentElementSetinSet_noContent, getCode()=" + weraProductSetinSet.getCode() );
//		LOG.info ("s._createContentElementSetinSet_noContent getName=" + weraProductSetinSet.getName() );
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


			// --- SatzInSatz Info
			textXML.addContent(oMetaData);

			/////////////////////////////////////////////////////////////////////////////////////
			// --- Schleife �ber alle Varianten des Satzes (START)
			/////////////////////////////////////////////////////////////////////////////////////
			ArrayList listVariantdata = (ArrayList) productHash.get("variantdata");
			for (final Iterator itVariant = listVariantdata.iterator(); itVariant.hasNext();) {
				final HashMap hashVariantdata = (HashMap) itVariant.next();

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
				// --- Icons --------------------------------------------------------------------------------------------------------------


				// --- Artikelnummer merken
				strPrevArtnr = strArtnr;

			} // --- for ( Iterator itVariant=listVariantdata.Iterator(); itVariant.hasNext() )
			/////////////////////////////////////////////////////////////////////////////////////
			// --- Schleife �ber alle Varianten des Satzes (ENDE)
			/////////////////////////////////////////////////////////////////////////////////////


		} // --- for (final Iterator iterProductSetinSet = m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {


		// --- Display-Darstellung ------------------------------------------------------------------------------
		setDisplayTitleKatalog( weraProductSet, oTexList, " x" );

		// --- Debug
		// LOG.info ("e._createContentElementSetinSet_noContent="+weraProductSetinSet.getCode() );
	}

	/**
	 * 
	 * @param weraProductSet
	 * @param oTexList 
	 */
	protected void _createContentElementWeraProductSet (final WeraProductSet weraProductSet, final Element oTexList) {

		ArrayList aContent = weraProductSet.generateWeraProductSetData();
		_createContentElement ( (WeraProduct) weraProductSet, oTexList, aContent );
	}
	
	/**
	 * 
	 * @param weraProductSet
	 * @param oTexList
	 * @param aContent 
	 */
	protected void _createContentElement (final WeraProduct weraProductSet, final Element oTexList, ArrayList aContent ) {
		
		// --- Debug
		LOG.info ("s._createContentElement="+weraProductSet.getCode() );

		// --- Initialize
		final String strLastCodeNr = "";
		Element oMetaData = null;
		WeraMedia icon1 = null;
		WeraMedia icon2 = null;
		ArrayList colHash = null;
		HashMap oHashMapProdukt = null;
		HashMap oHashMapArtikel = null;
		final ArrayList aMetaData = new ArrayList();
		//LOG.info("Nach generateWeraProductSetData");
		//weraProductSetinSet.debugOutWeraProductSetData();

		// --- Initialize
		String strTypeName = "";
		String strTagContent = "";
		int iPos = 0;
		int iOrder = 0;

		
		// --- preset
		String lastArtNr	= "";
		
		// --- Schleife �ber alle Content-Inhalte
		if (aContent != null && aContent.size() > 0) {
			for (final Iterator it1 = aContent.iterator(); it1.hasNext();) {

				// --- Hole Map
				oHashMapProdukt = (HashMap) it1.next();
				iPos++;
				LOG.info("_createContentElement iPOS=" + iPos);

				// --- Initialize
				strTypeName = (String) oHashMapProdukt.get("code");
				strTagContent = ""; /* strTypeName + "\r"; */

				// --- get the product
				WeraProduct oSetProduct = (WeraProduct) ProductManager.getInstance().getProductByPK((PK) oHashMapProdukt.get("pk"));
				boolean isWeraProductSet	= ( oSetProduct instanceof WeraProductSet);

				// --- metadata -------------------------------------------------------------
				if ( weraProductSet instanceof WeraProductSet ) {

					// --- Element anlegen
					oMetaData = new Element("Metadata");
					String ean				= (String)m_wm.getAttribute(weraProductSet, "ean");
					Integer vpe				= (Integer)m_wm.getAttribute(weraProductSet, "contentQuantity");
					//String vpe				= (String)oHashMapProdukt.get("vpe");
					if (ean == null || ean.length() == 0 || ean.length() < 8) {
						ean	= "";
					} else {
						ean = ean.substring(7);
					}
					if ( vpe == null ) { vpe = new Integer(0); }
					String strLagerNr		= (String)m_wm.getAttribute(weraProductSet, "lagernr");
					if ( strLagerNr == null ) { strLagerNr = ""; }
					String strArtnr			= (String)m_wm.getAttribute(weraProductSet, "artnr");
					if ( strArtnr == null ) { strArtnr = ""; }
					String strVariantennr	= (String)m_wm.getAttribute(weraProductSet, "variantennr");
					if ( strVariantennr == null ) { strVariantennr = ""; }
					
					// --- set Data
					String currentCodeNr	= strLagerNr + strArtnr + strVariantennr;
					String currentArtNr		= weraProductSet.getCode();
					
					// --- f�llen immer bei neuer CodeNr
					if ( !currentArtNr.equals(lastArtNr) ) {
						
						// --- aktuelle Artikel merken
						lastArtNr	= currentArtNr;
						
						// --- erster Eintrag Header ---------------------------------------------
						oMetaData = new Element("Metadata");
						oMetaData.addContent( "Code" );
						oMetaData.setAttribute("rawcode",  "Code" );
						oMetaData.setAttribute("ean", "4013288" );
						oMetaData.setAttribute("vpe", "" );
						final Element textXMLohneContent1 = _createTextElement(strTypeName, "", -1, m_iOffset, "BLT_SETHEADER", false, "", 0, "", false);
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

						
						// --- erster Eintrag mit VPE -----------------------------------------------
						// --- attribute setzen
						oMetaData = new Element("Metadata");
						oMetaData.addContent( currentCodeNr );
						oMetaData.setAttribute("rawcode",  currentCodeNr );
						oMetaData.setAttribute("ean", ean );
						oMetaData.setAttribute("vpe", vpe.toString() );

// --- debug
// oMetaData.setAttribute("testfirst", "test:1" );

						// --- Hole die Preisdaten
						// HashMap	priceData	= getPriceData ( weraProductSet );
						// oMetaData.setAttribute("PRBRUTTO_QUANTITY", (String)priceData.get("PRBRUTTO_QUANTITY") );
						// oMetaData.setAttribute("PRBRUTTO", (String)priceData.get("PRBRUTTO") );
						
						final Element textXMLohneContent = _createTextElement(strTypeName, "", iOrder, m_iOffset,"BLT_SET", false, "", 0, "", false);
						oTexList.addContent(textXMLohneContent);
						textXMLohneContent.addContent( oMetaData );
						m_iOffset++;
						
					}
						
					// --- attribute setzen
					oMetaData = new Element("Metadata");
					oMetaData.addContent("");
					oMetaData.setAttribute("rawcode",  "" );
					oMetaData.setAttribute("ean", "" );
					oMetaData.setAttribute("vpe", "" );
					// oMetaData.setAttribute("PRBRUTTO_QUANTITY", "" );
					// oMetaData.setAttribute("PRBRUTTO", "" );
					
				}
				// --- metadata -------------------------------------------------------------


				// --- Element anlegen
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
							if (iPos1 > 0 && iPos1 < colHash.size()) {
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
				strTagContent = this.getExportFormatter().formatSetContent(strTagContent);

				// --- Hochstellen des Registerzeichens
				strTypeName = strTypeName.replace("\u00ae",
						m_oExportFormatter.formatSetSupS() + "\u00ae" + m_oExportFormatter.formatSetSupE());

				// --- Zusammenhalten einer Zeile

				// --- metadata -------------------------------------------------------------

				// --- Textobjekt anlegen
				//final Element textXML			= _createTextElement(strTypeName, strTagContent, iOrder, m_iOffset,"BLT_SET", false, "", 0, "", false);
				final Element textXML			= this._createTextElementLocalized(strTypeName, strTagContent, iOrder, m_iOffset,"BLT_SET", false, "", 0, "", false, oSetProduct, isWeraProductSet, false );
				m_iOffset++;
				oTexList.addContent(textXML);
				if ( oMetaData != null ) {

					textXML.addContent( oMetaData );
				}
				// --- metadata -------------------------------------------------------------

				

				// --- Icon 1
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

		} else { // --- if ( aContent != null && aContent.size() > 0 ) {
			
			// --- Hochstellen des Registerzeichens
			strTypeName = strTypeName.replace("\u00ae",
					m_oExportFormatter.formatSetSupS() + "\u00ae" + m_oExportFormatter.formatSetSupE());
			
			// --- Zusammenhalten einer Zeile
			// --- Textobjekt anlegen
			final Element textXML = _createTextElement(strTypeName, "", iOrder, m_iOffset,"BLT_SET", false, "", 0, "", false);
			m_iOffset++;
			oTexList.addContent(textXML);
			
			// --- metadata -------------------------------------------------------------
			if ( weraProductSet instanceof WeraProductSet ) {

				// --- Element anlegen
				oMetaData = new Element("Metadata");
				String ean				= (String)m_wm.getAttribute(weraProductSet, "ean");
				Integer vpe				= (Integer)m_wm.getAttribute(weraProductSet, "contentQuantity");
				//String vpe				= (String)oHashMapProdukt.get("vpe");
				if (ean == null || ean.length() == 0 || ean.length() < 8) {
					ean	= "";
				} else {
					ean = ean.substring(7);
				}
				if ( vpe == null ) { vpe = new Integer(0); }
				String strLagerNr		= (String)m_wm.getAttribute(weraProductSet, "lagernr");
				if ( strLagerNr == null ) { strLagerNr = ""; }
				String strArtnr			= (String)m_wm.getAttribute(weraProductSet, "artnr");
				if ( strArtnr == null ) { strArtnr = ""; }
				String strVariantennr	= (String)m_wm.getAttribute(weraProductSet, "variantennr");
				if ( strVariantennr == null ) { strVariantennr = ""; }

				// --- set Data
				String currentCodeNr	= strLagerNr + strArtnr + strVariantennr;
				String currentArtNr		= weraProductSet.getCode();

				// --- attribute setzen
				oMetaData.addContent( currentCodeNr );
				oMetaData.setAttribute("rawcode",  currentCodeNr );
				oMetaData.setAttribute("ean", ean );
				oMetaData.setAttribute("vpe", vpe.toString() );

// --- debug
// oMetaData.setAttribute("testfirst", "test:2" );

				// --- Hole die Preisdaten
				// HashMap	priceData	= getPriceData ( weraProductSet );
				// oMetaData.setAttribute("PRBRUTTO_QUANTITY", (String)priceData.get("PRBRUTTO_QUANTITY") );
				// oMetaData.setAttribute("PRBRUTTO", (String)priceData.get("PRBRUTTO") );

				textXML.addContent( oMetaData );
			}
			// --- metadata -------------------------------------------------------------
		}
		
		// --- Debug
		LOG.info ("e._createContentElement="+weraProductSet.getCode() );
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

		// --- Content bereinigen
		strTagContent = strTagContent.replaceAll("##br##", " ");
		strTagContent = strTagContent.replaceAll("##br /##", " ");
		strTagContent = strTagContent.replaceAll("##br/##", " ");
		strTagContent = this.getExportFormatter().formatDescription(strTagContent);

		// --- get current language
		Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();

		// --- oTextBlock liste erstellen
		for ( String language  : colLanguages ) {

			// -- set language
			m_wm.SetLanguage(language);

			// --- Textblock erzeugen
			WeraProduct weraProductName	= null;
			String strNameLocalized = "";
			if ( weraProduct instanceof WeraVariante ) {

				// --- hole basisprodukt
				weraProductName = (WeraProduct) m_wm.getAttribute(weraProduct, "baseproduct");

			} else {

				weraProductName	= (WeraProduct)weraProduct;
			}
			strNameLocalized = (String) m_wm.getAttribute(weraProductName, "artikelnr_index");

			if ( boolHideArtikelnummer ) {
				strNameLocalized	= "";
			}

			// --- Textblock erzeugen
			Element oTextBlock	= new Element("TextBlock");
			oTextBlock.setAttribute("Language", language );
			oTextBlock.setAttribute("Type", "LocalizedContent" );


			//LOG.info("+++++_createTextElementLocalized.strNameLocalized=" +strNameLocalized);
			//LOG.info("+++++_createTextElementLocalized.getClass().getName()=" +weraProduct.getClass().getName());
			if ( true /* bcurrentIsSet */ ) {

				oTextBlock.setText( strNameLocalized + "\r" + strTagContent );

			} else {

				oTextBlock.setText( strVPE + "x " + strNameLocalized + "\r" + strTagContent );
			}
			textXML.addContent(oTextBlock);

		} // for ( String language  : colLanguages ) {

		// --- reset current language
		m_wm.SetLanguage( currentSessionLanguage.getIsoCode() );
		// --- Artikelnummer localized ---------------------------------------------------------------


		return textXML;
	}

}


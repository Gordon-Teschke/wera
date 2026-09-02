package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.media.Media;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Config;
import de.hybris.platform.jalo.c2l.Language;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.jdom.CDATA;
import org.jdom.Comment;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;


public class WebSiteXml extends BMEcatXml
{


	/** Edit the local|project.properties to change logging behavior (properties 'log4j.*'). */
	private static final Logger LOG = Logger.getLogger(WebSiteXml.class.getName());

	ArrayList m_aListLanguages = new ArrayList();
	ArrayList m_colRequestCategories = new ArrayList();
	HashSet m_IconSet = new HashSet();
	HashMap m_Icon2ProductMap = new HashMap();
	public HashSet m_hExludeList = new HashSet();
	String m_strLanguage = new String();
	Collection m_allProductsFromCatalog = new ArrayList();
	MediaCollector m_mediaCollector = null;
	public boolean m_bLoadExcludeList = false;

	public WebSiteXml()
	{
		super();
		// TODO Auto-generated constructor stub

		m_strCatalog = "web";
	}

	// --- Zurücksetzen der Sprache, aufräumen
	@Override
	public void cleanUp()
	{

		// --- Inistialiue
		String strLine = "";

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

		// --- Zähler zurücksetzen
		m_iOffsetIDCategory = 0;
		m_iOffsetIDEbene2 = 0;
		m_iOffsetIDEbene3 = 0;
	}

	public void setMediaCollector(final MediaCollector mediaCollector)
	{
		m_mediaCollector = mediaCollector;
	}

	// --- lade eine Lsite mit Exlude-ProduktNummern
	void LoadExcludeList(final String strFileName)
	{

		if (m_bLoadExcludeList)
		{
			// --- HashSet löschen
			m_hExludeList.clear();

			// --- Datei öffnen und einlesen
			BufferedReader in;
			try
			{
				in = new BufferedReader(new InputStreamReader(new FileInputStream(strFileName)));

				String strCode = "";
				try
				{
					while ((strCode = in.readLine()) != null)
					{
						m_hExludeList.add(strCode);
					}
					in.close();
				}
				catch (final IOException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			catch (final FileNotFoundException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void SetListLanguage(final String strLanguage)
	{
		m_aListLanguages.clear();
		m_aListLanguages.add(strLanguage);
		m_strLanguage = strLanguage;

		// --- lade ExcludeListe (Produktliste) für die aktuelle Sprache
		final String strFileName = Config.getParameter("wera.homepath") + "/export/website/control/web_" + m_strLanguage
				+ "_exclude.txt";
		LoadExcludeList(strFileName);
	}

	public void setRequestCategories(final Collection colCategories)
	{
		this.m_colRequestCategories = (ArrayList) colCategories;
	}

	public Collection getRequestCategories()
	{
		return this.m_colRequestCategories;
	}



	public void WebSiteGenerate(final String strCatalogversion)
	{
		// TODO Auto-generated method stub
		super.BMEcatGenerate(strCatalogversion);
	}


	@Override
	public Element GenCATALOG_STRUCTURE(Category oOutputCategory)
	{

		// --- Initialize
                Category oCategoryMasterCatalog = oOutputCategory;
		String strCatalogVersion = "";
                String strCategorieType = "";
		Boolean oIsWebCatalog = null;


		// --- Merke alle Produkte der Kategorie
		final Collection products = oOutputCategory.getProducts();
		m_allProductsFromCatalog.addAll(products);

		// --- Prüfe, ob die Katalogversion "Weramaster ist", sonst andere katagorie holen
		CatalogVersion catalogversion = null;
		try
		{
			catalogversion = (CatalogVersion) oOutputCategory.getAttribute("catalogVersion");
			if (catalogversion != null)
			{
				strCatalogVersion = catalogversion.getVersion();
				oIsWebCatalog = (Boolean) catalogversion.getAttribute("is_webcatalog");
				if (oIsWebCatalog == null)
				{
					oIsWebCatalog = new Boolean(false);
				}
			}
			//if ( oIsWebCatalog.booleanValue() && !strCatalogVersion.equals(Config.getParameter("wera.mastercatalogversion")) ) {
			if (!strCatalogVersion.equals(Config.getParameter("wera.mastercatalogversion")))
			{
				// --- Hole Category aus WeraMaster
				LOG.info("+++strCatalogVersion=" + strCatalogVersion + ", get weramaster, oOutputCategory=" + oOutputCategory.getCode() );
				oCategoryMasterCatalog = m_weraMasterCatalogVersion.getCategory(oOutputCategory.getCode());
				if ( oCategoryMasterCatalog == null ) {
					LOG.info("WARNING: Category with code >"+oOutputCategory.getCode()+"< does NOT exist in weramaster!");
				}
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

                // --- Categorie-type (node / leaf), based on outputCategory
                final Collection oColCategorySub = (Collection) m_wm.getAttribute(oOutputCategory, "products");
                if (oColCategorySub.size() > 0)
                   strCategorieType = "leaf";
                else
                   strCategorieType = "node";

		// --- XML-Knoten erzeugen
		return _genCATALOG_STRUCTURE(oCategoryMasterCatalog, strCategorieType );
	}

	private Element _genCATALOG_STRUCTURE(final Category oCategory, String strCategorieType )
	{
		// TODO Auto-generated method stub

		LOG.info("WebSiteXml.GenCATALOG_STRUCTURE");

		// --- Initialize
		Collection oColCategoryParent = null;
		Category oCategoryParent = null;
		Category oCategoryTopLevelParent = null;
		Element oGroupName = null;
		Element oGroupDescription = null;
		Element oPARENTCATEGORYTOPLEVEL_ID = null;
		Element oGROUP_ID = null;
		String strLanguage = "";
		String strName = "";

		/*
		 * if ( ! this.getRequestCategories().contains(oCategory.getCode()) && !oCategory.getCode().equals("root") ) {
		 * return null; }
		 */

		// --- Kategorie erzeugen					
		final Element oXmlCategory = new Element("CATALOG_STRUCTURE");

		// --- Hole Parent-Category
		if (!oCategory.getCode().equals("root"))
		{
			oColCategoryParent = (Collection) m_wm.getAttribute(oCategory, "supercategories");
			if (oColCategoryParent != null && oColCategoryParent.size() > 0)
			{
                            
                                // --- hole die direkt parent category
				oCategoryParent = (Category) oColCategoryParent.iterator().next();
				LOG.info("Category=" + oCategory.getCode() + ", Parent-Category=" + oCategoryParent.getCode());

                                // --- hole die erste parent-category
                                Collection oColCategoryParentTmp = (Collection) oCategory.getPath();
                                Category oParentCategoryTopLevel = null;
                                Object[] aCategoryParents = oColCategoryParentTmp.toArray();
                                if ( aCategoryParents.length >= 2 ) {
                                    oParentCategoryTopLevel = (Category)aCategoryParents[aCategoryParents.length-2]; // lenght-1 == root
                                }
                                if ( oParentCategoryTopLevel == null ) {
                                    oParentCategoryTopLevel=oCategoryParent;
                                }
                                LOG.info("* oParentCategoryTopLevel=" + oParentCategoryTopLevel.getCode() );

				// --- set category-type based on Outputcategory
                                oXmlCategory.setAttribute("type",  strCategorieType ); 

				oPARENTCATEGORYTOPLEVEL_ID = new Element("PARENTCATEGORYTOPLEVEL_ID");
				oXmlCategory.addContent(oPARENTCATEGORYTOPLEVEL_ID.addContent(m_wm.getAttribute(oParentCategoryTopLevel, "code").toString()));
				oGROUP_ID = new Element("GROUP_ID");
				oXmlCategory.addContent(oGROUP_ID.addContent(m_wm.getAttribute(oCategory, "code").toString()));

				// --- Schleife über alle Ausgabesprachen
				for (final Iterator it1 = m_aListLanguages.iterator(); it1.hasNext();)
				{
					// --- Sprache setzen
					strLanguage = (String) it1.next();
					m_wm.SetLanguage(strLanguage);


					strName = (String) m_wm.getAttribute(oCategory, "name");
					if (strName == null)
					{
						strName = "";
					}

					oGroupName = new Element("GROUP_NAME").addContent(strName);

					oGroupName.setAttribute("lang", strLanguage);
					oXmlCategory.addContent(oGroupName);
					oGroupName.setAttribute("normalized_url", m_wm.strNormalizeURL(oCategory, strLanguage));
					String sUntertitel = (String) m_wm.getAttribute(oCategory, "untertitel");
					if (sUntertitel == null)
					{
						sUntertitel = "";
					}
					oGroupName = new Element("GROUP_SUBTITEL").addContent(sUntertitel);
					oGroupName.setAttribute("lang", strLanguage);
					oXmlCategory.addContent(oGroupName);

					// --- GROUP_SUBTITEL an GROUP_NAME anhängen
					Boolean bConcat_description = (Boolean) m_wm.getAttribute(oCategory, "concat_description");
					if (bConcat_description == null)
					{
						bConcat_description = new Boolean(false);
					}
					if (bConcat_description.booleanValue())
					{
						oGroupName.setAttribute("concat_description", "1");
					}
					else
					{
						oGroupName.setAttribute("concat_description", "0");
					}

					// --- Group-Desciption
					oGroupDescription = new Element("GROUP_DESCRIPTION");
					String sDescription = (String) m_wm.getAttribute(oCategory, "description");
					if (sDescription == null)
					{
						sDescription = "";
					}
					final CDATA cdataText = new CDATA(sDescription);
					oGroupDescription.addContent(cdataText);
					oGroupDescription.setAttribute("lang", strLanguage);
					oXmlCategory.addContent(oGroupDescription);


				}
				oXmlCategory.addContent(new Element("PARENT_ID").addContent(m_wm.getAttribute(oCategoryParent, "code").toString()));
                                Integer oOrder = (Integer) m_wm.getAttribute(oCategory, "order");
                                if ( oOrder != null )
                                    oXmlCategory.addContent(new Element("GROUP_ORDER").addContent(oOrder.toString()));
                                else {
                                    oXmlCategory.addContent(new Element("GROUP_ORDER").addContent("1"));
                                    LOG.info("Category=" + oCategory.getCode() + ", no orderinformation default=1" );
                                }

				// --- Bilddaten Initialisieren
				InitMedia(oXmlCategory, oCategory);
			}
		}
		else
		{

			// --- Kategorie füllen
			oXmlCategory.setAttribute("type", "root");
			oGROUP_ID = new Element("GROUP_ID");
			oXmlCategory.addContent(oGROUP_ID.addContent(m_wm.getAttribute(oCategory, "code").toString()));
                        
                        
                        oPARENTCATEGORYTOPLEVEL_ID = new Element("PARENTCATEGORYTOPLEVEL_ID");
                        oXmlCategory.addContent(oPARENTCATEGORYTOPLEVEL_ID.addContent("root"));

			// --- Schleife über alle Ausgabesprachen
			for (final Iterator it1 = m_aListLanguages.iterator(); it1.hasNext();)
			{
				// --- Sprache setzen
				strLanguage = (String) it1.next();
				m_wm.SetLanguage(strLanguage);


				strName = (String) m_wm.getAttribute(oCategory, "name");
				if (strName == null)
				{
					strName = "";
				}
				oGroupName = new Element("GROUP_NAME").addContent(strName);
				oXmlCategory.addContent(oGroupName);
				oGroupName.setAttribute("lang", strLanguage);
				oGroupName.setAttribute("normalized_url", m_wm.strNormalizeURL(oCategory, strLanguage));
				String sUntertitel = (String) m_wm.getAttribute(oCategory, "untertitel");
				if (sUntertitel == null)
				{
					sUntertitel = "";
				}
				oGroupName = new Element("GROUP_SUBTITEL").addContent(sUntertitel);
				oGroupName.setAttribute("lang", strLanguage);
				oXmlCategory.addContent(oGroupName);

				// --- GROUP_SUBTITEL an GROUP_NAME anhängen
				Boolean bConcat_description = (Boolean) m_wm.getAttribute(oCategory, "concat_description");
				if (bConcat_description == null)
				{
					bConcat_description = new Boolean(false);
				}
				if (bConcat_description.booleanValue())
				{
					oGroupName.setAttribute("concat_description", "1");
				}
				else
				{
					oGroupName.setAttribute("concat_description", "0");
				}

				// --- Group-Desciption
				oGroupDescription = new Element("GROUP_DESCRIPTION");
				String sDescription = (String) m_wm.getAttribute(oCategory, "description");
				if (sDescription == null)
				{
					sDescription = "";
				}
				final CDATA cdataText = new CDATA(sDescription);
				oGroupDescription.addContent(cdataText);
				oXmlCategory.addContent(oGroupDescription);

			}

			oXmlCategory.addContent(new Element("PARENT_ID").addContent("0"));
			oXmlCategory.addContent(new Element("GROUP_ORDER").addContent(m_wm.getAttribute(oCategory, "order").toString()));

			// --- Bilddaten Initialisieren
			InitMedia(oXmlCategory, oCategory);
		}

		// --- Sprache zurücksetzen
		m_wm.SetLanguage("de");

		return oXmlCategory;
	}


	private void InitMedia(final Element oXmlCategory, final Category oCategory)
	{
		// TODO Auto-generated method stub

		// --- Initialize
		Collection media = new ArrayList();
		Media oAktmedia = null;

		// --- Get Media- Collection (Bilder 150px - Home / Pulldown)
		media = (Collection) m_wm.getAttribute(oCategory, "medias");
		if (media != null)
		{
			for (final Iterator it = media.iterator(); it.hasNext();)
			{
				// --- next one
				oAktmedia = (Media) it.next();

				// --- Init Media-Node
				if (oAktmedia.getCode().contains("pulldown"))
				{
					oXmlCategory.addContent(_InitMediaNode(oAktmedia, "pulldown", "category" + File.separator + "150"));
				}
				else
				{
					oXmlCategory.addContent(_InitMediaNode(oAktmedia, "home", "category" + File.separator + "150"));
				}
			}
		}

		// --- Get Media- Collection (Bilder 220px - Kategoryübersichten)
		media = (Collection) m_wm.getAttribute(oCategory, "data_sheet");
		if (media != null)
		{
			for (final Iterator it = media.iterator(); it.hasNext();)
			{
				// --- next one
				oAktmedia = (Media) it.next();

				// --- Init Media-Node
				oXmlCategory.addContent(_InitMediaNode(oAktmedia, "category_panel", "category" + File.separator + "220"));
			}
		}

		// --- Get Media- Collection (Bilder 60px - Kleine Naviagtion)
		media = (Collection) m_wm.getAttribute(oCategory, "detail");
		if (media != null)
		{
			for (final Iterator it = media.iterator(); it.hasNext();)
			{
				// --- next one
				oAktmedia = (Media) it.next();

				// --- Init Media-Node
				if (oAktmedia.getCode().contains("rollover"))
				{
					oXmlCategory.addContent(_InitMediaNode(oAktmedia, "klein_rollover", "category" + File.separator + "60"));
				}
				else
				{
					oXmlCategory.addContent(_InitMediaNode(oAktmedia, "klein_normal", "category" + File.separator + "60"));
				}
			}
		}

		// --- Get Media- Collection (Bilder 960px - Headbilder)
		media = (Collection) m_wm.getAttribute(oCategory, "logo");
		int iMediaCnt = 0;
		if (media != null)
		{
			for (final Iterator it = media.iterator(); it.hasNext();)
			{
				// --- next one
				oAktmedia = (Media) it.next();

				// --- Init Media-Node
				oXmlCategory.addContent(_InitMediaNode(oAktmedia, "headbild", "category" + File.separator + "960"));
				iMediaCnt++;
			}
		}
		if (iMediaCnt == 0)
		{
			// --- FAKE - REMOVE ASAP
			final Element oGROUP_IMAGE = new Element("GROUP_IMAGE");
			oGROUP_IMAGE.setAttribute("type", "headbild");
			oXmlCategory.addContent(oGROUP_IMAGE.addContent("headbild_dummy.jpg"));
		}
	}

	private Element _InitMediaNode(final Media oAktmedia, final String strMediaType, final String strDestPath)
	{
		// TODO Auto-generated method stub

		// --- Neues Media - Object anlegen
		m_mediaCollector.addMedia(oAktmedia.getPK().toString(), oAktmedia.getRealFileName(), oAktmedia.getFileName(), strDestPath);

		// --- XML-Knoten erstellen
		final Element oGROUP_IMAGE = new Element("GROUP_IMAGE");
		oGROUP_IMAGE.setAttribute("type", strMediaType);
		oGROUP_IMAGE.addContent(oAktmedia.getCode());

		return oGROUP_IMAGE;
	}

	@Override
	public Collection CreateArticleList(final WeraProduct oProduct, final Collection categories)
	{

		// --- Initialize
		final ArrayList oArticelList = new ArrayList();
		final String strProductCode = "";

		// --- Fülle einen Product oder Set
		if (oProduct != null)
		{
			final Element oElementTmp = CreateArticle(oProduct, categories);
			if (oElementTmp != null)
			{
				oArticelList.add(oElementTmp);
			}
		}

		return oArticelList;

	}

	// --- ARTICLE mode="new">...</ARTICLE>
	@Override
	public void BMEcatGenARTICLE()
	{
		// --- Initialize
		final ArrayList oArticelList = new ArrayList();

		// --- Alle Produkte#
		Product oProduct = null;
		//Collection products = (Collection)WeraProduct.getAllProductsFromCatalog(m_weraCatalogVersion,false, "code");
		LOG.info("BMEcatGenARTICLE - Anzahl Products=" + m_allProductsFromCatalog.size());
		for (final Iterator it1 = m_allProductsFromCatalog.iterator(); it1.hasNext();)
		{

			// -- Get WERAPRODUKT
			oProduct = (Product) it1.next();


			// --- Prüfen, ob das Produkt sich in einer Auschlussliste befindet 
			if (m_hExludeList.contains(oProduct.getCode()) == false)
			{


				// --- Erzeuge ein Produktelement
				if (oProduct instanceof WeraProduct)
				{

					// if ( oProduct.getCode().equals("1334 SK/6")==false )
					// continue;

					// --- Hole alle Kategorien
					final Collection categories = (Collection) m_wm.getAttribute(oProduct, "supercategories");

					// --- Prüfe Alternativ-Produkt
					final WeraProduct oAlternativProduct = ((WeraProduct) oProduct).getAlternateProduct(m_strLanguage);
					if (oAlternativProduct != null)
					{
						LOG.info("++Change to alternate product=" + oAlternativProduct.getCode());
						oProduct = oAlternativProduct;
					}

					// --- 
					//if (m_wm.checkContaining(categories, "code", "SBKATALOG") != null)
					//	continue;
					if (m_wm.checkContaining(categories, "code", "VERKAUFSHILFE") != null)
					{
						continue;
					}
					if (m_wm.checkContaining(categories, "code", "PREISLISTENARTIKEL") != null)
					{
						continue;
					}

					if (true)
					{
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

							// --- Alles Aritkel oder Sätze des aktuellen
							// Produkts als Liste generieren
							oArticelList.addAll(CreateArticleList((WeraProduct) oProduct, categories));

							// --- Schleife über alle Elemete
							for (final Iterator it2 = oArticelList.iterator(); it2.hasNext();)
							{
								// --- Element in Root-Knoten einfügen
								m_oNewCatalogElement.addContent((Element) it2.next());
							}
						}
					}
				}
			} // if ( m_hExludeList.contains(oProduct.getCode()) == false ) {

		} // --- for ( Iterator it1 = products.iterator(); it1.hasNext();) {

	}

	// --- Holt die Reihenfolge des Products an der Kategory / Products Ext
	//     Fallback, Attribute "order" vom Produkt
	private String _getOrderByCategory2Productexts(final Product oProduct, final Category oCategoryMatching)
	{
		Category2ProductExt category2productext = null;
		Integer oIntOrder = null;
		String sCategoryPK = null;
		// oCategoryMatching ist die Kategorie, für die wir die kategorieinterne Produktreihenfolge wissen wollen.
		if (oCategoryMatching != null)
		{
			sCategoryPK = oCategoryMatching.getPK().toString();
			category2productext = ((WeraProduct) oProduct).getCategory2Productexts(sCategoryPK);
			if (category2productext != null)
			{
				// --- Hole Order / By Category
				final String strPrio = category2productext.getPriority();
				if (strPrio != null && strPrio.trim().length() > 0)
				{
					// --- Falls Prio in category2productext vorhanden, nehmen wir diese
					oIntOrder = new Integer(Integer.parseInt(strPrio));
				}
			}
		}
		// Falls kategoriespezifische Reihenfolge nicht bestimmt werden kann, dann direkt aus dem Produkt versuchen
		if (oIntOrder == null)
		{
			oIntOrder = (Integer) m_wm.getAttribute(oProduct, "order");
			if (oIntOrder == null)
			{
				// --- Falls keine Prio im Produkt selbst, dann auf 0 setzen
				oIntOrder = new Integer(0);
			}
		}
		String strOrder = "";
		strOrder = "000000" + oIntOrder.toString();
		return strOrder;
	}

	/**
	 * 
	 * @param Product oProduct
	 * @param Collection categories
	 * @return Element
	 */
	public Element CreateArticle(final Product oProduct, final Collection categories)
	{

		// --- Initialize
		boolean bIsBit = false;
		Element oElementARTICLE = null;
		String strLanguage = "";
		Element oDESCRIPTION_SHORT = null;

		// --- Produktcode
		final String strProductCode = oProduct.getCode();

		
		
		// --- Keine doppelten Artikel übernehmen
		if (m_hashCATALOGGROUP.containsKey(strProductCode) == false)
		{

			// --- Initialize
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
			if (oProduct instanceof WeraProductSet || oProduct instanceof WeraVariante)
			{
				bIsOrderProduct = true;
			}

			// --- prüfe orderprodukt, Auslaufartikel
			if ( bIsOrderProduct ) {
				// --- prüfe Auslaufartikel
				Boolean bProduktAuslauf = (Boolean) m_wm.getAttribute(oProduct, "artikel_auslauf");
				if (bProduktAuslauf == null) {
					bProduktAuslauf = new Boolean(false);
				}
				if ( bProduktAuslauf.booleanValue() ) {
					
					// --- skip auslaufartikel
					LOG.info ( "+++++++++skip Auslaufartikel >" + strProductCode + "<" );
					return null;
				}
			}
			
			// --- Create Element_Tag
			oElementARTICLE = new Element("PRODUCT");

			// --- Hole das Basisprodukt
			final Product oBaseProduct = oProduct;


			// --- Code-Nummer
			LOG.info("Artikel=" + strProductCode);
			oElementARTICLE.addContent(new Element("SUPPLIER_AID").addContent(strProductCode));

			// --- Artikel-Details
			final Element oElementDetails = new Element("PRODUCT_DETAILS");
			final String strDescription1 = getValidString(((GeneratedWeraProduct) oBaseProduct).getDescription1(m_jaloSession
					.getSessionContext()));
			oElementARTICLE.addContent(oElementDetails);
			for (final Iterator it1 = m_aListLanguages.iterator(); it1.hasNext();)
			{
				// --- Sprache setzen
				strLanguage = (String) it1.next();
				m_wm.SetLanguage(strLanguage);
				oDESCRIPTION_SHORT = new Element("DESCRIPTION_SHORT").addContent((String) m_wm.getAttribute(oBaseProduct, "name"));
				oDESCRIPTION_SHORT.setAttribute("lang", strLanguage);
				oElementDetails.addContent(oDESCRIPTION_SHORT);
			}

			// --- Artikel / Kategorie Zuordnung
			CatalogVersion csvTmp = null;
			Category oCategory = null;
			Category oCategoryWM = null;
			Category oCategoryMatching = null;
			final Collection colCategories = new ArrayList();
			final HashMap hashPRODUCT = new HashMap();
			m_hashCATALOGGROUP.put(strProductCode, hashPRODUCT);
			for (final Iterator it1 = categories.iterator(); it1.hasNext();)
			{
				oCategory = (Category) it1.next();

				if (oCategory.getCode().substring(0, 2).equals("AA") == false
						&& oCategory.getCode().substring(0, 2).equals("_0") == false)
				{

					// --- Filter der Categoriern anhand der Katalogversion
					csvTmp = (CatalogVersion) m_wm.getAttribute(oCategory, "CatalogVersion");
					if (m_weraCatalogVersion.equals(csvTmp))
					{

						// --- Merke Kategorie
						if (!colCategories.contains(oCategory.getCode()))
						{
							final HashMap hashCATEGORY = new HashMap();
							hashCATEGORY.put("code", oCategory.getCode());
							oCategoryMatching = oCategory;
							/*
							 * Fix - 28.03.2011 GT String strOrder = ""; if (m_wm.getAttribute(oCategory, "order") == null)
							 * strOrder = "00000000"; else strOrder = "000000" + m_wm.getAttribute(oCategory, "order")
							 * .toString();
							 */
							// --- Holt die Reihenfolge des Products an der Kategory / Products Ext
							//     Fallback, Attribute "order" vom Produkt
							final String strOrder = _getOrderByCategory2Productexts(oProduct, oCategoryMatching);
							hashCATEGORY.put("order", strOrder.substring(strOrder.length() - 7));
							//LOG.info ( "+++++++++use oCategoryMatching=" + oCategoryMatching.getCode() + ", order=" + strOrder );

							// --- Hole Category aus Weramaster für normalize-URL
							oCategoryWM = m_weraMasterCatalogVersion.getCategory(oCategory.getCode());
							hashCATEGORY.put("category_normalized_url", m_wm.strNormalizeURL(oCategoryWM, strLanguage));

							colCategories.add(hashCATEGORY);
                                                        
                                                        //---------------------------------------------------------------------------------------------
                                                        // --- Erweiterung für neues BIT-Layout - TEIL I - START
                                                        // --- Pruefe auf Bit-Category
                                                        //---------------------------------------------------------------------------------------------
                                                        // --- Alle Oberkategorien der Produktkategorie holen, um zu sehen,
                                                        //     ob die Produktkategorie eine spezielle Kategorie unter BITS ist.
                                                        if ( bIsBit == false && oCategoryMatching != null)
                                                        {
                                                            Collection colSupercategories = colSupercategories = oCategoryMatching.getSupercategories();
                                                            if (colSupercategories != null)
                                                            {
                                                                for (final Iterator itCategory = colSupercategories.iterator(); itCategory.hasNext();) {
                                                                    Category oTmpCategory = (Category)itCategory.next();
//LOG.info("+++ "+ strProductCode+ " - oTmpCategory.getCode=true" + oTmpCategory.getCode() );
                                                                    if (oTmpCategory.getCode().equals("BITS") || oTmpCategory.getCode().equals("SB_BITS")
                                                                             || oTmpCategory.getCode().equals("AERO_BITS") ) {
                                                                        bIsBit = true;
                                                                        break;
                                                                    }

                                                                } // for (final Iterator itCategory = colSupercategories.iterator(); itCategory.hasNext();) {
                                                            }
                                                        }
                                                        //---------------------------------------------------------------------------------------------
                                                        // --- Erweiterung für neues BIT-Layout - TEIL I - START
                                                        //---------------------------------------------------------------------------------------------

                                                        
                                                        
                                                        
                                                        
						} // --- if (!colCategories.contains(oCategory.getCode())) {

					} // --- if ( m_weraCatalogVersion.equals(csvTmp) ) {

				}

			} // --- for ( Iterator it1=categories.iterator(); it1.hasNext(); ) {


			//---------------------------------------------------------------------------------------------
			// --- Erweiterung für neues BIT-Layout - TEIL II - START
			//---------------------------------------------------------------------------------------------
			
                        String sReihe = "";
			String sBitsReiheOrder = "";
                        
                        // --- ist das aktuelle Produkt ein Bit?
                        if ( bIsBit ) {
//LOG.info("+++ "+ strProductCode+ " - ISBIT=true");
                                // Die Produktkategorie ist eine Unterkategorie unter Bits.
                                // Die Zahl hinter dem ersten Slash der Produkt Code-Nummer bestimmt die Reihe!

                                // Leider Ausnahmen, für die die Regel nicht anwendbar ist
                                boolean isException = false;
                                // 3 Bits auf Reihe 1 forcieren
                                if (strProductCode.equals("700 A HTS") || strProductCode.equals("700 B HTS")
                                                || strProductCode.equals("700 C HTS"))
                                {
                                        sReihe = "1";
                                        isException = true;
                                }
                                // --- Führungshülsen (sonderfall)
                                if (strProductCode.equals("803") || strProductCode.equals("807/4 Z"))
                                {
                                        sReihe = "fuehrungshuelse";
                                        isException = true;
                                }

                                // Alle anderen über Codenummer erkennen	   				
                                if (!isException)
                                {
                                        final int iReihentrenner = strProductCode.indexOf("/");
                                        final int iLength = strProductCode.length();
                                        if (iReihentrenner >= 0)
                                        {
                                                int iPos = iReihentrenner + 1;
                                                while (iPos < iLength)
                                                {
                                                        final String sReihenZiffer = strProductCode.substring(iPos, iPos + 1);
                                                        if (sReihenZiffer != null && !sReihenZiffer.equals(" "))
                                                        {
                                                                sReihe += sReihenZiffer;
                                                        }
                                                        else
                                                        {
                                                                break;
                                                        }
                                                        iPos++;
                                                }
                                        }
                                }

                                // Reihenfolge der Bits-Reihe ermitteln
                                sBitsReiheOrder = getReiheOrder(sReihe);

                                LOG.info("oSuperSuperCategory is BITS => Reihe = " + sReihe);
                        }
                        else
                        {
LOG.info("+++ "+ strProductCode+ " - ISBIT=false");
                                // LOG.info("oSuperSuperCategory is not BITS => no further Reihe calculation");
                        }
			
                        // --- Reihenfolge Bits
			hashPRODUCT.put("BITS_REIHE", sReihe);
			hashPRODUCT.put("BITS_REIHE_ORDER", sBitsReiheOrder);
                        
			//---------------------------------------------------------------------------------------------
			// --- Erweiterung für neues BIT-Layout - TEIL II - ENDE
			//---------------------------------------------------------------------------------------------

                        
                        
                        

			//---------------------------------------------------------------------------------------------



			/*
			 * String strOrder = ""; if ( m_wm.getAttribute(oProduct,"order") == null ) hashPRODUCT.put("ORDER",
			 * "00000000"); else { strOrder = "000000" + m_wm.getAttribute(oProduct,"order").toString();
			 * hashPRODUCT.put("ORDER", strOrder.substring(strOrder.length()-7) ); }
			 */
			//---------------------------------------------------------------------------------------------

			if (oBaseProduct instanceof WeraProductSetinSet)
			{
				hashPRODUCT.put("type", "WeraProductSetinSet");
			}
			else
			{
				if (oBaseProduct instanceof WeraProductSet)
				{
					hashPRODUCT.put("type", "WeraProductSet");
				}
				else
				{
					hashPRODUCT.put("type", "WeraProduct");
				}
			}
			hashPRODUCT.put("colCategories", colCategories);
			hashPRODUCT.put("produkt_normalized_url", WeraProduct.s_normalizeFilenameForImageLookup(oBaseProduct.getCode()));
			m_hashCATALOGGROUP.put(strProductCode, hashPRODUCT);

			// --- MIME_INFO				
			// --- Normal
			final WeraMedia oWeraMedia = null;
			String strProduktImage = ((WeraProduct) oBaseProduct).normalizeFilenameForImageLookup() + ".jpg";
			strProduktImage = _NormalizeImageName(strProduktImage);
			m_hashImages.put(strProduktImage, strProduktImage);

			final Element oMIME_INFO = new Element("MIME_INFO");
			oElementARTICLE.addContent(oMIME_INFO);
			final Element oMIME = new Element("MIME");
			oMIME_INFO.addContent(oMIME);
			oMIME.addContent(new Element("MIME_TYPE").addContent("image/jpg"));
			oMIME.addContent(new Element("MIME_SOURCE").addContent(strProduktImage));

			// --- Initialize IconSets
			final HashMap setIcon1 = new HashMap();
			final HashMap setIcon2 = new HashMap();

			// --- Abtriebsicon
			if (oProduct instanceof WeraProductSet)
			{
				// --- Initialize
				// WeraMedia oWeraMediaIcon1 = null;
				// WeraMedia oWeraMediaIcon2 = null;
				final ArrayList colHash = null;
				HashMap oHashMapProdukt = null;
				final HashMap oHashMapArtikel = null;
				final int iPos = 0;

				final ArrayList aContent = ((WeraProductSet) oProduct).generateWeraProductSetData();
				if (aContent != null && aContent.size() > 0)
				{

					// --- Schleife über alle Icons
					int iCnt = 0;
					for (final Iterator it1 = aContent.iterator(); it1.hasNext();)
					{

						// --- Hole Map
						oHashMapProdukt = (HashMap) it1.next();

						// --- Initialize
						//oWeraMediaIcon1 = (WeraMedia) oHashMapProdukt.get("icons1");
						//oWeraMediaIcon2 = (WeraMedia) oHashMapProdukt.get("icons2");
                                                
                            
                                                // --- iterate on icon-collection
                                                Collection<WeraMedia> colWeraMediaIcon1 = (Collection<WeraMedia>) oHashMapProdukt.get("icons1_collection");
                                                for (final Iterator itIconMedias1 = colWeraMediaIcon1.iterator(); itIconMedias1.hasNext();)
                                                {
                                                    // --- Hole Artikel / Variante
                                                    WeraMedia oWeraMediaIcon1  = (WeraMedia) itIconMedias1.next();
                                                    if (oWeraMediaIcon1 != null)
                                                    {
                                                        // --- Liste aller Icons
                                                        final String strIcon = _normalizeIconName(oWeraMediaIcon1.getCode().toString());
                                                        m_IconSet.add(strIcon);

                                                        // --- Liste der Icons / Produkte
                                                        if (!setIcon1.containsKey(strIcon))
                                                        {
                                                            final HashMap hashIcon = new HashMap();
                                                            hashIcon.put("icon", strIcon);
                                                            hashIcon.put("order", new Integer(iCnt++).toString());
                                                            setIcon1.put(strIcon, hashIcon);
                                                        }
                                                    }
                                                    
                                                } // --- for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)

                                                
                                                // --- iterate on icon-collection
                                                Collection<WeraMedia> colWeraMediaIcon2 = (Collection<WeraMedia>) oHashMapProdukt.get("icons2_collection");
                                                for (final Iterator itIconMedias2 = colWeraMediaIcon2.iterator(); itIconMedias2.hasNext();)
                                                {
                                                    // --- Hole Artikel / Variante
                                                    WeraMedia oWeraMediaIcon2  = (WeraMedia) itIconMedias2.next();
                                                    if (oWeraMediaIcon2 != null)
                                                    {
                                                        // --- Liste aller Icons
                                                        final String strIcon = _normalizeIconName(oWeraMediaIcon2.getCode().toString());
                                                        m_IconSet.add(strIcon);

                                                        // --- Liste der Icons / Produkte
                                                        if (!setIcon2.containsKey(strIcon))
                                                        {
                                                            final HashMap hashIcon = new HashMap();
                                                            hashIcon.put("icon", strIcon);
                                                            hashIcon.put("order", new Integer(iCnt++).toString());
                                                            setIcon2.put(strIcon, hashIcon);
                                                        }
                                                    }
                                                    
                                                } // --- for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)

					} // --- for ( Iterator it1 = aContent.iterator(); it1.hasNext(); ) {

					// --- Iconliste merken
					hashPRODUCT.put("ICON", setIcon1);
					hashPRODUCT.put("ICON2", setIcon2);
				}
			}
			else
			{
				final Collection colIcons1 = (Collection) m_wm.getAttribute(oBaseProduct, "icons1");
				final Collection colIcons2 = (Collection) m_wm.getAttribute(oBaseProduct, "icons2");
				WeraMedia oWeraMediaIcon1 = null;
				if (colIcons1.size() > 0)
				{
                                    // --- ICON 1 (Liste) ----------------------------------------------------------------------------------------------------------
                                    // --- iterate on all icons
                                    int iSequence = 1;
                                    for (final Iterator itIconMedias = colIcons1.iterator(); itIconMedias.hasNext();)
                                    {
                                        // --- Hole Artikel / Variante
                                        oWeraMediaIcon1 = (WeraMedia) itIconMedias.next();

                                        // --- Liste aller Icons
                                        final String strIcon = _normalizeIconName(oWeraMediaIcon1.getCode().toString());
                                        m_IconSet.add( _normalizeIconName(strIcon) );

                                        // --- Liste der Icons / Produkte
                                        if (!setIcon1.containsKey(strIcon)) {
                                            final HashMap hashIcon = new HashMap();
                                            hashIcon.put("icon", strIcon);
                                            hashIcon.put("order", new Integer(iSequence++).toString());
                                            setIcon1.put(strIcon, hashIcon);
                                        }
                                    }
                                   // --- ICON 1 (Liste) ----------------------------------------------------------------------------------------------------------
                                    
				}
				WeraMedia oWeraMediaIcon2 = null;
				if (colIcons2.size() > 0)
				{
					oWeraMediaIcon2 = (WeraMedia) colIcons2.iterator().next();

					// --- Liste aller Icons
					m_IconSet.add(_normalizeIconName(oWeraMediaIcon2.getCode().toString()));

					// --- Liste der Icons / Produkte
					final String strIcon = _normalizeIconName(oWeraMediaIcon2.getCode().toString());
					final HashMap hashIcon = new HashMap();
					hashIcon.put("icon", strIcon);
					hashIcon.put("order", new Integer(1).toString());
					setIcon2.put(strIcon, hashIcon);
				}

				// --- Iconliste merken
				hashPRODUCT.put("ICON", setIcon1);
				hashPRODUCT.put("ICON2", setIcon2);
			}


		} // --- if ( m_hashCATALOGGROUP.containsKey(strProductCode) == false ) {

		// --- Sprache zurücksetzen
		m_wm.SetLanguage("de");

		return oElementARTICLE;
	}

	private String _normalizeIconName(String strIcon)
	{

		strIcon = strIcon.replaceAll("_weiss.jpg", ".jpg");
		if (strIcon.substring(0, 2).equals("AA"))
		{
			final int iCharPos = strIcon.indexOf('_') + 1;
			strIcon = strIcon.substring(iCharPos);
		}

		return strIcon;
	}

	@Override
	public void writeDocument(final String strXmlPath, final String strXmlFile)
	{
		// TODO Auto-generated method stub

		// --- Debug
		LOG.info("Ausgabe in Datei  =" + strXmlPath + strXmlFile);

		// --- Liste der Abtriebsicons
		final Element m_oICON_LIST = new Element("ICON_LIST");
		m_rootElement.addContent(m_oICON_LIST);

		// --- Schleife über alle Abtriebs-Icons
		final ComposedType TipsType = TypeManager.getInstance().getComposedType(Tips.class);
		Tips tip = null;
		final String strIcon1 = "";
		final Set setTips = TipsType.getAllInstances();
		Boolean bAktiv = null;
		for (final Iterator it1 = setTips.iterator(); it1.hasNext();)
		{
			tip = (Tips) it1.next();

			// --- AKTIV?
			bAktiv = (Boolean) m_wm.getAttribute(tip, "aktiv");
			if (bAktiv == null || bAktiv.booleanValue() == false)
			{
				continue;
				// --- AKTIV?
			}

			String strCode = (String) m_wm.getAttribute(tip, "code");
			String strOrder = "";
			if (m_wm.getAttribute(tip, "order") == null)
			{
				strOrder = "00000000";
			}
			else
			{
				strOrder = "000000" + m_wm.getAttribute(tip, "order").toString();
			}

			final Element oIcon = new Element("ICON").setAttribute("code", strCode).setAttribute("type", "abtrieb");
			oIcon.setAttribute("order", strOrder.substring(strOrder.length() - 7));
			strCode = strCode.toLowerCase().replaceAll(".jpg", "");
			strCode = strCode.toLowerCase().replaceAll(".gif", "");
			oIcon.setAttribute("filename", strCode);
			for (final Iterator it3 = m_aListLanguages.iterator(); it3.hasNext();)
			{
				final String strLang = (String) it3.next();
				final Element oTextentry = new Element("LONGNAME");
				oIcon.addContent(oTextentry);
				oTextentry.setAttribute("lang", strLang);
				m_wm.SetLanguage(strLang);
				oTextentry.addContent((String) m_wm.getAttribute(tip, "name"));
			}
			m_oICON_LIST.addContent(oIcon);
		}
		m_wm.SetLanguage("de");

		// --- Textliste
		final Element m_oTEXT_LIST = new Element("TEXT_LIST");
		m_rootElement.addContent(m_oTEXT_LIST);
		final ComposedType TextitemType = TypeManager.getInstance().getComposedType(Textitem.class);
		final Set textitems = TextitemType.getAllInstances();
		Textitem textitem = null;
		for (final Iterator it2 = textitems.iterator(); it2.hasNext();)
		{
			textitem = (Textitem) it2.next();
			final Element oTextitem = new Element("TEXTITEM");
			m_oTEXT_LIST.addContent(oTextitem);
			oTextitem.setAttribute("code", (String) m_wm.getAttribute(textitem, "code"));
			for (final Iterator it3 = m_aListLanguages.iterator(); it3.hasNext();)
			{
				final String strLang = (String) it3.next();
				final Element oTextentry = new Element("TEXTENTRY");
				oTextitem.addContent(oTextentry);
				oTextentry.setAttribute("lang", strLang);
				m_wm.SetLanguage(strLang);
				oTextentry.addContent((String) m_wm.getAttribute(textitem, "textblock"));
			}
			// --- Hole Image
			final Collection colImageList = (Collection) m_wm.getAttribute(textitem, "pictures4textitems");
			if (colImageList != null && !colImageList.isEmpty())
			{
				final WeraMedia oWeraMedia = (WeraMedia) colImageList.iterator().next();

				// --- Media-Object für Export registrieren
				m_mediaCollector.addMedia(oWeraMedia.getPK().toString(), oWeraMedia.getRealFileName(), oWeraMedia.getFileName(),
						"products/icons");

				final String strImageName = oWeraMedia.getCode();
				final Element oImageEntry = new Element("ICON");
				oImageEntry.addContent(strImageName);
				oTextitem.addContent(oImageEntry);

				// --- Bild für Exportliste merknen
				m_IconSet.add(strImageName);
			}
		}

		m_wm.SetLanguage(this.m_strLanguage);

                
                // --- disable fallback language handling (Weblings / Extimages)
                Boolean bLANGUAGE_FALLBACK_ENABLED = (Boolean)m_jaloSession.getSessionContext().getAttribute("languageFallbackEnabled");
                m_jaloSession.getSessionContext().setAttribute("languageFallbackEnabled", (Object)new Boolean(false));
                
		// --- hole Fallback der aktuellen Sprache
		final Language oAktLanguage = m_jaloSession.getSessionContext().getLanguage();
		List listCurrentFallbacks = oAktLanguage.getFallbackLanguages();
		
		// --- Fallback für aktuelle Sprache entfernen
		final ArrayList listNewFallback = new ArrayList();
		oAktLanguage.setFallbackLanguages(listNewFallback);
		
		// --- WEBLINK_LIST -------------------------------------------------------------------------------------------------
		String strDomain = (String) m_wm.getAttribute(m_weraCatalogVersion, "domain");
		if (strDomain == null)
		{
			strDomain = "wera.de";
                        LOG.error("++++ WEBLINK_LIST Domain by CatalogVersion not defined. set wera.de as default.");
		}

		// --- define attribute mapping ( xml-attribute, items-xml qualifier /)
		final HashMap<String, Object> hashParam = new HashMap<String, Object>();
		hashParam.put("code", "code");
		hashParam.put("title", new ArrayList(Arrays.asList("weblinktext2weblink", "textblock")));
		hashParam.put("href", "link_" + strDomain.replaceAll("\\.", "_").replaceAll("-", "_"));
                final HashMap<String, Boolean> hashMandatory = new HashMap<String, Boolean>();
		hashMandatory.put("code", new Boolean (true) );
		hashMandatory.put("title", new Boolean (true) );
		hashMandatory.put("href", new Boolean (true) );
                
		// --- generates a simple xml-list of Weblink.class, representing a list of attributes
		Element m_oWEBLINK_LIST = new Element("WEBLINK_LIST");
		m_oWEBLINK_LIST = GenerateSimpleNodeList(TypeManager.getInstance().getComposedType(Weblink.class), m_oWEBLINK_LIST,
				hashParam, hashMandatory, "WEBLINK");
		m_rootElement.addContent(m_oWEBLINK_LIST);

		m_wm.SetLanguage(this.m_strLanguage);
		// --- WEBLINK_LIST -------------------------------------------------------------------------------------------------


		// --- EXTERNAL_IMAGE_LIST -----------------------------------------------------------------------------------------
		// --- generates a simple xml-list of ExtImage.class, representing a list of attributes
		final Element m_oEXTERNAL_IMAGE_LIST = new Element("EXTERNAL_IMAGE_LIST");
		m_oWEBLINK_LIST = GenerateSimpleNodeList(TypeManager.getInstance().getComposedType(ExtImage.class), m_oEXTERNAL_IMAGE_LIST,
				new HashMap<String, Object>()
				{
					{
						/* xml-attribute, items-xml qualifier */
						put("code", "code");
						put("title", "title");
						put("text", "description");
						put("href", "location");
					}
				}, 
				new HashMap<String, Boolean>()
				{
					{ // --- mandatory
						/* xml-attribute, items-xml qualifier */
						put("code", new Boolean (true) );
						put("title", new Boolean (true) );
						put("text", new Boolean (false) );
						put("href", new Boolean (true) );
					}
				}, 
                                "IMAGE");
		m_rootElement.addContent(m_oEXTERNAL_IMAGE_LIST);
		// --- EXTERNAL_IMAGE_LIST -----------------------------------------------------------------------------------------

                // --- reset fallback language handling
                m_jaloSession.getSessionContext().setAttribute( "languageFallbackEnabled", bLANGUAGE_FALLBACK_ENABLED );
		oAktLanguage.setFallbackLanguages(listCurrentFallbacks);
                
                
                // --- reset language
                m_wm.SetLanguage(this.m_strLanguage);

		// --- Textliste
		//Element m_oICON_LIST = new Element ("ICON_LIST");
		//m_rootElement.addContent(m_oICON_LIST);

		/*
		 * // --- Liste der Produkt / Icons Relation Element m_oPRODUCT_TO_ICON_MAP = m_oPRODUCT_TO_ICON_MAP = new Element
		 * ("PRODUCT_TO_ICON_MAP"); m_rootElement.addContent(m_oPRODUCT_TO_ICON_MAP);
		 * 
		 * // --- Schleife über alle Abtriebs-Icons String strProductCode = ""; Element oPRODUCT_TO_ICON = null; for (
		 * Iterator it1 = m_Icon2ProductMap.keySet().iterator(); it1.hasNext(); ) { strProductCode = (String)it1.next();
		 * 
		 * 
		 * oPRODUCT_TO_ICON = new Element("PRODUCT_TO_ICON").setAttribute("PRODUCTCODE",strProductCode);
		 * oPRODUCT_TO_ICON.setAttribute( "ICON", (String) m_Icon2ProductMap.get(strProductCode) );
		 * m_oPRODUCT_TO_ICON_MAP.addContent(oPRODUCT_TO_ICON); }
		 */

		// --- Document erzeugen und schreiben
		m_oXmlDocument = new Document(m_rootElement);


		// --- Formatierung
		final XMLOutputter outp = new XMLOutputter(Format.getPrettyFormat());
		//outp.setIndent("  ");
		//outp.setNewlines(true);
		FileOutputStream out;

		try
		{
			out = new FileOutputStream(strXmlPath + strXmlFile, false);
			outp.output(m_oXmlDocument, out);
			out.close();
			out = null;
		}
		catch (final IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	// --- ARTICLE_TO_CATALOGGROUP_MAP>...</ARTICLE_TO_CATALOGGROUP_MAP>
	@Override
	public void BMEcatGenARTICLE_TO_CATALOGGROUP_MAP()
	{
		// --- Initialize
		String strArtikel = null;
		String strOrder = "";
		String strIcon1 = "";
		String strIcon2 = "";
		String strType = "";
		String strProdukt_normalized_url = "";
		String strCategory_normalized_url = "";
		final String strCategory = null;
		String strBitsReihe = null;
		String strBitsReiheOrder = null;
		Collection colCategories = null;
		HashMap hashPRODUCT = null;
		HashMap hashCATEGORY = null;
		// --- Schleife über alle Zuordnungen
		for (final Iterator it1 = m_hashCATALOGGROUP.keySet().iterator(); it1.hasNext();)
		{

			// --- Hole Produktdaten
			strArtikel = (String) it1.next();
			hashPRODUCT = (HashMap) m_hashCATALOGGROUP.get(strArtikel);

			// --- restliche Daten
			colCategories = (Collection) hashPRODUCT.get("colCategories");
			strType = (String) hashPRODUCT.get("type");
			strProdukt_normalized_url = (String) hashPRODUCT.get("produkt_normalized_url");
			strBitsReihe = (String) hashPRODUCT.get("BITS_REIHE");
			if (strBitsReihe == null)
			{
				strBitsReihe = "";
			}
			strBitsReiheOrder = (String) hashPRODUCT.get("BITS_REIHE_ORDER");
			if (strBitsReiheOrder == null)
			{
				strBitsReiheOrder = "";
			}

			for (final Iterator it2 = colCategories.iterator(); it2.hasNext();)
			{

				hashCATEGORY = (HashMap) it2.next();
				strCategory_normalized_url = (String) hashCATEGORY.get("category_normalized_url");
				strOrder = (String) hashCATEGORY.get("order");

				// --- Erzeuge Iconliste -------------------------------------------------
				final Element oElementICONLIST = new Element("ICONLIST");
				final HashMap iconSet1 = (HashMap) hashPRODUCT.get("ICON");
				final HashMap iconSet2 = (HashMap) hashPRODUCT.get("ICON2");
				strIcon1 = "";
				strIcon2 = "";
				if (iconSet1 != null)
				{
					for (final Iterator iterIcons = iconSet1.keySet().iterator(); iterIcons.hasNext();)
					{
                                            final String strIconTmp = (String) iterIcons.next();
                                            final HashMap hashIcon = (HashMap) iconSet1.get(strIconTmp);
                                            final Element oICON = new Element("ICON");
                                            oICON.setAttribute("type", "1");
                                            String strOrderIcon = (String) hashIcon.get("order");
                                            if (strOrderIcon == null)
                                            {
                                                    strOrderIcon = "";
                                            }
                                            oICON.setAttribute("order", strOrderIcon);
                                            oICON.addContent(strIconTmp);
                                            oElementICONLIST.addContent(oICON);

                                            // --- 1. Icon1 merken
                                            if (strIcon1.equals(""))
                                            {
                                                strIcon1 = strIconTmp;
                                            }
					}
				}
				if (iconSet2 != null)
				{
					for (final Iterator iterIcons = iconSet2.keySet().iterator(); iterIcons.hasNext();)
					{
						final String strIconTmp = (String) iterIcons.next();
						final HashMap hashIcon = (HashMap) iconSet2.get(strIconTmp);
						final Element oICON = new Element("ICON");
						oICON.setAttribute("type", "2");
						String strOrderIcon = (String) hashIcon.get("order");
						if (strOrderIcon == null)
						{
							strOrderIcon = "";
						}
						oICON.setAttribute("order", strOrderIcon);
						oICON.addContent(strIconTmp);
						oElementICONLIST.addContent(oICON);

						// --- 1. Icon2 merken
						if (strIcon2.equals(""))
						{
							strIcon2 = strIconTmp;
						}
					}
				}
				// --- Erzeuge Iconliste -------------------------------------------------

				final Element oElementCATALOGGROUP = new Element("ARTICLE_TO_CATALOGGROUP_MAP");
				oElementCATALOGGROUP.setAttribute("CATEGORY_NORMALIZED_URL", strCategory_normalized_url);
				oElementCATALOGGROUP.setAttribute("PRODUKT_NORMALIZED_URL", strProdukt_normalized_url);
				oElementCATALOGGROUP.setAttribute("type", strType);
				oElementCATALOGGROUP.setAttribute("PRODUCTORDER", strOrder);
				oElementCATALOGGROUP.setAttribute("ICON", strIcon1);
				oElementCATALOGGROUP.setAttribute("ICON2", strIcon2);
				oElementCATALOGGROUP.setAttribute("BITS_REIHE", strBitsReihe);
				oElementCATALOGGROUP.setAttribute("BITS_REIHE_ORDER", strBitsReiheOrder);

				oElementCATALOGGROUP.addContent(new Element("ART_ID").addContent(strArtikel));

				oElementCATALOGGROUP.addContent(new Element("CATALOG_GROUP_ID").addContent((String) hashCATEGORY.get("code")));
				oElementCATALOGGROUP.addContent(oElementICONLIST);
				oElementCATALOGGROUP.setAttribute("CATEGORYORDER", (String) hashCATEGORY.get("order"));

				// --- Element in Root-Knoten einfügen
				m_oNewCatalogElement.addContent(oElementCATALOGGROUP);
			}
		}
	}

	// --- Ausgabedatei / Verzeichnis erzeugen
	@Override
	public void genOutputFileName()
	{

		// TODO Auto-generated method stub
		LOG.info("WebSiteXml.genOutputFileName");

		// --- Datum ermitteln
		m_strDatum = m_wm.InitOutputDatum();

		// --- Ausgabedatum
		final Comment dateComment = new Comment(" Datenexport vom: " + m_strDatum);
		m_rootElement.addContent(dateComment);

		final String strDatum = m_strDatum;
		//m_strOutputPath = Config.getParameter("wera.exportpath") + "website/allgemein_" + strDatum;
		m_strOutputFile = m_strLanguage + "_categories.xml";

		// --- Pfad nalegen falls noch nicht vorhanden
		m_wm.createDirectory(m_strOutputPath);

		// --- Auagabepfade für XML-Daten anlegen
		m_strDatenTransferPath = "";
		//m_wm.createDirectory( m_strOutputPath + m_strDatenTransferPath );

	}

	// liefert zu einer Bitsreihen-Kennung die Reihenfolge zurück
	public String getReiheOrder(final String sReihe)
	{
		final Map BITS_REIHE_TO_ORDER = new HashMap()
		{
			{
				put("1", "10");
				put("4", "20");
				put("6", "30");
				put("23", "40");
				put("24", "50");
				put("00", "60");
				put("0", "70");
				put("3", "80");
				put("2", "90");
				put("5", "100");
				put("7", "110");
				put("19", "120");
				put("11", "130");
				put("12", "140");
				put("15", "150");
				put("16", "160");
				put("8", "170");
				put("9", "180");
				put("21", "190");
				put("22", "200");
				put("25", "210");
			}
		};
		String sBitsReiheOrder = null;
		if (sReihe != null)
		{
			sBitsReiheOrder = (String) BITS_REIHE_TO_ORDER.get(sReihe);
		}
		if (sBitsReiheOrder == null)
		{
			sBitsReiheOrder = "1000";
		}

		return sBitsReiheOrder;
	}

}

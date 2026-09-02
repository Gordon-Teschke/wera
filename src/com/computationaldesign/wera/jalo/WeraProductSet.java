package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.ProductFeature;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.jalo.type.AttributeDescriptor;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.util.Config;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.variants.jalo.VariantProduct;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;
/*
class OrderComparator implements Comparator
{
	public int compare(final Object o1, final Object o2) // --- descending order 
	{

		// --- Initialize
		Integer iValue1 = new Integer(0);
		Integer iValue2 = new Integer(0);
		final int iResult = 0;

		try
		{
			// --- Hole Values
			iValue1 = (Integer) ((Item) o1).getAttribute("order");
			iValue2 = (Integer) ((Item) o2).getAttribute("order");
			//System.out.println("iValue1="+iValue1+", iValue2="+iValue2);
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
*/
class OrderCtrlComparator implements Comparator
{
	WeraProduct m_product 		= null;
	Collection m_outputcontrols = null;

	public void setProduct(final Product product)
	{
		// --- Hole die Ausgabesteuerung
		m_product = (WeraProduct) product;
		try
		{
			m_outputcontrols = (Collection) m_product.getAttribute("outputcontrols");
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
	}

	public Object checkContaining(final Collection collection, final String stringField, final String stringMatch)
	{
		Object oResult = null;
		Item item = null;
		if (collection != null)
		{
			for (final Iterator it1 = collection.iterator(); it1.hasNext();)
			{

				item = (Item) it1.next();
				try
				{
					if (item.getAttribute(stringField).equals(stringMatch))
					{
						oResult = item;
						break;
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
			}
		}

		return oResult;
	}

	public Integer getOrderByProduct(final Item item)
	{
		// --- Initialize
		String strCode = "";
		Integer oInteger = new Integer(0);
		Outputcontrol outputcontrol = null;
		try
		{
			final ClassificationAttribute ca = ((ClassAttributeAssignment) item).getClassificationAttribute();
			strCode = (String) ca.getAttribute("code");
			outputcontrol = (Outputcontrol) checkContaining(m_outputcontrols, "code", strCode);
			if (outputcontrol != null)
			{
				oInteger = (Integer) outputcontrol.getAttribute("order");
				if (oInteger == null)
				{
					return new Integer(0);
				}
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
		// System.out.println("getOrderByProduct=" + strCode + ", order="+ oInteger.intValue());

		return oInteger;
	}

	public int compare(final Object o1, final Object o2) /* descending order */
	{

		// --- Initialize
		Integer iValue1 = new Integer(0);
		Integer iValue2 = new Integer(0);

		// --- Hole Values
		iValue1 = getOrderByProduct((Item) o1);
		iValue2 = getOrderByProduct((Item) o2);

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


@SuppressWarnings("PMD")
public class WeraProductSet extends GeneratedWeraProductSet
{
        protected static CatalogVersion m_catalogVersion = null;
        protected static boolean m_isLiveCatalog = false;
        
	private static Logger LOG = Logger.getLogger(WeraProductSet.class.getName());


	public WeraProductSet()
	{
		// empty

	}

        public void setCatalogVersion( final CatalogVersion cv, boolean isLiveCatalog ) {
            this.m_catalogVersion = cv;
            this.m_isLiveCatalog = isLiveCatalog;
        }
        
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes)
			throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		final Item item = super.createItem(ctx, type, allAttributes);
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	@Override
	public void beforeSave(final Map values)
	{
		LOG.info("+++ WeraProductSet::beforeSave (START) -code" + getCode());
		LOG.info("+++ WeraProductSet::beforeSave  (ENDE) -code" + getCode());
	}

	@Override
	public void afterSave(final Map values)
	{
		LOG.info("+++ WeraProductSet::afterSave (START) MUST BE IMPLEMENTED!!!!!!!! -code" + getCode());
/*
		final List items = getLinkedItems( 
			JaloSession.getCurrentSession().getSessionContext(),
			true,
			"WeraProductSetVariants",
			null,
			true,
			true
		);
		System.out.println("WeraProductSetVariants="+items);
			for (final Iterator iter0 = items.iterator(); iter0.hasNext();)
			{
				// each WeraVarianteSet is a wrapper around the standard WeraVariante
				final Item item = (Item) iter0.next();
				System.out.println("WeraProductSetVariants.item="+item);
			}
		final List variants = getLinkedItems( 
			JaloSession.getCurrentSession().getSessionContext(),
			true,
			"variants",
			null,
			true,
			true
		);
		System.out.println("variants="+variants);
			for (final Iterator iter1 = variants.iterator(); iter1.hasNext();)
			{
				// each WeraVarianteSet is a wrapper around the standard WeraVariante
				final Item item = (Item) iter1.next();
				System.out.println("variants.item="+item);
			}
*/			
		// --- Anlegen der Outputcontrols
		try
		{
			WeraManager.getInstance().createOutputcontrolByProduct(this);

			// --- Product / Category Zusatzinformationen
			WeraManager.getInstance().createCategory2ProductExt(this, Config.getParameter("wera.mastercatalogversion"));

			// --- Index Alphanum. (START) ------------------------------------------------------------------------------------------------
			//
			updateArtikelNrIndex();

		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final JaloBusinessException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Bearbeite Bilder
		checkMedia(values);

		LOG.info("+++ WeraProductSet::afterSave (ENDE) -code" + getCode());
	}

	// --- Initialisierung der Grunddaten f�r WERA
	@Override
	public void setEssentialData()
	{
		// --- UNIT
		final List aResult = JaloSession
				.getCurrentSession()
				.getFlexibleSearch()
				.search("select {pk},{code},{name[de]} from {Unit} WHERE code='pieces'", null, Collections.singletonList(Unit.class),
						true, // fail on unknown fields
						true, // don't need total
						0, 1 // range
				).getResult();
		Unit fnResult = null;
		if (aResult != null && aResult.isEmpty() == false)
		{
			fnResult = (Unit) aResult.get(0);
		}
		if (fnResult != null)
		{
			setUnit(fnResult);
			WeraManager.getInstance().setAttribute(this, "contentUnit", fnResult);
		}
		// --- UNIT

		// --- Produkt Defaultwerte initialisieren
		WeraManager.getInstance().setAttribute(this, "aktiv", new Boolean(true));

		WeraManager.getInstance().setAttribute(this, "manufacturerName", new String("WERA"));
	}

	public ArrayList generateWeraProductSetData()
	{
                LOG.info("generateWeraProductSetData() called.");
		Collection colWeraVarSet = null;
		final ArrayList listVariants = new ArrayList();
		final ArrayList listVarianteSet = new ArrayList();
		try
		{
			// for WeraProductSets, the "variants" field is a collection of WeraVarianteSet
			colWeraVarSet = (Collection) getAttribute("variants");

			// --- Sortierung nach Reihe
			// --- Sortiere, nach "order"
			Collection colWeraVarSetResult = new ArrayList();
			colWeraVarSetResult.addAll(colWeraVarSet);
			if (colWeraVarSetResult != null && colWeraVarSetResult.size() > 0)
			{
				Collections.sort((List) colWeraVarSetResult, new OrderComparator());
			}
			
			
			// --- Schleife �ber alle Zeilen
			for (final Iterator iter = colWeraVarSetResult.iterator(); iter.hasNext();)
			{
				// each WeraVarianteSet is a wrapper around the standard WeraVariante
				final WeraVarianteSet weraVarSet = (WeraVarianteSet) iter.next();
				final WeraVariante myWeraVariante = (WeraVariante) weraVarSet.getAttribute("weravariants");
				listVarianteSet.add(weraVarSet);
				listVariants.add(myWeraVariante);
			}
		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}
                LOG.info("generateWeraProductSetData() finished.");
		// call the wera product method to calculate variant structure data
		return _genCADataForVariantList(listVariants, listVarianteSet);
	}



	Collection getProductFeatures(final Product product, final ClassificationAttribute classificationAttribute)
			throws JaloInvalidParameterException, JaloSecurityException
	{
		//System.out.println("WeraProductSet::getProductFeatures() called");

		final Collection collectionResult = new ArrayList();

		// --------------------------------------------------------
		// productFeatures
		// --- Hole alle ProductFeatures f�r das Product
		final Collection colProductFeatures = (Collection) product.getAttribute("features");
		ProductFeature productFeature = null;
		for (final Iterator it1 = colProductFeatures.iterator(); it1.hasNext();)
		{
			// --- N�chstes ProductFeature
			productFeature = (ProductFeature) it1.next();

			// --- Pr�fe, ob das ProductFeature zum
			if (classificationAttribute.getCode().toString().equals(productFeature.getQualifier().toString()))
			{
				// -- collect
				collectionResult.add(productFeature);
			}
		}

		return collectionResult;
	}




	private String getExtendedCode()
	{
		String extCode = null;
		String prefix = "";
		String postfix = "";
		String code = "";

		try
		{
			prefix = (String) getAttribute("lagerNr");
			postfix = (String) getAttribute("variantenNr");

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

		code = getArtnr();
		extCode = prefix + "<span class='codebig'>" + code + "</span>" + postfix;
		return extCode;
	}


	public Collection<Collection<String>> generateOutputCells(final Collection<WeraMedia> colSetIcons)
	{
		final Collection<Collection<String>> colRows = new ArrayList();


		// --- Zur Vermeidung von Icon Duplikaten werden die FileNames der Icons im Set gespeichert
		HashSet setIconCodes = new HashSet();
		// --- Spalten f�r Code, Icon, Artikelcode, Values, VPE, Preis
		int iProdCnt = 0;
		final Collection colData = this.m_colWeraProductSetData;
		// --- F�r jedes Produkt eine Row
		for (final Iterator iterProduct = colData.iterator(); iterProduct.hasNext();)
		{
			final Collection<String> colCells = new ArrayList();
			String strRow = "";
			final HashMap productHash = (HashMap) iterProduct.next();
			final Collection colVariants = (Collection) productHash.get("colHashArtikel");
			iProdCnt++;

			// Col #1 (with 3 subcolumn for variantennr, code and lagernr)
			if (iProdCnt == 1)
			{

				colCells.add(this.getVariantenNr());
				colCells.add(this.getArtnr());
				colCells.add(this.getLagerNr());
			}
			else
			{
				colCells.add("");
				colCells.add("");
				colCells.add("");
			}

			// Col #2
                        String med1url = "";
                        String med2url = "";
			// final WeraMedia med1 = (WeraMedia) productHash.get("icons1");
			final Collection<WeraMedia> colWeraMediaIcon1 = (Collection<WeraMedia>) productHash.get("icons1_collection");
			final WeraMedia med2 = (WeraMedia) productHash.get("icons2");
			final Collection<WeraMedia> colWeraMediaIcon2 = (Collection<WeraMedia>) productHash.get("icons2_collection");
			// Icons sammeln für Collection Rückgabe
                        if ( colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1 ) {
                            
                            // --- iterate on icon-collection
                            for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)
                            {
                                // --- Hole Artikel / Variante
                                WeraMedia oIcon  = (WeraMedia) itIconMedias.next();
                                if (oIcon != null)
                                {
                                    if (setIconCodes.add(oIcon.getRealFileName()))
                                    {
                                        colSetIcons.add(oIcon);
                                    }
                                }
                                med1url += (oIcon == null ? "&nbsp;" : "<img src='" + oIcon.getURL() + "' border='0' width='12'>");
                            }
                            
                        } 
                        
                        /*
                         * *** Icon2 f�r Setausgabe ignorieren, es werden nur Antriebe angezeigt ***
                         * 
                         * if ( med2 != null ) { if ( setIconCodes.add (med2.getRealFileName() ) ) { colSetIcons.add(med2); } }
                         */
                        med2url = (med2 == null ? "&nbsp;" : "<img src='" + med2.getURL() + "' border='0' width='12'>");
                        

			colCells.add(med1url);
			colCells.add(med2url);


			// Col #3
			// colCells.add((String) productHash.get("code"));
                        colCells.add((String) productHash.get("artikelnr_index"));


			// Col #4

			for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();)
			{
				final HashMap variantHash = (HashMap) iterVariant.next();
				String myValue = (String) variantHash.get("value");
				final Collection myFootnotes = (Collection) variantHash.get("footnotes");
				if (myFootnotes != null)
				{
					for (final Iterator itft = myFootnotes.iterator(); itft.hasNext();)
					{
						final Integer iFootnoteIndex = (Integer) itft.next();
						if (iFootnoteIndex != null)
						{
							myValue += "<sup>" + iFootnoteIndex + ") </sup>";
						}
					}
				}
				strRow += "<nobr>" + myValue + "</nobr>";

				if (iterVariant.hasNext())
				{
					strRow += "; ";
				}
			}
			colCells.add(strRow);


			// Col #5
			String stringInfo = "";
			if (iProdCnt == 1)
			{
				try
				{
					final Integer iInfo = (Integer) getAttribute("contentQuantity");
					if (iInfo != null)
					{
						stringInfo = iInfo.toString();
					}

				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
			colCells.add(stringInfo);


			// Col #6
			colCells.add(getName());

                        // Col #7 and #8
                        final Date dValidFrom = this.getValid_from();
                        Date dNow = new Date();
                        if ( dValidFrom != null ) {
                            colCells.add( dValidFrom.toLocaleString() );
                            colCells.add( dValidFrom.before(dNow) ? "1" : "0" );
                            
                        } else {
                            colCells.add( "" );
                            colCells.add( "1" );
                        }


			// add cells to row
			colRows.add(colCells);


		}


		setIconCodes = null;
		return colRows;
	}

	// --- generiert HTML Table rows f�r die Set Artikeltabelle und liefert
	//     Collection von WeraMedias zur�ck, die anstelle des normalen icons1
	//     bei Sets eingeblendet werden m�ssen
	public Collection generateOutputRows(final Collection colSetIcons)
	{

		final Collection colHTMLRows = new ArrayList();
		// --- Spalten f�r Code, Icon, Artikelcode, Values, VPE, Preis
		int iProdCnt = 0;

		// --- Zur Vermeidung von Icon Duplikaten werden die FileNames der Icons im Set gespeichert
		HashSet setIconCodes = new HashSet();

		final Collection colData = this.m_colWeraProductSetData;
		// --- F�r jedes Produkt eine Row
		for (final Iterator iterProduct = colData.iterator(); iterProduct.hasNext();)
		{
			String strRow = "";
			final HashMap productHash = (HashMap) iterProduct.next();
			final Collection colVariants = (Collection) productHash.get("colHashArtikel");
			iProdCnt++;

			// Col #1
			if (iProdCnt == 1)
			{
				strRow += "<td class='datacode' style='vertical-align:top;'>" + getExtendedCode() + "</td>";
			}
			else
			{
				strRow += "<td class='datacode' style='vertical-align:top;'>&nbsp;</td>";
			}

			// Col #2
                        String med1url = "";
                        String med2url = "";
			// final WeraMedia med1 = (WeraMedia) productHash.get("icons1");
			final Collection<WeraMedia> colWeraMediaIcon1 = (Collection<WeraMedia>) productHash.get("icons1_collection");
			final WeraMedia med2 = (WeraMedia) productHash.get("icons2");
			final Collection<WeraMedia> colWeraMediaIcon2 = (Collection<WeraMedia>) productHash.get("icons2_collection");

			// Icons sammeln f�r Collection R�ckgabe
			// Icons sammeln f�r Collection R�ckgabe
                        if ( colWeraMediaIcon1 != null && colWeraMediaIcon1.size() >= 1 ) {
                            
                            // --- iterate on icon-collection
                            for (final Iterator itIconMedias = colWeraMediaIcon1.iterator(); itIconMedias.hasNext();)
                            {
                                // --- Hole Artikel / Variante
                                WeraMedia oIcon  = (WeraMedia) itIconMedias.next();
                                if (oIcon != null)
                                {
                                    if (setIconCodes.add(oIcon.getRealFileName()))
                                    {
                                        colSetIcons.add(oIcon);
                                    }
                                }
                                med1url += (oIcon == null ? "&nbsp;" : "<img src='" + oIcon.getURL() + "' border='0' width='12'>");
                            }
                            
                        }
                        
			/*
			 * *** Icon2 f�r Setausgabe ignorieren, es werden nur Antriebe angezeigt ***
			 * 
			 * if ( med2 != null ) { if ( setIconCodes.add (med2.getRealFileName() ) ) { colSetIcons.add(med2); } }
			 */
			med2url = (med2 == null ? "&nbsp;" : "<img src='" + med2.getURL() + "' border='0' width='12'>");



			strRow += "<td class='dataset'>" + med1url + "</td>";
			strRow += "<td class='dataset'>" + med2url + "</td>";

			// Col #3
			strRow += "<td class='dataleft' nowrap><b>" + (String) productHash.get("code") + "</b></td>";


			// Col #4
			strRow += "<td class='dataleft'>";
			for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();)
			{
				final HashMap variantHash = (HashMap) iterVariant.next();
				String myValue = (String) variantHash.get("value");
				final Collection myFootnotes = (Collection) variantHash.get("footnotes");
				if (myFootnotes != null)
				{
					for (final Iterator itft = myFootnotes.iterator(); itft.hasNext();)
					{
						final Integer iFootnoteIndex = (Integer) itft.next();
						if (iFootnoteIndex != null)
						{
							myValue += "<sup>" + iFootnoteIndex + ") </sup>";
						}
					}
				}
				strRow += "<nobr>" + myValue + "</nobr>";

				if (iterVariant.hasNext())
				{
					strRow += "; ";
				}
			}
			strRow += "</td>";


			// Col #5
			String stringInfo = "";
			if (iProdCnt == 1)
			{
				try
				{
					final Integer iInfo = (Integer) getAttribute("contentQuantity");
					if (iInfo != null)
					{
						stringInfo = iInfo.toString();
					}

				}
				catch (final Exception e)
				{
					e.printStackTrace();
				}
			}
			strRow += "<td class='dataset'>" + stringInfo + "</td>";

			// Col #6
			strRow += "<td class='dataset'>&nbsp;</td>";

			LOG.info("generateOutputRows(): adding row " + strRow);
			colHTMLRows.add(strRow);
		}


		setIconCodes = null;
		return colHTMLRows;

	}


	public static Set getAllInstances()
	{


		final ComposedType WeraProductSetType = TypeManager.getInstance().getComposedType(WeraProductSet.class);

		return WeraProductSetType.getAllInstances();

	}

	// ********************************************************************************
	// --- Hole alle angelegten Datenfelder (Portalclassifizierung)
	public static HashMap getAllDatenfelderAsHash()
	{

		// --- Initiliaze
		final HashMap setMerkmale = new HashMap();

		// --- Schleife �ber alle Felder
		final Collection descriptors = TypeManager.getInstance().getComposedType(WeraProductSet.class).getAttributeDescriptors();
		for (final Iterator it1 = descriptors.iterator(); it1.hasNext();)
		{
			// --- Hole Attribut-describtor
			final AttributeDescriptor oAD = (AttributeDescriptor) it1.next();
			if (oAD.isWritable())
			{

				// --- Filter nach Typen
				if (!(oAD.getAttributeType() instanceof de.hybris.platform.jalo.type.AtomicType))
				{
					continue;
				}

				final HashMap hMerkmal = new HashMap();
				hMerkmal.put("name", oAD.getQualifier());
				hMerkmal.put("visibility", "Artikel");
				if (oAD.getName() == null)
				{
					hMerkmal.put("description", oAD.getQualifier());
				}
				else
				{
					hMerkmal.put("description", oAD.getName());
				}
				hMerkmal.put("type", oAD.getAttributeType().getName());
				hMerkmal.put("namespace", "Artikel@hybris/core");
				setMerkmale.put(oAD.getQualifier(), hMerkmal);
			}
		}

		return setMerkmale;
	}

	// --- Name des Produktbildes
	public String getPreview_etikett_image(final SessionContext ctx) {
		
		// --- initialize
		String strPreview		= "";
		
		// --- hole den bildnamens
		String strBildname		= (String) WeraManager.getInstance().getAttribute(this, "etikett_image");

		// --- ist ein Bildname vorhanden
		if ( strBildname != null && !strBildname.trim().equals("") ) {
			
			// --- gen. preview link
			strPreview	= strBildname;
		}
		
		return strPreview;
	}
	
	public String getDWHInfo ( final String lang_iso, final String field_name ) {
	    String sContentInfo = "";
	    // Connection myCon = this._init_datawarehouse_db();
	    Connection myCon = WeraManager.getDWHConnection();

	    String product_id = this.getLagerNr() + this.getArtnr() + this.getVariantenNr();
	    try {
		PreparedStatement preparedStmt = myCon.prepareStatement("select "+field_name+" from products_merged where product_id=? and lang_iso=?");
		preparedStmt.setString(1, product_id);
		preparedStmt.setString(2, lang_iso);
		final ResultSet rsContentInfo = preparedStmt.executeQuery();
		if (rsContentInfo.next()) {
		    sContentInfo = rsContentInfo.getString( field_name );
		}
	    } catch (final SQLException e) {
		LOG.error("ERROR retrieving content_info from products_merged for product_id="+product_id+" and lang_iso="+lang_iso);
	    }

	    try {
		if (!myCon.isClosed()) {
		    myCon.close();
		    // LOG.info("getDWHInfo(): mysql connection closed.");
		} else {
		    LOG.warn("getDWHInfo(): mysql connection already closed or invalid!");
		}
	    } catch (final SQLException e) {
		LOG.error("getDWHInfo(): error trying to close mysql connection.");
	    }
	    return sContentInfo;	
	}	

	/**
	 * pr�fe ob eine dr Varianten die Marketingtexte ausspielen soll
	 *
	 * @return null - keine Ausspielung|WeraVariante - verwendete Weravariante
	 */
	public Item getExportmarktingtext() {
		
		// --- Debug
		LOG.info ("weraproductset.getExportmarktingtext => für product="+ getCode());
		
		// --- Initialize
		final WeraManager wm = WeraManager.getInstance();

		// --- Darf der Marketingtext ausgegeben werden
		final Boolean is_marketing_representative = (Boolean)wm.getAttribute(this, "is_marketing_representative");

		// --- verwende hier die Marktingtexte
		if ( is_marketing_representative != null && is_marketing_representative.booleanValue() ) {
// LOG.info ("is_marketing_representative = true");
			return this;
		}
// LOG.info ("is_marketing_representative = false");

		return null;
	}

	/**
	 * Vorschau Bulletpoints aus Variante - Jalo
	 * 
	 * @Override
	 * @param SessionContext ctx
	 * @return String
	 */
	public String getBulletpoints_print_selection_preview(final SessionContext ctx) {

		// --- preset
		String strResult				= "";
		final WeraManager wm			= WeraManager.getInstance();
		WeraVariante weraproductvariant	= null;


		// --- Darf der Bulletpoint ausgegeben werden
		final Boolean is_bulletpoint_representative = (Boolean)wm.getAttribute(this, "is_bulletpoint_representative");

		// --- Daten anzeigen
		if ( is_bulletpoint_representative != null && is_bulletpoint_representative.booleanValue() ) {
			
			// --- bullets
			Collection<Textbaustein> oBulletsTextbausteine	= (Collection)wm.getAttribute(this, "weraproductset2bulletpoints");
			if ( oBulletsTextbausteine != null && oBulletsTextbausteine.size() > 0 ) {

				for ( Textbaustein bulletpoint : oBulletsTextbausteine ) {

					// --- vorschau generieren
					strResult	+= "\r\n" + bulletpoint.getCode() 
											+ "\r\n" + bulletpoint.getText()
											+ "\r\n----------------------";

				}
			}
			
		}
		
		return strResult;
	}

	/**
	 * pr�fe ob eine dr Varianten die Marketingtexte ausspielen soll
	 *
	 * @return null - keine Ausspielung|WeraVariante - verwendete Weravariante
	 */
	public Item getExportbulletpoints() {
		
		// --- Debug
		LOG.info ("weraproductset.getExportbulletpoints => f�r product="+ getCode());

		// --- Initialize
		final WeraManager wm = WeraManager.getInstance();
		Item resultItem					= null;

		// --- Hole alle Produkte
		// --- Hole Aktiv-Flag Localized
		final Boolean is_bulletpoint_representative = (Boolean)wm.getAttribute(this, "is_bulletpoint_representative");
		if ( is_bulletpoint_representative != null && is_bulletpoint_representative.booleanValue() ) {
			resultItem	= this;
		}

		return resultItem;
	}
	
}

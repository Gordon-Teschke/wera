package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.model.CatalogVersionModel;
import de.hybris.platform.core.PK;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.apache.commons.lang.StringUtils;

import org.apache.log4j.Logger;


public class WeraProductSetinSet extends GeneratedWeraProductSetinSet {
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger(WeraProductSetinSet.class.getName());

	public final HashMap m_Contentmap = new HashMap();
	public final ArrayList m_colWeraProductSetinSetData = new ArrayList();
	// public static ArrayList m_colWeraProductSetData = null;
	private WeraProduct m_oWeraMainProduct = null;
	Set<String> setOfInheritancedAttributes = new HashSet<String>(Arrays.asList("description1", "description2", "description3",
			"icons1", "icons2", "otherproduct_pictures", "extimages", "productReferences", "weblinks", "sortimenttippref",
			"anwenderimageref2weraproduct"));

	class SiSComponentOrderComparator implements Comparator {
		@Override
		public int compare(final Object o1, final Object o2) /* descending order */ {
			Integer i1 = 0;
			Integer i2 = 0;
			if (o1 instanceof WeraVarianteVariants) {
				i1 = ((WeraVarianteVariants) o1).getOrder();
			} else {
				i1 = ((WeraProductSetVariants) o1).getOrder();
			}
			if (o2 instanceof WeraVarianteVariants) {
				i2 = ((WeraVarianteVariants) o2).getOrder();
			} else {
				i2 = ((WeraProductSetVariants) o2).getOrder();
			}
			if (i1 == null) {
				i1 = 0;
			}
			if (i2 == null) {
				i2 = 0;
			}
			return i1.compareTo(i2);
		}
	}

	public WeraProductSetinSet() {
		// --- initialize
		m_oWeraMainProduct = null;
	}

	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes)
			throws JaloBusinessException {
		// business code placed here will be executed before the item is created
		// then create the item
		final Item item = super.createItem(ctx, type, allAttributes);
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	/*
	 * @Override public Object setLocalizedProperty(SessionContext ctx, String strAttributeName, Object value) {
	 * 
	 * LOG.info( "setLocalizedProperty (ctx) Property=" + strAttributeName );
	 * 
	 * Object oResultProperty = null;
	 * 
	 * try {
	 * 
	 * // --- get allways the current value as fallback if ( getMainProduct() != null ) {
	 * 
	 * // --- pr�fen ob der value vom Hauptprodukt geholt werden soll if ( setOfInheritancedAttributes.contains(
	 * strAttributeName ) ) {
	 * 
	 * LOG.info( "setLocalizedProperty.get from Hauptprodukt Property=" + strAttributeName );
	 * 
	 * // --- get the name of Mainprodct Object oResult = (Object)m_oWeraMainProduct.getLocalizedProperty(
	 * strAttributeName ); if ( oResult != null ) { LOG.info(
	 * "setLocalizedProperty.get save-hauptprodukt-value Property=" + strAttributeName ); oResultProperty =
	 * super.setLocalizedProperty( ctx, strAttributeName, oResult ); }
	 * 
	 * } else{
	 * 
	 * LOG.info( "setLocalizedProperty (ctx) (mainprodukt) get save-direkt Property=" + strAttributeName );
	 * 
	 * // --- set attribute value oResultProperty = super.setLocalizedProperty( ctx, strAttributeName, value );
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * LOG.info( "setLocalizedProperty (ctx) save-direkt Property=" + strAttributeName );
	 * 
	 * // --- set attribute value oResultProperty = super.setLocalizedProperty( ctx, strAttributeName, value );
	 * 
	 * } // --- if ( getMainProduct() != null ) {
	 * 
	 * } catch (Exception e) {}
	 * 
	 * return oResultProperty; }
	 */
	/*
	 * @Override public void setAttribute(SessionContext ctx, String strAttributeName, Object value) {
	 * 
	 * LOG.info( "setAttribute (ctx) attribute=" + strAttributeName );
	 * 
	 * try {
	 * 
	 * // --- get allways the current value as fallback if ( getMainProduct() != null ) {
	 * 
	 * // --- pr�fen ob der value vom Hauptprodukt geholt werden soll if ( setOfInheritancedAttributes.contains(
	 * strAttributeName ) ) {
	 * 
	 * LOG.info( "setAttribute.get from Hauptprodukt attribute=" + strAttributeName );
	 * 
	 * // --- get the name of Mainprodct Object oResult = (Object)m_oWeraMainProduct.getAttribute( strAttributeName ); if
	 * ( oResult != null ) { LOG.info( "setAttribute.get save-hauptprodukt-value attribute=" + strAttributeName );
	 * super.setAttribute( ctx, strAttributeName, oResult ); }
	 * 
	 * } else{
	 * 
	 * LOG.info( "setAttribute (ctx) (mainprodukt) get save-direkt attribute=" + strAttributeName );
	 * 
	 * // --- set attribute value super.setAttribute( ctx, strAttributeName, value );
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * LOG.info( "setAttribute (ctx) save-direkt attribute=" + strAttributeName );
	 * 
	 * // --- set attribute value super.setAttribute( ctx, strAttributeName, value );
	 * 
	 * } // --- if ( getMainProduct() != null ) {
	 * 
	 * } catch (Exception e) {}
	 * 
	 * }
	 * 
	 * @Override public void setAttribute(String strAttributeName, Object value) {
	 * 
	 * LOG.info( "setAttribute attribute=" + strAttributeName );
	 * 
	 * try {
	 * 
	 * // --- get allways the current value as fallback if ( getMainProduct() != null ) {
	 * 
	 * // --- pr�fen ob der value vom Hauptprodukt geholt werden soll if ( setOfInheritancedAttributes.contains(
	 * strAttributeName ) ) {
	 * 
	 * LOG.info( "setAttribute.get from Hauptprodukt attribute=" + strAttributeName );
	 * 
	 * // --- get the name of Mainprodct Object oResult = (Object)m_oWeraMainProduct.getAttribute( strAttributeName ); if
	 * ( oResult != null ) { LOG.info( "setAttribute.get save-hauptprodukt-value attribute=" + strAttributeName );
	 * super.setAttribute( strAttributeName, oResult ); }
	 * 
	 * } else{
	 * 
	 * LOG.info( "setAttribute (mainprodukt) get save-direkt attribute=" + strAttributeName );
	 * 
	 * // --- set attribute value super.setAttribute( strAttributeName, value );
	 * 
	 * }
	 * 
	 * } else {
	 * 
	 * LOG.info( "setAttribute save-direkt attribute=" + strAttributeName );
	 * 
	 * // --- set attribute value super.setAttribute( strAttributeName, value );
	 * 
	 * } // --- if ( getMainProduct() != null ) {
	 * 
	 * } catch (Exception e) {}
	 * 
	 * }
	 */

	@Override
	public Object getAttribute(final SessionContext ctx, final String strAttributeName) {

		// --- initialize
		Object oResult = null;

		try {
			// --- get allways the current value as fallback
			oResult = super.getAttribute(ctx, strAttributeName);

			// --- pr�fen ob das attribute vom Hauptprodukt geholt werden soll
			if (setOfInheritancedAttributes.contains(strAttributeName)) {
				// --- pr�fe ob ein Hauptprodukt exisitert
				if (getMainProduct() != null) {
					// --- get the name of Mainprodct
					oResult = m_oWeraMainProduct.getAttribute(ctx, strAttributeName);
					// LOG.info( "m_oWeraMainProduct.getAttribute. see=" + strAttributeName );
				}
			}
		} catch (final Exception e) {
		}

		return oResult;
	}

	@Override
	public Object getAttribute(final String strAttributeName) {
		// --- initialize
		Object oResult = null;
		// LOG.info("getAttribute. see=" + strAttributeName);

		try {
			// --- get allways the current value as fallback
			oResult = super.getAttribute(strAttributeName);

			// --- pr�fen ob das attribute vom Hauptprodukt geholt werden soll
			if (setOfInheritancedAttributes.contains(strAttributeName)) {
				// --- pr�fe ob ein Hauptprodukt exisitert
				if (getMainProduct() != null) {
					// --- get the name of Mainprodct
					oResult = m_oWeraMainProduct.getAttribute(strAttributeName);
					// LOG.info( "m_oWeraMainProduct.getAttribute. see=" + strAttributeName );
				}
			}
		} catch (final Exception e) {
		}


		return oResult;
	}

	/**
	 * holt alle feature-icons vom Hauptprodukt oder von dieser SB-Variante
	 *
	 * @return HashMap
	 */
	@Override
	public HashMap getFeatureIconsByBooleanProperties() {

		// LOG.info("WeraProductSetinSet.getFeatureIconsByBooleanProperties");

		// --- initialize
		Object oResult = null;

		try {

			// --- pr�fe ob ein Hauptprodukt exisitert
			if (getMainProduct() != null) {
				// --- get the name of Mainprodct
				oResult = m_oWeraMainProduct.getFeatureIconsByBooleanProperties();
			}

		} catch (final Exception e) {
		}

		// --- get feature from current sb-variant
		if (oResult == null) {
			// --- get allways the current value as fallback
			oResult = super.getFeatureIconsByBooleanProperties();
		}

		return (HashMap) oResult;
	}

	/**
	 * holt das hauptprodukt dieser SB-Variante
	 *
	 * @return WeraProduct
	 */
	public WeraProduct getMainProduct() {

		try {
			if (true /* m_oWeraMainProduct == null */) {
				m_oWeraMainProduct = (WeraProduct) super.getAttribute("weraproducts_relation");
			}
			//if (m_oWeraMainProduct != null)
			//{
			//	LOG.info("Hauptprodukt aktiv. see=" + m_oWeraMainProduct.getCode());
			//}

		} catch (final Exception e) {
		}

		return m_oWeraMainProduct;
	}

	// --- Name des Produktbildes
	@Override
	public String getPreview_variant_image_amazon(final SessionContext ctx) {

		// --- initialize
		String strPreview = "";

		// --- hole den bildnamens
		String strBildname = (String) WeraManager.getInstance().getAttribute(this, "variant_image_amazon");

		// --- ist ein Bildname vorhanden
		if (strBildname != null && !strBildname.trim().equals("")) {

			// --- gen. preview link
			strPreview = strBildname;
		}

		return strPreview;
	}

	/**
	 * get sortet collectionn of SIS-Components
	 *
	 * @return Collection colComponents
	 */
	public Collection getWeraProductSetDataComponents() {


		final Collection colWeraProductSet = (Collection) getAttribute("weraproductsetvariants_qual");
		final Collection colWeraVariante = (Collection) getAttribute("weravariantevariants_qual");
		Collection colComponents = new ArrayList();
		colComponents.addAll(colWeraVariante);
		colComponents.addAll(colWeraProductSet);
		Collections.sort((List) colComponents, new SiSComponentOrderComparator());

		return colComponents;
	}


	/**
	 * enthält das Element weitere SIS
	 *
	 * @return boolean product_containsSIS
     */
	public boolean containsSIS() {

		// --- preset
		final WeraManager wm 				= WeraManager.getInstance();
		WeraProductSet weraproductset		= null;
		boolean product_containsSIS			= false;


		// --- hole alle enthaltenen sätze
		final Collection colWeraProductSet = (Collection) wm.getAttribute(this, "weraproductsetvariants_qual");

		// --- iterate on all
		for (final Iterator iterPComponent = colWeraProductSet.iterator(); iterPComponent.hasNext();) {

			// --- hole den Satz / SIS
			Item oContainer = (Item)iterPComponent.next();
			weraproductset = (WeraProductSet) wm.getAttribute(oContainer,"weraproductsets");

			// --- ist das Produtct ein weiteres SIS?
			if (weraproductset instanceof WeraProductSetinSet ) {
				product_containsSIS	= true;
				break;
			}
		}

		return product_containsSIS;
	}

	/**
	 * prüft ob beim SIS die Artikelnummer anstellle der lokalisierten Artikelnummer
	 * ausgegeben werden soll
	 *
	 * ItemName=sis_prioritize_code
	 *
	 * @return Boolean
     */

	public Boolean prioritizeCodeNummer() {

		return getBooleanAttribute ( "sis_prioritize_code" );
	}

	/**
	 * prüft ob das SIS ein Display ist
	 *
	 * ItemName=ist_display
	 *
	 * @return Boolean
     */
	public Boolean isDisplay() {

		return getBooleanAttribute ( "ist_display" );
	}


	@Override
	public ArrayList generateWeraProductSetData()
	{
		// LOG.info("generateWeraProductSetData() called for product set (SiS) "+this.getCode());
		final WeraManager wm = WeraManager.getInstance();
		Collection colWeraVarSet = null;
		final ArrayList listVariants = new ArrayList();
		final ArrayList listVarianteSet = new ArrayList();
		ArrayList listResultList = null;

		// --- prüfe auf Display
		Boolean bIstDisplay = this.isDisplay();
                
		try
		{
			// --- Initialize
                    
			if (m_colWeraProductSetData == null)
			{
				m_colWeraProductSetData = new ArrayList();
			}
                    
			m_colWeraProductSetinSetData.clear();


			// --- Hole alle ProductSets
			Collection colComponents	= getWeraProductSetDataComponents();

			//for (final Iterator iterPSet = colWeraProductSet.iterator(); iterPSet.hasNext();)
                        for (final Iterator iterPComponent = colComponents.iterator(); iterPComponent.hasNext();)
			{
                                Integer oIntegerVPE = null;
                                WeraProductSetVariants weraproductsetvariants = null;
                                WeraProductSet weraproductset = null;
                                WeraVarianteVariants weravariantevariants = null;
                                WeraVariante weravariante = null;
                                Object oComponent = iterPComponent.next();
                                boolean isVariant = oComponent instanceof WeraVarianteVariants;
                                boolean isComponentSiS = false;
                                if ( isVariant ) {
                                    weravariantevariants = (WeraVarianteVariants) oComponent;
                                    weravariante = (WeraVariante) weravariantevariants.getAttribute("weravariants");
                                    oIntegerVPE = (Integer) weravariantevariants.getAttribute("vpe");
                                } else {
                                    weraproductsetvariants = (WeraProductSetVariants) oComponent;
                                    weraproductset = (WeraProductSet) weraproductsetvariants.getAttribute("weraproductsets");
                                    isComponentSiS = ( weraproductset instanceof WeraProductSetinSet );
                                    oIntegerVPE = (Integer) weraproductsetvariants.getAttribute("vpe");
                                }
				if ( weraproductset == null && weravariantevariants == null ) {
					continue;
				}
				Integer oIntegercontentQuantity = (Integer) this.getAttribute("contentQuantity");
				if (oIntegercontentQuantity == null)
				{
					oIntegercontentQuantity = new Integer(0);
				}
				
				if (oIntegerVPE == null)
				{
					oIntegerVPE = new Integer(0);
				}
				// --- Daten speichern
				m_colWeraProductSetData.clear();
				listResultList = new ArrayList();
				listVarianteSet.clear();
				listVariants.clear();
                                
				final HashMap hData = new HashMap();
				hData.put("vpe", oIntegerVPE.toString());
				hData.put("contentQuantity", oIntegercontentQuantity ); // Menge bei Produktset
                                hData.put("SiSComponents", new ArrayList() );
                                if ( weraproductsetvariants != null ) {
                                    hData.put("ean", StringUtils.trimToEmpty(  (String) weraproductset.getAttribute("ean") ) );
                                    hData.put("ean_us", StringUtils.trimToEmpty(  (String) weraproductset.getAttribute("ean_us") ) );
                                    hData.put("varnr", wm.getAttribute(weraproductset, "variantenNr"));
                                    hData.put("artnr", wm.getAttribute(weraproductset, "artnr"));
                                    hData.put("code", weraproductset.getCode());
                                    //hData.put("artikelnr_index", "(AIND SET) " + weraproductset.getArtikelnr_index());
                                    hData.put("artikelnr_index", weraproductset.getArtikelnr_index());
                                    hData.put("lagernr", wm.getAttribute(weraproductset, "lagerNr"));
                                    hData.put("name", wm.getAttribute(weraproductset, "name"));
                                    hData.put("isSiS", isComponentSiS ? "1" : "0");
                                    hData.put("isVariant", "0");
                                    hData.put("pk", weraproductset.getPK());
                                    
                                    // determine visibility of components of type set
                                    byte iVisibility =  wm.getVisibilityForCatalog( weraproductset );
                                    hData.put("visibility", String.valueOf(iVisibility) );
                                    
                                    if (isComponentSiS) {
                                        ((WeraProductSetinSet) weraproductset).generateWeraProductSetData();
                                        // LOG.info("set "+weraproductset.getCode()+ "'s m_colWeraProductSetinSetinSetData has size "+ ((WeraProductSetinSet) weraproductset).m_colWeraProductSetinSetData.size());
                                        hData.put("SiSComponents", ((WeraProductSetinSet) weraproductset).m_colWeraProductSetinSetData );
                                        //((WeraProductSetinSet) weraproductset).debugOutWeraProductSetinSetData(0);
                                        //this.debugOutWeraProductSetinSetData(0);
                                    } else {
                                        colWeraVarSet = (Collection) weraproductset.getAttribute("variants");
                                        final Collection colWeraVarSet1 = new ArrayList();
                                        colWeraVarSet1.addAll(colWeraVarSet);
                                        if (colWeraVarSet1 != null && colWeraVarSet1.size() > 0)
                                        {
                                                Collections.sort((List) colWeraVarSet1, new OrderComparator());
                                        }
                                        for (final Iterator iter = colWeraVarSet1.iterator(); iter.hasNext();)
                                        {
                                                // --- Hole den Container
                                                final WeraVarianteSet weraVarSet = (WeraVarianteSet) iter.next();
                                                // --- Hole die Variante
                                                final WeraVariante myWeraVariante = (WeraVariante) weraVarSet.getAttribute("weravariants");
                                                listVarianteSet.add(weraVarSet);
                                                listVariants.add(myWeraVariante);
                                        }
                                    }
                                } else {
                                    hData.put("ean", StringUtils.trimToEmpty(  (String) weravariante.getAttribute("ean") ) );
                                    hData.put("ean_us", StringUtils.trimToEmpty(  (String) weravariante.getAttribute("ean_us") ) );
                                    hData.put("varnr", StringUtils.trimToEmpty( (String) weravariante.getAttribute("variantenNr")));
                                    hData.put("artnr", "artnr" );
                                    hData.put("code", StringUtils.trimToEmpty( (String) weravariante.getCode() ));
                                    WeraProduct oBase = (WeraProduct) weravariante.getAttribute("baseProduct");
                                    if (oBase != null) {
                                        // hData.put("artikelnr_index", "(AIND BASE) " + oBase.getArtikelnr_index());
                                        hData.put("artikelnr_index", oBase.getArtikelnr_index());
                                    } else {
                                        // hData.put("artikelnr_index", "(AIND BASE) " +"???" );
                                        hData.put("artikelnr_index", "???" );
                                    }
                                    
                                    hData.put("lagernr", StringUtils.trimToEmpty( (String) weravariante.getAttribute("lagerNr")));
                                    hData.put("name", StringUtils.trimToEmpty( (String) weravariante.getAttribute("name")));
                                    hData.put("isSiS", "0");
                                    hData.put("isVariant", "1");
                                    hData.put("pk", weravariante.getPK());
                                    // determine visibility of components of type set
                                    byte iVisibility =  wm.getVisibilityForCatalog( weravariante );
                                    hData.put("visibility", String.valueOf(iVisibility) );
                                    
                                    listVarianteSet.add(weravariantevariants);
                                    listVariants.add(weravariante);
                                }

				// --- Variantdaten
				listResultList.addAll(_genCADataForVariantList(listVariants, listVarianteSet));
				//System.out.println("++listResultList="+listResultList );
				//System.out.println("++listResultList.size()="+listResultList.size());
				hData.put("variantdata", listResultList);

				// --- Daten übernehmen
				//System.out.println("++listResultList="+listResultList);
				m_colWeraProductSetinSetData.add(hData);

			} // --- for (final Iterator iterPSet = colWeraProductSet.iterator(); iterPSet.hasNext();)
                        
                        // this.recalculateFootnotes();

		}
		catch (final Exception e)
		{
			e.printStackTrace();
		}

                // LOG.info("generateWeraProductSetData() finished.");
		return listResultList;
	}

	@Override
	public Collection<Collection<String>> generateOutputCells(final Collection colSetIcons)
	{
		final WeraManager wm = WeraManager.getInstance();
		final Collection<Collection<String>> colRows = new ArrayList();

		// --- Initialize
		//final Language oLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();
		final Locale oLocale = JaloSession.getCurrentSession().getSessionContext().getLocale();
		boolean bIsNextSet = true;
		String strPrevArtnr = "";
		String strLastCodeNr = "";
                
		// --- Zur Vermeidung von Icon Duplikaten werden die FileNames der Icons im Set gespeichert
		HashSet setIconCodes = new HashSet();

		// --- prüfe auf Display
		Boolean bIstDisplay = (Boolean) wm.getAttribute(this, "ist_display");
		if (bIstDisplay == null)
		{
			bIstDisplay = new Boolean(false);
		}
                String sOutputTemplate = (String) wm.getAttribute(this, "outputtemplate");
		if (sOutputTemplate == null)
		{
			sOutputTemplate = "";
		}
                boolean bIsSiSNoContent = sOutputTemplate.equals("PRODUCTSETINSET_NO_CONTENT");
                
		String strDisplayArtnr = (String) wm.getAttribute(this, "artnr");
		if (strDisplayArtnr == null)
		{
			strDisplayArtnr = "";
		}
		String strDisplayVarnr = (String) wm.getAttribute(this, "variantenNr");
		if (strDisplayVarnr == null)
		{
			strDisplayVarnr = "";
		}
		String strDisplayLagernr = (String) wm.getAttribute(this, "lagernr");
		if (strDisplayLagernr == null)
		{
			strDisplayLagernr = "";
		}
		String strDisplayEAN = (String) wm.getAttribute(this, "ean");
		if (strDisplayEAN == null)
		{
			strDisplayEAN = "-";
		}
		String strDisplayEAN_US = (String) wm.getAttribute(this, "ean_us");
		if (strDisplayEAN_US == null)
		{
			strDisplayEAN_US = "-";
		}
		if (oLocale.toString().equals("us_EN") || oLocale.toString().equals("us_ES") || oLocale.toString().equals("us_FR"))
		{
			strDisplayEAN = strDisplayEAN_US;
		}

		// --- Schleife über alle Daten
		for (final Iterator iterProductSetinSet = m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();)
		{
			// --- Nächster Satz
			final HashMap productHash = (HashMap) iterProductSetinSet.next();

			// --- Initialize
			String strArtnr = "";
			String strVarnr = "";
			String strLagernr = "";
			String strName = (String) productHash.get("name");
			if (strName == null)
			{
				strName = "isnull";
			}
			String strArtikelnrIndex = (String) productHash.get("artikelnr_index");
			if (strArtikelnrIndex == null)
			{
				strArtikelnrIndex = "isnull";
			}
			String strIsVariant = (String) productHash.get("isVariant");
			if (strIsVariant == null)
			{
				strIsVariant = "0";
			}
			String strIsSiS = (String) productHash.get("isSiS");
			if (strIsSiS == null)
			{
				strIsSiS = "0";
			}
                        
			if (bIstDisplay.booleanValue())
			{
				strVarnr = strDisplayVarnr;
				strArtnr = strDisplayArtnr;
				strLagernr = strDisplayLagernr;
			}
			else
			{
				strArtnr = (String) productHash.get("artnr");
				strVarnr = (String) productHash.get("varnr");
				strLagernr = (String) productHash.get("lagernr");
			}
			if (strArtnr == null)
			{
				strArtnr = "";
			}
			if (strVarnr == null)
			{
				strVarnr = "";
			}
			if (strLagernr == null)
			{
				strLagernr = "";
			}
/* disabled for now                        
                        final Date dValidFrom = (Date) productHash.get("valid_from");
                        // LOG.info("generateOutputCells dValidFrom = " + dValidFrom);
                        Boolean bIsValidFrom = (Boolean) productHash.get("is_valid_from");
                        // LOG.info("generateOutputCells bIsValidFrom = " + bIsValidFrom);
*/                        
                        
			//System.out.println ( "productHash=>" +  productHash );			
			//System.out.println ( "strArtnr=>" +  strArtnr );			

			// --- Schleife über alle Varianten des Satzes
			final ArrayList listVariantdata = (ArrayList) productHash.get("variantdata");
                        
                        int iCnt = 0;
			for (final Iterator itVariant = listVariantdata.iterator(); itVariant.hasNext();)
			{
				final HashMap hashVariantdata = (HashMap) itVariant.next();
                                iCnt++;

                                
				// --- Prüfen, ob ein neuer Satz beginnt
				bIsNextSet = (strPrevArtnr.equals(strArtnr) == false);

				// --- Neue Zeile initialisieren
				final Collection<String> colCells = new ArrayList();

				// --- Neuer Satz?
				if (bIsNextSet)
				{
					colCells.add(strVarnr);
					colCells.add(strArtnr);
					colCells.add(strLagernr);
				}
				else
				{
					colCells.add("");
					colCells.add("");
					colCells.add("");
				}

				// --- Icons --------------------------------------------------------------------------------------------------------------
				final WeraMedia med1 = (WeraMedia) hashVariantdata.get("icons1");
				final WeraMedia med2 = (WeraMedia) hashVariantdata.get("icons2");
				// --- Icons sammeln für Collection Rückgabe
				if (med1 != null)
				{
					if (setIconCodes.add(med1.getRealFileName()))
					{
						colSetIcons.add(med1);
					}
				}
				final String med1url = (med1 == null ? "&nbsp;" : "<img src='" + med1.getURL() + "' border='0' width='12'>");
				final String med2url = (med2 == null ? "&nbsp;" : "<img src='" + med2.getURL() + "' border='0' width='12'>");

				// --- Media1
				colCells.add(med1url);

				// --- Media2
				colCells.add(med2url);
				// --- Icons --------------------------------------------------------------------------------------------------------------

				// --- Code
				// LOG.info("+++++productHash: code=" + productHash.get("code"));
				// LOG.info("+++++hashVariantdata: code=" + hashVariantdata.get("code"));
                                // LOG.info("+++++bIstDisplay: =" + bIstDisplay.booleanValue());
                                // LOG.info("+++++m_colWeraProductSetinSetData: =" + m_colWeraProductSetinSetData.size());
                                // LOG.info("+++++listVariantdata: =" + listVariantdata.size());
                                // LOG.info("+++++strIsVariant: =" + strIsVariant );
                                // LOG.info("+++++bIsNextSet: =" + bIsNextSet );

				if (bIstDisplay.booleanValue())
				{
					// --- Darstellung Displays ---------------------------------------------------
                                    /*
					String strCodeNr = "";
					if (m_colWeraProductSetinSetData.size() == 1)
					{
						if (listVariantdata.size() == 1)
						{
							// --- 1 Satz, mit einem Artikel, Ausgabe Artikelnummer des Satz
							strCodeNr = (String) productHash.get("code");
						}
						else
						{
							// --- 1 Satz, mit mehreren Artikel, Ausgabe Artikelnummer der Artikel
							strCodeNr = (String) hashVariantdata.get("code");
						}
					}
					else
					{
						if (listVariantdata.size() == 1)
						{
							// --- n-Sätze, mit einem Artikel, Ausgabe Artikelnummer des Satz
							strCodeNr = (String) productHash.get("code");
						}
						else
						{
							// --- n-Sätze, mit mehreren Artikel, Ausgabe Artikelnummer der Artikel
							strCodeNr = (String) hashVariantdata.get("code");
						}
					}
                                    */
                                        String strCodeNr = (String) hashVariantdata.get("code");
                                        // strCodeNr = "|" + (String) hashVariantdata.get("artikelnr_index") + "|";
                                        strCodeNr = (String) hashVariantdata.get("artikelnr_index");
                                        if ( strCodeNr == null ) {
                                            strCodeNr = "";
                                        }
					if (strLastCodeNr.equals(strCodeNr))
					{
						// --- doppelte nicht ausgeben
						colCells.add("");
					}
					else
					{
                                                colCells.add(strCodeNr);
					}
					strLastCodeNr = strCodeNr;
				}
				else
				{
					// --- Darstellung SB-Programm ----------------------------------------------
					// colCells.add( (String) hashVariantdata.get("code"));
                                    colCells.add( (String) hashVariantdata.get("artikelnr_index"));
				}
				// colCells.add((String) productHash.get("code"));

				// --- Variantdaten
				final Collection colVariants = (Collection) hashVariantdata.get("colHashArtikel");
				// LOG.info("+++++colVariants=" + colVariants);
				String strRow = "";
				for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();)
				{
					final HashMap variantHash = (HashMap) iterVariant.next();

					String myValue = "";
					if (bIsSiSNoContent) {

/* SJ 2021-05-25: we use a version of "value" here WITHOUT the preceding quantity to avoid duplicate quantity rendering in the SiS template for PRODUCTSETINSET_NO_CONTENT
* String myValue = (String) variantHash.get("value");
*/
						myValue = (String) variantHash.get("value_no_quant");

					} else {

						myValue = (String) variantHash.get("value");
					}
                                        
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
				} // --- for (final Iterator iterVariant = colVariants.iterator(); iterVariant.hasNext();)

				// --- Variante
				colCells.add(strRow);

				// --- Neuer Satz?
				if (bIsNextSet)
				{
					// --- EAN
					String strEAN = "";
					if (oLocale.toString().equals("us_EN") || oLocale.toString().equals("us_ES") || oLocale.toString().equals("us_FR"))
					{
						strEAN = (String) productHash.get("ean_us");
						LOG.info("+++get EAN_US");
					}
					else
					{
						strEAN = (String) productHash.get("ean");
					}
					if (bIstDisplay.booleanValue())
					{
						strEAN = strDisplayEAN;
					}
					if (strEAN == null || strEAN.length() < 10)
					{
						colCells.add("-");
					}
					else
					{
						//colCells.add(productHash.get("ean").toString().substring(7));
						colCells.add(strEAN.substring(7));
					}

					// --- VPE
					colCells.add((String) productHash.get("vpe"));
				}
				else
				{
					// --- EAN-Spalte
					colCells.add("");

					// --- VPE - SPalte
					String strVPE = "";
					if (bIstDisplay.booleanValue())
					{
						// --- Darstellung Display
						if (m_colWeraProductSetinSetData.size() > 1)
						{
							strVPE = (String) productHash.get("vpe");
						}
					}
					else
					{
						// --- Darstellung SB
						strVPE = "";
					}
					colCells.add(strVPE);
				}


				// Col #
/*				
				final String name = (String) productHash.get("name");
				System.out.println("Name="  + name );
				final WeraVariante variant = (WeraVariante) productHash.get("variant");
				final WeraProduct weraProduct = (WeraProduct) wm.getAttribute(variant, "baseProduct");
				if ( weraProduct == null  ) {
					colCells.add( "weraProduct == null" );
				} else {
					
					colCells.add( weraProduct.getName() );
				}
*/				
				
                                colCells.add ( strArtikelnrIndex ); // from productHash!!! | vorher: colCells.add( strName );
                                colCells.add ( strIsVariant );
                                colCells.add ( strIsSiS );
                                colCells.add ( "" );
                                /*
                                Boolean bFoam = (Boolean) hashVariantdata.get("prioritizeCode");
                                
                                if ( bFoam != null && bFoam.booleanValue() ) {
                                    colCells.add ((String) hashVariantdata.get("code"));
                                } else {
                                    colCells.add ((String) hashVariantdata.get("name"));
                                }
                                */
                                
                                colCells.add ((String) hashVariantdata.get("artikelnr_index"));
                                colCells.add ( (String) hashVariantdata.get("name") );
                                Boolean bFoam = (Boolean) hashVariantdata.get("prioritizeCode");
                                //LOG.info("bFoam = "+bFoam);
                                //LOG.info("name = "+(String) hashVariantdata.get("name"));
                                if ( bFoam != null && bFoam.booleanValue() ) {
                                    LOG.info("adding art_index "+ (String) hashVariantdata.get("artikelnr_index"));
                                    colCells.add ((String) hashVariantdata.get("artikelnr_index"));
                                } else {
                                    colCells.add ( (String) productHash.get("name") );
                                }
                                
                                colCells.add ( (String) productHash.get("visibility") );
                                /*
                                colCells.add( dValidFrom != null ? dValidFrom.toLocaleString() : "" );
                                if ( bIsValidFrom != null ) {
                                    colCells.add( bIsValidFrom ? "1" : "0");
                                } else {
                                    colCells.add( "1" );
                                }
                                */
				colRows.add(colCells);

				// --- Artikelnummer merken
				strPrevArtnr = strArtnr;

			} // --- for ( Iterator itVariant=listVariantdata.Iterator(); itVariant.hasNext() ) {

			// --- wenn Komponente selber ein SiS ist ...
			if ( strIsSiS == "1" ) {
				// --- Neue Zeile initialisieren
				final Collection<String> colCells1 = new ArrayList();
                                colCells1.add(strVarnr);
                                colCells1.add(strArtnr);
                                colCells1.add(strLagernr);

				colCells1.add(""); // tip
				colCells1.add(""); // antrieb
				colCells1.add( (String) productHash.get("code") ); // BaseCode
				colCells1.add(""); // VarInfo
				colCells1.add(""); //EAN
				// --- VPE
				colCells1.add((String) productHash.get("vpe")); // Quantity
				colCells1.add( strName ); // Name
                                
                                colCells1.add("0"); // isVariant
                                colCells1.add("1"); // isSiS
                                final Collection<String> colCells2 = new ArrayList();
                                
                                // get the SiS object, which is part of "this" SiS root object.
                                final PK componentSiSPK = (PK) productHash.get("pk");
                                // WeraProductSetinSet componentSiS = JaloSession.getCurrentSession().getItem(componentSiSPK);
                                colCells1.add(componentSiSPK.toString()); // sSiSComponentPK
                                colCells1.add(""); // strVariantBaseName
                                colCells1.add(""); // sIsToolTrolleyFoam
                                colCells1.add("");
				colRows.add(colCells1);
			} else {
                            // --- keine row gefüllt?
                            if ( listVariantdata.size() == 0 ) {
                                    // --- Neue Zeile initialisieren
                                    final Collection<String> colCells1 = new ArrayList();

                                    // --- Neuer Satz?
                                    if (bIsNextSet)
                                    {
                                            colCells1.add(strVarnr);
                                            colCells1.add(strArtnr);
                                            colCells1.add(strLagernr);
                                    }
                                    else
                                    {
                                            colCells1.add("");
                                            colCells1.add("");
                                            colCells1.add("");
                                    }

                                    colCells1.add("");
                                    colCells1.add("");
                                    colCells1.add("");
                                    colCells1.add("");
                                    colCells1.add("");
                                    // --- VPE
                                    colCells1.add((String) productHash.get("vpe"));
                                    colCells1.add( strName );

                                    // SJ: this was missing???
                                    colCells1.add("0");
                                    colCells1.add("0");
                                    
                                    colCells1.add("");
                                    colCells1.add("");
                                    colCells1.add("");
                                    colCells1.add("");
                                    colRows.add(colCells1);
                            }
                        }


		} // --- for (final Iterator iterProductSetinSet = m_colWeraProductSetinSetData.iterator(); iterProductSetinSet.hasNext();) {


		setIconCodes = null;
		return colRows;
	}

        /*
         * recalculateFootnotes: calculate Footnotes on article level for SiS and SiSiS
         *   Note: this method basically completely replaces WeraProduct:_genFootnotes() method for SiS(iS)
         *   aSisData: some SiS's m_colWeraProductSetinSetData data structure
         *   colWeraProductSiSFootnotes: SiS(iS) global list of Footnotes to check against on article level
         *   Returns: the updated global list of Footnotes
         */
        public ArrayList recalculateFootnotes( ArrayList aSisData, ArrayList colWeraProductSiSFootnotes ) {
            //ArrayList colWeraProductSiSFootnotes = new ArrayList();
            int iCnt = 0;
            ArrayList aUsedSiSData = ( aSisData != null ) ? aSisData : this.m_colWeraProductSetinSetData;
            for (final Iterator i = aUsedSiSData.iterator(); i.hasNext();) {
                final HashMap myHash = (HashMap) i.next();
                final String sCode = (String) myHash.get("code");
                iCnt++;
                //LOG.info("recalculateFootnotes(): product #"+iCnt+": "+sCode);
                final ArrayList listSiSdata = (ArrayList) myHash.get("SiSComponents");
                if (listSiSdata != null && listSiSdata.size() > 0 ) {
                    //LOG.info("recalculateFootnotes(): recursing ...");
                    colWeraProductSiSFootnotes = this.recalculateFootnotes(listSiSdata, colWeraProductSiSFootnotes);
                }
                final ArrayList listVariantdata = (ArrayList) myHash.get("variantdata");
                int iVarCnt = 0;
                for (final Iterator itVariant = listVariantdata.iterator(); itVariant.hasNext();) {
                    final HashMap hashVariantdata = (HashMap) itVariant.next();
                    iVarCnt++;
                    int jCnt = 0;
                    for (final Iterator ia = ((Collection) hashVariantdata.get("colHashArtikel")).iterator(); ia.hasNext();) {
                        final HashMap myAHash = (HashMap) ia.next();
                        jCnt++;
                        final WeraVariante wv = (WeraVariante) myAHash.get("variant");
                        if (wv != null) {
                            String sVariantCode = "";
                            
                            try {
                                sVariantCode = (String) wv.getAttribute("code");
                            } catch (Exception e) {
                                // ignore
                            }
                            
                            Collection colFootnotes = null;
                            try {
                                colFootnotes = (Collection) wv.getAttribute("footnotes");
                            } catch (final Exception e) {
                                // ignore
                            }
                            if (colFootnotes != null) {
                                if ( colFootnotes.size() > 0 ) {
                                    // LOG.info("recalculateFootnotes(): product fam " + hashVariantdata.get("code") + ", variant "+sVariantCode + " has footnotes.");
                                }
                                for (final Iterator ifn = colFootnotes.iterator(); ifn.hasNext();) {
                                    
                                    final Footnote oFootnote = (Footnote) ifn.next();
                                    String sFootnoteId = "";
                                    try {
                                        sFootnoteId = (String) oFootnote.getAttribute("code");
                                    } catch (Exception e) {
                                        // ignore
                                    }
                                    int iFootnodeIndex = colWeraProductSiSFootnotes.indexOf(oFootnote);
                                    if (iFootnodeIndex < 0) {
                                        colWeraProductSiSFootnotes.add(oFootnote);
                                        iFootnodeIndex = colWeraProductSiSFootnotes.lastIndexOf(oFootnote);
                                        LOG.info("recalculateFootnotes(): found NEW footnote: " + sFootnoteId + " with index: "+iFootnodeIndex);
                                    } else {
                                        LOG.info("recalculateFootnotes(): found OLD footnote: " + sFootnoteId + " with index: "+iFootnodeIndex);
                                    }
                                    if (myAHash.get("footnotes") == null) {
                                            myAHash.put("footnotes", new ArrayList());
                                    }
                                    final Collection myAHashFootnotes = (Collection) myAHash.get("footnotes");
                                    myAHashFootnotes.add(new Integer(iFootnodeIndex + 1));
                                    
                                }
                            }
                        }
                    }
                }
            }
            return colWeraProductSiSFootnotes;
        }
        
	public void debugOutWeraProductSetinSetData( final int iResursiveIndent, final ArrayList overrideSiSData ) {
                if ( iResursiveIndent == 0 ) {
                    LOG.info ("debugOutWeraProductSetinSetData() called for product set "+this.getCode() + " with indent "+iResursiveIndent);
                    LOG.info("----------------------------------");
                }
                final ArrayList sisData =  ( overrideSiSData != null ) ? overrideSiSData : m_colWeraProductSetinSetData;
                final String sRecursiveIndent = StringUtils.repeat(" ", iResursiveIndent);
		int iCnt = 0;
		int jCnt = 0;
		if (sisData == null) {
			LOG.info(sRecursiveIndent + "sisData not yet initialized!");
		} else {
			for (final Iterator i = sisData.iterator(); i.hasNext();) {
				final HashMap myHash = (HashMap) i.next();
				final WeraMedia med1 = (WeraMedia) myHash.get("icons1");
				final WeraMedia med2 = (WeraMedia) myHash.get("icons2");
				iCnt++;
				LOG.info(sRecursiveIndent + "SiS component #" + iCnt);
                                LOG.info(sRecursiveIndent + " o PK : "+myHash.get("pk"));
                                LOG.info(sRecursiveIndent + " o isVariant : " + myHash.get("isVariant"));
                                LOG.info(sRecursiveIndent + " o isSiS : " + myHash.get("isSiS"));
				LOG.info(sRecursiveIndent + " o lagernr : " + myHash.get("lagernr"));
                                LOG.info(sRecursiveIndent + " o code : " + myHash.get("code"));
				LOG.info(sRecursiveIndent + " o varnr : " + myHash.get("varnr"));
                                LOG.info(sRecursiveIndent + " o artnr : " + myHash.get("artnr"));
                                LOG.info(sRecursiveIndent + " o ean : " + myHash.get("ean"));
                                LOG.info(sRecursiveIndent + " o name : " + myHash.get("name"));
                                final ArrayList listSiSdata = (ArrayList) myHash.get("SiSComponents");
                                if (listSiSdata!=null) {
                                    LOG.info(sRecursiveIndent + " o SiSComponents (size "+listSiSdata.size()+"):");
                                    if (listSiSdata.size() > 0 ) {
                                        this.debugOutWeraProductSetinSetData(2,listSiSdata);
                                    }
                                } else {
                                    LOG.info(sRecursiveIndent + " o SiSComponents : NULL");
                                }
                                
                                final ArrayList listVariantdata = (ArrayList) myHash.get("variantdata");
                                if (listVariantdata!=null) {
                                    LOG.info(sRecursiveIndent + " o variantdata (size "+listVariantdata.size()+"):");
                                } else {
                                    LOG.info(sRecursiveIndent + " o variantdata : NULL");
                                }
                                int iVarCnt = 0;
                                for (final Iterator itVariant = listVariantdata.iterator(); itVariant.hasNext();)
                                {
                                        iVarCnt++;
                                        LOG.info(sRecursiveIndent + "   Product family #"+iVarCnt);
                                        LOG.info(sRecursiveIndent + "   ===================");
                                        final HashMap hashVariantdata = (HashMap) itVariant.next();
                                        LOG.info(sRecursiveIndent + "   o code : " + hashVariantdata.get("code"));
                                        LOG.info(sRecursiveIndent + "   o name : " + hashVariantdata.get("name"));
                                        final WeraMedia varmed1 = (WeraMedia) hashVariantdata.get("icons1");
                                        final WeraMedia varmed2 = (WeraMedia) hashVariantdata.get("icons2");
                                        if ( varmed1 != null ) {
                                            LOG.info(sRecursiveIndent + "   o media1 : " +varmed1.getCode() );
                                        }
                                        if ( varmed2 != null ) {
                                            LOG.info(sRecursiveIndent + "   o media2 : " +varmed2.getCode() );
                                        }
                                        LOG.info(sRecursiveIndent + "   o prioritizeCode : " + hashVariantdata.get("prioritizeCode"));
                                        
                                        jCnt = 0;
                                        if ( hashVariantdata.get("colHashArtikel") != null ) {
                                            LOG.info(sRecursiveIndent + "   o colHashArtikel: ");
                                            for (final Iterator ia = ((Collection) hashVariantdata.get("colHashArtikel")).iterator(); ia.hasNext();) {
                                                    final HashMap myAHash = (HashMap) ia.next();
                                                    jCnt++;
                                                    LOG.info(sRecursiveIndent + "     Variant #" + jCnt);
                                                    LOG.info(sRecursiveIndent + "      - variant :" + ((WeraVariante) myAHash.get("variant")).getCode());
                                                    LOG.info(sRecursiveIndent + "      - value:" + myAHash.get("value"));
                                                    LOG.info(sRecursiveIndent + "      - order :" + myAHash.get("order"));
                                                    LOG.info(sRecursiveIndent + "      - contentQuantity :" + myAHash.get("contentQuantity"));
                                                    if (myAHash.get("footnotes") != null) {
                                                            LOG.info(sRecursiveIndent + "      - footnotes :");
                                                            for (final Iterator ift = ((Collection) myAHash.get("footnotes")).iterator(); ift.hasNext();) {
                                                                    final Integer oFootnote = (Integer) ift.next();
                                                                    try {
                                                                            LOG.info(sRecursiveIndent + "        > FN: " + oFootnote.intValue());
                                                                    } catch (final Exception e) {
                                                                            e.printStackTrace();
                                                                    }
                                                            }
                                                    }
                                                    if (myAHash.get("sis_footnotes") != null) {
                                                            LOG.info(sRecursiveIndent + "      - sis footnotes :");
                                                            for (final Iterator ift = ((Collection) myAHash.get("sis_footnotes")).iterator(); ift.hasNext();) {
                                                                    final Integer oFootnote = (Integer) ift.next();
                                                                    try {
                                                                            LOG.info(sRecursiveIndent + "        > FN: " + oFootnote.intValue());
                                                                    } catch (final Exception e) {
                                                                            e.printStackTrace();
                                                                    }
                                                            }
                                                    }
                                            }
                                        }                                        
                                }                                
				// LOG.info  ( " o colCA : " + myHash.get("colCA") );
				// LOG.info  ( " o colCAAll : " + myHash.get("colCAAll") );
			}
		}

	}        
        
}

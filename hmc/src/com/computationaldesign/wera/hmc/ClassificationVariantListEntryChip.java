/*
 * [y] hybris jakarta
 * 
 * Copyright (c) 2000-2005 hybris
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package com.computationaldesign.wera.hmc;

import de.hybris.platform.hmc.ItemChip;
import de.hybris.platform.hmc.ItemListChip;
import de.hybris.platform.hmc.HMCHelper;
import de.hybris.platform.hmc.generic.EditableItemListEntryChip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.hmc.jalo.ItemEditorContext;
import de.hybris.platform.catalog.jalo.ProductFeature;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.link.LinkManager;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;

import java.util.*;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.Footnote;
import com.computationaldesign.wera.hmc.HMCClassificationHelper.ClassificationAttributeEntry;

public class ClassificationVariantListEntryChip extends EditableItemListEntryChip
{
	private static final String JSP_URI = "/ext/wera/classificationVariantListEntryChip.jsp";

	public static final String OPEN_FOOTNOTE = "openfootnotecreator";
	public static final String OPEN_FUNCTIONS_WERAVARIANTE = "weravariante.functions";
	public static final String OPEN_FOOTNOTE_WERAVARIANTE_PRICE = "product.prices";
	
	
	private Map classificationMap = new LinkedHashMap();
	private Map classificationMapAssignedOnly = new LinkedHashMap();
	
	private Collection inheritedCategories = new HashSet();		// might not be needed
	private Collection unboundFeatures = new ArrayList();		// might not be needed
	private Product product;
	
	public ClassificationVariantListEntryChip( DisplayState displayState, ItemListChip parent, Item item )
	{
		super(displayState, parent, item);
		this.product = (Product) getItem();
		
		HMCClassificationHelper.loadClassificationAttributes((Product) getItem(), this, getClassificationMap(), getClassificationMapAssignedOnly(), getInheritedCategories(), getUnboundFeatures());
	}
    public Product getProduct() { return this.product; }
    public boolean isChanged()
	{
	System.out.println("ClassificationVariantListEntryChip.isChanged()");
		// normal attributes
		if( super.isChanged() )
		{
			return true;
		}

		// classification attributes
		for( final Iterator iter = getAllClassificationAttributeEntries().iterator(); iter.hasNext(); )
		{
			if( ((ClassificationAttributeEntry) iter.next()).isChanged() )
			{
				return true;
			}
		}
		return false;
	}

	protected void refreshEntry()
	{
		super.refreshEntry();
		
		getClassificationMap().clear();
		getInheritedCategories().clear();
		getUnboundFeatures().clear();
		
		HMCClassificationHelper.loadClassificationAttributes((Product) getItem(), this, getClassificationMap(), getClassificationMapAssignedOnly(), getInheritedCategories(), getUnboundFeatures());
	}

	public void save()
	{
		System.out.println ("+++++++++++++++ ClassificationVariantListEntryChip in Save (START)");
		// normal attributes
		super.save();
		// classification attributes
		for( final Iterator iter = getAllClassificationAttributeEntries().iterator(); iter.hasNext(); )
		{
			((ClassificationAttributeEntry) iter.next()).save();
		}
		System.out.println ("+++++++++++++++ ClassificationVariantListEntryChip in Save (ENDE)");
	}

	public Collection getAllClassificationAttributeEntries()
	{
		Collection result = new ArrayList();
		
		for( final Iterator categoryIter = getClassificationMap().keySet().iterator(); categoryIter.hasNext(); )
		{
			for( final Iterator entryIter = ((Collection) getClassificationMap().get(categoryIter.next())).iterator(); entryIter.hasNext(); )
			{

				ClassificationAttributeEntry classificationAttributeEntry = ((ClassificationAttributeEntry) entryIter.next());
/*				
				EnumerationValue visibility = classificationAttributeEntry.getVisibility();
				if ( visibility != null && !visibility.equals(EnumerationManager.getInstance().getEnumerationValue("ClassificationAttributeVisibilityEnum","visible_in_variant")) ) {
						continue;
				}
*/
				result.add(classificationAttributeEntry);
			}
		}
		
		return result;
	}
	
	public Collection getInheritedCategories()
	{
		return inheritedCategories;
	}

	public Collection getUnboundFeatures()
	{
		return unboundFeatures;
	}
	
	public Map getClassificationMap()
	{
		return classificationMap;
	}

	public Map getClassificationMapAssignedOnly()
	{
		return classificationMapAssignedOnly;
	}
	
	
	public String getJSPURI()
	{
		return JSP_URI;
	}	

	public void processEvents(Map events)
	{
		System.out.println( "--------------- processEvents 1111 ");
		super.processEvents( events );
		System.out.println( "--------------- processEvents 2222 ");
		System.out.println( "events: " + events );		
		
		String getFeaturePk = getStringValue( events, OPEN_FOOTNOTE );
		
		if( getFeaturePk != null && getFeaturePk.length() > 0 )
		{
			System.out.println( "--------------- OPEN_FOOTNOTE.processEvents 3333 ");
			final Item item = getItem();
			ComposedType featureType = TypeManager.getInstance().getComposedType( ProductFeature.class );

			HashMap initialValues = new HashMap();
			
			HMCHelper.openItemCreator( featureType, initialValues, null, null, null, false );
			
			ProductFeature pf = (ProductFeature) JaloSession.getCurrentSession().getItem( getFeaturePk );
			try {
				HMCHelper.getHMCContextFor( this ).openItem( pf, true );
			} catch (JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println( "--------------- processEvents 4444 ");
			return;
		}
		
		// --- Fussnoten
		String getStringTab = getStringValue( events, OPEN_FUNCTIONS_WERAVARIANTE );
		if( getStringTab != null && getStringTab.length() > 0 )
			getStringTab = OPEN_FUNCTIONS_WERAVARIANTE;
		else
		{
			// --- Preise
			getStringTab = getStringValue( events, OPEN_FOOTNOTE_WERAVARIANTE_PRICE );
			if( getStringTab != null && getStringTab.length() > 0 )
				getStringTab = OPEN_FOOTNOTE_WERAVARIANTE_PRICE;
		}
		
		if( getStringTab != null && getStringTab.length() > 0 )
		{
			System.out.println( "--------------- "+getStringTab+".processEvents 3333 ");

			final Item item = getItem();
			try {
				// --- Open Element im gewünschten Tab
				ItemEditorContext context = HMCHelper.getHMCContextFor( this ).openItem( item, true );
				context.setCurrentTab(getStringTab);
				
			} catch (JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			System.out.println( "--------------- processEvents 4444 ");
		}
	}	
	
	
}

/*
 * $Log: ClassificationVariantListEntryChip.java,v $
 * Revision 1.1.2.1  2005/10/09 20:23:57  rutten
 * CATALOG-241: added first version of classification variant list editor
 *
 */
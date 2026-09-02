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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import de.hybris.platform.hmc.HMCHelper;
import de.hybris.platform.hmc.HMCHelper.AttributePreset;
import de.hybris.platform.hmc.generic.*;
import de.hybris.platform.jalo.security.AccessManager;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.variants.constants.GeneratedVariantsConstants;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.type.*;

public class ClassificationVariantListEditorChip extends GenericReferenceListEditorChip 
{
	private static final String JSP_URI = "/ext/wera/classificationVariantListEditorChip.jsp";
	
	public class OrderComparator implements Comparator{
		public int compare(Object o1, Object o2) /*descending order*/  {
			
			// --- Initialize
			Integer iValue1 = new Integer(0);
			Integer iValue2 = new Integer(0);
			int iResult =  0;
			
			try {
				// --- Hole Values
				iValue1 = (Integer) ((Item) o1).getAttribute("order");
				iValue2 = (Integer) ((Item) o2).getAttribute("order");
			} catch (JaloInvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
			if ( iValue1 == null || iValue2 == null )
				return 0;
			else
			   return iValue1.compareTo(iValue2);
		}
	}

	// --- Value wird gesetzt, neue Sortierung nach Order
	public void setValue(Object arg0) {
		// TODO Auto-generated method stub

		// --- Sortiere nach Order
		List colList = new ArrayList();
		if ( arg0 != null ) {
		   colList.addAll((Collection) arg0);
		   Collections.sort(colList, new OrderComparator() );
			super.setValue(colList);
		}
		else
		   super.setValue(arg0);
	}

	public ClassificationVariantListEditorChip(DisplayState displayState, Chip parent)
	{
		super(displayState, parent, (CollectionType) TypeManager.getInstance().getType("VPCollection"), true);

		// try to set the permission
		try
		{
			final ComposedType productType = HMCHelper.getProductType();
			final AttributeDescriptor variantsDescriptor = productType != null ? productType.getAttributeDescriptor(GeneratedVariantsConstants.Attributes.Product.VARIANTS) : null;

/* !!! ACHTUNG 			
			setEditable(variantsDescriptor != null ? AccessManager.getInstance().canChange(variantsDescriptor) : false);
*/			
			setEditable(variantsDescriptor != null ? true : false);
			setEditable(true);
		}
		catch( JaloItemNotFoundException e )
		{
			e.printStackTrace();
		}
		
		setEntriesEditable(true);
		getListChip().setExternalCreate(false);
	
		addPresets();		
	}

	/**
	 * Adjust this method to change the behaviour of presetting attributes when creating new variant products.
	 * The first parameter of the Preset object is the name of the source attribute, the second is the name of the target attribute.
	 * Usually they are the same. 
	 *
	 * The list of preset attributes could also be made configurable using parameters in the webmc.xml configuration.
	 * This method here should then use {@link de.hybris.jakarta.webmc.attribute.AbstractAttributeEditorChip#getParameters()} to
	 * set the correct presets.  
	 */
	protected void addPresets()
	{
		getListChip().addCreationPreset(new AttributePreset("code", "code"));
		getListChip().addCreationPreset(new AttributePreset("approvalStatus", "approvalStatus"));
		getListChip().addCreationPreset(new AttributePreset("catalogVersion", "catalogVersion"));
		getListChip().addCreationPreset(new AttributePreset("baseProduct", null));
	}
	
	protected String getTypeAttribute()
	{
		return GeneratedVariantsConstants.Attributes.Product.VARIANTTYPE;
	}

	protected GenericItemListChip createListChip(boolean resortable, ComposedType elementType, boolean isPartOf)
	{
		return new ClassificationVariantListChip(getDisplayState(), this, elementType);
	}	
	
	/**
	 * The {@link #OPEN_ATTRIBUTE} event is handled here. The item with the given pk will be opened
	 * in a separate window.
	 */
	public void processEvents(Map events)
	{
	System.out.println("++processEvents.clvleChip=" + events);
		super.processEvents( events );
				
/*		
		if( events.containsKey(OPEN_FOOTNOTE) )
		{
			final String pk = (String) ((List) events.get(OPEN_ATTRIBUTE)).get(0);

			if( !pk.equals("") )
			{
				try
				{
					final Item item = getJaloSession().getItem(pk);
					WebMCHelper.getWebmcContextFor(this).openItem(item, true);
				}
				catch( Exception e )
				{
					// if anything goes wrong, just do nothing...
					e.printStackTrace();
				}
			}
		}
*/		
	}
	
	
}

/*
 * $Log: ClassificationVariantListEditorChip.java,v $
 * Revision 1.1.2.1  2005/10/09 20:23:57  rutten
 * CATALOG-241: added first version of classification variant list editor
 *
 */
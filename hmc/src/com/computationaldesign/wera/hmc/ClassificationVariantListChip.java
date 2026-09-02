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
import java.util.Iterator;

import com.computationaldesign.wera.jalo.WeraMedia;
import com.computationaldesign.wera.hmc.HMCClassificationHelper.ClassificationAttributeEntry;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloItemNotFoundException;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.type.AttributeDescriptor;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.hmc.generic.GenericItemListChip;
import de.hybris.platform.hmc.generic.GenericItemListEntryChip;
import de.hybris.platform.jalo.enumeration.EnumerationManager;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.hmc.AbstractToolbarChip;

public class ClassificationVariantListChip extends GenericItemListChip
{
	private static final String JSP_URI = "/ext/wera/classificationVariantListChip.jsp";

	private Collection attributeNames;
	
	public ClassificationVariantListChip(DisplayState displayState, Chip parent, ComposedType type)
	{
		super( displayState, parent, type, true);
	}
/*
	public AbstractToolbarChip getToolbar() {
	AbstractToolbarChip atc = super.getToolbar();
		

	return atc;
	}
*/	
	protected GenericItemListEntryChip createListEntryChip(Item item)
	{
		return new ClassificationVariantListEntryChip(getDisplayState(), this, item);								
	}

	public String getJSPURI()
	{
		return JSP_URI;
	}
 	public Collection getClassificationAttributeNames()
	{
		if( attributeNames == null && !getListEntries().isEmpty() )
		{
			attributeNames = new ArrayList();
			
			final ClassificationVariantListEntryChip entryChip = (ClassificationVariantListEntryChip) getListEntries().get(0);
			
			for( final Iterator categoryIter = entryChip.getClassificationMap().keySet().iterator(); categoryIter.hasNext(); )
			{
				for( final Iterator caIter = ((Collection) entryChip.getClassificationMap().get(categoryIter.next())).iterator(); caIter.hasNext(); )
				{
					ClassificationAttributeEntry classificationAttributeEntry = ((ClassificationAttributeEntry) caIter.next());
					
					String stringIcon = "";
					String stringName = "<br>" + classificationAttributeEntry.getName();
					String stringUnit = "<br>&nbsp;";
					EnumerationValue visibility = classificationAttributeEntry.getVisibility();

					
						if ( classificationAttributeEntry.getUnit() != null )
						   stringUnit = "<br>[" + classificationAttributeEntry.getUnitName() + "]";

						Collection icons = classificationAttributeEntry.getIcons();
						if( icons != null && icons.size() > 0 )
						{	
							WeraMedia weraIcon = (WeraMedia) icons.iterator().next();
							stringIcon = "<img width='30px;' src='" + weraIcon.getURL() + "' />";
						 }
					
						attributeNames.add( stringIcon + stringName  + stringUnit ); 
					
				}
			}
		}

		
		return attributeNames;
	}
 	
	public String getTitle(String qualifier)
	{
		String title = (String) getItemLayoutNode().getAttributeTitles().get(qualifier);

		if( title != null )
		{
			// explicit title was set, try finding a localization
			return DisplayState.getCurrent().getLocalizedString(title);		
		}
		else
		{
			// no explicit title was set, use attributename or qualifier
			try
			{
				AttributeDescriptor descriptor = getItemType().getAttributeDescriptor(qualifier);
				String mytitle = "";
				String name = "<br>" + descriptor.getName();
				if (name == null)
				{
					name = getDisplayState().getLocalizedString(qualifier);
				}
				
				String icon = "";
				String unit = "<br>&nbsp;";
				   
				Collection icons = (Collection)descriptor.getAttribute( "icons" );
				if( icons != null && icons.size() > 0 )
				{	
					WeraMedia weraIcon = (WeraMedia) icons.iterator().next();
					icon = "<img width='30px;' src='" + weraIcon.getURL() + "' />";
				}
				
				mytitle = icon + name + unit;
				
				return mytitle;
			}
			catch (JaloItemNotFoundException e)
			{
				getLogger().warn( "Attribute " + qualifier + " does not exist!" );
				return "";
			} catch (JaloInvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return "";
	}
}

/*
 * $Log: ClassificationVariantListChip.java,v $
 * Revision 1.1.2.1  2005/10/09 20:23:57  rutten
 * CATALOG-241: added first version of classification variant list editor
 *
 */
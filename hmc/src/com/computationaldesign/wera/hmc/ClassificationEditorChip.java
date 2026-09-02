/*
 * [y] hybris platform
 * 
 * Copyright (c) 2000-2005 hybris Holding AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information").  You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 */
package com.computationaldesign.wera.hmc;

import de.hybris.platform.hmc.*;
import de.hybris.platform.hmc.HMCHelper;
import de.hybris.platform.hmc.generic.*;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.catalog.jalo.*;

//import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;

import de.hybris.platform.variants.jalo.VariantProduct;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.product.Product;
import java.util.*;

import com.computationaldesign.wera.hmc.HMCClassificationHelper.ClassificationAttributeEntry;

public class ClassificationEditorChip extends CustomChip implements SpecialSaveChip, SpecialReloadChip, ChangeIndicatorChip
{
	private static final String JSP_URI = "/ext/wera/classificationEditorChip.jsp";
	
	private final static String VALUE_DELIMITER = ";";
	
	public final static String OPEN_ATTRIBUTE = "open_attribute";
	
	private final GenericItemChip itemChip;  

	private final Map classificationMap = new LinkedHashMap();
	private final Map classificationMapAssignedOnly = new LinkedHashMap();	
	
	private final Collection unboundFeatures = new ArrayList();
	private final Collection inheritedCategories = new HashSet();
	
	private boolean isChanged;		// will be set to true if at least one attribute has been changed

	public ClassificationEditorChip(DisplayState displayState, Chip parent, Map attributes, String jspURI)
	{
		super(displayState, parent, attributes, jspURI);

		itemChip = GenericHelper.getItemChip(parent);

}

	/**
	 * Returns the enclosing {@link GenericItemChip} of this tab.
	 * 
	 * @return the enclosing {@link GenericItemChip} of this tab.
	 */
	protected GenericItemChip getItemChip()
	{
		return itemChip;
	}

	/**
	 * Returns the product which is in the enclosing item editor of this tab.
	 * 
	 * @return the product which is in the enclosing item editor of this tab.
	 */
	public Product getProduct()
	{
		return getItemChip() != null ? (Product) getItemChip().getItem() : null;
	}
		
	public String getDefaultJSPURI()
	{
		return JSP_URI;
	}

	/**
	 * Loads all classification categories and the appropriate classification attributes.
	 *
	 */
	protected void loadAttributes()
	{
		if( getProduct() == null )
		{
			return;
		}

		// first clear the category->attributes map and the unbound product features collection (this may be a reload and the map could contain data from previous loading)
		getClassificationMap().clear();
		getUnboundFeatures().clear();
		getInheritedCategories().clear();
		
		HMCClassificationHelper.loadClassificationAttributes(getProduct(), this, getClassificationMap(), getClassificationMapAssignedOnly(), getInheritedCategories(), getUnboundFeatures());
	}

	/**
	 * Returns the classification map which contains category->attributecollection mappings 
	 * for each classification category containing this editor's product.
	 * 
	 * @return the classification map
	 */
	public Map getClassificationMap()
	{
		return classificationMap;
	}

	/**
	 * Returns the classification map which contains category->attributecollection mappings 
	 * for each classification category containing this editor's product.
	 * 
	 * @return the classification map
	 */
	public Map getClassificationMapAssignedOnly()
	{
		return classificationMapAssignedOnly;
	}
	
	
	/**
	 * Returns the list of unbound {@link ProductFeature ProductFeatures}.
	 * 
	 * @return the list of unbound ProductFeatures
	 */
	public Collection getUnboundFeatures()
	{
		return unboundFeatures;
	}

	/**
	 * @return Returns the inheritedCategories.
	 */
	public Collection getInheritedCategories()
	{
		return inheritedCategories;
	}
		
	/**
	 * Saves all the ClassificationAttributeEntrys.
	 */
	protected void saveAttributes()
	{
		System.out.println( "ClassificationEditorChip.saveAttributes() ++ (START) +++++++++++++++++++++++++++++++++++++" );
		for( final Iterator categoryIter = getClassificationMap().keySet().iterator(); categoryIter.hasNext(); )
		{
			for( final Iterator attributeIter = ((Collection) getClassificationMap().get(categoryIter.next())).iterator(); attributeIter.hasNext(); )
			{
				((ClassificationAttributeEntry) attributeIter.next()).save();
			}			
		}
		System.out.println( "ClassificationEditorChip.saveAttributes() ++ (ENDE) +++++++++++++++++++++++++++++++++++++" );
	}
	
	/**
	 * Returns a human readable name for the classification denoted by the given ClassificationClass (containing catalog id, version and the classificationclass name).
	 * 
	 * @param ClassificationClass the ClassificationClass for which to return the classification name
	 * @return a human readable name for the classification denoted by the given ClassificationClass
	 */
	public String getClassificationName(ClassificationClass classificationclass)
	{
		final StringBuffer name = new StringBuffer(); 
		
		final CatalogVersion version = (CatalogVersion) CatalogManager.getInstance().getCatalogVersion(classificationclass);
		
		name.append(version.getCatalog().getId() + " - " + version.getVersion() + " : " + classificationclass.getName() + " - " + classificationclass.getCode());
		
		if( getInheritedCategories().contains(classificationclass) )
		{
			// the enclosing product must be a VariantProduct and this classificationclass was 'inherited' from its baseproduct
			name.append(" [" + getDisplayState().getLocalizedString("tab.catalog.classification.definedinbase", new Object[] { ((VariantProduct) getProduct()).getBaseProduct().getCode() }) + "]");
		}
		
		return name.toString(); 
	}
			
	/**
	 * Implements {@link SpecialReloadChip} interface and reloads the classification categories and attributes.
	 */
	public void reload()
	{
		loadAttributes();
		isChanged = false;
	}

	/**
	 * Implements {@link SpecialSaveChip} interface and saves all classification attributes.
	 */
	public void save()
	{
		if( isChanged() )
		{
			saveAttributes();
		}
	}

	/**
	 * If any of the contained ClassificationAttributes was changed this will return true.
	 * 
	 * @return true if any of the contained ClassificationAttributes was changed 
	 */
	public boolean isChanged()
	{
		if( !isChanged )
		{		
			for( final Iterator categoryIter = getClassificationMap().keySet().iterator(); categoryIter.hasNext(); )
			{
				for( final Iterator attributeIter = ((Collection) getClassificationMap().get(categoryIter.next())).iterator(); attributeIter.hasNext(); )
				{
					if( ((ClassificationAttributeEntry) attributeIter.next()).isChanged() )
					{
						isChanged = true;
						return true;
					}
				}				
			}
		}

		return isChanged;
	}

	/**
	 * The {@link #OPEN_ATTRIBUTE} event is handled here. The item with the given pk will be opened
	 * in a separate window.
	 */
	public void processEvents(Map events)
	{
		if( events.containsKey(OPEN_ATTRIBUTE) )
		{
			final String pk = (String) ((List) events.get(OPEN_ATTRIBUTE)).get(0);

			if( !pk.equals("") )
			{
				try
				{
					final Item item = getJaloSession().getItem(pk);
					HMCHelper.getHMCContextFor(this).openItem(item, true);
				}
				catch( Exception e )
				{
					// if anything goes wrong, just do nothing...
					e.printStackTrace();
				}
			}
		}
	}	
}

/*
 * $Log: ClassificationEditorChip.java,v $
 * Revision 1.1.2.5  2005/10/09 20:23:57  rutten
 * CATALOG-241: added first version of classification variant list editor
 *
 * Revision 1.1.2.4  2005/10/07 12:38:28  rutten
 * CATALOG-241: classificationeditor now searches through all supercategories
 *
 * Revision 1.1.2.3  2005/10/04 14:06:19  hertz
 * CATALOG-241  (isEditable() hinzugefügt)
 *
 * Revision 1.1.2.2  2005/09/30 08:56:33  hertz
 * kommentar
 *
 * Revision 1.1.2.1  2005/09/27 16:54:59  rutten
 * CATALOG-241: classificationattribute editor is now a customchip and the
 * editors are typed
 *
 */
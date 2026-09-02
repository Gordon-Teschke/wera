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

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.enumeration.EnumerationType;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.hmc.HMCHelper;
import de.hybris.platform.hmc.attribute.AbstractAttributeEditorChip;
import de.hybris.platform.hmc.attribute.AttributeChip;
import de.hybris.platform.hmc.generic.GenericHelper;
import de.hybris.platform.hmc.generic.GenericItemChip;
import de.hybris.platform.catalog.jalo.classification.util.FeatureValue;
import java.util.*;


public class ShowPreviewChip extends AbstractAttributeEditorChip
{
    private static final String JSP_URI = "/ext/wera/showPreviewChip.jsp";

	public static final String SET_VALUE = "setvalue";
	public static final String PING = "ping";

	private CollectionType collectionType;

	private String selectionOfAttributeQualifier;

	private List possibleEntries;
	private int size = 4;

	
	/**
	 * @param displayState The current displaystate this chip belongs to.
	 * @param parent The composition parent chip.
	 * @param type The type of the new value, must not be null.
	 */
	publicShowPreviewChip(DisplayState displayState, Chip parent, CollectionType type)
	{
		super(displayState, parent);
		setCollectionType(type);
		if (type == null)
		{
			throw new IllegalArgumentException("Type must not be null");
		}
	}

	/**
	 * Process the {@link #SET_VALUE} event by which the selected values are
	 * added to this editor's collection value.
	 */
	public void processEvents(Map events)
	{
		if( events.containsKey(PING) && !events.containsKey(SET_VALUE) )
		{
			// empty selection
			if( getValue() != null && !((Collection) getValue()).isEmpty() )
			{
//System.out.println("REferenzSinleSelectEditorChip.processEvents=>"+getCollectionType().newInstance());
			setValue(getCollectionType().newInstance());
			}

			events.remove(PING);
		}

		if( events.containsKey(SET_VALUE) )
		{
			final List selectedIndexes = (List) events.get(SET_VALUE);

			final Collection values = getCollectionType().newInstance();

			for( final Iterator iter = selectedIndexes.iterator(); iter.hasNext(); )
			{
				final int index = Integer.parseInt((String) iter.next());
				if( index >= 0 && index < getPossibleEntries().size() )
				{
					// System.out.println("REferenzSingleSelectEditorChip.processEvents: index = "+index);
					values.add(getPossibleEntries().get(index));
				} else {
					// System.out.println("No value selected.");
				}
			}

			if( getValue() == null || !values.equals(getValue()))
			{
				// replace old values
				// System.out.println("REferenzSingleSelectEditorChip.processEvents=>"+values);
				setValue(values);
			}

			events.remove(SET_VALUE);
		}
	}

	public String getJSPURI()
	{
		return JSP_URI;
	}

	public CollectionType getCollectionType()
	{
		return collectionType;
	}

	public void setCollectionType(CollectionType collectionType)
	{
		this.collectionType = collectionType;
	}

	protected List createPossibleEntries()
	{
		List result = null;

		if( HMCHelper.getType(EnumerationValue.class).isAssignableFrom(getCollectionType().getElementType()) )
		{
			// elements are enumvalues, get all possible values
			result = ((EnumerationType) getCollectionType().getElementType()).getValues();
		}
		else if( getSelectionOfAttributeQualifier() != null )
		{
			// elements are items and there is a 'selection of' attribute set
			final GenericItemChip itemChip = GenericHelper.getItemChip(this);
			if( itemChip != null )
			{
				// try getting the current values from the appropriate attributechip
				final AttributeChip collectionChip = itemChip.getAttributeChip(getSelectionOfAttributeQualifier());
				if( collectionChip != null )
				{
					// get value from collection attributechip
					Object value = collectionChip.getValue();
					if( value instanceof Collection )
					{
						result = new ArrayList((Collection) value);
					}
				}
				else
				{
					// no appropriate attribute chip for the collection, get the values from item itself
					final Item item = itemChip.getItem();
					if( item != null )
					{
						try
						{
							final Collection coll = (Collection) item.getAttribute(getSelectionOfAttributeQualifier());
							if( coll != null )
							{
								result = new ArrayList(coll);
							}
						}
						catch( JaloInvalidParameterException e )
						{
							collectionChip.setError(e);
						}
						catch( JaloSecurityException e )
						{
							collectionChip.setError(e);
						}
					}
				}
			}
		}
		else
		{
			// elements are items, get all instances of the element type
			result = new ArrayList(((ComposedType) getCollectionType().getElementType()).getAllInstances());
		}

		return result != null ? result : Collections.EMPTY_LIST;
	}

	/**
	 * Returns all the possible entries for this editor.
	 *
	 * If the 'selectionof' attribute of <referencemultiselecteditor> has been set then the given
	 * attribute of the enclosing item will be used to provide the possible entries.
	 *
	 * If the element type of this editor's collectiontype is an EnumerationMetaType then all existing
	 * EnumerationValues of this type will be returned as possible entries.
	 *
	 * If the element type is 'normal' composed type then all instances of this type will be possible entries.
	 *
	 * @return a collection of items which represent the possible (allowed) entries for this collection attribute editor.
	 */
	public List getPossibleEntries()
	{
		if( possibleEntries == null )
		{
			setPossibleEntries(createPossibleEntries());
		}
		return possibleEntries;
	}

	public class DescendingComparator implements Comparator{
		public int compare(Object o1, Object o2) /*descending order*/  {
			
			// --- Initialize
			String strValue1 = "";
			String strValue2 = ""; 
			int iResult =  0;
			
			try {
				// --- Hole Values
				strValue1 = (String) ((Item) o1).getAttribute("name");
				strValue2 = (String) ((Item) o2).getAttribute("name");
				if ( strValue1 == null )
					strValue1 = "";
				if ( strValue2 == null )
					strValue2 = "";
				
			} catch (JaloInvalidParameterException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JaloSecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			// --- Try Number
			try {
				// --- Zuerst versuchen wir einen nummerischen Vergleich
				Double dValue1 = new Double (strValue1);
				Double dValue2 = new Double (strValue2);
				iResult = dValue1.compareTo(dValue2);
			}
			catch(NumberFormatException e){
				
				// --- Ok, String Vergleich
				iResult = strValue1.compareTo(strValue2);
				
			}
			
			return iResult;
		}
	}
	/**
	 * @see #getPossibleEntries()
	 */
	public void setPossibleEntries(List possibleEntries)
	{
		// --- Sortiere
	    Collections.sort( possibleEntries, new DescendingComparator() );		

		this.possibleEntries = possibleEntries;
	}

	/**
	 * Returns the index of the given item within the 'possible entries' list.
	 * Is used to reference the items in the jsp's select box.
	 *
	 * @param item the Item for which to find the index
	 * @return the index of the given item within the 'possible entries' list.
	 */
	public int indexOf(Item item)
	{
		return getPossibleEntries().indexOf(item);
	}

	/**
	 * Returns true if the given item is selected. I.e. the item is found in the collection which
	 * represents the value of this collection attribute editor.
	 *
	 * @return true if the given item is selected
	 */
	public boolean isItemSelected(Item item)
	{
	
/*	
	System.out.println("\n-----\n++++++++sItemSelected="  );
	try {
	System.out.println("item.getClass()="+ item.getClass() );
	System.out.println("item.toString()="+ item.toString() );
	System.out.println("item.name="+ item.getAttribute("name") );
	} catch ( Exception e ) {
	
	}
*/

	Collection values = ((Collection) getValue());
//Collection values = ((Collection) getPossibleEntries());
//System.out.println("RefEC-values.size()=" + values.size() );
	boolean bFound = false;
	for ( Iterator it1 = values.iterator(); it1.hasNext(); ) {
		Object obj = (Object )it1.next();
		Object obj1 = null;
		if ( obj instanceof FeatureValue )
			obj1 = ((FeatureValue)obj).getValue();
		else
			obj1 = (Object)obj;
//System.out.println("RefEC-value=>obj.getClass()="+ obj.getClass() + "/ " + obj.toString() );
//System.out.println("RefEC-item=>item.getClass()="+ item.getClass() + "/ " + item.toString() );
		if ( obj1 != null && obj1.equals(item) ) {
		    bFound = true;
			break;
		}
		   
	}
	return bFound;
/*	
	System.out.println("result="+ values != null ? values.contains(item) : false );
	
		return values != null ? values.contains(item) : false;
*/		
	}

	/**
	 * Returns the size of the select box (the number of visible rows).
	 *
	 * @return the size of the select box (the number of visible rows).
	 */
	public int getSize()
	{
		return size;
	}

	/**
	 * Sets the size of the select box (the number of visible rows).
	 *
	 * @param size The size to set.
	 */
	public void setSize( int size )
	{
		this.size = size;
	}

	/**
	 * Returns the qualifier of the attribute in which the 'source items' of this editor are found (i.e. another attribute at this editor's enlosing
	 * item contains a collection of items which is the list of possible enries for this editor).
	 */
	public String getSelectionOfAttributeQualifier()
	{
		return selectionOfAttributeQualifier;
	}

	public void setSelectionOfAttributeQualifier( String selectionOfAttributeQualifier )
	{
		this.selectionOfAttributeQualifier = selectionOfAttributeQualifier;
	}
    public boolean isChanged() {
		boolean isChanged = super.isChanged();
	//System.out.println("+++ReferenceSingleSelectEditorChip.isChanged()="+isChanged  );
			return isChanged;
	}
}

/*
 * $Log:ShowPreviewChip.java,v $
 * Revision 1.1.2.1  2005/09/19 16:48:52  rutten
 * CORE-2984: added <referencemultiselecteditor>
 *
 */
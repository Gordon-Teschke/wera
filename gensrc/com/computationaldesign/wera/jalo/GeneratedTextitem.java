/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.WeraMedia;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Textitem Textitem}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTextitem extends GenericItem
{
	/** Qualifier of the <code>Textitem.name</code> attribute **/
	public static final String NAME = "name".intern();
	/** Qualifier of the <code>Textitem.pictures4textitems</code> attribute **/
	public static final String PICTURES4TEXTITEMS = "pictures4textitems".intern();
	/** Qualifier of the <code>Textitem.header</code> attribute **/
	public static final String HEADER = "header".intern();
	/** Qualifier of the <code>Textitem.order</code> attribute **/
	public static final String ORDER = "order".intern();
	/** Qualifier of the <code>Textitem.textblock</code> attribute **/
	public static final String TEXTBLOCK = "textblock".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.header</code> attribute.
	 * @return the header - Text ist eine Ãberschrift
	 */
	public Boolean isHeader(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, HEADER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.header</code> attribute.
	 * @return the header - Text ist eine Ãberschrift
	 */
	public Boolean isHeader()
	{
		return isHeader( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.header</code> attribute. 
	 * @return the header - Text ist eine Ãberschrift
	 */
	public boolean isHeaderAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isHeader( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.header</code> attribute. 
	 * @return the header - Text ist eine Ãberschrift
	 */
	public boolean isHeaderAsPrimitive()
	{
		return isHeaderAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.header</code> attribute. 
	 * @param value the header - Text ist eine Ãberschrift
	 */
	public void setHeader(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, HEADER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.header</code> attribute. 
	 * @param value the header - Text ist eine Ãberschrift
	 */
	public void setHeader(final Boolean value)
	{
		setHeader( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.header</code> attribute. 
	 * @param value the header - Text ist eine Ãberschrift
	 */
	public void setHeader(final SessionContext ctx, final boolean value)
	{
		setHeader( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.header</code> attribute. 
	 * @param value the header - Text ist eine Ãberschrift
	 */
	public void setHeader(final boolean value)
	{
		setHeader( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.name</code> attribute.
	 * @return the name
	 */
	public abstract String getName(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.name</code> attribute.
	 * @return the name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.order</code> attribute.
	 * @return the order - Reihenfolge
	 */
	public Integer getOrder(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.order</code> attribute.
	 * @return the order - Reihenfolge
	 */
	public Integer getOrder()
	{
		return getOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.order</code> attribute. 
	 * @return the order - Reihenfolge
	 */
	public int getOrderAsPrimitive(final SessionContext ctx)
	{
		Integer value = getOrder( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.order</code> attribute. 
	 * @return the order - Reihenfolge
	 */
	public int getOrderAsPrimitive()
	{
		return getOrderAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final Integer value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final SessionContext ctx, final int value)
	{
		setOrder( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final int value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.pictures4textitems</code> attribute.
	 * @return the pictures4textitems
	 */
	public Collection<WeraMedia> getPictures4textitems(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.pictures4textitems</code> attribute.
	 * @return the pictures4textitems
	 */
	public Collection<WeraMedia> getPictures4textitems()
	{
		return getPictures4textitems( getSession().getSessionContext() );
	}
	
	public long getPictures4textitemsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null
		);
	}
	
	public long getPictures4textitemsCount()
	{
		return getPictures4textitemsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.pictures4textitems</code> attribute. 
	 * @param value the pictures4textitems
	 */
	public void setPictures4textitems(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.pictures4textitems</code> attribute. 
	 * @param value the pictures4textitems
	 */
	public void setPictures4textitems(final Collection<WeraMedia> value)
	{
		setPictures4textitems( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pictures4textitems. 
	 * @param value the item to add to pictures4textitems
	 */
	public void addToPictures4textitems(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pictures4textitems. 
	 * @param value the item to add to pictures4textitems
	 */
	public void addToPictures4textitems(final WeraMedia value)
	{
		addToPictures4textitems( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pictures4textitems. 
	 * @param value the item to remove from pictures4textitems
	 */
	public void removeFromPictures4textitems(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pictures4textitems. 
	 * @param value the item to remove from pictures4textitems
	 */
	public void removeFromPictures4textitems(final WeraMedia value)
	{
		removeFromPictures4textitems( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.textblock</code> attribute.
	 * @return the textblock - Text
	 */
	public String getTextblock(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTextitem.getTextblock requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TEXTBLOCK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.textblock</code> attribute.
	 * @return the textblock - Text
	 */
	public String getTextblock()
	{
		return getTextblock( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.textblock</code> attribute. 
	 * @return the localized textblock - Text
	 */
	public Map<Language,String> getAllTextblock(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TEXTBLOCK,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textitem.textblock</code> attribute. 
	 * @return the localized textblock - Text
	 */
	public Map<Language,String> getAllTextblock()
	{
		return getAllTextblock( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.textblock</code> attribute. 
	 * @param value the textblock - Text
	 */
	public void setTextblock(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTextitem.setTextblock requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TEXTBLOCK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.textblock</code> attribute. 
	 * @param value the textblock - Text
	 */
	public void setTextblock(final String value)
	{
		setTextblock( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.textblock</code> attribute. 
	 * @param value the textblock - Text
	 */
	public void setAllTextblock(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TEXTBLOCK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textitem.textblock</code> attribute. 
	 * @param value the textblock - Text
	 */
	public void setAllTextblock(final Map<Language,String> value)
	{
		setAllTextblock( getSession().getSessionContext(), value );
	}
	
}

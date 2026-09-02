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
 * Generated class for type {@link com.computationaldesign.wera.jalo.Tips Tips}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTips extends GenericItem
{
	/** Qualifier of the <code>Tips.aktiv</code> attribute **/
	public static final String AKTIV = "aktiv".intern();
	/** Qualifier of the <code>Tips.order</code> attribute **/
	public static final String ORDER = "order".intern();
	/** Qualifier of the <code>Tips.icon</code> attribute **/
	public static final String ICON = "icon".intern();
	/** Qualifier of the <code>Tips.name</code> attribute **/
	public static final String NAME = "name".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.aktiv</code> attribute.
	 * @return the aktiv
	 */
	public Boolean isAktiv(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTips.isAktiv requires a session language", 0 );
		}
		return (Boolean)getLocalizedProperty( ctx, AKTIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.aktiv</code> attribute.
	 * @return the aktiv
	 */
	public Boolean isAktiv()
	{
		return isAktiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.aktiv</code> attribute. 
	 * @return the aktiv
	 */
	public boolean isAktivAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isAktiv( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.aktiv</code> attribute. 
	 * @return the aktiv
	 */
	public boolean isAktivAsPrimitive()
	{
		return isAktivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.aktiv</code> attribute. 
	 * @return the localized aktiv
	 */
	public Map<Language,Boolean> getAllAktiv(final SessionContext ctx)
	{
		return (Map<Language,Boolean>)getAllLocalizedProperties(ctx,AKTIV,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.aktiv</code> attribute. 
	 * @return the localized aktiv
	 */
	public Map<Language,Boolean> getAllAktiv()
	{
		return getAllAktiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.aktiv</code> attribute. 
	 * @param value the aktiv
	 */
	public void setAktiv(final SessionContext ctx, final Boolean value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTips.setAktiv requires a session language", 0 );
		}
		setLocalizedProperty(ctx, AKTIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.aktiv</code> attribute. 
	 * @param value the aktiv
	 */
	public void setAktiv(final Boolean value)
	{
		setAktiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.aktiv</code> attribute. 
	 * @param value the aktiv
	 */
	public void setAktiv(final SessionContext ctx, final boolean value)
	{
		setAktiv( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.aktiv</code> attribute. 
	 * @param value the aktiv
	 */
	public void setAktiv(final boolean value)
	{
		setAktiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.aktiv</code> attribute. 
	 * @param value the aktiv
	 */
	public void setAllAktiv(final SessionContext ctx, final Map<Language,Boolean> value)
	{
		setAllLocalizedProperties(ctx,AKTIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.aktiv</code> attribute. 
	 * @param value the aktiv
	 */
	public void setAllAktiv(final Map<Language,Boolean> value)
	{
		setAllAktiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.icon</code> attribute.
	 * @return the icon
	 */
	public Collection<WeraMedia> getIcon(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.icon</code> attribute.
	 * @return the icon
	 */
	public Collection<WeraMedia> getIcon()
	{
		return getIcon( getSession().getSessionContext() );
	}
	
	public long getIconCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null
		);
	}
	
	public long getIconCount()
	{
		return getIconCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.icon</code> attribute. 
	 * @param value the icon
	 */
	public void setIcon(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.icon</code> attribute. 
	 * @param value the icon
	 */
	public void setIcon(final Collection<WeraMedia> value)
	{
		setIcon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icon. 
	 * @param value the item to add to icon
	 */
	public void addToIcon(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icon. 
	 * @param value the item to add to icon
	 */
	public void addToIcon(final WeraMedia value)
	{
		addToIcon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icon. 
	 * @param value the item to remove from icon
	 */
	public void removeFromIcon(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icon. 
	 * @param value the item to remove from icon
	 */
	public void removeFromIcon(final WeraMedia value)
	{
		removeFromIcon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.name</code> attribute.
	 * @return the name - Text
	 */
	public String getName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTips.getName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.name</code> attribute.
	 * @return the name - Text
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.name</code> attribute. 
	 * @return the localized name - Text
	 */
	public Map<Language,String> getAllName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.name</code> attribute. 
	 * @return the localized name - Text
	 */
	public Map<Language,String> getAllName()
	{
		return getAllName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.name</code> attribute. 
	 * @param value the name - Text
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTips.setName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.name</code> attribute. 
	 * @param value the name - Text
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.name</code> attribute. 
	 * @param value the name - Text
	 */
	public void setAllName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.name</code> attribute. 
	 * @param value the name - Text
	 */
	public void setAllName(final Map<Language,String> value)
	{
		setAllName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.order</code> attribute.
	 * @return the order - Reihenfolge
	 */
	public Integer getOrder(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.order</code> attribute.
	 * @return the order - Reihenfolge
	 */
	public Integer getOrder()
	{
		return getOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.order</code> attribute. 
	 * @return the order - Reihenfolge
	 */
	public int getOrderAsPrimitive(final SessionContext ctx)
	{
		Integer value = getOrder( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tips.order</code> attribute. 
	 * @return the order - Reihenfolge
	 */
	public int getOrderAsPrimitive()
	{
		return getOrderAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final Integer value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final SessionContext ctx, final int value)
	{
		setOrder( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tips.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final int value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
}

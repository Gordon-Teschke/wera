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
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Outputcontrol Outputcontrol}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedOutputcontrol extends GenericItem
{
	/** Qualifier of the <code>Outputcontrol.showinset_onlineshops</code> attribute **/
	public static final String SHOWINSET_ONLINESHOPS = "showinset_onlineshops".intern();
	/** Qualifier of the <code>Outputcontrol.visibility</code> attribute **/
	public static final String VISIBILITY = "visibility".intern();
	/** Qualifier of the <code>Outputcontrol.order</code> attribute **/
	public static final String ORDER = "order".intern();
	/** Qualifier of the <code>Outputcontrol.unitca</code> attribute **/
	public static final String UNITCA = "unitca".intern();
	/** Qualifier of the <code>Outputcontrol.icons</code> attribute **/
	public static final String ICONS = "icons".intern();
	/** Qualifier of the <code>Outputcontrol.background</code> attribute **/
	public static final String BACKGROUND = "background".intern();
	/** Qualifier of the <code>Outputcontrol.showinset</code> attribute **/
	public static final String SHOWINSET = "showinset".intern();
	/** Qualifier of the <code>Outputcontrol.bezeichnung</code> attribute **/
	public static final String BEZEICHNUNG = "bezeichnung".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.background</code> attribute.
	 * @return the background - Hintergrundfarbe J/N
	 */
	public Boolean isBackground(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedOutputcontrol.isBackground requires a session language", 0 );
		}
		return (Boolean)getLocalizedProperty( ctx, BACKGROUND);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.background</code> attribute.
	 * @return the background - Hintergrundfarbe J/N
	 */
	public Boolean isBackground()
	{
		return isBackground( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.background</code> attribute. 
	 * @return the background - Hintergrundfarbe J/N
	 */
	public boolean isBackgroundAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isBackground( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.background</code> attribute. 
	 * @return the background - Hintergrundfarbe J/N
	 */
	public boolean isBackgroundAsPrimitive()
	{
		return isBackgroundAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.background</code> attribute. 
	 * @return the localized background - Hintergrundfarbe J/N
	 */
	public Map<Language,Boolean> getAllBackground(final SessionContext ctx)
	{
		return (Map<Language,Boolean>)getAllLocalizedProperties(ctx,BACKGROUND,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.background</code> attribute. 
	 * @return the localized background - Hintergrundfarbe J/N
	 */
	public Map<Language,Boolean> getAllBackground()
	{
		return getAllBackground( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setBackground(final SessionContext ctx, final Boolean value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedOutputcontrol.setBackground requires a session language", 0 );
		}
		setLocalizedProperty(ctx, BACKGROUND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setBackground(final Boolean value)
	{
		setBackground( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setBackground(final SessionContext ctx, final boolean value)
	{
		setBackground( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setBackground(final boolean value)
	{
		setBackground( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setAllBackground(final SessionContext ctx, final Map<Language,Boolean> value)
	{
		setAllLocalizedProperties(ctx,BACKGROUND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setAllBackground(final Map<Language,Boolean> value)
	{
		setAllBackground( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.bezeichnung</code> attribute.
	 * @return the bezeichnung
	 */
	public abstract String getBezeichnung(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.bezeichnung</code> attribute.
	 * @return the bezeichnung
	 */
	public String getBezeichnung()
	{
		return getBezeichnung( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.icons</code> attribute.
	 * @return the icons
	 */
	public Collection<WeraMedia> getIcons(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.icons</code> attribute.
	 * @return the icons
	 */
	public Collection<WeraMedia> getIcons()
	{
		return getIcons( getSession().getSessionContext() );
	}
	
	public long getIconsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null
		);
	}
	
	public long getIconsCount()
	{
		return getIconsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.icons</code> attribute. 
	 * @param value the icons
	 */
	public void setIcons(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.icons</code> attribute. 
	 * @param value the icons
	 */
	public void setIcons(final Collection<WeraMedia> value)
	{
		setIcons( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icons. 
	 * @param value the item to add to icons
	 */
	public void addToIcons(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icons. 
	 * @param value the item to add to icons
	 */
	public void addToIcons(final WeraMedia value)
	{
		addToIcons( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icons. 
	 * @param value the item to remove from icons
	 */
	public void removeFromIcons(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icons. 
	 * @param value the item to remove from icons
	 */
	public void removeFromIcons(final WeraMedia value)
	{
		removeFromIcons( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.order</code> attribute.
	 * @return the order - Reihenfolge Merkmal
	 */
	public Integer getOrder(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.order</code> attribute.
	 * @return the order - Reihenfolge Merkmal
	 */
	public Integer getOrder()
	{
		return getOrder( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.order</code> attribute. 
	 * @return the order - Reihenfolge Merkmal
	 */
	public int getOrderAsPrimitive(final SessionContext ctx)
	{
		Integer value = getOrder( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.order</code> attribute. 
	 * @return the order - Reihenfolge Merkmal
	 */
	public int getOrderAsPrimitive()
	{
		return getOrderAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.order</code> attribute. 
	 * @param value the order - Reihenfolge Merkmal
	 */
	public void setOrder(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.order</code> attribute. 
	 * @param value the order - Reihenfolge Merkmal
	 */
	public void setOrder(final Integer value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.order</code> attribute. 
	 * @param value the order - Reihenfolge Merkmal
	 */
	public void setOrder(final SessionContext ctx, final int value)
	{
		setOrder( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.order</code> attribute. 
	 * @param value the order - Reihenfolge Merkmal
	 */
	public void setOrder(final int value)
	{
		setOrder( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.showinset</code> attribute.
	 * @return the showinset - Anzeige im Satz
	 */
	public Boolean isShowinset(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOWINSET);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.showinset</code> attribute.
	 * @return the showinset - Anzeige im Satz
	 */
	public Boolean isShowinset()
	{
		return isShowinset( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.showinset</code> attribute. 
	 * @return the showinset - Anzeige im Satz
	 */
	public boolean isShowinsetAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShowinset( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.showinset</code> attribute. 
	 * @return the showinset - Anzeige im Satz
	 */
	public boolean isShowinsetAsPrimitive()
	{
		return isShowinsetAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.showinset</code> attribute. 
	 * @param value the showinset - Anzeige im Satz
	 */
	public void setShowinset(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOWINSET,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.showinset</code> attribute. 
	 * @param value the showinset - Anzeige im Satz
	 */
	public void setShowinset(final Boolean value)
	{
		setShowinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.showinset</code> attribute. 
	 * @param value the showinset - Anzeige im Satz
	 */
	public void setShowinset(final SessionContext ctx, final boolean value)
	{
		setShowinset( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.showinset</code> attribute. 
	 * @param value the showinset - Anzeige im Satz
	 */
	public void setShowinset(final boolean value)
	{
		setShowinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.showinset_onlineshops</code> attribute.
	 * @return the showinset_onlineshops - Anzeige im Satz Online Shops
	 */
	public Boolean isShowinset_onlineshops(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOWINSET_ONLINESHOPS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.showinset_onlineshops</code> attribute.
	 * @return the showinset_onlineshops - Anzeige im Satz Online Shops
	 */
	public Boolean isShowinset_onlineshops()
	{
		return isShowinset_onlineshops( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.showinset_onlineshops</code> attribute. 
	 * @return the showinset_onlineshops - Anzeige im Satz Online Shops
	 */
	public boolean isShowinset_onlineshopsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShowinset_onlineshops( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.showinset_onlineshops</code> attribute. 
	 * @return the showinset_onlineshops - Anzeige im Satz Online Shops
	 */
	public boolean isShowinset_onlineshopsAsPrimitive()
	{
		return isShowinset_onlineshopsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.showinset_onlineshops</code> attribute. 
	 * @param value the showinset_onlineshops - Anzeige im Satz Online Shops
	 */
	public void setShowinset_onlineshops(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOWINSET_ONLINESHOPS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.showinset_onlineshops</code> attribute. 
	 * @param value the showinset_onlineshops - Anzeige im Satz Online Shops
	 */
	public void setShowinset_onlineshops(final Boolean value)
	{
		setShowinset_onlineshops( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.showinset_onlineshops</code> attribute. 
	 * @param value the showinset_onlineshops - Anzeige im Satz Online Shops
	 */
	public void setShowinset_onlineshops(final SessionContext ctx, final boolean value)
	{
		setShowinset_onlineshops( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.showinset_onlineshops</code> attribute. 
	 * @param value the showinset_onlineshops - Anzeige im Satz Online Shops
	 */
	public void setShowinset_onlineshops(final boolean value)
	{
		setShowinset_onlineshops( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.unitca</code> attribute.
	 * @return the unitca
	 */
	public String getUnitca(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedOutputcontrol.getUnitca requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, UNITCA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.unitca</code> attribute.
	 * @return the unitca
	 */
	public String getUnitca()
	{
		return getUnitca( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.unitca</code> attribute. 
	 * @return the localized unitca
	 */
	public Map<Language,String> getAllUnitca(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,UNITCA,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.unitca</code> attribute. 
	 * @return the localized unitca
	 */
	public Map<Language,String> getAllUnitca()
	{
		return getAllUnitca( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.unitca</code> attribute. 
	 * @param value the unitca
	 */
	public void setUnitca(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedOutputcontrol.setUnitca requires a session language", 0 );
		}
		setLocalizedProperty(ctx, UNITCA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.unitca</code> attribute. 
	 * @param value the unitca
	 */
	public void setUnitca(final String value)
	{
		setUnitca( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.unitca</code> attribute. 
	 * @param value the unitca
	 */
	public void setAllUnitca(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,UNITCA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.unitca</code> attribute. 
	 * @param value the unitca
	 */
	public void setAllUnitca(final Map<Language,String> value)
	{
		setAllUnitca( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.visibility</code> attribute.
	 * @return the visibility - Visibility of attribute: visible, not_visible, visible_in_base or visible_in_variant
	 */
	public EnumerationValue getVisibility(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, VISIBILITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Outputcontrol.visibility</code> attribute.
	 * @return the visibility - Visibility of attribute: visible, not_visible, visible_in_base or visible_in_variant
	 */
	public EnumerationValue getVisibility()
	{
		return getVisibility( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.visibility</code> attribute. 
	 * @param value the visibility - Visibility of attribute: visible, not_visible, visible_in_base or visible_in_variant
	 */
	public void setVisibility(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, VISIBILITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Outputcontrol.visibility</code> attribute. 
	 * @param value the visibility - Visibility of attribute: visible, not_visible, visible_in_base or visible_in_variant
	 */
	public void setVisibility(final EnumerationValue value)
	{
		setVisibility( getSession().getSessionContext(), value );
	}
	
}

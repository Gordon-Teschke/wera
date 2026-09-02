/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.Weblink;
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
 * Generated class for type {@link com.computationaldesign.wera.jalo.WeblinkText WeblinkText}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeblinkText extends GenericItem
{
	/** Qualifier of the <code>WeblinkText.weblink2weblinktext</code> attribute **/
	public static final String WEBLINK2WEBLINKTEXT = "weblink2weblinktext".intern();
	/** Qualifier of the <code>WeblinkText.textblock</code> attribute **/
	public static final String TEXTBLOCK = "textblock".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeblinkText.textblock</code> attribute.
	 * @return the textblock - Weblink-Text
	 */
	public String getTextblock(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblinkText.getTextblock requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TEXTBLOCK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeblinkText.textblock</code> attribute.
	 * @return the textblock - Weblink-Text
	 */
	public String getTextblock()
	{
		return getTextblock( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeblinkText.textblock</code> attribute. 
	 * @return the localized textblock - Weblink-Text
	 */
	public Map<Language,String> getAllTextblock(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TEXTBLOCK,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeblinkText.textblock</code> attribute. 
	 * @return the localized textblock - Weblink-Text
	 */
	public Map<Language,String> getAllTextblock()
	{
		return getAllTextblock( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeblinkText.textblock</code> attribute. 
	 * @param value the textblock - Weblink-Text
	 */
	public void setTextblock(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblinkText.setTextblock requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TEXTBLOCK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeblinkText.textblock</code> attribute. 
	 * @param value the textblock - Weblink-Text
	 */
	public void setTextblock(final String value)
	{
		setTextblock( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeblinkText.textblock</code> attribute. 
	 * @param value the textblock - Weblink-Text
	 */
	public void setAllTextblock(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TEXTBLOCK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeblinkText.textblock</code> attribute. 
	 * @param value the textblock - Weblink-Text
	 */
	public void setAllTextblock(final Map<Language,String> value)
	{
		setAllTextblock( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeblinkText.weblink2weblinktext</code> attribute.
	 * @return the weblink2weblinktext
	 */
	public Collection<Weblink> getWeblink2weblinktext(final SessionContext ctx)
	{
		final List<Weblink> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeblinkText.weblink2weblinktext</code> attribute.
	 * @return the weblink2weblinktext
	 */
	public Collection<Weblink> getWeblink2weblinktext()
	{
		return getWeblink2weblinktext( getSession().getSessionContext() );
	}
	
	public long getWeblink2weblinktextCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null
		);
	}
	
	public long getWeblink2weblinktextCount()
	{
		return getWeblink2weblinktextCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeblinkText.weblink2weblinktext</code> attribute. 
	 * @param value the weblink2weblinktext
	 */
	public void setWeblink2weblinktext(final SessionContext ctx, final Collection<Weblink> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeblinkText.weblink2weblinktext</code> attribute. 
	 * @param value the weblink2weblinktext
	 */
	public void setWeblink2weblinktext(final Collection<Weblink> value)
	{
		setWeblink2weblinktext( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblink2weblinktext. 
	 * @param value the item to add to weblink2weblinktext
	 */
	public void addToWeblink2weblinktext(final SessionContext ctx, final Weblink value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblink2weblinktext. 
	 * @param value the item to add to weblink2weblinktext
	 */
	public void addToWeblink2weblinktext(final Weblink value)
	{
		addToWeblink2weblinktext( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblink2weblinktext. 
	 * @param value the item to remove from weblink2weblinktext
	 */
	public void removeFromWeblink2weblinktext(final SessionContext ctx, final Weblink value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblink2weblinktext. 
	 * @param value the item to remove from weblink2weblinktext
	 */
	public void removeFromWeblink2weblinktext(final Weblink value)
	{
		removeFromWeblink2weblinktext( getSession().getSessionContext(), value );
	}
	
}

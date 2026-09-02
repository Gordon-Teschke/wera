/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraProductSetinSet;
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
 * Generated class for type {@link com.computationaldesign.wera.jalo.ExtImage ExtImage}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedExtImage extends GenericItem
{
	/** Qualifier of the <code>ExtImage.title</code> attribute **/
	public static final String TITLE = "title".intern();
	/** Qualifier of the <code>ExtImage.preview</code> attribute **/
	public static final String PREVIEW = "preview".intern();
	/** Qualifier of the <code>ExtImage.code</code> attribute **/
	public static final String CODE = "code".intern();
	/** Qualifier of the <code>ExtImage.weraproductsetinsets</code> attribute **/
	public static final String WERAPRODUCTSETINSETS = "weraproductsetinsets".intern();
	/** Qualifier of the <code>ExtImage.description</code> attribute **/
	public static final String DESCRIPTION = "description".intern();
	/** Qualifier of the <code>ExtImage.weraproducts</code> attribute **/
	public static final String WERAPRODUCTS = "weraproducts".intern();
	/** Qualifier of the <code>ExtImage.location</code> attribute **/
	public static final String LOCATION = "location".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.description</code> attribute.
	 * @return the description - Bildbeschreibung
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedExtImage.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.description</code> attribute.
	 * @return the description - Bildbeschreibung
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.description</code> attribute. 
	 * @return the localized description - Bildbeschreibung
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.description</code> attribute. 
	 * @return the localized description - Bildbeschreibung
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.description</code> attribute. 
	 * @param value the description - Bildbeschreibung
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedExtImage.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.description</code> attribute. 
	 * @param value the description - Bildbeschreibung
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.description</code> attribute. 
	 * @param value the description - Bildbeschreibung
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.description</code> attribute. 
	 * @param value the description - Bildbeschreibung
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.location</code> attribute.
	 * @return the location - Bild URL
	 */
	public String getLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.location</code> attribute.
	 * @return the location - Bild URL
	 */
	public String getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.location</code> attribute. 
	 * @param value the location - Bild URL
	 */
	public void setLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.location</code> attribute. 
	 * @param value the location - Bild URL
	 */
	public void setLocation(final String value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.preview</code> attribute.
	 * @return the preview
	 */
	public abstract String getPreview(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.preview</code> attribute.
	 * @return the preview
	 */
	public String getPreview()
	{
		return getPreview( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.title</code> attribute.
	 * @return the title - Bild-Titel
	 */
	public String getTitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedExtImage.getTitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.title</code> attribute.
	 * @return the title - Bild-Titel
	 */
	public String getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.title</code> attribute. 
	 * @return the localized title - Bild-Titel
	 */
	public Map<Language,String> getAllTitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.title</code> attribute. 
	 * @return the localized title - Bild-Titel
	 */
	public Map<Language,String> getAllTitle()
	{
		return getAllTitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.title</code> attribute. 
	 * @param value the title - Bild-Titel
	 */
	public void setTitle(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedExtImage.setTitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.title</code> attribute. 
	 * @param value the title - Bild-Titel
	 */
	public void setTitle(final String value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.title</code> attribute. 
	 * @param value the title - Bild-Titel
	 */
	public void setAllTitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.title</code> attribute. 
	 * @param value the title - Bild-Titel
	 */
	public void setAllTitle(final Map<Language,String> value)
	{
		setAllTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.weraproducts</code> attribute.
	 * @return the weraproducts
	 */
	public Collection<WeraProduct> getWeraproducts(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.weraproducts</code> attribute.
	 * @return the weraproducts
	 */
	public Collection<WeraProduct> getWeraproducts()
	{
		return getWeraproducts( getSession().getSessionContext() );
	}
	
	public long getWeraproductsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null
		);
	}
	
	public long getWeraproductsCount()
	{
		return getWeraproductsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.weraproducts</code> attribute. 
	 * @param value the weraproducts
	 */
	public void setWeraproducts(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.weraproducts</code> attribute. 
	 * @param value the weraproducts
	 */
	public void setWeraproducts(final Collection<WeraProduct> value)
	{
		setWeraproducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproducts. 
	 * @param value the item to add to weraproducts
	 */
	public void addToWeraproducts(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproducts. 
	 * @param value the item to add to weraproducts
	 */
	public void addToWeraproducts(final WeraProduct value)
	{
		addToWeraproducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproducts. 
	 * @param value the item to remove from weraproducts
	 */
	public void removeFromWeraproducts(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproducts. 
	 * @param value the item to remove from weraproducts
	 */
	public void removeFromWeraproducts(final WeraProduct value)
	{
		removeFromWeraproducts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.weraproductsetinsets</code> attribute.
	 * @return the weraproductsetinsets
	 */
	public Collection<WeraProductSetinSet> getWeraproductsetinsets(final SessionContext ctx)
	{
		final List<WeraProductSetinSet> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ExtImage.weraproductsetinsets</code> attribute.
	 * @return the weraproductsetinsets
	 */
	public Collection<WeraProductSetinSet> getWeraproductsetinsets()
	{
		return getWeraproductsetinsets( getSession().getSessionContext() );
	}
	
	public long getWeraproductsetinsetsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null
		);
	}
	
	public long getWeraproductsetinsetsCount()
	{
		return getWeraproductsetinsetsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.weraproductsetinsets</code> attribute. 
	 * @param value the weraproductsetinsets
	 */
	public void setWeraproductsetinsets(final SessionContext ctx, final Collection<WeraProductSetinSet> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ExtImage.weraproductsetinsets</code> attribute. 
	 * @param value the weraproductsetinsets
	 */
	public void setWeraproductsetinsets(final Collection<WeraProductSetinSet> value)
	{
		setWeraproductsetinsets( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetinsets. 
	 * @param value the item to add to weraproductsetinsets
	 */
	public void addToWeraproductsetinsets(final SessionContext ctx, final WeraProductSetinSet value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetinsets. 
	 * @param value the item to add to weraproductsetinsets
	 */
	public void addToWeraproductsetinsets(final WeraProductSetinSet value)
	{
		addToWeraproductsetinsets( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetinsets. 
	 * @param value the item to remove from weraproductsetinsets
	 */
	public void removeFromWeraproductsetinsets(final SessionContext ctx, final WeraProductSetinSet value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetinsets. 
	 * @param value the item to remove from weraproductsetinsets
	 */
	public void removeFromWeraproductsetinsets(final WeraProductSetinSet value)
	{
		removeFromWeraproductsetinsets( getSession().getSessionContext(), value );
	}
	
}

/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.Bildreferenz;
import com.computationaldesign.wera.jalo.WeraProduct;
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
 * Generated class for type {@link com.computationaldesign.wera.jalo.AbtriebSchraubprofil AbtriebSchraubprofil}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAbtriebSchraubprofil extends GenericItem
{
	/** Qualifier of the <code>AbtriebSchraubprofil.code</code> attribute **/
	public static final String CODE = "code".intern();
	/** Qualifier of the <code>AbtriebSchraubprofil.name</code> attribute **/
	public static final String NAME = "name".intern();
	/** Qualifier of the <code>AbtriebSchraubprofil.imageref2abtriebSchraubprofil</code> attribute **/
	public static final String IMAGEREF2ABTRIEBSCHRAUBPROFIL = "imageref2abtriebSchraubprofil".intern();
	/** Qualifier of the <code>AbtriebSchraubprofil.products2abtriebSchraubprofil</code> attribute **/
	public static final String PRODUCTS2ABTRIEBSCHRAUBPROFIL = "products2abtriebSchraubprofil".intern();
	/** Qualifier of the <code>AbtriebSchraubprofil.description</code> attribute **/
	public static final String DESCRIPTION = "description".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.description</code> attribute.
	 * @return the description - AbtriebSchraubprofil Beschreibung
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAbtriebSchraubprofil.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.description</code> attribute.
	 * @return the description - AbtriebSchraubprofil Beschreibung
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.description</code> attribute. 
	 * @return the localized description - AbtriebSchraubprofil Beschreibung
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.description</code> attribute. 
	 * @return the localized description - AbtriebSchraubprofil Beschreibung
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.description</code> attribute. 
	 * @param value the description - AbtriebSchraubprofil Beschreibung
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAbtriebSchraubprofil.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.description</code> attribute. 
	 * @param value the description - AbtriebSchraubprofil Beschreibung
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.description</code> attribute. 
	 * @param value the description - AbtriebSchraubprofil Beschreibung
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.description</code> attribute. 
	 * @param value the description - AbtriebSchraubprofil Beschreibung
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.imageref2abtriebSchraubprofil</code> attribute.
	 * @return the imageref2abtriebSchraubprofil
	 */
	public Collection<Bildreferenz> getImageref2abtriebSchraubprofil(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.imageref2abtriebSchraubprofil</code> attribute.
	 * @return the imageref2abtriebSchraubprofil
	 */
	public Collection<Bildreferenz> getImageref2abtriebSchraubprofil()
	{
		return getImageref2abtriebSchraubprofil( getSession().getSessionContext() );
	}
	
	public long getImageref2abtriebSchraubprofilCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null
		);
	}
	
	public long getImageref2abtriebSchraubprofilCount()
	{
		return getImageref2abtriebSchraubprofilCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.imageref2abtriebSchraubprofil</code> attribute. 
	 * @param value the imageref2abtriebSchraubprofil
	 */
	public void setImageref2abtriebSchraubprofil(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.imageref2abtriebSchraubprofil</code> attribute. 
	 * @param value the imageref2abtriebSchraubprofil
	 */
	public void setImageref2abtriebSchraubprofil(final Collection<Bildreferenz> value)
	{
		setImageref2abtriebSchraubprofil( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imageref2abtriebSchraubprofil. 
	 * @param value the item to add to imageref2abtriebSchraubprofil
	 */
	public void addToImageref2abtriebSchraubprofil(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imageref2abtriebSchraubprofil. 
	 * @param value the item to add to imageref2abtriebSchraubprofil
	 */
	public void addToImageref2abtriebSchraubprofil(final Bildreferenz value)
	{
		addToImageref2abtriebSchraubprofil( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imageref2abtriebSchraubprofil. 
	 * @param value the item to remove from imageref2abtriebSchraubprofil
	 */
	public void removeFromImageref2abtriebSchraubprofil(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imageref2abtriebSchraubprofil. 
	 * @param value the item to remove from imageref2abtriebSchraubprofil
	 */
	public void removeFromImageref2abtriebSchraubprofil(final Bildreferenz value)
	{
		removeFromImageref2abtriebSchraubprofil( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.name</code> attribute.
	 * @return the name - AbtriebSchraubprofil Name
	 */
	public String getName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAbtriebSchraubprofil.getName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.name</code> attribute.
	 * @return the name - AbtriebSchraubprofil Name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.name</code> attribute. 
	 * @return the localized name - AbtriebSchraubprofil Name
	 */
	public Map<Language,String> getAllName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.name</code> attribute. 
	 * @return the localized name - AbtriebSchraubprofil Name
	 */
	public Map<Language,String> getAllName()
	{
		return getAllName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.name</code> attribute. 
	 * @param value the name - AbtriebSchraubprofil Name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAbtriebSchraubprofil.setName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.name</code> attribute. 
	 * @param value the name - AbtriebSchraubprofil Name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.name</code> attribute. 
	 * @param value the name - AbtriebSchraubprofil Name
	 */
	public void setAllName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.name</code> attribute. 
	 * @param value the name - AbtriebSchraubprofil Name
	 */
	public void setAllName(final Map<Language,String> value)
	{
		setAllName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.products2abtriebSchraubprofil</code> attribute.
	 * @return the products2abtriebSchraubprofil
	 */
	public Collection<WeraProduct> getProducts2abtriebSchraubprofil(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebSchraubprofil.products2abtriebSchraubprofil</code> attribute.
	 * @return the products2abtriebSchraubprofil
	 */
	public Collection<WeraProduct> getProducts2abtriebSchraubprofil()
	{
		return getProducts2abtriebSchraubprofil( getSession().getSessionContext() );
	}
	
	public long getProducts2abtriebSchraubprofilCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null
		);
	}
	
	public long getProducts2abtriebSchraubprofilCount()
	{
		return getProducts2abtriebSchraubprofilCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.products2abtriebSchraubprofil</code> attribute. 
	 * @param value the products2abtriebSchraubprofil
	 */
	public void setProducts2abtriebSchraubprofil(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebSchraubprofil.products2abtriebSchraubprofil</code> attribute. 
	 * @param value the products2abtriebSchraubprofil
	 */
	public void setProducts2abtriebSchraubprofil(final Collection<WeraProduct> value)
	{
		setProducts2abtriebSchraubprofil( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products2abtriebSchraubprofil. 
	 * @param value the item to add to products2abtriebSchraubprofil
	 */
	public void addToProducts2abtriebSchraubprofil(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products2abtriebSchraubprofil. 
	 * @param value the item to add to products2abtriebSchraubprofil
	 */
	public void addToProducts2abtriebSchraubprofil(final WeraProduct value)
	{
		addToProducts2abtriebSchraubprofil( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products2abtriebSchraubprofil. 
	 * @param value the item to remove from products2abtriebSchraubprofil
	 */
	public void removeFromProducts2abtriebSchraubprofil(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products2abtriebSchraubprofil. 
	 * @param value the item to remove from products2abtriebSchraubprofil
	 */
	public void removeFromProducts2abtriebSchraubprofil(final WeraProduct value)
	{
		removeFromProducts2abtriebSchraubprofil( getSession().getSessionContext(), value );
	}
	
}

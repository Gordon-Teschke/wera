/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.AntriebAnschluss;
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
 * Generated class for type {@link com.computationaldesign.wera.jalo.AbtriebAnschluss AbtriebAnschluss}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAbtriebAnschluss extends GenericItem
{
	/** Qualifier of the <code>AbtriebAnschluss.name</code> attribute **/
	public static final String NAME = "name".intern();
	/** Qualifier of the <code>AbtriebAnschluss.products2abtriebAnschluss</code> attribute **/
	public static final String PRODUCTS2ABTRIEBANSCHLUSS = "products2abtriebAnschluss".intern();
	/** Qualifier of the <code>AbtriebAnschluss.code</code> attribute **/
	public static final String CODE = "code".intern();
	/** Qualifier of the <code>AbtriebAnschluss.imageref2abtriebAnschluss</code> attribute **/
	public static final String IMAGEREF2ABTRIEBANSCHLUSS = "imageref2abtriebAnschluss".intern();
	/** Qualifier of the <code>AbtriebAnschluss.abtriebAnschlussTo</code> attribute **/
	public static final String ABTRIEBANSCHLUSSTO = "abtriebAnschlussTo".intern();
	/** Qualifier of the <code>AbtriebAnschluss.description</code> attribute **/
	public static final String DESCRIPTION = "description".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.abtriebAnschlussTo</code> attribute.
	 * @return the abtriebAnschlussTo
	 */
	public Collection<AntriebAnschluss> getAbtriebAnschlussTo(final SessionContext ctx)
	{
		final List<AntriebAnschluss> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.abtriebAnschlussTo</code> attribute.
	 * @return the abtriebAnschlussTo
	 */
	public Collection<AntriebAnschluss> getAbtriebAnschlussTo()
	{
		return getAbtriebAnschlussTo( getSession().getSessionContext() );
	}
	
	public long getAbtriebAnschlussToCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null
		);
	}
	
	public long getAbtriebAnschlussToCount()
	{
		return getAbtriebAnschlussToCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.abtriebAnschlussTo</code> attribute. 
	 * @param value the abtriebAnschlussTo
	 */
	public void setAbtriebAnschlussTo(final SessionContext ctx, final Collection<AntriebAnschluss> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.abtriebAnschlussTo</code> attribute. 
	 * @param value the abtriebAnschlussTo
	 */
	public void setAbtriebAnschlussTo(final Collection<AntriebAnschluss> value)
	{
		setAbtriebAnschlussTo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebAnschlussTo. 
	 * @param value the item to add to abtriebAnschlussTo
	 */
	public void addToAbtriebAnschlussTo(final SessionContext ctx, final AntriebAnschluss value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebAnschlussTo. 
	 * @param value the item to add to abtriebAnschlussTo
	 */
	public void addToAbtriebAnschlussTo(final AntriebAnschluss value)
	{
		addToAbtriebAnschlussTo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebAnschlussTo. 
	 * @param value the item to remove from abtriebAnschlussTo
	 */
	public void removeFromAbtriebAnschlussTo(final SessionContext ctx, final AntriebAnschluss value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebAnschlussTo. 
	 * @param value the item to remove from abtriebAnschlussTo
	 */
	public void removeFromAbtriebAnschlussTo(final AntriebAnschluss value)
	{
		removeFromAbtriebAnschlussTo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.description</code> attribute.
	 * @return the description - AbtriebAnschluss Beschreibung
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAbtriebAnschluss.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.description</code> attribute.
	 * @return the description - AbtriebAnschluss Beschreibung
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.description</code> attribute. 
	 * @return the localized description - AbtriebAnschluss Beschreibung
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.description</code> attribute. 
	 * @return the localized description - AbtriebAnschluss Beschreibung
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.description</code> attribute. 
	 * @param value the description - AbtriebAnschluss Beschreibung
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAbtriebAnschluss.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.description</code> attribute. 
	 * @param value the description - AbtriebAnschluss Beschreibung
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.description</code> attribute. 
	 * @param value the description - AbtriebAnschluss Beschreibung
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.description</code> attribute. 
	 * @param value the description - AbtriebAnschluss Beschreibung
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.imageref2abtriebAnschluss</code> attribute.
	 * @return the imageref2abtriebAnschluss
	 */
	public Collection<Bildreferenz> getImageref2abtriebAnschluss(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.imageref2abtriebAnschluss</code> attribute.
	 * @return the imageref2abtriebAnschluss
	 */
	public Collection<Bildreferenz> getImageref2abtriebAnschluss()
	{
		return getImageref2abtriebAnschluss( getSession().getSessionContext() );
	}
	
	public long getImageref2abtriebAnschlussCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null
		);
	}
	
	public long getImageref2abtriebAnschlussCount()
	{
		return getImageref2abtriebAnschlussCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.imageref2abtriebAnschluss</code> attribute. 
	 * @param value the imageref2abtriebAnschluss
	 */
	public void setImageref2abtriebAnschluss(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.imageref2abtriebAnschluss</code> attribute. 
	 * @param value the imageref2abtriebAnschluss
	 */
	public void setImageref2abtriebAnschluss(final Collection<Bildreferenz> value)
	{
		setImageref2abtriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imageref2abtriebAnschluss. 
	 * @param value the item to add to imageref2abtriebAnschluss
	 */
	public void addToImageref2abtriebAnschluss(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imageref2abtriebAnschluss. 
	 * @param value the item to add to imageref2abtriebAnschluss
	 */
	public void addToImageref2abtriebAnschluss(final Bildreferenz value)
	{
		addToImageref2abtriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imageref2abtriebAnschluss. 
	 * @param value the item to remove from imageref2abtriebAnschluss
	 */
	public void removeFromImageref2abtriebAnschluss(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imageref2abtriebAnschluss. 
	 * @param value the item to remove from imageref2abtriebAnschluss
	 */
	public void removeFromImageref2abtriebAnschluss(final Bildreferenz value)
	{
		removeFromImageref2abtriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.name</code> attribute.
	 * @return the name - AbtriebAnschluss Name
	 */
	public String getName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAbtriebAnschluss.getName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.name</code> attribute.
	 * @return the name - AbtriebAnschluss Name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.name</code> attribute. 
	 * @return the localized name - AbtriebAnschluss Name
	 */
	public Map<Language,String> getAllName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.name</code> attribute. 
	 * @return the localized name - AbtriebAnschluss Name
	 */
	public Map<Language,String> getAllName()
	{
		return getAllName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.name</code> attribute. 
	 * @param value the name - AbtriebAnschluss Name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAbtriebAnschluss.setName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.name</code> attribute. 
	 * @param value the name - AbtriebAnschluss Name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.name</code> attribute. 
	 * @param value the name - AbtriebAnschluss Name
	 */
	public void setAllName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.name</code> attribute. 
	 * @param value the name - AbtriebAnschluss Name
	 */
	public void setAllName(final Map<Language,String> value)
	{
		setAllName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.products2abtriebAnschluss</code> attribute.
	 * @return the products2abtriebAnschluss
	 */
	public Collection<WeraProduct> getProducts2abtriebAnschluss(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AbtriebAnschluss.products2abtriebAnschluss</code> attribute.
	 * @return the products2abtriebAnschluss
	 */
	public Collection<WeraProduct> getProducts2abtriebAnschluss()
	{
		return getProducts2abtriebAnschluss( getSession().getSessionContext() );
	}
	
	public long getProducts2abtriebAnschlussCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null
		);
	}
	
	public long getProducts2abtriebAnschlussCount()
	{
		return getProducts2abtriebAnschlussCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.products2abtriebAnschluss</code> attribute. 
	 * @param value the products2abtriebAnschluss
	 */
	public void setProducts2abtriebAnschluss(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AbtriebAnschluss.products2abtriebAnschluss</code> attribute. 
	 * @param value the products2abtriebAnschluss
	 */
	public void setProducts2abtriebAnschluss(final Collection<WeraProduct> value)
	{
		setProducts2abtriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products2abtriebAnschluss. 
	 * @param value the item to add to products2abtriebAnschluss
	 */
	public void addToProducts2abtriebAnschluss(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products2abtriebAnschluss. 
	 * @param value the item to add to products2abtriebAnschluss
	 */
	public void addToProducts2abtriebAnschluss(final WeraProduct value)
	{
		addToProducts2abtriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products2abtriebAnschluss. 
	 * @param value the item to remove from products2abtriebAnschluss
	 */
	public void removeFromProducts2abtriebAnschluss(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products2abtriebAnschluss. 
	 * @param value the item to remove from products2abtriebAnschluss
	 */
	public void removeFromProducts2abtriebAnschluss(final WeraProduct value)
	{
		removeFromProducts2abtriebAnschluss( getSession().getSessionContext(), value );
	}
	
}

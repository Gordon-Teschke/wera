/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.AbtriebAnschluss;
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
 * Generated class for type {@link com.computationaldesign.wera.jalo.AntriebAnschluss AntriebAnschluss}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedAntriebAnschluss extends GenericItem
{
	/** Qualifier of the <code>AntriebAnschluss.description</code> attribute **/
	public static final String DESCRIPTION = "description".intern();
	/** Qualifier of the <code>AntriebAnschluss.code</code> attribute **/
	public static final String CODE = "code".intern();
	/** Qualifier of the <code>AntriebAnschluss.abtriebAnschlussFrom</code> attribute **/
	public static final String ABTRIEBANSCHLUSSFROM = "abtriebAnschlussFrom".intern();
	/** Qualifier of the <code>AntriebAnschluss.name</code> attribute **/
	public static final String NAME = "name".intern();
	/** Qualifier of the <code>AntriebAnschluss.products2antriebAnschluss</code> attribute **/
	public static final String PRODUCTS2ANTRIEBANSCHLUSS = "products2antriebAnschluss".intern();
	/** Qualifier of the <code>AntriebAnschluss.imageref2antriebAnschluss</code> attribute **/
	public static final String IMAGEREF2ANTRIEBANSCHLUSS = "imageref2antriebAnschluss".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.abtriebAnschlussFrom</code> attribute.
	 * @return the abtriebAnschlussFrom
	 */
	public Collection<AbtriebAnschluss> getAbtriebAnschlussFrom(final SessionContext ctx)
	{
		final List<AbtriebAnschluss> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.abtriebAnschlussFrom</code> attribute.
	 * @return the abtriebAnschlussFrom
	 */
	public Collection<AbtriebAnschluss> getAbtriebAnschlussFrom()
	{
		return getAbtriebAnschlussFrom( getSession().getSessionContext() );
	}
	
	public long getAbtriebAnschlussFromCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null
		);
	}
	
	public long getAbtriebAnschlussFromCount()
	{
		return getAbtriebAnschlussFromCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.abtriebAnschlussFrom</code> attribute. 
	 * @param value the abtriebAnschlussFrom
	 */
	public void setAbtriebAnschlussFrom(final SessionContext ctx, final Collection<AbtriebAnschluss> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.abtriebAnschlussFrom</code> attribute. 
	 * @param value the abtriebAnschlussFrom
	 */
	public void setAbtriebAnschlussFrom(final Collection<AbtriebAnschluss> value)
	{
		setAbtriebAnschlussFrom( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebAnschlussFrom. 
	 * @param value the item to add to abtriebAnschlussFrom
	 */
	public void addToAbtriebAnschlussFrom(final SessionContext ctx, final AbtriebAnschluss value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebAnschlussFrom. 
	 * @param value the item to add to abtriebAnschlussFrom
	 */
	public void addToAbtriebAnschlussFrom(final AbtriebAnschluss value)
	{
		addToAbtriebAnschlussFrom( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebAnschlussFrom. 
	 * @param value the item to remove from abtriebAnschlussFrom
	 */
	public void removeFromAbtriebAnschlussFrom(final SessionContext ctx, final AbtriebAnschluss value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSS2ANTRIEBANSCHLUSS,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebAnschlussFrom. 
	 * @param value the item to remove from abtriebAnschlussFrom
	 */
	public void removeFromAbtriebAnschlussFrom(final AbtriebAnschluss value)
	{
		removeFromAbtriebAnschlussFrom( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.description</code> attribute.
	 * @return the description - AntriebAnschluss Beschreibung
	 */
	public String getDescription(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAntriebAnschluss.getDescription requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.description</code> attribute.
	 * @return the description - AntriebAnschluss Beschreibung
	 */
	public String getDescription()
	{
		return getDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.description</code> attribute. 
	 * @return the localized description - AntriebAnschluss Beschreibung
	 */
	public Map<Language,String> getAllDescription(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.description</code> attribute. 
	 * @return the localized description - AntriebAnschluss Beschreibung
	 */
	public Map<Language,String> getAllDescription()
	{
		return getAllDescription( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.description</code> attribute. 
	 * @param value the description - AntriebAnschluss Beschreibung
	 */
	public void setDescription(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAntriebAnschluss.setDescription requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.description</code> attribute. 
	 * @param value the description - AntriebAnschluss Beschreibung
	 */
	public void setDescription(final String value)
	{
		setDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.description</code> attribute. 
	 * @param value the description - AntriebAnschluss Beschreibung
	 */
	public void setAllDescription(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.description</code> attribute. 
	 * @param value the description - AntriebAnschluss Beschreibung
	 */
	public void setAllDescription(final Map<Language,String> value)
	{
		setAllDescription( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.imageref2antriebAnschluss</code> attribute.
	 * @return the imageref2antriebAnschluss
	 */
	public Collection<Bildreferenz> getImageref2antriebAnschluss(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.imageref2antriebAnschluss</code> attribute.
	 * @return the imageref2antriebAnschluss
	 */
	public Collection<Bildreferenz> getImageref2antriebAnschluss()
	{
		return getImageref2antriebAnschluss( getSession().getSessionContext() );
	}
	
	public long getImageref2antriebAnschlussCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null
		);
	}
	
	public long getImageref2antriebAnschlussCount()
	{
		return getImageref2antriebAnschlussCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.imageref2antriebAnschluss</code> attribute. 
	 * @param value the imageref2antriebAnschluss
	 */
	public void setImageref2antriebAnschluss(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.imageref2antriebAnschluss</code> attribute. 
	 * @param value the imageref2antriebAnschluss
	 */
	public void setImageref2antriebAnschluss(final Collection<Bildreferenz> value)
	{
		setImageref2antriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imageref2antriebAnschluss. 
	 * @param value the item to add to imageref2antriebAnschluss
	 */
	public void addToImageref2antriebAnschluss(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imageref2antriebAnschluss. 
	 * @param value the item to add to imageref2antriebAnschluss
	 */
	public void addToImageref2antriebAnschluss(final Bildreferenz value)
	{
		addToImageref2antriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imageref2antriebAnschluss. 
	 * @param value the item to remove from imageref2antriebAnschluss
	 */
	public void removeFromImageref2antriebAnschluss(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imageref2antriebAnschluss. 
	 * @param value the item to remove from imageref2antriebAnschluss
	 */
	public void removeFromImageref2antriebAnschluss(final Bildreferenz value)
	{
		removeFromImageref2antriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.name</code> attribute.
	 * @return the name - AntriebAnschluss Name
	 */
	public String getName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAntriebAnschluss.getName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.name</code> attribute.
	 * @return the name - AntriebAnschluss Name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.name</code> attribute. 
	 * @return the localized name - AntriebAnschluss Name
	 */
	public Map<Language,String> getAllName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.name</code> attribute. 
	 * @return the localized name - AntriebAnschluss Name
	 */
	public Map<Language,String> getAllName()
	{
		return getAllName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.name</code> attribute. 
	 * @param value the name - AntriebAnschluss Name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedAntriebAnschluss.setName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.name</code> attribute. 
	 * @param value the name - AntriebAnschluss Name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.name</code> attribute. 
	 * @param value the name - AntriebAnschluss Name
	 */
	public void setAllName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.name</code> attribute. 
	 * @param value the name - AntriebAnschluss Name
	 */
	public void setAllName(final Map<Language,String> value)
	{
		setAllName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.products2antriebAnschluss</code> attribute.
	 * @return the products2antriebAnschluss
	 */
	public Collection<WeraProduct> getProducts2antriebAnschluss(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>AntriebAnschluss.products2antriebAnschluss</code> attribute.
	 * @return the products2antriebAnschluss
	 */
	public Collection<WeraProduct> getProducts2antriebAnschluss()
	{
		return getProducts2antriebAnschluss( getSession().getSessionContext() );
	}
	
	public long getProducts2antriebAnschlussCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null
		);
	}
	
	public long getProducts2antriebAnschlussCount()
	{
		return getProducts2antriebAnschlussCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.products2antriebAnschluss</code> attribute. 
	 * @param value the products2antriebAnschluss
	 */
	public void setProducts2antriebAnschluss(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>AntriebAnschluss.products2antriebAnschluss</code> attribute. 
	 * @param value the products2antriebAnschluss
	 */
	public void setProducts2antriebAnschluss(final Collection<WeraProduct> value)
	{
		setProducts2antriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products2antriebAnschluss. 
	 * @param value the item to add to products2antriebAnschluss
	 */
	public void addToProducts2antriebAnschluss(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products2antriebAnschluss. 
	 * @param value the item to add to products2antriebAnschluss
	 */
	public void addToProducts2antriebAnschluss(final WeraProduct value)
	{
		addToProducts2antriebAnschluss( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products2antriebAnschluss. 
	 * @param value the item to remove from products2antriebAnschluss
	 */
	public void removeFromProducts2antriebAnschluss(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products2antriebAnschluss. 
	 * @param value the item to remove from products2antriebAnschluss
	 */
	public void removeFromProducts2antriebAnschluss(final WeraProduct value)
	{
		removeFromProducts2antriebAnschluss( getSession().getSessionContext(), value );
	}
	
}

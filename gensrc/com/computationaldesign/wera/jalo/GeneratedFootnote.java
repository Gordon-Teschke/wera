/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraVariante;
import de.hybris.platform.catalog.jalo.ProductFeature;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Footnote Footnote}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedFootnote extends GenericItem
{
	/** Qualifier of the <code>Footnote.variants</code> attribute **/
	public static final String VARIANTS = "variants".intern();
	/** Qualifier of the <code>Footnote.valid_to</code> attribute **/
	public static final String VALID_TO = "valid_to".intern();
	/** Qualifier of the <code>Footnote.weraproducts</code> attribute **/
	public static final String WERAPRODUCTS = "weraproducts".intern();
	/** Qualifier of the <code>Footnote.name</code> attribute **/
	public static final String NAME = "name".intern();
	/** Qualifier of the <code>Footnote.valid_from</code> attribute **/
	public static final String VALID_FROM = "valid_from".intern();
	/** Qualifier of the <code>Footnote.productfeatures</code> attribute **/
	public static final String PRODUCTFEATURES = "productfeatures".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.name</code> attribute.
	 * @return the name
	 */
	public String getName(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFootnote.getName requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.name</code> attribute.
	 * @return the name
	 */
	public String getName()
	{
		return getName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.name</code> attribute. 
	 * @return the localized name
	 */
	public Map<Language,String> getAllName(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.name</code> attribute. 
	 * @return the localized name
	 */
	public Map<Language,String> getAllName()
	{
		return getAllName( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedFootnote.setName requires a session language", 0 );
		}
		setLocalizedProperty(ctx, NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.name</code> attribute. 
	 * @param value the name
	 */
	public void setName(final String value)
	{
		setName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.name</code> attribute. 
	 * @param value the name
	 */
	public void setAllName(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.name</code> attribute. 
	 * @param value the name
	 */
	public void setAllName(final Map<Language,String> value)
	{
		setAllName( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.productfeatures</code> attribute.
	 * @return the productfeatures
	 */
	public ProductFeature getProductfeatures(final SessionContext ctx)
	{
		return (ProductFeature)getProperty( ctx, PRODUCTFEATURES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.productfeatures</code> attribute.
	 * @return the productfeatures
	 */
	public ProductFeature getProductfeatures()
	{
		return getProductfeatures( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.productfeatures</code> attribute. 
	 * @param value the productfeatures
	 */
	public void setProductfeatures(final SessionContext ctx, final ProductFeature value)
	{
		setProperty(ctx, PRODUCTFEATURES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.productfeatures</code> attribute. 
	 * @param value the productfeatures
	 */
	public void setProductfeatures(final ProductFeature value)
	{
		setProductfeatures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, VALID_FROM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from()
	{
		return getValid_from( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, VALID_FROM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final Date value)
	{
		setValid_from( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, VALID_TO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to()
	{
		return getValid_to( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, VALID_TO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final Date value)
	{
		setValid_to( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.variants</code> attribute.
	 * @return the variants
	 */
	public Collection<WeraVariante> getVariants(final SessionContext ctx)
	{
		final List<WeraVariante> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEFOOTNOTERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.variants</code> attribute.
	 * @return the variants
	 */
	public Collection<WeraVariante> getVariants()
	{
		return getVariants( getSession().getSessionContext() );
	}
	
	public long getVariantsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEFOOTNOTERELATION,
			null
		);
	}
	
	public long getVariantsCount()
	{
		return getVariantsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.variants</code> attribute. 
	 * @param value the variants
	 */
	public void setVariants(final SessionContext ctx, final Collection<WeraVariante> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEFOOTNOTERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.variants</code> attribute. 
	 * @param value the variants
	 */
	public void setVariants(final Collection<WeraVariante> value)
	{
		setVariants( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to variants. 
	 * @param value the item to add to variants
	 */
	public void addToVariants(final SessionContext ctx, final WeraVariante value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEFOOTNOTERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to variants. 
	 * @param value the item to add to variants
	 */
	public void addToVariants(final WeraVariante value)
	{
		addToVariants( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from variants. 
	 * @param value the item to remove from variants
	 */
	public void removeFromVariants(final SessionContext ctx, final WeraVariante value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEFOOTNOTERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from variants. 
	 * @param value the item to remove from variants
	 */
	public void removeFromVariants(final WeraVariante value)
	{
		removeFromVariants( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.weraproducts</code> attribute.
	 * @return the weraproducts
	 */
	public Collection<WeraProduct> getWeraproducts(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Footnote.weraproducts</code> attribute.
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
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
			null
		);
	}
	
	public long getWeraproductsCount()
	{
		return getWeraproductsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.weraproducts</code> attribute. 
	 * @param value the weraproducts
	 */
	public void setWeraproducts(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Footnote.weraproducts</code> attribute. 
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
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
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
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
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
	
}

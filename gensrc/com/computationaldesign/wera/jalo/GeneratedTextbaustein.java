/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.Bildreferenz;
import com.computationaldesign.wera.jalo.Tipp;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraVariante;
import de.hybris.platform.category.jalo.Category;
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
 * Generated class for type {@link com.computationaldesign.wera.jalo.Textbaustein Textbaustein}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTextbaustein extends GenericItem
{
	/** Qualifier of the <code>Textbaustein.code</code> attribute **/
	public static final String CODE = "code".intern();
	/** Qualifier of the <code>Textbaustein.typeofproduct_product</code> attribute **/
	public static final String TYPEOFPRODUCT_PRODUCT = "typeofproduct_product".intern();
	/** Qualifier of the <code>Textbaustein.introtext2category</code> attribute **/
	public static final String INTROTEXT2CATEGORY = "introtext2category".intern();
	/** Qualifier of the <code>Textbaustein.marketing2weravariante</code> attribute **/
	public static final String MARKETING2WERAVARIANTE = "marketing2weravariante".intern();
	/** Qualifier of the <code>Textbaustein.bulletpoints2weraproductset</code> attribute **/
	public static final String BULLETPOINTS2WERAPRODUCTSET = "bulletpoints2weraproductset".intern();
	/** Qualifier of the <code>Textbaustein.text</code> attribute **/
	public static final String TEXT = "text".intern();
	/** Qualifier of the <code>Textbaustein.amz_artikeltyp_product</code> attribute **/
	public static final String AMZ_ARTIKELTYP_PRODUCT = "amz_artikeltyp_product".intern();
	/** Qualifier of the <code>Textbaustein.headlinetippref</code> attribute **/
	public static final String HEADLINETIPPREF = "headlinetippref".intern();
	/** Qualifier of the <code>Textbaustein.marketing2weraproduct</code> attribute **/
	public static final String MARKETING2WERAPRODUCT = "marketing2weraproduct".intern();
	/** Qualifier of the <code>Textbaustein.tippref</code> attribute **/
	public static final String TIPPREF = "tippref".intern();
	/** Qualifier of the <code>Textbaustein.bulletpoints2weravariante</code> attribute **/
	public static final String BULLETPOINTS2WERAVARIANTE = "bulletpoints2weravariante".intern();
	/** Qualifier of the <code>Textbaustein.imageref</code> attribute **/
	public static final String IMAGEREF = "imageref".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.amz_artikeltyp_product</code> attribute.
	 * @return the amz_artikeltyp_product
	 */
	public Collection<WeraProduct> getAmz_artikeltyp_product(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.amz_artikeltyp_product</code> attribute.
	 * @return the amz_artikeltyp_product
	 */
	public Collection<WeraProduct> getAmz_artikeltyp_product()
	{
		return getAmz_artikeltyp_product( getSession().getSessionContext() );
	}
	
	public long getAmz_artikeltyp_productCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null
		);
	}
	
	public long getAmz_artikeltyp_productCount()
	{
		return getAmz_artikeltyp_productCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.amz_artikeltyp_product</code> attribute. 
	 * @param value the amz_artikeltyp_product
	 */
	public void setAmz_artikeltyp_product(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.amz_artikeltyp_product</code> attribute. 
	 * @param value the amz_artikeltyp_product
	 */
	public void setAmz_artikeltyp_product(final Collection<WeraProduct> value)
	{
		setAmz_artikeltyp_product( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to amz_artikeltyp_product. 
	 * @param value the item to add to amz_artikeltyp_product
	 */
	public void addToAmz_artikeltyp_product(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to amz_artikeltyp_product. 
	 * @param value the item to add to amz_artikeltyp_product
	 */
	public void addToAmz_artikeltyp_product(final WeraProduct value)
	{
		addToAmz_artikeltyp_product( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from amz_artikeltyp_product. 
	 * @param value the item to remove from amz_artikeltyp_product
	 */
	public void removeFromAmz_artikeltyp_product(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from amz_artikeltyp_product. 
	 * @param value the item to remove from amz_artikeltyp_product
	 */
	public void removeFromAmz_artikeltyp_product(final WeraProduct value)
	{
		removeFromAmz_artikeltyp_product( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.bulletpoints2weraproductset</code> attribute.
	 * @return the bulletpoints2weraproductset
	 */
	public Collection<WeraProduct> getBulletpoints2weraproductset(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.bulletpoints2weraproductset</code> attribute.
	 * @return the bulletpoints2weraproductset
	 */
	public Collection<WeraProduct> getBulletpoints2weraproductset()
	{
		return getBulletpoints2weraproductset( getSession().getSessionContext() );
	}
	
	public long getBulletpoints2weraproductsetCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null
		);
	}
	
	public long getBulletpoints2weraproductsetCount()
	{
		return getBulletpoints2weraproductsetCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.bulletpoints2weraproductset</code> attribute. 
	 * @param value the bulletpoints2weraproductset
	 */
	public void setBulletpoints2weraproductset(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.bulletpoints2weraproductset</code> attribute. 
	 * @param value the bulletpoints2weraproductset
	 */
	public void setBulletpoints2weraproductset(final Collection<WeraProduct> value)
	{
		setBulletpoints2weraproductset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to bulletpoints2weraproductset. 
	 * @param value the item to add to bulletpoints2weraproductset
	 */
	public void addToBulletpoints2weraproductset(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to bulletpoints2weraproductset. 
	 * @param value the item to add to bulletpoints2weraproductset
	 */
	public void addToBulletpoints2weraproductset(final WeraProduct value)
	{
		addToBulletpoints2weraproductset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from bulletpoints2weraproductset. 
	 * @param value the item to remove from bulletpoints2weraproductset
	 */
	public void removeFromBulletpoints2weraproductset(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from bulletpoints2weraproductset. 
	 * @param value the item to remove from bulletpoints2weraproductset
	 */
	public void removeFromBulletpoints2weraproductset(final WeraProduct value)
	{
		removeFromBulletpoints2weraproductset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.bulletpoints2weravariante</code> attribute.
	 * @return the bulletpoints2weravariante
	 */
	public Collection<WeraVariante> getBulletpoints2weravariante(final SessionContext ctx)
	{
		final List<WeraVariante> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.bulletpoints2weravariante</code> attribute.
	 * @return the bulletpoints2weravariante
	 */
	public Collection<WeraVariante> getBulletpoints2weravariante()
	{
		return getBulletpoints2weravariante( getSession().getSessionContext() );
	}
	
	public long getBulletpoints2weravarianteCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINBULLETPOINTSRELATION,
			null
		);
	}
	
	public long getBulletpoints2weravarianteCount()
	{
		return getBulletpoints2weravarianteCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.bulletpoints2weravariante</code> attribute. 
	 * @param value the bulletpoints2weravariante
	 */
	public void setBulletpoints2weravariante(final SessionContext ctx, final Collection<WeraVariante> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.bulletpoints2weravariante</code> attribute. 
	 * @param value the bulletpoints2weravariante
	 */
	public void setBulletpoints2weravariante(final Collection<WeraVariante> value)
	{
		setBulletpoints2weravariante( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to bulletpoints2weravariante. 
	 * @param value the item to add to bulletpoints2weravariante
	 */
	public void addToBulletpoints2weravariante(final SessionContext ctx, final WeraVariante value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to bulletpoints2weravariante. 
	 * @param value the item to add to bulletpoints2weravariante
	 */
	public void addToBulletpoints2weravariante(final WeraVariante value)
	{
		addToBulletpoints2weravariante( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from bulletpoints2weravariante. 
	 * @param value the item to remove from bulletpoints2weravariante
	 */
	public void removeFromBulletpoints2weravariante(final SessionContext ctx, final WeraVariante value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from bulletpoints2weravariante. 
	 * @param value the item to remove from bulletpoints2weravariante
	 */
	public void removeFromBulletpoints2weravariante(final WeraVariante value)
	{
		removeFromBulletpoints2weravariante( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.headlinetippref</code> attribute.
	 * @return the headlinetippref
	 */
	public Collection<Tipp> getHeadlinetippref(final SessionContext ctx)
	{
		final List<Tipp> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.headlinetippref</code> attribute.
	 * @return the headlinetippref
	 */
	public Collection<Tipp> getHeadlinetippref()
	{
		return getHeadlinetippref( getSession().getSessionContext() );
	}
	
	public long getHeadlinetipprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null
		);
	}
	
	public long getHeadlinetipprefCount()
	{
		return getHeadlinetipprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.headlinetippref</code> attribute. 
	 * @param value the headlinetippref
	 */
	public void setHeadlinetippref(final SessionContext ctx, final Collection<Tipp> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.headlinetippref</code> attribute. 
	 * @param value the headlinetippref
	 */
	public void setHeadlinetippref(final Collection<Tipp> value)
	{
		setHeadlinetippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to headlinetippref. 
	 * @param value the item to add to headlinetippref
	 */
	public void addToHeadlinetippref(final SessionContext ctx, final Tipp value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to headlinetippref. 
	 * @param value the item to add to headlinetippref
	 */
	public void addToHeadlinetippref(final Tipp value)
	{
		addToHeadlinetippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from headlinetippref. 
	 * @param value the item to remove from headlinetippref
	 */
	public void removeFromHeadlinetippref(final SessionContext ctx, final Tipp value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from headlinetippref. 
	 * @param value the item to remove from headlinetippref
	 */
	public void removeFromHeadlinetippref(final Tipp value)
	{
		removeFromHeadlinetippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.imageref</code> attribute.
	 * @return the imageref
	 */
	public Collection<Bildreferenz> getImageref(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.imageref</code> attribute.
	 * @return the imageref
	 */
	public Collection<Bildreferenz> getImageref()
	{
		return getImageref( getSession().getSessionContext() );
	}
	
	public long getImagerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null
		);
	}
	
	public long getImagerefCount()
	{
		return getImagerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.imageref</code> attribute. 
	 * @param value the imageref
	 */
	public void setImageref(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.imageref</code> attribute. 
	 * @param value the imageref
	 */
	public void setImageref(final Collection<Bildreferenz> value)
	{
		setImageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imageref. 
	 * @param value the item to add to imageref
	 */
	public void addToImageref(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imageref. 
	 * @param value the item to add to imageref
	 */
	public void addToImageref(final Bildreferenz value)
	{
		addToImageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imageref. 
	 * @param value the item to remove from imageref
	 */
	public void removeFromImageref(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imageref. 
	 * @param value the item to remove from imageref
	 */
	public void removeFromImageref(final Bildreferenz value)
	{
		removeFromImageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.introtext2category</code> attribute.
	 * @return the introtext2category
	 */
	public Collection<Category> getIntrotext2category(final SessionContext ctx)
	{
		final List<Category> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.introtext2category</code> attribute.
	 * @return the introtext2category
	 */
	public Collection<Category> getIntrotext2category()
	{
		return getIntrotext2category( getSession().getSessionContext() );
	}
	
	public long getIntrotext2categoryCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null
		);
	}
	
	public long getIntrotext2categoryCount()
	{
		return getIntrotext2categoryCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.introtext2category</code> attribute. 
	 * @param value the introtext2category
	 */
	public void setIntrotext2category(final SessionContext ctx, final Collection<Category> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.introtext2category</code> attribute. 
	 * @param value the introtext2category
	 */
	public void setIntrotext2category(final Collection<Category> value)
	{
		setIntrotext2category( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to introtext2category. 
	 * @param value the item to add to introtext2category
	 */
	public void addToIntrotext2category(final SessionContext ctx, final Category value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to introtext2category. 
	 * @param value the item to add to introtext2category
	 */
	public void addToIntrotext2category(final Category value)
	{
		addToIntrotext2category( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from introtext2category. 
	 * @param value the item to remove from introtext2category
	 */
	public void removeFromIntrotext2category(final SessionContext ctx, final Category value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from introtext2category. 
	 * @param value the item to remove from introtext2category
	 */
	public void removeFromIntrotext2category(final Category value)
	{
		removeFromIntrotext2category( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.marketing2weraproduct</code> attribute.
	 * @return the marketing2weraproduct
	 */
	public Collection<WeraProduct> getMarketing2weraproduct(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.marketing2weraproduct</code> attribute.
	 * @return the marketing2weraproduct
	 */
	public Collection<WeraProduct> getMarketing2weraproduct()
	{
		return getMarketing2weraproduct( getSession().getSessionContext() );
	}
	
	public long getMarketing2weraproductCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null
		);
	}
	
	public long getMarketing2weraproductCount()
	{
		return getMarketing2weraproductCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.marketing2weraproduct</code> attribute. 
	 * @param value the marketing2weraproduct
	 */
	public void setMarketing2weraproduct(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.marketing2weraproduct</code> attribute. 
	 * @param value the marketing2weraproduct
	 */
	public void setMarketing2weraproduct(final Collection<WeraProduct> value)
	{
		setMarketing2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to marketing2weraproduct. 
	 * @param value the item to add to marketing2weraproduct
	 */
	public void addToMarketing2weraproduct(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to marketing2weraproduct. 
	 * @param value the item to add to marketing2weraproduct
	 */
	public void addToMarketing2weraproduct(final WeraProduct value)
	{
		addToMarketing2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from marketing2weraproduct. 
	 * @param value the item to remove from marketing2weraproduct
	 */
	public void removeFromMarketing2weraproduct(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from marketing2weraproduct. 
	 * @param value the item to remove from marketing2weraproduct
	 */
	public void removeFromMarketing2weraproduct(final WeraProduct value)
	{
		removeFromMarketing2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.marketing2weravariante</code> attribute.
	 * @return the marketing2weravariante
	 */
	public Collection<WeraVariante> getMarketing2weravariante(final SessionContext ctx)
	{
		final List<WeraVariante> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINMARKETINGRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.marketing2weravariante</code> attribute.
	 * @return the marketing2weravariante
	 */
	public Collection<WeraVariante> getMarketing2weravariante()
	{
		return getMarketing2weravariante( getSession().getSessionContext() );
	}
	
	public long getMarketing2weravarianteCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINMARKETINGRELATION,
			null
		);
	}
	
	public long getMarketing2weravarianteCount()
	{
		return getMarketing2weravarianteCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.marketing2weravariante</code> attribute. 
	 * @param value the marketing2weravariante
	 */
	public void setMarketing2weravariante(final SessionContext ctx, final Collection<WeraVariante> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINMARKETINGRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.marketing2weravariante</code> attribute. 
	 * @param value the marketing2weravariante
	 */
	public void setMarketing2weravariante(final Collection<WeraVariante> value)
	{
		setMarketing2weravariante( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to marketing2weravariante. 
	 * @param value the item to add to marketing2weravariante
	 */
	public void addToMarketing2weravariante(final SessionContext ctx, final WeraVariante value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINMARKETINGRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to marketing2weravariante. 
	 * @param value the item to add to marketing2weravariante
	 */
	public void addToMarketing2weravariante(final WeraVariante value)
	{
		addToMarketing2weravariante( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from marketing2weravariante. 
	 * @param value the item to remove from marketing2weravariante
	 */
	public void removeFromMarketing2weravariante(final SessionContext ctx, final WeraVariante value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTETEXTBAUSTEINMARKETINGRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from marketing2weravariante. 
	 * @param value the item to remove from marketing2weravariante
	 */
	public void removeFromMarketing2weravariante(final WeraVariante value)
	{
		removeFromMarketing2weravariante( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.text</code> attribute.
	 * @return the text - Baustein Text
	 */
	public String getText(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTextbaustein.getText requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, TEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.text</code> attribute.
	 * @return the text - Baustein Text
	 */
	public String getText()
	{
		return getText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.text</code> attribute. 
	 * @return the localized text - Baustein Text
	 */
	public Map<Language,String> getAllText(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,TEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.text</code> attribute. 
	 * @return the localized text - Baustein Text
	 */
	public Map<Language,String> getAllText()
	{
		return getAllText( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.text</code> attribute. 
	 * @param value the text - Baustein Text
	 */
	public void setText(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedTextbaustein.setText requires a session language", 0 );
		}
		setLocalizedProperty(ctx, TEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.text</code> attribute. 
	 * @param value the text - Baustein Text
	 */
	public void setText(final String value)
	{
		setText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.text</code> attribute. 
	 * @param value the text - Baustein Text
	 */
	public void setAllText(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,TEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.text</code> attribute. 
	 * @param value the text - Baustein Text
	 */
	public void setAllText(final Map<Language,String> value)
	{
		setAllText( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.tippref</code> attribute.
	 * @return the tippref
	 */
	public Collection<Tipp> getTippref(final SessionContext ctx)
	{
		final List<Tipp> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.tippref</code> attribute.
	 * @return the tippref
	 */
	public Collection<Tipp> getTippref()
	{
		return getTippref( getSession().getSessionContext() );
	}
	
	public long getTipprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null
		);
	}
	
	public long getTipprefCount()
	{
		return getTipprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.tippref</code> attribute. 
	 * @param value the tippref
	 */
	public void setTippref(final SessionContext ctx, final Collection<Tipp> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.tippref</code> attribute. 
	 * @param value the tippref
	 */
	public void setTippref(final Collection<Tipp> value)
	{
		setTippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to tippref. 
	 * @param value the item to add to tippref
	 */
	public void addToTippref(final SessionContext ctx, final Tipp value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to tippref. 
	 * @param value the item to add to tippref
	 */
	public void addToTippref(final Tipp value)
	{
		addToTippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from tippref. 
	 * @param value the item to remove from tippref
	 */
	public void removeFromTippref(final SessionContext ctx, final Tipp value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from tippref. 
	 * @param value the item to remove from tippref
	 */
	public void removeFromTippref(final Tipp value)
	{
		removeFromTippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.typeofproduct_product</code> attribute.
	 * @return the typeofproduct_product
	 */
	public Collection<WeraProduct> getTypeofproduct_product(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Textbaustein.typeofproduct_product</code> attribute.
	 * @return the typeofproduct_product
	 */
	public Collection<WeraProduct> getTypeofproduct_product()
	{
		return getTypeofproduct_product( getSession().getSessionContext() );
	}
	
	public long getTypeofproduct_productCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null
		);
	}
	
	public long getTypeofproduct_productCount()
	{
		return getTypeofproduct_productCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.typeofproduct_product</code> attribute. 
	 * @param value the typeofproduct_product
	 */
	public void setTypeofproduct_product(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Textbaustein.typeofproduct_product</code> attribute. 
	 * @param value the typeofproduct_product
	 */
	public void setTypeofproduct_product(final Collection<WeraProduct> value)
	{
		setTypeofproduct_product( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to typeofproduct_product. 
	 * @param value the item to add to typeofproduct_product
	 */
	public void addToTypeofproduct_product(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to typeofproduct_product. 
	 * @param value the item to add to typeofproduct_product
	 */
	public void addToTypeofproduct_product(final WeraProduct value)
	{
		addToTypeofproduct_product( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from typeofproduct_product. 
	 * @param value the item to remove from typeofproduct_product
	 */
	public void removeFromTypeofproduct_product(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from typeofproduct_product. 
	 * @param value the item to remove from typeofproduct_product
	 */
	public void removeFromTypeofproduct_product(final WeraProduct value)
	{
		removeFromTypeofproduct_product( getSession().getSessionContext(), value );
	}
	
}

/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.Bildreferenz;
import com.computationaldesign.wera.jalo.Textbaustein;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraProductSetinSet;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.SessionContext;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Tipp Tipp}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedTipp extends GenericItem
{
	/** Qualifier of the <code>Tipp.iconref</code> attribute **/
	public static final String ICONREF = "iconref".intern();
	/** Qualifier of the <code>Tipp.imageref</code> attribute **/
	public static final String IMAGEREF = "imageref".intern();
	/** Qualifier of the <code>Tipp.weraproductsetinset2sortimenttipppref</code> attribute **/
	public static final String WERAPRODUCTSETINSET2SORTIMENTTIPPPREF = "weraproductsetinset2sortimenttipppref".intern();
	/** Qualifier of the <code>Tipp.code</code> attribute **/
	public static final String CODE = "code".intern();
	/** Qualifier of the <code>Tipp.valid_from</code> attribute **/
	public static final String VALID_FROM = "valid_from".intern();
	/** Qualifier of the <code>Tipp.featureref</code> attribute **/
	public static final String FEATUREREF = "featureref".intern();
	/** Qualifier of the <code>Tipp.valid_to</code> attribute **/
	public static final String VALID_TO = "valid_to".intern();
	/** Qualifier of the <code>Tipp.weraproduct2sortimenttipppref</code> attribute **/
	public static final String WERAPRODUCT2SORTIMENTTIPPPREF = "weraproduct2sortimenttipppref".intern();
	/** Qualifier of the <code>Tipp.category2tippref</code> attribute **/
	public static final String CATEGORY2TIPPREF = "category2tippref".intern();
	/** Qualifier of the <code>Tipp.textbausteinheadlineref</code> attribute **/
	public static final String TEXTBAUSTEINHEADLINEREF = "textbausteinheadlineref".intern();
	/** Qualifier of the <code>Tipp.textbausteinref</code> attribute **/
	public static final String TEXTBAUSTEINREF = "textbausteinref".intern();
	/** Qualifier of the <code>Tipp.category2haupttippref</code> attribute **/
	public static final String CATEGORY2HAUPTTIPPREF = "category2haupttippref".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.category2haupttippref</code> attribute.
	 * @return the category2haupttippref
	 */
	public Collection<Category> getCategory2haupttippref(final SessionContext ctx)
	{
		final List<Category> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.category2haupttippref</code> attribute.
	 * @return the category2haupttippref
	 */
	public Collection<Category> getCategory2haupttippref()
	{
		return getCategory2haupttippref( getSession().getSessionContext() );
	}
	
	public long getCategory2haupttipprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null
		);
	}
	
	public long getCategory2haupttipprefCount()
	{
		return getCategory2haupttipprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.category2haupttippref</code> attribute. 
	 * @param value the category2haupttippref
	 */
	public void setCategory2haupttippref(final SessionContext ctx, final Collection<Category> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.category2haupttippref</code> attribute. 
	 * @param value the category2haupttippref
	 */
	public void setCategory2haupttippref(final Collection<Category> value)
	{
		setCategory2haupttippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2haupttippref. 
	 * @param value the item to add to category2haupttippref
	 */
	public void addToCategory2haupttippref(final SessionContext ctx, final Category value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2haupttippref. 
	 * @param value the item to add to category2haupttippref
	 */
	public void addToCategory2haupttippref(final Category value)
	{
		addToCategory2haupttippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2haupttippref. 
	 * @param value the item to remove from category2haupttippref
	 */
	public void removeFromCategory2haupttippref(final SessionContext ctx, final Category value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2haupttippref. 
	 * @param value the item to remove from category2haupttippref
	 */
	public void removeFromCategory2haupttippref(final Category value)
	{
		removeFromCategory2haupttippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.category2tippref</code> attribute.
	 * @return the category2tippref
	 */
	public Collection<Category> getCategory2tippref(final SessionContext ctx)
	{
		final List<Category> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.category2tippref</code> attribute.
	 * @return the category2tippref
	 */
	public Collection<Category> getCategory2tippref()
	{
		return getCategory2tippref( getSession().getSessionContext() );
	}
	
	public long getCategory2tipprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
			null
		);
	}
	
	public long getCategory2tipprefCount()
	{
		return getCategory2tipprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.category2tippref</code> attribute. 
	 * @param value the category2tippref
	 */
	public void setCategory2tippref(final SessionContext ctx, final Collection<Category> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.category2tippref</code> attribute. 
	 * @param value the category2tippref
	 */
	public void setCategory2tippref(final Collection<Category> value)
	{
		setCategory2tippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2tippref. 
	 * @param value the item to add to category2tippref
	 */
	public void addToCategory2tippref(final SessionContext ctx, final Category value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2tippref. 
	 * @param value the item to add to category2tippref
	 */
	public void addToCategory2tippref(final Category value)
	{
		addToCategory2tippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2tippref. 
	 * @param value the item to remove from category2tippref
	 */
	public void removeFromCategory2tippref(final SessionContext ctx, final Category value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2tippref. 
	 * @param value the item to remove from category2tippref
	 */
	public void removeFromCategory2tippref(final Category value)
	{
		removeFromCategory2tippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.featureref</code> attribute.
	 * @return the featureref
	 */
	public Collection<ClassAttributeAssignment> getFeatureref(final SessionContext ctx)
	{
		final List<ClassAttributeAssignment> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.featureref</code> attribute.
	 * @return the featureref
	 */
	public Collection<ClassAttributeAssignment> getFeatureref()
	{
		return getFeatureref( getSession().getSessionContext() );
	}
	
	public long getFeaturerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
			null
		);
	}
	
	public long getFeaturerefCount()
	{
		return getFeaturerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.featureref</code> attribute. 
	 * @param value the featureref
	 */
	public void setFeatureref(final SessionContext ctx, final Collection<ClassAttributeAssignment> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.featureref</code> attribute. 
	 * @param value the featureref
	 */
	public void setFeatureref(final Collection<ClassAttributeAssignment> value)
	{
		setFeatureref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to featureref. 
	 * @param value the item to add to featureref
	 */
	public void addToFeatureref(final SessionContext ctx, final ClassAttributeAssignment value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to featureref. 
	 * @param value the item to add to featureref
	 */
	public void addToFeatureref(final ClassAttributeAssignment value)
	{
		addToFeatureref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from featureref. 
	 * @param value the item to remove from featureref
	 */
	public void removeFromFeatureref(final SessionContext ctx, final ClassAttributeAssignment value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from featureref. 
	 * @param value the item to remove from featureref
	 */
	public void removeFromFeatureref(final ClassAttributeAssignment value)
	{
		removeFromFeatureref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.iconref</code> attribute.
	 * @return the iconref
	 */
	public Collection<Bildreferenz> getIconref(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.iconref</code> attribute.
	 * @return the iconref
	 */
	public Collection<Bildreferenz> getIconref()
	{
		return getIconref( getSession().getSessionContext() );
	}
	
	public long getIconrefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null
		);
	}
	
	public long getIconrefCount()
	{
		return getIconrefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.iconref</code> attribute. 
	 * @param value the iconref
	 */
	public void setIconref(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.iconref</code> attribute. 
	 * @param value the iconref
	 */
	public void setIconref(final Collection<Bildreferenz> value)
	{
		setIconref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to iconref. 
	 * @param value the item to add to iconref
	 */
	public void addToIconref(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to iconref. 
	 * @param value the item to add to iconref
	 */
	public void addToIconref(final Bildreferenz value)
	{
		addToIconref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from iconref. 
	 * @param value the item to remove from iconref
	 */
	public void removeFromIconref(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from iconref. 
	 * @param value the item to remove from iconref
	 */
	public void removeFromIconref(final Bildreferenz value)
	{
		removeFromIconref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.imageref</code> attribute.
	 * @return the imageref
	 */
	public Collection<Bildreferenz> getImageref(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.imageref</code> attribute.
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
			true,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
			null
		);
	}
	
	public long getImagerefCount()
	{
		return getImagerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.imageref</code> attribute. 
	 * @param value the imageref
	 */
	public void setImageref(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.imageref</code> attribute. 
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
			true,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
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
			true,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
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
	 * <i>Generated method</i> - Getter of the <code>Tipp.textbausteinheadlineref</code> attribute.
	 * @return the textbausteinheadlineref
	 */
	public Collection<Textbaustein> getTextbausteinheadlineref(final SessionContext ctx)
	{
		final List<Textbaustein> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.textbausteinheadlineref</code> attribute.
	 * @return the textbausteinheadlineref
	 */
	public Collection<Textbaustein> getTextbausteinheadlineref()
	{
		return getTextbausteinheadlineref( getSession().getSessionContext() );
	}
	
	public long getTextbausteinheadlinerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null
		);
	}
	
	public long getTextbausteinheadlinerefCount()
	{
		return getTextbausteinheadlinerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.textbausteinheadlineref</code> attribute. 
	 * @param value the textbausteinheadlineref
	 */
	public void setTextbausteinheadlineref(final SessionContext ctx, final Collection<Textbaustein> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.textbausteinheadlineref</code> attribute. 
	 * @param value the textbausteinheadlineref
	 */
	public void setTextbausteinheadlineref(final Collection<Textbaustein> value)
	{
		setTextbausteinheadlineref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to textbausteinheadlineref. 
	 * @param value the item to add to textbausteinheadlineref
	 */
	public void addToTextbausteinheadlineref(final SessionContext ctx, final Textbaustein value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to textbausteinheadlineref. 
	 * @param value the item to add to textbausteinheadlineref
	 */
	public void addToTextbausteinheadlineref(final Textbaustein value)
	{
		addToTextbausteinheadlineref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from textbausteinheadlineref. 
	 * @param value the item to remove from textbausteinheadlineref
	 */
	public void removeFromTextbausteinheadlineref(final SessionContext ctx, final Textbaustein value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINHEADLINERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from textbausteinheadlineref. 
	 * @param value the item to remove from textbausteinheadlineref
	 */
	public void removeFromTextbausteinheadlineref(final Textbaustein value)
	{
		removeFromTextbausteinheadlineref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.textbausteinref</code> attribute.
	 * @return the textbausteinref
	 */
	public Collection<Textbaustein> getTextbausteinref(final SessionContext ctx)
	{
		final List<Textbaustein> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.textbausteinref</code> attribute.
	 * @return the textbausteinref
	 */
	public Collection<Textbaustein> getTextbausteinref()
	{
		return getTextbausteinref( getSession().getSessionContext() );
	}
	
	public long getTextbausteinrefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null
		);
	}
	
	public long getTextbausteinrefCount()
	{
		return getTextbausteinrefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.textbausteinref</code> attribute. 
	 * @param value the textbausteinref
	 */
	public void setTextbausteinref(final SessionContext ctx, final Collection<Textbaustein> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.textbausteinref</code> attribute. 
	 * @param value the textbausteinref
	 */
	public void setTextbausteinref(final Collection<Textbaustein> value)
	{
		setTextbausteinref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to textbausteinref. 
	 * @param value the item to add to textbausteinref
	 */
	public void addToTextbausteinref(final SessionContext ctx, final Textbaustein value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to textbausteinref. 
	 * @param value the item to add to textbausteinref
	 */
	public void addToTextbausteinref(final Textbaustein value)
	{
		addToTextbausteinref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from textbausteinref. 
	 * @param value the item to remove from textbausteinref
	 */
	public void removeFromTextbausteinref(final SessionContext ctx, final Textbaustein value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPPTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from textbausteinref. 
	 * @param value the item to remove from textbausteinref
	 */
	public void removeFromTextbausteinref(final Textbaustein value)
	{
		removeFromTextbausteinref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, VALID_FROM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from()
	{
		return getValid_from( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, VALID_FROM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final Date value)
	{
		setValid_from( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, VALID_TO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to()
	{
		return getValid_to( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, VALID_TO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final Date value)
	{
		setValid_to( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.weraproduct2sortimenttipppref</code> attribute.
	 * @return the weraproduct2sortimenttipppref
	 */
	public Collection<WeraProduct> getWeraproduct2sortimenttipppref(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.weraproduct2sortimenttipppref</code> attribute.
	 * @return the weraproduct2sortimenttipppref
	 */
	public Collection<WeraProduct> getWeraproduct2sortimenttipppref()
	{
		return getWeraproduct2sortimenttipppref( getSession().getSessionContext() );
	}
	
	public long getWeraproduct2sortimenttippprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null
		);
	}
	
	public long getWeraproduct2sortimenttippprefCount()
	{
		return getWeraproduct2sortimenttippprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.weraproduct2sortimenttipppref</code> attribute. 
	 * @param value the weraproduct2sortimenttipppref
	 */
	public void setWeraproduct2sortimenttipppref(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.weraproduct2sortimenttipppref</code> attribute. 
	 * @param value the weraproduct2sortimenttipppref
	 */
	public void setWeraproduct2sortimenttipppref(final Collection<WeraProduct> value)
	{
		setWeraproduct2sortimenttipppref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproduct2sortimenttipppref. 
	 * @param value the item to add to weraproduct2sortimenttipppref
	 */
	public void addToWeraproduct2sortimenttipppref(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproduct2sortimenttipppref. 
	 * @param value the item to add to weraproduct2sortimenttipppref
	 */
	public void addToWeraproduct2sortimenttipppref(final WeraProduct value)
	{
		addToWeraproduct2sortimenttipppref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproduct2sortimenttipppref. 
	 * @param value the item to remove from weraproduct2sortimenttipppref
	 */
	public void removeFromWeraproduct2sortimenttipppref(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproduct2sortimenttipppref. 
	 * @param value the item to remove from weraproduct2sortimenttipppref
	 */
	public void removeFromWeraproduct2sortimenttipppref(final WeraProduct value)
	{
		removeFromWeraproduct2sortimenttipppref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.weraproductsetinset2sortimenttipppref</code> attribute.
	 * @return the weraproductsetinset2sortimenttipppref
	 */
	public Collection<WeraProductSetinSet> getWeraproductsetinset2sortimenttipppref(final SessionContext ctx)
	{
		final List<WeraProductSetinSet> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Tipp.weraproductsetinset2sortimenttipppref</code> attribute.
	 * @return the weraproductsetinset2sortimenttipppref
	 */
	public Collection<WeraProductSetinSet> getWeraproductsetinset2sortimenttipppref()
	{
		return getWeraproductsetinset2sortimenttipppref( getSession().getSessionContext() );
	}
	
	public long getWeraproductsetinset2sortimenttippprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null
		);
	}
	
	public long getWeraproductsetinset2sortimenttippprefCount()
	{
		return getWeraproductsetinset2sortimenttippprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.weraproductsetinset2sortimenttipppref</code> attribute. 
	 * @param value the weraproductsetinset2sortimenttipppref
	 */
	public void setWeraproductsetinset2sortimenttipppref(final SessionContext ctx, final Collection<WeraProductSetinSet> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Tipp.weraproductsetinset2sortimenttipppref</code> attribute. 
	 * @param value the weraproductsetinset2sortimenttipppref
	 */
	public void setWeraproductsetinset2sortimenttipppref(final Collection<WeraProductSetinSet> value)
	{
		setWeraproductsetinset2sortimenttipppref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetinset2sortimenttipppref. 
	 * @param value the item to add to weraproductsetinset2sortimenttipppref
	 */
	public void addToWeraproductsetinset2sortimenttipppref(final SessionContext ctx, final WeraProductSetinSet value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetinset2sortimenttipppref. 
	 * @param value the item to add to weraproductsetinset2sortimenttipppref
	 */
	public void addToWeraproductsetinset2sortimenttipppref(final WeraProductSetinSet value)
	{
		addToWeraproductsetinset2sortimenttipppref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetinset2sortimenttipppref. 
	 * @param value the item to remove from weraproductsetinset2sortimenttipppref
	 */
	public void removeFromWeraproductsetinset2sortimenttipppref(final SessionContext ctx, final WeraProductSetinSet value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetinset2sortimenttipppref. 
	 * @param value the item to remove from weraproductsetinset2sortimenttipppref
	 */
	public void removeFromWeraproductsetinset2sortimenttipppref(final WeraProductSetinSet value)
	{
		removeFromWeraproductsetinset2sortimenttipppref( getSession().getSessionContext(), value );
	}
	
}

/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.AbtriebAnschluss;
import com.computationaldesign.wera.jalo.AbtriebSchraubprofil;
import com.computationaldesign.wera.jalo.AntriebAnschluss;
import com.computationaldesign.wera.jalo.Textbaustein;
import com.computationaldesign.wera.jalo.Tipp;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraProductSet;
import com.computationaldesign.wera.jalo.WeraProductSetinSet;
import com.computationaldesign.wera.jalo.WeraVariante;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Bildreferenz Bildreferenz}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedBildreferenz extends GenericItem
{
	/** Qualifier of the <code>Bildreferenz.imagetippref</code> attribute **/
	public static final String IMAGETIPPREF = "imagetippref".intern();
	/** Qualifier of the <code>Bildreferenz.icontippref</code> attribute **/
	public static final String ICONTIPPREF = "icontippref".intern();
	/** Qualifier of the <code>Bildreferenz.code</code> attribute **/
	public static final String CODE = "code".intern();
	/** Qualifier of the <code>Bildreferenz.weraproduct2secondaryimagesref</code> attribute **/
	public static final String WERAPRODUCT2SECONDARYIMAGESREF = "weraproduct2secondaryimagesref".intern();
	/** Qualifier of the <code>Bildreferenz.weravariante2amazonbilderref</code> attribute **/
	public static final String WERAVARIANTE2AMAZONBILDERREF = "weravariante2amazonbilderref".intern();
	/** Qualifier of the <code>Bildreferenz.media_type</code> attribute **/
	public static final String MEDIA_TYPE = "media_type".intern();
	/** Qualifier of the <code>Bildreferenz.weraproductset2amazonbilderref</code> attribute **/
	public static final String WERAPRODUCTSET2AMAZONBILDERREF = "weraproductset2amazonbilderref".intern();
	/** Qualifier of the <code>Bildreferenz.abtriebAnschluss2imageref</code> attribute **/
	public static final String ABTRIEBANSCHLUSS2IMAGEREF = "abtriebAnschluss2imageref".intern();
	/** Qualifier of the <code>Bildreferenz.title</code> attribute **/
	public static final String TITLE = "title".intern();
	/** Qualifier of the <code>Bildreferenz.category2anwenderimagedoorwayref</code> attribute **/
	public static final String CATEGORY2ANWENDERIMAGEDOORWAYREF = "category2anwenderimagedoorwayref".intern();
	/** Qualifier of the <code>Bildreferenz.weraproduct2anwenderimageref</code> attribute **/
	public static final String WERAPRODUCT2ANWENDERIMAGEREF = "weraproduct2anwenderimageref".intern();
	/** Qualifier of the <code>Bildreferenz.preview</code> attribute **/
	public static final String PREVIEW = "preview".intern();
	/** Qualifier of the <code>Bildreferenz.abtriebSchraubprofil2imageref</code> attribute **/
	public static final String ABTRIEBSCHRAUBPROFIL2IMAGEREF = "abtriebSchraubprofil2imageref".intern();
	/** Qualifier of the <code>Bildreferenz.category2anwenderimageref</code> attribute **/
	public static final String CATEGORY2ANWENDERIMAGEREF = "category2anwenderimageref".intern();
	/** Qualifier of the <code>Bildreferenz.location</code> attribute **/
	public static final String LOCATION = "location".intern();
	/** Qualifier of the <code>Bildreferenz.antriebAnschluss2imageref</code> attribute **/
	public static final String ANTRIEBANSCHLUSS2IMAGEREF = "antriebAnschluss2imageref".intern();
	/** Qualifier of the <code>Bildreferenz.weraproductsetinset2anwenderimageref</code> attribute **/
	public static final String WERAPRODUCTSETINSET2ANWENDERIMAGEREF = "weraproductsetinset2anwenderimageref".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.abtriebAnschluss2imageref</code> attribute.
	 * @return the abtriebAnschluss2imageref
	 */
	public Collection<AbtriebAnschluss> getAbtriebAnschluss2imageref(final SessionContext ctx)
	{
		final List<AbtriebAnschluss> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.abtriebAnschluss2imageref</code> attribute.
	 * @return the abtriebAnschluss2imageref
	 */
	public Collection<AbtriebAnschluss> getAbtriebAnschluss2imageref()
	{
		return getAbtriebAnschluss2imageref( getSession().getSessionContext() );
	}
	
	public long getAbtriebAnschluss2imagerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null
		);
	}
	
	public long getAbtriebAnschluss2imagerefCount()
	{
		return getAbtriebAnschluss2imagerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.abtriebAnschluss2imageref</code> attribute. 
	 * @param value the abtriebAnschluss2imageref
	 */
	public void setAbtriebAnschluss2imageref(final SessionContext ctx, final Collection<AbtriebAnschluss> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.abtriebAnschluss2imageref</code> attribute. 
	 * @param value the abtriebAnschluss2imageref
	 */
	public void setAbtriebAnschluss2imageref(final Collection<AbtriebAnschluss> value)
	{
		setAbtriebAnschluss2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebAnschluss2imageref. 
	 * @param value the item to add to abtriebAnschluss2imageref
	 */
	public void addToAbtriebAnschluss2imageref(final SessionContext ctx, final AbtriebAnschluss value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebAnschluss2imageref. 
	 * @param value the item to add to abtriebAnschluss2imageref
	 */
	public void addToAbtriebAnschluss2imageref(final AbtriebAnschluss value)
	{
		addToAbtriebAnschluss2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebAnschluss2imageref. 
	 * @param value the item to remove from abtriebAnschluss2imageref
	 */
	public void removeFromAbtriebAnschluss2imageref(final SessionContext ctx, final AbtriebAnschluss value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebAnschluss2imageref. 
	 * @param value the item to remove from abtriebAnschluss2imageref
	 */
	public void removeFromAbtriebAnschluss2imageref(final AbtriebAnschluss value)
	{
		removeFromAbtriebAnschluss2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.abtriebSchraubprofil2imageref</code> attribute.
	 * @return the abtriebSchraubprofil2imageref
	 */
	public Collection<AbtriebSchraubprofil> getAbtriebSchraubprofil2imageref(final SessionContext ctx)
	{
		final List<AbtriebSchraubprofil> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.abtriebSchraubprofil2imageref</code> attribute.
	 * @return the abtriebSchraubprofil2imageref
	 */
	public Collection<AbtriebSchraubprofil> getAbtriebSchraubprofil2imageref()
	{
		return getAbtriebSchraubprofil2imageref( getSession().getSessionContext() );
	}
	
	public long getAbtriebSchraubprofil2imagerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null
		);
	}
	
	public long getAbtriebSchraubprofil2imagerefCount()
	{
		return getAbtriebSchraubprofil2imagerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.abtriebSchraubprofil2imageref</code> attribute. 
	 * @param value the abtriebSchraubprofil2imageref
	 */
	public void setAbtriebSchraubprofil2imageref(final SessionContext ctx, final Collection<AbtriebSchraubprofil> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.abtriebSchraubprofil2imageref</code> attribute. 
	 * @param value the abtriebSchraubprofil2imageref
	 */
	public void setAbtriebSchraubprofil2imageref(final Collection<AbtriebSchraubprofil> value)
	{
		setAbtriebSchraubprofil2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebSchraubprofil2imageref. 
	 * @param value the item to add to abtriebSchraubprofil2imageref
	 */
	public void addToAbtriebSchraubprofil2imageref(final SessionContext ctx, final AbtriebSchraubprofil value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebSchraubprofil2imageref. 
	 * @param value the item to add to abtriebSchraubprofil2imageref
	 */
	public void addToAbtriebSchraubprofil2imageref(final AbtriebSchraubprofil value)
	{
		addToAbtriebSchraubprofil2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebSchraubprofil2imageref. 
	 * @param value the item to remove from abtriebSchraubprofil2imageref
	 */
	public void removeFromAbtriebSchraubprofil2imageref(final SessionContext ctx, final AbtriebSchraubprofil value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ABTRIEBSCHRAUBPROFILBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebSchraubprofil2imageref. 
	 * @param value the item to remove from abtriebSchraubprofil2imageref
	 */
	public void removeFromAbtriebSchraubprofil2imageref(final AbtriebSchraubprofil value)
	{
		removeFromAbtriebSchraubprofil2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.antriebAnschluss2imageref</code> attribute.
	 * @return the antriebAnschluss2imageref
	 */
	public Collection<AntriebAnschluss> getAntriebAnschluss2imageref(final SessionContext ctx)
	{
		final List<AntriebAnschluss> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.antriebAnschluss2imageref</code> attribute.
	 * @return the antriebAnschluss2imageref
	 */
	public Collection<AntriebAnschluss> getAntriebAnschluss2imageref()
	{
		return getAntriebAnschluss2imageref( getSession().getSessionContext() );
	}
	
	public long getAntriebAnschluss2imagerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null
		);
	}
	
	public long getAntriebAnschluss2imagerefCount()
	{
		return getAntriebAnschluss2imagerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.antriebAnschluss2imageref</code> attribute. 
	 * @param value the antriebAnschluss2imageref
	 */
	public void setAntriebAnschluss2imageref(final SessionContext ctx, final Collection<AntriebAnschluss> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.antriebAnschluss2imageref</code> attribute. 
	 * @param value the antriebAnschluss2imageref
	 */
	public void setAntriebAnschluss2imageref(final Collection<AntriebAnschluss> value)
	{
		setAntriebAnschluss2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to antriebAnschluss2imageref. 
	 * @param value the item to add to antriebAnschluss2imageref
	 */
	public void addToAntriebAnschluss2imageref(final SessionContext ctx, final AntriebAnschluss value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to antriebAnschluss2imageref. 
	 * @param value the item to add to antriebAnschluss2imageref
	 */
	public void addToAntriebAnschluss2imageref(final AntriebAnschluss value)
	{
		addToAntriebAnschluss2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from antriebAnschluss2imageref. 
	 * @param value the item to remove from antriebAnschluss2imageref
	 */
	public void removeFromAntriebAnschluss2imageref(final SessionContext ctx, final AntriebAnschluss value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.ANTRIEBANSCHLUSSBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from antriebAnschluss2imageref. 
	 * @param value the item to remove from antriebAnschluss2imageref
	 */
	public void removeFromAntriebAnschluss2imageref(final AntriebAnschluss value)
	{
		removeFromAntriebAnschluss2imageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.category2anwenderimagedoorwayref</code> attribute.
	 * @return the category2anwenderimagedoorwayref
	 */
	public Collection<Category> getCategory2anwenderimagedoorwayref(final SessionContext ctx)
	{
		final List<Category> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.category2anwenderimagedoorwayref</code> attribute.
	 * @return the category2anwenderimagedoorwayref
	 */
	public Collection<Category> getCategory2anwenderimagedoorwayref()
	{
		return getCategory2anwenderimagedoorwayref( getSession().getSessionContext() );
	}
	
	public long getCategory2anwenderimagedoorwayrefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null
		);
	}
	
	public long getCategory2anwenderimagedoorwayrefCount()
	{
		return getCategory2anwenderimagedoorwayrefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.category2anwenderimagedoorwayref</code> attribute. 
	 * @param value the category2anwenderimagedoorwayref
	 */
	public void setCategory2anwenderimagedoorwayref(final SessionContext ctx, final Collection<Category> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.category2anwenderimagedoorwayref</code> attribute. 
	 * @param value the category2anwenderimagedoorwayref
	 */
	public void setCategory2anwenderimagedoorwayref(final Collection<Category> value)
	{
		setCategory2anwenderimagedoorwayref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2anwenderimagedoorwayref. 
	 * @param value the item to add to category2anwenderimagedoorwayref
	 */
	public void addToCategory2anwenderimagedoorwayref(final SessionContext ctx, final Category value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2anwenderimagedoorwayref. 
	 * @param value the item to add to category2anwenderimagedoorwayref
	 */
	public void addToCategory2anwenderimagedoorwayref(final Category value)
	{
		addToCategory2anwenderimagedoorwayref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2anwenderimagedoorwayref. 
	 * @param value the item to remove from category2anwenderimagedoorwayref
	 */
	public void removeFromCategory2anwenderimagedoorwayref(final SessionContext ctx, final Category value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2anwenderimagedoorwayref. 
	 * @param value the item to remove from category2anwenderimagedoorwayref
	 */
	public void removeFromCategory2anwenderimagedoorwayref(final Category value)
	{
		removeFromCategory2anwenderimagedoorwayref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.category2anwenderimageref</code> attribute.
	 * @return the category2anwenderimageref
	 */
	public Collection<Category> getCategory2anwenderimageref(final SessionContext ctx)
	{
		final List<Category> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.category2anwenderimageref</code> attribute.
	 * @return the category2anwenderimageref
	 */
	public Collection<Category> getCategory2anwenderimageref()
	{
		return getCategory2anwenderimageref( getSession().getSessionContext() );
	}
	
	public long getCategory2anwenderimagerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null
		);
	}
	
	public long getCategory2anwenderimagerefCount()
	{
		return getCategory2anwenderimagerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.category2anwenderimageref</code> attribute. 
	 * @param value the category2anwenderimageref
	 */
	public void setCategory2anwenderimageref(final SessionContext ctx, final Collection<Category> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.category2anwenderimageref</code> attribute. 
	 * @param value the category2anwenderimageref
	 */
	public void setCategory2anwenderimageref(final Collection<Category> value)
	{
		setCategory2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2anwenderimageref. 
	 * @param value the item to add to category2anwenderimageref
	 */
	public void addToCategory2anwenderimageref(final SessionContext ctx, final Category value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2anwenderimageref. 
	 * @param value the item to add to category2anwenderimageref
	 */
	public void addToCategory2anwenderimageref(final Category value)
	{
		addToCategory2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2anwenderimageref. 
	 * @param value the item to remove from category2anwenderimageref
	 */
	public void removeFromCategory2anwenderimageref(final SessionContext ctx, final Category value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2anwenderimageref. 
	 * @param value the item to remove from category2anwenderimageref
	 */
	public void removeFromCategory2anwenderimageref(final Category value)
	{
		removeFromCategory2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.code</code> attribute.
	 * @return the code
	 */
	public String getCode(final SessionContext ctx)
	{
		return (String)getProperty( ctx, CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.code</code> attribute.
	 * @return the code
	 */
	public String getCode()
	{
		return getCode( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final SessionContext ctx, final String value)
	{
		setProperty(ctx, CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.code</code> attribute. 
	 * @param value the code
	 */
	public void setCode(final String value)
	{
		setCode( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.icontippref</code> attribute.
	 * @return the icontippref
	 */
	public Collection<Tipp> getIcontippref(final SessionContext ctx)
	{
		final List<Tipp> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.icontippref</code> attribute.
	 * @return the icontippref
	 */
	public Collection<Tipp> getIcontippref()
	{
		return getIcontippref( getSession().getSessionContext() );
	}
	
	public long getIcontipprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null
		);
	}
	
	public long getIcontipprefCount()
	{
		return getIcontipprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.icontippref</code> attribute. 
	 * @param value the icontippref
	 */
	public void setIcontippref(final SessionContext ctx, final Collection<Tipp> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.icontippref</code> attribute. 
	 * @param value the icontippref
	 */
	public void setIcontippref(final Collection<Tipp> value)
	{
		setIcontippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icontippref. 
	 * @param value the item to add to icontippref
	 */
	public void addToIcontippref(final SessionContext ctx, final Tipp value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icontippref. 
	 * @param value the item to add to icontippref
	 */
	public void addToIcontippref(final Tipp value)
	{
		addToIcontippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icontippref. 
	 * @param value the item to remove from icontippref
	 */
	public void removeFromIcontippref(final SessionContext ctx, final Tipp value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPICONREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icontippref. 
	 * @param value the item to remove from icontippref
	 */
	public void removeFromIcontippref(final Tipp value)
	{
		removeFromIcontippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.imagetippref</code> attribute.
	 * @return the imagetippref
	 */
	public Collection<Tipp> getImagetippref(final SessionContext ctx)
	{
		final List<Tipp> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.imagetippref</code> attribute.
	 * @return the imagetippref
	 */
	public Collection<Tipp> getImagetippref()
	{
		return getImagetippref( getSession().getSessionContext() );
	}
	
	public long getImagetipprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
			null
		);
	}
	
	public long getImagetipprefCount()
	{
		return getImagetipprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.imagetippref</code> attribute. 
	 * @param value the imagetippref
	 */
	public void setImagetippref(final SessionContext ctx, final Collection<Tipp> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.imagetippref</code> attribute. 
	 * @param value the imagetippref
	 */
	public void setImagetippref(final Collection<Tipp> value)
	{
		setImagetippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imagetippref. 
	 * @param value the item to add to imagetippref
	 */
	public void addToImagetippref(final SessionContext ctx, final Tipp value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to imagetippref. 
	 * @param value the item to add to imagetippref
	 */
	public void addToImagetippref(final Tipp value)
	{
		addToImagetippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imagetippref. 
	 * @param value the item to remove from imagetippref
	 */
	public void removeFromImagetippref(final SessionContext ctx, final Tipp value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TIPPBILDREFERENZRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from imagetippref. 
	 * @param value the item to remove from imagetippref
	 */
	public void removeFromImagetippref(final Tipp value)
	{
		removeFromImagetippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.location</code> attribute.
	 * @return the location
	 */
	public String getLocation(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LOCATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.location</code> attribute.
	 * @return the location
	 */
	public String getLocation()
	{
		return getLocation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LOCATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.location</code> attribute. 
	 * @param value the location
	 */
	public void setLocation(final String value)
	{
		setLocation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.media_type</code> attribute.
	 * @return the media_type - Typ der Abbildung
	 */
	public EnumerationValue getMedia_type(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, MEDIA_TYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.media_type</code> attribute.
	 * @return the media_type - Typ der Abbildung
	 */
	public EnumerationValue getMedia_type()
	{
		return getMedia_type( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.media_type</code> attribute. 
	 * @param value the media_type - Typ der Abbildung
	 */
	public void setMedia_type(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, MEDIA_TYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.media_type</code> attribute. 
	 * @param value the media_type - Typ der Abbildung
	 */
	public void setMedia_type(final EnumerationValue value)
	{
		setMedia_type( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.preview</code> attribute.
	 * @return the preview
	 */
	public abstract String getPreview(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.preview</code> attribute.
	 * @return the preview
	 */
	public String getPreview()
	{
		return getPreview( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.title</code> attribute.
	 * @return the title
	 */
	public Collection<Textbaustein> getTitle(final SessionContext ctx)
	{
		final List<Textbaustein> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.title</code> attribute.
	 * @return the title
	 */
	public Collection<Textbaustein> getTitle()
	{
		return getTitle( getSession().getSessionContext() );
	}
	
	public long getTitleCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null
		);
	}
	
	public long getTitleCount()
	{
		return getTitleCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final SessionContext ctx, final Collection<Textbaustein> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.title</code> attribute. 
	 * @param value the title
	 */
	public void setTitle(final Collection<Textbaustein> value)
	{
		setTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to title. 
	 * @param value the item to add to title
	 */
	public void addToTitle(final SessionContext ctx, final Textbaustein value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to title. 
	 * @param value the item to add to title
	 */
	public void addToTitle(final Textbaustein value)
	{
		addToTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from title. 
	 * @param value the item to remove from title
	 */
	public void removeFromTitle(final SessionContext ctx, final Textbaustein value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.BILDREFERENZTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from title. 
	 * @param value the item to remove from title
	 */
	public void removeFromTitle(final Textbaustein value)
	{
		removeFromTitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weraproduct2anwenderimageref</code> attribute.
	 * @return the weraproduct2anwenderimageref
	 */
	public Collection<WeraProduct> getWeraproduct2anwenderimageref(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weraproduct2anwenderimageref</code> attribute.
	 * @return the weraproduct2anwenderimageref
	 */
	public Collection<WeraProduct> getWeraproduct2anwenderimageref()
	{
		return getWeraproduct2anwenderimageref( getSession().getSessionContext() );
	}
	
	public long getWeraproduct2anwenderimagerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null
		);
	}
	
	public long getWeraproduct2anwenderimagerefCount()
	{
		return getWeraproduct2anwenderimagerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weraproduct2anwenderimageref</code> attribute. 
	 * @param value the weraproduct2anwenderimageref
	 */
	public void setWeraproduct2anwenderimageref(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weraproduct2anwenderimageref</code> attribute. 
	 * @param value the weraproduct2anwenderimageref
	 */
	public void setWeraproduct2anwenderimageref(final Collection<WeraProduct> value)
	{
		setWeraproduct2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproduct2anwenderimageref. 
	 * @param value the item to add to weraproduct2anwenderimageref
	 */
	public void addToWeraproduct2anwenderimageref(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproduct2anwenderimageref. 
	 * @param value the item to add to weraproduct2anwenderimageref
	 */
	public void addToWeraproduct2anwenderimageref(final WeraProduct value)
	{
		addToWeraproduct2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproduct2anwenderimageref. 
	 * @param value the item to remove from weraproduct2anwenderimageref
	 */
	public void removeFromWeraproduct2anwenderimageref(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproduct2anwenderimageref. 
	 * @param value the item to remove from weraproduct2anwenderimageref
	 */
	public void removeFromWeraproduct2anwenderimageref(final WeraProduct value)
	{
		removeFromWeraproduct2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weraproduct2secondaryimagesref</code> attribute.
	 * @return the weraproduct2secondaryimagesref
	 */
	public Collection<WeraProduct> getWeraproduct2secondaryimagesref(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weraproduct2secondaryimagesref</code> attribute.
	 * @return the weraproduct2secondaryimagesref
	 */
	public Collection<WeraProduct> getWeraproduct2secondaryimagesref()
	{
		return getWeraproduct2secondaryimagesref( getSession().getSessionContext() );
	}
	
	public long getWeraproduct2secondaryimagesrefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null
		);
	}
	
	public long getWeraproduct2secondaryimagesrefCount()
	{
		return getWeraproduct2secondaryimagesrefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weraproduct2secondaryimagesref</code> attribute. 
	 * @param value the weraproduct2secondaryimagesref
	 */
	public void setWeraproduct2secondaryimagesref(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weraproduct2secondaryimagesref</code> attribute. 
	 * @param value the weraproduct2secondaryimagesref
	 */
	public void setWeraproduct2secondaryimagesref(final Collection<WeraProduct> value)
	{
		setWeraproduct2secondaryimagesref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproduct2secondaryimagesref. 
	 * @param value the item to add to weraproduct2secondaryimagesref
	 */
	public void addToWeraproduct2secondaryimagesref(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproduct2secondaryimagesref. 
	 * @param value the item to add to weraproduct2secondaryimagesref
	 */
	public void addToWeraproduct2secondaryimagesref(final WeraProduct value)
	{
		addToWeraproduct2secondaryimagesref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproduct2secondaryimagesref. 
	 * @param value the item to remove from weraproduct2secondaryimagesref
	 */
	public void removeFromWeraproduct2secondaryimagesref(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproduct2secondaryimagesref. 
	 * @param value the item to remove from weraproduct2secondaryimagesref
	 */
	public void removeFromWeraproduct2secondaryimagesref(final WeraProduct value)
	{
		removeFromWeraproduct2secondaryimagesref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weraproductset2amazonbilderref</code> attribute.
	 * @return the weraproductset2amazonbilderref
	 */
	public Collection<WeraProductSet> getWeraproductset2amazonbilderref(final SessionContext ctx)
	{
		final List<WeraProductSet> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weraproductset2amazonbilderref</code> attribute.
	 * @return the weraproductset2amazonbilderref
	 */
	public Collection<WeraProductSet> getWeraproductset2amazonbilderref()
	{
		return getWeraproductset2amazonbilderref( getSession().getSessionContext() );
	}
	
	public long getWeraproductset2amazonbilderrefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null
		);
	}
	
	public long getWeraproductset2amazonbilderrefCount()
	{
		return getWeraproductset2amazonbilderrefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weraproductset2amazonbilderref</code> attribute. 
	 * @param value the weraproductset2amazonbilderref
	 */
	public void setWeraproductset2amazonbilderref(final SessionContext ctx, final Collection<WeraProductSet> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weraproductset2amazonbilderref</code> attribute. 
	 * @param value the weraproductset2amazonbilderref
	 */
	public void setWeraproductset2amazonbilderref(final Collection<WeraProductSet> value)
	{
		setWeraproductset2amazonbilderref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductset2amazonbilderref. 
	 * @param value the item to add to weraproductset2amazonbilderref
	 */
	public void addToWeraproductset2amazonbilderref(final SessionContext ctx, final WeraProductSet value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductset2amazonbilderref. 
	 * @param value the item to add to weraproductset2amazonbilderref
	 */
	public void addToWeraproductset2amazonbilderref(final WeraProductSet value)
	{
		addToWeraproductset2amazonbilderref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductset2amazonbilderref. 
	 * @param value the item to remove from weraproductset2amazonbilderref
	 */
	public void removeFromWeraproductset2amazonbilderref(final SessionContext ctx, final WeraProductSet value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductset2amazonbilderref. 
	 * @param value the item to remove from weraproductset2amazonbilderref
	 */
	public void removeFromWeraproductset2amazonbilderref(final WeraProductSet value)
	{
		removeFromWeraproductset2amazonbilderref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weraproductsetinset2anwenderimageref</code> attribute.
	 * @return the weraproductsetinset2anwenderimageref
	 */
	public Collection<WeraProductSetinSet> getWeraproductsetinset2anwenderimageref(final SessionContext ctx)
	{
		final List<WeraProductSetinSet> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weraproductsetinset2anwenderimageref</code> attribute.
	 * @return the weraproductsetinset2anwenderimageref
	 */
	public Collection<WeraProductSetinSet> getWeraproductsetinset2anwenderimageref()
	{
		return getWeraproductsetinset2anwenderimageref( getSession().getSessionContext() );
	}
	
	public long getWeraproductsetinset2anwenderimagerefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null
		);
	}
	
	public long getWeraproductsetinset2anwenderimagerefCount()
	{
		return getWeraproductsetinset2anwenderimagerefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weraproductsetinset2anwenderimageref</code> attribute. 
	 * @param value the weraproductsetinset2anwenderimageref
	 */
	public void setWeraproductsetinset2anwenderimageref(final SessionContext ctx, final Collection<WeraProductSetinSet> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weraproductsetinset2anwenderimageref</code> attribute. 
	 * @param value the weraproductsetinset2anwenderimageref
	 */
	public void setWeraproductsetinset2anwenderimageref(final Collection<WeraProductSetinSet> value)
	{
		setWeraproductsetinset2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetinset2anwenderimageref. 
	 * @param value the item to add to weraproductsetinset2anwenderimageref
	 */
	public void addToWeraproductsetinset2anwenderimageref(final SessionContext ctx, final WeraProductSetinSet value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetinset2anwenderimageref. 
	 * @param value the item to add to weraproductsetinset2anwenderimageref
	 */
	public void addToWeraproductsetinset2anwenderimageref(final WeraProductSetinSet value)
	{
		addToWeraproductsetinset2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetinset2anwenderimageref. 
	 * @param value the item to remove from weraproductsetinset2anwenderimageref
	 */
	public void removeFromWeraproductsetinset2anwenderimageref(final SessionContext ctx, final WeraProductSetinSet value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetinset2anwenderimageref. 
	 * @param value the item to remove from weraproductsetinset2anwenderimageref
	 */
	public void removeFromWeraproductsetinset2anwenderimageref(final WeraProductSetinSet value)
	{
		removeFromWeraproductsetinset2anwenderimageref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weravariante2amazonbilderref</code> attribute.
	 * @return the weravariante2amazonbilderref
	 */
	public Collection<WeraVariante> getWeravariante2amazonbilderref(final SessionContext ctx)
	{
		final List<WeraVariante> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERVARIANTERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Bildreferenz.weravariante2amazonbilderref</code> attribute.
	 * @return the weravariante2amazonbilderref
	 */
	public Collection<WeraVariante> getWeravariante2amazonbilderref()
	{
		return getWeravariante2amazonbilderref( getSession().getSessionContext() );
	}
	
	public long getWeravariante2amazonbilderrefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERVARIANTERELATION,
			null
		);
	}
	
	public long getWeravariante2amazonbilderrefCount()
	{
		return getWeravariante2amazonbilderrefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weravariante2amazonbilderref</code> attribute. 
	 * @param value the weravariante2amazonbilderref
	 */
	public void setWeravariante2amazonbilderref(final SessionContext ctx, final Collection<WeraVariante> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERVARIANTERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Bildreferenz.weravariante2amazonbilderref</code> attribute. 
	 * @param value the weravariante2amazonbilderref
	 */
	public void setWeravariante2amazonbilderref(final Collection<WeraVariante> value)
	{
		setWeravariante2amazonbilderref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weravariante2amazonbilderref. 
	 * @param value the item to add to weravariante2amazonbilderref
	 */
	public void addToWeravariante2amazonbilderref(final SessionContext ctx, final WeraVariante value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERVARIANTERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weravariante2amazonbilderref. 
	 * @param value the item to add to weravariante2amazonbilderref
	 */
	public void addToWeravariante2amazonbilderref(final WeraVariante value)
	{
		addToWeravariante2amazonbilderref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weravariante2amazonbilderref. 
	 * @param value the item to remove from weravariante2amazonbilderref
	 */
	public void removeFromWeravariante2amazonbilderref(final SessionContext ctx, final WeraVariante value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.AMAZONBILDERVARIANTERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weravariante2amazonbilderref. 
	 * @param value the item to remove from weravariante2amazonbilderref
	 */
	public void removeFromWeravariante2amazonbilderref(final WeraVariante value)
	{
		removeFromWeravariante2amazonbilderref( getSession().getSessionContext(), value );
	}
	
}

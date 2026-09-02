/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.Bildreferenz;
import com.computationaldesign.wera.jalo.ExtImage;
import com.computationaldesign.wera.jalo.Tipp;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraProductSet;
import com.computationaldesign.wera.jalo.WeraProductSetVariants;
import com.computationaldesign.wera.jalo.WeraVarianteVariants;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.WeraProductSetinSet WeraProductSetinSet}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeraProductSetinSet extends WeraProductSet
{
	/** Qualifier of the <code>WeraProductSetinSet.display_is_set</code> attribute **/
	public static final String DISPLAY_IS_SET = "display_is_set".intern();
	/** Qualifier of the <code>WeraProductSetinSet.ist_display</code> attribute **/
	public static final String IST_DISPLAY = "ist_display".intern();
	/** Qualifier of the <code>WeraProductSetinSet.variant_image_amazon</code> attribute **/
	public static final String VARIANT_IMAGE_AMAZON = "variant_image_amazon".intern();
	/** Qualifier of the <code>WeraProductSetinSet.SbClassificatrion</code> attribute **/
	public static final String SBCLASSIFICATRION = "SbClassificatrion".intern();
	/** Qualifier of the <code>WeraProductSetinSet.weraproducts_relation</code> attribute **/
	public static final String WERAPRODUCTS_RELATION = "weraproducts_relation".intern();
	/** Qualifier of the <code>WeraProductSetinSet.preview_variant_image_amazon</code> attribute **/
	public static final String PREVIEW_VARIANT_IMAGE_AMAZON = "preview_variant_image_amazon".intern();
	/** Qualifier of the <code>WeraProductSetinSet.weraproductsetvariants_qual</code> attribute **/
	public static final String WERAPRODUCTSETVARIANTS_QUAL = "weraproductsetvariants_qual".intern();
	/** Qualifier of the <code>WeraProductSetinSet.extimagessetinset</code> attribute **/
	public static final String EXTIMAGESSETINSET = "extimagessetinset".intern();
	/** Qualifier of the <code>WeraProductSetinSet.sortimenttipprefsetinset</code> attribute **/
	public static final String SORTIMENTTIPPREFSETINSET = "sortimenttipprefsetinset".intern();
	/** Qualifier of the <code>WeraProductSetinSet.weravariantevariants_qual</code> attribute **/
	public static final String WERAVARIANTEVARIANTS_QUAL = "weravariantevariants_qual".intern();
	/** Qualifier of the <code>WeraProductSetinSet.anwenderimageref2weraproductsetinset</code> attribute **/
	public static final String ANWENDERIMAGEREF2WERAPRODUCTSETINSET = "anwenderimageref2weraproductsetinset".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.anwenderimageref2weraproductsetinset</code> attribute.
	 * @return the anwenderimageref2weraproductsetinset
	 */
	public Collection<Bildreferenz> getAnwenderimageref2weraproductsetinset(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.anwenderimageref2weraproductsetinset</code> attribute.
	 * @return the anwenderimageref2weraproductsetinset
	 */
	public Collection<Bildreferenz> getAnwenderimageref2weraproductsetinset()
	{
		return getAnwenderimageref2weraproductsetinset( getSession().getSessionContext() );
	}
	
	public long getAnwenderimageref2weraproductsetinsetCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null
		);
	}
	
	public long getAnwenderimageref2weraproductsetinsetCount()
	{
		return getAnwenderimageref2weraproductsetinsetCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.anwenderimageref2weraproductsetinset</code> attribute. 
	 * @param value the anwenderimageref2weraproductsetinset
	 */
	public void setAnwenderimageref2weraproductsetinset(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.anwenderimageref2weraproductsetinset</code> attribute. 
	 * @param value the anwenderimageref2weraproductsetinset
	 */
	public void setAnwenderimageref2weraproductsetinset(final Collection<Bildreferenz> value)
	{
		setAnwenderimageref2weraproductsetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to anwenderimageref2weraproductsetinset. 
	 * @param value the item to add to anwenderimageref2weraproductsetinset
	 */
	public void addToAnwenderimageref2weraproductsetinset(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to anwenderimageref2weraproductsetinset. 
	 * @param value the item to add to anwenderimageref2weraproductsetinset
	 */
	public void addToAnwenderimageref2weraproductsetinset(final Bildreferenz value)
	{
		addToAnwenderimageref2weraproductsetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from anwenderimageref2weraproductsetinset. 
	 * @param value the item to remove from anwenderimageref2weraproductsetinset
	 */
	public void removeFromAnwenderimageref2weraproductsetinset(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from anwenderimageref2weraproductsetinset. 
	 * @param value the item to remove from anwenderimageref2weraproductsetinset
	 */
	public void removeFromAnwenderimageref2weraproductsetinset(final Bildreferenz value)
	{
		removeFromAnwenderimageref2weraproductsetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.display_is_set</code> attribute.
	 * @return the display_is_set - Satzkomponenten fließen in Teilezahl ein?
	 */
	public Boolean isDisplay_is_set(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, DISPLAY_IS_SET);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.display_is_set</code> attribute.
	 * @return the display_is_set - Satzkomponenten fließen in Teilezahl ein?
	 */
	public Boolean isDisplay_is_set()
	{
		return isDisplay_is_set( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.display_is_set</code> attribute. 
	 * @return the display_is_set - Satzkomponenten fließen in Teilezahl ein?
	 */
	public boolean isDisplay_is_setAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isDisplay_is_set( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.display_is_set</code> attribute. 
	 * @return the display_is_set - Satzkomponenten fließen in Teilezahl ein?
	 */
	public boolean isDisplay_is_setAsPrimitive()
	{
		return isDisplay_is_setAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.display_is_set</code> attribute. 
	 * @param value the display_is_set - Satzkomponenten fließen in Teilezahl ein?
	 */
	public void setDisplay_is_set(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, DISPLAY_IS_SET,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.display_is_set</code> attribute. 
	 * @param value the display_is_set - Satzkomponenten fließen in Teilezahl ein?
	 */
	public void setDisplay_is_set(final Boolean value)
	{
		setDisplay_is_set( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.display_is_set</code> attribute. 
	 * @param value the display_is_set - Satzkomponenten fließen in Teilezahl ein?
	 */
	public void setDisplay_is_set(final SessionContext ctx, final boolean value)
	{
		setDisplay_is_set( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.display_is_set</code> attribute. 
	 * @param value the display_is_set - Satzkomponenten fließen in Teilezahl ein?
	 */
	public void setDisplay_is_set(final boolean value)
	{
		setDisplay_is_set( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.extimagessetinset</code> attribute.
	 * @return the extimagessetinset
	 */
	public Collection<ExtImage> getExtimagessetinset(final SessionContext ctx)
	{
		final List<ExtImage> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.extimagessetinset</code> attribute.
	 * @return the extimagessetinset
	 */
	public Collection<ExtImage> getExtimagessetinset()
	{
		return getExtimagessetinset( getSession().getSessionContext() );
	}
	
	public long getExtimagessetinsetCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null
		);
	}
	
	public long getExtimagessetinsetCount()
	{
		return getExtimagessetinsetCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.extimagessetinset</code> attribute. 
	 * @param value the extimagessetinset
	 */
	public void setExtimagessetinset(final SessionContext ctx, final Collection<ExtImage> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.extimagessetinset</code> attribute. 
	 * @param value the extimagessetinset
	 */
	public void setExtimagessetinset(final Collection<ExtImage> value)
	{
		setExtimagessetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to extimagessetinset. 
	 * @param value the item to add to extimagessetinset
	 */
	public void addToExtimagessetinset(final SessionContext ctx, final ExtImage value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to extimagessetinset. 
	 * @param value the item to add to extimagessetinset
	 */
	public void addToExtimagessetinset(final ExtImage value)
	{
		addToExtimagessetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from extimagessetinset. 
	 * @param value the item to remove from extimagessetinset
	 */
	public void removeFromExtimagessetinset(final SessionContext ctx, final ExtImage value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETEXTIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from extimagessetinset. 
	 * @param value the item to remove from extimagessetinset
	 */
	public void removeFromExtimagessetinset(final ExtImage value)
	{
		removeFromExtimagessetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.ist_display</code> attribute.
	 * @return the ist_display - SIS ist ein Display
	 */
	public Boolean isIst_display(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, IST_DISPLAY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.ist_display</code> attribute.
	 * @return the ist_display - SIS ist ein Display
	 */
	public Boolean isIst_display()
	{
		return isIst_display( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.ist_display</code> attribute. 
	 * @return the ist_display - SIS ist ein Display
	 */
	public boolean isIst_displayAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIst_display( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.ist_display</code> attribute. 
	 * @return the ist_display - SIS ist ein Display
	 */
	public boolean isIst_displayAsPrimitive()
	{
		return isIst_displayAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.ist_display</code> attribute. 
	 * @param value the ist_display - SIS ist ein Display
	 */
	public void setIst_display(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, IST_DISPLAY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.ist_display</code> attribute. 
	 * @param value the ist_display - SIS ist ein Display
	 */
	public void setIst_display(final Boolean value)
	{
		setIst_display( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.ist_display</code> attribute. 
	 * @param value the ist_display - SIS ist ein Display
	 */
	public void setIst_display(final SessionContext ctx, final boolean value)
	{
		setIst_display( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.ist_display</code> attribute. 
	 * @param value the ist_display - SIS ist ein Display
	 */
	public void setIst_display(final boolean value)
	{
		setIst_display( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.preview_variant_image_amazon</code> attribute.
	 * @return the preview_variant_image_amazon - Preview Alternatives Produktbild (für Amazon-SB-Produkte ohne SB-Verpackung)
	 */
	public abstract String getPreview_variant_image_amazon(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.preview_variant_image_amazon</code> attribute.
	 * @return the preview_variant_image_amazon - Preview Alternatives Produktbild (für Amazon-SB-Produkte ohne SB-Verpackung)
	 */
	public String getPreview_variant_image_amazon()
	{
		return getPreview_variant_image_amazon( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.SbClassificatrion</code> attribute.
	 * @return the SbClassificatrion - Classic, Premium, Premium Plus
	 */
	public EnumerationValue getSbClassificatrion(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, SBCLASSIFICATRION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.SbClassificatrion</code> attribute.
	 * @return the SbClassificatrion - Classic, Premium, Premium Plus
	 */
	public EnumerationValue getSbClassificatrion()
	{
		return getSbClassificatrion( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.SbClassificatrion</code> attribute. 
	 * @param value the SbClassificatrion - Classic, Premium, Premium Plus
	 */
	public void setSbClassificatrion(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, SBCLASSIFICATRION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.SbClassificatrion</code> attribute. 
	 * @param value the SbClassificatrion - Classic, Premium, Premium Plus
	 */
	public void setSbClassificatrion(final EnumerationValue value)
	{
		setSbClassificatrion( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.sortimenttipprefsetinset</code> attribute.
	 * @return the sortimenttipprefsetinset
	 */
	public Collection<Tipp> getSortimenttipprefsetinset(final SessionContext ctx)
	{
		final List<Tipp> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.sortimenttipprefsetinset</code> attribute.
	 * @return the sortimenttipprefsetinset
	 */
	public Collection<Tipp> getSortimenttipprefsetinset()
	{
		return getSortimenttipprefsetinset( getSession().getSessionContext() );
	}
	
	public long getSortimenttipprefsetinsetCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null
		);
	}
	
	public long getSortimenttipprefsetinsetCount()
	{
		return getSortimenttipprefsetinsetCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.sortimenttipprefsetinset</code> attribute. 
	 * @param value the sortimenttipprefsetinset
	 */
	public void setSortimenttipprefsetinset(final SessionContext ctx, final Collection<Tipp> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.sortimenttipprefsetinset</code> attribute. 
	 * @param value the sortimenttipprefsetinset
	 */
	public void setSortimenttipprefsetinset(final Collection<Tipp> value)
	{
		setSortimenttipprefsetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to sortimenttipprefsetinset. 
	 * @param value the item to add to sortimenttipprefsetinset
	 */
	public void addToSortimenttipprefsetinset(final SessionContext ctx, final Tipp value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to sortimenttipprefsetinset. 
	 * @param value the item to add to sortimenttipprefsetinset
	 */
	public void addToSortimenttipprefsetinset(final Tipp value)
	{
		addToSortimenttipprefsetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from sortimenttipprefsetinset. 
	 * @param value the item to remove from sortimenttipprefsetinset
	 */
	public void removeFromSortimenttipprefsetinset(final SessionContext ctx, final Tipp value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETINSETSORTIMENTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from sortimenttipprefsetinset. 
	 * @param value the item to remove from sortimenttipprefsetinset
	 */
	public void removeFromSortimenttipprefsetinset(final Tipp value)
	{
		removeFromSortimenttipprefsetinset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.variant_image_amazon</code> attribute.
	 * @return the variant_image_amazon - Alternatives Produktbild (für Amazon-SB-Produkte ohne SB-Verpackung)
	 */
	public String getVariant_image_amazon(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VARIANT_IMAGE_AMAZON);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.variant_image_amazon</code> attribute.
	 * @return the variant_image_amazon - Alternatives Produktbild (für Amazon-SB-Produkte ohne SB-Verpackung)
	 */
	public String getVariant_image_amazon()
	{
		return getVariant_image_amazon( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.variant_image_amazon</code> attribute. 
	 * @param value the variant_image_amazon - Alternatives Produktbild (für Amazon-SB-Produkte ohne SB-Verpackung)
	 */
	public void setVariant_image_amazon(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VARIANT_IMAGE_AMAZON,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.variant_image_amazon</code> attribute. 
	 * @param value the variant_image_amazon - Alternatives Produktbild (für Amazon-SB-Produkte ohne SB-Verpackung)
	 */
	public void setVariant_image_amazon(final String value)
	{
		setVariant_image_amazon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.weraproducts_relation</code> attribute.
	 * @return the weraproducts_relation
	 */
	public WeraProduct getWeraproducts_relation(final SessionContext ctx)
	{
		return (WeraProduct)getProperty( ctx, WERAPRODUCTS_RELATION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.weraproducts_relation</code> attribute.
	 * @return the weraproducts_relation
	 */
	public WeraProduct getWeraproducts_relation()
	{
		return getWeraproducts_relation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.weraproducts_relation</code> attribute. 
	 * @param value the weraproducts_relation
	 */
	public void setWeraproducts_relation(final SessionContext ctx, final WeraProduct value)
	{
		setProperty(ctx, WERAPRODUCTS_RELATION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.weraproducts_relation</code> attribute. 
	 * @param value the weraproducts_relation
	 */
	public void setWeraproducts_relation(final WeraProduct value)
	{
		setWeraproducts_relation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.weraproductsetvariants_qual</code> attribute.
	 * @return the weraproductsetvariants_qual
	 */
	public Collection<WeraProductSetVariants> getWeraproductsetvariants_qual(final SessionContext ctx)
	{
		final List<WeraProductSetVariants> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.weraproductsetvariants_qual</code> attribute.
	 * @return the weraproductsetvariants_qual
	 */
	public Collection<WeraProductSetVariants> getWeraproductsetvariants_qual()
	{
		return getWeraproductsetvariants_qual( getSession().getSessionContext() );
	}
	
	public long getWeraproductsetvariants_qualCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null
		);
	}
	
	public long getWeraproductsetvariants_qualCount()
	{
		return getWeraproductsetvariants_qualCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.weraproductsetvariants_qual</code> attribute. 
	 * @param value the weraproductsetvariants_qual
	 */
	public void setWeraproductsetvariants_qual(final SessionContext ctx, final Collection<WeraProductSetVariants> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.weraproductsetvariants_qual</code> attribute. 
	 * @param value the weraproductsetvariants_qual
	 */
	public void setWeraproductsetvariants_qual(final Collection<WeraProductSetVariants> value)
	{
		setWeraproductsetvariants_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetvariants_qual. 
	 * @param value the item to add to weraproductsetvariants_qual
	 */
	public void addToWeraproductsetvariants_qual(final SessionContext ctx, final WeraProductSetVariants value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetvariants_qual. 
	 * @param value the item to add to weraproductsetvariants_qual
	 */
	public void addToWeraproductsetvariants_qual(final WeraProductSetVariants value)
	{
		addToWeraproductsetvariants_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetvariants_qual. 
	 * @param value the item to remove from weraproductsetvariants_qual
	 */
	public void removeFromWeraproductsetvariants_qual(final SessionContext ctx, final WeraProductSetVariants value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2PRODUCTSETINSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetvariants_qual. 
	 * @param value the item to remove from weraproductsetvariants_qual
	 */
	public void removeFromWeraproductsetvariants_qual(final WeraProductSetVariants value)
	{
		removeFromWeraproductsetvariants_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.weravariantevariants_qual</code> attribute.
	 * @return the weravariantevariants_qual
	 */
	public Collection<WeraVarianteVariants> getWeravariantevariants_qual(final SessionContext ctx)
	{
		final List<WeraVarianteVariants> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSetinSet.weravariantevariants_qual</code> attribute.
	 * @return the weravariantevariants_qual
	 */
	public Collection<WeraVarianteVariants> getWeravariantevariants_qual()
	{
		return getWeravariantevariants_qual( getSession().getSessionContext() );
	}
	
	public long getWeravariantevariants_qualCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null
		);
	}
	
	public long getWeravariantevariants_qualCount()
	{
		return getWeravariantevariants_qualCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.weravariantevariants_qual</code> attribute. 
	 * @param value the weravariantevariants_qual
	 */
	public void setWeravariantevariants_qual(final SessionContext ctx, final Collection<WeraVarianteVariants> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSetinSet.weravariantevariants_qual</code> attribute. 
	 * @param value the weravariantevariants_qual
	 */
	public void setWeravariantevariants_qual(final Collection<WeraVarianteVariants> value)
	{
		setWeravariantevariants_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weravariantevariants_qual. 
	 * @param value the item to add to weravariantevariants_qual
	 */
	public void addToWeravariantevariants_qual(final SessionContext ctx, final WeraVarianteVariants value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weravariantevariants_qual. 
	 * @param value the item to add to weravariantevariants_qual
	 */
	public void addToWeravariantevariants_qual(final WeraVarianteVariants value)
	{
		addToWeravariantevariants_qual( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weravariantevariants_qual. 
	 * @param value the item to remove from weravariantevariants_qual
	 */
	public void removeFromWeravariantevariants_qual(final SessionContext ctx, final WeraVarianteVariants value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTSET2VARIANTESETINSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weravariantevariants_qual. 
	 * @param value the item to remove from weravariantevariants_qual
	 */
	public void removeFromWeravariantevariants_qual(final WeraVarianteVariants value)
	{
		removeFromWeravariantevariants_qual( getSession().getSessionContext(), value );
	}
	
}

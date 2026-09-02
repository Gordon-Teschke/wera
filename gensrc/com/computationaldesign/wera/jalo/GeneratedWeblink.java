/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.WeblinkText;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraVariante;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.Weblink Weblink}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeblink extends GenericItem
{
	/** Qualifier of the <code>Weblink.categories</code> attribute **/
	public static final String CATEGORIES = "categories".intern();
	/** Qualifier of the <code>Weblink.movie_template_id</code> attribute **/
	public static final String MOVIE_TEMPLATE_ID = "movie_template_id".intern();
	/** Qualifier of the <code>Weblink.weblinktext2weblink</code> attribute **/
	public static final String WEBLINKTEXT2WEBLINK = "weblinktext2weblink".intern();
	/** Qualifier of the <code>Weblink.ar_tile_image_name</code> attribute **/
	public static final String AR_TILE_IMAGE_NAME = "ar_tile_image_name".intern();
	/** Qualifier of the <code>Weblink.movie_start_date</code> attribute **/
	public static final String MOVIE_START_DATE = "movie_start_date".intern();
	/** Qualifier of the <code>Weblink.target_self</code> attribute **/
	public static final String TARGET_SELF = "target_self".intern();
	/** Qualifier of the <code>Weblink.movie_subtitle</code> attribute **/
	public static final String MOVIE_SUBTITLE = "movie_subtitle".intern();
	/** Qualifier of the <code>Weblink.dam_media_name</code> attribute **/
	public static final String DAM_MEDIA_NAME = "dam_media_name".intern();
	/** Qualifier of the <code>Weblink.movie_tile_image_name</code> attribute **/
	public static final String MOVIE_TILE_IMAGE_NAME = "movie_tile_image_name".intern();
	/** Qualifier of the <code>Weblink.link_wera_de</code> attribute **/
	public static final String LINK_WERA_DE = "link_wera_de".intern();
	/** Qualifier of the <code>Weblink.attributes</code> attribute **/
	public static final String ATTRIBUTES = "attributes".intern();
	/** Qualifier of the <code>Weblink.weravariants</code> attribute **/
	public static final String WERAVARIANTS = "weravariants".intern();
	/** Qualifier of the <code>Weblink.movie_title</code> attribute **/
	public static final String MOVIE_TITLE = "movie_title".intern();
	/** Qualifier of the <code>Weblink.ar_start_date</code> attribute **/
	public static final String AR_START_DATE = "ar_start_date".intern();
	/** Qualifier of the <code>Weblink.movie_type</code> attribute **/
	public static final String MOVIE_TYPE = "movie_type".intern();
	/** Qualifier of the <code>Weblink.youtube_id</code> attribute **/
	public static final String YOUTUBE_ID = "youtube_id".intern();
	/** Qualifier of the <code>Weblink.ar_preview</code> attribute **/
	public static final String AR_PREVIEW = "ar_preview".intern();
	/** Qualifier of the <code>Weblink.ar_tile_image2_name</code> attribute **/
	public static final String AR_TILE_IMAGE2_NAME = "ar_tile_image2_name".intern();
	/** Qualifier of the <code>Weblink.ar_link</code> attribute **/
	public static final String AR_LINK = "ar_link".intern();
	/** Qualifier of the <code>Weblink.movie_detail_image_name</code> attribute **/
	public static final String MOVIE_DETAIL_IMAGE_NAME = "movie_detail_image_name".intern();
	/** Qualifier of the <code>Weblink.preview_link_wera_de</code> attribute **/
	public static final String PREVIEW_LINK_WERA_DE = "preview_link_wera_de".intern();
	/** Qualifier of the <code>Weblink.weraproducts</code> attribute **/
	public static final String WERAPRODUCTS = "weraproducts".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_link</code> attribute.
	 * @return the ar_link - AR Link
	 */
	public String getAr_link(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AR_LINK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_link</code> attribute.
	 * @return the ar_link - AR Link
	 */
	public String getAr_link()
	{
		return getAr_link( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_link</code> attribute. 
	 * @param value the ar_link - AR Link
	 */
	public void setAr_link(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AR_LINK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_link</code> attribute. 
	 * @param value the ar_link - AR Link
	 */
	public void setAr_link(final String value)
	{
		setAr_link( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_preview</code> attribute.
	 * @return the ar_preview - AR: Als Preview verwenden
	 */
	public Boolean isAr_preview(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, AR_PREVIEW);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_preview</code> attribute.
	 * @return the ar_preview - AR: Als Preview verwenden
	 */
	public Boolean isAr_preview()
	{
		return isAr_preview( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_preview</code> attribute. 
	 * @return the ar_preview - AR: Als Preview verwenden
	 */
	public boolean isAr_previewAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isAr_preview( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_preview</code> attribute. 
	 * @return the ar_preview - AR: Als Preview verwenden
	 */
	public boolean isAr_previewAsPrimitive()
	{
		return isAr_previewAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_preview</code> attribute. 
	 * @param value the ar_preview - AR: Als Preview verwenden
	 */
	public void setAr_preview(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, AR_PREVIEW,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_preview</code> attribute. 
	 * @param value the ar_preview - AR: Als Preview verwenden
	 */
	public void setAr_preview(final Boolean value)
	{
		setAr_preview( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_preview</code> attribute. 
	 * @param value the ar_preview - AR: Als Preview verwenden
	 */
	public void setAr_preview(final SessionContext ctx, final boolean value)
	{
		setAr_preview( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_preview</code> attribute. 
	 * @param value the ar_preview - AR: Als Preview verwenden
	 */
	public void setAr_preview(final boolean value)
	{
		setAr_preview( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_start_date</code> attribute.
	 * @return the ar_start_date - AR Start Datum
	 */
	public Date getAr_start_date(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, AR_START_DATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_start_date</code> attribute.
	 * @return the ar_start_date - AR Start Datum
	 */
	public Date getAr_start_date()
	{
		return getAr_start_date( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_start_date</code> attribute. 
	 * @param value the ar_start_date - AR Start Datum
	 */
	public void setAr_start_date(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, AR_START_DATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_start_date</code> attribute. 
	 * @param value the ar_start_date - AR Start Datum
	 */
	public void setAr_start_date(final Date value)
	{
		setAr_start_date( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_tile_image2_name</code> attribute.
	 * @return the ar_tile_image2_name - AR Kachelbild Overlay
	 */
	public String getAr_tile_image2_name(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AR_TILE_IMAGE2_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_tile_image2_name</code> attribute.
	 * @return the ar_tile_image2_name - AR Kachelbild Overlay
	 */
	public String getAr_tile_image2_name()
	{
		return getAr_tile_image2_name( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_tile_image2_name</code> attribute. 
	 * @param value the ar_tile_image2_name - AR Kachelbild Overlay
	 */
	public void setAr_tile_image2_name(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AR_TILE_IMAGE2_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_tile_image2_name</code> attribute. 
	 * @param value the ar_tile_image2_name - AR Kachelbild Overlay
	 */
	public void setAr_tile_image2_name(final String value)
	{
		setAr_tile_image2_name( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_tile_image_name</code> attribute.
	 * @return the ar_tile_image_name - AR Kachelbild Name
	 */
	public String getAr_tile_image_name(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AR_TILE_IMAGE_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.ar_tile_image_name</code> attribute.
	 * @return the ar_tile_image_name - AR Kachelbild Name
	 */
	public String getAr_tile_image_name()
	{
		return getAr_tile_image_name( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_tile_image_name</code> attribute. 
	 * @param value the ar_tile_image_name - AR Kachelbild Name
	 */
	public void setAr_tile_image_name(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AR_TILE_IMAGE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.ar_tile_image_name</code> attribute. 
	 * @param value the ar_tile_image_name - AR Kachelbild Name
	 */
	public void setAr_tile_image_name(final String value)
	{
		setAr_tile_image_name( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.attributes</code> attribute.
	 * @return the attributes
	 */
	public Collection<ClassificationAttribute> getAttributes(final SessionContext ctx)
	{
		final List<ClassificationAttribute> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.attributes</code> attribute.
	 * @return the attributes
	 */
	public Collection<ClassificationAttribute> getAttributes()
	{
		return getAttributes( getSession().getSessionContext() );
	}
	
	public long getAttributesCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null
		);
	}
	
	public long getAttributesCount()
	{
		return getAttributesCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.attributes</code> attribute. 
	 * @param value the attributes
	 */
	public void setAttributes(final SessionContext ctx, final Collection<ClassificationAttribute> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.attributes</code> attribute. 
	 * @param value the attributes
	 */
	public void setAttributes(final Collection<ClassificationAttribute> value)
	{
		setAttributes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to attributes. 
	 * @param value the item to add to attributes
	 */
	public void addToAttributes(final SessionContext ctx, final ClassificationAttribute value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to attributes. 
	 * @param value the item to add to attributes
	 */
	public void addToAttributes(final ClassificationAttribute value)
	{
		addToAttributes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from attributes. 
	 * @param value the item to remove from attributes
	 */
	public void removeFromAttributes(final SessionContext ctx, final ClassificationAttribute value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from attributes. 
	 * @param value the item to remove from attributes
	 */
	public void removeFromAttributes(final ClassificationAttribute value)
	{
		removeFromAttributes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.categories</code> attribute.
	 * @return the categories
	 */
	public Collection<Category> getCategories(final SessionContext ctx)
	{
		final List<Category> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.categories</code> attribute.
	 * @return the categories
	 */
	public Collection<Category> getCategories()
	{
		return getCategories( getSession().getSessionContext() );
	}
	
	public long getCategoriesCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null
		);
	}
	
	public long getCategoriesCount()
	{
		return getCategoriesCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.categories</code> attribute. 
	 * @param value the categories
	 */
	public void setCategories(final SessionContext ctx, final Collection<Category> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.categories</code> attribute. 
	 * @param value the categories
	 */
	public void setCategories(final Collection<Category> value)
	{
		setCategories( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to categories. 
	 * @param value the item to add to categories
	 */
	public void addToCategories(final SessionContext ctx, final Category value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to categories. 
	 * @param value the item to add to categories
	 */
	public void addToCategories(final Category value)
	{
		addToCategories( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from categories. 
	 * @param value the item to remove from categories
	 */
	public void removeFromCategories(final SessionContext ctx, final Category value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from categories. 
	 * @param value the item to remove from categories
	 */
	public void removeFromCategories(final Category value)
	{
		removeFromCategories( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.dam_media_name</code> attribute.
	 * @return the dam_media_name - DAM Medienname
	 */
	public String getDam_media_name(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.getDam_media_name requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DAM_MEDIA_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.dam_media_name</code> attribute.
	 * @return the dam_media_name - DAM Medienname
	 */
	public String getDam_media_name()
	{
		return getDam_media_name( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.dam_media_name</code> attribute. 
	 * @return the localized dam_media_name - DAM Medienname
	 */
	public Map<Language,String> getAllDam_media_name(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DAM_MEDIA_NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.dam_media_name</code> attribute. 
	 * @return the localized dam_media_name - DAM Medienname
	 */
	public Map<Language,String> getAllDam_media_name()
	{
		return getAllDam_media_name( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.dam_media_name</code> attribute. 
	 * @param value the dam_media_name - DAM Medienname
	 */
	public void setDam_media_name(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.setDam_media_name requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DAM_MEDIA_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.dam_media_name</code> attribute. 
	 * @param value the dam_media_name - DAM Medienname
	 */
	public void setDam_media_name(final String value)
	{
		setDam_media_name( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.dam_media_name</code> attribute. 
	 * @param value the dam_media_name - DAM Medienname
	 */
	public void setAllDam_media_name(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DAM_MEDIA_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.dam_media_name</code> attribute. 
	 * @param value the dam_media_name - DAM Medienname
	 */
	public void setAllDam_media_name(final Map<Language,String> value)
	{
		setAllDam_media_name( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.link_wera_de</code> attribute.
	 * @return the link_wera_de - Links www.wera.de
	 */
	public String getLink_wera_de(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.getLink_wera_de requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, LINK_WERA_DE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.link_wera_de</code> attribute.
	 * @return the link_wera_de - Links www.wera.de
	 */
	public String getLink_wera_de()
	{
		return getLink_wera_de( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.link_wera_de</code> attribute. 
	 * @return the localized link_wera_de - Links www.wera.de
	 */
	public Map<Language,String> getAllLink_wera_de(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,LINK_WERA_DE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.link_wera_de</code> attribute. 
	 * @return the localized link_wera_de - Links www.wera.de
	 */
	public Map<Language,String> getAllLink_wera_de()
	{
		return getAllLink_wera_de( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.link_wera_de</code> attribute. 
	 * @param value the link_wera_de - Links www.wera.de
	 */
	public void setLink_wera_de(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.setLink_wera_de requires a session language", 0 );
		}
		setLocalizedProperty(ctx, LINK_WERA_DE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.link_wera_de</code> attribute. 
	 * @param value the link_wera_de - Links www.wera.de
	 */
	public void setLink_wera_de(final String value)
	{
		setLink_wera_de( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.link_wera_de</code> attribute. 
	 * @param value the link_wera_de - Links www.wera.de
	 */
	public void setAllLink_wera_de(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,LINK_WERA_DE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.link_wera_de</code> attribute. 
	 * @param value the link_wera_de - Links www.wera.de
	 */
	public void setAllLink_wera_de(final Map<Language,String> value)
	{
		setAllLink_wera_de( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_detail_image_name</code> attribute.
	 * @return the movie_detail_image_name - Film Detailbild Name
	 */
	public String getMovie_detail_image_name(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MOVIE_DETAIL_IMAGE_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_detail_image_name</code> attribute.
	 * @return the movie_detail_image_name - Film Detailbild Name
	 */
	public String getMovie_detail_image_name()
	{
		return getMovie_detail_image_name( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_detail_image_name</code> attribute. 
	 * @param value the movie_detail_image_name - Film Detailbild Name
	 */
	public void setMovie_detail_image_name(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MOVIE_DETAIL_IMAGE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_detail_image_name</code> attribute. 
	 * @param value the movie_detail_image_name - Film Detailbild Name
	 */
	public void setMovie_detail_image_name(final String value)
	{
		setMovie_detail_image_name( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_start_date</code> attribute.
	 * @return the movie_start_date - Film Start Datum
	 */
	public Date getMovie_start_date(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, MOVIE_START_DATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_start_date</code> attribute.
	 * @return the movie_start_date - Film Start Datum
	 */
	public Date getMovie_start_date()
	{
		return getMovie_start_date( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_start_date</code> attribute. 
	 * @param value the movie_start_date - Film Start Datum
	 */
	public void setMovie_start_date(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, MOVIE_START_DATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_start_date</code> attribute. 
	 * @param value the movie_start_date - Film Start Datum
	 */
	public void setMovie_start_date(final Date value)
	{
		setMovie_start_date( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_subtitle</code> attribute.
	 * @return the movie_subtitle - Film Untertitel
	 */
	public String getMovie_subtitle(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.getMovie_subtitle requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, MOVIE_SUBTITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_subtitle</code> attribute.
	 * @return the movie_subtitle - Film Untertitel
	 */
	public String getMovie_subtitle()
	{
		return getMovie_subtitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_subtitle</code> attribute. 
	 * @return the localized movie_subtitle - Film Untertitel
	 */
	public Map<Language,String> getAllMovie_subtitle(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,MOVIE_SUBTITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_subtitle</code> attribute. 
	 * @return the localized movie_subtitle - Film Untertitel
	 */
	public Map<Language,String> getAllMovie_subtitle()
	{
		return getAllMovie_subtitle( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_subtitle</code> attribute. 
	 * @param value the movie_subtitle - Film Untertitel
	 */
	public void setMovie_subtitle(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.setMovie_subtitle requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MOVIE_SUBTITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_subtitle</code> attribute. 
	 * @param value the movie_subtitle - Film Untertitel
	 */
	public void setMovie_subtitle(final String value)
	{
		setMovie_subtitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_subtitle</code> attribute. 
	 * @param value the movie_subtitle - Film Untertitel
	 */
	public void setAllMovie_subtitle(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,MOVIE_SUBTITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_subtitle</code> attribute. 
	 * @param value the movie_subtitle - Film Untertitel
	 */
	public void setAllMovie_subtitle(final Map<Language,String> value)
	{
		setAllMovie_subtitle( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_template_id</code> attribute.
	 * @return the movie_template_id - Film Template ID
	 */
	public Integer getMovie_template_id(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, MOVIE_TEMPLATE_ID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_template_id</code> attribute.
	 * @return the movie_template_id - Film Template ID
	 */
	public Integer getMovie_template_id()
	{
		return getMovie_template_id( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_template_id</code> attribute. 
	 * @return the movie_template_id - Film Template ID
	 */
	public int getMovie_template_idAsPrimitive(final SessionContext ctx)
	{
		Integer value = getMovie_template_id( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_template_id</code> attribute. 
	 * @return the movie_template_id - Film Template ID
	 */
	public int getMovie_template_idAsPrimitive()
	{
		return getMovie_template_idAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_template_id</code> attribute. 
	 * @param value the movie_template_id - Film Template ID
	 */
	public void setMovie_template_id(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, MOVIE_TEMPLATE_ID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_template_id</code> attribute. 
	 * @param value the movie_template_id - Film Template ID
	 */
	public void setMovie_template_id(final Integer value)
	{
		setMovie_template_id( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_template_id</code> attribute. 
	 * @param value the movie_template_id - Film Template ID
	 */
	public void setMovie_template_id(final SessionContext ctx, final int value)
	{
		setMovie_template_id( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_template_id</code> attribute. 
	 * @param value the movie_template_id - Film Template ID
	 */
	public void setMovie_template_id(final int value)
	{
		setMovie_template_id( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_tile_image_name</code> attribute.
	 * @return the movie_tile_image_name - Film Kachelbild Name
	 */
	public String getMovie_tile_image_name(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MOVIE_TILE_IMAGE_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_tile_image_name</code> attribute.
	 * @return the movie_tile_image_name - Film Kachelbild Name
	 */
	public String getMovie_tile_image_name()
	{
		return getMovie_tile_image_name( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_tile_image_name</code> attribute. 
	 * @param value the movie_tile_image_name - Film Kachelbild Name
	 */
	public void setMovie_tile_image_name(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MOVIE_TILE_IMAGE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_tile_image_name</code> attribute. 
	 * @param value the movie_tile_image_name - Film Kachelbild Name
	 */
	public void setMovie_tile_image_name(final String value)
	{
		setMovie_tile_image_name( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_title</code> attribute.
	 * @return the movie_title - Film Titel
	 */
	public String getMovie_title(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.getMovie_title requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, MOVIE_TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_title</code> attribute.
	 * @return the movie_title - Film Titel
	 */
	public String getMovie_title()
	{
		return getMovie_title( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_title</code> attribute. 
	 * @return the localized movie_title - Film Titel
	 */
	public Map<Language,String> getAllMovie_title(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,MOVIE_TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_title</code> attribute. 
	 * @return the localized movie_title - Film Titel
	 */
	public Map<Language,String> getAllMovie_title()
	{
		return getAllMovie_title( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_title</code> attribute. 
	 * @param value the movie_title - Film Titel
	 */
	public void setMovie_title(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.setMovie_title requires a session language", 0 );
		}
		setLocalizedProperty(ctx, MOVIE_TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_title</code> attribute. 
	 * @param value the movie_title - Film Titel
	 */
	public void setMovie_title(final String value)
	{
		setMovie_title( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_title</code> attribute. 
	 * @param value the movie_title - Film Titel
	 */
	public void setAllMovie_title(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,MOVIE_TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_title</code> attribute. 
	 * @param value the movie_title - Film Titel
	 */
	public void setAllMovie_title(final Map<Language,String> value)
	{
		setAllMovie_title( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_type</code> attribute.
	 * @return the movie_type - Film Typ
	 */
	public EnumerationValue getMovie_type(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, MOVIE_TYPE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.movie_type</code> attribute.
	 * @return the movie_type - Film Typ
	 */
	public EnumerationValue getMovie_type()
	{
		return getMovie_type( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_type</code> attribute. 
	 * @param value the movie_type - Film Typ
	 */
	public void setMovie_type(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, MOVIE_TYPE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.movie_type</code> attribute. 
	 * @param value the movie_type - Film Typ
	 */
	public void setMovie_type(final EnumerationValue value)
	{
		setMovie_type( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.preview_link_wera_de</code> attribute.
	 * @return the preview_link_wera_de - Preview Links www.wera.de
	 */
	public abstract String getPreview_link_wera_de(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.preview_link_wera_de</code> attribute.
	 * @return the preview_link_wera_de - Preview Links www.wera.de
	 */
	public String getPreview_link_wera_de()
	{
		return getPreview_link_wera_de( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.preview_link_wera_de</code> attribute. 
	 * @return the localized preview_link_wera_de - Preview Links www.wera.de
	 */
	public abstract Map<Language,String> getAllPreview_link_wera_de(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.preview_link_wera_de</code> attribute. 
	 * @return the localized preview_link_wera_de - Preview Links www.wera.de
	 */
	public Map<Language,String> getAllPreview_link_wera_de()
	{
		return getAllPreview_link_wera_de( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.target_self</code> attribute.
	 * @return the target_self - Im neuen Fenster öffnen
	 */
	public Boolean isTarget_self(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, TARGET_SELF);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.target_self</code> attribute.
	 * @return the target_self - Im neuen Fenster öffnen
	 */
	public Boolean isTarget_self()
	{
		return isTarget_self( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.target_self</code> attribute. 
	 * @return the target_self - Im neuen Fenster öffnen
	 */
	public boolean isTarget_selfAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isTarget_self( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.target_self</code> attribute. 
	 * @return the target_self - Im neuen Fenster öffnen
	 */
	public boolean isTarget_selfAsPrimitive()
	{
		return isTarget_selfAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.target_self</code> attribute. 
	 * @param value the target_self - Im neuen Fenster öffnen
	 */
	public void setTarget_self(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, TARGET_SELF,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.target_self</code> attribute. 
	 * @param value the target_self - Im neuen Fenster öffnen
	 */
	public void setTarget_self(final Boolean value)
	{
		setTarget_self( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.target_self</code> attribute. 
	 * @param value the target_self - Im neuen Fenster öffnen
	 */
	public void setTarget_self(final SessionContext ctx, final boolean value)
	{
		setTarget_self( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.target_self</code> attribute. 
	 * @param value the target_self - Im neuen Fenster öffnen
	 */
	public void setTarget_self(final boolean value)
	{
		setTarget_self( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.weblinktext2weblink</code> attribute.
	 * @return the weblinktext2weblink
	 */
	public Collection<WeblinkText> getWeblinktext2weblink(final SessionContext ctx)
	{
		final List<WeblinkText> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.weblinktext2weblink</code> attribute.
	 * @return the weblinktext2weblink
	 */
	public Collection<WeblinkText> getWeblinktext2weblink()
	{
		return getWeblinktext2weblink( getSession().getSessionContext() );
	}
	
	public long getWeblinktext2weblinkCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null
		);
	}
	
	public long getWeblinktext2weblinkCount()
	{
		return getWeblinktext2weblinkCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.weblinktext2weblink</code> attribute. 
	 * @param value the weblinktext2weblink
	 */
	public void setWeblinktext2weblink(final SessionContext ctx, final Collection<WeblinkText> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.weblinktext2weblink</code> attribute. 
	 * @param value the weblinktext2weblink
	 */
	public void setWeblinktext2weblink(final Collection<WeblinkText> value)
	{
		setWeblinktext2weblink( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinktext2weblink. 
	 * @param value the item to add to weblinktext2weblink
	 */
	public void addToWeblinktext2weblink(final SessionContext ctx, final WeblinkText value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinktext2weblink. 
	 * @param value the item to add to weblinktext2weblink
	 */
	public void addToWeblinktext2weblink(final WeblinkText value)
	{
		addToWeblinktext2weblink( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinktext2weblink. 
	 * @param value the item to remove from weblinktext2weblink
	 */
	public void removeFromWeblinktext2weblink(final SessionContext ctx, final WeblinkText value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WEBLINKWEBLINKTEXTRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinktext2weblink. 
	 * @param value the item to remove from weblinktext2weblink
	 */
	public void removeFromWeblinktext2weblink(final WeblinkText value)
	{
		removeFromWeblinktext2weblink( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.weraproducts</code> attribute.
	 * @return the weraproducts
	 */
	public Collection<WeraProduct> getWeraproducts(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.weraproducts</code> attribute.
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
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
			null
		);
	}
	
	public long getWeraproductsCount()
	{
		return getWeraproductsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.weraproducts</code> attribute. 
	 * @param value the weraproducts
	 */
	public void setWeraproducts(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.weraproducts</code> attribute. 
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
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
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
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
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
	 * <i>Generated method</i> - Getter of the <code>Weblink.weravariants</code> attribute.
	 * @return the weravariants
	 */
	public Collection<WeraVariante> getWeravariants(final SessionContext ctx)
	{
		final List<WeraVariante> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEWEBLINKRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.weravariants</code> attribute.
	 * @return the weravariants
	 */
	public Collection<WeraVariante> getWeravariants()
	{
		return getWeravariants( getSession().getSessionContext() );
	}
	
	public long getWeravariantsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEWEBLINKRELATION,
			null
		);
	}
	
	public long getWeravariantsCount()
	{
		return getWeravariantsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.weravariants</code> attribute. 
	 * @param value the weravariants
	 */
	public void setWeravariants(final SessionContext ctx, final Collection<WeraVariante> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEWEBLINKRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.weravariants</code> attribute. 
	 * @param value the weravariants
	 */
	public void setWeravariants(final Collection<WeraVariante> value)
	{
		setWeravariants( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weravariants. 
	 * @param value the item to add to weravariants
	 */
	public void addToWeravariants(final SessionContext ctx, final WeraVariante value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEWEBLINKRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weravariants. 
	 * @param value the item to add to weravariants
	 */
	public void addToWeravariants(final WeraVariante value)
	{
		addToWeravariants( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weravariants. 
	 * @param value the item to remove from weravariants
	 */
	public void removeFromWeravariants(final SessionContext ctx, final WeraVariante value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAVARIANTEWEBLINKRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weravariants. 
	 * @param value the item to remove from weravariants
	 */
	public void removeFromWeravariants(final WeraVariante value)
	{
		removeFromWeravariants( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.youtube_id</code> attribute.
	 * @return the youtube_id - Youtube ID
	 */
	public String getYoutube_id(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.getYoutube_id requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, YOUTUBE_ID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.youtube_id</code> attribute.
	 * @return the youtube_id - Youtube ID
	 */
	public String getYoutube_id()
	{
		return getYoutube_id( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.youtube_id</code> attribute. 
	 * @return the localized youtube_id - Youtube ID
	 */
	public Map<Language,String> getAllYoutube_id(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,YOUTUBE_ID,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Weblink.youtube_id</code> attribute. 
	 * @return the localized youtube_id - Youtube ID
	 */
	public Map<Language,String> getAllYoutube_id()
	{
		return getAllYoutube_id( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.youtube_id</code> attribute. 
	 * @param value the youtube_id - Youtube ID
	 */
	public void setYoutube_id(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeblink.setYoutube_id requires a session language", 0 );
		}
		setLocalizedProperty(ctx, YOUTUBE_ID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.youtube_id</code> attribute. 
	 * @param value the youtube_id - Youtube ID
	 */
	public void setYoutube_id(final String value)
	{
		setYoutube_id( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.youtube_id</code> attribute. 
	 * @param value the youtube_id - Youtube ID
	 */
	public void setAllYoutube_id(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,YOUTUBE_ID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Weblink.youtube_id</code> attribute. 
	 * @param value the youtube_id - Youtube ID
	 */
	public void setAllYoutube_id(final Map<Language,String> value)
	{
		setAllYoutube_id( getSession().getSessionContext(), value );
	}
	
}

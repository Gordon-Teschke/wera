/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.Outputcontrol;
import com.computationaldesign.wera.jalo.Textitem;
import com.computationaldesign.wera.jalo.Tips;
import com.computationaldesign.wera.jalo.WeraProduct;
import de.hybris.platform.catalog.jalo.Keyword;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.media.Media;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.WeraMedia WeraMedia}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeraMedia extends Media
{
	/** Qualifier of the <code>WeraMedia.feature_description</code> attribute **/
	public static final String FEATURE_DESCRIPTION = "feature_description".intern();
	/** Qualifier of the <code>WeraMedia.thumbname</code> attribute **/
	public static final String THUMBNAME = "thumbname".intern();
	/** Qualifier of the <code>WeraMedia.products4picture2</code> attribute **/
	public static final String PRODUCTS4PICTURE2 = "products4picture2".intern();
	/** Qualifier of the <code>WeraMedia.keywords</code> attribute **/
	public static final String KEYWORDS = "keywords".intern();
	/** Qualifier of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute **/
	public static final String RAHMENBREITE_IN_PERCENT = "rahmenbreite_in_percent".intern();
	/** Qualifier of the <code>WeraMedia.products4featureicon2</code> attribute **/
	public static final String PRODUCTS4FEATUREICON2 = "products4featureicon2".intern();
	/** Qualifier of the <code>WeraMedia.intSizeX</code> attribute **/
	public static final String INTSIZEX = "intSizeX".intern();
	/** Qualifier of the <code>WeraMedia.thumbpath</code> attribute **/
	public static final String THUMBPATH = "thumbpath".intern();
	/** Qualifier of the <code>WeraMedia.categories4icon</code> attribute **/
	public static final String CATEGORIES4ICON = "categories4icon".intern();
	/** Qualifier of the <code>WeraMedia.Outputcontrol4icon</code> attribute **/
	public static final String OUTPUTCONTROL4ICON = "Outputcontrol4icon".intern();
	/** Qualifier of the <code>WeraMedia.products4picture1</code> attribute **/
	public static final String PRODUCTS4PICTURE1 = "products4picture1".intern();
	/** Qualifier of the <code>WeraMedia.products4others_productpictures</code> attribute **/
	public static final String PRODUCTS4OTHERS_PRODUCTPICTURES = "products4others_productpictures".intern();
	/** Qualifier of the <code>WeraMedia.hybrisfilename</code> attribute **/
	public static final String HYBRISFILENAME = "hybrisfilename".intern();
	/** Qualifier of the <code>WeraMedia.tips4weramedia</code> attribute **/
	public static final String TIPS4WERAMEDIA = "tips4weramedia".intern();
	/** Qualifier of the <code>WeraMedia.products4icon1</code> attribute **/
	public static final String PRODUCTS4ICON1 = "products4icon1".intern();
	/** Qualifier of the <code>WeraMedia.intSizeY</code> attribute **/
	public static final String INTSIZEY = "intSizeY".intern();
	/** Qualifier of the <code>WeraMedia.products4featureicon1</code> attribute **/
	public static final String PRODUCTS4FEATUREICON1 = "products4featureicon1".intern();
	/** Qualifier of the <code>WeraMedia.products4picture3</code> attribute **/
	public static final String PRODUCTS4PICTURE3 = "products4picture3".intern();
	/** Qualifier of the <code>WeraMedia.mediakategorie</code> attribute **/
	public static final String MEDIAKATEGORIE = "mediakategorie".intern();
	/** Qualifier of the <code>WeraMedia.products4icon2</code> attribute **/
	public static final String PRODUCTS4ICON2 = "products4icon2".intern();
	/** Qualifier of the <code>WeraMedia.intDPI</code> attribute **/
	public static final String INTDPI = "intDPI".intern();
	/** Qualifier of the <code>WeraMedia.textitems4pictures</code> attribute **/
	public static final String TEXTITEMS4PICTURES = "textitems4pictures".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.categories4icon</code> attribute.
	 * @return the categories4icon
	 */
	public Collection<Category> getCategories4icon(final SessionContext ctx)
	{
		final List<Category> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.categories4icon</code> attribute.
	 * @return the categories4icon
	 */
	public Collection<Category> getCategories4icon()
	{
		return getCategories4icon( getSession().getSessionContext() );
	}
	
	public long getCategories4iconCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null
		);
	}
	
	public long getCategories4iconCount()
	{
		return getCategories4iconCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.categories4icon</code> attribute. 
	 * @param value the categories4icon
	 */
	public void setCategories4icon(final SessionContext ctx, final Collection<Category> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.categories4icon</code> attribute. 
	 * @param value the categories4icon
	 */
	public void setCategories4icon(final Collection<Category> value)
	{
		setCategories4icon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to categories4icon. 
	 * @param value the item to add to categories4icon
	 */
	public void addToCategories4icon(final SessionContext ctx, final Category value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to categories4icon. 
	 * @param value the item to add to categories4icon
	 */
	public void addToCategories4icon(final Category value)
	{
		addToCategories4icon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from categories4icon. 
	 * @param value the item to remove from categories4icon
	 */
	public void removeFromCategories4icon(final SessionContext ctx, final Category value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from categories4icon. 
	 * @param value the item to remove from categories4icon
	 */
	public void removeFromCategories4icon(final Category value)
	{
		removeFromCategories4icon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.feature_description</code> attribute.
	 * @return the feature_description
	 */
	public String getFeature_description(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraMedia.getFeature_description requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, FEATURE_DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.feature_description</code> attribute.
	 * @return the feature_description
	 */
	public String getFeature_description()
	{
		return getFeature_description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.feature_description</code> attribute. 
	 * @return the localized feature_description
	 */
	public Map<Language,String> getAllFeature_description(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,FEATURE_DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.feature_description</code> attribute. 
	 * @return the localized feature_description
	 */
	public Map<Language,String> getAllFeature_description()
	{
		return getAllFeature_description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.feature_description</code> attribute. 
	 * @param value the feature_description
	 */
	public void setFeature_description(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraMedia.setFeature_description requires a session language", 0 );
		}
		setLocalizedProperty(ctx, FEATURE_DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.feature_description</code> attribute. 
	 * @param value the feature_description
	 */
	public void setFeature_description(final String value)
	{
		setFeature_description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.feature_description</code> attribute. 
	 * @param value the feature_description
	 */
	public void setAllFeature_description(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,FEATURE_DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.feature_description</code> attribute. 
	 * @param value the feature_description
	 */
	public void setAllFeature_description(final Map<Language,String> value)
	{
		setAllFeature_description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.hybrisfilename</code> attribute.
	 * @return the hybrisfilename
	 */
	public String getHybrisfilename(final SessionContext ctx)
	{
		return (String)getProperty( ctx, HYBRISFILENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.hybrisfilename</code> attribute.
	 * @return the hybrisfilename
	 */
	public String getHybrisfilename()
	{
		return getHybrisfilename( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.hybrisfilename</code> attribute. 
	 * @param value the hybrisfilename
	 */
	public void setHybrisfilename(final SessionContext ctx, final String value)
	{
		setProperty(ctx, HYBRISFILENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.hybrisfilename</code> attribute. 
	 * @param value the hybrisfilename
	 */
	public void setHybrisfilename(final String value)
	{
		setHybrisfilename( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intDPI</code> attribute.
	 * @return the intDPI
	 */
	public Integer getIntDPI(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, INTDPI);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intDPI</code> attribute.
	 * @return the intDPI
	 */
	public Integer getIntDPI()
	{
		return getIntDPI( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intDPI</code> attribute. 
	 * @return the intDPI
	 */
	public int getIntDPIAsPrimitive(final SessionContext ctx)
	{
		Integer value = getIntDPI( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intDPI</code> attribute. 
	 * @return the intDPI
	 */
	public int getIntDPIAsPrimitive()
	{
		return getIntDPIAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intDPI</code> attribute. 
	 * @param value the intDPI
	 */
	public void setIntDPI(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, INTDPI,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intDPI</code> attribute. 
	 * @param value the intDPI
	 */
	public void setIntDPI(final Integer value)
	{
		setIntDPI( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intDPI</code> attribute. 
	 * @param value the intDPI
	 */
	public void setIntDPI(final SessionContext ctx, final int value)
	{
		setIntDPI( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intDPI</code> attribute. 
	 * @param value the intDPI
	 */
	public void setIntDPI(final int value)
	{
		setIntDPI( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intSizeX</code> attribute.
	 * @return the intSizeX
	 */
	public Integer getIntSizeX(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, INTSIZEX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intSizeX</code> attribute.
	 * @return the intSizeX
	 */
	public Integer getIntSizeX()
	{
		return getIntSizeX( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intSizeX</code> attribute. 
	 * @return the intSizeX
	 */
	public int getIntSizeXAsPrimitive(final SessionContext ctx)
	{
		Integer value = getIntSizeX( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intSizeX</code> attribute. 
	 * @return the intSizeX
	 */
	public int getIntSizeXAsPrimitive()
	{
		return getIntSizeXAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intSizeX</code> attribute. 
	 * @param value the intSizeX
	 */
	public void setIntSizeX(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, INTSIZEX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intSizeX</code> attribute. 
	 * @param value the intSizeX
	 */
	public void setIntSizeX(final Integer value)
	{
		setIntSizeX( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intSizeX</code> attribute. 
	 * @param value the intSizeX
	 */
	public void setIntSizeX(final SessionContext ctx, final int value)
	{
		setIntSizeX( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intSizeX</code> attribute. 
	 * @param value the intSizeX
	 */
	public void setIntSizeX(final int value)
	{
		setIntSizeX( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intSizeY</code> attribute.
	 * @return the intSizeY
	 */
	public Integer getIntSizeY(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, INTSIZEY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intSizeY</code> attribute.
	 * @return the intSizeY
	 */
	public Integer getIntSizeY()
	{
		return getIntSizeY( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intSizeY</code> attribute. 
	 * @return the intSizeY
	 */
	public int getIntSizeYAsPrimitive(final SessionContext ctx)
	{
		Integer value = getIntSizeY( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.intSizeY</code> attribute. 
	 * @return the intSizeY
	 */
	public int getIntSizeYAsPrimitive()
	{
		return getIntSizeYAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intSizeY</code> attribute. 
	 * @param value the intSizeY
	 */
	public void setIntSizeY(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, INTSIZEY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intSizeY</code> attribute. 
	 * @param value the intSizeY
	 */
	public void setIntSizeY(final Integer value)
	{
		setIntSizeY( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intSizeY</code> attribute. 
	 * @param value the intSizeY
	 */
	public void setIntSizeY(final SessionContext ctx, final int value)
	{
		setIntSizeY( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.intSizeY</code> attribute. 
	 * @param value the intSizeY
	 */
	public void setIntSizeY(final int value)
	{
		setIntSizeY( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.keywords</code> attribute.
	 * @return the keywords
	 */
	public Collection<Keyword> getKeywords(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraMedia.getKeywords requires a session language", 0 );
		}
		final List<Keyword> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			ctx.getLanguage(),
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.keywords</code> attribute.
	 * @return the keywords
	 */
	public Collection<Keyword> getKeywords()
	{
		return getKeywords( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.keywords</code> attribute. 
	 * @return the localized keywords
	 */
	public Map<Language,Collection<Keyword>> getAllKeywords(final SessionContext ctx)
	{
		Map<Language,Collection<Keyword>> values = getAllLinkedItems( 
			false,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION
		);
		return values;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.keywords</code> attribute. 
	 * @return the localized keywords
	 */
	public Map<Language,Collection<Keyword>> getAllKeywords()
	{
		return getAllKeywords( getSession().getSessionContext() );
	}
	
	public long getKeywordsCount(final SessionContext ctx, final Language lang)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			lang
		);
	}
	
	public long getKeywordsCount(final Language lang)
	{
		return getKeywordsCount( getSession().getSessionContext(),lang );
	}
	
	public long getKeywordsCount(final SessionContext ctx)
	{
		return getKeywordsCount( ctx, ctx.getLanguage() );
	}
	
	public long getKeywordsCount()
	{
		return getKeywordsCount( getSession().getSessionContext(), getSession().getSessionContext().getLanguage() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.keywords</code> attribute. 
	 * @param value the keywords
	 */
	public void setKeywords(final SessionContext ctx, final Collection<Keyword> value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraMedia.setKeywords requires a session language", 0 );
		}
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			ctx.getLanguage(),
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.keywords</code> attribute. 
	 * @param value the keywords
	 */
	public void setKeywords(final Collection<Keyword> value)
	{
		setKeywords( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.keywords</code> attribute. 
	 * @param value the keywords
	 */
	public void setAllKeywords(final SessionContext ctx, final Map<Language,Collection<Keyword>> value)
	{
		setAllLinkedItems( 
			getAllValuesSessionContext(ctx),
			false,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			value
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.keywords</code> attribute. 
	 * @param value the keywords
	 */
	public void setAllKeywords(final Map<Language,Collection<Keyword>> value)
	{
		setAllKeywords( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to keywords. 
	 * @param value the item to add to keywords
	 */
	public void addToKeywords(final SessionContext ctx, final Language lang, final Keyword value)
	{
		if( lang == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraMedia.addToKeywords requires a language language", 0 );
		}
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			lang,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to keywords. 
	 * @param value the item to add to keywords
	 */
	public void addToKeywords(final Language lang, final Keyword value)
	{
		addToKeywords( getSession().getSessionContext(), lang, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from keywords. 
	 * @param value the item to remove from keywords
	 */
	public void removeFromKeywords(final SessionContext ctx, final Language lang, final Keyword value)
	{
		if( lang == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraMedia.removeFromKeywords requires a session language", 0 );
		}
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			lang,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from keywords. 
	 * @param value the item to remove from keywords
	 */
	public void removeFromKeywords(final Language lang, final Keyword value)
	{
		removeFromKeywords( getSession().getSessionContext(), lang, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.mediakategorie</code> attribute.
	 * @return the mediakategorie
	 */
	public String getMediakategorie(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MEDIAKATEGORIE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.mediakategorie</code> attribute.
	 * @return the mediakategorie
	 */
	public String getMediakategorie()
	{
		return getMediakategorie( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.mediakategorie</code> attribute. 
	 * @param value the mediakategorie
	 */
	public void setMediakategorie(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MEDIAKATEGORIE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.mediakategorie</code> attribute. 
	 * @param value the mediakategorie
	 */
	public void setMediakategorie(final String value)
	{
		setMediakategorie( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.Outputcontrol4icon</code> attribute.
	 * @return the Outputcontrol4icon
	 */
	public Collection<Outputcontrol> getOutputcontrol4icon(final SessionContext ctx)
	{
		final List<Outputcontrol> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.Outputcontrol4icon</code> attribute.
	 * @return the Outputcontrol4icon
	 */
	public Collection<Outputcontrol> getOutputcontrol4icon()
	{
		return getOutputcontrol4icon( getSession().getSessionContext() );
	}
	
	public long getOutputcontrol4iconCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null
		);
	}
	
	public long getOutputcontrol4iconCount()
	{
		return getOutputcontrol4iconCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.Outputcontrol4icon</code> attribute. 
	 * @param value the Outputcontrol4icon
	 */
	public void setOutputcontrol4icon(final SessionContext ctx, final Collection<Outputcontrol> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.Outputcontrol4icon</code> attribute. 
	 * @param value the Outputcontrol4icon
	 */
	public void setOutputcontrol4icon(final Collection<Outputcontrol> value)
	{
		setOutputcontrol4icon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to Outputcontrol4icon. 
	 * @param value the item to add to Outputcontrol4icon
	 */
	public void addToOutputcontrol4icon(final SessionContext ctx, final Outputcontrol value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to Outputcontrol4icon. 
	 * @param value the item to add to Outputcontrol4icon
	 */
	public void addToOutputcontrol4icon(final Outputcontrol value)
	{
		addToOutputcontrol4icon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from Outputcontrol4icon. 
	 * @param value the item to remove from Outputcontrol4icon
	 */
	public void removeFromOutputcontrol4icon(final SessionContext ctx, final Outputcontrol value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.OUTPUTCONTROLICONRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from Outputcontrol4icon. 
	 * @param value the item to remove from Outputcontrol4icon
	 */
	public void removeFromOutputcontrol4icon(final Outputcontrol value)
	{
		removeFromOutputcontrol4icon( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4featureicon1</code> attribute.
	 * @return the products4featureicon1
	 */
	public Collection<WeraProduct> getProducts4featureicon1(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4featureicon1</code> attribute.
	 * @return the products4featureicon1
	 */
	public Collection<WeraProduct> getProducts4featureicon1()
	{
		return getProducts4featureicon1( getSession().getSessionContext() );
	}
	
	public long getProducts4featureicon1Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null
		);
	}
	
	public long getProducts4featureicon1Count()
	{
		return getProducts4featureicon1Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4featureicon1</code> attribute. 
	 * @param value the products4featureicon1
	 */
	public void setProducts4featureicon1(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4featureicon1</code> attribute. 
	 * @param value the products4featureicon1
	 */
	public void setProducts4featureicon1(final Collection<WeraProduct> value)
	{
		setProducts4featureicon1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4featureicon1. 
	 * @param value the item to add to products4featureicon1
	 */
	public void addToProducts4featureicon1(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4featureicon1. 
	 * @param value the item to add to products4featureicon1
	 */
	public void addToProducts4featureicon1(final WeraProduct value)
	{
		addToProducts4featureicon1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4featureicon1. 
	 * @param value the item to remove from products4featureicon1
	 */
	public void removeFromProducts4featureicon1(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4featureicon1. 
	 * @param value the item to remove from products4featureicon1
	 */
	public void removeFromProducts4featureicon1(final WeraProduct value)
	{
		removeFromProducts4featureicon1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4featureicon2</code> attribute.
	 * @return the products4featureicon2
	 */
	public Collection<WeraProduct> getProducts4featureicon2(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4featureicon2</code> attribute.
	 * @return the products4featureicon2
	 */
	public Collection<WeraProduct> getProducts4featureicon2()
	{
		return getProducts4featureicon2( getSession().getSessionContext() );
	}
	
	public long getProducts4featureicon2Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null
		);
	}
	
	public long getProducts4featureicon2Count()
	{
		return getProducts4featureicon2Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4featureicon2</code> attribute. 
	 * @param value the products4featureicon2
	 */
	public void setProducts4featureicon2(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4featureicon2</code> attribute. 
	 * @param value the products4featureicon2
	 */
	public void setProducts4featureicon2(final Collection<WeraProduct> value)
	{
		setProducts4featureicon2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4featureicon2. 
	 * @param value the item to add to products4featureicon2
	 */
	public void addToProducts4featureicon2(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4featureicon2. 
	 * @param value the item to add to products4featureicon2
	 */
	public void addToProducts4featureicon2(final WeraProduct value)
	{
		addToProducts4featureicon2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4featureicon2. 
	 * @param value the item to remove from products4featureicon2
	 */
	public void removeFromProducts4featureicon2(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4featureicon2. 
	 * @param value the item to remove from products4featureicon2
	 */
	public void removeFromProducts4featureicon2(final WeraProduct value)
	{
		removeFromProducts4featureicon2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4icon1</code> attribute.
	 * @return the products4icon1
	 */
	public Collection<WeraProduct> getProducts4icon1(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4icon1</code> attribute.
	 * @return the products4icon1
	 */
	public Collection<WeraProduct> getProducts4icon1()
	{
		return getProducts4icon1( getSession().getSessionContext() );
	}
	
	public long getProducts4icon1Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null
		);
	}
	
	public long getProducts4icon1Count()
	{
		return getProducts4icon1Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4icon1</code> attribute. 
	 * @param value the products4icon1
	 */
	public void setProducts4icon1(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4icon1</code> attribute. 
	 * @param value the products4icon1
	 */
	public void setProducts4icon1(final Collection<WeraProduct> value)
	{
		setProducts4icon1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4icon1. 
	 * @param value the item to add to products4icon1
	 */
	public void addToProducts4icon1(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4icon1. 
	 * @param value the item to add to products4icon1
	 */
	public void addToProducts4icon1(final WeraProduct value)
	{
		addToProducts4icon1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4icon1. 
	 * @param value the item to remove from products4icon1
	 */
	public void removeFromProducts4icon1(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4icon1. 
	 * @param value the item to remove from products4icon1
	 */
	public void removeFromProducts4icon1(final WeraProduct value)
	{
		removeFromProducts4icon1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4icon2</code> attribute.
	 * @return the products4icon2
	 */
	public Collection<WeraProduct> getProducts4icon2(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4icon2</code> attribute.
	 * @return the products4icon2
	 */
	public Collection<WeraProduct> getProducts4icon2()
	{
		return getProducts4icon2( getSession().getSessionContext() );
	}
	
	public long getProducts4icon2Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null
		);
	}
	
	public long getProducts4icon2Count()
	{
		return getProducts4icon2Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4icon2</code> attribute. 
	 * @param value the products4icon2
	 */
	public void setProducts4icon2(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4icon2</code> attribute. 
	 * @param value the products4icon2
	 */
	public void setProducts4icon2(final Collection<WeraProduct> value)
	{
		setProducts4icon2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4icon2. 
	 * @param value the item to add to products4icon2
	 */
	public void addToProducts4icon2(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4icon2. 
	 * @param value the item to add to products4icon2
	 */
	public void addToProducts4icon2(final WeraProduct value)
	{
		addToProducts4icon2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4icon2. 
	 * @param value the item to remove from products4icon2
	 */
	public void removeFromProducts4icon2(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4icon2. 
	 * @param value the item to remove from products4icon2
	 */
	public void removeFromProducts4icon2(final WeraProduct value)
	{
		removeFromProducts4icon2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4others_productpictures</code> attribute.
	 * @return the products4others_productpictures
	 */
	public Collection<WeraProduct> getProducts4others_productpictures(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4others_productpictures</code> attribute.
	 * @return the products4others_productpictures
	 */
	public Collection<WeraProduct> getProducts4others_productpictures()
	{
		return getProducts4others_productpictures( getSession().getSessionContext() );
	}
	
	public long getProducts4others_productpicturesCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null
		);
	}
	
	public long getProducts4others_productpicturesCount()
	{
		return getProducts4others_productpicturesCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4others_productpictures</code> attribute. 
	 * @param value the products4others_productpictures
	 */
	public void setProducts4others_productpictures(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4others_productpictures</code> attribute. 
	 * @param value the products4others_productpictures
	 */
	public void setProducts4others_productpictures(final Collection<WeraProduct> value)
	{
		setProducts4others_productpictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4others_productpictures. 
	 * @param value the item to add to products4others_productpictures
	 */
	public void addToProducts4others_productpictures(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4others_productpictures. 
	 * @param value the item to add to products4others_productpictures
	 */
	public void addToProducts4others_productpictures(final WeraProduct value)
	{
		addToProducts4others_productpictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4others_productpictures. 
	 * @param value the item to remove from products4others_productpictures
	 */
	public void removeFromProducts4others_productpictures(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4others_productpictures. 
	 * @param value the item to remove from products4others_productpictures
	 */
	public void removeFromProducts4others_productpictures(final WeraProduct value)
	{
		removeFromProducts4others_productpictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4picture1</code> attribute.
	 * @return the products4picture1
	 */
	public Collection<WeraProduct> getProducts4picture1(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4picture1</code> attribute.
	 * @return the products4picture1
	 */
	public Collection<WeraProduct> getProducts4picture1()
	{
		return getProducts4picture1( getSession().getSessionContext() );
	}
	
	public long getProducts4picture1Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null
		);
	}
	
	public long getProducts4picture1Count()
	{
		return getProducts4picture1Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4picture1</code> attribute. 
	 * @param value the products4picture1
	 */
	public void setProducts4picture1(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4picture1</code> attribute. 
	 * @param value the products4picture1
	 */
	public void setProducts4picture1(final Collection<WeraProduct> value)
	{
		setProducts4picture1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4picture1. 
	 * @param value the item to add to products4picture1
	 */
	public void addToProducts4picture1(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4picture1. 
	 * @param value the item to add to products4picture1
	 */
	public void addToProducts4picture1(final WeraProduct value)
	{
		addToProducts4picture1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4picture1. 
	 * @param value the item to remove from products4picture1
	 */
	public void removeFromProducts4picture1(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4picture1. 
	 * @param value the item to remove from products4picture1
	 */
	public void removeFromProducts4picture1(final WeraProduct value)
	{
		removeFromProducts4picture1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4picture2</code> attribute.
	 * @return the products4picture2
	 */
	public Collection<WeraProduct> getProducts4picture2(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4picture2</code> attribute.
	 * @return the products4picture2
	 */
	public Collection<WeraProduct> getProducts4picture2()
	{
		return getProducts4picture2( getSession().getSessionContext() );
	}
	
	public long getProducts4picture2Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null
		);
	}
	
	public long getProducts4picture2Count()
	{
		return getProducts4picture2Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4picture2</code> attribute. 
	 * @param value the products4picture2
	 */
	public void setProducts4picture2(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4picture2</code> attribute. 
	 * @param value the products4picture2
	 */
	public void setProducts4picture2(final Collection<WeraProduct> value)
	{
		setProducts4picture2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4picture2. 
	 * @param value the item to add to products4picture2
	 */
	public void addToProducts4picture2(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4picture2. 
	 * @param value the item to add to products4picture2
	 */
	public void addToProducts4picture2(final WeraProduct value)
	{
		addToProducts4picture2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4picture2. 
	 * @param value the item to remove from products4picture2
	 */
	public void removeFromProducts4picture2(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4picture2. 
	 * @param value the item to remove from products4picture2
	 */
	public void removeFromProducts4picture2(final WeraProduct value)
	{
		removeFromProducts4picture2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4picture3</code> attribute.
	 * @return the products4picture3
	 */
	public Collection<WeraProduct> getProducts4picture3(final SessionContext ctx)
	{
		final List<WeraProduct> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.products4picture3</code> attribute.
	 * @return the products4picture3
	 */
	public Collection<WeraProduct> getProducts4picture3()
	{
		return getProducts4picture3( getSession().getSessionContext() );
	}
	
	public long getProducts4picture3Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null
		);
	}
	
	public long getProducts4picture3Count()
	{
		return getProducts4picture3Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4picture3</code> attribute. 
	 * @param value the products4picture3
	 */
	public void setProducts4picture3(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.products4picture3</code> attribute. 
	 * @param value the products4picture3
	 */
	public void setProducts4picture3(final Collection<WeraProduct> value)
	{
		setProducts4picture3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4picture3. 
	 * @param value the item to add to products4picture3
	 */
	public void addToProducts4picture3(final SessionContext ctx, final WeraProduct value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to products4picture3. 
	 * @param value the item to add to products4picture3
	 */
	public void addToProducts4picture3(final WeraProduct value)
	{
		addToProducts4picture3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4picture3. 
	 * @param value the item to remove from products4picture3
	 */
	public void removeFromProducts4picture3(final SessionContext ctx, final WeraProduct value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from products4picture3. 
	 * @param value the item to remove from products4picture3
	 */
	public void removeFromProducts4picture3(final WeraProduct value)
	{
		removeFromProducts4picture3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute.
	 * @return the rahmenbreite_in_percent
	 */
	public Double getRahmenbreite_in_percent(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, RAHMENBREITE_IN_PERCENT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute.
	 * @return the rahmenbreite_in_percent
	 */
	public Double getRahmenbreite_in_percent()
	{
		return getRahmenbreite_in_percent( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute. 
	 * @return the rahmenbreite_in_percent
	 */
	public double getRahmenbreite_in_percentAsPrimitive(final SessionContext ctx)
	{
		Double value = getRahmenbreite_in_percent( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute. 
	 * @return the rahmenbreite_in_percent
	 */
	public double getRahmenbreite_in_percentAsPrimitive()
	{
		return getRahmenbreite_in_percentAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute. 
	 * @param value the rahmenbreite_in_percent
	 */
	public void setRahmenbreite_in_percent(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, RAHMENBREITE_IN_PERCENT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute. 
	 * @param value the rahmenbreite_in_percent
	 */
	public void setRahmenbreite_in_percent(final Double value)
	{
		setRahmenbreite_in_percent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute. 
	 * @param value the rahmenbreite_in_percent
	 */
	public void setRahmenbreite_in_percent(final SessionContext ctx, final double value)
	{
		setRahmenbreite_in_percent( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.rahmenbreite_in_percent</code> attribute. 
	 * @param value the rahmenbreite_in_percent
	 */
	public void setRahmenbreite_in_percent(final double value)
	{
		setRahmenbreite_in_percent( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.textitems4pictures</code> attribute.
	 * @return the textitems4pictures
	 */
	public Collection<Textitem> getTextitems4pictures(final SessionContext ctx)
	{
		final List<Textitem> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.textitems4pictures</code> attribute.
	 * @return the textitems4pictures
	 */
	public Collection<Textitem> getTextitems4pictures()
	{
		return getTextitems4pictures( getSession().getSessionContext() );
	}
	
	public long getTextitems4picturesCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null
		);
	}
	
	public long getTextitems4picturesCount()
	{
		return getTextitems4picturesCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.textitems4pictures</code> attribute. 
	 * @param value the textitems4pictures
	 */
	public void setTextitems4pictures(final SessionContext ctx, final Collection<Textitem> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.textitems4pictures</code> attribute. 
	 * @param value the textitems4pictures
	 */
	public void setTextitems4pictures(final Collection<Textitem> value)
	{
		setTextitems4pictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to textitems4pictures. 
	 * @param value the item to add to textitems4pictures
	 */
	public void addToTextitems4pictures(final SessionContext ctx, final Textitem value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to textitems4pictures. 
	 * @param value the item to add to textitems4pictures
	 */
	public void addToTextitems4pictures(final Textitem value)
	{
		addToTextitems4pictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from textitems4pictures. 
	 * @param value the item to remove from textitems4pictures
	 */
	public void removeFromTextitems4pictures(final SessionContext ctx, final Textitem value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.TEXTITEMWERAMEDIARELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from textitems4pictures. 
	 * @param value the item to remove from textitems4pictures
	 */
	public void removeFromTextitems4pictures(final Textitem value)
	{
		removeFromTextitems4pictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.thumbname</code> attribute.
	 * @return the thumbname
	 */
	public String getThumbname(final SessionContext ctx)
	{
		return (String)getProperty( ctx, THUMBNAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.thumbname</code> attribute.
	 * @return the thumbname
	 */
	public String getThumbname()
	{
		return getThumbname( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.thumbname</code> attribute. 
	 * @param value the thumbname
	 */
	public void setThumbname(final SessionContext ctx, final String value)
	{
		setProperty(ctx, THUMBNAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.thumbname</code> attribute. 
	 * @param value the thumbname
	 */
	public void setThumbname(final String value)
	{
		setThumbname( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.thumbpath</code> attribute.
	 * @return the thumbpath
	 */
	public String getThumbpath(final SessionContext ctx)
	{
		return (String)getProperty( ctx, THUMBPATH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.thumbpath</code> attribute.
	 * @return the thumbpath
	 */
	public String getThumbpath()
	{
		return getThumbpath( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.thumbpath</code> attribute. 
	 * @param value the thumbpath
	 */
	public void setThumbpath(final SessionContext ctx, final String value)
	{
		setProperty(ctx, THUMBPATH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.thumbpath</code> attribute. 
	 * @param value the thumbpath
	 */
	public void setThumbpath(final String value)
	{
		setThumbpath( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.tips4weramedia</code> attribute.
	 * @return the tips4weramedia
	 */
	public Collection<Tips> getTips4weramedia(final SessionContext ctx)
	{
		final List<Tips> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraMedia.tips4weramedia</code> attribute.
	 * @return the tips4weramedia
	 */
	public Collection<Tips> getTips4weramedia()
	{
		return getTips4weramedia( getSession().getSessionContext() );
	}
	
	public long getTips4weramediaCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null
		);
	}
	
	public long getTips4weramediaCount()
	{
		return getTips4weramediaCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.tips4weramedia</code> attribute. 
	 * @param value the tips4weramedia
	 */
	public void setTips4weramedia(final SessionContext ctx, final Collection<Tips> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraMedia.tips4weramedia</code> attribute. 
	 * @param value the tips4weramedia
	 */
	public void setTips4weramedia(final Collection<Tips> value)
	{
		setTips4weramedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to tips4weramedia. 
	 * @param value the item to add to tips4weramedia
	 */
	public void addToTips4weramedia(final SessionContext ctx, final Tips value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to tips4weramedia. 
	 * @param value the item to add to tips4weramedia
	 */
	public void addToTips4weramedia(final Tips value)
	{
		addToTips4weramedia( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from tips4weramedia. 
	 * @param value the item to remove from tips4weramedia
	 */
	public void removeFromTips4weramedia(final SessionContext ctx, final Tips value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.TIPSWERAMEDIARELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from tips4weramedia. 
	 * @param value the item to remove from tips4weramedia
	 */
	public void removeFromTips4weramedia(final Tips value)
	{
		removeFromTips4weramedia( getSession().getSessionContext(), value );
	}
	
}

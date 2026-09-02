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
import com.computationaldesign.wera.jalo.Bildreferenz;
import com.computationaldesign.wera.jalo.Category2ProductExt;
import com.computationaldesign.wera.jalo.ExtImage;
import com.computationaldesign.wera.jalo.Farbitem;
import com.computationaldesign.wera.jalo.Footnote;
import com.computationaldesign.wera.jalo.Measure;
import com.computationaldesign.wera.jalo.Outputcontrol;
import com.computationaldesign.wera.jalo.Textbaustein;
import com.computationaldesign.wera.jalo.Textitem;
import com.computationaldesign.wera.jalo.Tipp;
import com.computationaldesign.wera.jalo.Tips;
import com.computationaldesign.wera.jalo.Weblink;
import com.computationaldesign.wera.jalo.WeblinkText;
import com.computationaldesign.wera.jalo.WeraMedia;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraProductSet;
import com.computationaldesign.wera.jalo.WeraProductSetVariants;
import com.computationaldesign.wera.jalo.WeraProductSetinSet;
import com.computationaldesign.wera.jalo.WeraVariante;
import com.computationaldesign.wera.jalo.WeraVarianteSet;
import com.computationaldesign.wera.jalo.WeraVarianteVariants;
import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.Keyword;
import de.hybris.platform.catalog.jalo.ProductFeature;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttributeValue;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.europe1.jalo.PDTRow;
import de.hybris.platform.europe1.jalo.PriceRow;
import de.hybris.platform.jalo.GenericItem;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.JaloSystemException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.extension.Extension;
import de.hybris.platform.jalo.link.Link;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.Descriptor;
import de.hybris.platform.jalo.type.JaloGenericCreationException;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link WeraManager}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeraManager extends Extension
{
	protected static final OneToManyHandler<Footnote> PRODUCTFEATUREFOOTNOTERELATIONFOOTNOTESHANDLER = new OneToManyHandler<Footnote>(
	WeraConstants.TC.FOOTNOTE,
	false,
	"productfeatures".intern(),
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.aktiv</code> attribute.
	 * @return the aktiv - Ausgabe Kategorie J/N
	 */
	public Boolean isAktiv(final SessionContext ctx, final Category item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.isAktiv requires a session language", 0 );
		}
		return (Boolean)item.getLocalizedProperty( ctx, WeraConstants.Attributes.Category.AKTIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.aktiv</code> attribute.
	 * @return the aktiv - Ausgabe Kategorie J/N
	 */
	public Boolean isAktiv(final Category item)
	{
		return isAktiv( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.aktiv</code> attribute. 
	 * @return the aktiv - Ausgabe Kategorie J/N
	 */
	public boolean isAktivAsPrimitive(final SessionContext ctx, final Category item)
	{
		Boolean value = isAktiv( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.aktiv</code> attribute. 
	 * @return the aktiv - Ausgabe Kategorie J/N
	 */
	public boolean isAktivAsPrimitive(final Category item)
	{
		return isAktivAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.aktiv</code> attribute. 
	 * @return the localized aktiv - Ausgabe Kategorie J/N
	 */
	public Map<Language,Boolean> getAllAktiv(final SessionContext ctx, final Category item)
	{
		return (Map<Language,Boolean>)item.getAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.AKTIV,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.aktiv</code> attribute. 
	 * @return the localized aktiv - Ausgabe Kategorie J/N
	 */
	public Map<Language,Boolean> getAllAktiv(final Category item)
	{
		return getAllAktiv( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Kategorie J/N
	 */
	public void setAktiv(final SessionContext ctx, final Category item, final Boolean value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.setAktiv requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, WeraConstants.Attributes.Category.AKTIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Kategorie J/N
	 */
	public void setAktiv(final Category item, final Boolean value)
	{
		setAktiv( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Kategorie J/N
	 */
	public void setAktiv(final SessionContext ctx, final Category item, final boolean value)
	{
		setAktiv( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Kategorie J/N
	 */
	public void setAktiv(final Category item, final boolean value)
	{
		setAktiv( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Kategorie J/N
	 */
	public void setAllAktiv(final SessionContext ctx, final Category item, final Map<Language,Boolean> value)
	{
		item.setAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.AKTIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Kategorie J/N
	 */
	public void setAllAktiv(final Category item, final Map<Language,Boolean> value)
	{
		setAllAktiv( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.alternative_image_name</code> attribute.
	 * @return the alternative_image_name - Alternativer Kategorie Bildname
	 */
	public String getAlternative_image_name(final SessionContext ctx, final Category item)
	{
		return (String)item.getProperty( ctx, WeraConstants.Attributes.Category.ALTERNATIVE_IMAGE_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.alternative_image_name</code> attribute.
	 * @return the alternative_image_name - Alternativer Kategorie Bildname
	 */
	public String getAlternative_image_name(final Category item)
	{
		return getAlternative_image_name( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.alternative_image_name</code> attribute. 
	 * @param value the alternative_image_name - Alternativer Kategorie Bildname
	 */
	public void setAlternative_image_name(final SessionContext ctx, final Category item, final String value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.Category.ALTERNATIVE_IMAGE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.alternative_image_name</code> attribute. 
	 * @param value the alternative_image_name - Alternativer Kategorie Bildname
	 */
	public void setAlternative_image_name(final Category item, final String value)
	{
		setAlternative_image_name( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.alternative_image_name</code> attribute.
	 * @return the alternative_image_name - Alternativer Bildname
	 */
	public String getAlternative_image_name(final SessionContext ctx, final ClassificationAttribute item)
	{
		return (String)item.getProperty( ctx, WeraConstants.Attributes.ClassificationAttribute.ALTERNATIVE_IMAGE_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.alternative_image_name</code> attribute.
	 * @return the alternative_image_name - Alternativer Bildname
	 */
	public String getAlternative_image_name(final ClassificationAttribute item)
	{
		return getAlternative_image_name( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.alternative_image_name</code> attribute. 
	 * @param value the alternative_image_name - Alternativer Bildname
	 */
	public void setAlternative_image_name(final SessionContext ctx, final ClassificationAttribute item, final String value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.ClassificationAttribute.ALTERNATIVE_IMAGE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.alternative_image_name</code> attribute. 
	 * @param value the alternative_image_name - Alternativer Bildname
	 */
	public void setAlternative_image_name(final ClassificationAttribute item, final String value)
	{
		setAlternative_image_name( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.alternative_name</code> attribute.
	 * @return the alternative_name - Alternative Bezeichnung
	 */
	public String getAlternative_name(final SessionContext ctx, final Category item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.getAlternative_name requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, WeraConstants.Attributes.Category.ALTERNATIVE_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.alternative_name</code> attribute.
	 * @return the alternative_name - Alternative Bezeichnung
	 */
	public String getAlternative_name(final Category item)
	{
		return getAlternative_name( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.alternative_name</code> attribute. 
	 * @return the localized alternative_name - Alternative Bezeichnung
	 */
	public Map<Language,String> getAllAlternative_name(final SessionContext ctx, final Category item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.ALTERNATIVE_NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.alternative_name</code> attribute. 
	 * @return the localized alternative_name - Alternative Bezeichnung
	 */
	public Map<Language,String> getAllAlternative_name(final Category item)
	{
		return getAllAlternative_name( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.alternative_name</code> attribute. 
	 * @param value the alternative_name - Alternative Bezeichnung
	 */
	public void setAlternative_name(final SessionContext ctx, final Category item, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.setAlternative_name requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, WeraConstants.Attributes.Category.ALTERNATIVE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.alternative_name</code> attribute. 
	 * @param value the alternative_name - Alternative Bezeichnung
	 */
	public void setAlternative_name(final Category item, final String value)
	{
		setAlternative_name( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.alternative_name</code> attribute. 
	 * @param value the alternative_name - Alternative Bezeichnung
	 */
	public void setAllAlternative_name(final SessionContext ctx, final Category item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.ALTERNATIVE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.alternative_name</code> attribute. 
	 * @param value the alternative_name - Alternative Bezeichnung
	 */
	public void setAllAlternative_name(final Category item, final Map<Language,String> value)
	{
		setAllAlternative_name( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.alternative_name</code> attribute.
	 * @return the alternative_name - Alternative Bezeichnung
	 */
	public String getAlternative_name(final SessionContext ctx, final ClassificationAttribute item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedClassificationAttribute.getAlternative_name requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, WeraConstants.Attributes.ClassificationAttribute.ALTERNATIVE_NAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.alternative_name</code> attribute.
	 * @return the alternative_name - Alternative Bezeichnung
	 */
	public String getAlternative_name(final ClassificationAttribute item)
	{
		return getAlternative_name( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.alternative_name</code> attribute. 
	 * @return the localized alternative_name - Alternative Bezeichnung
	 */
	public Map<Language,String> getAllAlternative_name(final SessionContext ctx, final ClassificationAttribute item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,WeraConstants.Attributes.ClassificationAttribute.ALTERNATIVE_NAME,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.alternative_name</code> attribute. 
	 * @return the localized alternative_name - Alternative Bezeichnung
	 */
	public Map<Language,String> getAllAlternative_name(final ClassificationAttribute item)
	{
		return getAllAlternative_name( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.alternative_name</code> attribute. 
	 * @param value the alternative_name - Alternative Bezeichnung
	 */
	public void setAlternative_name(final SessionContext ctx, final ClassificationAttribute item, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedClassificationAttribute.setAlternative_name requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, WeraConstants.Attributes.ClassificationAttribute.ALTERNATIVE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.alternative_name</code> attribute. 
	 * @param value the alternative_name - Alternative Bezeichnung
	 */
	public void setAlternative_name(final ClassificationAttribute item, final String value)
	{
		setAlternative_name( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.alternative_name</code> attribute. 
	 * @param value the alternative_name - Alternative Bezeichnung
	 */
	public void setAllAlternative_name(final SessionContext ctx, final ClassificationAttribute item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,WeraConstants.Attributes.ClassificationAttribute.ALTERNATIVE_NAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.alternative_name</code> attribute. 
	 * @param value the alternative_name - Alternative Bezeichnung
	 */
	public void setAllAlternative_name(final ClassificationAttribute item, final Map<Language,String> value)
	{
		setAllAlternative_name( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.anwenderimagedoorwayref</code> attribute.
	 * @return the anwenderimagedoorwayref
	 */
	public Collection<Bildreferenz> getAnwenderimagedoorwayref(final SessionContext ctx, final Category item)
	{
		final List<Bildreferenz> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.anwenderimagedoorwayref</code> attribute.
	 * @return the anwenderimagedoorwayref
	 */
	public Collection<Bildreferenz> getAnwenderimagedoorwayref(final Category item)
	{
		return getAnwenderimagedoorwayref( getSession().getSessionContext(), item );
	}
	
	public long getAnwenderimagedoorwayrefCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null
		);
	}
	
	public long getAnwenderimagedoorwayrefCount(final Category item)
	{
		return getAnwenderimagedoorwayrefCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.anwenderimagedoorwayref</code> attribute. 
	 * @param value the anwenderimagedoorwayref
	 */
	public void setAnwenderimagedoorwayref(final SessionContext ctx, final Category item, final Collection<Bildreferenz> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.anwenderimagedoorwayref</code> attribute. 
	 * @param value the anwenderimagedoorwayref
	 */
	public void setAnwenderimagedoorwayref(final Category item, final Collection<Bildreferenz> value)
	{
		setAnwenderimagedoorwayref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to anwenderimagedoorwayref. 
	 * @param value the item to add to anwenderimagedoorwayref
	 */
	public void addToAnwenderimagedoorwayref(final SessionContext ctx, final Category item, final Bildreferenz value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to anwenderimagedoorwayref. 
	 * @param value the item to add to anwenderimagedoorwayref
	 */
	public void addToAnwenderimagedoorwayref(final Category item, final Bildreferenz value)
	{
		addToAnwenderimagedoorwayref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from anwenderimagedoorwayref. 
	 * @param value the item to remove from anwenderimagedoorwayref
	 */
	public void removeFromAnwenderimagedoorwayref(final SessionContext ctx, final Category item, final Bildreferenz value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGEDOORWAYRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from anwenderimagedoorwayref. 
	 * @param value the item to remove from anwenderimagedoorwayref
	 */
	public void removeFromAnwenderimagedoorwayref(final Category item, final Bildreferenz value)
	{
		removeFromAnwenderimagedoorwayref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.anwenderimageref</code> attribute.
	 * @return the anwenderimageref
	 */
	public Collection<Bildreferenz> getAnwenderimageref(final SessionContext ctx, final Category item)
	{
		final List<Bildreferenz> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.anwenderimageref</code> attribute.
	 * @return the anwenderimageref
	 */
	public Collection<Bildreferenz> getAnwenderimageref(final Category item)
	{
		return getAnwenderimageref( getSession().getSessionContext(), item );
	}
	
	public long getAnwenderimagerefCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null
		);
	}
	
	public long getAnwenderimagerefCount(final Category item)
	{
		return getAnwenderimagerefCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.anwenderimageref</code> attribute. 
	 * @param value the anwenderimageref
	 */
	public void setAnwenderimageref(final SessionContext ctx, final Category item, final Collection<Bildreferenz> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.anwenderimageref</code> attribute. 
	 * @param value the anwenderimageref
	 */
	public void setAnwenderimageref(final Category item, final Collection<Bildreferenz> value)
	{
		setAnwenderimageref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to anwenderimageref. 
	 * @param value the item to add to anwenderimageref
	 */
	public void addToAnwenderimageref(final SessionContext ctx, final Category item, final Bildreferenz value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to anwenderimageref. 
	 * @param value the item to add to anwenderimageref
	 */
	public void addToAnwenderimageref(final Category item, final Bildreferenz value)
	{
		addToAnwenderimageref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from anwenderimageref. 
	 * @param value the item to remove from anwenderimageref
	 */
	public void removeFromAnwenderimageref(final SessionContext ctx, final Category item, final Bildreferenz value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from anwenderimageref. 
	 * @param value the item to remove from anwenderimageref
	 */
	public void removeFromAnwenderimageref(final Category item, final Bildreferenz value)
	{
		removeFromAnwenderimageref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.archiviert</code> attribute.
	 * @return the archiviert - Katalog archiviert J/N
	 */
	public Boolean isArchiviert(final SessionContext ctx, final CatalogVersion item)
	{
		return (Boolean)item.getProperty( ctx, WeraConstants.Attributes.CatalogVersion.ARCHIVIERT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.archiviert</code> attribute.
	 * @return the archiviert - Katalog archiviert J/N
	 */
	public Boolean isArchiviert(final CatalogVersion item)
	{
		return isArchiviert( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.archiviert</code> attribute. 
	 * @return the archiviert - Katalog archiviert J/N
	 */
	public boolean isArchiviertAsPrimitive(final SessionContext ctx, final CatalogVersion item)
	{
		Boolean value = isArchiviert( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.archiviert</code> attribute. 
	 * @return the archiviert - Katalog archiviert J/N
	 */
	public boolean isArchiviertAsPrimitive(final CatalogVersion item)
	{
		return isArchiviertAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.archiviert</code> attribute. 
	 * @param value the archiviert - Katalog archiviert J/N
	 */
	public void setArchiviert(final SessionContext ctx, final CatalogVersion item, final Boolean value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.CatalogVersion.ARCHIVIERT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.archiviert</code> attribute. 
	 * @param value the archiviert - Katalog archiviert J/N
	 */
	public void setArchiviert(final CatalogVersion item, final Boolean value)
	{
		setArchiviert( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.archiviert</code> attribute. 
	 * @param value the archiviert - Katalog archiviert J/N
	 */
	public void setArchiviert(final SessionContext ctx, final CatalogVersion item, final boolean value)
	{
		setArchiviert( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.archiviert</code> attribute. 
	 * @param value the archiviert - Katalog archiviert J/N
	 */
	public void setArchiviert(final CatalogVersion item, final boolean value)
	{
		setArchiviert( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.aufanfrage</code> attribute.
	 * @return the aufanfrage - Preis auf Anfrage
	 */
	public Boolean isAufanfrage(final SessionContext ctx, final PriceRow item)
	{
		return (Boolean)item.getProperty( ctx, WeraConstants.Attributes.PriceRow.AUFANFRAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.aufanfrage</code> attribute.
	 * @return the aufanfrage - Preis auf Anfrage
	 */
	public Boolean isAufanfrage(final PriceRow item)
	{
		return isAufanfrage( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.aufanfrage</code> attribute. 
	 * @return the aufanfrage - Preis auf Anfrage
	 */
	public boolean isAufanfrageAsPrimitive(final SessionContext ctx, final PriceRow item)
	{
		Boolean value = isAufanfrage( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.aufanfrage</code> attribute. 
	 * @return the aufanfrage - Preis auf Anfrage
	 */
	public boolean isAufanfrageAsPrimitive(final PriceRow item)
	{
		return isAufanfrageAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.aufanfrage</code> attribute. 
	 * @param value the aufanfrage - Preis auf Anfrage
	 */
	public void setAufanfrage(final SessionContext ctx, final PriceRow item, final Boolean value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.PriceRow.AUFANFRAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.aufanfrage</code> attribute. 
	 * @param value the aufanfrage - Preis auf Anfrage
	 */
	public void setAufanfrage(final PriceRow item, final Boolean value)
	{
		setAufanfrage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.aufanfrage</code> attribute. 
	 * @param value the aufanfrage - Preis auf Anfrage
	 */
	public void setAufanfrage(final SessionContext ctx, final PriceRow item, final boolean value)
	{
		setAufanfrage( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.aufanfrage</code> attribute. 
	 * @param value the aufanfrage - Preis auf Anfrage
	 */
	public void setAufanfrage(final PriceRow item, final boolean value)
	{
		setAufanfrage( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.background</code> attribute.
	 * @return the background - Hintergrundfarbe J/N
	 */
	public Boolean isBackground(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedClassAttributeAssignment.isBackground requires a session language", 0 );
		}
		return (Boolean)item.getLocalizedProperty( ctx, WeraConstants.Attributes.ClassAttributeAssignment.BACKGROUND);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.background</code> attribute.
	 * @return the background - Hintergrundfarbe J/N
	 */
	public Boolean isBackground(final ClassAttributeAssignment item)
	{
		return isBackground( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @return the background - Hintergrundfarbe J/N
	 */
	public boolean isBackgroundAsPrimitive(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		Boolean value = isBackground( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @return the background - Hintergrundfarbe J/N
	 */
	public boolean isBackgroundAsPrimitive(final ClassAttributeAssignment item)
	{
		return isBackgroundAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @return the localized background - Hintergrundfarbe J/N
	 */
	public Map<Language,Boolean> getAllBackground(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		return (Map<Language,Boolean>)item.getAllLocalizedProperties(ctx,WeraConstants.Attributes.ClassAttributeAssignment.BACKGROUND,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @return the localized background - Hintergrundfarbe J/N
	 */
	public Map<Language,Boolean> getAllBackground(final ClassAttributeAssignment item)
	{
		return getAllBackground( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setBackground(final SessionContext ctx, final ClassAttributeAssignment item, final Boolean value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedClassAttributeAssignment.setBackground requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, WeraConstants.Attributes.ClassAttributeAssignment.BACKGROUND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setBackground(final ClassAttributeAssignment item, final Boolean value)
	{
		setBackground( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setBackground(final SessionContext ctx, final ClassAttributeAssignment item, final boolean value)
	{
		setBackground( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setBackground(final ClassAttributeAssignment item, final boolean value)
	{
		setBackground( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setAllBackground(final SessionContext ctx, final ClassAttributeAssignment item, final Map<Language,Boolean> value)
	{
		item.setAllLocalizedProperties(ctx,WeraConstants.Attributes.ClassAttributeAssignment.BACKGROUND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.background</code> attribute. 
	 * @param value the background - Hintergrundfarbe J/N
	 */
	public void setAllBackground(final ClassAttributeAssignment item, final Map<Language,Boolean> value)
	{
		setAllBackground( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.category2introtext</code> attribute.
	 * @return the category2introtext
	 */
	public Collection<Textbaustein> getCategory2introtext(final SessionContext ctx, final Category item)
	{
		final List<Textbaustein> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.category2introtext</code> attribute.
	 * @return the category2introtext
	 */
	public Collection<Textbaustein> getCategory2introtext(final Category item)
	{
		return getCategory2introtext( getSession().getSessionContext(), item );
	}
	
	public long getCategory2introtextCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null
		);
	}
	
	public long getCategory2introtextCount(final Category item)
	{
		return getCategory2introtextCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.category2introtext</code> attribute. 
	 * @param value the category2introtext
	 */
	public void setCategory2introtext(final SessionContext ctx, final Category item, final Collection<Textbaustein> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.category2introtext</code> attribute. 
	 * @param value the category2introtext
	 */
	public void setCategory2introtext(final Category item, final Collection<Textbaustein> value)
	{
		setCategory2introtext( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2introtext. 
	 * @param value the item to add to category2introtext
	 */
	public void addToCategory2introtext(final SessionContext ctx, final Category item, final Textbaustein value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2introtext. 
	 * @param value the item to add to category2introtext
	 */
	public void addToCategory2introtext(final Category item, final Textbaustein value)
	{
		addToCategory2introtext( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2introtext. 
	 * @param value the item to remove from category2introtext
	 */
	public void removeFromCategory2introtext(final SessionContext ctx, final Category item, final Textbaustein value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYINTROTEXTRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2introtext. 
	 * @param value the item to remove from category2introtext
	 */
	public void removeFromCategory2introtext(final Category item, final Textbaustein value)
	{
		removeFromCategory2introtext( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.category2relcategories</code> attribute.
	 * @return the category2relcategories
	 */
	public Collection<Category> getCategory2relcategories(final SessionContext ctx, final Category item)
	{
		final List<Category> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.category2relcategories</code> attribute.
	 * @return the category2relcategories
	 */
	public Collection<Category> getCategory2relcategories(final Category item)
	{
		return getCategory2relcategories( getSession().getSessionContext(), item );
	}
	
	public long getCategory2relcategoriesCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null
		);
	}
	
	public long getCategory2relcategoriesCount(final Category item)
	{
		return getCategory2relcategoriesCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.category2relcategories</code> attribute. 
	 * @param value the category2relcategories
	 */
	public void setCategory2relcategories(final SessionContext ctx, final Category item, final Collection<Category> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.category2relcategories</code> attribute. 
	 * @param value the category2relcategories
	 */
	public void setCategory2relcategories(final Category item, final Collection<Category> value)
	{
		setCategory2relcategories( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2relcategories. 
	 * @param value the item to add to category2relcategories
	 */
	public void addToCategory2relcategories(final SessionContext ctx, final Category item, final Category value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2relcategories. 
	 * @param value the item to add to category2relcategories
	 */
	public void addToCategory2relcategories(final Category item, final Category value)
	{
		addToCategory2relcategories( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2relcategories. 
	 * @param value the item to remove from category2relcategories
	 */
	public void removeFromCategory2relcategories(final SessionContext ctx, final Category item, final Category value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2relcategories. 
	 * @param value the item to remove from category2relcategories
	 */
	public void removeFromCategory2relcategories(final Category item, final Category value)
	{
		removeFromCategory2relcategories( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.category2relcategoriesreverse</code> attribute.
	 * @return the category2relcategoriesreverse
	 */
	public Collection<Category> getCategory2relcategoriesreverse(final SessionContext ctx, final Category item)
	{
		final List<Category> items = item.getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.category2relcategoriesreverse</code> attribute.
	 * @return the category2relcategoriesreverse
	 */
	public Collection<Category> getCategory2relcategoriesreverse(final Category item)
	{
		return getCategory2relcategoriesreverse( getSession().getSessionContext(), item );
	}
	
	public long getCategory2relcategoriesreverseCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null
		);
	}
	
	public long getCategory2relcategoriesreverseCount(final Category item)
	{
		return getCategory2relcategoriesreverseCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.category2relcategoriesreverse</code> attribute. 
	 * @param value the category2relcategoriesreverse
	 */
	public void setCategory2relcategoriesreverse(final SessionContext ctx, final Category item, final Collection<Category> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.category2relcategoriesreverse</code> attribute. 
	 * @param value the category2relcategoriesreverse
	 */
	public void setCategory2relcategoriesreverse(final Category item, final Collection<Category> value)
	{
		setCategory2relcategoriesreverse( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2relcategoriesreverse. 
	 * @param value the item to add to category2relcategoriesreverse
	 */
	public void addToCategory2relcategoriesreverse(final SessionContext ctx, final Category item, final Category value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2relcategoriesreverse. 
	 * @param value the item to add to category2relcategoriesreverse
	 */
	public void addToCategory2relcategoriesreverse(final Category item, final Category value)
	{
		addToCategory2relcategoriesreverse( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2relcategoriesreverse. 
	 * @param value the item to remove from category2relcategoriesreverse
	 */
	public void removeFromCategory2relcategoriesreverse(final SessionContext ctx, final Category item, final Category value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDCATEGORIESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2relcategoriesreverse. 
	 * @param value the item to remove from category2relcategoriesreverse
	 */
	public void removeFromCategory2relcategoriesreverse(final Category item, final Category value)
	{
		removeFromCategory2relcategoriesreverse( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.category2relproducts</code> attribute.
	 * @return the category2relproducts
	 */
	public Collection<WeraProduct> getCategory2relproducts(final SessionContext ctx, final Category item)
	{
		final List<WeraProduct> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.category2relproducts</code> attribute.
	 * @return the category2relproducts
	 */
	public Collection<WeraProduct> getCategory2relproducts(final Category item)
	{
		return getCategory2relproducts( getSession().getSessionContext(), item );
	}
	
	public long getCategory2relproductsCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null
		);
	}
	
	public long getCategory2relproductsCount(final Category item)
	{
		return getCategory2relproductsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.category2relproducts</code> attribute. 
	 * @param value the category2relproducts
	 */
	public void setCategory2relproducts(final SessionContext ctx, final Category item, final Collection<WeraProduct> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.category2relproducts</code> attribute. 
	 * @param value the category2relproducts
	 */
	public void setCategory2relproducts(final Category item, final Collection<WeraProduct> value)
	{
		setCategory2relproducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2relproducts. 
	 * @param value the item to add to category2relproducts
	 */
	public void addToCategory2relproducts(final SessionContext ctx, final Category item, final WeraProduct value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to category2relproducts. 
	 * @param value the item to add to category2relproducts
	 */
	public void addToCategory2relproducts(final Category item, final WeraProduct value)
	{
		addToCategory2relproducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2relproducts. 
	 * @param value the item to remove from category2relproducts
	 */
	public void removeFromCategory2relproducts(final SessionContext ctx, final Category item, final WeraProduct value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from category2relproducts. 
	 * @param value the item to remove from category2relproducts
	 */
	public void removeFromCategory2relproducts(final Category item, final WeraProduct value)
	{
		removeFromCategory2relproducts( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.concat_description</code> attribute.
	 * @return the concat_description - Beschreibung und Untertitel zusammenfügen(WEB)
	 */
	public Boolean isConcat_description(final SessionContext ctx, final Category item)
	{
		return (Boolean)item.getProperty( ctx, WeraConstants.Attributes.Category.CONCAT_DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.concat_description</code> attribute.
	 * @return the concat_description - Beschreibung und Untertitel zusammenfügen(WEB)
	 */
	public Boolean isConcat_description(final Category item)
	{
		return isConcat_description( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.concat_description</code> attribute. 
	 * @return the concat_description - Beschreibung und Untertitel zusammenfügen(WEB)
	 */
	public boolean isConcat_descriptionAsPrimitive(final SessionContext ctx, final Category item)
	{
		Boolean value = isConcat_description( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.concat_description</code> attribute. 
	 * @return the concat_description - Beschreibung und Untertitel zusammenfügen(WEB)
	 */
	public boolean isConcat_descriptionAsPrimitive(final Category item)
	{
		return isConcat_descriptionAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.concat_description</code> attribute. 
	 * @param value the concat_description - Beschreibung und Untertitel zusammenfügen(WEB)
	 */
	public void setConcat_description(final SessionContext ctx, final Category item, final Boolean value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.Category.CONCAT_DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.concat_description</code> attribute. 
	 * @param value the concat_description - Beschreibung und Untertitel zusammenfügen(WEB)
	 */
	public void setConcat_description(final Category item, final Boolean value)
	{
		setConcat_description( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.concat_description</code> attribute. 
	 * @param value the concat_description - Beschreibung und Untertitel zusammenfügen(WEB)
	 */
	public void setConcat_description(final SessionContext ctx, final Category item, final boolean value)
	{
		setConcat_description( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.concat_description</code> attribute. 
	 * @param value the concat_description - Beschreibung und Untertitel zusammenfügen(WEB)
	 */
	public void setConcat_description(final Category item, final boolean value)
	{
		setConcat_description( getSession().getSessionContext(), item, value );
	}
	
	public AbtriebAnschluss createAbtriebAnschluss(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.ABTRIEBANSCHLUSS );
			return (AbtriebAnschluss)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AbtriebAnschluss : "+e.getMessage(), 0 );
		}
	}
	
	public AbtriebAnschluss createAbtriebAnschluss(final Map attributeValues)
	{
		return createAbtriebAnschluss( getSession().getSessionContext(), attributeValues );
	}
	
	public AbtriebSchraubprofil createAbtriebSchraubprofil(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.ABTRIEBSCHRAUBPROFIL );
			return (AbtriebSchraubprofil)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AbtriebSchraubprofil : "+e.getMessage(), 0 );
		}
	}
	
	public AbtriebSchraubprofil createAbtriebSchraubprofil(final Map attributeValues)
	{
		return createAbtriebSchraubprofil( getSession().getSessionContext(), attributeValues );
	}
	
	public AntriebAnschluss createAntriebAnschluss(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.ANTRIEBANSCHLUSS );
			return (AntriebAnschluss)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating AntriebAnschluss : "+e.getMessage(), 0 );
		}
	}
	
	public AntriebAnschluss createAntriebAnschluss(final Map attributeValues)
	{
		return createAntriebAnschluss( getSession().getSessionContext(), attributeValues );
	}
	
	public Bildreferenz createBildreferenz(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.BILDREFERENZ );
			return (Bildreferenz)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Bildreferenz : "+e.getMessage(), 0 );
		}
	}
	
	public Bildreferenz createBildreferenz(final Map attributeValues)
	{
		return createBildreferenz( getSession().getSessionContext(), attributeValues );
	}
	
	public Category2ProductExt createCategory2ProductExt(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.CATEGORY2PRODUCTEXT );
			return (Category2ProductExt)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Category2ProductExt : "+e.getMessage(), 0 );
		}
	}
	
	public Category2ProductExt createCategory2ProductExt(final Map attributeValues)
	{
		return createCategory2ProductExt( getSession().getSessionContext(), attributeValues );
	}
	
	public ExtImage createExtImage(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.EXTIMAGE );
			return (ExtImage)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating ExtImage : "+e.getMessage(), 0 );
		}
	}
	
	public ExtImage createExtImage(final Map attributeValues)
	{
		return createExtImage( getSession().getSessionContext(), attributeValues );
	}
	
	public Farbitem createFarbitem(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.FARBITEM );
			return (Farbitem)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Farbitem : "+e.getMessage(), 0 );
		}
	}
	
	public Farbitem createFarbitem(final Map attributeValues)
	{
		return createFarbitem( getSession().getSessionContext(), attributeValues );
	}
	
	public Footnote createFootnote(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.FOOTNOTE );
			return (Footnote)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Footnote : "+e.getMessage(), 0 );
		}
	}
	
	public Footnote createFootnote(final Map attributeValues)
	{
		return createFootnote( getSession().getSessionContext(), attributeValues );
	}
	
	public Measure createMeasure(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.MEASURE );
			return (Measure)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Measure : "+e.getMessage(), 0 );
		}
	}
	
	public Measure createMeasure(final Map attributeValues)
	{
		return createMeasure( getSession().getSessionContext(), attributeValues );
	}
	
	public Outputcontrol createOutputcontrol(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.OUTPUTCONTROL );
			return (Outputcontrol)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Outputcontrol : "+e.getMessage(), 0 );
		}
	}
	
	public Outputcontrol createOutputcontrol(final Map attributeValues)
	{
		return createOutputcontrol( getSession().getSessionContext(), attributeValues );
	}
	
	public Textbaustein createTextbaustein(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.TEXTBAUSTEIN );
			return (Textbaustein)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Textbaustein : "+e.getMessage(), 0 );
		}
	}
	
	public Textbaustein createTextbaustein(final Map attributeValues)
	{
		return createTextbaustein( getSession().getSessionContext(), attributeValues );
	}
	
	public Textitem createTextitem(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.TEXTITEM );
			return (Textitem)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Textitem : "+e.getMessage(), 0 );
		}
	}
	
	public Textitem createTextitem(final Map attributeValues)
	{
		return createTextitem( getSession().getSessionContext(), attributeValues );
	}
	
	public Tipp createTipp(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.TIPP );
			return (Tipp)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Tipp : "+e.getMessage(), 0 );
		}
	}
	
	public Tipp createTipp(final Map attributeValues)
	{
		return createTipp( getSession().getSessionContext(), attributeValues );
	}
	
	public Tips createTips(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.TIPS );
			return (Tips)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Tips : "+e.getMessage(), 0 );
		}
	}
	
	public Tips createTips(final Map attributeValues)
	{
		return createTips( getSession().getSessionContext(), attributeValues );
	}
	
	public Weblink createWeblink(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WEBLINK );
			return (Weblink)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating Weblink : "+e.getMessage(), 0 );
		}
	}
	
	public Weblink createWeblink(final Map attributeValues)
	{
		return createWeblink( getSession().getSessionContext(), attributeValues );
	}
	
	public WeblinkText createWeblinkText(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WEBLINKTEXT );
			return (WeblinkText)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeblinkText : "+e.getMessage(), 0 );
		}
	}
	
	public WeblinkText createWeblinkText(final Map attributeValues)
	{
		return createWeblinkText( getSession().getSessionContext(), attributeValues );
	}
	
	public WeraMedia createWeraMedia(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WERAMEDIA );
			return (WeraMedia)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeraMedia : "+e.getMessage(), 0 );
		}
	}
	
	public WeraMedia createWeraMedia(final Map attributeValues)
	{
		return createWeraMedia( getSession().getSessionContext(), attributeValues );
	}
	
	public WeraProduct createWeraProduct(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WERAPRODUCT );
			return (WeraProduct)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeraProduct : "+e.getMessage(), 0 );
		}
	}
	
	public WeraProduct createWeraProduct(final Map attributeValues)
	{
		return createWeraProduct( getSession().getSessionContext(), attributeValues );
	}
	
	public WeraProductSet createWeraProductSet(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WERAPRODUCTSET );
			return (WeraProductSet)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeraProductSet : "+e.getMessage(), 0 );
		}
	}
	
	public WeraProductSet createWeraProductSet(final Map attributeValues)
	{
		return createWeraProductSet( getSession().getSessionContext(), attributeValues );
	}
	
	public WeraProductSetinSet createWeraProductSetinSet(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WERAPRODUCTSETINSET );
			return (WeraProductSetinSet)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeraProductSetinSet : "+e.getMessage(), 0 );
		}
	}
	
	public WeraProductSetinSet createWeraProductSetinSet(final Map attributeValues)
	{
		return createWeraProductSetinSet( getSession().getSessionContext(), attributeValues );
	}
	
	public WeraProductSetVariants createWeraProductSetVariants(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WERAPRODUCTSETVARIANTS );
			return (WeraProductSetVariants)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeraProductSetVariants : "+e.getMessage(), 0 );
		}
	}
	
	public WeraProductSetVariants createWeraProductSetVariants(final Map attributeValues)
	{
		return createWeraProductSetVariants( getSession().getSessionContext(), attributeValues );
	}
	
	public WeraVariante createWeraVariante(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WERAVARIANTE );
			return (WeraVariante)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeraVariante : "+e.getMessage(), 0 );
		}
	}
	
	public WeraVariante createWeraVariante(final Map attributeValues)
	{
		return createWeraVariante( getSession().getSessionContext(), attributeValues );
	}
	
	public WeraVarianteSet createWeraVarianteSet(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WERAVARIANTESET );
			return (WeraVarianteSet)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeraVarianteSet : "+e.getMessage(), 0 );
		}
	}
	
	public WeraVarianteSet createWeraVarianteSet(final Map attributeValues)
	{
		return createWeraVarianteSet( getSession().getSessionContext(), attributeValues );
	}
	
	public WeraVarianteVariants createWeraVarianteVariants(final SessionContext ctx, final Map attributeValues)
	{
		try
		{
			ComposedType type = getTenant().getJaloConnection().getTypeManager().getComposedType( WeraConstants.TC.WERAVARIANTEVARIANTS );
			return (WeraVarianteVariants)type.newInstance( ctx, attributeValues );
		}
		catch( JaloGenericCreationException e)
		{
			final Throwable cause = e.getCause();
			throw (cause instanceof RuntimeException ?
			(RuntimeException)cause
			:
			new JaloSystemException( cause, cause.getMessage(), e.getErrorCode() ) );
		}
		catch( JaloBusinessException e )
		{
			throw new JaloSystemException( e ,"error creating WeraVarianteVariants : "+e.getMessage(), 0 );
		}
	}
	
	public WeraVarianteVariants createWeraVarianteVariants(final Map attributeValues)
	{
		return createWeraVarianteVariants( getSession().getSessionContext(), attributeValues );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.csv4upg</code> attribute.
	 * @return the csv4upg
	 */
	public Collection<CatalogVersion> getCsv4upg(final SessionContext ctx, final EnumerationValue item)
	{
		final List<CatalogVersion> items = item.getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.csv4upg</code> attribute.
	 * @return the csv4upg
	 */
	public Collection<CatalogVersion> getCsv4upg(final EnumerationValue item)
	{
		return getCsv4upg( getSession().getSessionContext(), item );
	}
	
	public long getCsv4upgCount(final SessionContext ctx, final EnumerationValue item)
	{
		return item.getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null
		);
	}
	
	public long getCsv4upgCount(final EnumerationValue item)
	{
		return getCsv4upgCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.csv4upg</code> attribute. 
	 * @param value the csv4upg
	 */
	public void setCsv4upg(final SessionContext ctx, final EnumerationValue item, final Collection<CatalogVersion> value)
	{
		item.setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.csv4upg</code> attribute. 
	 * @param value the csv4upg
	 */
	public void setCsv4upg(final EnumerationValue item, final Collection<CatalogVersion> value)
	{
		setCsv4upg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to csv4upg. 
	 * @param value the item to add to csv4upg
	 */
	public void addToCsv4upg(final SessionContext ctx, final EnumerationValue item, final CatalogVersion value)
	{
		item.addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to csv4upg. 
	 * @param value the item to add to csv4upg
	 */
	public void addToCsv4upg(final EnumerationValue item, final CatalogVersion value)
	{
		addToCsv4upg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from csv4upg. 
	 * @param value the item to remove from csv4upg
	 */
	public void removeFromCsv4upg(final SessionContext ctx, final EnumerationValue item, final CatalogVersion value)
	{
		item.removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from csv4upg. 
	 * @param value the item to remove from csv4upg
	 */
	public void removeFromCsv4upg(final EnumerationValue item, final CatalogVersion value)
	{
		removeFromCsv4upg( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.domain</code> attribute.
	 * @return the domain
	 */
	public String getDomain(final SessionContext ctx, final CatalogVersion item)
	{
		return (String)item.getProperty( ctx, WeraConstants.Attributes.CatalogVersion.DOMAIN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.domain</code> attribute.
	 * @return the domain
	 */
	public String getDomain(final CatalogVersion item)
	{
		return getDomain( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.domain</code> attribute. 
	 * @param value the domain
	 */
	public void setDomain(final SessionContext ctx, final CatalogVersion item, final String value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.CatalogVersion.DOMAIN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.domain</code> attribute. 
	 * @param value the domain
	 */
	public void setDomain(final CatalogVersion item, final String value)
	{
		setDomain( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.evk</code> attribute.
	 * @return the evk - Preis ist EVK
	 */
	public Boolean isEvk(final SessionContext ctx, final PriceRow item)
	{
		return (Boolean)item.getProperty( ctx, WeraConstants.Attributes.PriceRow.EVK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.evk</code> attribute.
	 * @return the evk - Preis ist EVK
	 */
	public Boolean isEvk(final PriceRow item)
	{
		return isEvk( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.evk</code> attribute. 
	 * @return the evk - Preis ist EVK
	 */
	public boolean isEvkAsPrimitive(final SessionContext ctx, final PriceRow item)
	{
		Boolean value = isEvk( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.evk</code> attribute. 
	 * @return the evk - Preis ist EVK
	 */
	public boolean isEvkAsPrimitive(final PriceRow item)
	{
		return isEvkAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.evk</code> attribute. 
	 * @param value the evk - Preis ist EVK
	 */
	public void setEvk(final SessionContext ctx, final PriceRow item, final Boolean value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.PriceRow.EVK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.evk</code> attribute. 
	 * @param value the evk - Preis ist EVK
	 */
	public void setEvk(final PriceRow item, final Boolean value)
	{
		setEvk( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.evk</code> attribute. 
	 * @param value the evk - Preis ist EVK
	 */
	public void setEvk(final SessionContext ctx, final PriceRow item, final boolean value)
	{
		setEvk( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.evk</code> attribute. 
	 * @param value the evk - Preis ist EVK
	 */
	public void setEvk(final PriceRow item, final boolean value)
	{
		setEvk( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttributeValue.farbitemspref</code> attribute.
	 * @return the farbitemspref
	 */
	public Collection<Farbitem> getFarbitemspref(final SessionContext ctx, final ClassificationAttributeValue item)
	{
		final List<Farbitem> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttributeValue.farbitemspref</code> attribute.
	 * @return the farbitemspref
	 */
	public Collection<Farbitem> getFarbitemspref(final ClassificationAttributeValue item)
	{
		return getFarbitemspref( getSession().getSessionContext(), item );
	}
	
	public long getFarbitemsprefCount(final SessionContext ctx, final ClassificationAttributeValue item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null
		);
	}
	
	public long getFarbitemsprefCount(final ClassificationAttributeValue item)
	{
		return getFarbitemsprefCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttributeValue.farbitemspref</code> attribute. 
	 * @param value the farbitemspref
	 */
	public void setFarbitemspref(final SessionContext ctx, final ClassificationAttributeValue item, final Collection<Farbitem> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttributeValue.farbitemspref</code> attribute. 
	 * @param value the farbitemspref
	 */
	public void setFarbitemspref(final ClassificationAttributeValue item, final Collection<Farbitem> value)
	{
		setFarbitemspref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to farbitemspref. 
	 * @param value the item to add to farbitemspref
	 */
	public void addToFarbitemspref(final SessionContext ctx, final ClassificationAttributeValue item, final Farbitem value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to farbitemspref. 
	 * @param value the item to add to farbitemspref
	 */
	public void addToFarbitemspref(final ClassificationAttributeValue item, final Farbitem value)
	{
		addToFarbitemspref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from farbitemspref. 
	 * @param value the item to remove from farbitemspref
	 */
	public void removeFromFarbitemspref(final SessionContext ctx, final ClassificationAttributeValue item, final Farbitem value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.FARBITEMCLASSIFICATIONATTRIBUTEVALUERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from farbitemspref. 
	 * @param value the item to remove from farbitemspref
	 */
	public void removeFromFarbitemspref(final ClassificationAttributeValue item, final Farbitem value)
	{
		removeFromFarbitemspref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductFeature.footnotes</code> attribute.
	 * @return the footnotes
	 */
	public Collection<Footnote> getFootnotes(final SessionContext ctx, final ProductFeature item)
	{
		return PRODUCTFEATUREFOOTNOTERELATIONFOOTNOTESHANDLER.getValues( ctx, item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ProductFeature.footnotes</code> attribute.
	 * @return the footnotes
	 */
	public Collection<Footnote> getFootnotes(final ProductFeature item)
	{
		return getFootnotes( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductFeature.footnotes</code> attribute. 
	 * @param value the footnotes
	 */
	public void setFootnotes(final SessionContext ctx, final ProductFeature item, final Collection<Footnote> value)
	{
		PRODUCTFEATUREFOOTNOTERELATIONFOOTNOTESHANDLER.setValues( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ProductFeature.footnotes</code> attribute. 
	 * @param value the footnotes
	 */
	public void setFootnotes(final ProductFeature item, final Collection<Footnote> value)
	{
		setFootnotes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to footnotes. 
	 * @param value the item to add to footnotes
	 */
	public void addToFootnotes(final SessionContext ctx, final ProductFeature item, final Footnote value)
	{
		PRODUCTFEATUREFOOTNOTERELATIONFOOTNOTESHANDLER.addValue( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to footnotes. 
	 * @param value the item to add to footnotes
	 */
	public void addToFootnotes(final ProductFeature item, final Footnote value)
	{
		addToFootnotes( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from footnotes. 
	 * @param value the item to remove from footnotes
	 */
	public void removeFromFootnotes(final SessionContext ctx, final ProductFeature item, final Footnote value)
	{
		PRODUCTFEATUREFOOTNOTERELATIONFOOTNOTESHANDLER.removeValue( ctx, item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from footnotes. 
	 * @param value the item to remove from footnotes
	 */
	public void removeFromFootnotes(final ProductFeature item, final Footnote value)
	{
		removeFromFootnotes( getSession().getSessionContext(), item, value );
	}
	
	@Override
	public String getName()
	{
		return WeraConstants.EXTENSIONNAME;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.haupttippref</code> attribute.
	 * @return the haupttippref
	 */
	public Collection<Tipp> getHaupttippref(final SessionContext ctx, final Category item)
	{
		final List<Tipp> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.haupttippref</code> attribute.
	 * @return the haupttippref
	 */
	public Collection<Tipp> getHaupttippref(final Category item)
	{
		return getHaupttippref( getSession().getSessionContext(), item );
	}
	
	public long getHaupttipprefCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null
		);
	}
	
	public long getHaupttipprefCount(final Category item)
	{
		return getHaupttipprefCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.haupttippref</code> attribute. 
	 * @param value the haupttippref
	 */
	public void setHaupttippref(final SessionContext ctx, final Category item, final Collection<Tipp> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.haupttippref</code> attribute. 
	 * @param value the haupttippref
	 */
	public void setHaupttippref(final Category item, final Collection<Tipp> value)
	{
		setHaupttippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to haupttippref. 
	 * @param value the item to add to haupttippref
	 */
	public void addToHaupttippref(final SessionContext ctx, final Category item, final Tipp value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to haupttippref. 
	 * @param value the item to add to haupttippref
	 */
	public void addToHaupttippref(final Category item, final Tipp value)
	{
		addToHaupttippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from haupttippref. 
	 * @param value the item to remove from haupttippref
	 */
	public void removeFromHaupttippref(final SessionContext ctx, final Category item, final Tipp value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYHAUPTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from haupttippref. 
	 * @param value the item to remove from haupttippref
	 */
	public void removeFromHaupttippref(final Category item, final Tipp value)
	{
		removeFromHaupttippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.icons</code> attribute.
	 * @return the icons
	 */
	public Collection<WeraMedia> getIcons(final SessionContext ctx, final Category item)
	{
		final List<WeraMedia> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.icons</code> attribute.
	 * @return the icons
	 */
	public Collection<WeraMedia> getIcons(final Category item)
	{
		return getIcons( getSession().getSessionContext(), item );
	}
	
	public long getIconsCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null
		);
	}
	
	public long getIconsCount(final Category item)
	{
		return getIconsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.icons</code> attribute. 
	 * @param value the icons
	 */
	public void setIcons(final SessionContext ctx, final Category item, final Collection<WeraMedia> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.icons</code> attribute. 
	 * @param value the icons
	 */
	public void setIcons(final Category item, final Collection<WeraMedia> value)
	{
		setIcons( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icons. 
	 * @param value the item to add to icons
	 */
	public void addToIcons(final SessionContext ctx, final Category item, final WeraMedia value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icons. 
	 * @param value the item to add to icons
	 */
	public void addToIcons(final Category item, final WeraMedia value)
	{
		addToIcons( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icons. 
	 * @param value the item to remove from icons
	 */
	public void removeFromIcons(final SessionContext ctx, final Category item, final WeraMedia value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYICONRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icons. 
	 * @param value the item to remove from icons
	 */
	public void removeFromIcons(final Category item, final WeraMedia value)
	{
		removeFromIcons( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.is_defining_feature</code> attribute.
	 * @return the is_defining_feature - Ist ein echtes Feature und keine Gattung
	 */
	public Boolean isIs_defining_feature(final SessionContext ctx, final ClassificationAttribute item)
	{
		return (Boolean)item.getProperty( ctx, WeraConstants.Attributes.ClassificationAttribute.IS_DEFINING_FEATURE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.is_defining_feature</code> attribute.
	 * @return the is_defining_feature - Ist ein echtes Feature und keine Gattung
	 */
	public Boolean isIs_defining_feature(final ClassificationAttribute item)
	{
		return isIs_defining_feature( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.is_defining_feature</code> attribute. 
	 * @return the is_defining_feature - Ist ein echtes Feature und keine Gattung
	 */
	public boolean isIs_defining_featureAsPrimitive(final SessionContext ctx, final ClassificationAttribute item)
	{
		Boolean value = isIs_defining_feature( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.is_defining_feature</code> attribute. 
	 * @return the is_defining_feature - Ist ein echtes Feature und keine Gattung
	 */
	public boolean isIs_defining_featureAsPrimitive(final ClassificationAttribute item)
	{
		return isIs_defining_featureAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.is_defining_feature</code> attribute. 
	 * @param value the is_defining_feature - Ist ein echtes Feature und keine Gattung
	 */
	public void setIs_defining_feature(final SessionContext ctx, final ClassificationAttribute item, final Boolean value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.ClassificationAttribute.IS_DEFINING_FEATURE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.is_defining_feature</code> attribute. 
	 * @param value the is_defining_feature - Ist ein echtes Feature und keine Gattung
	 */
	public void setIs_defining_feature(final ClassificationAttribute item, final Boolean value)
	{
		setIs_defining_feature( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.is_defining_feature</code> attribute. 
	 * @param value the is_defining_feature - Ist ein echtes Feature und keine Gattung
	 */
	public void setIs_defining_feature(final SessionContext ctx, final ClassificationAttribute item, final boolean value)
	{
		setIs_defining_feature( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.is_defining_feature</code> attribute. 
	 * @param value the is_defining_feature - Ist ein echtes Feature und keine Gattung
	 */
	public void setIs_defining_feature(final ClassificationAttribute item, final boolean value)
	{
		setIs_defining_feature( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.is_webcatalog</code> attribute.
	 * @return the is_webcatalog - Webkatalog
	 */
	public Boolean isIs_webcatalog(final SessionContext ctx, final CatalogVersion item)
	{
		return (Boolean)item.getProperty( ctx, WeraConstants.Attributes.CatalogVersion.IS_WEBCATALOG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.is_webcatalog</code> attribute.
	 * @return the is_webcatalog - Webkatalog
	 */
	public Boolean isIs_webcatalog(final CatalogVersion item)
	{
		return isIs_webcatalog( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.is_webcatalog</code> attribute. 
	 * @return the is_webcatalog - Webkatalog
	 */
	public boolean isIs_webcatalogAsPrimitive(final SessionContext ctx, final CatalogVersion item)
	{
		Boolean value = isIs_webcatalog( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.is_webcatalog</code> attribute. 
	 * @return the is_webcatalog - Webkatalog
	 */
	public boolean isIs_webcatalogAsPrimitive(final CatalogVersion item)
	{
		return isIs_webcatalogAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.is_webcatalog</code> attribute. 
	 * @param value the is_webcatalog - Webkatalog
	 */
	public void setIs_webcatalog(final SessionContext ctx, final CatalogVersion item, final Boolean value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.CatalogVersion.IS_WEBCATALOG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.is_webcatalog</code> attribute. 
	 * @param value the is_webcatalog - Webkatalog
	 */
	public void setIs_webcatalog(final CatalogVersion item, final Boolean value)
	{
		setIs_webcatalog( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.is_webcatalog</code> attribute. 
	 * @param value the is_webcatalog - Webkatalog
	 */
	public void setIs_webcatalog(final SessionContext ctx, final CatalogVersion item, final boolean value)
	{
		setIs_webcatalog( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.is_webcatalog</code> attribute. 
	 * @param value the is_webcatalog - Webkatalog
	 */
	public void setIs_webcatalog(final CatalogVersion item, final boolean value)
	{
		setIs_webcatalog( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.isbrutto</code> attribute.
	 * @return the isbrutto - Nur EVP Preisliste J/N
	 */
	public Boolean isIsbrutto(final SessionContext ctx, final EnumerationValue item)
	{
		return (Boolean)item.getProperty( ctx, WeraConstants.Attributes.UserPriceGroup.ISBRUTTO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.isbrutto</code> attribute.
	 * @return the isbrutto - Nur EVP Preisliste J/N
	 */
	public Boolean isIsbrutto(final EnumerationValue item)
	{
		return isIsbrutto( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.isbrutto</code> attribute. 
	 * @return the isbrutto - Nur EVP Preisliste J/N
	 */
	public boolean isIsbruttoAsPrimitive(final SessionContext ctx, final EnumerationValue item)
	{
		Boolean value = isIsbrutto( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.isbrutto</code> attribute. 
	 * @return the isbrutto - Nur EVP Preisliste J/N
	 */
	public boolean isIsbruttoAsPrimitive(final EnumerationValue item)
	{
		return isIsbruttoAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.isbrutto</code> attribute. 
	 * @param value the isbrutto - Nur EVP Preisliste J/N
	 */
	public void setIsbrutto(final SessionContext ctx, final EnumerationValue item, final Boolean value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.UserPriceGroup.ISBRUTTO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.isbrutto</code> attribute. 
	 * @param value the isbrutto - Nur EVP Preisliste J/N
	 */
	public void setIsbrutto(final EnumerationValue item, final Boolean value)
	{
		setIsbrutto( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.isbrutto</code> attribute. 
	 * @param value the isbrutto - Nur EVP Preisliste J/N
	 */
	public void setIsbrutto(final SessionContext ctx, final EnumerationValue item, final boolean value)
	{
		setIsbrutto( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.isbrutto</code> attribute. 
	 * @param value the isbrutto - Nur EVP Preisliste J/N
	 */
	public void setIsbrutto(final EnumerationValue item, final boolean value)
	{
		setIsbrutto( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.masterpk</code> attribute.
	 * @return the masterpk
	 */
	public String getMasterpk(final SessionContext ctx, final Category item)
	{
		return (String)item.getProperty( ctx, WeraConstants.Attributes.Category.MASTERPK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.masterpk</code> attribute.
	 * @return the masterpk
	 */
	public String getMasterpk(final Category item)
	{
		return getMasterpk( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.masterpk</code> attribute. 
	 * @param value the masterpk
	 */
	public void setMasterpk(final SessionContext ctx, final Category item, final String value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.Category.MASTERPK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.masterpk</code> attribute. 
	 * @param value the masterpk
	 */
	public void setMasterpk(final Category item, final String value)
	{
		setMasterpk( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.max_preiszeilen</code> attribute.
	 * @return the max_preiszeilen - Max. Anzahl Preiszeilen
	 */
	public Integer getMax_preiszeilen(final SessionContext ctx, final EnumerationValue item)
	{
		return (Integer)item.getProperty( ctx, WeraConstants.Attributes.UserPriceGroup.MAX_PREISZEILEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.max_preiszeilen</code> attribute.
	 * @return the max_preiszeilen - Max. Anzahl Preiszeilen
	 */
	public Integer getMax_preiszeilen(final EnumerationValue item)
	{
		return getMax_preiszeilen( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.max_preiszeilen</code> attribute. 
	 * @return the max_preiszeilen - Max. Anzahl Preiszeilen
	 */
	public int getMax_preiszeilenAsPrimitive(final SessionContext ctx, final EnumerationValue item)
	{
		Integer value = getMax_preiszeilen( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.max_preiszeilen</code> attribute. 
	 * @return the max_preiszeilen - Max. Anzahl Preiszeilen
	 */
	public int getMax_preiszeilenAsPrimitive(final EnumerationValue item)
	{
		return getMax_preiszeilenAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.max_preiszeilen</code> attribute. 
	 * @param value the max_preiszeilen - Max. Anzahl Preiszeilen
	 */
	public void setMax_preiszeilen(final SessionContext ctx, final EnumerationValue item, final Integer value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.UserPriceGroup.MAX_PREISZEILEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.max_preiszeilen</code> attribute. 
	 * @param value the max_preiszeilen - Max. Anzahl Preiszeilen
	 */
	public void setMax_preiszeilen(final EnumerationValue item, final Integer value)
	{
		setMax_preiszeilen( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.max_preiszeilen</code> attribute. 
	 * @param value the max_preiszeilen - Max. Anzahl Preiszeilen
	 */
	public void setMax_preiszeilen(final SessionContext ctx, final EnumerationValue item, final int value)
	{
		setMax_preiszeilen( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.max_preiszeilen</code> attribute. 
	 * @param value the max_preiszeilen - Max. Anzahl Preiszeilen
	 */
	public void setMax_preiszeilen(final EnumerationValue item, final int value)
	{
		setMax_preiszeilen( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Keyword.medias</code> attribute.
	 * @return the medias
	 */
	public Collection<WeraMedia> getMedias(final SessionContext ctx, final Keyword item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedKeyword.getMedias requires a session language", 0 );
		}
		final List<WeraMedia> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			ctx.getLanguage(),
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Keyword.medias</code> attribute.
	 * @return the medias
	 */
	public Collection<WeraMedia> getMedias(final Keyword item)
	{
		return getMedias( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Keyword.medias</code> attribute. 
	 * @return the localized medias
	 */
	public Map<Language,Collection<WeraMedia>> getAllMedias(final SessionContext ctx, final Keyword item)
	{
		Map<Language,Collection<WeraMedia>> values = item.getAllLinkedItems( 
			true,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION
		);
		return values;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Keyword.medias</code> attribute. 
	 * @return the localized medias
	 */
	public Map<Language,Collection<WeraMedia>> getAllMedias(final Keyword item)
	{
		return getAllMedias( getSession().getSessionContext(), item );
	}
	
	public long getMediasCount(final SessionContext ctx, final Keyword item, final Language lang)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			lang
		);
	}
	
	public long getMediasCount(final Keyword item, final Language lang)
	{
		return getMediasCount( getSession().getSessionContext(), item,lang );
	}
	
	public long getMediasCount(final SessionContext ctx, final Keyword item)
	{
		return getMediasCount( ctx, item, ctx.getLanguage() );
	}
	
	public long getMediasCount(final Keyword item)
	{
		return getMediasCount( getSession().getSessionContext(), item, getSession().getSessionContext().getLanguage() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Keyword.medias</code> attribute. 
	 * @param value the medias
	 */
	public void setMedias(final SessionContext ctx, final Keyword item, final Collection<WeraMedia> value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedKeyword.setMedias requires a session language", 0 );
		}
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			ctx.getLanguage(),
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Keyword.medias</code> attribute. 
	 * @param value the medias
	 */
	public void setMedias(final Keyword item, final Collection<WeraMedia> value)
	{
		setMedias( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Keyword.medias</code> attribute. 
	 * @param value the medias
	 */
	public void setAllMedias(final SessionContext ctx, final Keyword item, final Map<Language,Collection<WeraMedia>> value)
	{
		item.setAllLinkedItems( 
			getAllValuesSessionContext(ctx),
			true,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			value
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Keyword.medias</code> attribute. 
	 * @param value the medias
	 */
	public void setAllMedias(final Keyword item, final Map<Language,Collection<WeraMedia>> value)
	{
		setAllMedias( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to medias. 
	 * @param value the item to add to medias
	 */
	public void addToMedias(final SessionContext ctx, final Keyword item, final Language lang, final WeraMedia value)
	{
		if( lang == null )
		{
			throw new JaloInvalidParameterException("GeneratedKeyword.addToMedias requires a language language", 0 );
		}
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			lang,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to medias. 
	 * @param value the item to add to medias
	 */
	public void addToMedias(final Keyword item, final Language lang, final WeraMedia value)
	{
		addToMedias( getSession().getSessionContext(), item, lang, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from medias. 
	 * @param value the item to remove from medias
	 */
	public void removeFromMedias(final SessionContext ctx, final Keyword item, final Language lang, final WeraMedia value)
	{
		if( lang == null )
		{
			throw new JaloInvalidParameterException("GeneratedKeyword.removeFromMedias requires a session language", 0 );
		}
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAMEDIA2KEYWORDRELATION,
			lang,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from medias. 
	 * @param value the item to remove from medias
	 */
	public void removeFromMedias(final Keyword item, final Language lang, final WeraMedia value)
	{
		removeFromMedias( getSession().getSessionContext(), item, lang, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.meta_description</code> attribute.
	 * @return the meta_description
	 */
	public String getMeta_description(final SessionContext ctx, final Category item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.getMeta_description requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, WeraConstants.Attributes.Category.META_DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.meta_description</code> attribute.
	 * @return the meta_description
	 */
	public String getMeta_description(final Category item)
	{
		return getMeta_description( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.meta_description</code> attribute. 
	 * @return the localized meta_description
	 */
	public Map<Language,String> getAllMeta_description(final SessionContext ctx, final Category item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.META_DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.meta_description</code> attribute. 
	 * @return the localized meta_description
	 */
	public Map<Language,String> getAllMeta_description(final Category item)
	{
		return getAllMeta_description( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.meta_description</code> attribute. 
	 * @param value the meta_description
	 */
	public void setMeta_description(final SessionContext ctx, final Category item, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.setMeta_description requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, WeraConstants.Attributes.Category.META_DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.meta_description</code> attribute. 
	 * @param value the meta_description
	 */
	public void setMeta_description(final Category item, final String value)
	{
		setMeta_description( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.meta_description</code> attribute. 
	 * @param value the meta_description
	 */
	public void setAllMeta_description(final SessionContext ctx, final Category item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.META_DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.meta_description</code> attribute. 
	 * @param value the meta_description
	 */
	public void setAllMeta_description(final Category item, final Map<Language,String> value)
	{
		setAllMeta_description( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.meta_keywords</code> attribute.
	 * @return the meta_keywords
	 */
	public String getMeta_keywords(final SessionContext ctx, final Category item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.getMeta_keywords requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, WeraConstants.Attributes.Category.META_KEYWORDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.meta_keywords</code> attribute.
	 * @return the meta_keywords
	 */
	public String getMeta_keywords(final Category item)
	{
		return getMeta_keywords( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.meta_keywords</code> attribute. 
	 * @return the localized meta_keywords
	 */
	public Map<Language,String> getAllMeta_keywords(final SessionContext ctx, final Category item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.META_KEYWORDS,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.meta_keywords</code> attribute. 
	 * @return the localized meta_keywords
	 */
	public Map<Language,String> getAllMeta_keywords(final Category item)
	{
		return getAllMeta_keywords( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.meta_keywords</code> attribute. 
	 * @param value the meta_keywords
	 */
	public void setMeta_keywords(final SessionContext ctx, final Category item, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.setMeta_keywords requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, WeraConstants.Attributes.Category.META_KEYWORDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.meta_keywords</code> attribute. 
	 * @param value the meta_keywords
	 */
	public void setMeta_keywords(final Category item, final String value)
	{
		setMeta_keywords( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.meta_keywords</code> attribute. 
	 * @param value the meta_keywords
	 */
	public void setAllMeta_keywords(final SessionContext ctx, final Category item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.META_KEYWORDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.meta_keywords</code> attribute. 
	 * @param value the meta_keywords
	 */
	public void setAllMeta_keywords(final Category item, final Map<Language,String> value)
	{
		setAllMeta_keywords( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.order</code> attribute.
	 * @return the order - Reihenfolge
	 */
	public Integer getOrder(final SessionContext ctx, final PriceRow item)
	{
		return (Integer)item.getProperty( ctx, WeraConstants.Attributes.PriceRow.ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.order</code> attribute.
	 * @return the order - Reihenfolge
	 */
	public Integer getOrder(final PriceRow item)
	{
		return getOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.order</code> attribute. 
	 * @return the order - Reihenfolge
	 */
	public int getOrderAsPrimitive(final SessionContext ctx, final PriceRow item)
	{
		Integer value = getOrder( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>PriceRow.order</code> attribute. 
	 * @return the order - Reihenfolge
	 */
	public int getOrderAsPrimitive(final PriceRow item)
	{
		return getOrderAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final SessionContext ctx, final PriceRow item, final Integer value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.PriceRow.ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final PriceRow item, final Integer value)
	{
		setOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final SessionContext ctx, final PriceRow item, final int value)
	{
		setOrder( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>PriceRow.order</code> attribute. 
	 * @param value the order - Reihenfolge
	 */
	public void setOrder(final PriceRow item, final int value)
	{
		setOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.order</code> attribute.
	 * @return the order - Reihenfolge Merkmal
	 */
	public Integer getOrder(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		return (Integer)item.getProperty( ctx, WeraConstants.Attributes.ClassAttributeAssignment.ORDER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.order</code> attribute.
	 * @return the order - Reihenfolge Merkmal
	 */
	public Integer getOrder(final ClassAttributeAssignment item)
	{
		return getOrder( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.order</code> attribute. 
	 * @return the order - Reihenfolge Merkmal
	 */
	public int getOrderAsPrimitive(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		Integer value = getOrder( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.order</code> attribute. 
	 * @return the order - Reihenfolge Merkmal
	 */
	public int getOrderAsPrimitive(final ClassAttributeAssignment item)
	{
		return getOrderAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.order</code> attribute. 
	 * @param value the order - Reihenfolge Merkmal
	 */
	public void setOrder(final SessionContext ctx, final ClassAttributeAssignment item, final Integer value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.ClassAttributeAssignment.ORDER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.order</code> attribute. 
	 * @param value the order - Reihenfolge Merkmal
	 */
	public void setOrder(final ClassAttributeAssignment item, final Integer value)
	{
		setOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.order</code> attribute. 
	 * @param value the order - Reihenfolge Merkmal
	 */
	public void setOrder(final SessionContext ctx, final ClassAttributeAssignment item, final int value)
	{
		setOrder( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.order</code> attribute. 
	 * @param value the order - Reihenfolge Merkmal
	 */
	public void setOrder(final ClassAttributeAssignment item, final int value)
	{
		setOrder( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.preise_mit_komma</code> attribute.
	 * @return the preise_mit_komma - Verwende Preise mit Komma J/N
	 */
	public Boolean isPreise_mit_komma(final SessionContext ctx, final EnumerationValue item)
	{
		return (Boolean)item.getProperty( ctx, WeraConstants.Attributes.UserPriceGroup.PREISE_MIT_KOMMA);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.preise_mit_komma</code> attribute.
	 * @return the preise_mit_komma - Verwende Preise mit Komma J/N
	 */
	public Boolean isPreise_mit_komma(final EnumerationValue item)
	{
		return isPreise_mit_komma( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.preise_mit_komma</code> attribute. 
	 * @return the preise_mit_komma - Verwende Preise mit Komma J/N
	 */
	public boolean isPreise_mit_kommaAsPrimitive(final SessionContext ctx, final EnumerationValue item)
	{
		Boolean value = isPreise_mit_komma( ctx,item );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>UserPriceGroup.preise_mit_komma</code> attribute. 
	 * @return the preise_mit_komma - Verwende Preise mit Komma J/N
	 */
	public boolean isPreise_mit_kommaAsPrimitive(final EnumerationValue item)
	{
		return isPreise_mit_kommaAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.preise_mit_komma</code> attribute. 
	 * @param value the preise_mit_komma - Verwende Preise mit Komma J/N
	 */
	public void setPreise_mit_komma(final SessionContext ctx, final EnumerationValue item, final Boolean value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.UserPriceGroup.PREISE_MIT_KOMMA,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.preise_mit_komma</code> attribute. 
	 * @param value the preise_mit_komma - Verwende Preise mit Komma J/N
	 */
	public void setPreise_mit_komma(final EnumerationValue item, final Boolean value)
	{
		setPreise_mit_komma( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.preise_mit_komma</code> attribute. 
	 * @param value the preise_mit_komma - Verwende Preise mit Komma J/N
	 */
	public void setPreise_mit_komma(final SessionContext ctx, final EnumerationValue item, final boolean value)
	{
		setPreise_mit_komma( ctx, item, Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>UserPriceGroup.preise_mit_komma</code> attribute. 
	 * @param value the preise_mit_komma - Verwende Preise mit Komma J/N
	 */
	public void setPreise_mit_komma(final EnumerationValue item, final boolean value)
	{
		setPreise_mit_komma( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.priorityWebSearch</code> attribute.
	 * @return the priorityWebSearch - Such-Priorität Web
	 */
	public Integer getPriorityWebSearch(final SessionContext ctx, final Category item)
	{
		return (Integer)item.getProperty( ctx, WeraConstants.Attributes.Category.PRIORITYWEBSEARCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.priorityWebSearch</code> attribute.
	 * @return the priorityWebSearch - Such-Priorität Web
	 */
	public Integer getPriorityWebSearch(final Category item)
	{
		return getPriorityWebSearch( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.priorityWebSearch</code> attribute. 
	 * @return the priorityWebSearch - Such-Priorität Web
	 */
	public int getPriorityWebSearchAsPrimitive(final SessionContext ctx, final Category item)
	{
		Integer value = getPriorityWebSearch( ctx,item );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.priorityWebSearch</code> attribute. 
	 * @return the priorityWebSearch - Such-Priorität Web
	 */
	public int getPriorityWebSearchAsPrimitive(final Category item)
	{
		return getPriorityWebSearchAsPrimitive( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.priorityWebSearch</code> attribute. 
	 * @param value the priorityWebSearch - Such-Priorität Web
	 */
	public void setPriorityWebSearch(final SessionContext ctx, final Category item, final Integer value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.Category.PRIORITYWEBSEARCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.priorityWebSearch</code> attribute. 
	 * @param value the priorityWebSearch - Such-Priorität Web
	 */
	public void setPriorityWebSearch(final Category item, final Integer value)
	{
		setPriorityWebSearch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.priorityWebSearch</code> attribute. 
	 * @param value the priorityWebSearch - Such-Priorität Web
	 */
	public void setPriorityWebSearch(final SessionContext ctx, final Category item, final int value)
	{
		setPriorityWebSearch( ctx, item, Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.priorityWebSearch</code> attribute. 
	 * @param value the priorityWebSearch - Such-Priorität Web
	 */
	public void setPriorityWebSearch(final Category item, final int value)
	{
		setPriorityWebSearch( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.tippref</code> attribute.
	 * @return the tippref
	 */
	public Collection<Tipp> getTippref(final SessionContext ctx, final Category item)
	{
		final List<Tipp> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.tippref</code> attribute.
	 * @return the tippref
	 */
	public Collection<Tipp> getTippref(final Category item)
	{
		return getTippref( getSession().getSessionContext(), item );
	}
	
	public long getTipprefCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
			null
		);
	}
	
	public long getTipprefCount(final Category item)
	{
		return getTipprefCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.tippref</code> attribute. 
	 * @param value the tippref
	 */
	public void setTippref(final SessionContext ctx, final Category item, final Collection<Tipp> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.tippref</code> attribute. 
	 * @param value the tippref
	 */
	public void setTippref(final Category item, final Collection<Tipp> value)
	{
		setTippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to tippref. 
	 * @param value the item to add to tippref
	 */
	public void addToTippref(final SessionContext ctx, final Category item, final Tipp value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
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
	public void addToTippref(final Category item, final Tipp value)
	{
		addToTippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from tippref. 
	 * @param value the item to remove from tippref
	 */
	public void removeFromTippref(final SessionContext ctx, final Category item, final Tipp value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYTIPPRELATION,
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
	public void removeFromTippref(final Category item, final Tipp value)
	{
		removeFromTippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.tippref</code> attribute.
	 * @return the tippref
	 */
	public Collection<Tipp> getTippref(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		final List<Tipp> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassAttributeAssignment.tippref</code> attribute.
	 * @return the tippref
	 */
	public Collection<Tipp> getTippref(final ClassAttributeAssignment item)
	{
		return getTippref( getSession().getSessionContext(), item );
	}
	
	public long getTipprefCount(final SessionContext ctx, final ClassAttributeAssignment item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
			null
		);
	}
	
	public long getTipprefCount(final ClassAttributeAssignment item)
	{
		return getTipprefCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.tippref</code> attribute. 
	 * @param value the tippref
	 */
	public void setTippref(final SessionContext ctx, final ClassAttributeAssignment item, final Collection<Tipp> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassAttributeAssignment.tippref</code> attribute. 
	 * @param value the tippref
	 */
	public void setTippref(final ClassAttributeAssignment item, final Collection<Tipp> value)
	{
		setTippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to tippref. 
	 * @param value the item to add to tippref
	 */
	public void addToTippref(final SessionContext ctx, final ClassAttributeAssignment item, final Tipp value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
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
	public void addToTippref(final ClassAttributeAssignment item, final Tipp value)
	{
		addToTippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from tippref. 
	 * @param value the item to remove from tippref
	 */
	public void removeFromTippref(final SessionContext ctx, final ClassAttributeAssignment item, final Tipp value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CLASSATTRIBUTEASSIGNMENT2TIPPRELATION,
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
	public void removeFromTippref(final ClassAttributeAssignment item, final Tipp value)
	{
		removeFromTippref( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.untertitel</code> attribute.
	 * @return the untertitel
	 */
	public String getUntertitel(final SessionContext ctx, final Category item)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.getUntertitel requires a session language", 0 );
		}
		return (String)item.getLocalizedProperty( ctx, WeraConstants.Attributes.Category.UNTERTITEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.untertitel</code> attribute.
	 * @return the untertitel
	 */
	public String getUntertitel(final Category item)
	{
		return getUntertitel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.untertitel</code> attribute. 
	 * @return the localized untertitel
	 */
	public Map<Language,String> getAllUntertitel(final SessionContext ctx, final Category item)
	{
		return (Map<Language,String>)item.getAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.UNTERTITEL,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.untertitel</code> attribute. 
	 * @return the localized untertitel
	 */
	public Map<Language,String> getAllUntertitel(final Category item)
	{
		return getAllUntertitel( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.untertitel</code> attribute. 
	 * @param value the untertitel
	 */
	public void setUntertitel(final SessionContext ctx, final Category item, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedCategory.setUntertitel requires a session language", 0 );
		}
		item.setLocalizedProperty(ctx, WeraConstants.Attributes.Category.UNTERTITEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.untertitel</code> attribute. 
	 * @param value the untertitel
	 */
	public void setUntertitel(final Category item, final String value)
	{
		setUntertitel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.untertitel</code> attribute. 
	 * @param value the untertitel
	 */
	public void setAllUntertitel(final SessionContext ctx, final Category item, final Map<Language,String> value)
	{
		item.setAllLocalizedProperties(ctx,WeraConstants.Attributes.Category.UNTERTITEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.untertitel</code> attribute. 
	 * @param value the untertitel
	 */
	public void setAllUntertitel(final Category item, final Map<Language,String> value)
	{
		setAllUntertitel( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.upg4csv</code> attribute.
	 * @return the upg4csv
	 */
	public Collection<EnumerationValue> getUpg4csv(final SessionContext ctx, final CatalogVersion item)
	{
		final List<EnumerationValue> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.upg4csv</code> attribute.
	 * @return the upg4csv
	 */
	public Collection<EnumerationValue> getUpg4csv(final CatalogVersion item)
	{
		return getUpg4csv( getSession().getSessionContext(), item );
	}
	
	public long getUpg4csvCount(final SessionContext ctx, final CatalogVersion item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null
		);
	}
	
	public long getUpg4csvCount(final CatalogVersion item)
	{
		return getUpg4csvCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.upg4csv</code> attribute. 
	 * @param value the upg4csv
	 */
	public void setUpg4csv(final SessionContext ctx, final CatalogVersion item, final Collection<EnumerationValue> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.upg4csv</code> attribute. 
	 * @param value the upg4csv
	 */
	public void setUpg4csv(final CatalogVersion item, final Collection<EnumerationValue> value)
	{
		setUpg4csv( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to upg4csv. 
	 * @param value the item to add to upg4csv
	 */
	public void addToUpg4csv(final SessionContext ctx, final CatalogVersion item, final EnumerationValue value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to upg4csv. 
	 * @param value the item to add to upg4csv
	 */
	public void addToUpg4csv(final CatalogVersion item, final EnumerationValue value)
	{
		addToUpg4csv( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from upg4csv. 
	 * @param value the item to remove from upg4csv
	 */
	public void removeFromUpg4csv(final SessionContext ctx, final CatalogVersion item, final EnumerationValue value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATALOGVERSIONUSERPRICEGROUPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from upg4csv. 
	 * @param value the item to remove from upg4csv
	 */
	public void removeFromUpg4csv(final CatalogVersion item, final EnumerationValue value)
	{
		removeFromUpg4csv( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from(final SessionContext ctx, final Category item)
	{
		return (Date)item.getProperty( ctx, WeraConstants.Attributes.Category.VALID_FROM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from(final Category item)
	{
		return getValid_from( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final SessionContext ctx, final Category item, final Date value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.Category.VALID_FROM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final Category item, final Date value)
	{
		setValid_from( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from(final SessionContext ctx, final CatalogVersion item)
	{
		return (Date)item.getProperty( ctx, WeraConstants.Attributes.CatalogVersion.VALID_FROM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from(final CatalogVersion item)
	{
		return getValid_from( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final SessionContext ctx, final CatalogVersion item, final Date value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.CatalogVersion.VALID_FROM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final CatalogVersion item, final Date value)
	{
		setValid_from( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to(final SessionContext ctx, final Category item)
	{
		return (Date)item.getProperty( ctx, WeraConstants.Attributes.Category.VALID_TO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to(final Category item)
	{
		return getValid_to( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final SessionContext ctx, final Category item, final Date value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.Category.VALID_TO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final Category item, final Date value)
	{
		setValid_to( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to(final SessionContext ctx, final CatalogVersion item)
	{
		return (Date)item.getProperty( ctx, WeraConstants.Attributes.CatalogVersion.VALID_TO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>CatalogVersion.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to(final CatalogVersion item)
	{
		return getValid_to( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final SessionContext ctx, final CatalogVersion item, final Date value)
	{
		item.setProperty(ctx, WeraConstants.Attributes.CatalogVersion.VALID_TO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>CatalogVersion.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final CatalogVersion item, final Date value)
	{
		setValid_to( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.weblinks_greattools</code> attribute.
	 * @return the weblinks_greattools
	 */
	public Collection<Weblink> getWeblinks_greattools(final SessionContext ctx, final Category item)
	{
		final List<Weblink> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>Category.weblinks_greattools</code> attribute.
	 * @return the weblinks_greattools
	 */
	public Collection<Weblink> getWeblinks_greattools(final Category item)
	{
		return getWeblinks_greattools( getSession().getSessionContext(), item );
	}
	
	public long getWeblinks_greattoolsCount(final SessionContext ctx, final Category item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null
		);
	}
	
	public long getWeblinks_greattoolsCount(final Category item)
	{
		return getWeblinks_greattoolsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.weblinks_greattools</code> attribute. 
	 * @param value the weblinks_greattools
	 */
	public void setWeblinks_greattools(final SessionContext ctx, final Category item, final Collection<Weblink> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>Category.weblinks_greattools</code> attribute. 
	 * @param value the weblinks_greattools
	 */
	public void setWeblinks_greattools(final Category item, final Collection<Weblink> value)
	{
		setWeblinks_greattools( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinks_greattools. 
	 * @param value the item to add to weblinks_greattools
	 */
	public void addToWeblinks_greattools(final SessionContext ctx, final Category item, final Weblink value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinks_greattools. 
	 * @param value the item to add to weblinks_greattools
	 */
	public void addToWeblinks_greattools(final Category item, final Weblink value)
	{
		addToWeblinks_greattools( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinks_greattools. 
	 * @param value the item to remove from weblinks_greattools
	 */
	public void removeFromWeblinks_greattools(final SessionContext ctx, final Category item, final Weblink value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CATEGORYGREATTOOLSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinks_greattools. 
	 * @param value the item to remove from weblinks_greattools
	 */
	public void removeFromWeblinks_greattools(final Category item, final Weblink value)
	{
		removeFromWeblinks_greattools( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.weblinks_greattools</code> attribute.
	 * @return the weblinks_greattools
	 */
	public Collection<Weblink> getWeblinks_greattools(final SessionContext ctx, final ClassificationAttribute item)
	{
		final List<Weblink> items = item.getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>ClassificationAttribute.weblinks_greattools</code> attribute.
	 * @return the weblinks_greattools
	 */
	public Collection<Weblink> getWeblinks_greattools(final ClassificationAttribute item)
	{
		return getWeblinks_greattools( getSession().getSessionContext(), item );
	}
	
	public long getWeblinks_greattoolsCount(final SessionContext ctx, final ClassificationAttribute item)
	{
		return item.getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null
		);
	}
	
	public long getWeblinks_greattoolsCount(final ClassificationAttribute item)
	{
		return getWeblinks_greattoolsCount( getSession().getSessionContext(), item );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.weblinks_greattools</code> attribute. 
	 * @param value the weblinks_greattools
	 */
	public void setWeblinks_greattools(final SessionContext ctx, final ClassificationAttribute item, final Collection<Weblink> value)
	{
		item.setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>ClassificationAttribute.weblinks_greattools</code> attribute. 
	 * @param value the weblinks_greattools
	 */
	public void setWeblinks_greattools(final ClassificationAttribute item, final Collection<Weblink> value)
	{
		setWeblinks_greattools( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinks_greattools. 
	 * @param value the item to add to weblinks_greattools
	 */
	public void addToWeblinks_greattools(final SessionContext ctx, final ClassificationAttribute item, final Weblink value)
	{
		item.addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinks_greattools. 
	 * @param value the item to add to weblinks_greattools
	 */
	public void addToWeblinks_greattools(final ClassificationAttribute item, final Weblink value)
	{
		addToWeblinks_greattools( getSession().getSessionContext(), item, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinks_greattools. 
	 * @param value the item to remove from weblinks_greattools
	 */
	public void removeFromWeblinks_greattools(final SessionContext ctx, final ClassificationAttribute item, final Weblink value)
	{
		item.removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.CLASSIFICATIONATTRIBUTEGREATTOOLSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinks_greattools. 
	 * @param value the item to remove from weblinks_greattools
	 */
	public void removeFromWeblinks_greattools(final ClassificationAttribute item, final Weblink value)
	{
		removeFromWeblinks_greattools( getSession().getSessionContext(), item, value );
	}
	
}

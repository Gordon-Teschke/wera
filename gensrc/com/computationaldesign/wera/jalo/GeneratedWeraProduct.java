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
import com.computationaldesign.wera.jalo.Footnote;
import com.computationaldesign.wera.jalo.Outputcontrol;
import com.computationaldesign.wera.jalo.Textbaustein;
import com.computationaldesign.wera.jalo.Tipp;
import com.computationaldesign.wera.jalo.Weblink;
import com.computationaldesign.wera.jalo.WeraMedia;
import com.computationaldesign.wera.jalo.WeraProduct;
import com.computationaldesign.wera.jalo.WeraProductSetinSet;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.CollectionType;
import de.hybris.platform.util.OneToManyHandler;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * Generated class for type {@link com.computationaldesign.wera.jalo.WeraProduct WeraProduct}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeraProduct extends Product
{
	/** Qualifier of the <code>WeraProduct.great_tools_basename</code> attribute **/
	public static final String GREAT_TOOLS_BASENAME = "great_tools_basename".intern();
	/** Qualifier of the <code>WeraProduct.sis_prioritize_code</code> attribute **/
	public static final String SIS_PRIORITIZE_CODE = "sis_prioritize_code".intern();
	/** Qualifier of the <code>WeraProduct.artikelnr_index</code> attribute **/
	public static final String ARTIKELNR_INDEX = "artikelnr_index".intern();
	/** Qualifier of the <code>WeraProduct.footnotes</code> attribute **/
	public static final String FOOTNOTES = "footnotes".intern();
	/** Qualifier of the <code>WeraProduct.icons2</code> attribute **/
	public static final String ICONS2 = "icons2".intern();
	/** Qualifier of the <code>WeraProduct.amazon_es_cat4</code> attribute **/
	public static final String AMAZON_ES_CAT4 = "amazon_es_cat4".intern();
	/** Qualifier of the <code>WeraProduct.amazon_fr_cat2</code> attribute **/
	public static final String AMAZON_FR_CAT2 = "amazon_fr_cat2".intern();
	/** Qualifier of the <code>WeraProduct.sortimenttippref</code> attribute **/
	public static final String SORTIMENTTIPPREF = "sortimenttippref".intern();
	/** Qualifier of the <code>WeraProduct.category2productexts</code> attribute **/
	public static final String CATEGORY2PRODUCTEXTS = "category2productexts".intern();
	/** Qualifier of the <code>WeraProduct.www_uk</code> attribute **/
	public static final String WWW_UK = "www_uk".intern();
	/** Qualifier of the <code>WeraProduct.abtriebAnschluss2products</code> attribute **/
	public static final String ABTRIEBANSCHLUSS2PRODUCTS = "abtriebAnschluss2products".intern();
	/** Qualifier of the <code>WeraProduct.description3</code> attribute **/
	public static final String DESCRIPTION3 = "description3".intern();
	/** Qualifier of the <code>WeraProduct.alternate_product</code> attribute **/
	public static final String ALTERNATE_PRODUCT = "alternate_product".intern();
	/** Qualifier of the <code>WeraProduct.produkt_neu</code> attribute **/
	public static final String PRODUKT_NEU = "produkt_neu".intern();
	/** Qualifier of the <code>WeraProduct.weblinks</code> attribute **/
	public static final String WEBLINKS = "weblinks".intern();
	/** Qualifier of the <code>WeraProduct.weblinks_greattools</code> attribute **/
	public static final String WEBLINKS_GREATTOOLS = "weblinks_greattools".intern();
	/** Qualifier of the <code>WeraProduct.description2</code> attribute **/
	public static final String DESCRIPTION2 = "description2".intern();
	/** Qualifier of the <code>WeraProduct.outputtemplate</code> attribute **/
	public static final String OUTPUTTEMPLATE = "outputtemplate".intern();
	/** Qualifier of the <code>WeraProduct.amazon_es_cat3</code> attribute **/
	public static final String AMAZON_ES_CAT3 = "amazon_es_cat3".intern();
	/** Qualifier of the <code>WeraProduct.priorityWebSearch</code> attribute **/
	public static final String PRIORITYWEBSEARCH = "priorityWebSearch".intern();
	/** Qualifier of the <code>WeraProduct.outputcontrols</code> attribute **/
	public static final String OUTPUTCONTROLS = "outputcontrols".intern();
	/** Qualifier of the <code>WeraProduct.amazon_de_cat1</code> attribute **/
	public static final String AMAZON_DE_CAT1 = "amazon_de_cat1".intern();
	/** Qualifier of the <code>WeraProduct.amazon_uk_cat4</code> attribute **/
	public static final String AMAZON_UK_CAT4 = "amazon_uk_cat4".intern();
	/** Qualifier of the <code>WeraProduct.amazon_uk_cat3</code> attribute **/
	public static final String AMAZON_UK_CAT3 = "amazon_uk_cat3".intern();
	/** Qualifier of the <code>WeraProduct.anwenderimageref2weraproduct</code> attribute **/
	public static final String ANWENDERIMAGEREF2WERAPRODUCT = "anwenderimageref2weraproduct".intern();
	/** Qualifier of the <code>WeraProduct.product2relcategoriesreverse</code> attribute **/
	public static final String PRODUCT2RELCATEGORIESREVERSE = "product2relcategoriesreverse".intern();
	/** Qualifier of the <code>WeraProduct.priority</code> attribute **/
	public static final String PRIORITY = "priority".intern();
	/** Qualifier of the <code>WeraProduct.secondaryimagesref2weraproduct</code> attribute **/
	public static final String SECONDARYIMAGESREF2WERAPRODUCT = "secondaryimagesref2weraproduct".intern();
	/** Qualifier of the <code>WeraProduct.pictures3</code> attribute **/
	public static final String PICTURES3 = "pictures3".intern();
	/** Qualifier of the <code>WeraProduct.description1</code> attribute **/
	public static final String DESCRIPTION1 = "description1".intern();
	/** Qualifier of the <code>WeraProduct.use_in_pricelist</code> attribute **/
	public static final String USE_IN_PRICELIST = "use_in_pricelist".intern();
	/** Qualifier of the <code>WeraProduct.www_au</code> attribute **/
	public static final String WWW_AU = "www_au".intern();
	/** Qualifier of the <code>WeraProduct.icons1</code> attribute **/
	public static final String ICONS1 = "icons1".intern();
	/** Qualifier of the <code>WeraProduct.amazon_uk_cat1</code> attribute **/
	public static final String AMAZON_UK_CAT1 = "amazon_uk_cat1".intern();
	/** Qualifier of the <code>WeraProduct.www_us</code> attribute **/
	public static final String WWW_US = "www_us".intern();
	/** Qualifier of the <code>WeraProduct.ab_bulletpoints_print_selection</code> attribute **/
	public static final String AB_BULLETPOINTS_PRINT_SELECTION = "ab_bulletpoints_print_selection".intern();
	/** Qualifier of the <code>WeraProduct.bulletpoints_print_selection</code> attribute **/
	public static final String BULLETPOINTS_PRINT_SELECTION = "bulletpoints_print_selection".intern();
	/** Qualifier of the <code>WeraProduct.featureicons1</code> attribute **/
	public static final String FEATUREICONS1 = "featureicons1".intern();
	/** Qualifier of the <code>WeraProduct.featureicons2</code> attribute **/
	public static final String FEATUREICONS2 = "featureicons2".intern();
	/** Qualifier of the <code>WeraProduct.amazon_fr_cat3</code> attribute **/
	public static final String AMAZON_FR_CAT3 = "amazon_fr_cat3".intern();
	/** Qualifier of the <code>WeraProduct.others_productpictures</code> attribute **/
	public static final String OTHERS_PRODUCTPICTURES = "others_productpictures".intern();
	/** Qualifier of the <code>WeraProduct.amazon_es_cat2</code> attribute **/
	public static final String AMAZON_ES_CAT2 = "amazon_es_cat2".intern();
	/** Qualifier of the <code>WeraProduct.artikel_kurztext</code> attribute **/
	public static final String ARTIKEL_KURZTEXT = "artikel_kurztext".intern();
	/** Qualifier of the <code>WeraProduct.output_category_titel</code> attribute **/
	public static final String OUTPUT_CATEGORY_TITEL = "output_category_titel".intern();
	/** Qualifier of the <code>WeraProduct.check_artikelnr_index</code> attribute **/
	public static final String CHECK_ARTIKELNR_INDEX = "check_artikelnr_index".intern();
	/** Qualifier of the <code>WeraProduct.amazon_it_cat4</code> attribute **/
	public static final String AMAZON_IT_CAT4 = "amazon_it_cat4".intern();
	/** Qualifier of the <code>WeraProduct.masterpk</code> attribute **/
	public static final String MASTERPK = "masterpk".intern();
	/** Qualifier of the <code>WeraProduct.pictures2</code> attribute **/
	public static final String PICTURES2 = "pictures2".intern();
	/** Qualifier of the <code>WeraProduct.typeofproduct</code> attribute **/
	public static final String TYPEOFPRODUCT = "typeofproduct".intern();
	/** Qualifier of the <code>WeraProduct.amazon_fr_cat4</code> attribute **/
	public static final String AMAZON_FR_CAT4 = "amazon_fr_cat4".intern();
	/** Qualifier of the <code>WeraProduct.weraproductsetinsets_relation</code> attribute **/
	public static final String WERAPRODUCTSETINSETS_RELATION = "weraproductsetinsets_relation".intern();
	/** Qualifier of the <code>WeraProduct.www_de</code> attribute **/
	public static final String WWW_DE = "www_de".intern();
	/** Qualifier of the <code>WeraProduct.bulletpoints_print_selection_preview</code> attribute **/
	public static final String BULLETPOINTS_PRINT_SELECTION_PREVIEW = "bulletpoints_print_selection_preview".intern();
	/** Qualifier of the <code>WeraProduct.weraproductset2bulletpoints</code> attribute **/
	public static final String WERAPRODUCTSET2BULLETPOINTS = "weraproductset2bulletpoints".intern();
	/** Qualifier of the <code>WeraProduct.amz_artikeltyp</code> attribute **/
	public static final String AMZ_ARTIKELTYP = "amz_artikeltyp".intern();
	/** Qualifier of the <code>WeraProduct.amazon_de_cat3</code> attribute **/
	public static final String AMAZON_DE_CAT3 = "amazon_de_cat3".intern();
	/** Qualifier of the <code>WeraProduct.artikel_auslauf</code> attribute **/
	public static final String ARTIKEL_AUSLAUF = "artikel_auslauf".intern();
	/** Qualifier of the <code>WeraProduct.use_for_greatttools</code> attribute **/
	public static final String USE_FOR_GREATTTOOLS = "use_for_greatttools".intern();
	/** Qualifier of the <code>WeraProduct.artikel_sb_faehig</code> attribute **/
	public static final String ARTIKEL_SB_FAEHIG = "artikel_sb_faehig".intern();
	/** Qualifier of the <code>WeraProduct.amazon_uk_cat2</code> attribute **/
	public static final String AMAZON_UK_CAT2 = "amazon_uk_cat2".intern();
	/** Qualifier of the <code>WeraProduct.amazon_de_cat4</code> attribute **/
	public static final String AMAZON_DE_CAT4 = "amazon_de_cat4".intern();
	/** Qualifier of the <code>WeraProduct.aktiv</code> attribute **/
	public static final String AKTIV = "aktiv".intern();
	/** Qualifier of the <code>WeraProduct.desc_picture1</code> attribute **/
	public static final String DESC_PICTURE1 = "desc_picture1".intern();
	/** Qualifier of the <code>WeraProduct.amazon_fr_cat1</code> attribute **/
	public static final String AMAZON_FR_CAT1 = "amazon_fr_cat1".intern();
	/** Qualifier of the <code>WeraProduct.weraproduct2marketing</code> attribute **/
	public static final String WERAPRODUCT2MARKETING = "weraproduct2marketing".intern();
	/** Qualifier of the <code>WeraProduct.use_in_catalog</code> attribute **/
	public static final String USE_IN_CATALOG = "use_in_catalog".intern();
	/** Qualifier of the <code>WeraProduct.antriebAnschluss2products</code> attribute **/
	public static final String ANTRIEBANSCHLUSS2PRODUCTS = "antriebAnschluss2products".intern();
	/** Qualifier of the <code>WeraProduct.amazon_us_cat2</code> attribute **/
	public static final String AMAZON_US_CAT2 = "amazon_us_cat2".intern();
	/** Qualifier of the <code>WeraProduct.amazon_us_cat1</code> attribute **/
	public static final String AMAZON_US_CAT1 = "amazon_us_cat1".intern();
	/** Qualifier of the <code>WeraProduct.abtriebSchraubprofil2products</code> attribute **/
	public static final String ABTRIEBSCHRAUBPROFIL2PRODUCTS = "abtriebSchraubprofil2products".intern();
	/** Qualifier of the <code>WeraProduct.extimages</code> attribute **/
	public static final String EXTIMAGES = "extimages".intern();
	/** Qualifier of the <code>WeraProduct.amazon_es_cat1</code> attribute **/
	public static final String AMAZON_ES_CAT1 = "amazon_es_cat1".intern();
	/** Qualifier of the <code>WeraProduct.amazon_de_cat2</code> attribute **/
	public static final String AMAZON_DE_CAT2 = "amazon_de_cat2".intern();
	/** Qualifier of the <code>WeraProduct.amazon_it_cat1</code> attribute **/
	public static final String AMAZON_IT_CAT1 = "amazon_it_cat1".intern();
	/** Qualifier of the <code>WeraProduct.sb_bulletpoints_print_selection</code> attribute **/
	public static final String SB_BULLETPOINTS_PRINT_SELECTION = "sb_bulletpoints_print_selection".intern();
	/** Qualifier of the <code>WeraProduct.orderPL</code> attribute **/
	public static final String ORDERPL = "orderPL".intern();
	/** Qualifier of the <code>WeraProduct.amazon_it_cat2</code> attribute **/
	public static final String AMAZON_IT_CAT2 = "amazon_it_cat2".intern();
	/** Qualifier of the <code>WeraProduct.pictures1</code> attribute **/
	public static final String PICTURES1 = "pictures1".intern();
	/** Qualifier of the <code>WeraProduct.amazon_it_cat3</code> attribute **/
	public static final String AMAZON_IT_CAT3 = "amazon_it_cat3".intern();
	protected static final OneToManyHandler<WeraProductSetinSet> WERAPRODUCTSETINSETS_RELATIONHANDLER = new OneToManyHandler<WeraProductSetinSet>(
	WeraConstants.TC.WERAPRODUCTSETINSET,
	false,
	"weraproducts_relation".intern(),
	null,
	false,
	true,
	CollectionType.COLLECTION
	);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.ab_bulletpoints_print_selection</code> attribute.
	 * @return the ab_bulletpoints_print_selection
	 */
	public String getAb_bulletpoints_print_selection(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AB_BULLETPOINTS_PRINT_SELECTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.ab_bulletpoints_print_selection</code> attribute.
	 * @return the ab_bulletpoints_print_selection
	 */
	public String getAb_bulletpoints_print_selection()
	{
		return getAb_bulletpoints_print_selection( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.ab_bulletpoints_print_selection</code> attribute. 
	 * @param value the ab_bulletpoints_print_selection
	 */
	public void setAb_bulletpoints_print_selection(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AB_BULLETPOINTS_PRINT_SELECTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.ab_bulletpoints_print_selection</code> attribute. 
	 * @param value the ab_bulletpoints_print_selection
	 */
	public void setAb_bulletpoints_print_selection(final String value)
	{
		setAb_bulletpoints_print_selection( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.abtriebAnschluss2products</code> attribute.
	 * @return the abtriebAnschluss2products
	 */
	public Collection<AbtriebAnschluss> getAbtriebAnschluss2products(final SessionContext ctx)
	{
		final List<AbtriebAnschluss> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.abtriebAnschluss2products</code> attribute.
	 * @return the abtriebAnschluss2products
	 */
	public Collection<AbtriebAnschluss> getAbtriebAnschluss2products()
	{
		return getAbtriebAnschluss2products( getSession().getSessionContext() );
	}
	
	public long getAbtriebAnschluss2productsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null
		);
	}
	
	public long getAbtriebAnschluss2productsCount()
	{
		return getAbtriebAnschluss2productsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.abtriebAnschluss2products</code> attribute. 
	 * @param value the abtriebAnschluss2products
	 */
	public void setAbtriebAnschluss2products(final SessionContext ctx, final Collection<AbtriebAnschluss> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.abtriebAnschluss2products</code> attribute. 
	 * @param value the abtriebAnschluss2products
	 */
	public void setAbtriebAnschluss2products(final Collection<AbtriebAnschluss> value)
	{
		setAbtriebAnschluss2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebAnschluss2products. 
	 * @param value the item to add to abtriebAnschluss2products
	 */
	public void addToAbtriebAnschluss2products(final SessionContext ctx, final AbtriebAnschluss value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebAnschluss2products. 
	 * @param value the item to add to abtriebAnschluss2products
	 */
	public void addToAbtriebAnschluss2products(final AbtriebAnschluss value)
	{
		addToAbtriebAnschluss2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebAnschluss2products. 
	 * @param value the item to remove from abtriebAnschluss2products
	 */
	public void removeFromAbtriebAnschluss2products(final SessionContext ctx, final AbtriebAnschluss value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBANSCHLUSSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebAnschluss2products. 
	 * @param value the item to remove from abtriebAnschluss2products
	 */
	public void removeFromAbtriebAnschluss2products(final AbtriebAnschluss value)
	{
		removeFromAbtriebAnschluss2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.abtriebSchraubprofil2products</code> attribute.
	 * @return the abtriebSchraubprofil2products
	 */
	public Collection<AbtriebSchraubprofil> getAbtriebSchraubprofil2products(final SessionContext ctx)
	{
		final List<AbtriebSchraubprofil> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.abtriebSchraubprofil2products</code> attribute.
	 * @return the abtriebSchraubprofil2products
	 */
	public Collection<AbtriebSchraubprofil> getAbtriebSchraubprofil2products()
	{
		return getAbtriebSchraubprofil2products( getSession().getSessionContext() );
	}
	
	public long getAbtriebSchraubprofil2productsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null
		);
	}
	
	public long getAbtriebSchraubprofil2productsCount()
	{
		return getAbtriebSchraubprofil2productsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.abtriebSchraubprofil2products</code> attribute. 
	 * @param value the abtriebSchraubprofil2products
	 */
	public void setAbtriebSchraubprofil2products(final SessionContext ctx, final Collection<AbtriebSchraubprofil> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.abtriebSchraubprofil2products</code> attribute. 
	 * @param value the abtriebSchraubprofil2products
	 */
	public void setAbtriebSchraubprofil2products(final Collection<AbtriebSchraubprofil> value)
	{
		setAbtriebSchraubprofil2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebSchraubprofil2products. 
	 * @param value the item to add to abtriebSchraubprofil2products
	 */
	public void addToAbtriebSchraubprofil2products(final SessionContext ctx, final AbtriebSchraubprofil value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to abtriebSchraubprofil2products. 
	 * @param value the item to add to abtriebSchraubprofil2products
	 */
	public void addToAbtriebSchraubprofil2products(final AbtriebSchraubprofil value)
	{
		addToAbtriebSchraubprofil2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebSchraubprofil2products. 
	 * @param value the item to remove from abtriebSchraubprofil2products
	 */
	public void removeFromAbtriebSchraubprofil2products(final SessionContext ctx, final AbtriebSchraubprofil value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTABTRIEBSCHRAUBPROFILRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from abtriebSchraubprofil2products. 
	 * @param value the item to remove from abtriebSchraubprofil2products
	 */
	public void removeFromAbtriebSchraubprofil2products(final AbtriebSchraubprofil value)
	{
		removeFromAbtriebSchraubprofil2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.aktiv</code> attribute.
	 * @return the aktiv - Ausgabe Produkt J/N
	 */
	public Boolean isAktiv(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, AKTIV);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.aktiv</code> attribute.
	 * @return the aktiv - Ausgabe Produkt J/N
	 */
	public Boolean isAktiv()
	{
		return isAktiv( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.aktiv</code> attribute. 
	 * @return the aktiv - Ausgabe Produkt J/N
	 */
	public boolean isAktivAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isAktiv( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.aktiv</code> attribute. 
	 * @return the aktiv - Ausgabe Produkt J/N
	 */
	public boolean isAktivAsPrimitive()
	{
		return isAktivAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Produkt J/N
	 */
	public void setAktiv(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, AKTIV,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Produkt J/N
	 */
	public void setAktiv(final Boolean value)
	{
		setAktiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Produkt J/N
	 */
	public void setAktiv(final SessionContext ctx, final boolean value)
	{
		setAktiv( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.aktiv</code> attribute. 
	 * @param value the aktiv - Ausgabe Produkt J/N
	 */
	public void setAktiv(final boolean value)
	{
		setAktiv( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.alternate_product</code> attribute.
	 * @return the alternate_product
	 */
	public Collection<WeraProduct> getAlternate_product(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.getAlternate_product requires a session language", 0 );
		}
		Collection<WeraProduct> coll = (Collection<WeraProduct>)getLocalizedProperty( ctx, ALTERNATE_PRODUCT);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.alternate_product</code> attribute.
	 * @return the alternate_product
	 */
	public Collection<WeraProduct> getAlternate_product()
	{
		return getAlternate_product( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.alternate_product</code> attribute. 
	 * @return the localized alternate_product
	 */
	public Map<Language,Collection<WeraProduct>> getAllAlternate_product(final SessionContext ctx)
	{
		return (Map<Language,Collection<WeraProduct>>)getAllLocalizedProperties(ctx,ALTERNATE_PRODUCT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.alternate_product</code> attribute. 
	 * @return the localized alternate_product
	 */
	public Map<Language,Collection<WeraProduct>> getAllAlternate_product()
	{
		return getAllAlternate_product( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.alternate_product</code> attribute. 
	 * @param value the alternate_product
	 */
	public void setAlternate_product(final SessionContext ctx, final Collection<WeraProduct> value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.setAlternate_product requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ALTERNATE_PRODUCT,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.alternate_product</code> attribute. 
	 * @param value the alternate_product
	 */
	public void setAlternate_product(final Collection<WeraProduct> value)
	{
		setAlternate_product( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.alternate_product</code> attribute. 
	 * @param value the alternate_product
	 */
	public void setAllAlternate_product(final SessionContext ctx, final Map<Language,Collection<WeraProduct>> value)
	{
		setAllLocalizedProperties(ctx,ALTERNATE_PRODUCT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.alternate_product</code> attribute. 
	 * @param value the alternate_product
	 */
	public void setAllAlternate_product(final Map<Language,Collection<WeraProduct>> value)
	{
		setAllAlternate_product( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_de_cat1</code> attribute.
	 * @return the amazon_de_cat1
	 */
	public String getAmazon_de_cat1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_DE_CAT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_de_cat1</code> attribute.
	 * @return the amazon_de_cat1
	 */
	public String getAmazon_de_cat1()
	{
		return getAmazon_de_cat1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_de_cat1</code> attribute. 
	 * @param value the amazon_de_cat1
	 */
	public void setAmazon_de_cat1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_DE_CAT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_de_cat1</code> attribute. 
	 * @param value the amazon_de_cat1
	 */
	public void setAmazon_de_cat1(final String value)
	{
		setAmazon_de_cat1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_de_cat2</code> attribute.
	 * @return the amazon_de_cat2
	 */
	public String getAmazon_de_cat2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_DE_CAT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_de_cat2</code> attribute.
	 * @return the amazon_de_cat2
	 */
	public String getAmazon_de_cat2()
	{
		return getAmazon_de_cat2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_de_cat2</code> attribute. 
	 * @param value the amazon_de_cat2
	 */
	public void setAmazon_de_cat2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_DE_CAT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_de_cat2</code> attribute. 
	 * @param value the amazon_de_cat2
	 */
	public void setAmazon_de_cat2(final String value)
	{
		setAmazon_de_cat2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_de_cat3</code> attribute.
	 * @return the amazon_de_cat3
	 */
	public String getAmazon_de_cat3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_DE_CAT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_de_cat3</code> attribute.
	 * @return the amazon_de_cat3
	 */
	public String getAmazon_de_cat3()
	{
		return getAmazon_de_cat3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_de_cat3</code> attribute. 
	 * @param value the amazon_de_cat3
	 */
	public void setAmazon_de_cat3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_DE_CAT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_de_cat3</code> attribute. 
	 * @param value the amazon_de_cat3
	 */
	public void setAmazon_de_cat3(final String value)
	{
		setAmazon_de_cat3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_de_cat4</code> attribute.
	 * @return the amazon_de_cat4
	 */
	public String getAmazon_de_cat4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_DE_CAT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_de_cat4</code> attribute.
	 * @return the amazon_de_cat4
	 */
	public String getAmazon_de_cat4()
	{
		return getAmazon_de_cat4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_de_cat4</code> attribute. 
	 * @param value the amazon_de_cat4
	 */
	public void setAmazon_de_cat4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_DE_CAT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_de_cat4</code> attribute. 
	 * @param value the amazon_de_cat4
	 */
	public void setAmazon_de_cat4(final String value)
	{
		setAmazon_de_cat4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_es_cat1</code> attribute.
	 * @return the amazon_es_cat1
	 */
	public String getAmazon_es_cat1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_ES_CAT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_es_cat1</code> attribute.
	 * @return the amazon_es_cat1
	 */
	public String getAmazon_es_cat1()
	{
		return getAmazon_es_cat1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_es_cat1</code> attribute. 
	 * @param value the amazon_es_cat1
	 */
	public void setAmazon_es_cat1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_ES_CAT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_es_cat1</code> attribute. 
	 * @param value the amazon_es_cat1
	 */
	public void setAmazon_es_cat1(final String value)
	{
		setAmazon_es_cat1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_es_cat2</code> attribute.
	 * @return the amazon_es_cat2
	 */
	public String getAmazon_es_cat2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_ES_CAT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_es_cat2</code> attribute.
	 * @return the amazon_es_cat2
	 */
	public String getAmazon_es_cat2()
	{
		return getAmazon_es_cat2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_es_cat2</code> attribute. 
	 * @param value the amazon_es_cat2
	 */
	public void setAmazon_es_cat2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_ES_CAT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_es_cat2</code> attribute. 
	 * @param value the amazon_es_cat2
	 */
	public void setAmazon_es_cat2(final String value)
	{
		setAmazon_es_cat2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_es_cat3</code> attribute.
	 * @return the amazon_es_cat3
	 */
	public String getAmazon_es_cat3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_ES_CAT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_es_cat3</code> attribute.
	 * @return the amazon_es_cat3
	 */
	public String getAmazon_es_cat3()
	{
		return getAmazon_es_cat3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_es_cat3</code> attribute. 
	 * @param value the amazon_es_cat3
	 */
	public void setAmazon_es_cat3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_ES_CAT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_es_cat3</code> attribute. 
	 * @param value the amazon_es_cat3
	 */
	public void setAmazon_es_cat3(final String value)
	{
		setAmazon_es_cat3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_es_cat4</code> attribute.
	 * @return the amazon_es_cat4
	 */
	public String getAmazon_es_cat4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_ES_CAT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_es_cat4</code> attribute.
	 * @return the amazon_es_cat4
	 */
	public String getAmazon_es_cat4()
	{
		return getAmazon_es_cat4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_es_cat4</code> attribute. 
	 * @param value the amazon_es_cat4
	 */
	public void setAmazon_es_cat4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_ES_CAT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_es_cat4</code> attribute. 
	 * @param value the amazon_es_cat4
	 */
	public void setAmazon_es_cat4(final String value)
	{
		setAmazon_es_cat4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_fr_cat1</code> attribute.
	 * @return the amazon_fr_cat1
	 */
	public String getAmazon_fr_cat1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_FR_CAT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_fr_cat1</code> attribute.
	 * @return the amazon_fr_cat1
	 */
	public String getAmazon_fr_cat1()
	{
		return getAmazon_fr_cat1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_fr_cat1</code> attribute. 
	 * @param value the amazon_fr_cat1
	 */
	public void setAmazon_fr_cat1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_FR_CAT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_fr_cat1</code> attribute. 
	 * @param value the amazon_fr_cat1
	 */
	public void setAmazon_fr_cat1(final String value)
	{
		setAmazon_fr_cat1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_fr_cat2</code> attribute.
	 * @return the amazon_fr_cat2
	 */
	public String getAmazon_fr_cat2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_FR_CAT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_fr_cat2</code> attribute.
	 * @return the amazon_fr_cat2
	 */
	public String getAmazon_fr_cat2()
	{
		return getAmazon_fr_cat2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_fr_cat2</code> attribute. 
	 * @param value the amazon_fr_cat2
	 */
	public void setAmazon_fr_cat2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_FR_CAT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_fr_cat2</code> attribute. 
	 * @param value the amazon_fr_cat2
	 */
	public void setAmazon_fr_cat2(final String value)
	{
		setAmazon_fr_cat2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_fr_cat3</code> attribute.
	 * @return the amazon_fr_cat3
	 */
	public String getAmazon_fr_cat3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_FR_CAT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_fr_cat3</code> attribute.
	 * @return the amazon_fr_cat3
	 */
	public String getAmazon_fr_cat3()
	{
		return getAmazon_fr_cat3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_fr_cat3</code> attribute. 
	 * @param value the amazon_fr_cat3
	 */
	public void setAmazon_fr_cat3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_FR_CAT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_fr_cat3</code> attribute. 
	 * @param value the amazon_fr_cat3
	 */
	public void setAmazon_fr_cat3(final String value)
	{
		setAmazon_fr_cat3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_fr_cat4</code> attribute.
	 * @return the amazon_fr_cat4
	 */
	public String getAmazon_fr_cat4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_FR_CAT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_fr_cat4</code> attribute.
	 * @return the amazon_fr_cat4
	 */
	public String getAmazon_fr_cat4()
	{
		return getAmazon_fr_cat4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_fr_cat4</code> attribute. 
	 * @param value the amazon_fr_cat4
	 */
	public void setAmazon_fr_cat4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_FR_CAT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_fr_cat4</code> attribute. 
	 * @param value the amazon_fr_cat4
	 */
	public void setAmazon_fr_cat4(final String value)
	{
		setAmazon_fr_cat4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_it_cat1</code> attribute.
	 * @return the amazon_it_cat1
	 */
	public String getAmazon_it_cat1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_IT_CAT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_it_cat1</code> attribute.
	 * @return the amazon_it_cat1
	 */
	public String getAmazon_it_cat1()
	{
		return getAmazon_it_cat1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_it_cat1</code> attribute. 
	 * @param value the amazon_it_cat1
	 */
	public void setAmazon_it_cat1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_IT_CAT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_it_cat1</code> attribute. 
	 * @param value the amazon_it_cat1
	 */
	public void setAmazon_it_cat1(final String value)
	{
		setAmazon_it_cat1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_it_cat2</code> attribute.
	 * @return the amazon_it_cat2
	 */
	public String getAmazon_it_cat2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_IT_CAT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_it_cat2</code> attribute.
	 * @return the amazon_it_cat2
	 */
	public String getAmazon_it_cat2()
	{
		return getAmazon_it_cat2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_it_cat2</code> attribute. 
	 * @param value the amazon_it_cat2
	 */
	public void setAmazon_it_cat2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_IT_CAT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_it_cat2</code> attribute. 
	 * @param value the amazon_it_cat2
	 */
	public void setAmazon_it_cat2(final String value)
	{
		setAmazon_it_cat2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_it_cat3</code> attribute.
	 * @return the amazon_it_cat3
	 */
	public String getAmazon_it_cat3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_IT_CAT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_it_cat3</code> attribute.
	 * @return the amazon_it_cat3
	 */
	public String getAmazon_it_cat3()
	{
		return getAmazon_it_cat3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_it_cat3</code> attribute. 
	 * @param value the amazon_it_cat3
	 */
	public void setAmazon_it_cat3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_IT_CAT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_it_cat3</code> attribute. 
	 * @param value the amazon_it_cat3
	 */
	public void setAmazon_it_cat3(final String value)
	{
		setAmazon_it_cat3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_it_cat4</code> attribute.
	 * @return the amazon_it_cat4
	 */
	public String getAmazon_it_cat4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_IT_CAT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_it_cat4</code> attribute.
	 * @return the amazon_it_cat4
	 */
	public String getAmazon_it_cat4()
	{
		return getAmazon_it_cat4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_it_cat4</code> attribute. 
	 * @param value the amazon_it_cat4
	 */
	public void setAmazon_it_cat4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_IT_CAT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_it_cat4</code> attribute. 
	 * @param value the amazon_it_cat4
	 */
	public void setAmazon_it_cat4(final String value)
	{
		setAmazon_it_cat4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_uk_cat1</code> attribute.
	 * @return the amazon_uk_cat1
	 */
	public String getAmazon_uk_cat1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_UK_CAT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_uk_cat1</code> attribute.
	 * @return the amazon_uk_cat1
	 */
	public String getAmazon_uk_cat1()
	{
		return getAmazon_uk_cat1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_uk_cat1</code> attribute. 
	 * @param value the amazon_uk_cat1
	 */
	public void setAmazon_uk_cat1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_UK_CAT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_uk_cat1</code> attribute. 
	 * @param value the amazon_uk_cat1
	 */
	public void setAmazon_uk_cat1(final String value)
	{
		setAmazon_uk_cat1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_uk_cat2</code> attribute.
	 * @return the amazon_uk_cat2
	 */
	public String getAmazon_uk_cat2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_UK_CAT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_uk_cat2</code> attribute.
	 * @return the amazon_uk_cat2
	 */
	public String getAmazon_uk_cat2()
	{
		return getAmazon_uk_cat2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_uk_cat2</code> attribute. 
	 * @param value the amazon_uk_cat2
	 */
	public void setAmazon_uk_cat2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_UK_CAT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_uk_cat2</code> attribute. 
	 * @param value the amazon_uk_cat2
	 */
	public void setAmazon_uk_cat2(final String value)
	{
		setAmazon_uk_cat2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_uk_cat3</code> attribute.
	 * @return the amazon_uk_cat3
	 */
	public String getAmazon_uk_cat3(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_UK_CAT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_uk_cat3</code> attribute.
	 * @return the amazon_uk_cat3
	 */
	public String getAmazon_uk_cat3()
	{
		return getAmazon_uk_cat3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_uk_cat3</code> attribute. 
	 * @param value the amazon_uk_cat3
	 */
	public void setAmazon_uk_cat3(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_UK_CAT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_uk_cat3</code> attribute. 
	 * @param value the amazon_uk_cat3
	 */
	public void setAmazon_uk_cat3(final String value)
	{
		setAmazon_uk_cat3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_uk_cat4</code> attribute.
	 * @return the amazon_uk_cat4
	 */
	public String getAmazon_uk_cat4(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_UK_CAT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_uk_cat4</code> attribute.
	 * @return the amazon_uk_cat4
	 */
	public String getAmazon_uk_cat4()
	{
		return getAmazon_uk_cat4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_uk_cat4</code> attribute. 
	 * @param value the amazon_uk_cat4
	 */
	public void setAmazon_uk_cat4(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_UK_CAT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_uk_cat4</code> attribute. 
	 * @param value the amazon_uk_cat4
	 */
	public void setAmazon_uk_cat4(final String value)
	{
		setAmazon_uk_cat4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_us_cat1</code> attribute.
	 * @return the amazon_us_cat1
	 */
	public String getAmazon_us_cat1(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_US_CAT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_us_cat1</code> attribute.
	 * @return the amazon_us_cat1
	 */
	public String getAmazon_us_cat1()
	{
		return getAmazon_us_cat1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_us_cat1</code> attribute. 
	 * @param value the amazon_us_cat1
	 */
	public void setAmazon_us_cat1(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_US_CAT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_us_cat1</code> attribute. 
	 * @param value the amazon_us_cat1
	 */
	public void setAmazon_us_cat1(final String value)
	{
		setAmazon_us_cat1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_us_cat2</code> attribute.
	 * @return the amazon_us_cat2
	 */
	public String getAmazon_us_cat2(final SessionContext ctx)
	{
		return (String)getProperty( ctx, AMAZON_US_CAT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amazon_us_cat2</code> attribute.
	 * @return the amazon_us_cat2
	 */
	public String getAmazon_us_cat2()
	{
		return getAmazon_us_cat2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_us_cat2</code> attribute. 
	 * @param value the amazon_us_cat2
	 */
	public void setAmazon_us_cat2(final SessionContext ctx, final String value)
	{
		setProperty(ctx, AMAZON_US_CAT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amazon_us_cat2</code> attribute. 
	 * @param value the amazon_us_cat2
	 */
	public void setAmazon_us_cat2(final String value)
	{
		setAmazon_us_cat2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amz_artikeltyp</code> attribute.
	 * @return the amz_artikeltyp
	 */
	public Collection<Textbaustein> getAmz_artikeltyp(final SessionContext ctx)
	{
		final List<Textbaustein> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.amz_artikeltyp</code> attribute.
	 * @return the amz_artikeltyp
	 */
	public Collection<Textbaustein> getAmz_artikeltyp()
	{
		return getAmz_artikeltyp( getSession().getSessionContext() );
	}
	
	public long getAmz_artikeltypCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null
		);
	}
	
	public long getAmz_artikeltypCount()
	{
		return getAmz_artikeltypCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amz_artikeltyp</code> attribute. 
	 * @param value the amz_artikeltyp
	 */
	public void setAmz_artikeltyp(final SessionContext ctx, final Collection<Textbaustein> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.amz_artikeltyp</code> attribute. 
	 * @param value the amz_artikeltyp
	 */
	public void setAmz_artikeltyp(final Collection<Textbaustein> value)
	{
		setAmz_artikeltyp( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to amz_artikeltyp. 
	 * @param value the item to add to amz_artikeltyp
	 */
	public void addToAmz_artikeltyp(final SessionContext ctx, final Textbaustein value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to amz_artikeltyp. 
	 * @param value the item to add to amz_artikeltyp
	 */
	public void addToAmz_artikeltyp(final Textbaustein value)
	{
		addToAmz_artikeltyp( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from amz_artikeltyp. 
	 * @param value the item to remove from amz_artikeltyp
	 */
	public void removeFromAmz_artikeltyp(final SessionContext ctx, final Textbaustein value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from amz_artikeltyp. 
	 * @param value the item to remove from amz_artikeltyp
	 */
	public void removeFromAmz_artikeltyp(final Textbaustein value)
	{
		removeFromAmz_artikeltyp( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.antriebAnschluss2products</code> attribute.
	 * @return the antriebAnschluss2products
	 */
	public Collection<AntriebAnschluss> getAntriebAnschluss2products(final SessionContext ctx)
	{
		final List<AntriebAnschluss> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.antriebAnschluss2products</code> attribute.
	 * @return the antriebAnschluss2products
	 */
	public Collection<AntriebAnschluss> getAntriebAnschluss2products()
	{
		return getAntriebAnschluss2products( getSession().getSessionContext() );
	}
	
	public long getAntriebAnschluss2productsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null
		);
	}
	
	public long getAntriebAnschluss2productsCount()
	{
		return getAntriebAnschluss2productsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.antriebAnschluss2products</code> attribute. 
	 * @param value the antriebAnschluss2products
	 */
	public void setAntriebAnschluss2products(final SessionContext ctx, final Collection<AntriebAnschluss> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.antriebAnschluss2products</code> attribute. 
	 * @param value the antriebAnschluss2products
	 */
	public void setAntriebAnschluss2products(final Collection<AntriebAnschluss> value)
	{
		setAntriebAnschluss2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to antriebAnschluss2products. 
	 * @param value the item to add to antriebAnschluss2products
	 */
	public void addToAntriebAnschluss2products(final SessionContext ctx, final AntriebAnschluss value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to antriebAnschluss2products. 
	 * @param value the item to add to antriebAnschluss2products
	 */
	public void addToAntriebAnschluss2products(final AntriebAnschluss value)
	{
		addToAntriebAnschluss2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from antriebAnschluss2products. 
	 * @param value the item to remove from antriebAnschluss2products
	 */
	public void removeFromAntriebAnschluss2products(final SessionContext ctx, final AntriebAnschluss value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTANTRIEBANSCHLUSSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from antriebAnschluss2products. 
	 * @param value the item to remove from antriebAnschluss2products
	 */
	public void removeFromAntriebAnschluss2products(final AntriebAnschluss value)
	{
		removeFromAntriebAnschluss2products( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.anwenderimageref2weraproduct</code> attribute.
	 * @return the anwenderimageref2weraproduct
	 */
	public Collection<Bildreferenz> getAnwenderimageref2weraproduct(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.anwenderimageref2weraproduct</code> attribute.
	 * @return the anwenderimageref2weraproduct
	 */
	public Collection<Bildreferenz> getAnwenderimageref2weraproduct()
	{
		return getAnwenderimageref2weraproduct( getSession().getSessionContext() );
	}
	
	public long getAnwenderimageref2weraproductCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null
		);
	}
	
	public long getAnwenderimageref2weraproductCount()
	{
		return getAnwenderimageref2weraproductCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.anwenderimageref2weraproduct</code> attribute. 
	 * @param value the anwenderimageref2weraproduct
	 */
	public void setAnwenderimageref2weraproduct(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.anwenderimageref2weraproduct</code> attribute. 
	 * @param value the anwenderimageref2weraproduct
	 */
	public void setAnwenderimageref2weraproduct(final Collection<Bildreferenz> value)
	{
		setAnwenderimageref2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to anwenderimageref2weraproduct. 
	 * @param value the item to add to anwenderimageref2weraproduct
	 */
	public void addToAnwenderimageref2weraproduct(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to anwenderimageref2weraproduct. 
	 * @param value the item to add to anwenderimageref2weraproduct
	 */
	public void addToAnwenderimageref2weraproduct(final Bildreferenz value)
	{
		addToAnwenderimageref2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from anwenderimageref2weraproduct. 
	 * @param value the item to remove from anwenderimageref2weraproduct
	 */
	public void removeFromAnwenderimageref2weraproduct(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTANWENDERIMAGERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from anwenderimageref2weraproduct. 
	 * @param value the item to remove from anwenderimageref2weraproduct
	 */
	public void removeFromAnwenderimageref2weraproduct(final Bildreferenz value)
	{
		removeFromAnwenderimageref2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_auslauf</code> attribute.
	 * @return the artikel_auslauf - Auslauf Produkt
	 */
	public Boolean isArtikel_auslauf(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ARTIKEL_AUSLAUF);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_auslauf</code> attribute.
	 * @return the artikel_auslauf - Auslauf Produkt
	 */
	public Boolean isArtikel_auslauf()
	{
		return isArtikel_auslauf( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_auslauf</code> attribute. 
	 * @return the artikel_auslauf - Auslauf Produkt
	 */
	public boolean isArtikel_auslaufAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isArtikel_auslauf( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_auslauf</code> attribute. 
	 * @return the artikel_auslauf - Auslauf Produkt
	 */
	public boolean isArtikel_auslaufAsPrimitive()
	{
		return isArtikel_auslaufAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_auslauf</code> attribute. 
	 * @param value the artikel_auslauf - Auslauf Produkt
	 */
	public void setArtikel_auslauf(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ARTIKEL_AUSLAUF,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_auslauf</code> attribute. 
	 * @param value the artikel_auslauf - Auslauf Produkt
	 */
	public void setArtikel_auslauf(final Boolean value)
	{
		setArtikel_auslauf( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_auslauf</code> attribute. 
	 * @param value the artikel_auslauf - Auslauf Produkt
	 */
	public void setArtikel_auslauf(final SessionContext ctx, final boolean value)
	{
		setArtikel_auslauf( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_auslauf</code> attribute. 
	 * @param value the artikel_auslauf - Auslauf Produkt
	 */
	public void setArtikel_auslauf(final boolean value)
	{
		setArtikel_auslauf( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_kurztext</code> attribute.
	 * @return the artikel_kurztext
	 */
	public String getArtikel_kurztext(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.getArtikel_kurztext requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ARTIKEL_KURZTEXT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_kurztext</code> attribute.
	 * @return the artikel_kurztext
	 */
	public String getArtikel_kurztext()
	{
		return getArtikel_kurztext( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_kurztext</code> attribute. 
	 * @return the localized artikel_kurztext
	 */
	public Map<Language,String> getAllArtikel_kurztext(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ARTIKEL_KURZTEXT,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_kurztext</code> attribute. 
	 * @return the localized artikel_kurztext
	 */
	public Map<Language,String> getAllArtikel_kurztext()
	{
		return getAllArtikel_kurztext( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_kurztext</code> attribute. 
	 * @param value the artikel_kurztext
	 */
	public void setArtikel_kurztext(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.setArtikel_kurztext requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ARTIKEL_KURZTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_kurztext</code> attribute. 
	 * @param value the artikel_kurztext
	 */
	public void setArtikel_kurztext(final String value)
	{
		setArtikel_kurztext( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_kurztext</code> attribute. 
	 * @param value the artikel_kurztext
	 */
	public void setAllArtikel_kurztext(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ARTIKEL_KURZTEXT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_kurztext</code> attribute. 
	 * @param value the artikel_kurztext
	 */
	public void setAllArtikel_kurztext(final Map<Language,String> value)
	{
		setAllArtikel_kurztext( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_sb_faehig</code> attribute.
	 * @return the artikel_sb_faehig - SB-fähig
	 */
	public Boolean isArtikel_sb_faehig(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, ARTIKEL_SB_FAEHIG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_sb_faehig</code> attribute.
	 * @return the artikel_sb_faehig - SB-fähig
	 */
	public Boolean isArtikel_sb_faehig()
	{
		return isArtikel_sb_faehig( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_sb_faehig</code> attribute. 
	 * @return the artikel_sb_faehig - SB-fähig
	 */
	public boolean isArtikel_sb_faehigAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isArtikel_sb_faehig( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikel_sb_faehig</code> attribute. 
	 * @return the artikel_sb_faehig - SB-fähig
	 */
	public boolean isArtikel_sb_faehigAsPrimitive()
	{
		return isArtikel_sb_faehigAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_sb_faehig</code> attribute. 
	 * @param value the artikel_sb_faehig - SB-fähig
	 */
	public void setArtikel_sb_faehig(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, ARTIKEL_SB_FAEHIG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_sb_faehig</code> attribute. 
	 * @param value the artikel_sb_faehig - SB-fähig
	 */
	public void setArtikel_sb_faehig(final Boolean value)
	{
		setArtikel_sb_faehig( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_sb_faehig</code> attribute. 
	 * @param value the artikel_sb_faehig - SB-fähig
	 */
	public void setArtikel_sb_faehig(final SessionContext ctx, final boolean value)
	{
		setArtikel_sb_faehig( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikel_sb_faehig</code> attribute. 
	 * @param value the artikel_sb_faehig - SB-fähig
	 */
	public void setArtikel_sb_faehig(final boolean value)
	{
		setArtikel_sb_faehig( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikelnr_index</code> attribute.
	 * @return the artikelnr_index
	 */
	public String getArtikelnr_index(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.getArtikelnr_index requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, ARTIKELNR_INDEX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikelnr_index</code> attribute.
	 * @return the artikelnr_index
	 */
	public String getArtikelnr_index()
	{
		return getArtikelnr_index( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikelnr_index</code> attribute. 
	 * @return the localized artikelnr_index
	 */
	public Map<Language,String> getAllArtikelnr_index(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,ARTIKELNR_INDEX,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.artikelnr_index</code> attribute. 
	 * @return the localized artikelnr_index
	 */
	public Map<Language,String> getAllArtikelnr_index()
	{
		return getAllArtikelnr_index( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikelnr_index</code> attribute. 
	 * @param value the artikelnr_index
	 */
	public void setArtikelnr_index(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.setArtikelnr_index requires a session language", 0 );
		}
		setLocalizedProperty(ctx, ARTIKELNR_INDEX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikelnr_index</code> attribute. 
	 * @param value the artikelnr_index
	 */
	public void setArtikelnr_index(final String value)
	{
		setArtikelnr_index( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikelnr_index</code> attribute. 
	 * @param value the artikelnr_index
	 */
	public void setAllArtikelnr_index(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,ARTIKELNR_INDEX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.artikelnr_index</code> attribute. 
	 * @param value the artikelnr_index
	 */
	public void setAllArtikelnr_index(final Map<Language,String> value)
	{
		setAllArtikelnr_index( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.bulletpoints_print_selection</code> attribute.
	 * @return the bulletpoints_print_selection
	 */
	public String getBulletpoints_print_selection(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BULLETPOINTS_PRINT_SELECTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.bulletpoints_print_selection</code> attribute.
	 * @return the bulletpoints_print_selection
	 */
	public String getBulletpoints_print_selection()
	{
		return getBulletpoints_print_selection( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.bulletpoints_print_selection</code> attribute. 
	 * @param value the bulletpoints_print_selection
	 */
	public void setBulletpoints_print_selection(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BULLETPOINTS_PRINT_SELECTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.bulletpoints_print_selection</code> attribute. 
	 * @param value the bulletpoints_print_selection
	 */
	public void setBulletpoints_print_selection(final String value)
	{
		setBulletpoints_print_selection( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.bulletpoints_print_selection_preview</code> attribute.
	 * @return the bulletpoints_print_selection_preview - Name der Mediadatei
	 */
	public abstract String getBulletpoints_print_selection_preview(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.bulletpoints_print_selection_preview</code> attribute.
	 * @return the bulletpoints_print_selection_preview - Name der Mediadatei
	 */
	public String getBulletpoints_print_selection_preview()
	{
		return getBulletpoints_print_selection_preview( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.category2productexts</code> attribute.
	 * @return the category2productexts
	 */
	public Collection<Category2ProductExt> getCategory2productexts(final SessionContext ctx)
	{
		Collection<Category2ProductExt> coll = (Collection<Category2ProductExt>)getProperty( ctx, CATEGORY2PRODUCTEXTS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.category2productexts</code> attribute.
	 * @return the category2productexts
	 */
	public Collection<Category2ProductExt> getCategory2productexts()
	{
		return getCategory2productexts( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.category2productexts</code> attribute. 
	 * @param value the category2productexts
	 */
	public void setCategory2productexts(final SessionContext ctx, final Collection<Category2ProductExt> value)
	{
		setProperty(ctx, CATEGORY2PRODUCTEXTS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.category2productexts</code> attribute. 
	 * @param value the category2productexts
	 */
	public void setCategory2productexts(final Collection<Category2ProductExt> value)
	{
		setCategory2productexts( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.check_artikelnr_index</code> attribute.
	 * @return the check_artikelnr_index - Name der Mediadatei
	 */
	public abstract String getCheck_artikelnr_index(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.check_artikelnr_index</code> attribute.
	 * @return the check_artikelnr_index - Name der Mediadatei
	 */
	public String getCheck_artikelnr_index()
	{
		return getCheck_artikelnr_index( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.desc_picture1</code> attribute.
	 * @return the desc_picture1 - Name der Mediadatei
	 */
	public abstract String getDesc_picture1(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.desc_picture1</code> attribute.
	 * @return the desc_picture1 - Name der Mediadatei
	 */
	public String getDesc_picture1()
	{
		return getDesc_picture1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description1</code> attribute.
	 * @return the description1
	 */
	public String getDescription1(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.getDescription1 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description1</code> attribute.
	 * @return the description1
	 */
	public String getDescription1()
	{
		return getDescription1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description1</code> attribute. 
	 * @return the localized description1
	 */
	public Map<Language,String> getAllDescription1(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION1,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description1</code> attribute. 
	 * @return the localized description1
	 */
	public Map<Language,String> getAllDescription1()
	{
		return getAllDescription1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description1</code> attribute. 
	 * @param value the description1
	 */
	public void setDescription1(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.setDescription1 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description1</code> attribute. 
	 * @param value the description1
	 */
	public void setDescription1(final String value)
	{
		setDescription1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description1</code> attribute. 
	 * @param value the description1
	 */
	public void setAllDescription1(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description1</code> attribute. 
	 * @param value the description1
	 */
	public void setAllDescription1(final Map<Language,String> value)
	{
		setAllDescription1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description2</code> attribute.
	 * @return the description2
	 */
	public String getDescription2(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.getDescription2 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description2</code> attribute.
	 * @return the description2
	 */
	public String getDescription2()
	{
		return getDescription2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description2</code> attribute. 
	 * @return the localized description2
	 */
	public Map<Language,String> getAllDescription2(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION2,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description2</code> attribute. 
	 * @return the localized description2
	 */
	public Map<Language,String> getAllDescription2()
	{
		return getAllDescription2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description2</code> attribute. 
	 * @param value the description2
	 */
	public void setDescription2(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.setDescription2 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description2</code> attribute. 
	 * @param value the description2
	 */
	public void setDescription2(final String value)
	{
		setDescription2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description2</code> attribute. 
	 * @param value the description2
	 */
	public void setAllDescription2(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description2</code> attribute. 
	 * @param value the description2
	 */
	public void setAllDescription2(final Map<Language,String> value)
	{
		setAllDescription2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description3</code> attribute.
	 * @return the description3
	 */
	public String getDescription3(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.getDescription3 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, DESCRIPTION3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description3</code> attribute.
	 * @return the description3
	 */
	public String getDescription3()
	{
		return getDescription3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description3</code> attribute. 
	 * @return the localized description3
	 */
	public Map<Language,String> getAllDescription3(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,DESCRIPTION3,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.description3</code> attribute. 
	 * @return the localized description3
	 */
	public Map<Language,String> getAllDescription3()
	{
		return getAllDescription3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description3</code> attribute. 
	 * @param value the description3
	 */
	public void setDescription3(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProduct.setDescription3 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, DESCRIPTION3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description3</code> attribute. 
	 * @param value the description3
	 */
	public void setDescription3(final String value)
	{
		setDescription3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description3</code> attribute. 
	 * @param value the description3
	 */
	public void setAllDescription3(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,DESCRIPTION3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.description3</code> attribute. 
	 * @param value the description3
	 */
	public void setAllDescription3(final Map<Language,String> value)
	{
		setAllDescription3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.extimages</code> attribute.
	 * @return the extimages
	 */
	public Collection<ExtImage> getExtimages(final SessionContext ctx)
	{
		final List<ExtImage> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.extimages</code> attribute.
	 * @return the extimages
	 */
	public Collection<ExtImage> getExtimages()
	{
		return getExtimages( getSession().getSessionContext() );
	}
	
	public long getExtimagesCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null
		);
	}
	
	public long getExtimagesCount()
	{
		return getExtimagesCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.extimages</code> attribute. 
	 * @param value the extimages
	 */
	public void setExtimages(final SessionContext ctx, final Collection<ExtImage> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.extimages</code> attribute. 
	 * @param value the extimages
	 */
	public void setExtimages(final Collection<ExtImage> value)
	{
		setExtimages( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to extimages. 
	 * @param value the item to add to extimages
	 */
	public void addToExtimages(final SessionContext ctx, final ExtImage value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to extimages. 
	 * @param value the item to add to extimages
	 */
	public void addToExtimages(final ExtImage value)
	{
		addToExtimages( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from extimages. 
	 * @param value the item to remove from extimages
	 */
	public void removeFromExtimages(final SessionContext ctx, final ExtImage value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTEXTIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from extimages. 
	 * @param value the item to remove from extimages
	 */
	public void removeFromExtimages(final ExtImage value)
	{
		removeFromExtimages( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.featureicons1</code> attribute.
	 * @return the featureicons1
	 */
	public Collection<WeraMedia> getFeatureicons1(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.featureicons1</code> attribute.
	 * @return the featureicons1
	 */
	public Collection<WeraMedia> getFeatureicons1()
	{
		return getFeatureicons1( getSession().getSessionContext() );
	}
	
	public long getFeatureicons1Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null
		);
	}
	
	public long getFeatureicons1Count()
	{
		return getFeatureicons1Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.featureicons1</code> attribute. 
	 * @param value the featureicons1
	 */
	public void setFeatureicons1(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.featureicons1</code> attribute. 
	 * @param value the featureicons1
	 */
	public void setFeatureicons1(final Collection<WeraMedia> value)
	{
		setFeatureicons1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to featureicons1. 
	 * @param value the item to add to featureicons1
	 */
	public void addToFeatureicons1(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to featureicons1. 
	 * @param value the item to add to featureicons1
	 */
	public void addToFeatureicons1(final WeraMedia value)
	{
		addToFeatureicons1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from featureicons1. 
	 * @param value the item to remove from featureicons1
	 */
	public void removeFromFeatureicons1(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from featureicons1. 
	 * @param value the item to remove from featureicons1
	 */
	public void removeFromFeatureicons1(final WeraMedia value)
	{
		removeFromFeatureicons1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.featureicons2</code> attribute.
	 * @return the featureicons2
	 */
	public Collection<WeraMedia> getFeatureicons2(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.featureicons2</code> attribute.
	 * @return the featureicons2
	 */
	public Collection<WeraMedia> getFeatureicons2()
	{
		return getFeatureicons2( getSession().getSessionContext() );
	}
	
	public long getFeatureicons2Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null
		);
	}
	
	public long getFeatureicons2Count()
	{
		return getFeatureicons2Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.featureicons2</code> attribute. 
	 * @param value the featureicons2
	 */
	public void setFeatureicons2(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.featureicons2</code> attribute. 
	 * @param value the featureicons2
	 */
	public void setFeatureicons2(final Collection<WeraMedia> value)
	{
		setFeatureicons2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to featureicons2. 
	 * @param value the item to add to featureicons2
	 */
	public void addToFeatureicons2(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to featureicons2. 
	 * @param value the item to add to featureicons2
	 */
	public void addToFeatureicons2(final WeraMedia value)
	{
		addToFeatureicons2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from featureicons2. 
	 * @param value the item to remove from featureicons2
	 */
	public void removeFromFeatureicons2(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTFEATUREICON2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from featureicons2. 
	 * @param value the item to remove from featureicons2
	 */
	public void removeFromFeatureicons2(final WeraMedia value)
	{
		removeFromFeatureicons2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.footnotes</code> attribute.
	 * @return the footnotes
	 */
	public Collection<Footnote> getFootnotes(final SessionContext ctx)
	{
		final List<Footnote> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.footnotes</code> attribute.
	 * @return the footnotes
	 */
	public Collection<Footnote> getFootnotes()
	{
		return getFootnotes( getSession().getSessionContext() );
	}
	
	public long getFootnotesCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
			null
		);
	}
	
	public long getFootnotesCount()
	{
		return getFootnotesCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.footnotes</code> attribute. 
	 * @param value the footnotes
	 */
	public void setFootnotes(final SessionContext ctx, final Collection<Footnote> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.footnotes</code> attribute. 
	 * @param value the footnotes
	 */
	public void setFootnotes(final Collection<Footnote> value)
	{
		setFootnotes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to footnotes. 
	 * @param value the item to add to footnotes
	 */
	public void addToFootnotes(final SessionContext ctx, final Footnote value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to footnotes. 
	 * @param value the item to add to footnotes
	 */
	public void addToFootnotes(final Footnote value)
	{
		addToFootnotes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from footnotes. 
	 * @param value the item to remove from footnotes
	 */
	public void removeFromFootnotes(final SessionContext ctx, final Footnote value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTFOOTNOTERELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from footnotes. 
	 * @param value the item to remove from footnotes
	 */
	public void removeFromFootnotes(final Footnote value)
	{
		removeFromFootnotes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.great_tools_basename</code> attribute.
	 * @return the great_tools_basename
	 */
	public String getGreat_tools_basename(final SessionContext ctx)
	{
		return (String)getProperty( ctx, GREAT_TOOLS_BASENAME);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.great_tools_basename</code> attribute.
	 * @return the great_tools_basename
	 */
	public String getGreat_tools_basename()
	{
		return getGreat_tools_basename( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.great_tools_basename</code> attribute. 
	 * @param value the great_tools_basename
	 */
	public void setGreat_tools_basename(final SessionContext ctx, final String value)
	{
		setProperty(ctx, GREAT_TOOLS_BASENAME,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.great_tools_basename</code> attribute. 
	 * @param value the great_tools_basename
	 */
	public void setGreat_tools_basename(final String value)
	{
		setGreat_tools_basename( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.icons1</code> attribute.
	 * @return the icons1
	 */
	public Collection<WeraMedia> getIcons1(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.icons1</code> attribute.
	 * @return the icons1
	 */
	public Collection<WeraMedia> getIcons1()
	{
		return getIcons1( getSession().getSessionContext() );
	}
	
	public long getIcons1Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null
		);
	}
	
	public long getIcons1Count()
	{
		return getIcons1Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.icons1</code> attribute. 
	 * @param value the icons1
	 */
	public void setIcons1(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.icons1</code> attribute. 
	 * @param value the icons1
	 */
	public void setIcons1(final Collection<WeraMedia> value)
	{
		setIcons1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icons1. 
	 * @param value the item to add to icons1
	 */
	public void addToIcons1(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icons1. 
	 * @param value the item to add to icons1
	 */
	public void addToIcons1(final WeraMedia value)
	{
		addToIcons1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icons1. 
	 * @param value the item to remove from icons1
	 */
	public void removeFromIcons1(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icons1. 
	 * @param value the item to remove from icons1
	 */
	public void removeFromIcons1(final WeraMedia value)
	{
		removeFromIcons1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.icons2</code> attribute.
	 * @return the icons2
	 */
	public Collection<WeraMedia> getIcons2(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.icons2</code> attribute.
	 * @return the icons2
	 */
	public Collection<WeraMedia> getIcons2()
	{
		return getIcons2( getSession().getSessionContext() );
	}
	
	public long getIcons2Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null
		);
	}
	
	public long getIcons2Count()
	{
		return getIcons2Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.icons2</code> attribute. 
	 * @param value the icons2
	 */
	public void setIcons2(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.icons2</code> attribute. 
	 * @param value the icons2
	 */
	public void setIcons2(final Collection<WeraMedia> value)
	{
		setIcons2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icons2. 
	 * @param value the item to add to icons2
	 */
	public void addToIcons2(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to icons2. 
	 * @param value the item to add to icons2
	 */
	public void addToIcons2(final WeraMedia value)
	{
		addToIcons2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icons2. 
	 * @param value the item to remove from icons2
	 */
	public void removeFromIcons2(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTICON2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from icons2. 
	 * @param value the item to remove from icons2
	 */
	public void removeFromIcons2(final WeraMedia value)
	{
		removeFromIcons2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.masterpk</code> attribute.
	 * @return the masterpk
	 */
	public String getMasterpk(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MASTERPK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.masterpk</code> attribute.
	 * @return the masterpk
	 */
	public String getMasterpk()
	{
		return getMasterpk( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.masterpk</code> attribute. 
	 * @param value the masterpk
	 */
	public void setMasterpk(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MASTERPK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.masterpk</code> attribute. 
	 * @param value the masterpk
	 */
	public void setMasterpk(final String value)
	{
		setMasterpk( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.orderPL</code> attribute.
	 * @return the orderPL - Reihenfolge Preisliste
	 */
	public Integer getOrderPL(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, ORDERPL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.orderPL</code> attribute.
	 * @return the orderPL - Reihenfolge Preisliste
	 */
	public Integer getOrderPL()
	{
		return getOrderPL( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.orderPL</code> attribute. 
	 * @return the orderPL - Reihenfolge Preisliste
	 */
	public int getOrderPLAsPrimitive(final SessionContext ctx)
	{
		Integer value = getOrderPL( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.orderPL</code> attribute. 
	 * @return the orderPL - Reihenfolge Preisliste
	 */
	public int getOrderPLAsPrimitive()
	{
		return getOrderPLAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.orderPL</code> attribute. 
	 * @param value the orderPL - Reihenfolge Preisliste
	 */
	public void setOrderPL(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, ORDERPL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.orderPL</code> attribute. 
	 * @param value the orderPL - Reihenfolge Preisliste
	 */
	public void setOrderPL(final Integer value)
	{
		setOrderPL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.orderPL</code> attribute. 
	 * @param value the orderPL - Reihenfolge Preisliste
	 */
	public void setOrderPL(final SessionContext ctx, final int value)
	{
		setOrderPL( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.orderPL</code> attribute. 
	 * @param value the orderPL - Reihenfolge Preisliste
	 */
	public void setOrderPL(final int value)
	{
		setOrderPL( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.others_productpictures</code> attribute.
	 * @return the others_productpictures
	 */
	public Collection<WeraMedia> getOthers_productpictures(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.others_productpictures</code> attribute.
	 * @return the others_productpictures
	 */
	public Collection<WeraMedia> getOthers_productpictures()
	{
		return getOthers_productpictures( getSession().getSessionContext() );
	}
	
	public long getOthers_productpicturesCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null
		);
	}
	
	public long getOthers_productpicturesCount()
	{
		return getOthers_productpicturesCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.others_productpictures</code> attribute. 
	 * @param value the others_productpictures
	 */
	public void setOthers_productpictures(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.others_productpictures</code> attribute. 
	 * @param value the others_productpictures
	 */
	public void setOthers_productpictures(final Collection<WeraMedia> value)
	{
		setOthers_productpictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to others_productpictures. 
	 * @param value the item to add to others_productpictures
	 */
	public void addToOthers_productpictures(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to others_productpictures. 
	 * @param value the item to add to others_productpictures
	 */
	public void addToOthers_productpictures(final WeraMedia value)
	{
		addToOthers_productpictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from others_productpictures. 
	 * @param value the item to remove from others_productpictures
	 */
	public void removeFromOthers_productpictures(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURES2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from others_productpictures. 
	 * @param value the item to remove from others_productpictures
	 */
	public void removeFromOthers_productpictures(final WeraMedia value)
	{
		removeFromOthers_productpictures( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.output_category_titel</code> attribute.
	 * @return the output_category_titel - Ausgabe des Kategoriettitels anstelle des Produktnames
	 */
	public Boolean isOutput_category_titel(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, OUTPUT_CATEGORY_TITEL);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.output_category_titel</code> attribute.
	 * @return the output_category_titel - Ausgabe des Kategoriettitels anstelle des Produktnames
	 */
	public Boolean isOutput_category_titel()
	{
		return isOutput_category_titel( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.output_category_titel</code> attribute. 
	 * @return the output_category_titel - Ausgabe des Kategoriettitels anstelle des Produktnames
	 */
	public boolean isOutput_category_titelAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isOutput_category_titel( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.output_category_titel</code> attribute. 
	 * @return the output_category_titel - Ausgabe des Kategoriettitels anstelle des Produktnames
	 */
	public boolean isOutput_category_titelAsPrimitive()
	{
		return isOutput_category_titelAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.output_category_titel</code> attribute. 
	 * @param value the output_category_titel - Ausgabe des Kategoriettitels anstelle des Produktnames
	 */
	public void setOutput_category_titel(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, OUTPUT_CATEGORY_TITEL,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.output_category_titel</code> attribute. 
	 * @param value the output_category_titel - Ausgabe des Kategoriettitels anstelle des Produktnames
	 */
	public void setOutput_category_titel(final Boolean value)
	{
		setOutput_category_titel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.output_category_titel</code> attribute. 
	 * @param value the output_category_titel - Ausgabe des Kategoriettitels anstelle des Produktnames
	 */
	public void setOutput_category_titel(final SessionContext ctx, final boolean value)
	{
		setOutput_category_titel( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.output_category_titel</code> attribute. 
	 * @param value the output_category_titel - Ausgabe des Kategoriettitels anstelle des Produktnames
	 */
	public void setOutput_category_titel(final boolean value)
	{
		setOutput_category_titel( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.outputcontrols</code> attribute.
	 * @return the outputcontrols
	 */
	public Collection<Outputcontrol> getOutputcontrols(final SessionContext ctx)
	{
		Collection<Outputcontrol> coll = (Collection<Outputcontrol>)getProperty( ctx, OUTPUTCONTROLS);
		return coll != null ? coll : Collections.EMPTY_LIST;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.outputcontrols</code> attribute.
	 * @return the outputcontrols
	 */
	public Collection<Outputcontrol> getOutputcontrols()
	{
		return getOutputcontrols( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.outputcontrols</code> attribute. 
	 * @param value the outputcontrols
	 */
	public void setOutputcontrols(final SessionContext ctx, final Collection<Outputcontrol> value)
	{
		setProperty(ctx, OUTPUTCONTROLS,value == null || !value.isEmpty() ? value : null );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.outputcontrols</code> attribute. 
	 * @param value the outputcontrols
	 */
	public void setOutputcontrols(final Collection<Outputcontrol> value)
	{
		setOutputcontrols( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.outputtemplate</code> attribute.
	 * @return the outputtemplate - Output Template
	 */
	public String getOutputtemplate(final SessionContext ctx)
	{
		return (String)getProperty( ctx, OUTPUTTEMPLATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.outputtemplate</code> attribute.
	 * @return the outputtemplate - Output Template
	 */
	public String getOutputtemplate()
	{
		return getOutputtemplate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.outputtemplate</code> attribute. 
	 * @param value the outputtemplate - Output Template
	 */
	public void setOutputtemplate(final SessionContext ctx, final String value)
	{
		setProperty(ctx, OUTPUTTEMPLATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.outputtemplate</code> attribute. 
	 * @param value the outputtemplate - Output Template
	 */
	public void setOutputtemplate(final String value)
	{
		setOutputtemplate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.pictures1</code> attribute.
	 * @return the pictures1
	 */
	public Collection<WeraMedia> getPictures1(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.pictures1</code> attribute.
	 * @return the pictures1
	 */
	public Collection<WeraMedia> getPictures1()
	{
		return getPictures1( getSession().getSessionContext() );
	}
	
	public long getPictures1Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null
		);
	}
	
	public long getPictures1Count()
	{
		return getPictures1Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.pictures1</code> attribute. 
	 * @param value the pictures1
	 */
	public void setPictures1(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.pictures1</code> attribute. 
	 * @param value the pictures1
	 */
	public void setPictures1(final Collection<WeraMedia> value)
	{
		setPictures1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pictures1. 
	 * @param value the item to add to pictures1
	 */
	public void addToPictures1(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pictures1. 
	 * @param value the item to add to pictures1
	 */
	public void addToPictures1(final WeraMedia value)
	{
		addToPictures1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pictures1. 
	 * @param value the item to remove from pictures1
	 */
	public void removeFromPictures1(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE1RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pictures1. 
	 * @param value the item to remove from pictures1
	 */
	public void removeFromPictures1(final WeraMedia value)
	{
		removeFromPictures1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.pictures2</code> attribute.
	 * @return the pictures2
	 */
	public Collection<WeraMedia> getPictures2(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.pictures2</code> attribute.
	 * @return the pictures2
	 */
	public Collection<WeraMedia> getPictures2()
	{
		return getPictures2( getSession().getSessionContext() );
	}
	
	public long getPictures2Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null
		);
	}
	
	public long getPictures2Count()
	{
		return getPictures2Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.pictures2</code> attribute. 
	 * @param value the pictures2
	 */
	public void setPictures2(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.pictures2</code> attribute. 
	 * @param value the pictures2
	 */
	public void setPictures2(final Collection<WeraMedia> value)
	{
		setPictures2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pictures2. 
	 * @param value the item to add to pictures2
	 */
	public void addToPictures2(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pictures2. 
	 * @param value the item to add to pictures2
	 */
	public void addToPictures2(final WeraMedia value)
	{
		addToPictures2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pictures2. 
	 * @param value the item to remove from pictures2
	 */
	public void removeFromPictures2(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE2RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pictures2. 
	 * @param value the item to remove from pictures2
	 */
	public void removeFromPictures2(final WeraMedia value)
	{
		removeFromPictures2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.pictures3</code> attribute.
	 * @return the pictures3
	 */
	public Collection<WeraMedia> getPictures3(final SessionContext ctx)
	{
		final List<WeraMedia> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.pictures3</code> attribute.
	 * @return the pictures3
	 */
	public Collection<WeraMedia> getPictures3()
	{
		return getPictures3( getSession().getSessionContext() );
	}
	
	public long getPictures3Count(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null
		);
	}
	
	public long getPictures3Count()
	{
		return getPictures3Count( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.pictures3</code> attribute. 
	 * @param value the pictures3
	 */
	public void setPictures3(final SessionContext ctx, final Collection<WeraMedia> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.pictures3</code> attribute. 
	 * @param value the pictures3
	 */
	public void setPictures3(final Collection<WeraMedia> value)
	{
		setPictures3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pictures3. 
	 * @param value the item to add to pictures3
	 */
	public void addToPictures3(final SessionContext ctx, final WeraMedia value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to pictures3. 
	 * @param value the item to add to pictures3
	 */
	public void addToPictures3(final WeraMedia value)
	{
		addToPictures3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pictures3. 
	 * @param value the item to remove from pictures3
	 */
	public void removeFromPictures3(final SessionContext ctx, final WeraMedia value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.PRODUCTPICTURE3RELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from pictures3. 
	 * @param value the item to remove from pictures3
	 */
	public void removeFromPictures3(final WeraMedia value)
	{
		removeFromPictures3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.priority</code> attribute.
	 * @return the priority - Reihenfolge Variante
	 */
	public Double getPriority(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, PRIORITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.priority</code> attribute.
	 * @return the priority - Reihenfolge Variante
	 */
	public Double getPriority()
	{
		return getPriority( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.priority</code> attribute. 
	 * @return the priority - Reihenfolge Variante
	 */
	public double getPriorityAsPrimitive(final SessionContext ctx)
	{
		Double value = getPriority( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.priority</code> attribute. 
	 * @return the priority - Reihenfolge Variante
	 */
	public double getPriorityAsPrimitive()
	{
		return getPriorityAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.priority</code> attribute. 
	 * @param value the priority - Reihenfolge Variante
	 */
	public void setPriority(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, PRIORITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.priority</code> attribute. 
	 * @param value the priority - Reihenfolge Variante
	 */
	public void setPriority(final Double value)
	{
		setPriority( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.priority</code> attribute. 
	 * @param value the priority - Reihenfolge Variante
	 */
	public void setPriority(final SessionContext ctx, final double value)
	{
		setPriority( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.priority</code> attribute. 
	 * @param value the priority - Reihenfolge Variante
	 */
	public void setPriority(final double value)
	{
		setPriority( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.priorityWebSearch</code> attribute.
	 * @return the priorityWebSearch - Such-Priorität Web
	 */
	public Integer getPriorityWebSearch(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, PRIORITYWEBSEARCH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.priorityWebSearch</code> attribute.
	 * @return the priorityWebSearch - Such-Priorität Web
	 */
	public Integer getPriorityWebSearch()
	{
		return getPriorityWebSearch( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.priorityWebSearch</code> attribute. 
	 * @return the priorityWebSearch - Such-Priorität Web
	 */
	public int getPriorityWebSearchAsPrimitive(final SessionContext ctx)
	{
		Integer value = getPriorityWebSearch( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.priorityWebSearch</code> attribute. 
	 * @return the priorityWebSearch - Such-Priorität Web
	 */
	public int getPriorityWebSearchAsPrimitive()
	{
		return getPriorityWebSearchAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.priorityWebSearch</code> attribute. 
	 * @param value the priorityWebSearch - Such-Priorität Web
	 */
	public void setPriorityWebSearch(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, PRIORITYWEBSEARCH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.priorityWebSearch</code> attribute. 
	 * @param value the priorityWebSearch - Such-Priorität Web
	 */
	public void setPriorityWebSearch(final Integer value)
	{
		setPriorityWebSearch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.priorityWebSearch</code> attribute. 
	 * @param value the priorityWebSearch - Such-Priorität Web
	 */
	public void setPriorityWebSearch(final SessionContext ctx, final int value)
	{
		setPriorityWebSearch( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.priorityWebSearch</code> attribute. 
	 * @param value the priorityWebSearch - Such-Priorität Web
	 */
	public void setPriorityWebSearch(final int value)
	{
		setPriorityWebSearch( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.product2relcategoriesreverse</code> attribute.
	 * @return the product2relcategoriesreverse
	 */
	public Collection<Category> getProduct2relcategoriesreverse(final SessionContext ctx)
	{
		final List<Category> items = getLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.product2relcategoriesreverse</code> attribute.
	 * @return the product2relcategoriesreverse
	 */
	public Collection<Category> getProduct2relcategoriesreverse()
	{
		return getProduct2relcategoriesreverse( getSession().getSessionContext() );
	}
	
	public long getProduct2relcategoriesreverseCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null
		);
	}
	
	public long getProduct2relcategoriesreverseCount()
	{
		return getProduct2relcategoriesreverseCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.product2relcategoriesreverse</code> attribute. 
	 * @param value the product2relcategoriesreverse
	 */
	public void setProduct2relcategoriesreverse(final SessionContext ctx, final Collection<Category> value)
	{
		setLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.product2relcategoriesreverse</code> attribute. 
	 * @param value the product2relcategoriesreverse
	 */
	public void setProduct2relcategoriesreverse(final Collection<Category> value)
	{
		setProduct2relcategoriesreverse( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to product2relcategoriesreverse. 
	 * @param value the item to add to product2relcategoriesreverse
	 */
	public void addToProduct2relcategoriesreverse(final SessionContext ctx, final Category value)
	{
		addLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to product2relcategoriesreverse. 
	 * @param value the item to add to product2relcategoriesreverse
	 */
	public void addToProduct2relcategoriesreverse(final Category value)
	{
		addToProduct2relcategoriesreverse( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from product2relcategoriesreverse. 
	 * @param value the item to remove from product2relcategoriesreverse
	 */
	public void removeFromProduct2relcategoriesreverse(final SessionContext ctx, final Category value)
	{
		removeLinkedItems( 
			ctx,
			false,
			WeraConstants.Relations.CATEGORYRELATEDWERAPRODUCTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from product2relcategoriesreverse. 
	 * @param value the item to remove from product2relcategoriesreverse
	 */
	public void removeFromProduct2relcategoriesreverse(final Category value)
	{
		removeFromProduct2relcategoriesreverse( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.produkt_neu</code> attribute.
	 * @return the produkt_neu - Neues Produkt
	 */
	public Boolean isProdukt_neu(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, PRODUKT_NEU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.produkt_neu</code> attribute.
	 * @return the produkt_neu - Neues Produkt
	 */
	public Boolean isProdukt_neu()
	{
		return isProdukt_neu( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.produkt_neu</code> attribute. 
	 * @return the produkt_neu - Neues Produkt
	 */
	public boolean isProdukt_neuAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isProdukt_neu( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.produkt_neu</code> attribute. 
	 * @return the produkt_neu - Neues Produkt
	 */
	public boolean isProdukt_neuAsPrimitive()
	{
		return isProdukt_neuAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.produkt_neu</code> attribute. 
	 * @param value the produkt_neu - Neues Produkt
	 */
	public void setProdukt_neu(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, PRODUKT_NEU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.produkt_neu</code> attribute. 
	 * @param value the produkt_neu - Neues Produkt
	 */
	public void setProdukt_neu(final Boolean value)
	{
		setProdukt_neu( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.produkt_neu</code> attribute. 
	 * @param value the produkt_neu - Neues Produkt
	 */
	public void setProdukt_neu(final SessionContext ctx, final boolean value)
	{
		setProdukt_neu( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.produkt_neu</code> attribute. 
	 * @param value the produkt_neu - Neues Produkt
	 */
	public void setProdukt_neu(final boolean value)
	{
		setProdukt_neu( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.sb_bulletpoints_print_selection</code> attribute.
	 * @return the sb_bulletpoints_print_selection
	 */
	public String getSb_bulletpoints_print_selection(final SessionContext ctx)
	{
		return (String)getProperty( ctx, SB_BULLETPOINTS_PRINT_SELECTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.sb_bulletpoints_print_selection</code> attribute.
	 * @return the sb_bulletpoints_print_selection
	 */
	public String getSb_bulletpoints_print_selection()
	{
		return getSb_bulletpoints_print_selection( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.sb_bulletpoints_print_selection</code> attribute. 
	 * @param value the sb_bulletpoints_print_selection
	 */
	public void setSb_bulletpoints_print_selection(final SessionContext ctx, final String value)
	{
		setProperty(ctx, SB_BULLETPOINTS_PRINT_SELECTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.sb_bulletpoints_print_selection</code> attribute. 
	 * @param value the sb_bulletpoints_print_selection
	 */
	public void setSb_bulletpoints_print_selection(final String value)
	{
		setSb_bulletpoints_print_selection( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.secondaryimagesref2weraproduct</code> attribute.
	 * @return the secondaryimagesref2weraproduct
	 */
	public Collection<Bildreferenz> getSecondaryimagesref2weraproduct(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.secondaryimagesref2weraproduct</code> attribute.
	 * @return the secondaryimagesref2weraproduct
	 */
	public Collection<Bildreferenz> getSecondaryimagesref2weraproduct()
	{
		return getSecondaryimagesref2weraproduct( getSession().getSessionContext() );
	}
	
	public long getSecondaryimagesref2weraproductCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null
		);
	}
	
	public long getSecondaryimagesref2weraproductCount()
	{
		return getSecondaryimagesref2weraproductCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.secondaryimagesref2weraproduct</code> attribute. 
	 * @param value the secondaryimagesref2weraproduct
	 */
	public void setSecondaryimagesref2weraproduct(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.secondaryimagesref2weraproduct</code> attribute. 
	 * @param value the secondaryimagesref2weraproduct
	 */
	public void setSecondaryimagesref2weraproduct(final Collection<Bildreferenz> value)
	{
		setSecondaryimagesref2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to secondaryimagesref2weraproduct. 
	 * @param value the item to add to secondaryimagesref2weraproduct
	 */
	public void addToSecondaryimagesref2weraproduct(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to secondaryimagesref2weraproduct. 
	 * @param value the item to add to secondaryimagesref2weraproduct
	 */
	public void addToSecondaryimagesref2weraproduct(final Bildreferenz value)
	{
		addToSecondaryimagesref2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from secondaryimagesref2weraproduct. 
	 * @param value the item to remove from secondaryimagesref2weraproduct
	 */
	public void removeFromSecondaryimagesref2weraproduct(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSECONDARYIMAGESRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from secondaryimagesref2weraproduct. 
	 * @param value the item to remove from secondaryimagesref2weraproduct
	 */
	public void removeFromSecondaryimagesref2weraproduct(final Bildreferenz value)
	{
		removeFromSecondaryimagesref2weraproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.sis_prioritize_code</code> attribute.
	 * @return the sis_prioritize_code - Show Code instead of Name in SiS product tables (leere Schaumstoffeinlagen)
	 */
	public Boolean isSis_prioritize_code(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SIS_PRIORITIZE_CODE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.sis_prioritize_code</code> attribute.
	 * @return the sis_prioritize_code - Show Code instead of Name in SiS product tables (leere Schaumstoffeinlagen)
	 */
	public Boolean isSis_prioritize_code()
	{
		return isSis_prioritize_code( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.sis_prioritize_code</code> attribute. 
	 * @return the sis_prioritize_code - Show Code instead of Name in SiS product tables (leere Schaumstoffeinlagen)
	 */
	public boolean isSis_prioritize_codeAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isSis_prioritize_code( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.sis_prioritize_code</code> attribute. 
	 * @return the sis_prioritize_code - Show Code instead of Name in SiS product tables (leere Schaumstoffeinlagen)
	 */
	public boolean isSis_prioritize_codeAsPrimitive()
	{
		return isSis_prioritize_codeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.sis_prioritize_code</code> attribute. 
	 * @param value the sis_prioritize_code - Show Code instead of Name in SiS product tables (leere Schaumstoffeinlagen)
	 */
	public void setSis_prioritize_code(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SIS_PRIORITIZE_CODE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.sis_prioritize_code</code> attribute. 
	 * @param value the sis_prioritize_code - Show Code instead of Name in SiS product tables (leere Schaumstoffeinlagen)
	 */
	public void setSis_prioritize_code(final Boolean value)
	{
		setSis_prioritize_code( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.sis_prioritize_code</code> attribute. 
	 * @param value the sis_prioritize_code - Show Code instead of Name in SiS product tables (leere Schaumstoffeinlagen)
	 */
	public void setSis_prioritize_code(final SessionContext ctx, final boolean value)
	{
		setSis_prioritize_code( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.sis_prioritize_code</code> attribute. 
	 * @param value the sis_prioritize_code - Show Code instead of Name in SiS product tables (leere Schaumstoffeinlagen)
	 */
	public void setSis_prioritize_code(final boolean value)
	{
		setSis_prioritize_code( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.sortimenttippref</code> attribute.
	 * @return the sortimenttippref
	 */
	public Collection<Tipp> getSortimenttippref(final SessionContext ctx)
	{
		final List<Tipp> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.sortimenttippref</code> attribute.
	 * @return the sortimenttippref
	 */
	public Collection<Tipp> getSortimenttippref()
	{
		return getSortimenttippref( getSession().getSessionContext() );
	}
	
	public long getSortimenttipprefCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null
		);
	}
	
	public long getSortimenttipprefCount()
	{
		return getSortimenttipprefCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.sortimenttippref</code> attribute. 
	 * @param value the sortimenttippref
	 */
	public void setSortimenttippref(final SessionContext ctx, final Collection<Tipp> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.sortimenttippref</code> attribute. 
	 * @param value the sortimenttippref
	 */
	public void setSortimenttippref(final Collection<Tipp> value)
	{
		setSortimenttippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to sortimenttippref. 
	 * @param value the item to add to sortimenttippref
	 */
	public void addToSortimenttippref(final SessionContext ctx, final Tipp value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to sortimenttippref. 
	 * @param value the item to add to sortimenttippref
	 */
	public void addToSortimenttippref(final Tipp value)
	{
		addToSortimenttippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from sortimenttippref. 
	 * @param value the item to remove from sortimenttippref
	 */
	public void removeFromSortimenttippref(final SessionContext ctx, final Tipp value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSORTIMENTTIPPRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from sortimenttippref. 
	 * @param value the item to remove from sortimenttippref
	 */
	public void removeFromSortimenttippref(final Tipp value)
	{
		removeFromSortimenttippref( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.typeofproduct</code> attribute.
	 * @return the typeofproduct
	 */
	public Collection<Textbaustein> getTypeofproduct(final SessionContext ctx)
	{
		final List<Textbaustein> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.typeofproduct</code> attribute.
	 * @return the typeofproduct
	 */
	public Collection<Textbaustein> getTypeofproduct()
	{
		return getTypeofproduct( getSession().getSessionContext() );
	}
	
	public long getTypeofproductCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null
		);
	}
	
	public long getTypeofproductCount()
	{
		return getTypeofproductCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.typeofproduct</code> attribute. 
	 * @param value the typeofproduct
	 */
	public void setTypeofproduct(final SessionContext ctx, final Collection<Textbaustein> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.typeofproduct</code> attribute. 
	 * @param value the typeofproduct
	 */
	public void setTypeofproduct(final Collection<Textbaustein> value)
	{
		setTypeofproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to typeofproduct. 
	 * @param value the item to add to typeofproduct
	 */
	public void addToTypeofproduct(final SessionContext ctx, final Textbaustein value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to typeofproduct. 
	 * @param value the item to add to typeofproduct
	 */
	public void addToTypeofproduct(final Textbaustein value)
	{
		addToTypeofproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from typeofproduct. 
	 * @param value the item to remove from typeofproduct
	 */
	public void removeFromTypeofproduct(final SessionContext ctx, final Textbaustein value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINRELATION2,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from typeofproduct. 
	 * @param value the item to remove from typeofproduct
	 */
	public void removeFromTypeofproduct(final Textbaustein value)
	{
		removeFromTypeofproduct( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_for_greatttools</code> attribute.
	 * @return the use_for_greatttools - Great Tools Ausgabe?
	 */
	public Boolean isUse_for_greatttools(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, USE_FOR_GREATTTOOLS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_for_greatttools</code> attribute.
	 * @return the use_for_greatttools - Great Tools Ausgabe?
	 */
	public Boolean isUse_for_greatttools()
	{
		return isUse_for_greatttools( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_for_greatttools</code> attribute. 
	 * @return the use_for_greatttools - Great Tools Ausgabe?
	 */
	public boolean isUse_for_greatttoolsAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isUse_for_greatttools( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_for_greatttools</code> attribute. 
	 * @return the use_for_greatttools - Great Tools Ausgabe?
	 */
	public boolean isUse_for_greatttoolsAsPrimitive()
	{
		return isUse_for_greatttoolsAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_for_greatttools</code> attribute. 
	 * @param value the use_for_greatttools - Great Tools Ausgabe?
	 */
	public void setUse_for_greatttools(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, USE_FOR_GREATTTOOLS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_for_greatttools</code> attribute. 
	 * @param value the use_for_greatttools - Great Tools Ausgabe?
	 */
	public void setUse_for_greatttools(final Boolean value)
	{
		setUse_for_greatttools( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_for_greatttools</code> attribute. 
	 * @param value the use_for_greatttools - Great Tools Ausgabe?
	 */
	public void setUse_for_greatttools(final SessionContext ctx, final boolean value)
	{
		setUse_for_greatttools( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_for_greatttools</code> attribute. 
	 * @param value the use_for_greatttools - Great Tools Ausgabe?
	 */
	public void setUse_for_greatttools(final boolean value)
	{
		setUse_for_greatttools( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_in_catalog</code> attribute.
	 * @return the use_in_catalog - Ausgabe in Katalog J/N
	 */
	public Boolean isUse_in_catalog(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, USE_IN_CATALOG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_in_catalog</code> attribute.
	 * @return the use_in_catalog - Ausgabe in Katalog J/N
	 */
	public Boolean isUse_in_catalog()
	{
		return isUse_in_catalog( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_in_catalog</code> attribute. 
	 * @return the use_in_catalog - Ausgabe in Katalog J/N
	 */
	public boolean isUse_in_catalogAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isUse_in_catalog( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_in_catalog</code> attribute. 
	 * @return the use_in_catalog - Ausgabe in Katalog J/N
	 */
	public boolean isUse_in_catalogAsPrimitive()
	{
		return isUse_in_catalogAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_in_catalog</code> attribute. 
	 * @param value the use_in_catalog - Ausgabe in Katalog J/N
	 */
	public void setUse_in_catalog(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, USE_IN_CATALOG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_in_catalog</code> attribute. 
	 * @param value the use_in_catalog - Ausgabe in Katalog J/N
	 */
	public void setUse_in_catalog(final Boolean value)
	{
		setUse_in_catalog( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_in_catalog</code> attribute. 
	 * @param value the use_in_catalog - Ausgabe in Katalog J/N
	 */
	public void setUse_in_catalog(final SessionContext ctx, final boolean value)
	{
		setUse_in_catalog( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_in_catalog</code> attribute. 
	 * @param value the use_in_catalog - Ausgabe in Katalog J/N
	 */
	public void setUse_in_catalog(final boolean value)
	{
		setUse_in_catalog( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_in_pricelist</code> attribute.
	 * @return the use_in_pricelist - Ausgabe in Preisliste J/N
	 */
	public Boolean isUse_in_pricelist(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, USE_IN_PRICELIST);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_in_pricelist</code> attribute.
	 * @return the use_in_pricelist - Ausgabe in Preisliste J/N
	 */
	public Boolean isUse_in_pricelist()
	{
		return isUse_in_pricelist( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_in_pricelist</code> attribute. 
	 * @return the use_in_pricelist - Ausgabe in Preisliste J/N
	 */
	public boolean isUse_in_pricelistAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isUse_in_pricelist( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.use_in_pricelist</code> attribute. 
	 * @return the use_in_pricelist - Ausgabe in Preisliste J/N
	 */
	public boolean isUse_in_pricelistAsPrimitive()
	{
		return isUse_in_pricelistAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_in_pricelist</code> attribute. 
	 * @param value the use_in_pricelist - Ausgabe in Preisliste J/N
	 */
	public void setUse_in_pricelist(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, USE_IN_PRICELIST,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_in_pricelist</code> attribute. 
	 * @param value the use_in_pricelist - Ausgabe in Preisliste J/N
	 */
	public void setUse_in_pricelist(final Boolean value)
	{
		setUse_in_pricelist( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_in_pricelist</code> attribute. 
	 * @param value the use_in_pricelist - Ausgabe in Preisliste J/N
	 */
	public void setUse_in_pricelist(final SessionContext ctx, final boolean value)
	{
		setUse_in_pricelist( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.use_in_pricelist</code> attribute. 
	 * @param value the use_in_pricelist - Ausgabe in Preisliste J/N
	 */
	public void setUse_in_pricelist(final boolean value)
	{
		setUse_in_pricelist( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weblinks</code> attribute.
	 * @return the weblinks
	 */
	public Collection<Weblink> getWeblinks(final SessionContext ctx)
	{
		final List<Weblink> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTWEBLINKRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weblinks</code> attribute.
	 * @return the weblinks
	 */
	public Collection<Weblink> getWeblinks()
	{
		return getWeblinks( getSession().getSessionContext() );
	}
	
	public long getWeblinksCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTWEBLINKRELATION,
			null
		);
	}
	
	public long getWeblinksCount()
	{
		return getWeblinksCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weblinks</code> attribute. 
	 * @param value the weblinks
	 */
	public void setWeblinks(final SessionContext ctx, final Collection<Weblink> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTWEBLINKRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weblinks</code> attribute. 
	 * @param value the weblinks
	 */
	public void setWeblinks(final Collection<Weblink> value)
	{
		setWeblinks( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinks. 
	 * @param value the item to add to weblinks
	 */
	public void addToWeblinks(final SessionContext ctx, final Weblink value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTWEBLINKRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinks. 
	 * @param value the item to add to weblinks
	 */
	public void addToWeblinks(final Weblink value)
	{
		addToWeblinks( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinks. 
	 * @param value the item to remove from weblinks
	 */
	public void removeFromWeblinks(final SessionContext ctx, final Weblink value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTWEBLINKRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinks. 
	 * @param value the item to remove from weblinks
	 */
	public void removeFromWeblinks(final Weblink value)
	{
		removeFromWeblinks( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weblinks_greattools</code> attribute.
	 * @return the weblinks_greattools
	 */
	public Collection<Weblink> getWeblinks_greattools(final SessionContext ctx)
	{
		final List<Weblink> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weblinks_greattools</code> attribute.
	 * @return the weblinks_greattools
	 */
	public Collection<Weblink> getWeblinks_greattools()
	{
		return getWeblinks_greattools( getSession().getSessionContext() );
	}
	
	public long getWeblinks_greattoolsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
			null
		);
	}
	
	public long getWeblinks_greattoolsCount()
	{
		return getWeblinks_greattoolsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weblinks_greattools</code> attribute. 
	 * @param value the weblinks_greattools
	 */
	public void setWeblinks_greattools(final SessionContext ctx, final Collection<Weblink> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weblinks_greattools</code> attribute. 
	 * @param value the weblinks_greattools
	 */
	public void setWeblinks_greattools(final Collection<Weblink> value)
	{
		setWeblinks_greattools( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weblinks_greattools. 
	 * @param value the item to add to weblinks_greattools
	 */
	public void addToWeblinks_greattools(final SessionContext ctx, final Weblink value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
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
	public void addToWeblinks_greattools(final Weblink value)
	{
		addToWeblinks_greattools( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weblinks_greattools. 
	 * @param value the item to remove from weblinks_greattools
	 */
	public void removeFromWeblinks_greattools(final SessionContext ctx, final Weblink value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTGREATTOOLSRELATION,
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
	public void removeFromWeblinks_greattools(final Weblink value)
	{
		removeFromWeblinks_greattools( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weraproduct2marketing</code> attribute.
	 * @return the weraproduct2marketing
	 */
	public Collection<Textbaustein> getWeraproduct2marketing(final SessionContext ctx)
	{
		final List<Textbaustein> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weraproduct2marketing</code> attribute.
	 * @return the weraproduct2marketing
	 */
	public Collection<Textbaustein> getWeraproduct2marketing()
	{
		return getWeraproduct2marketing( getSession().getSessionContext() );
	}
	
	public long getWeraproduct2marketingCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null
		);
	}
	
	public long getWeraproduct2marketingCount()
	{
		return getWeraproduct2marketingCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weraproduct2marketing</code> attribute. 
	 * @param value the weraproduct2marketing
	 */
	public void setWeraproduct2marketing(final SessionContext ctx, final Collection<Textbaustein> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weraproduct2marketing</code> attribute. 
	 * @param value the weraproduct2marketing
	 */
	public void setWeraproduct2marketing(final Collection<Textbaustein> value)
	{
		setWeraproduct2marketing( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproduct2marketing. 
	 * @param value the item to add to weraproduct2marketing
	 */
	public void addToWeraproduct2marketing(final SessionContext ctx, final Textbaustein value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproduct2marketing. 
	 * @param value the item to add to weraproduct2marketing
	 */
	public void addToWeraproduct2marketing(final Textbaustein value)
	{
		addToWeraproduct2marketing( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproduct2marketing. 
	 * @param value the item to remove from weraproduct2marketing
	 */
	public void removeFromWeraproduct2marketing(final SessionContext ctx, final Textbaustein value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTTEXTBAUSTEINMARKETINGRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproduct2marketing. 
	 * @param value the item to remove from weraproduct2marketing
	 */
	public void removeFromWeraproduct2marketing(final Textbaustein value)
	{
		removeFromWeraproduct2marketing( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weraproductset2bulletpoints</code> attribute.
	 * @return the weraproductset2bulletpoints
	 */
	public Collection<Textbaustein> getWeraproductset2bulletpoints(final SessionContext ctx)
	{
		final List<Textbaustein> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weraproductset2bulletpoints</code> attribute.
	 * @return the weraproductset2bulletpoints
	 */
	public Collection<Textbaustein> getWeraproductset2bulletpoints()
	{
		return getWeraproductset2bulletpoints( getSession().getSessionContext() );
	}
	
	public long getWeraproductset2bulletpointsCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null
		);
	}
	
	public long getWeraproductset2bulletpointsCount()
	{
		return getWeraproductset2bulletpointsCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weraproductset2bulletpoints</code> attribute. 
	 * @param value the weraproductset2bulletpoints
	 */
	public void setWeraproductset2bulletpoints(final SessionContext ctx, final Collection<Textbaustein> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weraproductset2bulletpoints</code> attribute. 
	 * @param value the weraproductset2bulletpoints
	 */
	public void setWeraproductset2bulletpoints(final Collection<Textbaustein> value)
	{
		setWeraproductset2bulletpoints( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductset2bulletpoints. 
	 * @param value the item to add to weraproductset2bulletpoints
	 */
	public void addToWeraproductset2bulletpoints(final SessionContext ctx, final Textbaustein value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductset2bulletpoints. 
	 * @param value the item to add to weraproductset2bulletpoints
	 */
	public void addToWeraproductset2bulletpoints(final Textbaustein value)
	{
		addToWeraproductset2bulletpoints( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductset2bulletpoints. 
	 * @param value the item to remove from weraproductset2bulletpoints
	 */
	public void removeFromWeraproductset2bulletpoints(final SessionContext ctx, final Textbaustein value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.WERAPRODUCTSETTEXTBAUSTEINBULLETPOINTSRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductset2bulletpoints. 
	 * @param value the item to remove from weraproductset2bulletpoints
	 */
	public void removeFromWeraproductset2bulletpoints(final Textbaustein value)
	{
		removeFromWeraproductset2bulletpoints( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weraproductsetinsets_relation</code> attribute.
	 * @return the weraproductsetinsets_relation
	 */
	public Collection<WeraProductSetinSet> getWeraproductsetinsets_relation(final SessionContext ctx)
	{
		return WERAPRODUCTSETINSETS_RELATIONHANDLER.getValues( ctx, this );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.weraproductsetinsets_relation</code> attribute.
	 * @return the weraproductsetinsets_relation
	 */
	public Collection<WeraProductSetinSet> getWeraproductsetinsets_relation()
	{
		return getWeraproductsetinsets_relation( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weraproductsetinsets_relation</code> attribute. 
	 * @param value the weraproductsetinsets_relation
	 */
	public void setWeraproductsetinsets_relation(final SessionContext ctx, final Collection<WeraProductSetinSet> value)
	{
		WERAPRODUCTSETINSETS_RELATIONHANDLER.setValues( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.weraproductsetinsets_relation</code> attribute. 
	 * @param value the weraproductsetinsets_relation
	 */
	public void setWeraproductsetinsets_relation(final Collection<WeraProductSetinSet> value)
	{
		setWeraproductsetinsets_relation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetinsets_relation. 
	 * @param value the item to add to weraproductsetinsets_relation
	 */
	public void addToWeraproductsetinsets_relation(final SessionContext ctx, final WeraProductSetinSet value)
	{
		WERAPRODUCTSETINSETS_RELATIONHANDLER.addValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to weraproductsetinsets_relation. 
	 * @param value the item to add to weraproductsetinsets_relation
	 */
	public void addToWeraproductsetinsets_relation(final WeraProductSetinSet value)
	{
		addToWeraproductsetinsets_relation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetinsets_relation. 
	 * @param value the item to remove from weraproductsetinsets_relation
	 */
	public void removeFromWeraproductsetinsets_relation(final SessionContext ctx, final WeraProductSetinSet value)
	{
		WERAPRODUCTSETINSETS_RELATIONHANDLER.removeValue( ctx, this, value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from weraproductsetinsets_relation. 
	 * @param value the item to remove from weraproductsetinsets_relation
	 */
	public void removeFromWeraproductsetinsets_relation(final WeraProductSetinSet value)
	{
		removeFromWeraproductsetinsets_relation( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_au</code> attribute.
	 * @return the www_au - Australien  J/N
	 */
	public Boolean isWww_au(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, WWW_AU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_au</code> attribute.
	 * @return the www_au - Australien  J/N
	 */
	public Boolean isWww_au()
	{
		return isWww_au( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_au</code> attribute. 
	 * @return the www_au - Australien  J/N
	 */
	public boolean isWww_auAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isWww_au( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_au</code> attribute. 
	 * @return the www_au - Australien  J/N
	 */
	public boolean isWww_auAsPrimitive()
	{
		return isWww_auAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_au</code> attribute. 
	 * @param value the www_au - Australien  J/N
	 */
	public void setWww_au(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, WWW_AU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_au</code> attribute. 
	 * @param value the www_au - Australien  J/N
	 */
	public void setWww_au(final Boolean value)
	{
		setWww_au( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_au</code> attribute. 
	 * @param value the www_au - Australien  J/N
	 */
	public void setWww_au(final SessionContext ctx, final boolean value)
	{
		setWww_au( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_au</code> attribute. 
	 * @param value the www_au - Australien  J/N
	 */
	public void setWww_au(final boolean value)
	{
		setWww_au( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_de</code> attribute.
	 * @return the www_de - Weltkatalog  J/N
	 */
	public Boolean isWww_de(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, WWW_DE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_de</code> attribute.
	 * @return the www_de - Weltkatalog  J/N
	 */
	public Boolean isWww_de()
	{
		return isWww_de( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_de</code> attribute. 
	 * @return the www_de - Weltkatalog  J/N
	 */
	public boolean isWww_deAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isWww_de( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_de</code> attribute. 
	 * @return the www_de - Weltkatalog  J/N
	 */
	public boolean isWww_deAsPrimitive()
	{
		return isWww_deAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_de</code> attribute. 
	 * @param value the www_de - Weltkatalog  J/N
	 */
	public void setWww_de(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, WWW_DE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_de</code> attribute. 
	 * @param value the www_de - Weltkatalog  J/N
	 */
	public void setWww_de(final Boolean value)
	{
		setWww_de( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_de</code> attribute. 
	 * @param value the www_de - Weltkatalog  J/N
	 */
	public void setWww_de(final SessionContext ctx, final boolean value)
	{
		setWww_de( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_de</code> attribute. 
	 * @param value the www_de - Weltkatalog  J/N
	 */
	public void setWww_de(final boolean value)
	{
		setWww_de( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_uk</code> attribute.
	 * @return the www_uk - UK  J/N
	 */
	public Boolean isWww_uk(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, WWW_UK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_uk</code> attribute.
	 * @return the www_uk - UK  J/N
	 */
	public Boolean isWww_uk()
	{
		return isWww_uk( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_uk</code> attribute. 
	 * @return the www_uk - UK  J/N
	 */
	public boolean isWww_ukAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isWww_uk( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_uk</code> attribute. 
	 * @return the www_uk - UK  J/N
	 */
	public boolean isWww_ukAsPrimitive()
	{
		return isWww_ukAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_uk</code> attribute. 
	 * @param value the www_uk - UK  J/N
	 */
	public void setWww_uk(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, WWW_UK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_uk</code> attribute. 
	 * @param value the www_uk - UK  J/N
	 */
	public void setWww_uk(final Boolean value)
	{
		setWww_uk( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_uk</code> attribute. 
	 * @param value the www_uk - UK  J/N
	 */
	public void setWww_uk(final SessionContext ctx, final boolean value)
	{
		setWww_uk( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_uk</code> attribute. 
	 * @param value the www_uk - UK  J/N
	 */
	public void setWww_uk(final boolean value)
	{
		setWww_uk( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_us</code> attribute.
	 * @return the www_us - Nordamerika  J/N
	 */
	public Boolean isWww_us(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, WWW_US);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_us</code> attribute.
	 * @return the www_us - Nordamerika  J/N
	 */
	public Boolean isWww_us()
	{
		return isWww_us( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_us</code> attribute. 
	 * @return the www_us - Nordamerika  J/N
	 */
	public boolean isWww_usAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isWww_us( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProduct.www_us</code> attribute. 
	 * @return the www_us - Nordamerika  J/N
	 */
	public boolean isWww_usAsPrimitive()
	{
		return isWww_usAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_us</code> attribute. 
	 * @param value the www_us - Nordamerika  J/N
	 */
	public void setWww_us(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, WWW_US,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_us</code> attribute. 
	 * @param value the www_us - Nordamerika  J/N
	 */
	public void setWww_us(final Boolean value)
	{
		setWww_us( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_us</code> attribute. 
	 * @param value the www_us - Nordamerika  J/N
	 */
	public void setWww_us(final SessionContext ctx, final boolean value)
	{
		setWww_us( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProduct.www_us</code> attribute. 
	 * @param value the www_us - Nordamerika  J/N
	 */
	public void setWww_us(final boolean value)
	{
		setWww_us( getSession().getSessionContext(), value );
	}
	
}

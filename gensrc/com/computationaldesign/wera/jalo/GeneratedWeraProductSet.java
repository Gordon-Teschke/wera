/*
 * ----------------------------------------------------------------
 * --- WARNING: THIS FILE IS GENERATED AND WILL BE OVERWRITTEN! ---
 * --- Generated at 29.07.2026 15:42:09                         ---
 * ----------------------------------------------------------------
 */
package com.computationaldesign.wera.jalo;

import com.computationaldesign.wera.constants.WeraConstants;
import com.computationaldesign.wera.jalo.Bildreferenz;
import com.computationaldesign.wera.jalo.WeraProduct;
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
 * Generated class for type {@link com.computationaldesign.wera.jalo.WeraProductSet WeraProductSet}.
 */
@SuppressWarnings({"deprecation","unused","cast","PMD"})
public abstract class GeneratedWeraProductSet extends WeraProduct
{
	/** Qualifier of the <code>WeraProductSet.shop_bullet_point3</code> attribute **/
	public static final String SHOP_BULLET_POINT3 = "shop_bullet_point3".intern();
	/** Qualifier of the <code>WeraProductSet.asin_us</code> attribute **/
	public static final String ASIN_US = "asin_us".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_grifflaenge</code> attribute **/
	public static final String XPACE_GRIFFLAENGE = "xpace_grifflaenge".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_stellbar</code> attribute **/
	public static final String XPACE_STELLBAR = "xpace_stellbar".intern();
	/** Qualifier of the <code>WeraProductSet.ZolltarifNr</code> attribute **/
	public static final String ZOLLTARIFNR = "ZolltarifNr".intern();
	/** Qualifier of the <code>WeraProductSet.preview_etikett_image</code> attribute **/
	public static final String PREVIEW_ETIKETT_IMAGE = "preview_etikett_image".intern();
	/** Qualifier of the <code>WeraProductSet.valid_from</code> attribute **/
	public static final String VALID_FROM = "valid_from".intern();
	/** Qualifier of the <code>WeraProductSet.packm_hoehe</code> attribute **/
	public static final String PACKM_HOEHE = "packm_hoehe".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute **/
	public static final String XPACE_HOEHE_VERPACKUNG_PRODUKT = "xpace_hoehe_verpackung_produkt".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_gap_x</code> attribute **/
	public static final String XPACE_GAP_X = "xpace_gap_x".intern();
	/** Qualifier of the <code>WeraProductSet.etikett_image</code> attribute **/
	public static final String ETIKETT_IMAGE = "etikett_image".intern();
	/** Qualifier of the <code>WeraProductSet.asin_it</code> attribute **/
	public static final String ASIN_IT = "asin_it".intern();
	/** Qualifier of the <code>WeraProductSet.GewVE</code> attribute **/
	public static final String GEWVE = "GewVE".intern();
	/** Qualifier of the <code>WeraProductSet.shop_schreiner</code> attribute **/
	public static final String SHOP_SCHREINER = "shop_schreiner".intern();
	/** Qualifier of the <code>WeraProductSet.OnlineREACHinfo</code> attribute **/
	public static final String ONLINEREACHINFO = "OnlineREACHinfo".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute **/
	public static final String XPACE_GEWICHT_VERPACKUNG_PRODUKT = "xpace_gewicht_verpackung_produkt".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute **/
	public static final String XPACE_BREITE_VERPACKUNG_PRODUKT = "xpace_breite_verpackung_produkt".intern();
	/** Qualifier of the <code>WeraProductSet.asin_fr</code> attribute **/
	public static final String ASIN_FR = "asin_fr".intern();
	/** Qualifier of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute **/
	public static final String SHOP_AUTOMOTIV_AFTERSALES = "shop_automotiv_aftersales".intern();
	/** Qualifier of the <code>WeraProductSet.shop_keywords</code> attribute **/
	public static final String SHOP_KEYWORDS = "shop_keywords".intern();
	/** Qualifier of the <code>WeraProductSet.artikel_volumen</code> attribute **/
	public static final String ARTIKEL_VOLUMEN = "artikel_volumen".intern();
	/** Qualifier of the <code>WeraProductSet.lagerNr</code> attribute **/
	public static final String LAGERNR = "lagerNr".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute **/
	public static final String XPACE_TIEFE_VERPACKUNG_PRODUKT = "xpace_tiefe_verpackung_produkt".intern();
	/** Qualifier of the <code>WeraProductSet.packm_gewichteh</code> attribute **/
	public static final String PACKM_GEWICHTEH = "packm_gewichteh".intern();
	/** Qualifier of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute **/
	public static final String SHOP_SCHLOSSER_MONTEUR = "shop_schlosser_monteur".intern();
	/** Qualifier of the <code>WeraProductSet.shop_aircraft</code> attribute **/
	public static final String SHOP_AIRCRAFT = "shop_aircraft".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute **/
	public static final String XPACE_KLINGENLAENGE = "xpace_klingenlaenge".intern();
	/** Qualifier of the <code>WeraProductSet.packm_breite</code> attribute **/
	public static final String PACKM_BREITE = "packm_breite".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_dateiname_prefix</code> attribute **/
	public static final String XPACE_DATEINAME_PREFIX = "xpace_dateiname_prefix".intern();
	/** Qualifier of the <code>WeraProductSet.shop_bullet_point5</code> attribute **/
	public static final String SHOP_BULLET_POINT5 = "shop_bullet_point5".intern();
	/** Qualifier of the <code>WeraProductSet.variantenNrEU</code> attribute **/
	public static final String VARIANTENNREU = "variantenNrEU".intern();
	/** Qualifier of the <code>WeraProductSet.packm_laengen_einheit</code> attribute **/
	public static final String PACKM_LAENGEN_EINHEIT = "packm_laengen_einheit".intern();
	/** Qualifier of the <code>WeraProductSet.modelljahr</code> attribute **/
	public static final String MODELLJAHR = "modelljahr".intern();
	/** Qualifier of the <code>WeraProductSet.OnlineSicherheitsdatenblatt</code> attribute **/
	public static final String ONLINESICHERHEITSDATENBLATT = "OnlineSicherheitsdatenblatt".intern();
	/** Qualifier of the <code>WeraProductSet.artikel_hoehe</code> attribute **/
	public static final String ARTIKEL_HOEHE = "artikel_hoehe".intern();
	/** Qualifier of the <code>WeraProductSet.shop_ish</code> attribute **/
	public static final String SHOP_ISH = "shop_ish".intern();
	/** Qualifier of the <code>WeraProductSet.packm_laenge</code> attribute **/
	public static final String PACKM_LAENGE = "packm_laenge".intern();
	/** Qualifier of the <code>WeraProductSet.variant_image</code> attribute **/
	public static final String VARIANT_IMAGE = "variant_image".intern();
	/** Qualifier of the <code>WeraProductSet.GewichtBrutto</code> attribute **/
	public static final String GEWICHTBRUTTO = "GewichtBrutto".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_haengbar</code> attribute **/
	public static final String XPACE_HAENGBAR = "xpace_haengbar".intern();
	/** Qualifier of the <code>WeraProductSet.packm_volumen</code> attribute **/
	public static final String PACKM_VOLUMEN = "packm_volumen".intern();
	/** Qualifier of the <code>WeraProductSet.shop_vde</code> attribute **/
	public static final String SHOP_VDE = "shop_vde".intern();
	/** Qualifier of the <code>WeraProductSet.asin_uk</code> attribute **/
	public static final String ASIN_UK = "asin_uk".intern();
	/** Qualifier of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute **/
	public static final String IS_BULLETPOINT_REPRESENTATIVE = "is_bulletpoint_representative".intern();
	/** Qualifier of the <code>WeraProductSet.shop_modellbau</code> attribute **/
	public static final String SHOP_MODELLBAU = "shop_modellbau".intern();
	/** Qualifier of the <code>WeraProductSet.shop_title</code> attribute **/
	public static final String SHOP_TITLE = "shop_title".intern();
	/** Qualifier of the <code>WeraProductSet.addOnline_Verweis</code> attribute **/
	public static final String ADDONLINE_VERWEIS = "addOnline_Verweis".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_typ_verpackung</code> attribute **/
	public static final String XPACE_TYP_VERPACKUNG = "xpace_typ_verpackung".intern();
	/** Qualifier of the <code>WeraProductSet.artikel_laengen_einheit</code> attribute **/
	public static final String ARTIKEL_LAENGEN_EINHEIT = "artikel_laengen_einheit".intern();
	/** Qualifier of the <code>WeraProductSet.shop_description</code> attribute **/
	public static final String SHOP_DESCRIPTION = "shop_description".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_variant_id</code> attribute **/
	public static final String XPACE_VARIANT_ID = "xpace_variant_id".intern();
	/** Qualifier of the <code>WeraProductSet.shop_bullet_point6</code> attribute **/
	public static final String SHOP_BULLET_POINT6 = "shop_bullet_point6".intern();
	/** Qualifier of the <code>WeraProductSet.artikel_breite</code> attribute **/
	public static final String ARTIKEL_BREITE = "artikel_breite".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_default_z</code> attribute **/
	public static final String XPACE_DEFAULT_Z = "xpace_default_z".intern();
	/** Qualifier of the <code>WeraProductSet.shop_esd</code> attribute **/
	public static final String SHOP_ESD = "shop_esd".intern();
	/** Qualifier of the <code>WeraProductSet.contentQuantity</code> attribute **/
	public static final String CONTENTQUANTITY = "contentQuantity".intern();
	/** Qualifier of the <code>WeraProductSet.shop_bau</code> attribute **/
	public static final String SHOP_BAU = "shop_bau".intern();
	/** Qualifier of the <code>WeraProductSet.GewichtEinheit</code> attribute **/
	public static final String GEWICHTEINHEIT = "GewichtEinheit".intern();
	/** Qualifier of the <code>WeraProductSet.variantenNr</code> attribute **/
	public static final String VARIANTENNR = "variantenNr".intern();
	/** Qualifier of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute **/
	public static final String SHOP_WERKSTATT_INSTANDHALTUNG = "shop_werkstatt_instandhaltung".intern();
	/** Qualifier of the <code>WeraProductSet.Gewicht</code> attribute **/
	public static final String GEWICHT = "Gewicht".intern();
	/** Qualifier of the <code>WeraProductSet.variantenNrUS</code> attribute **/
	public static final String VARIANTENNRUS = "variantenNrUS".intern();
	/** Qualifier of the <code>WeraProductSet.asin_de</code> attribute **/
	public static final String ASIN_DE = "asin_de".intern();
	/** Qualifier of the <code>WeraProductSet.artnr</code> attribute **/
	public static final String ARTNR = "artnr".intern();
	/** Qualifier of the <code>WeraProductSet.shop_bullet_point2</code> attribute **/
	public static final String SHOP_BULLET_POINT2 = "shop_bullet_point2".intern();
	/** Qualifier of the <code>WeraProductSet.asin_es</code> attribute **/
	public static final String ASIN_ES = "asin_es".intern();
	/** Qualifier of the <code>WeraProductSet.amazonbilderref2weraproductset</code> attribute **/
	public static final String AMAZONBILDERREF2WERAPRODUCTSET = "amazonbilderref2weraproductset".intern();
	/** Qualifier of the <code>WeraProductSet.shop_bullet_point4</code> attribute **/
	public static final String SHOP_BULLET_POINT4 = "shop_bullet_point4".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute **/
	public static final String XPACE_EUROLOCH_Y_KOORDINATE = "xpace_euroloch_y_koordinate".intern();
	/** Qualifier of the <code>WeraProductSet.shop_industrie</code> attribute **/
	public static final String SHOP_INDUSTRIE = "shop_industrie".intern();
	/** Qualifier of the <code>WeraProductSet.Ursprungsland</code> attribute **/
	public static final String URSPRUNGSLAND = "Ursprungsland".intern();
	/** Qualifier of the <code>WeraProductSet.shop_bullet_point1</code> attribute **/
	public static final String SHOP_BULLET_POINT1 = "shop_bullet_point1".intern();
	/** Qualifier of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute **/
	public static final String PRODUKT_UEBERPRUEFT_SVHS = "Produkt_ueberprueft_SVHS".intern();
	/** Qualifier of the <code>WeraProductSet.Benennung_SVHC_Stoffes</code> attribute **/
	public static final String BENENNUNG_SVHC_STOFFES = "Benennung_SVHC_Stoffes".intern();
	/** Qualifier of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute **/
	public static final String SVHC_STOFF_ENTHALTEN = "SVHC_Stoff_enthalten".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute **/
	public static final String XPACE_EUROLOCH_X_KOORDINATE = "xpace_euroloch_x_koordinate".intern();
	/** Qualifier of the <code>WeraProductSet.valid_to</code> attribute **/
	public static final String VALID_TO = "valid_to".intern();
	/** Qualifier of the <code>WeraProductSet.Gewicht_Stoffes</code> attribute **/
	public static final String GEWICHT_STOFFES = "Gewicht_Stoffes".intern();
	/** Qualifier of the <code>WeraProductSet.xpace_schuettbar</code> attribute **/
	public static final String XPACE_SCHUETTBAR = "xpace_schuettbar".intern();
	/** Qualifier of the <code>WeraProductSet.is_marketing_representative</code> attribute **/
	public static final String IS_MARKETING_REPRESENTATIVE = "is_marketing_representative".intern();
	/** Qualifier of the <code>WeraProductSet.artikel_laenge</code> attribute **/
	public static final String ARTIKEL_LAENGE = "artikel_laenge".intern();
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.addOnline_Verweis</code> attribute.
	 * @return the addOnline_Verweis
	 */
	public String getAddOnline_Verweis(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ADDONLINE_VERWEIS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.addOnline_Verweis</code> attribute.
	 * @return the addOnline_Verweis
	 */
	public String getAddOnline_Verweis()
	{
		return getAddOnline_Verweis( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.addOnline_Verweis</code> attribute. 
	 * @param value the addOnline_Verweis
	 */
	public void setAddOnline_Verweis(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ADDONLINE_VERWEIS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.addOnline_Verweis</code> attribute. 
	 * @param value the addOnline_Verweis
	 */
	public void setAddOnline_Verweis(final String value)
	{
		setAddOnline_Verweis( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.amazonbilderref2weraproductset</code> attribute.
	 * @return the amazonbilderref2weraproductset
	 */
	public Collection<Bildreferenz> getAmazonbilderref2weraproductset(final SessionContext ctx)
	{
		final List<Bildreferenz> items = getLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null,
			true,
			true
		);
		return items;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.amazonbilderref2weraproductset</code> attribute.
	 * @return the amazonbilderref2weraproductset
	 */
	public Collection<Bildreferenz> getAmazonbilderref2weraproductset()
	{
		return getAmazonbilderref2weraproductset( getSession().getSessionContext() );
	}
	
	public long getAmazonbilderref2weraproductsetCount(final SessionContext ctx)
	{
		return getLinkedItemsCount(
			ctx,
			true,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null
		);
	}
	
	public long getAmazonbilderref2weraproductsetCount()
	{
		return getAmazonbilderref2weraproductsetCount( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.amazonbilderref2weraproductset</code> attribute. 
	 * @param value the amazonbilderref2weraproductset
	 */
	public void setAmazonbilderref2weraproductset(final SessionContext ctx, final Collection<Bildreferenz> value)
	{
		setLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null,
			value,
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.amazonbilderref2weraproductset</code> attribute. 
	 * @param value the amazonbilderref2weraproductset
	 */
	public void setAmazonbilderref2weraproductset(final Collection<Bildreferenz> value)
	{
		setAmazonbilderref2weraproductset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to amazonbilderref2weraproductset. 
	 * @param value the item to add to amazonbilderref2weraproductset
	 */
	public void addToAmazonbilderref2weraproductset(final SessionContext ctx, final Bildreferenz value)
	{
		addLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Adds <code>value</code> to amazonbilderref2weraproductset. 
	 * @param value the item to add to amazonbilderref2weraproductset
	 */
	public void addToAmazonbilderref2weraproductset(final Bildreferenz value)
	{
		addToAmazonbilderref2weraproductset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from amazonbilderref2weraproductset. 
	 * @param value the item to remove from amazonbilderref2weraproductset
	 */
	public void removeFromAmazonbilderref2weraproductset(final SessionContext ctx, final Bildreferenz value)
	{
		removeLinkedItems( 
			ctx,
			true,
			WeraConstants.Relations.AMAZONBILDERSETRELATION,
			null,
			Collections.singletonList(value),
			true,
			true
		);
	}
	
	/**
	 * <i>Generated method</i> - Removes <code>value</code> from amazonbilderref2weraproductset. 
	 * @param value the item to remove from amazonbilderref2weraproductset
	 */
	public void removeFromAmazonbilderref2weraproductset(final Bildreferenz value)
	{
		removeFromAmazonbilderref2weraproductset( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_breite</code> attribute.
	 * @return the artikel_breite - Artikel Breite
	 */
	public String getArtikel_breite(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTIKEL_BREITE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_breite</code> attribute.
	 * @return the artikel_breite - Artikel Breite
	 */
	public String getArtikel_breite()
	{
		return getArtikel_breite( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_breite</code> attribute. 
	 * @param value the artikel_breite - Artikel Breite
	 */
	public void setArtikel_breite(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTIKEL_BREITE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_breite</code> attribute. 
	 * @param value the artikel_breite - Artikel Breite
	 */
	public void setArtikel_breite(final String value)
	{
		setArtikel_breite( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_hoehe</code> attribute.
	 * @return the artikel_hoehe - Artikel HÃ¶he
	 */
	public String getArtikel_hoehe(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTIKEL_HOEHE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_hoehe</code> attribute.
	 * @return the artikel_hoehe - Artikel HÃ¶he
	 */
	public String getArtikel_hoehe()
	{
		return getArtikel_hoehe( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_hoehe</code> attribute. 
	 * @param value the artikel_hoehe - Artikel HÃ¶he
	 */
	public void setArtikel_hoehe(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTIKEL_HOEHE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_hoehe</code> attribute. 
	 * @param value the artikel_hoehe - Artikel HÃ¶he
	 */
	public void setArtikel_hoehe(final String value)
	{
		setArtikel_hoehe( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_laenge</code> attribute.
	 * @return the artikel_laenge - Artikel länge
	 */
	public String getArtikel_laenge(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTIKEL_LAENGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_laenge</code> attribute.
	 * @return the artikel_laenge - Artikel länge
	 */
	public String getArtikel_laenge()
	{
		return getArtikel_laenge( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_laenge</code> attribute. 
	 * @param value the artikel_laenge - Artikel länge
	 */
	public void setArtikel_laenge(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTIKEL_LAENGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_laenge</code> attribute. 
	 * @param value the artikel_laenge - Artikel länge
	 */
	public void setArtikel_laenge(final String value)
	{
		setArtikel_laenge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_laengen_einheit</code> attribute.
	 * @return the artikel_laengen_einheit - Artikel längen Einheit
	 */
	public EnumerationValue getArtikel_laengen_einheit(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, ARTIKEL_LAENGEN_EINHEIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_laengen_einheit</code> attribute.
	 * @return the artikel_laengen_einheit - Artikel längen Einheit
	 */
	public EnumerationValue getArtikel_laengen_einheit()
	{
		return getArtikel_laengen_einheit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_laengen_einheit</code> attribute. 
	 * @param value the artikel_laengen_einheit - Artikel längen Einheit
	 */
	public void setArtikel_laengen_einheit(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, ARTIKEL_LAENGEN_EINHEIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_laengen_einheit</code> attribute. 
	 * @param value the artikel_laengen_einheit - Artikel längen Einheit
	 */
	public void setArtikel_laengen_einheit(final EnumerationValue value)
	{
		setArtikel_laengen_einheit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_volumen</code> attribute.
	 * @return the artikel_volumen - Artikel Volumen
	 */
	public String getArtikel_volumen(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTIKEL_VOLUMEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artikel_volumen</code> attribute.
	 * @return the artikel_volumen - Artikel Volumen
	 */
	public String getArtikel_volumen()
	{
		return getArtikel_volumen( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_volumen</code> attribute. 
	 * @param value the artikel_volumen - Artikel Volumen
	 */
	public void setArtikel_volumen(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTIKEL_VOLUMEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artikel_volumen</code> attribute. 
	 * @param value the artikel_volumen - Artikel Volumen
	 */
	public void setArtikel_volumen(final String value)
	{
		setArtikel_volumen( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artnr</code> attribute.
	 * @return the artnr
	 */
	public String getArtnr(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ARTNR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.artnr</code> attribute.
	 * @return the artnr
	 */
	public String getArtnr()
	{
		return getArtnr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artnr</code> attribute. 
	 * @param value the artnr
	 */
	public void setArtnr(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ARTNR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.artnr</code> attribute. 
	 * @param value the artnr
	 */
	public void setArtnr(final String value)
	{
		setArtnr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_de</code> attribute.
	 * @return the asin_de - ASIN DE
	 */
	public String getAsin_de(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASIN_DE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_de</code> attribute.
	 * @return the asin_de - ASIN DE
	 */
	public String getAsin_de()
	{
		return getAsin_de( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_de</code> attribute. 
	 * @param value the asin_de - ASIN DE
	 */
	public void setAsin_de(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASIN_DE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_de</code> attribute. 
	 * @param value the asin_de - ASIN DE
	 */
	public void setAsin_de(final String value)
	{
		setAsin_de( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_es</code> attribute.
	 * @return the asin_es - ASIN ES
	 */
	public String getAsin_es(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASIN_ES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_es</code> attribute.
	 * @return the asin_es - ASIN ES
	 */
	public String getAsin_es()
	{
		return getAsin_es( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_es</code> attribute. 
	 * @param value the asin_es - ASIN ES
	 */
	public void setAsin_es(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASIN_ES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_es</code> attribute. 
	 * @param value the asin_es - ASIN ES
	 */
	public void setAsin_es(final String value)
	{
		setAsin_es( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_fr</code> attribute.
	 * @return the asin_fr - ASIN FR
	 */
	public String getAsin_fr(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASIN_FR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_fr</code> attribute.
	 * @return the asin_fr - ASIN FR
	 */
	public String getAsin_fr()
	{
		return getAsin_fr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_fr</code> attribute. 
	 * @param value the asin_fr - ASIN FR
	 */
	public void setAsin_fr(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASIN_FR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_fr</code> attribute. 
	 * @param value the asin_fr - ASIN FR
	 */
	public void setAsin_fr(final String value)
	{
		setAsin_fr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_it</code> attribute.
	 * @return the asin_it - ASIN IT
	 */
	public String getAsin_it(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASIN_IT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_it</code> attribute.
	 * @return the asin_it - ASIN IT
	 */
	public String getAsin_it()
	{
		return getAsin_it( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_it</code> attribute. 
	 * @param value the asin_it - ASIN IT
	 */
	public void setAsin_it(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASIN_IT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_it</code> attribute. 
	 * @param value the asin_it - ASIN IT
	 */
	public void setAsin_it(final String value)
	{
		setAsin_it( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_uk</code> attribute.
	 * @return the asin_uk - ASIN UK
	 */
	public String getAsin_uk(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASIN_UK);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_uk</code> attribute.
	 * @return the asin_uk - ASIN UK
	 */
	public String getAsin_uk()
	{
		return getAsin_uk( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_uk</code> attribute. 
	 * @param value the asin_uk - ASIN UK
	 */
	public void setAsin_uk(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASIN_UK,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_uk</code> attribute. 
	 * @param value the asin_uk - ASIN UK
	 */
	public void setAsin_uk(final String value)
	{
		setAsin_uk( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_us</code> attribute.
	 * @return the asin_us - ASIN US
	 */
	public String getAsin_us(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ASIN_US);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.asin_us</code> attribute.
	 * @return the asin_us - ASIN US
	 */
	public String getAsin_us()
	{
		return getAsin_us( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_us</code> attribute. 
	 * @param value the asin_us - ASIN US
	 */
	public void setAsin_us(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ASIN_US,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.asin_us</code> attribute. 
	 * @param value the asin_us - ASIN US
	 */
	public void setAsin_us(final String value)
	{
		setAsin_us( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Benennung_SVHC_Stoffes</code> attribute.
	 * @return the Benennung_SVHC_Stoffes
	 */
	public String getBenennung_SVHC_Stoffes(final SessionContext ctx)
	{
		return (String)getProperty( ctx, BENENNUNG_SVHC_STOFFES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Benennung_SVHC_Stoffes</code> attribute.
	 * @return the Benennung_SVHC_Stoffes
	 */
	public String getBenennung_SVHC_Stoffes()
	{
		return getBenennung_SVHC_Stoffes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Benennung_SVHC_Stoffes</code> attribute. 
	 * @param value the Benennung_SVHC_Stoffes
	 */
	public void setBenennung_SVHC_Stoffes(final SessionContext ctx, final String value)
	{
		setProperty(ctx, BENENNUNG_SVHC_STOFFES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Benennung_SVHC_Stoffes</code> attribute. 
	 * @param value the Benennung_SVHC_Stoffes
	 */
	public void setBenennung_SVHC_Stoffes(final String value)
	{
		setBenennung_SVHC_Stoffes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.contentQuantity</code> attribute.
	 * @return the contentQuantity - Verpackungsmenge
	 */
	public Integer getContentQuantity(final SessionContext ctx)
	{
		return (Integer)getProperty( ctx, CONTENTQUANTITY);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.contentQuantity</code> attribute.
	 * @return the contentQuantity - Verpackungsmenge
	 */
	public Integer getContentQuantity()
	{
		return getContentQuantity( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.contentQuantity</code> attribute. 
	 * @return the contentQuantity - Verpackungsmenge
	 */
	public int getContentQuantityAsPrimitive(final SessionContext ctx)
	{
		Integer value = getContentQuantity( ctx );
		return value != null ? value.intValue() : 0;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.contentQuantity</code> attribute. 
	 * @return the contentQuantity - Verpackungsmenge
	 */
	public int getContentQuantityAsPrimitive()
	{
		return getContentQuantityAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.contentQuantity</code> attribute. 
	 * @param value the contentQuantity - Verpackungsmenge
	 */
	public void setContentQuantity(final SessionContext ctx, final Integer value)
	{
		setProperty(ctx, CONTENTQUANTITY,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.contentQuantity</code> attribute. 
	 * @param value the contentQuantity - Verpackungsmenge
	 */
	public void setContentQuantity(final Integer value)
	{
		setContentQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.contentQuantity</code> attribute. 
	 * @param value the contentQuantity - Verpackungsmenge
	 */
	public void setContentQuantity(final SessionContext ctx, final int value)
	{
		setContentQuantity( ctx,Integer.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.contentQuantity</code> attribute. 
	 * @param value the contentQuantity - Verpackungsmenge
	 */
	public void setContentQuantity(final int value)
	{
		setContentQuantity( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.etikett_image</code> attribute.
	 * @return the etikett_image - Etikett
	 */
	public String getEtikett_image(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ETIKETT_IMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.etikett_image</code> attribute.
	 * @return the etikett_image - Etikett
	 */
	public String getEtikett_image()
	{
		return getEtikett_image( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.etikett_image</code> attribute. 
	 * @param value the etikett_image - Etikett
	 */
	public void setEtikett_image(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ETIKETT_IMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.etikett_image</code> attribute. 
	 * @param value the etikett_image - Etikett
	 */
	public void setEtikett_image(final String value)
	{
		setEtikett_image( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Gewicht</code> attribute.
	 * @return the Gewicht - Artikel Gewicht pro Stück
	 */
	public String getGewicht(final SessionContext ctx)
	{
		return (String)getProperty( ctx, GEWICHT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Gewicht</code> attribute.
	 * @return the Gewicht - Artikel Gewicht pro Stück
	 */
	public String getGewicht()
	{
		return getGewicht( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Gewicht</code> attribute. 
	 * @param value the Gewicht - Artikel Gewicht pro Stück
	 */
	public void setGewicht(final SessionContext ctx, final String value)
	{
		setProperty(ctx, GEWICHT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Gewicht</code> attribute. 
	 * @param value the Gewicht - Artikel Gewicht pro Stück
	 */
	public void setGewicht(final String value)
	{
		setGewicht( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Gewicht_Stoffes</code> attribute.
	 * @return the Gewicht_Stoffes
	 */
	public String getGewicht_Stoffes(final SessionContext ctx)
	{
		return (String)getProperty( ctx, GEWICHT_STOFFES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Gewicht_Stoffes</code> attribute.
	 * @return the Gewicht_Stoffes
	 */
	public String getGewicht_Stoffes()
	{
		return getGewicht_Stoffes( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Gewicht_Stoffes</code> attribute. 
	 * @param value the Gewicht_Stoffes
	 */
	public void setGewicht_Stoffes(final SessionContext ctx, final String value)
	{
		setProperty(ctx, GEWICHT_STOFFES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Gewicht_Stoffes</code> attribute. 
	 * @param value the Gewicht_Stoffes
	 */
	public void setGewicht_Stoffes(final String value)
	{
		setGewicht_Stoffes( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.GewichtBrutto</code> attribute.
	 * @return the GewichtBrutto - Brutto Gewicht
	 */
	public String getGewichtBrutto(final SessionContext ctx)
	{
		return (String)getProperty( ctx, GEWICHTBRUTTO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.GewichtBrutto</code> attribute.
	 * @return the GewichtBrutto - Brutto Gewicht
	 */
	public String getGewichtBrutto()
	{
		return getGewichtBrutto( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.GewichtBrutto</code> attribute. 
	 * @param value the GewichtBrutto - Brutto Gewicht
	 */
	public void setGewichtBrutto(final SessionContext ctx, final String value)
	{
		setProperty(ctx, GEWICHTBRUTTO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.GewichtBrutto</code> attribute. 
	 * @param value the GewichtBrutto - Brutto Gewicht
	 */
	public void setGewichtBrutto(final String value)
	{
		setGewichtBrutto( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.GewichtEinheit</code> attribute.
	 * @return the GewichtEinheit - Artikel Gewichteinheit
	 */
	public EnumerationValue getGewichtEinheit(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, GEWICHTEINHEIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.GewichtEinheit</code> attribute.
	 * @return the GewichtEinheit - Artikel Gewichteinheit
	 */
	public EnumerationValue getGewichtEinheit()
	{
		return getGewichtEinheit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.GewichtEinheit</code> attribute. 
	 * @param value the GewichtEinheit - Artikel Gewichteinheit
	 */
	public void setGewichtEinheit(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, GEWICHTEINHEIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.GewichtEinheit</code> attribute. 
	 * @param value the GewichtEinheit - Artikel Gewichteinheit
	 */
	public void setGewichtEinheit(final EnumerationValue value)
	{
		setGewichtEinheit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.GewVE</code> attribute.
	 * @return the GewVE - Gewicht Verpackung ohne Inhalt
	 */
	public String getGewVE(final SessionContext ctx)
	{
		return (String)getProperty( ctx, GEWVE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.GewVE</code> attribute.
	 * @return the GewVE - Gewicht Verpackung ohne Inhalt
	 */
	public String getGewVE()
	{
		return getGewVE( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.GewVE</code> attribute. 
	 * @param value the GewVE - Gewicht Verpackung ohne Inhalt
	 */
	public void setGewVE(final SessionContext ctx, final String value)
	{
		setProperty(ctx, GEWVE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.GewVE</code> attribute. 
	 * @param value the GewVE - Gewicht Verpackung ohne Inhalt
	 */
	public void setGewVE(final String value)
	{
		setGewVE( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute.
	 * @return the is_bulletpoint_representative - Ist Stellvertreter für Bulletpoints?
	 */
	public Boolean isIs_bulletpoint_representative(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, IS_BULLETPOINT_REPRESENTATIVE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute.
	 * @return the is_bulletpoint_representative - Ist Stellvertreter für Bulletpoints?
	 */
	public Boolean isIs_bulletpoint_representative()
	{
		return isIs_bulletpoint_representative( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute. 
	 * @return the is_bulletpoint_representative - Ist Stellvertreter für Bulletpoints?
	 */
	public boolean isIs_bulletpoint_representativeAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIs_bulletpoint_representative( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute. 
	 * @return the is_bulletpoint_representative - Ist Stellvertreter für Bulletpoints?
	 */
	public boolean isIs_bulletpoint_representativeAsPrimitive()
	{
		return isIs_bulletpoint_representativeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute. 
	 * @param value the is_bulletpoint_representative - Ist Stellvertreter für Bulletpoints?
	 */
	public void setIs_bulletpoint_representative(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, IS_BULLETPOINT_REPRESENTATIVE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute. 
	 * @param value the is_bulletpoint_representative - Ist Stellvertreter für Bulletpoints?
	 */
	public void setIs_bulletpoint_representative(final Boolean value)
	{
		setIs_bulletpoint_representative( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute. 
	 * @param value the is_bulletpoint_representative - Ist Stellvertreter für Bulletpoints?
	 */
	public void setIs_bulletpoint_representative(final SessionContext ctx, final boolean value)
	{
		setIs_bulletpoint_representative( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.is_bulletpoint_representative</code> attribute. 
	 * @param value the is_bulletpoint_representative - Ist Stellvertreter für Bulletpoints?
	 */
	public void setIs_bulletpoint_representative(final boolean value)
	{
		setIs_bulletpoint_representative( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.is_marketing_representative</code> attribute.
	 * @return the is_marketing_representative - Ist Stellvertreter für Marketingtexte?
	 */
	public Boolean isIs_marketing_representative(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, IS_MARKETING_REPRESENTATIVE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.is_marketing_representative</code> attribute.
	 * @return the is_marketing_representative - Ist Stellvertreter für Marketingtexte?
	 */
	public Boolean isIs_marketing_representative()
	{
		return isIs_marketing_representative( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.is_marketing_representative</code> attribute. 
	 * @return the is_marketing_representative - Ist Stellvertreter für Marketingtexte?
	 */
	public boolean isIs_marketing_representativeAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isIs_marketing_representative( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.is_marketing_representative</code> attribute. 
	 * @return the is_marketing_representative - Ist Stellvertreter für Marketingtexte?
	 */
	public boolean isIs_marketing_representativeAsPrimitive()
	{
		return isIs_marketing_representativeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.is_marketing_representative</code> attribute. 
	 * @param value the is_marketing_representative - Ist Stellvertreter für Marketingtexte?
	 */
	public void setIs_marketing_representative(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, IS_MARKETING_REPRESENTATIVE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.is_marketing_representative</code> attribute. 
	 * @param value the is_marketing_representative - Ist Stellvertreter für Marketingtexte?
	 */
	public void setIs_marketing_representative(final Boolean value)
	{
		setIs_marketing_representative( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.is_marketing_representative</code> attribute. 
	 * @param value the is_marketing_representative - Ist Stellvertreter für Marketingtexte?
	 */
	public void setIs_marketing_representative(final SessionContext ctx, final boolean value)
	{
		setIs_marketing_representative( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.is_marketing_representative</code> attribute. 
	 * @param value the is_marketing_representative - Ist Stellvertreter für Marketingtexte?
	 */
	public void setIs_marketing_representative(final boolean value)
	{
		setIs_marketing_representative( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.lagerNr</code> attribute.
	 * @return the lagerNr
	 */
	public String getLagerNr(final SessionContext ctx)
	{
		return (String)getProperty( ctx, LAGERNR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.lagerNr</code> attribute.
	 * @return the lagerNr
	 */
	public String getLagerNr()
	{
		return getLagerNr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.lagerNr</code> attribute. 
	 * @param value the lagerNr
	 */
	public void setLagerNr(final SessionContext ctx, final String value)
	{
		setProperty(ctx, LAGERNR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.lagerNr</code> attribute. 
	 * @param value the lagerNr
	 */
	public void setLagerNr(final String value)
	{
		setLagerNr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.modelljahr</code> attribute.
	 * @return the modelljahr - Modelljahr
	 */
	public String getModelljahr(final SessionContext ctx)
	{
		return (String)getProperty( ctx, MODELLJAHR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.modelljahr</code> attribute.
	 * @return the modelljahr - Modelljahr
	 */
	public String getModelljahr()
	{
		return getModelljahr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.modelljahr</code> attribute. 
	 * @param value the modelljahr - Modelljahr
	 */
	public void setModelljahr(final SessionContext ctx, final String value)
	{
		setProperty(ctx, MODELLJAHR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.modelljahr</code> attribute. 
	 * @param value the modelljahr - Modelljahr
	 */
	public void setModelljahr(final String value)
	{
		setModelljahr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.OnlineREACHinfo</code> attribute.
	 * @return the OnlineREACHinfo
	 */
	public String getOnlineREACHinfo(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ONLINEREACHINFO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.OnlineREACHinfo</code> attribute.
	 * @return the OnlineREACHinfo
	 */
	public String getOnlineREACHinfo()
	{
		return getOnlineREACHinfo( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.OnlineREACHinfo</code> attribute. 
	 * @param value the OnlineREACHinfo
	 */
	public void setOnlineREACHinfo(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ONLINEREACHINFO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.OnlineREACHinfo</code> attribute. 
	 * @param value the OnlineREACHinfo
	 */
	public void setOnlineREACHinfo(final String value)
	{
		setOnlineREACHinfo( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.OnlineSicherheitsdatenblatt</code> attribute.
	 * @return the OnlineSicherheitsdatenblatt
	 */
	public String getOnlineSicherheitsdatenblatt(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ONLINESICHERHEITSDATENBLATT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.OnlineSicherheitsdatenblatt</code> attribute.
	 * @return the OnlineSicherheitsdatenblatt
	 */
	public String getOnlineSicherheitsdatenblatt()
	{
		return getOnlineSicherheitsdatenblatt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.OnlineSicherheitsdatenblatt</code> attribute. 
	 * @param value the OnlineSicherheitsdatenblatt
	 */
	public void setOnlineSicherheitsdatenblatt(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ONLINESICHERHEITSDATENBLATT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.OnlineSicherheitsdatenblatt</code> attribute. 
	 * @param value the OnlineSicherheitsdatenblatt
	 */
	public void setOnlineSicherheitsdatenblatt(final String value)
	{
		setOnlineSicherheitsdatenblatt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_breite</code> attribute.
	 * @return the packm_breite - Packmass Breite
	 */
	public String getPackm_breite(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PACKM_BREITE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_breite</code> attribute.
	 * @return the packm_breite - Packmass Breite
	 */
	public String getPackm_breite()
	{
		return getPackm_breite( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_breite</code> attribute. 
	 * @param value the packm_breite - Packmass Breite
	 */
	public void setPackm_breite(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PACKM_BREITE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_breite</code> attribute. 
	 * @param value the packm_breite - Packmass Breite
	 */
	public void setPackm_breite(final String value)
	{
		setPackm_breite( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_gewichteh</code> attribute.
	 * @return the packm_gewichteh - Packmass Gewicht Einheit
	 */
	public EnumerationValue getPackm_gewichteh(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, PACKM_GEWICHTEH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_gewichteh</code> attribute.
	 * @return the packm_gewichteh - Packmass Gewicht Einheit
	 */
	public EnumerationValue getPackm_gewichteh()
	{
		return getPackm_gewichteh( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_gewichteh</code> attribute. 
	 * @param value the packm_gewichteh - Packmass Gewicht Einheit
	 */
	public void setPackm_gewichteh(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, PACKM_GEWICHTEH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_gewichteh</code> attribute. 
	 * @param value the packm_gewichteh - Packmass Gewicht Einheit
	 */
	public void setPackm_gewichteh(final EnumerationValue value)
	{
		setPackm_gewichteh( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_hoehe</code> attribute.
	 * @return the packm_hoehe - Packmass HÃ¶he
	 */
	public String getPackm_hoehe(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PACKM_HOEHE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_hoehe</code> attribute.
	 * @return the packm_hoehe - Packmass HÃ¶he
	 */
	public String getPackm_hoehe()
	{
		return getPackm_hoehe( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_hoehe</code> attribute. 
	 * @param value the packm_hoehe - Packmass HÃ¶he
	 */
	public void setPackm_hoehe(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PACKM_HOEHE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_hoehe</code> attribute. 
	 * @param value the packm_hoehe - Packmass HÃ¶he
	 */
	public void setPackm_hoehe(final String value)
	{
		setPackm_hoehe( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_laenge</code> attribute.
	 * @return the packm_laenge - Packmass länge
	 */
	public String getPackm_laenge(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PACKM_LAENGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_laenge</code> attribute.
	 * @return the packm_laenge - Packmass länge
	 */
	public String getPackm_laenge()
	{
		return getPackm_laenge( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_laenge</code> attribute. 
	 * @param value the packm_laenge - Packmass länge
	 */
	public void setPackm_laenge(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PACKM_LAENGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_laenge</code> attribute. 
	 * @param value the packm_laenge - Packmass länge
	 */
	public void setPackm_laenge(final String value)
	{
		setPackm_laenge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_laengen_einheit</code> attribute.
	 * @return the packm_laengen_einheit - Packmass längen Einheit
	 */
	public EnumerationValue getPackm_laengen_einheit(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, PACKM_LAENGEN_EINHEIT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_laengen_einheit</code> attribute.
	 * @return the packm_laengen_einheit - Packmass längen Einheit
	 */
	public EnumerationValue getPackm_laengen_einheit()
	{
		return getPackm_laengen_einheit( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_laengen_einheit</code> attribute. 
	 * @param value the packm_laengen_einheit - Packmass längen Einheit
	 */
	public void setPackm_laengen_einheit(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, PACKM_LAENGEN_EINHEIT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_laengen_einheit</code> attribute. 
	 * @param value the packm_laengen_einheit - Packmass längen Einheit
	 */
	public void setPackm_laengen_einheit(final EnumerationValue value)
	{
		setPackm_laengen_einheit( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_volumen</code> attribute.
	 * @return the packm_volumen - Packmass Volumen
	 */
	public String getPackm_volumen(final SessionContext ctx)
	{
		return (String)getProperty( ctx, PACKM_VOLUMEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.packm_volumen</code> attribute.
	 * @return the packm_volumen - Packmass Volumen
	 */
	public String getPackm_volumen()
	{
		return getPackm_volumen( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_volumen</code> attribute. 
	 * @param value the packm_volumen - Packmass Volumen
	 */
	public void setPackm_volumen(final SessionContext ctx, final String value)
	{
		setProperty(ctx, PACKM_VOLUMEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.packm_volumen</code> attribute. 
	 * @param value the packm_volumen - Packmass Volumen
	 */
	public void setPackm_volumen(final String value)
	{
		setPackm_volumen( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.preview_etikett_image</code> attribute.
	 * @return the preview_etikett_image - Preview Etikett Image
	 */
	public abstract String getPreview_etikett_image(final SessionContext ctx);
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.preview_etikett_image</code> attribute.
	 * @return the preview_etikett_image - Preview Etikett Image
	 */
	public String getPreview_etikett_image()
	{
		return getPreview_etikett_image( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute.
	 * @return the Produkt_ueberprueft_SVHS - Produkt_ueberprueft_SVHS
	 */
	public Boolean isProdukt_ueberprueft_SVHS(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, PRODUKT_UEBERPRUEFT_SVHS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute.
	 * @return the Produkt_ueberprueft_SVHS - Produkt_ueberprueft_SVHS
	 */
	public Boolean isProdukt_ueberprueft_SVHS()
	{
		return isProdukt_ueberprueft_SVHS( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute. 
	 * @return the Produkt_ueberprueft_SVHS - Produkt_ueberprueft_SVHS
	 */
	public boolean isProdukt_ueberprueft_SVHSAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isProdukt_ueberprueft_SVHS( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute. 
	 * @return the Produkt_ueberprueft_SVHS - Produkt_ueberprueft_SVHS
	 */
	public boolean isProdukt_ueberprueft_SVHSAsPrimitive()
	{
		return isProdukt_ueberprueft_SVHSAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute. 
	 * @param value the Produkt_ueberprueft_SVHS - Produkt_ueberprueft_SVHS
	 */
	public void setProdukt_ueberprueft_SVHS(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, PRODUKT_UEBERPRUEFT_SVHS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute. 
	 * @param value the Produkt_ueberprueft_SVHS - Produkt_ueberprueft_SVHS
	 */
	public void setProdukt_ueberprueft_SVHS(final Boolean value)
	{
		setProdukt_ueberprueft_SVHS( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute. 
	 * @param value the Produkt_ueberprueft_SVHS - Produkt_ueberprueft_SVHS
	 */
	public void setProdukt_ueberprueft_SVHS(final SessionContext ctx, final boolean value)
	{
		setProdukt_ueberprueft_SVHS( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Produkt_ueberprueft_SVHS</code> attribute. 
	 * @param value the Produkt_ueberprueft_SVHS - Produkt_ueberprueft_SVHS
	 */
	public void setProdukt_ueberprueft_SVHS(final boolean value)
	{
		setProdukt_ueberprueft_SVHS( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_aircraft</code> attribute.
	 * @return the shop_aircraft - Aircraft
	 */
	public Boolean isShop_aircraft(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_AIRCRAFT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_aircraft</code> attribute.
	 * @return the shop_aircraft - Aircraft
	 */
	public Boolean isShop_aircraft()
	{
		return isShop_aircraft( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_aircraft</code> attribute. 
	 * @return the shop_aircraft - Aircraft
	 */
	public boolean isShop_aircraftAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_aircraft( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_aircraft</code> attribute. 
	 * @return the shop_aircraft - Aircraft
	 */
	public boolean isShop_aircraftAsPrimitive()
	{
		return isShop_aircraftAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_aircraft</code> attribute. 
	 * @param value the shop_aircraft - Aircraft
	 */
	public void setShop_aircraft(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_AIRCRAFT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_aircraft</code> attribute. 
	 * @param value the shop_aircraft - Aircraft
	 */
	public void setShop_aircraft(final Boolean value)
	{
		setShop_aircraft( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_aircraft</code> attribute. 
	 * @param value the shop_aircraft - Aircraft
	 */
	public void setShop_aircraft(final SessionContext ctx, final boolean value)
	{
		setShop_aircraft( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_aircraft</code> attribute. 
	 * @param value the shop_aircraft - Aircraft
	 */
	public void setShop_aircraft(final boolean value)
	{
		setShop_aircraft( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute.
	 * @return the shop_automotiv_aftersales - Automotiv aftersales
	 */
	public Boolean isShop_automotiv_aftersales(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_AUTOMOTIV_AFTERSALES);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute.
	 * @return the shop_automotiv_aftersales - Automotiv aftersales
	 */
	public Boolean isShop_automotiv_aftersales()
	{
		return isShop_automotiv_aftersales( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute. 
	 * @return the shop_automotiv_aftersales - Automotiv aftersales
	 */
	public boolean isShop_automotiv_aftersalesAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_automotiv_aftersales( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute. 
	 * @return the shop_automotiv_aftersales - Automotiv aftersales
	 */
	public boolean isShop_automotiv_aftersalesAsPrimitive()
	{
		return isShop_automotiv_aftersalesAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute. 
	 * @param value the shop_automotiv_aftersales - Automotiv aftersales
	 */
	public void setShop_automotiv_aftersales(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_AUTOMOTIV_AFTERSALES,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute. 
	 * @param value the shop_automotiv_aftersales - Automotiv aftersales
	 */
	public void setShop_automotiv_aftersales(final Boolean value)
	{
		setShop_automotiv_aftersales( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute. 
	 * @param value the shop_automotiv_aftersales - Automotiv aftersales
	 */
	public void setShop_automotiv_aftersales(final SessionContext ctx, final boolean value)
	{
		setShop_automotiv_aftersales( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_automotiv_aftersales</code> attribute. 
	 * @param value the shop_automotiv_aftersales - Automotiv aftersales
	 */
	public void setShop_automotiv_aftersales(final boolean value)
	{
		setShop_automotiv_aftersales( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bau</code> attribute.
	 * @return the shop_bau - Bau
	 */
	public Boolean isShop_bau(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_BAU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bau</code> attribute.
	 * @return the shop_bau - Bau
	 */
	public Boolean isShop_bau()
	{
		return isShop_bau( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bau</code> attribute. 
	 * @return the shop_bau - Bau
	 */
	public boolean isShop_bauAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_bau( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bau</code> attribute. 
	 * @return the shop_bau - Bau
	 */
	public boolean isShop_bauAsPrimitive()
	{
		return isShop_bauAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bau</code> attribute. 
	 * @param value the shop_bau - Bau
	 */
	public void setShop_bau(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_BAU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bau</code> attribute. 
	 * @param value the shop_bau - Bau
	 */
	public void setShop_bau(final Boolean value)
	{
		setShop_bau( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bau</code> attribute. 
	 * @param value the shop_bau - Bau
	 */
	public void setShop_bau(final SessionContext ctx, final boolean value)
	{
		setShop_bau( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bau</code> attribute. 
	 * @param value the shop_bau - Bau
	 */
	public void setShop_bau(final boolean value)
	{
		setShop_bau( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point1</code> attribute.
	 * @return the shop_bullet_point1
	 */
	public String getShop_bullet_point1(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_bullet_point1 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_BULLET_POINT1);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point1</code> attribute.
	 * @return the shop_bullet_point1
	 */
	public String getShop_bullet_point1()
	{
		return getShop_bullet_point1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point1</code> attribute. 
	 * @return the localized shop_bullet_point1
	 */
	public Map<Language,String> getAllShop_bullet_point1(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_BULLET_POINT1,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point1</code> attribute. 
	 * @return the localized shop_bullet_point1
	 */
	public Map<Language,String> getAllShop_bullet_point1()
	{
		return getAllShop_bullet_point1( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point1</code> attribute. 
	 * @param value the shop_bullet_point1
	 */
	public void setShop_bullet_point1(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_bullet_point1 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_BULLET_POINT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point1</code> attribute. 
	 * @param value the shop_bullet_point1
	 */
	public void setShop_bullet_point1(final String value)
	{
		setShop_bullet_point1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point1</code> attribute. 
	 * @param value the shop_bullet_point1
	 */
	public void setAllShop_bullet_point1(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_BULLET_POINT1,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point1</code> attribute. 
	 * @param value the shop_bullet_point1
	 */
	public void setAllShop_bullet_point1(final Map<Language,String> value)
	{
		setAllShop_bullet_point1( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point2</code> attribute.
	 * @return the shop_bullet_point2
	 */
	public String getShop_bullet_point2(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_bullet_point2 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_BULLET_POINT2);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point2</code> attribute.
	 * @return the shop_bullet_point2
	 */
	public String getShop_bullet_point2()
	{
		return getShop_bullet_point2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point2</code> attribute. 
	 * @return the localized shop_bullet_point2
	 */
	public Map<Language,String> getAllShop_bullet_point2(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_BULLET_POINT2,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point2</code> attribute. 
	 * @return the localized shop_bullet_point2
	 */
	public Map<Language,String> getAllShop_bullet_point2()
	{
		return getAllShop_bullet_point2( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point2</code> attribute. 
	 * @param value the shop_bullet_point2
	 */
	public void setShop_bullet_point2(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_bullet_point2 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_BULLET_POINT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point2</code> attribute. 
	 * @param value the shop_bullet_point2
	 */
	public void setShop_bullet_point2(final String value)
	{
		setShop_bullet_point2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point2</code> attribute. 
	 * @param value the shop_bullet_point2
	 */
	public void setAllShop_bullet_point2(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_BULLET_POINT2,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point2</code> attribute. 
	 * @param value the shop_bullet_point2
	 */
	public void setAllShop_bullet_point2(final Map<Language,String> value)
	{
		setAllShop_bullet_point2( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point3</code> attribute.
	 * @return the shop_bullet_point3
	 */
	public String getShop_bullet_point3(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_bullet_point3 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_BULLET_POINT3);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point3</code> attribute.
	 * @return the shop_bullet_point3
	 */
	public String getShop_bullet_point3()
	{
		return getShop_bullet_point3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point3</code> attribute. 
	 * @return the localized shop_bullet_point3
	 */
	public Map<Language,String> getAllShop_bullet_point3(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_BULLET_POINT3,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point3</code> attribute. 
	 * @return the localized shop_bullet_point3
	 */
	public Map<Language,String> getAllShop_bullet_point3()
	{
		return getAllShop_bullet_point3( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point3</code> attribute. 
	 * @param value the shop_bullet_point3
	 */
	public void setShop_bullet_point3(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_bullet_point3 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_BULLET_POINT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point3</code> attribute. 
	 * @param value the shop_bullet_point3
	 */
	public void setShop_bullet_point3(final String value)
	{
		setShop_bullet_point3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point3</code> attribute. 
	 * @param value the shop_bullet_point3
	 */
	public void setAllShop_bullet_point3(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_BULLET_POINT3,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point3</code> attribute. 
	 * @param value the shop_bullet_point3
	 */
	public void setAllShop_bullet_point3(final Map<Language,String> value)
	{
		setAllShop_bullet_point3( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point4</code> attribute.
	 * @return the shop_bullet_point4
	 */
	public String getShop_bullet_point4(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_bullet_point4 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_BULLET_POINT4);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point4</code> attribute.
	 * @return the shop_bullet_point4
	 */
	public String getShop_bullet_point4()
	{
		return getShop_bullet_point4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point4</code> attribute. 
	 * @return the localized shop_bullet_point4
	 */
	public Map<Language,String> getAllShop_bullet_point4(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_BULLET_POINT4,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point4</code> attribute. 
	 * @return the localized shop_bullet_point4
	 */
	public Map<Language,String> getAllShop_bullet_point4()
	{
		return getAllShop_bullet_point4( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point4</code> attribute. 
	 * @param value the shop_bullet_point4
	 */
	public void setShop_bullet_point4(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_bullet_point4 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_BULLET_POINT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point4</code> attribute. 
	 * @param value the shop_bullet_point4
	 */
	public void setShop_bullet_point4(final String value)
	{
		setShop_bullet_point4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point4</code> attribute. 
	 * @param value the shop_bullet_point4
	 */
	public void setAllShop_bullet_point4(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_BULLET_POINT4,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point4</code> attribute. 
	 * @param value the shop_bullet_point4
	 */
	public void setAllShop_bullet_point4(final Map<Language,String> value)
	{
		setAllShop_bullet_point4( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point5</code> attribute.
	 * @return the shop_bullet_point5
	 */
	public String getShop_bullet_point5(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_bullet_point5 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_BULLET_POINT5);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point5</code> attribute.
	 * @return the shop_bullet_point5
	 */
	public String getShop_bullet_point5()
	{
		return getShop_bullet_point5( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point5</code> attribute. 
	 * @return the localized shop_bullet_point5
	 */
	public Map<Language,String> getAllShop_bullet_point5(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_BULLET_POINT5,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point5</code> attribute. 
	 * @return the localized shop_bullet_point5
	 */
	public Map<Language,String> getAllShop_bullet_point5()
	{
		return getAllShop_bullet_point5( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point5</code> attribute. 
	 * @param value the shop_bullet_point5
	 */
	public void setShop_bullet_point5(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_bullet_point5 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_BULLET_POINT5,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point5</code> attribute. 
	 * @param value the shop_bullet_point5
	 */
	public void setShop_bullet_point5(final String value)
	{
		setShop_bullet_point5( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point5</code> attribute. 
	 * @param value the shop_bullet_point5
	 */
	public void setAllShop_bullet_point5(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_BULLET_POINT5,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point5</code> attribute. 
	 * @param value the shop_bullet_point5
	 */
	public void setAllShop_bullet_point5(final Map<Language,String> value)
	{
		setAllShop_bullet_point5( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point6</code> attribute.
	 * @return the shop_bullet_point6
	 */
	public String getShop_bullet_point6(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_bullet_point6 requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_BULLET_POINT6);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point6</code> attribute.
	 * @return the shop_bullet_point6
	 */
	public String getShop_bullet_point6()
	{
		return getShop_bullet_point6( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point6</code> attribute. 
	 * @return the localized shop_bullet_point6
	 */
	public Map<Language,String> getAllShop_bullet_point6(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_BULLET_POINT6,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_bullet_point6</code> attribute. 
	 * @return the localized shop_bullet_point6
	 */
	public Map<Language,String> getAllShop_bullet_point6()
	{
		return getAllShop_bullet_point6( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point6</code> attribute. 
	 * @param value the shop_bullet_point6
	 */
	public void setShop_bullet_point6(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_bullet_point6 requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_BULLET_POINT6,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point6</code> attribute. 
	 * @param value the shop_bullet_point6
	 */
	public void setShop_bullet_point6(final String value)
	{
		setShop_bullet_point6( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point6</code> attribute. 
	 * @param value the shop_bullet_point6
	 */
	public void setAllShop_bullet_point6(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_BULLET_POINT6,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_bullet_point6</code> attribute. 
	 * @param value the shop_bullet_point6
	 */
	public void setAllShop_bullet_point6(final Map<Language,String> value)
	{
		setAllShop_bullet_point6( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_description</code> attribute.
	 * @return the shop_description
	 */
	public String getShop_description(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_description requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_DESCRIPTION);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_description</code> attribute.
	 * @return the shop_description
	 */
	public String getShop_description()
	{
		return getShop_description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_description</code> attribute. 
	 * @return the localized shop_description
	 */
	public Map<Language,String> getAllShop_description(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_DESCRIPTION,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_description</code> attribute. 
	 * @return the localized shop_description
	 */
	public Map<Language,String> getAllShop_description()
	{
		return getAllShop_description( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_description</code> attribute. 
	 * @param value the shop_description
	 */
	public void setShop_description(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_description requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_description</code> attribute. 
	 * @param value the shop_description
	 */
	public void setShop_description(final String value)
	{
		setShop_description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_description</code> attribute. 
	 * @param value the shop_description
	 */
	public void setAllShop_description(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_DESCRIPTION,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_description</code> attribute. 
	 * @param value the shop_description
	 */
	public void setAllShop_description(final Map<Language,String> value)
	{
		setAllShop_description( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_esd</code> attribute.
	 * @return the shop_esd - ESD
	 */
	public Boolean isShop_esd(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_ESD);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_esd</code> attribute.
	 * @return the shop_esd - ESD
	 */
	public Boolean isShop_esd()
	{
		return isShop_esd( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_esd</code> attribute. 
	 * @return the shop_esd - ESD
	 */
	public boolean isShop_esdAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_esd( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_esd</code> attribute. 
	 * @return the shop_esd - ESD
	 */
	public boolean isShop_esdAsPrimitive()
	{
		return isShop_esdAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_esd</code> attribute. 
	 * @param value the shop_esd - ESD
	 */
	public void setShop_esd(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_ESD,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_esd</code> attribute. 
	 * @param value the shop_esd - ESD
	 */
	public void setShop_esd(final Boolean value)
	{
		setShop_esd( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_esd</code> attribute. 
	 * @param value the shop_esd - ESD
	 */
	public void setShop_esd(final SessionContext ctx, final boolean value)
	{
		setShop_esd( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_esd</code> attribute. 
	 * @param value the shop_esd - ESD
	 */
	public void setShop_esd(final boolean value)
	{
		setShop_esd( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_industrie</code> attribute.
	 * @return the shop_industrie - Industrie
	 */
	public Boolean isShop_industrie(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_INDUSTRIE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_industrie</code> attribute.
	 * @return the shop_industrie - Industrie
	 */
	public Boolean isShop_industrie()
	{
		return isShop_industrie( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_industrie</code> attribute. 
	 * @return the shop_industrie - Industrie
	 */
	public boolean isShop_industrieAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_industrie( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_industrie</code> attribute. 
	 * @return the shop_industrie - Industrie
	 */
	public boolean isShop_industrieAsPrimitive()
	{
		return isShop_industrieAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_industrie</code> attribute. 
	 * @param value the shop_industrie - Industrie
	 */
	public void setShop_industrie(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_INDUSTRIE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_industrie</code> attribute. 
	 * @param value the shop_industrie - Industrie
	 */
	public void setShop_industrie(final Boolean value)
	{
		setShop_industrie( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_industrie</code> attribute. 
	 * @param value the shop_industrie - Industrie
	 */
	public void setShop_industrie(final SessionContext ctx, final boolean value)
	{
		setShop_industrie( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_industrie</code> attribute. 
	 * @param value the shop_industrie - Industrie
	 */
	public void setShop_industrie(final boolean value)
	{
		setShop_industrie( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_ish</code> attribute.
	 * @return the shop_ish - ISH
	 */
	public Boolean isShop_ish(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_ISH);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_ish</code> attribute.
	 * @return the shop_ish - ISH
	 */
	public Boolean isShop_ish()
	{
		return isShop_ish( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_ish</code> attribute. 
	 * @return the shop_ish - ISH
	 */
	public boolean isShop_ishAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_ish( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_ish</code> attribute. 
	 * @return the shop_ish - ISH
	 */
	public boolean isShop_ishAsPrimitive()
	{
		return isShop_ishAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_ish</code> attribute. 
	 * @param value the shop_ish - ISH
	 */
	public void setShop_ish(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_ISH,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_ish</code> attribute. 
	 * @param value the shop_ish - ISH
	 */
	public void setShop_ish(final Boolean value)
	{
		setShop_ish( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_ish</code> attribute. 
	 * @param value the shop_ish - ISH
	 */
	public void setShop_ish(final SessionContext ctx, final boolean value)
	{
		setShop_ish( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_ish</code> attribute. 
	 * @param value the shop_ish - ISH
	 */
	public void setShop_ish(final boolean value)
	{
		setShop_ish( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_keywords</code> attribute.
	 * @return the shop_keywords
	 */
	public String getShop_keywords(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_keywords requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_KEYWORDS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_keywords</code> attribute.
	 * @return the shop_keywords
	 */
	public String getShop_keywords()
	{
		return getShop_keywords( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_keywords</code> attribute. 
	 * @return the localized shop_keywords
	 */
	public Map<Language,String> getAllShop_keywords(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_KEYWORDS,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_keywords</code> attribute. 
	 * @return the localized shop_keywords
	 */
	public Map<Language,String> getAllShop_keywords()
	{
		return getAllShop_keywords( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_keywords</code> attribute. 
	 * @param value the shop_keywords
	 */
	public void setShop_keywords(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_keywords requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_KEYWORDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_keywords</code> attribute. 
	 * @param value the shop_keywords
	 */
	public void setShop_keywords(final String value)
	{
		setShop_keywords( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_keywords</code> attribute. 
	 * @param value the shop_keywords
	 */
	public void setAllShop_keywords(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_KEYWORDS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_keywords</code> attribute. 
	 * @param value the shop_keywords
	 */
	public void setAllShop_keywords(final Map<Language,String> value)
	{
		setAllShop_keywords( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_modellbau</code> attribute.
	 * @return the shop_modellbau - Modellbau
	 */
	public Boolean isShop_modellbau(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_MODELLBAU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_modellbau</code> attribute.
	 * @return the shop_modellbau - Modellbau
	 */
	public Boolean isShop_modellbau()
	{
		return isShop_modellbau( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_modellbau</code> attribute. 
	 * @return the shop_modellbau - Modellbau
	 */
	public boolean isShop_modellbauAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_modellbau( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_modellbau</code> attribute. 
	 * @return the shop_modellbau - Modellbau
	 */
	public boolean isShop_modellbauAsPrimitive()
	{
		return isShop_modellbauAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_modellbau</code> attribute. 
	 * @param value the shop_modellbau - Modellbau
	 */
	public void setShop_modellbau(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_MODELLBAU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_modellbau</code> attribute. 
	 * @param value the shop_modellbau - Modellbau
	 */
	public void setShop_modellbau(final Boolean value)
	{
		setShop_modellbau( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_modellbau</code> attribute. 
	 * @param value the shop_modellbau - Modellbau
	 */
	public void setShop_modellbau(final SessionContext ctx, final boolean value)
	{
		setShop_modellbau( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_modellbau</code> attribute. 
	 * @param value the shop_modellbau - Modellbau
	 */
	public void setShop_modellbau(final boolean value)
	{
		setShop_modellbau( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute.
	 * @return the shop_schlosser_monteur - Schlosser/ Monteur
	 */
	public Boolean isShop_schlosser_monteur(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_SCHLOSSER_MONTEUR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute.
	 * @return the shop_schlosser_monteur - Schlosser/ Monteur
	 */
	public Boolean isShop_schlosser_monteur()
	{
		return isShop_schlosser_monteur( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute. 
	 * @return the shop_schlosser_monteur - Schlosser/ Monteur
	 */
	public boolean isShop_schlosser_monteurAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_schlosser_monteur( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute. 
	 * @return the shop_schlosser_monteur - Schlosser/ Monteur
	 */
	public boolean isShop_schlosser_monteurAsPrimitive()
	{
		return isShop_schlosser_monteurAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute. 
	 * @param value the shop_schlosser_monteur - Schlosser/ Monteur
	 */
	public void setShop_schlosser_monteur(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_SCHLOSSER_MONTEUR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute. 
	 * @param value the shop_schlosser_monteur - Schlosser/ Monteur
	 */
	public void setShop_schlosser_monteur(final Boolean value)
	{
		setShop_schlosser_monteur( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute. 
	 * @param value the shop_schlosser_monteur - Schlosser/ Monteur
	 */
	public void setShop_schlosser_monteur(final SessionContext ctx, final boolean value)
	{
		setShop_schlosser_monteur( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_schlosser_monteur</code> attribute. 
	 * @param value the shop_schlosser_monteur - Schlosser/ Monteur
	 */
	public void setShop_schlosser_monteur(final boolean value)
	{
		setShop_schlosser_monteur( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_schreiner</code> attribute.
	 * @return the shop_schreiner - Schreiner
	 */
	public Boolean isShop_schreiner(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_SCHREINER);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_schreiner</code> attribute.
	 * @return the shop_schreiner - Schreiner
	 */
	public Boolean isShop_schreiner()
	{
		return isShop_schreiner( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_schreiner</code> attribute. 
	 * @return the shop_schreiner - Schreiner
	 */
	public boolean isShop_schreinerAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_schreiner( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_schreiner</code> attribute. 
	 * @return the shop_schreiner - Schreiner
	 */
	public boolean isShop_schreinerAsPrimitive()
	{
		return isShop_schreinerAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_schreiner</code> attribute. 
	 * @param value the shop_schreiner - Schreiner
	 */
	public void setShop_schreiner(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_SCHREINER,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_schreiner</code> attribute. 
	 * @param value the shop_schreiner - Schreiner
	 */
	public void setShop_schreiner(final Boolean value)
	{
		setShop_schreiner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_schreiner</code> attribute. 
	 * @param value the shop_schreiner - Schreiner
	 */
	public void setShop_schreiner(final SessionContext ctx, final boolean value)
	{
		setShop_schreiner( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_schreiner</code> attribute. 
	 * @param value the shop_schreiner - Schreiner
	 */
	public void setShop_schreiner(final boolean value)
	{
		setShop_schreiner( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_title</code> attribute.
	 * @return the shop_title
	 */
	public String getShop_title(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getShop_title requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, SHOP_TITLE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_title</code> attribute.
	 * @return the shop_title
	 */
	public String getShop_title()
	{
		return getShop_title( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_title</code> attribute. 
	 * @return the localized shop_title
	 */
	public Map<Language,String> getAllShop_title(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,SHOP_TITLE,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_title</code> attribute. 
	 * @return the localized shop_title
	 */
	public Map<Language,String> getAllShop_title()
	{
		return getAllShop_title( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_title</code> attribute. 
	 * @param value the shop_title
	 */
	public void setShop_title(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setShop_title requires a session language", 0 );
		}
		setLocalizedProperty(ctx, SHOP_TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_title</code> attribute. 
	 * @param value the shop_title
	 */
	public void setShop_title(final String value)
	{
		setShop_title( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_title</code> attribute. 
	 * @param value the shop_title
	 */
	public void setAllShop_title(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,SHOP_TITLE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_title</code> attribute. 
	 * @param value the shop_title
	 */
	public void setAllShop_title(final Map<Language,String> value)
	{
		setAllShop_title( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_vde</code> attribute.
	 * @return the shop_vde - VDE
	 */
	public Boolean isShop_vde(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_VDE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_vde</code> attribute.
	 * @return the shop_vde - VDE
	 */
	public Boolean isShop_vde()
	{
		return isShop_vde( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_vde</code> attribute. 
	 * @return the shop_vde - VDE
	 */
	public boolean isShop_vdeAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_vde( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_vde</code> attribute. 
	 * @return the shop_vde - VDE
	 */
	public boolean isShop_vdeAsPrimitive()
	{
		return isShop_vdeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_vde</code> attribute. 
	 * @param value the shop_vde - VDE
	 */
	public void setShop_vde(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_VDE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_vde</code> attribute. 
	 * @param value the shop_vde - VDE
	 */
	public void setShop_vde(final Boolean value)
	{
		setShop_vde( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_vde</code> attribute. 
	 * @param value the shop_vde - VDE
	 */
	public void setShop_vde(final SessionContext ctx, final boolean value)
	{
		setShop_vde( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_vde</code> attribute. 
	 * @param value the shop_vde - VDE
	 */
	public void setShop_vde(final boolean value)
	{
		setShop_vde( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute.
	 * @return the shop_werkstatt_instandhaltung - Werkstatt / Instandhaltung
	 */
	public Boolean isShop_werkstatt_instandhaltung(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SHOP_WERKSTATT_INSTANDHALTUNG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute.
	 * @return the shop_werkstatt_instandhaltung - Werkstatt / Instandhaltung
	 */
	public Boolean isShop_werkstatt_instandhaltung()
	{
		return isShop_werkstatt_instandhaltung( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute. 
	 * @return the shop_werkstatt_instandhaltung - Werkstatt / Instandhaltung
	 */
	public boolean isShop_werkstatt_instandhaltungAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isShop_werkstatt_instandhaltung( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute. 
	 * @return the shop_werkstatt_instandhaltung - Werkstatt / Instandhaltung
	 */
	public boolean isShop_werkstatt_instandhaltungAsPrimitive()
	{
		return isShop_werkstatt_instandhaltungAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute. 
	 * @param value the shop_werkstatt_instandhaltung - Werkstatt / Instandhaltung
	 */
	public void setShop_werkstatt_instandhaltung(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SHOP_WERKSTATT_INSTANDHALTUNG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute. 
	 * @param value the shop_werkstatt_instandhaltung - Werkstatt / Instandhaltung
	 */
	public void setShop_werkstatt_instandhaltung(final Boolean value)
	{
		setShop_werkstatt_instandhaltung( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute. 
	 * @param value the shop_werkstatt_instandhaltung - Werkstatt / Instandhaltung
	 */
	public void setShop_werkstatt_instandhaltung(final SessionContext ctx, final boolean value)
	{
		setShop_werkstatt_instandhaltung( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.shop_werkstatt_instandhaltung</code> attribute. 
	 * @param value the shop_werkstatt_instandhaltung - Werkstatt / Instandhaltung
	 */
	public void setShop_werkstatt_instandhaltung(final boolean value)
	{
		setShop_werkstatt_instandhaltung( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute.
	 * @return the SVHC_Stoff_enthalten - SVHC_Stoff_enthalten
	 */
	public Boolean isSVHC_Stoff_enthalten(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, SVHC_STOFF_ENTHALTEN);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute.
	 * @return the SVHC_Stoff_enthalten - SVHC_Stoff_enthalten
	 */
	public Boolean isSVHC_Stoff_enthalten()
	{
		return isSVHC_Stoff_enthalten( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute. 
	 * @return the SVHC_Stoff_enthalten - SVHC_Stoff_enthalten
	 */
	public boolean isSVHC_Stoff_enthaltenAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isSVHC_Stoff_enthalten( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute. 
	 * @return the SVHC_Stoff_enthalten - SVHC_Stoff_enthalten
	 */
	public boolean isSVHC_Stoff_enthaltenAsPrimitive()
	{
		return isSVHC_Stoff_enthaltenAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute. 
	 * @param value the SVHC_Stoff_enthalten - SVHC_Stoff_enthalten
	 */
	public void setSVHC_Stoff_enthalten(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, SVHC_STOFF_ENTHALTEN,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute. 
	 * @param value the SVHC_Stoff_enthalten - SVHC_Stoff_enthalten
	 */
	public void setSVHC_Stoff_enthalten(final Boolean value)
	{
		setSVHC_Stoff_enthalten( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute. 
	 * @param value the SVHC_Stoff_enthalten - SVHC_Stoff_enthalten
	 */
	public void setSVHC_Stoff_enthalten(final SessionContext ctx, final boolean value)
	{
		setSVHC_Stoff_enthalten( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.SVHC_Stoff_enthalten</code> attribute. 
	 * @param value the SVHC_Stoff_enthalten - SVHC_Stoff_enthalten
	 */
	public void setSVHC_Stoff_enthalten(final boolean value)
	{
		setSVHC_Stoff_enthalten( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Ursprungsland</code> attribute.
	 * @return the Ursprungsland
	 */
	public String getUrsprungsland(final SessionContext ctx)
	{
		return (String)getProperty( ctx, URSPRUNGSLAND);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.Ursprungsland</code> attribute.
	 * @return the Ursprungsland
	 */
	public String getUrsprungsland()
	{
		return getUrsprungsland( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Ursprungsland</code> attribute. 
	 * @param value the Ursprungsland
	 */
	public void setUrsprungsland(final SessionContext ctx, final String value)
	{
		setProperty(ctx, URSPRUNGSLAND,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.Ursprungsland</code> attribute. 
	 * @param value the Ursprungsland
	 */
	public void setUrsprungsland(final String value)
	{
		setUrsprungsland( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, VALID_FROM);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.valid_from</code> attribute.
	 * @return the valid_from - Gültig ab
	 */
	public Date getValid_from()
	{
		return getValid_from( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, VALID_FROM,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.valid_from</code> attribute. 
	 * @param value the valid_from - Gültig ab
	 */
	public void setValid_from(final Date value)
	{
		setValid_from( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to(final SessionContext ctx)
	{
		return (Date)getProperty( ctx, VALID_TO);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.valid_to</code> attribute.
	 * @return the valid_to - Gültig bis
	 */
	public Date getValid_to()
	{
		return getValid_to( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final SessionContext ctx, final Date value)
	{
		setProperty(ctx, VALID_TO,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.valid_to</code> attribute. 
	 * @param value the valid_to - Gültig bis
	 */
	public void setValid_to(final Date value)
	{
		setValid_to( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variant_image</code> attribute.
	 * @return the variant_image - Varianten-Bild (Name)
	 */
	public String getVariant_image(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VARIANT_IMAGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variant_image</code> attribute.
	 * @return the variant_image - Varianten-Bild (Name)
	 */
	public String getVariant_image()
	{
		return getVariant_image( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variant_image</code> attribute. 
	 * @param value the variant_image - Varianten-Bild (Name)
	 */
	public void setVariant_image(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VARIANT_IMAGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variant_image</code> attribute. 
	 * @param value the variant_image - Varianten-Bild (Name)
	 */
	public void setVariant_image(final String value)
	{
		setVariant_image( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variantenNr</code> attribute.
	 * @return the variantenNr
	 */
	public String getVariantenNr(final SessionContext ctx)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.getVariantenNr requires a session language", 0 );
		}
		return (String)getLocalizedProperty( ctx, VARIANTENNR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variantenNr</code> attribute.
	 * @return the variantenNr
	 */
	public String getVariantenNr()
	{
		return getVariantenNr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variantenNr</code> attribute. 
	 * @return the localized variantenNr
	 */
	public Map<Language,String> getAllVariantenNr(final SessionContext ctx)
	{
		return (Map<Language,String>)getAllLocalizedProperties(ctx,VARIANTENNR,C2LManager.getInstance().getAllLanguages());
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variantenNr</code> attribute. 
	 * @return the localized variantenNr
	 */
	public Map<Language,String> getAllVariantenNr()
	{
		return getAllVariantenNr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variantenNr</code> attribute. 
	 * @param value the variantenNr
	 */
	public void setVariantenNr(final SessionContext ctx, final String value)
	{
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("GeneratedWeraProductSet.setVariantenNr requires a session language", 0 );
		}
		setLocalizedProperty(ctx, VARIANTENNR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variantenNr</code> attribute. 
	 * @param value the variantenNr
	 */
	public void setVariantenNr(final String value)
	{
		setVariantenNr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variantenNr</code> attribute. 
	 * @param value the variantenNr
	 */
	public void setAllVariantenNr(final SessionContext ctx, final Map<Language,String> value)
	{
		setAllLocalizedProperties(ctx,VARIANTENNR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variantenNr</code> attribute. 
	 * @param value the variantenNr
	 */
	public void setAllVariantenNr(final Map<Language,String> value)
	{
		setAllVariantenNr( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variantenNrEU</code> attribute.
	 * @return the variantenNrEU - Varianten-Nummer Handelsraum EU
	 */
	public String getVariantenNrEU(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VARIANTENNREU);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variantenNrEU</code> attribute.
	 * @return the variantenNrEU - Varianten-Nummer Handelsraum EU
	 */
	public String getVariantenNrEU()
	{
		return getVariantenNrEU( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variantenNrEU</code> attribute. 
	 * @param value the variantenNrEU - Varianten-Nummer Handelsraum EU
	 */
	public void setVariantenNrEU(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VARIANTENNREU,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variantenNrEU</code> attribute. 
	 * @param value the variantenNrEU - Varianten-Nummer Handelsraum EU
	 */
	public void setVariantenNrEU(final String value)
	{
		setVariantenNrEU( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variantenNrUS</code> attribute.
	 * @return the variantenNrUS - Varianten-Nummer Handelsraum US
	 */
	public String getVariantenNrUS(final SessionContext ctx)
	{
		return (String)getProperty( ctx, VARIANTENNRUS);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.variantenNrUS</code> attribute.
	 * @return the variantenNrUS - Varianten-Nummer Handelsraum US
	 */
	public String getVariantenNrUS()
	{
		return getVariantenNrUS( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variantenNrUS</code> attribute. 
	 * @param value the variantenNrUS - Varianten-Nummer Handelsraum US
	 */
	public void setVariantenNrUS(final SessionContext ctx, final String value)
	{
		setProperty(ctx, VARIANTENNRUS,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.variantenNrUS</code> attribute. 
	 * @param value the variantenNrUS - Varianten-Nummer Handelsraum US
	 */
	public void setVariantenNrUS(final String value)
	{
		setVariantenNrUS( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute.
	 * @return the xpace_breite_verpackung_produkt - XPace Breite Verpackung/Produkt
	 */
	public Double getXpace_breite_verpackung_produkt(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_BREITE_VERPACKUNG_PRODUKT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute.
	 * @return the xpace_breite_verpackung_produkt - XPace Breite Verpackung/Produkt
	 */
	public Double getXpace_breite_verpackung_produkt()
	{
		return getXpace_breite_verpackung_produkt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute. 
	 * @return the xpace_breite_verpackung_produkt - XPace Breite Verpackung/Produkt
	 */
	public double getXpace_breite_verpackung_produktAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_breite_verpackung_produkt( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute. 
	 * @return the xpace_breite_verpackung_produkt - XPace Breite Verpackung/Produkt
	 */
	public double getXpace_breite_verpackung_produktAsPrimitive()
	{
		return getXpace_breite_verpackung_produktAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute. 
	 * @param value the xpace_breite_verpackung_produkt - XPace Breite Verpackung/Produkt
	 */
	public void setXpace_breite_verpackung_produkt(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_BREITE_VERPACKUNG_PRODUKT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute. 
	 * @param value the xpace_breite_verpackung_produkt - XPace Breite Verpackung/Produkt
	 */
	public void setXpace_breite_verpackung_produkt(final Double value)
	{
		setXpace_breite_verpackung_produkt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute. 
	 * @param value the xpace_breite_verpackung_produkt - XPace Breite Verpackung/Produkt
	 */
	public void setXpace_breite_verpackung_produkt(final SessionContext ctx, final double value)
	{
		setXpace_breite_verpackung_produkt( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_breite_verpackung_produkt</code> attribute. 
	 * @param value the xpace_breite_verpackung_produkt - XPace Breite Verpackung/Produkt
	 */
	public void setXpace_breite_verpackung_produkt(final double value)
	{
		setXpace_breite_verpackung_produkt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_dateiname_prefix</code> attribute.
	 * @return the xpace_dateiname_prefix - XPace Dateiname Prefix
	 */
	public String getXpace_dateiname_prefix(final SessionContext ctx)
	{
		return (String)getProperty( ctx, XPACE_DATEINAME_PREFIX);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_dateiname_prefix</code> attribute.
	 * @return the xpace_dateiname_prefix - XPace Dateiname Prefix
	 */
	public String getXpace_dateiname_prefix()
	{
		return getXpace_dateiname_prefix( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_dateiname_prefix</code> attribute. 
	 * @param value the xpace_dateiname_prefix - XPace Dateiname Prefix
	 */
	public void setXpace_dateiname_prefix(final SessionContext ctx, final String value)
	{
		setProperty(ctx, XPACE_DATEINAME_PREFIX,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_dateiname_prefix</code> attribute. 
	 * @param value the xpace_dateiname_prefix - XPace Dateiname Prefix
	 */
	public void setXpace_dateiname_prefix(final String value)
	{
		setXpace_dateiname_prefix( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_default_z</code> attribute.
	 * @return the xpace_default_z - XPace Default Z
	 */
	public Double getXpace_default_z(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_DEFAULT_Z);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_default_z</code> attribute.
	 * @return the xpace_default_z - XPace Default Z
	 */
	public Double getXpace_default_z()
	{
		return getXpace_default_z( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_default_z</code> attribute. 
	 * @return the xpace_default_z - XPace Default Z
	 */
	public double getXpace_default_zAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_default_z( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_default_z</code> attribute. 
	 * @return the xpace_default_z - XPace Default Z
	 */
	public double getXpace_default_zAsPrimitive()
	{
		return getXpace_default_zAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_default_z</code> attribute. 
	 * @param value the xpace_default_z - XPace Default Z
	 */
	public void setXpace_default_z(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_DEFAULT_Z,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_default_z</code> attribute. 
	 * @param value the xpace_default_z - XPace Default Z
	 */
	public void setXpace_default_z(final Double value)
	{
		setXpace_default_z( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_default_z</code> attribute. 
	 * @param value the xpace_default_z - XPace Default Z
	 */
	public void setXpace_default_z(final SessionContext ctx, final double value)
	{
		setXpace_default_z( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_default_z</code> attribute. 
	 * @param value the xpace_default_z - XPace Default Z
	 */
	public void setXpace_default_z(final double value)
	{
		setXpace_default_z( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute.
	 * @return the xpace_euroloch_x_koordinate - XPace Euroloch x-Koordinate
	 */
	public Double getXpace_euroloch_x_koordinate(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_EUROLOCH_X_KOORDINATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute.
	 * @return the xpace_euroloch_x_koordinate - XPace Euroloch x-Koordinate
	 */
	public Double getXpace_euroloch_x_koordinate()
	{
		return getXpace_euroloch_x_koordinate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute. 
	 * @return the xpace_euroloch_x_koordinate - XPace Euroloch x-Koordinate
	 */
	public double getXpace_euroloch_x_koordinateAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_euroloch_x_koordinate( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute. 
	 * @return the xpace_euroloch_x_koordinate - XPace Euroloch x-Koordinate
	 */
	public double getXpace_euroloch_x_koordinateAsPrimitive()
	{
		return getXpace_euroloch_x_koordinateAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute. 
	 * @param value the xpace_euroloch_x_koordinate - XPace Euroloch x-Koordinate
	 */
	public void setXpace_euroloch_x_koordinate(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_EUROLOCH_X_KOORDINATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute. 
	 * @param value the xpace_euroloch_x_koordinate - XPace Euroloch x-Koordinate
	 */
	public void setXpace_euroloch_x_koordinate(final Double value)
	{
		setXpace_euroloch_x_koordinate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute. 
	 * @param value the xpace_euroloch_x_koordinate - XPace Euroloch x-Koordinate
	 */
	public void setXpace_euroloch_x_koordinate(final SessionContext ctx, final double value)
	{
		setXpace_euroloch_x_koordinate( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_euroloch_x_koordinate</code> attribute. 
	 * @param value the xpace_euroloch_x_koordinate - XPace Euroloch x-Koordinate
	 */
	public void setXpace_euroloch_x_koordinate(final double value)
	{
		setXpace_euroloch_x_koordinate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute.
	 * @return the xpace_euroloch_y_koordinate - XPace Euroloch y-Koordinate
	 */
	public Double getXpace_euroloch_y_koordinate(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_EUROLOCH_Y_KOORDINATE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute.
	 * @return the xpace_euroloch_y_koordinate - XPace Euroloch y-Koordinate
	 */
	public Double getXpace_euroloch_y_koordinate()
	{
		return getXpace_euroloch_y_koordinate( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute. 
	 * @return the xpace_euroloch_y_koordinate - XPace Euroloch y-Koordinate
	 */
	public double getXpace_euroloch_y_koordinateAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_euroloch_y_koordinate( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute. 
	 * @return the xpace_euroloch_y_koordinate - XPace Euroloch y-Koordinate
	 */
	public double getXpace_euroloch_y_koordinateAsPrimitive()
	{
		return getXpace_euroloch_y_koordinateAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute. 
	 * @param value the xpace_euroloch_y_koordinate - XPace Euroloch y-Koordinate
	 */
	public void setXpace_euroloch_y_koordinate(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_EUROLOCH_Y_KOORDINATE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute. 
	 * @param value the xpace_euroloch_y_koordinate - XPace Euroloch y-Koordinate
	 */
	public void setXpace_euroloch_y_koordinate(final Double value)
	{
		setXpace_euroloch_y_koordinate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute. 
	 * @param value the xpace_euroloch_y_koordinate - XPace Euroloch y-Koordinate
	 */
	public void setXpace_euroloch_y_koordinate(final SessionContext ctx, final double value)
	{
		setXpace_euroloch_y_koordinate( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_euroloch_y_koordinate</code> attribute. 
	 * @param value the xpace_euroloch_y_koordinate - XPace Euroloch y-Koordinate
	 */
	public void setXpace_euroloch_y_koordinate(final double value)
	{
		setXpace_euroloch_y_koordinate( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_gap_x</code> attribute.
	 * @return the xpace_gap_x - XPace Gap X
	 */
	public Double getXpace_gap_x(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_GAP_X);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_gap_x</code> attribute.
	 * @return the xpace_gap_x - XPace Gap X
	 */
	public Double getXpace_gap_x()
	{
		return getXpace_gap_x( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_gap_x</code> attribute. 
	 * @return the xpace_gap_x - XPace Gap X
	 */
	public double getXpace_gap_xAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_gap_x( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_gap_x</code> attribute. 
	 * @return the xpace_gap_x - XPace Gap X
	 */
	public double getXpace_gap_xAsPrimitive()
	{
		return getXpace_gap_xAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_gap_x</code> attribute. 
	 * @param value the xpace_gap_x - XPace Gap X
	 */
	public void setXpace_gap_x(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_GAP_X,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_gap_x</code> attribute. 
	 * @param value the xpace_gap_x - XPace Gap X
	 */
	public void setXpace_gap_x(final Double value)
	{
		setXpace_gap_x( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_gap_x</code> attribute. 
	 * @param value the xpace_gap_x - XPace Gap X
	 */
	public void setXpace_gap_x(final SessionContext ctx, final double value)
	{
		setXpace_gap_x( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_gap_x</code> attribute. 
	 * @param value the xpace_gap_x - XPace Gap X
	 */
	public void setXpace_gap_x(final double value)
	{
		setXpace_gap_x( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute.
	 * @return the xpace_gewicht_verpackung_produkt - XPace Gewicht Verpackung/Produkt
	 */
	public Double getXpace_gewicht_verpackung_produkt(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_GEWICHT_VERPACKUNG_PRODUKT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute.
	 * @return the xpace_gewicht_verpackung_produkt - XPace Gewicht Verpackung/Produkt
	 */
	public Double getXpace_gewicht_verpackung_produkt()
	{
		return getXpace_gewicht_verpackung_produkt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute. 
	 * @return the xpace_gewicht_verpackung_produkt - XPace Gewicht Verpackung/Produkt
	 */
	public double getXpace_gewicht_verpackung_produktAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_gewicht_verpackung_produkt( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute. 
	 * @return the xpace_gewicht_verpackung_produkt - XPace Gewicht Verpackung/Produkt
	 */
	public double getXpace_gewicht_verpackung_produktAsPrimitive()
	{
		return getXpace_gewicht_verpackung_produktAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute. 
	 * @param value the xpace_gewicht_verpackung_produkt - XPace Gewicht Verpackung/Produkt
	 */
	public void setXpace_gewicht_verpackung_produkt(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_GEWICHT_VERPACKUNG_PRODUKT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute. 
	 * @param value the xpace_gewicht_verpackung_produkt - XPace Gewicht Verpackung/Produkt
	 */
	public void setXpace_gewicht_verpackung_produkt(final Double value)
	{
		setXpace_gewicht_verpackung_produkt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute. 
	 * @param value the xpace_gewicht_verpackung_produkt - XPace Gewicht Verpackung/Produkt
	 */
	public void setXpace_gewicht_verpackung_produkt(final SessionContext ctx, final double value)
	{
		setXpace_gewicht_verpackung_produkt( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_gewicht_verpackung_produkt</code> attribute. 
	 * @param value the xpace_gewicht_verpackung_produkt - XPace Gewicht Verpackung/Produkt
	 */
	public void setXpace_gewicht_verpackung_produkt(final double value)
	{
		setXpace_gewicht_verpackung_produkt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_grifflaenge</code> attribute.
	 * @return the xpace_grifflaenge - XPace Grifflänge
	 */
	public Double getXpace_grifflaenge(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_GRIFFLAENGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_grifflaenge</code> attribute.
	 * @return the xpace_grifflaenge - XPace Grifflänge
	 */
	public Double getXpace_grifflaenge()
	{
		return getXpace_grifflaenge( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_grifflaenge</code> attribute. 
	 * @return the xpace_grifflaenge - XPace Grifflänge
	 */
	public double getXpace_grifflaengeAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_grifflaenge( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_grifflaenge</code> attribute. 
	 * @return the xpace_grifflaenge - XPace Grifflänge
	 */
	public double getXpace_grifflaengeAsPrimitive()
	{
		return getXpace_grifflaengeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_grifflaenge</code> attribute. 
	 * @param value the xpace_grifflaenge - XPace Grifflänge
	 */
	public void setXpace_grifflaenge(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_GRIFFLAENGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_grifflaenge</code> attribute. 
	 * @param value the xpace_grifflaenge - XPace Grifflänge
	 */
	public void setXpace_grifflaenge(final Double value)
	{
		setXpace_grifflaenge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_grifflaenge</code> attribute. 
	 * @param value the xpace_grifflaenge - XPace Grifflänge
	 */
	public void setXpace_grifflaenge(final SessionContext ctx, final double value)
	{
		setXpace_grifflaenge( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_grifflaenge</code> attribute. 
	 * @param value the xpace_grifflaenge - XPace Grifflänge
	 */
	public void setXpace_grifflaenge(final double value)
	{
		setXpace_grifflaenge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_haengbar</code> attribute.
	 * @return the xpace_haengbar - XPace Hängbar
	 */
	public Boolean isXpace_haengbar(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, XPACE_HAENGBAR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_haengbar</code> attribute.
	 * @return the xpace_haengbar - XPace Hängbar
	 */
	public Boolean isXpace_haengbar()
	{
		return isXpace_haengbar( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_haengbar</code> attribute. 
	 * @return the xpace_haengbar - XPace Hängbar
	 */
	public boolean isXpace_haengbarAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isXpace_haengbar( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_haengbar</code> attribute. 
	 * @return the xpace_haengbar - XPace Hängbar
	 */
	public boolean isXpace_haengbarAsPrimitive()
	{
		return isXpace_haengbarAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_haengbar</code> attribute. 
	 * @param value the xpace_haengbar - XPace Hängbar
	 */
	public void setXpace_haengbar(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, XPACE_HAENGBAR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_haengbar</code> attribute. 
	 * @param value the xpace_haengbar - XPace Hängbar
	 */
	public void setXpace_haengbar(final Boolean value)
	{
		setXpace_haengbar( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_haengbar</code> attribute. 
	 * @param value the xpace_haengbar - XPace Hängbar
	 */
	public void setXpace_haengbar(final SessionContext ctx, final boolean value)
	{
		setXpace_haengbar( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_haengbar</code> attribute. 
	 * @param value the xpace_haengbar - XPace Hängbar
	 */
	public void setXpace_haengbar(final boolean value)
	{
		setXpace_haengbar( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute.
	 * @return the xpace_hoehe_verpackung_produkt - XPace HÃ¶he Verpackung/Produkt
	 */
	public Double getXpace_hoehe_verpackung_produkt(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_HOEHE_VERPACKUNG_PRODUKT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute.
	 * @return the xpace_hoehe_verpackung_produkt - XPace HÃ¶he Verpackung/Produkt
	 */
	public Double getXpace_hoehe_verpackung_produkt()
	{
		return getXpace_hoehe_verpackung_produkt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute. 
	 * @return the xpace_hoehe_verpackung_produkt - XPace HÃ¶he Verpackung/Produkt
	 */
	public double getXpace_hoehe_verpackung_produktAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_hoehe_verpackung_produkt( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute. 
	 * @return the xpace_hoehe_verpackung_produkt - XPace HÃ¶he Verpackung/Produkt
	 */
	public double getXpace_hoehe_verpackung_produktAsPrimitive()
	{
		return getXpace_hoehe_verpackung_produktAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute. 
	 * @param value the xpace_hoehe_verpackung_produkt - XPace HÃ¶he Verpackung/Produkt
	 */
	public void setXpace_hoehe_verpackung_produkt(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_HOEHE_VERPACKUNG_PRODUKT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute. 
	 * @param value the xpace_hoehe_verpackung_produkt - XPace HÃ¶he Verpackung/Produkt
	 */
	public void setXpace_hoehe_verpackung_produkt(final Double value)
	{
		setXpace_hoehe_verpackung_produkt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute. 
	 * @param value the xpace_hoehe_verpackung_produkt - XPace HÃ¶he Verpackung/Produkt
	 */
	public void setXpace_hoehe_verpackung_produkt(final SessionContext ctx, final double value)
	{
		setXpace_hoehe_verpackung_produkt( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_hoehe_verpackung_produkt</code> attribute. 
	 * @param value the xpace_hoehe_verpackung_produkt - XPace HÃ¶he Verpackung/Produkt
	 */
	public void setXpace_hoehe_verpackung_produkt(final double value)
	{
		setXpace_hoehe_verpackung_produkt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute.
	 * @return the xpace_klingenlaenge - XPace Klingenlänge
	 */
	public Double getXpace_klingenlaenge(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_KLINGENLAENGE);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute.
	 * @return the xpace_klingenlaenge - XPace Klingenlänge
	 */
	public Double getXpace_klingenlaenge()
	{
		return getXpace_klingenlaenge( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute. 
	 * @return the xpace_klingenlaenge - XPace Klingenlänge
	 */
	public double getXpace_klingenlaengeAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_klingenlaenge( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute. 
	 * @return the xpace_klingenlaenge - XPace Klingenlänge
	 */
	public double getXpace_klingenlaengeAsPrimitive()
	{
		return getXpace_klingenlaengeAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute. 
	 * @param value the xpace_klingenlaenge - XPace Klingenlänge
	 */
	public void setXpace_klingenlaenge(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_KLINGENLAENGE,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute. 
	 * @param value the xpace_klingenlaenge - XPace Klingenlänge
	 */
	public void setXpace_klingenlaenge(final Double value)
	{
		setXpace_klingenlaenge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute. 
	 * @param value the xpace_klingenlaenge - XPace Klingenlänge
	 */
	public void setXpace_klingenlaenge(final SessionContext ctx, final double value)
	{
		setXpace_klingenlaenge( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_klingenlaenge</code> attribute. 
	 * @param value the xpace_klingenlaenge - XPace Klingenlänge
	 */
	public void setXpace_klingenlaenge(final double value)
	{
		setXpace_klingenlaenge( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_schuettbar</code> attribute.
	 * @return the xpace_schuettbar - XPace Schüttbar
	 */
	public Boolean isXpace_schuettbar(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, XPACE_SCHUETTBAR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_schuettbar</code> attribute.
	 * @return the xpace_schuettbar - XPace Schüttbar
	 */
	public Boolean isXpace_schuettbar()
	{
		return isXpace_schuettbar( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_schuettbar</code> attribute. 
	 * @return the xpace_schuettbar - XPace Schüttbar
	 */
	public boolean isXpace_schuettbarAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isXpace_schuettbar( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_schuettbar</code> attribute. 
	 * @return the xpace_schuettbar - XPace Schüttbar
	 */
	public boolean isXpace_schuettbarAsPrimitive()
	{
		return isXpace_schuettbarAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_schuettbar</code> attribute. 
	 * @param value the xpace_schuettbar - XPace Schüttbar
	 */
	public void setXpace_schuettbar(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, XPACE_SCHUETTBAR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_schuettbar</code> attribute. 
	 * @param value the xpace_schuettbar - XPace Schüttbar
	 */
	public void setXpace_schuettbar(final Boolean value)
	{
		setXpace_schuettbar( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_schuettbar</code> attribute. 
	 * @param value the xpace_schuettbar - XPace Schüttbar
	 */
	public void setXpace_schuettbar(final SessionContext ctx, final boolean value)
	{
		setXpace_schuettbar( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_schuettbar</code> attribute. 
	 * @param value the xpace_schuettbar - XPace Schüttbar
	 */
	public void setXpace_schuettbar(final boolean value)
	{
		setXpace_schuettbar( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_stellbar</code> attribute.
	 * @return the xpace_stellbar - XPace Stellbar
	 */
	public Boolean isXpace_stellbar(final SessionContext ctx)
	{
		return (Boolean)getProperty( ctx, XPACE_STELLBAR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_stellbar</code> attribute.
	 * @return the xpace_stellbar - XPace Stellbar
	 */
	public Boolean isXpace_stellbar()
	{
		return isXpace_stellbar( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_stellbar</code> attribute. 
	 * @return the xpace_stellbar - XPace Stellbar
	 */
	public boolean isXpace_stellbarAsPrimitive(final SessionContext ctx)
	{
		Boolean value = isXpace_stellbar( ctx );
		return value != null ? value.booleanValue() : false;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_stellbar</code> attribute. 
	 * @return the xpace_stellbar - XPace Stellbar
	 */
	public boolean isXpace_stellbarAsPrimitive()
	{
		return isXpace_stellbarAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_stellbar</code> attribute. 
	 * @param value the xpace_stellbar - XPace Stellbar
	 */
	public void setXpace_stellbar(final SessionContext ctx, final Boolean value)
	{
		setProperty(ctx, XPACE_STELLBAR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_stellbar</code> attribute. 
	 * @param value the xpace_stellbar - XPace Stellbar
	 */
	public void setXpace_stellbar(final Boolean value)
	{
		setXpace_stellbar( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_stellbar</code> attribute. 
	 * @param value the xpace_stellbar - XPace Stellbar
	 */
	public void setXpace_stellbar(final SessionContext ctx, final boolean value)
	{
		setXpace_stellbar( ctx,Boolean.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_stellbar</code> attribute. 
	 * @param value the xpace_stellbar - XPace Stellbar
	 */
	public void setXpace_stellbar(final boolean value)
	{
		setXpace_stellbar( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute.
	 * @return the xpace_tiefe_verpackung_produkt - XPace Tiefe Verpackung/Produkt
	 */
	public Double getXpace_tiefe_verpackung_produkt(final SessionContext ctx)
	{
		return (Double)getProperty( ctx, XPACE_TIEFE_VERPACKUNG_PRODUKT);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute.
	 * @return the xpace_tiefe_verpackung_produkt - XPace Tiefe Verpackung/Produkt
	 */
	public Double getXpace_tiefe_verpackung_produkt()
	{
		return getXpace_tiefe_verpackung_produkt( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute. 
	 * @return the xpace_tiefe_verpackung_produkt - XPace Tiefe Verpackung/Produkt
	 */
	public double getXpace_tiefe_verpackung_produktAsPrimitive(final SessionContext ctx)
	{
		Double value = getXpace_tiefe_verpackung_produkt( ctx );
		return value != null ? value.doubleValue() : 0.0d;
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute. 
	 * @return the xpace_tiefe_verpackung_produkt - XPace Tiefe Verpackung/Produkt
	 */
	public double getXpace_tiefe_verpackung_produktAsPrimitive()
	{
		return getXpace_tiefe_verpackung_produktAsPrimitive( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute. 
	 * @param value the xpace_tiefe_verpackung_produkt - XPace Tiefe Verpackung/Produkt
	 */
	public void setXpace_tiefe_verpackung_produkt(final SessionContext ctx, final Double value)
	{
		setProperty(ctx, XPACE_TIEFE_VERPACKUNG_PRODUKT,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute. 
	 * @param value the xpace_tiefe_verpackung_produkt - XPace Tiefe Verpackung/Produkt
	 */
	public void setXpace_tiefe_verpackung_produkt(final Double value)
	{
		setXpace_tiefe_verpackung_produkt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute. 
	 * @param value the xpace_tiefe_verpackung_produkt - XPace Tiefe Verpackung/Produkt
	 */
	public void setXpace_tiefe_verpackung_produkt(final SessionContext ctx, final double value)
	{
		setXpace_tiefe_verpackung_produkt( ctx,Double.valueOf( value ) );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_tiefe_verpackung_produkt</code> attribute. 
	 * @param value the xpace_tiefe_verpackung_produkt - XPace Tiefe Verpackung/Produkt
	 */
	public void setXpace_tiefe_verpackung_produkt(final double value)
	{
		setXpace_tiefe_verpackung_produkt( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_typ_verpackung</code> attribute.
	 * @return the xpace_typ_verpackung - XPace Typ Verpackung
	 */
	public EnumerationValue getXpace_typ_verpackung(final SessionContext ctx)
	{
		return (EnumerationValue)getProperty( ctx, XPACE_TYP_VERPACKUNG);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_typ_verpackung</code> attribute.
	 * @return the xpace_typ_verpackung - XPace Typ Verpackung
	 */
	public EnumerationValue getXpace_typ_verpackung()
	{
		return getXpace_typ_verpackung( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_typ_verpackung</code> attribute. 
	 * @param value the xpace_typ_verpackung - XPace Typ Verpackung
	 */
	public void setXpace_typ_verpackung(final SessionContext ctx, final EnumerationValue value)
	{
		setProperty(ctx, XPACE_TYP_VERPACKUNG,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_typ_verpackung</code> attribute. 
	 * @param value the xpace_typ_verpackung - XPace Typ Verpackung
	 */
	public void setXpace_typ_verpackung(final EnumerationValue value)
	{
		setXpace_typ_verpackung( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_variant_id</code> attribute.
	 * @return the xpace_variant_id - XPace Varianten-ID
	 */
	public String getXpace_variant_id(final SessionContext ctx)
	{
		return (String)getProperty( ctx, XPACE_VARIANT_ID);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.xpace_variant_id</code> attribute.
	 * @return the xpace_variant_id - XPace Varianten-ID
	 */
	public String getXpace_variant_id()
	{
		return getXpace_variant_id( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_variant_id</code> attribute. 
	 * @param value the xpace_variant_id - XPace Varianten-ID
	 */
	public void setXpace_variant_id(final SessionContext ctx, final String value)
	{
		setProperty(ctx, XPACE_VARIANT_ID,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.xpace_variant_id</code> attribute. 
	 * @param value the xpace_variant_id - XPace Varianten-ID
	 */
	public void setXpace_variant_id(final String value)
	{
		setXpace_variant_id( getSession().getSessionContext(), value );
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.ZolltarifNr</code> attribute.
	 * @return the ZolltarifNr
	 */
	public String getZolltarifNr(final SessionContext ctx)
	{
		return (String)getProperty( ctx, ZOLLTARIFNR);
	}
	
	/**
	 * <i>Generated method</i> - Getter of the <code>WeraProductSet.ZolltarifNr</code> attribute.
	 * @return the ZolltarifNr
	 */
	public String getZolltarifNr()
	{
		return getZolltarifNr( getSession().getSessionContext() );
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.ZolltarifNr</code> attribute. 
	 * @param value the ZolltarifNr
	 */
	public void setZolltarifNr(final SessionContext ctx, final String value)
	{
		setProperty(ctx, ZOLLTARIFNR,value);
	}
	
	/**
	 * <i>Generated method</i> - Setter of the <code>WeraProductSet.ZolltarifNr</code> attribute. 
	 * @param value the ZolltarifNr
	 */
	public void setZolltarifNr(final String value)
	{
		setZolltarifNr( getSession().getSessionContext(), value );
	}
	
}

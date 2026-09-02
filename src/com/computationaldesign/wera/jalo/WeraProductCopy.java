/*
 * [y] hybris Platform
 *
 * Copyright (c) 2000-2010 hybris AG
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of hybris
 * ("Confidential Information"). You shall not disclose such Confidential
 * Information and shall use it only in accordance with the terms of the
 * license agreement you entered into with hybris.
 * 
 *  
 */
package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;
import de.hybris.platform.catalog.jalo.classification.ClassificationAttribute;
import de.hybris.platform.catalog.jalo.classification.ClassificationClass;
import de.hybris.platform.catalog.constants.GeneratedCatalogConstants;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.c2l.C2LManager;
import de.hybris.platform.jalo.c2l.Language;
import de.hybris.platform.jalo.enumeration.EnumerationValue;
import de.hybris.platform.jalo.product.Product;
import de.hybris.platform.jalo.type.AttributeDescriptor;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.jalo.product.ProductManager;
import de.hybris.platform.variants.jalo.VariantProduct;
import de.hybris.platform.jalo.c2l.LocalizableItem;
import de.hybris.platform.jalo.SearchResult;
import de.hybris.platform.catalog.jalo.ProductFeature;
import de.hybris.platform.catalog.jalo.classification.util.Feature;
import de.hybris.platform.catalog.jalo.classification.util.TypedFeature;
import de.hybris.platform.jalo.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * This is the extension manager of the Wera extension.
 */
public class WeraProductCopy extends WeraManager {

	private static final Logger LOG = Logger.getLogger(WeraProductCopy.class.getName());
	private Product m_productCloned = null;
	private ArrayList<String> m_aErrorResult = null;
	private HashMap m_hashArticle = null;

	/**
	 * Gibt das neue Produkt zur�ck
	 * @return 
	 */
	public Product getClonedProduct() {
		// TODO Auto-generated method stub
		return m_productCloned;
	}

	/**
	 * Setzt das neue Produkt
	 * 
	 * @param productCloned 
	 */
	public void setClonedProduct(Product productCloned) {
		m_productCloned = productCloned;
	}

	/**
	 * Gibt das Fehlerprotokoll zur�ck
	 * 
	 * @return 
	 */
	public ArrayList<String> getErrorResult() {
		// TODO Auto-generated method stub
		return m_aErrorResult;
	}

	/**
	 * Kopieren eines kompletten Produktes inkl. aller Artikel 
	 * 
	 * @param productSrc
	 * @param strDestProduct
	 * @param hashArticle
	 * @param strProduktCodeNeuSet
	 * @return 
	 */
	public boolean bCloneProduct(Product productSrc, String strDestProduct, HashMap hashArticle, String strProduktCodeNeuSet, HashMap<String,String> hashArticleIndex ) {
		// --- Initialize
		boolean bProductCopyOk = true;

		// --- Liste der Artikel die umkopiert werden d�rfen / sollen
		if (m_hashArticle == null) {
			m_hashArticle = new HashMap();
		} else {
			m_hashArticle.clear();
		}
		m_hashArticle.putAll(hashArticle);

		// --- Pr�fe, ob das Zielpodukt, oder Artikel daraus bereits existiertieren
		//     Result= true Produkt oder Artikel exisitieren (siehe Result in m_aErrorResult)
		if (!_bCheck4Existing(strDestProduct, hashArticle, strProduktCodeNeuSet)) {
			// --- Kopieren eines kompletten Produktes inkl. aller Artikel 
			bProductCopyOk = _cloneProduct(productSrc, strDestProduct, strProduktCodeNeuSet, hashArticleIndex);
		} else // --- Fehler beim kopieren
		{
			bProductCopyOk = false;
		}

		return bProductCopyOk;
	}

	/**
	 * Kopieren eines kompletten Produktes inkl. aller Artikel 
	 * 
	 * @param productSrc
	 * @param strDestProduct
	 * @param strProduktCodeNeuSet
	 * @return 
	 */
	private boolean _cloneProduct(Product productSrc, String strDestProduct, String strProduktCodeNeuSet, HashMap<String,String> hashArticleIndex) {
		// --- Iniitialize
		String strResult = "";
		SessionContext sessioncontext = JaloSession.getCurrentSession().getSessionContext();
		ProductManager pm = ProductManager.getInstance();
		WeraManager wm = WeraManager.getInstance();
		Collection products = new ArrayList();
		WeraProduct productDest = null;
		Set descriptorsProduct = null;
		String strSrcProdukt = productSrc.getCode();

		// --- Hole das Original Produkt
		System.out.println("===>START <===Clone Produkt=");
		System.out.println("cloneProduct.strSrcProdukt=" + strSrcProdukt);
		System.out.println("cloneProduct.strDestProduct=" + strDestProduct);
		System.out.println("cloneProduct., strProduktCodeNeuSet=" + strProduktCodeNeuSet);

		// --- Hole das Orginal-Produkt
		if (productSrc != null) {

			// --- Iniitialize
			m_weraCatalogVersion = (CatalogVersion) getAttribute(productSrc, "catalogVersion");

			// --- Produkt anlegen
			Map mapAttributes = new HashMap();
			mapAttributes.put("code", strDestProduct);
			mapAttributes.put("catalogVersion", m_weraCatalogVersion);
			


			
			
			if (productSrc instanceof WeraProductSetinSet) {
				productDest = wm.createWeraProductSetinSet(mapAttributes);
				// --- Felder initialisieren (Produkt)
				descriptorsProduct = TypeManager.getInstance().getComposedType(WeraProductSetinSet.class).getAttributeDescriptors();
			} else {
				if (productSrc instanceof WeraProductSet) {
					mapAttributes.put("artnr", strProduktCodeNeuSet);
					productDest = wm.createWeraProductSet(mapAttributes);

					// --- Felder initialisieren (Produkt)
					descriptorsProduct = TypeManager.getInstance().getComposedType(WeraProductSet.class).getAttributeDescriptors();
				} else {
					productDest = wm.createWeraProduct(mapAttributes);
					// --- Felder initialisieren (Produkt)
					descriptorsProduct = TypeManager.getInstance().getComposedType(WeraProduct.class).getAttributeDescriptors();
				}
			}
			if (productDest == null) {
				strResult = "++++ERROR+++ cloneProduct.strDestProduct=" + strDestProduct;
				m_aErrorResult.add(strResult);
				System.out.println(strResult);
				return false;
			}
			if (descriptorsProduct == null) {
				strResult = "++++ERROR+++ cloneProduct.descriptorsProduct=" + descriptorsProduct;
				m_aErrorResult.add(strResult);
				System.out.println(strResult);
				return false;
			}

			// --- Hole alle Categories f�r das Product
			Collection colCategories = (Collection) getAttribute(productSrc, "supercategories");
			Collection colNewCategories = new ArrayList();
			boolean bSkipCategory = true;
			// --- Schleife �ber alle Kategorien
			for (Iterator it1 = colCategories.iterator(); it1.hasNext();) {

				// --- Hole n�chste kategorie
				Category oCategory = (Category) it1.next();

				// --- FILTER -----------------------------------------------------------
				// --- Nur klassifizierende Kategorien oder Kategorien aus Katalogversion "weramaster"
				bSkipCategory = true;
				if (oCategory instanceof ClassificationClass) {
					bSkipCategory = false;
				} else {
					CatalogVersion catalogVersion = (CatalogVersion) getAttribute(oCategory, "catalogVersion");
					if (catalogVersion != null && catalogVersion.getVersion().equals("weramaster")) {
						bSkipCategory = false;
					}
				}
				// --- FILTER -----------------------------------------------------------

				// --- 
				if (!bSkipCategory) // --- ok, we take this
				{
					colNewCategories.add(oCategory);
				}

			} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();)

			// --- Hole Wera Category
			setAttribute(productDest, "supercategories", colNewCategories);

			// --- �bertragung der Elemente
			_cloneItems(descriptorsProduct, productSrc, productDest);

			// --- Index Alphanum. (START) ------------------------------------------------------------------------------------------------
			//
			// --- preset / hole sprachen
			Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();
			final Collection colLang = C2LManager.getInstance().getAllLanguages();

			// --- schleife �ber alle Sprachen
			for (final Iterator it1 = colLang.iterator(); it1.hasNext();) {

				final Language lang1 = (Language) it1.next();
				final String currentLanguage = lang1.getIsoCode();
				String artikelnr_index		= hashArticleIndex.get(currentLanguage);

				// --- setze die aritkelnummer
				wm.SetLanguage(currentLanguage);
				wm.setAttribute( (Item)productDest, "artikelnr_index", artikelnr_index );
			}
			wm.SetLanguage( currentSessionLanguage.getIsoCode() );
			// --- Index Alphanum. (ENDE) ------------------------------------------------------------------------------------------------
			
			
			// --- Felder initialisieren (Variante)
			ComposedType cpWV = TypeManager.getInstance().getComposedType("WeraVariante");
			Set descriptorsVariante = cpWV.getAttributeDescriptors();

			// --- Hole alle Artikel f�r das Product -------------------------------------------- START
			if ((productSrc instanceof WeraProductSet == false && productSrc instanceof WeraProductSetinSet == false)) {

				Collection colVariants = (Collection) getAttribute(productSrc, "variants");
				Collection colNewVariants = new ArrayList();
				VariantProduct oVariante = null;
				for (Iterator it2 = colVariants.iterator(); it2.hasNext();) {
					// --- Hole Name
					Product oVariant = (Product) it2.next();

					// --- Wurde w�r diesen Artikel ein Code
					if (m_hashArticle.containsKey(oVariant.getPK())) {
						// --- Kopieren eines Artikels
						String strCodeNew = (String) m_hashArticle.get(oVariant.getPK());
						if (strCodeNew.length() > 0) {
							System.out.println("===>START <===Clone Artikel. CODE=" + oVariant.getCode());
							oVariante = _cloneArtikel(productDest, descriptorsVariante, oVariant.getCode(), strCodeNew);
							colNewVariants.add(oVariante);
							System.out.println("===>ENDE <===Clone Artikel. CODE=" + oVariant.getCode());
						} else {
							System.out.println("+++SKIP ARTIKEL-CODE=" + oVariant.getCode());
						}
					}
				}
				setAttribute(productDest, "variants", colNewVariants);
			} else {
				System.out.println("WERAPRODUCTSET => Skipping cloneArtikels");
			}
			// --- Hole alle Artikel f�r das Product -------------------------------------------- ENDE

		} else {
			strResult = "++++ERROR+++ cloneProduct.productSrc=null";
			System.out.println(strResult);
		}

		// --- Setzt das neue Produkt
		setClonedProduct(productDest);

		// --- Fertig
		System.out.println("===>ENDE <===Clone Produkt=");

		return true;
	}

	// --------------------------------------------------------------------------------------------------------------------------------	
	// --- Hilfsfunktionen
	// --------------------------------------------------------------------------------------------------------------------------------	
	/**
	 * Pr�fe, ob das Zielpodukt, oder Artikel daraus bereits existiertieren Result= true Produkt oder Artikel exisitieren (siehe Result in m_aErrorResult)
	 * 
	 * @param strDestProduct
	 * @param hashArticle
	 * @param strProduktCodeNeuSet
	 * @return 
	 */
	boolean _bCheck4Existing(String strDestProduct, HashMap hashArticle, String strProduktCodeNeuSet) {

		// --- Initialize
		m_aErrorResult = new ArrayList<String>();
		boolean bExisting = false;
		String strCode = "";
		String strSearch = "";
		WeraProduct productDest = null;
		VariantProduct articleDest = null;
		SearchResult res = null;

		// --- Pr�fe, ob das Produkt bereits existiert
		strSearch = "select {pk} from {Product} WHERE code='" + strDestProduct + "'";
		res = serachItem(strSearch, Collections.singletonList(WeraProduct.class));
		if (res != null && res.getCount() > 0) {
			productDest = (WeraProduct) res.getResult().get(0);

			// --- Error Message einrichten
			m_aErrorResult.add("Produkt existiert bereits=" + strDestProduct);

			// --- Produkt existiert bereits
			bExisting = true;
		}

		// --- Pr�fe ob der Satz schon existiert
		if (strProduktCodeNeuSet.length() > 0) {

			strSearch = "select {pk} from {WeraProductSet} WHERE p_artnr='" + strProduktCodeNeuSet + "'";
			res = serachItem(strSearch, Collections.singletonList(WeraProductSet.class));
			if (res != null && res.getCount() > 0) {
				// --- Error Message einrichten
				m_aErrorResult.add("ProduktSet existiert bereits=" + strProduktCodeNeuSet);

				// --- Produkt existiert bereits
				bExisting = true;
			}

		} // ---if ( strProduktCodeNeuSet.lenght() > 0 ) {

		// --- Pr�fe, ob einer der Artikel bereits existiert
		for (Iterator it1 = hashArticle.values().iterator(); it1.hasNext();) {
			strCode = (String) it1.next();
			strSearch = "select {pk} from {VariantProduct} WHERE code='" + strCode + "'";
			res = serachItem(strSearch, Collections.singletonList(WeraProduct.class));
			if (res.getCount() > 0) {
				articleDest = (VariantProduct) res.getResult().get(0);

				// --- Error Message einrichten
				m_aErrorResult.add("Artikel existiert bereits=" + strCode);

				// --- Artikel existiert bereits
				bExisting = true;
			}

		} // --- for ( Iterator it1=hashArticle.iterator(); it1.hasNext() ) {

		return bExisting;
	}

	/**
	 * Kopieren eines Artikels
	 * 
	 * @param oBaseProdukt
	 * @param descriptors
	 * @param strSrcProdukt
	 * @param strDestProduct
	 * @return 
	 */
	private VariantProduct _cloneArtikel(Product oBaseProdukt, Set descriptors, String strSrcProdukt, String strDestProduct) {
		// --- Initialize
		SessionContext sessioncontext = JaloSession.getCurrentSession().getSessionContext();
		ProductManager pm = ProductManager.getInstance();
		WeraManager wm = WeraManager.getInstance();
		Collection products = new ArrayList();
		VariantProduct oVariantProductSrc = null;
		VariantProduct oVariantProductDest = null;

		String strSearch = "select {pk} from {VariantProduct} WHERE code='" + strSrcProdukt + "'";
		SearchResult res = serachItem(strSearch, Collections.singletonList(VariantProduct.class));
		if (res.getCount() > 0) {
			// --- Hole die Original-Variante
			oVariantProductSrc = (VariantProduct) res.getResult().get(0);
			if (oVariantProductSrc == null) {
				System.out.println("oVariantProductSrc not found=" + strSrcProdukt);
				return null;
			}

			// --- Artikel / Variante anlegen
			Map mapAttributes = new HashMap();
			mapAttributes.put("code", strDestProduct);
			mapAttributes.put("baseProduct", oBaseProdukt);
			mapAttributes.put("catalogVersion", m_weraCatalogVersion);
			oVariantProductDest = wm.createWeraProductVariante(mapAttributes);
			if (oVariantProductDest == null) {
				System.out.println("oVariantProductDest-Error=" + strDestProduct);
				return null;
			}

			// --- Hole alle Categories f�r die Variante
			Collection colCategories = (Collection) getAttribute(oVariantProductSrc, "supercategories");
			Collection colNewCategories = new ArrayList();
			// --- Schleife �ber alle Kategorien
			for (Iterator it1 = colCategories.iterator(); it1.hasNext();) {
				// --- Hole Kategorie
				Category oCategory = (Category) it1.next();

				// --- Handelt sich um eine Wera Category, dann l�schen
				colNewCategories.add(oCategory);

			} // --- for (Iterator it1 = result1.iterator(); it1.hasNext();)

			// --- Hole Wera Category
			setAttribute(oVariantProductDest, "supercategories", colNewCategories);

			// --- �bertragung der Elemente
			_cloneItems(descriptors, oVariantProductSrc, oVariantProductDest);
		}

		return oVariantProductDest;
	}

	/**
	 * 
	 * @param oProductSrc
	 * @param oProductDest
	 * @param strAttribute 
	 */
	public void transferAttribute(LocalizableItem oProductSrc, LocalizableItem oProductDest, String strAttribute) {

		// --- Hole Wert
		Object oObject = getAttribute(oProductSrc, strAttribute);

		// --- Setze Wert
		if (oObject != null) {
			setAttribute(oProductDest, strAttribute, oObject);
		}
	}

	/**
	 * 
	 * @param oProductSrc
	 * @param oProductDest
	 * @param strAttribute 
	 */
	public void transferLocalizedValues(LocalizableItem oProductSrc, LocalizableItem oProductDest, String strAttribute) {
		final Language currentSessionLanguage = JaloSession.getCurrentSession().getSessionContext().getLanguage();
		// --- Hole alle Werte der Map
		Collection colLang = C2LManager.getInstance().getAllLanguages();
		for (Iterator it0 = colLang.iterator(); it0.hasNext();) {
			// --- Sprache aktivieren
			Language lang1 = (Language) it0.next();
			SetLanguage(lang1.getIsoCode());

			// --- Setze Map
			Object colSrcMap = oProductSrc.getLocalizedProperty(strAttribute);
			if (colSrcMap != null) {
				oProductDest.setLocalizedProperty(strAttribute, colSrcMap);
			}
		}
		SetLanguage(currentSessionLanguage.getIsoCode());
	}

	/**
	 * 
	 * @param oSrcObject
	 * @param oProduct
	 * @param hashItem
	 * @return 
	 */
	public Object _cloneItem(LocalizableItem oSrcObject, Product oProduct, HashMap hashItem) {
		
		// --- Initialize
		ComposedType cp = oSrcObject.getComposedType();
		Set descriptors = cp.getAttributeDescriptors();
		LocalizableItem newItem = null;

		// --- Neue Instance mit den Werten anlegen
		try {
			// --- Neu anlegen falls keine Vorbelegung existiert!
			if (hashItem == null) {
				hashItem = new HashMap();
			}
			newItem = (LocalizableItem) cp.newInstance(hashItem);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// --- Felder initialisieren
		if (newItem != null) {

			for (Iterator it1 = descriptors.iterator(); it1.hasNext();) {
				// --- Hole Attribut-describtor
				AttributeDescriptor oAD = (AttributeDescriptor) it1.next();

				// --- F�lle Map
				if (oAD.getQualifier().equals("pk") == false) {
					if (oAD.isWritable()) {
						if (oAD.isLocalized()) {
							transferLocalizedValues(oSrcObject, newItem, oAD.getQualifier());
						} else {
							transferAttribute(oSrcObject, newItem, oAD.getQualifier());
						}
					}
				}

			} // --- for (Iterator it1 = descriptors.iterator(); it1.hasNext();) {
		}

		return newItem;
	}

	/**
	 * Kopieren und anlegen eines Productfeatures
	 * 
	 * @param product
	 * @param oProductFeature
	 * @return 
	 */
	protected ProductFeature _copyProductFeature(final Product product, ProductFeature oProductFeature) {
		
		// --- Initialize
		//System.out.println ( "oProductFeature.qualifier=" + getAttribute ( oProductFeature, "qualifier" ) );
		ClassAttributeAssignment caa = oProductFeature.getClassificationAttributeAssignment();
		if (caa == null) {
			return null;
		}
		ClassificationClass cc = caa.getClassificationClass();
		ClassificationAttribute ca = caa.getClassificationAttribute();

		EnumerationValue ev = caa.getAttributeType();
		if (ev.getCode().equals(GeneratedCatalogConstants.Enumerations.ClassificationAttributeTypeEnum.BOOLEAN)) {
			// --- Boolean Value
			Boolean bCAV = (Boolean) oProductFeature.getValue();
			final TypedFeature<Boolean> BooleanFeature = Feature.loadTyped(product, caa);
			// --- now create it
			BooleanFeature.createValue(bCAV);
			try {
				BooleanFeature.getParent().store();
			} catch (final ConsistencyCheckException e) {
				e.printStackTrace();
			}
		} else {
			// --- String Value
			String sCodeCAV = (String) oProductFeature.getValue();
			final TypedFeature<String> StringFeature = Feature.loadTyped(product, caa);
			// --- now create it
			StringFeature.createValue(sCodeCAV);
			try {
				StringFeature.getParent().store();
			} catch (final ConsistencyCheckException e) {
				e.printStackTrace();
			}
		}
		//System.out.println ( "cc.qualifier=" + cc.getCode() );

		return null;
	}

	/**
	 * 
	 * @param descriptors
	 * @param oVariantProductSrc
	 * @param oVariantProductDest 
	 */
	public void _cloneItems(Set descriptors, Product oVariantProductSrc, Product oVariantProductDest) {
		
		// --- Initialize
		boolean bCopy = false;
		SessionContext sessioncontext = JaloSession.getCurrentSession().getSessionContext();

		// --- Schleife �ber alle Felder
		for (Iterator it1 = descriptors.iterator(); it1.hasNext();) {
			// --- Hole Attribut-describtor
			AttributeDescriptor oAD = (AttributeDescriptor) it1.next();

			// --- Nur bei S�tzen das Feld Artnr �berspringen
			if (oVariantProductDest instanceof WeraProductSet) {
				if (!(oVariantProductDest instanceof WeraProductSetinSet) && oAD.getQualifier().equals("artnr")) {
					System.out.println("SKIP-Attribute Class=" + oVariantProductDest.getClass().toString() + " =>" + oAD.getQualifier());
					continue;
				}
			}

			// --- �bertrage Attribute  
			if (	// --- XPACE alle Felder
					oAD.getQualifier().contains("xpace_") == false

					// --- Multimedia
					&& oAD.getQualifier().equals("variant_image") == false
					&& oAD.getQualifier().equals("etikett_image") == false
					&& oAD.getQualifier().equals("others_productpictures") == false
					
					// --- Onlineshops
					&& oAD.getQualifier().equals("shop_title") == false
					&& oAD.getQualifier().equals("shop_description") == false
					&& oAD.getQualifier().equals("shop_keywords") == false
					&& oAD.getQualifier().contains("shop_bullet_point") == false
					&& oAD.getQualifier().equals("weraproductset2bulletpoints") == false
					&& oAD.getQualifier().equals("artikel_kurztext") == false
					&& oAD.getQualifier().equals("amazon_de_cat1") == false
					&& oAD.getQualifier().equals("amazon_de_cat2") == false
					&& oAD.getQualifier().equals("amazon_de_cat3") == false
					&& oAD.getQualifier().equals("amazon_de_cat4") == false
					&& oAD.getQualifier().equals("asin_de") == false
					&& oAD.getQualifier().equals("asin_fr") == false
					&& oAD.getQualifier().equals("asin_it") == false
					&& oAD.getQualifier().equals("asin_uk") == false
					&& oAD.getQualifier().equals("asin_es") == false
					&& oAD.getQualifier().equals("asin_us") == false
					&& oAD.getQualifier().equals("modelljahr") == false
					&& oAD.getQualifier().equals("shop_industrie") == false
					&& oAD.getQualifier().equals("shop_esd") == false
					&& oAD.getQualifier().equals("shop_schlosser_monteur") == false
					&& oAD.getQualifier().equals("shop_ish") == false
					&& oAD.getQualifier().equals("shop_aircraft") == false
					&& oAD.getQualifier().equals("shop_modelbau") == false
					&& oAD.getQualifier().equals("shop_vde") == false
					&& oAD.getQualifier().equals("shop_werkstatt_instandhaltung") == false
					&& oAD.getQualifier().equals("shop_schreiner") == false
					&& oAD.getQualifier().equals("shop_automotiv_aftersales") == false
					&& oAD.getQualifier().equals("shop_bau") == false

					// --- ausgabesteuerung
					&& oAD.getQualifier().equals("alternate_product") == false
					
					// --- erweiterte eigenschaften
					&& oAD.getQualifier().equals("artikelnr_index") == false

					// --- sbvarianten
					&& oAD.getQualifier().equals("weraproducts_relation") == false
					&& oAD.getQualifier().equals("weraproductsetinsets_relation") == false

					&& oAD.getQualifier().equals("weblinks") == false
					&& oAD.getQualifier().equals("productReferences") == false
					&& oAD.getQualifier().equals("keywords") == false
					&& oAD.getQualifier().equals("pictures1") == false
					&& oAD.getQualifier().equals("pictures2") == false
					&& oAD.getQualifier().equals("pictures3") == false
					&& oAD.getQualifier().equals("ean") == false
					&& oAD.getQualifier().equals("ean_us") == false
					&& oAD.getQualifier().equals("featureicons1") == false
					&& oAD.getQualifier().equals("variants") == false
					&& oAD.getQualifier().equals("pk") == false
					&& oAD.getQualifier().equals("partOf") == false
					&& oAD.getQualifier().equals("baseProduct") == false
					&& oAD.getQualifier().equals("supercategories") == false
					&& oAD.getQualifier().equals("untypedFeatures") == false
					&& oAD.getQualifier().equals("features") == false
					&& oAD.getQualifier().equals("code") == false
					&& oAD.getQualifier().equals("outputcontrols") == false
					&& oAD.getQualifier().equals("category2productexts") == false) {

				if (oAD.isWritable()) {
					if (oAD.isLocalized()) // --- Attribut is localized
					{
						transferLocalizedValues(oVariantProductSrc, oVariantProductDest, oAD.getQualifier());
					} else {
						transferAttribute(oVariantProductSrc, oVariantProductDest, oAD.getQualifier());
					}
				}
			} else {
				System.out.println("SKIP-Attribute=" + oAD.getQualifier());
			}

			// --- Elemente die komplett umkopiert werden m�ssen
			bCopy = false;
			if (oAD.getQualifier().equals("outputcontrols") || oAD.getQualifier().equals("category2productexts") || oAD.getQualifier().equals("features")) {
				bCopy = true;
			}
			if ((oVariantProductSrc instanceof WeraProductSet || oVariantProductSrc instanceof WeraProductSetinSet)
					&& (oAD.getQualifier().equals("variants") || oAD.getQualifier().equals("weraproductsetvariants_qual"))) {
				bCopy = true;
			}
			if (bCopy) {

				System.out.println("START-Attribute=" + oAD.getQualifier());

				Collection oSrcObject = (Collection) getAttribute(oVariantProductSrc, oAD.getQualifier());
				ArrayList oNewList = new ArrayList();
				Object oDestObject = null;
				LocalizableItem oItem = null;
				for (Iterator it2 = oSrcObject.iterator(); it2.hasNext();) {
					// --- Hole Kategorie
					oItem = (LocalizableItem) it2.next();

					// --- Vorbelegung der Map
					HashMap hashmap = null;
					boolean bHasDone = false;
					if (oAD.getQualifier().equals("variants")) { // --- WeraVarianten (Satz)
						// --- Bearbeitet
						bHasDone = true;

						// --- Varianten
						System.out.println(oAD.getQualifier() + "." + oItem.getPK());
						if (m_hashArticle.containsKey(oItem.getPK())) {
							System.out.println("+found (gen)=" + oItem.getPK());

							hashmap = new HashMap();
							hashmap.put("baseProduct", oVariantProductDest);
							hashmap.put("code", getAttribute(oItem, "code"));
							hashmap.put("weravariants", getAttribute(oItem, "weravariants"));
							hashmap.put("contentQuantity", getAttribute(oItem, "contentQuantity"));

							try {

								oDestObject = TypeManager.getInstance().getComposedType(WeraVarianteSet.class).newInstance(hashmap);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							System.out.println("+skip=" + oItem.getPK());
						}

					} // --- if ( oAD.getQualifier().equals("variants") ) {

					if (oAD.getQualifier().equals("weraproductsetvariants_qual")) { // --- WeraVarianten (SatzInSatz)
						// --- Bearbeitet
						bHasDone = true;

						// --- Varianten
						System.out.println(oAD.getQualifier() + "." + oItem.getPK());
						if (m_hashArticle.containsKey(oItem.getPK())) {
							System.out.println("+found (gen) =" + oItem.getPK());
							hashmap = new HashMap();
							hashmap.put("weraproductsets", getAttribute(oItem, "weraproductsets"));
							hashmap.put("vpe", getAttribute(oItem, "vpe"));
							hashmap.put("order", getAttribute(oItem, "order"));

							try {

								oDestObject = TypeManager.getInstance().getComposedType(WeraProductSetVariants.class).newInstance(hashmap);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							System.out.println("+skip=" + oItem.getPK());
						}

					} // --- if ( oAD.getQualifier().equals("weraproductsetvariants_qual") ) {

					if (oAD.getQualifier().equals("features")) {
						// --- Bearbeitet
						bHasDone = true;

						// --- Kopieren und anlegen eines Productfeatures
						oDestObject = (LocalizableItem) _copyProductFeature(oVariantProductDest, (ProductFeature) oItem);
					}

					if (!bHasDone) {

						// --- Klonen
						oDestObject = _cloneItem(oItem, oVariantProductDest, hashmap);
					}

					if (oDestObject != null) {
						oNewList.add(oDestObject);
					}
				}

				// --- Attribute speichern, Sonderbehandlung bei Produktfeatures
				if (oAD.getQualifier().equals("features") == false) {
					setAttribute(oVariantProductDest, oAD.getQualifier(), oNewList);
				}
				System.out.println("ENDE-Attribute=" + oAD.getQualifier());
			}

		} // --- for (Iterator it1 = descriptors.iterator(); it1.hasNext();) {

		System.out.println("_cloneItems.ende");
	}

}

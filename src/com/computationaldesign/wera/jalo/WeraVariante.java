package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.ItemAttributeMap;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import org.apache.log4j.Logger;
import de.hybris.platform.jalo.JaloSession;

import de.hybris.platform.jalo.order.price.ProductPriceInformations;
import de.hybris.platform.jalo.order.price.JaloPriceFactoryException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.order.price.PriceInformation;
import de.hybris.platform.util.PriceValue;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.jalo.product.Unit;
import de.hybris.platform.catalog.jalo.ProductFeature;
import de.hybris.platform.jalo.ConsistencyCheckException;
import de.hybris.platform.jalo.type.AttributeDescriptor;

import java.lang.String;
import java.lang.Boolean;
import java.util.*;


@SuppressWarnings("PMD")
public class WeraVariante extends GeneratedWeraVariante
{
	private static Logger log = Logger.getLogger( WeraVariante.class.getName() );
	
	public WeraVariante()
	{
		// empty
	}
	
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		Item item = super.createItem( ctx, type, allAttributes );
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	/**
	 * remove the item.
	 * you can delete this method if you don't want to intercept the removal of this item
	 */
	@Override
	public void remove( SessionContext ctx ) throws ConsistencyCheckException
   {
      // ## business code placed here will be executed before the item is removed

	  try {

		  Collection colProductFeatures = (Collection) getAttribute("features");

		  System.out.println ("WeraVariante::remove() => delete Produktfeatures...");
		  // Produktfeatures löschen
		  ProductFeature productFeature = null;
		  for (Iterator it1 = colProductFeatures.iterator(); it1.hasNext();) {
			// --- Nächstes ProductFeature
			productFeature = (ProductFeature) it1.next();
			productFeature.remove();
		  }
		  System.out.println ("WeraVariante::remove() => delete Produktfeatures done.");

		  // then create the item
		  super.remove( ctx );



	  } catch (Exception e) {
	  	// TODO Auto-generated catch block
		e.printStackTrace();
	  }


	   // ## business code placed here will be executed after the item was removed
   }
	
	public String getPriceInfo(SessionContext ctx) {

		   // we want all prices gross
		   boolean netprice = false;
		   String prices = "";

		   ProductPriceInformations productPriceInfos = null;
		try {
			productPriceInfos = getAllPriceInformations(netprice);
		} catch (JaloPriceFactoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if( productPriceInfos == null ) return "-";

		   Iterator it = productPriceInfos.getPrices().iterator();
		   while(it.hasNext()) {
		      PriceInformation priceInfo = (PriceInformation)it.next();
		      PriceValue priceValue = priceInfo.getPrice();
		      prices += "[" + priceValue.getValue() + "]";
		   }

		return prices;
	}

	public void setPriceInfo(SessionContext ctx, String param) {
		// TODO Auto-generated method stub

	}
	
	public String getF1(SessionContext ctx) {
		// TODO Auto-generated method stub
		return null;
	}

	public void setF1(SessionContext ctx, String param) {
		// TODO Auto-generated method stub

	}
	
	
	public String getName() {
   		WeraProduct baseProduct = null;
   		try {
			baseProduct = (WeraProduct) getAttribute("baseproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ( baseProduct != null ) {
			return baseProduct.getName();
		} else {
			return "";
		}

	}

  	public String getName(SessionContext ctx) {
   		WeraProduct baseProduct = null;
   		try {
			baseProduct = (WeraProduct) getAttribute("baseproduct");
		} catch (Exception e) {
			e.printStackTrace();
		}
		if ( baseProduct != null ) {
			return baseProduct.getName(ctx);
		} else {
			return "";
		}

	}
 	
	public static Set getAllInstances () {


		ComposedType WeraVarianteType = TypeManager.getInstance()
					.getComposedType(WeraVariante.class);

		return WeraVarianteType.getAllInstances();

	}
	// --- Soll der Atrikel in der Preisliste ausgegeben werden????
	public boolean bIsAkitvPriceList ()
	{
		Boolean bAkitvPriceListe = null;
		try {
			bAkitvPriceListe = (Boolean) getLocalizedProperty("ausgabe_preisliste");
		} catch (JaloInvalidParameterException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
/*		
		if ( bAkitvPriceListe == null || bAkitvPriceListe.booleanValue() == false )
			return false;
		else
			return true;
*/			
	}
	// --- Initialisierung der Grunddaten für WERA
	public void setEssentialData()
	{
		// --- UNIT
		final List aResult = JaloSession.getCurrentSession().getFlexibleSearch().search(
				"select {pk},{code},{name[de]} from {Unit} WHERE code='pieces'",
				null,
				Collections.singletonList(Unit.class),
				true,  // fail on unknown fields
				true,  // don't need total
				0, 1  // range
		).getResult();
		Unit fnResult = null;
		if ( aResult != null && aResult.isEmpty() == false )
		{
			fnResult = (Unit) aResult.get(0);	
		}
		if ( fnResult != null ) {

		    setUnit(fnResult);

			WeraManager.getInstance().setAttribute (this, "contentUnit",fnResult);

		}
		// --- UNIT

		// --- Produkt Defaultwerte initialisieren
		setLocalizedProperty( "aktiv", new Boolean(true) );

		WeraManager.getInstance().setAttribute (this, "manufacturerName",new String("WERA") );
	}
	
	// --- Hole alle angelegten Datenfelder (Portalclassifizierung)
	public static HashMap getAllDatenfelderAsHash() {
		
		// --- Initiliaze
		HashMap setMerkmale = new HashMap();
		
		// --- Schleife über alle Felder
		Collection descriptors = TypeManager.getInstance().getComposedType(WeraVariante.class).getAttributeDescriptors();				
		for (Iterator it1 = descriptors.iterator(); it1.hasNext();) {
			// --- Hole Attribut-describtor
			AttributeDescriptor oAD = (AttributeDescriptor) it1.next();
                        //log.info("0.oAD.getQualifier()=" + oAD.getQualifier());
			if ( oAD.isWritable() ) {
                        //log.info("1.oAD.getQualifier()=" + oAD.getQualifier());
			
				// --- Filter nach Typen
				if ( !(oAD.getAttributeType() instanceof de.hybris.platform.jalo.type.AtomicType ) )
					continue;
                        //log.info("2.oAD.getQualifier()=" + oAD.getQualifier());
					 
					HashMap hMerkmal = new HashMap();
					hMerkmal.put("name",        oAD.getQualifier() );
					hMerkmal.put("visibility",  "Artikel" );
					if ( oAD.getName() == null )
						hMerkmal.put("description", oAD.getQualifier() );
					else
						hMerkmal.put("description", oAD.getName());
					hMerkmal.put("type",        oAD.getAttributeType().getName() );
					hMerkmal.put("namespace",   "Artikel@hybris/core" );
					setMerkmale.put(oAD.getQualifier(),hMerkmal);

			}
		}
		
	 return setMerkmale;
	}

}

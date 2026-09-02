package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;

import java.util.Set;

import org.apache.log4j.Logger;

@SuppressWarnings("PMD")
public class WeraVarianteSet extends GeneratedWeraVarianteSet
{
	private static Logger log = Logger.getLogger(WeraVarianteSet.class.getName());

	public WeraVarianteSet()
	{
		// empty
	}
/*
	
	public WeraVariante getWeravariants(final SessionContext ctx) {
		Object col = super.getWeravariants(ctx);
		System.out.println("col="+col);
		
		return (WeraVariante)null;
	}
*/
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes)
			throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		System.out.println("+++ WeraVarianteSet::createItem");

		final Item item = super.createItem(ctx, type, allAttributes);
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	/**
	 * 
	 * @param ctx
	 * @return 
	 */
	@Override
	public String getProductdescription(final SessionContext ctx)
	{
		// TODO Auto-generated method stub
		String strResult = "[na]";

		try {
			
			// --- Hole die Produktnamen unserer Variante
			strResult = getCode() + "[not found]";

			final WeraVariante weraVariant = getWeravariants();
			if (weraVariant != null)
			{
				final WeraProduct weraBaseProduct = (WeraProduct) weraVariant.getBaseProduct();
				if (weraBaseProduct != null)
				{
					strResult = weraBaseProduct.getCode() + " - " + weraBaseProduct.getName();
				}
			}
			
		} catch ( Exception e ) {
			
			// --- show the exception
			strResult = "[Fehler]" + e.getMessage();
		}
		
		return strResult;
	}

	public static Set getAllInstances()
	{
		final ComposedType WeraVarianteSetType = TypeManager.getInstance().getComposedType(WeraVarianteSet.class);

		return WeraVarianteSetType.getAllInstances();
	}
}

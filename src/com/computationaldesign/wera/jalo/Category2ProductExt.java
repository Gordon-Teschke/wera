package com.computationaldesign.wera.jalo;

import de.hybris.platform.catalog.jalo.CatalogVersion;
import de.hybris.platform.category.jalo.Category;
import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.type.ComposedType;

import org.apache.log4j.Logger;


@SuppressWarnings("PMD")
public class Category2ProductExt extends GeneratedCategory2ProductExt
{
	private static Logger log = Logger.getLogger(Category2ProductExt.class.getName());

	public Category2ProductExt()
	{
		// empty
	}


	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes)
			throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		final Item item = super.createItem(ctx, type, allAttributes);
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	@Override
	public String getCatalogversion_desc(final SessionContext ctx)
	{
		// TODO Auto-generated method stub
		String strResult = "n/a";
		CatalogVersion catalogversion = null;

		try
		{
			final Category oCategory = (Category) getAttribute("category");
			if (oCategory != null)
			{
				catalogversion = (CatalogVersion) oCategory.getAttribute("catalogVersion");
			}

			if (catalogversion != null)
			{
				strResult = catalogversion.getVersion();
			}

		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (final JaloSecurityException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return strResult;
	}

}

package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;

import java.util.Set;
import org.apache.log4j.Logger;

public class ExtImage extends GeneratedExtImage {

	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger(ExtImage.class.getName());

	/**
	 *
	 * @param ctx
	 * @param type
	 * @param allAttributes
	 * @return
	 * @throws JaloBusinessException
	 */
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException {
		// business code placed here will be executed before the item is created
		// then create the item
		final Item item = super.createItem(ctx, type, allAttributes);
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}

	/**
	 *
	 * @return
	 */
	public static Set getAllInstances() {

		final ComposedType ExtImageType = TypeManager.getInstance().getComposedType(ExtImage.class);

		return ExtImageType.getAllInstances();

	}

	/**
	 *
	 * @return String
	 */
	public String getCode() {
		// --- Initialize
		String strCode = "";

		try {
			// --- Hole das Attrbiute
			strCode = (String) this.getAttribute("code");

		} catch (final Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return strCode;
	}

	/**
	 * url der preview
	 *
	 * @param ctx
	 * @return
	 */
	@Override
	public String getPreview(final SessionContext ctx) {
		return this.getPreview();
	}

	/**
	 * url der preview
	 *
	 * @return
	 */
	@Override
	public String getPreview() {
		
		// --- initialize
		String strPreview		= "";
/*		String strPathExtImages	= Config.getParameter("wera.dam.url"); */
		
		// --- hole den bildnamens
		String strBildname		= (String) WeraManager.getInstance().getAttribute(this, "location");

		// --- ist ein Bildname vorhanden
		if ( strBildname != null && !strBildname.equals("") ) {
			
			// --- gen. preview link
			strPreview	= /* strPathExtImages + */ strBildname;
		}
		
		return strPreview;
	}

}

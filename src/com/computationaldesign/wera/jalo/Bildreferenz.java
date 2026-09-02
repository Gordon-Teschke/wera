package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;

import org.apache.log4j.Logger;

public class Bildreferenz extends GeneratedBildreferenz {

	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger(Bildreferenz.class.getName());

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
	 * test 
	 * @return
	 */
	@Override
	public String getPreview() {
		
		// --- initialize
		String strPreview		= "";
		
		// --- hole den bildnamens
		String strBildname		= (String) WeraManager.getInstance().getAttribute(this, "location");

		// --- ist ein Bildname vorhanden
		if ( strBildname != null && !strBildname.equals("") ) {
			
			// --- gen. preview link
			// strPreview	= strPathExtImages + strBildname;
			strPreview	= strBildname;
		}
		
		return strPreview;
	}

}

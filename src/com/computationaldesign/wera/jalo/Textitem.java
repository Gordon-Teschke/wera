package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import org.apache.log4j.Logger;

public class Textitem extends GeneratedTextitem
{
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger( Textitem.class.getName() );
	
	@Override
	protected Item createItem(final SessionContext ctx, final ComposedType type, final ItemAttributeMap allAttributes) throws JaloBusinessException
	{
		// business code placed here will be executed before the item is created
		// then create the item
		final Item item = super.createItem( ctx, type, allAttributes );
		// business code placed here will be executed after the item was created
		// and return the item
		return item;
	}
	
public String getName(SessionContext ctx) {
		// TODO Auto-generated method stub
		
		// --- Initialize
		String strResult = null;
		
		// --- Prüfwe vorhandenen Text
		String strTextblock = getTextblock();
		if ( strTextblock != null && strTextblock.length() > 0 )
			strResult = strTextblock;
		else
			strResult = "<noch keine Eingabe vorhanden>";
		
	
		return strResult;
	}


	public void setName(SessionContext ctx, String param) {
		// TODO Auto-generated method stub
		
	}	
	
}

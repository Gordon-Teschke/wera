package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.Item.ItemAttributeMap;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import org.apache.log4j.Logger;
import de.hybris.platform.jalo.security.JaloSecurityException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.catalog.jalo.classification.ClassAttributeAssignment;

@SuppressWarnings("PMD")
public class Outputcontrol extends GeneratedOutputcontrol
{
	private static Logger log = Logger.getLogger( Outputcontrol.class.getName() );
	
	public Outputcontrol()
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
	

public String getBezeichnung(SessionContext ctx) {
	// TODO Auto-generated method stub

	// --- Initialize
	String strCode = "";
	String strResult = "n/a";

	try {
		strCode = (String) getAttribute("code");

		ClassAttributeAssignment ca = (ClassAttributeAssignment)getAttribute("classattributeassignment");
		if ( ca != null )
		   strResult = ca.getClassificationAttribute().getName();
	} catch (JaloInvalidParameterException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (JaloSecurityException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
 return strResult;
}
public void setBezeichnung(SessionContext ctx, String param) {
	// TODO Auto-generated method stub
	
}

}

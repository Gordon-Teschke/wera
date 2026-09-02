package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloSession;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;


@SuppressWarnings("PMD")
public class Footnote extends GeneratedFootnote
{
	/** Qualifier of the <code>Footnote.name</code> attribute **/
	public static final String CODE = "code".intern();
	
	private static Logger log = Logger.getLogger(Footnote.class.getName());

	public Footnote()
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


	private int m_iLfdNr = 0;

	public void setLfdNr(final int iLfdNr)
	{
		m_iLfdNr = iLfdNr;
	}

	public int getLfdNr()
	{
		return m_iLfdNr;
	}


	public static Footnote getFootnodeByCode(final String strCode)
	{
		Footnote fnResult = null;
		final Map value = new HashMap();
		value.put("code", strCode);
		//		
		final List aResult = JaloSession.getCurrentSession().getFlexibleSearch().search(
				"SELECT {" + Item.PK + "} FROM {Footnote} " + "WHERE {code} " + "LIKE ?code", value,
				Collections.singletonList(Footnote.class), true, // fail on unknown fields
				true, // don't need total
				0, 1 // range
				).getResult();

		if (aResult != null && aResult.isEmpty() == false)
		{
			fnResult = (Footnote) aResult.get(0);
		}

		return fnResult;
	}

}

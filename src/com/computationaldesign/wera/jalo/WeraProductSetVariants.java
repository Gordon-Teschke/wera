package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import java.text.SimpleDateFormat;
import java.util.Date;
import org.apache.log4j.Logger;

public class WeraProductSetVariants extends GeneratedWeraProductSetVariants
{
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger( WeraProductSetVariants.class.getName() );
	
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
	
        
        @Override
        public String getView_valid_from(final SessionContext ctx) {
            WeraProductSet wps = this.getWeraproductsets(ctx);
            Date d = ( wps == null || wps.getValid_from() == null) ? null : wps.getValid_from();
            return ( d != null ) ? new SimpleDateFormat("yyyy-MM-dd").format( d ) : "";
        }
        
        @Override
        public String getView_valid_to(final SessionContext ctx) {
            WeraProductSet wps = this.getWeraproductsets(ctx);
            Date d = ( wps == null || wps.getValid_to() == null) ? null : wps.getValid_to();
            return ( d != null ) ? new SimpleDateFormat("yyyy-MM-dd").format( d ) : "";         
        }

        
}

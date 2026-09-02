package com.computationaldesign.wera.jalo;

import de.hybris.platform.jalo.Item;
import de.hybris.platform.jalo.JaloBusinessException;
import de.hybris.platform.jalo.JaloInvalidParameterException;
import de.hybris.platform.jalo.SessionContext;
import de.hybris.platform.jalo.type.ComposedType;
import de.hybris.platform.jalo.type.TypeManager;
import de.hybris.platform.jalo.c2l.Language;
import java.util.Map;
import java.util.Set;
import org.apache.log4j.Logger;

public class Weblink extends GeneratedWeblink
{
	@SuppressWarnings("unused")
	private final static Logger LOG = Logger.getLogger( Weblink.class.getName() );
	
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
	public static Set getAllInstances()
	{

		final ComposedType WeblinkType = TypeManager.getInstance().getComposedType(Weblink.class);

		return WeblinkType.getAllInstances();

	}
     
        /**
         * 
         * @return String
         */
        public String getCode() {
		// --- Initialize
		String strCode = "";

		try
		{
			// --- Hole das Attrbiute
			strCode = (String)this.getAttribute("code");

		}
		catch (final Exception e1)
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return strCode;
        }
		
	/**
	 * preview Weblink
	 *
	 * @param ctx
	 * @return
	 */
	@Override
	public String getPreview_link_wera_de(final SessionContext ctx) {
		if( ctx == null || ctx.getLanguage() == null )
		{
			throw new JaloInvalidParameterException("Weblink.getLink_wera_de requires a session language", 0 );
		}
		
		String strPreview		= "";
		// --- hole den bildnamens
		// String strLinkWeraDe		= (String)getLocalizedProperty( ctx, LINK_WERA_DE);
		String strYoutubeId		= (String)getLocalizedProperty( ctx, YOUTUBE_ID );
		
		if ( strYoutubeId != null ) {
			if ( ! strYoutubeId.trim().isEmpty() ) {
				String strDamMediaName	= (String)getLocalizedProperty( ctx, DAM_MEDIA_NAME);
				if ( strDamMediaName != null ) {
					strPreview = "https://www-de.wera.de/fileadmin/scripts/youtube-embed.php?movie=" + strDamMediaName + "&v="+strYoutubeId;
				}
			}
		}		
		
		return strPreview;
	}
	
	/**
	 * preview Weblink
	 *
	 * @param ctx
	 * @return
	 */
	@Override
	public Map<Language,String> getAllPreview_link_wera_de(final SessionContext ctx) {
		return super.getAllLink_wera_de(ctx);
	}	
}

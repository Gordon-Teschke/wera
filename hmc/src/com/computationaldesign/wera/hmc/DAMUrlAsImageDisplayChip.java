package com.computationaldesign.wera.hmc;

import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.hmc.attribute.UrlAsImageDisplayChip;
import de.hybris.platform.util.Config;
import de.hybris.platform.jalo.Item;

import java.util.*;


public class DAMUrlAsImageDisplayChip extends UrlAsImageDisplayChip
{
    private static final String JSP_URI = "/ext/wera/DAMUrlAsImageDisplayChip.jsp";

	public static final String PING = "ping";

	/**
	 * bildhöhe default
	 */
	private int previewHeight = 30;
	
	/**
	 * bildgrößen ( xs,sm md, lg )
	 */
	private String previewType = "xs";
	private static final HashSet<String> allowedpreviewTypes = new HashSet<String>(Arrays.asList(
														new String[] {"xs","sm","md","lg"} )
														);

	/**
	 * Constructor
	 * @param displayState
	 * @param parent
	 * @param jspURI
	 * @param item
	 * @param qualifier 
	 */
	public DAMUrlAsImageDisplayChip(DisplayState displayState, Chip parent, String jspURI, Item item, String qualifier) 
	{
		super(displayState, parent, jspURI, item, qualifier);
	}
	
	/**
	 * 
	 * @return 
	 */
	protected  String	getDefaultJSPURI() {
		return JSP_URI;
	}
	
	/**
	 * set parameter
	 * @param parameters 
	 */
	public void setParameters(Map parameters) {
		
		// --- parameter auswerten
		Map mapParameters	= parameters;

		// --- bildhöhe
		if ( mapParameters.containsKey("height")) {
			
			// --- set parameters 
			String tmpValue = (String)mapParameters.get("height");
			if ( tmpValue != null && !tmpValue.equals("") ) {
				this.previewHeight	= Integer.parseInt(tmpValue);
			}
		}		
		// --- bildgröße ( xs,sm md, lg )
		if ( mapParameters.containsKey("previewtype") ) {
			
			// --- set parameters 
			String tmpValue = (String)mapParameters.get("previewtype").toString().toLowerCase();
			if ( tmpValue != null && !tmpValue.equals("") && allowedpreviewTypes.contains(tmpValue)) {
				this.previewType	= tmpValue;
			}
		}		
	}
	
	/**
	 * generate the preview value
	 * @return 
	 */
	public String getDisplayValue() {
		
		// --- initalize
		String strImageUrl	= "";
		
		// --- preset external - url
		String strPathExtImages	= Config.getParameter("wera.dam.url");

		// --- hole den value
		Object oValue		= this.getValue();
		String strBildname		= oValue.toString();
		
		return strPathExtImages + this.previewType + "/" + strBildname;
	}

	/**
	 * generate the preview value sized by a given type
	 * @param strPreviewType 
	 * @return 
	 */
	public String getValueByPreviewType ( String strPreviewType ) {
		
		// --- preset external - url
		String strPathExtImages	= Config.getParameter("wera.dam.url");

		// --- hole den value
		String strBildname		= (String)super.getValue();

		// --- check preview-type
		if ( allowedpreviewTypes.contains(strPreviewType.toLowerCase()) ) {

			return strPathExtImages + strPreviewType + "/" + strBildname;
		} else {
			
			return strPathExtImages + this.previewType + "/" + strBildname;
		}
	}

	/**
	 * 
	 * @param newHeight 
	 */
	public void setHeight(int newHeight) {
		
		this.previewHeight = newHeight;
	}

	/**
	 * 
	 * @return 
	 */
	public int getHeight(){
		return this.previewHeight;
	}

	/**
	 * 
	 * @return 
	 */
	public String getPreviewType() {
		return previewType;
	}

	/**
	 * 
	 * @param previewType 
	 */
	public void setPreviewType(String previewType) {
		this.previewType = previewType;
	}

	/**
	 * Process the {@link #SET_VALUE} event by which the selected values are
	 * added to this editor's collection value.
	 * @param events
	 */
	public void processEvents(Map events)
	{
	}

	/**
	 * 
	 * @return 
	 */
	public String getJSPURI()
	{
		return JSP_URI;
	}

}

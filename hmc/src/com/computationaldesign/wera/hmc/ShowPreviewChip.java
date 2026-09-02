package com.computationaldesign.wera.hmc;

import de.hybris.platform.hmc.webchips.Chip;
import de.hybris.platform.hmc.webchips.DisplayState;
import de.hybris.platform.hmc.attribute.AbstractAttributeEditorChip;
import de.hybris.platform.util.Config;

import java.util.*;


public class ShowPreviewChip extends AbstractAttributeEditorChip
{
    private static final String JSP_URI = "/ext/wera/showPreviewChip.jsp";

	public static final String PING = "ping";

	/**
	 * bildhöhe default
	 */
	private int previewHeight = 150;
	
	/**
	 * bildgrößen ( xs,sm md, lg )
	 */
	private String previewType = "md";
	private static final HashSet<String> allowedpreviewTypes = new HashSet<String>(Arrays.asList(
														new String[] {"xs","sm","md","lg"} )
														);


	
	/**
	 * @param displayState The current displaystate this chip belongs to.
	 * @param parent The composition parent chip.
	 */
	public ShowPreviewChip(DisplayState displayState, Chip parent )
	{
		super(displayState, parent);
	}

	
	/**
	 * set parameter
	 * @param map 
	 */
	public void setParameters(Map<String,String> map) {
		
		// --- default
		super.setParameters(map);
		
		// --- parameter auswerten
		Map<String,String> mapParameters	= map;

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
			String tmpValue = (String)mapParameters.get("previewtype").toLowerCase();
			if ( tmpValue != null && !tmpValue.equals("") && allowedpreviewTypes.contains(tmpValue)) {
				this.previewType	= tmpValue;
			}
		}		
	}
	
	/**
	 * generate the preview value
	 * @return 
	 */
	public String getValue() {
		
		// --- initalize
		String strImageUrl	= "";
		
		// --- preset external - url
		String strPathExtImages	= Config.getParameter("wera.dam.url");

		// --- hole den value
		String strBildname		= (String)super.getValue();

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
	 * generate the preview value sized by a given type
	 * @param strPreviewType 
	 * @return 
	 */
	public String getBaseValue () {
		// --- hole den value
		return (String)super.getValue();
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

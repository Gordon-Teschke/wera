<%--
	[y] hybris E-Business Platform
	
	Copyright (c) 2000-2005 hybris
	All rights reserved.
	
	This software is the confidential and proprietary information of hybris
	("Confidential Information").  You shall not dis such Confidential
	Information and shall use it only in accordance with the terms of the
	license agreement you entered into with hybris.

 	version $Revision: 1.1.2.1 $
				GenericHelper.toStringForReference(theDisplayState, item) %>:

--%>
<%@include file="../../head.inc"%>
<%@page import="de.hybris.platform.jalo.enumeration.*" %>
<%@page import="com.computationaldesign.wera.hmc.ReferenceSingleSelectEditorChip" %>
<%
	ReferenceSingleSelectEditorChip theChip = (ReferenceSingleSelectEditorChip) request.getAttribute(AbstractChip.CHIP_KEY);
%>
<%-- 
multiple="multiple" > 
id="<%= theChip.getUniqueName() %>_select"
onkeypress="return true;"
style="width:<%= theChip.getWidth() %>px;background-image:none;"
size="<%= theChip.getSize() %>"
style="width:<%= theChip.getWidth() %>px;background-image:none;"
 --%>
<%-- the following PING is needed to recognize empty selections (which create no event) --%>
<input type="hidden" name="<%= theChip.getEventID(ReferenceSingleSelectEditorChip.PING) %>" value="<%= AbstractChip.TRUE %>"/>
<div class="referenceMultiSelectEditorChip" style="padding:1px;" >
	<select
					class="<%= (theChip.isEditable() ? "enabled" : "disabled") %>"
					name="<%= theChip.getEventID(ReferenceSingleSelectEditorChip.SET_VALUE) %>"
					style="padding:3px;border:0px;width:100px;background-image:none;"
					<%= (theChip.isEditable() ? "" : "disabled") %>
					>
					<option value="-1">-</option> 
<%	
		for( Iterator iter = theChip.getPossibleEntries().iterator(); iter.hasNext(); )
		{
		    JaloSession js = JaloSession.getCurrentSession();
		
  		    // --- Defaultsprache
		    js.setAttribute(LocalizableItem.LANGUAGE_FALLBACK_ENABLED, Boolean.TRUE);
			
			final Item item = (Item) iter.next();
            String strCode = "";
            String strName = (String)item.getAttribute ( "name" );
			if ( item.getAttribute("code").toString().length() > 0 && !item.getAttribute("code").toString().substring(0,2).equals("H_") ) 
			    strCode = ", " + item.getAttribute("code").toString();
%>
			<option value="<%= theChip.indexOf(item) %>" <%= (theChip.isItemSelected(item) ? "selected" : "") %>>
				<%= strName != null ? strName : localized("notdefined") %><%= strCode %>
			</option>
<%
		}
%>
	</select>
</div>

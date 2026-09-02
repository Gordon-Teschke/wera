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
<%@page import="com.computationaldesign.wera.hmc.ReferenceMultiSelectEditorChip" %>
<%
	ReferenceMultiSelectEditorChip theChip = (ReferenceMultiSelectEditorChip) request.getAttribute(AbstractChip.CHIP_KEY);
%>
	<%-- the following PING is needed to recognize empty selections (which create no event) --%>
	<input type="hidden" name="<%= theChip.getEventID(ReferenceMultiSelectEditorChip.PING) %>" value="<%= AbstractChip.TRUE %>"/>
	<select
					class="enabled"
					size="<%= theChip.getSize() %>"
					name="<%= theChip.getEventID(ReferenceMultiSelectEditorChip.SET_VALUE) %>"
					onkeypress="return true;"
					id="<%= theChip.getUniqueName() %>_select"
					style="width:<%= theChip.getWidth() %>px;background-image:none;"
					<%= (theChip.isEditable() ? "" : "disabled") %> multiple="multiple">
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

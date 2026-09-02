<%@include file="../../head.inc"%>
<%@page import="com.computationaldesign.wera.hmc.ShowPreviewChip" %>
<%
	ShowPreviewChip theChip = (ShowPreviewChip) request.getAttribute(AbstractChip.CHIP_KEY);
	String stringImageUrl = (String) theChip.getValue();
	
	// --- preview nur anzeigen wenn vorhanden
	if ( stringImageUrl != null && !stringImageUrl.equals("") ) {
%>
<div class="showPreviewChip" >
	<img src="<%= theChip.getValue() %>" />
</div>
<%
}
%>
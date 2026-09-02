<%@include file="../../head.inc"%>
<%@page import="com.computationaldesign.wera.hmc.DAMUrlAsImageDisplayChip" %>
<%
	UrlAsImageDisplayChip theChip = (UrlAsImageDisplayChip) request.getAttribute(AbstractChip.CHIP_KEY);
	String stringImageUrl = (String) theChip.getDisplayValue();
	int iHeight = (int) theChip.getHeight();
	
	// --- preview nur anzeigen wenn vorhanden
	if ( stringImageUrl != null && !stringImageUrl.equals("") ) {
%>
<!-- start: /attribute/attributeDisplayChip.jsp chip: com.computationaldesign.wera.hmc.DAMUrlAsImageDisplayChip::902458491 -->
<div id="Content/UrlAsImageDisplay[<img src=&quot;<%= stringImageUrl %>&quot; height=&quot;<%= iHeight %>px&quot; />]_span">
<img src="<%= stringImageUrl %>">
</div>
<!-- end: /attribute/attributeDisplayChip.jsp chip: com.computationaldesign.wera.hmc.DAMUrlAsImageDisplayChip::902458491 -->

<%
}
%>
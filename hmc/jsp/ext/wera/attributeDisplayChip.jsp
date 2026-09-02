<%--
	[y] hybris E-Business Platform
	
	Copyright (c) 2000-2004 hybris
	All rights reserved.
	
	This software is the confidential and proprietary information of hybris
	("Confidential Information").  You shall not dis such Confidential
	Information and shall use it only in accordance with the terms of the
	license agreement you entered into with hybris.

 	version $Revision: 1.1.6.1 $
--%>
<%@include file="../../head.inc"%>
<%
	final AbstractAttributeDisplayChip theChip = (AbstractAttributeDisplayChip) request.getAttribute( AbstractChip.CHIP_KEY );

	String displayValue = "";
	String style = "";
	
	if( (displayValue = theChip.getDisplayValue()) == null || displayValue.equals("") )
	{
		displayValue = localized("notdefined");
	}
	else if( theChip.allowHTMLEscaping() )
	{
		displayValue = escapeHTML(displayValue);
	}
	
	if( theChip.isAlignRight() )
	{
		style = "style=\"text-align:right; padding-right:3px;\"";
	}
%>
<%= "" %><div <%= style %>><%= displayValue %>wera..</div>

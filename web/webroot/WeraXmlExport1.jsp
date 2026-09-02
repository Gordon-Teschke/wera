<%@page import="de.hybris.platform.jalo.*"%>
<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.computationaldesign.wera.jalo.WeraManager"%>
<%@page import="com.computationaldesign.wera.jalo.MediandoXml"%>
<%@page import="com.computationaldesign.wera.jalo.WeraProduktExport"%>
<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.jalo.tools.WebSessionFunctions"%>
<%@page import="com.computationaldesign.wera.jalo.*"%>
<%@page import="de.hybris.platform.ext.category.jalo.Category"%>
<%@page	import="de.hybris.platform.ext.catalog.jalo.classification.ClassificationSystemVersion"%>
<%@page	import="de.hybris.platform.ext.catalog.jalo.classification.ClassificationSystem"%>
<%@page import="de.hybris.platform.jalo.type.AttributeDescriptor"%>
<%@page import="de.hybris.platform.jalo.type.ComposedType"%>
<%@page import="de.hybris.platform.jalo.user.User"%>
<%@page import="de.hybris.platform.jalo.user.UserManager"%>
<%@page import="de.hybris.platform.jalo.user.Customer"%>
<%@page import="de.hybris.platform.jalo.user.Employee"%>
<%@page import="de.hybris.platform.jalo.extension.ExtensionManager"%>
<%@page import="de.hybris.platform.ext.category.jalo.CategoryManager"%>
<%@page import="de.hybris.platform.ext.catalog.jalo.CatalogManager"%>
<%@page import="de.hybris.platform.jalo.security.JaloSecurityException"%>
<%@page import="de.hybris.platform.ext.catalog.jalo.classification.ClassificationAttribute"%>
<%@page import="de.hybris.platform.jalo.c2l.C2LManager"%>
<%@page import="de.hybris.platform.jalo.c2l.Language"%>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
		JaloSession jaloSession = WebSessionFunctions.getSession(request);
		WeraManager wm = WeraManager.getInstance();
		
        // --- Initialize
		boolean bExportReady = false;
        String strTemp = ""; 
        String strExportResult = ""; 

   if ( !bExportReady ) {
        
        // --- Initialize
        boolean bTextwechsel = false;
        Collection colCategories = new ArrayList();

        if ( request.getParameter("textwechsel") != null ) {
     		bTextwechsel = true;
        }

        String[] arrayStr  =  request.getParameterValues("categories");
   		out.println("<br><br><b>Ausgew&auml;hlte Kapitel:</b>" );
   		for ( int iPos=0; iPos < arrayStr.length; iPos++ ) {
    		out.println("<br>" + arrayStr[iPos] );
    		colCategories.add(arrayStr[iPos]);
    	}

        // --- Produktexport
		WeraProduktExport oWeraProduktExport = null;
		oWeraProduktExport = new WeraProduktExport();

		String strXmlResultFile = oWeraProduktExport.ProduktExport(colCategories, request.getParameter("language"), 
		                          request.getParameter("catalog"), request.getParameter("catalogversion"), 
		                          request.getParameter("destfile"), bTextwechsel);
       // --- Aufräumen
       oWeraProduktExport = null;


       MediandoXml mediandoxml = new MediandoXml();
       String strResult = mediandoxml.zipRequestedFiles(strXmlResultFile,strXmlResultFile);
       String strDlResult = mediandoxml.strDownloadFile ( response, strResult, true);

       bExportReady = true;
} 
%>
<% 
   if ( bExportReady ) {
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base  href="<%=basePath%>">

<title>DATENEXPORT f&uuml;r Inbetween</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Language" CONTENT="de">
    <link rel="stylesheet" type="text/css" href="styles.css">
</head>

<body bgcolor="#0B50F3" text="#BDD9FF" width="100%">
<div style="text-align:right;font-size:24px; font-weight:bold; color:#ffffff;"><img src="image/e-business_platform.gif" /><br/>WERA - Edition</div>
<p>DATENEXPORT f&uuml;r Inbetween erfolgreich abgeschlossen.</p>
<br><b>Exportierte Dateien finden Sie unter:</b> <%= strExportResult %>
<br><b>Logdatei:</b> c:/home/hybris/export/export.log
<FORM name="step2" method="post" enctype="application/x-www-form-urlencoded" action="<%= basePath %>WeraXmlExport.jsp" name="export">

<!-- VALUES (START) -->
<INPUT type="hidden" name="do" value="init" />
<!-- VALUES (ENDE) -->

<INPUT type="submit" value="Zur&uuml;ck" name="bstep4" />
</FORM>
<% } %>
</body>
</html>

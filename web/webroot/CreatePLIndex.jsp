<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="com.computationaldesign.wera.jalo.WeraManager"%>
<%@page import="com.computationaldesign.wera.jalo.WeraImportPricelist"%>
<%@page import="com.computationaldesign.wera.jalo.MediandoXml"%>
<%@page import="com.computationaldesign.wera.jalo.DataExport"%>
<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.util.WebSessionFunctions"%>
<%@page import="com.computationaldesign.wera.jalo.*"%>
<%@page import="de.hybris.platform.category.jalo.Category"%>
<%@page	import="de.hybris.platform.catalog.jalo.CatalogVersion"%>
<%@page	import="de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion"%>
<%@page	import="de.hybris.platform.catalog.jalo.classification.ClassificationSystem"%>
<%@page import="de.hybris.platform.catalog.jalo.classification.ClassificationAttribute"%>
<%@page import="de.hybris.platform.jalo.type.AttributeDescriptor"%>
<%@page import="de.hybris.platform.jalo.type.ComposedType"%>
<%@page import="de.hybris.platform.jalo.user.User"%>
<%@page import="de.hybris.platform.jalo.user.UserManager"%>
<%@page import="de.hybris.platform.jalo.user.Customer"%>
<%@page import="de.hybris.platform.jalo.user.Employee"%>
<%@page import="de.hybris.platform.jalo.extension.ExtensionManager"%>
<%@page import="de.hybris.platform.category.jalo.CategoryManager"%>
<%@page import="de.hybris.platform.catalog.jalo.CatalogManager"%>
<%@page import="de.hybris.platform.catalog.jalo.Catalog"%>
<%@page import="de.hybris.platform.catalog.jalo.CatalogVersion"%>
<%@page import="de.hybris.platform.jalo.security.JaloSecurityException"%>
<%@page import="de.hybris.platform.jalo.c2l.C2LManager"%>
<%@page import="de.hybris.platform.jalo.c2l.Language"%>
<%@page import="de.hybris.platform.util.Config"%>
<%@page import="org.apache.commons.collections.iterators.ArrayListIterator" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Language" CONTENT="de">
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<H1>Indexerstellung f&uuml;r Wera Preisliste</H1>
<%
// --- Initialize
JaloSession jaloSession = WebSessionFunctions.getSession(request);
WeraManager wm = WeraManager.getInstance();

// --- LOGIN ------------------------------------------------------------------------------
UserManager userMg = jaloSession.getUserManager();
String loginname = "";
String password = "";
try {
	// --- Get adminuser
	Employee customer = userMg.getAdminEmployee();

	// --- Initialize Logindata
	loginname = customer.getLogin();
	password = customer.getPassword();
} catch (Exception e) {
	e.printStackTrace();
}
// set the new user to the jaloSession
Properties prop = new Properties();
prop.setProperty("user.principal", loginname);
prop.setProperty("user.credentials", password);
// the user is a customer
prop.setProperty("session.type", "employee");

try {
	// transfers the jaloSession to a different login
	jaloSession.transfer(prop);
} catch (JaloSecurityException e) {
	e.printStackTrace();
} catch (Exception e) {
	e.printStackTrace();
}
// --- LOGIN ------------------------------------------------------------------------------

// --- Hole akuellen katalog
Catalog weraCatalog = CatalogManager.getInstance().getCatalog("weracatalog");
Collection colCatalogVersions = weraCatalog.getCatalogVersions();

// --- Hier gehts los
if ( request.getParameter("do") != null && request.getParameter("do").equals("createindex") ) {

		
        // --- Initialize
		boolean bExportReady = false;
        String strTemp = ""; 
        String strExportResult = ""; 

%>
<P>
Indexerstellung f&uuml;r Wera Preisliste l&auml;uft ...
</P>

<%

		// --- Funktion 1: Katalog erstellen ------------------------------------------------------
   	    WeraImportPricelist oWeraImportPricelist = new WeraImportPricelist();
    	oWeraImportPricelist.CreateIndexPL(request, "weramaster", null );
		
 } // --- if ( request.getAttribute("do") != null && request.getAttribute("do").equals("createIndex") ) { ...

%>

<hr><br>
<!-- ************************************* STEP 4 ************************************* -->

<form action="http://localhost:10001/wera/CreateIndex.jsp?do=createindex" method="post" enctype="multipart/form-data" >
	<INPUT type="hidden" name="language"       value="de" />
	<INPUT type="hidden" name="catalog"        value="weracatalog" />
	<INPUT type="hidden" name="catalogversion" value="weramaster" />
		
<!-- Auswahl Referenzkatalog (START) -->
<p>
<STRONG>Auswahl Referenzkatalog (Seitenangaben):</STRONG><br><br>
<select style="width: 250px; height="200px;" name="refcatalog" >
<%
   for (Iterator it0 = colCatalogVersions.iterator(); it0.hasNext();) {
		CatalogVersion catVersion = (CatalogVersion) it0.next();
		String sCatVersion = (String) catVersion.getAttribute("version");
%>
			<option value='<%= sCatVersion %>'><%= sCatVersion %></option>
<%
    } // --- for (Iterator it0 = result.iterator(); it0.hasNext();)
%>
</select>
</p>
<!-- Auswahl Referenzkatalog (ENDE) --> 

	<br><STRONG>CSV-Datei mit Seitennummern ausw&auml;hlen:</STRONG><br>
	<input style="width:300px;" type="file" name="fileupload" /><br>

	<p style="padding:0px;border:0x;margin:0px;margin-top:30px; border: 0px solid red;">
	<input style="width:110px;" type="submit" value="Index erstellen"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	</p>
</form>

</body>
</html>

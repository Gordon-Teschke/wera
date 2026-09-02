<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="com.computationaldesign.wera.jalo.WeraManager"%>
<%@page import="com.computationaldesign.wera.jalo.WeraImportKeywords"%>
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
<%@page import="de.hybris.platform.jalo.Item"%>
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
<H1>Produktimport -/export  (CSV) f&uuml;r Wera Keywords</H1>
<%
// --- Initialize
JaloSession jaloSession = WebSessionFunctions.getSession(request);
WeraManager wm = WeraManager.getInstance();
//CatalogVersion oCatalogVersion = wm.getCatalogVersion("preisliste", "preisliste_2009_brutto");

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


// --- new instance
WeraImportKeywords weraImportKeywords = new WeraImportKeywords();



// ----------------------------------- IMPORT ---------------------------------------------
// --- import
String strResult = "";
weraImportKeywords.importKeywordCSV ( "C:/home/hybris/import/","import_keyword_fi.txt" );
if ( strResult.length() > 0 )
out.println(strResult);

%>


</body>
</html>

<%@page import="de.hybris.platform.jalo.*"%>
<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="com.computationaldesign.wera.jalo.WeraManager"%>
<%@page import="com.computationaldesign.wera.jalo.DataImportOpal" %>
<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.jalo.tools.WebSessionFunctions"%>
<%@page import="com.computationaldesign.wera.jalo.*"%>
<%@page import="de.hybris.platform.category.jalo.Category"%>
<%@page	import="de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion"%>
<%@page	import="de.hybris.platform.catalog.jalo.classification.ClassificationSystem"%>
<%@page import="de.hybris.platform.jalo.type.AttributeDescriptor"%>
<%@page import="de.hybris.platform.jalo.type.ComposedType"%>
<%@page import="de.hybris.platform.jalo.user.User"%>
<%@page import="de.hybris.platform.jalo.user.UserManager"%>
<%@page import="de.hybris.platform.jalo.user.Customer"%>
<%@page import="de.hybris.platform.jalo.user.Employee"%>
<%@page import="de.hybris.platform.jalo.extension.ExtensionManager"%>
<%@page import="de.hybris.platform.ext.category.jalo.CategoryManager"%>
<%@page import="de.hybris.platform.catalog.jalo.CatalogManager"%>
<%@page import="de.hybris.platform.jalo.security.JaloSecurityException"%>
<%@page import="de.hybris.platform.catalog.jalo.classification.ClassificationAttribute"%>
<%@page import="de.hybris.platform.util.Config"%>

<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";

		JaloSession jaloSession = WebSessionFunctions.getSession(request);
		%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">

    <title>My JSP 'MyJsp.jsp' starting page</title>

    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Language" CONTENT="ru">    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>

  <body>
<%
        // --- LOGIN ------------------------------------------------------------------------------
		jaloSession = WebSessionFunctions.getSession(request);
		UserManager userMg = jaloSession.getUserManager();
		String loginname = "";
		String password = "";
		try {
			// --- Get adminuser
			Employee customer = userMg.getAdminEmployee();

			// --- Initialize Logindata
			loginname = customer.getLogin();
			password = customer.getPassword();
		} catch (JaloItemNotFoundException e) {
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
			jaloSession.getConnection().transferSession(jaloSession, prop);
		} catch (JaloSecurityException e) {
			e.printStackTrace();
		} catch (JaloInvalidParameterException e) {
			e.printStackTrace();
		}
        // --- LOGIN ------------------------------------------------------------------------------

        // --- Initialize
        String strResult = "";
		
		// ---- Import Opal
        DataImportOpal oDataImportOpal = new DataImportOpal();
        //oDataImportOpal.testJOM("//HYBRIS/T_NEW_CATALOG/CATALOG_GROUP_SYSTEM/");
        oDataImportOpal.ScheduleOpalImport();

	    
%>
<%= strResult %>
</body>
</html>

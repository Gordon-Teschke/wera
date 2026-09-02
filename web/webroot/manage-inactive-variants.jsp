<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="com.computationaldesign.wera.jalo.WeraProductCopy"%>
<%@page import="com.computationaldesign.wera.jalo.WeraPricelist"%>
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
<%@page import="de.hybris.platform.core.PK" %>
<%@page import="com.computationaldesign.wera.model.WeraVarianteModel"%>
<%@page import="com.computationaldesign.wera.model.WeraVarianteSetModel"%>
<%@page import="com.computationaldesign.wera.model.WeraProductModel"%>


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
<H1>Manage inactive variants</H1>
<%
// --- Initialize
	final JaloSession jalosession = JaloSession.getCurrentSession();
	jalosession.getSessionContext().setUser( jalosession.getUserManager().getAdminEmployee() );
	Language de = C2LManager.getInstance().getLanguageByIsoCode("de");
	jalosession.getSessionContext().setLanguage(de);
	
	String sMode = ( request.getParameter("mode") != null ) ? request.getParameter("mode") : "0";
	int iMode = Integer.parseInt(sMode);
	
	if ( iMode<0 || iMode>2 ) {
		iMode = 0;
	}
	
	DataCorrector dc = new DataCorrector();

	Collection colLists = dc.mapProductActiveToDate(iMode);
	Iterator iter = colLists.iterator();
	Collection colVariants = (Collection) iter.next();
	Collection colSetVariants = (Collection) iter.next();

	out.println("<a href=\"./manage-inactive-variants.jsp?mode=1\">Ablaufdatum setzen</a><br>");
	out.println("<a href=\"./manage-inactive-variants.jsp?mode=2\">Ablaufdatum entfernen</a>");

	out.println ("WERA VARIANTS:");
	out.println ("==============");
	for (final Iterator itVar = colVariants.iterator(); itVar.hasNext();) {
		final WeraVarianteModel wvm = (WeraVarianteModel) itVar.next();
		final WeraProductModel wpm = (WeraProductModel) wvm.getBaseProduct();
		final String sProductCode = wpm != null ? wpm.getCode() : "";
		out.println ( wvm.getPk() + "\t" + wvm.getCode() + "\t" + sProductCode + "\t"  + wvm.getValid_from() + "\t" + wvm.getValid_to() );
	}
	out.println ("WERA SET VARIANTS:");
	out.println ("==================");
	for (final Iterator itVar = colSetVariants.iterator(); itVar.hasNext();) {
		final WeraVarianteSetModel wvm = (WeraVarianteSetModel) itVar.next();
		final WeraProductModel wpm = (WeraProductModel) wvm.getBaseProduct();
		final String sProductCode = wpm != null ? wpm.getCode() : "";
		out.println ( wvm.getPk() + "\t" + wvm.getCode() + "\t" + sProductCode );
	}
	
	out.println(" DONE.");
%>
	


</body>
</html>

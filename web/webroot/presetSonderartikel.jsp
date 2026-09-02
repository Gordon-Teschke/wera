<%@page import="com.computationaldesign.wera.jalo.*"%>
<%@page import="de.hybris.jakarta.jalo.product.*"%>
<%@page import="de.hybris.platform.catalog.jalo.CatalogManager"%>
<%@page import="de.hybris.platform.catalog.jalo.classification.*"%>
<%@page import="de.hybris.platform.category.jalo.Category"%>
<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.catalog.jalo.CatalogManager"%>
<%@page import="de.hybris.platform.jalo.JaloBusinessException"%>
<%@page import="de.hybris.platform.jalo.JaloInvalidParameterException"%>
<%@page import="de.hybris.platform.jalo.security.JaloSecurityException"%>
<%@page import="de.hybris.platform.jalo.type.TypeManager"%>
<%@page import="de.hybris.platform.jalo.type.ComposedType"%>
<%@page import="de.hybris.platform.jalo.enumeration.EnumerationManager"%>
<%@page import="de.hybris.platform.jalo.enumeration.EnumerationValue"%>
<%@page import="de.hybris.platform.jalo.c2l.*"%>
<%@page import="de.hybris.platform.europe1.constants.GeneratedEurope1Constants.Enumerations.UserPriceGroup"%>

<%@page language="java" import="java.util.*"%>
<%@page import="java.io.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>Preset Sonderartikel</title>
	<script type="text/javascript" src="javascript/jquery-1.4.2.min.js"></script>
</head>
<body>
<%! String[] loc_lang; %>
<%
	final JaloSession jalosession = JaloSession.getCurrentSession();
	jalosession.getSessionContext().setUser( jalosession.getUserManager().getAdminEmployee() );
	Language de = C2LManager.getInstance().getLanguageByIsoCode("de");
	jalosession.getSessionContext().setLanguage(de);
		
	DataCorrector dc = new DataCorrector();
	dc.presetSonderartikelToRegion();
	out.println("Script execution finished.");
%>
</body>
</html>
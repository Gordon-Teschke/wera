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
<%@page import="com.computationaldesign.datawarehouse.jalo.DatawarehouseManager"%>

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
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=utf-8">
<META HTTP-EQUIV="Language" CONTENT="de">
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<H1>Import Feature Names</H1>
<%
// --- Initialize
	final JaloSession jalosession = JaloSession.getCurrentSession();
	jalosession.getSessionContext().setUser( jalosession.getUserManager().getAdminEmployee() );
	Language de = C2LManager.getInstance().getLanguageByIsoCode("de");
	jalosession.getSessionContext().setLanguage(de);
	
	DataCorrector dc = new DataCorrector();

dc.appendShopKeywords( "05117990001" );
dc.appendShopKeywords( "05117991001" );
dc.appendShopKeywords( "05117992001" );
dc.appendShopKeywords( "05118002001" );
dc.appendShopKeywords( "05118003001" );
dc.appendShopKeywords( "05118000001" );
dc.appendShopKeywords( "05117993001" );
dc.appendShopKeywords( "05118004001" );
dc.appendShopKeywords( "05117997001" );
dc.appendShopKeywords( "05118017001" );
dc.appendShopKeywords( "05118005001" );
dc.appendShopKeywords( "05118006001" );
dc.appendShopKeywords( "05118007001" );
dc.appendShopKeywords( "05117994001" );
dc.appendShopKeywords( "05118008001" );
dc.appendShopKeywords( "05117995001" );
dc.appendShopKeywords( "05118010001" );
dc.appendShopKeywords( "05118012001" );
dc.appendShopKeywords( "05118014001" );
dc.appendShopKeywords( "05345290001" );
dc.appendShopKeywords( "05118019001" );
dc.appendShopKeywords( "05118020001" );
dc.appendShopKeywords( "05118026001" );
dc.appendShopKeywords( "05118022001" );
dc.appendShopKeywords( "05118023001" );
dc.appendShopKeywords( "05118024001" );
dc.appendShopKeywords( "05118030001" );
dc.appendShopKeywords( "05118032001" );
dc.appendShopKeywords( "05118180001" );
dc.appendShopKeywords( "05118181001" );
dc.appendShopKeywords( "05118182001" );
dc.appendShopKeywords( "05118183001" );
dc.appendShopKeywords( "05118184001" );
dc.appendShopKeywords( "05118185001" );
dc.appendShopKeywords( "05118186001" );
dc.appendShopKeywords( "05118035001" );
dc.appendShopKeywords( "05118036001" );
dc.appendShopKeywords( "05118037001" );
dc.appendShopKeywords( "05118039001" );
dc.appendShopKeywords( "05118040001" );
dc.appendShopKeywords( "05118042001" );
dc.appendShopKeywords( "05118044001" );
dc.appendShopKeywords( "05118046001" );
dc.appendShopKeywords( "05118048001" );
dc.appendShopKeywords( "05118050001" );
dc.appendShopKeywords( "05118052001" );
dc.appendShopKeywords( "05118054001" );
dc.appendShopKeywords( "05030160001" );
dc.appendShopKeywords( "05118060001" );
dc.appendShopKeywords( "05118062001" );
dc.appendShopKeywords( "05118064001" );
dc.appendShopKeywords( "05118066001" );
dc.appendShopKeywords( "05118068001" );
dc.appendShopKeywords( "05118070001" );
dc.appendShopKeywords( "05118072001" );
dc.appendShopKeywords( "05345284001" );
dc.appendShopKeywords( "05345285001" );
dc.appendShopKeywords( "05118074001" );
dc.appendShopKeywords( "05118076001" );
dc.appendShopKeywords( "05118078001" );
dc.appendShopKeywords( "05118080001" );
dc.appendShopKeywords( "05118082001" );
dc.appendShopKeywords( "05118075001" );
dc.appendShopKeywords( "05118089001" );
dc.appendShopKeywords( "05118090001" );
dc.appendShopKeywords( "05118092001" );
dc.appendShopKeywords( "05118094001" );
dc.appendShopKeywords( "05118096001" );
dc.appendShopKeywords( "05118098001" );
dc.appendShopKeywords( "05118100001" );
dc.appendShopKeywords( "05118102001" );
dc.appendShopKeywords( "05118106001" );
dc.appendShopKeywords( "05118103001" );
dc.appendShopKeywords( "05118145001" );
dc.appendShopKeywords( "05118108001" );
dc.appendShopKeywords( "05118109001" );
dc.appendShopKeywords( "05118110001" );
dc.appendShopKeywords( "05118112001" );
dc.appendShopKeywords( "05118114001" );
dc.appendShopKeywords( "05118116001" );
dc.appendShopKeywords( "05118118001" );
dc.appendShopKeywords( "05118120001" );
dc.appendShopKeywords( "05118122001" );
dc.appendShopKeywords( "05118124001" );
dc.appendShopKeywords( "05118126001" );
dc.appendShopKeywords( "05345281001" );
dc.appendShopKeywords( "05118128001" );
dc.appendShopKeywords( "05118130001" );
dc.appendShopKeywords( "05118132001" );
dc.appendShopKeywords( "05118134001" );
dc.appendShopKeywords( "05118136001" );
dc.appendShopKeywords( "05118135001" );
dc.appendShopKeywords( "05118137001" );
dc.appendShopKeywords( "05345282001" );
dc.appendShopKeywords( "05030101001" );
dc.appendShopKeywords( "05030100001" );
dc.appendShopKeywords( "05030102001" );
dc.appendShopKeywords( "05030108001" );
dc.appendShopKeywords( "05030103001" );
dc.appendShopKeywords( "05030104001" );
dc.appendShopKeywords( "05030105001" );
dc.appendShopKeywords( "05030106001" );
dc.appendShopKeywords( "05030107001" );
dc.appendShopKeywords( "05030117001" );
dc.appendShopKeywords( "05030118001" );
dc.appendShopKeywords( "05030110001" );
dc.appendShopKeywords( "05030119001" );
dc.appendShopKeywords( "05030111001" );
dc.appendShopKeywords( "05030112001" );
dc.appendShopKeywords( "05030115001" );
dc.appendShopKeywords( "05030116001" );
dc.appendShopKeywords( "05030400001" );
dc.appendShopKeywords( "05030401001" );
dc.appendShopKeywords( "05030402001" );
dc.appendShopKeywords( "05030403001" );
dc.appendShopKeywords( "05030404001" );
dc.appendShopKeywords( "05030405001" );
dc.appendShopKeywords( "05030406001" );
dc.appendShopKeywords( "05030120001" );
dc.appendShopKeywords( "05030121001" );
dc.appendShopKeywords( "05030122001" );
dc.appendShopKeywords( "05030135001" );
dc.appendShopKeywords( "05030080001" );
dc.appendShopKeywords( "05030081001" );
dc.appendShopKeywords( "05030082001" );
dc.appendShopKeywords( "05030083001" );
dc.appendShopKeywords( "05030084001" );
dc.appendShopKeywords( "05030410001" );
dc.appendShopKeywords( "05030411001" );
dc.appendShopKeywords( "05030412001" );
dc.appendShopKeywords( "05030413001" );
dc.appendShopKeywords( "05030414001" );
dc.appendShopKeywords( "05030415001" );
dc.appendShopKeywords( "05030416001" );
dc.appendShopKeywords( "05030417001" );
dc.appendShopKeywords( "05030418001" );
dc.appendShopKeywords( "05030150001" );
dc.appendShopKeywords( "05030151001" );
dc.appendShopKeywords( "05073675001" );
dc.appendShopKeywords( "05073677001" );
dc.appendShopKeywords( "05118150001" );
dc.appendShopKeywords( "05118152001" );
dc.appendShopKeywords( "05118154001" );
dc.appendShopKeywords( "05118156001" );
dc.appendShopKeywords( "05118158001" );
dc.appendShopKeywords( "05030181001" );
dc.appendShopKeywords( "05030170001" );
dc.appendShopKeywords( "05030180001" );
%>
</body>
</html>

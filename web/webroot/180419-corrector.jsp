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
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Language" CONTENT="de">
<link rel="stylesheet" type="text/css" href="styles.css">
</head>
<body>
<H1>Bullet Point Replacer 04/2018</H1>
<%
// --- Initialize
	final JaloSession jalosession = JaloSession.getCurrentSession();
	jalosession.getSessionContext().setUser( jalosession.getUserManager().getAdminEmployee() );
	Language de = C2LManager.getInstance().getLanguageByIsoCode("de");
	jalosession.getSessionContext().setLanguage(de);
	
	DatawarehouseManager dwh = new DatawarehouseManager();
	
	Collection<String> colProductIds = new ArrayList();
colProductIds.add("05006153001");
colProductIds.add("05006154001");
colProductIds.add("05006156001");
colProductIds.add("05006158001");
colProductIds.add("05006159001");
colProductIds.add("05006450001");
colProductIds.add("05006451001");
colProductIds.add("05020131001");
colProductIds.add("05020133001");
colProductIds.add("05004650001");
colProductIds.add("05004655001");
colProductIds.add("05004660001");
colProductIds.add("05004665001");
colProductIds.add("05004670001");
colProductIds.add("05004675001");
colProductIds.add("05006160001");
colProductIds.add("05006162001");
colProductIds.add("05006163001");
colProductIds.add("05006164001");
colProductIds.add("05006165001");
colProductIds.add("05006166001");
colProductIds.add("05006168001");
colProductIds.add("05006460001");
colProductIds.add("05006461001");
colProductIds.add("05006169001");
colProductIds.add("05006170001");
colProductIds.add("05006172001");
colProductIds.add("05006174001");
colProductIds.add("05006176001");
colProductIds.add("05006177001");
colProductIds.add("05006178001");
colProductIds.add("05006179001");
colProductIds.add("05006181001");
colProductIds.add("05006182001");
colProductIds.add("05006183001");
colProductIds.add("05006184001");
colProductIds.add("05006186001");
colProductIds.add("05022729001");
colProductIds.add("05022730001");
colProductIds.add("05022731001");
colProductIds.add("05022732001");
colProductIds.add("05022733001");
colProductIds.add("05022734001");
colProductIds.add("05022740001");
colProductIds.add("05022741001");
colProductIds.add("05032005001");
colProductIds.add("05032006001");
colProductIds.add("05032007001");
colProductIds.add("05032001001");
colProductIds.add("05032002001");
colProductIds.add("05032003001");
colProductIds.add("05032004001");
colProductIds.add("05007610001");
colProductIds.add("05007620001");
colProductIds.add("05007621001");
colProductIds.add("05007635001");
colProductIds.add("05007640001");
colProductIds.add("05110010001");
colProductIds.add("05110011001");
colProductIds.add("05110104001");
colProductIds.add("05110105001");
colProductIds.add("05340330001");
colProductIds.add("05007670001");
colProductIds.add("05007671001");
colProductIds.add("05007672001");
colProductIds.add("05007673001");
colProductIds.add("05007674001");
colProductIds.add("05007675001");
colProductIds.add("05007676001");
colProductIds.add("05007677001");
colProductIds.add("05008006001");
colProductIds.add("05008007001");
colProductIds.add("05008008001");
colProductIds.add("05008009001");
colProductIds.add("05008015001");
colProductIds.add("05008027001");
colProductIds.add("05008055001");
colProductIds.add("05008060001");
colProductIds.add("05008061001");
colProductIds.add("05110000001");
colProductIds.add("05110001001");
colProductIds.add("05110002001");
colProductIds.add("05110003001");
colProductIds.add("05110004001");
colProductIds.add("05110005001");
colProductIds.add("05110006001");
colProductIds.add("05110007001");
colProductIds.add("05110008001");
colProductIds.add("05032020001");
colProductIds.add("05032021001");
colProductIds.add("05032022001");
colProductIds.add("05032023001");
colProductIds.add("05032030001");
colProductIds.add("05032031001");
colProductIds.add("05032032001");
colProductIds.add("05032033001");
colProductIds.add("05032050001");
colProductIds.add("05032051001");
colProductIds.add("05032052001");
colProductIds.add("05032053001");
colProductIds.add("05032054001");
colProductIds.add("05032055001");
colProductIds.add("05032056001");
colProductIds.add("05032057001");
colProductIds.add("05032058001");
colProductIds.add("05008705001");
colProductIds.add("05008706001");
colProductIds.add("05008710001");
colProductIds.add("05008712001");
colProductIds.add("05008715001");
colProductIds.add("05008720001");
colProductIds.add("05008723001");
colProductIds.add("05008725001");
colProductIds.add("05008730001");
colProductIds.add("05008735001");
colProductIds.add("05008740001");
colProductIds.add("05008750001");
colProductIds.add("05008751001");
colProductIds.add("05008752001");
colProductIds.add("05008753001");
colProductIds.add("05022795001");
colProductIds.add("05022800001");
colProductIds.add("05022805001");
colProductIds.add("05022810001");
colProductIds.add("05022815001");
colProductIds.add("05022820001");
colProductIds.add("05022825001");
colProductIds.add("05022830001");
colProductIds.add("05022835001");
colProductIds.add("05022905001");
colProductIds.add("05022910001");
colProductIds.add("05022915001");
colProductIds.add("05022920001");
colProductIds.add("05022925001");
colProductIds.add("05022930001");
colProductIds.add("05022935001");
colProductIds.add("05138070001");
colProductIds.add("05023105001");
colProductIds.add("05023107001");
colProductIds.add("05023110001");
colProductIds.add("05023115001");
colProductIds.add("05023120001");
colProductIds.add("05023125001");
colProductIds.add("05023130001");
colProductIds.add("05009305001");
colProductIds.add("05009310001");
colProductIds.add("05009312001");
colProductIds.add("05009313001");
colProductIds.add("05009315001");
colProductIds.add("05009317001");
colProductIds.add("05009319001");
colProductIds.add("05009320001");
colProductIds.add("05009325001");
colProductIds.add("05347743001");
colProductIds.add("05009340001");
colProductIds.add("05009341001");
colProductIds.add("05009342001");
colProductIds.add("05009343001");
colProductIds.add("05028000001");
colProductIds.add("05028001001");
colProductIds.add("05028002001");
colProductIds.add("05028003001");
colProductIds.add("05028004001");
colProductIds.add("05028005001");
colProductIds.add("05028008001");
colProductIds.add("05028010001");
colProductIds.add("05028012001");
colProductIds.add("05028013001");
colProductIds.add("05028015001");
colProductIds.add("05028020001");
colProductIds.add("05028025001");
colProductIds.add("05110050001");
colProductIds.add("05110051001");
colProductIds.add("05110052001");
colProductIds.add("05110053001");
colProductIds.add("05110054001");
colProductIds.add("05110055001");
colProductIds.add("05110150001");
colProductIds.add("05110151001");
colProductIds.add("05105631001");
colProductIds.add("05105630001");
colProductIds.add("05135927001");
colProductIds.add("05051010001");
colProductIds.add("05051011001");
colProductIds.add("05347106001");
colProductIds.add("05004310001");
colProductIds.add("05004313001");
colProductIds.add("05006145001");
colProductIds.add("05006147001");
colProductIds.add("05133355001");
colProductIds.add("05006148001");
colProductIds.add("05347777001");
colProductIds.add("05006480001");
colProductIds.add("05135961001");
colProductIds.add("05100000001");
colProductIds.add("05100001001");
colProductIds.add("05100002001");
colProductIds.add("05100003001");
colProductIds.add("05100004001");
colProductIds.add("05100011001");
colProductIds.add("05100012001");
colProductIds.add("05100015001");
colProductIds.add("05100016001");
colProductIds.add("05100030001");
colProductIds.add("05100031001");
colProductIds.add("05105650001");
colProductIds.add("05105656001");
colProductIds.add("05347778001");
colProductIds.add("05133356001");
colProductIds.add("05007680001");
colProductIds.add("05007681001");
colProductIds.add("05100042001");
colProductIds.add("05100043001");
colProductIds.add("05100044001");
colProductIds.add("05105622001");
colProductIds.add("05320540001");
colProductIds.add("05100050001");
colProductIds.add("05100051001");
colProductIds.add("05100052001");
colProductIds.add("05100055001");
colProductIds.add("05100056001");
colProductIds.add("05100057001");
colProductIds.add("05100060001");
colProductIds.add("05100061001");
colProductIds.add("05100062001");
colProductIds.add("05100063001");
colProductIds.add("05100064001");
colProductIds.add("05028062001");
colProductIds.add("05022728001");
colProductIds.add("05022745001");
colProductIds.add("05032060001");
colProductIds.add("05032063001");
colProductIds.add("05032061001");

	dwh.switchBulletPoint(	"text_tipp_take_it_easy_schraubendreher"	,"text_bit_check_tool_check_kk_zyklop_werkzeugfinder_take_it_easy", colProductIds);
	// dwh.switchBulletPoint(	"text_satz_schraubendreher_griffkennzeichnung","text_bit_check_tool_check_kk_zyklop_werkzeugfinder_take_it_easy", colProductIds);
	
	out.println("Script execution finished.");

	out.println(" DONE.");
%>
	


</body>
</html>

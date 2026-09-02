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
dc.setDefiningFeature( "" );
dc.setDefiningFeature( "_00116f001" );
dc.setDefiningFeature( "_00008f001" ); 
dc.setDefiningFeature( "_00701f001"  );
dc.setDefiningFeature( "_00107f001"  );
dc.setDefiningFeature( "_00106f001"  );
dc.setDefiningFeature( "_00026f001"  );
dc.setDefiningFeature( "_00702f001"  );
dc.setDefiningFeature( "_00118f001"  );
dc.setDefiningFeature( "_00119f001"  );
dc.setDefiningFeature( "_00200f001"  );
dc.setDefiningFeature( "_00503f001"  );
dc.setDefiningFeature( "_00801f001"  );
dc.setDefiningFeature( "_00046f001"  );
dc.setDefiningFeature( "_00115f001"  );
dc.setDefiningFeature( "_00303f001"  );
dc.setDefiningFeature( "_00221f001"  );
dc.setDefiningFeature( "_00220f001"  );
dc.setDefiningFeature( "_00219f001"  );
dc.setDefiningFeature( "_00121f001"  );
dc.setDefiningFeature( "_00029f001"  );
dc.setDefiningFeature( "_00215f001"  );
dc.setDefiningFeature( "_00009f001"  );
dc.setDefiningFeature( "_00117f001"  );
dc.setDefiningFeature( "_00108f001"  );
dc.setDefiningFeature( "_00110f001"  );
dc.setDefiningFeature( "_00114f001"  );
dc.setDefiningFeature( "_00025f001"  );
dc.setDefiningFeature( "_00710f001"  );
dc.setDefiningFeature( "_00111f001"  );
dc.setDefiningFeature( "_00102f001"  );
dc.setDefiningFeature( "_00105f001"  );
dc.setDefiningFeature( "_00104f001"  );
dc.setDefiningFeature( "_00705f001"  );
dc.setDefiningFeature( "_00109f001"  );
dc.setDefiningFeature( "_00130f002"  );
dc.setDefiningFeature( "_00223f001"  );
dc.setDefiningFeature( "_00706f001"  );
dc.setDefiningFeature( "_00027f001"  );
dc.setDefiningFeature( "_00703f001"  );
dc.setDefiningFeature( "_00071f001"  );
dc.setDefiningFeature( "_00712f001"  );
dc.setDefiningFeature( "_00305f001"  );
dc.setDefiningFeature( "_00132f002"  );
dc.setDefiningFeature( "_00217f001"  );
dc.setDefiningFeature( "_00133f002"  );
dc.setDefiningFeature( "_00131f002"  );
dc.setDefiningFeature( "_00507f001"  );
dc.setDefiningFeature( "_00603f001"  );
dc.setDefiningFeature( "_00709f001"  );

dc.setAlternativeNames( "nl", "_00221f001", "Vasthoudfunctie", "hf_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00220f001", "Vasthoudfunctie", "hf_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00219f001", "Vasthoudfunctie", "hf_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00121f001", "Vasthoudfunctie", "hf_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00029f001", "Vasthoudfunctie", "hf_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00221f001", "Funzione di ritegno", "hf_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00220f001", "Funzione di ritegno", "hf_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00219f001", "Funzione di ritegno", "hf_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00121f001", "Funzione di ritegno", "hf_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00029f001", "Funzione di ritegno", "hf_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00221f001", "Haltefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00220f001", "Haltefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00219f001", "Haltefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00121f001", "Haltefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00029f001", "Haltefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00221f001", "Přidržovací funkce", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00220f001", "Přidržovací funkce", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00219f001", "Přidržovací funkce", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00121f001", "Přidržovací funkce", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00029f001", "Přidržovací funkce", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00221f001", "Fonction de retenue", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00220f001", "Fonction de retenue", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00219f001", "Fonction de retenue", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00121f001", "Fonction de retenue", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00029f001", "Fonction de retenue", "hf_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00221f001", "保持機能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00220f001", "保持機能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00219f001", "保持機能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00121f001", "保持機能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00029f001", "保持機能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00221f001", "Holding function", "hf_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00220f001", "Holding function", "hf_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00219f001", "Holding function", "hf_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00121f001", "Holding function", "hf_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00029f001", "Holding function", "hf_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00221f001", "Holdefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00220f001", "Holdefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00219f001", "Holdefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00121f001", "Holdefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00029f001", "Holdefunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00221f001", "Фиксирующая функция", "hf_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00220f001", "Фиксирующая функция", "hf_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00219f001", "Фиксирующая функция", "hf_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00121f001", "Фиксирующая функция", "hf_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00029f001", "Фиксирующая функция", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00221f001", "固持功能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00220f001", "固持功能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00219f001", "固持功能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00121f001", "固持功能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00029f001", "固持功能", "hf_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00221f001", "Funkcja przytrzymywania", "hf_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00220f001", "Funkcja przytrzymywania", "hf_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00219f001", "Funkcja przytrzymywania", "hf_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00121f001", "Funkcja przytrzymywania", "hf_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00029f001", "Funkcja przytrzymywania", "hf_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00221f001", "Holdefunksjon", "hf_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00220f001", "Holdefunksjon", "hf_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00219f001", "Holdefunksjon", "hf_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00121f001", "Holdefunksjon", "hf_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00029f001", "Holdefunksjon", "hf_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00221f001", "Función de retención", "hf_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00220f001", "Función de retención", "hf_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00219f001", "Función de retención", "hf_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00121f001", "Función de retención", "hf_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00029f001", "Función de retención", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00221f001", "Pitotoiminto", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00220f001", "Pitotoiminto", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00219f001", "Pitotoiminto", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00121f001", "Pitotoiminto", "hf_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00029f001", "Pitotoiminto", "hf_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00221f001", "Hållarfunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00220f001", "Hållarfunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00219f001", "Hållarfunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00121f001", "Hållarfunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00029f001", "Hållarfunktion", "hf_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00027f001", "Roestvrij", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00027f001", "Inox", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00027f001", "Rostfrei", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00027f001", "Nerezové", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00027f001", "Inoxydable", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00027f001", "ステンレス", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00027f001", "Stainless", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00027f001", "Rustfri", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00027f001", "Нержавеющие", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00027f001", "不锈钢", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00027f001", "Nierdzewny", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00027f001", "Rustfritt", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00027f001", "Inoxidable", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00027f001", "Ruostumaton", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00027f001", "Rostfri", "rostfrei_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "nl", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "it", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "de", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cs", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fr", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "jp", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "en", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "dk", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "ru", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "cn", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "pl", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "no", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "es", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "fi", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00305f001 ", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00132f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00217f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00133f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00131f002", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );
dc.setAlternativeNames( "se", "_00507f001", "Take it easy", "take_it_easy_screwdriver_s_grund.jpg" );


%>
</body>
</html>

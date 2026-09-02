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

<%
// --- Initialize
final JaloSession jalosession = JaloSession.getCurrentSession();
jalosession.getSessionContext().setUser( jalosession.getUserManager().getAdminEmployee() );
Language de = C2LManager.getInstance().getLanguageByIsoCode("de");


Language langCS = C2LManager.getInstance().getLanguageByIsoCode("cs");
Language langNL = C2LManager.getInstance().getLanguageByIsoCode("nl");
Language langNO = C2LManager.getInstance().getLanguageByIsoCode("no");
Language langPL = C2LManager.getInstance().getLanguageByIsoCode("pl");
Language langSE = C2LManager.getInstance().getLanguageByIsoCode("se");
Language langRU = C2LManager.getInstance().getLanguageByIsoCode("ru");
Language langDK = C2LManager.getInstance().getLanguageByIsoCode("dk");
Language langIT = C2LManager.getInstance().getLanguageByIsoCode("it");
Language langCN = C2LManager.getInstance().getLanguageByIsoCode("cn");
Language langKO = C2LManager.getInstance().getLanguageByIsoCode("ko");
Language langJP = C2LManager.getInstance().getLanguageByIsoCode("jp");

Language langES = C2LManager.getInstance().getLanguageByIsoCode("es");
Language langFR = C2LManager.getInstance().getLanguageByIsoCode("fr");
Language langFI = C2LManager.getInstance().getLanguageByIsoCode("fi");


out.println("<H1>Import keywords</H1>");
	
DataCorrector dc = new DataCorrector();
	// dc.importKeywords("",""); Artikelnummer, Keywords

jalosession.getSessionContext().setLanguage(langES);
// dc.importKeywords("1834", "プラスドライバー, 埋め込み型グリップ, 高トルク, 高軸力, 転がり防止機能, レーザーチップ");
dc.importKeywords("1834", "destornillador de ranura Kraftform Ball-Grip, molduras en el mango, altos pares de giro, altas fuerzas axiales, sistema antirrodamiento, Lasertip, altos pares de giro, altas fuerzas axiales");
dc.importKeywords("1850 PH", "destornillador de ranura en cruz PH Kraftform Ball-Grip, molduras en el mango, altos pares de giro, altas fuerzas axiales, sistema antirrodamiento, Lasertip, altos pares de giro, altas fuerzas axiales");
dc.importKeywords("1855 PZ", "destornillador de ranura en cruz PZ Kraftform Ball-Grip, molduras en el mango, altos pares de giro, altas fuerzas axiales, sistema antirrodamiento, Lasertip, altos pares de giro, altas fuerzas axiales");
dc.importKeywords("9478", "caja de material textil, bolso para la herramienta, alojamiento de la herramienta, ligero, estable");
dc.importKeywords("8790 B Impaktor", "inserto para llave de vaso, vaso de inserción, máquina atornilladora de golpe, taladro de ranura de perfil hexagonal, tornillos de hexágono exterior, tuercas de hexágono exterior, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("9607", "listón magnético para alojamiento y sostenimiento, inserto de llave de vaso, vaso de inserción, máquina atornilladora de golpe, taladro de ranura de perfil hexagonal, tornillos de hexágono exterior, tuercas de hexágono exterior, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("9608", "listón magnético para alojamiento y sostenimiento, inserto de llave de vaso, cotas en pulgadas, pulgada, vaso de inserción, máquina atornilladora de golpe, taladro de ranura de perfil hexagonal, tornillos de hexágono exterior, tuercas de hexágono exterior, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("454/5 HF SHK Set 1", "juego de destornilladores de mango transversal, mango en T, mango transversal, función de sostenimiento de tornillos de hexágono interior, sistema de búsqueda de herramienta de Wera Take it easy, Wera Hex-Plus, técnica de instalaciones sanitarias, técnica de calefacciones, técnica de climatización, altos pares de apriete, altos pares de desapriete");
dc.importKeywords("Bit-Check 6 SHK 1", "juego de puntas, puntas largas, puntas duras y resistentes, uso universal, técnica de instalaciones sanitarias, técnica de calefacciones, técnica de climatización");
dc.importKeywords("9610", "listón magnético para alojamiento y sostenimiento, Joker, llave de boca");
dc.importKeywords("9630", "juego de llaves carraca de boca y anillo, listón magnético para alojamiento y sostenimiento, Joker, llave de boca, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("9631", "juego de llaves carraca de boca y anillo, listón magnético para alojamiento y sostenimiento, Joker, llave de boca, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("9632", "juego de llaves carraca de boca y anillo, listón magnético para alojamiento y sostenimiento, Joker, llave de boca, cotas en pulgadas, pulgadas, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("9640", "juego de llaves de boca y anillo, listón magnético para alojamiento y sostenimiento, Joker, llave de boca, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("9641", "juego de llaves de boca y anillo, listón magnético para alojamiento y sostenimiento, Joker, llave de boca, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("9642", "juego de llaves de boca y anillo, listón magnético para alojamiento y sostenimiento, Joker, llave de boca, cotas en pulgadas, pulgadas, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("8798 B", "adaptador de prolongación de llave de inserción de 3/8\", cuadradillo, función \"Lock\", bloqueo de insertos de llave de vaso, manguito de marcha libre");
dc.importKeywords("8010 B", "carraca aislada VDE, palanca para cambio del sentido de giro, forma delgada, dentado fino de 4,5°, bloqueo seguro de vasos, herramienta probada en un 100 %");
dc.importKeywords("8100 SB 12", "juego de carracas, palanca para cambio del sentido de giro, insertos de llave de vaso, vasos, prolongación, adaptador, caja de material textil, bolso de herramientas");
dc.importKeywords("8100 SB 13", "juego de carracas, palanca para cambio del sentido de giro, insertos de llave de vaso, vasos, prolongación, adaptador, caja de material textil, bolso de herramientas, cotas en pulgadas, pulgadas");
dc.importKeywords("9650", "juego de destornilladores, listón magnético para alojamiento y sostenimiento, Lasertip, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("9611", "listón magnético para alojamiento y sostenimiento, destornillador");
dc.importKeywords("837 i RA", "varillas intercambiables para mango manual aislado VDE, función de carraca con dentado fino, marcha conmutable derecha/izquierda, herramienta probada en un 100 %");
dc.importKeywords("Kraftform Kompakt VDE 17 RA Imperial 1", "juego de herramientas aisladas VDE, varillas intercambiables para mango manual aislado VDE, función de carraca, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy, cotas en pulgadas, pulgadas");
dc.importKeywords("Kraftform Kompakt VDE 17 RA 1", "juego de herramientas aisladas VDE, varillas intercambiables para mango manual aislado VDE, función de carraca, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 17 Universal 1 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para mango manual aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("838 RA S", "puntas para destornillador carraca, mecanismo de carraca con dentado fino, marcha conmutable derecha/izquierda, imán permanente");
dc.importKeywords("Kraftform Kompakt VDE Big Pack 1", "juego de herramientas aisladas VDE, varillas intercambiables para mango manual aislado VDE, herramienta probada en un 100 %, bolso plegable robusto, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("839 RA M", "puntas para destornillador carraca, mecanismo de carraca con dentado fino, marcha conmutable derecha/izquierda, toma de puntas Rapidaptor");
dc.importKeywords("Click-Torque X 7", "llave dinamométrica, toma de cuadradillo, llave dinamométrica ajustable, apriete derecha/izquierda, mecanismo de activación, herramienta de inserción");
dc.importKeywords("Kraftform Kompakt 400 RA SHK Set 1", "juego de herramientas, puntas para portapuntas manuales de mango transversal, mecanismo de carraca con dentado, apriete conmutable derecha/izquierda, toma de puntas Rapidaptor, puntas resistentes y duras, caja de material textil, bolso para herramientas");
dc.importKeywords("Kraftform Kompakt VDE 60 i TiE", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 iS TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 iS TiE", "varilla intercambiable aislada VDE, tornillos de ranura, diámetro de varilla reducido, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 62 iS TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura en cruz Phillips-Recess, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 62 i TiE", "varilla intercambiable aislada VDE, tornillos de ranura en cruz Phillips-Recess, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 62 iS TiE", "varilla intercambiable aislada VDE, tornillos de ranura en cruz Phillips-Recess, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 68 i TiE", "varilla intercambiable aislada VDE, tornillos de cuadradillo interior, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 68 iS TiE", "varilla intercambiable aislada VDE, tornillos de cuadradillo interior, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 64 i TiE", "varilla intercambiable aislada VDE, tornillos de hexágono interior, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 67 i TiE", "varilla intercambiable aislada VDE, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 67 iS TiE", "varilla intercambiable aislada VDE, tornillos TORX®, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 62 i PH/S TiE", "varilla intercambiable aislada VDE, tornillos PlusMinus ranura/Phillips, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH/S TiE", "varilla intercambiable aislada VDE, tornillos PlusMinus ranura/Phillips, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 i PZ/S TiE", "varilla intercambiable aislada VDE, tornillos PlusMinus ranura/Pozidriv, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ/S TiE", "varilla intercambiable aislada VDE, tornillos PlusMinus ranura/Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 i TiE", "varilla intercambiable aislada VDE, tornillos Pozidriv, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,4 x 2,5 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,5 x 3,0 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,6 x 3,5 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,8 x 4,0 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 i 1,0 x 5,5 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 i 1,2 x 6,5 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 i TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 60 iS 0,8 x 4,0 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de ranura, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH 1 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos PlusMinus ranura/Phillips, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH 2 x 157 TiE SB", "varilla intercambiable aislada VDE,  tornillos PlusMinus ranura/Phillips, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 64 i 4,0 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de hexágono interior, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 64 i 5,0 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de hexágono interior, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 64 i 6,0 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos de hexágono interior, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 15 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 20 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 25 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 30 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ/S # 1 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos PlusMinus ranura/Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 1 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 2 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS TiE", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos de ranura, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3060 i TiE", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 10 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 67 i TORX® TiE SB", "varilla intercambiable aislada VDE, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® TiE", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® TiE", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos TORX®, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S TiE", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos PlusMinus ranura/Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ TiE", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3060 i 0,4 x 2,5 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3060 i 1,2 x 6,5 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos de ranura, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 0,6 x 3,5 x 157 mm TiE SB", "varilla intercambiable aislada VDE acero inoxidable, inoxidable, tornillos de ranura, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 0,8 x 4,0 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos de ranura, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 1,0 x 5,5 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos de ranura, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS PH 1 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos PlusMinus ranura/Phillips, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS PH 2 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos PlusMinus ranura/Phillips, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos PlusMinus ranura/Phillips, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 8 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 9 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 10 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 30 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable,  tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable,  tornillos TORX®, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 15 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable,  tornillos TORX®, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 20 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable,  tornillos TORX®, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 25 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable,  tornillos TORX®, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable,  tornillos TORX®, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S # 1 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos PlusMinus ranura/Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S # 2 x 157 mm TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos PlusMinus ranura/Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos PlusMinus ranura/Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ 1 x 157 TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ 2 x 157 TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ TiE SB", "varilla intercambiable aislada VDE, acero inoxidable, inoxidable, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt 71 Security", "juego de herramientas, uniones atornilladas de seguridad, porta-puntas manual, toma de puntas Rapidaptor, porta-puntas universal, imán permanente, puntas resistentes y duras, caja de material textil, bolso para herramientas");
dc.importKeywords("Kraftform Kompakt 70 Universal", "juego de herramientas, porta-puntas manual, toma de puntas Rapidaptor, porta-puntas universal, imán permanente, puntas resistentes y duras, caja de material textil, bolso para herramientas");
dc.importKeywords("Kraftform Kompakt 27 XL Universal 1", "destornillador de puntas, varilla larga, toma de puntas delgada bloqueable con imán permanente, recámara de puntas integrada, puntas");
dc.importKeywords("950/9 Hex-Plus 8", "juego de llaves acodadas, cromado en bastidor, tornillos de hexágono interior, Hex-Plus, magnetizador, soporte de mesa, soporte de fijación en pared");
dc.importKeywords("950/9 Hex-Plus Multicolour 3", "juego de llaves acodadas, tornillos de hexágono interior, Hex-Plus, cabeza esférica hexagonal, magnetizador, soporte de mesa, soporte de fijación en pared, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt 27 XL Universal Imperial Set 1", "destornillador de puntas, varilla larga, toma de puntas delgada bloqueable con imán permanente, recámara de puntas integrada, puntas, cotas en pulgadas, pulgadas");
dc.importKeywords("Kraftform Kompakt 27 XL SHK 1", "destornillador de puntas, varilla larga, toma de puntas delgada bloqueable con imán permanente, recámara de puntas integrada, puntas, técnica sanitaria, técnica de calefacciones, técnica de climatización");
dc.importKeywords("867/4 Z TORX® Take it easy", "punta, juego de destornilladores, punta TORX, TX, resistente y dura, universal, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt RA S 1", "juego de herramientas, destornillador carraca, porta-puntas manual, mecanismo de carraca con dentado fino, marcha conmutable derecha/izquierda, toma de puntas con imán permanente, puntas, sistema de búsqueda de herramienta Take it easy, caja de material textil, bolso para herramienta");
dc.importKeywords("Kraftform Kompakt RA M 1", "juego de herramientas, destornillador carraca, porta-puntas manual, mecanismo de carraca con dentado fino, marcha conmutable derecha/izquierda, toma de puntas Rapidaptor, puntas, sistema de búsqueda de herramienta Take it easy, caja de material textil, bolso para herramienta");
dc.importKeywords("Kraftform Kompakt RA M Imperial 1", "juego de herramientas, destornillador carraca, porta-puntas manual, mecanismo de carraca con dentado fino, marcha conmutable derecha/izquierda, toma de puntas Rapidaptor, puntas, sistema de búsqueda de herramienta Take it easy, caja de material textil, bolso para herramienta");
dc.importKeywords("Kraftform Kompakt RA S Imperial 1", "juego de herramientas, destornillador carraca, porta-puntas manual, mecanismo de carraca con dentado fino, marcha conmutable derecha/izquierda, toma de puntas con imán permanente, puntas, sistema de búsqueda de herramienta Take it easy, cotas en pulgadas, pulgadas, caja de material textil, bolso para herramienta");
dc.importKeywords("Safe-Torque A 1 SHK Set 1", "juego de herramientas, llave dinamométrica ajustable, marcha conmutable derecha/izquierda, insertos para llaves de vaso, vasos, vasos de punta, caja de material textil, bolso para herramienta, técnica sanitaria, técnica de calefacciones, técnica de climatización");
dc.importKeywords("9524", "juego de herramientas, técnica fotovoltáica, montaje de placas solares, destornillador dinamométrico con mango de pistola, toma de puntas Rapidaptor, llave de montaje conector de instalaciones solares, adaptador para vástago de herramienta ");
dc.importKeywords("9108", "llave de montaje para conectores de instalaciones solares");
dc.importKeywords("8790 HMC Deep Open", "inserto largo de llave de vaso, vaso largo de llave de inserción, vaso largo, vaso de inserción para profundidad, inserto de llave de vaso de profundidad, llave de inserción larga, vaso hexagonal de llave de inserción, vaso de inserto para pernos roscados, versión ranurada");
dc.importKeywords("7880 Joker XXL", "llave de boca de inserción autoajustante, tornillos hexagonales, herramienta dinamométrica de inserción, llave de boca con función de carraca, cuadradillo de inserción");
dc.importKeywords("7880 Joker XL", "llave de boca de inserción autoajustante, tornillos hexagonales, herramienta dinamométrica de inserción, llave de boca con función de carraca, cuadradillo de inserción");
dc.importKeywords("7880 Joker L", "llave de boca de inserción autoajustante, tornillos hexagonales, herramienta dinamométrica de inserción, llave de boca con función de carraca, cuadradillo de inserción");
dc.importKeywords("9530", "juego de herramientas, llave dinamométrica ajustable, apriete conmutable derecha/izquierda, llave de boca de inserción autoajustante, tornillos hexagonales, herramienta dinamométrica de inserción, llave de boca con función de carraca, cuadradillo de inserción, caja de material textil, bolso para herramienta");
dc.importKeywords("9532", "juego de herramientas, herramienta para bicicletas, minicarraca de puntas, porta-puntas manual, tronchacadenas, soportacadenas, pinza para cierre de cadena, puntas, caja de material textil, bolso para herramienta");
dc.importKeywords("9533", "juego de herramientas, herramienta para bicicletas");
dc.importKeywords("Wera 2go SHK 1", "juego de herramientas, técnica sanitaria, técnica de calefacciones, técnica de climatización, contenedor para herramientas, bolso de herramientas, herramienta para trabajos de técnica sanitaria, en calefacciones y climatizaciones");
dc.importKeywords("9541", "bidón de bebidas para bicicletas, bidón para ciclismo, material plástico, rellenable, de limpieza fácil");
dc.importKeywords("9540", "juego de destornilladores, destorcincel, destornilladores VDE aislados, puntas, adaptador para puntas, bidón de bebidas para bicicletas, material plástico, rellenable, de limpieza fácil");
dc.importKeywords("Bit-Check 7 Tool Finder 1, leer", "caja Bit Box vacía, soporte para puntas, alojamiento de puntas, caballete para puntas");
dc.importKeywords("KNIPEX Profi-Key für gängige Absperrsysteme", "llave para técnica de calefacciones, técnica de climatización, técnica sanitaria, tecnología de construcción, ventilación de calefacciones, aplicación de puntas ranura  y ranura en cruz PH, adaptador");
dc.importKeywords("950/9 Hex-Plus Multicolour Imperial 3", "Juego de llaves acodadas, tornillos de hexágono interior, Hex-Plus, cabeza hexagonal esférica, magnetizador, soporte de mesa, soporte para la pared, sistema de búsqueda de herramienta Take it easy, cotas en pulgadas, pulgada");
dc.importKeywords("Bit-Check 30 TX Universal 1 SB", "juego de puntas, tornillos TORX®, porta-puntas universal con manguito de acero inoxidable, porta-puntas con anillo elástico e imán permanente, puntas resistentes y duras");
dc.importKeywords("Bit-Check 30 Metal 1 SB", "juego de puntas, porta-puntas universal con manguito de acero inoxidable, porta-puntas, puntas resistentes y duras para trabajos en metal");
dc.importKeywords("Bit-Check 30 Wood 2 SB", "juego de puntas, porta-puntas universal con manguito de acero inoxidable, porta-puntas, puntas resistentes y duras para trabajos en madera");
dc.importKeywords("9108/2", "llave de montaje de conectores en instalaciones solares");
dc.importKeywords("9490", "bolso plegable de material textil vacío, bolso para herramienta vacío, herramienta para bicicletas");
dc.importKeywords("9534", "pasador de remache de repuesto para tronchacadenas");
dc.importKeywords("9542", "juego de recambios para tronchacadenas");
dc.importKeywords("7510", "puntas para destornilladores dinamométricos, pares de apriete elegibles, indicación numérica del valor de par de giro, marcha conmutable derecha/izquierda, toma de puntas, puntas para porta-puntas de cambio rápido, función \"Torque Lock\"");
dc.importKeywords("7510/16", "juego de herramientas, puntas para destornilladores dinamométricos, pares de apriete elegibles, indicación numérica del valor de par de giro, marcha conmutable derecha/izquierda, toma de puntas, puntas para porta-puntas de cambio rápido, función \"Torque Lock\", puntas, bolso plegable de material textil, bolso para herramientas");
dc.importKeywords("7510/7", "juego de herramientas, herramienta para bicicletas, puntas para destornilladores dinamométricos, pares de apriete elegibles, indicación numérica del valor de par de giro, marcha conmutable derecha/izquierda, toma de puntas, puntas para porta-puntas de cambio rápido, función \"Torque Lock\", puntas, bolso plegable de material textil, bolso para herramientas");
dc.importKeywords("9529 C", "llave escalonada, montaje y desmontaje de válvulas de radiadores, uniones atornilladas de termostatos y drenajes, herramienta para trabajos de técnica sanitaria, técnica de calefacciones y técnica de climatización");
dc.importKeywords("Bit-Check 6 SHK 1 SB", "juego de puntas, Bit Box, puntas resistentes y duras, herramienta para trabajos de técnica sanitaria, técnica de calefacciones y técnica de climatización");
dc.importKeywords("9523", "juego de herramientas para cadenas de bicicleta, herramienta para bicicletas");
dc.importKeywords("Kraftform Kompakt VDE 7 Universal 1 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 7 Universal 2 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 18 Universal 1 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 7 Imperial 1 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 16 Universal 1 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 7 extra slim 1 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy, diámetro de varilla reducido");
dc.importKeywords("Kraftform Kompakt VDE 16 extra slim 1 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy, diámetro de varilla reducido");
dc.importKeywords("Kraftform Kompakt Turbo i 1 Tool Finder", "juego de herramientas, varillas intercambiables para porta-puntas aislado VDE, destornillador manual VDE para engranajes, porta-puntas manual aislado, destornillador manual aislado, cuadruplicación de velocidad de atornillar, bolso para herramienta, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt Turbo i Imperial 1 Tool Finder", "juego de herramientas, varillas intercambiables para porta-puntas aislado VDE, destornillador manual VDE para engranajes, porta-puntas manual aislado, destornillador manual aislado, cuadruplicación de velocidad de atornillar, bolso para herramienta, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 15 Torque 1,2-3,0 Nm extra slim 1 Tool Finder", "herramienta manual;destornillador;juego de destornilladores;conjunto de destornilladores;juego de atornilladores;conjunto de atornilladores;kit de destornilladores;kit de atornilladores;herramienta de atornillar;herramienta;sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 16 Torque 1,2-3,0 Nm extra slim 1 Tool Finder", "herramienta manual;destornillador;juego de destornilladores;conjunto de destornilladores;juego de atornilladores;conjunto de atornilladores;kit de destornilladores;kit de atornilladores;herramienta de atornillar;herramienta;sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 17 extra slim 1 Tool Finder", "juego de herramientas para trabajos eléctricos, juego de herramientas VDE, herramientas para electricistas, juego de herramientas para electricista, juego de destornilladores para electricista, juego de destornilladores VDE, pinza para instalación eléctrica, pinza VDE, pinza multifuncional para trabajos eléctricos, herramientas aisladas, juego compacto de herramientas, varillas intercambiables para porta-puntas aislado VDE; sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 18 Universal 2 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 18 Imperial 1 Tool Finder", "juego de herramientas aisladas VDE, varillas intercambiables para porta-puntas aislado VDE, herramienta probada en un 100 %, bolso para el cinturón, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 iS TiE", "varilla intercambiable aislada VDE, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 iS TiE SB", "varilla intercambiable aislada VDE, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 1 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 2 x 157 TiE SB", "varilla intercambiable aislada VDE, tornillos Pozidriv, diámetro reducido de varilla, herramienta probada en un 100 %, sistema de búsqueda de herramienta Take it easy");

jalosession.getSessionContext().setLanguage(langFR);
dc.importKeywords("1834", "tournevis pour vis à fente Kraftform Ball-Grip, manche ergonomique, couples élevés, forces axiales élevées, dispositif antiroulement, Lasertip");
dc.importKeywords("1850 PH", "tournevis pour vis PH Kraftform Ball-Grip, manche ergonomique, couples élevés, forces axiales élevées, dispositif antiroulement, Lasertip");
dc.importKeywords("1855 PZ", "tournevis pour vis PZ Kraftform Ball-Grip, manche ergonomique, couples élevés, forces axiales élevées, dispositif antiroulement, Lasertip");
dc.importKeywords("9478", "housse textile, trousse à outils, rangement outils, léger, solide");
dc.importKeywords("8790 B Impaktor", "douille, visseuse à choc, gorge alésée profil à six pans, vis à tête hexagonale, écrous hexagonaux, repéreur d’outils « Take it easy »");
dc.importKeywords("9607", "barre magnétique rangement fixation, douille, visseuse à choc, gorge alésée profil à six pans, vis à tête hexagonale, écrous hexagonaux, repéreur d’outils « Take it easy »");
dc.importKeywords("9608", "barre magnétique rangement fixation, douilles, dimensions impériales, pouce, douille, visseuse à choc, gorge alésée profil à six pans, vis à tête hexagonale, écrous hexagonaux, repéreur d’outils « Take it easy »");
dc.importKeywords("454/5 HF SHK Set 1", "jeu de tournevis manche en T, manche en T, fonction de retenue vis à six pans creux, repéreur d’outils « Take it easy », Wera Hex-Plus, plomberie, chauffagerie, climatisation, couples de serrage élevés, couples de desserrage élevés");
dc.importKeywords("Bit-Check 6 SHK 1", "jeu d’embouts, embouts longs, embouts extra-rigides, usage universel, plomberie, chauffagerie, climatisation");
dc.importKeywords("9610", "barre magnétique rangement fixation, Joker, clé à fourche");
dc.importKeywords("9630", "jeu de clés mixtes à cliquet, barre magnétique rangement fixation, Joker, clé à fourche, repéreur d’outils « Take it easy »");
dc.importKeywords("9631", "jeu de clés mixtes à cliquet, barre magnétique rangement fixation, Joker, clé à fourche, repéreur d’outils « Take it easy »");
dc.importKeywords("9632", "jeu de clés mixtes à cliquet, barre magnétique rangement fixation, Joker, clé à fourche, dimensions impériales, pouce, repéreur d’outils « Take it easy »");
dc.importKeywords("9640", "jeu de clés mixtes, barre magnétique rangement fixation, Joker, clé à fourche, repéreur d’outils « Take it easy »");
dc.importKeywords("9641", "jeu de clés mixtes, barre magnétique rangement fixation, Joker, clé à fourche, repéreur d’outils « Take it easy »");
dc.importKeywords("9642", "jeu de clés mixtes, barre magnétique rangement fixation, Joker, clé à fourche, dimensions impériales, pouce, repéreur d’outils « Take it easy »");
dc.importKeywords("8798 B", "rallonge de douille adaptateur 3/8\", carré, fonction Lock, sécurisation douilles, bague de rotation rapide");
dc.importKeywords("8010 B", "cliquet isolé VDE, levier d’inversion changement de sens, forme fine, denture fine 4,5°, maintien sûr de la douille, outil contrôlé pièce à pièce");
dc.importKeywords("8100 SB 12", "jeu de cliquets, levier d’inversion changement de sens, douilles, rallonge, adaptateur, housse textile, trousse à outils");
dc.importKeywords("8100 SB 13", "jeu de cliquets, levier d’inversion changement de sens, douilles, rallonge, adaptateur, housse textile, trousse à outils, dimensions impériales, pouce");
dc.importKeywords("9650", "jeu de tournevis, barre magnétique rangement fixation, Lasertip, repéreur d’outils « Take it easy »");
dc.importKeywords("9611", "barre magnétique rangement fixation, tournevis");
dc.importKeywords("837 i RA", "porte-embouts isolé VDE lames interchangeables, fonction cliquet fine denture, changement de sens droite/gauche, outil contrôlé pièce à pièce");
dc.importKeywords("Kraftform Kompakt VDE 17 RA Imperial 1", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, fonction cliquet, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy », dimensions impériales, pouce");
dc.importKeywords("Kraftform Kompakt VDE 17 RA 1", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, fonction cliquet, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 17 Universal 1 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("838 RA S", "tournevis à cliquet embouts, mécanisme de cliquet fine denture, changement de sens droite/gauche, aimant permanent");
dc.importKeywords("Kraftform Kompakt VDE Big Pack 1", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, pochette pliable robuste, repéreur d’outils « Take it easy »");
dc.importKeywords("839 RA M", "tournevis à cliquet embouts, mécanisme de cliquet fine denture, changement de sens droite/gauche, admission d’embout Rapidaptor");
dc.importKeywords("Click-Torque X 7", "clé dynamométrique, admission carrée, clé dynamométrique réglable, serrage droite/gauche, mécanisme de déclenchement, outil interchangeable");
dc.importKeywords("Kraftform Kompakt 400 RA SHK Set 1", "jeu d’outils, manche en T embouts, mécanisme de cliquet denture fine, changement de sens droite/gauche, admission d’embout Rapidaptor, embouts extra-rigides, housse textile, trousse à outils");
dc.importKeywords("Kraftform Kompakt VDE 60 i TiE", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 iS TiE SB", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 iS TiE", "lame interchangeable isolée VDE, vis à fente, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 62 iS TiE SB", "lame interchangeable isolée VDE, vis Phillips-Recess, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 62 i TiE", "lame interchangeable isolée VDE, vis Phillips-Recess, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 62 iS TiE", "lame interchangeable isolée VDE, vis Phillips-Recess, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 68 i TiE", "lame interchangeable isolée VDE, vis à empreinte carrée, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 68 iS TiE", "lame interchangeable isolée VDE, vis à empreinte carrée, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 64 i TiE", "lame interchangeable isolée VDE, vis à six pans creux, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 67 i TiE", "lame interchangeable isolée VDE, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 67 iS TiE", "lame interchangeable isolée VDE, vis TORX®, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 62 i PH/S TiE", "lame interchangeable isolée VDE, vis PlusMinus à fente/Phillips, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH/S TiE", "lame interchangeable isolée VDE, vis PlusMinus à fente/Phillips, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 i PZ/S TiE", "lame interchangeable isolée VDE, vis PlusMinus à fente/Pozidriv, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ/S TiE", "lame interchangeable isolée VDE, vis PlusMinus à fente/Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 i TiE", "lame interchangeable isolée VDE, vis Pozidriv, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,4 x 2,5 x 157 TiE SB", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,5 x 3,0 x 157 TiE SB", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,6 x 3,5 x 157 TiE SB", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,8 x 4,0 x 157 TiE SB", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 i 1,0 x 5,5 x 157 TiE SB", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 i 1,2 x 6,5 x 157 TiE SB", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 i TiE SB", "lame interchangeable isolée VDE, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 60 iS 0,8 x 4,0 x 157 TiE SB", "lame interchangeable isolée VDE, vis à fente, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH 1 x 157 TiE SB", "lame interchangeable isolée VDE, vis PlusMinus à fente/Phillips, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH 2 x 157 TiE SB", "lame interchangeable isolée VDE, vis PlusMinus à fente/Phillips, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 64 i 4,0 x 157 TiE SB", "lame interchangeable isolée VDE, vis à six pans creux, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 64 i 5,0 x 157 TiE SB", "lame interchangeable isolée VDE, vis à six pans creux, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 64 i 6,0 x 157 TiE SB", "lame interchangeable isolée VDE, vis à six pans creux, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 15 x 157 TiE SB", "lame interchangeable isolée VDE, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 20 x 157 TiE SB", "lame interchangeable isolée VDE, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 25 x 157 TiE SB", "lame interchangeable isolée VDE, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 30 x 157 TiE SB", "lame interchangeable isolée VDE, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ/S # 1 x 157 TiE SB", "lame interchangeable isolée VDE, vis PlusMinus à fente/Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 1 x 157 TiE SB", "lame interchangeable isolée VDE, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 2 x 157 TiE SB", "lame interchangeable isolée VDE, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS TiE", "lame interchangeable isolée VDE, acier inoxydable, antirouille, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3060 i TiE", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 10 x 157 TiE SB", "lame interchangeable isolée VDE, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 67 i TORX® TiE SB", "lame interchangeable isolée VDE, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® TiE", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® TiE", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S TiE", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis PlusMinus à fente/Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ TiE", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3060 i 0,4 x 2,5 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3060 i 1,2 x 6,5 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis à fente, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 0,6 x 3,5 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 0,8 x 4,0 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 1,0 x 5,5 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS PH 1 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis PlusMinus à fente/Phillips, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS PH 2 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis PlusMinus à fente/Phillips, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis PlusMinus à fente/Phillips, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 8 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 9 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 10 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 30 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 15 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 20 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 25 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis TORX®, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S # 1 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis PlusMinus à fente/Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S # 2 x 157 mm TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis PlusMinus à fente/Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis PlusMinus à fente/Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ 1 x 157 TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ 2 x 157 TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ TiE SB", "lame interchangeable isolée VDE, acier inoxydable, antirouille, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt 71 Security", "jeu d’outils, vissages de sécurité, porte-embouts, admission d’embouts Rapidaptor, porte-embouts universel embouts, aimant permanent, embouts extra-rigides, housse textile, trousse à outils");
dc.importKeywords("Kraftform Kompakt 70 Universal", "jeu d’outils, porte-embouts, admission d’embouts Rapidaptor, porte-embouts universel embouts, aimant permanent, embouts extra-rigides, housse textile, trousse à outils");
dc.importKeywords("Kraftform Kompakt 27 XL Universal 1", "embout tournevis, lame longue, admission d’embouts verrouillable mince aimant permanent, réserve d’embouts intégrée, embouts");
dc.importKeywords("950/9 Hex-Plus 8", "jeu de clés coudées, chromé, vis à six pans creux, Hex-Plus, bloc de magnétisation, rack table, rack à fixer au mur");
dc.importKeywords("950/9 Hex-Plus Multicolour 3", "jeu de clés coudées, vis à six pans creux, Hex-Plus, tête sphérique hexagonale, bloc de magnétisation, rack table, rack à fixer au mur, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt 27 XL Universal Imperial Set 1", "embout tournevis, lame longue, admission d’embouts verrouillable mince aimant permanent, réserve d’embouts intégrée, embouts, système impérial, pouce");
dc.importKeywords("Kraftform Kompakt 27 XL SHK 1", "embout tournevis, lame longue, admission d’embouts verrouillable mince aimant permanent, réserve d’embouts intégrée, embouts, plomberie, chauffagerie, climatisation");
dc.importKeywords("867/4 Z TORX® Take it easy", "embout, embout de tournevis, embout TORX, TX, extra-rigide, universel, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt RA S 1", "jeu d’outils, tournevis à cliquet, manche embouts, mécanisme de cliquet denture fine, changement de sens droite/gauche, admission d’embouts aimant permanent, embouts, repéreur d’outils « Take it easy », housse textile, trousse à outils");
dc.importKeywords("Kraftform Kompakt RA M 1", "jeu d’outils, tournevis à cliquet, manche embouts, mécanisme de cliquet denture fine, changement de sens droite/gauche, admission d’embout Rapidaptor, embouts, repéreur d’outils « Take it easy », housse textile, trousse à outils");
dc.importKeywords("Kraftform Kompakt RA M Imperial 1", "jeu d’outils, tournevis à cliquet, manche embouts, mécanisme de cliquet denture fine, changement de sens droite/gauche, admission d’embout Rapidaptor, embouts, repéreur d’outils « Take it easy », housse textile, trousse à outils");
dc.importKeywords("Kraftform Kompakt RA S Imperial 1", "jeu d’outils, tournevis à cliquet, manche embouts, mécanisme de cliquet denture fine, changement de sens droite/gauche, admission d’embouts aimant permanent, embouts, repéreur d’outils « Take it easy », dimensions impériales, pouce, housse textile, trousse à outils");
dc.importKeywords("Safe-Torque A 1 SHK Set 1", "jeu d’outils, clé dynamométrique réglable, changement de sens droite/gauche, douilles, douille-embouts, housse textile, trousse à outils, plomberie, chauffagerie, climatisation");
dc.importKeywords("9524", "jeu d’outils, photovoltaïque, montage de panneaux solaires, tournevis dynamométrique manche revolver, admission d’embouts Rapidaptor, clé de montage pour connecteurs solaires, tige d’outil adaptateur");
dc.importKeywords("9108", "clé de montage pour connecteurs solaires");
dc.importKeywords("8790 HMC Deep Open", "douille longue, douille profonde, douille hexagonale, douille pour tiges filetées, version fendue");
dc.importKeywords("7880 Joker XXL", "clé à fourche interchangeable auto-ajustable, vis à six pans, outil interchangeable couple dynamométrique, clé à fourche avec fonction cliquet, attachement carré");
dc.importKeywords("7880 Joker XL", "clé à fourche interchangeable auto-ajustable, vis à six pans, outil interchangeable couple dynamométrique, clé à fourche avec fonction cliquet, attachement carré");
dc.importKeywords("7880 Joker L", "clé à fourche interchangeable auto-ajustable, vis à six pans, outil interchangeable couple dynamométrique, clé à fourche avec fonction cliquet, attachement carré");
dc.importKeywords("9530", "jeu d’outils, clé dynamométrique réglable, changement de sens droite/gauche, clé à fourche interchangeable auto-ajustable, vis à six pans, outil interchangeable couple dynamométrique, clé à fourche avec fonction cliquet, attachement carré, housse textile, trousse à outils");
dc.importKeywords("9532", "jeu d’outils, outil vélo, cliquet à embout Mini, porte-embouts, dérive-chaîne, crochet de chaîne, pince à attache rapide, embouts, pochette textile pliable, trousse à outils");
dc.importKeywords("9533", "jeu d’outils, outil vélo");
dc.importKeywords("Wera 2go SHK 1", "jeu d’outils, plomberie, chauffagerie, climatisation, valise porte-outils, trousse à outils, outil pour plomberie chauffagerie et climatisation");
dc.importKeywords("9541", "bidon vélo, bidon cyclisme, plastique, réutilisable, nettoyage facile");
dc.importKeywords("9540", "jeu de tournevis, tournevis-burin, tournevis isolés VDE, embouts, adaptateur d’embouts, bidon vélo, plastique, réutilisable, nettoyage facile");
dc.importKeywords("Bit-Check 7 Tool Finder 1, leer", "Bit-Box vide, porte-embouts, rangement embouts, support embouts");
dc.importKeywords("KNIPEX Profi-Key für gängige Absperrsysteme", "clé chauffagerie climatisation plomberie immotique, aération de chauffage, douille à embout fente cruciforme PH, adaptateur");
dc.importKeywords("950/9 Hex-Plus Multicolour Imperial 3", "jeu de clés coudées, vis à six pans creux, Hex-Plus, tête sphérique hexagonale, bloc de magnétisation, rack table, rack à fixer au mur, repéreur d’outils « Take it easy », système impérial, pouce");
dc.importKeywords("Bit-Check 30 TX Universal 1 SB", "jeu d’embouts, vis TORX®, porte-embouts universel douille inox, porte-embouts jonc d’arrêt aimant permanent, embouts extra-rigides");
dc.importKeywords("Bit-Check 30 Metal 1 SB", "jeu d’embouts, porte-embouts universel douille inox, porte-embouts, embouts extra-rigides métal");
dc.importKeywords("Bit-Check 30 Wood 2 SB", "jeu d’embouts, porte-embouts universel douille inox, porte-embouts, embouts extra-rigides bois");
dc.importKeywords("9108/2", "clé de montage pour connecteurs solaires");
dc.importKeywords("9490", "pochette textile pliable vide, trousse à outils vide, outil vélo");
dc.importKeywords("9534", "goupille de rechange dérive-chaîne");
dc.importKeywords("9542", "jeu de pièces de rechange dérive-chaîne");
dc.importKeywords("7510", "tournevis dynamométriques embouts, couples de serrage au choix, affichage numérique de la valeur de couple, changement de sens droite/gauche, admission d’embouts, manche à changement rapide embouts, fonction Torque Lock");
dc.importKeywords("7510/16", "jeu d’outils, tournevis dynamométriques embouts, couples de serrage au choix, affichage numérique de la valeur de couple, changement de sens droite/gauche, admission d’embouts, manche à changement rapide embouts, fonction Torque Lock, embouts, pochette textile pliable, trousse à outils");
dc.importKeywords("7510/7", "jeu d’outils, outil vélo, tournevis dynamométriques embouts, couples de serrage au choix, affichage numérique de la valeur de couple, changement de sens droite/gauche, admission d’embouts, manche à changement rapide embouts, fonction Torque Lock, embouts, pochette textile pliable, trousse à outils");
dc.importKeywords("9529 C", "clé étagée, montage démontage vannes de radiateurs vissages de thermostats raccords d’évacuation, outil pour plomberie chauffagerie climatisation, plomberie, chauffagerie, climatisation");
dc.importKeywords("Bit-Check 6 SHK 1 SB", "jeu d’embouts, Bit-Box, embouts extra-rigides, outil pour plomberie chauffagerie climatisation, plomberie, chauffagerie, climatisation");
dc.importKeywords("9523", "chaînes de vélo, jeu d’outils, outil vélo");
dc.importKeywords("Kraftform Kompakt VDE 7 Universal 1 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 7 Universal 2 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 18 Universal 1 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 7 Imperial 1 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 16 Universal 1 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 7 extra slim 1 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy », lame à diamètre réduit");
dc.importKeywords("Kraftform Kompakt VDE 16 extra slim 1 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy », lame à diamètre réduit");
dc.importKeywords("Kraftform Kompakt Turbo i 1 Tool Finder", "jeu d’outils, porte-embouts VDE lames interchangeables, VDE train tournevis à main, manche isolé, tournevis à main isolé, vitesse de vissage quadruplée, trousse à outils, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt Turbo i Imperial 1 Tool Finder", "jeu d’outils, porte-embouts VDE lames interchangeables, VDE train tournevis à main, manche isolé, tournevis à main isolé, vitesse de vissage quadruplée, trousse à outils, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 15 Torque 1,2-3,0 Nm extra slim 1 Tool Finder", "jeu d’outils, tournevis, jeu de tournevis, outil de vissage, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 16 Torque 1,2-3,0 Nm extra slim 1 Tool Finder", "jeu d’outils, tournevis, jeu de tournevis, outil de vissage, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 17 extra slim 1 Tool Finder", "jeu d’outils électronique, jeu d’outils VDE, outils d’électricien, jeu d’outils électricien, jeu de tournevis électricien, jeu de tournevis VDE, pince installations électriques, pince VDE, pince multifonction électrique, outils isolés, jeu d’outils compact, porte-embouts VDE lames interchangeables, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 18 Universal 2 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 18 Imperial 1 Tool Finder", "jeu d’outils isolés VDE, porte-embouts isolé VDE lames interchangeables, outil contrôlé pièce à pièce, trousse de ceinture, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 iS TiE", "lame interchangeable isolée VDE, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 iS TiE SB", "lame interchangeable isolée VDE, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 1 x 157 TiE SB", "lame interchangeable isolée VDE, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 2 x 157 TiE SB", "lame interchangeable isolée VDE, vis Pozidriv, lame à diamètre réduit, outil contrôlé pièce à pièce, repéreur d’outils « Take it easy »");

jalosession.getSessionContext().setLanguage(langFI);
dc.importKeywords("1834", "uraruuvitaltta Kraftform Ball Grip, otesyvennykset, suuret vääntömomentit, suuret aksiaalivoimat, kierimisenesto, Lasertip-kärki, suuret vääntömomentit, suuret aksiaalivoimat");
dc.importKeywords("1850 PH", "ristipäinen PH-ruuvitaltta Kraftorm Ball Grip, otesyvennykset, suuret vääntömomentit, suuret aksiaalivoimat, Kierimisenesto, Lasertip-kärki, suuret vääntömomentit, suuret aksiaalivoimat");
dc.importKeywords("1855 PZ", "ristipäinen PZ-ruuvitaltta Kraftform Ball Grip, otesyvennykset, suuret vääntömomentit, suuret aksiaalivoimat, Kierimisenesto, Lasertip-kärki, suuret vääntömomentit, suuret aksiaalivoimat");
dc.importKeywords("9478", "tekstiililaatikko, työkalulaukku, säilytys työkalu, kevyt, tukeva");
dc.importKeywords("8790 B Impaktor", "hylsy, pistohylsy, iskuväännin, kuusiokantaprofiili ura reikä, kuusiokantaruuvit, kuusiokantamutterit, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("9607", "magneettilista säilytys kiinnitys, Hylsy, pistohylsy, iskuväännin, kuusiokantaprofiili ura reikä, kuusiokantaruuvit, kuusiokantamutterit, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("9608", "magneettilista säilytys kiinnitys, Hylsy, tuumaiset mitat, tuuma, pistohylsy, iskuväännin, kuusiokantaprofiili ura reikä, kuusiokantaruuvit, kuusiokantamutterit, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("454/5 HF SHK Set 1", "ruuvitalttasarja T-kahva, T-kahva, T-kahva, pitotoiminto kuusiokoloruuvit, Weran Take it easy -värikoodausjärjestelmä, Wera Hex-Plus, vesitekniikka, lämmitystekniikka, ilmastointitekniikka, korkeat kiristysmomentit, korkea irrotusmomentit");
dc.importKeywords("Bit-Check 6 SHK 1", "Bits-kärkisarja, pitkät Bits-kärjet, sitkeäksi karkaistut Bits-kärjet, yleiskäyttö, vesitekniikka, lämmitystekniikka, ilmastointitekniikka");
dc.importKeywords("9610", "magneettilista säilytys kiinnitys, Joker, kiintoavain");
dc.importKeywords("9630", "räikkälenkkiavainsarja, magneettilista säilytys kiinnitys, Joker, kiintoavain, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("9631", "Räikkälenkkiavainsarja, magneettilista säilytys kiinnitys, Joker, kiintoavain, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("9632", "Räikkälenkkiavainsarja, magneettilista säilytys kiinnitys, Joker, kiintoavain, tuumaiset mitat, tuuma, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("9640", "kiintolenkkiavainsarja, magneettilista säilytys kiinnitys, Joker, kiintoavain, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("9641", "kiintolenkkiavainsarja, magneettilista säilytys kiinnitys, Joker, kiintoavain, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("9642", "kiintolenkkiavainsarja, magneettilista säilytys kiinnitys, Joker, kiintoavain, tuumaiset mitat, tuuma, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("8798 B", "hylsyavain jatkokappale sovitin 3/8\", nelikanta, Lock-toiminto, varmistaminen hylsyt, vapaasti pyörivä otepinta");
dc.importKeywords("8010 B", "eristetty VDE-räikkä, suunnanvaihtovipu suunnanvaihto, kapea muotoilu, hieno hammastus 4,5°, varma hylsyn lukitus, yksittäin testattu työkalu");
dc.importKeywords("8100 SB 12", "räikkäsarja, suunnanvaihtovipu suunnanvaihto, hylsyt, hylsyt, jatkokappale, sovitin, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("8100 SB 13", "räikkäsarja, suunnanvaihtovipu suunnanvaihto, Hylsyt, hylsyt, Jatkokappale, Sovitin, tekstiililaatikko, Työkalulaukku, tuumaiset mitat, tuuma");
dc.importKeywords("9650", "ruuvitalttasarja, magneettilista säilytys kiinnitys, Lasertip-kärki, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("9611", "magneettilista säilytys kiinnitys, ruuvitaltta");
dc.importKeywords("837 i RA", "eristetty VDE-ruuvitalttakahva vaihtoterät, räikkätoiminto hieno hammastus, suunta vaihdettavissa myötä- ja vastapäivään, yksittäin testattu työkalu");
dc.importKeywords("Kraftform Kompakt VDE 17 RA Imperial 1", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, räikkätoiminto, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä, tuumaiset mitat, tuuma");
dc.importKeywords("Kraftform Kompakt VDE 17 RA 1", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, Räikkätoiminto, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 17 Universal 1 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("838 RA S", "räikkäruuvitaltta Bits-kärjet, räikkämekanismi hieno hammastus, suunta vaihdettavissa myötä- ja vastapäivään, kestomagneetti");
dc.importKeywords("Kraftform Kompakt VDE Big Pack 1", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, lujatekoinen säilytystasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("839 RA M", "räikkäruuvitaltta Bits-kärjet, räikkämekanismi hieno hammastus, suunta vaihdettavissa myötä- ja vastapäivään, Rapidaptor Bits-kärkikiinnitys");
dc.importKeywords("Click-Torque X 7", "momenttiavain, nelikantakiinnitys, momenttiavain säädettävä, myötä- ja vastapäivään kiristäminen, laukaisumekanismi, vaihtopää");
dc.importKeywords("Kraftform Kompakt 400 RA SHK Set 1", "työkalusarja, T-kahvallinen ruuvitalttakahva Bits-kärjet, räikkämekanismi hieno hammastus, suunta vaihdettavissa myötä- ja vastapäivään, Rapidaptor Bits-kärkikiinnitys, sitkeäksi karkaistut Bits-kärjet, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("Kraftform Kompakt VDE 60 i TiE", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 iS TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 iS TiE", "eristetty VDE-vaihtoterä, uraruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 62 iS TiE SB", "eristetty VDE-vaihtoterä, ristipääruuvit Phillips, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 62 i TiE", "eristetty VDE-vaihtoterä, Ristipääruuvit Phillips, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 62 iS TiE", "eristetty VDE-vaihtoterä, Ristipääruuvit Phillips, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 68 i TiE", "eristetty VDE-vaihtoterä, neliökoloruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 68 iS TiE", "eristetty VDE-vaihtoterä, neliökoloruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 64 i TiE", "eristetty VDE-vaihtoterä, kuusiokoloruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 67 i TiE", "eristetty VDE-vaihtoterä, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 67 iS TiE", "eristetty VDE-vaihtoterä, TORX® ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 62 i PH/S TiE", "eristetty VDE-vaihtoterä, PlusMinus-ruuvit ura/Phillips, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH/S TiE", "eristetty VDE-vaihtoterä, PlusMinus-ruuvit ura/Phillips, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 i PZ/S TiE", "eristetty VDE-vaihtoterä, PlusMinus-ruuvit ura/Pozidriv, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ/S TiE", "eristetty VDE-vaihtoterä, PlusMinus-ruuvit ura/Pozidriv, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 i TiE", "eristetty VDE-vaihtoterä, Pozidriv-ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,4 x 2,5 x 157 TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,5 x 3,0 x 157 TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,6 x 3,5 x 157 TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 i 0,8 x 4,0 x 157 TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 i 1,0 x 5,5 x 157 TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 i 1,2 x 6,5 x 157 TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 i TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 60 iS 0,8 x 4,0 x 157 TiE SB", "eristetty VDE-vaihtoterä, uraruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH 1 x 157 TiE SB", "eristetty VDE-vaihtoterä, PlusMinus-ruuvit ura/Phillips, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 62 iS PH 2 x 157 TiE SB", "eristetty VDE-vaihtoterä, PlusMinus-ruuvit ura/Phillips, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 64 i 4,0 x 157 TiE SB", "eristetty VDE-vaihtoterä, kuusiokoloruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 64 i 5,0 x 157 TiE SB", "eristetty VDE-vaihtoterä, kuusiokoloruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 64 i 6,0 x 157 TiE SB", "eristetty VDE-vaihtoterä, kuusiokoloruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 15 x 157 TiE SB", "eristetty VDE-vaihtoterä, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 20 x 157 TiE SB", "eristetty VDE-vaihtoterä, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 25 x 157 TiE SB", "eristetty VDE-vaihtoterä, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 30 x 157 TiE SB", "eristetty VDE-vaihtoterä, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ/S # 1 x 157 TiE SB", "eristetty VDE-vaihtoterä, PlusMinus-ruuvit ura/Pozidriv, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 1 x 157 TiE SB", "eristetty VDE-vaihtoterä, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 2 x 157 TiE SB", "eristetty VDE-vaihtoterä, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS TiE", "eristetty VDE-vaihtoterä, ruostumaton teräs, ruostumaton, uraruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3060 i TiE", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 67 i TX 10 x 157 TiE SB", "eristetty VDE-vaihtoterä, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 67 i TORX® TiE SB", "eristetty VDE-vaihtoterä, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® TiE", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® TiE", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S TiE", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, PlusMinus-ruuvit ura/Pozidriv, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ TiE", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3060 i 0,4 x 2,5 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3060 i 1,2 x 6,5 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, uraruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 0,6 x 3,5 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, uraruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 0,8 x 4,0 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, uraruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3060 iS 1,0 x 5,5 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, uraruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS PH 1 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, PlusMinus-ruuvit ura/Phillips, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS PH 2 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, PlusMinus-ruuvit ura/Phillips, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3062 iS TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, PlusMinus-ruuvit ura/Phillips, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 8 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 9 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 10 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® 30 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 i TORX® TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 15 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 20 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® 25 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3067 iS TORX® TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, TORX® ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S # 1 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, PlusMinus-ruuvit ura/Pozidriv, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S # 2 x 157 mm TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, PlusMinus-ruuvit ura/Pozidriv, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ/S TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, PlusMinus-ruuvit ura/Pozidriv, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ 1 x 157 TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ 2 x 157 TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 3065 iS PZ TiE SB", "eristetty VDE-vaihtoterä, Ruostumaton teräs, ruostumaton, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt 71 Security", "Työkalusarja, turvaruuviliitännät, ruuvitalttakahva, Rapidaptor Bits-kärkikiinnitys, yleispidin Bits-kärjet, kestomagneetti, sitkeäksi karkaistut Bits-kärjet, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("Kraftform Kompakt 70 Universal", "Työkalusarja, Ruuvitalttakahvat, Rapidaptor Bits-kärkikiinnitys, yleispidin Bits-kärjet, kestomagneetti, sitkeäksi karkaistut Bits-kärjet, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("Kraftform Kompakt 27 XL Universal 1", "Bits-ruuvitaltta, pitkä terä, kapea lukittava Bits-kärkikiinnitys kestomagneetti, integroitu Bits-kärkilipas, Bits-kärjet");
dc.importKeywords("950/9 Hex-Plus 8", "L-avainsarja, runko kromattu, kuusiokoloruuvit, Hex-Plus, magnetointilaite, teline pöytä, teline kiinnitys seinä");
dc.importKeywords("950/9 Hex-Plus Multicolour 3", "L-avainsarja, kuusiokoloruuvit, Hex-Plus, kuusikantainen pallopää, magnetointilaite, teline pöytä, teline kiinnitys seinä, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt 27 XL Universal Imperial Set 1", "Bits-ruuvitaltta, pitkä terä, kapea lukittava Bits-kärkikiinnitys kestomagneetti, integroitu Bits-kärkilipas, Bits-kärjet, tuumaiset mitat, tuuma");
dc.importKeywords("Kraftform Kompakt 27 XL SHK 1", "Bits-ruuvitaltta, pitkä terä, kapea lukittava Bits-kärkikiinnitys kestomagneetti, integroitu Bits-kärkilipas, Bits-kärjet, vesitekniikka, lämmitystekniikka, ilmastointitekniikka");
dc.importKeywords("867/4 Z TORX® Take it easy", "Bits-kärki, ruuvikärki, TORX Bits-kärki, TX, sitkeäksi karkaistu, yleiskäyttöinen, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt RA S 1", "Työkalusarja, räikkäruuvitaltta, ruuvitalttakahva Bits-kärjet, räikkämekanismi hieno hammastus, suunta vaihdettavissa myötä- ja vastapäivään, Bits-kärkikiinnitys kestomagneetti, Bits-kärjet, Take it easy -värikoodausjärjestelmä, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("Kraftform Kompakt RA M 1", "Työkalusarja, Räikkäruuvitaltat, ruuvitalttakahva Bits-kärjet, räikkämekanismi hieno hammastus, suunta vaihdettavissa myötä- ja vastapäivään, Rapidaptor Bits-kärkikiinnitys, Bits-kärjet, Take it easy -värikoodausjärjestelmä, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("Kraftform Kompakt RA M Imperial 1", "Työkalusarja, Räikkäruuvitaltat, ruuvitalttakahva Bits-kärjet, räikkämekanismi hieno hammastus, suunta vaihdettavissa myötä- ja vastapäivään, Rapidaptor Bits-kärkikiinnitys, Bits-kärjet, Take it easy -värikoodausjärjestelmä, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("Kraftform Kompakt RA S Imperial 1", "Työkalusarja, Räikkäruuvitaltat, ruuvitalttakahva Bits-kärjet, räikkämekanismi hieno hammastus, suunta vaihdettavissa myötä- ja vastapäivään, Bits-kärkikiinnitys kestomagneetti, Bits-kärjet, Take it easy -värikoodausjärjestelmä, tuumaiset mitat, tuuma, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("Safe-Torque A 1 SHK Set 1", "Työkalusarja, säädettävä momenttiavain, suunta vaihdettavissa myötä- ja vastapäivään kiristämiseen, Hylsyt, hylsyt, kärkihylsyt, tekstiililaatikko, Työkalulaukku, vesitekniikka, lämmitystekniikka, ilmastointitekniikka");
dc.importKeywords("9524", "Työkalusarja, aurinkosähkö, asennus aurinkopaneelit, momenttiruuvitaltta pistoolikahva, Rapidaptor Bits-kärkikiinnitys, asennusavain aurinkosähköliitin, työkalusovitin sovitin");
dc.importKeywords("9108", "asennusavain aurinkosähköliitin");
dc.importKeywords("8790 HMC Deep Open", "hylsy pitkä, hylsyavaimen hylsy pitkä, pitkä hylsy, syvä pistohylsy, syvä hylsy, hylsyavain pitkä, kuusiokanta hylsyavain hylsy, hylsy kierretangoille, uritettu rakenne");
dc.importKeywords("7880 Joker XXL", "itsesäätyvä kiintoavainvaihtopää, kuusioruuvit, vaihtopää vääntömomentti, kiintoavain räikkätoiminnolla, nelikulmavaihtopää");
dc.importKeywords("7880 Joker XL", "itsesäätyvä kiintoavainvaihtopää, kuusioruuvit, vaihtopää vääntömomentti, kiintoavain räikkätoiminnolla, nelikulmavaihtopää");
dc.importKeywords("7880 Joker L", "itsesäätyvä kiintoavainvaihtopää, kuusioruuvit, vaihtopää vääntömomentti, kiintoavain räikkätoiminnolla, nelikulmavaihtopää");
dc.importKeywords("9530", "Työkalusarja, säädettävä momenttiavain, suunta vaihdettavissa myötä- ja vastapäivään kiristämiseen, itsesäätyvät kiintoavainvaihtopäät, kuusioruuvit, vaihtopäät vääntömomentti, kiintoavain räikkätoiminnolla, nelikulmavaihtopää, tekstiililaatikko, Työkalulaukku");
dc.importKeywords("9532", "Työkalusarja, polkupyörä työkalu, Mini-Bits-kärkiräikkä, Bits-ruuvitalttakahva, ketjun niittaustyökalu, ketjunpidin, ketjulukkopihdit, Bits-kärjet, tekstiilinen säilytystasku, Työkalulaukku");
dc.importKeywords("9533", "Työkalusarja, polkupyörä työkalu");
dc.importKeywords("Wera 2go SHK 1", "Työkalusarja, vesitekniikka, lämmitystekniikka, ilmastointitekniikka, työkalusalkku, Työkalulaukku, työkalu LVI-sovelluksiin");
dc.importKeywords("9541", "polkupyörä juomapullo, juomapullo pyöräily, muovi, uudelleen täytettävä, helppo puhdistus");
dc.importKeywords("9540", "Ruuvitalttasarja, iskunkestävät ruuvitaltat, eristetyt VDE-ruuvitaltat, Bits-kärjet, Bits-kärkisovitin, polkupyörä juomapullo, muovi, uudelleen täytettävä, helppo puhdistus");
dc.importKeywords("Bit-Check 7 Tool Finder 1, leer", "Bit-Box tyhjä, Bits-kärkien pidike, Bits-kärkien säilytys, Bits-kärkien jalusta");
dc.importKeywords("KNIPEX Profi-Key für gängige Absperrsysteme", "avain lämmitystekniikka ilmastointitekniikka vesitekniikka talotekniikka, lämmityksen ilmaus, Bits-kärki ura ristipää PH, Sovitin");
dc.importKeywords("950/9 Hex-Plus Multicolour Imperial 3", "L-avainsarja, kuusiokoloruuvit, Hex-Plus, kuusikantainen pallopää, magnetointilaite, teline pöytä, teline kiinnitys seinä, Take it easy -värikoodausjärjestelmä, tuumaiset mitat, tuuma");
dc.importKeywords("Bit-Check 30 TX Universal 1 SB", "Bits-kärkisarja, TORX® ruuvit, yleispidin ruostumattomasta teräksestä valmistettu hylsy, Bits-kärkipidin lukkorengas kestomagneetti, sitkeäksi karkaistut Bits-kärjet");
dc.importKeywords("Bit-Check 30 Metal 1 SB", "Bits-kärkisarja, yleispidin ruostumattomasta teräksestä valmistettu hylsy, Bits-kärkipidin, sitkeäksi karkaistut Bits-kärjet metallin kanssa työskentely");
dc.importKeywords("Bit-Check 30 Wood 2 SB", "Bits-kärkisarja, yleispidin ruostumattomasta teräksestä valmistettu hylsy, Bits-kärkipidin, sitkeäksi karkaistut Bits-kärjet puun kanssa työskentely");
dc.importKeywords("9108/2", "asennusavain aurinkosähköliitin");
dc.importKeywords("9490", "tekstiilinen säilytystasku tyhjä, työkalulaukku tyhjä, polkupyörä työkalu");
dc.importKeywords("9534", "varaniittaustappi ketjun niittaustyökalu");
dc.importKeywords("9542", "varaosasarja ketjun niittaustyökalu");
dc.importKeywords("7510", "momenttiruuvitaltta Bits-kärjet, valittavissa olevat kiristysmomentit, numeerinen momenttiarvon näyttö, suunta vaihdettavissa myötä- ja vastapäivään, Bits-kärkikiinnitys, pikaistukka Bits-kärjet, Torque Lock -toiminto");
dc.importKeywords("7510/16", "Työkalusarja, momenttiruuvitaltta Bits-kärjet, valittavissa olevat kiristysmomentit, numeerinen momenttiarvon näyttö, suunta vaihdettavissa myötä- ja vastapäivään, Bits-kärkikiinnitys, pikaistukka Bits-kärjet, Torque Lock -toiminto, Bits-kärjet, tekstiilinen säilytystasku, Työkalulaukku");
dc.importKeywords("7510/7", "Työkalusarja, polkupyörä työkalu, momenttiruuvitaltta Bits-kärjet, valittavissa olevat kiristysmomentit, numeerinen momenttiarvon näyttö, suunta vaihdettavissa myötä- ja vastapäivään, Bits-kärkikiinnitys, pikaistukka Bits-kärjet, Torque Lock -toiminto, Bits-kärjet, tekstiilinen säilytystasku, Työkalulaukku");
dc.importKeywords("9529 C", "porrasavain, asennus irrotus patteriventtiilit termostaattiliittimet tyhjennysliittimet, työkalu LVI-sovelluksiin, vesitekniikka, lämmitystekniikka, ilmastointitekniikka");
dc.importKeywords("Bit-Check 6 SHK 1 SB", "Bits-kärkisarja, Bit-Box, sitkeäksi karkaistut Bits-kärjet, työkalu LVI-sovelluksiin, vesitekniikka, lämmitystekniikka, ilmastointitekniikka");
dc.importKeywords("9523", "polkupyörien ketjujen työkalusarja, polkupyörä työkalu");
dc.importKeywords("Kraftform Kompakt VDE 7 Universal 1 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 7 Universal 2 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 18 Universal 1 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 7 Imperial 1 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 16 Universal 1 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 7 extra slim 1 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä, kavennettu terä");
dc.importKeywords("Kraftform Kompakt VDE 16 extra slim 1 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä, kavennettu terä");
dc.importKeywords("Kraftform Kompakt Turbo i 1 Tool Finder", "työkalusarja, VDE-ruuvitalttakahva, vaihdettavat terät, VDE-ruuvinväännin, eristetty ruuvitalttakahva, eristetty ruuvinväännin, nelinkertainen ruuvausnopeus, työkalulaukku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt Turbo i Imperial 1 Tool Finder", "työkalusarja, VDE-ruuvitalttakahva, vaihdettavat terät, VDE-ruuvinväännin, eristetty ruuvitalttakahva, eristetty ruuvinväännin, nelinkertainen ruuvausnopeus, työkalulaukku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 15 Torque 1,2-3,0 Nm extra slim 1 Tool Finder", "käsityökalu; ruuvitaltta; ruuvitaltta-sarja; ruuvitaltta-setti; ruuvitalttasarja; ruuvitalttasetti; ruuvimeisseli-sarja; ruuvimeisseli-setti; ruuvimeisselisarja; ruuvimeisselisetti; ruuvaustyökalu; työkalu; Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 16 Torque 1,2-3,0 Nm extra slim 1 Tool Finder", "käsityökalu; ruuvitaltta; ruuvitaltta-sarja; ruuvitaltta-setti; ruuvitalttasarja; ruuvitalttasetti; ruuvimeisseli-sarja; ruuvimeisseli-setti; ruuvimeisselisarja; ruuvimeisselisetti; ruuvaustyökalu; työkalu; Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 17 extra slim 1 Tool Finder", "työkalusarja sähkö, VDE-työkalusarja, sähköasentajan työkalut, työkalusarja sähköasentajat, sähkö ruuvitalttasarja, VDE-ruuvitalttasarja, sähkö asennuspihdit, VDE-pihdit, sähkö monitoimipihdit, eristetyt työkalut, kompakti työkalusarja, ruuvitalttakahva VDE-vaihtoterät; Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 18 Universal 2 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 18 Imperial 1 Tool Finder", "eristetty VDE-työkalusarja, eristetty VDE-ruuvitalttakahva vaihtoterät, yksittäin testattu työkalu, vyötasku, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 iS TiE", "eristetty VDE-vaihtoterä, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 iS TiE SB", "eristetty VDE-vaihtoterä, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 1 x 157 TiE SB", "eristetty VDE-vaihtoterä, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");
dc.importKeywords("Kraftform Kompakt VDE 65 iS PZ 2 x 157 TiE SB", "eristetty VDE-vaihtoterä, Pozidriv-ruuvit, kavennettu terä, yksittäin testattu työkalu, Take it easy -värikoodausjärjestelmä");


jalosession.getSessionContext().setLanguage(de);
out.println("Script execution finished.");
%>
</body>
</html>

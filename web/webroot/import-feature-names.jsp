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
dc.importFeatureName ( "se", "AAA820f001", "Vierkant", "Fyrkant" );
dc.importFeatureName ( "se", "_00714f001", "Länge kurzer Schenkel", "Längd hos kort skänkel" );
dc.importFeatureName ( "se", "_00011f001", "Artikelnummer", "Artikelnummer" );
dc.importFeatureName ( "se", "_00048f001", "Aufnahmegröße sechskant", "Hållarstorlek sexkant" );
dc.importFeatureName ( "se", "_00968f001", "Abtriebsgrösse Torx", "Fattningsstorlek Torx" );
dc.importFeatureName ( "cs", "_00714f001", "Länge kurzer Schenkel", "Délka krátkého ramene" );
dc.importFeatureName ( "cs", "AAA794f001", "Max. Kopfhöhe - Ratschenringschlüssel", "Max. výška hlavy – ráčnový očkový klíč" );
dc.importFeatureName ( "cs", "_00048f001", "Aufnahmegröße sechskant", "Velikost upnutí šestihranu" );
dc.importFeatureName ( "cs", "_00968f001", "Abtriebsgrösse Torx", "Velikost profilu Torx" );
dc.importFeatureName ( "dk", "_00714f001", "Länge kurzer Schenkel", "Længde af kort arm" );
dc.importFeatureName ( "dk", "_00011f001", "Artikelnummer", "Artikelnummer" );
dc.importFeatureName ( "dk", "_00048f001", "Aufnahmegröße sechskant", "Tilslutningsstørrelse sekskant" );
dc.importFeatureName ( "dk", "_00968f001", "Abtriebsgrösse Torx", "Udgangsstørrelse Torx" );
dc.importFeatureName ( "es", "_00714f001", "Länge kurzer Schenkel", "longitud del brazo largo" );
dc.importFeatureName ( "es", "_00048f001", "Aufnahmegröße sechskant", "tamaño de toma del hexagonal" );
dc.importFeatureName ( "es", "_00968f001", "Abtriebsgrösse Torx", "tamaño de salida Torx" );
dc.importFeatureName ( "fi", "_00714f001", "Länge kurzer Schenkel", "Lyhyen varren pituus" );
dc.importFeatureName ( "fi", "_00048f001", "Aufnahmegröße sechskant", "Kiinnityksen koko, kuusiokanta" );
dc.importFeatureName ( "fi", "_00968f001", "Abtriebsgrösse Torx", "Kiinnityskoko Torx" );
dc.importFeatureName ( "it", "_00714f001", "Länge kurzer Schenkel", "Lato lungo corto" );
dc.importFeatureName ( "it", "_00048f001", "Aufnahmegröße sechskant", "Dimensione attacco esagonale" );
dc.importFeatureName ( "it", "_00968f001", "Abtriebsgrösse Torx", "Dimensione uscita Torx" );
dc.importFeatureName ( "nl", "AAA820f001", "Vierkant", "Vierkant" );
dc.importFeatureName ( "nl", "_00714f001", "Länge kurzer Schenkel", "Lengte van korte kant" );
dc.importFeatureName ( "nl", "_00011f001", "Artikelnummer", "Artikelnummer" );
dc.importFeatureName ( "nl", "_00048f001", "Aufnahmegröße sechskant", "Formaat zeskantopname" );
dc.importFeatureName ( "nl", "_00968f001", "Abtriebsgrösse Torx", "Aandrijfformaat Torx" );
dc.importFeatureName ( "no", "AAA820f001", "Vierkant", "Firkant" );
dc.importFeatureName ( "no", "_00714f001", "Länge kurzer Schenkel", "Lengde kort arm" );
dc.importFeatureName ( "no", "_00011f001", "Artikelnummer", "Artikkelnummer" );
dc.importFeatureName ( "no", "_00048f001", "Aufnahmegröße sechskant", "Festestørrelse sekskant" );
dc.importFeatureName ( "no", "_00968f001", "Abtriebsgrösse Torx", "Bitsdimensjoner Torx" );
dc.importFeatureName ( "pl", "_00714f001", "Länge kurzer Schenkel", "Długość krótkiego ramienia" );
dc.importFeatureName ( "pl", "_00083f001", "Durchmesser Uhr (inch)", "Średnica zegara (cale)" );
dc.importFeatureName ( "pl", "_00048f001", "Aufnahmegröße sechskant", "Rozmiar chwytu, sześciokąt" );
dc.importFeatureName ( "pl", "_00968f001", "Abtriebsgrösse Torx", "Rozmiar części roboczej, Torx" );
dc.importFeatureName ( "ru", "_00714f001", "Länge kurzer Schenkel", "Длина короткой части" );
dc.importFeatureName ( "ru", "_00048f001", "Aufnahmegröße sechskant", "Размер присоединительного шестигранника " );
dc.importFeatureName ( "ru", "_00968f001", "Abtriebsgrösse Torx", "Размер профиля Torx" );
dc.importFeatureName ( "jp", "_00080f001", "Länge Griff (inch)", "グリップ長（インチサイズ）" );
dc.importFeatureName ( "jp", "_00153f001", "Länge Griff G2 (inch)", "グリップ長G2（インチサイズ）" );
dc.importFeatureName ( "jp", "_00048f001", "Aufnahmegröße sechskant", "ボルスター" );
dc.importFeatureName ( "jp", "_00968f001", "Abtriebsgrösse Torx", "アウトプットトルクス" );
dc.importFeatureName ( "jp", "_00082f001", "Länge Rohr (inch)", "チューブ長（インチサイズ）" );
dc.importFeatureName ( "jp", "_00081f001", "Breite Rohr (inch)", "チューブ幅（インチサイズ）" );
dc.importFeatureName ( "jp", "_00152f001", "Höhe Ratschenkopf H2 (inch)", "ラチェット厚みH２（インチサイズ）" );
dc.importFeatureName ( "jp", "_00714f001", "Länge kurzer Schenkel", "ショートアーム長" );
dc.importFeatureName ( "jp", "_00079f001", "Durchmesser des Kopfes(inch)", "刃先径（インチサイズ）" );
dc.importFeatureName ( "jp", "_00083f001", "Durchmesser Uhr (inch)", "目盛りの直径（インチサイズ）" );
dc.importFeatureName ( "jp", "_AAA190f001", "Schlüsselweite inch", "レンチ寸法（インチサイズ）" );
dc.importFeatureName ( "jp", "AAA959f001", "Blade diameter", "軸径" );
dc.importFeatureName ( "jp", "AAB353f001", "Drive size (inches)", "差込寸法（インチサイズ）" );
dc.importFeatureName ( "jp", "_00716f001", "Gesamtlänge Klinge", "軸長" );
dc.importFeatureName ( "jp", "_00717f001", "Gesamtlänge Klinge in Zoll", "軸長（インチサイズ）" );
dc.importFeatureName ( "jp", "AAA962f001", "Operating hexagon wrench size", "スパナ寸法" );
dc.importFeatureName ( "jp", "_00014f001", "Subscale pre-set reading", "目盛りの読み取り" );
dc.importFeatureName ( "jp", "AAA968f001", "Tip size", "刃先寸法" );
dc.importFeatureName ( "jp", "AAA971f001", "Tip size (inches)", "刃先寸法（インチサイズ）" );
dc.importFeatureName ( "jp", "AAA970f001", "Tip size (metric)", "刃先寸法（ミリサイズ）" );
dc.importFeatureName ( "cn", "_00080f001", "Länge Griff (inch)", "手柄长度（英制）" );
dc.importFeatureName ( "cn", "_00153f001", "Länge Griff G2 (inch)", "手柄长度 G2（英制）" );
dc.importFeatureName ( "cn", "_00048f001", "Aufnahmegröße sechskant", "六角加力螺栓" );
dc.importFeatureName ( "cn", "_00968f001", "Abtriebsgrösse Torx", "梅花输出" );
dc.importFeatureName ( "cn", "_00082f001", "Länge Rohr (inch)", "管长（英制）" );
dc.importFeatureName ( "cn", "_00081f001", "Breite Rohr (inch)", "管宽（英制）" );
dc.importFeatureName ( "cn", "_00152f001", "Höhe Ratschenkopf H2 (inch)", "棘轮头厚度H2（英制）" );
dc.importFeatureName ( "cn", "_00714f001", "Länge kurzer Schenkel", "短柄长度" );
dc.importFeatureName ( "cn", "_00079f001", "Durchmesser des Kopfes(inch)", "刀头直径（英制）" );
dc.importFeatureName ( "cn", "_00083f001", "Durchmesser Uhr (inch)", "表径（英制）" );
dc.importFeatureName ( "cn", "_AAA190f001", "Schlüsselweite inch", "扳手尺寸（英制）" );
dc.importFeatureName ( "cn", "_00716f001", "Gesamtlänge Klinge", "刀杆长度" );
dc.importFeatureName ( "cn", "_00717f001", "Gesamtlänge Klinge in Zoll", "刀杆长度（英制）" );
dc.importFeatureName ( "fr", "_00714f001", "Länge kurzer Schenkel", "Longueur de la tige courte" );
dc.importFeatureName ( "fr", "_00048f001", "Aufnahmegröße sechskant", "Taille d’admission hexagonale" );
dc.importFeatureName ( "fr", "_00968f001", "Abtriebsgrösse Torx", "Taille de pointe Torx" );
dc.importFeatureName ( "en", "_00714f001", "Länge kurzer Schenkel", "Short arm length" );
dc.importFeatureName ( "en", "_00080f001", "Länge Griff (inch)", "Handle length  (inches)" );
dc.importFeatureName ( "en", "_00082f001", "Länge Rohr (inch)", "Pipe length (inches)" );
dc.importFeatureName ( "en", "_00081f001", "Breite Rohr (inch)", "Pipe width (inches)" );
dc.importFeatureName ( "en", "_00079f001", "Durchmesser des Kopfes(inch)", "Tip diameter (inches)" );
dc.importFeatureName ( "en", "_00083f001", "Durchmesser Uhr (inch)", "Watch diameter (inches)" );
dc.importFeatureName ( "en", "_AAA190f001", "Schlüsselweite inch", "Wrench size (inches)" );
dc.importFeatureName ( "en", "_00153f001", "Länge Griff G2 (inch)", "Handle length G2 (inches)" );
dc.importFeatureName ( "en", "_00152f001", "Höhe Ratschenkopf H2 (inch)", "Ratchet head height  H2 (inches)" );
dc.importFeatureName ( "en", "_00048f001", "Aufnahmegröße sechskant", "Mounting size hexagon" );
dc.importFeatureName ( "en", "_00968f001", "Abtriebsgrösse Torx", "Output Torx" );
dc.importFeatureName ( "ko", "_00080f001", "Länge Griff (inch)", "손잡이 길이(인치)" );
dc.importFeatureName ( "ko", "_00153f001", "Länge Griff G2 (inch)", "손잡이 길이 G2(인치)" );
dc.importFeatureName ( "ko", "_00048f001", "Aufnahmegröße sechskant", "육각 마운팅 사이즈" );
dc.importFeatureName ( "ko", "_00968f001", "Abtriebsgrösse Torx", "톡스 출력" );
dc.importFeatureName ( "ko", "_00082f001", "Länge Rohr (inch)", "파이프 길이(인치)" );
dc.importFeatureName ( "ko", "_00081f001", "Breite Rohr (inch)", "파이프 폭(인치)" );
dc.importFeatureName ( "ko", "_00152f001", "Höhe Ratschenkopf H2 (inch)", "라쳇 머리부 높이 H2(인치)" );
dc.importFeatureName ( "ko", "_00714f001", "Länge kurzer Schenkel", "짧은 축 길이" );
dc.importFeatureName ( "ko", "_00079f001", "Durchmesser des Kopfes(inch)", "팁 지름(인치)" );
dc.importFeatureName ( "ko", "_00083f001", "Durchmesser Uhr (inch)", "시계 지름(인치)" );
dc.importFeatureName ( "ko", "_AAA190f001", "Schlüsselweite inch", "렌치 사이즈(인치)" );
dc.importFeatureName ( "ko", "AAA968f001", "Abtriebsgröße", "팁 사이즈" );
dc.importFeatureName ( "ko", "AAA971f001", "Abtriebsgröße (in Zoll)", "팁 사이즈(인치)" );
dc.importFeatureName ( "ko", "AAA970f001", "Abtriebsgröße (metrisch)", "팁 사이즈(밀리)" );
dc.importFeatureName ( "ko", "_00047f001", "Abtriebsgröße Microstix", "마이크로스틱스 팁 사이즈" );
dc.importFeatureName ( "ko", "_00019f001", "Abtriebsgröße zweite Seite", "두번째 측면 팁 사이즈" );
dc.importFeatureName ( "ko", "AAB353f001", "Antriebsgröße (in Zoll)", "드라이브 사이즈(인치)" );
dc.importFeatureName ( "ko", "AAA816f001", "Antriebsvierkant vorn", "전면 정사각형 드라이브" );
dc.importFeatureName ( "ko", "AAA678f001", "Anzahl Teile", "부품 번호" );
dc.importFeatureName ( "ko", "AAA119f001", "Arbeitsbereich", "작업 공간" );
dc.importFeatureName ( "ko", "_00011f001", "Artikelnummer", "아이템 번호" );
dc.importFeatureName ( "ko", "AAC026f001", "Aufnahmegröße", "마운팅 사이즈" );
dc.importFeatureName ( "ko", "AAC436f001", "Aufnahmegröße Werkzeugseitig", "공구면 마운팅 사이즈" );
dc.importFeatureName ( "ko", "_00066f001", "Ausführung Aufnahme", "마운팅 버전" );
dc.importFeatureName ( "ko", "AAA353f001", "Außendurchmesser", "바깥 칫수" );
dc.importFeatureName ( "ko", "AAA801f001", "Außenmaß (b) Maul", "외부 칫수" );
dc.importFeatureName ( "ko", "AAA785f001", "Außenmaß (b1)", "외부 칫수(b1)" );
dc.importFeatureName ( "ko", "AAA785f001", "Außenmaß (b1)  - Ratschenringschlüssel", "외부 칫수(b1) - 라쳇렌치" );
dc.importFeatureName ( "ko", "AAA786f001", "Außenmaß (b2)", "외부 칫수(b2)" );
dc.importFeatureName ( "ko", "AAA811f001", "Außenmaß (d1)", "와부 칫수(d1)" );
dc.importFeatureName ( "ko", "AAA819f001", "Außenvierkant", "외부 정사각형" );
dc.importFeatureName ( "ko", "AAA040f001", "Breite", "폭" );
dc.importFeatureName ( "ko", "_00076f001", "Breite Rohr", "튜브 폭" );
dc.importFeatureName ( "ko", "AAA032f001", "Durchmesser", "지름" );
dc.importFeatureName ( "ko", "_00078f001", "Durchmesser Uhr", "시계 지름" );
dc.importFeatureName ( "ko", "_00074f001", "Durchmesser des Kopfes", "머리부 지름" );
dc.importFeatureName ( "ko", "_00033f001", "Durchmesser hinten (d2)", "후면 지름(d2)" );
dc.importFeatureName ( "ko", "_00020f001", "Einhandknarre Breite", "한손용 라쳇 폭" );
dc.importFeatureName ( "ko", "_00021f001", "Einhandknarre Breite (in Zoll)", "한손용 라쳇 폭(인치)" );
dc.importFeatureName ( "ko", "_00022f001", "Einhandknarre Höhe", "한손용 라쳇 높이" );
dc.importFeatureName ( "ko", "_00023f001", "Einhandknarre Höhe (in Zoll)", "한손용 라쳇 높이(인치)" );
dc.importFeatureName ( "ko", "_AAA972f001", "Farbe", "색상" );
dc.importFeatureName ( "ko", "AAA081f001", "Gesamtlänge", "전체 길이" );
dc.importFeatureName ( "ko", "_00716f001", "Gesamtlänge Klinge", "블레이드 길이" );
dc.importFeatureName ( "ko", "_00717f001", "Gesamtlänge Klinge in Zoll", "블레이드 인치 길이" );
dc.importFeatureName ( "ko", "_00154f001", "Gesamtlänge Kopf", "머리부 전체 길이" );
dc.importFeatureName ( "ko", "_00804f001", "Gewicht des Produktes", "제품 중량" );
dc.importFeatureName ( "ko", "AAA282f001", "Gewindelänge", "나사선 길이" );
dc.importFeatureName ( "ko", "AAA686f001", "Griffdurchmesser", "손잡이 지름" );
dc.importFeatureName ( "ko", "AAA972f001", "Grifffarbe", "손잡이 색상" );
dc.importFeatureName ( "ko", "AAA776f001", "Grifflänge", "손잡이 길이" );
dc.importFeatureName ( "ko", "AAC081f001", "Größe", "사이즈" );
dc.importFeatureName ( "ko", "_00003f001", "Hauptskala Messbereich", "주 눈금자 측정범위" );
dc.importFeatureName ( "ko", "AAA418f001", "Hauptskalenteilung", "주 눈금자 눈금" );
dc.importFeatureName ( "ko", "AAA031f001", "Höhe", "높이" );
dc.importFeatureName ( "ko", "_00032f001", "Höhe Abtrieb", "팁 높이" );
dc.importFeatureName ( "ko", "_00006f001", "Höhe Drehmomentschlüssel", "토크렌치 높이" );
dc.importFeatureName ( "ko", "_00007f001", "Höhe Drehmomentschlüssel (in Zoll)", "토크렌치 높이(인치)" );
dc.importFeatureName ( "ko", "_00150f001", "Höhe Ratschenkopf H2", "라쳇 머리부 높이 H2" );
dc.importFeatureName ( "ko", "_00031f001", "Innenabstand", "내부 칫수" );
dc.importFeatureName ( "ko", "AAA953f001", "Klingenbreite", "블레이드 폭" );
dc.importFeatureName ( "ko", "AAA954f001", "Klingenbreite (in Zoll)", "블레이드 폭(인치)" );
dc.importFeatureName ( "ko", "_00017f001", "Klingenbreite zweite Seite", "두번째 측면 블레이드 폭" );
dc.importFeatureName ( "ko", "AAA955f001", "Klingendicke", "블레이드 두께" );
dc.importFeatureName ( "ko", "_00030f001", "Klingendicke zweite Seite", "두번째 측면 블레이드 두께" );
dc.importFeatureName ( "ko", "AAA959f001", "Klingendurchmesser", "블레이드 지름" );
dc.importFeatureName ( "ko", "_00068f001", "Klingendurchmesser ohne Isolationsschicht", "절연층을 제외한 블레이드 지름" );
dc.importFeatureName ( "ko", "AAC132f001", "Klingenlänge", "블레이드 길이" );
dc.importFeatureName ( "ko", "AAA957f001", "Klingenlänge (in Zoll)", "블레이드 길이(인치)" );
dc.importFeatureName ( "ko", "_00001f001", "Klingenlänge kurz", "짧은 블레이드 길이" );
dc.importFeatureName ( "ko", "_00002f001", "Klingenlänge kurz (in Zoll)", "짧은 블레이드 길이(인치)" );
dc.importFeatureName ( "ko", "_00015f001", "Klingenlänge überdreht", "오버토크 블레이드 길이" );
dc.importFeatureName ( "ko", "_00016f001", "Klingenlänge überdreht (in Zoll)", "오버토크 블레이드 길이(인치)" );
dc.importFeatureName ( "ko", "AAA802f001", "Kopfdicke", "머리부 두께" );
dc.importFeatureName ( "ko", "AAA788f001", "Kopfdicke bis (a2)", "(a2) 까지 머리부 두께" );
dc.importFeatureName ( "ko", "AAA787f001", "Kopfdicke von (a1)", "(a1) 부터의 머리부 두께" );
dc.importFeatureName ( "ko", "AAA178f001", "Kopfdurchmesser", "머리부 지름" );
dc.importFeatureName ( "ko", "AAA290f001", "Kopflänge", "머리부 길이" );
dc.importFeatureName ( "ko", "AAA002f001", "Länge", "길이" );
dc.importFeatureName ( "ko", "_00010f001", "Länge (in Zoll)", "길이(인치)" );
dc.importFeatureName ( "ko", "AAA591f001", "Länge Drehmomentschlüssel", "토크렌치 길이" );
dc.importFeatureName ( "ko", "_00005f001", "Länge Drehmomentschlüssel (in Zoll)", "토크렌치 길이(인치)" );
dc.importFeatureName ( "ko", "_00075f001", "Länge Griff", "손잡이 길이" );
dc.importFeatureName ( "ko", "_00151f001", "Länge Griff G2", "손잡이 길이 G2" );
dc.importFeatureName ( "ko", "_00077f001", "Länge Rohr", "파이프 길이" );
dc.importFeatureName ( "ko", "_00042f001", "Maulschlüssel Joker", "조커렌치" );
dc.importFeatureName ( "ko", "AAA794f001", "Max. Kopfhöhe - Ratschenringschlüssel", "최대 머리부 높이 - 라쳇렌치" );
dc.importFeatureName ( "ko", "_00004f001", "Nebenskala Messbereich", "보조 척도 측정범위" );
dc.importFeatureName ( "ko", "AAA420f001", "Nebenskalenteilung", "보조 척도 눈금" );
dc.importFeatureName ( "ko", "AAA294f001", "Nenndurchmesser", "지정된 지름" );
dc.importFeatureName ( "ko", "_00809f001", "Preis", "가격" );
dc.importFeatureName ( "ko", "AAA224f001", "Schaftlänge", "샤프트 길이" );
dc.importFeatureName ( "ko", "AAA198f001", "Schlüsselweite", "렌치 사이즈" );
dc.importFeatureName ( "ko", "AAA960f001", "Schlüsselweite (Sechskantklinge)", "렌치 사이즈(육각 블레이드)" );
dc.importFeatureName ( "ko", "_00028f001", "Schlüsselweite (in Zoll)", "렌치 사이즈(인치)" );
dc.importFeatureName ( "ko", "AAA781f001", "Schlüsselweite 1", "렌치 사이즈 1" );
dc.importFeatureName ( "ko", "AAA782f001", "Schlüsselweite 2", "렌치 사이즈 2" );
dc.importFeatureName ( "ko", "AAA962f001", "Schlüsselweite Betätigungssechskant", "운영 육각 렌치 사이즈" );
dc.importFeatureName ( "ko", "AAC524f001", "Spirallänge", "나선형 길이" );
dc.importFeatureName ( "ko", "_00808f001", "Sprache", "언어" );
dc.importFeatureName ( "ko", "_00089f001", "Tiefe", "깊이" );
dc.importFeatureName ( "ko", "AAA820f001", "Vierkant", "정사각형" );
dc.importFeatureName ( "ko", "_00012f001", "Voreingestellter Messwert Hauptskala", "주 눈금자 선고정 판독" );
dc.importFeatureName ( "ko", "_00014f001", "Voreingestellter Messwert Nebenskala", "보조 눈금자 선고정 판독" );
dc.importFeatureName ( "ko", "AAC506f001", "Zylinderschaftdurchmesser", "원통형 샤프트 지름" );
%>
</body>
</html>

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

dc.importFeatureName ( "tr", "AAA965f001", "Abrollschutz", "Yurvarlanma Koruması" );
dc.importFeatureName ( "tr", "_00116f001", "Abrollschutz", "Yurvarlanma Koruması" );
dc.importFeatureName ( "tr", "_00968f001", "Abtriebsgrösse Torx", "Torx Uç Boyutu " );
dc.importFeatureName ( "tr", "AAA968f001", "Abtriebsgröße", "Uç Boyutu" );
dc.importFeatureName ( "tr", "AAA971f001", "Abtriebsgröße (in Zoll)", "Uç Boyutu (inç)" );
dc.importFeatureName ( "tr", "AAA970f001", "Abtriebsgröße (metrisch)", "Uç Boyutu (metrik)" );
dc.importFeatureName ( "tr", "_00047f001", "Abtriebsgröße Microstix", "Microstix Uç Boyutu" );
dc.importFeatureName ( "tr", "_00019f001", "Abtriebsgröße zweite Seite", "İkinci Taraf Uç Boyutu" );
dc.importFeatureName ( "tr", "_00413f001", "Adjustable 1-3 S", "Ayarlanabilir 1-3 P" );
dc.importFeatureName ( "tr", "_00414f001", "Adjustable 2-6 S", "Ayarlanabilir 2-6 P" );
dc.importFeatureName ( "tr", "_00301f001", "Anti-Slip", "Kaymaz" );
dc.importFeatureName ( "tr", "AAB353f001", "Antriebsgröße (in Zoll)", "Sürücü Ölçüsü (inç)" );
dc.importFeatureName ( "tr", "AAA816f001", "Antriebsvierkant vorn", "Ön Kare Sürücü" );
dc.importFeatureName ( "tr", "AAA817f001", "Antriebsvierkant_bis", "Kare Sürücü'ye Kadar" );
dc.importFeatureName ( "tr", "AAA678f001", "Anzahl Teile", "Parça Sayısı" );
dc.importFeatureName ( "tr", "AAA119f001", "Arbeitsbereich", "Çalışma Alanı" );
dc.importFeatureName ( "tr", "_00011f001", "Artikelnummer", "Ürün Numarası" );
dc.importFeatureName ( "tr", "_00040f001", "Aufnahme für Verlängerung", "Tutuculu Uzatma" );
dc.importFeatureName ( "tr", "AAC026f001", "Aufnahmegröße", "Tutucu Ölçüsü" );
dc.importFeatureName ( "tr", "AAC436f001", "Aufnahmegröße Werkzeugseitig", "Alet Tarafı Tutucu Boyutu" );
dc.importFeatureName ( "tr", "_00048f001", "Aufnahmegröße sechskant", "Altıgen Tutucu Ölçüsü" );
dc.importFeatureName ( "tr", "_00066f001", "Ausführung Aufnahme", "Tutucu Versionu" );
dc.importFeatureName ( "tr", "AAA353f001", "Außendurchmesser", "Dış Çap" );
dc.importFeatureName ( "tr", "AAA801f001", "Außenmaß (b) Maul", "Dış Ölçü (b) Ağız" );
dc.importFeatureName ( "tr", "AAA785f001", "Außenmaß (b1)", "Dış Ölçü (b1)" );
dc.importFeatureName ( "tr", "AAA785f001", "Außenmaß (b1)  - Ratschenringschlüssel", "Dış Ölçü (b1) - Yıldız Cırcırlı Anahtar" );
dc.importFeatureName ( "tr", "AAA786f001", "Außenmaß (b2)", "Dış Ölçü (b2)" );
dc.importFeatureName ( "tr", "AAA811f001", "Außenmaß (d1)", "Dış Ölçü (d1)" );
dc.importFeatureName ( "tr", "AAA819f001", "Außenvierkant", "Dıştan Kare" );
dc.importFeatureName ( "tr", "_00135f001", "Ballgrip", "Ballgrip/Yumru Sap" );
dc.importFeatureName ( "tr", "AAA831f001", "Betätigungssechskant", "Altıgen Soket" );
dc.importFeatureName ( "tr", "_00008f001", "BiTorsion", "Bitorsiyon" );
dc.importFeatureName ( "tr", "_00701f001", "BiTorsion", "Bitorsiyon" );
dc.importFeatureName ( "tr", "_00224f001", "Bicycle Set", "Bisiklet Seti" );
dc.importFeatureName ( "tr", "_00127f002", "Bit-Handhalter mit Rapidaptor-Schnellwechselfutter", "Rapidaptor Hızlı Uç Değiştirme Mandrenli Bits Tutuculu Sap" );
dc.importFeatureName ( "tr", "_00061f001", "Bithalter mit Rapidaptor-Schnellwechselfutter", "Rapidaptor Hızlı Uç Değiştirme Mandrenli Bits Tutucu" );
dc.importFeatureName ( "tr", "_00207f001", "Bithalter mit Rapidaptor-Schnellwechselfutter", "Rapidaptor Hızlı Uç Değiştirme Mandrenli Bits Tutucu" );
dc.importFeatureName ( "tr", "_00107f001", "Black Point Spitze", "Blackpoint Uç" );
dc.importFeatureName ( "tr", "_00106f001", "Black Point Spitze, rund", "Blackpoint Uç, Yuvarlak" );
dc.importFeatureName ( "tr", "AAA040f001", "Breite", "Genişlik" );
dc.importFeatureName ( "tr", "_AA040f001", "Breite", "Genişlik" );
dc.importFeatureName ( "tr", "_00076f001", "Breite Rohr", "Boru Genişliği" );
dc.importFeatureName ( "tr", "_00081f001", "Breite Rohr (inch)", "Boru Genişliği (inç)" );
dc.importFeatureName ( "tr", "_00901f001", "Customized Bit-Box", "Özelleştirilmiş Bit-Box" );
dc.importFeatureName ( "tr", "_00904f001", "Customized Cardboard Box", "Özelleştirilmiş Cardboard Box" );
dc.importFeatureName ( "tr", "_00903f001", "Customized Tool", "Özelleştirilmiş Aletler" );
dc.importFeatureName ( "tr", "AAA590f001", "Daten protokollierbar", "Veriler Kaydedilebilir" );
dc.importFeatureName ( "tr", "_00026f001", "Diamantbeschichtet", "Elmas Kaplı" );
dc.importFeatureName ( "tr", "_00702f001", "Diamantbeschichtet", "Elmas Kaplı" );
dc.importFeatureName ( "tr", "_00300f001", "Doppelmaul Joker", "Çift Ağızlı Joker" );
dc.importFeatureName ( "tr", "AAA832f001", "Durchgehende Klinge", "Yekpare Bıçak" );
dc.importFeatureName ( "tr", "_00118f001", "Durchgehende Klinge", "Yekpare Bıçak" );
dc.importFeatureName ( "tr", "AAA032f001", "Durchmesser", "Çap" );
dc.importFeatureName ( "tr", "_00033f002", "Durchmesser", "Çap" );
dc.importFeatureName ( "tr", "_00078f001", "Durchmesser Uhr", "Saat Çapı" );
dc.importFeatureName ( "tr", "_00083f001", "Durchmesser Uhr (inch)", "Saat Çapı (inç)" );
dc.importFeatureName ( "tr", "_00074f001", "Durchmesser des Kopfes", "Kafa Çapı" );
dc.importFeatureName ( "tr", "_00079f001", "Durchmesser des Kopfes(inch)", "Kafa Çapı (inç)" );
dc.importFeatureName ( "tr", "_00033f001", "Durchmesser hinten (d2)", "Arka Çap (d2)" );
dc.importFeatureName ( "tr", "_00037f001", "ESD", "ESD" );
dc.importFeatureName ( "tr", "_00037f001", "ESD Werkzeuge", "ESD Aletler" );
dc.importFeatureName ( "tr", "_00119f001", "ESD Werkzeuge", "ESD Aletler" );
dc.importFeatureName ( "tr", "_00027f001", "Edelstahl", "Paslanmaz Çelik" );
dc.importFeatureName ( "tr", "_00020f001", "Einhandknarre Breite", "Tek Elle Kullanılan Cırcır Genişliği" );
dc.importFeatureName ( "tr", "_00021f001", "Einhandknarre Breite (in Zoll)", "Tek Elle Kullanılan Cırcır Genişliği (inç)" );
dc.importFeatureName ( "tr", "_00022f001", "Einhandknarre Höhe", "Tek Elle Kullanılan Cırcır Yüksekliği" );
dc.importFeatureName ( "tr", "_00023f001", "Einhandknarre Höhe (in Zoll)", "Tek Elle Kullanılan Cırcır Yüksekliği (inç)" );
dc.importFeatureName ( "tr", "_00401f001", "Einstellbar", "Ayarlanabilir" );
dc.importFeatureName ( "tr", "AAB136f001", "Einsätze auswechselbar", "Değiştirilebilir Uçlar" );
dc.importFeatureName ( "tr", "_00302f001", "Endanschlag", "Entegre Limit-Stop" );
dc.importFeatureName ( "tr", "_00200f001", "Extra slim", "Ekstra İnce" );
dc.importFeatureName ( "tr", "_00503f001", "Extra-short", "Ekstra-Kısa" );
dc.importFeatureName ( "tr", "_AAA972f001", "Farbe", "Renk" );
dc.importFeatureName ( "tr", "_00038f001", "Flexible Lock Funktion", "Esnek Kilit Fonksiyonu" );
dc.importFeatureName ( "tr", "_00203f001", "Frei schwenkbarer Knarrenkopf", "Açılı/Hareketli Cırcır Kafası" );
dc.importFeatureName ( "tr", "_00206f001", "Freilaufhülse", "Dönerçark Manşon" );
dc.importFeatureName ( "tr", "_00226f001", "Freilaufhülse Nuss", "Dönerçark Manşonlu Lokma" );
dc.importFeatureName ( "tr", "_00115f001", "GS - Geprüfte Sicherheit", "GS-Onaylı Güvenlik" );
dc.importFeatureName ( "tr", "_00222f001", "GS - Geprüfte Sicherheit ohne Kaltschlagfestigkeitsprüfung", "GS-Soğuk Darbe Dayanım Testi Olmaksızın Onaylı Güvenlik" );
dc.importFeatureName ( "tr", "_00801f001", "Geeignet für Wera 2go", "Wera 2Go için Uygun" );
dc.importFeatureName ( "tr", "AAA814f001", "Gelenk", "Mafsal" );
dc.importFeatureName ( "tr", "AAA081f001", "Gesamtlänge", "Toplam Uzunluk" );
dc.importFeatureName ( "tr", "_00155f001", "Gesamtlänge", "Toplam Uzunluk" );
dc.importFeatureName ( "tr", "_AA081f001", "Gesamtlänge", "Toplam Uzunluk" );
dc.importFeatureName ( "tr", "_00716f001", "Gesamtlänge Klinge", "Toplam Bıçak Uzunluğu" );
dc.importFeatureName ( "tr", "_00717f001", "Gesamtlänge Klinge in Zoll", "Toplam Bıçak Uzunluğu (inç)" );
dc.importFeatureName ( "tr", "_00154f001", "Gesamtlänge Kopf", "Toplam Kafa Uzunluğu" );
dc.importFeatureName ( "tr", "_00204f001", "Gesenkgeschmiedete Vollstahlausführung", "Dövme Masif Çelik Tasarım" );
dc.importFeatureName ( "tr", "AAA003f001", "Gewicht", "Ürün Ağırlığı" );
dc.importFeatureName ( "tr", "_00804f001", "Gewicht des Produktes", "Ürünün Ağırlığı" );
dc.importFeatureName ( "tr", "AAA282f001", "Gewindelänge", "Diş Uzunluğu" );
dc.importFeatureName ( "tr", "AAA686f001", "Griffdurchmesser", "Sap Çapı" );
dc.importFeatureName ( "tr", "AAA972f001", "Grifffarbe", "Sap Rengi" );
dc.importFeatureName ( "tr", "AAA776f001", "Grifflänge", "Sap Uzunluğu" );
dc.importFeatureName ( "tr", "AAA022f001", "Griffüberzug", "Sap Kapağı" );
dc.importFeatureName ( "tr", "AAC081f001", "Größe", "Boyut" );
dc.importFeatureName ( "tr", "_00029f001", "HF - Haltefunktion", "Hf - Tutma Fonksiyonu" );
dc.importFeatureName ( "tr", "_00121f001", "HF - Mit Haltefunktion", "Hf - Tutma Fonksiyonlu" );
dc.importFeatureName ( "tr", "_00128f002", "Halfmoon HIOS Antrieb", "Halfmoon/Yarımay HIOS Sürücü" );
dc.importFeatureName ( "tr", "_00303f001", "Haltefunktion", "Tutma Fonksiyonu" );
dc.importFeatureName ( "tr", "_00219f001", "Haltefunktion für Aussensechskantschrauben", "Altıgen Soketli Vidalar için Tutma Tonksiyonu" );
dc.importFeatureName ( "tr", "_00039f001", "Hammerfunktion", "Çekiç Fonksiyonu" );
dc.importFeatureName ( "tr", "_00215f001", "Hammerfunktion", "Çekiç Fonksiyonu" );
dc.importFeatureName ( "tr", "_00601f001", "Hand- und Maschinenverschraubung", "Manüel ve Makineyle Vidalama" );
dc.importFeatureName ( "tr", "_00003f001", "Hauptskala Messbereich", "Ana Ölçek Ölçüm Aralığı" );
dc.importFeatureName ( "tr", "_00003f003", "Hauptskala Messbereich Max", "Ana Ölçek Ölçüm Aralığı Maks" );
dc.importFeatureName ( "tr", "_00003f002", "Hauptskala Messbereich Min", "Ana Ölçek Ölçüm Aralığı Min" );
dc.importFeatureName ( "tr", "AAA418f001", "Hauptskalenteilung", "Ana Ölçek Bölümü" );
dc.importFeatureName ( "tr", "_00009f001", "Hex-Plus", "Hex-Plus" );
dc.importFeatureName ( "tr", "_00117f001", "Hex-Plus", "Hex-Plus" );
dc.importFeatureName ( "tr", "AAA031f001", "Höhe", "Yükseklik" );
dc.importFeatureName ( "tr", "_00033f003", "Höhe", "Yükseklik" );
dc.importFeatureName ( "tr", "_AA031f001", "Höhe", "Yükseklik" );
dc.importFeatureName ( "tr", "_00032f001", "Höhe Abtrieb", "Sürücü Yüksekliği" );
dc.importFeatureName ( "tr", "_00006f001", "Höhe Drehmomentschlüssel", "Tork Anahtarı Yüksekliği" );
dc.importFeatureName ( "tr", "_00007f001", "Höhe Drehmomentschlüssel (in Zoll)", "Tork Anahtarı Yüksekliği (inç)" );
dc.importFeatureName ( "tr", "_00150f001", "Höhe Ratschenkopf H2", "Cırcır Kafası Yüksekliği H2" );
dc.importFeatureName ( "tr", "_00152f001", "Höhe Ratschenkopf H2 (inch)", "Cırcır Kafası Yüksekliği H2 (inç)" );
dc.importFeatureName ( "tr", "_00070f001", "Impaktor Diamant", "Impactor Diamond/Elmas" );
dc.importFeatureName ( "tr", "_00031f001", "Innenabstand", "İç Mesafe" );
dc.importFeatureName ( "tr", "_00108f001", "Innenvierkantaufnahme", "İçten Kare Tutucu" );
dc.importFeatureName ( "tr", "_00310f001", "Joker Pedal Extra slim", "Joker Pedal Ekstra İnce" );
dc.importFeatureName ( "tr", "AAA832f001", "Klinge durchgehend", "Yekpare Bıçak" );
dc.importFeatureName ( "tr", "AAA953f001", "Klingenbreite", "Bıçak Genişliği" );
dc.importFeatureName ( "tr", "AAA954f001", "Klingenbreite (in Zoll)", "Bıçak Genişliği (inç)" );
dc.importFeatureName ( "tr", "_00017f001", "Klingenbreite zweite Seite", "Bıçak Genişliği İkinci Taraf" );
dc.importFeatureName ( "tr", "AAA955f001", "Klingendicke", "Bıçak Kalınlığı" );
dc.importFeatureName ( "tr", "_00030f001", "Klingendicke zweite Seite", "Bıçak Kalınlığı İkinci Taraf" );
dc.importFeatureName ( "tr", "AAA959f001", "Klingendurchmesser", "Bıçak Çapı" );
dc.importFeatureName ( "tr", "_00068f001", "Klingendurchmesser ohne Isolationsschicht", "Yalıtımsız Bıçak Çapı" );
dc.importFeatureName ( "tr", "AAC132f001", "Klingenlänge", "Bıçak Uzunluğu" );
dc.importFeatureName ( "tr", "AAA957f001", "Klingenlänge (in Zoll)", "Bıçak Uzunluğu (inç)" );
dc.importFeatureName ( "tr", "_00001f001", "Klingenlänge kurz", "Bıçak Uzunluğu Kısa" );
dc.importFeatureName ( "tr", "_00002f001", "Klingenlänge kurz (in Zoll)", "Bıçak Uzunluğu Kısa (inç)" );
dc.importFeatureName ( "tr", "_00015f001", "Klingenlänge überdreht", "İnceltilmiş Bıçak Uzunluğu" );
dc.importFeatureName ( "tr", "_00016f001", "Klingenlänge überdreht (in Zoll)", "İnceltilmiş Bıçak Uzunluğu (inç)" );
dc.importFeatureName ( "tr", "_00228f001", "Kombinationsantrieb", "Kombine Sürücü" );
dc.importFeatureName ( "tr", "AAA802f001", "Kopfdicke", "Kafa Kalınlığı" );
dc.importFeatureName ( "tr", "_00072f001", "Kopfdicke", "Kafa Kalınlığı" );
dc.importFeatureName ( "tr", "AAA788f001", "Kopfdicke bis (a2)", "(a2)'ye Kadar Kafa Kalınlığı" );
dc.importFeatureName ( "tr", "AAA787f001", "Kopfdicke von (a1)", "d1'den İtibaren Kafa Kalınlığı" );
dc.importFeatureName ( "tr", "AAA178f001", "Kopfdurchmesser", "Kafa Çapı" );
dc.importFeatureName ( "tr", "_00073f001", "Kopfdurchmesser", "Kafa Çapı" );
dc.importFeatureName ( "tr", "AAA290f001", "Kopflänge", "Kafa Uzunluğu" );
dc.importFeatureName ( "tr", "_00100f001", "Kraftform Griff", "Kraftform Sap" );
dc.importFeatureName ( "tr", "_00124f001", "Kraftform Micro Kraftzone", "Kraftform Mikro Kraftzone" );
dc.importFeatureName ( "tr", "_00125f001", "Kraftform Micro Präzisionzone", "Kraftform Mikro Hassas Bölge" );
dc.importFeatureName ( "tr", "_00122f001", "Kraftform Micro Schnelldrehzone", "Kraftform Mikro Hızlı Dönüş Bölgesi" );
dc.importFeatureName ( "tr", "_00123f001", "Kraftform Micro drehbare Kappe", "Kraftform Mikro Döner Kapak" );
dc.importFeatureName ( "tr", "_00604f001", "Kraftform Turbo", "Kraftform Turbo" );
dc.importFeatureName ( "tr", "_00110f001", "Kugelkopf", "Topbaş" );
dc.importFeatureName ( "tr", "_00063f001", "Kupfer-Beryllium-Hülse", "Bakır-Berilyum Kovan" );
dc.importFeatureName ( "tr", "_00025f001", "Lasertip", "Lasertip" );
dc.importFeatureName ( "tr", "_00114f001", "Lasertip", "Lasertip" );
dc.importFeatureName ( "tr", "_00126f002", "Lederschlagkappe", "Deri Darbe Başlığı" );
dc.importFeatureName ( "tr", "AAA002f001", "Länge", "Uzunluk" );
dc.importFeatureName ( "tr", "_00010f001", "Länge (in Zoll)", "Uzunluk (inç)" );
dc.importFeatureName ( "tr", "AAA591f001", "Länge Drehmomentschlüssel", "Tork Anahtarı Uzunluğu" );
dc.importFeatureName ( "tr", "_00005f001", "Länge Drehmomentschlüssel (in Zoll)", "Tork Anahtarı Uzunluğu (inç)" );
dc.importFeatureName ( "tr", "_00075f001", "Länge Griff", "Sap Uzunluğu" );
dc.importFeatureName ( "tr", "_00080f001", "Länge Griff (inch)", "Sap Uzunluğu (inç)" );
dc.importFeatureName ( "tr", "_00151f001", "Länge Griff G2", "Sap Uzunluğu G2" );
dc.importFeatureName ( "tr", "_00153f001", "Länge Griff G2 (inch)", "Sap Uzunluğu G2 (inç)" );
dc.importFeatureName ( "tr", "_00077f001", "Länge Rohr", "Boru Uzunluğu" );
dc.importFeatureName ( "tr", "_00082f001", "Länge Rohr (inch)", "Boru Uzunluğu (inç)" );
dc.importFeatureName ( "tr", "_00714f001", "Länge kurzer Schenkel", "Kısa Kol Uzunluğu" );
dc.importFeatureName ( "tr", "_00137f001", "Magnetic Rail", "Manyetik Şerit" );
dc.importFeatureName ( "tr", "_00138f001", "Magnetic Socket Rail", "Manyetik Lokma Şeriti" );
dc.importFeatureName ( "tr", "_00041f001", "Magnetisch", "Manyetik" );
dc.importFeatureName ( "tr", "_00710f001", "Magnetisch", "Manyetik" );
dc.importFeatureName ( "tr", "_00103f001", "Magnetisiergerät", "Mıknatıslayıcı" );
dc.importFeatureName ( "tr", "_00506f001", "Magnetisiergerät", "Mıknatıslayıcı" );
dc.importFeatureName ( "tr", "_00713f001", "Magnetisierter Bit", "Mıknatıslı Bits" );
dc.importFeatureName ( "tr", "_00042f001", "Maulschlüssel Joker", "Joker Anahtar" );
dc.importFeatureName ( "tr", "AAA794f001", "Max. Kopfhöhe - Ratschenringschlüssel", "Maks. Baş Yüksekliği - Cırcırlı Yıldız Anahtar" );
dc.importFeatureName ( "tr", "_01011f001", "Maximales Lösemoment", "Maksimum Gevşetme Torku" );
dc.importFeatureName ( "tr", "_00205f001", "Mit Durchsteckvierkant", "İtmeli Kare Sürücülü" );
dc.importFeatureName ( "tr", "_00220f001", "Mit Haltefunktion für Innen-TORX®-Schrauben", "Torx® Soketli Vidalar için Tutma Fonksiyonlu" );
dc.importFeatureName ( "tr", "_00221f001", "Mit Haltefunktion für Innensechskant-Schrauben", "Altıgen Soketli Vidalar için Tutma Fonksiyonlu" );
dc.importFeatureName ( "tr", "_00102f001", "Mit Hohlschaft", "İçi Boş Şaftlı" );
dc.importFeatureName ( "tr", "_00705f001", "Mit Ringmagnet", "Halka Mıknatıslı" );
dc.importFeatureName ( "tr", "_00065f001", "Mit Schnellwechselfutter", "Hızlı Değiştirme Mandrenli" );
dc.importFeatureName ( "tr", "_00109f001", "Mit Sechskantklinge", "Altıgen Bıçaklı" );
dc.importFeatureName ( "tr", "_00062f001", "Mit Sprengring", "Segmanlı" );
dc.importFeatureName ( "tr", "_00130f002", "Mit Vierkantklinge", "Kare Bıçaklı" );
dc.importFeatureName ( "tr", "_00111f001", "Mit flexiblem Schaft", "Esnek Şaftlı" );
dc.importFeatureName ( "tr", "_00105f001", "Mit reduziertem Klingen- und Griffdurchmesser", "İnceltilmiş Bıçak ve Sap Çaplı" );
dc.importFeatureName ( "tr", "_00104f001", "Mit reduziertem Klingendurchmesser und integrierter Schutzisolation", "İnceltilmiş Bıçak Çapı ve Entegre Koruyucu İzolasyonlu" );
dc.importFeatureName ( "tr", "_00802f001", "Mit reduziertem Schaftdurchmesser", "İnceltilmiş Şaft Çaplı" );
dc.importFeatureName ( "tr", "_00004f001", "Nebenskala Messbereich", "İkincil Ayar Ölçüm Aralığı" );
dc.importFeatureName ( "tr", "_00004f003", "Nebenskala Messbereich Max", "İkincil Ayar Ölçüm Aralığı Maks" );
dc.importFeatureName ( "tr", "_00004f002", "Nebenskala Messbereich Min", "İkincil Ayar Ölçüm Aralığı Min" );
dc.importFeatureName ( "tr", "AAA420f001", "Nebenskalenteilung", "İkincil Ölçüm Bölümü" );
dc.importFeatureName ( "tr", "AAA294f001", "Nenndurchmesser", "Nominal/Brüt Çap" );
dc.importFeatureName ( "tr", "_00223f001", "Nussfixierfunktion", "Lokma Kilitleme Fonksiyonu" );
dc.importFeatureName ( "tr", "_01013f001", "Personalized Tool", "Kişiselleştirilmiş Alet" );
dc.importFeatureName ( "tr", "_00809f001", "Preis", "Fiyat" );
dc.importFeatureName ( "tr", "_00308f001", "Profilschonend", "Profil Hassasiyetli" );
dc.importFeatureName ( "tr", "AAA414f001", "Prüfzertifikat gemäß ISO 6789", "ISO 6789'a Uygun Test Sertifikası" );
dc.importFeatureName ( "tr", "_00225f001", "Radmutternschutz", "Jant Bijonu Koruması" );
dc.importFeatureName ( "tr", "_00061f001", "Rapidaptor", "Rapidaptor" );
dc.importFeatureName ( "tr", "_00402f001", "Rapidaptor Torque", "Rapidaptor Tork" );
dc.importFeatureName ( "tr", "_00064f001", "Rapidaptor Universalhalter mit Ringmagnet", "Rapidaptor Üniversal Tutucu, Halka Mıknatıslı" );
dc.importFeatureName ( "tr", "_00309f001", "Ratschenfunktion 6004", "Cırcır Fonksiyonlu 6004" );
dc.importFeatureName ( "tr", "AAA668f001", "Rohrdurchmesser", "Boru Çapı" );
dc.importFeatureName ( "tr", "_00706f001", "Rostfrei", "Paslanmaz" );
dc.importFeatureName ( "tr", "_00134f002", "Rundklinge mit Sechskantschlüsselhilfe", "Altıgen Anahtar Destekli Yuvarlak Bıçak" );
dc.importFeatureName ( "tr", "_00304f001", "Rückholwinkel", "Geri Dönüş Açısı" );
dc.importFeatureName ( "tr", "_00306f001", "Rückholwinkel 15°", "Geri Dönüş Açısı 15°" );
dc.importFeatureName ( "tr", "_00213f001", "Rückholwinkel 4,5°", "Geri Dönüş Açısı 4,5°" );
dc.importFeatureName ( "tr", "_00201f001", "Rückholwinkel 4,7°", "Geri Dönüş Açısı 4,7°" );
dc.importFeatureName ( "tr", "_00202f001", "Rückholwinkel 5°", "Geri Dönüş Açısı 5°" );
dc.importFeatureName ( "tr", "_00214f001", "Rückholwinkel 6°", "Geri Dönüş Açısı 6°" );
dc.importFeatureName ( "tr", "_00136f001", "Rückholwinkel 9°", "Geri Dönüş Açısı 9°" );
dc.importFeatureName ( "tr", "AAB134f001", "Rückschlagsfrei", "Geri Tepmesiz" );
dc.importFeatureName ( "tr", "_00129f002", "SB-fähig", "SB-Özelikli" );
dc.importFeatureName ( "tr", "_00044f001", "SDS Plus", "SDS Plus" );
dc.importFeatureName ( "tr", "_00708f001", "SDS Plus", "SDS Plus" );
dc.importFeatureName ( "tr", "_00412f001", "Safe-Torque Nussverriegelung", "Safe-Torque Lokma Kilitleme" );
dc.importFeatureName ( "tr", "_00409f001", "Safe-Torque Rechts/Links Umschalten", "Safe-Torque Sağ/Sol Yön Değiştirme" );
dc.importFeatureName ( "tr", "AAA224f001", "Schaftlänge", "Şaft Uzunluğu" );
dc.importFeatureName ( "tr", "_00113f001", "Schlagkappe", "Darbe Kapağı" );
dc.importFeatureName ( "tr", "_00071f001", "Schlagschrauberfest", "Darbeli Tornavida" );
dc.importFeatureName ( "tr", "_00703f001", "Schlagschrauberfest", "Darbeli Tornavida" );
dc.importFeatureName ( "tr", "AAA831f001", "Schlüsselhilfe", "Anahtar Yardımı" );
dc.importFeatureName ( "tr", "AAA198f001", "Schlüsselweite", "Anahtar Genişliği" );
dc.importFeatureName ( "tr", "AAA960f001", "Schlüsselweite (Sechskantklinge)", "Anahtar Genişliği (Altıgen Bıçak)" );
dc.importFeatureName ( "tr", "_00028f001", "Schlüsselweite (in Zoll)", "Anahtar Genişliği (inç)" );
dc.importFeatureName ( "tr", "AAA781f001", "Schlüsselweite 1", "Anahtar Genişliği 1" );
dc.importFeatureName ( "tr", "AAA782f001", "Schlüsselweite 2", "Anahtar Genişliği 2" );
dc.importFeatureName ( "tr", "AAA962f001", "Schlüsselweite Betätigungssechskant", "Altıgen Soket Genişliği" );
dc.importFeatureName ( "tr", "_AAA190f001", "Schlüsselweite inch", "Genişlik (inç)" );
dc.importFeatureName ( "tr", "AAA198f002", "Schlüsselweiten einstellbare Werkzeuge", "Ayarlanabilir Anahtar Genişliğine Sahip Aletler" );
dc.importFeatureName ( "tr", "AAA198f003", "Schlüsselweiten inch einstellbare Werkzeuge", "Ayarlanabilir Anahtar Genişliğine Sahip Aletler (inç)" );
dc.importFeatureName ( "tr", "_00065f001", "Schnellwechselfutter", "Hızlı Değiştirme Mandreni" );
dc.importFeatureName ( "tr", "_00067f001", "Sechskantschlüsselhilfe", "Altıgen Anahtar Yardımı" );
dc.importFeatureName ( "tr", "_00120f001", "Sechskantschlüsselhilfe", "Altıgen Anahtar Yardımı" );
dc.importFeatureName ( "tr", "_00307f001", "Selbsteinstellend", "Kendinden Ayarlı" );
dc.importFeatureName ( "tr", "AAC524f001", "Spirallänge", "Spiral Uzunluğu" );
dc.importFeatureName ( "tr", "_00062f001", "Sprengring", "Segman" );
dc.importFeatureName ( "tr", "_00156f001", "Stichmass", "Gösterge" );
dc.importFeatureName ( "tr", "_00156f002", "Stichmasse einstellbare Werkzeuge", "Göstergeli Ayarlanabilir Aletler" );
dc.importFeatureName ( "tr", "_00156f003", "Stichmasse inch einstellbare Werkzeuge", "Göstergeli Ayarlanabilir Aletler (inç)" );
dc.importFeatureName ( "tr", "_00157f001", "Stichmaß Prüfaufsatz", "Gösterge Ataşmanı" );
dc.importFeatureName ( "tr", "AAB135f001", "Stiel auswechselbar", "Değiştirilebilir Sap" );
dc.importFeatureName ( "tr", "_00158f001", "Strecke Kraftanlagepunkt/Prüfgerät-Achse", "Kuvvet Uygulama Nokta Mesafesi/Test Cihazı Ekseni" );
dc.importFeatureName ( "tr", "_00407f001", "Switch Torque", "Switch Torque" );
dc.importFeatureName ( "tr", "_00101f001", "TORQ-SET Mplus", "Torq-Set Mplus" );
dc.importFeatureName ( "tr", "_00159f001", "Take it Easy KK VDE", "Take it Easy KK VDE" );
dc.importFeatureName ( "tr", "_00132f002", "Take it Easy Kraftform Micro Schraubendreher", "Take it Easy Kraftform Mikro Tornavida" );
dc.importFeatureName ( "tr", "_00131f002", "Take it Easy Kraftform Schraubendreher", "Take it Easy Kraftform Tornavida" );
dc.importFeatureName ( "tr", "_00133f002", "Take it Easy Quergriff Schraubendreher", "Take it Easy T Saplı Tornavida" );
dc.importFeatureName ( "tr", "_00712f001", "Take it easy Bits", "Take it Easy Bits" );
dc.importFeatureName ( "tr", "_00305f001", "Take it easy Maul-Ringratschen-Schlüssel Joker", "Take it Easy Joker Kombine Cırcır Anahtar" );
dc.importFeatureName ( "tr", "_00217f001", "Take it easy Nuss", "Take it Easy Lokma" );
dc.importFeatureName ( "tr", "_00507f001", "Take it easy Winkelschlüssel", "Take it Easy Allen Anahtar" );
dc.importFeatureName ( "tr", "_00602f001", "Tasche", "Çanta/Kılıf" );
dc.importFeatureName ( "tr", "_00603f001", "Teleskopklinge", "Teleskopik Bıçak" );
dc.importFeatureName ( "tr", "_00089f001", "Tiefe", "Derinlik" );
dc.importFeatureName ( "tr", "_00709f001", "Tiefenanschlag", "Derinlik Kontrolü" );
dc.importFeatureName ( "tr", "_00112f001", "Torque", "Tork" );
dc.importFeatureName ( "tr", "_00411f001", "Torque Lock Funktion", "Tork Kilitleme Fonksiyonu" );
dc.importFeatureName ( "tr", "AAB349f001", "Torsionszone", "Torsiyon Bölgesi" );
dc.importFeatureName ( "tr", "_00069f001", "Torsionszone", "Torsiyon Bölgesi" );
dc.importFeatureName ( "tr", "_00704f001", "Torsionszone", "Torsiyon Bölgesi" );
dc.importFeatureName ( "tr", "_00218f001", "Twist and Lock", "Döndür ve Kilitle" );
dc.importFeatureName ( "tr", "_00211f001", "Ultra-Compact", "Ultra Kompakt" );
dc.importFeatureName ( "tr", "_00229f001", "Umschalten Ratschenschraubendreher", "Yön Halkalı Cırcırlı Tornavida" );
dc.importFeatureName ( "tr", "_00216f001", "UnLock", "Kilidi Aç" );
dc.importFeatureName ( "tr", "_00715f001", "Unmagnetisch", "Manyetik Olmayan (Mıknatıssız)" );
dc.importFeatureName ( "tr", "_00046f001", "VDE geprüft", "VDE Test Onaylı" );
dc.importFeatureName ( "tr", "AAD140f001", "VDE-geprüft", "VDE Test Onaylı" );
dc.importFeatureName ( "tr", "AAA820f001", "Vierkant", "Kare" );
dc.importFeatureName ( "tr", "_00033f004", "Volumen", "Hacim" );
dc.importFeatureName ( "tr", "_00403f001", "Voreingestellt", "Önayarlı" );
dc.importFeatureName ( "tr", "_00012f001", "Voreingestellter Messwert Hauptskala", "Önayarlı Ölçüm Değeri Ana Ölçek" );
dc.importFeatureName ( "tr", "_00014f001", "Voreingestellter Messwert Nebenskala", "Önayarlı Ölçüm Değeri Alt Ölçek" );
dc.importFeatureName ( "tr", "_00227f001", "Wheel Socket", "Wheel Soket" );
dc.importFeatureName ( "tr", "AAC446f001", "Zentrierspitze", "Merkez Noktası" );
dc.importFeatureName ( "tr", "_01010f001", "Zuschaltbare Schlagschraubfunktion", "Aç/Kapa Darbeli Anahtar Fonksiyonu" );
dc.importFeatureName ( "tr", "_01012f001", "Zuschaltbare Schlagschraubfunktion", "Aç/Kapa Darbeli Anahtar Fonksiyonu" );
dc.importFeatureName ( "tr", "_00209f001", "Zyklop Metal Switch", "Zyklop Metal Switch" );
dc.importFeatureName ( "tr", "_00208f001", "Zyklop Speed", "Zyklop Speed" );
dc.importFeatureName ( "tr", "AAC506f001", "Zylinderschaftdurchmesser", "Silindirik Sap Çapı" );
dc.importFeatureName ( "tr", "AAA822f001", "arretierbar", "Kilitlenebilir" );
dc.importFeatureName ( "tr", "AAA969f001", "drehbare Kappe", "Döner Kapak" );
dc.importFeatureName ( "tr", "AAA015f001", "isoliert", "Yalıtımlı" );
dc.importFeatureName ( "tr", "AAA833f001", "isoliert nach EN 60900", "EN 60900'e Göre Yalıtımlı" );
dc.importFeatureName ( "tr", "AAB357f001", "mit Bohrung", "Delikli" );
dc.importFeatureName ( "tr", "AAA824f001", "mit Griff", "Saplı" );
dc.importFeatureName ( "tr", "_00013f001", "voreingestellter Messwert 1", "Önayarlı Ölçüm Değeri 1" );
dc.importFeatureName ( "tr", "_00013f002", "voreingestellter Messwert 2", "Önayarlı Ölçüm Değeri 2" );
dc.importFeatureName ( "tr", "_00013f003", "voreingestellter Messwert 3", "Önayarlı Ölçüm Değeri 3" );
dc.importFeatureName ( "tr", "_00013f004", "voreingestellter Messwert 4", "Önayarlı Ölçüm Değeri 4" );
dc.importFeatureName ( "tr", "_00013f005", "voreingestellter Messwert 5", "Önayarlı Ölçüm Değeri 5" );
dc.importFeatureName ( "tr", "_00410f001", "Überrutschmechanik", "Serbest Bırakma Mekanizması" );

%>
</body>
</html>

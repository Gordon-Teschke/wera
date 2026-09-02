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

dc.importMediaFeatureDescription ( "de", "Ausführung: Mit Umschaltknarre", "tr", "Dizayn: Terse çevrilebilir cırcırlı" );
dc.importMediaFeatureDescription ( "de", "BiTorsion für lange Lebensdauer", "tr", "Uzun hizmet ömürlü BiTorsion" );
dc.importMediaFeatureDescription ( "de", "Black Point Spitze", "tr", "BlackPoint Uç" );
dc.importMediaFeatureDescription ( "de", "Diamantbeschichtung für sicheren Sitz in der Schraube", "tr", "Vidaya güvenli bir şekilde oturması için elmas kaplama" );
dc.importMediaFeatureDescription ( "de", "Doppelbits mit 1/4\" -Sechskant Antrieb (Reihe 23)", "tr", "1/4\" altıgen sürücülü çift taraflı bits (Seri 23)" );
dc.importMediaFeatureDescription ( "de", "Doppelmaulschlüssel", "tr", "Çift açık ağız anahtar" );
dc.importMediaFeatureDescription ( "de", "ESD", "tr", "ESD" );
dc.importMediaFeatureDescription ( "de", "Edelstahl", "tr", "Paslanmaz çelik" );
dc.importMediaFeatureDescription ( "de", "Flexible-Lock-System für wahlweise dauerhaft-feste Verriegelung oder schnelles Wechseln des Aufsteckwerkzeugs", "tr", "Kalıcı kilitleme veya geçmeli aletin hızlı değişimi için esnek kilit sistemi" );
dc.importMediaFeatureDescription ( "de", "Führungshülsen", "tr", "Kılavuz manşonlar" );
dc.importMediaFeatureDescription ( "de", "Geeignet für Bits mit 4 mm Halfmoon-Antrieb (und Wera Reihe 9) und 4 mm HIOS-Antrieb (und Wera Reihe 21)", "tr", "4 mm halfmoon/yarımay sürücülü (ve Wera seri 9) ve 4 mm HIOS sürücülü (ve Wera seri 21) bitsler için uygundur" );
dc.importMediaFeatureDescription ( "de", "Geschmiedete Vollmetallknarre", "tr", "Dövme masif metal cırcır" );
dc.importMediaFeatureDescription ( "de", "Gewindebits mit M 4 Antrieb für direkten Maschinenanschluss (Reihe 11)", "tr", "Doğrudan makine bağlantılı M 4 sürücülü vidalı bitsler (Seri 11)" );
dc.importMediaFeatureDescription ( "de", "Gewindebits mit M 5 Antrieb für direkten Maschinenanschluss (Reihe 12)", "tr", "Doğrudan makine bağlantılı M 5 sürücülü vidalı bitsler (Seri 12)" );
dc.importMediaFeatureDescription ( "de", "Gewindebits mit M 6 Antrieb für direkten Maschinenanschluss (Reihe 15)", "tr", "Doğrudan makine bağlantılı M 6 sürücülü vidalı bitsler (Seri 15)" );
dc.importMediaFeatureDescription ( "de", "Gewindebits mit UNF #10-32 Antrieb für direkten Maschinenanschluss (Reihe 16)", "tr", "Doğrudan makine bağlantılı UNF #10-32 sürücülü vidalı bitsler (Seri 16)" );
dc.importMediaFeatureDescription ( "de", "Haltefunktion für Schraube und Mutter, mit Endanschlag gegen Abrutschen nach unten", "tr", "Vida ve somun için tutma fonksiyonu, aşağı doğru kaymayı önlemek için uç durduruculu" );
dc.importMediaFeatureDescription ( "de", "Hammerfunktion z. B. zum Ausrichten von Werkstücken", "tr", "Çekiç fonksiyonu, örneğin iş parçalarını düzeltmek için" );
dc.importMediaFeatureDescription ( "de", "Hex-Plus", "tr", "Hex-Plus" );
dc.importMediaFeatureDescription ( "de", "Hohlschaft für überstehende Gewindestangen", "tr", "Uzun vidalı dişli miller için delikli şaft" );
dc.importMediaFeatureDescription ( "de", "Impaktor", "tr", "Impactor" );
dc.importMediaFeatureDescription ( "de", "Isoliert, stückgeprüft gemäß IEC 60900", "tr", "Yalıtımlı, IEC 60900 standardına göre ayrı ayrı test edilmiştir" );
dc.importMediaFeatureDescription ( "de", "Klinge: Durchgehend Sechskant", "tr", "Bıçak: Yekpare altıgen" );
dc.importMediaFeatureDescription ( "de", "Klinge: Rund, biegsam", "tr", "Bıçak: Yuvarlak, esnek" );
dc.importMediaFeatureDescription ( "de", "Klinge: Sechskant", "tr", "Bıçak: Altıgen" );
dc.importMediaFeatureDescription ( "de", "Klinge: Vierkant als Schlüsselhilfe", "tr", "Bıçak: Kare anahtar destekli" );
dc.importMediaFeatureDescription ( "de", "Kraftform Micro mit Abrollschutz und drehbarer Kappe", "tr", "Kraftform Mikro, yuvarlanmaya karşı korumalı ve döner kapaklı" );
dc.importMediaFeatureDescription ( "de", "Kraftform mit Abrollschutz", "tr", "Yuvarlanmaya karşı korumalı Kraftform" );
dc.importMediaFeatureDescription ( "de", "Lasertip-Spitze", "tr", "Lasertip-Uç" );
dc.importMediaFeatureDescription ( "de", "Mit 1/4\" Sechskantantrieb für Halter D 6,3 (Reihe 1)", "tr", "D 6.3 tutucu için 1/4\" altıgen sürücülü (Seri 1)" );
dc.importMediaFeatureDescription ( "de", "Mit 1/4\" Sechskantantrieb für Halter F 6,3 (Reihe 4)", "tr", "F 6.3 tutucu için 1/4\" altıgen sürücülü (Seri 4)" );
dc.importMediaFeatureDescription ( "de", "Mit 4 mm HIOS-Antrieb für direkten Maschinenanschluss (Reihe 21)", "tr", "Doğrudan makine bağlantılı 4 mm HIOS sürücülü (Seri 21)" );
dc.importMediaFeatureDescription ( "de", "Mit 4 mm Halfmoon-Antrieb für direkten Maschinenanschluss (Reihe 9)", "tr", "Doğrudan makine bağlantılı 4 mm halfmoon/yarımay sürücü (Seri 9)" );
dc.importMediaFeatureDescription ( "de", "Mit 5 mm HIOS-Antrieb für direkten Maschinenanschluss (Reihe 22)", "tr", "Doğrudan makine bağlantılı 5 mm HIOS sürücülü (Seri 22)" );
dc.importMediaFeatureDescription ( "de", "Mit 5/16\"-Außenvierkantantrieb (Reihe 25)", "tr", "5/16\" kare sürücülü (Seri 25)" );
dc.importMediaFeatureDescription ( "de", "Mit 5/16\"-Sechskant Antrieb für Halter F 8 oder für direkten Maschinenanschluss (Reihe 6)", "tr", "F 8 tutucu veya doğrudan makine bağlantılı 5/16\" altıgen sürücülü (Seri 6)" );
dc.importMediaFeatureDescription ( "de", "Mit 5/16\"-Sechskant Antrieb für Halter nach D 8 oder für direkten Maschinenanschluss (Reihe 2)", "tr", "D 8 tutucu veya doğrudan makine bağlantılı 5/16\" altıgen sürücülü (Seri 2)" );
dc.importMediaFeatureDescription ( "de", "Mit 5/8\"-Sechskant Antrieb für direkten Maschinenanschluss (Reihe 19)", "tr", "Doğrudan makine bağlantısı için 5/8\" altıgen sürücülü (Seri 19)" );
dc.importMediaFeatureDescription ( "de", "Mit 7/16\"-Sechskant Antrieb für Halter F 11,2 oder für direkten Maschinenanschluss (Reihe 7)", "tr", "F 11.2 tutucu veya doğrudan makine bağlantılı 7/16\" altıgen sürücülü (Seri 7)" );
dc.importMediaFeatureDescription ( "de", "Mit 7mm Antrieb für direkten Maschinenanschluss nach H 7 - Fein Maschinen (Reihe 8)", "tr", "H 7 - Fein makinelere doğrudan bağlantılı için 7 mm sürücülü (Seri 8)" );
dc.importMediaFeatureDescription ( "de", "Mit Werkzeugfinder \"Take it easy\": Farbkennzeichnung nach Größen", "tr", "\"Take it easy\" alet buluculu: boyuta göre renk kodlaması" );
dc.importMediaFeatureDescription ( "de", "Mit reduziertem Klingendurchmesser sowie teilweise reduziertem Griffdurchmesser", "tr", "İnceltilmiş uç/bıçak çaplı ve kısmen inceltilmiş sap çaplı" );
dc.importMediaFeatureDescription ( "de", "Mit starkem Dauermagnet", "tr", "Güçlü kalıcı mıknatıslı" );
dc.importMediaFeatureDescription ( "de", "Reduzierter Klingendurchmesser", "tr", "İnceltilmiş bıçak/uç çapı" );
dc.importMediaFeatureDescription ( "de", "Rückschwenkwinkel von nur 30°", "tr", "Sadece 30°'lik geri dönüş açısı" );
dc.importMediaFeatureDescription ( "de", "SDS plus", "tr", "SDS plus" );
dc.importMediaFeatureDescription ( "de", "Schlagkappe", "tr", "Darbe kapağı" );
dc.importMediaFeatureDescription ( "de", "Schlagkappe mit integriertem Innenvierkant zur Aufnahme von 1/4\"-Klingen für höhere Kraftübertragung", "tr", "Daha yüksek güç aktarımı için 1/4\" uçları/bıçakları tutmak için entegre kare soketli darbe başlığı" );
dc.importMediaFeatureDescription ( "de", "Schlüsselhilfe", "tr", "Anahtar Yardımı" );
dc.importMediaFeatureDescription ( "de", "Schraubtiefe kann stufenlos eingestellt werden und ermöglicht das bündige Versenken von Schrauben", "tr", "Vida derinliği kademesiz olarak ayarlanabilir ve vidaların aynı hizada havşalanmasını sağlar" );
dc.importMediaFeatureDescription ( "de", "Schwungmassenkonstruktion und griffige Schnelldrehhülse für hohe Arbeitsgeschwindigkeit", "tr", "Yüksek çalışma hızı için döner dişli tasarımı ve kaymaz hızlı serbest bırakma manşonu" );
dc.importMediaFeatureDescription ( "de", "Sechskant-Kugelkopf", "tr", "Altıgen-Topbaş" );
dc.importMediaFeatureDescription ( "de", "Take it easy Werkzeugfinder: Mit Abtrieb-Farbkennzeichung und Größenstempel für einfaches Finden des gewünschten Bits", "tr", "\"Take it Easy\" alet bulucu: İhtiyaç duyulan bitsin kolayca bulunması için profil renk kodlu ve boyut damgalı" );
dc.importMediaFeatureDescription ( "de", "Take it easy Werkzeugfinder: Mit Farbkennzeichnung nach Größen sowie Größenstempelung – zum einfachen und schnellen Finden der gewünschten Nuss.", "tr", "\"Take it Easy\" alet bulucu: İhtiyaç duyulan lokmanın kolay ve hızlı bulunması için boyuta göre renk kodlu ve boyut damgalı." );
dc.importMediaFeatureDescription ( "de", "Teleskopklinge", "tr", "Teleskopik bıçak" );
dc.importMediaFeatureDescription ( "de", "Torsionsform gegen frühzeitigen Verschleiß", "tr", "Erken aşınmayı önlemek için burulma/torsiyon bölgeli" );
dc.importMediaFeatureDescription ( "de", "Umschalthebel für einen bequemen Richtungswechsel", "tr", "Rahat bir yön değişikliği için yön mandalı" );
dc.importMediaFeatureDescription ( "de", "Voreingestellter Messwert", "tr", "Önceden ayarlanmış ölçüm değeri" );
dc.importMediaFeatureDescription ( "de", "colour", "tr", "renk" );
dc.importMediaFeatureDescription ( "de", "durch die Doppelklinkenkonstruktion wird aus 38 robusten Zähnen eine 76er Feinzahn-Teilung mit einem Rückholwinkel von nur 4,7° ermöglicht", "tr", "Çift dişli tasarımı, 38 sağlam dişten 76 ince dişli yapı sadece 4,7°'lik bir dönüş açısı sağlar" );
dc.importMediaFeatureDescription ( "de", "einstellbar", "tr", "ayarlanabilir" );
dc.importMediaFeatureDescription ( "de", "frei schwenkbarer Knarrenkopf", "tr", "serbest dönüşlü cırcır kafası" );
dc.importMediaFeatureDescription ( "de", "in oberflächenschonender, kompakter textiler Box mit hoher Robustheit", "tr", "Yüksek sağlamlığa sahip, yüzey dostu, kompakt bir kumaş kılıf içinde" );
dc.importMediaFeatureDescription ( "de", "kleiner Rückholwinkel von 5°", "tr", "5 derecelik düşük geri dönüş açısı" );
dc.importMediaFeatureDescription ( "de", "kleiner Rückholwinkel von 6°", "tr", "6 derecelik düşük geri dönüş açısı" );
dc.importMediaFeatureDescription ( "de", "mit Durchsteckvierkant", "tr", "İtmeli kare sürücülü" );
dc.importMediaFeatureDescription ( "de", "mit Endanschlag gegen Abrutschen nach unten", "tr", "Aşağı doğru kaymayı önlemek için uç durduruculu" );
dc.importMediaFeatureDescription ( "de", "mit Haltefunktion", "tr", "Tutma fonksiyonlu" );
dc.importMediaFeatureDescription ( "de", "mit Lederkappe", "tr", "Deri kapaklı" );
dc.importMediaFeatureDescription ( "de", "mit Magnetisiergerät", "tr", "Mıknatıslayıcılı" );
dc.importMediaFeatureDescription ( "de", "mit Rapidaptor-Technologie", "tr", "Rapidaptor teknolojili" );
dc.importMediaFeatureDescription ( "de", "mit Ringmagnet und Anschlaghülse für den sicheren Halt der Schraube auf dem Halter", "tr", "Vidayı tutucu üzerinde güvenli bir şekilde tutmak için halka mıknatıs ve durdurma manşonlu" );
dc.importMediaFeatureDescription ( "de", "mit Schnelldrehhülse", "tr", "Hızlı dönüş manşonlu" );
dc.importMediaFeatureDescription ( "de", "mit extra kurzem Schenkel", "tr", "ekstra kısa kollu" );
dc.importMediaFeatureDescription ( "de", "schlanke Bauform zum Arbeiten auch in sehr engen Bauräumen", "tr", "Çok dar alanlarda bile çalışmak için ince tasarım" );
dc.importMediaFeatureDescription ( "de", "show in great tools", "tr", "harika aletlerde göster" );


dc.importAlternativeNames ( "de", "Black Point Spitze", "tr", "BlackPoint Uç" );
dc.importAlternativeNames ( "de", "Extra kurz", "tr", "Ekstra kısa" );
dc.importAlternativeNames ( "de", "Flexibler Schaft", "tr", "Esnek şaft" );
dc.importAlternativeNames ( "de", "GS - Geprüfte Sicherheit", "tr", "GS-Onaylı güvenlik" );
dc.importAlternativeNames ( "de", "Haltefunktion", "tr", "Tutma fonksiyonu" );
dc.importAlternativeNames ( "de", "Hohlschaft", "tr", "İçi boş şaft" );
dc.importAlternativeNames ( "de", "Joker - Doppelmaul", "tr", "Joker - çift ağız anahtar" );
dc.importAlternativeNames ( "de", "Nussfixierfunktion", "tr", "Lokma sabitleme fonksiyonu" );
dc.importAlternativeNames ( "de", "Rapidaptor", "tr", "Rapidaptor" );
dc.importAlternativeNames ( "de", "Reduzierter Klingendurchmesser und integrierte Schutzisolation", "tr", "İnceltilmiş uç/bıçak çapı ve entegre koruyucu yalıtım" );
dc.importAlternativeNames ( "de", "Ringmagnet", "tr", "Halka mıknatıs" );
dc.importAlternativeNames ( "de", "Rostfrei", "tr", "Paslanmaz çelik" );
dc.importAlternativeNames ( "de", "Sechskantklinge", "tr", "Altıgen bıçak" );
dc.importAlternativeNames ( "de", "Take it easy", "tr", "Take it Easy" );
dc.importAlternativeNames ( "de", "Umschaltbar", "tr", "Terse çevrilebilir/yön mandallı" );
dc.importAlternativeNames ( "de", "Vierkantklinge", "tr", "Kare bıçak" );
dc.importAlternativeNames ( "de", "Wera 2go", "tr", "Wera 2go" );
dc.importAlternativeNames ( "de", "Zyklop Durchsteckvierkant", "tr", "Zyklop itmeli kare sürücü" );
dc.importAlternativeNames ( "de", "Zyklop frei schwenkbarer Knarrenkopf", "tr", "Zyklop serbest dönüşlü cırcırlı kafa" );


%>
</body>
</html>

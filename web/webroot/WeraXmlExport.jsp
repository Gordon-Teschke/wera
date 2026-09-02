<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="com.computationaldesign.wera.jalo.WeraManager"%>
<%@page import="com.computationaldesign.wera.jalo.MediandoXml"%>
<%@page import="com.computationaldesign.wera.jalo.WeraProduktExport"%>
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
<%@page import="org.apache.commons.collections.iterators.ArrayListIterator" %>
<%
		class FileComparatorExport implements Comparator {
			public int compare(Object o1, Object o2) /*descending order*/{

				// --- Initialize
				File oFile1 = null;
				File oFile2 = null;

				try {
					// --- Hole Values
					oFile1 = ((File) o1);
					oFile2 = ((File) o2);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (oFile1 == null || oFile2 == null)
					return 0;
				else
					return oFile2.getName().compareTo(oFile1.getName());
			}
		}
		class WeraXMLExport {

			public Collection getExportFiles() {

				File fileliste = new File(Config
						.getParameter("wera.exportpath"));
				File[] files = fileliste.listFiles();
				Collection colFilelist = new ArrayList();
                for ( int iPos=0; iPos < files.length; iPos++ ) {
                    if ( files[iPos].isDirectory() )
                       colFilelist.add(files[iPos]);
                }
				return colFilelist;
			}
		}
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";


		// --- Handler
		if (request.getParameter("do") != null) {
			// --- Datendownload ------------------------------------------------
			if (request.getParameter("do").equals("download")) {

				// --- Initialize
				request.removeAttribute("do");

				MediandoXml mediandoxml = new MediandoXml();

				String strResultFile = mediandoxml.zipRequestedFiles( request.getParameter("file"), 
				                                                      request.getParameter("file"));

				String strDlResult = mediandoxml.strDownloadFile(response,
						strResultFile, true);

				mediandoxml = null;
			}
			// --- Datendownload ------------------------------------------------
		}
%>			
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<html>
<head>
<base href="<%=basePath%>">

<title>DATENEXPORT aus Hybris - Wera Wera</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<META HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=iso-8859-1">
<META HTTP-EQUIV="Language" CONTENT="de">
<link rel="stylesheet" type="text/css" href="styles.css">
<script type="text/javascript" src="javascript/jquery.js"></script>
<script type="text/javascript" src="javascript/jquery.tablesorter.js"></script>
<script>
$(function() {

   // --- Init
   $("div.download_area").hide();
   $("div.export_area").hide();
   $("div.buttondownload").show("slow");
   $("div.buttonexport_xml").show("slow");
   $("div.buttonexport_csv").show("slow");

   $("div.buttondownload").click(function() {
     $("div.export_area").hide();
     $("div.buttondownload").hide();
/*     
     $("div.download_area_content").empty();
     $.ajax({
                  url: "<%=basePath+"WeraXmlExport.jsp"%>",
                  data: "do=downloadliste",
                  async: false,
                  dataType: "html",
			      success: function(cResult) { 
			      alert("ready");
                     $("div.download_area_content").html( cResult  );
                  }
                  }).responseText;
*/                  
     $("div.download_area").show("slow");
     $("div.buttonexport_csv").show("slow");
     $("div.buttonexport_xml").show("slow");
   });	
   
   $("div.buttonexport_xml").click(function() {
     $("div.download_area").hide();
     $("div.buttonexport_xml").hide();
     $("div.export_area").show("slow");
     $("div.buttondownload").show("slow");
     $("div.buttonexport_csv").show("slow");
     $("div.export_area h2").empty().append(" XML => Inbetween");
     $("div.export_area div.checkbox_textwechsel").empty().append(" &nbsp;<STRONG>Exportdatei f&uuml;r Textwechsel optimieren</STRONG>");
     $("div.export_area input[@name='do']").attr('value','export_xml');
   });	
   
   $("div.buttonexport_csv").click(function() {
     $("div.download_area").hide();
     $("div.buttonexport_csv").hide();
     $("div.export_area").show("slow");
     $("div.buttondownload").show("slow");
     $("div.buttonexport_xml").show("slow");
     $("div.export_area h2").empty().append(" CSV => Texte f&uuml;r &Uuml;bersetzer");
     $("div.export_area div.checkbox_textwechsel").empty().append(" &nbsp;<STRONG>Entferne Trademarks</STRONG>");
     $("div.export_area input[@name='do']").attr('value','export_csv');
   });	
  
  
  // --- Lösche mehrere Exporte 
  $("img.download_delete_all").click(function() {

 jQuery.fn.extend({
   delete_all: function() {
     return this.each(function() { 
        if ( this.checked )  { 
            $.ajax({
                  url: "<%=request.getRequestURL()%>",
                  data: "do=delete&file=" + this.value,
			      success: function(cResult) { 
                     $("div.download_area_content tr."+this.value).remove();
                  }
                  });
                  
        }   
     });
   }
 });
    
      $("div.download_area_content input[@type=checkbox]").delete_all();
/*
      $("div.download_area_content").empty();
      var cValue = $.ajax({
                  url: "<%=basePath+"WeraXmlExport_func.jsp"%>",
                  data: "do=downloadliste",
                  async: false
                  }).responseText;
      $("div.download_area_content").append ( cValue  );
*/                  
   });	

   
   // --- Wechsel der Auswahl          
   $("img.download_toggle_all").click(function() {
 jQuery.fn.extend({
    
   toggle: function() {
     return this.each(function() { if ( this.checked ) this.checked = false; else this.checked = true; });
   },
   check: function() {
     return this.each(function() { this.checked = true; });
   },
   uncheck: function() {
     return this.each(function() { this.checked = false; });
   }
 });
      $("div.download_area_content input[@type=checkbox]").toggle();
   });	
   
   
		$("#simple").tableSorter();
		
		$("#simple-init-sort").tableSorter({
			sortColumn: 'name'	// Integer or String of the name of the column to sort by.  
		});

		$("#styling").tableSorter({
			sortColumn:     'name',					// Integer or String of the name of the column to sort by.  
			sortClassAsc:   'dataTableHeadingContent', 		// class name for ascending sorting action to header
			sortClassDesc:  'dataTableHeadingContent',	// class name for descending sorting action to header
			headerClass:    'dataTableHeadingContent', 				// class name for headers (th's)
			highlightClass: 'dataTableRowSelected' 		// class name for sort column highlighting.
		});
		
		$("#styling-cutom-striping").tableSorter({
			sortColumn: 'name',					// Integer or String of the name of the column to sort by.  
			sortClassAsc: 'headerSortUp', 		// class name for ascending sorting action to header
			sortClassDesc: 'headerSortDown', 	// class name for descending sorting action to header
			headerClass: 'header', 				// class name for headers (th's)
			stripingRowClass: ['even','odd'],	// class names for striping supplyed as a array.
			stripeRowsOnStartUp: true
		});
		
		$("#styling-cutom-highlighting").tableSorter({
			sortColumn: 'name',					// Integer or String of the name of the column to sort by.
			sortClassAsc: 'headerSortUp',		// Class name for ascending sorting action to header
			sortClassDesc: 'headerSortDown',	// Class name for descending sorting action to header
			headerClass: 'header'				// Class name for headers (th's)
			
		});
		
		$("#date-uk").tableSorter({
			sortColumn: 'name',			// Integer or String of the name of the column to sort by.
			sortClassAsc: 'headerSortUp',		// Class name for ascending sorting action to header
			sortClassDesc: 'headerSortDown',	// Class name for descending sorting action to header
			headerClass: 'header',			// Class name for headers (th's)
			dateFormat: 'dd/mm/yyyy' 		// set date format for non iso dates default us, in this case override and set uk-format
		});
	});
</script>

<style>
.dataTableHeadingContent {

    font-family: Verdana, Arial, Helvetica, sans-serif;
    font-size: 10px;
    font-weight: bold;
    padding: 5px;
    background-color: #0B50F3;
    color: #ffffff;
    border: 1px solid #CCCCCC;
}
.dataTableRow {
background-color: #F9F0F1;

}
.dataTableRowSelected {
 background-color: #FFF9E9;
 font-weight: bold;

 }
.dataTableRowOver {
 background-color: #F6F6F6; 

 }
.dataTableContent {
 font-family: Verdana, Arial, sans-serif; 
 font-size: 10px; 
 color: #000000;
 vertical-align: top;
 border-bottom: 1px dashed #cccccc;
 }
.dataTableContent_products {
 font-family: Verdana, Arial, sans-serif; 
 font-size: 10px; 
 color: #000000; 
 vertical-align: middle;
 background-color: #FFF2F2;
 } 
 

 
.dataTableContentRow {
font-family: Verdana, Arial, sans-serif; 
font-size: 10px; 
color: #000000; 
vertical-align: middle;
border-bottom: 1px solid;
border-color: #cccccc;
}
</style>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0" bgcolor="#ffffff" text="#BDD9FF" width="100%">
<div style="height: 120px; vertical-align: middle; margin:0px; padding:0;border:0px;background-color:#0B50F3; text-align:right;font-size:24px; font-weight:bold; color:#ffffff;">
<div style="vertical-align: bottom; align: left; float: left; margin:20px;adding:0;border:0;background-color:#0B50F3; text-align:right;font-size:24px; font-weight:bold; color:#ffffff;">DATENEXPORT aus Hybris</div>
<div style="align: right; margin:20px;padding:0;border:0;background-color:#0B50F3; text-align:right;font-size:24px; font-weight:bold; color:#ffffff;"><img src="image/e-business_platform.gif" /><br/>WERA - Edition</div>
</div>
<div style="margin:0px;margin-left:20px;padding:0px;border:0px;background-color:#ffffff; text-align:left;font-size:12px; font-weight:bold; color:#0B50F3;">
<%

		// --- Handler
		if (request.getParameter("do") != null) {
			// --- Datendownload ------------------------------------------------
			if (request.getParameter("do").equals("downloadliste")) {

               WeraXMLExport support = new WeraXMLExport();
               Collection colFileList = support.getExportFiles();
               if ( colFileList.size() > 0  ) { 
%>
<div class="download_area_toolbar" >
<img title="Auswahl aller Downloads" class="download_toggle_all"   border='0' width='15px' src='images/pfeil_down.jpg' />
<img title="L&ouml;sche alle Downloads" class="download_delete_all"   border='0' width='15px' src='images/loeschen.gif' />
</div>
<table id="styling" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<th></th>
			<th></th>
			<th></th>
			<th>Name</th>
		</tr>
	</thead>
	<tbody>
<%
				String cUrlDel = "";
				String cUrlDownL = "";
				String cImgDel = "<img border='0' src='" + basePath
						+ "images/loeschen.gif' />";
				String cImgDownL = "<img border='0' src='" + basePath
						+ "images/vorlage_gif.gif' />";
				File file = null;
				for (Iterator it1 = colFileList.iterator(); it1.hasNext();) {
					file = (File) it1.next();
					System.out.println("++File="+ file.getName() );
						cUrlDel = basePath + "WeraXmlExport.jsp?do=delete&file="
								+ file.getName();
						cUrlDownL = basePath
								+ "WeraXmlExport.jsp?do=download&file=" + file.getName();
						String cCheckBox = "<input type='checkbox' name='fn_"+file.getName()+"' value='"+file.getName()+"' />";
						String cURLDownL = "<a href='" + cUrlDownL + "'>" + cImgDownL + "</a>";
						String cURLDel = "<a href='" + cUrlDel + "'>" + cImgDel + "</a>";
%>
		<tr>
			<td><%=cCheckBox%></td>
			<td><%=cURLDownL%></td>
			<td> - </td>
			<td><%=file.getName()%></td>
		</tr>
<%								
				} // --- for (Iterator it1 = colFileList.iterator(); it1.hasNext();) {
%>
	</tbody>
</table>
<%
}
else {
%>
Zur Zeit sind keine Downloads verf&uuml;gbar.
<%
}}
			// --- Datendownload ------------------------------------------------

			// --- Datenexport ------------------------------------------------
			if (request.getParameter("do").equals("export_csv") ) {
 			   
 			   // --- Function support
 			   Collection colCategories = new ArrayList();
				String[] arrayStr = request.getParameterValues("categories");
				Collections.addAll(colCategories,arrayStr);

                // --- Initialize
				boolean bRemoveTrademarks = false;
				if (request.getParameter("textwechsel") != null) {
					bRemoveTrademarks = true;
				}
                
				// --- Kategoryexport
				WeraProduktExport oWeraProduktExport = null;
				oWeraProduktExport = new WeraProduktExport();
                String strResultFile1 = oWeraProduktExport.CategoryExportCSV(null,colCategories, request.getParameter("language"),
						request.getParameter("catalog"), request
								.getParameter("catalogversion"), bRemoveTrademarks );

				// --- Kategoryexport
                oWeraProduktExport.ProduktExportCSV( strResultFile1, colCategories, request.getParameter("language"),
						request.getParameter("catalog"), request
								.getParameter("catalogversion"), bRemoveTrademarks );

                // ---- Fussnoten
                oWeraProduktExport.exportAllFootnotes ( strResultFile1, request.getParameter("language"), bRemoveTrademarks );

                // --- Keywords
  	            oWeraProduktExport.exportAllKeywords ( strResultFile1, request.getParameter("language"), bRemoveTrademarks );
                
				// --- Aufräumen
				oWeraProduktExport = null;
				request.removeAttribute("do");

				// --- ENDE
				out.println("Datenexport abgeschossen.");
				out.println("Exportdatei:" + strResultFile1 );
				out.println("<br><a href='" + request.getRequestURL()
						+ "?show=download'>Zur&uumlk;ck zum Download</a>");
			}
			
			// --- Datenexport (XML) ------------------------------------------
			if (request.getParameter("do").equals("export_xml")  ) {
				// --- Initialize
				Collection colCategories = new ArrayList();
				boolean bTextwechsel = false;
				if (request.getParameter("textwechsel") != null) {
					bTextwechsel = true;
				}

				String[] arrayStr = request.getParameterValues("categories");
				Collections.addAll(colCategories,arrayStr);

				// --- Produktexport
				WeraProduktExport oWeraProduktExport = null;
				oWeraProduktExport = new WeraProduktExport();
                //oWeraProduktExport.setExportFormatter("WeraWebExportFormatter");

				String strXmlResultFile = oWeraProduktExport.ProduktExport(
						colCategories, request.getParameter("language"),
						request.getParameter("catalog"), request
								.getParameter("catalogversion"), request
								.getParameter("destfile"), bTextwechsel);

				// --- Aufräumen
				oWeraProduktExport = null;
				request.removeAttribute("do");

				// --- ENDE
				out.println("<p>Datenexport abgeschossen.");
				out.println("Neue Exportdatei: " + strXmlResultFile);
				//out.println("</p><br><br><a href='" + request.getRequestURL()
				//		+ "&do=download&file=" + strXmlResultFile + "_" + request.getParameter("language") + "'>Download der XML-Datei als ZIP-Archiv</a>");
				out.println("<br><a href='" + request.getRequestURL()
						+ "'>Zur&uuml;ck zu &Uuml;bersicht</a>");
			}
			// --- Datenexport ------------------------------------------------


			// --- Dateiliste  ------------------------------------------------
			if (request.getParameter("do").equals("dateiliste")) {

				// --- Initialize
				request.removeAttribute("do");

				File fileliste = new File(Config
						.getParameter("wera.exportpath"));
				File[] files = fileliste.listFiles();

				out.println("<ul style='padding: 0em;'>");
				for (int iPos = 0; iPos < files.length; iPos++) {
					if (files[iPos].isDirectory()) {
						out.println("<li >" + files[iPos].getName()
								+ "a href='" + request.getRequestURL()
								+ "?do=download&file=" + files[iPos].getName()
								+ "'>Download</a>&nbsp;<a href='"
								+ request.getRequestURL() + "?do=delete&file="
								+ files[iPos].getName() + "'>Deletr</a>");
					}
				}
				out.println("</ul>");
				out.println("<br><a href='" + request.getRequestURL()
						+ "?show=download'>Zur&uumlk;ck zur Startseite</a>");
			}
			// --- Dateiliste  ------------------------------------------------

			// --- Löschen eines Exports ----------------------------------------
			if (request.getParameter("do").equals("delete")) {

				MediandoXml mediandoxml = new MediandoXml();
				mediandoxml.deleteDirRecursiv(Config
						.getParameter("wera.exportpath"), request
						.getParameter("file"));

				File delFile = new File(Config.getParameter("wera.exportpath")
						+ "/" + request.getParameter("file"));
				out.println("<br>L&ouml;sche "
							+ Config.getParameter("wera.exportpath") + "/"
							+ request.getParameter("file"));
				if (delFile == null) 
					// --- ENDE
					out.println("<br>L&ouml;schen erfolgreich abgeschossen.");
				else
					out.println("<br>L&ouml;schen erfolgreich abgeschossen.");
				out.println("<br><a href='"
									+ request.getRequestURL()
									+ "?show=download'>Zur&uuml;ck zur Startseite</a>");
			}
			// --- Löschen eines Exports ----------------------------------------
		}

		// ---
		if (request.getParameter("do") == null) {

			JaloSession jaloSession = WebSessionFunctions.getSession(request);
			WeraManager wm = WeraManager.getInstance();

			UserManager userMg = jaloSession.getUserManager();
			String loginname = "";
			String password = "";
			try {
				// --- Get adminuser
				Employee customer = userMg.getAdminEmployee();

				// --- Initialize Logindata
				loginname = customer.getLogin();
				password = customer.getPassword();
			} catch (Exception e) {
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
				jaloSession.transfer(prop);
			} catch (JaloSecurityException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}

			// --- Sprache initialisieren
			String strLang = request.getParameter("language");
			if (strLang == null)
				strLang = "de";
			wm.initLanguageByIsoCode(strLang);

			// --- Katalog initialisieren
			String strCatalog = request.getParameter("catalog");
			if (strCatalog == null)
				strCatalog = "weracatalog";

			// --- Katalogversion initialisieren
			String strCatalogVersion = request.getParameter("catalogversion");
			if (strCatalogVersion == null)
				strCatalogVersion = "weramaster";
				//strCatalogVersion = "prospekt_automotive_2008";


			// --- Initialize
			String strTemp = "";
			%>
<!-- ************************************* STEP 1 ************************************* -->
<!-- Buttons 
<div class="button_pl_export_xml">>> Export Preislisten  => Inbetween (XML)</div>
<div class="buttonexport_csv">>> Export Kistenpfennig (CSV+Grafiken)</div>
<div class="buttondownload">>> Import Fussnoten (XML)</div>
<div class="buttonexport_csv">>> Import Keywords (XML)</div>
<div class="buttonexport_csv">>> Generiere Index</div>
-->
<div style="background-color: #0B50F3; height: 1px; max-height:1px; min-height:1px; width: auto;" ></div>
<div class="buttonexport_csv">>> Export Texte f&uuml;r &Uuml;bersetzer (CSV)</div>
<div class="buttonexport_xml">>> Export Katalogdaten => Inbetween (XML)</div>
<div class="buttondownload">>> Download der Exporte</div>
<div style="background-color: #0B50F3; height: 1px; max-height:1px; min-height:1px; width: auto;" ></div>
<!-- Buttons -->


<!-- ************************************* Dateidownload ****************************** -->
<div class="download_area" >
<h2>Download der Exporte</h2>
<div class="download_area_content">
<%
			// --- Datendownload ------------------------------------------------
               WeraXMLExport support = new WeraXMLExport();
               Collection colFileList = support.getExportFiles();
               if ( colFileList.size() > 0  ) { 
%>
<div class="download_area_toolbar" >
<img title="Auswahl aller Downloads" class="download_toggle_all"   border='0' width='15px' src='images/pfeil_down.jpg' />
<img title="L&ouml;sche alle Downloads" class="download_delete_all"   border='0' width='15px' src='images/loeschen.gif' />
</div>
<table id="styling" border="0" cellpadding="0" cellspacing="0">
	<thead>
		<tr>
			<th></th>
			<th></th>
			<th></th>
			<th>Name</th>
		</tr>
	</thead>
	<tbody>
<%
				String cUrlDel = "";
				String cUrlDownL = "";
				String cImgDel = "<img border='0' src='" + basePath
						+ "images/loeschen.gif' />";
				String cImgDownL = "<img border='0' src='" + basePath
						+ "images/vorlage_gif.gif' />";
				File file = null;
				for (Iterator it1 = colFileList.iterator(); it1.hasNext();) {
					file = (File) it1.next();
					System.out.println("++File="+ file.getName() );
						cUrlDel = basePath + "WeraXmlExport.jsp?do=delete&file="
								+ file.getName();
						cUrlDownL = basePath
								+ "WeraXmlExport.jsp?do=download&file=" + file.getName();
						String cCheckBox = "<input type='checkbox' name='fn_"+file.getName()+"' value='"+file.getName()+"' />";
						String cURLDownL = "<a href='" + cUrlDownL + "'>" + cImgDownL + "</a>";
						String cURLDel = "<a href='" + cUrlDel + "'>" + cImgDel + "</a>";
%>
		<tr id="<%=file.getName()%>">
			<td><%=cCheckBox%></td>
			<td><%=cURLDownL%></td>
			<td> - </td>
			<td><%=file.getName()%></td>
		</tr>
<%								
				} // --- for (Iterator it1 = colFileList.iterator(); it1.hasNext();) {
%>
	</tbody>
</table>
<%
}
else {
%>
Zur Zeit sind keine Downloads verf&uuml;gbar.
<%
			// --- Datendownload ------------------------------------------------
}
%>
</div>
</div>
<!-- ************************************* Dateidownload ****************************** -->






<!-- ************************************* Datenexport ******************************** -->
<%
if ( true ) 
%>
<div class="export_area" >
<h2>Export</h2>
<FORM name="step2" method="post" enctype="application/x-www-form-urlencoded" action="<%= basePath %>WeraXmlExport.jsp" name="export">

<!-- VALUES (START) -->
<INPUT type="hidden" name="show" value="" />
<!-- VALUES (ENDE) -->

<!-- Auswahl Sprache (START) -->
<p>
<br><STRONG>Auswahl Sprache:</STRONG><br>
<select id="language" style="width: 250px;" name="language" onchange="javascript:reload(this,'language');"><%// --- Hole alle Sprachen
			Collection colLang = C2LManager.getInstance().getAllLanguages();
			for (Iterator it0 = colLang.iterator(); it0.hasNext();) {
				Language lang = (Language) it0.next();
				if (strLang.equals(lang.getIsoCode()))
					strTemp = "selected";
				else
					strTemp = "";
				%>
<option value='<%= lang.getIsoCode() %>' <%= strTemp %>><%= lang.getName()%></option><%
			} // --- for (Iterator it0 = result.iterator(); it0.hasNext();)

			%>
</select>
</p>
<!-- Auswahl Sprache (ENDE) -->

<!-- Auswahl Katalog (START) -->
<script>
function reload ( oSelect, strParameter ) {
   
   oCatalogversion = document.getElementById('catalogversion');
   oLanguage       = document.getElementById('language');
   document.location.href='WeraXmlExport.jsp?catalogversion=' + oCatalogversion.value + '&language=' + oLanguage.value;
}
</script>
<p>
<br><STRONG>Auswahl Katalog (weracatalog):</STRONG><br>
<select id="catalogversion" style="width: 250px;" name="catalogversion" onchange="javascript:reload(this,'catalogversion');">
	<%
	        // --- Hole alle Katalogversionen
			Catalog weraCatalog = CatalogManager.getInstance().getCatalog(strCatalog);
			Collection colCatalogVersions = weraCatalog.getCatalogVersions();
			for (Iterator it0 = colCatalogVersions.iterator(); it0.hasNext();) {
				CatalogVersion classificationSystemVersion = (CatalogVersion) it0
						.next();
			    String strVersion = (String)classificationSystemVersion.getAttribute("version");
			    
				if ( classificationSystemVersion instanceof CatalogVersion ) {
                  if ( strVersion.equals(strCatalogVersion) )
                      out.write("<option value=" + strVersion + " selected>" + strVersion + "</option>");
                  else
                      out.write("<option value=" + strVersion + ">" + strVersion + "</option>");
	            }
			} // --- for (Iterator it0 = result.iterator(); it0.hasNext();)
	  %>
			
</select>
</p>

<!-- VALUES (START) -->
<INPUT type="hidden" name="do"             value="export" />
<INPUT type="hidden" name="language"       value="<%= strLang %>" />
<INPUT type="hidden" name="catalog"        value="<%= strCatalog %>" />
<INPUT type="hidden" name="catalogversion" value="<%= strCatalogVersion %>" />
<!-- VALUES (ENDE) -->

<!-- Auswahl Kapitel (START) -->
<p>
<STRONG>Auswahl Produktgruppen:</STRONG><br><br>
<select size="20" style="width: 250px; height:200px;" name="categories" multiple>
<%
				// --- 1. Ebene
				Category RootCategory = wm.getRootCategory(strCatalog,strCatalogVersion);
				String strRootCategory = "";
				if ( RootCategory != null )
				    strRootCategory = RootCategory.getCode();
				else
				    strRootCategory = "root";
				
				wm.enableCheckForActivation();
 			    wm.initLanguageByIsoCode(strLang);
				Collection result1 = wm.getCategories(strCatalog,
						strCatalogVersion, strRootCategory );
				for (Iterator it1 = result1.iterator(); it1.hasNext();) {
					Category category1 = (Category) it1.next();

%>
	<option value='<%= category1.getCode() %>' selected><%= category1.getName()%></option>
<%
					// --- 2. Ebene
					Collection result2 = wm.getCategories(strCatalog,
							strCatalogVersion, category1.getCode());
					for (Iterator it2 = result2.iterator(); it2.hasNext();) {
						Category category2 = (Category) it2.next();
%>
	<option value='<%= category2.getCode() %>' selected>-><%= category2.getName()%></option>
<%
 
						// --- 3. Ebene
						Collection result3 = wm.getCategories(strCatalog,
								strCatalogVersion, category2.getCode());
						for (Iterator it3 = result3.iterator(); it3.hasNext();) {
							Category category3 = (Category) it3.next();
%>
	<option value='<%= category3.getCode() %>' selected>--><%= category3.getName()%></option>
<%
						} // --- for (Iterator it3 = result.iterator(); it3.hasNext();)

					} // --- for (Iterator it2 = result.iterator(); it2.hasNext();)

				%>
	<option value='' disabled="disabled">&nbsp;&nbsp;-------&nbsp;&nbsp;</option>
<%} // --- for (Iterator it0 = result.iterator(); it0.hasNext();)

%>
 </select>
</p>
<!-- Auswahl Kapitel (ENDE) --> 

<!-- Textwechsel (START) -->
<p>
<INPUT style="float:left;" type="checkbox" name="textwechsel" value="textwechsel" >
<div class="checkbox_textwechsel"></div>
</p>
<!-- Textwechsel (ENDE) -->

<!-- Zieldatei (START)  -->
<p>
Zieldatei:</STRONG><br>
<INPUT size="128" type="text" name="destfile" value="<%= Config.getParameter("wera.exportpath") %>daten.xml">
</p>
<!-- Zieldatei (ENDE) -->

<!-- START -->
<INPUT type="submit" value="EXPORT starten" name="bstep2" />
</FORM>
</div>
<%}
%>
<!-- ************************************* Datenexport ******************************** -->
</div>



</body>
</html>
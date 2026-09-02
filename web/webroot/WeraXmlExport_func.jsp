<%@page import="de.hybris.platform.jalo.*"%>
<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="com.computationaldesign.wera.jalo.WeraManager"%>
<%@page import="com.computationaldesign.wera.jalo.MediandoXml"%>
<%@page import="com.computationaldesign.wera.jalo.WeraProduktExport"%>
<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.jalo.tools.WebSessionFunctions"%>
<%@page import="com.computationaldesign.wera.jalo.*"%>
<%@page import="de.hybris.platform.ext.category.jalo.Category"%>
<%@page	import="de.hybris.platform.ext.catalog.jalo.classification.ClassificationSystemVersion"%>
<%@page	import="de.hybris.platform.ext.catalog.jalo.CatalogVersion"%>
<%@page	import="de.hybris.platform.ext.catalog.jalo.classification.ClassificationSystem"%>
<%@page import="de.hybris.platform.jalo.type.AttributeDescriptor"%>
<%@page import="de.hybris.platform.jalo.type.ComposedType"%>
<%@page import="de.hybris.platform.jalo.user.User"%>
<%@page import="de.hybris.platform.jalo.user.UserManager"%>
<%@page import="de.hybris.platform.jalo.user.Customer"%>
<%@page import="de.hybris.platform.jalo.user.Employee"%>
<%@page import="de.hybris.platform.jalo.extension.ExtensionManager"%>
<%@page import="de.hybris.platform.ext.category.jalo.CategoryManager"%>
<%@page import="de.hybris.platform.ext.catalog.jalo.CatalogManager"%>
<%@page import="de.hybris.platform.jalo.security.JaloSecurityException"%>
<%@page import="de.hybris.platform.ext.catalog.jalo.classification.ClassificationAttribute"%>
<%@page import="de.hybris.platform.jalo.c2l.C2LManager"%>
<%@page import="de.hybris.platform.jalo.c2l.Language"%>
<%@page import="de.hybris.platform.util.Config"%>
<%@ page import="org.apache.commons.collections.iterators.ArrayListIterator" %>
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
				} catch (JaloInvalidParameterException e) {
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
				Collections
						.sort((List) colFilelist, new FileComparatorExport());
				return colFilelist;
			}
		}
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";

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
						cUrlDel = basePath + "WeraXMLExport.jsp?do=delete&file="
								+ file.getName();
						cUrlDownL = basePath
								+ "WeraXMLExport.jsp?do=download&file=" + file.getName();
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
%>

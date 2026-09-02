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

			// --- Datenexport (XML) ------------------------------------------
			
				// --- Initialize
				boolean bTextwechsel = false;
				bTextwechsel = true;
				Collection colCategories = new ArrayList();

				//String[] arrayStr = request.getParameterValues("categories");
				//Collections.addAll(colCategories,arrayStr);
				colCategories.add("HOLZ");
				// --- Produktexport
				WeraProduktExport oWeraProduktExport = null;
				oWeraProduktExport = new WeraProduktExport();
                //oWeraProduktExport.setExportFormatter("WeraWebExportFormatter");

				String strXmlResultFile = oWeraProduktExport.ProduktExport(
						colCategories, "de",
						"weracatalog", "weramaster", "c:/daten.xml", bTextwechsel);

				// --- Aufräumen
				oWeraProduktExport = null;

				// --- ENDE
				out.println("<p>Datenexport abgeschossen.");
				out.println("Neue Exportdatei: " + strXmlResultFile);
				//out.println("</p><br><br><a href='" + request.getRequestURL()
				//		+ "&do=download&file=" + strXmlResultFile + "_" + request.getParameter("language") + "'>Download der XML-Datei als ZIP-Archiv</a>");
				out.println("<br><a href='" + request.getRequestURL()
						+ "'>Zur&uuml;ck zu &Uuml;bersicht</a>");
			// --- Datenexport ------------------------------------------------
%>
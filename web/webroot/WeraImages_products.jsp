<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="java.io.FileWriter"%>
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
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'MyJsp.jsp' starting page</title>
    
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">
    
    <!--
    <link rel="stylesheet" type="text/css" href="styles.css">
    -->
  </head>
  
  <body>
    This is my JSP page. <br>
    <% 
		JaloSession jaloSession = WebSessionFunctions.getSession(request);
		
         // --- LOGIN ------------------------------------------------------------------------------
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
        // --- LOGIN ------------------------------------------------------------------------------
       
        // --- OS
        String strOS = request.getParameter("os");
        out.println("OS=" + strOS + "=");
        if ( strOS == null || strOS.length() == 0 )
           strOS = "win";
        out.println("OS=" + strOS);
        
        // --- Initialize
        String srcImgPath  = "home/hybris/import/images/web_slim";
        String destImgPath = "";
        if ( strOS.equals("win") ) {
           srcImgPath  = "C:/" + srcImgPath;
           destImgPath = "C:/development/hybris_v43_wera/data/media/sys_master/";
        } else {
           destImgPath = "/www_root/hybris_v43_wera/data/media/sys_master/";
           srcImgPath  = "C:" + srcImgPath;
           destImgPath = "C:/development/hybris22/platform/ext/mediaweb/web/webroot/";
        }
		destImgPath = "/www_root/hybris_v43_wera/data/media/sys_master/";
		srcImgPath  = "/home/hybris/import/images/web_slim";
		
        // --- Hole Daten
        WeraManager wm = WeraManager.getInstance();
        WeraProduct weraproduct = null;
        Collection allProducts = new ArrayList();
        allProducts.addAll( WeraProduct.getAllInstances() );
        WeraMedia media = null;
        Collection allMedias = new ArrayList();
        allMedias.addAll( WeraMedia.getAllInstances() );
     	int iCnt = 0;



// --- Anlegen der Media-Objekte (Produkte) ---------------------------
        // --- Initialize
		Collection colMediaCollection = null;
   	    WeraMedia webWeraMedia        = null;
   	    WeraMedia tmpWeraMedia        = null;
		ArrayList aSizes = new ArrayList();
		aSizes.add("100");
		aSizes.add("260");
		aSizes.add("380");
		
		// --- Holen aller WeraProdukte
		HashMap hMapProduct = new HashMap();
	    for ( Iterator it0 = allProducts.iterator(); it0.hasNext(); ) {
   	        weraproduct = (WeraProduct)it0.next();
     		String jpgNormalizedFilename = weraproduct.normalizeFilenameForImageLookup()+".jpg";
			hMapProduct.put (jpgNormalizedFilename,weraproduct);
		}
		
		// --- Schleife über alle Grössen
		for ( Iterator it2 = aSizes.iterator(); it2.hasNext(); ) {
			String strSize1 = (String)it2.next();
			out.println( "<br>++" + strSize1 );
			
			FileWriter hScript1 = new FileWriter(srcImgPath + "/list" + strSize1 + ".txt" );
			File dir1 = new File(srcImgPath + "/" + strSize1 );
			String[] children1 = dir1.list();
			if (children1 == null) {
				// Either dir1 does not exist or is not a directory
				out.println( "<br>++keine Dateien gefunden" );
			} else {
			
				// --- Schleife über alle Dateien
				for (int i=0; i<children1.length; i++) {
					
					// --- get filename
					String filename = children1[i];
					out.println("++Datei: " + filename);

					// --- Hole WeraProdukt
					String strProductHashCode = filename.replace("web"  + strSize1 + "_","");
					weraproduct = (WeraProduct)hMapProduct.get (strProductHashCode);
					if ( weraproduct != null ) {
						// --- Wera Produkt gefunden
						hScript1.write( strProductHashCode + "\tweraproduct found" );
// --- Bilder anlegen -----------------------------------------------------------------------------------------			

					   // --- Prüfe Webbilder
					   if ( strSize1.equals("100" ) )
							colMediaCollection = (Collection)weraproduct.getAttribute("pictures2");
					   if ( strSize1.equals("260" ) )
							colMediaCollection = (Collection)weraproduct.getAttribute("pictures3");
					   if ( strSize1.equals("380" ) )
							colMediaCollection = (Collection)weraproduct.getAttribute("featureicons1");
							
						// --- sind Bilder vorhanden, dann ersetzen
					   if ( colMediaCollection != null && colMediaCollection.size() > 0 ) {
							try {
								// -- get Media
								webWeraMedia = (WeraMedia)colMediaCollection.iterator().next();
							   
								// --- Grafik hochladen
								out.println("++replace Media weraproduct:" + weraproduct);
								hScript1.write( "\t" + filename + "\tupload" );
								
								// --- Upload
								File file = new File(srcImgPath + "/" + strSize1 + "/" + filename);
								webWeraMedia.setFile( file );
								wm.setAttribute( webWeraMedia, "code", "web" + strSize1 + "_" + filename);
								wm.setAttribute( webWeraMedia, "mediakategorie", "webimage" );
								wm.setAttribute( webWeraMedia, "realfilename", file.getName() );
								file = null;
								hScript1.write( "\tOK");
								
							} catch ( Exception e) {

								// TODO Auto-generated catch block
								hScript1.write( "\tERROR-catch");
								out.println("Exception:" + e.getMessage());
								e.printStackTrace();
							}
					   }
					   else  {
							try {
								out.println("++create Media weraproduct:" + weraproduct);
								
								hScript1.write( "\t" + filename + " - create");
								
								colMediaCollection = new ArrayList();
								webWeraMedia = wm.createWebMedia ( srcImgPath + "/" + strSize1 + "/" + filename, "web" + strSize1 + "_" + filename, "webimage" );
								if ( webWeraMedia != null ) {
									colMediaCollection.add ( webWeraMedia );
									
								   // --- Prüfe Webbilder
								   if ( strSize1.equals("100" ) )
										wm.setAttribute( weraproduct, "pictures2", colMediaCollection);
								   if ( strSize1.equals("260" ) )
										wm.setAttribute( weraproduct, "pictures3", colMediaCollection);
								   if ( strSize1.equals("380" ) )
										wm.setAttribute( weraproduct, "featureicons1", colMediaCollection);
									hScript1.write( "\tOK");
									
								} // --- if ( webWeraMedia != null ) 
								else {
									out.println("++webWeraMedia=" + webWeraMedia);
									hScript1.write( "++webWeraMedia=" + webWeraMedia );
								}
								
							} catch ( Exception e) {

								// TODO Auto-generated catch block
								hScript1.write( "\tERROR-catch");
								out.println("Exception:" + e.getMessage());
								e.printStackTrace();
							}
						
				} // --- if ( colMediaCollection != null && colMediaCollection.size() > 0 ) {

// --- Bilder anlegen -----------------------------------------------------------------------------------------
						
					} else  {
						
						// --- Wera Produkt nicht gefunden
						hScript1.write( strProductHashCode + "\tweraproduct not found");
						out.println( "<span style='color:red;'>"+ strProductHashCode + " weraproduct not found</span>");
						
					} // --- if ( weraproduct != null )
					
					// --- Nächste Zeile
					out.println("<hr>");
					hScript1.write( "\r\n" );
					
				} // ---for (int i=0; i<children1.length; i++) {
			
			} // --- if (children1 == null) {
			
			// --- Logdatei schliessen
			hScript1.close();
			
		} // --- for ( Iterator it2 = aSizes.iterator(); it2.hasNext(); ) {
		

// --- Anlegen der Media-Objekte (Produkte) ---------------------------

%>
  </body>
</html>
    
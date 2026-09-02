<!-- 
ordnet die Produkte des Masterkataloges den Feature-Klassen zu
Datum: 24.03.15 GT
-->

<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="java.io.File"%>
<%@page import="de.hybris.platform.core.Registry"%>
<%@page import="com.computationaldesign.wera.jalo.WeraManager"%>
<%@page import="com.computationaldesign.wera.jalo.MediandoXml"%>
<%@page import="com.computationaldesign.wera.jalo.DimensionImport"%>
<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.util.WebSessionFunctions"%>
<%@page import="com.computationaldesign.wera.jalo.*"%>
<%@page import="de.hybris.platform.category.jalo.Category"%>
<%@page	import="de.hybris.platform.catalog.jalo.CatalogVersion"%>
<%@page	import="de.hybris.platform.catalog.jalo.classification.ClassificationSystemVersion"%>
<%@page	import="de.hybris.platform.catalog.jalo.classification.ClassificationSystem"%>
<%@page	import="de.hybris.platform.catalog.jalo.classification.ClassificationClass"%>
<%@page import="de.hybris.platform.catalog.jalo.classification.ClassificationAttribute"%>
<%@page import="de.hybris.platform.catalog.model.classification.ClassificationAttributeModel"%>
<%@page import="de.hybris.platform.servicelayer.search.FlexibleSearchService"%>
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
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
%>
<base href="<%=basePath%>">

<title>DATENIMPORT von Dimensionen</title>

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
<hr>
<h2>Ordnet die Produkte des Masterkataloges den Feature-Klassen zu</h2>
<br>Beispiel: weramaster/SCREWDRIVER => werazusatz/FEATURES_SCREWDRIVER
<br>Script kann beliebig oft ausgeführt werden und aktualisiert die Relationen
<hr>
<%
        // --- Initialize
        JaloSession jaloSession = WebSessionFunctions.getSession(request);
        WeraManager wm = WeraManager.getInstance();

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


        // --- Funktion 1: Hole alle obersten Klassen des Masterkataloges  ---------------------------------

        final CatalogVersion oWeramasterCV = wm.getCatalogVersion("weracatalog", "weramaster");
        Category oRootCategory = CatalogManager.getInstance().getCatalogCategory(oWeramasterCV, "root" );
        Collection<Category> colSubCategories = (Collection<Category>) wm.getAttribute ( oRootCategory, "categories" );
        // --- Funktion 1: Hole alle obersten Klassen des Masterkataloges  ---------------------------------



        // --- Funktion 2: Zuordnung der Produkte zu den klassifizierenden Feature-Klassen  ----------------
        final ClassificationSystemVersion oWeramasterCSV = wm.getClassificationSystemVersion("weraclassification", "werazusatz");

        // --- initialize
        HashMap<String,WeraProduct> hashProducts = new HashMap();

        // --- schleife über alle kategorien des masterkataloges
        for ( Category oCategory : colSubCategories ) {
            
            // --- initialize
            String strFeatureClassName = "FEATURES_" + oCategory.getCode();

            // --- clear product-hash
            hashProducts.clear();

            // --- prüfe ob die klassifizierende Kategorie existiert
            out.println( "<br>Searching |<b>" + strFeatureClassName + "</b>| ... " );
            ClassificationClass oCurrentClass = (ClassificationClass)CatalogManager.getInstance().getCatalogCategory( oWeramasterCSV, strFeatureClassName );
            if ( oCurrentClass != null ) {
                // --- ok, success
                out.println("<br><span style='color:green;'>Klassifizierende Klasse " + strFeatureClassName + " found. - Go for it and have fun!</span>" );
                out.println("<br>Artikel werden nun zugeordnet." );
                
                // --- Hole alle Unterkategorien
                Collection<Category> colAllSubCategories = (Collection<Category>)oCategory.getAllSubcategories();

                // --- Prüfe ob an der Hauptkategorie Produkte hängen
                if ( colAllSubCategories == null || colAllSubCategories.size() == 0 ) {

                    // --- Produkte hängen an der Hauptkategorie (z.B. Edelstahl)
                    out.println("<br><span style='color:orange;'>Hauptkategorie enthält bereits Artikel.</span>" );

                    // --- iterate on all products
                    Collection<WeraProduct> colProducts = (Collection<WeraProduct>) wm.getAttribute ( oCategory, "products" );
                    for ( WeraProduct oWeraProduct : colProducts ) {
                        if ( !hashProducts.containsKey(oWeraProduct.getPK().toString()) ) {
                            out.println("<br>Product=>" + oWeraProduct.getCode() + "< added." );
                            hashProducts.put(oWeraProduct.getPK().toString(), oWeraProduct );
                        }
                    }

                } else {

                    // --- schleife über alle Kategorien
                    for ( Category oSubCategory : colAllSubCategories ) {

                        // --- hole alle Produkte des aktuellen Katagorie-Nodes
                        Collection<WeraProduct> colProducts = (Collection<WeraProduct>) wm.getAttribute ( oSubCategory, "products" );
                        if ( colProducts != null && colProducts.size() > 0 ) {
                            // --- iterate on all products
                            for ( WeraProduct oWeraProduct : colProducts ) {
                                if ( !hashProducts.containsKey(oWeraProduct.getPK().toString()) ) {
                                    out.println("<br>Product=>" + oWeraProduct.getCode() + "< added." );
                                    hashProducts.put(oWeraProduct.getPK().toString(), oWeraProduct );
                                }
                            }
                        }

                        // --- weise die Produkte der klassifizierenden Kategorie zu
                    } // --- for ( Category oCategory : oCategory.getAllSubcategories() ) 
                }


                
                // --- get the unique-product-list
                if ( hashProducts != null && hashProducts.size() > 0 ) {
                    Collection colProducts = new ArrayList();
                    colProducts.addAll(hashProducts.values());
                    if ( colProducts.size() > 0 ) {
                        wm.setAttribute ( oCurrentClass, "products", colProducts );
                    }
                }

                

            } else {
                // --- ignore category
                out.println("<br><span style='color:red;'>SKIP=>" + strFeatureClassName + "< has not been created.</span>" );
            }
            out.println("<br>##<br>##" );

        } // --- for ( Category oRootCat : colSubCategories ) {

        // --- Funktion 2: Zuordnung der Produkte zu den klassifizierenden Feature-Klassen  ----------------
	     
 
		
%>
 

<hr><br>
<!-- ************************************* STEP 4 ************************************* -->
</body>
</html>

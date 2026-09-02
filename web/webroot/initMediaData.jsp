<!-- 
Bildbreiten % ermitteln und übertragen
Datum: 26.03.15 GT
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
<h2>Bildbreiten % ermitteln und übertragen</h2>
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

        // --- initialize
        HashMap<String,WeraProduct> hashProducts = new HashMap();
        HashMap<String,Double> hashTemplates = new HashMap();

        // --- schleife über alle kategorien des masterkataloges
        for ( Category oCategory : colSubCategories ) {
            
            // --- initialize

            // --- ok, success

            Collection<Category> colAllSubCategories = (Collection<Category>)oCategory.getAllSubcategories();
            for ( Category oSubCategory : colAllSubCategories ) {

                // --- hole alle Produkte des aktuellen Katagorie-Nodes
                Collection<WeraProduct> colProducts = (Collection<WeraProduct>) wm.getAttribute ( oSubCategory, "products" );
                if ( colProducts != null && colProducts.size() > 0 ) {
                    // --- iterate on all products
                    for ( WeraProduct oWeraProduct : colProducts ) {
                        if ( !hashProducts.containsKey(oWeraProduct.getPK().toString()) ) {
                            out.println("<br>Product=>" + oWeraProduct.getCode() + "< added." );
                            hashProducts.put(oWeraProduct.getPK().toString(), oWeraProduct );

                            // --- hole Ausgabetemplate
                            String strOutputtemplate = (String) wm.getAttribute ( oWeraProduct, "outputtemplate" );
                            if ( strOutputtemplate != null && strOutputtemplate.length() > 0 ) {
                                
                                hashTemplates.put(strOutputtemplate, new Double(0) );

                                // --- image I
                                Collection<WeraMedia> colProductMedia1 = (Collection<WeraMedia>) wm.getAttribute ( oWeraProduct, "pictures1" );
                                if ( colProductMedia1 != null && colProductMedia1.size() > 0 ) {
                                    WeraMedia oWeraMedia = (WeraMedia)colProductMedia1.iterator().next();

                                    if ( strOutputtemplate.equals("SHORTPIC85") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC85") ) {
                                        out.println("<br>%=85." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(85) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC75") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC75") ) {
                                        out.println("<br>%=75." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(75) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC65") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC65") ) {
                                        out.println("<br>%=65." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(65) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC55") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC55") ) {
                                        out.println("<br>%=55." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(55) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC45") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC45") ) {
                                        out.println("<br>%=45." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(45*1.1) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC35") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC35") ) {
                                        out.println("<br>%=35." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(35*1.3) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC25") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC25") ) {
                                        out.println("<br>%=25." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(25*1.4) );
                                    }

                                }

                                // --- image II
                                Collection<WeraMedia> colProductMedia2 = (Collection<WeraMedia>) wm.getAttribute ( oWeraProduct, "others_productpictures" );
                                if ( colProductMedia2 != null && colProductMedia2.size() > 0 ) {
                                    WeraMedia oWeraMedia = (WeraMedia)colProductMedia2.iterator().next();

                                    if ( strOutputtemplate.equals("SHORTPIC85") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC85") ) {
                                        out.println("<br>%=85." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(85) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC75") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC75") ) {
                                        out.println("<br>%=75." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(75) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC65") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC65") ) {
                                        out.println("<br>%=65." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(65) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC55") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC55") ) {
                                        out.println("<br>%=55." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(55) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC45") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC45") ) {
                                        out.println("<br>%=45." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(45*1.1) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC35") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC35") ) {
                                        out.println("<br>%=35." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(35*1.3) );
                                    }
                                    if ( strOutputtemplate.equals("SHORTPIC25") || strOutputtemplate.equals("COLOREDPRODUCT_SHORTPIC25") ) {
                                        out.println("<br>%=25." );
                                        wm.setAttribute ( oWeraMedia, "rahmenbreite_in_percent", new Double(25*1.4) );
                                    }
                                }

                            } // --- if ( strOutputtemplate != null && strOutputtemplate.length() > 0 ) {
                            
                        }
                    }
                }

                // --- weise die Produkte der klassifizierenden Kategorie zu
            } // --- for ( Category oCategory : oCategory.getAllSubcategories() ) 

            out.println("<br>##" );

        } // --- for ( Category oRootCat : colSubCategories ) {

        // --- Funktion 2: Zuordnung der Produkte zu den klassifizierenden Feature-Klassen  ----------------
	     
%>
 

<hr><br>
<!-- ************************************* STEP 4 ************************************* -->
</body>
</html>

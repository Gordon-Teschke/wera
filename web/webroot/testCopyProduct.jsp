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
<H1>Produkt kopieren</H1>
<%
// --- Initialize
JaloSession jaloSession = WebSessionFunctions.getSession(request);
WeraProductCopy wm = new WeraProductCopy();

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

// --- Initialize
long lPK_102          = 8796093218817L; // --- 102
long lPK_testsetinset = 8796387966977L; // --- testsetinset
long lPK_334_6        = 8796219408385L; // --- 334_6
long lPK              = lPK_102;
Product oSrcproduct   = (Product)jaloSession.getItem( PK.fromLong(lPK) );

// --- Produktcode initialisieren		
String strPostFix        = "0202.1"; 
String strProduktCode    = oSrcproduct.getCode();
String strProduktCodeNeu = strProduktCode + "_" + strPostFix; 

// --- Hier gehts los
if ( request.getParameter("do") != null && request.getParameter("do").equals("testCopyProduct") ) {
		
        // --- Initialize
		strPostFix        = request.getParameter("postfix"); 
		strProduktCodeNeu = strProduktCode + "_" + strPostFix; 
		boolean bExportReady = false;
		String sResult = "";
        String strTemp = ""; 
        String strExportResult   = ""; 
		String strLanguage       = request.getParameter("language");
%>
<P>
Produkt kopieren l&auml;uft ...<br>
<br>Sprache: <%= strLanguage %>
<br>ProduktCodeNeu: <%= strProduktCodeNeu %>
</P>
<%
        // --- Setze Parameter für Clone TEST -------------------------------------------------
		Collection colVariants    = (Collection) wm.getAttribute( oSrcproduct, "variants");
		HashMap hashArticle = new HashMap();
		for (Iterator it2 = colVariants.iterator(); it2.hasNext();) {
			// --- Hole Name
			Product oVariant = (Product) it2.next();
			out.println("<br>ARTCILE=" + oVariant.getCode() + ", NEU=" + request.getParameter("articleneu['" + oVariant.getCode() + "']"));
			hashArticle.put(oVariant.getCode(),request.getParameter( "articleneu['" + oVariant.getCode() + "']") );
		}
		if ( wm.bCloneProduct( oSrcproduct, strProduktCodeNeu, hashArticle) ) {
			Product oDestproduct = wm.getClonedProduct();
			out.println( "<br><br>Kopieren erfolgreich<br>Neues Produkt:<br>Produktnummer=" + oDestproduct.getCode() );
		} else {
			// --- Gibt das Fehlerprotokoll zurück
			ArrayList<String> aResultList = wm.getErrorResult();
			out.println( "<br><br><u>Fehlerliste:</u><br>" );
			for ( Iterator itResult = aResultList.iterator(); itResult.hasNext(); ) {
				String strResult = (String)itResult.next();
				out.println( "*&nbsp;" + strResult + "<br>" );
			}
		}
        // --- Setze Parameter für Clone TEST -------------------------------------------------

		
 } // --- if ( request.getAttribute("do") != null && request.getAttribute("do").equals("testCopyProduct") ) { ...
%>

<hr><br>
<!-- ************************************* STEP 4 ************************************* -->
<form action="http://localhost:10001/wera/testCopyProduct.jsp" method="get" enctype="multipart/form-data" >
	<INPUT type="hidden" name="do"              value="testCopyProduct" />
	<INPUT type="hidden" name="language"        value="de" />
	<INPUT type="hidden" name="catalog"         value="weracatalog" />
	<INPUT type="hidden" name="catalogversion"  value="weramaster" />
		
<p>
<br>Postfix:&nbsp;-&nbsp;<input style="width:200px;" type="text" name="postfix" value="<%= strPostFix %>" />
</p>
<%
		Collection colVariants    = (Collection) wm.getAttribute( oSrcproduct, "variants");
		for (Iterator it2 = colVariants.iterator(); it2.hasNext();) {
			// --- Hole Name
			Product oVariant = (Product) it2.next();
%>
<br>Variantcode neu:<%= oVariant.getCode() %>&nbsp;-&nbsp;<input style="width:200px;" type="text" name="articleneu['<%= oVariant.getCode() %>']" value="<%= oVariant.getCode() + "_" + strPostFix %>"" />
<%
		}
%>

	

<p style="padding:0px;border:0x;margin:0px;margin-top:30px; border: 0px solid red;">
	<input style="width:200px;" type="submit" value=" Produkt <%= strProduktCode %> kopieren "/>
</p>
</form>

</body>
</html>

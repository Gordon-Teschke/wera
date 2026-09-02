<%@page import="de.hybris.jalo.jalo.*"%>
<%@page import="de.hybris.platform.jalo.product.*"%>
<%@page language="java" import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.computationaldesign.wera.jalo.*"%>
<%@page import="com.computationaldesign.wera.jalo.WeraManager"%>
<%@page import="com.computationaldesign.wera.jalo.WeraKatalog"%>
<%@page import="com.computationaldesign.wera.jalo.DataCorrector"%>
<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.util.WebSessionFunctions"%>
<%@page import="de.hybris.platform.category.jalo.Category"%>
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
<%@page import="de.hybris.platform.jalo.security.JaloSecurityException"%>
<%@page import="de.hybris.platform.jalo.c2l.C2LManager"%>
<%@page import="de.hybris.platform.jalo.c2l.Language"%>
<%@page import="de.hybris.platform.catalog.jalo.CatalogVersion"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<%
		String path = request.getContextPath();
		String basePath = request.getScheme() + "://" + request.getServerName()
				+ ":" + request.getServerPort() + path + "/";
				
        // --- Initialize
		JaloSession jaloSession = WebSessionFunctions.getSession(request);
		WebSiteXml oWebSiteExport = new WebSiteXml();
		WeraManager wm = WeraManager.getInstance();
		
        // --- Initialize
		boolean bExportReady = false;
        String strTemp = ""; 
        String strExportResult = ""; 


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
<style type="text/css">
	body {
		font-family: Arial, helvetica;
	}
	table#replacement th {
		color:#BDD9FF;
		text-align:left;
		font-weight: bold;
		background-color: #0B50F3;
		font-size:11px;
		padding:3px;
	}
	table#replacement td {
		color:#0B50F3;
		text-align:left;
		font-weight: normal;
		background-color: #FFFFFF;
		vertical-align: top;
		font-size:11px;
		padding:3px;
	}
	.tooltip {
	  position: absolute;
	  display: none;
	  background-color: #dddddd;
	  color: black;
	  border: 1px solid black;
	  padding: 5px;
	  font-family: Arial, helvetica;
	  font-weight: normal;
	}
	a {
		text-decoration:none;
		color:#BDD9FF;
	}
</style>
<script type="text/javascript">
<!--
wmtt = null;
document.onmousemove = updateWMTT;
function updateWMTT(e) {
  if (wmtt != null) {
    x = (document.all) ? window.event.x + wmtt.offsetParent.scrollLeft : e.pageX;
    y = (document.all) ? window.event.y + wmtt.offsetParent.scrollTop  : e.pageY;
    wmtt.style.left = (x + 20) + "px";
    wmtt.style.top   = (y + 0) + "px";
  }
}
function showWMTT(id) {
  wmtt = document.getElementById(id);
  wmtt.style.display = "block"
}
function hideWMTT() {
  wmtt.style.display = "none";
}
// -->
</script>
</head>

<body marginheight="0" marginwidth="0" leftmargin="0" topmargin="0" bgcolor="#ffffff" text="#BDD9FF" width="100%">
<div style="height: 120px; vertical-align: middle; margin:0px; padding:0;border:0px;background-color:#0B50F3; text-align:right;font-size:24px; font-weight:bold; color:#ffffff;">
<div style="vertical-align: bottom; align: left; float: left; margin:20px;padding:0;border:0;background-color:#0B50F3; text-align:right;font-size:24px; font-weight:bold; color:#ffffff;">WERA Product image status</div>
<div style="align: right; margin:20px;padding:0;border:0;background-color:#0B50F3; text-align:right;font-size:24px; font-weight:bold; color:#ffffff;"><img src="images/e-business_platform.gif" /><br/>WERA - Edition</div></div>
<div style="margin:0px;padding:0px;border:0px;background-color:#ffffff; text-align:left;font-size:12px; font-weight:bold; color:#0B50F3;">


<br/>

<div id="1" class="tooltip">The product code as defined in the hybris platform.</div> 
<div id="2" class="tooltip">The <strong>expected EPS filename</strong> is the filename the WeraLive expects when determining availability of medias for this product. It is directly derived from the current product code.<br/>The <strong>assigned EPS filename</strong> in hybris should always match the expected EPS filename.<br/>If both filenames do not match, the erranous assigned name will be displayed.
<br/><br/>For technical reference, the derivation rules for determining the expected EPS filename are as follows:<br/>
<ul>
<li>Any occurrence of SPACE,'/','#' or '+' is replaced by an underscore "_".</li>
<li>Any occurrence of a Registered trademark sign is ignored.</li>
<li>'&ouml;','&auml;','&uuml;' and '&szlig;' are replaced by "oe", "ae", "ue" and "ss", respectively.</li>
<li>All other characters are taken as is.</li>
</ul>
</div>
<div id="3" class="tooltip">Shows, if the physical EPS file is really found on the hard disk for the expected EPS filename.<br/>If the expected and assigned names do not match, it shows if the assigned EPS file exists.</div>
<div id="4" class="tooltip">Shows, if the physical 300dpi JPG file is really found on the hard disk.<br/>If not (but the expected EPS file exists), it will be auto-generated whenever the product detail page is viewed.</div>
<div id="5" class="tooltip">Shows, if the physical 300dpi TIF file is really found on the hard disk.<br/>If not (but the expected EPS file exists), it will be auto-generated whenever the product's PDF file is requested.</div>
<div id="6" class="tooltip">If checked, the list will only show products with problems related to missing expected EPS files.</div>

<%
	HashMap productMap = new HashMap();

	String epsDir = "/home/hybris/imagepool/pictures/";
	String NexMartDir = "/home/hybris/nexmart/imagepool/";
	String jpg300dpiDir = "/home/hybris/imagepool/pictures/jpg/";
	String tif300dpiDir = "/home/hybris/imagepool/pictures/tif/";
	
	String checkIcon = "images/Check-icon.png";
	String deleteIcon = "images/Delete-icon.png";
	
	String mode = new String("all");

	String userCatalogVersion = ( request.getParameter("catalogversion") != null ) ? request.getParameter("catalogversion") : "print/print_2011";
	boolean bProblemsOnly = ( request.getParameter("problemsonly") != null ) && ( request.getParameter("problemsonly").equals("on") );
	boolean bNexMartOnly = ( request.getParameter("nexmartonly") != null ) && ( request.getParameter("nexmartonly").equals("on") );
	String userSuppliedProductCode = ( request.getParameter("usersuppliedproduct") != null ) ? request.getParameter("usersuppliedproduct") : "";
	
	
	out.println("<form name=\"weraproductform\" action=\"./ImageStatus.jsp\" method=\"POST\">");
	
	// --- Katalogversion auswählen
	out.println("Katalogversion:&nbsp;<select name='catalogversion'>");
	Set versions = (Set) CatalogManager.getInstance().getAllCatalogVersions();
	for ( Iterator it1 = versions.iterator(); it1.hasNext(); ) {
		CatalogVersion cv = (CatalogVersion) it1.next();
		String strCatalogValue      = cv.getCatalog().getId() + "/" + cv.getVersion();
		
		if ( strCatalogValue.equals(userCatalogVersion) ) 
			out.println("<option selected value='" + strCatalogValue + "'>" + strCatalogValue + "</option>" );
		else
			out.println("<option value='"  + strCatalogValue + "'>"  + strCatalogValue + "</option>" );
	}
	out.println("</select>");
	
	
	out.println("<input type=\"text\" name=\"usersuppliedproduct\" value=\""+userSuppliedProductCode+"\" /> Request image name for product code.<br/>");
	if ( userSuppliedProductCode != "" ) {
		out.println ("The product with code <span style=\"color:red;\"> "+userSuppliedProductCode+"</span> translates to image name: <span style=\"color:red;\">"+WeraProduct.s_normalizeFilenameForImageLookup(userSuppliedProductCode) +".eps</span><br/><br/>" );
	}
	out.println("<input type=\"checkbox\" name=\"problemsonly\" value=\"on\" " + ( bProblemsOnly ? " checked=\"checked\" " : "" ) + "/> Show only problem cases related to WeraLive. <a onmouseover=\"showWMTT('6')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a>");
	out.println("<input type=\"checkbox\" name=\"nexmartonly\" value=\"on\" " + ( bNexMartOnly ? " checked=\"checked\" " : "" ) + "/> Check only NexMart. <a onmouseover=\"showWMTT('6')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a>");
	out.println("<input type=\"submit\" value=\"Update\" />");
	out.println("</form>");
	
	
	
	out.println("<table id='replacement' border='1' cellpadding='0' cellspacing='0' width='70%'>");	
	out.println("<tr>\n");
	out.println("<th>No.</th>");
	out.println("<th>Product Code <a onmouseover=\"showWMTT('1')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
	out.println("<th>EPS Name <a onmouseover=\"showWMTT('2')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a><br/><small>expected / <span style='color:red;'>(assigned)</small></span></th>");
	out.println("<th>EPS exists <a onmouseover=\"showWMTT('3')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a><br/><small>expected / <span style='color:red;'>(assigned)</small></span></th>");
	out.println("<th>JPG (300dpi) exists <a onmouseover=\"showWMTT('4')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
	out.println("<th>TIF (300dpi) exists <a onmouseover=\"showWMTT('5')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
	if ( bNexMartOnly ) {
		out.println("<th>NexMart (Thumb) exists <a onmouseover=\"showWMTT('5')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
		out.println("<th>NexMart (Normal) exists <a onmouseover=\"showWMTT('5')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
		out.println("<th>NexMart (Zoom) exists <a onmouseover=\"showWMTT('5')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
	} else {
		out.println("<th>Web (100px) exists <a onmouseover=\"showWMTT('5')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
		out.println("<th>Web (260px) exists <a onmouseover=\"showWMTT('5')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
		out.println("<th>Web (380px) exists <a onmouseover=\"showWMTT('5')\" onmouseout=\"hideWMTT()\" href=\"#\"><img src=\"images/Help-icon.png\" border=\"0\"></a></th>");
	}
	out.println("</tr>\n");
	
	// --- Holen der Produkte
//	Collection products = wm.getProducts();
	Collection products = (Collection) WeraProductSet.getAllInstances();
	WeraKatalog oWerakatalog = new WeraKatalog();
	String[] aCatalogVersion = userCatalogVersion.split("/");
	System.out.println(aCatalogVersion[0]);
	System.out.println(aCatalogVersion[1]);
	Collection colProductData = oWerakatalog.getProductsFromCatalogVersion ( "weracatalog", "weramaster", 1  );
	Collection productsWithoutMedia = new ArrayList();

	// --- Schleife über alle Daten (Produkt)
	String strLine;
	String strPK, strCode, strName;
	String strPK_Variant, strCode_Variant, strReihe_Variant, strBeschreibung_Variant;
	Integer oReihe_Variant = null;
	WeraVariante oVariante = null;
	int iCnt = 0;
	int iCntShown = 0;
	for ( Iterator it1 = colProductData.iterator(); it1.hasNext(); ) {
		HashMap hashData = (HashMap) it1.next();
		Product p = (Product) hashData.get("product");
			
		if ( p instanceof WeraProductSet  )  { 
			if ( p instanceof WeraProductSetinSet ) 
				continue;
				
			iCnt++;
			WeraProductSet weraproductset = (WeraProductSet) p;
			// --- FILTER WEB!!
			Collection categories= (Collection)wm.getAttribute( weraproductset, "supercategories" );
			if ( categories != null ) {
				for ( Iterator itcategories = categories.iterator(); itcategories.hasNext(); ) {
					Category oCategory = (Category)itcategories.next();
					CatalogVersion oCatalogVersion = (CatalogVersion) wm.getAttribute(oCategory,"catalogVersion");
					//System.out.println(oCatalogVersion.getVersion());
					if ( oCatalogVersion.getVersion().equals("web_de_2012") 
							|| oCatalogVersion.getVersion().equals("web_us_2012") 
							|| oCatalogVersion.getVersion().equals("web_uk_2012") ) {
						//out.print (oCategory.getCode());
						//out.print (oCatalogVersion.getVersion());
						// --- ok wir sind im web ;)
						break;
					} else 
						continue;
				}
			} // --- if ( categories != null ) {
			// --- FILTER WEB!!
			
			
			// --- initialize data
			strPK   = weraproductset.getPK().toString();
			strCode = weraproductset.getCode();
			strName = weraproductset.getName();
			if ( strName == null ) strName="";
			strLine = strPK + "##" + strCode + "##" + strName;
			
			// --- Hole alle Varianten
			Collection variants = (Collection)wm.getAttribute( weraproductset, "variants" );
			if ( variants != null ) {
				for ( Iterator itvariants = variants.iterator(); itvariants.hasNext(); ) {
					// --- get entry
					WeraVarianteSet weravarianteset = (WeraVarianteSet) itvariants.next();;
					strPK_Variant 			= weravarianteset.getPK().toString();
					oVariante 		        = (WeraVariante)wm.getAttribute( weravarianteset, "weravariants" );
					if ( oVariante != null )
						strCode_Variant 		= (String)wm.getAttribute( oVariante, "code" );
					else
						strCode_Variant 		= (String)"n/a";
					oReihe_Variant 		    = (Integer)wm.getAttribute( weravarianteset, "order" );
					if ( oReihe_Variant != null )
						strReihe_Variant 		= (String)oReihe_Variant.toString();
					else
						strReihe_Variant 		= "1";
					strBeschreibung_Variant = (String)wm.getAttribute( weravarianteset, "productdescription" );
					if ( strBeschreibung_Variant == null ) strBeschreibung_Variant="";
					
					out.print("<br>" + strPK_Variant + "##" + strLine + "##" + strCode_Variant + "##" + strReihe_Variant + "##" + strBeschreibung_Variant );
			
				} // --- for ( Iterator itvariants = variants.iterator(); itvariants.hasNext(); ) 
				//out.print("<br>" );
				
			} // --- if ( variants != null ) {
			
			
		} // --- if ( p instanceof WeraProductSet ) {

	} // --- for ( Iterator it1 = colProductData.iterator(); it1.hasNext(); ) {
	out.println("</table>");
	
	

%>

</div>

<!-- ************************************* STEP 4 ************************************* -->
</body>
</html>

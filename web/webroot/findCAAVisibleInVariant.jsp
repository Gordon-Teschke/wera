<%@page import="com.computationaldesign.wera.jalo.*"%>
<%@page import="de.hybris.jakarta.jalo.product.*"%>
<%@page import="de.hybris.platform.catalog.jalo.CatalogManager"%>
<%@page import="de.hybris.platform.catalog.jalo.classification.*"%>
<%@page import="de.hybris.platform.category.jalo.Category"%>
<%@page import="de.hybris.platform.jalo.JaloSession"%>
<%@page import="de.hybris.platform.catalog.jalo.CatalogManager"%>
<%@page import="de.hybris.platform.jalo.JaloBusinessException"%>
<%@page import="de.hybris.platform.jalo.JaloInvalidParameterException"%>
<%@page import="de.hybris.platform.jalo.security.JaloSecurityException"%>
<%@page import="de.hybris.platform.jalo.type.TypeManager"%>
<%@page import="de.hybris.platform.jalo.type.ComposedType"%>
<%@page import="de.hybris.platform.jalo.enumeration.EnumerationManager"%>
<%@page import="de.hybris.platform.jalo.enumeration.EnumerationValue"%>
<%@page import="de.hybris.platform.jalo.enumeration.EnumerationType"%>
<%@page import="de.hybris.platform.jalo.c2l.*"%>
<%@page import="de.hybris.platform.europe1.constants.GeneratedEurope1Constants.Enumerations.UserPriceGroup"%>

<%@page language="java" import="java.util.*"%>
<%@page import="java.io.*"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
	<title>Wera hybris empty units finder</title>
	<script type="text/javascript" src="javascript/jquery-1.4.2.min.js"></script>
</head>
<body>

<%
final class OrderComparator implements Comparator
{
	public int compare(final Object o1, final Object o2)
	{

		// --- Initialize
		String iValue1 = "";
		String iValue2 = "";
		ClassAttributeAssignment ca1 = null;
		ClassAttributeAssignment ca2 = null;
		String cmp1 = null;
		String cmp2 = null;
		try
		{
			// --- Hole Values
			ca1 = (ClassAttributeAssignment) o1;
			ca2 = (ClassAttributeAssignment) o2;
			
		}
		catch (final JaloInvalidParameterException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (ca1 == null || ca2 == null)
		{
			return 0;
		}
		else
		{
			cmp1 = ca1.getSystemVersion()+"|"+ca1.getClassificationClass().getCode()+"|"+ca1.getClassificationAttribute().getCode();
			cmp2 = ca2.getSystemVersion()+"|"+ca2.getClassificationClass().getCode()+"|"+ca2.getClassificationAttribute().getCode();
			return cmp1.compareTo(cmp2);
		}
	}
}

	boolean bChangeIt = ( request.getParameter("doit") != null ) && ( request.getParameter("doit").equals("1") );

	final JaloSession jalosession = JaloSession.getCurrentSession();
	jalosession.getSessionContext().setUser( jalosession.getUserManager().getAdminEmployee() );
	Language de = C2LManager.getInstance().getLanguageByIsoCode("de");
	jalosession.getSessionContext().setLanguage(de);
	

	final ClassificationSystem newCS = CatalogManager.getInstance().getClassificationSystem("weraclassification");
	TypeManager tm = TypeManager.getInstance();
	
	ComposedType typeCAA = tm.getComposedType("ClassAttributeAssignment");
	
	Set<ClassAttributeAssignment> setCAA = typeCAA.getAllInstances();
	
	List<ClassAttributeAssignment> listCAA = new ArrayList();
	listCAA.addAll(setCAA);
	Collections.sort(listCAA, new OrderComparator() );
	
	HashMap<String, Set<String>> mapA2U = new HashMap();
	EnumerationManager em = EnumerationManager.getInstance();
	final EnumerationType ClassificationAttributeVisibilityEnumType = em.getEnumerationType("ClassificationAttributeVisibilityEnum");	
	final EnumerationValue ev_viv = em.getEnumerationValue(ClassificationAttributeVisibilityEnumType, "visible_in_variant");
	final EnumerationValue ev_vib = em.getEnumerationValue(ClassificationAttributeVisibilityEnumType, "visible_in_base");
	
		
	for ( ClassAttributeAssignment caa : listCAA ) {
		ClassificationSystemVersion csv = caa.getSystemVersion();
		
		EnumerationValue ev = caa.getVisibility();
		
		if ( csv.getVersion().equals("werazusatz") || csv.getVersion().equals("3.0") ) {
			String sClassName = caa.getClassificationClass().getCode();
			String sAttributeName = caa.getClassificationAttribute().getCode();
			String sAttributeDesc = caa.getClassificationAttribute().getName();
			if ( caa.getVisibility().equals(ev_viv) ) {
				out.print(csv.getVersion()+", "+sClassName+", "+sAttributeName);
				if ( bChangeIt ) {
					caa.setVisibility(ev_vib);
					if ( caa.getVisibility().equals(ev_vib) ) {
						out.print(" => successfully changed to BASE visibility.");
					} else {
						out.print(" => ERROR changing to BASE visibility.");
					}
				}
				out.print("<br>");
			}
		}
	}
	out.print("DONE.");
		
		
%>
</body>
</html>
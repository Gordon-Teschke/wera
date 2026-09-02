<%--
	[y] hybris E-Business Platform
	
	Copyright (c) 2000-2004 hybris
	All rights reserved.
	
	This software is the confidential and proprietary information of hybris
	("Confidential Information").  You shall not dis such Confidential
	Information and shall use it only in accordance with the terms of the
	license agreement you entered into with hybris.

 	version $Revision: 1.1.2.2 $

<%@include file="../../xp_button.inc"%>
	<input type="hidden" name="<%= myCommandIdWV_PRICE %>" value="">
	<input type="hidden" name="<%= myCommandIdWV %>" value="">
--%>
<%@include file="../../head.inc"%>

<%@page import="com.computationaldesign.wera.hmc.ClassificationVariantListEntryChip,
				com.computationaldesign.wera.hmc.HMCClassificationHelper.ClassificationAttributeEntry,
				de.hybris.platform.catalog.jalo.ProductFeature" %>

<%
	final ClassificationVariantListEntryChip theChip = (ClassificationVariantListEntryChip) request.getAttribute(AbstractChip.CHIP_KEY);
	boolean isEditable = true;
	if( theChip.getParent() instanceof GenericItemListChip )
	{
		isEditable = ((GenericItemListChip) theChip.getParent()).isEditable();
	}
	
	if( !theChip.isElementReadable() )
	{
%>		
		<td colspan="<%= theChip.getAttributeCount() + 2 %>" <%= !isEditable ? "style=\"border-left:1px solid #bbbbbb;\"" : "" %>>
			<%= localized("elementnotreadable") %>
		</td>
<%
	}
	else
	{

	String myCommandId = theChip.getCommandID(ClassificationVariantListEntryChip.OPEN_FOOTNOTE);
	String myCommandIdWV_PRICE = theChip.getCommandID(ClassificationVariantListEntryChip.OPEN_FOOTNOTE_WERAVARIANTE_PRICE);
	String myCommandIdWV = theChip.getCommandID(ClassificationVariantListEntryChip.OPEN_FUNCTIONS_WERAVARIANTE);
%>		
										<td>
											<table class="gilcIcon" cellpadding="0" cellspacing="0">
												<tr>
													<td class="eilecIsChangedSign">
														<div>
															
														</div>
													</td>
													<td class="eilecIcon">
															<div class="button-on-white chip-event">

																	<a href="#" title="Editor öffnen" hidefocus="true"
																		name="<%=theChip.getCommandID(GenericItemListChip.OPEN_EDITOR)%>">

																		<span id="<%= theChip.getUniqueName() %>_span">
																			<img class="icon" src="images/icons/e_variantproduct.gif" id="<%= theChip.getUniqueName() %>_img"/>
																		</span>

																	</a>

															</div>
													</td>											
												</tr>
											</table>
										</td>
<%
		// all "normal" product attributes (which have been configured to be shown in this list)
		
	
		for (final Iterator it = theChip.getAttributeNodes().iterator(); it.hasNext(); )
		{
			AttributeNode an = (AttributeNode) it.next();
			String width = (an.getActualColumnWidth() == 0 || !it.hasNext()) ? "" : " style=\"width: " + an.getColumnWidth() + "px; overflow: hidden;\" ";
%>
			<td <%= width %>>
				<div <%= width %>>
<%
				if( theChip.getAttributeEditors().containsKey(an.getAttributeQualifier()) )
				{
				 boolean bOutput = true;
					String productPk = theChip.getProduct().getPK().toString();
					if( an.getAttributeQualifier().equals( "priceInfo" ) )
					{
						// getLink( theChip.getEventID(GenericItemListEntryChip.OPEN_EDITOR), localized("edit.prices") ) 
						%>
						 <a href="#"  
								onMouseout="window.status = ''; return true;" hidefocus="true" 
								onclick="document.editorForm.elements['<%= myCommandIdWV_PRICE %>'].value='<%= productPk %>'; setScrollAndSubmit(); return false;">
								<%= localized("edit.prices") %></a>
						<%
						bOutput = false;
					}
					if( an.getAttributeQualifier().equals( "f1" ) )
					{
						//getLink( theChip.getEventID(myCommandId), localized("fn") ) 
						// getLink( myCommandId, localized("fn") ) 
						%>
						 <a href="#"  
								onMouseout="window.status = ''; return true;" hidefocus="true" 
								onclick="document.editorForm.elements['<%= myCommandIdWV %>'].value='<%= productPk %>'; setScrollAndSubmit(); return false;">
								<%= localized("edit.f1") %></a>
						<%
						bOutput = false;
					}
                    if ( bOutput )
 					    theChip.getEditorForCurrentLanguage(an.getAttributeQualifier()).render(pageContext);
				}
%>			
				</div>
			</td>
<%
		}
%>
<%@ page import="de.hybris.platform.jalo.product.*" %>
<%@ page import="de.hybris.platform.catalog.jalo.Catalog.*" %>		
<%@ page import="de.hybris.platform.jalo.enumeration.EnumerationManager" %>		
<%@ page import="de.hybris.platform.jalo.enumeration.EnumerationType" %>		
<%@ page import="de.hybris.platform.jalo.enumeration.EnumerationValue" %>		
	
<%		
		// all classification attributes, dynamically derived for the current product
		for( final Iterator categoryIter = theChip.getClassificationMap().keySet().iterator(); categoryIter.hasNext(); )
		{
			for( final Iterator entryIter = ((Collection) theChip.getClassificationMap().get( categoryIter.next() )).iterator(); entryIter.hasNext(); )
			{
				final ClassificationAttributeEntry caEntry = (ClassificationAttributeEntry) entryIter.next();
				
				String productFeaturePk = "";
				Product product = caEntry.getProduct();
				Collection productFeatures = (Collection)product.getAttribute( "features" );
				for( Iterator it = productFeatures.iterator(); it.hasNext(); )
				{
					ProductFeature pf = (ProductFeature)it.next();
					String code = pf.getQualifier();
					if( code.equals( caEntry.getCode() ) )
					{
						productFeaturePk = pf.getPK().toString();
						break;
					}
				}	
%> 
				<td>
<%
					caEntry.getValueEditor().render(pageContext);
%>					
					<% if( productFeaturePk.length() > 0 ) { %>
						 <a class="normallink" href="#"  
								onMouseout="window.status = ''; return true;" hidefocus="true" 
								onclick="document.editorForm.elements['<%= myCommandId %>'].value='<%= productFeaturePk %>'; setScrollAndSubmit(); return false;">
								<!--  <%= localized("edit.footnotes") %> --></a>
					<% } %>
				</td>
<%
			}
		}
	}
%>

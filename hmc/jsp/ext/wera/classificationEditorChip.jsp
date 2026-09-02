<%@include file="../../head.inc"%>

<%@page import="com.computationaldesign.wera.hmc.ClassificationEditorChip,
					 com.computationaldesign.wera.hmc.HMCClassificationHelper.ClassificationAttributeEntry,
					 de.hybris.platform.catalog.jalo.classification.*,
					 de.hybris.platform.catalog.*,
					 de.hybris.platform.catalog.jalo.classification.ClassificationClass,
					 de.hybris.platform.catalog.jalo.ProductFeature" %>
<%
	final ClassificationEditorChip theChip = (ClassificationEditorChip) request.getAttribute(AbstractChip.CHIP_KEY);
	
	final String event = theChip.getCommandID(ClassificationEditorChip.OPEN_ATTRIBUTE);
	final String tooltip = localized("tab.catalog.classification.openattribute");

	// this tab is only active if there is a product
	if( theChip.getProduct() != null )
	{
%>
		<!-- hidden input field for opening single classificationattributes -->
		<input type="hidden" name="<%= event %>" value="" />

<%		for( final Iterator categoryIter = theChip.getClassificationMap().keySet().iterator(); categoryIter.hasNext(); )
		{
			final ClassificationClass classificationclass = (ClassificationClass) categoryIter.next();

			String classificationName = theChip.getClassificationName(classificationclass);
			if ( classificationName == null ) 
			    classificationName = localized("notdefined");
				
			final Collection classificationAttributes = (Collection) theChip.getClassificationMap().get(classificationclass);
			if( !classificationAttributes.isEmpty() )
			{
%>
			<!-- section header for each classification classificationclass -->
			<table>
				<tr>
					<td><div style="width:6px"/></td>
					<td class="sectionheader" <%= classificationAttributes.isEmpty() ? " style=\"border-bottom:0px; \"" : "" %>><%= classificationName %></td>
				</tr>
			</table>

				<table class="listtable" style="margin-left:20px; margin-top:10px; width:550px;" cellpadding="3px" cellspacing="0px">
					<tr style="text-align:left;">
						<th style="width:10px; border-right:0px;"><div style="width:10px;"></div></th>
						<th style="width:200px; padding-top:3px; padding-left:6px;"><div style="width:200px"><%= localized("tab.catalog.classification.name") %></div></th>
						<th style="border-right:1px solid #bbbbbb; padding-top:3px; padding-left:6px;"><%= localized("tab.catalog.classification.value") %></th>
						<th style="width:100px; border-left:0px; padding-top:3px;"><div style="width:100px;"><%= localized("tab.catalog.classification.code") %></div></th>
					</tr>
<%
				// show all classification attribute values for the current classification system and the enclosing product
				for( Iterator attributeIter = classificationAttributes.iterator(); attributeIter.hasNext(); )
				{
					final ClassificationAttributeEntry entry = ((ClassificationAttributeEntry) attributeIter.next());
					final String errorStyle = entry.getValueEditor().containsValidValue() ? "" : "style=\"color:red;\"";
%>	
					<tr>
						<td style="border-left:1px solid #bbbbbb;"><%= entry.isChanged() ? "*" : "" %></td>
						<td style="padding-left:8px;"><%= entry.isMandatory() ? "<b>" : "" %>
 							<a class="normallink" <%= errorStyle %> href="#" onMouseover="window.status = '<%= tooltip %>'; return true;" 
								onMouseout="window.status = ''; return true;" hidefocus="true" title="<%= tooltip %>"
								onclick="document.editorForm.elements['<%= event %>'].value='<%= entry.getAttributeAssignment().getPK().toString() %>'; setScrollAndSubmit(); return false;">
								<%= entry.getName() != null ? entry.getName() : localized("notdefined")%>
								<%= entry.getUnit() != null ? "(in " + entry.getUnit().getName() + ")" : "" %>
								<%= entry.isMandatory() ? "</b>" : "" %>
 							</a>
						</td>
						<td>
							<% entry.getValueEditor().render(pageContext); %>
							<%
							
							if( entry.getValueEditor() instanceof ReferenceMultiSelectEditorChip )
							{
							%>
							<a class="normallink" <%= errorStyle %> href="#" onMouseover="window.status = '<%= tooltip %>'; return true;" 
								onMouseout="window.status = ''; return true;" hidefocus="true" title="<%= tooltip %>"
								onclick="document.editorForm.elements['<%= event %>'].value='<%= entry.getAttributeAssignment().getPK().toString() %>'; setScrollAndSubmit(); return false;">
								<%= localized("add.customValues") %>
								</a>								
							<%
							}
							
							%>
							
						</td>						
						<td style="border-right:1px solid #bbbbbb; padding-left:6px;">
<!-- 
							<a class="normallink" <%= errorStyle %> href="#" onMouseover="window.status = '<%= tooltip %>'; return true;" 
								onMouseout="window.status = ''; return true;" hidefocus="true" title="<%= tooltip %>"
								onclick="document.editorForm.elements['<%= event %>'].value='<%= entry.getClassificationAttribute().getPK().toString() %>'; setScrollAndSubmit(); return false;">
-->
								<%= entry.getCode() %>
<!-- 							</a> -->
						</td>
					</tr>
<%
				}
%>
				</table>
<%
			}
		}   
//		if( false && !theChip.getUnboundFeatures().isEmpty() )
		if( !theChip.getUnboundFeatures().isEmpty() )
		{
%>
			<!-- unbound productfeatures -->
			<table style="margin-top:10px;">
				<tr>
					<td><div style="width:6px"/></td>
					<td class="sectionheader"><%= localized("section.catalog.unboundproductfeatures") %></td>
				</tr>
			</table>
			<table style="margin-left:23px; margin-top:10px;" cellpadding="3px" cellspacing="0px" border="0px">
				<tr style="text-align:left;">
					<th style="width:100px;"><div style="width:100px"><%= localized("tab.catalog.classification.code") %></div></th>
					<th style="width:150px;"><div style="width:150px"><%= localized("tab.catalog.classification.value") %></div></th>
				</tr>
<%
				for( final Iterator unboundIter = theChip.getUnboundFeatures().iterator(); unboundIter.hasNext(); )
				{
					final ProductFeature feature = ((ProductFeature) unboundIter.next());
					
					final String code = feature.getQualifier();
					final StringBuffer values = new StringBuffer();
/* ACHTUNG!!!
					if( feature.getValues() != null )
					{
						for( final Iterator iter = feature.getValues().iterator(); iter.hasNext(); )
						{
							values.append(iter.next());
							if( iter.hasNext() )
							{
								values.append(", ");
							}
						}
					}
*/					
					final String description = feature.getDescription();
						
%>
					<tr style="text-align:left;">
						<td><%= code %></td>
						<td><%= values.toString() %></td>
						<td><%= description != null ? description : localized("notdefined") %></td>
					</tr>
<%
				}
%>
			</table>
<%
		}
	}
%>
<div style="height:10px;"/>
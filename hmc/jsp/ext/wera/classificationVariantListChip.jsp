<%--
	[y] hybris E-Business Platform
	
	Copyright (c) 2000-2004 hybris
	All rights reserved.
	
	This software is the confidential and proprietary information of hybris
	("Confidential Information").  You shall not dis such Confidential
	Information and shall use it only in accordance with the terms of the
	license agreement you entered into with hybris.

 	version $Revision: 1.1.2.1 $
<table class="genericItemListChip" style="width:400px;height:40px;" cellspacing="0" cellpadding="0" oncontextmenu="(new Menu([ new MenuEntry('Neu anlegen', 'null', 'null', true, 'images/icons/list_menu_create.gif', [ new MenuEntry('Artikel             ', '<%=theChip.getCommandID(GenericItemListChip.CREATE)%>', 'WeraVariante', true, 'images/icons/e_variantproduct.gif', null, '', true), new MenuEntry('&amp;nbsp;&amp;nbsp;Artikel             ', '<%=theChip.getCommandID(GenericItemListChip.CREATE)%>', 'WeraVarianteSet', true, 'images/icons/e_variantproduct.gif', null, '', true) ], '', true) , { uniqueName: '<%= theChip.getUniqueName() %>'} )).show(); return false;" >
	<tr>
		<td>
		</td>
	</tr>
</table>
--%>
<%@include file="../../head.inc"%>

<%@page import="com.computationaldesign.wera.hmc.ClassificationVariantListChip" %>
<%@page import="com.computationaldesign.wera.jalo.WeraProductSet" %>
<%@page import="de.hybris.platform.jalo.product.Product" %>

<%
	final ClassificationVariantListChip theChip = (ClassificationVariantListChip) request.getAttribute(AbstractChip.CHIP_KEY);
	
	int width = theChip.getWidth();
	
	if( false && ((!theChip.isEditable() || theChip.getToolbar().isEmpty()) && theChip.getListEntries().isEmpty()) )
	{
		%>
		<div class="disabled" ><%=localized("listisempty")%></div>
		<%
	}
	else
	{
		final boolean showScrollbars = Config.getBoolean("hmc.enable.scrollbar",true);
/* TODO: later product Type
		Product theProduct = (Product)theChip.getEntry(0).getProduct();
		String strVariantenType = "";
		String strVariantenTypeDesc = "";
		if ( theProduct instanceof WeraProductSet ) {
			strVariantenType = "WeraVarianteSet";
			strVariantenTypeDesc = "Variante (Satz)";
		}
		else {
			strVariantenType = "WeraVariante";
			strVariantenTypeDesc = "Variante (Satz)";
		}
*/		
%>
<table class="genericItemListChip" cellspacing="0" cellpadding="0" oncontextmenu="(
new Menu([ new MenuEntry('In neuem Fenster öffnen', '<%=theChip.getCommandID(GenericItemListChip.OPEN_EDITOR)%>', 'true', true, 'images/icons/list_menu_open.gif', null, '', true), 
new MenuEntry('Öffnen', '<%=theChip.getCommandID(GenericItemListChip.OPEN_EDITOR_INTERNAL )%>', 'true', true, 'images/icons/list_menu_open.gif', null, '', true), 
new MenuEntry('Hinzufügen', '<%=theChip.getCommandID(GenericItemListChip.ADD)%>', 'true', true, 'images/icons/list_menu_add.gif', null, '', false), 
new MenuEntry('Neu anlegen', 'null', 'null', true, 'images/icons/list_menu_create.gif', [ new MenuEntry('Artikel             ', '<%=theChip.getCommandID(GenericItemListChip.CREATE)%>',  'WeraVariante', true, 'images/icons/e_variantproduct.gif', null, '', true), new MenuEntry('&amp;nbsp;&amp;nbsp;Artikel (Satz)', '<%=theChip.getCommandID(GenericItemListChip.CREATE)%>',  'WeraVarianteSet', true, 'images/icons/e_variantproduct.gif', null, '', true), new MenuEntry('&amp;nbsp;&amp;nbsp;Artikel (Satz in Satz)', '<%=theChip.getCommandID(GenericItemListChip.CREATE)%>',  'WeraProductSetVariants', true, 'images/icons/e_variantproduct.gif', null, '', true) ], '', true), 
new MenuEntry('Entfernen', '<%=theChip.getCommandID(GenericItemListChip.REMOVE)%>', 'true', true, 'images/icons/list_menu_remove.gif', null, 'Wollen Sie die markierten Einträge wirklich löschen?', true), 
new MenuSplitter('<hr class=\'splitter\' size=\'1\' noshade>'), 
new MenuEntry('Alle auswählen', '<%=theChip.getCommandID(GenericItemListChip.SELECT_VISIBLE)%>', 'true', true, 'images/icons/list_menu_select_all.gif', null, '', true), 
new MenuEntry('Alles abwählen', '<%=theChip.getCommandID(GenericItemListChip.SELECT_VISIBLE)%>', 'true', true, 'images/icons/list_menu_deselect_all.gif', null, '', true), 
new MenuEntry('Kopieren', '<%=theChip.getCommandID(GenericItemListChip.COPY_EVENT)%>', 'true', true, 'images/icons/list_menu_copy.gif', null, '', true) ], event, null, null, { uniqueName: '<%= theChip.getUniqueName() %>'} )).show(); return false;" >
				<tr>
					<td>
						<div style="width:100%;overflow:auto;" id="resultlist_<%= theChip.getUniqueName() %>">
						<%
								if( showScrollbars && theChip.getListEntryCount()>12 )
								{
						%>
							<script language="JavaScript1.2">
								document.getElementById( "resultlist_<%= theChip.getUniqueName() %>" ).style.height=260;
							</script>
						<%
								}
						%>
						<table id="table_<%= theChip.getUniqueName() %>_table" class="listtable selecttable" cellpadding="0" cellspacing="0">
								<tr>
								<!-- gilcHeaderCheckbox -->
								<th class="checkbox gilcCheckbox">
									<div>
										<input name="SELECTOR" class="header"  onclick="document.editorForm.elements['<%=theChip.getCommandID(GenericItemListChip.SELECT_VISIBLE)%>'].value='true';setScrollAndSubmit();" type="checkbox" name="" value="ALL" />
										<input type="hidden" name="<%=theChip.getCommandID(GenericItemListChip.SELECT_VISIBLE)%>" value=""/>
										<input type="hidden" name="<%=theChip.getCommandID("mark")%>" value="" />   <!-- dummy event to allow lists to be completely de-selected -->
									</div>
								</th>
								<!-- gilcHeaderIcon -->
								<th class="gilcIcon" style="border-left:0px;">
									<div class="gilcIcon">&nbsp;</div>
								</th>

<%
		// normal attributes
		for( final Iterator iter = theChip.getAttributeNodes().iterator(); iter.hasNext(); )
		{
			final AttributeNode attributeNode = (AttributeNode) iter.next();
			final String qualifier = attributeNode.getAttributeQualifier();
			final int attributeWidth = attributeNode.getActualColumnWidth();
				
%>
									<th class="gilcEntry" ><div><%= theChip.getTitle(qualifier) %></div></th>
<%
		}
		
		// classification attributes
		if( theChip.getClassificationAttributeNames() != null )
		{
			for( final Iterator iter = theChip.getClassificationAttributeNames().iterator(); iter.hasNext(); )
			{
%>
									<th class="gilcEntry" ><div><%=(String) iter.next() %></div></th>
<%
			}
		}
%>
								</tr>
<%		
		if( theChip.getListEntries().isEmpty() && theChip.getNewItemEntry() == null )
		{
%>
								<tr>
									<td class="disabled" width="100%" colspan="<%= theChip.getHeaderCount() + 3 %>" style="border: 1px solid #bbbbbb">
										<div class="disabled"><%= localized( "listisempty" ) %></div>
									</td>
								</tr>
<%
		}
		else
		{
			List removedEntries = new ArrayList();
            int iCheckBox = 0;
			for (final Iterator theChips = theChip.getRestrictedListEntries().iterator(); theChips.hasNext();)
			{
				GenericItemListEntryChip chip = (GenericItemListEntryChip) theChips.next();
				if( (chip.getItem() == null) ||chip.getItem().isAlive() )
				{
%>
								<tr id="<%= chip.getUniqueName() %>_tr" class="doubleclick-event <%=chip.getCommandID("edit")%>" onclick="">	
									<!-- gilcCheckbox -->
										<td class="checkbox gilcCheckbox" id="<%= theChip.getUniqueName() %>_td" >
										<input type="checkbox" 
												 name="<%=theChip.getCommandID("mark")%>" 
												 value="<%=iCheckBox%>" />
										</td>
<%
										// --- Nächste Checkbox
										iCheckBox++;
					chip.render(pageContext);
%>
								</tr>
<%
				}
				else
				{
					removedEntries.add(chip);
				}
			}

			for( final Iterator removedIter = removedEntries.iterator(); removedIter.hasNext(); )
			{
				Chip chipToRemove = (Chip) removedIter.next();
				theChip.removeListEntry(chipToRemove);
			}
		}
		if( theChip.getNewItemEntry() != null )
		{
			final CreateItemListEntryChip chip = theChip.getNewItemEntry();
			// there is a new entry
%>
			<tr>
				<td style="width:20px; border-left:1px solid #bbbbbb; padding:0px;">
					<div style="width:20px;"></div>
				</td>
<%
					chip.render(pageContext);
%>
			</tr>
<%
		}
%>
							</table>
						</div>
					</td>
				</tr>
<%
				int maxCount = theChip.getMaxCount();
				int totalCount = theChip.getListEntryCount();
				boolean showRange = (maxCount != 0) && (maxCount < totalCount);
%>
				<tr>
					<td>
						<!-- grey footer bar -->
						<table class="footer" cellspacing="0" cellpadding="0" <%= showRange ? "" : "style=\"font-size:1pt;\"" %>>
							<tr height="7px">
								<td style="text-align: left; vertical-align:bottom; width: 7px;"><img src="images/editortab_corner_bl.gif"/></td>
<%
								if( showRange )
								{
%>
									<td style="white-space:nowrap; height:20px; vertical-align:middle; font-size:8pt;">1 - <%= maxCount %> <%=localized("searchlist.of")%> <%= totalCount %>&nbsp;&nbsp;</td>
<%
								}
%>									
								<td width="100%"></td>
								<td style="text-align: right; vertical-align:bottom; width: 7px;"><img src="images/editortab_corner_br.gif"/></td>
							</tr>
						</table>
					</td>
				</tr>
   
			</table>
<%
	}
%>

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

	
JSP	JAVA
artikelClassificationVariantListChip.jsp 		=> ArtikelClassificationVariantListChip.java
artikelClassificationVariantListEntryChip.jsp 	=> ArtikelClassificationVariantListEntryChip.java
ArtikelClassificationVariantListEditorChip.java

--%>
<%@include file="../../head.inc"%>

<%@page import="com.computationaldesign.wera.hmc.ArtikelClassificationVariantListChip" %>
<%@page import="com.computationaldesign.wera.jalo.WeraProductSet" %>
<%@page import="de.hybris.platform.jalo.product.Product" %>

<style type="text/css">
<!--
div.code_ca, div.code_assigned_ca {
	min-width:120px;
	text-align: center;
	border-left: 1px solid #BBBBBB;
	font-weight: bold;
	padding-left: 5px;
	padding-right: 5px;
}

th.code_assigned_ca {
	background-color:#EEEEEE;
	background-image:url('images/icons/invisible_light_icon.png');
	background-repeat: no-repeat;
	
}

div.normal_cols {
	text-align: center;
	padding-left: 5px;
	padding-right: 5px;
	font-weight: bold;
	display:block;
	max-width:120px;
	white-space:normal;
}
td.cilecEntry {
	text-align: center;
	padding-left: 5px;
	padding-right: 5px;
	max-width:120px;
	white-space:normal;
}
div.col_code {
	text-align: center;
	padding-left: 5px;
	padding-right: 15px;
	font-weight: bold;
	border-left: 1px solid #BBBBBB;
	display:none;
}
#ArtikelClassificationVariantListEntryChip {
	clear:left;
	height:auto;
}
-->
</style>
<script language="JavaScript1.2">
function toogle_cols () {
	
	// --- Alle Code-Spalten ein-/ ausblenden
	aElements = document.getElementsByName( "col_code" );
	for ( iNodeCount=0; iNodeCount < aElements.length; iNodeCount++ ) {
		oObj = aElements[iNodeCount];
		if ( oObj.style.display == 'none' )
			oObj.style.display = 'block';
		else
			oObj.style.display = 'none';
	}
	
	// --- Alle normalen Spalten ein-/ ausblenden
	aElements = document.getElementsByName( "normal_cols" );
	for ( iNodeCount=0; iNodeCount < aElements.length; iNodeCount++ ) {
		oObj = aElements[iNodeCount];
		if ( oObj.style.display == 'none' )
			oObj.style.display = 'block';
		else
			oObj.style.display = 'none';
	}
	
	// --- Alle Klassifikationsattribute ein- / ausblenden, die nicht nicht in die Tabelle geh�ren
	aElements = document.getElementsByName( "code_assigned_ca" );
	for ( iNodeCount=0; iNodeCount < aElements.length; iNodeCount++ ) {
		oObj = aElements[iNodeCount];
		if ( oObj.style.display == 'none' )
			oObj.style.display = 'block';
		else
			oObj.style.display = 'none';
	}
	
}
function docReady(fn) {
	// see if DOM is already available
	if (document.readyState === "complete" || document.readyState === "interactive") {
		// call on next available tick
		setTimeout(fn, 1);
	} else {
		document.addEventListener("DOMContentLoaded", fn);
	}
}

function onChangeInputDate(event) {
	console.log('onChangeDatePicker');
	console.log(event);
	//console.log(containerObj);
}
function onUpdateInputDate(event) {
	console.log('onUpdateInputDate');
	console.log(event);
	//console.log(containerObj);
}

function setBackgroundColorByDateChip() {

	// --- hole alle Datumsfelder
	aRows	= document.getElementsByClassName("artikeluebersicht_tr")
	console.log("Anzahl Zeilen: " + aRows.length)
	for ( iNodeCount=0; iNodeCount < aRows.length; iNodeCount++ ) {

		// --- hole akutelle zeile
		oRow = aRows[iNodeCount];

		console.log("-----")
		// console.log(oRow)

		// --- hole Datumsfelder
		aElements	= oRow.getElementsByClassName("dateEditorChip")

		// --- hole zusammenhängede Datumsfelfer
		oDateElementVon		= aElements[0]
		oDateElementBis		= aElements[1]
		oInputElementVon	= oDateElementVon.getElementsByTagName("input")[0]
		oInputElementBis	= oDateElementBis.getElementsByTagName("input")[0]


		// --- preset
		dateDiffVon			= NaN;
		dateDiffBis			= NaN;
		dateVonIsCurrent	= true;
		dateBisIsCurrent	= true;

		// --- aktuelles Datum
		const heute = new Date();

		// --- zeitdifferenz berechner, wenn möglich
		if ( oInputElementVon ) {

			if ( oInputElementVon.value != '' ) {

				datum 				= oInputElementVon.value.split('.');
				dateVon 			= new Date(datum[2],datum[1]-1,datum[0]);
				dateDiffVon			= dateVon.getTime() - heute.getTime();
				dateVonIsCurrent	= (heute.getDate() == dateVon.getDate() && heute.getMonth() == dateVon.getMonth() && heute.getFullYear() == dateVon.getFullYear());

			}
		}
		if ( oInputElementBis ) {

			if ( oInputElementBis.value != '' ) {

				datum 				= oInputElementBis.value.split('.');
				dateBis 			= new Date(datum[2],datum[1]-1,datum[0]);
				dateDiffBis			= dateBis.getTime() - heute.getTime();
				dateBisIsCurrent	= (heute.getDate() == dateBis.getDate() && heute.getMonth() == dateBis.getMonth() && heute.getFullYear() == dateBis.getFullYear());

			}
		}

		// --- ist ein gültige Zeitdifferrenz vorhanden?
		if ( dateDiffVon > dateDiffBis && dateDiffVon != NaN && dateDiffBis != NaN  ) {
			// --- zeitbereich ungültig
			oRow.style.backgroundColor = 'yellow';
			continue;
		}

		// --- prüfe das Startdatum
		if ( dateDiffVon != NaN && dateDiffVon > 0 && dateVonIsCurrent == false ) {

			// --- das Startdatum liegt in der Zukunft
			oRow.style.backgroundColor = 'red';

		} else if ( dateDiffBis != NaN && dateDiffBis < 0  && dateBisIsCurrent == false ) { // --- prüfe das Endedatum

			// --- das Endedatum liegt in der Vergangenheit
			oRow.style.backgroundColor = 'red';
		}

	} // for ( iNodeCount=0; iNodeCount < aRows.length; iNodeCount++ ) {
}
// pass a function reference
docReady(setBackgroundColorByDateChip);


</script>

<div onclick='toogle_cols();' style="top:0px;text-align:top;cursor:pointer;clear:left;width:250;padding:bottom:10px;"  >
Umschaltung Tabellen-/ Komplettansicht&nbsp;<img src="images/icons/footer_previous.gif" /><img src="images/icons/footer_next.gif" /></div>
<div id="ArtikelClassificationVariantListEntryChip" >
<%
	final ArtikelClassificationVariantListChip theChip = (ArtikelClassificationVariantListChip) request.getAttribute(AbstractChip.CHIP_KEY);
	
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
%>
<table class="genericItemListChip" cellspacing="0" cellpadding="0" oncontextmenu="(
new Menu([ new MenuEntry('In neuem Fenster �ffnen', '<%=theChip.getCommandID(GenericItemListChip.OPEN_EDITOR)%>', 'true', true, 'images/icons/list_menu_open.gif', null, '', true), 
new MenuEntry('�ffnen', '<%=theChip.getCommandID(GenericItemListChip.OPEN_EDITOR_INTERNAL )%>', 'true', true, 'images/icons/list_menu_open.gif', null, '', true), 
new MenuEntry('Hinzuf�gen', '<%=theChip.getCommandID(GenericItemListChip.ADD)%>', 'true', true, 'images/icons/list_menu_add.gif', null, '', false), 
new MenuEntry('Neu anlegen', 'null', 'null', true, 'images/icons/list_menu_create.gif', [ new MenuEntry('Artikel             ', '<%=theChip.getCommandID(GenericItemListChip.CREATE)%>',  'WeraVariante', true, 'images/icons/e_variantproduct.gif', null, '', true), new MenuEntry('&amp;nbsp;&amp;nbsp;Artikel (Satz)', '<%=theChip.getCommandID(GenericItemListChip.CREATE)%>',  'WeraVarianteSet', true, 'images/icons/e_variantproduct.gif', null, '', true), new MenuEntry('&amp;nbsp;&amp;nbsp;Artikel (Satz in Satz)', '<%=theChip.getCommandID(GenericItemListChip.CREATE)%>',  'WeraProductSetVariants', true, 'images/icons/e_variantproduct.gif', null, '', true) ], '', true), 
new MenuEntry('Entfernen', '<%=theChip.getCommandID(GenericItemListChip.REMOVE)%>', 'true', true, 'images/icons/list_menu_remove.gif', null, 'Wollen Sie die markierten Eintr�ge wirklich l�schen?', true), 
new MenuSplitter('<hr class=\'splitter\' size=\'1\' noshade>'), 
new MenuEntry('Alle ausw�hlen', '<%=theChip.getCommandID(GenericItemListChip.SELECT_VISIBLE)%>', 'true', true, 'images/icons/list_menu_select_all.gif', null, '', true), 
new MenuEntry('Alles abw�hlen', '<%=theChip.getCommandID(GenericItemListChip.SELECT_VISIBLE)%>', 'true', true, 'images/icons/list_menu_deselect_all.gif', null, '', true), 
new MenuEntry('Kopieren', '<%=theChip.getCommandID(GenericItemListChip.COPY_EVENT)%>', 'true', true, 'images/icons/list_menu_copy.gif', null, '', true) ], event, null, null, { uniqueName: '<%= theChip.getUniqueName() %>'} )).show(); return false;" >
				<tr>
					<td>
						<div style="width:100%;overflow:auto;" id="resultlist_<%= theChip.getUniqueName() %>">
						<%
								if( showScrollbars && theChip.getListEntryCount()>100 )
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
								<th class="checkbox gilcCheckbox" style="border-left:0px;background-color:#BBBBBB;">
									<div>
										<input name="SELECTOR" class="header"  onclick="document.editorForm.elements['<%=theChip.getCommandID(GenericItemListChip.SELECT_VISIBLE)%>'].value='true';setScrollAndSubmit();" type="checkbox" name="" value="ALL" />
										<input type="hidden" name="<%=theChip.getCommandID(GenericItemListChip.SELECT_VISIBLE)%>" value=""/>
										<input type="hidden" name="<%=theChip.getCommandID("mark")%>" value="" />   <!-- dummy event to allow lists to be completely de-selected -->
									</div>
								</th>
								<!-- gilcHeaderIcon -->
								<th class="gilcIcon" style="border-left:0px;background-color:#BBBBBB;">
									<div class="gilcIcon">&nbsp;</div>
								</th>

<%
		// --- normal attributes
		for( final Iterator iter = theChip.getAttributeNodes().iterator(); iter.hasNext(); )
		{
			final AttributeNode attributeNode = (AttributeNode) iter.next();
			final String qualifier = attributeNode.getAttributeQualifier();
			final int attributeWidth = attributeNode.getActualColumnWidth();
%>
			<th class="gilcEntry" ><div id="normal_cols" name="normal_cols" class="normal_cols" style="display:block;min-width:<%= attributeWidth %>px;" ><%= theChip.getTitle(qualifier) %></div></th>
<%
		}
%>
		<!-- hidden CODE - Spalte fuer Tabellenmodus --->
		<th class="gilcEntry" ><div id="col_code" name="col_code" class="col_code" style="display:none;" >Code</div></th>

<%
		// --- classification attributes assigned only
		if( theChip.getClassificationAttributeAssignedOnlyNames() != null )
		{
			for( final Iterator iter = theChip.getClassificationAttributeAssignedOnlyNames().iterator(); iter.hasNext(); )
			{
%>
				<th class="gilcEntry code_assigned_ca" ><div name="code_assigned_ca" class="code_assigned_ca" style="border-left:0px;" ><%=(String) iter.next() %></div></th>
<%
			}
		}
%>

		
<%
		// --- classification attributes
		if( theChip.getClassificationAttributeNames() != null )
		{
			for( final Iterator iter = theChip.getClassificationAttributeNames().iterator(); iter.hasNext(); )
			{
%>
				<th class="gilcEntry" ><div class="code_ca" style="border-left:0px;" ><%=(String) iter.next() %></div></th>
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
								<tr id="<%= chip.getUniqueName() %>_tr" class="artikeluebersicht_tr doubleclick-event <%=chip.getCommandID("edit")%>" onclick="">
									<!-- gilcCheckbox -->
										<td class="checkbox gilcCheckbox" id="<%= theChip.getUniqueName() %>_td" >
										<input type="checkbox" 
												 name="<%=theChip.getCommandID("mark")%>" 
												 value="<%=iCheckBox%>" />
										</td>
<%
										// --- N�chste Checkbox
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
</div><!-- test frame -->

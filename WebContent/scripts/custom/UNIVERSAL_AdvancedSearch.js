var flagDisplay = false;
function displayDiv() {
	if (flagDisplay) {
		flagDisplay = false;
		document.getElementById("advancedSearch").style.display = "none";
		document.getElementById("goAdvSearch").src = "images/arrow_dwn.png";

	} else {
		flagDisplay = true;
		document.getElementById("advancedSearch").style.display = "inline";
		document.getElementById("goAdvSearch").src = "images/arrow_up.png";

	}
	var parentMenuId = document.getElementById("parentMenuID").value;
	if(parentMenuId==2)
		parentMenuId=20;
	$.getJSON(
					'searchFieldList.htm',
					{
						parentMenuId : parentMenuId
					},
					function(data) {
						if (data.status && data.status=="Error") {	
							parent.jbarOnFailure(data.message);
						}else{
						var rowHTML = "";
						if (data.length != 0) {

							var options = [];
							$
									.each(
											data,
											function(item) {
							
												rowHTML = rowHTML
														+ "<tr id="
														+ data[item].columnName
														+ " style=\"display: none;\"><td>"
														+ data[item].uiFieldName
														+ "</td><td><select id=\""+data[item].columnName+"opId\" name="
														+ data[item].columnName
														+ "SelectOption\"><option>Contains </option><option>Equals</option></select></td><td><input type=\"text\" size=\"25\" id=\""
														+ data[item].columnName
														+ "TxtVal\"> </td></tr> ";
														options.push('<option value="'
														+ data[item].columnName + '">'
														+ data[item].uiFieldName
														+ '</option>');

											});
							
							$("#addSearchQueryROWID").html(rowHTML);
							$("#fieldSelectorId").html(options.join(''));
							}
						}
					});
}

function hidetxtbox(hideDiv, showDiv) {
	document.getElementById(hideDiv).style.display = 'none';
	document.getElementById(showDiv).style.display = 'inline';
	document.getElementById("searchValue").focus();

}

function getSelectedOptions(oList) {

	document.getElementById("matchCase").style.display = "inline";
	var selectedVal;
	for ( var i = 0; i < oList.options.length; i++) {
		if (oList.options[i].selected == true) {

			selectedVal = oList.options[i].value;
			document.getElementById(selectedVal).style.display = "inline";
		}
	}

}
function dispSearchResult() {

	var ticketId = $("#singleSearchId").val();
	var menuId = $("#menuID").val();
var parentMenuId =$("#parentMenuID").val(); 
var menuName =$("#menuNameID").val(); 
	$('div.ui-layout-center', window.parent.document)
			.html(
					'<iframe id="listticketiframe" width="95%" height="95%" src="UNIVERSAL_ListPage.htm?menuId='
							+ menuId + '&ticketId=' + ticketId + '&parentMenuId='+parentMenuId+'&menuName='+menuName+'"/>')

}

function advanceSearchResult(oList) {

	//var operator=document.getElementById("opId").value;
	document.getElementById("matchCase").style.display = "inline";
	var selectedVal = "";
	var txtVal = "";
	var opVal="";
	var menuId = $("#menuID").val();
	var parentMenuId =$("#parentMenuID").val(); 
	var menuName =$("#menuNameID").val(); 
	var searchfields = "";
	var oplist= "";
	var searchlength =0;
	for ( var i = 0; i < oList.options.length; i++) {
		if (oList.options[i].selected == true) {
			selectedVal = oList.options[i].value;
			txtVal = selectedVal + "TxtVal";
			opVal = "#"+selectedVal + "opId";
			if (document.getElementById(txtVal).value != null) {
				searchlength++;
				if (selectedVal == "") {
					searchfields = selectedVal + ","
							+ document.getElementById(txtVal).value +","+$(opVal).val()+";";
							
					
				} else {
					
					searchfields = searchfields + selectedVal + ","
					+ document.getElementById(txtVal).value +","+$(opVal).val()+";";
				}

			}
			}
	}

	var search_cond = $("#searchCondID").val();//document.getElementById("searchCondID").value;
	if (searchlength != 0) {
		$('div.ui-layout-center', window.parent.document)
				.html(
						'<iframe id="listticketiframe" width="95%" height="95%" src="UNIVERSAL_ListPage.htm?menuId='
								+ menuId
								+ '&searchfields='
								+ searchfields
								+ '&searchlength='
								+ searchlength
								+ '&search_cond=' + search_cond + '&parentMenuId='+parentMenuId+'&menuName='+menuName+'"/>');
	}
	
}

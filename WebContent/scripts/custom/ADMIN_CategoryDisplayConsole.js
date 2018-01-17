//On KEY Press Event
function check(e) {  
	var keynum  ;
	var keychar  ;
	var numcheck  ;
	// For Internet Explorer  
	if (window.event)  
	{  
	keynum = e.keyCode  ;
	}  
	// For Netscape/Firefox/Opera  
	else if (e.which)  
	{  
	keynum = e.which  ;
	}  
	keychar = String.fromCharCode(keynum)  ;
	//List of special characters you want to restrict  
	if (keychar == "'" || keychar == "`" || keychar == "&" || keychar == "<" || keychar == ">" || keychar == "=" || keychar == "\"" || keychar == "\'"
		|| keychar == "\\" || keychar == "\\/"  || keychar == "~")  
	{  	  
		return false;  
	}  
	else {  
		return true;  
	}  
} 
/**
 * getCategoryList: This method fetches the list of Category. All the json vars are reset before populating the Category List.
 * 
 */
function getCategoryList(){		

	 categoryJson="{ \"category\":{";
	 endCategoryJson="}}";

	 changedJson="{ \"category\":{";
	 endChangedJson="}}";

	 deletedApproverArray=new Array();
	 approvrArray=new Array();
	 valArray=new Array();

	 oldApproverArr=new Array();
	 newApp=new Array();
	 newApproverArr=new Array();

	 finalJsonToUpdate="";	
	
	var statusIdVal = $('input[name=status]:radio:checked').val();
	var functionId=$("#function").val();
	var id='function';
	if(($('#function').val()=="")||($('#function').val()=="--Please Select--")||($('#function').val()==0)){		
		$('#TextFieldSpan'+id+'').text('');
		$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
	}else{
		$('#TextFieldSpan'+id+'').text('');
		$.get('adminConsole_getCategoryList.htm',{functionId : $('#function').val(),categoryName : $("#categoryName").val(),statusVal : statusIdVal},function(data) {
			 $("#CATEGORY_JMESA_TR").removeClass();
			 $("#CATEGORY_JMESA").removeClass();        
	         $("#CATEGORY_JMESA").html("");      
	         $("#CATEGORY_JMESA").html(data);
	     });
	}
}

function editCategoryRow(rowID){
	var fieldArray=new Array();
	var isRowCheckboxChecked=0;
	fieldArray.push("checkbox"+rowID);
	fieldArray.push("CATEGORY_NAME"+rowID);
	fieldArray.push("CATEGORY_ID"+rowID)
	fieldArray.push("CATEGORY_STATUS"+rowID);
	fieldArray.push("SUBCATEGORY_NAME"+rowID);
	fieldArray.push("SUBCATEGORY_ID"+rowID)
	fieldArray.push("SUBCATEGORY_STATUS"+rowID);
	fieldArray.push("IS_CHANGE_REQUEST"+rowID);
	fieldArray.push("RECOMMENDED_PRIORITY"+rowID);
	fieldArray.push("APPROVER_LEVEL_1_"+rowID);
	fieldArray.push("APPROVER_LEVEL_2_"+rowID);
	fieldArray.push("APPROVER_LEVEL_3_"+rowID);	
	fieldArray.push("APPROVER_LEVEL_STATUS_1_"+rowID);
	fieldArray.push("APPROVER_LEVEL_STATUS_2_"+rowID);
	fieldArray.push("APPROVER_LEVEL_STATUS_3_"+rowID);	
	fieldArray.push("LINK"+rowID);	
	var tableName='Category_Detail_row';
	
	// noOfRows is the difference between the total length and 5 (to exclude the headers,tool-bar and filter for the table)
	var noOfRows=($('#Category_Detail tr').length)-5;	
	
	var actualRowID=0;
	
	if(rowID>15){
		actualRowID=rowID%noOfRows;
	}else{
		actualRowID=rowID;
	}
	if(actualRowID==0){
		actualRowID=rowID;
	}
	
	var tableRowID='Category_Detail_row'+actualRowID;
	var trColorOnEdit='#B7AFA3';
	var trColorEven='#F5F5E0';
	var trColorOdd='#FFFFFF';
	$.each(fieldArray,function(id,value){
		if(value=="checkbox"+rowID){
			if($("#"+value).attr('checked')==true){
				isRowCheckboxChecked=1;					
			}else{
				isRowCheckboxChecked=0;
			}
		}else{
			if(isRowCheckboxChecked==1){
				if(value.indexOf('CATEGORY_ID')<0 && value.indexOf('APPROVER_LEVEL_')<0){					
						$("#"+value).removeAttr("readonly");						
						getActiveInactiveDropdown(value,rowID);	
						$("#"+tableRowID).css('background-color', trColorOnEdit);
					
			}				
			}else if(isRowCheckboxChecked==0){
				//revert the old values
				if(actualRowID%2==0){
					$("#"+tableRowID).css('background-color', trColorEven);
				}else{
					$("#"+tableRowID).css('background-color', trColorOdd);
				}
				
			}
		}
	});
	
	if(isRowCheckboxChecked==1){
		
		//TODO: Add only unique values
		var newcatCheckboxArr=new Array();
		var isPresent=0;//rowID not present
		if(catCheckboxArr.length>0){
			newcatCheckboxArr=catCheckboxArr;
			$.each(newcatCheckboxArr,function(id,value){
				if(rowID==value){
					isPresent=1;//rowID is present					
				}			
			});
			
			if(isPresent==0){
				catCheckboxArr.push(rowID);
			}
		}else{
			catCheckboxArr.push(rowID);
		}
		//add values to JSON Object
		addValuesToJson(catCheckboxArr,fieldArray,rowID);
		//on edit of any field make corresponding change in the json object
		if($("#IS_CHANGE_REQUEST"+rowID).val()=="Yes"){
			$("#MODIFY_APPROVER_"+rowID).attr('style','display:block');
		}
	}else{
		//TODO:Remove the row ID from checkbox array
		//removeCheckboxValue(catCheckboxArr,rowID);
		$("#MODIFY_APPROVER_"+rowID).attr('style','display:none');
		
		var newArrCheckBox=new Array();
		$.each(catCheckboxArr,function(id,value){		
			if(value!=rowID){
				newArrCheckBox.push(value);				
				}		
		});
		catCheckboxArr=new Array();
		catCheckboxArr=newArrCheckBox;		
		
		//Fetch the rowID from categoryJson and revert the values in the row and remove the Json Values
		undoRowChangeOnUncheck(categoryJson,rowID,"categoryJson");
		//Fetch the rowID from changedJson and remove the Json Values
		undoRowChangeOnUncheck(changedJson,rowID,"changedJson");
	}
	
}

function getActiveInactiveDropdown(fldValue,rowID){
	var originalVal=$("#"+fldValue).val();
	if(fldValue.indexOf("Y_STATUS") >1 ){
		
		var html="<select id=\""+fldValue+"\" onchange=\"disableFieldOnChange(\'"+fldValue+"\',"+rowID+")\" name=\""+fldValue+"\" class=\"myTextInputForSelectCategory\">";
		html+="<option value=\"Active\">Active</option>";
		html+="<option value=\"Inactive\">Inactive</option>";
		html+="</select>";
		$("#"+fldValue).html('');
		$("#"+fldValue).replaceWith(html);
		
		$("#"+fldValue+" option[value='"+originalVal+"']").attr("selected",true);
		disableFieldOnChange_NOJSONCHANGE(fldValue,rowID);
	}

	if(fldValue.indexOf("IS_CHANGE_REQUEST")>=0){
		var html="<select id=\""+fldValue+"\" onchange=\"disableFieldOnChange(\'"+fldValue+"\',"+rowID+")\" name=\""+fldValue+"\" class=\"myTextInputForSelectCategory\">";
		html+="<option value=\"Yes\">Yes</option>";
		html+="<option value=\"No\">No</option>";
		html+="</select>";
		$("#"+fldValue).html('');
		$("#"+fldValue).replaceWith(html);
		
		$("#"+fldValue+" option[value='"+originalVal+"']").attr("selected",true);
		disableFieldOnChange_NOJSONCHANGE(fldValue,rowID);
	}
	if(fldValue.indexOf("RECOMMENDED_PRIORITY")>=0){
		var html="<select id=\""+fldValue+"\" onchange=\"disableFieldOnChange(\'"+fldValue+"\',"+rowID+")\" name=\""+fldValue+"\" class=\"myTextInputForSelectCategory\">";
		html+="<option value=\"\">--Please Select--</option>";
		html+="<option value=\"LOW\">LOW</option>";
		html+="<option value=\"MEDIUM\">MEDIUM</option>";
		html+="<option value=\"HIGH\">HIGH</option>";
		html+="</select>";
		$("#"+fldValue).html('');
		$("#"+fldValue).replaceWith(html);
		
		$("#"+fldValue+" option[value='"+originalVal+"']").attr("selected",true);
		disableFieldOnChange_NOJSONCHANGE(fldValue,rowID);
	}
	
	
}
function disableFieldOnChange_NOJSONCHANGE(fldValue,rowID){
	var fieldArray=new Array();
	
	fieldArray.push("SUBCATEGORY_NAME"+rowID);
	fieldArray.push("SUBCATEGORY_STATUS"+rowID);
	fieldArray.push("IS_CHANGE_REQUEST"+rowID);
	fieldArray.push("RECOMMENDED_PRIORITY"+rowID);
	fieldArray.push("APPROVER_LEVEL_1_"+rowID);
	fieldArray.push("APPROVER_LEVEL_2_"+rowID);
	fieldArray.push("APPROVER_LEVEL_3_"+rowID);	
	fieldArray.push("MODIFY_APPROVER_"+rowID);
	var fldVals="CATEGORY_STATUS"+rowID;
	//If categoryStatus is Inactive then disable SubCategory Name/Status, Is Change Request,Rec. Priority,approvers 
	if(fldValue==fldVals ){
		if($("#"+fldValue).val()=="Inactive"){	
			for(var i=0;i<fieldArray.length;i++){				
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:none');
				}else{
					$("#"+fieldArray[i]).attr("disabled","disabled");
				}		
			}
		}else{
			for(var i=0;i<fieldArray.length;i++){				
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:block');
				}else{
					$("#"+fieldArray[i]).removeAttr("disabled");
				}
			}
		}
		
	}	
	
	if(fldValue.indexOf("SUBCATEGORY_STATUS")>=0){		
		if($("#"+fldValue).val()=="Inactive"){	
			for(var i=2;i<fieldArray.length;i++){			
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:none');
				}else{
					$("#"+fieldArray[i]).attr("disabled","disabled");
				}
			}
		}else{
			for(var i=2;i<fieldArray.length;i++){			
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:block');
				}else{
					$("#"+fieldArray[i]).removeAttr("disabled");
				}
			}
		}
	}
	if(fldValue.indexOf("IS_CHANGE_REQUEST")>=0){
		if($("#"+fldValue).val()=="No"){
			for(var i=3;i<fieldArray.length;i++){
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:none');
				}else{
					$("#"+fieldArray[i]).attr("disabled","disabled");
				}
			}
		}else{
			for(var i=3;i<fieldArray.length;i++){			
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:block');
				}else{
					$("#"+fieldArray[i]).removeAttr("disabled");
				}
			}
		}
		
	}

	
}
function disableFieldOnChange(fldValue,rowID){
	var fieldArray=new Array();
	
	fieldArray.push("SUBCATEGORY_NAME"+rowID);
	fieldArray.push("SUBCATEGORY_STATUS"+rowID);
	fieldArray.push("IS_CHANGE_REQUEST"+rowID);
	fieldArray.push("RECOMMENDED_PRIORITY"+rowID);
	fieldArray.push("APPROVER_LEVEL_1_"+rowID);
	fieldArray.push("APPROVER_LEVEL_2_"+rowID);
	fieldArray.push("APPROVER_LEVEL_3_"+rowID);	
	fieldArray.push("MODIFY_APPROVER_"+rowID);
	var fldVals="CATEGORY_STATUS"+rowID;
	//If categoryStatus is Inactive then disable SubCategory Name/Status, Is Change Request,Rec. Priority,approvers 
	if(fldValue==fldVals ){
		if($("#"+fldValue).val()=="Inactive"){	
			$("#SUBCATEGORY_STATUS"+rowID+" option[value='Inactive']").attr("selected",true);			
			disableFieldOnChange("SUBCATEGORY_STATUS"+rowID,rowID);
			for(var i=0;i<fieldArray.length;i++){				
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:none');
				}else{
					$("#"+fieldArray[i]).attr("disabled","disabled");
				}		
			}			
		}else{
			for(var i=0;i<fieldArray.length;i++){				
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:block');
				}else{
					$("#"+fieldArray[i]).removeAttr("disabled");
				}
			}
		}
		
	}	
	
	if(fldValue.indexOf("SUBCATEGORY_STATUS")>=0){		
		if($("#"+fldValue).val()=="Inactive"){	
			for(var i=2;i<fieldArray.length;i++){			
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:none');
				}else{
					$("#"+fieldArray[i]).attr("disabled","disabled");
				}
			}
		}else{
			for(var i=2;i<fieldArray.length;i++){			
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:block');
				}else{
					$("#"+fieldArray[i]).removeAttr("disabled");
				}
			}
		}
	}
	if(fldValue.indexOf("IS_CHANGE_REQUEST")>=0){
		if($("#"+fldValue).val()=="No"){
			for(var i=3;i<fieldArray.length;i++){
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:none');
				}else{
					$("#"+fieldArray[i]).attr("disabled","disabled");
				}
			}
		}else{
			for(var i=3;i<fieldArray.length;i++){			
				if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
					$("#"+fieldArray[i]).attr('style','display:block');
				}else{
					$("#"+fieldArray[i]).removeAttr("disabled");
				}
			}
		}
		
	}
	/*if(fldValue.indexOf("RECOMMENDED_PRIORITY")>=0){
		for(var i=4;i<fieldArray.length;i++){
			if(fieldArray[i].indexOf("APPROVER_LEVEL_")>=0){
				$("#"+fieldArray[i]).attr('style','display:none');
			}else{
				$("#"+fieldArray[i]).attr("disabled","disabled");
			}
		}
	}*/
	
	changeJSONArr(rowID,fldValue);
}
function addValuesToJson(catCheckboxArr,fieldArray,rowID){
	var jsonToAppend="";	
	jsonToAppend="\""+rowID+"\":{";
	$.each(fieldArray,function(id,value){		
		if(value!="checkbox"+rowID){			
			jsonToAppend+="\""+value+"\":\""+$("#"+value).val()+"\"";		
			if(id+1!=fieldArray.length){
				jsonToAppend+=",";
			}
		}
	});
	jsonToAppend+="}";	
	
	if(catCheckboxArr.length<=1	){
		categoryJson+=jsonToAppend;
		changedJson+=jsonToAppend;
	}else{
		categoryJson+=","+jsonToAppend;
		changedJson+=","+jsonToAppend;
	}	
}

function undoRowChangeOnUncheck(categoryJsonVal,rowID,jsonStrName){
	if(categoryJsonVal.length>0){
		var jsonVar=categoryJsonVal;
		jsonVar+="}}";		
		jsonVar=jsonVar.replace("}}}}","}}}");	
		var newArrs="";
		if(jsonVar.indexOf("category")<0){
			newArrs="{\"category\":{"+ jsonVar;		
			jsonVar=newArrs;
		}
		var jsonObje=$.parseJSON(jsonVar);
		var catJsonObje=jsonObje.category;
		var mainStr="";
		var str="";
		var mnStr="";
		var count=0;
		
		//Reset the values	
		if(jsonStrName=="categoryJson"){
			$.each(catJsonObje,function(id,value){
				if(rowID==id){
				$.each(value,function(ids,vals){				
					if($('#'+ids).is('input:text')  ){				
						$("#"+ids).replaceWith("<input type=\'text\' readonly=\'readonly\' class=\'borderlessTextInput\' size=\'30%\' id=\'"+ids+"\' value=\'"+vals+"\'  onchange=\"changeJSONArr(\'"+rowID+"\',\'CATEGORY_NAME"+rowID+"\')\"   onkeypress=\"return check(event)\"></input>");
						$("#"+ids).attr("readonly","readonly");	
						
						var spanFldName='span_'+ids;
						
						if($("#"+spanFldName).length>0){
							$("#"+spanFldName).attr('style','display:none');						
						}
						
					} else 
						if($('#'+ids).is('select')){					
							$("#"+ids).replaceWith("<input type=\'text\' class=\'borderlessTextInput\' readonly=\'readonly\' size=\'30%\' id=\'"+ids+"\' value=\'"+vals+"\'></input>");
							$("#"+ids).attr("readonly","readonly");		
						}
					
				});
				}
			});
		}
		$.each(catJsonObje,function(id,value){		
		if(rowID!=id){			
			if(count==0){
				mainStr+="{\""+id+"\":{";
			}else{
				mainStr+=",\""+id+"\":{";
			}
			
			
			var countNext=0;
			$.each(value,function(id,value){
				if(countNext==0){
					str+="\""+id+"\":\""+value+"\"";
				}else{
					str+=",\""+id+"\":\""+value+"\"";
				}
				
				countNext++;
			});
			count++;	
			mnStr+=mainStr+str+"}";		
			mainStr="";
			str="";
		}		
		});	
		var jsn="";
		if(mnStr!=""){
			 jsn="{\"category\":"+mnStr;
		}else{
				jsn="";
		}
		if(jsonStrName=="categoryJson"){
			categoryJson="";
			categoryJson=jsn;
			
		}else if(jsonStrName=="changedJson"){
			changedJson="";
			changedJson=jsn;		
		}
	}	
}
function changeJSONArr(rowNumber,fieldID){	
	var toChangeJsonArr=changedJson;
	toChangeJsonArr+="}}";
	toChangeJsonArr=toChangeJsonArr.replace("}}}}","}}}");	
	var newArr="";
	if(toChangeJsonArr.indexOf("category")<0){
		newArr="{\"category\":{"+ toChangeJsonArr;		
		toChangeJsonArr=newArr;
	}
	
	var jsonObj=$.parseJSON(toChangeJsonArr);
	
	var catJsonObjct=jsonObj.category;
	var fieldValue=$("#"+fieldID).val();
	catJsonObjct[rowNumber][fieldID]=fieldValue;	
	var newJsn=JSON.stringify(catJsonObjct);
	changedJson="{\"category\":"+newJsn;
	changedJson=changedJson.replace("}}","}");
	var fldName="span_"+fieldID;
	if($("#"+fieldID).val().length<=0){		
		$("#"+fldName).attr('style','display:block');
	}else{
		$("#"+fldName).attr('style','display:none');
	}
	if((fieldID).indexOf("IS_CHANGE_REQUEST")>=0){
		if($("#"+fieldID).val()=='No'){	
			$("#MODIFY_APPROVER_"+rowNumber).attr('style','display:none');
		}else
		if($("#"+fieldID).val()=='Yes'){	
			$("#MODIFY_APPROVER_"+rowNumber).attr('style','display:block');
		}
	}
}
function changeJSONAppArr(rowNumber,fieldID,fieldStatusID,statusVal,idValue){
	var toChangeJsonArr=changedJson;
	toChangeJsonArr+="}}";
	toChangeJsonArr=toChangeJsonArr.replace("}}}}","}}}");	
	var newArr="";
	if(toChangeJsonArr.indexOf("category")<0){
		newArr="{\"category\":{"+ toChangeJsonArr;		
		toChangeJsonArr=newArr;
	}	
	var jsonObj=$.parseJSON(toChangeJsonArr);	
	var catJsonObjct=jsonObj.category;
	
	$("#"+fieldID).val(idValue);
	$("#"+fieldStatusID).val(statusVal);
	
	var fieldValue=idValue;
	var fielsStatusValue=statusVal;
	catJsonObjct[rowNumber][fieldID]=fieldValue;	
	catJsonObjct[rowNumber][fieldStatusID]=fielsStatusValue;
	var newJsn=JSON.stringify(catJsonObjct);
	changedJson="{\"category\":"+newJsn;
	changedJson=changedJson.replace("}}","}");
	var fldName="span_"+fieldID;
	if($("#"+fieldID).val().length<=0){		
		$("#"+fldName).attr('style','display:block');
	}else{
		$("#"+fldName).attr('style','display:none');
	}
	
	
}
function removeCheckboxValue(catCheckboxArr,rowID){
	var newArr=new Array();
	$.each(catCheckboxArr,function(id,value){		
		if(value!=rowID){
			newArr.push(value);				
			}		
	});
	catCheckboxArr=new Array();
	catCheckboxArr=newArr;
}

function validateRequest_categoryDetail(){

	//Arrays Used are: catCheckboxArr,changedJson,categoryJson
	isValidJson=1;
	if(catCheckboxArr.length<=0){
		isValidJson=0;
	}
	var count=0;
	var isChanged=0;	
	var isMandatory=0;
	finalJsonToUpdate="";	
	if(isValidJson==1){
		var toChangeJsonArr=changedJson+"}}";
		toChangeJsonArr=toChangeJsonArr.replace("}}}}","}}}");	
		
		var toCategoryJson=categoryJson+"}}";
		toCategoryJson=toCategoryJson.replace("}}}}","}}}");	
		
	var jsonToAppend="{ \"category\":{";
	
	if(toCategoryJson.indexOf(jsonToAppend)<0){
		toCategoryJson=jsonToAppend+toCategoryJson;
		
	}
	
	if(toChangeJsonArr.indexOf(jsonToAppend)<0){
		toChangeJsonArr=jsonToAppend+toChangeJsonArr;
		
	}
	toCategoryJson=toCategoryJson.replace(":{{",":{");	
	toChangeJsonArr=toChangeJsonArr.replace(":{{",":{");
	toChangeJsonArr=toChangeJsonArr.replace('\"category\":{\"category\"','\"category\"');
	
	
		var oldJsonObj=$.parseJSON(toCategoryJson).category;
		var newJsonObj=$.parseJSON(toChangeJsonArr).category;
		
		var fieldArray=new Array();	
		fieldArray.push("CATEGORY_NAME");
		fieldArray.push("CATEGORY_STATUS");
		fieldArray.push("SUBCATEGORY_NAME");
		fieldArray.push("SUBCATEGORY_STATUS");
		fieldArray.push("IS_CHANGE_REQUEST");
		fieldArray.push("RECOMMENDED_PRIORITY");
		fieldArray.push("APPROVER_LEVEL_1_");
		fieldArray.push("APPROVER_LEVEL_2_");
		fieldArray.push("APPROVER_LEVEL_3_");
		fieldArray.push("APPROVER_LEVEL_STATUS_1_");
		fieldArray.push("APPROVER_LEVEL_STATUS_2_");
		fieldArray.push("APPROVER_LEVEL_STATUS_3_");
		fieldArray.push("LINK");//added by sali
		count=0;
		var isChngReq=0;
		$.each(oldJsonObj,function(oldID,oldRowID){
					
			$.each(newJsonObj,function(newID,newRowID){
				if(oldID==newID){	
					var isValidApprover=1;
					 isChngReq=0;
					$.each(fieldArray,function(fieldID,fieldName){
						var fieldNm="";
						fieldNm=fieldName+oldID;						
						//Compare if the data differs in the 2 json objects
						if(oldRowID[fieldNm]!= newRowID[fieldNm]){
							var fldName="span_"+fieldNm;							
							if(newRowID[fieldNm].length<=0){	
								if(fieldNm.indexOf("APPROVER")<0){
								$("#"+fldName).attr('style','display:block');
								isMandatory=1;
								}
							}else{
								isChanged=1;
								$("#"+fldName).attr('style','display:none');
								isMandatory=0;								
							}
							
							if(fieldName.indexOf("IS_CHANGE_REQUEST")>=0){
								if($("#"+fieldNm).val()=="Yes"){
									isChngReq=1;
								}
							}
							
						
						}						
						
					});	
					
				if(isChngReq==1){	
					if($("#APPROVER_LEVEL_1_"+oldID).val().length<=0 && $("#APPROVER_LEVEL_2_"+oldID).val().length<=0 && $("#APPROVER_LEVEL_3_"+oldID).val().length<=0){
						$("#span_APPROVER_LEVEL_1_"+oldID).attr('style','display:block');
						$("#span_APPROVER_LEVEL_2_"+oldID).attr('style','display:block');
						$("#span_APPROVER_LEVEL_3_"+oldID).attr('style','display:block');
						
						isValidApprover=0;
						
					}
				}	
					if(isChanged==1 && isMandatory==0 && isValidApprover==1){
						if(count==0){
							finalJsonToUpdate+="\""+oldID+"\":"+JSON.stringify(newRowID);
						}else{
							finalJsonToUpdate+=",\""+oldID+"\":"+JSON.stringify(newRowID);
						}
						count++;
					}else{
						isValidJson=0;
						return false;
					}
				}
			});
		});		
	}	
	
	
	
	if(isValidJson==0){
		parent.jbarOnFailure("Select valid fields to update");
	}
	if(isChanged==0	){	
		parent.jbarOnFailure("No change made");
		isValidJson=0;
	}	
	var	final_json_to_update="{\"category\":{"+ finalJsonToUpdate+"}}";
	if(isValidJson==1){		
		//Check if sub category or category status is In Active
		//If In active find the number of open and closed tickets for category or sub category
		$.ajaxSetup({ cache: false });  
		parent.blockUI();
		
		$.postJSON("insertNewCategoryAC.htm",{jsonString:final_json_to_update,functionId:$("#function").val()},function(response){
			var ticketList=response.responseList;
			var insertionMsg=response.message;
			
			
			if(ticketList!=null){
				if(ticketList.length>0){				
					var phtml="<table width='100%' border='0' id='approverTable' cellspacing='2' cellpadding='0' class='myDataTable'>";
					phtml+="<tr><th colspan=\"4\" style=\"background:#FAAC58\"><b><font size=\'2\' >RESULT (Please close the below tickets before inactivating a category or sub category)</font></b></th></tr>";
					phtml+="<tr><th>Category Name</th><th>Sub Category Name</th><th align=\'left\'>Count of  Tickets(Sub Category wise)</th><th align=\'left\'>Open Tickets(Max.10 tickets displayed)</th></tr>";
					$.each(ticketList,function(id,value){
						var NthOccurenceoFcomma=GetSubstringIndex(value.TICKET_ID, ',', 10);
						var ticketLastLen='';						
						if(NthOccurenceoFcomma>10){
							ticketLastLen=value.TICKET_ID.replace(value.TICKET_ID.substr(NthOccurenceoFcomma,value.TICKET_ID.length),'');
							ticketLastLen+='...';
						 }else{
							 ticketLastLen =value.TICKET_ID;
						 }						
						phtml+="<tr><td>"+value.CATEGORY_NAME+"</td><td>"+value.SUB_CATEGORY_NAME+"</td><td>"+value.COUNT_TICKETS+"</td><td style=\"word-wrap: break-word;\">"+ticketLastLen+"</td></tr>";						
					});
					parent.unblockUI();	
					
					phtml+="</table>";
						$('.submitButton_categoryDetail').colorbox({width:"65%", height:"95%",html:phtml,open:true,onComplete : function() { 		
						},onClosed:function(){
							$("#approverTable").html('');
							$(".submitButton_categoryDetail").removeClass('cboxElement').removeData('colorbox');
							
						}	});

				}else{
					parent.unblockUI();
					
					if(insertionMsg=="Success"){						
						$("#submitButton_categoryDetail").attr("disabled","disabled");
						$("#CATEGORY_TABLE").find("select,input,button,textarea").attr("disabled", "disabled");
						$("#Category_Detail").find("select,input,button,textarea").attr("disabled", "disabled");
						parent.jbarOnSuccess("Successfully updated!!!");
					}else if(insertionMsg=="invalidName"){
						parent.jbarOnFailure("Category Name already exists !!!");
					}else{
						parent.jbarOnFailure("Failure in updation!!!");
					}
				}
			}		else{
				parent.unblockUI();				
				if(insertionMsg=="Success"){
					$("#submitButton_categoryDetail").attr("disabled","disabled");
					$("#CATEGORY_TABLE").find("select,input,button,textarea").attr("disabled", "disabled");
					$("#Category_Detail").find("select,input,button,textarea").attr("disabled", "disabled");
					parent.jbarOnSuccess("Successfully updated!!!");
				}else{
					parent.jbarOnFailure("Failure in updation!!!");
				}
			}
		});		

	}
}
function GetSubstringIndex(str, substring, n) {
    var times = 0, index = null;

    while (times < n && index !== -1) {
        index = str.indexOf(substring, index+1);
        times++;
    }

    return index;
}

function getApproverMatrixPopup(tableRowID,approverFieldName, approverField,subcategoryID){
	
	
	var popupHtml="<table width='100%' border='0' id='approverTable' cellspacing='2' cellpadding='0' class='createTable'>";
	popupHtml+="<tr><th colspan=\"4\" style=\"background:#FAAC58\"><b><font size=\'2\' >MODIFY APPROVER</font></b></th></tr>"; 
	popupHtml+="<td colspan='4' align='left'><span class='containerBlock1'><b>View Status of category</b><font color='brown'><b><i> (Select Status)</i></b></font></span></td>";
	popupHtml+="<tr align='left'><td align='left' class=\"labelAdmin\" width='50%'>Approver Role</td><td align='center' width='50%'><input type=\'text\' readonly=\'readonly\'  id=\'"+approverFieldName+"\' value=\'"+approverField+"\'></input> </td></tr>";
	popupHtml+="<tr align='left'><td align='left' class=\"labelAdmin\" width='50%'>Status</td><td  align='center'>Active:<input type='radio' id='status'  name='status' value='Active' checked='true' onclick=\"getApproverTable('"+tableRowID+"','"+approverFieldName+"','"+approverField+"','"+subcategoryID+"')\"></input>&nbsp;Inactive:<input type='radio' id='status'   name='status' value='0' onclick=\"getApproverTable('"+tableRowID+"','"+approverFieldName+"','"+approverField+"','"+subcategoryID+"')\"></input>&nbsp;All:<input type='radio' id='status'  name='status'  value='All' onclick=\"getApproverTable('"+tableRowID+"','"+approverFieldName+"','"+approverField+"','"+subcategoryID+"')\"></input></td></tr>";
	popupHtml+="<tr><td><input type='button' id='viewApproverButton' onclick=\"getApproverTable('"+tableRowID+"','"+approverFieldName+"','"+approverField+"','"+subcategoryID+"')\" value='View'/></td></tr>";
	popupHtml+="</table>"; 
	popupHtml+="<table width='100%'align='center' border='0' cellspacing='2' id='approverMemberTable' cellpadding='0' class='myDataTable' style='display:none'>";
	popupHtml+="<tr><td colspan='4' align='left'><span class='containerBlock1'><b>View Approver Employee Detail</b><font color='brown'><b><i> (Make Modifications if required)</i></b></font></span></td></tr>";
	popupHtml+="<tr><th>Member Id</th><th>Role</th><th>Status</th><th>Action</th></tr>";
	popupHtml+="</table>";
	var statusValue = $("input[@name=status]:checked").val();
	
		$('.'+approverFieldName).colorbox({width:"65%", height:"95%",html:popupHtml,onComplete : function() { 		
		}});
	
	
}

function getApproverTable(tableRowID,approverFieldName, approverField,subcategoryID){
	var tableHtml="";
	
	tableHtml+="<tr><td colspan='4' align='left'><span class='containerBlock1'><b>View Approver Employee Detail</b><font color='brown'><b><i> (Make Modifications if required)</i></b></font></span></td></tr>";
	tableHtml+="<tr><th>Member Id</th><th>Role</th><th>Status</th><th>Action</th></tr>";
	
	var statusValue = $("input[@name=status]:checked").val();
	$.get('adminConsole_getApproverMemberList.htm',{statusValue : statusValue,approverName: approverField , statusVal:statusValue,subcategoryID:subcategoryID},function(data) {
		if(data!=null ){
			if(data.length>0){
				for(var  i=0;i<data.length;i++){
					tableHtml+="<tr><td>"+data[i].EMPLOYEE+"</td><td>"+data[i].APPROVER_NAME+"</td><td>"+data[i].IS_ACTIVE+"</td><td>"+data[i].ACTION+"</td></tr>"
				}
			}
		}		
		tableHtml+="</table>";
		$("#approverMemberTable").html(tableHtml);
	});
	
	$("#approverMemberTable").attr('style','display:block');
}

function modifyApprovers(finalRowID,approverLeve1,approverLeve2,approverLeve3){
	var approverLevel1=$("#"+approverLeve1).val();
	var approverLevel2=$("#"+approverLeve2).val();
	var approverLevel3=$("#"+approverLeve3).val();
	var popupHtml="<table width='100%' border='0' id='approverTable' cellspacing='2' cellpadding='0' class='myDataTable'>";
	popupHtml+="<tr><th colspan=\"4\" style=\"background:#FAAC58\"><b><font size=\'2\' >MODIFY APPROVER</font></b></th></tr>"; 
	popupHtml+="<tr><td colspan='4' align='left'><span class='containerBlock1'><b>View Approver Detail</b><font color='brown'><b><i> (Select Status)</i></b></font></span></td></tr>";
	popupHtml+="<tr><th>Appprover Level</th><th>Approver Role</th><th>Action</th></tr>";
	popupHtml+="<tr><td align='left' class=\"labelCategory\" width='25%'>Approver Level 1</td><td width='25%' align='center'><input type=\'text\' id=\'APPROVERS_LEVEL_1_"+finalRowID+"\' class=\'myTextInputCategory\' readonly='readonly' value=\'"+approverLevel1+"\'/></td><td><img src='images/reject.png' title='Delete' onclick=\"removeApprover(\'"+approverLevel1+"\',\'APPROVERS_LEVEL_1_"+finalRowID+"\',\'reject1_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" ></img><img src='images/plussnode.png'  onclick=\"addSameApprover(\'"+approverLevel1+"\',\'APPROVERS_LEVEL_1_"+finalRowID+"\',\'add1_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" title='Revert to Original Value'></img><img src='images/editnode.jpg'  onclick=\"modifyEditApprover(\'"+approverLevel1+"\',\'APPROVERS_LEVEL_1_"+finalRowID+"\',\'edit1_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" title=\"Edit\"></img></td></tr>";
	popupHtml+="<tr><td align='left' class=\"labelCategory\" width='25%'>Approver Level 2</td><td width='25%' align='center'><input type=\'text\' id=\'APPROVERS_LEVEL_2_"+finalRowID+"\' class=\'myTextInputCategory\' readonly='readonly' value=\'"+approverLevel2+"\'/></td><td><img src='images/reject.png' title='Delete' onclick=\"removeApprover(\'"+approverLevel2+"\',\'APPROVERS_LEVEL_2_"+finalRowID+"\',\'reject2_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" ></img><img src='images/plussnode.png'  onclick=\"addSameApprover(\'"+approverLevel2+"\',\'APPROVERS_LEVEL_2_"+finalRowID+"\',\'add2_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" title='Revert to Original Value'\"></img><img src='images/editnode.jpg'  onclick=\"modifyEditApprover(\'"+approverLevel2+"\',\'APPROVERS_LEVEL_2_"+finalRowID+"\',\'edit2_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" title=\"Edit\"></img></td></tr>";
	popupHtml+="<tr><td align='left' class=\"labelCategory\" width='25%'>Approver Level 3</td><td width='25%' align='center'><input type=\'text\' id=\'APPROVERS_LEVEL_3_"+finalRowID+"\' class=\'myTextInputCategory\' readonly='readonly' value=\'"+approverLevel3+"\'/></td><td><img src='images/reject.png' title='Delete' onclick=\"removeApprover(\'"+approverLevel3+"\',\'APPROVERS_LEVEL_3_"+finalRowID+"\',\'reject3_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" ></img><img src='images/plussnode.png'  onclick=\"addSameApprover(\'"+approverLevel3+"\',\'APPROVERS_LEVEL_3_"+finalRowID+"\',\'add3_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" title='Revert to Original Value'\"></img><img src='images/editnode.jpg'  onclick=\"modifyEditApprover(\'"+approverLevel3+"\',\'APPROVERS_LEVEL_3_"+finalRowID+"\',\'edit3_"+finalRowID+"\',\'"+finalRowID+"\')\" style=\"cursor: pointer; cursor: hand;\" title=\"Edit\"></img></td></tr>";
	popupHtml+="<tr><td align='center'  colspan='4'><input type='button' id='resetApprover' value='Reset Approver' onclick=\"resetApprovers(\'"+finalRowID+"\',\'"+approverLevel1+"\',\'"+approverLevel2+"\',\'"+approverLevel3+"\')\" ></input></td></tr>";
	popupHtml+="</table>";
	
	$('.MODIFY_APPROVER_'+finalRowID).colorbox({width:"65%", height:"95%",html:popupHtml,onComplete : function() { 	
	}});
	
	
}
function removeApprover(approverLvl,approverID,btnID,finalRowID){
	var htmlApp="<input type=\'text\' id=\'"+approverID+"\' class=\'myTextInputCategory\' readonly='readonly' value=\'"+approverLvl+"\'/>";
	$("#"+approverID).html('');
	$("#"+approverID).replaceWith(htmlApp);
	$("#"+approverID).css("background-color","#EBDDE2");
	$("#"+approverID).css("font-style","italic");
	
}

function addSameApprover(approverLvl,approverID,btnID,finalRowID){
	var htmlApp="<input type=\'text\' id=\'"+approverID+"\' class=\'myTextInputCategory\' readonly='readonly' value=\'"+approverLvl+"\'/>";
	$("#"+approverID).html('');
	$("#"+approverID).replaceWith(htmlApp);
	$("#"+approverID).css("background-color","#FFFFFF");
	$("#"+approverID).css("font-style","normal");

	 
	 
	valArray.push($("#"+"APPROVER_LEVELS_1_"+finalRowID).val());
	valArray.push($("#"+"APPROVER_LEVELS_2_"+finalRowID).val());
	valArray.push($("#"+"APPROVER_LEVELS_3_"+finalRowID).val());
	
	var idArray=new Array();
	idArray.push("APPROVERS_LEVEL_1_"+finalRowID);
	idArray.push("APPROVERS_LEVEL_2_"+finalRowID);
	idArray.push("APPROVERS_LEVEL_3_"+finalRowID);
	
	
	 $.each(idArray,function(id,value){
		 if($("#TextFieldSpan"+value).length>0){
			 $("#TextFieldSpan"+value).html('');
			 $("#TextFieldSpan"+value).replaceWith('<span class="error" id="TextFieldSpan'+value+'" value=""></span>');
		 }	
	 });
	
	$.each(idArray,function(id,value){
		if(value!=approverID){
			if( $("#"+value).css("background-color") == "rgb(255, 255, 255)" ) {
				 if(($("#"+approverID).val().length>0) && ($("#"+approverID).val()==$("#"+value).val())){	
					 $("#"+approverID).after('');
					if($("#TextFieldSpan"+approverID).length>0){
						$("#TextFieldSpan"+approverID).remove();						
					}
					$("#"+approverID).after('<span class="error" id="TextFieldSpan'+approverID+'" >'+'Approver already exists!!'+''+'</span>');
					 
				 }
			}
		}
	});

}

function modifyEditApprover(approverLvl,approverID,btnID,finalRowID){
	$("#"+approverID).css("background-color","#FFFFFF");
	$("#"+approverID).css("font-style","normal");
	
	
	if($("#"+approverID).val()=='Reporting Officer'){
		parent.jbarOnFailure("Cannot Edit when approver is a  Reporting Officer");
	}else{	
		
	var html="<select id=\""+approverID+"\" onchange=\"validateApprovers(\'"+approverLvl+"\',\'"+approverID+"\',\'"+finalRowID+"\')\" name=\""+approverID+"\" class=\"myTextInputForSelectCategory\" width=\'20%\'>";
	html+="<option value=\"\">--Please Select--</option>";
 if(approverID.indexOf("APPROVERS_LEVEL_1_")>=0){
	 html+="<option value=\"Reporting Officer\">Reporting Officer</option>";
}	
 	html+="<option value=\"IT Function Head\">IT Function Head</option>";
	html+="<option value=\"IT Security\">IT Security</option>";
	
	html+="</select>";
	$("#"+approverID).html('');
	$("#"+approverID).replaceWith(html);	
	$("#"+approverID+" option[value='"+approverLvl+"']").attr("selected",true);
	}
}
function validateApprovers(approverLvl,approverID,finalRowID){
	var idArray=new Array();
	idArray.push("APPROVERS_LEVEL_1_"+finalRowID);
	idArray.push("APPROVERS_LEVEL_2_"+finalRowID);
	idArray.push("APPROVERS_LEVEL_3_"+finalRowID);
	
	var isVldApprover=1;
	
	 $.each(idArray,function(id,value){
		 if($("#TextFieldSpan"+value).length>=0){
			 $("#TextFieldSpan"+value).html('');
			 $("#TextFieldSpan"+value).val('');
			 $("#TextFieldSpan"+value).replaceWith('<span class="error" id="TextFieldSpan'+value+'" value=""></span>');
			 isVldApprover=1;
		 }	
	 });
	if(approverID.indexOf("APPROVERS_LEVEL_3_")>=0){
		for(var i=0;i<idArray.length-1;i++){
			if($.trim($("#"+idArray[i]).val()).length<=0){
				$("#"+idArray[i]).after('<span class="error" id="TextFieldSpan'+idArray[i]+'" >'+'Required !!'+''+'</span>');
				isVldApprover=0;
			}
		}
	}
	if(approverID.indexOf("APPROVERS_LEVEL_2_")>=0){
		for(var i=0;i<idArray.length-2;i++){
			if($.trim($("#"+idArray[i]).val()).length<=0){
				$("#"+idArray[i]).after('<span class="error" id="TextFieldSpan'+idArray[i]+'" >'+'Required !!'+''+'</span>');
				isVldApprover=0;
			}
		}
	}
	if(isVldApprover==1)	
	{
		$.each(idArray,function(id,value){
			if(value!=approverID){
				if( $("#"+value).css("background-color") == "rgb(255, 255, 255)" ) {
					 if( $("#"+value).val().length>0 && $("#"+approverID).val()==$("#"+value).val()){			
						 if($("#TextFieldSpan"+approverID).length>0){
								$("#TextFieldSpan"+approverID).remove();						
							}
						 $("#"+approverID).after('<span class="error" id="TextFieldSpan'+approverID+'" >'+'Approver already exists!!'+''+'</span>');
					 }
				
				}
			}
		});
	}
	
}
function resetApprovers(finalRowID,approverLevel1,approverLevel2,approverLevel3){
	
	oldApproverArr=new Array();	 
	newApproverArr=new Array();
	
	oldApproverArr.push({id:("APPROVER_LEVEL_1_"+finalRowID),value:approverLevel1});	
	oldApproverArr.push({id:("APPROVER_LEVEL_2_"+finalRowID),value:approverLevel2});	
	oldApproverArr.push({id:("APPROVER_LEVEL_3_"+finalRowID),value:approverLevel3});
	
	newApproverArr.push({id:("APPROVERS_LEVEL_1_"+finalRowID),value:$("#APPROVERS_LEVEL_1_"+finalRowID).val()});	
	newApproverArr.push({id:("APPROVERS_LEVEL_2_"+finalRowID),value:$("#APPROVERS_LEVEL_2_"+finalRowID).val()});	
	newApproverArr.push({id:("APPROVERS_LEVEL_3_"+finalRowID),value:$("#APPROVERS_LEVEL_3_"+finalRowID).val()});
	
	var isVldApprovers=1;
	
	
	if($.trim($("#"+newApproverArr[2].id).val()).length >0){
		if(($.trim($("#"+newApproverArr[0].id).val()).length <=0 )|| ($.trim($("#"+newApproverArr[1].id).val()).length <=0) ){
			isVldApprovers=0;
		}
	}
	
	if($.trim($("#"+newApproverArr[1].id).val()).length >0){
		if($.trim($("#"+newApproverArr[0].id).val()).length <=0 ){
			isVldApprovers=0;
		}
	}
if(isVldApprovers==1){	
	for(var i=0;i<newApproverArr.length;i++){
		if( $("#"+newApproverArr[i].id).length<=0){
			isVldApprovers=0;
		}else{					
			if(i!=(newApproverArr.length-1)){
				if($("#"+newApproverArr[i+1].id.length>0)){
				if($("#"+newApproverArr[i].id).val().length>0 || $("#"+newApproverArr[i+1].id).val().length>0 ){
					if($("#"+newApproverArr[i].id).val()==$("#"+newApproverArr[i+1].id).val()){
						isVldApprovers=0;
						break;
					}else{
						isVldApprovers=1;
					}
				}
					
				}
				
			}else{
				if($("#"+newApproverArr[i].id).val()==$("#"+newApproverArr[0].id).val()){
					isVldApprovers=0;
				}else{
					isVldApprovers=1;
				}
			}
				
			
			
		}
	}
}	
	if(isVldApprovers==0){
		parent.jbarOnFailure("Select approvers in order");
	}else{
		
			$.each(newApproverArr,function(i,v){
				if($("#"+v.id).css("background-color")== "rgb(255, 255, 255)"){	
					$("#"+fldID).css("background-color","#FFFFFF");
					var fldID=v.id.replace('APPROVERS_LEVEL','APPROVER_LEVEL');
					var fldIDStatus=v.id.replace('APPROVERS_LEVEL','APPROVER_LEVEL_STATUS');
					$("#"+fldIDStatus).val('1');
					$("#"+fldID).val(v.value);	
					changeJSONAppArr(finalRowID,fldID,fldIDStatus,'1',v.value);
				}else{
					var fldID=v.id.replace('APPROVERS_LEVEL','APPROVER_LEVEL');		
					var fldIDStatus=v.id.replace('APPROVERS_LEVEL','APPROVER_LEVEL_STATUS');
					$("#"+fldIDStatus).val('0');
					$("#"+fldID).val(v.value);
					changeJSONAppArr(finalRowID,fldID,fldIDStatus,'0',v.value);
				}				
			});
			
			$.colorbox.close();		
	}	
}
function resetTableOnPagination(catCheckboxArr,changedJson,tableName){
	
	var trColorOnEdit='#B7AFA3';
	var trColorEven='#F5F5E0';
	var trColorOdd='#FFFFFF';
	$.each(catCheckboxArr,function(id,value){	
		if($("#checkbox"+value).length>0){		
			$("#checkbox"+value).attr("checked",true);	
			$("#MODIFY_APPROVER_"+value).attr('style','display:block');
			$("#Category_Detail_row"+value).css('background-color', trColorOnEdit);	
		}
	});
	//Data Array re-check back	
	var jsonObj=$.parseJSON(changedJson+"}}");	
	var catJsonObjct=jsonObj.category;	
	$.each(catJsonObjct,function(id,value){				
		$.each(value,function(i,v){					
			if(i.indexOf('CATEGORY_ID')<0 && i.indexOf('APPROVER_LEVEL_')<0){					
				$("#"+i).removeAttr("readonly");				
				getActiveInactiveDropdown(i,id);						
			}				
			$("#"+i).val(v);			
			if(i.indexOf("Y_STATUS")>=1 || i.indexOf("IS_CHANGE_REQUEST")>=1 || i.indexOf("RECOMMENDED_PRIORITY")>=1){
				disableFieldOnChange_NOJSONCHANGE(v,value);	
			}
		});
		
	});
}
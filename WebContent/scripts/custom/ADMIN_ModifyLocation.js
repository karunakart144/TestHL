function getLocationDetails(){
	locationJson="{ \"location\":{";
	 endLocationJson="}}";

	 changedJson="{ \"location\":{";
	 endChangedJson="}}";
	 finalJsonToUpdate="";
	
	
	var locationId=$("#locationToChange :selected").text();
	var id='locationToChange';
	if((locationId=="")||(locationId=="--Please Select--")||(locationId==0)){		
		$('#TextFieldSpan'+id+'').text('');
		$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!'+''+'</span>');
	}else{
		$('#TextFieldSpan'+id+'').text('');
		$.get('adminConsole_getLocationDetails.htm',{locationId : locationId},function(data) {
			 $("#LOCATION_JMESA_TR").removeClass();
			 $("#LOCATION_JMESA").removeClass();        
	         $("#LOCATION_JMESA").html("");      
	         $("#LOCATION_JMESA").html(data);
	     });
	}
}

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

function editLocationRow(rowID){
	
	var fieldArray=new Array();
	var isRowCheckboxChecked=0;
	fieldArray.push("checkbox"+rowID);
	fieldArray.push("CITY"+rowID);
	fieldArray.push("BUILDING"+rowID);
	fieldArray.push("FLOOR"+rowID);
	fieldArray.push("LOCDETID"+rowID);
	fieldArray.push("STATUS"+rowID);
	fieldArray.push("LOCATION_ID"+rowID);
	
	
		
	var tableName='Location_Detail_row';
	
	// noOfRows is the difference between the total length and 5 (to exclude the headers,tool-bar and filter for the table)
	var noOfRows=($('#Location_Detail tr').length)-5;	
	
	var actualRowID=0;
	
	if(rowID>15){
		actualRowID=rowID%noOfRows;
	}else{
		actualRowID=rowID;
	}
	if(actualRowID==0){
		actualRowID=rowID;
	}
	
	var tableRowID='Location_Detail_row'+actualRowID;
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
		}else if(isRowCheckboxChecked==1){	
			
			if(value.indexOf("LOCDETID")<=0){
				$("#"+value).removeAttr("readonly");
			}
				getActiveInactiveDropdown(value,rowID);	
		
				$("#"+tableRowID).css('background-color', trColorOnEdit);
			}
			else if(isRowCheckboxChecked==0){
				//revert the old values
				if(actualRowID%2==0){
					$("#"+tableRowID).css('background-color', trColorEven);
				}else{
					$("#"+tableRowID).css('background-color', trColorOdd);
				}
				
			}
		});
	
	
	if(isRowCheckboxChecked==1){
		
		//TODO: Add only unique values
		var newlocCheckboxArr=new Array();
		var isPresent=0;//rowID not present
		if(locCheckboxArr.length>0){
			newlocCheckboxArr=locCheckboxArr;
			$.each(newlocCheckboxArr,function(id,value){
				if(rowID==value){
					isPresent=1;//rowID is present					
				}			
			});
			
			if(isPresent==0){
				locCheckboxArr.push(rowID);
			}
		}else{
			locCheckboxArr.push(rowID);
		}
		//add values to JSON Object
		addValuesToJson(locCheckboxArr,fieldArray,rowID);
		//on edit of any field make corresponding change in the json object
	}else{
		var newArrCheckBox=new Array();
		$.each(locCheckboxArr,function(id,value){		
			if(value!=rowID){
				newArrCheckBox.push(value);				
				}		
		});
		
		locCheckboxArr=new Array();
		locCheckboxArr=newArrCheckBox;		
		
		
		undoRowChangeOnUncheck(locationJson,rowID,"locationJson");
		
		undoRowChangeOnUncheck(changedJson,rowID,"changedJson");
		
		
	}
	
}

function getActiveInactiveDropdown(fldValue,rowID){
	var originalVal=$("#"+fldValue).val();
	
	if(fldValue.indexOf("STATUS")>=0){
		var html="<select id=\""+fldValue+"\" onchange=\"disableFieldOnChange(\'"+fldValue+"\',"+rowID+")\" name=\""+fldValue+"\" class=\"myTextInputForSelectLocation\">";
		html+="<option value=\"Active\">Active</option>";
		html+="<option value=\"Inactive\">Inactive</option>";
		html+="</select>";
		$("#"+fldValue).html('');
		$("#"+fldValue).replaceWith(html);
		
		$("#"+fldValue+" option[value='"+originalVal+"']").attr("selected",true);
		
	}
	
	
}

function disableFieldOnChange(fldValue,rowID){
	var fieldArray=new Array();
	
	fieldArray.push("checkbox"+rowID);
	fieldArray.push("CITY"+rowID);
	fieldArray.push("BUILDING"+rowID);
	fieldArray.push("FLOOR"+rowID);
	fieldArray.push("LOCDETID"+rowID);
	fieldArray.push("STATUS"+rowID);
	var fldVals="STATUS"+rowID;
		
	changeJSONArr(rowID,fldValue);
}

$(document).ready(function() { 
	
	parent.unblockUI(); 

	});

function addValuesToJson(locCheckboxArr,fieldArray,rowID){
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
	
	if(locCheckboxArr.length<=1	){
		locationJson+=jsonToAppend;
		changedJson+=jsonToAppend;
	}else{
		locationJson+=","+jsonToAppend;
		changedJson+=","+jsonToAppend;
	}	
}



function undoRowChangeOnUncheck(locationJsonVal,rowID,jsonStrName){
	if(locationJsonVal.length>0){
		var jsonVar=locationJsonVal;
		jsonVar+="}}";		
		jsonVar=jsonVar.replace("}}}}","}}}");	
		var newArrs="";
		if(jsonVar.indexOf("location")<0){
			newArrs="{\"location\":{"+ jsonVar;			
			jsonVar=newArrs;
		}
		var jsonObje=$.parseJSON(jsonVar);
		var locJsonObje=jsonObje.location;
		var mainStr="";
		var str="";
		var mnStr="";
		var count=0;
		
		//Reset the values	
		if(jsonStrName=="locationJson"){
			$.each(locJsonObje,function(id,value){
				if(rowID==id){
				$.each(value,function(ids,vals){				
					if($('#'+ids).is('input:text')  ){				
						$("#"+ids).replaceWith("<input type=\'text\' readonly=\'readonly\' class=\'borderlessTextInput\' size=\'30%\' id=\'"+ids+"\' value=\'"+vals+"\'  onchange=\"changeJSONArr(\'"+rowID+"\',\'CITY"+rowID+"\')\"   onkeypress=\"return check(event)\"></input>");
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
		$.each(locJsonObje,function(id,value){		
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
			 jsn="{\"location\":"+mnStr;
		}else{
				jsn="";
		}
		if(jsonStrName=="locationJson"){
			locationJson="";
			locationJson=jsn;
			
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
	if(toChangeJsonArr.indexOf("location")<0){
		newArr="{\"location\":{"+ toChangeJsonArr;		
		toChangeJsonArr=newArr;
	}
	
	var jsonObj=$.parseJSON(toChangeJsonArr);
	
	var locJsonObjct=jsonObj.location;
	var fieldValue=$("#"+fieldID).val();
	locJsonObjct[rowNumber][fieldID]=fieldValue;	
	var newJsn=JSON.stringify(locJsonObjct);
	changedJson="{\"location\":"+newJsn;
	changedJson=changedJson.replace("}}","}");
	var fldName="span_"+fieldID;
	if($("#"+fieldID).val().length<=0){		
		$("#"+fldName).attr('style','display:block');
	}else{
		$("#"+fldName).attr('style','display:none');
	}

}

function changeJSONAppArr(rowNumber,fieldID,fieldStatusID,statusVal,idValue){
	var toChangeJsonArr=changedJson;
	toChangeJsonArr+="}}";
	toChangeJsonArr=toChangeJsonArr.replace("}}}}","}}}");	
	var newArr="";
	if(toChangeJsonArr.indexOf("location")<0){
		newArr="{\"location\":{"+ toChangeJsonArr;		
		toChangeJsonArr=newArr;
	}	
	var jsonObj=$.parseJSON(toChangeJsonArr);	
	var locJsonObjct=jsonObj.location;
	
	$("#"+fieldID).val(idValue);
	$("#"+fieldStatusID).val(statusVal);
	
	var fieldValue=idValue;
	var fielsStatusValue=statusVal;
	locJsonObjct[rowNumber][fieldID]=fieldValue;	
	locJsonObjct[rowNumber][fieldStatusID]=fielsStatusValue;
	var newJsn=JSON.stringify(locJsonObjct);
	changedJson="{\"location\":"+newJsn;
	changedJson=changedJson.replace("}}","}");
	var fldName="span_"+fieldID;
	if($("#"+fieldID).val().length<=0){		
		$("#"+fldName).attr('style','display:block');
	}else{
		$("#"+fldName).attr('style','display:none');
	}
	
	
}
function removeCheckboxValue(locCheckboxArr,rowID){
	var newArr=new Array();
	$.each(locCheckboxArr,function(id,value){		
		if(value!=rowID){
			newArr.push(value);				
			}		
	});
	locCheckboxArr=new Array();
	locCheckboxArr=newArr;
}

function validateRequest_LoactionDetail(){

	isValidJson=1;
	if(locCheckboxArr.length<=0){
		isValidJson=0;
	}
	var count=0;
	var isChanged=0;	
	var isMandatory=0;
	var finalJsonToUpdate="";	
	if(isValidJson==1){
		var toChangeJsonArr=changedJson+"}}";
		toChangeJsonArr=toChangeJsonArr.replace("}}}}","}}}");	
		
		var toLocationJson=locationJson+"}}";
		toLocationJson=toLocationJson.replace("}}}}","}}}");	
		
	var jsonToAppend="{ \"location\":{";
	
	if(toLocationJson.indexOf(jsonToAppend)<0){
		toLocationJson=jsonToAppend+toLocationJson;
		
	}
	
	if(toChangeJsonArr.indexOf(jsonToAppend)<0){
		toChangeJsonArr=jsonToAppend+toChangeJsonArr;
		
	}
	//
	toLocationJson=toLocationJson.replace(":{{",":{");	
	toChangeJsonArr=toChangeJsonArr.replace(":{{",":{");
	toChangeJsonArr=toChangeJsonArr.replace('\"location\":{\"location\"','\"location\"');
	
		var oldJsonObj=$.parseJSON(toLocationJson).location;
		var newJsonObj=$.parseJSON(toChangeJsonArr).location;
		
		var fieldArray=new Array();	
		fieldArray.push("LOCATION_ID");
		fieldArray.push("CITY");
		fieldArray.push("BUILDING");
		fieldArray.push("FLOOR");
		fieldArray.push("LOCDETID");
		fieldArray.push("STATUS");
	
		count=0;
		var isChngReq=0;
		$.each(oldJsonObj,function(oldID,oldRowID){
					
			$.each(newJsonObj,function(newID,newRowID){
				if(oldID==newID){	
					
					 isChngReq=0;
					$.each(fieldArray,function(fieldID,fieldName){
						var fieldNm="";
						fieldNm=fieldName+oldID;						
						//Compare if the data differs in the 2 json objects
						if(oldRowID[fieldNm]!= newRowID[fieldNm]){
							var fldName="span_"+fieldNm;
							
							
							if(newRowID[fieldNm].length<=0){								
								$("#"+fldName).attr('style','display:block');
								isMandatory=1;								
							}else{
								isChanged=1;
								$("#"+fldName).attr('style','display:none');
								isMandatory=0;								
							}
						}						
						
					});	
					
				
					if(isChanged==1 && isMandatory==0){
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
	
	
/*	if(isChanged==0	){	
		parent.jbarOnFailure("No change made");
		isValidJson=0;
	}*/
	if(isValidJson==0 || isChanged==0){
		parent.jbarOnFailure("Select valid fields to update\\No change made");
		isValidJson=0;
	}
		
	var	final_json_to_update="{\"location\":{"+ finalJsonToUpdate+"}}";
	if(isValidJson==1){		
		
		$.ajaxSetup({ cache: false });  
		
		$.postJSON("ModifyLocationAC.htm",{jsonString:final_json_to_update,locationName:$("#locationToChange :selected").text(),locationId:$("#locationToChange").val()},function(response){
			
			var insertionMsg=response.message;
								
				if(insertionMsg=="Success"){
					$("#submitButton_locationDetail").attr("disabled","disabled");
					$("#LOCATION_TABLE").find("select,input,button,textarea").attr("disabled", "disabled");
					$("#Location_Detail").find("select,input,button,textarea").attr("disabled", "disabled");
					parent.jbarOnSuccess("Successfully updated!!!");
				}else{
					parent.jbarOnFailure("Failure in updation!!!");
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

function resetTableOnPagination(locCheckboxArr,changedJson,tableName){
	
	var trColorOnEdit='#B7AFA3';
	var trColorEven='#F5F5E0';
	var trColorOdd='#FFFFFF';
	$.each(locCheckboxArr,function(id,value){	
		if($("#checkbox"+value).length>0){		
			$("#checkbox"+value).attr("checked",true);	
			
			$("#Location_Detail_row"+value).css('background-color', trColorOnEdit);	
		}
	});
	
}
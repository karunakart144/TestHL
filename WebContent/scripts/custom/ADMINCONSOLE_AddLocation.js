
function getOperatingTimeTable(functionChange){
	var html='';
	if(functionChange=='functionChange'){
	html+='<tr><th>Day</th><th>Start Time(IST)<span class="red_text">* </span></th><th>End Time(IST)<span class="red_text">* </span></th><th>Next Working Day<span class="red_text">* </span></th></tr>';
	var rowCount=5;
	var dayArray=['MON','TUE','WED','THU','FRI'];
	for(var i=0;i<rowCount;i++){
		html+="<tr id='OPTime_"+i+"'><td align='center'><input class='borderlessTextInput' type='text' size='5%' id='DayTD_"+(i)+"' value='"+dayArray[i]+"' readonly='readonly'></input></td><td align='center'  class=\"bodyText\">"+buildHourPicker("startTime",i)+" &nbsp; "+buildMinutePicker("startTime",i)+" </td><td align='center'>"+buildHourPicker("endTime",i)+" &nbsp;"+buildMinutePicker("endTime",i)+"</td><td align='center'>"+buildNextDayPicker(dayArray[i],i)+"</td><td style='display:none' id='imageOpTimeTD_"+(i)+"' class='borderlessTextInput' width='10%'><img src='images/plussnode.png' title='Add' onclick=\'getOperatingTimeTable(\"rowClick\")\' style=\"cursor: pointer; cursor: hand;\" title=\"title='Add'\"></img></td><td style='display:none' width='10%' id='errorMsgTDTime_"+(i)+"' ><span id='errorMsgTime_"+(i)+"'></span></td></tr> ";
	}	
	$("#operatingTimeDetTable").html(html);	
	var rowCount_new = $('#operatingTimeDetTable tr').length;
	$("#imageOpTimeTD_"+(rowCount_new-2)).attr('style','display:block');
	
	}else if(functionChange=='rowClick'){
		var newRowHtml='';
		var rowCountNew = 0;
		rowCountNew=$('#operatingTimeDetTable tr').length-2;		
		$("#imageOpTimeTD_"+rowCountNew).attr('style','display:none');
		if($('#operatingTimeDetTable tr').length<7){
			rowCountNew=rowCountNew+1;
			newRowHtml+="<tr id='OPTime_"+rowCountNew+"'><td align='center'><input class='borderlessTextInput'  type='text' size='5%' id='DayTD_"+(rowCountNew)+"' value='SAT'readonly='readonly'></input></td><td align='center' class=\"bodyText\"> "+buildHourPicker("startTime",rowCountNew)+" &nbsp;"+buildMinutePicker("startTime",rowCountNew)+" </td><td align='center'>"+buildHourPicker("endTime",rowCountNew)+" &nbsp;"+buildMinutePicker("endTime",rowCountNew)+"</td><td align='center'><input class='borderlessTextInput' type='text' size='5%' id='nextDay_"+(rowCountNew)+"' value='MON' readonly='readonly'></input></td><td style='display:none' id='imageOpTimeTD_"+(rowCountNew)+"' class='borderlessTextInput' width='10%'><img src='images/plussnode.png' title='Add'  onclick=\'getOperatingTimeTable(\"rowClick2\")\' style=\"cursor: pointer; cursor: hand;\"></img></td><td id='imageOpTimeTDS_"+(rowCountNew)+"' class='borderlessTextInput' align='center' width='10%'><img src='images/minusnode.png'  onclick=\'getOperatingTimeTableRemoveRow(\"rowClick3\",\"OPTime_"+rowCountNew+"\")\' style=\"cursor: pointer; cursor: hand;\"></img></td><td style='display:none' id='errorMsgTDTime_"+(rowCountNew)+"' ><span id='errorMsgTime_"+(rowCountNew)+"'></span></td></tr> ";
			$('#operatingTimeDetTable > tbody:last').append(newRowHtml);	
		}
			$("#nextDay_4").val('SAT');
		var rowCountFinal = 0;
		rowCountFinal = $('#operatingTimeDetTable tr').length;		
		$("#imageOpTimeTD_"+(rowCountFinal-2)).attr('style','display:block');
	}else if(functionChange=='rowClick2'){
		var newRowHtml='';
		var rowCountNew = 0;
		rowCountNew=$('#operatingTimeDetTable tr').length-2;		
		$("#imageOpTimeTD_"+rowCountNew).attr('style','display:none');		
		if($('#operatingTimeDetTable tr').length==7){
			rowCountNew=rowCountNew+1;
			newRowHtml+="<tr id='OPTime_"+rowCountNew+"'><td align='center'><input class='borderlessTextInput'  type='text' size='5%' id='DayTD_"+(rowCountNew)+"' value='SUN'readonly='readonly'></input></td><td align='center' class=\"bodyText\"> "+buildHourPicker("startTime",rowCountNew)+" &nbsp;"+buildMinutePicker("startTime",rowCountNew)+" </td><td align='center'>"+buildHourPicker("endTime",rowCountNew)+" &nbsp;"+buildMinutePicker("endTime",rowCountNew)+"</td><td align='center'><input class='borderlessTextInput' type='text' size='5%' id='nextDay_"+(rowCountNew)+"' value='MON' readonly='readonly'></input></td><td id='imageOpTimeTD_"+(rowCountNew)+"' class='borderlessTextInput' align='center' width='10%'><img src='images/minusnode.png'  onclick=\'getOperatingTimeTableRemoveRow(\"rowClick2\",\"OPTime_"+rowCountNew+"\")\' style=\"cursor: pointer; cursor: hand;\"></img></td><td style='display:none' id='errorMsgTDTime_"+(rowCountNew)+"' ><span id='errorMsgTime_"+(rowCountNew)+"'></span></td></tr> ";
			$('#operatingTimeDetTable > tbody:last').append(newRowHtml);		
		}
		$("#nextDay_5").val('SUN');
		var rowCountFinal = 0;
		rowCountFinal = $('#operatingTimeDetTable tr').length;		
		$("#imageOpTimeTDS_"+(rowCountFinal-3)).attr('style','display:none');
	}
	
}


function getOperatingTimeTableRemoveRow(functionChange,id){	
	
	if(functionChange=='rowClick2'){
		$("#nextDay_5").val('MON');
		$("#imageOpTimeTD_5").attr('style','display:block');
		$("#imageOpTimeTDS_5").attr('style','display:block');
		
	}else if(functionChange=='rowClick3'){
		$("#nextDay_4").val('MON');
	}
	if(id=='OPTime_5'){		
		rowCountFinal = $('#operatingTimeDetTable tr').length;		
		$("#imageOpTimeTD_4").attr('style','display:block');
	}else{
		var rowCountFinal = 0;
		rowCountFinal = $('#operatingTimeDetTable tr').length;		
		$("#imageOpTimeTD_"+(rowCountFinal-2)).attr('style','display:block');
	}
	$('#'+id).remove();
}
function buildHourPicker(timeType,index) {
	 var html='<select id=\"hour_'+timeType+'_'+index+'\" onchange=\"removeErrorClassTime(\'hour_'+timeType+'_'+index+'\')\">';
	 var html2='';
	if(index==0){
		html='<select id=\"hour_'+timeType+'_'+index+'\" onchange=\"removeErrorClassTime(\'hour_'+timeType+'_'+index+'\'); selectAllHrMinDropdown(\'hour_'+timeType+'_\')\">';
	}
    
       html+='<option selected="selected" value="">HH</option>';
	for(var h=0; h<24; h++){	
		html2='';
		if(h<10){
			html+='<option value=0'+h+ '>0'+ h+ '</option>';
		}else{			
			html2='<option value='+h+ '>'+ h+ '</option>';			
		}
		
		html+=html2;
		html2='';
	}
	html+='</select>';
	
	return html;
}     
function buildMinutePicker(minuteType,index) {
	 
	  
	 var html='<select id=\"minute_'+minuteType+'_'+index+'\" onchange=\"removeErrorClassTime(\'minute_'+minuteType+'_'+index+'\')\">';
		if(index==0){
			html='<select id=\"minute_'+minuteType+'_'+index+'\" onchange=\"removeErrorClassTime(\'minute_'+minuteType+'_'+index+'\');selectAllHrMinDropdown(\'minute_'+minuteType+'_\')\">';
		}
	  html+='<option selected="selected" value="">MM</option>';	  
		 html+='<option value=00>00</option>';
		 html+='<option value=30>30</option>';		
	html+='</select>';
	return html;
} 
function selectAllHrMinDropdown(hourMinFieldID){
	var hourMinFieldVal=$("#"+hourMinFieldID+"0").val();
	for(var hourRow=1;hourRow<$('#operatingTimeDetTable tr').length;hourRow++){
		$("#"+hourMinFieldID+hourRow).val(hourMinFieldVal);
	}
	
}   

function buildNextDayPicker(dayValue,index) {
	var dayArray=['MON','TUE','WED','THU','FRI','SAT','SUN'];
	var html='';
	 if(dayValue.length>0) {
	for(var m=0; m<5; m++){
		if(dayValue.length>0){
		
		if(dayValue==dayArray[m]){	
			if(index<4){	
				html="<input class='borderlessTextInput' type='text' size='5%' id='nextDay_"+(index)+"' value='"+dayArray[m+1]+"' readonly='readonly'></input>"
			}else if(index==4){
				html="<input class='borderlessTextInput' type='text' size='5%' id='nextDay_"+(index)+"' value='MON' readonly='readonly'></input>"
			}
		}
			
		
	}
	}
	}
	return html;
} 

function showCountryDiv(fieldID){
	$('#'+fieldID).show();	
}


function getBuildingRowForITOrISorAdmin(functionName,functionChangeFlag){
	var html="";	
	if(functionName=='IT Infrastructure Management'){
		
		if(functionChangeFlag=='functionChange'){
			html+='<tr><th>Building</th><th>Floor</th></tr>';
			var rowNumbers=1;
			for(var i=0;i<rowNumbers;i++){
				html+="<tr id='BuildingTR_"+(i+1)+"'><td align='center'><input type='text' size='40%' onkeypress=\"return check(event)\" id='BuildingTD_"+(i+1)+"' onfocus=\"removeErrorClass(\'BuildingTD_"+(i+1)+"\')\"  maxlength=\"50\"></input></td><td align='center'><input type='text' size='40%' id='FloorTD_"+(i+1)+"'  onkeypress=\"return check(event)\"  onfocus=\"removeErrorClass(\'FloorTD_"+(i+1)+"\')\"  maxlength=\"50\"></input></td><td  class='borderlessTextInput'><img src='images/plussnode.png'id='addBuild_"+(i+1)+"' size='20%' onclick=\'getBuildingRowForITOrISorAdmin(\""+functionName+"\",\"rowClick\")\' style=\"cursor: pointer; cursor: hand;\"></img></td><td  class='borderlessTextInput' ><img src='images/minusnode.png' size='20%' id='removeBuild_"+(i+1)+"' onclick=\'removeBuildingRowForITOrISorAdmin(\"BuildingTR_"+(i+1)+"\")\' style=\"cursor: pointer; cursor: hand;\"></img></td><td style='display:none' id='errorMsgTD_"+(i+1)+"'><span id='errorMsg_"+(i+1)+"'></span></td></tr>";
			}	
			$('#buildingDetTable').html(html);
			var rowCount = $('#buildingDetTable tr').length;			
			$("#addBuild_"+(rowCount-1)).attr('style','display:block');
			maxIDArrBuilding.push(1);
		}else if(functionChangeFlag=='rowClick'){
			var rowCountFinal=0;
			var newRowHtml='';
			rowCountFinal=$('#buildingDetTable tr').length;
			var trs = $("#buildingDetTable").find("tbody>tr");
			var maxID=1;
			for(var i=1;i<trs.length;i++){
				//get the row iD
				var trID=trs[i].id;
				var rowID=trID.substr(trID.lastIndexOf('_')+1,trID.length);
				maxIDArrBuilding.push(rowID);
				//get the index and put to the array			
			}
			//get the maximum index
			//increase the max index by 1
			var j=0;
			for(var i=0;i<maxIDArrBuilding.length;i++){
				 j=maxIDArrBuilding[i];
				if(j<maxIDArrBuilding[i+1]){
					j=maxIDArrBuilding[i+1];
				}
			}
			j=(j*1)+1;
			newRowHtml+="<tr id='BuildingTR_"+(j)+"'><td align='center'><input type='text' size='40%' id='BuildingTD_"+(j)+"' onfocus=\"removeErrorClass(\'BuildingTD_"+(j)+"\')\"  maxlength=\"50\"></input></td><td align='center'><input type='text'  size='40%' id='FloorTD_"+(j)+"' onfocus=\"removeErrorClass(\'FloorTD_"+(j)+"\')\"  maxlength=\"50\"></input></td><td class='borderlessTextInput' ><img id='addBuild_"+(j)+"' src='images/plussnode.png'  onclick=\'getBuildingRowForITOrISorAdmin(\""+functionName+"\",\"rowClick\")\' style=\"cursor: pointer; cursor: hand;\" ></img></td><td><img src='images/minusnode.png'  id='removeBuild_"+(j)+"' onclick=\"removeBuildingRowForITOrISorAdmin(\'"+j+"\')\" style=\"cursor: pointer; cursor: hand;\"></img></td><td style='display:none' id='errorMsgTD_"+(j)+"'><span id='errorMsg_"+j+"'></span></td></tr>";
						
			$('#buildingDetTable > tbody:last').append(newRowHtml);
			rowCountFinal = ($('#buildingDetTable tr').length );		
			$("#addBuild_"+(rowCountFinal)).attr('style','display:block');
			maxIDArrBuilding.push(j);
		}
		
	}else if(functionName=='Admin'){ 
		
		if(functionChangeFlag=='functionChange'){
			html+='<tr><th>Building</th><th>Floor</th><th>ODC</th></tr>';
			var rowNumbers=4;
			for(var i=0;i<rowNumbers;i++){
				html+="<tr id='BuildingTR_"+(i+1)+"'><td align='center'><input type='text' size='40%' id='BuildingTD_"+(i+1)+"' onfocus=\"removeErrorClass(\'BuildingTD_"+(i+1)+"\')\"></input></td><td align='center'><input type='text'  size='40%' id='FloorTD_"+(i+1)+"' onfocus=\"removeErrorClass(\'FloorTD_"+(i+1)+"\')\"></input></td><td align='center'><input type='text' size='40%' id='ODCTD_"+(i+1)+"' onfocus=\"removeErrorClass(\'ODCTD_"+(i+1)+"\')\" ></input></td><td style='display:none' id='imageTD_"+(i+1)+"' class='borderlessTextInput'><img src='images/plussnode.png'  onclick=\'getBuildingRowForITOrISorAdmin(\""+functionName+"\",\"rowClick\")\'  style=\"cursor: pointer; cursor: hand;\"></img></td><td style='display:none' id=\'errorMsgTD_"+(i+1)+"\' ><span id=\'errorMsg_"+(i+1)+"\'></span></td></tr>";				
			}	
			$('#buildingDetTable').html(html);
			var rowCount = $('#buildingDetTable tr').length;
			$("#imageTD_"+(rowCount-1)).attr('style','display:block');
		}else if(functionChangeFlag=='rowClick'){
			var rowCount = $('#buildingDetTable tr').length;
			var newRowHtml='';
			newRowHtml+="<tr id='BuildingTR_"+(rowCount)+"'><td align='center'><input type='text' size='40%' id='BuildingTD_"+(rowCount)+"' onfocus=\"removeErrorClass(\'BuildingTD_"+(i+1)+"\')\"></input></td><td align='center'><input type='text'  size='40%' id='FloorTD_"+(rowCount)+"' onfocus=\"removeErrorClass(\'FloorTD_"+(i+1)+"\')\"></input></td><td align='center'><input type='text' size='40%' id='ODCTD_"+(rowCount)+"' onfocus=\"removeErrorClass(\'ODCTD_"+(i+1)+"\')\"></input></td><td style='display:none' id='imageTD_"+(rowCount)+"' class='borderlessTextInput'><img src='images/plussnode.png'  onclick=\'getBuildingRowForITOrISorAdmin(\""+functionName+"\",\"rowClick\")\' style=\"cursor: pointer; cursor: hand;\"></img></td><td style='display:none' id='errorMsgTD_"+(rowCount)+"'><span id='errorMsg_'"+rowCount+"></span></td></tr>";
			$("#imageTD_"+(rowCount-1)).attr('style','display:none');
			$('#buildingDetTable > tbody:last').append(newRowHtml);
			var newRowCount=$('#buildingDetTable tr').length;
			$("#imageTD_"+(newRowCount-1)).attr('style','display:block');
		}
	}
}


function displayTableForApproverMatrix(functionChangeFlag){
	var approverHTML='';	
	
	approverHTML+='<tr><th>Employee ID<span class="red_text">* </span></th><th>Role<span class="red_text">* </span></th></tr>';
	if(functionChangeFlag=='functionChange'){
		approverHTML+="<tr id=\"OPROW_1\"><td align='center' id='OPID_1'><input type='text' id='APPROVER_1' value='' onfocus=\"removeMsgClass(\'APPROVER_1\',\'1\')\"></input></td><td align='center'><select id='APPROVER_ROLE_1'>"+approverIDOptionsHTML+"</select></td><td class='borderlessTextInput'><img src='images/plussnode.png'  id='APPROVER_IMG_1' onclick=\'displayTableForApproverMatrix(\"rowClick\")\' style=\"cursor: pointer; cursor: hand;\"></img></td><td class='borderlessTextInput'><img src='images/minusnode.png'  id='APPROVER_IMG_REM_1' onclick=\'remApproverMatrix(\"1\")\' style=\"cursor: pointer; cursor: hand;\"></img></td><td id='empResultMessageTD_1'><span id='empResultMessage_1' class='invalid_textAdmin'></span></td></tr>";
		$("#approverDetTable").html(approverHTML);
		lastID=$("#approverDetTable").find("tr:last").attr('id');	
		maxIDArr.push(1);
	}else if(functionChangeFlag=='rowClick'){
		var rowCountFinal=0;
		var approverHTML_new='';
		rowCountFinal=$('#approverDetTable tr').length;
		var trs = $("#approverDetTable").find("tbody>tr");
		var maxID=1;
		for(var i=1;i<trs.length;i++){
			//get the row iD
			var trID=trs[i].id;
			var rowID=trID.substr(trID.lastIndexOf('_')+1,trID.length);
			maxIDArr.push(rowID);
			//get the index and put to the array			
		}
		//get the maximum index
		//increase the max index by 1
		var j=0;
		for(var i=0;i<maxIDArr.length;i++){
			 j=maxIDArr[i];
			if(j<maxIDArr[i+1]){
				j=maxIDArr[i+1];
			}
		}
		j=(j*1)+1;
		approverHTML_new+="<tr id=\'OPROW_"+j+"\'><td align='center' id=\'OPID_'"+j+"\'><input type='text' id='APPROVER_"+j+"' value='' onfocus=\"removeMsgClass(\'APPROVER_"+j+"\',\'"+j+"\')\"></input></td><td align='center'><select id='APPROVER_ROLE_"+j+"'>"+approverIDOptionsHTML+"</select></td><td class='borderlessTextInput'><img src='images/plussnode.png'  id='APPROVER_IMG_"+j+"' onclick=\"displayTableForApproverMatrix(\'rowClick\')\" style=\"cursor: pointer; cursor: hand;\"></img></td><td class='borderlessTextInput'><img src='images/minusnode.png'  id='APPROVER_IMG_REM_"+j+"' onclick=\"remApproverMatrix(\'"+j+"\')\" style=\"cursor: pointer; cursor: hand;\"></img></td><td id='empResultMessageTD_"+j+"'><span id='empResultMessage_"+j+"' class='invalid_textAdmin'></span></td></tr>";
		$('#approverDetTable > tbody:last').append(approverHTML_new);	
		rowCountFinal = ($('#approverDetTable tr').length );
		$("#APPROVER_IMG_"+rowCountFinal).attr('style','display:block');
		maxIDArr.push(j);
	}
}



var maxNo=0;
function remApproverMatrix(rowCountFinal){
	if($('#approverDetTable tr').length>2){
		$('#OPROW_'+rowCountFinal).remove();
	}else{
		var trs=$("#approverDetTable").find("tbody>tr");
		var tr_fstID=trs[1].id;
		var idNo=tr_fstID.substr(tr_fstID.indexOf('_')+1,tr_fstID.length);
		document.getElementById("empResultMessage_"+idNo).innerHTML ="** Mandatory";
		$("#empResultMessage_"+idNo).addClass("invalid_textAdmin");
		$("#empResultMessage_"+idNo).removeClass("valid_textAdmin");
		$("#empResultMessage_"+idNo).show();	
		$("#APPROVER_IMG_"+idNo).attr('style','display:block');
	}	
}

var maxBuildNo=0;
function removeBuildingRowForITOrISorAdmin(rowCountFinal){
	
	if($('#buildingDetTable tr').length>2){
		$('#BuildingTR_'+rowCountFinal).remove();		
	}else{		
		var trs=$("#buildingDetTable").find("tbody>tr");
		var tr_fstID=trs[1].id;
		var idNo=tr_fstID.substr(tr_fstID.indexOf('_')+1,tr_fstID.length);
		
		document.getElementById("errorMsg_"+idNo).innerHTML ="** Mandatory";
		$("#errorMsg_"+idNo).addClass("invalid_textAdmin");
		$("#errorMsg_"+idNo).removeClass("valid_textAdmin");
		$("#errorMsgTD_"+idNo).attr('style','display:block');
		$("#errorMsg_"+idNo).show();	
		
	}
}

/*******************************************************Validation and insertion first step********************************/
function validateRequestStep1(){
	
	isValidCCC=1;
	isValidTime=1;
	hourDiffZeroCount=0;
	/******************************************************STEP1:Validate Function Country and City**************************************************/
	var validateFldArray=new Array();	
		validateFldArray.push('function');
		validateFldArray.push('country');
		validateFldArray.push('city');
		
		$.each(validateFldArray, function(index, value) {			
			var inputType = $('#'+value).attr('type');
			$('#TextFieldSpan'+value+'').text('');
			if(inputType.indexOf('select')==0){				
				if($('#'+value).val()==0 || $('#'+value).val()=='Please Select'){					
					$('#'+value).after('<span class="invalid_textAdmin" id="TextFieldSpan'+value+'" >'+'* Mandatory!!'+''+'</span>');		
					isValidCCC=0;
				}
			}
			if(inputType.indexOf('select')<0){	
				if($('#'+value).val().length==0){
					$('#'+value+'').after('<span class="invalid_textAdmin" id="TextFieldSpan'+value+'" >'+'* Mandatory!!'+''+'</span>');
					isValidCCC=0;
				}
				
			}			
		});
		
		
	/******************************************************END STEP1:Validate Function Country and City**************************************************/
		
	/*******************************************************Find visible tables**************************************************************************/			
	var tableNameArray=new Array();
		tableNameArray.push('buildingDetTable');
		tableNameArray.push('operatingTimeDetTable');
		tableNameArray.push('approverDetTable');
		tableNameArray.push('operatingTimeHolidayTable');
		
	var visibleTableArray=new Array();		
		$.each(tableNameArray, function(index, value) {
			if($("#"+value).is(":visible")){
				visibleTableArray.push(value);
			}
		});		
	/*******************************************************END:Find visible tables**************************************************************************/			
		var colArray=new Array();	
		var isTimeValid=1;
		var isBuildgValid=1;
	
	/*******************************************************Find invalid fields and show Error Message****************************************************/
		if(visibleTableArray.length>0){
		$.each(visibleTableArray, function(index, tableName) {		
		
			var numCols = $("#"+tableName).find('tr')[0].cells.length;	
			var trs = $("#"+tableName).find("tbody>tr");
			
	/*******************************************************IT/Admin :Building Table Validation***********************************************************/
			if(tableName=='buildingDetTable'){				
				var inputsArrID=new Array();
				inputsArrID.push('BuildingTD_');
				inputsArrID.push('FloorTD_');
				inputsArrID.push('ODCTD_');
				if(numCols<=2){
					inputsArrID.splice($.inArray('ODCTD_',inputsArrID),1);					
				}	
				
				var vldArr=new Array();
				for(var i=1;i<trs.length;i++){	
					isBuildgValid=1;
					$.each(inputsArrID, function(index, value){
						var inputID=value+i;	
						if($("#"+inputID).length>0){
						if($("#"+inputID).val().length<=0){							
						isBuildgValid=0;
						return false;
						}
						}
					});
					if(isBuildgValid==0){
						vldArr.push(i);
					}
					
				}
			
				if(vldArr.length==(trs.length-1)){
					for(var i=1;i<trs.length;i++){
						$.each(inputsArrID, function(index, value){
							var inputID=value+i;
							$("#"+inputID).css('background-color', '#fbc2a0');
							isValidCCC=0;
						});
					}
					
					$("#errorMsg_"+1).text('Enter valid building/floor/ODC detail. !!');
					$("#errorMsg_"+1).addClass("invalid_textAdmin");
					$("#errorMsgTD_"+1).attr('style','display:block');
					isValidCCC=0;
				}
				
				
			
			}
	/*****************************************************END :IT/Admin :Building Table Validation*******************************************/
	/*****************************************************Operating Time Validation**********************************************************/
			if(tableName=='operatingTimeDetTable'){
				var inputsArrID=new Array();
				inputsArrID.push('hour_startTime_');
				inputsArrID.push('minute_startTime_');				
				inputsArrID.push('hour_endTime_');
				inputsArrID.push('minute_endTime_');
				
				inputsArrID.push('hour_startTime_');
				inputsArrID.push('minute_startTime_');				
				inputsArrID.push('hour_endTime_');
				inputsArrID.push('minute_endTime_');				
				for(var i=0;i<trs.length;i++){
					$.each(inputsArrID, function(index, value){
						var inputID=value+i;
						if($("#"+inputID+" option:selected").text()=="HH" || $("#"+inputID+" option:selected").text()=="MM"){							
							$("#"+inputID).css('background-color', '#fbc2a0');
							$("#errorMsgTime_"+i).text('Select value for highlighted fields!');
							$("#errorMsgTime_"+i).addClass("invalid_textAdmin");
							$("#errorMsgTDTime_"+i).attr('style','display:block');
						
							isTimeValid=0;
							isValidTime=0;
							
					}
					});
					
					if(isTimeValid==1){					
						var startTimeHr=$("#hour_startTime_"+i+" :selected").text();						
						var startTimeMin=$("#minute_startTime_"+i+" :selected").text();
						
						var endTimeHr=$("#hour_endTime_"+i+" :selected").text();
						var endTimeMin=$("#minute_endTime_"+i+" :selected").text();
						
						var startTime='';
						startTime=startTimeHr+':'+startTimeMin;
						var endTime='';
						endTime=endTimeHr+':'+endTimeMin;
						
						validateOperatingTimeWindow(startTime,endTime,i);						
						
						
					}
					isTimeValid=1;
				}
				
			}
	/*****************************************************END:Operating Time Validation**********************************************************/	
	/*****************************************************Approver Detail Validation*************************************************************/
			if(tableName=='approverDetTable'){
				var inputsArrID=new Array();
				inputsArrID.push('APPROVER_');
				for(var i=1;i<trs.length;i++){
					$.each(inputsArrID, function(index, value){						
					var trID=trs[i].id;
					var rowID=trID.substr(trID.lastIndexOf('_')+1,trID.length);					
					var inputID=value+rowID;					
					ValidateEmpId($("#"+inputID).val(),rowID);					
					});									
				}
				
				for(var i=1;i<trs.length;i++){
					var trID=trs[i].id;
					var rowID=trID.substr(trID.lastIndexOf('_')+1,trID.length);	
					var inputID="APPROVER_"+rowID;
					var empID=$("#"+inputID).val();
					for(var j=1;j<trs.length;j++){
						if(i!=j){
							var trIDNxt=trs[j].id;
							var rowIDNxt=trIDNxt.substr(trIDNxt.lastIndexOf('_')+1,trIDNxt.length);	
							var inputIDNxt="APPROVER_"+rowIDNxt;
							var empIDNxt=$("#"+inputIDNxt).val();
							if(empIDNxt==empID){	
								
								if($("#empResultMessage_"+(rowID)).hasClass("valid_textAdmin") ){								
									
									var empErrVal=document.getElementById("empResultMessage_"+rowID).innerHTML;
									document.getElementById("empResultMessage_"+rowID).innerHTML=empErrVal +'  (Duplicate Value)';
									$("#empResultMessage_"+rowID).removeClass("valid_textAdmin");
									$("#empResultMessage_"+rowID).addClass("invalid_textAdmin");
									$("#empResultMessage_"+rowID).show();	
									$("#APPROVER_"+rowID).css('background-color', '#fbc2a0');
									if( $("#empResultMessage_"+(rowIDNxt)).hasClass("valid_textAdmin") ){
										
										var empErrValNxt=document.getElementById("empResultMessage_"+rowIDNxt).innerHTML;
										document.getElementById("empResultMessage_"+rowIDNxt).innerHTML=empErrValNxt +'  (Duplicate Value)';
										$("#empResultMessage_"+rowIDNxt).removeClass("valid_textAdmin");
										$("#empResultMessage_"+rowIDNxt).addClass("invalid_textAdmin");
										$("#empResultMessage_"+rowIDNxt).show();
										$("#APPROVER_"+rowIDNxt).css('background-color', '#fbc2a0');
										isValidCCC=0;
									}
								}
							}
						}						
					}
				}
				
				
			}
	/*****************************************************END:Approver Detail Validation**********************************************************/		
			
				if(tableName=='operatingTimeHolidayTable'){
					//Error message Name:holidayResultMessage_1
					if($.trim($("#Holiday_1").val()).length==0){
						$("#Holiday_1").css('background-color', '#fbc2a0');
						$("#holidayResultMessage_1").text('Mandatory!!');
						$("#holidayResultMessage_1").addClass('invalid_textAdmin');
						$("#holidayResultMessage_1").attr('style','display:block');
						isValidCCC=0;
					}
				}
			});
		}
	/*END:Find invalid fields and show Error Message*/		
		if(hourDiffZeroCount>0){
			if(confirm("Selected date difference is 24 hours.Do u want to reset the date to 24 hours?")){        		
	    		
	    	}else{
			/*$("#errorMsgTime_"+i).text('Selected value is greater than 8 hrs!');
			$("#errorMsgTime_"+i).addClass("invalid_textAdmin");
			$("#errorMsgTDTime_"+i).attr('style','display:block');*/
			isValidTime=0;
	    	}
		}
		if(isValidCCC==1 && isValidTime==1){		
			var functionName=$('#function option:selected').text();
			getJSONToAddLocationStep1(visibleTableArray);		
			isValidReq=1;
			$("#STEP1_TABLE").find("select,input,button,textarea").attr("disabled", "disabled");			
		}		
}



function validateOperatingTimeWindow(startTime,endTime,i){
	
	
	if(startTime.length>1 && endTime.length>1){
		var sArr=startTime.split(':');
		var eArr=endTime.split(':');
		//var seconds = (sArr[0]) * 60+ (sArr[1]) * 60 + (sArr[2]); 
		
		var sSec=(sArr[0]*60)+(sArr[1]*1);
		var eSec=(eArr[0]*60)+(eArr[1]*1);
		
		var timeDiff=(sSec-eSec);
		var timeStart = new Date("01/01/2007 " + startTime).getHours();
        var timeEnd = new Date("01/01/2007 " + endTime).getHours();
        var hourDiff = timeEnd - timeStart;   
        
        if(hourDiff<8 && hourDiff>0){
			$("#errorMsgTime_"+i).text('Selected value is lesser than 8 hrs!');
			$("#errorMsgTime_"+i).addClass("invalid_textAdmin");
			$("#errorMsgTDTime_"+i).attr('style','display:block');
			isValidTime=0;
		}
        
        if(hourDiff==0){
                	hourDiffZeroCount++;
		}
		
	}
	
	
}

function removeErrorClass(elementID){
	var id=elementID.indexOf('_');
	var ele=elementID.substr(id+1,elementID.length);
	$("#"+elementID).css('background-color', 'white');
	$("#errorMsg_"+ele).text('');
	$("#errorMsg_"+ele).addClass("valid_textAdmin");
	$("#errorMsgTD_"+ele).attr('style','display:none');
}
function removeErrorClassTime(elementID){	
	$("#"+elementID).css('background-color', 'white');
	var id=elementID.lastIndexOf('_');
	var ele=elementID.substr(id+1,elementID.length);	
	$("#errorMsgTime_"+ele).text('');
	$("#errorMsgTime_"+ele).addClass("valid_textAdmin");
	$("#errorMsgTDTime_"+ele).attr('style','display:none');
}
function validateCity(){
	if($("#city").length>0){			
		$("#TextFieldSpancity").remove();
	}
}

function getJSONToAddLocationStep1(visibleTableArray){
	var jsonToInsert="";
	var jsonObj='';
	var jsonObjTime="";
	var jsonToInsertTime="";
	var jsonToInsertAppr="";
	var jsonObjAppr="";
	var jsonToInsertGrp="";
	var jsonObjGrp="";
	var jsonToInsertGrpDept="";
	var jsonObjGrpDept="";
	var jsonToInsertGrpLoc="";
	var jsonObjGrpLoc="";
	var maxIDArrIndex=1;
	$.each(visibleTableArray, function(index, tableName) {
		var trs = $("#"+tableName).find("tbody>tr");	
		if(tableName=='buildingDetTable'){
			jsonToInsert="\"BuildJSON\":{";
			var inputsArrID=new Array();
			inputsArrID.push('BuildingTD_');
			inputsArrID.push('FloorTD_');
			inputsArrID.push('ODCTD_');
			
			for(var i=0;i<maxIDArrBuilding.length;i++){
				maxIDArrIndex=maxIDArrBuilding[i];
				if(maxIDArrIndex<maxIDArrBuilding[i+1]){
					maxIDArrIndex=maxIDArrBuilding[i+1];
				}
			}
			var count=1;
			for(var i=1;i<=maxIDArrIndex;i++){
				
				if($('#function option:selected').text()=='IT Infrastructure Management'){
					
					if($("#"+inputsArrID[0]+i).length>0 && $("#"+inputsArrID[1]+i).length>0 ){						
						if(jQuery.trim($("#"+inputsArrID[0]+i).val()).length>0 && jQuery.trim($("#"+inputsArrID[1]+i).val()).length>0){		
							if(count==1){
							jsonObj+="\""+count+"\":";
							}else{
							jsonObj+=",\""+count+"\":";
							}
							jsonObj+="{\"";								
							jsonObj+=inputsArrID[0]+count +"\":\""+jQuery.trim($("#"+inputsArrID[0]+i).val());	
							jsonObj+="\",\"";	
							jsonObj+=inputsArrID[1]+count +"\":\""+jQuery.trim($("#"+inputsArrID[1]+i).val());
							jsonObj+="\"}";	
							count++;
						}
					}
				}else if($('#function option:selected').text()=='Admin'){
						if($("#"+inputsArrID[0]+i).length>0 && $("#"+inputsArrID[1]+i).length>0 && $("#"+inputsArrID[2]+i).length>0){						
						if(jQuery.trim($("#"+inputsArrID[0]+i).val()).length>0 && jQuery.trim($("#"+inputsArrID[1]+i).val()).length>0 && jQuery.trim($("#"+inputsArrID[2]+i).val()).length>0){							
							jsonObj+="\""+i+"\":";
							jsonObj+="{\"";								
							jsonObj+=inputsArrID[0]+i +"\":\""+jQuery.trim($("#"+inputsArrID[0]+i).val());	
							jsonObj+="\",\"";	
							jsonObj+=inputsArrID[1]+i +"\":\""+jQuery.trim($("#"+inputsArrID[1]+i).val());
							jsonObj+="\",\"";	
							jsonObj+=inputsArrID[2]+i +"\":\""+jQuery.trim($("#"+inputsArrID[2]+i).val());
							jsonObj+="\"}";	
							
							if(i!=trs.length-1){
								jsonObj+=",";	
							}
						}
					}
					
				}
				
			}
			
			jsonToInsert+=jsonObj;
			jsonToInsert+="}";	
			//End of Build Json
		} 
		if(tableName=='operatingTimeDetTable'){
			var inputsArrID=new Array();
			var startTimeHr="";
			var startTimeMin="";
			var endTimeHr="";
			var endTimeMin="";
			var day="";
			var nxtDay="";
			var startTime='';
			var endTime='';
			
			inputsArrID.push('DayTD_');
			inputsArrID.push('hour_startTime_');
			inputsArrID.push('minute_startTime_');				
			inputsArrID.push('hour_endTime_');
			inputsArrID.push('minute_endTime_');
			inputsArrID.push('nextDay_');
			inputsArrID.push('hour_startTime_');
			inputsArrID.push('minute_startTime_');				
			inputsArrID.push('hour_endTime_');
			inputsArrID.push('minute_endTime_');
			jsonToInsertTime="\"BuildTimeJSON\":{";
			jsonObjTime+="";		
		
			for(var i=0;i<trs.length;i++){									
				for(var j=0;j<inputsArrID.length;j++){
					if($("#"+inputsArrID[j]+i).length>0){
						if(jQuery.trim($("#"+inputsArrID[j]+i).val()).length>0){						
							if((inputsArrID[j]+i).indexOf('DayTD_')>=0){
								day=jQuery.trim($("#"+inputsArrID[j]+i).val());
							}else 
							if((inputsArrID[j]).indexOf('nextDay_')>=0){
								nxtDay=jQuery.trim($("#"+inputsArrID[j]+i).val());
							}else							
							if((inputsArrID[j]).indexOf('hour_startTime_')>=0){								
							 startTimeHr=$("#hour_startTime_"+i+" :selected").text();
							}else
							if((inputsArrID[j]).indexOf('minute_startTime_')>=0){
							 startTimeMin=$("#minute_startTime_"+i+" :selected").text();
							}else
							if((inputsArrID[j]).indexOf('hour_endTime_')>=0){
							 endTimeHr=$("#hour_endTime_"+i+" :selected").text();
							}else if ((inputsArrID[j]).indexOf('minute_endTime_')>=0){
							 endTimeMin=$("#minute_endTime_"+i+" :selected").text();
							}
							
							
							
							
							}							
							
							
						}
					}
				if(startTimeHr.length>0 && startTimeMin.length>0 && endTimeHr.length>0 && endTimeMin.length>0 ){
					if(i==0){
					jsonObjTime+="\""+i+"\":";
					}else{
						jsonObjTime+=",\""+i+"\":";
					}
					jsonObjTime+="{";	
					jsonObjTime+=("\"DayTD_"+i)+"\":\""+day+"\",";
					jsonObjTime+=("\"nextDay_"+i)+"\":\""+nxtDay+"\",";
					startTime=startTimeHr+':'+startTimeMin;							
					endTime=endTimeHr+':'+endTimeMin;
					jsonObjTime+="\"startTime_"+i+"\":\""+startTime+"\",";
					jsonObjTime+="\"endTime_"+i+"\":\""+endTime+"\"";
					jsonObjTime+="}";
					
				}
				
			}				
			jsonToInsertTime=jsonToInsertTime+jsonObjTime;
			jsonToInsertTime=jsonToInsertTime+"}";				
			//End of Time Json
		}

//		ApproverDetTable
		if(tableName=='approverDetTable'){
			var inputsArrID=new Array();
			inputsArrID.push('APPROVER_');		
			inputsArrID.push('APPROVER_ROLE_');
			var maxIndex=1;			
			jsonToInsertAppr="\"BuildApproverJSON\":{";
			jsonObjAppr="";
			//For to check Max Index
			var appr="";
			var apprRole="";
			for(var i=0;i<maxIDArr.length;i++){
				maxIndex=maxIDArr[i];
				if(maxIndex<maxIDArr[i+1]){
					maxIndex=maxIDArr[i+1];
				}
			}//End For to check Max Index
			var count=0;
			//For to Iterate thru the Approver table	
			
			for(var i=1;i<=maxIndex;i++){				
				for(var j=0;j<inputsArrID.length;j+=2){
					
					if($("#"+inputsArrID[j]+i).length>0){						
								
						if(jQuery.trim($("#"+inputsArrID[j]+i).val()).length>0){
							
								appr=jQuery.trim($("#"+inputsArrID[j]+i).val());
								apprRole=jQuery.trim($("#"+inputsArrID[j+1]+i).val());
							
						}
					}
					
					if(count==0){
						jsonObjAppr+="\""+count+"\":{\"APPROVER\":\""+appr+"\",\"APPROVER_ROLE\":\""+apprRole+"\"";	
						count++;
					}else{
						jsonObjAppr+=",\""+count+"\":{\"APPROVER\":\""+appr+"\",\"APPROVER_ROLE\":\""+apprRole+"\"";	
						count++;
					}
					jsonObjAppr+="}";
				}
			}//End For to Iterate thru the Approver table
			jsonToInsertAppr=jsonToInsertAppr+jsonObjAppr;
			jsonToInsertAppr=jsonToInsertAppr+"}";	
			
			
		}//End Of ApproverDetTable  JSON
		
});

var functionVal=$("#function").val();
var countryVal=$("#country").val();
var newCountryVal=$("#newCountry").val();
var cityVal=$("#city").val();
var countryName=$('#country option:selected').text();
var holidayExclusionVal=$("#Holiday_1").val().split(',');

finalJson+='\"function\":\"'+functionVal+'\",';
finalJson+='\"country\":\"'+countryVal+'\",';
finalJson+='\"newCountry\":\"'+newCountryVal+'\",';
finalJson+='\"countryName\":\"'+countryName+'\",';
finalJson+='\"city\":\"'+cityVal+'\",';
finalJson+='\"holiday\":\"'+holidayExclusionVal+'\",';


 	var finalArr=new Array();
 	
 	if(jsonToInsert.length>0){ 		
 		finalArr.push(jsonToInsert);
 	}
 	if(jsonToInsertTime.length>0){ 		
 		finalArr.push(jsonToInsertTime);
 	}
 	if(jsonToInsertAppr.length>0){ 		
 		finalArr.push(jsonToInsertAppr);
 	}
 	$.each(finalArr,function(index,value){ 				
		finalJson+=value; 		 	
		if((finalArr.length-1)!=index){
			finalJson+=",";
		}
 	});
 	finalJson+="}";
	
	insertLocationDetStep1(finalJson);
}

function insertLocationDetStep1(finalJson){
	var functionName=$('#function option:selected').text();
	var functionId=$('#function').val();
	$.ajaxSetup({ cache: false });
	$.postJSON("insertAdminConsole_NewLocation_Step1.htm",{jsonString:finalJson},function(data){
		if(data=="Error"){
			parent.parent.jbarOnFailure(data.error);
			$("#slaId").val(slaId);
		}else if(data.res=="1"){
			parent.parent.jbarOnSuccess(data.messsage);		
			var slaId=data.slaID;
			var locationId=data.locationId;
			$("#slaId").val(slaId);
			$("#locationId").val(locationId);
		}else if(data.res=="2"){
			parent.parent.jbarOnFailure(data.error);			
		}else if(data.res=="3"){
			parent.parent.jbarOnFailure(data.error);	
			$("#STEP1_TABLE").find("select,input,button,textarea").removeAttr("disabled");	
		}
			
	});
	
	if($("#slaId").length>0){
		showVisibleDivs(functionName,functionId);
		$("#submitButtonFirstTR").hide();
	}
	showVisibleDivs(functionName,functionId);
	$("#submitButtonFirstTR").hide();
}

function removeMsgClass(EmpIdField,rowCountFinal){
	if(EmpIdField=='Holiday_1'){
		//The below code is to shift the focus from date field to City as the Date field is Readonly and on delete the page goes to prev page.To 
		//prevent this focus is shifted.
		$("#city").focus();
		if($("#holidayResultMessage_1").hasClass("invalid_textAdmin")){	
			document.getElementById("holidayResultMessage_1").innerHTML ="";		
			$("#holidayResultMessage_1").removeClass("invalid_textAdmin");
			$("#Holiday_1").css('background-color', 'white');
		}
	}else{
	if($("#empResultMessage_"+(rowCountFinal)).hasClass("invalid_textAdmin")){	
		$("#APPROVER_"+rowCountFinal).val('');
		document.getElementById("empResultMessage_"+rowCountFinal).innerHTML ="";
		$("#empResultMessage_"+(rowCountFinal)).removeClass("invalid_textAdmin");
		$("#APPROVER_"+rowCountFinal).css('background-color', 'white');
	}
	}
}

function ValidateEmpId(EmpIdField,rowCountFinal){	
	var EmpId=EmpIdField;
	if(/^[a-zA-Z0-9_.]+$/.test(EmpId) ){	
		$.getJSON('validateEmployee.htm',{employeeId:EmpId},function(Emp){
			if(Emp.NAME){				
				document.getElementById("empResultMessage_"+rowCountFinal).innerHTML = Emp.NAME ;
				$("#empResultMessage_"+rowCountFinal).removeClass("invalid_textAdmin");
				$("#empResultMessage_"+rowCountFinal).addClass("valid_textAdmin");
				$("#empResultMessage_"+rowCountFinal).show();	
				$("#APPROVER_"+rowCountFinal).css('background-color', '#fbc2a0');
			}else{
				document.getElementById("empResultMessage_"+rowCountFinal).innerHTML ="Please enter valid Employee id";
				$("#empResultMessage_"+rowCountFinal).removeClass("valid_textAdmin");
				$("#empResultMessage_"+rowCountFinal).addClass("invalid_textAdmin");
				$("#empResultMessage_"+rowCountFinal).show();	
				$("#APPROVER_"+rowCountFinal).css('background-color', '#fbc2a0');
			}
		});
	}else{
		document.getElementById("empResultMessage_"+rowCountFinal).innerHTML ="Please enter valid Employee id";
		$("#empResultMessage_"+rowCountFinal).removeClass("valid_textAdmin");
		$("#empResultMessage_"+rowCountFinal).addClass("invalid_textAdmin");
		$("#empResultMessage_"+rowCountFinal).show();	
		$("#APPROVER_"+rowCountFinal).css('background-color', '#fbc2a0');
	}
	
	
}	
/*********************************************************************saveNewLocationStep1*****************************************************/


function showVisibleDivs(functionName,functionId){
	
	$("#STEP2_TABLE").show();
	if(functionName=='Admin'){
			$('#GROUP_LOC_TR').show();
			$('#GROUP_LOC_TR_2').show();
			//TODO:getGroupLocTableStep1(functionId,0,9,'functionChange');
	}else if($('#function option:selected').text()=='HR'){
			$("#adminTableStep1").attr('style','display:none');
			$("#adminTableStep2").attr('style','display:block');
			getGroupDetailTable(functionId);
			//TODO:getGroupDetailTableStep1(functionId,0,9,'functionChange');
			//TODO:getGroupDefAssignmentTable(functionId,1,10,'functionChange');		
	}else if($('#function option:selected').text()=='Delivery' || $('#function option:selected').text()=='Finance' || $('#function option:selected').text()=='IGATE Corporate University' ||$('#function option:selected').text()=='Quality'){ ////TODO 10/16/2014 Nisha
			$('#GROUP_TR').show();
			$('#GROUP_TR_2').show();
			//TODO:getGroupDetailTableStep1(functionId,0,9,'functionChange');
	}else{
		$("#STEP2_TABLE").hide();
	}
}

function getOperatingHolidayTable(functionChange){
	//Table Name:operatingTimeHolidayTable
	var holidayHTML='';	
	holidayHTML+='<tr><th>Click on Calendar icon to select the dates(yyyy-mm-dd)<span class="red_text">* </span></th></tr>';
	if(functionChange=='functionChange'){
		holidayHTML+="<tr id=\"HolidayRow_1\"><td align='center' id='HolidayTD_1'><textarea style='font-size: 12px;' size='100%' rows='3' cols='110' id='Holiday_1' value='' readonly='readonly' onfocus=\"removeMsgClass(\'Holiday_1\',\'1\')\"></textarea></td><td class='borderlessTextInput'><span id='holidayResultMessage_1' class='invalid_textAdmin'></span></td></tr>";
		$("#operatingTimeHolidayTable").html(holidayHTML);
		 var date = new Date(); 
		    var y = date.getFullYear(); 
		$('#Holiday_1').datepick({multiSelect: 25, showTrigger: '#callImg',dateFormat: 'yyyy-mm-dd',  minDate: new Date(y, 1 - 1, 01), 
		    maxDate: new Date(y+1, 12 - 1, 31),changeMonth: true, yearRange: 'c-0:c+1',alignment: 'topLeft'});		
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





/************************************************************Group Mapping**************************************************************************/
function getGroupDetailTable(functionId){
	$.ajaxSetup({ cache: false });
	$.get('ADMIN_Console_GrpListDetail.htm?functionId=' + functionId, function (data) {
		 $("#GRP_DET_JMESA_TR").removeClass();
		 $("#GRP_DET_JMESA").removeClass();        
         $("#GRP_DET_JMESA").html("");      
         $("#GRP_DET_JMESA").html(data);
     });
	$.ajaxSetup({ cache: false });
	$.get('ADMIN_Console_Dept_GrpListDetail.htm?functionId=' + functionId, function (data) {
		 $("#DEPT_GRP_DET_JMESA_TR").removeClass();
		 $("#DEPT_GRP_DET_JMESA").removeClass();        
         $("#DEPT_GRP_DET_JMESA").html("");      
         $("#DEPT_GRP_DET_JMESA").html(data);
     });
	
	
}
//on change of group add the selected val
function editGrp(categoryIde,subCatgeoryIde,grpIde,rowCount){
	var functionId=$("#function").val();	
	
	var noOfRows=$('#Group_Detail tr').length;	
	var trIde=rowCount%noOfRows;
	$("#Group_Detail_row"+trIde).css('background-color', '#B7AFA3');
	
	$.getJSON('getGroup.htm',{functionId:functionId},function(response){
		var html="<select id=\"grpIDJ_"+rowCount+"\" onchange=\"addCheckboxArronChange(\'grpIDJ_"+rowCount+"\',"+subCatgeoryIde+")\"  class=\"myTextInputForSelect\">";	
		for(var i=0;i<response.length;i++){
				html+="<option value=\""+response[i].GROUP_ID+"\">"+response[i].GROUP_NAME+"</option>";
		}
		html+="</select>";
		$("#grpIDJ_"+rowCount).html('');		
		$("#grpIDJ_"+rowCount).replaceWith(html);		
	});
	$("#checkboxJ"+rowCount).attr("checked",true);		
	addCheckboxArr(group_CheckboxArr,"group_CheckboxArr",rowCount,subCatgeoryIde,'');
	
}
//on change of group add the selected val
function editGrpDept(categoryIde,subCatgeoryIde,grpIde,rowCount,deptIde){
	var functionId=$("#function").val();	
	
	var noOfRows=($('#Group_Detail_Dept tr').length)-5;	
	var trIde=rowCount%noOfRows;
	$("#Group_Detail_Dept_row"+trIde).css('background-color', '#B7AFA3');
	
	
	
	$.getJSON('getGroup.htm',{functionId:functionId},function(response){
		var html="<select id=\"grpIdDept_"+rowCount+"\" onchange=\"addCheckboxArronChangeDept(\'grpIdDept_"+rowCount+"\',"+subCatgeoryIde+","+deptIde+")\"  class=\"myTextInputForSelect\">";	
		for(var i=0;i<response.length;i++){
				html+="<option value=\""+response[i].GROUP_ID+"\">"+response[i].GROUP_NAME+"</option>";
		}
		html+="</select>";
		$("#grpIdDept_"+rowCount).html('');		
		$("#grpIdDept_"+rowCount).replaceWith(html);		
	});
	$("#checkboxDept"+rowCount).attr("checked",true);		
	addCheckboxArr(group_CheckboxDeptArr,"group_CheckboxDeptArr",rowCount,subCatgeoryIde,deptIde);
	
}

function undoGrp(categoryIde,subCatgeoryIde,grpIde,rowCount){
	//Check if rowcount is even /odd to change bg color of the table row	
	
	var noOfRows=($('#Group_Detail tr').length) -5;
	var trIde=rowCount%noOfRows;
		if(trIde%2==0){
			$("#Group_Detail_row"+trIde).css('background-color', '#F5F5E0');
		}else{
			$("#Group_Detail_row"+trIde).css('background-color', '#F5F5E0');
		}
	
	
	$.getJSON('getGroupsForSubCategoryAC.htm',{subCategoryID:subCatgeoryIde},function(response){
		var html="<select id=\"grpIDJ_"+rowCount+"\" onchange=\"\"  class=\"myTextInputForSelect\">";	
		for(var i=0;i<response.length;i++){
				html+="<option value=\""+response[i].GROUP_ID+"\">"+response[i].GROUP_NAME+"</option>";
		}
		html+="</select>";
		$("#grpIDJ_"+rowCount).html('');		
		$("#grpIDJ_"+rowCount).replaceWith(html);	
	});
	$("#checkboxJ"+rowCount).attr("checked",false);
	
	removeCheckboxArr(group_DetailArr,"group_DetailArr",rowCount,subCatgeoryIde,'');
	removeCheckboxArr(group_CheckboxArr,"group_CheckboxArr",rowCount,subCatgeoryIde,'');
	
}

function undoGrpDept(categoryIde,subCatgeoryIde,grpIde,rowCount,deptIde){
	//Check if rowcount is even /odd to change bg color of the table row	
	
	var noOfRows=($('#Group_Detail_Dept tr').length)-5;
	
	var trIde=rowCount%noOfRows;
	if(trIde%2==0){
		$("#Group_Detail_Dept_row"+trIde).css('background-color', '#F5F5E0');
	}else{
		$("#Group_Detail_Dept_row"+trIde).css('background-color', '#ffffff');
	}
	
	$.getJSON('getGroupsForSubCategoryAC.htm',{subCategoryID:subCatgeoryIde},function(response){
		var html="<select id=\"grpIdDept_"+rowCount+"\" onchange=\"\"  class=\"myTextInputForSelect\">";	
		for(var i=0;i<response.length;i++){
				html+="<option value=\""+response[i].GROUP_ID+"\">"+response[i].GROUP_NAME+"</option>";
		}
		html+="</select>";
		$("#grpIdDept_"+rowCount).html('');		
		$("#grpIdDept_"+rowCount).replaceWith(html);	
	});
	$("#checkboxDept"+rowCount).attr("checked",false);	
	removeCheckboxArr(group_DeptArr,"group_DeptArr",rowCount,subCatgeoryIde,deptIde);
	removeCheckboxArr(group_CheckboxDeptArr,"group_CheckboxDeptArr",rowCount,subCatgeoryIde,deptIde);	
}

function editCheckBoxAC(categoryIde,subCatgeoryIde,grpIde,rowCount){
	
	var checkboxVal=$("#checkboxJ"+rowCount).val();
	var rowcnt=rowCount;
	if($("#checkboxJ"+rowCount).attr('checked')==false){
		
		var noOfRows=($('#Group_Detail tr').length)-5;		
		var trIde=rowcnt%noOfRows;
		if(trIde%2==0){
				$("#Group_Detail_row"+trIde).css('background-color', '#F5F5E0');
		}else{
				$("#Group_Detail_row"+trIde).css('background-color', '#ffffff');
		}
		
		
		$.getJSON('getGroupsForSubCategoryAC.htm',{subCategoryID:subCatgeoryIde},function(response){
			var html="<select id=\"grpIDJ_"+rowCount+"\" onchange=\"\"  class=\"myTextInputForSelect\">";	
			for(var i=0;i<response.length;i++){
					html+="<option value=\""+response[i].GROUP_ID+"\">"+response[i].GROUP_NAME+"</option>";
			}
			html+="</select>";
			$("#grpIDJ_"+rowCount).html('');		
			$("#grpIDJ_"+rowCount).replaceWith(html);	
		});		
		//remove the grp id from the array		
		
		removeCheckboxArr(group_DetailArr,"group_DetailArr",rowCount,subCatgeoryIde,'');
		removeCheckboxArr(group_CheckboxArr,"group_CheckboxArr",rowCount,subCatgeoryIde,'');
		
		
	}else{		
		
		var noOfRows=($('#Group_Detail tr').length)-5;
		var trIde=rowcnt%noOfRows;			
		$("#Group_Detail_row"+trIde).css('background-color', '#B7AFA3');
		
		//Add value and checkbox id
		
		addCheckboxArr(group_DetailArr,"group_DetailArr",rowCount,subCatgeoryIde,'');
		addCheckboxArr(group_CheckboxArr,"group_CheckboxArr",rowCount,subCatgeoryIde,'');
		
		//TODO: If reqd ,populate the full groups dropdown		
	}	
}

function editCheckBoxDeptAC(categoryIde,subCatgeoryIde,grpIde,rowCount,departmentIde){
	
	var checkboxVal=$("#checkboxDept"+rowCount).val();
	
	if($("#checkboxDept"+rowCount).attr('checked')==false){
		
		var noOfRows=($('#Group_Detail_Dept tr').length)-5;		
		var trIde=rowCount%noOfRows;
			if(trIde%2==0){
				$("#Group_Detail_Dept_row"+trIde).css('background-color', '#F5F5E0');
			}else{
				$("#Group_Detail_Dept_row"+trIde).css('background-color', '#ffffff');
			}
	
		$.getJSON('getGroupsForSubCategoryAC.htm',{subCategoryID:subCatgeoryIde},function(response){
			var html="<select id=\"grpIdDept_"+rowCount+"\" onchange=\"\"  class=\"myTextInputForSelect\">";	
			for(var i=0;i<response.length;i++){
					html+="<option value=\""+response[i].GROUP_ID+"\">"+response[i].GROUP_NAME+"</option>";
			}
			html+="</select>";
			$("#grpIdDept_"+rowCount).html('');		
			$("#grpIdDept_"+rowCount).replaceWith(html);	
		});		
		//remove the grp id from the array			
		removeCheckboxArr(group_DeptArr,"group_DeptArr",rowCount,subCatgeoryIde,departmentIde);		
		removeCheckboxArr(group_CheckboxDeptArr,"group_CheckboxDeptArr",rowCount,subCatgeoryIde,departmentIde);
	}else{		
		
		
		var noOfRows=($('#Group_Detail_Dept tr').length)-5;		
			var trIde=rowCount%noOfRows;
			$("#Group_Detail_Dept_row"+trIde).css('background-color', '#B7AFA3');
		
		//Add value and checkbox id
		addCheckboxArr(group_DeptArr,"group_DeptArr",rowCount,subCatgeoryIde,departmentIde);
		addCheckboxArr(group_CheckboxDeptArr,"group_CheckboxDeptArr",rowCount,subCatgeoryIde,departmentIde);
	}	
}

function editCheckBoxJAll(tableName,condn){	
	var checkBoxIde="";
	var checkBoxClass="";
	var flagValue=0;
	var noOfTrs=0;
	var startCount=0;
	var endCount=0;
	var pageno=1;
	
	var checkboxName="";
	if(tableName=="Group_Detail"){
		checkBoxIde="checkboxAllJ";
		checkBoxClass="group_detail_Checkbox";
		flagValue=isCheckedAll;	
		checkboxName="checkboxJ";
		pageno=page_no_grp;
		noOfTrs=($('#Group_Detail tr').length)-5;
	}else if(tableName=="Group_Detail_Dept"){
		checkBoxIde="checkboxAllDept";
		checkBoxClass="group_dept_Checkbox";
		flagValue=isCheckedAllDept;	
		checkboxName="checkboxDept";
		pageno=page_no_dept;
		noOfTrs=($('#Group_Detail_Dept tr').length)-5;
	}
	
	if(condn=="checkboxClick"){
		endCount=pageno*noOfTrs;
		startCount=(endCount-noOfTrs)+1;
		if($("#"+checkBoxIde).attr('checked')==true){			
			for(var i=startCount;i<=endCount;i++){
				$('#'+checkboxName+i).attr('checked', 'checked');
				var checkboxVals=$('#'+checkboxName+i).val();
				var splitArray = checkboxVals.split(",");		
				var subCategoryIde=splitArray[1];				
				if(tableName=="Group_Detail"){
						addCheckboxArr(group_DetailArr,"group_DetailArr",i,subCategoryIde,'');			
						addCheckboxArr(group_CheckboxArr,"group_CheckboxArr",i,subCategoryIde,'');
						
						var trIde=i%noOfTrs;				
						if(trIde==0){
							$("#Group_Detail_row"+noOfTrs).css('background-color', '#B7AFA3');
						}else{
							$("#Group_Detail_row"+trIde).css('background-color', '#B7AFA3');
						}
						
						
				}else if(tableName=="Group_Detail_Dept"){
					var deptIde=splitArray[3];
					addCheckboxArr(group_DeptArr,"group_DeptArr",i,subCategoryIde,deptIde);			
					addCheckboxArr(group_CheckboxDeptArr,"group_CheckboxDeptArr",i,subCategoryIde,deptIde);
					
					var trIde=i%noOfTrs;				
					if(trIde==0){
						$("#Group_Detail_Dept_row"+noOfTrs).css('background-color', '#B7AFA3');
					}else{
						$("#Group_Detail_Dept_row"+trIde).css('background-color', '#B7AFA3');
					}
					
				} 
			}
		}else{
			for(var i=startCount;i<=endCount;i++){
				$('#'+checkboxName+i).removeAttr('checked');
				var checkboxVals=$('#'+checkboxName+i).val();
				var splitArray = checkboxVals.split(",");		
				var subCategoryIde=splitArray[1];				
				if(tableName=="Group_Detail"){
						removeCheckboxArr(group_DetailArr,"group_DetailArr",i,subCategoryIde,'');			
						removeCheckboxArr(group_CheckboxArr,"group_CheckboxArr",i,subCategoryIde,'');
						var trIde=i%noOfTrs;				
						if(trIde==0){
							$("#Group_Detail_row"+noOfTrs).css('background-color', '#ffffff');
						}else{
							if(trIde%2==0){
								$("#Group_Detail_row"+trIde).css('background-color', '#F5F5E0');
							}else{
								$("#Group_Detail_row"+trIde).css('background-color', '#ffffff');
							}							
						}					
					
				}else if(tableName=="Group_Detail_Dept"){
					var deptIde=splitArray[3];
					removeCheckboxArr(group_DeptArr,"group_DeptArr",i,subCategoryIde,deptIde);			
					removeCheckboxArr(group_CheckboxDeptArr,"group_CheckboxDeptArr",i,subCategoryIde,deptIde);
					var trIde=i%noOfTrs;				
					if(trIde==0){					
						$("#Group_Detail_Dept_row"+noOfTrs).css('background-color', '#ffffff');
					}else{
						if(trIde%2==0){
							$("#Group_Detail_Dept_row"+trIde).css('background-color', '#F5F5E0');
						}else{
							$("#Group_Detail_Dept_row"+trIde).css('background-color', '#ffffff');
						}
					}
				} 
			}
		}
	}
	
	if(tableName=="Group_Detail"){
		isCheckedAll=flagValue;
	}else if(tableName=="Group_Detail_Dept"){
		isCheckedAllDept=flagValue;
	}	
	
}
function addCheckboxArr(arr,arryName,rowcountVal,subCatgeoryIde,deptIde){
	
	if(arryName=="group_DetailArr"){		
		var newArr=new Array();
		var newArr_SubCategory=new Array();
		var key="grpIDJ_"+rowcountVal;
		$.each(arr,function(id,value){			
			if(value.id!=key){
				newArr.push({
					id:value.id,
					value:value.value
					});						
				}		
		});
		$.each(group_SubCategoryArr,function(id,value){	
			if(value.id!=key){
				newArr_SubCategory.push({
					id:value.id,
					value:value.value
				});	
			}
		});
		
		group_DetailArr=new Array();
		group_DetailArr=newArr;
		group_SubCategoryArr=new Array();
		group_SubCategoryArr=newArr_SubCategory;
		group_DetailArr.push({
			id:key,
			value:$("#"+key).val()});
		group_SubCategoryArr.push({
			id:key,
			value:subCatgeoryIde
		});
	}
	
	if(arryName=="group_CheckboxArr"){		
		arr.push("checkboxJ"+rowcountVal);		
	}
	
	
	
	if(arryName=="group_DeptArr"){		
		var newArr=new Array();
		var newArr_deptSubCategory=new Array();
		var newArr_dept=new Array();
		
		var key="grpIdDept_"+rowcountVal;
		$.each(arr,function(id,value){			
			if(value.id!=key){
				newArr.push({
					id:value.id,
					value:value.value
					});						
				}		
		});
		$.each(group_SubCategoryDeptArr,function(id,value){	
			if(value.id!=key){
				newArr_deptSubCategory.push({
					id:value.id,
					value:value.value
				});	
			}
		});
		
		$.each(group_DeptAllArr,function(id,value){	
			if(value.id!=key){
				newArr_dept.push({
					id:value.id,
					value:value.value
				});	
			}
		});
		
		group_DeptArr=new Array();
		group_DeptArr=newArr;
		group_SubCategoryDeptArr=new Array();
		group_SubCategoryDeptArr=newArr_deptSubCategory;
		group_DeptAllArr=new Array();
		group_DeptAllArr=newArr_dept;
		
		group_DeptArr.push({
			id:key,
			value:$("#"+key).val()});
		
		group_SubCategoryDeptArr.push({
			id:key,
			value:subCatgeoryIde
		});
		group_DeptAllArr.push({
			id:key,
			value:deptIde
		});
	}
	if(arryName=="group_CheckboxDeptArr"){		
		arr.push("checkboxDept"+rowcountVal);		
	}
	
}

function addCheckboxArronChange(rowVal,subCategIDe){	
		var newArr=new Array();
		var newArr_SubCategory=new Array();
		var key=rowVal;
		$.each(group_DetailArr,function(id,value){			
			if(value.id!=key){
				newArr.push({
					id:value.id,
					value:value.value
					});						
				}		
		});
		$.each(group_SubCategoryArr,function(id,value){	
			if(value.id!=key){
				newArr_SubCategory.push({
					id:value.id,
					value:value.value
				});	
			}
		});
		
		
		
		group_DetailArr=new Array();
		group_DetailArr=newArr;
		group_SubCategoryArr=newArr_SubCategory;
		group_DetailArr.push({
			id:rowVal,
			value:$("#"+rowVal).val()});
		group_SubCategoryArr.push({
			id:rowVal,
			value:subCategIDe
		});
		
}

function addCheckboxArronChangeDept(rowVal,subCategIDe,deptIde){	
	var newArr=new Array();
	var newArr_SubCategory=new Array();
	var newArr_dept=new Array();
	
	var key=rowVal;
	$.each(group_DeptArr,function(id,value){			
		if(value.id!=key){
			newArr.push({
				id:value.id,
				value:value.value
				});						
			}		
	});
	$.each(group_SubCategoryDeptArr,function(id,value){	
		if(value.id!=key){
			newArr_SubCategory.push({
				id:value.id,
				value:value.value
			});	
		}
	});
	$.each(group_DeptAllArr,function(id,value){	
		if(value.id!=key){
			newArr_dept.push({
				id:value.id,
				value:value.value
			});	
		}
	});
	
	
	group_DetailArr=new Array();
	group_DetailArr=newArr;
	group_SubCategoryArr=new Array();
	group_SubCategoryArr=newArr_SubCategory;
	group_DeptAllArr=new Array();
	group_DeptAllArr=newArr_dept;
	
	group_DetailArr.push({
		id:rowVal,
		value:$("#"+rowVal).val()});
	group_SubCategoryArr.push({
		id:rowVal,
		value:subCategIDe
	});
	group_DeptAllArr.push({
		id:rowVal,
		value:deptIde
	});
	
}



function removeCheckboxArr(	arr,arryName,rowcountVal,subCatgeoryIde,deptIde){	
	/***********************Group Detail-HR********************************************/
	
	if(arryName=="group_DetailArr"){
		var newArr=new Array();
		var key="grpIDJ_"+rowcountVal;		
		var newArr_SubCategory=new Array();
		$.each(arr,function(id,value){		
			if(value.id!=key){
				newArr.push({
					id:value.id,
					value:value.value
					});				
				}		
		});
		$.each(group_SubCategoryArr,function(id,value){			
			if(value.id!=key){
				newArr_SubCategory.push({
					id:value.id,
					value:value.value
				});
			}
		});
		
		group_DetailArr=new Array();
		group_DetailArr=newArr;	
		group_SubCategoryArr=new Array();
		group_SubCategoryArr=newArr_SubCategory;
		
		
		
		
	}	
	
	if(arryName=="group_CheckboxArr"){		
	//	arr.splice($.inArray("checkboxJ"+rowcountVal,arr),1);		
		var newArrayCheckbox=new Array();
		$.each(arr,function(id,value){	
			if(value!="checkboxJ"+rowcountVal){
				newArrayCheckbox.push(value);
			}
		});
		group_CheckboxArr=new Array();
		group_CheckboxArr=newArrayCheckbox;
	}
	
	
	
	if(arryName=="group_DeptArr"){
		var newArrDept=new Array();
		var key="grpIdDept_"+rowcountVal;		
		var newArr_DeptSubCategory=new Array();
		var newArr_Dept=new Array();
		$.each(arr,function(id,value){		
			if(value.id!=key){
				newArrDept.push({
					id:value.id,
					value:value.value
					});				
				}		
		});
		$.each(group_SubCategoryDeptArr,function(id,value){			
			if(value.id!=key){
				newArr_DeptSubCategory.push({
					id:value.id,
					value:value.value
				});
			}
		});
		
		$.each(group_DeptAllArr,function(id,value){			
			if(value.id!=key){
				newArr_Dept.push({
					id:value.id,
					value:value.value
				});
			}
		});
		
		group_DeptArr=new Array();
		group_DeptArr=newArrDept;
	
		group_SubCategoryDeptArr=new Array();
		group_SubCategoryDeptArr=newArr_DeptSubCategory;
		
		group_DeptAllArr=new Array();
		group_DeptAllArr=newArr_Dept;
		
	}	
	if(arryName=="group_CheckboxDeptArr"){
		//arr.splice($.inArray("checkboxDept"+rowcountVal,arr),1);
		
		var newArrayCheckbox=new Array();
		$.each(arr,function(id,value){	
			if(value!="checkboxDept"+rowcountVal){
				newArrayCheckbox.push(value);
			}
		});
		group_CheckboxDeptArr=new Array();
		group_CheckboxDeptArr=newArrayCheckbox;		
		

		
	}
	
	
	
	
	/***********************Group Detail-HR********************************************/
}



function resetGroupAndCheckBoxValuesOnPagination(grp_arr,grp_cBox_arr,table_name){
//Reset all check box on Pagination

	
$.each(grp_cBox_arr,function(id,value){	
	if($("#"+value).length){		
		$("#"+value).attr("checked",true);
		
	}
});

if(table_name=="Group_Detail"){

	$.each(grp_arr,function(id,value){
		var key=value.id;
		var keyVal=value.value;	
		var checkboxID=key.replace('grpIDJ_','checkboxJ');
		var rowID=key.replace('grpIDJ_','Group_Detail_row');
		var rowcnt=key.replace('grpIDJ_','');
		if($("#"+checkboxID).length){
			var checkboxVal=$("#"+checkboxID).val();
			var splitArray = checkboxVal.split(",");		
			var subCateegoryIDe=splitArray[1];	
			var functionId=$("#function").val();		
			
			$.ajaxSetup({ cache: false });
			$.getJSON('getGroup.htm',{functionId:functionId},function(response){
				var html="<select id=\""+key+"\"  onchange=\"addCheckboxArronChange(\'"+key+"\',"+subCateegoryIDe+")\"  class=\"myTextInputForSelect\" selected=\"selected\">";	
				for(var i=0;i<response.length;i++){
						html+="<option value=\""+response[i].GROUP_ID+"\">"+response[i].GROUP_NAME+"</option>";
				}
				html+="</select>";
				$("#"+key).html('');		
				$("#"+key).replaceWith(html);		
				
				var noOfRows=($('#Group_Detail tr').length)-5;	
				var trIde=rowcnt%noOfRows;				
				if(trIde==0){
					$("#Group_Detail_row"+noOfRows).css('background-color', '#B7AFA3');
				}else{
					$("#Group_Detail_row"+trIde).css('background-color', '#B7AFA3');
				}

				$("#"+key+" option[selected]").removeAttr("selected");
				$("#"+key+" option[value='"+keyVal+"']").attr("selected",true);
			});		
		}		
	});
}else if(table_name=="Group_Detail_Dept"){

	$.each(grp_arr,function(id,value){
		var key=value.id;
		var keyVal=value.value;	
		var checkboxID=key.replace('grpIdDept_','checkboxDept');
		var rowID=key.replace('grpIdDept_','Group_Detail_Dept_row');
		var rowcnt=key.replace('grpIdDept_','');
		if($("#"+checkboxID).length){			
			var checkboxVal=$("#"+checkboxID).val();
			var splitArray = checkboxVal.split(",");		
			var subCateegoryIDe=splitArray[1];	
			var deptIDE=splitArray[3];
			var functionId=$("#function").val();		
			
			$.ajaxSetup({ cache: false });
			$.getJSON('getGroup.htm',{functionId:functionId},function(response){
				var html="<select id=\""+key+"\"  onchange=\"addCheckboxArronChange(\'"+key+"\',"+subCateegoryIDe+","+deptIDE+")\"  class=\"myTextInputForSelect\" selected=\"selected\">";	
				for(var i=0;i<response.length;i++){
						html+="<option value=\""+response[i].GROUP_ID+"\">"+response[i].GROUP_NAME+"</option>";
				}
				html+="</select>";
				$("#"+key).html('');		
				$("#"+key).replaceWith(html);		
				
				
				var noOfRows=($('#Group_Detail_Dept tr').length)-5;				
				var trIde=rowcnt%noOfRows;	
				if(trIde==0){
					$("#Group_Detail_Dept_row"+noOfRows).css('background-color', '#B7AFA3');
				}else{
					$("#Group_Detail_Dept_row"+trIde).css('background-color', '#B7AFA3');
				}
				
				
				$("#"+key+" option[selected]").removeAttr("selected");
				$("#"+key+" option[value='"+keyVal+"']").attr("selected",true);
			});		
		}		
	});
}


}

/************************************************************Group Mapping**************************************************************************/



/***************************************************************Group Mapping Updation**************************************************************/
function validateRequest_groupDetail(){
	isValidGroup=1;
	if(group_DetailArr=='' || group_CheckboxArr=='' || group_DeptArr=='' || group_CheckboxDeptArr==''){		
		isValidGroup=0;
	}
	if(isValidGroup==1){	
		//Call the method to build json for group		
		if(confirm("Do you want to update the group mapping?")){   
			buildJsonForGroupMapping();
		}		
	}else{
		parent.parent.jbarOnFailure("Select atleast one group to be mapped in both the tables !");
	}
	
}

function buildJsonForGroupMapping(){

	def_grpJson_GroupFunctionMapping="";
	def_grpJson_GroupFunctionMapping+="\"defaultGroupJSON\":{";
	var count=0;
	$.each(group_DetailArr,function(id,value){			
		if(count==0){
			def_grpJson_GroupFunctionMapping+="\""+count+"\":{\"GROUP_ID\":\""+value.value+"\",\"SUB_CATEGORY_ID\":\""+group_SubCategoryArr[count].value+"\"";	
			count++;
		}else{
			def_grpJson_GroupFunctionMapping+=",\""+count+"\":{\"GROUP_ID\":\""+value.value+"\",\"SUB_CATEGORY_ID\":\""+group_SubCategoryArr[count].value+"\"";
			count++;
		}
		def_grpJson_GroupFunctionMapping+="}";		
	});
	def_grpJson_GroupFunctionMapping+="}";
	
	
	
	defDept_grpJson_GroupFunctionMapping="";
	defDept_grpJson_GroupFunctionMapping+="\"deptDefaultGroupJSON\":{";
	var count1=0;
	$.each(group_DeptArr,function(id,value){			
		if(count1==0){
			defDept_grpJson_GroupFunctionMapping+="\""+count1+"\":{\"GROUP_ID\":\""+value.value+"\",\"SUB_CATEGORY_ID\":\""+group_SubCategoryDeptArr[count1].value+"\",\"DEPT_ID\":\""+group_DeptAllArr[count1].value+"\"";	
			count1++;
		}else{
			defDept_grpJson_GroupFunctionMapping+=",\""+count1+"\":{\"GROUP_ID\":\""+value.value+"\",\"SUB_CATEGORY_ID\":\""+group_SubCategoryDeptArr[count1].value+"\",\"DEPT_ID\":\""+group_DeptAllArr[count1].value+"\"";
			count1++;
		}
		defDept_grpJson_GroupFunctionMapping+="}";		
	});
	defDept_grpJson_GroupFunctionMapping+="}";
	
	var slaId=$("#slaId").val();
	var functionId=$("#function").val();
	var locationId=$("#locationId").val();
	var final_Grp_Json='{';
	 final_Grp_Json+='\"functionId\":\"'+functionId+'\",';
	 final_Grp_Json+='\"slaId\":\"'+slaId+'\",';
	 final_Grp_Json+='\"locationId\":\"'+locationId+'\",';	 
	 final_Grp_Json+=def_grpJson_GroupFunctionMapping+','+defDept_grpJson_GroupFunctionMapping;
	 final_Grp_Json+='}';	 
	 
	
	 
	 $.ajaxSetup({ cache: false });
		$.postJSON("insertNewLocation_grpDetail_Step2.htm",{jsonString:final_Grp_Json},function(data){
			if(data=="error"){
				parent.parent.jbarOnFailure(data.error);		
				$("#submitButton_groupDetail").show();
			
			}else{
				parent.parent.jbarOnSuccess(data.messsage);			
				$("#submitButton_groupDetail").hide();
				$("#STEP2_TABLE").find("select,input,button,textarea").attr("disabled", "disabled");	
			}
				
		});
	
}




/***************************************************************Group Mapping Updation**************************************************************/


/****************************************************************Dept Default Assignment Details****************************************************/
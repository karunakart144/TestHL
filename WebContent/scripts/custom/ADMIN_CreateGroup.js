$(document).ready(function() {		
	
	$("#empResultMessage").hide();
	
	$("#mon_start_hh").change(function() {
		var days = ['tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
		for (var i=0; i < (days.length); i++){		
		    $('#'+days[i]+'_start_hh').val($(this).val());
		}
	});
	
	$("#mon_start_mm").change(function() {
		var days = ['tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
		for (var i=0; i < (days.length); i++){		
		    $('#'+days[i]+'_start_mm').val($(this).val());
		}
	});
	
	$("#mon_end_hh").change(function() {
		var	days = ['tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
		for (var i=0; i < (days.length); i++){		
		    $('#'+days[i]+'_end_hh').val($(this).val());
		}
	});
	
	$("#mon_end_mm").change(function() {
		var days = ['tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
		for (var i=0; i < (days.length); i++){		
		    $('#'+days[i]+'_end_mm').val($(this).val());
		}
	});
	
	$("#save").click(function() {
		$("#save").attr('disabled',true);
		$("#cancel").attr('disabled',true);
		$("#enable_sat").attr('disabled',true);
		$("#enable_sun").attr('disabled',true);
		$("#addEngineer"+rownum).attr('disabled',true)
		setTimeout(function() {
			if(getstatus()==true){
				
				$("#save").attr('disabled',true);
				$("#cancel").attr('disabled',true);
				$("#enable_sat").attr('disabled',true);
				$("#enable_sun").attr('disabled',true);
				$("#addEngineer"+rownum).attr('disabled',true);
				//$('#groupCreation').attr('disabled',true);
				
				if(oprMessage != ""){
									
					if(confirm(oprMessage)){
						saveData();
					}else{
						
						$("#save").attr('disabled',false);
						$("#cancel").attr('disabled',false);
						$("#enable_sat").attr('disabled',false);
						$("#enable_sun").attr('disabled',false);
						$("#addEngineer"+rownum).attr('disabled',false);
						//$('#groupCreation').attr('disabled',false);
						return false;
					}
					
				}else{
					saveData();
				}
			}else{
			
				$("#save").attr('disabled',false);
				$("#cancel").attr('disabled',false);
				$("#enable_sat").attr('disabled',false);
				$("#enable_sun").attr('disabled',false);
				$("#addEngineer"+rownum).attr('disabled',false);
			}
		},1000);
	});
	
	$("#cancel").click(function() { 
		window.location.reload( true );
	});
	
	parent.unblockUI(); 
});

var rownum=1;
var groupStatus = true;
var oprMessage = "";
var splChars = /^[a-zA-Z0-9\s&() :\/\._\-]*$/;

function ValidateEmpId(EmpId,obj){
	var spanElemet = obj.id;
	if(EmpId != ''){
		
			$.getJSON('validateEmployee.htm',{employeeId:EmpId},function(Emp){
				if (Emp.status && Emp.status=="Error") {	
					parent.jbarOnFailure(Emp.message);
					$('#Group_Manager').css('background-color', 'red');
				}else {
				if(Emp.NAME){
					document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
					$("#empResultMessage").removeClass("invalid_text");
					$("#empResultMessage").addClass("valid_text");
					$("#empResultMessage").show();						
					$("#IsValidEmpId").val("true");
					$('#Group_Manager').css('background-color', 'white');
				}else{
					document.getElementById("empResultMessage").innerHTML ="Please enter valid Employee id";
					$("#empResultMessage").removeClass("invalid_text");
					$("#empResultMessage").addClass("invalid_text");
					$("#empResultMessage").show();						
					$("#IsValidEmpId").val("false");
					$('#Group_Manager').css('background-color', 'red');
					}
				}
			});
		}else{
			document.getElementById("empResultMessage").innerHTML ="Please enter valid Employee id";
			$("#empResultMessage").removeClass("valid_text");
			$("#empResultMessage").addClass("invalid_text");
			$("#empResultMessage").show();				
			$("#IsValidEmpId").val("false");
			$('#Group_Manager').css('background-color', 'red');
		}
}

function getLocationsForFunction(obj)
{
	var function_id=obj.options[obj.selectedIndex].value;
	$.getJSON('getLocationListForFunction.htm',{functionid:function_id},function(locations){
		
		$('#locationId').html($("<option></option>").attr("value",'0').text('--Please Select--'));
		
		for(var i=0;i<locations.length;i++){
			$('#locationId').append($("<option></option>").attr("value",locations[i].location_ID).text(locations[i].city)); 
		 }		
	});
	
	checkGroupNameAvailability();
	
	if(isChange == true){
		for(var i=1;parseInt(i, 10)<= parseInt(rownum, 10);i=parseInt(i, 10)+parseInt(1, 10)){
			getRoleAndAccessLevel(i,function_id)
		}
	}else{
	$.getJSON('getRole.htm',{functionId : function_id},function(data) {	
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {					
		var roleListOptions = [];
		var managerRoleOptions = [];
		roleListOptions.push('<option value="0">--Please Select--</option>');
		for(var i=0;i<data.length;i++){
			roleListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
			if(data[i].substring(0,data[i].indexOf(",", 0)).indexOf("Manager")!=-1){
				if(function_id==1020){
					if(data[i].substring(0,data[i].indexOf(",", 0)).indexOf("Group Quality Manager")!=-1){
						managerRoleOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
					}
				}else{				
					managerRoleOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
				}
			}
		}	
		$('#managerRole').html(managerRoleOptions.join(''));
		$('#role1').html(roleListOptions.join(''));
		}
	});
	
	$.getJSON('getAccessLevel.htm',{},function(data) {	
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {
			var accessListOptions = [];
			accessListOptions.push('<option value="0">--Please Select--</option>');
			for(var i=0;i<data.length;i++){
				accessListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
			}
			$('#roleAccess1').html(accessListOptions.join(''));
		}
	});
	}
	while(parseInt(rownum, 10) > parseInt(1, 10)){
		$('#engineer'+rownum).remove();
		rownum=parseInt(rownum, 10)-parseInt(1, 10);
	}
	$('#td_addEngineer1').show();
	$('#tdError_td_addEngineer1').remove();
	
}

function enableOperatingWindow(trId) {
	
	if((trId.id)=='sat'){
		document.getElementById('enable_sat_td').style.display="none";
		document.getElementById('sat_tr').style.display="block";
		
		$('#next_wrk_fri').html('SAT');
		
	}else if((trId.id)=='sun'){
		document.getElementById('enable_sun_td').style.display="none";
		document.getElementById('sun_tr').style.display="block";
		
		$('#next_wrk_sat').html('SUN');
	}
	
}

function disableOperatingWindow(trId) {
	
	if((trId.id)=='sat'){
		document.getElementById('enable_sat_td').style.display="block";
		document.getElementById('sat_tr').style.display="none";
		
		$('#next_wrk_fri').html('MON');
		
		$('#sat_start_hh').find('option:selected').css('background-color', 'white');
		$('#sat_start_mm').find('option:selected').css('background-color', 'white');
		$('#sat_end_hh').find('option:selected').css('background-color', 'white');
		$('#sat_end_mm').find('option:selected').css('background-color', 'white');

	}else if((trId.id)=='sun'){
		document.getElementById('enable_sun_td').style.display="block";
		document.getElementById('sun_tr').style.display="none";

		$('#next_wrk_sat').html('MON');
		
		$('#sun_start_hh').find('option:selected').css('background-color', 'white');
		$('#sun_start_mm').find('option:selected').css('background-color', 'white');
		$('#sun_end_hh').find('option:selected').css('background-color', 'white');
		$('#sun_end_mm').find('option:selected').css('background-color', 'white');
	}
	
}

function addEngineer(obj) {
	
	var functionId = $("#functionToAdd").val();
	var message='';
	
	if(functionId == '0') {
		highlightOption('#functionToAdd');
		message='Required';
		isChange=false;
	}
	else{

		var id = obj.name.substring(obj.name.length-1,obj.name.length);
		var nextId=parseInt(id, 10) + parseInt(1, 10);

		if ($('#Group_Name').val() == ''){
			$('#Group_Name').css('background-color', 'red');
			message='Required';
			isChange=false;
		}else{
			$('#Group_Name').css('background-color', 'white');
		}
		if ($('#Group_Manager').val() == ''){
			$('#Group_Manager').css('background-color', 'red');
			message='Required';
			isChange=false;
		}else{
			checkForGroupManager();
		}
		if($('#Engineer'+id).val() == ''){
			message='Required';
			$('#Engineer'+id).css('background-color', 'red');
			isChange=false;
		}else{
			$('#Engineer'+id).css('background-color', 'white');
		}
		if($('#role'+id).val() == '0'){
			message='Required';
			highlightOption('#role'+id);
			isChange=false;
		}		
		if ($('#roleAccess'+id).val() == '0'){
			message='Required';
			highlightOption('#roleAccess'+id);
			isChange=false;
		}
		
		if($('#role'+id).val() != '0' && $('#roleAccess'+id).val() != '0' && $('#Engineer'+id).val() != '' && isChange){
			
			rownum=parseInt(rownum, 10) + parseInt(1, 10);
			
			var content = '<tr id="engineer'+nextId+'"><td><input type="text" maxlength="10" name="Engineer'+nextId+'" id="Engineer'+nextId
			+'" style="border:1px solid #4598b5;"  onblur="checkEngineer(this);"/></td><td><select id="role'+nextId+'" name="role'+nextId
			+'" style="border:1px solid #4598b5; "></select></td><td><select id="roleAccess'+nextId
			+'" name="roleAccess'+nextId+'" style="border:1px solid #4598b5;"></select></td><td>'
			+'<input type="checkbox" name="assignment'+nextId+'" id="assignment'+nextId+'" checked="checked"'
			+'style="border:1px solid #4598b5;"/></td><td id="td_addEngineer'+nextId+'"><input name="removeEngineer'+nextId
			+'" type="image" src="images/reject.png" id="removeEngineer'+nextId
			+'" onclick = "removeEngineer(this);isChange=false;"/><input name="addEngineer'+nextId
			+'" type="image" src="images/addnode.jpg" id="addEngineer'+nextId
			+'" onclick = "isChange=true;addEngineer(this);"/></td></tr>';
		
			var disableId="td_addEngineer"+id;
			$('#'+disableId).hide();
			$('#tdError_td_addEngineer'+id).remove();
				
			$("#groupMembers").append(content);
			getRoleAndAccessLevel(nextId,functionId);
		}	
		
	}
	if(message != ''){
		if($('#TextFieldSpantd_addEngineer'+rownum)){
			$('#tdError_td_addEngineer'+rownum).remove();
			$('#td_addEngineer'+rownum).after('<td id="tdError_td_addEngineer'+rownum+'"><span class="error" id="TextFieldSpantd_addEngineer'+rownum+'" >Please enter value for Highlighted field</span></td>');
		}
		return false;
	}else{
		$('#td_addEngineer'+rownum).show();
		$('#tdError_td_addEngineer'+rownum).remove();
	}
}

function getRoleAndAccessLevel(id,function_id){
	
	var role="role"+id;
	var access="roleAccess"+id;
	
	$.getJSON('getRole.htm',{functionId : function_id},function(data) {	
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {					
		var roleListOptions = [];
		var managerRoleOptions = [];
		roleListOptions.push('<option value="0">--Please Select--</option>');
		for(var i=0;i<data.length;i++){
			roleListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
			if(data[i].substring(0,data[i].indexOf(",", 0)).indexOf("Manager")!=-1){
				managerRoleOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
			}
		}	
		$('#managerRole').html(managerRoleOptions.join(''));
		$('#'+role).html(roleListOptions.join(''));
		}
	});
	
	$.getJSON('getAccessLevel.htm',{},function(data) {	
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {
			var accessListOptions = [];
			accessListOptions.push('<option value="0">--Please Select--</option>');
			for(var i=0;i<data.length;i++){
				accessListOptions.push('<option value="'+data[i].substring(data[i].indexOf(",", 0)+1,data[i].length)+ '">'+ data[i].substring(0,data[i].indexOf(",", 0))+ '</option>');
			}
			$('#'+access).html(accessListOptions.join(''));
		}
	});
	
}

function removeEngineer(obj){
	
	var id = obj.name.substring(obj.name.length-1,obj.name.length);
	var removeId="engineer"+id;
	rownum=parseInt(rownum, 10) - parseInt(1, 10);
	var previousId=parseInt(id, 10) - parseInt(1, 10);
	var enableId="td_addEngineer"+previousId;
	
	$('#'+removeId).remove();
	$('#'+enableId).show();
	
}

function getstatus(){

	
	if($('#functionToAdd').val()=="0"){	
		var id='functionToAdd';
		$('#TextFieldSpan'+id+'').text('');
		$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!</span>');
			groupStatus = false;				
	}else{
		var id='functionToAdd';
		$('#TextFieldSpan'+id+'').text('');
	}
	if($('#Group_Name').val()==""){	
		var id='Group_Name';
		$('#TextFieldSpan'+id+'').text('');
		$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!</span>');
		groupStatus = false;				
	}else if (!($.trim($('#Group_Name').val())).match(splChars)) {
		
		document.getElementById("groupResultMessage").innerHTML ="Group Name contains special characters. Please change the group name";
		$("#groupResultMessage").removeClass("invalid_text");
		$("#groupResultMessage").addClass("invalid_text");
		$("#groupResultMessage").show();						
		$('#Group_Name').css('background-color', 'red');
		
		groupStatus = false;
	}else{
		$.getJSON('getGroupNameDetailsList.htm',{functionId : $('#functionToAdd').val()},function(data) {
			var matchFound=false;
			if (data.status && data.status=="Error") {	
				parent.jbarOnFailure(data.message);
			}
			else {					
				for(var i=0;i<data.length;i++){
					if($.trim($('#Group_Name').val()).toUpperCase() == $.trim(data[i].substring(0,data[i].indexOf(",", 0))).toUpperCase()){
						matchFound=true;
					}
				}
				
				if(matchFound){
					document.getElementById("groupResultMessage").innerHTML ="Group Name already Exists. Please change the group name";
					$("#groupResultMessage").removeClass("invalid_text");
					$("#groupResultMessage").addClass("invalid_text");
					$("#groupResultMessage").show();						
					$('#Group_Name').css('background-color', 'red');
					
					groupStatus = false;
				}else{				
					$('#TextFieldSpanGroup_Name').text('');			
				}
				
			}
		});		
	}
	if($('#Group_Manager').val()==""){	
		document.getElementById("empResultMessage").innerHTML ="Please enter valid Employee id";
		$("#empResultMessage").removeClass("invalid_text");
		$("#empResultMessage").addClass("invalid_text");
		$("#empResultMessage").show();						
		$("#IsValidEmpId").val("false");
	}else{
			$.getJSON('validateEmployee.htm',{employeeId:$('#Group_Manager').val()},function(Emp){
				if (Emp.status && Emp.status=="Error") {	
					parent.jbarOnFailure(Emp.message);
				}else {
				if(Emp.NAME){
					document.getElementById("empResultMessage").innerHTML = Emp.NAME ;
					$("#empResultMessage").removeClass("invalid_text");
					$("#empResultMessage").addClass("valid_text");
					$("#empResultMessage").show();						
					$("#IsValidEmpId").val("true");
				}else{
					document.getElementById("empResultMessage").innerHTML ="Please enter valid Employee id";
					$("#empResultMessage").removeClass("invalid_text");
					$("#empResultMessage").addClass("invalid_text");
					$("#empResultMessage").show();						
					$("#IsValidEmpId").val("false");
					}
				}
			});
		}	
			
	if($('#locationId').val()=="0"){	
		var id='locationId';
		$('#TextFieldSpan'+id+'').text('');
		$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!</span>');
			groupStatus = false;				
	}else{
		var id='locationId';
		$('#TextFieldSpan'+id+'').text('');
	}
	if($('#managerRole').val()=="0"){	
		var id='managerRole';
		$('#TextFieldSpan'+id+'').text('');
		$('#'+id+'').after('<span class="error" id="TextFieldSpan'+id+'" >'+'Required!!</span>');
			groupStatus = false;				
	}else{
		var id='managerRole';
		$('#TextFieldSpan'+id+'').text('');
	}
	if($('#mon_start_hh').val()!="" && $('#mon_start_mm').val()!="" && $('#mon_end_hh').val()!="" 
		&& $('#mon_end_mm').val()!=""){
		var value_start = $("#mon_start_hh").val()+':'+$("#mon_start_mm").val();
	    var value_end = '';
	    
	    if($("#mon_end_hh").val() == $("#mon_end_mm").val() && $("#mon_end_mm").val() == 0){
	    	value_end = 23+':'+59;
	    }else if(($('#mon_start_hh').val() == $("#mon_end_hh").val()) && ($('#mon_start_mm').val() == $("#mon_end_mm").val())){
	    	oprMessage="For 24 Hour Operating window, Start Time and End Time will be considered as 00:00 and 23:59";
	    	value_start = 0+':'+0;
	    	value_end = 23+':'+59;
	    	
	    }else{
	    	value_end = $("#mon_end_hh").val()+':'+$("#mon_end_mm").val();
	    }
	    if(!checkForOperatingWindow(value_start,value_end)){
	    	errorForOperatingWindow('not8hrs','mon');	    	
	    }else{
	    	$('#tdError_next_wrk_mon').remove();
	    }	    
	}else{
		if($('#mon_start_hh').val()==""){
			highlightOption('#mon_start_hh');
			errorForOperatingWindow('noStartHH','mon');
			groupStatus = false;				
		}	
		if($('#mon_start_mm').val()==""){
			highlightOption("#mon_start_mm");
			errorForOperatingWindow('noStartMM','mon');
			groupStatus = false;				
		}
		if($('#mon_end_hh').val()==""){
			highlightOption("#mon_end_hh");
			errorForOperatingWindow('noEndHH','mon');
			groupStatus = false;				
		}	
		if($('#mon_end_mm').val()==""){
			highlightOption("#mon_end_mm");
			errorForOperatingWindow('noEndMM','mon');
			groupStatus = false;				
		}
	}
	if($('#tue_start_hh').val()!="" && $('#tue_start_mm').val()!="" && $('#tue_end_hh').val()!="" 
		&& $('#tue_end_mm').val()!=""){
		var value_start = $("#tue_start_hh").val()+':'+$("#tue_start_mm").val();
		var value_end = '';
	    
	    if($("#tue_end_hh").val() == $("#tue_end_mm").val() && $("#tue_end_mm").val() == 0){
	    	value_end = 23+':'+59;
	    }else if(($('#tue_start_hh').val() == $("#tue_end_hh").val()) && ($('#tue_start_mm').val() == $("#tue_end_mm").val())){
	    	oprMessage="For 24 Hour Operating window, Start Time and End Time will be considered as 00:00 and 23:59";
	    	value_start = 0+':'+0;
	    	value_end = 23+':'+59;
	    }else{
	    	value_end = $("#tue_end_hh").val()+':'+$("#tue_end_mm").val();
	    }	  
	    
	    if(!checkForOperatingWindow(value_start,value_end)){
	    	errorForOperatingWindow('not8hrs','tue');	    	
	    }else{
	    	$('#tdError_next_wrk_tue').remove();
	    }	    
	}else{
		if($('#tue_start_hh').val()==""){
			highlightOption('#tue_start_hh');
			errorForOperatingWindow('noStartHH','tue');
			groupStatus = false;				
		}	
		if($('#tue_start_mm').val()==""){
			highlightOption("#tue_start_mm");
			errorForOperatingWindow('noStartMM','tue');
			groupStatus = false;				
		}
		if($('#tue_end_hh').val()==""){
			highlightOption("#tue_end_hh");
			errorForOperatingWindow('noEndHH','tue');
			groupStatus = false;				
		}	
		if($('#tue_end_mm').val()==""){
			highlightOption("#tue_end_mm");
			errorForOperatingWindow('noEndMM','tue');
			groupStatus = false;				
		}
	}
	if($('#wed_start_hh').val()!="" && $('#wed_start_mm').val()!="" && $('#wed_end_hh').val()!="" 
		&& $('#wed_end_mm').val()!=""){
		var value_start = $("#wed_start_hh").val()+':'+$("#wed_start_mm").val();
		var value_end = '';
	    
	    if($("#wed_end_hh").val() == $("#wed_end_mm").val() && $("#wed_end_mm").val() == 0){
	    	value_end = 23+':'+59;
	    }else if(($('#wed_start_hh').val() == $("#wed_end_hh").val()) && ($('#wed_start_mm').val() == $("#wed_end_mm").val())){
	    	oprMessage="For 24 Hour Operating window, Start Time and End Time will be considered as 00:00 and 23:59";
	    	value_start = 0+':'+0;
	    	value_end = 23+':'+59;
	    }else{
	    	value_end = $("#wed_end_hh").val()+':'+$("#wed_end_mm").val();
	    }
	    
	    if(!checkForOperatingWindow(value_start,value_end)){
	    	errorForOperatingWindow('not8hrs','wed');	    	
	    }else{
	    	$('#tdError_next_wrk_wed').remove();
	    }	    
	}else{
		if($('#wed_start_hh').val()==""){
			highlightOption('#wed_start_hh');
			errorForOperatingWindow('noStartHH','wed');
			groupStatus = false;				
		}	
		if($('#wed_start_mm').val()==""){
			highlightOption("#wed_start_mm");
			errorForOperatingWindow('noStartMM','wed');
			groupStatus = false;				
		}
		if($('#wed_end_hh').val()==""){
			highlightOption("#wed_end_hh");
			errorForOperatingWindow('noEndHH','wed');
			groupStatus = false;				
		}	
		if($('#wed_end_mm').val()==""){
			highlightOption("#wed_end_mm");
			errorForOperatingWindow('noEndMM','wed');
			groupStatus = false;				
		}
	}
	if($('#thu_start_hh').val()!="" && $('#thu_start_mm').val()!="" && $('#thu_end_hh').val()!="" 
		&& $('#thu_end_mm').val()!=""){
		var value_start = $("#thu_start_hh").val()+':'+$("#thu_start_mm").val();
		var value_end = '';
	    
	    if($("#thu_end_hh").val() == $("#thu_end_mm").val() && $("#thu_end_mm").val() == 0){
	    	value_end = 23+':'+59;
	    }else if(($('#thu_start_hh').val() == $("#thu_end_hh").val()) && ($('#thu_start_mm').val() == $("#thu_end_mm").val())){
	    	oprMessage="For 24 Hour Operating window, Start Time and End Time will be considered as 00:00 and 23:59";
	    	value_start = 0+':'+0;
	    	value_end = 23+':'+59;
	    }else{
	    	value_end = $("#thu_end_hh").val()+':'+$("#thu_end_mm").val();
	    }	 
	    
	    if(!checkForOperatingWindow(value_start,value_end)){
	    	errorForOperatingWindow('not8hrs','thu');	    	
	    }else{
	    	$('#tdError_next_wrk_thu').remove();
	    }	    
	}else{
		if($('#thu_start_hh').val()==""){
			highlightOption('#thu_start_hh');
			errorForOperatingWindow('noStartHH','thu');
			groupStatus = false;				
		}	
		if($('#thu_start_mm').val()==""){
			highlightOption("#thu_start_mm");
			errorForOperatingWindow('noStartMM','thu');
			groupStatus = false;				
		}
		if($('#thu_end_hh').val()==""){
			highlightOption("#thu_end_hh");
			errorForOperatingWindow('noEndHH','thu');
			groupStatus = false;				
		}	
		if($('#thu_end_mm').val()==""){
			highlightOption("#thu_end_mm");
			errorForOperatingWindow('noEndMM','thu');
			groupStatus = false;				
		}
	}
	if($('#fri_start_hh').val()!="" && $('#fri_start_mm').val()!="" && $('#fri_end_hh').val()!="" 
		&& $('#fri_end_mm').val()!=""){
		var value_start = $("#fri_start_hh").val()+':'+$("#fri_start_mm").val();
		var value_end = '';
	    
	    if($("#fri_end_hh").val() == $("#fri_end_mm").val() && $("#fri_end_mm").val() == 0){
	    	value_end = 23+':'+59;
	    }else if(($('#fri_start_hh').val() == $("#fri_end_hh").val()) && ($('#fri_start_mm').val() == $("#fri_end_mm").val())){
	    	oprMessage="For 24 Hour Operating window, Start Time and End Time will be considered as 00:00 and 23:59";
	    	value_start = 0+':'+0;
	    	value_end = 23+':'+59;
	    }else{
	    	value_end = $("#fri_end_hh").val()+':'+$("#fri_end_mm").val();
	    }
		
	    if(!checkForOperatingWindow(value_start,value_end)){
	    	errorForOperatingWindow('not8hrs','fri');	    	
	    }else{
	    	$('#tdError_next_wrk_fri').remove();
	    }
	}else{
		if($('#fri_start_hh').val()==""){
			highlightOption('#fri_start_hh');
			errorForOperatingWindow('noStartHH','fri');
			groupStatus = false;				
		}	
		if($('#fri_start_mm').val()==""){
			highlightOption("#fri_start_mm");
			errorForOperatingWindow('noStartMM','fri');
			groupStatus = false;				
		}
		if($('#fri_end_hh').val()==""){
			highlightOption("#fri_end_hh");
			errorForOperatingWindow('noEndHH','fri');
			groupStatus = false;				
		}	
		if($('#fri_end_mm').val()==""){
			highlightOption("#fri_end_mm");
			errorForOperatingWindow('noEndMM','fri');
			groupStatus = false;				
		}
	}
	if(isSat){
		if($('#sat_start_hh').val()!="" && $('#sat_start_mm').val()!="" && $('#sat_end_hh').val()!="" 
			&& $('#sat_end_mm').val()!=""){
			var value_start = $("#sat_start_hh").val()+':'+$("#sat_start_mm").val();
			var value_end = '';
		    
		    if($("#sat_end_hh").val() == $("#sat_end_mm").val() && $("#sat_end_mm").val() == 0){
		    	value_end = 23+':'+59;
		    }else if(($('#sat_start_hh').val() == $("#sat_end_hh").val()) && ($('#sat_start_mm').val() == $("#sat_end_mm").val())){
		    	oprMessage="For 24 Hour Operating window, Start Time and End Time will be considered as 00:00 and 23:59";
		    	value_start = 0+':'+0;
		    	value_end = 23+':'+59;
		    }else{
		    	value_end = $("#sat_end_hh").val()+':'+$("#sat_end_mm").val();
		    }
		    
		    if(!checkForOperatingWindow(value_start,value_end)){
		    	errorForOperatingWindow('not8hrs','sat');	
		    }else{
		    	$('#tdError_next_wrk_sat').remove();
		    }	    
		}else{
			if($('#sat_start_hh').val()==""){
				highlightOption('#sat_start_hh');
				errorForOperatingWindow('noStartHH','sat');
				groupStatus = false;				
			}	
			if($('#sat_start_mm').val()==""){
				highlightOption("#sat_start_mm");
				errorForOperatingWindow('noStartMM','sat');
				groupStatus = false;				
			}
			if($('#sat_end_hh').val()==""){
				highlightOption("#sat_end_hh");
				errorForOperatingWindow('noEndHH','sat');
				groupStatus = false;				
			}	
			if($('#sat_end_mm').val()==""){
				highlightOption("#sat_end_mm");
				errorForOperatingWindow('noEndMM','sat');
				groupStatus = false;				
			}
		}
	}
	if(isSun){
		if($('#sun_start_hh').val()!="" && $('#sun_start_mm').val()!="" && $('#sun_end_hh').val()!="" 
			&& $('#sun_end_mm').val()!=""){
			var value_start = $("#sun_start_hh").val()+':'+$("#sun_start_mm").val();
			var value_end = '';
		    
		    if($("#sun_end_hh").val() == $("#sun_end_mm").val() && $("#sun_end_mm").val() == 0){
		    	value_end = 23+':'+59;
		    }else if(($('#sun_start_hh').val() == $("#sun_end_hh").val()) && ($('#sun_start_mm').val() == $("#sun_end_mm").val())){
		    	oprMessage="For 24 Hour Operating window, Start Time and End Time will be considered as 00:00 and 23:59";
		    	value_start = 0+':'+0;
		    	value_end = 23+':'+59;
		    }else{
		    	value_end = $("#sun_end_hh").val()+':'+$("#sun_end_mm").val();
		    }
		    
		    if(!checkForOperatingWindow(value_start,value_end)){
		    	errorForOperatingWindow('not8hrs','sun');
		    }else{
		    	$('#disable_sun_td').show();
		    	$('#tdError_next_wrk_sun').remove();
		    }	    
		}else{
			if($('#sun_start_hh').val()==""){
				highlightOption('#sun_start_hh');
				errorForOperatingWindow('noStartHH','sun');
				groupStatus = false;				
			}	
			if($('#sun_start_mm').val()==""){
				highlightOption("#sun_start_mm");
				errorForOperatingWindow('noStartMM','sun');
				groupStatus = false;				
			}
			if($('#sun_end_hh').val()==""){
				highlightOption("#sun_end_hh");
				errorForOperatingWindow('noEndHH','sun');
				groupStatus = false;				
			}	
			if($('#sun_end_mm').val()==""){
				highlightOption("#sun_end_mm");
				errorForOperatingWindow('noEndMM','sun');
				groupStatus = false;				
			}
		}
	}
	for(var i=1;parseInt(i, 10)<= parseInt(rownum, 10);i=parseInt(i, 10)+parseInt(1, 10)){
		var engineer="#Engineer"+i;
		var role="#role"+i;
		var accessLevel="#roleAccess"+i;
		
		if($(engineer).val()== ""){
			$(engineer).css('background-color', 'red');
			groupStatus = false;
		}else{
			validateEmployee($(engineer).val(),engineer);
		}
		if($(role).val()=="0"){
			highlightOption(role);
			groupStatus = false;				
		}
		if($(accessLevel).val()=="0"){
			highlightOption(accessLevel);
			groupStatus = false;				
		}
		
		for (var j=1;parseInt(j, 10)< parseInt(i, 10);j=parseInt(j, 10)+parseInt(1, 10)){
			var prevEngineer="#Engineer"+j;
			if($(engineer).val()==$(prevEngineer).val()){
				
				groupStatus = false;
			}
		}	
	}
return groupStatus;
}

function highlightOption(selectId){
	$(selectId).find('option:selected').css('background-color', 'red');
}

function validateEmployee(EmpId,id){
	if(EmpId!=$('#Group_Manager').val()){	
		$.getJSON('validateEmployee.htm',{employeeId:EmpId},function(Emp){
				if (Emp.status && Emp.status=="Error") {	
					$(id).css('background-color', 'red');
					groupStatus=false;
				}else if(Emp.NAME){
					$(id).css('background-color', 'white');
					groupStatus = true;
				}else{
					$(id).css('background-color', 'red');
					groupStatus = false;
				}
		});
	}else{
		$(id).css('background-color', 'red');
		groupStatus = false;
	}
}

function checkEngineer(obj){

	var currentRow = obj.name.substring(obj.name.length-1,obj.name.length);
	
	var id='td_addEngineer'+currentRow;
	if(($('#Group_Manager').val() == $(obj).val()) && ($('#Group_Manager').val() != '' || $(obj).val() != '')){
		
		if($('#TextFieldSpan'+id)){
			$('#tdError_td_addEngineer'+currentRow).remove();
			$('#Engineer'+currentRow).css('background-color', 'red');
			$('#'+id).after('<td id="tdError_td_addEngineer'+currentRow+'"><span class="error" id="TextFieldSpan'+id+'" >Manager Id should not be added as engineer</span></td>');
			
		}
		groupStatus = false;	
	}else{		
		var tdEnable=true;
		
			$.getJSON('validateEmployee.htm',{employeeId:$(obj).val()},function(Emp){
				if (Emp.status && Emp.status=="Error") {	
					if($('#TextFieldSpan'+id)){
						$('#tdError_td_addEngineer'+currentRow).remove();
						$('#Engineer'+currentRow).css('background-color', 'red');
						$('#'+id).after('<td id="tdError_td_addEngineer'+currentRow+'"><span class="error" id="TextFieldSpan'+id+'" >Please enter valid EmployeeId</span></td>');
						
					}
					groupStatus=false;
					tdEnable=false;
				}else if(Emp.NAME){
					
					for(var i=1;parseInt(i, 10)<= parseInt(rownum, 10);i=parseInt(i, 10)+parseInt(1, 10)){
						
						var engineer="#Engineer"+i;	
						
						for (var j=1;parseInt(j, 10)< parseInt(i, 10);j=parseInt(j, 10)+parseInt(1, 10)){
							var prevEngineer="#Engineer"+j;
							if($(engineer).val()==$(prevEngineer).val() && $(engineer).val() != ''){
								if($('#TextFieldSpan'+id)){
									$('#tdError_td_addEngineer'+currentRow).remove();
									$('#Engineer'+currentRow).css('background-color', 'red');
									$('#'+id).after('<td id="tdError_td_addEngineer'+currentRow+'"><span class="error" id="TextFieldSpan'+id+'" >Duplicate Record</span></td>');
									
								}else{
									$('#TextFieldSpan'+id).append('Engineer should not be duplicate');
								}
								groupStatus=false;
								tdEnable=false;
							}
						}
					}
				}else{
					if($('#TextFieldSpan'+id)){
						$('#tdError_td_addEngineer'+currentRow).remove();
						$('#Engineer'+currentRow).css('background-color', 'red');
						$('#'+id).after('<td id="tdError_td_addEngineer'+currentRow+'"><span class="error" id="TextFieldSpan'+id+'" >Please enter valid EmployeeId</span></td>');
						
					}
					groupStatus=false;
					tdEnable=false;
					}
			});
		
			if(tdEnable){
				if(currentRow == rownum){
					$('#'+id).show();
				}
			$('#Engineer'+currentRow).css('background-color', 'white');
			$('#tdError_td_addEngineer'+currentRow).remove();
		}
	}
}

function checkForGroupManager(){
	for(var i=1;parseInt(i, 10)<= parseInt(rownum, 10);i=parseInt(i, 10)+parseInt(1, 10)){
		var engineer="#Engineer"+i;	
		var id='td_addEngineer'+i;
		if(($('#Group_Manager').val() == $(engineer).val()) && ($('#Group_Manager').val() != '' || $(engineer).val() != '')){
			if($('#TextFieldSpan'+id)){
				$('#tdError_td_addEngineer'+i).remove();
				$('#'+id).after('<td id="tdError_td_addEngineer'+i+'"><span class="error" id="TextFieldSpan'+id+'" >Manager Id should not be added as engineer</span></td>');
			}
			groupStatus=false;
		}else if(rownum == i){
			$('#'+id).show();
			$('#Group_Manager').css('background-color', 'white')
			$('#tdError_td_addEngineer'+i).remove();
		}
	}
		
}

function checkForOperatingWindow(value_start,value_end){
	
	//create date format 
    var timeStart = new Date("01/01/2012 " + value_start);
    var timeEnd = new Date("01/01/2012 " + value_end);
    
	if(timeStart > timeEnd){
		var timeStart = new Date("01/01/2012 " + value_start);
	    var timeEnd = new Date("01/02/2012 " + value_end);
	}
     
     var hourDiff = (timeEnd - timeStart)/3600000;
     
     if(hourDiff > parseInt(8, 10)){
    	 return true;
     }else{
    	 return false; 
     }
	
}

function errorForOperatingWindow(error,day){
	if(error == 'not8hrs'){
		if(day == 'fri'){
			if($('#TextFieldSpan_next_wrk_'+day)){
				$('#tdError_next_wrk_'+day).remove();
				$('#enable_sat_td').after('<td id="tdError_next_wrk_'+day+'"><span class="error" id="TextFieldSpan_next_wrk_'+day+'">Time Difference less than 8 hrs</span></td>');
				groupStatus=false;
			}
			$('#enable_sat_td').hide();
		}else if(day == 'sat'){
			if($('#TextFieldSpan_next_wrk_'+day)){
				$('#tdError_next_wrk_'+day).remove();
				$('#enable_sun_td').after('<td id="tdError_next_wrk_'+day+'"><span class="error" id="TextFieldSpan_next_wrk_'+day+'">Time Difference less than 8 hrs</span></td>');
				groupStatus=false;
			}
			$('#enable_sun_td').hide();
		}else if(day =='sun'){
			if($('#TextFieldSpan_next_wrk_'+day)){
				$('#tdError_next_wrk_'+day).remove();
				$('#disable_sun_td').after('<td id="tdError_next_wrk_'+day+'"><span class="error" id="TextFieldSpan_next_wrk_'+day+'">Time Difference less than 8 hrs</span></td>');
				groupStatus=false;
			}
			$('#disable_sun_td').hide();
		}else{
			if($('#TextFieldSpan_next_wrk_'+day)){
				$('#tdError_next_wrk_'+day).remove();
				$('#next_wrk_'+day).after('<td id="tdError_next_wrk_'+day+'"><span class="error" id="TextFieldSpan_next_wrk_'+day+'">Time Difference less than 8 hrs</span></td>');
				groupStatus=false;
			}
		}
	}else{
		if(day == 'fri'){
			if($('#TextFieldSpan_next_wrk_'+day)){
				$('#tdError_next_wrk_'+day).remove();
				$('#enable_sat_td').after('<td id="tdError_next_wrk_'+day+'"><span class="error" id="TextFieldSpan_next_wrk_'+day+'">Select value for highlighted fields</span></td>');
			}
			$('#enable_sat_td').hide();
		}else if(day == 'sat'){
			if($('#TextFieldSpan_next_wrk_'+day)){
				$('#tdError_next_wrk_'+day).remove();
				$('#enable_sun_td').after('<td id="tdError_next_wrk_'+day+'"><span class="error" id="TextFieldSpan_next_wrk_'+day+'">Select value for highlighted fields</span></td>');
			}
			$('#enable_sun_td').hide();
		}else if(day =='sun'){
			if($('#TextFieldSpan_next_wrk_'+day)){
				$('#tdError_next_wrk_'+day).remove();
				$('#disable_sun_td').after('<td id="tdError_next_wrk_'+day+'"><span class="error" id="TextFieldSpan_next_wrk_'+day+'">Select value for highlighted fields</span></td>');
			}
			$('#disable_sun_td').hide();
		}else{
			if($('#TextFieldSpan_next_wrk_'+day)){
				$('#tdError_next_wrk_'+day).remove();
				$('#next_wrk_'+day).after('<td id="tdError_next_wrk_'+day+'"><span class="error" id="TextFieldSpan_next_wrk_'+day+'">Select value for highlighted fields</span></td>');
			}
		}
	}
}

function saveData(){
	var daysArray = ['mon', 'tue', 'wed', 'thu', 'fri' ];
	if(isSat){
		daysArray = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat'];
	}
	if(isSun){
		daysArray = ['mon', 'tue', 'wed', 'thu', 'fri', 'sat', 'sun'];
	}

	var grp_jsonobj='';
	var opr_jsonobj='[';
	var mem_jsonobj='[';
	
	grp_jsonobj+='{"FUNCTION_ID":"'+$("#functionToAdd").val()+'","GROUP_NAME":"'+$("#Group_Name").val()+
	'","GROUP_MANAGER":"'+$("#Group_Manager").val()+'","LOCATION_ID":"'+$("#locationId").val()+'","AUTO_ASSIGNMENT":"'+
	$('input[name=autoAssignment]:radio:checked').val()+'"}';
	
	for (var i=0; i < (daysArray.length); i++){
		
		if(($('#'+daysArray[i]+'_start_hh').find('option:selected').val()==$('#'+daysArray[i]+'_end_hh').find('option:selected').val())
				&& ($('#'+daysArray[i]+'_start_mm').find('option:selected').val()==$('#'+daysArray[i]+'_end_mm').find('option:selected').val())){
			
			opr_jsonobj+='["'+$('#'+daysArray[i]).html()+'","00:00:00","23:59:59",';		
		
		}else{
			if($('#'+daysArray[i]+'_start_hh').find('option:selected').val().length < 2){
				if($('#'+daysArray[i]+'_start_mm').find('option:selected').val().length < 2){
					opr_jsonobj+='["'+$('#'+daysArray[i]).html()+'","0'+$('#'+daysArray[i]+'_start_hh').find('option:selected').val()+':0'+$('#'+daysArray[i]+'_start_mm').find('option:selected').val()+':00",';
				}else{
					opr_jsonobj+='["'+$('#'+daysArray[i]).html()+'","0'+$('#'+daysArray[i]+'_start_hh').find('option:selected').val()+':'+$('#'+daysArray[i]+'_start_mm').find('option:selected').val()+':00",';
				}
			}else{
				if($('#'+daysArray[i]+'_start_mm').find('option:selected').val().length < 2){
					opr_jsonobj+='["'+$('#'+daysArray[i]).html()+'","'+$('#'+daysArray[i]+'_start_hh').find('option:selected').val()+':0'+$('#'+daysArray[i]+'_start_mm').find('option:selected').val()+':00",';
				}else{
					opr_jsonobj+='["'+$('#'+daysArray[i]).html()+'","'+$('#'+daysArray[i]+'_start_hh').find('option:selected').val()+':'+$('#'+daysArray[i]+'_start_mm').find('option:selected').val()+':00",';
				}
			}
			
			if($('#'+daysArray[i]+'_end_hh').find('option:selected').val().length < 2){
				if($('#'+daysArray[i]+'_end_hh').find('option:selected').val() == $('#'+daysArray[i]+'_end_mm').find('option:selected').val() && $('#'+daysArray[i]+'_end_hh').find('option:selected').val() == 0){
					opr_jsonobj+='"23:59:59",';
					
				}else{				
					if($('#'+daysArray[i]+'_end_mm').find('option:selected').val().length < 2){
						opr_jsonobj+='"0'+$('#'+daysArray[i]+'_end_hh').find('option:selected').val()+':0'+$('#'+daysArray[i]+'_end_mm').find('option:selected').val()+':00",';
					}else{
						opr_jsonobj+='"0'+$('#'+daysArray[i]+'_end_hh').find('option:selected').val()+':'+$('#'+daysArray[i]+'_end_mm').find('option:selected').val()+':00",';
					}
				}
			}else{
				if($('#'+daysArray[i]+'_end_mm').find('option:selected').val().length < 2){
					opr_jsonobj+='"'+$('#'+daysArray[i]+'_end_hh').find('option:selected').val()+':0'+$('#'+daysArray[i]+'_end_mm').find('option:selected').val()+':00",';
				}else{
					opr_jsonobj+='"'+$('#'+daysArray[i]+'_end_hh').find('option:selected').val()+':'+$('#'+daysArray[i]+'_end_mm').find('option:selected').val()+':00",';
				}
			}
		}
		
		opr_jsonobj+='"'+$('#next_wrk_'+daysArray[i]).html()+'"],';
	}
	
	mem_jsonobj+='["'+$("#Group_Manager").val()+'","'
	+$('#managerRole').find('option:selected').val()+'","2","1"],';
	for(var i=1; i <= rownum; i++){
		if($('#assignment'+i).is(':checked')){				
			mem_jsonobj+='["'+$('#Engineer'+i).val()+'","'
			+$('#role'+i).find('option:selected').val()+'","'
			+$('#roleAccess'+i).find('option:selected').val()+'","1"],';
		}else{
			mem_jsonobj+='["'+$('#Engineer'+i).val()+'","'
			+$('#role'+i).find('option:selected').val()+'","'
			+$('#roleAccess'+i).find('option:selected').val()+'","0"],';
		}
	}
	
	opr_jsonobj+=']';
	mem_jsonobj+=']';
	
	$.getJSON('createGroup.htm',{json:grp_jsonobj,jsonOpr:opr_jsonobj,jsonMem:mem_jsonobj},function(data) {
		if (data.status && data.status=="Error") {	
			parent.jbarOnFailure(data.message);
		}
		else {
		if(data==1){
			parent.jbarOnSuccess('New Group '+$("#Group_Name").val()+' created Successfully');
			$('#groupCreation').attr('disabled',true);
		}
		else{
			parent.jbarOnFailure('Error while creating group');
		}
		}
	});
}

function checkGroupNameAvailability(){
	
	var groupName=$('#Group_Name').val();
	var selectedCategory=$('#functionToAdd').val();

		if($.trim(groupName) != ""){
		
			$.getJSON('getGroupNameDetailsList.htm',{functionId : selectedCategory},function(data) {
				var matchFound=false;
				if (data.status && data.status=="Error") {	
					parent.jbarOnFailure(data.message);
				}
				else {					
					for(var i=0;i<data.length;i++){
						if($.trim(groupName).toUpperCase() == $.trim(data[i].substring(0,data[i].indexOf(",", 0))).toUpperCase()){
							matchFound=true;
						}
					}
					
					if(matchFound){
						document.getElementById("groupResultMessage").innerHTML ="Group Name already Exists. Please change the group name";
						$("#groupResultMessage").removeClass("invalid_text");
						$("#groupResultMessage").addClass("invalid_text");
						$("#groupResultMessage").show();						
						$('#Group_Name').css('background-color', 'red');
						
						groupStatus = false;
					}else if (!($.trim(groupName)).match(splChars)) {
												
						document.getElementById("groupResultMessage").innerHTML ="Group Name contains special characters. Please change the group name";
						$("#groupResultMessage").removeClass("invalid_text");
						$("#groupResultMessage").addClass("invalid_text");
						$("#groupResultMessage").show();						
						$('#Group_Name').css('background-color', 'red');
						
						groupStatus = false;
					}else{
						document.getElementById("groupResultMessage").innerHTML = "Available" ;
						$("#groupResultMessage").removeClass("invalid_text");
						$("#groupResultMessage").addClass("valid_text");
						$("#groupResultMessage").hide();	
						$('#Group_Name').css('background-color', 'white');
					}
					
				}
			});	
		}
		return false;
}
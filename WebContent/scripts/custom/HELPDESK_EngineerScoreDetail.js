

//pre-submit callback
function showRequest(formData, jqForm, options) {
	if(isValidateDate()==true){		
		return true;
	}else{
		return false;
	}
}
function showResponse(response, statusText, xhr, $form)  { 
	if(response!=null){
		if(response.length!=0){
			$("#scoreDetTBL").find("tr:gt(1)").remove(); 
			var html="<tr><th>Employee Id</th><th>Created By</th><th>Created Date</th><th>Ticket Id</th><th>Weightage</th></tr>";
			for(var i=0;i<response.length;i++){
				html+='<tr><td>'+response[i].EMPLOYEE_ID+'</td><td>'+response[i].CHANGED_BY+'</td><td>'+response[i].CHANGED_DATE+'</td><td>'+response[i].TICKET_ID+'</td><td>'+response[i].WEIGHTAGE+'</td></tr>';
			}
			$("#scoreDetTBL").html(html);
		}//else of resp.len
	}//else of resp is null
}

function clearDateField(dateId){
    $('#'+dateId).val('');
}
function goBackToList()
{
    var url="UNIVERSAL_ListPage.htm?menuId=129&parentMenuId=1&menuName=Engineer Score&parentMenuName=Helpdesk";   
    document.location.href = url;
}

function isValidateDate(){
    var frmDt=$("#fromDate").val();
    var toDt=$("#toDate").val();
    if(frmDt.length==0 && toDt.length==0){
   	 	alert('Please select From Date and To Date Field');
   	 //	return false;
    }else if(frmDt.length!=0 && toDt.length==0){
        alert('Please select To Date Field');
      //  return false;
    }else if(frmDt.length==0 && toDt.length!=0){
        alert('Please select From Date Field');
      //  return false;
    }else if(compareDates(frmDt,toDt)==true){
        alert('End Date Should be Greater than Start Date!!');
       // return false;
    }
    else {                
       $("#ScoreDetail").submit();
    	//return true;
    }
}
function compareDates(dt1,dt2){	
   var dateGraterFlg = false;
   var dateLesserFlg = false;
   var arr1 = dt1.split('/');
   var arr2 = dt2.split('/');
   var year1;                
   var year2;               
	var timeArr1 = arr1[2].split(' ');
	var timeArr2 = arr2[2].split(' ');
	year1 = timeArr1[0]; 
	year2 = timeArr2[0];
	var compareDt1 = new Date( parseInt(year1,10),parseInt(arr1[1]-1,10),parseInt(arr1[0],10),0,0,0);          
   var compareDt2 =new Date( parseInt(year2,10),parseInt(arr2[1]-1,10),parseInt(arr2[0],10),0,0,0);			
   if(parseInt(Date.parse(compareDt1.toString())) > parseInt(Date.parse(compareDt2.toString()))){    							
      return true;
   }else if(parseInt(Date.parse(compareDt1.toString())) <= parseInt(Date.parse(compareDt2.toString()))){							
      return false;
   } else {                                   
      return true;
   }					
}

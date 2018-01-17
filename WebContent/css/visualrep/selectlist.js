$(document).ready(function(){
	
	// The select element to be replaced:
	var $select = $('select.select_search');


	 var $htmlStr = $('<div style="width: 146px;" class="tzSelect">');
	 var $appndClass = $('<div class="selectBox"></div>');
	 $htmlStr.append($appndClass);
	 $select.after($htmlStr);
	 
	 var $dropDown = $('<ul class="dropDown">');
	 
	 $(".tzSelect").after($dropDown);
	//var dropDown = $('<ul>',{class:'dropDown'});
	var $selectBox = $htmlStr.find('.selectBox');
	
	// Looping though the options of the original select element
	
	$select.find('option').each(function(i){
		var $option = $(this);
		
		if(i==$select.attr('selectedIndex')){
			$selectBox.add($option.text());
			
		}
		
		var $li = $('<li><img src="'+$option.data('icon')+'" /><span>'+
					$option.data('html-text')+'</span>');
				//alert($option.text("Employee Name/ID"));
					$selectBox.text("Employee Name / ID");
		$li.click(function(){
			$("#suggestions").css("display","none");
			$selectBox.html($option.text());
			if($option.text()=='Cubicle Code'){
				 $("#headersearchfilterinput").attr("placeholder", "Cubicle Code");
				 
			}else{
				$("#headersearchfilterinput").attr("placeholder", "Employee Name / ID");
			}
			$("#headersearchfilterinput").val("");
			$("#headersearchfilterinput").focus();
			$dropDown.trigger('hide');
			
			// When a click occurs, we are also reflecting
			// the change on the original select element:
			$select.val($option.val());
			
			return false;
		});
		
		$dropDown.append($li);
	});
	 $htmlStr.append($dropDown.hide());
	//selectBoxContainer.append(dropDown.hide());
	$select.hide().after($htmlStr);
	
	// Binding custom show and hide events on the dropDown:
	
	$dropDown.bind('show',function(){
		
		if($dropDown.is(':animated')){
			return false;
		}
		
		$selectBox.addClass('expanded');
		$('#suggestions').css("display","none");
		$dropDown.slideDown();
		
	}).bind('hide',function(){
		
		if($dropDown.is(':animated')){
			return false;
		}
		
		$selectBox.removeClass('expanded');
		$('#suggestions').css("display","none");
		$dropDown.slideUp();
		
	}).bind('toggle',function(){
		if($selectBox.hasClass('expanded')){
			$dropDown.trigger('hide');
		}
		else $dropDown.trigger('show');
	});
	
	$selectBox.click(function(){
		$dropDown.trigger('toggle');
		return false;
	});

	// If we click anywhere on the page, while the
	// dropdown is shown, it is going to be hidden:
	
	$(document).click(function(){
		$dropDown.trigger('hide');
	});
	
	
});
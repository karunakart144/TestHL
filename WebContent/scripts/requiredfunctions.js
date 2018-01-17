	function toggleLiveResizing () {
		$.each('north,south,west,east'.split(','), function (i, pane) {
			var opts = myLayout.options[ pane ];
			opts.resizeWhileDragging = !opts.resizeWhileDragging;
		});
	};
	
	function toggleStateManagement ( skipAlert ) {
		var enable = !myLayout.options.useStateCookie; // OPPOSITE of current setting
		myLayout.options.useStateCookie = enable; // toggle option
 
		if (!enable) { // if disabling state management...
			myLayout.deleteCookie(); // ...clear cookie so will NOT be found on next refresh
			if (!skipAlert)
				alert( 'This layout will reload as options specify \nwhen the page is refreshed.' );
		}
		else if (!skipAlert)
			alert( 'This layout will save & restore its last state \nwhen the page is refreshed.' );
 
		// update text on button
		var $Btn = $('#btnToggleState'), text = $Btn.html();
		//alert(text);
		if (enable && (text != null))
			$Btn.html( text.replace(/Enable/i, "Disable") );
		else if(text != null)
			$Btn.html( text.replace(/Disable/i, "Enable") );
	};
 
	// set EVERY 'state' here so will undo ALL layout changes
	// used by the 'Reset State' button: myLayout.loadState( stateResetSettings )
	var stateResetSettings = {
			north__size:		"auto"
		,	north__initClosed:	false
		,	north__initHidden:	false
		,	south__size:		"auto"
		,	south__initClosed:	false
		,	south__initHidden:	false
		,	west__size:			200
		,	west__initClosed:	false
		,	west__initHidden:	false
		,	east__size:			400
		,	east__initClosed:	false
		,	east__initHidden:	false
	};
 
	var myLayout;
 
	$(document).ready(function () {
 
		// this layout could be created with NO OPTIONS - but showing some here just as a sample...
		// myLayout = $('body').layout(); -- syntax with No Options
 
		myLayout = $('body').layout({
 
		//	enable showOverflow on west-pane so CSS popups will overlap north pane
			west__showOverflowOnHover: false
 
		//	reference only - these options are NOT required because 'true' is the default
		,	closable:				true	// pane can open & close
		,	resizable:				true	// when open, pane can be resized 
		,	slidable:				true	// when closed, pane can 'slide' open over other panes - closes on mouse-out
 
		//	some resizing/toggling settings
		,	north__slidable:		false	// OVERRIDE the pane-default of 'slidable=true'
		,	north__togglerLength_closed: '100%'	// toggle-button is full-width of resizer-bar
		,	north__spacing_closed:	20		// big resizer-bar when open (zero height)
		,	south__resizable:		false	// OVERRIDE the pane-default of 'resizable=true'
		,	south__spacing_open:	0		// no resizer-bar when open (zero height)
		,	south__spacing_closed:	20		// big resizer-bar when open (zero height)
		//	some pane-size settings
		,	west__minSize:			100
		,	east__size:				400
		,	east__minSize:			400
		,	east__maxSize:			Math.floor(screen.availWidth / 2) // 1/2 screen width
		,	center__minWidth:		80
		,	center__maxWidth:		80
 
		,	useStateCookie:			true
		});
		// if there is no state-cookie, then DISABLE state management initially
		var cookieExists = false;
		for (var key in myLayout.getCookie()) {
			cookieExists = true;
			break
		}

		/*if (!cookieExists) toggleStateManagement( true );
 
		// add event to the 'Close' button in the East pane dynamically...
		myLayout.addCloseBtn('#btnCloseEast', 'east');
 
		// add event to the 'Toggle South' buttons in Center AND South panes dynamically...
		myLayout.addToggleBtn('.south-toggler', 'south');
 
		// add MULTIPLE events to the 'Open All Panes' button in the Center pane dynamically...
		myLayout.addOpenBtn('#openAllPanes', 'north');
		myLayout.addOpenBtn('#openAllPanes', 'south');
		myLayout.addOpenBtn('#openAllPanes', 'west');
		myLayout.addOpenBtn('#openAllPanes', 'east');
 
		// 'Reset State' button requires updated functionality in rc29.15
		if ($.layout.revision && $.layout.revision >= 0.032915)
			$('#btnReset').show();*/
 
 	});
document.onkeydown = function()
{ 
        e=window.event;
        k=String.fromCharCode(e.keyCode).toLowerCase();
        if( k=="n" && e.ctrlKey) 
        {
              window.event.keyCode = 505;
        }

        if(window.event && (window.event .keyCode == 116 || window.event .keyCode == 122 || window.event .keyCode == 123)) 
        { 
              window.event.keyCode = 505; 
        }

        if(window.event && window.event .keyCode == 505) 
        { 
              alert("Due to security reasons this action not allowed");
              // Must return false or the browser will refresh anyway 
              event.returnValue = false;
              return false;
        }
  }

var message="Due to security reasons this action not allowed";
function clickIE4()
{
        if (event.button==2)
        {
              alert(message);
              return false;
        }
  }
  function clickNS4(e)
  {
        if (document.layers||document.getElementById&&!document.all)
        {
              if (e.which==2||e.which==3)
              {
                    alert(message);
                    return false;
              }
        }
  }
  if (document.layers)
  {
        document.captureEvents(Event.MOUSEDOWN);
        document.onmousedown=clickNS4;
  }
  else if (document.all&&!document.getElementById)
  {
        document.onmousedown=clickIE4;
  }
  document.oncontextmenu=new Function("alert(message);return false;")

/*//Prevent the backspace key from navigating back.
  $(document).unbind('keydown').bind('keydown', function (event) {
      var doPrevent = false;
      if (event.keyCode === 8) {
          var d = event.srcElement || event.target;
          if ((d.tagName.toUpperCase() === 'INPUT' && d.type.toUpperCase() === 'TEXT') 
               || d.tagName.toUpperCase() === 'TEXTAREA' ||( d.tagName.toUpperCase() === 'INPUT' && d.type
						.toUpperCase() === 'PASSWORD')) {
              doPrevent = d.readOnly || d.disabled;
          }
          else {
              doPrevent = true;
  			alert("Usage of Backspace key is not allowed");
          }
      }

      if (doPrevent) {
          event.preventDefault();
      }
  });*/
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<link rel="icon" type="image/gif" href="./favicon.gif">
<script type="text/javascript">
function OpenStartupScreen()
{
	
	var rndVal;
	var wOpen;
	var sOptions;
	var currentTimeMillis = new Date().getTime();
		
	sOptions = 'scrollbars=yes,resizable=yes,toolbar=no,status=no,menubar=no';
    sOptions = sOptions  + ',width    = ' + (screen.availWidth - 10).toString();
    sOptions = sOptions  + ',height = ' + (screen.availHeight - 122).toString();
    sOptions = sOptions +  ',screenX=0,screenY=0,left=0,top=0';

	rndVal = parseInt((60000 * Math.random()) + 1);   
	var link = "Login.htm";
	wOpen = window.open('',rndVal,sOptions);
	wOpen.location = link;
    wOpen.focus();
    wOpen.moveTo(0,0);
    wOpen.resizeTo(screen.availWidth, screen.availHeight );
}
</script>


</head>

<body onload="OpenStartupScreen();window.opener='X';window.open('','_parent','');window.close();" oncontextmenu="return false;"    >
<p>Loading ...</p>

</body>
</html>

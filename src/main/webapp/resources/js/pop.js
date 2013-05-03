//SETTING UP OUR POPUP
//0 means disabled; 1 means enabled;
var forgotPasswordStatus = 0;

//loading popup with jQuery magic!
function loadForgotPasswordPopup()
{
	//loads popup only if it is disabled
	if(forgotPasswordStatus==0)
	{
		$j("#backgroundPopup").css({
			"opacity": "0.7"
		});
		$j("#backgroundPopup").fadeIn("slow");
		$j("#popupForgot").fadeIn("slow");
		forgotPasswordStatus = 1;
	}
}

//disabling popup with jQuery magic!
function disableForgotPasswordPopup()
{
	//disables popup only if it is enabled
	if(forgotPasswordStatus==1){
		$j("#backgroundPopup").fadeOut("slow");
		$j("#popupForgot").fadeOut("slow");
		forgotPasswordStatus = 0;
	}
}

//centering popup
function centerForgotPasswordPopup()
{
	//request data for centering
	var windowWidth = document.documentElement.clientWidth;
	var windowHeight = document.documentElement.clientHeight;
	var popupHeight = $j("#popupForgot").height();
	var popupWidth = $j("#popupForgot").width();
	//centering
	$j("#popupForgot").css(
	{
		"position": "absolute",
		"top": ((windowHeight/2-popupHeight/2)-30),
		"left": ((windowWidth/2-popupWidth/2)-20)
	});
	//only need force for IE6
	
	$j("#backgroundPopup").css(
	{
		"height": windowHeight
	});
	
}
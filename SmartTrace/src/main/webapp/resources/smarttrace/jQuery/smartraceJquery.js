jQuery(document).ready(function($) {
	setIcons();
});

function setIcons(){
	$(".ui-icon.ui-icon-seek-prev").text("");
	$(".ui-icon.ui-icon-seek-next").text("");
	$(".ui-icon.ui-icon-seek-end").text("");
	$(".ui-icon.ui-icon-seek-first").text("");
	$("span.ui-icon.ui-icon-circle-triangle-e").text("");
	$("span.ui-icon.ui-icon-circle-triangle-w").text("");
}
function showHideMenuLeft(){
	$('.body-complete').toggleClass('body-complete-full');
	$('.menu-left').toggleClass('menu-left-open');
	$('.button-menu-left').toggleClass('button-menu-left-hide');
	$('.button-menu-left').children('.ui-button-icon-left.ui-icon.ui-c').toggleClass('fa-bars');
	$('.button-menu-left').children('.ui-button-icon-left.ui-icon.ui-c').toggleClass('fa-chevron-right');
}
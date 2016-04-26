jQuery(document).ready(function($) {
	$('.container').mouseover(function() {
		var num = 0;
		var menu = $(PrimeFaces.escapeClientId('menu-form:menu-root'));
		$('.container').css('margin-right', '');
		$('.container').css('left', '');
		num = $(".menu-root-control").val();
		if(num == 1){
			console.log("CloseMenuRoot");
			menu.removeClass("animated bounceInDown").addClass("animated bounceOutDown");
			setTimeout(function () {
			      menu.css('display', 'none');
			    }, 500);
		}
	});
});	

function openMenuRoot(){
	var num = 1;
	$(".menu-root-control").val(num);
	console.log("OpenMenuRoot");
	$(PrimeFaces.escapeClientId('menu-form:menu-root')).css('display', 'block');
	$(PrimeFaces.escapeClientId('menu-form:menu-root')).removeClass("animated bounceOutDown").addClass("animated bounceInDown");
	$('.container').css('margin-right', '196px');
	$('.container').css('left', '-16px');
}

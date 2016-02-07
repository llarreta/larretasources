jQuery(document).ready(function($) {
	$('.container').mouseover(function() {
		var num = 0;
		num = $(".menu-control").val();
		if(num == 1){
			$(PrimeFaces.escapeClientId('menu-form:menu')).removeClass("animated fadeInLeft").addClass("animated fadeOutLeft").animate({display: "none"}, 1000);
			$(".menu-control").val(0);
		}
	});
});	

function openMenu(){
	var num = 1;
	$(".menu-control").val(num);
	console.log("OpenMenu");
	$(PrimeFaces.escapeClientId('menu-form:menu')).animate({display: "initial"}, 500);
	$(PrimeFaces.escapeClientId('menu-form:menu')).removeClass("animated fadeOutLeft").addClass("animated fadeInLeft");
}

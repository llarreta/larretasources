var j = jQuery.noConflict();
j(document).ready(function() {
 	j("#css3dimagePager li").click(function(){
		var rotateY = ($(this).index() * -90); 
		j("#css3dimageslider ul").css({"-webkit-transform":"rotateY("+rotateY+"deg)", "-moz-transform":"rotateY("+rotateY+"deg)", "-ms-transform":"rotateY("+rotateY+"deg)", "transform":"rotateY("+rotateY+"deg)"});
		j("#css3dimagePager li").removeClass("active");
		j(this).addClass("active");
	});
 	j("#css3dtransparency").click(function() {
		j("#css3dimageslider").toggleClass("transparency");
		j(this).toggleClass("active");
	});
 });
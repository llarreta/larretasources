
jQuery(document).ready(function($) {
	$(PrimeFaces.escapeClientId('tab0')).click(function() {
		$('html, body').animate({
	        scrollTop: $("#tab0-content").offset().top-150
	    }, 2000);
		$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
		$(this).removeClass("default-selection-tab");
		$(this).addClass("active-selection-tab");
	});
	$(PrimeFaces.escapeClientId('tab1')).click(function() {
		$('html, body').animate({
	        scrollTop: $("#tab1-content").offset().top+50
	    }, 2000);
		$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
		$(this).removeClass("default-selection-tab");
		$(this).addClass("active-selection-tab");
	});
	$(PrimeFaces.escapeClientId('tab2')).click(function() {
		$('html, body').animate({
	        scrollTop: $("#tab2-content").offset().top+50
	    }, 2000);
		$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
		$(this).removeClass("default-selection-tab");
		$(this).addClass("active-selection-tab");
	});
	$(PrimeFaces.escapeClientId('tab3')).click(function() {
		$('html, body').animate({
	        scrollTop: $("#tab3-content").offset().top+50
	    }, 2000);
		$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
		$(this).removeClass("default-selection-tab");
		$(this).addClass("active-selection-tab");
	});
	$(PrimeFaces.escapeClientId('tab4')).click(function() {
		$('html, body').animate({
	        scrollTop: $("#tab4-content").offset().top+50
	    }, 2000);
		$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
		$(this).removeClass("default-selection-tab");
		$(this).addClass("active-selection-tab");
	});
	$(PrimeFaces.escapeClientId('tab5')).click(function() {
		$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
		$(this).removeClass("default-selection-tab");
		$(this).addClass("active-selection-tab");
		$('html, body').animate({
	        scrollTop: $("#tab5-content").offset().top+50
	    }, 2000);
	});
	$(PrimeFaces.escapeClientId('tab6')).click(function() {
		$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
		$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
		$(this).removeClass("default-selection-tab");
		$(this).addClass("active-selection-tab");
		$('html, body').animate({
	        scrollTop: $("#tab6-content").offset().top+50
	    }, 2000);
	});
	
	$('.fixed').makeFixed();
	
	$('.fixed-with-callback').makeFixed
	({
		onFixed: function (el)
		{
			if ($(el).attr('id') == 'menu-login')
			{
				$(el).css("background-color", "rgba(57, 179, 215, 0.8)");
				$(el).css("border-radius", "4px");
			}
		},
		onUnFixed: function (el)
		{
			if ($(el).attr('id') == 'menu-login')
			{
				$(el).css("background-color", "inherit");
				$(el).css("border-radius", "0px");
			}
		}
	});
	
	function scroll_fn(){

	    document_height = $(document).height();
	    scroll_so_far = $(window).scrollTop();
	    window_height = $(window).height();
	    
		max_scroll = document_height-window_height;

		scroll_percentage = scroll_so_far/(max_scroll/100);
	    
	    $('#loading').width(scroll_percentage + '%');
	    
	    if(scroll_so_far >= $("#tab0-content").offset().top && scroll_so_far < $("#tab1-content").offset().top){
	    	$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
			$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
			$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
			$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
			$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
			$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
			$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
			$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
			$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
			$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
			$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
			$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
			$(PrimeFaces.escapeClientId('tab0')).removeClass("default-selection-tab");
			$(PrimeFaces.escapeClientId('tab0')).addClass("active-selection-tab");
	    }else{
	    	if(scroll_so_far >= $("#tab1-content").offset().top && scroll_so_far < $("#tab2-content").offset().top){
	    		$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
				$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
				$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
				$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
				$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
				$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
				$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
				$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
				$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
				$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
				$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
				$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
				$(PrimeFaces.escapeClientId('tab1')).removeClass("default-selection-tab");
				$(PrimeFaces.escapeClientId('tab1')).addClass("active-selection-tab");
	    	}else{
	    		if(scroll_so_far >= $("#tab2-content").offset().top && scroll_so_far < $("#tab3-content").offset().top){
	    			$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
					$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
					$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
					$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
					$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
					$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
					$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
					$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
					$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
					$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
					$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
					$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
					$(PrimeFaces.escapeClientId('tab2')).removeClass("default-selection-tab");
					$(PrimeFaces.escapeClientId('tab2')).addClass("active-selection-tab");
	    		}else{
	    			if(scroll_so_far >= $("#tab3-content").offset().top && scroll_so_far < $("#tab4-content").offset().top){
		    			$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
						$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
						$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
						$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
						$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
						$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
						$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
						$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
						$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
						$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
						$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
						$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
						$(PrimeFaces.escapeClientId('tab3')).removeClass("default-selection-tab");
						$(PrimeFaces.escapeClientId('tab3')).addClass("active-selection-tab");
		    		}else{
		    			if(scroll_so_far >= $("#tab4-content").offset().top && scroll_so_far < $("#tab5-content").offset().top){
			    			$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
							$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
							$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
							$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
							$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
							$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
							$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
							$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
							$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
							$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
							$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
							$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
							$(PrimeFaces.escapeClientId('tab4')).removeClass("default-selection-tab");
							$(PrimeFaces.escapeClientId('tab4')).addClass("active-selection-tab");
			    		}else{
			    			if(scroll_so_far >= $("#tab5-content").offset().top && scroll_so_far < $("#tab6-content").offset().top){
				    			$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
								$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
								$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
								$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
								$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
								$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
								$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
								$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
								$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
								$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
								$(PrimeFaces.escapeClientId('tab6')).removeClass("active-selection-tab");
								$(PrimeFaces.escapeClientId('tab6')).addClass("default-selection-tab");
								$(PrimeFaces.escapeClientId('tab5')).removeClass("default-selection-tab");
								$(PrimeFaces.escapeClientId('tab5')).addClass("active-selection-tab");
				    		}else{
				    			if(scroll_so_far >= $("#tab6-content").offset().top && scroll_so_far < $("#tab7-content").offset().top){
					    			$(PrimeFaces.escapeClientId('tab0')).removeClass("active-selection-tab");
									$(PrimeFaces.escapeClientId('tab0')).addClass("default-selection-tab");
									$(PrimeFaces.escapeClientId('tab1')).removeClass("active-selection-tab");
									$(PrimeFaces.escapeClientId('tab1')).addClass("default-selection-tab");
									$(PrimeFaces.escapeClientId('tab2')).removeClass("active-selection-tab");
									$(PrimeFaces.escapeClientId('tab2')).addClass("default-selection-tab");
									$(PrimeFaces.escapeClientId('tab4')).removeClass("active-selection-tab");
									$(PrimeFaces.escapeClientId('tab4')).addClass("default-selection-tab");
									$(PrimeFaces.escapeClientId('tab5')).removeClass("active-selection-tab");
									$(PrimeFaces.escapeClientId('tab5')).addClass("default-selection-tab");
									$(PrimeFaces.escapeClientId('tab3')).removeClass("active-selection-tab");
									$(PrimeFaces.escapeClientId('tab3')).addClass("default-selection-tab");
									$(PrimeFaces.escapeClientId('tab6')).removeClass("default-selection-tab");
									$(PrimeFaces.escapeClientId('tab6')).addClass("active-selection-tab");
					    		}
				    		}
			    		}
		    		}
	    		}
	    	}
	    }

	}



	$(window).scroll(function() {
	scroll_fn();
	});

	$(window).resize(function() {
	scroll_fn();
	});
	
});

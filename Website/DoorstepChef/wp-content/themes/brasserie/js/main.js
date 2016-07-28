( function( $ ) {
	
	
	/*
	 * Animate elements with .animated class name and use the data-fx as the animation type
	 */ 
 	$(".animated").on("inview", function(isVisible) {
	  // Event is triggered once the element becomes visible in the browser's viewport, and once when it becomes invisible
	  if (isVisible) {
		  $(this).css('visibility','visible');
		  $(this).addClass($(this).data('fx')); 
	  }
	});
	
	$('.top-search a').on('click', function(e){
		e.preventDefault();
		$('.show-search,.top-search').toggleClass('live');
		$('#s').focus();
	});
	
	
	var brasserie_header_height = $('.stickyHead').innerHeight() + $('#topbar_container').innerHeight();
	

    function brasserie_sticky() {
        if ($('body').hasClass('sticky-header') && $(window).width() > 1000 ) {
            var start_y = brasserie_header_height;
            var window_y = $(window).scrollTop();
            if (window_y > start_y) {
                if (!($('.stickyHead').hasClass('is-sticky'))) {
                    $('.header_placeholder').css('margin-top', $('.stickyHead').innerHeight() - $('#topbar_container').innerHeight());
					var window_x = $(window).width();
					if(window_x > 600){
	                    $('.stickyHead').addClass('is-sticky').css('top', -60).animate({
	                        'top': $('#wpadminbar').innerHeight()
	                    }, 300);
					}else{
						$('.stickyHead').addClass('is-sticky').css('top', -60).animate({
	                        'top': '0'
	                    }, 300);
					}
                }
            } else {
                if ($('.stickyHead').hasClass('is-sticky')) {
                    $('.header_placeholder').css('margin-top', 0);
                    $('.stickyHead').removeClass('is-sticky').css('top', 61);
                }
            }
        }
    }
	$(window).scroll(function() { 
		brasserie_sticky();
	});

	

	
	/*
	 * tooltip-ize all links with title attributes, except Site title.
	 */
	$('.hastip').tooltipsy({
		offset: [0, 10]
	});
	
	
	// accordion style navigation for small screens.
	var $window = $(window);
	var windowsize = $window.width();
	$('.page_item_has_children > a, .menu-item-has-children > a').on('click', function(e){
		if (windowsize < 1000) {
			e.preventDefault();
		}
		var clickedLink = $(this);
		clickedLink.toggleClass('open');
		var child = clickedLink.siblings('ul');
		child.toggleClass('show');
	});
	
} )( jQuery );


/**
 * Theme Customizer enhancements for a better user experience.
 *
 * Contains handlers to make Theme Customizer preview reload changes asynchronously.
 */

( function( $ ) {
	// Site title and description.
	wp.customize( 'blogname', function( value ) {
		value.bind( function( to ) {
			$( '.site-title a' ).html( to );
		} );
	} );
	wp.customize( 'blogdescription', function( value ) {
		value.bind( function( to ) {
			$( '.site-description' ).html( to );
		} );
	} );
	
	wp.customize( 'topBarContact_telNo', function( value ) {
		value.bind( function( to ) {
			$( '.topbar_content_left span.tel' ).html( to );
		} );
	} );
	
	wp.customize( 'topBarContact_email', function( value ) {
		value.bind( function( to ) {
			$( '.topbar_content_left span.email' ).html( to );
		} );
	} );
	
	wp.customize( 'featured_textbox_header_one', function( value ) {
		value.bind( function( to ) {
			$( '.box-one .featuretext h2 a' ).html( to );
		} );
	} );
	
	wp.customize( 'featured_textbox_header_two', function( value ) {
		value.bind( function( to ) {
			$( '.box-two .featuretext h2 a' ).html( to );
		} );
	} );
	
	wp.customize( 'featured_textbox_header_three', function( value ) {
		value.bind( function( to ) {
			$( '.box-three .featuretext h2 a' ).html( to );
		} );
	} );
	
	wp.customize( 'featured_textbox_text_one', function( value ) {
		value.bind( function( to ) {
			$( '.box-one .featuretext p' ).html( to );
		} );
	} );
	
	wp.customize( 'featured_textbox_text_two', function( value ) {
		value.bind( function( to ) {
			$( '.box-two .featuretext p' ).html( to );
		} );
	} );
	
	wp.customize( 'featured_textbox_text_three', function( value ) {
		value.bind( function( to ) {
			$( '.box-three .featuretext p' ).html( to );
		} );
	} );
	
	wp.customize( 'brasserie_partner_txt', function( value ) {
		value.bind( function( to ) {
			$( '.client h3' ).html( to );
		} );
	} );
	
	wp.customize( 'featured_btn_textbox', function( value ) {
		value.bind( function( to ) {
			$( '.featuretext_button a' ).html( to );
		} );
	} );
	
	wp.customize( 'featured_textbox', function( value ) {
		value.bind( function( to ) {
			$( '.featuretext_top h3' ).html( to );
		} );
	} );
	
	
/**
	* -----------------------------------------------------------------------------
	* Renders Highlight Color (brasserie) Live Preview JS 
	* =============================================================================
	*/		
	wp.customize( 'brasserie_link_color', function( value ) {
												   
		value.bind( function( to ) {
			$( '.main-navigation li:hover > a, .main-navigation li.current_page_item a, .main-navigation li.current-menu-item a,.main-navigation > li > a, .main-navigation li.current_page_ancestor a,.main-navigation ul ul li:hover > a, .main-navigation ul ul li.current_page_item a, .main-navigation ul ul li.current-menu-item a,.main-small-navigation li:hover > a, .main-small-navigation li.current_page_item a, .main-small-navigation li.current-menu-item a, .main-small-navigation ul ul a:hover, .main-small-navigation ul.children li:hover a, .entry-meta a, .authorLinks a, cite, cite a,  cite a:visited, cite a:active, .section_thumbnails h3, .client h3, .socialIcons a, .socialIcons a:visited, .entry-content a,  .entry-content a:visited,  .entry-summary a,  .entry-summary a:visited, .featuretext h2 a, .flex-caption-title h3 a:hover, .social-media a:hover, .widget-title, .recent a .recent_title h2 , .recent a:visited .recent_title h2, .tooltipsy' ).css( 'color', to );
		} );
		
		value.bind( function( to ) {
			$( '.title-bottom-border, .tagcloud a, .grid-more-link, .more-link, .menu-toggle, #smoothup:hover' ).css( 'background-color', to );
		} );
		
		value.bind( function( to ) {
			$( '.widget-title, .featuretext_middle,  .widget-title, .widget-title:after,  .featuretext_middle, .tooltipsy, #masthead-wrap, .flex-container, .main-navigation ul ul, .tagcloud a' ).css( 'border-color', to );
		} );
		
	} );
	
	


	/**
	* -----------------------------------------------------------------------------
	* Initializes & sets up Homepage Promotion Bar Customizer (brasserie) Live Preview JS 
	* =============================================================================
	*/
	wp.customize( 'brasserie_shout_text_color', function( value ) {
		value.bind( function( to ){
			$('.featuretext_top h3').css('color', to);	
		})	
	});
	wp.customize( 'brasserie_shout_color', function( value ) {
		value.bind( function( to ){
			$('#featuretext_container').css('background-color', to);	
		})	
	});
	wp.customize( 'brasserie_shout_button_color', function( value ) {
		value.bind( function( to ){
			$('.featuretext_button a').css('background-color', to);	
		})	
	});
	wp.customize( 'brasserie_shout_button_text_color', function( value ) {
		value.bind( function( to ){
			$('.featuretext_button a').css('color', to);	
		})	
	});
	
		
	
	/**
	* -----------------------------------------------------------------------------
	* Initializes & sets up Google Web Fonts Customizer (brasserie) Live Preview JS 
	* =============================================================================
	*/
	
	var list_font_weights = ['100', '100italic', '200', '200italic', '300', '300italic', '400', '400italic', '500', '500italic', '600', '600italic', '700', '700italic', '800', '800italic', '900', '900italic'];
		
		//	BODY TEXT

	
		/* font family */
		wp.customize( "brasserie_body_font_family", function(value){
			value.bind(function(newval){
				if(newval != 'default'){
					var fontFamily = newval;
					var fontFamilyUrl = newval.split(" ").join("+");
					var googleFontPath = "http://fonts.googleapis.com/css?family="+fontFamilyUrl+":"+list_font_weights.join(); +"";
					var googleFontSource = "<link id='brasserie-body-font-family' href='"+googleFontPath+"' rel='stylesheet' type='text/css'>";					
					var checkLink = $("head").find("#brasserie-body-font-family").length;
					if (checkLink > 0) {
						$("head").find("#brasserie-body-font-family").remove();
						$("head").find("#brasserie-body-style").remove();
						$("head").append(googleFontSource);
					} else {
						$("head").append(googleFontSource);
					}	
					$("body").css("font-family", "'"+fontFamily+"', sans-serif", "important");
					if(fontFamily == 'default'){
						$("head").find("#brasserie-body-font-family").remove();
						$("head").find("#brasserie-body-style").remove();
						$("body").css("font-family", "");
						$("body").css("font-weight", "");
						$("body").css("font-style", "");
					}
				}
			});
		});	
	
		/* font weight & style */
		wp.customize( "brasserie_body_font_weight", function(value){
			value.bind(function(newval){
				var fontWeight = newval.replace(/\D/g,'');
				var fontStyle = newval.replace(/[0-9]/g, '');
				$("body").css("font-weight", fontWeight, "important");
				if ( fontStyle != "" ){
					$("body").css("font-style", fontStyle, "important");	
				}else{
					$("body").css("font-style", "normal");	
				}			
			});
		});
		
		//	HEADERS
	
		/* font family */
		wp.customize( "brasserie_h1_font_family", function(value){
			value.bind(function(newval){
				if(newval != 'default'){				
					var fontFamily = newval;
					var fontFamilyUrl = newval.split(" ").join("+");
					var googleFontPath = "http://fonts.googleapis.com/css?family="+fontFamilyUrl+":"+list_font_weights.join(); +"";
					var googleFontSource = "<link id='brasserie-h1-font-family' href='"+googleFontPath+"' rel='stylesheet' type='text/css'>";					
					var checkLink = $("head").find("#brasserie-h1-font-family").length;
					if (checkLink > 0) {
						$("head").find("#brasserie-h1-font-family").remove();
						$("head").find("#brasserie-h1-style").remove();
						$("head").append(googleFontSource);
					} else {
						$("head").append(googleFontSource);
					}	
					$("h1, h2, h3, h4, h5, h6").css("font-family", "'"+fontFamily+"', sans-serif", "important");
					if(fontFamily == 'default'){
						$("head").find("#brasserie-h1-font-family").remove();
						$("head").find("#brasserie-h1-style").remove();
						$("h1, h2, h3, h4, h5, h6").css("font-family", "");
						$("h1, h2, h3, h4, h5, h6").css("font-weight", "");
						$("h1, h2, h3, h4, h5, h6").css("font-style", "");
					}
				}
			});
		});	
	
		/* font weight & style */
		wp.customize( "brasserie_h1_font_weight", function(value){
			value.bind(function(newval){
				var fontWeight = newval.replace(/\D/g,'');
				var fontStyle = newval.replace(/[0-9]/g, '');
				$("h1, h2, h3, h4, h5, h6").css("font-weight", fontWeight, "important");
				if ( fontStyle != "" ){
					$("h1, h2, h3, h4, h5, h6").css("font-style", fontStyle, "important");	
				}else{
					$("h1, h2, h3, h4, h5, h6").css("font-style", "normal");	
				}				
			});
		});
	
} )( jQuery );

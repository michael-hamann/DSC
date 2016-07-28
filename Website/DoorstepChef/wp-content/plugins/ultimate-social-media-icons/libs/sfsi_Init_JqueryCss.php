<?php 
/*  instalation of javascript and css  */
function theme_back_enqueue_script()
{
		wp_enqueue_style("SFSImainAdminCss", SFSI_PLUGURL . 'css/sfsi-admin-style.css' );
		
		if(isset($_GET['page']))
		{
			if($_GET['page'] == 'sfsi-options')
			{
				/* include CSS for backend  */
  				wp_enqueue_style('thickbox');
				wp_enqueue_style("SFSImainCss", SFSI_PLUGURL . 'css/sfsi-style.css' );
				wp_enqueue_style("SFSIJqueryCSS", SFSI_PLUGURL . 'css/jquery-ui-1.10.4/jquery-ui-min.css' );
				wp_enqueue_style("wp-color-picker");
			}
		}
		
		//including floating option css
		$option5=  unserialize(get_option('sfsi_section5_options',false));
		
		if($option5['sfsi_disable_floaticons'] == 'yes')
		{
			wp_enqueue_style("disable_sfsi", SFSI_PLUGURL . 'css/disable_sfsi.css' );
		}
		
		if(isset($_GET['page']))
		{
			if($_GET['page'] == 'sfsi-options')
			{
				wp_enqueue_script('jquery');
			 	wp_enqueue_script("jquery-migrate");
				
				wp_enqueue_script('media-upload');
				wp_enqueue_script('thickbox'); 
				
				wp_register_script('SFSIJqueryFRM', SFSI_PLUGURL . 'js/jquery.form-min.js', '', '', true);
				wp_enqueue_script("SFSIJqueryFRM");
				
				
				wp_enqueue_script("jquery-ui-accordion");	
				wp_enqueue_script("wp-color-picker");
				wp_enqueue_script("jquery-effects-core");
				wp_enqueue_script("jquery-ui-sortable");
				
				wp_register_script('SFSICustomFormJs', SFSI_PLUGURL . 'js/custom-form-min.js', '', '', true);
				wp_enqueue_script("SFSICustomFormJs");
				
				wp_register_script('SFSICustomJs', SFSI_PLUGURL . 'js/custom-admin.js', '', '', true);
				wp_enqueue_script("SFSICustomJs");
				/* end cusotm js */
				
				/* initilaize the ajax url in javascript */
				wp_localize_script( 'SFSICustomJs', 'ajax_object', array( 'ajax_url' => admin_url( 'admin-ajax.php' ) ) );
				wp_localize_script( 'SFSICustomJs', 'ajax_object', array( 'ajax_url' => admin_url( 'admin-ajax.php' ),'plugin_url'=> SFSI_PLUGURL) );
			}
		}			
}
add_action( 'admin_enqueue_scripts', 'theme_back_enqueue_script' );

function theme_front_enqueue_script()
{
		wp_enqueue_script('jquery');
	 	wp_enqueue_script("jquery-migrate");
		
		wp_enqueue_script('jquery-ui-core');
		
		wp_register_script('SFSIjqueryModernizr', SFSI_PLUGURL . 'js/shuffle/modernizr.custom.min.js', array('jquery'),'',true);
		wp_enqueue_script("SFSIjqueryModernizr");
		
		wp_register_script('SFSIjqueryShuffle', SFSI_PLUGURL . 'js/shuffle/jquery.shuffle.min.js', array('jquery'),'',true);
		wp_enqueue_script("SFSIjqueryShuffle");
		
		wp_register_script('SFSIjqueryrandom-shuffle', SFSI_PLUGURL . 'js/shuffle/random-shuffle-min.js', array('jquery'),'',true);
		wp_enqueue_script("SFSIjqueryrandom-shuffle");
		
		wp_register_script('SFSICustomJs', SFSI_PLUGURL . 'js/custom.js', array('jquery'),'',true);
		wp_enqueue_script("SFSICustomJs");
		/* end cusotm js */
		
		/* initilaize the ajax url in javascript */
		wp_localize_script( 'SFSICustomJs', 'ajax_object', array( 'ajax_url' => admin_url( 'admin-ajax.php' ) ) );
		wp_localize_script( 'SFSICustomJs', 'ajax_object', array( 'ajax_url' => admin_url( 'admin-ajax.php' ),'plugin_url'=> SFSI_PLUGURL) );
		
		/* include CSS for front-end and backend  */
		wp_enqueue_style("SFSImainCss", SFSI_PLUGURL . 'css/sfsi-style.css' );
		
		//including floating option css
		$option5=  unserialize(get_option('sfsi_section5_options',false));
		if($option5['sfsi_disable_floaticons'] == 'yes')
		{
			wp_enqueue_style("disable_sfsi", SFSI_PLUGURL . 'css/disable_sfsi.css' );
		}
}
add_action( 'wp_enqueue_scripts', 'theme_front_enqueue_script' );

/*function sfsi_footerFeedbackScript()
{
    wp_enqueue_style('wp-pointer');
    wp_enqueue_script('wp-pointer');
    wp_enqueue_script('utils'); // for user settings
	
	$html = '<div>';
		$html .= '<label>Optional: Please tell us why you deactivate our plugin so that we can make it better!</label>';
		$html .= '<textarea id="sfsi_feedbackMsg" name="reason"></textarea>';
	$html .= '</div>';
	?>
    <script type="text/javascript">
		jQuery('#sfsi_deactivateButton').click(function(){
			jQuery('#sfsi_deactivateButton').pointer({
				content: '<form method="post" id="sfsi_feedbackForm"><?php echo $html; ?><div><input type="button" name="sfsi_sendFeedback" value="Deactivate" class="button primary-button" /></div><img id="sfsi_loadergif" src="<?php echo site_url()."/wp-includes/images/spinner.gif"; ?>" /></form>',
				position: {
					edge:'top',
					align:'left',
				},
				close: function() {
					//
				}
			}).pointer('open');
			return false;
		});
		jQuery("body").on("click","input[name='sfsi_sendFeedback']", function(){
			var ajaxurl = '<?php echo admin_url('admin-ajax.php'); ?>';
			var deactivateUrl = jQuery('#social-media-and-share-icons-ultimate-social-media .deactivate a').attr('href');
			var e = {
				action:"sfsi_feedbackForm",
				email: '<?php echo get_option("admin_email"); ?>',
				msg:jQuery("#sfsi_feedbackMsg").val()
			};
			jQuery("#sfsi_loadergif").show();
			jQuery.ajax({
				url:ajaxurl,
				type:"post",
				data:e,
				success:function(responce) {
					//alert(responce);
					jQuery("#sfsi_loadergif").hide();
					window.location.href = deactivateUrl;
				}
			});
		});
	</script>
	<?php
}
add_action( 'admin_footer', 'sfsi_footerFeedbackScript' );*/
?>
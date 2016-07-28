<?php

// loads the notices jQuery
function pippin_notice_js() {
	$logged_in = 'yes';
	wp_enqueue_style( 'notifications', SIMPLE_NOTICES_URL . 'css/notifications.css');
	if(!is_user_logged_in()) {
		wp_enqueue_script( 'jquery-coookies', SIMPLE_NOTICES_URL . 'js/jquery.cookie.js', array( 'jquery' ) );
		$logged_in = 'no';
	}
	wp_enqueue_script( 'notifications', SIMPLE_NOTICES_URL . 'js/notifications.js', array( 'jquery' ) );
	wp_localize_script( 'notifications', 'notices_ajax_script', array( 
		'ajaxurl' => admin_url( 'admin-ajax.php' ),
		'logged_in' => $logged_in
		)
	);	
}
add_action('wp_enqueue_scripts', 'pippin_notice_js');
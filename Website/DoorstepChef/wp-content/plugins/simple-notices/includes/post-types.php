<?php

/////////////////////////////////////////////////
// notice custom post type
/////////////////////////////////////////////////

function pippin_create_notices() {

	$labels = array(
		'name' => _x( 'Notices', 'post type general name' ), // Tip: _x('') is used for localization
		'singular_name' => _x( 'Notice', 'post type singular name' ),
		'add_new' => _x( 'Add New', 'Notice' ),
		'add_new_item' => __( 'Add New Notice' ),
		'edit_item' => __( 'Edit Notice' ),
		'new_item' => __( 'New Notice' ),
		'view_item' => __( 'View Notice' ),
		'search_items' => __( 'Search Notices' ),
		'not_found' =>  __( 'No Notices found' ),
		'not_found_in_trash' => __( 'No Notices found in Trash' ),
		'parent_item_colon' => ''
	);

 	$notice_args = array(
     	'labels' => $labels,
     	'singular_label' => __('Notice', 'simple-notices'),
     	'public' => true,
     	'show_ui' => true,
	  	'capability_type' => 'post',
     	'hierarchical' => false,
     	'rewrite' => false,
     	'supports' => array('title', 'editor'),
     );
 	register_post_type('notices', $notice_args);
}
add_action('init', 'pippin_create_notices');
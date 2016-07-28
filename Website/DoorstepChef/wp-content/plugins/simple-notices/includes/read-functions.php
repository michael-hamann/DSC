<?php

function pippin_check_notice_is_read($post_id, $user_id) {
	
	$read_notices = get_user_meta($user_id, 'pippin_notice_ids', true);
	if($read_notices && is_array($read_notices)) {
		if(in_array($post_id, $read_notices)) {
			return true;
		}
	}
	
	// if not closed
	return false;
	
}

function pippin_notice_add_to_usermeta($post_id) {
	global $user_ID;
	$read_notices = get_user_meta($user_ID, 'pippin_notice_ids', true);
	$read_notices[] = $post_id;
	
	update_user_meta($user_ID, 'pippin_notice_ids', $read_notices);
}

function pippin_notice_mark_as_read() {
	if(isset($_POST['notice_read'])) {
		$notice_id = intval($_POST['notice_read']);
		$marked_as_read = pippin_notice_add_to_usermeta($notice_id);
	}
	die();
}
add_action('wp_ajax_mark_notice_as_read', 'pippin_notice_mark_as_read');

<?php

//custom meta boxes
$sn_prefix = 'sn_';

$sn_meta_box = array(
    'id' => 'sn_meta_box',
    'title' => __('Notice Configuration', 'simple-notices'),
    'context' => 'side',
    'priority' => 'low',
    'fields' => array(
        array(
        	'name' => __('Color', 'rcp'),
        	'id' => '_notice_color',
        	'type' => 'select',
        	'desc' => __('Choose the notice color', 'simple-notices'),
			'options' => array('Blue', 'Red', 'Orange', 'Green', 'Gray')
     	),
		array(
        	'name' => __('Logged In Users', 'rcp'),
        	'id' => '_notice_for_logged_in_only',
        	'type' => 'checkbox',
        	'desc' => __('Logged-in users only', 'simple-notices')
     	)
    )
);

// Add meta box


function sn_add_meta_boxes() {
    global $sn_meta_box;
	add_meta_box($sn_meta_box['id'], $sn_meta_box['title'], 'sn_render_meta_box', 'notices', $sn_meta_box['context'], $sn_meta_box['priority']);
}
add_action('admin_menu', 'sn_add_meta_boxes');


// Callback function to show fields in meta box
function sn_render_meta_box() {
    global $sn_meta_box, $post;
    
    // Use nonce for verification
    echo '<input type="hidden" name="sn_meta_box" value="', wp_create_nonce(basename(__FILE__)), '" />';
    
    echo '<table class="form-table">';
	
    foreach ($sn_meta_box['fields'] as $field) {
        // get current post meta data
        $meta = get_post_meta($post->ID, $field['id'], true);
        
        echo '<tr>';
			echo '<td>', $field['desc'], '</td>';
            echo '<td>';
				switch ($field['type']) {
					case 'select':
						echo '<select name="', $field['id'], '" id="', $field['id'], '">';
						foreach ($field['options'] as $option) {
							echo '<option', $meta == $option ? ' selected="selected"' : '', '>', $option, '</option>';
						}
						echo '</select>';
						break;
					case 'checkbox':
						echo '<input type="checkbox" value="1" name="', $field['id'], '" id="', $field['id'], '"', $meta ? ' checked="checked"' : '', ' />';
						break;
				}
			echo '</td>';			
        echo '</tr>';
    }
    
    echo '</table>';
}

// Save data from meta box
function sn_save_meta_data($post_id) {
    global $sn_meta_box;
    
    // verify nonce
    if (!isset($_POST['sn_meta_box']) || !wp_verify_nonce($_POST['sn_meta_box'], basename(__FILE__))) {
        return $post_id;
    }

    // check autosave
    if (defined('DOING_AUTOSAVE') && DOING_AUTOSAVE) {
        return $post_id;
    }

    // check permissions
    if ('page' == $_POST['post_type']) {
        if (!current_user_can('edit_page', $post_id)) {
            return $post_id;
        }
    } elseif (!current_user_can('edit_post', $post_id)) {
        return $post_id;
    }
    
    foreach ($sn_meta_box['fields'] as $field) {
		if(isset($_POST[$field['id']])) {
			
			$old = get_post_meta($post_id, $field['id'], true);
			$data = $_POST[$field['id']];
			
			if (($data || $data == 0) && $data != $old) {
				update_post_meta($post_id, $field['id'], $data);
			} elseif ('' == $data && $old) {
				delete_post_meta($post_id, $field['id'], $old);
			}
		} else {
			delete_post_meta($post_id, $field['id']);
		}
    }
}
add_action('save_post', 'sn_save_meta_data');
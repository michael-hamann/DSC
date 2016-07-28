<?php
/*
Plugin Name: Simple Notices
Plugin URI: http://pippinsplugins.com/simple-notices-plugin
Description: A simple notices plugin for WordPress
Version: 1.0.7
Author: Pippin Williamson
Author URI: http://pippinsplugins.com
Contributors: mordauk
*/

if(!defined('SIMPLE_NOTICES_DIR')) define('SIMPLE_NOTICES_URL', plugin_dir_url( __FILE__ ));

include('includes/post-types.php');
include('includes/scripts.php');
include('includes/read-functions.php');

if(!is_admin()) {
	include('includes/display-functions.php');
} else {
	include('includes/metabox.php');
}

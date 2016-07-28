<?php
/*
  Plugin Name: WP Construction Mode
  Plugin URI: http://smartcatdesign.net/downloads/construction-mode-v2-pro/
  Description: Display a customizable Under Construction or Coming Soon landing page for all users except the admin. Perfect for developing a new site!
  Version: 3.31
  Author: SmartCat
  Author URI: http://smartcatdesign.net
  License: GPL v2
  Text domain: sc-construction
  @author:  Bilal Hassan <bilal@smartcat.ca>
  @copyright: Smartcat <info@smartcatdesign.net>
 */

// Exit if accessed directly
if( !defined( 'ABSPATH' ) ) {
    die;
}
if (!defined('SC_CONSTRUCTION_PATH'))
    define('SC_CONSTRUCTION_PATH', plugin_dir_path(__FILE__));
if (!defined('SC_CONSTRUCTION_URL'))
    define('SC_CONSTRUCTION_URL', plugin_dir_url(__FILE__));

require_once ( plugin_dir_path( __FILE__ ) . 'inc/class/class.smartcat-construction.php' );


// activation and de-activation hooks
register_activation_hook( __FILE__, array( 'SmartcatConstructionPlugin', 'activate' ) );
register_deactivation_hook( __FILE__, array( 'SmartcatConstructionPlugin', 'deactivate') );

SmartcatConstructionPlugin::instance();

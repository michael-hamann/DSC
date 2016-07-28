<?php
/*
Plugin Name: SEO Consultant
Plugin URI: http://ketchupthemes.com/seo-consultant
Description: The Ultimate WordPress SEO Tool For Backlinks Reporting and Off Page Analysis.
Version: 1.1.3
Author: Alex Itsios
Author URI: http://www.ketchupthemes.com
License: GPL-2.0+
License URI: http://www.gnu.org/licenses/gpl-2.0.txt
Network: true
Text Domain: seo_consultant
Domain Path: /language
*/

//Set seo consultant file value;
if (!defined('_SEOCONS_FILE')) {

    define('_SEOCONS_FILE', __FILE__);
    define('_SEOCONS_TEXTDOMAIN', 'seo_consultant');
    define('_SEOCONS_PATH', plugin_dir_path(_SEOCONS_FILE));
}

//Require application class
require_once _SEOCONS_PATH . '/app/SeoConsultant.php';
require_once _SEOCONS_PATH . '/app/AutoLoader.php';

//Autoloading all modules and files
$autoloader = new AutoLoader(_SEOCONS_PATH);


/**
 * Initialize Plugin
 */
if (!function_exists('seo_consultant_start')) {
    function seo_consultant_start()
    {
        $seoConsultant = new SeoConsultant();
    }

    add_action('plugins_loaded', 'seo_consultant_start', 1);
}


/**
 * Methods which run when plugin is in activation status
 */
if (!function_exists('seo_consultant_activation')) {
    function seo_consultant_activation()
    {
        $db = \SeoConsultant\Database::getInstance();
        $db->setupDatabase();
    }
    register_activation_hook(_SEOCONS_FILE, 'seo_consultant_activation');
}


if (!function_exists('seo_block_unwanted_traffic')) {
    function seo_block_unwanted_traffic()
    {
        ob_start();
        $analyzer = new \SeoConsultant\AnalyzeRequest;
        $is_data = $analyzer->analyzeReferer();

        if ($is_data) {
            $db = \SeoConsultant\Database::getInstance();
            $blocked_referrers = $db->getAllDomains(array('is_blocked' => 1));

            $is_forbitten_header = false;
            if(!empty($blocked_referrers)) {
                foreach ($blocked_referrers as $blocked_referrer) {
                    if (preg_match('/(' . $blocked_referrer['DOMAIN_NAME'] . ')/', $analyzer->referer['domain'])) {
                        $is_forbitten_header = true;
                    }
                }
            }
            
            if ($is_forbitten_header) {
                header("Location: " . $_SERVER['HTTP_REFERER'], true, 303);
                exit();

                ob_flush();
            }
        }
    }

    add_action('send_headers', 'seo_block_unwanted_traffic', 1);
}


/**
 * This function creates a document based of a GET query
 */
if (!function_exists('seo_downloading_page')) {
    function seo_downloading_page()
    {
        if(is_user_logged_in() && current_user_can('edit_posts')) {
            $requestedPage = filter_input(INPUT_GET, 'seocons-page', FILTER_SANITIZE_STRING);
            if ($requestedPage) {
                ob_start(); //The ob_start, is to prevent wordpress's "headers all ready sent"!

                //get type value
                $type = (isset($_GET['type'])) ? filter_input(INPUT_GET, 'type', FILTER_SANITIZE_STRING) : false;

                //redirect to the file generator responsible for the requested type, if type is not defined, redirects
                //to the admin panel
                $class = '\SeoConsultant\Generate' . strtoupper($type);
                if (class_exists($class)) {
                    $file = new $class();
                    $file->generateContent($requestedPage);
                } else {
                    header('Location:' . admin_url());
                }

                exit();
                ob_flush(); //The ob_flush, is to prevent wordpress's "headers all ready sent"!
            }
        }
    }
    add_action('init', 'seo_downloading_page', 1);
}

if (!function_exists('seo_update_ignorelist')) {
    function seo_update_ignorelist()
    {
       $ignore_list = array(
            'Baidu','Bing','DuckDuckGo','Exalead','Gigablast','Google','Munax','Qwant','Sogou','Soso','Yahoo','Yandex','Youdao'
       );
        $db = \SeoConsultant\Database::getInstance();
        $results = $db->getIndexInIgnoredList($ignore_list);

        $ids = array();
        if($results){
            foreach($results as $result){
                $ids[] = $result['ID'];
            }
            $db->setIgnored($ids);
        }

    }
    add_action('init', 'seo_update_ignorelist', 1);
}

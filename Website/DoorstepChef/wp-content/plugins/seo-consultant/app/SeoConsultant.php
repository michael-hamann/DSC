<?php

/**
 * Created by ketchupthemes.com
 */

require_once 'AutoLoader.php';


class SeoConsultant
{
    const VERSION = 0.96;

    //Plugin name
    public static $pluginName = 'seo_consultant';

    //Language folder path
    public $langPath = FALSE;

    //Folder
    public $folder = FALSE;

    //Plugin Path
    public static $path = FALSE;

    //Localized vars, for use with javascript
    private $localvars = NULL;

    private $messages = NULL;

    private $authorization = FALSE;

    //Instance of the Filter Class
    private static $filter = FALSE;


    //Constructor
    public function __construct()
    {
        global $wpdb;

        self::$path = plugin_dir_path(_SEOCONS_FILE) . '/app';
        $this->folder = dirname(_SEOCONS_FILE);
        $this->langPath = $this->folder . '/language';
        $this->messages = $this->setMessages();
        $this->authorization = current_user_can('edit_posts');


        //local vars
        $this->localvars = array(
            'admin' => admin_url('admin-ajax.php'),
            'access_key' => wp_create_nonce('seo-cons-nonce'),
            'translations' => array()
        );

        self::$filter = new SeoConsultant\RequestFilter;

        $this->init();


        $logger = new \SeoConsultant\Logger;
    }

    /**
     * Initializes all of the plugins functionality
     */
    public function init()
    {
        add_action('init', array($this, 'loadLanguage'), 1);

        $this->update();

        //Enqueue Scripts and Stylesheets
        add_action('admin_enqueue_scripts', array($this, 'loadStylesheets'), 1 );

        add_action( 'current_screen', array($this, 'do_in_screen'),1);

        //Initialize Ajax Handler
        add_action('wp_ajax_seocons_handle_request', array($this, 'handleAjaxRequest'));

        //Initialize Admin Pages
        if($this->authorization){
            $this->addAdminPages();
        }


        //Enable dashboard widgets if the option is active
        //The option is in Settings page, The "Enable Widgets" option
        if( !get_option('disable-widgets', false) && $this->authorization){
            $this->addDashboardWidgets();
        }

    }

    public function update(){
       // echo get_option('seo_consultant_current_version', 0.1);
        //Check if internal version is smaller than plugin's version and if so
        //attempts to update the database if there is an update!
        $current_version = floatval(self::VERSION);
        $system_version = floatval(get_option('seo_consultant_current_version', 0.1));

        if($current_version > $system_version){
            $db = SeoConsultant\Database::getInstance();
            $db->updateTables();

            //Update plugin internal version
            update_option('seo_consultant_current_version', $current_version);
        }
    }

    public function loadStylesheets(){
        wp_enqueue_style('seo-font-awesome', $this->_url('/assets/stylesheets/font-awesome.min.css'), array(), '1.0');
        wp_enqueue_style('seo-consultant-css', $this->_url('/assets/stylesheets/style.css'), array('seo-font-awesome'), '1.0');
    }

    public function loadScripts(){
        wp_register_script('seo-angular-js', $this->_url('/assets/javascripts/angular.min.js'), array(), '1.4.3', false);

        //Bootstraping
        wp_register_script('seo-bootstrap-js', $this->_url('/assets/javascripts/seo-bootstrap.js'), array('seo-consultant-js', 'seo-async-service-js', 'seo-controller-js'), '1.0', false);
        wp_register_script('seo-consultant-js', $this->_url('/assets/javascripts/seo-consultant.js'), array('seo-angular-js'), '1.0', false);

        //Services
        wp_register_script('seo-async-service-js', $this->_url('/assets/javascripts/services/seo-async-service.js'), array('seo-consultant-js'), '1.0', false);

        //Controllers
        wp_register_script('seo-blocked-ips-main-controller-js', $this->_url('/assets/javascripts/controllers/BlockedIPsMainController.js'), array('seo-consultant-js','seo-async-service-js'), '1.0', false);
        wp_register_script('seo-broken-links-main-controller-js', $this->_url('/assets/javascripts/controllers/BrokenLinksMainController.js'), array('seo-consultant-js','seo-async-service-js'), '1.0', false);
        wp_register_script('seo-referrers-main-controller-js', $this->_url('/assets/javascripts/controllers/ReferrersMainController.js'), array('seo-consultant-js','seo-async-service-js'), '1.0', false);
        wp_register_script('seo-referring-pages-main-controller-js', $this->_url('/assets/javascripts/controllers/ReferringPagesMainController.js'), array('seo-consultant-js','seo-async-service-js'), '1.0', false);
        wp_register_script('seo-stats-main-controller-js', $this->_url('/assets/javascripts/controllers/StatsMainController.js'), array('seo-consultant-js','seo-async-service-js'), '1.0', false);
        wp_register_script('seo-anchor-distribution-main-controller-js', $this->_url('/assets/javascripts/controllers/AnchorDistributionMainCtrl.js'), array('seo-consultant-js','seo-async-service-js'), '1.0', false);

        //Concat, for the application to work properly and organized. Once you add a controller above! You must add it to controllers.js dependencies.
        wp_register_script(
            'seo-controller-js',
            $this->_url('/assets/javascripts/controllers/controllers.js'),
            array(
            'seo-stats-main-controller-js',
            'seo-anchor-distribution-main-controller-js',
            'seo-referring-pages-main-controller-js',
            'seo-referring-pages-main-controller-js',
            'seo-referrers-main-controller-js',
            'seo-broken-links-main-controller-js',
            'seo-blocked-ips-main-controller-js'), '1.0', false);


        //Localize Vars
        wp_localize_script('seo-consultant-js', 'seovars', $this->localvars );

        //Enqueue All scripts
        wp_enqueue_script('seo-bootstrap-js');
    }

    /**
     * Load plugin textdomain
     */
    public function loadLanguage($relpath_to_folder = FALSE)
    {
        if ($relpath_to_folder) {
            $this->langPath = $this->folder . $relpath_to_folder;
        }
        load_plugin_textdomain(self::$pluginName, false, $this->langPath);
    }

    /**
     * Initializes the Backend pages for this plugin
     */
    public function addAdminPages()
    {
        add_action('admin_menu', array('SeoConsultant\Settings', 'registerPage'));
    }

    /**
     * Initiliazes the Dashboard Widgets
     */
    public function addDashboardWidgets()
    {
        $dashboard = new SeoConsultant\Dashboard;
        $dashboard->initializeWidgets();
    }

    public function handleAjaxRequest(){
        if($this->authorization){
            die($this->processRequest());
        } else {
            die($this->messages['request_denial']);
        }

    }

    public function do_in_screen(){
        $current_screen = get_current_screen();

        if($current_screen->base === 'dashboard'){
            add_action('admin_enqueue_scripts', array($this, 'loadScripts'), 1);
        }
    }

    /**
     * Helper method for logging potential problematic outputs
     *
     * @param $text
     * @return mixed
     */
    private function writeFile($text){
        $file = fopen(plugin_dir_path(_SEOCONS_FILE)."log.txt",'a');
        fputs($file, $text."\n\r");
        fclose($file);

        return $text;
    }

    /**
     * Utility function for getting the absolute path for a resource
     *
     * @param $path
     * @return string
     */
    private function _url($path){
        return plugins_url($path, _SEOCONS_FILE);
    }


    /**
     * Utility method for handling internal async post requests
     *
     * @return mixed|string|void
     */
    private function processRequest(){
        if(!wp_verify_nonce(self::$filter->post('access_key'),'seo-cons-nonce')){
            die($this->messages['request_denial']);
        }

        $database = SeoConsultant\Database::getInstance();

        $operation = self::$filter->post('operation');
        $data = self::$filter->paramString('data');

        return json_encode($database->{$operation}($data));
    }

    /**
     * Store method for clarity to set application's messages
     *
     * @return array
     */
    private function setMessages(){
        return array(
            'request_denial' => __('You think, it\'s going to be easy! No! no! no!, Mon\'Amie')
        );
    }






}
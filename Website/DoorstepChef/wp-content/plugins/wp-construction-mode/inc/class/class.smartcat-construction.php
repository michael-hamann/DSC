<?php

/**
 * Plugin Class
 * 
 */
class SmartcatConstructionPlugin {

    const VERSION = '3.24';

    private static $instance;
    private $options;

    public static function instance() {
        if ( !self::$instance ) :
            self::$instance = new self;
            self::$instance->get_options();
            self::$instance->add_hooks();
        endif;
    }

    public static function activate() {

        $options = array(
            'mode' => false,
            'set_page' => 'all',
            'template' => 'progress',
            'percentage' => 30,
            'logo' => '',
            'backgound_color' => '#666',
            'font_color' => '#fff',
            'accent_color' => '#e5640d',
            'background_image' => SC_CONSTRUCTION_URL . '/inc/img/construction.jpg',
            'title' => 'We are working on something awesome',
            'sub_title' => 'We will be back soon!',
            'animation' => 'fade',
            'shortcode' => '',
            'facebook' => 'http://smartcatdesign.net',
            'gplus' => 'http://smartcatdesign.net',
            'twitter' => 'http://smartcatdesign.net',
            'email' => '',
            'instagram' => 'http://smartcatdesign.net',
            'digg' => 'http://smartcatdesign.net',
            'flickr' => '',
            'skype' => '',
            'tumblr' => '',
            'youtube' => '',
            'analytics' => '',
        );

        if ( !get_option( 'smartcat_construction_options' ) ) {
            add_option( 'smartcat_construction_options', $options );
            $options[ 'redirect' ] = true;
            update_option( 'smartcat_construction_options', $options );            
        }else {
            
            $option = get_option('smartcat_construction_options');
            
            if( !isset( $option['analytics'] ) ) :
                
                $options['analytics'] = '';
                update_option( 'smartcat_construction_options', $options );
                
            endif;
            
        }

    }

    public static function deactivate() {
        
    }

    private function add_hooks() {
        
        add_action('template_redirect', array( $this, 'preview' ) );
        
        if( $this->options['mode'] ) :
            add_action( 'init', array( $this,  'escape_catch_redirect') );
        endif;
        
        add_action( 'admin_init', array( $this, 'smartcat_construction_activation_redirect' ) );
        add_action( 'wp_head', array( $this, 'sc_custom_styles' ) );
        add_action( 'admin_enqueue_scripts', array( $this, 'smartcat_construction_load_admin_styles_scripts' ) );
        add_action( 'admin_menu', array( $this, 'under_construction_menu' ) );
        add_action( 'admin_bar_menu', array( $this, 'create_admin_bar_menu' ), 1000 );
        add_action('plugins_loaded', array( $this, 'load_textdomain' ) );
        
    }
    
    public function load_textdomain(){
        
        load_plugin_textdomain( 'sc-construction', FALSE, dirname( plugin_basename( __FILE__ ) ) . '/languages' );
        
    }
    
    
    public function preview() {
        

        $template = SC_CONSTRUCTION_PATH . 'inc/template/construction.php';

        if ( isset( $_GET['smartcat_construction'] ) && $_GET['smartcat_construction'] == 'preview') :

            include_once $template;
            exit();
            
        endif;        
        
    }    

    public function escape_catch_redirect(){
        global $pagenow;
        
        if( 'wp-login.php' == $pagenow ) :
            return;
        else :
            add_action( 'template_redirect', array( $this, 'catch_redirect') );
        endif;
    }
    
    private function get_options() {
        if ( get_option( 'smartcat_construction_options' ) ) :
            $this->options = get_option( 'smartcat_construction_options' );
        endif;
    }

    public function smartcat_construction_activation_redirect() {

        if ( isset( $this->options[ 'redirect' ] ) && $this->options[ 'redirect' ] ) :
            $old_val = $this->options;
            $old_val[ 'redirect' ] = false;
            update_option( 'smartcat_construction_options', $old_val );
            wp_safe_redirect( admin_url( 'admin.php?page=under_construction' ) );
        endif;
    }

    public function under_construction_menu() {
        add_menu_page( 'Construction Mode', 'Construction Mode', 'manage_options', 'under_construction', array($this, 'show_under_construction_menu'), 'dashicons-hammer');
    }

    public function show_under_construction_menu() {

        if ( isset( $_REQUEST[ 'smartcat_construction_save' ] ) && $_REQUEST[ 'smartcat_construction_save' ] == 'Update' ) :
            update_option( 'smartcat_construction_options', $_REQUEST[ 'smartcat_construction_options' ] );
        endif;

        include_once SC_CONSTRUCTION_PATH . 'admin/form.php';
    }

    public function smartcat_load_screen() {

        $current_user = wp_get_current_user();
        extract( $this->options );
        if ( $this->options[ 'mode' ] && !user_can( $current_user, 'administrator' ) ) {
            if ( $this->options[ 'set_page' ] == get_the_ID() || $this->options[ 'set_page' ] == 'all' ) {
                
                include_once SC_CONSTRUCTION_PATH . 'inc/template/construction.php';
                exit( 0 );
            }
        }
    }

    public function create_admin_bar_menu( $admin_bar ) {

        if ( !$this->options[ 'mode' ] )
            return;

        $admin_bar->add_menu( array(
            'id' => 'smartcat_construction',
            'title' => 'Construction Mode',
            'href' => admin_url() . 'admin.php?page=under_construction',
        ) );
    }

    public function smartcat_construction_load_admin_styles_scripts( $hook ) {

        wp_enqueue_style( 'wp-color-picker' );
        wp_enqueue_script( 'jquery-ui-tooltip' );
        wp_enqueue_style( 'thickbox' );
        wp_enqueue_script( 'media-upload' );

        if ($hook == 'toplevel_page_under_construction') :
            wp_enqueue_style( 'smartcat_construction_admin_style', SC_CONSTRUCTION_URL . 'inc/style/style_admin.css' );
            wp_enqueue_script( 'smartcat_construction_script', SC_CONSTRUCTION_URL . 'inc/script/script_admin.js', array( 'jquery', 'wp-color-picker', 'media-upload', 'thickbox', 'jquery-ui-tooltip' ) );
        endif;
    }

    function smartcat_construction_load_styles_scripts() {

        // plugin main style
        wp_enqueue_style( 'smartcat_construction_default_style', SC_CONSTRUCTION_URL . 'inc/style/style.css', false, '1.0' );

        wp_enqueue_style( 'smartcat_construction_fontawesome', SC_CONSTRUCTION_URL . 'inc/style/font-awesome.min.css', false, '1.0' );
        // plugin main script
        wp_enqueue_script( 'smartcat_construction_default_script', SC_CONSTRUCTION_URL . 'inc/script/script.js', array( 'jquery' ), '1.0' );
    }

    public function sc_custom_styles() {?>

        <style>
            .wuc-overlay{ background-image: url('<?php echo $this->options[ 'background_image' ]; ?>'); }
            #wuc-wrapper #wuc-box h2.title,
            #wuc-box .subtitle,
            #defaultCountdown,
            #defaultCountdown span{ color: <?php echo $this->options[ 'font_color' ]; ?> }
        </style>
        <?php
    }
    public function catch_redirect(){
        
        $current_user = wp_get_current_user();             
        
        if (( $this->options['mode'] && !user_can($current_user, 'administrator') && !user_can($current_user, 'editor'))) :
            if( $this->options['set_page'] == 'all' || $this->options['set_page'] == get_the_ID() ) :
                include_once SC_CONSTRUCTION_PATH . 'inc/template/construction.php';
                exit();
            endif;
        endif;
        
        
    }
}

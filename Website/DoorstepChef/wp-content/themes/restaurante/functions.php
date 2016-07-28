<?php
/**==================== =
 * Main Functions file.
 * Please be careful when
 * editing.
 *===================== */

/**=== Setup Theme == **/

DEFINE('FRAMEWORK_PATH',get_template_directory().'/framework/');


add_action('after_setup_theme','restaurante_setup_theme');
function restaurante_setup_theme(){

        /**== Set main content width ==**/
        if(!isset( $content_width ))
            $content_width = 750;

        /**== Add RSS links to the theme ==**/
        add_theme_support( 'automatic-feed-links' );

        /**== This theme is ready for translation ==**/
        load_theme_textdomain( 'restaurante', get_template_directory(). '/languages' );

        /**== Add HTML5 to some elements -galleries and captions ==**/
        add_theme_support('html5', array( 'gallery', 'caption' ));

        /**== Add newest title-tag support for wordpress version 4.1 and above ==**/
        global $wp_version;
        if (version_compare( $wp_version, '4.1', '>=' )):
            add_theme_support( 'title-tag' );
        endif;

        /**== Register the navigation menus and positions ==**/
        register_nav_menus( array(
            'main' =>       __( 'Main Navigation', 'restaurante' ),
            'footer'=>     __('Footer Menu','restaurante')
        ));


        /**== Custom Background ==**/
        $restaurante_restaurant_background_defaults = array(
            'default-color' => 'ffffff',
            'default-image' => '',
            'wp-head-callback' => 'restaurante_background_callback',
        );
        add_theme_support( 'custom-background', $restaurante_restaurant_background_defaults );

        /**== Custom Header Image ==**/
        $restaurante_restaurant_header_defaults = array(
            'default-image'          => '',
            'random-default'         => false,
            'width'                  => '1920',
            'height'                 => '750',
            'flex-height'            => false,
            'flex-width'             => false,
            'default-text-color'     => '',
            'header-text'            => false,
            'uploads'                => true,
            'wp-head-callback'       => '',
            'admin-head-callback'    => '',
            'admin-preview-callback' => '',
        );
        add_theme_support( 'custom-header', $restaurante_restaurant_header_defaults );



        /**== This theme uses featured images ==**/
        add_theme_support('post-thumbnails' );
        add_image_size('restaurante-restaurant-slider-full',1920,950,true);
        add_image_size('restaurante-restaurant-member',419,658,true);
        add_image_size('menu-item-image',100,100,true);
        add_image_size('blog-image',750,394,true);
        add_image_size('member-image',320,320,true);
        add_image_size('latest-event',1140,320,true);

        add_image_size('restaurante-restaurant-page-image-col-md-12',1140,420,true);
        add_image_size('restaurante-restaurant-page-image-col-md-8',895,400,true);
        add_image_size('restaurante-restaurant-page-image-col-md-6',585,280,true);
        add_image_size('restaurante-restaurant-page-image-col-md-4',330,250,true);
        add_image_size('restaurante-restaurant-page-image-col-md-3',295,220,true);
        add_image_size('restaurante-restaurant-recent-posts-image',100,100,true);
    }


/**== Setup theme ends here ==**/

/**================================================**/
/** Fallback functions
 **  - Background Callback
 **  - Navigation Menu callback (when is not a
 *     menu set up)
/**================================================**/
if(!function_exists('restaurante_background_callback')):

    function restaurante_background_callback() {
        $background = set_url_scheme( get_background_image() );
        $color = get_theme_mod( 'background_color', get_theme_support( 'custom-background', 'default-color' ) );

        if ( ! $background && ! $color )
            return;

        $style = $color ? "background-color: #$color;" : '';

        if ( $background ) {
            $image = " background-image: url('$background');";

            $repeat = get_theme_mod( 'background_repeat', get_theme_support( 'custom-background', 'default-repeat' ) );
            if ( ! in_array( $repeat, array( 'no-repeat', 'repeat-x', 'repeat-y', 'repeat' ) ) )
                $repeat = 'repeat';
            $repeat = " background-repeat: $repeat;";

            $position = get_theme_mod( 'background_position_x', get_theme_support( 'custom-background', 'default-position-x' ) );
            if ( ! in_array( $position, array( 'center', 'right', 'left' ) ) )
                $position = 'left';
            $position = " background-position: top $position;";

            $attachment = get_theme_mod( 'background_attachment', get_theme_support( 'custom-background', 'default-attachment' ) );
            if ( ! in_array( $attachment, array( 'fixed', 'scroll' ) ) )
                $attachment = 'scroll';
            $attachment = " background-attachment: $attachment;";

            $style .= $image . $repeat . $position . $attachment;
        }
        ?>
        <style type="text/css" id="custom-background-css">
            body.custom-background { <?php echo trim( $style ); ?> }
        </style>
    <?php
    }
endif;

if(!function_exists('restaurante_restaurant_fallback_menu')):
    function restaurante_restaurant_fallback_menu($args){
        if ( ! current_user_can( 'manage_options' ) )
        {
            return;
        }
        extract( $args );
        $link = $link_before
            . '<a href="' .admin_url( 'nav-menus.php' ) . '">' . $before . __('Add a menu','restaurante') . $after . '</a>'
            . $link_after;
        if ( FALSE !== stripos( $items_wrap, '<ul' )
            or FALSE !== stripos( $items_wrap, '<ol' )
        )
        {
            $link = "<li>$link</li>";
        }

        $output = sprintf( $items_wrap, $menu_id, $menu_class, $link );
        if ( ! empty ( $container ) )
        {
            $output  = "<$container class='$container_class' id='$container_id'>$output</$container>";
        }
        if ( $echo )
        {
            echo $output;
        }

        return $output;
    }
endif;


/**================================================**/
/**
 * Enqueue CSS & JS for the theme.
 **
/**================================================**/

add_action('wp_enqueue_scripts','restaurante_add_stylesheets');
function restaurante_add_stylesheets(){

    wp_enqueue_style( 'restaurante-restaurant-bootstrap', get_template_directory_uri().'/css/bootstrap.min.css','','','all' );

    wp_enqueue_style( 'restaurante-restaurantbootstrap-theme', get_template_directory_uri().'/css/bootstrap-theme.min.css','','','all' );

    wp_enqueue_style( 'restaurante-restaurant-fontawesome', get_template_directory_uri().'/css/font-awesome.min.css','','','all' );


    wp_enqueue_style( 'restaurante-montserrat', get_template_directory_uri().'/fonts/Montserrat-Font/stylesheet.css','','','all' );
    wp_enqueue_style( 'restaurante-kaushan', get_template_directory_uri().'/fonts/Kaushan-Script-Font/stylesheet.css','','','all' );


    /*************************************************************************/

    wp_enqueue_style('restaurante-restaurant-animate',get_template_directory_uri().'/css/animate.min.css','','all');



    wp_enqueue_style('restaurante-restaurant-blog',get_template_directory_uri().'/css/blog.css','','all');

    wp_enqueue_style('restaurante-restaurant-slicknav',get_template_directory_uri().'/css/slicknav.css','','all');


    wp_enqueue_style('restaurante-restaurant-magnific-popupcss',get_template_directory_uri().'/css/magnific-popup.css','','all');


    wp_enqueue_style('restaurante-restaurantstyle',get_stylesheet_uri(),'','','all');

}
add_action('wp_enqueue_scripts','restaurante_add_scripts');
function restaurante_add_scripts(){

    if ( is_singular() && get_option( 'thread_comments' ) )
        wp_enqueue_script( 'comment-reply' );


    wp_enqueue_script('restaurante-restaurant-bootstrap', get_template_directory_uri().'/js/bootstrap.min.js',array('jquery'),'',true);

    /******************************************************************
    *******************----- Scripts ----******************************/


    wp_enqueue_script('restaurante-restaurant-slicknav', get_template_directory_uri().'/js/jquery.slicknav.min.js', array('jquery'),'',true);


    wp_enqueue_script('restaurante-restaurant-matchHeight', get_template_directory_uri().'/js/jquery.matchHeight-min.js', array('jquery'),'',true);


    wp_enqueue_script('italian-restaurant-magnificpopupjs', get_template_directory_uri().'/js/jquery.magnific-popup.min.js', array('jquery'),'',true);



    wp_enqueue_script('restaurante-restauranttheme-js', get_template_directory_uri().'/js/init.js', array('jquery'),'',true);
}

add_action('wp_head', 'restaurante_restaurant_add_html5shiv');
function restaurante_restaurant_add_html5shiv () {
    echo '<!--[if lt IE 9]>';
    echo '<script src="'.get_template_directory_uri().'/js/html5shiv.min.js"></script>';
    echo '<![endif]-->';
}

/**== Admin Enqueue Script ==**/
function restaurante_restaurant_add_admin_scripts(){
    wp_enqueue_script('restaurante-restaurant-shortcodes', get_template_directory_uri()
        .'/js/ketchup-mce-button.js',
        array('jquery'),'',true);
}
add_action('admin_enqueue_scripts','restaurante_restaurant_add_admin_scripts');

/**====================== 
 Sidebars.
 ======================**/

/**== Add some sidebars ==**/
add_action('widgets_init','restaurante_register_sidebars');
function restaurante_register_sidebars(){

    /**== Right sidebar ==**/
    register_sidebar( array(
        'name'              => __( 'Sidebar', 'restaurante' ),
        'id'                => 'sidebar',
        'description'       => __( 'This is the main sidebar.It is in every page - post and
        in the blog page. However you can override this setting from within each post.',
            'restaurante' ),
        'before_widget'     => '<div id="%1$s" class="widget %2$s">',
        'after_widget'      => '</div>',
        'before_title'      => '<h3 class="widget-title">',
        'after_title'       => '</h3>'
    ));

    /**== Footer Sidebar 1 ==**/
    register_sidebar( array(
        'name'              => __( 'Footer Sidebar 1', 'restaurante' ),
        'id'                => 'footer_1_sidebar',
        'description'       => __( 'This is the sidebar in the footer, on the left', 'restaurante' ),
        'before_widget'     => '<aside id="%1$s" class="footerwidget %2$s">',
        'after_widget'      => '</aside>',
        'before_title'      => '<h3 class="widget-title">',
        'after_title'       => '</h3>'
    ));
    /**== Footer Sidebar 1 ==**/
    register_sidebar( array(
        'name'              => __( 'Footer Sidebar 2', 'restaurante' ),
        'id'                => 'footer_2_sidebar',
        'description'       => __( 'This is the sidebar in the footer, the second on the left', 'restaurante' ),
        'before_widget'     => '<aside id="%1$s" class="footerwidget %2$s">',
        'after_widget'      => '</aside>',
        'before_title'      => '<h3 class="widget-title">',
        'after_title'       => '</h3>'
    ));
}

/**=====================
 * Comments
 *=====================/
/**== Post Comment Callback ==**/
if(!function_exists('restaurante_restaurant_comment')):
    function restaurante_restaurant_comment($comment, $args, $depth) {
        $GLOBALS['comment'] = $comment;
        extract($args, EXTR_SKIP);

        if ( 'div' == $args['style'] ) {
            $tag = 'div';
            $add_below = 'comment';
        } else {
            $tag = 'li';
            $add_below = 'div-comment';
        }
        ?>
        <<?php echo $tag ?> <?php comment_class( empty( $args['has_children'] ) ? '' : 'parent' ) ?> id="comment-<?php comment_ID() ?>">
        <?php if ( 'div' != $args['style'] ) : ?>
            <div id="div-comment-<?php comment_ID() ?>" class="comment-body">
        <?php endif; ?>
        <div class="comment-author vcard">
            <?php
            if ( $args['avatar_size'] != 0 ) echo get_avatar( $comment, $args['avatar_size'] ); ?>
            <?php printf( __( '<cite class="fn">%s</cite> <span class="says">'._e('says:','restaurante').'</span>' ), get_comment_author_link() ); ?>

            <div class="comment-meta commentmetadata"><a href="<?php echo htmlspecialchars( get_comment_link( $comment->comment_ID ) ); ?>">
                    <?php
                    printf( __('%1$s at %2$s','restaurante'), get_comment_date(),  get_comment_time() ); ?></a><?php edit_comment_link( __( '(Edit)','restaurante' ), '  ', '' );
                ?>
            </div>
        </div>

        <?php if ( $comment->comment_approved == '0' ) : ?>
            <em class="comment-awaiting-moderation"><?php _e( 'Your comment is awaiting moderation.','restaurante' ); ?></em>
            <br />
        <?php endif; ?>
        <div class="beyond_comment_body">
            <?php comment_text(); ?>
        </div>
        <div class="reply">
            <?php comment_reply_link( array_merge( $args, array( 'add_below' => $add_below, 'depth' => $depth, 'max_depth' => $args['max_depth'] ) ) ); ?>
        </div>
        <?php if ( 'div' != $args['style'] ) : ?>
            </div>
        <?php endif; ?>
    <?php
    }
endif;


function restaurante_get_image_id($image_url) {
    global $wpdb;
    $attachment = $wpdb->get_col($wpdb->prepare("SELECT ID FROM $wpdb->posts WHERE guid='%s';", $image_url ));
    return $attachment[0];
}
function restaurante_upsell_notice(){
    // Enqueue the script
    wp_enqueue_script(
        'prefix-customizer-upsell',
        get_template_directory_uri() . '/js/upsell.js',
        array(), '1.0.0',
        true
    );

    wp_localize_script(
        'prefix-customizer-upsell',
        'prefixL10n',
        array(
            'prefixURL' => esc_url('http://ketchupthemes.com/restaurante-theme'),
            'prefixLabel' => __('Upgrade To Premium', 'restaurante') ,
            'paragraphText'=>__('Features Marked As Pro, Are Available In The Premium Version Only!!!','restaurante'),
            'premiumSpanTxt'=>__('- OR CHECK -','restaurante'),
            'premiumDemoTxt'=>__('Premium Demo','restaurante'),
            'premiumDemoUrl'=>esc_url('http://ketchupthemes.com/themes-demo/?theme=Restaurante')
        )
    );


}
add_action('customize_controls_enqueue_scripts', 'restaurante_upsell_notice');

/**=====================
 * Requires
 *==================== */
 require_once(get_template_directory().'/framework/ketchup-functions.php');
 require_once(get_template_directory().'/framework/libs/class-tgm-plugin-activation.php'); ?>
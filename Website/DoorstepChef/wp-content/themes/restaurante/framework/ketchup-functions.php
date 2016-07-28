<?php
/**===================================== **
 * This is the main framework functions.
 * These are also theme specific functions.
 *====================================== **/
/**
 * This file represents an example of the code that themes would use to register
 * the required plugins.
 *
 * It is expected that theme authors would copy and paste this code into their
 * functions.php file, and amend to suit.
 *
 * @see http://tgmpluginactivation.com/configuration/ for detailed documentation.
 *
 * @package    TGM-Plugin-Activation
 * @subpackage Example
 * @version    2.5.1
 * @author     Thomas Griffin, Gary Jones, Juliette Reinders Folmer
 * @copyright  Copyright (c) 2011, Thomas Griffin
 * @license    http://opensource.org/licenses/gpl-2.0.php GPL v2 or later
 * @link       https://github.com/TGMPA/TGM-Plugin-Activation
 */

/**
 * Include the TGM_Plugin_Activation class.
 */

add_action( 'tgmpa_register', 'restaurante_register_required_plugins' );
/**
 * Register the required plugins for this theme.
 *
 * In this example, we register five plugins:
 * - one included with the TGMPA library
 * - two from an external source, one from an arbitrary source, one from a GitHub repository
 * - two from the .org repo, where one demonstrates the use of the `is_callable` argument
 *
 * The variable passed to tgmpa_register_plugins() should be an array of plugin
 * arrays.
 *
 * This function is hooked into tgmpa_init, which is fired within the
 * TGM_Plugin_Activation class constructor.
 */
function restaurante_register_required_plugins() {
    /*
     * Array of plugin arrays. Required keys are name and slug.
     * If the source is NOT from the .org repo, then source is also required.
     */
    $plugins = array(

        array(
            'name' => 'Ketchup Restaurant Reservations',
            'slug' => 'ketchup-restaurant-reservations',
            'required' => false
        ),
        array(
            'name' => 'SEO Consultant',
            'slug' => 'seo-consultant',
            'required' => false
        ),
        array(
            'name'      => 'Bootstrap 3 Shortcodes',
            'slug'      => 'bootstrap-3-shortcodes',
            'required'  => false,
        ),
        array(
            'name'      => 'Ketchup Shortcodes',
            'slug'      => 'ketchup-shortcodes-pack',
            'required'  => false,
        )

    );
    
    $config = array(
        'id'           => 'restaurante',                 // Unique ID for hashing notices for multiple instances of TGMPA.
        'default_path' => '',                      // Default absolute path to bundled plugins.
        'menu'         => 'tgmpa-install-plugins', // Menu slug.
        'parent_slug'  => 'themes.php',            // Parent menu slug.
        'capability'   => 'edit_theme_options',    // Capability needed to view plugin install page, should be a capability associated with the parent menu used.
        'has_notices'  => true,                    // Show admin notices or not.
        'dismissable'  => true,                    // If false, a user cannot dismiss the nag message.
        'dismiss_msg'  => '',                      // If 'dismissable' is false, this message will be output at top of nag.
        'is_automatic' => false,                   // Automatically activate plugins after installation or not.
        'message'      => '',                      // Message to output right before the plugins table.

        
        'strings'      => array(
            'page_title'                      => __( 'Install Required Plugins','restaurante' ),
            'menu_title'                      => __( 'Install Plugins','restaurante' ),
            'installing'                      => __( 'Installing Plugin: %s','restaurante' ), // %s = plugin name.
            'oops'                            => __( 'Something went wrong with the plugin API.','restaurante' ),
            'notice_can_install_required'     => _n_noop(
                'This theme requires the following plugin: %1$s.',
                'This theme requires the following plugins: %1$s.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'notice_can_install_recommended'  => _n_noop(
                'This theme recommends the following plugin: %1$s.',
                'This theme recommends the following plugins: %1$s.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'notice_cannot_install'           => _n_noop(
                'Sorry, but you do not have the correct permissions to install the %1$s plugin.',
                'Sorry, but you do not have the correct permissions to install the %1$s plugins.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'notice_ask_to_update'            => _n_noop(
                'The following plugin needs to be updated to its latest version to ensure maximum compatibility with this theme: %1$s.',
                'The following plugins need to be updated to their latest version to ensure maximum compatibility with this theme: %1$s.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'notice_ask_to_update_maybe'      => _n_noop(
                'There is an update available for: %1$s.',
                'There are updates available for the following plugins: %1$s.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'notice_cannot_update'            => _n_noop(
                'Sorry, but you do not have the correct permissions to update the %1$s plugin.',
                'Sorry, but you do not have the correct permissions to update the %1$s plugins.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'notice_can_activate_required'    => _n_noop(
                'The following required plugin is currently inactive: %1$s.',
                'The following required plugins are currently inactive: %1$s.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'notice_can_activate_recommended' => _n_noop(
                'The following recommended plugin is currently inactive: %1$s.',
                'The following recommended plugins are currently inactive: %1$s.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'notice_cannot_activate'          => _n_noop(
                'Sorry, but you do not have the correct permissions to activate the %1$s plugin.',
                'Sorry, but you do not have the correct permissions to activate the %1$s plugins.',
               'restaurante'
            ), // %1$s = plugin name(s).
            'install_link'                    => _n_noop(
                'Begin installing plugin',
                'Begin installing plugins',
               'restaurante'
            ),
            'update_link' 					  => _n_noop(
                'Begin updating plugin',
                'Begin updating plugins',
               'restaurante'
            ),
            'activate_link'                   => _n_noop(
                'Begin activating plugin',
                'Begin activating plugins',
               'restaurante'
            ),
            'return'                          => __( 'Return to Required Plugins Installer','restaurante' ),
            'plugin_activated'                => __( 'Plugin activated successfully.','restaurante' ),
            'activated_successfully'          => __( 'The following plugin was activated successfully:','restaurante' ),
            'plugin_already_active'           => __( 'No action taken. Plugin %1$s was already active.','restaurante' ),  // %1$s = plugin name(s).
            'plugin_needs_higher_version'     => __( 'Plugin not activated. A higher version of %s is needed for this theme. Please update the plugin.','restaurante' ),  // %1$s = plugin name(s).
            'complete'                        => __( 'All plugins installed and activated successfully. %1$s','restaurante' ), // %s = dashboard link.
            'contact_admin'                   => __( 'Please contact the administrator of this site for help.', 'restaurante' ),

            'nag_type'                        => 'updated', // Determines admin notice type - can only be 'updated', 'update-nag' or 'error'.
        ),
       
    );

    tgmpa( $plugins, $config );
}


if (!function_exists('restaurante_body_schema')):

    function restaurante_body_schema()
    {

        if (is_page_template('page-templates/presentation-page.php')):

            return false;

        endif;


        if (is_page()):

            return "itemscope  itemtype='http://schema.org/WebPage'";

        endif;

        if (is_single()):


                return "itemscope  itemtype='http://schema.org/BlogPosting'";


        endif;
    }
endif;
/** CUSTOMIZR **/

function ketchup_customizer($wp_customize)
{

    require_once(FRAMEWORK_PATH . '/customizer-files/already-defined.php');
    require_once(FRAMEWORK_PATH . '/customizer-files/customizer-general.php');
}

add_action('customize_register', 'ketchup_customizer');

function restaurante_excerpt($length)
{
    return 25;
}

add_filter('excerpt_length', 'restaurante_excerpt', 999);

if (!function_exists('restaurante_mod')):
    function restaurante_mod($theme_mod)
    {

        $variable = get_theme_mod($theme_mod, '');

        return $variable;
    }
endif;

/**== Get the excerpt outside the loop ==**/
if (!function_exists('ketchup_excerpt_by_id')):
    function ketchup_excerpt_by_id($post_id,
                                   $excerpt_length = 35,
                                   $line_breaks = TRUE)
    {
        $the_post = get_post($post_id); //Gets post ID
        $the_excerpt = $the_post->post_excerpt ? $the_post->post_excerpt : $the_post->post_content; //Gets post_excerpt or post_content to be used as a basis for the excerpt
        $the_excerpt = apply_filters('the_excerpt', $the_excerpt);
        $the_excerpt = $line_breaks ? strip_tags(strip_shortcodes($the_excerpt), '<p><br>') : strip_tags(strip_shortcodes($the_excerpt)); //Strips tags and images
        $words = explode(' ', $the_excerpt, $excerpt_length + 1);
        if (count($words) > $excerpt_length) :
            array_pop($words);
            array_push($words, '…');
            $the_excerpt = implode(' ', $words);
            $the_excerpt = $line_breaks ? $the_excerpt . '</p>' : $the_excerpt;
        endif;
        $the_excerpt = trim($the_excerpt);
        return strip_tags(trim(html_entity_decode($the_excerpt)));
    }
endif;

if (version_compare($GLOBALS['wp_version'], '4.1', '<')) :
    function restaurante_wp_title($title,
                                  $sep)
    {
        if (is_feed()) {
            return $title;
        }
        global $page, $paged;

        $title .= get_bloginfo('name', 'display');

        $site_description = get_bloginfo('description', 'display');
        if ($site_description && (is_home() || is_front_page())) {
            $title .= " $sep $site_description";
        }
        if (($paged >= 2 || $page >= 2) && !is_404()) {
            $title .= " $sep " . sprintf(__('Page %s', 'restaurante'), max($paged, $page));
        }
        return $title;
    }

    add_filter('wp_title', 'restaurante_wp_title', 10, 2);

endif;

/*== Add 'nextpage' quicktag in WordPress Editor ==**/
if (!function_exists('restaurante_mce_editor')):
    function restaurante_mce_editor($mce_buttons)
    {
        $pos = array_search('wp_more', $mce_buttons, true);
        if ($pos !== false) {
            $tmp_buttons = array_slice($mce_buttons, 0, $pos + 1);
            $tmp_buttons[] = 'wp_page';
            $mce_buttons = array_merge($tmp_buttons, array_slice($mce_buttons, $pos + 1));
        }
        return $mce_buttons;
    }
endif;
add_filter('mce_buttons', 'restaurante_mce_editor');

/**=== Get logo or text for the logo container ===**/

function restaurante_get_logo2()
{
    $logo = get_theme_mod('restaurante_logo_upload', '');

    if (!empty($logo)):

        $logo_item = '<a href="' . esc_url(home_url('/')) . '"> <img id="kt-logo-image"
        src="' .
            $logo . '"
        alt="'
            . get_bloginfo('name') . '" />
            </a>';
    else:
        $logo_item = '<h1 class="h3"><a href="' . esc_url(home_url()) . '">' . get_bloginfo('name') . '</a></h1>';
        $logo_item .= '<h4 class="h5">' . get_bloginfo('description') . '</h4>';

    endif;

    return $logo_item;

}


/**=== Get the header image if is any ===**/
if (!function_exists('restaurante_get_header_image')):
    function restaurante_get_header_image()
    {
        $html = '';
        if (get_header_image() != ''):
            $html .= '<img class="img-responsive" src="' . get_header_image() . '" height="' . get_custom_header()->height . '" width="' . get_custom_header()->width . '" alt=""/>';
        else:
            return false;
        endif;
        return $html;
    }
endif;

/**== Numeric pagination ==**/
if (!function_exists('ketchup_restaurant_custom_pagination')):
    function ketchup_restaurant_custom_pagination($numpages = '',
                                                  $pagerange = '',
                                                  $paged = '')
    {

        if (empty($pagerange)) {
            $pagerange = 2;
        }
        global $paged;
        if (empty($paged)) {
            $paged = 1;
        }
        if ($numpages == '') {
            global $wp_query;
            $numpages = $wp_query->max_num_pages;
            if (!$numpages) {
                $numpages = 1;
            }
        }

        if (is_front_page()):
            $paged = 'page';
        else:
            $paged = 'paged';
        endif;

        $pagination_args = array(
            'base' => get_pagenum_link(1) . '%_%',
            'format' => 'page/%#%',
            'total' => $numpages,
            'current' => max(1, get_query_var($paged)),
            'show_all' => False,
            'end_size' => 1,
            'mid_size' => $pagerange,
            'prev_next' => True,
            'prev_text' => __('&laquo; PREVIOUS', 'restaurante'),
            'next_text' => __('&raquo; NEXT', 'restaurante'),
            'type' => 'plain',
            'add_args' => false,
            'add_fragment' => ''
        );

        $paginate_links = paginate_links($pagination_args);

        if ($paginate_links) {
            echo "<nav class='custom-pagination'>";
            //echo "<span class='page-numbers page-num'>".__('Page ','restaurante') . $paged .
            " of
" . $numpages . "</span> ";
            echo $paginate_links;
            echo "</nav>";
        }

    }
endif;

/**== Blog page functions ==**/

if (!function_exists('restaurante_get_posts_number')):
    function restaurante_get_posts_number()
    {
        $postnumber = absint((get_theme_mod
        ('restaurante_blog_posts_number',
            10)));

        if ($postnumber != '' || $postnumber != 0):

            return $postnumber;

        else:
            return 10;
        endif;
    }
endif;

/**=== Footer Functions ===**/

if (!function_exists('restaurante_has_active_footer')):
    function restaurante_has_active_footer()
    {
        $active_footer_sidebars = 0;
        $active_sidebars = array();

        for ($i = 1; $i < 3; $i++) {
            if (is_active_sidebar('footer_' . $i . '_sidebar')):
                $active_footer_sidebars++;
            endif;
        }

        if ($active_footer_sidebars == 0):
            return false;

        elseif ($active_footer_sidebars == 1):

            $active_sidebars['class'] = 'col-md-12';
            $active_sidebars['sidebars_count'] = 1;
            return $active_sidebars;

        elseif ($active_footer_sidebars == 2):

            $active_sidebars['class'] = 'col-md-6';
            $active_sidebars['sidebars_count'] = 2;
            return $active_sidebars;

        endif;

    }
endif;

/**
 * Meta description
 */

if (!function_exists('restaurante_meta_description')):
    function restaurante_meta_description()
    {
        $html = '';
        $pid = get_queried_object_id(); // What user sees.
        $hmd = get_theme_mod('restaurante_meta_description', ''); // Home meta description
        // only for the homepage

        $excerpt = ketchup_excerpt_by_id($pid, 35, true);


        if (is_front_page() && $hmd != ''):

            $html = '<meta name="description" content="' . $hmd . '">' . PHP_EOL;

        elseif (is_single() && trim($excerpt) != "") :   // single posts

            $html = '<meta name="description" content="' . $excerpt . '"/>' . PHP_EOL;


        elseif (is_page() && trim($excerpt) != ""):

            $html = '<meta name="description" content="' . $excerpt . '"/>' . PHP_EOL;

        endif;


        return $html;


    }
endif;

/**
 * Opengraph tags
 */

if (!function_exists('restaurante_og_tags')):

    function restaurante_og_tags()
    {

        $html = '';
        $og_tags = get_theme_mod('restaurante_disable_og', '0');

        $pid = get_queried_object_id(); // What user sees.

        $featured_image = wp_get_attachment_image_src(get_post_thumbnail_id($pid), '');

        $excerpt = ketchup_excerpt_by_id($pid, 35, true);
        $hmd = get_theme_mod('restaurante_meta_description', '');


        if (is_front_page() && $og_tags == '0' || is_page() && $og_tags == '0' || is_single
            () && $og_tags == '0'
        ):


            $html .= '<meta property="og:type" content="article"/>' . PHP_EOL;

            if ($featured_image[0] != ''):

                /** Featured Image og sizes **/
                $image_width = $featured_image[1];
                $image_height = $featured_image[2];

                $og_default_width = 470;

                $ogh = ($og_default_width * $image_height) / $image_width;
                $ogh_height = absint($ogh);

                $html .= '<meta property="og:image" content="' . $featured_image[0] . '">' . PHP_EOL;
                $html .= '<meta property="og:image:width" content="470"/>' . PHP_EOL;
                $html .= '<meta property="og:image:height" content="' . $ogh_height . '"/>' .
                    PHP_EOL;

            endif;

            /**Title **/

            if (is_front_page()):
                $html .= '<meta property="og:title" content="' . get_bloginfo('name') . '|'
                    . get_bloginfo('description') . '"/>'
                    . PHP_EOL;
            else:
                $html .= '<meta property="og:title" content="' . get_the_title() . '"/>'
                    . PHP_EOL;
            endif;


            if (is_front_page() && $hmd != ''):

                $html .= '<meta property="og:description" content="' . $hmd . '"/>' . PHP_EOL;
                $html .= '<meta property="og:url" content="' . get_the_permalink() . '"/>';

            else:
                $html .= '<meta property="og:description" content="' . $excerpt . '"/>' . PHP_EOL;
                $html .= '<meta property="og:url" content="' . get_the_permalink() . '"/>';

            endif;

            return $html;

        else:
            return false;
        endif;
    }

endif;

/***
 * Twitter Cards **/

function check_if_active_twitter()
{
    $twitter_acc = get_theme_mod('restaurante_add_twitter', '');

    if ($twitter_acc != ''):

        return $twitter_acc;
    else:
        return false;

    endif;
}
if (!function_exists('restaurante_twitter_cards')):

    function restaurante_twitter_cards()
    {
        $html = '';

        $check_twitter = check_if_active_twitter();

        if (is_front_page() || is_single() || is_page() && $check_twitter != false):

            $pid = get_queried_object_id(); // What the user sees

            if (get_post_format($pid) == 'video' || get_post_format($pid) == 'audio'):

                return false; // Is single, or page but it is a video / audio post format.


            else: // If is single/page/frontpage BUT not with audio/video post format

                $featured_image = wp_get_attachment_image_src(get_post_thumbnail_id($pid), '');


                $twitter_username = esc_attr(get_theme_mod('restaurante_add_twitter',''));

                if ($featured_image != ''):

                    $html = '<meta name="twitter:card" content="summary_large_image"/>' . PHP_EOL;

                    $html .= '<meta name="twitter:site" content="' . $twitter_username . '"/>' .
                        PHP_EOL;

                    $html .= '<meta name="twitter:creator" content="' . $twitter_username . '"/>'
                        . PHP_EOL;
                    $html .= '<meta name="twitter:image" content="' . $featured_image[0] . '"/>' . PHP_EOL;

                else:

                    $html .= '<meta name="twitter:card" content="summary">' . PHP_EOL;
                    $html .= '<meta name="twitter:site" content="' . $twitter_username . '"/>' . PHP_EOL;
                endif;

                /** Next Twitter Cards  **/

                if (is_front_page()):

                    $html .= '<meta name="twitter:title" content="' . get_bloginfo('name') .
                        '|'
                        . get_bloginfo('description') . '"/>'
                        . PHP_EOL;
                else:
                    $html .= '<meta name="twitter:title" content="' . get_the_title() . '"/>' . PHP_EOL;
                endif;

                //Check for Home Meta Description
                $hmd = get_theme_mod('restaurante_meta_description', '');
                $excerpt = ketchup_excerpt_by_id($pid, 35, true);

                if (is_front_page() && $hmd != ''):
                    $html .= '<meta name="twitter:description" content="' . $hmd . '"/>' . PHP_EOL;

                else:
                    $html .= '<meta name="twitter:description" content="' . $excerpt . '"/>' . PHP_EOL;
                endif;


                return $html;

            endif;


        endif;

    }

endif;

if(!function_exists('restaurante_has_header')):
    function restaurante_has_header(){
        $header = restaurante_get_header_image();
        if($header != false && is_front_page()):

            return 'single-kt-header-area';

        else:
            return 'kt-no-header-or-slider';

        endif;
    }
endif;
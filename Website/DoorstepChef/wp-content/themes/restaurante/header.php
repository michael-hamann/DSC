<!DOCTYPE html>
<html <?php language_attributes(); ?> prefix="og: http://ogp.me/ns#">
<head>
    <meta charset="<?php bloginfo('charset'); ?>"/>

    <?php $pid = get_queried_object_id(); ?>
    <?php echo restaurante_meta_description(); ?>
    <?php
    if (check_if_active_twitter() != false):
        echo restaurante_twitter_cards();
    endif;
    ?>
    <?php echo restaurante_og_tags(); ?>

    <link rel="profile" href="http://gmpg.org/xfn/11"/>
    <link rel="pingback" href="<?php bloginfo('pingback_url'); ?>"/>
    <?php if (get_theme_mod('restaurante_favicon_upload', '') != ''): ?>
        <link rel="icon"
              href="<?php echo esc_url(get_theme_mod('restaurante_favicon_upload', '')); ?> "
              type="image/x-icon">
    <?php endif; ?>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <?php wp_head(); ?>
</head>
<body <?php body_class(); ?> <?php echo restaurante_body_schema(); ?>>
<div class="site-loader"></div>
<header id="masthead" class="site-header clearfix">
<?php $id = strip_tags(restaurante_has_header()); ?>
    <div id="<?php echo strip_tags($id); ?>">
        <div class="container">
            <div class="row">
                <div class="col-md-4" id="kt-logo-area">
                    <div id="kt-logo-container">
                        <?php echo restaurante_get_logo2(); ?>
                    </div>
                </div>
                <div class="col-md-8">
                    <nav class="clearfix"
                         id="kt-main-navigation">

                        <?php
                        $restaurante_menu_args = array(
                            'theme_location' => 'main',
                            'fallback_cb' => 'restaurante_restaurant_fallback_menu',
                            'container' => false,
                            'menu_id' => 'kt-navigation',
                            'menu_class' => 'fixed-nav',
                            'echo' => true);
                        wp_nav_menu($restaurante_menu_args);
                        ?>
                    </nav>
                </div>
            </div>
        </div>
    </div>

    <!-- If has a slider or a header image the logo and the nav will be on top of them -->

    <?php if (restaurante_get_header_image() != false && is_front_page()): ?>

        <div id="restaurante_header_area">
            <?php echo restaurante_get_header_image(); ?>
        </div>

    <?php endif; ?>


</header>
<!-- Header ends -->
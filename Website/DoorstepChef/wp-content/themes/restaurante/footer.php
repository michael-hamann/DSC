<?php if (restaurante_has_active_footer() != false): ?>
    <footer id="kt-footer">
        <?php

        $restaurante_restaurant_footer_info = restaurante_has_active_footer();
        $restaurante_restaurant_footer_class = $restaurante_restaurant_footer_info['class'];
        $restaurante_restaurant_sidebars_count = $restaurante_restaurant_footer_info['sidebars_count'];
        ?>
        <div class="container">
            <div class="row">
                <div id="kt-footer-area">
                    <?php for ($i = 1; $i < $restaurante_restaurant_sidebars_count + 1; $i++): ?>

                        <div
                            class="<?php echo $restaurante_restaurant_footer_class; ?>
                            kt-footer-sidebar">
                            <?php if (!dynamic_sidebar('footer_' . $i . '_sidebar')): ?>
                                <div class="pre-widget">

                                </div>
                            <?php endif; ?>
                        </div>
                    <?php endfor; ?>
                </div>
            </div>
        </div>

    </footer>
<?php endif; // if which checks active footer sidebars ?>
<div id="kt-copyright">
    <div class="container">
        <div class="row">


            <div class="col-md-6">

                <p>
                    <a rel="nofollow" href="<?php echo esc_url(__(
                        'http://ketchupthemes.com/restaurante-theme/',
                        'restaurante')); ?>">
                        <?php printf(__('Ketchupthemes.com', 'restaurante'));
                        ?>
                    </a>,
                    <?php echo __('&copy;', 'restaurante') . date('Y'); ?>
                    <?php bloginfo('name'); ?>

                </p>
            </div>


            <!--- Footer Navigation -->
            <nav class="col-md-6">

                <?php if (has_nav_menu('footer')): ?>
                    <div class="col-md-12 clearfix">
                        <?php
                        $restaurante_menu_args = array(
                            'theme_location' => 'footer',
                            'container' => false,
                            'menu_id' => 'kt-footer-navigation');
                        wp_nav_menu($restaurante_menu_args);
                        ?>

                    </div>
                <?php endif; ?>

            </nav>
        </div>
    </div>
</div>
<?php wp_footer(); ?>
</body>
</html>
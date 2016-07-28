<?php
get_header(); ?>

    <header id="kt-page-header-simple" class="text-center">

        <h1 class="kt-post-entry-title kt-category-title">
            <?php if (is_year()):
                echo __('Archive for: ', 'restaurante') . get_the_time('Y');
                echo '<span class="screen-reader-text">' . __('Archive for: ', 'restaurante') . get_the_time('Y') . '</span>';
            elseif (is_month()):
                echo __('Archive for ', 'restaurante') . get_the_time('F, Y');
                echo '<span class="screen-reader-text">' . __('Archive for: ', 'restaurante') .
                    get_the_time('F Y') . '</span>';
            elseif (is_day()):
                echo __('Archive for ', 'restaurante') . get_the_time('F jS, Y');
                echo '<span class="screen-reader-text">' . __('Archive for: ', 'restaurante') . get_the_time('F jS, Y') . '</span>';
            endif;
            ?>

            <?php if ($paged > 1):
                echo
                    '<small>' . __('   Page:
', 'restaurante') . $paged
                    . '</small>'; endif; ?>
        </h1><!-- .entry-title -->
    </header>

    <div class="container">
        <div class="row">
            <div
                class="col-md-8">

                <section id="kt-primary">

                    <div id="kt-content" class="clearfix">

                        <?php get_template_part('framework/templates/content-archive'); ?>

                    </div>

                </section>
            </div>

            <div class="col-md-3 col-md-offset-1">
                <?php get_sidebar(); ?>
            </div>

        </div>
    </div>
    <!-- primary-page content ends here -->
<?php
get_footer();
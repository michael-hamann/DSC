<?php
get_header(); ?>

    <header id="kt-page-header-simple" class="text-center">

        <h1 class="kt-post-entry-title kt-category-title">
            <?php single_cat_title(_e('You are viewing posts in: ', 'restaurante')); ?>
            <span class="screen-reader-text"> <?php single_cat_title(_e('You are viewing posts in:
        ', 'restaurante')); ?></span>
            <?php if ($paged > 1):
                echo
                    '<small>' . __('   Page:
','restaurante') . $paged
                    . '</small>';

            endif; ?>
        </h1><!-- .entry-title -->

    </header>

    <div class="container">
        <div class="row">
            <div class="col-md-8">

                <section id="kt-primary">

                    <div id="kt-content" class="clearfix">

                        <?php get_template_part('framework/templates/content-category'); ?>

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
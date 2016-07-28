<?php
get_header(); ?>

    <header id="kt-page-header-simple"   class="text-center">

    <h1 class="kt-post-entry-title kt-category-title">
        <?php single_tag_title(_e('You are viewing posts tagged with: ', 'restaurante')); ?>
        <span class="screen-reader-text">   <?php single_tag_title(_e('You are viewing posts tagged with: ', 'restaurante')); ?></span>
        <?php if ($paged > 1):
            echo
                '<small>' . __('   Page:
','restaurante') . $paged
                . '</small>'; endif; ?>
    </h1><!-- .entry-title -->
    </header>
    <div class="container">
        <div class="row">
            <div class="col-md-4">

                <section id="kt-primary">

                    <div id="kt-content" class="clearfix">

                        <?php get_template_part('framework/templates/content-tag'); ?>

                    </div>

                </section>
            </div>
                <div class="col-md-3 col-md-offset-1">
                    <aside id="kt-sidebar">
                        <?php if (!dynamic_sidebar('tag-sidebar')): ?>
                            <div class="pre-widget">

                            </div>
                        <?php endif; ?>
                    </aside>
                </div>

        </div>
    </div>
    <!-- primary-page content ends here -->
<?php
get_footer();
<?php
get_header();

?>

    <div class="container">
        <div class="row">


            <div class="col-md-8">

                <main id="kt-primary">

                    <div id="kt-content" class="clearfix">
                        <?php get_template_part('framework/templates/content-post-template'); ?>
                    </div>

                </main>

            </div>

            <div class="col-md-3 col-md-offset-1 ">
                <?php get_sidebar(); ?>
            </div>

        </div>

    </div>
    <!-- primary-page content ends here -->
<?php get_footer();
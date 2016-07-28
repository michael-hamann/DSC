<?php
get_header(); ?>
<div class="row">
    <div class="col-md-12">
        <main id="kt-primary">

            <div id="kt-content" class="clearfix">
                <div id="error-404" class="text-center">
                    <i id="error-icon" class="fa fa-thumbs-down fa-5x"></i>
                    <h4><?php echo __('Well, this page does not exist. Maybe search
                    something else?','restaurante'); ?></h4>
                    <?php echo get_search_form(); ?>
                </div>

            </div>
        </main>
    </div>
</div>
<!-- primary-page content ends here -->
<?php get_footer();

<?php
/*
 * Template Name: Presentation
 */

get_header();
?>
    <main>
        <div id="kt-content" class="clearfix">
           <?php
           if(have_posts()):while(have_posts()):the_post();

               the_content();

            endwhile; endif;
           ?>
        </div>
    </main>
    <!-- primary-page content ends here -->
<?php get_footer();

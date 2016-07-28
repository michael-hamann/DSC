<section id="post-<?php the_ID(); ?>" <?php post_class(); ?>>

    <main class="kt-entry-content kt-category-list clearfix">

        <?php if (have_posts()):while (have_posts()):the_post(); ?>

            <div class="kt-blog-post matchHeight">

                <h2 class="h1">
                    <a class="kt-article-title" href="<?php
                    the_permalink(); ?>"
                       title="<?php the_title_attribute(); ?>"> <?php
                        the_title();
                        ?>
                        <span class="screen-reader-text">
                            <?php _e('Read an article about ',
                                'restaurante').' '.the_title();?></span>
                    </a>
                </h2>

                <div class=" blog-list-meta kt-post-meta-body clearfix">
                    <p class="meta-comments">

                        <?php echo get_the_date(get_option('date_format')); ?>

                        | <?php _e('By ', 'restaurante') . the_author(); ?> |
                        <a href="<?php
                        the_permalink(); ?>#comments"><?php
                            echo
                            comments_number(__('No comments',
                                'restaurante'), __('1 Comment', 'restaurante'), __('% Comments',
                                'restaurante')); ?></a></p>

                </div>


                <?php if (has_post_thumbnail()): ?>

                    <div class="kt-blog-post-image">

                        <a href="<?php the_permalink(); ?>"
                           title="<?php the_title_attribute(); ?>">
                            <figure>
                                <?php
                                the_post_thumbnail('restaurante-restaurant-page-image-'.$class,
                                    array('class' => 'img-responsive kt-featured-img', 'alt' => get_the_title()));
                                ?>
                            </figure>
                        </a>
                    </div>
                <?php endif; ?>

                <div class="kt-blog-list-body">

                    <section class="blog-list-content">
                        <?php the_excerpt(); ?>
                    </section>

                    <section class="blog-list-categories">
                        <?php echo get_the_category_list(' ', ''); ?>
                    </section>
                    <a class="blog-list-read-more" href="<?php the_permalink(); ?>"
                       title="<?php
                       the_title_attribute();
                       ?>"><?php _e('READ MORE', 'restaurante'); ?></a>


                </div>

            </div>

            <!--kt-blog-post ends here -->
        <?php endwhile;
        else: echo 'No posts';
        endif;

        ?>
    </main>


    <div class="clearfix"></div>
    <div id="kt-pagination">

        <?php
        if (function_exists('ketchup_restaurant_custom_pagination')) {
            ketchup_restaurant_custom_pagination("", "", $paged);
        }
        ?>

    </div>

</section>

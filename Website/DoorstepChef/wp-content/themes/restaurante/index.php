<?php
get_header();
?>
    <div class="container">
        <div class="row">
            <div class="col-md-8">

                <main id="kt-primary">

                    <div id="kt-content" class="clearfix">

                        <section id="post-<?php the_ID(); ?>" <?php post_class(); ?>>

                            <section class="kt-entry-content kt-blog-list clearfix">

                                <?php if (have_posts()):while (have_posts()):the_post(); ?>

                                    <article itemscope itemtype="http://schema.org/BlogPosting"
                                             class="kt-blog-post">

                                        <h2 class="h1" itemprop="headline">
                                            <a class="kt-article-title" href="<?php
                                            the_permalink(); ?>"
                                               title="<?php the_title_attribute(); ?>"> <?php
                                                the_title();
                                                ?>
                                            </a>
                                        </h2>

                                        <div class=" blog-list-meta kt-post-meta-body clearfix">
                                            <p class="meta-comments">

                                                <time
                                                    dateTime="<?php echo get_the_date('Y-m-d\TH:i'); ?>">
                                                        <span itemprop="datePublished"><?php
                                                            echo get_the_date(get_option('date_format')); ?></span>
                                                </time>

                                                | <?php _e('By ', 'restaurante') . the_author(); ?>
                                                |
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
                                                        the_post_thumbnail('restaurante-restaurant-page-image-' . $class,
                                                            array('itemprop' => 'image', 'class' => 'img-responsive
                                        kt-featured-img', 'alt' => get_the_title()));
                                                        ?>
                                                    </figure>
                                                </a>
                                            </div>
                                        <?php endif; ?>

                                        <div class="kt-blog-list-body">

                                            <section
                                                class="blog-list-content" itemprop="description">
                                                <?php the_excerpt(); ?>
                                            </section>

                                            <section class="blog-list-categories">
                                                <?php echo get_the_category_list(' ', ''); ?>
                                            </section>
                                            <a class="blog-list-read-more"
                                               href="<?php the_permalink(); ?>"
                                               title="<?php
                                               the_title_attribute();
                                               ?>"><?php _e('READ MORE', 'restaurante'); ?></a>


                                        </div>

                                    </article>

                                    <!--kt-blog-post ends here -->
                                <?php endwhile;
                                else: echo 'No posts';
                                endif;
                                wp_reset_postdata();
                                ?>
                            </section>


                            <div class="clearfix"></div>
                            <div id="kt-pagination">

                                <?php
                                if (function_exists('ketchup_restaurant_custom_pagination')) {
                                    ketchup_restaurant_custom_pagination("", "", $paged);
                                }
                                ?>

                            </div>
                            <footer class="entry-meta-footer clearfix">

                                <!-- Comments -->
                                <?php if (comments_open()): ?>
                                    <div class="kt-comments-area">
                                        <?php comments_template('', true); ?>
                                    </div>
                                <?php endif; ?>

                            </footer>

                        </section>

                    </div>

                </main>
            </div>

            <div class="col-md-3 col-md-offset-1">
                <?php get_sidebar(); ?>
            </div>
        </div>
        <!-- primary-page content ends here -->
    </div>

<?php
get_footer();
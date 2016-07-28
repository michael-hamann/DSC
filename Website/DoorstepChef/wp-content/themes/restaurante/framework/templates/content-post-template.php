<?php if (have_posts()):while (have_posts()):the_post(); ?>

    <article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>

        <header class="entry-header clearfix">

            <h1 class="entry-title" itemprop="headline"><?php the_title(); ?></h1>

            <div class="kt-post-meta entry-meta clearfix">

                <div class="kt-post-meta-body">


                    <?php _e('by ', 'restaurante') . _e('<span itemprop="author">',
                        'restaurante') . the_author()
                    . _e('</span>', 'restaurante'); ?> |

                    <time datetime="<?php echo get_the_date('Y-m-d\TH:i'); ?>">
        <span
            itemprop="datePublished"> <?php
            echo
            get_the_date
            (get_option
            ('date_format')); ?></span>
                    </time>

                    | <?php echo comments_number(__('No comments',
                        'restaurante'), __('1 Comment', 'restaurante'), __('% Comments',
                        'restaurante')); ?> |

                    <?php echo get_the_category_list(',', ''); ?>


                </div>

            </div>


        </header>

        <section itemprop="articleBody" class="kt-entry-content clearfix">

            <?php
            the_content();
            ?>
        </section>

        <?php if (has_tag()): ?>
            <i class="fa fa-check"></i> <?php echo get_the_tag_list('', ','); ?>
        <?php endif; ?>
        <div class="clearfix"></div>

        <?php wp_link_pages(array('before' => '<div class="page-link"><span>' . __('Pages:',
                'restaurante') . '</span>', 'after' => '</div>')); ?>
        <footer class="entry-meta-footer clearfix">

            <!-- Comments -->

            <div class="kt-comments-area">
                <?php comments_template('', true); ?>
            </div>


        </footer>
    </article>
<?php endwhile; endif; ?>
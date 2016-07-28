<?php if (have_posts()):while (have_posts()):the_post(); ?>

    <article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>


        <header class="entry-header clearfix">


                <h1 class="entry-title"  itemprop="name"><?php the_title(); ?></h1>



            <?php if (has_post_thumbnail()): ?>
                <figure>
                    <?php
                    the_post_thumbnail('restaurante-restaurant-page-image-' .
                        restaurante_show_columns_class(),
                        array
                        ('itemprop'=>'image','class' => 'img-responsive kt-featured-img', 'alt'
                    =>
                    get_the_title
                        ()));
                    ?>
                </figure>
            <?php endif; ?>

        </header>

        <section class="kt-entry-content clearfix">

            <?php
            the_content();
            ?>
        </section>

        <div class="clearfix"></div>

        <?php wp_link_pages(array('before' => '<div class="page-link"><span>' . __('Pages:',
                'restaurante') . '</span>', 'after' => '</div>')); ?>

        <div class="clearfix"></div>

        <footer class="entry-meta-footer clearfix">

            <!-- Comments -->

            <div class="kt-comments-area">
                <?php comments_template('', true); ?>
            </div>


        </footer>
    </article>
<?php endwhile; endif; ?>
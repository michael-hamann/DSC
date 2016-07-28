<?php
/**
 * The template for displaying all pages.
 *
 * This is the template that displays all pages by default.
 * Please note that this is the WordPress construct of pages
 * and that other 'pages' on your WordPress site will use a
 * different template.
 *
 * @package brasserie
 * @since brasserie 1.0
 */

get_header(); ?>
<header class="entry-header">
    <div class="title-container">




      <h1 class="page-title"><?php
            if ( is_woocommerce_activated() && is_product_category() ){
              single_term_title();
            } elseif( is_woocommerce_activated() && is_shop() ){
              woocommerce_page_title();
            } else{
              the_title();
            }
        ?></h1>
    </div>
</header><!-- .entry-header -->
	<div id="primary_wrap">
		<div id="primary" class="content-area">
			<div id="content" class="site-content" role="main">
				<?php if ( have_posts() ) : ?>
				<?php woocommerce_breadcrumb(); ?>
					<?php
						/**
						 * woocommerce_before_shop_loop hook
						 *
						 * @hooked woocommerce_result_count - 20
						 * @hooked woocommerce_catalog_ordering - 30
						 */
						do_action( 'woocommerce_before_shop_loop' );
					?>

					<?php woocommerce_product_loop_start(); ?>

						<?php woocommerce_product_subcategories(); ?>

						<?php while ( have_posts() ) : the_post(); ?>

							<?php
								if( is_product() ){
									wc_get_template_part( 'content', 'single-product' );
								}else{
									wc_get_template_part( 'content', 'product' );
								}
							?>

						<?php endwhile; // end of the loop. ?>

					<?php woocommerce_product_loop_end(); ?>

					<?php
						/**
						 * woocommerce_after_shop_loop hook
						 *
						 * @hooked woocommerce_pagination - 10
						 */
						do_action( 'woocommerce_after_shop_loop' );
					?>

				<?php elseif ( ! woocommerce_product_subcategories( array( 'before' => woocommerce_product_loop_start( false ), 'after' => woocommerce_product_loop_end( false ) ) ) ) : ?>

					<?php wc_get_template( 'loop/no-products-found.php' ); ?>

				<?php endif; ?>

				</div>



			</div>

			<?php get_sidebar('woocommerce'); ?>
	</div>
<?php get_footer(); ?>

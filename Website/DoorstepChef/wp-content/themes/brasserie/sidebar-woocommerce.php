<?php
/**
 * The Woocomerce Sidebar containing the main widget areas.
 *
 * @package brasserie
 * @since brasserie 1.0
 */

 $isWoocommerce = false;
 if( ! is_checkout() || ! is_cart() ){
 		$isWoocommerce = true;
 }

?>
<section id="secondary" class="widget-area" role="complementary">
		<?php if( ! $isWoocommerce ): ?>
			<h1 class="page-title"><?php woocommerce_page_title(); ?></h1>
		<?php endif; ?>
		<?php do_action( 'before_sidebar' ); ?>
		<?php if ( ! dynamic_sidebar( 'woocommerce_sidebar' ) ) : ?>

			<aside id="search" class="widget widget_search">
				<?php get_search_form(); ?>
			</aside>

			<aside id="archives" class="widget">
				<h1 class="widget-title"><?php _e( 'Archives', 'brasserie' ); ?></h1>
				<ul>
					<?php wp_get_archives( array( 'type' => 'monthly' ) ); ?>
				</ul>
			</aside>

			<aside id="meta" class="widget">
				<h1 class="widget-title"><?php _e( 'Meta', 'brasserie' ); ?></h1>
				<ul>
					<?php wp_register(); ?>
					<li><?php wp_loginout(); ?></li>
					<?php wp_meta(); ?>
				</ul>
			</aside>

		<?php endif; // end sidebar widget area ?>
</section><!-- #secondary .widget-area -->

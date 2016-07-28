<?php
/**
 * The template for displaying 404 pages (Not Found).
 *
 * @package brasserie
 * @since brasserie 1.0
 */

get_header(); ?>
	<header class="entry-header">
		<div class="title-container">
			<h1 class="page-title"><?php _e( 'Oops! That page can&rsquo;t be found.', 'brasserie' ); ?></h1>
		</div>
	</header><!-- .entry-header -->
	<div id="primary_wrap">
		<div id="primary" class="content-area">
			<div id="content" class="site-content" role="main">
				<article id="post-0" class="post error404 not-found">
					<div class="entry-content">
						<p><?php _e( 'It looks like nothing was found at this location. Maybe try navigating to another page or a search?', 'brasserie' ); ?></p>
						<?php get_search_form(); ?>
					</div><!-- .entry-content -->
				</article><!-- #post-0 .post .error404 .not-found -->
			
			</div><!-- #content .site-content -->
		</div><!-- #primary .content-area -->
		<?php get_sidebar(); ?>
	</div><!-- /#primary_wrap -->
	
<?php get_footer(); ?>
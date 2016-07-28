<?php
/**
 * The Template for displaying all single posts.
 *
 * @package brasserie
 * @since brasserie 1.0
 */

get_header(); ?>
	<header class="entry-header">
    <div class="title-container">
		<h1 class="page-title"><?php the_title(); ?></h1>
        </div>
		</header><!-- .entry-header -->
		<div id="primary_wrap">
		<div id="primary" class="content-area">
			<div id="content" class="site-content" role="main">

			<?php while ( have_posts() ) : the_post(); ?>

				<?php get_template_part( 'content', 'single' ); ?>

				<?php brasserie_content_nav( 'nav-below' ); ?>

				<?php
					// If comments are open or we have at least one comment, load up the comment template
					if ( comments_open() || '0' != get_comments_number() )
						comments_template( '', true );
				?>

			<?php endwhile; // end of the loop. ?>

			</div><!-- #content .site-content -->
		</div><!-- #primary .content-area -->

<?php get_sidebar(); ?>
	</div>
<?php get_footer(); ?>
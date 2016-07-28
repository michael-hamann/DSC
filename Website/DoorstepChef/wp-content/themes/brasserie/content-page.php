<?php
/**
 * The template used for displaying page content in page.php
 *
 * @package brasserie
 * @since brasserie 1.0
 */
?>
	<div class="entry-content">
		<?php the_content(); ?>
		<?php wp_link_pages( array( 'before' => '<div class="page-links">' . __( 'Pages:', 'brasserie' ), 'after' => '</div>' ) ); ?>
        <footer class="entry-meta">
		<?php edit_post_link( __( 'Edit', 'brasserie' ), '<span class="edit-link"><i class="fa fa-pencil"></i>', '</span>' ); ?>
        </footer><!-- .entry-meta -->
	</div><!-- .entry-content -->

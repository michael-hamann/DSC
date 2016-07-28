<?php
/**
 * @package brasserie
 * @since brasserie 1.0
 */
?>

<article id="post-<?php the_ID(); ?>" <?php post_class(); ?>>
	 <div class="blog-image">
				<?php
			if ( has_post_thumbnail() ) {
    $image_src = wp_get_attachment_image_src( get_post_thumbnail_id(),'featured' );
     echo '<img alt="post" class="imagerct" src="' . $image_src[0] . '">';
}
  			?>
    </div>
	<header class="entry-header">
		<h1 class="entry-title"><a href="<?php the_permalink(); ?>" title="<?php echo esc_attr( sprintf( __( 'Permalink to %s', 'brasserie' ), the_title_attribute( 'echo=0' ) ) ); ?>" rel="bookmark"><?php the_title(); ?></a></h1>

		<?php if ( 'post' == get_post_type() ) : ?>
		<div class="entry-meta">
			<?php brasserie_posted_on(); ?>
         <?php if ( 'post' == get_post_type() ) : // Hide category and tag text for pages on Search ?>
			<?php
				/* translators: used between list items, there is a space after the comma */
				$categories_list = get_the_category_list( __( ', ', 'brasserie' ) );
				if ( $categories_list && brasserie_categorized_blog() ) :
			?>
			<span class="cat-links"><span class="fa fa-folder-open"></span>
				<?php printf( __( ' In %1$s', 'brasserie' ), $categories_list ); ?>
			</span>
			<?php endif; // End if categories ?>

			<?php
				/* translators: used between list items, there is a space after the comma */
				$tags_list = get_the_tag_list( '', __( ', ', 'brasserie' ) );
				if ( $tags_list ) :
			?>
			
			<span class="tag-links"><span class="fa fa-tags"></span>
				<?php printf( __( 'Tagged %1$s', 'brasserie' ), $tags_list ); ?>
			</span>
			<?php endif; // End if $tags_list ?>
		<?php endif; // End if 'post' == get_post_type() ?>

		<?php if ( ! post_password_required() && ( comments_open() || '0' != get_comments_number() ) ) : ?>
		
		<span class="comments-link"><span class="fa fa-comment"></span> <?php comments_popup_link( __( ' Leave a comment', 'brasserie' ), __( '1 Comment', 'brasserie' ), __( '% Comments', 'brasserie' ) ); ?></span>
		<?php endif; ?>

		<?php edit_post_link( __( 'Edit', 'brasserie' ), '<span class="edit-link"><i class="fa fa-pencil"></i>', '</span>' ); ?>
		</div><!-- .entry-meta -->
		<?php endif; ?>
	</header><!-- .entry-header -->

	<?php if ( is_search() ) : // Only display Excerpts for Search ?>
	<div class="entry-summary">
		<?php the_excerpt(); ?>
	</div><!-- .entry-summary -->
	<?php else : ?>
	<div class="entry-content">
		<?php the_content( __( 'Read More', 'brasserie' ) ); ?>
		<?php wp_link_pages( array( 'before' => '<div class="page-links">' . __( 'Pages:', 'brasserie' ), 'after' => '</div>' ) ); ?>
	</div><!-- .entry-content -->
	<?php endif; ?>

</article><!-- #post-<?php the_ID(); ?> -->

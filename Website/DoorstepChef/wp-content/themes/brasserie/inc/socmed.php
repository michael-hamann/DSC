<?php

/**
 * This template generates links to social media icons once set in the theme options.  
 *
 * @package brasserie
 */
?>
<ul class="social-media">
	<?php if ( get_theme_mod( 'twitter' ) ) : ?>
		<li><a class="hastip" title="Twitter" href="<?php echo esc_url( get_theme_mod( 'twitter' ) ); ?>" target="_blank"><i class="fa fa-twitter"></i></a></li>
	<?php endif; ?>
	
	<?php if ( get_theme_mod( 'facebook' ) ) : ?>
		<li><a class="hastip" title="Facebook" href="<?php echo esc_url( get_theme_mod( 'facebook' ) ); ?>" target="_blank"><i class="fa fa-facebook-square"></i></a></li>
	<?php endif; ?>
	
	<?php if ( get_theme_mod( 'pinterest' ) ) : ?>
		<li><a class="hastip" title="Pinterest" href="<?php echo esc_url( get_theme_mod( 'pinterest' ) ); ?>" target="_blank"><i class="fa fa-pinterest"></i></a></li>
	<?php endif; ?>
	
	<?php if ( get_theme_mod( 'linkedin' ) ) : ?>
		<li><a class="hastip" title="LinkedIn" href="<?php echo esc_url( get_theme_mod( 'linkedin' ) ); ?>" target="_blank"><i class="fa fa-linkedin"></i></a></li>
	<?php endif; ?>
	
	<?php if ( get_theme_mod( 'googleplus' ) ) : ?>
		<li><a class="hastip" title="Google+" href="<?php echo esc_url( get_theme_mod( 'googleplus' ) ); ?>" target="_blank"><i class="fa fa-google-plus-square"></i></a></li>
	<?php endif; ?>
	
	<?php if ( get_theme_mod( 'flickr' ) ) : ?>
		<li><a class="hastip" title="Flickr" href="<?php echo esc_url( get_theme_mod( 'flickr' ) ); ?>" target="_blank"><i class="fa fa-flickr"></i></a></li>
	<?php endif; ?>
	
	<?php if ( get_theme_mod( 'youtube' ) ) : ?>
		<li><a class="hastip" title="Youtube" href="<?php echo esc_url( get_theme_mod( 'youtube' ) ); ?>" target="_blank"><i class="fa fa-youtube-play"></i></a></li>
	<?php endif; ?>
	
	<?php if ( get_theme_mod( 'vimeo' ) ) : ?>
		<li><a class="hastip" title="Vimeo" href="<?php echo esc_url( get_theme_mod( 'vimeo' ) ); ?>" target="_blank"><i class="fa fa-vimeo-square"></i></a></li>
	<?php endif; ?>
	
	<?php if ( get_theme_mod( 'dribble' ) ) : ?>
		<li><a class="hastip" title="Dribbble" href="<?php echo esc_url( get_theme_mod( 'dribble' ) ); ?>" target="_blank"><i class="fa fa-dribbble"></i></a></li>
	<?php endif; ?>	
	
	<?php if ( get_theme_mod( 'tumblr' ) ) : ?>
		<li><a class="hastip" title="Tumblr" href="<?php echo esc_url( get_theme_mod( 'tumblr' ) ); ?>" target="_blank"><i class="fa fa-tumblr-square"></i></a></li>
	<?php endif; ?>

	<?php if ( get_theme_mod( 'github' ) ) : ?>
		<li><a class="hastip" title="Github" href="<?php echo esc_url( get_theme_mod( 'github' ) ); ?>" target="_blank"><i class="fa fa-github"></i></a></li>
	<?php endif; ?>	
    
    <?php if ( get_theme_mod( 'instagram' ) ) : ?>
		<li><a class="hastip" title="Instagram" href="<?php echo esc_url( get_theme_mod( 'instagram' ) ); ?>" target="_blank"><i class="fa fa-instagram"></i></a></li>
	<?php endif; ?>
    
    <?php if ( get_theme_mod( 'xing' ) ) : ?>
		<li><a class="hastip" title="Xing" href="<?php echo esc_url( get_theme_mod( 'xing' ) ); ?>" target="_blank"><i class="fa fa-xing"></i></a></li>
	<?php endif; ?>

</ul><!-- #social-icons-->
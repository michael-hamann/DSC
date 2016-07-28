<?php
/*
 * Template Name: Custom Home Page
 * Description: A home page with slider and widgets.
 *
 * @package brasserie
 * @since brasserie 1.0
 */
get_header();?>
     
	
    <?php 
	    // Only display if this section is marked for display in customizer	
	    if(!get_theme_mod('hide_slider_section')):
			
			 // if slider options changed
			 if(get_theme_mod( 'brasserie_slider' )) :
			 	if(get_theme_mod( 'brasserie_slider' ) == 'flexslider'):
			 		 // include felxslider html
			 		 get_template_part( 'content', 'flexslider');
			 	endif;
			 		
			 endif;
			 
		endif;
    ?>
<?php  	
	// Only display if this section is marked for display in customizer		
	if(!get_theme_mod('hide_feature_text_boxes')): 
	
		$list_featureboxes = array(
			'one' 		=> __('slideInLeft', 'brasserie'),
			'two'			=> __('fadeInUp', 'brasserie'),
			'three'			=> __('slideInRight', 'brasserie'),
		);
?>
	<?php  	// Only display if this section is marked for display in customizer.
		if(!get_theme_mod('hide_promo_bar')): 
?>    
	<div id="featuretext_container">
		<div class="featuretext_top">
			<h3><?php echo esc_html(get_theme_mod( 'featured_textbox' ) ); ?></h3>
			<?php if ( get_theme_mod( 'featured_button_url' ) ) : ?>
				<div class="featuretext_button animated" data-fx="slideInRight">
					<a href="<?php echo esc_url( get_theme_mod( 'featured_button_url' ) ); ?>" ><?php if(get_theme_mod('featured_btn_textbox')): echo(get_theme_mod('featured_btn_textbox')); else: echo __('Find out more', 'brasserie'); endif;?></a>
				</div>  
			<?php endif; ?>
		</div> 
	</div>
<?php endif; ?>
    
	<div class="featuretext_middle">
		<div class="section group">
			<?php foreach($list_featureboxes as $key => $value){ ?>
				<div class="col span_1_of_3  box-<?php echo $key; ?> animated" data-fx="<?php echo($value); ?>">         
					<div class="featuretext">
						<?php if ( get_theme_mod( 'header-'.$key.'-file-upload' ) ) : ?>
							<a href="<?php echo esc_url( get_theme_mod( 'header_'.$key.'_url' ) ); ?>"><img src="<?php echo esc_url( get_theme_mod( 'header-'.$key.'-file-upload' ) ); ?>"  alt="<?php echo esc_attr('feature '.$key)?>"></a>
						<?php else : ?>
							<?php echo '<p>' . __('Insert Image', 'brasserie') . '</p>'; ?>
						<?php endif; ?>
						<h2><a href="<?php echo esc_url( get_theme_mod( 'header_'.$key.'_url' ) ); ?>"><?php echo esc_html(get_theme_mod( 'featured_textbox_header_'.$key ) ); ?></a></h2>
						<p><?php echo esc_html(get_theme_mod( 'featured_textbox_text_'.$key ) ); ?></p>
					</div>
				</div><!-- /.col span_1_of_3 animated -->
			<?php } ?>		
		</div><!-- /.section group -->
	</div><!-- /.featuretext_middle -->
<?php endif; ?>
        
<?php  	// Only display if this section is marked for display in customizer.
		if(!get_theme_mod('hide_recent_posts')): 
?>      
	
			<div class="section_thumbnails group">
				<?php echo '<h3>' . __('From the blog', 'brasserie') . '</h3>'; ?>
				<?php $the_query = new WP_Query(array(
					'showposts' => 4,
					'post__not_in' => get_option("sticky_posts"),
					));
				?>
				<?php while ($the_query -> have_posts()) : $the_query -> the_post(); ?>
					<div class="col span_1_of_4">
						<article class="recent animated" data-fx="fadeInUp">
							<a href="<?php the_permalink(); ?>">
								<?php
									if ( has_post_thumbnail() ) {
										$image_src = wp_get_attachment_image_src( get_post_thumbnail_id(),'recent' );
										echo '<img alt="post" class="imagerct_home" src="' . $image_src[0] . '">';
									}
								?> 
								<div class="recent_title">
									<h2><?php the_title(); ?></h2>
                                    <p><?php echo brasserie_get_recentposts_excerpt(); ?>...</p>
								</div>
							</a>
						</article>
					</div>	
				<?php endwhile; ?>
			</div><!-- END section_thumbnails -->
		
<?php endif; ?>
<?php  	
	// Only display if this section is marked for display in customizer.
	if(!get_theme_mod('hide_partner_logos')):
?>   
	<div class="section_clients group">
		<div class="client animated" data-fx="fadeInUp">
		
			<h3> <?php if(get_theme_mod('brasserie_partner_txt')): 
							echo(get_theme_mod('brasserie_partner_txt')); 
						else: 
							echo __('Todays Specials', 'brasserie'); 
						endif;?>
			</h3>
			<div class="col span_1_of_4">
				<div class="client_recent">
					<?php if ( get_theme_mod( 'brasserie_one_logo_upload' ) ) : ?>
						<a href="<?php echo esc_url( get_theme_mod( 'brasserie_one_company_url' ) ); ?>"><img src="<?php echo esc_url( get_theme_mod( 'brasserie_one_logo_upload' ) ); ?>"  alt="<?php echo __('special one', 'brasserie')?>"></a>
					<?php else : ?>
						<?php echo '<h4>' . __('Insert Image', 'brasserie') . '</h4>'; ?>
					<?php endif; ?>
				</div>
			</div>
			<div class="col span_1_of_4">
				<div class="client_recent">
					<?php if ( get_theme_mod( 'brasserie_two_logo_upload' ) ) : ?>
						<a href="<?php echo esc_url( get_theme_mod( 'brasserie_two_company_url' ) ); ?>"><img src="<?php echo esc_url( get_theme_mod( 'brasserie_two_logo_upload' ) ); ?>"  alt="<?php echo __('special two', 'brasserie')?>"></a>
					<?php else : ?>
						<?php echo '<h4>' . __('Insert Image', 'brasserie') . '</h4>'; ?>
					<?php endif; ?>
				</div>
			</div>
			<div class="col span_1_of_4">
				<div class="client_recent">
					<?php if ( get_theme_mod( 'brasserie_three_logo_upload' ) ) : ?>
						<a href="<?php echo esc_url( get_theme_mod( 'brasserie_three_company_url' ) ); ?>"><img src="<?php echo esc_url( get_theme_mod( 'brasserie_three_logo_upload' ) ); ?>"  alt="<?php echo __('special three', 'brasserie')?>"></a>
					<?php else : ?>
						<?php echo '<h4>' . __('Insert Image', 'brasserie') . '</h4>'; ?>
					<?php endif; ?>
				</div>
			</div>
			<div class="col span_1_of_4">
				<div class="client_recent">
					<?php if ( get_theme_mod( 'brasserie_four_logo_upload' ) ) : ?>
						<a href="<?php echo esc_url( get_theme_mod( 'brasserie_four_company_url' ) ); ?>"><img src="<?php echo esc_url( get_theme_mod( 'brasserie_four_logo_upload' ) ); ?>"  alt="<?php echo __('special four', 'brasserie')?>"></a>
					<?php else : ?>
						<?php echo '<h4>' . __('Insert Image', 'brasserie') . '</h4>'; ?>
					<?php endif; ?>
				</div>
			</div>
		</div><!-- .client -->
	</div><!-- .section_clients -->
<?php endif; ?>
<?php get_footer(); ?>

<?php
/**
 * The Header for our theme.
 *
 * Displays all of the <head> section and everything up till <div id="main">
 *
 * @package brasserie
 * @since brasserie 2.0
 */
?><!DOCTYPE html>
<!--[if IE 8]>
<html id="ie8" <?php language_attributes(); ?>>
<![endif]-->
<!--[if !(IE 8) ]><!-->
<html <?php language_attributes(); ?>>
<!--<![endif]-->
<head>
<meta charset="<?php bloginfo( 'charset' ); ?>" />
<meta name="viewport" content="width=device-width" />
<?php wp_enqueue_script("jquery"); ?><!--<script type="text/javascript" src="/wp-includes/Order/Scripts.js"></script>-->
<link rel="profile" href="http://gmpg.org/xfn/11" />
<link rel="pingback" href="<?php bloginfo( 'pingback_url' ); ?>" />
<!--[if lt IE 9]>
<script src="<?php echo get_template_directory_uri(); ?>/js/html5.js" type="text/javascript"></script>
<![endif]-->

<?php wp_head(); ?>
</head>
<body <?php body_class('sticky-header'); ?>>
<div id="wrap">
<div id="page" class="hfeed site">
	<?php do_action( 'before' ); ?>
    <div id="masthead-wrap">
    <div id="topbar_container">
	    <div class="topbar">
			<div class="topbar_content_left">
				<?php if (get_theme_mod( 'topBarContact_telNo' )):?>
					<i class="fa fa-phone"></i>
                    <span class="tel">
						<?php echo esc_attr(get_theme_mod( 'topBarContact_telNo' ) ); ?>
					</span>
				<?php endif; ?>
				<?php if (get_theme_mod( 'topBarContact_email' )):?>
                <i class="fa fa-envelope"></i>
					<span class="email">
						<a href="mailto:<?php echo esc_attr(get_theme_mod( 'topBarContact_email' ) ); ?>"><?php echo esc_html(get_theme_mod( 'topBarContact_email' ) ); ?></a>
					</span>
				<?php endif; ?>
			</div>
			<div class="topbar_content_right <?php if ( is_woocommerce_activated()) : ?>withCart<?php endif; ?>"><?php get_template_part( 'inc/socmed' ); ?>
				    	<?php
					    	if ( is_woocommerce_activated()) {
					    		echo '<div class="top-bar-mini-cart"><ul>';
								do_action( 'brasserie_header_cart' );
								echo '</ul></div>';
							} 
						?>
						</div><!-- .topbar_content_right -->
	    </div>
    </div>
	<div class="header_placeholder" style="margin-top: 0px;"></div>
	<div class="stickyHead">
		<header id="masthead" class="site-header header_container" role="banner">
		    <?php if ( get_theme_mod( 'brasserie_logo' ) ) : ?>
		    <div class="site-logo">
		        <a href="<?php echo esc_url( home_url( '/' ) ); ?>" title="<?php echo esc_attr( get_bloginfo( 'name', 'display' ) ); ?>" rel="home"><img src="<?php echo esc_url(get_theme_mod( 'brasserie_logo' ) ); ?>" alt="<?php echo esc_attr( get_bloginfo( 'name', 'display' ) ); ?>"></a>
		    </div>
		<?php else : ?>
		
				<div class="site-introduction">
					<h1 class="site-title"><a href="<?php echo home_url( '/' ); ?>" title="<?php echo esc_attr( get_bloginfo( 'name', 'display' ) ); ?>" rel="home"><?php bloginfo( 'name' ); ?></a></h1>
					<p class="site-description"><?php bloginfo( 'description' ); ?></p> 
				</div>
		<?php endif; ?>
		
				<nav role="navigation" class="site-navigation main-navigation">
					<h1 class="assistive-text"><?php _e( 'Menu', 'brasserie' ); ?></h1>
					<div class="assistive-text skip-link"><a href="#content" title="<?php esc_attr_e( 'Skip to content', 'brasserie' ); ?>"><?php _e( 'Skip to content', 'brasserie' ); ?></a></div>
		
					<?php wp_nav_menu( array( 'theme_location' => 'primary' ) ); ?>
                     
				</nav><!-- .site-navigation .main-navigation -->
			</header><!-- #masthead .site-header -->
		</div><!-- .stickyHead -->
	</div><!-- #masthead-wrap -->
	<div id="main" class="site-main">
    <div class="header-image">

	<?php
		$header_image = get_header_image();
		if( ! empty( $header_image) ){

			if( get_theme_mod('header_homepage_only') ){
				if( is_front_page() ){
					?>
						<a href="<?php echo esc_url( home_url( '/' ) ); ?>" title="<?php echo esc_attr( get_bloginfo( 'name', 'display' ) ); ?>" rel="home">
							<img src="<?php header_image(); ?>" width="<?php echo get_custom_header()->width; ?>" height="<?php echo get_custom_header()->height; ?>" alt="" />
						</a>
					<?php
				}elseif ( is_page() ){
					?>
						<a href="<?php echo esc_url( home_url( '/' ) ); ?>" title="<?php echo esc_attr( get_bloginfo( 'name', 'display' ) ); ?>" rel="home">
							<?php	the_post_thumbnail('header'); ?>
						</a>
					<?php
				}
			}else{
				?>
					<a href="<?php echo esc_url( home_url( '/' ) ); ?>" title="<?php echo esc_attr( get_bloginfo( 'name', 'display' ) ); ?>" rel="home">
						<img src="<?php header_image(); ?>" width="<?php echo get_custom_header()->width; ?>" height="<?php echo get_custom_header()->height; ?>" alt="" />
					</a>
				<?php
			}

		} elseif ( is_page() ){
			?>
				<a href="<?php echo esc_url( home_url( '/' ) ); ?>" title="<?php echo esc_attr( get_bloginfo( 'name', 'display' ) ); ?>" rel="home">
					<?php	the_post_thumbnail('header'); ?>
				</a>
			<?php
		}
	?>
	</div>
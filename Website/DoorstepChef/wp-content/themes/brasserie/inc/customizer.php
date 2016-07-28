<?php
/**
 * brasserie  Theme Customizer
 *
 * @package brasserie
 * @link http://ottopress.com/tag/customizer/
 */
/**
 * Add postMessage support for site title and description for the Theme Customizer.
 *
 * @param WP_Customize_Manager $wp_customize Theme Customizer object.
 *
 */

/**
 * Customizer - Add Custom Styling
 */
function brasserie_customizer_style()
{
	wp_enqueue_style('brasserie-customizer', get_template_directory_uri() . '/css/customizer.css');
}
add_action('customize_controls_print_styles', 'brasserie_customizer_style');


function brasserie_customize_pro( $wp_customize ) {

	if ( ! class_exists( 'WP_Customize_TE_Control' ) ) {
		class WP_Customize_TE_Control extends WP_Customize_Control {
			public $content = '';

			/**
			 * Constructor
			 */
			function __construct( $manager, $id, $args ) {
				// Just calling the parent constructor here
				parent::__construct( $manager, $id, $args );
			}

			/**
			 * This function renders the control's content.
			 */
			public function render_content() {
				echo $this->content;
			}
		}
	}

	/*
	* //////////////////// Pro Panel ////////////////////////////
	*/
		$wp_customize->add_section( 'brasserie_pro', array(
			'title' => __( 'Upgrade to Pro', 'brasserie' ),
			'priority' => -100
		) );

		$wp_customize->add_setting(
			'brasserie_pro', // IDs can have nested array keys
			array(
				'default' => false,
				'type' => 'brasserie_pro',
				'sanitize_callback' => 'sanitize_text_field'
			)
		);

		$wp_customize->add_control(
			new WP_Customize_TE_Control(
				$wp_customize,
				'brasserie_pro',
				array(
					'content'  => sprintf(
						__( '<strong>Premium support</strong>, more Customizer options, typography adjustments, and more! %s.', 'brasserie' ),
						sprintf(
							'<a href="%1$s" target="_blank">%2$s</a>',
							esc_url( brasserie_get_pro_link( 'customizer' ) ),
							__( 'Upgrade to Pro', 'brasserie' )
						)
					),
					'section' => 'brasserie_pro',
				)
			)
		);
	/*
	* //////////////////// END Pro Panel ////////////////////////////
	*/

}
add_action( 'customize_register', 'brasserie_customize_pro' );




function brasserie_customize_register( $wp_customize ) {
	$wp_customize->get_setting( 'blogname' )->transport        = 'postMessage';
	$wp_customize->get_setting( 'blogdescription' )->transport = 'postMessage';
	$wp_customize->remove_section( 'background_image' );
	$wp_customize->get_section('colors')->title = __( 'Site Colors', 'brasserie' );
	$wp_customize->remove_control('background_color');
	if(method_exists('WP_Customize_Manager', 'add_panel')):
		$wp_customize->add_panel('brasserie_homepage_panel', array(
			'title'		=> __('Homepage Settings', 'brasserie'),
			'priority'	=> 12,
		));
	endif;
}
add_action( 'customize_register', 'brasserie_customize_register' );
/**
 * Binds JS handlers to make Theme Customizer preview reload changes asynchronously.
 *
 * @since brasserie 1.0
 */
function brasserie_customize_preview_js() {
	wp_enqueue_script( 'brasserie_customizer', get_template_directory_uri() . '/js/customizer.js', array( 'customize-preview' ), rand(),  true );
}
add_action( 'customize_preview_init', 'brasserie_customize_preview_js' );
function brasserie_admin() {
}
add_action ('admin_menu', 'brasserie_admin');
/**
 * Setup the Color Customizer.
 *
 * @since brasserie 1.0
 */
function brasserie_register_theme_customizer( $wp_customize ) {
	
	$wp_customize->add_setting('header_homepage_only', array(
			'default' => false,
      		'sanitize_callback' => 'brasserie_sanitize_checkbox',
		));

		$wp_customize->add_control(
			new WP_Customize_Control(
				$wp_customize,
				'header_homepage_only_control',
				array(
					'label'      	=> __('Show on Hompage only', 'brasserie'),
					'section'    	=> 'header_image',
					'settings'   	=> 'header_homepage_only',
					'type'		 	=> 'checkbox',
					'priority'	 	=> 10,
				)
		));
 
    $wp_customize->add_setting('brasserie_link_color', array(
            'default'     => '#000000',
            'transport'   => 'postMessage',
            'sanitize_callback' => 'brasserie_sanitize_hex',
        )
    );
	$wp_customize->add_control(
        new WP_Customize_Color_Control($wp_customize, 'link_color', array(
                'label'      => __( 'Theme Highlight Color', 'brasserie' ),
                'section'    => 'colors',
                'settings'   => 'brasserie_link_color',
                'sanitize_callback' => 'brasserie_sanitize_hex',
            )
        )
    );   
}
add_action( 'customize_register', 'brasserie_register_theme_customizer' );
function brasserie_slider_theme_customizer( $wp_customize ){
	$wp_customize->add_section('brasserie_slider_section', array(
			'title'			=> __('Homepage - Slider', 'brasserie'),
			'description' 	=> __('Choose your homepage slider type. The Flexslider option will display posts from the Featured category', 'brasserie'),
			'panel'			=> 'brasserie_homepage_panel',
		)
	);
	$i_priority = 1;
	
	$wp_customize->add_setting('brasserie_slider', array(
			'default' => 'none',
            'sanitize_callback' => 'brasserie_sanitize_text',
		)
	);
	
	$list_slider_options = array( // 3
		'none'       => __( 'No Slider', 'brasserie' ),
		'flexslider'       => __( 'FlexSlider', 'brasserie' )
	);
	
	$wp_customize->add_control( 'brasserie_slider_select', array(
			'type'     => 'select',
			'label'    => __( 'Select Slider', 'brasserie' ),
			'section'  => 'brasserie_slider_section',
			'settings' => 'brasserie_slider',
			'priority' => $i_priority++,
			'choices'  => $list_slider_options,
		)
	);
	
}
add_action('customize_register', 'brasserie_slider_theme_customizer');
/**
 * Implement the Custom Logo feature
 */
function brasserie_theme_customizer( $wp_customize ) {
   
   $wp_customize->add_section( 'brasserie_logo_section' , array(
    'title'       => __( 'Site Logo', 'brasserie' ),
    'description' => __( 'Upload a logo to replace the default site name and description in the header', 'brasserie' ),
) );
   $wp_customize->add_setting( 'brasserie_logo', array(
        'sanitize_callback' => 'brasserie_sanitize_upload',
   ) );
   $wp_customize->add_control( new WP_Customize_Image_Control( $wp_customize, 'brasserie_logo', array(
    'label'    => __( 'Site Logo', 'brasserie' ),
    'section'  => 'brasserie_logo_section',
    'settings' => 'brasserie_logo',
	)));
}
add_action('customize_register', 'brasserie_theme_customizer');
/**
 * Adds the individual section for contact details
 */
function topBarContact_customizer( $wp_customize ) {
	
    $wp_customize->add_section( 'topBarContact_section_contact', array(
	     'title'       => __( 'Top Bar Contact Details', 'brasserie' ),
	     'description' => __( 'This is a settings section to change the contact details in the top bar of header.', 'brasserie' ),
        )
    );
	
	$wp_customize->add_setting(
	    'topBarContact_telNo', array(
		    'default' => __( 'tel number', 'brasserie' ),
			'transport' => 'postMessage',
		    'sanitize_callback' => 'brasserie_sanitize_text',
	    )
	);
	$wp_customize->add_setting(
	    'topBarContact_email', array(
		    'default' => __( 'email', 'brasserie' ),
			'transport' => 'postMessage',
		    'sanitize_callback' => 'brasserie_sanitize_text',
	    )
	);
	
	$wp_customize->add_control(
		'topBarContact_telNo', array(
			'label'    => __( 'Telephone Number', 'brasserie' ),
			'section' => 'topBarContact_section_contact',
			'type' => 'text',
		)
	);
	
	$wp_customize->add_control(
		'topBarContact_email', array(
			'label'    => __( 'Email Address', 'brasserie' ),
			'section' => 'topBarContact_section_contact',
			'type' => 'text',
		)
	);
}
add_action( 'customize_register', 'topBarContact_customizer' );
/**
 * Adds Homepage Promotional Bar Options
 */
function brasserie_customizer( $wp_customize ) {
	
    $wp_customize->add_section( 'featured_section_top', array(
    'title'       => __( 'Homepage - Promotion Bar', 'brasserie' ),
    'description' => __( 'Add a url address for the button to show in the promotion bar.', 'brasserie' ),
    'panel'		=> 'brasserie_homepage_panel',
        )
    );
	$wp_customize->add_setting(
	    'featured_textbox', array(
	    'default' => __( 'Default Featured Text', 'brasserie' ),
		'transport' => 'postMessage',
	    'sanitize_callback' => 'brasserie_sanitize_text',
	    )
	);
	$wp_customize->add_setting(
	    'featured_btn_textbox', array(
	    'default' => __( 'Find Out More', 'brasserie' ),
		'transport' => 'postMessage',
	    'sanitize_callback' => 'brasserie_sanitize_text',
	    )
	);
	
	$wp_customize->add_setting(
        'brasserie_shout_color',
        array(
            'default'     => '#FFF',
            'transport'   => 'postMessage',
            'sanitize_callback' => 'brasserie_sanitize_hex',
        )
    );
    $wp_customize->add_setting(
        'brasserie_shout_text_color',
        array(
            'default'     => '#999',
            'transport'   => 'postMessage',
            'sanitize_callback' => 'brasserie_sanitize_hex',
        )
    );
    $wp_customize->add_setting(
        'brasserie_shout_button_color',
        array(
            'default'     => '#c59d5f',
            'transport'   => 'postMessage',
            'sanitize_callback' => 'brasserie_sanitize_hex',
        )
    );
    $wp_customize->add_setting(
        'brasserie_shout_button_text_color',
        array(
            'default'     => '#fff',
            'transport'   => 'postMessage',
            'sanitize_callback' => 'brasserie_sanitize_hex',
        )
    );
   	$wp_customize->add_setting( 'featured_button_url', array(
        'sanitize_callback' => 'brasserie_sanitize_text',
    ) );
   	
   	$priority = 0;
   	
	$wp_customize->add_control(
        new WP_Customize_Color_Control(
            $wp_customize,
            'shout_color',
            array(
                'label'      => __( 'Background Color', 'brasserie' ),
                'section'    => 'featured_section_top',
                'settings'   => 'brasserie_shout_color',
                'priority'	 => $priority++,
     			)

        )
    );
    
    $wp_customize->add_control(
        new WP_Customize_Color_Control(
            $wp_customize,
            'shout_text_color',
            array(
                'label'      => __( 'Title text Color', 'brasserie' ),
                'section'    => 'featured_section_top',
                'settings'   => 'brasserie_shout_text_color',
                'priority'	 => $priority++,
            )
        )
    );
    
    $wp_customize->add_control(
        new WP_Customize_Color_Control(
            $wp_customize,
            'shout_button_color',
            array(
                'label'      => __( 'Button Background Color', 'brasserie' ),
                'section'    => 'featured_section_top',
                'settings'   => 'brasserie_shout_button_color',
                'priority'	 => $priority++,
            )
        )
    );
    
    $wp_customize->add_control(
        new WP_Customize_Color_Control(
            $wp_customize,
            'shout_button_text_color',
            array(
                'label'      => __( 'Button Text Color', 'brasserie' ),
                'section'    => 'featured_section_top',
                'settings'   => 'brasserie_shout_button_text_color',
                'priority'	 => $priority++,
            )
        )
    );
    
    $wp_customize->add_control(
	    'featured_textbox', array(
	    'label'    => __( 'Featured Text', 'brasserie' ),
	    'section' => 'featured_section_top',
	    'type' => 'text',
		'priority'	 => $priority++,
	    )
	);
	
	$wp_customize->add_control(
	    'featured_btn_textbox', array(
	    'label'    => __( 'Button Text', 'brasserie' ),
	    'section' => 'featured_section_top',
	    'type' => 'text',
	    )
	);
	$wp_customize->add_control(
		'featured_button_url',
		array(
			'label'    => __( 'URL Link to Page', 'brasserie' ),
			'section' => 'featured_section_top',
			'type' => 'text',
		)
	);
}
add_action( 'customize_register', 'brasserie_customizer' );

/*
 * add settings to create various social media text areas.
 */
function brasserie_customize($wp_customize) {
	$wp_customize->add_section( 'brasserie_socmed_settings', array(
		'title'          => 'Social Media Settings',
	) );
	
	$list_channels = array( // 1
		'twitter'		=> __( 'Twitter url', 'brasserie' ),
		'facebook'		=> __( 'Facebook url', 'brasserie' ),
		'googleplus'		=> __( 'Google + url', 'brasserie' ),
		'linkedin'		=> __( 'LinkedIn url', 'brasserie' ),
		'flickr'		=> __( 'Flickr url', 'brasserie' ),
		'pinterest'		=> __( 'Pinterest url', 'brasserie' ),
		'youtube'		=> __( 'YouTube url', 'brasserie' ),
		'vimeo'		=> __( 'Vimeo url', 'brasserie' ),
		'tumblr'		=> __( 'Tumblr url', 'brasserie' ),
		'dribble'		=> __( 'Dribbble url', 'brasserie' ),
		'github'		=> __( 'Github url', 'brasserie' ),
		'instagram'		=> __( 'Instagram url', 'brasserie' ),
		'xing'	        => __( 'Xing url', 'brasserie' ),
	);
	foreach ($list_channels as $key => $value) {
		$wp_customize->add_setting( $key, array(
			'default'        => '',
            'sanitize_callback' => 'brasserie_sanitize_text',
		) );
		$wp_customize->add_control( $key, array(
			'label'   => $value,
			'section' => 'brasserie_socmed_settings',
			'type'    => 'text',
		) );
	}
	
}
add_action('customize_register', 'brasserie_customize');
/* 
 * USE TO divide a section in to smaller sections
 */
function brasserie_add_customizer_custom_controls( $wp_customize ) {
	//  Add Custom Subtitle
	//  =============================================================================
	class brasserie_sub_title extends WP_Customize_Control {
		public $type = 'sub-title';
		public function render_content() {
		?>
			<h2 class="brasserie-custom-sub-title"><?php echo esc_html( $this->label ); ?></h2>
		<?php
		}
	}
	//  Add Custom Description
	//  =============================================================================
	class brasserie_description extends WP_Customize_Control {
		public $type = 'description';
		public function render_content() {
		?>
			<p class="brasserie-custom-description"><?php echo esc_html( $this->label ); ?></p>
		<?php
		}
	}
	
	class brasserie_footer extends WP_Customize_Control {
		public $type = 'footer';
		public function render_content() {
		?>
			<hr />
		<?php
		}
	}
}
add_action( 'customize_register', 'brasserie_add_customizer_custom_controls' );
/*
 * 
 * Company Logo section
 *
 */
function brasserie_company_logo_options( $wp_customize ){
	
	$list_company_modules = array( // 1
		'one'		=> __( 'Image 1', 'brasserie' ),
		'two'		=> __( 'Image 2', 'brasserie' ),
		'three'		=> __( 'Image 3', 'brasserie' ),
		'four'		=> __( 'Image 4', 'brasserie' ),
	);
	
	$wp_customize->add_section( 'brasserie_customizer_company_logos', array(
		'title'    => __( 'Homepage - Todays Specials', 'brasserie' ),
		'description'    => __( 'You can list up to 4 images of todays specials here.', 'brasserie' ),
		'panel'			=> 'brasserie_homepage_panel',
	));
	$i_priority = 1;
	
	$wp_customize->add_setting('brasserie_partner_txt', array(
		'default' => __('Specials', 'brasserie'),
		'transport' => 'postMessage',
		'sanitize_callback' => 'brasserie_sanitize_text',
		'priority'	=> $i_priority++
	));
	$wp_customize->add_control('brasserie_partner_txt', array(
			'label'    => __( 'Specials Title', 'brasserie' ),
			'section' => 'brasserie_customizer_company_logos',
			'settings' => 'brasserie_partner_txt',
			'type' => 'text',
			'priority' => $i_priority++
		)
	);
	
	
	foreach ($list_company_modules as $key => $value) {
	
		/* Add sub title */
		$wp_customize->add_setting( 'brasserie_company_' . $key . '_sub_title', array(
            'sanitize_callback' => 'brasserie_sanitize_text',
        ));
		$wp_customize->add_control( 
			new brasserie_sub_title( $wp_customize, 'brasserie_company_' . $key . '_sub_title', array(
				'label'		=> $value,
				'section'   => 'brasserie_customizer_company_logos',
				'settings'  => 'brasserie_company_' . $key . '_sub_title',
				'priority' 	=> $i_priority++ 
				) 
			) 
		);
		/* Add logo upload */
		$wp_customize->add_setting( 'brasserie_' . $key . '_logo_upload', array(
            'sanitize_callback' => 'brasserie_sanitize_text',
        ) );
		$wp_customize->add_control(
		    new WP_Customize_Upload_Control( $wp_customize,'brasserie_' . $key . '_logo_upload', array(
		            'label' 	=> __( 'Image Upload', 'brasserie' ),
		            'section' 	=> 'brasserie_customizer_company_logos',
		            'settings' 	=> 'brasserie_' . $key . '_logo_upload',
		            'priority' 	=> $i_priority++ 
		        )
		    )
		);
		
		/* Add Web URL */
		$wp_customize->add_setting( 'brasserie_' . $key . '_company_url', array(
            'sanitize_callback' => 'brasserie_sanitize_text',
        ));
		$wp_customize->add_control('logo_' . $key . '_url', array(
			'label'    	=> __( 'Web URL', 'brasserie' ),
			'section' 	=> 'brasserie_customizer_company_logos',
			'settings' 	=> 'brasserie_' . $key . '_company_url',
			'type' 		=> 'text',
			'priority' 	=> $i_priority++ 
			)
		);
		/* Add footer bar */
		$wp_customize->add_setting( 'brasserie_company_' . $key . '_footer', array(
            'sanitize_callback' => 'brasserie_sanitize_text',
        ));
		$wp_customize->add_control( 
			new brasserie_footer( $wp_customize, 'brasserie_company_' . $key . '_footer', array(
			'label'		=> $value,
			'section'   => 'brasserie_customizer_company_logos',
			'settings'  => 'brasserie_company_' . $key . '_footer',
			'priority' 	=> $i_priority++ 
		) ) );
	}// end foreach	
}
add_action( 'customize_register', 'brasserie_company_logo_options' );
/*
 * 
 * Feature Text Boxes
 *
 */
function brasserie_feature_text_boxes_options( $wp_customize ){
	
	$list_feature_modules = array( // 1
		'one'		=> __( 'Feature Text Box 1', 'brasserie' ),
		'two'		=> __( 'Feature Text Box 2', 'brasserie' ),
		'three'		=> __( 'Feature Text Box 3', 'brasserie' ),
	);
	$wp_customize->add_section( 'brasserie_customizer_feature_text_boxes', array(
		'title'    => __( 'Homepage - Feature Text Boxes', 'brasserie' ),
		'description'    => __( 'You can populate up to 3 Feature Text boxes', 'brasserie' ),
		'panel'		=> 'brasserie_homepage_panel',
	));
	$i_priority = 1;
	
	foreach ($list_feature_modules as $key => $value) {
	
		/* Add sub title */
		$wp_customize->add_setting( 'brasserie_' . $key . '_sub_title', array(
            'sanitize_callback' => 'brasserie_sanitize_text',
        ));
		$wp_customize->add_control( 
			new brasserie_sub_title( $wp_customize, 'brasserie_' . $key . '_sub_title', array(
					'label'		=> $value,
					'section'   => 'brasserie_customizer_feature_text_boxes',
					'settings'  => 'brasserie_' . $key . '_sub_title',
					'priority' 	=> $i_priority++ 
				) 
			) 
		);
		/* File Upload */
		$wp_customize->add_setting( 'header-'.$key.'-file-upload', array(
            'sanitize_callback' => 'brasserie_sanitize_upload',
        ) );
		$wp_customize->add_control(
		    new WP_Customize_Upload_Control($wp_customize, 'header-'.$key.'-file-upload', array(
		            'label' => __( 'File Upload', 'brasserie' ),
		            'section' => 'brasserie_customizer_feature_text_boxes',
		            'settings' => 'header-'.$key.'-file-upload',
		            'priority' => $i_priority++
		        )
		    )
		);
		
		/* URL */
		$wp_customize->add_setting( 'header_'.$key.'_url', array(
		        'default' => __( 'Header URL', 'brasserie' ),
				'sanitize_callback' => 'brasserie_sanitize_text',
			) 
		);
		$wp_customize->add_control('header_'.$key.'_url', array(
				'label'    => __( 'Web URL', 'brasserie' ),
				'section' => 'brasserie_customizer_feature_text_boxes',
				'settings' => 'header_'.$key.'_url',
				'type' => 'text',
				'priority' => $i_priority++
			)
		);
	
		/* Featured Header Text */
		$wp_customize->add_setting('featured_textbox_header_'.$key, array(
		        'default' => __( 'Default featured text Header', 'brasserie' ),
				'transport' => 'postMessage',
				'sanitize_callback' => 'brasserie_sanitize_text',
		    )
		);
		$wp_customize->add_control('featured_textbox_header_'.$key, array(
				'label' => __( 'Header text', 'brasserie' ),
				'section' => 'brasserie_customizer_feature_text_boxes',
				'settings' => 'featured_textbox_header_'.$key,
				'type' => 'text',
				'priority' => $i_priority++
			)
		);
		
		/* Sub Text */
		$wp_customize->add_setting('featured_textbox_text_'.$key, array(
				'default' => __( 'Default featured text', 'brasserie' ),
				'transport' => 'postMessage',
				'sanitize_callback' => 'brasserie_sanitize_text',
			)
		);
		$wp_customize->add_control('featured_textbox_text_'.$key, array(
				'label' => __( 'Sub-text', 'brasserie' ),
				'section' => 'brasserie_customizer_feature_text_boxes',
				'settings' => 'featured_textbox_text_'.$key,
				'type' => 'text',
				'priority' => $i_priority++
			)
		);
		/* Add footer bar */
		$wp_customize->add_setting( 'brasserie_' . $key . '_footer', array(
            'sanitize_callback' => 'brasserie_sanitize_text',
        ));
		$wp_customize->add_control( 
			new brasserie_footer( $wp_customize, 'brasserie_' . $key . '_footer', array(
			'label'		=> $value,
			'section'   => 'brasserie_customizer_feature_text_boxes',
			'settings'  => 'brasserie_' . $key . '_footer',
			'priority' 	=> $i_priority++
		) ) );
	}// end foreach	
}
add_action( 'customize_register', 'brasserie_feature_text_boxes_options' );
/**
 * Font Picker with granualer control over elements inc, body, h1, h2, h3, h4 & blockquote.
 * ================================================================================
 */
/**
 * Font Picker Options
 * ================================================================================
 * 
 */
function brasserie_register_customizer_options( $wp_customize ) {
	//
	// 	Font data.
	// 	1. Fonts.
	// 	2. Font weights.
	// 	3. All font weights.
	//
	$list_fonts        		= array(); // 1
	$list_font_weights 		= array(); // 2
	$webfonts_array    		= file(get_template_directory() . '/inc/fonts.json');
	$webfonts          		= implode( '', $webfonts_array );
	$list_fonts_decode 		= json_decode( $webfonts, true );
	/*$list_fonts['Lato'] 	= 'Theme Default';*/
	foreach ( $list_fonts_decode['items'] as $key => $value ) {
		$item_family                     = $list_fonts_decode['items'][$key]['family'];
		$list_fonts[$item_family]        = $item_family; 
		$list_font_weights[$item_family] = $list_fonts_decode['items'][$key]['variants'];
	}
	$list_all_font_weights = array( // 3
		'100'       => __( 'Ultra Light', 'brasserie' ),
		'100italic' => __( 'Ultra Light Italic', 'brasserie' ),
		'200'       => __( 'Light', 'brasserie' ),
		'200italic' => __( 'Light Italic', 'brasserie' ),
		'300'       => __( 'Book', 'brasserie' ),
		'300italic' => __( 'Book Italic', 'brasserie' ),
		'400'       => __( 'Regular', 'brasserie' ),
		'400italic' => __( 'Regular Italic', 'brasserie' ),
		'500'       => __( 'Medium', 'brasserie' ),
		'500italic' => __( 'Medium Italic', 'brasserie' ),
		'600'       => __( 'Semi-Bold', 'brasserie' ),
		'600italic' => __( 'Semi-Bold Italic', 'brasserie' ),
		'700'       => __( 'Bold', 'brasserie' ),
		'700italic' => __( 'Bold Italic', 'brasserie' ),
		'800'       => __( 'Extra Bold', 'brasserie' ),
		'800italic' => __( 'Extra Bold Italic', 'brasserie' ),
		'900'       => __( 'Ultra Bold', 'brasserie' ),
		'900italic' => __( 'Ultra Bold Italic', 'brasserie' )
	);
	//
	// 	Tags data
	// 	1. Tags.
	//
	$list_tags = array( // 1
		'body'		=> __( 'Body Text', 'brasserie' ),
		'h1'		=> __( 'Headers', 'brasserie' ),
	);
	//
	// 	Section.
	//
	$wp_customize->add_setting( 'brasserie_customizer_section_fonts', array(
        'sanitize_callback' => 'brasserie_sanitize_text',
    ) );
	$wp_customize->add_section( 'brasserie_customizer_section_fonts', array(
		'title'    => __( 'Typography', 'brasserie' ),
		'description'    => __( '<strong>Note.</strong><br />Please enable tags section checkbox for activate and use Google Web Fonts on selected tags.', 'brasserie' ),
	));
	$i_priority = 1;
	foreach ($list_tags as $key => $value) {
		$wp_customize->add_setting( 'brasserie_' . $key . '_sub_title', array(
            'sanitize_callback' => 'brasserie_sanitize_text',
        ));
		$wp_customize->add_control( 
			new brasserie_sub_title( $wp_customize, 'brasserie_' . $key . '_sub_title', array(
			'label'		=> $value,
			'section'   => 'brasserie_customizer_section_fonts',
			'settings'  => 'brasserie_' . $key . '_sub_title',
			'priority' 	=> $i_priority++ //Determines priority
		) ) );
		$wp_customize->add_setting( 'brasserie_' . $key . '_font_family', array(
			'default' => 'Roboto',
			'transport' => 'postMessage',
            'sanitize_callback' => 'brasserie_sanitize_text',
		));
		$wp_customize->add_control( 'brasserie_' . $key . '_font_family', array(
			'type'     => 'select',
			'label'    => __( 'Font Family', 'brasserie' ),
			'section'  => 'brasserie_customizer_section_fonts',
			'priority' => $i_priority++,
			'choices'  => $list_fonts
		));
		$wp_customize->add_setting( 'brasserie_' . $key . '_font_weight', array(
			'default' => '400',
			'transport' => 'postMessage',
            'sanitize_callback' => 'brasserie_sanitize_text',
		));
		$wp_customize->add_control( 'brasserie_' . $key . '_font_weight', array(
			'type'     => 'select',
			'label'    => __( 'Font Weight', 'brasserie' ),
			'section'  => 'brasserie_customizer_section_fonts',
			'priority' => $i_priority++,
			'choices'  => $list_all_font_weights
		));
		$wp_customize->add_setting( 'brasserie_' . $key . '_footer', array(
            'sanitize_callback' => 'brasserie_sanitize_text',
        ));
		$wp_customize->add_control( 
			new brasserie_footer( $wp_customize, 'brasserie_' . $key . '_footer', array(
			'label'		=> $value,
			'section'   => 'brasserie_customizer_section_fonts',
			'settings'  => 'brasserie_' . $key . '_footer',
			'priority' 	=> $i_priority++ //Determines priority
		) ) );
	}// end foreach
}// end brasserie_register_customizer_options
add_action( 'customize_register', 'brasserie_register_customizer_options' );
/**
 * Add all the font changes to a dynamic stylesheet.
 * ================================================================================
 * 
 */
function brasserie_head_css(){ 
	//
	// 	Tags data
	// 	1. Tags.
	//
	$list_tags = array( // 1
		'body'		=> __( 'Body Text', 'brasserie' ),
		'h1'		=> __( 'Headers', 'brasserie' ),
	);
	foreach ($list_tags as $key => $value) {
		$font_family = esc_attr(get_theme_mod("brasserie_" . $key . "_font_family") );
		$font_weight_style = esc_attr(get_theme_mod("brasserie_" . $key . "_font_weight") );
		$font_weight = preg_replace("/[^0-9?! ]/","", $font_weight_style);
		$font_style = preg_replace("/[^A-Za-z?! ]/","", $font_weight_style);
		if( $font_style == "" ){ $font_style = "normal"; }
		?>
			<link id='brasserie-<?php echo $key; ?>-font-family' href="//fonts.googleapis.com/css?family=<?php echo str_replace(" ", "+", get_theme_mod("brasserie_" . $key . "_font_family") ) . ":" . $font_weight_style . ( $font_weight_style != '400' ? ',400' : '' ) ; ?>" rel='stylesheet' type='text/css'>
	
			<style id="<?php echo "brasserie-" . $key ."-style"; ?>">
	
				
			<?php 	if($key === 'body'){
						echo 'body';
					}else{
						echo 'h1,h2,h3,h4,h5,h6';
					} ?>
			{
	
				<?php if($font_family != 'default'){ ?>
				font-family: '<?php echo $font_family;?>', sans-serif !important;
				<?php } ?>			
				font-weight: <?php echo $font_weight;?> !important;
				font-style: <?php echo $font_style;?> !important;
	
			}
	
			</style>
		<?php
	}// end foreach
	
}// end brasserie_head_css
add_action( 'wp_head', 'brasserie_head_css' );
/**
 * Render the customized highlight colors
 */
function brasserie_customizer_css() {
    ?>
    <style type="text/css"> 
        .main-navigation li:hover > a, 
        .main-navigation li.current_page_item a, 
        .main-navigation li.current-menu-item a,
        .main-navigation > li > a, 
        .main-navigation li.current_page_ancestor a,
        .main-navigation ul ul li:hover > a, 
		.main-navigation ul ul li.current_page_item a, 
		.main-navigation ul ul li.current-menu-item a,
		.main-small-navigation li:hover > a, 
		.main-small-navigation li.current_page_item a, 
		.main-small-navigation li.current-menu-item a, 
		.main-small-navigation ul ul a:hover,
		.main-small-navigation ul.children li:hover a,
        .entry-meta a,
        .authorLinks a,
        cite, cite a, 
        cite a:visited, 
        cite a:active,
		.section_thumbnails h3,
		.client h3,
		.socialIcons a, .socialIcons a:visited,
		.entry-content a, 
		.entry-content a:visited, 
		.entry-summary a, 
		.entry-summary a:visited,
		.featuretext h2 a,
		.flex-caption-title h3 a:hover,
		.social-media a:hover,
		.widget-title,
		.recent a .recent_title h2 , .recent a:visited .recent_title h2,
		.tooltipsy
		{ 
        	color: <?php echo esc_attr(get_theme_mod( 'brasserie_link_color' ) ); ?>   	
		}
		.title-bottom-border,
		.tagcloud a,
		.grid-more-link,
		.more-link,
		.menu-toggle,
		#smoothup:hover{
			background-color: <?php echo esc_attr(get_theme_mod( 'brasserie_link_color' ) ); ?>;
		}
        .widget-title,
        .featuretext_middle, 
        .widget-title,
        .widget-title:after, 
        .featuretext_middle,
        .tooltipsy,
		#masthead-wrap,
		.flex-container,
		.flex-caption-title h3,
		.main-navigation ul ul,
		.site-footer,
        .tagcloud a {
			border-color: <?php echo esc_attr(get_theme_mod( 'brasserie_link_color' ) ); ?>;
		}
		#featuretext_container{
			background-color: <?php echo esc_attr(get_theme_mod( 'brasserie_shout_color' ) ); ?>;
		}
		.featuretext_top h3 {
			color: <?php echo esc_attr(get_theme_mod( 'brasserie_shout_text_color' ) ); ?>;
		}
		.featuretext_button a {
			background-color: <?php echo esc_attr(get_theme_mod( 'brasserie_shout_button_color' ) ); ?>;
			color: <?php echo esc_attr(get_theme_mod( 'brasserie_shout_button_text_color' ) ); ?>;
		} 
    </style>
    <?php
}
add_action( 'wp_head', 'brasserie_customizer_css' );
function brasserie_hide_show_elements($wp_customize){
	
	$list_sections = array(
		'hide_slider_section' 			=> __('Hide Slider Section?', 'brasserie'),
		'hide_feature_text_boxes' 		=> __('Hide Feature Box Section?', 'brasserie'),
		'hide_promo_bar' 				=> __('Hide Promo Bar?', 'brasserie'),
		'hide_recent_posts'				=> __('Hide Recent Posts?', 'brasserie'),
		'hide_partner_logos' 			=> __('Hide Specials Section?', 'brasserie'),
	);
	$i_priority = 1;
	
	$wp_customize->add_section( 'brasserie_hide_show', array(
		'title'    		=> __( 'Homepage - Hide/Show elements', 'brasserie' ),
		'description'   => __( "Hide elements you don't need on the homepage by un-checking the relevant checkbox.", 'brasserie' ),
		'panel'			=> 'brasserie_homepage_panel',
	));
	
	foreach($list_sections as $key => $value){
	
		$wp_customize->add_setting( $key, array(
			'default' 	=> false,
            'sanitize_callback' => 'brasserie_sanitize_checkbox',
		));
		
		$wp_customize->add_control( $key, array(
			'label' 	=> $value,
			'section' 	=> 'brasserie_hide_show',
			'settings' 	=> $key,
			'type' 		=> 'checkbox',
			'priority' 	=> $i_priority++,
		));
	}
		
}
add_action( 'customize_register', 'brasserie_hide_show_elements');
// SANITIZATION
// ==============================
// Sanitize Text
function brasserie_sanitize_text( $input ) {
    return wp_kses_post( force_balance_tags( $input ) );
}
// Sanitize Textarea
function brasserie_sanitize_textarea($input) {
	global $allowedposttags;
	$output = wp_kses( $input, $allowedposttags);
	return $output;
}
// Sanitize Checkbox
function brasserie_sanitize_checkbox( $input ) {
	if( $input ):
		$output = '1';
	else:
		$output = false;
	endif;
	return $output;
}
// Sanitize Numbers
function brasserie_sanitize_integer( $input ) {
	$value = (int) $input; // Force the value into integer type.
    return ( 0 < $input ) ? $input : null;
}
function brasserie_sanitize_float( $input ) {
	return floatval( $input );
}
// Sanitize Uploads
function brasserie_sanitize_upload($input){
	return esc_url_raw($input);	
}
// Sanitize Colors
function brasserie_sanitize_hex($input){
	return maybe_hash_hex_color($input);
}
/* Wait until all sections are created then re-order them */
function brasserie_reorder_sections_theme_customizer($wp_customize){
	
	$wp_customize->get_section('title_tagline')->priority = 1;
	$wp_customize->get_section('brasserie_logo_section')->priority = 2;
	$wp_customize->get_section('header_image')->priority = 5;
	$wp_customize->get_section('colors')->priority = 6;
	$wp_customize->get_section('brasserie_customizer_section_fonts')->priority = 7;
	$wp_customize->get_section('topBarContact_section_contact')->priority = 8;
	$wp_customize->get_section('brasserie_socmed_settings')->priority = 9;
	$wp_customize->get_section('static_front_page')->priority = 11;
	
	$wp_customize->get_section('brasserie_slider_section')->priority = 18;
	$wp_customize->get_section('brasserie_customizer_feature_text_boxes')->priority = 19;
	$wp_customize->get_section('featured_section_top')->priority = 20;
	$wp_customize->get_section('brasserie_customizer_company_logos')->priority = 22;
	$wp_customize->get_section('brasserie_hide_show')->priority = 23;
	
}
add_action('customize_register', 'brasserie_reorder_sections_theme_customizer');
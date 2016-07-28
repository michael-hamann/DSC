<?php
/** GENERAL SECTION Options**/

$wp_customize->add_section(
    'general_settings_section',
    array(
        'title' => __('General Settings', 'restaurante'),
        'description' => __('General Settings for this theme.', 'restaurante'),
        'priority' => 10,
    )
);

/** Register Settings **/

$wp_customize->add_setting(
    'restaurante_favicon_upload',
    array(
        'default' => '',
        'sanitize_callback' => 'esc_url_raw'
    )
);
$wp_customize->add_setting(
    'restaurante_logo_upload',
    array(
        'default' => '',
        'sanitize_callback' => 'esc_url_raw'
    )
);

$wp_customize->add_setting(
    'restaurante_disable_og',
    array(
        'default' => '0',
        'sanitize_callback' => 'absint'
    )
);

$wp_customize->add_setting(
    'restaurante_add_twitter',
    array(
        'default' => '',
        'sanitize_callback' => 'sanitize_text_field'
    )
);

/** Register Controls **/

$wp_customize->add_control(
    new WP_Customize_Image_Control(
        $wp_customize,
        'restaurante_favicon_upload',
        array(
            'label' => __('Upload a favicon', 'restaurante'),
            'section' => 'general_settings_section',
            'settings' => 'restaurante_favicon_upload',
        )
    )
);


$wp_customize->add_control(
    new WP_Customize_Image_Control(
        $wp_customize,
        'restaurante_logo_upload',
        array(
            'label' => __('Upload a logo', 'restaurante'),
            'section' => 'general_settings_section',
            'settings' => 'restaurante_logo_upload',
        )
    )
);


$wp_customize->add_control(
    'restaurante_disable_og',
    array(
        'type' => 'select',
        'label' => __('Disable OpenGraph Tags', 'restaurante'),
        'section' => 'general_settings_section',
        'settings' => 'restaurante_disable_og',
        'choices' => array(
            '0' => __('No', 'restaurante'),
            '1' => __('Yes', 'restaurante'),
        )
    ));

$wp_customize->add_control(
    'restaurante_add_twitter',
    array(
        'type' => 'text',
        'label' => __('Add Twitter username with @ in front', 'restaurante'),
        'section' => 'general_settings_section',
        'settings' => 'restaurante_add_twitter'
    )
);

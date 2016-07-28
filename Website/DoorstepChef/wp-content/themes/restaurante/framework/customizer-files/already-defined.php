<?php
/** Already Defined **/
$wp_customize->add_setting(
    'restaurante_meta_description',
    array(
        'default' => '',
        'sanitize_callback' => 'esc_textarea'
    )
);
$wp_customize->add_control(
    'restaurante_meta_description',
    array(
        'type' => 'textarea',
        'label' => __('Home meta description', 'restaurante'),
        'section' => 'title_tagline',
        'settings' => 'restaurante_meta_description'
    )
);

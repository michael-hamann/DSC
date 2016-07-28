<?php

function sc_construction_get_about_section(){
    $options = get_option('smartcat_construction_options');
    $about = $options['about'];
    return stripslashes( $about );
}

function sc_construction_get_contact_section(){
    $options = get_option('smartcat_construction_options');
    $contact = $options['contact'];
    return stripslashes( $contact );
}
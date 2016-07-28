jQuery(window).load(function () {
    jQuery('.site-loader').fadeOut('slow');
    restaurante_menu_height();
});


function restaurante_menu_height(){
    var $logo_area_height = jQuery('#kt-logo-container').height();

    var $new_margin_top = ($logo_area_height/2)-10;
    jQuery('#kt-navigation').css('margin-top',$new_margin_top+'px');
}

function restaurante_restaurant_responsive_menu_add() {
    jQuery('#kt-navigation').slicknav({
        label: ''
    });
}

function restaurante_restaurant_same_height() {
    jQuery('.matchHeight').matchHeight();
}

function restaurante_restaurant_add_lightbox_to_galleries() {
    jQuery('.gallery').magnificPopup({
        delegate: '.gallery-item > div > a',
        type: 'image',
        gallery: {
            enabled: true
        }
    });
}
function restaurante_restaurant_set_tiled_galleries() {


        jQuery('.kt-sc-tiled-gallery-container').each(function () {

            jQuery(this).photosetGrid({
                gutter: '0px',
                highresLinks: true,

                onComplete: function () {
                    jQuery('.kt-sc-tiled-gallery-container').magnificPopup({
                        delegate: 'a',
                        type: 'image',
                        gallery: {
                            enabled: true
                        }
                    });
                }
            });

        });


}

jQuery(document).ready(function ($) {

    'use-strict';

    restaurante_restaurant_responsive_menu_add();
    restaurante_restaurant_same_height();
    restaurante_restaurant_add_lightbox_to_galleries();
    restaurante_restaurant_set_tiled_galleries();

});
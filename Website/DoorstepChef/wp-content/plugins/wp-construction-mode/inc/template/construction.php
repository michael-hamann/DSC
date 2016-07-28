<?php
/*
 * 
 * @author bilal hassan <info@smartcatdesign.net>
 * Copyright Smartcat <http://smartcatdesign.net>
 */
?>
<?php extract( get_option( 'smartcat_construction_options' ) ); ?>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>
            <?php
            bloginfo( 'name' );
            $site_description = get_bloginfo( 'description' );
            ?>
        </title>
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="<?php echo $site_description; ?>">
        <meta name="author" content="">

        <link rel="stylesheet" href="<?php echo SC_CONSTRUCTION_URL . 'inc/style/style.css' ?>">
        <link rel="stylesheet" href="<?php echo SC_CONSTRUCTION_URL . 'inc/style/font-awesome.min.css' ?>">

        <link href='http://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,600,700' rel='stylesheet' type='text/css'>
        <link href='http://fonts.googleapis.com/css?family=Open+Sans:400,300,600' rel='stylesheet' type='text/css'>


        <script type="text/javascript" src="<?php echo SC_CONSTRUCTION_URL . 'inc/script/jquery-2.1.3.min.js'; ?>"></script>

        <script type="text/javascript" src="<?php echo SC_CONSTRUCTION_URL . 'inc/script/script.js'; ?>"></script>
        <?php echo stripslashes_deep( $analytics ); ?>
        
        <?php if ( $animation == 'fade' ) { ?>
            <script>
                jQuery(document).ready(function ($) {
                    $('.wuc-box').css({'display': 'none'});
                    $('.wuc-progress').css({'visibility': 'none'});
                    $('.wuc-box').delay(200).fadeIn(800, function () {
                        $('.wuc-progress').fadeIn(500, function () {
                            $('.wuc-progress-bar').animate({'width': '<?php echo $this->options[ 'percentage' ]; ?>%'}, 3000, function () {
                                $('.wuc-progress-number').fadeIn(500);
                            });
                        });
                    });
                });
            </script>
        <?php }else{ ?>
            <script>
                jQuery(document).ready(function ($) {
                    $('.wuc-progress-bar').css({width: '<?php echo $this->options[ 'percentage' ]; ?>%'}); 
                });
            </script>
       <?php } ?>
        <style>
            .wuc-overlay{ background-image: url('<?php echo $background_image; ?>'); }
            #wuc-wrapper .wuc-box h2.title,
            .wuc-box .subtitle,

            body{ font-family: 'Source Sans Pro', sans-serif; }
            body{ font-family: 'Open Sans', sans-serif; }
            .wuc-shortcode input[type=submit]{ 
                background: <?php echo $accent_color; ?>; 
                color: <?php echo $font_color; ?>;
            }
            .trigger, .wuc_icons a, #admin-login{ background: <?php echo $accent_color; ?>;}
            .wuc-shortcode input[type=text]:focus, .wuc-shortcode input[type=email]:focus{
                box-shadow: 0 0 6px <?php echo $accent_color; ?>;
                -moz-box-shadow: 0 0 6px <?php echo $accent_color; ?>;
                -webkit-box-shadow: 0 0 6px <?php echo $accent_color; ?>;
            }
            #about .wuc-box h3,
            #contact .wuc-box h3{ color: <?php echo $accent_color; ?>; border-color: <?php echo $accent_color; ?>; }
        </style>
    </head>
    <body>
        <a href="<?php echo wp_login_url(); ?>" id="admin-login" class="trigger">
            <i class="fa fa-lock"></i>
        </a>

        
    <div class='wuc-overlay'></div>
    <div id="wuc-wrapper"></div>
    
    <div class="wuc-box">
        <div class="wuc-logo center sc-col-sm-12">
            <?php if ( $logo != "" ) { ?>
                <img src="<?php echo $logo; ?>" width="200px"/>        
            <?php } ?>
        </div>        

        <h2 class="title sc-col-sm-12">
            <?php echo stripslashes( $title ); ?>
        </h2>
        <h3 class="subtitle sc-col-sm-12">
            <?php echo stripslashes( $sub_title ); ?>
        </h3>
        <?php if ( $template == 'progress' ) { ?>
            <div class="wuc-progress sc-col-sm-12">
                <div class="wuc-progress-bar"></div>
                <div class="wuc-progress-number"><?php echo $percentage; ?>%</div>
            </div>        
        <?php } else { ?>
        <?php } ?>

        <?php if ( $shortcode != '' ) : ?>
            <div class="wuc-shortcode sc-col-sm-12">
                <?php echo do_shortcode( $shortcode ); ?>
            </div>
        <?php endif; ?>                

        <div class="wuc_icons sc-col-sm-12">
            <?php if ( $facebook != '' ) { ?>
                <a href="<?php echo $facebook; ?>" target="_blank">
                    <i class="fa fa-facebook"></i>
                </a>
            <?php } ?>
            <?php if ( $gplus != '' ) { ?>
                <a href="<?php echo $gplus; ?>" target="_blank">
                    <i class="fa fa-google-plus"></i>
                </a>
            <?php } ?>
            <?php if ( $twitter != '' ) { ?>
                <a href="<?php echo $twitter; ?>" target="_blank">
                    <i class="fa fa-twitter"></i>
                </a>
            <?php } ?>
            <?php if ( $email != '' ) { ?>
                <a href="mailto:<?php echo $email; ?>" target="_blank">
                    <i class="fa fa-envelope"></i>
                </a>
            <?php } ?>
            <?php if ( $instagram != '' ) { ?>
                <a href="<?php echo $instagram; ?>" target="_blank">
                    <i class="fa fa-instagram"></i>
                </a>
            <?php } ?>
            <?php if ( $digg != '' ) { ?>
                <a href="<?php echo $digg; ?>" target="_blank">
                    <i class="fa fa-digg"></i>
                </a>
            <?php } ?>
            <?php if ( $flickr != '' ) { ?>
                <a href="<?php echo $flickr; ?>" target="_blank">
                    <i class="fa fa-flickr"></i>
                </a>
            <?php } ?>
            <?php if ( $skype != '' ) { ?>
                <a href="<?php echo $skype; ?>" target="_blank">
                    <i class="fa fa-skype"></i>
                </a>
            <?php } ?>
            <?php if ( $tumblr != '' ) { ?>
                <a href="<?php echo $tumblr; ?>" target="_blank">
                    <i class="fa fa-tumblr"></i>
                </a>
            <?php } ?>
            <?php if ( $youtube != '' ) { ?>
                <a href="<?php echo $youtube; ?>" target="_blank">
                    <i class="fa fa-youtube"></i>
                </a>
            <?php } ?>
        </div>
    </div>
</body>

</html>

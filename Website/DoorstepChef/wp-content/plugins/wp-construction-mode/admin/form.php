<?php extract(get_option('smartcat_construction_options')); ?>
<script type="text/javascript">
    jQuery(document).ready( function ($) {

        $('.wp-editor-area').attr('disabled', 'disabled')

        <?php if( $template == 'progress'){ ?>
            jQuery('.choose-progress').show();
            jQuery('.choose-time').hide();
        <?php }?>
        $('.sc-construction-menu a').click(function(){
            $('html, body').animate({
                scrollTop: $( $.attr(this, 'href') ).offset().top - 50,
            }, 500);
            return false;
        });  
    });
  
</script>
<style>

</style>

<div id="wrapper">
    <div id="gopro">
        <div class="left width70">
            <h1 class="title">WP Construction & Maintenance</h1>
            
            <div>
                <p><em>Why go pro?</em></p>
                <p>Animated countdown timer, Client login, custom contact form, more color customization options, About Us section, Contact Us section, HTML support and more</p>
            </div>
        </div>
        <div class="left">
            <div class="right">
                <a href="https://smartcatdesign.net/downloads/construction-mode-v2-pro/" target="_blank" class="button-primary" style="padding: 25px;line-height: 0;font-size: 16px">GO PRO NOW</a>
                <a href="https://smartcatdesign.net/smartcat-construction-mode-v2-pro/" target="_blank" class="button-secondary" style="padding: 25px;line-height: 0;font-size: 16px">View Demo</a>                
            </div>
            
        </div>
        <div class="right">
            
        </div>        
    </div>

    <div class="width25 right">
        <table class="widefat">
            <thead>
                <tr>
                    <th><?php _e("Support", "sc-construction") ?></th>
                </tr>
                <tr>
                    <td>
                        <?php
                        _e("Do you need support?", "sc-construction");
                        ?>
                        <a href="https://smartcatdesign.net/smartcat-products/how-to-use-wp-construction-mode-pro-plugin-for-wordpress/"><?php _e( 'Click here for instructions how to use the plugin', 'sc-construction' ); ?></a>
                        <p><br><em><strong>Troubleshooting Tip:</strong> If you are using a cache plugin, you may need to delete the cache when you switch Construction Mode On/Off</em></p>
                    </td>
                </tr>

        <table class="widefat">
            <thead>
                <tr>
                    <th><?php _e("Default Template", "sc-construction") ?></th>
                </tr>                
            </thead>                
            <tbody>
                <tr>
                    <td class='center'>
                        
                        <img src="<?php echo SC_CONSTRUCTION_URL . '/inc/img/Capture5.JPG' ?>" style="width: 100%;"/>
                        
                    </td>
                </tr>          
                </tbody>
        </table>

        <table class="widefat">
            <thead>
                <tr>
                    <th><?php _e("One Page", "sc-construction") ?></th>
                </tr>                
            </thead>                
            <tbody>
                <tr>
                    <td class='center'>
                        
                        <img src="<?php echo SC_CONSTRUCTION_URL . '/inc/img/Capture4.JPG' ?>" style="width: 100%;"/>
                        
                    </td>
                </tr>          
                </tbody>
        </table>

        <table class="widefat">
            <thead>
                <tr>
                    <th><?php _e("Moving Background", "sc-construction") ?></th>
                </tr>                
            </thead>                
            <tbody>
                <tr>
                    <td class='center'>
                        
                        <img src="<?php echo SC_CONSTRUCTION_URL . '/inc/img/Capture3.JPG' ?>" style="width: 100%;"/>
                        
                    </td>
                </tr>          
                </tbody>
        </table>

        <table class="widefat">
            <thead>
                <tr>
                    <th><?php _e("Client Login", "sc-construction") ?></th>
                </tr>                
            </thead>                
            <tbody>
                <tr>
                    <td class='center'>
                        
                        <img src="<?php echo SC_CONSTRUCTION_URL . '/screenshot-4.jpg' ?>" style="width: 100%;"/>
                        
                    </td>
                </tr>          
                </tbody>
        </table>


    </div>
    
    <div class="width70 left">
        
        <form name="post_form" method="post" action="" enctype="multipart/form-data">
            
            <table class="widefat sc-construction-menu">
                <thead>
                    <tr>
                        <td><a href="#general"><?php _e('General Settings', 'sc-construction'); ?></a></td>
                        
                        <td><a href="#appearance"><?php _e('Appearance', 'sc-construction'); ?></a></td>
                        <td><a href="#shortcode"><?php _e('Newsletters & Shortcodes', 'sc-construction'); ?></a></td>
                        <td><a href="#social"><?php _e('Social Icons', 'sc-construction'); ?></a></td>
                        <td><a href="#contact"><?php _e('Contact Form', 'sc-construction'); ?></a></td>
                        <td><a href="#login"><?php _e('Login', 'sc-construction'); ?></a></td>                        
                        <td><a href="#about"><?php _e('About Section', 'sc-construction'); ?></a></td>
                        <td><a href="#contact"><?php _e('Contact Section', 'sc-construction'); ?></a></td>
                    </tr>
                </thead>
                
            </table>
            
        <p>
            WP Construction Mode allows the site Admin & Editor profiles to continue seeing the normal website, while everyone else sees the Construction Mode page.<br>
        </p>            
            
            <table class="widefat" id="general">
                <thead>
                    <tr>
                        <th colspan="2"><span class="dashicons-admin-tools dashicons"></span> General Settings</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><?php _e('Activate Construction/Maintenance Mode') ?></td>
                        <td>    
                            <div class="toggle-mode <?php echo $mode ? 'active' : ''; ?>"></div>   
                            <input type="hidden" name="smartcat_construction_options[mode]" value="<?php echo $mode; ?>" id="sc-mode"/>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Set To Specific Page, or entire site', 'sc-construction') ?></td>
                        <td>
                            <select name="smartcat_construction_options[set_page]" id="set_page">
                                <option value="all"><?php _e('Entire Website') ?></option>
                                <?php
                                $pages = get_pages();
                                foreach ($pages as $page) {
                                    $option = "<option value= '" . $page->ID . "'";
                                    if ($set_page == $page->ID)
                                        $option .= "selected";
                                    $option .= ">";
                                    $option .= $page->post_title;
                                    $option .= '</option>';
                                    echo $option;
                                }
                                ?>

                            </select>    
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Loading Style') ?></td>
                        <td>
                            <select name="smartcat_construction_options[template]" id="wuc_loading">
                                <option disabled="">Countdown Timer - Pro Version</option>
                                <option value="progress" <?php selected ($template, 'progress'); ?>>Progress Bar</option>
                                <option value="none" <?php selected ($template, 'none'); ?>>None</option>
                            </select>

                        </td>
                    </tr>
                    <tr class="choose-progress">
                        <td><?php _e('Percentage Complete') ?></td>
                        <td>
                            <select name="smartcat_construction_options[percentage]">
                                <option value="10" <?php selected ($percentage == '10'); ?>>10%</option>
                                <option value="20" <?php selected ($percentage == '20'); ?>>20%</option>
                                <option value="30" <?php selected ($percentage == '30'); ?>>30%</option>
                                <option value="40" <?php selected ($percentage == '40'); ?>>40%</option>
                                <option value="50" <?php selected ($percentage == '50'); ?>>50%</option>
                                <option value="60" <?php selected ($percentage == '60'); ?>>60%</option>
                                <option value="70" <?php selected ($percentage == '70'); ?>>70%</option>
                                <option value="80" <?php selected ($percentage == '80'); ?>>80%</option>
                                <option value="90" <?php selected ($percentage == '90'); ?>>90%</option>
                            </select>
                        </td>
                    </tr>  
                    <tr>
                        <td><?php _e( 'Analytics Code', 'sc-construction' ); ?></td>
                        <td>
                            <textarea name="smartcat_construction_options[analytics]" style="width: 100%" rows="7" placeholder="<?php _e( 'Enter your full Analytics code including the <script> tags', 'sc-construction') ?>"><?php echo isset( $analytics ) ? stripslashes_deep( esc_textarea( $analytics ) ) : ''; ?></textarea>
                        </td>
                    </tr>                    
            </table>      
        
            <table class="widefat" id="appearance">
                <thead>
                    <tr>
                        <th colspan="2"><span class="dashicons-admin-appearance dashicons"></span> Appearance</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><?php _e('Select Template') ?></td>
                        <td>
                            <select name ="smartcat_construction_options[display_template]" id="smartcat_construction_select_template">
                                <option value="template1">Default</option>
                                <option disabled="disabled">One Page - Pro Version</option>
                                <option disabled="disabled">Moving Background - Pro Version</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Logo') ?></td>
                        <td>
                            
                            <input id="image_location" type="text" name="smartcat_construction_options[logo]" value="<?php echo esc_url( $logo ); ?>" size="50" />
                            <input  class="onetarek-upload-button button" type="button" value="Upload Image" />
                            
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Background Image') ?></td>
                        <td>
                            <input id="image_location" type="text" name="smartcat_construction_options[background_image]" value="<?php echo esc_url( $background_image ); ?>" size="50" />
                            <input  class="onetarek-upload-button button" type="button" value="Upload Image" />                            
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Title', 'sc-construction') ?></td>
                        <td>
                            <textarea name="smartcat_construction_options[title]" id="set_msg" style="width: 90%"><?php echo stripslashes( esc_textarea($title) ); ?></textarea>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Sub-title', 'sc-construction') ?></td>
                        <td>
                            <textarea  name="smartcat_construction_options[sub_title]" id="set_caption" style="width: 50%"><?php echo stripslashes( esc_textarea($sub_title) ); ?></textarea>   

                        </td>
                    </tr>
                    
                    <tr>
                        <td><?php _e( 'Font Color', 'sc-construction'); ?></td>
                        <td>
                            <input type="text" value="<?php echo $font_color; ?>" class="smartcat_construction_font_color_picker" name="smartcat_construction_options[font_color]"/>
                        </td>
                        
                    </tr>
                    
                    <tr>
                        <td><?php _e( 'Accent Color', 'sc-construction'); ?></td>
                        <td>
                            <input type="text" value="<?php echo $accent_color; ?>" class="smartcat_construction_font_color_picker" name="smartcat_construction_options[accent_color]"/>
                        </td>
                        
                    </tr>

                    <tr>
                        <td><?php _e('Animation', 'sc-construction') ?></td>
                        <td>
                            <select name="smartcat_construction_options[animation]">
                                <option value="">None</option>
                                <option disabled="">Slide In - Pro Version</option>
                                <option value="fade" <?php echo $animation == 'fade' ? 'selected' : ''; ?>>Fade In</option>
                            </select>
                        </td>
                    </tr>   
                    
                <tr>
                    <td>
                        
                    </td>
                </tr>
                    
                    
                </tbody>
            </table>

            <table class="widefat" id="shortcode">
                <thead>
                    <tr>
                        <th colspan="2">
                            Newsletter / Contact Form Shortcode 
                            <img 
                                src="<?php echo SC_CONSTRUCTION_URL . '/inc/img/mailchimp.png'; ?>"
                                style="width: 20px;">
                            <img 
                                src="<?php echo SC_CONSTRUCTION_URL . '/inc/img/constant.png'; ?>"
                                style="width: 30px;">
                            <img 
                                src="<?php echo SC_CONSTRUCTION_URL . '/inc/img/gravity.png'; ?>"
                                style="width: 30px;">
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><?php _e('Shortcode', 'sc-construction'); ?></td>
                        <td>
                            <input class="wuc_shortcode" type="text" name="smartcat_construction_options[shortcode]" value="<?php echo esc_attr($shortcode); ?>" placeholder="Enter any Shortcode here"/>
                        </td>
                    </tr>
                    <tr>
                        <td colspan="2">
                            You can enter any shortcode here, such as mailchimp, contact form 7, gravity forms, icon shortcodes or anything you want!
                        </td>
                    </tr>
                </tbody>

            </table>


            <table class="widefat" id="social">
                <thead>
                    <tr>
                        <th colspan="2"><span class="dashicons dashicons-share"></span> <?php _e( 'Social Icons', 'sc-construction'); ?></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><?php _e('Facebook URL', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[facebook]" 
                                   value="<?php echo esc_url($facebook); ?>" placeholder="<?php _e('Enter Facebook URL or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Google Plus URL', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[gplus]" 
                                   value="<?php echo esc_url($gplus); ?>" placeholder="<?php _e('Enter Google Plus URL or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Twitter URL', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[twitter]" 
                                   value="<?php echo esc_url($twitter); ?>" placeholder="<?php _e('Enter Twitter URL or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Email Address', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[email]" 
                                   value="<?php echo esc_attr($email); ?>" placeholder="<?php _e('Enter email address or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e('Instagram', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[instagram]" 
                                   value="<?php echo esc_attr($instagram); ?>" placeholder="<?php _e('Enter Instagram URL or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>                    
                    <tr>
                        <td><?php _e('Digg', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[digg]" 
                                   value="<?php echo esc_attr($digg); ?>" placeholder="<?php _e('Enter Digg URL or leave blank for no icon'); ?>" />
                        </td>
                    </tr>                    
                    <tr>
                        <td>Flickr</td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[flickr]" 
                                   value="<?php echo esc_attr($flickr); ?>" placeholder="<?php _e('Enter Flickr URL or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>                    
                    <tr>
                        <td><?php _e('Skype', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[skype]" 
                                   value="<?php echo esc_attr($skype); ?>" placeholder="<?php _e('Enter Skype URL or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>                    
                    <tr>
                        <td><?php _e('Tumblr', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[tumblr]" 
                                   value="<?php echo esc_attr($tumblr); ?>" placeholder="<?php _e('Enter Tumblr URL or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>                    
                    <tr>
                        <td><?php _e('Youtube', 'sc-construction') ?></td>
                        <td>
                            <input type="text" 
                                   name="smartcat_construction_options[youtube]" 
                                   value="<?php echo esc_attr($youtube); ?>" placeholder="<?php _e('Enter Youtube URL or leave blank for no icon'); ?>"/>
                        </td>
                    </tr>                    
                </tbody>
            </table>
            
        
            <table class="widefat" id="login">
                <thead>
                    <tr>
                        <th colspan="2">
                            <span class="dashicons dashicons-lock"></span> <?php _e( 'Client Login', 'sc-construction' ); ?> - <em class="pro">Pro Version</em>
                        </th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td colspan="2"><em><?php _e( 'Use this feature to allow your client & visitors unique access with a username & password', 'sc-construction') ?></em></td>
                    </tr>
                    <tr>
                        <td><?php _e( 'Enable Client Login ?', 'sc-contruction' ); ?></td>
                        <td>
                            <input type="radio" value="on" disabled="disabled"/> <?php _e( 'Yes', 'sc-conctruction' ); ?><br>
                            <input checked="checked" type="radio" value="off" checked="checked" disabled="disabled"/> <?php _e( 'No', 'sc-conctruction' ); ?> 
                        </td>
                    </tr>
                    
                    <tr>
                        <td><?php _e( 'Username', 'sc-construction'); ?></td>
                        <td><input type="text" value="client" disabled="disabled"/></td>
                    </tr>
                    
                    <tr>
                        <td><?php _e( 'Password', 'sc-construction'); ?></td>
                        <td><input type="text" value="preview" disabled="disabled" /></td>
                    </tr>
                    
                </tbody>
            </table> 
        
            <table class="widefat" id="contact">
                <thead>
                    <tr>
                        <th colspan="2"><span class="dashicons dashicons-email"></span> <?php _e( 'Custom Contact Form', 'sc-construction' ); ?> - <em class="pro">Pro Version</em></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td>
                            <?php _e( 'Display Contact form ? ', 'sc-construction' ); ?>
                        </td>
                        <td>
                            <input type="radio" value="on" disabled="disabled"/> Yes <br>
                            <input type="radio" value="off" disabled="disabled" checked="checked"/> No <br>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e( 'Delivery Email Address', 'sc-construction' ); ?></td>
                        <td>
                            <input type="text" value="<?php echo get_option('admin_email'); ?>" disabled="disabled"/>
                        </td>
                    </tr>
                </tbody>
                
            </table>          
        
            <table class="widefat" id="about">
                <thead>
                    <tr>
                        <th colspan="2"><strong><span class="dashicons-info dashicons"></span> <?php _e('Section 1 - <em>ex: About Us</em>', 'sc-construction'); ?></strong> - <em class="pro">Pro Version</em></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td><?php _e('Show Section 1 ?', 'sc-construction'); ?></td>
                        <td>
                            <input type="radio" disabled="disabled" /> <?php _e( 'Yes', 'sc-construction') ?>
                            <input type="radio" disabled="disabled" checked="checked" /> <?php _e( 'No', 'sc-construction') ?>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e( 'Label', 'sc-construction' ); ?></td>
                        <td>
                            <input type="text" value="About Us" disabled="disabled"/>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <em></em>
                        </td>
                        <td>
                            <?php wp_editor( '', 'smartcat_about', array(
                                'textarea_name'     => 'smartcat_construction_options[about]',
                                'tinymce'           => false,
                                'media_buttons'     => false,
                            ) ); ?> 
                        </td>   
                    </tr>
                    </tbody>
                
            </table>
            
            <table class="widefat" id="contact">
                <thead>
                    <tr>
                        <th colspan="2"><strong><span class="dashicons dashicons-location"></span> <?php _e('Section 2 - <em>ex: Location</em>', 'sc-construction'); ?></strong> - <em class="pro">Pro Version</em></th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td><?php _e('Show Section 2 ?', 'sc-construction'); ?></td>
                        <td>
                            <input type="radio" name="" disabled="disabled"/> <?php _e( 'Yes', 'sc-construction') ?>
                            <input type="radio" name="" disabled="disabled" checked="checked"/> <?php _e( 'No', 'sc-construction') ?>
                        </td>
                    </tr>
                    <tr>
                        <td><?php _e( 'Label', 'sc-construction' ); ?></td>
                        <td>
                            <input type="text" name="" value="Contact Us" disabled="" />
                        </td>
                    </tr>                    
                    <tr>
                        <td><em></em></td>
                        <td>
                            
                            <?php wp_editor( '', 'smartcat_contact', array(
                                'textarea_name'     => '',
                                'tinymce'           => false,
                                'media_buttons'     => false,
                            ) ); ?>
                            
                        </td>   
                    </tr>
                   
                </tbody>
            </table>
        
        
            <div style="text-align: right">
                <a target="_blank" href="<?php echo home_url('/'); ?>?smartcat_construction=preview" class="button button-default" style="padding: 17px 22px; line-height: 0">Preview</a>    
                <input type="submit" name="smartcat_construction_save" value="Update" class="button button-primary" style="padding: 17px 22px; line-height: 0"/>
            </div>
            <!--<input type="hidden" name="act" value="save" />-->
        </form>

    </div>

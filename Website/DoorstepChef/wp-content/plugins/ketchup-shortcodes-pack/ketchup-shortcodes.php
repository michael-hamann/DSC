<?php
/*
Plugin Name: Ketchup Shortcodes Pack
Description: This plugin enables some nice shortcodes for use with a theme.
Version: 0.1.2
Author: Alex Itsios
Author URI: http://ketchupthemes.com
*/
DEFINE ('PLUGIN_PATH',plugin_dir_path( __FILE__ ));
/*------------------------*/
/*      Shortcodes        */
/*------------------------*/
add_action('init','ketchup_shortcodes_register');
function ketchup_shortcodes_register(){
    add_shortcode('spacer','ketchup_spacer_shortcode');
    add_shortcode('fullwidth_background','ketchup_fullwidth_background_shortcode');
    add_shortcode('title_and_subtitle','ketchup_titles_shortcode');
    add_shortcode('content_block','ketchup_content_block_shortcode');
    add_shortcode('blog_post','ketchup_blog_post_shortcode');
}
function ketchup_spacer_shortcode($atts){
     extract(shortcode_atts(array(
    'margin_top'=>'10px',
    'margin_bottom'=>'10px'
    ),$atts));
    
    $html = '<div class="ketchup_spacer" style="margin-top:'.$margin_top.'; margin-bottom:'.$margin_bottom.';"></div>';
    
    return $html;
    
}
/*-------------------------*/
/* Background Shortcodes   */
/*-------------------------*/
function ketchup_background_ret($background_color=null, $background_url=null){
    $bg_type = '';
    if($background_url != ''){
        $bg_type = 'bg';
    }
    elseif($background_color != ''){
        $bg_type = 'color';
    }
    return $bg_type;
}
function ketchup_fullwidth_background_shortcode($atts,$content = null){
    extract(shortcode_atts(array(
    'background_url'=>'',
    'background_color'=>''
    ),$atts));
    
    $bg_type = ketchup_background_ret($background_color,$background_url);
    
    $html = '';
    
    if($bg_type == 'color'):
    $html .='<div class="ketchup_fullwidth_bg" style="background:'.$background_color.'">';
    elseif($bg_type == 'bg'):
    $html .='<div class="ketchup_fullwidth_bg" style="background:url('.$background_url.')">';
    else:
    $html .='<div class="ketchup_fullwidth_bg">';
    endif;
    
    $html .='<div class="container">';
    $html .= do_shortcode($content);
    $html .= '</div>';
    $html .= '</div>';
   
    
    
    return $html;
}
/*--------------------------------*/
/* Title and Subtitle Shortcodes  */
/*--------------------------------*/
function ketchup_titles_shortcode($atts){
    extract(shortcode_atts(array(
    'title'=>'',
    'subtitle'=>''
    ),$atts));
    
    $html .= '<h1 class="ketchup_section_title">'.$title.'</h1>';
    $html .= '<h4 class="ketchup_section_subtitle">'.$subtitle.'</h4>';
    
    return $html;
    
}
/*---------------------------*/
/* Content Block Shortcodes  */
/*---------------------------*/
function ketchup_content_block_shortcode($atts){
   extract(shortcode_atts(array(
    'block_title'=>'',
    'block_css_class'=>'',
    'block_text'=>'',
    'block_text_color'=>'#000000',
    'block_button_text'=>'',
    'block_button_link'=>'',
    'block_button_css'=>'',
    'block_button_color'=>'',
    'block_image'=>''
   ),$atts));  
   
   $html = '<div class="ketchup_block_content '.$block_css_class.'">';
    if($block_image != ''): 
        $html .= '<img class="img-responsive" src="'.$block_image.'"/>'; 
    endif;
   $html .= '<h2>'.$block_title.'</h2>';
   $html .= '<p style="color:'.$block_text_color.';">'.$block_text.'</p>';
   $html .= '<a href="'.$block_button_link.'" class="'.$block_button_css.'" style="color:'.$block_button_color.'">'.$block_button_text.'</a>';
   $html .= '</div>';
   return $html;
}
/*-------------------------*/
/* Blog Post Shortcode     */
/*-------------------------*/
function ketchup_blog_post_shortcode($atts){
  extract(shortcode_atts(array(
    'post_id'=>'',
    'post_css_class'=>'',
    'post_font_color'=>'#000000',
    'post_read_more'=>''
  ),$atts));
  
  global $wpdb,$post;
  $query_args = array('post_type'=>'post', 
  'post__in'=>array($post_id),
  'posts_per_page'=>1,
  'ignore_sticky_posts'=>true);
  
  $post_query = new WP_Query($query_args); 
  if($post_query->have_posts()):while($post_query->have_posts()):$post_query->the_post();
   $picture_url = wp_get_attachment_image_src( get_post_thumbnail_id( $post->ID ), ' ' );
  $html =  '<div class="ketchup_blog_post '.$post_css_class.'">';
  $html .= '<img class="img-responsive" src="'.$picture_url[0].'"/>';
  $html .= '<h2><a href="'.get_the_permalink().'">'.get_the_title().'</a></h2>'; 
  $html .= '<p style="color:'.$post_font_color.';">'.get_the_excerpt().'</p>';
  endwhile;
  
  else:
  $html ='<div class="no-posts">No posts with this ID found</div>';
       
  endif; wp_reset_query();
  $html .='</div>';
  return $html;
}
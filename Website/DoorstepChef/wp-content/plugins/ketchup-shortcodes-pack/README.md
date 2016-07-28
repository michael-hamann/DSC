=== Ketchup Shortcodes ===
Contributors: alexitsios
Tags: shortcodes, custom shortcodes
Requires at least: 3.5.1
Tested up to: 4.0
Stable tag:0.1.1 trunk
License: GPLv2 or later
License URI: http://www.gnu.org/licenses/gpl-2.0.html

A simple plugin that creates a pack of shortcodes available for use with a theme.

== Description ==

A very simple plugin that creates five highly customisable shortcodes that can be used with any theme.

== Installation ==

This section describes how to install the plugin and get it working.

1. Upload the `ketchup-shortcodes` folder to the `/wp-content/plugins/` directory
2. Activate the plugin through the 'Plugins' menu in WordPress
3. Shortcodes are ready for you to use them!

== Documentation ==

- Available Shortcodes

    [spacer margin_top="10px" margin_bottom="10px"]
    -- margin_top,margin_bottom are not required
    
    [fullwidth_background background_color="#efefef" or background_url="{image_url}"]...[/fullwidth_background]
    -- Only for use with frontpage - full width
    
    [title_and_subtitle title="title goes here" subtitle="subtitle goes here"]
    
    [content_block block_title="" block_css_class="" block_text="" block_text_color = "#000000" block_button_css="" block_button_text="" block_button_link="" block_image="" block_button_color=""]
    -- For general content blocks

    [blog_post post_id="id" post_css_class="" post_font_color="#000000" post_read_more="read more text"]
    -- You can get the id of a post from dashboard
    
== Changelog ==

= 0.1.0 =
* First release.
= 0.1.1 =
 - Correct Documentation
 - Added Shortcode Variables
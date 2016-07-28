<?php $menu_post = get_post( $post_id ); ?>

<div class="erm_menu <?php echo ( $show_thumbnails ? '' : 'no-thumbs' ); ?>">

    <h1 class="erm_title"><?php echo $menu_post->post_title; ?></h1>
    <div class="erm_desc"><?php echo apply_filters('the_content', $menu_post->post_content); ?></div>

    <ul class="erm_menu_content">
        <?php

            // Thumb size
            $option_thumb_size = ERM()->settings->get('erm_menu_thumb_size');
            if (empty($option_thumb_size)) $option_thumb_size = 'medium';

            // Menu items
            $menu_items = get_post_meta( $post_id, '_erm_menu_items', true );
            if ( !empty($menu_items) ) {

                $menu_items = preg_split('/,/', $menu_items);

                foreach( $menu_items as $item_id ) {

                    // Visible item
                    if ( get_post_meta( $item_id, '_erm_visible', true ) != 1 ) continue;

                    // Get the post item
                    $the_post = get_post( $item_id );
                    $type = get_post_meta( $item_id, '_erm_type', true );

                    switch( $type ) {
                        case 'section':
                            include( 'menu-item-section.php' );
                            break;
                        case 'product':
                            include( 'menu-item-product.php' );
                            break;
                        default:
                            break;
                    }
                }
            }
        ?>
    </ul>

    <div class="erm_footer_desc"><?php echo apply_filters('the_content', get_post_meta( $menu_post->ID, '_erm_footer_menu', true )); ?></div>

</div>

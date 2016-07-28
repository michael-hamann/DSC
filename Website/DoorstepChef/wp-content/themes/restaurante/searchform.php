<form action="<?php echo esc_url(home_url( '/' )); ?>" method="get" class="form-inline">
    <fieldset>
        <div class="input-group">
            <input type="text" name="s" id="search" placeholder="<?php _e("Search",
                "restaurante"); ?>" value="<?php the_search_query(); ?>"
                   class="form-control" />

            <span class="input-group-btn">
            <button type="submit" class="btn btn-default"><i class="fa fa-search"></i></button>
            </span>
        </div>
    </fieldset>
</form>

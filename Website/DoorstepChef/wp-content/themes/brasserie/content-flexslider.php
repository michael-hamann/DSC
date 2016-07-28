	<div class="flex-container">
		<div class="flexslider">
			<ul class="slides">
				<?php 
					$the_query = new WP_Query(array(
						'category_name' => 'featured', 'posts_per_page' => 6
					));
				?>
				<?php while ($the_query -> have_posts()) : $the_query -> the_post(); ?>
				<li>
					<?php the_post_thumbnail(); ?>
					<div class="caption_wrap">
						<div class="flex-caption">
							<div class="flex-caption-title"><h3>
								<a href="<?php the_permalink() ?>"><?php the_title(); ?></a></h3>
							</div>
								<p><?php echo brasserie_get_slider_excerpt(); ?><a href="<?php the_permalink() ?>">...</a></p>
								<div class="flex-more-link"><a href="<?php the_permalink() ?>"><?php echo __('Read More', 'brasserie'); ?></a></div>
						</div>
					</div>
				</li>
				<?php
				endwhile;
				?>
			</ul>
		</div>
	</div>
<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class Stats
{
    public static function iniliatialize(){
        ?>
        <section ng-module="Statistics" xmlns="http://www.w3.org/1999/html">
            <div class="seo-table seo-statistics" ng-controller="StatsMainCtrl as statistics">


                <dl ng-cloak>
                    <dt><h3><?php _e('Nofollow',_SEOCONS_TEXTDOMAIN) ?>/<?php _e('Dofollow', _SEOCONS_TEXTDOMAIN) ?></h3></dt>
                    <dd>{{followstats.NOFOLLOW}}% / {{followstats.FOLLOW}}%</dd>
                    <dt><h3><?php _e('Backlinks',_SEOCONS_TEXTDOMAIN) ?></h3></dt>
                    <dd>{{linksQnt}}</dd>
                    <dt><h3><?php _e('Backlinks Lost',_SEOCONS_TEXTDOMAIN) ?></h3></dt>
                    <dd>{{LostBackLinks}}</dd>
                    <dt><h3><?php _e('Referring Domains',_SEOCONS_TEXTDOMAIN) ?></h3></dt>
                    <dd>{{domainsQnt}}</dd>
                    <dt><h3><?php _e('Referring Domains Lost',_SEOCONS_TEXTDOMAIN) ?></h3></dt>
                    <dd>{{LostRefDomains}}</dd>
                </dl>

                <div class="seo-widget-controls">
                    <a href="<?php echo admin_url(); ?>?seocons-page=statistics&type=pdf" class="button-primary" ><?php _e('Download', _SEOCONS_TEXTDOMAIN); ?><i style="padding-left: .5em" class="fa fa-file-pdf-o"></i></a>
                    <a href="<?php echo admin_url(); ?>?seocons-page=statistics&type=csv" class="button-primary" ><?php _e('Download', _SEOCONS_TEXTDOMAIN); ?><i style="padding-left: .5em" class="fa fa-file-excel-o"></i></a>
                </div>

                <ask-button></ask-button>
            </div>
        </section>
        <?php
    }


}
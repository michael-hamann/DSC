<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class AnchorTextDistribution
{
    public static function iniliatialize(){

        ?>
        <section ng-module="AnchorDistribution">
            <div class="seo-table achors-distribution" ng-controller="AnchorDistributionMainCtrl as Anchor">
                <header class="container">
                    <nav class="container-row">

                        <!--                    headers width filters-->
                        <h4 class="container-cell table-filter">
                            <a ng-click="changeOrder('ANCHOR_TEXT')"><?php _e('Anchor Text', _SEOCONS_TEXTDOMAIN) ?></a>
                        </h4>
                        <h4 class="container-cell table-filter">
                            <a ng-click="changeOrder('RATIO')"><?php _e('Ratio', _SEOCONS_TEXTDOMAIN) ?></a>
                        </h4>
                    </nav>
                </header>
                <div class="wrapper">
                    <div class="container" ng-cloak>
                        <div ng-repeat="anchor in distribution | orderBy: column" ng-class="{'container-row': true, 'light': $even, 'animate-repeat' : true}" >
                            <span class="container-cell">{{(anchor.ANCHOR_TEXT==''||anchor.ANCHOR_TEXT==null) ? 'empty' : anchor.ANCHOR_TEXT}}</span>
                            <span class="container-cell">{{anchor.RATIO}}</span>
                        </div>
                    </div>
                </div>
                <div class="seo-widget-controls">
                    <a href="<?php echo admin_url(); ?>?seocons-page=anchors&type=pdf" class="button-primary" ><?php _e('Download', _SEOCONS_TEXTDOMAIN); ?><i style="padding-left: .5em" class="fa fa-file-pdf-o"></i></a>
                    <a href="<?php echo admin_url(); ?>?seocons-page=anchors&type=csv" class="button-primary" ><?php _e('Download', _SEOCONS_TEXTDOMAIN); ?><i style="padding-left: .5em" class="fa fa-file-excel-o"></i></a>
                </div>
                <ask-button></ask-button>
            </div>
        </section>
        <?php
    }
}
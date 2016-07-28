<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class ReferringPages
{
    public static function iniliatialize()
    {
        ?>
        <section ng-module="ReferringPages">
            <div class="seo-table reflinks" ng-controller="ReferringPagesMainCtrl as pages">
                <header class="container">
                    <nav class="container-row">

                        <!--                    headers width filters-->
                        <h4 class="container-cell table-filter">
                            <a ng-click="changeOrder('REF_LINK')"><?php _e('Referring Link', _SEOCONS_TEXTDOMAIN) ?></a>
                        </h4>
                        <h4 class="container-cell table-filter">
                            <a ng-click="changeOrder('ANCHOR_TEXT')"><?php _e('Anchor Text', _SEOCONS_TEXTDOMAIN) ?></a>
                        </h4>
                        <h4 class="container-cell  table-filter">
                            <a ng-click="changeOrder('NO_FOLLOW')"><?php _e('Follow', _SEOCONS_TEXTDOMAIN) ?></a>
                        </h4>
                    </nav>
                </header>
                <div class="wrapper">
                    <div class="container" ng-cloak>
                        <div ng-repeat="log in logs | orderBy: column" ng-class="{'container-row': true, 'light': $even}" >
                            <span class="container-cell"><a ng-href="{{log.REF_LINK}}">{{log.REF_LINK}}</a></span>
                            <span class="container-cell">{{(log.ANCHOR_TEXT==''||log.ANCHOR_TEXT==null) ? 'empty' : log.ANCHOR_TEXT}}</span>
                            <span class="container-cell">
                                <i class="fa fa-ban" ng-show="log.NO_FOLLOW==0"></i>
                                <i class="fa fa-check" ng-show="log.NO_FOLLOW==1"></i>
                            </span>
                        </div>
                    </div>
                </div>
                <div class="seo-widget-controls">
                    <a href="<?php echo admin_url(); ?>?seocons-page=pages&type=pdf" class="button-primary" ><?php _e('Download', _SEOCONS_TEXTDOMAIN); ?><i style="padding-left: .5em" class="fa fa-file-pdf-o"></i></a>
                    <a href="<?php echo admin_url(); ?>?seocons-page=pages&type=csv" class="button-primary" ><?php _e('Download', _SEOCONS_TEXTDOMAIN); ?><i style="padding-left: .5em" class="fa fa-file-excel-o"></i></a>
                </div>
                <ask-button></ask-button>
            </div>
        </section>
        <?php
    }
}
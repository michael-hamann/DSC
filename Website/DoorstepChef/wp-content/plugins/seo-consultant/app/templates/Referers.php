<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class Referers
{
    public static function iniliatialize()
    {
        add_thickbox();
        ?>
        <section ng-module="Referrers">
            <div class="seo-table" ng-controller="ReferrersMainCtrl as referrers">
                <header class="container">
                    <nav class="container-row">

<!--                    headers width filters-->
                        <h4 class="container-cell table-filter">
                            <a ng-click="changeOrder('ID')"><?php _e('Links', _SEOCONS_TEXTDOMAIN) ?></a>
                        </h4>
                        <h4 class="container-cell table-filter">
                            <a ng-click="changeOrder('DOMAIN_NAME')"><?php _e('Domain Name', _SEOCONS_TEXTDOMAIN) ?></a>
                        </h4>

<!--                    Controls Header-->
                        <h4 class="container-cell"><?php _e('IP', _SEOCONS_TEXTDOMAIN) ?></h4>
                        <h4 class="container-cell"><?php _e('Action', _SEOCONS_TEXTDOMAIN) ?></h4>
                    </nav>
                </header>
                <div class="wrapper">
                    <div class="container" ng-cloak>
                        <div ng-repeat="referrer in domains | orderBy: column" ng-class="{'container-row': true, 'light': $even, 'animate-repeat' : true}" >
                            <span class="container-cell">{{(referrer.ID)? referrer.OCCURS : ''}}</span>
                            <span class="container-cell">{{referrer.DOMAIN_NAME}}</span>
                            <span class="container-cell">{{referrer.IP}}</span>
                        <span class="controls container-cell">
                            <i title="delete" class="delete fa fa-trash" ng-click="del(referrer.ID)"></i>
                            <i title="block" class="block fa fa-ban" ng-click="block(referrer.ID)"></i>

                            <a href="#TB_inline?width=600&height=200&inlineId=referrer-info" id="infobox" class="thickbox" style="display: none"></a>
                        </span>
                        </div>
                    </div>
                </div>

                <div class="seo-widget-controls">
                    <a href="<?php echo admin_url(); ?>?seocons-page=domains&type=pdf" class="button-primary" ><?php _e('Download', _SEOCONS_TEXTDOMAIN); ?><i style="padding-left: .5em" class="fa fa-file-pdf-o"></i></a>
                    <a href="<?php echo admin_url(); ?>?seocons-page=domains&type=csv" class="button-primary" ><?php _e('Download', _SEOCONS_TEXTDOMAIN); ?><i style="padding-left: .5em" class="fa fa-file-excel-o"></i></a>
                </div>
                <ask-button></ask-button>
<!--                Thickbox-->
                <div id="referrer-info" style="display:none;">
                    <header>
                        <h1>{{domain_id}}.{{heading}}</h1>
                        <h3>{{subheading}}</h3>
                    </header>
                    <p>

                    </p>
                </div>
            </div>
        </section>
        <?php
    }


}
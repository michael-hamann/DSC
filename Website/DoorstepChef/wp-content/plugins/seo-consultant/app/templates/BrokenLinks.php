<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class BrokenLinks
{
    public static function iniliatialize(){
     ?>
        <section ng-module="BrokenLinks">
            <div class="seo-table" ng-controller="BrokenLinksMainCtrl as brokenlinks">
                <header class="container" ng-cloak>
                    <nav class="container-row">

                        <!--                    headers width filters-->
                        <h4 class="container-cell table-filter">
                            <a ng-show="!NoBrokenLinksMessage" ng-click="changeOrder('BROKEN_LINK')"><?php _e('Broken', _SEOCONS_TEXTDOMAIN) ?></a>
                        </h4>
                    </nav>
                </header>
                <div class="wrapper">
                    <h3 ng-show="!!NoBrokenLinksMessage">{{NoBrokenLinksMessage}}</h3>
                    <div ng-show="!NoBrokenLinksMessage">
                        <div ng-repeat="link in links">
                            <span>{{link.broken}}</span>
                        </div>
                    </div>
                </div>
                <ask-button></ask-button>
            </div>
        </section>
        <?php
    }
}
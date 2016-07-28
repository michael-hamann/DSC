<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class BlockedIps
{
    public static function iniliatialize(){
//        $db  = \SeoConsultant\Database::getInstance();
//        $blocked_ips = $db->getIPs(array('blocked'));
        ?>
        <section ng-module="BlockedIPs">
            <div class="seo-table" ng-controller="BlockedIPsMainCtrl as blocked">
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
                            <i title="unblock" class="unblock fa fa-unlock" ng-click="unblock(referrer.ID)"></i>
                        </span>
                        </div>
                    </div>
                </div>
                <ask-button></ask-button>
            </div>
        </section>
        <?php
    }
}
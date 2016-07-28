<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;

class Dashboard
{
    public function initializeWidgets(){
        add_action('wp_dashboard_setup', array('SeoConsultant\Dashboard', 'displayReferers'));
        add_action('wp_dashboard_setup', array('SeoConsultant\Dashboard', 'displayBlockedIps'));
        add_action('wp_dashboard_setup', array('SeoConsultant\Dashboard', 'displayBrokenLinks'));
        add_action('wp_dashboard_setup', array('SeoConsultant\Dashboard', 'displayStats'));
        add_action('wp_dashboard_setup', array('SeoConsultant\Dashboard', 'displayReferringPages'));
        add_action('wp_dashboard_setup', array('SeoConsultant\Dashboard', 'displayAnchorDistribution'));
    }

    public static function displayReferers(){
        wp_add_dashboard_widget(
            'http_referers',
            __('Referring Domains',_SEOCONS_TEXTDOMAIN),
            array('SeoConsultant\Referers', 'iniliatialize')
        );
    }

    public static function displayReferringPages(){
        wp_add_dashboard_widget(
            'http_referring_pages',
            __('Referring Pages',_SEOCONS_TEXTDOMAIN),
            array('SeoConsultant\ReferringPages', 'iniliatialize')
        );
    }

    public static function displayBlockedIps(){
        wp_add_dashboard_widget(
            'blocked_ips',
            __('Blocked Referring Domains',_SEOCONS_TEXTDOMAIN),
            array('SeoConsultant\BlockedIps', 'iniliatialize')
        );
    }

    public static function displayBrokenLinks(){
        wp_add_dashboard_widget(
            'broken_links',
            __('Broken Links',_SEOCONS_TEXTDOMAIN),
            array('SeoConsultant\BrokenLinks', 'iniliatialize')
        );
    }

    public static function displayStats(){
        wp_add_dashboard_widget(
            'stats',
            __('SEO Stats',_SEOCONS_TEXTDOMAIN),
            array('SeoConsultant\Stats', 'iniliatialize')
        );
    }

    public static function displayAnchorDistribution(){
        wp_add_dashboard_widget(
            'anchor_distribution',
            __('Anchor Distribution',_SEOCONS_TEXTDOMAIN),
            array('SeoConsultant\AnchorTextDistribution', 'iniliatialize')
        );
    }
}
<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class Settings
{
    public static function registerPage(){
        $page = 'options-general.php';
        if (is_multisite()) {
            $page = 'settings.php';
        }
        add_submenu_page($page, __('SEO Consultant', _SEOCONS_TEXTDOMAIN), __('SEO Consultant', _SEOCONS_TEXTDOMAIN), 'manage_options', 'seocons_settings', array('SeoConsultant\Settings', 'pageTemplate' ));

        add_action('admin_init', array('SeoConsultant\Settings', 'pageSettings'));
    }

    public static function pageSettings(){
        register_setting('seocons_settings', 'check-links');
        register_setting('seocons_settings', 'enable-htaccess-auto-rewrite');
        register_setting('seocons_settings', 'check-live-link');
        register_setting('seocons_settings', 'disable-widgets');
    }

    public static function pageTemplate(){
        ?>
        <div class="seo-consultant-settings">
            <header>
                <h1>SEO Consultant Settings</h1>
            </header>

            <div role="dialog" class="seo-consultant-form-container">
                <form id="seo-consulant-settings-form" name="settings" method="post" action="options.php">
                    <?php
                    settings_fields('seocons_settings');
                    do_settings_sections('seocons_settings');
                    ?>

                    <fieldset>
                        <label><?php _e('Check Broken Links', _SEOCONS_TEXTDOMAIN) ?></label>
                        <input type="checkbox" name="check-links" value="1" <?php checked( get_option('check-links', false), true, true); ?>>
                    </fieldset>

                    <fieldset>
                        <label><?php _e('Enable HTACCESS Auto rewrite', _SEOCONS_TEXTDOMAIN) ?></label>
                        <input type="checkbox" name="enable-htaccess-auto-rewrite" value="1" <?php checked( get_option('enable-htaccess-auto-rewrite', false), true, true); ?>>
                        <p><?php _e('If this is enabled any custom HTACCESS rewrite rule, not defined with wordpress api, will be ovewritten',_SEOCONS_TEXTDOMAIN) ?></p>
                    </fieldset>

                    <fieldset>
                        <label><?php _e('Live Links Checker', _SEOCONS_TEXTDOMAIN) ?></label>
                        <input type="checkbox" name="check-live-link" value="1" <?php checked( get_option('check-live-link', false), true, true); ?>>
                    </fieldset>


                    <fieldset>
                        <label><?php _e('Disable Widgets', _SEOCONS_TEXTDOMAIN) ?></label>
                        <input type="checkbox" name="disable-widgets" value="1" <?php checked( get_option('disable-widgets', false), true, true); ?>>
                    </fieldset>


                    <?php submit_button(__('Save Settings', _SEOCONS_TEXTDOMAIN), 'primary'); ?>
                </form>
            </div>
        </div>
    <?php
    }
}
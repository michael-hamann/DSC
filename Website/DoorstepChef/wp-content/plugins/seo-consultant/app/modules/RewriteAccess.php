<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class RewriteAccess
{
    private $rewritefile = null;

    private $wpRewrite = null;

    private $rootpath = null;

    private $rewriteRules = '';

    public function __construct(){
        $this->rewritefile = new ReWrite($this->rootpath);
        $this->rewritefile->read();

        //instantiate wp_rewrite class
        $this->wpRewrite = new \WP_Rewrite();
    }

    public function flushWpRules(){
        $file = new \SplFileObject(get_home_path().'/.htaccess', 'w+');
        $rewrite_rules = $this->wpRewrite->mod_rewrite_rules();
        $rwr = explode("\n",$rewrite_rules);

        $endpos = array_search('</IfModule>', $rwr);

        $end = $rwr[$endpos];
        unset($rwr[$endpos]);

        $rwr[] = $this->rewriteRules.$end;
        $rwr = implode("\n", $rwr);

        return $file->fwrite("\r# BEGIN WordPress\n".$rwr."\n# END WordPress\r");
    }

    public function blockIps($ips){
        $body = array();
        foreach ($ips as $index => $ip) {
            $body[] = $this->createIpRule($ip,$index + 1 === count($ips));
        }

        $this->rewriteRules .= (!empty($ips)) ? "\n".implode("\n", $body)."\n".'RewriteRule .* - [F]'."\n" : "\n";
        return $this;
    }

    public function blockWebsites($websites){

        $body = array();
        foreach($websites as $index => $website){
            $body[] = $this->createWebsiteRule($website, $index + 1 === count($websites));
        }
        $this->rewriteRules .= (!empty($websites)) ? "\n".implode("\n", $body)."\n".'RewriteRule .* - [F]'."\n" : "\n";

        return $this;
    }


    private function createWebsiteRule($website, $last = false){
        $website = str_replace('.','\.',$website);
        return ($last) ?
            "RewriteCond %{HTTP_REFERER} {$website}  [NC]" :
            "RewriteCond %{HTTP_REFERER} {$website}  [NC,OR]";
    }

    private function createIpRule($ip, $last= false){
        return ($last) ?
            "RewriteCond %{REMOTE_ADDR} ^{$ip}$" :
            "RewriteCond %{REMOTE_ADDR} ^{$ip}$ [OR]";
    }

}
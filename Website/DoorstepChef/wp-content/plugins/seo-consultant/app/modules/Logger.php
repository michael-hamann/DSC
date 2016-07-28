<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;

class Logger
{
    public function __construct()
    {

        $home_url = parse_url(get_bloginfo('wpurl'));
        $subdomain = '';

        //Get User Headers
        $analyzer = new AnalyzeRequest();
        $analyzer->analyzeReferer();
        //echo '<h1 class="superman">'.$analyzer->referer['domain'].'</h1>';


        if (!preg_match('/(' . str_replace('.', '\.',$analyzer->referer['domain']) . ')/', $home_url['host']) ) {


            $subdomain = $analyzer->referer['subdomains'];
            $checklnk = new \SeoConsultant\CheckLink($analyzer->referer['referer'], $this->getCurrentLink());

            $db = \SeoConsultant\Database::getInstance();
            $db->log(array(
                'domain_name' => $analyzer->referer['domain'],
                'ip' => $analyzer->referer['ip'][0],
                'subdomain' => $subdomain,
                'port' => 80,
                'user_ip' => $analyzer->referer['user_ip'],
                'no_follow' => $checklnk->nofollow,
                'anchor_text' => $checklnk->anchorText,
                'ref_link' =>$analyzer->referer['referer']
            ));
        }


    }

    private function getCurrentLink(){
        $Path=$_SERVER['REQUEST_URI'];
        return site_url() .$Path;
    }
}



//$db = new \SeoConsultant\Database;
////DOMAIN, SUBDOMAIN,PORT,REFERER_IP, USER_IP, NO_FOLLOW, ANCHOR_TEXT,
//$l = $db->log(array(
//    'domain_name' => 'texturing.ck',
//    'ip' => '93.68.12.31',
//    'subdomain' => '',
//    'port' => 80,
//    'user_ip' => '192.168.2.2',
//    'no_follow' => '',
//    'anchor_text' => ''
//));
//var_dump($l);
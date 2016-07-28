<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class GenerateCSV
{

    public $supportedTypes = array(
        'statistics', 'pages', 'domains', 'anchors'
    );

    private $document = '';

    public function __construct(){
        return $this;
    }

    public function header($filename){
        //Set some headers
        header('Content-Type: text/csv');
        header('Content-Disposition: attachment;filename='.$filename.'.csv');
    }

    public function generateContent($type){

        if(!in_array($type, $this->supportedTypes)){
           $this->redirect();
        }
        echo $this->{ucfirst($type)}();
        $this->header($type.'-seo-consultant-report');
    }

    private function Statistics(){
        $db = Database::getInstance();
        $_follow = $db->getStatisticsOfFollow();
        $_backlinks = $db->getStaticsticsOfBacklinksQnt();
        $_refdomains = $db->getStaticsticsOfRefDomainsQnt();

        $_headings = 'Nofollow/Dofollow,Backlinks,Backlinks Lost,Referring Domains,Referring Domains Lost'."\n";
        $_body = array();
        $_body[] = $this->percent($_follow[0]['NOFOLLOW']).'/'.$this->percent($_follow[0]['FOLLOW']);
        $_body[] = $_backlinks[0]['LINKS'];
        $_body[] = 0;
        $_body[] = $_refdomains[0]['DOMAINS'];
        $_body[] = 0;

        return $_headings.implode(',',$_body)."\n";
    }

    private function Domains(){
        $db = Database::getInstance();
        $_domains = $db->getDomains();

       return $this->formatcsv($_domains);
    }

    private function Pages(){
        $db = Database::getInstance();
        $_pages = $db->getLog();

        return $this->formatcsv($_pages);
    }

    private function Anchors(){
        $db = Database::getInstance();
        $_anchors = $db->getStaticsticsOfAnchorTextDistribution();

        return $this->formatcsv($_anchors);
    }

    private function formatcsv($queried){
        $_headings = '';
        $_body = '';

        foreach($queried as $index => $domain){
            if($index===0){
                $_headings = implode(',', array_keys($domain))."\n";
            }
            $_body .= implode(',', array_values($domain))."\n";
        }
        return $_headings.$_body;
    }

    private function redirect(){
        header('Location:' . admin_url());
        exit();
    }

    private function percent($value){
        return (number_format($value,2)*100) . '%';
    }



}
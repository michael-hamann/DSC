<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class CheckLink
{

    public $exists = false;

    public $anchorText = '';

    public $nofollow = '';

    private $http_headers = array(
        'Host' => 'host',
        'Accept' => "*/*",
        'User-Agent' => "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.130 Safari/537.36",
        'Content-Length' => 0,
        'Content-type' => 'application/x-www-form-urlencoded',
        'Accept-language' => 'el,en;q=0.8',
        'Cache-Control' => 'no-cache',
        'Accept-Encoding' => 'raw',
    );

    private $file = null;

    private $referrer = null;

    public function __construct($referrer, $link){
        $this->referrer = $referrer;

        if($this->get_content()){
            $this->searchContent($link);
        }
    }

    private function get_content(){
        $analyzer = AnalyzeRequest::getInstance();

        $host = $analyzer->parseHost( parse_url($this->referrer, PHP_URL_HOST), 'domain' );

        $this->http_headers = array_merge($this->http_headers, array('Host' => $host));
        $stream = stream_context_create(array(
            'http' => array(
                'methor' => 'GET',
                'header' => $this->http_headers
            )
        ));

        return $this->file = @file_get_contents($this->referrer, false,  $stream);
    }

    private function searchContent($link){
        $link = str_replace('/','\/', $link);
        $matches = array();

        preg_match_all('/<[aA][^>.]+(?:'.$link.').*>(.*)<\/[aA]>/', $this->file, $matches);

        if(isset($matches[0][0]))
            if(preg_match('/rel=[\'"]nofollow[\'"]/', $matches[0][0])){
                $this->nofollow = 0;
            }
            else {
                $this->nofollow = 1;
            }

        $this->anchorText =(isset($matches[1][0])) ? $matches[1][0] : '';
    }
}
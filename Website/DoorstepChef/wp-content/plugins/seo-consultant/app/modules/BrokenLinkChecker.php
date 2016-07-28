<?php

/**
 * Created by ketchupthemes.com
 */

class BrokenLinkChecker
{
    private $links = NULL;

    private $brokenLinks = array();

    private $linkRegex = NULL;

    public function __construct($linkArray, $filterCallback = ''){
        $this->links = (is_array($linkArray)) ? $linkArray : NULL;
        $this->linkRegex = '/(http|https)\:\/\/[a-zA-Z0-9\-\.]+\.[a-zA-Z]{2,3}(\/\S*)?/';
        $this->filterCallback = $filterCallback;
    }

    public function setExpression($regex){
        return $this->linkRegex = $regex;
    }

    public function checkLinks(){
        $linkIterator = new ArrayIterator($this->links);

        if(function_exists($this->filterCallback)){
            $linkIterator = new CallbackFilterIterator($linkIterator, $this->filterCallback);
        }

        foreach($linkIterator as $link){
            if( $this->is_link($link) && !$this->check_link($link) ){
                array_push($this->brokenLinks, $link);
            }
        }
        return $this->brokenLinks;
    }

    private function check_link($uri)
    {
        //Initialize Curl
        $request= @curl_init($uri);

        //Checking if curl is initialized
        if (!is_resource($request) || !isset($request)) {
            throw new Exception("Unable to create cURL session");
        }

        $options = array(
            CURLOPT_HEADER => TRUE,
            CURLOPT_NOBODY => TRUE,
            CURLOPT_RETURNTRANSFER => TRUE,
            CURLOPT_FOLLOWLOCATION => FALSE,
            CURLOPT_SSL_VERIFYPEER => FALSE
        );

        @curl_setopt_array($request, $options);
        curl_exec($request);

        return (curl_getinfo($request, CURLINFO_HTTP_CODE) == 200);
    }

    private function is_link($text){
        return preg_match($this->linkRegex, $text);
    }

}

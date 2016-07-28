<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class RequestFilter
{
    public function post($name, $filters = FILTER_SANITIZE_MAGIC_QUOTES){
        return filter_input(INPUT_POST, $name, $filters);
    }

    public function get($name, $filters = FILTER_SANITIZE_MAGIC_QUOTES){
        return filter_input(INPUT_GET, $name, $filters);
    }

    public function json($name){
        $result = mb_convert_encoding(stripslashes_deep($_POST[$name]), 'UTF-8', 'UTF-8');
        json_decode($name, true);
    }

    public function paramString($name, $filters = FILTER_SANITIZE_MAGIC_QUOTES){
        $result = filter_input(INPUT_POST, $name, $filters);
        return $this->parse_str($result);
    }

    private function parse_str($paramstr){
        $_arr =  array();
        parse_str($paramstr, $_arr);
        return $_arr;
    }
}
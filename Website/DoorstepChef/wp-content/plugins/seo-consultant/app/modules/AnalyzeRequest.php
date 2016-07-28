<?php

/**
 * Created by ketchupthemes.com
 */
namespace SeoConsultant;

class AnalyzeRequest
{
    public $referer = NULL;

    //Contains the request headers
    private $request = NULL;

    //Contains the string
    private $user_agent = NULL;

    private $user_ip = NULL;

    private $https = NULL;

    private static $instance = null;

    public function __construct()
    {
        $this->request = $this->assign_value('HTTP_HOST');
        $this->user_agent = $this->assign_value('HTTP_USER_AGENT');
        $this->user_ip = $this->assign_value('REMOTE_ADDR');
        $this->referer = $this->assign_value('HTTP_REFERER');
        $this->https = $this->assign_value('HTTPS');       
    }

    public static function getInstance()
    {
        if (self::$instance === null) {
            self::$instance = new AnalyzeRequest;
        }

        return self::$instance;
    }

    public function analyzeReferer()
    {
        if ($this->referer) {
            $_url = parse_url($this->referer);
            $_host = $this->parseHost($_url['host']);

            $this->referer = array_merge(array(
                'referer' => $this->referer,
                'user_ip' => $this->user_ip,
                'ip' => gethostbynamel($_url['host']),
                'subdomains' =>  $_host['subdomain'],
                'domain' => $_host['domain']
            ), parse_url($this->referer));

            return true;
        }

        return false;
    }

    public function parseHost($host, $part = false)
    {
        $host = explode('.', str_replace('www.', '', $host));

        $host_parts = array();
        if (count($host) == 4) {
            $host_parts['subdomain'] = $host[0];
            $host_parts['domain'] = implode('.', array_slice($host, 1));
        } else {
            $host_parts['subdomain'] = '';
            $host_parts['domain'] = implode('.', $host);
        }

        return (in_array($part, array('domain', 'subdomain'))) ? $host_parts[$part] : $host_parts;
    }

    private function assign_value($server_value)
    {
        $value = strtoupper($server_value);
        return (isset($_SERVER[$value])) ? $_SERVER[$value] : FALSE;
    }

    private function removePort($host)
    {
        return preg_replace('/\:[0-9]+/', '', $host);
    }

}


/*
 Code:

    $test = new AnalyzeRequest();
    $test->analyzeReferer();

    var_export($test);

 Results:

    AnalyzeRequest::__set_state(array(
       'request' => 'localhost:63342',
       'user_agent' => 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.132 Safari/537.36',
       'user_ip' => '127.0.0.1',
       'referer' =>
      array (
        'referer' => 'http://stackoverflow.com/questions/5292937/php-function-to-get-the-subdomain-of-a-url',
        'ip' =>
        array (
          0 => '198.252.206.16',
        ),
        'subdomains' => false,
        'domain' => 'stackoverflow.com',
        'scheme' => 'http',
        'host' => 'stackoverflow.com',
        'path' => '/questions/5292937/php-function-to-get-the-subdomain-of-a-url',
      ),
       'https' => false,
))
 */


/*
array(3) {
  ["scheme"]=>
  string(4) "http"
  ["host"]=>
  string(17) "www.w3schools.com"
  ["path"]=>
  string(19) "/tags/att_a_rel.asp"
}
array(9) {
  ["Host"]=>
  string(9) "localhost"
  ["Connection"]=>
  string(10) "keep-alive"
  ["Accept"]=>
  string(74) "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*//*;q=0.8"
  ["Upgrade-Insecure-Requests"]=>
  string(1) "1"
  ["User-Agent"]=>
  string(109) "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/44.0.2403.107 Safari/537.36"
  ["Referer"]=>
  string(43) "http://www.w3schools.com/tags/att_a_rel.asp"
  ["Accept-Encoding"]=>
  string(19) "gzip, deflate, sdch"
  ["Accept-Language"]=>
  string(11) "el,en;q=0.8"
  ["Cookie"]=>
  string(374) "wordpress_logged_in_7f60c8a58b87b45d3b5d4fe00119844d=admin%7C1438431296%7C2rvVNc9hasux5Qk4oU3vwBvwo5S4SEvhaPv5r3X6y1Z%7Cf128a2a5d9a8f7f6d13fbd589cb46ef06fb9e1d3c3489cc0bed471e00a83c28a; wp-settings-time-1=1437963221; _ga=GA1.1.2125238733.1432917465; wp-settings-41=libraryContent%3Dbrowse%26editor%3Dhtml; wp-settings-time-41=1433457063; PHPSESSID=mlaleo8o4pp8k1m9dtge0t5rd5"
}
============================array(7) {
    ["referer"]=>
  string(43) "http://www.w3schools.com/tags/att_a_rel.asp"
  ["ip"]=>
  array(1) {
        [0]=>
    string(13) "93.184.220.20"
  }
  ["subdomains"]=>
  array(1) {
        [0]=>
    string(3) "www"
  }
  ["domain"]=>
  string(13) "w3schools.com"
  ["scheme"]=>
  string(4) "http"
  ["host"]=>
  string(17) "www.w3schools.com"
  ["path"]=>
  string(19) "/tags/att_a_rel.asp"
}
 */
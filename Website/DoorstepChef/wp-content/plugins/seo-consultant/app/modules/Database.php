<?php

/**
 * Created by ketchupthemes.com
 */
namespace SeoConsultant;

class Database
{

    public static $HOSTINDEX = NULL;
    public static $INDEXIGNORE = NULL;
    public static $HOSTLOG = NULL;


    private static $instance = null;

    public function __construct()
    {
        global $wpdb;

        //Set wpdb values
        $wpdb->hostindex = "{$wpdb->prefix}seo_hostindex";
        $wpdb->hostlog = "{$wpdb->prefix}seo_hostlog";

        //setup class constants
        self::$HOSTINDEX = $wpdb->hostindex;
        self::$HOSTLOG = $wpdb->hostlog;
    }

    public static function getInstance()
    {
        if (self::$instance === null) {
            self::$instance = new Database;
        }

        return self::$instance;
    }

    /**
     * Inserts data on seo_hostindex table
     *
     * @param array $data
     *
     * $data (array) : DOMAIN, SUBDOMAIN,PORT,REFERER_IP, USER_IP, NO_FOLLOW, ANCHOR_TEXT,
     */
    public function log(array $data)
    {
        global $wpdb;

        $exists = false;
        $ip = false;
        $domain = false;
        $check = $this->checkIndex($data);

        if (empty($check['results'])) {
            extract($data);

            $index = array($data['domain_name'], $data['ip'], 0);
            $createIndex = (!$exists) ?
                $wpdb->query($this->prepare("INSERT INTO " . self::$HOSTINDEX . " (DOMAIN_NAME,IP,IS_BLOCKED) VALUES('%s', '%s', '%d');", $index)) :
                false;
        }
        $check = $this->checkIndex($data);

        if (!empty($check)) {
            $host = array(
                $check['results']['ID'],
                $data['subdomain'],
                $data['port'],
                $data['user_ip'],
                $data['no_follow'],
                $data['anchor_text'],
                $data['ref_link']);

            return $wpdb->query(
                $this->prepare("INSERT INTO " . self::$HOSTLOG . " (DOMAIN_ID,SUBDOMAIN,PORT,USER_IP, NO_FOLLOW, ANCHOR_TEXT,REF_LINK) VALUES('%d','%s','%d','%s','%d','%s','%s')", $host));
        }
    }

    public function deleteLog($data)
    {
        global $wpdb;

        return $wpdb->query(
            $this->prepare("DELETE FROM " . self::$HOSTLOG . " WHERE ID='%d';", array($data['ID'])));
    }

    public function deleteIndex($data)
    {
        global $wpdb;
        $output = $wpdb->query($this->prepare("DELETE FROM " . self::$HOSTINDEX . " WHERE ID='%d';", array($data['ID'])));
        $wpdb->query( $this->prepare("DELETE FROM " . self::$HOSTLOG . " WHERE DOMAIN_ID='%d';", array($data['ID'])) );

        $this->triggerHTACCESSUpdate();
        return $output;
    }

    /**
     * @param bool|false $filter
     * @return mixed
     */
    public function getIPs($filter = false)
    {
        global $wpdb;
        $conditions = $this->indexFilter($filter);

        return $wpdb->get_results("SELECT ID,IP FROM " . self::$HOSTINDEX . $conditions, ARRAY_A);
    }


    /**
     * @param $filter
     * @return mixed
     */
    public function getDomains($filter = false)
    {
        global $wpdb;
        $filter = ($filter) ? $filter : array('is_blocked' => 0);
        return $wpdb->get_results( $this->prepare("SELECT DISTINCT idx.ID,idx.DOMAIN_NAME,idx.IP,count(DISTINCT(log.REF_LINK)) AS OCCURS FROM ".self::$HOSTINDEX." idx JOIN ".self::$HOSTLOG." log on idx.ID=log.DOMAIN_ID WHERE IS_BLOCKED=%d AND IGNORED=0 GROUP BY DOMAIN_NAME", array((int) $filter['is_blocked'])),ARRAY_A);
    }

    //This function doesn't care the domain having links to display it!
    public function getAllDomains($filter = false){
        global $wpdb;
        $filter = ($filter) ? $filter : array('is_blocked' => 0);
        $wpdb->get_results( $this->prepare("SELECT DOMAIN_NAME FROM " . self::$HOSTINDEX . " WHERE IS_BLOCKED='%d'",array((int) $filter['is_blocked'])), ARRAY_A);
    }


    /**
     * @param $filter
     * @return mixed
     */
    public function getLog()
    {
        global $wpdb;
        return $wpdb->get_results("SELECT HLG.ID, HLG.ANCHOR_TEXT, HLG.NO_FOLLOW, HLG.REF_LINK, count(HLG.REF_LINK) AS OCCURS FROM " . self::$HOSTLOG . " HLG LEFT JOIN " . self::$HOSTINDEX . " HI ON HLG.DOMAIN_ID=HI.ID WHERE HI.IS_BLOCKED='0' AND HI.IGNORED='0' GROUP BY HLG.REF_LINK;", ARRAY_A);
    }

    public function getStatisticsOfFollow(){
        global $wpdb;
        return $wpdb->get_results("SELECT distinct
            round((SELECT distinct count(REF_LINK) FROM " . self::$HOSTLOG . " LOG left join ".self::$HOSTINDEX ." IDX ON IDX.ID=LOG.DOMAIN_ID WHERE LOG.NO_FOLLOW=1 AND IDX.IS_BLOCKED=0 AND IDX.IGNORED=0)/count(REF_LINK), 2)
            as FOLLOW,
            round((SELECT distinct count(REF_LINK) FROM " . self::$HOSTLOG . " LOG left join ".self::$HOSTINDEX ." IDX ON IDX.ID=LOG.DOMAIN_ID WHERE LOG.NO_FOLLOW=0 AND IDX.IS_BLOCKED=0 AND IDX.IGNORED=0)/count(REF_LINK),2)
            as NOFOLLOW
            from " . self::$HOSTLOG . " LOG left join ".self::$HOSTINDEX ." IDX
            ON IDX.ID=LOG.DOMAIN_ID WHERE IDX.IS_BLOCKED=0 AND IDX.IGNORED=0", ARRAY_A);
    }

    public function getStaticsticsOfBacklinksQnt(){
        global $wpdb;
        return $wpdb->get_results("SELECT count(DISTINCT(REF_LINK)) as LINKS FROM " . self::$HOSTLOG . " LOG LEFT JOIN " . self::$HOSTINDEX . " IDX ON IDX.ID=LOG.DOMAIN_ID WHERE IDX.IS_BLOCKED=0 AND IDX.IGNORED=0", ARRAY_A);
    }

    public function getStaticsticsOfRefDomainsQnt(){
        global $wpdb;
        return $wpdb->get_results("SELECT count(DOMAIN_NAME) as DOMAINS  FROM ".self::$HOSTINDEX ." WHERE IS_BLOCKED=0 AND IGNORED=0", ARRAY_A);
    }

    public function getStaticsticsOfAnchorTextDistribution(){
        global $wpdb;
        return $wpdb->get_results("SELECT LOG.ANCHOR_TEXT, concat( cast( round( (count(ANCHOR_TEXT)/(SELECT count(LOG.ID) from ".self::$HOSTLOG." LOG LEFT JOIN ".self::$HOSTINDEX." IDX ON IDX.ID=LOG.DOMAIN_ID WHERE IDX.IS_BLOCKED=0 AND IDX.IGNORED=0))*100, 2) as char), '%')  as RATIO from ".self::$HOSTLOG." LOG LEFT JOIN ".self::$HOSTINDEX." IDX ON IDX.ID=LOG.DOMAIN_ID WHERE IDX.IS_BLOCKED=0 AND IDX.IGNORED=0 group by LOG.ANCHOR_TEXT", ARRAY_A);
    }

    public function getIndexInIgnoredList(array $ignored_list){
        if(!empty($ignored_list)){
            global $wpdb;
            $ignoredregexp = strtolower(implode('|', $ignored_list));
            return $wpdb->get_results("SELECT ID from " . self::$HOSTINDEX . " where DOMAIN_NAME REGEXP '$ignoredregexp' AND IGNORED=0;", ARRAY_A);
        }
        return false;
    }

    public function setIgnored(array $ids){
        if(!empty($ids)){
            global $wpdb;
            $ids = implode(',', $ids);
            return $wpdb->get_results("UPDATE " . self::$HOSTINDEX . " set IGNORED=1 WHERE ID in ($ids)");
        }
    }

    /**
     * @param $filter
     * @return array|string
     */
    private function  indexFilter($filter)
    {
        //$this->databaseLog(implode("\n",$filter));

        $conditions = '';
        if (is_array($filter) && !empty($filter)) {
            $conditions = array();

            if (in_array('blocked', $filter)) {
                $conditions['IS_BLOCKED'] = 1;
            }
            if (in_array('allowed', $filter)) {
                $conditions['IS_BLOCKED'] = 0;
            }


            $conditions = $this->mergeConditions($conditions);
        }

        return $conditions;
    }

    /**
     * @param $data
     * @return bool
     */
    public function blockByIP($data)
    {
        $check = $this->checkIndex($data);

        return $this->block(array('ID' => $check['results']['ID']));
    }

    /**
     * @param $data
     * @return bool
     */
    public function blockByDomain($data)
    {
        $check = $this->checkIndex($data);
        return $this->block(array('ID' => $check['results']['ID']));
    }

    public function blockByID($data){
        return $this->block($data);
    }


    public function unBlockByIP($data)
    {
        $check = $this->checkIndex($data);

        return $this->unblock(array('ID' => $check['results']['ID']));
    }

    /**
     * @param $data
     * @return bool
     */
    public function unBlockByDomain($data)
    {
        $check = $this->checkIndex($data);
        return $this->unblock(array('ID' => $check['results']['ID']));
    }


    public function unBlockByID($data)
    {
        return $this->unblock($data);
    }


    /**
     *
     */
    public static function setupDatabase()
    {
        //Create Tables
        self::createHostIndex();
        self::createLog();
    }

    /**
     * @param $data
     * @return bool
     */
    private function block($data)
    {
        global $wpdb;

        $output = $wpdb->query($this->prepare("UPDATE " . self::$HOSTINDEX . " set IS_BLOCKED=1 WHERE ID='%s';", array($data['ID']))) or
        die(mysql_error());

        //if rewrite access is enabled it rewrites the rules to block the domain
        $this->triggerHTACCESSUpdate();

        return $output;
    }

    private function unblock($data)
    {
        global $wpdb;

        $output = $wpdb->query($this->prepare("UPDATE " . self::$HOSTINDEX . " set IS_BLOCKED=0 WHERE ID='%s';", array($data['ID']))) or
        die(mysql_error());

        $this->triggerHTACCESSUpdate();
        return $output;
    }


    /**
     * @return bool
     */
    private static function createHostIndex()
    {
        global $wpdb;

        return $wpdb->query(
            "CREATE TABLE IF NOT EXISTS $wpdb->hostindex (
                ID INT(10) AUTO_INCREMENT PRIMARY KEY,
                DOMAIN_NAME VARCHAR(100) NOT NULL,
                IP VARCHAR(100) NOT NULL,
                IS_BLOCKED BOOLEAN NOT NULL DEFAULT  0,
                IGNORED BOOLEAN NOT NULL DEFAULT  0
        );") or die(mysql_error());
    }

    /**
     * @return bool
     */
    private static function createLog()
    {
        global $wpdb;

        return $wpdb->query(
            "CREATE TABLE IF NOT EXISTS $wpdb->hostlog (
                ID INT(10) AUTO_INCREMENT PRIMARY KEY,
                DOMAIN_ID INT(10) NOT NULL,
                SUBDOMAIN VARCHAR(100),
                PORT INT(10),
                USER_IP VARCHAR(100),
                NO_FOLLOW BOOLEAN,
                ANCHOR_TEXT VARCHAR(100),
                REF_LINK TEXT,
                LAST_ACCESS TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            )"
        ) or die(mysql_error());
    }

    /**
     *
     */
    public function updateTables()
    {
        global $wpdb;

        if ($this->checkTableName($wpdb->hostindex)) {

            if ($this->checkColumnName($wpdb->hostindex, 'IGNORED') === 0) {
                $wpdb->query("ALTER TABLE " . self::$HOSTINDEX . " ADD COLUMN IGNORED BOOLEAN DEFAULT 0;");
            }
        }

        if ($this->checkTableName($wpdb->hostlog)) {

            if ($this->checkColumnName($wpdb->hostlog, 'USER_IP') === 0) {
                $wpdb->query("ALTER TABLE " . self::$HOSTLOG . " ADD COLUMN USER_IP VARCHAR(100), ADD COLUMN NO_FOLLOW BOOLEAN DEFAULT 0, ADD COLUMN ANCHOR_TEXT VARCHAR(100);");
            }
        }
    }


    /**
     * @param $table_name
     * @return bool
     */
    private function checkTableName($table_name)
    {
        global $wpdb;

        return $wpdb->query("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '$table_name';");
    }


    /**
     * @param $table_name
     * @param $column_name
     * @return mixed
     */
    private function checkColumnName($table_name, $column_name)
    {
        global $wpdb;

        return $wpdb->query("SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = '$table_name' AND COLUMN_NAME = '$column_name';");
    }

    private function mergeConditions($conditions, $operator = false, $prefix = '')
    {
        if ($operator) {
            $operator = " {$operator} ";
        } else {
            $operator = ' ';
        }

        return (!empty($conditions)) ? ' WHERE ' .
            implode($operator, array_map(function ($v, $k ) {
                return $k . '=' . $v;
            }, $conditions, array_keys($conditions))) : '';
    }


    /**
     * @param $data
     * @return array
     */
    private function checkIndex(array $data)
    {
        global $wpdb;

        if (!empty($data)) {
            $conditions = array();
            $columns = array('ID', 'DOMAIN_NAME', 'IP', 'IS_BLOCKED', 'IGNORED');
            foreach ($data as $key => $value) {
                if (!empty($value) && in_array(strtoupper($key), $columns)) {
                    $conditions[strtoupper($key)] = "'{$value}'";
                }
            }

            $conditions = $this->mergeConditions($conditions, 'OR');
            $results = $wpdb->get_results("SELECT * FROM " . self::$HOSTINDEX . $conditions, ARRAY_A);

            return array(
                'results' => isset($results[0]) ? $results[0] : false,
                'exists' => count($results) > 0,
                'ip' => isset($results[0]) ? $results[0]['IP'] : false,
                'domain' => isset($results[0]) ? $results[0]['DOMAIN_NAME'] : false);
        }

        return null;
    }

    private function triggerHTACCESSUpdate(){
        global $wpdb;
        //if rewrite access is enabled it rewrites the rules to block the domain
        if(get_option('enable-htaccess-auto-rewrite')){
            $websites = $wpdb->get_results("SELECT DOMAIN_NAME FROM ".self::$HOSTINDEX." WHERE IS_BLOCKED='1';", ARRAY_N);
            $this->getArrayFirstVals($websites);

            $rewriteAccess = new \SeoConsultant\RewriteAccess;
            $rewriteAccess->blockWebsites($websites)->flushWpRules();
        }
    }

    /**
     * @param $query
     * @param array $data
     * @return mixed|string|void
     */
    private function prepare($query, array $data)
    {
        return vsprintf($query, $data);
    }

    private function databaseLog($text)
    {
        $h = fopen(_SEOCONS_PATH . 'databaselog.txt', 'a');
        fputs($h, $text."\n");
        fclose($h);
    }

    private function getArrayFirstVals(&$Narr){
        $_temp = array();
        foreach($Narr as $val){
            if(isset($val)){
                $_temp[] = is_array($val)? $val[0] : $val;
            }
        }
        $Narr = $_temp;
    }
}
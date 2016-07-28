<?php
/**
 * Created by ketchupthemes.com
 */

namespace SeoConsultant;


class Rewrite
{
    private $filepath = null;

    private $fileArr = array();

    public function __construct($filepath){
        if(file_exists($filepath) && !is_dir($filepath)){
            $this->filepath = $filepath;
        }
    }

    /*
     * Reads the file pass by initialization
     */
    public function read(){
        if(!$this->filepath) return null;

        $file = new \SplFileObject($this->filepath, 'r');

        $lines = array();
        foreach($file as $line){
            $lines[] = $line;
        }

        return $this->fileArr = $lines;
    }

    public function write(array $additions, $mod = 'passive', $starting_clue = false, $ending_clue = false){
        $output = '';

        switch($mod){
            case 'search' : $output = $this->searchWrite($additions, $starting_clue, $ending_clue);
                break;
            case 'passive' : $output = $this->passiveWrite($additions, $starting_clue, $ending_clue);
                break;
            default: $output = $this->passiveWrite($additions, $starting_clue, $ending_clue);
        }

        return $output;
    }

    private function searchWrite(array $additions, $starting_clue = false, $ending_clue = false){
        if($starting_clue === false || $ending_clue === false) return null;

        $output = array();
        $previous_markers = $this->detectPrevious($starting_clue, $ending_clue);

        if(isset($previous_markers[0]) && isset($previous_markers[1])){
            $output = array_merge(
                array_slice($this->fileArr, 0, $previous_markers[0]),
                array($starting_clue),
                array("\r".implode("\r", $additions)."\r"),
                array($ending_clue),
                array_slice($this->fileArr, $previous_markers[1])
            );

            $file = fopen($this->filepath, 'r+');
            fwrite($file, implode("", $output));
            fclose($file);
        }
        else{
            $output = array_merge(
                array("\r\r".$starting_clue),
                array("\r".implode("\r", $additions)."\r"),
                array($ending_clue."\r")
            );

            $file = fopen($this->filepath, 'a');
            fwrite($file, implode("", $output));
            fclose($file);
        }

        return $output;

    }

    /**
     * @param array $additions
     * @param $starting_clue, it's text, header or comment which signs the start of the addition, and detects previous insertions for replacement
     * @param $ending_clue, it's text, header or comment which signs the end of the addition, and detects previous insertions for replacement
     */
    private  function passiveWrite(array $additions, $starting_clue = false, $ending_clue = false){
        if($starting_clue === false || $ending_clue === false) return null;

        $output = array();

        $output = array_merge(
            array("\r".$starting_clue),
            array("\r".implode("\r", $additions)."\r"),
            array($ending_clue."\r")
        );

        $file = fopen($this->filepath, 'a');
        fwrite($file, implode("", $output));
        fclose($file);

        return $output;
    }

    /**
     * Detects previous insertions between text markers
     * @param $starting_clue
     * @param $ending_clue
     * @return array
     */
    public function detectPrevious($starting_clue, $ending_clue){
        $lines = $this->fileArr;

        $markers = array();
        foreach($lines as $index => $line){
            $detect_start = preg_match('/'.$starting_clue.'/', $line);
            $detect_end = preg_match('/'.$ending_clue.'/', $line);

            if($detect_start || $detect_end){
                $markers[] = $index;
            }
        }

        return $markers;
    }
}
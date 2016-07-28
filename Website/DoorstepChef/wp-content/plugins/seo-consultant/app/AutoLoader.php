<?php

/**
 * Created by ketchupthemes.com
 */


class AutoLoader
{
    //Extra Folders Array
    public $extra_folders = array();

    //Default Folders to require
    private $default_folders = array(
        'modules',
        'templates'
    );

    //Holds the basepath information
    private $basepath = NULL;

    /**
     * @param $basepath
     */
    public function __construct($basepath){
        $this->basepath = $basepath;

        $this->load_default_folders();
    }

    public function autoload_folder($folder) {
        $this->load_folder( $this->extra_folders[] = $folder );
    }

    /**
     * @param $folder
     */
    private function load_folder($folder){
        //setting a recursive directory iterator
        $folder = new RecursiveDirectoryIterator($this->basepath.'/app/'.$folder);

        //set flags
        $folder->setFlags( FilesystemIterator::UNIX_PATHS | FilesystemIterator::SKIP_DOTS);

        //set a second iterator for loop in the inner folders
        $files = new RecursiveIteratorIterator($folder);
        $files->setMaxDepth(-1);

        foreach($files as $path => $file){
            require_once( $path );
        }

    }

    /**
     * Load all the predifined folders
     */
    private function load_default_folders(){
        foreach($this->default_folders as $folder){
            $this->autoload_folder($folder);
        }
    }

}
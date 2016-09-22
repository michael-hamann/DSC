<?php

//require '/firebaseNew/src/firebaseLib.php';
require '/lib/TokenGen/TokenGenerator.php';
require '/lib/Firebase/Firebase.php';

//use Firebase\FirebaseLib;

use Firebase\Token\TokenException;
use Firebase\Token\TokenGenerator;

try {
    $generator = new TokenGenerator('9zeiksdYSEyETtuUDSlXiTRAo23KTQBv2mIfrMyp');
    $token = $generator
            ->setData(array('uid' => 'Website'))
            ->create();
} catch (TokenException $e) {
    echo "Error: " . $e->getMessage();
}

$secret = "9zeiksdYSEyETtuUDSlXiTRAo23KTQBv2mIfrMyp";

$firebase = Firebase::fromDatabaseUriAndSecret("https://dsc-database.firebaseio.com/",$secret);
$database = $firebase->getDatabase();
$root = $database->getReference("/");
$snapshot = $root->getSnapshot();
$routes = $root->getChild("Routes")->getValue();

//$searchName = $_REQUEST["name"];
//
//$uri = "https://dsc-database.firebaseio.com/";
//$uid = "Bleg";
//$firebase = new FirebaseLib($uri, $token);
//
//$name = $firebase->get("/Clients");
//echo $name;
//$orders = $firebase->get("/Orders");
//echo $orders;

?>
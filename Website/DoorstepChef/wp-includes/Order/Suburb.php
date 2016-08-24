<?php

//https://github.com/firebase/firebase-token-generator-php
//https://github.com/ktamas77/firebase-php

require '/lib/FirebaseLogin/firebaseLib.php';
require '/lib/TokenGen/TokenGenerator.php';

use Firebase\Firebaselib;
use Firebase\Token\TokenException;
use Firebase\Token\TokenGenerator;

try {
    $generator = new TokenGenerator('9zeiksdYSEyETtuUDSlXiTRAo23KTQBv2mIfrMyp');
    $token = $generator
        ->setData(array('uid' => 'Website'))
        ->create();
} catch (TokenException $e) {
    echo "Error: ".$e->getMessage();
}

$uri = "https://dsc-database.firebaseio.com/";
$firebase = new FirebaseLib($uri, $token);

$routeData = $firebase->get("Routes/");
echo json_encode($routeData);
?>
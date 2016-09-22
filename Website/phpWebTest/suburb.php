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
    echo "Error: " . $e->getMessage();
}

$uri = "https://dsc-database.firebaseio.com/";
$firebase = new FirebaseLib($uri, $token);

$routeData = json_decode($firebase->get("Routes/"), TRUE);

$data = array();
$counter = 1;

foreach ($routeData as $val) {
    array_push($data, array(
        $counter => array(
            "Suburbs" => $val["Suburbs"],
            "TimeFrame" => $val["TimeFrame"]
        )
            )
    );
    $counter++;
}

echo json_encode($data);
?>
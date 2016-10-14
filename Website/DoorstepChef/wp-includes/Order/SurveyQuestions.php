<?php

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

$surveyQuestions = $firebase->get("Survey/Questions/");

echo $surveyQuestions;

?>
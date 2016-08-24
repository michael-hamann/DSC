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

$json = json_decode($_POST["mydata"], true);
$routeID = $_REQUEST["routeID"];

$IDs = json_decode($firebase->get("/META-Data"));

$clientID = $IDs->ClientID;
$orderID = $IDs->OrderID;

$clientArr = array(
    "Name" => $json["clientName"],
    "Address" => $json["address"],
    "AdditionalInfo" => $json["addInfo"],
    "Alternative Number" => $json["altNum"],
    "ContactNum" => $json["contactNum"],
    "Email" => $json["email"],
    "Suburb" => $json["suburb"],
    "Surname" => $json["clientSurname"]
);

//$client = json_encode($clientArr);


echo $firebase->set("/Clients/" . $clientID, $clientArr) . "\n";
$firebase->set("/META-Data/ClientID", $clientID + 1);

$mealsArr = array(6);

for ($i = 0; $i < count($json["meals"]); $i++) {
    if ($json["meals"][$i][0] !== ""){
        $mealsArr[$i] = array(
        "Quantity" => $json["meals"][$i][0],
        "MealType" => $json["meals"][$i][1],
        "Allergies" => $json["meals"][$i][2],
        "Exclusions" => $json["meals"][$i][3]
    );
    }
}

$orderArr = array(
    "ClientID" => $clientID,
    "FamilySize" => $json["familySize"],
    "RouteID" => $routeID,
    "Duration" => $json["orderDuration"],
    "StartingDate" => $json["startingDate"],
    "Active" => true,
    "EndDate" => "-",
    "Meals" => $mealsArr
);

echo $firebase->set("/Orders/" . $orderID, $orderArr) . "\n";
$firebase->set("/META-Data/OrderID", $orderID + 1);
?>


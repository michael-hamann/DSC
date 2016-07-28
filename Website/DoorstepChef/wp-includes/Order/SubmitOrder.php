<?php

$servername = "22.153.255.61";
$username = "root";
$password = "root";
$dbname = "doorstepchef";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("connection failed (Input Set): " . $conn->connect_error);
}

$sql = "SELECT OrderID FROM order_tb";
$results = $conn->query($sql);

$lastID = 0;
while ($row = $results->fetch_assoc()) {
    $lastID = $row["OrderID"];
}
$orderID = $lastID + 1;

$json = json_decode($_POST["mydata"], true);

$sql = 'SELECT route_tb.RouteID FROM route_tb, suburb_tb WHERE route_tb.TimeFrame = "' . $json['timeSlot'] . '" AND Suburb = "' . $json['suburb'] . '" AND suburb_tb.Route_ID = route_tb.RouteID';
echo $sql;
$results = $conn->query($sql);
$routeID = 0;

if ($results->num_rows > 0) {
    while ($row1 = $results->fetch_assoc()) {
        $routeID = $row1["RouteID"]." ";
        echo $routeID;
    }
}
$sqlInsertOrderTB = "INSERT INTO order_tb VALUES (" . $orderID . ", " . $json['familySize'] . ", '" . substr($json['startingDate'], 0, 10) . "', " . $routeID . ", \"" . $json['orderDuration'] . "\")";
echo $sqlInsertOrderTB;
//$conn->query($sqlInsertOrderTB);
//------------------------------------------------------------------------------ORDER

$sql = "SELECT MealID FROM meal_tb";
$results = $conn->query($sql);

$lastID = 0;
while ($row = $results->fetch_assoc()) {
    $lastID = $row["MealID"];
}
$mealID = $lastID + 1;

foreach ($json["meals"] as $value) {
    if (!$value[0] == "") {
        $mealType = $value[1];
        $mealAllergies = $value[2];
        $mealExclutions = $value[3];
        $mealQuantity = $value[0];

        $sqlInsertMealTB = "INSERT INTO meal_tb VALUES (" . $mealID . " ,\"" . $mealType . "\" ,\"" . $mealAllergies . "\" ,\"" . $mealExclutions . "\" ," . $mealQuantity . " ," . $orderID . " )";
        $mealID ++;
        echo $sqlInsertMealTB;
        //$conn->query($sqlInsertMealTB);
        //----------------------------------------------------------------------MEAL
    }
}

$sql = "SELECT ClientID FROM client_tb";
$results = $conn->query($sql);

$clientID = 0;
while ($row = $results->fetch_assoc()) {
    $lastID = $row["ClientID"];
}
$clientID = $lastID + 1;

$clientName = $json["clientName"];
$clientSurname = $json["clientSurname"];
$suburb = $json["suburb"];
$address = $json["address"];
$addInfo = $json["addInfo"];
$contactNum = $json["contactNum"];
$altNum = $json["altNum"];
$email = $json["email"];

$sqlInsertClientTB = "INSERT INTO client_tb VALUES ( " . $clientID . ",\"" . $clientName . "\" ,\"" . $clientSurname . "\" ,\"" . $suburb . "\" ,\"" . $address . "\" ,\"" . $addInfo . "\" ,\"" . $contactNum . "\" ,\"" . $altNum . "\" ,\"" . $email . "\" ," . $orderID . " )";
echo $sqlInsertClientTB;
//$conn->query($sqlInsertClientTB);
//------------------------------------------------------------------------------CLIENT
//INSERT INTO order_tb VALUES (2, 1, '2016-07-31', 2, "Monday - Friday")
//INSERT INTO meal_tb VALUES (2 ,"Low Carb" ,"jhs" ,"s" ,1 ,2 )
//INSERT INTO client_tb VALUES ( 2,"Coral" ,"Chump" ,"Brackenfell" ,"Machomp" ,"Press 6" ,"5646654851" ,"4967421683" ,"Chump@gmail.com" ,2 )
?>


<?php

$serverName = "22.153.255.61";
$hostName = "root";
$hostPass = "root";
$dbName = "doorstepchef";

$suburb = $_REQUEST["suburb"];

$conn = mysqli_connect($serverName, $hostName, $hostPass, $dbName);

if ($conn->connect_error) {
    die("Connection failed (TimeSlotChange): " . $conn->connect_error);
}

$sql = 'SELECT route_tb.TimeFrame FROM route_tb, suburb_tb WHERE suburb_tb.Suburb = "' . $suburb . '" AND suburb_tb.Route_ID = route_tb.RouteID';
$results = $conn->query($sql);
$order = array("", "", "");

if ($results->num_rows > 0) {
    $counter = 0;
    while ($row = $results->fetch_assoc()) {
        $order[$counter] = $row["TimeFrame"];
        $counter++;
    }

    for ($index = 0; $index < 3; $index++) {
        $time;
        if ($index == 0) {
            $time = "Afternoon";
        } else if ($index == 1) {
            $time = "Late Afternoon";
        } else {
            $time = "Evening";
        }
        if ($order[0] == $time) {
            echo $order[0] . ", ";
        }
        if ($order[1] == $time) {
            echo $order[1] . ", ";
        }
        if ($order[2] == $time) {
            echo $order[2] . ", ";
        }
    }
}
?>
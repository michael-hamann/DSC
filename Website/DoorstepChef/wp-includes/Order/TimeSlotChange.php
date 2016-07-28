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

$sql = "SELECT route_tb.TimeFrame FROM route_tb, suburb_tb WHERE suburb_tb.Suburb = '" . $suburb . "' AND suburb_tb.Route_ID = route_tb.RouteID";
$results = $conn->query($sql);

if ($results->num_rows > 0) {
    while ($row = $results->fetch_assoc()) {
        echo $row["TimeFrame"]. ", ";
    }
}
?>
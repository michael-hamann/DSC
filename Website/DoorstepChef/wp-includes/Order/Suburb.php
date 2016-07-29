<?php

$servername = "22.153.255.61";
$username = "root";
$password = "root";
$dbname = "doorstepchef";

$conn = new mysqli($servername, $username, $password, $dbname);

if ($conn->connect_error) {
    die("connection failed (Suburb Get): " . $conn->connect_error);
}

$sql = "SELECT DISTINCT Suburb FROM suburb_tb WHERE Suburb NOT LIKE 'Collection' ORDER BY Suburb";
$results = $conn->query($sql);
echo '<option hidden="" disabled="disabled" selected="selected">Suburb</option>';
while ($row = $results->fetch_assoc()) {
    echo "<option>" . $row['Suburb'] . "</option>";
}
?>
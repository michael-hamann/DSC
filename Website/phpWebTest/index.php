<html>
    <head>
    <h1>Person Info:</h1>
    <link rel="stylesheet" type="text/css" href="Style.css">
    <script src="https://cdn.firebase.com/js/client/2.4.2/firebase.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script src="https://www.gstatic.com/firebasejs/3.2.1/firebase.js"></script>
    <script>
        // Initialize Firebase
        var config = {
            apiKey: "AIzaSyAA0U3r7vgl6buP7XiSPY4t5tdLImAuXPE",
            authDomain: "dsc-database.firebaseapp.com",
            databaseURL: "https://dsc-database.firebaseio.com",
            storageBucket: "dsc-database.appspot.com",
        };
        firebase.initializeApp(config);
    </script>


    <script>
//        GenToken();
//        function GenToken() {
//            var FirebaseTokenGenerator = require("firebase-token-generator");
//            var tokenGenerator = new FirebaseTokenGenerator("9zeiksdYSEyETtuUDSlXiTRAo23KTQBv2mIfrMyp");
//            var token = tokenGenerator.createToken(
//                    {uid: "P01", some: "arbitrary", data: "here"}
//            );
//            alert(token);
//        }

        function createPerson() {
            var ref = new Firebase("https://dsc-database.firebaseio.com/Routes/");
            var token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJkIjp7InVpZCI6IlAwMSJ9LCJ2IjowLCJpYXQiOjE0NzEzNDczNjR9.jku08ygt9LT3RcZSMYYbY60aSwbXARgJ--pYvplmcII";
            ref.authWithCustomToken(token, function (error, authData) {
                if (error) {
                    console.log("Authentication Failed!", error);
                } else {
                    console.log("Authenticated successfully with payload:", authData);
                    var auth = authData;
                    console.log(auth.expires);
                    console.log(new Date(auth.expires).toDateString());
                }
            });

//
//            var firstName = document.getElementById("FirstNameField").value;
//            var lastName = document.getElementById("LastNameField").value;
//            var age = document.getElementById("AgeField").value;

            var suburbs = new Array("Brackenfell", "Morgenster", "Protea Village", "GamsDorp");
            var orders = new Array("");
            var newRef = ref.push({
                "Active": true,
                "TimeFrame": "Afternoon",
                "Suburbs": suburbs,
                "Orders": orders,
                "Drivers": {
                    "-KPNXTKROjOtyC8Th7X7": {
                        "StartDate": new Date().toISOString(),
                        "EndDate": "-"
                    }
                }
            }, function (error) {
                if (error) {
                    alert("Data unsuccsesfully added: " + error);
                } else {
                    alert("Data succsesfully added" + newRef.name());
                }
            });


        }

//        function readDataPHP() {
//
//            jQuery.ajax({
//                url: "suburb.php",
//                type: "GET",
//                dataType: 'text',
//                success: function (data, textStatus, jqXHR) {
//                    if (data !== "false") {
//                console.log(data);
//                document.getElementById("pageContent").innerHTML = html;
//                getDates("startDate");
//
//                var routeData = JSON.parse(data);
//                console.log(routeData);
//                for (var i in routeData) {
//                    if (routeData[i].Active) {
//                        var arr = routeData[i].Suburbs;
//                        var arrTimes = routeData[i].TimeFrame;
//                        for (var j = 0; j < arr.length; j++) {
//                            var found = false;
//                            for (var k = 0; k < allSuburbs.length; k++) {
//                                if (allSuburbs[k].suburb === arr[j]) {
//                                    if (!allSuburbs[k].timeframes.includes(arrTimes)) {
//                                        allSuburbs[k].timeframes += arrTimes + ", ";
//                                    }
//                                    found = true;
//                                }
//                            }
//                            if (!found) {
//                                allSuburbs.push({suburb: arr[j], timeframes: arrTimes + ", "});
//                            }
//                        }
//                    }
//                }
//
//                for (var i in routeData) {
//                    if (routeData[i].Active) {
//                        var arr = routeData[i].Suburbs;
//                        var arrTimes = routeData[i].TimeFrame;
//                        var routeID = i;
//                        for (var j = 0; j < arr.length; j++) {
//                            routeInfo.push({suburb: arr[j], timeframe: arrTimes, routeID: i});
//                        }
//
//                    }
//                }
//
//                var val = '<option hidden="" disabled="disabled" selected="selected">Please Select a Suburb</option>';
//                for (var i = 1; i < allSuburbs.length; i++) {
//                    if (allSuburbs[i].suburb !== "Collection") {
//                        val += "<option>" + allSuburbs[i].suburb + "</option>";
//                    }
//                    console.log(allSuburbs[i].suburb);
//                }
//
//                document.getElementById("James").innerHTML = val;
//            } else {
//                document.getElementById("pageContent").innerHTML = "<a>ERROR 400: DATABASE CONNECTION COULD NOT BE ESTABLISHED</a>";
//            }
//
//        }
//                },
//                error: function (jqXHR, textStatus, errorThrown) {
//                    alert("error: " + errorThrown);
//                }
//            })
//
//        }

        function addStuff(){
            var options = document.getElementById("Reason").innerHTML;
            options += "\n<option>Colin</option>"+
                    "\n<option>McGreggor</option>";
            document.getElementById("Reason").innerHTML = options;
        }



    </script>

</head>
<body>

    <div>
        <div>
            <p><label>First Name: </label><input type="text" id="FirstNameField" ></p>
            <p><label>Last  Name: </label><input type="text" id="LastNameField"></p>
               
            
            
            <p><label>Age:</label><input type="number" id="AgeField" max="100" ></p>
            <p><button type="Submit" name="Submit" onclick="createPerson()">Send Data</button></p>
            <p><button type="Submit" name="Read" onclick="readDataPHP()">Read Data</button>Search Field:<input type="text" id="searchField" ></p>
        </div>

        <a id="James">
            
            <p><button type="Submit" name="Reason" id="ReasonButton" onclick="addStuff()">Do Stuff</button></p>
            
            
            
            
            
            
            
            
            
            
                <select name="Reason" id="Reason">
                    <option hidden="" disabled="disabled" selected="selected">Carla</option>
                    <option>James</option>
                    <option>Bond</option>
            </select>
        </a>

    </div>
</body>

</html>
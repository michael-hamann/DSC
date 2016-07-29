connectionCheck();

function getDates(dates) {
    var currDate = new Date();
    var counter = 0;
    var mon1, mon2, mon3, mon4;
    while (counter < 4) {
        for (var i = 0; i < 7; i++) {
            if (currDate.getDay() != 1) {
                currDate.setDate(currDate.getDate() + 1);
            }
        }
        counter++;
        switch (counter) {
            case 1:
                mon1 = new Date(currDate).toDateString();
                break;
            case 2:
                mon2 = new Date(currDate).toDateString();
                break;
            case 3:
                mon3 = new Date(currDate).toDateString();
                break;
            case 4:
                mon4 = new Date(currDate).toDateString();
                break;
        }
        currDate.setDate(currDate.getDate() + 1);
    }

    document.getElementById(dates).innerHTML = '<option hidden="" disabled="disabled" selected="selected">Starting date</option>' +
            '<option value="option1">' + mon1 + '</option>' +
            '<option value="option2">' + mon2 + '</option>' +
            '<option value="option3">' + mon3 + '</option>' +
            '<option value="option4">' + mon4 + '</option>';
}


function listSuburbs(suburbDropdown) {
    var url = "/DoorstepChef/wp-includes/Order/Suburb.php";

    jQuery.ajax({
        type: 'get',
        url: url,
        success: function (data, textStatus, jqXHR) {
            document.getElementById(suburbDropdown).innerHTML = data;
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Failed to get Dates: " + errorThrown);
        }
    });
}


function timeSlotAdj() {
    var selectedSuburb = document.getElementById("Suburb").options[document.getElementById("Suburb").selectedIndex].text;
    var url = "/DoorstepChef/wp-includes/Order/TimeSlotChange.php";

    jQuery.ajax({
        type: 'get',
        url: url,
        data: {suburb: selectedSuburb},
        dataType: "text",
        success: function (data, textStatus, jqXHR) {
            var responseData = data;
            var responseText = new Array(5);
            responseText[0] = "<labelB>";
            responseText[1] = "";
            responseText[2] = "";
            responseText[3] = "";
            responseText[4] = "</labelB> <br><br>";
            var responses = responseData.split(", ");
            var firstIterate;
            for (var i = 0; i < responses.length - 1; i++) {

                if (i == 0) {
                    firstIterate = 'checked="checked" ';
                } else {
                    firstIterate = '';
                }

                if (responses[i] == "Afternoon") {
                    responseText[1] = '<input id="rbnAfternoon" ' + firstIterate + ' name="time" type="radio" value="frame1" />      Between 11:00 and 13:00 <br>';
                } else if (responses[i] == "Late Afternoon") {
                    responseText[2] = '<input id="rbnLateAfternoon" ' + firstIterate + ' name="time" type="radio" value="frame2" />      Between 14:00 and 16:00<br>';
                } else if (responses[i] == "Evening") {
                    responseText[3] = '<input id="rbnEvening" ' + firstIterate + ' name="time" type="radio" value="frame3" />      Between 17:00 and 19:00<br>'
                }
            }
            document.getElementById("TimeSlots").innerHTML = responseText[0] + responseText[1] + responseText[2] + responseText[3] + responseText[4];
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Something happned: " + errorThrown);
        }
    });
}

function connectionCheck(){
    var url = "/DoorstepChef/wp-includes/Order/CheckConnection.php";
    var timeID = setTimeout(connFailed, 5000);
    var html = document.getElementById("pageContent").innerHTML;
    
    document.getElementById("pageContent").innerHTML = "Loading ...";
    jQuery.ajax({
        type: 'get',
        dataType: 'text',
        url: url,
        success: function (data, textStatus, jqXHR) {
            if (data == "") {
                clearTimeout(timeID);
                document.getElementById("pageContent").innerHTML = html;
            }
        },
        error: function (jqXHR, textStatus, errorThrown){
            alert("Someting happned: " + errorThrown);
        }
    });
}

function connFailed(){
    document.getElementById("pageContent").innerHTML = "<a>ERROR 400: DATABASE CONNECTION COULD NOT BE ESTABLISHED</a>";
}

function submitData() {
    var clienName = document.getElementById("name").value;
    var clientSurname = document.getElementById("surname").value;
    var contactNum = document.getElementById("contactNum").value;
    var altNum = "";
    if (!document.getElementById("altNum").value == "") {
        altNum = document.getElementById("altNum").value;
    } else {
        altNum = "N/A";
    }
    var email = document.getElementById("email").value;
    var suburb = "";
    var address = "";
    var addInfo = "";
    if (document.getElementById("collections").checked) {
        suburb = "Collection";
        address = "-";
        addInfo = "-";
    } else {
        suburb = document.getElementById("Suburb").options[document.getElementById("Suburb").selectedIndex].text;
        address = document.getElementById("address").value;
        addInfo = document.getElementById("addInfo").value;
    }

    var familySize = document.getElementById("fam1").selectedIndex;
    var timeSlot = "";
    if (document.getElementById("rbnAfternoon") != null) {
        if (document.getElementById("rbnAfternoon").checked) {
            timeSlot = "Afternoon";
            alert(timeSlot);
        }
    }
    if (document.getElementById("rbnLateAfternoon") != null) {
        if (document.getElementById("rbnLateAfternoon").checked) {
            timeSlot = "Late Afternoon";
            alert(timeSlot);
        }
    }
    if (document.getElementById("rbnEvening") != null) {
        if (document.getElementById("rbnEvening").checked) {
            timeSlot = "Evening";
            alert(timeSlot);
        }
    }

    var startingDate = new Date(document.getElementById("startDate").options[document.getElementById("startDate").selectedIndex].text);
    var orderDuration = "";
    if (document.getElementById("rbnMonThur").checked) {
        orderDuration = "Monday - Thursday";
    } else {
        orderDuration = "Monday - Friday";
    }
    var meals = new Array(6);
    for (var i = 0; i < 6; i++) {
        var arr = new Array(4);
        try {
            arr[0] = document.getElementById("orderAmount_" + i).value;
            arr[1] = document.getElementById("mealTypeSelector_" + i).options[document.getElementById("mealTypeSelector_" + i).selectedIndex].text;
            arr[2] = document.getElementById("orderAll_" + i).value;
            arr[3] = document.getElementById("orderExc_" + i).value;
        } catch (err) {
            arr[0] = "";
            arr[1] = "";
            arr[2] = "";
            arr[3] = "";
        }
        meals[i] = arr;
    }

    console.log(clienName);
    console.log(clientSurname);
    console.log(contactNum);
    console.log(altNum);
    console.log(email);
    console.log(suburb);
    console.log(address);
    console.log(addInfo);
    console.log(familySize);
    console.log(timeSlot);
    console.log(startingDate);
    console.log(orderDuration);
    for (var i = 0; i < 6; i++) {
        console.log(meals[i][0]);
        console.log(meals[i][1]);
        console.log(meals[i][2]);
        console.log(meals[i][3]);
    }

    var jsonOrder = JSON.stringify({
        "clientName": clienName,
        "clientSurname": clientSurname,
        "suburb": suburb,
        "address": address,
        "addInfo": addInfo,
        "contactNum": contactNum,
        "altNum": altNum,
        "email": email,
        "familySize": familySize,
        "timeSlot": timeSlot,
        "startingDate": startingDate,
        "orderDuration": orderDuration,
        "meals": meals
    });

    var url = "/DoorstepChef/wp-includes/Order/SubmitOrder.php";
    jQuery.ajax({
        type: 'POST',
        url: url,
        data: {mydata: jsonOrder},
        dataType: 'text',
        success: function (data, textStatus, jqXHR) {
            document.getElementById("TestDisplay").innerHTML = data + " - data";
        },
        error: function (jqXHR, textStatus, errorThrown) {
            alert("Failed : " + errorThrown);
        }
    });

    document.getElementById("submit").blur();
}
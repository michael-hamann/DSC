var routeInfo;
var allSuburbs;
listSuburbs();

function getDates(dates) {
    var currDate = new Date();
    var counter = 0;
    var mon1, mon2, mon3, mon4;
    while (counter < 4) {
        for (var i = 0; i < 7; i++) {
            if (currDate.getDay() !== 1) {
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

//-------------------------------------------------------------------------*Data Reduction (PHP Side)
function listSuburbs() {



    if (routeInfo == null) {
        var url = "/DoorstepChef/wp-includes/Order/Suburb.php";

        var html = document.getElementById("pageContent").innerHTML;
        document.getElementById("pageContent").innerHTML = "Loading ...";
        jQuery.ajax({
            type: 'get',
            url: url,
            success: function (data, textStatus, jqXHR) {
                if (data !== "false") {

                    allSuburbs = [
                        {suburb: "", timeframes: ""}
                    ];
                    routeInfo = [
                        {suburb: "", timeframe: "", routeID: "0"}
                    ];

                    document.getElementById("pageContent").innerHTML = html;
                    getDates("startDate");

                    var routeData = JSON.parse(data);
                    for (var i in routeData) {
                        if (routeData[i].Active) {
                            var arr = routeData[i].Suburbs;
                            var arrTimes = routeData[i].TimeFrame;
                            for (var j = 0; j < arr.length; j++) {
                                var found = false;
                                for (var k = 0; k < allSuburbs.length; k++) {
                                    if (allSuburbs[k].suburb === arr[j]) {
                                        if (!allSuburbs[k].timeframes.includes(arrTimes)) {
                                            allSuburbs[k].timeframes += arrTimes + ", ";
                                        }
                                        found = true;
                                    }
                                }
                                if (!found) {
                                    allSuburbs.push({suburb: arr[j], timeframes: arrTimes + ", "});
                                }
                            }
                        }
                    }

                    for (var i in routeData) {
                        if (routeData[i].Active) {
                            var arr = routeData[i].Suburbs;
                            var arrTimes = routeData[i].TimeFrame;
                            var routeID = i;
                            for (var j = 0; j < arr.length; j++) {
                                routeInfo.push({suburb: arr[j], timeframe: arrTimes, routeID: i});
                            }

                        }
                    }

                    var val = '<option hidden="" disabled="disabled" selected="selected">Please Select a Suburb</option>';
                    for (var i = 1; i < allSuburbs.length; i++) {
                        if (allSuburbs[i].suburb !== "Collection") {
                            val += "<option>" + allSuburbs[i].suburb + "</option>";
                        }
                        console.log(allSuburbs[i].suburb);
                    }

                    document.getElementById("Suburb").innerHTML = val;
                } else {
                    document.getElementById("pageContent").innerHTML = "<a>ERROR 400: DATABASE CONNECTION COULD NOT BE ESTABLISHED</a>";
                }

            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Failed to get Suburbs: " + errorThrown);
            }
        });
    } else {
        var val = '<option hidden="" disabled="disabled" selected="selected">Please Select a Suburb</option>';
        for (var i = 1; i < allSuburbs.length; i++) {
            if (allSuburbs[i].suburb !== "Collection") {
                val += "<option>" + allSuburbs[i].suburb + "</option>";
            }
            console.log(allSuburbs[i].suburb);
        }

        document.getElementById("Suburb").innerHTML = val;
    }
}


function timeSlotAdj() {
    var selectedSuburb = document.getElementById("Suburb").options[document.getElementById("Suburb").selectedIndex].text;
    var responseData = "";

    for (var i = 0; i < allSuburbs.length; i++) {
        if (allSuburbs[i].suburb === selectedSuburb) {
            responseData = allSuburbs[i].timeframes;
        }
    }

    var responseText = new Array(5);
    responseText[0] = "<labelB>";
    responseText[1] = "";
    responseText[2] = "";
    responseText[3] = "";
    responseText[4] = "</labelB> <br><br>";
    var responses = responseData.split(", ");
    var firstIterate = 'checked="checked" ';
    for (var i = 0; i < responses.length - 1; i++) {
        if (responses[i] === "Afternoon") {
            responseText[1] = '<input id="rbnAfternoon" # name="time" type="radio" value="frame1" />      Between 11:00 and 13:00 <br>';
        } else if (responses[i] === "Late Afternoon") {
            responseText[2] = '<input id="rbnLateAfternoon" # name="time" type="radio" value="frame2" />      Between 14:00 and 16:00<br>';
        } else if (responses[i] === "Evening") {
            responseText[3] = '<input id="rbnEvening" # name="time" type="radio" value="frame3" />      Between 17:00 and 19:00<br>';
        }
    }

    for (var i = 1; i < responseText.length - 1; i++) {
        if (responseText[i].includes("#")) {
            responseText[i] = responseText[i].replace("#", firstIterate);
            firstIterate = "";
        }
    }
    document.getElementById("TimeSlots").innerHTML = responseText[0] + responseText[1] + responseText[2] + responseText[3] + responseText[4];

    document.getElementById("addInfo").disabled = false;
    document.getElementById("address").disabled = false;
    document.getElementById("addInfo").style.borderColor = "#cccccc";
    document.getElementById("address").style.borderColor = "#cccccc";
    document.getElementById("addInfo").style.color = "#666";
    document.getElementById("address").style.color = "#666";
    document.getElementById("addInfoLbl").style.color = "#666";
    document.getElementById("addressLbl").style.color = "#666";
    document.getElementById("Suburb").style.borderColor = "#cccccc";
    document.getElementById("suburbErr").innerHTML = "";
}

function submitSimpleTextCheck(component, errorText) {
    var letters = /^[A-Za-z \'-]+$/;
    var value = document.getElementById(component).value.trim();
    if (value === "") {
        document.getElementById(component).style.borderColor = "#DD5C61";
        document.getElementById(errorText).innerHTML = "* You can't leave this empty.";
        return false;
    } else if (!value.match(letters)) {
        document.getElementById(component).style.borderColor = "#DD5C61";
        document.getElementById(errorText).innerHTML = "* This field can only take letters.";
        return false;
    } else {
        document.getElementById(component).style.borderColor = "#cccccc";
        document.getElementById(errorText).innerHTML = "";
        return true;
    }
}

function submitNumberCheck(component, errorText, compulsory) {
    var numFormat = /^[0-9]*$/;
    var value = document.getElementById(component).value.trim() + "";
    if (compulsory && value === "") {
        document.getElementById(component).style.borderColor = "#DD5C61";
        document.getElementById(errorText).innerHTML = "* You can't leave this empty.";
        return false;
    } else if (!value.match(numFormat)) {
        document.getElementById(component).style.borderColor = "#DD5C61";
        document.getElementById(errorText).innerHTML = "* This field can only take numbers.";
        return false;
    } else if (value.length < 10) {
        document.getElementById(component).style.borderColor = "#DD5C61";
        document.getElementById(errorText).innerHTML = "* This field has to be 10 digits long.";
        return false;
    } else {
        document.getElementById(component).style.borderColor = "#cccccc";
        document.getElementById(errorText).innerHTML = "";
        return true;
    }
}

function submitEmailCheck(component, errorText) {
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var value = document.getElementById(component).value.trim();
    if (value === "") {
        document.getElementById(component).style.borderColor = "#cccccc";
        document.getElementById(errorText).innerHTML = "";
        return true;
    } else if (!value.match(mailformat)) {
        document.getElementById(component).style.borderColor = "#DD5C61";
        document.getElementById(errorText).innerHTML = "* Not a valid e-mail address.";
        return false;
    } else {
        document.getElementById(component).style.borderColor = "#cccccc";
        document.getElementById(errorText).innerHTML = "";
        return true;
    }

}

function submitAddressCheck(componentSuburb, errorTextSuburb, componentAddress, errorTextAddress) {
    var suburbValue = document.getElementById("Suburb").options[document.getElementById("Suburb").selectedIndex].text;
    var addressValue = document.getElementById("address").value;

    if (suburbValue === "Please choose a suburb.") {
        document.getElementById(componentSuburb).style.borderColor = "#DD5C61";
        document.getElementById(errorTextSuburb).innerHTML = "* You must select a suburb.";
        return false;
    } else if (addressValue === "") {
        document.getElementById(componentAddress).style.borderColor = "#DD5C61";
        document.getElementById(errorTextAddress).innerHTML = "* You must supply an address.";
    } else {
        document.getElementById(componentAddress).style.borderColor = "#cccccc";
        document.getElementById(errorTextAddress).innerHTML = "";
        return true;
    }

}

function submitFamilyCheck(component, errorText) {
    var selection = document.getElementById(component).selectedIndex;
    if (selection === 0) {
        document.getElementById(errorText).innerHTML = "* You must select a family size.";
        document.getElementById(component).style.borderColor = "#DD5C61";
        return false;
    } else {
        return true;
    }
}

function submitDateCheck(component, errorText) {
    var selection = document.getElementById(component).selectedIndex;
    if (selection === 0) {
        document.getElementById(errorText).innerHTML = "* You must select a starting date.";
        document.getElementById(component).style.borderColor = "#DD5C61";
        return false;
    } else {
        return true;
    }
}

function valColorChange() {
    document.getElementById("startingDateErr").innerHTML = "";
    document.getElementById("startDate").style.borderColor = "#ccc";
}

function findPos(obj) {
    var curtop = 0;
    if (obj.offsetParent) {
        do {
            curtop += obj.offsetTop;
        } while (obj = obj.offsetParent);
        return [curtop];
    }
}

function submitData() {


    var allGood = true;
    var compFocus;

    var clienName;
    if (submitSimpleTextCheck("name", "nameErr")) {
        clienName = document.getElementById("name").value;
    } else {
        window.scroll(0, (findPos(document.getElementById("name")) - 110));
        allGood = false;
    }

    var clientSurname;
    if (submitSimpleTextCheck("surname", "surnameErr")) {
        clientSurname = document.getElementById("surname").value;
    } else {
        if (allGood) {
            window.scroll(0, (findPos(document.getElementById("surname")) - 110));
        }
        allGood = false;
    }

    var contactNum;
    if (submitNumberCheck("contactNum", "contactNumErr", true)) {
        contactNum = document.getElementById("contactNum").value;
    } else {
        if (allGood) {
            window.scroll(0, (findPos(document.getElementById("contactNum")) - 110));
        }
        allGood = false;
    }

    var altNum = "";
    if (!document.getElementById("altNum").value === "") {
        if (submitNumberCheck("altNum", "altNumErr", false)) {
            altNum = document.getElementById("altNum").value;
        } else {
            if (allGood) {
                window.scroll(0, (findPos(document.getElementById("altName")) - 110));
            }
            allGood = false;
        }
    } else {
        altNum = "N/A";
    }

    var email = "";
    if (submitEmailCheck("email", "emailErr")) {
        email = document.getElementById("email").value;
    } else {
        if (allGood) {
            window.scroll(0, (findPos(document.getElementById("email")) - 110));
        }
        allGood = false;
    }

    var suburb = "";
    var address = "";
    var addInfo = "";
    if (document.getElementById("collections").checked) {
        suburb = "Collection";
        address = "-";
        addInfo = "-";
    } else {
        if (submitAddressCheck("Suburb", "suburbErr", "address", "addressErr")) {
            suburb = document.getElementById("Suburb").options[document.getElementById("Suburb").selectedIndex].text;
            address = document.getElementById("address").value;
            addInfo = document.getElementById("addInfo").value;
        } else {
            if (allGood) {
                window.scroll(0, (findPos(document.getElementById("Suburb")) - 110));
            }
            allGood = false;
        }
    }

    if (submitFamilyCheck("fam1", "fam1Err")) {
        var familySize = document.getElementById("fam1").selectedIndex;
    } else {
        if (allGood) {
            window.scroll(0, (findPos(document.getElementById("fam1")) - 110));
        }
        allGood = false;
    }

    var timeSlot = "";
    if (document.getElementById("rbnAfternoon") !== null) {
        if (document.getElementById("rbnAfternoon").checked) {
            timeSlot = "Afternoon";
        }
    }
    if (document.getElementById("rbnLateAfternoon") !== null) {
        if (document.getElementById("rbnLateAfternoon").checked) {
            timeSlot = "Late Afternoon";
        }
    }
    if (document.getElementById("rbnEvening") !== null) {
        if (document.getElementById("rbnEvening").checked) {
            timeSlot = "Evening";
        }
    }
    if (submitDateCheck("startDate", "startingDateErr")) {
        var startingDate = new Date(document.getElementById("startDate").options[document.getElementById("startDate").selectedIndex].text);
    } else {
        if (allGood) {
            window.scroll(0, (findPos(document.getElementById("startDate")) - 110));
        }
        allGood = false;
    }

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




    if (allGood) {
        document.getElementById("submit").disabled = true;
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


        var routeID = "";
        for (var i = 0; i < routeInfo.length; i++) {
            if (routeInfo[i]["suburb"] === suburb && routeInfo[i]["timeframe"] === timeSlot) {
                routeID = routeInfo[i]["routeID"];
            }
        }
        console.log(routeID);

        var url = "/DoorstepChef/wp-includes/Order/SubmitOrder.php";
        jQuery.ajax({
            type: 'POST',
            url: url,
            data: {mydata: jsonOrder,
                routeID: routeID},
            dataType: 'text',
            success: function (data, textStatus, jqXHR) {
                if (data === true) {
                    alert("Order has been placed");
                }
            },
            error: function (jqXHR, textStatus, errorThrown) {
                alert("Failed to add Order: " + errorThrown);
            }
        });
    } else {
        // --------------------------------------------------------------------- Logic for incorrect values.
    }

    document.getElementById("submit").blur();
}
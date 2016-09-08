var counter = 1;
var limit = 3;
var changed = false;
var mealSize = null;
var mealCount = 0;
var spinnerResult;
var addMealsComponent = false;
var sum = 0;
var substraction = 0;
var setMax = 3;
var familyCheckSuccess = false;
var totalMealsAtTheMoment;
var spinner;
var mealsId = ["undefined", "undefined", "undefined", "undefined", "undefined", "undefined"];
var componentCount = 1;
var compID;


function validateIfFamilySizeIsChecked() {

    var TotalMealsLeft = document.getElementById("totalMeals").value;

    if (mealSize != null) {

    } else if (mealSize == null) {

        alert("Please choose a family size.");
        document.getElementById("fam1").focus();
        document.getElementById('orderAmount_0').value = 1;

    }

    validateIfMoreMealsCanBeOrdered();


}



function validateIfMoreMealsCanBeOrdered(compID) {

    sum = 0;

    var checkSpinner = parseInt(document.getElementById('orderAmount_' + (compID)).value);
    var validateTotal = document.getElementById("totalMeals").value;

    if (checkSpinner < 0 || validateTotal < 0) {

        alert("Do not do abnormal things!");

        checkMeals();


    } else {

        var TotalMealsLeft = parseInt(document.getElementById("totalMeals").value);

        for (iterate = 0; iterate < 6; iterate++) {

            try {

                sum += parseInt(document.getElementById('orderAmount_' + (iterate)).value);

            } catch (Exception) {

            }
        }


        var valueOfSpinner = document.getElementById('orderAmount_' + (compID)).value;

        if (sum <= mealSize) {

            if (sum > mealSize) {

                alert("Please Decrease Order Amount of delete an entire order");

                spinnerResult = parseInt(document.getElementById('orderAmount_' + (compID)).value);

                if (document.getElementById('orderAmount_' + (compID)).value - 1 >= 0) {

                    document.getElementById('orderAmount_' + (compID)).value = spinnerResult - 1;

                } else {

                    document.getElementById('order').removeChild(div.parentNode);

                }

            } else {

                document.getElementById("totalMeals").value = mealSize - sum;

            }
        } else {

            var currentValue = document.getElementById('orderAmount_' + (compID)).value;
            document.getElementById("totalMeals").value = 0;

            if (mealSize == 0) {

                document.getElementById('orderAmount_' + (compID)).value = 1;

            } else {

                var subtractIncorrectValue = sum - currentValue;
                document.getElementById('orderAmount_' + (compID)).value = mealSize - subtractIncorrectValue;

            }
            alert("not allowed!");
        }

    }
}

function addOrder() {

    var NumberOfmeals = document.getElementById("totalMeals").value;

    var calc = document.getElementById("totalMeals").value - sum - 1;

    if (counter <= mealSize && NumberOfmeals - 1 >= 0 && document.getElementById("totalMeals").value - 1 >= 0) {

        addMealsComponent = true;

        var changeTotalMeals = document.getElementById("totalMeals").value;

        document.getElementById("totalMeals").value = changeTotalMeals - 1;

        for (iterate = 1; iterate < mealSize; iterate++) {

            try {

                if (mealsId[iterate] == "undefined") {

                    mealsId[iterate] = iterate;
                    componentCount = mealsId[iterate];
                    var newdiv = document.createElement('div');

                    newdiv.innerHTML = '<labelA style = "margin-right: 3.6em" > </labelA> <input style="height:1em;width:1.5em" type="number" name="Orderamount" onChange= "validateIfMoreMealsCanBeOrdered(' + componentCount + ')" id="orderAmount_' + componentCount + '" min="1" max="6" step="1" value="1" > <select name="size" style="height:2em" id="mealTypeSelector_' + componentCount + '"><option value="Standard" id = 1>Standard</option><option value="Low Carb" id = 1 >Low Carb</option><option value="Kiddies" id = 1>Kiddies</option></select> <input type = "text" style="height:1em" id = "orderAll_' + componentCount + '"> <input type = "text" style="height:1em" id = "orderExc_' + componentCount + '" style="height:0.5em"> <input style="height:1em;background-image: url(https://cdn2.iconfinder.com/data/icons/3_Minicons-Free-_Pack/45/RecycleBin.png)" type="Deletebutton" onclick="removeOrder(this,' + componentCount + ')">';

                    document.getElementById('order').appendChild(newdiv);
                    componentCount++;
                    break;

                }

            } catch (Exception) {

            }
        }

        counter++;

    } else if (counter > 5) {

        alert("too many fields!");

    }

    document.getElementById("addOrderID").blur();

}


function removeOrder(div, compID) {


    spinnerResult = parseInt(document.getElementById('orderAmount_' + compID).value);

    totalMealsAtTheMoment = parseInt(document.getElementById("totalMeals").value);

    document.getElementById("totalMeals").value = totalMealsAtTheMoment + spinnerResult;

    document.getElementById('order').removeChild(div.parentNode);

    var changeTotalMeals = parseInt(document.getElementById("totalMeals").value);

    mealsId[compID] = "undefined";

    --counter;

}




function addSpinner() {

    var spinnerDiv = document.createElement('div');
    spinnerDiv.innerHTML = '<input type="number" name="Orderamount" id="orderAmount_' + counter + '" + min="0" max="6" step="1" value="1" >'
    document.getElementById('OrderAmount').appendChild(spinnerDiv);



}


function doCheck() {

    var collectionBox = document.getElementById("collections").checked;

    switch (collectionBox) {

        case true:

            document.getElementById("removalDiv").innerHTML = "";
            document.getElementById("TimeSlots").innerHTML =
                    '<labelB>' +
                    '<input id="rbnAfternoon" checked="checked"  name="time" type="radio" value="frame1" />      Between 11:00 and 13:00 <br>' +
                    '<input id="rbnLateAfternoon" # name="time" type="radio" value="frame2" />      Between 14:00 and 16:00<br>' +
                    '<input id="rbnEvening" # name="time" type="radio" value="frame3" />      Between 17:00 and 19:00<br>' +
                    '</labelB> <br><br>';


            break;

        default:

            document.getElementById("removalDiv").innerHTML = '<label id = "suburbLbl">Suburb</label><select name="Suburb" id="Suburb" onChange="timeSlotAdj()">' +
                    '<option hidden="" disabled="disabled" selected="selected">Please choose a suburb.</option><br>' +
                    ' </select><f id="suburbErr"></f>' +
                    ' <br> <br><label style = "width:200px" id = "addressLbl" >Delivery Address </label><textarea id="address" disabled="disabled" name="address" rows="5" cols="10" style = "width:396px"></textarea><f id="addressErr"></f><br><br>' +
                    '<label id= "addInfoLbl">Additional Information</label><input id="addInfo" disabled="disabled" name="addInfo" size="50" type="text" maxlength="35" minlength="0" /><br>';
            document.getElementById("TimeSlots").innerHTML = "Please select a suburb!<br><br>"
            listSuburbs('Suburb');

    }
}


function testFunction() {


    var nameField = document.getElementById("name").value.trim();
    var surnameField = document.getElementById("surname").value.trim();
    var altNumField = document.getElementById("altNum").value.trim();
    var contactNumField = document.getElementById("contactNum").value.trim();
    var email = document.getElementById("email").value.trim();
    var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
    var letters = /^[A-Za-z]+$/;
    var numFormat = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/;

    //Name	
    switch (nameField) {

        case "":

            document.getElementById("name").focus();

            alert("Please enter your name");

            break;

        default:

            if (nameField.value.match(letters)) {
                return true;
            } else {
                alert('Name must have alphabet characters only');
                nameField.focus();
                return false;
            }
    }


    switch (surnameField) {

        case "":


            document.getElementById("surname").focus();


            alert("surnamefield empty");

            break;

        default:

            alert("Correct!");
    }

    switch (altNumField) {

        case "":

            document.getElementById("altNum").focus();

            alert("altNumumber empty");

            break;

        default:

            alert("Correct!");
    }

}


function resetElements() {

    document.getElementById("collections").checked = false;


}

function checkMeals() {

    counter = 1;

    document.getElementById("fam1Err").innerHTML = "";
    document.getElementById("fam1").style.borderColor = "#ccc";


    var numberOfMeals = parseInt(document.getElementById("fam1").value);

    document.getElementById("order").innerHTML = "";

    order.innerHTML = '<div id="order"> <labelB> <input type="button" onClick = "addOrder()" value = "+" id="addOrderID" > </labelB> <input type="number" style="height:1em;width:1.5em" onchange="validateIfMoreMealsCanBeOrdered(\'0\')" name="Orderamount" id="orderAmount_0" + min="1" max="6" step="1" value="1" style="width:2.5em;height:1em" > <select name="size" style="height:2em" id="mealTypeSelector_0">' +
            ' <option value="Standard">Standard</option>' +
            ' <option value="Low Carb">Low Carb</option>' +
            '<option value="Kiddies">Kiddies</option></select> <input type = "text" style="height:1em" id = "orderAll_0" style="height:1.5em"> <input type = "text" style="height:1em" id = "orderExc_0"> </div>' +
            '<div id = "OrderAmount">';



    switch (numberOfMeals) {

        case 0:

            mealsId = ["undefined", "undefined", "undefined", "undefined", "undefined", "undefined"];

            mealSize = 0;

            changed = true;

            mealCount = 1;

            document.getElementById("totalMeals").value = 0;

            document.getElementById("addOrderID").disabled = false;
            document.getElementById("orderAmount_0").disabled = false;
            document.getElementById("mealTypeSelector_0").disabled = false;

            break;

        case 1:

            mealsId = ["undefined", "undefined", "undefined", "undefined", "undefined", "undefined"];

            mealCount = 2;

            mealSize = 2;

            changed = true;

            document.getElementById("totalMeals").value = 1;

            document.getElementById("addOrderID").disabled = false;
            document.getElementById("orderAmount_0").disabled = false;
            document.getElementById("mealTypeSelector_0").disabled = false;

            break;

        case 2:

            mealsId = ["undefined", "undefined", "undefined", "undefined", "undefined", "undefined"];

            mealCount = 3;

            mealSize = 3;

            changed = true;

            document.getElementById("totalMeals").value = 2;

            document.getElementById("addOrderID").disabled = false;
            document.getElementById("orderAmount_0").disabled = false;
            document.getElementById("mealTypeSelector_0").disabled = false;

            break;

        case 3:

            mealsId = ["undefined", "undefined", "undefined", "undefined", "undefined", "undefined"];

            mealCount = 4;

            mealSize = 4;

            changed = true;

            document.getElementById("totalMeals").value = 3;

            document.getElementById("addOrderID").disabled = false;
            document.getElementById("orderAmount_0").disabled = false;
            document.getElementById("mealTypeSelector_0").disabled = false;

            break;

        case 4:

            mealsId = ["undefined", "undefined", "undefined", "undefined", "undefined", "undefined"];

            mealCount = 5;

            mealSize = 5;

            changed = true;

            document.getElementById("totalMeals").value = 4;

            document.getElementById("addOrderID").disabled = false;
            document.getElementById("orderAmount_0").disabled = false;
            document.getElementById("mealTypeSelector_0").disabled = false;

            break;

        case 5:

            mealsId = ["undefined", "undefined", "undefined", "undefined", "undefined", "undefined"];

            mealSize = 6;

            mealCount = 6;

            changed = true;

            document.getElementById("totalMeals").value = 5;

            document.getElementById("addOrderID").disabled = false;
            document.getElementById("orderAmount_0").disabled = false;
            document.getElementById("mealTypeSelector_0").disabled = false;

            break;



    }


}


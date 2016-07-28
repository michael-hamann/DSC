var counter = 1;
var limit = 3;
var changed = false;
var mealSize = 0 ;
var mealCount = 0;
var spinnerResult;
var addMealsComponent = false;

function addOrder() {

	alert("addMealsComponent = "+addMealsComponent);

	if(addMealsComponent){ 
	
	
	var elementName = "orderAmount_";
		
	spinnerResult = parseInt(document.getElementById(elementName+counter).value);
		
	}else if(addMealsComponent!=true){
			
    spinnerResult = parseInt(document.getElementById("orderAmount_0").value);
		
    }
		
    var val = document.getElementById("fam1");

    var option = val.options[val.selectedIndex].text;

	var numberOfMealsCheck = parseInt(document.getElementById("totalMeals").value);
	
	var calculation = numberOfMealsCheck + spinnerResult;
	
	alert("spinner = "+spinnerResult);
	alert("numberOfMealsCheck = "+numberOfMealsCheck);
	alert("mealSize = "+mealSize);
	
	alert("calculation = "+calculation);
	
	alert(changed);
	
	if( numberOfMealsCheck + spinnerResult <= mealSize && changed == true ){
	
	
    if (counter <= 5) {

        var newdiv = document.createElement('div');

        newdiv.innerHTML =  '<labelA style = "margin-right: 51px" > </labelA> <input type="number" name="Orderamount" id="orderAmount_' + counter + '" + min="1" max="6" step="1" value="1" style="width:2.5em; height:1em" ><select name="size" id="mealTypeSelector_' + counter + '"><option value="Standard" id = 1>Standard</option><option value="Low Carb" id = 1 >Low Carb</option><option value="Kiddies" id = 1>Kiddies</option></select> <input type = "text" id = "orderAll_' + counter + '" style="height:0.5em"><input type = "text" id = "orderExc_' + counter + '" style="height:0.5em"><input type="Deletebutton" value="-" onclick="removeOrder(this)" style="height:0.5px">';

        document.getElementById('order').appendChild(newdiv);
		
		addMealsComponent = true;
		
		var changeTotalMeals = document.getElementById("totalMeals").value;
		
		var thisChange = changeTotalMeals - 1;
		
		document.getElementById("totalMeals").value = thisChange ;
		
		alert("changeTotalMeals = "+thisChange);
		
        counter++;

    } else if (counter > 5) {

        alert("too many fields!");

    }
	
	}else{
		
		alert("Meal Limit Reached!");
		
	}
	
	document.getElementById("addOrderID").blur();

}


/* function result(){
	
	var display = document.getElementById("orderAmount_0").value;
	
	alert(display);
	
} */


function removeOrder(div) {
	
    document.getElementById('order').removeChild(div.parentNode);

	var changeTotalMeals = document.getElementById("totalMeals").value;
		
	var thisChange = parseInt(changeTotalMeals + 1) ;
		
	document.getElementById("totalMeals").value = thisChange ;
	
	alert("thisChange = "+thisChange);
	
    --counter;
}




function addSpinner() {

    var spinnerDiv = document.createElement('div');
    spinnerDiv.innerHTML = '<input type="number" name="Orderamount" id="orderAmount_' + counter + '" + min="0" max="6" step="1" value="1" >'
    document.getElementById('OrderAmount').appendChild(spinnerDiv);



}


function doCheck(){

	var collectionBox = document.getElementById("collections").checked;

	switch(collectionBox){

		case true:

		alert ("collect it!");

		document.getElementById("address").disabled = true;
		document.getElementById("address").value = "16 & 17 Boulevard Park\nTienie Meyer Bypass\nBellville\n7530";

	 	jQuery(document).ready(function(){
        jQuery("#addInfo").remove();
		jQuery("#address").remove();
		jQuery("#addressLbl").remove();
		jQuery("#addInfoLbl").remove();
		jQuery("#space").remove();



        });



		break;

		default:

		//document.getElementById("address").disabled = false;
		//document.getElementById("address").value = "Delivery Address";

		jQuery(document).ready(function(){
		jQuery('<label id= "addInfoLbl">Additional Information</label><input id="addInfo" name="addInfo" size="50" type="text" maxlength="35" minlength="0" /> <br id = "space" > <br id = "space" >').insertBefore("#contactHr");
		jQuery('<label style = " width:200px " id = "addressLbl" > Delivery Address </label> <textarea id="address" name="address" rows="5" cols="10" style = "width:396px">Delivery Address</textarea>').insertBefore("#addInfoLbl");
        jQuery('<br id = "space" > <br id = "space" >').insertBefore("#addInfoLbl");
        });

		alert("we'll be there soon!");


	}


}


function testFunction(){


	var nameField = document.getElementById("name").value.trim();
	var surnameField = document.getElementById("surname").value.trim();
	var altNumField = document.getElementById("altNum").value.trim();
	var contactNumField = document.getElementById("contactNum").value.trim();
	var email = document.getElementById("email").value.trim();


	switch(nameField){

		case "":

		document.getElementById("name").focus();

		alert("namefield empty");

		break;

		default:

		alert("Correct!");
	}



	switch(surnameField){

		case "":


		document.getElementById("surname").focus();


		alert("surnamefield empty");

		break;

		default:

		alert("Correct!");
	}

	switch(altNumField){

		case "":

		document.getElementById("altNum").focus();

		alert("altNumumber empty");

		break;

		default:

		alert("Correct!");
	}




}


function resetElements(){

	document.getElementById("collections").checked = false;
	

}

function checkMeals(){
	
	
	
	var numberOfMeals = parseInt(document.getElementById("fam1").value);
	
	
	document.getElementById("order").innerHTML = "";
	
	order.innerHTML ='<div id="order"> <labelB> <input type="button" onClick = "addOrder()" value = "+" id="addOrderID" > </labelB> <input type="number" name="Orderamount" id="orderAmount_0" + min="1" max="6" step="1" value="1" style="width:2.5em; height:1em" ><select name="size" id="mealTypeSelector_0">'+
                          ' <option value="Standard">Standard</option>'+
                          ' <option value="Low Carb">Low Carb</option>'+
                           '<option value="Kiddies">Kiddies</option></select><input type = "text" id = "orderAll_0" style="height:0.5em"> <input type = "text" id = "orderExc_0" style="height:0.5em"> </div>'+
                           '<div id = "OrderAmount">';
	
	
	switch(numberOfMeals){
		
		case 0:
		
		mealSize = 0;
		
		changed = true;
		
		document.getElementById("totalMeals").value = 0;
		
		break;
		
		case 1:
		
		mealSize = 2;
		
		changed = true;
		
		document.getElementById("totalMeals").value = 1;
		
		break;
		
		case 2:
		
		mealSize = 3;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 2;
		
		break;
		
		case 3:
		
		mealSize = 4;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 3;
		
		break;
		
		case 4:
		
		mealSize = 5;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 4;
		
		break;
		
		case 5:
		
		mealSize = 6;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 5;
		
		break;
		
		default:
		
		alert("please choose a family size.");
		
		document.getElementById("fam1").focus();
		
		
	}
	
	
}


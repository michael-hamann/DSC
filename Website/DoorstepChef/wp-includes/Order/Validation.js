var counter = 1;
var limit = 3;
var changed = false;
var mealSize = null ;
var mealCount = 0;
var spinnerResult;
var addMealsComponent = false;
var sum = 0;
var substraction = 0;
var setMax= 3;
var familyCheckSuccess =false;
var totalMealsAtTheMoment;






function validateIfFamilySizeIsChecked(){
	
	var TotalMealsLeft	= document.getElementById("totalMeals").value;
	
	if(mealSize!=null){
	
	}else if(mealSize==null){
		
		alert("Please choose a family size.");
		document.getElementById("fam1").focus();	
		document.getElementById('orderAmount_0').value = 1;
		alert(mealSize);
		
	}
	
	validateIfMoreMealsCanBeOrdered();
	
	
}



function validateIfMoreMealsCanBeOrdered(){
	
	sum = 0;
	
	var TotalMealsLeft	= document.getElementById("totalMeals").value;
	
	alert("counter = "+counter);
	
	for(iterate = 0; iterate < counter ; iterate++){
		
		
		sum += parseInt(document.getElementById('orderAmount_'+(iterate)).value);
		
		
	}
	
	alert("sum = "+ sum);
	
	if(sum > mealSize){
		
		alert("Please Decrease Order Amount of delete an entire order");
		spinnerResult = parseInt(document.getElementById('orderAmount_'+(counter-1)).value);
		document.getElementById('orderAmount_'+(counter-1)).value = spinnerResult-1;
		
		
	}
	
	
	
	
}










function validateIfFamilySizeIsChecked(){
	
	if(mealSize!=null){
		
		
	}else{
		
		alert("please choose a family size.");
		document.getElementById("fam1").focus();	
		document.getElementById('orderAmount_0').value = 1;
		alert(mealSize);
		
	}
	
	
}









function addOrder() {

    if (counter <= 5) {

        var newdiv = document.createElement('div');

        newdiv.innerHTML =  '<labelA style = "margin-right: 51px" > </labelA> <input type="number" name="Orderamount" onChange= "validateIfMoreMealsCanBeOrdered()" id="orderAmount_' + counter + '" min="1" max="6" step="1" value="1" style="width:2.5em; height:1em" ><select name="size" id="mealTypeSelector_' + counter + '"><option value="Standard" id = 1>Standard</option><option value="Low Carb" id = 1 >Low Carb</option><option value="Kiddies" id = 1>Kiddies</option></select> <input type = "text" id = "orderAll_' + counter + '" style="height:0.5em"><input type = "text" id = "orderExc_' + counter + '" style="height:0.5em"><input type="Deletebutton" value="-" onclick="removeOrder(this)" style="height:0.5px">';

        document.getElementById('order').appendChild(newdiv);
		
		addMealsComponent = true;
		
		var changeTotalMeals = parseInt(document.getElementById("totalMeals").value);
		
		document.getElementById("totalMeals").value = 	changeTotalMeals - 1;
		
        counter++;

    } else if (counter > 5) {

        alert("too many fields!");

    }
	
	document.getElementById("addOrderID").blur();

}


function removeOrder(div) {
	
    document.getElementById('order').removeChild(div.parentNode);

	var changeTotalMeals = parseInt(document.getElementById("totalMeals").value);
	
	spinnerResult = parseInt(document.getElementById('orderAmount_'+(counter-1)).value);
	
	totalMealsAtTheMoment = parseInt(document.getElementById("totalMeals").value); 
	
	 document.getElementById("totalMeals").value = totalMealsAtTheMoment + spinnerResult;
	
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

		document.getElementById("removalDiv").innerHTML = "";
		
		break;

		default:

		document.getElementById("removalDiv").innerHTML = '<label id = "suburbLbl">Suburb</label><select name="Suburb" id="Suburb" onChange="timeSlotAdj()">'+
                                                         ' <option hidden="" disabled="disabled" selected="selected">Suburb</option><br>'+
														'  </select>'+
														 ' <br> <br><label style = "width:200px" id = "addressLbl" >Delivery Address </label><textarea id="address" name="address" rows="5" cols="10" style = "width:396px">Delivery Address</textarea><br><br>'+
														 ' <label id= "addInfoLbl">Additional Information</label><input id="addInfo" name="addInfo" size="50" type="text" maxlength="35" minlength="0" /><br>';
		
		 listSuburbs('Suburb');
		
		alert("we'll be there soon!");
	
	}
	}


function testFunction(){


	var nameField = document.getElementById("name").value.trim();
	var surnameField = document.getElementById("surname").value.trim();
	var altNumField = document.getElementById("altNum").value.trim();
	var contactNumField = document.getElementById("contactNum").value.trim();
	var email = document.getElementById("email").value.trim();
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;  
	var letters = /^[A-Za-z]+$/;
	var numFormat = /^\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/; 

	//Name	
	switch(nameField){

		case "":

		document.getElementById("name").focus();

		alert("Please enter your name");

		break;

		default:

		if(nameField.value.match(letters)){  
			return true;  
		}  
		else{  
			alert('Name must have alphabet characters only');  
			nameField.focus();  
			return false;  
		}
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
	
	order.innerHTML ='<div id="order"> <labelB> <input type="button" onClick = "addOrder()" value = "+" id="addOrderID" > </labelB> <input type="number" onchange="validateIfMoreMealsCanBeOrdered()" name="Orderamount" id="orderAmount_0" + min="1" max="6" step="1" value="1" style="width:2.5em; height:1em" ><select name="size" id="mealTypeSelector_0">'+
                          ' <option value="Standard">Standard</option>'+
                          ' <option value="Low Carb">Low Carb</option>'+
                           '<option value="Kiddies">Kiddies</option></select><input type = "text" id = "orderAll_0" style="height:0.5em"> <input type = "text" id = "orderExc_0" style="height:0.5em"> </div>'+
                           '<div id = "OrderAmount">';
	
	
	
	switch(numberOfMeals){
		
		case 0:
		
		mealSize = 0;
		
		changed = true;
		
		mealCount = 1 ;
		
		document.getElementById("totalMeals").value = 0;
		
		break;
		
		case 1:
		
		mealCount = 2 ;
		
		mealSize = 2;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 1;
		
		break;
		
		case 2:
		
		mealCount = 3 ;
		
		mealSize = 3;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 2;
		
		break;
		
		case 3:
		
		mealCount = 4 ;
		
		mealSize = 4;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 3;
		
		break;
		
		case 4:
		
		mealCount = 5 ;
		
		mealSize = 5;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 4;
		
		break;
		
		case 5:
		
		mealSize = 6;
		
		mealCount = 6 ;
		
		changed = true;
		
		document.getElementById("totalMeals").value  = 5;
		
		break;
		
		
		
	}
	
	
}


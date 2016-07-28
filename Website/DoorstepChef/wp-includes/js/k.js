

var counter = 1;
var limit = 3;

function addOrder(){
		
		if(counter <=6){
		
          var newdiv = document.createElement('div');
          newdiv.innerHTML = 'Order : <input type = "text" name = "order_'+counter+'"> <input type="button" value="-" onclick="removeKid(this)">';
          document.getElementById('order').appendChild(newdiv);
          counter++;
		  
		}
		
		else{
			
			Alert("too many fields!");
			
		}
     
}

function removeOrder(div) {	
    document.getElementById('order').removeChild( div.parentNode );
	counter--;
}
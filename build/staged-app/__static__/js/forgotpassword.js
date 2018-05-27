document.querySelector("#reset").addEventListener("click",sendmail);
var emailregex=new RegExp('^[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,4}$');

function sendmail()
{
	var email=document.querySelector("#email").value;
	if(!emailregex.test(email))
	  {
	  document.querySelector("#emailerror").innerHTML="Please enter valid email";
	  setTimeout(function(){ document.querySelector("#emailerror").innerHTML=""}, 4000);
	  document.querySelector("#email").classList.add("invalidtextboxes");
	  return false;
	  }
	var method="GET";  
	var url="/user/sendmail/"+email;  
	var xhr=new XMLHttpRequest(); 
    xhr.open(method,url,true);
    xhr.onload=function(){
    	if (xhr.readyState == 4 && xhr.status == 200) {
    		
    		var data=xhr.response;
	    	var jsonResponse = JSON.parse(data);
	    	if(jsonResponse.success){
	    		
	    		document.querySelector("#success").innerHTML=jsonResponse.message;   
	    		 
	    	    }
	    	 else
	    		{
	    		 document.querySelector("#emailerror").innerHTML=jsonResponse.message;
	    		  setTimeout(function(){ document.querySelector("#emailerror").innerHTML=""}, 4000);
	    		
	    		}
	   } 
    }
   	xhr.send();  
}
function keypress(id){
 	document.querySelector("#"+id).classList.remove("invalidtextboxes");	
 }
 
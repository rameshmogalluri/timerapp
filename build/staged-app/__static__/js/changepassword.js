document.querySelector("#change").addEventListener("click",changepassword);
var passregex=new RegExp('^$');
function changepassword()
{
	var password=document.querySelector("#password").value; 
	var retypepwd=document.querySelector("#cnfpassword").value;
	var randstring=document.querySelector("#randstring").value;  
	
	if(passregex.test(password))
	  {
	     document.querySelector("#pwdmsg").innerHTML="Please enter valid password";
  	     setTimeout(function(){ document.querySelector("#signinerror").innerHTML=""}, 1000);
		  return false;
	  }
	else if(passregex.test(retypepwd))
	  {
	     document.querySelector("#pwdmsg").innerHTML="Please enter valid conform password";
	     setTimeout(function(){ document.querySelector("#signinerror").innerHTML=""}, 1000);
		  return false;
	  }
	else if(password != retypepwd)
		{
		document.querySelector("#pwdmsg").innerHTML="Password and Conform password should match";
	     setTimeout(function(){ document.querySelector("#signinerror").innerHTML=""}, 1000);
		  return false;
		}
	
	var method="POST";
	var url="/user/resetpassword/"+randstring;
	var resetdata={
		"password":password
	};
	var xhr=new XMLHttpRequest();
    xhr.open(method,url,true);
    xhr.onload=function(){
    	if (xhr.readyState == 4 && xhr.status == 200) {
    		
    		var data=xhr.response;
	    	var jsonResponse = JSON.parse(data);
	    	console.log(jsonResponse);
	    	if(jsonResponse.success){
	    		document.querySelector("#success").innerHTML=jsonResponse.message;
	    		setTimeout(function(){ location.href="/index.jsp"}, 10000);
  
	    	    }
	    	 else
	    		{
	    		document.querySelector("#pwdmsg").innerHTML=jsonResponse.message;
	    		setTimeout(function(){ document.querySelector("#pwdmsg").innerHTML="" }, 4000);
	    		}
	   } 
    }
    var requestdata=JSON.stringify(resetdata);
    xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(requestdata);  
    
}
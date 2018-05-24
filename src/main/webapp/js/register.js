
var error=document.querySelector("#signinerror").textContent.trim();
        if(error)
        	document.querySelector("#login").click();
        setTimeout(function(){ document.querySelector("#signinerror").style.display="none" }, 4000);
        
        setTimeout(function(){ document.querySelector("#gsigninerror").style.display="none" }, 4000);
        
document.querySelector("#register").addEventListener("click",register);
document.querySelector("#signinbutton").addEventListener("click",login);
document.querySelector("#GoogleSigninButton").addEventListener("click",googleoauth);

var nameregex=new RegExp('^[a-zA-Z][a-zA-Z0-9\\s]*$');
var emailregex=new RegExp('^[a-z0-9._%+-]+@[a-z0-9.-]+[.][a-z]{2,4}$');
var phoneregex=new RegExp('^[789][0-9]{9}$');
var passregex=new RegExp('^$');

function register()
 {
	var name=document.querySelector("#name").value;
	var email=document.querySelector("#email").value;
	var phonenumber=document.querySelector("#mobilenumber").value;
	var address=document.querySelector("#address").value;
	var password=document.querySelector("#password").value;
	
	  if(!nameregex.test(name))
		{
		  document.querySelector("#signuperror").innerHTML="Please enter valid name";
		  setTimeout(function(){ document.querySelector("#signuperror").innerHTML=""}, 1000);
		  return false;
		}
	 
	  else if(!emailregex.test(email))
	  {
	  document.querySelector("#signuperror").innerHTML="Please enter valid email";
	  setTimeout(function(){ document.querySelector("#signuperror").innerHTML=""}, 1000);
	  return false;
	  }
	  else if(phonenumber != "")
	  {
	    if(!phoneregex.test(phonenumber))
	    	{
	    	 document.querySelector("#signuperror").innerHTML="Please enter valid mobile number";
	    	 setTimeout(function(){ document.querySelector("#signuperror").innerHTML=""}, 1000);
			  return false;
	    	}
	    
	  }
	  else if(passregex.test(password))
		  {
		     document.querySelector("#signuperror").innerHTML="Please enter valid password";
	    	 setTimeout(function(){ document.querySelector("#signuperror").innerHTML=""}, 1000);
			  return false;
		  }
	      var requestdata={
	    	"name":name,
	    	"email":email,
	    	"mobileNumber":phonenumber,
	    	"address":address,
	    	"password":password
	      };
	      
	      var method="POST";
	      var url="/user/create";
	      var xhr=new XMLHttpRequest();
	      xhr.open(method,url,true);
	      xhr.onload=function(){
		  if (xhr.readyState == 4 && xhr.status == 200) {
			 
			var data=xhr.response;
	    	var jsonResponse = JSON.parse(data);
	    	if(jsonResponse.Success){
	    		
	    		location.href = "/welcome.jsp";	
	    	}
	    	else
	    		 {
	    		document.querySelector("#signuperror").innerHTML=jsonResponse.message;
	    		document.querySelector("#signuperror").style.display="block";
	    		setTimeout(function(){ document.querySelector("#signuperror").style.display="none" }, 4000);
	    		 }
			    
		}
	}
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(JSON.stringify(requestdata));
  }
 function login()
 {
	 var username=document.querySelector("#username").value;
		var password=document.querySelector("#loginpassword").value;
		if((!emailregex.test(username)) && (!phoneregex.test(username)))
		{
			document.querySelector("#signuperror").innerHTML="Please enter valid Email or Phonenumber";
			  setTimeout(function(){ document.querySelector("#signuperror").innerHTML=""}, 1000);
			  return false;
		}
		else if(passregex.test(password))
		  {
		     document.querySelector("#signuperror").innerHTML="Please enter valid password";
	    	 setTimeout(function(){ document.querySelector("#signuperror").innerHTML=""}, 1000);
			  return false;
		  }

		var method="POST";
		var url="/user/login";
		var logindata={
			"email":username,
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
		    		
		    		location.href = "/welcome.jsp";
		    		
		    	    }
		    	 else
		    		{
		    		document.querySelector("#signinerror").innerHTML=jsonResponse.message;
		    		document.querySelector("#signinerror").style.display="block";
		    		setTimeout(function(){ document.querySelector("#signinerror").style.display="none" }, 4000);
		    		}
		   } 
	    }
	    var requestlogin=JSON.stringify(logindata);
	    xhr.setRequestHeader("Content-type", "application/json");
		xhr.send(requestlogin);  
 }
 
 function googleoauth()
 {
	url="https://accounts.google.com/o/oauth2/v2/auth?"
		 +"scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fdrive.metadata.readonly&"
		 +"redirect_uri=https://timerapp-204808.appspot.com/signinwithgoogle&"
		 +"response_type=code&"
		 +"client_id=280361308016-ebcqotaemi6n5k5gj6rn1fuju5jv9fdg.apps.googleusercontent.com"
		location.href=url;
 }
 
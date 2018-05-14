var id=document.querySelector("#userid").value;
  document.querySelector("#edit").addEventListener("click",main);
  document.querySelector("#close").addEventListener("click",close);
  document.querySelector("#updatebtn").addEventListener("click",update);
  document.querySelector("#deactivate").addEventListener("click",deactivate); 
  document.querySelector("#logout").addEventListener("click",logout);
  
  
  function deactivate(){
	  
	  var xhr= new XMLHttpRequest();
	    var url= "/user/deactivate/"+id;
	    xhr.onload = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    var result = xhr.response;
	    var parsedResult = JSON.parse(result);
	    location.href = "/index.jsp";
	      }
	    };
	    xhr.open("PUT",url,true);
	    xhr.send();
  }
    function main(){
    	document.querySelector("#edit").style.display='none';
    	
    	document.querySelector("#imagediv").style.display='none';
    	var a=document.getElementsByClassName("sessiondata");
    	for(i=0;i<a.length;i++)
          a[i].style.display='none';  
    	var b=document.getElementsByClassName("data")
    	for(i=0;i<b.length;i++)
            b[i].style.display='block';
    	
    }
    function close() 
      {
    	document.querySelector("#edit").style.display='block';
    	document.querySelector("#imagediv").style.display='block';
    	var a=document.getElementsByClassName("sessiondata");
    	for(i=0;i<a.length;i++)
          a[i].style.display='block';  
    	var b=document.getElementsByClassName("data")
    	for(i=0;i<b.length;i++)
            b[i].style.display='none'; 
      }
    function logout()
    {
    var xhr= new XMLHttpRequest();
    var url= "/user/logout";
    xhr.onload = function() {
    if (this.readyState == 4 && this.status == 200) {
    var result = xhr.response;
    var parsedResult = JSON.parse(result);
    location.replace("/");
    }
    };
    xhr.open("GET",url,true);
    xhr.send();
    }
    function update()
      {
    	try{
    		document.querySelector("#edit").style.display='block';
    		var phoneregex=new RegExp('^[789][0-9]{9}$');
    		var nameregex=new RegExp('^[a-zA-Z][a-zA-Z0-9\\s]*$');
    		
    	    var name=document.querySelector("#name").value;
    	    var phonenumber=document.querySelector("#mobileNumber").value;
    	    var address=document.querySelector("#address").value;
    	
    	if(!nameregex.test(name))
		{
    		document.querySelector("#updated").style.display="none";
    		document.querySelector("#nochanges").style.display="none";
    		document.querySelector("#error").style.display="block";
		  document.querySelector("#error").innerHTML="Please enter valid name";
		  setTimeout(function(){ document.querySelector("#error").innerHTML=""}, 4000);
		  document.querySelector("#edit").style.display='none';
		  return false;
		}
     else if(phonenumber != "")
   	  {
   	    if(!phoneregex.test(phonenumber))
   	    	{
   	    	document.querySelector("#updated").style.display="none";
   	    	document.querySelector("#nochanges").style.display="none";
   	    	document.querySelector("#error").style.display="block";
   	    	 document.querySelector("#error").innerHTML="Please enter valid mobile number";
   	    	 setTimeout(function(){ document.querySelector("#error").innerHTML=""}, 1000);
   	    	document.querySelector("#edit").style.display='none';
   			  return false;
   	    	}
   	    
   	  }
    	var method="PUT";
    	var url="/user/update/"+id;
    	var updatedetails={
    		"name":name,
    		"mobileNumber":phonenumber,
    		"address":address
    	};
    	 var xhr=new XMLHttpRequest();	
    	 xhr.open(method,url,true);
    	 xhr.onload = function () {
    		    if (xhr.readyState == 4 && xhr.status == 200) {
    		      
    		    	var data=xhr.response;
    		    	var jsonResponse = JSON.parse(data);
    		    	if(jsonResponse.success === true)
    		    		{
    		            document.querySelector("#sessionname").innerHTML=jsonResponse.name;
    		            document.querySelector("#name").value=jsonResponse.name;
    		            document.querySelector("#welcomename").innerHTML=jsonResponse.name;
    		       
    		            document.querySelector("#sessionphoneno").innerHTML=jsonResponse.phoneno;
    		            document.querySelector("#mobileNumber").value=jsonResponse.phoneno;
    		            document.querySelector("#sessionadress").innerHTML=jsonResponse.address;
    		            document.querySelector("#address").value=jsonResponse.address;
    		            document.querySelector("#updated").style.display="block";
    		            document.querySelector("#nochanges").style.display="none";
    		            document.querySelector("#close").click();
    		            document.querySelector("#modalclose").click();
    		           
    		            setTimeout(function(){ document.querySelector("#updated").style.display="none" }, 4000);
    		            
    		    		}
    		    	else
    		    		{
    		    		document.querySelector("#nochanges").style.display="block";
    		    		document.querySelector("#updated").style.display="none";
    		    		document.querySelector("#modalclose").click();
    		    		document.querySelector("#close").click();
    		    		setTimeout(function(){ document.querySelector("#nochanges").style.display="none" }, 4000);
    		    		}
    		    }
    		  }
    	      xhr.setRequestHeader("Content-type", "application/json");
    		  xhr.send(JSON.stringify(updatedetails));   
    	}
    	catch(error)
    	   {
    		console.log(error); 
    	   }
      }
document.querySelector("#path").addEventListener("click",checkpathparam);
document.querySelector("#payload").addEventListener("click",checkpayload);
var username="Ramesh";
var password="rammi";
function checkpathparam()
  {
	 var method="POST";
     var url="/pathparameters/"+username+"/"+password;
     var xhr=new XMLHttpRequest();
     xhr.open(method,url,true);
     xhr.onload=function(){
		  if (xhr.readyState == 4 && xhr.status == 200) {
			 
			var data=xhr.response;
	    	var jsonResponse = JSON.parse(data);
	    	console.log(jsonResponse);
			    
		}
	}
xhr.send();
  }
function checkpayload()
{
	 var method="POST";
     var url="/payloadparameters";
     var xhr=new XMLHttpRequest();
     var data={
    	'username':username,
    	'password':password
     }
     var json=JSON.stringify(data); 
     xhr.open(method,url,true);
     xhr.onload=function(){
		  if (xhr.readyState == 4 && xhr.status == 200) {
			 
			var data=xhr.response;
	    	var jsonResponse = JSON.parse(data);
	    	console.log(jsonResponse);
			    
		}
	}
xhr.send(json);

}
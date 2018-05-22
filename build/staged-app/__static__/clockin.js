 document.getElementById("clockin").addEventListener("click",clockin); 
 
  var d=new Date();
  var intime=d.getTime();
  
  function clockin(){
	  
	 var xhr= new XMLHttpRequest();
	 var url="/user/clockinwithjs/"+intime;
	        
	 xhr.onload = function() {   
		    if (this.readyState == 4 && this.status == 200) {
		        var result = xhr.response;
		        var parsedResult = JSON.parse(result);
		        if(parsedResult.success){
		        	
		        	document.getElementById("time").innerHTML=parsedResult.time;
		        }
		        
		      }
		    };
	  xhr.open("GET",url,true);
	  xhr.send(); 
 }
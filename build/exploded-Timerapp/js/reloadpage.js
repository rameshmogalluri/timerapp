

function pagereload(){
	
   try{
	   var xhr= new XMLHttpRequest(); 
		var url="/user/timerinfo/"+id; 
		xhr.open("GET",url,true)
		 xhr.onload=function(){
		   if (this.readyState == 4) {  
			      var result = xhr.response;
		          var parsedResult = JSON.parse(result);
		          if (parsedResult.success) {
		        	
		        	   		        
		            }  
		   } 
		};
		xhr.send(); 
   }	
   catch(error)
   {
 	console.log(error); 
   }
}
var id=document.querySelector("#userid").value;
document.querySelector("#clockin").addEventListener("click",clockin);
document.querySelector("#clockout").addEventListener("click",clockout); 

var seconds=document.querySelector("#lblsec");
var minutes=document.querySelector("#lblmin");
var hours=document.querySelector("#lblhr"); 

var sec=0;
var min=0;
var hrs=0;

 function clockin(){  
	 var xhr= new XMLHttpRequest();
	 var url="/user/clockin/"+id;
	        
	 xhr.onload = function() {   
		    if (this.readyState == 4 && this.status == 200) {
		        var result = xhr.response;
		        var parsedResult = JSON.parse(result);
		        if(parsedResult.success){
		        	
		        	document.querySelector("#entry").value=parsedResult.entryid;
		        }
		        timer();
		      }
		    };
	  xhr.open("POST",url,true);
	  xhr.send(); 
 }
 function clockout(){  
	 var entryid=document.querySelector("#entry").value;
	 var xhr= new XMLHttpRequest();
	 var url="/user/clockout/"+id+"/"+entryid; 
	        
	 xhr.onload = function() {   
		    if (this.readyState == 4 && this.status == 200) {
		        var result = xhr.response;
		        var parsedResult = JSON.parse(result);
		        clearTimeout(t); 
		      }
		    };
	  xhr.open("PUT",url,true);
	  xhr.send(); 
 }
 function timer()
   {
	 t = setTimeout(add, 1000);
   }
 function add(){
	 sec++;
	 if(sec>=60){
		min++;
		sec=0;
		if(min>=60){
			hrs++;
			min=0;
		}
	 }
	 if(sec<=9)
	 seconds.innerHTML="0"+sec;
	 else
		 seconds.innerHTML=sec;
	 if(min<=9)
		 minutes.innerHTML="0"+min;
		 else
			 minutes.innerHTML=min;
	 if(hrs<=9)
		 hours.innerHTML="0"+hrs;
		 else
			 hours.innerHTML=hrs; 
	 timer();
 }
var id=document.querySelector("#userid").value;
document.querySelector("#clockin").addEventListener("click",clockin);
document.querySelector("#clockout").addEventListener("click",clockout); 
document.querySelector("#entrydelete").addEventListener("click",timerentrydelete);
document.addEventListener("DOMContentLoaded",pagereload);
document.querySelector("#clockout").disabled = true;
var seconds=document.querySelector("#lblsec");
var minutes=document.querySelector("#lblmin");
var hours=document.querySelector("#lblhr"); 

var entrylist=document.querySelector("#entrylist"); 


var sec=0;
var min=0;
var hrs=0;

function createdaydiv(divid,intimeday,outtimeday,duration,entryid)
{
	 var myEle = document.getElementById(divid);
	 var ele;
	 var ele1;
	 if(myEle === null)   
	  {
	  ele = document.createElement("div");
	 entrylist.appendChild(ele);
	 ele.classList.add("timeinfo"); 
	 ele.id=divid;     
	 
	 var innerdiv=document.createElement("div");
	 ele.appendChild(innerdiv);
	 innerdiv.classList.add("date");

	 
	 var span1=document.createElement("span");
	 innerdiv.appendChild(span1);
	 span1.innerHTML=divid+"               "; 
	 span1.id=divid+"day format";
	 
	 var span2=document.createElement("span");
	 innerdiv.appendChild(span2);
	 span2.innerHTML="0 h 0 m";
	 span2.classList.add("duration");
	 span2.id=divid+"duration";
	 
	 var link=document.createElement("a");
	 innerdiv.appendChild(link);
	 link.href="#";
	 var temp=divid+"btn";
	 var temp1=temp.replace(/ /g,"_"); 
	 link.id=temp1; 
	 link.onclick = function() { buttonclick(temp1) }; 
	 
	 
	 
	 var span6=document.createElement("span");
	 link.appendChild(span6);
	 span6.classList.add("glyphicon");
	 span6.classList.add("glyphicon-menu-down");
	 span6.classList.add("displaybuttons");
	 
	  ele1 = document.createElement("div"); 
	 ele1.id=entryid;
	 ele1.classList.add("entrylist");
	 ele.appendChild(ele1);
	}
	  else
		 {
	   ele1 = document.createElement("div"); 
	   ele1.id=entryid;
	   ele1.classList.add("entrylist");
	   myEle.appendChild(ele1);  
		 }
	 var span3=document.createElement("span");
	 ele1.appendChild(span3);
	 span3.innerHTML=intimeday;
	 span3.classList.add("timeslist");
	 span3.id=intimeday;
	 
	 var span4=document.createElement("span");
	 ele1.appendChild(span4);
	 if(outtimeday === null)
		 {
		 span4.innerHTML="Ongoing"; 
		 span4.id="clockoutentry";
		 }
	 else
		 {
	     span4.innerHTML=outtimeday;
	     span4.id=outtimeday;
		 }
	 span4.classList.add("timeslist");
	 
	 
	 var span5=document.createElement("span");
	 ele1.appendChild(span5);
	 span5.innerHTML=duration;
	 span5.classList.add("timeslist");
	 
	 if(outtimeday != null)
	 {
	 var link=document.createElement("a");
	 ele1.appendChild(link);
	 link.href="#"; 
	 link.onclick = function() { deleteclick(link.parentElement.id) }; 
	 
	 var span7=document.createElement("span");
	 link.appendChild(span7);
	 span7.classList.add("glyphicon");
	 span7.classList.add("glyphicon-trash");
	 span7.classList.add("displaybuttons");
	 span7.setAttribute("data-toggle","modal"); 
	 span7.setAttribute("data-target","#deleteentry");
   }
}
var todaysec=0;
var todaymin=0;
var todayhrs=0;

var dayOverallhrs=0;
var dayOverallmin=0;
var dayhrs=0;
var daymin=0;
var tempdayformat;
var tempdayformat1;
function calculateduration(intime,outtime,dayformat,todaydayformat,completedstatus,currentdate){
	tempdayformat=dayformat;
	
	  if(!completedstatus)
		 {	
		   outtime=currentdate;
		 }
	var diff =Math.abs(outtime-intime);     
	var diffSeconds =parseInt(diff / 1000 % 60);
	var diffMinutes =parseInt(diff / (60 * 1000) % 60);
	var diffHours = parseInt(diff / (60 * 60 * 1000) % 24);
	
	//var duration=diffHours+" h "+diffMinutes+" m";
	
	 var temphours=diffHours;
	 var tempminutes=diffMinutes;
	 
	 if(temphours<=9)
		 temphours="0"+temphours;
	 if(tempminutes<=9)
		 tempminutes="0"+tempminutes;
	 var duration=temphours+" h "+tempminutes+" m"
	
	if(dayformat === todaydayformat)
		{ 
		   todaysec=sec+=diffSeconds;
		   todaymin=min+=diffMinutes;
		   todayhrs=hrs+=diffHours;
		     while(todaysec>=60)
		    	 {
		    	 todaymin++;
		    	 todaysec=todaysec-60;
		    	 }
		     while(todaymin>=60)
		    	 {
		    	 todayhrs++;
		    	 todaymin=todaymin-60;
		    	 }
		     sec=todaysec;
		     min=todaymin;
		     hrs=todayhrs;
		     if(todaysec<=9)
		    	 seconds.innerHTML="0"+todaysec;
		    	 else
		    		 seconds.innerHTML=todaysec;
		    	 if(todaymin<=9)
		    		 minutes.innerHTML="0"+todaymin;
		    		 else
		    			 minutes.innerHTML=todaymin;
		    	 if(todayhrs<=9)
		    		 hours.innerHTML="0"+todayhrs;
		    		 else
		    			 hours.innerHTML=todayhrs;  
		    	 
		}
	
	if(tempdayformat!=tempdayformat1){
		 dayOverallhrs=0;
		 dayOverallmin=0;
		 daymin=0;
		 dayhrs=0;
		tempdayformat1=tempdayformat;
	}
	if(tempdayformat === tempdayformat1)
		{
		   dayOverallhrs=dayhrs+=diffHours;
		    dayOverallmin=daymin+=diffMinutes;
		    
			     while(dayOverallmin>=60)
			    	 {
			    	 dayOverallhrs++;
			    	 dayOverallmin=dayOverallmin-60;
			    	 }
			     
			     daymin=dayOverallmin;
			     dayhrs=dayOverallhrs;   
			     if(dayOverallmin<=9)
			    	 {
			    	 dayOverallmin="0"+dayOverallmin;
			    	 }
			     if(dayOverallhrs<=9)
		    	 {
			    	 dayOverallhrs="0"+dayOverallhrs;
		    	 }
		}
    
    return duration;
}
 function buttonclick(id)
{
	 $("#"+id).parent().siblings().toggle();  
}
 
 function deleteclick(parentid){
	 console.log(parentid);
	 var modal = document.getElementById('myModal');
	 modal.style.display = "block";
	document.getElementById("hideentryid").value=parentid;
 }
 
 var modal = document.getElementById('myModal');
 var span = document.getElementsByClassName("close")[0];
 span.onclick = function() {
	    modal.style.display = "none";
	}
 var no=document.getElementById('no');
 no.onclick = function() {
	    modal.style.display = "none";
	} 
 
 window.onclick = function(event) {
	    if (event.target == modal) {
	        modal.style.display = "none";
	    }
	}
 
 function timerentrydelete()
 {
	var entryid=document.getElementById("hideentryid").value;
	console.log(entryid); 
	
	try{
		var xhr= new XMLHttpRequest();
	    var url= "/user/timerentry/delete/"+entryid;
	    xhr.onload = function() {
	    if (this.readyState == 4 && this.status == 200) {
	    var result = xhr.response;
	    var parsedResult = JSON.parse(result);
	           if(parsedResult.success)
	        	    {
	        	     location.reload();  
	        	    }
	      }
	    };
	    xhr.open("DELETE",url,true);
	    xhr.send();
		
	}
	catch(error)
	   {
	 	console.log(error);  
	   }
 }
 
 
function pagereload(){
	
	   try{ 
		   var xhr= new XMLHttpRequest(); 
			var url="/user/timerentries/"+id;  
			xhr.open("GET",url,true)
			 xhr.onload=function(){
			   if (this.readyState == 4 && this.status == 200) {  
				      var result = xhr.response;
			          var parsedResult = JSON.parse(result);
			          if (parsedResult.success) {
			        	
			        	   	for(i=parsedResult.timeentry.length-1;i>=0;i--)	{
			        	   	 
			        	   		var duration=calculateduration(parsedResult.timeentry[i].inTime,parsedResult.timeentry[i].outTime,parsedResult.timeentry[i].dayformat,parsedResult.todaydate,parsedResult.timeentry[i].completed,parsedResult.currentdate); 
			        	   		createdaydiv(parsedResult.timeentry[i].dayformat,parsedResult.timeentry[i].intimeday,parsedResult.timeentry[i].outtimeday,duration,parsedResult.timeentry[i].id);
			        	   		document.getElementById(parsedResult.timeentry[i].dayformat+"duration").innerHTML=dayOverallhrs+" h "+dayOverallmin+" m";
			        	   		if(!(parsedResult.timeentry[i].completed)) 
			        			 {	
			        	   		   document.getElementById("entry").value = parsedResult.timeentry[i].id;
			        	   		   add();
			     	        	   document.getElementById("clockin").disabled = true;
			     	        	   document.getElementById("clockout").disabled = false;
			        			 }
			        	   	 }   
			        	   	var todaydurationspan=document.getElementById(parsedResult.todaydate+"duration");
			        	   	  if(todaydurationspan != null)
			        	   		  {
			        	   		   var temptodayhrs=todayhrs;
			        	   		   var temptodaymin=todaymin;
			        	   		if(temptodaymin<=9)
						    	 {
			        	   			temptodaymin="0"+todaymin;
						    	 }
						         if(temptodayhrs<=9)
					    	       {
						    	    temptodayhrs="0"+todayhrs;
					    	       }
			        	   		   todaydurationspan.innerHTML=temptodayhrs+" h "+temptodaymin+" m";
			        	   		 }
			        	   	
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


 function clockin(){  
	 var xhr= new XMLHttpRequest();
	 var url="/user/clockin/"+id;
	        
	 xhr.onload = function() {   
		    if (this.readyState == 4 && this.status == 200) {
		        var result = xhr.response;
		        var parsedResult = JSON.parse(result);
		        if(parsedResult.success){
		        	document.querySelector("#clockin").disabled = true;
		        	document.querySelector("#clockout").disabled = false;
		        	
		        	
		        	var duration=0+" h "+0+" m"; 
		        	document.querySelector("#entry").value=parsedResult.runningentry.id;
        	   		createdaydiv(parsedResult.runningentry.dayformat,parsedResult.runningentry.intimeday,parsedResult.runningentry.outtimeday,duration,parsedResult.runningentry.id);
        	   		//timer();
        	   		location.reload();
		        }
		        
		      }
		    };
	  xhr.open("POST",url,true);
	  xhr.send(); 
 }
 function clockout(){  
	 var entryid=document.querySelector("#entry").value;
	 var xhr= new XMLHttpRequest();
	 var url="/user/clockout/"+entryid;  
	 xhr.open("GET",url,true);
	 
	 xhr.onload = function() {   
		    if (this.readyState == 4 && this.status == 200) {
		        var result = xhr.response;
		        var parsedResult = JSON.parse(result);
		        if(parsedResult.success)
		         {
		           document.querySelector("#clockin").disabled = false;
		           document.querySelector("#clockout").disabled = true;
		        		           
		           var duration=calculateduration(parsedResult.clockoutentry.inTime,parsedResult.clockoutentry.outTime,parsedResult.clockoutentry.dayformat,parsedResult.clockoutentry.dayformat,parsedResult.clockoutentry.completed,""); 
		          var clockoutspan= document.getElementById("clockoutentry").innerHTML=parsedResult.clockoutentry.outtimeday;
		          document.getElementById("clockoutentry").id=parsedResult.clockoutentry.outtimeday;
		          document.getElementById(parsedResult.clockoutentry.outtimeday).nextSibling.innerHTML=duration;
		         var parentdiv= document.getElementById(parsedResult.clockoutentry.id);
		         
		         var link=document.createElement("a");
		         parentdiv.appendChild(link);
		    	 link.href="#"; 
		    	 link.onclick = function() { deleteclick(parsedResult.clockoutentry.id) };   
		    	 
		    	 var span7=document.createElement("span");
		    	 link.appendChild(span7);
		    	 span7.classList.add("glyphicon");
		    	 span7.classList.add("glyphicon-trash");
		    	 span7.classList.add("displaybuttons");
		    	 
		           clearTimeout(t);
		         }
		      }
		    };
	   
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

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
    <%response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
if(session.getAttribute("user") == null)
	response.sendRedirect("/index.jsp");
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <title>Home</title>
      
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"  href=".//style.css" />
  </head>
  <body>
  
  <%
    if(session.getAttribute("user") != null)
    {
  %>
       <p class="updatenotification" id="nochanges" style="display:none">
      No changes to Modify
      </p>
      <p class="updatenotification" id="updated" style="display:none">
      Profile Updated Successfully
      </p>
       <p class="updatenotification" id="error" style="display:none">
      
      </p>
      
      <div style="margin-left:1000px"><h3>Welcome <span id="welcomename">${sessionScope.user.name}</span></h3>
      </div>
    <div class="maindiv">
     
       <div class="innerdiv">
        
       <br>
       <button type="button" class="btn btn-default btn-sm" style="margin-left:580px" id="edit"> 
          <span class="glyphicon glyphicon-pencil"></span> EDIT 
        </button>
       <br>
       <table class="table table-striped"> 
       <thead>
       <tr>
          <th>Your Details are:</th>
       </tr>
       </thead>
       <tbody>
       <tr>
       <td>Name</td>
        <td class="sessiondata" id="sessionname">${sessionScope.user.name}</td>
        <td class="data"><input type="text" value="${sessionScope.user.name}" name="name" id="name" required></td>
        </tr>
       <tr>
       <td>Email</td>
        <td class="sessiondata" id="sessionemail">${sessionScope.user.email}</td>
        <td class="data"><input type="email" value="${sessionScope.user.email}" name="email" id="email" disabled readonly></td>
        </tr>  
      <tr>
      <td>Mobile Number</td>
        <td class="sessiondata" id="sessionphoneno">${sessionScope.user.mobileNumber}</td>
        <td class="data"><input type="tel" pattern="[789][0-9]{9}" title="Please Enter valid Mobile number" value="${sessionScope.user.mobileNumber}" name="mobileNumber" id="mobileNumber"></td>
        </tr>
        <tr>
        <td> Address</td>
        <td class="sessiondata" id="sessionadress">${sessionScope.user.address}</td>
         <td class="data"><input type="text" value="${sessionScope.user.address}" name="address" id="address"></td>
        </tr> 
        <tr> 
         <td class="data"><input type="hidden" value="${sessionScope.user.id}"  id="userid"></td>
        </tr>       
     </tbody>
        </table>
          <div class="data">
             <button type="button"  class="btn buttons" id="updatebtn">UPDATE</button>
             <button type="button"  class="btn buttons" id="close">BACK</button>
        
           </div>
        
        <div style="margin-left:450px;margin-right:40px">
         <button type="button" class="btn btn-default btn-sm" data-toggle="modal" data-target="#myModal">
          <span class="glyphicon glyphicon-remove"></span> Close Account 
        </button>
        
         <button type="button" class="btn btn-default" id="logout">LOGOUT</button> 
       
        <!-- Modal -->
  <div class="modal fade" id="myModal" role="dialog">
    <div class="modal-dialog">
    
      <!-- Modal content-->
      <div class="modal-content">
        <div class="modal-header">
          <button type="button" class="close" data-dismiss="modal">&times;</button>
          
        </div>
        <div class="modal-body">
          <p>Do you want to permanently delete the account?</p>  
        </div>
        <div class="modal-footer">
        <a href="/user/delete"><button type="button" class="btn btn-default" id="deactivate">YES</button></a>
          <button type="button" class="btn btn-default" data-dismiss="modal">NO</button>
        </div>
      </div>
      
    </div>
  </div>
    
       </div>
        </div> 
      </div>
  <%}%>
   <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>			 	
  <script src=".//js/main.js"></script>  
  </body>
</html>
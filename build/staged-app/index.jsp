<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%response.setHeader("Cache-Control","no-store"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0);
if(session.getAttribute("user") != null)
	response.sendRedirect("/welcome.jsp"); 
%>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" lang="en">
  <head>
    <meta http-equiv="content-type" content="application/xhtml+xml; charset=UTF-8" />
    <meta name="google-signin-client_id" content="396012987819-mq6f3iqjj7co9gsel1dbcq9mf8m208h8.apps.googleusercontent.com">
    <title>SIGNUP/SIGNIN</title>
      
     <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"  href="style.css" />
  </head>
  <body>
    <div class="maindiv">
       <br>                
        <p  id="signinerror" class="error">
      </p>
      <p  class="error" id="signuperror">
          
      </p>
        
       <div class="innerdiv">
       <ul>
         <li><a data-toggle="tab" href="#signup">SIGN UP</a></li>
         <li><a data-toggle="tab" href="#signin" id="login">SIGN IN</a></li>
       </ul>
       <br>
        <div class="tab-content">
    <div id="signup" class="tab-pane fade in active">
      <h3>SIGNUP</h3>
      <br> 
          <div class="form-group">
            <input type="text" id="name" placeholder="Name*" required name="name">
         </div>
          <div class="form-group">
        
            <input type="email" id="email" placeholder="Email*" required name="email">
         </div>
         <div class="form-group">
           
            <input type="tel" pattern="[789][0-9]{9}" title="Please Enter valid Mobile number" id="mobilenumber" placeholder="Mobile Number" name="mobilenumber">
         </div>
         <div class="form-group">
             
            <input type="text" id="address" placeholder="Address" name="address">
         </div>
     <div class="form-group">
         <input type="password"  id="password" placeholder="Password*" required name="password">
      </div>
      
      <div class="form-group"> 
       <button type="button"  class="btn buttons" id="register">SIGNUP</button> 
        <button type="reset"  class="btn buttons" >CLEAR</button>
      </div>
       
    </div>
    <div id="signin" class="tab-pane fade">
      <h3>SIGNIN</h3>
      <br>
    
          <div class="form-group">
            <input type="email" id="username" placeholder="Email*" required name="username">
         </div>
     <div class="form-group">
     <input type="password"  id="loginpassword" placeholder="Password*" required name="password">
      </div>
            <div class="form-group">
        <button type="button"  class="btn buttons" id="signinbutton">SIGNIN</button> 
        <button type="reset"  class="btn buttons" >CLEAR</button>
      </div>
      
        <!--  <div class="g-signin2" data-onsuccess="onSignIn"></div> --> 
       <br>
       
    </div>
  </div>
  </div>
    </div>
    
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
  <script src="https://apis.google.com/js/platform.js" async defer></script>  
    <script src="/js/register.js"> 
     </script>
  </body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Forgot password</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"  href="style.css" />
</head>
<body>
<div class="maindiv">
<p  id="emailerror" class="error">
<p  id="success" class="successmsg"> 
      </p>
     <div class="innerdiv">
     
      <div style="margin-left:170px;margin-top:40px">  
         <div class="form-group">
           <span>Enter your email address to reset your password</span>
         </div>
         <div class="form-group" style="margin-left:40px">
            <input type="email" id="email" placeholder="Email" name="email" onkeypress="keypress(this.id)">
         </div>
         <div class="form-group" style="margin-left:60px"> 
       <button type="button"  class="btn buttons" id="reset">Reset Password</button> 
       </div>
      </div>
     </div>
</div>
 <script src="/js/forgotpassword.js"> 
     </script>
</body>
</html>
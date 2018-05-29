<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>New password</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet"  href="style.css" />
</head>
<body>
   <%
   
     String rendstring=request.getParameter("randstring"); 
   %>
<div class="maindiv">
<p  id="pwdmsg" class="error">      
 </p>
 <p  id="success" class="successmsgreset"> 
      </p>
     <div class="innerdiv">
     
      <div style="margin-left:170px;margin-top:40px">
         <div class="form-group">
           <span>Enter new Password to reset your password</span>
         </div>
         <div class="form-group" style="margin-left:40px">
            <input type="password"  id="password" placeholder=" Password*"  name="password" onkeypress="keypress(this.id)">
         </div>
         <div class="form-group" style="margin-left:40px">
            <input type="password"  id="cnfpassword" placeholder="Conform Password*"  name="cnfpassword" onkeypress="keypress(this.id)">
         </div>
         <div class="form-group" style="margin-left:40px"> 
       <button type="button"  class="btn buttons" id="change">Change</button> 
       <input type="hidden" value="<%=rendstring%>" id="randstring">    
       </div>
      </div>
     </div>
</div>
     <script src="/js/changepassword.js"> 
     </script>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri = "http://java.sun.com/jsp/jstl/core" prefix = "c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home</title>
<link href="../home.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

 <style><%@include file="../home.css"%>
 	.log{
 		position:relative;
 		float:right;
 		right:10px;
 	}
 	
 </style> 
</head>
<body>
<%@ include file="heading.jsp" %> 
 
 
<div class="topnav">
  	<a class="active" href="homeServlet">Home</a>
    <a href="MyPosts">MyPosts</a>
  	<a href="AllReports">Problems</a>
    <a href="MyReports">MyProblems</a>
    <a href="profileServlet">Profile</a>
      
         <c:if test = "${roles == 'ADMIN'}"> <a href="adminServlet"> ${roles}</a>
      </c:if>
    <a class="log" href="Login.jsp">Logout</a>
    
</div>
</body>
</html>
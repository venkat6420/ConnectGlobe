<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css" integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">

 <style>
 .blue-background {
    background-color: rgb(204,255,255);
    
}


.imag{
	border-radius:50%;
	width:10%;
	float: left;
}
 
 </style> 
</head>
<body>
<div class="container-fluid">
    <div class="row blue-background">
        <div class="col-lg-6 col-xs-12 col-lg-offset-1">
     <img class="imag" src="data:image/jpeg;base64,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" width="450" height="100" border="0" /><br>
            <h1 style="position: relative;">Connect Globe</h1>
        </div>
    </div>
</div>
</body>
</html>
<%@ taglib prefix="authz"
    uri="http://www.springframework.org/security/tags"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:url value="/" var="base" />
<link type="text/css" rel="stylesheet" href="${base}resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript" src="${base}resources/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript" src="${base}resources/bootstrap/js/bootstrap.min.js"></script>
<title>Welcome</title>
</head>
<body>
    <div class="container">
        <center>
            <div class="page-header text-center h1">Thank you. ${firstname}!</div>
        </center>
        
        <p>
            Welcome! and Thank you for your registration. We will sent confirm mail to your email address. You must confirm or 
            click below confirm.
        </p>

        <div class="panel panel-info">
            <div class="panel-heading">
              <h3 class="panel-title">Your information is...</h3>
            </div>
            <div class="panel-body">
              Username (Email) : ${username} <br>
              First name : ${firstname} <br>
              Second name : ${secondname}
              Confirm URL : <a href="<c:url value="/confirm/${confirmcode}?username=${username}"/>">Confirm Now!</a>
            </div>
        </div>
        
        <div>
            <a href="<c:url value="/signin"/>"><button class="btn btn-primary" type="submit">Sign In</button></a>
            <a href="<c:url value="/"/>"><button class="btn btn-primary" type="submit">Go Home</button></a>
        </div>
    </div>
</body>
</html>
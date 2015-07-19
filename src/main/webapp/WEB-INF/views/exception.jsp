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
<title>Spring Starter Project ::: Exception</title>
</head>
<body>
    <div class="container">
        <h1>Exception occurred</h1>

        <h1>Woops!</h1>

        <p class="error">Exception => ${name} : ${message}</p>
        
        <a href="<c:url value="/"/>">Goto Home</a>
    </div>
</body>
</html>
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
<title>Spring Starter Project ::: User Information</title>
</head>
<script type="text/javascript">
function logout() {
    if (confirm('Do you really want logout?')) {
        var form = document.forms[0];
        form.submit();
    }
}
</script>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="#">Spring Starter</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">
            <li class="active"><a href="<c:url value="/"/>">Home</a></li>
            <li><a href="#about">About</a></li>
            <li><a href="#contact">Contact</a></li>
            <authz:authorize ifAnyGranted="USER, GUEST, ADMIN">
                <li class="dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-expanded="false">My Info. <span class="caret"></span></a>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="<c:url value="/user/${userid}"/>">View My Info.</a></li>
                    <li class="divider"></li>
                    <li><a href="#" onClick="logout()">Logout</a></li>
                  </ul>
                </li>
            </authz:authorize>
            <authz:authorize ifNotGranted="USER, GUEST, ADMIN">
                <li><a href="<c:url value="/signup"/>">Sign Up</a></li>
                <li><a href="<c:url value="/signin"/>">Sign In</a></li>
            </authz:authorize>
          </ul>
        </div><!--/.nav-collapse -->
      </div>
    </nav>
    
    <form action="/logout" method="post">
        <input type="hidden"
            name="${_csrf.parameterName}"
            value="${_csrf.token}"/>
    </form>
    
    <div class="container">
        <div>&nbsp;</div>
        <div>&nbsp;</div>
        <div class="page-header text-center h1">User Information about ${firstname} ${secondname}</div>

        <c:if test="${not empty param.error_message}">
            <h1>Woops!</h1>

            <p class="error">Error : ${error_message}</p>
        </c:if>
        
        <div class="panel panel-primary">
            <div class="panel-heading">
              <h3 class="panel-title">Your information is...</h3>
            </div>
            <div class="panel-body">
              Username (Email) : ${username} <br>
              First name : ${firstname} <br>
              Second name : ${secondname}
              <c:if test="${not empty param.confirmcode}">
                </a>
                 Confirm URL : <a href="<c:url value="/confirm/${confirmcode}?email=${username}"/>">Confirm Now!</a>
              </c:if>
            </div>
        </div>
        
        <a href="<c:url value="/"/>"><button class="btn btn-primary" type="submit">Go Home</button></a>
    </div>
</body>
</html>
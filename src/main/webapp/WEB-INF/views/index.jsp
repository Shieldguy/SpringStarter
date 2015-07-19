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
<title>Welcome Spring Starter Project Home</title>
</head>
<authz:authorize ifAnyGranted="USER, GUEST, ADMIN">
<script type="text/javascript">
function cancelSignOut() {
	document.getElementById("signOut").style.display = "none";
}

function signOutNow(form) {
	var form = document.forms[0];
    form.submit();
}

function signout() {    
    var getHost = function() {
        var secure = (window.location.port == "8443") ? true : false;
        return ((secure) ? 'https://' : 'http://') + window.location.hostname + ":" + 
             window.location.port;
    };
    
    $.ajax({
        url: getHost() + "/signout",
        dataType: "html",
        type: "GET",
        contentType: "application/x-www-form-urlencoded;charset=UTF8",
        success: function(data) {
            $('#signOut').before(data);
            document.getElementById("signOut").style.display = "block";
        },
        error: function(data, status) {
        	document.getElementById("signOut").style.display = "block";
        }
    }); 
}
</script>
</authz:authorize>
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
	                <li><a href="<c:url value="/user/${username}"/>">View My Info.</a></li>
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
                    
    <div class="container">
        <div>&nbsp;</div>
        <div>&nbsp;</div>
        <div class="page-header text-center h1">Welcome to Spring Starter Project</div>

        <p>
            This is a test web site that for testing spring framework based web application.
        </p>

        <authz:authorize ifAnyGranted="USER, GUEST, ADMIN">
            <center>
            <div id="signOut" class="panel panel-success" style="display:none; width:300px;">
                <div class="panel-heading">
	              <h3 class="panel-title">Sign Out?</h3>
	            </div>
	            <div class="panel-body">
		            <p>
		              Are you really want to sign out?
		            </p>
	                <button class="btn btn-primary" type="submit" onClick="signOutNow()">Yes</button>
	                <button class="btn" type="submit" onClick="cancelSignOut()">No</button>
	            </div>
			</div>
			</center>

            <div id="welcome">
	            <p> Welcome 
	               <authz:authorize ifAllGranted="ADMIN">
	                   Manager
	               </authz:authorize>
	               <authz:authorize ifNotGranted="ADMIN">
	                   <a href="<c:url value="/user/${username}"/>">${firstname} ${secondname} (${username})</a>
	               </authz:authorize>
	                   ! Nice to meet you. </p>
	            <p>
						<button class="btn btn-primary" type="submit" onClick="signout()">Sign Out</button>
	            </p>
            </div>
        </authz:authorize>
      
        <authz:authorize ifNotGranted="USER, GUEST, ADMIN">
            <div>
                <a href="<c:url value="/signin"/>"><button class="btn btn-primary" type="submit">Sign In</button></a> 
                <a href="<c:url value="/signup"/>"><button class="btn btn-primary" type="submit">Sign On</button></a>
            </div>
        </authz:authorize>
    </div>
</body>
</html>
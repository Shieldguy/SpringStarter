<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<c:url value="/" var="base" />
<link type="text/css" rel="stylesheet"
	href="${base}resources/bootstrap/css/bootstrap.min.css" />
<script type="text/javascript"
	src="${base}resources/jquery/jquery-1.11.3.min.js"></script>
<script type="text/javascript"
	src="${base}resources/bootstrap/js/bootstrap.min.js"></script>
<title>Spring Starter Project Sign in</title>
</head>
<script type="text/javascript">
function checkForm(form) {
	// check username
	if(form.username.value == "") {
		alert("The username field is required.");
        form.username.focus();
        return false;
	}
	
	// check username format
    var regex = /^([a-zA-Z0-9_\.\-\+])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if(!regex.test(form.username.value)) {
        alert("The username doesn't look like an email address.");
        form.username.value = "";
        form.username.focus();
        return false;
    } 
    
	// check password
    if(form.password.value == "") {
        alert("The password field is required.");
        form.password.focus();
        return false;
    }
	
	var getHost = function() {
	    var secure = (window.location.port == "8443") ? true : false;
	    return ((secure) ? 'https://' : 'http://') + window.location.hostname + ":" + 
	         window.location.port;
	};

	var check = "";
	
	$.ajax({
		url: getHost() + "/chkacnt",
		dataType: "html",
		type: "POST",
		contentType: "application/x-www-form-urlencoded;charset=UTF8",
		data: {
			"username": form.username.value,
			"password": form.password.value,
			"_csrf": form._csrf.value},
		success: function(data) {
			if(data == "ok") {
				form.submit();
			} else if(data == "unknown" || data == "invalid"){
				//$("#warningTag1").toggle();
				document.getElementById("warningTag1").style.display = "block";
				//$('#loginform').before("<div class='alert alert-danger'><strong>Woops!</strong>" +
				//	    " Invalid login or password. Please try again.</div>");
				check = "err";
			}
		},
		error: function(data, status) {
			document.getElementById("warningTag2").style.display = "block";
			//$('#loginform').before("<div class='alert alert-danger'><strong>Woops!</strong>" +
            //            " Error occurred during service request. Please try again.</div>");
			check = "err";
		}
	}); 
	
	if(check == "ok")
		return true;
	else
		return false;
}
</script>
<body>

	<div class="container">
		<center>
			<div class="page-header text-center h1">Spring Starter Project</div>

			<div class="text-center h4">Welcome to the Spring Starter Project Page!</div>

			<div class="text-center">&nbsp;</div>

			<c:if test="${not empty param.authentication_error}">
				<div class="alert alert-danger" role="alert">
					<strong>Woops!</strong> Your login attempt was not successful. :::
					${error_message}
				</div>
			</c:if>
			<c:if test="${not empty param.authorization_error}">
				<div class="alert alert-danger" role="alert">
					<strong>Woops!</strong> You are not permitted to access that
					resource. ::: ${error_message}
				</div>
			</c:if>
			
			<div class='alert alert-danger' id='warningTag1' style='display:none'><strong>Woops!</strong> 
			     Invalid login or password. Please try again.</div>
			<div class='alert alert-danger' id='warningTag2' style='display:none'><strong>Woops!</strong> 
                 Error occurred during service request. Please try again.</div>

			<div id="loginform">
				<center>
					<table border=0 width="400">
						<tr>
							<td align="center">
								<ul class="nav nav-tabs" role="tablist">
									<li role="presentation" class="active"><a href="#">Sign
											In</a></li>
									<li role="presentation"><a href="/signup">Sign Up</a></li>
								</ul>
							</td>
						</tr>
						<tr bgcolor=#000000>
							<td bgcolor=#ffffff>&nbsp;</td>
						</tr>
						<tr>
							<td>
								<form action="${base}login" method="post" name="loginform" role="form" onsubmit="return checkForm(document.forms[0])">
									<fieldset>
										<!-- legend>Please put your account.</legend -->
										<div class="form-group">
											<input id="username" class="form-control" type='text'
												name='username' value="" placeholder="Username (Email)" />
											${userid_absent}
										</div>
										<div class="form-group">
											<input id="password" class="form-control" type='password'
												name='password' value="" placeholder="Password" />
											${passwd_absent}
										</div>
										<center>
											<button class="btn btn-primary" type="submit">Sign
												In</button>
										</center>
										<input type="hidden" name="${_csrf.parameterName}"
											value="${_csrf.token}" />
									</fieldset>
								</form>
							</td>
						</tr>
					</table>
				</center>
			</div>
		</center>
	</div>
</body>
</html>
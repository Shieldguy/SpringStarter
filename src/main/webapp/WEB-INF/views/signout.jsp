<%@ taglib prefix="authz"
    uri="http://www.springframework.org/security/tags"%> 
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

            <div id="signOutForm">
                <form action="/logout" method="post">
                    <input type="hidden"
                        name="${_csrf.parameterName}"
                        value="${_csrf.token}"/>
                </form>
            </div>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <link rel="shortcut icon"
          href="https://e7.pngegg.com/pngimages/359/699/png-clipart-favicon-computer-icons-icon-design-share-icon-globalization-text-logo.png">
    <title><fmt:message key="reg.title">Registration</fmt:message></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css" />
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>

<div class="container">
    <nav class="navbar navbar-inverse">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand">WebSiteName</a>
            </div>
            <ul class="nav navbar-nav">
                <li>
                    <a href="${pageContext.request.contextPath}/app/menu">
                        <fmt:message key="nav.home">Home</fmt:message>
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li>
                    <a href="${pageContext.request.contextPath}/app/login">
                        <fmt:message key="nav.login">Login</fmt:message>
                    </a>
                </li>
                <li><a href="?lang=en"><fmt:message key="lang.eng">English</fmt:message></a></li>
                <li><a href="?lang=uk"><fmt:message key="lang.ua">Ukrainian</fmt:message></a></li>
            </ul>
        </div>
    </nav>

    <div class="col-md-6 col-md-offset-3">
        <h1><fmt:message key="reg.header">Registration Form</fmt:message></h1>
    </div>

    <div class="row">
        <div class="col-md-6 col-md-offset-3">

            <c:if test="${requestScope.valid_Error != null}">
                <h3 class="error"><c:out value="${requestScope.valid_Error}"/></h3>
            </c:if>
<%--            <c:if test="${requestScope.first_name_error != null}">--%>
<%--                <h3 class="error"><c:out value="${requestScope.first_name_error}"/></h3>--%>
<%--            </c:if>--%>
<%--            <c:if test="${requestScope.last_name_error != null}">--%>
<%--                <h3 class="error"><c:out value="${requestScope.last_name_error}"/></h3>--%>
<%--            </c:if>--%>
<%--            <c:if test="${requestScope.password_error != null}">--%>
<%--                <h3 class="error"><c:out value="${requestScope.password_error}"/></h3>--%>
<%--            </c:if>--%>

            <form autocomplete="off" id="reg-form" method="post" name="form" novalidate>

                <div class="form-group">
                    <label id="usernameLabel" for="username">
                        <fmt:message key="reg.login">
                            username
                        </fmt:message>
                    </label>
                    <input type="text"
                           name="username"
                           class="form-control"
                           id="username"
                           value="${requestScope.username}"
                           placeholder="<fmt:message key="reg.login.placeholder">username</fmt:message>"
                           required>
                </div>

                <div class="form-group">
                    <label for="firstName">
                        <fmt:message key="reg.first_name">
                            First Name
                        </fmt:message>
                    </label>
                    <input type="text"
                           name="firstName"
                           class="form-control"
                           id="firstName"
                           value="${requestScope.first_name}"
                           placeholder="<fmt:message key="reg.first_name.placeholder">first name</fmt:message>"
                           required>
                </div>

                <div class="form-group">
                    <label id="lastNameLabel" for="lastName">
                        <fmt:message key="reg.last_name">
                            last_name
                        </fmt:message>
                    </label>
                    <input type="text"
                           name="lastName"
                           class="form-control"
                           id="lastName"
                           value="${requestScope.last_name}"
                           placeholder="<fmt:message key="reg.last_name.placeholder">last name</fmt:message>"
                           required>
                </div>

                <div class="form-group">
                    <label id="passwordLabel" for="password">
                        <fmt:message key="reg.password">
                            password
                        </fmt:message>
                    </label>
                    <input type="text"
                           name="password"
                           class="form-control"
                           id="password"
                           value="${requestScope.password}"
                           placeholder="<fmt:message key="reg.password.placeholder">password</fmt:message>"
                           required>
                </div>

                <button type="submit" class="btn btn-success">
                    <fmt:message key="reg.register">register</fmt:message>
                </button>

            </form>
        </div>
    </div>

</div>

</body>
</html>

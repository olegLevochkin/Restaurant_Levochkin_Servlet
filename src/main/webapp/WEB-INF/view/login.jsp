<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>
<html lang="en">
<meta charset="UTF-8">
<link rel="shortcut icon" href="https://e7.pngegg.com/pngimages/359/699/png-clipart-favicon-computer-icons-icon-design-share-icon-globalization-text-logo.png">
<title><fmt:message key="login.title">Log In</fmt:message></title>
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
                    <a href="${pageContext.request.contextPath}/app/registration">
                        <fmt:message key="nav.reg">Registration</fmt:message>
                    </a>
                </li>
                <li><a href="?lang=en"><fmt:message key="lang.eng">English</fmt:message></a></li>
                <li><a href="?lang=uk"><fmt:message key="lang.ua">Ukrainian</fmt:message></a></li>
            </ul>
        </div>
    </nav>
    <div class="col-md-6 col-md-offset-3">
        <h1><fmt:message key="login.logging_in">Logging in</fmt:message></h1>
    </div>
    <div class="row">
        <div class="col-md-6 col-md-offset-3">

            <form action="${pageContext.request.contextPath}/app/login" method="post">

                <c:if test="${requestScope.message_er!=null}">
                    <h3 class="error"><c:out value="${requestScope.message_er}"/></h3>
                </c:if>

                <div class="form-group">
                    <label for="username">
                        <fmt:message key="login.username">Login</fmt:message>
                    </label>
                    <input type="text"
                           id="username"
                           name="username"
                           placeholder=
                           <fmt:message key="login.username.placeholder"/>
                                   required
                           class="form-control">
                </div>

                <div class="form-group">
                    <label for="password">
                        <fmt:message key="login.password">Password</fmt:message>
                    </label>
                    <input type="password"
                           id="password"
                           name="password"
                           placeholder=
                           <fmt:message key="login.password.placeholder"/>
                                   required
                           class="form-control">
                </div>

                <button type="submit" class="btn btn-success">
                    <fmt:message key="login.sign_in">sign in</fmt:message>
                </button>
            </form>
        </div>
    </div>
</div>

</body>
</html>

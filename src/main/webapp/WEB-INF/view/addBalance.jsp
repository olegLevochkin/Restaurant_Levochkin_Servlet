<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title><fmt:message key="balance.title">Registration</fmt:message></title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/main.css"/>
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
                <li>
                    <a href="${pageContext.request.contextPath}/app/order">
                        <fmt:message key="nav.order">Order</fmt:message>
                    </a>
                </li>
                <li class="active">
                    <a href="${pageContext.request.contextPath}/app/addMoney">
                        <fmt:message key="nav.balance">Balance</fmt:message>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/app/user_confirm">
                        <fmt:message key="nav.odering">Odering</fmt:message>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/app/adminOrder">
                        <fmt:message key="nav.admin">Admin</fmt:message>
                    </a>
                </li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <li class="nav-item">
                    <a class="nav-link" href="${pageContext.request.contextPath}/app/logout"><fmt:message
                            key="index.nav_bar.log_out">sign out</fmt:message></a>
                </li>
                <li><a href="?lang=en"><fmt:message key="lang.eng">English</fmt:message></a></li>
                <li><a href="?lang=uk"><fmt:message key="lang.ua">Ukrainian</fmt:message></a></li>
            </ul>
        </div>
    </nav>
    <form method="post" action="${ pageContext.request.contextPath}/app/addBalance" class="balance__form">

        <c:if test="${balanceError == 'true'}">
            <h3 class="error">
                <fmt:message key="balance.error">error</fmt:message>
            </h3>
        </c:if>
        <c:if test="${balanceErrorSum != null}">
            <h3 class="error">
                <fmt:message key="balance.notEnoughtMoney">no money</fmt:message>
            </h3>
        </c:if>
        <c:if test="${moneyBalance != null}">
            <h3>
                <fmt:message key="menu.balance">money</fmt:message> ${moneyBalance}
            </h3>
        </c:if>

        <div class="form-group">
            <label for="moneyToAdd">
                <fmt:message key="balance.sumToAdd">Sum to add</fmt:message>
            </label>
            <input class="form-control" id="moneyToAdd" name="moneyToAdd">
        </div>

        <button class="btn btn-success enter balance__form__button"
                type="submit" value="Sign In">
            <fmt:message key="balance.add">add money</fmt:message>
        </button>
    </form>
</div>

</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title><fmt:message key="admin.title">Registration</fmt:message></title>
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
                <li class="active">
                    <a href="${pageContext.request.contextPath}/app/adminOrder">
                        <fmt:message key="nav.admin">Admin</fmt:message>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/app/users">
                        <fmt:message key="nav.users">All Users</fmt:message>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/app/add">
                        <fmt:message key="nav.add.dish">Add Dish</fmt:message>
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
    <table class="table">
        <tr>
            <th><fmt:message key="dish.dishName">Name of Dish</fmt:message></th>
            <th><fmt:message key="dish.price">Price of dish</fmt:message></th>
            <th><fmt:message key="dish.need">Total count</fmt:message></th>
        </tr>
        <c:forEach var="dish" items="${requestScope.orderClient}">
            <tr>
                <td>
                    <c:set var="localeCode" value="${pageContext.response.locale}"/>
                    <c:choose>
                        <c:when test="${lang == 'uk'}">
                            ${dish.key.nameUkr}
                        </c:when>
                        <c:otherwise>
                            ${dish.key.name}
                        </c:otherwise>
                    </c:choose>
                </td>
                <td>${dish.key.price}</td>
                <td>${dish.value}</td>
            </tr>
        </c:forEach>
    </table>

    <table class="table">
        <tr>
            <th><fmt:message key="product.name">Product</fmt:message></th>
            <th><fmt:message key="product.need">Number of Product</fmt:message></th>
        </tr>
        <c:forEach var="product" items="${requestScope.products}">
            <tr>
                <td><fmt:message key="prod.${product.key}">product</fmt:message></td>
                <td>${product.value}</td>
            </tr>
        </c:forEach>
    </table>
    <form method="post" action="${ pageContext.request.contextPath}/app/checkOrder/Confirm">
        <button class="btn btn-success enter " name="orderID"
                value="${orderID}" type="submit">
            <fmt:message key="admin.confirm">Confirm</fmt:message>
        </button>
    </form>
</div>
</body>
</html>

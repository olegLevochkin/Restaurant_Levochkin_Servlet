<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title><fmt:message key="order.title">Order</fmt:message></title>
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
                    <a href="${pageContext.request.contextPath}/app/order">
                        <fmt:message key="nav.order">Order</fmt:message>
                    </a>
                </li>
                <li>
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
    <c:if test="${notEnought != null}">
        <h3 class="error"><fmt:message key="order.notEnought">Ukrainian</fmt:message></h3>
    </c:if>
    <c:if test="${isEmpty == 'true'}">
        <h1><fmt:message key="order.no">no orders</fmt:message></h1>
    </c:if>
    <c:if test="${isEmpty == 'false'}">
        <table class="table">
            <tr>
                <th scope="col">
                    <fmt:message key="dish.dishName">name of Dish</fmt:message>
                </th>
                <th scope="col">
                    <fmt:message key="dish.price">price of dish</fmt:message>
                </th>
                <th scope="col">
                    <fmt:message key="dish.amount">Count</fmt:message>
                </th>
                <th scope="col">
                    <fmt:message key="dish.delete">delete</fmt:message>
                </th>
            </tr>
            <c:forEach var="entry" items="${requestScope.map}">
                <tr>
                    <td>
                <span>
                    <c:set var="localeCode" value="${pageContext.response.locale}"/>
                        <c:choose>
                            <c:when test="${lang == 'uk'}">
                                ${entry.key.nameUkr}
                            </c:when>
                            <c:otherwise>
                                ${entry.key.name}
                            </c:otherwise>
                        </c:choose>
                </span>
                    </td>
                    <td>${entry.key.price}</td>
                    <td>${entry.value}</td>
                    <td>
                        <form action="${pageContext.request.contextPath}/app/order/removeD" method="post">
                            <button class="btn btn-danger enter" name="dish" type="submit"
                                    value="${entry.key.name}">
                                Delete
                            </button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </table>
        <h2>
            <fmt:message key="order.total">All price</fmt:message>: ${amount}
        </h2>
        <form action="${pageContext.request.contextPath}/app/order/addedOrder" method="post">
            <c:if test="${notEnought == null}">
                <button class="btn btn-success enter" name="actA" type="submit">
                    <fmt:message key="admin.confirm">confirm</fmt:message>
                </button>
            </c:if>
        </form>
    </c:if>
</div>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title><fmt:message key="menu.title">Menu</fmt:message></title>
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
                <li class="active">
                    <a href="${pageContext.request.contextPath}/app/menu">
                        <fmt:message key="nav.home">Home</fmt:message>
                    </a>
                </li>
                <c:if test="${isAuthorize == 2 || isAuthorize == 1}">
                    <li>
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
                    <c:if test="${isAuthorize == 2}">
                        <li>
                            <a href="${pageContext.request.contextPath}/app/adminOrder">
                                <fmt:message key="nav.admin">Admin</fmt:message>
                            </a>
                        </li>
                    </c:if>

                </c:if>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${isAuthorize == 1 || isAuthorize == 2}">
                    <li class="nav-item">
                        <a class="nav-link" href="${pageContext.request.contextPath}/app/logout"><fmt:message
                                key="index.nav_bar.log_out">sign out</fmt:message></a>
                    </li>
                </c:if>
                <c:if test="${isAuthorize == 0}">
                    <li>
                        <a href="${pageContext.request.contextPath}/app/login">
                            <fmt:message key="nav.login">Login</fmt:message>
                        </a>
                    </li>
                </c:if>
                <li><a href="?lang=en"><fmt:message key="lang.eng">English</fmt:message></a></li>
                <li><a href="?lang=uk"><fmt:message key="lang.ua">Ukrainian</fmt:message></a></li>
            </ul>
        </div>
    </nav>
    <c:if test="${moneyBalance == null}">

    </c:if>
    <c:if test="${moneyBalance != null}">
        <fmt:message key="menu.balance"></fmt:message>
        ${moneyBalance}
    </c:if>
    <h1><fmt:message key="menu.menu">menu</fmt:message></h1>
    <table class="table">
        <tr>
            <th scope="col"><fmt:message key="dish.foto">Foto</fmt:message></th>
            <th scope="col"><fmt:message key="dish.dishName">name of Dish</fmt:message></th>
            <th scope="col"><fmt:message key="dish.price">price of dish</fmt:message></th>
            <th scope="col"><fmt:message key="dish.products">Products</fmt:message></th>
            <c:if test="${isAuthorize == 2 || isAuthorize == 1}">
                <th scope="col"><fmt:message key="dish.getNow">GetNow</fmt:message></th>
            </c:if>
        </tr>
        <c:forEach var="dish" items="${requestScope.dishes}">
            <tr>
                <td>
                    <img alt="Picture" height="100"
                         src="${pageContext.request.contextPath}/images/${dish.fileName}" width="100">
                </td>
                <td>
                <span>
                    <c:set var="localeCode" value="${pageContext.response.locale}"/>
                        <c:choose>
                            <c:when test="${lang == 'uk'}">
                                ${dish.nameUkr}
                            </c:when>
                            <c:otherwise>
                                ${dish.name}
                            </c:otherwise>
                        </c:choose>
                </span>
                </td>
                <td>
                        ${dish.price}
                </td>
                <td>
                    <c:forEach var="product" items="${dish.productsForDish}">
                        <fmt:message key="prod.${product.product}"></fmt:message>
                    </c:forEach>
                </td>
                <c:if test="${isAuthorize == 2 || isAuthorize == 1}">
                    <td>
                        <form action="${pageContext.request.contextPath}/app/order/AddToCard" method="post">
                            <button class="btn btn-success enter" name="dish" value="${dish.name}" type="submit"
                                    value="Registrate">
                                <fmt:message key="dish.add"></fmt:message>
                            </button>
                        </form>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>

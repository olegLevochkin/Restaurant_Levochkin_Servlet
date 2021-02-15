<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="property/messages"/>

<html lang="en">

<head>
    <meta charset="UTF-8">
    <title><fmt:message key="admin.title">ADMIN</fmt:message></title>
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
                    <a href="${pageContext.request.contextPath}/app/adminOrder">
                        <fmt:message key="nav.admin">Admin</fmt:message>
                    </a>
                </li>
                <li>
                    <a href="${pageContext.request.contextPath}/app/users">
                        <fmt:message key="nav.users">All Users</fmt:message>
                    </a>
                </li>
                <li class="active">
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
    <div class="dishes__form">
        <form class="dishes_form__add" method="post" action="${ pageContext.request.contextPath}/app/add/addDish"
              enctype="multipart/form-data">

            <c:if test="${error !=null}">
                <h3 class="error">
                    <fmt:message key="addDish.add">error</fmt:message>
                </h3>
            </c:if>

            <c:if test="${error1 !=null}">
                <h3 class="error">
                    <fmt:message key="dish.size.error">error</fmt:message>
                </h3>
            </c:if>

            <div class="form-group">
                <label for="file">
                    <fmt:message key="dish.foto">
                        FileName
                    </fmt:message>
                </label>
                <input type="file"
                       id="file"
                       name="file"
                       formenctype="multipart/form-data"
                       value="${requestScope.filename}"
                       placeholder=<fmt:message key="dish.foto"/>>
            </div>

            <div class="form-group">
                <label for="nameUkr">
                    <fmt:message key="dish.ukrName"/>
                </label>
                <input class="form-control" id="nameUkr" name="nameUkr">
            </div>

            <div class="form-group">
                <label for="name"><fmt:message key="dish"/></label>
                <input class="form-control" id="name" name="name">
            </div>

            <div class="form-group">
                <label for="price">
                    <fmt:message key="dish.price">price</fmt:message>
                </label>
                <input class="form-control" id="price" name="price" type="number">
            </div>

            <div class="form-group">
                <c:forEach var="prodT" items="${requestScope.products}">
                    <div class="check-box">
                        <label for="${prodT.product}">
                            <fmt:message key="prod.${prodT.product}">product</fmt:message>
                        </label>
                        <input name="${prodT.product}" id="${prodT.product}" value="${prodT.product}" type="checkbox">
                    </div>
                </c:forEach>
            </div>

            <button class="btn btn-success enter"
                    type="submit" value="Sign In">
                <fmt:message key="dish.add">add</fmt:message>
            </button>
        </form>
        <form class="dishes_form__delete" method="post" action="${ pageContext.request.contextPath}/app/add/removeDish">
            <h2><fmt:message key="dish.remove">remove</fmt:message></h2>

            <c:forEach var="dish" items="${requestScope.dishes}">
                <div class="form-group">
                    <input name="dishesToRemove" id="${dish.name}" value="${dish.name}" type="checkbox">

                    <c:if test="${language == 'en'}">
                        ${dish.name}
                    </c:if>

                    <c:if test="${language == 'uk'}">
                        ${dish.nameUkr}
                    </c:if>
                </div>

            </c:forEach>

            <button class="btn btn-danger enter"
                    type="submit"
                    value="Sign In">
                <fmt:message key="dish.delete">delete</fmt:message>
            </button>
        </form>
    </div>
</div>
</body>
</html>

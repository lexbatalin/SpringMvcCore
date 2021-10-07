<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="<c:url value="/resources/css/home.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>
<body>

    <form:form method="POST" modelAttribute="user" action="check_user"  class="box login">

        <fieldset class="boxBody">
            <form:label path="name">Username:</form:label>
            <form:input path="name"/>

            <form:label path="password">Password:</form:label>
            <form:input path="password"/>
        </fieldset>

        <footer>
            Administrator <form:checkbox path="isAdmin" value="isAdmin"/>
            <input type="submit" class="btnLogin" value="Login" tabindex="4">
        </footer>

    </form:form>

</body>
</html>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>
<head>
    <link href="<c:url value="/resources/css/home.css" />" rel="stylesheet">
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title><spring:message code="login"/></title>

    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>

    <script type="text/javascript">
        function doAjax() {
            $.ajax({
                url: 'checkStrength',
                data: ({password : $('#password').val()}),
                success: function(data) {
                    $('#strengthValue').html(data);
                }
            });
        }
    </script>

</head>
<body>

    <form:form method="POST" modelAttribute="user" action="check_user"  class="box login">

        <fieldset class="boxBody">
            <span style="float: right">
                <a href="?lang=en">en</a>
                <a href="?lang=ru">ru</a>
            </span>

            <form:label path="name"><spring:message code="username"/>:</form:label>
            <form:input path="name"/>
            <form:errors path="name" cssClass="error"/>

            <form:label path="password"><spring:message code="password"/>:</form:label>
            <form:password path="password" onkeyup="doAjax()"/>
            <form:errors path="password" cssClass="error"/>
            <span style="float: right" id="strengthValue"></span>
        </fieldset>

        <footer>
            <spring:message code="admin"/> <form:checkbox path="isAdmin" value="isAdmin"/>
            <input type="submit" class="btnLogin" value="Login" tabindex="4">
        </footer>

    </form:form>

</body>
</html>
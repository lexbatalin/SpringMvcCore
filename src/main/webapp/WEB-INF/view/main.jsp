<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Main</title>
</head>
<body>
    <h1>Hello ${user.name}</h1>
    <h2>Your password is ${user.password}</h2>
    <h2>You are ${user.isAdmin ? "admin" : "not admin"}</h2>
    <h1>Locale ${locale}</h1>

    <form:form method="POST" action="uploadFile" modelAttribute="uploadedFile" enctype="multipart/form-data">
        <table>
            <tr>
                <td>Upload file:</td>
                <td><input type="file" name="file"/></td>
                <td style="color: red; font-style: italic;"><form:errors path="file"/> </td>
            </tr>
            <tr>
                <td></td>
                <td><input type="submit" value="Upload"/></td>
            </tr>
        </table>
    </form:form>
</body>
</html>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Main</title>
</head>
<body>
    <h1>Hello ${user.name}</h1>
    <h2>Your password is ${user.password}</h2>
    <h2>You are ${user.isAdmin ? "admin" : "not admin"}</h2>
    <h1>Locale ${locale}</h1>
</body>
</html>

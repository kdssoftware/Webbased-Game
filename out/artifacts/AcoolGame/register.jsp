<%@ page import="model.User" %><%--
  Created by IntelliJ IDEA.
  User: karel
  Date: 14.03.19
  Time: 19:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Register</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
    <% User user = (User) request.getAttribute("user");%>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="get?r=account">Account</a></li>
            <li class="nav-item"><a class="nav-link" href="get?r=missions">missions</a></li>
            <li class="nav-item"><a class="nav-link" href="get?r=shop">shop</a></li>
            <li class="nav-item"><a class="nav-link" href="get?r=logout">logout</a></li>
        </ul>
    </nav>

    <div class="jumbotron jumbotron-fluid d-flex flex-column bd-highlight mb-3">
        <div class="container p-2 bd-highlight">
            <%if(request.getAttribute("errorMessage")!=null && !request.getAttribute("errorMessage").equals("")){%>
            <div class="alert alert-danger" role="alert">
                <%=request.getAttribute("errorMessage")%>
            </div>
            <%}%>
            <h1 class="display-4">Welcome new Player</h1>
            <p class="lead">Your ID is: <%=user.getId()%></p>
        </div>
    </div>
</body>
</html>

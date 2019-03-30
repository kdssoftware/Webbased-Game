<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %><%--
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
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
    <% User user = (User) request.getAttribute("user");%>

    <div class="pos-f-t">
        <div class="collapse" id="navbarToggleExternalContent">
            <div class="bg-dark p-4">
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link" href="get?r=account">Account</a></li>
                        <li class="nav-item"><a class="nav-link" href="get?r=missions">Missions</a></li>
                        <li class="nav-item"><a class="nav-link" href="get?r=shop">Shop</a></li>
                        <li class="nav-item"><a class="nav-link" href="get?r=rivals">Rivals</a></li>
                        <li class="nav-item"><a class="nav-link" href="get?r=logout">Logout</a></li>
                    </ul>
                </nav>
            </div>
        </div>
        <nav class="navbar navbar-dark bg-dark">
            <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarToggleExternalContent" aria-controls="navbarToggleExternalContent" aria-expanded="false" aria-label="Toggle navigation">
                <span class="navbar-toggler-icon"></span>
            </button>
        </nav>
    </div>

    <div class="jumbotron jumbotron-fluid d-flex flex-column bd-highlight mb-3">
        <div class="container p-2 bd-highlight">
            <%ArrayList<String> errorList = (ArrayList<String>)request.getAttribute("errorMessage");%>
            <%if(errorList != null && errorList.size() != 0) {%>
            <%for(String error : errorList){%>
            <div class="alert alert-danger" role="alert">
                <%=error%>
            </div>
            <%}%>
            <%}%>
            <%ArrayList<String> messageList = (ArrayList<String>)request.getAttribute("message");%>
            <%if(messageList != null && messageList.size() != 0){%>
            <%for(String message : messageList){%>
            <div class="alert alert-success" role="alert">
                <%=message%>
            </div>
            <%}%>
            <%}%>
            <h1 class="display-4">Welcome, <%=user.getUsername()%>!</h1>
            <p class="lead">To login again, you need this id.</p>
            <h3>ID: </h3><span class="badge badge-secondary"><%=user.getId()%></span>
        </div>
    </div>
</body>
</html>

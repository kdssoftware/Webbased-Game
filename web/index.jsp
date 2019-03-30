<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="db.DB" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Login</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body><%
    String db;
    if(request.getAttribute("DB") == null){
        db = "0";
    }else{
        db = request.getAttribute("DB").toString();
    } %>
<%if(request.getParameter("admin") !=null){%>
    <form  action="get?r=adminLogin" method="post">
        <input type="password" name="password" placeholder="password">
        <input class="btn btn-outline-secondary" type="submit" name="submit" value="Admin" required>
    </form>
<%}%>
<div class="jumbotron jumbotron-fluid d-flex flex-column bd-highlight mb-3">
    <div class="container">
        <div class="p-2 bd-highlight">
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
            <h1 class="display-4">Login here:</h1>
            <form action="get?r=login" method="post">
                <div class="input-group flex-nowrap">
                    <div class="input-group-prepend">
                        <span class="input-group-text" id="addon-wrapping">ID</span>
                    </div>
                    <input type="text" name="loginId" class="form-control" placeholder="login using your id" aria-label="Username" aria-describedby="addon-wrapping" required>
                </div>
                <input class="btn btn-primary" type="submit" value="login">
            </form>
        </div>
        <div class="p-2 bd-highlight">
            <h3 class="display-4">Register here:</h3>
            <form  action="get?r=register" method="post">
                <input type="text" name="username" placeholder="username">
                <input class="btn btn-outline-primary" type="submit" name="submit" value="register"required>
            </form>
            <h3>Total amount of players:<span class="badge badge-success"><%=db%></span></h3>
            <p class="lead">version: alpha v0.1.0 (stable, unbalanced)</p>
        </div>
    </div>
</div>
</body>
</html>
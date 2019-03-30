<%@ page import="model.User" %><%-- Created by IntelliJ IDEA. --%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
  <title>Login</title>
  <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
  <body>
  <div class="jumbotron jumbotron-fluid d-flex flex-column bd-highlight mb-3">
    <div class="container">
      <div class="p-2 bd-highlight">
        <%if(request.getAttribute("errorMessage")!=null && !request.getAttribute("errorMessage").equals("")){%>
        <div class="alert alert-danger" role="alert">
          <%=request.getAttribute("errorMessage")%>
        </div>
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
      <h2 class="display-4">Register here:</h2>
      <a href="get?r=register"><button type="button" class="btn btn-outline-primary">Register</button></a>
      <p class="lead">version: alpha v0.0.3 (unstable)</p>
      </div>
    </div>
  </div>
  </body>
</html>
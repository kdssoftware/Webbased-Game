<%@ page import="model.User" %>
<%@ page import="model.Mission" %>
<%@ page import="db.DB" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="model.ShopItem" %>
<%@ page import="java.lang.reflect.Array" %><%--
  Created by IntelliJ IDEA.
  User: karel
  Date: 14.03.19
  Time: 19:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <title>Missions</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
<% User user = (User) request.getAttribute("user");%>
<header>
    <div class="pos-f-t">
        <div class="collapse" id="navbarToggleExternalContent">
            <div class="bg-dark p-4">
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link" href="get?r=account">Account</a></li>
                        <li class="nav-item"><a class="nav-link" href="get?r=missions">Missions</a></li>
                        <li class="nav-item"><a class="nav-link" href="get?r=shop">Shop</a></li>
                        <li class="nav-item rivals"><a class="nav-link" href="get?r=rivals">Rivals</a></li>
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
</header>
<div class="jumbotron jumbotron-fluid">
    <div class="container" >
        <%ArrayList<User> db = (ArrayList<User>) request.getAttribute("DB");%>
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
        <ul class="list-group">
            <li class="list-group-item">
                <h3>
                    <span class="badge badge-primary">
                        lvl: <%=user.getLevel()%>
                    </span>
                </h3>
            </li>
            <li class="list-group-item">
                <div class="progress">
                    <div class="progress-bar bg-success text-dark" role="progressbar" style="width: <%=user.getRatioExp()%>%" aria-valuenow="<%=user.getExp()%>" aria-valuemin="0" aria-valuemax="<%=user.getExpCap(user.getLevel())%>">
                        <%=user.getExp()%> exp/<%=user.getExpCap(user.getLevel())%> exp
                    </div>
                </div>
            </li>
            <li  class="list-group-item">
                <div class="progress">
                    <div class="progress-bar bg-warning text-dark" role="progressbar" style="width: <%=user.getRatioEnergy()%>%" aria-valuenow="<%=user.getEnergy()%>" aria-valuemin="0" aria-valuemax="<%=user.getEnergyMax()%>">
                        <%=user.getEnergy()%> E
                    </div>
                    <a href="get?r=rival" class="btn btn-secondary btn-lg " role="button" ><!--<img id="refresh" src="./img/refresh.png" alt="refresh">--></a>
                </div>
            </li>
            <li class="list-group-item">
                <p class="lead">Cash: <span class="badge badge-pill badge-success">  $<%=user.getCash()%>  </span></p>
            </li>
            <li class="list-group-item">
                <p class="lead">Attack: <span class="badge badge-pill badge-dark">  <%=user.getAttack()%>  </span></p>
                <p class="lead">Defense: <span class="badge badge-pill badge-dark">  <%=user.getDefense()%>  </span></p>
                <p class="lead">Health : <span class="badge badge-danger badge-pill"> <%=user.getHealth()%></span></p>
            </li>
        </ul>
    </div>
</div>
    <div class="table-responsive">
    <table class="table">
        <thead class="thead-dark table-striped">
        <tr>
            <th scope="col">User</th>
            <th scope="col">lvl</th>
            <th scope="col">Defense</th>
            <th scope="col">Cost</th>
            <th scope="col">Attack</th>
        </tr>
        </thead>
        <tbody>
        <%for(User u : db){%>
            <tr>
                <td>
                    <p class="lead"><%=u.getUsername()%></p>
                </td>
                <td>
                    <span class="badge badge-primary"><%=u.getLevel()%></span>
                </td>
                <td>
                    <span class="badge badge-dark"><%=u.getDefense()%></span>
                </td>
                <td>
                    <a href="get?r=rivals&attack=<%=u.getId()%>"><button type="button" class="btn btn-outline-primary">Fight</button></a>
                </td>
                <td>
                    <span class="badge badge-warning"><%=user.getLevel()%> exp </span>
                </td>
            </tr>
        <%}%>
        </tbody>
    </table>
    </div>
</body>
</html>

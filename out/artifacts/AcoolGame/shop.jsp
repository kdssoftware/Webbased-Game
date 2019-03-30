<%@ page import="model.User" %>
<%@ page import="model.Mission" %>
<%@ page import="db.DB" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.LocalTime" %>
<%@ page import="model.ShopItem" %><%--
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
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
</head>
<body>
<% User user = (User) request.getAttribute("user");%>
<header>
    <nav class="navbar navbar-expand-lg navbar-dark bg-primary">
        <ul class="navbar-nav">
            <li class="nav-item"><a class="nav-link" href="get?r=account">Account</a></li>
            <li class="nav-item"><a class="nav-link" href="get?r=missions">missions</a></li>
            <li class="nav-item active"><a class="nav-link" href="get?r=shop">shop</a></li>
            <li class="nav-item"><a class="nav-link" href="get?r=logout">logout</a></li>
        </ul>
    </nav>
</header>
<div class="jumbotron jumbotron-fluid ">
    <div class="container " >
        <%if(request.getAttribute("errorMessage")!=null && !request.getAttribute("errorMessage").equals("")){%>
        <div class="alert alert-danger" role="alert">
            <%=request.getAttribute("errorMessage")%>
        </div>
        <%}%>
        <p class="lead">you are lvl <%=user.getLevel()%></p>
        <div class="progress">
            <div class="progress-bar bg-success text-dark" role="progressbar" style="width: <%=user.getRatioExp()%>%" aria-valuenow="<%=user.getExp()%>" aria-valuemin="0" aria-valuemax="<%=user.getExpCap(user.getLevel())%>">
                <%=user.getExp()%> exp/<%=user.getExpCap(user.getLevel())%> exp
            </div>
        </div>

        <div class="progress">
            <div class="progress-bar bg-warning text-dark" role="progressbar" style="width: <%=user.getRatioEnergy()%>%" aria-valuenow="<%=user.getEnergy()%>" aria-valuemin="0" aria-valuemax="<%=user.getEnergyMax()%>">
                <%=user.getEnergy()%> energy
            </div>
        </div>
        <p class="lead">Cash: <span class="badge badge-pill badge-success">  $<%=user.getCash()%>  </span></p>
    </div>
</div>
<table class="table">
    <thead class="thead-dark table-striped">
    <tr>
        <th scope="col">Ability</th>
        <th scope="col">Cost</th>
        <th scope="col"></th>
    </tr>
    </thead>
    <tbody>
        <p class="lead">Current Energy increase speed <%=user.getSpeed()%> seconds</p>
        <%for(ShopItem i : user.getShopItemDB()){%>
        <tr>
            <td><%=i.getName()%></td>
            <td><%=i.getCost()%></td>
            <td><a href="get?r=shop&Item=<%=i.getId()%>"><button type="button" class="btn btn-outline-primary">Buy</button></a></td>
        </tr><%}%>
        </tbody>
    <p class="lead">Shop is unfinished</p>
</table>
</body>
</html>

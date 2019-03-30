<%@ page import="model.User" %>
<%@ page import="model.Mission" %>
<%@ page import="db.DB" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.time.temporal.ChronoUnit" %>
<%@ page import="java.time.LocalTime" %><%--
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
<% ArrayList<Mission> missions = (ArrayList<Mission>) request.getAttribute("DB");%>
<header>
    <div class="pos-f-t">
        <div class="collapse" id="navbarToggleExternalContent">
            <div class="bg-dark p-4">
                <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
                    <ul class="navbar-nav">
                        <li class="nav-item"><a class="nav-link" href="get?r=account">Account</a></li>
                        <li class="nav-item active"><a class="nav-link" href="get?r=missions">Missions</a></li>
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
</header>
<div class="jumbotron jumbotron-fluid ">
    <div class="container " >
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
                    <a href="get?r=missions" class="btn btn-secondary btn-lg " role="button" ><!--<img id="refresh" src="./img/refresh.png" alt="refresh">--></a>
                </div>
            </li>
            <li class="list-group-item">
                <p class="lead">Cash: <span class="badge badge-pill badge-success">  $<%=user.getCash()%>  </span></p>
                <p class="lead">Health : <span class="badge badge-danger badge-pill"> <%=user.getHealth()%></span></p>
            </li>
        </ul>
    </div>
    </div>
        <div class="table-responsive">
        <table class="table">
            <thead class="thead-dark table-striped">
            <tr>
                <th scope="col">mission</th>
                <th scope="col">Cost</th>
                <th scope="col">cash</th>
                <th scope="col">exp</th>
                <th scope="col"></th>
                <th scope="col">extra</th>
            </tr>
            </thead>
            <tbody>
            <%for(Mission m : missions){%>
                <tr id="<%=m.getId()%>">
                    <th scope="row">
                        <p class="lead"><%=m.getName()%>
                            <%if(m.getTimesCompleted()!=1){%>
                                <span class="badge badge-light"><%=m.getTimesCompleted()-1%></span>
                            <%}%>
                        </p>
                        <div class="progress">
                            <div class="progress-bar bg-success text-dark" role="progressbar" style="width: <%=user.getCompletionDB().getCompletedRatio(m)%>%" aria-valuenow="<%=user.getCompletionDB().getCompletedRatio(m)%>" aria-valuemin="0" aria-valuemax="100.0">
                                <%=user.getCompletionDB().getCompletedRatio(m)%>%
                            </div>
                        </div>
                            <p class="lead">
                                <%if(m.getBonusHealth() != 0){%>
                                    <span class="badge badge-danger"><%=m.getBonusHealth()%></span><%}%>
                                <%if(m.getBonusExp() != 0){%>
                                    <span class="badge badge-warning"><%=m.getBonusExp()%></span><%}%>
                                <%if(m.getBonuxCash() != 0){%>
                                     <span class="badge badge-success"><%=m.getBonuxCash()%></span><%}%>
                                <%if(m.getBonusAttack() != 0 || m.getBonusDefense() != 0){%>
                                    <span class="badge badge-dark"><%=m.getBonusDefense()%> / <%=m.getBonusAttack()%></span><%}%>
                            </p>
                    </th>
                    <td><span class="badge badge-warning"><%=m.getEnergyCost()%> E</span></td>
                    <td><span class="badge badge-success"><%=m.getCashEarnings()%>$</span></td>
                    <td><span class="badge badge-info"><%=m.getExpEarnings()%> exp</span></td>
                    <td><a href="get?r=missions&Do=<%=m.getId()%>"><button type="button" class="btn btn-outline-primary">Go</button></a></td>
                    <td>
                        <%if(m.getIncreaseHealth()!= 0){%>
                            <span class="badge badge-pill badge-danger"><%=m.getIncreaseHealth()%></span>
                        <%}
                        if(m.getIncreaseDefense() != 0 || m.getIncreaseAttack() != 0){%>
                            <span class="badge badge-dark"><%=m.getIncreaseDefense()%> / <%=m.getIncreaseAttack()%></span>
                        <%}%>
                    </td>
                </tr>
            <%}%>
            </tbody>
        </table>
    </div>
</body>
</html>

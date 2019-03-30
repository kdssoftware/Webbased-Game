<%@ page import="model.Handler" %>
<%@ page import="model.User" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: karel
  Date: 18.03.19
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>admin page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
</head>
<body>
<%Handler handler = (Handler)request.getAttribute("handler");%>
<a href="get?r=adminLogout"><button type="button" class="btn btn-outline-primary">Logout</button></a></td>
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
<%for(Object o: handler.getDb().getUserDB()){
    User u;
    if(o instanceof User){
        u = (User)o;
        %>

<ul class="list-group">
            <li class="list-group-item list-group-item-primary">
                <p>
                    Name:<%=(u.getUsername())%>
                </p>
                <a href="get?r=admin&removeUser=<%=u.getId()%>">
                    <button type="button" class="btn btn-outline-danger">
                        remove
                    </button>
                </a>
                <a href="get?r=downloadFile&u=<%=u.getId()%>">
                    <button type="button" class="btn btn-outline-success">
                        download
                    </button>
                </a>
            </li>
            <li class="list-group-item">
                <p>ID:<%=u.getId()%></p>
            </li>
            <li class="list-group-item">
                <p>Exp:  <%=u.getEnergy()%> / <%=u.getEnergyMax()%></p>
            </li>
            <li class="list-group-item">
                <p>cash: <%=u.getCash()%></p>
            </li>
            <li class="list-group-item">
                <p>lvl: <%=u.getLevel()%></p>
            </li>
            <li class="list-group-item">
                <p>Energy recovery: every <%=(int)u.getSpeed()%> Seconds</p>
            </li>
            <li  class="list-group-item">
                <p>Last online <%=u.getTime().toString()%></p>
            </li>
            <li class="list-group-item">
                <p>remote host: <%=request.getRemoteHost()%></p>
                <p>remote address: <%=request.getRemoteAddr()%></p>
            </li>
        </ul>
    <%}%>
<%}%>
</body>
</html>

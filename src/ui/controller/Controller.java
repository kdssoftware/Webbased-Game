package ui.controller;

import model.Handler;
import model.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/get")
public class Controller extends HttpServlet {

    public static final boolean UNSTABLE_FEATURES_ON = false;
    public Handler handler = new Handler();
    private User currentLogonUser;
    private String pass;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        process(request,response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        handler.deleteAllErrors();
        handler.deleteAllMessages();
        String input;
        String destination;
        if(currentLogonUser != null) {
                currentLogonUser.checkTimeForEnergy();
                this.handler.save(currentLogonUser);
        }if (request.getParameter("r") != null) {
            input = request.getParameter("r");
            switch (input) {
                case "logout":
                    destination = logout(request, response);
                    break;
                case "missions":
                    destination = missions(request,response);
                    break;
                case "register":
                    destination = register(request,response);
                    break;
                case "login":
                    destination = login(request, response);
                    break;
                case "account":
                    destination = account(request,response);
                    break;
                case "index":
                    destination = index(request,response);
                    break;
                case "shop":
                    destination = shop(request,response);
                    break;
                case "admin":
                    destination = admin(request,response);
                    break;
                case "adminLogout":
                    destination = adminLogout(request,response);
                    break;
                case "adminLogin":
                    destination = adminLogin(request, response);
                    break;
                case "downloadFile":
                    destination = downloadFile(request,response);
                    break;
                case "rivals":
                    destination = rivals(request,response);
                    break;
                default:
                    destination = destinationError(request,response);
            }
        }else{
            destination = destinationError(request,response);
        }
        if(handler.getError().size() != 0){
            setErrorHandler(request);
        }
        if(handler.getMessage().size() != 0){
            setMessageHandler(request);
        }
        if(destination != null || !destination.equals("FILE")) {
            request.getRequestDispatcher(destination).forward(request, response);
        }
    }


    //String methods below

    private String rivals(HttpServletRequest request, HttpServletResponse response) {
        setUserLoggedIn(request);
        if(request.getParameter("attack") != null){
            currentLogonUser.attackUser(handler.db.getUserByID(request.getParameter("attack")));
        }
        request.setAttribute("DB", handler.db.getRivalsDB(currentLogonUser));
        return "rivals.jsp";
    }

    private String downloadFile(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String userID = request.getParameter("u");
        handler.download(handler.db.getUserByID(userID),response);
        return "FILE";
    }

    private String adminLogout(HttpServletRequest request, HttpServletResponse response) {
        this.pass = "";
        return index(request,response);
    }

    private String adminLogin(HttpServletRequest request, HttpServletResponse response) {
        this.pass= request.getParameter("password");
        return admin(request,response);
    }

    private String admin(HttpServletRequest request, HttpServletResponse response) {
        if(handler.checkPassword(pass)){
            if(request.getParameter("removeUser") != null){
                if(handler.db.getUserByID(request.getParameter("removeUser")) == null){
                    handler.addError("cannot find "+request.getParameter("removeUser") + " ");
                }else{
                    handler.db.removeUser(handler.db.getUserByID(request.getParameter("removeUser")));
                    handler.addError("removed user");
                }
            }
            request.setAttribute("handler",handler);
            setMessageHandler(request);
            setErrorHandler(request);
            return "admin.jsp";
        }else{
            return adminLogout(request,response);
        }
    }

    private String destinationError(HttpServletRequest request, HttpServletResponse response){
        if(currentLogonUser != null){
            handler.addError("Error: Destination unknown " + " (User ID: "+currentLogonUser.getId()+") (#004)");
        }else{
            handler.addError("Error: Destination unknown, has no value (#005)");
        }
        return logout(request,response);
    }

    private String shop(HttpServletRequest request, HttpServletResponse response) {
        setUserLoggedIn(request);
        try{
            if(currentLogonUser.getShopItemByID(request.getParameter("Item"))==null){
                throw new NullPointerException("Error: Shop Item cannot be found, has no value (#006)");
            }
        }catch (NullPointerException e){
            return "shop.jsp";
        }
        currentLogonUser.getShopItemByID(request.getParameter("Item")).buy(this.currentLogonUser);
        return "shop.jsp";
    }

    private String logout(HttpServletRequest request, HttpServletResponse response) {
        currentLogonUser = null;
        return index(request,response);
    }

    private String missions(HttpServletRequest request, HttpServletResponse response) {
        setUserLoggedIn(request);
        String missionID = request.getParameter("Do");
        if(request.getParameter("Do") != null){
            currentLogonUser.doMission(handler.db.getMissionByID(missionID));
        }
        request.setAttribute("DB",handler.db.getMissionUser(currentLogonUser));
        return "missions.jsp";
    }

    private String register(HttpServletRequest request, HttpServletResponse response) {
        if(request.getParameter("username") == null || request.getParameter("username").trim().equals("")){
            handler.addError("No username given");
            return "index.jsp";
        }
        User registeredUser = handler.db.createNewUser(request.getParameter("username"));
        currentLogonUser = registeredUser;
        setUserLoggedIn(request);
        return "register.jsp";
    }

    private String login(HttpServletRequest request, HttpServletResponse response) {
        try {
            if ((request.getParameter("loginId") == null) || request.getParameter("loginId").trim().equals("")) {
                throw new IllegalArgumentException("Error: Geen geldige ID (#007)");
            } else {
                if (handler.db.getUserByID(request.getParameter("loginId")) == null) {
                    throw new NullPointerException("Error: Geen geldige ID (#008)");
                } else {
                    currentLogonUser = handler.db.getUserByID(request.getParameter("loginId"));
                    currentLogonUser.checkTimeForEnergy();
                    return account(request, response);
                }
            }
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }catch(NullPointerException ex){
            handler.addError(ex.getMessage());
        }
        return "index.jsp";
    }

    private String account(HttpServletRequest request, HttpServletResponse response) {
        setUserLoggedIn(request);
        return "account.jsp";
    }

    private String index(HttpServletRequest request, HttpServletResponse response) {
            request.setAttribute("DB",handler.db.getAmountOfPLayers());
        return "index.jsp";
    }

    //Util methods below

    private void setUser(HttpServletRequest request){
        request.setAttribute("user", currentLogonUser );
    }

    private void setUserLoggedIn(HttpServletRequest request){
        try{
            if(currentLogonUser == null){
                throw new NullPointerException("Error: You are not logged in (#009)");
            }else{
                setUser(request);
            }
        }catch(NullPointerException e){
            handler.addError(e.getMessage());
        }

    }

    private void setErrorHandler(HttpServletRequest request){
        request.setAttribute("errorMessage",handler.getError());
    }

    private void setMessageHandler(HttpServletRequest request){
        request.setAttribute("message",handler.getMessage());
    }
}


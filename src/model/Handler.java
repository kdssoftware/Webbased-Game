package model;

import db.DB;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Array;
import java.nio.file.Path;
import java.util.ArrayList;

public class Handler {
    public ArrayList<String> errorList;
    public ArrayList<String> messageList;
    public DB db = new DB(this);
    private String  password = "rupaul12";

    public Handler() {
        errorList = new ArrayList<>();
        messageList = new ArrayList<>();
    }

    public void download(User currentUser, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        String path = "/home/karel/test/";
        String file = currentUser.getId() + ".sav";
        response.setContentType("APPLICATION/OCTET-STREAM");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + file +"\"");
        FileInputStream fileInputStream = new FileInputStream(path + file);
        int i;
        while ((i = fileInputStream.read()) != -1) {
            out.write(i);
        }
        fileInputStream.close();
        out.close();

    }
    public void save(User user){
        try {
            PrintWriter out = new PrintWriter( "/home/karel/test/" + user.getId() + ".sav");
            out.println("USER");
            out.println(
                    user.getEnergy()+"\n"+
                            user.getLevel()+"\n" +
                            user.getUsername()+"\n"+
                            user.getId()+"\n" +
                            user.getCash()+"\n"+
                            user.getTime()+"\n"
            );
            out.println("SHOPITEMS");
            if(user.getShopItemDB().size()!=0) {
                for (ShopItem s : user.getShopItemDB()) {
                    out.println(s.getId());
                    out.println(s.getTimesBought());
                }
            }
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
    public void deleteAllErrors(){
        errorList = new ArrayList<>();
    }
    public ArrayList<String> getError() {
        return errorList;
    }

    public void addError(String error) {
        errorList.add(error);
    }

    public void deleteAllMessages(){
        messageList = new ArrayList<>();
    }

    public ArrayList<String> getMessage(){
        return messageList;
    }

    public void addMessage(String message){
        messageList.add(message);
    }

    public DB getDb(){
       return this.db;
    }

    public boolean checkPassword(String tryPass){
        if(tryPass == null || tryPass.trim().equals("")){
            this.addError("wrong password");
            return false;
        }else if(tryPass.equals(password)){
            return true;
        }
        addError("wrong pasword");
        return false;
    }

}

package model;

import java.util.UUID;

public class ShopItem {

    public Handler handler;
    private String name;
    private int cost;
    private String id;
    private int timesBought = 0;
    private boolean isNotWorking = false;

    public ShopItem(Handler handler, String name,int cost){
        this.handler = handler;
        setName(name);
        setCost(cost);
        this.id = "i"+ UUID.randomUUID().toString();
    }
    public void buy(User user){
        if(user.getCash() >= getCost()) {
            user.spendCash(getCost());
            setTimesBought(getTimesBought() + 1);
            handler.addMessage("Successfully bought 1 " + this.getName() +"!");
        }else{
            this.handler.addError("Error: You do not have enough cash (#034)");
        }

    }
    //Getters and Setters below
    public String getName() {
        return name;
    }

    private void setName(String name) {
        try{
            if(name == null){
                throw new NullPointerException("Error: Name of shop item (#025)");
            }else if(name.trim().equals("")){
                throw new IllegalArgumentException("Error: Name of shop item (#026)");
            }
            this.name = name;
        }catch(NullPointerException e){
            handler.addError(e.getMessage());
        }catch (IllegalArgumentException ex){
            handler.addError(ex.getMessage());
        }
    }

    public int getCost() {
        if(getTimesBought()==0){
            return cost;
        }
        return cost*getTimesBought();
    }

    private void setCost(int cost) {
        try{
            if(cost < 0){
                throw new IllegalArgumentException("Error: Cost of ShopItem "+name+" (#027)");
            }
            this.cost = cost;
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public String getId() {
        return id;
    }

    public int getTimesBought() {
        return timesBought;
    }

    protected void setTimesBought(int timesBought) {
        try{
            if(timesBought <= 0){
                throw  new IllegalArgumentException("Times bought for "+name+"is negative");
            }

        this.timesBought = timesBought;
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public boolean isNotWorking() {
        return isNotWorking;
    }

    public void setNotWorking(boolean notWorking) {
        isNotWorking = notWorking;
    }
}

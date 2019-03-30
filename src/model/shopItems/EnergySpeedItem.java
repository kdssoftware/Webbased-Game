package model.shopItems;

import model.Handler;
import model.ShopItem;
import model.User;

public class EnergySpeedItem extends ShopItem {
    private double increaseSpeed = 1.0;
    public EnergySpeedItem(Handler handler, String name,int cost) {
        super(handler, name, cost);
    }
    @Override
    public void buy(User user){
        super.setTimesBought(getTimesBought()+1);
        setIncreaseSpeed((int)(1.0/getIncreaseSpeed()*20.0));
    }

    public double getIncreaseSpeed() {
        return increaseSpeed;
    }

    public void setIncreaseSpeed(double increaseSpeed) {
        try {
            if (increaseSpeed <= 100.0) {
                throw new IllegalArgumentException("Error: Reached energy speed cap (#028)");
            }else {
                this.increaseSpeed = increaseSpeed;
            }
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }
}

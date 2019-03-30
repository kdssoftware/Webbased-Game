package model.shopItems;

import model.Handler;
import model.ShopItem;
import model.User;

public class IncreaseEnergyCapItem extends ShopItem {
    private int energyIncreasedAmount = 0;
    public IncreaseEnergyCapItem(Handler handler, String name, int cost) {
        super(handler, name, cost);
    }

    @Override
    public void buy(User user){
        if(user.getCash() >= getCost()) {
            super.buy(user);
            setEnergyIncreasedAmount(getEnergyIncreasedAmount()+1);
        }else{
            this.handler.addError("Error: You do not have enough cash #035");
        }
    }

    //Getters and Setters below

    public int getEnergyIncreasedAmount() {
        return energyIncreasedAmount;
    }

    private void setEnergyIncreasedAmount(int energyIncreasedAmount) {
        try{
            if(energyIncreasedAmount < 0){
                throw new IllegalArgumentException("Error: Energy Increased Amount has a negative amount (#033)");
            }

        }catch (IllegalArgumentException e){
            super.handler.addError(e.getMessage());
        }

        this.energyIncreasedAmount = energyIncreasedAmount;
    }
}

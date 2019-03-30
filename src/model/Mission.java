package model;

import java.util.UUID;

public class Mission {

    private String name;
    private int energyCost;
    private int cashEarnings;
    private int expEarnings;
    private int timesToComplete = 1;
    private String id;
    public Handler handler;
    private int availableFromLvl;
    private int increaseAttack;
    private int increaseDefense;
    private int increaseHealth;
    private int bonusExp;
    private int bonuxCash;
    private int bonusDefense;
    private int bonusHealth;
    private int bonusAttack;
    private int timesCompleted = 1;
    //ONLY CASH
    public Mission(Handler handler, String name,int energyCost,int availableFromLvl,int cashEarnings, int expEarnings,int increaseHealth,int increaseDefense, int increaseAttack){
        this.handler = handler;
        setName(name);
        setEnergyCost(energyCost);
        setAvailableFromLvl(availableFromLvl);
        setCashEarnings(cashEarnings);
        setExpEarnings(expEarnings);
        setIncreaseHealth(increaseHealth);
        setIncreaseDefense(increaseDefense);
        setIncreaseAttack(increaseAttack);
        this.id = "m"+UUID.randomUUID().toString();
    }

    public Mission(Handler handler, String name,int energyCost,int availableFromLvl,int cashEarnings, int expEarnings,int increaseHealth,int increaseDefense, int increaseAttack, int timesToComplete, int bonusExp, int bonuxCash, int bonusDefense, int bonusHealth, int bonusAttack){
        this.handler = handler;
        setName(name);
        setEnergyCost(energyCost);
        setAvailableFromLvl(availableFromLvl);
        setCashEarnings(cashEarnings);
        setExpEarnings(expEarnings);
        setIncreaseHealth(increaseHealth);
        setIncreaseDefense(increaseDefense);
        setIncreaseAttack(increaseAttack);

        setTimesToComplete(timesToComplete);
        setBonusExp(bonusExp);
        setBonuxCash(bonuxCash);
        setBonusHealth(bonusHealth);
        setBonusDefense(bonusDefense);
        setBonusAttack(bonusAttack);

        this.id = "m"+UUID.randomUUID().toString();
    }
    //Getters and Setters below

    public String getName() {
        return name;
    }

    private void setName(String name) {
        try {
            if (name == null || name.trim().equals("")) {
                throw new IllegalArgumentException("Error: name of mission is empty (#010)");
            }
            this.name = name;
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public int getEnergyCost() {
        if(timesCompleted!= 0){
            return (int)((double)energyCost*(((double)getTimesCompleted())/2.0)+(double)energyCost);
        }
        return energyCost;
    }

    private void setEnergyCost(int energyCost) {
        try{
            if(energyCost < 0){
                throw new IllegalArgumentException("Error: new energy cost is negative (#011)");
            }
            this.energyCost = energyCost;
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public int getCashEarnings() {
        if(timesCompleted!= 0){
            return (int)((double)cashEarnings*(((double)getTimesCompleted())/2.0)+(double)cashEarnings/2.0);
        }
        return cashEarnings;
    }

    private void setCashEarnings(int cashEarnings) {
        try {
            if (cashEarnings < 0) {
                throw new IllegalArgumentException("Error: Cash earnings is negative (#012)");
            }
            this.cashEarnings = cashEarnings;
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public String getId(){
        return this.id;
    }

    private void setExpEarnings(int expEarnings) {
        try {
            if (expEarnings < 0) {
                throw new IllegalArgumentException("Error: Exp earning of mission is negative (#013)");
            }
            this.expEarnings = expEarnings;
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public int getExpEarnings(){
        if(timesCompleted!= 0){
            return (int)((double)expEarnings*(((double)getTimesCompleted())/2.0)+(double)expEarnings/2.0);
        }
        return this.expEarnings;
    }

    public int getTimesToComplete() {
        return timesToComplete;
    }

    private void setTimesToComplete(int timesToComplete) {
        try {
            if (timesToComplete <= 0) {
                throw new IllegalArgumentException("Error: Times to complete from mission is 0 or less (#014)");
            }
            this.timesToComplete = timesToComplete;
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAvailableFromLvl() {
        return availableFromLvl;
    }

    private void setAvailableFromLvl(int availableFromLvl) {
        try{
            if(availableFromLvl <= 0){
                throw new IllegalArgumentException("Error: Available level "+availableFromLvl+" is too low (in mission:"+this.getName()+" (#039)");
            }else{
                this.availableFromLvl = availableFromLvl;
            }
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
        this.availableFromLvl = availableFromLvl;
    }
    public int getIncreaseAttack() {
        return increaseAttack;
    }

    private void setIncreaseAttack(int increaseAttack) {
        this.increaseAttack = increaseAttack;
    }

    public int getIncreaseDefense() {
        return increaseDefense;
    }

    private void setIncreaseDefense(int increaseDefense) {
        this.increaseDefense = increaseDefense;
    }

    public int getIncreaseHealth() {
        return increaseHealth;
    }

    private void setIncreaseHealth(int increaseHealth) {
        this.increaseHealth = increaseHealth;
    }

    public int getBonusExp() {
        if(getTimesCompleted()==0)
            return bonusExp;
        return (int)((double)bonusExp*(((double)getTimesCompleted())/2.0)+(double)bonusExp/2.0);
    }

    public void setBonusExp(int bonusExp) {
        this.bonusExp = bonusExp;
    }

    public int getBonuxCash() {
        if(getTimesCompleted()==0)
            return bonuxCash;
        return (int)((double)bonuxCash*(((double)getTimesCompleted())/2.0)+(double)bonuxCash/2.0);
    }

    public void setBonuxCash(int bonuxCash) {
        this.bonuxCash = bonuxCash;
    }

    public int getBonusDefense() {
        if(getTimesCompleted()==0)
            return bonusDefense;
        return (int)((double)bonusDefense*(((double)getTimesCompleted())/2.0)+(double)bonusDefense/2.0);
    }

    public void setBonusDefense(int bonusDefense) {
        this.bonusDefense = bonusDefense;
    }

    public int getBonusHealth() {
        if(getTimesCompleted()==0)
            return bonusHealth;
        return (int)((double)bonusHealth*(((double)getTimesCompleted())/2.0)+(double)bonusHealth/2.0);
    }

    public void setBonusHealth(int bonusHealth) {
        this.bonusHealth = bonusHealth;
    }

    public int getBonusAttack() {
        if(getTimesCompleted()==0)
            return bonusAttack;
        return (int)((double)bonusAttack*(((double)getTimesCompleted())/2.0)+(double)bonusAttack/2.0);
    }

    public void setBonusAttack(int bonusAttack) {
        this.bonusAttack = bonusAttack;
    }
    public void increaseTimesCompletedByOne(){
        this.timesCompleted += 1;
    }
    public int getTimesCompleted() {
        return timesCompleted;
    }
}

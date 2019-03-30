package model;

import db.CompletionDB;
import model.shopItems.EnergySpeedItem;
import model.shopItems.IncreaseEnergyCapItem;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.UUID;
import static java.time.Duration.between;

public class User {

    private int energy; //to do stuff
    private Level level;  //to unlock stuff
    private int cash;   //to buy stuff
    private String id;
    private int energyMax;
    public Handler handler;
    public CompletionDB completionDB;
    private LocalTime time = LocalTime.now();
    private double speed = 30.0;
    private double seconds = 0.0;
    public static final int MAX_ENERGY_START = 20;
    private ArrayList<ShopItem> shopItemDB;
    private String username;
    private int defense = 0;
    private int attack = 0;
    private int health = 100;
    public static final int MAX_HEALTH_START = 100;

    //private int abilityPoints
    public User(Handler handler, String username) {
        this.shopItemDB = new ArrayList<>();
        this.completionDB = new CompletionDB();
        this.level = new Level(handler);
        this.handler = handler;
        setEnergyMax(MAX_ENERGY_START);
        setEnergy(getEnergyMax());
        setCash(0);
        setUsername(username);
        this.id = "u" + UUID.randomUUID().toString();
        setCompletionDB();
        this.shopItemDB.add(new IncreaseEnergyCapItem(this.handler, "Increase energy cap", 50));

    }

    public void attackUser(User rival) {
        if (useEnergy(getLevel())) {
            if (this.getHealth() < 20) {
                if (rival.getDefense() > this.getAttack()) {
                    handler.addMessage("Defeated! You lost 20 health");
                    this.changeHealth(-20);
                } else if (rival.getHealth() < 10) {
                    this.handler.addError("This player is too badly injured");
                } else {
                    rival.changeHealth(-10);
                }
                earnCash((int) (((double) rival.getCash()) / 10.0));
                this.level.gainExp(10 * rival.getLevel());
                handler.addMessage("You won! " + ((double) rival.getCash()) / 10.0 + "$ and " + (10 * rival.getLevel()) + " exp");
            } else {
                this.handler.addError("You are too badly injured");
            }
        } else {
            this.handler.addError("You dont have enough energy");
        }
    }

    public void doMission(Mission mission){
        boolean noHealth = false;
        if(useEnergy(mission.getEnergyCost())){
            if(mission.getIncreaseHealth() != 0){//missions that decreases health
                if (getHealth() > Math.abs(mission.getIncreaseHealth())) {
                    this.changeHealth(mission.getIncreaseHealth());
                }else{
                    handler.addError("You are too badly injured");
                    noHealth = true;
                }
            }
            if(!noHealth){
                if(completionDB.getCompletedRatio(mission)>= 100){
                    completionDB.resetCompletion(mission);
                }
                earnAttack(mission.getIncreaseAttack());
                earnDefense(mission.getIncreaseDefense());
                earnCash(mission.getCashEarnings());
                level.gainExp(mission.getExpEarnings());
                completionDB.completed(mission);

                if(completionDB.getCompletedRatio(mission)>=100){
                    earnAttack(mission.getBonusAttack());
                    earnDefense(mission.getBonusDefense());
                    changeHealth(mission.getBonusHealth());
                    earnCash(mission.getBonuxCash());
                    this.level.gainExp(mission.getBonusExp());
                    String str = "You earn bonus stuff! ";
                    if(mission.getBonuxCash()!=0){
                        str += "$"+ mission.getBonuxCash() +" ";
                    }
                    if(mission.getBonusExp() != 0){
                        str += mission.getBonusExp() + " exp ";
                    }
                    if(mission.getBonusAttack() != 0){
                        str += mission.getBonusAttack() + " attack ";
                    }
                    if(mission.getBonusDefense() != 0){
                        str += mission.getBonusDefense() + " defense ";
                    }
                    handler.addMessage(str) ;
                }
            }
        }
    }

    public void spendCash(int cash){
        try {
            if (cash < 0) {
                throw new IllegalArgumentException("Error: Cash amount is negative (#015)");
            }
            setCash(getCash() - cash);
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    protected void earnCash(int cash){
        try {
            if (cash < 0) {
                throw new IllegalArgumentException("Error: Cash amount is negative (#016)");
            }
            setCash(getCash() + cash);
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    protected boolean useEnergy(int energy){
        int restEnergy = getEnergy()-energy;
        try{
            if(energy < 0 ){
                throw new IllegalArgumentException("Error: Energy cannot use negative energy (#017)");
            }
            if(energy> getEnergy()){
                throw new IllegalArgumentException("You don't have enough energy");
            }else{
                setEnergy(restEnergy);
            }
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
            return false;
        }
        return true;
    }
    public void checkTimeForEnergy() {
        if(getEnergy() == getEnergyMax()){
            time = LocalTime.now();
            return;
        }else{
            LocalTime timeNow = LocalTime.now();
            Duration dur = between(time,timeNow);
            seconds = (double)dur.getSeconds();
            if(seconds >= speed){
                time = LocalTime.now();
                int energyGained = (int)((seconds-(seconds%speed))/speed);
                seconds = seconds%speed;
                if(getEnergy()+energyGained >= getEnergyMax()){
                    setEnergy(getEnergyMax());
                }else{
                    setEnergy(getEnergy()+energyGained);
                }
            }
        }
    }

    public int getRatioExp(){
        int ratioExp;
        if(this.getExp()==0){
            ratioExp = 0;
        }else {
            int level = this.getLevel();
            int expCap_percent = getExpCap(level);
            int exp = this.getExp();
            double _ratio = ((double)exp/expCap_percent);
            double ratio = (_ratio)*100;
            ratioExp = (int)ratio;
        }
        return ratioExp;
    }

    public int getRatioEnergy(){
         int ratioEn;
        if(this.getEnergy()==0){
            ratioEn = 0;
        }else {
            ratioEn = (int)((double)this.getEnergy()/((double)this.getEnergyMax()/100.0));
        }
        return ratioEn;
    }

    public void increaseSpeed(double time){
        try {
            if (time <= 0) {
                throw new IllegalArgumentException("Error: Increase speed time is a negative (#019)");
            }
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }


    public  ShopItem getShopItemByID(String uuid){
            for(ShopItem i : shopItemDB){
                if(i.getId().equals(uuid)){
                    return i;
                }
            }
            return null;
    }

    public String getIDItemByName(String name){
            if(name != null) {
                    for (ShopItem i : this.getShopItemDB()) {
                    if (i.getName().equals(name)) {
                        return i.getId();
                    }
                }
            }else{
                throw new NullPointerException("Cannot get ID from item's name, name has no value");
            }
        return null;
    }
    public void changeHealth(int health){
        if(health+this.health >= MAX_HEALTH_START && health != 0){
            setHealth(MAX_HEALTH_START);
            handler.addMessage("You have full health");
        }else {
            this.health += health;
        }
    }
    private void earnAttack(int attack){
        this.attack += attack;
    }
    private void earnDefense(int defense){
        this.defense += defense;
    }
    //Getters and Setters below

    public int getEnergy() {

        return this.energy;
    }

    private void setEnergy(int energy) {
        try {
            if (energy < 0) {
                throw new IllegalArgumentException("Error: Energy is a negative amount (#021)");
            }
            this.energy = energy;
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public int getCash() {
        return cash;
    }

    private void setCash(int cash) {
        try {
            if (cash < 0) {
                throw new IllegalArgumentException("Internal Error: Cash set lower then 0 (#022)");
            }
            this.cash = cash;
        }catch(IllegalArgumentException e){
           return;
        }
    }

    public String getId() {
        return id;
    }

    public int getEnergyMax(){
        try{
            IncreaseEnergyCapItem ieci;
            String name ="Increase energy cap";
            String si_id = getIDItemByName(name);
            ShopItem si = getShopItemByID(si_id);
            if(si instanceof IncreaseEnergyCapItem){
                ieci = (IncreaseEnergyCapItem)si;
                return MAX_ENERGY_START+ieci.getEnergyIncreasedAmount();
            }else if (si != null){
                throw new IllegalArgumentException("Error: " +name +" item (#023)");
            }
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }catch (NullPointerException e){
            handler.addError(e.getMessage());
        }
        return this.energyMax;
    }

    private void setEnergyMax(int energyMax){
        this.energyMax = energyMax;
    }

    public int getLevel(){
        return level.getLevel();
    }

    public int getExp(){
        return level.getExp();
    }

    public int getExpCap(int level){
        return this.level.getExpCapFromLevel(level);
    }

    public CompletionDB getCompletionDB() {
        return completionDB;
    }

    public double getSeconds() {
        return seconds;
    }

    public double getSpeed() {
        return speed;
    }

    private void setSpeed(double speed) {
        try {
            if (speed >= 0.0 || speed < 120.0) {
                throw new IllegalArgumentException("Internal Error: Speed of Energy (#024)");
            }
            this.speed = speed;
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public ArrayList<ShopItem> getShopItemDB() {
        return shopItemDB;
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        try{
            if(username == null){
                throw new NullPointerException("username cannot be empty ");
            }else if(username.trim().equals("")){
                throw new IllegalArgumentException("username cannot be empty");
            }else{
                this.username = username;
            }
        }catch(NullPointerException e){
            handler.addError(e.getMessage());
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public LocalTime getTime() {
        return time;
    }

    public int getDefense() {
        return defense;
    }

    private void setDefense(int defense) {
        if(defense < 0){
            throw new IllegalArgumentException("defense of a user cannot be less than 0");
        }
        this.defense = defense;
    }

    public int getAttack() {
        return attack;
    }

    private void setAttack(int attack) {
        if(attack < 0){
            throw new IllegalArgumentException("attack of user cannot be less than 0");
        }
        this.attack = attack;
    }

    private void setCompletionDB() {
        for(Mission m : (ArrayList<Mission>)handler.db.getMissionDB()){
            completionDB.add(m,0.0);
        }
    }

    public int getHealth() {
        return health;
    }

    private void setHealth(int health) {
        this.health = health;
    }
}

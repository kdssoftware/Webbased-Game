package model;

public class Level {

    private int level;
    private int exp;
    public Handler handler;
    private int[] expMaxPerLevel = new int[] {
            50,150,300,500,800,1200,1500,2100,3000,4200,5500,7300,9900,14000,200000
    };
    private int levelCap;
    public Level(Handler handler){
        this.handler = handler;
        createMaxExpPerLevel();
        setLevel(1);
        setExp(0);
    }

    protected void gainExp(int expGained){
        int current_level = getLevel();
        if(expGained <= 0){
            return;
        }else if(expGained >= getExpCapFromLevel(getLevel())-getExp()){
            levelUp();
            gainExp(expGained-(getExpCapFromLevel(current_level)-getExp()));
        }else{
            setExp(getExp()+expGained);
        }
    }

    protected int getExpCapFromLevel(int level){
        try {
            if(level > expMaxPerLevel.length){
                throw new IllegalArgumentException("Error: Level cap exceeded (#029)");
            }
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
        return expMaxPerLevel[level-1];
    }

    private void createMaxExpPerLevel() {
        this.levelCap = expMaxPerLevel.length;
    }

    private void levelUp() {
        handler.addMessage("You leveled up! New jobs are available");
        this.level += 1;
        setExp(0);
    }

    //Getters and Setters below
    private void setLevel(int level){
        try {
            if (level <= 0) {
                throw new IllegalArgumentException("Error: Level set lower then 0 (#030)");
            } else if (level > levelCap) {
                throw new IllegalArgumentException("Error: Level exceeded the level cap (#031)");
            }
            this.level = level;
        }catch(IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

    public int getLevel() {
        return level;
    }

    public int getExp() {
        return exp;
    }

    private void setExp(int exp) {
        try {
            if (exp < 0) {
                throw new IllegalArgumentException("Error: Exp amount is negative (#032)");
            }
            this.exp = exp;
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }
    }

}

package db;

import model.Handler;
import model.Mission;
import model.User;
import java.util.ArrayList;
import java.util.Random;

public class DB {

    public Handler handler;
    private ArrayList<User> userDb = new ArrayList<>();
    private ArrayList<Mission> missionDB = new ArrayList<>();

    public DB(Handler handler){
        this.handler = handler;

        this.missionDB.add(new Mission(this.handler,"Look for a job", 1, 1, 0, 10, 0, 0, 0,
                /**ON COMPLETION: */ 10, 100, 1300, 0,0,0));

        this.missionDB.add(new Mission(this.handler, "Outsmart the boss", 2, 2, 100, 10, 0, 0, 0,
                                    5, 250, 500,0,0,0));

        this.missionDB.add(new Mission(this.handler, "get into a fight", 3, 2, 0, 50, -10,0,0,
                                            5,500, 0, 0, 0,0));

        this.missionDB.add(new Mission(this.handler, "patch yourself up", 1, 2, 0, 0, 20,0,0,
                                                    5,0,0,0,60,0));

        this.missionDB.add(new Mission(this.handler, "Hire a thug", 5, 3, 0, 40,0,0,1,
                                                    5,200,1000,0,0,2));

        this.missionDB.add(new Mission(this.handler, "Bribe a police officer", 5, 3, 0, 40, 0, 1, 0,
                                                            5,700,400,2,0,0));

    }
    public ArrayList getMissionUser(User u){
        try {
            ArrayList<Mission> lvlCappedMissionDB = new ArrayList<>();
            for (Mission m : missionDB) {
                if (m.getAvailableFromLvl() <= u.getLevel()) {
                    lvlCappedMissionDB.add(m);
                }
            }
            if (lvlCappedMissionDB.size() <= 0) {
                throw new IllegalArgumentException("Error: while getting the mission, something wrong with your lvl (#040)");
            }
            return lvlCappedMissionDB;
        }catch (IllegalArgumentException e) {
            handler.addError(e.getMessage());
        }
        return missionDB;
    }

    public ArrayList getRivalsDB(User currentUser){
        ArrayList<User> userRivalsTemp = this.getUserDB();
        userRivalsTemp.remove(currentUser);
        if(userRivalsTemp.size()==0){
            handler.addError("No other users on the platform");
        }
        return userRivalsTemp;
    }

    public ArrayList getMissionDB(){
        return this.missionDB;
    }

    public int getAmountOfPLayers(){
        return userDb.size();
    }

    public ArrayList getUserDB(){
        return this.userDb;
    }

    public void addMission(Mission mission){
        missionDB.add(mission);
    }

    public void addUser(User user){
        userDb.add(user);
    }

    public User createNewUser(String username){
        User u = new User(handler,username);
        userDb.add(u);
        return u;
    }

    public User getUserByID(String uuid){
        try{
            for(User u : userDb){
                if(u.getId().equals(uuid)){
                    return u;
                }
            }
            throw new NullPointerException("Error: UserID, "+uuid+" not found (#001)");
        }catch (NullPointerException e){
            handler.addError(e.getMessage());
            return null;
        }
    }

    public Mission getMissionByID(String uuid){
        try {
            for (Mission m : missionDB) {
                String id = m.getId();
                if (id.equals(uuid)) {
                    return m;
                }
            }
            throw new NullPointerException("Error: MissionID,"+uuid+" not found (#002)");
        }catch (NullPointerException e){
            handler.addError(e.getMessage());
            return null;
        }
    }
    public int getIndexByUser(User user){
        try {
            if(user == null){
                throw new IllegalArgumentException("Error: User cannot be found (#041)");
            }else{
                for(int i = 0; i <= userDb.size(); i++){
                    String s =userDb.get(i).getId() ;
                    String st = user.getId();
                    if(userDb.get(i).getId().equals(user.getId())){
                        return i;
                    }
                }
                throw new NullPointerException("Error: User cannot be found (#042)");
            }
        }catch (IllegalArgumentException e){
            handler.addError(e.getMessage());
        }catch(NullPointerException ex){
            handler.addError(ex.getMessage());
        }
        return -1;
    }
    public void removeUser(User u){
        userDb.remove(u);
    }
}

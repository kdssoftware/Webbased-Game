package db;
import model.Handler;
import model.Mission;
import java.util.ArrayList;

public class CompletionDB {
    private ArrayList<model.Mission> missionList = new ArrayList<>();
    private ArrayList<Double> completionList = new ArrayList<>();
    Handler handler = new Handler();
    public CompletionDB(){

    }


    public void add(model.Mission mission, Double completion){
        missionList.add(mission);
        completionList.add(completion);
    }

    public void completed(model.Mission mission){
        int index = getIndexByMission(mission);
        try{
            if(index == -1){
                throw new IndexOutOfBoundsException("Error: could not find mission, "+mission.getName()+", with id, "+index+" (#003)");
            }
        }catch (IndexOutOfBoundsException e){
            handler.addError(e.getMessage());
        }
        Mission m = missionList.get(index);
        double times = m.getTimesToComplete();
        Double c = completionList.get(index);
        if(c>=100.0){
            return;
        }
        completionList.set(index,c+(100.0/times));
    }

    public int getCompletedRatio(Mission mission){
        return completionList.get(getIndexByMission(mission)).intValue();
    }

    private int getIndexByMission(model.Mission mission){
        for(int i = 0; i < missionList.size();i++){
            if(mission.getName().equals(missionList.get(i).getName())){
                return i;
            }
        }
        return -1;
    }

    public void resetCompletion(Mission mission){
        mission.increaseTimesCompletedByOne();
        completionList.set(getIndexByMission(mission),0.);
    }
}

package Conductor_Model;

public class MasterPlayer extends Player {

    public MasterPlayer() {
    }

    public MasterPlayer(String name, int level, int investigationPoints) {
        super(name, level, investigationPoints);
    }

    @Override
    public void addPoints(int points, int trial_type){
        if(points > 0){ // WIN
            if(trial_type == this.level+1){
                this.investigationPoints = 10;
            }else {
                this.investigationPoints += points;
            }
        }else{ // LOSE
            this.investigationPoints += points/2;
        }
    }

}

package Conductor_Model;

public abstract class Player {

    protected String name;
    protected int level;
    protected int investigationPoints;

    public Player(String name, int level, int investigationPoints) {
        this.name = name;
        this.level = level;
        this.investigationPoints = investigationPoints;
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isAlive() {
        return investigationPoints >= 1;
    }

    public int getInvestigationPoints() {
        return investigationPoints;
    }

    public void setInvestigationPoints(int investigationPoints) {
        this.investigationPoints = investigationPoints;
    }

    public String toCSV(){
        return name + "-" + investigationPoints +  "-" + level;
    }

    public void setPlayerValuesFromCSV(String line){
        String[] values = line.split("-");
        name = values[0];
        investigationPoints = Integer.parseInt(values[1]);
        //System.out.println("Value of values[2]: " + values[2]);
        //System.out.println("Before: " + setTrueFalseFromString(values[2]));
        //System.out.println("After: " + alive);
        level = Integer.parseInt(values[2]);
    }

   public void addPoints(int points, int trial_type){

    }

}

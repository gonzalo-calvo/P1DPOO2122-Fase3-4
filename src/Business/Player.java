package Business;

public class Player {

    private String name;
    private boolean alive;
    private int investigationPoints;
    private int level;
    private boolean trialPass;

    public Player(String name, boolean alive, int investigationPoints, int level, boolean trialPass) {
        this.name = name;
        this.alive = alive;
        this.investigationPoints = investigationPoints;
        this.level = level;
        this.trialPass = trialPass;
    }

    public Player() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public int getInvestigationPoints() {
        return investigationPoints;
    }

    public void setInvestigationPoints(int investigationPoints) {
        this.investigationPoints = investigationPoints;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public boolean isTrialPass() {
        return trialPass;
    }

    public void setTrialPass(boolean trialPass) {
        this.trialPass = trialPass;
    }

    public String toCSV(){
        return name + "-" + investigationPoints + "-" + alive + "-" + level;
    }

    public void setPlayerValuesFromCSV(String line){
        String[] values = line.split("-");
        name = values[0];
        investigationPoints = Integer.parseInt(values[1]);
        //System.out.println("Value of values[2]: " + values[2]);
        //System.out.println("Before: " + setTrueFalseFromString(values[2]));
        alive = setTrueFalseFromString(values[2]);
        //System.out.println("After: " + alive);
        level = Integer.parseInt(values[3]);
    }

    public boolean setTrueFalseFromString(String line){
        return line.equals("true");
    }

}

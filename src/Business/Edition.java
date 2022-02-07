package Business;

import java.io.File;
import java.util.ArrayList;

public class Edition {

    private int editionYear;
    private int numPlayers;
    private int numTrials;
    private int trialExecuting=0;
    private ArrayList<Trial> trials;
    private ArrayList<Player> playerList;

    public Edition(int editionYear, int numPlayers, int numTrials, ArrayList<Trial> trials, ArrayList<Player> playerList, int trialExecuting) {
        this.editionYear = editionYear;
        this.numPlayers = numPlayers;
        this.numTrials = numTrials;
        this.trials = trials;
        this.playerList = playerList;
        this.trialExecuting = trialExecuting;
    }

    public Edition() {
        this.trials = new ArrayList<>();
        this.playerList = new ArrayList<>();
    }

    public int getTrialExecuting() {
        return trialExecuting;
    }

    public void setTrialExecuting(int trialExecuting) {
        this.trialExecuting = trialExecuting;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public int getEditionYear() {
        return editionYear;
    }

    public void setEditionYear(int editionYear) {
        this.editionYear = editionYear;
    }

    public int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        this.numPlayers = numPlayers;
    }

    public int getNumTrials() {
        return numTrials;
    }

    public void setNumTrials(int numTrials) {
        this.numTrials = numTrials;
    }

    public ArrayList<Trial> getTrials() {
        return trials;
    }

    public void setTrials(ArrayList<Trial> trials) {
        this.trials = trials;
    }

    public void printDetails(){
        System.out.println("\nYear: " + editionYear);
        System.out.println("Players: " + numPlayers);

        for (int i = 0; i < numTrials; i++) {
            System.out.println("    " + (i+1) + "- " + trials.get(i).getTrialNameDetail());
        }

    }

    public boolean isAnyoneAlive(){

        for (Player player : playerList) {
            if (player.isAlive()) {
                return true;
            }
        }
        return false;
    }

    public int howManyFinishers(){
        int count=0;
        for (Player player : playerList) {
            if (player.isAlive()) {
                count++;
            }
        }
        return count;
    }

    public String toCSV(){
        return editionYear + ";" + numPlayers + ";" + playersListToCSV() + ";" + numTrials + ";" + trialsListToCSV() + ";" + trialExecuting;
    }

    public String playersListToCSV(){
        StringBuilder stringPlayers = new StringBuilder();

        if (playerList.size()==0){
            return stringPlayers.toString();
        }

        for (int i = 0; i < playerList.size(); i++) {
            if (i==playerList.size()-1){
                stringPlayers.append(playerList.get(i).toCSV());
            } else {
                stringPlayers.append(playerList.get(i).toCSV()).append(":");
            }
        }
        return stringPlayers.toString();
    }

    public String trialsListToCSV(){
        StringBuilder stringTrials = new StringBuilder();

        if (trials.size()==0){
            return stringTrials.toString();
        }

        for (int i = 0; i < trials.size(); i++) {
            if (i==trials.size()-1){
                stringTrials.append(trials.get(i).toCSV());
            } else {
                stringTrials.append(trials.get(i).toCSV()).append(":");
            }
        }
        return stringTrials.toString();
    }

    public void setEditionValuesFromCSV(String line) {
        String[] values = line.split(";");
        editionYear = Integer.parseInt(values[0]);
        numPlayers = Integer.parseInt(values[1]);

        String[] playerValues = values[2].split(":");
        //System.out.println("length value of player values: " + playerValues.length);
        //System.out.println("value of only element of player value: " + playerValues[0] + " :");
        if (!playerValues[0].equals("")) {
            for (int i = 0; i < playerValues.length; i++) {
                Player player = new Player();
                player.setPlayerValuesFromCSV(playerValues[i]);
                playerList.add(player);
            }
        }

        numTrials = Integer.parseInt(values[3]);

        String[] trialValues = values[4].split(":");
        for (int i = 0; i < trialValues.length; i++) {
            Trial trial = new Trial();
            trial.setValuesFromCSV(trialValues[i]);
            trials.add(trial);
        }

        trialExecuting = Integer.parseInt(values[5]);

    }
}

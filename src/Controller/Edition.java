package Controller;

import Composer_Model.*;
import Conductor_Model.EngineerPlayer;
import Conductor_Model.Player;
import java.util.ArrayList;

public class Edition {

    private int editionYear;
    private int numPlayers;
    private int numTrials;
    private int trialExecuting=0;
    private ArrayList<Trial> editionsTrialsList;
    private ArrayList<Player> playerList;

    public Edition(int editionYear, int numPlayers, int numTrials, ArrayList<Trial> editionsTrialsList, ArrayList<Player> playerList, int trialExecuting) {
        this.editionYear = editionYear;
        this.numPlayers = numPlayers;
        this.numTrials = numTrials;
        this.editionsTrialsList = editionsTrialsList;
        this.playerList = playerList;
        this.trialExecuting = trialExecuting;
    }

    public Edition() {
        this.editionsTrialsList = new ArrayList<>();
        this.playerList = new ArrayList<>();
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

    public int getTrialExecuting() {
        return trialExecuting;
    }

    public void setTrialExecuting(int trialExecuting) {
        this.trialExecuting = trialExecuting;
    }

    public ArrayList<Trial> getEditionsTrialsList() {
        return editionsTrialsList;
    }

    public void setEditionsTrialsList(ArrayList<Trial> editionsTrialsList) {
        this.editionsTrialsList = editionsTrialsList;
    }

    public ArrayList<Player> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public void printDetails(){
        System.out.println("\nYear: " + editionYear);
        System.out.println("Players: " + numPlayers);
        System.out.println("Trials: ");

        for (int i = 0; i < numTrials; i++) {
            System.out.println("    " + (i+1) + "- " + editionsTrialsList.get(i).getName() + " (" + getTrialNameDetail(editionsTrialsList.get(i).getType()) + ")");
        }
    }

    public String getTrialNameDetail(int type){

        return switch (type) {
            case 1 -> "Paper publication";
            case 2 -> "Master studies";
            case 3 -> "Doctoral thesis defense";
            case 4 -> "Budget request";
            default -> "Should never see this message";
        };

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

        if (editionsTrialsList.size()==0){
            return stringTrials.toString();
        }

        for (int i = 0; i < editionsTrialsList.size(); i++) {
            Trial trial = editionsTrialsList.get(i);
            if (trial.getType() == 1) {
                PaperPublicationTrial paperPublicationTrial = (PaperPublicationTrial) trial;
                stringTrials.append(paperPublicationTrial.toCSV());
            } else if (trial.getType() == 2) {
                MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) trial;
                stringTrials.append(masterStudiesTrial.toCSV());
            } else if (trial.getType() == 3) {
                DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) trial;
                stringTrials.append(doctoralThesisDefenseTrial.toCSV());
            } else if (trial.getType() == 4) {
                BudgetRequestTrial budgetRequestTrial = (BudgetRequestTrial) trial;
                stringTrials.append(budgetRequestTrial.toCSV());
            }

            if (i!=editionsTrialsList.size()-1){
                stringTrials.append(":");
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
                Player player = new EngineerPlayer();
                player.setPlayerValuesFromCSV(playerValues[i]);
                playerList.add(player);
            }
        }

        numTrials = Integer.parseInt(values[3]);

        String[] trialValues = values[4].split(":");
        for (int i = 0; i < trialValues.length; i++) {
            switch (trialValues[i].charAt(0)) {
                case '1' -> {
                    PaperPublicationTrial paperPublicationTrial = new PaperPublicationTrial();
                    paperPublicationTrial.setType(1);
                    paperPublicationTrial.setValuesFromCSV(trialValues[i]);
                    editionsTrialsList.add(paperPublicationTrial);
                }
                case '2' -> {
                    MasterStudiesTrial masterStudiesTrial = new MasterStudiesTrial();
                    masterStudiesTrial.setType(2);
                    masterStudiesTrial.setValuesFromCSV(trialValues[i]);
                    editionsTrialsList.add(masterStudiesTrial);
                }
                case '3' -> {
                    DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = new DoctoralThesisDefenseTrial();
                    doctoralThesisDefenseTrial.setType(3);
                    doctoralThesisDefenseTrial.setValuesFromCSV(trialValues[i]);
                    editionsTrialsList.add(doctoralThesisDefenseTrial);
                }
                case '4' -> {
                    BudgetRequestTrial budgetRequestTrial = new BudgetRequestTrial();
                    budgetRequestTrial.setType(4);
                    budgetRequestTrial.setValuesFromCSV(trialValues[i]);
                    editionsTrialsList.add(budgetRequestTrial);
                }
                default -> System.out.println("No identification number for trial type");
            }
        }

        trialExecuting = Integer.parseInt(values[5]);

    }




}

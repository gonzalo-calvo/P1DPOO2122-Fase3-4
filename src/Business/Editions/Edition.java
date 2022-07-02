package Business.Editions;

import Business.Trials.*;
import Business.Players.EngineerPlayer;
import Business.Players.Player;
import java.util.ArrayList;

/**
 * Classe Edició
 */
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


    /**
     * Aquest mètode printa tota l'informació de la edició
     */
    public void printDetails(){
        System.out.println("\nYear: " + editionYear);
        System.out.println("Players: " + numPlayers);
        System.out.println("Trials: ");

        for (int i = 0; i < numTrials; i++) {
            System.out.println("    " + (i+1) + "- " + editionsTrialsList.get(i).getName() + " (" + getTrialNameDetail(editionsTrialsList.get(i).getType()) + ")");
        }
    }

    /**
     * Aquest mètode printa el nom del tipos de trial depenent del tipos de trial
     * @param type Tipos de trial a printar
     * @return Retorna el text que ha de printar
     */
    public String getTrialNameDetail(int type){

        return switch (type) {
            case 1 -> "Paper publication";
            case 2 -> "Master studies";
            case 3 -> "Doctoral thesis defense";
            case 4 -> "Budget request";
            default -> "Should never see this message";
        };

    }

    /**
     * Aquest mètode busca si hi han jugadors que segueixen vius, es a dir, que tinguin mes d'un punt
     * @return Retorna un boolean a true si almenys algu esta viu
     */
    public boolean isAnyoneAlive(){

        for (Player player : playerList) {
            if (player.isAlive()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Aquest mètode busca si quedan jugadors amb més d'un punt una vegada acabada les proves per saber quants jugadors han acbaat les proves
     * @return Retorna un integer amb la quantitat de jugadors que han acabat les proves
     */
    public int howManyFinishers(){
        int count=0;
        for (Player player : playerList) {
            if (player.isAlive()) {
                count++;
            }
        }
        return count;
    }

    /**
     * Aquest mètode genera un string amb l'infomació de la edició
     * @return String amb l'informació de l'edició
     */
    public String toCSV(){
        return editionYear + ";" + numPlayers + ";" + playersListToCSV() + ";" + numTrials + ";" + trialsListToCSV() + ";" + trialExecuting;
    }

    /**
     * Aquest mètode genera una string amb l'informació de cada jugador amb format CSV
     * @return Retorna un string de tipos CSV
     */
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

    /**
     * Aquest mètode genera una string de tipos CSV per guardarla
     * @return Retorna la cadena string amb format CSV
     */
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

    /**
     * Aquest mètode agafa una linea de tipos CSV del fitxer i extreu informació per guardarla a un objecte tipos edition a la llista d'edicions
     * @param line String amb l'informació de l'edició
     */
    public void setEditionValuesFromCSV(String line) {
        System.out.println("line is: " + line );
        String[] values = line.split(";");
        editionYear = Integer.parseInt(values[0]);
        numPlayers = Integer.parseInt(values[1]);

        String[] playerValues = values[2].split(":");
        //System.out.println("length value of player values: " + playerValues.length);
        //System.out.println("value of only element of player value: " + playerValues[0] + " :");
        if (!playerValues[0].equals("")) {
            for (String playerValue : playerValues) {
                EngineerPlayer engineer = new EngineerPlayer();
                engineer.setPlayerValuesFromCSV(playerValue);
                playerList.add(engineer);
            }
        }

        numTrials = Integer.parseInt(values[3]);

        String[] trialValues = values[4].split(":");
        for (String trialValue : trialValues) {
            switch (trialValue.charAt(0)) {
                case '1' -> {
                    PaperPublicationTrial paperPublicationTrial = new PaperPublicationTrial();
                    paperPublicationTrial.setType(1);
                    paperPublicationTrial.setValuesFromCSV(trialValue);
                    editionsTrialsList.add(paperPublicationTrial);
                }
                case '2' -> {
                    MasterStudiesTrial masterStudiesTrial = new MasterStudiesTrial();
                    masterStudiesTrial.setType(2);
                    masterStudiesTrial.setValuesFromCSV(trialValue);
                    editionsTrialsList.add(masterStudiesTrial);
                }
                case '3' -> {
                    DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = new DoctoralThesisDefenseTrial();
                    doctoralThesisDefenseTrial.setType(3);
                    doctoralThesisDefenseTrial.setValuesFromCSV(trialValue);
                    editionsTrialsList.add(doctoralThesisDefenseTrial);
                }
                case '4' -> {
                    BudgetRequestTrial budgetRequestTrial = new BudgetRequestTrial();
                    budgetRequestTrial.setType(4);
                    budgetRequestTrial.setValuesFromCSV(trialValue);
                    editionsTrialsList.add(budgetRequestTrial);
                }
                default -> System.out.println("No identification number for trial type");
            }
        }

        trialExecuting = Integer.parseInt(values[5]);

    }

}

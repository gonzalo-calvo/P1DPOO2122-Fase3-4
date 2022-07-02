package Business.Execution;

import Business.Editions.Edition;
import Business.Trials.BudgetRequestTrial;
import Business.Trials.DoctoralThesisDefenseTrial;
import Business.Trials.MasterStudiesTrial;
import Business.Trials.PaperPublicationTrial;
import Business.Players.DoctorPlayer;
import Business.Players.EngineerPlayer;
import Business.Players.MasterPlayer;
import Business.Players.Player;
import Persistence.DAOs.ExecutionDAO;
import Presentation.MenuController;
import Presentation.Views.ExecutionView;

import java.awt.*;
import java.util.*;

/**
 * Classe que s'encarrega de executar la edició de l'any actual
 */
public class ExecutionManager {

    private ExecutionDAO executionDAO;
    private MenuController menuController;
    private ExecutionView executionView;

    private Edition editionExecute;


    public ExecutionManager(){

    }

    public ExecutionManager(ExecutionDAO executionDAO, MenuController menuController, ExecutionView executionView) {
        this.executionDAO = executionDAO;
        this.menuController = menuController;
        this.executionView = executionView;
        this.editionExecute = executionDAO.getExecutionFromFile();
    }

    public void setEditionExecute(Edition editionExecute) {
        this.editionExecute = new Edition(editionExecute.getEditionYear(), editionExecute.getNumPlayers(), editionExecute.getNumTrials(), editionExecute.getEditionsTrialsList(), editionExecute.getPlayerList(), editionExecute.getTrialExecuting());
    }


    /**
     * Mètode general que s'encarrega d'executar la edició
     */
    public void executeEdition(){

        String continueExecution = "yes";

        System.out.println("\n--- The Trials " + editionExecute.getEditionYear() + " ---\n");

        if (editionExecute.getPlayerList().isEmpty()) {   //if empty --> first time executing
            for (int i = 0; i < editionExecute.getNumPlayers(); i++) {
                EngineerPlayer auxEngineer = new EngineerPlayer();
                auxEngineer.setName(askUserNonEmptyString("Enter the player’s name (" + (i + 1) + "/" + editionExecute.getNumPlayers() + "): "));
                auxEngineer.setInvestigationPoints(5);
                auxEngineer.setLevel(1);
                editionExecute.getPlayerList().add(auxEngineer);
            }
        } else {
            for (int i = 0; i < editionExecute.getNumPlayers(); i++) {
                menuController.printPlayer(editionExecute.getPlayerList().get(i));
            }
        }

        while (editionExecute.getNumTrials()>editionExecute.getTrialExecuting() && continueExecution.equals("yes") && editionExecute.isAnyoneAlive()){

            System.out.println("\nTrial #" + (editionExecute.getTrialExecuting()+1) + " - " + editionExecute.getEditionsTrialsList().get(editionExecute.getTrialExecuting()).getName() + "\n");

            switch (editionExecute.getEditionsTrialsList().get(editionExecute.getTrialExecuting()).getType()){
                case 1:
                    for (int i = 0; i < editionExecute.getNumPlayers(); i++) {
                        if (editionExecute.getPlayerList().get(i).isAlive()){
                            submitPlayerInPaperPublicationTrial(editionExecute, i, editionExecute.getTrialExecuting());
                        }
                    }
                    break;

                case 2:
                    for (int i = 0; i < editionExecute.getNumPlayers(); i++) {
                        if (editionExecute.getPlayerList().get(i).isAlive()){
                            submitPlayerInMasterStudiesTrial(editionExecute, i, editionExecute.getTrialExecuting());
                        }
                    }
                    break;

                case 3:
                    for (int i = 0; i < editionExecute.getNumPlayers(); i++) {
                        if (editionExecute.getPlayerList().get(i).isAlive()){
                            submitPlayerInDoctoralThesis(editionExecute, i, editionExecute.getTrialExecuting());
                        }
                    }
                    break;

                case 4:
                    submitTeamInBudgetRequest(editionExecute, editionExecute.getTrialExecuting());
                    break;

                default:
                    System.out.println("Error de switch escogiendo tipo de trial. Valor switch: " + editionExecute.getEditionsTrialsList().get(editionExecute.getTrialExecuting()).getType());
            }

            System.out.println("\nUpdating player evolution");
            updatePlayerEvolution(editionExecute);

            editionExecute.setTrialExecuting(editionExecute.getTrialExecuting()+1);

            if ((editionExecute.getNumTrials()==editionExecute.getTrialExecuting())) {
                System.out.println("\nTHE TRIALS 2021 HAVE ENDED " + editionExecute.howManyFinishers() + " PLAYERS WON");
                clearEditionToExecute();
            } else {
                continueExecution = menuController.askUserYesNo();
            }
        }

        if (!editionExecute.isAnyoneAlive()){
            System.out.println("\nTHE TRIALS 2021 HAVE ENDED IN FAILURE - 0 PLAYERS ENDED");
            clearEditionToExecute();
        }

        if (continueExecution.equals("no")){
            executionDAO.saveExecutionToFile(editionExecute);
        }
    }

    /**
     * Mètode que s'encarrega de veure si els jugadors pujen de nivell o no després de cada trial
     * @param edition Se li passa la edició que s'esta executant per extreure informació d'ella
     */
    private void updatePlayerEvolution(Edition edition) {

        for (int i = 0; i < edition.getNumPlayers(); i++) {

            Player auxPlayer = edition.getPlayerList().get(i);

            if (auxPlayer.getInvestigationPoints()>=10) {

                switch (edition.getPlayerList().get(i).getLevel()) {
                    case 1 -> {
                        MasterPlayer mp = new MasterPlayer(auxPlayer.getName(), 2, 5);
                        edition.getPlayerList().set(i,mp);  //intercambia el objeto de ingeniero por uno tipo master en la posicion de i
                        System.out.println(mp.getName() + " is now a master (with 5 PI).");
                    }
                    case 2 -> {
                        DoctorPlayer dp = new DoctorPlayer(auxPlayer.getName(), 3, 5);
                        edition.getPlayerList().set(i,dp);
                        System.out.println(dp.getName() + " is now a doctor (with 5 PI).");
                    }
                    case 3 -> {
                    }
                    default -> System.out.println("Error en update player evolution. Switch value es: " + edition.getPlayerList().get(i).getLevel());
                }
            }
        }
    }

    /**
     * Mètode que sotmet al jugador a la trial de tipos paper publication
     * @param edition Objecte amb informació de la edició
     * @param playerID Identificador del jugador dintre de la llista de jugadors
     * @param trialID Identificació de la trial dintre de la llista de trials
     */
    private void submitPlayerInPaperPublicationTrial(Edition edition, int playerID, int trialID){

        PaperPublicationTrial paperPublicationTrial = (PaperPublicationTrial) edition.getEditionsTrialsList().get(trialID);

        int acc = paperPublicationTrial.getAcceptanceProbability();
        int rev = paperPublicationTrial.getRevisionProbability();
        int quartile = paperPublicationTrial.getQuartile();
        String result;

        switch (edition.getPlayerList().get(playerID).getLevel()){
            case 1 -> System.out.print("    " + edition.getPlayerList().get(playerID).getName() + " is submitting... ");
            case 2 -> System.out.print("    Master " + edition.getPlayerList().get(playerID).getName() + " is submitting... ");
            case 3 -> System.out.print("    " + edition.getPlayerList().get(playerID).getName() + ", PhD" + " is submitting... ");
            default -> System.out.println("Error: The player level is: " + edition.getPlayerList().get(playerID).getLevel());
        }

        do{
            result = getRandomDecisionPaperPublication(acc,rev);

            switch (result) {
                case "Accepted" -> {
                    edition.getPlayerList().get(playerID).addPoints(getRewardOrPenaltyPaperPublication(quartile,"+"),1);
                    System.out.println("Accepted! PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
                }
                case "Revisions" -> System.out.print("Revisions... ");
                case "Rejected" -> {
                    edition.getPlayerList().get(playerID).addPoints(getRewardOrPenaltyPaperPublication(quartile,"-"),1);
                    if (edition.getPlayerList().get(playerID).getInvestigationPoints() <= 0) {
                        edition.getPlayerList().get(playerID).setInvestigationPoints(0);
                        System.out.println("Rejected. PI count: 0 - Disqualified!");
                    } else {
                        System.out.println("Rejected. PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
                    }
                }
                default -> System.out.println("ERROR Submit player paper publication trial. Value of getRandomDecisionPaperPublication is: " + result);
            }
        } while (result.equals("Revisions"));

    }

    /**
     * Mètode que sotmet al jugador a la trial de tipos master studies
     * @param edition Objecte amb informació de la edició
     * @param playerID Identificador del jugador dintre de la llista de jugadors
     * @param trialID Identificació de la trial dintre de la llista de trials
     */
    private void submitPlayerInMasterStudiesTrial(Edition edition, int playerID, int trialID){

        MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) edition.getEditionsTrialsList().get(trialID);

        int numCredits = masterStudiesTrial.getCreditNum(), passProbability = masterStudiesTrial.getPassProbability(), creditsPassed;

        switch (edition.getPlayerList().get(playerID).getLevel()){
            case 1 -> System.out.print(edition.getPlayerList().get(playerID).getName() + " ");
            case 2 -> System.out.print("Master " + edition.getPlayerList().get(playerID).getName() + " ");
            case 3 -> System.out.print(edition.getPlayerList().get(playerID).getName() + ", PhD ");
        }

        creditsPassed = willMasterStudiesPass(numCredits, passProbability);

        if (creditsPassed >= (numCredits/2)){
            edition.getPlayerList().get(playerID).addPoints(3,2);
            System.out.println("passed " + creditsPassed + "/" + numCredits + " ECTS. Congrats! PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());

        } else {

            if (edition.getPlayerList().get(playerID).getInvestigationPoints() <= 3) {
                edition.getPlayerList().get(playerID).setInvestigationPoints(0);
                System.out.println("passed " + creditsPassed + "/" + numCredits + " ECTS. Sorry... PI count: 0 - Disqualified!");
            } else {
                edition.getPlayerList().get(playerID).addPoints(-3,2);
                System.out.println("passed " + creditsPassed + "/" + numCredits + " ECTS. Sorry... PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
            }

        }
    }

    /**
     * Mètode que sotmet al jugador a la trial de tipos doctoral thesis
     * @param edition Objecte amb informació de la edició
     * @param playerID Identificador del jugador dintre de la llista de jugadors
     * @param trialID Identificador de la trial dintre de la llista de trials
     */
    private void submitPlayerInDoctoralThesis(Edition edition, int playerID, int trialID) {
        DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) edition.getEditionsTrialsList().get(trialID);

        double result=0;

        switch (edition.getPlayerList().get(playerID).getLevel()){
            case 1 -> System.out.print(edition.getPlayerList().get(playerID).getName() + " ");
            case 2 -> System.out.print("Master " + edition.getPlayerList().get(playerID).getName() + " ");
            case 3 -> System.out.print(edition.getPlayerList().get(playerID).getName() + ", PhD ");
        }

        for (int i = 0; i < doctoralThesisDefenseTrial.getDifficulty(); i++) {
            result = result + (2*(i+1) - 1);
        }
        result = Math.sqrt(result);


        if (edition.getPlayerList().get(playerID).getInvestigationPoints() >= result){
            edition.getPlayerList().get(playerID).addPoints(5,3);
            System.out.println("was successful. Congrats! Pi count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
        } else {
            if (edition.getPlayerList().get(playerID).getInvestigationPoints() <= 5) {
                edition.getPlayerList().get(playerID).setInvestigationPoints(0);
                System.out.println("was unsuccessful. Sorry... Pi count: 0 - Disqualified!");
            } else {
                edition.getPlayerList().get(playerID).addPoints(-5, 3);
                System.out.println("was unsuccessful. Sorry... Pi count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
            }
        }
        System.out.println("Result of puntuation: " + result);

    }

    /**
     * Mètode que sotmet a tots els jugadors a la trial de tipos budget request
     * @param edition Objecte amb l'informació de la edició
     * @param trialID Identificador de la trial dintre de la llista de trials
     */
    private void submitTeamInBudgetRequest(Edition edition, int trialID){

        BudgetRequestTrial budgetRequestTrial = (BudgetRequestTrial) edition.getEditionsTrialsList().get(trialID);

        int suma = 0;

        for (int i = 0; i < edition.getNumPlayers(); i++) {
            suma = suma + edition.getPlayerList().get(i).getInvestigationPoints();
        }

        if (suma >= Math.log(budgetRequestTrial.getMoneyAmount())/Math.log(2)){
            System.out.println("The research group got the budget!");
            for (int i = 0; i < edition.getPlayerList().size(); i++) {
                if (edition.getPlayerList().get(i).isAlive()){
                    switch (edition.getPlayerList().get(i).getLevel()){
                        case 1 -> System.out.print(edition.getPlayerList().get(i).getName());
                        case 2 -> System.out.print("Master " + edition.getPlayerList().get(i).getName());
                        case 3 -> System.out.print(edition.getPlayerList().get(i).getName() + ", PhD");
                    }
                    //edition.getPlayerList().get(i).setTrialPass(true);
                    edition.getPlayerList().get(i).setInvestigationPoints(edition.getPlayerList().get(i).getInvestigationPoints() + getRewardBudgetRequest(edition, i));
                    System.out.println(". PI count: " + edition.getPlayerList().get(i).getInvestigationPoints());
                }
            }
        }else{
            for (int i = 0; i < edition.getNumPlayers(); i++) {
                System.out.println("\nThe research group didn't get the budget!");
                edition.getPlayerList().get(i).setInvestigationPoints(edition.getPlayerList().get(i).getInvestigationPoints() - 2);
                System.out.println(". PI count: " + edition.getPlayerList().get(i).getInvestigationPoints());
            }
        }
    }

    /**
     * Mètode que genera un numer aleatori i mira quin resultat ha obtingut l'usuari dintre de les provabilitat
     * @param acc Valor de acceptació
     * @param rev Valor de revisió
     * @return Retorna una string amb el resultat (accepted, revisions, rejected)
     */
    private String getRandomDecisionPaperPublication(int acc, int rev){
        int decision=0;
        Random rand = new Random();

        decision = rand.nextInt(100);

        if (decision <=acc){
            return "Accepted";
        } else if (decision <= (acc + rev)){
            return "Revisions";
        } else if (decision > (acc + rev)){
            return "Rejected";
        } else {
            System.out.println("value of acceptance is: " + acc + " and value of revision is: " + rev);
            return String.valueOf(decision);
        }
    }

    /**
     * Mètode que calcula la puntuació que treu l'usuari depenent el "quartile" de la prova
     * @param quartile Valor de la prova per treure la puntuació
     * @param sumres Identificador per saber si la puntuació es suma o resta depenent de si ha passat l'usuari o no la prova
     * @return Retorna un valor de tipos integer amb la puntuació que li toca
     */
    private int getRewardOrPenaltyPaperPublication(int quartile, String sumres){
        //sumres es una variable con caracter + o - para saber si le sumamos o restamos para apovechar un mismo metodo para obtener puntuacion de victoria y derrota

        if (sumres.equals("+")) {
            return switch (quartile) {
                case 1 -> 4;
                case 2 -> 3;
                case 3 -> 2;
                case 4 -> 1;
                default -> -1;
            };
        } else if (sumres.equals("-")) {
            return switch (quartile) {
                case 1 -> -5;
                case 2 -> -4;
                case 3 -> -3;
                case 4 -> -2;
                default -> -1;
            };
        } else {
            return -1;
        }
    }

    /**
     * Mètode que calcula de manera aleatòria la quantitat de crèdits que pasa l'usuari
     * @param numCredits Número de crèdits que ha de cursar l'usuari
     * @param passProbability Dificultat de passar el crèdit
     * @return Retorna la quantitat de crèdits que ha passat l'usuari
     */
    private int willMasterStudiesPass(int numCredits, int passProbability) {
        Random rand = new Random();
        int pass, creditsPassed=0;

        for (int i = 0; i < numCredits; i++) {
            pass = rand.nextInt(100);
            //System.out.println("Pass[" + i + "] = " + pass);
            if (pass<passProbability){
                creditsPassed++;
                //System.out.println("Total credit passed = " + creditsPassed);
            }
        }

        return creditsPassed;
    }

    /**
     * Mètode que calcula si el grup ha obtingut el pressupost
     * @param edition Objecte amb l'informació de l'edició
     * @param playerID Identificador del jugador dintre de la llista de jugadors
     * @return Retorna la puntuació que li pertoca al jugador
     */
    private int getRewardBudgetRequest(Edition edition, int playerID) {

        int playerScore = edition.getPlayerList().get(playerID).getInvestigationPoints();

        if (Math.floorMod(playerScore,2)>0){
            return ((playerScore + 1)/2);
        } else {
            return (playerScore/2);
        }

    }

    /**
     * Mètode que demana a l'usuari una string que no sigui vuida
     * @param text Text a printar per pantalla
     * @return Retorna la string introduida per l'usuari
     */
    private String askUserNonEmptyString(String text){

        Scanner scanner = new Scanner(System.in);
        System.out.print(text );

        return scanner.next();
    }

    private void clearEditionToExecute(){
        editionExecute.setEditionYear(0);
        editionExecute.setNumTrials(0);
        editionExecute.setEditionsTrialsList(null);
        editionExecute.setNumPlayers(0);
        editionExecute.setPlayerList(null);
        editionExecute.setTrialExecuting(0);
    }


    public void saveExecutionsListToFile() {
        executionDAO.saveExecutionToFile(this.editionExecute);
    }

    public boolean isThisYearsEditionExecuting() {
        editionExecute.toCSV();
        if (this.editionExecute.getNumPlayers() == 0){
            return false;
        } else {
            return true;
        }
    }


}



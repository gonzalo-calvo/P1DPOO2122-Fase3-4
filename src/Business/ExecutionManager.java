package Business;

import java.util.Locale;
import java.util.Random;
import java.util.Scanner;

public class ExecutionManager {

    public ExecutionManager() {
    }

    public void executeEdition(Edition editionToExecute){

        String continueExecution = "yes";

        System.out.println("\n--- The Trials " + editionToExecute.getEditionYear() + " ---\n");

        if (editionToExecute.getPlayerList().isEmpty()) {   //if empty --> first time executing
            for (int i = 0; i < editionToExecute.getNumPlayers(); i++) {
                Player auxPlayer = new Player();
                auxPlayer.setName(askUserNonEmptyString("Enter the player’s name (" + (i + 1) + "/" + editionToExecute.getNumPlayers() + "): "));
                auxPlayer.setInvestigationPoints(5);
                auxPlayer.setLevel(1);
                auxPlayer.setAlive(true);
                editionToExecute.getPlayerList().add(auxPlayer);
            }
        } else {
            for (int i = 0; i < editionToExecute.getNumPlayers(); i++) {
                printPlayer(editionToExecute.getPlayerList().get(i));
            }
        }


        while (editionToExecute.getNumTrials()>editionToExecute.getTrialExecuting() && continueExecution.equals("yes") && editionToExecute.isAnyoneAlive()){

            System.out.println("\nTrial #" + (editionToExecute.getTrialExecuting()+1) + " - " + editionToExecute.getEditionsTrialsList().get(editionToExecute.getTrialExecuting()).getName() + "\n");

            switch (editionToExecute.getEditionsTrialsList().get(editionToExecute.getTrialExecuting()).getType()){
                case 1:
                    for (int i = 0; i < editionToExecute.getNumPlayers(); i++) {
                        if (editionToExecute.getPlayerList().get(i).isAlive()){
                            submitPlayerInPaperPublicationTrial(editionToExecute, i, editionToExecute.getTrialExecuting());
                        }
                    }
                    break;

                case 2:
                    for (int i = 0; i < editionToExecute.getNumPlayers(); i++) {
                        if (editionToExecute.getPlayerList().get(i).isAlive()){
                            submitPlayerInMasterStudiesTrial(editionToExecute, i, editionToExecute.getTrialExecuting());
                        }
                    }
                    break;

                case 3:
                    for (int i = 0; i < editionToExecute.getNumPlayers(); i++) {
                        if (editionToExecute.getPlayerList().get(i).isAlive()){
                            submitPlayerInDoctoralThesis(editionToExecute, i, editionToExecute.getTrialExecuting());
                        }
                    }
                    break;

                case 4:
                    submitPlayersInBudgetRequest(editionToExecute, editionToExecute.getTrialExecuting());
                    break;

                default:
                    System.out.println("Error de switch escogiendo tipo de trial");

            }

            System.out.println("");
            for (int i = 0; i < editionToExecute.getPlayerList().size(); i++) {
                System.out.println("entra for");
                if (editionToExecute.getPlayerList().get(i).getLevel()<3){
                    updatePlayerEvolution(editionToExecute, editionToExecute.getTrialExecuting());
                }
            }

            editionToExecute.setTrialExecuting(editionToExecute.getTrialExecuting()+1);

            if ((editionToExecute.getNumTrials()==editionToExecute.getTrialExecuting())) {
                System.out.println("\nTHE TRIALS 2021 HAVE ENDED " + editionToExecute.howManyFinishers() + " PLAYERS WON");

            } else {
                continueExecution = askUserYesNo();
            }
        }

        if (!editionToExecute.isAnyoneAlive()){
            System.out.println("\nTHE TRIALS 2021 HAVE ENDED IN FAILURE - 0 PLAYERS ENDED");
        }
    }





    private void updatePlayerEvolution(Edition edition, int trialID) {

        for (int i = 0; i < edition.getNumPlayers(); i++) {

            if (edition.getPlayerList().get(i).isTrialPass()) {

                if (edition.getEditionsTrialsList().get(trialID).getType() == 1  && edition.getPlayerList().get(i).getInvestigationPoints()>=10) {

                    switch (edition.getPlayerList().get(i).getLevel()) {
                        case 1 -> {
                            edition.getPlayerList().get(i).setLevel(2);
                            System.out.println(edition.getPlayerList().get(i).getName() + " is now a master (with 5 PI).");
                            edition.getPlayerList().get(i).setInvestigationPoints(5);
                        }
                        case 2 -> {
                            edition.getPlayerList().get(i).setLevel(3);
                            System.out.println(edition.getPlayerList().get(i).getName() + " is now a doctor (with 5 PI).");
                            edition.getPlayerList().get(i).setInvestigationPoints(5);
                        }
                        case 3 -> {
                        }
                        default -> System.out.println("No de seberia mostrar este mensaje");
                    }

                }   else if (edition.getEditionsTrialsList().get(trialID).getType() == 2){

                    switch (edition.getPlayerList().get(i).getLevel()) {
                        case 1 -> {
                            edition.getPlayerList().get(i).setLevel(2);
                            System.out.println(edition.getPlayerList().get(i).getName() + " is now a master (with 5 PI).");
                            edition.getPlayerList().get(i).setInvestigationPoints(5);
                        }
                        case 2 -> {
                            if (edition.getPlayerList().get(i).getInvestigationPoints()>=10) {
                                edition.getPlayerList().get(i).setLevel(3);
                                System.out.println(edition.getPlayerList().get(i).getName() + " is now a doctor (with 5 PI).");
                                edition.getPlayerList().get(i).setInvestigationPoints(5);
                            }
                        }
                        case 3 -> {
                        }
                        default -> System.out.println("No de seberia mostrar este mensaje");

                    }

                }   else if (edition.getEditionsTrialsList().get(trialID).getType() == 3){

                }   else if (edition.getEditionsTrialsList().get(trialID).getType() == 4){

                }

                edition.getPlayerList().get(i).setTrialPass(false);
            }
        }
    }

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
        }


        do{
            result = getRandomDecisionPaperPublication(acc,rev);

            switch (result) {
                case "Accepted" -> {
                    edition.getPlayerList().get(playerID).setInvestigationPoints(edition.getPlayerList().get(playerID).getInvestigationPoints() + getRewardPaperPublication(edition, quartile, playerID));
                    edition.getPlayerList().get(playerID).setTrialPass(true);
                    System.out.println("Accepted! PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
                }
                case "Revisions" -> System.out.print("Revisions... ");
                case "Rejected" -> {
                    edition.getPlayerList().get(playerID).setInvestigationPoints(edition.getPlayerList().get(playerID).getInvestigationPoints() - getPenalizationPaperPublication(edition, quartile, playerID));
                    if (edition.getPlayerList().get(playerID).getInvestigationPoints() <= 0) {
                        edition.getPlayerList().get(playerID).setAlive(false);
                        edition.getPlayerList().get(playerID).setInvestigationPoints(0);
                        System.out.println("Rejected. PI count: 0 - Disqualified!");
                    } else {
                        System.out.println("Rejected. PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
                    }
                }
                default -> System.out.println("ERROR Submit player paper publication trial");
            }

        } while (result.equals("Revisions"));

    }

    private void submitPlayerInMasterStudiesTrial(Edition edition, int playerID, int trialID){

        MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) edition.getEditionsTrialsList().get(trialID);

        int numCredits = masterStudiesTrial.getCreditNum();
        int passProbability = masterStudiesTrial.getPassProbability();
        int creditsPassed=0;

        switch (edition.getPlayerList().get(playerID).getLevel()){
            case 1 -> System.out.print(edition.getPlayerList().get(playerID).getName() + " ");
            case 2 -> System.out.print("Master " + edition.getPlayerList().get(playerID).getName() + " ");
            case 3 -> System.out.print(edition.getPlayerList().get(playerID).getName() + ", PhD ");
        }

        creditsPassed = willMasterStudiesPass(numCredits, passProbability);

        if (creditsPassed >= (numCredits/2)){
            //System.out.println("Credits passed value= " + creditsPassed);
            edition.getPlayerList().get(playerID).setTrialPass(true);
            edition.getPlayerList().get(playerID).setInvestigationPoints(edition.getPlayerList().get(playerID).getInvestigationPoints() + getRewardMasterStudies(edition, playerID));
            System.out.print("passed " + creditsPassed + "/" + numCredits + " ECTS. Congrats! PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());

        } else {
            edition.getPlayerList().get(playerID).setInvestigationPoints(edition.getPlayerList().get(playerID).getInvestigationPoints() - getPenalizationMasterStudies(edition,playerID));
            if (edition.getPlayerList().get(playerID).getInvestigationPoints() > 3) {
                System.out.println("passed " + creditsPassed + "/" + numCredits + " ECTS. Sorry... PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
            } else {
                System.out.println("passed " + creditsPassed + "/" + numCredits + " ECTS. Sorry... PI count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
                edition.getPlayerList().get(playerID).setAlive(false);
                edition.getPlayerList().get(playerID).setInvestigationPoints(0);
            }
        }
    }

    private void submitPlayerInDoctoralThesis(Edition edition, int playerID, int trialID) {
        DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) edition.getEditionsTrialsList().get(trialID);

        boolean pass = false;
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
        System.out.println("Result of puntuation: " + result);

        if (edition.getPlayerList().get(playerID).getInvestigationPoints() >= result){
            edition.getPlayerList().get(playerID).setTrialPass(true);
            edition.getPlayerList().get(playerID).setInvestigationPoints(edition.getPlayerList().get(playerID).getInvestigationPoints() + getRewardDoctoralThesis(edition, playerID));
            System.out.print("was successful. Congrats! Pi count: " + edition.getPlayerList().get(playerID).getInvestigationPoints());
        } else {
            edition.getPlayerList().get(playerID).setInvestigationPoints(edition.getPlayerList().get(playerID).getInvestigationPoints() - getPenalizationDoctoralThesis(edition, playerID));
            System.out.print("Rejected. PI count:  " + edition.getPlayerList().get(playerID).getInvestigationPoints());
        }







    }

    private void submitPlayersInBudgetRequest(Edition edition, int trialID){
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
                    edition.getPlayerList().get(i).setTrialPass(true);
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

    private int getRewardBudgetRequest(Edition edition, int playerID) {

        int playerScore = edition.getPlayerList().get(playerID).getInvestigationPoints();

        if (Math.floorMod(playerScore,2)>0){
            return ((playerScore + 1)/2);
        } else {
            return (playerScore/2);
        }

    }

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

    private String askUserNonEmptyString(String text){

        Scanner scanner = new Scanner(System.in);
        System.out.print(text );

        return scanner.next();
    }

    private void printPlayer(Player player){

        System.out.println("Player name: " + player.getName() + " , Player Investigation Points: " + player.getInvestigationPoints() + " , Is player alive? " + player.isAlive());

    }

    private String getRandomDecisionPaperPublication(int acc, int rev){
        int decision=0;
        Random rand = new Random();

        decision = rand.nextInt(100);

        if (decision <=acc){
            return "Accepted";
        } else if (decision < (acc + rev)){
            return "Revisions";
        } else if (decision > (acc + rev)){
            return "Rejected";
        } else {
            return "-1";
        }
    }

    private int getRewardPaperPublication(Edition edition, int quartile, int playerID){

        if (edition.getPlayerList().get(playerID).getLevel()==3) {
            return switch (quartile) {
                case 1 -> 8;
                case 2 -> 6;
                case 3 -> 4;
                case 4 -> 2;
                default -> -1;
            };
        } else {
            return switch (quartile) {
                case 1 -> 4;
                case 2 -> 3;
                case 3 -> 2;
                case 4 -> 1;
                default -> -1;
            };
        }
    }

    private int getPenalizationPaperPublication(Edition edition, int quartile, int playerID){

        if (edition.getPlayerList().get(playerID).getLevel()==1) {
            return switch (quartile) {
                case 1 -> 5;
                case 2 -> 4;
                case 3 -> 3;
                case 4 -> 2;
                default -> -1;
            };
        } else {
            return switch (quartile) {
                case 1, 2 -> 2;
                case 3, 4 -> 1;
                default -> -1;
            };
        }
    }

    private int getRewardMasterStudies(Edition edition, int playerID){

        return switch (edition.getPlayerList().get(playerID).getLevel()) {
            case 1,2 -> 3;
            case 3 -> 6;
            default -> -1;
        };

    }

    private int getPenalizationMasterStudies(Edition edition, int playerID){

        return switch (edition.getPlayerList().get(playerID).getLevel()) {
            case 1 -> 3;
            case 2,3 -> 1;
            default -> -1;
        };

    }

    private int getRewardDoctoralThesis(Edition edition, int playerID) {

        return switch (edition.getPlayerList().get(playerID).getLevel()) {
            case 1 -> 5;
            case 2,3 -> 10;
            default -> -1;
        };

    }

    private int getPenalizationDoctoralThesis(Edition edition, int playerID) {

        return switch (edition.getPlayerList().get(playerID).getLevel()) {
            case 1 -> 5;
            case 2,3 -> 10;
            default -> -1;
        };

    }

    private String  askUserYesNo(){
        boolean check = false;
        Scanner scanner = new Scanner(System.in);
        String input = null;
        while (!check) {

            System.out.print("\nContinue the execution? [yes/no]: ");

            try {
                input = scanner.nextLine().toLowerCase(Locale.ROOT);
                if (input.equals("yes")){
                    check=true;
                } else if (input.equals("no")){
                    check=true;
                } else {
                    System.out.println("The input must be 'yes' or 'no'");
                }
                
            } catch (Exception ex){
                System.out.println("The input must be 'yes' or 'no'");
                check = false;
            }
        }
        return input;
    }




}

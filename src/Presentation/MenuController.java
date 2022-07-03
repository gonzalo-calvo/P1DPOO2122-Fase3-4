package Presentation;

import Business.Editions.EditionsManager;
import Business.Execution.ExecutionManager;
import Business.Players.Player;
import Business.Trials.TrialsManager;
import Presentation.Views.MainView;



import java.util.Calendar;

/**
 * Classe menu controller gestiona tot el tema de moures pel menu
 */
public class MenuController {

    private MainView mainView;
    private TrialsManager trialsManager;
    private EditionsManager editionsManager;
    private ExecutionManager executionManager;

    public MenuController() {
    }

    public MenuController(MainView mainView) {
        this.mainView = mainView;
    }

    public void addManagersToMenuController(TrialsManager trialsManager, EditionsManager editionsManager, ExecutionManager executionManager){
        this.trialsManager = trialsManager;
        this.editionsManager = editionsManager;
        this.executionManager = executionManager;
    }


    /**
     * Aquest mètode s'encarrega de gestionar si l'usuari vol executar el programa com a conductor o compositor
     * Gestiona també si es vol treballar a l'execució amb fitxers JSON o CSV
     */
    public void start (){

        char role = mainView.getRole();

        if (role == 'A'){                   // COMPOSER
            int option;

            do {
                option = mainView.getManagementMode();

                if (option == 1) {          // MANAGE TRIALS
                    manageTrials();

                } else if (option == 2) {   // MANGE EDITIONS
                    manageEditions();
                }
            }while (option != 3);

            trialsManager.saveTrialsListToFile();
            editionsManager.saveEditionsListToFile();

        }else if (role == 'B'){             // CONDUCTOR
            boolean foundEdition = false;
            int editionId=0;
            int year = Calendar.getInstance().get(Calendar.YEAR);
            mainView.printSingleLine("Entering execution mode...");

            for (int i = 0; i < editionsManager.getEditionsList().size(); i++) {
                if (editionsManager.getEditionsList().get(i).getEditionYear() == year){
                    foundEdition = true;
                    editionId = i;
                }
            }

            if (executionManager.isThisYearsEditionExecuting()){
                executionManager.executeEdition();
            } else {
                if (foundEdition) {
                    executionManager.setEditionExecute(editionsManager.getEditionsList().get(editionId));
                    executionManager.executeEdition();
                } else {
                    mainView.printSingleLine("No edition is defined for the current year (" + year + ").");
                }
            }

            if (!executionManager.isExecutionEnded()){
                executionManager.saveExecutionToFile();
            } else {
                executionManager.clearEditionToExecute();
            }

        }



        mainView.printSingleLine("\nShutting down...");
    }

    /**
     * Manage trials s'encarrega del submenú per crear, llistar o esborrar trials
     */
    public void manageTrials(){

        int option;

        do {
            option = mainView.subManageTrials();

            if (option == 'a') {
                // Create trial
                trialsManager.createTrial();

            } else if (option == 'b') {
                // List trial
                trialsManager.listTrials();

            } else if (option == 'c') {
                // Delete trial
                trialsManager.deleteTrials();
            }

        } while (option != 'd');

    }

    /**
     * Manage editions s'encarrega del submenú per crear, llistar, duplicar o eliminar edicions
     */
    public void manageEditions(){

        int option2;

        do {
            option2 = mainView.subManageEditions();

            if (option2 == 'a') {
                // Create edition
                editionsManager.createEdition();

            } else if (option2 == 'b') {
                // List edition
                editionsManager.listEditions();
            } else if (option2 == 'c') {
                // Duplicate edition
                editionsManager.duplicateEditions();
            } else if (option2 == 'd'){
                // Delete edition
                editionsManager.deleteEdition();
            }

        } while (option2 != 'e');
    }

    public void printPlayer(Player player){
        mainView.printPlayer(player);
    }

    public String  askUserYesNo(){
        return mainView.askUserYesNo();
    }

    public int askUserOptionBetweenNumbers(String text, int min, int max){
        return mainView.askUserOptionBetweenNumbers(text, min, max);
    }

    public void printSingleLine (String line){
        mainView.printSingleLine(line);
    }

    public String scanLine() {
        return mainView.scanLine();
    }
}

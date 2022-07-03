package Business.Editions;

import Business.Trials.*;
import Persistence.DAOs.EditionsDAO;
import Presentation.MenuController;
import Presentation.Views.EditionView;

import java.util.ArrayList;

/**
 * Aquesta classe s'encarrega de gestionar les edicions
 */
public class EditionsManager {

    private EditionsDAO editionsDAO;
    private MenuController menuController;
    private EditionView editionView;

    private ArrayList<Trial> trialsList;
    private ArrayList<Edition> editionsList = new ArrayList<>();


    public EditionsManager(ArrayList<Trial> trialsList) {
        this.trialsList = trialsList;
    }

    public EditionsManager(EditionsDAO editionsDAO, MenuController menuController, EditionView editionView) {
        this.editionsDAO = editionsDAO;
        this.menuController = menuController;
        this.editionView = editionView;
        this.editionsList = editionsDAO.getEditionsListFromFile();
    }

    public EditionsManager(){

    }

    public ArrayList<Trial> getTrialsList() {
        return trialsList;
    }

    public void setTrialsList(ArrayList<Trial> trialsList) {
        this.trialsList = trialsList;
    }

    public ArrayList<Edition> getEditionsList() {
        return editionsList;
    }

    public void setEditionsList(ArrayList<Edition> editionsList) {
        this.editionsList = editionsList;
    }


    /**
     * Aquest mètode s'encarrega de crear una edició nova demanant dades a l'usuari
     */
    public void createEdition() {

        Edition edition = new Edition();
        ArrayList<Trial> auxTrial = new ArrayList<>();
        int year, num_players, num_trials;
        boolean error = false;

        do {
            year = menuController.askUserOptionBetweenNumbers("\nEnter the edition’s year: " , 2022, 999999999);
            for (Edition value : editionsList) {
                if (value.getEditionYear() == year) {
                    menuController.printSingleLine("This edition already exists!");
                    error = true;
                } else {
                    error = false;
                }
            }
        }while (error);
        edition.setEditionYear(year);

        num_players = menuController.askUserOptionBetweenNumbers("Enter the initial number of players [1-5]: ", 1, 5);
        edition.setNumPlayers(num_players);

        num_trials = menuController.askUserOptionBetweenNumbers("Enter the number of trials [3-12]: ", 3, 12);
        edition.setNumTrials(num_trials);

        menuController.printSingleLine("\n     --- Trials ---\n");

        for (int i = 0; i < trialsList.size(); i++) {
            menuController.printSingleLine((i+1) + ") " + trialsList.get(i).getName());
        }

        for (int i = 0; i < num_trials; i++) {
            int trial = menuController.askUserOptionBetweenNumbers("Pick a trial (" + (i+1) + "/" + num_trials + "): " , 1, trialsList.size())-1;

            if (trialsList.get(trial).getType() == 1) {
                PaperPublicationTrial paperPublicationTrial = (PaperPublicationTrial) trialsList.get(trial);
                auxTrial.add(paperPublicationTrial);
            } else if (trialsList.get(trial).getType() == 2) {
                MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) trialsList.get(trial);
                auxTrial.add(masterStudiesTrial);
            } else if (trialsList.get(trial).getType() == 3) {
                DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) trialsList.get(trial);
                auxTrial.add(doctoralThesisDefenseTrial);
            } else if (trialsList.get(trial).getType() == 4) {
                BudgetRequestTrial budgetRequestTrial = (BudgetRequestTrial) trialsList.get(trial);
                auxTrial.add(budgetRequestTrial);
            }
        }
        edition.setEditionsTrialsList(auxTrial);

        menuController.printSingleLine("\nThe edition was created successfully!");
        editionsList.add(edition);

    }

    /**
     * Aquest mètode s'encarrega de printar la llista d'edicions
     */
    public void listEditions() {

        int option;
        
            if (editionsList.size()>0) {
                menuController.printSingleLine("Here are the current editions, do you want to see more details or go back?");
                editionView.printEditions(editionsList);
                option = menuController.askUserOptionBetweenNumbers("Enter an option: ", 1, editionsList.size()+1) - 1;
                if (editionsList.size()>option) {
                    editionView.printDetails(editionsList.get(option));

                }
            } else {
                menuController.printSingleLine("\nNo editions in the list");
            }
        }

    /**
     * Aques mètode s'encarrega de duplicar una edició canviant any i numero de jugadors
     */
    public void duplicateEditions() {
        int option, yearToClone, numPlayersToClone;
        menuController.printSingleLine("\nWhich edition do you want to clone?");

        editionView.printEditions(editionsList);

        option = menuController.askUserOptionBetweenNumbers("Enter an option: ", 1, editionsList.size()+1) - 1;

        if (editionsList.get(option).getTrialExecuting()==0) {
            yearToClone = menuController.askUserOptionBetweenNumbers("\nEnter the new edition’s year: ", 1, 9999999);
            numPlayersToClone = menuController.askUserOptionBetweenNumbers("Enter the new edition’s initial number of players [1-5]: ", 1, 5);

            cloneEdition(editionsList.get(option), yearToClone, numPlayersToClone);
            menuController.printSingleLine("The edition was cloned successfully!");
        } else {
            menuController.printSingleLine("This Edition has already been started and can't be duplicated");
        }

    }

    /**
     * Aquest mètode s'encarrega d'esborrar una edició
     */
    public void deleteEdition(){
        int option, confirmationYear;
        menuController.printSingleLine("\nWhich edition do you want to delete?");

        editionView.printEditions(editionsList);

        option = menuController.askUserOptionBetweenNumbers("Enter an option: ", 1, editionsList.size()+1) - 1;
        confirmationYear = menuController.askUserOptionBetweenNumbers("\nEnter the edition’s year for confirmation: ", 1, 9999999);

        if (confirmationYear == editionsList.get(option).getEditionYear()){
            editionsList.remove(option);
            editionView.printEditions(editionsList);
            menuController.printSingleLine("The edition was successfully deleted.");
        } else {
            menuController.printSingleLine("The edition has not been deleted.");
        }

    }

    /**
     * Aquest mètode s'encarrega de clonar una edició
     * @param edition Paràmetre amb l'edició que es vol clonar
     * @param year Any de l'edició nova
     * @param numPlayers Numero de jugadors que es vol introduir a la nova edició
     */
    public void cloneEdition(Edition edition, int year, int numPlayers){
        ArrayList<Trial> trialsClone = new ArrayList<>(edition.getEditionsTrialsList());

        Edition editionClone = new Edition(year, numPlayers, edition.getNumTrials(), trialsClone, new ArrayList<>(),0);
        editionsList.add(editionClone);

    }

    public void saveEditionsListToFile() {
        editionsDAO.saveEditionsListToFile(this.editionsList);
    }
}

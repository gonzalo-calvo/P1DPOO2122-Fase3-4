package Business.Trials;

import Persistence.DAOs.TrialsDAO;
import Presentation.MenuController;
import Presentation.Views.TrialView;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * La classe trialsmanager es l'encarregada de gestionar la creacio, llistat i eliminacio de trials
 */
public class TrialsManager {

    private TrialsDAO trialsDAO;
    private MenuController menuController;
    private TrialView trialView;

    private ArrayList<Trial> trialList;


    public TrialsManager(TrialsDAO trialsDAO, MenuController menuController, TrialView trialView) {
        this.trialList = new ArrayList<>();
        this.trialsDAO = trialsDAO;
        this.menuController = menuController;
        this.trialView = trialView;
        this.trialList = trialsDAO.getTrialsListFromFile();
    }


    public ArrayList<Trial> getTrialList() {
        return trialList;
    }

    public void setTrialList(ArrayList<Trial> trialList) {
        this.trialList = trialList;
    }


    /**
     * Aquest mètode anirà demanarà a l'usuari el tipos de trial que es vol crear i trucarà a un altre mètode per omplir dades específiques d'aquell tipos de trial
     */
    public void createTrial(){
        int trialType;

        trialView.printCreateTrialMenu();
        trialType = menuController.askUserOptionBetweenNumbers("Enter the trial’s type: ", 1, 4);

        switch (trialType) {
            case 1 -> trialList.add(fillNewPaperSubmissionTrial());
            case 2 -> trialList.add(fillNewMasterStudiesTrial());
            case 3 -> trialList.add(fillNewDoctoralThesisDefenseTrial());
            case 4 -> trialList.add(fillNewBudgetRequestTrial());
        }

        trialView.printSingleLine("\nThe trial was created successfully!");
    }

    /**
     * Aquest mètode llista totes les trials de la llista de trials
     */
    public void listTrials (){

        int option;

        if (trialList.isEmpty()){
            menuController.printSingleLine("\nThere are no trials currently.");
        }else {
            menuController.printSingleLine("\nHere are the current trials, do you want to see more details or go back?\n");

            trialView.printTrialsList(trialList);

            option = menuController.askUserOptionBetweenNumbers("Enter an option: ", 1, trialList.size() + 1);

            if (option >= 1 && option <= trialList.size()){

                option = option - 1;
                if (trialList.get(option).getType() == 1) {
                    PaperPublicationTrial paperPublicationTrial = (PaperPublicationTrial) trialList.get(option);
                    trialView.printPaperPublication(paperPublicationTrial);
                } else if (trialList.get(option).getType() == 2) {
                    MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) trialList.get(option);
                    trialView.printMasterStudies(masterStudiesTrial);
                } else if (trialList.get(option).getType() == 3) {
                    DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) trialList.get(option);
                    trialView.printDoctoralThesisDefense(doctoralThesisDefenseTrial);
                } else if (trialList.get(option).getType() == 4) {
                    BudgetRequestTrial budgetRequestTrial = (BudgetRequestTrial) trialList.get(option);
                    trialView.printBudgetRequest(budgetRequestTrial);
                }
            }
        }
    }

    /**
     * Aquest mètode demanarà a l'usuari una trial i l'eliminarà de la llista de trials
     */
    public void deleteTrials () {
        int option;
        String trial_deleted;

        if (trialList.isEmpty()) {
            menuController.printSingleLine("\nThere are no trials currently.");
        } else {
            menuController.printSingleLine("\nWhich trial do you want to delete?\n");
            trialView.printTrialsList(trialList);

            option = menuController.askUserOptionBetweenNumbers("Enter an option: ", 1, trialList.size() + 1);
            if (option >= 1 && option <= trialList.size()) {
                menuController.printSingleLine("Enter the trial’s name for confirmation: ");
                trial_deleted = menuController.scanLine();
                if (trial_deleted.equals(trialList.get(option-1).getName())){
                    trialList.remove(option-1);
                    menuController.printSingleLine("The trial was successfully deleted.\n");
                }else{
                    menuController.printSingleLine("The trial wasn't deleted, try again.\n");
                }
            }
        }
    }

    /**
     * Aquest mètode demanarà a l'usuari totes les dades necessaries i especifiques per omplir la trial de tipus paper publication
     * @return Retorna un objecte de tipos paper publication per ser afegit a la llista de trials
     */
    private PaperPublicationTrial fillNewPaperSubmissionTrial(){

        PaperPublicationTrial paperPublicationTrial = new PaperPublicationTrial();

        boolean trial_exists, validate;
        String trial_name, journal_name, quartile;
        int acceptance, revision, rejection;

        //SET TYPE
        paperPublicationTrial.setType(1);

        //TRIAL NAME
        do {
            trial_exists = false;
            menuController.printSingleLine("Enter the trial's name: ");
            trial_name = menuController.scanLine();
            if (compareIfTrialInTrialList(trial_name)){
                trial_exists=true;
            }
        }while (trial_exists);
        paperPublicationTrial.setName(trial_name);

        // JOURNAL NAME
        do {
            menuController.printSingleLine("Enter the journal’s name: ");
            journal_name = menuController.scanLine();
            if (journal_name == null || journal_name.isEmpty()) {
                menuController.printSingleLine("\nJournal's name is wrong, try again.\n");
            }
        }while(journal_name == null || journal_name.isEmpty());
        paperPublicationTrial.setJournalName(journal_name);

        // QUARTILE
        do {
            validate=false;
            menuController.printSingleLine("Enter the journal’s quartile: ");
            quartile = menuController.scanLine();

            if (quartile.equals("Q1") || quartile.equals("Q2") || quartile.equals("Q3") || quartile.equals("Q4")){
                switch (quartile) {
                    case "Q1" -> paperPublicationTrial.setQuartile(1);
                    case "Q2" -> paperPublicationTrial.setQuartile(2);
                    case "Q3" -> paperPublicationTrial.setQuartile(3);
                    case "Q4" -> paperPublicationTrial.setQuartile(4);
                }
                validate=true;
            } else  {
                menuController.printSingleLine("\nWrong quartile, try again.\n");
            }

        }while (validate);

        // PERCENTAGES
        do {
            acceptance = menuController.askUserOptionBetweenNumbers("1/3) Enter the acceptance probability [0-100]: ", 0, 100);
            revision = menuController.askUserOptionBetweenNumbers("2/3) Enter the revision probability [0-100]: ", 0, 100);
            rejection = menuController.askUserOptionBetweenNumbers("3/3) Enter the rejection probability [0-100]: ", 0, 100);

            if (acceptance + revision + rejection != 100){
                menuController.printSingleLine("\nThe percentages aren't correct, try again.\n");
            }

        } while (acceptance + revision + rejection != 100);
        paperPublicationTrial.setAcceptanceProbability(acceptance);
        paperPublicationTrial.setRevisionProbability(revision);
        paperPublicationTrial.setRejectionProbability(rejection);

        return paperPublicationTrial;
    }

    /**
     * Aquest mètode demanarà a l'usuari totes les dades necessaries i especifiques per omplir la trial de tipus master studies
     * @return Retorna un objecte de tipus master studies per ser afegit a la llista de trials
     */
    private MasterStudiesTrial fillNewMasterStudiesTrial() {

        MasterStudiesTrial masterStudiesTrial = new MasterStudiesTrial();

        String masterName;

        //SET TYPE
        masterStudiesTrial.setType(2);

        //TRIAL NAME
        boolean trial_exists;
        String trial_name;

        do {
            trial_exists = false;
            menuController.printSingleLine("\nEnter the trial's name: ");
            trial_name = menuController.scanLine();
            if (compareIfTrialInTrialList(trial_name)){
                trial_exists=true;
            }
        }while (trial_exists);
        masterStudiesTrial.setName(trial_name);

        //MASTER NAME
        do {
            menuController.printSingleLine("Enter the master's name: ");
            masterName = menuController.scanLine();
            if (masterName == null || masterName.isEmpty()) {
                menuController.printSingleLine("\nJournal's name is wrong, try again.\n");
            }

        }while (masterName == null || masterName.isEmpty());
        masterStudiesTrial.setMasterName(masterName);

        //ECTS NUMBER
        masterStudiesTrial.setCreditNum(menuController.askUserOptionBetweenNumbers("Enter the Master's ECTS number [60-120]: ", 60, 120));

        //CREDIT PASS PROBABILITY
        masterStudiesTrial.setPassProbability(menuController.askUserOptionBetweenNumbers("Enter the credit pass probability [0-100%]: ", 0,100));

        return masterStudiesTrial;

    }

    /**
     * Aquest mètode demanarà a l'usuari totes les dades necessaries i especifiques per omplir la trial de tipus doctoral thesis
     * @return Retorna un objecte de tipos doctoral thesis per ser afegit a la llista de trials
     */
    private DoctoralThesisDefenseTrial fillNewDoctoralThesisDefenseTrial() {
        DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = new DoctoralThesisDefenseTrial();

        boolean trial_exists;
        String trial_name, journal_name;

        //SET TYPE
        doctoralThesisDefenseTrial.setType(3);

        //FIELD OF STUDY
        do {
            trial_exists = false;
            menuController.printSingleLine("\nEnter the trial's name: ");
            trial_name = menuController.scanLine();
            if (compareIfTrialInTrialList(trial_name)){
                menuController.printSingleLine("The trial's name already exists, introduce a different one");
                trial_exists=true;
            }
        }while (trial_exists);
        doctoralThesisDefenseTrial.setName(trial_name);

        // JOURNAL NAME
        do {
            menuController.printSingleLine("Enter the thesis field of study: ");
            journal_name = menuController.scanLine();
            if (journal_name == null || journal_name.isEmpty()) {
                menuController.printSingleLine("\nJournal's name is wrong, try again.\n");
            }
        }while(journal_name == null || journal_name.isEmpty());
        doctoralThesisDefenseTrial.setFieldOfStudy(journal_name);

        //DEFENSE DIFFICULTY
        doctoralThesisDefenseTrial.setDifficulty(menuController.askUserOptionBetweenNumbers("Enter the defense difficulty [1-10]: " , 1,10));

        return doctoralThesisDefenseTrial;
    }

    /**
     * Aquest mètode demanarà a l'usuari totes les dades necessaries i especifiques per omplir la trial de tipus budget request
     * @return Retorna un objecte de tipos budget request per ser afegit a la llista de trials
     */
    private BudgetRequestTrial fillNewBudgetRequestTrial() {
        BudgetRequestTrial budgetRequestTrial = new BudgetRequestTrial();

        boolean trial_exists;
        String trial_name, journal_name;

        //SET TYPE
        budgetRequestTrial.setType(4);

        //FIELD OF STUDY
        do {
            trial_exists = false;
            menuController.printSingleLine("\nEnter the trial's name: ");
            trial_name = menuController.scanLine();
            if (compareIfTrialInTrialList(trial_name)){
                trial_exists=true;
                menuController.printSingleLine("\nTrial name already exists.\n");
            }
        }while (trial_exists || trial_name == null || trial_name.isEmpty());
        budgetRequestTrial.setName(trial_name);

        //NAME OF ENTITY
        do {
            menuController.printSingleLine("Enter the entity’s name: ");
            journal_name = menuController.scanLine();
            if (journal_name == null || journal_name.isEmpty()) {
                menuController.printSingleLine("\nName of entity is wrong, try again.\n");
            }
        }while(journal_name == null || journal_name.isEmpty());
        budgetRequestTrial.setEntityName(journal_name);

        //AMOUNT OF MONEY
        budgetRequestTrial.setMoneyAmount(menuController.askUserOptionBetweenNumbers("Enter the budget amount: " , 1000,2000000000));

        return budgetRequestTrial;
    }

    /**
     * Aquest mètode agafa la llista de trials i compara si el nom de la nova trial existeix ja a la llista de trials
     * @param trialName Es el nom de la trial que es vol comparar si està utilitzada o no
     * @return Retorna valor en booleà segons si existeix ja el nom o no
     */
    public boolean compareIfTrialInTrialList(String trialName){

        for (Trial trial : trialList) {
            if (trial.getType() == 1) {
                PaperPublicationTrial paperPublicationTrial = (PaperPublicationTrial) trial;
                if (paperPublicationTrial.getName().equals(trialName)){
                    return true;
                }
            } else if (trial.getType() == 2) {
                MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) trial;
                if (masterStudiesTrial.getName().equals(trialName)){
                    return true;
                }
            } else if (trial.getType() == 3) {
                DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) trial;
                if (doctoralThesisDefenseTrial.getName().equals(trialName)){
                    return true;
                }
            } else if (trial.getType() == 4) {
                BudgetRequestTrial budgetRequestTrial = (BudgetRequestTrial) trial;
                if (budgetRequestTrial.getName().equals(trialName)){
                    return true;
                }
            }
        }

        return false;
    }

    public void saveTrialsListToFile() {
        trialsDAO.saveTrialsListToFile(this.trialList);
    }
}

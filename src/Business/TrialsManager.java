package Business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TrialsManager {

    private ArrayList<Trial> trialList;

    File file = new File("TrialList.csv");

    public TrialsManager() {
        this.trialList = new ArrayList<>();
        //trials.add(new Trial("Submitting to OOPD", "Observatory Of Programming Developments", 2, 20, 50, 30));
        //trials.add(new Trial("Publishing to APDS", "Observatory Of Programming Developments", 4, 40, 40, 20));
        //trialList.add(new Trial("Submitting to LSJournal", "Observatory Of Programming Developments", 3, 35, 25, 40));


    }

    public ArrayList<Trial> getTrialList() {
        return trialList;
    }

    public void setTrialList(ArrayList<Trial> trialList) {
        this.trialList = trialList;
    }

    public void createTrial(){

        int trialType;


        System.out.println("    --- Trial types ---\n");
        System.out.println("    1) Paper publication");
        System.out.println("    2) Master studies");
        System.out.println("    3) Doctoral thesis defense");
        System.out.println("    4) Budget request\n");

        trialType = askUserOptionBetweenNumbers("Enter the trial’s type: " ,1,4);

        switch (trialType) {
            case 1 -> trialList.add(fillNewPaperSubmissionTrial());
            case 2 -> trialList.add(fillNewMasterStudiesTrial());
            case 3 -> trialList.add(fillNewDoctoralThesisDefenseTrial());
            case 4 -> trialList.add(fillNewBudgetRequestTrial());
            default -> System.out.println("The option is not valid");
        }

        System.out.println("The trial was created successfully!");

    }

    public boolean compareIfTrialInTrialList(String trialName){

            for (Trial trial : trialList) {
                if (trial.getType() == 1) {
                    PaperPublicationTrial paperPublicationTrial = (PaperPublicationTrial) trial;
                    if (paperPublicationTrial.name.equals(trialName)){
                        return true;
                    }
                } else if (trial.getType() == 2) {
                    MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) trial;
                    if (masterStudiesTrial.name.equals(trialName)){
                        return true;
                    }
                } else if (trial.getType() == 3) {
                    DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) trial;
                    if (doctoralThesisDefenseTrial.name.equals(trialName)){
                        return true;
                    }
                } else if (trial.getType() == 4) {
                    BudgetRequestTrial budgetRequestTrial = (BudgetRequestTrial) trial;
                    if (budgetRequestTrial.name.equals(trialName)){
                        return true;
                    }
                }
            }

        return false;
    }

    private Trial fillNewBudgetRequestTrial() {
        BudgetRequestTrial budgetRequestTrial = new BudgetRequestTrial();

        Scanner scanner = new Scanner(System.in);
        boolean trial_exists;
        String trial_name, journal_name;

        //SET TYPE
        budgetRequestTrial.setType(4);

        //FIELD OF STUDY
        do {
            trial_exists = false;
            System.out.print("Enter the trial's name: ");
            trial_name = scanner.nextLine();
            if (compareIfTrialInTrialList(trial_name)){
                trial_exists=true;
                System.out.println("\nTrial name already exists.\n");
            }
        }while (trial_exists || trial_name == null || trial_name.isEmpty());
        budgetRequestTrial.setName(trial_name);


        //NAME OF ENTITY
        do {
            System.out.print("Enter the entity’s name: ");
            journal_name = scanner.nextLine();
            if (journal_name == null || journal_name.isEmpty()) {
                System.out.println("\nName of entity is wrong, try again.\n");
            }
        }while(journal_name == null || journal_name.isEmpty());
        budgetRequestTrial.setEntityName(journal_name);

        //AMOUNT OF MONEY
        budgetRequestTrial.setMoneyAmount(askUserOptionBetweenNumbers("Enter the budget amount: " , 1000,2000000000));

        return budgetRequestTrial;
    }

    private Trial fillNewDoctoralThesisDefenseTrial() {
        DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = new DoctoralThesisDefenseTrial();

        Scanner scanner = new Scanner(System.in);
        boolean trial_exists;
        String trial_name, journal_name;

        //SET TYPE
        doctoralThesisDefenseTrial.setType(3);

        //FIELD OF STUDY
        do {
            trial_exists = false;
            System.out.print("Enter the trial's name: ");
            trial_name = scanner.nextLine();
            if (compareIfTrialInTrialList(trial_name)){
                trial_exists=true;
            }
        }while (trial_exists);
        doctoralThesisDefenseTrial.setName(trial_name);


        // JOURNAL NAME
        do {
            System.out.print("Enter the thesis field of study: ");
            journal_name = scanner.nextLine();
            if (journal_name == null || journal_name.isEmpty()) {
                System.out.println("\nJournal's name is wrong, try again.\n");
            }
        }while(journal_name == null || journal_name.isEmpty());
        doctoralThesisDefenseTrial.setFieldOfStudy(journal_name);

        //DEFENSE DIFFICULTY
        doctoralThesisDefenseTrial.setDifficulty(askUserOptionBetweenNumbers("Enter the defense difficulty: " , 1,10));

        return doctoralThesisDefenseTrial;
    }

    private PaperPublicationTrial fillNewPaperSubmissionTrial(){

        PaperPublicationTrial paperPublicationTrial = new PaperPublicationTrial();

        Scanner scanner = new Scanner(System.in);
        boolean trial_exists, validate;
        String trial_name, journal_name, quartile;
        int acceptance, revision, rejection;

        //SET TYPE
        paperPublicationTrial.setType(1);

        //TRIAL NAME
        do {
            trial_exists = false;
            System.out.print("Enter the trial's name: ");
            trial_name = scanner.nextLine();
            if (compareIfTrialInTrialList(trial_name)){
                trial_exists=true;
            }
        }while (trial_exists);
        paperPublicationTrial.setName(trial_name);

        // JOURNAL NAME
        do {
            System.out.print("Enter the journal’s name: ");
            journal_name = scanner.nextLine();
            if (journal_name == null || journal_name.isEmpty()) {
                System.out.println("\nJournal's name is wrong, try again.\n");
            }
        }while(journal_name == null || journal_name.isEmpty());
        paperPublicationTrial.setJournalName(journal_name);

        // QUARTILE
        do {
            validate=false;
            System.out.print("Enter the journal’s quartile: ");
            quartile = scanner.nextLine();

            if (!quartile.equals("Q1") && !quartile.equals("Q2") && !quartile.equals("Q3") && !quartile.equals("Q4")){
                System.out.println("\nWrong quartile, try again.\n");
            } else  {
                switch (quartile) {
                    case "Q1" -> paperPublicationTrial.setQuartile(1);
                    case "Q2" -> paperPublicationTrial.setQuartile(2);
                    case "Q3" -> paperPublicationTrial.setQuartile(3);
                    case "Q4" -> paperPublicationTrial.setQuartile(4);
                    default -> throw new IllegalStateException("Unexpected value: " + quartile);
                }
                validate=true;
            }

        }while (validate);

        // PERCENTAGES
        do {
            acceptance = askUserOptionBetweenNumbers("Enter the acceptance probability: ", 0, 100);
            revision = askUserOptionBetweenNumbers("Enter the revision probability: ", 0, 100);
            rejection = askUserOptionBetweenNumbers("Enter the rejection probability: ", 0, 100);

            if (acceptance + revision + rejection != 100){
                System.out.println("\nThe percentages aren't correct, try again.\n");
            }

        } while (acceptance + revision + rejection != 100);
        paperPublicationTrial.setAcceptanceProbability(acceptance);
        paperPublicationTrial.setRevisionProbability(revision);
        paperPublicationTrial.setRejectionProbability(rejection);

        return paperPublicationTrial;
    }


    private MasterStudiesTrial fillNewMasterStudiesTrial() {

        MasterStudiesTrial masterStudiesTrial = new MasterStudiesTrial();

        Scanner scanner = new Scanner(System.in);
        String masterName;

        //SET TYPE
        masterStudiesTrial.setType(2);

        //TRIAL NAME
        boolean trial_exists;
        String trial_name;

        do {
            trial_exists = false;
            System.out.print("Enter the trial's name: ");
            trial_name = scanner.nextLine();
            if (compareIfTrialInTrialList(trial_name)){
                trial_exists=true;
            }
        }while (trial_exists);
        masterStudiesTrial.setName(trial_name);

        //MASTER NAME
        do {
            System.out.println("Enter the master's name: ");
            masterName = scanner.nextLine();
            if (masterName == null || masterName.isEmpty()) {
                System.out.println("\nJournal's name is wrong, try again.\n");
            }

        }while (masterName == null || masterName.isEmpty());
        masterStudiesTrial.setMasterName(masterName);

        //ECTS NUMBER
        masterStudiesTrial.setCreditNum(askUserOptionBetweenNumbers("Enter the Master's ECTS number: ", 60, 120));

        //CREDIT PASS PROBABILITY
        masterStudiesTrial.setPassProbability(askUserOptionBetweenNumbers("Enter the credit pass probability: ", 0,100));

        return masterStudiesTrial;

    }

    public void listTrials (){

        int option;

        if (trialList.isEmpty()){
            System.out.println("\nThere are no trials currently.");
        }else {
            System.out.println("\nHere are the current trials, do you want to see more details or go back?\n");

            for (int i = 0; i < trialList.size(); i++) {
                System.out.println(i + 1 + ") " + trialList.get(i).getName());
            }
            System.out.println();
            System.out.println(trialList.size()+1 + ") Back");
            System.out.println();

            option = askUserOptionBetweenNumbers("Enter an option: ", 1, trialList.size() + 1);

            System.out.println(option);
            System.out.println("Trial size: " + trialList.size());

            if (option >= 1 && option <= trialList.size()){
                System.out.println("\nTrial: " + trialList.get(option - 1).getName());

                for (Trial trial : trialList) {

                    if (trial.getType() == 1) {
                        PaperPublicationTrial paperPublicationTrial = (PaperPublicationTrial) trial;
                        paperPublicationTrial.printDetails();
                    } else if (trial.getType() == 2) {
                        MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) trial;
                        masterStudiesTrial.printDetails();
                    } else if (trial.getType() == 3) {
                        DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) trial;
                        doctoralThesisDefenseTrial.printDetails();
                    } else if (trial.getType() == 4) {
                        BudgetRequestTrial budgetRequestTrial = (BudgetRequestTrial) trial;
                        budgetRequestTrial.printDetails();
                    }
                }

            }

        }
    }

    public void deleteTrials () {
        int option, trial_deleted_int;
        String trial_deleted;
        Scanner scanner = new Scanner(System.in);

        if (trialList.isEmpty()) {
            System.out.println("\nThere are no trials currently.");
        } else {
            System.out.println("\nWhich trial do you want to delete?\n");

            for (int i = 0; i < trialList.size(); i++) {
                System.out.println(i + 1 + ") " + trialList.get(i).name);
            }
            System.out.println();
            System.out.println(trialList.size() + 1 + ") Back");
            System.out.println();

            option = askUserOptionBetweenNumbers("Enter an option: ", 1, trialList.size() + 1);

            if (option >= 1 && option <= trialList.size()) {
                System.out.println("Enter the trial’s name for confirmation: ");
                trial_deleted = scanner.nextLine();
                //trial_deleted_int = Integer.parseInt(trial_deleted);
                if (trial_deleted.equals(trialList.get(option-1).name)){
                    trialList.remove(option-1);
                    System.out.println("The trial was successfully deleted.\n");
                }else{
                    System.out.println("The trial wasn't deleted, try again.\n");
                }
            }
        }
    }

    private int askUserOptionBetweenNumbers(String text, int min, int max){
        int option;
        String input;

        Scanner scanner = new Scanner(System.in);

        do{
            System.out.print(text);
            input = scanner.nextLine();

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException exception) {
                option = -1;
            }

            if (option<min || option>max){
                System.out.println("\nWrong input! Try again.");
            }

        } while (option<min || option>max);
        return option;
    }

    public void loadTrialsListFromCSV(){

        try{
            Scanner scanner = new Scanner(file);

            while (scanner.hasNextLine()){
                String line = scanner.nextLine();

                switch (line.charAt(0)) {
                    case '1' -> {
                        PaperPublicationTrial paperPublicationTrial = new PaperPublicationTrial();
                        paperPublicationTrial.setType(1);
                        paperPublicationTrial.setValuesFromCSV(line);
                        trialList.add(paperPublicationTrial);
                    }
                    case '2' -> {
                        MasterStudiesTrial masterStudiesTrial = new MasterStudiesTrial();
                        masterStudiesTrial.setType(2);
                        masterStudiesTrial.setValuesFromCSV(line);
                        trialList.add(masterStudiesTrial);
                    }
                    case '3' -> {
                        DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = new DoctoralThesisDefenseTrial();
                        doctoralThesisDefenseTrial.setType(3);
                        doctoralThesisDefenseTrial.setValuesFromCSV(line);
                        trialList.add(doctoralThesisDefenseTrial);
                    }
                    case '4' -> {
                        BudgetRequestTrial budgetRequestTrial = new BudgetRequestTrial();
                        budgetRequestTrial.setType(4);
                        budgetRequestTrial.setValuesFromCSV(line);
                        trialList.add(budgetRequestTrial);
                    }
                    default -> System.out.println("No identification number for trial type");
                }
            }
            System.out.println("Trials loaded successfully");
        } catch (FileNotFoundException e){
            System.out.println("Error with file, couldn't load trials");
        }
    }



    public void copyTrialsListToCSV(){
        try{
            FileWriter fw = new FileWriter(file, false);
            for (int i = 0, trialListSize = trialList.size(); i < trialListSize; i++) {
                Trial trial = trialList.get(i);
                if (trial.getType() == 1) {
                    PaperPublicationTrial paperPublicationTrial = (PaperPublicationTrial) trial;
                    fw.write(paperPublicationTrial.toCSV());
                } else if (trial.getType() == 2) {
                    MasterStudiesTrial masterStudiesTrial = (MasterStudiesTrial) trial;
                    fw.write(masterStudiesTrial.toCSV());
                } else if (trial.getType() == 3) {
                    DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = (DoctoralThesisDefenseTrial) trial;
                    fw.write(doctoralThesisDefenseTrial.toCSV());
                } else if (trial.getType() == 4) {
                    BudgetRequestTrial budgetRequestTrial = (BudgetRequestTrial) trial;
                    fw.write(budgetRequestTrial.toCSV());
                }
                fw.write(System.lineSeparator());
            }
            fw.close();
        }catch (IOException e){
            System.out.println("Error is: " + e);
        }
    }

     

}

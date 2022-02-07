package Business;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class EditionsManager {

    private final ArrayList<Trial> trialsList;
    private final ArrayList<Edition> editionsList;
    File file = new File("EditionList.csv");

    public EditionsManager(ArrayList<Trial> trialsList) {
        this.trialsList = trialsList;
        this.editionsList = new ArrayList<>();
        ArrayList<Trial> trialForNextEdition = new ArrayList<>();
        trialForNextEdition.add(trialsList.get(0));
        trialForNextEdition.add(trialsList.get(2));
        trialForNextEdition.add(trialsList.get(0));
        editionsList.add(new Edition(2022, 5, 3, trialForNextEdition, new ArrayList<>(),0));
    }


    public ArrayList<Trial> getTrialsList() {
        return trialsList;
    }

    public ArrayList<Edition> getEditionsList() {
        return editionsList;
    }

    public void createEdition() {

        Edition edition = new Edition();
        ArrayList<Trial> trials = new ArrayList<Trial>();
        int year, num_players, num_trials;
        boolean error = false;

        do {
            year = askUserOptionBetweenNumbers("\nEnter the edition’s year: " , 2022, 999999999);
            for (Edition value : editionsList) {
                if (value.getEditionYear() == year) {
                    System.out.println("This edition already exists!");
                    error = true;
                } else {
                    error = false;
                }
            }
        }while (error);
        edition.setEditionYear(year);

        num_players = askUserOptionBetweenNumbers("Enter the initial number of players: ", 1, 5);
        edition.setNumPlayers(num_players);

        num_trials = askUserOptionBetweenNumbers("Enter the number of trials: ", 3, 12);
        edition.setNumTrials(num_trials);

        System.out.println("\n     --- Trials ---\n");

        for (int i = 0; i < trialsList.size(); i++) {
            System.out.println( (i+1) + ") " + trialsList.get(i).getTrialName());

        }

        for (int i = 0; i < num_trials; i++) {
            int trial = askUserOptionBetweenNumbers("Pick a trial (" + (i+1) + "/" + num_trials + "): " , 1, trialsList.size());
            //trials.add(trialsList.get(trial-1));
            edition.getTrials().add(trialsList.get(trial-1));
            //((ArrayList<Trial>)((Edition) edition).getTrials()).add(trialsList.get(trial-1));
            //edition.getTrials().clone();
        }

        System.out.println("\nThe edition was created successfully!");
        editionsList.add(edition);

    }

    public void listEditions() {

        int option;

        if (editionsList.size()>=1) {
            System.out.println("Here are the current editions, do you want to see more details or go back?");
            printEditions();
            option = askUserOptionBetweenNumbers("Enter an option: ", 1, editionsList.size()+1) - 1;
            if (editionsList.size()>option) {
                editionsList.get(option).printDetails();
                //Con print Details podemos imprimir toda la info de la edition desde la clase edition misma
            }
        } else {
            System.out.println("No editions in the list");
        }
    }

    public void duplicateEditions() {
        int option, yearToClone, numPlayersToClone;
        System.out.println("\nWhich edition do you want to clone?");

        printEditions();

        option = askUserOptionBetweenNumbers("Enter an option: ", 1, editionsList.size()+1) - 1;

        if (editionsList.get(option).getTrialExecuting()==0) {
            yearToClone = askUserOptionBetweenNumbers("Enter the new edition’s year: ", 1, 9999999);
            numPlayersToClone = askUserOptionBetweenNumbers("Enter the new edition’s initial number of players: ", 1, 5);

            cloneEdition(editionsList.get(option), yearToClone, numPlayersToClone);
            System.out.println("The edition was cloned successfully!");
        } else {
            System.out.println("This Edition has already been started and can't be duplicated");
        }

    }

    public void deleteEdition(){
        int option, confirmationYear;
        System.out.println("\nWhich edition do you want to delete?");

        printEditions();

        option = askUserOptionBetweenNumbers("Enter an option: ", 1, editionsList.size()+1) - 1;
        confirmationYear = askUserOptionBetweenNumbers("Enter the edition’s year for confirmation:", 1, 9999999);

        if (confirmationYear == editionsList.get(option).getEditionYear()){
            editionsList.remove(option);
            printEditions();
            System.out.println("The edition was successfully deleted.");
        } else {
            System.out.println("The edition has not been deleted.");
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

    public void cloneEdition(Edition edition, int year, int numPlayers){
        ArrayList<Trial> trialsClone = new ArrayList<>();
        /*
        for(Trial trial:edition.getTrials()){
            trialsClone.add(trial);
        }
        */
        // CONTRA -> no sabemos el valor de i
        trialsClone.addAll(edition.getTrials());

        Edition editionClone = new Edition(year, numPlayers, edition.getNumTrials(), trialsClone, new ArrayList<>(),0);
        editionsList.add(editionClone);

        //editionClone = edition; esto no hay que hacerlo por noseque de los PUTOS punteros

    }


    public void printEditions(){

        System.out.print("\n");
        for (int i = 0; i < editionsList.size(); i++) {
            System.out.println("    " + (i+1) + ") The Trials " + editionsList.get(i).getEditionYear());
        }
        System.out.println("\n    " + (editionsList.size()+1) + ") Back\n");

    }

    public void printInfoYearEdition(int year){
        int aux=0;
        for (int i = 0; i < editionsList.size(); i++) {
            if (year==editionsList.get(i).getEditionYear()){
                aux=i;
            }
        }
        editionsList.get(aux).printDetails();

        for (int i = 0; i < editionsList.get(aux).getPlayerList().size(); i++) {
            System.out.println("Player name:" + editionsList.get(aux).getPlayerList().get(i).getName());
            System.out.println("Player PI: " + editionsList.get(aux).getPlayerList().get(i).getInvestigationPoints());
            System.out.println("Is alive? " + editionsList.get(aux).getPlayerList().get(i).isAlive());
        }




    }


    public void copyEditionsListToCSV(){
        try{
            FileWriter fw = new FileWriter(file, false);
            for (Edition edition : editionsList) {

                fw.write(edition.toCSV());
                fw.write(System.lineSeparator());
            }
            fw.close();
        }catch (IOException e){
            System.out.println(e);
        }
    }

    public boolean checkForRepeatedEditions(Edition auxedition){
        int aux=0;
        for (int i = 0; i < editionsList.size(); i++) {
            if (editionsList.get(i).getTrialExecuting()>0){
                aux=i;
            }
        }
        if (editionsList.get(aux).getEditionYear()==auxedition.getEditionYear()){
            return false;
        } else {
            return true;
        }

    }

    public void loadEditionsListFromCSV(){

        try{
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                Edition edition = new Edition();
                edition.setEditionValuesFromCSV(line);
                editionsList.add(edition);
            }
            System.out.println("Editions loaded successfully");
            System.out.println("Aqui las printo: ");
            printInfoYearEdition(2022);
        } catch (FileNotFoundException e){
            System.out.println("Error with file, couldn't load trials");
        }
    }
}

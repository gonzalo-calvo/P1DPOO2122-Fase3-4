package Persistence;

import Business.Editions.Edition;
import Business.Trials.*;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Classe que s'encarrega de gestionar el llegir i escriure a fitxers
 */
public class FilesController {

    File jsonEditionFile = new File("JSONEditionList.json");
    File csvEditionFile = new File("CSVEditionList.csv");
    File jsonTrialsFile = new File("JSONTrialList.json");
    File csvTrialsFile = new File("CSVTrialList.csv");


    /**
     * Constructor de la classe
     */
    public FilesController() {
    }


    /**
     * Aquest mètode guarda les edicions a l'arxiu tipos CSV
     * @param editionsList Paràmetre amb la llista d'edicions per guardar a l'arxiu CSV
     */
    public void copyEditionsListToCSV(ArrayList<Edition> editionsList){
        try{
            FileWriter fw = new FileWriter(csvEditionFile, false);
            for (Edition edition : editionsList) {
                fw.write(edition.toCSV());
                fw.write(System.lineSeparator());
            }
            fw.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Aquest mètode guarda les edicions a l'arxiu tipos JSON
     * @param editionsList Paràmetre amb la llista d'edicions per guardar a l'arxiu JSON
     */
    public void copyEditionsListToJSON(ArrayList<Edition> editionsList){
        try {
            FileWriter fw = new FileWriter(jsonEditionFile,false);
            fw.write(new Gson().toJson(editionsList));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aquest mètode agafa l'arxiu CSV, el llegeix i crea objectes tipos edicions a l'array d'edicions
     * @param editionsList Paràmetre amb la llista d'edicions que ha d'omplir
     */
    public void loadEditionsListFromCSV(ArrayList<Edition> editionsList){

        try{
            Scanner scanner = new Scanner(csvEditionFile);
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                Edition edition = new Edition();
                edition.setEditionValuesFromCSV(line);
                editionsList.add(edition);
            }
            System.out.println("Editions loaded successfully");
        } catch (FileNotFoundException e){
            System.out.println("Error with file, couldn't load trials");
        }
    }

    public void loadEditionsListFromJson(ArrayList<Edition> editionsList){

    }


    /**
     * Aquest mètode guarda les trials a l'arxiu tipos CSV
     * @param trialList Paràmetre amb la llista de trials per guardar a l'arxiu CSV
     */
    public void copyTrialsListToCSV(ArrayList<Trial> trialList){

    }

    /**
     * Aquest mètode guarda les trials a l'arxiu tipos JSON
     * @param trialList Paràmetre amb la llista de trials per guardar a l'arxiu JSON
     */
    public void copyTrialsListToJSON(ArrayList<Trial> trialList){
        try {
            FileWriter fw = new FileWriter(jsonTrialsFile,false);
            String json = new Gson().toJson(trialList);

            System.out.println(json);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Aquest mètode agafa l'arxiu CSV, el llegeix i crea objectes tipos trials a l'array de trials
     * @param trialList Paràmetre amb la llista de trials que ha d'omplir
     */
    public void loadTrialsListFromCSV(ArrayList<Trial> trialList){
        try{
            Scanner scanner = new Scanner(csvTrialsFile);

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

    /**
     * Aquest mètode agafa l'arxiu JSON, el llegeix i crea objectes tipos trials a l'array de trials
     * @param trialList Paràmetre amb la llista de trials que ha d'omplir
     */
    public void loadTrialsListFromJSON(ArrayList<Trial> trialList){
        Gson gson = new Gson();

        JsonReader reader = null;

        try {
            //System.out.println("about to read");
            loadTrialsListFromCSV(trialList);
            reader = new JsonReader(new FileReader(jsonTrialsFile));
        } catch (FileNotFoundException e) {
            System.out.println("The JSON file could not be read, the 'path' or the content of the file is incorrect");
            e.printStackTrace();
        }

        //trialList = gson.fromJson(reader,Trial.class);
        //System.out.println(reader);
        //System.out.println("loaded");


        //System.out.println("saved");


        //trialList = gson.fromJson(String.valueOf(jsonTrialsFile), (Type) Trial[].class);
        //System.out.println(gson.fromJson(String.valueOf(jsonTrialsFile), (Type) Trial[].class).toString());
        //trialList.add(gson.fromJson(String.valueOf(jsonTrialsFile), (Type) Trial[].class));



    }



}

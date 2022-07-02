package Persistence.CSVDAOs;

import Business.Editions.Edition;
import Business.Trials.*;
import Persistence.DAOs.TrialsDAO;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVTrialsDAO implements TrialsDAO {

    String csvTrialsFile = "Files/CSVTrialList.csv";

    @Override
    public void saveTrialsListToFile(ArrayList<Trial> trialList) {
        try{
            FileWriter fw = new FileWriter(csvTrialsFile, false);
            for (Trial trial : trialList) {
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



    @Override
    public ArrayList<Trial> getTrialsListFromFile() {
        ArrayList<Trial> trialList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(csvTrialsFile));

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
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }


        return trialList;
    }


}

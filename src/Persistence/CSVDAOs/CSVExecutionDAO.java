package Persistence.CSVDAOs;

import Business.Editions.Edition;
import Business.Players.DoctorPlayer;
import Business.Players.EngineerPlayer;
import Business.Players.MasterPlayer;
import Business.Players.Player;
import Business.Trials.*;
import Persistence.DAOs.ExecutionDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVExecutionDAO implements ExecutionDAO {

    String csvExecutionFile = "Files/CSVExecutionFile.csv";

    @Override
    public void saveExecutionToFile(Edition edition) {
        try{
            FileWriter fw = new FileWriter(csvExecutionFile, false);
            fw.write(edition.toCSV());
            fw.close();
        }catch (IOException e){
            System.out.println("Error is: " + e);
        }
    }

    @Override
    public Edition getExecutionFromFile() {
        Edition edition = new Edition();

        try {
            Scanner scanner = new Scanner(new File(csvExecutionFile));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                edition = setEditionValuesFromCSV(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return edition;
    }

    @Override
    public void eraseExecutionFile() {
        try {
            new FileWriter(csvExecutionFile, false).close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Edition setEditionValuesFromCSV(String line) {

        Edition auxEdition = new Edition();
        ArrayList<Player> auxPlayers = new ArrayList<>();
        ArrayList<Trial> auxTrials = new ArrayList<>();

        String[] values = line.split(";");

        auxEdition.setEditionYear(Integer.parseInt(values[0]));

        auxEdition.setNumPlayers(Integer.parseInt(values[1]));

        String[] playerValues = values[2].split(":");
        if (!playerValues[0].equals("")) {
            for (int i = 0; i < playerValues.length; i++) {
                String[] playerInfo = playerValues[i].split("-");
                switch (playerInfo[2]) {
                    case "1":
                        EngineerPlayer engineer = new EngineerPlayer();
                        engineer.setPlayerValuesFromCSV(playerValues[i]);
                        auxPlayers.add(engineer);
                        break;
                    case "2":
                        MasterPlayer masterPlayer = new MasterPlayer();
                        masterPlayer.setPlayerValuesFromCSV(playerValues[i]);
                        auxPlayers.add(masterPlayer);
                        break;
                    case "3":
                        DoctorPlayer doctorPlayer = new DoctorPlayer();
                        doctorPlayer.setPlayerValuesFromCSV(playerValues[i]);
                        auxPlayers.add(doctorPlayer);
                        break;
                }
            }
        }

        auxEdition.setNumTrials(Integer.parseInt(values[3]));

        String[] trialValues = values[4].split(":");
        for (String trialValue : trialValues) {
            switch (trialValue.charAt(0)) {
                case '1' -> {
                    PaperPublicationTrial paperPublicationTrial = new PaperPublicationTrial();
                    paperPublicationTrial.setType(1);
                    paperPublicationTrial.setValuesFromCSV(trialValue);
                    auxTrials.add(paperPublicationTrial);
                }
                case '2' -> {
                    MasterStudiesTrial masterStudiesTrial = new MasterStudiesTrial();
                    masterStudiesTrial.setType(2);
                    masterStudiesTrial.setValuesFromCSV(trialValue);
                    auxTrials.add(masterStudiesTrial);
                }
                case '3' -> {
                    DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = new DoctoralThesisDefenseTrial();
                    doctoralThesisDefenseTrial.setType(3);
                    doctoralThesisDefenseTrial.setValuesFromCSV(trialValue);
                    auxTrials.add(doctoralThesisDefenseTrial);
                }
                case '4' -> {
                    BudgetRequestTrial budgetRequestTrial = new BudgetRequestTrial();
                    budgetRequestTrial.setType(4);
                    budgetRequestTrial.setValuesFromCSV(trialValue);
                    auxTrials.add(budgetRequestTrial);
                }
            }
        }

        auxEdition.setTrialExecuting(Integer.parseInt(values[5]));

        auxEdition.setPlayerList(auxPlayers);
        auxEdition.setEditionsTrialsList(auxTrials);

        return auxEdition;

    }
}

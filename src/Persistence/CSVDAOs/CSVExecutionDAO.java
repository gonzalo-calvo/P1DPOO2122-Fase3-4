package Persistence.CSVDAOs;

import Business.Editions.Edition;
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
                edition.setEditionValuesFromCSV(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return edition;
    }
}

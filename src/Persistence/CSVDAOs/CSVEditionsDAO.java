package Persistence.CSVDAOs;

import Business.Editions.Edition;
import Persistence.DAOs.EditionsDAO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CSVEditionsDAO implements EditionsDAO {

    String csvEditionFile = "Files/CSVEditionList.csv";

    @Override
    public void saveEditionsListToFile(ArrayList<Edition> editionsList) {
        try{
            FileWriter fw = new FileWriter(csvEditionFile, false);
            for (Edition edition : editionsList) {
                fw.write(edition.toCSV());
                fw.write(System.lineSeparator());
            }
            fw.close();
        }catch (IOException e){
            System.out.println("Error is: " + e);
        }
    }

    @Override
    public ArrayList<Edition> getEditionsListFromFile() {

        ArrayList<Edition> editionsList = new ArrayList<>();

        try {
            Scanner scanner = new Scanner(new File(csvEditionFile));
            while (scanner.hasNextLine()){
                String line = scanner.nextLine();
                Edition edition = new Edition();
                edition.setEditionValuesFromCSV(line);
                editionsList.add(edition);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return editionsList;

    }

}

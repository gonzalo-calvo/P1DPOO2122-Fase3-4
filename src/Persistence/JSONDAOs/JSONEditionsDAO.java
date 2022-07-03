package Persistence.JSONDAOs;

import Business.Editions.Edition;
import Persistence.DAOs.EditionsDAO;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONEditionsDAO implements EditionsDAO {

    File jsonEditionFile = new File("JSONEditionList.json");

    @Override
    public void saveEditionsListToFile(ArrayList<Edition> editionsList) {
        try {
            FileWriter fw = new FileWriter(jsonEditionFile,false);
            fw.write(new Gson().toJson(editionsList));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<Edition> getEditionsListFromFile() {
        return null;
    }

}

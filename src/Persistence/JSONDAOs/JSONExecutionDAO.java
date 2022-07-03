package Persistence.JSONDAOs;

import Business.Editions.Edition;
import Persistence.DAOs.ExecutionDAO;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class JSONExecutionDAO implements ExecutionDAO {

    File jsonExecutionFile = new File("JSONExecutionList.json");

    @Override
    public void saveExecutionToFile(Edition edition) {
        try {
            FileWriter fw = new FileWriter(jsonExecutionFile,false);
            fw.write(new Gson().toJson(edition));
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Edition getExecutionFromFile() {
        return null;
    }

    @Override
    public void eraseExecutionFile() {

    }
}

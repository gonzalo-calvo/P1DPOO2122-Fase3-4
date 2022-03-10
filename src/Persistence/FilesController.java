package Persistence;

import Business.Edition;
import com.google.gson.Gson;

import java.io.FileWriter;
import java.io.IOException;

public class FilesController {

    public FilesController() {
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

    public void copyEditionsListToJson(){

        try {
            FileWriter fw = new FileWriter(jsonFile,false);
            String json = new Gson().toJson(getEditionsList());

            System.out.println(json);
            System.out.println("path: " + jsonFile);
            fw.write(json);
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }





}

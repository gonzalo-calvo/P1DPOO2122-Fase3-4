package Persistence.DAOs;

import Business.Editions.Edition;

import java.util.ArrayList;

public interface EditionsDAO {

    void saveEditionsListToFile(ArrayList<Edition> editionsList);

    ArrayList<Edition> getEditionsListFromFile();

}

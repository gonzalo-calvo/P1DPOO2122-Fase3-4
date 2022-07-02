package Persistence.DAOs;

import Business.Editions.Edition;

import java.util.ArrayList;

public interface ExecutionDAO {

    void saveExecutionsListToFile(ArrayList<Edition> editionsList);

    ArrayList<Edition> getExecutionsListFromFile();

}

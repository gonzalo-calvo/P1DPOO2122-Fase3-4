package Persistence.JSONDAOs;

import Business.Editions.Edition;
import Persistence.DAOs.ExecutionDAO;

import java.util.ArrayList;

public class JSONExecutionDAO implements ExecutionDAO {
    @Override
    public void saveExecutionsListToFile(ArrayList<Edition> editionsList) {

    }

    @Override
    public ArrayList<Edition> getExecutionsListFromFile() {
        return null;
    }
}

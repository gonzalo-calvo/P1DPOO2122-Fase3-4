package Persistence.DAOs;

import Business.Editions.Edition;

import java.util.ArrayList;

public interface ExecutionDAO {

    void saveExecutionToFile(Edition editionsList);

    Edition getExecutionFromFile();

}

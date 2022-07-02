package Persistence.DAOs;

import Business.Editions.Edition;
import Business.Trials.Trial;

import java.util.ArrayList;

public interface TrialsDAO {

    void saveTrialsListToFile(ArrayList<Trial> trialList);

    ArrayList<Trial> getTrialsListFromFile();

}

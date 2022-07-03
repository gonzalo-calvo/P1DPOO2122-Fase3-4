package Persistence.JSONDAOs;

import Business.Editions.Edition;
import Business.Trials.*;
import Persistence.DAOs.EditionsDAO;
import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;


public class JSONEditionsDAO implements EditionsDAO {

    File jsonEditionFile = new File("Files/JSONEditionList.json");

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
        ArrayList<Edition> editionList = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();

        try {

            Object obj = jsonParser.parse(new FileReader(jsonEditionFile));
            JSONArray editionsListJSON = (JSONArray) obj;

            Iterator<JSONObject> editionsListJSON_iterator = editionsListJSON.iterator();

            while (editionsListJSON_iterator.hasNext()){
                JSONObject edition = editionsListJSON_iterator.next();
                Edition auxEdition = new Edition();
                auxEdition.setTrialExecuting(0);
                auxEdition.setEditionYear(Integer.parseInt(edition.get("editionYear").toString()));
                auxEdition.setNumTrials(Integer.parseInt(edition.get("numTrials").toString()));
                auxEdition.setNumPlayers(Integer.parseInt(edition.get("numPlayers").toString()));
                auxEdition.setEditionsTrialsList(getTrialArrayFromObject(edition.get("editionsTrialsList")));
                editionList.add(auxEdition);
            }

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return editionList;
    }

    private ArrayList<Trial> getTrialArrayFromObject(Object editionsTrialsList){
        ArrayList<Trial> trialList = new ArrayList<>();

            Object obj = editionsTrialsList;
            JSONArray trialListJSON = (JSONArray) obj;
            System.out.println("json list is: " + trialListJSON);

            Iterator<JSONObject> trialsListJSON_iterator = trialListJSON.iterator();

            while (trialsListJSON_iterator.hasNext()){
                JSONObject theme = trialsListJSON_iterator.next();
                String type = theme.get("type").toString();

                switch (type){
                    case "1":
                        PaperPublicationTrial paperPublicationTrial = new PaperPublicationTrial();
                        paperPublicationTrial.setType(1);
                        paperPublicationTrial.setName(theme.get("name").toString());
                        paperPublicationTrial.setJournalName(theme.get("journalName").toString());
                        paperPublicationTrial.setQuartile(Integer.parseInt(theme.get("quartile").toString()));
                        paperPublicationTrial.setAcceptanceProbability(Integer.parseInt(theme.get("acceptanceProbability").toString()));
                        paperPublicationTrial.setRevisionProbability(Integer.parseInt(theme.get("revisionProbability").toString()));
                        paperPublicationTrial.setRejectionProbability(Integer.parseInt(theme.get("rejectionProbability").toString()));
                        trialList.add(paperPublicationTrial);
                        System.out.println("1");
                        break;
                    case "2":
                        MasterStudiesTrial masterStudiesTrial = new MasterStudiesTrial();
                        masterStudiesTrial.setType(2);
                        masterStudiesTrial.setName(theme.get("name").toString());
                        masterStudiesTrial.setMasterName(theme.get("masterName").toString());
                        masterStudiesTrial.setCreditNum(Integer.parseInt(theme.get("creditNum").toString()));
                        masterStudiesTrial.setPassProbability(Integer.parseInt(theme.get("passProbability").toString()));
                        trialList.add(masterStudiesTrial);
                        System.out.println("2");
                        break;
                    case "3":
                        DoctoralThesisDefenseTrial doctoralThesisDefenseTrial = new DoctoralThesisDefenseTrial();
                        doctoralThesisDefenseTrial.setType(3);
                        doctoralThesisDefenseTrial.setName(theme.get("name").toString());
                        doctoralThesisDefenseTrial.setFieldOfStudy(theme.get("fieldOfStudy").toString());
                        doctoralThesisDefenseTrial.setDifficulty(Integer.parseInt(theme.get("difficulty").toString()));
                        trialList.add(doctoralThesisDefenseTrial);
                        System.out.println("3");
                        break;
                    case "4":
                        BudgetRequestTrial budgetRequestTrial = new BudgetRequestTrial();
                        budgetRequestTrial.setType(4);
                        budgetRequestTrial.setName(theme.get("name").toString());
                        budgetRequestTrial.setEntityName(theme.get("entityName").toString());
                        budgetRequestTrial.setMoneyAmount(Integer.parseInt(theme.get("moneyAmount").toString()));
                        trialList.add(budgetRequestTrial);
                        System.out.println("4");
                        break;
                }
            }

        return trialList;
    }



}

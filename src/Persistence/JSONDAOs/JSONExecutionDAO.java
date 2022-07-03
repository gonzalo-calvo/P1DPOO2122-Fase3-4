package Persistence.JSONDAOs;

import Business.Editions.Edition;
import Business.Players.DoctorPlayer;
import Business.Players.EngineerPlayer;
import Business.Players.MasterPlayer;
import Business.Players.Player;
import Business.Trials.*;
import Persistence.DAOs.ExecutionDAO;
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

public class JSONExecutionDAO implements ExecutionDAO {

    File jsonExecutionFile = new File("Files/JSONExecutionList.json");

    @Override
    public void eraseExecutionFile() {

    }

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
        Edition editionToExecute = new Edition();

        JSONParser jsonParser = new JSONParser();

        try {

            Object obj = jsonParser.parse(new FileReader(jsonExecutionFile));
            JSONObject edition = (JSONObject) obj;


            editionToExecute.setTrialExecuting(Integer.parseInt(edition.get("trialExecuting").toString()));
            editionToExecute.setEditionYear(Integer.parseInt(edition.get("editionYear").toString()));
            editionToExecute.setNumTrials(Integer.parseInt(edition.get("numTrials").toString()));
            editionToExecute.setNumPlayers(Integer.parseInt(edition.get("numPlayers").toString()));
            editionToExecute.setEditionsTrialsList(getTrialArrayFromObject(edition.get("editionsTrialsList")));
            editionToExecute.setPlayerList(getPlayerArrayFromObject(edition.get("playerList")));

        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return editionToExecute;
    }

    private ArrayList<Player> getPlayerArrayFromObject(Object playerObject) {
        ArrayList<Player> playerList = new ArrayList<>();

        Object obj = playerObject;
        JSONArray trialListJSON = (JSONArray) obj;

        Iterator<JSONObject> trialsListJSON_iterator = trialListJSON.iterator();

        while (trialsListJSON_iterator.hasNext()) {
            JSONObject player = trialsListJSON_iterator.next();
            String level = player.get("level").toString();
            switch (level){
                case "1":
                    EngineerPlayer engineerPlayer = new EngineerPlayer();
                    engineerPlayer.setLevel(1);
                    engineerPlayer.setName(player.get("name").toString());
                    engineerPlayer.setInvestigationPoints(Integer.parseInt(player.get("investigationPoints").toString()));
                    playerList.add(engineerPlayer);
                    break;
                case "2":
                    MasterPlayer masterPlayer = new MasterPlayer();
                    masterPlayer.setLevel(2);
                    masterPlayer.setName(player.get("name").toString());
                    masterPlayer.setInvestigationPoints(Integer.parseInt(player.get("investigationPoints").toString()));
                    playerList.add(masterPlayer);
                    break;
                case "3":
                    DoctorPlayer doctorPlayer = new DoctorPlayer();
                    doctorPlayer.setLevel(3);
                    doctorPlayer.setName(player.get("name").toString());
                    doctorPlayer.setInvestigationPoints(Integer.parseInt(player.get("investigationPoints").toString()));
                    playerList.add(doctorPlayer);
                    break;
            }
        }
        return playerList;
    }

    private ArrayList<Trial> getTrialArrayFromObject(Object editionsTrialsList){
        ArrayList<Trial> trialList = new ArrayList<>();

        Object obj = editionsTrialsList;
        JSONArray trialListJSON = (JSONArray) obj;

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

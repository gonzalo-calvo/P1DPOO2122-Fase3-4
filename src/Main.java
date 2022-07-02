import Business.Editions.EditionsManager;
import Business.Execution.ExecutionManager;
import Persistence.CSVDAOs.CSVEditionsDAO;
import Persistence.CSVDAOs.CSVExecutionDAO;
import Persistence.CSVDAOs.CSVTrialsDAO;
import Persistence.DAOs.EditionsDAO;
import Persistence.DAOs.ExecutionDAO;
import Persistence.JSONDAOs.JSONEditionsDAO;
import Persistence.JSONDAOs.JSONExecutionDAO;
import Persistence.JSONDAOs.JSONTrialsDAO;
import Persistence.DAOs.TrialsDAO;
import Presentation.MenuController;
import Business.Trials.TrialsManager;
import Presentation.Views.EditionView;
import Presentation.Views.ExecutionView;
import Presentation.Views.MainView;
import Presentation.Views.TrialView;

/**
 * Class main que executa el programa
 */
public class Main {
    /**
     * Mètode que truca a la classe menu controller per començar el programa
     */
    public static void main(String[] args) {

        MainView mainView = new MainView();
        EditionView editionView = new EditionView();
        TrialView trialView = new TrialView();
        ExecutionView executionView = new ExecutionView();

        TrialsDAO trialsDAO;
        EditionsDAO editionsDAO;
        ExecutionDAO executionDAO;

        if (mainView.selectFileFormat().equals("I")){
            trialsDAO = new CSVTrialsDAO();
            editionsDAO = new CSVEditionsDAO();
            executionDAO = new CSVExecutionDAO();
        } else {
            trialsDAO = new JSONTrialsDAO();
            editionsDAO = new JSONEditionsDAO();
            executionDAO = new JSONExecutionDAO();
        }

        MenuController menuController = new MenuController(mainView);

        mainView.printSingleLine("Loading data from CSV files...");
        TrialsManager trialsManager = new TrialsManager(trialsDAO, menuController, trialView);
        EditionsManager editionsManager = new EditionsManager(editionsDAO, menuController, editionView);
        ExecutionManager executionManager = new ExecutionManager(executionDAO, menuController, executionView);
        menuController.addManagersToMenuController(trialsManager, editionsManager, executionManager);

        menuController.start();

    }
}

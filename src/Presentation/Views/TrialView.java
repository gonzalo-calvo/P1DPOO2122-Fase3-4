package Presentation.Views;

import Business.Trials.*;

import java.util.ArrayList;

public class TrialView {

    public TrialView() {
    }

    public void printCreateTrialMenu() {
        System.out.println("\n    --- Trial types ---\n");
        System.out.println("    1) Paper publication");
        System.out.println("    2) Master studies");
        System.out.println("    3) Doctoral thesis defense");
        System.out.println("    4) Budget request\n");
    }

    public void printSingleLine(String line) {
        System.out.println(line);
    }

    public void printTrialsList(ArrayList<Trial> trialList) {
        for (int i = 0; i < trialList.size(); i++) {
            System.out.println(i + 1 + ") " + trialList.get(i).getName());
        }
        System.out.println();
        System.out.println(trialList.size()+1 + ") Back");
        System.out.println();

    }

    public void printPaperPublication(PaperPublicationTrial paperPublicationTrial) {
            System.out.println("\nTrial: " + paperPublicationTrial.getName() + " (Paper publication)");
            System.out.println("Journal: " + paperPublicationTrial.getJournalName() + " (Q" + paperPublicationTrial.getQuartile() + ")");
            System.out.println("Chances: " +
                    paperPublicationTrial.getAcceptanceProbability() + "% acceptance, " +
                    paperPublicationTrial.getRevisionProbability() + "% revision, " +
                    paperPublicationTrial.getRejectionProbability() + "% rejection\n");
    }

    public void printMasterStudies(MasterStudiesTrial masterStudiesTrial) {
        System.out.println("\nTrial: " + masterStudiesTrial.getName() + " (Master studies)");
        System.out.println("Master: " + masterStudiesTrial.getMasterName());
        System.out.println("ECTS: " + masterStudiesTrial.getCreditNum() + ", with a " + masterStudiesTrial.getPassProbability() + "chance to pass each one");
    }

    public void printDoctoralThesisDefense(DoctoralThesisDefenseTrial doctoralThesisDefenseTrial) {
        System.out.println("\nTrial: " + doctoralThesisDefenseTrial.getName() + " (Doctoral thesis defense)");
        System.out.println("Field: " + doctoralThesisDefenseTrial.getFieldOfStudy());
        System.out.println("Difficulty: " + doctoralThesisDefenseTrial.getDifficulty());
    }

    public void printBudgetRequest(BudgetRequestTrial budgetRequestTrial) {
        System.out.println("\nTrial: " + budgetRequestTrial.getName() + " (Budget request)");
        System.out.println("Entity: " + budgetRequestTrial.getEntityName());
        System.out.println("Budget: " + budgetRequestTrial.getMoneyAmount() + " €");
    }
}

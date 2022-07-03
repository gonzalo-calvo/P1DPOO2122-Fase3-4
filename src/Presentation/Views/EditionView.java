package Presentation.Views;

import Business.Editions.Edition;

import java.util.ArrayList;

public class EditionView {

    public EditionView() {
    }

    /**
     * Mètode que printa directament totes les edicions de la llista d'edicions
     * @param editionsList
     */
    public void printEditions(ArrayList<Edition> editionsList){

        System.out.print("\n");
        for (int i = 0; i < editionsList.size(); i++) {
            System.out.println("    " + (i+1) + ") The Trials " + editionsList.get(i).getEditionYear());
        }
        System.out.println("\n    " + (editionsList.size()+1) + ") Back\n");

    }

    /**
     * Aquest mètode printa tota l'informació de la edició
     * @param edition
     */
    public void printDetails(Edition edition){
        System.out.println("\nYear: " + edition.getEditionYear());
        System.out.println("Players: " + edition.getNumPlayers());
        System.out.println("Trials: ");

        for (int i = 0; i < edition.getNumTrials(); i++) {
            System.out.println("    " + (i+1) + "- " + edition.getEditionsTrialsList().get(i).getName() + " (" + getTrialNameDetail(edition.getEditionsTrialsList().get(i).getType()) + ")");
        }
    }

    /**
     * Aquest mètode printa el nom del tipos de trial depenent del tipos de trial
     * @param type Tipos de trial a printar
     * @return Retorna el text que ha de printar
     */
    public String getTrialNameDetail(int type){

        return switch (type) {
            case 1 -> "Paper publication";
            case 2 -> "Master studies";
            case 3 -> "Doctoral thesis defense";
            case 4 -> "Budget request";
            default -> "Should never see this message";
        };

    }
}

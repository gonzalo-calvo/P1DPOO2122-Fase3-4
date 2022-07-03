package Presentation.Views;

import Business.Editions.Edition;

import java.util.Scanner;

public class ExecutionView {

    public ExecutionView() {
    }

    /**
     * Mètode que demana a l'usuari una string que no sigui vuida
     * @param text Text a printar per pantalla
     * @return Retorna la string introduida per l'usuari
     */
    public String askUserNonEmptyString(String text){
        Scanner scanner = new Scanner(System.in);
        System.out.print(text);

        return scanner.next();
    }

    public void printTrialExecutionDetails(int level, Edition edition, int playerID) {
        switch (level){
            case 1 -> System.out.print("    " + edition.getPlayerList().get(playerID).getName() + " is submitting... ");
            case 2 -> System.out.print("    Master " + edition.getPlayerList().get(playerID).getName() + " is submitting... ");
            case 3 -> System.out.print("    " + edition.getPlayerList().get(playerID).getName() + ", PhD" + " is submitting... ");
            default -> System.out.println("Error: The player level is: " + edition.getPlayerList().get(playerID).getLevel());
        }
    }
}

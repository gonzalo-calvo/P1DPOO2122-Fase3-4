package Presentation.Views;

import Business.Players.Player;

import java.util.Locale;
import java.util.Scanner;

/**
 * Classe que s'encarrega de gesionar el menu de funcionament principal del programa
 */
public class MainView {

    /**
     * Constructor de la classe MenuView
     */
    public MainView() {
    }

    /**
     * Mètode que presenta el logo inicial de las proves i et demana si vols escollir rol de "Composer" o "Conducotr"
     * @return Retorna un caràcter A o B segons el mode que hagi escollit l'usuari
     */
    public char getRole() {

        Scanner scanner = new Scanner(System.in);
        char input;

        System.out.println(" _____ _             _____      _       _");
        System.out.println("/__   \\ |__   ___   /__   \\_ __(_) __ _| |___");
        System.out.println("  / /\\/ '_ \\ / _ \\    / /\\/ '__| |/ _` | / __|");
        System.out.println(" / /  | | | |  __/   / /  | |  | | (_| | \\__ \\");
        System.out.println(" \\/   |_| |_|\\___|   \\/   |_|  |_|\\__,_|_|___/");
        System.out.println("\nWelcome to The Trials. Who are you?\n");
        System.out.println("     A) The Composer");
        System.out.println("     B) This year's Conductor\n");

        do{
            System.out.print("Enter role: ");
            input = scanner.next().charAt(0);

            if (input != 'A' && input != 'B'){
                System.out.println("\nWrong input! Try again.");
            }
        } while (input != 'A' && input != 'B');

        return input;
    }

    /**
     * Mètode en el que tries si vols administrar les "Trials" o les "Editions". Es manté en bucle mentre l'opció no sigui vàlida
     * @return Retorna el número de l'opció que l'usuari ha escollit
     */
    public int getManagementMode(){
        int option;

        System.out.println("\nEntering management mode...\n");
        System.out.println("     1) Manage Trials");
        System.out.println("     2) Manage Editions\n");
        System.out.println("     3) Exit\n");
        option = askUserOptionBetweenNumbers("Enter an option: ", 1,3);

        return option;
    }

    /**
     * Mètode en el que tries que vol fer l'usuari sobre les trials. Es manté en bucle fins que s'obtingui una upció vàlida
     * @return Retorna una lletra segons el que l'usuari vulgui fer
     */
    public int subManageTrials(){
        int option;

        System.out.println("\na) Create Trial");
        System.out.println("b) List Trials");
        System.out.println("c) Delete Trial");
        System.out.println("\nd) Back\n");

        option = askUserOptionBetweenLetters("Enter an option: ", 'a', 'd');
        return option;
    }

    /**
     * Mètode en el que tries que vol fer l'usuari sobre les edicions. Es manté en bucle fins que s'obtingui una upció vàlida
     * @return Retorna una lletra segons el que l'usuari vulgui fer
     */
    public int subManageEditions(){
        int option;

        System.out.println("\na) Create Edition");
        System.out.println("b) List Editions");
        System.out.println("c) Duplicate Editions");
        System.out.println("d) Delete Edition");
        System.out.println("\ne) Back\n");

        option = askUserOptionBetweenLetters("Enter an option: ", 'a', 'e');
        return option;
    }

    /**
     * Mètode que controla si una lletra està dins d'un rang
     * @param text valor en string
     * @param min mínim rang
     * @param max màxim rang
     * @return valor
     */
    private char askUserOptionBetweenLetters (String text, char min, char max){
        char input;

        Scanner scanner = new Scanner(System.in);

        do{
            System.out.print("Enter an option: ");
            input = scanner.next().charAt(0);

            if (input<min || input>max){
                System.out.println("\nWrong input! Try again.");
            }

        } while (input<min || input>max);
        return input;
    }

    /**
     * Mètode que controla si un valor està dins d'un rang
     * @param text valor en string
     * @param min mínim rang
     * @param max màxim rang
     * @return valor
     */
    public int askUserOptionBetweenNumbers(String text, int min, int max){
        int option;
        String input;

        Scanner scanner = new Scanner(System.in);

        do{
            System.out.print(text);
            input = scanner.nextLine();

            try {
                option = Integer.parseInt(input);
            } catch (NumberFormatException excepcion) {
                option = -1;
            }

            if (option<min || option>max){
                System.out.println("\nWrong input! Try again.");
            }

        } while (option<min || option>max);
        return option;
    }

    /**
     * Mètode que demana a l'usuari si vol continuar amb l'execució. Només accepta un "yes" o un "no"
     * @return Retrona la string amb el resultat que ha introduit l'usuari
     */
    public String  askUserYesNo(){
        boolean check = false;
        Scanner scanner = new Scanner(System.in);
        String input = null;
        while (!check) {

            System.out.print("\nContinue the execution? [yes/no]: ");

            try {
                input = scanner.nextLine().toLowerCase(Locale.ROOT);
                if (input.equals("yes") || input.equals("no")){
                    check=true;
                } else {
                    System.out.println("The input must be 'yes' or 'no'");
                }

            } catch (java.util.InputMismatchException e){
                System.out.println("The input must be 'yes' or 'no'");
                check = false;
            }
        }
        return input;
    }

    /**
     * Mètode que demana per pantalla mitjançant quin tipos de format vol treballar l'usuari, si CSV o JSON
     * @return Retorna una String identificativa per cada opció
     */
    public String selectFileFormat() {

        Scanner scanner = new Scanner(System.in);
        String selection;
        boolean flag = false;

        System.out.println("\nThe IEEE needs to know where your allegiance lies\n");
        System.out.println("    I)  People's Front of Engineering (CSV)");
        System.out.println("    II) Engineering People's Front (JSON)");

        do {
            System.out.print("\nPick a fiction: ");
            selection = scanner.nextLine();
            //System.out.println("scanner has read: " + selection);

            if (selection.compareTo("I")==0){
                flag = true;
            } else if (selection.compareTo("II")==0) {
                flag = true;
            } else {
                System.out.println("\nWrong input. Pick again");
            }

        } while (!flag);

        return selection;
    }

    /**
     * Mètode que printa l'informació del jugador
     * @param player Ojecte de tipos jugador amb la seva informació
     */
    public void printPlayer(Player player){

        System.out.println("Player name: " + player.getName() + " , Player Investigation Points: " + player.getInvestigationPoints() + " , Is player alive? " + player.isAlive());

    }

    public void printSingleLine(String stringToPrint){
        System.out.println(stringToPrint);
    }

    public String scanLine() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}

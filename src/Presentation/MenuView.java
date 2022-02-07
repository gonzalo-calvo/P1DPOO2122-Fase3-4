package Presentation;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;
import Business.MenuController;
import Business.Trial;


public class MenuView {
    public MenuView() {
    }


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

    public int getManagmentMode(){
        int option;

        System.out.println("\nEntering management mode...\n");
        System.out.println("     1) Manage Trials");
        System.out.println("     2) Manage Editions\n");
        System.out.println("     3) Exit\n");
        option = askUserOptionBetweenNumbers("Enter an option: ", 1,3);

        return option;
    }

    public int subManageTrials(){
        int option;

        System.out.println("\na) Create Trial");
        System.out.println("b) List Trials");
        System.out.println("c) Delete Trial");
        System.out.println("\nd) Back\n");

        option = askUserOptionBetweenLetters("Enter an option: ", 'a', 'd');
        return option;
    }

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


    private char askUserOptionBetweenLetters (String text, char min, char max){

        char input;

        Scanner scanner = new Scanner(System.in);

        do{
            System.out.print(text);
            input = scanner.next().charAt(0);

            if (input<min || input>max){
                System.out.println("\nWrong input! Try again.");
            }

        } while (input<min || input>max);
        return input;
    }

    private int askUserOptionBetweenNumbers(String text, int min, int max){
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


}

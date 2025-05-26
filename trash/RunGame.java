package sololeveling;

import sololeveling.utils.Display;
import java.util.Scanner;

public class RunGame {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Display.clear();
        Menu.showWelcomeScreen();
        scanner.nextLine();
        Display.clear();
        Menu.showMainMenu();
        scanner.close();
    } 
}
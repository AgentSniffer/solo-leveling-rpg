package ui;

import java.util.Scanner;

/**
 * Base class for all CLI interfaces in the game.
 * Provides common functionality like screen clearing and input handling.
 */
public abstract class BaseCLI {
    protected static final Scanner scanner = new Scanner(System.in);
    protected boolean isRunning = true;
    
    /**
     * Displays the UI screen
     */
    public abstract void display();
    
    /**
     * Handles user input for the current screen
     */
    public abstract void handleInput();
    
    /**
     * Clears the console screen
     */
    protected void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
    
    /**
     * Waits for user to press Enter
     */
    protected void waitForInput() {
        System.out.println("\nPress Enter to continue...");
        scanner.nextLine();
    }
    
    /**
     * Prints a box around the given content
     * @param content The content to be boxed
     */
    protected void printBox(String content) {
        String[] lines = content.split("\\r?\\n");
        int maxLength = 0;
        for (String line : lines) {
            if (line.length() > maxLength) {
                maxLength = line.length();
            }
        }
        
        String border = "+" + "-".repeat(maxLength + 2) + "+";
        System.out.println(border);
        for (String line : lines) {
            System.out.printf("| %-" + maxLength + "s |%n", line);
        }
        System.out.println(border);
    }
    
    /**
     * Runs the UI loop
     */
    public void run() {
        while (isRunning) {
            display();
            handleInput();
        }
    }
}

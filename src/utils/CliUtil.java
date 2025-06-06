package utils;

import cli.LoginCli;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class CliUtil {

    public static Scanner sc = new Scanner(System.in);
    public static Random ra = new Random();

    public static String BRIGHT_BLUE = "\u001B[94m";
    public static String BRIGHT_PURPLE = "\u001B[95m";
    public static String DEFAULT_COLOR = "\u001B[0m";
    public static String BLACK = "\u001B[30m";
    public static String GRAY = "\u001B[90m";
    public static String RED = "\u001B[31m";
    public static String GREEN = "\u001B[32m";
    public static String YELLOW = "\u001B[33m";
    public static String BLUE = "\u001B[34m";
    public static String PURPLE = "\u001B[35m";
    public static String CYAN = "\u001B[36m";
    public static String WHITE = "\u001B[37m";
    public static String RESET = "\u001B[0m";

    public static void clearScreen() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (IOException | InterruptedException e) {
        }
    }

    public static void settings() {
        while (true) {
            clearScreen();
            printBox("Settings\n1. 🎨 Change input color\n2. 🔙 Back");
            System.out.print(DEFAULT_COLOR + "> ");

            String input = sc.nextLine().trim();
            System.out.print(RESET);

            switch (input) {
                case "1" ->
                    displaySettingsColor();
                case "2" -> {
                    LoginCli.display();
                    return;
                }
                default -> {
                    System.out.println(RED + "\n❌ Invalid input." + RESET);
                    pause();
                }
            }
        }
    }

    public static void displaySettingsColor() {
        while (true) {
            clearScreen();
            printBox("Choose a color\n1. Red\n2. Green\n3. Blue\n4. Yellow\n5. Purple\n6. Cyan\n7. Black\n8. White\n9. Back");

            System.out.print(DEFAULT_COLOR + "> ");
            String input = sc.nextLine().trim();
            resetColor();

            boolean valid = true;
            switch (input) {
                case "1" ->
                    DEFAULT_COLOR = RED;
                case "2" ->
                    DEFAULT_COLOR = GREEN;
                case "3" ->
                    DEFAULT_COLOR = BLUE;
                case "4" ->
                    DEFAULT_COLOR = YELLOW;
                case "5" ->
                    DEFAULT_COLOR = PURPLE;
                case "6" ->
                    DEFAULT_COLOR = CYAN;
                case "7" ->
                    DEFAULT_COLOR = BLACK;
                case "8" ->
                    DEFAULT_COLOR = WHITE;
                case "9" -> {
                    return;
                }
                default -> {
                    System.out.println(RED + "\n❌ Invalid input." + RESET);
                    pause();
                    valid = false;
                }
            }

            if (valid) {
                System.out.println(GREEN + "\n✅ Color changed successfully!");
                pause();
                return;
            }
        }
    }

    public static void resetColor() {
        System.out.print(RESET);
    }

    public static void pause() {
        System.out.println("Press " + RED + "ENTER" + RESET + " to continue...");
        sc.nextLine();
    }

    public static void exit() {
        System.out.println("Arrivederci Hunter!");
        closeScanner();
        System.exit(0);
    }

    public static void closeScanner() {
        sc.close();
    }

    public static void printBox(String text) {
        String[] textLines = text.split("\n");

        int maxLineLength = 0;
        for (String line : textLines) {
            maxLineLength = Math.max(maxLineLength, line.length());
        }

        String horizontalLine = "";
        for (int i = 0; i < maxLineLength + 2; i++) {
            horizontalLine += "═";
        }

        System.out.println("╔" + horizontalLine + "╗");
        for (String line : textLines) {
            int remainingSpace = (maxLineLength - line.length());

            for (int i = 0; i < remainingSpace; i++) {
                line += " ";
            }
            System.out.println("║" + " " + line + " " + "║");
        }
        System.out.println("╚" + horizontalLine + "╝");
    }

    public static void loadingScreen(double seconds) {
        String[] LOADING_TEXTS = {
            "Summoning Igris...",
            "Awakening Shadows...",
            "Leveling Up...",
            "Dungeon Break Detected...",
            "System Initializing...",
            "Shadow Army Assembling...",
            "Monarch Power Rising...",
            "Daily Quest Loading...",
            "Hunter Rank Calculating...",
            "Shadow Exchange Active...",
            "Arise, My Soldiers...",
            "System Window Opening...",
            "Mana Core Stabilizing...",
            "Shadow Extraction Ready..."
        };

        String BLADE_ART
                = ".______________________________________________________|_._._._._._._._._._.\n"
                + " \\_____________________________________________________|_#_#_#_#_#_#_#_#_#_|\n"
                + "                                                       l";

        java.util.Random random = new java.util.Random();
        int frame = 0;
        int currentTextIndex = 0;
        long lastTextChange = System.currentTimeMillis();
        long targetDuration = (long) (seconds * 1000);
        boolean isComplete = false;

        // Clear screen and hide cursor for smoother animation
        System.out.print("\033[2J\033[H\033[?25l");
        System.out.flush();

        // Draw static elements once
        drawStaticElements(BLADE_ART, random);

        long startTime = System.currentTimeMillis();
        int lastProgress = -1;
        int lastTextIndex = -1;
        boolean lastPulse = false;
        final long frameDelay = 50; // 50ms between frames

        while (!isComplete) {
            try {
                long currentTime = System.currentTimeMillis();
                long elapsed = currentTime - startTime;

                if (elapsed >= targetDuration) {
                    isComplete = true;
                }

                // Only update if enough time has passed (frame-based timing)
                long nextFrameTime = startTime + ((frame + 1) * frameDelay);
                if (currentTime >= nextFrameTime || isComplete) {

                    // Update text every 1.5 seconds (faster text changes)
                    if (currentTime - lastTextChange >= 1500) {
                        currentTextIndex = (currentTextIndex + 1) % LOADING_TEXTS.length;
                        lastTextChange = currentTime;
                    }

                    // Faster pulse animation
                    boolean pulse = (frame / 3) % 2 == 0;
                    int progress = Math.min(100, (int) ((elapsed * 100) / targetDuration));
                    if (isComplete) {
                        progress = 100;
                    }

                    // Update loading text only if it changed
                    if (currentTextIndex != lastTextIndex || pulse != lastPulse) {
                        updateLoadingText(LOADING_TEXTS[currentTextIndex], pulse);
                        lastTextIndex = currentTextIndex;
                        lastPulse = pulse;
                    }

                    // Update progress bar only if it changed
                    if (progress != lastProgress) {
                        updateProgressBar(progress);
                        lastProgress = progress;
                    }

                    frame++;
                }

            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
                break;
            }

            // Check for interruption
            if (Thread.currentThread().isInterrupted()) {
                return;
            }
        }

        // Show completion message (centered like the subtitle)
        System.out.print("\033[15;1H");
        System.out.print("\033[2K");
        String completionMsg = "Finished Loading!";
        int completionPadding = (80 - completionMsg.length()) / 2;
        StringBuilder completionSpaces = new StringBuilder();
        for (int i = 0; i < completionPadding; i++) {
            completionSpaces.append(" ");
        }
        System.out.println(completionSpaces.toString() + GREEN + completionMsg + RESET);

        // Show cursor again and wait
        System.out.print("\033[?25h");
        System.out.flush();

        try {
            Thread.sleep(800);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            // Don't need to do anything else, just exit gracefully
        }

        clearScreen();
    }

    private static void drawStaticElements(String bladeArt, java.util.Random random) {
        System.out.println("\n\n");

        // Draw stars background
        for (int row = 0; row < 2; row++) {
            System.out.print("    ");
            for (int col = 0; col < 70; col++) {
                if (random.nextInt(100) < 3) {
                    System.out.print(BRIGHT_BLUE + "*" + RESET);
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        System.out.println("\n");

        // Draw blade art
        String[] bladeLines = bladeArt.split("\n");
        for (String line : bladeLines) {
            System.out.print("    ");
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);
                if (c != ' ') {
                    if (i < line.length() * 0.2) {
                        System.out.print(GRAY + c + RESET);
                    } else if (i < line.length() * 0.4) {
                        System.out.print(WHITE + c + RESET);
                    } else if (i < line.length() * 0.6) {
                        System.out.print(CYAN + c + RESET);
                    } else if (i < line.length() * 0.8) {
                        System.out.print(BRIGHT_BLUE + c + RESET);
                    } else {
                        System.out.print(BRIGHT_PURPLE + c + RESET);
                    }
                } else {
                    System.out.print(c);
                }
            }
            System.out.println();
        }
        System.out.println("\n");
    }

    private static void updateLoadingText(String loadingText, boolean pulse) {
        // Move cursor to loading text line (approximately line 10)
        System.out.print("\033[10;1H");

        // Clear the line
        System.out.print("\033[2K");

        // Center and print the text (back to original)
        int padding = (80 - loadingText.length()) / 2;
        for (int i = 0; i < padding; i++) {
            System.out.print(" ");
        }

        if (pulse) {
            System.out.print(BRIGHT_BLUE + loadingText + RESET);
        } else {
            System.out.print(BLUE + loadingText + RESET);
        }
        System.out.flush();
    }

    private static void updateProgressBar(int progress) {
        // Move cursor to progress bar line (approximately line 12)
        System.out.print("\033[12;1H");

        // Clear the line
        System.out.print("\033[2K");

        int barWidth = 50;
        int filled = (progress * barWidth) / 100;

        System.out.print("               [");

        for (int i = 0; i < barWidth; i++) {
            if (i < filled) {
                System.out.print(BRIGHT_BLUE + "█" + RESET);
            } else if (i == filled && progress < 100) {
                System.out.print(CYAN + "▓" + RESET);
            } else {
                System.out.print(GRAY + "░" + RESET);
            }
        }

        System.out.print("] " + BRIGHT_BLUE + progress + "%" + RESET);

        // Move to next line and add the subtitle (centered)
        System.out.print("\033[14;1H");
        System.out.print("\033[2K");
        String subtitle = "Shadow Extraction System Loading";
        int subtitlePadding = (80 - subtitle.length()) / 2;
        StringBuilder subtitleSpaces = new StringBuilder();
        for (int i = 0; i < subtitlePadding; i++) {
            subtitleSpaces.append(" ");
        }
        System.out.print(subtitleSpaces.toString() + GRAY + subtitle + RESET);
        System.out.flush();
    }
}

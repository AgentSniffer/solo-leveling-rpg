import java.io.IOException;

// ─────────────────────────────────────────────────────────────
// ▼ Display - Handles all screen stuff
// ─────────────────────────────────────────────────────────────
public class Display {

    // ─────────────────────────────────────────────────────────────
    // ▼ LOGO & SCREEN CONTROL METHODS
    // ─────────────────────────────────────────────────────────────

    public static void logo() {
        System.out.println(Messages.ASCII_GAME_LOGO);
    }

    public static void dungeonBattleChoice() {
        printBox(Messages.BATTLE_MENU_OPTIONS);
        System.out.printf(Messages.BATTLE_MENU_OPTIONS);
        
    }

    public static void showWelcomeScreen() {
        System.out.printf(Messages.WELCOME_SCREEN, 69);
    }

    public static void delay(double seconds) {
        try {
            Thread.sleep((long)(seconds * 1000)); // Convert seconds to milliseconds
        } catch (InterruptedException e) {
            // Nothing needed here
        }
    }

    public static void clear() {
        try {
            String os = System.getProperty("os.name").toLowerCase();

            if (os.contains("windows")) {
                // Windows console clear using cmd
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();

            } else if (os.contains("linux")) {
                // Linux use terminal clear command
                new ProcessBuilder("clear").inheritIO().start().waitFor();

            } else {
                // Other OS types (Mac, BSD, unknown): fallback
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }

        } catch (IOException e) {
            // Fallback to ANSI escape codes if other methods fail
            System.out.print("\033[H\033[2J");
            System.out.flush();

        } catch (Exception e) {
            // Fallback to ANSI escape codes if other methods fail
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }

        /* Exception Handling ⚠️:
            InputMismatchException for non-numeric input.  
            General Exception as fallback and act as a Safety Net.
            reference: https://www.youtube.com/watch?v=xTtL8E4LzTQ&t=33035s
        */
    }

    // ─────────────────────────────────────────────────────────────
    // ▼ MENU DISPLAY METHODS
    // ─────────────────────────────────────────────────────────────

    public static void showGameMenu() {
        printBox(Messages.GAME_MENU_OPTIONS);
    }

    public static void showBattleMenu() {
        printBox(Messages.BATTLE_MENU_OPTIONS);
    }

    // ─────────────────────────────────────────────────────────────
    // ▼ PRINT BOX METHODS (with and without padding)
    // ─────────────────────────────────────────────────────────────

    public static void printBox(String text, int marginLeft, int marginRight, int marginTop, int marginBottom) {
        String[] textLines = text.split("\n");

        // Find the longest line
        int maxLineLength = 0;
        for (String line : textLines) {
            if (line.length() > maxLineLength) {
                maxLineLength = line.length();
            }
        }

        int totalWidth = maxLineLength + marginLeft + marginRight;

        // Create horizontal line
        String horizontalLine = "";
        for (int i = 0; i < totalWidth; i++) {
            horizontalLine += "═";
        }

        // Print top border
        System.out.println("╔" + horizontalLine + "╗");

        // Print top vertical padding
        for (int i = 0; i < marginTop; i++) {
            System.out.print("║");
            for (int j = 0; j < totalWidth; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // Print text lines with margins
        for (String line : textLines) {
            int rightPadding = maxLineLength - line.length();

            System.out.print("║");

            for (int i = 0; i < marginLeft; i++) {
                System.out.print(" ");
            }

            System.out.print(line);

            for (int i = 0; i < rightPadding + marginRight; i++) {
                System.out.print(" ");
            }

            System.out.println("║");
        }

        // Print bottom vertical padding
        for (int i = 0; i < marginBottom; i++) {
            System.out.print("║");
            for (int j = 0; j < totalWidth; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // Print bottom border
        System.out.println("╚" + horizontalLine + "╝");
    }

    // Default box printing with preset padding
    public static void printBox(String text) {
        printBox(text, 1, 1, 0, 0);
    }

}


public class Display {

    public static void logo() {
        System.out.println("             _____       _         __                    _  _           ");
        System.out.println("            |   __| ___ | | ___   |  |    ___  _ _  ___ | ||_| ___  ___ ");
        System.out.println("            |__   || . || || . |  |  |__ | -_|| | || -_|| || ||   || . |");
        System.out.println("            |_____||___||_||___|  |_____||___| \\_/ |___||_||_||_|_||_  |");
        System.out.println("                                                                   |___|");

    }

    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix-like systems (Linux/Mac), use ANSI escape codes directly
                System.out.println("\033[H\033[2J");
            }
        } catch (Exception e) {
            // Fallback to ANSI escape codes if other methods fail
            System.out.print("\033[H\033[2J");
        }
    }

    public static void showWelcomeScreen() {
        System.out.println("                    High Score: 999999\n");
        System.out.println("\n\n                 Welcome to Solo Leveling\n");
        System.out.println("                 Press Enter to continue...\n");
    }

    public static void printBox(String text) {
        String[] textLines = text.split("\n");

        // Find the longest line
        int maxLineLength = 0;
        for (String line : textLines) {
            if (line.length() > maxLineLength) {
                maxLineLength = line.length();
            }
        }

        // Add margin for aesthetic purposes
        int marginTop = 0;
        int marginBottom = 0;
        int marginLeft = 1;
        int marginRight = 1;

        int totalWidth = maxLineLength + marginLeft + marginRight; // 2 means 2 spaces both side

        // Create horizontal line
        String horizontalLine = "";
        for (int i = 0; i < totalWidth; i++) {
            horizontalLine += "═";
        }

        // Print top border top
        System.out.println("╔" + horizontalLine + "╗");

        // Print top vertical padding
        for (int i = 0; i < marginTop; i++) {
            System.out.print("║");
            for (int j = 0; j < totalWidth; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // ==============================================================
        // Print text lines with horizontal and vertical padding
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
        // ==============================================================

        // Print bottom vertical padding
        for (int i = 0; i < marginBottom; i++) {
            System.out.print("║");
            for (int j = 0; j < totalWidth; j++) {
                System.out.print(" ");
            }
            System.out.println("║");
        }

        // Print bottom border bottom
        System.out.println("╚" + horizontalLine + "╝");
    }
}

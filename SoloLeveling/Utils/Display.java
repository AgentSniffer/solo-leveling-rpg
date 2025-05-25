package SoloLeveling.Utils;

public class Display {
   
    public static void clear() {
        try {
            if (System.getProperty("os.name").contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                // For Unix-like systems (Linux/Mac), use ANSI escape codes directly
                System.out.print("\033[H\033[2J");
                System.out.flush();
            }
        } catch (Exception e) {
            // Fallback to ANSI escape codes if other methods fail
            System.out.print("\033[H\033[2J");
            System.out.flush();
        }
    }

    public static void menu() {
        System.out.println("               +-------------------------+");
        System.out.println("               |        MAIN MENU        |");
        System.out.println("               +-------------------------+");
        System.out.println("               | 1. Start Game           |");
        System.out.println("               | 2. Instructions         |");
        System.out.println("               | 3. Credits              |");
        System.out.println("               | 4. Exit                 |");
        System.out.println("               +-------------------------+");
        System.out.print  ("               Choose an option: ");
    }

    public static void logo() {
        System.out.println("             _____       _         __                    _  _           ");
        System.out.println("            |   __| ___ | | ___   |  |    ___  _ _  ___ | ||_| ___  ___ ");
        System.out.println("            |__   || . || || . |  |  |__ | -_|| | || -_|| || ||   || . |");
        System.out.println("            |_____||___||_||___|  |_____||___| \\_/ |___||_||_||_|_||_  |");
        System.out.println("                                                                   |___|");
    }
}
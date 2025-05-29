import java.util.HashMap;
import java.util.Scanner;

public class Register {
    private static HashMap<String, String> accounts = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

     showMainMenu(scanner);
    }

    public static void showMainMenu(Scanner scanner) {
        boolean signedIn = false;
               while (!signedIn) {
            Display.printBox("1. Sign In\n2. Create Account");
            System.out.print("> ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    if (accounts.isEmpty()) {
                        System.out.println("You don't have an account yet.");
                    } else {
                        signedIn = signIn(scanner);
                    }
                    break;
                case "2":
                    createAccount(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }

        System.out.println("Welcome to Solo Leveling!");
        showGameMenu(scanner);
        scanner.close();
        
    }
    private static boolean signIn(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        return accounts.containsKey(username) && accounts.get(username).equals(password);
    }

    private static void createAccount(Scanner scanner) {
        System.out.print("Enter a username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Confirm password: ");
        String confirmPassword = scanner.nextLine();

        if (!password.equals(confirmPassword)) {
            System.out.println("Passwords do not match.");
            return;
        }

        accounts.put(username, password);
        System.out.println("Account created successfully!");
    }

    private static void showGameMenu(Scanner scanner) {
        while (true) {
            Display.printBox("1. New Game\n2. Load Game\n3. Options\n4. Back to Main Menu");
            System.out.print("> ");
            String input = scanner.nextLine();

            switch (input) {
                case "1":
                    newGameFlow(scanner);
                    return;
                case "2":
                    System.out.println("Loading saved game...");
                    break;
                case "3":
                    System.out.println("Opening options...");
                    break;
                case "4":
                    showMainMenu(scanner);
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }

    private static void newGameFlow(Scanner scanner) {
        System.out.print("Enter your character's name: ");
        String name = scanner.nextLine();

        String[] roles = {"Mage", "Fighter", "Healer", "Tanker", "Assassin", "Ranger"};
        StringBuilder roleMenu = new StringBuilder("Choose your role:\n");
        for (int i = 0; i < roles.length; i++) {
            roleMenu.append((i + 1)).append(". ").append(roles[i]).append("\n");
        }
        Display.printBox(roleMenu.toString());
        System.out.print("> ");

        int roleChoice;
        while (true) {
            try {
                roleChoice = Integer.parseInt(scanner.nextLine());
                if (roleChoice >= 1 && roleChoice <= roles.length) break;
            } catch (NumberFormatException ignored) {}
            System.out.print("Please enter a valid number (1-6): ");
        }

        String role = roles[roleChoice - 1];
        System.out.println("Congrats, " + name + "! You are now classified as an E-Rank " + role + " hunter.\nYou need to level up more to achieve a higher rank.");

        Display.printBox("Would you like to play a tutorial?\n1. YES\n2. NO");
        System.out.print("> ");
        int tutorialChoice;
        while (true) {
            try {
                tutorialChoice = Integer.parseInt(scanner.nextLine());
                if (tutorialChoice == 1 || tutorialChoice == 2) break;
            } catch (NumberFormatException ignored) {}
            System.out.print("Please enter 1 or 2: ");
        }

        System.out.println("Starting your adventure, " + name + " the " + role + " hunter!");
    }
}

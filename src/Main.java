
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

class User {

    String username;
    String password;

    User(String u, String p) {
        username = u;
        password = p;
    }
}

class PlayerData {

    String playerName;
    int level;
    int exp;

    PlayerData(String name) {
        playerName = name;
        level = 1;
        exp = 0;
    }

    PlayerData(String name, int lvl, int xp) {
        playerName = name;
        level = lvl;
        exp = xp;
    }

    void gainExp(int amount) {
        exp += amount;
        if (exp >= 100) {
            level++;
            exp = 0;
            System.out.println("\nLevel up! Now level " + level);
        }
    }

    public String getStatus() {
        return "=== " + playerName + "'s Game ===\n"
                + "Level: " + level + "\n"
                + "Exp: " + exp + "/100\n";
    }
}

public class Main {

    static final String DB_BASE_URL = "jdbc:mysql://db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com:25060/";
    static final String DB_NAME = "solo_leveling_user";
    static final String DB_URL = DB_BASE_URL + DB_NAME + "?useSSL=true&requireSSL=true";
    static final String DB_USER = "solo_leveling_user";
    static final String DB_PASS = "AVNS_1D695Xh6nkrCEdFXEIg";
    static final String ADMIN_PASSWORD = "admin123";

    static Scanner scanner = new Scanner(System.in);
    static boolean running = true;
    static User user = null;
    static PlayerData player = null;

    static Connection getConnection() {
        try {
            return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
        } catch (SQLException e) {
            System.err.println("Database connection failed: " + e.getMessage());
            return null;
        }
    }

    static void ensureDatabaseExists() {
        Connection c = null;
        try {
            c = DriverManager.getConnection(DB_BASE_URL, DB_USER, DB_PASS);
            Statement s = c.createStatement();
            s.execute("CREATE DATABASE IF NOT EXISTS " + DB_NAME);
            s.close();
        } catch (SQLException e) {
            System.err.println("Error setting up database: " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                if (c != null) {
                    c.close();

                }
            } catch (SQLException e) {
            }
        }
    }

    static void initDB() {
        Connection c = getConnection();
        if (c == null) {
            System.err.println("Failed to connect to database");
            System.exit(1);
        }

        try (Statement s = c.createStatement()) {
            s.execute("""
                CREATE TABLE IF NOT EXISTS users (
                    username VARCHAR(50) PRIMARY KEY,
                    password VARCHAR(100) NOT NULL
                )""");

            s.execute("""
                CREATE TABLE IF NOT EXISTS game_states (
                    username VARCHAR(50) PRIMARY KEY,
                    player_name VARCHAR(100) NOT NULL,
                    level INT NOT NULL,
                    exp INT NOT NULL,
                    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
                )""");
        } catch (SQLException e) {
            System.err.println("DB init error: " + e.getMessage());
            System.exit(1);
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            ensureDatabaseExists();  // Make sure database exists
            initDB();
            showLoginMenu();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    static boolean userExists(String name) {
        Connection c = getConnection();
        if (c == null) {
            return false;
        }

        try (PreparedStatement s = c.prepareStatement("SELECT 1 FROM users WHERE username = ?")) {
            s.setString(1, name);
            ResultSet r = s.executeQuery();
            return r.next();
        } catch (SQLException e) {
            System.err.println("User check failed: " + e.getMessage());
            return false;
        } finally {
            try {
                if (c != null) {
                    c.close();

                }
            } catch (SQLException e) {
            }
        }
    }

    static void showLoginMenu() {
        while (true) {
            clearScreen();
            System.out.println("=== LOGIN / REGISTER ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Exit");
            System.out.print("\nEnter your choice: ");

            String input = scanner.nextLine();

            switch (input.trim()) {
                case "1" -> {
                    if (login()) {
                        showMainMenu();

                    }
                }
                case "2" ->
                    register();
                case "3" -> {
                    return;
                }
                default -> {
                    System.out.println("\nInvalid choice. Try again");
                    waitForInput();
                }
            }
        }
    }

    static boolean login() {
        clearScreen();
        System.out.println("=== LOGIN ===");
        System.out.print("Username: ");
        String name = scanner.nextLine().trim();
        System.out.print("Password: ");
        String pass = scanner.nextLine().trim();

        Connection c = getConnection();
        if (c == null) {
            return false;
        }

        try (PreparedStatement s = c.prepareStatement("SELECT password FROM users WHERE username = ?")) {
            s.setString(1, name);
            ResultSet r = s.executeQuery();

            if (r.next() && r.getString(1).equals(pass)) {
                user = new User(name, pass);
                loadGameState();
                System.out.println("\nLogin successful!");
                waitForInput();
                r.close();
                return true;
            }

            System.out.println("\nInvalid credentials");
            r.close();
            return false;
        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
            return false;
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static void register() {
        clearScreen();
        System.out.println("=== REGISTER ===");

        String name;
        while (true) {
            System.out.print("Choose username: ");
            name = scanner.nextLine().trim();
            if (name.length() < 3) {
                System.out.println("Min 3 chars");
                continue;
            }
            if (userExists(name)) {
                System.out.println("Name taken");
            } else {
                break;
            }
        }

        String pass;
        while (true) {
            System.out.print("Choose password: ");
            pass = scanner.nextLine().trim();
            if (pass.length() < 6) {
                System.out.println("Min 6 chars");
            } else {
                break;
            }
        }

        Connection c = getConnection();
        if (c == null) {
            return;
        }

        try (PreparedStatement s = c.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            s.setString(1, name);
            s.setString(2, pass);
            s.executeUpdate();

            user = new User(name, pass);
            player = new PlayerData(name);
            saveGameState();

            System.out.println("\nAccount created! Logged in.");
            waitForInput();
        } catch (SQLException e) {
            System.err.println("Register failed: " + e.getMessage());
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static void showMainMenu() {
        while (running) {
            clearScreen();
            System.out.println("=== SOLO LEVELING GAME ===");
            System.out.println("1. New Game");
            System.out.println("2. Load Game");
            System.out.println("3. Options");
            System.out.println("4. Admin");
            System.out.println("5. Exit");
            System.out.print("\nEnter choice: ");

            String input = scanner.nextLine();

            switch (input.trim()) {
                case "1" ->
                    startNewGame();
                case "2" ->
                    loadGame();
                case "3" ->
                    showOptions();
                case "4" -> {
                    System.out.print("\nEnter admin password: ");
                    if (scanner.nextLine().equals(ADMIN_PASSWORD)) {
                        showAdminMenu();
                    } else {
                        System.out.println("\nInvalid password!");
                        waitForInput();
                    }
                }
                case "5" -> {
                    System.out.println("\nBye!");
                    running = false;
                }
                default -> {
                    System.out.println("\nInvalid");
                    waitForInput();
                }
            }
        }
    }

    static void startNewGame() {
        if (user == null) {
            System.out.println("\nLogin required!");
            waitForInput();
            return;
        }

        clearScreen();
        System.out.println("=== NEW GAME ===");
        System.out.print("Character name: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Player";
        }

        player = new PlayerData(name);
        saveGameState();
        playGame();
    }

    static void loadGame() {
        if (user == null) {
            System.out.println("\nLogin required!");
            waitForInput();
            return;
        }

        if (loadGameState()) {
            playGame();
        } else {
            System.out.println("\nNo saves found!");
            waitForInput();
        }
    }

    static void playGame() {
        while (true) {
            clearScreen();
            System.out.println(player.getStatus());
            System.out.println("1. Hunt Monsters (Gain EXP)");
            System.out.println("2. Save & Return to Menu");
            System.out.print("\nChoose an action: ");

            String input = scanner.nextLine();
            switch (input.trim()) {
                case "1" -> {
                    int expGained = 10 + (int) (Math.random() * 20);
                    System.out.println("\nYou gained " + expGained + " EXP!");
                    player.gainExp(expGained);
                    waitForInput();
                }
                case "2" -> {
                    saveGameState();
                    return;
                }
                default -> {
                    System.out.println("\nInvalid choice!");
                    waitForInput();
                }
            }
        }
    }

    static void showOptions() {
        while (true) {
            clearScreen();
            System.out.println("=== OPTIONS ===");
            System.out.println("1. Toggle Sound");
            System.out.println("2. Change Difficulty");
            System.out.println("3. Back");
            System.out.print("\nEnter choice: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> {
                    System.out.println("\nSound toggled!");
                    waitForInput();
                }
                case "2" -> {
                    System.out.println("\nDifficulty changed!");
                    waitForInput();
                }
                case "3" -> {
                    return;
                }
                default -> {
                    System.out.println("\nInvalid");
                    waitForInput();
                }
            }
        }
    }

    static void clearScreen() {
        try {
            String os = System.getProperty("os.name");
            if (os.contains("Windows")) {
                new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
            } else {
                new ProcessBuilder("clear").inheritIO().start().waitFor();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static void waitForInput() {
        System.out.println("\nPress Enter...");
        scanner.nextLine();
    }

    static void saveGameState() {
        if (player == null || user == null) {
            return;
        }

        Connection c = getConnection();
        if (c == null) {
            return;
        }

        try (PreparedStatement s = c.prepareStatement(
                "INSERT INTO game_states (username, player_name, level, exp) VALUES (?, ?, ?, ?) "
                + "ON DUPLICATE KEY UPDATE player_name=VALUES(player_name), level=VALUES(level), exp=VALUES(exp)")) {

            s.setString(1, user.username);
            s.setString(2, player.playerName);
            s.setInt(3, player.level);
            s.setInt(4, player.exp);
            s.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Save failed: " + e.getMessage());
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static boolean loadGameState() {
        if (user == null) {
            return false;
        }

        Connection c = getConnection();
        if (c == null) {
            return false;
        }

        try (PreparedStatement s = c.prepareStatement(
                "SELECT player_name, level, exp FROM game_states WHERE username = ?")) {

            s.setString(1, user.username);
            ResultSet r = s.executeQuery();

            if (r.next()) {
                player = new PlayerData(
                        r.getString("player_name"),
                        r.getInt("level"),
                        r.getInt("exp")
                );
                r.close();
                return true;
            }
            r.close();
            return false;
        } catch (SQLException e) {
            System.err.println("Load failed: " + e.getMessage());
            return false;
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static void listAllUsers() {
        Connection c = getConnection();
        if (c == null) {
            return;
        }

        String query = "SELECT u.username, COALESCE(gs.player_name, 'N/A') as player_name, "
                + "COALESCE(gs.level, 1) as level, COALESCE(gs.exp, 0) as exp "
                + "FROM users u "
                + "LEFT JOIN game_states gs ON u.username = gs.username";

        try (Statement s = c.createStatement(); ResultSet r = s.executeQuery(query)) {
            System.out.println("\n=== ALL USERS ===");
            System.out.printf("%-20s %-20s %-10s %s\n", "USERNAME", "CHARACTER", "LEVEL", "EXP");
            System.out.println("-".repeat(60));

            while (r.next()) {
                String username = r.getString("username");
                String charName = r.getString("player_name");
                int level = r.getInt("level");
                int exp = r.getInt("exp");
                System.out.printf("%-20s %-20s %-10d %d\n", username, charName, level, exp);
            }
            r.close();
        } catch (SQLException e) {
            System.err.println("Error listing users: " + e.getMessage());
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static void resetDatabase() {
        Connection c = getConnection();
        if (c == null) {
            return;
        }

        try (Statement s = c.createStatement()) {
            s.execute("SET FOREIGN_KEY_CHECKS = 0");
            s.execute("DROP TABLE IF EXISTS game_states");
            s.execute("DROP TABLE IF EXISTS users");
            s.execute("SET FOREIGN_KEY_CHECKS = 1");

            System.out.println("\nDatabase reset successfully!");
            initDB(); // Re-initialize the database
        } catch (SQLException e) {
            System.err.println("Error resetting database: " + e.getMessage());
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static void showAdminMenu() {
        while (true) {
            clearScreen();
            System.out.println("\n=== ADMIN MENU ===");
            System.out.println("1. List All Users");
            System.out.println("2. Reset Database");
            System.out.println("3. MySQL Queries");
            System.out.println("4. Back to Main Menu");
            System.out.print("\nEnter choice: ");

            switch (scanner.nextLine().trim()) {
                case "1" -> {
                    listAllUsers();
                    waitForInput();
                }
                case "2" -> {
                    System.out.print("\nWARNING: This will delete ALL data! Type 'CONFIRM' to continue: ");
                    if (scanner.nextLine().equals("CONFIRM")) {
                        System.out.print("Enter admin password: ");
                        if (scanner.nextLine().equals(ADMIN_PASSWORD)) {
                            resetDatabase();
                        } else {
                            System.out.println("Invalid password!");
                        }
                        waitForInput();
                    }
                }
                case "3" ->
                    runSqlQuery();
                case "4" -> {
                    return;
                }
                default -> {
                    System.out.println("\nInvalid choice!");
                    waitForInput();
                }
            }
        }
    }

    static void runSqlQuery() {
        while (true) {
            clearScreen();
            System.out.println("=== RUN SQL QUERY (type 'back' to return) ===");
            System.out.print("MySQL [solo_leveling_user]> ");

            String query = scanner.nextLine().trim();
            if (query.equalsIgnoreCase("back")) {
                return;
            }
            if (query.endsWith(";")) {
                query = query.substring(0, query.length() - 1);
            }

            if (query.trim().isEmpty()) {
                continue;
            }

            Connection c = getConnection();
            if (c == null) {
                System.out.println("ERROR: Failed to connect to database!");
                waitForInput();
                return;
            }

            try (Statement s = c.createStatement()) {
                long startTime = System.currentTimeMillis();

                if (query.trim().toUpperCase().startsWith("SELECT")) {
                    // For SELECT queries, show results in MySQL format
                    try (ResultSet r = s.executeQuery(query)) {
                        java.sql.ResultSetMetaData meta = r.getMetaData();
                        int columns = meta.getColumnCount();
                        int[] columnWidths = new int[columns];
                        String[][] rows = new String[100][columns]; // Max 100 rows for display
                        int rowCount = 0;

                        // Get all rows first to calculate column widths
                        while (r.next() && rowCount < 100) {
                            for (int i = 1; i <= columns; i++) {
                                String value = r.getString(i) == null ? "NULL" : r.getString(i);
                                rows[rowCount][i - 1] = value;
                                columnWidths[i - 1] = Math.max(columnWidths[i - 1], Math.max(value.length(), meta.getColumnName(i).length()));
                            }
                            rowCount++;
                        }

                        // Print top border
                        System.out.print("+-");
                        for (int i = 0; i < columns; i++) {
                            System.out.print("-".repeat(columnWidths[i]) + "-+-");
                        }
                        System.out.println("\b ");

                        // Print column headers
                        System.out.print("| ");
                        for (int i = 1; i <= columns; i++) {
                            System.out.printf("%-" + columnWidths[i - 1] + "s | ", meta.getColumnName(i));
                        }
                        System.out.println("\b");

                        // Print header separator
                        System.out.print("+-");
                        for (int i = 0; i < columns; i++) {
                            System.out.print("-".repeat(columnWidths[i]) + "-+-");
                        }
                        System.out.println("\b ");

                        // Print rows
                        for (int i = 0; i < rowCount; i++) {
                            System.out.print("| ");
                            for (int j = 0; j < columns; j++) {
                                System.out.printf("%-" + columnWidths[j] + "s | ", rows[i][j]);
                            }
                            System.out.println("\b");
                        }

                        // Print bottom border
                        System.out.print("+-");
                        for (int i = 0; i < columns; i++) {
                            System.out.print("-".repeat(columnWidths[i]) + "-+-");
                        }
                        System.out.println("\b ");

                        // Print row count and execution time
                        long endTime = System.currentTimeMillis();
                        System.out.printf("%d %s in set (%.3f sec)\n\n",
                                rowCount,
                                rowCount == 1 ? "row" : "rows",
                                (endTime - startTime) / 1000.0);
                    }
                } else {
                    // For non-SELECT queries
                    int result = s.executeUpdate(query);
                    long endTime = System.currentTimeMillis();
                    System.out.printf("Query OK, %d %s affected (%.3f sec)\n\n",
                            result,
                            result == 1 ? "row" : "rows",
                            (endTime - startTime) / 1000.0);
                }
            } catch (SQLException e) {
                System.err.println("ERROR " + e.getErrorCode() + ": " + e.getMessage());
                System.out.println();
            } finally {
                try {
                    c.close();
                } catch (SQLException e) {
                }
            }

            // Wait for Enter before showing next prompt
            System.out.print("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }
}

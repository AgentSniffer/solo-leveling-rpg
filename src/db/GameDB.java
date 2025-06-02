package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GameDB {

    static final String DB_BASE_URL = "jdbc:mysql://db-mysql-sgp1-information-management-do-user-9437339-0.l.db.ondigitalocean.com:25060/";
    static final String DB_NAME = "solo_leveling_user";
    static final String DB_URL = DB_BASE_URL + DB_NAME + "?useSSL=true&requireSSL=true";
    static final String DB_USER = "solo_leveling_user";
    static final String DB_PASS = "AVNS_1D695Xh6nkrCEdFXEIg";

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

    static User authenticateUser(String name, String pass) {
        Connection c = getConnection();
        if (c == null) {
            return null;
        }

        try (PreparedStatement s = c.prepareStatement("SELECT password FROM users WHERE username = ?")) {
            s.setString(1, name);
            ResultSet r = s.executeQuery();

            if (r.next() && r.getString(1).equals(pass)) {
                r.close();
                return new User(name, pass);
            }
            r.close();
            return null;
        } catch (SQLException e) {
            System.err.println("Login failed: " + e.getMessage());
            return null;
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static boolean createUser(String name, String pass) {
        Connection c = getConnection();
        if (c == null) {
            return false;
        }

        try (PreparedStatement s = c.prepareStatement("INSERT INTO users (username, password) VALUES (?, ?)")) {
            s.setString(1, name);
            s.setString(2, pass);
            s.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Register failed: " + e.getMessage());
            return false;
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static void saveGameState(User user, PlayerData player) {
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

    static PlayerData loadGameState(User user) {
        if (user == null) {
            return null;
        }

        Connection c = getConnection();
        if (c == null) {
            return null;
        }

        try (PreparedStatement s = c.prepareStatement(
                "SELECT player_name, level, exp FROM game_states WHERE username = ?")) {

            s.setString(1, user.username);
            ResultSet r = s.executeQuery();

            if (r.next()) {
                PlayerData player = new PlayerData(
                        r.getString("player_name"),
                        r.getInt("level"),
                        r.getInt("exp")
                );
                r.close();
                return player;
            }
            r.close();
            return null;
        } catch (SQLException e) {
            System.err.println("Load failed: " + e.getMessage());
            return null;
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
            initDB();
        } catch (SQLException e) {
            System.err.println("Error resetting database: " + e.getMessage());
        } finally {
            try {
                c.close();
            } catch (SQLException e) {
            }
        }
    }

    static void runSqlQuery(String query) {
        if (query.endsWith(";")) {
            query = query.substring(0, query.length() - 1);
        }

        if (query.trim().isEmpty()) {
            return;
        }

        Connection c = getConnection();
        if (c == null) {
            System.out.println("ERROR: Failed to connect to database!");
            return;
        }

        try (Statement s = c.createStatement()) {
            long startTime = System.currentTimeMillis();

            if (query.trim().toUpperCase().startsWith("SELECT")) {
                try (ResultSet r = s.executeQuery(query)) {
                    java.sql.ResultSetMetaData meta = r.getMetaData();
                    int columns = meta.getColumnCount();
                    int[] columnWidths = new int[columns];
                    String[][] rows = new String[100][columns];
                    int rowCount = 0;

                    while (r.next() && rowCount < 100) {
                        for (int i = 1; i <= columns; i++) {
                            String value = r.getString(i) == null ? "NULL" : r.getString(i);
                            rows[rowCount][i - 1] = value;
                            columnWidths[i - 1] = Math.max(columnWidths[i - 1], Math.max(value.length(), meta.getColumnName(i).length()));
                        }
                        rowCount++;
                    }

                    System.out.print("+-");
                    for (int i = 0; i < columns; i++) {
                        System.out.print("-".repeat(columnWidths[i]) + "-+-");
                    }
                    System.out.println("\b ");

                    System.out.print("| ");
                    for (int i = 1; i <= columns; i++) {
                        System.out.printf("%-" + columnWidths[i - 1] + "s | ", meta.getColumnName(i));
                    }
                    System.out.println("\b");

                    System.out.print("+-");
                    for (int i = 0; i < columns; i++) {
                        System.out.print("-".repeat(columnWidths[i]) + "-+-");
                    }
                    System.out.println("\b ");

                    for (int i = 0; i < rowCount; i++) {
                        System.out.print("| ");
                        for (int j = 0; j < columns; j++) {
                            System.out.printf("%-" + columnWidths[j] + "s | ", rows[i][j]);
                        }
                        System.out.println("\b");
                    }

                    System.out.print("+-");
                    for (int i = 0; i < columns; i++) {
                        System.out.print("-".repeat(columnWidths[i]) + "-+-");
                    }
                    System.out.println("\b ");

                    long endTime = System.currentTimeMillis();
                    System.out.printf("%d %s in set (%.3f sec)\n\n",
                            rowCount,
                            rowCount == 1 ? "row" : "rows",
                            (endTime - startTime) / 1000.0);
                }
            } else {
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
    }
}

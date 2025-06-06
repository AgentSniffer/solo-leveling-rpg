package db;

import utils.DBUtil;

public class SchemaDB extends DBUtil {
    
    public static void setup() {
        loadDatabaseConfig();
        connect();
        initializeSchema();
    }

    public static void initializeSchema() {

        // First create the database if it doesn't exist
        executeMySQL("CREATE DATABASE IF NOT EXISTS Solo_Leveling_RPG");
        executeMySQL("USE Solo_Leveling_RPG");

        String createUsersTable = """
            CREATE TABLE IF NOT EXISTS users (
                user_id INT AUTO_INCREMENT PRIMARY KEY,
                email VARCHAR(255) NOT NULL UNIQUE,
                username VARCHAR(255) UNIQUE,
                password_hash VARCHAR(255) NOT NULL,
                salt VARCHAR(255) NOT NULL,
                created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
            );
            """;

        String createPlayersTable = """
            CREATE TABLE IF NOT EXISTS players (
                player_id INT AUTO_INCREMENT PRIMARY KEY,
                user_id INT NOT NULL,
                player_name VARCHAR(255) NOT NULL,
                level INT DEFAULT 1,
                exp INT DEFAULT 0,
                FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
            );
            """;

        executeMySQL(createUsersTable);
        executeMySQL(createPlayersTable);
    }

    public static void resetSchema() {
        executeMySQL("DROP TABLE IF EXISTS players");
        executeMySQL("DROP TABLE IF EXISTS users");
    }
}

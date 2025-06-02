-- Drop database if exists (for clean setup)
DROP DATABASE IF EXISTS solo_leveling_user;

-- Create database
CREATE DATABASE solo_leveling_user;
USE solo_leveling_user;

-- Users table (1st Normal Form - Atomic values)
CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(100) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Game states table (2nd Normal Form - Depends on key)
CREATE TABLE game_states (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    player_name VARCHAR(100) NOT NULL,
    level INT NOT NULL DEFAULT 1,
    exp INT NOT NULL DEFAULT 0,
    last_saved TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE,
    UNIQUE KEY unique_username (username)
);

-- Future tables can be added here following 3NF
-- For example, if you want to track player inventory:
/*
CREATE TABLE inventory (
    id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    item_name VARCHAR(100) NOT NULL,
    quantity INT NOT NULL DEFAULT 1,
    FOREIGN KEY (username) REFERENCES users(username) ON DELETE CASCADE
);
*/

-- Add initial admin user (password: admin123)
INSERT INTO users (username, password) VALUES ('admin', 'admin123');

// 1. Connection - Manages the database connection
// Without this: You can't connect to any database
// Error: Cannot resolve symbol 'Connection'
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseBasics {

    public static void main(String[] args) {
        // ===== 1. DATABASE CONNECTION DETAILS =====
        // Format: jdbc:mysql://<host>:<port>/
        String url = "jdbc:mysql://localhost:3306/";  // Requires: java.sql.DriverManager
        String user = "root";                         // Requires: java.sql.DriverManager
        String password = "password";                 // Requires: java.sql.DriverManager

        // ===== 2. ESTABLISH CONNECTION =====
        // try-with-resources ensures resources are closed automatically
        try (Connection conn = DriverManager.getConnection(url, user, password); // Requires: java.sql.Connection, java.sql.DriverManager
                 Statement stmt = conn.createStatement()) {  // Requires: java.sql.Statement

            System.out.println("✅ Connected to database server!");

            // ===== 3. CREATE DATABASE =====
            // executeUpdate() is used for: CREATE, ALTER, DROP, INSERT, UPDATE, DELETE
            stmt.executeUpdate("CREATE DATABASE IF NOT EXISTS school");  // Requires: java.sql.Statement
            System.out.println("✅ Database 'school' ready!");

            // ===== 4. SELECT DATABASE =====
            stmt.executeUpdate("USE school");  // Requires: java.sql.Statement

            // ===== 5. CREATE TABLE =====
            String createTableSQL
                    = "CREATE TABLE IF NOT EXISTS students ("
                    + "id INT AUTO_INCREMENT PRIMARY KEY, "
                    + "name VARCHAR(50) NOT NULL, "
                    + "age INT, "
                    + "grade CHAR(1)"
                    + ")";
            stmt.executeUpdate(createTableSQL);  // Requires: java.sql.Statement
            System.out.println("✅ Table 'students' ready!");

            // ===== 6. INSERT DATA =====
            stmt.executeUpdate("INSERT INTO students (name, age, grade) VALUES ('Alice', 20, 'A')");  // Requires: java.sql.Statement
            stmt.executeUpdate("INSERT INTO students (name, age, grade) VALUES ('Bob', 22, 'B')");    // Requires: java.sql.Statement
            System.out.println("✅ Added sample data!");

            // ===== 7. QUERY DATA =====
            // executeQuery() is used for SELECT statements
            System.out.println("\n📋 List of students:");
            ResultSet rs = stmt.executeQuery("SELECT * FROM students");  // Requires: java.sql.ResultSet

            // Process the result set
            while (rs.next()) {  // Requires: java.sql.ResultSet
                int id = rs.getInt("id");              // Requires: java.sql.ResultSet
                String name = rs.getString("name");     // Requires: java.sql.ResultSet
                int age = rs.getInt("age");            // Requires: java.sql.ResultSet
                String grade = rs.getString("grade");   // Requires: java.sql.ResultSet

                System.out.printf("ID: %d, Name: %s, Age: %d, Grade: %s%n",
                        id, name, age, grade);
            }

            // ===== 8. UPDATE DATA =====
            stmt.executeUpdate("UPDATE students SET grade = 'A+' WHERE name = 'Alice'");  // Requires: java.sql.Statement
            System.out.println("\n✅ Updated Alice's grade!");

            // ===== 9. DELETE DATA =====
            stmt.executeUpdate("DELETE FROM students WHERE name = 'Bob'");  // Requires: java.sql.Statement
            System.out.println("✅ Removed Bob from records!");
        } catch (SQLException e) {  // Requires: java.sql.SQLException
            // Handle any database errors
            System.err.println("❌ Database error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

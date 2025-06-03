import db.GameDB;
import ui.UIManager;

public class Main {
    public static void main(String[] args) {
        try {
            // Initialize database
            Class.forName("com.mysql.cj.jdbc.Driver");
            GameDB.ensureDatabaseExists();
            GameDB.initDB();
            
            // Initialize and start UI
            UIManager uiManager = UIManager.getInstance();
            uiManager.showLogin();
            uiManager.start();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

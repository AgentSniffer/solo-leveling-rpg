
import db.GameDB;
import ui.GameUI;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            GameDB.ensureDatabaseExists();
            GameDB.initDB();
            GameUI.showLoginMenu();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

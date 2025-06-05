
import cli.LoginCli;
import db.SchemaDB;
public class Main {

    public static void main(String[] args) {
        SchemaDB.initializeSchema();
        LoginCli.display();
    }
}

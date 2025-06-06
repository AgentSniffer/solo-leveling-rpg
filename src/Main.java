
import cli.LoginCli;
import db.SchemaDB;
public class Main {

    public static void main(String[] args) {
        SchemaDB.setup();
        LoginCli.display();
    }
}

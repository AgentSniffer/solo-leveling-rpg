
import cli.LoginCli;
import utils.DBUtil;

public class Main {

    public static void main(String[] args) {
        DBUtil.initialize();
        LoginCli.display();
    }
}

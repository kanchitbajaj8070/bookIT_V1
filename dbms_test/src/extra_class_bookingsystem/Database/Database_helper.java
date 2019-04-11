package extra_class_bookingsystem.Database;

import extra_class_bookingsystem.Alert_maker.Alert_handler;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database_helper {

    private static Database_helper handler = null;

    private static Connection conn = null;
    private static Statement stmt = null;
    static {
        createConnection();
    }

    private Database_helper() {

    }
    public static Database_helper getInstance() {
        if (handler == null) {
            handler = new Database_helper();
        }
        return handler;
    }
    private static void createConnection() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conn= DriverManager.getConnection(
                    "jdbc:mysql://bookit1.clpnl0p1qjqz.us-east-2.rds.amazonaws.com:3306/bookit1", "root", "db2admin");
        }
        catch (Exception e) {
        Alert_handler.showErrorMessage("cant load database","can not connect to database",null);
        }
    }
    public Connection getConnection() {
        return conn;
    }
    public static void closeConnection() throws SQLException {
        conn.close();
    }
}

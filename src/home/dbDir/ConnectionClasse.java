package home.dbDir;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClasse {

    private final static String HOST_NAME = "127.0.0.1";
    private final static String DB_NAME = "creche_dar_elhadith";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private final static String PORT = "3306";
    private final static String timeZone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection = null;

    public Connection getConnection() {
        return getConnection(HOST_NAME, PORT, DB_NAME, USERNAME, PASSWORD);
    }

    public Connection getConnection(String host,String port, String dbname, String user, String passeword) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ((port.isEmpty()) ? "" : ":" + port) +"/"+dbname+ timeZone, user, passeword);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}

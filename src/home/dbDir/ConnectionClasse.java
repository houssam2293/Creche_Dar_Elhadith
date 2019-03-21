package home.dbDir;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionClasse {
    private static final String port = "3306";
    private static final String timeZone = "/?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection = null;

    public Connection getConnecction(String dbname, String host, String user, String passeword) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + timeZone, user, passeword);
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

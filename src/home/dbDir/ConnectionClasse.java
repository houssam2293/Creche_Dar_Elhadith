package home.dbDir;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionClasse {

    private final static String HOST_NAME = "127.0.0.1";
    private final static String DB_NAME = "creche_dar_elhadith";
    private final static String USERNAME = "root";
    private final static String PASSWORD = "root";
    private final static String PORT = "3306";
    private final static String timeZone = "?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
    private Connection connection = null;
    private Path currentRelativePath = Paths.get("");
    private String s = currentRelativePath.toAbsolutePath().toString();
    private final String filename = (s + "/dbCredentials.properties");
    private String hst, user, pass;

    public Connection getConnection() {
        Properties properties = new Properties();

        boolean fileRecovered = false;
        try {
            InputStream input = new FileInputStream(filename);
            fileRecovered = true;
            properties.load(input);
            hst = properties.getProperty("database");
            user = properties.getProperty("dbuser");
            pass = properties.getProperty("dbpassword");
        } catch (IOException e) {
            fileRecovered = false;
            e.printStackTrace();
        }
        if (fileRecovered) {
            return getConnection(hst, PORT, DB_NAME, user, pass);
        } else
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

    public void shutdown(Connection connection) throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }
}

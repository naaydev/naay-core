package naay.dev.core.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class Database {

    private static Connection connection;
    private static String host, port, database, user, password;

    public static void setup(String host, String port, String database, String user, String password) {
        Database.host = host;
        Database.port = port;
        Database.database = database;
        Database.user = user;
        Database.password = password;

        try {
            connection = DriverManager.getConnection(
                "jdbc:mysql://" + host + ":" + port + "/" + database + "?useSSL=false",
                user, password
            );
            createTables();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createTables() {
        execute("CREATE TABLE IF NOT EXISTS players (id INT AUTO_INCREMENT PRIMARY KEY, nick VARCHAR(16) UNIQUE, uuid VARCHAR(36), coins INT DEFAULT 0, cash INT DEFAULT 0, wins INT DEFAULT 0, kills INT DEFAULT 0, deaths INT DEFAULT 0, level INT DEFAULT 1, xp INT DEFAULT 0, lastlogin LONG)");
    }

    public static Connection getConnection() {
        return connection;
    }

    public static void close() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static java.sql.PreparedStatement prepare(String sql) {
        try {
            return connection.prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void execute(String sql) {
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
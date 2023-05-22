package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DB_Connection {
    private static Connection connection;
    private static volatile DB_Connection instance;
    private static final String URL = "jdbc:mysql://localhost:3306/proiect_pao";

    private static final String userName = "root";

    private static final String password = "root";

    private DB_Connection() {
        try {
            connection = DriverManager.getConnection(URL, userName, password);
        } catch (SQLException e) {
            System.out.println("Connection failed. " + e.getMessage());
        }
    }

    public static Connection getInstance() throws SQLException{
        if (connection == null) {
            instance = new DB_Connection();
        }
        return instance.connection;
    }
}

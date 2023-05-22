package persistence;

import database.DB_Connection;

import java.sql.Connection;
import java.sql.SQLException;

public class BaseRepository {
    protected Connection db;

    public BaseRepository() {
        try {
            this.db = DB_Connection.getInstance();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
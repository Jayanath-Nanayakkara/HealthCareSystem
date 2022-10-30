package lk.ijse.healthcare.db;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    private static DatabaseConnection databaseConnection;
    private Connection connection;

    private DatabaseConnection() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        connection= DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/healthcare",
                "root","1234"
        );
    }
    public static DatabaseConnection getInstance() throws SQLException, ClassNotFoundException {
        return databaseConnection==null?
                databaseConnection= new DatabaseConnection():
                databaseConnection;
    }
    public Connection getConnection(){
        return connection;
    }

}

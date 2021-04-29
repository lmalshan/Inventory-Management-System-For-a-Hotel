package DatabaseLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnection {

    private String URL = "jdbc:sqlserver://localhost:1433;databaseName=InventoryDBB;user=sa;password=sa123";
    private Connection conn;

    public DatabaseConnection() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            conn = DriverManager.getConnection(URL);

        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex.getMessage());
        }

    }

    public boolean Insert(String query) {
        try {
            Statement statement = conn.createStatement();
            int results = statement.executeUpdate(query);
            boolean out = (results > 0);
            return out;

        } catch (SQLException ex) {
            System.out.println("" + ex.getMessage());
            return false;
        }
    }

}

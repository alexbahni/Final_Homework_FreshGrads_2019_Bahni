import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.*;
import java.util.Properties;

public class DBConnection {
    ReadPropertyFile properties;

    public String makeDBConnection(String dbUrl, String dbUser, String dbPassword, String query) {
        Connection connectionDB = null;
        Statement statementDB = null;

        try {
            connectionDB = DriverManager.getConnection(dbUrl, dbUser, dbPassword);

            statementDB = connectionDB.createStatement();

            ResultSet resultSet = statementDB.executeQuery(query);
            while (resultSet.next()) {
                return resultSet.getString("username");
            }
            resultSet.close();
            statementDB.close();
            connectionDB.close();
        } catch (SQLException e) {
            System.out.println("Exception: " + e);
        } finally {
            if (connectionDB != null) {
                try {
                    connectionDB.close();
                } catch (SQLException e) {
                    System.out.println("Exception: " + e);
                }
            }
        }
        return "no user found";
    }
}

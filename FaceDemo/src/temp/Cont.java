package temp;

import java.sql.*;

public class Cont {

    private static Connection connection;
    static Statement statement;

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement() throws SQLException {
        try {
            statement = connection.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return statement;
    }

    public static ResultSet getResultset(String query) throws SQLException {
        statement = getStatement();
        ResultSet rs = statement.executeQuery(query);
        return rs;
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/face", "root", "MePushkar@sql#193?PW");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
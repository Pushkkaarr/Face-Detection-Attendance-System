package temp;

import java.sql.*;

public class Cont {

    private static Connection connection;
    static Statement statement;

    public static Connection getConnection() {
    if (connection == null) {
            initializeConnection();
        }
        return connection;
    }

    private static void initializeConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/face", "root", "MePushkar@sql#193?PW");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static Statement getStatement() throws SQLException {
        statement = connection.createStatement();
        return statement;
    }
    
    
   public static byte[] getFaceImageFromDatabase(String userId) {
    byte[] image = null;
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/face", "root", "MePushkar@sql#193?PW");
        Statement statement = getStatement();
         ResultSet resultSet = statement.executeQuery("SELECT FaceImage FROM teacherdetails WHERE TeacherID = '" + userId + "'")) {
        if (resultSet.next()) {
            image = resultSet.getBytes("FaceImage");
        }
        resultSet.close();
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return image;
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
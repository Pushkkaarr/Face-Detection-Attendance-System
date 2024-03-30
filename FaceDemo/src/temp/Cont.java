package temp;

import java.sql.*;

public class Cont {

    private static Connection connection;
    static Statement statement;

    public static Connection getConnection() {
        return connection;
    }

    public static Statement getStatement(Connection connection) throws SQLException {
        try {
              if (connection != null) {
        return connection.createStatement();
    } else {
                  
        throw new SQLException("Connection object is null.");
    }
            //statement = connection.createStatement();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return statement;
    }
    
   public static byte[] getFaceImageFromDatabase(String userId) {
    byte[] image = null;
    try (Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/face", "root", "MePushkar@sql#193?PW");
        Statement statement = getStatement(connection);
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
        statement = getStatement(connection);
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
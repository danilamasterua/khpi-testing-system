package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.model.beans.UserPoints;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserPointsDAO {
    private static final String SQL_INSERT_USERPOINT="insert into userpoints(testId, userId, points, datetime) VALUES (?,?,?,?)";

    public static void insertUserpoint(UserPoints up){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_USERPOINT);
            statement.setInt(1, up.getTestId());
            statement.setInt(2, up.getUserId());
            statement.setDouble(3, up.getPoints());
            statement.setTimestamp(4, Timestamp.valueOf(up.getDateTime()));
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }
}

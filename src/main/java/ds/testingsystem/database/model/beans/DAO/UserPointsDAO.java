package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.Fields;
import ds.testingsystem.database.model.User;
import ds.testingsystem.database.model.beans.UserPoints;
import ds.testingsystem.database.model.beans.UserTestPoints;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class UserPointsDAO {
    private static final String SQL_INSERT_USERPOINT="insert into userpoints(testId, userId, points, datetime) VALUES (?,?,?,?)";
    private static final String SQL_GET_USERPOINT = "select user.userId, user.firstName, user.lastName, user.login, user.password, user.roleId, user.email, user.groupId, userpoints.points, userpoints.datetime " +
            "from user right join userpoints on user.userId=userpoints.userId " +
            "where user.groupId=? and userpoints.testId=?";
    private static final String SQL_DELETE_USERPOINT = "delete from userpoints where testId=? and userId=?";

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
    public static LinkedList<UserTestPoints> getUsersPoints(int groupId, int testId){
        LinkedList<UserTestPoints> utp = new LinkedList<>();
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_USERPOINT);
            statement.setInt(1, groupId);
            statement.setInt(2, testId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                User user = new User(rs.getInt(Fields.userId),
                        rs.getString(Fields.userFirstName),
                        rs.getString(Fields.userLastName),
                        rs.getString(Fields.userLogin),
                        rs.getString(Fields.userPassword),
                        rs.getInt(Fields.userRoleId),
                        rs.getString(Fields.userEmail));
                Double points = rs.getDouble("points");
                LocalDateTime ldt = rs.getTimestamp("datetime").toLocalDateTime();
                utp.add(new UserTestPoints(user,points, ldt));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
        return utp;
    }
    public static void deleteUserPoint(int testId, int userId){
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_DELETE_USERPOINT);
            statement.setInt(1, testId);
            statement.setInt(2, userId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
    }
}

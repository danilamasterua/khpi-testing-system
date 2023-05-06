package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.model.beans.GroupAccess;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class GroupAccessDAO {
    private static final String SQL_INSERT_GROUPACCESS = "insert into groupaccess(groupId, testId, accStTime, accFinTime) values (?,?,?,?)";
    private static final String SQL_REMOVE_GROUPACCESS = "delete from groupaccess where testId=? and groupId=?";

    public static void grantAccess(GroupAccess ga){
        Connection con = null;
        try {
            con= Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_GROUPACCESS);
            statement.setInt(1, ga.getGroupId());
            statement.setInt(2, ga.getTestId());
            statement.setTimestamp(3, Timestamp.valueOf(ga.getAccStTime()));
            statement.setTimestamp(4, Timestamp.valueOf(ga.getAccFinTime()));
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }
    public static void denyAccess(int groupId, int testId){
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_REMOVE_GROUPACCESS);
            statement.setInt(1, testId);
            statement.setInt(2, groupId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }
}

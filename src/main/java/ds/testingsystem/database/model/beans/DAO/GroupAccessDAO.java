package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.EntityMapper;
import ds.testingsystem.database.Fields;
import ds.testingsystem.database.model.beans.GroupAccess;

import java.sql.*;
import java.time.LocalDateTime;

public class GroupAccessDAO {
    private static final String SQL_INSERT_GROUPACCESS = "insert into groupaccess(groupId, testId, accStTime, accFinTime, minToFin) values (?,?,?,?,?)";
    private static final String SQL_REMOVE_GROUPACCESS = "delete from groupaccess where testId=? and groupId=?";
    private static final String SQL_GET_ACCESS_DATA_BY_TEST = "select * from groupaccess where testId=? and groupId=?";

    public static void grantAccess(GroupAccess ga){
        Connection con = null;
        try {
            con= Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_GROUPACCESS);
            statement.setInt(1, ga.getGroupId());
            statement.setInt(2, ga.getTestId());
            if(ga.getAccStTime()!=null&&ga.getAccFinTime()!=null) {
                statement.setTimestamp(3, Timestamp.valueOf(ga.getAccStTime()));
                statement.setTimestamp(4, Timestamp.valueOf(ga.getAccFinTime()));
            } else {
                statement.setTimestamp(3, null);
                statement.setTimestamp(4, null);
            }
            statement.setInt(5, ga.getMinToFin());
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
    public static GroupAccess getGroupAccessDataTest(int testId, int groupId){
        GroupAccess ga = new GroupAccess();
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_ACCESS_DATA_BY_TEST);
            statement.setInt(1, testId);
            statement.setInt(2, groupId);
            ResultSet rs = statement.executeQuery();
            GroupAccessMapper mapper = new GroupAccessMapper();
            while (rs.next()){
                ga = mapper.mapRow(rs);
            }
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return ga;
    }
}

class GroupAccessMapper implements EntityMapper<GroupAccess>{
    @Override
    public GroupAccess mapRow(ResultSet rs) {
        GroupAccess ga = new GroupAccess();
        try {
            ga.setTestId(rs.getInt(Fields.testId));
            ga.setGroupId(rs.getInt(Fields.groupId));
            if(rs.getTimestamp("accStTime")!=null&&rs.getTimestamp("accFinTime")!=null) {
                ga.setAccStTime(rs.getTimestamp("accStTime").toLocalDateTime());
                ga.setAccFinTime(rs.getTimestamp("accFinTime").toLocalDateTime());
            }
            ga.setMinToFin(rs.getInt(Fields.testMinToFin));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return ga;
    }
}

package ds.testingsystem.database;

import ds.testingsystem.database.model.Group;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class GroupDAO {
    private static final String SQL_GET_GROUPS_ACCESSED = "select * from edgroup where edgroup.groupId in (select groupId from groupaccess where testId=?)";
    private static final String SQL_GET_GROUPS_NOT_ACCESSED = "select * from edgroup where edgroup.groupId not in (select groupId from groupaccess where testId=?)";
    public static HashMap<Integer, Group> getAccessedGroup(int testId, boolean accessedGroups){
        HashMap<Integer, Group> retMap = new HashMap<>();
        Connection con = null;
        try{
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = null;
            if(accessedGroups){
                statement = con.prepareStatement(SQL_GET_GROUPS_ACCESSED);
            } else {
                statement = con.prepareStatement(SQL_GET_GROUPS_NOT_ACCESSED);
            }
            statement.setInt(1, testId);
            ResultSet rs = statement.executeQuery();
            GroupMapper mapper = new GroupMapper();
            while (rs.next()){
                retMap.put(rs.getInt(Fields.groupId), mapper.mapRow(rs));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return retMap;
    }
}
class GroupMapper implements EntityMapper<Group>{
    @Override
    public Group mapRow(ResultSet rs) {
        Group group = new Group();
        try {
            group.setName(rs.getString(Fields.groupName));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return group;
    }
}

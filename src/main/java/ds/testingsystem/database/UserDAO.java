package ds.testingsystem.database;

import ds.testingsystem.database.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private static final String SQL_GET_USER_INFO = "select * from user where login=?";

    public static User getUserInfo(String login) throws SQLException{
        User user = new User();
        try (Connection con = Connect.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(SQL_GET_USER_INFO);
            statement.setString(1, login);
            ResultSet resultSet = statement.executeQuery();
            UserMapper mp = new UserMapper();
            while (resultSet.next()){
                user = mp.mapRow(resultSet);
            }
        } catch (SQLException ex){
            Connect.getInstance().rollbackAndClose(Connect.getInstance().getConnection());
            ex.printStackTrace();
        } finally {
            Connect.getInstance().commitAndClose(Connect.getInstance().getConnection());
        }
        return user;
    }
}

class UserMapper implements EntityMapper<User>{
    public User mapRow(ResultSet rs){
        User retUser = new User();
        try{
            retUser.setUserId(rs.getInt(Fields.userId));
            retUser.setFirstName(rs.getString(Fields.userFirstName));
            retUser.setLastName(rs.getString(Fields.userLastName));
            retUser.setLogin(rs.getString(Fields.userLogin));
            retUser.setPassword(rs.getString(Fields.userPassword));
            retUser.setRoleId(rs.getInt(Fields.userRoleId));
            retUser.setEmail(rs.getString(Fields.userEmail));
            retUser.setGroupId(rs.getInt("groupId"));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return retUser;
    }
}



package ds.testingsystem.database;

import ds.testingsystem.database.model.User;

import java.sql.*;
import java.util.HashMap;

public class UserDAO {
    private static final String SQL_GET_USER_INFO = "select * from user where login=? and blockstatus=0";
    private static final String SQL_GET_USERS_TEACHERS = "select * from user where roleId=2 and blockstatus=0";
    private static final String SQL_INSERT_NEW_USER = "insert into user(firstName, lastName, login, password, roleId, email, groupId) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_GET_USERS_BY_GROUP = "select * from user where groupId=? and blockstatus=0";

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

    public static HashMap<Integer, User> getTeachers(){
        HashMap<Integer, User> teachers = new HashMap<>();
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_GET_USERS_TEACHERS);
            UserMapper mapper = new UserMapper();
            while (rs.next()){
                teachers.put(rs.getInt(Fields.userId), mapper.mapRow(rs));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
        return teachers;
    }
    public static int addUser(User user){
        Connection con = null;
        int ret = -1;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_NEW_USER, PreparedStatement.RETURN_GENERATED_KEYS);
            int k=1;
            statement.setString(k++, user.getFirstName());
            statement.setString(k++, user.getLastName());
            statement.setString(k++, user.getLogin());
            statement.setString(k++, user.getPassword());
            statement.setInt(k++, user.getRoleId());
            statement.setString(k++, user.getEmail());
            statement.setInt(k, user.getGroupId());
            statement.executeUpdate();
            ResultSet rs = statement.getGeneratedKeys();
            while (rs.next()){
                ret=rs.getInt(1);
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e) {
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
        return ret;
    }
    public static HashMap<Integer, User> getUsersByGroup(int groupId){
        HashMap<Integer, User> users = new HashMap<>();
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_USERS_BY_GROUP);
            statement.setInt(1, groupId);
            ResultSet rs= statement.executeQuery();
            UserMapper mapper = new UserMapper();
            while (rs.next()){
                users.put(rs.getInt(Fields.userId), mapper.mapRow(rs));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
        return users;
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



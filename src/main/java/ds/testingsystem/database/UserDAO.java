package ds.testingsystem.database;

import ds.testingsystem.database.model.User;
import ds.testingsystem.database.model.beans.ChangePassword;

import java.sql.*;
import java.util.HashMap;

public class UserDAO {
    private static final String SQL_GET_USER_INFO = "select * from user where login=? and blockstatus=0";
    private static final String SQL_GET_USERS_TEACHERS = "select * from user where roleId=2 and blockstatus=0";
    private static final String SQL_INSERT_NEW_USER = "insert into user(firstName, lastName, login, password, roleId, email, groupId) VALUES (?,?,?,?,?,?,?)";
    private static final String SQL_GET_USERS_BY_GROUP = "select * from user where groupId=? and blockstatus=0";
    private static final String SQL_GET_USER_BY_ID = "select * from user where userId=?";
    private static final String SQL_UPDATE_USER = "update user set firstName=?, lastName=?, email=? where userId=?";
    private static final String SQL_BLOCK_USER = "update user set blockstatus=1 where userId=?";
    private static final String SQL_GET_BLOCKED_USERS = "select * from user where blockstatus=1";
    private static final String SQL_UNBLOCK_USER = "update user set blockstatus=0 where userId=?";
    private static final String SQL_PREPARE_CHANGE_PASSWORD = "insert into chng_password(userId, ver_code, new_password) values (?,?,?)";
    private static final String SQL_GET_PREPARED_CHANGE_PASSWORD = "select * from chng_password where userId=? order by datetime desc limit 1";
    private static final String SQL_SET_NEW_PASSWORD = "update user set password=? where userId=?";

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

    public static User getUserById(int userId){
        User user = new User();
        Connection con=null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_USER_BY_ID);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            UserMapper mapper = new UserMapper();
            while (rs.next()){
                user = mapper.mapRow(rs);
            }
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return user;
    }
    public static void updateUser(User user){
        Connection con=null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_UPDATE_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getLastName());
            statement.setString(3, user.getEmail());
            statement.setInt(4, user.getUserId());
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
    }
    public static void blockUser(int userId){
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_BLOCK_USER);
            statement.setInt(1, userId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e) {
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
    }
    public static HashMap<Integer, User> getBlockedUsers(){
        HashMap<Integer, User> users = new HashMap<>();
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_BLOCKED_USERS);
            UserMapper mapper = new UserMapper();
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                users.put(rs.getInt(Fields.userId), mapper.mapRow(rs));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return users;
    }
    public static void unblockUser(int userId){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_UNBLOCK_USER);
            statement.setInt(1, userId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().commitAndClose(con);
            e.printStackTrace();
        }
    }
    public static int prepareChangePassword(ChangePassword chgPwd){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_PREPARE_CHANGE_PASSWORD);
            statement.setInt(1, chgPwd.getUserId());
            statement.setString(2, chgPwd.getVerCode());
            statement.setString(3, chgPwd.getNewPassword());
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
            return 200;
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
            return 500;
        }
    }
    public static ChangePassword getPreparedChangePassword(int userId){
        Connection con=null;
        ChangePassword cp = new ChangePassword();
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_PREPARED_CHANGE_PASSWORD);
            ChangePasswordMapper mapper = new ChangePasswordMapper();
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                cp = mapper.mapRow(rs);
            }
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return cp;
    }
    public static int setNewPassword(int userId, String password){
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_SET_NEW_PASSWORD);
            statement.setString(1, password);
            statement.setInt(2, userId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
            return 200;
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
            return 500;
        }
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

class ChangePasswordMapper implements EntityMapper<ChangePassword>{
    @Override
    public ChangePassword mapRow(ResultSet rs) {
        ChangePassword cp = new ChangePassword();
        try {
            cp.setUserId(rs.getInt(Fields.userId));
            cp.setVerCode(rs.getString("ver_code"));
            cp.setNewPassword(rs.getString("new_password"));
            cp.setLocalDateTime(rs.getTimestamp("datetime").toLocalDateTime());
        } catch (SQLException e){
            e.printStackTrace();
        }
        return cp;
    }
}



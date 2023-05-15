package ds.testingsystem.database;

import ds.testingsystem.database.model.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class ModuleDAO {
    private static final String SQL_GET_MODULES_FROM_TEST = "select * from module where testId=?";
    private static final String SQL_INSERT_MODULE = "insert into module(testId, description, qCount) VALUES (?,?,?)";
    private static final String SQL_GET_MODULE = "SELECT * FROM MODULE WHERE moduleId=?";
    private static final String SQL_UPDATE_MODULE = "update module set description=?, qCount=? where moduleId=?";
    private static final String SQL_DELETE_MODULE = "delete from module where moduleId=?";

    public static HashMap<Integer, Module> getModulesFromTest(int testId) throws SQLException{
        HashMap<Integer, Module> hashMap = new HashMap<>();
        try (Connection con = Connect.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(SQL_GET_MODULES_FROM_TEST);
            statement.setInt(1, testId);
            ResultSet resultSet = statement.executeQuery();
            ModuleMapper mapper = new ModuleMapper();
            while (resultSet.next()){
                hashMap.put(resultSet.getInt(Fields.moduleId), mapper.mapRow(resultSet));
            }
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(Connect.getInstance().getConnection());
            e.printStackTrace();
        } finally {
            Connect.getInstance().commitAndClose(Connect.getInstance().getConnection());
        }
        return hashMap;
    }

    public static void insertModule(int testId, Module module){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_MODULE);
            statement.setInt(1, testId);
            statement.setString(2, module.getDescription());
            statement.setInt(3, module.getqCount());
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }
    public static Module getModule(int moduleId){
        Connection con = null;
        Module module = new Module();
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_MODULE);
            statement.setInt(1, moduleId);
            ResultSet rs = statement.executeQuery();
            ModuleMapper mapper = new ModuleMapper();
            while (rs.next()){
                module = mapper.mapRow(rs);
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return module;
    }

    public static void updateModule(int moduleId, Module module){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_UPDATE_MODULE);
            statement.setString(1, module.getDescription());
            statement.setInt(2, module.getqCount());
            statement.setInt(3, moduleId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
    }

    public static void deleteModule(int moduleId){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_DELETE_MODULE);
            statement.setInt(1, moduleId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
    }
}
class ModuleMapper implements EntityMapper<Module>{
    @Override
    public Module mapRow(ResultSet rs) {
        Module retModule = new Module();
        try {
            retModule.setDescription(rs.getString(Fields.moduleDescription));
            retModule.setqCount(rs.getInt(Fields.moduleQCount));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return retModule;
    }
}

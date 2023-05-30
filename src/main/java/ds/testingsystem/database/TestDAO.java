package ds.testingsystem.database;

import ds.testingsystem.database.model.Test;
import ds.testingsystem.database.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class TestDAO {
    private static final String SQL_GET_AVAILABLE_TESTS_FOR_USER = "select test.testId, test.name, test.description, test.userId" +
            " from test inner join groupaccess on test.testId = groupaccess.testId " +
            " where groupaccess.groupId in (select groupId from user where userId=?) and" +
            " test.testId not in (select testId from userpoints where userId=?)  and " +
            " test.testId in (select testId from groupaccess where groupaccess.accFinTime<now() or groupaccess.accFinTime is null)" +
            " and test.blockstatus=0";
    private static final String SQL_GET_PASSED_TEST = "select test.testId, test.name, test.description, test.userId, userpoints.points " +
            " from userpoints left join test on userpoints.testId=test.testId " +
            " where userpoints.userId = ? order by userpoints.datetime desc";
    private static final String SQL_GET_TEST_INFO_BY_ID = "select * from test where testId = ?";
    private static final String SQL_GET_TEST_BY_USER_ID = "select * from test where userId=? and blockstatus=0";
    private static final String SQL_GET_BLOCKED_TEST_BY_USER_ID = "select * from test where userId=? and blockstatus=0";
    private static final String SQL_INSERT_TEST_DATA = "insert into test(name, description, userId) values (?,?,?)";
    private static final String SQL_DELETE_TEST="delete from test where testId=?";
    private static final String SQL_UPDATE_TEST="UPDATE test set name=?, description=? where testId=?";
    private static final String SQL_BLOCK_TEST="UPDATE test set blockstatus=1 where testId=?";
    private static final String SQL_UNBLOCK_TEST="UPDATE test set blockstatus=0 where testId=?";

    public static HashMap<Integer, Test> getAvailableTest(User user) throws SQLException{
        HashMap<Integer, Test> retMap = new HashMap<>();
        try (Connection con = Connect.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(SQL_GET_AVAILABLE_TESTS_FOR_USER);
            statement.setInt(1, user.getUserId());
            statement.setInt(2, user.getUserId());
            ResultSet resultSet = statement.executeQuery();
            TestMapper mapper = new TestMapper();
            while (resultSet.next()){
                retMap.put(resultSet.getInt(Fields.testId), mapper.mapRow(resultSet));
            }
        } catch (SQLException ex){
            Connect.getInstance().rollbackAndClose(Connect.getInstance().getConnection());
            ex.printStackTrace();
        } finally{
            Connect.getInstance().commitAndClose(Connect.getInstance().getConnection());
        }
        return retMap;
    }
    public static HashMap<Test, Double> getPassedTest(User user){
        HashMap<Test, Double> retValue = new HashMap<>();
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_PASSED_TEST);
            statement.setInt(1, user.getUserId());
            ResultSet rs = statement.executeQuery();
            TestMapper mapper = new TestMapper();
            while (rs.next()){
                retValue.put(mapper.mapRow(rs), rs.getDouble("points"));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
        return retValue;
    }
    public static Test getTestInfo(int testId) throws SQLException{
        Test retTest = new Test();
        try (Connection con = Connect.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(SQL_GET_TEST_INFO_BY_ID);
            statement.setInt(1,testId);
            ResultSet resultSet = statement.executeQuery();
            TestMapper mapper = new TestMapper();
            while (resultSet.next()){
                retTest = mapper.mapRow(resultSet);
            }
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(Connect.getInstance().getConnection());
            e.printStackTrace();
        } finally {
            Connect.getInstance().commitAndClose(Connect.getInstance().getConnection());
        }
        return retTest;
    }

    public static HashMap<Integer, Test> getTestByUserId(int userId) throws SQLException{
        HashMap<Integer, Test> retTests = new HashMap<>();
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_TEST_BY_USER_ID);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            TestMapper mapper = new TestMapper();
            while (resultSet.next()){
                retTests.put(resultSet.getInt(Fields.testId), mapper.mapRow(resultSet));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            System.out.println(e.getMessage());
        }
        return retTests;
    }

    public static HashMap<Integer, Test> getBlockedTestByUserId(int userId) throws SQLException{
        HashMap<Integer, Test> retTests = new HashMap<>();
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_BLOCKED_TEST_BY_USER_ID);
            statement.setInt(1,userId);
            ResultSet resultSet = statement.executeQuery();
            TestMapper mapper = new TestMapper();
            while (resultSet.next()){
                retTests.put(resultSet.getInt(Fields.testId), mapper.mapRow(resultSet));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            System.out.println(e.getMessage());
        }
        return retTests;
    }

    public static int insertTestData(Test test){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_TEST_DATA, PreparedStatement.RETURN_GENERATED_KEYS);
            int c=1;
            statement.setString(c++, test.getName());
            statement.setString(c++, test.getDescription());
            statement.setInt(c, test.getUserId());
            statement.executeUpdate();
            int testId=-1;
            ResultSet resultSet = statement.getGeneratedKeys();
            if(resultSet.next()){
                testId= resultSet.getInt(1);
            }
            Connect.getInstance().commitAndClose(con);
            return testId;
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return -1;
    }

    public static void deleteTest(int testId){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_DELETE_TEST);
            statement.setInt(1, testId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }

    public static void updateTest(int testId, Test test){
        Connection con=null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_UPDATE_TEST);
            statement.setString(1, test.getName());
            statement.setString(2, test.getDescription());
            statement.setInt(3, testId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }
    public static void blockTest(int testId){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_BLOCK_TEST);
            statement.setInt(1, testId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }
    public static void unblockTest(int testId){
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_UNBLOCK_TEST);
            statement.setInt(1, testId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }
}

class TestMapper implements EntityMapper<Test>{
    @Override
    public Test mapRow(ResultSet rs) {
        Test retTest = new Test();
        try {
            retTest.setName(rs.getString(Fields.testName));
            retTest.setDescription(rs.getString(Fields.testDescription));
            retTest.setUserId(rs.getInt(Fields.testUserId));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return retTest;
    }
}

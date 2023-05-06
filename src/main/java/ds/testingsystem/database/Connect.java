package ds.testingsystem.database;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Connect {
    private static Connect instance;

    private Connect(){}
    public static synchronized Connect getInstance(){
        if(instance==null)
            instance = new Connect();
        return instance;
    }
    public Connection getConnection() throws SQLException {
        try{Class.forName("com.mysql.cj.jdbc.Driver");}
        catch (ClassNotFoundException ex){ex.printStackTrace();}
        Properties props = new Properties();
        try(InputStream io = Objects.requireNonNull(this.getClass().getResource("/database.properties")).openStream()){
            props.load(io);
        } catch (IOException ex){
            ex.printStackTrace();
        }
        String url = props.getProperty("url");
        String user = props.getProperty("username");
        String password = props.getProperty("password");
        Connection connection = DriverManager.getConnection(url, user, password);
        connection.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
        connection.setAutoCommit(false);
        return connection;
    }
    public void commitAndClose(Connection con){
        try{
            con.commit();
            con.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }
    public void rollbackAndClose(Connection con){
        try{
            con.rollback();
            con.close();
        } catch (SQLException ex){
            ex.printStackTrace();
        }
    }

}

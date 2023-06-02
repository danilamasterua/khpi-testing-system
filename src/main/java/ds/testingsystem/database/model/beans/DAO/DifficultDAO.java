package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.EntityMapper;
import ds.testingsystem.database.Fields;
import ds.testingsystem.database.model.beans.Difficult;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class DifficultDAO {
    private static String SQL_GET_DIFFICULT_LIST = "select * from difficult";

    public static HashMap<Integer, Difficult> getDifficults() throws SQLException{
        HashMap<Integer, Difficult> difficults = new HashMap<>();
        try (Connection con = Connect.getInstance().getConnection()){
            Statement statement = con.createStatement();
            DifficultMapper mapper = new DifficultMapper();
            ResultSet resultSet = statement.executeQuery(SQL_GET_DIFFICULT_LIST);
            while (resultSet.next()){
                difficults.put(resultSet.getInt(Fields.difficultId), mapper.mapRow(resultSet));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(Connect.getInstance().getConnection());
            e.printStackTrace();
        }
        return difficults;
    }
}

class DifficultMapper implements EntityMapper<Difficult>{
    @Override
    public Difficult mapRow(ResultSet rs) {
        Difficult difficult = new Difficult();
        try {
            difficult.setDescription(rs.getString(Fields.difficultDescription));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return difficult;
    }
}
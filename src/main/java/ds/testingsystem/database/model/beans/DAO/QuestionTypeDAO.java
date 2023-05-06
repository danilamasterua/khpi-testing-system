package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.EntityMapper;
import ds.testingsystem.database.model.beans.QuestionType;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

public class QuestionTypeDAO {
    private static String sql_getQuestionTypes = "select * from questiontype";

    public static HashMap<Integer, QuestionType> getAllQuestionTypes(){
        Connection con = null;
        HashMap<Integer, QuestionType> retQuestionTypes = new HashMap<>();
        try {
            con = Connect.getInstance().getConnection();
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(sql_getQuestionTypes);
            QuestionTypeMapper mapper = new QuestionTypeMapper();
            while (rs.next()){
                retQuestionTypes.put(rs.getInt("qTypeId"), mapper.mapRow(rs));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return retQuestionTypes;
    }
}

class QuestionTypeMapper implements EntityMapper<QuestionType>{
    @Override
    public QuestionType mapRow(ResultSet rs) {
        QuestionType questionType = new QuestionType();
        try {
            questionType.setDescription(rs.getString("description"));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return questionType;
    }
}
package ds.testingsystem.database;

import ds.testingsystem.database.model.Question;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class QuestionDAO {
    private static final String SQL_GET_QUESTIONS_FROM_MODULE = "select * from question where moduleId=?";
    private static final String SQL_INSERT_QUESTION = "insert into question (text, imgSrc, qTypeId, moduleId, difficultId) values (?,?,?,?,?)";

    public static HashMap<Integer, Question> getQuestionsFromModule(int moduleId) throws SQLException{
        HashMap<Integer, Question> retHashMap = new HashMap<>();
        try (Connection con = Connect.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(SQL_GET_QUESTIONS_FROM_MODULE);
            statement.setInt(1, moduleId);
            ResultSet resultSet = statement.executeQuery();
            QuestionMapper mapper = new QuestionMapper();
            while (resultSet.next()){
                retHashMap.put(resultSet.getInt(Fields.questionId), mapper.mapRow(resultSet));
            }
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(Connect.getInstance().getConnection());
            e.printStackTrace();
        } finally {
            Connect.getInstance().commitAndClose(Connect.getInstance().getConnection());
        }
        return retHashMap;
    }

    public static int insertQuestion(Question question, int moduleId){
        int retInt = -1;
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_QUESTION, PreparedStatement.RETURN_GENERATED_KEYS);
            int k=1;
            statement.setString(k++, question.getText());
            statement.setString(k++, question.getImgSrc());
            statement.setInt(k++, question.getqTypeId());
            statement.setInt(k++, moduleId);
            statement.setInt(k, question.getDifficultId());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            while (resultSet.next()){
                retInt=resultSet.getInt(1);
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return retInt;
    }
}

class QuestionMapper implements EntityMapper<Question>{
    @Override
    public Question mapRow(ResultSet rs) {
        Question retQ = new Question();
        try {
            retQ.setText(rs.getString(Fields.questionText));
            retQ.setImgSrc(rs.getString(Fields.questionImgSrc));
            retQ.setqTypeId(rs.getInt(Fields.questionQTypeId));
            retQ.setDifficultId(rs.getInt(Fields.questionDifficultId));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return retQ;
    }
}

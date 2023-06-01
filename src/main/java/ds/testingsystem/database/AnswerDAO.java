package ds.testingsystem.database;

import ds.testingsystem.database.model.Answer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class AnswerDAO {
    private static final String SQL_GET_ANSWERS_FROM_QUESTION = "select * from answer where questionId=?";
    private static final String SQL_INSERT_ANSWER = "insert ignore into answer(questionId, text, isRight) VALUES (?,?,?)";
    private static final String SQL_DELETE_ANSWER = "delete from answer where answerId=?";
    private static final String SQL_UPDATE_ANSWER = "update answer set text=?, isRight=? where answerId=?";


    public static HashMap<Integer, Answer> getAnswerFromQuestion(int questionId) throws SQLException{
        HashMap<Integer, Answer> answers = new HashMap<>();
        try (Connection con = Connect.getInstance().getConnection()){
            PreparedStatement statement = con.prepareStatement(SQL_GET_ANSWERS_FROM_QUESTION);
            statement.setInt(1, questionId);
            AnswerMapper mapper = new AnswerMapper();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                answers.put(resultSet.getInt(Fields.answerId), mapper.mapRow(resultSet));
            }
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(Connect.getInstance().getConnection());
            e.printStackTrace();
        } finally {
            Connect.getInstance().commitAndClose(Connect.getInstance().getConnection());
        }
        return answers;
    }

    public static void insertAnswer(int questionId, Answer answer){
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_ANSWER);
            statement.setInt(1, questionId);
            statement.setString(2, answer.getText());
            statement.setBoolean(3, answer.isRight());
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }

    public static void deleteAnswer(int answerId){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_DELETE_ANSWER);
            statement.setInt(1, answerId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }

    public static void updateAnswer(int answerId, Answer answer){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_UPDATE_ANSWER);
            statement.setString(1, answer.getText());
            statement.setBoolean(2, answer.isRight());
            statement.setInt(3, answerId);
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
    }
}
class AnswerMapper implements EntityMapper<Answer>{
    @Override
    public Answer mapRow(ResultSet rs) {
        Answer retAnswer = new Answer();
        try {
            retAnswer.setText(rs.getString(Fields.answerText));
            retAnswer.setRight(rs.getBoolean(Fields.answerIsRight));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return retAnswer;
    }
}

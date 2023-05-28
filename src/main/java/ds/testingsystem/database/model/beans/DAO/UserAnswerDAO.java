package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.EntityMapper;
import ds.testingsystem.database.Fields;
import ds.testingsystem.database.model.beans.UserAnswer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class UserAnswerDAO {
    private static final String SQL_INSERT_USERANSWER_WITH_ANSWER_ID = "insert into useranswer(answerId, questionId, userId, answerdate) values (?,?,?,?)";
    private static final String SQL_INSERT_USERANSWER_WITH_TEXT = "INSERT INTO useranswer(questionId, userId, text, answerdate) VALUES (?,?,?,?)";
    private static final String SQL_GET_USERANSWER_BY_QUESTION_ID = "select * from useranswer where userId=? and questionId=? and answerdate>?";
    private static final String SQL_GET_QUESTION_IDS_BY_USER = "select questionId from useranswer where userId=? and answerdate>?";

    public static void setUserAnswerOnAnswerId(UserAnswer userAnswer) throws SQLException{
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_USERANSWER_WITH_ANSWER_ID);
            int c=1;
            statement.setInt(c++, userAnswer.getAnswerId());
            statement.setInt(c++, userAnswer.getqId());
            statement.setInt(c++, userAnswer.getUserId());
            statement.setTimestamp(c, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException ex){
            Connect.getInstance().rollbackAndClose(con);
            System.out.println(ex.getMessage());
        }
    }
    public static void setUserAnswerOnText(UserAnswer userAnswer) throws SQLException{
        Connection con=null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_USERANSWER_WITH_TEXT);
            int c=1;
            statement.setInt(c++, userAnswer.getqId());
            statement.setInt(c++, userAnswer.getUserId());
            statement.setString(c++, userAnswer.getText());
            statement.setTimestamp(c, Timestamp.valueOf(LocalDateTime.now()));
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException ex){
            Connect.getInstance().rollbackAndClose(con);
            System.out.println(ex.getMessage());
        }
    }
    public static LinkedList<UserAnswer> getUserAnswersByQuestionId(int qId, int userId, LocalDateTime startDate){
        LinkedList<UserAnswer> userAnswers = new LinkedList<>();
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_USERANSWER_BY_QUESTION_ID);
            statement.setInt(1, userId);
            statement.setInt(2, qId);
            statement.setTimestamp(3, Timestamp.valueOf(startDate));
            ResultSet rs = statement.executeQuery();
            UserAnswerMapper mapper = new UserAnswerMapper();
            while (rs.next()){
                userAnswers.add(mapper.mapRow(rs));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return userAnswers;
    }

    public static LinkedList<Integer> getUserQuestions(int userId, LocalDateTime startDate){
        LinkedList<Integer> questionIdS = new LinkedList<>();
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_QUESTION_IDS_BY_USER);
            statement.setInt(1, userId);
            statement.setTimestamp(2, Timestamp.valueOf(startDate));
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                questionIdS.add(rs.getInt("questionId"));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return questionIdS;
    }
}

class UserAnswerMapper implements EntityMapper<UserAnswer>{
    @Override
    public UserAnswer mapRow(ResultSet rs) {
        UserAnswer userAnswer = new UserAnswer();
        try {
            userAnswer.setAnswerId(rs.getInt(Fields.answerId));
            userAnswer.setqId(rs.getInt(Fields.questionId));
            userAnswer.setUserId(rs.getInt(Fields.userId));
            userAnswer.setAnsDateTime(rs.getTimestamp("answerdate").toLocalDateTime());
            userAnswer.setText(rs.getString(Fields.answerText));
        } catch (SQLException e){
            e.printStackTrace();
        }
        return userAnswer;
    }
}

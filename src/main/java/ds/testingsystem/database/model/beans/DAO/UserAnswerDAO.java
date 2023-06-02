package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.EntityMapper;
import ds.testingsystem.database.Fields;
import ds.testingsystem.database.model.beans.UserAnswer;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.LinkedList;

public class UserAnswerDAO {
    private static final String SQL_INSERT_USERANSWER_WITH_ANSWER_ID = "insert into useranswer(answerId, questionId, userId, answerdate) values (?,?,?,?) on duplicate key update answerId=?";
    private static final String SQL_INSERT_USERANSWER_WITH_TEXT = "INSERT INTO useranswer(questionId, userId, text, answerdate) VALUES (?,?,?,?) on duplicate key update text=?";
    private static final String SQL_GET_USERANSWER_BY_QUESTION_ID = "select * from useranswer where userId=? and questionId=?";
    private static final String SQL_GET_QUESTION_IDS_BY_USER = "select useranswer.questionId " +
            "from useranswer left join question on useranswer.questionId = question.questionId " +
            "join module on question.moduleId=module.moduleId " +
            "join test on module.testId = test.testId " +
            "where test.testId=? and useranswer.userId=?";
    private static final String SQL_INSERT_PREPARED_ANSWER = "insert into useranswer(questionId, userId) values (?,?) on duplicate key update text=null, answerId=null";
    private static final String SQL_GET_NOT_PASSED_QUESTION = "select questionId from useranswer where userId=? and answerId is null and text is null";
    private static final String SQL_GET_USERANSWERS_BY_QUESTION = "select * from useranswer where questionId=?";

    public static void setUserAnswerOnAnswerId(UserAnswer userAnswer) throws SQLException{
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_USERANSWER_WITH_ANSWER_ID);
            int c=1;
            statement.setInt(c++, userAnswer.getAnswerId());
            statement.setInt(c++, userAnswer.getqId());
            statement.setInt(c++, userAnswer.getUserId());
            statement.setTimestamp(c++, Timestamp.valueOf(LocalDateTime.now()));
            statement.setInt(c, userAnswer.getAnswerId());
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
            statement.setTimestamp(c++, Timestamp.valueOf(LocalDateTime.now()));
            statement.setString(c, userAnswer.getText());
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException ex){
            Connect.getInstance().rollbackAndClose(con);
            System.out.println(ex.getMessage());
        }
    }
    public static LinkedList<UserAnswer> getUserAnswersByQuestionId(int qId, int userId){
        LinkedList<UserAnswer> userAnswers = new LinkedList<>();
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_USERANSWER_BY_QUESTION_ID);
            statement.setInt(1, userId);
            statement.setInt(2, qId);
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

    public static LinkedList<Integer> getUserQuestions(int testId, int userId){
        LinkedList<Integer> questionIdS = new LinkedList<>();
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(SQL_GET_QUESTION_IDS_BY_USER);
            preparedStatement.setInt(1, testId);
            preparedStatement.setInt(2, userId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                questionIdS.add(rs.getInt(1));
            }
        } catch (SQLException e){
            Connect.getInstance().rollbackAndClose(con);
            e.printStackTrace();
        }
        return questionIdS;
    }

    public static void prepareAnswer(UserAnswer ua){
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_INSERT_PREPARED_ANSWER);
            statement.setInt(1, ua.getqId());
            statement.setInt(2, ua.getUserId());
            statement.executeUpdate();
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
    }

    public static LinkedList<Integer> getNotPassedQuestions(int userId){
        LinkedList<Integer> notPassedQuestions = new LinkedList<>();
        Connection con = null;
        try {
            con = Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_NOT_PASSED_QUESTION);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                notPassedQuestions.add(rs.getInt(Fields.questionId));
            }
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
        return notPassedQuestions;
    }

    public static LinkedList<UserAnswer> getAnswersByQuestion(int questionId){
        LinkedList<UserAnswer> userAnswers = new LinkedList<>();
        Connection con = null;
        try {
            con=Connect.getInstance().getConnection();
            PreparedStatement statement = con.prepareStatement(SQL_GET_USERANSWERS_BY_QUESTION);
            statement.setInt(1, questionId);
            UserAnswerMapper mapper = new UserAnswerMapper();
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                userAnswers.add(mapper.mapRow(rs));
            }
            Connect.getInstance().commitAndClose(con);
        } catch (SQLException e){
            e.printStackTrace();
            Connect.getInstance().rollbackAndClose(con);
        }
        return userAnswers;
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

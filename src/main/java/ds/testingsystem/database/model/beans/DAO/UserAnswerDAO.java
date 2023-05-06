package ds.testingsystem.database.model.beans.DAO;

import ds.testingsystem.database.Connect;
import ds.testingsystem.database.model.beans.UserAnswer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserAnswerDAO {
    private static final String SQL_INSERT_USERANSWER_WITH_ANSWER_ID = "insert into useranswer(answerId, questionId, userId, answerdate) values (?,?,?,?)";
    private static final String SQL_INSERT_USERANSWER_WITH_TEXT = "INSERT INTO useranswer(questionId, userId, text, answerdate) VALUES (?,?,?,?)";

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
}

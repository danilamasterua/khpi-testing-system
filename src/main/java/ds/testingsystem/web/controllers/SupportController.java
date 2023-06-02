package ds.testingsystem.web.controllers;

import ds.testingsystem.database.model.beans.DAO.DifficultDAO;
import ds.testingsystem.database.model.beans.DAO.GroupAccessDAO;
import ds.testingsystem.database.model.beans.DAO.QuestionTypeDAO;
import ds.testingsystem.database.model.beans.Difficult;
import ds.testingsystem.database.model.beans.GroupAccess;
import ds.testingsystem.database.model.beans.QuestionType;

import java.sql.SQLException;
import java.util.HashMap;

public class SupportController {
    public static HashMap<Integer, Difficult> getDifficults() throws SQLException {
        return DifficultDAO.getDifficults();
    }
    public static HashMap<Integer, QuestionType> getQuestionTypes() throws SQLException{
        return QuestionTypeDAO.getAllQuestionTypes();
    }
    public static void grantAccess(GroupAccess ga){
        GroupAccessDAO.grantAccess(ga);
    }
    public static void denyAccess(int groupId, int testId){
        GroupAccessDAO.denyAccess(groupId, testId);
    }
    public static GroupAccess getAccessData(int testId, int groupId){
        return GroupAccessDAO.getGroupAccessDataTest(testId, groupId);
    }
}

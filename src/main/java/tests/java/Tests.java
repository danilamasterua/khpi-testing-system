package tests.java;

import ds.testingsystem.database.model.beans.DAO.UserAnswerDAO;

import java.util.LinkedList;

public class Tests {
    public static void main(String[] args){
        System.out.println("Test 1");
        testDbQueryGetQIds();
    }
    public static void testDbQueryGetQIds(){
        int userId=22;
        int testId=1;
        LinkedList<Integer> list = UserAnswerDAO.getUserQuestions(testId, userId);
        if(list.isEmpty()){
            throw new RuntimeException("List is empty");
        } else {
            System.out.println("Successful!");
            System.out.println(list);
        }
    }
}

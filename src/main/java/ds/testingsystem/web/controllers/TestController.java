package ds.testingsystem.web.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ds.testingsystem.database.*;
import ds.testingsystem.database.model.Module;
import ds.testingsystem.database.model.*;
import ds.testingsystem.database.model.beans.DAO.UserAnswerDAO;
import ds.testingsystem.database.model.beans.UserAnswer;
import ds.testingsystem.database.model.beans.UserAnswerList;
import org.json.JSONObject;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class TestController {
    public static HashMap<Integer, Test> getAvailableTests(String login) throws SQLException {
        User user = UserDAO.getUserInfo(login);
        return TestDAO.getAvailableTest(user);
    }
    public static Test loadTest(int testId) throws SQLException{
        Test retTest = TestDAO.getTestInfo(testId);
        HashMap<Integer, Module> modules = ModuleDAO.getModulesFromTest(testId);
        for (Map.Entry<Integer, Module> entry:modules.entrySet()){
            HashMap<Integer, Question> questions = QuestionDAO.getQuestionsFromModule(entry.getKey());
            for (Map.Entry<Integer, Question> questionEntry:questions.entrySet()){
                HashMap<Integer, Answer> answers = AnswerDAO.getAnswerFromQuestion(questionEntry.getKey());
                questionEntry.getValue().setAnswers(answers);
            }
            entry.getValue().setQuestions(questions);
        }
        retTest.setQuestions(modules);
        return retTest;
    }
    public static void setAnswers(int userId, UserAnswerList userAnswerList) throws SQLException{
        for (String str: userAnswerList.getChanses()){
            if(userAnswerList.getqTypeId()==3) {
                UserAnswer ua = new UserAnswer(userId, userAnswerList.getqId(), str);
                UserAnswerDAO.setUserAnswerOnText(ua);
            } else if(userAnswerList.getqTypeId()==1||userAnswerList.getqTypeId()==2){
                UserAnswer ua = new UserAnswer(userId, Integer.parseInt(str), userAnswerList.getqId());
                UserAnswerDAO.setUserAnswerOnAnswerId(ua);
            }
        }
    }
    public static HashMap<Integer, Test> getTestsCreatedByUser(int userId) throws SQLException{
        return TestDAO.getTestByUserId(userId);
    }
    public static int createTest(Test test){
        return TestDAO.insertTestData(test);
    }
    public static void insertModule(String jsonData){
        Gson g = new Gson();
        JsonObject moduleJson = g.fromJson(jsonData, JsonObject.class);
        Module module = new Module(moduleJson.get("description").getAsString(), moduleJson.get("qCount").getAsInt());
        int testId = moduleJson.get("testId").getAsInt();
        ModuleDAO.insertModule(testId, module);
    }
    public static void createQuestion(String jsonData){
        Gson g = new Gson();
        JsonObject question = g.fromJson(jsonData, JsonObject.class);
        int moduleId = question.get("moduleId").getAsInt();
        String qText = question.get("text").getAsString();
        String imgSrc = question.get("imgSrc").getAsString();
        int qTypeId = question.get("qTypeId").getAsInt();
        int difficultId = question.get("difficultId").getAsInt();
        Question qu = new Question(qText, imgSrc, qTypeId, difficultId);
        int qId = QuestionDAO.insertQuestion(qu, moduleId);
        JsonArray answers = question.getAsJsonArray("questions");
        for(JsonElement js:answers){
            JsonObject obj = js.getAsJsonObject();
            String aText = obj.get("text").getAsString();
            boolean isRight = obj.get("isRight").getAsBoolean();
            AnswerDAO.insertAnswer(qId, new Answer(aText, isRight));
        }
    }
    public static void deleteTest(int testId){
        TestDAO.deleteTest(testId);
    }
    public static HashMap<Integer, Group> getAccessedGroup(int testId){
        return GroupDAO.getAccessedGroup(testId, true);
    }
    public static HashMap<Integer, Group> getNotAccessedGroup(int testId){
        return GroupDAO.getAccessedGroup(testId, false);
    }
    public static JSONObject getTestJsonResponse(int testId) throws SQLException {
        Test newTest = TestController.loadTest(testId);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("testId", testId);
        jsonResponse.put("test", newTest);
        jsonResponse.put("modules", newTest.getModules());
        return jsonResponse;
    }

    public static void deleteQuestion(int qId){
        QuestionDAO.deleteQuestion(qId);
    }

    public static JSONObject getQuestionJSON(int qId) throws SQLException{
        Question q = QuestionDAO.getQuestion(qId);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("text", q.getText());
        jsonResponse.put("imgSrc", q.getImgSrc());
        jsonResponse.put("qTypeId", q.getqTypeId());
        jsonResponse.put("difficultId", q.getDifficultId());
        jsonResponse.put("answers", AnswerDAO.getAnswerFromQuestion(qId));
        return jsonResponse;
    }
    public static void updateTest(int testId, Test test) throws SQLException{
        TestDAO.updateTest(testId, test);
    }

    public static void updateQuestion(String jsonData){
        Gson g = new Gson();
        JsonObject question = g.fromJson(jsonData, JsonObject.class);
        Question q = new Question(question.get("text").getAsString(), question.get("imgSrc").getAsString(), question.get("qTypeId").getAsInt(), question.get("difficultId").getAsInt());
        QuestionDAO.updateQuestion(question.get("qId").getAsInt(), q);
        JsonArray answers = question.getAsJsonArray("questions");
        for(JsonElement js:answers){
            JsonObject obj = js.getAsJsonObject();
            String aText = obj.get("text").getAsString();
            boolean isRight = obj.get("isRight").getAsBoolean();
            AnswerDAO.insertAnswer(question.get("qId").getAsInt(), new Answer(aText, isRight));
        }
    }

    public static Module getModule(int moduleId){
        return ModuleDAO.getModule(moduleId);
    }

    public static JSONObject getModuleJson(int moduleId){
        Module module = getModule(moduleId);
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("description", module.getDescription());
        jsonResponse.put("qCount", module.getqCount());
        return jsonResponse;
    }

    public static void updateModule(String jsonObject){
        Gson g = new Gson();
        JsonObject object = g.fromJson(jsonObject, JsonObject.class);
        ModuleDAO.updateModule(object.get("moduleId").getAsInt(), new Module(object.get("description").getAsString(), object.get("qCount").getAsInt()));
    }

    public static void deleteModule(int moduleId){ModuleDAO.deleteModule(moduleId);}
}

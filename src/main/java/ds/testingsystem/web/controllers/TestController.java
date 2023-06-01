package ds.testingsystem.web.controllers;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import ds.testingsystem.database.*;
import ds.testingsystem.database.model.Module;
import ds.testingsystem.database.model.*;
import ds.testingsystem.database.model.beans.DAO.UserAnswerDAO;
import ds.testingsystem.database.model.beans.DAO.UserPointsDAO;
import ds.testingsystem.database.model.beans.UserAnswer;
import ds.testingsystem.database.model.beans.UserAnswerList;
import ds.testingsystem.database.model.beans.UserPoints;
import ds.testingsystem.database.model.beans.UserTestPoints;
import ds.testingsystem.services.GenerateExcelFile;
import org.json.JSONObject;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.*;

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
    public static boolean setAnswers(int userId, UserAnswerList userAnswerList) throws SQLException{
        HashMap<Integer, Answer> answers = AnswerDAO.getAnswerFromQuestion(userAnswerList.getqId());
        boolean isRight=false;
        for (String str: userAnswerList.getChanses()) {
            if (userAnswerList.getqTypeId() == 3) {
                UserAnswer ua = new UserAnswer(userId, userAnswerList.getqId(), str);
                UserAnswerDAO.setUserAnswerOnText(ua);
                for(Map.Entry<Integer, Answer> entry: answers.entrySet()){
                    if(entry.getValue().getText().equals(str)){
                        isRight=true;
                        break;
                    } else if(isRight){
                        isRight=false;
                        break;
                    }
                }
            } else if (userAnswerList.getqTypeId() == 1 || userAnswerList.getqTypeId() == 2) {
                UserAnswer ua = new UserAnswer(userId, Integer.parseInt(str), userAnswerList.getqId());
                UserAnswerDAO.setUserAnswerOnAnswerId(ua);
                isRight = answers.get(Integer.parseInt(str)).isRight();
            }
        }
        return isRight;
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
        HashMap<Integer, Group> group = GroupDAO.getAccessedGroup(testId, true);
        for (Map.Entry<Integer, Group> entry:group.entrySet()){
            entry.getValue().setUsers(UserDAO.getUsersByGroup(entry.getKey()));
        }
        return group;
    }
    public static HashMap<Integer, Group> getNotAccessedGroup(int testId){
        HashMap<Integer, Group> groups = GroupDAO.getAccessedGroup(testId, false);
        for (Map.Entry<Integer, Group> entry:groups.entrySet()){
            entry.getValue().setUsers(UserDAO.getUsersByGroup(entry.getKey()));
        }
        return groups;
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

    public static double getPoints(int userId) throws SQLException {
        DoubleWrapper points = new DoubleWrapper();
        LinkedList<Integer> questionIdS = UserAnswerDAO.getUserQuestions(userId);
        System.out.println("qIds{\n"+questionIdS+"\n}");
        for (int id:questionIdS){
            LinkedList<UserAnswer> ua = UserAnswerDAO.getUserAnswersByQuestionId(id, userId);
            System.out.println("ua={\n"+ua+"\n}");
            Question question = QuestionDAO.getQuestion(id);
            HashMap<Integer, Answer> qAnswers = AnswerDAO.getAnswerFromQuestion(id);
            ua.forEach(a -> {
                System.out.println("aId" + a.getAnswerId());
                if (a.getAnswerId()!=0) {
                    Answer answer = qAnswers.get(a.getAnswerId());
                    if (question.getqTypeId() == 1 || question.getqTypeId() == 2) {
                        if (answer.isRight()) {
                            points.setValue(getPointsDD(points, question));
                        }
                    } else if (question.getqTypeId() == 3) {
                        if (answer.getText().equals(a.getText())) {
                            points.setValue(getPointsDD(points, question));
                        }
                    }
                }
            });
        }

        return points.getValue();
    }

    public static double getMaxPoint(int userId) throws SQLException {
        DoubleWrapper points = new DoubleWrapper();
        LinkedList<Integer> questionIdS = UserAnswerDAO.getUserQuestions(userId);
        for (int id:questionIdS){
            Question question = QuestionDAO.getQuestion(id);
            HashMap<Integer, Answer> qAnswers = AnswerDAO.getAnswerFromQuestion(id);
            qAnswers.entrySet().stream().forEach(entry-> {
                if (question.getqTypeId() == 1 || question.getqTypeId() == 2) {
                    if (entry.getValue().isRight()) {
                        points.setValue(getPointsDD(points, question));
                    }
                } else if (question.getqTypeId()==3) {
                    points.setValue(getPointsDD(points, question));
                }
            });
        }
        return points.getValue();
    }
    public static void setUserPoint(double points, int testId, int userId){
        UserPointsDAO.insertUserpoint(new UserPoints(testId, userId, points, LocalDateTime.now()));
    }
    public static HashMap<Test, Double> getPassedTests(User user){
        return TestDAO.getPassedTest(user);
    }
    public static HashMap<Group, LinkedList<UserTestPoints>> getUserPointsByTestId(int testId){
        HashMap<Integer, Group> groups = GroupDAO.getAccessedGroup(testId, true);
        HashMap<Group, LinkedList<UserTestPoints>> ret = new HashMap<>();
        for (Map.Entry<Integer, Group> entry: groups.entrySet()){
            LinkedList<UserTestPoints> utp = UserPointsDAO.getUsersPoints(entry.getKey(), testId);
            ret.put(entry.getValue(), utp);
        }
        return ret;
    }
    private static HashMap<Integer, Question> getRandomQuestions(int qCount, HashMap<Integer, Question> orig){
        HashMap<Integer, Question> retHashMap = new HashMap<>();
        Set<Integer> keys = orig.keySet();
        System.out.println(keys);
        Random random = new Random();
        while (retHashMap.size()<qCount){
            int i = random.nextInt(keys.size());
            Integer key = (Integer) keys.toArray()[i];
            Question value = orig.get(key);
            retHashMap.put(key, value);
        }
        return retHashMap;
    }
    public static Test loadTestForPass(int testId, int userId) throws SQLException {
        LinkedList<Integer> npq = UserAnswerDAO.getNotPassedQuestions(userId);
        System.out.println(npq.size());
        if(npq.size()==0){
            return loadTestWithRandomQuestions(testId, userId);
        } else {
            return loadTestWithNotPassedQuestion(testId, npq);
        }
    }
    public static String generateExcelTestPoints(String fullPathName, int testId) throws SQLException {
        Test test = TestDAO.getTestInfo(testId);
        return GenerateExcelFile.generateExcel(fullPathName, getUserPointsByTestId(testId), test);
    }
    public static void blockTest(int testId){TestDAO.blockTest(testId);}
    public static void unblockTest(int testId){TestDAO.unblockTest(testId);}
    public static HashMap<Integer, Test> getBlockedTest(int userId) throws SQLException {return TestDAO.getBlockedTestByUserId(userId);}
    private static Double getPointsDD(DoubleWrapper points, Question question) {
        switch (question.getDifficultId()) {
            case 1:
                points.setValue(points.getValue() + (1 * 0.5));
                break;
            case 2:
                points.setValue(points.getValue() + (1 * 0.75));
                break;
            case 3:
                points.setValue(points.getValue() + 1);
                break;
            default:
                throw new RuntimeException("Unsupported difficult ID");
        }
        return points.getValue();
    }
    private static Test loadTestWithRandomQuestions(int testId, int userId) throws SQLException {
        Test retTest = TestDAO.getTestInfo(testId);
        HashMap<Integer, Module> modules = ModuleDAO.getModulesFromTest(testId);
        for (Map.Entry<Integer, Module> entry:modules.entrySet()){
            HashMap<Integer, Question> questionsOrig = QuestionDAO.getQuestionsFromModule(entry.getKey());
            HashMap<Integer, Question> questions = getRandomQuestions(entry.getValue().getqCount(), questionsOrig);
            for (Map.Entry<Integer, Question> questionEntry:questions.entrySet()){
                HashMap<Integer, Answer> answers = AnswerDAO.getAnswerFromQuestion(questionEntry.getKey());
                questionEntry.getValue().setAnswers(answers);
                UserAnswer ua = new UserAnswer();
                ua.setqId(questionEntry.getKey());
                ua.setUserId(userId);
                UserAnswerDAO.prepareAnswer(ua);
            }
            entry.getValue().setQuestions(questions);
        }
        retTest.setQuestions(modules);
        return retTest;
    }
    private static Test loadTestWithNotPassedQuestion(int testId, LinkedList<Integer> npq) throws SQLException{
        Test retTest = TestDAO.getTestInfo(testId);
        HashMap<Integer, Module> modules = ModuleDAO.getModulesFromTest(testId);
        for (Map.Entry<Integer, Module> entry: modules.entrySet()){
            HashMap<Integer, Question> questionsOrig = QuestionDAO.getQuestionsFromModule(entry.getKey());
            HashMap<Integer, Question> questions = new HashMap<>();
            for (Map.Entry<Integer, Question> qEntry: questionsOrig.entrySet()){
                if(npq.contains(qEntry.getKey())){
                    HashMap<Integer, Answer> answers = AnswerDAO.getAnswerFromQuestion(qEntry.getKey());
                    qEntry.getValue().setAnswers(answers);
                    questions.put(qEntry.getKey(), qEntry.getValue());
                }
            }
            entry.getValue().setQuestions(questions);
        }
        retTest.setQuestions(modules);
        return retTest;
    }
}

class DoubleWrapper{
    private double value;
    public DoubleWrapper() {value=0;}
    public DoubleWrapper(double value) {this.value = value;}
    public double getValue() {return value;}
    public void setValue(double value) {this.value = value;}
}
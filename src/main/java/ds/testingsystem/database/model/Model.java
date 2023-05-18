package ds.testingsystem.database.model;

import java.util.HashMap;

public class Model {
    private User currentUser;
    private HashMap<Integer, Test> tests;
    private HashMap<Test, Double> passedTests;

    public Model() {
    }
    public Model(User currentUser) {
        this.currentUser = currentUser;
    }
    public Model(User currentUser, HashMap<Integer, Test> tests) {
        this.currentUser = currentUser;
        this.tests = tests;
    }
    public Model(User currentUser, HashMap<Integer, Test> tests, HashMap<Test, Double> passedTests) {
        this.currentUser = currentUser;
        this.tests = tests;
        this.passedTests = passedTests;
    }
    public User getCurrentUser() {
        return currentUser;
    }
    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
    public HashMap<Integer, Test> getTests() {
        return tests;
    }
    public void setTests(HashMap<Integer, Test> tests) {
        this.tests = tests;
    }
    public HashMap<Test, Double> getPassedTests() {return passedTests;}
    public void setPassedTests(HashMap<Test, Double> passedTests) {this.passedTests = passedTests;}
}

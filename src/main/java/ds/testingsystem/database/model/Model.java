package ds.testingsystem.database.model;

import java.util.HashMap;

public class Model {
    private User currentUser;
    private HashMap<Integer, Test> tests;
    private HashMap<Integer, User> users;

    public Model() {
    }
    public Model(User currentUser) {
        this.currentUser = currentUser;
    }
    public Model(User currentUser, HashMap<Integer, Test> tests) {
        this.currentUser = currentUser;
        this.tests = tests;
    }
    public Model(User currentUser, HashMap<Integer, Test> tests, HashMap<Integer, User> users) {
        this.currentUser = currentUser;
        this.tests = tests;
        this.users = users;
    }

    public HashMap<Integer, User> getUsers() {
        return users;
    }

    public void setUsers(HashMap<Integer, User> users) {
        this.users = users;
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
}

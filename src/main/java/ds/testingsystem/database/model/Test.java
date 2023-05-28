package ds.testingsystem.database.model;

import java.util.HashMap;

public class Test {
    private String name;
    private String description;
    private int userId;
    private HashMap<Integer, Module> modules;

    public Test() {
    }

    public Test(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Test(String name, String description, int userId, int minToFin) {
        this.name = name;
        this.description = description;
        this.userId = userId;
    }

    public Test(String name, String description, int userId, HashMap<Integer, Module> modules) {
        this.name = name;
        this.description = description;
        this.userId = userId;
        this.modules = modules;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public HashMap<Integer, Module> getModules() {
        return modules;
    }

    public void setQuestions(HashMap<Integer, Module> modules) {
        this.modules = modules;
    }

    @Override
    public String toString() {
        return "Test{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", userId=" + userId +
                ", modules=" + modules +
                '}';
    }
}

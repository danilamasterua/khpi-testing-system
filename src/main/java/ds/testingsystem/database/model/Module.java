package ds.testingsystem.database.model;

import java.util.HashMap;

public class Module {
    private String description;
    private HashMap<Integer, Question> questions;
    private int qCount;

    public Module() {
    }

    public Module(String description) {
        this.description = description;
    }

    public Module(String description, int qCount) {
        this.description = description;
        this.qCount = qCount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Module(HashMap<Integer, Question> questions) {
        this.questions = questions;
    }

    public Module(HashMap<Integer, Question> questions, int qCount) {
        this.questions = questions;
        this.qCount = qCount;
    }

    public HashMap<Integer, Question> getQuestions() {
        return questions;
    }

    public void setQuestions(HashMap<Integer, Question> questions) {
        this.questions = questions;
    }

    public int getqCount() {
        return qCount;
    }

    public void setqCount(int qCount) {
        this.qCount = qCount;
    }
}

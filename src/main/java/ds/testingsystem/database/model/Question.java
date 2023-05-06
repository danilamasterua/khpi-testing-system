package ds.testingsystem.database.model;

import java.util.HashMap;

public class Question {
    private String text;
    private String imgSrc;
    private int qTypeId;
    private int difficultId;
    private HashMap<Integer, Answer> answers;

    public Question() {
    }

    public Question(String text, String imgSrc, int qTypeId, int difficultId) {
        this.text = text;
        this.imgSrc = imgSrc;
        this.qTypeId = qTypeId;
        this.difficultId=difficultId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public int getqTypeId() {
        return qTypeId;
    }

    public void setqTypeId(int qTypeId) {
        this.qTypeId = qTypeId;
    }

    public HashMap<Integer, Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap<Integer, Answer> answers) {
        this.answers = answers;
    }

    public int getDifficultId() {return difficultId;}

    public void setDifficultId(int difficultId) {this.difficultId = difficultId;}
}

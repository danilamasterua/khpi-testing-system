package ds.testingsystem.database.model.beans;

import java.time.LocalDateTime;

public class UserAnswer {
    private int userId;
    private int answerId;
    private int qId;
    private String text;
    private LocalDateTime ansDateTime;

    public UserAnswer(int userId, int qId, String text) {
        this.userId = userId;
        this.qId = qId;
        this.text = text;
    }

    public UserAnswer(int userId, int answerId, int qId) {
        this.userId = userId;
        this.answerId = answerId;
        this.qId = qId;
    }

    public UserAnswer(int userId, int answerId, int qId, LocalDateTime ansDateTime) {
        this.userId = userId;
        this.answerId = answerId;
        this.qId = qId;
        this.ansDateTime = ansDateTime;
    }

    public UserAnswer(int userId, int qId, String text, LocalDateTime ansDateTime) {
        this.userId = userId;
        this.qId = qId;
        this.text = text;
        this.ansDateTime = ansDateTime;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getAnswerId() {
        return answerId;
    }

    public void setAnswerId(int answerId) {
        this.answerId = answerId;
    }

    public int getqId() {
        return qId;
    }

    public void setqId(int qId) {
        this.qId = qId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getAnsDateTime() {
        return ansDateTime;
    }

    public void setAnsDateTime(LocalDateTime ansDateTime) {
        this.ansDateTime = ansDateTime;
    }
}

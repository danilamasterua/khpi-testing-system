package ds.testingsystem.database.model.beans;

import java.time.LocalDateTime;

public class UserPoints {
    private int testId;
    private int userId;
    private double points;
    private LocalDateTime dateTime;

    public UserPoints() {}

    public UserPoints(int testId, int userId, double points, LocalDateTime dateTime) {
        this.testId = testId;
        this.userId = userId;
        this.points = points;
        this.dateTime = dateTime;
    }
    public int getTestId() {return testId;}
    public void setTestId(int testId) {this.testId = testId;}
    public int getUserId() {return userId;}
    public void setUserId(int userId) {this.userId = userId;}
    public double getPoints() {return points;}
    public void setPoints(double points) {this.points = points;}
    public LocalDateTime getDateTime() {return dateTime;}
    public void setDateTime(LocalDateTime dateTime) {this.dateTime = dateTime;}
}

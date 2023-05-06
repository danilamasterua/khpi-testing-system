package ds.testingsystem.database.model.beans;

import java.time.LocalDateTime;

public class GroupAccess {
    private int testId;
    private int groupId;
    private LocalDateTime accStTime;
    private LocalDateTime accFinTime;

    public GroupAccess() {
    }

    public GroupAccess(int testId, int groupId, LocalDateTime accStTime, LocalDateTime accFinTime) {
        this.testId = testId;
        this.groupId = groupId;
        this.accStTime = accStTime;
        this.accFinTime = accFinTime;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public LocalDateTime getAccStTime() {
        return accStTime;
    }

    public void setAccStTime(LocalDateTime accStTime) {
        this.accStTime = accStTime;
    }

    public LocalDateTime getAccFinTime() {
        return accFinTime;
    }

    public void setAccFinTime(LocalDateTime accFinTime) {
        this.accFinTime = accFinTime;
    }
}

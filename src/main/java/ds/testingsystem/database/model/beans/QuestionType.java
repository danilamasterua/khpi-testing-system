package ds.testingsystem.database.model.beans;

public class QuestionType {
    private String description;

    public QuestionType() {}
    public QuestionType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}

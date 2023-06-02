package ds.testingsystem.database.model;

public class Answer {
    private String text;
    private boolean isRight;

    public Answer() {
    }

    public Answer(String text, boolean isRight){
        this.text=text;
        this.isRight=isRight;
    }

    public String getText() {return text;}
    public void setText(String text) {this.text = text;}
    public boolean isRight() {return isRight;}
    public void setRight(boolean right) {isRight = right;}
}

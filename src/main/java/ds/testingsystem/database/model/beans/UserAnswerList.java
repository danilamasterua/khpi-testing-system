package ds.testingsystem.database.model.beans;

public class UserAnswerList {
        private int qId;
        private int qTypeId;
        private String[] chanses;
        public int getqId() {return qId;}
        public void setqId(int qId) {this.qId = qId;}
        public int getqTypeId() {return qTypeId;}
        public void setqTypeId(int qTypeId) {this.qTypeId = qTypeId;}
        public String[] getChanses() {return chanses;}
        public void setChanses(String[] chanses) {this.chanses = chanses;}
}

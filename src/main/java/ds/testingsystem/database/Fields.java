package ds.testingsystem.database;

public final class Fields {
    //User
    public static final String userId="userId";
    public static final String userFirstName="firstName";
    public static final String userLastName="lastName";
    public static final String userLogin = "login";
    public static final String userPassword = "password";
    public static final String userRoleId="roleId";
    public static final String userEmail="email";
    //Test
    public static final String testId="testId";
    public static final String testName="name";
    public static final String testDescription="description";
    public static final String testUserId = "userId";
    public static final String testMinToFin = "minToFin";
    //Module
    public static final String moduleId="moduleId";
    public static final String moduleTestId = "testId";
    public static final String moduleDescription = "description";
    public static final String moduleQCount = "qCount";
    //Question
    public static final String questionId = "questionId";
    public static final String questionText = "text";
    public static final String questionImgSrc = "imgSrc";
    public static final String questionQTypeId = "qTypeId";
    public static final String questionModuleId = "moduleId";
    public static final String questionDifficultId="difficultId";
    //Answer
    public static final String answerId = "answerId";
    public static final String answerQId = "questionId";
    public static final String answerText = "text";
    public static final String answerIsRight = "isRight";
    //Group
    public static final String groupId = "groupId";
    public static final String groupName = "name";
    //Difficult
    public static final String difficultId = "difficultId";
    public static final String difficultDescription = "description";
}

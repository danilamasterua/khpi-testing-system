create table Difficult
(
    difficultId int identity(1,1) primary key,
    description varchar(25)
);

insert into Difficult(description) values ('Easy');
insert into Difficult(description) values ('Medium');
insert into Difficult(description) values ('Hard');

create table Role
(
    roleId int identity(1,1) primary key,
    description varchar(25)
);

insert into Role(description) values ('System Administrator');
insert into Role(description) values ('Teacher');
insert into Role(description) values ('Student');

create table SUser
(
    userId int identity(1,1) primary key,
    firstName varchar(15),
    lastName varchar(50),
    login varchar(65) unique not null,
    password varchar(512) not null,
    roleId int,
    email varchar(100) unique,
    foreign key (roleId) references Role(roleId) on delete cascade
);

create table Test
(
    testId int identity(1,1) primary key,
    name varchar(50),
    description varchar(250),
    userId int,
    difficultId int,
    minToFin int,
    foreign key (userId) references SUser(userId) on delete cascade,
    foreign key (difficultId) references Difficult(difficultId) on delete cascade
);

create table BlockList
(
    testId int,
    userId int,
    primary key (testId, userId),
    foreign key (testId) references Test(testId),
    foreign key (userId) references SUser(userId)
);

create table Module
(
    moduleId int identity(1,1) primary key,
    testId int,
    description varchar(50),
    foreign key (testId) references Test(testId) on delete cascade
);

create table QuestionType
(
    qTypeId int identity(1,1) primary key,
    description varchar(25)
);

insert into QuestionType (description) values ('Radiobutton');
insert into QuestionType (description) values ('Checkbox');
insert into QuestionType (description) values ('Textbox');

create table Question
(
    questionId int identity(1,1) primary key,
    text varchar(250) not null,
    imgSrc varchar(500),
    qTypeId int not null,
    moduleId int,
    testId int,
    foreign key (qTypeId) references QuestionType(qTypeId) on delete cascade,
    foreign key (moduleId) references Module(moduleId) on delete cascade,
    foreign key (testId) references Test(testId) on delete cascade
);

create table Answer
(
    answerId int identity(1,1) primary key,
    questionId int not null,
    text varchar(50) not null,
    foreign key (questionId) references Question(questionId) on delete cascade
);

create table UserAnswer
(
    answerId int,
    questionId int,
    userId int,
    primary key (answerId, questionId, userId),
    foreign key (answerId) references Answer(answerId),
    foreign key (questionId) references Question(questionId),
    foreign key (userId) references SUser(userId)
);

create table EDGroup
(
    groupId int identity(1,1) primary key,
    name varchar(50)
);

create table UserGroup
(
    groupId int,
    userId int,
    primary key (groupId, userId),
    foreign key (groupId) references EDGroup(groupId),
    foreign key (userId) references SUser(userId)
);

create table GroupAccess
(
    groupId int,
    testId int,
    accDateTime DATETIME,
    primary key (groupId, testId),
    foreign key (groupId) references EDGroup(groupId),
    foreign key (testId) references Test(testId)
)
class answer{
    constructor(text, isRight) {
        this.text=text;
        this.isRight=isRight;
    }
}

function createTest(obj){
    let form = document.getElementById(obj.id).parentElement;
    let data = {
        name: form.elements['name'].value,
        description: form.elements['description'].value
    }
    $.ajax({
        method: 'POST',
        url: 'create-test',
        data: {test: JSON.stringify(data)},
        dataType: 'json',
        success: function (response) {
            obj.disabled = 'true';
            let testId = document.createElement("input");
            testId.setAttribute("type", "hidden");
            testId.setAttribute("name", "testId");
            testId.setAttribute("value", response.testId);
            document.getElementById("modules").querySelector("form").append(testId);
            getModules(response);
        },
        error: function (error){
            alert(error);
        }
    })
}

function showCreateModuleForm(obj){
    let form = document.getElementById(obj.id).parentElement;
    let testId = form.elements['testId'].value;
    let testIdF=document.createElement("input");
    testIdF.setAttribute("type", "hidden");
    testIdF.setAttribute("name", "testId")
    testIdF.setAttribute("id", "tempField");
    testIdF.setAttribute("value", testId);
    document.getElementById("createNewModule").querySelector('div').querySelector('form').append(testIdF);
    document.getElementById("modules").style.display="none";
    document.getElementById("createNewModule").style.display="block";
}

function getModules(response){
    let modules = document.getElementById("modules");
    let modulesList = response.modules;
    $.each(modulesList, function (index, module){
        let moduleTable = createModuleTable(index, module);
        modules.append(moduleTable);
    })
    console.log(modulesList);
    modules.style.display="block";
}

function showCreateQuestionForm(obj){
    document.getElementById("modules").style.display="none";
    let cnqForm = document.getElementById("createNewQuestion");
    let pForm = document.getElementById(obj.id).parentElement;
    let moduleId = pForm.elements['moduleId'].value;
    let moduleIdEl = document.createElement("input");
    moduleIdEl.setAttribute("type", "hidden");
    moduleIdEl.setAttribute("id", "tempField");
    moduleIdEl.setAttribute("name", "moduleId");
    moduleIdEl.setAttribute("value", moduleId);
    cnqForm.querySelector(".login-form-block").querySelector('form').prepend(moduleIdEl);
    cnqForm.style.display="block";
}

function createModuleTable(index, module){
    let mDescription = document.createElement("h2");
    mDescription.append(module.description);
    let div=document.createElement("div");
    div.setAttribute("class", "container-fluid");
    let createQuestionFormTech = document.createElement("form");
    let moduleId = document.createElement("input");
    moduleId.setAttribute("type", "hidden");
    moduleId.setAttribute("name", "moduleId");
    moduleId.setAttribute("value", index);
    createQuestionFormTech.append(moduleId);
    let createQuestionButton = document.createElement("input");
    createQuestionButton.setAttribute("type", "button");
    createQuestionButton.setAttribute("id", "btnMod"+index);
    createQuestionButton.setAttribute("onclick", "showCreateQuestionForm(this)");
    createQuestionButton.setAttribute("class", "btn btn-primary");
    createQuestionButton.setAttribute("value", "Створити запитання у модулі")
    createQuestionFormTech.append(createQuestionButton);
    let table = document.createElement("table");
    table.setAttribute("class", "table table-striped");
    $.each(module.questions, function (index, question){
        let tr=document.createElement("tr");
        let tdText = document.createElement("td");
        tdText.append(question.text);
        tr.append(tdText);
        table.append(tr);
    })
    div.append(mDescription);
    div.append(createQuestionFormTech);
    div.append(table);
    return div;
}

function clearModules(){
    let modulesBlock = document.getElementById("modules");
    let divs = modulesBlock.querySelectorAll("div");
    for (let div of divs){
        div.remove();
    }
}

function createModule(obj){
    let form = document.getElementById(obj.id).parentElement.parentElement;
    let data = {
        testId: form.elements['testId'].value,
        description: form.elements['description'].value,
        qCount: form.elements['qCount'].value
    };
    console.log(JSON.stringify(data));
    $.ajax({
        method: 'POST',
        url:'createModule',
        data: {data: JSON.stringify(data)},
        dataType: 'json',
        success: function (response) {
            form.querySelector("#tempField").remove();
            form.elements['description'].value='';
            form.elements['qCount'].value='';
            document.getElementById("createNewModule").style.display="none";
            clearModules();
            getModules(response);
        },
        error: function (error) {
            alert(error);
        }
    })
}

function backToModule(obj){
    let form = document.getElementById(obj.id).parentElement.parentElement;
    form.querySelector("#tempField").remove();
    form.parentElement.parentElement.style.display="none";
    document.getElementById("modules").style.display="block";
}

function createQuestion(obj){
    let form = document.getElementById(obj.id).parentElement.parentElement;
    let data = {
        moduleId: form.elements['moduleId'].value,
        qTypeId: form.elements['questionType'].value,
        text: form.elements['text'].value,
        imgSrc: form.elements['imgSrc'].value,
        difficultId: form.elements['questionDif'].value,
        questions: []
    }
    let answerList = form.querySelectorAll("div");
    for(let ans of answerList){
        let text = ans.querySelector("input[type='text']").value;
        let isRight = ans.querySelector("input[type='checkbox']").checked;
        data.questions.push(new answer(text, isRight));
    }
    $.ajax({
        method: 'POST',
        url: 'createQuestion',
        data: {data: JSON.stringify(data)},
        success: function (response){
            form.querySelector("#tempField").remove();
            let divList = form.querySelectorAll('div');
            for(let div of divList){
                div.remove();
            }
            form.elements['text'].value='';
            form.elements["imgSrc"].value='';
            document.getElementById("createNewQuestion").style.display="none";
            clearModules();
            getModules(response);
        },
        error: function (error){
            alert(error);
        }
    })
}

function addAnswerVar(obj){
    let form = document.getElementById(obj.id).parentElement;
    let div = document.createElement("div");
    div.setAttribute("class", "row");
    let text = document.createElement("input");
    text.setAttribute("type", "text");
    text.setAttribute("class", "form-control col-3");
    text.setAttribute("name", "ansText");
    let isRight = document.createElement("input");
    isRight.setAttribute("type", "checkbox");
    isRight.setAttribute("class", "form-check-input col-1");
    isRight.setAttribute("name", "isRight");
    div.append(text);
    div.append(isRight);
    form.append(div);
}
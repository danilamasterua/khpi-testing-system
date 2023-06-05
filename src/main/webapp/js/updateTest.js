function formQuestionForm(obj, response){
    let questionId = obj.id;
    let qId = createQIdInput(questionId);
    let uM = document.getElementById("createNewQuestion");
    let question = response;
    console.log(question);
    let answers = question.answers;
    let form = uM.querySelector(".login-form-block").querySelector("form");
    form.elements['questionType'].value=question.qTypeId;
    form.elements['text'].value=question.text;
    if(question.imgSrc!=null||question.imgSrc!=="") {
        form.elements['imgSrc'].value = question.imgSrc;
    }
    form.elements['questionDif'].value=question.difficultId;
    form.append(qId);
    $.each(answers, function (index, answer){
        form.append(createAnsDiv(index, answer));
    });
    saveBtnCreate(form, false);
    document.getElementById("modules").style.display="none";
    uM.style.display="block";
}

function openUpdateQuestionForm(obj){
    $.ajax({
            method: "get",
            url:"getQuestion",
            data:{qId:obj.id},
            dataType: 'json',
            success: function (response){
                formQuestionForm(obj, response);
            },
            error: function (error){
                alert(error);
            }
    });
}

function createQIdInput(questionId){
    let input = document.createElement("input");
    input.setAttribute("type", "hidden");
    input.setAttribute("id", "tempField");
    input.setAttribute("value", questionId);
    input.setAttribute("name", "qId");
    return input;
}

function deleteQuestion(obj){
    $.ajax({
        method: "get",
        url: "deleteQuestion",
        data: {qId:obj.id},
        success: function (response){
           reloadPage();
        },
        error: function (error){
            alert(error);
        }
    })
}

function reloadPage(){
    location.reload();
}

function createAnsDiv(index, answer){
    let div = document.createElement("div");
    let ansText = document.createElement('input');
    ansText.setAttribute("type", "text");
    ansText.setAttribute("class", "form-control");
    ansText.setAttribute("name", "ansText");
    ansText.setAttribute("value", answer.text);
    let isRight = document.createElement("input");
    isRight.setAttribute("type", "checkbox");
    isRight.setAttribute("class", "form-check-input");
    isRight.setAttribute("name", "isRight");
    if(answer.right===true){
        isRight.checked=true;
    }
    let deleteBtn = document.createElement("button");
    deleteBtn.setAttribute("type", "button");
    deleteBtn.setAttribute("class", "btn btn-warning");
    deleteBtn.setAttribute("onclick", "deleteNEAns(this)");
    deleteBtn.setAttribute("id", index);
    deleteBtn.append("Видалити");
    div.append(ansText);
    div.append(isRight);
    div.append(deleteBtn);
    return div;
}

function updateQuestion(obj){
    let form = document.getElementById(obj.id).parentElement.parentElement;
    let data = {
        qId: form.elements['qId'].value,
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
        method: "post",
        url: "getQuestion",
        data:{data: JSON.stringify(data)},
        success: function (response) {
            reloadPage();
        }
    });
}

function saveBtnCreate(form, isCreate){
    let btn = form.querySelector('p').querySelector("#cMB");
    if(isCreate===false){
        btn.setAttribute("onclick", "updateQuestion(this)");
    } else {
        btn.setAttribute("onclick", "createQuestionN(this)");
    }
}

function createQuestionN(obj) {
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
            reloadPage();
        },
        error: function (error){
            alert(error);
        }
    })
}

function deleteNEAns(obj){
    let objC = obj;
    $.ajax({
        method: "post",
        url: "deleteAnswer",
        data:{ansId:obj.id},
        success: function (response){
            document.getElementById(obj.id).parentElement.remove();
        }
    })
}

function showCreateQuestionFormM(obj){
    document.getElementById("modules").style.display="none";
    let cnqForm = document.getElementById("createNewQuestion");
    let pForm = obj.parentElement;
    let moduleId = pForm.elements['moduleId'].value;
    let moduleIdEl = document.createElement("input");
    moduleIdEl.setAttribute("type", "hidden");
    moduleIdEl.setAttribute("id", "tempField");
    moduleIdEl.setAttribute("name", "moduleId");
    moduleIdEl.setAttribute("value", moduleId);
    let form = cnqForm.querySelector('form');
    saveBtnCreate(form, true);
    form.append(moduleIdEl);
    cnqForm.style.display="block";
}

function showUpdateModuleForm(obj){
    let mId = obj.parentElement.elements['moduleId'].value;
    $.ajax({
        method: 'get',
        url: 'updateModule',
        data: {mId:mId},
        dataType: 'json',
        success: function (response){
            formUpdateModuleForm(response, mId);
        },
        error: function (error){
            alert(error);
        }
    })
}

function formUpdateModuleForm(response, mId){
    let moduleId = document.createElement("input");
    moduleId.setAttribute("type", "hidden");
    moduleId.setAttribute("name", "moduleId");
    moduleId.setAttribute("id", "tempField");
    moduleId.setAttribute("value", mId);
    let cnm = document.getElementById("createNewModule");
    let form = cnm.querySelector("form");
    form.append(moduleId);
    form.elements['description'].value=response.description;
    form.elements['qCount'].value=response.qCount;
    setFuncSaveModuleBtn(form, false);
    document.getElementById("modules").style.display="none";
    cnm.style.display="block";
}

function setFuncSaveModuleBtn(form, isSave){
    let btn = form.querySelector("#cM");
    if(isSave===true){
        btn.setAttribute("onclick", "createModule(this); reloadPage();");
    } else {
        btn.setAttribute("onclick", "updateModule(this)");
    }
}

function sCM(obj){
    showCreateModuleForm(obj);
    let form = document.getElementById("createNewModule").querySelector('div').querySelector('form');
    setFuncSaveModuleBtn(form, true);
}

function updateModule(obj){
    let form = document.getElementById(obj.id).parentElement.parentElement;
    let data = {
        moduleId: form.elements['moduleId'].value,
        description: form.elements['description'].value,
        qCount: form.elements['qCount'].value
    };
    $.ajax({
        method: 'post',
        url: 'updateModule',
        data: {data:JSON.stringify(data)},
        success: function (response){
            reloadPage();
        }
    })
}

function deleteModule(obj){
    $.ajax({
        method: 'post',
        url:"deleteModule",
        data:{mId:obj.parentElement.elements['moduleId'].value},
        success: function (){
            reloadPage();
        }
    })
}
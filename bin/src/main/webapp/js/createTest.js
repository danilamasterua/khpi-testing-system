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
            getModules(response);
        },
        error: function (error){
            alert(error);
        }
    })
}

function showCreateModuleForm(){
    document.getElementById("modules").style.display="none";
    document.getElementById("createNewModule").style.display="block";
}

function getModules(response){
    let modules = document.getElementById("modules");
    let testId = document.createElement("input");
    testId.setAttribute("type", "hidden");
    testId.setAttribute("name", "testId");
    testId.setAttribute("value", response.testId);
    let modulesList = response.modules;
    $.each(modulesList, function (index, module){
        let mDescription = document.createElement("h2");
        mDescription.append(module.description);
        let createQuestionFormTech = document.createElement("form");
        let moduleId = document.createElement("input");
        moduleId.setAttribute("type", "hidden");
        moduleId.setAttribute("name", "moduleId");
        moduleId.setAttribute("value", index);
        createQuestionFormTech.append(moduleId);
        let createQuestionButton = document.createElement("input");
        createQuestionButton.setAttribute("type", "button");
        createQuestionButton.setAttribute("onclick", "showCreateQuestionForm(this)");
        createQuestionButton.setAttribute("class", "btn btn-primary");
        createQuestionButton.setAttribute("value", "Створити запитання у модулі")
        createQuestionFormTech.append(createQuestionButton);
        let table = document.createElement("table");
        $.each(module.questions, function (index, question){
            let tr=document.createElement("tr");
            let tdText = document.createElement("td");
            tdText.append(question.text);
            tr.append(tdText);
            table.append(tr);
        })
        modules.append(mDescription);
        modules.append(createQuestionFormTech);
        modules.append(table);
    })
    console.log(modulesList);
    modules.querySelector("form").append(testId);
    modules.style.display="block";
}

function showCreateQuestionForm(obj){
    document.getElementById("modules").style.display="none";
    document.getElementById("createNewQuestion").style.display="block";
}
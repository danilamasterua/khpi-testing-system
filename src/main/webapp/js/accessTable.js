function backToTable(obj){
    let form = document.getElementById(obj.id).parentElement.parentElement;
    form.querySelector("#tempField").remove();
    form.parentElement.parentElement.style.display="none";
    document.getElementById("accessTable").style.display="flex";
}

function showCrAccForm(obj){
    document.getElementById("accessTable").style.display="none";
    let form = document.getElementById(obj.id).parentElement;
    let gId = form.elements['gId'].value;
    let ga = document.getElementById("getAccess");
    let groupId = document.createElement("input");
    groupId.setAttribute("type", "hidden");
    groupId.setAttribute("id", "tempField")
    groupId.setAttribute("name", "groupId");
    groupId.setAttribute("value", gId);
    ga.querySelector('form').append(groupId);
    ga.style.display="block";
}

function showCrUserForm(obj){
    document.getElementById("accessTable").style.display="none";
    let form = document.getElementById(obj.id).parentElement;
    let gId = form.elements['gId'].value;
    let ga = document.getElementById("createNewUser");
    let groupId = document.createElement("input");
    groupId.setAttribute("type", "hidden");
    groupId.setAttribute("id", "tempField")
    groupId.setAttribute("name", "groupId");
    groupId.setAttribute("value", gId);
    ga.querySelector('form').append(groupId);
    ga.style.display="block";
}

function showCrGroupForm(obj){
    document.getElementById("accessTable").style.display="none";
    document.getElementById("createNewGroup").style.display="block";
}

function createStudent(obj){
    let form = obj.parentElement.parentElement;
    let testId = form.elements['testId'].value;
    let gId = form.elements['groupId'].value;
    let firstName = form.elements['firstName'].value;
    let lastName = form.elements['lastName'].value;
    let email = form.elements['email'].value;
    if(firstName.length!==0&&lastName.length!==0&&email.length!==0) {
        $.ajax({
            method: 'post',
            url: 'createStudent',
            data: {
                testId: testId,
                groupId: gId,
                firstName: firstName,
                lastName: lastName,
                email: email
            },
            success: function (response) {
                location.reload();
            },
            error: function (error) {
                alert(error);
            }
        })
    } else {
        alert("Поля не повинні бути пусті");
    }
}

function createGroup(obj) {
    let form = obj.parentElement;
    let description = form.elements['description'].value;
    if (description.length!==0) {
        $.ajax({
            method: 'get',
            url: 'createGroup',
            data: {description: description},
            dataType: 'json',
            success: function (response) {
                if (response.gId === "-1") {
                    alert("Помилка при створенні групи, можливо, що група з такою назвою вже існує");
                } else {
                    location.reload();
                }
            }
        })
    } else {
        alert("Поля не повинні бути пусті")
    }
}

function createUserSmallDiv(obj){
    let form = obj.parentElement;
    let usDiv = document.createElement("div");
    usDiv.setAttribute("class", "row")
    let firstName = document.createElement("input");
    firstName.setAttribute("type", "text");
    firstName.setAttribute("class", "form-control col");
    firstName.setAttribute("name", "firstName");
    let lastName = document.createElement("input");
    lastName.setAttribute("type", "text");
    lastName.setAttribute("class", "form-control col");
    lastName.setAttribute("name", "lastName");
    let email = document.createElement("input");
    email.setAttribute("type", "text");
    email.setAttribute("class", "form-control col");
    email.setAttribute("name", "email");
    usDiv.append(firstName);
    usDiv.append(lastName);
    usDiv.append(email);
}

function changeAccType(obj){
    let selOpt = obj.value;
    switch (selOpt){
        case "temp":
            document.getElementById("tempAccess").style.display="block";
            document.getElementById("permTimeAcc").style.display="none";
            break;
        case "permanent":
            document.getElementById("tempAccess").style.display="none";
            document.getElementById("permTimeAcc").style.display="none";
            break;
        case "permanentTime":
            document.getElementById("tempAccess").style.display="none";
            document.getElementById("permTimeAcc").style.display="block";
            break;
        default:
            alert("Error");
            break;
    }
}

function checkEmail(value){
    let pattern = /^[\w\.-]+@[\w\.-]+\.\w+$/gm
    return pattern.test(value);
}

function checkEmailAndEnableButton(value){
    document.getElementById("cNU").disabled = !checkEmail(value);
}

function deleteUserPoints(obj){
    let form = obj.parentElement;
    let tId = form.elements['testId'].value;
    let uId = form.elements['userId'].value;
    $.ajax({
        method: 'post',
        url: 'deleteUserPoints',
        data: {
            userId: uId,
            testId: tId
        },
        success: function (response) {
            form.parentElement.parentElement.remove();
        },
        error: function (error) {
            alert("Помилка");
        }
    })
}
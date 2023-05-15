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
    document.getElementById("accessTable").display="none";
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

function createStudent(obj){
    let form = obj.parentElement.parentElement;
    let testId = form.elements['testId'].value;
    let gId = form.elements['groupId'].value;
    let firstName = form.elements['firstName'].value;
    let lastName = form.elements['lastName'].value;
    let email = form.elements['email'].value;
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
        success: function (response){
            location.reload();
        },
        error: function (error){
            alert(error);
        }
    })
}

function createGroup(obj) {
    let form = obj.parentElement;
    let description = form.elements['description'].value;
    $.ajax({
        method: 'get',
        url: 'createGroup',
        data: {description: description},
        dataType: 'json',
        success: function (response) {
            let hidden = document.createElement("input");
            hidden.setAttribute("id", "tempField");
            hidden.setAttribute("name", "groupId");
            hidden.setAttribute("value", response.gId);
            hidden.setAttribute("type", "hidden");
            form.prepend(hidden);
            obj.disabled=true;
            form.querySelector("#nUBtn").disabled=false;
        }
    })
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


}
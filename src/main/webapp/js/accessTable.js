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
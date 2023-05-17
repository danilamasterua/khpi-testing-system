function getAnswer(obj){
    obj.disabled = 'true';
    let form = document.getElementById(obj.id).parentElement
    let data={
        qId: form.elements['qId'].value,
        qTypeId: form.elements['qTypeId'].value,
        chanses: []
    }
    if (data.qTypeId==1||data.qTypeId==2) {
        let chanses = form.elements['chans'];
        for (let i = 0; i < chanses.length; i++) {
            if (chanses[i].checked) {
                data.chanses.push(chanses[i].value);
            }
        }
    } else if(data.qTypeId==3){
        let chans = form.elements['chans'];
        data.chanses.push(chans.value);
    }
    $.ajax({
        method: 'POST',
        url: "setAnswer",
        data: {data: JSON.stringify(data)},
        dataType: 'json',
        success: function (response){
            console.log(response.isRight);
            if (response.isRight){
                form.append("Відповідь правильна");
            } else {
                form.append("Відповідь неправильна");
            }
        },
        error: function (error){
            alert("Error: "+error);
        }
    });
}

function startTest(obj){
    let startform =document.getElementById('startTest');
    startform.style.display="none";
    startform.nextElementSibling.style.display="flex";
}

function openNextBlock(obj){
    let sD = obj.parentElement.parentElement;
    sD.style.display="none";
    sD.nextElementSibling.style.display="flex";
}

function getTestPoints(obj){
    obj.disabled=true;
    document.getElementById("loading").style.display = "flex";
    let testInfo = obj.parentElement;
    $.ajax({
        method: 'POST',
        url: 'getPoints',
        data: {
            testId: testInfo.elements['testId'].value,
            startDate: testInfo.elements['startDate'].value
        },
        dataType: 'json',
        success: function (response){
            document.getElementById("resultField").append(response.points+"%");
            document.getElementById("loading").style.display="none";
            document.getElementById("results").style.display="flex";
        }
    })
}
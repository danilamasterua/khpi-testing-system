let startTime;
let duration;
let finTime;
let timer;
function getAnswer(obj){
    obj.disabled = 'true';
    let form = document.getElementById(obj.id).parentElement;
    form.elements['isAnswered'].value="true"
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
    let startform=document.getElementById('startTest');
    startTimer(obj);
    startform.style.display="none";
    startform.nextElementSibling.style.display="flex";
}

function skipQuestion(obj){
    let form = document.getElementById(obj.id).parentElement;
    let isAnswered = form.elements['isAnswered'].value;
    if(isAnswered==="false"){
        if(document.getElementById("sqZag")!=null) {
            document.getElementById("sqZag").remove();
        }
        let cont = document.getElementById("skippedQuestions");
        let zag = document.createElement("h4");
        zag.setAttribute("id", "sqZag");
        zag.append("Ви пропустили такі запитання");
        cont.prepend(zag);
        let btn = document.createElement("button");
        btn.setAttribute("type", "button")
        btn.setAttribute("class", "btn btn-link");
        btn.setAttribute("id", obj.parentElement.parentElement.id+"b");
        btn.setAttribute("onclick", "passQuestion(this)");
        btn.append(form.querySelector('[id^="qT"]').textContent);
        cont.append(btn);
        console.log(form.parentElement.id);
    }
    openNextBlock(obj);
}

function openNextBlock(obj){
    let sD = obj.parentElement.parentElement;
    sD.style.display="none";
    sD.nextElementSibling.style.display="flex";
}

function getTestPoints(){
    let obj = document.getElementById("endTestBtn");
    obj.disabled=true;
    clearTimeout(timer);
    document.getElementById("loading").style.display = "flex";
    let testInfo = obj.parentElement;
    $.ajax({
        method: 'POST',
        url: 'getPoints',
        data: {
            testId: testInfo.elements['testId'].value,
        },
        dataType: 'json',
        success: function (response){
            document.getElementById("resultField").append(response.points+"%");
            document.getElementById("loading").style.display="none";
            document.getElementById("results").style.display="flex";
        }
    })
}

function startTimer(obj){
    startTime = Math.floor(Date.now()/1000);
    let form = obj.parentElement;
    let finTestDate = form.elements['finAccDate'].value;
    let minToFin = form.elements['minToFin'].value;
    if(finTestDate!=""){
        console.log("Starting test with finTestDate");
        finTime=new Date(form.elements['finAccDate'].value);
        duration = Math.floor((finTime-startTime)/1000);
        console.log(duration);
        timer = setInterval(updateTimer, 1000);
    } else if (minToFin!=""&&minToFin!=0){
        duration = Number.parseInt(minToFin) * 60;
        console.log("Starting test with minToFin");
        console.log(duration);
        timer = setInterval(updateTimer, 1000);
    } else {
        console.log("Starting test without timer");
    }
}

function updateTimer(){
    let currentTime = Math.floor(Date.now()/1000);
    let elapsedTime = currentTime - startTime;
    let remainingTime = duration - elapsedTime;
    if(remainingTime>0){
        let hours = Math.floor(remainingTime/3600);
        let minutes = Math.floor((remainingTime/60)%60);
        let seconds = remainingTime%60;
        console.log(hours+":"+minutes+":"+seconds);
        let formattedTime =
            ("0" + hours).slice(-2) +
            ":" +
            ("0" + minutes).slice(-2) +
            ":" +
            ("0" + seconds).slice(-2);

        document.getElementById("timer").textContent = formattedTime;
    } else {
        clearTimeout(timer);
        let qDivs = document.querySelectorAll('[id^="q"]');
        for (let qDiv of qDivs){
            qDiv.style.display="none";
        }
        document.getElementById("endTest").style.display="flex";
        getTestPoints();
    }
}

function passQuestion(obj){
    let qid = obj.id.slice(0,-1);
    obj.remove();
    document.getElementById(qid).style.display="flex";
    document.getElementById("endTest").style.display="none";
    let el = document.getElementById(qid).querySelector("form").elements['skip'];
    el.setAttribute("onclick", "goToEndTest(this)");
}

function goToEndTest(obj){
    obj.parentElement.style.display="none";
    document.getElementById("endTest").style.display="flex";
}
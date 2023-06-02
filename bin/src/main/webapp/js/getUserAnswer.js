function getAnswer(obj){
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
        success: function (response){
            let div=document.createElement("div");
            div.innerHTML="Відповідь записано";
            form.append(div);
            obj.disabled = 'true';
        },
        error: function (error){
            alert("Error: "+error);
        }
    });
}
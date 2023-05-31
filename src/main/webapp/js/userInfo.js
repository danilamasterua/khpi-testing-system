const ALERT_DANGER="alert-danger"
const ALERT_SUCCESS= "alert-success"
function openChangePasswordForm(){
    document.getElementById("change-password").style.display="flex";
}

function isRightFormatCheck(value){
    let pattern = /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d\S]{8,}$/gm;
    if (pattern.test(value)) {
        let message = 'Формат правильний';
        showAlert(message, ALERT_SUCCESS);
        document.getElementById("pwd2").disabled=false;
    } else {
        let message = "Пароль повинен:<br>" +
            "- Складатися щонайменше з 8 символів<br>" +
            "- Містити тільки латинські літери, цифри (0-9), та спецсимволи<br>" +
            "- Містити щонайменше одну літеру та одну цифру";
        showAlert(message, ALERT_DANGER);
        document.getElementById("pwd2").disabled=true;
        document.getElementById("chBtn").disabled=true;
    }
}

function showAlert(message, alertClass){
    let box = document.getElementById("alert-box");
    box.innerHTML = "<div class='alert "+alertClass+"'><b>Повідомлення</b><hr>"+message+"</div>"
}

function preparePasswordChanging(){
    document.getElementById("chBtn").disabled=true;
    let form = document.getElementById("chBtn").parentElement;
    let data = {
        userId: form.elements['userId'].value,
        oldPassword: form.elements['oldPassword'].value,
        newPassword: form.elements['newPassword2'].value
    }
    $.ajax({
        method: 'post',
        url: 'prepareChangePassword',
        data:{
            data: JSON.stringify(data)
        },
        dataType: 'json',
        success: function (response) {
            let code = response.errorCode;
            switch (code){
                case 200:{
                    showAlert("Перевірте пошту", ALERT_SUCCESS);
                    document.getElementById("change-password").style.display="none";
                    document.getElementById("verification").style.display="flex";
                    break;
                }
                case 500:{
                    showAlert("Невідома помилка", ALERT_DANGER);
                    break;
                }
                case 401:{
                    showAlert("Пароль неправильний", ALERT_DANGER);
                    break;
                }
                default:{
                    showAlert("Невідома помилка", ALERT_DANGER);
                }
            }
        }
    })
}

function checkPasswords(value){
    if(value===document.getElementById("pwd1").value){
        showAlert("Паролі збігаються", ALERT_SUCCESS);
        document.getElementById("chBtn").disabled=false;
    } else {
        showAlert("Паролі не збігаються", ALERT_DANGER);
        document.getElementById("chBtn").disabled=true;
    }
}

function changePassword(){
    let form = document.getElementById("verBtn").parentElement;
    document.getElementById("verBtn").disabled=true;
    $.ajax({
        method: 'post',
        url:'changePassword',
        data:{
            userId: form.elements['userId'].value,
            verCode: form.elements['verCode'].value
        },
        dataType: 'json',
        success: function (response){
            let code = response.errorCode;
            switch(code){
                case 200:
                    showAlert("Пароль успішно змінено" , ALERT_SUCCESS);
                    break;
                case 500:
                    showAlert("Невідома помилка", ALERT_DANGER);
                    break;
                case 401:
                    showAlert("Невірно введений код, або строк дії коду минув", ALERT_DANGER);
                    setTimeout(
                        $.ajax({
                        method: "get",
                        url:"exit"
                    }), 5000);
                    break;
                default:
                    showAlert("Невідома помилка", ALERT_DANGER);
            }
        }
    })
}
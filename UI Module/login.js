
$('#loginForm').submit(async function (event) {
    event.preventDefault();
    var isValid = await validateLogin(event);
    if (isValid) {
        var username = document.getElementById("email").value;
        sessionStorage.setItem('username', username);
        await getUserID();
        window.location.href = "index.html";
    } else {
        alert("Incorrect Login Credentials.")
    }
});

$('#signupForm').submit(async function (event) {
    event.preventDefault();
    var isValid = await newsignup(event);

    if (isValid == true) {
        $('#signupmodal').modal('hide');
        alert("Account Created, please proceed to login")
    } else {
        document.getElementById("signupForm").reset();
        alert(isValid)
        $('#signupmodal').modal('hide');
    }
});

async function newsignup(event) {
    event.preventDefault();
    var username = document.getElementById("signup_username").value;
    var email = document.getElementById("signup_email").value;
    var password = document.getElementById("signup_password").value;
    var toSend = {"user_name": username, "email": email, "password": password};
    const response =
        await fetch(url + "accounts/newAccount", { method: 'POST', headers: { "Content-Type": "application/json" }, mode: 'cors', body: JSON.stringify(toSend) });
    const data = await response.json();
    if (data.status == 200) {
        return true;
    }
    else {
        return data.message;
    }
}

async function validateLogin(event) {
    event.preventDefault();
    var username = document.getElementById("email").value;
    var password = document.getElementById("password").value;
    if (username == "" || password == "") {
        alert("Please enter username and password");
        return;
    }
    var toSend = { "user_name": username, "password": password };
    const response =
        await fetch(url + "accounts/loginValidation", { method: 'POST', headers: { "Content-Type": "application/json" }, mode: 'cors', body: JSON.stringify(toSend) });
    const data = await response.json();
    if (data.message == "Account successfully verified") {
        return true;
    }
    return false;
}

async function getUserID() {
    let requestParam = {
        headers: { "content-type": "charset=UTF-8" },
        mode: 'cors', // allow cross-origin resource sharing
        method: 'GET',
    }
    var username = document.getElementById("email").value;
    const response =
        await fetch(
            url + "accounts/getAccountbyUsername/" + username, requestParam
        );
    const data = await response.json();
    var resp = data.message[0].user_id;
    sessionStorage.setItem('userID', resp);
}

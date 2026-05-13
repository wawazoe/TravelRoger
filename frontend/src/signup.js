function signup() {

    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8081/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `email=${email}&password=${password}`
    })
    .then(res => res.text())
    .then(data => {

        if (data === "OK") {
            alert("登録成功");
            location.href = "login.html";
        } else {
            alert("登録失敗");
        }
    });
}
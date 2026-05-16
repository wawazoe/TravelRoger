console.log("signup.js loaded");

function signup() {

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

console.log(name);
console.log(email);
console.log(password);

    fetch("http://localhost:8081/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `name=${name}&email=${email}&password=${password}`
    })
    .then(res => res.text())
    .then(data => {

        if (data === "OK") {
            alert("登録成功");
            location.href = "main.html";
        } else {
            alert("登録失敗");
        }
    });
}
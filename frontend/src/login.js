function login() {
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8081/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `email=${email}&password=${password}`
    })
    .then(res => res.text())
    .then(data => {
        if (data !== "NG") {
            localStorage.setItem("username", data);
            location.href = "main.html";
        } else {
            alert("ログイン失敗");
        }
    });
}
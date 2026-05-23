function signup() {

    const name = document.getElementById("name").value;
    const email = document.getElementById("email").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8081/signup", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `name=${name}&email=${email}&password=${password}`
    })
    .then(res => res.text())
    .then(data => {

        if (data !== "NG") {
            localStorage.setItem("username", data);
            alert("登録成功");
            location.href = "main.html";
        } else {
            alert("登録失敗");
        }
    });
}
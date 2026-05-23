const username = localStorage.getItem("username");

document.getElementById("welcome").innerText =
    username + "さん、ようこそ";
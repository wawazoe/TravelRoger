function saveRecord() {
    console.log("record button clicked");
    const title = document.getElementById("title").value;
    const event_date = document.getElementById("event_date").value;
    const purpose = document.getElementById("purpose").value;
    const location = document.getElementById("location").value;
    const transportation = document.getElementById("transportation").value;
    const impression = document.getElementById("impression").value;

    fetch("http://localhost:8081/record", {
        method: "POST",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded"
        },
        body: `title=${title}` +
              `&event_date=${event_date}` +
              `&purpose=${purpose}` +
              `&location=${location}` +
              `&transportation=${transportation}` +
              `&impression=${impression}`
    })
    .then(res => res.text())
    .then(data => {
        if (data === "OK") {
            alert("投稿成功");
            location.href = "main.html";
        } else {
            alert("投稿失敗");
        }
    });
}
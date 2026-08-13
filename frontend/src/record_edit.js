console.log("record_edit.js 読み込まれた");

// 編集する投稿のデータを取得
window.onload = function() {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    fetch("http://localhost:8081/record?id=" + id)
        .then(res => res.json())
        .then(data => {
            document.getElementById("title").value = data.title;
            document.getElementById("event_date").value = data.event_date;
            document.getElementById("purpose").value = data.purpose;
            document.getElementById("location").value = data.location;
            document.getElementById("transportation").value = data.transportation;
            document.getElementById("impression").value = data.impression;
        });
};


// 保存ボタン
document.getElementById("saveBtn").onclick = function() {

    console.log("保存");

};
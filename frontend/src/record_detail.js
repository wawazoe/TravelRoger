const params = new URLSearchParams(location.search);

const id = params.get("id");

fetch("http://localhost:8081/record?id=" + id)
.then(response => response.json())
.then(record => {

    document.getElementById("title").textContent =
        record.title;

    document.getElementById("event_date").textContent =
        record.event_date;

    document.getElementById("purpose").textContent =
        record.purpose;

    document.getElementById("location").textContent =
        record.location;

    document.getElementById("transportation").textContent =
        record.transportation;

    document.getElementById("impression").textContent =
        record.impression;
});
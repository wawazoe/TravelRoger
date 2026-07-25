fetch("http://localhost:8081/history")
    .then(response => response.json())
    .then(data => {

        const list = document.getElementById("history-list");

        data.forEach(record => {

            list.innerHTML += `
                <div>
                    <h2>${record.title}</h2>
                    <p>日付：${record.event_date}</p>
                    <p>目的：${record.purpose}</p>
                    <p>場所：${record.location}</p>
                    <hr>
                </div>
            `;

        });

    });
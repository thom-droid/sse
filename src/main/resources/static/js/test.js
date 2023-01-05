
const url = 'http://localhost:8080/sse';
const request = new Request(url);
const sse = new EventSource(url);

sse.onmessage = function (evt) {
    console.log(sse.readyState);
    var el = document.getElementById('test');
    el.appendChild(document.createTextNode(evt.data));
    el.appendChild(document.createElement('br'));
};

sse.onerror = function (){
    console.log("timed out. Reestablishing the connection...");

    fetch(request).then((response) => {

        const status = response.status;
        console.log(`connection reestablished. ${status}`);

    }).catch((error) =>{
        console.log(`there is no server running. ${error}`);
        sse.close();
    });


}


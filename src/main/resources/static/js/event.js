var uuid = document.getElementById('uuid');
var url = 'http://localhost:8080/event-stream?member-uuid=' + uuid.innerText;
var sse = new EventSource(url);

sse.onmessage = function (evt) {
    var el = document.getElementById('notification');
    el.appendChild(document.createTextNode(evt.data));
    el.appendChild(document.createElement('br'));
};

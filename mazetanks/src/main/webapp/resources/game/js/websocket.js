
var socket = new WebSocket("ws://localhost:9080/tanks/pipe");

socket.onopen = function() {
  console.info("Соединение установлено.");
};

socket.onclose = function(event) {
  if (event.wasClean) {
      console.info("Соединение закрыто.");
  } else {
      console.error('Обрыв соединения'); // например, "убит" процесс сервера
  }
  //alert('Код: ' + event.code + ' причина: ' + event.reason);
};

socket.onmessage = function(event) {
    moveTank(event.data);
};

socket.onerror = function(error) {
  console.error("Ошибка " + error.message);
};


function sendMessage (message) {
    socket.send(message);
}




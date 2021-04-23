var stompClient = null;

function connect() {	
	var socket = new SockJS('/stomp');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
    });
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    console.log("Disconnected");
}


function sendCommentary() {
    stompClient.send("/app/live-comment", {}, JSON.stringify({'commentary': $("#commentary").val()}));
    $("#commentary").val('');
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    
    $("#publish").click(function() { sendCommentary(); });
});


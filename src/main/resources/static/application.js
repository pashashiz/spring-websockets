class WebSocketExample {

    constructor(webSocketEndpoint) {
        let socket = new SockJS(webSocketEndpoint);
        this.stompClient = Stomp.over(socket);
    }

    connectAndSubscribe(messageTopic) {
        console.log('Connecting...');
        this.stompClient.connect({}, (frame) => {
            console.log(`Connected:  + ${frame}`);
            this.stompClient.subscribe(messageTopic, (greeting) => {
                console.log('Received from server: ' + JSON.parse(greeting.body).message);
            });
        });
    }

    send(endpoint, message) {
        console.log(`Sending ${message}...`);
        this.stompClient.send(endpoint, {}, message);
    }

}

let example = new WebSocketExample('/websocket');
example.connectAndSubscribe('/topic/messages');
setInterval(() => {
    example.send('/app/hello', JSON.stringify({'name': 'Pavlo'}));
}, 5000);

var webSocket;
var msgId = 0;
var futures = {};

function getWsUrl(port, endpoint) {
    var l = window.location;
    return ((l.protocol === "https:") ? "wss://" : "ws://") + l.hostname + ":" + port + endpoint;
}

var endpoint = getWsUrl(8080, "/grava");

function GameClient() {
    this.sessionId = null;
    this.side      = null;

    this.login = function(params) {
        if (params.session === undefined || params.side === undefined) {
            console.log("Invalid login message");
            return;
        }

        this.sessionId = params.session;
        this.side      = params.side;

        if (this.side == "A") {
            notify('Waiting for opponent');
        }
    };

    this.update = function(params) {
        if (params.constructor !== Array) {
            console.log("Invalid update message");
            return;
        }

        for (var i = 13; i >= 0; --i) {
            $('#p' + i).text(params[i]);
        }
    };

    this.turn = function (params) {
        if (params.side === undefined || params.turnType === undefined) {
            console.log("Invalid turn message");
            return;
        }

        var side     = params.side;
        var turnType = params.turnType;

        var thisSide = side == this.side;
        if (turnType != "PLAYER") {
            toggleSowButtons(false);

            if (thisSide) {
                notify('Game over, you win');
            } else {
                notify('Game over, opponent wins');
            }
        } else {
            toggleSowButtons(thisSide);

            if (thisSide) {
                notify('Your turn');
            } else {
                notify("Opponent's turn");
            }
        }
    }
}

var gameClient = new GameClient();

function notify(msg) {
    $('#info').text(msg);
}

function toggleSowButtons(enable) {
    if (enable) {
        for (var i = 5; i >= 0; --i) {
            $('#sowButton' + i).removeAttr('disabled');
        }
    } else {
        for (var i = 5; i >= 0; --i) {
            $('#sowButton' + i).attr('disabled', 'disabled');
        }
    }
}

function onDefault(msg) {
    console.log("Default handler called");

    if (msg.error !== undefined && msg.error.message !== undefined) {
        console.log(msg.error.message);
        notify(msg.error.message);
    }
}

function connect() {
    // Ensures only one connection is open at a time
    if (this.webSocket !== undefined && this.webSocket.readyState !== WebSocket.CLOSED) {
        console.log("WebSocket is already opened.");
        return;
    }
    // Create a new instance of the websocket
    this.webSocket = new WebSocket(this.endpoint);

    var isMessageValid = this.isMessageValid;
    var onNotify       = this.onNotify;

    this.webSocket.onmessage = function(event) {
        console.log(event.data);

        var msg = JSON.parse(event.data);
        if (!isMessageValid(msg)) {
            console.log("Invalid message");
            return;
        }

        if (msg.id !== undefined) {
            var callback = futures[msg.id];
            callback(msg.result, msg.error);
        } else {
            onNotify(msg);
        }
    };

    this.webSocket.onclose = function(event) {
        console.log("Connection closed");

        toggleSowButtons(false);
        notify('Connection closed');
    };
}

function isMessageValid(msg) {
    return !(msg.jsonrpc === undefined || msg.jsonrpc != "2.0")
}

function onNotify(msg) {
    if (msg.method === undefined)
        console.log("Invalid notification");

    var method = gameClient[msg.method];
    if (method !== undefined) {
        method(msg.params);
    } else {
        console.log("Method " + msg.method + " not found");
        onDefault(msg);
    }
}

function sow(idx) {
    var params = {
        index: idx.toString()
    };
    this.invoke('sow', params, this.onSow);
}

function onSow(result, error) {
    if (error !== undefined && error.message !== undefined) {
        console.log(error.message);
        notify(error.message);
    }
}

function invoke(method, params, callback) {
    var msg = {
        jsonrpc: "2.0",
        method:  method,
        params:  params,
        id:      (++this.msgId).toString()
    };

    this.futures[this.msgId] = callback;

    console.log(JSON.stringify(msg));

    this.webSocket.send(JSON.stringify(msg));
}
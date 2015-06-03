<!DOCTYPE html>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Grava Hal</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width">

    <script src="js/jquery-2.1.4.min.js"></script>

    <script type="text/javascript">

        var endpoint = "ws://localhost:8080/grava";

        var webSocket;
        var msgId = 0;

        var futures = {};

        function GameClient() {
            this.sessionId = null;
            this.side      = null;

            this.login = function (params) {
                if (params.session === undefined || params.side === undefined) {
                    console.log("Invalid login message");
                    return;
                }

                this.sessionId = params.session;
                this.side      = params.side;
            };

            this.update = function (params) {
                if (params.constructor !== Array) {
                    console.log("Invalid update message");
                    return;
                }

                for (var i = 13; i >= 0; --i)
                    $('#p' + i).text(params[i]);
            };

            this.turn = function (params) {
                if (params.side === undefined || params.turnType === undefined) {
                    console.log("Invalid turn message");
                    return;
                }

                var side     = params.side;
                var turnType = params.turnType;

                if (turnType != "PLAYER") {
                    $('#info').text('Game over');
                    toggleSowButtons(false);
                }
                else
                    toggleSowButtons(side == this.side);
            };

            this.start = function (params) {
                $('#info').text('Game started');
            };

            this.default = function(msg) {
                console.log("default handler");

                if (msg.error !== undefined && msg.error.message !== undefined) {
                    console.log(msg.error.message);
                    $('#info').text(msg.error.message);
                }
            };
        };

        var gameClient = new GameClient();

        function toggleSowButtons(enable) {
            if (enable)
                for (var i = 5; i >= 0; --i)
                    $('#sowButton' + i).removeAttr('disabled');
            else
                for (var i = 5; i >= 0; --i)
                    $('#sowButton' + i).attr('disabled','disabled');
        }

        function openSocket() {
            // Ensures only one connection is open at a time
            if (webSocket !== undefined && webSocket.readyState !== WebSocket.CLOSED){
                console.log("WebSocket is already opened.");
                return;
            }
            // Create a new instance of the websocket
            webSocket = new WebSocket(endpoint);

            webSocket.onopen = function(event){
                if (event.data === undefined)
                    return;
            };

            webSocket.onmessage = function(event) {
                console.log(event.data);

                var msg = JSON.parse(event.data);
                if (!isMessageValid(msg)) {
                    console.log("Invalid message");
                    return;
                }

                if (msg.id !== undefined) {
                    var callback = futures[msg.id];
                    callback(msg.result, msg.error);
                    return;
                }
                else {
                    onNotify(msg);
                }
            };

            webSocket.onclose = function(event) {
                console.log("Connection closed");
            };
        }

//        openSocket();

        function isMessageValid(msg) {
            if (msg.jsonrpc === undefined || msg.jsonrpc != "2.0")
                return false;
            return true;
        }

        function onNotify(msg) {
            if (msg.method === undefined)
                console.log("Invalid notification");

            var method = gameClient[msg.method];
            if (method !== undefined) {
                method(msg.params);

            }
            else {
                console.log("Method " + msg.method + " not found");
                gameClient.default(msg);
            }
        }

        function sow(idx) {
            var params = {
                index: idx.toString()
            };
            invokeMethod('sow', params, onSow);
        }

        function onSow(result, error) {
            console.log("onSow called");

            if (error !== undefined && error.message !== undefined) {
                console.log(error.message);
                $('#info').text(error.message);
            }
        }

        function invokeMethod(method, params, callback) {
            var msg = {
                jsonrpc: "2.0",
                method:  method,
                params:  params,
                id:      (++msgId).toString()
            }

            futures[msgId] = callback;

            console.log(JSON.stringify(msg));

            webSocket.send(JSON.stringify(msg));
        }

        function closeSocket() {
            //$('#closeButton').hide();
            //$('#sendButton').hide();

            webSocket.close();
        }

    </script>

</head>
<body>

<div>
    <div>
        <label id="info" />
    </div>
    <div>
        <table>
            <tr>
                <td rowspan="2"><label id="p13">0</label></td>
                <td><label id="p12">0</label></td>
                <td><label id="p11">0</label></td>
                <td><label id="p10">0</label></td>
                <td><label id="p9">0</label></td>
                <td><label id="p8">0</label></td>
                <td><label id="p7">0</label></td>
                <td rowspan="2"><label id="p6">0</label></td>
            </tr>
            <tr>
                <td><label id="p0">0</label></td>
                <td><label id="p1">0</label></td>
                <td><label id="p2">0</label></td>
                <td><label id="p3">0</label></td>
                <td><label id="p4">0</label></td>
                <td><label id="p5">0</label></td>
            </tr>
            <tr>
                <td></td>
                <td><button id="sowButton0" onclick="sow(0);" disabled>&gt;</button></td>
                <td><button id="sowButton1" onclick="sow(1);" disabled>&gt;</button></td>
                <td><button id="sowButton2" onclick="sow(2);" disabled>&gt;</button></td>
                <td><button id="sowButton3" onclick="sow(3);" disabled>&gt;</button></td>
                <td><button id="sowButton4" onclick="sow(4);" disabled>&gt;</button></td>
                <td><button id="sowButton5" onclick="sow(5);" disabled>&gt;</button></td>
                <td></td>
            </tr>
        </table>
    </div>
</div>

<div>
    <button onclick="openSocket();">Open</button>
    <button id="closeButton" onclick="closeSocket();">Close</button>
</div>

<!-- Server responses get written here -->
<div>
    <label id="messages" />
</div>

</body>
</html>
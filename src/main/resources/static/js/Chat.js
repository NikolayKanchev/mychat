/*<![CDATA[*/

(function ()
{
    var userName = document.getElementById('userName').value;

    var chatLogs = document.getElementById('chatLogs');

    var quitBtn = document.getElementById("quitBtn");

    quitBtn.onclick = function ()
    {
        sendMessage(stompClient,"/app/visible", userName, false);

        updateListUsers(stompClient,"/app/listUsers", userName, "listUsers");

        setTimeout(function ()
        {
            window.location.href="index.html";

        }, 500);
    };

    var ws = new SockJS("/chat");

    var stompClient = Stomp.over(ws);

    stompClient.connect({}, function (frame)
    {
        updateListUsers(stompClient,"/app/listUsers", userName, "listUsers");

        stompClient.subscribe("/topic/listUsers", function (listUsers)
        {
            var users = JSON.parse(listUsers.body);

            var usersRows = document.getElementById("rows");

            usersRows.innerHTML = "";

            for(var key in users)
            {
                if (users[key] === true)
                {
                    usersRows.innerHTML +=
                        "<div class=\"row\">\n" +
                        "<div class=\"online\"></div>\n" +
                        "<p class=\"user\">" + key + "</p>\n" +
                        "</div>\n";
                } else
                {
                    usersRows.innerHTML +=
                        "<div class=\"row\">\n" +
                        "<div class=\"notOnline\"></div>\n" +
                        "<p class=\"user\">" + key + "</p>\n" +
                        "</div>\n";
                }
            }

        });

        stompClient.subscribe("/topic/chat", function (message)
        {
            var gson = JSON.parse(message.body);

            if(gson.userName !== userName)
            {
                chatLogs.innerHTML +=
                    "<div class=\"chat friend\">\n" +
                    "<div class=\"userName friend\">" + gson.userName + "</div>\n" +
                    "<p class=\"chatMessage friend\">" + gson.message + "</p>\n" +
                    "</div>";
            }else
            {
                chatLogs.innerHTML +=
                    "<div class=\"chat self\">\n" +
                    "<div class=\"userName self\">"+ gson.userName +"</div>\n" +
                    "<p class=\"chatMessage self\">"+ gson.message +"</p>\n" +
                    "</div>";
            }
        });
    }, function (error)
    {
        console.log("STOMP protocol error " + error);
    });

    document.querySelector('button').onclick = function ()
    {
        var messageText = document.getElementById('message').value;

        sendMessage(stompClient,"/app/chat", userName, messageText);

    };

    var checkBox = document.getElementById('checkbox');

    checkBox.onclick = function ()
    {
        var messageText;

        if (checkBox.checked)
        {
            messageText = false;
        }else
        {
            messageText = true;
        }

        sendMessage(stompClient,"/app/visible", userName, messageText);

        updateListUsers(stompClient,"/app/listUsers", userName, "listUsers");

    };
})();

function sendMessage(stompClient ,path, userName, messageText)
{
    var jsonObject = JSON.stringify({'userName' : userName, "message" : messageText});

    stompClient.send(path,{}, jsonObject);
}

function updateListUsers(stompClient, path, userName, messageText)
{
    setTimeout(function ()
    {
        sendMessage(stompClient, path, userName, messageText);

    }, 500);
}
/*]]>*/



//region simple WebSocket working with spring
//            var ws = new WebSocket('ws://localhost:8000/chat');
//
//            var chatLogs = document.getElementById('chatLogs');
//
//            ws.onmessage = function (data)
//            {
//                chatLogs.innerHTML += "<p>" + data.data +"</p>";
//            };
//
//            document.querySelector('button').onclick = function ()
//              {
//                var messageText = document.getElementById('message').value;
//
//                ws.send(messageText)
//            };
//endrerion

//region this is working wit javascript server
//            var userName = prompt('What is your username?');
//
//            var sock = new WebSocket("ws://localhost:5001");
//
//            var chatLogs = document.getElementById('chatLogs');
//
//            sock.onopen = function(){
//                sock.send(JSON.stringify({
//                    type: "name",
//                    data: userName
//                }));
//            };
//
//            sock.onmessage = function (event) {
//
//                console.log(event);
//
//                var json = JSON.parse(event.data);
//
//                chatLogs.innerHTML += "<p>" + json.name + ": " + json.data +"</p>";
//            };
//
//            document.querySelector('button').onclick = function () {
//
//                var messageText = document.getElementById('message').value;
//
//                sock.send(JSON.stringify({
//                    type: "message",
//                    data: messageText
//                }));
//
//                chatLogs.innerHTML += "<p> You: " + messageText + "</p>";
//            };
//endregion
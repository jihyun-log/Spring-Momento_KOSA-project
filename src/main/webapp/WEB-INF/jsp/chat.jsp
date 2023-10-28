<%--정용준: 이 페이지는 실시간 채팅 및 파일 업로드를 지원하는 웹 애플리케이션의 일부분입니다. --%>
<%--       사용자는 닉네임을 설정하고 메시지를 보내며, 받은 메시지는 웹 소켓을 통해 실시간으로 업데이트됩니다.--%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>채팅</title>
    <!-- Include Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            padding: 25px;
        }
        .chating {
            background-color: #F6F6F6;
            width: 500px;
            height: 500px;
            overflow: auto;
        }
        .chating .me {
            color: #000;
            text-align: left;
            font-weight: bold;
        }
        .chating .others {
            color: #333;
            text-align: left;
        }
        #yourMsg {
            display: none;
            border: 1px solid #ccc; /* 1px 두께의 실선 테두리, 다른 스타일도 적용 가능 */
            padding: 10px; /* 내부 여백을 조절하려면 원하는 크기로 설정하세요 */
        }
        .msgImg {
            width: 300px;
            height: 400px;
        }
        .clearBoth {
            clear: both;
        }
        .img {
            float: center;
        }
    </style>
</head>

<script type="text/javascript">
    var ws;

    function wsOpen(){
        //웹소켓 전송시 현재 방의 번호를 넘겨서 보낸다.
        ws = new WebSocket("ws://" + location.host + "/chating/"+$("#roomNumber").val());
        wsEvt();
    }

    function wsEvt() {
        ws.onopen = function(data){
            //소켓이 열리면 동작
        }

        ws.onmessage = function(data) {
            //메시지를 받으면 동작
            var msg = data.data;
            if(msg != null && msg.type != ''){
                //파일 업로드가 아닌 경우 메시지를 뿌려준다.
                var d = JSON.parse(msg);
                if(d.type == "getId"){
                    var si = d.sessionId != null ? d.sessionId : "";
                    if(si != ''){
                        $("#sessionId").val(si);
                    }
                }else if(d.type == "message"){
                    if(d.sessionId == $("#sessionId").val()){
                        $("#chating").append("<p class='me'>나: " + d.msg + "</p>");
                    }else{
                        $("#chating").append("<p class='others'>" + d.userName + ": " + d.msg + "</p>");
                    }

                }else{
                    console.warn("unknown type!");
                }
            }else{
                //파일 업로드한 경우 업로드한 파일을 채팅방에 뿌려준다.
                var url = URL.createObjectURL(new Blob([msg]));
                $("#chating").append("<div class='img'><img class='msgImg' src="+url+"></div><div class='clearBoth'></div>");
            }
        }

        document.addEventListener("keypress", function(e){
            if(e.keyCode == 13){ //enter press
                send();
            }
        });
    }

    function chatName(){
        var userName = $("#userName").val();
        if(userName == null || userName.trim() == ""){
            alert("사용자 이름을 입력해주세요.");
            $("#userName").focus();
        }else{
            wsOpen();
            $("#yourName").hide();
            $("#yourMsg").show();
        }
    }

    function send() {
        var option ={
            type: "message",
            roomNumber: $("#roomNumber").val(),
            sessionId : $("#sessionId").val(),
            userName : $("#userName").val(),
            msg : $("#chatting").val()
        }
        ws.send(JSON.stringify(option))
        $('#chatting').val("");
    }

    function fileSend(){
        var file = document.querySelector("#fileUpload").files[0];
        var fileReader = new FileReader();
        fileReader.onload = function() {
            var param = {
                type: "fileUpload",
                file: file,
                roomNumber: $("#roomNumber").val(),
                sessionId : $("#sessionId").val(),
                msg : $("#chatting").val(),
                userName : $("#userName").val()
            }
            ws.send(JSON.stringify(param)); //파일 보내기전 메시지를 보내서 파일을 보냄을 명시한다.

            arrayBuffer = this.result;
            ws.send(arrayBuffer); //파일 소켓 전송
        };
        fileReader.readAsArrayBuffer(file);
    }

    function scrollToBottom() {
        var chatingDiv = document.getElementById("chating");
        chatingDiv.scrollTop = chatingDiv.scrollHeight;
    }

    function send() {
        var option = {
            type: "message",
            roomNumber: $("#roomNumber").val(),
            sessionId: $("#sessionId").val(),
            userName: $("#userName").val(),
            msg: $("#chatting").val()
        }
        ws.send(JSON.stringify(option));
        $('#chatting').val("");
        scrollToBottom(); // 메시지를 보낸 후 스크롤을 아래로 이동
    }
</script>

<body>
<div id="container" class="container">
    <h1 class="display-6">방제: ${roomName}</h1>
    <input type="hidden" id="sessionId" value="">
    <input type="hidden" id="roomNumber" value="${roomNumber}">

    <div id="chating" class="chating">
    </div>

    <div id="yourName">
        <div class="row">
            <div class="col">
                <input type="text" class="form-control" id="userName" placeholder="닉네임을 입력하세요.">
            </div>
            <div class="col">
                <button class="btn btn-secondary" onclick="chatName()" id="startBtn">닉네임 설정</button>
                <a href="http://localhost:8080/room"><button class="btn btn-secondary">방 목록</button></a>
            </div>
        </div>
    </div>

    <div id="yourMsg">
        <div class="row">
            <div class="col">
                <input type="text" class="form-control" id="chatting" placeholder="메시지를 입력하세요.">
            </div>
            <div class="col">
                <button class="btn btn-secondary" onclick="send()" id="sendBtn">보내기</button>
                <a href="http://localhost:8080/room"><button class="btn btn-secondary">방 목록</button></a>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col">
                <input type="file" id="fileUpload"><button class="btn btn-secondary" onclick="fileSend()" id="sendFileBtn">파일 올리기</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
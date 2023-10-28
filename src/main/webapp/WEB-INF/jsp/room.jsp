<%--정용준: 이 HTML 코드는 채팅 애플리케이션의 메인 페이지를 나타내며, 채팅 방 목록을 표시하고 새로운 채팅 방을 만들거나 --%>
<%--       기존의 채팅 방에 참여하는 기능을 제공합니다.--%>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta name="_csrf" content="${_csrf.token}"/>
    <meta name="_csrf_header" content="${_csrf.headerName}"/>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <meta charset="UTF-8">
    <title>채팅</title>
    <!-- Include Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .container {
            padding: 25px;
        }
        .roomList th {
            background-color: #333;
            color: #fff;
            font-weight: bold;
        }
        .createRoomButton {
            background-color: #333;
            color: #fff;
            font-weight: bold;
        }
        .center-text {
            text-align: center;
        }
    </style>
</head>

<body>
<div class="container">
    <h1 class="display-6">채팅</h1>
    <div class="roomContainer">
        <table class="table table-bordered roomList">
            <thead>
            <tr>
                <th class="center-text num">번호</th>
                <th class="center-text room">방제</th>
                <th class="center-text go">입장하기</th>
            </tr>
            </thead>
            <tbody id="roomList"></tbody>
        </table>
    </div>
    <div class="inputTable row">
        <div class="col-8">
            <input type="text" class="form-control" name="roomName" id="roomName" placeholder="방제를 입력하세요.">
        </div>
        <div class="col-4">
            <button class="btn createRoomButton" id="createRoom">방 만들기</button>
        </div>
        <br><br>
        <div ><p style="margin-top: 55px;">AI와 대화하고 싶다면?</p></div>
        <div class="col-8">
            <a href="/chatgpt" class="btn chatGPTButton" style='background-color: #333; color: #fff; border: none; margin-top: 50px;'>ChatGPT와 대화</a>
        </div>
    </div>
</div>
<!-- Include Bootstrap JS and Popper.js -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript">
    var ws;
    window.onload = function(){
        getRoom();
        createRoom();
    }

    function getRoom(){
        commonAjax('/getRoom', "", 'post', function(result) {
            createChatingRoom(result);
        });
    }

    function createRoom(){
        $("#createRoom").click(function(){
            var msg = { roomName : $('#roomName').val() };

            commonAjax('/createRoom', msg, 'post', function(result) {
                createChatingRoom(result);
            });

            $("#roomName").val("");
        });
    }

    function goRoom(number, name){
        location.href="/moveChating?roomName="+name+"&"+"roomNumber="+number;
    }

    function createChatingRoom(res){
        if(res != null){
            var tag = "";
            res.forEach(function(d, idx){
                var rn = d.roomName.trim();
                var roomNumber = d.roomNumber;
                tag += "<tr>" +
                    "<td class='num text-center'>" + (idx + 1) + "</td>" +
                    "<td class='room text-center'>" + rn + "</td>" +
                    "<td class='go text-center'><button type='button' onclick='goRoom(\"" + roomNumber + "\", \"" + rn + "\")' class='btn btn-primary' style='background-color: #333; color: #fff; border: none;'>입장</button></td>" +
                    "</tr>";
            });
            $("#roomList").empty().append(tag);
        }
    }

    function commonAjax(url, parameter, type, callback, contentType) {
        var token = $("meta[name='_csrf']").attr("content");
        var header = $("meta[name='_csrf_header']").attr("content");

        $.ajax({
            url: url,
            data: parameter,
            type: type,
            contentType : contentType != null ? contentType : 'application/x-www-form-urlencoded; charset=UTF-8',
            beforeSend : function(xhr) {
                xhr.setRequestHeader(header, token);
            },
            success: function (res) {
                callback(res);
            },
            error : function(err){
                console.log('===> error');
                callback(err);
            }
        });
    }
</script>
</body>
</html>

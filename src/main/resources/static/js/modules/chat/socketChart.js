//消息对象数组
var msgObjArr = new Array();

var webSocket = null;
$("#user").val(merchantId);
//判断当前浏览器是否支持webSocket， springboot是项目名
if ('webSocket' in window) {
    webSocket = new WebSocket("ws://localhost:9001/web_socket/order_notification/" + merchantId+"/112");
} else {
    console.error("不支持webSocket");
}

//连接发生错误的回调方法
webSocket.onerror = function(){
    console.error("webSocket连接发生错误");
};

//先加密
let aesKey = aesUtil.genKey();//秘钥对// 二进制方式接收数据
//连接成功建立的回调方法
webSocket.onopen = function(event){
    //获取所有在线用户
    $.ajax({
        type: 'post',
        url: baseURL + "/webSocket/getOnlineList",
        contentType: 'application/json;charset=utf-8',
        dataType: 'json',
        data: {merchantId:merchantId},
        success: function (r) {
            if (r.data.length) {
                //列表
                for (var i = 0; i < r.data.length; i++) {
                    var username = r.data[i];
                    $("#hz-group-body").append("<div class=\"hz-group-list\"><input class='hz-group-list-input' id='inputQun' type='hidden' value='1'><span class='hz-group-list-merchantId'>" + username + "</span><span id=\"" + username + "-status\">[在线]</span><div id=\"hz-badge-" + username + "\" class='hz-badge'>0</div></div>");
                }

                //在线人数
                $("#onlineCount").text(r.data.length);
            }
        },
        error: function (xhr, status, error) {
            console.log("ajax错误！");
        }
    });
}

//接收到消息的回调方法
webSocket.onmessage = function(event){
    var messageJson = eval("(" + event.data + ")");
    console.log(messageJson);
    //普通消息(私聊)
    if (messageJson.type == "1") {
        //来源用户
        var fromUser = messageJson.fromUser;
        //目标用户
        var toUser = messageJson.toUser;
        //消息
        var message = messageJson.msg;

        //最加聊天数据
        setMessageInnerHTML(fromUser,fromUser, messageJson);
    }

    //普通消息(群聊)
    if (messageJson.type == "2"){
        //来源用户
        var fromUser = messageJson.fromUser;
        //目标用户
        var toUser = messageJson.toUser;
        //消息
        var message = messageJson.msg;
        console.log($("#user").val() +"-----------"+fromUser)
        //最加聊天数据
        setMessageInnerHTML($("#user").val(),fromUser, messageJson);
    }

    //对方不在线
    if (messageJson.type == "0"){
        //消息
        var message = messageJson.msg;

        $("#hz-message-body").append(
            "<div class=\"hz-message-list\" style='text-align: center;'>" +
                "<div class=\"hz-message-list-text\">" +
                    "<span>" + message + "</span>" +
                "</div>" +
            "</div>");
    }

    //在线人数
    if (messageJson.type == "onlineCount") {
        //取出merchantId
        var onlineCount = messageJson.onlineCount;
        var merchantId = messageJson.merchantId;
        var oldOnlineCount = $("#onlineCount").text();

        //新旧在线人数对比
        if (oldOnlineCount < onlineCount) {
            if($("#" + merchantId + "-status").length > 0){
                $("#" + merchantId + "-status").text("[在线]");
            }else{
                $("#hz-group-body").append("<div class=\"hz-group-list\"><input class='hz-group-list-input' id='inputQun' type='hidden' value='1'><span class='hz-group-list-merchantId'>" + merchantId + "</span><span id=\"" + merchantId + "-status\">[在线]</span><div id=\"hz-badge-" + merchantId + "\" class='hz-badge'>0</div></div>");
            }
        } else {
            //有人下线
            $("#" + merchantId + "-status").text("[离线]");
        }
        $("#onlineCount").text(onlineCount);
    }

}

//连接关闭的回调方法
webSocket.onclose = function () {
    alert("webSocket连接关闭");
}

//将消息显示在对应聊天窗口    对于接收消息来说这里的tomerchantId就是来源用户，对于发送来说则相反
function setMessageInnerHTML(fromUser,toUser, message) {
    //判断
    var childrens = $("#hz-group-body").children(".hz-group-list");
    var isExist = false;
    for (var i = 0; i < childrens.length; i++) {
        var text = $(childrens[i]).find(".hz-group-list-merchantId").text();
        if (text == fromUser) {
            isExist = true;
            break;
        }
    }
    if (!isExist) {
        //追加聊天对象
        msgObjArr.push({
            fromUser: fromUser,
            message: [message]//封装数据
        });
        $("#hz-group-body").append("<div class=\"hz-group-list\">"+
        "<input class='hz-group-list-input' id='inputQun' type='hidden' value='1'>"+
        "<span class='hz-group-list-merchantId'>" + fromUser + "</span>"+
        "<span class='hz-group-nickName'>" + message.nickName + "</span>"+
        "<span id=\"" + fromUser + "-status\">[在线]</span>"+
        "<div id=\"hz-badge-" + fromUser + "\" class='hz-badge'>0</div></div>");

    } else {
        //取出对象
        var isExist = false;
        for (var i = 0; i < msgObjArr.length; i++) {
            var obj = msgObjArr[i];
            if (obj.fromUser == fromUser) {
                //保存最新数据
                obj.message.push(message);
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            //追加聊天对象
            msgObjArr.push({
                fromUser: fromUser,
                message: [message]//封装数据
            });
        }
    }

    // 对于接收消息来说这里的tomerchantId就是来源用户，对于发送来说则相反
    var merchantId = $("#toUser").text();

    //刚好打开的是对应的聊天页面
    if (fromUser == merchantId) {
        $("#hz-message-body").append(
            "<div class=\"hz-message-list\">" +
                "<p class='hz-message-list-merchantId'>"+message.nickName+" "+ message.date +"：</p>" +
            "<div class=\"hz-message-list-text left\">" +
                "<span>" + message.msg + "</span>" +
            "</div>" +
            "<div style=\" clear: both; \"></div>" +
            "</div>");
    } else {
        //小圆点++
        var conut = $("#hz-badge-" + fromUser).text();
        $("#hz-badge-" + fromUser).text(parseInt(conut) + 1);
        $("#hz-badge-" + fromUser).css("opacity", "1");
    }
}

//发送消息
function send() {
    //目标用户名
    var tarUserName = $("#user").val();
    //登录用户名
    var srcUserName = $("#talks").text();
    if(tarUserName ==null || tarUserName == ""){
        alert("请选择联系人");
        return ;
    }
    var message = {
        msg: $("#talkwords").html(),
        fromUser: srcUserName,
        nickName: getRandomText(),
        toUser: tarUserName,
        type: 1,
        code: 1,
        date: NowTime()
    };
       if (message.msg == "") {
            alert("访问消息不能为空");
       } else {
        let data = {
            data:aesUtil.encrypt(JSON.stringify(message),aesKey),aesKey
            }
           webSocket.send(JSON.stringify(data));
       }
    $("#hz-message-body").append(
        "<div class=\"hz-message-list\">" +
        "<div class=\"hz-message-list-text right\">" +
        "<span>" + message.msg + "</span>" +
        "</div>" +
        "</div>");
    $("#talkwords").html("");
    //取出对象
    if (msgObjArr.length > 0) {
        var isExist = false;
        for (var i = 0; i < msgObjArr.length; i++) {
            var obj = msgObjArr[i];
            if (obj.fromUser == message.fromUser) {
                //保存最新数据
                obj.message.push(message);
                isExist = true;
                break;
            }
        }
        if (!isExist) {
            //追加聊天对象
            msgObjArr.push({
                fromUser: message.toUser,
                message: [message]//封装数据[{merchantId:huanzi,message:"你好，我是欢子！",date:2018-04-29 22:48:00}]
            });
        }
    } else {
        //追加聊天对象
        msgObjArr.push({
            fromUser: message.fromUser,
            message: [message]//封装数据[{merchantId:huanzi,message:"你好，我是欢子！",date:2018-04-29 22:48:00}]
        });
    }
}
//监听点击用户
$("body").on("click", ".hz-group-list", function () {
    $(".hz-group-list").css("background-color", "");
    $(this).css("background-color", "whitesmoke");
    $("#user").val($(this).find(".hz-group-list-merchantId").text());
    $("#getUid").val($(this).find("#inputQun").val());
    $("#toUser").text($(this).find(".hz-group-list-merchantId").text());

    //清空旧数据，从对象中取出并追加
    $("#hz-message-body").empty();
    $("#hz-badge-" + $("#toUser").text()).text("0");
    $("#hz-badge-" + $("#toUser").text()).css("opacity", "0");
    if (msgObjArr.length > 0) {
        for (var i = 0; i < msgObjArr.length; i++) {
            
            var obj = msgObjArr[i];
            if (obj.fromUser == $("#toUser").text()) {
                //追加数据
                var messageArr = obj.message;
                if (messageArr.length > 0) {
                    for (var j = 0; j < messageArr.length; j++) {
                        var msgObj = messageArr[j];
                        var leftOrRight = "right";
                        var message = msgObj.msg;
                        var fromUser =  msgObj.fromUser;
                        var dateTime =  msgObj.date;
                        var toUser = $("#toUser").text();

                        //当聊天窗口与fromUser的人相同，文字在左边（对方/其他人），否则在右边（自己）
                        if (fromUser == toUser) {
                            leftOrRight = "left";
                        }

                        //但是如果点击的是自己，群聊的逻辑就不太一样了
                        if (merchantId == toUser && fromUser != toUser) {
                            leftOrRight = "left";
                        }

                        if (merchantId == toUser && fromUser == toUser) {
                            leftOrRight = "right";
                        }

                        var magUserName = leftOrRight == "left" ? "<p class='hz-message-list-merchantId'>"+msgObj.nickName+" "+ dateTime +"：</p>" : "";

                        $("#hz-message-body").append(
                            "<div class=\"hz-message-list\">" +
                            magUserName+
                            "<div class=\"hz-message-list-text " + leftOrRight + "\">" +
                                "<span>" + message + "</span>" +
                            "</div>" +
                            "<div style=\" clear: both; \"></div>" +
                            "</div>");
                    }
                }
                break;
            }
        }
    }
});

//获取当前时间
function NowTime() {
    var time = new Date();
    var year = time.getFullYear();//获取年
    var month = time.getMonth() + 1;//或者月
    var day = time.getDate();//或者天
    var hour = time.getHours();//获取小时
    var minu = time.getMinutes();//获取分钟
    var second = time.getSeconds();//或者秒
    var data = year + "-";
    if (month < 10) {
        data += "0";
    }
    data += month + "-";
    if (day < 10) {
        data += "0"
    }
    data += day + " ";
    if (hour < 10) {
        data += "0"
    }
    data += hour + ":";
    if (minu < 10) {
        data += "0"
    }
    data += minu + ":";
    if (second < 10) {
        data += "0"
    }
    data += second;
    return data;
}

function getRandomText(){ 
    eval( "var word=" +  '"\\u' + (Math.round(Math.random() * 20901) + 19968).toString(16)+'"')//生成随机汉字
    return word+"\n \n ";
  }
  
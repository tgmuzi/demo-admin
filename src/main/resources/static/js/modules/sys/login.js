
$(function () {
  $('#gameTime').val(getNowFormatDate() +' 00:00:00 - '+ getNowFormatDate() + ' 23:59:59');
  laydate.render({
    elem: '#gameTime',
    type: 'datetime',
    min:-90,
    max: getNowFormatDate() + ' 23:59:59',
    range: true
});
});
function getNowFormatDate() {
  var date = new Date();
  var seperator1 = "-";
  var seperator2 = ":";
  var month = date.getMonth() + 1;
  var strDate = date.getDate();
  if (month >= 1 && month <= 9) {
      month = "0" + month;
  }
  if (strDate >= 0 && strDate <= 9) {
      strDate = "0" + strDate;
  }
  var currentdate = date.getFullYear() + seperator1 + month + seperator1 + strDate;
  return currentdate;
}

$(function () {
  var data = [[1, 'Exxon Mobil', '339938.0', '36130.0'],
      [2, 'Wal-Mart Stores', '315654.0', '11231.0'],
      [3, 'Royal Dutch Shell', '306731.0', '25311.0'],
      [4, 'BP', '267600.0', '22341.0'],
      [5, 'General Motors', '192604.0', '-10567.0'],
      [6, 'Chevron', '189481.0', '14099.0'],
      [7, 'DaimlerChrysler', '186106.3', '3536.3'],
      [8, 'Toyota Motor', '185805.0', '12119.6'],
      [9, 'Ford Motor', '177210.0', '2024.0'],
      [10, 'ConocoPhillips', '166683.0', '13529.0'],
      [11, 'General Electric', '157153.0', '16353.0'],
      [12, 'Total', '152360.7', '15250.0'],
      [13, 'ING Group', '138235.3', '8958.9'],
      [14, 'Citigroup', '131045.0', '24589.0'],
      [15, 'AXA', '129839.2', '5186.5'],
      [16, 'Allianz', '121406.0', '5442.4'],
      [17, 'Volkswagen', '118376.6', '1391.7'],
      [18, 'Fortis', '112351.4', '4896.3'],
      [19, 'Crédit Agricole', '110764.6', '7434.3'],
      [20, 'American Intl. Group', '108905.0', '10477.0']];

  var obj = { 
      numberCell:{resizable:true,title:"#",width:30,minWidth:30},
      title: "带有数组数据的网格",
      resizable:true,
      wrap:false,
      pageModel: { type: "local", rPP: 10 ,rPPOptions: [10, 50, 100 , 500 , 1000 , 10000, 100000], float:"right"},
      scrollModel:{autoFit:true, theme:true}
  };
  obj.colModel = [
      { title: "Rank", width: 100, dataType: "integer" ,editable: false},
      { title: "Company", width: 200, editable: false,dataType: "string" },
      { title: "Revenues ($ millions)", width: 150, dataType: "float", editable: false,align: "right" },
      { title: "Profits ($ millions)", width: 150, dataType: "float", editable: false,align: "right"},
      { title: "操作", editable: false, minWidth: 165, sortable: false,
        render: function (ui) {
            return "<button type='button' class='edit_btn'>编辑</button>\
                <button type='button' class='delete_btn'>删除</button>";
        }
      }
  ];
  obj.dataModel = { 
      location: "local", // remote
      dataType: "json",
      method: "GET",
      url: baseURL + "admin",
      data: data 
    };
  $("#jqGrid").pqGrid(obj);
});
var xhr;
var app = new Vue({
    el: '#app',
    data: {
      message: 'Hello Vue!',
      src: 'captcha.jpg',
      valueName: '',
      getQrcode: 'getQrcode',
    },
    methods:{
      query: function () {
        $.get(baseURL + "add", function(r){
          console.log(r);
      });
      $.ajax({
        url:baseURL + "admin",
        success:function(result){
          var protocol = location.protocol;
          var hostname = location.hostname;
          var port = location.port; 
          var basePath = protocol + "//" + hostname + ":" + port;
          }
        });
    },
    refreshCode:function(){
      this.src = "captcha.jpg?t=" + $.now();
      this.getQrcode = "getQrcode?t=" + $.now();
      app.query();
    },
    UpladFile: function(file){
      var fileObj = document.getElementById("file").files[0]; // js 获取文件对象
            var url =  baseURL + "uploadFile/upload"; // 接收上传文件的后台地址
 
            var form = new FormData(); // FormData 对象
            form.append("file", fileObj); // 文件对象
 
            xhr = new XMLHttpRequest();  // XMLHttpRequest 对象
            xhr.open("post", url, true); //post方式，url为服务器请求地址，true 该参数规定请求是否异步处理。
            xhr.onload = app.uploadComplete; //请求完成
            xhr.onerror =  app.uploadFailed; //请求失败
 
            xhr.upload.onprogress = app.progressFunction;//【上传进度调用方法实现】
            xhr.upload.onloadstart = function(){//上传开始执行方法
                ot = new Date().getTime();   //设置上传开始时间
                oloaded = 0;//设置上传开始时，以上传的文件大小为0
            };
 
            xhr.send(form); //开始上传，发送form数据
    },
    uploadComplete: function(evt){
      //服务断接收完文件返回的结果
      var data = JSON.parse(evt.target.responseText);
      console.log(data.filePath);
      app.valueName = data.filePath;
      if(data.success) {
        console.log("上传成功！");
      }else{
        console.log("上传失败！");
      }

    },
    //上传失败
    uploadFailed:function () {
      console.log("上传失败！");
    },
    cancleUploadFile:function(){
      xhr.abort();
    },
    progressFunction:function(evt) {
      var progressBar = document.getElementById("progressBar");
      var percentageDiv = document.getElementById("percentage");
      // event.total是需要传输的总字节，event.loaded是已经传输的字节。如果event.lengthComputable不为真，则event.total等于0
      if (evt.lengthComputable) {//
          progressBar.max = evt.total;
          progressBar.value = evt.loaded;
          percentageDiv.innerHTML = Math.round(evt.loaded / evt.total * 100) + "%";
      }
      var time = document.getElementById("time");
      var nt = new Date().getTime();//获取当前时间
      var pertime = (nt-ot)/1000; //计算出上次调用该方法时到现在的时间差，单位为s
      ot = new Date().getTime(); //重新赋值时间，用于下次计算
      var perload = evt.loaded - oloaded; //计算该分段上传的文件大小，单位b
      oloaded = evt.loaded;//重新赋值已上传文件大小，用以下次计算
      //上传速度计算
      var speed = perload/pertime;//单位b/s
      var bspeed = speed;
      var units = 'b/s';//单位名称
      if(speed/1024>1){
          speed = speed/1024;
          units = 'k/s';
      }
      if(speed/1024>1){
          speed = speed/1024;
          units = 'M/s';
      }
      speed = speed.toFixed(1);
      //剩余时间
      var resttime = ((evt.total-evt.loaded)/bspeed).toFixed(1);
      time.innerHTML = '，速度：'+speed+units+'，剩余时间：'+resttime+'s';
      if(bspeed==0) time.innerHTML = '上传已取消';
  }
    }
  })
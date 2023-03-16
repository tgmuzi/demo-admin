//定义变量
var domObj = {
    $inFqcPrdRowSelData: $("#inFqcPrdRowSelData"),

    buttons: {
        $moveIn_btn: $('#moveIn_btn'),
        $register_btn: $('#register_btn'),
        $moveOut_btn: $('#moveOut_btn'),
    },
    $inFqcPrdListDiv: $('#inFqcPrdListDiv'),

    tables: {
        $inFqcPrdListGrd: $('#inFqcPrdListGrd'),
    },
};

var onCreateFun = {
//初始化表格方法
    initInFqcProInfoGrdFun: function () {
      var obj = {
            numberCell:{resizable:true,title:"#",width:30,minWidth:30},
            resizable:true,
            wrap:false,
            pageModel: { type: "local", rPP: 10 ,rPPOptions: [10, 50, 100 , 500 , 1000 , 10000, 100000], float:"right"},
            scrollModel:{autoFit:true, theme:true},
            selectionModel: {type: 'row', mode: 'single'},
            numberCell: {show: true},
            scrollModel: {pace: 'fast', autoFit: true, theme: true},
            wrap: false,
            stripeRows: true,
            hoverMode: 'row'
      };
      obj.colModel = [
            { title: "Order ID", width: 100, dataIndx: "jobId" },
            { title: "容器名",dataIndx:"beanName", width: 100, dataType: "integer" ,editable: false},
            { title: "方法名",dataIndx:"methodName", width: 200, editable: false,dataType: "string" },
            { title: "参数",dataIndx:"params", width: 150, dataType: "float", editable: false,align: "right" },
            { title: "定时",dataIndx:"cronExpression", width: 150, dataType: "float", editable: false,align: "right"},
            { title: "状态",dataIndx:"status", width: 150, dataType: "float", editable: false,align: "right"},
            { title: "备注",dataIndx:"remark", width: 150, dataType: "float", editable: false,align: "right"},
            { title: "操作", editable: false, minWidth: 165, sortable: false,
            render: function (ui) {
                return "<button type='button' class='edit_btn'>编辑</button><button type='button' class='delete_btn'>删除</button>";
            }
            }
      ];
      obj.dataModel = {
          location: "remote", // remote
          dataType: "json",
          method: "POST",
          url: baseURL + "/quartz/scheduleJob/list",
          getData: function (dataJSON) {		//返回后的分页处理，需要前后台协调
                          var data = dataJSON.page.records;
                          return { curPage: dataJSON.current, totalRecords: dataJSON.total, data: data };
                      }
        };
      $("#jqGrid").pqGrid(obj);
    },


// 监听屏幕大小变化 作内容自适应
    refreshTab3LayoutFun: function () {
        domObj.$inFqcPrdListDiv.height($(window).height() - domObj.$inFqcPrdListDiv.offset().top() - $('.page-header.navbar').height());
        // domObj.tables.$inFqcPrdListGrd.ddChangePQGridTableLocation({
        //     referenceH: $(window).height(),
        //     differenceH: domObj.$inFqcPrdListDiv.offset().top + $('.page-header.navbar').height()
        // });
    },
}

  // 页面初始化方法
  function onStartFun() {
      onCreateFun.initInFqcProInfoGrdFun();
      onCreateFun.refreshTab3LayoutFun();
      //iniButtonAction();
  };
  onStartFun();
var xhr;
var vm = new Vue({
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
        url:baseURL + "/quartz/scheduleJob",
        success:function(result){
          var protocol = location.protocol;
          var hostname = location.hostname;
          var port = location.port; 
          var basePath = protocol + "//" + hostname + ":" + port;
          }
        });
    }
    }
  })
//生成 菜单
var menuItem = Vue.extend({
	name: 'menu-item',
	props:{item:{},index:0},
	template:[
	          '<li class="site-menu-item">',
	              '<a v-if="item.type == 0" href="javascript:;" class="site-menu-item">',
	          		  '<i v-if="item.icon != null" :class="item.icon"></i>',
	                  '<span class="nav-label">{{item.name}}</span>',
	                  '<span class="fa arrow"></span>',
	              '</a>',
	              '<ul v-if="item.type == 0" class="nav nav-second-level">',
	                  '<menu-item1 :item="item" v-for="item in item.list"></menu-item1>',
	              '</ul>',
	              '<a class="J_menuItem" href="javascript:;" v-if="item.type == 1" :data-url="item.url" :data-title="item.name" :data-id="item.code">'+
	                  '<i v-if="item.icon != null" :class="item.icon"></i>'+
	                  '<span class="nav-label" style="display: block;">{{item.name}}</span>'+
	              '</a>',
	          '</li>'
	].join('')
});
//生成菜单
var menuItem1 = Vue.extend({
	name: 'menu-item1',
	props:{item:{},index:0},
	template:[
	          '<li>',
	              '<a v-if="item.type == 0" href="javascript:;">',
	          		  '<i v-if="item.icon != null" :class="item.icon"></i>',
	                  '<span class="nav-label">{{item.name}}</span>',
	                  '<span class="fa arrow"></span>',
	              '</a>',
	              '<ul v-if="item.type == 0" class="nav nav-second-level">',
	                  '<menu-item1 :item="item" v-for="item in item.list"></menu-item1>',
	              '</ul>',
	              '<a class="J_menuItem" href="javascript:;" v-if="item.type == 1" :data-url="item.url" :data-title="item.name" :data-id="item.code">'+
	                  '<i v-if="item.icon != null" :class="item.icon"></i>'+
	                  '<span class="nav-label" style="display: block;">{{item.name}}</span>'+
	              '</a>',
	          '</li>'
	].join('')
});
var url = 'user';
//iframe自适应
$(window).on('resize', function() {
	var $content = $('.content');
	$content.height($(this).height());
	$content.find('iframe').each(function() {
		$(this).height($content.height());
	});
}).resize();

function resizeHeight(){
	return window.parent.innerHeight -150;
}
//注册菜单组件
Vue.component('menuItem',menuItem);
//注册菜单组件
Vue.component('menuItem1',menuItem1);
var vm = new Vue({
    el: '#app',
    data: {
        user:{
            userName: '',
            password: '',
            captcha: ''
        },
        src: 'captcha.jpg',
        valueName: '',
        getQrcode: 'getQrcode',
        menuList:{},
        navTitle:"",
        user: {},
    },
    methods:{
        refreshCode:function(){
          this.src = "captcha.jpg?t=" + $.now();
          this.getQrcode = "getQrcode?t=" + $.now();
        },
        getUser: function(){
          $.getJSON(baseURL + "sysUser/info", function(r){
            vm.user = r.user;
          });
        },
        query: function () {
          $.ajax({
            url:baseURL + "login",
            data:JSON.stringify(vm.user),
            contentType: "application/json",
            type: "POST",
            success:function(r){
              if(r.code == 0){//登录成功
                  localStorage.setItem("token", r.data.token);
                  localStorage.setItem("userId", r.data.userId);
                  parent.location.href = baseURL;
                }else{
                  vm.showStatus(r.msg);
                  vm.refreshCode();
                }
              }
            });
        },
        showStatus: function(msg){
          layer.msg(msg);
        },
        getMenuList: function () {
          $.ajax({
            type: "POST",
              url: baseURL + "sys/sysMenu/user",
              data: null,
              dataType: "json",
              success: function(r){
              if(r.code == 0){
                setTimeout(function(){
                   $('#side-menu').metisMenu();
                },200)
                vm.menuList = r.data.menuList;
              }else{
                layer.msg(r.msg);
              }
            }
          });
        }
    },
    created: function(){
      this.getMenuList();
      this.getUser();
    },
    updated: function(){
      navtabsInit();
    }
});




function routerList(router, menuList){
	for(var key in menuList){
		var menu = menuList[key];
		if(menu.type == 0){
		}else if(menu.type == 1){
			router.add('#'+menu.url, function() {
				var url = window.location.hash;
				
				//替换iframe的url
			    //vm.main = url.replace('#', '');
				//$(".J_menuItem").on('click', menuItem);
			    
			    //导航菜单展开
			    $(".treeview-menu li").removeClass("active");
			    $("a[href='"+url+"']").parents("li").addClass("active");
			    
			    vm.navTitle = $("a[href='"+url+"']").text();
			});
		}
	}
}
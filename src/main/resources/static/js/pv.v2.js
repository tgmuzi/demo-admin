$(function() {
    load_time = (new Date().getTime() - start_time) / 1000;
    bindLoadPoint();//pv-load
    bindClickPoint();//pv-click
    bindHoverPoint();//pv-hover
    bindHoverPointZero();//pv-hover
    bindAsyncLoadPoint()//pv-async-load
    bindEnterPoint();//pv-enter
    bindScrollPoint();//pv-scroll
});

function bindLoadPoint() {
    // $('[pv-load]').each(function() {
    //     pv_page_id = $(this).attr('pv-load');
    //     sendRecordPv($(this), pv_page_id)
    // });
    $('[_l]').each(function() {
        pv_page_id = $(this).attr('_l');
        sendRecordPvByGif($(this), pv_page_id)
    });
}

function bindClickPoint() {
    // $('body').on('click', '[pv-click]', function() {
    //     pv_page_id = $(this).attr('pv-click');
    //     sendRecordPv($(this), pv_page_id);
    // });
    $('body').on('click', '[_c]', function() {
        pv_page_id = $(this).attr('_c');
        sendRecordPvByGif($(this), pv_page_id);
    });
}

function bindEnterPoint() {
    // $('body').on('keypress', '[pv-enter]', function(event) {
    //     if (event.keyCode == 13) {
    //         pv_page_id = $(this).attr('pv-enter');
    //         sendRecordPv($(this), pv_page_id);
    //     }
    // });
    $('body').on('keypress', '[_e]', function(event) {
        if(event.keyCode == 13) {
            pv_page_id = $(this).attr('_e');
            sendRecordPvByGif($(this), pv_page_id);
        }
    });
}

function bindScrollPoint() {
    $(window).scroll(function () {
        // $.each($("[pv-scroll]"),function (i,v) {
        //     pv_page_id = $(v).attr('pv-scroll');
        //     if($(window).scrollTop()>=($(v).offset().top-$(window).height()) && $(window).scrollTop()<=($(v).offset().top-$(window).height()+$(v).height())) {
        //         sendRecordPv($(v), pv_page_id);
        //         $(v).removeAttr('pv-scroll');
        //     }
        // })

        $.each($("[_s]"),function (i,v) {
            pv_page_id = $(v).attr('_s');
            if($(window).scrollTop()>=($(v).offset().top-$(window).height()) && $(window).scrollTop()<=($(v).offset().top-$(window).height()+$(v).height())) {
                sendRecordPvByGif($(v), pv_page_id);
                $(v).removeAttr('_s');
            }
        })
    })
}

function bindAsyncLoadPoint() {
    interval = window.setInterval(function() {
        // $('[pv-async-load]').each(function() {
        //     if ($(this).is(':hidden') != true) {
        //         pv_page_id = $(this).attr('pv-async-load');
        //         $(this).removeAttr('pv-async-load');
        //         sendRecordPv($(this), pv_page_id)
        //     }
        // });
        $('[_a]').each(function() {
            if ($(this).is(':hidden') != true) {
                pv_page_id = $(this).attr('_a');
                $(this).removeAttr('_a');
                sendRecordPvByGif($(this), pv_page_id)
            }
        });
    }, 1000);
}

function bindHoverPoint() {
    // $('body').on('mouseenter', '[pv-hover]', function() {
    //     var _this = $(this);
    //     _this.attr('pv-p-enter-time', new Date().getTime());
    //     _this.attr('pv-timeout_id',setTimeout(function () {
    //         sendRecordPv(_this, _this.attr('pv-hover'));
    //     },1000))
    // }).on('mouseleave', '[pv-hover-out]', function() {
    //     var _this = $(this);
    //     clearTimeout(_this.attr('pv-timeout_id'));
    //     _this.removeAttr('pv-timeout_id');
    //     if (_this.attr('pv-p-enter-time') > 0) {
    //         _this.attr('pv-p-num', new Date().getTime() - _this.attr('pv-p-enter-time'));
    //         sendRecordPv(_this, _this.attr('pv-hover-out'));
    //     }
    //     _this.removeAttr('pv-p-num');
    //     _this.removeAttr('pv-p-enter-time');
    // });

    $('body').on('mouseenter', '[_h]', function() {
        var _this = $(this);
        _this.attr('p-enter-time', new Date().getTime());
        _this.attr('timeout_id',setTimeout(function () {
            sendRecordPvByGif(_this, _this.attr('_h'));
        },1000))
    }).on('mouseleave', '[_o]', function() {
        var _this = $(this);
        clearTimeout(_this.attr('timeout_id'));
        _this.removeAttr('timeout_id');
        if (_this.attr('p-enter-time') > 0) {
            _this.attr('p-total-time', new Date().getTime() - _this.attr('p-enter-time'));
            sendRecordPvByGif(_this, _this.attr('_o'));
        }
        _this.removeAttr('p-total-time');
        _this.removeAttr('p-enter-time');
    });
}

function bindHoverPointZero() {
    // $('body').on('mouseenter', '[pv-hover-zero]', function() {
    //     var _this = $(this);
    //     sendRecordPv(_this, _this.attr('pv-hover-zero'));
    // });

    $('body').on('mouseenter', '[_z]', function() {
        var _this = $(this);
        sendRecordPvByGif(_this, _this.attr('_z'));
    });
}

function sendRecordPv(dom, page_id) {
    return false;
    pv_kw = dom.attr('pv-p-kw');
    pv_tid = dom.attr('pv-p-tid');
    pv_kid_1 = dom.attr('pv-p-kid_1');
    pv_kid_2 = dom.attr('pv-p-kid_2');
    pv_page_num = dom.attr('pv-p-num');

    page_id = page_id.split(',');
    page_id.map((value) => {
        if ((value * 1) > 0) {
            recordPv(value, pv_kw, pv_tid, pv_kid_1, pv_kid_2, pv_page_num);
        }
    });
}

function sendRecordPvByGif(dom, page_id) {
    if(dom !== "") {
        total_time = dom.attr('p-total-time');
        a8 = dom.attr('a8');
        if (!a8 && typeof a8 == "undefined") {
            a8 = "";
        }
        if (total_time && total_time !== '') {
            a8 += '&ht=' + total_time;
        }
    }else {
        a8 = "";
    }

    if (a8 && typeof a8 != "undefined" && a8 !=="undefined") {
        if (a8.indexOf("$text") !== -1) {
            dom_text = $.trim(dom.text());
            a8 = a8.replace(/\$text/g, dom_text)
        }
        if (a8.indexOf("$val") !== -1) {
            dom_val = $.trim(dom.val());
            a8 = a8.replace(/\$val/g, dom_val)
        }
    }

    if(typeof page_id != "string"){
        page_id+='';
    }
    page_id = page_id.split(',');
    page_id.map((value) => {
        if ((value * 1) > 0) {
            recordPvGif(value, a8);
        }
    });
}

function recordPvGif(value, a8) {
    if(typeof for_portal != 'undefined' && for_portal) {
        return ;
    }
    link = "https://p.818ps.com/p.gif?pid=" + value

    userBase_cookie = getCookie("ui_818ps");
    if(userBase_cookie) {
        a8 += '&ui=' + encodeURIComponent(userBase_cookie);
    }else if(userBase && userBase !== '') {
        a8 += '&ui=' + encodeURIComponent(userBase);
    }
    if(typeof windowLocationHref !== "undefined"){
        url_address = windowLocationHref;
    }else{
        url_address = window.location.href;
    }
    a8 += '&u=' + encodeURIComponent(url_address);
    if(typeof HTTP_REFERER!="undefined" && HTTP_REFERER) {
        a8 += '&r=' + encodeURIComponent(HTTP_REFERER);
    }

    if (a8 && typeof a8 != "undefined") {
        if (a8.substr(0, 1) != "&") {
            link += "&" + a8
        } else {
            link += a8
        }
    }
    if(typeof pt_platm == 'undefined') {
        link += "&pt=web"
    }else {
        link += "&pt=" + pt_platm;
    }
    if(typeof keyword != 'undefined' && keyword!=='') {
        if(typeof keyword == "object") {
            link += "&kw=" + keyword.value;
        }else {
            link += "&kw=" + keyword;
        }
    }
    if(typeof page_info_id != 'undefined') {
        link += "&sa=" + page_info_id;
    }
    if(typeof page_num != 'undefined') {
        link += "&n=" + page_num;
    }
    if(typeof server_net_time != 'undefined') {
        link += "&st=" + server_net_time;
    }
    if(typeof transfer_time != 'undefined') {
        link += "&rt=" + transfer_time;
    }
    if(typeof dom_load_time != 'undefined') {
        link += "&dt=" + dom_load_time;
    }
    if(typeof img_load_time != 'undefined') {
        link += "&it=" + img_load_time;
    }
    if(typeof ab_test != 'undefined') {
        link += "&ab=" + ab_test;
    }
    if(typeof win_version != 'undefined' &&  win_version!=='') {
        link += "&ver=" + win_version;
    }
    if(typeof routeId != 'undefined' &&  routeId!=='') {
        link += "&ri=" + routeId;
    }
    if(typeof route != 'undefined' &&  route!=='') {
        link += "&rb=" + route;
    }
    if(typeof afterRoute != 'undefined' &&  afterRoute!=='') {
        link += "&ra=" + afterRoute;
    }
    link += "&v="+ new Date().getTime()
    link = link.replace("#",'');

    var cc = new Image();

    var res = checkFailed(cc); // 埋点发送失败监听
    if(res == 1){
        return false;
    }
    cc.src = link;
}

function recordPv(page_id, kw, tid, kid_1, kid_2, page_num,async) {
    return false;
    if (!page_id || page_id <= 0 || for_portal) return false;
    csrfToken = $('#_csrf').val();
    page_num = page_num ? page_num : all_page_num;
    url_address = window.location.href;

    post_url = '/site/pv';
    if (typeof debug_mode != "undefined" && debug_mode == 1) {
        post_url += "?page_id=" + page_id;
    }
    if(async=='async') {
        $.ajaxSettings.async = false;
    }
    $.post(post_url, {
        'page_id': page_id, _csrf: csrfToken, 'user_source': user_source, 'load_time': load_time,
        'exec_time': exec_time, 'user_request_time': user_request_time, 'deal_time': deal_time,
        'first_screen_load_time': first_screen_load_time, 'ready_time': ready_time,
        'url': "//" + url_address,
        'referer': HTTP_REFERER, "kw": kw, "templ_id": tid, "kid_1": kid_1, "kid_2": kid_2,
        'page_num': page_num,
        'send_area': page_info_id
    });
    if(async=='async') {
        $.ajaxSettings.async = true;
    }
}

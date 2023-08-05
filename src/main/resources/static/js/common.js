var Common = {
    checkLogin: function(href, no_jump) {
        if (isGuest) {
            changeLoginRefer(href);
            popLogin();
            return false;
        }
        if (!no_jump) {
            var newTab = window.open(href);
        }
    },
    recordTplEdit: function(tid) {
        if (isGuest) return false;
        $.get('/site/cache_search_edit', {'tid': tid}, function(res) {
            $("#edit_" + tid).html('<i class="iconfont">&#xe606;</i>&nbsp;' + res);
        });
        return true;
    },
    tpl_pv_edit: function(tid, keyword) {
        if (isGuest) return false;
        recordPv_edit(tid, 8, keyword);
        templateClick(tid);
        return true;
    },
    setLocalStorage: function(key, value) {
        if (!window.localStorage) {
            return false;
        }
        return window.localStorage.setItem(key, value);
    },
    getLocalStorage: function(key) {
        if (!window.localStorage) {
            return false;
        }
        return window.localStorage.getItem(key);
    },
    delLocalStorage: function(key) {
        if (!window.localStorage) {
            return false;
        }
        return window.localStorage.removeItem(key);
    },
    setSearchHistory: function(keyword, pinyin) {
        //如果是场景页则单独存这个场景页的搜索信息
        var scene_id = 0;
        if($('.search-history').hasClass('scene_page')) {
            var scene_id = $('#scene_id').val();
            var old = this.getLocalStorage('search-history-'+scene_id);
        }else{
            var old = this.getLocalStorage('search-history');
        }

        if (old) {
            old = this.json_decode(old);
        } else {
            old = {};
        }
        old[keyword] = pinyin;
        keywords = Object.keys(old);
        keywords = keywords.slice(-10);
        for (var key in old) {
            if (keywords.indexOf(key) <= -1) {
                delete old[key]
            }
        }
        if($('.search-history').hasClass('scene_page')) {
            return this.setLocalStorage('search-history-'+scene_id, this.json_encode(old));
        }else{
        return this.setLocalStorage('search-history', this.json_encode(old));
        }
    },
    getSearchHistory: function() {
        //如果是场景页则单独存这个场景页的搜索信息
        var scene_id = 0;
        if($('.search-history').hasClass('scene_page')) {
            var scene_id = $('#scene_id').val();
            var old = this.getLocalStorage('search-history-'+scene_id);
        }else{
        var old = this.getLocalStorage('search-history');
        }
        newObj = this.json_decode(old);
        return this.sortObj(newObj);
    },
    json_encode: function(obj) {
        str = JSON.stringify(obj);
        return str;
    },
    json_decode: function(str) {
        obj = JSON.parse(str);
        return obj;
    },
    sortObj: function(obj) {
        var arr = [];
        for (var i in obj) {
            arr.push([obj[i], i]);
        }
        ;
        arr.reverse();
        var len = arr.length;
        var obj = {};
        for (var i = 0; i < len; i++) {
            obj[arr[i][1]] = arr[i][0];
        }
        return obj;
    }
};
var Base64 = {
    _keyStr: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=", encode: function(e) {
        var t = "";
        var n, r, i, s, o, u, a;
        var f = 0;
        e = Base64._utf8_encode(e);
        while (f < e.length) {
            n = e.charCodeAt(f++);
            r = e.charCodeAt(f++);
            i = e.charCodeAt(f++);
            s = n >> 2;
            o = (n & 3) << 4 | r >> 4;
            u = (r & 15) << 2 | i >> 6;
            a = i & 63;
            if (isNaN(r)) {
                u = a = 64
            } else if (isNaN(i)) {
                a = 64
            }
            t = t + this._keyStr.charAt(s) + this._keyStr.charAt(o) + this._keyStr.charAt(u) + this._keyStr.charAt(a)
        }
        return t
    }, decode: function(e) {
        var t = "";
        var n, r, i;
        var s, o, u, a;
        var f = 0;
        e = e.replace(/[^A-Za-z0-9+/=]/g, "");
        while (f < e.length) {
            s = this._keyStr.indexOf(e.charAt(f++));
            o = this._keyStr.indexOf(e.charAt(f++));
            u = this._keyStr.indexOf(e.charAt(f++));
            a = this._keyStr.indexOf(e.charAt(f++));
            n = s << 2 | o >> 4;
            r = (o & 15) << 4 | u >> 2;
            i = (u & 3) << 6 | a;
            t = t + String.fromCharCode(n);
            if (u != 64) {
                t = t + String.fromCharCode(r)
            }
            if (a != 64) {
                t = t + String.fromCharCode(i)
            }
        }
        t = Base64._utf8_decode(t);
        return t
    }, _utf8_encode: function(e) {
        e = e.replace(/rn/g, "n");
        var t = "";
        for (var n = 0; n < e.length; n++) {
            var r = e.charCodeAt(n);
            if (r < 128) {
                t += String.fromCharCode(r)
            } else if (r > 127 && r < 2048) {
                t += String.fromCharCode(r >> 6 | 192);
                t += String.fromCharCode(r & 63 | 128)
            } else {
                t += String.fromCharCode(r >> 12 | 224);
                t += String.fromCharCode(r >> 6 & 63 | 128);
                t += String.fromCharCode(r & 63 | 128)
            }
        }
        return t
    }, _utf8_decode: function(e) {
        var t = "";
        var n = 0;
        var r = c1 = c2 = 0;
        while (n < e.length) {
            r = e.charCodeAt(n);
            if (r < 128) {
                t += String.fromCharCode(r);
                n++
            } else if (r > 191 && r < 224) {
                c2 = e.charCodeAt(n + 1);
                t += String.fromCharCode((r & 31) << 6 | c2 & 63);
                n += 2
            } else {
                c2 = e.charCodeAt(n + 1);
                c3 = e.charCodeAt(n + 2);
                t += String.fromCharCode((r & 15) << 12 | (c2 & 63) << 6 | c3 & 63);
                n += 3
            }
        }
        return t
    }
};

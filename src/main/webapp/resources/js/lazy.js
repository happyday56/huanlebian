/*! lazy http://zzsvn.pcauto.com.cn/svn/doc/javascript/%cd%bc%c6%ac%b0%b4%d0%e8%bc%d3%d4%d8/lazy.js */
var Lazy = {
    eCatch: {}, eHandle: 0, isFunction: function (a) {
        return Object.prototype.toString.call(a) === "[object Function]";
    }, addEvent: function (c, b, a) {
        if (c.addEventListener) {
            c.addEventListener(b, a, false);
        } else {
            c.attachEvent("on" + b, a);
        }
        this.eCatch[++this.eHandle] = {handler: a};
        return this.eHandle;
    }, removeEvent: function (c, b, a) {
        if (c.addEventListener) {
            c.removeEventListener(b, this.eCatch[a].handler, false);
        } else {
            c.detachEvent("on" + b, this.eCatch[a].handler);
        }
    }, $$: function (a) {
        return (typeof(a) == "object") ? a : document.getElementById(a);
    }, create: function (d) {
        d.loading = false;
        d.timmer = undefined;
        d.time_act = 0;
        d.delay = d.delay || 100;
        d.delay_tot = d.delay_tot || 1000;
        var c;
        d.imgList = [];
        if (document.querySelectorAll) {
            c = document.querySelectorAll("#" + d.lazyId + " img");
        } else {
            c = document.getElementById(d.lazyId).getElementsByTagName("img");
        }
        for (var b = 0, a = c.length; b < a; b++) {
            if (c[b].getAttribute(d.trueSrc)) {
                d.imgList.push(c[b]);
            }
        }
        d.imgCount = d.imgList.length;
        if (d.jsList) {
            d.jsCount = d.jsList.length;
            for (var b = 0; b < d.jsCount; b++) {
                d.jsList[b].oDom = (typeof(d.jsList[b].id) == "object") ? d.jsList[b].id : document.getElementById(d.jsList[b].id);
            }
        } else {
            d.jsList = [];
            d.jsCount = 0;
        }
        this.init(d);
        return d;
    }, checkPhone: function (a) {
        if (a.indexOf("android") > -1 || a.indexOf("iphone") > -1 || a.indexOf("ipod") > -1 || a.indexOf("ipad") > -1) {
            this.isPhone = true;
        } else {
            this.isPhone = false;
        }
    }, checkLazyLoad: function (a) {
        if (a.indexOf("opera mini") > -1) {
            return false;
        } else {
            return true;
        }
    }, init: function (b) {
        if (b.unNeedListen) {
            this.loadOnce(b);
            return;
        }
        if (b.imgCount == 0 && b.jsCount == 0) {
            return;
        }
        var a = navigator.userAgent.toLowerCase();
        if (this.checkLazyLoad(a)) {
            this.checkPhone(a);
            b.e1 = this.addEvent(window, "scroll", this.load(b));
            b.e2 = this.addEvent(window, "touchmove", this.load(b));
            b.e3 = this.addEvent(window, "touchend", this.load(b));
            this.loadTime(b);
        } else {
            this.loadOnce(b);
        }
    }, getYGetBound: function (b, a) {
        var c = b.getBoundingClientRect().top || 0;
        return c == 0 ? null : (c + a);
    }, getYOffSet: function (a) {
        var b = 0;
        while (a.offsetParent) {
            b += a.offsetTop;
            a = a.offsetParent;
        }
        return b == 0 ? null : b;
    }, getHideY: function (c, b, a) {
        while (c && c !== document) {
            var d = b(c, a);
            if (d != null) {
                return d;
            }
            c = c.parentNode;
        }
        return 0;
    }, getY: function (b, c) {
        var a;
        if (b.getBoundingClientRect) {
            a = this.getHideY(b, this.getYGetBound, c);
        } else {
            a = this.getHideY(b, this.getYOffSet);
        }
        return a;
    }, load: function (a) {
        return function () {
            if (a.loading == true) {
                return;
            }
            a.loading = true;
            if (a.time_act && ((1 * new Date() - a.time_act) > a.delay_tot)) {
                a.timmer && clearTimeout(a.timmer);
                Lazy.loadTime(a);
            } else {
                a.timmer && clearTimeout(a.timmer);
                a.timmer = setTimeout(function () {
                    Lazy.loadTime(a);
                }, a.delay);
            }
            a.loading = false;
        };
    }, setSrc: function (b, a) {
        b.setAttribute("src", b.getAttribute(a));
        b.removeAttribute(a);
    }, setJs: function (js) {
        Lazy.isFunction(js) ? js.call(this) : eval(js);
    }, loadTime: function (b) {
        b.time_act = 1 * new Date();
        var e, h, c;
        if (this.isPhone) {
            e = window.screen.height;
            h = window.scrollY;
            c = h + e;
        } else {
            e = document.documentElement.clientHeight || document.body.clientHeight;
            h = Math.max(document.documentElement.scrollTop, document.body.scrollTop);
            c = e + h;
        }
        b.offset = b.offset || e;
        b.preOffset = b.preOffset || e;
        if (b.imgCount) {
            var k = [];
            for (var f = 0; f < b.imgCount; f++) {
                var g = b.imgList[f], l;
                var l = this.getY(g, h);
                if (l > (h - b.preOffset) && l < (c + b.offset)) {
                    if (l > h && l < c) {
                        this.setSrc(g, b.trueSrc);
                    } else {
                        k.push(g);
                    }
                    b.imgList.splice(f, 1);
                    f--;
                    b.imgCount--;
                }
            }
            var a = k.length;
            if (a) {
                for (var f = 0; f < a; f++) {
                    var g = k[f];
                    this.setSrc(g, b.trueSrc);
                }
            }
        }
        if (b.jsCount) {
            for (var f = 0; f < b.jsCount; f++) {
                var d = b.jsList[f];
                var j = this.getY(d.oDom, h);
                if (j < (c + b.offset)) {
                    this.setJs.call(d.oDom, d.js);
                    b.jsList.splice(f, 1);
                    f--;
                    b.jsCount--;
                }
            }
        }
        if (b.imgCount == 0 && b.jsCount == 0) {
            this.removeEvent(window, "scroll", b.e1);
            this.removeEvent(window, "touchmove", b.e2);
            this.removeEvent(window, "touchend", b.e3);
        }
    }, loadOnce: function (d) {
        for (var b = 0; b < d.imgCount; b++) {
            var a = d.imgList[b];
            this.setSrc(a, d.trueSrc);
        }
        if (d.jsList) {
            for (var b = 0; b < d.jsCount; b++) {
                var c = d.jsList[b];
                this.setJs.call(c.oDom, c.js);
            }
        }
    }
};

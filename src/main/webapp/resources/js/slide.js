window.slide01 = new pc.tab({
    target: pc.getElems('#slide01 .c'),
    control: pc.getElems('#slide01 .control a'),
    autoPlay: true,
    stay: 4000,
    onchange: function () {
        var _target = this.target[this.curPage],
            imgs = _target.getElementsByTagName("img");
        if (!imgs) return;
        for (var i = 0, len = imgs.length; i < len; i++) {
            var img = imgs[i], src;
            (src = img.getAttribute('#src1')) && (img.src = src, img.removeAttribute("#src1"));
        }
    }
});
/*按需执行js*/
var jsList_ = [
    {
        id: "nav", js: function () {
        /*主导航*/
        var dl = document.getElementById('nav').getElementsByTagName('li'), l = dl.length;
        for (var i = 0; i < l; i++) {
            dl[i].onmouseover = function () {
                reset();
                this.className = "cur";
            };
        }
        ;
        function reset() {
            for (var i = 0; i < l; i++) {
                dl[i].className = "";
            }
            ;
        }
    }
    }

];

/*执行lazy*/
var xx = Lazy.create({
    lazyId: "Jbody",
    jsList: jsList_,
    trueSrc: '#src'
});
Lazy.init(xx);

/*搜索*/
var s_input = pc.getElem("#q");
var form_div = pc.getElem("#s-form");
pc.addEvent(form_div, "click", function (e) {
    form_div.className = 'form form-active';
    s_input.value = '';
    e.stopPropagation();
})
pc.addEvent(pc.getElem("body"),"click",function(){
    form_div.className='form';
})

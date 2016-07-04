//定义加入收藏夹函数
function joinFavorite(siteUrl, siteName) {
    try {
        if (document.all) {
            window.external.addFavorite(siteUrl, siteName);
        } else if (window.sidebar) {
            window.sidebar.addPanel(siteName, siteUrl, '');
        }
    }
    catch (e) {
        alert("加入收藏夹失败，请使用Ctrl+D快捷键进行添加操作!");
    }
}


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


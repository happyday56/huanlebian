//定义加入收藏夹函数
function addFav() {
    var siteUrl = 'http://www.huanlebian.com';
    var siteName = '育儿知识大全_欢乐变亲子网';
    if (document.all) {
        window.external.AddFavorite(siteName, siteUrl);
    } else if (window.sidebar) {
        window.sidebar.addPanel(siteName, siteUrl, '');
    }
    else {
        alert("加入收藏夹失败，请使用Ctrl+D快捷键进行添加操作!");
    }
    return false;
}


var s_input = pc.getElem("#q");
var form_div = pc.getElem("#s-form");
pc.addEvent(form_div, "click", function (e) {
    form_div.className = 'form form-active';
    s_input.value = '';
    e.stopPropagation();
})
pc.addEvent(pc.getElem("body"), "click", function () {
    form_div.className = 'form';
})


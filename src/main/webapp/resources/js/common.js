//定义加入收藏夹函数
// function addFav() {
//     var siteUrl = 'http://www.huanlebian.com';
//     var siteName = '育儿知识大全_欢乐变';
//     if (document.all) {
//         window.external.AddFavorite(siteName, siteUrl);
//     } else if (window.sidebar) {
//         window.sidebar.addPanel(siteName, siteUrl, '');
//     }
//     else {
//         alert("加入收藏夹失败，请使用Ctrl+D快捷键进行添加操作!");
//     }
//     return false;
// }


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



/*导航条鼠标经过*/
navHover();
function navHover () {
    var dropMenu = $('#subNav .drop');
    for(var i=0;i<dropMenu.length;i++){
        dropMenu[i].onmouseover=function(){
            this.className='current drop';
        };
        dropMenu[i].onmouseout=function(){
            this.className='drop';
        };
    }
}
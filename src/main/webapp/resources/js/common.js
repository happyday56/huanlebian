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


package com.lgh.huanlebian.model;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页的分类列表
 * Created by lgh on 2016/7/1.
 */
@Getter
@Setter
public class WebIndexCategoryListModel extends WebCategoryListModel {
    List<WebCategoryListModel> sub;

    public WebIndexCategoryListModel(String title, String url) {
        super(title, url);
    }

    public WebIndexCategoryListModel(String title, String url, List<WebCategoryListModel> sub) {
        super(title, url);
        this.sub = sub;
    }
}

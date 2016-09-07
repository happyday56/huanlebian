package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */
@Getter
@Setter
public class WebBaikeCategoryListModel extends WebCategoryListModel {

    private List<WebBaikeCategoryListModel> sub;

    public WebBaikeCategoryListModel() {
    }

    public WebBaikeCategoryListModel(String title, String url) {
        super(title, url);
    }

    public WebBaikeCategoryListModel(String title, String url, List<WebBaikeCategoryListModel> sub) {
        super(title, url);
        this.sub = sub;
    }
}

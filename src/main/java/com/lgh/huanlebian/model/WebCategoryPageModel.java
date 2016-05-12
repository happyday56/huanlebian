package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by lgh on 2016/5/10.
 */

@Getter
@Setter
public class WebCategoryPageModel extends WebBasePageModel {
    private String categoryName;
    private List<WebCategoryListModel> topNav;
    private List<WebNewsListModel> newsList;
}

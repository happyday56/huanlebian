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
    private String categoryUrl;
    private List<WebCategoryListModel> topNav;
    private List<WebSlideListModel> slideList;
    /**
     * 子分类信息列表
     */
    private List<WebSubNewsListModel> subNewsList;


}

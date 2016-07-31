package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by lgh on 2016/7/31.
 */

@Getter
@Setter
public class WebThreeCategoryPageModel extends WebBasePageModel {

    private List<WebCategoryListModel> topNav;
    /**
     * 头部栏目名称
     */
    private String headName;
    /**
     * 头部栏目地址
     */
    private String headUrl;

    private List<WebNewsListModel> webNewsList;

}
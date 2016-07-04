package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by lgh on 2016/5/26.
 */

@Getter
@Setter
public class WebSecondCategoryPageModel extends WebBasePageModel {
    private String categoryName;
    private String categoryUrl;
    private String parentCategoryName;
    private String parentCategoryUrl;
    private List<WebCategoryListModel> topNav;
    private List<WebSlideListModel> slideList;

    private List<WebNewsListModel> webNewsList;

}

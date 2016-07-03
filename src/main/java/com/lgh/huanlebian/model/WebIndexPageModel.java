package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 首页
 * Created by lgh on 2016/6/16.
 */

@Getter
@Setter
public class WebIndexPageModel {
    private List<WebIndexCategoryListModel> topNav;
    private List<WebSlideListSummaryModel> slideList;

}

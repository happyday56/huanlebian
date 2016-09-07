package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Administrator on 2016/9/3.
 */

@Getter
@Setter
public class WebBaikePageModel extends WebBasePageModel {

    private List<WebBaikeCategoryListModel> topNav;
    private List<WebSlideListSummaryModel> slideList;

    private List<WebBaikeCategoryListModel> list;
}

package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by lgh on 2016/6/16.
 */
@Getter
@Setter
public class WebNewsPageModel extends WebBasePageModel {

    private WebNewsModel data;

    private List<WebCategoryListModel> topNav;
}

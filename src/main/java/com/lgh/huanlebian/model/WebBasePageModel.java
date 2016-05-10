package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;

/**
 * web页面基础
 * Created by lgh on 2016/5/10.
 */
@Getter
@Setter
public abstract class WebBasePageModel {
    /**
     * 标题
     */
    private String title;
    /**
     * 关键字
     */
    private String keywords;

    /**
     * 描述
     */
    private String description;
}

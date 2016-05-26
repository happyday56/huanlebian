package com.lgh.huanlebian.model;

import lombok.Getter;
import lombok.Setter;


/**
 * Created by lgh on 2016/5/24.
 */

@Getter
@Setter
public class WebSubNewsListModel {
    private String title;
    /**
     * 推荐
     */
    private WebIndexRecommendModel indexRecommend;
}

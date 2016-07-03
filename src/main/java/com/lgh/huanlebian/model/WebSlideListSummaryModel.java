package com.lgh.huanlebian.model;


import lombok.Getter;
import lombok.Setter;

/**
 * Created by Administrator on 2016/7/3.
 */
@Getter
@Setter
public class WebSlideListSummaryModel extends WebSlideListModel {
    private String summary;

    public WebSlideListSummaryModel(String title, String imageUrl, String url, String summary) {
        super(title, imageUrl, url);
        this.summary = summary;
    }
}

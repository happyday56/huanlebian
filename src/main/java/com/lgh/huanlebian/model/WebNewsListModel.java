package com.lgh.huanlebian.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by lgh on 2016/5/11.
 */

@Getter
@Setter
@AllArgsConstructor
public class WebNewsListModel {

    private String title;
    private String url;
    private String imageUrl;
    private String summary;
    private Date time;

}

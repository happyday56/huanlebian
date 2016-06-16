package com.lgh.huanlebian.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

/**
 * Created by lgh on 2016/5/11.
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WebNewsModel {

    private Long id;
    /**
     * 标题
     */
    private String title;

    /**
     * 图片地址
     */
    private String pictureUrl;
    /**
     * 内容
     */
    private String content;

    /**
     * 摘要
     */
    private String summary;
    /**
     * 关键字
     */
    private String keywords;

    /**
     * 描述
     */
    private String description;
    /**
     * 上传时间
     */

    private Date uploadTime;

    /**
     * 浏览次数
     */
    private Long views;

    /**
     * 对应的url
     */
    private String url;
}

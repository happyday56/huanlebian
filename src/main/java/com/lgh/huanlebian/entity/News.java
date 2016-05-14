package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 * 最新资讯
 * Created by Administrator on 2016/5/2.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类
     */
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Category category;

    /**
     * 种类
     */
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Kind kind;

    /**
     * 标题
     */
    @Column(length = 200)
    private String title;

    /**
     * 图片地址
     */
    @Column(length = 200)
    private String pictureUrl;
    /**
     * 内容
     */
    @Lob
    private String content;

    /**
     * 摘要
     */
    @Column(length = 500)
    private String summary;
    /**
     * 关键字
     */
    @Column(length = 200)
    private String keywords;

    /**
     * 描述
     */
    @Column(length = 500)
    private String description;


    /**
     * 上传时间
     */
    @Temporal(value = TemporalType.TIMESTAMP)
    private Date uploadTime;

    /**
     * 浏览次数
     */
    private Long views;
}

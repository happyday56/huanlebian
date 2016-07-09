package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by lgh on 2016/7/6.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Wiki {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类
     */
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private WikiCategory category;

    /**
     * 标题
     */
    @Column(length = 200)
    private String title;

    /**
     * 目录
     */
    @Column(length = 2000)
    private String catalog;

    /**
     * 图片地址
     */
    @Column(length = 200)
    private String pictureUrl;
    /**
     * 内容
     */
    @Lob
    @Basic(fetch = FetchType.LAZY)
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

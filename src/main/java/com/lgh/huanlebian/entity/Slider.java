package com.lgh.huanlebian.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 幻灯片栏目
 * Created by lgh on 2016/5/12.
 */
@Entity
@Getter
@Setter
public class Slider {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类
     */
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Category category;

    /**
     * 标题
     */
    @Column(length = 100)
    private String title;

    /**
     * 图片地址
     */
    @Column(length = 200)
    private String imageUrl;

    /**
     * 跳转地址
     */
    @Column(length = 200)
    private String targetUrl;


    /**
     * 排序号
     */
    private Integer sort;
}

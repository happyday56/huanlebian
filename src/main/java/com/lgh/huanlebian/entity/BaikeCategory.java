package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * 百科分类
 * Created by lgh on 2016/7/5.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BaikeCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父项
     */
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private BaikeCategory parent;

    /**
     * 标题
     */
    @Column(length = 20)
    private String title;
    /**
     * 分类路径，如孕早期（yzq）
     * 根据标题首字母生产
     */
    @Column(length = 50)
    private String path;
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
     * 排序号
     */
    private Integer sort;

    /**
     * 是否主要
     */
    private Boolean isMain;
}

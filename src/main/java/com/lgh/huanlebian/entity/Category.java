package com.lgh.huanlebian.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * Created by Administrator on 2016/5/2.
 */
@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * 标题
     */
    @Column(length = 20)
    private String title;
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
     * 分类路径，如孕早期（yzq）
     * 根据标题首字母生产
     */
    @Column(length = 50)
    private String path;
}

package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;

/**
 * Created by Administrator on 2016/5/2.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "path")})
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 父项
     */
    @ManyToOne(cascade = {CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private Category parent;

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

}

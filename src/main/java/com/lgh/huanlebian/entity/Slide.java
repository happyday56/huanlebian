package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 幻灯片栏目
 * Created by lgh on 2016/5/12.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Slide {

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
    private String url;

}

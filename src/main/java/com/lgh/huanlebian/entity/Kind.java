package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

/**
 * 种类
 * 如护理 疾病
 * Created by lgh on 2016/5/14.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Kind {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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
}

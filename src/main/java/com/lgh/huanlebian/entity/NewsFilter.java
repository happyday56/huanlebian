package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * 新闻内容过滤器
 * Created by Administrator on 2016/6/4.
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class NewsFilter {
    /**
     * 标题
     */
    @Column(length = 200)
    @Id
    private String title;
}

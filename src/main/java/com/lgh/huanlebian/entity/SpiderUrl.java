package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

/**
 *
 * 爬虫的网址
 * Created by Administrator on 2016/7/24.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpiderUrl {
    /**
     * 爬虫的网址
     */
    @Id
    @Column(length = 500)
    private String fromUrl;

    /**
     * 时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;
}

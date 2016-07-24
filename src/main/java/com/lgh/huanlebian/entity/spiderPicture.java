package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


/**
 *
 * 爬虫的图片
 * Created by Administrator on 2016/7/24.
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SpiderPicture{

    /**
     * 爬虫的网址
     */
    @Id
    @Column(length = 255)
    private String fromUrl;

    /**
     * 保存后的图片地址
     */
    private String pictureUrl;

    /**
     * 时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    private Date time;

}

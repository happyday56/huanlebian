package com.lgh.huanlebian.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


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
public class SpiderPicture extends SpiderUrl {
    /**
     * 保存后的图片地址
     */
    private String pictureUrl;
}

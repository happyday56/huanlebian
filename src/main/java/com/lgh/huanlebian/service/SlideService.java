package com.lgh.huanlebian.service;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.Slide;

import java.util.List;

/**
 * Created by lgh on 2016/5/16.
 */
public interface SlideService {

    /**
     * 获取幻灯片前几条数据
     * 获取前3条
     * @param category 分类
     * @return
     */
    List<Slide> findTopSlideList(Category category);
}

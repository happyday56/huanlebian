package com.lgh.huanlebian.service;

import com.lgh.huanlebian.entity.WikiCategory;

/**
 * Created by Administrator on 2016/9/7.
 */
public interface WikiCategoryService {
    /**
     * 获得主分类
     * @param wikiCategory
     * @return
     */
    WikiCategory getMainCategory(WikiCategory wikiCategory);
}

package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.entity.WikiCategory;
import com.lgh.huanlebian.service.WikiCategoryService;
import org.springframework.stereotype.Service;

/**
 * Created by Administrator on 2016/9/7.
 */
@Service
public class WikiCategoryServiceImpl implements WikiCategoryService {

    public WikiCategory getMainCategory(WikiCategory wikiCategory) {
        //找到主分类
        WikiCategory wikiCategoryMain = null;
        if (wikiCategory.getParent().getIsMain()) {
            wikiCategoryMain = wikiCategory.getParent();
        } else if (wikiCategory.getParent().getParent().getIsMain()) {
            wikiCategoryMain = wikiCategory.getParent().getParent();
        }
        return wikiCategoryMain;
    }
}

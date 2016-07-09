package com.lgh.huanlebian.service;

import com.lgh.huanlebian.entity.WikiCategory;

import javax.transaction.Transactional;
import java.io.IOException;

/**
 * Created by lenovo on 2015/7/10.
 */
public interface SpliderService {

    @Transactional
    void start() throws Exception;

    @Transactional
    void handleSlide();


    /**
     * 初始化百科分类
     * @param wikiCategory
     */
    @Transactional
    void initWikiData(WikiCategory wikiCategory) ;
}

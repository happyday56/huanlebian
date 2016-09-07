package com.lgh.huanlebian.service;

/**
 * Created by Administrator on 2016/6/10.
 */
public interface URIService {
    String getCategoryURI(String one);

    String getCategoryURI(String one, String two);

    String getCategoryURI(String one, String two, String kind);

    String getNewURI(Long id);

    /**
     * 百科主分类目录
     *
     * @param path
     * @return
     */
    String getWikiCategoryURI(String path);

    /**
     * 百科间接过渡页面
     *
     * @param id
     * @return
     */
    String getWikiListURI(Long id);

    /**
     * 百科最终页面
     *
     * @param id
     * @return
     */
    String getWikiURI(Long id);

}

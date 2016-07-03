package com.lgh.huanlebian.service;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.Kind;
import com.lgh.huanlebian.entity.News;
import com.lgh.huanlebian.model.WebCategoryListModel;
import com.lgh.huanlebian.model.WebNewsModel;

import java.net.URISyntaxException;
import java.util.List;

/**
 * Created by lgh on 2016/5/27.
 */
public interface NewsService {

    /**
     * 获取分类的前几个 （id排序）
     *
     * @param category 一二级分类
     * @param top 前几
     * @return
     */
    List<News> getTopByCategoryOrderById(Category category, Integer top);

    /**
     * 获取该分类的前几个（view排序）
     *
     * @param category 一二级分类
     * @param top 前几
     * @return
     */
    List<News> getTopByCategoryOrderByViews(Category category, Integer top);

//    List<News> getTopByCategoryOrderByViews();

    /**
     * 获取全路径
     *
     * @param category 不能为空
     * @param kind     可为空
     * @return
     */
    List<WebCategoryListModel> getFullPath(Category category, Kind kind);

    /**
     * 转为WebNewsModel
     *
     * @param news
     * @return
     */
    WebNewsModel toWebNewsModel(News news) throws URISyntaxException;
}

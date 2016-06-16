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

    List<News> getTopByCategory(Category category, Integer top);

    List<News> getTopByCategoryGroup(Category category, Integer top);

    List<News> getTopByCategoryGroup();

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

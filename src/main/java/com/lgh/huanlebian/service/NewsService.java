package com.lgh.huanlebian.service;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.News;

import java.util.List;

/**
 * Created by lgh on 2016/5/27.
 */
public interface NewsService {

    List<News> getTopByCategory(Category category, Integer top);
}

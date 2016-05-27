package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.News;
import com.lgh.huanlebian.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lgh on 2016/5/27.
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    EntityManager entityManager;

    public List<News> getTopByCategory(Category category, Integer top) {
        List<News> newsList = new ArrayList<>();
        StringBuilder hql = new StringBuilder();
        hql.append("select news from News news where news.category=:category order by news.id");
        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("category", category);
        query.setMaxResults(top);
        List list = query.getResultList();
        for (Object o : list) {
            newsList.add((News) o);
        }
        return newsList;
    }

}

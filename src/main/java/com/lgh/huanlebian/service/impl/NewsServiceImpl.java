package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.News;
import com.lgh.huanlebian.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lgh on 2016/5/27.
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    EntityManager entityManager;

    public List<News> getTopByCategory(Category category, Integer top) {
        List<News> result = new ArrayList<>();
        StringBuilder hql = new StringBuilder();
        hql.append("select news from News news where news.category=:category order by news.id");
        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("category", category);
        query.setMaxResults(top);
        List list = query.getResultList();
        for (Object o : list) {
            result.add((News) o);
        }
        return result;
    }

    public List<News> getTopByCategoryGroup(Category category, Integer top) {
        List<News> result = new ArrayList<>();
        StringBuilder hql = new StringBuilder();
        hql.append("select news from News news where news.category=:category order by news.views");
        Query query = entityManager.createQuery(hql.toString());
        query.setParameter("category", category);
        query.setMaxResults(top);
        List list = query.getResultList();
        for (Object o : list) {
            result.add((News) o);
        }
        return result;
    }

    /**
     * 废弃
     *
     * @return
     */
    public List<News> getTopByCategoryGroup() {
        List<News> result = new ArrayList<>();

//        StringBuilder hql = new StringBuilder();
//        hql.append("select news.category,news from News news group by news.category order by news.category,news.views");
//        Query query = entityManager.createQuery(hql.toString());
//        query.setMaxResults(5);
//        List list = query.getResultList();
//        for (Object o : list) {
//            Object[] item = (Object[]) o;
//            result.add((News) item[1]);
//        }
        return result;
    }
}

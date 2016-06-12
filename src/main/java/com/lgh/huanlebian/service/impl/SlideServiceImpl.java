package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.Slide;
import com.lgh.huanlebian.repository.CategoryRepository;
import com.lgh.huanlebian.repository.SlideRepository;
import com.lgh.huanlebian.service.SlideService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * Created by lgh on 2016/5/16.
 */
@Service
public class SlideServiceImpl implements SlideService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    SlideRepository slideRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public List<Slide> findTopSlideList(Category category,Integer top) {
        List<Slide> slides = new ArrayList<>();
        StringBuilder hql = new StringBuilder();
        hql.append("select slide from Slide slide ");
        if (category != null) {
            if (category.getParent() == null)//one
                hql.append(" where slide.category in (select category from Category category where category.parent=:category)");
            else
                hql.append(" where slide.category=:category");
        }
        hql.append(" order by slide.id desc");


        Query query = entityManager.createQuery(hql.toString());
        if (category != null) {
            if (category.getParent() == null) {//one
                query.setParameter("category", category);
            } else
                query.setParameter("category", category);
        }

        query.setMaxResults(top);
        List list = query.getResultList();
        for (Object object : list) {
            slides.add((Slide) object);
        }
        return slides;
    }
}

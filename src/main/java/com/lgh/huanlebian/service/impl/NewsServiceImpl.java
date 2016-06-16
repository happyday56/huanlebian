package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.CategoryKind;
import com.lgh.huanlebian.entity.Kind;
import com.lgh.huanlebian.entity.News;
import com.lgh.huanlebian.model.WebCategoryListModel;
import com.lgh.huanlebian.model.WebNewsModel;
import com.lgh.huanlebian.repository.CategoryKindRepository;
import com.lgh.huanlebian.repository.CategoryRepository;
import com.lgh.huanlebian.service.NewsService;
import com.lgh.huanlebian.service.StaticResourceService;
import com.lgh.huanlebian.service.URIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lgh on 2016/5/27.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    EntityManager entityManager;

    @Autowired
    CategoryKindRepository categoryKindRepository;

    @Autowired
    URIService uriService;

    @Autowired
    CategoryRepository categoryRepository;


    @Autowired
    StaticResourceService  staticResourceService;


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

    @Override
    public List<WebCategoryListModel> getFullPath(Category category, Kind kind) {
        List<WebCategoryListModel> webCategoryListModels = new ArrayList<>();

        if (category != null && category.getParent() != null) {
            webCategoryListModels.add(new WebCategoryListModel(category.getParent().getTitle(), uriService.getCategoryURI(category.getParent().getPath())));

            webCategoryListModels.add(new WebCategoryListModel(category.getTitle(), uriService.getCategoryURI(category.getParent().getPath(),category.getPath())));

            CategoryKind categoryKind = categoryKindRepository.findByCategoryAndKind(category, kind);
            if (categoryKind != null) {
                webCategoryListModels.add(new WebCategoryListModel(categoryKind.getTitle(), uriService.getCategoryURI(category.getParent().getPath()
                        , category.getPath(), kind.getPath())));
            }
        }

        return webCategoryListModels;
    }

    @Override
    public WebNewsModel toWebNewsModel(News news) throws URISyntaxException {
        WebNewsModel webNewsModel = new WebNewsModel();
        webNewsModel.setTitle(news.getTitle());
        webNewsModel.setDescription(news.getDescription());
        webNewsModel.setKeywords(news.getKeywords());
        webNewsModel.setContent(news.getContent());
        webNewsModel.setId(news.getId());
        webNewsModel.setPictureUrl(staticResourceService.getResource(news.getPictureUrl()).toString());
        webNewsModel.setSummary(news.getSummary());
        webNewsModel.setUploadTime(news.getUploadTime());
        webNewsModel.setViews(news.getViews());
        webNewsModel.setUrl(uriService.getNewURI(news.getId()));
        return webNewsModel;
    }
}

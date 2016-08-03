package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.CategoryKind;
import com.lgh.huanlebian.entity.Kind;
import com.lgh.huanlebian.entity.News;
import com.lgh.huanlebian.entity.Slide;
import com.lgh.huanlebian.entity.pk.CategoryKindPK;
import com.lgh.huanlebian.model.*;
import com.lgh.huanlebian.repository.CategoryKindRepository;
import com.lgh.huanlebian.repository.CategoryRepository;
import com.lgh.huanlebian.repository.KindRepository;
import com.lgh.huanlebian.repository.NewsRepository;
import com.lgh.huanlebian.repository.SlideRepository;
import com.lgh.huanlebian.service.*;
import com.lgh.huanlebian.service.impl.WebService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分类
 * Created by Administrator on 2016/5/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
@RequestMapping("/d")
public class CategoryController {

    private static Log log = LogFactory.getLog(CategoryController.class);


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    CategoryKindRepository categoryKindRepository;

    @Autowired
    NewsRepository newsRepository;

    @Autowired
    SlideService slideService;

    @Autowired
    NewsService newsService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    StaticResourceService staticResourceService;

    @Autowired
    URIService uriService;

    @Autowired
    private KindRepository kindRepository;

    @Autowired
    private WebService webService;

    /**
     * 一级分类
     *
     * @param one   分类路径
     * @param model
     * @return
     */
    @RequestMapping(value = "/{one}", method = RequestMethod.GET)
    public String one(@PathVariable("one") String one, Model model) throws URISyntaxException {

        Category oneCategory = categoryRepository.findByPath(one);
        if (oneCategory != null) {
            WebCategoryPageModel webCategoryPageModel = new WebCategoryPageModel();
            webCategoryPageModel.setTitle(oneCategory.getTitle());
            webCategoryPageModel.setKeywords(oneCategory.getKeywords());
            webCategoryPageModel.setDescription(oneCategory.getDescription());

            webCategoryPageModel.setCategoryName(oneCategory.getTitle());
            webCategoryPageModel.setCategoryUrl(uriService.getCategoryURI(one));

            //分类部分
            List<Category> categories = categoryRepository.findAllByParentOrderBySortAsc(oneCategory);
            List<WebCategoryListModel> webCategoryListModels = new ArrayList<>();
            for (Category category : categories) {
                webCategoryListModels.add(new WebCategoryListModel(category.getTitle(), uriService.getCategoryURI(one, category.getPath())));
            }
            webCategoryPageModel.setTopNav(webCategoryListModels);

            //轮播部分
            List<WebSlideListModel> webSlideListModels = new ArrayList<>();
            List<Slide> slides = slideService.findTopSlideList(oneCategory, 5);
            for (Slide slide : slides) {
                webSlideListModels.add(new WebSlideListModel(slide.getTitle()
                        , webService.handlePicture(slide.getImageUrl()), slide.getUrl()));
            }
            webCategoryPageModel.setSlideList(webSlideListModels);

            //子项列表
            List<WebNewsListModel> webNewsListModels = new ArrayList<>();
            List<News> newsList = newsService.getTopByCategoryOrderById(oneCategory, 10);
            for (News news : newsList) {
                webNewsListModels.add(new WebNewsListModel(news.getTitle(), uriService.getNewURI(news.getId())
                        , webService.handlePicture(news.getPictureUrl()), news.getSummary()));
            }
            webCategoryPageModel.setWebNewsList(webNewsListModels);

            model.addAttribute("page", webCategoryPageModel);
        }
        return "category/index";
    }

    /**
     * 二级分类
     *
     * @param two
     * @param model
     * @return
     */
    @RequestMapping(value = "/{one}/{two}", method = RequestMethod.GET)
    public String two(@PathVariable("one") String one, @PathVariable("two") String two, Model model) throws URISyntaxException {

        Category secondCategory = categoryRepository.findByPath(two);
        if (secondCategory != null) {
            WebSecondCategoryPageModel webSecondCategoryPageModel = new WebSecondCategoryPageModel();
            webSecondCategoryPageModel.setTitle(secondCategory.getTitle());
            webSecondCategoryPageModel.setKeywords(secondCategory.getKeywords());
            webSecondCategoryPageModel.setDescription(secondCategory.getDescription());

            webSecondCategoryPageModel.setCategoryName(secondCategory.getTitle());
            webSecondCategoryPageModel.setParentCategoryName(secondCategory.getParent().getTitle());
            webSecondCategoryPageModel.setParentCategoryUrl(uriService.getCategoryURI(one));

            //种类部分
            List<CategoryKind> categoryKinds = categoryKindRepository.findAllByCategory(secondCategory);
            List<WebCategoryListModel> webCategoryListModels = new ArrayList<>();
            for (CategoryKind categoryKind : categoryKinds) {
                webCategoryListModels.add(new WebCategoryListModel(categoryKind.getTitle()
                        , uriService.getCategoryURI(one, two, categoryKind.getKind().getPath())));
            }
            webSecondCategoryPageModel.setTopNav(webCategoryListModels);

            //轮播部分
            List<WebSlideListModel> webSlideListModels = new ArrayList<>();
            List<Slide> slides = slideService.findTopSlideList(secondCategory, 5);
            for (Slide slide : slides) {
                webSlideListModels.add(new WebSlideListModel(slide.getTitle()
                        , webService.handlePicture(slide.getImageUrl()), slide.getUrl()));
            }
            webSecondCategoryPageModel.setSlideList(webSlideListModels);


            //子项列表
            List<WebNewsListModel> webSubNewsListModels = new ArrayList<>();
            List<News> newsList = newsService.getTopByCategoryOrderById(secondCategory, 10);
            for (News news : newsList) {
                webSubNewsListModels.add(new WebNewsListModel(news.getTitle(), uriService.getNewURI(news.getId())
                        , webService.handlePicture(news.getPictureUrl()), news.getSummary()));
            }
            webSecondCategoryPageModel.setWebNewsList(webSubNewsListModels);

            model.addAttribute("page", webSecondCategoryPageModel);
        }

        return "category/secondindex";
    }

    /**
     * 二级分类和种类
     *
     * @param two
     * @param kindPath 种类路径
     * @param model
     * @return
     */
    @RequestMapping(value = "/{one}/{two}/{kind}", method = RequestMethod.GET)
    public String three(@PathVariable("two") String two, @PathVariable("kind") String kindPath, Model model) throws URISyntaxException {
        loadThreeIndex(two, kindPath, 0, model);
        return "category/threeindex";
    }

    @RequestMapping(value = "/{one}/{two}/{kind}/index_{page}.html", method = RequestMethod.GET)
    public String three(@PathVariable("two") String two, @PathVariable("kind") String kindPath
            , @PathVariable("page") Integer page, Model model) throws URISyntaxException {
        loadThreeIndex(two, kindPath, page, model);
        return "category/threeindex";
    }

    /**
     *  获取二级分类及种类
     * @param two
     * @param kindPath
     * @param page
     * @param model
     */
    private void loadThreeIndex(String two, String kindPath, Integer page, Model model) {
        Category secondCategory = categoryRepository.findByPath(two);
        Kind kind = kindRepository.findByPath(kindPath);
        if (secondCategory != null && kind != null) {
//            CategoryKindPK categoryKindPK = new CategoryKindPK(secondCategory.getId(), kind.getId());
//            CategoryKind categoryKind = categoryKindRepository.getOne(categoryKindPK);

            WebThreeCategoryPageModel webThreeCategoryPageModel = new WebThreeCategoryPageModel();
            webThreeCategoryPageModel.setTitle(secondCategory.getTitle());
            webThreeCategoryPageModel.setKeywords(secondCategory.getKeywords());
            webThreeCategoryPageModel.setDescription(secondCategory.getDescription());

            webThreeCategoryPageModel.setHeadName(secondCategory.getParent().getTitle());
            webThreeCategoryPageModel.setHeadUrl(uriService.getCategoryURI(secondCategory.getParent().getPath()));
            webThreeCategoryPageModel.setTopNav(newsService.getFullPath(secondCategory, kind));


            Integer size = 10;
            Pageable pageable = new PageRequest(page, size, new Sort(Sort.Direction.DESC, "id"));
            //子项列表
            List<WebNewsListModel> webSubNewsListModels = new ArrayList<>();
            Page<News> newsList = newsRepository.findAllByCategoryAndKind(secondCategory, kind, pageable);
            for (News news : newsList) {
                webSubNewsListModels.add(new WebNewsListModel(news.getTitle(), uriService.getNewURI(news.getId())
                        , webService.handlePicture(news.getPictureUrl()), news.getSummary()));
            }
            webThreeCategoryPageModel.setWebNewsList(webSubNewsListModels);
            Paging paging = new Paging();
            paging.setSize(size);
            paging.setPage(page);
            paging.setCount(pageable.());
            webThreeCategoryPageModel.setPaging(paging);
            model.addAttribute("page", webThreeCategoryPageModel);
        }
    }
}

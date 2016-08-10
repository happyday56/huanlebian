package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.News;
import com.lgh.huanlebian.entity.Slide;
import com.lgh.huanlebian.model.*;
import com.lgh.huanlebian.repository.CategoryRepository;
import com.lgh.huanlebian.service.*;
import com.lgh.huanlebian.service.impl.WebService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
public class IndexController {

    private static Log log = LogFactory.getLog(IndexController.class);
    private static Integer NEWS_PAGE_SIZE = 5;
    private static Integer SLIDE_PAGE_SIZE=2;

    @Autowired
    private URIService uriService;

    @Autowired
    private SlideService slideService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private StaticResourceService staticResourceService;

    @Autowired
    private NewsService newsService;

    @Autowired
    private WebService webService;

    @Autowired
    private WikiSerice wikiSerice;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) throws URISyntaxException {
        WebIndexPageModel webIndexPageModel = new WebIndexPageModel();

        //主导航
        List<WebIndexCategoryListModel> webIndexCategoryListModels = new ArrayList<>();
        List<Category> categories = categoryRepository.findAllByParentOrderBySortAsc(null);
        for (Category category : categories) {
            WebIndexCategoryListModel webIndexCategoryListModel = new WebIndexCategoryListModel(category.getTitle(), uriService.getCategoryURI(category.getPath()));

            List<WebCategoryListModel> webCategoryListModels = new ArrayList<>();
            List<Category> subCategories = categoryRepository.findAllByParentOrderBySortAsc(category);
            subCategories.forEach(x -> {
                webCategoryListModels.add(new WebCategoryListModel(x.getTitle(), uriService.getCategoryURI(category.getPath(), x.getPath())));
            });
            webIndexCategoryListModel.setSub(webCategoryListModels);

            webIndexCategoryListModels.add(webIndexCategoryListModel);
        }
        webIndexPageModel.setTopNav(webIndexCategoryListModels);

        //轮播
        List<WebSlideListSummaryModel> webSlideListSummaryModels = new ArrayList<>();
        List<Slide> slides = slideService.findTopSlideList(null, SLIDE_PAGE_SIZE);
        for (Slide slide : slides) {
            webSlideListSummaryModels.add(new WebSlideListSummaryModel(slide.getTitle()
                    , webService.handlePicture(slide.getImageUrl())
                    , slide.getUrl(), slide.getSummary()));
        }
        webIndexPageModel.setSlideList(webSlideListSummaryModels);


        //内容列表
        List<WebIndexContentListModel> webIndexContentListModels = new ArrayList<>();
        for (Category category : categories) {
            List<News> newses = newsService.getTopByCategoryOrderById(category, NEWS_PAGE_SIZE);
            WebIndexContentListModel webIndexContentListModel = new WebIndexContentListModel();

            List<WebNewsListModel> webNewsListModels = new ArrayList<>();
            final int[] count = {0};
            newses.forEach(x -> {
                String imageUrl = webService.handlePicture(x.getPictureUrl());

                if (count[0] == 0) {
                    webIndexContentListModel.setTitle(x.getTitle());
                    webIndexContentListModel.setImageUrl(imageUrl);
                    webIndexContentListModel.setUrl(uriService.getNewURI(x.getId()).toString());
                } else {
                    webNewsListModels.add(new WebNewsListModel(
                            x.getTitle()
                            , uriService.getNewURI(x.getId()).toString()
                            , imageUrl
                            , x.getSummary(),x.getUploadTime()));
                }
                count[0]++;
            });
            if (webNewsListModels.size() > 0) webIndexContentListModel.setNewsList(webNewsListModels);

            if (newses.size() > 0)
                webIndexContentListModels.add(webIndexContentListModel);
        }


        webIndexPageModel.setContentList(webIndexContentListModels);

        model.addAttribute("page", webIndexPageModel);
        return "index";
    }


    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(Model model) {
        WebCategoryPageModel webCategoryPageModel = new WebCategoryPageModel();
        webCategoryPageModel.setTitle("网页标题1");
        webCategoryPageModel.setKeywords("关键字1");
        webCategoryPageModel.setDescription("描述1");

        List<Category> categories = categoryRepository.findAllByParentOrderBySortAsc(null);

        List<WebCategoryListModel> webCategoryListModels = new ArrayList<>();
        for (Category category : categories) {
            webCategoryListModels.add(new WebCategoryListModel(category.getTitle(), "d/" + category.getPath()));
        }
        webCategoryPageModel.setTopNav(webCategoryListModels);


        List<WebNewsListModel> webNewsListModels = new ArrayList<>();
//        webNewsListModels.add(new WebNewsListModel("开心", "a", "", ""));
//        webNewsListModels.add(new WebNewsListModel("快乐", "b", "", ""));
        model.addAttribute("page", webCategoryPageModel);
        return "test";
    }


    @RequestMapping(value = "/initbaike", method = RequestMethod.GET)
    @ResponseBody
    public String initBaike() {
        wikiSerice.initBaikeCategory();
        return "ok";
    }
}

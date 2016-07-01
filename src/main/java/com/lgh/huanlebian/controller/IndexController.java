package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.Slide;
import com.lgh.huanlebian.model.*;
import com.lgh.huanlebian.repository.CategoryRepository;
import com.lgh.huanlebian.repository.SlideRepository;
import com.lgh.huanlebian.service.SlideService;
import com.lgh.huanlebian.service.URIService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/5/8.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Controller
public class IndexController {

    private static Log log = LogFactory.getLog(IndexController.class);

    @Autowired
    URIService uriService;

    @Autowired
    SlideService slideService;

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
        WebIndexPageModel webIndexPageModel = new WebIndexPageModel();

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

        List<WebSlideListModel> webSlideListModels = new ArrayList<>();
        List<Slide> slides = slideService.findTopSlideList(null, 5);
        for (Slide slide : slides) {
            webSlideListModels.add(new WebSlideListModel(slide.getTitle(), slide.getImageUrl(), slide.getUrl()));
        }
        webIndexPageModel.setSlideList(webSlideListModels);

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
        webNewsListModels.add(new WebNewsListModel("开心", "a", "", ""));
        webNewsListModels.add(new WebNewsListModel("快乐", "b", "", ""));
        model.addAttribute("page", webCategoryPageModel);
        return "test";
    }
}

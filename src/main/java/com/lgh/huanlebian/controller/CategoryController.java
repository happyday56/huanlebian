package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.Slide;
import com.lgh.huanlebian.model.*;
import com.lgh.huanlebian.repository.CategoryRepository;
import com.lgh.huanlebian.repository.SlideRepository;
import com.lgh.huanlebian.service.SlideService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

/**
 * 分类
 * Created by Administrator on 2016/5/8.
 */
@Controller
@RequestMapping("/d")
public class CategoryController {

    private static Log log = LogFactory.getLog(CategoryController.class);


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SlideRepository slideRepository;

    @Autowired
    SlideService slideService;

    /**
     * 一级分类
     *
     * @param one   分类路径
     * @param model
     * @return
     */
    @RequestMapping(value = "/{one}", method = RequestMethod.GET)
    public String one(@PathVariable("one") String one, Model model) {

        Category oneCategory = categoryRepository.findByPath(one);
        if (oneCategory != null) {
            WebCategoryPageModel webCategoryPageModel = new WebCategoryPageModel();
            webCategoryPageModel.setTitle(oneCategory.getTitle());
            webCategoryPageModel.setKeywords(oneCategory.getKeywords());
            webCategoryPageModel.setDescription(oneCategory.getDescription());

            webCategoryPageModel.setCategoryName(oneCategory.getTitle());

            List<Category> categories = categoryRepository.findAllByParent(oneCategory);
            List<WebCategoryListModel> webCategoryListModels = new ArrayList<>();
            for (Category category : categories) {
                webCategoryListModels.add(new WebCategoryListModel(category.getTitle(), category.getPath()));
            }
            webCategoryPageModel.setTopNav(webCategoryListModels);

            List<WebSlideListModel> webSlideListModels = new ArrayList<>();
            List<Slide> slides = slideService.findTopSlideList(oneCategory);
            for (Slide slide : slides) {
                webSlideListModels.add(new WebSlideListModel(slide.getTitle(), slide.getImageUrl(), slide.getUrl()));
            }
            webCategoryPageModel.setSlideList(webSlideListModels);

            //子项列表
            List<WebSubNewsListModel> webSubNewsListModels = new ArrayList<>();
            webCategoryPageModel.setSubNewsList(webSubNewsListModels);

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
    public String two(@PathVariable("two") String two, Model model) {

        Category secondCategory = categoryRepository.findByPath(two);
        if (secondCategory != null) {
            WebSecondCategoryPageModel webSecondCategoryPageModel = new WebSecondCategoryPageModel();
            webSecondCategoryPageModel.setTitle(secondCategory.getTitle());
            webSecondCategoryPageModel.setKeywords(secondCategory.getKeywords());
            webSecondCategoryPageModel.setDescription(secondCategory.getDescription());

            webSecondCategoryPageModel.setCategoryName(secondCategory.getTitle());

            List<Category> categories = categoryRepository.findAllByParent(secondCategory);
            List<WebCategoryListModel> webCategoryListModels = new ArrayList<>();
            for (Category category : categories) {
                webCategoryListModels.add(new WebCategoryListModel(category.getTitle(), category.getPath()));
            }
            webSecondCategoryPageModel.setTopNav(webCategoryListModels);

            List<WebSlideListModel> webSlideListModels = new ArrayList<>();
            List<Slide> slides = slideService.findTopSlideList(secondCategory);
            for (Slide slide : slides) {
                webSlideListModels.add(new WebSlideListModel(slide.getTitle(), slide.getImageUrl(), slide.getUrl()));
            }
            webSecondCategoryPageModel.setSlideList(webSlideListModels);

            //子项列表
            List<WebNewsListModel> webSubNewsListModels = new ArrayList<>();
            webSecondCategoryPageModel.setWebNewsList(webSubNewsListModels);

            model.addAttribute("page", webSecondCategoryPageModel);
        }

        return "category/secondindex";
    }

    /**
     * 二级分类和种类
     *
     * @param two
     * @param kind  种类路径
     * @param model
     * @return
     */
    @RequestMapping(value = "/{one}/{two}/{kind}", method = RequestMethod.GET)
    public String three(@PathVariable("two") String two, @PathVariable("kind") String kind, Model model) {
        return "category/index";
    }
}

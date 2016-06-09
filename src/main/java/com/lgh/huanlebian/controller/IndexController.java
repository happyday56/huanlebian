package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.model.WebCategoryListModel;
import com.lgh.huanlebian.model.WebCategoryPageModel;
import com.lgh.huanlebian.model.WebNewsListModel;
import com.lgh.huanlebian.repository.CategoryRepository;
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
@Controller
public class IndexController {

    private static Log log = LogFactory.getLog(IndexController.class);

    @Autowired
    private CategoryRepository categoryRepository;

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String index(Model model) {
//        model.addAttribute("title", "网页标题");
//        model.addAttribute("keywords", "关键字");
//        model.addAttribute("description", "描述");

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
        webNewsListModels.add(new WebNewsListModel("开心", "a","",""));
        webNewsListModels.add(new WebNewsListModel("快乐", "b","",""));
//        webCategoryPageModel.setNewsList(webNewsListModels);

        model.addAttribute("page", webCategoryPageModel);


        return "test";
    }
}

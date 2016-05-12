package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.model.WebCategoryListModel;
import com.lgh.huanlebian.model.WebCategoryPageModel;
import com.lgh.huanlebian.repository.CategoryRepository;
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

    /**
     * 分类首页
     *
     * @param categoryPath 分类路径
     * @param model
     * @return
     */
    @RequestMapping(value = "/{categoryPath}", method = RequestMethod.GET)
    public String index(@PathVariable("categoryPath") String categoryPath, Model model) {

        Category parentCategory = categoryRepository.findByPath(categoryPath);
        if (parentCategory != null) {
            WebCategoryPageModel webCategoryPageModel = new WebCategoryPageModel();
            webCategoryPageModel.setTitle(parentCategory.getTitle());
            webCategoryPageModel.setKeywords(parentCategory.getKeywords());
            webCategoryPageModel.setDescription(parentCategory.getDescription());

            webCategoryPageModel.setCategoryName(parentCategory.getTitle());

            List<Category> categories = categoryRepository.findAllByParent(parentCategory);
            List<WebCategoryListModel> webCategoryListModels = new ArrayList<>();
            for (Category category : categories) {
                webCategoryListModels.add(new WebCategoryListModel(category.getTitle(), category.getPath()));
            }
            webCategoryPageModel.setTopNav(webCategoryListModels);

            model.addAttribute("page", webCategoryPageModel);
        }
        return "category/index";
    }
}

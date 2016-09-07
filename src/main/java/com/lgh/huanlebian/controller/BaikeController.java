package com.lgh.huanlebian.controller;

import com.lgh.huanlebian.entity.WikiCategory;
import com.lgh.huanlebian.model.WebBaikeCategoryListModel;
import com.lgh.huanlebian.model.WebBaikePageModel;
import com.lgh.huanlebian.repository.WikiCategoryRepository;
import com.lgh.huanlebian.service.URIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Administrator on 2016/9/3.
 */
@Controller
@RequestMapping("/baike")
public class BaikeController {

    @Autowired
    private WikiCategoryRepository wikiCategoryRepository;

    @Autowired
    private URIService uriService;


    /**
     * 百科首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = {"/", ""})
    public String index(Model model) {
        WebBaikePageModel webBaikePageModel = new WebBaikePageModel();

        List<WikiCategory> wikiCategoriesAll = wikiCategoryRepository.findAll();
        //查找顶级分类
        List<WikiCategory> wikiCategoriesParent = wikiCategoriesAll.stream().filter(x -> x.getParent() == null)
                .sorted((x1, x2) -> x1.getSort().compareTo(x2.getSort())).collect(Collectors.toList());


        List<WebBaikeCategoryListModel> top = new ArrayList<>();
//        List<WikiCategory> wikiCategories = wikiCategoryRepository.findAllByParentOrderBySort(null);
        if (wikiCategoriesParent.size() > 0) {
            for (WikiCategory wikiCategory : wikiCategoriesParent) {
                WebBaikeCategoryListModel webBaikeCategoryListModel = new WebBaikeCategoryListModel();
                webBaikeCategoryListModel.setTitle(wikiCategory.getTitle());
                webBaikeCategoryListModel.setUrl(uriService.getWikiCategoryURI(wikiCategory.getPath()));

                if (!wikiCategory.getIsMain()) {
//                    List<WikiCategory> wikiCategories1 = wikiCategoryRepository.findAllByParentOrderBySort(wikiCategory);
                    List<WikiCategory> wikiCategoriesSecond = wikiCategoriesAll.stream().filter(x -> x.getParent() != null && x.getParent().equals(wikiCategory))
                            .sorted((x1, x2) -> x1.getSort().compareTo(x2.getSort())).collect(Collectors.toList());

                    List<WebBaikeCategoryListModel> webCategoryListModels = wikiCategoriesSecond.stream().map(wikiCategory1 ->
                            new WebBaikeCategoryListModel(wikiCategory1.getTitle(), uriService.getWikiCategoryURI(wikiCategory1.getPath())))
                            .collect(Collectors.toList());
                    webBaikeCategoryListModel.setSub(webCategoryListModels);
                }

                top.add(webBaikeCategoryListModel);
            }
        }
        webBaikePageModel.setTopNav(top);


        List<WebBaikeCategoryListModel> list = new ArrayList<>();
        Integer currentLevel = 0;
        Integer toLevel = 3;
        //获得主要分类
        for (WikiCategory wikiCategory : wikiCategoriesParent) {
            if (wikiCategory.getIsMain()) {
                list.add(getWebBaikeCategoryList(wikiCategoriesAll, wikiCategory, currentLevel, toLevel));
            } else {
                List<WikiCategory> wikiCategoriesSecond = wikiCategoriesAll.stream().filter(x -> x.getParent() != null && x.getParent().equals(wikiCategory))
                        .sorted((x1, x2) -> x1.getSort().compareTo(x2.getSort())).collect(Collectors.toList());
                wikiCategoriesSecond.forEach(x -> list.add(getWebBaikeCategoryList(wikiCategoriesAll, x, currentLevel, toLevel)));
            }
        }
        webBaikePageModel.setList(list);

        model.addAttribute("page", webBaikePageModel);
        return "baike/index";
    }

    private WebBaikeCategoryListModel getWebBaikeCategoryList(List<WikiCategory> wikiCategoriesAll, WikiCategory wikiCategory
            , Integer currentLevel, Integer toLevel) {
        WebBaikeCategoryListModel webBaikeCategoryListModel = new WebBaikeCategoryListModel();
        if (currentLevel < toLevel) {
            currentLevel = currentLevel + 1;
            webBaikeCategoryListModel.setTitle(wikiCategory.getTitle());
            if (currentLevel == 1)
                webBaikeCategoryListModel.setUrl(uriService.getWikiCategoryURI(wikiCategory.getPath()));
            else if (currentLevel == 3)
                webBaikeCategoryListModel.setUrl(uriService.getWikiListURI(wikiCategory.getId()));


            List<WikiCategory> wikiCategoriesSecond = wikiCategoriesAll.stream().filter(x -> x.getParent() != null && x.getParent().equals(wikiCategory))
                    .sorted((x1, x2) -> x1.getSort().compareTo(x2.getSort())).collect(Collectors.toList());

            List<WebBaikeCategoryListModel> webBaikeCategoryListModels = new ArrayList<>();
            Integer finalCurrentLevel = currentLevel;
            wikiCategoriesSecond.forEach(x -> webBaikeCategoryListModels.add(getWebBaikeCategoryList(wikiCategoriesAll, x, finalCurrentLevel, toLevel)));
            webBaikeCategoryListModel.setSub(webBaikeCategoryListModels);
        }
        return webBaikeCategoryListModel;
    }


    /**
     * 百科主分类页面
     *
     * @param path
     * @return
     */
    @RequestMapping("/{path}")
    public String category(@PathVariable(value = "path") String path) {
        return "baike/category";
    }

}

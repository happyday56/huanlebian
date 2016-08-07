package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.constant.CatetoryConstant;
import com.lgh.huanlebian.entity.WikiCategory;
import com.lgh.huanlebian.repository.WikiCategoryRepository;
import com.lgh.huanlebian.service.SpliderService;
import com.lgh.huanlebian.service.WikiSerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/7.
 */
@Service
public class WikiSericeImpl implements WikiSerice {
    @Autowired
    private WikiCategoryRepository wikiCategoryRepository;

    @Autowired
    private SpliderService spliderService;

    public void initBaikeCategory() {

        //todo keywords despcrition
        List<WikiCategory> list = new ArrayList<>();
        WikiCategory parentWikiCategory = new WikiCategory(0L, null, "怀孕", CatetoryConstant.huaiyun, "", "", 1, false);
        parentWikiCategory = wikiCategoryRepository.save(parentWikiCategory);
        list.add(new WikiCategory(0L, parentWikiCategory, "备孕", CatetoryConstant.yunqian, "", "", 1, true));
        list.add(new WikiCategory(0L, parentWikiCategory, "孕期", CatetoryConstant.yunqi, "", "", 2, true));
        list.add(new WikiCategory(0L, parentWikiCategory, "分娩", CatetoryConstant.fenmian, "", "", 3, true));
        list.add(new WikiCategory(0L, parentWikiCategory, "月子", CatetoryConstant.yuezi, "", "", 4, true));
        list = wikiCategoryRepository.save(list);
        for(WikiCategory wikiCategory :list) {
            spliderService.initWikiData(wikiCategory);
        }

        parentWikiCategory = new WikiCategory(0L, null, "育儿", CatetoryConstant.yuer, "", "", 2, false);
        parentWikiCategory = wikiCategoryRepository.save(parentWikiCategory);
        list.add(new WikiCategory(0L, parentWikiCategory, "新生儿", CatetoryConstant.xinshenger, "", "", 1, true));
        list.add(new WikiCategory(0L, parentWikiCategory, "0-1岁", CatetoryConstant.yinger, "", "", 2, true));
        list.add(new WikiCategory(0L, parentWikiCategory, "1-3岁", CatetoryConstant.youer, "", "", 3, true));
        list.add(new WikiCategory(0L, parentWikiCategory, "3-6岁", CatetoryConstant.xuelingqian, "", "", 4, true));
        list = wikiCategoryRepository.save(list);
        for(WikiCategory wikiCategory :list) {
            spliderService.initWikiData(wikiCategory);
        }

        parentWikiCategory = new WikiCategory(0L, null, "营养", CatetoryConstant.meishi, "", "", 3, true);
        parentWikiCategory = wikiCategoryRepository.save(parentWikiCategory);
        spliderService.initWikiData(parentWikiCategory);

        parentWikiCategory = new WikiCategory(0L, null, "生活", CatetoryConstant.shenghuo, "", "", 4, true);
        parentWikiCategory = wikiCategoryRepository.save(parentWikiCategory);
        spliderService.initWikiData(parentWikiCategory);

        parentWikiCategory = new WikiCategory(0L, null, "用品", CatetoryConstant.yongpin, "", "", 5, true);
        parentWikiCategory = wikiCategoryRepository.save(parentWikiCategory);
        spliderService.initWikiData(parentWikiCategory);

    }
}

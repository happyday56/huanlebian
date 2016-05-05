package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.SystemConfig;
import com.lgh.huanlebian.model.CommonVersion;
import com.lgh.huanlebian.repository.CategoryRepository;
import com.lgh.huanlebian.repository.SystemConfigRepository;
import com.lgh.huanlebian.service.JdbcService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.sql.Statement;

/**
 * 启动设置
 * Created by lgh on 2016/5/5.
 */
@Service
public class WebBootService implements ApplicationListener<ContextRefreshedEvent> {

    private static Log log = LogFactory.getLog(WebBootService.class);

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private BaseService baseService;

    @Autowired
    private JdbcService jdbcService;

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() != null) {
            SystemConfig systemConfig = systemConfigRepository.findOne("DatabaseVersion");
            if (systemConfig == null) {
                SystemConfig databaseVession = new SystemConfig();
                databaseVession.setCode("DatabaseVersion");
                databaseVession.setValueForCode(String.valueOf(CommonVersion.initVersion.ordinal()));
                systemConfigRepository.save(databaseVession);
            }


            //系统升级
            baseService.systemUpgrade("DatabaseVersion", CommonVersion.class, CommonVersion.Version101, (upgrade) -> {
                switch (upgrade) {
                    case Version101:
                        //系统分类
                        try {
                            initCategory();
                        } catch (Exception e) {
                            log.info("upgrade to " + CommonVersion.Version101.ordinal() + " error", e);
                        }

                        break;
                }
            });

        }
    }


    private void initCategory() {
        String description = "欢乐变母婴网以备孕男女、孕产妇为主要服务对象，提供备孕、孕早中晚期等各个特定时间段的专业知识，内容涵盖孕前准备、孕妇的饮食、保健、疾病等方面。致力于用鲜活实用的知识，打造受众人群每日必读的孕育宝典。";
        Category parentCategory = new Category(0L, null, "怀孕", "huaiyun", "准备怀孕,备孕,生男孩,生女孩,起名字大全,避孕,流产,遗传,优生,孕前饮食,孕前检查,孕期保健", description, 1);
        parentCategory = categoryRepository.save(parentCategory);

        Category category = new Category(0L, parentCategory, "备孕", "beiyun", "遗传优生,生男生女,孕前准备,孕前饮食,不孕不育,人工受孕,怀孕测试", description, 1);
        categoryRepository.save(category);

        category = new Category(0L, parentCategory, "孕早", "beiyun", "遗传优生,生男生女,孕前准备,孕前饮食,不孕不育,人工受孕,怀孕测试", description, 1);
        categoryRepository.save(category);


    }
}

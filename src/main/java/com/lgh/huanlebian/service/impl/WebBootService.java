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
import java.util.ArrayList;
import java.util.List;

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
        List<Category> list = new ArrayList<>();
        Category parentCategory;
        String description;

        description = "欢乐变母婴网以备孕男女、孕产妇为主要服务对象，提供备孕、孕早中晚期等各个特定时间段的专业知识，内容涵盖孕前准备、孕妇的饮食、保健、疾病等方面。致力于用鲜活实用的知识，打造受众人群每日必读的孕育宝典。";
        parentCategory = new Category(0L, null, "怀孕", "huaiyun", "准备怀孕,备孕,生男孩,生女孩,起名字大全,避孕,流产,遗传,优生,孕前饮食,孕前检查,孕期保健", description, 1);
        parentCategory = categoryRepository.save(parentCategory);
        list.add(new Category(0L, parentCategory, "备孕", "beiyun", "遗传优生,生男生女,孕前准备,孕前饮食,不孕不育,人工受孕,怀孕测试", description, 1));
        list.add(new Category(0L, parentCategory, "孕早期", "yunzaoqi", "孕早期,孕早期注意事项,孕早期症状,怀孕初期症状,孕早期经历", description, 2));
        list.add(new Category(0L, parentCategory, "孕中期", "yunzhongqi", "孕中期,孕中期注意事项,孕中期症状,孕中期经历", description, 3));
        list.add(new Category(0L, parentCategory, "孕晚期", "yunwanqi", "孕晚期,孕晚期注意事项,孕晚期症状,怀孕晚期症状,孕晚期经历", description, 4));
        list.add(new Category(0L, parentCategory, "分娩", "fenmian", "分娩方式,生孩子,怎样分娩,分娩症状,产前征兆,陪伴分娩,产前心理,分娩时刻,待产物品", description, 5));
        categoryRepository.save(list);

        description = "欢乐变母婴网产后频道以产后妈妈为主要服务对象，致力于为产后妈妈提供全方位的产后恢复、身心保健知识。频道下设女性健康、产后减肥、产后美容等多个栏目，帮助妈妈平稳健康的度过产后恢复期，塑造美丽健康新形象。";
        parentCategory = new Category(0L, null, "怀孕", "chanhou", "产后恢复,产后减肥,产后避孕,产后调理,产后同房,产后检查,产后保健", description, 2);
        parentCategory = categoryRepository.save(parentCategory);
        list.add(new Category(0L, parentCategory, "月子", "yuezi", "孕晚期,孕晚期注意事项,孕晚期症状,怀孕晚期症状,孕晚期经历", description, 1));
        list.add(new Category(0L, parentCategory, "减肥", "jianfei", "产后减肥,产后瘦身，产后健身操，产后如何减肥", "产后如何瘦身，产后怎样减肥？产后健身操要怎么做？健康的减肥方法,帮助新妈妈快速、健康恢复美丽！", 2));
        list.add(new Category(0L, parentCategory, "避孕", "biyun", "产后如何避孕,产后避孕注意事项,产后性生活,产妇多久可以同房,产后避孕措施,安全套", "避孕流产栏目为女性提供避孕知识，让女性了解什么是流产以及流产后的注意事项，为女性健康服务！", 3));
        list.add(new Category(0L, parentCategory, "大姨妈", "dayima", "大姨妈,产后恢复攻略,产后恢复体操,产后恢复身材,产后恢复注意事项", "产后恢复怎么做？产后恢复攻略有哪些，产后恢复体操怎么做，产后恢复身材方法有哪些？产后恢复栏目告诉你产后恢复注意事项,帮助妈妈们做好防御！", 4));
        list.add(new Category(0L, parentCategory, "女性健康", "biaojian", "女性健康,产后女性生理健康,产后私处护理,产后身体疾病", "用专业的建议教您应对产后盗汗、体虚等健康问题，用有效的经验助您尽快恢复伤口，让您产后得到最好的恢复，尽享健康生活。", 5));
        categoryRepository.save(list);


    }
}

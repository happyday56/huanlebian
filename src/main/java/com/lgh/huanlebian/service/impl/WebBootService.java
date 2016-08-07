package com.lgh.huanlebian.service.impl;

import com.lgh.huanlebian.constant.CatetoryConstant;
import com.lgh.huanlebian.constant.KindConstant;
import com.lgh.huanlebian.entity.WikiCategory;
import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.CategoryKind;
import com.lgh.huanlebian.entity.Kind;
import com.lgh.huanlebian.entity.SystemConfig;
import com.lgh.huanlebian.model.CommonVersion;
import com.lgh.huanlebian.repository.WikiCategoryRepository;
import com.lgh.huanlebian.repository.CategoryKindRepository;
import com.lgh.huanlebian.repository.CategoryRepository;
import com.lgh.huanlebian.repository.KindRepository;
import com.lgh.huanlebian.repository.SystemConfigRepository;
import com.lgh.huanlebian.service.JdbcService;
import com.lgh.huanlebian.service.SpliderService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 启动设置
 * Created by lgh on 2016/5/5.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@Service
public class WebBootService implements ApplicationListener<ContextRefreshedEvent> {

    private static Log log = LogFactory.getLog(WebBootService.class);

    @Autowired
    private SystemConfigRepository systemConfigRepository;

    @Autowired
    private BaseService baseService;


    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    KindRepository kindRepository;

    @Autowired
    CategoryKindRepository categoryKindRepository;



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

                            initKind();

                            initCategoryKind();

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

        description = "欢乐变以备孕男女、孕产妇为主要服务对象，提供备孕、孕早中晚期等各个特定时间段的专业知识，内容涵盖孕前准备、孕妇的饮食、保健、疾病等方面。致力于用鲜活实用的知识，打造受众人群每日必读的孕育宝典。";
        parentCategory = new Category(0L, null, "怀孕", CatetoryConstant.huaiyun, "准备怀孕,备孕,生男孩,生女孩,起名字大全,避孕,流产,遗传,优生,孕前饮食,孕前检查,孕期保健", description, 1);
        parentCategory = categoryRepository.save(parentCategory);
        list.add(new Category(0L, parentCategory, "备孕", CatetoryConstant.beiyun, "遗传优生,生男生女,孕前准备,孕前饮食,不孕不育,人工受孕,怀孕测试", "孕前频道,未准妈妈的生育顾问!提供遗传优生、孕前准备等资讯，其中包括孕前饮食、孕前准备、怀孕禁忌、遗传优生、生男生女、不孕不育、怀孕测试等栏目,让你轻松“好孕”！", 1));
        list.add(new Category(0L, parentCategory, "孕早期", CatetoryConstant.yunzaoqi, "孕早期,孕早期注意事项,孕早期症状,怀孕初期症状,孕早期经历", description, 2));
        list.add(new Category(0L, parentCategory, "孕中期", CatetoryConstant.yunzhongqi, "孕中期,孕中期注意事项,孕中期症状,孕中期经历", description, 3));
        list.add(new Category(0L, parentCategory, "孕晚期", CatetoryConstant.yunwanqi, "孕晚期,孕晚期注意事项,孕晚期症状,怀孕晚期症状,孕晚期经历", description, 4));
        list.add(new Category(0L, parentCategory, "分娩", CatetoryConstant.fenmian, "分娩方式,生孩子,怎样分娩,分娩症状,产前征兆,陪伴分娩,产前心理,分娩时刻,待产物品", description, 5));
        categoryRepository.save(list);

        description = "欢乐变产后频道以产后妈妈为主要服务对象，致力于为产后妈妈提供全方位的产后恢复、身心保健知识。频道下设女性健康、产后减肥、产后美容等多个栏目，帮助妈妈平稳健康的度过产后恢复期，塑造美丽健康新形象。";
        parentCategory = new Category(0L, null, "产后", CatetoryConstant.chanhou, "产后恢复,产后减肥,产后避孕,产后调理,产后同房,产后检查,产后保健", description, 2);
        parentCategory = categoryRepository.save(parentCategory);
        list.add(new Category(0L, parentCategory, "月子", CatetoryConstant.yuezi, "孕晚期,孕晚期注意事项,孕晚期症状,怀孕晚期症状,孕晚期经历", description, 1));
        list.add(new Category(0L, parentCategory, "减肥", CatetoryConstant.jianfei, "产后减肥,产后瘦身，产后健身操，产后如何减肥", "产后如何瘦身，产后怎样减肥？产后健身操要怎么做？健康的减肥方法,帮助新妈妈快速、健康恢复美丽！", 2));
        list.add(new Category(0L, parentCategory, "避孕", CatetoryConstant.biyun, "产后如何避孕,产后避孕注意事项,产后性生活,产妇多久可以同房,产后避孕措施,安全套", "避孕流产栏目为女性提供避孕知识，让女性了解什么是流产以及流产后的注意事项，为女性健康服务！", 3));
        list.add(new Category(0L, parentCategory, "大姨妈", CatetoryConstant.dayima, "大姨妈,产后恢复攻略,产后恢复体操,产后恢复身材,产后恢复注意事项", "产后恢复怎么做？产后恢复攻略有哪些，产后恢复体操怎么做，产后恢复身材方法有哪些？产后恢复栏目告诉你产后恢复注意事项,帮助妈妈们做好防御！", 4));
        list.add(new Category(0L, parentCategory, "女性健康", CatetoryConstant.biaojian, "女性健康,产后女性生理健康,产后私处护理,产后身体疾病", "用专业的建议教您应对产后盗汗、体虚等健康问题，用有效的经验助您尽快恢复伤口，让您产后得到最好的恢复，尽享健康生活。", 5));
        categoryRepository.save(list);


        description = "欢乐变育儿频道致力于服务新手父母，并按宝贝的成长提供分阶段的婴幼儿养育知识。频道划分为新生儿、0-1岁、1-3岁、3-6岁四个栏目，涵盖婴幼儿喂养、护理、健康等各方面相关的育儿资讯，让您的育儿生活更加丰富。";
        parentCategory = new Category(0L, null, "育儿", CatetoryConstant.yuer, "儿童营养与健康,小儿喂养,小儿护理,小儿疾病,儿童营养食谱,幼儿健康,幼儿健康教育", description, 3);
        parentCategory = categoryRepository.save(parentCategory);
        list.add(new Category(0L, parentCategory, "新生儿", CatetoryConstant.xinshenger, "新生儿,新生儿黄疸,新生儿睡眠,新生儿护理,新生儿喂养,新生儿起名,新生儿抚触,母乳喂养", "欢乐变新生儿频道，提供全面的新生儿护理和新生儿喂养资讯。介绍内容包括新生儿护理、新生儿发育、新生儿喂养、新生儿疾病、早产儿、母婴交流等栏目。太平洋亲子网，让新手爸爸妈妈学会育儿！", 1));
        list.add(new Category(0L, parentCategory, "0-1岁", CatetoryConstant.yinger, "婴儿,婴儿腹泻,婴儿护理,婴儿湿疹,婴儿睡眠,婴儿辅食,母乳喂养,婴儿早期教育", "欢乐变婴儿频道，提供最新最全的婴儿护理、喂养常识，内容包括婴儿发育、婴儿营养、婴儿护理、婴儿疾病、婴儿教育等栏目。太平洋亲子网，让爸爸妈妈成为育儿高手！", 2));
        list.add(new Category(0L, parentCategory, "1-3岁", CatetoryConstant.youer, "幼儿,幼儿食谱,幼儿教育,幼儿保健,幼儿行为习惯,幼儿心理健康,入托", "欢乐变幼儿频道是太平洋亲子网的一个主要频道。它提供全方位的幼儿护理常识和幼儿教育知识和技巧。内容包括幼儿饮食、幼儿护理、幼儿发育、幼儿心理、幼儿疾病和幼儿教育等栏目。让爸爸妈妈成为育儿专家！", 3));
        list.add(new Category(0L, parentCategory, "3-6岁", CatetoryConstant.xuelingqian, "学龄前,学龄前儿童护理,儿童饮食,小儿常见病,儿童心理,儿童行为习惯,幼儿园,幼小衔接,学龄前教育", "欢乐变学龄前频道，全方位介绍了学龄前儿童护理、儿童饮食、小儿常见病、儿童心理、幼儿园、学龄前教育等知识和育儿技巧。让爸爸妈妈成为育儿专家！", 4));
        categoryRepository.save(list);

    }

    public void initKind() {
        List<Kind> list = new ArrayList<>();
        list.add(new Kind(0L, "准备", KindConstant.zhunbei));
        list.add(new Kind(0L, "生育", KindConstant.shengyu));
        list.add(new Kind(0L, "遗传", KindConstant.yichuan));
        list.add(new Kind(0L, "食谱", KindConstant.shipu));
        list.add(new Kind(0L, "喂养", KindConstant.weiyang));
        list.add(new Kind(0L, "护理", KindConstant.huli));
        list.add(new Kind(0L, "疾病", KindConstant.jibing));
        list.add(new Kind(0L, "禁忌", KindConstant.jinji));
        list.add(new Kind(0L, "方式", KindConstant.fangshi));
        list.add(new Kind(0L, "注意事项", KindConstant.zhuyishixiang));
        list.add(new Kind(0L, "药物", KindConstant.yaowu));
        list.add(new Kind(0L, "性生活", KindConstant.xingshenghuo));
        kindRepository.save(list);
    }


    public void initCategoryKind() {
        List<CategoryKind> list = new ArrayList<>();
        Category category = categoryRepository.findByPath(CatetoryConstant.beiyun);
        Kind kind = kindRepository.findByPath(KindConstant.zhunbei);
        list.add(new CategoryKind(category, kind, "孕前准备"));
        kind = kindRepository.findByPath(KindConstant.shengyu);
        list.add(new CategoryKind(category, kind, "生男生女"));
        kind = kindRepository.findByPath(KindConstant.yichuan);
        list.add(new CategoryKind(category, kind, "遗传优生"));
        categoryKindRepository.save(list);

        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.yunzaoqi);
        kind = kindRepository.findByPath(KindConstant.jinji);
        list.add(new CategoryKind(category, kind, "孕早期禁忌"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "孕早期食谱"));
        categoryKindRepository.save(list);


        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.yunzhongqi);
        kind = kindRepository.findByPath(KindConstant.jinji);
        list.add(new CategoryKind(category, kind, "孕中期禁忌"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "孕中期食谱"));
        categoryKindRepository.save(list);

        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.yunwanqi);
        kind = kindRepository.findByPath(KindConstant.jinji);
        list.add(new CategoryKind(category, kind, "孕晚期禁忌"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "孕晚期食谱"));
        categoryKindRepository.save(list);

        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.fenmian);
        kind = kindRepository.findByPath(KindConstant.zhunbei);
        list.add(new CategoryKind(category, kind, "产前准备"));
        kind = kindRepository.findByPath(KindConstant.fangshi);
        list.add(new CategoryKind(category, kind, "分娩方式"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "产妇饮食"));
        categoryKindRepository.save(list);

        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.yuezi);
        kind = kindRepository.findByPath(KindConstant.zhuyishixiang);
        list.add(new CategoryKind(category, kind, "月子注意事项"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "月子食谱"));
        categoryKindRepository.save(list);

        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.chanhou);
        kind = kindRepository.findByPath(KindConstant.zhunbei);
        list.add(new CategoryKind(category, kind, "剖腹产减肥"));
        kind = kindRepository.findByPath(KindConstant.fangshi);
        list.add(new CategoryKind(category, kind, "产后瘦肚子"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "产后瘦身食谱"));
        categoryKindRepository.save(list);

        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.biyun);
        kind = kindRepository.findByPath(KindConstant.fangshi);
        list.add(new CategoryKind(category, kind, "产后避孕法"));
        kind = kindRepository.findByPath(KindConstant.yaowu);
        list.add(new CategoryKind(category, kind, "产后避孕药"));
        kind = kindRepository.findByPath(KindConstant.xingshenghuo);
        list.add(new CategoryKind(category, kind, "产后性生活"));
        categoryKindRepository.save(list);


        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.dayima);
        kind = kindRepository.findByPath(KindConstant.huli);
        list.add(new CategoryKind(category, kind, "产后生理期"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "产后食谱"));
        categoryKindRepository.save(list);


        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.biaojian);
        kind = kindRepository.findByPath(KindConstant.jibing);
        list.add(new CategoryKind(category, kind, "产后疾病"));
        kind = kindRepository.findByPath(KindConstant.jinji);
        list.add(new CategoryKind(category, kind, "产后禁忌"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "产后食谱"));
        categoryKindRepository.save(list);

        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.xinshenger);
        kind = kindRepository.findByPath(KindConstant.weiyang);
        list.add(new CategoryKind(category, kind, "新生儿喂养"));
        kind = kindRepository.findByPath(KindConstant.huli);
        list.add(new CategoryKind(category, kind, "新生儿护理"));
        kind = kindRepository.findByPath(KindConstant.jibing);
        list.add(new CategoryKind(category, kind, "新生儿疾病"));
        categoryKindRepository.save(list);

        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.yinger);
        kind = kindRepository.findByPath(KindConstant.huli);
        list.add(new CategoryKind(category, kind, "0-1护理"));
        kind = kindRepository.findByPath(KindConstant.jibing);
        list.add(new CategoryKind(category, kind, "0-1疾病"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "0-1饮食"));
        categoryKindRepository.save(list);


        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.youer);
        kind = kindRepository.findByPath(KindConstant.huli);
        list.add(new CategoryKind(category, kind, "1-3护理"));
        kind = kindRepository.findByPath(KindConstant.jibing);
        list.add(new CategoryKind(category, kind, "1-3疾病"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "1-3饮食"));
        categoryKindRepository.save(list);


        list = new ArrayList<>();
        category = categoryRepository.findByPath(CatetoryConstant.xuelingqian);
        kind = kindRepository.findByPath(KindConstant.huli);
        list.add(new CategoryKind(category, kind, "3-6护理"));
        kind = kindRepository.findByPath(KindConstant.jibing);
        list.add(new CategoryKind(category, kind, "3-6疾病"));
        kind = kindRepository.findByPath(KindConstant.shipu);
        list.add(new CategoryKind(category, kind, "3-6饮食"));
        categoryKindRepository.save(list);
    }



}

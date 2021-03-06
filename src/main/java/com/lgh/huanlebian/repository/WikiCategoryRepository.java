package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.WikiCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lgh on 2016/7/6.
 */
@Repository
public interface WikiCategoryRepository extends JpaRepository<WikiCategory, Long> {

    List<WikiCategory> findAllByParentOrderBySort(WikiCategory parent);
}

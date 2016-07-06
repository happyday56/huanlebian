package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.BaikeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2016/7/6.
 */
@Repository
public interface BaikeCategoryRepository extends JpaRepository<BaikeCategory,Long> {
}

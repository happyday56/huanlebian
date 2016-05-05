package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by lgh on 2016/5/5.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
        
}

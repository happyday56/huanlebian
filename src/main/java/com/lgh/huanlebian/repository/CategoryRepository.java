package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lgh on 2016/5/5.
 */
@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    @Query("select c.id from Category c where c.parent=?1")
    List<Long> findIdsByParent(Category parent);

    List<Category> findAllByParent(Category parent);

    Category findByPath(String path);

}

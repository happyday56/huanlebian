package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.CategoryKind;
import com.lgh.huanlebian.entity.Kind;
import com.lgh.huanlebian.entity.pk.CategoryKindPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lgh on 2016/5/27.
 */
@Repository
public interface CategoryKindRepository extends JpaRepository<CategoryKind, CategoryKindPK> {

    List<CategoryKind> findAllByCategory(Category category);

    CategoryKind findByCategoryAndKind(Category category, Kind kind);
}

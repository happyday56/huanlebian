package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.Kind;
import com.lgh.huanlebian.entity.News;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Created by lgh on 2016/5/27.
 */
@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    Page<News> findAllByCategoryAndKind(Category category, Kind kind,Pageable pageable);
}

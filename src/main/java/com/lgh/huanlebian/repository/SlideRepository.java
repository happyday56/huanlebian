package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.Category;
import com.lgh.huanlebian.entity.Slide;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by lgh on 2016/5/16.
 */
@Repository
public interface SlideRepository extends JpaRepository<Slide,Long> {

//    List<Slide> findAllByCategory(Category category);
}

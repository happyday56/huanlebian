package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.NewsFilter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/6/4.
 */
@Repository
public interface NewsFilterRepository extends JpaRepository<NewsFilter,String> {
}

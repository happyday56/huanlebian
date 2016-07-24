package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.SpiderUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/7/24.
 */
@Repository
public interface SpiderUrlRepository extends JpaRepository<SpiderUrl,String> {

}

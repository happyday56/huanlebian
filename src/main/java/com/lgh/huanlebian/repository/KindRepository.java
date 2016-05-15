package com.lgh.huanlebian.repository;

import com.lgh.huanlebian.entity.Kind;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2016/5/15.
 */

@Repository
public interface KindRepository extends JpaRepository<Kind, Long> {
}

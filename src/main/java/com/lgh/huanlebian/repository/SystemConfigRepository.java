package com.lgh.huanlebian.repository;


import com.lgh.huanlebian.entity.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author xhk
 */
@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, String> {

}

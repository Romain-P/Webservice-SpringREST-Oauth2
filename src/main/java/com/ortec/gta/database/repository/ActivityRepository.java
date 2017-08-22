package com.ortec.gta.database.repository;

import com.ortec.gta.database.model.entity.Activity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 10/08/2017
 */
@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {
    Set<Activity> findByActiveTrue();
    Set<Activity> findByParentActivityIsNull();
}

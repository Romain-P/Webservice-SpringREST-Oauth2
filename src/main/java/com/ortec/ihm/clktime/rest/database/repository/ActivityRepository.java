package com.ortec.ihm.clktime.rest.database.repository;

import com.ortec.ihm.clktime.rest.database.model.entity.Activity;
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
}

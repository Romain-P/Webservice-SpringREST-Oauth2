package com.ortec.ihm.clktime.rest.database.repository;

import com.ortec.ihm.clktime.rest.database.model.entity.Activity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: romain.pillot
 * @Date: 10/08/2017
 */
@Repository
public interface ActivityRepository extends CrudRepository<Activity, Integer> {}

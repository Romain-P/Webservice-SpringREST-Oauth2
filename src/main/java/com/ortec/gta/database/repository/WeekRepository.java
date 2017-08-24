package com.ortec.gta.database.repository;

import com.ortec.gta.database.model.entity.Week;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */
public interface WeekRepository extends CrudRepository<Week, Integer> {
    @Query("select w from Week w where w.user.id = ?1")
    Set<Week> findByUserId(int id);
}

package com.ortec.gta.database.repository;

import com.ortec.gta.database.entity.Week;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

/**
 * @Author: romain.pillot
 * @Date: 24/08/2017
 */
public interface WeekRepository extends CrudRepository<Week, Long> {
    @Query("select w from Week w where w.user.id = ?1 and w.weekNumber = ?2 and w.year = ?3")
    Set<Week> findByUserId(long id, int weekNumber, int year);
}

package com.ortec.ihm.clktime.rest.repositories;

import com.ortec.ihm.clktime.rest.model.entities.Test;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: romain.pillot
 * @Date: 28/07/2017
 */
@Repository
public interface TestRepository extends JpaRepository<Test, Integer> {
    Test findByNumber(int number);
}

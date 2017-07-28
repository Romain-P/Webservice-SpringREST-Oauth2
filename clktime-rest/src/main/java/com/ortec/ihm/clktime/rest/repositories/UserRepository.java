package com.ortec.ihm.clktime.rest.repositories;

import com.ortec.ihm.clktime.rest.model.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: romain.pillot
 * @Date: 28/07/2017
 */
@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findByUsername(String username);
    User findByUsernameAndPassword(String username, String password);
}

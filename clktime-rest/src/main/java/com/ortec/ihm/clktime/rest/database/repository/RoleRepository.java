package com.ortec.ihm.clktime.rest.database.repository;

import com.ortec.ihm.clktime.rest.database.model.entity.Role;
import com.ortec.ihm.clktime.rest.database.model.entity.User;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 28/07/2017
 */

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {}

package com.ortec.gta.database.repository;

import com.ortec.gta.database.entity.Role;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author: romain.pillot
 * @Date: 28/07/2017
 */

@Repository
public interface RoleRepository extends CrudRepository<Role, Long> {}

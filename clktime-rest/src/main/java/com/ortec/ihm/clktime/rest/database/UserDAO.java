package com.ortec.ihm.clktime.rest.database;

import com.ortec.ihm.clktime.rest.database.model.dto.UserDTO;
import com.ortec.ihm.clktime.rest.database.model.entity.User;
import org.springframework.stereotype.Repository;

/**
 * @Author: romain.pillot
 * @Date: 02/08/2017
 */

@Repository
public class UserDAO extends AbstractDTOConverterDAO<User, UserDTO> {}
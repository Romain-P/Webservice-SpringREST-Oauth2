package com.ortec.ihm.clktime.rest.database.model.dto;

import com.ortec.ihm.clktime.rest.database.model.entity.User;
import com.sun.istack.internal.NotNull;
import fr.ortec.dsi.domaine.Utilisateur;
import lombok.*;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
import java.util.*;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */
@Accessors(chain = true)
@Getter
public final class UserDTO {
    private final Optional<Utilisateur> ldap;
    private final User model;

    /**
     * Have to be called for an known or unknown ldapUser, that have
     * a custom user model (previously loaded, in case of temporary employee
     * such as an interim, trainee ..)
     *
     * @param ldapUser a nullable active directory user (ldap)
     * @param user an existing user model (that is previously loaded)
     * @return a UserDTO from a ldapUser and user model.
     */
    public static UserDTO of(@Nullable Utilisateur ldapUser, @NotNull User user) {
        return new UserDTO(ldapUser, user);
    }

    /**
     * Have to be called for a first connection of a ldap user.
     *
     * @param ldapUser an active directory user (ldap).
     * @return a UserDTO from a ldap user.
     */
    public static UserDTO of(@NotNull Utilisateur ldapUser) {
        User user = new User();
        user.setUsername(ldapUser.getUsername());
        return new UserDTO(ldapUser, user);
    }

    private UserDTO(Utilisateur ldapUser, User model)
    {
        this.ldap = Optional.ofNullable(ldapUser);
        this.model = model;
    }
}

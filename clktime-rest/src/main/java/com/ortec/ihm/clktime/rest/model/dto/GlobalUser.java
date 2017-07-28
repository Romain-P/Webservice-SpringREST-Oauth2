package com.ortec.ihm.clktime.rest.model.dto;

import com.ortec.ihm.clktime.rest.model.entities.User;
import com.ortec.ihm.clktime.rest.repositories.UserRepository;
import com.ortec.ihm.clktime.rest.service.authentication.Utilisateur;
import com.sun.istack.internal.NotNull;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */
@Accessors(chain = true)
@Getter
public final class GlobalUser {
    private final Optional<Utilisateur> ldap;
    private final User model;

    public static Optional<GlobalUser> of(@Nullable Utilisateur ldapUser, @Nullable User user) {
        if (user == null)
            return Optional.empty();
        return Optional.of(new GlobalUser(ldapUser, user));
    }

    public static GlobalUser of(@NotNull Utilisateur ldapUser) {
        User user = new User();
        user.setUsername(ldapUser.getPrenom());
        return new GlobalUser(ldapUser, user);
    }

    private GlobalUser(Utilisateur ldapUser, User model)
    {
        this.ldap = Optional.ofNullable(ldapUser);
        this.model = model;
    }
}

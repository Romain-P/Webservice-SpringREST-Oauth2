package com.ortec.ihm.clktime.rest.model.dto;

import com.ortec.ihm.clktime.rest.model.entities.User;
import fr.ortec.dsi.domaine.Utilisateur;
import lombok.*;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */
@Accessors(chain = true)
@Getter @Setter
public final class GlobalUser extends User {
    private final Optional<Utilisateur> ldap;

    public static GlobalUser of(@Nullable Utilisateur ldapUser, @Nullable  User user) {
        GlobalUser tokened = new GlobalUser(ldapUser);
        tokened.setId(user.getId());
        tokened.setUsername(user.getUsername());
        return (tokened);
    }

    private GlobalUser(Utilisateur ldapUser)
    {
        this.ldap = Optional.ofNullable(ldapUser);
    }
}

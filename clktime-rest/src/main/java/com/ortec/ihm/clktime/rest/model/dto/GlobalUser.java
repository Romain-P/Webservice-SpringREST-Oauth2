package com.ortec.ihm.clktime.rest.model.dto;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.ortec.ihm.clktime.rest.model.entity.User;
import com.sun.istack.internal.NotNull;
import fr.ortec.dsi.domaine.Utilisateur;
import lombok.*;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */
@Accessors(chain = true)
@Getter
public final class GlobalUser {
    private final Optional<Utilisateur> ldap;
    private final User model;

    /**
     * Have to be called for an known or unknown ldapUser, that have
     * a custom user model (previously loaded, in case of temporary employee
     * such as an interim, trainee ..)
     *
     * @param ldapUser a nullable active directory user (ldap)
     * @param user an existing user model (that is previously loaded)
     * @return a GlobalUser from a ldapUser and user model.
     */
    public static GlobalUser of(@Nullable Utilisateur ldapUser, @NotNull User user) {
        return new GlobalUser(ldapUser, user);
    }

    /**
     * Have to be called for a first connection of a ldap user.
     *
     * @param ldapUser an active directory user (ldap)
     * @return a GlobalUser from a ldap user.
     */
    public static GlobalUser of(@NotNull Utilisateur ldapUser) {
        User user = new User();
        user.setUsername(ldapUser.getUsername());
        return new GlobalUser(ldapUser, user);
    }

    private GlobalUser(Utilisateur ldapUser, User model)
    {
        this.ldap = Optional.ofNullable(ldapUser);
        this.model = model;
    }

    /**
     * Do not call for grant or remote roles.
     * Use model.getRoles() instead, or directly call methods from the UserRoleService
     *
     * @return the model list transformed to an immutable set of GrantedAuthority.
     *         This method might be be called to create a new Authentication.
     */
    public Set<GrantedAuthority> getRoles() {
        return model.getRoles().stream()
                .map(x -> new SimpleGrantedAuthority(x.getName()))
                .collect(ImmutableSet.toImmutableSet());
    }
}

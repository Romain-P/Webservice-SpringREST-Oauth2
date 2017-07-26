package com.ortec.ihm.clktime.rest.model.dto;

import com.google.common.collect.Lists;
import fr.ortec.dsi.domaine.Utilisateur;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */
@Accessors(chain = true)
@Getter
@Setter
@ToString
public class User extends Utilisateur {
    private final Collection<? extends GrantedAuthority> authorities;
    @Setter private String password;

    public User() {
        this.authorities = Lists.newArrayList();
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }
}

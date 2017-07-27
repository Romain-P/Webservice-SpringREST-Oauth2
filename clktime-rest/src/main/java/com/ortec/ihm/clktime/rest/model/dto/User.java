package com.ortec.ihm.clktime.rest.model.dto;

import fr.ortec.dsi.domaine.Utilisateur;
import lombok.*;
import lombok.experimental.Accessors;

import javax.annotation.Nullable;

/**
 * @Author: romain.pillot
 * @Date: 24/07/2017
 */
@Accessors(chain = true)
@Getter @Setter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class User {
    private final String username;
    private final String forname;
    private final String lastname;
    private final String email;
    private final String title;
    private final String location;
    private final String entity;
    private final byte[] avatar;

    public static User create(@Nullable  Utilisateur dist) {
        return dist == null ? null
        : new User(
                dist.getUsername(),
                dist.getNom(),
                dist.getPrenom(),
                dist.getEmail(),
                dist.getTitre(),
                dist.getDepartement(),
                dist.getEntite(),
                dist.getAvatar()
        );
    }
}

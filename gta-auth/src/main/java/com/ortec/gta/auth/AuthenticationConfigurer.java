package com.ortec.gta.auth;

import com.ortec.gta.domain.UserIdentity;
import fr.ortec.dsi.domaine.Utilisateur;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * @Author: romain.pillot
 * @Date: 26/09/2017
 */

public class AuthenticationConfigurer<U extends UserIdentity> {
    Function<String, Optional<U>> findByUsername;
    Function<Utilisateur, U> firstConnection;
    BiFunction<String, String, Optional<U>> invalid;
    boolean rsaPassword;

    /**
     * Initializing callbacks to avoid null pointer exceptions
     */
    public AuthenticationConfigurer() {
        this.findByUsername = (username) -> Optional.empty();
        this.firstConnection = (user) -> null;
        this.invalid = (user, pass) -> Optional.empty();
    }

    /**
     * Call this method to retrieve user details of a given Username.
     * Example: configurer.loadUserByUsername( username -> userRepository.findByUsername(username) );
     *
     * You must return an Optional of `U` fill or null.
     */
    public AuthenticationConfigurer<U> loadUserByUsername(Function<String, Optional<U>> findByUsername) {
        this.findByUsername = findByUsername;
        return this;
    }

    /**
     * Call this method to create and persist your User entity from the ldap user details.
     *
     * You must return your new entity with the attached id.
     */
    public AuthenticationConfigurer<U> createUserEntity(Function<Utilisateur, U> firstConnection) {
        this.firstConnection = firstConnection;
        return this;
    }

    /**
     * Call this method to do another operation when the authentication via ldap failed.
     * You can try to search an user in your custom database.
     *
     * Example: configurer.rescueInvalidAuthentication( (username, password) -> userRepository.findBy(username, password) );
     * You must return an Optional of `U` fill or null.
     */
    public AuthenticationConfigurer<U> rescueInvalidAuthentication(BiFunction<String, String, Optional<U>> invalid) {
        this.invalid = invalid;
        return this;
    }

    public AuthenticationConfigurer<U> enableRsaPassword() {
        this.rsaPassword = true;
        return this;
    }
}
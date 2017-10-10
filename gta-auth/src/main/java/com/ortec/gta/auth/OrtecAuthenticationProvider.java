package com.ortec.gta.auth;

import com.lambdista.util.Try;
import com.ortec.gta.domain.UserIdentity;
import fr.ortec.dsi.domaine.Utilisateur;
import fr.ortec.dsi.securite.authentification.activedirectory.ADAuthentification;
import fr.ortec.dsi.securite.authentification.services.Authentification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.annotation.PostConstruct;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @Author: romain.pillot
 * @Date: 26/09/2017
 */
public abstract class OrtecAuthenticationProvider<U extends UserIdentity> implements AuthenticationProvider {
    private AuthenticationConfigurer<U> configurer;
    private Authentification authentication;

    @Autowired
    private RsaHandler rsaHandler;

    @Value("${active-directory.url}") String remoteAddress;
    @Value("${active-directory.domain}") String baseDomain;
    @Value("${active-directory.authentication-type}") String authenticationType;

    @PostConstruct
    public void initialize() {
        this.authentication = new ADAuthentification(remoteAddress, baseDomain, authenticationType);
        this.configurer = new AuthenticationConfigurer<>();

        configure(configurer);
    }

    /**
     * Perform a connection by username and password.
     *
     * @param authentication an user login process
     * @return a authentication token by username and password
     * @throws AuthenticationException in case of invalid credentials
     */
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        if (configurer.rsaPassword) {
            final String fixSpaces = password.replace(" ", "+");

            password = Try.apply(() -> rsaHandler.decrypt(fixSpaces)).toOptional()
                    .orElseThrow(() -> new RuntimeException("Problem while decrypting password, check keys format?"));
        }

        UserIdentity user = loadByConnection(name, password)
                .orElseThrow(() -> new BadCredentialsException(String.format("Bad user/pass for %s", name)));

        Set<GrantedAuthority> roles = user.getRoles().stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toSet());

        return new UsernamePasswordAuthenticationToken(user, null, roles);
    }

    private Optional<U> loadByConnection(String username, String password) {
        Optional<Utilisateur> ldapUser = Try.apply(() -> authentication.getUtilisateur(username, password)).toOptional();

        return Optional.ofNullable(
                ldapUser.map(ldap -> configurer.findByUsername.apply(username)
                        .orElseGet(() -> configurer.firstConnection.apply(ldap)))
                        .orElseGet(() -> configurer.invalid.apply(username, password).orElse(null)));
    }

    protected abstract void configure(AuthenticationConfigurer<U> configurer);

    /**
     * @param authentication an authentication template looking for its implementation
     * @return true if the expected authentication is an UsernamePasswordAuthenticationToken.
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
    }
}

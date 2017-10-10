package com.ortec.gta.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Collections;
import java.util.List;

/**
 * @Author: romain.pillot
 * @Date: 27/09/2017
 */
public abstract class AuthenticationConfiguration {

    @Configuration
    @EnableAuthorizationServer
    @PropertySource("classpath:oauth.properties")
    public static class OAuthConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        TokenStore tokenStore;

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Value("${oauth.token-request.url}")
        private String tokenUrl;

        /**
         * Bind our authentication provider to oauth,
         * with a in-memory token store.
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints
                    .pathMapping("/oauth/token", tokenUrl)
                    .tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager);
        }

        @Value("${oauth.secret-id}")
        private String clientId;

        @Value("${oauth.secret-key}")
        private String clientKey;

        /**
         * Define all applications that will access to this one.
         * All applications accessing to this rest app is considered as a client.
         * One client can be created at this moment TODO: multiple clients
         * Config your secret credentials in resources/application.properties
         */
        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients
                    .inMemory()
                    .withClient(clientId)
                    .authorizedGrantTypes("password", "refresh_token")
                    .authorities("USER")
                    .scopes("read", "write")
                    .secret(clientKey);
        }

        /**
         * Setups the token store and enables refresh tokens.
         * (Refresh tokens can be requested e.g after an Inactivity,
         * See online, oauth2 IdleTime configuration)
         */
        @Bean
        @Primary
        public DefaultTokenServices tokenServices() {
            DefaultTokenServices tokenServices = new DefaultTokenServices();
            tokenServices.setSupportRefreshToken(true);
            tokenServices.setTokenStore(this.tokenStore);
            return tokenServices;
        }

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }
    }

    @Configuration
    @EnableWebSecurity
    @EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
    public static class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Autowired @Lazy
        private OrtecAuthenticationProvider authenticationProvider;

        /**
         * @return a custom AuthenticationProvider implementation, required to
         * perform a connection by username and password.
         * The default implementation of spring (UserDetails) provides an username only.
         */
        @Override
        @Bean
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return new ProviderManager(Collections.singletonList(authenticationProvider));
        }

        @Bean
        public RsaHandler rsaHandler(@Value("${crypto.rsa.public-key}") String publicKey,
                                     @Value("${crypto.rsa.private-key}") String privateKey)
        {
            return new RsaHandler(publicKey, privateKey);
        }
    }

    @Configuration
    @EnableResourceServer
    public static class AccessConfiguration extends ResourceServerConfigurerAdapter {

        @Value("${oauth.requests.protected}")
        private String[] protectedRequests;

        /**
         * Define allowed and forbidden requests
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            ExpressionUrlAuthorizationConfigurer<HttpSecurity>
                    .ExpressionInterceptUrlRegistry register = http.authorizeRequests();

            if (protectedRequests != null && protectedRequests.length > 0)
                register.antMatchers(protectedRequests).authenticated();
            else
                register.anyRequest().permitAll();
        }
    }

    @Configuration
    public static class ArgumentResolverConfiguration extends WebMvcConfigurerAdapter {
        @Override
        public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
            argumentResolvers.add(new SessionUserResolver());
        }
    }
}

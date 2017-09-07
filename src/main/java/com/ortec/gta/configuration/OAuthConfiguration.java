package com.ortec.gta.configuration;

import com.ortec.gta.common.user.TokenedUser;
import com.ortec.gta.service.UserRoleService;
import com.ortec.gta.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Configuration
public class OAuthConfiguration {

    @Configuration
    @EnableResourceServer
    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

        /**
         * Define authorized requests for all users.
         */
        @Override
        public void configure(HttpSecurity http) throws Exception {
            http.authorizeRequests().antMatchers("/").permitAll().antMatchers("/test").authenticated();
        }
    }

    @Configuration
    @EnableAuthorizationServer
    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {

        @Autowired
        TokenStore tokenStore;

        @Bean
        public TokenStore tokenStore() {
            return new InMemoryTokenStore();
        }

        @Autowired
        @Qualifier("authenticationManagerBean")
        private AuthenticationManager authenticationManager;

        @Value("${authentication.token-request.url}")
        private String tokenUrl;

        /**
         * Bind our authentication provider to oauth,
         * with a in-memory token store.
         */
        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            endpoints.addInterceptor(new HandlerInterceptorAdapter() {
                @Override
                public boolean preHandle(HttpServletRequest hsr, HttpServletResponse rs, Object o) throws Exception {
                    rs.setHeader("Access-Control-Allow-Origin", "*");
                    rs.setHeader("Access-Control-Allow-Methods", "GET, PUT, POST,OPTIONS");
                    rs.setHeader("Access-Control-Max-Age", "3600");
                    rs.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept, Authorization");
                    return true;
                }
            });

            endpoints
                    .pathMapping("/oauth/token", tokenUrl)
                    .tokenStore(this.tokenStore)
                    .authenticationManager(this.authenticationManager);
        }

        @Value("${authentication.secret-id}")
        private String clientId;

        @Value("${authentication.secret-key}")
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
    }
}

package org.career.my5g.configuration;

import org.career.my5g.configuration.keycloak.KeycloakAuthenticationSuccessHandlerImpl;
import org.career.my5g.configuration.keycloak.KeycloakLogoutHandler;
import org.career.my5g.configuration.keycloak.KeycloakOAuth2UserService;
import org.career.my5g.service.LoginService;
import org.keycloak.adapters.springsecurity.authentication.KeycloakAuthenticationSuccessHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.authority.mapping.SimpleAuthorityMapper;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.session.RegisterSessionAuthenticationStrategy;

@Configuration
public class SecurityConfiguration {
    private AuthenticationSuccessHandler authenticationSuccessHandler;
    private LogoutHandler logoutHandler;

    @Value("${spring.security.oauth2.resourceserver.jwt.issuer-uri}")
    private String issuerUri;

    public SecurityConfiguration(LoginService loginService) {
        this.authenticationSuccessHandler = new KeycloakAuthenticationSuccessHandler(
            new KeycloakAuthenticationSuccessHandlerImpl(loginService));
        this.logoutHandler = new KeycloakLogoutHandler(loginService);
    }

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
            .authorizeHttpRequests(requests -> {
                requests.antMatchers(
                        "/static/**",
                        "/signup/**"
                        )
                        .permitAll()
                    .anyRequest().authenticated();
            })
            .oauth2Login(login -> {
                login.loginPage(issuerUri)
                    .successHandler(authenticationSuccessHandler);
            })
            .logout(logout -> {
                logout.addLogoutHandler(logoutHandler)
                    .invalidateHttpSession(true)
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/");
            })
            .oauth2ResourceServer(oauth2 -> {
                oauth2.jwt();
            })
            .sessionManagement(session -> {
                session.sessionAuthenticationStrategy(
                        new RegisterSessionAuthenticationStrategy(new SessionRegistryImpl())
                    )
                    .sessionAuthenticationErrorUrl("/");
            })
            .csrf(csrf -> {
                csrf.disable();
            })
            .build();
    }

    @Bean
    KeycloakOAuth2UserService keycloakOAuth2UserService(OAuth2ClientProperties oauth2ClientProperties) {
        JwtDecoder jwtDecoder = NimbusJwtDecoder.withJwkSetUri(
                oauth2ClientProperties.getProvider().get("keycloak").getJwkSetUri())
            .build();

        SimpleAuthorityMapper authoritiesMapper = new SimpleAuthorityMapper();
        authoritiesMapper.setConvertToUpperCase(true);

        return new KeycloakOAuth2UserService(jwtDecoder, authoritiesMapper);
    }

}

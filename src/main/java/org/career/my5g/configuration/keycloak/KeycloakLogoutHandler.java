package org.career.my5g.configuration.keycloak;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.career.my5g.service.LoginService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;


public class KeycloakLogoutHandler implements LogoutHandler {
    private static final Logger logger = LoggerFactory.getLogger(KeycloakLogoutHandler.class);
    private final RestTemplate restTemplate;
    private LoginService loginService;

    public KeycloakLogoutHandler(LoginService loginService) {
        this.restTemplate = new RestTemplate();
        this.loginService = loginService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, 
            Authentication auth) {
        if (auth == null) {
            return;
        }
        logoutFromKeycloak((OidcUser) auth.getPrincipal());
        try {
            WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
            loginService.saveLogout(details.getSessionId());
        } catch (Exception e) {
            logger.error("{}", e.getMessage());
        }
    }

    private void logoutFromKeycloak(OidcUser user) {
        String endSessionEndpoint = user.getIssuer() + "/protocol/openid-connect/logout";
        UriComponentsBuilder builder = UriComponentsBuilder
            .fromUriString(endSessionEndpoint)
            .queryParam("id_token_hint", user.getIdToken().getTokenValue());

        ResponseEntity<String> logoutResponse = restTemplate.getForEntity(
        builder.toUriString(), String.class);
        if (logoutResponse.getStatusCode().is2xxSuccessful()) {
            logger.info("Successfulley logged out from Keycloak");
        } else {
            logger.error("Could not propagate logout to Keycloak");
        }
    }

}

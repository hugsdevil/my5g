package org.career.my5g.service;

import java.security.Principal;
import java.util.NoSuchElementException;

import org.career.my5g.domain.entity.UserDetail;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    private UserService userService;

    public LoginService(UserService userService) {
        this.userService = userService;
    }

    public UserDetail findByPrincipal(Principal principal) {
        if (principal instanceof OAuth2AuthenticationToken) {
            OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;
            UserDetail userDetail = userService.viewByAccount(token.getName())
                .orElseThrow(() -> new NoSuchElementException(String.format("no such a user name: %s", token.getName())));
            userDetail.withSession(token);
            return userDetail;
        } else {
            throw new ClassCastException("principal is non oauth2 token");
        }
    }

    public void saveLogin(Authentication authentication) {
    }

    public void saveLogout(String sessionId) {
    }
    
}

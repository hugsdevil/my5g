package org.career.my5g.configuration.keycloak;

import org.career.my5g.service.LoginService;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;

public class KeycloakAuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private LoginService loginService;

    public KeycloakAuthenticationSuccessHandlerImpl(LoginService loginService) {
        this.loginService = loginService;
    }

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        HttpSession session = request.getSession(true);
        try {
            session.setAttribute("USER_DETAIL", loginService.findByPrincipal(authentication));
            loginService.saveLogin(authentication);
            response.sendRedirect("/");
        } catch (Exception e) {
            response.sendRedirect("/signup");
        }
    }
}

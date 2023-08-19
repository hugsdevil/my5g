package org.career.my5g.configuration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfiguration implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String []excludePathPatterns = {
            "/static/**", 
            "/signup/**",
        };
        registry.addInterceptor(new RemoteAddressInterceptor()).excludePathPatterns(excludePathPatterns);
    }

    public static class RemoteAddressInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {
            HttpSession session = request.getSession(true);
            session.setAttribute("REMOTE_ADDERSS", extractRemoteAddress(request));

            return true;
        }

        private String extractRemoteAddress(HttpServletRequest request) {
            String[] headerNames = {
                "X-Forwarded-For", 
                "Proxy-Client-IP", 
                "WL-Proxy-Client-IP", 
                "HTTP_CLIENT_IP", 
                "HTTP_X_FORWARDED_FOR",
            };
            for (String headerName : headerNames) {
                String remoteAddress = request.getHeader(headerName);
                if (StringUtils.hasText(remoteAddress)) {
                    return remoteAddress;
                }
            }
            return request.getRemoteAddr();
        }
    }
}

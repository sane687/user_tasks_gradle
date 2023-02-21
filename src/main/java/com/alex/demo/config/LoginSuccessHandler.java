package com.alex.demo.config;

import com.alex.demo.service.MyUserDetails;
import jakarta.servlet.http.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.util.Set;

@Configuration
public class LoginSuccessHandler implements AuthenticationSuccessHandler {
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Set<String> roleSet = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();

            if (roleSet.contains("ADMIN")) {
                response.sendRedirect("/admin");
            } else if (roleSet.contains("MODERATOR")) {
                response.sendRedirect("/moderator/" + userDetails.getId());
            } else if (roleSet.contains("USER")) {
                response.sendRedirect("/" + userDetails.getId());
            }
        }

    }

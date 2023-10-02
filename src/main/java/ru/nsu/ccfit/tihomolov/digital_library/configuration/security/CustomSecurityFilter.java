package ru.nsu.ccfit.tihomolov.digital_library.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Setter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Map;

@Setter
public class CustomSecurityFilter extends OncePerRequestFilter {
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();
    private final ObjectMapper objectMapper = new ObjectMapper();

    private final AuthenticationSuccessHandler successHandler =
            new SimpleUrlAuthenticationSuccessHandler("/auth/success");
    private final AuthenticationFailureHandler failureHandler =
            new SimpleUrlAuthenticationFailureHandler("/auth/failed");

    private RequestMatcher processAuthenticationRequestMatcher;
    private AuthenticationManager authenticationManager;
    private SecurityContextRepository securityContextRepository;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (!this.processAuthenticationRequestMatcher.matches(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            Authentication authentication = obtainBody(request);
            authentication = this.authenticationManager.authenticate(authentication);
            successfulAuthentication(request, response, authentication);
        } catch (AuthenticationException exception) {
            unSuccessfulAuthentication(request, response, exception);
        }

    }

    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws ServletException, IOException {
        SecurityContext context = this.securityContextHolderStrategy.createEmptyContext();
        context.setAuthentication(authentication);
        this.securityContextHolderStrategy.setContext(context);
        this.securityContextRepository.saveContext(context, request, response);
        this.successHandler.onAuthenticationSuccess(request, response, authentication);
    }

    protected void unSuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException exception) throws ServletException, IOException {
        SecurityContext context = this.securityContextHolderStrategy.getContext();
        this.securityContextHolderStrategy.clearContext();
        context.setAuthentication(null);
        this.failureHandler.onAuthenticationFailure(request, response, exception);
    }

    protected Authentication obtainBody(HttpServletRequest request) {
        try {
            Map<String, String> map = this.objectMapper.readValue(request.getInputStream(), Map.class);
            String username = map.get("username");
            String password = map.get("password");

            if (!StringUtils.hasLength(username) || !StringUtils.hasLength(password)) {
                throw new BadCredentialsException("Username or password is/are empty");
            }
            return UsernamePasswordAuthenticationToken.unauthenticated(username, password);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

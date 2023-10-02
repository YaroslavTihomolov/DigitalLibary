package ru.nsu.ccfit.tihomolov.digital_library.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import ru.nsu.ccfit.tihomolov.digital_library.configuration.security.CustomConfigurer;

@Configuration
public class SecurityCustomConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(registry -> registry
                .requestMatchers("/digital_library/admin/**").hasRole("ADMIN")
                .requestMatchers("/digital_library/manager/**").hasRole("ADMIN")
                .requestMatchers("/digital_library/**").permitAll());
        http.cors(configurer -> configurer.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()));
        http.csrf(AbstractHttpConfigurer::disable);
        http.apply(new CustomConfigurer());
        return http.build();
    }
}

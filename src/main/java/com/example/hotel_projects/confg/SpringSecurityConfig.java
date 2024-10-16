package com.example.hotel_projects.confg;



import com.example.hotel_projects.utl.MDUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableMethodSecurity
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
    public class SpringSecurityConfig {
    public static final String [] AUTH_WHITELIST={
            "/v2/api-docs" +
                    "/v3/$%7Bserver.url",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/swagger-resources",
            "/swagger-resources/**",
            "/auth/**"


    };
        @Autowired
        private JwtTokenFilter jwtTokenFilter;
        @Autowired
        private UserDetailsService userDetailsService;

        @Bean
        public AuthenticationProvider authenticationProvider() {
            final DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setUserDetailsService(userDetailsService);
            authenticationProvider.setPasswordEncoder(passwordEncoder());
            return authenticationProvider;
        }

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http.authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                authorizationManagerRequestMatcherRegistry
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/room/pagination").permitAll()
                        .anyRequest()
                        .authenticated();
            });
            http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
            http.csrf(AbstractHttpConfigurer::disable);
            http.cors(AbstractHttpConfigurer::disable);


            return http.build();
        }

        public PasswordEncoder passwordEncoder() {
            return new PasswordEncoder() {
                @Override
                public String encode(CharSequence rawPassword) {
                    return rawPassword.toString();
                }

                @Override
                public boolean matches(CharSequence rawPassword, String encodedPassword) {
                    return MDUtil.encode(rawPassword.toString()).equals(encodedPassword);
                }
            };
        }
    }



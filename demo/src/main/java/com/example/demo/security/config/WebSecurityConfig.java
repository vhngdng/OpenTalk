package com.example.demo.security.config;

import com.example.demo.security.Jwt.EntryPoint.AuthEntryPointJwt;
import com.example.demo.security.Jwt.Filter.CustomAuthorizationFilter;
import com.example.demo.security.SecureService.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(                         // enable @PreAuthorize and @PostAuthorize
        prePostEnabled = true,
        securedEnabled = true,
        jsr250Enabled = true
)
@Slf4j
//@Order(1)
public class WebSecurityConfig {
    private final UserDetailServiceImpl userDetailServiceImpl;
    private final AuthEntryPointJwt authEntryPointJwt;


    @Bean
    public CustomAuthorizationFilter authorizationJwtTokenUtil() {
        return new CustomAuthorizationFilter();
    }
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailServiceImpl);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
//        return authenticationConfiguration.getAuthenticationManager();
//    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/images/**", "/js/**", "/webjars/**");
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf()
                .disable()
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPointJwt).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()  // 4type off session: "always", "ifRequired", "never", "stateless"
                .authorizeHttpRequests(requests -> {
                    try {
                        requests
                                .antMatchers("/login")
                                .permitAll()
                                .antMatchers("/oauth/token").permitAll()
                                .antMatchers("/employee/**")
                                .hasAnyRole("ADMIN", "EMPLOYEE")
                                .antMatchers("/admin/**")
                                .hasRole("ADMIN")
                                .anyRequest()
                                .authenticated()
                                .and().rememberMe().userDetailsService(userDetailServiceImpl) ;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
//                .formLogin(withDefaults());
//                .loginPage("/login")
//                .defaultSuccessUrl("/login", true)
//                .usernameParameter("email")
//                .successHandler(customAuthenticationSuccessHandler);
        http.addFilterBefore(authorizationJwtTokenUtil(), UsernamePasswordAuthenticationFilter.class);
        http.authenticationProvider(authenticationProvider());
        return http.build();



    }




    }





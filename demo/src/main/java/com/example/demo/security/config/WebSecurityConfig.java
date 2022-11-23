package com.example.demo.security.config;

import com.example.demo.security.Jwt.EntryPoint.AuthEntryPointJwt;
import com.example.demo.security.Jwt.EntryPoint.CustomAuthenticationSuccessHandler;
import com.example.demo.security.Jwt.Filter.CustomAuthorizationFilter;
import com.example.demo.security.SecureService.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
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
    private final CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;

//    private final CustomUsernamePasswordAuthenticationFilter customUsernamePasswordAuthenticationFilter;

    @Bean
    public CustomAuthorizationFilter authorizationJwtTokenUtil() {
        return new CustomAuthorizationFilter();
    }

//    @Bean
//    public CustomUsernamePasswordAuthenticationFilter authenticationCustomJwtTokenUtil() {
//        return new CustomUsernamePasswordAuthenticationFilter();
//    }

//    @Bean
//    public BCryptPasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

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

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

//    @Bean
//    public UsernamePasswordAuthenticationFilter succesfull
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http.authorizeHttpRequests(requests -> {
//                            try {
//                                requests
//                                        .antMatchers("/login")
//                                        .permitAll()
//                                        .antMatchers("/employee/**")
//                                        .hasAnyRole("EMPLOYEE", "ADMIN")
//                                        .antMatchers("/admin/**")
//                                        .hasRole("ADMIN")
//                                        .anyRequest()
//                                        .authenticated()
//                                        .and()
//                                        .csrf()
//                                        .disable()
//                                ;
////                                http.addFilterBefore(authe)
//                            } catch (Exception e) {
//                                throw new RuntimeException(e);
//                            }
//                        });
////                        .authenticated()
//
////                .formLogin(form -> form.loginPage("/login").usernameParameter("email").permitAll())
////                .httpBasic(Customizer.withDefaults());
//
////                .oauth2ResourceServer().jwt();
////                .logout(LogoutConfigurer::permitAll)
////                .oauth2ResourceServer().jwt();
//
//
//        return http.build();
//    }
//        http.authorizeHttpRequests().antMatchers("/**").permitAll().and().csrf().disable();
//                .cors().and().authorizeRequests().anyRequest().permitAll();
//        return http.build();


    //
//    @Bean
//    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
//        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//        manager.createUser(User.withUsername("employee")
//                .password(bCryptPasswordEncoder.encode("password"))
//                .roles("EMPLOYEE")
//                .build());
//        manager.createUser(User.withUsername("admin")
//                .password(bCryptPasswordEncoder.encode("admin"))
//                .roles("EMPLOYEE", "ADMIN")
//                .build());
//        return manager;
//        UserDetails user = User.withUsername("user")
//                .password("password")
//                .roles("ADMIN")
//                .build();
//        return new InMemoryUserDetailsManager(user);
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
//        http.addFilter((Filter) customUsernamePasswordAuthenticationFilter);
//        http.addFilter(customUsernamePasswordAuthenticationFilter);
//        http.addFilter(CustomUsernamePasswordAuthenticationFilter());
        http.authenticationProvider(authenticationProvider());
        return http.build();



    }




    }





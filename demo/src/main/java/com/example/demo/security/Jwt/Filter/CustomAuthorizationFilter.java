package com.example.demo.security.Jwt.Filter;

import com.example.demo.security.Jwt.JwtTokenUtil;
import com.example.demo.security.SecureService.UserDetailImpl;
import com.example.demo.security.SecureService.UserDetailServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private JwtTokenUtil jwtUtil;


    private static final Logger logger = LoggerFactory.getLogger(CustomAuthorizationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        String jwt = parseJwt(request);
//        logger.info("jwt from request " + jwt);
//        if (jwt != null && jwtUtil.validateJwtToken(jwt)) {
//            String username = jwtUtil.getUserNameFromJwtToken(jwt);
//
//            UserDetails userDetails = userDetailService.loadUserByUsername(username);
//            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        }
//        logger.info("token:" + jwt);
        try {
            final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
            String token = null;
            String username = null;

            //check header != null
            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
                logger.info("Token from header: " + token);
                username = jwtUtil.getUserNameFromJwtToken(token);
                logger.info("username: " + username);
            }

            // if authentication == null
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetailImpl userDetail = userDetailService.loadUserByUsername(username);
                //if jwt is validate
                if (jwtUtil.validateJwtToken(token)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetail, null, userDetail.getAuthorities());
                    logger.info(userDetail.getAuthorities().toString());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    filterChain.doFilter(request, response);
                }
            }
            filterChain.doFilter(request, response);
        }catch(Exception ex){
            log.error("Error logging in: {}", ex.getMessage());
            response.setHeader("error", ex.getMessage());
            response.sendError(HttpStatus.FORBIDDEN.value());
        }
    }

//    private String parseJwt (HttpServletRequest request) {
//        return jwtUtil.getJwtFromCookies(request);
//    }
}

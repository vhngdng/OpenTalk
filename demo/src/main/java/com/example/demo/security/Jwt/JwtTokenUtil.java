package com.example.demo.security.Jwt;

import com.example.demo.CONST.Constant;
import com.example.demo.exception.AccessDeniedException;
import com.example.demo.security.SecureService.UserDetailImpl;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtTokenUtil {

    private final static int jwtExpirationInMs = Constant.refreshExpirationDateInMs;

    private final static long EXPIRE_DURATION = Constant.maxAge;
    //            = 15*60*1000;  // 15 mins

    private final static String secret = Constant.secret;

    private final static String jwtCookie = Constant.cookiename;

//    private Key getSigningKey() {
//        byte[] keyBytes = Decoders.BASE64.decode(this.secret);
//        return Keys.hmacShaKeyFor(keyBytes);
//    }

    //    private Key getSigningKey() {
//
//    }
//    @Value("{app.jwt.refreshExpirationDateInMs}")
//    public void setJwtExpirationInMs(int jwtExpirationInMs) {
//        this.jwtExpirationInMs = jwtExpirationInMs;
//    }

    private final Logger logger = LoggerFactory.getLogger(JwtTokenUtil.class);

    public String generateAccessToken(Authentication authentication) {
        UserDetailImpl userDetailImpl = (UserDetailImpl) authentication.getPrincipal();

        return Jwts
                .builder()
                .setSubject(userDetailImpl.getUsername())
                .setIssuer("OpenTalk")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE_DURATION))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
//        String refreshToken = Jwts
//                .builder()
//                .setSubject(userDetailImpl.getUsername())
//                .setIssuer("OpenTalk")
//                .setIssuedAt(new Date())
//                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//        Map<String, String> tokens = new HashMap<>();
//        tokens.put("access_token", accessToken);
//        tokens.put("refresh_token", refreshToken);

    }

//    public String getJwtFromCookies(HttpServletRequest request) {
//        Cookie cookie = WebUtils.getCookie(request, jwtCookie);
//
//        if (cookie != null){
//            logger.info("cookie name:  " + cookie.getName());
//            return cookie.getValue();}
//        else return null;
//    }

//    public ResponseCookie generateJwtCookie(UserDetailImpl usePrincipal) {
//        String jwt = generateTokenFromUser(usePrincipal.getUsername());
//        logger.info("Token get from cookie " + jwt);
//        return ResponseCookie.from(jwtCookie, jwt).path("/api").maxAge(EXPIRE_DURATION).httpOnly(true).build();
//    }

    public boolean validateJwtToken(String authToken) {
        try {
            return !Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken).getBody().getSubject().isBlank();
//            final String username = getUserNameFromJwtToken(authToken);
//            if (username.equals())
//            return true;
        }catch (AccessDeniedException e){
            throw new AccessDeniedException("Access denied");
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

//    public String generateTokenFromUser(String username) {
//        return Jwts.builder()
//                .setSubject(username)
//                .setIssuedAt(new Date())
//                .setExpiration(new Date((new Date()).getTime() + EXPIRE_DURATION))
//                .signWith(SignatureAlgorithm.HS256, secret)
//                .compact();
//    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }


    public String generateRefreshToken(Authentication authentication) {
        UserDetailImpl userDetailImpl = (UserDetailImpl) authentication.getPrincipal();
        return Jwts
                .builder()
                .setSubject(userDetailImpl.getUsername())
                .setIssuer("OpenTalk")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + jwtExpirationInMs))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }
}

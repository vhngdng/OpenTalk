package com.example.demo.security.Jwt.Filter;

//@Slf4j
//@NoArgsConstructor
//@Component
public class CustomUsernamePasswordAuthenticationFilter{}
//        extends UsernamePasswordAuthenticationFilter {

//    private AuthenticationManager authenticationManager;
//    @Autowired
//    private JwtTokenUtil jwtUtil;

//    @Autowired
//    @Override
//    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
//        this.setAuthenticationManager(super.getAuthenticationManager());
//    }

//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request,
//                                                HttpServletResponse response)
//            throws AuthenticationException {
//        UsernamePasswordAuthenticationToken authRequest = getAuthRequest(request);
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }
//
//    private UsernamePasswordAuthenticationToken getAuthRequest(HttpServletRequest request) {
//        String username = obtainUsername(request);
//        String password = obtainPassword(request);
//        return new UsernamePasswordAuthenticationToken(username,password);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
//        String accessToken = jwtUtil.generateAccessToken(authResult);
//        String refreshToken = jwtUtil.generateRefreshToken(authResult);
//        Map<String, String> tokens = new HashMap<>();
//        tokens.put("access_token", accessToken);
//        tokens.put("refresh_Token", refreshToken);
//        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
//        new ObjectMapper().writeValue(response.getOutputStream(), tokens);
//
//    }
//}

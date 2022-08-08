package br.com.elo7.starlink.infra.security.service;

import br.com.elo7.starlink.domains.user.dto.UserDTO;
import br.com.elo7.starlink.domains.user.service.UserService;
import br.com.elo7.starlink.infra.security.dto.UserAuthDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component
public class TokenAuthenticationService {

    private static final long EXPIRATION_TIME = 28_800_000; // 8hs
    private static final String SECRET = "MySecret";
    private static final String TOKEN_PREFIX = "Bearer";
    private static final String HEADER_STRING = "Authorization";

    public void addAuthentication(HttpServletResponse response, UserDTO user) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String JWT = Jwts.builder()
                .setSubject(user.getEmail())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        UserAuthDTO userResponse = buildToUserResponse(JWT, user);

        response.setStatus(HttpServletResponse.SC_OK);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(mapper.writeValueAsString(userResponse));
        response.getWriter().flush();
        response.getWriter().close();
    }

    public Authentication getAuthentication(HttpServletRequest request, UserService userService) {
        String token = request.getHeader(HEADER_STRING);
        if (token != null) {
            String payload = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                    .getBody()
                    .getSubject();
            String email = payload.split("-")[0];
            UserDTO user = userService.findByEmail(email);
            if (user != null) {
                return new UsernamePasswordAuthenticationToken(buildToUserAuth(user), user.getPassword(), null);
            }
        }
        return null;
    }

    private UserAuthDTO buildToUserAuth(UserDTO user) {
        UserAuthDTO auth = new UserAuthDTO();
        auth.setId(user.getId());
        auth.setEmail(user.getEmail());
        auth.setName(user.getName());
        auth.setPassword(user.getPassword());
        return auth;
    }

    private UserAuthDTO buildToUserResponse(String token, UserDTO user) {
        UserAuthDTO userResponse = new UserAuthDTO();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setToken(token);
        userResponse.setName(user.getName());
        return userResponse;
    }
}
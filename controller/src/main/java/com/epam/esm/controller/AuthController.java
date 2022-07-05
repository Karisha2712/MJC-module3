package com.epam.esm.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.epam.esm.dto.UserDto;
import com.epam.esm.entity.User;
import com.epam.esm.entity.UserRole;
import com.epam.esm.service.UserService;
import com.epam.esm.util.ControllerResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@AllArgsConstructor
public class AuthController {
    private final UserService userService;

    @PostMapping("/registration")
    @PreAuthorize("permitAll()")
    public ControllerResponse registerUser(@Valid @RequestBody UserDto userDto) {
        userService.registerUser(userDto);
        return new ControllerResponse("User was successfully registered");
    }

    @GetMapping("/refresh_token")
    @PreAuthorize("permitAll()")
    public Map<String, String> refreshToken(HttpServletRequest request, HttpServletResponse response) {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String refreshToken = authorizationHeader.substring("Bearer ".length());
            Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
            JWTVerifier verifier = JWT.require(algorithm).build();
            DecodedJWT decodedJWT = verifier.verify(refreshToken);
            String login = decodedJWT.getSubject();
            User user = userService.retrieveUserByLogin(login);
            String accessToken = JWT.create()
                    .withSubject(user.getLogin())
                    .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                    .withIssuer(request.getRequestURI())
                    .withClaim("role", UserRole.getRoleById(user.getRoleId()).toString())
                    .sign(algorithm);
            Map<String, String> tokens = new HashMap<>();
            tokens.put("access_token", accessToken);
            tokens.put("refresh_token", refreshToken);
            response.setContentType(APPLICATION_JSON_VALUE);
            return tokens;
        } else {
            throw new AccessDeniedException("Refresh token is missed");
        }
    }
}

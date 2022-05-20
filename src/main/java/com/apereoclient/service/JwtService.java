package com.apereoclient.service;

import com.apereoclient.exeption.BadRequestException;
import com.apereoclient.model.config.JwtConfigEntity;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

import static com.apereoclient.logging.LogMessage.*;

@Service
@Slf4j
public class JwtService {

    private final JwtConfigEntity jwtConfig;

    public JwtService(JwtConfigEntity jwtConfig) {
        this.jwtConfig = jwtConfig;
    }

    /**
     * Signs and creates a JWT {@link String}.
     * @param responseObject an {@link Object} that holds user data.
     * @return a JWT {@link String}
     */
    public String createAndSignJwt(Object responseObject) {
        try {
            return Jwts.builder()
                    .setSubject(jwtConfig.getIssuer())
                    .claim("payload", responseObject)
                    .setIssuedAt(new Date())
                    .setExpiration(new Date(System.currentTimeMillis() + jwtConfig.getValidity()))
                    .signWith(Keys.hmacShaKeyFor(jwtConfig.getKey().getBytes()))
                    .compact();
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            throw new BadRequestException(SIGNING_JWT_ERROR);
        }
    }

    /**
     * Validates a JWT string, signed by this service.
     * @param jwtToken a {@link String}
     * @return an {@link Object} containing the decoded JWT
     */
    public Object validateJwt(String jwtToken) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(Keys.hmacShaKeyFor(jwtConfig.getKey().getBytes()))
                    .build()
                    .parse(jwtToken)
                    .getBody();
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            throw new BadRequestException(DECODING_JWT_ERROR);
        }
    }
}

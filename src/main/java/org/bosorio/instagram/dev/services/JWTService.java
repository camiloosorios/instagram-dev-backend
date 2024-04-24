package org.bosorio.instagram.dev.services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.enterprise.context.Dependent;

import java.util.Date;

@Dependent
public class JWTService {

    private static final byte[] SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256).getEncoded();
    private static final long EXPIRATION_TIME_MS = 86400000; // 24 horas en milisegundos

    public String generateToken(String username) {
        return Jwts.builder()
                   .setSubject(username)
                   .setIssuedAt(new Date(System.currentTimeMillis()))
                   .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME_MS))
                   .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                   .compact();
    }

    public void validateToken(String token) throws RuntimeException {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token.split(" ")[1]).getBody();
            Date expirationDate = claims.getExpiration();
            System.out.println(claims);
            if (expirationDate.before(new Date())) {
                throw new RuntimeException("El token has expired");
            }
        } catch (JwtException | IllegalArgumentException e) {
            throw new RuntimeException("Invalid token");
        }
    }

}

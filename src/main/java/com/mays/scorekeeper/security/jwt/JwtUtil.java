package com.mays.scorekeeper.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.UUID;

/**
 * A JWT convenience class
 *
 * @author Clayton Mays
 */
@Component
@Slf4j
public class JwtUtil {

    private final static long JWT_LIFETIME_HOURS = 24;

    private final Key key;

    /**
     * A class constructor initializing instance variable with a signature key
     */
    JwtUtil() {
        this.key = Keys.secretKeyFor(SignatureAlgorithm.HS512);
    }

    /**
     * Generates a JWT token to apply to an authorized user making request
     * @param userDetails container for authorized user making request
     * @return the generated JWT token
     */
    public String generateToken(UserDetails userDetails) {
        var claims = new HashMap<String, Object>();
        var now = Instant.now();
        var subject = userDetails.getUsername();
        var jti = UUID.randomUUID().toString();

        return Jwts.builder()
                .setClaims(claims)
                .setId(jti)
                .setSubject(subject)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(now.plus(JWT_LIFETIME_HOURS, ChronoUnit.HOURS)))
                .signWith(key)
                .compact();
    }

    /**
     * extracts authorized user's username from previously generated token
     * @param token a previously generated token to extract username from
     * @return username attached to token
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getAllClaimsFromToken(token);
        return claims.getSubject();
    }

    /**
     * extracts token's issued at date property
     * @param token previously generated JWT token
     * @return Date object containing token's issued at property
     */
    public Date getIssuedAtDateFromToken(String token) {
        return  getAllClaimsFromToken(token).getIssuedAt();
    }

    /**
     * extracts token's expiration date property
     * @param token previously generated JWT token
     * @return Date object containing token's expiration date property
     */
    public Date getExpirationDateFromToken(String token) {
        return getAllClaimsFromToken(token).getExpiration();
    }

    /**
     * Checks that a JWT token is not expired, or if it can be expired
     * @param token previously generated token
     * @return true if the token has not expired
     */
    public boolean canTokenBeRefreshed(String token) {
        return isNotExpired(token);
    }

    /**
     * Validates a JWT token by
     *  comparing token's username property against know user details,
     *  then checks that the token has not expired.
     * @param token previously generated JWT token
     * @param userDetails details generated from JWT token username
     * @return true if the user is the one generated from the tokens username,
     *   and has not expired.
     */
    public boolean validateToken(String token, UserDetails userDetails) {
        var username = getUsernameFromToken(token);
        var isSameUser = username.equals(userDetails.getUsername());
        return (isSameUser && isNotExpired(token));
    }

    /**
     * Extracts all claims from JWT token
     * @param token previously generated token
     * @return a collection of key/value pairs extracted from the token
     */
    private Claims getAllClaimsFromToken(String token) {
        var parser = Jwts.parserBuilder().setSigningKey(key).build();
        try {
            var claims = parser.parseClaimsJws(token);
            return claims.getBody();
        } catch (ExpiredJwtException | UnsupportedJwtException |
                 MalformedJwtException | IllegalArgumentException e) {
            throw new JwtException("Cannot extract claims from token", e);
        }
    }

    /**
     * Extracts the expiration date from a token, and validates it has not expired
     * @param token previously made JWT token
     * @return true if token was created in the last 24 hours
     */
    private boolean isNotExpired(String token) {
        var expiration = getExpirationDateFromToken(token);
        return expiration.after(Date.from(Instant.now()));
    }
}

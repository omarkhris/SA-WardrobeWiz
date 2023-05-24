package edu.miu.orderService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;

import java.util.Base64;
public class JwtUtil {
    private static final String SECRET_KEY = "your-secret-key"; // Replace with your own secret key

    public static String extractEmailFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .setSigningKey(Base64.getEncoder().encodeToString(SECRET_KEY.getBytes()))
                    .parseClaimsJws(token)
                    .getBody();

            return claims.get("email", String.class);
        } catch (JwtException | IllegalArgumentException e) {
            // Handle invalid token or missing email claim
            throw new IllegalArgumentException("Invalid JWT token");
        }
    }
}

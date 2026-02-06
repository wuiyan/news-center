package com.example.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.util.Date;

public class JwtUtil {

    // 至少 32 位
    private static final String SECRET =
            "example-secret-key-1234567890123456";

    // 24小时
    private static final long EXPIRE = 1000 * 60 * 60 * 24;

    private static final SecretKey KEY =
            Keys.hmacShaKeyFor(SECRET.getBytes());


    // 生成 Token
    public static String createToken(Integer userId) {

        return Jwts.builder()
                .setSubject(userId.toString())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(KEY)
                .compact();
    }

    // 解析 Token
    public static Integer parseToken(String token) {

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return Integer.parseInt(claims.getSubject());
    }
}

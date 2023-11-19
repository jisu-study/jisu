package com.study.project.util;

import com.study.project.web.dto.member.MemberSaveRequestDto;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

public class JWTUtil {

    private static final String SECRET_KEY = "mango";

    public static String generateToken(MemberSaveRequestDto member) {
        long now = System.currentTimeMillis();
        long expirationSecond = 60*30;  //30분

        Map<String, Object> header = new HashMap<>();
        header.put("alg", "HS256");
        header.put("typ", "JWT");

        Claims claims = Jwts.claims();
        claims.put("email", member.getUserEmail());
        claims.put("name", member.getUserName());

        return Jwts.builder()
                .setHeader(header)
                .setClaims(claims)
                .setExpiration(new Date(now+1000*expirationSecond))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)
                .compact();
    }

    public static Jws<Claims> validateToken(String token){
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token);
    }

    public static String decodeToken(String token){
        String[] splitToken = token.split("\\.");
        String body = splitToken[1];

        Base64.Decoder decoder = Base64.getDecoder();
        String decodeBody = new String(decoder.decode(body));

        return decodeBody;
    }

}

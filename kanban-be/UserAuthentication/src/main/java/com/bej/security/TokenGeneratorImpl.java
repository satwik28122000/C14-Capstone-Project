package com.bej.security;

import com.bej.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenGeneratorImpl implements ITokenGenerator{
    public String createToken(User user){
        // Write logic to create the Jwt
        Map<String,Object> map = new HashMap<>();
        String userId = user.getUserId();
        map.put("userId",userId);
        return generateToken(map, user.getUserId());
    }

    public String generateToken(Map<String,Object> claims,String subject) {
        // Generate the token and set the user id in the claims
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"secretKey")
                .compact();
        return jwtToken;
    }
}

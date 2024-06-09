package com.bej.security;

import com.bej.domain.Employee;
import com.bej.domain.Manager;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class TokenGeneratorImpl implements ITokenGenerator{
    public String createToken(Employee employee){
        Map<String,Object> map = new HashMap<>();
        String userId = employee.getUserId();
        map.put("userId",userId);
        return generateToken(map, employee.getUserId());
    }
    public String createTokenForManager(Manager manager){
        Map<String,Object> map = new HashMap<>();
        String managerId = manager.getManagerId();
        map.put("managerId",managerId);
        return generateToken(map, manager.getManagerId());
    }
    public String generateToken(Map<String,Object> claims,String subject) {
        // Generate the token and set the user id in the claims
        String jwtToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"kanbanboard")
                .compact();
        return jwtToken;
    }
}

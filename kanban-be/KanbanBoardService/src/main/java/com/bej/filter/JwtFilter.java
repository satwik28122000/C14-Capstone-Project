package com.bej.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class JwtFilter extends GenericFilter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request =(HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String authHeader = request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")){
            throw new ServletException("Missing token or not a bearer token");
        }
        String token = authHeader.substring(7);

        Claims claims = Jwts.parser().setSigningKey("kanbanboard").parseClaimsJws(token).getBody();
        String userId = claims.getSubject(); // Assuming the subject contains the user ID
        request.setAttribute("userId",userId);
        filterChain.doFilter(request,response);
    }
}

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
        if(authHeader==null || !authHeader.startsWith("Bearer")){
            throw new ServletException("Missing token or not a bearer token");
        }
        String token = authHeader.substring(7);

        Claims claims = Jwts.parser().setSigningKey("kanbanboard").parseClaimsJws(token).getBody();
        String userId = claims.getSubject(); // Assuming the subject contains the user ID
        String role = claims.get("role", String.class); // Assuming there's a role claim to distinguish between Employee and Manager

        if ("manager".equals(role)) {
            request.setAttribute("managerId", userId);
        } else if ("employee".equals(role)) {
            request.setAttribute("employeeId", userId);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid role in token");
            return;
        }

        filterChain.doFilter(request,response);
    }
}

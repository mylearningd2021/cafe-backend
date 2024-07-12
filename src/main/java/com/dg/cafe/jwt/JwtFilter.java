package com.dg.cafe.jwt;

import com.dg.cafe.pojo.User;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CustomeUserDetailsService customeUserDetailsService;

    Claims claims = null;
    private String username = null;
    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        if(httpServletRequest.getServletPath().equals("/user/signup | /user/forgotpassword | /user/login"))
        {
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }else {
                String authorizationHeader = httpServletRequest.getHeader("Authorization");
                String token = null;
                if(!Objects.isNull(authorizationHeader) && authorizationHeader.startsWith("Bearer "))
                {
                    token = authorizationHeader.substring(7);
                    username = jwtUtils.extractUsername(token);
                    claims = jwtUtils.extractAllClaims(token);
                }

                if(username != null && SecurityContextHolder.getContext().getAuthentication()==null)
                {   //from spring security package
                    UserDetails userDetails = customeUserDetailsService.loadUserByUsername(username);
                    if(jwtUtils.validateToken(token,userDetails))
                    {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                                new UsernamePasswordAuthenticationToken(userDetails,null,
                                        userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken.setDetails(
                                new WebAuthenticationDetailsSource().buildDetails(httpServletRequest)
                        );

                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                }
            filterChain.doFilter(httpServletRequest,httpServletResponse);
        }
    }

    public Boolean isAdmin()
    {
        return "admin".equalsIgnoreCase((String) claims.get("role"));
    }
    public Boolean isUser()
    {
        return "user".equalsIgnoreCase((String) claims.get("role"));
    }
    public String getCurrentUser()
    {
        return username;
    }
}

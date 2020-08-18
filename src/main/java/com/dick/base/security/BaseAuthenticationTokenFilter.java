package com.dick.base.security;

import com.dick.base.session.service.SessionService;
import com.dick.base.util.BaseConstProperties;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class BaseAuthenticationTokenFilter extends OncePerRequestFilter {

    private SessionService sessionService;

    public BaseAuthenticationTokenFilter(SessionService sessionService) {
        this.sessionService = sessionService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String header = request.getHeader(BaseConstProperties.getInstance().getTokenName());
        if (!StringUtils.isEmpty(header)) {
            UserDetails userDetails = sessionService.loadUserByUsername(header);
            if ( userDetails != null && userDetails.isEnabled()) {
                setDetails(request, response, filterChain, userDetails);
                return;
            }
        }
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
    }

    private void setDetails(HttpServletRequest request, HttpServletResponse response, FilterChain chain, UserDetails userDetails) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}

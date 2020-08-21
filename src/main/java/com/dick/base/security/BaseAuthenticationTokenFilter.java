package com.dick.base.security;

import com.dick.base.exception.ExceptionProperties;
import com.dick.base.exception.GlobalExceptionHandler;
import com.dick.base.session.service.SessionService;
import com.dick.base.util.BaseConstProperties;
import com.dick.base.util.BaseResult;
import com.dick.base.util.JsonUtil;
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
    private GlobalExceptionHandler globalExceptionHandler;

    public BaseAuthenticationTokenFilter(SessionService sessionService, GlobalExceptionHandler globalExceptionHandler) {
        this.sessionService = sessionService;
        this.globalExceptionHandler = globalExceptionHandler;
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

        JsonUtil.writeResponse(BaseResult.errorResult(globalExceptionHandler.getLocaleMessage(ExceptionProperties.getInstance().getUserNotLogin(), request.getLocale())), HttpStatus.UNAUTHORIZED.value());
    }

    private void setDetails(HttpServletRequest request, HttpServletResponse response, FilterChain chain, UserDetails userDetails) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}

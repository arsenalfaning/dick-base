package com.dick.base.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private BaseAuthenticationTokenFilter baseAuthenticationTokenFilter;

    public SecurityConfig(BaseAuthenticationTokenFilter baseAuthenticationTokenFilter) {
        this.baseAuthenticationTokenFilter = baseAuthenticationTokenFilter;
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers("/public/**", "/static/**");
        web.ignoring().antMatchers("/v2/api-docs", "/webjars/**", "/swagger-resources/**");
        web.ignoring().antMatchers("/**.html", "/**.js", "/**.css", "/**.ico", "/static/**", "/**.png", "/**.svg");
        web.ignoring().mvcMatchers(HttpMethod.OPTIONS, "/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.headers().cacheControl();
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .and()
                .formLogin().disable() //不要UsernamePasswordAuthenticationFilter
                .httpBasic().disable() //不要BasicAuthenticationFilter
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .securityContext().and()
                .anonymous().disable()
                .servletApi();
        httpSecurity.addFilterBefore(baseAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}

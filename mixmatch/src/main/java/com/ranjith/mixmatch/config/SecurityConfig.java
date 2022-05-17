package com.ranjith.mixmatch.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import com.ranjith.mixmatch.util.SecurityFilter;

@Component
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	@Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder bCryptEncoder;
    
    @Autowired
    private SecurityFilter securityFilter;
    
    @Autowired
    private UnAuthorizedUserAuthenticationEntryPoint authenticationEntryPoint;
    
    @Override
    @Bean
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptEncoder);
    }

	@Override
	protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
        .antMatchers("/api/users/signup", "/api/users/login","/api/users/hello")
        .permitAll()
        .anyRequest().authenticated().and().exceptionHandling()
        .authenticationEntryPoint(authenticationEntryPoint).and().sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
        .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
        .httpBasic();
	}
}

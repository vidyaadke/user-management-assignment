package com.users.example.security;

import com.users.example.enums.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        // Disable CSRF
        http.csrf().disable()
            // Only admin can perform HTTP put operation
            .authorizeRequests()

            // any authenticated user can perform all other operations
            .antMatchers(HttpMethod.GET,"/users/**").hasAnyRole(Role.ADMIN, Role.USER, Role.CONSULTANT)
            .antMatchers(HttpMethod.PUT,"/users/**").hasAnyRole(Role.ADMIN).and().httpBasic()

            // Permit all other request without authentication
            .and().authorizeRequests().anyRequest().permitAll()

            // We don't need sessions to be created.
            .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("admin")
            .password("{noop}password")
            .roles(Role.ADMIN)
            .and()
            .withUser("user")
            .password("{noop}password1")
            .roles(Role.USER)
            .and()
            .withUser("consultant")
            .password("{noop}password2")
            .roles(Role.CONSULTANT);
    }
}
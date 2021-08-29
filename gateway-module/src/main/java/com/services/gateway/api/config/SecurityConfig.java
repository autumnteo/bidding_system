package com.services.gateway.api.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.services.gateway.api.security.BidUserDetailsService;
import com.services.gateway.api.security.JwtConfigurer;
import com.services.gateway.api.security.JwtTokenProvider;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{
	
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    BidUserDetailsService bidUserDetailsService;
    
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(bidUserDetailsService)
        .passwordEncoder(passwordEncoder());
    }
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .httpBasic().disable()
                .csrf().disable()
                .cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS,"/*").permitAll()
                .antMatchers(HttpMethod.GET,"/user/admin/*").hasRole("ADMIN")
                .antMatchers(HttpMethod.POST,"/api/login").permitAll()
                .antMatchers(HttpMethod.POST,"/user/api/create").permitAll()
                .antMatchers(HttpMethod.GET,"/user/api/confirm-account/**").permitAll()
                .antMatchers(HttpMethod.GET,"/user/api/**").authenticated()
                .antMatchers(HttpMethod.POST,"/user/api/triggerEmail/*").denyAll()
                .antMatchers(HttpMethod.GET,"/bid/api/public/*").authenticated()
                .antMatchers(HttpMethod.GET,"/bid/api/*").denyAll()
                .antMatchers("/*").permitAll()
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        
        httpSecurity.exceptionHandling().accessDeniedPage("/login");
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
        // Allow eureka client to be accessed without authentication
        web.ignoring().antMatchers("/*/")//
                .antMatchers("/eureka/**")//
                .antMatchers(HttpMethod.OPTIONS, "/**")
                .antMatchers(HttpMethod.OPTIONS, "/*"); // Request type options should be allowed.
    }
}

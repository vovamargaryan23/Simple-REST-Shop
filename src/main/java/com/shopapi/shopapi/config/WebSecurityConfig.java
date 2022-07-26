package com.shopapi.shopapi.config;

import com.shopapi.shopapi.model.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                    .antMatchers("/api/v1/products/**").permitAll()
                    .anyRequest()
                    .authenticated()
                    .and()
                    .httpBasic();
    }


    @Bean
    public BCryptPasswordEncoder getEncoder(){
        return new BCryptPasswordEncoder(16);
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService(){
        UserDetails user =
                User.builder()
                .username("user")
                .password(getEncoder().encode("pass"))
                .authorities(Role.USER.getAuthorities())
                .build();

        UserDetails admin =
                User.builder()
                        .username("admin")
                        .password(getEncoder().encode("pass"))
                        .authorities(Role.ADMIN.getAuthorities())
                        .build();

        return new InMemoryUserDetailsManager(user,admin);
    }
}

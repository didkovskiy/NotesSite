package com.didkovskiy.shitsite.config;

import com.didkovskiy.shitsite.domains.userstore.Permission;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    public SecurityConfig(@Qualifier("userDetailsServiceImpl") UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                    .passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers(HttpMethod.GET,"/")
                        .hasAuthority(Permission.READ.getPermission())
                    .antMatchers(HttpMethod.POST, "/","/**")
                        .hasAuthority(Permission.READ.getPermission())
                    .antMatchers(HttpMethod.GET, "/filter", "/delete")
                        .denyAll()
                    .antMatchers(HttpMethod.POST, "/delete")
                        .hasAuthority(Permission.DELETE.getPermission())
                    .antMatchers("/login" , "/register").permitAll()
                    .antMatchers("/users","/users/**").hasAuthority(Permission.DELETE.getPermission())
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true)
                .and()
                    .logout()
                        .logoutSuccessUrl("/login")
                ;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(BCryptPasswordEncoder.BCryptVersion.$2A);
    }

}

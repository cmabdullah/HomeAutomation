package com.abdullah.home.automation.config;

import com.abdullah.home.automation.service.UserSecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private UserSecurityService userSecurityService;

    private static final String[] PUBLIC_MATCHERS = {
            "/css/**",
            "/**",
            "/webjars/**",
            "/js/**",
            "/image/**",
            //"/",
            "/newUser",
            "/forgetPassword",
            "/login",
            "/live-temperature/**",
            "/temp",
            "/live-commentary",
            "/live-comment",
            "/stomp/**",
            "/static/**",
            "/publisher",
            "/get-file",
//            "/myAccount",
            "/fonts/**"
    };

    private static final String[] AUTH_MATCHERS = {
            "/actuator/**",
            "/"
    };

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userSecurityService).passwordEncoder(bCryptPasswordEncoder);
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTH_MATCHERS)
                .access("hasRole('ROLE_USER')")
                .and()
                .formLogin().failureUrl("/login?error").defaultSuccessUrl("/")
                .loginPage("/login").permitAll()

                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/?logout").deleteCookies("remember-me").permitAll()
                .and()
                .rememberMe()

                .key("uniqueAndSecret")
        ;

                http.authorizeRequests().
                /*	antMatchers("/**").*/
                        antMatchers(PUBLIC_MATCHERS).
                permitAll().anyRequest().authenticated();

        http.csrf().disable();
        http.headers().frameOptions().disable();
    }


}
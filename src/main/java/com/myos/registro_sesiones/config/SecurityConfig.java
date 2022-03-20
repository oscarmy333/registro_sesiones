package com.myos.registro_sesiones.config;

import com.myos.registro_sesiones.servicio.UsuarioDetailsServiceImpl;
import com.myos.registro_sesiones.util.constants.RoleName;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.SecurityExpressionOperations;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebSecurityExpressionRoot;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UsuarioDetailsServiceImpl userDetailsService;
    //private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(UsuarioDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
        //this.passwordEncoder = this.getPasswordEncoderBean();
        //Falta terminar el registro...
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.userDetailsService).passwordEncoder(this.getPasswordEncoderBean());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/super-admin/**").hasRole(RoleName.SUPER_ADMIN.toString())
                .antMatchers("/admin/**").hasAnyRole(RoleName.ADMIN.toString(), RoleName.SUPER_ADMIN.toString())
                .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .and()
                .logout()
                    // By default logout is done by GET method, but it is good practice to replace it with POST
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"))
                .and()
                // disable csrf if you are about to use any time of custom POST request, except standard form submission
                // even for AJAX requests, disable CSRF. It can be under some arguing with security experts.
                // but it is common practice to disable csrf for REST web services
                .csrf().disable()
                .sessionManagement()
                    // To forbid having more than 3 session at the same time.
                    // That will save our App from having some kind of DDoS attack, of spawning infinity many sessions
                    // and thus waste our Redis server resources
                    .maximumSessions(3)
                    .maxSessionsPreventsLogin(true);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.expressionHandler(
                new DefaultWebSecurityExpressionHandler() {
            @Override
            protected SecurityExpressionOperations createSecurityExpressionRoot(Authentication authentication, FilterInvocation filterInvocation) {
                WebSecurityExpressionRoot root = (WebSecurityExpressionRoot) super.createSecurityExpressionRoot(authentication, filterInvocation);
                root.setDefaultRolePrefix("");
                return root;
            }
        });
    }

    @Bean
    public BCryptPasswordEncoder getPasswordEncoderBean() {
        return new BCryptPasswordEncoder();
    }
}

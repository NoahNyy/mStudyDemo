package org.noahnyy.demo.spring.security.config;

import org.noahnyy.demo.spring.security.encoder.DefaultPasswordEncoder;
import org.noahnyy.demo.spring.security.filter.JwtRequestFilter;
import org.noahnyy.demo.spring.security.point.JwtAuthenticationEntryPoint;
import org.noahnyy.demo.spring.security.service.JwtUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * 通过 @EnableWebSecurity 注解开启Spring Security的功能
 * <p>
 * 使用 @EnableGlobalMethodSecurity(prePostEnabled = true) 这个注解，
 * 可以开启security的注解，我们可以在需要控制权限的方法上面使用@PreAuthorize，@PreFilter这些注解。
 *
 * @author niuyy
 * @since 2020/2/21
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private JwtUserDetailService jwtUserDetailsService;
    @Autowired
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    @Autowired
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private DefaultPasswordEncoder defaultPasswordEncoder;
    @Value("${web.excludePathPatterns}")
    private String[] excludePathPatterns;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // We don't need CSRF for this example
        http.csrf().disable()
                // dont authenticate this particular request
                .authorizeRequests()
                .antMatchers(excludePathPatterns)
                .permitAll()
                // all other requests need to be authenticated
                // 因为使用了jwt, 所以拦截使用jwtRequestFilter, 不需要security的验证
                /*.anyRequest()
                .authenticated()*/
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                // make sure we use stateless session; session won't be used to store user's state.
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // Add a filter to validate the tokens with every request
                .and()
                .addFilterAt(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(jwtUserDetailsService)
                .passwordEncoder(defaultPasswordEncoder);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

}

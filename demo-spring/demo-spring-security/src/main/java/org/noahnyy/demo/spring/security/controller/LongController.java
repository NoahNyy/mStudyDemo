package org.noahnyy.demo.spring.security.controller;

import org.noahnyy.demo.spring.security.config.JwtAudienceConfig;
import org.noahnyy.demo.spring.security.service.JwtUserDetailService;
import org.noahnyy.demo.spring.security.service.JwtUserDetails;
import org.noahnyy.demo.spring.security.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author niuyy
 * @since 2020/2/21
 */
@RestController
public class LongController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUserDetailService userDetailsService;
    @Autowired
    private JwtAudienceConfig audienceConfig;

    @PostMapping("/login")
    public String test(@RequestParam String username, @RequestParam String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            JwtUserDetails userDetails = (JwtUserDetails) userDetailsService.loadUserByUsername(username);
            return JwtTokenUtil.createToken(userDetails.getUserId(), userDetails.getUsername(), audienceConfig);
        } catch (Exception e) {
            return "登录失败! ";
        }
    }
}

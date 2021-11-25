package com.tulingxueyuan.mallfront.configs;

import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

/***
 * @Author peipei
 */
@Configuration
@EnableWebSecurity  // 启动
public class MallSecurityConfig extends SecurityConfig {

    @Autowired
    private UmsMemberService memberService;

    /**
     * 认证交给springSecurity
     * @return UserDetailsService
     */
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> memberService.loadMemberByName(username);
    }

}

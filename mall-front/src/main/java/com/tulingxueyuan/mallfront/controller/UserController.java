package com.tulingxueyuan.mallfront.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.common.util.JwtTokenUtil;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberCacheService;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import java.util.HashMap;

/**
 * @Description:
 * @Name: LoginController
 * @Author peipei
 * @Date 2021/10/19
 */
@RequestMapping("/user")
@RestController
public class UserController {
    @Autowired
    UmsMemberService memberService;

    @Autowired
    JwtTokenUtil jwtTokenUtil;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.tokenHeader}")
    private String tokenHeader;

    @PostMapping("/login")
    public CommonResult login(@Validated UmsMember umsMember) {
        UmsMember loginMember = memberService.login(umsMember.getUsername(),umsMember.getPassword());
        String token = jwtTokenUtil.generateUserNameStr(loginMember.getUsername());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tokenHead", tokenHead);
        hashMap.put("tokenHeader", tokenHeader);
        hashMap.put("token", token);
        return CommonResult.success(hashMap);
    }

    @PostMapping("/register")
    public CommonResult register(@Validated @RequestBody UmsMember umsMember) {
        boolean registerMember = memberService.registerMember(umsMember);
        if (registerMember) {
            return CommonResult.success(registerMember);
        } else {
            return CommonResult.failed();
        }
    }
}

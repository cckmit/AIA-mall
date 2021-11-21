package com.tulingxueyuan.mallfront.modules.ums.service.impl;

import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mallfront.modules.ums.service.RedisService;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberCacheService;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @Description:
 * @Name: UmsMemberCacheServiceIml
 * @Author peipei
 * @Date 2021/10/17
 */
@Service
public class UmsMemberCacheServiceIml implements UmsMemberCacheService {
    @Autowired
    UmsMemberService memberService;

    @Autowired
    RedisService redisService;

    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.user}")
    private String REDIS_USER;

    @Override
    public UmsMember getUser(String userName) {
        String key = REDIS_DATABASE + ":" + REDIS_USER + ":" + userName;
        UmsMember umsMember= (UmsMember)redisService.get(key);
        return umsMember;
    }

    @Override
    public void setUser(UmsMember umsMember) {
        String key = REDIS_DATABASE + ":" + REDIS_USER + ":" + umsMember.getUsername();
        redisService.set(key,umsMember,REDIS_EXPIRE);
    }

    @Override
    public boolean delUser(Long id) {
        UmsMember memberById = memberService.getById(id);
        if (memberById != null) {
            String key = REDIS_DATABASE + ":" + REDIS_USER + ":" + memberById.getUsername();
            Boolean del = redisService.del(key);
            return del;
        } else {
            throw new ApiException("未查找到该用户信息");
        }
    }
}

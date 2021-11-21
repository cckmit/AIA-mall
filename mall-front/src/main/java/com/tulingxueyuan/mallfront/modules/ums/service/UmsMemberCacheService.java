package com.tulingxueyuan.mallfront.modules.ums.service;

import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;

/**
 * The interface Ums member cache service.
 *
 * @Description:
 * @Name: UmsMemberCacheService
 * @Author peipei
 * @Date 2021 /10/17
 */
public interface UmsMemberCacheService {

    /**
     * 获得该用户的缓存信息
     *
     * @param username the user name
     * @return the user
     */
    UmsMember getUser(String username);


    /**
     * 设置该用户的缓存信息
     *
     * @param umsMember the user name
     */
    void setUser(UmsMember umsMember);


    /**
     * 删除该用户的缓存信息
     *
     * @param id the user name
     */
    boolean delUser(Long id);

}

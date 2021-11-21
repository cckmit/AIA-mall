package com.tulingxueyuan.mallfront.modules.ums.service;

import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
public interface UmsMemberService extends IService<UmsMember> {


    /**
     * 获取当前登陆的用户
     * @return
     */
    UmsMember getCurrentMember();


    UserDetails loadMemberByName(String username);


    /**
     * 新用户注册
     * @param umsMember
     * @return
     */
    boolean registerMember(UmsMember umsMember);

    /**
     * 通过id删除该用户
     * @param id
     * @return
     */
    boolean deleteMember(Long id);

    /**
     * 登录该用户
     * @param userName
     * @param passWord
     * @return
     */
    UmsMember login(String userName,String passWord);


    boolean starStore(Long orderId);
}

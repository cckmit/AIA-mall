package com.tulingxueyuan.mallfront.modules.ums.service.impl;

import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mall.common.exception.Asserts;
import com.tulingxueyuan.mallfront.domain.MemberDetails;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsOrderService;
import com.tulingxueyuan.mallfront.modules.ums.mapper.UmsMemberStarMapper;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsAdmin;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.mapper.UmsMemberMapper;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberStar;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberStore;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberCacheService;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberStarService;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberStoreService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
@Service
public class UmsMemberServiceImpl extends ServiceImpl<UmsMemberMapper, UmsMember> implements UmsMemberService {
    @Autowired
    UmsMemberCacheService memberCacheService;

    @Autowired
    UmsMemberService umsMemberService;

    @Autowired
    OmsOrderService omsOrderService;

    @Autowired
    UmsMemberStarMapper umsMemberStarMapper;

    @Autowired
    UmsMemberStoreService umsMemberStoreService;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UmsMember getCurrentMember() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        MemberDetails memberDetails = (MemberDetails) authentication.getPrincipal();
        UmsMember currentMember = memberDetails.getUmsMember();
        return currentMember;
    }

    public UmsMember getMemberByUserName(String userName) {
        UmsMember cacheMember = memberCacheService.getUser(userName);
        if (cacheMember == null) /*该用户信息未在redis中缓存*/ {
            LambdaQueryWrapper<UmsMember> memberLambdaQueryWrapper = new QueryWrapper<UmsMember>().lambda();
            memberLambdaQueryWrapper.eq(UmsMember::getUsername, userName)
                    .eq(UmsMember::getStatus, 1);
            UmsMember memberByUserName = umsMemberService.getOne(memberLambdaQueryWrapper);
            return memberByUserName;
        }
        return cacheMember;
    }

    @Override
    public boolean registerMember(UmsMember umsMember) {
        //先从数据库中查找有没有该用户信息
        LambdaQueryWrapper<UmsMember> umsMemberLambdaQueryWrapper = new QueryWrapper<UmsMember>().lambda();
        umsMemberLambdaQueryWrapper.eq(UmsMember::getUsername, umsMember.getUsername());
        UmsMember memberByName = umsMemberService.getOne(umsMemberLambdaQueryWrapper);
        if (memberByName != null) {
            throw new ApiException("用户名已被注册!");
        }
        String encodePassword = bCryptPasswordEncoder.encode(umsMember.getPassword());
        umsMember.setUsername(umsMember.getUsername());
        umsMember.setPassword(encodePassword);
        boolean saveNewMember = umsMemberService.save(umsMember);
        return saveNewMember;
    }

    @Override
    public UserDetails loadMemberByName(String username) {
        UmsMember memberByUserName = this.getMemberByUserName(username);
        if (memberByUserName != null) {
            MemberDetails memberDetails = new MemberDetails(memberByUserName);
            return memberDetails;
        } else {
            throw new ApiException("未查找到该用户");
        }
    }

    @Override
    public boolean deleteMember(Long id) {
        LambdaQueryWrapper<UmsMember> memberLambdaQueryWrapper = new QueryWrapper<UmsMember>().lambda();
        memberLambdaQueryWrapper.eq(UmsMember::getId, id);
        boolean removeById = umsMemberService.remove(memberLambdaQueryWrapper);
        //清楚该用户的缓存信息
        memberCacheService.delUser(id);
        return removeById;

    }

    @Override
    public UmsMember login(String userName, String passWord) {
        UmsMember umsAdmin=null;
        try {
            UserDetails userDetails =  loadMemberByName(userName);
            umsAdmin=((MemberDetails)userDetails).getUmsMember();
            if(!bCryptPasswordEncoder.matches(passWord,umsAdmin.getPassword())){
                System.out.println("密码不正确");
                Asserts.fail("密码不正确");
            }

            // 生成springsecurity的通过认证标识
            UsernamePasswordAuthenticationToken authenticationToken=new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            if(!userDetails.isEnabled()){
                Asserts.fail("帐号已被禁用");
            }
        } catch (Exception e) {
            Asserts.fail("登录异常:"+e.getMessage());
        }
        return umsAdmin;

    }

    /**
     * 收藏店铺
     * @param orderId
     * @return
     */
    @Override
    public boolean starStore(Long orderId) {
        OmsOrder orderById = omsOrderService.getById(orderId);
        LambdaQueryWrapper<UmsMember> memberLambdaQueryWrapper = new QueryWrapper<UmsMember>().lambda();
        memberLambdaQueryWrapper.eq(UmsMember::getUsername, orderById.getConsignorName());
        UmsMember consignorMember = this.getOne(memberLambdaQueryWrapper);
        LambdaQueryWrapper<UmsMemberStore> storeLambdaQueryWrapper = new QueryWrapper<UmsMemberStore>().lambda();
        storeLambdaQueryWrapper.eq(UmsMemberStore::getMemberId, consignorMember.getId());
        UmsMemberStore consignorStore = umsMemberStoreService.getOne(storeLambdaQueryWrapper);
        LambdaQueryWrapper<UmsMemberStar> memberStarLambdaQueryWrapper = new QueryWrapper<UmsMemberStar>().lambda();
        memberStarLambdaQueryWrapper.eq(UmsMemberStar::getMemberId, getCurrentMember().getId());
        UmsMemberStar umsMemberStar = umsMemberStarMapper.selectOne(memberStarLambdaQueryWrapper);
        String starStoreIds = umsMemberStar.getStarStoreId();
        for (int i = 0; i < starStoreIds.length(); i++) {
            String id = String.valueOf(starStoreIds.charAt(i));
            if (!id.equals(",")&&consignorStore.getId()==Long.valueOf(id)) {
                return false;
            }
        }
        return umsMemberStarMapper.updateStarStore(getCurrentMember().getId(),consignorStore.getId());
    }


}

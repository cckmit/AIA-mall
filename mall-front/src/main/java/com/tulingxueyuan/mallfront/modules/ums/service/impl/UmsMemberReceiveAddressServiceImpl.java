package com.tulingxueyuan.mallfront.modules.ums.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mall.common.exception.ApiException;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMember;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberReceiveAddress;
import com.tulingxueyuan.mallfront.modules.ums.mapper.UmsMemberReceiveAddressMapper;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberReceiveAddressService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
@Service
public class UmsMemberReceiveAddressServiceImpl extends ServiceImpl<UmsMemberReceiveAddressMapper, UmsMemberReceiveAddress> implements UmsMemberReceiveAddressService {
    @Autowired
    UmsMemberService umsMemberService;

    /**
     * 获取当前登录用户的所有收获地址
     * @return
     */
    @Override
    public List<UmsMemberReceiveAddress> getMemberAddressList() {
        UmsMember currentMember = umsMemberService.getCurrentMember();
        LambdaQueryWrapper<UmsMemberReceiveAddress> addressLambdaQueryWrapper = new QueryWrapper<UmsMemberReceiveAddress>().lambda();
        addressLambdaQueryWrapper.eq(UmsMemberReceiveAddress::getMemberId, currentMember.getId());
        List<UmsMemberReceiveAddress> memberReceiveAddressList = this.list(addressLambdaQueryWrapper);
        if (memberReceiveAddressList == null) {
            throw new ApiException("您暂时还没有收货地址");
        } else {
            return memberReceiveAddressList;
        }
    }
}

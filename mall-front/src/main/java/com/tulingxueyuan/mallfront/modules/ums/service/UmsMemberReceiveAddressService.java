package com.tulingxueyuan.mallfront.modules.ums.service;

import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberReceiveAddress;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 会员收货地址表 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-10-17
 */
public interface UmsMemberReceiveAddressService extends IService<UmsMemberReceiveAddress> {

    List<UmsMemberReceiveAddress> getMemberAddressList();
}

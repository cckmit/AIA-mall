package com.tulingxueyuan.mallfront.controller;

import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsOrderService;
import com.tulingxueyuan.mallfront.modules.ums.model.UmsMemberReceiveAddress;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberReceiveAddressService;
import com.tulingxueyuan.mallfront.modules.ums.service.UmsMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Description:
 * @Name: MemberController
 * @Author peipei
 * @Date 2021/10/26
 */

@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    UmsMemberReceiveAddressService memberReceiveAddressService;

    @Autowired
    UmsMemberService umsMemberService;


    @GetMapping("/address/list")
    public CommonResult getMemberAddressList() {
        List<UmsMemberReceiveAddress> memberReceiveAddressList = memberReceiveAddressService.getMemberAddressList();
        return CommonResult.success(memberReceiveAddressList);
    }

    @PostMapping("/star/store")
    public CommonResult starStore(@RequestParam("orderId") Long orderId) {
        boolean starStore=umsMemberService.starStore(orderId);
            return CommonResult.success(starStore);
    }
}


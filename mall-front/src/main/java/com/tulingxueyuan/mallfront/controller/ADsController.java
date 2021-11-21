package com.tulingxueyuan.mallfront.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mallfront.dto.ADProductDTO;
import com.tulingxueyuan.mallfront.modules.oms.model.OmsOrder;
import com.tulingxueyuan.mallfront.modules.oms.service.OmsOrderService;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProduct;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProductCategory;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsProductCategoryService;
import com.tulingxueyuan.mallfront.modules.pms.service.PmsProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @Description:
 * @Name: ADsController
 * @Author peipei
 * @Date 2021/11/20
 */
@RestController
@RequestMapping("/ads")
public class ADsController {
    @Autowired
    PmsProductCategoryService pmsProductCategoryService;

    @Autowired
    PmsProductService pmsProductService;

    @Autowired
    OmsOrderService omsOrderService;


    @GetMapping("/like/orderComplete")
    public CommonResult getOrderCompleteLikes(@RequestParam("orderId") Integer orderId) {
        List<ADProductDTO> adProductDTOList=pmsProductService.getOrderCompleteLikes(orderId);
        return CommonResult.success(adProductDTOList);
    }
}

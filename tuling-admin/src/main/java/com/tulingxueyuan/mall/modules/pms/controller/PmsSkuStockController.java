package com.tulingxueyuan.mall.modules.pms.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.tulingxueyuan.mall.common.api.CommonResult;
import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku的库存 前端控制器
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@RestController
@RequestMapping("/sku")
public class PmsSkuStockController {
    @Autowired
    PmsSkuStockService pmsSkuStockService;

    @ApiOperation("获取商品库存信息")
    @GetMapping("/{id}")
    public CommonResult getSkuStockList(@PathVariable("id") Long id,
                                        @RequestParam(value = "keyword", defaultValue = "") String keyWord) {
        // LambdaQueryWrapper<PmsSkuStock> skuStockQueryWrapper = new QueryWrapper<PmsSkuStock>().lambda();
        // skuStockQueryWrapper.like(PmsSkuStock::getSkuCode, keyWord).eq(PmsSkuStock::getId,id);
        List<PmsSkuStock> skuStocks = pmsSkuStockService.getSkuStocks(id, keyWord);
        return CommonResult.success(skuStocks);
    }

    @ApiOperation("更新货品信息")
    @PostMapping("/update/{pid}")
    public CommonResult update(@PathVariable("pid") Long pid,
                               @RequestBody List<PmsSkuStock> skuStockList) {
        UpdateWrapper<PmsSkuStock> pmsSkuStockUpdateWrapper = new UpdateWrapper<>();
        LambdaUpdateWrapper<PmsSkuStock> updateWrapper = pmsSkuStockUpdateWrapper.lambda();
        updateWrapper.eq(PmsSkuStock::getProductId,pid);
        boolean updateBatch = pmsSkuStockService.saveOrUpdateBatch(skuStockList);
        if (updateBatch) {
            return CommonResult.success(updateBatch);
        } else {
            return CommonResult.failed();
        }
    }



}


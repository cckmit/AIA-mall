package com.tulingxueyuan.mall.modules.pms.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.tulingxueyuan.mall.modules.pms.service.PmsSkuStockService;
import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;
import com.tulingxueyuan.mall.modules.pms.mapper.PmsSkuStockMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * sku的库存 服务实现类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
@Slf4j
@Service
public class PmsSkuStockServiceImpl extends ServiceImpl<PmsSkuStockMapper, PmsSkuStock> implements PmsSkuStockService {
    @Autowired
    PmsSkuStockMapper skuStockMapper;

    @Override
    public List<PmsSkuStock> getSkuStocks(Long id,String keyWord) {
        LambdaQueryWrapper<PmsSkuStock> skuStockWrapper = new QueryWrapper<PmsSkuStock>().lambda();
        skuStockWrapper.eq(PmsSkuStock::getProductId, id).like(PmsSkuStock::getSkuCode, keyWord);
        List<PmsSkuStock> skuStocks = skuStockMapper.selectList(skuStockWrapper);
        return skuStocks;
    }
}

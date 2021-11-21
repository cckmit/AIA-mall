package com.tulingxueyuan.mall.modules.pms.service;

import com.tulingxueyuan.mall.modules.pms.model.PmsSkuStock;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku的库存 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
public interface PmsSkuStockService extends IService<PmsSkuStock> {

    List<PmsSkuStock> getSkuStocks(Long id,String keyWord);
}

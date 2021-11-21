package com.tulingxueyuan.mallfront.modules.pms.service;

import com.tulingxueyuan.mallfront.dto.ADProductDTO;
import com.tulingxueyuan.mallfront.dto.ProductDetailDTO;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProduct;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品信息 服务类
 * </p>
 *
 * @author XuShu
 * @since 2021-03-14
 */
public interface PmsProductService extends IService<PmsProduct> {


    // ProductDetailDTO getProductDetail(Long id);

    List<ADProductDTO> getOrderCompleteLikes(Integer orderId);
}

package com.tulingxueyuan.mallfront.modules.pms.mapper;

import com.tulingxueyuan.mallfront.dto.ProductDetailDTO;
import com.tulingxueyuan.mallfront.modules.pms.model.PmsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author XuShu
 * @since 2021-03-14
 */
public interface PmsProductMapper extends BaseMapper<PmsProduct> {

    // ProductDetailDTO getProductDetail(Long id);
}

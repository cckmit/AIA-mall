package com.tulingxueyuan.mall.modules.pms.mapper;

import com.tulingxueyuan.dto.PmsProductDetailDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProduct;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 * 商品信息 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
public interface PmsProductMapper extends BaseMapper<PmsProduct> {
    PmsProductDetailDTO getInfoById(Long id);
}

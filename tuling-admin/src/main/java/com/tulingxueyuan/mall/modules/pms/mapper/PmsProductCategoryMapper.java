package com.tulingxueyuan.mall.modules.pms.mapper;

import com.tulingxueyuan.dto.ProductCateChildrenDTO;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductCategory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 产品分类 Mapper 接口
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
public interface PmsProductCategoryMapper extends BaseMapper<PmsProductCategory> {

    List<ProductCateChildrenDTO> getWithChildren();
}

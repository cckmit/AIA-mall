package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttributeCategory;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 产品属性分类表 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
public interface PmsProductAttributeCategoryService extends IService<PmsProductAttributeCategory> {
    Page getList(Integer pageNum, Integer pageSize);


}

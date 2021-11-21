package com.tulingxueyuan.mall.modules.pms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tulingxueyuan.mall.modules.pms.model.PmsProductAttribute;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性参数表 服务类
 * </p>
 *
 * @author peipei
 * @since 2021-09-04
 */
public interface PmsProductAttributeService extends IService<PmsProductAttribute> {

    Page getList(Long attCateId,Integer pageNum,Integer pageSize,Integer type);


    // List<PmsProductAttribute> getList(Long id,Integer type);

    boolean create(PmsProductAttribute productAttribute);

    boolean removes(Long cid,Integer type,List<Long> ids);
}
